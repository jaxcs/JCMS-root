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
function displayBubbles(xhr, status, args){
    var json = args.strainViabilityData;
    var r = 720;
    var svgWidth = 1280,
        svgHeight = 800;
    //var maxRadius = args.maxMice;
    var x = d3.scale.linear().range([0, r]),
        y = d3.scale.linear().range([0, r]);
    var root;
    var zoomedNode;
    var zooming = false;
    
    //create tooltip
    var div = d3.select("body").append("div")
        .attr("class", "tooltip")
        .style("opacity", 1e-6);
        
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

    var svg = d3.select(".contentDiv").append("svg")
        .attr("width", svgWidth)
        .attr("height", svgHeight)
    .append("svg:g")
        .attr("class", "root")
        .attr("transform", /*"translate(50, 50)"*/"translate(" + (svgWidth - r) / 2 + "," + (svgHeight - r) / 2 + ")");
        
    if (json != "") {
        zoomedNode = root = JSON.parse(json);
    } else {
        return ;
    }
    
//    d3.json("test.json", function(data){
    var nodes = pack.nodes(root);

    var node = svg.selectAll(".node")
                .data(nodes)
            .enter().append("g")
                .attr("opacity", function(d){return d.type == "strain" ? 1 : 1e-6})//function(d){d.children ? d3.select(d.children).attr("opacity", 1.0) : d3.select(d.parent.children).attr("opacity", 1)})
                .attr("class", function(d){return d.type == "strain" ? "strain" : "genotype"})
                .attr("strainKey", function(d){return d.strainKey})
                .attr("transform", function(d){return "translate(" + d.x + "," + d.y + ")"});//"translate(" + (w - r) / 2 + "," + (h - r) / 2 + ")";});
                    
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
        .attr("r", function(d) {return d.r})
        .on("mouseover", showTooltip)
        .on("mouseout", hideTooltip)
        .on("click",  function(d) { return zoom(zoomedNode == d ? root : d); });
        
    var boundRect = d3.select("#strainName_")[0][0].getBoundingClientRect();
     
     /*
    var height = boundRect.height + 100;
    var width = boundRect.right + boundRect.left;
    
        
    d3.select("svg")
        .attr("width", width)
        .attr("height", height);
        */
    
        
    node.append("text")
        .text(function(d){
            return getLabels(d);
        })
        .attr("text-anchor", "middle")
        .on("mouseover", showTooltip)
        .on("click",  function(d) { return zoom(zoomedNode == d ? root : d); });
        
    function getLabels(d){
        var fitCharNum;
        var circleWidth
        
        if(d.type == "strain"){
            //higher the relative size, the fewer characters you can fit in the circle
            //console.log("Strain Name: " + d.strainName);
            circleWidth = d3.select("#strainName_" + d.strainKey)[0][0].getBoundingClientRect().width;
            if(circleWidth < 30){
                return "";
            }
            else if(d.strainName.length * 5 > circleWidth){
                //circle not wide enough for the name, trim it.
                //can fit ~ 1 char per 5.35 units (probably px) of width
                fitCharNum = Math.floor(circleWidth/5.35) - 2;
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
                fitCharNum = Math.floor(circleWidth/5.35) - 2;
                return d.genotype.substring(0,fitCharNum) + "...";
            }
            else{
                return d.genotype;
            }
        }
    }
    
    function zoom(d, i) {
        zooming = true;
        var k = r / d.r / 2;
        x.domain([d.x - d.r, d.x + d.r]);
        y.domain([d.y - d.r, d.y + d.r]);

        var t = d3.select("g").selectAll("g")
            .attr("transform", function(d) { return "translate(" + x(d.x) + "," + y(d.y) + ")"; });
        console.log(x(d.x) + " " + y(d.x));

        t.selectAll("circle")
            .attr("r", function(d) { return k * d.r; });

        t.selectAll("text")
            .attr("x", function(d) { return k * d.dx / 2; })
            .attr("y", function(d) { return k * d.dy / 2; })
            .style("opacity", function(d) { return k * d.r > 20 ? 1 : 0; })
            .text(function(d){return getLabels(d)});
        //TO DO: RECALCULATE THE LABEL AFTER ZOOM!!

        zoomedNode = d;
        d3.event.stopPropagation();
        zooming = false;
    }
    
    //mouseover events
    function showTooltip(d){
        if(d.type == 'strain'){
            div.html("Strain: <b>" + d.strainName + "</b><br />" 
                      + "Total Mice: <b>" + d.totalMice + "</b><br />" 
                      + "Green Status Females: <b>" + d.greenFemales + "</b><br />" 
                      + "Yellow Status Females: <b>" + d.yellowFemales + "</b><br />" 
                      + "Red Status Females: <b>" + d.redFemales + "</b><br />" 
                      + "Green Status Males: <b>" + d.greenMales + "</b><br />" 
                      + "Yellow Status Males: <b>" + d.yellowMales + "</b><br />" 
                      + "Red Status Males: <b>" + d.redMales + "</b><br />" 
                      + "Total Matings: <b>" + d.matings + "</b><br />" 
                      + "Total Cages: <b>" + d.cages + "</b>")
                .transition()
                .duration(500)
                .style("opacity", 1)
                .style("left", (d3.event.pageX - 34) + "px")
                .style("top", (d3.event.pageY + 15) + "px");
        }
        else if(d.type == 'genotype'){
            var strainKey = d.strainKey;
            var strainOfInterest = d3.selectAll(".strain")
                .filter(function(d){return d.strainKey == strainKey});
            var strainData = strainOfInterest[0][0].__data__;
            div.html("Strain: <b>" + strainData.strainName + "</b><br />" 
                      + "Genotype: <b>" + d.genotype + "</b><br />" 
                      + "# this Genotype: <b>" + d.genotypeCount + "</b><br />" 
                      + "Total Mice: <b>" + strainData.totalMice + "</b><br />" 
                      + "Green Status Females: <b>" + strainData.greenFemales + "</b><br />" 
                      + "Yellow Status Females: <b>" + strainData.yellowFemales + "</b><br />" 
                      + "Red Status Females: <b>" + strainData.redFemales + "</b><br />" 
                      + "Green Status Males: <b>" + strainData.greenMales + "</b><br />" 
                      + "Yellow Status Males: <b>" + strainData.yellowMales + "</b><br />" 
                      + "Red Status Males: <b>" + strainData.redMales + "</b><br />" 
                      + "Total Matings: <b>" + strainData.matings + "</b><br />" 
                      + "Total Cages: <b>" + strainData.cages + "</b>")
                .transition()
                .duration(500)
                .style("opacity", 1)
                .style("left", (d3.event.pageX - 34) + "px")
                .style("top", (d3.event.pageY + 15) + "px");
        }
        showGenotypes(d);
    }
    
    function hideTooltip(d){
        div.transition()
            .duration(500)
            .style("opacity", 1e-6);
        hideGenotypes(d);
    }
    
    function showGenotypes(d){
        var genotypeCircles = d3.selectAll(".genotype");
        var theStrain = d.strainKey;
        if(d.type == "strain"){
            genotypeCircles.filter(function(d){ return d.strainKey == theStrain})
                .transition()
                    .duration(500)
                    .attr("opacity", 1);
        }
        else if(d.type == "genotype"){            
            genotypeCircles.filter(function(d){ return d.strainKey == theStrain})
                .transition()
                    .duration(500)
                    .attr("opacity", 1);
        }
        //mouseover "holder" circle --> a mouse out of strain bubble that is entirely genotyped
        else if(d.type == "holder"){
            d3.selectAll(".genotype")
                .transition()
                    .duration(500)
                    .attr("opacity", 1e-6);
        }
    }
    
    function hideGenotypes(d){
        if(d.type == "strain"){  
            d3.selectAll(".genotype")
                .transition()
                    .duration(500)
                    .attr("opacity", 1e-6);
        }
    }    
    
    d3.select("#loadingIcon").remove();
}