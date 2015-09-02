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
public class AdminContainerStatusDTO extends ITBaseDTO {
    private String containerStatusKey = "0";
    private String containerStatus = "";
    private Boolean billable = false;
    private String version = "";

    public AdminContainerStatusDTO () {
        
    }
    
    public AdminContainerStatusDTO (
        String containerStatusKey,
        String containerStatus,
        Boolean billable,
        String version) {
        this.setContainerStatusKey(containerStatusKey);
        this.setContainerStatus(containerStatus);
        this.setBillable(billable);
        this.setVersion(version);
    }

    /**
     * @return the containerStatusKey
     */
    public String getContainerStatusKey() {
        return containerStatusKey;
    }

    /**
     * @param containerStatusKey the containerStatusKey to set
     */
    public void setContainerStatusKey(String containerStatusKey) {
        this.containerStatusKey = containerStatusKey;
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
     * @return the billable
     */
    public Boolean getBillable() {
        return billable;
    }

    /**
     * @param billable the billable to set
     */
    public void setBillable(Boolean billable) {
        this.billable = billable;
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
        return (this.getContainerStatusKey().equalsIgnoreCase("0") || this.getContainerStatusKey().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        if (this.getContainerStatus().equalsIgnoreCase("Active") || 
            this.getContainerStatus().equalsIgnoreCase("Proposed") || 
            this.getContainerStatus().equalsIgnoreCase("Retired")) {
            disabled = true;
        }
        return disabled;
    }
}
