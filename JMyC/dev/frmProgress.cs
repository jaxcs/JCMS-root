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
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.IO;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace JCMS_DB_Converter
{
    public partial class frmProgress : Form
    {
        private const string MYSQL_DB_NAME = "jcms_db";
        //private const string MYSQL_DB_NAME = textBoxDatabaseName;
        private const string MY_ODBC_VERSION = "MySQL ODBC 3.51 Driver";
        private ADODB.Connection conJCMS_db = new ADODB.Connection();
        private ADODB.Connection conMySQL = new ADODB.Connection();
        private ADODB.Connection conJCMS = new ADODB.Connection();
        private ADOX.Catalog catAccess = new ADOX.Catalog();
        private ADOX.Catalog catMySQL = new ADOX.Catalog();
        private ADOX.Catalog catJCMS = new ADOX.Catalog();
        private ADOX.Catalog catLoop = new ADOX.Catalog(); //copy of JCMS catalog, used to loop for deleting links
        private string strSourceDbName = "";
        private string strSourceDbFullPath = "";
        private string strSourceDbPath = "";
        private string strAccessPath = "";
        private string strMySQLUser = "";
        private string strMySQLPassword = "";
        private string strMySQLPort = "";
        private string strMySQLHost = "";
        private string strMySQLDBName = MYSQL_DB_NAME;
        private string strDbVersion="";
        private string strTempPath = "";
        StreamWriter swLog;
        BackgroundWorker bwkr;
        private bool blnDisable;
        private bool blnSuccess = true;
        private bool blnCancel = false;

        public frmProgress(string AccessPath, string MySQLUser, string MySQLPassword, string MySQLPort, string MySQLHost, string MyNewSQLDBName, bool Disable)
        {
            InitializeComponent();
            //initialize variables
            strAccessPath = AccessPath;
            strMySQLUser = MySQLUser;
            strMySQLPassword = MySQLPassword;
            strMySQLPort = MySQLPort;
            strMySQLHost = MySQLHost;
            strMySQLDBName = MyNewSQLDBName;
            blnDisable = Disable;

            //add the handler to start the conversion
            this.Shown += new EventHandler(frmProgress_Shown);            
        }

        private void frmProgress_Shown(object sender, System.EventArgs e) 
        {
            //initialize the form
            pgbConversion.Minimum = 1;
            pgbConversion.Maximum = 15;
            pgbConversion.Step = 1;
            btnCancel.Enabled = true;
            btnExit.Enabled = false;
            SetTempPath();

            //create log file
            swLog = new StreamWriter(Application.StartupPath + "\\Conversion.txt");
            ConvertDB();

            
        }

        private void StepStarted(string strMessage)
        {
            try
            {
                lblConversion.Text=strMessage ;
                this.Refresh();
                rtbOutput.SelectionStart = rtbOutput.Text.Length;
                swLog.WriteLine(strMessage);
            }
            catch { }
        }

        private void StepComplete(string strMessage)
        {
            try
            {
                rtbOutput.Text = rtbOutput.Text + strMessage + System.Environment.NewLine + System.Environment.NewLine;
                pgbConversion.PerformStep();
                this.Refresh();
                rtbOutput.SelectionStart = rtbOutput.Text.Length;
                swLog.WriteLine(strMessage);
            }
            catch { }
        }
        private void WriteOutput(string strMessage)
        {
            try
            {
                rtbOutput.Text = rtbOutput.Text + strMessage + System.Environment.NewLine;
                rtbOutput.SelectionStart = rtbOutput.Text.Length;
                swLog.WriteLine(strMessage);
            }
            catch (Exception e)
            {
                Console.WriteLine (e.Message); 
            }
        }

        private void ConvertDB()
        {
            //set up the thread for copying data so that it can run in the background            
            bwkr = new BackgroundWorker();
            bwkr.WorkerReportsProgress = true;
            bwkr.WorkerSupportsCancellation = true;
            bwkr.DoWork += new DoWorkEventHandler(bwkr_DoWork);
            bwkr.RunWorkerCompleted += new RunWorkerCompletedEventHandler(bwkr_RunWorkerCompleted);
            bwkr.ProgressChanged += new ProgressChangedEventHandler(bwkr_ProgressChanged);

            //connect to the databases
            StepStarted("Connecting to Access source");
            if (ConnectToMSACC())
                StepComplete("Connected to Access source");
            else
                blnSuccess = false;

            if (blnSuccess == true)
            {
                StepStarted("Connecting to MySQL");
                if (ConnectToMySQL())
                    StepComplete("Connected to MySQL");
                else
                    blnSuccess = false;
            }
            // copy database schema 
            if (blnSuccess == true)
            {
                StepStarted( "Creating database schema in MySQL");
                if (CopySchema())
                {
                    StepComplete ("MySQL schema created");
                    pgbConversion.PerformStep();
                }
                else
                    blnSuccess = false;
            }

            //copy the data:  this is handled differently because it runs in the background
            if (blnSuccess == true)
            {
                StepStarted("Copying data to MySQL");
                bwkr.RunWorkerAsync();
                //wait for the data copy to complete
                while (bwkr.IsBusy)
                    Application.DoEvents();
                //a cancel or error during the copy process will set success flag to false
                if (blnSuccess == true)
                StepComplete("Data copied to MySQL database.");
            }
            //Create foreign keys
            if (blnSuccess == true)
            {
                StepStarted( "Creating table relationships");
                if (CreateRelationships() == true)
                    StepComplete ("Relationships created");
                else
                    blnSuccess = false;
            }
            //Re-link tables
            if (blnSuccess == true)
            {
                StepStarted( "Re-linking tables");
                if (RelinkTables() == true)
                    StepComplete("Tables re-linked");
            }

            //Close the database connections, a wait here for garbage collection to happen, 
            //otherwise the connections are held open and the db cannot be renamed
            if (blnSuccess == true)
            {
                StepStarted("Closing connections");
                try
                {
                    conMySQL.Close();
                    conJCMS_db.Close();
                    conJCMS.Close();
                    conJCMS = null;
                    conJCMS_db = null;
                    conMySQL = null;
                    catAccess = null;
                    catJCMS = null;

                    System.GC.Collect();
                    System.GC.WaitForPendingFinalizers();
                    // Worker loop ensure connections are closed
                    for (int i = 0; i < 10000; i++)
                    {
                        Console.WriteLine("Disabling the source database");
                    }
                }
                catch
                {
                };
            }
            //Disable database
            if ((blnSuccess == true) && (blnDisable == true))
            {
                StepStarted("Disabling database");
                if (DisableDatabase() == true)
                {
                    StepComplete ("Database disabled");
                    pgbConversion.PerformStep();
                }
                else
                    //still considered a successful conversion
                    WriteOutput("Could not disable database.  This step must be performed manually by renaming the source database.");
            }

            //final steps
            if (blnSuccess == true)
            {
                lblConversion.Text = "Database conversion successful";
                WriteOutput("Database conversion successful.");
            }
            else
            {

                if (blnCancel == true)
                {
                    lblConversion.Text = "Database conversion cancelled.";
                    WriteOutput("Database conversion cancelled.");
                }
                else
                {
                    //drop the schema 
                    string strSQL = null;
                    //drop the MySQL database 
                    strSQL = "DROP SCHEMA " + strMySQLDBName;
                    blnCancel = true;
                    blnSuccess = false;
                    try
                    {
                        ExecuteSQL(strSQL);
                    }
                    catch
                    { }
                    lblConversion.Text = "Database conversion failed.";
                    WriteOutput("Database conversion failed.");
                }
            }
            try
            {
                swLog.Close();
                pgbConversion.Value = 15;
                btnCancel.Enabled = false;
                btnExit.Enabled = true;
            }
            catch { }
        }

        private void CancelProcess(string strCancelMessage)
        {
            string strSQL = null;
            //drop the MySQL database 
            strSQL = "DROP SCHEMA " + strMySQLDBName;
            blnCancel = true;
            blnSuccess = false;
            try
            {
                ExecuteSQL(strSQL);
            }
            catch(Exception e)
            {  };
            WriteOutput(strCancelMessage);
        }
        private void bwkr_DoWork(object sender, DoWorkEventArgs e)
        {
           if(CopyData()==false)
               blnSuccess=false;
        }
        private void bwkr_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            if (e.Error != null)
            {
                WriteOutput ("Could not copy data to MySQL.  Error: " + e.Error.Message);
                blnSuccess = false;
            } 
        }

        private void bwkr_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            WriteOutput(e.UserState.ToString());
        }

        private void SetTempPath()
        {
            string strPath = System.IO.Path.GetTempPath();
            strPath = strPath.Substring(strPath.IndexOf(":") - 1);
            if (Directory.Exists(strPath)==false)
            {
                MessageBox.Show("Could not locate a temp directory for the conversion process.  You will be prompted to select a temporary directory, temporary data files will be copied there and then removed.");
                dlgOpen.ShowDialog();
                strPath = dlgOpen.SelectedPath.ToString();
            }
            strPath = strPath.Replace("\\", "\\\\");
            if (!strPath.EndsWith("\\")) strPath += "\\";
            strTempPath = strPath;
        }

        private bool ConnectToMSACC()
        {
            //connect to the JCMS database
            string strConx;
            try
            {
                strConx = "Provider=Microsoft.Jet.OLEDB.4.0;Persist Security Info=True;Data Source=" + strAccessPath + ";";
                conJCMS.Mode = ADODB.ConnectModeEnum.adModeReadWrite;
                conJCMS.Open(strConx, "", "", 0);
                catJCMS.ActiveConnection = conJCMS;
            }
            catch (Exception e)
            {
                WriteOutput("Could not connect to Access interface database.  Error: " + e.Message);
                WriteOutput("Access path= " + strAccessPath);
                return false;
            }
            //locate source database from link properties of interface database
            foreach (ADOX.Table tblLoop in catJCMS.Tables)
            {
                if (tblLoop.Type == "LINK")
                {
                    strSourceDbFullPath = tblLoop.Properties["Jet OLEDB:Link DataSource"].Value.ToString();
                    strSourceDbPath = strSourceDbFullPath.Substring(0, strSourceDbFullPath.LastIndexOf("\\") + 1);
                    strSourceDbName = strSourceDbFullPath.Substring(strSourceDbFullPath.LastIndexOf("\\") + 1);
                    break;
                }
            }
            //connect to source database
            try
            {
                strConx = "Provider=Microsoft.Jet.OLEDB.4.0;Persist Security Info=True;Data Source=" + strSourceDbFullPath + ";";
                conJCMS_db.Open(strConx, "", "", 0);
                return true;
            }
            catch (Exception e)
            {
                WriteOutput("Could not connect to Access interface database.  Error: " + e.Message + "  Please verify that your interface database contains links to the source database.");
                return false;
            }
        }

        private bool ConnectToMySQL()
        {
            try
            {
                string strConxString = "Provider=MSDASQL;" + "Driver=" + MY_ODBC_VERSION + ";" + "Server=" + strMySQLHost + ";" + "UID=" + strMySQLUser + ";" + "PWD=" + strMySQLPassword + ";" + "Port=" + strMySQLPort ;
                conMySQL.Open(strConxString, strMySQLUser, strMySQLPassword, 0);
                return true;
            }
            catch (Exception e)
            {
                WriteOutput("Could not connect to MySQL database.  Error: " + e.Message);
                return false;
            }
        }

        public bool CopySchema()
        {
            try
            {
                string strSQL = null;
                catAccess.ActiveConnection = conJCMS_db;
                try
                {
                    strSQL = "CREATE DATABASE " + strMySQLDBName;
                    ExecuteSQL(strSQL);
                }
                catch (Exception e)
                {
                    if (e.Message.ToString().IndexOf("database exists") > 0)
                    {
                        MessageBox.Show("JCMS MySQL database already exists." + System.Environment.NewLine + "You must either remove or rename the MySQL database named " + strMySQLDBName + " before running the conversion tool.", "Database Already Exists", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        WriteOutput("Database already exists and was not overwritten.");
                        blnCancel = true;
                        return false;
                    }
                    else
                    {
                        strSQL = "CREATE DATABASE " + strMySQLDBName;
                        ExecuteSQL(strSQL);
                    }
                }

                foreach (ADOX.Table tblLoop in catAccess.Tables)
                {
                    if (tblLoop.Type == "TABLE" | tblLoop.Type == "VIEW")
                    {
                        if (tblLoop.Columns.Count > 0)
                        {
                            try
                            {
                                strSQL = TableDefinition(tblLoop).ToString();
                                ExecuteSQL(strSQL);
                            }
                            catch (Exception e)
                            {
                                WriteOutput("Error creating table: " + tblLoop.Name + System.Environment.NewLine + "Error: " + e.Message);
                                return false;
                            }
                        }
                        else
                        {
                            //if no columns are visible, it is probably a permissions issue (no "Read Design" permission)
                            WriteOutput("Error creating table: " + tblLoop.Name + System.Environment.NewLine + "Please verify that you have the correct permissions.  See help section 'Preparing your Access database' for details.");
                            return false;
                        }
                    }
                }
                return true;
            }
            catch (Exception e)
            {
                WriteOutput("Could not copy database schema.  Error: " + e.Message);
                return false;
            }
        }

        public bool CopyData()
        {
            // Scripting.FileSystemObject objFSO = new Scripting.FileSystemObject();
                ExecuteSQL("USE " + strMySQLDBName);
                foreach (ADOX.Table tblLoop in catAccess.Tables)
                {
                    if (bwkr.CancellationPending == true)
                        return false;
                    if (tblLoop.Type == "TABLE" | tblLoop.Type == "VIEW")
                    {
                        CopyTable(tblLoop);
                        //check again for cancel, don't output if the cancel happened during this table copy
                        if (bwkr.CancellationPending == false)
                            bwkr.ReportProgress(1, "Copied table " + tblLoop.Name);
                        else
                        //if process exited due to cancel or error, clean up the data file
                        {
                            FileInfo fI = new FileInfo(strSourceDbPath.Replace("\\", "\\\\") + "\\" + tblLoop.Name + ".txt");
                            if (fI.Exists )
                            {
                                fI.Delete();
                            }
                        }
                    }
                }
                return true;
        }

        public bool CreateRelationships()
        {
            //Relationships are read from a copy of the MSysRelationships table, called DbRelationships
            //and an alter table statement is issued for each

            string strSQL = "";

            try
            {
                foreach (ADOX.Table tblLoop in catAccess.Tables)
                {
                    if ((tblLoop.Type == "TABLE" | tblLoop.Type == "VIEW") && ("MSys" != tblLoop.Name.Substring(0, 4)))
                    {
                        foreach (ADOX.Key tblKey in tblLoop.Keys)
                        {
                            if (tblKey.Type == ADOX.KeyTypeEnum.adKeyForeign)
                            {

                                foreach (ADOX.Column tblColumn in tblKey.Columns)
                                {
//                                    if ( 0 <= tblKey.Name.ToString().IndexOf('{') ) 
//                                    {
                                        strSQL = "ALTER TABLE "
                                        + MySQLName(tblLoop.Name)
                                        + " ADD FOREIGN KEY ("
                                        + MySQLName(tblColumn.Name) + ") REFERENCES "
                                        + MySQLName(tblKey.RelatedTable) + "("
                                        + MySQLName(tblColumn.RelatedColumn) + ") ON DELETE RESTRICT ON UPDATE RESTRICT";
//                                    }
//                                    else
//                                    {
//                                        strSQL = "ALTER TABLE "
//                                        + MySQLName(tblLoop.Name)
//                                        + " ADD CONSTRAINT " + MySQLName(tblKey.Name) + " FOREIGN KEY ("
//                                        + MySQLName(tblColumn.Name) + ") REFERENCES "
//                                        + MySQLName(tblKey.RelatedTable) + "("
//                                        + MySQLName(tblColumn.RelatedColumn) + ") ON DELETE RESTRICT ON UPDATE RESTRICT";
//                                    }
                                    System.Console.WriteLine("\n" + strSQL + "\n");
                                    ExecuteSQL(strSQL);
                                }
                            }
                        }
                    }
                }
            }






/*          ADODB.Recordset recRelationships = new ADODB.Recordset();
            try
            {
                recRelationships.Open("SELECT * FROM DbRelationships", conJCMS_db, ADODB.CursorTypeEnum.adOpenStatic, ADODB.LockTypeEnum.adLockBatchOptimistic, 0);
            }
            catch (Exception e)
            {
                WriteOutput("Error creating relationships.  Could not read table DbRelationships");
                return false;
            }

            try
            {
                while (!recRelationships.EOF)
                {
                    if ( 0  > recRelationships.Fields["szRelationship"].Value.ToString().IndexOf('{'))
                    {
                        strSQL = "ALTER TABLE " + MySQLName(recRelationships.Fields["szObject"].Value.ToString()) + " ADD CONSTRAINT " + recRelationships.Fields["szRelationship"].Value.ToString() + " ADD FOREIGN KEY (" + MySQLName(recRelationships.Fields["szColumn"].Value.ToString()) + ") REFERENCES " + MySQLName(recRelationships.Fields["szReferencedObject"].Value.ToString()) + "(" + MySQLName(recRelationships.Fields["szReferencedColumn"].Value.ToString()) + ") ON DELETE RESTRICT ON UPDATE RESTRICT";
                    }
                    else
                        strSQL = "ALTER TABLE " + MySQLName(recRelationships.Fields["szObject"].Value.ToString()) + " ADD FOREIGN KEY (" + MySQLName(recRelationships.Fields["szColumn"].Value.ToString()) + ") REFERENCES " + MySQLName(recRelationships.Fields["szReferencedObject"].Value.ToString()) + "(" + MySQLName(recRelationships.Fields["szReferencedColumn"].Value.ToString()) + ") ON DELETE RESTRICT ON UPDATE RESTRICT";
                    ExecuteSQL(strSQL);
                    recRelationships.MoveNext();
                }
            }
*/

            catch (Exception e)
            {
                WriteOutput("Error creating relationships." + System.Environment.NewLine + "Error: " + e.Message);
                return false;
            }
            return true;

        }

        private bool RelinkTables()
        {
            string strConx;
            ADOX.Table tblLink;

            catJCMS.ActiveConnection = conJCMS;
            catLoop.ActiveConnection = conJCMS;
            catMySQL.ActiveConnection = conMySQL; //opened in ConnectToMySQL
            try
            {
                //drop existing table links
                foreach (ADOX.Table tblLoop in catLoop.Tables)
                {
                    if (tblLoop.Type == "LINK")
                        catJCMS.Tables.Delete(tblLoop.Name);
                }

                //create a new link for each table in the MySQL database
                foreach (ADOX.Table tblLoop in catMySQL.Tables)
                {
                    tblLink = new ADOX.Table();
                    tblLink.Name = tblLoop.Name;
                    tblLink.ParentCatalog = catJCMS;
                    tblLink.Properties["Jet OLEDB:Link Datasource"].Value = strSourceDbFullPath;
                    tblLink.Properties["Jet OLEDB:Link Provider String"].Value = "ODBC;DATABASE=" + strMySQLDBName + ";FILEDSN=myJCMS;OPTION=0;UID=" + strMySQLUser + ";PWD=" + strMySQLPassword + ";PORT=" + strMySQLPort  + ";SERVER=" + strMySQLHost + ";";
                    tblLink.Properties["Jet OLEDB:Remote Table Name"].Value = tblLoop.Name;
                    tblLink.Properties["Jet OLEDB:Create Link"].Value = true;
                    tblLink.Properties["Jet OLEDB:Cache Link Name/Password"].Value = true;
                    catJCMS.Tables.Append(tblLink);
                }
            }
            catch (Exception e)
            {
                WriteOutput("Could not re-link tables.  Error: " + e.Message + ".  This step must be performed manually.  Please see help section 'Troubleshooting problems with database conversion' for details.");
                return false;
            }
            return true;

        }

        private bool DisableDatabase()
        {
            try
            {
                FileInfo filDBSource = new FileInfo(strSourceDbFullPath);
                filDBSource.MoveTo(strSourceDbPath + strSourceDbName + ".disbled");
                return true;
            }
            catch (Exception e)
            {
                WriteOutput(e.Message);
                return false;
            }
        }

        public object ColDefinition(ADOX.Column col)
        {
            string strDefine = null;
            strDefine = MySQLName(col.Name) + " " + DataType(col);


            if (col.Properties["Autoincrement"].Value.ToString() == "True")
            {
                strDefine += "  NOT NULL AUTO_INCREMENT";
            }
            else
            {
                if (col.Properties["nullable"].Value.ToString() == "True")
                {
                    strDefine += " NULL";
                }
                else
                {
                    strDefine += " NOT NULL";
                }
            }
            if (col.Properties["Default"].Value != null)
            {
                if (col.Type == ADOX.DataTypeEnum.adBoolean)
                {
                    Console.WriteLine("\tColumn: " + col.Name.ToString() + " : " + col.Properties["Default"].Value.ToString());
                    if (col.Properties["Default"].Value.ToString() == "Yes" ||
                        col.Properties["Default"].Value.ToString() == "True" ||
                        col.Properties["Default"].Value.ToString() == "1" ||
                        col.Properties["Default"].Value.ToString() == "-1"  )
                        strDefine += " DEFAULT -1";
                    else
                        strDefine += " DEFAULT 0";
                }
                else if (col.Type == ADOX.DataTypeEnum.adVarWChar)
                {
                    String strDefValue = col.Properties["Default"].Value.ToString().Trim();
                    strDefValue = strDefValue.Replace("\"", "");
                    strDefValue = strDefValue.Replace("\'", ""); 
                    
                    if ("NULL" == strDefValue.ToUpper()) 
                        strDefValue = "NULL";

                    strDefine += " DEFAULT '" + strDefValue + "'";
                }
                else
                    strDefine += " DEFAULT " + col.Properties["Default"].Value.ToString().ToUpper();
            }
            return strDefine;
        }

        public object DataType(ADOX.Column colDef)
        {
            int intLength = 0;
            int intPrecision = 0;
            int intScale = 0;
            string strNewType = null;

            intLength = colDef.DefinedSize;
            intPrecision = colDef.Precision;
            intScale = colDef.NumericScale;
            strNewType = colDef.Name;
            switch (colDef.Type)
            {
                case ADOX.DataTypeEnum.adBigInt:
                    strNewType = "BIGINT";
                    break;
                case ADOX.DataTypeEnum.adBoolean:
                    strNewType = "TINYINT(1)";
                    break;
                case ADOX.DataTypeEnum.adDouble:
                    strNewType = "FLOAT";
                    break;
                case ADOX.DataTypeEnum.adInteger:
                    strNewType = "INTEGER";
                    break;
                case ADOX.DataTypeEnum.adNumeric:
                    strNewType = "NUMERIC (" + intPrecision + ", " + intScale + ")";
                    break;
                case ADOX.DataTypeEnum.adSingle:
                    strNewType = "REAL";
                    break;
                case ADOX.DataTypeEnum.adUnsignedTinyInt:
                    strNewType = "TINYINT";
                    break;
                case ADOX.DataTypeEnum.adSmallInt:
                    strNewType = "SMALLINT";
                    break;
                case ADOX.DataTypeEnum.adTinyInt:
                    strNewType = "TINYINT";
                    break;
                case ADOX.DataTypeEnum.adVarChar:
                    strNewType = "VARCHAR (" + intLength + ")";
                    break;
                case ADOX.DataTypeEnum.adVarWChar:
                    strNewType = "VARCHAR (" + intLength + ")";
                    break;
                case ADOX.DataTypeEnum.adLongVarWChar:
                    strNewType = "LONGTEXT";
                    break;
                case ADOX.DataTypeEnum.adDate:
                    strNewType = "DATETIME";
                    break;
                case ADOX.DataTypeEnum.adCurrency:
                    strNewType = "FLOAT";
                    break;
                default:
                    strNewType = "UNKNOWN";
                    break;
            }
            return strNewType;
        }

        public object IndexDefinition(ADOX.Table tblDef, ADOX.Index idxDef)
        {
            int intLoop = 0;
            string strIndex = null;
            ADOX.Column colDef = null;

            if (idxDef.PrimaryKey == true)
            {
                strIndex = strIndex + "PRIMARY KEY ";
            }
            else if (idxDef.Unique)
            {
                strIndex = strIndex + "UNIQUE INDEX " + MySQLName(idxDef.Name);
            }
            else
            {
                strIndex = strIndex + "INDEX " + MySQLName(idxDef.Name);
            }

            strIndex = strIndex + "(";
            for (intLoop = 0; intLoop < idxDef.Columns.Count; intLoop++)
            {
                colDef = idxDef.Columns[intLoop];
                strIndex = strIndex + MySQLName(colDef.Name);
                if (intLoop < idxDef.Columns.Count - 1)
                {
                    strIndex = strIndex + ",";
                }
            }

            strIndex = strIndex + ")";
            return strIndex;
        }

        public object TableDefinition(ADOX.Table tblDef)
        {
            int intLoop = 0;
            string strTable = "";
            string strIndex = "";

            ADODB.Recordset recSchema = new ADODB.Recordset();
            Console.WriteLine("Table: " + tblDef.Name.ToString());

            recSchema.Open("SELECT * FROM [" + tblDef.Name +"]", conJCMS_db, ADODB.CursorTypeEnum.adOpenStatic, ADODB.LockTypeEnum.adLockBatchOptimistic, 0);

            strTable = "CREATE TABLE " + strMySQLDBName + "." + MySQLName(tblDef.Name) + System.Environment.NewLine + "(";


            for (intLoop = 0; intLoop < recSchema.Fields.Count; intLoop++)
            {
                strTable = strTable + ColDefinition(tblDef.Columns[recSchema.Fields[intLoop].Name]);
                if (intLoop + 1 < tblDef.Columns.Count)
                {
                    strTable = strTable + ", " + System.Environment.NewLine + " ";
                }
            }

            foreach (ADOX.Index idxLoop in tblDef.Indexes)
            {
                strIndex = IndexDefinition(tblDef, idxLoop).ToString();
                if (strIndex != "")
                {
                    strTable = strTable + ", " + System.Environment.NewLine + strIndex;
                }
            }
            strTable = strTable + ")";

            strTable = strTable + " ENGINE =INNODB";
            strTable = strTable + " CHARACTER SET utf8";

            return strTable;
        }
        
        public void CopyTable(ADOX.Table tblAccess)
        {             
            ADODB.Recordset recMaster = new ADODB.Recordset();
            ADODB.Recordset recLoop = new ADODB.Recordset();
            int intLoop = 0;

            string strInfile = "";
            string strSQL = "SELECT ";
            string strRecord;
            string strLoadFilePath = strSourceDbPath.Replace("\\", "\\\\");
            string strFileName = strTempPath + tblAccess.Name + ".txt";


            StreamWriter sw = new StreamWriter(strFileName, false);
            //create the infile
                strInfile += "LOAD DATA LOCAL INFILE '" + strFileName + "' INTO TABLE " + strMySQLDBName + "." + tblAccess.Name + " ";
                strInfile += "FIELDS TERMINATED BY ',' ";
                strInfile += "ESCAPED BY '\\\\' ";
                strInfile += "LINES TERMINATED BY 0x0d0a ";
                strInfile += "(";

                //loop through fields to enumerate them for the infile and build a select statement
                for (intLoop = 0; intLoop < tblAccess.Columns.Count; intLoop++)
                {
                    strInfile += MySQLName((tblAccess.Columns[intLoop].Name));
                    switch (tblAccess.Columns[intLoop].Type)
                    {
                        case ADOX.DataTypeEnum.adDate: //convert to MySQL datetime format
                            strSQL += "FORMAT([" + tblAccess.Columns[intLoop].Name + "],  'YYYY-MM-DD HH:MM:SS') as " + tblAccess.Columns[intLoop].Name;
                            break;
                        default:
                            strSQL += "[" + tblAccess.Columns[intLoop].Name + "]";
                            break;
                    }
                    if (intLoop < tblAccess.Columns.Count - 1)
                    {
                        strSQL += ",";
                        strInfile += ", ";
                    }
                }
                strInfile += ");";
                strSQL += " FROM [" + tblAccess.Name + "]";

                //open the "Master" recordset
                recMaster.CursorLocation = ADODB.CursorLocationEnum.adUseClient;
                recMaster.Open(strSQL, conJCMS_db, ADODB.CursorTypeEnum.adOpenStatic, ADODB.LockTypeEnum.adLockOptimistic, 0);

                //create the "Loop" recordset, this is a clone of the master, with the exception
                //that the definedsize for text fields is lengthened.  This is because the added
                //escape characters could potentially exceed the field length in the master recordset
                recLoop.CursorLocation = ADODB.CursorLocationEnum.adUseClient;
                ADODB.Fields fdsLoop = recLoop.Fields;
                ADODB.Fields fdsMaster = recMaster.Fields;
                foreach (ADODB.Field fldIn in fdsMaster)
                {
                    if (fldIn.Type.ToString().IndexOf("Char") > 0)
                    {
                        fdsLoop.Append(fldIn.Name,
                            fldIn.Type,
                            fldIn.DefinedSize + 30,
                            ADODB.FieldAttributeEnum.adFldIsNullable,
                            null);
                    }
                    else
                    {
                        fdsLoop.Append(fldIn.Name,
                        fldIn.Type,
                        fldIn.DefinedSize,
                        ADODB.FieldAttributeEnum.adFldIsNullable,
                        null);
                    }
                }
                recLoop.Open(System.Reflection.Missing.Value, System.Reflection.Missing.Value, ADODB.CursorTypeEnum.adOpenStatic, ADODB.LockTypeEnum.adLockOptimistic, 0);
                
                recLoop.AddNew(System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                
                while (!recMaster.EOF)
                {
                    for (int columnIndex = 0; columnIndex < recMaster.Fields.Count; columnIndex++)
                    {
                        recLoop.Fields[columnIndex].Value = recMaster.Fields[columnIndex].Value;
                        if (recLoop.Fields[columnIndex].Value.ToString().Length > 0)
                        {
                            if ((recLoop.Fields[columnIndex].Value.ToString().IndexOf("\\", 0) + 1) > 0)
                            {
                                recLoop.Fields[columnIndex].Value = recLoop.Fields[columnIndex].Value.ToString().Replace("\\", "\\\\");
                            }
                            if ((recLoop.Fields[columnIndex].Value.ToString().IndexOf(",", 0) + 1) > 0)
                            {
                                recLoop.Fields[columnIndex].Value = recLoop.Fields[columnIndex].Value.ToString().Replace(",", "\\,");
                            }
                            if ((recLoop.Fields[columnIndex].Value.ToString().IndexOf(System.Environment.NewLine, 0) + 1) > 0)
                            {
                                recLoop.Fields[columnIndex].Value = recLoop.Fields[columnIndex].Value.ToString().Replace(System.Environment.NewLine, " ");
                            }
                        }
                    }
                    strRecord = recLoop.GetString(ADODB.StringFormatEnum.adClipString, 1, ",", System.Environment.NewLine, "\\N");
                    recLoop.MovePrevious();
                    sw.Write(strRecord);
                    recMaster.MoveNext();
                }
                recMaster.Close();
                recMaster.ActiveConnection = null;
                try
                {
                    recLoop.Close();
                }
                catch
                {

                }
                sw.Close();
                ExecuteSQL(strInfile);
                File.Delete(strFileName);
                recLoop = null;
 
        }

        public object MySQLName(object tmp)
        {
            return "`" + tmp.ToString() + "`";
        }
        private void ExecuteSQL(string sql)
        {
            object objOut;
            //don't execute any sql after a cancel is issued, unless it is a drop schema 
            if ((bwkr.CancellationPending==false) || (bwkr.CancellationPending==true && sql.IndexOf("DROP SCHEMA")>-1))
                 conMySQL.Execute(sql, out objOut, 0);  // MMM
        }
        

        private void btnExit_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            if (bwkr.IsBusy) bwkr.CancelAsync();
            CancelProcess("Process cancelled by user.");
            StepStarted("Cancelling process.");
        }


    }
}