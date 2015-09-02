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
namespace JCMS_DB_Converter
{
    partial class frmProgress
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmProgress));
            this.lblConversion = new System.Windows.Forms.Label();
            this.btnExit = new System.Windows.Forms.Button();
            this.rtbOutput = new System.Windows.Forms.RichTextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.pgbConversion = new System.Windows.Forms.ProgressBar();
            this.btnCancel = new System.Windows.Forms.Button();
            this.dlgOpen = new System.Windows.Forms.FolderBrowserDialog();
            this.SuspendLayout();
            // 
            // lblConversion
            // 
            this.lblConversion.AutoSize = true;
            this.lblConversion.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblConversion.Location = new System.Drawing.Point(9, 65);
            this.lblConversion.Name = "lblConversion";
            this.lblConversion.Size = new System.Drawing.Size(394, 13);
            this.lblConversion.TabIndex = 18;
            this.lblConversion.Text = "Starting Conversion...                                                           " +
                "       ";
            // 
            // btnExit
            // 
            this.btnExit.Location = new System.Drawing.Point(507, 376);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(101, 22);
            this.btnExit.TabIndex = 20;
            this.btnExit.Text = "Exit";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // rtbOutput
            // 
            this.rtbOutput.HideSelection = false;
            this.rtbOutput.Location = new System.Drawing.Point(12, 134);
            this.rtbOutput.Name = "rtbOutput";
            this.rtbOutput.Size = new System.Drawing.Size(596, 236);
            this.rtbOutput.TabIndex = 21;
            this.rtbOutput.Text = "";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 107);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(49, 13);
            this.label1.TabIndex = 22;
            this.label1.Text = "Output:";
            // 
            // pgbConversion
            // 
            this.pgbConversion.Location = new System.Drawing.Point(12, 27);
            this.pgbConversion.Name = "pgbConversion";
            this.pgbConversion.Size = new System.Drawing.Size(596, 23);
            this.pgbConversion.TabIndex = 19;
            // 
            // btnCancel
            // 
            this.btnCancel.Location = new System.Drawing.Point(400, 376);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(101, 22);
            this.btnCancel.TabIndex = 23;
            this.btnCancel.Text = "Cancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // frmProgress
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(620, 410);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.rtbOutput);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.pgbConversion);
            this.Controls.Add(this.lblConversion);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "frmProgress";
            this.Text = "JAX-CMS Database Conversion";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        internal System.Windows.Forms.Label lblConversion;
        internal System.Windows.Forms.Button btnExit;
        internal System.Windows.Forms.Label label1;
        internal System.Windows.Forms.RichTextBox rtbOutput;
        internal System.Windows.Forms.ProgressBar pgbConversion;
        internal System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.FolderBrowserDialog dlgOpen;
    }
}