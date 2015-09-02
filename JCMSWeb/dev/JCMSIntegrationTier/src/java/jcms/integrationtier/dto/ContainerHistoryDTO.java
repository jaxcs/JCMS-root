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
public class ContainerHistoryDTO extends ITBaseDTO {
    // ContainerHistory
    private Integer containerHistorykey;
    private Date actionDate;
    private Integer version;
    private Integer containerKey;
    private Integer containerStatuskey;
    private String containerStatus;
    
    private String x = "";
    private String y = "";
    private String z = "";
    private String levelKey = "";

    // Container 
    private String containerID;
    
    // Room
    private Integer roomKey;
    private String roomName; 
    private Integer healthLevelHistorykey;
    private String healthLevelHistory;
    
    // Level 
    private String levelId;

    public ContainerHistoryDTO() {
    }

    public ContainerHistoryDTO(Integer containerHistorykey) {
        this.containerHistorykey = containerHistorykey;
    }

    public ContainerHistoryDTO(Integer containerHistorykey, Date actionDate, Integer version) {
        this.containerHistorykey = containerHistorykey;
        this.actionDate = actionDate;
        this.version = version;
    }

    public Integer getContainerHistorykey() {
        return containerHistorykey;
    }

    public void setContainerHistorykey(Integer containerHistorykey) {
        this.containerHistorykey = containerHistorykey;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the containerKey
     */
    public Integer getContainerKey() {
        return containerKey;
    }

    /**
     * @param containerKey the containerKey to set
     */
    public void setContainerKey(Integer containerKey) {
        this.containerKey = containerKey;
    }

    /**
     * @return the containerStatuskey
     */
    public Integer getContainerStatuskey() {
        return containerStatuskey;
    }

    /**
     * @param containerStatuskey the containerStatuskey to set
     */
    public void setContainerStatuskey(Integer containerStatuskey) {
        this.containerStatuskey = containerStatuskey;
    }

    /**
     * @return the roomKey
     */
    public Integer getRoomKey() {
        return roomKey;
    }

    /**
     * @param roomKey the roomKey to set
     */
    public void setRoomKey(Integer roomKey) {
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

    /**
     * @return the containerStatus
     */
    public String getContainerStatus() {
        return containerStatus;
    }

    /**
     * @param containerStatus the containerStatus to set
     */
    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    /**
     * @return the healthLevelHistorykey
     */
    public Integer getHealthLevelHistorykey() {
        return healthLevelHistorykey;
    }

    /**
     * @param healthLevelHistorykey the healthLevelHistorykey to set
     */
    public void setHealthLevelHistorykey(Integer healthLevelHistorykey) {
        this.healthLevelHistorykey = healthLevelHistorykey;
    }

    /**
     * @return the healthLevelHistory
     */
    public String getHealthLevelHistory() {
        return healthLevelHistory;
    }

    /**
     * @param healthLevelHistory the healthLevelHistory to set
     */
    public void setHealthLevelHistory(String healthLevelHistory) {
        this.healthLevelHistory = healthLevelHistory;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public String getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(String z) {
        this.z = z;
    }

    /**
     * @return the levelKey
     */
    public String getLevelKey() {
        return levelKey;
    }

    /**
     * @param levelKey the levelKey to set
     */
    public void setLevelKey(String levelKey) {
        this.levelKey = levelKey;
    }

    /**
     * @return the levelId
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    
}
