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

package jcms.integrationtier.base;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.MatingSearchDAO;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.exception.ConstraintViolationExceptionMySqlPatch;
import jcms.integrationtier.exception.CreateEntityException;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.EditEntityException;
import jcms.integrationtier.exception.SaveEntityException;
import org.hibernate.exception.ConstraintViolationException;
import org.jax.cs.rscommon.RSUtils;

/**
 * <b>File name:</b>  ITBaseFacade.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Offers generic CRUD actions to submit
 *      database.  <p>
 * <b>Overview:</b>  Offers generic CRUD actions to submit
 *      database.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class ITBaseFacade extends ITBaseObject implements ITBaseFacadeLocal, ITBaseFacadeRemote
{
    public static final long serialVersionUID = 1L;

    @PersistenceContext(unitName=ITBaseFacade.IntegrationTierPU)
    public EntityManager em;

    /**
     *  <b>Purpose:</b>  Creates a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Make a ITBaseEntityInterface managed and persistent.  <br>
     *  @param entity contains ITBaseEntityInterface data.
     *  @throws CreateEntityException  Unable to create database record.
     *  @throws DuplicateNameException Unique name constraint violation
     */
    @Override
    public void baseCreate (ITBaseEntityInterface entity) throws 
            CreateEntityException, DuplicateNameException
    {
        try {
                getEm().persist(entity);
            }
        catch (EntityExistsException eee)
        {
            String    constraintName;
            String    errorMessage;
            Object[]  values;

            Throwable t = eee.getCause();
            if ( t.getClass() == ConstraintViolationException.class ) {
                ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)t );
                constraintName = cvep.getConstraintName();
                values         = cvep.getValues();
                errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
            } else {
                constraintName = "";
                values         = new String[ 0 ];
                errorMessage   = getCustomizedMessage();
            }

            DuplicateNameException dne = new DuplicateNameException( errorMessage );

            dne.setConstraintName( constraintName );
            dne.setValues( values );

            throw dne;
        }
        catch (IllegalStateException ise)
        {
            throw new CreateEntityException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            throw new CreateEntityException("Argument is not an entity bean.  " + iae);
        }
        catch (TransactionRequiredException iae)
        {
            throw new CreateEntityException("No persistence context type TRANSACTION.  " + iae);
        }
    }   
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Merge the state of a ITBaseEntityInterface entity into the
     *                    current persistent context. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     *  @throws DuplicateNameException Unique name constraint violation
     */
    @Override
    public void baseEdit (ITBaseEntityInterface entity) throws SaveEntityException, DuplicateNameException
    {
        try
        {
            getEm().merge(entity);
        }
        catch (EntityExistsException eee)
        {
            String    constraintName;
            String    errorMessage;
            Object[]  values;

            Throwable t = eee.getCause();
            if ( t.getClass() == ConstraintViolationException.class ) {
                ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)t );
                constraintName = cvep.getConstraintName();
                values         = cvep.getValues();
                errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
            } else {
                constraintName = "";
                values         = new String[ 0 ];
                errorMessage   = getCustomizedMessage();
            }

            DuplicateNameException dne = new DuplicateNameException( errorMessage );

            dne.setConstraintName( constraintName );
            dne.setValues( values );

            throw dne;
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage("Entity not found. " +
                    entity.getClass().getName(), "baseEdit"));
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseEdit"));
            throw new SaveEntityException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseEdit"));
            throw new SaveEntityException("Argument is not an entity bean.  " + iae);
        }
        catch (TransactionRequiredException tre)
        {
            this.getLogger().logFatal(this.formatLogMessage(tre.toString(), "baseEdit"));
            throw new SaveEntityException("No persistence context type TRANSACTION.  " + tre);
        }
    }

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     *  @throws DuplicateNameException Unique name constraint violation
     */
    @Override
    public void baseSave(ITBaseEntityInterface entity) throws SaveEntityException, DuplicateNameException
    {
        try
        {
            if (entity.getKey() != null)
            {
                getLogger().logInfo(formatLogMessage( "baseSave"  , "baseEdit is executed"));
                // Save edits
                this.baseEdit(entity);
            }
            else
            {
                getLogger().logInfo(formatLogMessage( "baseSave"  , "baseCreate is executed"));
                // Create object
                this.baseCreate(entity);
            }
        }
        catch (CreateEntityException cee)
        {
            throw new SaveEntityException("Error saving data.  " + cee);
        }
        catch (EditEntityException eee)
        {
            throw new SaveEntityException("Error saving data.  " + eee);
        }
        catch (EntityExistsException eee)
        {
            String    constraintName;
            String    errorMessage;
            Object[]  values;

            Throwable t = eee.getCause();
            if ( t.getClass() == ConstraintViolationException.class ) {
                ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)t );
                constraintName = cvep.getConstraintName();
                values         = cvep.getValues();
                errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
            } else {
                constraintName = "";
                values         = new String[ 0 ];
                errorMessage   = getCustomizedMessage();
            }

            DuplicateNameException dne = new DuplicateNameException( errorMessage );

            dne.setConstraintName( constraintName );
            dne.setValues( values );

            throw dne;
        }
    }

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity into the
     *                    current persistent context. <br>
     *  @param list contains List<ITBaseEntityInterface> edits
     *  @throws SaveEntityException  Unable to update database record.
     *  @throws DuplicateNameException Unique name constraint violation
     */
    @Override
    public void baseSave(List<ITBaseEntityInterface> list) throws SaveEntityException, DuplicateNameException
    {
        if ((list != null) && (list.size() > 0))
        {
            for (ITBaseEntityInterface entity : list )
            {
                this.baseSave(entity);
            }
        }
    }

    /**
     *  <b>Purpose:</b>  Process base table of objects to save and delete. <br>
     *  @param list contains List<ITBaseEntityTable>
     *  @throws SaveEntityException  Unable to update database record.
     *  @throws DeleteEntityException  Failed to delete an object
     *  @throws DuplicateNameException Unique name constraint violation
     */
    @Override
    public void baseSave(ITBaseEntityTable table) throws SaveEntityException, DeleteEntityException, DuplicateNameException
    {
        try {
            this.baseSave(table.getSaveList());
            this.baseDeleteAll(table.getDeleteDTO());
        }
        catch (EntityExistsException eee)
        {
            String    constraintName;
            String    errorMessage;
            Object[]  values;

            Throwable t = eee.getCause();
            if ( t.getClass() == ConstraintViolationException.class ) {
                ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)t );
                constraintName = cvep.getConstraintName();
                values         = cvep.getValues();
                errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
            } else {
                constraintName = "";
                values         = new String[ 0 ];
                errorMessage   = getCustomizedMessage();
            }

            DuplicateNameException dne = new DuplicateNameException( errorMessage );

            dne.setConstraintName( constraintName );
            dne.setValues( values );

            throw dne;
        }
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
    public ITBaseEntityInterface baseFind(ITBaseEntityInterface entity) throws EntityNotFoundException
    {
        ITBaseEntityInterface rtnEntity = null;

        try
        {
            rtnEntity = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findBykey")
                          .setParameter("key", entity.getKey())
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnEntity;
    }

    /**
     *  <b>Purpose:</b>  Find the maximum version number for a parent item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findMaxVesrionNumber.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    @Override
    public Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException
    {
        Integer max = 0;

        try
        {
            max = (Integer) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxVersionNumber")
                                   .setParameter("parentKey", parentKey)
                                   .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return max;
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
    @Override
    public Integer baseFindMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException
    {
        Integer max = 0;

        try
        {
            max = (Integer) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxPrimaryKey")
                                   .setParameter("parentKey", parentKey)
                                   .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxPrimaryKey"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return max;
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
    @Override
    public Integer baseFindEntityMaxPrimaryKey(ITBaseEntityInterface entity) throws EntityNotFoundException
    {
        Integer max = 0;

        try
        {
            max = (Integer) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxPrimaryKey")
                                   .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxPrimaryKey"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return max;
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
    @Override
    public Integer baseFindEntityMaxContainerID(ITBaseEntityInterface entity) 
            throws EntityNotFoundException {
        Integer max = 0;

        try
        {
            max = (Integer) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxContainerID")
                                   .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxPrimaryKey"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxPrimaryKey"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return max;
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
    @Override
    public Integer baseFindEntityMaxMatingID(ITBaseEntityInterface entity) 
            throws EntityNotFoundException {
        Integer max = 0;

        try
        {
            max = (Integer) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxMatingID")
                                   .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxMatingID"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxMatingID"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxMatingID"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxMatingID"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        return max;
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
    @Override
    public String baseFindMaxLitterID(ITBaseEntityInterface entity, int lmatingKey) 
            throws EntityNotFoundException {
        String max = "";

        try
        {
            /*max = (String) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findMaxLitterIDByMating")
                                   .setParameter("lmatingKey", lmatingKey)
                                   .getSingleResult();*/
            return new MatingSearchDAO().findMaxLitterIDByMating(entity, lmatingKey);
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxLitterID"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxLitterID"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxLitterID"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxLitterID"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        catch (Exception iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxLitterID"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        return null;
    }
    
    public Integer baseFindMaxLitterKey(ITBaseEntityInterface entity, int lmatingKey) 
            throws EntityNotFoundException {
        Integer max = 0;

        try
        {
            return new MatingSearchDAO().findMaxLitterKeyByMating(entity, lmatingKey);
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindMaxLitterKey"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindMaxLitterKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindMaxLitterKey"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxLitterKey"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        catch (Exception iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindMaxLitterID"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        return null;
    }
    
    /**
     *  <b>Purpose:</b>  Find all active data for a parent key.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllActiveForParent.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllActiveForParent(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                   getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllActiveForParent")
                                   .setParameter("parentKey", parentKey)
                                   .getResultList();

        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface entity, String owner) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllByOwner")
                                   .setParameter("fOwner", owner)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindGenotypesByMouse(ITBaseEntityInterface entity, 
    int key) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findGenotypesByMouse")
                                   .setParameter("mKey", key)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFindGenotypesByMouse"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindGenotypesByMouse"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindGenotypesByMouse"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindGenotypesByMouse"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindGenotypesByMouse"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindLittersByMating(ITBaseEntityInterface entity, 
    int key) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findLittersByMatingkey")
                                   .setParameter("mKey", key)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFindLittersByMating"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFindLittersByMating"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFindLittersByMating"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindLittersByMating"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindLittersByMating"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface 
            entity) throws EntityNotFoundException {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByIsActive")
                                   //.setParameter("isActive", -1)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface 
            entity, int containerID) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByContainerID")
                          .setParameter("containerID", containerID)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.
                    getClass().getSimpleName() + ".findByMTSVar")
                          .setParameter("mTSVar", val)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface 
            entity, int containerKey) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByContainerHistorykey")
                          .setParameter("containerHistorykey", containerKey)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface 
            entity, String id) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByLitterID")
                          .setParameter("litterID", id)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface 
            entity, String id) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findById")
                          .setParameter("id", id)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    @Override
    public ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface 
            entity, int id) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByMatingID")
                          .setParameter("matingID", id)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface 
            entity, String containerName) throws EntityNotFoundException {
        List<ITBaseEntityInterface> list = null;

        try
        {
            list = (List<ITBaseEntityInterface>) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByContainerName")
                          .setParameter("containerName", containerName)
                          .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface 
            entity, String newTag) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByNewTag")
                                   .setParameter("newTag", newTag).getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();
        List<String> inList = new ArrayList<String>();

        try
        {
            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllByOwners")
                                   .setParameter("lOwner", inList)
                                   .getResultList();            
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllByMouseOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();
        List<String> inList = new ArrayList<String>();

        try
        {
            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllMouseStrainsByOwners")
                                   .setParameter("lOwner", inList)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllByMatingOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();
        List<String> inList = new ArrayList<String>();

        try
        {
            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllMatingStrainsByOwners")
                                   .setParameter("lOwner", inList)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
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
                                                throws EntityNotFoundException
    {
        ITBaseEntityInterface rtnEntity = null;

        try
        {
            rtnEntity = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findBykey")
                          .setParameter("key", key)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnEntity;
    }

    /**
     *  <b>Purpose:</b>  Find all data for a given owner.  <br />
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return List<ITBaseEntityInterface> list of active rows
     */
    @Override
    public List<ITBaseEntityInterface> baseFindByOwner(ITBaseEntityInterface entity, String owner) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = new ArrayList<ITBaseEntityInterface>();

        try
        {
            list = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                   getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByOwner")
                                   .setParameter("owner", owner)
                                   .getResultList();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all objects for base entity type. <br />
     *  <b>Overview:</b>  Find all objects for base entity type.
     *      Returns null if no entity found.  <br />
     * @param entity object type to retrieve
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAll(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAll")
                               .getResultList();

        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindAll"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindAll"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnList;
    }
    
    /**
     *  <b>Purpose:</b>  Find all objects for base entity type. <br />
     *  <b>Overview:</b>  Find all objects for base entity type.
     *      This method does not filter on workgroup,the entities
     *      passed in have no workgroup key
     *      Returns null if no entity found.  <br />
     * @param entity object type to retrieve
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    public List<ITBaseEntityInterface> baseFindAllGlobal(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAll")
                             .getResultList();
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindAll"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindAll"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnList;
    }
    /**
     *  <b>Purpose:</b>  Find all active objects for base entity type. <br>
     *  <b>Overview:</b>  Find all active objects for base entity type.
     *      Returns null if no entity found.  <br>
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    @Override
    public List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllActive")
                           .getResultList();
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindAllActive"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindAllActive"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnList;
    }

      /**
     *  <b>Purpose:</b>  Find all active objects for base entity type. <br>
     *  <b>Overview:</b>  Find all active objects for base entity type.
     *      Returns null if no entity found.  <br>
     *       Does not filter by workgroup, entities using this
     *      have no workgroup key.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    //@Override
    public List<ITBaseEntityInterface> baseFindAllActiveGlobal(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (java.util.List<jcms.integrationtier.base.ITBaseEntityInterface>)
                    getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findAllActive")
                           .getResultList();
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFindAllActive"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFindAllActive"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return rtnList;
    }

    /**
     *  <b>Purpose:</b>  Delete entity object from database. <br />
     *  <b>Overview:</b>  Delete entity object from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    @Override
    public void baseDelete(ITBaseEntityInterface entity) throws DeleteEntityException
    {
        try
        {
            if (entity.getKey() != null)
            {
                ITBaseEntityInterface baseEntity = null;
                baseEntity = this.baseFind(entity);

                if (baseEntity != null)
                {
                    getEm().remove(baseEntity);
                }
            }
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
     *  <b>Purpose:</b>  Delete list of entity objects from database. <br />
     *  <b>Overview:</b>  Delete list of entity objects from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    @Override
    public void baseDeleteAll(List<ITBaseEntityInterface> list) throws DeleteEntityException
    {
            for (ITBaseEntityInterface entity : list )
            {
                this.baseDelete(entity);
            }
    }

    /**
     *  <b>Purpose:</b>  Delete list of entity objects from database. <br />
     *  <b>Overview:</b>  Delete list of entity objects from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    @Override
    public void baseDeleteAll(DeleteDTO deleteDTO) throws DeleteEntityException
    {
        if (deleteDTO != null)
        {
            List<ITBaseEntityInterface> list = deleteDTO.getDeleteList();

            for (ITBaseEntityInterface entity : list )
            {
                if (entity.getKey() != null)
                    this.baseDelete(entity);
            }

        }
    }

    @Override
    public ITBaseEntityInterface baseFindByLitterType(ITBaseEntityInterface entity, String id) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByLitterType")
                          .setParameter("litterType", id)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }
    
    @Override
    public ITBaseEntityInterface baseFindByTheilerStage(ITBaseEntityInterface entity, int id) throws EntityNotFoundException {
        ITBaseEntityInterface list = null;

        try
        {
            list = (ITBaseEntityInterface) getEm().createNamedQuery (entity.getClass().getSimpleName() + ".findByTheilerStage")
                          .setParameter("theilerStage", id)
                          .getSingleResult();
        }
        catch (NoResultException nre)
        {
            this.getLogger().logWarn(this.formatLogMessage("Did not find entity for key "+ entity.getKey() +
                    "\n\tClass type "+ entity.getClass().getName(), "baseFind"));
            this.getLogger().logWarn(this.formatLogMessage(nre.toString(), "baseFind"));
        }
        catch (EntityNotFoundException enfe)
        {
            this.getLogger().logWarn(this.formatLogMessage(enfe.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + enfe);
        }
        catch (IllegalStateException ise)
        {
            this.getLogger().logFatal(this.formatLogMessage(ise.toString(), "baseFind"));
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            this.getLogger().logFatal(this.formatLogMessage(iae.toString(), "baseFind"));
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }

        return list;
    }

    
    /**
     * Returns a generic 'Save Failed' message.
     * @return a generic 'Save Failed' message
     */
    @Override public String getCustomizedMessage() {
        return RSUtils.getCustomizedMessage( null, null );
    }

    /**
     * Given a DuplicateNameException, this method creates the customized message
     * using the constraintName and the values that caused the constraint violation,
     * both embedded within the DuplicateNameException. If the exception definition
     * is found in CustomizedMessages.properties, that customized message is
     * returned. If it is not found, a generic 'Save Failed' message is returned
     * with time and date.
     * @param dne the DuplicateNameException that was thrown
     * @return the customized error message, suitable for display to the user.
     */
    @Override public String getCustomizedMessage( DuplicateNameException dne ) {
        return RSUtils.getCustomizedMessage( dne.getConstraintName(), dne.getValues() );
    }

    // protected methods

    /**
     * @return the em
     */
    protected EntityManager getEntityManager()
    {
        return this.getEm();
    }

    /**
     * @return the em
     */
    protected EntityManager getEm() {
        return em;
    }
}