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

package jcms.web.admin;

/**
 *
 * @author mkamato
 */

import org.richfaces.model.TreeNodeImpl;
import jcms.integrationtier.dto.AdminWorkgroupUserFunctionalAreaDTO;
import jcms.integrationtier.dto.AdminFunctionalAreaDTO;
import jcms.integrationtier.dao.AdministrationDAO;
import jcms.integrationtier.dto.AdminCenterDTO;
import jcms.integrationtier.dto.AdminUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupDTO;
import java.util.ArrayList;

public class AdminTreeNode extends TreeNodeImpl {
    
    private Object data;
    private ArrayList<AdminWorkgroupUserFunctionalAreaDTO> WUFADTOs = new ArrayList();
    private AdminFunctionalAreaDTO permissions = null;
    private ArrayList<AdminFunctionalAreaDTO> permissionOptions;
    private String parentKey = "";
    private String iconPath = "";
    private String displayString = "";
    
    public AdminTreeNode(Object theData){
        data = theData;
        if(theData instanceof AdminCenterDTO){
            AdminCenterDTO temp = (AdminCenterDTO) theData;
            iconPath = "/images/Center_Enabled.jpg";
            displayString = temp.getCenter();
        }
        else if(theData instanceof AdminWorkgroupDTO){
            AdminWorkgroupDTO temp = (AdminWorkgroupDTO) theData;
            iconPath="/images/Workgroup_Enabled.jpg";
            displayString = temp.getWorkgroupName();
        }
        else{
            iconPath="/images/User_Enabled.jpg";
            AdminUserDTO temp = (AdminUserDTO) theData;
            displayString = temp.getLastName() + ", " + temp.getFirstName() + " (" + temp.getUserName() + ")";
        }
        permissionOptions = new AdministrationDAO().getFunctionalAreas();
    }
    
    public AdminTreeNode(){}

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the permissions
     */
    public AdminFunctionalAreaDTO getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(AdminFunctionalAreaDTO permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the iconPath
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * @param iconPath the iconPath to set
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * @return the displayString
     */
    public String getDisplayString() {
        return displayString;
    }

    /**
     * @param displayString the displayString to set
     */
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    /**
     * @return the permissionOptions
     */
    public ArrayList<AdminFunctionalAreaDTO> getPermissionOptions() {
        return permissionOptions;
    }

    /**
     * @param permissionOptions the permissionOptions to set
     */
    public void setPermissionOptions(ArrayList<AdminFunctionalAreaDTO> permissionOptions) {
        this.permissionOptions = permissionOptions;
    }

    /**
     * @return the parentKey
     */
    public String getParentKey() {
        return parentKey;
    }

    /**
     * @param parentKey the parentKey to set
     */
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    /**
     * @return the WUFADTOs
     */
    public ArrayList<AdminWorkgroupUserFunctionalAreaDTO> getWUFADTOs() {
        return WUFADTOs;
    }

    /**
     * @param WUFADTOs the WUFADTOs to set
     */
    public void setWUFADTOs(ArrayList<AdminWorkgroupUserFunctionalAreaDTO> WUFADTOs) {
        this.WUFADTOs = WUFADTOs;
    }
}
