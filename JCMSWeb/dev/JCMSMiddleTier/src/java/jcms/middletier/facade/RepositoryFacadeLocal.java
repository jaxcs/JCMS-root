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

import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.middletier.base.BTBaseFacadeLocal;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.dto.UserDTO;
import jcms.middletier.dto.DbInfoDTO;
import jcms.middletier.dto.JCMSDbInfoDTO;

/**
 * <b>File name:</b>  RepositoryFacadeLocal.java  <p>
 * <b>RsDate developed:</b>  October 2009 <p>
 * <b>Purpose:</b>  Provides methods to perform system search and find.  <p>
 * <b>Overview:</b>  Provides methods to perform system search and find.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */
@Local
public interface RepositoryFacadeLocal extends BTBaseFacadeLocal
{
    /**
     *  <b>Purpose:</b>  Find an object of parameters type. <br>
     *  <b>Overview:</b>  Find an object of parameters type using the primary key.
     *      Returns null if no entity found.  <br>
     * @param entity Contains entity object primary key.
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the primary key parameter.
     *                                  Checked Exception.
     */
    @Override
    ITBaseEntityInterface baseFind(ITBaseEntityInterface entity);

    /**
     *  <b>Purpose:</b>  Find the maximum version number for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxVesrionNumber.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    @Override
    public Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey) ;

    DbInfoDTO getDatabaseInformation();
    

    /**
     * <b>Purpose:</b>  Find the user identified by <code>networkID</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>networkID</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    UserDTO findUser( String userName );


    /**
     *  <b>Purpose:</b>  Get the workgroups for logged in user. <br>
     *  <b>Overview:</b>  this given method returns the workgroups for logged
     * in user. <br>
     *  @throws Exception  Unable to run query.
     */
    Result findWorkgroupsForUser(String user) throws Exception;

    ITBaseEntityInterface findWorkgroupEntity(String wg) throws Exception;

    JCMSDbInfoDTO getJCMSDatabaseInformation();

}