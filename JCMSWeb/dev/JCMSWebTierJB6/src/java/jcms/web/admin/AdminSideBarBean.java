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

import javax.faces.event.ActionEvent;
import jcms.web.base.WTBaseBackingBean;

/**
 * <b>File name:</b> AdminSideBarBean.java  <p>
 * <b>Date developed:</b> March 2010 <p>
 * <b>Purpose:</b>  Backing bean for administration side bar.  <p>
 * <b>Overview:</b> When using Session scoped beans it is imperative to clear the
 *      session object otherwise the constructor is not called on existing
 *      session beans and the data is consequently never refreshed.
 *      Did not place the refresh code in the data model getter because it gets
 *      triggered way too much.  <p>
 * <b>Last changed by:</b>   $Author: cnh $ <p>
 * <b>Last changed date:</b> $Date: 2010-10-05 13:27:31 -0400 (Tue, 05 Oct 2010) $   <p>
 * @author Craig Hanna
 * @version $Revision: 11203 $
 */
public class AdminSideBarBean extends WTBaseBackingBean
{

    public AdminSideBarBean()
    {
        
    }

    // ACTION LISTENERS

    /**
     * <b>Purpose:</b> Need to clean up session backing beans prior to displaying page.  <p>
     * <b>Overview:</b> Otherwise you end up with old cached data.  <p>
     * @param ae action event triggered by menu option
     */
    public void removeListMethodActionListener(ActionEvent ae)
    {
        this.getLogger().logDebug(this.formatLogMessage("Removing both method " +
                "list and edit bean from session scope. ", "removeListMethodActionListener"));
    }

    /**
     * <b>Purpose:</b> Need to clean up session backing beans prior to displaying page.  <p>
     * <b>Overview:</b> Otherwise you end up with old cached data.  <p>
     * @param ae action event triggered by menu option
     */
    public void removeProcedureActionListener(ActionEvent ae)
    {
        this.getLogger().logDebug(this.formatLogMessage("Removing procedure " +
                "backing beans from session scope. ", "removeProcedureActionListener"));
        this.removeSessionParameter("AdminProcedureDefinition_Backing");
    }

}
