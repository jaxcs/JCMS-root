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
function buildHistogram(json, yAxisLabel){
    
    var margin = {top: 20, right: 20, bottom: 30, left: 40},
        width = 1110 - margin.left - margin.right,
        height = 600 - margin.top - margin.bottom,
        histogramWidth = width - 150;

    var classes = ["stack1", "stack2", "stack3", 
        "stack4", "stack5", "stack6", "stack7"];

    var x = d3.scale.ordinal();

    var y = d3.scale.linear()
            .rangeRound([height,0]);

    var parseDate = d3.time.format("%m-%d-%Y").parse;

    var classMap;

    var classIndex;


    var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom")
            .tickFormat(d3.time.format("%m-%d"));

    var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");

    var svg;
    
    //parse json string into object
    var data = JSON.parse(json);

    //build axes/keplan meier line
    buildCageChart(data.strain);

    //build legend
    buildLegend(data);
	
    function buildCageChart(data){
        
        d3.selectAll("svg").remove();
        
        svg = d3.select(".contentDiv").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
        .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        
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
        })
        
        var dateMin = d3.extent(data, function(d) {return d.d3Date;})[0];
        var dateMax = d3.extent(data, function(d) {return d.d3Date;})[1];
        
        x.domain(d3.time.days(dateMin, d3.time.day.offset(dateMax,1))) 
            .rangeRoundBands([0, histogramWidth], .1);
        y.domain([0, ymax]);
        
        xAxis.tickValues(x.domain().filter(function(d, i) 
            { 
                return !(i % 4); 
            })
        )

        //x axis
        svg.append("g")
            .attr("transform", "translate(0," + height + ")")
            .attr("class", "x axis")
            .call(xAxis);


        //y axis
        svg.append("g")
            .call(yAxis)
            .attr("class", "y axis")
        .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")
            .style("text-anchor", "end")
            .text(yAxisLabel);
        
        var groupByOwnerTextPosition = histogramWidth/2 - 50;
        var groupByStrainTextPosition = groupByOwnerTextPosition + 100;
        
        svg.append("text")
           .text("Group by Owner")
           .attr("id", "groupByOwner")
           .attr("class", "inactiveGrouping")
           .attr("transform", "translate(" + groupByOwnerTextPosition + ", -10)")
           .on("click", transitionToOwner);
           
           
        svg.append("text")
           .text("Group by Strain")
           .attr("id", "groupByStrain")
           .attr("class", "activeGrouping")
           .attr("transform", "translate(" + groupByStrainTextPosition + ", -10)")
           .on("click", transitionToStrain);

        var date = svg.selectAll(".date")
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
                .on("mouseover", function(d){d3.select("text[name='" + d.group + "']").classed("focused", true)})
                .on("mouseout", function(d){d3.select("text[name='" + d.group + "']").classed("focused", false)});
    }

    function buildLegend(){        
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

        var legendWidth = d3.select("#legendGroup")[0][0].getBoundingClientRect().width + 15;
        var legendHeight = d3.select("#legendGroup")[0][0].getBoundingClientRect().height + 15;
        var legendPosition = width - legendWidth;
        //position the legend group, then set the size of the legend according to longest text string...
        legend.attr("transform", "translate(" + legendPosition + "," + 0 + ")");
        containerRect.attr("width", legendWidth)
                     .attr("height", legendHeight); 
                     
        
    }
    
    function transitionToStrain(){
        buildCageChart(data.strain);
        buildLegend();
        d3.selectAll(".inactiveGrouping")
          .attr("class", "");
        d3.selectAll(".activeGrouping")
          .attr("class", "");
        d3.selectAll("#groupByStrain")
          .attr("class", "activeGrouping");
        d3.selectAll("#groupByOwner")
          .attr("class", "inactiveGrouping");        
    }
    
    
    function transitionToOwner(){
        buildCageChart(data.owner);      
        buildLegend();  
        d3.selectAll(".inactiveGrouping")
          .attr("class", "");
        d3.selectAll(".activeGrouping")
          .attr("class", "");
        d3.selectAll("#groupByOwner")
          .attr("class", "activeGrouping");
        d3.selectAll("#groupByStrain")
          .attr("class", "inactiveGrouping");   
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

function buildMouseHistogram(xhr, status, args){    
    //json string from server
    var json = args.mouseHistogramData;
    buildHistogram(json, "Total Mice");
}

function buildCageHistogram(xhr, status, args){
    //json string from server
    var json = args.cageHistogramData;
    buildHistogram(json, "Total Cages");
}