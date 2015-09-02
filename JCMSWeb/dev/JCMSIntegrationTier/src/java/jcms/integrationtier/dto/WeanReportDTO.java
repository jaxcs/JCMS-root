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

import java.util.Date;
import java.util.List;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;

/**
 *
 * @author bas
 */
public class WeanReportDTO {
    private Date pbStartDate = null;
    private List<OwnerEntity> owners = null;
    private Date weanDate;
    private MatingEntity mating;
    private ContainerEntity container;
    private LitterEntity litter;
    //used as return parameters
    private String matingID = "";
    private String matingDate = "";
    private String room = "";
    private String strainName = "";
    private String jrNum = "";
    private String owner = "";
    private String birthDate = "";
    private String dateToWean = "";
    private String litterID = "";
    private String totalBorn = "";
    private String numFemale = "";
    private String numMale = "";
    private String tagDate = "";
    private String weanRecorded = "";
    private String litterStatus = "";
    private String generation = "";
    private String needsTyping = "";
    private String cageID = "";
    private String cageName = "";
    private String comment = "";
    private String weanNote = "";
    private String rack = "";
    private String rackRow = "";
    private String rackColumn = "";

    
    public WeanReportDTO(){
        
    }
    
    public WeanReportDTO(WeanReportDTO dto){
        this.owners         = dto.owners;
        this.matingID       = dto.matingID;
        this.matingDate     = dto.matingDate;
        this.room           = dto.room;
        this.strainName     = dto.strainName;
        this.jrNum          = dto.jrNum;
        this.owner          = dto.owner;
        this.birthDate      = dto.birthDate;
        this.dateToWean     = dto.dateToWean;
        this.litterID       = dto.litterID;
        this.totalBorn      = dto.totalBorn;
        this.numFemale      = dto.numFemale;
        this.numMale        = dto.numMale;
        this.tagDate        = dto.tagDate;
        this.weanRecorded   = dto.weanRecorded;
        this.litterStatus   = dto.litterStatus;
        this.generation     = dto.generation;
        this.needsTyping    = dto.needsTyping;
        this.cageID         = dto.cageID;
        this.cageName       = dto.cageName;
        this.comment        = dto.comment;
        this.weanNote       = dto.weanNote;
        this.rack           = dto.rack;
        this.rackRow        = dto.rackRow;
        this.rackColumn     = dto.rackColumn;
    }

        /**
     * @return the pbStartDate
     */
    public Date getPbStartDate() {
        if (this.pbStartDate == null)
            this.pbStartDate = new Date();
        return pbStartDate;
    }
    
    /**
     * @param pbStartDate the pbStartDate to set
     */
    public void setPbStartDate(Date pbStartDate) {
        this.pbStartDate = pbStartDate;
    }

    /**
     * @return the owners
     */
    public List<OwnerEntity> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<OwnerEntity> owners) {
        this.owners = owners;
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
     * @return the matingDate
     */
    public String getMatingDate() {
        return matingDate;
    }

    /**
     * @param matingDate the matingDate to set
     */
    public void setMatingDate(String matingDate) {
        this.matingDate = matingDate;
    }
    
        /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
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
     * @return the jrNum
     */
    public String getJrNum() {
        return jrNum;
    }

    /**
     * @param jrNum the jrNum to set
     */
    public void setJrNum(String jrNum) {
        this.jrNum = jrNum;
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
    
    
     //return the dateToWean
    public String getDateToWean() {
        return dateToWean;
    }

    /**
     * @param dateToWean the dateToWean to set
    */ 
    
    public void setDateToWean(String dateToWean) {
        this.dateToWean = dateToWean;
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
//Copied from mka I AM NOT SURE WE NEED ANY OF THE ENTITIES
    /**
     * @return the weanDate
     */
    public Date getWeanDate() {
        return weanDate;
    }

    /**
     * @param weanDate the weanDate to set
     */
    public void setWeanDate(Date weanDate) {
        this.weanDate = weanDate;
    }

    /**
     * @return the mating
     */
    public MatingEntity getMating() {
        return mating;
    }

    /**
     * @param mating the mating to set
     */
    public void setMating(MatingEntity mating) {
        this.mating = mating;
    }

    /**
     * @return the container
     */
    public ContainerEntity getContainer() {
        return container;
    }

    /**
     * @param container the container to set
     */
    public void setContainer(ContainerEntity container) {
        this.container = container;
    }

    /**
     * @return the litter
     */
    public LitterEntity getLitter() {
        return litter;
    }

    /**
     * @param litter the litter to set
     */
    public void setLitter(LitterEntity litter) {
        this.litter = litter;
    }

    /**
     * @return the totalBorn
     */
    public String getTotalBorn() {
        return totalBorn;
    }

    /**
     * @param totalBorn the totalBorn to set
     */
    public void setTotalBorn(String totalBorn) {
        this.totalBorn = totalBorn;
    }

    /**
     * @return the numFemale
     */
    public String getNumFemale() {
        return numFemale;
    }

    /**
     * @param numFemale the numFemale to set
     */
    public void setNumFemale(String numFemale) {
        this.numFemale = numFemale;
    }

    /**
     * @return the numMale
     */
    public String getNumMale() {
        return numMale;
    }

    /**
     * @param numMale the numMale to set
     */
    public void setNumMale(String numMale) {
        this.numMale = numMale;
    }

    /**
     * @return the tagDate
     */
    public String getTagDate() {
        return tagDate;
    }

    /**
     * @param tagDate the tagDate to set
     */
    public void setTagDate(String tagDate) {
        this.tagDate = tagDate;
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
     * @return the needsTyping
     */
    public String getNeedsTyping() {
        return needsTyping;
    }

    /**
     * @param needsTyping the needsTyping to set
     */
    public void setNeedsTyping(String needsTyping) {
        this.needsTyping = needsTyping;
    }

    /**
     * @return the cageID
     */
    public String getCageID() {
        return cageID;
    }

    /**
     * @param cageID the cageID to set
     */
    public void setCageID(String cageID) {
        this.cageID = cageID;
    }

    /**
     * @return the cageName
     */
    public String getCageName() {
        return cageName;
    }

    /**
     * @param cageName the cageName to set
     */
    public void setCageName(String cageName) {
        this.cageName = cageName;
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
     * @return the weanNote
     */
    public String getWeanNote() {
        return weanNote;
    }

    /**
     * @param weanNote the weanNote to set
     */
    public void setWeanNote(String weanNote) {
        this.weanNote = weanNote;
    }

    /**
     * @return the weanRecorded
     */
    public String getWeanRecorded() {
        return weanRecorded;
    }

    /**
     * @param weanRecorded the weanRecorded to set
     */
    public void setWeanRecorded(String weanRecorded) {
        this.weanRecorded = weanRecorded;
    }

    /**
     * @return the litterStatus
     */
    public String getLitterStatus() {
        return litterStatus;
    }

    /**
     * @param litterStatus the litterStatus to set
     */
    public void setLitterStatus(String litterStatus) {
        this.litterStatus = litterStatus;
    }

    /**
     * @return the rack
     */
    public String getRack() {
        return rack;
    }

    /**
     * @param rack the rack to set
     */
    public void setRack(String rack) {
        this.rack = rack;
    }

    /**
     * @return the rackRow
     */
    public String getRackRow() {
        return rackRow;
    }

    /**
     * @param rackRow the rackRow to set
     */
    public void setRackRow(String rackRow) {
        this.rackRow = rackRow;
    }

    /**
     * @return the rackColumn
     */
    public String getRackColumn() {
        return rackColumn;
    }

    /**
     * @param rackColumn the rackColumn to set
     */
    public void setRackColumn(String rackColumn) {
        this.rackColumn = rackColumn;
    }
   
}
