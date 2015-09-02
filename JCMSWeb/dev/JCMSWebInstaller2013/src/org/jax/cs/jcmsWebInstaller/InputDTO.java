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
public class InputDTO {
    private String WebInstallationDirectory = "";
    private String MySQLUserName = "";
    private String MySQLServerName = "";
    private String MySQLRemoteUserName = "";
    private String MySQLPort = "";
    private String MySQLPassword = "";
    private String MySQLRemotePassword = "";
    private String DatabaseName = "";

    /**
     * @return the WebInstallationDirectory
     */
    public String getWebInstallationDirectory() {
        return WebInstallationDirectory;
    }

    /**
     * @param WebInstallationDirectory the WebInstallationDirectory to set
     */
    public void setWebInstallationDirectory(String WebInstallationDirectory) {
        this.WebInstallationDirectory = WebInstallationDirectory;
    }

    /**
     * @return the MySQLUserName
     */
    public String getMySQLUserName() {
        return MySQLUserName;
    }

    /**
     * @param MySQLUserName the MySQLUserName to set
     */
    public void setMySQLUserName(String MySQLUserName) {
        this.MySQLUserName = MySQLUserName;
    }

    /**
     * @return the MySQLServerName
     */
    public String getMySQLServerName() {
        return MySQLServerName;
    }

    /**
     * @param MySQLServerName the MySQLServerName to set
     */
    public void setMySQLServerName(String MySQLServerName) {
        this.MySQLServerName = MySQLServerName;
    }

    /**
     * @return the MySQLPort
     */
    public String getMySQLPort() {
        return MySQLPort;
    }

    /**
     * @param MySQLPort the MySQLPort to set
     */
    public void setMySQLPort(String MySQLPort) {
        this.MySQLPort = MySQLPort;
    }

    /**
     * @return the MySQLPassword
     */
    public String getMySQLPassword() {
        return MySQLPassword;
    }

    /**
     * @param MySQLPassword the MySQLPassword to set
     */
    public void setMySQLPassword(String MySQLPassword) {
        this.MySQLPassword = MySQLPassword;
    }

    /**
     * @return the DatabaseName
     */
    public String getDatabaseName() {
        return DatabaseName;
    }

    /**
     * @param DatabaseName the DatabaseName to set
     */
    public void setDatabaseName(String DatabaseName) {
        this.DatabaseName = DatabaseName;
    }

    /**
     * @return the MySQLRemoteUserName
     */
    public String getMySQLRemoteUserName() {
        return MySQLRemoteUserName;
    }

    /**
     * @param MySQLRemoteUserName the MySQLRemoteUserName to set
     */
    public void setMySQLRemoteUserName(String MySQLRemoteUserName) {
        this.MySQLRemoteUserName = MySQLRemoteUserName;
    }

    /**
     * @return the MySQLRemotePassword
     */
    public String getMySQLRemotePassword() {
        return MySQLRemotePassword;
    }

    /**
     * @param MySQLRemotePassword the MySQLRemotePassword to set
     */
    public void setMySQLRemotePassword(String MySQLRemotePassword) {
        this.MySQLRemotePassword = MySQLRemotePassword;
    }

}
