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

import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author cnh
 */
public class JCMSPanel extends JPanel {
    private JDesktopPane desktopPane = null;
    private JFrame frame = null;
    private JCMSView jcmsView = null;
    private JCMSPanel previousPanel = null;
    private PropertiesDTO propertiesDTO = new PropertiesDTO();
    
    public void JCMSPanel() {
        this.setLocation(20, 20);
    }
    
    public void JCMSPanel(JDesktopPane desktopPane, JFrame frame) {
        this.setLocation(20, 20);
        this.setDesktopPane(desktopPane);
        this.setFrame(frame);
    }

    public void initialize() {
        System.out.println("Panel class name is "+ this.getClass().getName());
        this.getFrame().setTitle("JCMS Web Installer - "+ (((JCMSWebInstallerApp)this.getFrame()).getIsNewInstallation() ? "Install" : "Upgrade" ));
    }
    
    protected void nextPanel(JCMSPanel currentPanel, JCMSPanel nextPanel, JCMSView jcmsView) {
        currentPanel.setVisible(false);

        nextPanel.setJcmsView(jcmsView);
        nextPanel.setBounds(20, 20, 600, 500);
        nextPanel.setVisible(true);
        nextPanel.setPreviousPanel(currentPanel);
        nextPanel.initialize();
        
        this.getDesktopPane().setVisible(true);
        this.getFrame().setVisible(true);
    }

    protected void previousPanel(JCMSPanel currentPanel, JCMSView jcmsView) {
        currentPanel.setVisible(false);

        getPreviousPanel().setJcmsView(jcmsView);
        getPreviousPanel().setBounds(20, 20, 600, 500);
        getPreviousPanel().setVisible(true);
        
        this.getDesktopPane().setVisible(true);
        this.getFrame().setVisible(true);
    }

    protected static final JScrollPane getScrollPane(JComponent component) {
         Container p = component .getParent();
         if (p instanceof JViewport) {
                Container gp = p.getParent();
                if (gp instanceof JScrollPane) {
                    return (JScrollPane)gp;
                }
         }
         return null;
    }

    /**
     * @return the desktopPane
     */
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    /**
     * @param desktopPane the desktopPane to set
     */
    public void setDesktopPane(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    /**
     * @return the frame
     */
    public JFrame getFrame() {
        // return ((JCMSWebInstallerApp)this.getFrame());
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * @return the jcmsView
     */
    public JCMSView getJcmsView() {
        return jcmsView;
    }

    /**
     * @param jcmsView the jcmsView to set
     */
    public void setJcmsView(JCMSView jcmsView) {
        this.jcmsView = jcmsView;
    }

    /**
     * @return the previousPanel
     */
    public JCMSPanel getPreviousPanel() {
        return previousPanel;
    }

    /**
     * @param previousPanel the previousPanel to set
     */
    public void setPreviousPanel(JCMSPanel previousPanel) {
        this.previousPanel = previousPanel;
    }

    /**
     * @return the propertiesDTO
     */
    public PropertiesDTO getPropertiesDTO() {
        return propertiesDTO;
    }

}
