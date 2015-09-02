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
    applyLevelJQuery();
});

function applyLevelJQuery(){
    $( "span[id*='levelText']" ).draggable({
        appendTo: "body",
        helper: "clone"
    })
    $( "span[class*='ui-treenode-label']" ).droppable({
        hoverClass: "ui-state-hover",
        accept: "span[id*='levelText']",
        drop: function( event, ui ) {
            $( "input[name*=levelDragKey]" )[0].value = $( ui.draggable ).find("input[name*=hiddenKey]")[0].value;
            $( "input[name*=dropKey]" )[0].value = $( this ).find("input[name*=hiddenKey]")[0].value;
            $( "input[name*=dropClass]" )[0].value = $( this ).find("input[name*=hiddenClass]")[0].value;
            dropNode();
        }
    })
}