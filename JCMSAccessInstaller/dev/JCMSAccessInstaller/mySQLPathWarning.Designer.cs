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
    partial class mySQLPathWarning
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(mySQLPathWarning));
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.forumsLink = new System.Windows.Forms.LinkLabel();
            this.faqsLink = new System.Windows.Forms.LinkLabel();
            this.nextButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // textBox1
            // 
            this.textBox1.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.textBox1.Location = new System.Drawing.Point(46, 114);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(515, 56);
            this.textBox1.TabIndex = 0;
            this.textBox1.Text = resources.GetString("textBox1.Text");
            // 
            // forumsLink
            // 
            this.forumsLink.AutoSize = true;
            this.forumsLink.Location = new System.Drawing.Point(46, 187);
            this.forumsLink.Name = "forumsLink";
            this.forumsLink.Size = new System.Drawing.Size(72, 13);
            this.forumsLink.TabIndex = 1;
            this.forumsLink.TabStop = true;
            this.forumsLink.Text = "JCMS Forums";
            this.forumsLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.forumsLink_LinkClicked);
            // 
            // faqsLink
            // 
            this.faqsLink.AutoSize = true;
            this.faqsLink.Location = new System.Drawing.Point(46, 171);
            this.faqsLink.Name = "faqsLink";
            this.faqsLink.Size = new System.Drawing.Size(64, 13);
            this.faqsLink.TabIndex = 2;
            this.faqsLink.TabStop = true;
            this.faqsLink.Text = "JCMS FAQs";
            this.faqsLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.faqsLink_LinkClicked);
            // 
            // nextButton
            // 
            this.nextButton.Location = new System.Drawing.Point(515, 303);
            this.nextButton.Name = "nextButton";
            this.nextButton.Size = new System.Drawing.Size(75, 23);
            this.nextButton.TabIndex = 3;
            this.nextButton.Text = "Close";
            this.nextButton.UseVisualStyleBackColor = true;
            this.nextButton.Click += new System.EventHandler(this.nextButton_Click);
            // 
            // mySQLPathWarning
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(602, 338);
            this.Controls.Add(this.nextButton);
            this.Controls.Add(this.faqsLink);
            this.Controls.Add(this.forumsLink);
            this.Controls.Add(this.textBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "mySQLPathWarning";
            this.Text = "JCMS Installer/Upgrader";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.LinkLabel forumsLink;
        private System.Windows.Forms.LinkLabel faqsLink;
        private System.Windows.Forms.Button nextButton;
    }
}