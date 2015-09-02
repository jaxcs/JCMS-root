/*
 * Copyright (c) 2015 The Jackson Laboratory
 * 
 * This is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by  
 * the Free Software Foundation, either version 3 of the License, or  
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
var kineticStage;
var currentLevel;

/*
 * Loading...
 */
jQuery.ajaxSetup({
	beforeSend: function() {
		$('.loader').show();
	},
	complete: function(){
		$('.loader').hide();
	},
	success: function() {}
});

/**
 * 
 */
function requestLayoutDetails(levelKey){
    
    Kinetic.pixelRatios = 1;
    
    //used to determine whether touch is a 'longtouch'
    
    var url;
    if(getSecure()){
        url = 'https://' + getHost() + ':8443';
    }
    else{
        url = 'http://' + getHost() + ':8080';
    }
    //get strain values from dropdown...
    $.ajax({
        type: 'POST',
        url: url + '/jcms/vivariaLayout/getRackDetails/' + encodeURIComponent(getUser()) + "/" + encodeURIComponent(getEncrypedPW()) + "/" + encodeURIComponent(levelKey),
        success: function (json) {
        	
            var fromServer = JSON.parse(json);
            //check that server operation was successful
            if(fromServer.status == 'success'){
            	var data = fromServer.level;
	            if(kineticStage != undefined){
	                kineticStage.clear();
	            }
	
	            
	            /* 
	             * variables used to move cages around and return cages to original 
	             * position in case of error.
	             */
	            var originalX;
	            var originalY;
	            var originalRow;
	            var originalColumn;
	            var cols = parseInt(data.xmax);
	            var rows = parseInt(data.ymax);
	            
	            //font size in the mouse table
	            var mouseFontSize = 20;
	            //cage being viewed currently (showing mice in that cage)
	            var mouseInfoCage;
	
	            var canvasWidth = screen.width - 50;
	            var canvasHeight = screen.height - 50 - mouseFontSize * 5;
	
	            //10% of total space dedicated to padding...
	            var xPadding = canvasWidth/(10*(cols+1));
	            var yPadding = canvasHeight/(10*(rows+1));
	
	            var boxWidth = (canvasWidth - xPadding*(cols + 1))/cols;
	            var boxHeight = (canvasHeight - yPadding*(rows + 1))/rows;
	
	            kineticStage = new Kinetic.Stage({
	                container: 'container',
	                width: canvasWidth,
	                height: canvasHeight
	            });
	
	            //layers...
	           var slotLayer = new Kinetic.Layer({id:'slotLayer'});
	           var cageLayer = new Kinetic.Layer({id:'cageLayuer'});
	           var hiddenLayer = new Kinetic.Layer({id:'hiddenLayer'});
	           var miceLayer = new Kinetic.Layer({id:'miceLayer'});
	
	            //object that acts as highlight to show users where current cage is hovering
	            var highlightCage = new Kinetic.Rect({
	                x: col*xPadding + (col - 1)*boxWidth,
	                y: row*yPadding + (row - 1)*boxHeight,
	                width: boxWidth,
	                height: boxHeight,
	                fill: 'yellow',
	                stroke: 'black',
	                strokeWidth: 4
	            });
	            hiddenLayer.add(highlightCage);
	            highlightCage.hide();
	
	            //initialize menu, shown on hold
	            var menu = new Kinetic.Group({
	                id: 'menu'
	            });
	            var container = new Kinetic.Rect({
	                width: boxWidth*2,
	                height: boxHeight/2
	            });
	            var text = new Kinetic.Text({
	                align: 'center',
	                width: container.getWidth(),
	                text: 'Retire Cage and Exit Mice',
	                fontSize: 12,
	                height: 12,
	                fontFamily: 'Calibri'
	            });
	            menu.add(container);
	            menu.add(text);
	            hiddenLayer.add(menu);
	            menu.hide();
	            
	            //create slot grid
	            //rows
	            for(var row=1; row <= rows; row++){
	                //columns
	                for(var col=1; col <= cols; col++){
	                    var box = new Kinetic.Rect({
	                        x: col*xPadding + (col - 1)*boxWidth,
	                        y: row*yPadding + (row - 1)*boxHeight,
	                        width: boxWidth,
	                        height: boxHeight,
	                        fill: 'gray',
	                        stroke: 'black',
	                        strokeWidth: 4,
	                        name: 'slot',
	                        column: col,
	                        row: row
	                    });
	                    box.on('tap', slotTouch);
	                    slotLayer.add(box);
	                }
	            }
	
	            //add cages...
	            $.each(data.cages, function(i, cage){
	                createCage(cage);            
	            });
	            //add layers to the stage
	            kineticStage.add(miceLayer);
	            kineticStage.add(slotLayer);
	            kineticStage.add(hiddenLayer);
	            kineticStage.add(cageLayer);
	            
	            /*THE BELOW FUNCTIONS CORRESPOND TO DIFFERENT VIVARIUM EVENTS*/
	            /**
	             * Function that is called when a user touches an empty slot. This 
	             * implies the wish to add a cage to that empty slot.
	             * 
	             * @param evt - touch event on slot layer.
	             */
	            function slotTouch(evt){
	                if(detectCageIntersection(this, cageLayer) == null){
	                    var selectedSlot = this;
	                    $('#addCageWindow').css('visibility', 'visible');
	                    $('#addCageToLevel').unbind('click');
	                    //function to add cage.
	                    $("#addCageToLevel").on("click", function(){
	                        var newCage = new Object();
	                        newCage._level_key = currentLevel._level_key;
	                        newCage._room_key = currentLevel._room_key;
	                        newCage.x = selectedSlot.attrs.column;
	                        newCage.y = selectedSlot.attrs.row;
	                        newCage.newCage = $("#nextAvailable").is(":checked");
	                        newCage.containerID = $("#cageInput").val();
	                        if($("#cageInput").val() == '' && !$("#nextAvailable").is(":checked")){
	                        	navigator.notification.alert(
	                    		    'Please select either a valid cage ID or check the use next available ID box.',  // message
	                    		    genericCallback,	// callback
	                    		    'Add Cage',         // title
	                    		    'Ok'            	// buttonName
	                    		);
	                        }
	                        else{
		                        //store coordinates in case it needs to be returned.
		                        var slot_position = {x: selectedSlot.getX() + xPadding, y: selectedSlot.getY() + yPadding};
		                        var cageJSON = JSON.stringify(newCage);
		                        /*
		                        *two cases, either moving existing cage to row, or adding 
		                        *new (empty) cage
		                        */
		                        $.ajax({
		                            type: 'POST',
		                            url: url + '/jcms/vivariaLayout/addCageToLevel/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(cageJSON),
		                            success: function(data){
		                                var cageDetails = JSON.parse(data);	      
		                                if(cageDetails.status == 'success'){
			                                createCage(cageDetails);
			                                $('#addCageWindow').css('visibility', 'hidden');
			                                cageLayer.batchDraw();
			                            }
		                                else{
		                                	navigator.notification.alert(
		                            		    'Server error: ' + cageDetails.error,  // message
		                            		    genericCallback,	// callback
		                            		    'Add Cage',         // title
		                            		    'Ok'            	// buttonName
		                            		);
		                                }
		                            },
		                            error: function(xhr, status, error){
		                                console.log(error);
		                                cageLayer.batchDraw();
		                            }
		                        });
	                        }
	                    });
	                }
	            }
	            
	            /**
	             * Function that is called when a user starts a touch event, is 
	             * meant to store the original coordinates of the selected cage in
	             * the case where upon dropping a swap is required with another cage 
	             * OR the cage needs to be returned to it's original position (it 
	             * didn't get dropped in a viable slot).
	             * 
	             * @param evt the dragstart event
	             */
	            function cageStartMove(evt){
	                console.log('dragstart');
	                var touchedCage = this;
	                //store coordinates in case it needs to be returned.
	                var new_position = {x: this.getX() + xPadding, y: this.getY() + yPadding};
	                //var collision = slotLayer.getIntersection(new_position);
	                var collision = detectCageIntersection(touchedCage, slotLayer);
	                //record original position in case cage needs to be returned
	                if(collision != null){
	                    originalX = collision.getX();
	                    originalY = collision.getY();
	                    originalRow = collision.attrs.row;
	                    originalColumn = collision.attrs.column;
	                }
	                else{
	                    originalX = this.getX();
	                    originalY = this.getY();
	                }
	            }
	            
	            /**
	             * Function that is called as user is moving the cage around using 
	             * drag functionality. This function serves to 'highlight' the slot
	             * the cage would be dropped in if the user dropped the cage there.
	             * 
	             * @param evt the DragMove event
	             */
	            function cageMove(evt){     
	                //var new_position = {x: this.attrs.x + xPadding, y: this.attrs.y + yPadding};
	                var dragCage = this;
	                //var collision = this.getStage().getIntersection(new_position);
	                var collision = detectCageIntersection(dragCage, slotLayer);
	                if(collision != null){		    			  
	                    highlightCage.setX(collision.getX());
	                    highlightCage.setY(collision.getY());
	                    highlightCage.show();
	                    hiddenLayer.batchDraw();
	                }
	                else{
	                    highlightCage.hide();
	                    hiddenLayer.batchDraw();
	                }
	            }
	            
	            /** 
	             * Function to move a dropped cage into an empty slot or switch the 
	             * position of two cages if one cage is dropped on another.
	             * 
	             * On drop the following items are required:
	             * 1. The cage slot that you are dropping on KineticJS rectangle
	             * named collision in below function.
	             * 2. The cage that you dragging to a new location (KineticJS
	             * Group and also 'this' in the below method).
	             * 3. ***IF*** The cage is being being dropped on an occupied 
	             * slot, the cage that occupied that slot before drop is 
	             * required and is moved to the slot that was previously
	             * occupied by the dropped cage. This object is a KineticJS 
	             * Rectangle object and is found using the previousCagePosition
	             * variable.
	             * 
	             * @param evt - the dragend event triggered by user ending drag 
	             */
	            function cageDrop(evt){
	                highlightCage.hide();
	                hiddenLayer.batchDraw();
	                //collision is the kineticjs rectangle 'slot'
	                var collision = detectCageIntersection(this, slotLayer);//slotLayer.getIntersection(slotPosition);
	                /* 
	                 * need to make a json object representing the dropped cage and the cage it's dropped
	                 * on with new positions it will look like:
	                 * [{'_container_key' : ...,
	                 *   '_level_key' : ...,
	                 *   '_room_key' : ...,
	                 *   'x' : ..., 
	                 *   'y' : ...
	                 *   },
	                 *  {'_container_key' : ...,
	                 *   '_level_key' : ...,
	                 *   '_room_key' : ...,
	                 *   'x' : ..., 
	                 *   'y' : ...
	                 *   }
	                 * }]
	                 */
	                var droppedCageGroup = this;
	                var currentSlotOccupant = detectCageIntersection(droppedCageGroup, cageLayer);
	                var cages;
	                var droppedCage;
	                var cageJSON;
	                                
	                //if there's a collision, and it's with a cage or slot, drop dragged cage into that slot
	                /*Case one, you're swapping cages meaning you're moving two cages.*/
	                if(collision != null && currentSlotOccupant != null){   
	                    //get the cage that is being moved to the old slot of the dragged cage
	                    //cageLayer.getIntersection(collision.getPosition()).getParent();
	                    /*
	                     * I'm updating the view first so the action doesn't appear 
	                     * laggy to the user. If there is an error on the server 
	                     * side it will return to original position.
	                     */
	                    //update view...
	                    //adjust previous occupant to now be in slot where the dragged cage started from
	                    currentSlotOccupant.setX(originalX);
	                    currentSlotOccupant.setY(originalY);  
	                    droppedCageGroup.setX(collision.getX());
	                    droppedCageGroup.setY(collision.getY());                      
	                    cageLayer.batchDraw();
	                    
	                    //first we create the dropped cage object
	                    cages = [];
	                    droppedCage = new Object();
	                    droppedCage._level_key = currentLevel._level_key;
	                    droppedCage._room_key = currentLevel._room_key;
	                    droppedCage._container_key = droppedCageGroup.attrs.key;
	                    droppedCage.x = collision.attrs.column;
	                    droppedCage.y = collision.attrs.row;
	                    
	                    var switchedCage = new Object();                    
	                    switchedCage._level_key = currentLevel._level_key;
	                    switchedCage._room_key = currentLevel._room_key;
	                    switchedCage._container_key = currentSlotOccupant.attrs.key;
	                    switchedCage.x = originalColumn;
	                    switchedCage.y = originalRow;     
	                    
	                    cages[0] = droppedCage;
	                    cages[1] = switchedCage;
	                    
	                    cageJSON = JSON.stringify(cages);
	                    
	                    $.ajax({
	                        type: 'POST',
	                        url: url + '/jcms/vivariaLayout/updateCagePosition/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(cageJSON),
	                        success: function(data){
	                        	console.log(data);
	                            var moveCageFeedback = JSON.parse(data);
	                            //if it wasn't successful server side return it to original position
	                            if(moveCageFeedback.status != 'success'){
		                            droppedCageGroup.setX(originalX);
		                            droppedCageGroup.setY(originalY);
		                            cageLayer.batchDraw();

		                        	navigator.notification.alert(
		                    		    'Error: ' + moveCageFeedback.error,  // message
		                    		    genericCallback,	// callback
		                    		    'Move Cage',         // title
		                    		    'Ok'            	// buttonName
		                    		);
	                            }
	                        },
	                        error: function(xhr, status, error){
	                            console.log(error);
	                            droppedCageGroup.setX(originalX);
	                            droppedCageGroup.setY(originalY);
	                            cageLayer.batchDraw();
	                        }
	                    });
	                }
	                /*Case two, you're putting the cage in an empty slot, only need to update 1 cage*/
	                else if(collision != null){
	                    //first update the view
	                    droppedCageGroup = this;
	                    droppedCageGroup.setX(collision.getX());
	                    droppedCageGroup.setY(collision.getY());
	                    cageLayer.batchDraw();
	                    
	                    droppedCage = new Object();
	                    droppedCage._level_key = currentLevel._level_key;
	                    droppedCage._room_key = currentLevel._room_key;
	                    droppedCage._container_key = droppedCageGroup.attrs.key;
	                    droppedCage.x = collision.attrs.column;
	                    droppedCage.y = collision.attrs.row;
	                    
	                    cages = [];
	                    cages[0] = droppedCage;
	                    
	                    cageJSON = JSON.stringify(cages);
	                    
	                    $.ajax({
	                        type: 'POST',
	                        url: url + '/jcms/vivariaLayout/updateCagePosition/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(cageJSON),
	                        success: function(data){
	                        	var moveCageFeedback = JSON.parse(data);
	                            //if it wasn't successful server side return it to original position
	                            if(moveCageFeedback.status != 'success'){
		                            droppedCageGroup.setX(originalX);
		                            droppedCageGroup.setY(originalY);
		                            cageLayer.batchDraw();

		                        	navigator.notification.alert(
		                    		    'Error: ' + moveCageFeedback.error,  // message
		                    		    genericCallback,	// callback
		                    		    'Move Cage',         // title
		                    		    'Ok'            	// buttonName
		                    		);
	                            }
	                        },
	                        error: function(xhr, status, error){
	                            console.log(error);
	                            droppedCageGroup.setX(originalX);
	                            droppedCageGroup.setY(originalY);
	                            cageLayer.batchDraw();
	                        }
	                    });
	                }
	                /*Case three, if there is no collision return that cage to it's current slot*/
	                else{
	                    this.setX(originalX);
	                    this.setY(originalY);
	                    cageLayer.batchDraw();
	                }
	            }
	            
	            /**
	             * Function to populate the mouse table in the canvas, is called when
	             * user taps on any cage in the rack. In this cage 'this' is a 
	             * KineticJS Group object containing the cage details.
	             * 
	             * @param evt The touch event triggered
	             */
	            function cageTouch(evt){
	                
	                var url;
	                //the cage that was touched
	                var selectedCage = this;
	                //mouse info cage is the group that contains the information for the current cage
	                if(getSecure()){
	                    url = 'https://' + getHost() + ':8443';
	                }
	                else{
	                    url = 'http://' + getHost() + ':8080';
	                }                
	                
	                /*Possibility one, there are mice selected and you're moving them*/
	                if(miceLayer.find('.selectedMouse').length > 0){
	                    //array of KineticJS.Group objects 
	                    var miceToMove = miceLayer.find('.selectedMouse');
	                    var mice = [];
	                    $.each(miceToMove, function(i, mouse){
	                        var jsonObject = new Object();
	                        jsonObject._container_key = selectedCage.attrs.key;
	                        jsonObject._container_key_previous = mouse.attrs._container_key;
	                        jsonObject._mouse_key = mouse.attrs._mouse_key;
	                        mice[i] = jsonObject;
	                        mouse.remove();
	                    });
	                    miceLayer.batchDraw();
	                    var mouseJSON = JSON.stringify(mice);
	                    $.ajax({
	                        type: 'POST',
	                        url: url + '/jcms/vivariaLayout/moveMiceToCage/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(mouseJSON),
	                        success: function(feedback){
	                            /*
	                             *Feedback contains whether or not the database action
	                             *was successful as well as the new strains and sexes
	                             *for the old and new cage
	                             */
	                            var info = JSON.parse(feedback);
	                            if(info.status == 'success'){
	                                //update cages with new strains and sexes.
	                                mouseInfoCage.children[2].setText(info.oldCageStrain);
	                                selectedCage.children[2].setText(info.newCageStrain);
	                                mouseInfoCage.children[3].setText(info.oldCageSex);
	                                selectedCage.children[3].setText(info.newCageSex);
	                                cageLayer.draw();
	                                //change position of remaining mice to fit nicely in table
	                                $.each(miceLayer.children, function(i, mouse){
	                                    if(mouse.getID != 'labelGroup'){
	                                        mouse.setY(canvasHeight + i * mouseFontSize);
	                                    }
	                                });
	                                miceLayer.batchDraw();
	                            }
	                            else{
	                                //return mice to previous cage in view
	                                $.each(miceToMove, function(i, mouse){
	                                    miceLayer.add(mouse);
	                                });
	                                miceLayer.batchDraw();
	                                navigator.notification.alert(
		                    		    'Error: ' + info.error,  // message
		                    		    genericCallback,	// callback
		                    		    'Move Mice',         // title
		                    		    'Ok'            	// buttonName
		                    		);
	                            }
	                        },
	                        error: function(){
	                            //return mouse to previous spot
	                            $.each(miceToMove, function(i, mouse){
	                                miceLayer.add(mouse);
	                            });
	                            miceLayer.batchDraw();
	                            alert('Failure: Server error, please check your connection.');
	                        }
	                    });
	                }
	                /* possibility two, getting mouse info for the cage */
	                else{
	                    mouseInfoCage = this;
	                    var containerID = mouseInfoCage.getId();
	                    var previousViewCage = cageLayer.find('.viewedCage')[0];
	                    if(previousViewCage != undefined){                    
	                        previousViewCage.children[0].setFill('LightBlue');
	                        previousViewCage.setName('');
	                    }
	                    miceLayer.removeChildren();
	                    $.ajax({
	                        type: 'GET',
	                        url: url + '/jcms/vivariaLayout/getMiceInCage/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(containerID),
	                        success: function(json){
	                            selectedCage.children[0].setFill('green');
	                            selectedCage.setName('viewedCage');
                                var cageContentData = JSON.parse(json);
	                            if(cageContentData.status == 'success'){
	                                var miceData = cageContentData.results;
	                                //adjust stage height to fit mouse table...
	                                kineticStage.setHeight(screen.height - 50);
	                                $.each(miceData, function(i, mouse){
	                                    var mouseGroup = new Kinetic.Group({                                    
	                                        y: canvasHeight + (i+1)*mouseFontSize,
	                                        _mouse_key: mouse._mouse_key,
	                                        _container_key: mouse._container_key
	                                    });
	
	                                    //if first go ahead and build the header row
	                                    if(miceData[0] == mouse){
	                                        var labelGroup = new Kinetic.Group({                                    
	                                            y: canvasHeight,
	                                            id: "labelGroup"
	                                        });
	                                        //labels
	                                        //id...
	                                        var labelX = 0;
	                                        var mouseIDLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseIDLabelRect = new Kinetic.Rect({                                    
	                                            width: (screen.width - 50)/6,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseIDLabelText = new Kinetic.Text({
	                                            text: 'ID',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/6,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        });
	                                        mouseIDLabelGroup.add(mouseIDLabelRect);
	                                        mouseIDLabelGroup.add(mouseIDLabelText);
	
	                                        //birthdate...
	                                        labelX = labelX + (screen.width - 50)/6;
	                                        var mouseBDLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseBDLabelRect = new Kinetic.Rect({                                    
	                                            width: (screen.width - 50)/6,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseBDLabelText = new Kinetic.Text({
	                                            text: 'Birthdate',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/6,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        }); 
	                                        mouseBDLabelGroup.add(mouseBDLabelRect);
	                                        mouseBDLabelGroup.add(mouseBDLabelText);
	
	                                        //sex
	                                        labelX = labelX + (screen.width - 50)/6; 
	                                        var mouseSexLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseSexLabelRect = new Kinetic.Rect({         
	                                            width: (screen.width - 50)/12,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseSexLabelText = new Kinetic.Text({
	                                            text: 'Sex',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/12,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        });
	                                        mouseSexLabelGroup.add(mouseSexLabelRect);
	                                        mouseSexLabelGroup.add(mouseSexLabelText);
	
	                                        //strain
	                                        labelX = labelX + (screen.width - 50)/12; 
	                                        var mouseStrainLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseStrainLabelRect = new Kinetic.Rect({         
	                                            width: (screen.width - 50)/4,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseStrainLabelText = new Kinetic.Text({
	                                            text: 'Strain',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/4,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        });
	                                        mouseStrainLabelGroup.add(mouseStrainLabelRect);
	                                        mouseStrainLabelGroup.add(mouseStrainLabelText);
	
	                                        //strain
	                                        labelX = labelX + (screen.width - 50)/4; 
	                                        var mouseOwnerLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseOwnerLabelRect = new Kinetic.Rect({         
	                                            width: (screen.width - 50)/12,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseOwnerLabelText = new Kinetic.Text({
	                                            text: 'Owner',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/12,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        });
	                                        mouseOwnerLabelGroup.add(mouseOwnerLabelRect);
	                                        mouseOwnerLabelGroup.add(mouseOwnerLabelText);
	
	                                        //genotype
	                                        labelX = labelX + (screen.width - 50)/12; 
	                                        var mouseGenotypeLabelGroup = new Kinetic.Group({                                    
	                                            x: labelX
	                                        });
	                                        var mouseGenotypeLabelRect = new Kinetic.Rect({         
	                                            width: (screen.width - 50)/4,
	                                            height: mouseFontSize + 1,
	                                            stroke: 'black',
	                                            strokeWidth: 1
	                                        });
	                                        var mouseGenotypeLabelText = new Kinetic.Text({
	                                            text: 'Genotype',
	                                            fontSize: mouseFontSize,
	                                            height: mouseFontSize,
	                                            width: (screen.width - 50)/4,
	                                            align: 'center',
	                                            fontFamily: 'Calibri',
	                                            fill: 'black'
	                                        });   
	                                        mouseGenotypeLabelGroup.add(mouseGenotypeLabelRect);
	                                        mouseGenotypeLabelGroup.add(mouseGenotypeLabelText);
	                                        labelGroup.add(mouseIDLabelGroup);
	                                        labelGroup.add(mouseBDLabelGroup);
	                                        labelGroup.add(mouseSexLabelGroup);
	                                        labelGroup.add(mouseStrainLabelGroup);
	                                        labelGroup.add(mouseOwnerLabelGroup);
	                                        labelGroup.add(mouseGenotypeLabelGroup);
	                                        miceLayer.add(labelGroup);
	                                    }
	
	                                    //id...
	                                    var X = 0;
	                                    var mouseIDGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseIDRect = new Kinetic.Rect({                                    
	                                        width: (screen.width - 50)/6,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseIDText = new Kinetic.Text({
	                                        text: mouse.ID,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/6,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    });
	                                    mouseIDGroup.add(mouseIDRect);
	                                    mouseIDGroup.add(mouseIDText);
	
	                                    //birthdate...
	                                    X = X + (screen.width - 50)/6;
	                                    var mouseBDGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseBDRect = new Kinetic.Rect({                                    
	                                        width: (screen.width - 50)/6,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseBDText = new Kinetic.Text({
	                                        text: mouse.birthDate,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/6,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    }); 
	                                    mouseBDGroup.add(mouseBDRect);
	                                    mouseBDGroup.add(mouseBDText);
	
	                                    //sex
	                                    X = X + (screen.width - 50)/6; 
	                                    var mouseSexGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseSexRect = new Kinetic.Rect({         
	                                        width: (screen.width - 50)/12,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseSexText = new Kinetic.Text({
	                                        text: mouse.sex,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/12,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    });
	                                    mouseSexGroup.add(mouseSexRect);
	                                    mouseSexGroup.add(mouseSexText);
	
	                                    //strain
	                                    X = X + (screen.width - 50)/12; 
	                                    var mouseStrainGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseStrainRect = new Kinetic.Rect({         
	                                        width: (screen.width - 50)/4,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseStrainText = new Kinetic.Text({
	                                        text: mouse.strainName,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/4,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    });
	                                    mouseStrainGroup.add(mouseStrainRect);
	                                    mouseStrainGroup.add(mouseStrainText);
	
	                                    //strain
	                                    X = X + (screen.width - 50)/4; 
	                                    var mouseOwnerGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseOwnerRect = new Kinetic.Rect({         
	                                        width: (screen.width - 50)/12,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseOwnerText = new Kinetic.Text({
	                                        text: mouse.owner,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/12,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    });
	                                    mouseOwnerGroup.add(mouseOwnerRect);
	                                    mouseOwnerGroup.add(mouseOwnerText);
	
	                                    //genotype
	                                    X = X + (screen.width - 50)/12; 
	                                    var mouseGenotypeGroup = new Kinetic.Group({                                    
	                                        x: X
	                                    });
	                                    var mouseGenotypeRect = new Kinetic.Rect({         
	                                        width: (screen.width - 50)/4,
	                                        height: mouseFontSize + 1,
	                                        stroke: 'black',
	                                        strokeWidth: 1
	                                    });
	                                    var mouseGenotypeText = new Kinetic.Text({
	                                        text: mouse.genotype,
	                                        fontSize: mouseFontSize,
	                                        height: mouseFontSize,
	                                        width: (screen.width - 50)/4,
	                                        fontFamily: 'Calibri',
	                                        fill: 'black'
	                                    });   
	                                    mouseGenotypeGroup.add(mouseGenotypeRect);
	                                    mouseGenotypeGroup.add(mouseGenotypeText);    
	
	
	                                    mouseGroup.add(mouseIDGroup);
	                                    mouseGroup.add(mouseBDGroup);
	                                    mouseGroup.add(mouseSexGroup);
	                                    mouseGroup.add(mouseStrainGroup);
	                                    mouseGroup.add(mouseOwnerGroup);
	                                    mouseGroup.add(mouseGenotypeGroup);
	                                    mouseGroup.on('tap', highlightMouse);
	                                    miceLayer.add(mouseGroup);
	                                });
	                            }
	                            miceLayer.batchDraw();
	                            kineticStage.draw();
	                        },
	                        error: function(xhr, status, error){
	                            console.log(error);
	                        }
	                    });
	                    function highlightMouse(evt){
	                        if(this.getName() == 'selectedMouse'){
	                            this.setName('');
	                            $.each(this.getChildren(), function(i, group){
	                                group.children[0].setFill('white');
	                            });
	                        }
	                        else{
	                            this.setName('selectedMouse');
	                            $.each(this.getChildren(), function(i, group){
	                                group.children[0].setFill('green');
	                            });
	                        }
	                        miceLayer.batchDraw();
	                    }
	                }
	            }
	            
	            /**
	             * Function that is called on double tap of a cage in the cage
	             * layer. It is meant to display a menu of options for the user to 
	             * take an action on the cage (retire, add mating etc.).
	             * 
	             * @param evt - the double tap event
	             */
	            function showMenu(evt){
	                var selectedCage = this;
	                var menuPosition = kineticStage.getPointerPosition();
	                var menuWidth = 300;
	                var menuHeight = 50;
	                //adjust menu position in case it would overflow out of canvas
	                if(menuPosition.x + menuWidth > canvasWidth){
	                    menuPosition.x = canvasWidth - menuWidth;
	                }
	                if(menuPosition.y + menuHeight > canvasHeight){
	                    menuPosition.y = canvasHeight - menuHeight;
	                }
	                
	                var menuFontSize = 20;
	                
	                var menuGroup = new Kinetic.Group({
	                    x: menuPosition.x,
	                    y: menuPosition.y,
	                    name: 'menu'                    
	                });
	                var menuRect = new Kinetic.Rect({                    
	                    height: menuHeight,
	                    width: menuWidth,
	                    fill: 'white',
	                    stroke: 'black',
	                    strokeWidth: 2
	                });
	                
	                var exitAndRetire = new Kinetic.Text({
	                    x: menuRect.getX(),
	                    y: menuRect.getStrokeWidth(),
	                    text: 'Exit Mice and Retire Cage',
	                    fontSize: menuFontSize,
	                    height: menuFontSize,
	                    fontFamily: 'Calibri',
	                    width: menuRect.getWidth(),
	                    align: 'left',    
	                    fill: 'black'
	                });                
	                
	                var cancel = new Kinetic.Text({
	                    x: menuRect.getX(),
	                    y: exitAndRetire.getY() + 25,
	                    text: 'Cancel',
	                    fontSize: menuFontSize,
	                    height: menuFontSize,
	                    fontFamily: 'Calibri',
	                    width: menuRect.getWidth(),
	                    align: 'left',    
	                    fill: 'black'
	                });
	                
	                cancel.on('tap', function(){
	                    cageLayer.hitGraphEnabled(true);
	                    slotLayer.hitGraphEnabled(true);
	                    miceLayer.hitGraphEnabled(true);
	                    kineticStage.draw();
	                    menuGroup.remove();
	                    hiddenLayer.batchDraw();
	                    hiddenLayer.moveDown();
	                });                
	                
	                exitAndRetire.on('tap', function(){
	                    //reset the canvas to work properly...
	                    cageLayer.hitGraphEnabled(true);
	                    slotLayer.hitGraphEnabled(true);
	                    miceLayer.hitGraphEnabled(true);
	                    kineticStage.draw();
	                    menuGroup.remove();
	                    hiddenLayer.batchDraw();
	                    hiddenLayer.moveDown();
	                    
	                    //show the remove cage window...
	                    $('#removeCageWindow').css('visibility', 'visible');
	                    
	                    $('#retireCage').unbind('click');
	                    //function to retire cage.
	                    $("#retireCage").on("click", function(){
	                        var cage = new Object();
	                        cage._level_key = currentLevel._level_key;
	                        cage._room_key = currentLevel._room_key;
	                        cage._container_key = selectedCage.attrs.key;
	                        cage.lifeStatus = $('#exitStatusesDropdown').val();
	                        cage._containerStatus_key = $('#cageStatusesDropdown').val();                    
	                        cage.cod = $('#codDropdown').val();
	                        if(cage.cod == ''
	                    	 || cage._containerStatus_key == ''
	                         || cage.lifeStatus == ''){
	                        	navigator.notification.alert(
	                    		    'Please select a life status, a cage status, and a cause of death.',  // message
	                    		    genericCallback,	// callback
	                    		    'Add Cage',         // title
	                    		    'Ok'            	// buttonName
	                    		);
	                        }
	                        else{
		                        //store coordinates in case it needs to be returned.
		                        var cageJSON = JSON.stringify(cage);
		                        /*
		                        *two cases, either moving existing cage to row, or adding 
		                        *new (empty) cage
		                        */
		                        $.ajax({
		                            type: 'POST',
		                            url: url + '/jcms/vivariaLayout/retireCage/' + encodeURIComponent(getUser()) + '/' + encodeURIComponent(getEncrypedPW()) + '/' + encodeURIComponent(cageJSON),
		                            success: function(data){
		                                var cageDetails = JSON.parse(data);
		                                if(cageDetails.status == 'success'){
			                                createCage(cageDetails);
			                                $('#addCageWindow').css('visibility', 'hidden');
			                                cageLayer.batchDraw();
		                                }
		                                else{
		                                	navigator.notification.alert(
		                            		    'Server error: ' + cageDetails.error,  // message
		                            		    genericCallback,	// callback
		                            		    'Retire Cages',     // title
		                            		    'Ok'            	// buttonName
		                            		);
		                                }
		                            },
		                            error: function(xhr, status, error){
		                                console.log(error);
		                                cageLayer.batchDraw();
		                            }
		                        });
		                        
		                        //show the remove cage window...
		                        $('#removeCageWindow').css('visibility', 'hidden');
		                        selectedCage.remove();
		                        cageLayer.draw();
	                        }
	                    });
	                });
	                
	                menuGroup.add(menuRect);
	                menuGroup.add(exitAndRetire);
	                menuGroup.add(cancel);
	                hiddenLayer.add(menuGroup);
	                hiddenLayer.moveUp();
	                hiddenLayer.batchDraw();
	                
	                cageLayer.hitGraphEnabled(false);
	                slotLayer.hitGraphEnabled(false);
	                miceLayer.hitGraphEnabled(false);
	            }
	            
	            /**
	             * Function to create a cage object to be displayed in a slot in the 
	             * vivaria layout view.
	             * 
	             * JSON looks as follows:
	             * {
	             *   "x":x,
	             *   "y":y,
	             *   "_container_key":key,
	             *   "containerID" : ID,
	             *   "cageStrain" : theStrain,
	             *   "cageSex" : cageSex
	             * }
	             * 
	             * @param cage The details that contains 
	             */
	            function createCage(cage){
	                //create cage...
	                var col = cage.x;
	                var row = cage.y;
	
	                //cage group is a grouping object containing box and labels
	                var cageGroup = new Kinetic.Group({
	                    x: col*xPadding + (col - 1)*boxWidth,
	                    y: row*yPadding + (row - 1)*boxHeight,
	                    draggable: true,
	                    name: 'cage',
	                    key: cage._container_key,
	                    id: cage.containerID
	                });
	
	                //actual box...
	                var cageBox = new Kinetic.Rect({
	                    width: boxWidth,
	                    height: boxHeight,
	                    fill: 'LightBlue',
	                    stroke: 'black',
	                    strokeWidth: 4
	                });
	
	                var fontsize = 12;
	
	                //containerID text
	                var containerID = new Kinetic.Text({
	                    x: cageBox.getX(),
	                    y: cageBox.getStrokeWidth(),
	                    text: cage.containerID,
	                    fontSize: fontsize,
	                    height: fontsize,
	                    fontFamily: 'Calibri',
	                    width: cageBox.getWidth(),
	                    align: 'center',    
	                    fill: 'white'
	                });
	                //cage strain label text...
	                var cageStrain = new Kinetic.Text({
	                    x: cageBox.getX(),
	                    y: cageBox.getHeight()/2 - fontsize,
	                    text: cage.cageStrain,
	                    fontSize: fontsize,
	                    height: fontsize,
	                    fontFamily: 'Calibri',
	                    width: cageBox.getWidth(),
	                    align: 'center',    
	                    fill: 'white'
	                });
	                //cage sex label text
	                var cageSex = new Kinetic.Text({
	                    x: cageBox.getX(),
	                    y: cageBox.getHeight()/2,
	                    text: cage.cageSex,
	                    fontSize: fontsize,
	                    height: fontsize,
	                    fontFamily: 'Calibri',
	                    width: cageBox.getWidth(),
	                    align: 'center',    
	                    fill: 'white'
	                });
	
	                cageGroup.add(cageBox);
	                cageGroup.add(containerID);
	                cageGroup.add(cageStrain);
	                cageGroup.add(cageSex);
	
	                cageLayer.add(cageGroup);
	                //startdrag
	                cageGroup.on('touchstart', cageStartMove);
	                cageGroup.on('dragmove', cageMove);
	                cageGroup.on('dbltap', showMenu);
	                cageGroup.on('dragend', cageDrop);
	                cageGroup.on('tap click', cageTouch);     
	            }
	            
	            /*
	             * Method to get intersection between an object (obj) and the children
	             * of a layer (layer)
	             */
	            function detectCageIntersection(obj, layer){
	                var draggedX = obj.getX();
	                var draggedY = obj.getY();
	                var cageOfInterest = null;
	                $.each(layer.getChildren(), function(i, cage){
	                    if(((draggedX - cage.getX()) >= 0 && ((draggedX - (cage.getX() + boxWidth)) <= 0)) && 
	                       ((draggedY - cage.getY()) >= 0 && (draggedY - (cage.getY() + boxHeight)) <= 0)){
	                        if(obj != cage){
	                        cageOfInterest = cage;
	                        }
	                    } 
	                });
	                return cageOfInterest;
	            }
	        }
            else{
            	navigator.notification.alert(
        		    'Server error: ' + data.error,  // message
        		    genericCallback,	// callback
        		    'Get Vivarium',         // title
        		    'Ok'            	// buttonName
        		);
            }
        },
        error: function(xhr, status, error){
            alert(error);
        }
    });
}

/**
 * A callback is required for phonegap native notification. Here it is, just a 
 * dummy holding method.
 */
function genericCallback(){}

/*
 * Build level tree
 */
$(document).ready(function(){
    var url;
    if(getSecure()){
        url = 'https://' + getHost() + ':8443';
    }
    else{
        url = 'http://' + getHost() + ':8080';
    }
    //get strain values from dropdown...
    $.ajax({
        type: 'POST',
        url: url + '/jcms/vivariaLayout/generateLevelTreeData/' + encodeURIComponent(getUser()) + "/" + encodeURIComponent(getEncrypedPW()),
        success: function (json) {
            var treeData = JSON.parse(json);
            if(treeData.status == 'success'){
	            $('#treeview').treeview({data: treeData.contents});
	            $('#treeview').on('nodeSelected', function(event, node) {
	                if(node.type == 'level'){
	                    currentLevel = node;
	                    requestLayoutDetails(node._level_key);
	                }
	            });
            }
            else{
            	navigator.notification.alert(
        		    'Server error: ' + data.error,  // message
        		    genericCallback,	// callback
        		    'Load Tree Data',         // title
        		    'Ok'            	// buttonName
        		);
            }
        },
        error: function(xhr, status, error){
            alert(error);
        }
    });
    //get cage status values for dropdown...
    $.ajax({
        type: 'GET',
        url: url + '/jcms/dropdown/generateContainerStatusDropdownData/' + getUser() + "/" + getEncrypedPW(),
        success: function (json) {
            var data = JSON.parse(json);
            $.each(data.containerStatuses, function(index, item){
                $("#cageStatusesDropdown").append("<option value='" + item._containerStatus_key + "'>" + item.containerStatus + "</option>")
            });
        },
        error: function(xhr, status, error){
                alert(error);
        }
    });
    //get cage status values from dropdown...
    $.ajax({
        type: 'GET',
        url: url + '/jcms/dropdown/generateExitStatusDropdownData/' + getUser() + "/" + getEncrypedPW(),
        success: function (json) {
            var data = JSON.parse(json);
            $.each(data.lifeStatuses, function(index, item){
                $("#exitStatusesDropdown").append("<option value='" + item.lifeStatus + "'>" + item.lifeStatus + "</option>")
            });
        },
        error: function(xhr, status, error){
                alert(error);
        }
    });
    //get cause of death values from dropdown...
    $.ajax({
        type: 'GET',
        url: url + '/jcms/dropdown/generateCauseOfDeathDropdownData/' + getUser() + "/" + getEncrypedPW(),
        success: function (json) {
            var data = JSON.parse(json);
            $.each(data.cods, function(index, item){
                $("#codDropdown").append("<option value='" + item.cod + "'>" + item.cod + "</option>")
            });
        },
        error: function(xhr, status, error){
            alert(error);
        }
    });
});
