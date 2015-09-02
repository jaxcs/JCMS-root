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

// http://docs.jboss.org/richfaces/latest_3_3_X/en/devguide/html/ArchitectureOverview.html#FilterConfiguration
//A4J.AJAX.onError = function(req, status, message)
//{
//    gotoPage("/sessionExpired.xhtml");
////    window.alert("Custom onError handler "+message);
//}

// http://docs.jboss.org/richfaces/latest_3_3_X/en/devguide/html/ArchitectureOverview.html#FilterConfiguration
//A4J.AJAX.onExpired = function(loc, expiredMsg)
//{
////    if (window.confirm("Custom onExpired handler "+ expiredMsg +" for a location: "+ loc))
////    {
//        gotoPage("/sessionExpired.xhtml");
//        return loc;
////    }
////    else
////    {
////        return false;
////    }
//}


function doPopup(source)
{
    // window.alert("doPopup");

    popup = window.open("",
            "popup",
            "height=600,width=800,scrollbars=yes,toolbar=no,menubar=no");

    // openerFormId = source.form.id;
    popup.focus();

    document.getElementById("hidden:go").onclick(null);
}

function doSaveLine(lineKey)
{
    var formId = window.document.getElementById("LineFormId:parentFormId").value;

    // This is proof of concept.  Field is on AdministrationHome.xhtml.
    opener.document.forms[formId][formId + ":lineKey"].value = lineKey;

    window.close();
    return false;
}

function doPopupDescription(source)
{
    // formId:inputs:0:descriptionId
    var idName      = source.id;
    var lastIndex   = idName.lastIndexOf(":");
    var rootId      = idName.substring(0,lastIndex);
    var fullDescriptionId   = rootId + ":descriptionId" ;

    var description = document.getElementById(source.id).value;

    document.getElementById("hidden:description").value = "This is a test.";
    document.getElementById("hidden:openerFormDescriptionId").value = fullDescriptionId;

    window.alert(document.getElementById("hidden:description").value);

    popup = window.open("",
            "popup",
            "height=350,width=500,scrollbars=yes,toolbar=no,menubar=no");

    // openerFormId = source.form.id;
    popup.focus();

    document.getElementById("hidden:go").onclick(null);
}


function showhidediv(divID)
{
    if (document.getElementById)
    {
        obj = document.getElementById(divID);

        if (obj.style.visibility == 'hidden')
        {
            obj.style.visibility = 'visible';
            obj.style.display = 'inline';
        } else
        {
            obj.style.visibility = 'hidden';
            obj.style.display = 'none';
        }
    }
}

//  When selecting a default location enable or disable the corresponding location
function synchronizeLocation(frm)
{
    var idName          = frm.id;
    var startingIndex   = (idName.lastIndexOf(":")) + 1;
    var rowValue        = idName.substring(startingIndex);

    window.document.getElementById("formId:locations:"+ rowValue).checked = true;
    
    return false;
}

//  When checking the first location auto select it as the default location
function synchronizeDefaultLocation(frm)
{
    var idName          = frm.id;
    var startingIndex   = (idName.lastIndexOf(":")) + 1;
    var rowValue        = idName.substring(startingIndex);

    window.document.getElementById("formId:defaultLocation:"+ rowValue).checked = window.document.getElementById("formId:locations:"+ rowValue).checked;

    return false;
}
