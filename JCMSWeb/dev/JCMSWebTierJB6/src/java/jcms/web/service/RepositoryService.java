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

package jcms.web.service;

import java.util.List;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.UserDTO;
import jcms.integrationtier.exception.EntityNotFoundException;
import jcms.middletier.dto.DbInfoDTO;
import jcms.middletier.dto.JCMSDbInfoDTO;
import jcms.web.base.WTBaseService;


/**
 * <b>File name:</b>  RepositoryService.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Service to retrieve data by search or find.  <p>
 * <b>Overview:</b>  Provide a single point of service to all backing beans for
 *      retrieving data via search or find.  Hides logic to access the business tier
 *      and consolidates all business tier references to service classes.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2012-11-16 11:51:28 -0500 (Fri, 16 Nov 2012) $   <p>
 * @author kavitha rama
 * @version $Revision: 18517 $
 */
public class RepositoryService extends WTBaseService
{

    public RepositoryService()
    {

    }

    // BASE FINDERS

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
    public ITBaseEntityInterface baseFind(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().baseFind(entity);
    }
    
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
    public MouseEntity baseFind(int key) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findMouse(key);
    }
    
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
    public MouseEntity findDam(String id) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().validDam(id);
    }
    
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
    public MouseEntity findSire(String id) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().validSire(id);
    }
    
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
    public MouseEntity findMouseByID(String id) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findMouseByID(id);
    }
    
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
    public GenotypeEntity baseFindGenotype(int key) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findGenotype(key);
    }
    
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
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey) throws 
            Exception {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findGenotypeByMouseAndGene(mKey, gKey);
    }
    
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
    public int findMatingByDamAndSire(int mKey, int gKey) throws 
            Exception {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findMatingByDamAndSire(mKey, gKey);
    }
    
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
    public ITBaseEntityTable findAllelesByGene(int key) throws Exception
    {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findAllelesByGene(key);
    }

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    public ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                findAndForceLazyLoad(parentEntity);
    }

    /**
     *  <b>Purpose:</b>  Find all objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public List<ITBaseEntityInterface> baseFindAll(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().baseFindAll( entity );
    }

    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().baseFindAllActive(entity);
    }

    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface
            entity, String owner) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindAllByOwner(entity, owner);
    }
        
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface
            entity, String id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByLitterID(entity, id);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByMoueID(ITBaseEntityInterface
            entity, String id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByMouseID(entity, id);
    }
    
    public ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface
            entity, int id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByMatingID(entity, id);
    }
    
    public ITBaseEntityTable findMatingByMouse(int mKey) throws Exception {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findMatingByMouse(mKey);
    }
    
    public ITBaseEntityTable findMatingBySire(int mKey) throws Exception {
        return this.getBusinessTierPortal().getServiceFacadeLocal().
                findMatingBySire(mKey);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface
            entity, int key) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByKey(entity, key);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface
            entity, int id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByContainerID(entity, id);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindBySetupVariable(ITBaseEntityInterface
            entity, String val) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindBySetUpVariable(entity, val);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface
            entity, int id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByContainerHistoryKey(entity, id);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface
            entity, String id) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByContainerName(entity, id);
    }
    
    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    public ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface
            entity, String newtag) {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindByNewTag(entity, newtag);
    }

    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. <br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity or null
     */
    public ITBaseEntityInterface baseFindDefaultEntity(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindDefaultEntity(entity);
    }

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
    public Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxVersionNumber(entity, parentKey);
    }

    /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxPrimaryKey(entity, parentKey);
    }
    
     /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindMaxEntityPrimaryKey(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().baseFindMaxEntityPrimaryKey(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindMaxContainerID(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxContainerID(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindMaxLitterKey(ITBaseEntityInterface entity, int key)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxLitterKey(entity, key);
    }
    
    /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public String baseFindMaxLitterID(ITBaseEntityInterface entity, int key)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxLitterID(entity, key);
    }
    
    /**
     *  <b>Purpose:</b>  Find the maximum primary key for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindMaxMatingID(ITBaseEntityInterface entity)
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                baseFindMaxMatingID(entity);
    }

    public DbInfoDTO getDatabaseInformation()
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                getDatabaseInformation();
    }

    public JCMSDbInfoDTO getJCMSDatabaseInformation()
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                getJCMSDatabaseInformation();
    }

     /**
     * <b>Purpose:</b>  Find the user identified by <code>userName</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>userName</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    public UserDTO findUser( String userName )
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                findUser( userName );
    }

     /**
     * <b>Purpose:</b>  Find the user identified by <code>userName</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>userName</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    public Result findWorkgroups( String userName ) throws Exception
    {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                findWorkgroupsForUser(userName);
    }

    /**
     * <b>Purpose:</b>  Find the user identified by <code>userName</code>. <br />
     * <b>Overview:</b>  Find the user identified by <code>userName</code>.
     *      Null is returned if the user is not found.  <p />
     * @param userName the userName (userID) of the user to be created
     * @return <code>UserDTO</code> describing the user matching <code>
     *         userName</code>
     */
    public ITBaseEntityInterface findWorkgroupEntity(String wg) throws
            Exception {
        return this.getBusinessTierPortal().getRepositoryFacadeLocal().
                findWorkgroupEntity(wg);
    }
    
}