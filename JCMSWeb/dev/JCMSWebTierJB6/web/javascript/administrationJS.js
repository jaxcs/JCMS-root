/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/
var isEdit = false;
function getIsEdit() {
    return isEdit;
}
function setIsEdit(b) {
    isEdit = b;
}
function retirePensWarning() {
    var question = "Do you want to retire all pens that have no live mice in them (i.e. those that are empty or all mice have an exit life status)?  \n\n" + 
                "This procedure can take several minutes to run.  Approximately 610 pens a minute.  It is best to run this procedure when no one else is using the database.  ";
    if(confirm(question)){
        document.getElementById('adminHomeId:adminRetirePensView:secretRetirePensButton').click();
    }
}
function disableInputDate() {
    $(".rf-cal-inp").css("color", "black");
    return true;
}
function enableStrainEditButton() {
    $("[id$=strainEditButton]").removeAttr("disabled");           
    return true;
}
function disableStrainEditButton() { 
    $("[id$=strainEditButton]").attr("disabled", "disabled");           
    return true;
}
function enableStrainSaveButton() {
    $("[id$=strainSaveButton]").removeAttr("disabled");           
    return true;
}
function disableStrainSaveButton() {
    $("[id$=strainSaveButton]").attr("disabled", "disabled");           
    return true;
}
function showStrainDetail() {
    $("[id$=adminStrainDetailPanel]").show();           
    return true;
}
function hideStrainDetail() {
    $("[id$=adminStrainDetailPanel]").hide();           
    return true;
}
function moveFocusToInput() {
    $('.startHere').first().focus();
    return true;
}
function setNumberOfFilteredRows() {
    $('[id$=alleleFilterCount]').text($('[id$=alleleTable] tbody:first .rf-dt-r').length);
    $('[id$=approvedStrainFilterCount]').text($('[id$=approvedStrainTable] tbody:first .rf-dt-r').length);
    $('[id$=cageFilterCount]').text($('[id$=cageResultsTableId] tbody:first .rf-dt-r').length);
    $('[id$=containerFilterCount]').text($('[id$=containerStatusTable] tbody:first .rf-dt-r').length);
    $('[id$=geneFilterCount]').text($('[id$=geneTable] tbody:first .rf-dt-r').length);
    $('[id$=generalFilterCount]').text($('[id$=tableId] tbody:first .rf-dt-r').length);
    $('[id$=lifeStatusFilterCount]').text($('[id$=lifeStatusTable] tbody:first .rf-dt-r').length);
    $('[id$=mouseUseFilterCount]').text($('[id$=mouseUseTable] tbody:first .rf-dt-r').length);
    $('[id$=roomFilterCount]').text($('[id$=roomResultsTableId] tbody:first .rf-dt-r').length);
    $('[id$=strainFilterCount]').text($('[id$=strainResultsTableId] tbody:first .rf-dt-r').length);
    return true;
}
function addWorkgroupWarning(){
    var r = false;
    var r=confirm("Are you sure you wish to create this workgroup? Once completed this cannot be undone");
    if(r){
        document.getElementById('adminHomeId:adminCenterView:secretAddWGButton').click();
    }
}
function addCenterWarning(){
    var r = false;
    var r=confirm("Are you sure you wish to create this center? Once completed this cannot be undone");
    if(r){
        document.getElementById('adminHomeId:adminCenterView:secretAddCenterButton').click();
    }
}
function warnRemoveUser(){
    var r = false;
    var r = confirm("This is the last WG you are an administrator of, if you continue you will be logged off and may be unable to log back in. \n\n" +
                    "Are you sure you want to remove yourself from this workgroup?");
    if(r){
        removeUser();
    }
}
function warnUpdateUser(){
    var r = false;
    var r = confirm("This is the last WG that you administrate, if you continue with this update you will \n"+
                    "be logged off and unable to access the administration screen upon logging back in. \n\n" +
                    "Are you sure you want to remove your administrative privileges for this workgroup?");
    if(r){
        updateUser();
    }
}
function processStrainEvent (target) {
    updateStrainVal(target.previousElementSibling.children[0].value);
}
function showHistoryCheck (primaryKey) {
    if (!getIsEdit())
        showHistory(primaryKey);
}
function blankTarget(){
    if(document.getElementById('adminHomeId:adminManageCageStatusLocationView:queryDefintionID').value != ''){
        var theTarget = document.getElementById('adminHomeId').target;
        //makes it open in new window
        document.getElementById('adminHomeId').target='_blank';
        document.getElementById('adminHomeId:adminManageCageStatusLocationView:secretButtonPrintCageCard').click();
        //resets to original target
        document.getElementById('adminHomeId').target = theTarget;
    }
}