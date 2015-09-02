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
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.dao.ColonySummaryDAO;
import jcms.integrationtier.dao.JDBCSearchDAO;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.CageSummaryDAO;
import jcms.integrationtier.dao.FilteredQueriesDAO;
import jcms.integrationtier.dao.LitterDAO;
import jcms.integrationtier.dao.LitterSearchDAO;
import jcms.integrationtier.dao.MatingSearchDAO;
import jcms.integrationtier.dao.MouseDAO;
import jcms.integrationtier.dao.MouseSearchDAO;
import jcms.integrationtier.dao.PenSearchDAO;
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.dao.WeanCardDAO;
import jcms.integrationtier.dao.MatingCardDAO;
import jcms.integrationtier.dao.TwoPenWeanCardDAO;
import jcms.integrationtier.dao.DetailCardDAO;
import jcms.integrationtier.dao.GenotypeDAO;
import jcms.integrationtier.dao.JPTDAO;
import jcms.integrationtier.dao.MatingDAO;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.JPTDTO;

/**
 * <b>File name:</b>  SystemFacade.java  <p>
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
@Stateless(name=SystemDao.SystemFacade)
public class SystemFacade extends ITBaseFacade implements SystemFacadeLocal,
                                                       SystemFacadeRemote
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 001L;
    /**
     * Creates a new instance of SystemFacade
     */
    public SystemFacade ()
    {
    }

    /**
     *  <b>Purpose:</b>  Find the root object given a key and also force the
     *      load of lazy relationships depending on the type of object. <br>
     * @param parentEntity type of object to query and holds the primary key identifier to search for
     * @return ITBaseEntityInterface ITBaseEntityInterface entity
     */
    @Override
    public ITBaseEntityInterface findAndForceLazyLoad(ITBaseEntityInterface parentEntity)
    {
        ITBaseEntityInterface entity = this.baseFind(parentEntity);

        if (entity != null)
        {

        }

        return entity;
    }

    
    /*@Override
    public ITBaseEntityTable findAllLinesJDBC()
    {
        return new SearchJDBC().getLines();
    }*/

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
    @Override
    public ITBaseEntityInterface findDefaultEntity(ITBaseEntityInterface entity)
                                                throws EntityNotFoundException
    {
        ITBaseEntityInterface rtnEntity = null;

        try
        {
            rtnEntity = entity.getClass().newInstance();

            rtnEntity = entity.getClass().cast(getEm().createNamedQuery (rtnEntity.getClass().getSimpleName() + ".findByIsDefault")
                                                      .setParameter("isDefault", SystemDao.ACTIVE)
                                                      .getSingleResult()
                                               ) ;
        }
        catch (IllegalStateException ise)
        {
            String msg = this.getClass().getSimpleName() +
                    ": findDefaultEntity: EntityNotFoundException: " +
                    "Entity Manager closed.  " + ise ;
            throw new EntityNotFoundException(msg);
        }
        catch (IllegalArgumentException iae)
        {
            String msg = this.getClass().getSimpleName() +
                    ": findDefaultEntity: IllegalArgumentException: " +
                    "Argument is not an entity bean.  " + iae ;
            throw new EntityNotFoundException(msg);
        }
        catch (InstantiationException ie)
        {
            String msg = this.getClass().getSimpleName() +
                    ": findDefaultEntity: InstantiationException: " +
                    "Error trying to instantiate class.  " + ie ;
            throw new EntityNotFoundException(msg);
        }
        catch (IllegalAccessException iace)
        {
            String msg = this.getClass().getSimpleName() +
                    ": findDefaultEntity: IllegalAccessException: " +
                    "Access violation trying to instantiate class.  " + iace;
            throw new EntityNotFoundException(msg);
        }
        catch (Exception ejbE)
        {
            String msg = this.getClass().getSimpleName() +
                    ": findDefaultEntity: Exception: " +
                    "No default record or no records at all.  " + ejbE;
            throw new EntityNotFoundException(msg);
        }

        return rtnEntity;
    }     

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
    @Override
    public List<ITBaseEntityInterface> findEnumerationClassByItem(Integer enumerationItemkey) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (List<ITBaseEntityInterface>)
                    getEm().createNamedQuery ("EnumerationClassItemEntity.findEnumerationClassByItemkey")
                             .setParameter("key", enumerationItemkey)
                             .getResultList();
            if (rtnList == null)
            {
                throw new EntityNotFoundException("No records found.");
            }
        }
        catch (IllegalStateException ise)
        {
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        catch (NoResultException nre)
        {
            throw new EntityNotFoundException("No Entity found for query.  " + nre);
        }

        return rtnList;
    }

   
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
    @Override
    public List<ITBaseEntityInterface> findEnumerationItemByClass(Integer 
            enumerationClasskey) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> rtnList = null;

        try
        {
            rtnList = (List<ITBaseEntityInterface>)
                    getEm().createNamedQuery ("EnumerationClassItemEntity.findEnumerationItemByClasskey")
                             .setParameter("key", enumerationClasskey)
                             .getResultList();
            if (rtnList == null)
            {
                throw new EntityNotFoundException("No records found.");
            }
        }
        catch (IllegalStateException ise)
        {
            throw new EntityNotFoundException("Entity Manager closed.  " + ise);
        }
        catch (IllegalArgumentException iae)
        {
            throw new EntityNotFoundException("Argument is not an entity bean.  " + iae);
        }
        catch (NoResultException nre)
        {
            throw new EntityNotFoundException("No Entity found for query.  " + nre);
        }
        return rtnList;
    }

    @Override
    public Result findColonySummaryLiveMouseJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException
    {
       return new ColonySummaryDAO().getLiveMouseQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryActivePensJDBC(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException
    {
       return new ColonySummaryDAO().getActivePensQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryActivePensByOwner(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException
    {
       return new ColonySummaryDAO().getActivePensByOwner(ownerLst);
    }

    @Override
    public Result findColonySummaryActiveStrainsJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException
    {
       return new ColonySummaryDAO().getActiveStrainsQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryActiveMatingsJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException
    {
       return new ColonySummaryDAO().getActiveMatingsQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryActivePlansJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException
    {
       return new ColonySummaryDAO().getActivePlansQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryActiveTestsJDBC() throws SQLException
    {
       return new ColonySummaryDAO().getActiveTestsQueryResults();
    }

    @Override
    public Result findColonySummaryMouseTestsJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException
    {
       return new ColonySummaryDAO().getMouseTestsQueryResults(ownerLst);
    }

    @Override
    public Result findColonySummaryMouseOwnersJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException {
       return new ColonySummaryDAO().getLiveMiceDistinctOwners(ownerLst);
    }

    @Override
    public Result findColonySummaryMouseStrainsByOwnerJDBC(String owner)
            throws SQLException
    {
       return new ColonySummaryDAO().getLiveMiceStrainsByOwner(owner);
    }

    @Override
    public Result findColonySummaryMouseStrainsByOwnerJDBC(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException {
       return new ColonySummaryDAO().getLiveMiceStrainsByOwner(ownerLst);
    }

    @Override
    public Result findColonySummaryActiveMatings(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
       return new ColonySummaryDAO().getActiveMatingsByOwner(ownerLst);
    }

    @Override
    public Result findColonySummaryActivePlans(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        return new ColonySummaryDAO().getActivePlansAndTestResults(ownerLst);
    }

    @Override
    public Result findActivePenStatus() throws SQLException {
        return new ColonySummaryDAO().getPenStatus();
    }

    @Override
    public Result findActivePenCount(CageSummaryDTO dto, String date) 
            throws SQLException {
        return new CageSummaryDAO().getActivePenCount(dto, date);
    }
    
    @Override
    public Result findPenSearchResults(PenSearchDTO dto) 
            throws SQLException {
        return new PenSearchDAO().getSearchResults(dto);
    }
    
    @Override
    public List<MouseGenotypeDTO> findMouseGenotypeSearchResults(MouseSearchDTO 
            dto, GenotypeSearchDTO gdto) throws SQLException {
        return new MouseSearchDAO().getMouseGenotypeSearchResults(dto, gdto);
    }
    
    @Override
    public ITBaseEntityTable findGenotypeSearchResults(GenotypeSearchDTO 
            dto) throws SQLException {
        return new GenotypeDAO().genotypeSearchResults(dto);
    }
    
    @Override
    public ITBaseEntityTable findMatingSearchResults(MatingSearchDTO dto) 
            throws SQLException {
        return new MatingSearchDAO().getSearchResults(dto);
    }
    
    @Override
    public int saveMating(MatingEntity mating) throws SQLException {
        return new MatingDAO().saveMating(mating);
    }
    
    @Override
    public int saveMouse(MouseEntity mouse) throws SQLException {
        return new MouseDAO().saveMouse(mouse);
    }
    
    @Override
    public MouseEntity findMouse(int key) throws SQLException {
        return new MouseDAO().findMouse(key);
    }
    
    @Override
    public MouseEntity validDam(String id) throws SQLException {
        return new MouseDAO().validDam(id);
    }
    
    @Override
    public MouseEntity validSire(String id) throws SQLException {
        return new MouseDAO().validSire(id);
    }
    
    @Override
    public MouseEntity findMouseByID(String id) throws SQLException {
        return new MouseDAO().findMouseByID(id);
    }
    
    @Override
    public GenotypeEntity findGenotype(int key) throws SQLException {
        return new GenotypeDAO().findGenotype(key);
    }
    
    @Override
    public GenotypeEntity findGenotypeByMouseAndGene(int mkey, int gkey) 
            throws SQLException {
        return new GenotypeDAO().findGenotypeByMouseAndGene(mkey, gkey);
    }
    
    @Override
    public int findMatingByDamAndSire(int dkey, int skey) 
            throws SQLException {
        return new MatingSearchDAO().findMatingByDamAndSire(dkey, skey);
    }
    
    @Override
    public ITBaseEntityTable findMatingByMouse(int dkey) throws SQLException {
        return new MatingSearchDAO().findMatingByMouse(dkey);
    }
    
    @Override
    public ITBaseEntityTable findMatingBySire(int dkey) throws SQLException {
        return new MatingSearchDAO().findMatingBySire(dkey);
    }
    
    @Override
    public ITBaseEntityTable findAllelesByGene(int key) throws SQLException {
        return new GenotypeDAO().findAllelesByGene(key);
    }
    
    @Override
    public int updateGenotype(GenotypeEntity genotype) throws SQLException {
        return new GenotypeDAO().updateGenotype(genotype);
    }
    
    @Override
    public int saveLitter(LitterEntity litter) throws SQLException {
        return new LitterDAO().saveLitter(litter);
    }
    
    @Override
    public ITBaseEntityTable findLittersSearchResults(LitterSearchDTO dto) 
            throws SQLException {
        return new LitterSearchDAO().getLitterSearchResults(dto);
    }
    
    @Override
    public Result findMatings(MatingSearchDTO dto) 
            throws SQLException {
        return new MatingSearchDAO().getMatings(dto);
    }
    
    @Override
    public ITBaseEntityTable findMiceSearchResults(MouseSearchDTO dto) 
            throws SQLException {
        return new MouseSearchDAO().getSearchResults(dto);
    }
    
    @Override
    public Result findDamSearchResults(MouseSearchDTO dto) 
            throws SQLException {
        return new MouseSearchDAO().getDams(dto);
    }
    
    @Override
    public Result findSireSearchResults(MouseSearchDTO dto) 
            throws SQLException {
        return new MouseSearchDAO().getSires(dto);
    }
    
    @Override
    public Result findLitterSearchResults(LitterSearchDTO dto) 
            throws SQLException {
        return new LitterSearchDAO().getSearchResults(dto);
    }
    
    @Override
    public String findLitterGeneration(int key) 
            throws SQLException {
        return new LitterSearchDAO().getLitterGeneration(key);
    }
        
    @Override
    public int findLitterStrain(int key) 
            throws SQLException {
        return new LitterSearchDAO().getLitterStrain(key);
    }
    
    @Override
    public Result findLitterInfo(String id) 
            throws SQLException {
        return new LitterSearchDAO().getLitterInfo(id);
    }

    @Override
    public  int getRetiredKey() throws SQLException {
        return new CageSummaryDAO().getRetiredKey();
    }

    @Override
    public Result findColonySummaryActivePlansByOwner(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException {
        return new ColonySummaryDAO().getActivePlansAndTestsByOwner(ownerLst);
    }

    @Override
    public Result findActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            SQLException {
        return new ColonySummaryDAO().getActivePlansByOwner(ownerLst);
    }

    @Override
    public Result findActiveMouseScheduledForTests(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException {
        return new ColonySummaryDAO().getMouseScheduledForTests(ownerLst);
    }

    @Override
    public ResultDTO findMouseQueryJDBC(MouseQueryDTO search) throws SQLException
    {
       return new JDBCSearchDAO().getMouseQueryResults(search);
    }

    @Override
    public void generateMouseQueryReport(MouseQueryDTO search, BufferedWriter out) throws SQLException
    {
        new JDBCSearchDAO().getMouseQueryReport(search, out);
    }

    @Override
    public ResultDTO findMatingQueryJDBC(MatingQueryDTO search) throws SQLException {
        return new JDBCSearchDAO().getMatingQueryResults(search);
    }

    @Override
    public void generateMatingQueryReport(MatingQueryDTO search, BufferedWriter out)
            throws SQLException {
        new JDBCSearchDAO().getMatingQueryReport(search, out);
    }

    @Override
    public JCMSDbInfoEntity getJCMSDbInfo()
    {
        JCMSDbInfoEntity dbInfoEntity = null;
        List<ITBaseEntityInterface> list = this.baseFindAllGlobal(new JCMSDbInfoEntity());

        if (list != null)
        {
            for (ITBaseEntityInterface entity: list)
            {
                dbInfoEntity = (JCMSDbInfoEntity) entity;
            }
        }

        return dbInfoEntity ;
    }  // Method end    
    
    public CageCardResultDTO getCageCardQueryResults(CageCardDTO dto) throws SQLException{
        if(dto.getCageCardType().equals(CageCardType.Detail)){
            return new DetailCardDAO().query(dto);
        }
        else if(dto.getCageCardType().equals(CageCardType.Mating)){
            return new MatingCardDAO().query(dto);
        }
        else if(dto.getCageCardType().equals(CageCardType.Wean)){
            return new WeanCardDAO().query(dto);
        }
        else if(dto.getCageCardType().equals(CageCardType.TwoPenWean)){
            return new TwoPenWeanCardDAO().query(dto);
        }
        else{
            System.out.println("Something went terribly wrong");
            return null;
        } 
    }
    
    @Override
    public JPTDTO generateJPTData(String mouseID, String pedigreeType, int depth, ArrayList<String> wgs){
        return new JPTDAO().generateJPTData(mouseID, pedigreeType, depth, wgs);
    }


}