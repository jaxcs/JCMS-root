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
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;

/**
 *
 * @author rkavitha
 */
public class PenSearchDTO {
    private int                     containerKey        = 0;
    private int                     containerID         = 0;        
    private int                     containerHistoryKey = 0;
    
    private String                  penID  = "";    
    private String                  containerName = "";
    private String                  penFilter = "";
    
    private RoomEntity              room            = new RoomEntity();
    private CvContainerStatusEntity containerStatus = new CvContainerStatusEntity();
    private Date                    actionDate      = new Date();
    private boolean                 isbillable      = false;

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
     * @return the room
     */
    public RoomEntity getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    /**
     * @return the containerStatus
     */
    public CvContainerStatusEntity getContainerStatus() {
        return containerStatus;
    }

    /**
     * @param containerStatus the containerStatus to set
     */
    public void setContainerStatus(CvContainerStatusEntity containerStatus) {
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

    /**
     * @return the isbillable
     */
    public boolean isIsbillable() {
        return isbillable;
    }

    /**
     * @param isbillable the isbillable to set
     */
    public void setIsbillable(boolean isbillable) {
        this.isbillable = isbillable;
    }

    /**
     * @return the penID
     */
    public String getPenID() {
        return penID;
    }

    /**
     * @param penID the penID to set
     */
    public void setPenID(String penID) {
        this.penID = penID;
    }

    /**
     * @return the penFilter
     */
    public String getPenFilter() {
        return penFilter;
    }

    /**
     * @param penFilter the penFilter to set
     */
    public void setPenFilter(String penFilter) {
        this.penFilter = penFilter;
    }
    
}
