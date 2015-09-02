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
    partial class jcmsUpgradeForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(jcmsUpgradeForm));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.password = new System.Windows.Forms.TextBox();
            this.host = new System.Windows.Forms.TextBox();
            this.port = new System.Windows.Forms.TextBox();
            this.username = new System.Windows.Forms.TextBox();
            this.databaseDropdown = new System.Windows.Forms.ComboBox();
            this.upgradeButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.fileDirButton = new System.Windows.Forms.Button();
            this.updateDir = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 54);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(135, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "JCMS Interface Information";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(57, 77);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(124, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "JCMS Interface Location";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 116);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(146, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "MySQL Database Information";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(57, 136);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(67, 13);
            this.label4.TabIndex = 3;
            this.label4.Text = "MySQL Host";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(57, 162);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(64, 13);
            this.label5.TabIndex = 4;
            this.label5.Text = "MySQL Port";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(57, 189);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(93, 13);
            this.label6.TabIndex = 5;
            this.label6.Text = "MySQL Username";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(57, 243);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(115, 13);
            this.label7.TabIndex = 6;
            this.label7.Text = "JCMS Database Name";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(57, 216);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(91, 13);
            this.label8.TabIndex = 7;
            this.label8.Text = "MySQL Password";
            // 
            // password
            // 
            this.password.Location = new System.Drawing.Point(179, 213);
            this.password.Name = "password";
            this.password.Size = new System.Drawing.Size(294, 20);
            this.password.TabIndex = 7;
            this.password.UseSystemPasswordChar = true;
            this.password.LostFocus += new System.EventHandler(this.password_TextChanged);
            // 
            // host
            // 
            this.host.Location = new System.Drawing.Point(179, 133);
            this.host.Name = "host";
            this.host.Size = new System.Drawing.Size(294, 20);
            this.host.TabIndex = 4;
            this.host.Text = "localhost";
            this.host.LostFocus += new System.EventHandler(this.host_TextChanged);
            // 
            // port
            // 
            this.port.Location = new System.Drawing.Point(179, 159);
            this.port.Name = "port";
            this.port.Size = new System.Drawing.Size(294, 20);
            this.port.TabIndex = 5;
            this.port.Text = "3306";
            this.port.LostFocus += new System.EventHandler(this.port_TextChanged);
            // 
            // username
            // 
            this.username.Location = new System.Drawing.Point(179, 186);
            this.username.Name = "username";
            this.username.Size = new System.Drawing.Size(294, 20);
            this.username.TabIndex = 6;
            this.username.LostFocus += new System.EventHandler(this.username_TextChanged);
            // 
            // databaseDropdown
            // 
            this.databaseDropdown.FormattingEnabled = true;
            this.databaseDropdown.Location = new System.Drawing.Point(179, 240);
            this.databaseDropdown.Name = "databaseDropdown";
            this.databaseDropdown.Size = new System.Drawing.Size(294, 21);
            this.databaseDropdown.TabIndex = 8;
            this.databaseDropdown.GotFocus += new System.EventHandler(this.databaseDropdown_GotFocus);
            // 
            // upgradeButton
            // 
            this.upgradeButton.Location = new System.Drawing.Point(483, 294);
            this.upgradeButton.Name = "upgradeButton";
            this.upgradeButton.Size = new System.Drawing.Size(75, 23);
            this.upgradeButton.TabIndex = 9;
            this.upgradeButton.Text = "Upgrade";
            this.upgradeButton.UseVisualStyleBackColor = true;
            this.upgradeButton.Click += new System.EventHandler(this.upgradeButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(12, 294);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 10;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancel_Click);
            // 
            // fileDirButton
            // 
            this.fileDirButton.Location = new System.Drawing.Point(532, 72);
            this.fileDirButton.Name = "fileDirButton";
            this.fileDirButton.Size = new System.Drawing.Size(26, 23);
            this.fileDirButton.TabIndex = 3;
            this.fileDirButton.Text = "...";
            this.fileDirButton.UseVisualStyleBackColor = true;
            this.fileDirButton.Click += new System.EventHandler(this.fileDir_Click);
            // 
            // updateDir
            // 
            this.updateDir.Location = new System.Drawing.Point(187, 74);
            this.updateDir.Name = "updateDir";
            this.updateDir.Size = new System.Drawing.Size(339, 20);
            this.updateDir.TabIndex = 2;
            // 
            // jcmsUpgradeForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(570, 329);
            this.Controls.Add(this.updateDir);
            this.Controls.Add(this.fileDirButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.upgradeButton);
            this.Controls.Add(this.databaseDropdown);
            this.Controls.Add(this.username);
            this.Controls.Add(this.port);
            this.Controls.Add(this.host);
            this.Controls.Add(this.password);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "jcmsUpgradeForm";
            this.Text = "JCMS Installer/Upgrader";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox password;
        private System.Windows.Forms.TextBox host;
        private System.Windows.Forms.TextBox port;
        private System.Windows.Forms.TextBox username;
        private System.Windows.Forms.ComboBox databaseDropdown;
        private System.Windows.Forms.Button upgradeButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button fileDirButton;
        private System.Windows.Forms.TextBox updateDir;
    }
}