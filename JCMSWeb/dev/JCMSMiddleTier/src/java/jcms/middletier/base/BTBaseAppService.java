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

package jcms.middletier.base;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.portal.IntegrationTierPortal;

/**
 * <b>File name:</b>  BTBaseAppServicejava  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides general support to all Business Tier business objects.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class BTBaseAppService extends BTBaseObject
{
    private IntegrationTierPortal intTierPortal = new IntegrationTierPortal();

    protected Integer save(ITBaseEntityInterface entity) throws SaveEntityException, DuplicateNameException
    {
        Integer primaryKey = -1;

        new IntegrationTierPortal().getSystemFacadeLocal().baseSave(entity);

        if ((entity.getKey() != null) && (entity.getKey() >= 0))
            primaryKey = entity.getKey();
        else
            primaryKey = -1;

        return primaryKey;
    }

    /**
     * @return the intTierPortal
     */
    protected IntegrationTierPortal getIntTierPortal() {
        return intTierPortal;
    }

    /**
     * @param intTierPortal the intTierPortal to set
     */
    protected void setIntTierPortal(IntegrationTierPortal intTierPortal) {
        this.intTierPortal = intTierPortal;
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
    protected ITBaseEntityInterface baseFind(ITBaseEntityInterface entity)
    {
        return new BTBaseBO().baseFind(entity);
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
    protected ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface entity, int key)
    {
        return new BTBaseBO().baseFindByKey(entity, key);
    }

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    protected ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity)
    {
        return new BTBaseBO().findAndForceLazyLoad(parentEntity);
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
    protected List<ITBaseEntityInterface> basefindAll(ITBaseEntityInterface entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAll(entity);

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface 
            entity, String owner)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllByOwner(entity, owner);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface 
            entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindActiveStrains(entity);
        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface 
            entity, String id) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByMouseID(entity, id);

        return list;
    }
    
    public ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface 
            entity, int id) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByMatingID(entity, id);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface 
            entity, int id) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByContainerID(entity, id);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindBySetUpVariable(entity, val);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface 
            entity, String id) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByLitterID(entity, id);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface 
            entity, int id) {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByContainerHistoryKey(entity, id);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface 
            entity, String id) {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindByContainerName(entity, id);

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface 
            entity, String newTag)
    {
        ITBaseEntityInterface list = null;

        list = new BTBaseBO().baseFindByNewTag(entity, newTag);

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllByOwners(entity, ownerLst);

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindAllByMouseOwners(
            ITBaseEntityInterface entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllByMouseOwners(entity, ownerLst);

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindAllByMatingOwners(
            ITBaseEntityInterface entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllByMatingOwners(entity, ownerLst);

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has multiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    public List<ITBaseEntityInterface> baseFindAllByUser(ITBaseEntityInterface
            entity, Integer userKey) throws Exception {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllByUser(entity, userKey);

        return list;
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
    protected List<ITBaseEntityInterface> basefindAllActive(ITBaseEntityInterface entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = new BTBaseBO().baseFindAllActive(entity);

        return list;
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
    protected Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey)
    {
        return new BTBaseBO().baseFindMaxVersionNumber(entity, parentKey);
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
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxPrimaryKey"));
        return new BTBaseBO().baseFindMaxPrimaryKey(entity, parentKey);
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
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxEntityPrimaryKey"));
        return new BTBaseBO().baseFindMaxEntityPrimaryKey(entity);
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
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxEntityPrimaryKey"));
        return new BTBaseBO().baseFindMaxContainerID(entity);
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
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxLitterKey"));
        return new BTBaseBO().baseFindMaxLitterKey(entity, key);
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
    public String baseFindMaxLitterID(ITBaseEntityInterface entity, int key)
    {
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxLitterID"));
        return new BTBaseBO().baseFindMaxLitterID(entity, key);
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
        getLogger().logInfo(formatLogMessage( "BTBaseAppService"  , "baseFindMaxEntityMatingID"));
        return new BTBaseBO().baseFindMaxMatingID(entity);
    }

    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. <br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity or null
     */
    protected ITBaseEntityInterface baseFindDefaultEntity(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        ITBaseEntityInterface rtnEntity = null;

        try 
        {
            rtnEntity = new BTBaseBO().baseFindDefaultEntity(entity);
        }
        catch (Exception ex)

        {
            // return null;
        }

        return rtnEntity;
    }
}