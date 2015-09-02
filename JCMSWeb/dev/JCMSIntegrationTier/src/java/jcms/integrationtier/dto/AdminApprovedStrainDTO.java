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
public class AdminApprovedStrainDTO extends ITBaseDTO {
    private String approvedStrainKey = "0";
    private Boolean isActive = true;
    private String damStrainKey = "";
    private String sireStrainKey = "";
    private String litterStrainKey = "";
    private String ownerKey = "";
    private String version = "";
    private String damStrain = "";
    private String sireStrain = "";
    private String litterStrain = "";

    public AdminApprovedStrainDTO () {
        
    }
    
    public AdminApprovedStrainDTO (
        String approvedStrainKey,
        Boolean isActive,
        String damStrainKey,
        String sireStrainKey,
        String litterStrainKey,
        String ownerKey,
        String version, 
        String damStrain,
        String sireStrain, 
        String litterStrain) {
        this.setApprovedStrainKey(approvedStrainKey);
        this.setIsActive(isActive);
        this.setDamStrainKey(damStrainKey);
        this.setSireStrainKey(sireStrainKey);
        this.setLitterStrainKey(litterStrainKey);
        this.setOwnerKey(ownerKey);
        this.setVersion(version);
        this.setDamStrain(damStrain);
        this.setSireStrain(sireStrain);
        this.setLitterStrain(litterStrain);
    }
    
    /**
     * @return the approvedStrainKey
     */
    public String getApprovedStrainKey() {
        return approvedStrainKey;
    }

    /**
     * @param approvedStrainKey the approvedStrainKey to set
     */
    public void setApprovedStrainKey(String approvedStrainKey) {
        this.approvedStrainKey = approvedStrainKey;
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
     * @return the damStrainKey
     */
    public String getDamStrainKey() {
        return damStrainKey;
    }

    /**
     * @param damStrainKey the damStrainKey to set
     */
    public void setDamStrainKey(String damStrainKey) {
        this.damStrainKey = damStrainKey;
    }

    /**
     * @return the sireStrainKey
     */
    public String getSireStrainKey() {
        return sireStrainKey;
    }

    /**
     * @param sireStrainKey the sireStrainKey to set
     */
    public void setSireStrainKey(String sireStrainKey) {
        this.sireStrainKey = sireStrainKey;
    }

    /**
     * @return the litterStrainKey
     */
    public String getLitterStrainKey() {
        return litterStrainKey;
    }

    /**
     * @param litterStrainKey the litterStrainKey to set
     */
    public void setLitterStrainKey(String litterStrainKey) {
        this.litterStrainKey = litterStrainKey;
    }

    /**
     * @return the ownerKey
     */
    public String getOwnerKey() {
        return ownerKey;
    }

    /**
     * @param ownerKey the ownerKey to set
     */
    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
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

    public Boolean isInsert() {
        return (this.getApprovedStrainKey().equalsIgnoreCase("0") || this.getApprovedStrainKey().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        return disabled;
    }

    /**
     * @return the damStrain
     */
    public String getDamStrain() {
        return damStrain;
    }

    /**
     * @param damStrain the damStrain to set
     */
    public void setDamStrain(String damStrain) {
        this.damStrain = damStrain;
    }

    /**
     * @return the sireStrain
     */
    public String getSireStrain() {
        return sireStrain;
    }

    /**
     * @param sireStrain the sireStrain to set
     */
    public void setSireStrain(String sireStrain) {
        this.sireStrain = sireStrain;
    }

    /**
     * @return the litterStrain
     */
    public String getLitterStrain() {
        return litterStrain;
    }

    /**
     * @param litterStrain the litterStrain to set
     */
    public void setLitterStrain(String litterStrain) {
        this.litterStrain = litterStrain;
    }
}
