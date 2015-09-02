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
    public partial class mySQLPathWarning : Form
    {
        public mySQLPathWarning()
        {
            InitializeComponent();
        }

        private void faqsLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.faqsLink.LinkVisited = true;

            System.Diagnostics.Process.Start("http://colonymanagement.jax.org/support-2/faqs/");
        }

        private void forumsLink_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.forumsLink.LinkVisited = true;

            System.Diagnostics.Process.Start("http://community.jax.org/jcms_discussion_forum/default.aspx");
        }

        private void nextButton_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(1);
        }
    }
}
