/* -*- javascript -*- 
     Copyright 2008 The Jackson Lab.
     All Rights Reserved
     System        : X_JS : 
     Object Name   : $RCS_FILE$
     Revision      : $REVISION$
     Date          : Tue May 13 15:46:46 2008
     Created By    : Dave Springer Jr., The Jackson Lab
     Created       : Tue May 13 15:46:46 2008

     Last Modified : <080514.1013>
     ID            : $Id$
     Source        : $Source$
     Description	
     Notes
*/
//var ns4 = document.layers;
//var ie4 = document.all;
//var nn6 = document.getElementById && !document.all;

var shell;
    // create the shell object, and run the class
    shell = new ActiveXObject( "WScript.Shell" );
    set strDir = shell.WshShell.CurrentDirectory;
    shell.run("mysql.exe -u root -p < " + strDir + "\JCMS_db.mdb.mysql.sql", 1 );
