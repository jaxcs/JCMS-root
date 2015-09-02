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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jcms.integrationtier.dao.CVAdministrationDAO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminRetirePenBean extends AdminBean {
    private CVAdministrationDAO dao = new CVAdministrationDAO();
    
    public void retirePensAction() {
        String errorLog = "";
        String message = "";
        int retirePenCount = dao.retirePens(errorLog);
        
        if (retirePenCount == 0) {
            message = "Operation complete, no pens need to be retired.";
        } else {
            message = "Operation complete, "+ retirePenCount +" pens retired.";
        }
        addToMessageQueue(message, FacesMessage.SEVERITY_INFO);
        this.getLogger().logInfo(this.formatLogMessage("validate", message));
        
        if (errorLog.length() > 0) {
            addToMessageQueue(errorLog, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", errorLog));
        }
    }

}
