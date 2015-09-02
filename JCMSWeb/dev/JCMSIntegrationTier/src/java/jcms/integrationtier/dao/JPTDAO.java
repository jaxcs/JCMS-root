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

package jcms.integrationtier.dao;

import java.lang.Math;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import jcms.integrationtier.dto.JPTDTO;
/**
 *
 * @author mkamato
 */
public class JPTDAO extends MySQLDAO{
    
    
    ArrayList<String> wgs;    
    private String rootQuery = "SELECT TRIM(Mouse.ID) AS mouseID, TRIM(Strain.strainName) AS mouseStrain, TRIM(Litter.litterID) AS mouseLitterID, "
            + "ABS(Mating.matingID) AS mouseMatingID, TRIM(Mouse.origin) AS mouseOrigin, TRIM(Mouse.newTag) AS mouseNewTag, "
            + "DATE(Mouse.birthDate) AS mouseBirthdate, DATE(Mouse.exitDate) AS mouseExitDate, TRIM(Mouse.cod) AS mouseCOD, "
            + "TRIM(Mouse.codNotes) AS mouseCODNotes, TRIM(Mouse.generation) AS mouseGeneration, TRIM(Mouse.sex) AS mouseSex, "
            + "TRIM(Mouse.lifeStatus) AS mouseLifeStatus, TRIM(Mouse.breedingStatus) AS mouseBreedingStatus, "
            + "TRIM(Mouse.coatColor) AS mouseCoatColor, TRIM(Mouse.diet) AS mouseDiet, TRIM(Mouse.owner) AS mouseOwner, "
            + "TRIM(dam1.ID) AS dam1ID, TRIM(dam2.ID) AS dam2ID, TRIM(sire.ID) AS sireID, "
            + "TRIM(Mouse.`comment`) AS mouseComment, ABS(Mouse._litter_key) AS mouseLitterKey, "
            + "CONCAT(COALESCE(Gene.labSymbol, ''), ' ', COALESCE(Genotype.allele1, ''), ' ', COALESCE(Genotype.allele2, '')) AS mouseGenotype, "
            + "CONCAT(COALESCE(sireGene.labSymbol, ''), ' ', COALESCE(sireGenotype.allele1, ''), ' ', COALESCE(sireGenotype.allele2, '')) AS sireGenotype, "
            + "CONCAT(COALESCE(dam1Gene.labSymbol, ''), ' ', COALESCE(dam1Genotype.allele1, ''), ' ', COALESCE(dam1Genotype.allele2, '')) AS dam1Genotype, "
            + "CONCAT(COALESCE(dam2Gene.labSymbol, ''), ' ', COALESCE(dam2Genotype.allele1, ''), ' ', COALESCE(dam2Genotype.allele2, '')) AS dam2Genotype "
            + "FROM Mouse "
            + "LEFT JOIN Litter "
            + "ON Mouse._litter_key = Litter._litter_key "
            + "LEFT JOIN Mating "
            + "ON Litter._mating_key = Mating._mating_key "
            + "LEFT JOIN Mouse AS dam1 "
            + "ON Mating._dam1_key = dam1._mouse_key "
            + "LEFT JOIN Mouse AS dam2 "
            + "ON Mating._dam2_key = dam2._mouse_key "
            + "LEFT JOIN Mouse AS sire "
            + "ON Mating._sire_key = sire._mouse_key "
            + "LEFT JOIN Strain "
            + "ON Mouse._strain_key = Strain._strain_key "
            + "LEFT JOIN Genotype "
            + "ON Genotype._mouse_key = Mouse._mouse_key "
            + "LEFT JOIN Gene "
            + "ON Genotype._gene_key = Gene._gene_key "
            + "LEFT JOIN Genotype AS dam1Genotype "
            + "ON dam1Genotype._mouse_key = dam1._mouse_key "
            + "LEFT JOIN Gene AS dam1Gene "
            + "ON dam1Gene._gene_key = dam1Genotype._gene_key "
            + "LEFT JOIN Genotype AS dam2Genotype "
            + "ON dam2Genotype._mouse_key = dam2._mouse_key "
            + "LEFT JOIN Gene AS dam2Gene "
            + "ON dam2Gene._gene_key = dam2Genotype._gene_key "
            + "LEFT JOIN Genotype AS sireGenotype "
            + "ON sireGenotype._mouse_key = sire._mouse_key "
            + "LEFT JOIN Gene AS sireGene "
            + "ON sireGene._gene_key = sireGenotype._gene_key "
            + "WHERE Mouse.ID = '";
    
    private String ancestryQuery = "SELECT Mouse.ID, Mating.matingID, TRIM(Mouse.diet) AS mouseDiet, "
            + " TRIM(dam1.ID) AS dam1ID, TRIM(dam1Strain.strainName) AS dam1Strain, TRIM(dam1Litter.litterID) AS dam1LitterID, ABS(dam1Mating.matingID) AS dam1MatingID,"
            + " TRIM(dam1.origin) AS dam1Origin, TRIM(dam1.newTag) AS dam1NewTag, DATE(dam1.birthDate) AS dam1Birthdate, DATE(dam1.exitDate) AS dam1ExitDate,"
            + " TRIM(dam1.cod) AS dam1COD, TRIM(dam1.codNotes) AS dam1CODNotes, TRIM(dam1.generation) AS dam1Generation, TRIM(dam1.sex) AS dam1Sex,"
            + " TRIM(dam1.lifeStatus) AS dam1LifeStatus, TRIM(dam1.breedingStatus) AS dam1BreedingStatus, TRIM(dam1.coatColor) AS dam1CoatColor,"
            + " TRIM(dam1.diet) AS dam1Diet, TRIM(dam1.owner) AS dam1Owner, TRIM(dam1.`comment`) AS dam1Comment, ABS(dam1._litter_key) AS dam1LitterKey,"
            + " CONCAT(Coalesce(dam1Gene.labSymbol, ''), ' ', COALESCE(dam1Genotype.allele1, ''), ' ', COALESCE(dam1Genotype.allele2,'')) AS dam1Genotype,"
            + " TRIM(dam2.ID) AS dam2ID, TRIM(dam2Strain.strainName) AS dam2Strain, TRIM(dam2Litter.litterID) AS dam2LitterID, ABS(dam2Mating.matingID) AS dam2MatingID,"
            + " TRIM(dam2.origin) AS dam2Origin, TRIM(dam2.newTag) AS dam2NewTag, DATE(dam2.birthDate) AS dam2Birthdate, DATE(dam2.exitDate) AS dam2ExitDate,"
            + " TRIM(dam2.cod) AS dam2COD, TRIM(dam2.codNotes) AS dam2CODNotes, TRIM(dam2.generation) AS dam2Generation, TRIM(dam2.sex) AS dam2Sex,"
            + " TRIM(dam2.lifeStatus) AS dam2LifeStatus, TRIM(dam2.breedingStatus) AS dam2BreedingStatus, TRIM(dam2.coatColor) AS dam2CoatColor,"
            + " TRIM(dam2.diet) AS damDiet, TRIM(dam2.owner) AS dam2Owner, TRIM(dam2.`comment`) AS dam2Comment, ABS(dam2._litter_key) AS dam2LitterKey,"
            + " CONCAT(COALESCE(dam2Gene.labSymbol, ''), ' ', COALESCE(dam2Genotype.allele1, ''), ' ', COALESCE(dam2Genotype.allele2, '')) AS dam2Genotype,"
            + " TRIM(sire.ID) AS sireID, TRIM(sireStrain.strainName) AS sireStrain, TRIM(sireLitter.litterID) AS sireLitterID, ABS(sireMating.matingID) AS sireMatingID,"
            + " TRIM(sire.origin) AS sireOrigin, TRIM(sire.newTag) AS sireNewTag, DATE(sire.birthDate) AS sireBirthdate, DATE(sire.exitDate) AS sireExitDate,"
            + " TRIM(sire.cod) AS sireCOD, TRIM(sire.codNotes) AS sireCODNotes, TRIM(sire.generation) AS sireGeneration, TRIM(sire.sex) AS sireSex,"
            + " TRIM(sire.lifeStatus) AS sireLifeStatus, TRIM(sire.breedingStatus) AS sireBreedingStatus, TRIM(sire.coatColor) AS sireCoatColor,"
            + " TRIM(sire.diet) AS sireDiet, TRIM(sire.owner) AS sireOwner, TRIM(sire.`comment`) AS sireComment, ABS(sire._litter_key) AS sireLitterKey,"
            + " CONCAT(COALESCE(sireGene.labSymbol, ''), ' ', COALESCE(sireGenotype.allele1, ''), ' ', COALESCE(sireGenotype.allele2, '')) AS sireGenotype"
            + " FROM Mouse"
            + " LEFT JOIN Litter"
            + " ON Mouse._litter_key = Litter._litter_key"
            + " LEFT JOIN Mating"
            + " ON Litter._mating_key = Mating._mating_key"
            + " LEFT JOIN Mouse AS dam1"
            + " ON Mating._dam1_key = dam1._mouse_key"
            + " LEFT JOIN Mouse AS dam2"
            + " ON Mating._dam2_key = dam2._mouse_key"
            + " LEFT JOIN Mouse AS sire"
            + " ON Mating._sire_key = sire._mouse_key"
            + " LEFT JOIN Genotype AS dam1Genotype"
            + " ON dam1._mouse_key = dam1Genotype._mouse_key"
            + " LEFT JOIN Genotype AS dam2Genotype"
            + " ON dam2._mouse_key = dam2Genotype._mouse_key"
            + " LEFT JOIN Genotype AS sireGenotype"
            + " ON sire._mouse_key = sireGenotype._mouse_key"
            + " LEFT JOIN Gene as dam1Gene"
            + " ON dam1Genotype._gene_key = dam1Gene._gene_key"
            + " LEFT JOIN Gene as dam2Gene"
            + " ON dam2Genotype._gene_key = dam2Gene._gene_key"
            + " LEFT JOIN Gene as sireGene"
            + " ON sireGenotype._gene_key = sireGene._gene_key"
            + " LEFT JOIN Strain AS dam1Strain"
            + " ON dam1._strain_key = dam1Strain._strain_key"
            + " LEFT JOIN Strain AS dam2Strain"
            + " ON dam2._strain_key = dam2Strain._strain_key"
            + " LEFT JOIN Strain AS sireStrain"
            + " ON sire._strain_key = sireStrain._strain_key"
            + " LEFT JOIN Litter AS dam1Litter"
            + " ON dam1._litter_key = dam1Litter._litter_key"
            + " LEFT JOIN  Litter AS dam2Litter"
            + " ON dam2._litter_key = dam2Litter._litter_key"
            + " LEFT JOIN Litter AS sireLitter"
            + " ON sire._litter_key = sireLitter._litter_key"
            + " LEFT JOIN Mating AS dam1Mating"
            + " ON dam1Litter._mating_key = dam1Mating._mating_key"
            + " LEFT JOIN Mating AS dam2Mating"
            + " ON dam2Litter._mating_key = dam2Mating._mating_key"
            + " LEFT JOIN Mating AS sireMating"
            + " ON sireLitter._mating_key = sireMating._mating_key"
            + " WHERE Mouse.ID = '";
    
    private String progenyQuery = "SELECT Mouse._mouse_key, TRIM(Mouse.ID) AS parentID, TRIM(Mouse.diet) AS mouseDiet, TRIM(Mouse.sex) AS parentSex, "
            + "Mating.matingID, Litter.litterID, TRIM(child.ID) AS childID, TRIM(child.sex) AS childSex, "
            + "ABS(child._litter_key) AS childLitterKey, DATE(child.birthDate) AS childBirthdate, DATE(child.exitDate) AS childExitDate, "
            + "TRIM(child.cod) AS childCOD, TRIM(child.codNotes) AS childCODNotes, TRIM(child.generation) AS childGeneration, "
            + "TRIM(child.sex) AS childSex, TRIM(child.lifeStatus) AS childlifeStatus, "
            + "TRIM(child.breedingStatus) AS childBreedingStatus, TRIM(child.diet) AS childDiet, "
            + "TRIM(child.coatColor) AS childCoatColor, TRIM(child.owner) AS childOwner, TRIM(child.origin) AS childOrigin, "
            + "TRIM(dam1.ID) AS dam1ID, TRIM(dam2.ID) AS dam2ID, TRIM(sire.ID) AS sireID, "
            + "TRIM(child.protocol) AS childProtocol, TRIM(child.comment) AS childComment, TRIM(Strain.strainName) AS childStrainName, "
            + "ABS(Strain.jrNum) AS childJrNum,  "
            + "CONCAT(COALESCE(Gene.labSymbol, ''), ' ', COALESCE(Genotype.allele1, ''), ' ', COALESCE(Genotype.allele2, '')) AS childGenotype, "
            + "CONCAT(COALESCE(sireGene.labSymbol, ''), ' ', COALESCE(sireGenotype.allele1, ''), ' ', COALESCE(sireGenotype.allele2, '')) AS sireGenotype, "
            + "CONCAT(COALESCE(dam1Gene.labSymbol, ''), ' ', COALESCE(dam1Genotype.allele1, ''), ' ', COALESCE(dam1Genotype.allele2, '')) AS dam1Genotype, "
            + "CONCAT(COALESCE(dam2Gene.labSymbol, ''), ' ', COALESCE(dam2Genotype.allele1, ''), ' ', COALESCE(dam2Genotype.allele2, '')) AS dam2Genotype "
            + "FROM Mouse "
            + "LEFT JOIN Mating "
            + "ON Mating._sire_key = Mouse._mouse_key OR Mating._dam1_key = Mouse._mouse_key OR Mating._dam2_key = Mouse._mouse_key "
            + "LEFT JOIN Mouse AS dam1 "
            + "ON dam1._mouse_key = Mating._dam1_key "
            + "LEFT JOIN Mouse AS dam2 "
            + "ON dam2._mouse_key = Mating._dam2_key "
            + "LEFT JOIN Mouse AS sire "
            + "ON sire._mouse_key = Mating._sire_key "
            + "LEFT JOIN Litter "
            + "ON Litter._mating_key = Mating._mating_key "
            + "LEFT JOIN Mouse AS child "
            + "ON child._litter_key = Litter._litter_key "
            + "LEFT JOIN Strain "
            + "ON Strain._strain_key = child._strain_key "
            + "LEFT JOIN Genotype "
            + "ON Genotype._mouse_key = child._mouse_key "
            + "LEFT JOIN Gene "
            + "ON Gene._gene_key = Genotype._gene_key "
            + "LEFT JOIN Genotype AS dam1Genotype "
            + "ON dam1Genotype._mouse_key = dam1._mouse_key "
            + "LEFT JOIN Gene AS dam1Gene "
            + "ON dam1Gene._gene_key = dam1Genotype._gene_key "
            + "LEFT JOIN Genotype AS dam2Genotype "
            + "ON dam2Genotype._mouse_key = dam2._mouse_key "
            + "LEFT JOIN Gene AS dam2Gene "
            + "ON dam2Gene._gene_key = dam2Genotype._gene_key "
            + "LEFT JOIN Genotype AS sireGenotype "
            + "ON sireGenotype._mouse_key = sire._mouse_key "
            + "LEFT JOIN Gene AS sireGene "
            + "ON sireGene._gene_key = sireGenotype._gene_key "
            + "WHERE Mouse.ID = '";
    
    
    
    private JPTDTO JPTData = new JPTDTO();
    private Connection con = null;
    
    public JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs){
        this.wgs=wgs;
        if(pedigreeType.equals("ancestry")){
            System.out.println("generating JPT data for ancestry...");
            generateAncestryData(mouseID, depth, wgs);
            System.out.println("finished generating Ancestry data...");
        }
        else{
            System.out.println("generating JPT data for progeny...");
            generateProgenyData(mouseID, depth, wgs);
            System.out.println("finished generating progeny data...");
        }
        return JPTData;
    }
    
    private void generateAncestryData(String mouseID, int depth, ArrayList<String> wgs) {
        //queue for breadth first tree walk
        LinkedList<JPTDTO> tempQueue = new LinkedList<JPTDTO>();
        Result rootMouse = executeQuery(rootQuery + mouseID + "';");
        int iterations = 0;
        //calculate number of iterations neccessary to get info for tree
        for(int idx = 0; idx < depth; idx++){
            iterations = iterations + (int) Math.pow(3, idx);
        }
        if (rootMouse.getRowCount() > 0) {
            int count = 1;
            initializeRootDTO(JPTData, rootMouse);
            tempQueue.add(JPTData);
            while(!tempQueue.isEmpty() && iterations >= count){
                JPTDTO dto = tempQueue.poll();
                initializeAncestryDTO(dto);
                for(JPTDTO child : dto.getNextGen()){
                    tempQueue.add(child);
                }
                count++;
            }
        }
    }
    
    
    private void generateProgenyData(String mouseID, int limit, ArrayList<String> wgs){
        Result rootMouse = executeQuery(rootQuery + mouseID + "';");
        initializeRootDTO(JPTData, rootMouse);
        createProgenyTree(JPTData, limit, 0);
    }
    
    private boolean myContains(ArrayList<String> workgroups, String owner){
        for(String workgroup:workgroups){
            if(owner.equalsIgnoreCase(workgroup)){
                return true;
            }
        }
        return false;
    }
    
    
    private void createProgenyTree(JPTDTO root, int limit, int depth) {
        try {
            JPTDTO notYoursJPTDTO = new JPTDTO();
            notYoursJPTDTO.setIconPath("/images/stopSign.png");
            //increment depth, on first run depth should be 0 -> 1
            depth++;
            //if you've reached the maximum requested depth (as high as 6) return
            if (depth > limit) {
                return;
            }
            ArrayList<String> childNames = new ArrayList<String>();
            JPTDTO JPTDTOChild = new JPTDTO();
            if (!root.getID().equals("")) {
                SortedMap[] children = executeQuery(progenyQuery + root.getID() + "';").getRows();
                for (SortedMap child : children) {
                    //check to make sure it's not a duplicate (due to multiple genotypes)
                    if (!childNames.contains(myGet("childID", child)) && !myGet("childID", child).equals("")) {
                        //create JPTDTO from SortedMap (contains information on child)
                        JPTDTOChild = initializeJPTDTO(child);
                        //add child's JPTDTO to the NextGen arrayList
                        if (myContains(wgs,JPTDTOChild.getOwner())) {
                            root.getNextGen().add(JPTDTOChild);
                            childNames.add(myGet("childID", child));
                            createProgenyTree(JPTDTOChild, limit, depth);
                        }
                        else{
                            root.getNextGen().add(notYoursJPTDTO);
                        }
                    } 
                    else {
                        if (!JPTDTOChild.getGenotype().contains(myGet("childGenotype", child))) {
                            JPTDTOChild.setGenotype(JPTDTOChild.getGenotype() + ", " + myGet("childGenotype", child));
                        } 
                        else if (!JPTDTOChild.getSireGenotype().contains(myGet("sireGenotype", child))) {
                            JPTDTOChild.setSireGenotype(JPTDTOChild.getSireGenotype() + ", " + myGet("sireGenotype", child));
                        } 
                        else if (!JPTDTOChild.getDam1Genotype().contains(myGet("dam1Genotype", child))) {
                            JPTDTOChild.setDam1Genotype(JPTDTOChild.getDam1Genotype() + ", " + myGet("dam1Genotype", child));
                        } 
                        else if (!JPTDTOChild.getDam2Genotype().contains(myGet("dam2Genotype", child))) {
                            JPTDTOChild.setDam2Genotype(JPTDTOChild.getDam2Genotype() + ", " + myGet("dam2Genotype", child) );
                        }
                    }
                }
            }
        } 
        catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
    
    private JPTDTO initializeJPTDTO(SortedMap mouse){
        JPTDTO theChild = new JPTDTO();
        
        theChild.setBirthDate(myGet("childBirthdate", mouse));
        theChild.setMatingID(myGet("matingID", mouse));
        theChild.setLitterID(myGet("litterID", mouse));
        theChild.setID(myGet("childID",mouse));
        theChild.setSex(myGet("childSex", mouse));
        theChild.setBreedingStatus(myGet("childBreedingStatus", mouse));
        theChild.setCOD(myGet("childCOD", mouse));
        theChild.setCODNotes(myGet("childCODNotes", mouse));
        theChild.setCoatColor(myGet("childCoatColor", mouse));
        theChild.setComment(myGet("childComment", mouse));
        theChild.setDiet(myGet("childDiet", mouse));
        theChild.setExitDate(myGet("childExitDate", mouse));
        theChild.setGeneration(myGet("childGeneration", mouse));
        theChild.setLifeStatus(myGet("childLifeStatus", mouse));
        theChild.setOrigin(myGet("childOrigin", mouse));
        theChild.setDiet(myGet("childDiet", mouse));
        theChild.setOwner(myGet("childOwner", mouse));
        theChild.setProtocol(myGet("childProtocol", mouse));
        theChild.setStrainName(myGet("childStrainName", mouse));
        theChild.setDam1ID(myGet("dam1ID", mouse));
        theChild.setDam2ID(myGet("dam2ID", mouse));
        theChild.setSireID(myGet("sireID", mouse));
        theChild.setGenotype(myGet("childGenotype", mouse));
        theChild.setSireGenotype(myGet("sireGenotype", mouse));
        theChild.setDam1Genotype(myGet("dam1Genotype", mouse));
        theChild.setDam2Genotype(myGet("dam2Genotype", mouse));
        theChild.setSiblings(generateLitterData(myGet("childLitterKey", mouse)));
        
        if(theChild.getSex().equals("M")){
            theChild.setIconPath("/images/mouse2.png");
        }
        else{
            theChild.setIconPath("/images/mouse1.png");
        }
        
        return theChild;
    }
    
    private void initializeAncestryDTO(JPTDTO theDTO) {
        if (!theDTO.getID().equals("")){
            Result result = executeQuery(ancestryQuery + theDTO.getID() + "';");

            JPTDTO dam1JPTDTO = new JPTDTO();
            JPTDTO dam2JPTDTO = new JPTDTO();
            JPTDTO sireJPTDTO = new JPTDTO();

            SortedMap[] results = result.getRows();
            if (results.length > 0) {
                SortedMap ancestryInfo = results[0];
                theDTO.setDam1ID(myGet("dam1ID", ancestryInfo));
                theDTO.setDam2ID(myGet("dam2ID", ancestryInfo));      
                theDTO.setSireID(myGet("sireID", ancestryInfo));         
                theDTO.setDiet(myGet("mouseDiet", ancestryInfo));
                
                dam1JPTDTO.setID(myGet("dam1ID", ancestryInfo));
                dam1JPTDTO.setBirthDate(myGet("dam1Birthdate", ancestryInfo));
                dam1JPTDTO.setStrainName(myGet("dam1Strain", ancestryInfo));
                dam1JPTDTO.setLitterID(myGet("dam1LitterID", ancestryInfo));
                dam1JPTDTO.setMatingID(myGet("dam1MatingID", ancestryInfo));
                dam1JPTDTO.setOrigin(myGet("dam1Origin", ancestryInfo));
                dam1JPTDTO.setExitDate(myGet("dam1ExitDate", ancestryInfo));
                dam1JPTDTO.setCOD(myGet("dam1COD", ancestryInfo));
                dam1JPTDTO.setCODNotes(myGet("dam1CODNotes", ancestryInfo));
                dam1JPTDTO.setGeneration(myGet("dam1Generation", ancestryInfo));
                dam1JPTDTO.setSex(myGet("dam1Sex", ancestryInfo));
                dam1JPTDTO.setLifeStatus(myGet("dam1LifeStatus", ancestryInfo));
                dam1JPTDTO.setBreedingStatus(myGet("dam1BreedingStatus", ancestryInfo));
                dam1JPTDTO.setCoatColor(myGet("dam1CoatColor", ancestryInfo));
                dam1JPTDTO.setDiet(myGet("dam1Diet", ancestryInfo));
                dam1JPTDTO.setOwner(myGet("dam1Owner", ancestryInfo));
                dam1JPTDTO.setComment(myGet("dam1Comment", ancestryInfo));
                dam1JPTDTO.setSiblings(generateLitterData(myGet("dam1LitterKey", ancestryInfo)));
                dam1JPTDTO.setIconPath("/images/mouse1.png");

                dam2JPTDTO.setID(myGet("dam2ID", ancestryInfo));
                dam2JPTDTO.setBirthDate(myGet("dam2Birthdate", ancestryInfo));
                dam2JPTDTO.setStrainName(myGet("dam2Strain", ancestryInfo));
                dam2JPTDTO.setLitterID(myGet("dam2LitterID", ancestryInfo));
                dam2JPTDTO.setMatingID(myGet("dam2MatingID", ancestryInfo));
                dam2JPTDTO.setOrigin(myGet("dam2Origin", ancestryInfo));
                dam2JPTDTO.setExitDate(myGet("dam2ExitDate", ancestryInfo));
                dam2JPTDTO.setCOD(myGet("dam2COD", ancestryInfo));
                dam2JPTDTO.setCODNotes(myGet("dam2CODNotes", ancestryInfo));
                dam2JPTDTO.setGeneration(myGet("dam2Generation", ancestryInfo));
                dam2JPTDTO.setSex(myGet("dam2Sex", ancestryInfo));
                dam2JPTDTO.setLifeStatus(myGet("dam2LifeStatus", ancestryInfo));
                dam2JPTDTO.setBreedingStatus(myGet("dam2BreedingStatus", ancestryInfo));
                dam2JPTDTO.setCoatColor(myGet("dam2CoatColor", ancestryInfo));
                dam2JPTDTO.setDiet(myGet("dam2Diet", ancestryInfo));
                dam2JPTDTO.setOwner(myGet("dam2Owner", ancestryInfo));
                dam2JPTDTO.setComment(myGet("dam2Comment", ancestryInfo));
                dam2JPTDTO.setSiblings(generateLitterData(myGet("dam2LitterKey", ancestryInfo)));
                dam2JPTDTO.setIconPath("/images/mouse1.png");

                sireJPTDTO.setID(myGet("sireID", ancestryInfo));
                sireJPTDTO.setBirthDate(myGet("sireBirthdate", ancestryInfo));
                sireJPTDTO.setStrainName(myGet("sireStrain", ancestryInfo));
                sireJPTDTO.setLitterID(myGet("sireLitterID", ancestryInfo));
                sireJPTDTO.setMatingID(myGet("sireMatingID", ancestryInfo));
                sireJPTDTO.setOrigin(myGet("sireOrigin", ancestryInfo));
                sireJPTDTO.setExitDate(myGet("sireExitDate", ancestryInfo));
                sireJPTDTO.setCOD(myGet("sireCOD", ancestryInfo));
                sireJPTDTO.setCODNotes(myGet("sireCODNotes", ancestryInfo));
                sireJPTDTO.setGeneration(myGet("sireGeneration", ancestryInfo));
                sireJPTDTO.setSex(myGet("sireSex", ancestryInfo));
                sireJPTDTO.setLifeStatus(myGet("sireLifeStatus", ancestryInfo));
                sireJPTDTO.setBreedingStatus(myGet("sireBreedingStatus", ancestryInfo));
                sireJPTDTO.setCoatColor(myGet("sireCoatColor", ancestryInfo));
                sireJPTDTO.setDiet(myGet("sireDiet", ancestryInfo));
                sireJPTDTO.setOwner(myGet("sireOwner", ancestryInfo));
                sireJPTDTO.setComment(myGet("sireComment", ancestryInfo));
                sireJPTDTO.setSiblings(generateLitterData(myGet("sireLitterKey", ancestryInfo)));
                sireJPTDTO.setIconPath("/images/mouse2.png");
            }

            for (SortedMap data : result.getRows()) {
                if (!sireJPTDTO.getGenotype().contains(myGet("sireGenotype", data))) {
                    sireJPTDTO.setGenotype(sireJPTDTO.getGenotype() + " " + myGet("sireGenotype", data));
                    theDTO.setSireGenotype(sireJPTDTO.getGenotype() + " " + myGet("sireGenotype", data));
                }
                if (!dam1JPTDTO.getGenotype().contains(myGet("dam1Genotype", data))) {
                    dam1JPTDTO.setGenotype(dam1JPTDTO.getGenotype() + " " + myGet("dam1Genotype", data));
                    theDTO.setDam1Genotype(sireJPTDTO.getGenotype() + " " + myGet("sireGenotype", data));
                }
                if (!dam2JPTDTO.getGenotype().contains(myGet("dam2Genotype", data))) {
                    dam2JPTDTO.setGenotype(dam2JPTDTO.getGenotype() + " " + myGet("dam2Genotype", data));
                    theDTO.setDam2Genotype(sireJPTDTO.getGenotype() + " " + myGet("sireGenotype", data));
                }
            }
            
            JPTDTO notYoursJPTDTO = new JPTDTO();
            notYoursJPTDTO.setIconPath("/images/lgr_gry.gif");
            
            if(!dam1JPTDTO.getID().equals("") && myContains(wgs, dam1JPTDTO.getOwner())){
                theDTO.getNextGen().add(dam1JPTDTO);
            }
            else if(!dam1JPTDTO.getID().equals("")){
                theDTO.getNextGen().add(notYoursJPTDTO);
            }
            //only add dam2 if there was a dam2 in the mating.
            if(!dam2JPTDTO.getID().equals("") && myContains(wgs, dam2JPTDTO.getOwner())){
                theDTO.getNextGen().add(dam2JPTDTO);
            }
            else if(!dam2JPTDTO.getID().equals("")){
                theDTO.getNextGen().add(notYoursJPTDTO);
            }
            if(!sireJPTDTO.getID().equals("") && myContains(wgs, sireJPTDTO.getOwner())){
                theDTO.getNextGen().add(sireJPTDTO);
            }
            else if(!sireJPTDTO.getID().equals("")){
                theDTO.getNextGen().add(notYoursJPTDTO);
            }
        }
    }
    
    private void initializeRootDTO(JPTDTO theDTO, Result result){
        SortedMap mouseData = result.getRows()[0];
        theDTO.setBirthDate(myGet("mouseBirthdate",mouseData));
        theDTO.setCOD(myGet("mouseCOD",mouseData));
        theDTO.setCODNotes(myGet("mouseCODNotes", mouseData));
        theDTO.setComment(myGet("mouseComment",mouseData));
        theDTO.setExitDate(myGet("mouseExitDate",mouseData));
        theDTO.setID(myGet("mouseID",mouseData));
        theDTO.setLitterID(myGet("mouseLitterID",mouseData));
        theDTO.setMatingID(myGet("mouseMatingID", mouseData));
        theDTO.setOrigin(myGet("mouseOrigin",mouseData));
        theDTO.setOwner(myGet("mouseOwner",mouseData));
        theDTO.setProtocol(myGet("mouseProtocol",mouseData));
        theDTO.setSex(myGet("mouseSex", mouseData));
        theDTO.setStrainName(myGet("mouseStrain",mouseData));
        theDTO.setDiet(myGet("mouseDiet", mouseData));
        
        theDTO.setGeneration(myGet("mouseGeneration", mouseData));
        theDTO.setLifeStatus(myGet("mouseLifeStatus", mouseData));
        theDTO.setBreedingStatus(myGet("mouseBreedingStatus", mouseData));
        theDTO.setCoatColor(myGet("mouseCoatColor", mouseData));
        theDTO.setGeneration(myGet("mouseDiet", mouseData));  
        
        theDTO.setDam1ID(myGet("dam1ID", mouseData));
        theDTO.setDam2ID(myGet("dam2ID", mouseData));
        theDTO.setSireID(myGet("sireID", mouseData));
        
        if(theDTO.getSex().equals("M")){
            theDTO.setIconPath("/images/mouse2.png");
        }
        else{
            theDTO.setIconPath("/images/mouse1.png");
        }
        
        theDTO.setSiblings(generateLitterData(myGet("mouseLitterKey", mouseData)));
        
        for(SortedMap data : result.getRows()){
            if (!theDTO.getGenotype().contains(myGet("mouseGenotype", data))) {
                theDTO.setGenotype(theDTO.getGenotype() + myGet("mouseGenotype", data) + " ");
            } 
            if (!theDTO.getSireGenotype().contains(myGet("sireGenotype", data))) {
                theDTO.setSireGenotype(theDTO.getSireGenotype() + myGet("sireGenotype", data) + " ");
            } 
            if (!theDTO.getDam1Genotype().contains(myGet("dam1Genotype", data))) {
                theDTO.setDam1Genotype(theDTO.getDam1Genotype() + myGet("dam1Genotype", data) + " ");
            } 
            if (!theDTO.getDam2Genotype().contains(myGet("dam2Genotype", data))) {
                theDTO.setDam2Genotype(theDTO.getDam2Genotype() + myGet("dam2Genotype", data) + " ");
            }
        }
    }
    
    public ArrayList<String> generateLitterData(String litterKey){
        ArrayList<String> litterMates = new ArrayList<String>();
        if (!litterKey.equals("")) {


            String litterMateQuery = "SELECT ID FROM Mouse WHERE _litter_key = " + litterKey + ";";
            Result litterMateResult = executeQuery(litterMateQuery);

            for (SortedMap litterMate : litterMateResult.getRows()) {
                litterMates.add(myGet("ID", litterMate));
            }
        }
        return litterMates;
    }
    
    private Result executeQuery(String theQuery){
        ResultSet myResultSet;
        Result myResult;
        
        try {
            con = super.getConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
         }
         catch (SQLException ex) {
            Logger.getLogger(JPTDAO.class.getName()).log(Level.SEVERE, null, ex);  
            super.closeConnection(con);
            return null;
        }
    }

    @Override
    protected String myGet(String field, SortedMap result){
        if(result.get(field) == null){
            return "";
        }
        else{
            return result.get(field).toString();
        }
    }

    /**
     * @return the JPTData
     */
    public JPTDTO getJPTData() {
        return JPTData;
    }

    /**
     * @param JPTData the JPTData to set
     */
    public void setJPTData(JPTDTO JPTData) {
        this.JPTData = JPTData;
    }
}
