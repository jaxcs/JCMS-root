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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cnh
 */
public class Utils {
    private static final String WINDOWS_DESKTOP = "Desktop";
    PropertiesDTO propertiesDTO = new PropertiesDTO();
    
    public Utils() {
    }
    
    public String getCurrentTimestamp() {
        return this.getCurrentTimestamp(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
    }
    
    public String buildMySQLConnectionURL(String hostname, String port, String databaseName) {
        String url = "<connection-url>jdbc:mysql://"+ hostname +":"+ port +"/"+ databaseName +"</connection-url>";
        return url;
    }
    
    public Boolean isConnectionURL(String strLine) {
        return strLine.contains("<connection-url>");
    }
     
    public static String getWindowsCurrentUserDesktopPath() { //return the current user desktop path
        System.out.println("Windows user home is: "+ System.getenv("userprofile"));
        return System.getenv("userprofile") + File.separator + WINDOWS_DESKTOP ;
    }
    
    public static String getMACCurrentUserDesktopPath() { //return the current user desktop path
        System.out.println("MAC user home is: "+ System.getProperty("user.home"));
        return System.getProperty("user.home") + File.separator + "Desktop" ;
    }
    
    public void copyFolder(File src, File dest) throws IOException {
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
                   if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println("Directory copied from "+ src + " to " + dest);
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	} else {
                // Create directories if not exist
                new File(dest.getParent()).mkdirs();

    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
 
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        //copy the file content in bytes 
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
                if (propertiesDTO.getJCMSDebugConsole())
       	            System.out.println("File copied from " + src + " to " + dest);
    	}
    }
    
    public String delete(File file) throws IOException {
        String msg = "";
        
    	if(file.isDirectory()){
    		//directory is empty, then delete it
    		if(file.list().length==0){
    		   file.delete();
//                   msg += "Directory is deleted : "+ file.getAbsolutePath();
                   if (propertiesDTO.getJCMSDebugConsole())
                       System.out.println("Directory is deleted : "+ file.getAbsolutePath());
    		} else {
    		   //list all the directory contents
        	   String files[] = file.list();
 
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
        	      //recursive delete
        	     delete(fileDelete);
        	   }
 
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
                     if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println("Directory is deleted : " + file.getAbsolutePath());
        	   }
    		}
    	} else {
    		//if file, then delete it
    		file.delete();
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
        return msg;
    }
    
    public void removeWarningMessageFromDumpFile(String src, String dest) throws IOException {
        File srcFile = new File(src);
        File destFile = new File(dest);
        int lineCount = 0;
    	if(!srcFile.isDirectory()){
            //if file, then copy it removing first couple lines which should be commented 
            //if first two lines are commented properly than cancel, do nothing
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(srcFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            OutputStream out = new FileOutputStream(destFile); 
            String strLine ;

            //copy the file content in bytes 
            while ((strLine = br.readLine()) != null) {
                lineCount++;
                if (lineCount == 1) {
                    // If the first line of the dump file is not properly 
                    // commented continue else break and cancel - file is fine
                    if (strLine.length() >= 3) { 
                        System.out.println(strLine.substring(0,3));
                        if (strLine.substring(0,3).equalsIgnoreCase("-- ")) {
                            break;
                        }
                    }
                } else {
                    if (lineCount > 2) {
                        // Do not include the first two lines of a MySQL dump file
                        // when run on MAC MySQL v5.6.  The lines are replaced by 
                        // the Mysql warning you should not use a password on the command line.
                        out.write(strLine.getBytes("UTF-8"), 0, strLine.length());
                        out.write("\n".getBytes());
                    }
                }
            }

            in.close();
            out.close();
            if (lineCount > 5) {
                // Definitely had to fix the mysql dump file
                // Drop the old and rename the temp file to the original backup file name
                // Save copy of original dump file, just in case.
                this.copyFolder(new File(src), new File(src + ".original"));
                this.delete(srcFile);
                try {
                    this.copyFolder(new File(dest), new File(src));
                    System.out.println("Dump file rename successful.");
                } catch (NullPointerException npe) {
                    System.out.println("Dump file rename failed.");
                    throw new IOException(npe);
                } 
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println("First two lines of dump file fixed: " + src);
            }
    	}
    }
    
    public String join(String[] split, String string) {
        StringBuffer s = new StringBuffer();
        boolean isNotFirst = false;
        for (String str : split) {
            if (isNotFirst) {
                s.append(string);
            } else {
                isNotFirst = true;
            }
            s.append(str);
        }
        return s.toString();
    }    
    
    public String getCurrentTimestamp(Boolean substitueSpaceWithUnderscore, Boolean removeColons, Boolean removeDashes) {
        String strTimeStamp = "";
        
        // create a java calendar instance
        Calendar calendar = Calendar.getInstance();

        // get a java.util.Date from the calendar instance.
        // this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();

        // a java current time (now) instance
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        strTimeStamp = currentTimestamp.toString().substring(0, currentTimestamp.toString().indexOf("."));
        if (substitueSpaceWithUnderscore) { strTimeStamp = strTimeStamp.replace(" ", "_"); }
        if (removeColons) { strTimeStamp = strTimeStamp.replace("-", ""); }
        if (removeDashes) { strTimeStamp = strTimeStamp.replace(":", ""); }
        
        return strTimeStamp;
    }

    private String getOSName() {
        String osName = System.getProperty("os.name" );
        System.out.println("Operting System:  "+ osName);
        return osName;
    }
    
    public ResultDTO getJAVAVersion() {
        String javaVersion = "";
        ResultDTO resultDTO = new ResultDTO();
        try {
            javaVersion = System.getProperty ("java.specification.version");
            
            if (javaVersion == null) {
                resultDTO.setBlnSuccess(Boolean.FALSE);
            }
            
            System.out.println(javaVersion);
//            int pos = javaRunTimeVersion.indexOf(".");
//
//            Double jrtWhole = Double.parseDouble(javaRunTimeVersion.substring(0,pos));
//            String subString = javaRunTimeVersion.substring(pos + 1,javaRunTimeVersion.length());
//            Double jrtPart = Double.parseDouble("0." + subString.substring(0, javaRunTimeVersion.indexOf(".")));

            Double javaVersionDouble = Double.parseDouble(javaVersion);
            if ( 1.6 <= javaVersionDouble) {
                // INFO
                resultDTO.setStrMessage("The Java Runtime version is:" + javaVersion);
            } else {
                // WARN
                resultDTO.setStrMessage("The Java Runtime version is old,\nFound :" + javaVersion + "\nJava 1.6 or greater is recommended.");
            }
        } catch (Exception e) {
            resultDTO.setBlnSuccess(false);
            return resultDTO;
        }
        
        resultDTO.setStrJavaRuntimeVersion(javaVersion);
        return resultDTO;
    }
    
    public String getInstallerDirectory() {
        String jcmsWebInstallerDir = "";
        // Current Dir
        File dir1 = new File (".");
        try {
            jcmsWebInstallerDir = dir1.getCanonicalPath();
            System.out.println("Installer directory is " + jcmsWebInstallerDir);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return jcmsWebInstallerDir;
    }
    
    public static boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();        
        return (OS.indexOf("win") >= 0);
    }    
    public static boolean isMAC() {
        String OS = System.getProperty("os.name").toLowerCase();        
        return (OS.indexOf("mac") >= 0);
    }    
    public static boolean isLinux() {
        String OS = System.getProperty("os.name").toLowerCase();        
        return (OS.indexOf("nux") >= 0);
    }    
    public Boolean isOSWindows() {
        if (this.getOSName().contains("Windows"))
            return true;
        return false;
    }
    public Boolean isOSMAC() {
        String OS = System.getProperty("os.name").toLowerCase();        
        return (OS.indexOf("mac") >= 0);
    }    
    public Boolean isOSLinux() {
        String OS = System.getProperty("os.name").toLowerCase();        
        return (OS.indexOf("nux") >= 0);
    }    
}
