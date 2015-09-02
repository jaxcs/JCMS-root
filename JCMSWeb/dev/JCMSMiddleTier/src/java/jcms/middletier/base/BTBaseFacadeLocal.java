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
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.OwnerEntity;

/**
 * <b>File name:</b>  BTBaseFacadeLocal interface  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides general support to all Business Tier business objects.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Local
public interface BTBaseFacadeLocal
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
    ITBaseEntityInterface baseFind(ITBaseEntityInterface entity);

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
    ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface entity, int key);

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity);

    /**
     *  <b>Purpose:</b>  Find all objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    List<ITBaseEntityInterface> baseFindAll(ITBaseEntityInterface entity);

    /**
     *  <b>Purpose:</b>  Find all active objects for the parameter class type. <br />
     *  <b>Overview:</b>  Find all active object for the parameter class type.
     *      List of zero elements is returned when the result set has
     *      no rows.  Use to get all users, roles, or workgroups.  <br />
     *  <b>Usage:</b>  Just instantiate an entity and pass it into this
     *      method as an argument.  The code does the rest.  <br />
     * @return List<BaseEntityInterface> return <code>List<BaseEntityInterface></code> entity.
     */
    List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity);

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
    List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface
            entity, String owner);
    
    List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface
            entity);
    
    ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface
            entity, String newTag);
    
    ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface
            entity, String id);
    
    ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface
            entity, int id);
    
    ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface
            entity, int id);
    
    ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface
            entity, int id);
    
    List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface
            entity, String id);
    
    ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface
            entity, String id);
    
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
    List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst);

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
    List<ITBaseEntityInterface> baseFindAllByMouseOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst);

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
    List<ITBaseEntityInterface> baseFindAllByMatingOwners(ITBaseEntityInterface
            entity, ArrayList<OwnerEntity> ownerLst);

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
    Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey);

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
    Integer baseFindMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey);
    
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
    Integer baseFindMaxEntityPrimaryKey(ITBaseEntityInterface entity);
    
    Integer baseFindMaxContainerID(ITBaseEntityInterface entity);
    
    String baseFindMaxLitterID(ITBaseEntityInterface entity, int key);
    
    Integer baseFindMaxLitterKey(ITBaseEntityInterface entity, int key);
    
    Integer baseFindMaxMatingID(ITBaseEntityInterface entity);

    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. <br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity or null
     */
    ITBaseEntityInterface baseFindDefaultEntity(ITBaseEntityInterface entity);   
    
    ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val);
}