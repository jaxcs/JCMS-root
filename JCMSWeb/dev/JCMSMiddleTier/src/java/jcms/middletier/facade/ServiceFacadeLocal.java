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

import java.io.BufferedWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import jcms.middletier.exception.DeleteException;
import jcms.middletier.base.BTBaseFacadeLocal;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.cagecard.dtos.returnDTO;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.JPTDTO;


/**
 * <b>File name:</b> ServiceFacadeLocal.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b> Provides methods that coordinate saving data.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Local
public interface ServiceFacadeLocal extends BTBaseFacadeLocal
{

    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseSave(ITBaseEntityInterface entity) throws SaveEntityException;
    
    public int saveMouse(MouseEntity entity) throws SaveEntityException;
    
    public MouseEntity findMouse(int key) throws Exception;
    
    public MouseEntity validDam(String id) throws Exception;
    
    public MouseEntity validSire(String id) throws Exception;
    
    public MouseEntity findMouseByID(String id) throws Exception;
    
    public int saveMating(MatingEntity entity) throws SaveEntityException;
    
    public GenotypeEntity findGenotype(int key) throws Exception;
    
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey) throws 
            Exception;
    
    public int findMatingByDamAndSire(int mKey, int gKey) throws 
            Exception;
    
    public ITBaseEntityTable findMatingByMouse(int mKey) throws Exception;
    
    public ITBaseEntityTable findMatingBySire(int mKey) throws Exception;
    
    public ITBaseEntityTable findAllelesByGene(int key) throws Exception;
    
    public int updateGenotype(GenotypeEntity entity) throws SaveEntityException;
    
    public int saveLitter(LitterEntity entity) throws SaveEntityException;
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseCreate(ITBaseEntityInterface entity) throws SaveEntityException;
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public void baseEdit(ITBaseEntityInterface entity) throws SaveEntityException;
    
    public void baseDelete(ITBaseEntityInterface entity) throws 
            DeleteEntityException;

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
    void save(ITBaseEntityTable table) throws Exception;
    
    /**
     *  <b>Purpose:</b> A multi purpose method to delete a variety of Administrative
     *      objects from the database.  <br />
     *  <b>Overview:</b> Delete redirects this object to the appropriate business
     *      method based on the object type.  For instance, an InstitutionEntity
     *      is redirected to the InstitutionEntity delete method.  <br />
     * @throws DeleteException  Unable to delete database record exception.
     */
    void delete(DeleteDTO deleteDTO) throws DeleteException;

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mouse query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public ResultDTO runMouseQuery(MouseQueryDTO query) throws SQLException;

    /**
     *  <b>Purpose:</b>  Save mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Save mouse query by initiating SearchAppService and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO, name of type String
     *  @throws Exception  Unable to save query.
     */
    public void saveMouseQuery(MouseQueryDTO query, String name,UserEntity user,
            Integer wgKey, Integer queryDefinitionKey) throws Exception;

    /**
     *  <b>Purpose:</b>  Run mating query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public ResultDTO runMatingQuery(MatingQueryDTO query) throws Exception;

    /**
     *  <b>Purpose:</b>  Save mating  query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Save mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param equery of type MatingQueryDTO
     *  @throws Exception  Unable to save query.
     */
    public void saveMatingQuery(MatingQueryDTO query, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey) throws Exception;

    public void generateMouseQueryReport(MouseQueryDTO query, BufferedWriter out) 
            throws SQLException;

    public void generateMatingQueryReport(MatingQueryDTO query, BufferedWriter out) 
            throws SQLException;

    public MouseQueryDTO loadMouseQuery(Integer key)  throws Exception;

    public MatingQueryDTO loadMatingQuery(Integer key)  throws Exception;
    
    public boolean CageCardValidation(CageCardDTO dto) throws SQLException;
    
    public returnDTO generateCageCard(CageCardDTO DTO, HttpServletResponse HSR) 
            throws Exception;
    
    public Integer saveCageCard(CageCardDTO theCard, String cardName, UserEntity user, ArrayList<Integer> wgKeys) throws Exception;
    
    public CageCardDTO loadCageCard(Integer key) throws Exception;
    
    public JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs) throws Exception;
}
