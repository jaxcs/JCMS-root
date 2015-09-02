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

import java.util.ArrayList;
import jcms.integrationtier.base.ITBaseDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 *
 * @author cnh
 */
public class LoggedUserDTO extends ITBaseDTO {
    private boolean isMasterAdmin = false;
    private String loggedUser = ""; 
    private UserEntity userEntity = null;
    private ArrayList<WorkgroupEntity> adminWorkgroups = null;
    private ArrayList<WorkgroupEntity> colonyManagerWorkgroups = null;
    private ArrayList<WorkgroupEntity> guestWorkgroups = null;

    /**
     * @return the isMasterAdmin
     */
    public boolean isIsMasterAdmin() {
        return isMasterAdmin;
    }

    /**
     * @param isMasterAdmin the isMasterAdmin to set
     */
    public void setIsMasterAdmin(boolean isMasterAdmin) {
        this.isMasterAdmin = isMasterAdmin;
    }

    /**
     * @return the loggedUser
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * @param loggedUser the loggedUser to set
     */
    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * @param userEntity the userEntity to set
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * @return the adminWorkgroups
     */
    public ArrayList<WorkgroupEntity> getAdminWorkgroups() {
        return adminWorkgroups;
    }

    /**
     * @param adminWorkgroups the adminWorkgroups to set
     */
    public void setAdminWorkgroups(ArrayList<WorkgroupEntity> adminWorkgroups) {
        this.adminWorkgroups = adminWorkgroups;
    }

    /**
     * @return the colonyManagerWorkgroups
     */
    public ArrayList<WorkgroupEntity> getColonyManagerWorkgroups() {
        return colonyManagerWorkgroups;
    }

    /**
     * @param colonyManagerWorkgroups the colonyManagerWorkgroups to set
     */
    public void setColonyManagerWorkgroups(ArrayList<WorkgroupEntity> colonyManagerWorkgroups) {
        this.colonyManagerWorkgroups = colonyManagerWorkgroups;
    }

    /**
     * @return the guestWorkgroups
     */
    public ArrayList<WorkgroupEntity> getGuestWorkgroups() {
        return guestWorkgroups;
    }

    /**
     * @param guestWorkgroups the guestWorkgroups to set
     */
    public void setGuestWorkgroups(ArrayList<WorkgroupEntity> guestWorkgroups) {
        this.guestWorkgroups = guestWorkgroups;
    }
    
}
