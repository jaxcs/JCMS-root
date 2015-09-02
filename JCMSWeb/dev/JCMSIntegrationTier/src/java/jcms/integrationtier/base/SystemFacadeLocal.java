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

import java.io.BufferedWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.JPTDTO;

/**
 * <b>File name:</b>   SystemFacadeLocal.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a single access point to many general methods to get data.  <p>
 * <b>Overview:</b>  As more and more methods are added to this facade the time
 *      will come when a more logical container (facade) will be created to
 *      streamline these methods to system level methods.  System level methods
 *      should have a generic twist about them but may also reside based on the
 *      frequency of use.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Local
public interface SystemFacadeLocal extends ITBaseFacadeLocal
{
    
    /**
     *  <b>Purpose:</b>  Find the default <code>ITBaseEntityInterface</code>. <br>
     *  <b>Overview:</b>  Get the default <code>ITBaseEntityInterface</code> entity from the
     *                    current persistent context by checking the isDefault
     *                    property. Returns null if no entity found.<br>
     * @param entity  Is the object to find the default record for
     * @return ITBaseEntityInterface return <code>ITBaseEntityInterface</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    ITBaseEntityInterface findDefaultEntity(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find a <code>LineEntity</code> database table record. <br>
     *  <b>Overview:</b>  Get a <code>LineEntity</code> entity from the
     *                    current persistent context by providing a searchable item. <br>
     * @param stockNumber Contains <code>stockNumber</code> to search for
     * @return LineEntity return <code>LineEntity</code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    
    //ITBaseEntityTable findAllLinesJDBC();

    /**
     *  <b>Purpose:</b>  Find all <code>EnumerationClassItemEntity</code> items. <br>
     *  <b>Overview:</b>  Find all <code>EnumerationClassItemEntity</code> items from the
     *                    current persistent context by providing a searchable item. <br>
     * @param enumerationItemkey Contains enumeration item key to search for
     * @return List<EnumerationClassItemEntity> return <code>List<EnumerationClassItemEntity></code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    List<ITBaseEntityInterface> findEnumerationClassByItem(Integer enumerationItemkey) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all <code>EnumerationClassItemEntity</code> items. <br>
     *  <b>Overview:</b>  Find all <code>EnumerationClassItemEntity</code> items from the
     *                    current persistent context by providing a searchable item. <br>
     * @param enumerationClasskey Contains enumeration class key to search for
     * @return List<EnumerationClassItemEntity> return <code>List<EnumerationClassItemEntity></code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    List<ITBaseEntityInterface> findEnumerationItemByClass(Integer enumerationClasskey) throws EntityNotFoundException;

    /**
     *  <b>Purpose:</b>  Find all methods with associated versions. <br>
     *  <b>Overview:</b>  Get a <code>MethodVersionEntity</code> entity from the
     *                    current persistent context. <br>
     * @return List<MethodVersionEntity> return <code>List<MethodVersionEntity></code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    
    
    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity);

    Result findColonySummaryLiveMouseJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActivePensJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActivePensByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActiveStrainsJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActiveMatingsJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActivePlansJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActiveTestsJDBC() throws SQLException;

    Result findColonySummaryMouseTestsJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryMouseOwnersJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActiveMatings(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;
    
    Result findColonySummaryActivePlans(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findColonySummaryActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    Result findActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            SQLException;

    Result findActivePenStatus() throws SQLException;

    Result findActivePenCount(CageSummaryDTO dto, String date) 
            throws SQLException;
    
    Result findPenSearchResults(PenSearchDTO dto) throws SQLException;
    
    Result findLitterSearchResults(LitterSearchDTO dto) throws SQLException;
    
    ITBaseEntityTable findLittersSearchResults(LitterSearchDTO dto) 
            throws SQLException;
    
    ITBaseEntityTable findMiceSearchResults(MouseSearchDTO dto) 
            throws SQLException;
    
    String findLitterGeneration(int key) throws SQLException;
            
    int findLitterStrain(int key) throws SQLException;
    Result findLitterInfo(String key) throws SQLException;

    int getRetiredKey() throws SQLException;

    Result findColonySummaryMouseStrainsByOwnerJDBC(String owner) throws
            SQLException;

    Result findColonySummaryMouseStrainsByOwnerJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException;

    Result findActiveMouseScheduledForTests(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException;

    ResultDTO findMouseQueryJDBC(MouseQueryDTO search) throws SQLException;

    void generateMouseQueryReport(MouseQueryDTO search, BufferedWriter out) throws SQLException;

    ResultDTO findMatingQueryJDBC(MatingQueryDTO search) throws SQLException;

    void generateMatingQueryReport(MatingQueryDTO search, BufferedWriter out) throws SQLException;

    JCMSDbInfoEntity getJCMSDbInfo();

    int saveMouse(MouseEntity mouse) throws SQLException;
    
    int saveMating(MatingEntity mating) throws SQLException;
    
    int updateGenotype(GenotypeEntity genotype) throws SQLException;
    
    MouseEntity findMouse(int key) throws SQLException;
    
    MouseEntity validDam(String id) throws SQLException;
    
    MouseEntity validSire(String id) throws SQLException;
    
    public MouseEntity findMouseByID(String id) throws SQLException;
    
    GenotypeEntity findGenotype(int key) throws SQLException;
    
    GenotypeEntity findGenotypeByMouseAndGene(int mkey, int gkey) 
            throws SQLException;
    
    int findMatingByDamAndSire(int dkey, int skey) 
            throws SQLException;
    
    ITBaseEntityTable findMatingByMouse(int dkey) throws SQLException;
    
    ITBaseEntityTable findMatingBySire(int dkey) throws SQLException;
    
    ITBaseEntityTable findAllelesByGene(int key) throws SQLException;
    
    int saveLitter(LitterEntity litter) throws SQLException;
    
    Result findDamSearchResults(MouseSearchDTO dto) 
            throws SQLException;
    
    ITBaseEntityTable findMatingSearchResults(MatingSearchDTO dto) 
            throws SQLException;
    
    Result findSireSearchResults(MouseSearchDTO dto) 
            throws SQLException;
    
    List<MouseGenotypeDTO> findMouseGenotypeSearchResults(MouseSearchDTO 
            dto, GenotypeSearchDTO gdto) throws SQLException;
    
    Result findMatings(MatingSearchDTO dto) throws SQLException;
    
    CageCardResultDTO getCageCardQueryResults(CageCardDTO dto) throws SQLException;
    
    ITBaseEntityTable findGenotypeSearchResults(GenotypeSearchDTO 
            dto) throws SQLException;
    
    JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs);

    
}