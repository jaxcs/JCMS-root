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

// imports

import java.util.ArrayList;
import java.util.List;
import jcms.middletier.service.FindAppService;
import jcms.middletier.service.SystemAppService;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.exception.EntityNotFoundException;

/**
 * <b>File name:</b>  BTBaseFacade.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides general support to all Business Tier business objects.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class BTBaseFacade implements BTBaseFacadeLocal
{
    private FindAppService      findAppService      = new FindAppService();
    private SystemAppService    systemAppService    = new SystemAppService();   


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
    @Override
    public ITBaseEntityInterface baseFind(ITBaseEntityInterface entity)
    {
        return this.getFindAppService().baseFind(entity);
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
    @Override
    public ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface entity, int key)
    {
        return this.getFindAppService().baseFindByKey(entity, key);
    }

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    @Override
    public ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity)
    {
        return this.getFindAppService().findAndForceLazyLoad(parentEntity);
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
    @Override
    public List<ITBaseEntityInterface> baseFindAll(ITBaseEntityInterface entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().basefindAll(entity);

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
    @Override
    public List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface
            entity, String owner)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindAllByOwner(entity, owner);

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
    @Override
    public List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface
            entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindActiveStrains(entity);

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
    @Override
    public ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface
            entity, String id)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByMouseID(entity, id);

        return list;
    }
    
    @Override
    public ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByMatingID(entity, id);

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
    @Override
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByContainerHistoryKey(entity, id);

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
    @Override
    public ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByContainerID(entity, id);

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
    @Override
    public ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindBySetUpVariable(entity, val);

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
    @Override
    public ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface
            entity, String id)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByLitterID(entity, id);

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
    @Override
    public List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface
            entity, String id)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindByContainerName(entity, id);

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
    @Override
    public ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface
            entity, String newTag)
    {
        ITBaseEntityInterface list = null;

        list = this.getFindAppService().baseFindByNewTag(entity, newTag);

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
    @Override
    public List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindAllByOwners(entity, ownerLst);

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
    @Override
    public List<ITBaseEntityInterface> baseFindAllByMouseOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindAllByMouseOwners(entity, ownerLst);

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
    @Override
    public List<ITBaseEntityInterface> baseFindAllByMatingOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().baseFindAllByMatingOwners(entity, ownerLst);

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
    @Override
    public List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getFindAppService().basefindAllActive(entity);

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
    @Override
    public Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey)
    {
        return this.getFindAppService().baseFindMaxVersionNumber(entity, parentKey);
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
    @Override
    public Integer baseFindMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey)
    {
        return this.getFindAppService().baseFindMaxPrimaryKey(entity, parentKey);
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
    @Override
    public Integer baseFindMaxEntityPrimaryKey(ITBaseEntityInterface entity)
    {
        return this.getFindAppService().baseFindMaxEntityPrimaryKey(entity);
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
    @Override
    public Integer baseFindMaxContainerID(ITBaseEntityInterface entity)
    {
        return this.getFindAppService().baseFindMaxContainerID(entity);
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
    @Override
    public Integer baseFindMaxLitterKey(ITBaseEntityInterface entity, int key)
    {
        return this.getFindAppService().baseFindMaxLitterKey(entity, key);
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
    @Override
    public String baseFindMaxLitterID(ITBaseEntityInterface entity, int key)
    {
        return this.getFindAppService().baseFindMaxLitterID(entity, key);
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
    @Override
    public Integer baseFindMaxMatingID(ITBaseEntityInterface entity)
    {
        return this.getFindAppService().baseFindMaxMatingID(entity);
    }

    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. <br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity or null
     */
    @Override
    public ITBaseEntityInterface baseFindDefaultEntity(ITBaseEntityInterface entity)
    {
        return this.getFindAppService().baseFindDefaultEntity(entity);
    }

    /**
     * @return the findAppService
     */
    public FindAppService getFindAppService() {
        return findAppService;
    }

    /**
     * @param findAppService the findAppService to set
     */
    public void setFindAppService(FindAppService findAppService) {
        this.findAppService = findAppService;
    }

    /**
     * @return the systemAppService
     */
    public SystemAppService getSystemAppService() {
        return systemAppService;
    }

    /**
     * @param systemAppService the systemAppService to set
     */
    public void setSystemAppService(SystemAppService systemAppService) {
        this.systemAppService = systemAppService;
    }
}