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
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
namespace JCMSAccessInstaller
{
    public partial class jcmsInstallForm : Form
    {
        public jcmsInstallForm()
        {
            InitializeComponent();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        private void browseButton_Click(object sender, EventArgs e)
        {   /*
            OpenFileDialog OFG = new OpenFileDialog();
            FolderBrowserDialog FBD = new FolderBrowserDialog();
            FBD.SelectedPath = "";
            FBD.ShowNewFolderButton = true;
            FBD.ShowDialog();
            installDir.Text = FBD.SelectedPath;
            */
            SaveFileDialog save = new SaveFileDialog();
            save.Filter = "Access (*.mdb)|*.mdb";
            save.FileName = "JCMS.mdb";
            save.ShowDialog();
            installDir.Text = save.FileName;
            /*
            OFG.FileName = "";
            OFG.Filter = "Access Database|*.mdb";
            OFG.ShowDialog();
            installDir.Text = OFG.FileName;
            */
        }

        private void installButton_Click(object sender, EventArgs e)
        {
            bool ready = true;
            string strMessage = "";

            if (this.installDir.Text == "")
            {
                ready = false;
                strMessage = "Please choose a directory in which you would like to install JCMS.";
            }
            if(!Directory.Exists(System.IO.Path.GetDirectoryName(this.installDir.Text)))
            {
                ready = false;
                strMessage = "The selected directory could not be found.";
            }
            if(!System.IO.Path.GetExtension(this.installDir.Text).Equals(".mdb")){
                ready = false;
                strMessage = "File extension must be .mdb";
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
            if (this.port.Text == "")
            {
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide a MySQL port (likely 3443)";
            }
            if (this.username.Text == "")
            {
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide a MySQL username";
            }/*
            if (this.password.Text == "")
            {
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please provide the password associated with the provided MySQL username";
            }*/
            if (this.database.Text == "")
            {
                ready = false;
                if (strMessage != "")
                {
                    strMessage = strMessage + "\n";
                }
                strMessage = strMessage + "Please select a database to upgrade";
            }
            if (ready)
            {
                processProgress theProgress = new processProgress(this.host.Text, this.port.Text, this.username.Text, this.password.Text, this.database.Text, this.installDir.Text);
                this.Hide();
                theProgress.Show();
            }
            else
            {
                MessageBox.Show(strMessage);
            }
        }
    }
}
