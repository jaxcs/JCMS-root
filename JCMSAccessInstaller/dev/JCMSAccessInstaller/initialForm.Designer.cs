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
    partial class initialForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(initialForm));
            this.upgradeButton = new System.Windows.Forms.Button();
            this.installButton = new System.Windows.Forms.Button();
            this.installedSoftwareText = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // upgradeButton
            // 
            this.upgradeButton.AccessibleName = "upgradeButton";
            this.upgradeButton.Location = new System.Drawing.Point(486, 273);
            this.upgradeButton.Name = "upgradeButton";
            this.upgradeButton.Size = new System.Drawing.Size(75, 23);
            this.upgradeButton.TabIndex = 2;
            this.upgradeButton.Text = "Upgrade";
            this.upgradeButton.UseVisualStyleBackColor = true;
            this.upgradeButton.Click += new System.EventHandler(this.upgradeButton_Click);
            // 
            // installButton
            // 
            this.installButton.AccessibleName = "installButton";
            this.installButton.Location = new System.Drawing.Point(405, 273);
            this.installButton.Name = "installButton";
            this.installButton.Size = new System.Drawing.Size(75, 23);
            this.installButton.TabIndex = 1;
            this.installButton.Text = "Install";
            this.installButton.UseVisualStyleBackColor = true;
            this.installButton.Click += new System.EventHandler(this.installButton_Click);
            // 
            // installedSoftwareText
            // 
            this.installedSoftwareText.AutoSize = true;
            this.installedSoftwareText.Location = new System.Drawing.Point(179, 100);
            this.installedSoftwareText.Name = "installedSoftwareText";
            this.installedSoftwareText.Size = new System.Drawing.Size(196, 13);
            this.installedSoftwareText.TabIndex = 2;
            this.installedSoftwareText.Text = "Looking for necessary JCMS Software...";
            // 
            // cancelButton
            // 
            this.cancelButton.AccessibleName = "cancelButton";
            this.cancelButton.Location = new System.Drawing.Point(13, 273);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 3;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(106, 67);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(356, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "Before upgrading it is highly recommended that you backup your database";
            // 
            // initialForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(573, 308);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.installedSoftwareText);
            this.Controls.Add(this.installButton);
            this.Controls.Add(this.upgradeButton);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "initialForm";
            this.Text = "JCMS Installer/Upgrader";
            this.Shown += new System.EventHandler(this.initialForm_Show);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button upgradeButton;
        private System.Windows.Forms.Button installButton;
        private System.Windows.Forms.Label installedSoftwareText;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Label label1;
    }
}

