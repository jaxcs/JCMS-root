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

package jcms.middletier.service;

import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.JCMSWebSystemFacadeLocal;
import jcms.integrationtier.dto.UserDTO;
import jcms.integrationtier.exception.EntityNotFoundException;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.base.BTBaseAppService;
import jcms.middletier.businessobject.JCMSWebBO;

/**
 *
 * @author rkavitha
 */
public class JCMSWebAppService extends BTBaseAppService {
    
    JCMSWebBO bo;

    public JCMSWebAppService() {
        bo = new JCMSWebBO();
    }

    public void saveQuery(ITBaseEntityInterface entity) throws Exception {
        //get the access to facade that references JCMSWeb catalog
        bo.baseSave(entity);
    }

    public QueryDefinitionEntity loadQueryByKey(Integer key)
            throws Exception {
        //get the access to facade thta references JCMSWeb catalog
        return bo.baseFindByKey(key);
    }

    public Integer findMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey)
            throws EntityNotFoundException {
        return (bo.baseFindMaxPrimaryKey(entity, parentKey));
    }

    /**
     * <b>Purpose:</b>  Find the user identified by <code>networkID</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>networkID</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    public UserDTO findUser( String userName ) {
        return bo.findUser(userName);
    }

    /**
     *  <b>Purpose:</b>  Get the workgroups for logged in user. <br>
     *  <b>Overview:</b>  this given method returns the workgroups for logged
     * in user. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findWorkgroupsForUser(String user) throws Exception {
        try {
            return bo.findWorkgroupsForUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the workgroups for logged in user. <br>
     *  <b>Overview:</b>  this given method returns the workgroups for logged
     * in user. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityInterface findWorkgroupEntity(String wg) throws Exception {
        try {
            return bo.findWorkgroupEntity(wg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void baseDelete(ITBaseEntityInterface theEntity){
        try{
            bo.baseDelete(theEntity);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}