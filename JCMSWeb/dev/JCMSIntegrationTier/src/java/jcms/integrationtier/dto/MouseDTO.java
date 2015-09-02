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
import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author mkamato
 */
public class MouseDTO extends ITBaseDTO {
    private String _mouse_key = "";
    private String _litter_key = "";
    private String _strain_key = "";
    private String _pen_key = "";
    private String ID = "";
    private String newTag = "";
    private Date birthDate = null;
    private Date exitDate = null;
    private String cod = "";
    private String codNotes = "";
    private String generation = "";
    private String sex = "";
    private String lifeStatus = "";
    private String breedingStatus = "";
    private String coatColor = "";
    private String diet = "";
    private String owner = "";
    private String origin = "";
    private String protocol = "";
    private String comment = "";
    private String sampleVialID = "";
    private String sampleVialTagPosition = "";
    private String version = "";
    
    //lazy shortcuts so as to not include entire dto when really only care about the labels
    private String containerID = "";
    private String strainName = "";
    private String genotype = "";

    /**
     * @return the _mouse_key
     */
    public String getMouse_key() {
        return _mouse_key;
    }

    /**
     * @param mouse_key the _mouse_key to set
     */
    public void setMouse_key(String mouse_key) {
        this._mouse_key = mouse_key;
    }

    /**
     * @return the _litter_key
     */
    public String getLitter_key() {
        return _litter_key;
    }

    /**
     * @param litter_key the _litter_key to set
     */
    public void setLitter_key(String litter_key) {
        this._litter_key = litter_key;
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
     * @return the _pen_key
     */
    public String getPen_key() {
        return _pen_key;
    }

    /**
     * @param pen_key the _pen_key to set
     */
    public void setPen_key(String pen_key) {
        this._pen_key = pen_key;
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
     * @return the newTag
     */
    public String getNewTag() {
        return newTag;
    }

    /**
     * @param newTag the newTag to set
     */
    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }

    /**
     * @return the cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return the codNotes
     */
    public String getCodNotes() {
        return codNotes;
    }

    /**
     * @param codNotes the codNotes to set
     */
    public void setCodNotes(String codNotes) {
        this.codNotes = codNotes;
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
     * @return the sampleVialID
     */
    public String getSampleVialID() {
        return sampleVialID;
    }

    /**
     * @param sampleVialID the sampleVialID to set
     */
    public void setSampleVialID(String sampleVialID) {
        this.sampleVialID = sampleVialID;
    }

    /**
     * @return the sampleVialTagPosition
     */
    public String getSampleVialTagPosition() {
        return sampleVialTagPosition;
    }

    /**
     * @param sampleVialTagPosition the sampleVialTagPosition to set
     */
    public void setSampleVialTagPosition(String sampleVialTagPosition) {
        this.sampleVialTagPosition = sampleVialTagPosition;
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
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the exitDate
     */
    public Date getExitDate() {
        return exitDate;
    }

    /**
     * @param exitDate the exitDate to set
     */
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
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
     * @return the containerID
     */
    public String getContainerID() {
        return containerID;
    }

    /**
     * @param containerID the containerID to set
     */
    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }
    
}
