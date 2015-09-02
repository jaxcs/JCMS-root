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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 *
 * @author cnh
 */
public class LogIt {
    private Boolean fileLoggingEnabled = true;
    private String loggerFileName = "";
    private OutputStreamWriter osw = null;
    private BufferedWriter bw = null;
    private StringBuffer sb = new StringBuffer();

    public LogIt() {
        setLoggerFileName("jcmswebinstaller_"+ new Utils().getCurrentTimestamp(true, true, true) +".log");
    }
    
    /**
     *
     * @param severity
     * @param message
     */
    public String logMessage(Severity severity, String message) {
        StringBuilder loggerView = new StringBuilder();
        
        // create a java calendar instance
        Calendar calendar = Calendar.getInstance();

        // get a java.util.Date from the calendar instance.
        // this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();

        // a java current time (now) instance
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (Severity.INFO == severity) {
            loggerView.append("\n"+ currentTimestamp.toString() + "\t" + "INFO" + "\t" + message + "\n");
        } else if (Severity.WARN == severity) {
            loggerView.append("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            loggerView.append(currentTimestamp.toString() + "\t" + "WARN" + "\t" + message + "\n");
            loggerView.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        } else if (Severity.ERROR == severity) {
            loggerView.append("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            loggerView.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            loggerView.append(currentTimestamp.toString() + "\t" + "ERROR" + "\t" + message + "\n");
            loggerView.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
            loggerView.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        } else if (Severity.SUCCESS == severity) {
            loggerView.append("\n********************************************************\n");
            loggerView.append(currentTimestamp.toString() + "\t" + "SUCCESS" + "\t" + message + "\n");
            loggerView.append("********************************************************\n");
        } else {
            // Very Bad Programming
            loggerView.append("\n XXXXXXX  ERROR NEW SEVERITY INTRODUCED  XXXXXXXXXX" + "\n");
        }

        System.out.println(loggerView.toString());
        sb.append(loggerView.toString());
        
        return loggerView.toString();
    }
    
    public void logToFile() {
        if (this.getFileLoggingEnabled()) {
            this.openLogger();
            // stream to server log file            
            try {
                bw.write(sb.toString());
                bw.newLine();
            } catch (IOException ioe) {

            } finally {
                this.closeLogger();
            }
        }
    }

    private void openLogger() {
        try {
            File file = new File(this.getLoggerFileName());
            osw = new OutputStreamWriter(new FileOutputStream(file));
            bw = new BufferedWriter(osw);
        } catch (FileNotFoundException fnfe) {
            
        }
    }
    
    private void closeLogger() {
        try {
            if (bw != null) 
                bw.close();
            if (osw != null)
                osw.close();
        } catch (IOException e) {
            
        }
    }
    
    
    /**
     * @return the fileLoggingEnabled
     */
    public Boolean getFileLoggingEnabled() {
        return fileLoggingEnabled;
    }

    /**
     * @param fileLoggingEnabled the fileLoggingEnabled to set
     */
    public void setFileLoggingEnabled(Boolean fileLoggingEnabled) {
        this.fileLoggingEnabled = fileLoggingEnabled;
    }

    /**
     * @return the loggerFileName
     */
    public String getLoggerFileName() {
        return loggerFileName;
    }

    /**
     * @param loggerFileName the loggerFileName to set
     */
    public void setLoggerFileName(String loggerFileName) {
        this.loggerFileName = loggerFileName;
    }
    
}
