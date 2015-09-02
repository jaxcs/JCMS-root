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
 * @author cnh
 */
public class AdminHealthLevelHistoryDTO extends ITBaseDTO {
    private String healthLevelHistoryKey = "0"; 
    private String roomKey = "";
    private String healthLevelKey = "";
    private Date startDate;
    private String startDateFrom = "";
    private String startDateTo = "";
    private String version = "";
    private String createdBy = "";
    private String dateCreated = "";
    private String modifiedBy = "";
    private String dateModified = "";
    private String healthLevel = "";
    
    public AdminHealthLevelHistoryDTO() {
        
    }
    public AdminHealthLevelHistoryDTO(
        String healthLevelHistoryKey,
        String roomKey,
        String healthLevelKey,
        Date startDate,
        String version,
        String healthLevel,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified) {
        this.setHealthLevelHistoryKey(healthLevelHistoryKey);
        this.setRoomKey(roomKey);
        this.setHealthLevelKey(healthLevelKey);
        this.setStartDate(startDate);
        this.setVersion(version);
        this.setHealthLevel(healthLevel);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
    }

    public Boolean isInsert() {
        return (this.getHealthLevelHistoryKey().equalsIgnoreCase("0") || this.getHealthLevelHistoryKey().equalsIgnoreCase("")) ;
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
     * @return the healthLevelKey
     */
    public String getHealthLevelKey() {
        return healthLevelKey;
    }

    /**
     * @param healthLevelKey the healthLevelKey to set
     */
    public void setHealthLevelKey(String healthLevelKey) {
        this.healthLevelKey = healthLevelKey;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * @return the healthLevel
     */
    public String getHealthLevel() {
        return healthLevel;
    }

    /**
     * @param healthLevel the healthLevel to set
     */
    public void setHealthLevel(String healthLevel) {
        this.healthLevel = healthLevel;
    }

    /**
     * @return the startDateFrom
     */
    public String getStartDateFrom() {
        return startDateFrom;
    }

    /**
     * @param startDateFrom the startDateFrom to set
     */
    public void setStartDateFrom(String startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    /**
     * @return the startDateTo
     */
    public String getStartDateTo() {
        return startDateTo;
    }

    /**
     * @param startDateTo the startDateTo to set
     */
    public void setStartDateTo(String startDateTo) {
        this.startDateTo = startDateTo;
    }
    
}
