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

import javax.persistence.EntityExistsException;
import javax.transaction.RollbackException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.exception.ConstraintViolationExceptionMySqlPatch;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.middletier.exception.SaveException;
import jcms.web.base.WTBaseService;
import org.hibernate.exception.ConstraintViolationException;
import org.jax.cs.rscommon.RSUtils;

/**
 * <b>File name:</b>  ControlledVocabularyService.java  <p>
 * <b>RsDate developed:</b>  October 2009 <p>
 * <b>Purpose:</b>  Service to retrieve data by search or find.  <p>
 * <b>Overview:</b>  Provide a single point of service to all backing beans for
 *      retrieving data via search or find.  Hides logic to access the business tier
 *      and consolidates all business tier references to service classes.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2012-09-21 15:33:06 -0400 (Fri, 21 Sep 2012) $   <p>
 * @author Craig Hanna
 * @version $Revision: 17985 $
 */
public class SaveAppService extends WTBaseService
{

    public SaveAppService()
    {

    }

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseSave(ITBaseEntityInterface entity) throws SaveEntityException
    {
        try
        {
            this.getBusinessTierPortal().getServiceFacadeLocal().baseSave(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationExceptionMySqlPatch.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationExceptionMySqlPatch)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveMouse(MouseEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getBusinessTierPortal().getServiceFacadeLocal().saveMouse(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveMating(MatingEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getBusinessTierPortal().getServiceFacadeLocal().
                    saveMating(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int updateGenotype(GenotypeEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getBusinessTierPortal().getServiceFacadeLocal().
                    updateGenotype(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveLitter(LitterEntity entity) throws SaveEntityException
    {
        try
        {
            return this.getBusinessTierPortal().getServiceFacadeLocal().saveLitter(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseCreate(ITBaseEntityInterface entity) throws SaveEntityException
    {
        try
        {
            this.getBusinessTierPortal().getServiceFacadeLocal().baseCreate(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseEdit(ITBaseEntityInterface entity) throws SaveEntityException
    {
        try
        {
            this.getBusinessTierPortal().getServiceFacadeLocal().baseEdit(entity);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }

    /**
     * <b>Purpose:</b> General Save method to receive all entities, identify the
     *      type, redirect to the appropriate business method, and eventually save
     *      the information.  <br />
     * <b>Overview:</b> Data is passed as an ITBaseEntityTable object.  It
     *      encapsulates all ITBaseEntityInterface objects.  The proper class
     *      type is identified and directed to the appropriate business method.  <br />
     *      User friendly error message encapsulated in exception. <br />
     * @param table object containing a list of ITBaseEntityInterface objects
     * @throws SaveException data did not save, try again
     * @return void
     */
    public void save(ITBaseEntityTable table) throws Exception
    {
        try
        {
            this.getBusinessTierPortal().getServiceFacadeLocal().save(table);
        }
        catch ( RuntimeException re )
        {
            Throwable rollbackException            = re.getCause();             // getCause()
            Throwable entityExistsException        = null;                      // getCause().getCause()
            Throwable constraintViolationException = null;                      // getCause().getCause().getCause()
            if ( (rollbackException != null) && (rollbackException.getClass() == RollbackException.class ) ) {
                entityExistsException = rollbackException.getCause();
                if ( (entityExistsException != null) && (entityExistsException.getClass() == EntityExistsException.class) ) {
                    constraintViolationException = entityExistsException.getCause();
                    if ( (constraintViolationException != null) && (constraintViolationException.getClass() == ConstraintViolationException.class) ) {
                        ConstraintViolationExceptionMySqlPatch cvep = new ConstraintViolationExceptionMySqlPatch( (ConstraintViolationException)constraintViolationException );
                        String constraintName = cvep.getConstraintName();
                        Object[] values       = cvep.getValues();
                        String errorMessage   = RSUtils.getCustomizedMessage( constraintName, values );

                        throw new SaveException( errorMessage );
                    }
                }
            }
            throw re;                                                           // Re-throw the original exception.
        }
    }    

    /**
     *  <b>Purpose:</b> A multi purpose method to delete a variety of Administrative
     *      objects from the database.  <br />
     *  <b>Overview:</b> Delete redirects this object to the appropriate business
     *      method based on the object type.  For instance, an InstitutionEntity
     *      is redirected to the InstitutionEntity delete method.  <br />
     * @throws DeleteException  Unable to delete database record exception.
     */
    public void delete(DeleteDTO deleteDTO) throws Exception//DeleteException
    {
        this.getBusinessTierPortal().getServiceFacadeLocal().delete(deleteDTO);
    }
    
    /**
     *  <b>Purpose:</b> A multi purpose method to delete a variety of Administrative
     *      objects from the database.  <br />
     *  <b>Overview:</b> Delete redirects this object to the appropriate business
     *      method based on the object type.  For instance, an InstitutionEntity
     *      is redirected to the InstitutionEntity delete method.  <br />
     * @throws DeleteException  Unable to delete database record exception.
     */
    public void delete(ITBaseEntityInterface entity) throws Exception//DeleteException
    {
        this.getBusinessTierPortal().getServiceFacadeLocal().baseDelete(entity);
    }
}