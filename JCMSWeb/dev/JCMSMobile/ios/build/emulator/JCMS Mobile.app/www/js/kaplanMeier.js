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

var data;

function createKaplanMeier(){
	var url;
	if(getSecure()){
		url = 'https://' + getHost() + ':8443';
	}
	else{
		url = 'http://' + getHost() + ':8080';
	}
	$.ajax({
		type: 'POST',
		data: $("#kaplanMeierForm").serialize(),
		url: url + '/jcms/kaplanMeier/generateKaplanMeierData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
			//first line...
			if(data == undefined){
				//parse json string into object
	        	data = JSON.parse(json);
			}
			else{
				d3.selectAll("svg").remove();
				data.children.push(JSON.parse(json).children[0]);
			}
			buildVisualization(data);
		},
		error: function(xhr, status, error){
			alert("Could not connect to server.");
		}
	});
}

/** Creates the Kaplan Meier graph, for information on Kaplan Meier charts see:
 * http://en.wikipedia.org/wiki/Kaplan%E2%80%93Meier_estimator
 * 
 * @param json - a json string containing the Kaplan Meier chart information
 * 
 * @return none
 */
function buildVisualization(data){
   
	//margins and plot field specifics
    var margin = {top: 20, right: 20, bottom: 30, left: 50},
                    width, height;
    
	//add SVG element and create grouping element to contain chart components
    var svg = d3.select("#contentDiv").append("svg")
        .attr("width", "100%")
        .attr("height", "55%")
        .attr("id", "svg_vis")
    .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    
    width = parseInt(d3.select("svg").style("width")) - 45;
    height = parseInt(d3.select("svg").style("height")) - 50;
    
    //x axis
    var x = d3.scale.linear()
            .range([0, width]);

    //y axis
    var y = d3.scale.linear()
            .range([height, 0]);

    //x and y axis scales
    var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom");
    var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");
    
    /**
    * Builds the kaplan meier chart.
    * @param data  A json object containing all the information needed to 
    *              create a kaplan meier chart.
    * @return      N/A
    */
    function buildChart(data){
        //line function for kaplan meier plot
        var line = d3.svg.line()
                .x(function(d) { return x(d.xValue); })
                .y(function(d) { return y(d.yValue); })
                .interpolate("step-after");
        /* set x and y domain to be [0, MAX(maxDays)] from the json data. Max days
        * will vary from use schedule to use schedule and obviously you need the 
        * use schedule that has the highest max days to be the x-axis max with the 
        * same going for the total number of mice. 
        * 
        * For my porpoise the y axis will always be from 0 to 100 since it is percent
        * based, but might as well not pigeonhole the component into only working
        * with percent based y axis...*/
        var xmax = d3.extent(data.children, function(d) { return d.maxDays; })[1];
        var ymax = d3.extent(data.children, function(d) { return d.yMax; })[1];
        x.domain([0, xmax]);
        y.domain([0, ymax]);

        var lineNumber = 1;
        
        /*Create Survival Curves*/
        //every child of root data corresponds to a different survival curve
        data.children.forEach(function(survivalData){
            //the child data contains all the points for this particular line. 
            var dataPoints = survivalData.children;

            //create x-axis
            svg.append("g")
                    .attr("class", "x axis")
                    .attr("transform", "translate(0," + height + ")")
                    .call(xAxis)
                .append("text")
                    .attr("x", width - 10)
                    .attr("dy", "-.25em")
                    .style("text-anchor", "end")
                    .text(data.xLabel);

            //create y-axis
            svg.append("g")
                    .attr("class", "y axis")
                    .call(yAxis)
            .append("text")
                    .attr("transform", "rotate(-90)")
                    .attr("y", 6)
                    .attr("dy", ".71em")
                    .style("text-anchor", "end")
                    .text(data.yLabel);

            //create survival line
            svg.append("g")
                .datum(dataPoints)
                .attr("class", "line" + lineNumber)
                .append("path")
                .attr("d", line);

			if(lineNumber < 7){
				lineNumber = lineNumber + 1;
			}
			else{
				lineNumber = 1;
			}
        });
    }

    /**
    * Builds the legend of the kaplan meier chart with a box describing line color
    * for each keplan meier curve.
    * @param data  A json object containing all the information needed to 
    *              create a kaplan meier chart.
    * @return      N/A
    */
    function buildLegend(data){
        var legend = svg.append("g")
            .attr("id", "legendGroup");

        /*container rect is the container rectangle that contains all the color 
        * boxes as well as the labels for those boxes to map them to the keplan 
        * meier curves */
        var containerRect = legend.append("rect")
            .attr("id", "legend")
            .style("stroke", "steelblue")
            .style("stroke-width", 1.5)
            .style("fill", "white");

        var values = legend.selectAll(".colorKeys")
                .data(data.children)
                .enter();

        //the color boxes to map lines to their use schedule
        values.append("rect")
            .attr("width", 10)
            .attr("height", 10)
            .attr("x", function(d, i){return 15})
            .attr("y", function(d, i){return i*15 + 15})
            .attr("class", function(d, i){return "line" + (i + 1)});

        //labels of color boxes
        values.append("text")
            .text(function(d) {return d.protocolName + ' (' + d.total + ')';})
            .attr("x", function(d, i){return 30})
            .attr("y", function(d, i){return i*15 + 24});    

        //calculate width of box according to width of group that contains it as well as the labels
        var legendWidth = d3.select("#legendGroup")[0][0].getBoundingClientRect().width + 15;
        var legendHeight = d3.select("#legendGroup")[0][0].getBoundingClientRect().height + 15;
        var legendPosition = width - margin.right - legendWidth;
        //position the legend group, then set the size of the legend according to longest text string...
        legend.attr("transform", "translate(" + legendPosition + "," + 0 + ")");
        containerRect.attr("width", legendWidth)
                    .attr("height", legendHeight);        
    }
    
    
    //build axes/kaplan meier line
    buildChart(data);

    //build legend
    buildLegend(data);
    

    //rebuild chart with for better sizing on orientation change... 
    $(window).on("orientationchange", function(){
    	d3.selectAll("svg").remove();
    	//rebuild with new scales...
		buildVisualization(data);
    });
}


/*
 * Populates the dropdowns
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
		type: 'GET',
		url: url + '/jcms/dropdown/generateStrainDropdownData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
		    var data = JSON.parse(json);
		    $.each(data.strains, function(index, item){
		    	$("#strainDropdown").append("<option value='" + item.strainName + "'>" + item.strainName + "</option>")
		    });
		},
		error: function(xhr, status, error){
			alert(error);
		}
	});
	//get use schedules values from dropdown...
	$.ajax({
		type: 'GET',
		url: url + '/jcms/dropdown/generateUseScheduleDropdownData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
		    var data = JSON.parse(json);
		    $.each(data.useSchedules, function(index, item){
		    	$("#useScheduleDropdown").append("<option value='" + item.useScheduleTermName + "'>" + item.useScheduleTermName + "</option>")
		    });
		},
		error: function(xhr, status, error){
			alert(error);
		}
	});
	//get workgroup values from dropdown...
	$.ajax({
		type: 'GET',
		url: url + '/jcms/dropdown/generateWorkgroupDropdownData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
		    var data = JSON.parse(json);
		    $.each(data.workgroups, function(index, item){
		    	$("#workgroupDropdown").append("<option value='" + item.WorkgroupName + "'>" + item.WorkgroupName + "</option>")
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
	//get room values from dropdown...
	$.ajax({
		type: 'GET',
		url: url + '/jcms/dropdown/generateRoomDropdownData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
		    var data = JSON.parse(json);
		    $.each(data.rooms, function(index, item){
		    	$("#roomDropdown").append("<option value='" + item._room_key + "'>" + item.roomName + "</option>")
		    });
		},
		error: function(xhr, status, error){
			alert(error);
		}
	});
	//get life status values from dropdown...
	$.ajax({
		type: 'GET',
		url: url + '/jcms/dropdown/generateExitStatusDropdownData/' + getUser() + "/" + getEncrypedPW(),
		success: function (json) {
		    var data = JSON.parse(json);
		    $.each(data.exitStatuses, function(index, item){
		    	$("#lifeStatusDropdown").append("<option value='" + item.lifeStatus + "'>" + item.lifeStatus + "</option>")
		    });
		},
		error: function(xhr, status, error){
			alert(error);
		}
	});
});
