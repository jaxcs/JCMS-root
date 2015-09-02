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
// function to check or uncheck all rows
function checkAllRowsMouseQuery(frm, fieldChecked) {
    var str = "mouseQueryFormId:";
    var checkValue = frm.elements[str+fieldChecked].checked;
    
    frm.elements[str+'selectMouseID'].checked = checkValue;
    frm.elements[str+'selectMouseStrain'].checked = checkValue;
    frm.elements[str+'selectMouseLifeStatus'].checked = checkValue;
    frm.elements[str+'selectMouseBreedingStatus'].checked = checkValue;
    frm.elements[str+'selectMouseGeneration'].checked = checkValue;
    frm.elements[str+'selectMouseOwner'].checked = checkValue;
    frm.elements[str+'selectMouseOrigin'].checked = checkValue;
    frm.elements[str+'selectMouseSex'].checked = checkValue;
    frm.elements[str+'selectMouseCOD'].checked = checkValue;
    frm.elements[str+'selectMouseDOB'].checked = checkValue;
    frm.elements[str+'selectMouseExitDate'].checked = checkValue;
    frm.elements[str+'selectMouseLitter'].checked = checkValue;
    frm.elements[str+'selectMousePenID'].checked = checkValue;
    frm.elements[str+'selectMousePenName'].checked = checkValue;
    frm.elements[str+'selectMouseRoom'].checked = checkValue;
    frm.elements[str+'selectMouseUse'].checked = checkValue;
    frm.elements[str+'selectMouseGenotype'].checked = checkValue;
    frm.elements[str+'selectMouseGenotypeDate'].checked = checkValue;
    frm.elements[str+'selectMouseAge'].checked = checkValue;
    frm.elements[str+'selectMouseMating'].checked = checkValue;
    frm.elements[str+'selectMouseParents'].checked = checkValue;
    frm.elements[str+'selectMouseCoatColor'].checked = checkValue;
    frm.elements[str+'selectMouseDiet'].checked = checkValue;
    frm.elements[str+'selectMouseProtocolID'].checked = checkValue;
    frm.elements[str+'selectMouseLitterMates'].checked = checkValue;
    frm.elements[str+'selectMouseComment'].checked = checkValue;
    frm.elements[str+'selectUseScheduleTerm'].checked = checkValue;
    frm.elements[str+'selectMouseComment'].checked = checkValue;
    frm.elements[str+'selectMousePhenotypes'].checked = checkValue;
}

// function to check or uncheck all rows
function checkAllRowsMatingQuery(frm, fieldChecked) {
    var str = "matingQueryFormId:";
    var checkValue = frm.elements[str+fieldChecked].checked;

    frm.elements[str+'selectMatingID'].checked = checkValue;
    frm.elements[str+'selectMatingDates'].checked = checkValue;
    frm.elements[str+'selectMatingStatus'].checked = checkValue;
    frm.elements[str+'selectMatingStrain'].checked = checkValue;
    frm.elements[str+'selectMatingGeneration'].checked = checkValue;
    frm.elements[str+'selectMatingOwner'].checked = checkValue;
    frm.elements[str+'selectMatingPenId'].checked = checkValue;
    frm.elements[str+'selectMatingPenName'].checked = checkValue;
    frm.elements[str+'selectDam1ID'].checked = checkValue;
    frm.elements[str+'selectDam1Strain'].checked = checkValue;
    frm.elements[str+'selectDam1Stock'].checked = checkValue;
    frm.elements[str+'selectDam1Gen'].checked = checkValue;
    frm.elements[str+'selectDam1Genotype'].checked = checkValue;
    frm.elements[str+'selectDam1DOB'].checked = checkValue;
    frm.elements[str+'selectDam1PlugDate'].checked = checkValue;
    frm.elements[str+'selectDam2ID'].checked = checkValue;
    frm.elements[str+'selectDam2Strain'].checked = checkValue;
    frm.elements[str+'selectDam2Stock'].checked = checkValue;
    frm.elements[str+'selectDam2Gen'].checked = checkValue;
    frm.elements[str+'selectDam2Genotype'].checked = checkValue;
    frm.elements[str+'selectDam2DOB'].checked = checkValue;
    frm.elements[str+'selectDam2PlugDate'].checked = checkValue;
    frm.elements[str+'selectSireID'].checked = checkValue;
    frm.elements[str+'selectSireStrain'].checked = checkValue;
    frm.elements[str+'selectSireStock'].checked = checkValue;
    frm.elements[str+'selectSireGen'].checked = checkValue;
    frm.elements[str+'selectSireGenotype'].checked = checkValue;
    frm.elements[str+'selectSireDOB'].checked = checkValue;
    frm.elements[str+'selectDateRetired'].checked = checkValue;
    frm.elements[str+'selectWeanTime'].checked = checkValue;
    frm.elements[str+'selectNeedsTyping'].checked = checkValue;
}

// function to check or uncheck all rows
function checkAllRowsExperimentQuery(frm, fieldChecked) {
    var str = "experimentQueryFormId:";
    var checkValue = frm.elements[str+fieldChecked].checked;

    frm.elements[str+'selectMouseID'].checked = checkValue;
    frm.elements[str+'selectDataID'].checked = checkValue;
    frm.elements[str+'selectDataOwner'].checked = checkValue;
    frm.elements[str+'selectCollectionDate'].checked = checkValue;
    frm.elements[str+'selectCollectionAge'].checked = checkValue;
    frm.elements[str+'selectAbnormalData'].checked = checkValue;
    frm.elements[str+'selectMouseID'].checked = checkValue;
    frm.elements[str+'selectMouseStrain'].checked = checkValue;
    frm.elements[str+'selectMouseLifeStatus'].checked = checkValue;
    frm.elements[str+'selectDataResults'].checked = checkValue;
    frm.elements[str+'selectMouseComment'].checked = checkValue;
    frm.elements[str+'selectGeneration'].checked = checkValue;
    frm.elements[str+'selectBirthDate'].checked = checkValue;
    frm.elements[str+'selectSex'].checked = checkValue;
    frm.elements[str+'selectCOD'].checked = checkValue;
    frm.elements[str+'selectMouseCageID'].checked = checkValue;
    frm.elements[str+'selectCageName'].checked = checkValue;
}

// function to confirm submit
function confirmSubmit() {
    var agree=confirm("Are you sure you wish to delete?");
    if (agree)
    return true ;
    else
    return false ;
}