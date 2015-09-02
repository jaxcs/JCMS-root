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
/** Creates the Kaplan Meier graph, for information on Kaplan Meier charts see:
 * http://en.wikipedia.org/wiki/Kaplan%E2%80%93Meier_estimator
 * 
 * Note that the 3 params are returned using the primefaces library RequestContext
 * API which is why there is the extra stuff (xhr, status) that I don't really need.
 * @param xhr - not utilized 
 * @param status - not utilized
 * @param args - an array containing the information from the server, in this 
 *               case a json string containing the Kaplan Meier chart information
 * 
 * @return none
 */
function buildKaplanMeier(xhr, status, args){
    //json string from server
    var json = args.jsonData;
    
    if(json === ""){
        
    }
    else{
        //margins and plot field specifics
        var margin = {top: 20, right: 20, bottom: 30, left: 50},
                        width = 960 - margin.left - margin.right,
                        height = 500 - margin.top - margin.bottom;

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

        //line function for kaplan meier plot
        var line = d3.svg.line()
                .x(function(d) { return x(d.xValue); })
                .y(function(d) { return y(d.yValue); })
                .interpolate("step-after");

        //add SVG element and create grouping element to contain chart components
        var svg = d3.select(".contentDiv").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .attr("id", "svg_vis")
        .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


        //parse json string into object
        data = JSON.parse(json);

        /**
        * Builds the kaplan meier chart.
        * @param data  A json object containing all the information needed to 
        *              create a kaplan meier chart.
        * @return      N/A
        */
        function buildChart(data){
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
                    .attr("class", survivalData.scheduleClass)
                    .style("stroke", d3.rgb("#" + survivalData.color))
                    .append("path")
                    .attr("d", line);
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

            var offset = 0, buffer = 5;

            var vg = legend.selectAll("g") 
                .data(data.children) 
                .enter()
                .append("g");
                
            //the color boxes to map lines to their use schedule
            vg.append("rect")
                .attr("width", 10)
                .attr("height", 10)
                .attr("x", function(d, i){return 15})
                .attr("y", function(d, i){return i*15 + 15})
                .attr("class", function(d, i){return d.scheduleClass})
                .style("fill", function(d, i){return d3.rgb("#" + d.color)} );
                
            //labels of color boxes
            vg.append("text")
                    //.text(d.protocolName + ' (' + d.total + ')')
                .attr("x", function(d, i){return 30})
                .attr("y", function(d, i){return i*15 + 24})
                .each(function(d, i) {
                    var   lineNumber = 0;
                    var   text = d3.select(this),
                          words = splitToArray(d.protocolName + ' (' + d.total + ')', (width/2)).reverse(),
                          word,
                          lineHeight = 1.1, // ems
                          x = text.attr("x"),
                          y = text.attr("y"),
                          dy = 0; //parseFloat(text.attr("dy")),

                    while (word = words.pop()) {
                        text.append("tspan")
                            .attr("x", x)
                            .attr("y", y)
                            .attr("dy", lineNumber++ * lineHeight + dy + "em")
                            .text(word);
                    }
                    d3.select(this.parentNode)
                        .attr("transform",
                             "translate(0," + (offset) + ")");                    
                    offset += this.parentNode.getBBox().height;
                });
                
            //calculate width of box according to width of group that contains it as well as the labels
            var legendWidth = d3.select("#legendGroup")[0][0].getBoundingClientRect().width + 30;
            var legendHeight = d3.select("#legendGroup")[0][0].getBoundingClientRect().height + 30;
            var legendPosition = width - legendWidth;
            //position the legend group, then set the size of the legend according to longest text string...
            legend.attr("transform", "translate(" + legendPosition + "," + 0 + ")");
            containerRect.attr("width", legendWidth)
                        .attr("height", legendHeight);        
        }
        
        /**
         * Convert a string to an array structure.  Each string size is identified by chrLength.
         */
        function splitToArray(text, chrLength) {
            var lines = new Array();
            var start = 0;
            
            while (text.length > chrLength) {
                lines.push(text.substr(start,chrLength));
                text = text.substr(chrLength, text.length - chrLength)
            }
            
            if (text.length <= chrLength) {
                lines.push(text);
            }
            
            return lines;
        }
        
        //build axes/kaplan meier line
        buildChart(data);

        //build legend
        buildLegend(data);
    }
}