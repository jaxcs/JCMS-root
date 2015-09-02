/*
 * jptGUIReport.java
 *
 * Created on August 26, 2008, 10:31 AM
 */

package org.jax.cs.apps.jcms.jpt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import org.jdesktop.application.Task;
import org.jax.cs.apps.jcms.subsystems.dataAccess.*;
import java.util.Properties;
import org.jdesktop.application.TaskMonitor;

/**
 * jptGUIReport 
 * 
 * This form 
 * 
 * @author  daves
 */
public class jptGUIReport extends javax.swing.JFrame {
    String mouseID;
    String algorithm;
    File   rptFile;
    static JFileChooser fc ;

    public final static int HTML = 0 ;
    public final static int TEXT = 1 ;
    
    private DataAccessJDBC dbJCMS = null ;
    private Pedigree ped = null;
    private Task reportTaskX = null;
    private byte displayOptions = 3;
    
    /** Executes when class is loaded.  The file chooser seem to take a long
     * time to open.
     */
    static {
        fc = new JFileChooser();
    }

    /**
     * jptGUIReport pass the mouse id and type of report to initialize the form.
     * 
     * @param strMouseID
     * @param strProgeny
     */
    public jptGUIReport(String strMouseID, String strProgeny, byte displayOptions) {
        initComponents();
        
        this.displayOptions = displayOptions;
        
        lbMouseID.setText(lbMouseID.getText() + strMouseID);
        mouseID = strMouseID;
               
        if ( 0 == strProgeny.compareTo("Ancestors"))
        {
            ancestorAlgorithms.setSelected(true);
        }
        if ( 0 == strProgeny.compareTo("Progeny"))
        {
            progenyAlgorithm.setSelected(true);
        }
                
        // status bar initialization - message timeout, idle icon and busy animation, etc
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext().getResourceMap(jptGUIReport.class);
                
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            statusMessageLabel.setText("");
            }
        });
        
        messageTimer.setRepeats(false);
        int busyAnimationRate = 30 ; // resourceMap.getInteger("StatusBar.busyAnimationRate");
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
        TaskMonitor taskMonitor = new TaskMonitor(org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext());
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
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);
                btCancel.setEnabled(true);
            } else if ("done".equals(propertyName)) {
                busyIconTimer.stop();
                statusAnimationLabel.setIcon(idleIcon);
                progressBar.setVisible(false);
                progressBar.setValue(0);
                btCancel.setEnabled(false);                
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

    
    
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbMouseID = new javax.swing.JLabel();
        lbReportFile = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        reportFile = new javax.swing.JTextField();
        btFileOpen = new javax.swing.JButton();
        reportType = new javax.swing.JComboBox();
        lbMouseID1 = new javax.swing.JLabel();
        lbMouseID2 = new javax.swing.JLabel();
        numberOfGenerations = new javax.swing.JComboBox();
        btCancel = new javax.swing.JButton();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        myButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        ancestorAlgorithms = new javax.swing.JRadioButton();
        progenyAlgorithm = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext().getResourceMap(jptGUIReport.class);
        lbMouseID.setText(resourceMap.getString("lbMouseID.text")); // NOI18N
        lbMouseID.setName("lbMouseID"); // NOI18N

        lbReportFile.setText(resourceMap.getString("lbReportFile.text")); // NOI18N
        lbReportFile.setName("lbReportFile"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        reportFile.setText(resourceMap.getString("reportFile.text")); // NOI18N
        reportFile.setName("reportFile"); // NOI18N

        btFileOpen.setText(resourceMap.getString("btFileOpen.text")); // NOI18N
        btFileOpen.setName("btFileOpen"); // NOI18N
        btFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileOpenActionPerformed(evt);
            }
        });

        reportType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HTML", "TEXT" }));
        reportType.setName("reportType"); // NOI18N

        lbMouseID1.setText(resourceMap.getString("lbMouseID1.text")); // NOI18N
        lbMouseID1.setName("lbMouseID1"); // NOI18N

        lbMouseID2.setText(resourceMap.getString("lbMouseID2.text")); // NOI18N
        lbMouseID2.setName("lbMouseID2"); // NOI18N

        numberOfGenerations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        numberOfGenerations.setName("numberOfGenerations"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class).getContext().getActionMap(jptGUIReport.class, this);
        btCancel.setAction(actionMap.get("stopReportingX")); // NOI18N
        btCancel.setText(resourceMap.getString("btCancel.text")); // NOI18N
        btCancel.setName("btCancel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        myButton.setAction(actionMap.get("myReport")); // NOI18N
        myButton.setText(resourceMap.getString("myButton.text")); // NOI18N
        myButton.setName("myButton"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        ancestorAlgorithms.setText(resourceMap.getString("ancestorAlgorithms.text")); // NOI18N
        ancestorAlgorithms.setName("ancestorAlgorithms"); // NOI18N
        ancestorAlgorithms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ancestorAlgorithmsActionPerformed(evt);
            }
        });

        progenyAlgorithm.setText(resourceMap.getString("progenyAlgorithm.text")); // NOI18N
        progenyAlgorithm.setName("progenyAlgorithm"); // NOI18N
        progenyAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progenyAlgorithmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lbReportFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                        .addComponent(statusMessageLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(myButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reportFile, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btFileOpen))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbMouseID1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbMouseID2))
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(numberOfGenerations, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbMouseID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                .addComponent(ancestorAlgorithms)
                                .addComponent(progenyAlgorithm)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMouseID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ancestorAlgorithms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progenyAlgorithm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMouseID1)
                    .addComponent(reportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMouseID2)
                    .addComponent(numberOfGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbReportFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btFileOpen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancel)
                    .addComponent(myButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusAnimationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

/**
 * TODO
 * 
 * @param evt
 */
private void btFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileOpenActionPerformed

    int retval = fc.showOpenDialog(null);
    if (retval == JFileChooser.APPROVE_OPTION) {
        rptFile = fc.getSelectedFile();
        // DO YOUR PROCESSING HERE. OPEN FILE OR ...
//        JOptionPane.showMessageDialog(null, rptFile.toString() , "GetFile" , 1); 
        reportFile.setText(rptFile.toString());
    }    
    
}//GEN-LAST:event_btFileOpenActionPerformed

/**
 * 
 * 
 * @param evt
 */
private void ancestorAlgorithmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ancestorAlgorithmsActionPerformed
    ancestorAlgorithms.setSelected(true);
    progenyAlgorithm.setSelected(false);
}//GEN-LAST:event_ancestorAlgorithmsActionPerformed

/**
 * 
 * @param evt
 */
private void progenyAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progenyAlgorithmActionPerformed
    ancestorAlgorithms.setSelected(false);
    progenyAlgorithm.setSelected(true);

}//GEN-LAST:event_progenyAlgorithmActionPerformed


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jptGUIReport("60662","Ancestors",(byte) 15).setVisible(true);
            }
        });
    }

    /**
     * 
     */
    @org.jdesktop.application.Action
    public void stopReportingX() {
        if ((reportTaskX != null) && !reportTaskX.isDone()) {
            reportTaskX.cancel(true);
        }
    }
       
    /**
     * 
     * @return
     */
    @org.jdesktop.application.Action
    public Task myReport() {
        Integer iString = -99;

        // Test for File
        if (  (null == rptFile) || (0 == rptFile.toString().compareTo(""))   )
        {
            // Use what is in the text box
            if ( reportFile.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Specify A File", "No File Selected", 1); 
                return null;
            }
            else
                rptFile = new File(reportFile.getText().trim());
        }

        // What do we do if file exists
        if (  rptFile.exists()  )
        {

            // Do you want to over right
            iString = JOptionPane.showConfirmDialog(rootPane, "File Exists, do you wish to overwrite?");

            // Yes = 0 
            // No = 1, Cancel = 2
            if ( 1 == iString || 2 == iString )
                return(null);
        }
        
        jptGUIConfiguration configSubSystem = new jptGUIConfiguration();

        Properties appConfig;
        appConfig = configSubSystem.getProperties();
        configSubSystem = null ;
        
        dbJCMS = new DataAccessJDBC(appConfig);
        
        dbJCMS.setDisplayOptions(displayOptions);
        if ( ! dbJCMS.Connected)
        {
            JOptionPane.showMessageDialog(null, "Please Review Database Configuration.", "Database Connection Error", 1); 
            return null;
        }
        ped = new Pedigree(dbJCMS);
        ped.MAX_DEPTH = Integer.parseInt(numberOfGenerations.getItemAt(numberOfGenerations.getSelectedIndex()).toString());
        
        reportTaskX = new MyReportTask(org.jdesktop.application.Application.getInstance(org.jax.cs.apps.jcms.jpt.jptGUIApp.class));
        return reportTaskX;
       
    }

        /**
         * 
         */

        private class MyReportTask extends org.jdesktop.application.Task<String, Void> {
            MyReportTask(org.jdesktop.application.Application app) {
                // Runs on the EDT.  Copy GUI state that
                // doInBackground() depends on from parameters
                // to MyReportTask fields, here.
                super(app);
            }
            @Override protected String doInBackground() {
                // Your Task's code here.  This method runs
                // on a background thread, so don't reference
                // the Swing GUI from here.

                // "ReportAncestors"

                if (ancestorAlgorithms.isSelected() ){
                    setMessage("Generating Ancestors Report ...");
                    ped.ReportAncestors(0,mouseID);

                } // "ReportDescendants"
                else if ( progenyAlgorithm.isSelected()){
                    setMessage("Generating Progeny Report ...");
                    ped.ReportDescendants(0,mouseID);
                }
                else
                    return("Empty Report **Error**");

                String retString = "";
                switch (reportType.getSelectedIndex())
                {
                    case HTML:
                        retString = ped.getHTMLReport();
                        break;
                    case TEXT:
                        retString = ped.getReport();
                        break;
                    default:
                        retString = "Error ...";
                        break;
                }        
                return(retString);
            }

            /**
             * 
             * @param result
             */
            @Override protected void succeeded(String result) {
                // Runs on the EDT.  Update the GUI based on
                // the result computed by doInBackground().

                setMessage("Report Complete");

                PrintWriter out = null;
                try
                    {
                        if ( null == rptFile  )
                        {
                            rptFile = new File(reportFile.getText().trim());
                        }

                        // Create an Output Stream
                        FileOutputStream outStream = new FileOutputStream(rptFile);
                        // Filter bytes to ASCII
                        out = new PrintWriter(outStream);

                        out.print(result);

                    }
                    catch  (FileNotFoundException e) 
                    {
                            e.printStackTrace();
                    }
                    finally
                    {
                         out.close();
                         rptFile = new File("");
                    }
                    setMessage("Report Written");

                }


                /**
                 * 
                 */
                @Override protected void cancelled() {
                    setMessage("Report Cancelled!");
                }

        /**
         * 
         * @param e
         */
        @Override protected void interrupted(InterruptedException e) {
            setMessage("Report Interrupted!");
        }

        /**
         * 
         * @param e
         */
        @Override protected void failed(Throwable e) {
            setMessage("Report Failed!");
        }

        /**
         * 
         */
        @Override protected void finished() {
            ped  = null;
            setMessage("Report Finished.");
        }

    } // End of MyReportTask


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ancestorAlgorithms;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btFileOpen;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbMouseID;
    private javax.swing.JLabel lbMouseID1;
    private javax.swing.JLabel lbMouseID2;
    private javax.swing.JLabel lbReportFile;
    private javax.swing.JButton myButton;
    private javax.swing.JComboBox numberOfGenerations;
    private javax.swing.JRadioButton progenyAlgorithm;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextField reportFile;
    private javax.swing.JComboBox reportType;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

}
