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

/**
 *
 * @author cnh
 */
public class ProcessExec {

    public void runCommand(String strCommand) {
        runCommand(strCommand, null);
    }

    public int runCommand(String strCommand, String outputFileName) {
        String osName = System.getProperty("os.name" );
        System.out.println("Operting System:  "+ osName);
        String[] cmd = new String[3];
        if( osName.equals( "Windows 95" ) ) {
            cmd[0] = "command.com" ;
            cmd[1] = "/C" ;
            cmd[2] = strCommand;
        } else if( osName.contains( "Windows" )) {
            System.out.println("ProcessExec - IS WINDOWS: " + osName);
            cmd[0] = "cmd.exe" ;
            cmd[1] = "/C" ;
            cmd[2] = strCommand;
        } else {
            cmd[0] = "/bin/sh" ;
            cmd[1] = "-c" ;
            cmd[2] = strCommand ;
        }
        
        System.out.println("Executing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
        return this.runApplication(cmd, outputFileName);
    }
    
    private int runApplication(String[] strCommandLine, String outputFileName) {
        Utils utils = new Utils();
        int exitVal = 0;
        try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(strCommandLine);
            
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", outputFileName);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR", outputFileName);            

            // kick them off
            outputGobbler.start();
            errorGobbler.start();
            
            // any error???
            if (!strCommandLine[2].toString().contains("jcmsWeb.command")) {
                exitVal = proc.waitFor();
                System.out.println("ExitValue: " + exitVal);    
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } 
        return exitVal;
    }

}
