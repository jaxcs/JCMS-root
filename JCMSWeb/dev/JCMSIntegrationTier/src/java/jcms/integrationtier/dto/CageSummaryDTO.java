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
import java.util.List;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;

/**
 *
 * @author rkavitha
 */
public class CageSummaryDTO {

    private Date pbStartDate = null;
    private Date pbbEndDate = null;
    private CvContainerStatusEntity status = new CvContainerStatusEntity();
    private RoomEntity room = new RoomEntity();
    private boolean isBillable = false;
    private boolean isStatus = false;
    private List<OwnerEntity> owners = null;

    private String groupBy = "None";
    private String penBilling = "Any Day";

    /**
     * @return the pbStartDate
     */
    public Date getPbStartDate() {
        if (this.pbStartDate == null)
            this.pbStartDate = new Date();
        return pbStartDate;
    }

    /**
     * @param pbStartDate the pbStartDate to set
     */
    public void setPbStartDate(Date pbStartDate) {
        this.pbStartDate = pbStartDate;
    }

    /**
     * @return the pbbEndDate
     */
    public Date getPbbEndDate() {
        if (this.pbbEndDate == null)
            this.pbbEndDate = new Date();
        return pbbEndDate;
    }

    /**
     * @param pbbEndDate the pbbEndDate to set
     */
    public void setPbbEndDate(Date pbbEndDate) {
        this.pbbEndDate = pbbEndDate;
    }

    /**
     * @return the status
     */
    public CvContainerStatusEntity getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(CvContainerStatusEntity status) {
        this.status = status;
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
     * @return the groupBy
     */
    public String getGroupBy() {
        return groupBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    /**
     * @return the penBilling
     */
    public String getPenBilling() {
        return penBilling;
    }

    /**
     * @param penBilling the penBilling to set
     */
    public void setPenBilling(String penBilling) {
        this.penBilling = penBilling;
    }

    /**
     * @return the isBillable
     */
    public boolean isIsBillable() {
        return isBillable;
    }

    /**
     * @param isBillable the isBillable to set
     */
    public void setIsBillable(boolean isBillable) {
        this.isBillable = isBillable;
    }

    /**
     * @return the owners
     */
    public List<OwnerEntity> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<OwnerEntity> owners) {
        this.owners = owners;
    }

    /**
     * @return the isStatus
     */
    public boolean isIsStatus() {
        if (this.status != null && this.status.getContainerStatus() != null)
            this.isStatus = (this.status.getContainerStatus().trim().length() > 0);
        else
            this.isStatus = false;
        return this.isStatus;
    }

    /**
     * @param isStatus the isStatus to set
     */
    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }
}
