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
    partial class installSuccess
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(installSuccess));
            this.label2 = new System.Windows.Forms.Label();
            this.nextStepLabel = new System.Windows.Forms.Label();
            this.Close = new System.Windows.Forms.Button();
            this.statusText = new System.Windows.Forms.TextBox();
            this.forumsLink = new System.Windows.Forms.LinkLabel();
            this.faqsLink = new System.Windows.Forms.LinkLabel();
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(16, 191);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(94, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Click \'Close\' to exit";
            // 
            // nextStepLabel
            // 
            this.nextStepLabel.AutoSize = true;
            this.nextStepLabel.Location = new System.Drawing.Point(16, 266);
            this.nextStepLabel.MaximumSize = new System.Drawing.Size(450, 0);
            this.nextStepLabel.Name = "nextStepLabel";
            this.nextStepLabel.Size = new System.Drawing.Size(440, 26);
            this.nextStepLabel.TabIndex = 2;
            this.nextStepLabel.Text = "Next Step: (1) Create a password for user Admin, (2) Create user mtsadmin. See th" +
    "e \'Getting Started\' section of JCMS User Guide.";
            // 
            // Close
            // 
            this.Close.Location = new System.Drawing.Point(446, 288);
            this.Close.Name = "Close";
            this.Close.Size = new System.Drawing.Size(75, 23);
            this.Close.TabIndex = 3;
            this.Close.Text = "Close";
            this.Close.UseVisualStyleBackColor = true;
            this.Close.Click += new System.EventHandler(this.Close_Click);
            // 
            // statusText
            // 
            this.statusText.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.statusText.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.statusText.Location = new System.Drawing.Point(19, 22);
            this.statusText.Multiline = true;
            this.statusText.Name = "statusText";
            this.statusText.Size = new System.Drawing.Size(502, 166);
            this.statusText.TabIndex = 4;
            // 
            // forumsLink
            // 
            this.forumsLink.AutoSize = true;
            this.forumsLink.Location = new System.Drawing.Point(16, 214);
            this.forumsLink.Name = "forumsLink";
            this.forumsLink.Size = new System.Drawing.Size(72, 13);
            this.forumsLink.TabIndex = 5;
            this.forumsLink.TabStop = true;
            this.forumsLink.Text = "JCMS Forums";
            this.forumsLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.forumsLink_LinkClicked);
            // 
            // faqsLink
            // 
            this.faqsLink.AutoSize = true;
            this.faqsLink.Location = new System.Drawing.Point(16, 240);
            this.faqsLink.Name = "faqsLink";
            this.faqsLink.Size = new System.Drawing.Size(64, 13);
            this.faqsLink.TabIndex = 6;
            this.faqsLink.TabStop = true;
            this.faqsLink.Text = "JCMS FAQs";
            this.faqsLink.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.faqsLink_LinkClicked);
            // 
            // installSuccess
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(531, 320);
            this.Controls.Add(this.faqsLink);
            this.Controls.Add(this.forumsLink);
            this.Controls.Add(this.statusText);
            this.Controls.Add(this.Close);
            this.Controls.Add(this.nextStepLabel);
            this.Controls.Add(this.label2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "installSuccess";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "JCMS Installer/Upgrader";
            this.Load += new System.EventHandler(this.installSuccess_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label nextStepLabel;
        private System.Windows.Forms.Button Close;
        private System.Windows.Forms.TextBox statusText;
        private System.Windows.Forms.LinkLabel forumsLink;
        private System.Windows.Forms.LinkLabel faqsLink;
    }
}