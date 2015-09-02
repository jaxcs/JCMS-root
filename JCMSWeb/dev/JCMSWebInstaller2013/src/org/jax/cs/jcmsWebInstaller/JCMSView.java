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

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

/**
 *
 * @author cnh
 */
public class JCMSView {
    private JCMSPanel introductionPanel = null;
    private JCMSPanel userAgreementPanel = null;
    private JCMSPanel installationTypePanel = null;
    private JCMSPanel installMySQLPanel = null;
    private JCMSPanel configureMySQLPanel = null;
    private JCMSPanel databaseSetupPanel = null;
    private JCMSPanel installingJCMSWebPanel = null;
    private JCMSPanel installationCompletePanel = null;
    private JCMSPanel installationFailedPanel = null;
    private JCMSPanel resourcePanel = null;

    public JCMSView(JDesktopPane desktopPane, JFrame frame) {
        introductionPanel = new IntroductionPanel(desktopPane, frame);
        userAgreementPanel = new UserAgreementPanel(desktopPane, frame);
        installationTypePanel = new InstallationTypePanel(desktopPane, frame);
        installMySQLPanel = new InstallMySQLPanel(desktopPane, frame);
        configureMySQLPanel = new ConfigureMySQLPanel(desktopPane, frame);
        databaseSetupPanel = new DatabaseSetupPanel(desktopPane, frame);
        installingJCMSWebPanel = new InstallingJCMSWebPanel(desktopPane, frame);
        installationCompletePanel = new InstallationCompletePanel(desktopPane, frame);
        installationFailedPanel = new InstallationFailedPanel(desktopPane, frame);
        resourcePanel = new JCMSResourcePanel(desktopPane, frame);
        
        desktopPane.add(introductionPanel);
        desktopPane.add(userAgreementPanel);
        desktopPane.add(installationTypePanel);
        desktopPane.add(installMySQLPanel);
        desktopPane.add(configureMySQLPanel);
        desktopPane.add(databaseSetupPanel);
        desktopPane.add(installingJCMSWebPanel);
        desktopPane.add(installationCompletePanel);
        desktopPane.add(installationFailedPanel);
        desktopPane.add(resourcePanel);
    }

    /**
     * @return the introductionPanel
     */
    public JCMSPanel getIntroductionPanel() {
        return introductionPanel;
    }

    /**
     * @return the installationTypePanel
     */
    public JCMSPanel getInstallationTypePanel() {
        return installationTypePanel;
    }

    /**
     * @return the installMySQLPanel
     */
    public JCMSPanel getInstallMySQLPanel() {
        return installMySQLPanel;
    }

    /**
     * @return the configureMySQLPanel
     */
    public JCMSPanel getConfigureMySQLPanel() {
        return configureMySQLPanel;
    }

    /**
     * @return the databaseSetupPanel
     */
    public JCMSPanel getDatabaseSetupPanel() {
        return databaseSetupPanel;
    }

    /**
     * @return the installDatabasePanel
     */
    public JCMSPanel getInstallingJCMSWebPanel() {
        return installingJCMSWebPanel;
    }

    /**
     * @return the installationCompletePanel
     */
    public JCMSPanel getInstallationCompletePanel() {
        return installationCompletePanel;
    }

    /**
     * @return the userAgreementPanel
     */
    public JCMSPanel getUserAgreementPanel() {
        return userAgreementPanel;
    }

    /**
     * @return the resourcePanel
     */
    public JCMSPanel getResourcePanel() {
        return resourcePanel;
    }

    /**
     * @return the installationFailedPanel
     */
    public JCMSPanel getInstallationFailedPanel() {
        return installationFailedPanel;
    }
    
}
