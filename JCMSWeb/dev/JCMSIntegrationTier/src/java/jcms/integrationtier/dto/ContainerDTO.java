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
 * @author mkamato
 */
public class ContainerDTO extends ITBaseDTO {
    
    private String _container_key = "0";
    private String containerID = "";
    private String containerName = "";
    private String comment = "";
    private String _containerHistory_key = "0";
    private String version = "";
    private String historyCount = "0";
    private ContainerHistoryDTO cageHistoryDTO = new ContainerHistoryDTO();

    public Boolean isInsert() {
        return (this.getContainer_key().equalsIgnoreCase("0") || this.getContainer_key().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        return disabled;
    }

    public void clear() {
        this.setContainerName("");
        this.getCageHistoryDTO().setActionDate(null);
        this.setCageHistoryDTO(new ContainerHistoryDTO());
    }
    
    /**
     * @return the _container_key
     */
    public String getContainer_key() {
        return _container_key;
    }

    /**
     * @param container_key the _container_key to set
     */
    public void setContainer_key(String container_key) {
        this._container_key = container_key;
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the _containerHistory_key
     */
    public String getContainerHistory_key() {
        return _containerHistory_key;
    }

    /**
     * @param containerHistory_key the _containerHistory_key to set
     */
    public void setContainerHistory_key(String containerHistory_key) {
        this._containerHistory_key = containerHistory_key;
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
     * @return the cageHistoryDTO
     */
    public ContainerHistoryDTO getCageHistoryDTO() {
        return cageHistoryDTO;
    }

    /**
     * @param cageHistoryDTO the cageHistoryDTO to set
     */
    public void setCageHistoryDTO(ContainerHistoryDTO cageHistoryDTO) {
        this.cageHistoryDTO = cageHistoryDTO;
    }
}
