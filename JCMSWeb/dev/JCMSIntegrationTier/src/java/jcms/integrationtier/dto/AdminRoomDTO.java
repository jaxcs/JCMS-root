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
public class AdminRoomDTO extends ITBaseDTO {
    private String roomKey = "0"; 
    private String roomName = ""; 
    private String healthLevelHistoryKey = ""; 
    private String version = "";
    private Boolean isActive = true;
    private String createdBy = "";
    private String dateCreated = "";
    private String modifiedBy = "";
    private String dateModified = "";
    private AdminHealthLevelHistoryDTO healthLevelHistoryDTO = new AdminHealthLevelHistoryDTO();
    private String historyCount = "0";
    private String label = "";
    
    public AdminRoomDTO() {
        this.setHealthLevelHistoryDTO(new AdminHealthLevelHistoryDTO());
    }

    public AdminRoomDTO(
        String roomKey,
        String roomName,
        String healthLevelHistoryKey,
        String version,
        Boolean isActive,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified) {
        this.setRoomKey(roomKey);
        this.setRoomName(roomName);
        this.setHealthLevelHistoryKey(healthLevelHistoryKey);
        this.setVersion(version);
        this.setIsActive(isActive);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
    }
    
    public AdminRoomDTO(AdminRoomDTO dto){
        this.createdBy = dto.getCreatedBy();
        this.dateCreated = dto.getDateCreated();
        this.dateModified = dto.getDateModified();
        this.healthLevelHistoryDTO = dto.getHealthLevelHistoryDTO();
        this.healthLevelHistoryKey = dto.getHealthLevelHistoryKey();
        this.historyCount = dto.getHistoryCount();
        this.isActive = dto.getIsActive();
        this.label = dto.getLabel();
        this.modifiedBy = dto.getModifiedBy();
        this.roomKey = dto.getRoomKey();
        this.roomName = dto.getRoomName();
        this.version = dto.getVersion();
    }

    public Boolean isInsert() {
        return (this.getRoomKey().equalsIgnoreCase("0") || this.getRoomKey().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        return disabled;
    }

    public void clear() {
        this.setRoomName("");
        this.getHealthLevelHistoryDTO().setStartDate(null);
        this.setIsActive(true);
        this.setHealthLevelHistoryDTO(new AdminHealthLevelHistoryDTO());
    }
    
    /**
     * @return the roomKey
     */
    public String getRoomKey() {
        return roomKey;
    }

    /**
     * @param roomKey the roomKey to set
     */
    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the healthLevelHistoryKey
     */
    public String getHealthLevelHistoryKey() {
        return healthLevelHistoryKey;
    }

    /**
     * @param healthLevelHistoryKey the healthLevelHistoryKey to set
     */
    public void setHealthLevelHistoryKey(String healthLevelHistoryKey) {
        this.healthLevelHistoryKey = healthLevelHistoryKey;
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
     * @return the healthLevelHistoryDTO
     */
    public AdminHealthLevelHistoryDTO getHealthLevelHistoryDTO() {
        return healthLevelHistoryDTO;
    }

    /**
     * @param healthLevelHistoryDTO the healthLevelHistoryDTO to set
     */
    public void setHealthLevelHistoryDTO(AdminHealthLevelHistoryDTO healthLevelHistoryDTO) {
        this.healthLevelHistoryDTO = healthLevelHistoryDTO;
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
     * @return the historyCount
     */
    public String getHistoryCount() {
        return historyCount;
    }

    /**
     * @param historyCount the historyCount to set
     */
    public void setHistoryCount(String historyCount) {
        this.historyCount = historyCount;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
