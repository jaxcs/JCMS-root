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

namespace JCMSAccessInstaller
{
    public partial class installSuccess : Form
    {
        bool processSuccess;
        string errorMsgs;

        public installSuccess(bool success, string errors)
        {
            InitializeComponent();
            processSuccess = success;
            errorMsgs = errors;
        }

        private void Close_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }

        private void installSuccess_Load(object sender, EventArgs e)
        {
            if (programVariables.getInstall())
            {
                if (processSuccess)
                {
                    this.statusText.Text = "Your JCMS installation was successful! ";
                    this.statusText.Text = this.statusText.Text + "\n" + errorMsgs;
                    try
                    {
                        System.Diagnostics.Process.Start(".\\docs\\ReadMe.pdf");
                    }
                    catch (Exception ex) { Console.WriteLine(ex); }

                }
                else
                {
                    this.statusText.Text = "Your JCMS install was unsuccessful. Please see your install log for more information "
                    + "\nand visit http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
                    this.nextStepLabel.Text = "";
                }
            }
            else
            {
                this.nextStepLabel.Text = "";
                if (processSuccess)
                {
                    this.statusText.Text = "Your JCMS upgrade was successful! ";
                    this.statusText.Text = this.statusText.Text + "\n" + errorMsgs;
                }
                else
                {
                    this.statusText.Text = "Your JCMS upgrade was unsuccessful. Please see your upgrade log for more information "
                    + "\nand visit http://community.jax.org/jcms_discussion_forum/default.aspx for help.";
                }
            }
        }

        private void forumsLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.forumsLink.LinkVisited = true;

            System.Diagnostics.Process.Start("http://community.jax.org/jcms_discussion_forum/default.aspx");
        }

        private void faqsLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.faqsLink.LinkVisited = true;

            System.Diagnostics.Process.Start("http://colonymanagement.jax.org/support-2/faqs/");
        }
    }
}
