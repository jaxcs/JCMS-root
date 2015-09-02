Set objShell = CreateObject("Shell.Application")
Set objShell = CreateObject("Wscript.Shell")
objCommand = "MsiExec.exe /i  ""C:\Program Files (x86)\The Jackson Laboratory\JCMS Database Conversion Tool\mysql-connector-odbc-3.51.22-win32.msi""" 
objShell.Run(objCommand)
