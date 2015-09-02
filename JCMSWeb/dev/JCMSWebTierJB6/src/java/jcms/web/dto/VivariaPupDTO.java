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

import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.ContainerDTO;

/**
 *
 * @author mkamato
 */
public class VivariaPupDTO {
    
    private MouseEntity pup = new MouseEntity();
    private ContainerDTO cage = new ContainerDTO();
    private String position = "";

    /**
     * @return the pup
     */
    public MouseEntity getPup() {
        return pup;
    }

    /**
     * @param pup the pup to set
     */
    public void setPup(MouseEntity pup) {
        this.pup = pup;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the cage
     */
    public ContainerDTO getCage() {
        return cage;
    }

    /**
     * @param cage the cage to set
     */
    public void setCage(ContainerDTO cage) {
        this.cage = cage;
    }
    
    
}
