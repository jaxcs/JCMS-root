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
public class AdminLifeStatusDTO  extends ITBaseDTO {
    private String lifeStatusKey = "0";
    private String lifeStatus = "";
    private Boolean exitStatus = false;
    private String description = "";
    private String version = "";
    
    public AdminLifeStatusDTO() {
    }    
    
    public AdminLifeStatusDTO (
        String lifeStatusKey,
        String lifeStatus,
        Boolean exitStatus,
        String description,
        String version) {
        this.setLifeStatusKey(lifeStatusKey);
        this.setLifeStatus(lifeStatus);
        this.setExitStatus(exitStatus);
        this.setDescription(description);
        this.setVersion(version);
    }

    /**
     * @return the lifeStatusKey
     */
    public String getLifeStatusKey() {
        return lifeStatusKey;
    }

    /**
     * @param lifeStatusKey the lifeStatusKey to set
     */
    public void setLifeStatusKey(String lifeStatusKey) {
        this.lifeStatusKey = lifeStatusKey;
    }

    /**
     * @return the lifeStatus
     */
    public String getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
        return (this.getLifeStatusKey().equalsIgnoreCase("0") || this.getLifeStatusKey().equalsIgnoreCase("")) ;
    }

    /**
     * @return the exitStatus
     */
    public Boolean getExitStatus() {
        return exitStatus;
    }

    /**
     * @param exitStatus the exitStatus to set
     */
    public void setExitStatus(Boolean exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
     * @return the disableDelete
     */
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        if (this.getLifeStatus().equalsIgnoreCase("A") || 
            this.getLifeStatus().equalsIgnoreCase("D") || 
            this.getLifeStatus().equalsIgnoreCase("E") || 
            this.getLifeStatus().equalsIgnoreCase("K") || 
            this.getLifeStatus().equalsIgnoreCase("M") || 
            this.getLifeStatus().equalsIgnoreCase("S")) {
            disabled = true;
        }
        return disabled;
    }
}
