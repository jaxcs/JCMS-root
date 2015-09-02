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

package jcms.middletier.facade;

import javax.ejb.Stateless;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.dto.UserDTO;
import jcms.middletier.base.BTBaseFacade;
import jcms.middletier.service.JCMSWebAppService;

/**
 *
 * @author rkavitha
 */

@Stateless(name=FacadeDao.JCMSWebFacade)
public class JCMSWebFacade extends BTBaseFacade implements JCMSWebFacadeLocal,
                                                        JCMSWebFacadeRemote {

    /**
     * <b>Purpose:</b>  Find the user identified by <code>networkID</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>networkID</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    @Override
    public UserDTO findUser( String userName ) {
        return new JCMSWebAppService().findUser(userName);
    }


    /**
     *  <b>Purpose:</b>  Get the workgroups for logged in user. <br>
     *  <b>Overview:</b>  this given method returns the workgroups for logged
     * in user. <br>
     *  @throws Exception  Unable to run query.
     */
    @Override
    public Result findWorkgroupsForUser(String user) throws Exception {
        return new JCMSWebAppService().findWorkgroupsForUser(user);
    }

    /**
     *  <b>Purpose:</b>  Get the workgroups for logged in user. <br>
     *  <b>Overview:</b>  this given method returns the workgroups for logged
     * in user. <br>
     *  @throws Exception  Unable to run query.
     */
    @Override
    public ITBaseEntityInterface findWorkgroupEntity(String wg) throws Exception {
        try {
            return new JCMSWebAppService().findWorkgroupEntity(wg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}