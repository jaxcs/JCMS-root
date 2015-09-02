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

/**
 *
 * @author mkamato
 */
public class cvContainerStatusDTO {
    
    private String _containerStatus_key = "";
    private String containerStatus = "";
    private String billable = "";
    private String version = "";

    /**
     * @return the _containerStatus_key
     */
    public String getContainerStatus_key() {
        return _containerStatus_key;
    }

    /**
     * @param containerStatus_key the _containerStatus_key to set
     */
    public void setContainerStatus_key(String containerStatus_key) {
        this._containerStatus_key = containerStatus_key;
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
    public String getBillable() {
        return billable;
    }

    /**
     * @param billable the billable to set
     */
    public void setBillable(String billable) {
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
}
