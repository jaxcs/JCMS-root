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

import java.io.*;
 
public class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    String outputFileName;
    PropertiesDTO propertiesDTO = null;

    StreamGobbler(InputStream is, String type) {
        this(is, type, null);
    }

    StreamGobbler(InputStream is, String type, String outputFileName) {
        this.is = is;
        this.type = type;
        this.outputFileName = outputFileName;
        this.inititialize();
    }
    
    private void inititialize() {
        propertiesDTO = new PropertiesDTO();
    }
    
    /** creates readers to handle the text created by the external program
      */     
    public void run() {
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        Boolean append = false;

        try {
            if (outputFileName != null) {
                File file = new File(outputFileName);
                System.out.println("Log file is: "+ outputFileName);
                System.out.println("File exists: "+ file.exists());
                append = (file.exists() && outputFileName.contains("create"));
                osw = new OutputStreamWriter(new FileOutputStream(file, append));
                bw = new BufferedWriter(osw);
            }
            
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line=null;
            while ( (line = br.readLine()) != null) {
                if (osw != null) {
                    bw.write(line);
                    bw.newLine();
                }
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println(type + ">" + line);   
            }
            if (osw != null) {
                bw.close();
                osw.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(); 
        }
    }
    
}
