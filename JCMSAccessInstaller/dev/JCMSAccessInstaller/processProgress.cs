//***
//Copyright (c) 2015 The Jackson Laboratory
//
//This is free software: you can redistribute it and/or modify it 
//under the terms of the GNU General Public License as published by  
//the Free Software Foundation, either version 3 of the License, or  
//(at your option) any later version.
// 
//This software is distributed in the hope that it will be useful,  
//but WITHOUT ANY WARRANTY; without even the implied warranty of 
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
//General Public License for more details.
//
//You should have received a copy of the GNU General Public License 
//along with this software.  If not, see <http://www.gnu.org/licenses/>.
//***
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.IO;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using System.Runtime.InteropServices;


namespace JCMSAccessInstaller
{
    public partial class processProgress : Form
    {
        [DllImport("C:\\Windows\\System32\\ODBCCP32.dll")]
        private static extern bool SQLConfigDataSource(IntPtr parent, int request, string driver, string attributes);


        //should remain system constant - names of commands to do mysql stuff
        string MySQLFileName = "mysql.exe";
        string MySQLDumpFileName = "mysqldump.exe";

        string interfaceName = "interface"; //name of interface file in JCMSItems
        string JCMSItemsPath = "./JCMSItems/"; //location of stuff you need for JCMS
        string JCMSExtrasPath = "./JCMSExtras/";
        string docsPath = "./Docs/";
        string backupName = "";
        string now = ""; //time, unique identifier
        string where = ""; //directory of selected installation etc.
        bool processSuccess = true;
        string remainingSteps = "";
        StreamWriter swLog;

        //vary from install to install/upgrade to upgrade
        string host = "";
        string port = "";
        string username = "";
        string password = "";
        string databaseName = "";
        string interfacePath = "";

        public processProgress(string theHost, string thePort, string theUsername, string thePassword, string theDatabaseName, string theInterfacePath)
        {
            InitializeComponent();

            host = theHost;
            port = thePort;
            username = theUsername;
            password = thePassword;
            databaseName = theDatabaseName;
            interfacePath = theInterfacePath;

            this.Shown += new EventHandler(processProgress_Shown);
        }

        private void finishButton_Click(object sender, EventArgs e)
        {
            this.Hide();
            installSuccess successPage = new installSuccess(processSuccess, remainingSteps);
            successPage.Show();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        private void processProgress_Shown(object sender, EventArgs e)
        {
            now = DateTime.Now.ToString("yyyyMMddHHmmssfff");
            if (programVariables.getInstall())
            {
                swLog = new StreamWriter(System.IO.Path.GetDirectoryName(interfacePath) + string.Format("\\install{0}.txt", now));
                installJCMS();
            }
            else
            {
                swLog = new StreamWriter(System.IO.Path.GetDirectoryName(interfacePath) + string.Format("\\upgrade{0}.txt", now));
                upgradeJCMS();
            }
            swLog.Flush();
            swLog.Close();
        }

        private void installJCMS()
        {
            WriteOutput("Beginning JCMS installation in directory " + interfacePath + " with associated database " + databaseName);

            try
            {
                /*
                string createSchemaString = "CREATE SCHEMA " + databaseName + ";";
                executeNonQuery(createSchemaString);
                */


                //create Database
                Process p = new Process();
                WriteOutput("Creating schema " + databaseName);
                string createSchemaString = "CREATE SCHEMA " + databaseName + ";";
                string createSchemaArgs = string.Format("--user={0} --password={1} --host={2} --execute=\"{3}\"", username, password, host, createSchemaString);
                p.StartInfo.FileName = MySQLFileName;
                p.StartInfo.Arguments = createSchemaArgs;
                p.StartInfo.RedirectStandardOutput = true;
                p.StartInfo.RedirectStandardInput = true;
                p.StartInfo.RedirectStandardError = true;
                p.StartInfo.UseShellExecute = false;
                p.StartInfo.CreateNoWindow = true;
                p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;

                p.Start();
                string createSchemaOutput = p.StandardOutput.ReadToEnd();
                //get and write errors
                string createSchemaErrors = p.StandardError.ReadToEnd();
                p.WaitForExit();

                if (p.ExitCode != 0)
                {
                    WriteOutput(createSchemaErrors);
                    WriteOutput("There were errors during database creation and installation could not be finished.");
                    p.Close();
                    processSuccess = false;
                }
                else
                {
                    //the database was successfully created.
                    WriteOutput(createSchemaOutput);

                    //populate database
                    //StreamReader reader = new StreamReader("./JCMSItems/jcms_db.sql");
                    p = new Process();
                    WriteOutput("Populating database " + databaseName + ", this process may take a minute.");
                    p.StartInfo.FileName = MySQLFileName;
                    //  p.StartInfo.Arguments = "-u" + username + " -p" + password + " " + databaseName;
                    string installString = string.Format("--user={0} --password={1} --database={2} --port={3} --host={4} --verbose --execute=\"SOURCE {5}\"",
                                        username, password, databaseName, port, host, System.IO.Path.GetFullPath("./JCMSItems/jcms_db.sql"));
                    p.StartInfo.Arguments = installString;

                    p.StartInfo.RedirectStandardOutput = true;
                    p.StartInfo.RedirectStandardInput = true;
                    p.StartInfo.RedirectStandardError = true;
                    p.StartInfo.UseShellExecute = false;
                    p.StartInfo.CreateNoWindow = true;
                    p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;

                    p.Start();

                    /*                while(reader.EndOfStream == false){
                                        p.StandardInput.WriteLine(reader.ReadLine());
                                    }
                                    p.StandardInput.Close();*/

                    //get and write output
                    string output = p.StandardOutput.ReadToEnd();

                    //get and write errors
                    string errors = p.StandardError.ReadToEnd();
                    p.WaitForExit();
                    //problems
                    if (p.ExitCode != 0)
                    {
                        WriteOutput(errors);
                        WriteOutput("There were errors during database creation and installation could not be finished.");
                        p.Close();
                        processSuccess = false;
                    }
                    else //we good
                    {
                        p = new Process();
                        p.StartInfo.FileName = MySQLFileName;

                        //prevent the command window from popping up
                        p.StartInfo.UseShellExecute = false;
                        p.StartInfo.CreateNoWindow = true;
                        p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                        p.StartInfo.RedirectStandardOutput = true;
                        p.StartInfo.RedirectStandardError = true;
                        //cannot remove genotypeDocumentkey index w/o knowing DB name so cannot be SQL scripted before user input
                        //see remove genotypedocumentindex method for further note.

                        LinkedList<string> installScripts = programVariables.getInstallScripts();

                        // Backup database did not export procedures and functions.
                        // Create procedure just in case it is missing.
                        installScripts.AddFirst("dropConstraintWithoutName.sql");

                        /* mysql --user=root --password --database=jcms_db --host=localhost --verbose --execute="SOURCE j4.4.0-j4.5.0.mysql.sql"
                         * Source file is the SQL commands to upgrade to that version i.e. j4.4.0-j4.5.0.mysql.sql */
                        foreach (string fileName in installScripts)
                        {
                            string fileLocation = "." + System.IO.Path.DirectorySeparatorChar + "UpgradeScripts" + System.IO.Path.DirectorySeparatorChar + fileName;
                            string upgradeString = string.Format("--user={0} --password={1} --database={2} --port={3} --host={4} --verbose --execute=\"SOURCE {5}\"",
                                username, password, databaseName, port, host, fileLocation);
                            p.StartInfo.Arguments = upgradeString;
                            WriteOutput("Executing upgrade script: " + fileName);
                            p.Start();

                            //write output...    
                            WriteOutput(p.StandardOutput.ReadToEnd());

                            string errMessage = p.StandardError.ReadToEnd();

                            if (p.ExitCode != 0)
                            {
                                WriteOutput(errMessage);
                                processSuccess = false;
                                WriteOutput("Installation failure...");
                                break;
                            }
                            p.WaitForExit();
                        }
                        if (processSuccess)
                        {
                            /* At this point an installation has been 'successful' even if something after fails... */
                            //need to fill in the DbSetup table with the mysql username, hostname, port #, and database name
                            string userIDUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_USER_ID';", databaseName, username);
                            string serverUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_SERVER';", databaseName, host);
                            string databaseNameUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_DATABASE_NAME';", databaseName, databaseName);
                            this.executeNonQuery(userIDUpdate);
                            this.executeNonQuery(serverUpdate);
                            this.executeNonQuery(databaseNameUpdate);
                            
                            WriteOutput(output);
                            WriteOutput("Database creation successful");
                            
                            p.Close();
                            programVariables.setInstallSuccess(true);
                            WriteOutput("Creating JCMS interface.");

                            string sourceFile = System.IO.Path.Combine(JCMSItemsPath, interfaceName);

                            System.IO.File.Copy(@sourceFile, @interfacePath, true);

                            WriteOutput("JCMS interface created in directory " + interfacePath);

                            //get the directory of interface
                            string interfaceDirectory = System.IO.Path.GetDirectoryName(interfacePath);
                            //get the directory above the interface
                            string parent = Directory.GetParent(interfaceDirectory).FullName;


                            //create data directory
                            WriteOutput("Creating data directory in " + parent);
                            string dataDir = parent + System.IO.Path.DirectorySeparatorChar + "data";
                            Directory.CreateDirectory(dataDir);
                            //create docs directory
                            WriteOutput("Creating docs dir in " + parent);
                            string docsDir = parent + System.IO.Path.DirectorySeparatorChar + "docs";
                            Directory.CreateDirectory(docsDir);


                            //copy docs to docs directory
                            if (System.IO.Directory.Exists(docsDir))
                            {
                                string[] docsFiles = System.IO.Directory.GetFiles(docsPath);
                                foreach (string source in docsFiles)
                                {
                                    string documentFileName = System.IO.Path.GetFileName(source);
                                    string destFile = docsDir + System.IO.Path.DirectorySeparatorChar + documentFileName;
                                    if (!System.IO.File.Exists(@destFile))
                                    {
                                        WriteOutput("Copying " + source + " to " + docsDir);
                                        System.IO.File.Copy(@source, @destFile);
                                    }
                                    else
                                    {
                                        WriteOutput(string.Format("Document {0} already found in {1}", documentFileName, docsDir));
                                    }
                                }
                            }

                            //copy JCMSExtras
                            string[] extrasFiles = System.IO.Directory.GetFiles(JCMSExtrasPath);
                            foreach (string source in extrasFiles)
                            {
                                WriteOutput("Copying " + source + " to " + interfacePath);
                                string extraFileName = System.IO.Path.GetFileName(source);
                                string destFile = interfaceDirectory + System.IO.Path.DirectorySeparatorChar + extraFileName;
                                if (!System.IO.File.Exists(@destFile))
                                {
                                    System.IO.File.Copy(@source, @destFile);
                                }
                                else
                                {
                                    WriteOutput(string.Format("File {0} already found in {1}", extraFileName, interfaceDirectory));
                                }
                            }
                            linkTables();
                        }
                        else
                        {
                            //drop schema
                            installRollback();
                        }
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                WriteOutput("A file not found exception has been detected: " + e.Message + " This is likely caused by your MySQL commands not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Win32Exception e)
            {
                processSuccess = false;
                WriteOutput("There was an error during installation. This is likely caused by MySQL not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help. "
                    + "If MySQL is on your path, try rerunning the application.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (MySqlException e)
            {
                processSuccess = false;
                WriteOutput("There is a problem with MySQL: " + e.Message + ". Please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "The installation was not successful. Please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Exception e)
            {
                WriteOutput("There was an exception during install: " + e.Message);
                remainingSteps = "Database install was successful but the interface setup could not be completed, please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            finally
            {
                finishButton.Enabled = true;
            }
        }

        private void upgradeJCMS()
        {
            WriteOutput("Beginning JCMS upgrade to interface " + interfacePath + " with associated database " + databaseName);
            
            //directory location of interface - this is where back lives
            where = System.IO.Path.GetDirectoryName(interfacePath);

            try{
                //only proceed with upgrade if backup was successful!
                if (backup())
                {
                    WriteOutput("Backup successful. Can be found in: " + backupName);
                    //STEP 2: Upgrade database (only continue if upgrade was successful)
                    if (upgrade())
                    {
                        programVariables.setUpgradeSuccess(true);
                        if (renameOldInterface())
                        {
                            if (copyLatestInterface())
                            {
                                if (!copyDocsAndExtras())
                                {
                                    remainingSteps = remainingSteps + " Docs and data directory could not be created. For help and instructions please visit " +
                                    " our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx";
                                }
                                linkTables();
                            }
                            //case that copy of latest interface failed
                            else
                            {
                                remainingSteps = "Your database upgrade was successful, but interface set up could not be completed and failed at copying newest interface, these steps will have to be completed manually. "
                                             + "Please consult your upgrade log in " + interfacePath + " and visit http://colonymanagement.jax.org/support-2/faqs/ or for help visit "
                                             + "http://community.jax.org/jcms_discussion_forum/default.aspx";
                                if (!copyDocsAndExtras())
                                {
                                    remainingSteps = remainingSteps + " Docs and data directory could not be created. For help and instructions please visit " +
                                        " our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx";
                                }
                            }
                        }
                        else //case that rename of interface failed
                        {
                            string fullJCMSItemsPath = System.IO.Path.GetFullPath(JCMSItemsPath);
                            remainingSteps = "Your database upgrade was successful, but interface set up could not be completed and failed at renaming old interface. "
                                             + "These steps will have to be completed manually. "
                                             + "Please consult your upgrade log in " + interfacePath + " and visit http://colonymanagement.jax.org/support-2/faqs/ or for help visit "
                                             + "http://community.jax.org/jcms_discussion_forum/default.aspx";
                            if (!copyDocsAndExtras())
                            {
                                remainingSteps = remainingSteps + " Docs and data directory could not be created. For help and instructions please visit " +
                                    " our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx";
                            }
                        }
                    }
                    else
                    {
                        processSuccess = false;
                    }
                }
                else
                {
                    processSuccess = false;
                }
            }
            catch (Exception e) { 
                WriteOutput(e.Message); 
            }
            finally
            {
                finishButton.Enabled = true;
            } 
        }

        private bool backup()
        {
            bool success = true;
            try
            {
                //directory location of interface - this is where back libes
                string where = System.IO.Path.GetDirectoryName(interfacePath);

                backupName = where + System.IO.Path.DirectorySeparatorChar + databaseName + now + ".sql";

                //STEP 1: Dump (backup)
                Process p = new Process();
                //--database databasename --result-file=user --single-transaction --routines --triggers --user=filename --password=password
                string dumpString = string.Format(@"--database {0} --result-file=""{1}"" --single-transaction --routines --triggers --host={2} --port={3} --user={4} --password={5}",
                    databaseName, backupName, host, port, username, password);

                //correspond to executing mysqldump properly
                p.StartInfo.FileName = MySQLDumpFileName;
                p.StartInfo.Arguments = dumpString;

                //prevent the command window from popping up
                p.StartInfo.UseShellExecute = false;
                p.StartInfo.RedirectStandardError = true;
                p.StartInfo.CreateNoWindow = true;
                p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                WriteOutput("Backing up database " + host + ":" + databaseName + " into " + backupName + " with MySQL user " + username);
                p.Start();
                p.WaitForExit();
                string errorMsg = p.StandardError.ReadToEnd();
           //     Console.WriteLine("Exit Code is: " + p.ExitCode);
               // if (new System.IO.FileInfo(backupName).Length == 0 || !errorMsg.Equals(""))
                if(!(p.ExitCode == 0))
                {
                        WriteOutput("The following error occurred during backup: " + errorMsg);
                        WriteOutput("The upgrader could not begin because there was an error during backup, please see error log in " + interfacePath + " to diagnose the problem.");
                        System.IO.File.Delete(backupName);
                        success = false;
                }
            }
            catch (FileNotFoundException e)
            {
                success = false;
                WriteOutput("A file not found exception has been detected: " + e.Message + " This is likely caused by your MySQL commands not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Win32Exception e)
            {
                success = false;
                WriteOutput("There was an error during backup. This is likely caused by MySQL not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help. "
                    + "If MySQL is on your path, try rerunning the application.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (MySqlException e)
            {
                success = false;
                WriteOutput("There is a problem with MySQL: " + e.Message + ". Please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "The installation was not successful. Please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Exception e)
            {
                WriteOutput("There was an error during backup and upgrade could not continue: " + e.Message);
                success = false;
            }
            return success;
        }

        private bool upgrade()
        {
            Process p = new Process();
            bool success = true;
            bool versionError = false;
            try
            {
                //Get version of JCMS, but first, due to GUDM migration, need to find out if dbinfo table is Dbinfo (pre 4.2) or dbInfo (post 4.1)
                    bool post41 = false;
                    string getTablesQuery = "SHOW TABLES FROM " + databaseName;
                    MySqlDataReader reader = executeMultipleResultQuery(getTablesQuery);
                    while (reader.Read())
                    {
           //             Console.WriteLine(reader[0].ToString());
                        //dbinfo name is in program variables, default value is dbInfo (indicating that version is post 4.1
                        if (reader[0].ToString().Equals(programVariables.getDbInfoName()))
                        {
                            post41 = true;
                        }
                    }

                    string getReleaseNumberQuery = "";
                    string getDbVersQuery = "";
                    if (post41)
                    {
                        getReleaseNumberQuery = "SELECT releaseNum FROM " + databaseName + ".dbInfo;";
                        getDbVersQuery = "SELECT dbVers FROM " + databaseName + ".dbInfo;";
                    }
                    else
                    {
                        getReleaseNumberQuery = "SELECT releaseNum FROM  " + databaseName + ".Dbinfo;";
                        getDbVersQuery = "SELECT dbVers FROM " + databaseName + ".dbInfo;";
                    }

                    //check to make sure user isn't already using latest version...
                    bool alreadyUpgraded = false;
                    string releaseNumber = executeSingleResultQuery(getReleaseNumberQuery);
                    int dbVers = Convert.ToInt32(executeSingleResultQuery(getDbVersQuery));                    
                    
                    if (releaseNumber.Equals(programVariables.getLatestJCMS()))
                    {
                        alreadyUpgraded = true;
                        success = true; // we will copy interface etc
                        WriteOutput("You already have the latest version of the JCMS database that can be upgraded to using this version of the installer." 
                            + " Your interface will now be upgraded and tables linked.");
                    }

                    if (!alreadyUpgraded)
                    {
                        WriteOutput("Selected database release number is: " + releaseNumber + " and database version number is: " + dbVers.ToString() +". Upgrade will now begin.");

                        int removeNum = 0;
                        if(dbVers >= 103){
                            if (programVariables.getUpgradeMap().ContainsKey(dbVers.ToString()))
                            {
                                removeNum = programVariables.getUpgradeMap()[dbVers.ToString()];
                            }
                            else
                            {
                                versionError = true;
                                success = false;
                                WriteOutput("Upgrade methods for JCMS database version " + dbVers.ToString() + " could not be found, and "
                                    + "JCMS upgrades could not be completed.");
                            }
                        }
                        else{
                            if (programVariables.getUpgradeMap().ContainsKey(releaseNumber))
                            {
                                removeNum = programVariables.getUpgradeMap()[releaseNumber];
                            }
                            else
                            {
                                versionError = true;
                                success = false;
                                WriteOutput("Upgrade methods for JCMS version number " + releaseNumber + " could not be found, and "
                                    + "JCMS upgrades could not be completed.");
                            }
                        }

                        //continue as long as user doesn't already have latest version, and the version the wish to upgrade from
                        //exists in the upgradeMap dictionary object (programVariables.upgradeMap())
                        if (!versionError)
                        {
                            //after while loop will contain all the upgrade scripts that need to be run.
                            LinkedList<string> upgradesToRun = programVariables.getUpgradeScripts();

                            while (removeNum != 0)
                            {
                                upgradesToRun.RemoveFirst();
                                removeNum--;
                            }

                            // Backup database did not export procedures and functions.
                            // Create procedure just in case it is missing.
                            upgradesToRun.AddFirst("dropConstraintWithoutName.sql");

                            p = new Process();
                            p.StartInfo.FileName = MySQLFileName;

                            //prevent the command window from popping up
                            p.StartInfo.UseShellExecute = false;
                            p.StartInfo.CreateNoWindow = true;
                            p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                            p.StartInfo.RedirectStandardOutput = true;
                            p.StartInfo.RedirectStandardError = true;
                            //cannot remove genotypeDocumentkey index w/o knowing DB name so cannot be SQL scripted before user input
                            //see remove genotypedocumentindex method for further note.

                            /* mysql --user=root --password --database=jcms_db --host=localhost --verbose --execute="SOURCE j4.4.0-j4.5.0.mysql.sql"
                             * Source file is the SQL commands to upgrade to that version i.e. j4.4.0-j4.5.0.mysql.sql */
                            foreach (string fileName in upgradesToRun)
                            {
                                if (fileName.Equals("j4.1.0-j4.2.0.mysql.sql"))
                                {
                                    removeGenotypeDocumentIndex();
                                }

                                string fileLocation = "." + System.IO.Path.DirectorySeparatorChar + "UpgradeScripts" + System.IO.Path.DirectorySeparatorChar + fileName;
                                string upgradeString = string.Format("--user={0} --password={1} --database={2} --port={3} --host={4} --verbose --execute=\"SOURCE {5}\"",
                                    username, password, databaseName, port, host, fileLocation);
                                p.StartInfo.Arguments = upgradeString;
                                WriteOutput("Executing upgrade script: " + fileName);
                                p.Start();

                                //write output...    
                                WriteOutput(p.StandardOutput.ReadToEnd());
                                
                                string errMessage = p.StandardError.ReadToEnd();
                        //        Console.WriteLine("Exit Code is: " + p.ExitCode);
                             //   if (!errMessage.Equals(""))
                                if(p.ExitCode != 0)
                                {
                                        WriteOutput(errMessage);
                                        success = false;
                                        WriteOutput("Rolling back to original database...");
                                        rollback();
                                        break;
                                }
                                p.WaitForExit();
                            }
                            //need to fill in the DbSetup table with the mysql username, hostname, port #, and database name
                            string userIDUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_USER_ID';", databaseName, username);
                            string serverUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_SERVER';", databaseName, host);
                            string databaseNameUpdate = string.Format("UPDATE {0}.DbSetup SET MTSValue = '{1}' WHERE MTSVar = 'JCMS_MYSQL_DATABASE_NAME';", databaseName, databaseName); 
                            this.executeNonQuery(userIDUpdate);
                            this.executeNonQuery(serverUpdate);
                            this.executeNonQuery(databaseNameUpdate);
                            
                        }
                    }
            }
            catch (FileNotFoundException e)
            {
                WriteOutput("A file not found exception has been detected: " + e.Message + " This is likely caused by your MySQL commands not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Win32Exception e)
            {
                success = false;
                WriteOutput("There was an error during upgrade. This is likely caused by MySQL not existing on the path. Please visit "
                    + "our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help. "
                    + "If MySQL is on your path, try rerunning the application.");
                remainingSteps = "Installation failed, please add MySQL to the system path. For help please "
                    + "visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (MySqlException e)
            {
                success = false;
                WriteOutput("There is a problem with MySQL: " + e.Message + ". Please see your install log in " + interfacePath + " and"
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.");
                remainingSteps = "The installation was not successful. Please see your install log in " + interfacePath + " and "
                    + " visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
            }
            catch (Exception e)
            {
                WriteOutput("There was an error during the upgrade process and upgrade could not continue: " + e.Message);
                success = false;
            }
            return success;
        }

        private bool renameOldInterface()
        {
            bool success = true;
            string sourceFile = interfacePath;
            string selectedInterfaceName = System.IO.Path.GetFileNameWithoutExtension(interfacePath);
            string destFile = where + System.IO.Path.DirectorySeparatorChar + selectedInterfaceName + now + ".mdb";
            try
            {
                //STEP 3: Copy old interface with new name to preserve old forms
                WriteOutput("Renaming old interface.");

                System.IO.File.Move(@sourceFile, @destFile);
                WriteOutput("Old interface can now be found as " + destFile);
            }
            catch (Exception e)
            {
                WriteOutput("Old JCMS Interface could not be renamed: " + e.Message + ", this step will have to be performed manually along with "
                + "copying over latest JCMS interface.");
                success = false;
            }
            return success;
        }

        private bool copyLatestInterface()
        {
            bool success = true;
            string sourceFile = interfacePath;
            string destFile = where + System.IO.Path.DirectorySeparatorChar + System.IO.Path.GetFileName(sourceFile); ;
            try
            {
                sourceFile = System.IO.Path.Combine(JCMSItemsPath, interfaceName);
                System.IO.File.Copy(@sourceFile, @destFile);
                WriteOutput("New interface can now be found as " + destFile);
            }
            catch (Exception e)
            {
                WriteOutput("Latest interface version could not be moved to requested directory: " + e.Message
                + " this step will have to be completed manually.");
                success = false;
            }
            return success;
        }

        private bool copyDocsAndExtras()
        {
            try
            {
                //get the directory of interface
                string interfaceDirectory = System.IO.Path.GetDirectoryName(interfacePath);
                //get the directory above the interface
                string parent = Directory.GetParent(interfaceDirectory).FullName;


                //create data directory
                WriteOutput("Creating data directory in " + parent);
                string dataDir = parent + System.IO.Path.DirectorySeparatorChar + "data";
                Directory.CreateDirectory(dataDir);
                //create docs directory
                WriteOutput("Creating docs dir in " + parent);
                string docsDir = parent + System.IO.Path.DirectorySeparatorChar + "docs";
                Directory.CreateDirectory(docsDir);


                //copy docs to docs directory
                if (System.IO.Directory.Exists(docsDir))
                {
                    string[] docsFiles = System.IO.Directory.GetFiles(docsPath);
                    foreach (string source in docsFiles)
                    {
                        string documentFileName = System.IO.Path.GetFileName(source);
                        string destFile = docsDir + System.IO.Path.DirectorySeparatorChar + documentFileName;
                        if (!System.IO.File.Exists(@destFile))
                        {
                            WriteOutput("Copying " + source + " to " + docsDir);
                            System.IO.File.Copy(@source, @destFile);
                        }//if documents already exist, don't replace them, create docs with timestamp
                        else
                        {
                            destFile = docsDir + System.IO.Path.DirectorySeparatorChar + System.IO.Path.GetFileNameWithoutExtension(documentFileName) + now + System.IO.Path.GetExtension(documentFileName);
                            System.IO.File.Copy(@source, @destFile);
                            WriteOutput("Copying " + source + " to " + docsDir + " as " + System.IO.Path.GetFileName(destFile));
                        }
                    }
                }

                //copy JCMSExtras
                string[] extrasFiles = System.IO.Directory.GetFiles(JCMSExtrasPath);
                foreach (string source in extrasFiles)
                {
                    WriteOutput("Copying " + source + " to " + interfacePath);
                    string extraFileName = System.IO.Path.GetFileName(source);
                    string destFile = interfaceDirectory + System.IO.Path.DirectorySeparatorChar + extraFileName;
                    if (!System.IO.File.Exists(@destFile))
                    {
                        System.IO.File.Copy(@source, @destFile);
                    }
                    else
                    {
                        WriteOutput(string.Format("File {0} already found in {1}", extraFileName, interfaceDirectory));
                    }
                }
                return true;
            }
            catch (Exception e)
            {
                WriteOutput("There was an error during document and extra file copying: " + e.Message);
                return false;
            }
        }

        private void installRollback()
        {
            try
            {
                string dropSchemaString = "DROP SCHEMA " + databaseName + ";";
                executeNonQuery(dropSchemaString);
                WriteOutput("Database " + databaseName + " dropped. MySQL returned to pre-install state.");
            }
            catch (Exception e)
            {
                WriteOutput("There was an error during rollback: " + e.Message
                    + " The rollback will have to be performed manually using backup found in "
                    + backupName + ".");
            }
        }

        /* This method should be called when there was an error during upgrade 
         * and you want to roll user back to previous JCMS version. */
        private void rollback()
        {
            try
            {
                string dropSchemaString = "DROP SCHEMA " + databaseName + ";";
                string createSchemaString = "CREATE SCHEMA " + databaseName + ";";
                
                //drop schema that attempted to be upgraded
                WriteOutput("Recreating schema " + databaseName);
                executeNonQuery(dropSchemaString);
                //recreate it
                executeNonQuery(createSchemaString);
                

                Process p = new Process();
                //get streamreader to direct backup info into recreate database.
                //StreamReader reader = new StreamReader(backupName);

                string rollbackArgs = string.Format("--user={0} --password={1} --database={2} --port={3} --host={4} --execute=\"SOURCE {5}\"",
                                    username, password, databaseName, port, host, backupName);
                p = new Process();
                p.StartInfo.FileName = MySQLFileName;
                p.StartInfo.Arguments = rollbackArgs;

                //prevent the command window from popping up
                p.StartInfo.UseShellExecute = false;
                p.StartInfo.CreateNoWindow = true;
                p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                p.StartInfo.RedirectStandardOutput = true;
                p.StartInfo.RedirectStandardError = true;

                //"-u" + username + " -p" + password + " " + databaseName;
                /*
                p.StartInfo.RedirectStandardOutput = true;
                p.StartInfo.RedirectStandardInput = true;
                p.StartInfo.RedirectStandardError = true;
                p.StartInfo.UseShellExecute = false;
                p.StartInfo.CreateNoWindow = true;
                p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                */

                WriteOutput(string.Format("Restoring {0} to previous version, if your database is large this may take a while.", databaseName));
                p.Start();


                //get and write output
                string output = p.StandardOutput.ReadToEnd();
                WriteOutput(output);
                //get and write errors
                string errors = p.StandardError.ReadToEnd();
                WriteOutput(errors);
                p.WaitForExit();

  //              if (errors.Equals("") || errors.Contains("Warning"))
                if(p.ExitCode == 0)
                {
                    WriteOutput("Rollback successful.");
                }
                else
                {
                    WriteOutput("Rollback could not be completed, this process will have to be completed manually.");
                }
            }
            catch (Exception e)
            {
                WriteOutput("There was an error during rollback: " + e.Message
                    + " The rollback will have to be performed manually using backup found in "
                    + backupName + ".");
            }
        }

        /* need to run this separately because database isn't known...
        * need to manually remove index from genotype table if 4.1.0 or older
        * SELECT COUNT(1) INTO IndexCount
        *    FROM information_schema.statistics
        *    WHERE table_schema = 'jcms_db'  # <----- need to put in users selection of database here, not jcms_db.
        *    AND table_name = 'GenotypeDocument'
        *    AND index_name = '_GenotypeDocument_key';
        * -- Now delete it if it did.
        * SELECT '-- 145.2 ---';
        * IF IndexCount > 0 THEN
        *     ALTER TABLE DocumentMapping DROP INDEX `_GenotypeDocument_key`  ;
        * END IF;
        * 
        * */
        private void removeGenotypeDocumentIndex()
        {
            string genotypeDocumentIndexQuery = string.Format("SELECT COUNT(1) " +
                                                                "FROM information_schema.statistics " +
                                                                "WHERE table_schema = '{0}' " +
                                                                "AND table_name = 'GenotypeDocument' " +
                                                                "AND index_name = '_GenotypeDocument_key';", databaseName);
            string count = executeSingleResultQuery(genotypeDocumentIndexQuery);
            if (!count.Equals("0"))
            {
                //should remove index on _GenotypeDocument_key
                string alterGenotypeDocumentTable = string.Format("ALTER TABLE {0}.GenotypeDocument DROP INDEX `_GenotypeDocument_key`;", databaseName);
                executeNonQuery(alterGenotypeDocumentTable);
            }
        }
        
        private void executeNonQuery(string nonQueryString)
        {
            MySqlTransaction myTrans;
            MySqlConnection conMySQL = new MySqlConnection();
            //build connection string
            conMySQL.ConnectionString = "server=" + host + ";userid=" + username + ";password=" + password;
            //open connection
            conMySQL.Open();

            myTrans = conMySQL.BeginTransaction();

            //create nonquery command
            MySqlCommand theCommand = new MySqlCommand(nonQueryString, conMySQL);
            theCommand.Transaction = myTrans;
            theCommand.ExecuteNonQuery();
            myTrans.Commit();
        }

        private MySqlDataReader executeMultipleResultQuery(string queryString)
        {
            MySqlConnection conMySQL = new MySqlConnection();
            //build connection string
            conMySQL.ConnectionString = "server=" + host + ";userid=" + username + ";password=" + password;
            //open connection
            conMySQL.Open();

            //create nonquery command
            MySqlCommand theCommand = new MySqlCommand(queryString, conMySQL);
            return theCommand.ExecuteReader();
        }

        private string executeSingleResultQuery(string queryString)
        {
            string queryResults = "";
            MySqlConnection conMySQL = new MySqlConnection();
            //build connection string
            conMySQL.ConnectionString = "server=" + host + ";userid=" + username + ";password=" + password;
            //open connection
            conMySQL.Open();

            //create nonquery command
            MySqlCommand theCommand = new MySqlCommand(queryString, conMySQL);
            MySqlDataReader reader = theCommand.ExecuteReader();
            reader.Read();
            queryResults = reader[0].ToString();

            return queryResults;
        }

        private void linkTables()
        {
            //catalogs contain all the tables that exist in the database (or are linked there).
            //need one for JCMS (access interface) for a destination and one for mysql (source)
            ADOX.Catalog catJCMS = new ADOX.Catalog();
            ADOX.Catalog catMySQL = new ADOX.Catalog();
            //connections allow you to read all tables into catalogs, need one for JCMS to say where tables are supposed to go
            //and one for MySQL to say where connections are coming from
            ADODB.Connection conJCMS = new ADODB.Connection();
            ADODB.Connection conMySQL = new ADODB.Connection();

            string userDSN = "JCMS" + host + databaseName;

            try
            {
                //STEP 1: connect to JCMS interface
                string strConx = "Provider=Microsoft.ACE.OLEDB.12.0;Persist Security Info=True;Data Source=" + interfacePath + ";";
                conJCMS.Mode = ADODB.ConnectModeEnum.adModeReadWrite;
                conJCMS.Open(strConx, "", "", 0);
                //STEP 1.A.: Create catalog for JCMS
                catJCMS.ActiveConnection = conJCMS;
                

                //STEP 2: CREATE USER DSN
                String strAttributes = string.Format("DSN={0};", userDSN);
                strAttributes = strAttributes + "Database=" + databaseName + ";";
                strAttributes = strAttributes + string.Format("Description=DSN for {0}:{1};", host, databaseName);
                strAttributes = strAttributes + "Server=" + host + ";";
                strAttributes = strAttributes + "User=" + username + ";";
                strAttributes = strAttributes + "Password=" + password + ";";
                IntPtr please = new IntPtr(0);
                bool DSNSuccess = SQLConfigDataSource(please, 1, "MySQL ODBC 3.51 Driver", strAttributes);
                if (!DSNSuccess)
                {
                    WriteOutput("DSN Could not be created, could be a permissions issue, or ODBC driver is not properly installed. Tables will have to be linked manually.");
                }
                else
                {
                    WriteOutput("DSN created successfully");
                    //STEP 3: open mysql connection using user dsn created above               
                    //string strConxString = "Provider=MSDASQL;" + "Driver=" + "MySQL ODBC 3.51 Driver" + ";" + "Server=" + host + ";" + "UID=" + username + ";" + "PWD=" + password + ";" + "Port=" + port;
                    string strConxString = string.Format("Driver={0};Server={1};Database={2};Uid={3};Pwd={4};", "MySQL ODBC 3.51 Driver", host, databaseName, username, password);
                    conMySQL.Open(strConxString, username, password, 0);
                    catMySQL.ActiveConnection = conMySQL;
                    ADOX.Table tblLink;

                    //create links from MySQL to JCMS.
                    foreach (ADOX.Table tblLoop in catMySQL.Tables)
                    {
                        tblLink = new ADOX.Table();
                        tblLink.Name = tblLoop.Name;
                        tblLink.ParentCatalog = catJCMS;
                        tblLink.Properties["Jet OLEDB:Link Datasource"].Value = interfacePath;
                        tblLink.Properties["Jet OLEDB:Link Provider String"].Value = "ODBC;DATABASE=" + databaseName + ";DSN=" + userDSN +";OPTION=0;UID=" + username + ";PWD=" + password + ";PORT=" + port + ";SERVER=" + host + ";";
                        tblLink.Properties["Jet OLEDB:Remote Table Name"].Value = tblLoop.Name;
                        tblLink.Properties["Jet OLEDB:Create Link"].Value = true;
                        tblLink.Properties["Jet OLEDB:Cache Link Name/Password"].Value = true;
                        catJCMS.Tables.Append(tblLink);
                        WriteOutput(string.Format("Table {0} successfully linked.", tblLoop.Name));
                    }
                    if (programVariables.getInstall())
                    {
                        WriteOutput("Install process complete, tables successfully linked.");
                    }
                    else
                    {
                        WriteOutput("Upgrade process complete, tables successfully linked.");
                    }
                }
            }
            catch (Exception e)
            {
                WriteOutput("The following error occurred when trying to relink tables: " + e.Message 
                    + " This step will have to be completed manually. For help please visit our FAQs page at http://colonymanagement.jax.org/support-2/faqs/ "
                    + "or our forums at http://community.jax.org/jcms_discussion_forum/default.aspx");
            }
        }
        /*
        private void errorDataReceived(object sender, DataReceivedEventArgs e)
        {
            if (e.Data != null)
            {
                WriteOutput(e.Data);
            }
        }

        private void outputDataReceived(object sender, DataReceivedEventArgs e)
        {
            if (e.Data != null)
            {
                WriteOutput(e.Data);
            }
        }
        */
        private void WriteOutput(string message)
        {
            try
            {
                outputPanel.Text = outputPanel.Text + System.Environment.NewLine + message + System.Environment.NewLine;
                outputPanel.SelectionStart = outputPanel.Text.Length;
                outputPanel.ScrollToCaret();
                swLog.WriteLine(message);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void processProgress_Load(object sender, EventArgs e)
        {

        }
    }
}
