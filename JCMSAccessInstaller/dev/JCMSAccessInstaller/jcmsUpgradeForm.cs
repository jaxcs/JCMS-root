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
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.OleDb;
using System.Data.Odbc;
using MySql.Data.MySqlClient;

namespace JCMSAccessInstaller
{
    public partial class jcmsUpgradeForm : Form
    {
        private String driverVersion = "MySQL ODBC 3.51 Driver";
        private String theHost = "";
        private String theDatabase = "";
        private String theUsername = "";
        private String thePassword = "";
        private String thePort = "";
        private MySqlConnection conMySQL;
        public jcmsUpgradeForm()
        {
            InitializeComponent();
        }

        private void upgradeButton_Click(object sender, EventArgs e)
        {
            bool ready = true;
            string strMessage = "";


            if (this.updateDir.Text == "")
            {
                ready = false;
                strMessage = "Please provide the location of your JCMS interface file (jcms.mdb)";
            }
            else
            {
                if (!System.IO.File.Exists(this.updateDir.Text))
                {
                    ready = false;
                    if (strMessage != "")
                    {
                        strMessage = strMessage + "\n";
                    }
                    strMessage = strMessage + string.Format("The requested file {0} could not be found, please check that file exists.", this.updateDir.Text);
                }
                else
                {
                    if (checkFileOpen())
                    {
                        ready = false;
                        if (strMessage != "")
                        {
                            strMessage = strMessage + "\n";
                        }
                        strMessage = strMessage + string.Format("The file {0} is open, please close the requested file before upgrading.", this.updateDir.Text);
                    }
                }
            }
            if (this.host.Text == "")
            {
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }                
                strMessage = strMessage + "Please provide the host on which your MySQL server is located (if on the machine you're running this application type 'localhost')";
            }
            if(this.port.Text == ""){
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide a MySQL port (likely 3306)";
            }
            if(this.username.Text == ""){
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide a MySQL username";
            }/*
            if(this.password.Text == ""){
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide the password associated with the provided MySQL username";
            }*/
            if(this.databaseDropdown.Text == ""){
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please select a database to upgrade";
            }
            if (ready)
            {
                this.Hide();
                processProgress progressForm = new processProgress(theHost, thePort, theUsername, thePassword, this.databaseDropdown.Text, this.updateDir.Text);
                progressForm.Show();
            }
            else
            {
                MessageBox.Show(strMessage);
            }
        }

        private bool checkFileOpen()
        {
            System.IO.FileInfo file = new System.IO.FileInfo(this.updateDir.Text);
            System.IO.FileStream stream = null;

            try
            {
                stream = file.Open(System.IO.FileMode.Open, System.IO.FileAccess.ReadWrite, System.IO.FileShare.None);
            }
            catch (Exception)
            {
                return true; //file is open
            }
            finally
            {
                if (stream != null)
                {
                    stream.Close();
                }
            }
            return false;
        }

        private void fileDir_Click(object sender, EventArgs e)
        {
            OpenFileDialog OFG = new OpenFileDialog();
            OFG.FileName = "";
            OFG.Filter = "Access Database|*.mdb";
            OFG.ShowDialog();
            updateDir.Text = OFG.FileName;
        }

        private void cancel_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        private void databaseDropdown_GotFocus(object sender, EventArgs e)
        {
            populateDropDown();
        }

        private void getLinkedDatabase()
        {
            try
            {
                //try 2
                System.Data.OleDb.OleDbConnection conn = new OleDbConnection();
                
                ADODB.Connection conJCMS = new ADODB.Connection();
                string strConx = "Provider=Microsoft.Jet.OLEDB.4.0;Persist Security Info=True;Data Source=" + updateDir.Text + ";";
                conJCMS.Mode = ADODB.ConnectModeEnum.adModeReadWrite;
                conJCMS.Open(strConx, "", "", 0);
                ADODB.Recordset tables = new ADODB.Recordset();
                tables.Open("SELECT * FROM Mouse;", conJCMS, ADODB.CursorTypeEnum.adOpenStatic, ADODB.LockTypeEnum.adLockBatchOptimistic, 0);

                ADOX.Catalog catalog = new ADOX.Catalog();
                catalog.ActiveConnection = conJCMS;

                foreach (ADOX.Table tbl in catalog.Tables)
                {
                    if (tbl.Type == "LINK")
                    {
                        string strSourceDbFullPath = tbl.Properties["Jet OLEDB:Link DataSource"].Value.ToString();
                        string strSourceDbPath = strSourceDbFullPath.Substring(0, strSourceDbFullPath.LastIndexOf("\\") + 1);
                        string strSourceDbName = strSourceDbFullPath.Substring(strSourceDbFullPath.LastIndexOf("\\") + 1);
                        break;
                    }
                }

               


                //try one
                if (Environment.Is64BitOperatingSystem)
                {
                    conn.ConnectionString = @"Provider=Microsoft.ACE.OLEDB.12.0;" + @"Data Source=" + updateDir.Text +";";
                }
                else
                {
                    conn.ConnectionString = @"Provider=Microsoft.Jet.OLEDB.4.0;" + @"Data Source=" + updateDir.Text + ";";
                }
                
                conn.Open();
                ADOX.Catalog theCatalog = new ADOX.Catalog();
                theCatalog.ActiveConnection = conn;

                string databaseQuery = "SELECT * FROM Mouse;";
                OleDbCommand theCommand = new OleDbCommand(databaseQuery);
                theCommand.Connection = conn;
                DataTable schemaTable = conn.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, new object[] { null, null, null, "TABLE" });
                foreach (DataRow row in schemaTable.Rows)
                {
                    foreach(var item in row.ItemArray){
                        Console.WriteLine(item);
                    }
                }
                OleDbDataReader theReader = theCommand.ExecuteReader();
                while (theReader.Read())
                {
                    Console.WriteLine(theReader.GetString(1));
                }
                conn.Close();
            }
            catch (Exception e) { Console.WriteLine(e); }
        }

        private void populateDropDown()
        {
        //    getLinkedDatabase();
            theHost = host.Text;
            thePort = port.Text;
            try
            {
                if (!host.Text.Equals("") && !port.Text.Equals("") && !username.Text.Equals(""))
                {
                    conMySQL = new MySqlConnection();
                    conMySQL.ConnectionString = "server=" + theHost + ";userid=" + theUsername + ";password=" + thePassword;
                    conMySQL.Open();
                    string getDatabasesString = "SHOW SCHEMAS;";
                    MySqlCommand databaseCommand = new MySqlCommand(getDatabasesString, conMySQL);
                    MySqlDataReader theReader = databaseCommand.ExecuteReader();
                    databaseDropdown.Items.Clear();
                    while(theReader.Read())
                    {
                        Console.WriteLine(theReader[0]);
                        databaseDropdown.Items.Add(theReader[0]);
                    }
                    conMySQL.Close();
                }
            }
            catch(MySqlException e){
                MessageBox.Show("There was an error connecting to the MySQL server: " + e.Message);
            }
            catch(Exception e){
                Console.WriteLine("Problem connecting: " + e);
            }
        }

        private void host_TextChanged(object sender, EventArgs e)
        {
            theHost = host.Text;
        }

        private void port_TextChanged(object sender, EventArgs e)
        {
            thePort = port.Text;
        }

        private void username_TextChanged(object sender, EventArgs e)
        {
            theUsername = username.Text;
        }

        private void password_TextChanged(object sender, EventArgs e)
        {
            thePassword = password.Text;
        }
    }
}
