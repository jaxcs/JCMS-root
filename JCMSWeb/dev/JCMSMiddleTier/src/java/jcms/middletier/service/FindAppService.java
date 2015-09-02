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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.SystemFacadeLocal;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.middletier.base.BTBaseAppService;
import jcms.middletier.businessobject.JCMSBO;

/**
 * <b>File name:</b>  FindAppService.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains finder methods for selecting data spanning the system.  <p>
 * <b>Overview:</b>  Contains finder methods for selecting data spanning the system.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class FindAppService extends BTBaseAppService {

    private SystemFacadeLocal sfl = new JCMSBO().getIntegrationTierPortal().
            getSystemFacadeLocal();

    // CUSTOM FINDERS

    /**
     *  <b>Purpose:</b>  Get the query results for active matings. <br>
     *  <b>Overview:</b>  this given method returns the query results for active matings. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActiveMatingsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActiveMatingsJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for 
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePensQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActivePensJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePenStatus() throws Exception {
        try {
            return sfl.findActivePenStatus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getCageSummaryActivePenCount(CageSummaryDTO dto, 
            String date) throws Exception {
        try {
            return sfl.findActivePenCount(dto, date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getPenSearchResults(PenSearchDTO dto) throws Exception {
        try {
            return sfl.findPenSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable getMiceSearchResults(MouseSearchDTO dto) throws Exception {
        try {
            return sfl.findMiceSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public List<MouseGenotypeDTO> getMouseGenotypeSearchResults(MouseSearchDTO 
            dto, GenotypeSearchDTO gdto) throws Exception {
        try {
            return sfl.findMouseGenotypeSearchResults(dto, gdto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable genotypeSearchResults(GenotypeSearchDTO 
            dto) throws Exception {
        try {
            return sfl.findGenotypeSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable getMatingSearchResults(MatingSearchDTO dto) 
            throws Exception {
        try {
            return sfl.findMatingSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getMatings(MatingSearchDTO dto) 
            throws Exception {
        try {
            return sfl.findMatings(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getDamSearchResults(MouseSearchDTO dto) throws Exception {
        try {
            return sfl.findDamSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getSireSearchResults(MouseSearchDTO dto) throws Exception {
        try {
            return sfl.findSireSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLitterSearchResults(LitterSearchDTO dto) throws Exception {
        try {
            return sfl.findLitterSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public ITBaseEntityTable getLittersSearchResults(LitterSearchDTO dto) throws Exception {
        try {
            return sfl.findLittersSearchResults(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public String getLitterGeneration(int key) throws Exception {
        try {
            return sfl.findLitterGeneration(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public int getLitterStrain(int key) throws Exception {
        try {
            return sfl.findLitterStrain(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLitterInfo(String key) throws Exception {
        try {
            return sfl.findLitterInfo(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active pens. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  active pens. <br>
     *  @throws Exception  Unable to run query.
     */
    public int getPenRetiredKey() throws Exception {
        try {
            return sfl.getRetiredKey();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active strains. <br>
     *  <b>Overview:</b>  this given method returns the query results for 
     *  active strains. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActiveStrainsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActiveStrainsJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active plans. <br>
     *  <b>Overview:</b>  this given method returns the query results for 
     *  active plans. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePlansQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActivePlansJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for active tests. <br>
     *  <b>Overview:</b>  this given method returns the query results for 
     *  active tests. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActiveTestsQueryResults() throws Exception {
        try {
            return sfl.findColonySummaryActiveTestsJDBC();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for mouse tests scheduled. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  mouse tests scheduled. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getMouseTestsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryMouseTestsJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLiveMiceQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryLiveMouseJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

     /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLiveMiceOwners(ArrayList<OwnerEntity> ownerLst) throws 
            Exception {
        try {
            return sfl.findColonySummaryMouseOwnersJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

     /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLiveMiceStrainsByOwner(String owner) throws Exception {
        try {
            return sfl.findColonySummaryMouseStrainsByOwnerJDBC(owner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getLiveMiceStrainsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryMouseStrainsByOwnerJDBC(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActiveMatingsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActiveMatings(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePensByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            Exception {
        try {
            return sfl.findColonySummaryActivePensByOwner(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActivePlans(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePlanCountsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findActivePlansByOwner(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActivePlansAndTestsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findColonySummaryActivePlansByOwner(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  <b>Purpose:</b>  Get the query results for live mice. <br>
     *  <b>Overview:</b>  this given method returns the query results for
     *  live mice. <br>
     *  @throws Exception  Unable to run query.
     */
    public Result getActiveMouseTestsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws Exception {
        try {
            return sfl.findActiveMouseScheduledForTests(ownerLst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}