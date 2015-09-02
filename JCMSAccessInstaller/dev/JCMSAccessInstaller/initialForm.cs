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
using System.Drawing;
using System.Linq;
using System.Text;
using System.Management;
using System.Windows.Forms;
using Microsoft.Win32;

namespace JCMSAccessInstaller
{
    public partial class initialForm : Form
    {

        ArrayList mySQLPrograms = null;
        ArrayList jcmsPrograms = null;

        public initialForm()
        {
            InitializeComponent();
        }

        private void initialForm_Show(object sender, EventArgs e)
        {
            programVariables.initialize();
            mySQLPrograms = checkForApp("MySQL");
            jcmsPrograms = checkForApp("JAX-CMS");
            
            foreach(string program in mySQLPrograms){
                if (program.Contains("Server"))
                {
                    programVariables.setMySQLInstalled(true);
                    this.installedSoftwareText.Text = installedSoftwareText.Text + "\nMySQL Found..."; 
                }
                if (program.Contains("ODBC"))
                {
                    programVariables.setODBCInstalled(true);
                    this.installedSoftwareText.Text = installedSoftwareText.Text + "\nODBC Connector Found..."; 
                }
            }
            if (jcmsPrograms.Count > 0)
            {
                this.installedSoftwareText.Text = installedSoftwareText.Text + "\nJCMS Installation Found...";
            }
        }

        private void installButton_Click(object sender, EventArgs e)
        {
            programVariables.setInstall(true);
            if (!programVariables.getODBCInstalled())
            {
                this.Hide();
                ODBCWarning theWarningPage = new ODBCWarning();
                theWarningPage.Show();
            }
            else if (!programVariables.getMySQLInstalled())
            {
                this.Hide();
                installMySQL theMySQLPage = new installMySQL();
                theMySQLPage.Show();
            }
            else
            {
                this.Hide();
                jcmsInstallForm installJCMS = new jcmsInstallForm();
                installJCMS.Show();
            }
        }

        private void upgradeButton_Click(object sender, EventArgs e)
        {
            programVariables.setUpgrade(true);
            if (!programVariables.getODBCInstalled())
            {
                this.Hide();
                ODBCWarning theWarningPage = new ODBCWarning();
                theWarningPage.Show();
            }
            else
            {
                if (programVariables.getMySQLInstalled())
                {
                    this.Hide();
                    jcmsUpgradeForm upgradeForm = new jcmsUpgradeForm();
                    upgradeForm.Show();
                }
                else
                {
                    this.Hide();
                    installMySQL installMySqlForm = new installMySQL();
                    installMySqlForm.Show();
                }
            }
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        public ArrayList checkForApp(string p_name)
        {
            string displayName;
            RegistryKey key;
            ArrayList mySQLPrograms = new ArrayList();
            try
            {
                // search in: CurrentUser
                key = Registry.CurrentUser.OpenSubKey(@"SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall");
                if (key != null)
                {
                    foreach (String keyName in key.GetSubKeyNames())
                    {
                        RegistryKey subkey = key.OpenSubKey(keyName);
                        if (subkey != null)
                        {
                            displayName = subkey.GetValue("DisplayName") as string;
                            if (displayName != null && displayName.Contains(p_name) == true)
                            {
                                if (!mySQLPrograms.Contains(displayName))
                                {
                                    mySQLPrograms.Add(displayName);
                                }
                            }
                        }
                    }
                }

                // search in: LocalMachine_32
                key = Registry.LocalMachine.OpenSubKey(@"SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall");
                if (key != null)
                {
                    foreach (String keyName in key.GetSubKeyNames())
                    {
                        RegistryKey subkey = key.OpenSubKey(keyName);
                        if (subkey != null)
                        {
                            displayName = subkey.GetValue("DisplayName") as string;
                            if (displayName != null && displayName.Contains(p_name) == true)
                            {
                                if (!mySQLPrograms.Contains(displayName))
                                {
                                    mySQLPrograms.Add(displayName);
                                }
                            }
                        }
                    }
                }

                // search in: LocalMachine_64
                key = Registry.LocalMachine.OpenSubKey(@"SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall");
                if (key != null)
                {
                    foreach (String keyName in key.GetSubKeyNames())
                    {
                        RegistryKey subkey = key.OpenSubKey(keyName);
                        if (subkey != null)
                        {
                            displayName = subkey.GetValue("DisplayName") as string;
                            if (displayName != null && displayName.Contains(p_name) == true)
                            {
                                if (!mySQLPrograms.Contains(displayName))
                                {
                                    mySQLPrograms.Add(displayName);
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e){
                Console.WriteLine(e);
            }
            return mySQLPrograms;
        }

        public void myAdd(String program, ArrayList programs)
        {
            if (!programs.Contains(program))
            {
                programs.Add(program);
            }
        }
    }
}
