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

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import jcms.middletier.base.BTBaseFacade;
import jcms.middletier.service.SaveAppService;
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
import jcms.middletier.service.SearchAppService;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.middletier.exception.DeleteException;
import jcms.middletier.service.CageCardAppService;
import jcms.cagecard.dtos.returnDTO;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.*;

/**
 * <b>File name:</b> ServiceFacade.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b> Provides methods that coordinate saving data.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Stateless(name=FacadeDao.ServiceFacade)
public class ServiceFacade extends BTBaseFacade implements ServiceFacadeLocal,
                                                         ServiceFacadeRemote
{
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public int saveMouse(MouseEntity entity) throws SaveEntityException
    {
        return new SaveAppService().saveMouse(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public int saveMating(MatingEntity entity) throws SaveEntityException
    {
        return new SaveAppService().saveMating(entity);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public MouseEntity findMouse(int key) throws Exception
    {
        return new SearchAppService().findMouse(key);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public MouseEntity validSire(String id) throws Exception
    {
        return new SearchAppService().validSire(id);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public MouseEntity validDam(String id) throws Exception
    {
        return new SearchAppService().validDam(id);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public MouseEntity findMouseByID(String id) throws Exception
    {
        return new SearchAppService().findMouseByID(id);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public GenotypeEntity findGenotype(int key) throws Exception
    {
        return new SearchAppService().findGenotype(key);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey) throws 
            Exception {
        return new SearchAppService().findGenotypeByMouseAndGene(mKey, gKey);
    }
    
    /**
     *  <b>Purpose:</b>  find an object. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public int findMatingByDamAndSire(int mKey, int gKey) throws 
            Exception {
        return new SearchAppService().findMatingByDamAndSire(mKey, gKey);
    }
    
    /**
     *  <b>Purpose:</b>  find an object. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public ITBaseEntityTable findMatingByMouse(int mKey) throws Exception {
        return new SearchAppService().findMatingByMouse(mKey);
    }
    
    /**
     *  <b>Purpose:</b>  find an object. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public ITBaseEntityTable findMatingBySire(int mKey) throws Exception {
        return new SearchAppService().findMatingBySire(mKey);
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    @Override
    public ITBaseEntityTable findAllelesByGene(int key) throws Exception
    {
        return new SearchAppService().findAllelesByGene(key);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public int updateGenotype(GenotypeEntity entity) throws SaveEntityException
    {
        return new SaveAppService().updateGenotype(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public int saveLitter(LitterEntity entity) throws SaveEntityException
    {
        return new SaveAppService().saveLitter(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public void baseSave(ITBaseEntityInterface entity) throws SaveEntityException
    {
        new SaveAppService().baseSave(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public void baseCreate(ITBaseEntityInterface entity) throws SaveEntityException
    {
        new SaveAppService().baseCreate(entity);
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    @Override
    public void baseEdit(ITBaseEntityInterface entity) throws SaveEntityException
    {
        new SaveAppService().baseEdit(entity);
    }
    
    /**
     * <b>Purpose:</b>  Delete the object. <p />
     * @param entity object to delete
     * @throws SaveEntityException error thrown trying to delete data
     * @return void
     */
    public void baseDelete(ITBaseEntityInterface entity) throws 
            DeleteEntityException {
        new SaveAppService().baseDelete(entity);
    }

    @Override
    public void save(ITBaseEntityTable table) throws Exception {
        new SaveAppService().save(table);
    }

    /**
     *  <b>Purpose:</b> A multi purpose method to delete a variety of Administrative
     *      objects from the database.  <br />
     *  <b>Overview:</b> Delete redirects this object to the appropriate business
     *      method based on the object type.  For instance, an InstitutionEntity
     *      is redirected to the InstitutionEntity delete method.  <br />
     * @throws DeleteException  Unable to delete database record exception.
     */
    @Override
    public void delete(DeleteDTO deleteDTO) throws DeleteException {
        try {
        new SaveAppService().delete(deleteDTO);
        }
        catch(Exception e) {
            
        }
    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mouse query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public ResultDTO runMouseQuery(MouseQueryDTO query) throws SQLException {
        return new SearchAppService().runMouseQuery(query);
    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mouse query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public void generateMouseQueryReport(MouseQueryDTO query, BufferedWriter out)
            throws SQLException {
        new SearchAppService().generateMouseQueryReport(query, out);
    }

    /**
     *  <b>Purpose:</b>  Save mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Save mouse query by initiating SearchAppService and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO, name of type String
     *  @throws Exception  Unable to save query.
     */
    @Override
    public void saveMouseQuery(MouseQueryDTO query, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey)
            throws Exception {
        new SearchAppService().saveMouseQuery(query, name, user, wgKey, queryDefinitionKey);
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public ResultDTO runMatingQuery(MatingQueryDTO query) throws SQLException {
        return new SearchAppService().runMatingQuery(query);
    }

    /**
     *  <b>Purpose:</b>  Save mating  query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Save mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param equery of type MatingQueryDTO
     *  @throws Exception  Unable to save query.
     */
    @Override
    public void saveMatingQuery(MatingQueryDTO query, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey)
            throws Exception {
        new SearchAppService().saveMatingQuery(query, name, user, wgKey, queryDefinitionKey);
    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mouse query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public void generateMatingQueryReport(MatingQueryDTO query,
            BufferedWriter out)
            throws SQLException {
        new SearchAppService().generateMatingQueryReport(query, out);
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public MouseQueryDTO loadMouseQuery(Integer key)  throws Exception {
        return new SearchAppService().loadMouseQuery(key);
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating SearchAppService. <br>
     *  <b>Overview:</b>  Run mating  query by initiating SearchAppService and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    @Override
    public MatingQueryDTO loadMatingQuery(Integer key)  throws Exception {
        return new SearchAppService().loadMatingQuery(key);
    }
    
    @Override
    public returnDTO generateCageCard(CageCardDTO DTO, HttpServletResponse HSR) throws Exception {
        return new CageCardAppService().generateCageCard(DTO,HSR);
    }
    
    @Override
    public boolean CageCardValidation(CageCardDTO dto) throws SQLException{
        return new CageCardAppService().CageCardValidation(dto);
    }
    
    @Override
    public Integer saveCageCard(CageCardDTO theCard, String cardName, UserEntity user, ArrayList<Integer> wgKeys) throws Exception{
        return new CageCardAppService().saveCageCard(theCard, cardName, user, wgKeys);
    }
    
    @Override
    public CageCardDTO loadCageCard(Integer key) throws Exception{
        return new CageCardAppService().loadCageCard(key);
    }
    
    @Override
    public JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs) throws Exception{
        return new SearchAppService().generateJPTData(mouseID, pedigreeType, depth, wgs);
    }
}