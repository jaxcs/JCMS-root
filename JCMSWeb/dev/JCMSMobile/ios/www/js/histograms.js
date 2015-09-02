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
function buildHistograms(){
	var json = "";
	
	var url;
	if(getSecure()){
		url = 'https://' + getHost() + ':8443';
	}
	else{
		url = 'http://' + getHost() + ':8080';
	}
	$.ajax({
		type: 'GET',
		url: url + '/jcms/histogram/generateMouseData/' + getUser() + '/' + getEncrypedPW(),
		success: function (json) {
			buildHistogram(json, "mouseHistogram", "Total Mice");
		}
	});
	
	$.ajax({
		type: 'GET',
		url: url + '/jcms/histogram/generateCageData/' + getUser()+ '/' + getEncrypedPW(),
		success: function (json) {
			buildHistogram(json, "cageHistogram", "Total Cages");
		}
	});
	
	function buildHistogram(json, id, yAxisLabel){
		var margin = {top: 20, right: 20, bottom: 30, left: 40},
	        width = $(window).width() - margin.left - margin.right,
	        height = $(window).height() - margin.top - margin.bottom,
	        histogramWidth = width - 150;
	
	    var classes = ["stack1", "stack2", "stack3", 
	        "stack4", "stack5", "stack6", "stack7"];
	
	    var x = d3.scale.ordinal();
	
	    var xAxis = d3.svg.axis()
	            .scale(x)
	            .orient("bottom")
	            .tickFormat(d3.time.format("%m-%d"));
	
	    var y;
	
	    var parseDate = d3.time.format("%m-%d-%Y").parse;
	
	    var classMap;
	
	    var classIndex;
	
	    var yAxis;
	
	    var svg;
	    
	    //parse json string into object
	    var data = JSON.parse(json);
	
	    //build axes/keplan meier line
	    buildCageChart(data.strain);
	
	    //build legend
	    buildLegend(data);
	    
	    buildGroupByBox();
	    
	    function buildCageChart(data){
            
	        width = $(window).width() - margin.left - margin.right;
	        height = $(window).height() - margin.top - margin.bottom;

	        svg = d3.select("#" + id + "Div").append("svg")
	            .attr("width", width + margin.left + margin.right)
	            .attr("height", height + margin.top + margin.bottom)
	            .attr("id", id);
	        svgGroup = svg.append("g")
	            .attr("transform", "translate(" + (margin.left + 40) + "," + (margin.top - 40) + ")");
	        
	        histogramWidth = width - 50;
	        
	        y = d3.scale.linear().rangeRound([height,0]);
	        
	        yAxis = d3.svg.axis().scale(y).orient("left");
	        
	        classMap = {};
	        classIndex = 0;
	        var ymax = 0;
	        
	        data.forEach(function(d){
	            d.d3Date = parseDate(d.date);
	            var y0 = 0;
	            d.children.forEach(function(d2, i){
	                d2.y0 = y0;
	                d2.y1 = d2.volume + d2.y0;
	                y0 = d2.y1;
	                if(d2.y1 > ymax){
	                    ymax = d2.y1;
	                }
	                if(d2.group in classMap) {
	                    d2.styleClass = classMap[d2.group];                    
	                }
	                else {
	                    classMap[d2.group] = classes[classIndex++];
	                    d2.styleClass = classMap[d2.group];
	                    if(classIndex >= 7){
	                        classIndex = 0;
	                    }
	                }
	            })
	        });
	        
	        var dateMin = d3.extent(data, function(d) {return d.d3Date;})[0];
	        var dateMax = d3.extent(data, function(d) {return d.d3Date;})[1];
	        
	        x.domain(d3.time.days(dateMin, d3.time.day.offset(dateMax,1))) 
	            .rangeRoundBands([0, histogramWidth], .1);
	        y.domain([0, ymax]);
	        
	        xAxis.tickValues(x.domain().filter(function(d, i) 
	            { 
	                return !(i % 4); 
	            })
	        );
	        
	        //x axis
	        svgGroup.append("g")
	            .attr("transform", "translate(0," + height + ")")
	            .attr("class", "x axis")
	            .call(xAxis);
	        
	        //y axis
	        svgGroup.append("g")
	            .call(yAxis)
	            .attr("class", "y axis");
	        
	        var groupByOwnerTextPosition = histogramWidth/2 - 75;
	        var groupByStrainTextPosition = groupByOwnerTextPosition + 125;

//	        svg.append("text")
//	           .text("Group by Owner")
//	           .attr("id", "groupByOwner" + id)
//	           .attr("class", "inactiveGrouping")
//	           .attr("transform", "translate(" + groupByOwnerTextPosition + ", -5)")
//	           .on("click", transitionToOwner);
//	           
//	        svg.append("text")
//	           .text("Group by Strain")
//	           .attr("id", "groupByStrain" + id)
//	           .attr("class", "activeGrouping")
//	           .attr("transform", "translate(" + groupByStrainTextPosition + ", -5)")
//	           .on("click", transitionToStrain);
	        
	        var date = svgGroup.selectAll(".date")
	                .data(data)
	            .enter().append("g")
	                .attr("class", "g")
	                .attr("transform", function(d) { 
	                    return "translate(" + x(d.d3Date) + ",0)"; 
	                });
	
	        date.selectAll("rect")
	                .data(function(d) {return d.children;})
	             .enter().append("rect")
	                .attr("width", x.rangeBand())
	                .attr("y", function(d) { 
	                    return y(d.y1); 
	                })
	                .attr("height", function(d) { 
	                    return y(d.y0) - y(d.y1); 
	                })
	                .attr("name", function(d){return d.group})
	                .attr("class", function(d) {return d.styleClass;})
	                .on("mouseover", function(d){d3.selectAll("text[name='" + d.group + "']").classed("focused", true)})
	                .on("mouseout", function(d){d3.selectAll("text[name='" + d.group + "']").classed("focused", false)});

	        // y axis label
	        svg.append("text")
	            .attr("transform", "rotate(-90)")
	            .attr("y", (margin.left / 2))
	            .attr("x", -1 * (height / 2))
	            .attr("dy", ".71em")
	            .style("text-anchor", "middle")
	            .text(yAxisLabel);
	        // x axis label
	        svg.append("text")      
	        	.attr("x", (width / 2) + margin.left )
	        	.attr("y", height + margin.bottom)
	        	.style("text-anchor", "middle")
	        	.text("Day");
	    }

		
	    function buildGroupByBox(){        
	        var groupByBox = svg.append("g")
	            .attr("id", "groupBy" + id);
	
	        /* container rect is the container rectangle that contains the 
	         * group by owner and group by strain links 
	         * 
	         **/
	        var containerRect = groupByBox.append("rect")
	            .attr("id", "groupBy" + id)
	            .style("stroke", "steelblue")
	            .style("stroke-width", 1.5)
	            .style("fill", "white");
	                        
	        groupByBox.append("text")
                .text("Group by Owner")
                .attr("x", ((width / 2) / 2) + 10)
                .attr("y", 20)
	            .attr("id", "groupByOwner" + id)
	            .attr("class", "inactiveGrouping")
	            .on("click", transitionToOwner);
	
            groupByBox.append("text")
                .text("Group by Strain")
                .attr("x", ((width / 2) / 2) + (margin.left * 3) + margin.right)
                .attr("y", 20)
  	            .attr("id", "groupByStrain" + id)
	            .attr("class", "activeGrouping")
	            .on("click", transitionToStrain);
                
	        groupByBox.attr("transform", "translate(" + (width / 2) - margin.left + "," + 18 + ")");
	        containerRect.attr("width", 245)
                .attr("height", 25)
                .attr("x", ((width / 2) / 2) )
                .attr("y", 5); 
	    }
	    
	    function buildLegend(){        
	        var legend = svg.append("g")
	            .attr("id", "legendGroup" + id);
	
	        /*container rect is the container rectangle that contains all the color 
	         * boxes as well as the labels for those boxes to map them to the keplan 
	         * meier curves */
	        var containerRect = legend.append("rect")
	            .attr("id", "legend" + id)
	            .style("stroke", "steelblue")
	            .style("stroke-width", 1.5)
	            .style("fill", "white");
	                        
	        Object.keys(classMap).forEach(function(d, i){            
	            legend.append("rect")
	                .attr("width", 10)
	                .attr("height", 10)
	                .attr("x", function(){return 15})
	                .attr("y", function(){return i*15 + 15})
	                .attr("class", function(){return classMap[d]});
	                
	            //max number of chars in label should be 16 + ... in the middle
	            legend.append("text")
	                .text(function() {return buildLegendLabel(d);})
	                .attr("name", d)
	                .attr("x", function(){return 30})
	                .attr("y", function(){return i*15 + 24})
	                .on("mouseover",function(){
	                    d3.selectAll("rect[name='" + d + "']").classed("focused", true)
	                })
	                .on("mouseout",function(){
	                    d3.selectAll("rect[name='" + d + "']").classed("focused", false)
	                });
	        })
	
	        var legendWidth = d3.select("#legendGroup" + id)[0][0].getBoundingClientRect().width + 15;
	        var legendHeight = d3.select("#legendGroup" + id)[0][0].getBoundingClientRect().height + 15;
	        var legendPosition = width - legendWidth;
	        //position the legend group, then set the size of the legend according to longest text string...
	        legend.attr("transform", "translate(" + legendPosition + "," + 30 + ")");
	        containerRect.attr("width", legendWidth)
	                     .attr("height", legendHeight); 
	    }
	    
	    function transitionToStrain(){
//	    	alert("transition to strain");
	    	d3.selectAll("#" + id).remove();
	        buildCageChart(data.strain);
	        buildLegend();
	        buildGroupByBox();
	        d3.selectAll("#groupByOwner" + id)
	          .attr("class", "");
	        d3.selectAll("#groupByStrain" + id)
	          .attr("class", "");
	        d3.selectAll("#groupByStrain" + id)
	          .attr("class", "activeGrouping");
	        d3.selectAll("#groupByOwner" + id)
	          .attr("class", "inactiveGrouping");        
	    }
	    
	    
	    function transitionToOwner(){
//	    	alert("transition to owner");
	    	d3.selectAll("#" + id).remove();
	        buildCageChart(data.owner);      
	        buildLegend();  
	        buildGroupByBox();
	        d3.selectAll("#groupByStrain" + id)
	          .attr("class", "");
	        d3.selectAll("#groupByOwner" + id)
	          .attr("class", "");
	        d3.selectAll("#groupByOwner" + id)
	          .attr("class", "activeGrouping");
	        d3.selectAll("#groupByStrain" + id)
	          .attr("class", "inactiveGrouping");   
	    }
	    
	    //rebuild chart with for better sizing on orientation change... 
	    $(window).on("orientationchange", function(){
	    	d3.selectAll("#" + id).remove();
	    	//build axes/keplan meier line
		    buildCageChart(data.strain);
		
		    //build legend
		    buildLegend(data);
	    });
	}
    
    
    function buildLegendLabel(name){
        var length = name.length;
        if(length > 20){
            return name.substring(0, 8) + "..." + name.substring(length -8, length);
        }
        else{
            return name;
        }
    }
}