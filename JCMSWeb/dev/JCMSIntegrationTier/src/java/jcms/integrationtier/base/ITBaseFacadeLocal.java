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
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.exception.CreateEntityException;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.SaveEntityException;

/**
 * <b>File name:</b>  ITBaseFacadeLocal.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Local interface offers generic CRUD actions to submit
 *      database.  <p>
 * <b>Overview:</b>  Local interface offers generic CRUD actions to submit
 *      database.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Local
public interface ITBaseFacadeLocal
{
    /**
     * <b>Purpose:</b>  Creates a ITBaseEntityInterface record. <br>
     * <b>Overview:</b>  Make a ITBaseEntityInterface instance managed and persistent.  <br>
     * @param DbInfoEntity contains ITBaseEntityInterface data.
     * @throws CreateEntityException  Unable to create database record.
     * @throws DuplicateNameException Unique name constraint violation
     */
    void baseCreate(ITBaseEntityInterface entity) throws CreateEntityException, DuplicateNameException;

    /**
     * <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     * <b>Overview:</b>  Merge the state of a ITBaseEntityInterface entity into the
     * current persistent context. <br>
     * @param entity contains ITBaseEntityInterface edits
     * @throws SaveEntityException  Unable to update database record.
     * @throws DuplicateNameException Unique name constraint violation
     */
    void baseEdit(ITBaseEntityInterface entity) throws SaveEntityException, DuplicateNameException;

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity into the
     *                    current persistent context. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     * @throws DuplicateNameException Unique name constraint violation
     */
    void baseSave(ITBaseEntityInterface entity) throws SaveEntityException, DuplicateNameException;

    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity into the
     *                    current persistent context. <br>
     *  @param list contains List<ITBaseEntityInterface> edits
     *  @throws SaveEntityException  Unable to update database record.
     * @throws DuplicateNameException Unique name constraint violation
     */
    void baseSave(List<ITBaseEntityInterface> list) throws SaveEntityException, DuplicateNameException;

    /**
     *  <b>Purpose:</b>  Process base table of objects to save and delete. <br>
     *  @param list contains List<ITBaseEntityTable>
     *  @throws SaveEntityException  Unable to update database record.
     *  @throws DeleteEntityException  Failed to delete an object
     * @throws DuplicateNameException Unique name constraint violation
     */
    public void baseSave(ITBaseEntityTable table) throws SaveEntityException, DeleteEntityException, DuplicateNameException;

    /**
     *  <b>Purpose:</b>  Find an object of parameters type. <br>
     *  <b>Overview:</b>  Find an object of parameters type using the primary key. <br>
     * @param entity Contains entity object primary key.
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the primary key parameter.
     *                                  Checked Exception.
     */
    ITBaseEntityInterface baseFind(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find an object of parameters type. <br>
     *  <b>Overview:</b>  Find an object of parameters type using the primary key. <br>
     * @param entity Contains entity object primary key.
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the primary key parameter.
     *                                  Checked Exception.
     */
    ITBaseEntityInterface baseFindByKey(ITBaseEntityInterface entity, int key)
                                                throws EntityNotFoundException;

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
    Integer baseFindMaxVersionNumber(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException ;

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
    public Integer baseFindMaxPrimaryKey(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException ;
    
    /**
     *  <b>Purpose:</b>  Find the maximum primary key for an item.  <br />
     *  <b>Overview:</b>  Provide the parent key to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findMaxPrimaryKey.
     *      Returns 0 if no records found.  <br />
     * @param entity class instance to search on
     * @param parentKey parent key value to filter by
     * @return Integer maximum version number
     */
    public Integer baseFindEntityMaxPrimaryKey(ITBaseEntityInterface entity) throws EntityNotFoundException;
    
    Integer baseFindEntityMaxContainerID(ITBaseEntityInterface entity) 
            throws EntityNotFoundException;
    
    public ITBaseEntityInterface baseFindByContainerHistoryKey(ITBaseEntityInterface 
            entity, int containerKey) throws EntityNotFoundException;
    
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
    List<ITBaseEntityInterface> baseFindAllActiveForParent(ITBaseEntityInterface entity, Integer parentKey) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all objects for base entity type. <br>
     *  <b>Overview:</b>  Find all objects for base entity type. <br>
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAll(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException;
    
    /**
     *  <b>Purpose:</b>  Find all active objects for base entity type. <br>
     *  <b>Overview:</b>  Find all active objects for base entity type. <br>
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAllActive(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all data for a given owner. <br>
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAllByOwner(ITBaseEntityInterface entity, String owner)
                                                throws EntityNotFoundException;
    
    List<ITBaseEntityInterface> baseFindActiveStrains(ITBaseEntityInterface 
            entity) throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindByNewTag(ITBaseEntityInterface 
            entity, String newTag) throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindByMouseID(ITBaseEntityInterface 
            entity, String id) throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindByMatingID(ITBaseEntityInterface 
            entity, int id) throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindByContainerID(ITBaseEntityInterface 
            entity, int containerID) throws EntityNotFoundException;
    
    List<ITBaseEntityInterface> baseFindByContainerName(ITBaseEntityInterface 
            entity, String containerName) throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindByLitterID(ITBaseEntityInterface 
            entity, String id) throws EntityNotFoundException;
     
    ITBaseEntityInterface baseFindByTheilerStage(ITBaseEntityInterface entity, int id) throws EntityNotFoundException;
    ITBaseEntityInterface baseFindByLitterType(ITBaseEntityInterface entity, String litterType) throws EntityNotFoundException;
    
    List<ITBaseEntityInterface> baseFindGenotypesByMouse(ITBaseEntityInterface entity, 
            int key) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all data for a given owner. <br>
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAllByOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all data for a given owner. <br>
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAllByMouseOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all data for a given owner. <br>
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindAllByMatingOwners(ITBaseEntityInterface entity,
            ArrayList<OwnerEntity> ownerLst) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all data for a given owner. <br>
     *  <b>Overview:</b>  Provide the owner to filter by.  The entity defines
     *      the entity type that has mutiple versions and contains the named
     *      query findAllByowner.
     *      Returns 0 if no records found.  <br />
     * @return List<ITBaseEntityInterface>  Return a list of <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     */
    List<ITBaseEntityInterface> baseFindByOwner(ITBaseEntityInterface entity, String owner)
                                                throws EntityNotFoundException;
    
    Integer baseFindEntityMaxMatingID(ITBaseEntityInterface entity) 
            throws EntityNotFoundException;
    
    List<ITBaseEntityInterface> baseFindLittersByMating(ITBaseEntityInterface entity, 
            int key) throws EntityNotFoundException;
    
    String baseFindMaxLitterID(ITBaseEntityInterface entity, int key) 
            throws EntityNotFoundException;
    
    Integer baseFindMaxLitterKey(ITBaseEntityInterface entity, int key) 
            throws EntityNotFoundException;
    
    ITBaseEntityInterface baseFindBySetUpVariable(ITBaseEntityInterface 
            entity, String val) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Delete entity object from database. <br />
     *  <b>Overview:</b>  Delete entity object from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    void baseDelete(ITBaseEntityInterface entity) throws DeleteEntityException;

    /**
     *  <b>Purpose:</b>  Delete list of entity objects from database. <br />
     *  <b>Overview:</b>  Delete list of entity objects from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    void baseDeleteAll(List<ITBaseEntityInterface> list) throws DeleteEntityException;

    /**
     *  <b>Purpose:</b>  Delete list of entity objects from database. <br />
     *  <b>Overview:</b>  Delete list of entity objects from database. <br />
     * @throws DeleteEntityException  Unable to delete database record exception.
     */
    void baseDeleteAll(DeleteDTO deleteDTO) throws DeleteEntityException;

    /**
     * Returns a generic 'Save Failed' message.
     * @return a generic 'Save Failed' message
     */
    public String getCustomizedMessage();
    
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
    public String getCustomizedMessage( DuplicateNameException dne );

}
