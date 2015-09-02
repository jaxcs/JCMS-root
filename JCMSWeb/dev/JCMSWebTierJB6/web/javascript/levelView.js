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
jQuery.noConflict();
$(document).ready(function() {
    applyLevelViewJQuery();
    initTouch();
});

var touchDevice = is_touch_device();

function is_touch_device() {
  try {
    document.createEvent("TouchEvent");
    return true;
  } catch (e) {
    return false;
  }
}

function applyLevelViewJQuery(){
    initTouch();
    $( "div[class*='cage']" ).draggable({
        appendTo: "body",
        helper: "clone",
        scroll: true
    })
    $( "li[class*='ui-selectlistbox-item']" ).draggable({
        appendTo: "body",
        helper: "clone",
        scroll: true
    })
    $("table[class*='miceTable'] span[class*=mouseID]").draggable({
        appendTo: "body",
        helper: "clone",
        scroll: true,
        start: function(event, ui) {
            var c = {};
            c.tr = this;
            c.helper = ui.helper;
        }
    })
    $("img[id*='pupMale']").draggable({
        appendTo: "body",
        helper: "clone",
        scroll: true
    })
    $("img[id*='pupFemale']").draggable({
        appendTo: "body",
        helper: "clone",
        scroll: true
    })
    trashCanJQuery();
    $("div[class*='slot']"  ).droppable({
        hoverClass: "ui-state-hover",
        accept: function(d){
            //d is what you're dragging, $(this) is what you're dropping on
            if(d.hasClass("ui-selectlistbox-item")){
                if($(this).hasClass("empty")){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(d.hasClass("cage")){
                return true;
            }
            else if(d.hasClass("mouseID")){
                if($(this).hasClass("createMating")){
                    return true;
                }
                else if($(this).hasClass("weanCage")){
                    return true;
                }
                else if($(this).hasClass("empty")){
                    return false;
                }
                else{
                    return true;
                }
            }
            else if(d.hasClass("pupMale")){
                return true;
            }
            else if(d.hasClass("pupFemale")){
                return true;
            }
            else{
                return false;
            }
        },
        drop: function( event, ui ) {
            if($(ui.draggable ).hasClass('ui-selectlistbox-item')){             
                $( "input[name*=draggedCage]" )[0].value = $( ui.draggable )[0].innerHTML;
                $( "input[name*=droppedPosition]" )[0].value = $( this ).find("input[name*=position]")[0].value;
                dropNewCage();
            }
            else if($(ui.draggable ).hasClass('mouseID')){
                $( "input[name*=draggedMouse]")[0].value = $(ui.draggable).find("input")[0].value;
                $( "input[name*=droppedPosition]" )[0].value = $( this ).find("input[name*=position]")[0].value;
                if($(this).hasClass("createMating")){
                    matingPartnerDrop();
                }
                else{
                    moveMouseDrop();
                }    
            }            
            else if($(ui.draggable).hasClass('pupFemale')){
                $( "input[name*=droppedPosition]" )[0].value = $( this ).find("input[name*=position]")[0].value;
                dropFemalePup();
                disableWeanCageNames();
            }           
            else if($(ui.draggable).hasClass('pupMale')){
                $( "input[name*=droppedPosition]" )[0].value = $( this ).find("input[name*=position]")[0].value;
                dropMalePup();
                disableWeanCageNames();
            }
            else {
                $( "input[name*=draggedPosition]" )[0].value = $( ui.draggable ).find("input[name*=position]")[0].value;
                $( "input[name*=droppedPosition]" )[0].value = $( this ).find("input[name*=position]")[0].value;
                if($(this).hasClass("createMating")){
                    dropMatingMember();
                }
                else{
                    dropExistingCage();
                }    
            }
        }
    })
    //generally this is only called when the level grid is getting re rendered or when user first navs to page, so anything that does
    //anything to the level view needs to be called here
    onStrainSelect(); //highlights cages of that strain yellow
    deleteHighlight(); //
    checkDeleteHighlight();
    highlightShown();    
    styleWeanCages();
    addPupListener();
    disableWeanCageNames();
    $('body>div').bind("dragstart", function(event, ui){
        event.stopPropagation();
    })
}

function highlightShown(){
    $( "div[class*='shown-highlight']" ).removeClass('shown-highlight');
    var shown = $("input[name*=shownCage]")[0].value;
    if(shown != ''){
        //div object is the object that was selected to see the mice.
        $("input[value='" + shown +"'][name*='hiddenCageID']").parents("div[class*='cage']").addClass('shown-highlight');
    }
}

function trashCanJQuery(){
    $("div[class*='trashCan']").droppable({
        hoverClass: "ui-state-hover",
        accept: function(d){
            if(d.hasClass("cage")){
                return true;
            }
            else{
                return false;
            }
        },
        drop: function(event, ui){
            $( "input[name*=draggedPosition]" )[0].value = $( ui.draggable ).find("input[name*=position]")[0].value;
            $(ui.draggable).addClass('delete-highlight');
            cullDrop();
        }
    })
}

function onStrainSelect(){
    var strain = $( "input[id*=selectedStrain]" )[0].value;
    if(strain != ''){
        $( "div[class*='strain-highlight']" ).removeClass('strain-highlight');
        $( "div[class*='cage']" ).find("input[value='" + strain + "']").parents("div[class*='cage']").addClass('strain-highlight');
    }
    else{
        $( "div[class*='strain-highlight']" ).removeClass('strain-highlight');
    }
}

function deleteHighlight(){
    var deleting = $("span[id*=containerID]");
    for(var i = 0; i < deleting.length; i++){
        var containerID = deleting[i].innerHTML;
        var divObject = $("input[value='" + containerID +"']").parents("div[class*='cage']");
        if(!divObject.hasClass('delete-highlight')){
            divObject.addClass('delete-highlight');
        }
    }    
}

function checkDeleteHighlight(){
    //get all the highlighted values
    var highlighted = $("div[class*='delete-highlight']");
    //get all the values in the list of items to delete
    var deleting = $("span[id*=containerID]");
    var len = highlighted.length;
    //start looping through highlighted values and check to see that every highlighted value is 
    //in the list of values to be changed.
    for(var i = 0; i < len; i++){
        var inThere = false;
        //get the first highlighted ID
        var highlightedID = $(highlighted[i]).find("input[name*='hiddenCageID']")[0].value;
        var deletingLen = deleting.length;
        for(var j = 0; j < deletingLen; j++){
            //check to see that the highlighted ID is in the deleting list
            var deletingID = deleting[j].innerHTML;
            if(deletingID == highlightedID){
                inThere = true;
            }
        }
        //de-highlight
        if(!inThere){
            $(highlighted[0]).removeClass('delete-highlight');
        }
        inThere = false;
    }
}

function removeDeleteStyle(){
    checkDeleteHighlight();
}

function styleWeanCages(){
    var positions = $("input[id*='hiddenPupPosition']");
    $("div[class*='weanCage']").removeClass('weanCage');
    var len = positions.length;
    for(var i = 0; i < len; i++){
        var position = positions[i].value;
        var divObject = $("input[value='" + position +"']").parents("div[class*='slot']");
        if(!divObject.hasClass("weanCage")){
            divObject.addClass("weanCage");
        }
    }
}

function disableWeanCageNames(){
    //disable cage name for those cages that already have a cage name input
    var weanCages = $("div[class*='weanCage']");
    for(var i = 0; i < weanCages.length; i++){
        var position = $(weanCages[i]).find("input[name*='position']")[0].value;
        //need to find every cage name input in the mouse table that has this position
        var cageNameInputs = $("input[value='" + position +"']").parents("tr[class*='pupRow']").find("input[id*='pupCageNameInput']");
        for(var j = 0; j < cageNameInputs.length; j++){
            if(cageNameInputs[j].value == 'empty'){
                cageNameInputs[j].value = '';
            }
            if(j != cageNameInputs.length - 1){
                cageNameInputs[j].disabled = true;
            }
        }
    }
}

function addPupListener(){
    var pups = $("tr[class*='pupRow']");
    for(var i = 0; i < pups.length;  i++){
        var pup = pups[i];
        pup.addEventListener("mouseover", highlightPupCages, true);
        pup.addEventListener("mouseout", removePupHighlight, true);
        pup.addEventListener("touchstart", touchHighlightPupCages, true);
        pup.addEventListener("touchend", touchRemovePupHighlight, true);
    }
    var cages = $("div[class*='weanCage']");
    for(var j = 0; j < cages.length; j++){
        var cage = cages[j];
        cage.addEventListener("mouseover", highlightPups, true);
        cage.addEventListener("mouseout", removePupHighlight, true);
        cage.addEventListener("touchstart", touchHighlightPups, true);
        cage.addEventListener("touchend", touchRemovePupHighlight, true);
    }
}

function highlightPupCages(event){
    if(!touchDevice){
        var position = $("tr[id*='" + event.currentTarget.id + "']").find("input[id*='hiddenPupPosition']")[0].value;
        $("input[value='" + position +"']").parents("div[class*='slot']").addClass('pup-highlight');
        $("input[value='" + position +"']").parents("tr[class*='pupRow']").addClass('pup-highlight');
    }
}


function touchHighlightPupCages(event){
    if(touchDevice){
        var position = $("tr[id*='" + event.currentTarget.id + "']").find("input[id*='hiddenPupPosition']")[0].value;
        $("input[value='" + position +"']").parents("div[class*='slot']").addClass('pup-highlight');
        $("input[value='" + position +"']").parents("tr[class*='pupRow']").addClass('pup-highlight');        
        if(event.srcElement.id.indexOf("Button") == -1 && event.srcElement.id.indexOf("Input") == -1){
            event.preventDefault();
        }
    }
}

function highlightPups(event){
    if(!touchDevice){
        var position = $("div[id*='" + event.currentTarget.id + "']").find("input[name*='position']")[0].value;
        $("input[value='" + position +"']").parents("div[class*='slot']").addClass('pup-highlight');
        $("input[value='" + position +"']").parents("tr[class*='pupRow']").addClass('pup-highlight');
    }
}

function touchHighlightPups(event){
    if(touchDevice){
        var position = $("div[id*='" + event.currentTarget.id + "']").find("input[name*='position']")[0].value;
        $("input[value='" + position +"']").parents("div[class*='slot']").addClass('pup-highlight');
        $("input[value='" + position +"']").parents("tr[class*='pupRow']").addClass('pup-highlight');
        if(event.srcElement.id.indexOf("Button") == -1 && event.srcElement.id.indexOf("Input") == -1){
            event.preventDefault();
        }
    }
}

function removePupHighlight(event){
    if(!touchDevice){
        $(".pup-highlight").removeClass('pup-highlight');
    }
}

function touchRemovePupHighlight(event){
    if(touchDevice){
        $(".pup-highlight").removeClass('pup-highlight');
    }
}

function touchHandler(event)
{
    var flag = false;
    if(event.srcElement.id.indexOf("Button") !== -1){
        flag = true;
    }
    if(!flag){
        var touches = event.changedTouches,
            first = touches[0],
            type = "";

        switch(event.type)
        {
            case "touchstart":type = "mousedown";break;
            case "touchmove":type="mousemove";break;
            case "touchend":type="mouseup";break;
            default:return;
        }
        var simulatedEvent = document.createEvent("MouseEvent");
        simulatedEvent.initMouseEvent(type, true, true, window, 1,
                                first.screenX, first.screenY,
                                first.clientX, first.clientY, false,
                                false, false, false, 0/*left*/, null);

        first.target.dispatchEvent(simulatedEvent);
        event.preventDefault();
    }
}
 
function initTouch()
{
    if($('.ui-datagrid-data').length > 0){
        var cages = $("div[class*='cage']");
        for(var i = 0; i < cages.length; i++){
            var cage = cages[i];
            cage.addEventListener("touchstart", touchHandler, true);
            cage.addEventListener("touchmove", touchHandler, true);
            cage.addEventListener("touchend", touchHandler, true);
            cage.addEventListener("touchcancel", touchHandler, true);
        }
        var theCages = $( "li[class*='ui-selectlistbox-item']" );
        for(var j = 0; j < theCages.length; j++){
            var theCage = theCages[j];
            theCage.addEventListener("touchstart", touchHandler, true);
            theCage.addEventListener("touchmove", touchHandler, true);
            theCage.addEventListener("touchend", touchHandler, true);
            theCage.addEventListener("touchcancel", touchHandler, true);
        }
        var mice = $("table[class*='miceTable'] span[class*=mouseID]");
        for(var k = 0; k < mice.length; k++){
            var mouse = mice[k];
            mouse.addEventListener("touchstart", touchHandler, true);
            mouse.addEventListener("touchmove", touchHandler, true);
            mouse.addEventListener("touchend", touchHandler, true);
            mouse.addEventListener("touchcancel", touchHandler, true);
        }
        if($("img[id*='pupFemale']").length > 0){
            $("img[id*='pupFemale']")[0].addEventListener("touchstart", touchHandler, true);
            $("img[id*='pupFemale']")[0].addEventListener("touchmove", touchHandler, true);
            $("img[id*='pupFemale']")[0].addEventListener("touchend", touchHandler, true);
            $("img[id*='pupFemale']")[0].addEventListener("touchcancel", touchHandler, true);
        }
        if($("img[id*='pupMale']").length > 0){
            $("img[id*='pupMale']")[0].addEventListener("touchstart", touchHandler, true);
            $("img[id*='pupMale']")[0].addEventListener("touchmove", touchHandler, true);
            $("img[id*='pupMale']")[0].addEventListener("touchend", touchHandler, true);
            $("img[id*='pupMale']")[0].addEventListener("touchcancel", touchHandler, true);
        }
    }
}