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
public class AdminStrainTypeDTO extends ITBaseDTO {
    private String strainTypeKey = "";
    private String strainType = "";
    private Boolean isActive = true;
    private String createdBy = "";
    private String dateCreated = "";
    private String modifiedBy = "";
    private String dateModified = "";
    private String version = "";
    
    
    public AdminStrainTypeDTO() {
        
    }

    public AdminStrainTypeDTO(
        String strainTypeKey,
        String strainType,
        Boolean isActive,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
        this.setStrainTypeKey(strainTypeKey);
        this.setStrainType(strainType);
        this.setIsActive(isActive);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
        this.setVersion(version);
    }

    /**
     * @return the strainTypeKey
     */
    public String getStrainTypeKey() {
        return strainTypeKey;
    }

    /**
     * @param strainTypeKey the strainTypeKey to set
     */
    public void setStrainTypeKey(String strainTypeKey) {
        this.strainTypeKey = strainTypeKey;
    }

    /**
     * @return the strainType
     */
    public String getStrainType() {
        return strainType;
    }

    /**
     * @param strainType the strainType to set
     */
    public void setStrainType(String strainType) {
        this.strainType = strainType;
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
