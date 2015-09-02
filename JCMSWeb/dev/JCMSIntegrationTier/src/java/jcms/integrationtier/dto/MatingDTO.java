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

/**
 *
 * @author mkamato
 */
public class MatingDTO {
    private String _mating_key = "";
    private String _dam1_key = "";
    private String _dam2_key = "";
    private String _sire_key = "";
    private String _strain_key = "";
    private String matingID = "";
    private String suggestedPenID = "";
    private String weanTime = "";
    private String matingDate = "";
    private String retiredDate = "";
    private String generation = "";
    private String owner = "";
    private String weanNote = "";
    private String needsTyping = "";
    private String comment = "";
    private String proposedDiet = "";
    private String proposedRetireDate = "";
    private String proposedD1RetireDiet = "";
    private String proposedD2RetireDiet = "";
    private String proposedSRetireDiet = "";
    private String proposedD1BrStatus = "";
    private String proposedD2BrStatus = "";
    private String proposedSBrStatus = "";
    private String proposedD1LfStatus = "";
    private String proposedD2LfStatus = "";
    private String proposedSLfStatus = "";
    private String proposedRetirePenStatus = "";
    private String suggestedFirstLitterNumber = "";
    private String version = "";
    private String _crossStatus_key = "";

    /**
     * @return the _dam1_key
     */
    public String getDam1_key() {
        return _dam1_key;
    }

    /**
     * @param dam1_key the _dam1_key to set
     */
    public void setDam1_key(String dam1_key) {
        this._dam1_key = dam1_key;
    }

    /**
     * @return the _dam2_key
     */
    public String getDam2_key() {
        return _dam2_key;
    }

    /**
     * @param dam2_key the _dam2_key to set
     */
    public void setDam2_key(String dam2_key) {
        this._dam2_key = dam2_key;
    }

    /**
     * @return the _sire_key
     */
    public String getSire_key() {
        return _sire_key;
    }

    /**
     * @param sire_key the _sire_key to set
     */
    public void setSire_key(String sire_key) {
        this._sire_key = sire_key;
    }

    /**
     * @return the _strain_key
     */
    public String getStrain_key() {
        return _strain_key;
    }

    /**
     * @param strain_key the _strain_key to set
     */
    public void setStrain_key(String strain_key) {
        this._strain_key = strain_key;
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
     * @return the suggestedPenID
     */
    public String getSuggestedPenID() {
        return suggestedPenID;
    }

    /**
     * @param suggestedPenID the suggestedPenID to set
     */
    public void setSuggestedPenID(String suggestedPenID) {
        this.suggestedPenID = suggestedPenID;
    }

    /**
     * @return the weanTime
     */
    public String getWeanTime() {
        return weanTime;
    }

    /**
     * @param weanTime the weanTime to set
     */
    public void setWeanTime(String weanTime) {
        this.weanTime = weanTime;
    }
    
    public void setWeanTime(boolean weanTime){
        if(weanTime){
            this.weanTime = "-1";
        }
        else{
            this.weanTime = "0";
        }
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
     * @return the retiredDate
     */
    public String getRetiredDate() {
        return retiredDate;
    }

    /**
     * @param retiredDate the retiredDate to set
     */
    public void setRetiredDate(String retiredDate) {
        this.retiredDate = retiredDate;
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
        if(needsTyping.equals("true")){
            this.needsTyping = "-1";
        }
        else if(needsTyping.equals("false")){
            this.needsTyping = "0";
        }
    }
    
    public void setNeedsTyping(boolean needsTyping){
        if(needsTyping){
            this.needsTyping = "-1";
        }
        else{
            this.needsTyping = "0";
        }
    }
    
    public void setNeedsTyping(int needsTyping){
        if(needsTyping==0){
            this.needsTyping = "0";
        }
        else{
            this.needsTyping = "-1";
        }
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
     * @return the proposedDiet
     */
    public String getProposedDiet() {
        return proposedDiet;
    }

    /**
     * @param proposedDiet the proposedDiet to set
     */
    public void setProposedDiet(String proposedDiet) {
        this.proposedDiet = proposedDiet;
    }

    /**
     * @return the proposedRetireDate
     */
    public String getProposedRetireDate() {
        return proposedRetireDate;
    }

    /**
     * @param proposedRetireDate the proposedRetireDate to set
     */
    public void setProposedRetireDate(String proposedRetireDate) {
        this.proposedRetireDate = proposedRetireDate;
    }

    /**
     * @return the proposedD1RetireDiet
     */
    public String getProposedD1RetireDiet() {
        return proposedD1RetireDiet;
    }

    /**
     * @param proposedD1RetireDiet the proposedD1RetireDiet to set
     */
    public void setProposedD1RetireDiet(String proposedD1RetireDiet) {
        this.proposedD1RetireDiet = proposedD1RetireDiet;
    }

    /**
     * @return the proposedD2RetireDiet
     */
    public String getProposedD2RetireDiet() {
        return proposedD2RetireDiet;
    }

    /**
     * @param proposedD2RetireDiet the proposedD2RetireDiet to set
     */
    public void setProposedD2RetireDiet(String proposedD2RetireDiet) {
        this.proposedD2RetireDiet = proposedD2RetireDiet;
    }

    /**
     * @return the proposedSRetireDiet
     */
    public String getProposedSRetireDiet() {
        return proposedSRetireDiet;
    }

    /**
     * @param proposedSRetireDiet the proposedSRetireDiet to set
     */
    public void setProposedSRetireDiet(String proposedSRetireDiet) {
        this.proposedSRetireDiet = proposedSRetireDiet;
    }

    /**
     * @return the proposedD1BrStatus
     */
    public String getProposedD1BrStatus() {
        return proposedD1BrStatus;
    }

    /**
     * @param proposedD1BrStatus the proposedD1BrStatus to set
     */
    public void setProposedD1BrStatus(String proposedD1BrStatus) {
        this.proposedD1BrStatus = proposedD1BrStatus;
    }

    /**
     * @return the proposedD2BrStatus
     */
    public String getProposedD2BrStatus() {
        return proposedD2BrStatus;
    }

    /**
     * @param proposedD2BrStatus the proposedD2BrStatus to set
     */
    public void setProposedD2BrStatus(String proposedD2BrStatus) {
        this.proposedD2BrStatus = proposedD2BrStatus;
    }

    /**
     * @return the proposedSBrStatus
     */
    public String getProposedSBrStatus() {
        return proposedSBrStatus;
    }

    /**
     * @param proposedSBrStatus the proposedSBrStatus to set
     */
    public void setProposedSBrStatus(String proposedSBrStatus) {
        this.proposedSBrStatus = proposedSBrStatus;
    }

    /**
     * @return the proposedD1LfStatus
     */
    public String getProposedD1LfStatus() {
        return proposedD1LfStatus;
    }

    /**
     * @param proposedD1LfStatus the proposedD1LfStatus to set
     */
    public void setProposedD1LfStatus(String proposedD1LfStatus) {
        this.proposedD1LfStatus = proposedD1LfStatus;
    }

    /**
     * @return the proposedD2LfStatus
     */
    public String getProposedD2LfStatus() {
        return proposedD2LfStatus;
    }

    /**
     * @param proposedD2LfStatus the proposedD2LfStatus to set
     */
    public void setProposedD2LfStatus(String proposedD2LfStatus) {
        this.proposedD2LfStatus = proposedD2LfStatus;
    }

    /**
     * @return the proposedSLfStatus
     */
    public String getProposedSLfStatus() {
        return proposedSLfStatus;
    }

    /**
     * @param proposedSLfStatus the proposedSLfStatus to set
     */
    public void setProposedSLfStatus(String proposedSLfStatus) {
        this.proposedSLfStatus = proposedSLfStatus;
    }

    /**
     * @return the proposedRetirePenStatus
     */
    public String getProposedRetirePenStatus() {
        return proposedRetirePenStatus;
    }

    /**
     * @param proposedRetirePenStatus the proposedRetirePenStatus to set
     */
    public void setProposedRetirePenStatus(String proposedRetirePenStatus) {
        this.proposedRetirePenStatus = proposedRetirePenStatus;
    }

    /**
     * @return the suggestedFirstLitterNumber
     */
    public String getSuggestedFirstLitterNumber() {
        return suggestedFirstLitterNumber;
    }

    /**
     * @param suggestedFirstLitterNumber the suggestedFirstLitterNumber to set
     */
    public void setSuggestedFirstLitterNumber(String suggestedFirstLitterNumber) {
        this.suggestedFirstLitterNumber = suggestedFirstLitterNumber;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the _crossStatus_key
     */
    public String getCrossStatus_key() {
        return _crossStatus_key;
    }

    /**
     * @param crossStatus_key the _crossStatus_key to set
     */
    public void setCrossStatus_key(String crossStatus_key) {
        this._crossStatus_key = crossStatus_key;
    }

    /**
     * @return the _mating_key
     */
    public String getMating_key() {
        return _mating_key;
    }

    /**
     * @param mating_key the _mating_key to set
     */
    public void setMating_key(String mating_key) {
        this._mating_key = mating_key;
    }
    
}
