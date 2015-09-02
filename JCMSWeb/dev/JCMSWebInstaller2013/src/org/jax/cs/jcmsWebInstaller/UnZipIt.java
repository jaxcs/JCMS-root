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
import java.util.*;
import java.util.zip.*;

public class UnZipIt {

   final int BUFFER = 2048;

   public void unzip (String zipFileName, String destDir) {
       PropertiesDTO propertiesDTO = new PropertiesDTO();
      try {
         BufferedOutputStream destBOS = null;
         BufferedInputStream bis = null;
         ZipEntry entry;
         ZipFile zipfile = new ZipFile(zipFileName);
         Enumeration e = zipfile.entries();
         boolean firstEntry = true;
         while(e.hasMoreElements()) {
            entry = (ZipEntry) e.nextElement();
            if (firstEntry) {
                if ( 0 < entry.getName().indexOf("/")){
                    String zipDir = entry.getName().substring(0, entry.getName().indexOf("/"));
                    // Find the preceeding dir
                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println(entry.getName().substring(0, entry.getName().indexOf("/")));
                    // Create dir
                    if (new File(destDir.trim() + File.separator + zipDir.trim()).mkdirs()) {
                        if (propertiesDTO.getJCMSDebugConsole())
                            System.out.println ("Creating : " + destDir.trim() + File.separator + entry.getName() );
                    } else {
                        if (propertiesDTO.getJCMSDebugConsole())
                            System.out.println ("false dir : ");
                    }


                }
                firstEntry = false;
            }
            if ( entry.isDirectory() ) {
                // Create dir
                if (new File(destDir.trim() + File.separator + entry.getName()).mkdirs()) {
                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println ("Creating : " + destDir.trim() + File.separator + entry.getName() );
                } else {
                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println ("false dir : ");
                }
            } else {
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println("Extracting: " + entry);
                bis = new BufferedInputStream
                  (zipfile.getInputStream(entry));
                int count;
                byte data[] = new byte[BUFFER];
                FileOutputStream fos = new
                  FileOutputStream(destDir.trim() + File.separator + entry.getName());
                destBOS = new
                  BufferedOutputStream(fos, BUFFER);
                while ((count = bis.read(data, 0, BUFFER))
                  != -1) {
                   destBOS.write(data, 0, count);
                }
                destBOS.flush();
                destBOS.close();
                bis.close();
            }
          }
      } catch(Exception e) {
        System.out.println(e.getMessage());
      }
   }
}

