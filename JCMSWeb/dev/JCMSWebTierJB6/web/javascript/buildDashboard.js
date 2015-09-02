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
function buildDashboard(xhr, status, args){   
    
    //strain viability stuff
    function buildStrainViability(json){
        var r = 100;
        var svgWidth = 111,
            svgHeight = 111;
        var root;

        var pack = d3.layout.pack()
            .size([r, r])
            .value(function(d){
                if(d.type == "genotype"){
                    return d.value;
                }
                else if(d.type == "strain"){
                    return d.totalMice;
                }
            });

        var svg = d3.select("#strainViabilityDiv").append("svg")
            .attr("width", svgWidth)
            .attr("height", svgHeight)
        .append("svg:g")
            .attr("class", "root")
            .attr("transform", "translate(" + (svgWidth - r) / 2 + "," + (svgHeight - r) / 2 + ")");


        root = JSON.parse(json);

        var nodes = pack.nodes(root);

        var node = svg.selectAll(".node")
                    .data(nodes)
                .enter().append("g")
                    .attr("opacity", function(d){return d.type == "strain" ? 1 : 1e-6})//function(d){d.children ? d3.select(d.children).attr("opacity", 1.0) : d3.select(d.parent.children).attr("opacity", 1)})
                    .attr("class", function(d){return d.type == "strain" ? "strain" : "genotype"})
                    .attr("strainKey", function(d){return d.strainKey})
                    .attr("cursor", "pointer")
                    .attr("transform", function(d){return "translate(" + d.x + "," + d.y + ")"})                    
                    .on("click",function(){window.location.href='reports/bubbleStrain.xhtml';});

        node.append("circle")
            .attr("fill", function(d) { 
                if(d.type == "holder"){
                    return "white";
                }
                else if(d.type == "genotype"){
                    if(d.genotype == "No Genotype"){
                        return "gray";
                    }
                    else{
                        return "#33CCFF";
                    }
                }
                else{
                    return d.color;
                }
            })
            .attr("id", function(d){ 
                if(d.type == "strain") {
                    return "strainName_" + d.strainKey;
                } 
                else if(d.type == "genotype") {
                    return "genotypeKey_" + d.genotypeKey;
                }
                else{
                    return "strainName_";
                }
            })
            .attr("r", function(d) {return d.r});     
            
        node.append("text")
            .text(function(d){
                return getLabels(d);
            })
            .attr("font-size", "2px")
            .attr("text-anchor", "middle");
            
        function getLabels(d){
            var fitCharNum;
            var circleWidth

            if(d.type == "strain"){
                //higher the relative size, the fewer characters you can fit in the circle
                //console.log("Strain Name: " + d.strainName);
                circleWidth = d3.select("#strainName_" + d.strainKey)[0][0].getBoundingClientRect().width;
                if(circleWidth < 6){
                    return "";
                }
                else if(d.strainName.length * 1.5 > circleWidth){
                    //circle not wide enough for the name, trim it.
                    //can fit ~ 1 char per 5.35 units (probably px) of width
                    fitCharNum = Math.floor(circleWidth/1.5) - 2;
                    return d.strainName.substring(0,fitCharNum) + "...";
                }
                else{
                    return d.strainName;
                }
            }
            else if(d.type == "genotype"){
                circleWidth = d3.select("#genotypeKey_" + d.genotypeKey)[0][0].getBoundingClientRect().width;
                if(circleWidth < 30){
                    return "";
                }
                else if(d.genotype.length * 5 > circleWidth){
                    //circle not wide enough for the name, trim it.
                    //can fit ~ 1 char per 5.35 units (probably px) of width
                    fitCharNum = Math.floor(circleWidth/1.5) - 2;
                    return d.genotype.substring(0,fitCharNum) + "...";
                }
                else{
                    return d.genotype;
                }
            }
        }
    }
    
    
    //cage histogram stuff...    
    function buildHistograms(cageJSON, mouseJSON){
        
        d3.selectAll("#cageHistogramSVG").remove();
        d3.selectAll("#mouseHistogramSVG").remove();
        
        var margin = {top: 3, right: 3, bottom: 4.5, left: 6},
        width = 165 - margin.left - margin.right,
        height = 90 - margin.top - margin.bottom,
        histogramWidth = width;
        
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

        var cageSVG = d3.select("#cageHistogramDiv").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .attr("id", "cageHistogramSVG")
                .on("click",function(){window.location.href='reports/cageHistogram.xhtml';})
            .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
                .attr("cursor", "pointer");

        var mouseSVG = d3.select("#mouseHistogramDiv").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .attr("id", "mouseHistogramSVG")
                .on("click",function(){window.location.href='reports/mouseHistogram.xhtml';})
            .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
                .attr("cursor", "pointer");


        //parse json string into object
        var cageData = JSON.parse(cageJSON);
        //parse json string into object
        var mouseData = JSON.parse(mouseJSON);

        //build cage histogram
        buildCageChart(cageData.strain, cageSVG);

        //build cage histogram
        buildCageChart(mouseData.strain, mouseSVG);

        function buildCageChart(data, svg){
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
                .attr("class", "y axis");

            
            
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
                    .attr("class", function(d) {return d.styleClass;});
        }
    }
    
    
    d3.selectAll('.loadingIcon').remove();
    //call the build strain
    var cageHistogramJSON = args.cageHistogramData;    
    //call the build strain
    var mouseHistogramJSON = args.mouseHistogramData;
    buildHistograms(cageHistogramJSON, mouseHistogramJSON);
    
    //call the build strain viability function
    var strainViabilityJSON = args.strainViabilityData;
    buildStrainViability(strainViabilityJSON);
}

function setLoading(){
    d3.selectAll('svg').remove();
    d3.selectAll('.svgContainer')
        .append('img')
        .attr('src','/jcms/images/bhi_ball.gif')
        .attr('class', 'loadingIcon');
}
    