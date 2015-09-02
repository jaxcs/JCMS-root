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
 * @author cnh
 */
public class FunctionalAreaPrivilegeDTO {
    private String FunctionalArea ;
    private String Privilege ; 

    public FunctionalAreaPrivilegeDTO(String functionalArea, String privilege) {
        this.setFunctionalArea(functionalArea);
        this.setPrivilege(privilege);
    }
    
    /**
     * @return the FunctionalArea
     */
    public String getFunctionalArea() {
        return FunctionalArea;
    }

    /**
     * @param FunctionalArea the FunctionalArea to set
     */
    public void setFunctionalArea(String FunctionalArea) {
        this.FunctionalArea = FunctionalArea;
    }

    /**
     * @return the Privilege
     */
    public String getPrivilege() {
        return Privilege;
    }

    /**
     * @param Privilege the Privilege to set
     */
    public void setPrivilege(String Privilege) {
        this.Privilege = Privilege;
    }
}
