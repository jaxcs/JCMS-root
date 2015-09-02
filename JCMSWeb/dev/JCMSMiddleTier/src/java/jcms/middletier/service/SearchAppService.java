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

import java.io.BufferedWriter;
import java.util.ArrayList;
import jcms.middletier.businessobject.MatingSearchBO;
import jcms.middletier.businessobject.MouseSearchBO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.middletier.base.BTBaseAppService;
import jcms.middletier.exception.SaveException;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.JPTDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.exception.DeleteEntityException;
import jcms.integrationtier.exception.DuplicateNameException;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.base.BTBaseBO;
import jcms.middletier.businessobject.JPTBO;

/**
 * <b>File name:</b>  SearchAppService.java  <p>
 * <b>Date developed:</b>  September 2010 <p>
 * <b>Purpose:</b>  Contains finder methods for selecting data spanning the system.  <p>
 * <b>Overview:</b>  Contains finder methods for selecting data spanning the system.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class SearchAppService extends BTBaseAppService {
    
    public SearchAppService() {

    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b>  Run mouse query by initiating MouseQueryBO and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public ResultDTO runMouseQuery(MouseQueryDTO query) {
        try {
            return new MouseSearchBO().runQuery(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b>  Run mouse query by initiating MouseQueryBO and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public void generateMouseQueryReport(MouseQueryDTO query, BufferedWriter out) {
        try {
            new MouseSearchBO().generateReport(query, out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Save mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b>  Save mouse query by initiating MouseQueryBO and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO, name of type String
     *  @throws Exception  Unable to save query.
     */
    public void saveMouseQuery(MouseQueryDTO query, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey) {
        try {
            new MouseSearchBO().saveQuery(query, name, user, wgKey, queryDefinitionKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());            
        }
    }       
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public MouseEntity findMouse(int key) throws SaveException
    {
        try
        {
            return new BTBaseBO().findMouse(key);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public MouseEntity validDam(String id) throws SaveException
    {
        try
        {
            return new BTBaseBO().validDam(id);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public MouseEntity validSire(String id) throws SaveException
    {
        try
        {
            return new BTBaseBO().validSire(id);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    /**
     *  <b>Purpose:</b>  Update a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  Update the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface edits
     *  @throws SaveEntityException  Unable to update database record.
     */
    public MouseEntity findMouseByID(String id) throws SaveException
    {
        try
        {
            return new BTBaseBO().findMouseByID(id);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public GenotypeEntity findGenotype(int key) throws SaveException
    {
        try
        {
            return new BTBaseBO().findGenotype(key);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey)
            throws SaveException {
        try {
            return new BTBaseBO().findGenotypeByMouseAndGene(mKey, gKey);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public int findMatingByDamAndSire(int mKey, int gKey)
            throws SaveException {
        try {
            return new BTBaseBO().findMatingByDamAndSire(mKey, gKey);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public ITBaseEntityTable findMatingByMouse(int mKey) throws SaveException {
        try {
            return new BTBaseBO().findMatingByMouse(mKey);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public ITBaseEntityTable findMatingBySire(int mKey) throws SaveException {
        try {
            return new BTBaseBO().findMatingBySire(mKey);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  find a ITBaseEntityInterface record. <br>
     *  <b>Overview:</b>  find the state of a ITBaseEntityInterface entity. <br>
     *  @param entity contains ITBaseEntityInterface find
     *  @throws SaveEntityException  Unable to find database record.
     */
    public ITBaseEntityTable findAllelesByGene(int key) throws SaveException
    {
        try
        {
            return new BTBaseBO().findAllelesByGene(key);
        }
        catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b>  Run mating  query by initiating MatingQueryBO and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public ResultDTO runMatingQuery(MatingQueryDTO query) {
        try {
            return new MatingSearchBO().runQuery(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Save mating  query by initiating Mating QueryBO. <br>
     *  <b>Overview:</b>  Save mating  query by initiating Mating QueryBO and
     *  return the result set. <br>
     *  @param equery of type MatingQueryDTO
     *  @throws Exception  Unable to save query.
     */
    public void saveMatingQuery(MatingQueryDTO query, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey) {
        try {
            new MatingSearchBO().saveQuery(query, name, user, wgKey, queryDefinitionKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    public void saveQuery(ITBaseEntityTable table) throws SaveException
    {
        // General entity, no business logic checks required.
        // Save the data.
        try
        {
            new IntegrationTierPortal().getJCMSWebSystemFacadeLocal().
                    baseSave(table);
        }
        catch (DeleteEntityException ex)
        {
            String msg = "Error thrown trying to save Mouse/Mating query"
                        + ".  Failed to save data." ;
            this.getLogger().logError(this.formatLogMessage(msg, "save"));
            throw new SaveException(msg);
        }
        catch (SaveEntityException ex)
        {
            String msg = "Error thrown trying to save Mouse/Mating query." +
                    " Failed to save data." ;
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
     *  <b>Purpose:</b>  Run mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b>  Run mouse query by initiating MouseQueryBO and return
     *  the result set. <br>
     *  @param query of type MouseQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public void generateMatingQueryReport(MatingQueryDTO query, BufferedWriter
            out) {
        try {
            new MatingSearchBO().generateReport(query, out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b>  Run mating  query by initiating MatingQueryBO and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public MatingQueryDTO loadMatingQuery(Integer key) {
        try {
            return new MatingSearchBO().loadQuery(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b>  Run mating  query by initiating MatingQueryBO and
     *  return the result set. <br>
     *  @param query of type MatingQueryDTO
     *  @throws Exception  Unable to run query.
     */
    public MouseQueryDTO loadMouseQuery(Integer key) {
        try {
            return new MouseSearchBO().loadQuery(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs){
        try{
            return new JPTBO().generateJPTData(mouseID, pedigreeType, depth, wgs);
        }
        catch(Exception e){
            System.out.println("Exception generating JPT Data: " + e);
            return null;
        }
    }
}