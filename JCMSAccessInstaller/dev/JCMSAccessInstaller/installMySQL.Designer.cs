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
namespace JCMSAccessInstaller
{
    partial class installMySQL
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(installMySQL));
            this.installButton = new System.Windows.Forms.Button();
            this.skipButton = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.jcmsFaqsLink = new System.Windows.Forms.LinkLabel();
            this.SuspendLayout();
            // 
            // installButton
            // 
            this.installButton.AccessibleName = "installButton";
            this.installButton.Location = new System.Drawing.Point(449, 304);
            this.installButton.Name = "installButton";
            this.installButton.Size = new System.Drawing.Size(82, 23);
            this.installButton.TabIndex = 1;
            this.installButton.Text = "Install MySQL";
            this.installButton.UseVisualStyleBackColor = true;
            this.installButton.Click += new System.EventHandler(this.installButton_Click);
            // 
            // skipButton
            // 
            this.skipButton.AccessibleName = "skipButton";
            this.skipButton.Location = new System.Drawing.Point(361, 304);
            this.skipButton.Name = "skipButton";
            this.skipButton.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.skipButton.Size = new System.Drawing.Size(82, 23);
            this.skipButton.TabIndex = 0;
            this.skipButton.Text = "Skip MySQL";
            this.skipButton.UseVisualStyleBackColor = true;
            this.skipButton.Click += new System.EventHandler(this.skipButton_Click);
            // 
            // textBox1
            // 
            this.textBox1.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.textBox1.Location = new System.Drawing.Point(39, 129);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(463, 50);
            this.textBox1.TabIndex = 3;
            this.textBox1.Text = "To run the JCMS Installer-Upgrader you will need MySQL. If you already have MySQL" +
    " installed on this machine please press skip, otherwise click Install MySQL to b" +
    "egin the MySQL installer.";
            this.textBox1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // jcmsFaqsLink
            // 
            this.jcmsFaqsLink.AutoSize = true;
            this.jcmsFaqsLink.Location = new System.Drawing.Point(36, 182);
            this.jcmsFaqsLink.Name = "jcmsFaqsLink";
            this.jcmsFaqsLink.Size = new System.Drawing.Size(110, 13);
            this.jcmsFaqsLink.TabIndex = 4;
            this.jcmsFaqsLink.TabStop = true;
            this.jcmsFaqsLink.Text = "JCMS FAQs and Help";
            this.jcmsFaqsLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.jcmsFaqsLink_LinkClicked);
            // 
            // installMySQL
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(543, 339);
            this.Controls.Add(this.jcmsFaqsLink);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.skipButton);
            this.Controls.Add(this.installButton);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "installMySQL";
            this.Text = "JCMS Installer/Upgrader";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button installButton;
        private System.Windows.Forms.Button skipButton;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.LinkLabel jcmsFaqsLink;
    }
}