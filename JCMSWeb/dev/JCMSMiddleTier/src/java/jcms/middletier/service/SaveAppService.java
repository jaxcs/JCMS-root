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

import java.util.List;
import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TransactionRequiredException;
import jcms.middletier.base.BTBaseAppService;
import jcms.middletier.base.BTBaseBO;
import jcms.middletier.exception.SaveException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.exception.DeleteException;


/**
 * <b>File name:</b>  SaveAppService.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Validates object data and then call the proper business
 *      object to save the data.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class SaveAppService extends BTBaseAppService
{    
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveMouse(MouseEntity entity) throws SaveException
    {
        try
        {
            return new BTBaseBO().saveMouse(entity);
        }
        catch (Exception ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveMating(MatingEntity entity) throws SaveException
    {
        try
        {
            return new BTBaseBO().saveMating(entity);
        }
        catch (Exception ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int updateGenotype(GenotypeEntity entity) throws SaveException
    {
        try
        {
            return new BTBaseBO().updateGenotype(entity);
        }
        catch (Exception ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public int saveLitter(LitterEntity entity) throws SaveException
    {
        try
        {
            return new BTBaseBO().saveLitter(entity);
        }
        catch (Exception ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseSave(ITBaseEntityInterface entity) throws SaveException
    {
        try
        {
            new BTBaseBO().baseSave(entity);
        }
        catch (SaveEntityException ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseCreate(ITBaseEntityInterface entity) throws SaveException
    {
        try
        {
            new BTBaseBO().baseCreate(entity);
        }
        catch (SaveEntityException ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseEdit(ITBaseEntityInterface entity) throws SaveException
    {
        try
        {
            new BTBaseBO().baseEdit(entity);
        }
        catch (SaveEntityException ex)
            {
                String msg = "Error thrown trying to save entity.  Data did not save." ;
                this.getLogger().logError(this.formatLogMessage(msg, "save"));
                throw new SaveException(msg);
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
            new BTBaseBO().baseDelete(entity) ;
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
    public void save(ITBaseEntityTable table) throws SaveException
    {
        // General entity, no business logic checks required.
        // Save the data.
        try
        {
            new IntegrationTierPortal().getSystemFacadeLocal().baseSave(table);
        }
        catch (DeleteEntityException ex)
        {
            String msg = "Error thrown trying to save " + this.extractClassName(table.getSaveList())
                        + ".  Failed to save data." ;
            this.getLogger().logError(this.formatLogMessage(msg, "save"));
            throw new SaveException(msg);
        }
        catch (SaveEntityException ex)
        {
            String msg = "Error thrown trying to save " + this.extractClassName(table.getSaveList())
                        + ".  Failed to save data." ;
            this.getLogger().logError(this.formatLogMessage(msg, "save"));
            throw new SaveException(msg);
        }
        catch ( DuplicateNameException dne )
        {
            String msg = new ITBaseFacade().getCustomizedMessage( dne );
            throw new SaveException( msg );
        }
    }    
  
   
    /**
     * <b>Purpose:</b> Gets the class name of objects stored in the List.  <br />
     * @param List<ITBaseEntityInterface> object containing a list of ITBaseEntityInterface objects
     * @return String simple class name, empty string if no items.
     */
    private String extractClassName(List<ITBaseEntityInterface> list)
    {
        if ((list != null) && (list.size() > 0))
            return list.get(0).getClass().getSimpleName();
        else
            return "";
    }

    /**
     *  <b>Purpose:</b> A multi purpose method to delete a variety of Administrative
     *      objects from the database.  <br />
     *  <b>Overview:</b> Delete redirects this object to the appropriate business
     *      method based on the object type.  For instance, an InstitutionEntity
     *      is redirected to the InstitutionEntity delete method.  <br />
     * @throws DeleteException  Unable to delete database record exception.
     */
    public void delete(DeleteDTO deleteDTO) throws DeleteException
    {
        String message = "Error trying to delete data.  " +
            "This information was not removed from the database.  " +
            "Please report this error to the web master with date and time of " +
            "the error. " ;

        try
        {
            
        }

        catch (EJBTransactionRolledbackException ejbe)
        {
            this.getLogger().logInfo(this.formatLogMessage("EJBTransactionRolledbackException caught.  " +
                message, "delete"));
            // ACTION:  intercept this transaction and display an appropriate
            // concurrency message and throw a unique exception that the Web Tier
            // can intercept.
            throw new DeleteException(message);
        }
    }
}
