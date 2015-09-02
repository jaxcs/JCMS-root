VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "myQuery"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = True
Option Explicit

Dim ContrutorRun As Boolean
Dim myDatabase As DAO.Database
Dim wks As DAO.Workspace

Private Sub Class_Initialize()
 
' Get the Database we are connected to in the Access Form.
Set myDatabase = DBEngine(0)(0)

Exit Sub


End Sub


Public Sub Constructor(ByRef dbToUse As DAO.Database)

 ContrutorRun = True
 ' Set myDatabase = dbToUse
 
End Sub


Public Function ResultSetQuery(sSql As String) As DAO.Recordset

On Error GoTo ResultSetQuery_error

    'Debug message box
    'MsgBox sSQL
    
    If IsNull(myDatabase) Then
        MsgBox ("db isnull")
    Else
    'MsgBox myDatabase.Name
    Set ResultSetQuery = myDatabase.OpenRecordset(sSql)
    
    
    End If
    
Exit Function
    
    
ResultSetQuery_error:
    MsgBox "An error occurred in querying database. The system error message was: " & Err.Description
    MsgBox sSql
End Function


Public Function GetQueryValue(sSql As String, sValue As String) As String

On Error GoTo ResultSetQuery_error

    
    Dim rs As DAO.Recordset
    
    'Debug message box
    'MsgBox sSQL
    
    If IsNull(myDatabase) Then
        MsgBox ("db isnull")
    Else
    'MsgBox myDatabase.Name
    Set rs = myDatabase.OpenRecordset(sSql)
        If rs.RecordCount > 0 Then
            If IsNull(rs(sValue).Value) Then
                GetQueryValue = ""
            Else
                GetQueryValue = rs(sValue).Value
            End If
        Else
                GetQueryValue = ""
        End If
    End If
    
Exit Function
    
    
ResultSetQuery_error:
    MsgBox sSql
    MsgBox "An error occurred in function MTSUtils.getRecordSet(). The system error message was: " & Err.Description

End Function

'----------------------------------------------------------------------------
' executeSQLTransaction()
' INPUTS: an array of sql statemetns
' RETURNS: true if  success, false otherwise
' EFFECTS: all sql statements are executed against the MTS database
' or all are rolled back
' ERROR CONDITIONS: handles errors with error messages, rolls back transaction
' if necessary
' ASSUMES: sql statements array low bound starts at index zero
' COMMENTS: this function connects and executes a set of simple SQL
' commands.
'----------------------------------------------------------------------------

Function executeSQLTransaction(SQLStatements() As String) As Boolean
    Dim ourDatabase As Database
    Dim wsWorkspace As Workspace
    
    Dim i As Integer
    On Error GoTo OUTA_HERE_BY_ERR
    'Open connection
    Set ourDatabase = DBEngine(0)(0)
    Set wsWorkspace = DBEngine.Workspaces(0)

    On Error GoTo ROLL_BACK
    wsWorkspace.BeginTrans
    For i = 0 To UBound(SQLStatements)
        ourDatabase.Execute SQLStatements(i), dbFailOnError
    Next i
    wsWorkspace.CommitTrans
    executeSQLTransaction = True
    Exit Function

ROLL_BACK:

    MsgBox "TRANSACTION ROLLED BACK: An error occured in executeSQL. The system error message was: " & Err.Description _
            & " The SQL was: " & SQLStatements(i)
    ' where is the rollback?
    wsWorkspace.Rollback
    executeSQLTransaction = False
   ' MADConnection.Close
    Exit Function
OUTA_HERE_BY_ERR:
    MsgBox "An error occured in executeSQL. The system error message was: " & Err.Description
    executeSQLTransaction = False
    Exit Function

End Function

