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

import javax.faces.application.Application;
import javax.servlet.http.HttpSession;
import jcms.web.base.WTBaseBackingBean;

/**
 * Backing bean for mainbar.xhtml
 */
public class MainBarBean extends WTBaseBackingBean
{
    public MainBarBean()
    {

    }

    public String go()
    {
        String action = "main";

        return action;
    }

    public String logout()
    {
        HttpSession         session     = (HttpSession) this.getFacesContext().getExternalContext().getSession(false);

        // Invalid session to force user to log back in.
        session.invalidate();

        Application app = this.getFacesContext().getApplication();
        app.getNavigationHandler().handleNavigation(this.getFacesContext(), "/welcome.xhtml", "welcome");

        // To avoid using the navigation handler you could also use...
        //response.sendRedirect("../index.xhtml");

        return null;
    }

}