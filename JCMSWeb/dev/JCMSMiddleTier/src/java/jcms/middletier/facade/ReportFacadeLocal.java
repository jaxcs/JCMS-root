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
import javax.ejb.Local;
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
import jcms.middletier.base.BTBaseFacadeLocal;

/**
 *
 * @author rkavitha
 */
@Local
public interface ReportFacadeLocal extends BTBaseFacadeLocal {
    /**
     *  <b>Purpose:</b>  Get the query results for active matings. <br>
     *  <b>Overview:</b>  this given method returns the query results for active matings. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMatingsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePensQueryResults(ArrayList<OwnerEntity> ownerLst)
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active strains. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active strains. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveStrainsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active plans. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active tests. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active tests. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveTestsQueryResults() throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for mouse tests scheduled. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  mouse tests scheduled. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findMouseTestsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for live mice owners. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceOwners(ArrayList<OwnerEntity> ownerLst) throws 
            Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for live mice and strains by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceStrainsByOwner(String owner) throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for live mice and strains by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findLiveMiceStrainsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active matings by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active matings. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMatingsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active pens by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePensByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansCountByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active plans by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePlansAndTestsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePenStatus() throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActivePenCount(CageSummaryDTO dto, String date)
            throws Exception;
    
    public Result findPenSearchResults(PenSearchDTO dto)
            throws Exception;
    
    ITBaseEntityTable findMiceSearchResults(MouseSearchDTO dto)
            throws Exception;
    
    List<MouseGenotypeDTO> findMouseGenotypeSearchResults(MouseSearchDTO 
            dto, GenotypeSearchDTO gdto) throws Exception;
    
    public ITBaseEntityTable genotypeSearchResults(GenotypeSearchDTO 
            dto) throws Exception;
    
    ITBaseEntityTable findMatingSearchResults(MatingSearchDTO dto)
            throws Exception;
    
    Result findMatings(MatingSearchDTO dto) throws Exception;
    
    Result findDamSearchResults(MouseSearchDTO dto)
            throws Exception;
    
    Result findSireSearchResults(MouseSearchDTO dto)
            throws Exception;          
                
    public Result findLitterSearchResults(LitterSearchDTO dto)
            throws Exception;
    
    ITBaseEntityTable findLittersSearchResults(LitterSearchDTO dto)
            throws Exception;
    
    String findLitterGeneration(int key) throws Exception;
    
    int findLitterStrain(int key) throws Exception;
    
    public Result findLitterInfo(String key)
            throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active pen status by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pen status. <br>
     *  @throws Exception  Unable to run query.
     */
    int getPenRetiredKey() throws Exception;

    /**
     *  <b>Purpose:</b>  Get the query results for active mouse tests by owner. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active mouse tests. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result findActiveMouseTests(ArrayList<OwnerEntity> ownerLst) throws 
            Exception;
}