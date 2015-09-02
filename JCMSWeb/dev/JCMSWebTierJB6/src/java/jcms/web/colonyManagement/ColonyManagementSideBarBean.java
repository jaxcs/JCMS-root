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

package jcms.web.colonyManagement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class ColonyManagementSideBarBean {
    private String activeTab = "Manage Mice";
    
    public ColonyManagementSideBarBean() {
        
    }

    /**
     * @return the activeTab
     */
    public String getActiveTab() {
        return activeTab;
    }

    /**
     * @param activeTab the activeTab to set
     */
    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }
    
}
