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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;

/**
 *
 * @author cnh
 */
public class DeployJCMSWeb {
    private LogIt logIt = null;
    private Utils utils = new Utils();
    private PropertiesDTO propertiesDTO = new PropertiesDTO();
    
    public DeployJCMSWeb() {
        logIt = new LogIt();
    }

    public String backupJCMSWeb(String installDirectory) throws JCMSException {
        String msg = "";
        try {
            // Make sure JCMS Web orginal does not exist
            this.deleteJCMSWebOriginal(installDirectory + "_original");
            if ( ( new File(installDirectory)).exists() ) {
                File jcmsWebDir = new File(installDirectory);
                if (jcmsWebDir.isDirectory()) {
                    utils.copyFolder(jcmsWebDir, new File(installDirectory + "_original"));
                    msg += "JCMS Web Installation Archive: "+ installDirectory + "_original";
                }
            }
        } catch (IOException ioe) {
            throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nFailed to backup JCMS Web directory. "));
        }
        
        return msg;
    }
    
    public String rollbackJCMSWeb(String installDirectory) throws JCMSException {
        String msg = "";
        File jcmsWebDirOld = new File(installDirectory + "_original");
        File jcmsWebDirNew = new File(installDirectory);
        
        if (jcmsWebDirOld.exists() && jcmsWebDirOld.isDirectory()) {
            if (jcmsWebDirNew.exists() && jcmsWebDirNew.isDirectory()) {
                try {
                    utils.delete(jcmsWebDirNew);
                    utils.copyFolder(jcmsWebDirOld, jcmsWebDirNew);
                    utils.delete(jcmsWebDirOld);
                    msg += "\nOriginal JCMS Web installation restored to " + jcmsWebDirNew;
                } catch (IOException ioe) {
                    throw new JCMSException(logIt.logMessage(Severity.ERROR, "Failed to restore JCMS Web installation.  "
                            + "Original JCMS Web backup name is here at "+ jcmsWebDirOld));
                }
                
            }
        }
        
        return msg;
    }
    
    public void deleteJCMSWebOriginal(String installDirectory) {
        File jcmsWebDirRestore = new File(installDirectory + "_original");
        
        if (jcmsWebDirRestore.exists() && jcmsWebDirRestore.isDirectory()) {
            try {
                utils.delete(jcmsWebDirRestore);
            } catch (IOException ioe) {
                
            }
        }
    }
    
    /**
     * As part of the update strategy, make sure the old MySQL Connector drivers 
     * are removed before adding new drivers, otherwise two different drivers  
     * are installed instead of one.
     * @param installDirectory 
     */
    public String deleteMySQLConnectors(String installDirectory) throws JCMSException {
        String libDir = installDirectory + File.separator + "jcmsJboss" + File.separator + "server" + File.separator + "default" + File.separator + "lib" ;
        File dir = new File(libDir);
        ArrayList<String> files ;

        if (dir.exists() && dir.isDirectory()) {
            try {
                files = DeployJCMSWeb.listFilesMatching(dir, "^(mysql-conn).");
                for (String file : files) {
                    utils.delete(new File(file));
                }
            } catch (Exception ioe) {
                System.out.println("Matches exception " +ioe);
            }
        }
        return "";
    }
    
    private static ArrayList<String> listFilesMatching(File root, String regex) throws Exception {
        ArrayList files = new ArrayList();
        if(!root.isDirectory()) {
            throw new IllegalArgumentException(root+" is no directory.");
        }

        File[] list = root.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isFile()) {
                    Pattern pattern = Pattern.compile("mysql-conn", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(f.getName());
                    if (matcher.lookingAt()) {
                        files.add(f.getAbsolutePath());
                    }
                };
            }
//            final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
//            matcher = p.matcher(regex);
//            System.out.println("matches(): " + matcher.matches());
        }
        
        return files;
    }    
    
    public String updateDataSourceFile(String installDirectory, String connectionURL) throws JCMSException {
        String msg = "";
        String dsFileName = installDirectory + File.separator + "jcmsJboss" + File.separator + "server" + File.separator + "default" + File.separator + "deploy" + File.separator + "mysql-ds.xml";
        String originalFileName = dsFileName.replace(".xml", "_original.xml");
        
        if ( ( new File(dsFileName)).exists() ) {
            File srcFile = new File(dsFileName);
            File dstFile = new File(originalFileName);
            
            if (dstFile.exists())
                dstFile.delete();
            
            Boolean rename = srcFile.renameTo(dstFile);
            
            if (!rename)
                throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nFailed to rename JBOSS data source file to "+ originalFileName));
                
            // Read original, Write dsfile
            BufferedReader BR = null;
            PrintWriter PW = null;
            
            try {
                FileInputStream FIS = null;
                try {
                    FIS = new FileInputStream(originalFileName);
                } catch (FileNotFoundException ex) {
                    throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nOriginal JBOSS data source file not found, "+ originalFileName));
                }
                DataInputStream in = new DataInputStream(FIS);
                BR = new BufferedReader(new InputStreamReader(in));
                
                // Setting up output stream to new datasource file
                File dsFile = new File(dsFileName);
                FileOutputStream FOS = new FileOutputStream(dsFile);
                PW = new PrintWriter(FOS);

                String strLine;
                //Read File Line By Line
                while ((strLine = BR.readLine()) != null) {
                    if (utils.isConnectionURL(strLine)) {
                        // write this new connection url
                        PW.write(connectionURL);
                    }
                    else {
                        // copy the original line
                        PW.write(strLine);
                    }
                    PW.println();
                }
            } catch (IOException ex) {
                throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nIO Exception  \nException detail:\n"+ ex.getMessage()));
            } finally {                       // always close the file
                try {
                    if (BR != null) {
                        BR.close();
                    }
                    if (PW != null) {
                        PW.flush();
                        PW.close();
                    }
                } catch (IOException ioe2) {
                }
            }
            
            System.out.println("File renamed?" + rename);
        }
        
        return msg;
    }
    
    public String deploy(String installDirectory) throws JCMSException {
        String source = "";
        String destination = "";
        String msg = "";
        source = utils.getInstallerDirectory() + File.separator + "jcmslib" + File.separator + "jcmsJboss.jar";
        destination = installDirectory.trim();
        System.out.println(source);
        System.out.println(destination);

        try {
            // test source and destination
            if ( (new File(source)).exists() ) {
            } else {
                throw new JCMSException(logIt.logMessage(Severity.ERROR, "\nUnable to find the deployment archive "+ source));
            }

            if (! (new File(destination)).isDirectory() ) {
                // Create it
                try {
                    new File(destination).mkdir();
                } catch (Exception e) {
                    throw new JCMSException(logIt.logMessage(Severity.ERROR, "\nFailed to create install directory "+ destination));
                }
            }
        } catch (PatternSyntaxException pse) {
            throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nProblem parsing source file.  \nException detail:\n"+ pse.getMessage()));
        }

        /*
         * if jcmsweb.cmd file exsits prompt user.
         */
        Boolean foundJCMSWebApp = false;
        if ( ( new File(destination + File.separator + "jcmsweb.cmd" )).exists() ){
            foundJCMSWebApp = true;
            try {
                // Open file and read first two lines ...
                // command line parameter
                FileInputStream fstream = null;
                try {
                    fstream = new FileInputStream(destination + File.separator + "jcmsweb.cmd");
                } catch (FileNotFoundException ex) {
                    throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nFile Not Found Exception  \nException detail:\n"+ ex.getMessage()));
                }
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                int twoLines = 0;
//                ArrayList<String> myUrls = new ArrayList<String>();
                String installedVersion = "";
                String installedDate = "";
                //Read File Line By Line
                while ((strLine = br.readLine()) != null) {
                    if (0 == twoLines && strLine.trim().length() > 0) {
                        installedVersion = strLine.substring(strLine.indexOf(":"));
                        twoLines++;
                    } else if (1 == twoLines && strLine.trim().length() > 0) {
                        installedDate = strLine.substring(strLine.indexOf(":"));
                        break;
                    }
                }
                br.close();
                
                int i = JOptionPane.showConfirmDialog(null, "It appears that JCMS Web has already been installed."
                        + "\nVersion:" + installedVersion 
                        + "\nInstalled On:" + installedDate 
                        + "\n\nDo you wish to continue?");
                // i = 2 = Cancel
                // i = 1 = No
                if ( i == 1 || i == 2) {
                    throw new JCMSException(logIt.logMessage(Severity.INFO, msg + "\nJCMS Web installation cancelled at your request!"));
                }
                // i = 0 = Yes
                msg += logIt.logMessage(Severity.WARN, "\nOverwriting previous JCMS Web version ...");

            } catch (IOException ex) {
                throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nIO Exception  \nException detail:\n"+ ex.getMessage()));
            }
        }
        
        msg += deployArchive(source,destination);
        
        try {
            // Deploy JCMS Application code
            String deployDestination = destination + File.separator + "jcmsJboss" + File.separator + "server"
                                     + File.separator + "default" + File.separator + "deploy";
            utils.copyFolder(new File(utils.getInstallerDirectory() + File.separator + "jcmslib" + File.separator + "JCMSIntegrationTier.jar"), new File(deployDestination + File.separator + "JCMSIntegrationTier.jar"));
            utils.copyFolder(new File(utils.getInstallerDirectory() + File.separator + "jcmslib" + File.separator + "JCMSMiddleTier.jar"), new File(deployDestination + File.separator + "JCMSMiddleTier.jar"));
            utils.copyFolder(new File(utils.getInstallerDirectory() + File.separator + "jcmslib" + File.separator + "JCMSWebTier.war"), new File(deployDestination + File.separator + "JCMSWebTier.war"));
            utils.copyFolder(new File(utils.getInstallerDirectory() + File.separator + "Docs"), new File(destination));
        } catch (IOException ioe) {
            throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nFailed to copy JCMS application code to installation directory.  \nException detail:\n"+ ioe.getMessage()));
        }

        // Write out startup command file ...
        if (foundJCMSWebApp) {
            boolean success = (new File(destination + File.separator + "jcmsweb.cmd")).delete();
            if (!success) {
                // Deletion failed
               msg += logIt.logMessage(Severity.WARN, "\nUnable to create startup script ...");
            }
        }

        // REM jcmsweb.cmd
        // REM JCMS Web Version x.x.x
        // REM Install Date xx/yy/zzzz

        // REM Start jboss for the JCMS Web app
        // cmd /c start "JCMS WEb" /min run.bat
        String startFile = destination + File.separator + "jcmsweb.cmd";
        String macStartFile = destination + File.separator + "jcmsWeb.command";
//        String linuxStartFile = destination + File.separator + "jcmsweb.sh";
        
        File macFile = new File(macStartFile);
//        File linuxFile = new File(linuxStartFile);
        
        BufferedWriter bwStart = null;
        PrintWriter pwMac = null;
//        PrintWriter pwLinux = null;

        try {
            FileOutputStream fosMac = new FileOutputStream(macFile);
//            FileOutputStream fosLinux = new FileOutputStream(macFile);
            pwMac = new PrintWriter(fosMac);
//            pwLinux = new PrintWriter(fosLinux);

            String jcmswebVersionNumber = propertiesDTO.getJCMSWebReleaseNumber();
            bwStart = new BufferedWriter(new FileWriter(startFile, true));

            bwStart.write("REM JCMS Web Version : " + jcmswebVersionNumber.trim());
            pwMac.println("ECHO JCMS Web Version : " + jcmswebVersionNumber.trim());
//            pwLinux.println("ECHO JCMS Web Version : " + jcmswebVersionNumber.trim());
            
            bwStart.newLine();
            // create a java calendar instance
            Calendar calendar = Calendar.getInstance();
            // get a java.util.Date from the calendar instance.
            // this date will represent the current instant, or "now".
            java.util.Date now = calendar.getTime();
            // a java current time (now) instance
            java.sql.Timestamp installDate = new java.sql.Timestamp(now.getTime());
            
            bwStart.write("REM Install Date : " + installDate.toString());
            pwMac.println("ECHO Install Date : " + installDate.toString());
//            pwLinux.println("ECHO Install Date : " + installDate.toString());
            
            bwStart.newLine();
            pwMac.println();
//            pwLinux.println();
            
            bwStart.write("REM Start JBOSS for the JCMS Web app");
            pwMac.println("ECHO Start JBOSS for the JCMS Web app for Mac");
//            pwLinux.println("ECHO Start JBOSS for the JCMS Web app for Mac");
            
            bwStart.newLine();
            bwStart.write("cmd /c start \"JCMS Web\" /min \"" + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "run.bat\" -b 0.0.0.0");

            pwMac.println("chmod 544 " + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "run.sh");
//            pwLinux.println("chmod 544 " + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "run.sh");
            pwMac.println("chmod 544 " + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "shutdown.sh");
//            pwLinux.println("chmod 544 " + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "shutdown.sh");
            pwMac.println("\"" + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "run.sh\" -b 0.0.0.0");                    
//            pwLinux.println("\"" + destination.trim() + File.separator + "jcmsJboss" + File.separator + "bin" + File.separator + "run.sh\" -b 0.0.0.0");                    
            
            bwStart.newLine();
            bwStart.flush();
            pwMac.flush();
        } catch (IOException ioe) {
            msg += logIt.logMessage(Severity.WARN, "\nCannot create startup file.  You will have to start JBOSS manually.  \nException detail:\n"+ ioe.getMessage());
        } finally {                       // always close the file
            try {
                if (bwStart != null) {
                    bwStart.close();
                }
                if (pwMac != null) {
                    pwMac.close();
                    macFile.setExecutable(true);
                }
//                if (pwLinux != null) {
//                    pwLinux.close();
//                    linuxFile.setExecutable(true);
//                }
            } catch (IOException ioe2) {
            }
        }

        return msg;
    }
        
    public String deployArchive(String deployPackage, String deployDestination) throws JCMSException {
        String msg = "";
        String installDirectory = deployDestination.trim();
        UnZipIt uz = new UnZipIt();
        uz.unzip(deployPackage, deployDestination);

        msg += logIt.logMessage(Severity.INFO, "\nInstalling to:  "+ deployDestination);

        String confFile = installDirectory + File.separator 
                        + "jcmsJboss" + File.separator 
                        + "bin" + File.separator 
                        + "run.conf";
        System.out.println(confFile);
        String confJBossHome = installDirectory + File.separator + "jcmsJboss";
        System.out.println(confJBossHome);

        // Append path to run.conf and run.conf.bat
        BufferedWriter bwRun_conf = null;

        try {
            bwRun_conf = new BufferedWriter(new FileWriter(confFile.trim(), true));
            bwRun_conf.write("export JBOSS_HOME=" + confJBossHome);
            bwRun_conf.newLine();
            bwRun_conf.flush();
        } catch (IOException ioe) {
            throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nInvalid run file, "+ confFile +"!  JCMS Web archive may have been corrupted.  \nException detail:\n"+ ioe.getMessage()));        
        } finally {                       // always close the file
            if (bwRun_conf != null) try {
                bwRun_conf.close();
            } catch (IOException ioe2) {
            }
        } // end try/catch/finally


        BufferedWriter bwRun_conf_bat = null;
        try {
            bwRun_conf_bat = new BufferedWriter(new FileWriter(confFile.trim() + ".bat", true));
            bwRun_conf_bat.write("set JBOSS_HOME=" + confJBossHome );
            bwRun_conf_bat.newLine();
            bwRun_conf_bat.flush();
        } catch (IOException ioe) {
            throw new JCMSException(logIt.logMessage(Severity.ERROR, msg + "\nInvalid run file, "+ confFile +"!  JCMS Web archive may have been corrupted.  \nException detail:\n"+ ioe.getMessage()));        
        } finally {                       // always close the file
            if (bwRun_conf_bat != null) try {
                bwRun_conf_bat.close();
            } catch (IOException ioe2) {
            }
        } // end try/catch/finally

        return msg;
    }
    
    /**
     * @return the logIt
     */
    public LogIt getLogIt() {
        return logIt;
    }

    /**
     * @param logIt the logIt to set
     */
    public void setLogIt(LogIt logIt) {
        this.logIt = logIt;
    }
    
}
