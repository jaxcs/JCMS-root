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
function displayTree(xhr, status, args){
    
    var jsonData = args.jsonData;
    var gene = args.gene;
    var allele1 = args.allele1;
    var allele2 = args.allele2;
    
    var hetCount = args.hetCount;
    var hom1Count = args.hom1Count;
    var hom2Count = args.hom2Count;
    var ko1Count = args.ko1Count;
    var ko2Count = args.ko2Count;
    var nullCount = args.nullCount;

    
    var legendData = createLegendData();
        
    div = d3.select("body").append("div")
        .attr("class", "tooltip")
        .style("opacity", 1e-6);

    var m = [20, 120, 20, 120],
        w = 1280 - m[1] - m[3],
        h = 800 - m[0] - m[2],
        i = 0,
        root;

    var tree = d3.layout.tree()
        .size([h, w]);

    var diagonal = d3.svg.diagonal()
        .projection(function(d) {return [d.y, d.x];});

    var vis = d3.select(".contentDiv").append("svg:svg")
        .attr("width", w + m[1] + m[3])
        .attr("height", h + m[0] + m[2])
    .append("svg:g")
        .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

        
    var json = JSON.parse(jsonData);
//    d3.json("mousePedigree.json", function(json) {
    root = json;
    root.x0 = h / 2;
    root.y0 = 0;

    function toggleAll(d) {
        if (d.children) {
            d.children.forEach(toggleAll);
            toggle(d);
        }
    }

    // Initialize the display to show a few nodes.
    if(root.children !== undefined){
        root.children.forEach(toggleAll);
    }
    update(root);


    function update(source) {
        var duration = d3.event && d3.event.altKey ? 5000 : 500;

        // Compute the new tree layout.
        var nodes = tree.nodes(root).reverse();

        // Normalize for fixed-depth.
        nodes.forEach(function(d) {d.y = d.depth * 180;});

        // Update the nodes
        var node = vis.selectAll("g.node")
            .data(nodes, function(d) {return d.id || (d.id = ++i);});

        // Enter any new nodes at the parent's previous position.
        var nodeEnter = node.enter().append("svg:g")
            .attr("class", "node")
            .attr("sex", function(d){
                return d.sex;
            })
            .attr("transform", function(d) {return "translate(" + source.y0 + "," + source.x0 + ")";})
            .on("click", function(d) {toggle(d);update(d);});
        
        d3.selectAll("[sex=F]").append("svg:circle")
            .attr("r", 1e-6)
            //.style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; })
            .style("fill", function(d) {return determineColor(d)})
            .on("mouseover", showTooltip)
            .on("mouseout", hideTooltip);
            
            
           
        d3.selectAll("[sex=M]").append("svg:rect")
            .attr("width", 1e-6)
            .attr("height", 1e-6)
            .attr("x", "-.35em")
            .attr("y", "-.35em")
            .style("fill", function(d) {return determineColor(d)})
            .on("mouseover", showTooltip)
            .on("mouseout", hideTooltip);

        nodeEnter.append("svg:text")
            .attr("x", function(d) {return d.children || d._children ? -10 : 10;})
            .attr("dy", ".35em")
            .attr("text-anchor", function(d) {return d.children || d._children ? "end" : "start";})
            .text(function(d) {return d.ID;})
            .style("fill-opacity", 1e-6)
            .on("mouseover", showTooltip)
            .on("mouseout", hideTooltip);

        // Transition nodes to their new position.
        var nodeUpdate = node.transition()
            .duration(duration)
            .attr("transform", function(d) {return "translate(" + d.y + "," + d.x + ")";});

        nodeUpdate.select("circle")
            .attr("r", 4.5)
            .style("fill", function(d) {return determineColor(d)});
            
        nodeUpdate.select("rect")
            .attr("width", 10)
            .attr("height", 10)
            .style("fill", function(d) {return determineColor(d)});
            

        nodeUpdate.select("text")
            .style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", function(d) {return "translate(" + source.y + "," + source.x + ")";})
            .remove();

        nodeExit.select("circle")
            .attr("r", 1e-6);
            
        /*nodeExit.select("rect")
            .attr("width", 1e-6)
            .attr("height", 1e-6);
            */

        nodeExit.select("text")
            .style("fill-opacity", 1e-6);

        // Update the links�
        var link = vis.selectAll("path.link")
            .data(tree.links(nodes), function(d) {return d.target.id;});

        // Enter any new links at the parent's previous position.
        link.enter().insert("svg:path", "g")
            .attr("class", "link")
            .attr("d", function(d) {
                var o = {x: source.x0, y: source.y0};
                return diagonal({source: o, target: o});
            })
            .transition()
            .duration(duration)
            .attr("d", diagonal);

        // Transition links to their new position.
        link.transition()
            .duration(duration)
            .attr("d", diagonal);

        // Transition exiting nodes to the parent's new position.
        link.exit().transition()
            .duration(duration)
            .attr("d", function(d) {
                var o = {x: source.x, y: source.y};
                return diagonal({source: o, target: o});
            })
            .remove();

        // Stash the old positions for transition.
        nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });
    }

    // Toggle children.
    function toggle(d) {
        if (d.children) {
            d._children = d.children;
            d.children = null;
        } else {
            d.children = d._children;
            d._children = null;
        }
    }
    
    function showTooltip(d){
        if(d.strainName != ''){
            div.html("ID: <b>" + d.ID + "</b><br />" 
                      + "Strain: <b>" + d.strainName + "</b><br />" 
                      + "Sex: <b>" + d.sex + "</b><br />" 
                      + "Genotype: <b>" + d.genotype + "</b><br />" 
                      + "Diet: <b>" + d.diet + "</b><br />" 
                      + "Mating ID: <b>" + d.matingID + "</b><br />" 
                      + "Sire ID: <b>" + d.sireID + "</b><br />" 
                      + "Sire Genotype: <b>" + d.sireGenotype + "</b><br />" 
                      + "Dam 1 ID: <b>" + d.dam1ID + "</b><br />" 
                      + "Dam 1 Genotype: <b>" + d.dam1Genotype + "</b><br />" 
                      + "Dam 2 ID: <b>" + d.dam2ID + "</b><br />" 
                      + "Dam 2 Genotype: <b>" + d.dam2Genotype + "</b><br />" 
                      + "Owner: <b>" + d.owner + "</b><br />" 
                      + "Litter ID: <b>" + d.litterID + "</b>")
                .transition()
                .duration(500)
                .style("opacity", 1)
                .style("left", (d3.event.pageX - 34) + "px")
                .style("top", (d3.event.pageY + 15) + "px");
        }
    }
    
    function hideTooltip(){
        div.transition()
            .duration(500)
            .style("opacity", 1e-6);
    }
    
    
    function determineColor(d){
        if(allele1 !== "" && allele2 !== ""){
            return determineColor2Allele(d);
        }
        else if(allele1 !== ""){
            return determineColor1Allele(d);
        }
        else{
            return "gray";
        }
    }
    
    //will return true if Genotype contains Gene and allele asked for
    function determineColor1Allele(d){
        var genotypeString = gene + " " + allele1;
        if(d.genotype.indexOf(genotypeString) !== -1){
            return "blue";
        }
        else{
            return "gray";
        }
    }
    
    
    function determineColor2Allele(d){
        var heterozygousStr1 = gene + " " + allele1 + " " + allele2;
        var heterozygousStr2 = gene + " " + allele2 + " " + allele1;
        var homozygousStr1 = gene + " " + allele1 + " " + allele1;
        var homozygousStr2 = gene + " " + allele2 + " " + allele2;
        var KOString1 = gene + " " + allele1;
        var KOString2 = gene + " " + allele2;
        
        //heterozygous (Gene A Alleles B and C --> A B C on display)
        if(d.genotype.indexOf(heterozygousStr1) !== -1 || d.genotype.indexOf(heterozygousStr2) !== -1){
            return "yellow";
        }
        //homozygous allele 1 (Gene A Alleles B and B --> A B B)
        else if(d.genotype.indexOf(homozygousStr1) !== -1){
            return "blue";
        }
        //homozygous allele 2 (Gene A Alleles C and C --> A C C)
        else if(d.genotype.indexOf(homozygousStr2) !== -1){
            return "red";
        }
        //knockout 1 (Gene A Allele B)
        else if(d.genotype.indexOf(KOString1) !== -1){
            return "orange";
        }
        //knockout 1 (Gene A Allele C)
        else if(d.genotype.indexOf(KOString2) !== -1){
            return "purple";
        }
        //no genotype
        else{
            return "gray";
        }
    }
    
    function createLegendData(){
        var heterozygousStr1 = gene + " " + allele1 + " " + allele2;
        var homozygousStr1 = gene + " " + allele1 + " " + allele1;
        var homozygousStr2 = gene + " " + allele2 + " " + allele2;
        var KOString1 = gene + " " + allele1;
        var KOString2 = gene + " " + allele2;
        var totalMice;
        if(allele1 !== "" && allele2 !== ""){
            totalMice = parseInt(hetCount) + parseInt(hom1Count) + parseInt(hom2Count) + parseInt(ko1Count) + parseInt(ko2Count) + parseInt(nullCount);
            return [{"label" : heterozygousStr1 + " (" + Math.round(100*hetCount/totalMice) + "%)", color : "yellow", "x" : 15, "y" : 10}, 
                {"label" : homozygousStr1 + " (" + Math.round(100*hom1Count/totalMice) + "%)", color : "blue", "x" : 15, "y" : 25}, 
                {"label" : homozygousStr2 + " (" + Math.round(100*hom2Count/totalMice) + "%)", color : "red", "x" : 15, "y" : 40}, 
                {"label" : KOString1 + " (" + Math.round(100*ko1Count/totalMice) + "%)", color : "orange", "x" : 15, "y" : 55}, 
                {"label" : KOString2 + " (" + Math.round(100*ko2Count/totalMice) + "%)", color : "purple", "x" : 15, "y" : 70}, 
                {"label" : "No Genotype" + " (" + Math.round(100*nullCount/totalMice) + "%)", color : "gray", "x" : 15, "y" : 85}];        }
        else if(allele1 !== ""){
            totalMice = parseInt(hetCount) + parseInt(nullCount);
            return [{"label" : KOString1  + " (" + Math.round(100*hetCount/totalMice) + "%)", color : "blue", "x" : 15, "y" : 10}, 
                {"label" : "No Genotype" + " (" + Math.round(100*nullCount/totalMice) + "%)", color : "gray", "x" : 15, "y" : 25}];
        }
        else{
            return [];
        }
    }
    
    function createLegend(){
        if(legendData.length > 0){
            var legendWidth;
            var legendHeight;
            if(legendData.length == 2){
                legendWidth = 140;
                legendHeight = 50;
            }
            else{
                legendWidth = 140;
                legendHeight = 110;
            }
            var legend = d3.select("svg").append("g")
                .attr("transform", "translate(" + 10 + "," + 10 + ")");
            
            legend.append("rect")
                .attr("width", legendWidth)
                .attr("height", legendHeight)
                .attr("id", "legend")
                .style("stroke", "steelblue")
                .style("stroke-width", 1.5)
                .style("fill", "white");
            
            var values = legend.selectAll(".colorKeys")
                .data(legendData)
                .enter();
                
                
            values.append("rect")
                .style("fill", function(d){return d.color})
                .attr("width", 10)
                .attr("height", 10)
                .attr("x", function(d){return d.x})
                .attr("y", function(d){return d.y})
                .style("stroke", "steelblue")
                .style("stroke-width", 1.5);
                
            values.append("text")
                .text(function(d) {return d.label;})
                .attr("x", function(d){return d.x + 15})
                .attr("y", function(d){return d.y + 9});                
        }
    }

    createLegend();
}