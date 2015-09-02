/*
 * JPTGUIView.java
 */
package org.jax.cs.apps.jcms.jpt;

import java.awt.Dialog.ModalExclusionType;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import javax.swing.tree.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.*;
import org.jax.cs.apps.jcms.subsystems.dataAccess.*;
import org.jdesktop.application.Task;

import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.DISP_Mating;
import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.DISP_Litter;
import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.DISP_Strain;
import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.DISP_Genotype;
import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.DISP_Sex;


//--
//import org.jax.cs.apps.jcms.subsystems.dataAccess.*;
; // 1  - Only Valid with Ancestory
//    public final static byte DISP_Litter   =     2; // 2  - Only Valid with Progeny
//    public final static byte DISP_Strain   =     4; // 3
//    public final static byte DISP_Genotype =     8; // 4

//--
/** 
 * The application's main frame.
 * 
 * The JPT main interface
 * 
 * @author Dave Springer
 * 
 * 
 */
public class jptGUIView extends FrameView  {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root");
    protected DataAccessJDBC dbJCMS;
    private String newline = "\n";
    private Properties appConfig;
    private ShowTreeTask treeTask = null;
    public int x;
    public Integer y;
    private int appTreeDepth = 3;
    private byte displayOptions = 3;
    
    /** Constructor
     * 
     * @param app
     * 
     */
    public jptGUIView(SingleFrameApplication app) {
        super(app);
        
        initComponents();
 
        cbMating.setSelected(true);
        cbLitter.setSelected(true);
        
        // Create a tree with only a root node.
        mouseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        mouseTree.setEditable(true);
        mouseTreeModel = new DefaultTreeModel(rootNode) ;
        mouseTree.setModel(mouseTreeModel);
 
        // Load the app properties files Load the MouseID list!
        loadConfiguration();
        
        // Set ancestor radio
        ancestorsAlgorithm.setSelected(true);
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
      
        // Setup the Task for drawing the mouseTree
        @Override
        public void actionPerformed(ActionEvent e) {
            statusMessageLabel.setText("");
            }
        });
        
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
            statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
        }
        });
        
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        btCancel.setEnabled(false);
                
        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        
        @Override
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if ("started".equals(propertyName)) {
                if (!busyIconTimer.isRunning()) {
                    statusAnimationLabel.setIcon(busyIcons[0]);
                    busyIconIndex = 0;
                    busyIconTimer.start();
                }
                messageTimer.setInitialDelay(100000);
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);
                btCancel.setEnabled(true);
            } else if ("done".equals(propertyName)) {
                busyIconTimer.stop();
                statusAnimationLabel.setIcon(idleIcon);
                progressBar.setVisible(false);
                progressBar.setValue(0);
                btCancel.setEnabled(false);
                messageTimer.stop();
                messageTimer.setInitialDelay(5000);
            } else if ("message".equals(propertyName)) {
                String text = (String) (evt.getNewValue());
                statusMessageLabel.setText((text == null) ? "" : text);
                messageTimer.restart();
            } else if ("progress".equals(propertyName)) {
                int value = (Integer) (evt.getNewValue());
                progressBar.setVisible(true);
                progressBar.setIndeterminate(false);
                progressBar.setValue(value);
                btCancel.setEnabled(true);
            }
        }
        });
    }

    private void openConfiguration(){
    
        if (configDialog == null) {
            configDialog = new jptGUIConfiguration();

            configDialog.setTitle("JPT Configuration");
            configDialog.setResizable(false);
            configDialog.setLocation(jptGUIView.this.getFrame().getX() + mouseTreeLabel.getX() + mouseTreeLabel.getWidth() ,jptGUIView.this.getFrame().getY() + mouseTreeLabel.getY() );
            configDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        }
        jptGUIApp.getApplication().show(configDialog);

        return ;
    }
    
//------------------------------------------------------------------------------
    /** A application specific subclass of LoadTreeTask.
     *
     * This Task utlizes the pedigree class create a tree model for the 
     * mouseTree control.
     * 
     */
    private class ShowTreeTask extends LoadTreeTask {
        ShowTreeTask(String strFunction,String strMouseID,Pedigree  myPed) {
            super(jptGUIView.this.getApplication(), strFunction,strMouseID,myPed);
           // stopLoading();  
            treeTask = this;
       //     showImageMessage(imageURL, "loadingWait");
        }

        /** 
         * Stop the Task ...
         */
        @Override protected void cancelled() {
        }

        /**
         * This is the result of task that has completed.  Since we built up a
         * DefaultTreeNodes lets apply that to the Tree Model and then set that
         * model
         * 
         * @param myNode
         */
        @Override protected void succeeded(DefaultMutableTreeNode myNode) {
            if (treeTask == this) {
                 DefaultTreeModel x = new DefaultTreeModel(myNode) ;
                 mouseTree.setModel(x);
            }
        }
        /**
         * If there is an exception then execute this code.
         * @param e
         */
        @Override protected void failed(Throwable e) {
            super.failed(e);
            if (treeTask == this) {

            }
        }

        /**
         * When the task is done then deallocate treeTask.
         */
        @Override protected void finished() {
            super.finished();
            treeTask = null;
        }
    }

//------------------------------------------------------------------------------
    
    /**
     * loadConfiguration  
     * 
     * Read the properties file from disk and connect to the database. 
     * 
     */
    private void loadConfiguration()
    {
        Integer iRetValue = -99;

        try {
            // Get Properties for the application
            jptGUIConfiguration configSubSystem = new jptGUIConfiguration();

            appConfig = configSubSystem.getProperties();
            configSubSystem = null ;
            
            if ( (appConfig == null) || ( 0 == appConfig.size()) )
            {
                
                // Do you want to over right
                iRetValue = JOptionPane.showConfirmDialog(mainPanel, 
                        "Application Configuration file required!\n\n\tCreate one now?");


                // Yes = 0 
                // No = 1, Cancel = 2
                if ( 0 == iRetValue ) {
                    openConfiguration();
                }
                else if ( 1 == iRetValue || 2 == iRetValue ){
                    
                }
            }
            
            dbJCMS = new DataAccessJDBC(appConfig);

            if ( ! dbJCMS.Connected)
            {
                JOptionPane.showMessageDialog(null, "Please Review Database Configuration.", "Database Connection Error", 1); 
                return;
            }

            // Used for the depth the pedigree class will recurse.
            appTreeDepth = Integer.parseInt(appConfig.getProperty("applicationTreeDepth"));

            ArrayList mouseArrList;
            mouseArrList = dbJCMS.getMouseList();


            DefaultComboBoxModel aModel;
            aModel = new DefaultComboBoxModel(mouseArrList.toArray());

            mouseList.setModel(aModel);
        }
        catch (SQLException e)
        {
            if ( -1907 == e.getErrorCode()){
                JOptionPane.showMessageDialog(null,"Database Permissions set incorrectly.\n\tRefer to help page in the about box." , "Database Error...", 1); 
            }
            else {
                JOptionPane.showMessageDialog(null, e.getMessage() + ":" + e.getErrorCode(), "Database Error...", 1); 
            }
            
            //Database Permissions set incorrectly.  Refer to help page in the about box.
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
                
    }
 
    /**
     * addStylesToDocument Create a set of Styles for the supplied 
     * StyledDocument.
     * 
     * @param doc
     */
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
    }

    
    /**
     * The action that starts the background task for building the Tree.
     * @return
     */
   @Action 
    public Task drawTreeTask() {
        Task task = null;
        String searchFunction = "";

        if ( null == dbJCMS)
        {
            JOptionPane.showMessageDialog(null, "Not connected to database!", "Database Connnection Error...", 1); 
             return null;
        }

        
        setDisplayOptions();
                

        dbJCMS.setDisplayOptions(displayOptions);
        
        System.out.print(mouseList.getSelectedItem().toString() + "\n");

        myNode = new DefaultMutableTreeNode(mouseList.getSelectedItem().toString()) ;

        if (progenyAlgorithm.isSelected())
        {
            searchFunction = "FindDescendants";
        }
        if (ancestorsAlgorithm.isSelected() ) 
        {
            searchFunction = "FindAncestors";
        }
               
        Pedigree pedTask = new Pedigree(dbJCMS);
        pedTask.MAX_DEPTH = appTreeDepth; 
        
        task = new ShowTreeTask(searchFunction, mouseList.getSelectedItem().toString() , pedTask );
        return task;
    }

   
   
   /**
    * 
    * 
    * 
    * 
    */
    private void setDisplayOptions(){
        displayOptions = 0 ;
        
        if (cbMating.isSelected()){
            displayOptions += DISP_Mating; 
        }
        
        if (cbLitter.isSelected()){
            displayOptions += DISP_Litter;
        }

        if ( cbStrain.isSelected()){
            displayOptions += DISP_Strain;
        }

        if ( cbGenoType.isSelected()){
            displayOptions += DISP_Genotype;
        }

        if ( cbSex.isSelected()){
            displayOptions += DISP_Sex;
        }
    }

    
   /**
    * The Action that will cancel the execution of the backgraound task.
    * 
    */  
    @Action
    public void stopLoading() {
        if ((treeTask != null) && !treeTask.isDone()) {
            treeTask.cancel(true);
        }
    }
    
    /**
     * Show the about box in a model fashion.
     */
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = jptGUIApp.getApplication().getMainFrame();
            aboutBox = new jptGUIAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        jptGUIApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mouseListLabel = new javax.swing.JLabel();
        mouseList = new javax.swing.JComboBox();
        ancestorsAlgorithm = new javax.swing.JRadioButton();
        progenyAlgorithm = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mouseTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        mouseData = new javax.swing.JTextPane();
        mouseTreeLabel = new javax.swing.JLabel();
        mouseDataLabel = new javax.swing.JLabel();
        btDraw = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        reportDialog = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        cbMating = new javax.swing.JCheckBox();
        cbLitter = new javax.swing.JCheckBox();
        cbGenoType = new javax.swing.JCheckBox();
        cbStrain = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbSex = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        miConfig = new javax.swing.JMenuItem();
        miResetConfiguration = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jSeparator1 = new javax.swing.JSeparator();

        mainPanel.setAutoscrolls(true);
        mainPanel.setMaximumSize(new java.awt.Dimension(32767, 32760));
        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext().getResourceMap(jptGUIView.class);
        mouseListLabel.setFont(resourceMap.getFont("mouseListLabel.font")); // NOI18N
        mouseListLabel.setText(resourceMap.getString("mouseListLabel.text")); // NOI18N
        mouseListLabel.setName("mouseListLabel"); // NOI18N

        mouseList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        mouseList.setName("mouseID"); // NOI18N
        mouseList.setOpaque(false);
        mouseList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item5", "Item6", "Item7", "Item8" }));

        ancestorsAlgorithm.setText(resourceMap.getString("ancestorsAlgorithm.text")); // NOI18N
        ancestorsAlgorithm.setName("ancestorsAlgorithm"); // NOI18N
        ancestorsAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ancestorsAlgorithmActionPerformed(evt);
            }
        });

        progenyAlgorithm.setText(resourceMap.getString("progenyAlgorithm.text")); // NOI18N
        progenyAlgorithm.setName("progenyAlgorithm"); // NOI18N
        progenyAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progenyAlgorithmActionPerformed(evt);
            }
        });

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mouseTree.setAutoscrolls(mainPanel.getAutoscrolls());
        mouseTree.setModel(null);
        mouseTree.setName("mouseTree"); // NOI18N
        mouseTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseTreeMouseClicked(evt);
            }
        });
        mouseTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                mouseTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(mouseTree);

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        mouseData.setEditable(false);
        mouseData.setName("mouseData"); // NOI18N
        jScrollPane2.setViewportView(mouseData);

        mouseTreeLabel.setFont(resourceMap.getFont("mouseTreeLabel.font")); // NOI18N
        mouseTreeLabel.setText(resourceMap.getString("mouseTreeLabel.text")); // NOI18N
        mouseTreeLabel.setName("mouseTreeLabel"); // NOI18N

        mouseDataLabel.setFont(resourceMap.getFont("mouseDataLabel.font")); // NOI18N
        mouseDataLabel.setText(resourceMap.getString("mouseDataLabel.text")); // NOI18N
        mouseDataLabel.setName("mouseDataLabel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext().getActionMap(jptGUIView.class, this);
        btDraw.setAction(actionMap.get("drawTreeTask")); // NOI18N
        btDraw.setText(resourceMap.getString("btDraw.text")); // NOI18N
        btDraw.setName("btDraw"); // NOI18N

        btCancel.setAction(actionMap.get("stopLoading")); // NOI18N
        btCancel.setText(resourceMap.getString("btCancel.text")); // NOI18N
        btCancel.setName("btCancel"); // NOI18N

        reportDialog.setText(resourceMap.getString("reportDialog.text")); // NOI18N
        reportDialog.setName("reportDialog"); // NOI18N
        reportDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportDialogActionPerformed(evt);
            }
        });

        btExit.setAction(actionMap.get("quit")); // NOI18N
        btExit.setName("btExit"); // NOI18N

        cbMating.setText(resourceMap.getString("cbMating.text")); // NOI18N
        cbMating.setName("cbMating"); // NOI18N

        cbLitter.setText(resourceMap.getString("cbLitter.text")); // NOI18N
        cbLitter.setName("cbLitter"); // NOI18N

        cbGenoType.setText(resourceMap.getString("cbGenoType.text")); // NOI18N
        cbGenoType.setName("cbGenoType"); // NOI18N

        cbStrain.setText(resourceMap.getString("cbStrain.text")); // NOI18N
        cbStrain.setName("cbStrain"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        cbSex.setText(resourceMap.getString("cbSex.text")); // NOI18N
        cbSex.setName("cbSex"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mouseListLabel)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(mouseList, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDraw)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel))
                    .addComponent(mouseDataLabel)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(reportDialog)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExit))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(ancestorsAlgorithm)
                            .addComponent(progenyAlgorithm)
                            .addComponent(mouseTreeLabel))
                        .addGap(81, 81, 81)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbSex)
                            .addComponent(jLabel2)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(cbLitter)
                                .addGap(10, 10, 10)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbStrain)
                                    .addComponent(cbGenoType)))
                            .addComponent(cbMating)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                        .addGap(2, 2, 2)))
                .addGap(10, 10, 10))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(mouseListLabel)
                .addGap(1, 1, 1)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mouseList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btDraw, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(ancestorsAlgorithm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progenyAlgorithm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mouseTreeLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(cbStrain)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbGenoType))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(cbMating)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbLitter)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSex)))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mouseDataLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportDialog)
                    .addComponent(btExit))
                .addGap(12, 12, 12))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        miConfig.setText(resourceMap.getString("miConfig.text")); // NOI18N
        miConfig.setName("miConfig"); // NOI18N
        miConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConfigActionPerformed(evt);
            }
        });
        fileMenu.add(miConfig);

        miResetConfiguration.setText(resourceMap.getString("miResetConfiguration.text")); // NOI18N
        miResetConfiguration.setName("miResetConfiguration"); // NOI18N
        miResetConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miResetConfigurationActionPerformed(evt);
            }
        });
        fileMenu.add(miResetConfiguration);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setAction(actionMap.get("showAboutBox")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N
        progressBar.setString(""); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(statusPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
            .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(statusPanelLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(65, Short.MAX_VALUE)))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
 
    /**
     * Select either Ancestor disable Progeny.
     * 
     * @param evt
     */
    
private void ancestorsAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ancestorsAlgorithmActionPerformed

    ancestorsAlgorithm.setSelected(true);
    progenyAlgorithm.setSelected(false);
    

}//GEN-LAST:event_ancestorsAlgorithmActionPerformed
    
/**
 * Select either Progeny disable Ancestor.
 * 
 * @param evt
 */
private void progenyAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progenyAlgorithmActionPerformed
    ancestorsAlgorithm.setSelected(false);
    progenyAlgorithm.setSelected(true);
}//GEN-LAST:event_progenyAlgorithmActionPerformed

/**
 * reportDialogActionPerformed - Open up the report form.
 * @param evt
 */
private void reportDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportDialogActionPerformed
            
    String algorithm = "";
     if (progenyAlgorithm.isSelected())
         algorithm = "Progeny";
    
     if (ancestorsAlgorithm.isSelected() ) 
        algorithm = "Ancestors";

    
    setDisplayOptions();
   
    String strMouseID;
    
   strMouseID = mouseList.getSelectedItem().toString().trim();
    if ( null != jptReport && false == jptReport.isShowing())
    {
        jptReport.dispose();
        jptReport = null;
    }
   
    if (jptReport == null) {
        jptReport  = new jptGUIReport(strMouseID,algorithm, displayOptions);

        jptReport.setTitle("JPT Report");
        jptReport.setResizable(false);
        jptReport.setLocation(jptGUIView.this.getFrame().getX() + mouseTreeLabel.getX() + mouseTreeLabel.getWidth() ,jptGUIView.this.getFrame().getY() + mouseTreeLabel.getY() );
        jptReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    }
    jptGUIApp.getApplication().show(jptReport);
 
    return;
}//GEN-LAST:event_reportDialogActionPerformed


/**
 * mouseTreeMouseClicked when an item is selected query the database for mouse
 * data.
 * 
 * @param evt
 */
private void mouseTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseTreeMouseClicked
// TODO add your handling code here:
    String strMouseID = "";
            
    if ( 2 <= evt.getClickCount())
    {
        // Double click,
        
       int index = mouseTree.getSelectionPath().getLastPathComponent().toString().indexOf(":");
       if ( -1 == index)
       {
           strMouseID = mouseTree.getSelectionPath().getLastPathComponent().toString().trim();
       }   
       else
       {
           strMouseID = mouseTree.getSelectionPath().getLastPathComponent().toString().substring(0, index).trim();
       }
        
        mouseList.setSelectedItem(strMouseID);

    }
    
}//GEN-LAST:event_mouseTreeMouseClicked

/**
 * miConfigActionPerformed When menu item "Configuration" selected open 
 * Configuration form.
 * 
 * @param evt
 */
private void miConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConfigActionPerformed
        
    openConfiguration();
 
}//GEN-LAST:event_miConfigActionPerformed

/**
 * miResetConfigActionPerformed When menu item "Reset Configuration" 
 * selected open configuration file and load mouseList, by calling 
 * loadConfiguration. 
 * 
 * @param evt
 */
private void miResetConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miResetConfigurationActionPerformed
            loadConfiguration();
}//GEN-LAST:event_miResetConfigurationActionPerformed

    
/**
 * mouseTreeValueChanged 
 *   When the value in the Tree is selected retrieve all the data assocated 
 * with that mouse ID.
 * 
 * @param evt
 */    
private void mouseTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {                                       

    mouseData.setText("");
    
    StyledDocument doc;
    mouseData.setText("");

    doc = mouseData.getStyledDocument();
    
    // Text types ...
    //  "regular", "italic", "bold", "small", "large", "button", "icon"
    //               

        try {
            ArrayList mouseDataArr;
            doc = mouseData.getStyledDocument();
            addStylesToDocument(doc);
            
           String strMouseID;
           String strLitterID;
           String strMatingID;

           // If the tree changes to NULL, eg when we update with new model lets
           // determine this and simply return.
           if (null == mouseTree.getSelectionPath())
           {
               return;
           }
           
           int index = mouseTree.getSelectionPath().getLastPathComponent().toString().indexOf(":");
           if ( -1 == index)
           {
               strMouseID = mouseTree.getSelectionPath().getLastPathComponent().toString().trim();
           }   
           else
           {
               strMouseID = mouseTree.getSelectionPath().getLastPathComponent().toString().substring(0, index);
           }
           

           // getMouseMatingID
           strMatingID = dbJCMS.getMouseMatingID(strMouseID).trim();

           // getMouseLitterID
           strLitterID = dbJCMS.getMouseLitterID(strMouseID).trim();

           
           doc.insertString(doc.getLength(), "MouseID : ", doc.getStyle("bold"));
           doc.insertString(doc.getLength(), strMouseID + "\n", doc.getStyle("regular"));

           doc.insertString(doc.getLength(), "MatingID : ", doc.getStyle("bold"));
           doc.insertString(doc.getLength(), strMatingID + "\n" ,  doc.getStyle("regular"));

           doc.insertString(doc.getLength(), "LitterID : ", doc.getStyle("bold"));
           doc.insertString(doc.getLength(),  strLitterID + "\n", doc.getStyle("regular"));
 
           
           // getMouseStrain
           doc.insertString(doc.getLength(), "Strain : \n", doc.getStyle("bold"));
           mouseDataArr = dbJCMS.getMouseStrain(strMouseID);
           for (int i=0; i < mouseDataArr.size(); i++) {
                doc.insertString(doc.getLength(),  mouseDataArr.get(i) + newline ,
                                 doc.getStyle("regular"));
           }

           
           // getMouseGenoType
           doc.insertString(doc.getLength(), "Genotype(s) : \n", doc.getStyle("bold"));
           mouseDataArr = dbJCMS.getMouseGenoType(strMouseID);
           for (int i=0; i < mouseDataArr.size(); i++) {
                doc.insertString(doc.getLength(),  mouseDataArr.get(i) + newline ,
                                 doc.getStyle("regular"));
           }

           // getSyblings
           doc.insertString(doc.getLength(), "Syblings : \n", doc.getStyle("bold"));
           mouseDataArr = dbJCMS.getSyblings(strMouseID);
           for (int i=0; i < mouseDataArr.size(); i++) {
                doc.insertString(doc.getLength(), mouseDataArr.get(i) + newline,
                                 doc.getStyle("regular"));
           }

           // Mouse Data
           doc.insertString(doc.getLength(), "Mouse Record Data : ", doc.getStyle("bold"));
           mouseDataArr = dbJCMS.getMouseData(strMouseID);
           for (int i=0; i < mouseDataArr.size(); i++) {
                doc.insertString(doc.getLength(),  mouseDataArr.get(i) + newline ,
                                 doc.getStyle("regular"));
           }

           mouseData.moveCaretPosition(0);
           
           
        } 
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error...", 1); 
        }
        
        catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }

 

}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ancestorsAlgorithm;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDraw;
    private javax.swing.JButton btExit;
    private javax.swing.JCheckBox cbGenoType;
    private javax.swing.JCheckBox cbLitter;
    private javax.swing.JCheckBox cbMating;
    private javax.swing.JCheckBox cbSex;
    private javax.swing.JCheckBox cbStrain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miConfig;
    private javax.swing.JMenuItem miResetConfiguration;
    private javax.swing.JTextPane mouseData;
    private javax.swing.JLabel mouseDataLabel;
    private javax.swing.JComboBox mouseList;
    private javax.swing.JLabel mouseListLabel;
    private javax.swing.JTree mouseTree;
    private javax.swing.JLabel mouseTreeLabel;
    private javax.swing.JRadioButton progenyAlgorithm;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton reportDialog;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private DefaultTreeModel mouseTreeModel;
    private DefaultMutableTreeNode myNode;
    private jptGUIConfiguration configDialog;
    private jptGUIReport jptReport;
}
