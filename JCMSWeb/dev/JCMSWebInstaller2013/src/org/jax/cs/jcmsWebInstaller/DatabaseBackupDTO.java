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

/**
 *
 * @author cnh
 */
public class DatabaseBackupDTO {
    private String backupFileName = "";
    private String databaseName = "";

    public DatabaseBackupDTO(String backupFileName, String databaseName) {
        this.setBackupFileName(backupFileName);
        this.setDatabaseName(databaseName);
    }
    
    /**
     * @return the backupFileName
     */
    public String getBackupFileName() {
        return backupFileName;
    }

    /**
     * @param backupFileName the backupFileName to set
     */
    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

    /**
     * @return the databaseName
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @param databaseName the databaseName to set
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
}
