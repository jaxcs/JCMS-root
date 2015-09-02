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
function previewCageCard(){
    if(document.getElementById('buttons2:goodCard').value == "true"){                        
        var theTarget = document.getElementById('addMatingFormId').target;
        document.getElementById('buttons2').target = '_blank';
        document.getElementById('buttons2:secretButton').click();
        document.getElementById('buttons2').target = theTarget;
    }
}

function checkInput(){
    if(document.getElementById('buttons1:successfulSave').value=="true"){
            var theTarget = document.getElementById('addMatingFormId').target;
            document.getElementById('buttons2').target = '_blank';
            document.getElementById('buttons2:secretButton2').click();
            document.getElementById('buttons2').target = theTarget;
            document.getElementById('buttons2:secretButton3').click();
    }
}

function enableElements () {
    if(document.getElementById('addMatingFormId:selectDam1')){    
        document.getElementById('addMatingFormId:selectDam1').disabled = false;
    }
    if(document.getElementById('addMatingFormId:selectDam2')){
        document.getElementById('addMatingFormId:selectDam2').disabled = false;
    }
     if(document.getElementById('addMatingFormId:selectSire')){
        document.getElementById('addMatingFormId:selectSire').disabled = false;
    }
    if(document.getElementById('addMatingFormId:dam1CommandLink')){
        document.getElementById('addMatingFormId:dam1CommandLink').disabled = false;
    }
    if(document.getElementById('addMatingFormId:sireCommandLink')){
        document.getElementById('addMatingFormId:sireCommandLink').disabled = false;
    }
    if(document.getElementById('addMatingFormId:dam1DietButton')){
        document.getElementById('addMatingFormId:dam1DietButton').disabled = false;
    }
     if(document.getElementById('addMatingFormId:sireDietButton')){    
        document.getElementById('addMatingFormId:sireDietButton').disabled = false;
    }
     if(document.getElementById('addMatingFormId:dam2DietButton')){
        document.getElementById('addMatingFormId:dam2DietButton').disabled = false;
    }
    if(document.getElementById('addMatingFormId:dam2CommandLink')){
        document.getElementById('addMatingFormId:dam2CommandLink').disabled = false;
    }
 }