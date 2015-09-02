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

package jcms.web.dto;

import java.util.Date;

/**
 *
 * @author rkavitha
 */
public class PenInfoDTO {
    private int containerKey = 0;
    private int containerID = 0;
    private String containerName = "";
    
    private int containerHistoryKey = 0;
    private String room = "";
    private String containerStatus = "";
    private Date actionDate = null;

    /**
     * @return the containerKey
     */
    public int getContainerKey() {
        return containerKey;
    }

    /**
     * @param containerKey the containerKey to set
     */
    public void setContainerKey(int containerKey) {
        this.containerKey = containerKey;
    }

    /**
     * @return the containerID
     */
    public int getContainerID() {
        return containerID;
    }

    /**
     * @param containerID the containerID to set
     */
    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    /**
     * @return the containerName
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * @param containerName the containerName to set
     */
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    /**
     * @return the containerHistoryKey
     */
    public int getContainerHistoryKey() {
        return containerHistoryKey;
    }

    /**
     * @param containerHistoryKey the containerHistoryKey to set
     */
    public void setContainerHistoryKey(int containerHistoryKey) {
        this.containerHistoryKey = containerHistoryKey;
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
     * @return the actionDate
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * @param actionDate the actionDate to set
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    
}
