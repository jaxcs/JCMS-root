/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package org.jax.cs.jcmsWebInstaller;

import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author cnh
 */
public class DatabaseSetupPanel extends JCMSPanel {
    private JFileChooser chooser;
    private InputDTO inputDTO = null;
    private PropertiesDTO propertiesDTO = new PropertiesDTO();
    
    public DatabaseSetupPanel(JDesktopPane desktopPane, JFrame frame) {
        super.JCMSPanel(desktopPane, frame);
        initComponents();
        
        txtMessage.setText("");
        this.txtMySQLRemoteUserName.setEnabled(false);
        this.hidMySQLRemotePassword.setEnabled(false);
        this.txtMySQLUserName.setText(propertiesDTO.getMySQLUserName());
        this.hidMySQLPassword.setText(propertiesDTO.getMySQLPd());
        this.txtMySQLServerName.setText(propertiesDTO.getMySQLHostName());
        this.txtMySQLPort.setText(propertiesDTO.getMySQLPort());
        
        this.txtJCMSWebInstallationDirectory.setEnabled(false);
    }

    private Boolean areValidCredentials(Boolean showMessage) {
        Boolean areValidCredentials = true;
        
        if (this.txtMySQLUserName.getText().trim().isEmpty()) {
            if (showMessage) txtMessage.setText("Please enter your MySQL server user name.");
            areValidCredentials = false;
        } else 
        if (this.txtMySQLServerName.getText().trim().isEmpty()) {
            if (showMessage) txtMessage.setText("Please enter your MySQL server host name.");
            areValidCredentials = false;
        } else 
        if (this.txtMySQLPort.getText().trim().isEmpty()) {
            if (showMessage) txtMessage.setText("Please enter your MySQL server port.");
            areValidCredentials = false;
        }     
        
        return areValidCredentials;
    }
    
    private Boolean areValidRemoteCredentials(Boolean showMessage) {
        Boolean areValidCredentials = true;
        if (!this.txtMySQLServerName.getText().equalsIgnoreCase("localhost")) {
            if (this.txtMySQLRemoteUserName.getText().trim().isEmpty()) {
                if (showMessage) txtMessage.setText("Please enter your MySQL remote server user name.");
                areValidCredentials = false;
            } 
        }
        return areValidCredentials;
    }
    
    public void refreshPanel() {
        try {
            this.cbxJCMSDatabaseName.removeAllItems();
            if (((JCMSWebInstallerApp)this.getFrame()).getIsNewInstallation()) {
                this.cbxJCMSDatabaseName.setEditable(true);
                this.cbxJCMSDatabaseName.addItem(propertiesDTO.getJCMSDatabaseName());

                this.txtCurrentMySQLDatabases.setText("");
                for (String s: this.getDatabaseNames()) {
                    this.txtCurrentMySQLDatabases.append(s + "\n");
                }
                this.lblCurrentMySQLDatabases.setVisible(true);
                this.txtCurrentMySQLDatabases.setVisible(true);
                this.getFrame().repaint();
                this.getFrame().validate();
            } else {
                this.lblCurrentMySQLDatabases.setVisible(false);
                this.getScrollPane(this.txtCurrentMySQLDatabases).setVisible(false);
                this.cbxJCMSDatabaseName.setEditable(false);
                if (this.areValidCredentials(Boolean.FALSE) && this.areValidRemoteCredentials(Boolean.FALSE)) {
                    this.cbxJCMSDatabaseName.addItem("");
                    for (String s: this.getDatabaseNames()) {
                        this.cbxJCMSDatabaseName.addItem(s);
                    }
                    this.getFrame().repaint();
                    this.getFrame().validate();
                }
            }
        } catch (JCMSException e) {
            
        } catch (NullPointerException npe) {
            // Page is refreshed before valid credentials are entered
            // Handles user using the back button
        }
    }
    
    private ArrayList<String> getDatabaseNames() throws JCMSException {
        ArrayList<String> rtnAry = new ArrayList<String> ();
        Connector connector = new Connector(this.getInputDTO());
        try {
            connector.connect() ;
            rtnAry = connector.getDatabaseNames();
        } catch (JCMSException e) {
            new JCMSException("MySQL connection failed.  Please check user names and passwords.");
        } finally {
            // TODO revisit this.  Change connector to static.
            connector.disconnect();
        }
        return rtnAry;
    }
    
    private String getPassword() {
        char[] pass = this.hidMySQLPassword.getPassword();
        return new String(pass);
    }
    
    private String getRemotePassword() {
        char[] pass = this.hidMySQLRemotePassword.getPassword();
        return new String(pass);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMySQLServerName = new javax.swing.JTextField();
        txtMySQLUserName = new javax.swing.JTextField();
        hidMySQLPassword = new javax.swing.JPasswordField();
        cbxJCMSDatabaseName = new javax.swing.JComboBox();
        txtMessage = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtJCMSWebInstallationDirectory = new javax.swing.JTextField();
        btnSelectDirectory = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtMySQLPort = new javax.swing.JTextField();
        lblCurrentMySQLDatabases = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCurrentMySQLDatabases = new javax.swing.JTextArea();
        hidMySQLRemotePassword = new javax.swing.JPasswordField();
        txtMySQLRemoteUserName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonNextActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonNextActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("JCMS Web Setup");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setText("MySQL User Name");

        jLabel3.setText("MySQL Password");

        jLabel4.setText("MySQL Server Name");

        jLabel5.setText("JCMS Database Name");

        txtMySQLServerName.setToolTipText("MySQL server name");
        txtMySQLServerName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMySQLServerNameFocusLost(evt);
            }
        });

        txtMySQLUserName.setToolTipText("MySQL database administrator account");
        txtMySQLUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMySQLUserNameFocusLost(evt);
            }
        });

        hidMySQLPassword.setToolTipText("MySQL database administrator account password");
        hidMySQLPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hidMySQLPasswordFocusLost(evt);
            }
        });

        cbxJCMSDatabaseName.setEditable(true);
        cbxJCMSDatabaseName.setToolTipText("JCMS database name (jcms_db)");

        txtMessage.setForeground(new java.awt.Color(255, 0, 0));
        txtMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtMessage.setText("[Message Field]");

        jLabel7.setText("JCMS Web Installation Directory");

        txtJCMSWebInstallationDirectory.setToolTipText("Path to where JCMS Web is installed");
        txtJCMSWebInstallationDirectory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJCMSWebInstallationDirectoryFocusLost(evt);
            }
        });
        txtJCMSWebInstallationDirectory.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtJCMSWebInstallationDirectoryInputMethodTextChanged(evt);
            }
        });

        btnSelectDirectory.setText("...");
        btnSelectDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectDirectoryActionPerformed(evt);
            }
        });

        jLabel6.setText("MySQL Port");

        txtMySQLPort.setToolTipText("MySQL server name");
        txtMySQLPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMySQLPortFocusLost(evt);
            }
        });

        lblCurrentMySQLDatabases.setText("MySQL Databases");

        txtCurrentMySQLDatabases.setEditable(false);
        txtCurrentMySQLDatabases.setBackground(new java.awt.Color(204, 204, 204));
        txtCurrentMySQLDatabases.setColumns(20);
        txtCurrentMySQLDatabases.setRows(5);
        jScrollPane1.setViewportView(txtCurrentMySQLDatabases);

        hidMySQLRemotePassword.setEditable(false);
        hidMySQLRemotePassword.setToolTipText("MySQL remote database administrator account password");
        hidMySQLRemotePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hidMySQLRemotePasswordActionPerformed(evt);
            }
        });
        hidMySQLRemotePassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hidMySQLRemotePasswordFocusLost(evt);
            }
        });

        txtMySQLRemoteUserName.setEditable(false);
        txtMySQLRemoteUserName.setToolTipText("MySQL remote database administrator account");
        txtMySQLRemoteUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMySQLRemoteUserNameFocusLost(evt);
            }
        });

        jLabel8.setText("MySQL Remote Password");

        jLabel9.setText("MySQL Remote User Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(18, 18, 18)
                        .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(121, 121, 121))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hidMySQLPassword)
                            .addComponent(txtMySQLUserName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMySQLServerName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJCMSWebInstallationDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelectDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nextButton)
                                .addGap(48, 48, 48))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMySQLRemoteUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                    .addComponent(hidMySQLRemotePassword))
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(lblCurrentMySQLDatabases))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxJCMSDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMySQLPort, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtJCMSWebInstallationDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectDirectory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMySQLServerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMySQLUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hidMySQLPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMySQLRemoteUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hidMySQLRemotePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMySQLPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxJCMSDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCurrentMySQLDatabases)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(txtMessage)
                    .addComponent(backButton))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonNextActionPerformed
        Boolean blnContinue = true;
        ResultDTO resultDTO = null;
        Connector connector = null;
        String tempMsg = "";
        String strAction = (((JCMSWebInstallerApp)this.getFrame()).getIsNewInstallation() ? "enter" : "select" );
       
        if (this.txtJCMSWebInstallationDirectory.getText().trim().isEmpty()) {
            txtMessage.setText("Please select where you would like to install JCMS Web");
            blnContinue = false;
        } else if (this.cbxJCMSDatabaseName.getSelectedItem() == null || 
                   this.cbxJCMSDatabaseName.getSelectedItem().toString().trim().isEmpty()) {
            txtMessage.setText("Please "+ strAction + " the name of your JCMS database to "
                    + (((JCMSWebInstallerApp)this.getFrame()).getIsNewInstallation() ? "install" : "update" ) +".");
            blnContinue = false;
        } else if (!areValidCredentials(true)) {
            blnContinue = false;
        } else if (!areValidRemoteCredentials(true)) {
            blnContinue = false;
        } 

        try {
            if (blnContinue) {
                connector = new Connector(this.getInputDTO());
                if (!connector.getMySQLDriver()) {
                    JOptionPane.showMessageDialog(null, "MySQL JDBC Driver Not Found!", "MySQL Connection", 0);
                } else {
                    // Test each set of credentials first.
                    connector.testLocalConnection();
                    connector.testRemoteConnection();
                    
                    connector.connect();
                    // Database connection established.  Use this connection for all remaining panels.
                    ((JCMSWebInstallerApp)this.getFrame()).setConnector(connector); 
                }

                ArrayList<String> databaseNames = connector.getDatabaseNames();
                if (((JCMSWebInstallerApp)this.getFrame()).getIsNewInstallation()) {
                    for (String databaseName : databaseNames) {
                        if (databaseName.compareToIgnoreCase(this.cbxJCMSDatabaseName.getSelectedItem().toString().trim()) == 0) {
                            this.txtMessage.setText("Database already exists.  Please enter a unique database name.");
                            blnContinue = false;
                            break;
                        }
                    }
                } 

                if (blnContinue) {
                    this.nextPanel(this, this.getJcmsView().getInstallingJCMSWebPanel(), this.getJcmsView());
                    txtMessage.setText("");
                } else {
                    this.refreshPanel();
                }
                    
            }
        } catch (JCMSException e) {
            txtMessage.setText("Database connect failed. Invalid user name and password.\n");
            this.refreshPanel();
            JOptionPane.showMessageDialog(null, "Database connect failed.  Invalid user name or password. \n\n" + e, "MySQL Connection Failed", 0);
        }
        
    }//GEN-LAST:event_nextButtonNextActionPerformed

    private void backButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonNextActionPerformed
        this.previousPanel(this, this.getJcmsView());
    }//GEN-LAST:event_backButtonNextActionPerformed

    private void txtJCMSWebInstallationDirectoryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJCMSWebInstallationDirectoryFocusLost

    }//GEN-LAST:event_txtJCMSWebInstallationDirectoryFocusLost

    private void txtJCMSWebInstallationDirectoryInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtJCMSWebInstallationDirectoryInputMethodTextChanged

    }//GEN-LAST:event_txtJCMSWebInstallationDirectoryInputMethodTextChanged

    private void btnSelectDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectDirectoryActionPerformed
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("JCMSWeb Installation Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "+  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "+  chooser.getSelectedFile());
            this.txtJCMSWebInstallationDirectory.setText( this.removeAllWhitespace(chooser.getSelectedFile().toString()) );
        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_btnSelectDirectoryActionPerformed

    private void hidMySQLPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hidMySQLPasswordFocusLost
        if (this.areValidCredentials(Boolean.TRUE)) {
            this.refreshPanel();
        }
    }//GEN-LAST:event_hidMySQLPasswordFocusLost

    private void txtMySQLServerNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMySQLServerNameFocusLost
        if (this.areValidCredentials(Boolean.TRUE)) {
            if (this.txtMySQLServerName.getText().equalsIgnoreCase("localhost")) {
                this.txtMySQLRemoteUserName.setEnabled(false);
                this.hidMySQLRemotePassword.setEnabled(false);
            } else {
                this.txtMySQLRemoteUserName.setEnabled(true);
                this.hidMySQLRemotePassword.setEnabled(true);
                this.txtMySQLRemoteUserName.setEditable(true);
                this.hidMySQLRemotePassword.setEditable(true);
            }
                
            this.refreshPanel();
        }
    }//GEN-LAST:event_txtMySQLServerNameFocusLost

    private void txtMySQLPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMySQLPortFocusLost
        if (this.areValidCredentials(Boolean.TRUE)) {
            this.refreshPanel();
        }
    }//GEN-LAST:event_txtMySQLPortFocusLost

    private void hidMySQLRemotePasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hidMySQLRemotePasswordFocusLost
        if (this.areValidRemoteCredentials(Boolean.TRUE)) {
            this.refreshPanel();
        }
    }//GEN-LAST:event_hidMySQLRemotePasswordFocusLost

    private void txtMySQLRemoteUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMySQLRemoteUserNameFocusLost
        if (this.areValidRemoteCredentials(Boolean.TRUE)) {
            this.refreshPanel();
        }
    }//GEN-LAST:event_txtMySQLRemoteUserNameFocusLost

    private void hidMySQLRemotePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hidMySQLRemotePasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hidMySQLRemotePasswordActionPerformed

    private void txtMySQLUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMySQLUserNameFocusLost
        if (this.areValidCredentials(Boolean.TRUE)) {
            this.refreshPanel();
        }
    }//GEN-LAST:event_txtMySQLUserNameFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton btnSelectDirectory;
    private javax.swing.JComboBox cbxJCMSDatabaseName;
    private javax.swing.JPasswordField hidMySQLPassword;
    private javax.swing.JPasswordField hidMySQLRemotePassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentMySQLDatabases;
    private javax.swing.JButton nextButton;
    private javax.swing.JTextArea txtCurrentMySQLDatabases;
    private javax.swing.JTextField txtJCMSWebInstallationDirectory;
    private javax.swing.JLabel txtMessage;
    private javax.swing.JTextField txtMySQLPort;
    private javax.swing.JTextField txtMySQLRemoteUserName;
    private javax.swing.JTextField txtMySQLServerName;
    private javax.swing.JTextField txtMySQLUserName;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the inputDTO
     */
    public InputDTO getInputDTO() {
        InputDTO dto = new InputDTO();
        dto.setDatabaseName(this.cbxJCMSDatabaseName.getSelectedItem().toString().trim());
        dto.setMySQLPort(this.txtMySQLPort.getText().trim());
        dto.setMySQLServerName(this.txtMySQLServerName.getText().trim());
        dto.setMySQLUserName(this.txtMySQLUserName.getText().trim());
        dto.setMySQLPassword(this.getPassword());
        dto.setWebInstallationDirectory(this.txtJCMSWebInstallationDirectory.getText().trim());

        if (this.txtMySQLServerName.getText().trim().equalsIgnoreCase("localhost")) {
            // Use localhost credentials
            dto.setMySQLRemoteUserName(this.txtMySQLUserName.getText().trim());
            dto.setMySQLRemotePassword(this.getPassword());
        } else {
            // User remote credentials
            dto.setMySQLRemoteUserName(this.txtMySQLRemoteUserName.getText().trim());
            dto.setMySQLRemotePassword(this.getRemotePassword());
        }
        
        return dto;
    }

    private String removeAllWhitespace(String term) {
        while (term.contains(" ")) {
            term = term.replace(" ", "");
        }
        return term;
    }
}
