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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TransactionRequiredException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.EditMatingDAO;
import jcms.integrationtier.dao.GenotypeDAO;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.exception.DeleteException;


/**
 * <b>File name:</b>  BTBaseBO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides general support to all Business Tier business objects.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class BTBaseBO extends BTBaseObject
{
    private IntegrationTierPortal   integrationTierPortal = new
            IntegrationTierPortal();

    public BTBaseBO()
    {
        
    }

    // GENERIC METHOD CALLS
    
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().baseFind(entity);
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
    public ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface entity, int key)
    {
        return this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindByKey(entity, key);
    }

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    public ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity)
    {
        return this.getIntegrationTierPortal().getSystemFacadeLocal().findAndForceLazyLoad(parentEntity);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindMaxVersionNumber(entity, parentKey);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindMaxPrimaryKey(entity, parentKey);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindEntityMaxPrimaryKey(entity);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindEntityMaxContainerID(entity);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindMaxLitterKey(entity, key);
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().
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
        return this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindEntityMaxMatingID(entity);
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
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindAll(entity);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().baseFindAllActive(entity);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface 
            entity, String owner)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindAllByOwner(entity, owner);
                //.baseFindAllActive(entity);

        return list;
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
    public List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface 
            entity)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindActiveStrains(entity);

        return list;
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
    public ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface 
            entity, String id)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByMouseID(entity, id);

        return list;
    }
    
     public ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface 
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByMatingID(entity, id);

        return list;
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
    public ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface 
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByContainerID(entity, id);

        return list;
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
    public ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindBySetUpVariable(entity, val);

        return list;
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
    public ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface 
            entity, String id)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByLitterID(entity, id);

        return list;
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
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface 
            entity, int id)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByContainerHistoryKey(entity, id);

        return list;
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
    public List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface 
            entity, String name)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByContainerName(entity, name);

        return list;
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
    public ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface 
            entity, String newTag)
    {
        ITBaseEntityInterface list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindByNewTag(entity, newTag);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindAllByOwners(entity, ownerLst);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllByMouseOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindAllByMouseOwners(entity, ownerLst);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllByMatingOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst)
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getSystemFacadeLocal().
                baseFindAllByMatingOwners(entity, ownerLst);

        return list;
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
    public List<ITBaseEntityInterface> baseFindAllByUser(ITBaseEntityInterface 
            entity, Integer user) throws Exception
    {
        List<ITBaseEntityInterface> list = null;

        list = this.getIntegrationTierPortal().getJCMSWebSystemFacadeLocal().
                baseFindAllByUser(entity, user);

        return list;
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public void baseSave(ITBaseEntityInterface entity) throws SaveEntityException
    {
        try
        {
            this.getIntegrationTierPortal().getSystemFacadeLocal().baseSave(entity) ;
        }
        catch ( DuplicateNameException dne )
        {
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public int saveMating(MatingEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    saveMating(entity) ;
        }
        catch ( Exception dne )
        {
            return 0;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public int saveMouse(MouseEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    saveMouse(entity) ;
        }
        catch ( Exception dne )
        {
            return 0;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public int updateGenotype(GenotypeEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    updateGenotype(entity);
        }
        catch ( Exception dne )
        {
            return 0;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public MouseEntity findMouse(int key) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findMouse(key) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public MouseEntity validDam(String id) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    validDam(id) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public MouseEntity validSire(String id) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    validSire(id) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public MouseEntity findMouseByID(String id) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findMouseByID(id);
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public GenotypeEntity findGenotype(int key) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findGenotype(key) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public int findMatingByDamAndSire(int mKey, int gKey) throws 
            Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findMatingByDamAndSire(mKey, gKey) ;
        }
        catch ( Exception dne )
        {
            return 0;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public ITBaseEntityTable findMatingByMouse(int mKey) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findMatingByMouse(mKey);
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public ITBaseEntityTable findMatingBySire(int mKey) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findMatingBySire(mKey);
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey) throws 
            Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findGenotypeByMouseAndGene(mKey, gKey) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  find the object. <p />
     * @param entity object to find
     * @throws SaveEntityException error thrown trying to find data
     * @return Entity
     */
    public ITBaseEntityTable findAllelesByGene(int key) throws Exception
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    findAllelesByGene(key) ;
        }
        catch ( Exception dne )
        {
            return null;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public int saveLitter(LitterEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getIntegrationTierPortal().getSystemFacadeLocal().
                    saveLitter(entity) ;
        }
        catch ( Exception dne )
        {
            return 0;
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public void baseCreate(ITBaseEntityInterface entity) throws SaveEntityException
    {
        int rows = 0;
        try
        {
            this.getIntegrationTierPortal().getSystemFacadeLocal().baseCreate(entity);
                System.out.println("****************   ENTITY CLASS SIMPLE NAME is " + entity.getClass().getSimpleName());
                if (entity.getClass().getSimpleName().equalsIgnoreCase("GenotypeEntity")) {
                    // Fix for defect D-02209
                    rows = new GenotypeDAO().updateAlleleConfidence((GenotypeEntity) entity);
                }
                if (entity.getClass().getSimpleName().equalsIgnoreCase("MatingEntity")) {
                    // Fix for defect D-02209
                    rows = new EditMatingDAO().updateMatingWeanTimeNeedsTyping((MatingEntity) entity);
                }
        }
        catch ( DuplicateNameException dne ) {
            
        }
        catch ( SQLException sqle) {
        }
    }
    
    /**
     * <b>Purpose:</b>  Save the object. <p />
     * @param entity object to save
     * @throws SaveEntityException error thrown trying to save data
     * @return void
     */
    public void baseEdit(ITBaseEntityInterface entity) throws SaveEntityException
    {
        try
        {
            this.getIntegrationTierPortal().getSystemFacadeLocal().baseEdit(entity) ;
        }
        catch ( DuplicateNameException dne )
        {
            
        }
    }
    
    /**
     * <b>Purpose:</b>  Delete the object. <p />
     * @param entity object to delete
     * @throws SaveEntityException error thrown trying to delete data
     * @return void
     */
    public void baseDelete(ITBaseEntityInterface entity) throws DeleteEntityException
    {
        try
        {
            this.getIntegrationTierPortal().getSystemFacadeLocal().baseDelete(entity) ;
        }
        catch (EntityNotFoundException enfe)
        {
            // Row may have already been deleted. Do nothing.
            this.getLogger().logInfo(this.formatLogMessage("Entity Not Found" + enfe, "baseDelete"));
        }
        catch (IllegalStateException ise)
        {
            throw new DeleteEntityException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logError(this.formatLogMessage(iae.getMessage(), "baseDelete"));
            throw new DeleteEntityException("Argument is not an entity bean.  " + iae);
        }
        catch (TransactionRequiredException iae)
        {
            throw new DeleteEntityException("No persistence context type TRANSACTION.  " + iae);
        }
    }

    /**
     * <b>Purpose:</b>  Save entity data and delete entity data contained in
     *      delete dto. <p />
     * @param entity object to save
     * @param deleteDTO object data to delete
     * @param newVersion indicates to create a new procedure definition version
     * @throws SaveEntityException error thrown trying to save data
     * @throws DuplicateNameException Unique name constraint violation
     * @return void
     */
    public void baseSave(ITBaseEntityInterface entity, DeleteDTO deleteDTO, Boolean newVersion) throws SaveEntityException, DuplicateNameException
    {
        //this.getIntegrationTierPortal().getMethodFacadeLocal().saveProcedureDefinitionVersion(entity, deleteDTO, newVersion) ;
    }

    /**
     * <b>Purpose:</b>  Save a list of data. <br>
     * <b>Overview:</b>  Save a list of data.  <br>
     * @param list object data to save
     * @throws SaveEntityException error thrown trying to save data
     * @throws DuplicateNameException Unique name constraint violation
     * @return void
     */
    public void baseSave(List<ITBaseEntityInterface> list) throws SaveEntityException, DuplicateNameException
    {
        if (list != null)
        {
            new IntegrationTierPortal().getSystemFacadeLocal().baseSave(list);
        }
    }

    /**
     * <b>Purpose:</b> Deletes all entity objects contained in the transfer object. <br>
     * <b>Overview:</b> This general process does not account for dependent links.
     *      Refer to more specialized methods to check for orphans prior to
     *      deleting data.  An orphan's isActive flag is disabled to represent an
     *      implicit delete.<br>
     * @param deleteDTO entities to delete
     * @throws DeleteEntityException failed to delete data
     * @return void
     */
    public void baseDelete(DeleteDTO deleteDTO) throws DeleteException
    {
        if (deleteDTO != null)
        {
            try
            {
                new IntegrationTierPortal().getSystemFacadeLocal().baseDeleteAll(deleteDTO);
            } 
            catch (DeleteEntityException ex)
            {
                String message = "Failed to delete data from the database.  " +
                        "Please report this problem to the web master with date " +
                        "and time of event.  ";
                this.getLogger().logError(this.formatLogMessage(message + ex, "baseDelete"));
                throw new DeleteException(message + ex);

            }
        }
    }

    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. <br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    public ITBaseEntityInterface baseFindDefaultEntity(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        return this.getIntegrationTierPortal().getSystemFacadeLocal().findDefaultEntity(entity);
    }

    /**
     * @return the integrationTierPortal
     */
    public IntegrationTierPortal getIntegrationTierPortal() {
        return integrationTierPortal;
    }

    /**
     * @param integrationTierPortal the integrationTierPortal to set
     */
    public void setIntegrationTierPortal(IntegrationTierPortal integrationTierPortal) {
        this.integrationTierPortal = integrationTierPortal;
    }
}