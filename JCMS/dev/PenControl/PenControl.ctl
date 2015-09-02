VERSION 5.00
Object = "{86CF1D34-0C5F-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCOMCT2.OCX"
Begin VB.UserControl PenControl 
   ClientHeight    =   2835
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   5580
   PropertyPages   =   "PenControl.ctx":0000
   ScaleHeight     =   2835
   ScaleWidth      =   5580
   Begin VB.Frame Frame1 
      Appearance      =   0  'Flat
      ForeColor       =   &H80000008&
      Height          =   615
      Left            =   0
      TabIndex        =   19
      Top             =   2160
      Width           =   5535
      Begin VB.ComboBox cboRoom 
         Height          =   315
         Left            =   720
         TabIndex        =   6
         Text            =   "Combo1"
         Top             =   200
         Width           =   1470
      End
      Begin VB.Label lbHealthLevel 
         Caption         =   "Label1"
         Height          =   225
         Left            =   2855
         TabIndex        =   16
         Top             =   240
         Width           =   2655
      End
      Begin VB.Label lbRoom 
         Caption         =   "*Room:"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   30
         TabIndex        =   21
         Top             =   240
         Width           =   645
      End
      Begin VB.Label lbHLVL 
         Caption         =   "H Lvl:"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   2280
         TabIndex        =   20
         Top             =   240
         Width           =   540
      End
   End
   Begin VB.CheckBox chkRetire 
      Caption         =   "Retire"
      Height          =   255
      Left            =   3600
      TabIndex        =   11
      Top             =   1200
      Width           =   1455
   End
   Begin VB.Frame frmPen 
      Appearance      =   0  'Flat
      Caption         =   "Pen"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H80000008&
      Height          =   2165
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   5535
      Begin VB.ComboBox cboStatus 
         Height          =   315
         Left            =   650
         TabIndex        =   3
         Text            =   "Combo1"
         Top             =   840
         Width           =   1935
      End
      Begin MSComCtl2.DTPicker dtActionDate 
         Height          =   315
         Left            =   645
         TabIndex        =   4
         Top             =   1200
         Width           =   2050
         _ExtentX        =   3625
         _ExtentY        =   556
         _Version        =   393216
         CustomFormat    =   "M/dd/yyyy h:mm tt"
         Format          =   20643843
         CurrentDate     =   39976
      End
      Begin VB.TextBox txtNewName 
         Height          =   285
         Left            =   3360
         MaxLength       =   16
         TabIndex        =   10
         Top             =   840
         Width           =   2055
      End
      Begin VB.CommandButton cmdNextContainerID 
         Caption         =   "Next ID"
         Height          =   255
         Left            =   2640
         Style           =   1  'Graphical
         TabIndex        =   7
         Top             =   120
         Width           =   855
      End
      Begin VB.CheckBox chkIncrementName 
         Caption         =   "Increment Name"
         Height          =   255
         Left            =   3600
         TabIndex        =   9
         Top             =   480
         Width           =   1575
      End
      Begin VB.CheckBox chkUseNextID 
         Caption         =   "Use next available ID"
         Height          =   255
         Left            =   3600
         TabIndex        =   8
         Top             =   120
         Width           =   1815
      End
      Begin VB.ComboBox cboContainerID 
         BackColor       =   &H0000FF00&
         Height          =   315
         ItemData        =   "PenControl.ctx":001B
         Left            =   650
         List            =   "PenControl.ctx":001D
         TabIndex        =   1
         Text            =   "Pen ID"
         Top             =   120
         Width           =   1935
      End
      Begin VB.ComboBox cboContainerName 
         Height          =   315
         ItemData        =   "PenControl.ctx":001F
         Left            =   645
         List            =   "PenControl.ctx":0021
         Sorted          =   -1  'True
         TabIndex        =   2
         Text            =   "cboContainerName"
         Top             =   480
         Width           =   2895
      End
      Begin VB.TextBox txtComment 
         Height          =   495
         Left            =   960
         MaxLength       =   64
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   5
         Text            =   "PenControl.ctx":0023
         Top             =   1560
         Width           =   4455
      End
      Begin VB.Label lbNewName 
         Alignment       =   1  'Right Justify
         Caption         =   "New Name:"
         Height          =   375
         Left            =   2760
         TabIndex        =   18
         Top             =   840
         Width           =   495
      End
      Begin VB.Label lbID 
         Caption         =   "*ID:"
         Height          =   255
         Left            =   60
         TabIndex        =   17
         Top             =   240
         Width           =   600
      End
      Begin VB.Label lbComments 
         Caption         =   "Comments:"
         Height          =   255
         Left            =   60
         TabIndex        =   15
         Top             =   1680
         Width           =   780
      End
      Begin VB.Label lbDate 
         Caption         =   "*Date:"
         Height          =   255
         Left            =   60
         TabIndex        =   14
         Top             =   1200
         Width           =   600
      End
      Begin VB.Label lbStatus 
         Caption         =   "*Status:"
         Height          =   255
         Left            =   60
         TabIndex        =   13
         Top             =   840
         Width           =   600
      End
      Begin VB.Label lbName 
         Caption         =   "Name:"
         Height          =   255
         Left            =   60
         TabIndex        =   12
         Top             =   480
         Width           =   600
      End
   End
End
Attribute VB_Name = "PenControl"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = True
Attribute VB_Ext_KEY = "PropPageWizardRun" ,"Yes"
Option Explicit

' -- Win API Definitions ---
Private Declare Function SendMessageByString _
                         Lib "user32" _
                         Alias "SendMessageA" _
                         (ByVal hwnd As Long, _
                          ByVal wMsg As Long, _
                          ByVal wParam As Long, _
                          ByVal lParam As String) As Long



Private Declare Function SendMessageLong _
                        Lib "user32" _
                        Alias "SendMessageA" _
                        (ByVal hwnd As Long, _
                        ByVal wMsg As Long, _
                        ByVal wParam As Long, _
                        ByVal lParam As Long) As Long



'/* Combo box messages */
Private Const CB_GETEDITSEL = &H140
Private Const CB_LIMITTEXT = &H141
Private Const CB_SETEDITSEL = &H142
Private Const CB_ADDSTRING = &H143
Private Const CB_DELETESTRING = &H144
Private Const CB_DIR = &H145
Private Const CB_GETCOUNT = &H146
Private Const CB_GETCURSEL = &H147
Private Const CB_GETLBTEXT = &H148
Private Const CB_GETLBTEXTLEN = &H149
Private Const CB_INSERTSTRING = &H14A
Private Const CB_RESETCONTENT = &H14B
Private Const CB_FINDSTRING = &H14C
Private Const CB_SELECTSTRING = &H14D
Private Const CB_SETCURSEL = &H14E
Private Const CB_SHOWDROPDOWN = &H14F
Private Const CB_GETITEMDATA = &H150
Private Const CB_SETITEMDATA = &H151
Private Const CB_GETDROPPEDCONTROLRECT = &H152
Private Const CB_SETITEMHEIGHT = &H153
Private Const CB_GETITEMHEIGHT = &H154
Private Const CB_SETEXTENDEDUI = &H155
Private Const CB_GETEXTENDEDUI = &H156
Private Const CB_GETDROPPEDSTATE = &H157
Private Const CB_FINDSTRINGEXACT = &H158
Private Const CB_SETLOCALE = &H159
Private Const CB_GETLOCALE = &H15A
Private Const CB_GETTOPINDEX = &H15B
Private Const CB_SETTOPINDEX = &H15C
Private Const CB_GETHORIZONTALEXTENT = &H15D
Private Const CB_SETHORIZONTALEXTENT = &H15E
Private Const CB_GETDROPPEDWIDTH = &H15F
Private Const CB_SETDROPPEDWIDTH = &H160
Private Const CB_INITSTORAGE = &H161
Private Const CB_MULTIPLEADDSTRING = &H163
Private Const CB_GETCOMBOBOXINFO = &H164
Private Const CB_MSGMAX = &H165

'
Private Const CB_ERR = -1
Private Const CB_ERRSPACE = -2

' This TempBuffer is a string used to do combo box type search.
Dim tempBuffer As String


' Query object defined in class
Private oQuery As myQuery
Private bAutoUpdate As Boolean
Private bContainerNameClick As Boolean

' Color attributes
Const ActiveBackground_Cyan As Long = &HC0C000
Const DisAbledBackground_Gray As Long = &H8000000F
Const EnAbledBackground_White As Long = &H80000005
Const SubmitBackground_Green As Long = &HFF00&

'Default Property Values:
Const m_def_Mode = 4
Const m_def_LoadContainerMode = 1

'Property Variables:
Private lContainerKey As Long
Private lContainerIDSelectedIndex As Long
Private lContainerNameSelectedIndex  As Long
Private lContainerStatusKey As Long
Private lHealthLevelKey As Long
Private lHealthLevelHistoryKey As Long
Private lRoomKey As Long
Private lContainerHistoryKey As Long
Private bJCMSSortByPenName As Boolean

Private bUsingHealthLevel As Boolean
Private bUsingPenComments As Boolean
Private bUsingPenNames As Boolean
Private dJCMSSortByPenName As Boolean
Private bAutoColor  As Boolean
Private gStatusIndex As Integer
Private gRoomIndex As Integer
Private strLastContainerID  As String
Private strLastContainerName As String


Const gcContainerID = 1
Const gcContainerName = 2

' Modes
Const gcInfoMode = 1
Const gcAddMode = 2
Const gcEditMode = 3
Const gcAllMode = 4

Dim m_Mode As Integer
Dim m_LoadContainerMode As Integer

Private sJCMSVersion As String
Private bDebug As Boolean
Private cboContainerNameChanged As Boolean
Private cboContainerNameClicked As Boolean
Private cboContainerIDChanged As Boolean
Private cboContainerIDClicked As Boolean
Private bDeletePressed As Boolean

'Event Declarations:
Public Event OnContainerUpdate() 'MappingInfo=cboContainerID,cboContainerID,-1,Click
Public Event OnNextID(containerID As String)
Public Event OnPenControlEnter()

'
' Fired with the contents of cboContainerID change
'
Private Sub cboContainerID_Change()

Dim strTextToFind   As String
Dim SelStart As Long, SelEnd As Long, retVal As Long
 
cboContainerIDChanged = True

    ' Determine the highlight part of the text
    retVal = SendMessageLong(cboContainerID.hwnd, CB_GETEDITSEL, SelStart, SelEnd)
    ' Save parameter for later
    If (retVal > 0) Then
        SelStart = LoWord(retVal)
    End If

    ' Use this string to find with win api later.
    strTextToFind = cboContainerID.Text

    ' Did the use press delete or backspacee
    If bDeletePressed Then
       ' Find the exact string
       retVal = SendMessageByString(cboContainerID.hwnd, _
                                    CB_FINDSTRINGEXACT, _
                                    0, _
                                    strTextToFind)

       ' Did we find a string
       If (-1 = retVal) Then
            ' No set key -1 and exit.
            lContainerKey = -1
            lContainerIDSelectedIndex = -1
            GoTo cboContainerID_Exit
       Else
            ' Yes save the key of the pen
            lContainerIDSelectedIndex = retVal
            lContainerKey = getContainerIDItemData(retVal)
       End If
    Else
       ' Find a string in the list
        retVal = SendMessageByString(cboContainerID.hwnd, _
                                    CB_FINDSTRING, _
                                    0, _
                                    strTextToFind)
        ' Did we find a string
        If (-1 = retVal) Then
            lContainerKey = -1
            lContainerIDSelectedIndex = -1
            GoTo cboContainerID_Exit
        Else
            ' Save the key of the selected string
            lContainerKey = getContainerIDItemData(retVal)
            lContainerIDSelectedIndex = retVal
            
            ' Select the string found
            retVal = SendMessageByString(cboContainerID.hwnd, _
                                          CB_SELECTSTRING, _
                                          0, _
                                          strTextToFind)
        End If
        
        ' Hightlight the part that was not typed by user.
        retVal = SendMessageLong(cboContainerID.hwnd, _
                                 CB_SETEDITSEL, _
                                 0, _
                                 MAKELPARAM(SelStart, -1))
    End If

cboContainerID_Exit:
    ' Reset - this value is set by cboContainerID.keypress
    bDeletePressed = False
End Sub
'
' cboContainerID has been clicked.
Private Sub cboContainerID_Click()
    cboContainerIDClicked = True
    lContainerKey = getContainerIDItemData
End Sub

'
' cboContainerID has focus.
Private Sub cboContainerID_GotFocus()
        cboContainerID.BackColor = ActiveBackground_Cyan
        RaiseEvent OnPenControlEnter
End Sub



Private Sub cboContainerID_KeyPress(KeyAscii As Integer)

   If (vbKeyDelete = KeyAscii) Or (vbKeyBack = KeyAscii) Then
       bDeletePressed = True
   End If
   
End Sub

'
' cboContainerID has lost focus.
Private Sub cboContainerID_LostFocus()
       tempBuffer = ""
       cboContainerID_Validate False
End Sub

'
' cboContainerID has changed lets make sure it is valid
'
Private Sub cboContainerID_Validate(Cancel As Boolean)

Dim bSendUpdate As Boolean
bSendUpdate = False
Dim SelStart As Long, SelEnd As Long, retVal As Long
Dim bInList As Boolean

' Lets only validate text if the user changes it.
' if it is modified programatically we assume the programmer knows what they
' are doing.
If ((cboContainerIDChanged = False) And (cboContainerIDClicked = False)) Then
    Exit Sub
Else
    cboContainerIDChanged = False
    cboContainerIDClicked = False
    ' Get the current index of the combo box.
    retVal = SendMessageLong(cboContainerID.hwnd, _
                            CB_GETCURSEL, _
                            0, 0)

    ' If -1 the text typed in is not found.
    If (-1 = retVal) Then
        lContainerKey = -1
    Else
        ' text found so let's save index and get key.
        lContainerIDSelectedIndex = retVal
        lContainerKey = getContainerIDItemData(retVal)
    End If
End If

    If (-1 = lContainerIDSelectedIndex) Then
        bInList = False
    Else
        bInList = True
    End If

    If Not IsNumeric(cboContainerID.Text) And _
        ("" <> cboContainerID.Text) Then
        MsgBox ("Pen ID must be a number.")
        Exit Sub
    End If

' The modes are very simular, however if you look closly you will see that each mode
' handles the interaction between cboContainerID and cboContainerName a little different.
Select Case m_Mode
    Case gcInfoMode
        If bInList Then
            UpdateControl lContainerIDSelectedIndex, gcContainerID
            If (strLastContainerID <> cboContainerID.Text) Then
                strLastContainerID = cboContainerID.Text
                bSendUpdate = True
            End If
            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
            Else
                lContainerKey = getContainerIDItemData
            End If
        Else
            ' Not in list, select the last value ...
            SelectContainerID_Intern Val(strLastContainerID)
            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
            Else
                lContainerKey = getContainerIDItemData
            End If
        End If
    Case gcAddMode
        If bInList Then
                UpdateControl lContainerIDSelectedIndex, gcContainerID
                If (strLastContainerID <> cboContainerID.Text) Then
                    strLastContainerID = cboContainerID.Text
                    bSendUpdate = True
                End If
                strLastContainerName = cboContainerName.Text
                If (-1 = getContainerIDListIndex) Then
                    lContainerKey = -1
                Else
                    lContainerKey = getContainerIDItemData
                End If
            Else
                ' Container ID Key
                lContainerKey = -1
                ' Enable data fields
                enableDataFields True
            End If
            
            If UserControl.chkUseNextID.Value Then
                lContainerKey = -1
            End If
    Case gcEditMode
        If bInList Then
            UpdateControl lContainerIDSelectedIndex, gcContainerID
            If (strLastContainerID <> cboContainerID.Text) Then
                strLastContainerID = cboContainerID.Text
                strLastContainerName = cboContainerName.Text
                bSendUpdate = True
            End If

            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
                SelectContainerID_Intern Val(strLastContainerID)
            Else
                lContainerKey = getContainerIDItemData
            End If
        Else
            ' Not in list, select the last value ...
            SelectContainerID_Intern Val(strLastContainerID)
            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
            Else
                lContainerKey = getContainerIDItemData
            End If
        End If
    Case gcAllMode
    
    
End Select
    
    
    
cboContainerNameChanged = False

If bSendUpdate = True Then RaiseEvent OnContainerUpdate
    
    
End Sub


'
' Fired with the contents of cboContainerName change
'
Private Sub cboContainerName_Change()

Dim strTextToFind   As String
Dim SelStart As Long, SelEnd As Long, retVal As Long
 
cboContainerNameChanged = True

    ' Determine the highlight part of the text
    retVal = SendMessageLong(cboContainerName.hwnd, CB_GETEDITSEL, SelStart, SelEnd)
    ' Save parameter for later
    If (retVal > 0) Then
        SelStart = LoWord(retVal)
    End If

    ' Use this string to find with win api later.
    strTextToFind = cboContainerName.Text

    ' Did the use press delete or backspacee
    If bDeletePressed Then
       ' Find the exact string
       retVal = SendMessageByString(cboContainerName.hwnd, _
                                    CB_FINDSTRINGEXACT, _
                                    0, _
                                    strTextToFind)

       ' Did we find a string
       If (-1 = retVal) Then
            ' No set key -1 and exit.
            lContainerKey = -1
            lContainerNameSelectedIndex = -1
            GoTo cboContainerName_Exit
       Else
            ' Yes save the key of the pen
            lContainerNameSelectedIndex = retVal
            lContainerKey = getContainerNameItemData(retVal)
       End If
    Else
       ' Find a string in the list
        retVal = SendMessageByString(cboContainerName.hwnd, _
                                    CB_FINDSTRING, _
                                    0, _
                                    strTextToFind)
        ' Did we find a string
        If (-1 = retVal) Then
            lContainerKey = -1
            lContainerNameSelectedIndex = -1
            GoTo cboContainerName_Exit
        Else
            ' Save the key of the selected string
            lContainerKey = getContainerNameItemData(retVal)
            lContainerNameSelectedIndex = retVal
            
            ' Select the string found
            retVal = SendMessageByString(cboContainerName.hwnd, _
                                          CB_SELECTSTRING, _
                                          0, _
                                          strTextToFind)
        End If
        
        ' Hightlight the part that was not typed by user.
        retVal = SendMessageLong(cboContainerName.hwnd, _
                                 CB_SETEDITSEL, _
                                 0, _
                                 MAKELPARAM(SelStart, -1))
    End If

cboContainerName_Exit:
    ' Reset - this value is set by cboContainerID.keypress
    bDeletePressed = False

End Sub

'
' cboContainerName Clicked
Private Sub cboContainerName_Click()
    cboContainerNameClicked = True
    lContainerKey = getContainerNameItemData
End Sub

'
' cboContainerName has focus
Private Sub cboContainerName_GotFocus()
    cboContainerName.BackColor = ActiveBackground_Cyan
End Sub


Private Sub cboContainerName_KeyPress(KeyAscii As Integer)

   If (vbKeyDelete = KeyAscii) Or (vbKeyBack = KeyAscii) Then
       bDeletePressed = True
   End If

End Sub

'
' cboContainerName has no focus
Private Sub cboContainerName_LostFocus()
    tempBuffer = ""
    cboContainerName_Validate False
End Sub

'
' cboContainerID has changed lets make sure it is valid
'
Private Sub cboContainerName_Validate(Cancel As Boolean)
    
Dim bSendUpdate As Boolean
bSendUpdate = False

' If we didn't change then lets not validate
If (cboContainerNameChanged = False And cboContainerNameClicked = False) Then
    Exit Sub
End If

' Let's not validate if greater than 16 chars
If (16 < Len(cboContainerName.Text)) Then
    cboContainerName.Text = Mid(cboContainerName.Text, 1, 16)
    MsgBox "Name is too long: Must be less than 16 Char."
    cboContainerName.SetFocus
    Cancel = True
    Exit Sub
End If

Dim retVal As Long
Dim bInList As Boolean '
Dim strTextToFind As String
    
    ' Save the text of cboContainerName
    strTextToFind = cboContainerName.Text
    
    ' Get the current index of the combo box.
    retVal = getContainerNameListIndex()
                                      
    ' If retVal >= 0 Nothing selected.
    If -1 = retVal Then
       lContainerKey = -1
       bInList = False
    Else
        bInList = True
        lContainerKey = getContainerNameItemData(retVal)
        lContainerNameSelectedIndex = retVal
    End If
 
    
Select Case m_Mode
    ' ----------------  Mode 1 ------------------------------
    Case gcInfoMode
        
        If (strTextToFind = "") Then
            Exit Sub
        End If
        If bInList Then
            UpdateControl lContainerNameSelectedIndex, gcContainerName
            
            If strLastContainerName <> Trim(cboContainerName.Text) Or strLastContainerID <> cboContainerID.Text Then
                bSendUpdate = True
                strLastContainerName = Trim(cboContainerName.Text)
            End If

        Else
            ' Not in list, select the last value ...
            SelectContainerName Trim(strLastContainerName)
        End If
    ' ----------------  Mode 2 ------------------------------
    Case gcAddMode
        If chkUseNextID.Value Then ' If true
            ' First determin if cboContainerID.text is in list.
            If bInList Then

                UpdateControl getContainerIDListIndex, gcContainerName
                
                If strLastContainerName <> Trim(cboContainerName.Text) Or strLastContainerID <> cboContainerID.Text Then
                    bSendUpdate = True
                    strLastContainerName = Trim(cboContainerName.Text)
                End If
                
                If (-1 = getContainerIDListIndex) Then
                    lContainerKey = -1
                Else
                    lContainerKey = getContainerIDItemData
                End If
                
                If UserControl.chkUseNextID Then
                    enableDataFields True
                Else
                    enableDataFields False
                End If
            Else
                ' Enable data fields
              
                 enableDataFields True
    
            End If
        Else ' if false
            ' First determin if cboContainerID.text is in list.
            If bInList Then
                ' if ID not in list don't update ..
                If (-1 = getContainerIDListIndex) Then
                    lContainerKey = -1
                Else
                    If (-1 = getContainerIDListIndex) Then
                        SelectContainerName Trim(cboContainerName.Text)
                    End If
                    strLastContainerName = Trim(cboContainerName.Text)
                    If strLastContainerName <> Trim(cboContainerName.Text) Or strLastContainerID <> cboContainerID.Text Then
                        bSendUpdate = True
                        strLastContainerName = Trim(cboContainerName.Text)
                    End If
                    SetContainerIDIndex Val(getContainerNameItemData)
                    UpdateControl getContainerIDListIndex, gcContainerName
                   ' lContainerKey = getContainerIDItemData
                End If
                
                If UserControl.chkUseNextID Then
                    enableDataFields True
                Else
                    If (-1 = getContainerIDListIndex) Then
                        enableDataFields True
                    Else
                        enableDataFields False
                    End If
                End If
            Else
                ' Enable data fields
                If (-1 = getContainerIDListIndex) Then
                    enableDataFields True
                Else
                    enableDataFields False
                    cboContainerName.Text = strLastContainerName
                End If
    
            End If
        End If
        
        
        If UserControl.chkUseNextID.Value Then
         ' Commented this out for bug 826, Hope this doesn't mess something else up.
          '  lContainerKey = -1
        End If
    ' ----------------  Mode 3 ------------------------------
    Case gcEditMode

        If (cboContainerName.Text = "") Then
'            Exit Sub
        End If
        If inComboBox(cboContainerName, cboContainerName.Text) And (cboContainerName.Text <> "") Then
            ' In the list Select the value ...
            ' if index -1 then we type it in ..
            If (-1 = getContainerIDListIndex) Then
                SelectContainerName Trim(cboContainerName.Text)
            End If
            
            SetContainerIDIndex Val(getContainerNameItemData)
            UpdateControl getContainerIDListIndex, gcContainerName
            
            If strLastContainerName <> Trim(cboContainerName.Text) Or strLastContainerID <> cboContainerID.Text Then
                bSendUpdate = True
                strLastContainerName = Trim(cboContainerName.Text)
            End If
            txtNewName.Text = strLastContainerName
            txtNewName.BackColor = EnAbledBackground_White
            ' SetContainerIDIndex Val(getContainerNameItemData)
            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
            Else
                lContainerKey = getContainerIDItemData
            End If
        Else
            ' Not in list, select the last value ...
            SelectContainerName Trim(strLastContainerName)
            If (-1 = getContainerIDListIndex) Then
                lContainerKey = -1
            Else
                lContainerKey = getContainerIDItemData
            End If
        End If

    
    Case gcAllMode
    
    
End Select

If bSendUpdate = True Then RaiseEvent OnContainerUpdate

End Sub

Private Sub cboRoom_Change()
    
        cboRoom.ListIndex = gRoomIndex
  
End Sub

Private Sub cboRoom_Click()
    gRoomIndex = cboRoom.ListIndex
    cboRoom.BackColor = EnAbledBackground_White
    lRoomKey = cboRoom.ItemData(cboRoom.ListIndex)
    UpdateHealthLevel (lRoomKey)
    
End Sub

'---------  Public Meathods  ----------------
Public Sub SubmitAction(strSubmitID As String)

Dim strContainerID As String


    If ("" = Trim(strSubmitID)) Then
        ' Get current Container ID
        strContainerID = cboContainerID.Text
    Else
        ' Get current Container ID
        strContainerID = strSubmitID
    End If
    
    ' Reload Container controls
     LoadContainerID

    ' Set ContainerID to curernt ID
    SelectContainerID Val(strContainerID)
    UpdateControl getContainerIDListIndex, gcContainerID

    If UserControl.chkUseNextID = False And m_Mode = 2 Or m_Mode = 1 Then
        enableDataFields False
    Else
        enableDataFields True
    End If

    If Not bAutoColor Then
        Exit Sub
    End If

    UserControl.cboContainerID.BackColor = SubmitBackground_Green
    UserControl.cboContainerName.BackColor = SubmitBackground_Green
    UserControl.txtNewName.BackColor = SubmitBackground_Green
    
    If UserControl.cboRoom.Enabled Then
        UserControl.cboRoom.BackColor = SubmitBackground_Green
    End If
    
    If UserControl.txtComment.Enabled Then
        UserControl.txtComment.BackColor = SubmitBackground_Green
    End If
    
    If UserControl.cboStatus.Enabled Then
        UserControl.cboStatus.BackColor = SubmitBackground_Green
    End If
End Sub


Public Function SelectContainerStatus(strStatus As String) As Boolean

    Dim iStatusCount As Long
    Dim i As Long
    iStatusCount = cboStatus.ListCount
    
    For i = 0 To iStatusCount
        If strStatus = cboStatus.List(i) Then
            cboStatus.ListIndex = i
            gStatusIndex = i
            cboStatus.BackColor = EnAbledBackground_White
            SelectContainerStatus = True
            lContainerStatusKey = cboStatus.ItemData(cboStatus.ListIndex)
            Exit Function
        End If
    Next i
    SelectContainerStatus = False

End Function

Public Function SelectContainerName(strName As String) As Boolean
    Dim lIndex As Long
    
    lIndex = SendMessageByString(cboContainerName.hwnd, _
                                      CB_FINDSTRING, _
                                      0, _
                                      strName)
    If (-1 < lIndex) Then
        SelectContainerName = True
        setContainerNameListIndex lIndex
        cboContainerName.BackColor = ActiveBackground_Cyan
        lContainerKey = getContainerNameItemData
        UpdateControl getContainerIDListIndex, gcContainerName
        Exit Function
    End If
    
    SelectContainerName = False
End Function

Private Function SelectContainerID_Intern(lID As Long) As Boolean
    
    Dim lIndex As Long
    
    lIndex = SendMessageByString(cboContainerID.hwnd, _
                                      CB_FINDSTRINGEXACT, _
                                      0, _
                                      Trim(Str(lID)))
    If (-1 < lIndex) Then
        setContainerIDListIndex lIndex
        SelectContainerID_Intern = True
        lContainerKey = getContainerIDItemData
        UpdateControl getContainerIDListIndex, gcContainerID
        Exit Function
    End If
    
    
    SelectContainerID_Intern = False

End Function


Public Function SelectContainerID(lID As Long) As Boolean

    SelectContainerID = SelectContainerID_Intern(lID)
    
    Exit Function

End Function

'
'  UpdateControl -- Based on the value set by the cboContainerID or
'       cboContainerName, determined by masterControl, update the rest
'       of the control based off of the comboBox index iIndex.
'
Private Sub UpdateControl(iIndex As Long, masterControl As Integer)

On Error GoTo UpdateControl_Error
Dim iContainerIDKey As Long

    If bAutoUpdate Then
        bAutoUpdate = False
        ' Set the Container based on the container key!
        ' Master control cboContainerID
        If gcContainerID = masterControl Then
            If (-1 <> iIndex) Then
                bContainerNameClick = False
                iContainerIDKey = getContainerIDItemData(iIndex)
                ' Set Name
                SetContainerNameIndex (iContainerIDKey)
                bContainerNameClick = True
            Else
                iContainerIDKey = -1
                SetContainerNameIndex (iContainerIDKey)
            End If
        End If
        ' Master control cboContainerName
        If gcContainerName = masterControl Then
        
            If (-1 <> iIndex) Then
                iContainerIDKey = getContainerNameItemData(iIndex)
                ' Set ID
                SetContainerIDIndex (iContainerIDKey)
            Else
                iContainerIDKey = -1
                SetContainerIDIndex (iContainerIDKey)
            End If
        End If
        

         
       cboContainerID.BackColor = ActiveBackground_Cyan
       cboContainerName.BackColor = ActiveBackground_Cyan
        
        ' Update Status, ActionDate
        UpdateStatus (iContainerIDKey)
        
        ' Update Comment
        UpdateComment (iContainerIDKey)
        
        ' Update Health Level, Room
        UpdateRoom (iContainerIDKey)
        
        bAutoUpdate = True
        
   End If
    
    If m_Mode = 2 Then
        enableDataFields False
    End If
    
Exit Sub
UpdateControl_Error:
MsgBox Err.Description
'Resume

bAutoUpdate = True

End Sub


Private Sub enableDataFields(bEnable As Boolean)

    If bEnable Then
        If Not chkRetire.Value Then
            cboStatus.Enabled = True
        End If
        UserControl.dtActionDate.Enabled = True
        txtComment.Enabled = True
        cboRoom.Enabled = True
'        txtNewName.Enabled = True
    Else
        cboStatus.Enabled = False
        UserControl.dtActionDate.Enabled = False
        txtComment.Enabled = False
        cboRoom.Enabled = False
'        txtNewName.Enabled = False
    End If
    
End Sub
Private Sub UpdateRoom(iContainerkey As Long)

Dim rs As DAO.Recordset
Dim sSql As String

If -1 = iContainerkey Then
    lRoomKey = -1
    SetRoom (lRoomKey)
    UpdateHealthLevel (lRoomKey)
Else
    ' Find Room Key
    sSql = "SELECT Room.[_room_key] as roomKey, Room.roomName as roomName  " & _
          "FROM (((Container) Left Join ContainerHistory ON Container.[_containerHistory_key] = ContainerHistory.[_containerHistory_key] ) LEFT JOIN Room ON ContainerHistory.[_room_key] = Room.[_room_key] )  " & _
          " WHERE Container.[_container_key] = " & Str(iContainerkey) & _
          " ORDER BY ContainerHistory.actionDate DESC ; "
    Set rs = oQuery.ResultSetQuery(sSql)
    ' ContainerHistory.[_containerHistory_key] DESC

    
    If IsNull(rs("roomKey").Value) Then
        lRoomKey = -1
        SetRoom (lRoomKey)
        UpdateHealthLevel (lRoomKey)
        Exit Sub
    End If
    
    lRoomKey = rs("roomKey").Value
    SetRoom (lRoomKey)
    UpdateHealthLevel (lRoomKey)
End If

End Sub

Private Sub UpdateHealthLevel(zRoomKey As Long)

Dim rs As DAO.Recordset
Dim sSql As String

If -1 = zRoomKey Then
    lbHealthLevel.Caption = " "
    lHealthLevelHistoryKey = -1
    lHealthLevelKey = -1
Else
    
    ' Find Health Level
    sSql = "SELECT HealthLevelHistory.[_healthLevelHistory_key] as HealthLevelHistoryKey, " & _
            "cv_HealthLevel.healthLevel as HealthLevel , HealthLevelHistory.startDate as startDate," & _
            "cv_HealthLevel.[_healthLevel_key] as HealthLevelKey , " & _
            "cv_HealthLevel.description as Description  " & _
          "FROM (((Room) Left Join HealthLevelHistory ON Room.[_healthLevelHistory_key] = HealthLevelHistory.[_healthLevelHistory_key] ) LEFT JOIN cv_HealthLevel ON cv_HealthLevel.[_healthLevel_key] = HealthLevelHistory.[_healthLevel_key] )  " & _
          " WHERE Room.[_room_key] = " & Str(zRoomKey) & _
          " ORDER BY HealthLevelHistory.startDate DESC; "
          
    
    Set rs = oQuery.ResultSetQuery(sSql)
    
    'Room
    If IsNull(rs("HealthLevelHistoryKey").Value) Then
        lHealthLevelHistoryKey = -1
        lHealthLevelKey = -1
    Else
        lHealthLevelHistoryKey = rs("HealthLevelHistoryKey").Value
        lHealthLevelKey = rs("HealthLevelKey").Value
        lbHealthLevel.Caption = rs("HealthLevel").Value & " since " & rs("startDate").Value
    End If
End If

End Sub




Private Sub UpdateControlMode()

On Error GoTo UpdateControlMode_Error
Dim iContainerID As Long


    




Select Case m_Mode
    Case gcInfoMode
        ' Info
        ' Visible
            ' Next Available ID
            UserControl.chkUseNextID.Visible = False
            ' Increment Name
            UserControl.chkIncrementName.Visible = False
            ' New Name
            UserControl.txtNewName.Visible = False
            UserControl.lbNewName.Visible = False
            ' Retired Pend
            UserControl.chkRetire.Visible = False
        ' Enabled
            ' Status
            UserControl.cboStatus.Enabled = False
            UserControl.cboStatus.BackColor = DisAbledBackground_Gray
            UserControl.lbStatus.Enabled = False
            ' Date
            UserControl.dtActionDate.Enabled = False
            UserControl.lbDate.Enabled = False
            ' Comments
            UserControl.txtComment.Enabled = False
            UserControl.txtComment.BackColor = DisAbledBackground_Gray
            UserControl.lbComments.Enabled = False
            ' Room Status
            UserControl.lbRoom.Enabled = False
            UserControl.cboRoom.Enabled = False
            UserControl.cboRoom.BackColor = DisAbledBackground_Gray
            UserControl.lbHealthLevel.Enabled = False
            UserControl.lbHLVL.Enabled = False
    Case gcAddMode
        ' Add
        ' Visible
            ' Next Available ID
            UserControl.chkUseNextID.Visible = True
            ' Increment Name
            UserControl.chkIncrementName.Visible = True
            ' New Name
            UserControl.txtNewName.Visible = False
            UserControl.lbNewName.Visible = False
            ' Retired Pend
            UserControl.chkRetire.Visible = False
        ' Enabled
        If -1 = UserControl.chkUseNextID.Value Then
            ' Status
            UserControl.cboStatus.Enabled = True
            UserControl.cboStatus.BackColor = EnAbledBackground_White
            UserControl.lbStatus.Enabled = True
             ' Date
            UserControl.dtActionDate.Enabled = True
            UserControl.lbDate.Enabled = True
            ' Comments
            UserControl.txtComment.Enabled = True
            UserControl.txtComment.BackColor = EnAbledBackground_White
            UserControl.lbComments.Enabled = True
            ' Room Status
            UserControl.lbRoom.Enabled = True
            UserControl.cboRoom.Enabled = True
            UserControl.cboRoom.BackColor = EnAbledBackground_White
            UserControl.lbHealthLevel.Enabled = True
            UserControl.lbHLVL.Enabled = True
        Else
            ' Status
            UserControl.cboStatus.Enabled = False
            UserControl.cboStatus.BackColor = EnAbledBackground_White
            UserControl.lbStatus.Enabled = True
             ' Date
            UserControl.dtActionDate.Enabled = False
            UserControl.lbDate.Enabled = True
            ' Comments
            UserControl.txtComment.Enabled = False
            UserControl.txtComment.BackColor = EnAbledBackground_White
            UserControl.lbComments.Enabled = True
            ' Room Status
            UserControl.lbRoom.Enabled = True
            UserControl.cboRoom.Enabled = False
            UserControl.cboRoom.BackColor = EnAbledBackground_White
            UserControl.lbHealthLevel.Enabled = False
            UserControl.lbHLVL.Enabled = True
        End If
    Case gcEditMode
        ' Edit
        ' Visible
            ' Next Available ID
            UserControl.chkUseNextID.Visible = False
            ' Increment Name
            UserControl.chkIncrementName.Visible = False
            ' New Name
            UserControl.txtNewName.Visible = True
            UserControl.lbNewName.Visible = True
            ' Retired Pend
            UserControl.chkRetire.Visible = True
        ' Enabled
            ' Status
            UserControl.cboStatus.Enabled = True
            UserControl.cboStatus.BackColor = EnAbledBackground_White
            UserControl.lbStatus.Enabled = True
            ' Date
            UserControl.dtActionDate.Enabled = True
            UserControl.lbDate.Enabled = True
            ' Comments
            UserControl.txtComment.Enabled = True
            UserControl.txtComment.BackColor = EnAbledBackground_White
            UserControl.lbComments.Enabled = True
            ' Room Status
            UserControl.lbRoom.Enabled = True
            UserControl.cboRoom.Enabled = True
            UserControl.cboRoom.BackColor = EnAbledBackground_White
            UserControl.lbHealthLevel.Enabled = True
            UserControl.lbHLVL.Enabled = True
    Case gcAllMode
        ' Show everything
        ' Visible
            ' Next Available ID
            UserControl.chkUseNextID.Visible = True
            ' Increment Name
            UserControl.chkIncrementName.Visible = True
            ' New Name
            UserControl.txtNewName.Visible = True
            UserControl.lbNewName.Visible = True
            ' Retired Pend
            UserControl.chkRetire.Visible = True
        ' Enabled
            ' Status
            UserControl.cboStatus.Enabled = True
            UserControl.cboStatus.BackColor = EnAbledBackground_White
            UserControl.lbStatus.Enabled = True
            ' Date
            UserControl.dtActionDate.Enabled = True
            UserControl.lbDate.Enabled = True
            ' Comments
            UserControl.txtComment.Enabled = True
            UserControl.txtComment.BackColor = EnAbledBackground_White
            UserControl.lbComments.Enabled = True
            ' Room Status
            UserControl.lbRoom.Enabled = True
            UserControl.cboRoom.Enabled = True
            UserControl.cboRoom.BackColor = EnAbledBackground_White
            UserControl.lbHealthLevel.Enabled = True
            UserControl.lbHLVL.Enabled = True
    Case Else
        ' Set as if one
        m_Mode = 1
        UpdateControlMode
End Select
    

Exit Sub
UpdateControlMode_Error:
MsgBox Err.Description

End Sub



' UpdateStatus
Private Sub UpdateStatus(iContainerkey As Long)

Dim rs As DAO.Recordset
Dim sSql As String
Dim i As Long


' Don't update status is the Retire check box is checked.
If chkRetire.Value Then
    Exit Sub
End If


sSql = "SELECT  ContainerHistory.[_containerHistory_key] as containerHistoryKey, " & _
               "ContainerHistory.actionDate as actionDate , cv_ContainerStatus.containerStatus as status, " & _
               "cv_ContainerStatus.[_containerStatus_key] as statusKey  " & _
      " FROM (((Container) Left Join ContainerHistory ON Container.[_containerHistory_key] = ContainerHistory.[_containerHistory_key] ) " & _
      " LEFT JOIN cv_ContainerStatus ON ContainerHistory.[_containerStatus_key] = cv_ContainerStatus.[_containerStatus_key] )" & _
      " WHERE Container.[_container_key] = " & Str(iContainerkey) & _
      " ORDER BY ContainerHistory.actionDate DESC; "


    
If -1 = iContainerkey Then
    lContainerHistoryKey = -1
    lContainerStatusKey = -1
    '
    If Not 3 = m_Mode Then
        dtActionDate.Value = "01/01/1900"
    End If
    SetStatus (-1)
Else
    Set rs = oQuery.ResultSetQuery(sSql)
    
    If IsNull(rs("containerHistoryKey").Value) Then
        lContainerHistoryKey = -1
        lContainerStatusKey = -1
        '
        If Not 3 = m_Mode Then
            dtActionDate.Value = "01/01/1900"
        End If
        SetStatus (-1)
        Exit Sub
    End If
    lContainerHistoryKey = rs("containerHistoryKey").Value
    lContainerStatusKey = rs("statusKey").Value

    ' actionDate
    If IsNull(rs("actionDate").Value) Then
        '
        If Not 3 = m_Mode Then
            dtActionDate.Value = "01/01/1900"
        End If
    Else
        '
        If Not 3 = m_Mode Then
            dtActionDate.Value = rs("actionDate").Value
        End If
    End If
    
    ' Status  ..
    i = Val(rs("statusKey").Value)
    SetStatus (i)

End If

End Sub

' Pass the Key value and set the ContainerIDIndex properlty
Private Sub SetContainerIDIndex(lKey As Long)

    Dim lCounter As Long
    Dim retVal As Long
    Dim lSelectIndex As Long
    lSelectIndex = -1
    lCounter = 1
    retVal = 0
    
    While (retVal > -1)
        retVal = SendMessageLong(cboContainerID.hwnd, _
                        CB_GETITEMDATA, lCounter, 0)
        
        If lKey = retVal Then
            ' Found what we are looking for save it.
            lSelectIndex = lCounter
            ' Stop the loop from executing
            retVal = -1
        End If
        lCounter = lCounter + 1
    Wend
    
    If (-1 < lSelectIndex) Then
        setContainerIDListIndex lSelectIndex
        cboContainerID.BackColor = EnAbledBackground_White
        Exit Sub
    End If

setContainerIDListIndex 0

End Sub
' iIndex is the key of the containerName
Private Sub SetContainerNameIndex(iIndex As Long)
    
    Dim lIndex As Long
    Dim lCounter As Long
    Dim retVal As Long
    Dim lSelectIndex As Long
    lSelectIndex = -1
    lCounter = 1
   
    While (lIndex > -1)
        lIndex = SendMessageLong(cboContainerName.hwnd, _
                        CB_GETITEMDATA, lCounter, 0)
        
        If lIndex = iIndex Then
            ' Found what we are looking for save it.
            lSelectIndex = lCounter
            ' Stop the loop from executing
            lIndex = -1
        End If
        lCounter = lCounter + 1
    Wend
    
    If (-1 < lSelectIndex) Then
'    MsgBox "setcontainername:lSelectIndex:" + Str(lSelectIndex)
        setContainerNameListIndex lSelectIndex
        cboContainerName.BackColor = EnAbledBackground_White
        txtNewName.Text = cboContainerName.Text
        txtNewName.BackColor = EnAbledBackground_White
        Exit Sub
    End If

setContainerNameListIndex 0
txtNewName.Text = ""
txtNewName.BackColor = EnAbledBackground_White

End Sub


Private Sub SetRoom(iIndex As Long)

Dim iRoomCount As Long
Dim i As Long
iRoomCount = cboRoom.ListCount

For i = 0 To iRoomCount
    If iIndex = cboRoom.ItemData(i) Then
        cboRoom.ListIndex = i
        gRoomIndex = i
        cboRoom.BackColor = EnAbledBackground_White
        Exit Sub
    End If
Next i


End Sub

Private Sub SetRoomByName(strRoom As String)

Dim iRoomCount As Long
Dim i As Long
iRoomCount = cboRoom.ListCount

For i = 0 To iRoomCount
    If strRoom = cboRoom.List(i) Then
        cboRoom.ListIndex = i
        gRoomIndex = i
        cboRoom.BackColor = EnAbledBackground_White
        Exit Sub
    End If
Next i


End Sub
Private Sub SetStatus(iIndex As Long)

Dim iStatusCount As Long
Dim i As Long
iStatusCount = cboStatus.ListCount

        For i = 0 To iStatusCount
            If iIndex = cboStatus.ItemData(i) Then
                cboStatus.ListIndex = i
                gStatusIndex = i
                cboStatus.BackColor = EnAbledBackground_White
                Exit Sub
            End If
        Next i


End Sub


Private Sub UpdateComment(iContainerkey As Long)

Dim rs As DAO.Recordset

    If (-1 = iContainerkey) Then
        txtComment.Text = " "
    Else
        Set rs = oQuery.ResultSetQuery( _
        "Select Comment FROM Container WHERE [_container_key] = " & Str(iContainerkey) & ";")
        
        If IsNull(rs("Comment").Value) Then
            txtComment.Text = ""
            txtComment.BackColor = EnAbledBackground_White
        Else
            txtComment.Text = rs("Comment").Value
            txtComment.BackColor = EnAbledBackground_White
        End If
    End If

End Sub



Private Sub cboRoom_GotFocus()
    
        cboRoom.BackColor = EnAbledBackground_White
         RaiseEvent OnContainerUpdate
    
End Sub

Private Sub cboRoom_KeyDown(KeyCode As Integer, Shift As Integer)
    cboRoom.ListIndex = gRoomIndex
End Sub

Private Sub cboStatus_Change()
    
        cboStatus.ListIndex = gStatusIndex
         RaiseEvent OnContainerUpdate

End Sub

Private Sub cboStatus_Click()
    gStatusIndex = cboStatus.ListIndex
    cboStatus.BackColor = EnAbledBackground_White
    lContainerStatusKey = cboStatus.ItemData(cboStatus.ListIndex)
End Sub



Private Sub cboStatus_GotFocus()
   
        cboStatus.BackColor = EnAbledBackground_White
    
End Sub

Private Sub cboStatus_KeyDown(KeyCode As Integer, Shift As Integer)
    cboStatus.ListIndex = gStatusIndex
End Sub


Private Sub chkRetire_Click()
  If chkRetire Then
       UserControl.cboStatus.Enabled = False
       UserControl.cboStatus.Locked = True
       SelectContainerStatus ("retired")
       
   Else
       UserControl.cboStatus.Enabled = True
       UserControl.cboStatus.Locked = False
   End If
End Sub

Private Sub chkUseNextID_Click()

   If chkUseNextID Then
       UserControl.cboContainerID.Enabled = False
       enableDataFields True
   Else
       UserControl.cboContainerID.Enabled = True
       enableDataFields False
       ' set cboContainerID to ""
       'SelectContainerID -1
       'lContainerKey = -1
   End If

End Sub

Private Sub cmdNextContainerID_Click()
     
Dim iMaxPenID As Long
Dim sdbVers As String
Dim iSQLStatements As Long
Dim sSQLStatements() As String
iSQLStatements = -1
     
     ' Query DBInfo .. maxPenID
     Dim rs As DAO.Recordset
     Set rs = oQuery.ResultSetQuery("Select * from dbinfo")
     iMaxPenID = rs("maxPenID") + 1
     sdbVers = rs("dbVers")
    
     ' Update the maxPenID
    AddString2List sSQLStatements, iSQLStatements
    sSQLStatements(iSQLStatements) = "UPDATE dbInfo set maxPenID = " & iMaxPenID & " WHERE dbVers = '" & Trim(sdbVers) & "' ;"

    oQuery.executeSQLTransaction sSQLStatements
     
     RaiseEvent OnNextID(Trim(Str(iMaxPenID)))
     
End Sub




Private Sub dtActionDate_GotFocus()
    RaiseEvent OnContainerUpdate
End Sub

Private Sub txtComment_GotFocus()
    txtComment.BackColor = EnAbledBackground_White
    RaiseEvent OnContainerUpdate
End Sub

Private Sub txtNewName_GotFocus()
    txtNewName.BackColor = EnAbledBackground_White
    RaiseEvent OnContainerUpdate
End Sub


Private Sub UserControl_Initialize()


On Error GoTo UserControl_Initialize_error


    cboContainerNameChanged = False
    cboContainerIDChanged = False
' Don't update combo boxes automatically.
' bAutoUpdate = False
     
    bContainerNameClick = True
' UpdateControlMode
    
    Set oQuery = New myQuery
     
    Dim rs As DAO.Recordset
     
    Set rs = oQuery.ResultSetQuery("Select * from dbinfo")
     
    If IsNull(rs) Then
        MsgBox "Database table dbinfo not found."
    End If
    
    rs.MoveFirst
    sJCMSVersion = rs("releaseNum")
    
    Set rs = Nothing
    
    Set rs = oQuery.ResultSetQuery("Select * from dbSetup where MTSVar = 'MTS_AUTO_COLOR'")
     
    UserControl.cmdNextContainerID.Visible = False
    
    If IsNull(rs) Then
        MsgBox "Database table dbSetup not found."
    End If
    
    rs.MoveFirst
    bAutoColor = rs("MTSValue")
    Set rs = Nothing
    
        ' Load Container ID
    LoadContainerID
    
    'Load Room
    LoadRooms
    
    'Load Container Status
    LoadStatus
    
    ' Here we go
    bAutoUpdate = True

    ' Default runtime mode
    bDebug = False
    
    ' Set to cyan
    UserControl.cboContainerID.BackColor = &HC0C000
    UserControl.cboContainerName.BackColor = &HC0C000
    
    'Remove space in text box.
    UserControl.txtComment = ""
    

Exit Sub
UserControl_Initialize_error:
    MsgBox "UserControl_Initialize_error"
    MsgBox Err.Description
End Sub

Private Sub LoadRooms()

Dim rs As DAO.Recordset
Dim i As Long


Set rs = oQuery.ResultSetQuery("Select * FROM Room")

i = UpdateCombo(cboRoom, rs, "roomName", "[_room_key]")

End Sub

Private Sub LoadStatus()

Dim rs As DAO.Recordset
Dim i As Long


Set rs = oQuery.ResultSetQuery("Select * FROM cv_ContainerStatus")

i = UpdateCombo(cboStatus, rs, "containerStatus", "[_containerStatus_key]")

End Sub


Private Sub LoadContainerID()
Dim rs As DAO.Recordset
Dim i As Long
Dim strSQL As String

If Not (m_LoadContainerMode > 0 And m_LoadContainerMode < 3) Then
    Exit Sub
End If

' Add Mode
If 2 = m_LoadContainerMode Then
    If bJCMSSortByPenName Then
        strSQL = " Select " & _
            " Container.[_container_key] as containerKey, " & _
            " Container.containerID as containerID,    " & _
            " Container.containerName as containerName " & _
            " FROM ((Container) Left Join ContainerHistory on Container.[_containerHistory_key] =  ContainerHistory.[_containerHistory_key]) " & _
            " Left Join cv_ContainerStatus on ContainerHistory.[_containerStatus_key] = cv_ContainerStatus.[_containerStatus_key] " & _
            " Where cv_ContainerStatus.containerStatus <> 'retired' ORDER BY containerName DESC "
    Else
        strSQL = " Select " & _
            " Container.[_container_key] as containerKey, " & _
            " Container.containerID as containerID, " & _
            " Container.containerName as containerName " & _
            " FROM ((Container) Left Join ContainerHistory on Container.[_containerHistory_key] =  ContainerHistory.[_containerHistory_key]) " & _
            " Left Join cv_ContainerStatus on ContainerHistory.[_containerStatus_key] = cv_ContainerStatus.[_containerStatus_key] " & _
            " Where cv_ContainerStatus.containerStatus <> 'retired' ORDER BY containerID DESC "
    End If
Else
    If bJCMSSortByPenName Then
        strSQL = "Select  " & _
            " Container.[_container_key] as containerKey, " & _
            " Container.containerID as containerID, " & _
            " Container.containerName as containerName " & _
            " FROM Container ORDER BY containerName DESC"
    Else
        strSQL = "Select  " & _
            " Container.[_container_key] as containerKey, " & _
            " Container.containerID as containerID, " & _
            " Container.containerName as containerName " & _
            "  FROM Container ORDER BY containerID DESC"
    End If
End If

Set rs = oQuery.ResultSetQuery(strSQL)

i = UpdateContainerCombo(rs)
'i = UpdateCombo(cboContainerName, rs, "containerName", "[_container_key]")


End Sub


'-------  JCMSVersion Property  -----------------------------
Public Property Get JCMSVersion() As String
    JCMSVersion = sJCMSVersion
End Property

Public Property Let JCMSVersion(ByVal vNewValue As String)

End Property
 


'-------  UsingNextAvailableID Property  -----------------------------
Public Property Get UsingNextAvailableID() As Boolean
    UsingNextAvailableID = UserControl.chkUseNextID.Visible
End Property

Public Property Let UsingNextAvailableID(ByVal bNewValue As Boolean)
    UserControl.chkUseNextID.Visible = bNewValue
End Property

'-------  UsingIncrementName Property  -----------------------------
Public Property Get UsingIncrementName() As Boolean
    UsingIncrementName = UserControl.chkIncrementName.Visible
End Property

Public Property Let UsingIncrementName(ByVal bNewValue As Boolean)
    UserControl.chkIncrementName.Visible = bNewValue
End Property

'-------  UsingRetire Property  -----------------------------
Public Property Get UsingRetire() As Boolean
    UsingRetire = UserControl.chkRetire.Visible
End Property

Public Property Let UsingRetire(ByVal bNewValue As Boolean)
    UserControl.chkRetire.Visible = bNewValue
End Property


'-------  UsingHealthLevel Property  -----------------------------
Public Property Get UsingHealthLevel() As Boolean
    UsingHealthLevel = bUsingHealthLevel
End Property

Public Property Let UsingHealthLevel(ByVal bNewValue As Boolean)
    bUsingHealthLevel = bNewValue
    UserControl.lbHealthLevel.Visible = bUsingHealthLevel
    UserControl.lbHLVL.Visible = bUsingHealthLevel
End Property

' "", bUsingPenComments, False)
'-------  UsingPenComments Property  -----------------------------
Public Property Get UsingPenComments() As Boolean
    UsingPenComments = bUsingPenComments
End Property

Public Property Let UsingPenComments(ByVal bNewValue As Boolean)
    bUsingPenComments = bNewValue
    UserControl.txtComment.Visible = bUsingPenComments
    UserControl.lbComments.Visible = bUsingPenComments
End Property

' "", , False)
'-------  UsingPenNames Property  -----------------------------
Public Property Get UsingPenNames() As Boolean
    UsingPenNames = bUsingPenNames
End Property

Public Property Let UsingPenNames(ByVal bNewValue As Boolean)
    bUsingPenNames = bNewValue
    UserControl.cboContainerName.Visible = bUsingPenNames
    UserControl.lbName.Visible = bUsingPenNames
    UserControl.txtNewName.Visible = bUsingPenNames
    UserControl.lbNewName.Visible = bUsingPenNames
    
End Property


'-------  ContainerID Property  -----------------------------
Public Property Get containerID() As String
    
        
    If lContainerKey = -1 Then
        containerID = cboContainerID.Text
        Exit Sub
    End If
    
    Dim sSql As String
    'QueryDb for ContainerID
    sSql = "Select containerID from container where  [_container_key]  = " & lContainerKey & ";"
    containerID = Trim(oQuery.GetQueryValue(sSql, "containerID"))
    
End Property

Public Property Let containerID(ByVal vNewValue As String)
    cboContainerID.Text = vNewValue
End Property

'-------  ContainerName Property  -----------------------------
Public Property Get ContainerName() As String
    
    If lContainerKey = -1 Then
        ContainerName = cboContainerName.Text
        Exit Sub
    End If

    'QueryDb for ContainerID
    Dim sSql As String
    sSql = "Select containerName from container where  [_container_key]  = " & lContainerKey & ";"
    ContainerName = Trim(oQuery.GetQueryValue(sSql, "containerName"))

End Property

Public Property Let ContainerName(ByVal vNewValue As String)
    cboContainerName.Text = vNewValue
End Property

'-------  Status Property  -----------------------------
Public Property Get Status() As String
    
    If lContainerKey = -1 Or m_Mode = gcEditMode Then
        Status = cboStatus.Text
        Exit Sub
    End If
    
    'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT cv_ContainerStatus.containerStatus as containerStatus" & _
        " FROM ((Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key]) " & _
        " LEFT JOIN cv_ContainerStatus ON   cv_ContainerStatus.[_containerStatus_key] = ContainerHistory.[_containerStatus_key]" & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    Status = Trim(oQuery.GetQueryValue(sSql, "containerStatus"))
    
    
End Property

Public Property Let Status(ByVal vNewValue As String)
    SelectContainerStatus (vNewValue)
End Property



'-------  ContainerHistoryDate Property  -----------------------------
Public Property Get ContainerHistoryDate() As String
 
     If lContainerKey = -1 Or gcEditMode = m_Mode Or (m_Mode = gcAddMode And chkUseNextID.Value) Then
        ContainerHistoryDate = dtActionDate.Value
        Exit Sub
    End If

    'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT ContainerHistory.actionDate as actionDate" & _
        " FROM (Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key] " & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    ContainerHistoryDate = Trim(oQuery.GetQueryValue(sSql, "actionDate"))
    
    
End Property


Public Property Let ContainerHistoryDate(ByVal vNewValue As String)
    If IsDate(vNewValue) Then
        dtActionDate.Value = vNewValue
    End If
End Property

'-------  Comment Property  -----------------------------
Public Property Get Comment() As String
    
    If lContainerKey = -1 Or gcEditMode = m_Mode Or (m_Mode = gcAddMode And chkUseNextID.Value) Then
        Comment = txtComment.Text
        Exit Sub
    End If
    
        'QueryDb for ContainerID
    Dim sSql As String
    sSql = "Select comment from container where  [_container_key]  = " & lContainerKey & ";"
    Comment = Trim(oQuery.GetQueryValue(sSql, "comment"))

End Property


Public Property Let Comment(ByVal vNewValue As String)
    
        txtComment.Text = vNewValue
    
End Property

'-------  Room Property  -----------------------------
Public Property Get Room() As String
    
    If lContainerKey = -1 Or gcEditMode = m_Mode Or (m_Mode = gcAddMode And chkUseNextID.Value) Then
        Room = cboRoom.Text
        Exit Sub
    End If

    'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT Room.roomName as roomName" & _
        " FROM ((Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key]) " & _
        " LEFT JOIN Room ON   Room.[_room_key] = ContainerHistory.[_room_key]" & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    Room = Trim(oQuery.GetQueryValue(sSql, "roomName"))

End Property


Public Property Let Room(ByVal vNewValue As String)
    SetRoomByName vNewValue
End Property


'-------  HealthLevel Property  -----------------------------
Public Property Get HealthLevel() As String

    If lContainerKey = -1 Or gcEditMode = m_Mode Then
        HealthLevel = lbHealthLevel.Caption
        Exit Sub
    End If

    'QueryDb for ContainerID
    Dim sSql As String
    sSql = " SELECT cv_HealthLevel.healthLevel as healthLevel  " & _
        "  FROM ((((Container)  " & _
        "  LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key])  " & _
        "  LEFT JOIN Room ON   Room.[_room_key] = ContainerHistory.[_room_key])  " & _
        "  LEFT JOIN HealthLevelHistory ON   Room.[_healthLevelHistory_key] = HealthLevelHistory.[_healthLevelHistory_key])  " & _
        "  LEFT JOIN cv_HealthLevel ON   cv_HealthLevel.[_healthLevel_key] = HealthLevelHistory.[_healthLevel_key]  " & _
        "  Where Container.[_container_key] = " & Str(lContainerKey) & ";"
    HealthLevel = Trim(oQuery.GetQueryValue(sSql, "healthLevel"))


End Property


Public Property Let HealthLevel(ByVal vNewValue As String)


End Property

'-------  NextAvailableID Property  -----------------------------
Public Property Get NextAvailableID() As Boolean
    NextAvailableID = UserControl.chkUseNextID
    
End Property


Public Property Let NextAvailableID(ByVal vNewValue As Boolean)
    If True = vNewValue Then
        UserControl.chkUseNextID.Value = 1
    Else
        UserControl.chkUseNextID.Value = 0
    End If
End Property


'-------  IncrementName Property  -----------------------------
Public Property Get IncrementName() As Boolean
    IncrementName = chkIncrementName.Value
End Property


Public Property Let IncrementName(ByVal vNewValue As Boolean)
    
    If True = vNewValue Then
        UserControl.chkIncrementName.Value = 1
    Else
        UserControl.chkIncrementName.Value = 0
    End If
    
End Property

'-------  NewName Property  -----------------------------
Public Property Get NewName() As String
    NewName = txtNewName.Text
End Property


Public Property Let NewName(ByVal vNewValue As String)
    txtNewName.Text = vNewValue
    txtNewName.BackColor = EnAbledBackground_White
End Property
'-------   DebugControl  -------------------------------------
Public Property Get DebugControl() As Boolean
    DebugControl = bDebug
End Property


Public Property Let DebugControl(ByVal vNewValue As Boolean)
    bDebug = vNewValue
End Property

'-------  Retire Property  -----------------------------
Public Property Get Retire() As Boolean
    Retire = chkRetire.Value
End Property


Public Property Let Retire(ByVal vNewValue As Boolean)
    If True = vNewValue Then
        UserControl.chkRetire.Value = 1
    Else
        UserControl.chkRetire.Value = 0
    End If
End Property

'-------  Retire Property  -----------------------------
Public Property Get NextContainerID() As Boolean
    NextContainerID = cmdNextContainerID.Enabled
End Property



Public Property Let NextContainerID(ByVal bNewValue As Boolean)
    cmdNextContainerID.Enabled = bNewValue
    cmdNextContainerID.Visible = bNewValue
End Property

'-------  ContainerKey Property  -----------------------------
Public Property Get containerKey() As Long
    containerKey = lContainerKey
End Property


Public Property Let containerKey(ByVal lNewValue As Long)
    lContainerKey = lNewValue
End Property


'Dim lContainerStatusKey As Long
'-------  lContainerStatusKey Property  -----------------------------
Public Property Get ContainerStatusKey() As Long

    If lContainerKey = -1 Or m_Mode = gcEditMode Or (m_Mode = gcAddMode And chkUseNextID.Value) Then
        ContainerStatusKey = cboStatus.ItemData(cboStatus.ListIndex)
        Exit Sub
    End If

    'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT cv_ContainerStatus.[_containerStatus_key] as ContainerStatusKey" & _
        " FROM ((Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key]) " & _
        " LEFT JOIN cv_ContainerStatus ON   cv_ContainerStatus.[_containerStatus_key] = ContainerHistory.[_containerStatus_key]" & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    ContainerStatusKey = CLng(oQuery.GetQueryValue(sSql, "ContainerStatusKey"))

End Property


Public Property Let ContainerStatusKey(ByVal lNewValue As Long)
    lContainerStatusKey = lNewValue
End Property


'Dim lHealthLevelKey As Long
'-------  lHealthLevelKey Property  -----------------------------
Public Property Get HealthLevelKey() As Long
    
    If lContainerKey = -1 Or m_Mode = gcEditMode Then
        HealthLevelKey = -1
        Exit Sub
    End If
    
    'QueryDb for ContainerID
    Dim sSql As String
    sSql = " SELECT cv_HealthLevel.[_healthLevel_key] as HealthLevelKey  " & _
        "  FROM ((((Container)  " & _
        "  LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key])  " & _
        "  LEFT JOIN Room ON   Room.[_room_key] = ContainerHistory.[_room_key])  " & _
        "  LEFT JOIN HealthLevelHistory ON   Room.[_healthLevelHistory_key] = HealthLevelHistory.[_healthLevelHistory_key])  " & _
        "  LEFT JOIN cv_HealthLevel ON   cv_HealthLevel.[_healthLevel_key] = HealthLevelHistory.[_healthLevel_key]  " & _
        "  Where Container.[_container_key] = " & Str(lContainerKey) & ";"
    HealthLevelKey = CLng(oQuery.GetQueryValue(sSql, "HealthLevelKey"))

End Property

Public Property Let HealthLevelKey(ByVal lNewValue As Long)
    lHealthLevelKey = lNewValue
End Property


'Dim lHealthLevelHistoryKey As Long
'-------  lHealthLevelHistoryKey Property  -----------------------------
Public Property Get HealthLevelHistoryKey() As Long

    If lContainerKey = -1 Or m_Mode = gcEditMode Then
        HealthLevelHistoryKey = -1
        Exit Sub
    End If

   'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT Room.[_healthLevelHistory_key] as HealthLevelHistoryKey" & _
        " FROM ((Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key]) " & _
        " LEFT JOIN Room ON   Room.[_room_key] = ContainerHistory.[_room_key]" & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    HealthLevelHistoryKey = CLng(oQuery.GetQueryValue(sSql, "HealthLevelHistoryKey"))

End Property


Public Property Let HealthLevelHistoryKey(ByVal lNewValue As Long)
    lHealthLevelHistoryKey = lNewValue
End Property


'Dim lRoomKey As Long
'-------   lRoomKey  -----------------------------
Public Property Get RoomKey() As Long
    
    If lContainerKey = -1 Or m_Mode = gcEditMode Or (m_Mode = gcAddMode And chkUseNextID.Value) Then
        RoomKey = cboRoom.ItemData(cboRoom.ListIndex)
        Exit Sub
    End If

   'QueryDb for ContainerID
    Dim sSql As String
    sSql = "SELECT Room.[_room_key] as RoomKey" & _
        " FROM ((Container) LEFT JOIN ContainerHistory ON  ContainerHistory.[_containerHistory_key] = Container.[_containerHistory_key]) " & _
        " LEFT JOIN Room ON   Room.[_room_key] = ContainerHistory.[_room_key]" & _
        " WHERE  Container.[_container_key]  = " & lContainerKey & ";"
    RoomKey = CLng(oQuery.GetQueryValue(sSql, "RoomKey"))
    
    
End Property


Public Property Let RoomKey(ByVal lNewValue As Long)
    lRoomKey = lNewValue
End Property

'Dim lContainerHistoryKey As Long
'-------  ContainerHistoryKey  Property  -----------------------------
Public Property Get containerHistoryKey() As Long

    If lContainerKey = -1 Then
        containerHistoryKey = -1
        Exit Sub
    End If

    Dim sSql As String
    'QueryDb for ContainerID
    sSql = "Select container.[_containerHistory_key] as containerHistoryKey from container where  [_container_key]  = " & lContainerKey & ";"
    containerHistoryKey = Trim(oQuery.GetQueryValue(sSql, "containerHistoryKey"))

    
End Property


Public Property Let containerHistoryKey(ByVal lNewValue As Long)
    lContainerHistoryKey = lNewValue
End Property


'
'Dim dJCMSSortByPenName As Boolean
'-------  JCMSSortByPenName  Property  -----------------------------
Public Property Get JCMSSortByPenName() As Boolean
    JCMSSortByPenName = dJCMSSortByPenName
End Property


Public Property Let JCMSSortByPenName(ByVal bNewValue As Boolean)
    dJCMSSortByPenName = bNewValue
End Property



'-------------------- -- ------- --- ------------------------------
Private Function setContainerNameListIndex(lIndex As Long) As Long
   Dim lret As Long
   lret = SendMessageLong(cboContainerName.hwnd, _
                            CB_SETCURSEL, lIndex, 0)
    
    setContainerNameListIndex = lret

End Function

Private Function getContainerNameListIndex() As Long
   Dim lIndex As Long
   
   lIndex = SendMessageLong(cboContainerName.hwnd, _
                            CB_GETCURSEL, 0, 0)
    
    getContainerNameListIndex = lIndex
    
End Function

' oMyObject - ComboBox only.
Private Function getContainerNameItemData(Optional lSelectIndex As Long = -10) As Long
   
    Dim lIndex As Long
    Dim lItemData As Long
   
    If (lSelectIndex <> -10) Then
        lItemData = SendMessageLong(cboContainerName.hwnd, _
                            CB_GETITEMDATA, lSelectIndex, 0)
    Else
        lIndex = getContainerNameListIndex()
        
        lItemData = SendMessageLong(cboContainerName.hwnd, _
                            CB_GETITEMDATA, lIndex, 0)
    End If
    getContainerNameItemData = lItemData
    
End Function

Private Function setContainerIDListIndex(lIndex As Long) As Long
   Dim lret As Long
   lret = SendMessageLong(cboContainerID.hwnd, _
                            CB_SETCURSEL, lIndex, 0)
    
    setContainerIDListIndex = lret

End Function

Private Function getContainerIDListIndex() As Long
   Dim lIndex As Long
   
   lIndex = SendMessageLong(cboContainerID.hwnd, _
                            CB_GETCURSEL, 0, 0)
    
    getContainerIDListIndex = lIndex
    


End Function

Private Function getContainerIDItemData(Optional lSelectIndex As Long = -10) As Long
   
    Dim lIndex As Long
    Dim lItemData As Long
   
    If (lSelectIndex <> -10) Then
        lItemData = SendMessageLong(cboContainerID.hwnd, _
                            CB_GETITEMDATA, lSelectIndex, 0)
    Else
        lIndex = getContainerIDListIndex()
        
        lItemData = SendMessageLong(cboContainerID.hwnd, _
                            CB_GETITEMDATA, lIndex, 0)
    End If
    getContainerIDItemData = lItemData
    
End Function


' oMyObject - ComboBox only.
Private Function UpdateContainerCombo(rsResults As DAO.Recordset) As Long
    
    Dim strKey As String
    strKey = "containerKey"

On Error GoTo UpdateContainerCombo_error
     
     Dim lCounter As Long
    
    Dim lKey As Long
    Dim sName As String
    Dim retVal As Long
    ' Remove everything from the combo box.
    cboContainerID.Clear
    cboContainerName.Clear
        
    cboContainerID.AddItem ""
    cboContainerID.ItemData(cboContainerID.NewIndex) = -1
     
    cboContainerName.AddItem ""
    cboContainerName.ItemData(cboContainerName.NewIndex) = -1
     
     If IsNull(rsResults) Then
        UpdateContainerCombo = -100
        Exit Function
     End If
     
     If 0 = rsResults.RecordCount Then
        UpdateContainerCombo = -200
        Exit Function
    End If
     
'     rsResults.Sort = True
     rsResults.MoveFirst
     If Not rsResults.EOF Then
'         rsResults.Sort = Trim("[_container_key]") & " DESC;"
         rsResults.MoveFirst
    Else
        UpdateContainerCombo = 100
        Exit Function
    End If
    
    lCounter = 0
    While Not rsResults.EOF
        If Not IsNull(rsResults(Trim("containerName")).Value) Then
            If "" <> rsResults(Trim("containerName")).Value Then
                sName = rsResults(Trim("containerName")).Value
                lKey = rsResults(Trim(strKey)).Value
                ' Add Item
                retVal = SendMessageByString(cboContainerName.hwnd, _
                                      CB_INSERTSTRING, _
                                      -1, _
                                      sName)
                ' Add ItemData
                retVal = SendMessageLong(cboContainerName.hwnd, _
                             CB_SETITEMDATA, _
                             retVal, _
                             lKey)
            End If
        End If
      '  If Not IsNull(rsResults(Trim("containerID")).Value) Then
            sName = rsResults(Trim("containerID")).Value
            lKey = rsResults(Trim(strKey)).Value
            ' Add Item
            retVal = SendMessageByString(cboContainerID.hwnd, _
                                  CB_INSERTSTRING, _
                                  -1, _
                                  sName)
            ' Add ItemData
            retVal = SendMessageLong(cboContainerID.hwnd, _
                         CB_SETITEMDATA, _
                         retVal, _
                         lKey)
       ' End If
        rsResults.MoveNext
     '   lCounter = lCounter + 1
        DoEvents
    Wend
    
     ' Select the first Item in the combo box.
     setContainerIDListIndex 0
     setContainerNameListIndex 0
    
    UpdateContainerCombo = 0
   ' txtComment.Text = Str(lCounter)
   ' MsgBox "NumPens:" + Str(lCounter)
Exit Function
UpdateContainerCombo_error:
    UpdateContainerCombo = 99
    MsgBox "UpdateCombo Error"
    MsgBox Err.Description
    
End Function

' oMyObject - ComboBox only.
Private Function UpdateCombo(ByRef oMyObject As ComboBox, rsResults As DAO.Recordset, strColumn As String, strKey As String) As Long
    

On Error GoTo UpdateCombo_error
Const MaxItems = 32766
     Dim lCounter As Long
    
    Dim lKey As Long
    Dim sName As String
    Dim retVal As Long
    ' Remove everything from the combo box.
    oMyObject.Clear
        
    oMyObject.AddItem ""
    oMyObject.ItemData(oMyObject.NewIndex) = -1
     
     If IsNull(rsResults) Then
        UpdateCombo = -100
        Exit Function
     End If
     
     If 0 = rsResults.RecordCount Then
        UpdateCombo = -200
        Exit Function
    End If
     
     rsResults.Sort = True
     rsResults.MoveFirst
     If Not rsResults.EOF Then
         rsResults.Sort = Trim(strColumn) & " DESC;"
         rsResults.MoveFirst
    Else
        UpdateCombo = 100
        Exit Function
    End If
    
    lCounter = 0
    While Not rsResults.EOF And (lCounter < MaxItems)
        If Not IsNull(rsResults(Trim(strColumn)).Value) Then
            If "" <> rsResults(Trim(strColumn)).Value Then
              oMyObject.AddItem rsResults(Trim(strColumn)).Value
              oMyObject.ItemData(oMyObject.NewIndex) = rsResults(Trim(strKey)).Value
            End If
        End If
         rsResults.MoveNext
         lCounter = lCounter + 1
         DoEvents
    Wend
    
     ' Select the first Item in the combo box.
     oMyObject.ListIndex = 0
    
    UpdateCombo = 0
Exit Function
UpdateCombo_error:
    UpdateCombo = 99
    MsgBox "UpdateCombo Error"
    MsgBox Err.Description
    
End Function

Private Function inComboBox(ByRef oMyObject As ComboBox, strText As String) As Boolean
    Dim iStatusCount As Long
    Dim i As Long
    Dim strTypeName As String
    strTypeName = TypeName(oMyObject)
    If "ComboBox" <> strTypeName Then
        inComboBox = False
        Exit Function
    End If
    
    iStatusCount = oMyObject.ListCount
    
    For i = 0 To iStatusCount
        If Trim(strText) = oMyObject.List(i) Then
            inComboBox = True
            Exit Function
        End If
    Next i
    inComboBox = False
End Function

Private Function ArrayStringSize(ByRef stringArr() As String) As Long

On Error GoTo ArrayStringSize_error

ArrayStringSize = 0

ArrayStringSize = UBound(stringArr())

Dim strX As String

strX = stringArr(0)
If "empty" = stringArr(0) Then
    ArrayStringSize = -1
End If

Exit Function
ArrayStringSize_error:

If 9 = Err.Number Then
    ArrayStringSize = -1
End If

End Function



Private Sub AddString2List(ByRef stringArr() As String, iSize As Long, Optional bEmpty As Boolean = False)
On Error GoTo AddString2List_error

If bEmpty Then
    ReDim stringArr(0)
    stringArr(0) = "empty"
Else
    If "empty" = stringArr(0) Then
        iSize = 0
    Else
        iSize = UBound(stringArr()) + 1
    End If
    ReDim Preserve stringArr(iSize)
End If


Exit Sub
AddString2List_error:

    If 9 = Err.Number Then
        iSize = 0
        Resume Next
    End If

End Sub


' m_LoadContainerMode

'WARNING! DO NOT REMOVE OR MODIFY THE FOLLOWING COMMENTED LINES!
'
Public Property Get LoadContainerMode() As Integer
    LoadContainerMode = m_LoadContainerMode
End Property

Public Property Let LoadContainerMode(ByVal New_Mode As Integer)
    
    If Not bDebug Then
        If Ambient.UserMode Then Err.Raise 382
    End If
    
    If (New_Mode >= 1) And (New_Mode <= 2) Then
        m_LoadContainerMode = New_Mode
        PropertyChanged "LoadContainerMode"
        UpdateControlMode
        LoadContainerID
    End If
    
End Property


'WARNING! DO NOT REMOVE OR MODIFY THE FOLLOWING COMMENTED LINES!
'MemberInfo=7,1,0,2
Public Property Get Mode() As Integer
Attribute Mode.VB_Description = "Controls Mode"
    Mode = m_Mode
End Property

Public Property Let Mode(ByVal New_Mode As Integer)
    
    If Not bDebug Then
        If Ambient.UserMode Then Err.Raise 382
    End If
    
    If (New_Mode >= 1) And (New_Mode <= 4) Then
        m_Mode = New_Mode
        PropertyChanged "Mode"
        UpdateControlMode
        LoadContainerID
    End If
    
End Property

'Initialize Properties for User Control
Private Sub UserControl_InitProperties()

On Error GoTo UserControl_InitProperties_error
    m_Mode = m_def_Mode
    m_LoadContainerMode = m_def_LoadContainerMode
    bJCMSSortByPenName = False
    
    bUsingHealthLevel = False
    bUsingPenComments = False
    bUsingPenNames = False

Exit Sub
UserControl_InitProperties_error:
MsgBox "UserControl_InitProperties_error"
MsgBox Err.Description

End Sub


'Load property values from storage
Private Sub UserControl_ReadProperties(PropBag As PropertyBag)

On Error GoTo UserControl_ReadProperties_error
    
    m_Mode = PropBag.ReadProperty("Mode", m_def_Mode)
    ' LoadContainerMode
    m_LoadContainerMode = PropBag.ReadProperty("LoadContainerMode", m_def_LoadContainerMode)
    
    bJCMSSortByPenName = PropBag.ReadProperty("JCMSSortByPenName", False)

    bUsingHealthLevel = PropBag.ReadProperty("UsingHealthLevel", False)
    bUsingPenComments = PropBag.ReadProperty("UsingPenComments", False)
    bUsingPenNames = PropBag.ReadProperty("UsingPenNames", False)
    'Update mode
    UpdateControlMode
        ' Load Container ID
    LoadContainerID

Exit Sub

UserControl_ReadProperties_error:
MsgBox "UserControl_ReadProperties_error"
MsgBox Err.Description
    
End Sub

'Write property values to storage
Private Sub UserControl_WriteProperties(PropBag As PropertyBag)
    
On Error GoTo UserControl_WriteProperties_error
    
    Call PropBag.WriteProperty("Mode", m_Mode, m_def_Mode)
    Call PropBag.WriteProperty("LoadContainerMode", m_LoadContainerMode, m_def_LoadContainerMode)
    Call PropBag.WriteProperty("JCMSSortByPenName", bJCMSSortByPenName, False)
    Call PropBag.WriteProperty("UsingHealthLevel", bUsingHealthLevel, False)
    Call PropBag.WriteProperty("UsingPenComments", bUsingPenComments, False)
    Call PropBag.WriteProperty("UsingPenNames", bUsingPenNames, False)

Exit Sub
UserControl_WriteProperties_error:
MsgBox "UserControl_WriteProperties_error"
MsgBox Err.Description

End Sub

''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Copyright ©1996-2009 VBnet, Randy Birch, All Rights Reserved.
' Some pages may also contain other copyrights by the author.
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Distribution: You can freely use this code in your own
'               applications, but you may not reproduce
'               or publish this code on any web site,
'               online service, or distribute as source
'               on any media without express permission.
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' from: http://vbnet.mvps.org/index.html?code/helpers/numbercnvt.htm
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Public Function HiByte(ByVal wParam As Integer) As Byte
  
  'note: VB4-32 users should declare this function As Integer
   HiByte = (wParam And &HFF00&) \ (&H100)
 
End Function


Public Function LoByte(ByVal wParam As Integer) As Byte

  'note: VB4-32 users should declare this function As Integer
   LoByte = wParam And &HFF&

End Function


Public Function HiWord(wParam As Long) As Integer

   If wParam And &H80000000 Then
      HiWord = (wParam \ 65535) - 1
   Else
      HiWord = wParam \ 65535
   End If

End Function


Public Function LoWord(wParam As Long) As Integer


    If -1 = wParam Then
      LoWord = -1
      Exit Function
    End If
   If wParam And &H8000& Then
      LoWord = -1
      LoWord = &H8000& Or (wParam And &H7FFF&)
   Else
      LoWord = wParam And &HFFFF&
   End If

End Function


Public Function LoWordCM(wParam As Long) As Integer

  'using API
' The following line creates a compilation error.
'   CopyMemory LoWordCM, wParam, 2
  
End Function


Public Function LShiftWord(ByVal w As Integer, ByVal c As Integer) As Integer

   Dim dw As Long
   dw = w * (2 ^ c)
   If dw And &H8000& Then
      LShiftWord = CInt(dw And &H7FFF&) Or &H8000&
   Else
      LShiftWord = dw And &HFFFF&
   End If

End Function


Public Function RShiftWord(ByVal w As Integer, ByVal c As Integer) As Integer

   Dim dw As Long
   If c = 0 Then
      RShiftWord = w
   Else
      dw = w And &HFFFF&
      dw = dw \ (2 ^ c)
      RShiftWord = dw And &HFFFF&
   End If

End Function


Public Function MakeWord(ByVal bHi As Byte, ByVal bLo As Byte) As Integer

   If bHi And &H80 Then
      MakeWord = (((bHi And &H7F) * 256) + bLo) Or &H8000&
   Else
      MakeWord = (bHi * 256) + bLo
   End If

End Function


Public Function MakeDWord(wHi As Integer, wLo As Integer) As Long

   If wHi And &H8000& Then
      MakeDWord = (((wHi And &H7FFF&) * 65536) Or _
                    (wLo And &HFFFF&)) Or &H80000000
   Else
      MakeDWord = (wHi * 65535) + wLo
   End If

End Function
         

Public Function MAKELONG(wLow As Long, wHigh As Long) As Long

  MAKELONG = LoWord(wLow) Or (&H10000 * LoWord(wHigh))
  
End Function


Public Function MAKELPARAM(wLow As Long, wHigh As Long) As Long

 'Combines two integers into a long integer
  MAKELPARAM = MAKELONG(wLow, wHigh)
  
End Function
         

''''''''''''''''''''''' END ''''''''''''''''''''''''''''''
' Copyright ©1996-2009 VBnet, Randy Birch, All Rights Reserved.
''''''''''''''''''''''' END ''''''''''''''''''''''''''''''

