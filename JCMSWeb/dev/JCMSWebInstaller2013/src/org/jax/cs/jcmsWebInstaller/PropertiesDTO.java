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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author cnh
 */
public class PropertiesDTO {
    Properties jcmsViewProperties = null;
    
    public PropertiesDTO() {
        loadJCMSViewProperties();
    }
    
    // Read properties file.
    private void loadJCMSViewProperties() {
        jcmsViewProperties = new Properties();

        try {
            jcmsViewProperties = new Properties();
            InputStream in = getClass().getResourceAsStream("resources/JCMSWebInstaller.properties");
            jcmsViewProperties.load(in);
            in.close();
        } catch (IOException e) {
        }
        this.jcmsViewProperties = jcmsViewProperties;
    }
    
    public String getJCMSWebReleaseNumber() {
        String key = "jcms.webReleaseNum.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
    
    public String getMySQLUserName() {
        String key = "mysql.username.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
    
    public String getMySQLPd() {
        String key = "mysql.password.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
    
    public String getMySQLHostName() {
        String key = "mysql.hostname.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
    
    public String getMySQLPort() {
        String key = "mysql.port.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
    
    public String getJCMSDatabaseName() {
        String key = "jcms.databasename.text";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else;
            return "";
    }
        
    public Boolean getJCMSDebugConsole() {
        String key = "jcms.debug.console";
        if (jcmsViewProperties.containsKey(key)) 
            return (jcmsViewProperties.getProperty(key).equalsIgnoreCase("true") ? true: false);
        else
            return false;
    }

    /**
     * URLS
     * @return 
     */
    
    public String getJCMSCommunityForumsURL() {
        String key = "jcms.url.communityforums";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
   
    public String getJCMSWebForumURL() {
        String key = "jcms.url.jcmswebforum";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
   
    public String getJCMSWebFAQsURL() {
        String key = "jcms.url.faqs";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
   
    public String getJCMSWebUserTipsURL() {
        String key = "jcms.url.usertips";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
   
    public String getJCMSWebKnownIssuesURL() {
        String key = "jcms.url.knownissues";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSProductsURL() {
        String key = "jcms.url.products";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSHomeURL() {
        String key = "jcms.url.home";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSSupportURL() {
        String key = "jcms.url.support";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSTutorialsURL() {
        String key = "jcms.url.tutorials";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    // JAVA URLs
    public String getMySQLDownloadsURL() {
        String key = "mysql.url.downloads";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    
    //# MySQL URLs
    
    public String getJAVADownloadsURL() {
        String key = "java.url.download";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getMySQLInstallWindowsURL() {
        String key = "mysql.url.install.windows";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getMySQLInstallMacURL() {
        String key = "mysql.url.install.mac";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getMySQLInstallLinuxURL() {
        String key = "mysql.url.install.linux";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getMySQLAddWindowsToPathURL() {
        String key = "mysql.url.install.addwindowstopath";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSInstallationGuideURL() {
        String key = "jcms.url.installationguide";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
    
    public String getJCMSWebUsersGuideURL() {
        String key = "jcms.url.userguide";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
    
    public String getJCMSQuickStartInstructions(String jcmsWebInstallDirectory) {
        return "file:///"+ jcmsWebInstallDirectory + File.separator + "JCMSWebQuickStartInstructions.pdf";
    }
    
    public void copyJCMSQuickStartInstructionsToDesktop(String jcmsWebInstallDirectory) {
        Utils utils = new Utils();
        String filename = "JCMSWebQuickStartInstructions.pdf";
        try {
            if (Utils.isWindows()) {
                utils.copyFolder(new File(jcmsWebInstallDirectory + File.separator + filename), 
                                 new File(Utils.getWindowsCurrentUserDesktopPath() + File.separator + filename));
            } else if (Utils.isMAC()) {
                utils.copyFolder(new File(jcmsWebInstallDirectory + File.separator + filename), 
                                 new File(Utils.getMACCurrentUserDesktopPath() + File.separator + filename));
            }
        } catch (IOException ioe) {
            
        } 
            
    }
    
    public String getJCMSWebInstallerVersion() {
        String key = "Application.version";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSWebInstallerDescription() {
        String key = "Application.description";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }

    public String getJCMSWebInstallerVendor() {
        String key = "Application.vendor";
        if (jcmsViewProperties.containsKey(key)) 
            return jcmsViewProperties.getProperty(key);
        else
            return "";
    }
}
