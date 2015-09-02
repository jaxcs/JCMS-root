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
using System.IO;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;

namespace JCMSAccessInstaller
{
    public partial class ODBCWarning : Form
    {
        public ODBCWarning()
        {
            InitializeComponent();
        }

        private void continueButton_Click(object sender, EventArgs e)
        {                        
            Process p = new Process();

            p.StartInfo.FileName = "msiexec";
            p.StartInfo.Arguments = "/i \"MySQLODBC32.msi\"";
            
            p.Start();
            p.WaitForExit();
            //if MySQL not installed AND doing an install.
            if (!programVariables.getMySQLInstalled() && programVariables.getInstall())
            {
                this.Hide();
                installMySQL installMySQLPage = new installMySQL();
                installMySQLPage.Show();
            }
            //if mysql already installed and doing an install
            else if (programVariables.getInstall())
            {
                this.Hide();
                jcmsInstallForm theInstallForm = new jcmsInstallForm();
                theInstallForm.Show();
            }
            else
            {
                this.Hide();
                jcmsUpgradeForm theUpgradeForm = new jcmsUpgradeForm();
                theUpgradeForm.Show();
            }
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }
    }
}
