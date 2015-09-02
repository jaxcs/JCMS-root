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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.middletier.base.BTBaseFacade;
import jcms.middletier.service.FindAppService;

/**
 *
 * @author rkavitha
 */
@Stateless(name=FacadeDao.ReportFacade)
public class ReportFacade extends BTBaseFacade implements ReportFacadeLocal,
                                                        ReportFacadeRemote
{
    private FindAppService findService = new FindAppService();

    // Methods to access Report Generation information.

    /**
     *  <b>Purpose:</b>  Get the query results for active matings. <br>
     *  <b>Overview:</b>  this given method returns the query results for active matings. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMatingsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActiveMatingsQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePensQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActivePensQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active strains. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active strains. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveStrainsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActiveStrainsQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active plans. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActivePlansQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active tests. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active tests. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveTestsQueryResults() throws Exception {
        return findService.getActiveTestsQueryResults();
    }

    /**
     *  <b>Purpose:</b>  Get the query results for mouse tests scheduled. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  mouse tests scheduled. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findMouseTestsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getMouseTestsQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getLiveMiceQueryResults(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceOwners(ArrayList<OwnerEntity> ownerLst) throws 
            Exception {
        return findService.getLiveMiceOwners(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceStrainsByOwner(String owner) throws Exception {
        return findService.getLiveMiceStrainsByOwner(owner);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceStrainsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getLiveMiceStrainsByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active matings by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active matings. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMatingsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActiveMatingsByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePensByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            Exception {
        return findService.getActivePensByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePenStatus() throws Exception {
        return findService.getActivePenStatus();
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePenCount(CageSummaryDTO dto, String date)
            throws Exception {
        return findService.getCageSummaryActivePenCount(dto, date);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findPenSearchResults(PenSearchDTO dto)
            throws Exception {
        return findService.getPenSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable findMiceSearchResults(MouseSearchDTO dto)
            throws Exception {
        return findService.getMiceSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public List<MouseGenotypeDTO> findMouseGenotypeSearchResults(MouseSearchDTO 
            dto, GenotypeSearchDTO gdto) throws Exception {
        return findService.getMouseGenotypeSearchResults(dto, gdto);
    }
    
    public ITBaseEntityTable genotypeSearchResults(GenotypeSearchDTO 
            dto) throws Exception {
        return findService.genotypeSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable findMatingSearchResults(MatingSearchDTO dto)
            throws Exception {
        return findService.getMatingSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findMatings(MatingSearchDTO dto)
            throws Exception {
        return findService.getMatings(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findDamSearchResults(MouseSearchDTO dto)
            throws Exception {
        return findService.getDamSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findSireSearchResults(MouseSearchDTO dto)
            throws Exception {
        return findService.getSireSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLitterSearchResults(LitterSearchDTO dto)
            throws Exception {
        return findService.getLitterSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable findLittersSearchResults(LitterSearchDTO dto)
            throws Exception {
        return findService.getLittersSearchResults(dto);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public String findLitterGeneration(int key)
            throws Exception {
        return findService.getLitterGeneration(key);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public int findLitterStrain(int key)
            throws Exception {
        return findService.getLitterStrain(key);
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLitterInfo(String key)
            throws Exception {
        return findService.getLitterInfo(key);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public int getPenRetiredKey() throws Exception {
        return findService.getPenRetiredKey();
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActivePlansByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansCountByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActivePlanCountsByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansAndTestsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        return findService.getActivePlansAndTestsByOwner(ownerLst);
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active mouse tests by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active mouse tests. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMouseTests(ArrayList<OwnerEntity> ownerLst) throws 
            Exception {
        return findService.getActiveMouseTestsByOwner(ownerLst);
    }
}