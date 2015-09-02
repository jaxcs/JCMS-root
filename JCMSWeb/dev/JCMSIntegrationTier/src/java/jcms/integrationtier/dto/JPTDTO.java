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

package jcms.integrationtier.dto;


import java.util.ArrayList;
import jcms.integrationtier.colonyManagement.MouseEntity;

/**
 *
 * @author mkamato
 */
public class JPTDTO {

    //going
    private ArrayList<JPTDTO> nextGen = new ArrayList<JPTDTO>();
    private ArrayList<String> siblings = new ArrayList<String>(); //the mice from the same litter
    private String sex = "";
    private String ID = "";
    private String origin = "";
    private String protocol = "";
    private String owner = "";
    private String generation = "";
    private String CODNotes = "";
    private String COD = "";
    private String exitDate = "";
    private String birthDate = "";
    private String comment = "";
    private String genotype = "";
    private String strainName = "";
    private String lifeStatus = "";
    private String breedingStatus = "";
    private String coatColor = "";
    private String diet = "";
    private String type = ""; /*can be set as a style in the JSP to 
                                   specify icon based on animal/sample type 
                                   such as mouse, embryo, sperm straw, etc*/
    private String iconPath = "";
    private String displayString = "";
    private String litterID = "";
    private String matingID = "";
    private String dam1ID = "";
    private String dam2ID = "";
    private String sireID = "";
    private String dam1Genotype = "";
    private String dam2Genotype = "";
    private String sireGenotype = "";
    private boolean rootNode = false;

    /**
     * @return the nextGen
     */
    public ArrayList<JPTDTO> getNextGen() {
        return nextGen;
    }

    /**
     * @param nextGen the nextGen to set
     */
    public void setNextGen(ArrayList<JPTDTO> nextGen) {
        this.nextGen = nextGen;
    }

    /**
     * @return the siblings
     */
    public ArrayList<String> getSiblings() {
        return siblings;
    }

    /**
     * @param siblings the siblings to set
     */
    public void setSiblings(ArrayList<String> siblings) {
        this.siblings = siblings;
    }

    /**
     * @return the genotype
     */
    public String getGenotype() {
        return genotype;
    }

    /**
     * @param genotype the genotype to set
     */
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    /**
     * @return the strainName
     */
    public String getStrainName() {
        return strainName;
    }

    /**
     * @param strainName the strainName to set
     */
    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the iconPath
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * @param iconPath the iconPath to set
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * @return the displayString
     */
    public String getDisplayString() {
        return displayString;
    }

    /**
     * @param displayString the displayString to set
     */
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    /**
     * @return the litterID
     */
    public String getLitterID() {
        return litterID;
    }

    /**
     * @param litterID the litterID to set
     */
    public void setLitterID(String litterID) {
        this.litterID = litterID;
    }

    /**
     * @return the matingID
     */
    public String getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(String matingID) {
        this.matingID = matingID;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * @return the CODNotes
     */
    public String getCODNotes() {
        return CODNotes;
    }

    /**
     * @param CODNotes the CODNotes to set
     */
    public void setCODNotes(String CODNotes) {
        this.CODNotes = CODNotes;
    }

    /**
     * @return the COD
     */
    public String getCOD() {
        return COD;
    }

    /**
     * @param COD the COD to set
     */
    public void setCOD(String COD) {
        this.COD = COD;
    }

    /**
     * @return the exitDate
     */
    public String getExitDate() {
        return exitDate;
    }

    /**
     * @param exitDate the exitDate to set
     */
    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the lifeStatus
     */
    public String getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the breedingStatus
     */
    public String getBreedingStatus() {
        return breedingStatus;
    }

    /**
     * @param breedingStatus the breedingStatus to set
     */
    public void setBreedingStatus(String breedingStatus) {
        this.breedingStatus = breedingStatus;
    }

    /**
     * @return the coatColor
     */
    public String getCoatColor() {
        return coatColor;
    }

    /**
     * @param coatColor the coatColor to set
     */
    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    /**
     * @return the diet
     */
    public String getDiet() {
        return diet;
    }

    /**
     * @param diet the diet to set
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }

    /**
     * @return the dam1ID
     */
    public String getDam1ID() {
        return dam1ID;
    }

    /**
     * @param dam1ID the dam1ID to set
     */
    public void setDam1ID(String dam1ID) {
        this.dam1ID = dam1ID;
    }

    /**
     * @return the dam2ID
     */
    public String getDam2ID() {
        return dam2ID;
    }

    /**
     * @param dam2ID the dam2ID to set
     */
    public void setDam2ID(String dam2ID) {
        this.dam2ID = dam2ID;
    }

    /**
     * @return the sireID
     */
    public String getSireID() {
        return sireID;
    }

    /**
     * @param sireID the sireID to set
     */
    public void setSireID(String sireID) {
        this.sireID = sireID;
    }

    /**
     * @return the dam1Genotype
     */
    public String getDam1Genotype() {
        return dam1Genotype;
    }

    /**
     * @param dam1Genotype the dam1Genotype to set
     */
    public void setDam1Genotype(String dam1Genotype) {
        this.dam1Genotype = dam1Genotype;
    }

    /**
     * @return the dam2Genotype
     */
    public String getDam2Genotype() {
        return dam2Genotype;
    }

    /**
     * @param dam2Genotype the dam2Genotype to set
     */
    public void setDam2Genotype(String dam2Genotype) {
        this.dam2Genotype = dam2Genotype;
    }

    /**
     * @return the sireGenotype
     */
    public String getSireGenotype() {
        return sireGenotype;
    }

    /**
     * @param sireGenotype the sireGenotype to set
     */
    public void setSireGenotype(String sireGenotype) {
        this.sireGenotype = sireGenotype;
    }

    /**
     * @return the rootNode
     */
    public boolean isRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(boolean rootNode) {
        this.rootNode = rootNode;
    }
    
}
