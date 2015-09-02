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

package jcms.web.main;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dto.AdminDbInfoDTO;
import jcms.web.admin.AdminBean;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class WelcomeBean extends AdminBean {
    
    public WelcomeBean() {
    }
    
    public Boolean getEnvironmentCheck() {
        Boolean showLogin = true;
        CVAdministrationDAO dao = new CVAdministrationDAO();
        ArrayList<AdminDbInfoDTO> dtoList = dao.getDbInfo();
        if (dtoList != null) {
            for (AdminDbInfoDTO dto : dtoList) {
                if(!dto.getWebReleaseNum().equals("3.14.0")){
                    showLogin = false;
                }
                break;
            }
        }
        return (showLogin);
    }

}
