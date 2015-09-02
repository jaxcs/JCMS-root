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
//INSTANT C# NOTE: Formerly VB.NET project-level imports:
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Diagnostics;
using System.Windows.Forms;

namespace JCMS_DB_Converter
{
	[Microsoft.VisualBasic.CompilerServices.DesignerGenerated()]
	public partial class frmMain : System.Windows.Forms.Form
	{

		//Form overrides dispose to clean up the component list.
		internal frmMain()
		{
			InitializeComponent();
		}
		[System.Diagnostics.DebuggerNonUserCode()]
		protected override void Dispose(bool disposing)
		{
			if (disposing && components != null)
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		//Required by the Windows Form Designer
		private System.ComponentModel.IContainer components;

		//NOTE: The following procedure is required by the Windows Form Designer
		//It can be modified using the Windows Form Designer.  
		//Do not modify it using the code editor.
		[System.Diagnostics.DebuggerStepThrough()]
		private void InitializeComponent()
		{
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmMain));
            this.btnCancel = new System.Windows.Forms.Button();
            this.btnOk = new System.Windows.Forms.Button();
            this.txtMySQLUserName = new System.Windows.Forms.TextBox();
            this.Label2 = new System.Windows.Forms.Label();
            this.txtMySQLPwd = new System.Windows.Forms.TextBox();
            this.Label3 = new System.Windows.Forms.Label();
            this.txtMySQLHost = new System.Windows.Forms.TextBox();
            this.Label4 = new System.Windows.Forms.Label();
            this.txtMySQLPort = new System.Windows.Forms.TextBox();
            this.Label5 = new System.Windows.Forms.Label();
            this.dlgOpen = new System.Windows.Forms.OpenFileDialog();
            this.btnOpenInterface = new System.Windows.Forms.Button();
            this.txtJCMSDb = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.chkDisable = new System.Windows.Forms.CheckBox();
            this.mnuHelp = new System.Windows.Forms.MenuStrip();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.label1 = new System.Windows.Forms.Label();
            this.textBoxDatabaseName = new System.Windows.Forms.TextBox();
            this.mnuHelp.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnCancel
            // 
            this.btnCancel.Location = new System.Drawing.Point(491, 260);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(101, 22);
            this.btnCancel.TabIndex = 8;
            this.btnCancel.Text = "Exit";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // btnOk
            // 
            this.btnOk.Location = new System.Drawing.Point(384, 260);
            this.btnOk.Name = "btnOk";
            this.btnOk.Size = new System.Drawing.Size(101, 22);
            this.btnOk.TabIndex = 7;
            this.btnOk.Text = "Convert";
            this.btnOk.UseVisualStyleBackColor = true;
            this.btnOk.Click += new System.EventHandler(this.btnOk_Click);
            // 
            // txtMySQLUserName
            // 
            this.txtMySQLUserName.Location = new System.Drawing.Point(187, 185);
            this.txtMySQLUserName.Name = "txtMySQLUserName";
            this.txtMySQLUserName.Size = new System.Drawing.Size(123, 20);
            this.txtMySQLUserName.TabIndex = 5;
            this.txtMySQLUserName.Text = "root";
            // 
            // Label2
            // 
            this.Label2.AutoSize = true;
            this.Label2.Location = new System.Drawing.Point(33, 192);
            this.Label2.Name = "Label2";
            this.Label2.Size = new System.Drawing.Size(105, 13);
            this.Label2.TabIndex = 4;
            this.Label2.Text = "* MySQL User Name";
            // 
            // txtMySQLPwd
            // 
            this.txtMySQLPwd.Location = new System.Drawing.Point(187, 211);
            this.txtMySQLPwd.Name = "txtMySQLPwd";
            this.txtMySQLPwd.PasswordChar = '*';
            this.txtMySQLPwd.Size = new System.Drawing.Size(123, 20);
            this.txtMySQLPwd.TabIndex = 6;
            // 
            // Label3
            // 
            this.Label3.AutoSize = true;
            this.Label3.Location = new System.Drawing.Point(40, 218);
            this.Label3.Name = "Label3";
            this.Label3.Size = new System.Drawing.Size(91, 13);
            this.Label3.TabIndex = 6;
            this.Label3.Text = "MySQL Password";
            // 
            // txtMySQLHost
            // 
            this.txtMySQLHost.Location = new System.Drawing.Point(187, 134);
            this.txtMySQLHost.Name = "txtMySQLHost";
            this.txtMySQLHost.Size = new System.Drawing.Size(123, 20);
            this.txtMySQLHost.TabIndex = 3;
            this.txtMySQLHost.Text = "localhost";
            // 
            // Label4
            // 
            this.Label4.AutoSize = true;
            this.Label4.Location = new System.Drawing.Point(33, 141);
            this.Label4.Name = "Label4";
            this.Label4.Size = new System.Drawing.Size(74, 13);
            this.Label4.TabIndex = 8;
            this.Label4.Text = "* MySQL Host";
            // 
            // txtMySQLPort
            // 
            this.txtMySQLPort.Location = new System.Drawing.Point(187, 160);
            this.txtMySQLPort.Name = "txtMySQLPort";
            this.txtMySQLPort.Size = new System.Drawing.Size(123, 20);
            this.txtMySQLPort.TabIndex = 4;
            this.txtMySQLPort.Text = "3306";
            // 
            // Label5
            // 
            this.Label5.AutoSize = true;
            this.Label5.Location = new System.Drawing.Point(33, 167);
            this.Label5.Name = "Label5";
            this.Label5.Size = new System.Drawing.Size(71, 13);
            this.Label5.TabIndex = 10;
            this.Label5.Text = "* MySQL Port";
            // 
            // dlgOpen
            // 
            this.dlgOpen.FileName = "OpenFileDialog1";
            // 
            // btnOpenInterface
            // 
            this.btnOpenInterface.Location = new System.Drawing.Point(567, 50);
            this.btnOpenInterface.Name = "btnOpenInterface";
            this.btnOpenInterface.Size = new System.Drawing.Size(25, 19);
            this.btnOpenInterface.TabIndex = 1;
            this.btnOpenInterface.Text = "...";
            this.btnOpenInterface.UseVisualStyleBackColor = true;
            this.btnOpenInterface.Click += new System.EventHandler(this.btnOpenInterface_Click);
            // 
            // txtJCMSDb
            // 
            this.txtJCMSDb.Location = new System.Drawing.Point(194, 49);
            this.txtJCMSDb.Name = "txtJCMSDb";
            this.txtJCMSDb.Size = new System.Drawing.Size(367, 20);
            this.txtJCMSDb.TabIndex = 0;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(33, 56);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(155, 13);
            this.label9.TabIndex = 19;
            this.label9.Text = "* JCMS Installation (JCMS.mdb)";
            this.label9.Click += new System.EventHandler(this.label9_Click);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.Location = new System.Drawing.Point(6, 29);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(234, 17);
            this.label10.TabIndex = 22;
            this.label10.Text = "JAX-CMS Database Information";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(12, 114);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(219, 17);
            this.label11.TabIndex = 23;
            this.label11.Text = "MySQL Database Information";
            // 
            // chkDisable
            // 
            this.chkDisable.AutoSize = true;
            this.chkDisable.Checked = true;
            this.chkDisable.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chkDisable.Location = new System.Drawing.Point(187, 75);
            this.chkDisable.Name = "chkDisable";
            this.chkDisable.Size = new System.Drawing.Size(187, 17);
            this.chkDisable.TabIndex = 3;
            this.chkDisable.Text = "Disable database after conversion";
            this.chkDisable.UseVisualStyleBackColor = true;
            // 
            // mnuHelp
            // 
            this.mnuHelp.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.helpToolStripMenuItem});
            this.mnuHelp.Location = new System.Drawing.Point(0, 0);
            this.mnuHelp.Name = "mnuHelp";
            this.mnuHelp.Size = new System.Drawing.Size(604, 24);
            this.mnuHelp.TabIndex = 24;
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.aboutToolStripMenuItem});
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
            this.helpToolStripMenuItem.Text = "Help";
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(107, 22);
            this.aboutToolStripMenuItem.Text = "About";
            this.aboutToolStripMenuItem.Click += new System.EventHandler(this.aboutToolStripMenuItem_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(40, 241);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(122, 13);
            this.label1.TabIndex = 26;
            this.label1.Text = "MySQL Database Name";
            // 
            // textBoxDatabaseName
            // 
            this.textBoxDatabaseName.Location = new System.Drawing.Point(187, 237);
            this.textBoxDatabaseName.Name = "textBoxDatabaseName";
            this.textBoxDatabaseName.Size = new System.Drawing.Size(123, 20);
            this.textBoxDatabaseName.TabIndex = 7;
            this.textBoxDatabaseName.Text = "jcms_db";
            // 
            // frmMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(604, 293);
            this.Controls.Add(this.textBoxDatabaseName);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.chkDisable);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.btnOpenInterface);
            this.Controls.Add(this.txtJCMSDb);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.txtMySQLPort);
            this.Controls.Add(this.Label5);
            this.Controls.Add(this.txtMySQLHost);
            this.Controls.Add(this.Label4);
            this.Controls.Add(this.txtMySQLPwd);
            this.Controls.Add(this.Label3);
            this.Controls.Add(this.txtMySQLUserName);
            this.Controls.Add(this.Label2);
            this.Controls.Add(this.btnOk);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.mnuHelp);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.mnuHelp;
            this.Name = "frmMain";
            this.Text = "JAX-CMS Database Conversion Tool";
            this.mnuHelp.ResumeLayout(false);
            this.mnuHelp.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
		internal System.Windows.Forms.Button btnCancel;
		internal System.Windows.Forms.Button btnOk;
		internal System.Windows.Forms.TextBox txtMySQLUserName;
		internal System.Windows.Forms.Label Label2;
		internal System.Windows.Forms.TextBox txtMySQLPwd;
		internal System.Windows.Forms.Label Label3;
		internal System.Windows.Forms.TextBox txtMySQLHost;
		internal System.Windows.Forms.Label Label4;
		internal System.Windows.Forms.TextBox txtMySQLPort;
        internal System.Windows.Forms.Label Label5;
        internal System.Windows.Forms.OpenFileDialog dlgOpen;
        internal Button btnOpenInterface;
        internal TextBox txtJCMSDb;
        internal Label label9;
        internal Label label10;
        internal Label label11;
        private CheckBox chkDisable;
        private MenuStrip mnuHelp;
        private ToolStripMenuItem helpToolStripMenuItem;
        private ToolStripMenuItem aboutToolStripMenuItem;
        internal Label label1;
        internal TextBox textBoxDatabaseName;

	}

} //end of root namespace