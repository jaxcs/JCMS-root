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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author cnh
 */
public class Connector {
    private String userName = "";
    private String pd = "";
    private String hostName = "";
    private String port = "";
    private Connection dbConnectRemote = null;
    private Connection dbConnectLocalhost = null;
    private InputDTO inputDTO = null;
    private ArrayList<DatabaseBackupDTO> databaseBackups = new ArrayList<DatabaseBackupDTO>();
    private LogIt logIt = new LogIt();

    public static final String MySQLFileName = "mysql";
    public static final String MySQLDumpFileName = "mysqldump";
    
    public Connector(InputDTO inputDTO) {
        this.setInputDTO(inputDTO);
    }
    
    public int getDatabaseBackupCount() {
        return databaseBackups.size();
    }
    
    private String getMySQLFileName() throws JCMSException {
        Utils util = new Utils();
        String mysqlFileName = "";
        try {
            ResultDTO resultDTO = this.getMySQLBaseDirectory();
            mysqlFileName = resultDTO.getStrMessage().trim();
            if (util.isOSWindows()) {
                // Do not include path.  Spaces muck it up.
                // MySQL bin must be in path
                mysqlFileName = MySQLFileName ;
            } else {
                // Include complete path
                mysqlFileName += "bin"+ File.separator + MySQLFileName ;
            }
            
        } catch (SQLException e) {
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nError getting MySQL base direcory \n"));
        }
        
        return mysqlFileName;
    }
    
    private void createMySQLHome() {
        Utils util = new Utils();
        String strCmd = "";
        
        try {
            if (util.isOSWindows()) {
                // WINDOWS
                strCmd = this.getMySQLBaseDirectory().getStrMessage() ;
                ProcessExec process = new ProcessExec();
                process.runCommand("\"SET JCMSMYSQL_HOME=" + strCmd + "\"");
            } else {
                // LINUX MAC
            }
        } catch (SQLException e) {
            
        }
    }
    
    /*
     * Connect to database server.
     */
    public void connect() throws JCMSException {
        String msg = "";
        try {
            // remote connection
           dbConnectRemote = DriverManager.getConnection("jdbc:mysql://" + this.getInputDTO().getMySQLServerName() + ":" + this.getInputDTO().getMySQLPort() + "/", this.getInputDTO().getMySQLRemoteUserName(), this.getInputDTO().getMySQLRemotePassword());
           
            // establish local connection too
            this.connectLocalhost();
        } catch (SQLException e) {
           dbConnectRemote = null;
            msg = "\nFailed to connect to MySQL Community Server at "+ this.getInputDTO().getMySQLServerName() +"!  Please check MySQL remote user name and password.\n" + "Error message:  "+ e;
            System.out.println("getMySQLDatabaseConnection():  "+ msg);
            throw new JCMSException(msg);
        }
    }
    
    /*
     * Connect to database server.
     */
    private void connectLocalhost() throws JCMSException {
        try {
            dbConnectLocalhost = DriverManager.getConnection("jdbc:mysql://localhost:" + this.getInputDTO().getMySQLPort() + "/", this.getInputDTO().getMySQLUserName(), this.getInputDTO().getMySQLPassword());
        } catch (SQLException e) {
            dbConnectLocalhost = null;
            System.out.println("getMySQLDatabaseConnection():  Failed to connect to MySQL Community Server at LOCALHOST!  " + e);
            throw new JCMSException("\nFailed to connect to MySQL Community Server at "+ this.getInputDTO().getMySQLServerName() +"!  Please check MySQL user name and password.\n" + "Error message:  "+ e);
        }
    }
    
    public void testRemoteConnection() throws JCMSException {
        Connection dbConnectTest = null;
        String msg = "";
        try { 
            try {
                // remote connection
                dbConnectTest = DriverManager.getConnection("jdbc:mysql://" + this.getInputDTO().getMySQLServerName() + ":" + this.getInputDTO().getMySQLPort() + "/", this.getInputDTO().getMySQLRemoteUserName(), this.getInputDTO().getMySQLRemotePassword());
            } catch (SQLException e) {
                dbConnectTest = null;
                msg = "\nFailed to connect to MySQL Community Server at "+ this.getInputDTO().getMySQLServerName() +"!  Please check MySQL remote user name and password.\n" + "Error message:  "+ e;
                System.out.println("testRemoteConnection():  "+ msg);
                throw new JCMSException(msg);
            } finally {
                if (dbConnectTest != null && !dbConnectTest.isClosed()) {
                    dbConnectTest.close();
                }
            }
        } catch (SQLException sqle) {
            throw new JCMSException(msg);
        }
        
    }
    
    /*
     * Connect to database server.
     */
    public void testLocalConnection() throws JCMSException {
        Connection dbConnectTest = null;
        String msg = "";
        try { 
            try {
                dbConnectTest = DriverManager.getConnection("jdbc:mysql://localhost:" + this.getInputDTO().getMySQLPort() + "/", this.getInputDTO().getMySQLUserName(), this.getInputDTO().getMySQLPassword());
            } catch (SQLException e) {
                dbConnectTest = null;
                msg = "\nFailed to connect to MySQL Community Server at "+ this.getInputDTO().getMySQLServerName() +"!  Please check MySQL local user name and password.\n" + "Error message:  "+ e;
                System.out.println(msg);
                throw new JCMSException(msg);
            } finally {
                if (dbConnectTest != null && !dbConnectTest.isClosed()) {
                    dbConnectTest.close();
                }
            }
        } catch (SQLException sqle) {
            throw new JCMSException(msg);
        }
    }

    public void disconnect() {
        try {
            if (!this.dbConnectRemote.isClosed())
                this.dbConnectRemote.close();
            if (!this.dbConnectLocalhost.isClosed())
                this.dbConnectLocalhost.close();
        } catch (SQLException e) {
            
        }
    }
    
    public ResultSet executeQuery(String statement) throws SQLException {
        Statement sqlStmt = dbConnectRemote.createStatement();
        ResultSet tableData = sqlStmt.executeQuery(statement);
        return tableData;
    }
    
    public int executeUpdate(String statement) throws SQLException {
        Statement sqlStmt = dbConnectRemote.createStatement();
        int affectedRows = sqlStmt.executeUpdate(statement);
        return affectedRows;
    }
    
    public Boolean getMySQLDriver() {
        try
        {
            // Java Specific
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Failed to load MySQL connector driver: \n" + e);
            return false;
        }
        return true;
    }

    public String getMySQLVersion() {
        String msg = "";
        try {
            ResultSet tableData = null;
            String getVariablesStatement = "SHOW VARIABLES LIKE 'version%';";
            Statement sqlStmt = this.dbConnectRemote.createStatement();
            tableData = sqlStmt.executeQuery(getVariablesStatement);
            Integer pos = 0;
            String subString = "";

            // Create Collection to return.
            while (tableData.next())
            {
                if ( 0 == "version".compareTo(tableData.getString("Variable_name")))
                {
                    String mysqlVersion =  tableData.getString("Value") ;
                    System.out.println("MySQL version " + mysqlVersion);
                    pos = mysqlVersion.indexOf(".");

                    Double msqlWhole = Double.parseDouble(mysqlVersion.substring(0,pos));
                    subString = mysqlVersion.substring(pos + 1,mysqlVersion.length());
                    Double msqlPart = Double.parseDouble("0." + subString.substring(0, mysqlVersion.indexOf(".")));

                    if ( 5.1 <= msqlWhole + msqlPart ) {
                        // INFO
                        msg = "MySQL version is:  " + mysqlVersion;
                    } else {
                        // WARN
                        msg =  "MySQL version is old,\nFound:  " + mysqlVersion + "\nMySQL 5.1 or greater is recommended.";
                    }
                }
            }
        } catch (SQLException e) {
            
        }
        return msg;
    }
    
    public ResultDTO getMySQLLowerCase() {
        ResultDTO resultDTO = new ResultDTO();
        String msg = "";
        Boolean blnContinue = true;
        try {
            ResultSet tableData = null;
            String getLowerVariablesStatement = "SHOW VARIABLES LIKE 'lower_case%';";
            Statement sqlStmt = dbConnectRemote.createStatement();
            tableData = sqlStmt.executeQuery(getLowerVariablesStatement);

            boolean lowerCaseFileSystem = false;
            int lowerCaseTableNames = -1;
            String tested = null;

            // Create Collection to return.
            while (tableData.next())
            {
                msg += "\nMySQL Case Setting :" + tableData.getString("Variable_name") + " : " +  tableData.getString("Value");
                 if ( 0 == "lower_case_file_system".compareTo(tableData.getString("Variable_name")))
                 {
                     tested = "found";
                     if ( 0 == "OFF".compareTo(tableData.getString("Value")) )
                         lowerCaseFileSystem = false;
                     if ( 0 == "ON".compareTo(tableData.getString("Value")))
                         lowerCaseFileSystem = true;
                 }
                 if ( 0 == "lower_case_table_names".compareTo(tableData.getString("Variable_name")))
                 {
                    tested = "found";
                    lowerCaseTableNames = Integer.parseInt(tableData.getString("Value"));
                 }
            }

            // Did we find the variables?
            if ( null == tested ) {
                // Nasty message and exit.
                msg =  "Cannot find file system varibles:" ;
            } else {
                // If File System is Off then table names should be set to 0
                if ( lowerCaseFileSystem == false ){
                    if ( lowerCaseTableNames == 0  )
                    {
                        resultDTO.setSeverity(Severity.INFO);
                        msg +=  "\nMySQL Lowercase Setting Okay: " + Integer.toString(lowerCaseTableNames) ;
                    } else {
                        resultDTO.setSeverity(Severity.WARN);
                        msg +=  "\nMySQL Lowercase Setting Warning: " + Integer.toString(lowerCaseTableNames)  +
                            "\n Please review these issues from the MySQL website." +
                            "\n http://dev.mysql.com/doc/refman/5.1/en/server-system-variables.html#sysvar_lower_case_file_system" +
                            "\n http://dev.mysql.com/doc/refman/5.1/en/server-system-variables.html#sysvar_lower_case_table_names\n" ;
                    }

                }

                // If File System is ON then it should eqaul 2
                if ( lowerCaseFileSystem == true ){
                    if ( lowerCaseTableNames == 2  )
                    {
                        resultDTO.setSeverity(Severity.INFO);
                        msg +=  "\nMySQL Lowercase Setting Okay: " + Integer.toString(lowerCaseTableNames);

                    } else {
                        resultDTO.setSeverity(Severity.WARN);
                        msg +=  "\nMySQL Lowercase Setting Warning: " + Integer.toString(lowerCaseTableNames) +
                            "\nPlease review these issues from the MySQL website." +
                            "\nhttp://dev.mysql.com/doc/refman/5.1/en/server-system-variables.html#sysvar_lower_case_file_system" +
                            "\nhttp://dev.mysql.com/doc/refman/5.1/en/server-system-variables.html#sysvar_lower_case_table_names" ;
                    }
                }
            }
            resultDTO.setBlnSuccess(Boolean.TRUE);
        } catch (SQLException e) {
            msg = "\nMySQL lower case error:  "+ e;
            resultDTO.setSeverity(Severity.ERROR);
            resultDTO.setBlnSuccess(Boolean.FALSE);
        }
        resultDTO.setStrMessage(msg);
        
        return resultDTO;
    }
    
    private String formatPasswordParameter(String pass) {
        if (pass.length() == 0)
            return "";
        else
            return " -p"+ pass +" ";
    }
    
    public void runMySQLFile(String databaseName, String scriptName) throws JCMSException {
        Utils util = new Utils();
        String strCmd = "";
        String strFileName = "";
        String msg = "";
        String installerDirectory = util.getInstallerDirectory();
        String upgradeScriptDir = "";
        
        if (util.isOSWindows()) {
            strCmd = strCmd
                    + this.getMySQLFileName()
                    + " -h "+ this.getInputDTO().getMySQLServerName()
                    + " -u "+ this.getInputDTO().getMySQLRemoteUserName()
                    + " -P "+ this.getInputDTO().getMySQLPort()
                    + this.formatPasswordParameter(this.getInputDTO().getMySQLRemotePassword())
                    + " -D "+ databaseName 
                    + " -e \"SOURCE "+ installerDirectory + File.separator + "UpgradeScripts"+ File.separator + scriptName +"\"";
        } else {
            upgradeScriptDir = File.separator + "UpgradeScripts" + File.separator;
            strCmd = this.getMySQLFileName()
                    + " -h "+ this.getInputDTO().getMySQLServerName()
                    + " -u "+ this.getInputDTO().getMySQLRemoteUserName()
                    + " -P "+ this.getInputDTO().getMySQLPort()
                    + this.formatPasswordParameter(this.getInputDTO().getMySQLRemotePassword())
                    + " -D "+ databaseName 
                    + " -e \"SOURCE "+ installerDirectory + upgradeScriptDir + scriptName +"\"";
        }

        strFileName = util.getInstallerDirectory() + File.separator + "mysqlcreatedb.log" ;

        System.out.println("runMySQLFile cmd:  "+ strCmd);
        System.out.println("runMySQLFile log file:  "+ strFileName);
        ProcessExec process = new ProcessExec();
        int exitVal = process.runCommand(strCmd, strFileName); 

        if (exitVal > 0) {
            msg = "\nFailed to create JCMS schema "+ databaseName +".  "
                    + "\nJCMS Web installation cancelled.  "
                    + "\nRefer to log file for more information: "
                    + "\n"+ strFileName;
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
        }
    }
    
    public void clearDatabaseBackups() {
        this.databaseBackups.clear();
    }
    
    // Backup user selected database
    public ResultDTO backupJCMSDatabase(String installDirectory) throws JCMSException {
        return this.backupDatabase(this.getInputDTO().getDatabaseName(), installDirectory);
    }

    // Primary backup process and accomodates application code calls to backup other databases 
    public ResultDTO backupDatabase(String databaseName, String installDirectory) throws JCMSException {
        ResultDTO resultDTO = new ResultDTO();
        String msg = "";
        Utils util = new Utils();
        String strCmd = "";
        String strFullPathFileName = "";
        String strDbBackupFileName = "";
        try {
            if (this.findDatabase(databaseName)) {
                resultDTO = this.getMySQLBaseDirectory();
                String mysqlBaseDirectory = resultDTO.getStrMessage().trim();
                if (resultDTO.getBlnSuccess()) {
                    
                    if (util.isOSWindows()) {
                        // DO NOT INCLUDE FULL WINDOWS PATH.  HAVE NOT FIGURED OUT THE CORRECT SYNTAX 
                        // WHEN THERE ARE SPACES IN DIRECTORY NAMES
                        // surround exe path with quotes
                        strCmd = MySQLDumpFileName;
                        // strCmd = "\""+ mysqlBaseDirectory + "bin/mysqldump.exe\"";
                    } else {
                        // LINUX, MAC, UNIX ...
                        strCmd = mysqlBaseDirectory + "bin/mysqldump";
                    }
                    
                    // Sample mysqldump statement to backup database tables, procedures, functions and triggers
                    // mysqldump -h localhost -u dba -P 3306 -prsdba --default-character-set=utf8 --single-transaction=TRUE --routines jcms_db > test4.sql
                    // --routines option instructs mysqldump to export procedures and functions.
                    strCmd = strCmd
                            + " -h "+ this.getInputDTO().getMySQLServerName()
                            + " -u "+ this.getInputDTO().getMySQLRemoteUserName()
                            + " -P "+ this.getInputDTO().getMySQLPort()
//                            + " -p"+ this.getInputDTO().getMySQLRemotePassword() 
                            + this.formatPasswordParameter(this.getInputDTO().getMySQLRemotePassword())
                            + " --default-character-set=utf8 --single-transaction=TRUE --routines --triggers --events "
                            + " "+ databaseName ;
                    
                    strDbBackupFileName = "mysqldump_"
                            + databaseName +"_"
                            + util.getCurrentTimestamp(Boolean.TRUE,Boolean.TRUE,Boolean.TRUE) +".sql" ;
                    strFullPathFileName = util.getInstallerDirectory() + File.separator + strDbBackupFileName;
                    
                    System.out.println("Backup file command is "+ strCmd);
                    
                    ProcessExec process = new ProcessExec();
                    int exitVal = process.runCommand(strCmd, strFullPathFileName);
                    
                    if (exitVal == 0) {
                        msg = "\nJCMS database "+ databaseName + " successfully backed up to file " + strFullPathFileName ;
                        util.copyFolder(new File(strFullPathFileName), new File(installDirectory + File.separator + "backups" + File.separator + strDbBackupFileName));
                        resultDTO.setStrMessage(msg);
                        resultDTO.setSeverity(Severity.SUCCESS);
                        resultDTO.setBlnSuccess(Boolean.TRUE);
                        this.databaseBackups.add(new DatabaseBackupDTO(strFullPathFileName, databaseName));
                    } else {
                        msg = "\nJCMS database backup of "+ databaseName 
                                + " FAILED!!!  JCMS Web installation cancelled.  "
                                + "Computer account "
                                + "\n  1.  May not have permissions to run MySQL dump utility"
                                + "\n  2.  May not have permissions to create files in directory " + util.getInstallerDirectory() 
                                + "\n  3.  May not have MySQL bin in system path";
                        resultDTO.setStrMessage(msg);
                        resultDTO.setSeverity(Severity.ERROR);
                        resultDTO.setBlnSuccess(Boolean.FALSE);
                        throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
                    }
                    
                } else {
                    msg = "\nFailed to get MySQL's base directory.  "
                            + "\nJCMS Web installation cancelled.  "
                            + "\nCheck to see if MySQL is properly installed and try again.";
                    resultDTO.setStrMessage(msg);
                    resultDTO.setSeverity(Severity.ERROR);
                    resultDTO.setBlnSuccess(Boolean.FALSE);
                }
            }
        } catch (SQLException sqle) {
            msg = "\nFailed to backup JCMS database "+ databaseName +".  "
                    + "JCMS Web installation cancelled.  "+ sqle;
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
        } catch (Exception e) {
            msg = "\nFailed to backup JCMS database "+ databaseName +".  "
                    + "JCMS Web installation cancelled.  "+ e;
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
        }
        resultDTO.setStrMessage(msg);
        
        return resultDTO;
    }
    
    public void restoreDatabase(String databaseName, String backupFilename) throws JCMSException {
        Utils util = new Utils();
        int exitVal = 0;
        String strCmd = "";
        String strFileName = "";
        String msg = "";
        String installerDirectory = util.getInstallerDirectory();

        try {
            util.removeWarningMessageFromDumpFile(backupFilename, installerDirectory + File.separator + "temp.sql");
        } catch (IOException ioe) {
            
        }
        
        strCmd = strCmd
                + this.getMySQLFileName()
                + " -h "+ this.getInputDTO().getMySQLServerName()
                + " -u "+ this.getInputDTO().getMySQLRemoteUserName()
                + " -P "+ this.getInputDTO().getMySQLPort()
                + this.formatPasswordParameter(this.getInputDTO().getMySQLRemotePassword())
                + " -D "+ databaseName 
                + " -e \"SOURCE "+ backupFilename +"\"";
        
        strFileName = installerDirectory + File.separator
                +"mysqlrestore_"+ databaseName 
                + util.getCurrentTimestamp(Boolean.TRUE,Boolean.TRUE,Boolean.TRUE) +".log" ;
        System.out.println("RestoreDatabase cmd:  "+ strCmd);
        
        try {
            this.executeUpdate("CREATE DATABASE IF NOT EXISTS "+ databaseName +";");
            ProcessExec process = new ProcessExec();
            exitVal = process.runCommand(strCmd, strFileName); 
            if (exitVal == 0) {
                getLogIt().logMessage(Severity.INFO, databaseName +" restored.");
            } else {
                msg = "\nFailed to restore JCMS database "+ databaseName +".  "
                        + "\nJCMS Web installation cancelled.  "
                        + "\nRefer to log file for more information: "
                        + "\n"+ strFileName;
                throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
            }
        } catch (SQLException sqle) {
            msg = "\nFailed to restore JCMS database "+ databaseName +".  "
                    + "\nJCMS Web installation cancelled.  "
                    + "\nRefer to log file for more information: "
                    + "\n"+ strFileName;
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, msg));
        } 
        
    }
    
    private ResultDTO getMySQLBaseDirectory() throws SQLException {
        ResultDTO resultDTO = new ResultDTO();
        String msg = "";
        String baseDirectory = "";
        ResultSet tableData = null;
        String getBaseDirStatement = "SHOW VARIABLES WHERE Variable_name='basedir';";
        Statement sqlStmt = dbConnectLocalhost.createStatement();
        tableData = sqlStmt.executeQuery(getBaseDirStatement);

        // Create Collection to return.
        while (tableData.next())
        {
            String z = tableData.getString("Variable_name");
            if ( 0 == "basedir".compareTo(tableData.getString("Variable_name"))){
                baseDirectory = tableData.getString("Value");
            }
        }

        if (baseDirectory.length() > 0) {
            if (!baseDirectory.endsWith("/")) {
                baseDirectory += File.separator;
            }
            resultDTO.setStrMessage(baseDirectory.trim());
            resultDTO.setSeverity(Severity.INFO);
            resultDTO.setBlnSuccess(Boolean.TRUE);
        } else {
            resultDTO.setStrMessage("MySQL base directory not found!");
            resultDTO.setSeverity(Severity.ERROR);
            resultDTO.setBlnSuccess(Boolean.FALSE);
        }
        
        return resultDTO;
    }
    
    public ArrayList<String> getDatabaseNames() {
        ArrayList<String> lstDatabaseNames = new ArrayList<String>();
        try {
            String zMySQLStatement = "SHOW DATABASES;";
            Statement zsqlStmt = dbConnectRemote.createStatement();
            ResultSet ztableData = zsqlStmt.executeQuery(zMySQLStatement);
            String databaseName = "";

            // Create Collection to return.
            while (ztableData.next())
            {
                databaseName = ztableData.getString("Database");
                if (databaseName.compareToIgnoreCase("information_schema")!= 0 &&  
                    databaseName.compareToIgnoreCase("jcmsjboss")!= 0 && 
                    databaseName.compareToIgnoreCase("mysql")!= 0 && 
                    databaseName.compareToIgnoreCase("performance_schema")!= 0 )
                    lstDatabaseNames.add(databaseName);
            }
        } catch (SQLException e) {
            return lstDatabaseNames;
        }
        return lstDatabaseNames;
    }

    public Boolean findDatabase(String databaseName) throws SQLException {
            boolean dbExists = false;
            String mySQLStatement = "SHOW DATABASES;";
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(mySQLStatement);

            while (tableData.next())
            {
                 if ( 0 == databaseName.compareTo(tableData.getString("Database"))){
                     dbExists = true;
                     break;
                 }
            }
            
        return dbExists;
    }
    
    public void forceINNODBEngine(String databaseName) throws SQLException {
        String mySQLStatement = "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA='"+ databaseName +"' AND ENGINE='MyISAM'; ";
        Statement sqlStmt = dbConnectRemote.createStatement();
        ResultSet tableData = sqlStmt.executeQuery(mySQLStatement);

        while (tableData.next())
        {
            this.executeUpdate("ALTER TABLE "+ databaseName +"."+ tableData.getString("TABLE_NAME") +" ENGINE = InnoDB;");
        }
    }

    
    public ResultDTO validateJCMSSystemUser() {
        ResultDTO resultDTO = new ResultDTO();
        String msg = "";
        String jcmsHost = "";
        String jcmsUser = "";
        String jcmsPassword = "";
        String usersStatement = "SELECT Host, User, Password FROM mysql.user;";

        try {
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(usersStatement);
            Boolean foundLocalhost = false;
            Boolean foundAny = false;

            // Move to the first of the list.
            while (tableData.next())
            {
                if (!foundLocalhost &&
                    "_jcmsWeb".compareTo(tableData.getString("user")) == 0 && 
                    "localhost".compareTo(tableData.getString("host")) == 0) {
                    jcmsHost = tableData.getString("host");
                    jcmsUser = tableData.getString("user");
                    jcmsPassword = tableData.getString("password");
                    foundLocalhost = true;
                }
                if (!foundAny &&
                    "_jcmsWeb".compareTo(tableData.getString("user")) == 0 && 
                    "%".compareTo(tableData.getString("host")) == 0) {
                    jcmsHost = tableData.getString("host");
                    jcmsUser = tableData.getString("user");
                    jcmsPassword = tableData.getString("password");
                    foundAny = true;
                }
            }

            if (!foundLocalhost) {
                // Create JCMS Web localhost user
                String createUserStatement = "CREATE USER '_jcmsWeb'@'localhost' IDENTIFIED BY PASSWORD '*65A0C2FB8C999D66809145BFE3BFAC429AA363FD';";
                sqlStmt = dbConnectRemote.createStatement();
                sqlStmt.execute(createUserStatement);
                msg +=  "\nJCMS Web any host database user created!";
            }
            if (!foundAny) {
                // Create JCMS Web any host user
                String createUserStatement = "CREATE USER '_jcmsWeb'@'%' IDENTIFIED BY PASSWORD '*65A0C2FB8C999D66809145BFE3BFAC429AA363FD';";
                sqlStmt = dbConnectRemote.createStatement();
                sqlStmt.execute(createUserStatement);
                msg +=  "\nJCMS Web local host database user created!";
            }
            
            // Grant jcmsUser
            String grantUserStatement = "GRANT ALL ON "+ this.getInputDTO().getDatabaseName() +".* TO '_jcmsWeb'@'localhost' IDENTIFIED BY 'jcms';";
            sqlStmt = dbConnectRemote.createStatement();
            sqlStmt.execute(grantUserStatement);
            msg +=  "\nJCMS Web Database User GRANTED privileges on "+ this.getInputDTO().getDatabaseName() +" from localhost!";

            // Grant jcmsUser
            grantUserStatement = "GRANT ALL ON "+ this.getInputDTO().getDatabaseName() +".* TO '_jcmsWeb'@'%' IDENTIFIED BY 'jcms';";
            sqlStmt = dbConnectRemote.createStatement();
            sqlStmt.execute(grantUserStatement);
            msg +=  "\nJCMS Web Database User GRANTED privileges on "+ this.getInputDTO().getDatabaseName() +" from any host!";

            // Flush privileges
            grantUserStatement = "FLUSH PRIVILEGES;";
            sqlStmt = dbConnectRemote.createStatement();
            sqlStmt.execute(grantUserStatement);
            msg +=  "\nDatabase privileges Flushed";
            
            resultDTO.setBlnSuccess(Boolean.TRUE);
            resultDTO.setSeverity(Severity.INFO);
        } catch (SQLException e) {
            msg += "\nError validating JCMS user at localhost. \n  Exception detail:  "+ e;
            resultDTO.setSeverity(Severity.ERROR);
            resultDTO.setBlnSuccess(Boolean.FALSE);
        }
        
        resultDTO.setStrMessage(msg);
        
        return resultDTO;
    }

    public String getJCMSVersion() throws SQLException{
        String jcmsDBName = this.inputDTO.getDatabaseName();
        String jcmsVersion = "";
        
        ResultSet tableData = null;
        String query = "Select * FROM "+ jcmsDBName +".dbInfo;";
        Statement sqlStmt = this.dbConnectRemote.createStatement();
        tableData = sqlStmt.executeQuery(query);

        // Create Collection to return.
        while (tableData.next())
        {
            jcmsVersion = tableData.getString("releaseNum");
            System.out.println(inputDTO.getDatabaseName() +" version " + jcmsVersion);
        }
        
        return jcmsVersion;
    }

    public String getJCMSWebDatabaseReleaseNum() throws SQLException{
        String jcmsDBName = "jcmsweb_db";
        String value = "";
        
        ResultSet tableData = null;
        String query = "Select * FROM "+ jcmsDBName +".dbInfo;";
        Statement sqlStmt = this.dbConnectRemote.createStatement();
        tableData = sqlStmt.executeQuery(query);

        // Create Collection to return.
        while (tableData.next())
        {
            value = tableData.getString("databaseReleaseNum");
            System.out.println(inputDTO.getDatabaseName() +" JCMS Web database release number is " + value);
        }
        
        return value;
    }

    public String rollbackDatabases() throws JCMSException{
        String msg = "";
        // Restore database backups. MySQL dump files.
        try {
            // Drop Databases
            for (DatabaseBackupDTO dto : this.databaseBackups) {
                this.executeUpdate("DROP DATABASE IF EXISTS "+ dto.getDatabaseName() +";");
                this.executeUpdate("CREATE DATABASE IF NOT EXISTS "+ dto.getDatabaseName() +";");
            }
            for (DatabaseBackupDTO dto : this.databaseBackups) {
                this.restoreDatabase(dto.getDatabaseName(), dto.getBackupFileName());
                msg += "\n"+ dto.getDatabaseName() + " database restored.";
            }
            this.databaseBackups.clear();
        } catch (SQLException e) {
            msg = "\nFailed to restore original databases.  You must restore the original database(s) using MySQL command line. ";
            for (DatabaseBackupDTO dto : this.databaseBackups) {
                msg += "\nDatabase to restore is "+ dto.getDatabaseName() +".  \nMySQL dump filename is "+ dto.getBackupFileName() +"\n\n";
            }
            this.databaseBackups.clear();
            throw new JCMSException(msg);
        }
        return msg;
    }
    
    public void dropDatabase(String databaseName) throws JCMSException{
        try {
            this.executeUpdate("DROP DATABASE IF EXISTS "+ databaseName +";");
        } catch (SQLException e) {
            throw new JCMSException(getLogIt().logMessage(Severity.WARN, "\nFailed to drop database " + databaseName +"."));
        }
    }
    
    public void dropDatabase() throws JCMSException{
        String dbName = "";
        try {
            for (DatabaseBackupDTO dto : this.databaseBackups) {
                dbName = dto.getDatabaseName();
                this.executeUpdate("DROP DATABASE IF EXISTS "+ dto.getDatabaseName() +";");
            }
        } catch (SQLException e) {
            throw new JCMSException(getLogIt().logMessage(Severity.WARN, "\nFailed to retore previous database state by removing database " + dbName +"."));
        }
    }
    
    /**
     * The objective of this method is to migrate JCMSWebDB User Account information
     * and Query Definitions to one JCMS DB.
     * All scripts are idempotent allowing user's to run this upgrader again 
     * even after their JCMSWebDB has been migrated at a previous upgrade.
     */
    public void migrateJCMSWebDB(String obsoleteJCMSWebDBName) throws JCMSException {
        ResultSet tableData = null;
        String sqlStmt = "";
        ArrayList<String> workgroupUserList = new ArrayList<String>();
        String mtsadminKey = "";
        String privilegeKey = "";
        
        try {
            String jcmsDBName = this.inputDTO.getDatabaseName();                    // standard jcms database name
            String colonyManagementKey = "";
            String reportingKey = "";
            String queryingKey = "";

            if (obsoleteJCMSWebDBName.trim().length() > 0 && this.findDatabase(obsoleteJCMSWebDBName)) {
                /**
                 * JCMS Web version 2.8.0 and prior versions referenced user account
                 * information and query definitions in a second web database.
                 * The second web database has been deprecated and this code is 
                 * responsible for migrating this data to one JCMS DB.
                 * This means the keys must also be migrated to the JCMS DB user account schema
                 * because of workgroups and potentially user dependencies.
                 */
                
                // Get mtsadmin primary key
                tableData = this.executeQuery("SELECT * FROM `"+ obsoleteJCMSWebDBName +"`.`User` WHERE UserName = 'mtsadmin';");
                if (tableData.next()) {
                    mtsadminKey = tableData.getString("_User_key");

                    // Add new functional area ColonyManagement
                    sqlStmt = " INSERT INTO "+ obsoleteJCMSWebDBName +".FunctionalArea"
                            + " (FunctionalArea,CreatedBy,DateCreated,ModifiedBy,DateModified,Version) "
                            + " VALUES ('ColonyManagement','JCMSWeb Installer',now(),'JCMSWeb Installer',now(),1)";
                    this.executeUpdate(sqlStmt);

                    // Get ColonyManagement key
                    tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".FunctionalArea WHERE FunctionalArea = 'ColonyManagement';");
                    if (!tableData.next()) throw new JCMSException("\nNew functional area, ColonyManagement, not found.");
                    colonyManagementKey = tableData.getString("_FunctionalArea_key");
                    if (colonyManagementKey.length() == 0) throw new JCMSException("\nNew functional area, ColonyManagement, not found.");

                    // Get Reporting key
                    tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".FunctionalArea WHERE FunctionalArea = 'Reporting';");
                    if (!tableData.next()) throw new JCMSException("\nReporting functional area not found.");
                    reportingKey = tableData.getString("_FunctionalArea_key");
                    if (reportingKey.length() == 0) throw new JCMSException("\nReporting functional area not found.");

                    // Get Querying key
                    tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".FunctionalArea WHERE FunctionalArea = 'Querying';");
                    if (!tableData.next()) throw new JCMSException("\nQuerying functional area not found.");
                    queryingKey = tableData.getString("_FunctionalArea_key");
                    if (queryingKey.length() == 0) throw new JCMSException("\nQuerying functional area not found.");

                    // Get privilege key
                    tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".Privilege WHERE Privilege = 'Write';");
                    if (!tableData.next()) throw new JCMSException("\nPrivilege not found.");
                    privilegeKey = tableData.getString("_Privilege_key");
                    if (privilegeKey.length() == 0) throw new JCMSException("\nDid not find write privilege.");
                
                    // Associate all users to ColonyManagement where they are already
                    // associated to Querying and Reporting.  EXCLUDE MTSADMIN.  
                    // Taken care of by a different script.
                    tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".WorkgroupUser WHERE _User_key <> "+ mtsadminKey);
                    while (tableData.next()) {
                        workgroupUserList.add(tableData.getString("_WorkgroupUser_key"));
                    }

                    for (String wuKey :  workgroupUserList) {
                        // for each workgroup user check to see if the user belongs to Querying and Reporting
                        // If so, add workgroup user to ColonyManagement functional area.
                        
                        tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".WorkgroupUserFunctionalArea "
                                + " WHERE _FunctionalArea_key = "+ reportingKey +" "
                                + " AND _WorkgroupUser_key = "+ wuKey +";");
                        if (tableData.next()) {
                            // has reporting 
                            tableData = this.executeQuery("SELECT * FROM "+ obsoleteJCMSWebDBName +".WorkgroupUserFunctionalArea "
                                    + " WHERE _FunctionalArea_key = "+ queryingKey +" "
                                    + " AND _WorkgroupUser_key = "+ wuKey +";");
                            if (tableData.next()) {
                                // has querying
                                // Add user to colonymanagement functional area
                                sqlStmt = " INSERT INTO "+ obsoleteJCMSWebDBName +".WorkgroupUserFunctionalArea "
                                        + " (_FunctionalArea_key,_WorkgroupUser_key,_Privilege_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                                        + " VALUES"
                                        + " ("+ colonyManagementKey +","+ wuKey +","+ privilegeKey +",'JCMSWeb Installer',now(),'JCMSWeb Installer',now(),1);";
                                this.executeUpdate(sqlStmt);
                            }
                        }
                    }
                    
                    // Copy Centers
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".Center "
                            + " (_Center_key,Center,IsActive,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _Center_key,Center,IsActive,CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".Center wc"
                            + " WHERE NOT EXISTS "
                            + "    (SELECT 1 FROM "+ jcmsDBName +".Center dc WHERE dc._Center_key = wc._Center_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy Workgroups
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".Workgroup"
                            + " (_Workgroup_key,_Center_key,WorkgroupName,IsActive,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _Workgroup_key,_Center_key,WorkgroupName,IsActive,CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".Workgroup src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".Workgroup dest WHERE dest._Workgroup_key = src._Workgroup_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy Users
                    sqlStmt = " INSERT INTO `"+ jcmsDBName +"`.`User`"
                            + " (_User_key,NetworkID,FirstName,LastName,Title,EmailAddress,InternalPhone,ExternalPhone,HireDate,IsActive,"
                            + " CreatedBy,DateCreated,ModifiedBy,DateModified,Version,_DefaultWorkgroup_key,IsMasterAdministrator,UserName,Password_)"
                            + " SELECT"
                            + " _User_key,NetworkID,FirstName,LastName,Title,EmailAddress,InternalPhone,ExternalPhone,HireDate,IsActive,"
                            + " CreatedBy,DateCreated,ModifiedBy,DateModified,Version,_DefaultWorkgroup_key,IsMasterAdministrator,UserName,Password_"
                            + " FROM `"+ obsoleteJCMSWebDBName +"`.`User` src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM `"+ jcmsDBName +"`.`User` dest WHERE dest._User_key = src._User_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                     // Copy Workgroup Users
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".WorkgroupUser"
                            + " (_WorkgroupUser_key,_Workgroup_key,_User_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _WorkgroupUser_key,_Workgroup_key,_User_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".WorkgroupUser src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".WorkgroupUser dest WHERE dest._WorkgroupUser_key = src._WorkgroupUser_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy Functional Areas
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".FunctionalArea"
                            + " (_FunctionalArea_key,FunctionalArea,Description,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _FunctionalArea_key,FunctionalArea,Description,CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".FunctionalArea src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".FunctionalArea dest WHERE dest._FunctionalArea_key = src._FunctionalArea_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy WorkgroupUserFunctionalAreas
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".WorkgroupUserFunctionalArea"
                            + " (_WorkgroupUserFunctionalArea_key,_FunctionalArea_key,_WorkgroupUser_key,_Privilege_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _WorkgroupUserFunctionalArea_key,_FunctionalArea_key,_WorkgroupUser_key,_Privilege_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".WorkgroupUserFunctionalArea src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".WorkgroupUserFunctionalArea dest WHERE dest._WorkgroupUserFunctionalArea_key = src._WorkgroupUserFunctionalArea_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy cv_QueryType
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".cv_QueryType"
                            + " (_QueryType_key,QueryType,IsActive,SortOrder,_Workgroup_key,IsDefault,"
                            + "  CreatedBy,DateCreated,ModifiedBy,DateModified,IsDeprecated,"
                            + "  _VocabularySource_key,ElementID,Version)"
                            + " SELECT"
                            + " _QueryType_key,QueryType,IsActive,SortOrder,_Workgroup_key,IsDefault,"
                            + " CreatedBy,DateCreated,ModifiedBy,DateModified,IsDeprecated,"
                            + " _VocabularySource_key,ElementID,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".cv_QueryType src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".cv_QueryType dest WHERE dest._QueryType_key = src._QueryType_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    // Copy QueryDefinition
                    sqlStmt = " INSERT INTO "+ jcmsDBName +".QueryDefinition"
                            + " (_QueryDefinition_key,_User_key,_Workgroup_key,QueryName,QueryOptions,_QueryType_key,IsActive,"
                            + "  CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                            + " SELECT"
                            + " _QueryDefinition_key,_User_key,_Workgroup_key,QueryName,QueryOptions,_QueryType_key,IsActive,"
                            + " CreatedBy,DateCreated,ModifiedBy,DateModified,Version"
                            + " FROM "+ obsoleteJCMSWebDBName +".QueryDefinition src"
                            + " WHERE NOT EXISTS (SELECT 1 FROM "+ jcmsDBName +".QueryDefinition dest WHERE dest._QueryDefinition_key = src._QueryDefinition_key LIMIT 1);";
                    this.executeUpdate(sqlStmt);

                    this.executeUpdate("DROP DATABASE IF EXISTS "+ obsoleteJCMSWebDBName +";");
                }
            } else {
                throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nAn error occurred migrating user account information to JCMS DB.  "
                        + "Upgrade cancelled.  Application will be returned to its original state.\n"
                        + "Could not find JCMS Web database called "+ obsoleteJCMSWebDBName +"\n"));
            }
                
        } catch (SQLException sqle) {
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nAn error occurred migrating user account information to JCMS DB.  "
                    + "Upgrade cancelled.  Application will be returned to its original state.\n"+ sqle.getMessage() ));
        } catch (Exception e) {
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nAn error occurred migrating user account information to JCMS DB.  "
                    + "Upgrade cancelled.  Application will be returned to its original state.\n"+ e.getMessage() ));
        }
        
        
    }

    /*
     * Idempotent script to update the structure of table, dbInfo.
     */
    public void alterDbInfoStructure() throws JCMSException {
        ResultSet tableData = null;
        String sqlStmt = "";
        String jcmsDbName = this.inputDTO.getDatabaseName();
        
        try {
            ArrayList<String> fieldNames = new ArrayList<String>();
            fieldNames.add("databaseReleaseNum");
            fieldNames.add("majorVersion");
            fieldNames.add("minorVersion");
            fieldNames.add("bugFixVersion");
            fieldNames.add("buildVersion");
            
            for (String fieldName : fieldNames) {
                sqlStmt = "SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='"+ jcmsDbName +
                        "' AND TABLE_NAME = 'dbInfo' AND COLUMN_NAME = '"+ fieldName +"'";
                System.out.println(sqlStmt);
                tableData = this.executeQuery(sqlStmt);
                if (tableData.next()) {
                    // Field exists drop it.
                    sqlStmt = "ALTER TABLE "+ jcmsDbName +".dbInfo DROP COLUMN "+ fieldName +";";
                    System.out.println(sqlStmt);
                    this.executeUpdate(sqlStmt);
                }
            }
            
            tableData = this.executeQuery("SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='"+ jcmsDbName +
                    "' AND TABLE_NAME = 'dbInfo' AND COLUMN_NAME = 'webReleaseNum'");
            if (!tableData.next()) {
                // Field does not exist add it.
                this.executeUpdate("ALTER TABLE "+ jcmsDbName +".dbInfo ADD COLUMN webReleaseNum VARCHAR(16) NOT NULL AFTER version;");
            }
            
        } catch (SQLException sqle) {
            throw new JCMSException(sqle.getMessage());
        } 
    }
    
    public void grantMtsAdminFullAccess() throws JCMSException {
        String mtsadminKey = "";
        ResultSet tableData = null;
        ArrayList<String> workgroupUserList = new ArrayList<String>();
        ArrayList<String> functionalAreaList = new ArrayList<String>();
        String privilegeKey = "";
        
        try {
            String jcmsWebDatabaseName = this.inputDTO.getDatabaseName();       // standard jcms web database name
            
            // Get mtsadmin primary key
            tableData = this.executeQuery("SELECT * FROM `"+ jcmsWebDatabaseName +"`.`User` WHERE UserName = 'mtsadmin';");
            if (tableData.next()) {
                mtsadminKey = tableData.getString("_User_key");
                
                // Get privilege key
                tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Privilege WHERE Privilege = 'Write';");
                if (!tableData.next()) throw new JCMSException("\nPrivilege not found.");
                privilegeKey = tableData.getString("_Privilege_key");
                if (privilegeKey.length() == 0) throw new JCMSException("\nDid not find write privilege.");
                
                // Get all mtsadmin WorkgroupUser associations
                tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".WorkgroupUser WHERE _User_key = "+ mtsadminKey);
                while (tableData.next()) {
                    workgroupUserList.add(tableData.getString("_WorkgroupUser_key"));
                }
                if (workgroupUserList.size() == 0) throw new JCMSException("\nDid not find any workgroup users for mtsadmin.");
                
                String sqlStmt = "";
                for (String wuKey : workgroupUserList) {
                    // Get all functional areas where mtsadmin is not already associated with mtsadmin workgroup user
                    tableData = this.executeQuery("SELECT fa._FunctionalArea_key "
                            + " FROM "+ jcmsWebDatabaseName +".FunctionalArea fa "
                            + " WHERE NOT EXISTS "
                            + "   (SELECT 1 FROM "+ jcmsWebDatabaseName +".WorkgroupUserFunctionalArea wufa "
                            + "    WHERE wufa._FunctionalArea_key = fa._FunctionalArea_key "
                            + "      AND wufa._WorkgroupUser_key = "+ wuKey +")");
                    while (tableData.next()) {
                        functionalAreaList.add(tableData.getString("_FunctionalArea_key"));
                    }
                    
                    // Add missing mtsadmin user and functional area associations.
                    for (String faKey : functionalAreaList) {
                        sqlStmt = " INSERT INTO "+ jcmsWebDatabaseName +".WorkgroupUserFunctionalArea "
                                + " (_FunctionalArea_key,_WorkgroupUser_key,_Privilege_key,CreatedBy,DateCreated,ModifiedBy,DateModified,Version)"
                                + " VALUES"
                                + " ("+ faKey +","+ wuKey +","+ privilegeKey +",'JCMSWeb Installer',now(),'JCMSWeb Installer',now(),1);";
                        this.executeUpdate(sqlStmt);
                    }
                    functionalAreaList.clear();
                }
                
            }
        } catch (SQLException sqle) {
            throw new JCMSException(getLogIt().logMessage(Severity.WARN, "\nFailed to grant mtsadmin access all roles. "  +
                "\nSQL Exception detail:\n"+ sqle.getMessage()));
        } catch (Exception e) {
            throw new JCMSException(getLogIt().logMessage(Severity.WARN, "\nFailed to grant mtsadmin access all roles. "  +
                "\nException detail:\n"+ e.getMessage()));
        }
        
        
    }
   
    public String setupJCMSWebAccounts() throws JCMSException {
        String msg = "";
        PropertiesDTO propertiesDTO = new PropertiesDTO();
        try {
            ResultSet tableData = null;
            String jcmsWebDatabaseName = this.inputDTO.getDatabaseName();       // standard jcms web database name
            
    /*
     *  Is there a JCMS Web Center in the jcmsweb_db ?
     */
            // Get a list of Centers
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Center;");
            String jcmsWebCenter = "";
            boolean foundJCMSWebCenter = false;

            // Move to the first of the list.
            while (tableData.next())
            {
                jcmsWebCenter = tableData.getString("Center");
                if (jcmsWebCenter.compareTo("JCMS Web") == 0) {
                    foundJCMSWebCenter = true;
                    break;
                }
            }

            if (foundJCMSWebCenter) {
                msg += getLogIt().logMessage(Severity.INFO, "JCMS Web Center found: " + jcmsWebCenter);
            } else {  // Create Center
                String createCenterStatement = "INSERT INTO "+ jcmsWebDatabaseName +".Center (Center,IsActive,CreatedBy,DateCreated,ModifiedBy,DateModified,Version) "
                        + "VALUES ('JCMS Web',1,'JCMSWeb Installer',now(),'JCMSWeb Installer',now(),1);";
                if (this.executeUpdate(createCenterStatement) == 1 ) {
                    msg += getLogIt().logMessage(Severity.INFO, "JCMS Web Center Created! ");
                } else { // error
                    throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not create JCMSWeb Center, " + jcmsWebCenter
                        + "\nDatbase user may not have administrator privileges."));
                }
            }

    /*
     * Create Workgroups out of owners
     *
     */
            // Get a list of Centers
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Center");
            int jcmsWebCenterKey = -1;

            // Move to the first of the list.
            while (tableData.next())
            {
                jcmsWebCenter = tableData.getString("Center");
                if (jcmsWebCenter.compareTo("JCMS Web") == 0) {
                    jcmsWebCenterKey = tableData.getInt("_Center_key");
                    break;
                }
            }

            if ( jcmsWebCenterKey == -1 ) {
                throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not find the key for JCMS Web!"));
            }

            // Get list of workgroups in the "JCMS Web Center"
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Workgroup");
            ArrayList<String> wgList = new ArrayList<String>();

            // Move to the first of the list.
            // Add WorkgroupName to a list.
            while (tableData.next()) {
                    wgList.add(tableData.getString("WorkgroupName") );
            }

            // Get list of Owners 
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Owner");
            ArrayList<String> oList = new ArrayList<String>();

            // Add WorkgroupName to a list.
            while (tableData.next()) {
                oList.add(tableData.getString("owner"));
            }

            // If there is an Owner that is not in Workgroup Insert, Else OKay.
            // Go thru list of Workgroups ...
            boolean inList = false;
            for ( String o : oList ){
                inList = false;
                for (String wg :  wgList ) {
                    if (o.compareTo(wg) == 0){
                        inList = true;
                        continue;
                    }
                }
                if (!inList) {
                    // If not there Insert into workgroup.
                    String createWkGrpStatement = "INSERT INTO "+ jcmsWebDatabaseName +".Workgroup  (`_Center_key`, `WorkgroupName`, `isActive`, `CreatedBy`, `DateCreated`,  `ModifiedBy`, `DateModified`, `Version`) VALUES ( " +
                            Integer.toString(jcmsWebCenterKey).trim() +
                            " , '" +
                            o.trim() +
                            "' , " +
                            "1,'JCMSWeb Installer',now(),'JCMSWeb Installer',now(),1);";
                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println(createWkGrpStatement);

                    if (1 == this.executeUpdate(createWkGrpStatement)) { // user created
                        msg += getLogIt().logMessage(Severity.INFO, "JCMS Web workgroup " + o.trim() + " was created!");
                    } else { // error
                        throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not create workgroup "  + o +
                            "\nDatbase user may not have administrator privileges."));
                    }
                }
            }
            
    /*
     *  Get the key for the first Workgroup/Owner use it as the default workgroup for mtsadmin.
     */
            int mtsAdminDefaultWorkgroupKey = -1;
            // Get a list of Centers
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".Workgroup WHERE WorkgroupName = '" + oList.get(0) + "';");

            // Move to the first of the list.
            while (tableData.next())
            {
                mtsAdminDefaultWorkgroupKey = tableData.getInt("_Workgroup_key");
                break;
            }

    /*
     * Lookfor and/or Make the mtsadmin user
     *
     */
            // Get the mtsadmin user!
            String getUserStatement = "SELECT * FROM `"+ jcmsWebDatabaseName +"`.`User` WHERE UserName = 'mtsadmin' AND NetworkID is NULL;";
            tableData = this.executeQuery(getUserStatement);
            int jcmsAdminUserKey = -1;
            String jcmsWebAdmin;

            // Move to the first of the list.
            while (tableData.next())
            {
                jcmsWebAdmin = tableData.getString("UserName");
                if ( 0 == jcmsWebAdmin.compareTo("mtsadmin") ) {
                    jcmsAdminUserKey = tableData.getInt("_User_key");
                    continue;
                }
            }

            if (-1 != jcmsAdminUserKey) {
                msg += getLogIt().logMessage(Severity.INFO, "mtsadmin User exists!");
            } else { // error
                msg += getLogIt().logMessage(Severity.WARN, "Can not find user mtsadmin") ;
                // If not there Insert into User table.
                // -- MTS Admin pw changeMe
                String createAdminUserStatement = "INSERT INTO `"+ jcmsWebDatabaseName +"`.`User`  " +
                        "(`FirstName`, `LastName`,`IsActive`, " +
                        " `CreatedBy`,`DateCreated`,`ModifiedBy`, " +
                        "`DateModified`,`Version`,`_DefaultWorkgroup_key`, " +
                        "`IsMasterAdministrator`,`UserName`,`Password_`,`passwordChangedDate`)  " +
                        " VALUES ( " +
                        "'JCMS','Admin',1, " +
                        "'JCMS Web Install', now(), " +
                        "'JCMS Web Install', now(), " +
                        " 1, " +
                        Integer.toString(mtsAdminDefaultWorkgroupKey) +
                        ", 1, " +
                        "'mtsadmin', '9kLsWK8QdzaSh08Xqrckli8WAtkuz2vK/Lp1vg72gMY=', now());";
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println(createAdminUserStatement);

                if (1 == this.executeUpdate(createAdminUserStatement) ) { // user created
                    msg += getLogIt().logMessage(Severity.INFO, "\nThe user mtsadmin was Created!");
                } else { // error
                    throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not create the mtsadmin user. "  +
                        "\nDatabase user may not have administrator privileges."));
                }
            }

            // Make sure we have user and get the user Key.
            if (-1 != jcmsAdminUserKey) {

            } else {
                // query for admin user key
                tableData = this.executeQuery(getUserStatement);

                // Move to the first of the list.
                while (tableData.next())
                {
                    jcmsWebAdmin = tableData.getString("UserName");
                    if ( 0 == jcmsWebAdmin.compareTo("mtsadmin") ) {
                        jcmsAdminUserKey = tableData.getInt("_User_key");
                        continue;
                    }
                }

                if ( -1 == jcmsAdminUserKey ) {
                    throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nThe user mtsadmin has not be created. "  +
                            "\nDatbase user may not have administrator privileges."));
                }
            }

    /*
     * Lookfor and/or assign mtsadmin to workgroups
     *
     */
            // jcmsWebCenterKey
            // Get all the Workgroups in center "JCMS Web"
            String wrkGroupKeys = " SELECT " +
                " w._Workgroup_key, " +
                " w.WorkgroupName " +
                " FROM "+ jcmsWebDatabaseName +".Workgroup AS w" +
                " LEFT JOIN "+ jcmsWebDatabaseName +".WorkgroupUser AS wu on w._Workgroup_key = wu._Workgroup_key " +
                " WHERE " +
                " w._Center_key = " + Integer.toString(jcmsWebCenterKey) + ";" ;

            if (propertiesDTO.getJCMSDebugConsole())
                System.out.println(wrkGroupKeys);
            
            // query for admin user key
            tableData = this.executeQuery(wrkGroupKeys);
            int iWGKey = -1;
            ArrayList<Integer> wrkGroupList = new ArrayList<Integer>();

            // Move to the first of the list.
            while (tableData.next())
            {
                iWGKey = tableData.getInt("_Workgroup_key");
                wrkGroupList.add(iWGKey);
            }

            if ( 0 == wrkGroupList.size()) {
                throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nNo workgroups/Owners to add mtsAdmin."));
            }

            // For each workgroup lookfor a user and add if not found.
            for ( Integer i : wrkGroupList ){
                // Is The MTS Admin in this workgroup?
                String findAdminWorkgroup = " SELECT * FROM "+ jcmsWebDatabaseName +".WorkgroupUser Where _Workgroup_key = " + Integer.toString(i) + " AND _User_key = " + Integer.toString(jcmsAdminUserKey) + " ; ";
                if (propertiesDTO.getJCMSDebugConsole())
                    System.out.println(findAdminWorkgroup);
                // query for admin user key
                tableData = this.executeQuery(findAdminWorkgroup);

                // If so great
                if (tableData.next() ) {
                    // If not insert
                } else {
                    String createAdminWorkGroupStatement = "INSERT INTO `"+ jcmsWebDatabaseName +"`.`WorkgroupUser` " +
                            " ( `_Workgroup_key`, `_User_key`, `CreatedBy`, `DateCreated`, `ModifiedBy`, `DateModified`, `Version`) " +
                            " VALUES (   " +
                            Integer.toString(i) + " , " +
                            Integer.toString(jcmsAdminUserKey) + " , " +
                            "'JCMS Web Installer', now() , 'JCMS Web Installer', now(), 1 );" ;
                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println( createAdminWorkGroupStatement);
                    if (1 == this.executeUpdate(createAdminWorkGroupStatement) ) { // user created
                        msg += getLogIt().logMessage(Severity.INFO, "\nThe user mtsadmin was added to Workgroup " + Integer.toString(i) +  "!");
                    } else { // error
                        throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not add mtsadmin to WorkGroupUser " + Integer.toString(i) +
                            "\nDatbase user may not have administrator privileges."));
                    }
                }
            }

    /*
     * Lookfor and/or creat admin privileges for the mtsadmin user
     *
     */

    // -- Find Privilege
    //
            int privilegeAdministratorKey = -1;
            // query for admin user key
            tableData = this.executeQuery(" SELECT * FROM "+ jcmsWebDatabaseName +".Privilege WHERE Privilege = 'Write'; ");
            // Move to the first of the list.
            while (tableData.next())
            {
                privilegeAdministratorKey = tableData.getInt("_Privilege_key");
            }

    // -- Find Functional Area
    //
            int functionalAreaAdministratorKey = -1;
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".FunctionalArea WHERE FunctionalArea = 'Administration';");
            // Move to the first of the list.
            while (tableData.next())
            {
                functionalAreaAdministratorKey = tableData.getInt("_FunctionalArea_key");
            }

    // -- WorkgroupUser Keys
    // 
            wrkGroupList.clear();
            tableData = this.executeQuery("SELECT * FROM "+ jcmsWebDatabaseName +".WorkgroupUser WHERE _User_key = " + Integer.toString(jcmsAdminUserKey) + ";");
            // Move to the first of the list.
            while (tableData.next())
            {
                wrkGroupList.add(tableData.getInt("_WorkgroupUser_key"));
            }

    // -- WorkgroupUserFunctionalArea
            for ( int i : wrkGroupList ) {
               // Is there a wrkGroupList for this user
               String findAdminWorkgroupUserFunctionalArea = "SELECT * FROM "+ jcmsWebDatabaseName +".WorkgroupUserFunctionalArea " +
                    " WHERE WorkgroupUserFunctionalArea._WorkgroupUser_key = " + Integer.toString(i) + " ; ";
               if (propertiesDTO.getJCMSDebugConsole())
                   System.out.println(findAdminWorkgroupUserFunctionalArea);
               tableData = this.executeQuery(findAdminWorkgroupUserFunctionalArea);
                // If so great
                if (tableData.next() ) {
                // If not insert
                } else {
                   // If not insert
                   String insertWorkgroupFunctionalArea = "INSERT INTO `"+ jcmsWebDatabaseName +"`.`WorkgroupUserFunctionalArea` ( `_FunctionalArea_key`, `_WorkgroupUser_key`, `_Privilege_key`, `CreatedBy`, `DateCreated`, `ModifiedBy`, `DateModified`, `Version`) VALUES ( " +
                              Integer.toString(functionalAreaAdministratorKey)  + " ,  " +
                              Integer.toString(i)  + " ,  " +
                              Integer.toString(privilegeAdministratorKey)  + " ,  " +
                            "'JCMSWeb Installer', now(), 'JCMSWeb Installer', now(), 1 );";

                    if (propertiesDTO.getJCMSDebugConsole())
                        System.out.println( insertWorkgroupFunctionalArea);

                    if (1 == this.executeUpdate(insertWorkgroupFunctionalArea) ) { // user created
                        msg += getLogIt().logMessage(Severity.INFO, "\nThe user mtsadmin was Added to FunctionalAreaWorkgroupUser!");
                    } else { // error
                        throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not add mtsadmin to WorkGroupUser " +
                            "\nDatbase user may not have administrator privileges."));
                    }
                }
           }
        } catch (Exception e) {
            throw new JCMSException(getLogIt().logMessage(Severity.ERROR, "\nCan not create JCMS Web accounts."  +
                "\nDatabase user may not have administrator privileges. \nException detail:\n"+ e.getMessage()));
        }
        
        return msg;
    }
    

    /**
     * Get version of JCMS, but first, due to GUDM migration, need to find out
     * if dbinfo table is Dbinfo (pre 4.2) or dbInfo (post 4.1)
     * @return 
     */
    public Boolean isPost41() throws JCMSException {
        Boolean post41 = false;
        try {
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery("SHOW TABLES FROM "+ this.inputDTO.getDatabaseName() +";");
            String tableName = "";

            // Create Collection to return.
            while (tableData.next())
            {
                tableName = tableData.getString("Tables_in_"+ this.inputDTO.getDatabaseName());
                if (tableName.equalsIgnoreCase("dbInfo")) {
                    if (tableName.equals("dbInfo"))
                        post41 = true;
                    break;
                };
            }
        } catch (SQLException e) {
            throw new JCMSException("Error determining which JCMS release number this is.  " + e.getMessage());
        }
        return post41;
    }
    
    public String getReleaseNum() throws JCMSException {
        String version = "";
        String query = "";
        String dbVers = "";
        
        try {
            if (this.isPost41()) {
                query = "SELECT releaseNum, dbVers FROM "+ this.getInputDTO().getDatabaseName() +".dbInfo;";            }
            else {
                query = "SELECT releaseNum, dbVers FROM "+ this.getInputDTO().getDatabaseName() +".Dbinfo;";
            }
            
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(query);

            // Create Collection to return.
            if (tableData.next())
            {
                dbVers = tableData.getString("dbVers");
                if (Integer.parseInt(dbVers) >= 102)
                    version = dbVers;
                else 
                    version = tableData.getString("releaseNum");
            }
        } catch (SQLException e) {
            throw new JCMSException("Failed to find application release number.  " + e.getMessage());
        }
        return version;
    }
    
    public void removeGenotypeDocumentIndex() throws JCMSException {
        String strStmt = "";
        int count = 0;
        try {
            strStmt = " SELECT COUNT(1) FROM information_schema.statistics "
                    + " WHERE table_schema = '"+ this.getInputDTO().getDatabaseName() +"' "
                    + " AND table_name = 'GenotypeDocument' "
                    + " AND index_name = '_GenotypeDocument_key' ;";
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(strStmt);

            // Create Collection to return.
            if (tableData.next())
            {
                if (tableData.getString("COUNT(1)").equalsIgnoreCase("1")) {
                    // Drop index on _GenotypeDocument_key
                    strStmt = " ALTER TABLE "+ this.getInputDTO().getDatabaseName() +".GenotypeDocument"
                            + " DROP INDEX _GenotypeDocument_key ; ";
                    sqlStmt = dbConnectRemote.createStatement();
                    count = sqlStmt.executeUpdate(strStmt);
                }
            }
        } catch (SQLException e) {
            throw new JCMSException("Failed to remove GenotypeDocument index.  " + e.getMessage());
        }
    }
       
    public Boolean hasCenter() throws JCMSException {
        try {
            Statement sqlStmt = dbConnectRemote.createStatement();
            ResultSet tableData = sqlStmt.executeQuery("SELECT * FROM "+ this.getInputDTO().getDatabaseName() +".Center;");
            if (tableData.next()) 
                return true;
            else 
                return false;
        } catch (SQLException e) {
            throw new JCMSException("\nFailed to determine if there are any Centers.  \nException detail: "+ e.getMessage());
        }
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the pd
     */
    public String getPd() {
        return pd;
    }

    /**
     * @param pd the pd to set
     */
    public void setPd(String pd) {
        this.pd = pd;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the inputDTO
     */
    public InputDTO getInputDTO() {
        return inputDTO;
    }

    /**
     * @param inputDTO the inputDTO to set
     */
    public void setInputDTO(InputDTO inputDTO) {
        this.inputDTO = inputDTO;
    }

    /**
     * @return the logIt
     */
    public LogIt getLogIt() {
        return logIt;
    }

    /* 
     * What is the jcms_db version
     */
    public void jcmsVersion() {
            // What is the version of jcmsDB

    }
    
}
