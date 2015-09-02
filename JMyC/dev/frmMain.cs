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
using System.Data;
using System.Drawing;
using System.Diagnostics;
using System.Windows.Forms;
using System.Data.Odbc;
using System.Text.RegularExpressions;
using System.IO;
using System.Threading;
using System.ComponentModel;

namespace JCMS_DB_Converter

{
	public partial class frmMain
	{
        private void btnOk_Click(object sender, System.EventArgs e)
		{
			bool blnProceed = false;
			string strMessage = null;
			
            if (true == (txtMySQLHost.Text.Length == 0))
			{
					strMessage = "Please enter MySQL host name";
			}
			else if (true == (txtMySQLPort.Text.Length == 0))
			{
					strMessage = "Please enter MySQL port number";
			}
			else if (true == (txtMySQLUserName.Text.Length == 0))
			{
					strMessage = "Please enter MySQL user name";
			}
			else
			{
					blnProceed = true;
			}
			if (blnProceed == true)
			{
              
                //create the dsn
                Create_DSN();
                //setup the progress form
                frmProgress frmProgressForm = new frmProgress(txtJCMSDb.Text, txtMySQLUserName.Text, txtMySQLPwd.Text, txtMySQLPort.Text, txtMySQLHost.Text, textBoxDatabaseName.Text, chkDisable.Checked);
                frmProgressForm.Show();
                this.Hide();
			}
			else
			{
				 MessageBox.Show(strMessage);
			}
		}

		private void btnCancel_Click(object sender, System.EventArgs e)
		{
			System.Environment.Exit(1);
		}


        private void btnOpenInterface_Click(object sender, EventArgs e)
        {
            dlgOpen.FileName = "";
            dlgOpen.Filter = "Access Database|*.mdb";
            dlgOpen.ShowDialog();
            txtJCMSDb.Text = dlgOpen.FileName;
        }

            private bool Create_DSN()
        {

            try
            {
                FileStream fs;
                StreamWriter sw;

                fs = File.Create("C:\\Program Files\\Common Files\\ODBC\\Data Sources\\myJCMS.dsn");
                sw = new StreamWriter(fs);
                sw.WriteLine("[ODBC]");
                sw.WriteLine("DRIVER={MySQL ODBC 3.51 Driver}");
                sw.WriteLine("SERVER=" + txtMySQLHost.Text);
                sw.WriteLine("PORT=" + txtMySQLPort.Text);
                //sw.WriteLine("DATABASE=jcms_db");
                sw.WriteLine("DATABASE=" + textBoxDatabaseName.Text);
                sw.WriteLine("UID=" + txtMySQLUserName.Text);
                sw.WriteLine("PWD=" + txtMySQLPwd.Text);
                sw.Close();
                fs.Close(); 

                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
            }

        }

        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmAbout About=new frmAbout();
            About.ShowDialog();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            FileStream fs = File.Create("test.txt");
            StreamWriter sw = new StreamWriter(fs);
            sw.Write("Testing 1 2 3 ");
            sw.Close();
            fs.Close(); 

        }

        private void label9_Click(object sender, EventArgs e)
        {

        }
    }
} 


