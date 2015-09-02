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

import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class AdminStrainStatusDTO extends ITBaseDTO {
    private String strainStatusKey = "";
    private String strainStatus = "";
    private String description = "";
    private Boolean isActive = true;
    private String createdBy = "";
    private String dateCreated = "";
    private String modifiedBy = "";
    private String dateModified = "";
    private String version = "";
    
    
    public AdminStrainStatusDTO() {
        
    }

    public AdminStrainStatusDTO(
        String strainStatusKey,
        String strainStatus,
        String description,
        Boolean isActive,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
        this.setStrainStatusKey(strainStatusKey);
        this.setStrainStatus(strainStatus);
        this.setDescription(description);
        this.setIsActive(isActive);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
        this.setVersion(version);
    }

    /**
     * @return the strainStatusKey
     */
    public String getStrainStatusKey() {
        return strainStatusKey;
    }

    /**
     * @param strainStatusKey the strainStatusKey to set
     */
    public void setStrainStatusKey(String strainStatusKey) {
        this.strainStatusKey = strainStatusKey;
    }

    /**
     * @return the strainStatus
     */
    public String getStrainStatus() {
        return strainStatus;
    }

    /**
     * @param strainStatus the strainStatus to set
     */
    public void setStrainStatus(String strainStatus) {
        this.strainStatus = strainStatus;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the dateModified
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
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
    
    
}
