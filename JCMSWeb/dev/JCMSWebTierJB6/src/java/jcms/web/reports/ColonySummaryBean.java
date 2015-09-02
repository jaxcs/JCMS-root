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

package jcms.web.reports;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedMap;
import javax.faces.application.FacesMessage;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.ColonySummaryDAO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.dto.SimpleDTO;

/**
 *
 * @author rkavitha
 */
public class ColonySummaryBean extends WTBaseBackingBean implements
        Serializable {

    private static final long serialVersionUID = 0231L;

    private int numberOfLiveMice = 0;
    private int numberOfActivePens = 0;
    private int numberOfActiveMatings = 0;
    private int numberOfActiveStrains = 0;
    private int numberOfActivePlans = 0;
    private int numberOfActiveTests = 0;
    private int numberOfMouseTests = 0;
    private int numberOfOwners = 0;

    private String today = "";

    private Result owners = null;
    private Result liveMice = null;
    private Result activeMatings = null;
    private Result resultData = null;
    private Result activePlans = null;
    private Result activePlansAndTests = null;
    private Result activeTests = null;
    private Result activeMouseTests = null;

    private ReportFacadeLocal rfl;
    private ArrayList<SimpleDTO> simpleDTOLst = null;
    private ArrayList<SimpleDTO> activePensLst = null;
    private ArrayList<SimpleDTO> activePensByOwnerLst = null;
    private ArrayList<SimpleDTO> activeTestsLst = null;
    private ArrayList<SimpleDTO> activeMouseTestsLst = null;
    private ArrayList<SimpleDTO> activePlansAndTestsLst = null;
    
    private ArrayList<OwnerEntity> ownerLst = null;

    public ColonySummaryBean() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
        simpleDTOLst = new ArrayList<SimpleDTO>();
        
        // initialize the owner to logged in owner
        ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("guestOwnerEntityLst");
    }
        
    /**
     *  <b>Purpose:</b>  Run colony summary report. <br>
     *  <b>Overview:</b>  Action to run report
     *  @return String action returned to faces-config.xml
     *  @throws Exception  Unable to run report.
     */
    public String colonySummaryReportAction() {
        simpleDTOLst = new ArrayList<SimpleDTO>();
        activePensLst = new ArrayList<SimpleDTO>();
        activePensByOwnerLst = new ArrayList<SimpleDTO>();
        activeTestsLst = new ArrayList<SimpleDTO>();
        activePlansAndTestsLst = new ArrayList<SimpleDTO>();

        liveMice = null;
        activeMatings = null;
        activePlans = null;
        activePlansAndTests = null;
        activeTests = null;
        activeMouseTests = null;

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            this.today = dateFormat.format(calendar.getTime());
            this.summaryStatistics();
            this.liveMiceDetailedReport();
            this.activePensDetailedReport();
            this.activeMatingsDetailedReport();
            this.activePlansDetailedReport();
            this.activeMouseTestsDetailedReport();
            
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return "colonyReport";
    }

    public void summaryStatistics() {
        try {
            // get the result set
            this.resultData = rfl.findActiveMatingsQueryResults(this.ownerLst);
            if (resultData != null) {
                this.numberOfActiveMatings = resultData.getRowCount();
            }

            this.resultData = new ColonySummaryDAO().getActivePensForOwners(ownerLst);
            if (resultData != null) {
                this.numberOfActivePens = resultData.getRowCount();
            }

            this.resultData = rfl.findActiveStrainsQueryResults(this.ownerLst);
            if (resultData != null) {
                this.numberOfActiveStrains = resultData.getRowCount();
            }

            this.resultData = rfl.findActivePlansQueryResults(this.ownerLst);
            if (resultData != null) {
                this.numberOfActivePlans = resultData.getRowCount();
            }

            this.resultData = rfl.findActiveTestsQueryResults();
            if (resultData != null) {
                this.numberOfActiveTests = resultData.getRowCount();
            }

            this.resultData = rfl.findMouseTestsQueryResults(this.ownerLst);
            if (resultData != null) {
                this.numberOfMouseTests = resultData.getRowCount();
            }

            this.resultData = rfl.findLiveMiceQueryResults(this.ownerLst);
            if (resultData != null) {
                this.numberOfLiveMice = resultData.getRowCount();
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public Result liveMiceOwners() throws Exception {
        Result owner = rfl.findLiveMiceOwners(this.ownerLst);
        if (owner != null) {
            this.setNumberOfOwners(resultData.getRowCount());
        }
        return owner;
    }

    public void populateLiveMiceStrains() throws Exception {
        try {
            SimpleDTO dto;

            String owner = "";
            String own = "";

            long miceCnt = 0;
            int strCnt = 0;

            Result rs = null;
            SortedMap[] map = null;
            SortedMap[] miceMap = null;

            rs = this.liveMiceOwners();

            if (rs != null) {
                miceMap = rs.getRows();
            }

            rs = rfl.findLiveMiceStrainsByOwner(this.ownerLst);

            if (rs != null) {
                map = rs.getRows();
            }

            for (int m = 0; m < miceMap.length; ++m) {
                dto = new SimpleDTO();
                miceCnt = 0;
                strCnt = 0;
                own = "";

                // get the owner
                if (miceMap[m].get("owner") != null) {
                    owner = miceMap[m].get("owner").toString();
                    dto.setOwner(owner);
                }

                if (owner != null) {
                    for (int i = 0; i < map.length; ++i) {

                        if (map[i].get("owner") != null) {
                            own = map[i].get("owner").toString();
                        }

                        if (owner.equalsIgnoreCase(own)) {
                            // add strains for the same owner
                            if (map[i].get("strainName") != null) {
                                strCnt++;
                            }

                            // add mice for the same owner
                            if (map[i].get("mice") != null) {
                                miceCnt = miceCnt + (Long) map[i].get("mice");
                            }
                        }
                    }
                }
                dto.setStrainCnt(strCnt);
                dto.setLiveMiceCnt(miceCnt);

                getSimpleDTOLst().add(dto);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void populateActivePlans() {
        try {
            SimpleDTO dto;

            String owner = "";
            String own = "";
            String plan = "";
            String planName = "";
            String status = "";

            long testCnt = 0;

            Result rs = null;

            SortedMap[] map = null;
            SortedMap[] miceMap = null;

            rs = rfl.findActivePlansQueryResults(this.ownerLst);

            // get owners
            if (rs != null) {
                miceMap = rs.getRows();
            }

            rs = rfl.findActivePlansAndTestsByOwner(this.ownerLst);

            // get active mice by owner
            if (rs != null) {
                map = rs.getRows();
            }

            for (int m = 0; m < miceMap.length; ++m) {
                dto = new SimpleDTO();
                testCnt = 0;

                // set the owner first
                if (miceMap[m].get("owner") != null) {
                    owner = miceMap[m].get("owner").toString();
                    dto.setOwner(owner);
                }

                // set the plan
                if (miceMap[m].get("expPlanName") != null) {
                    plan = miceMap[m].get("expPlanName").toString();
                    dto.setExpPlanName(plan);
                }
                
                if (owner != null) {
                    for (int i = 0; i < map.length; ++i) {
                        own = "";
                        planName = "";
                        status = "";

                        if (map[i].get("owner") != null) {
                            own = map[i].get("owner").toString();
                        }

                        // add rooms for the same owner
                        if (map[i].get("expPlanName") != null) {
                            planName = map[i].get("expPlanName").toString();
                        }

                        // add active tests for the plans for the same owner
                        if (owner.equals(own) && plan.equals(planName)) {
                            if (map[i].get("testStatus") != null) {
                                status = map[i].get("testStatus").toString();

                                if (status.equalsIgnoreCase("active")) {
                                    testCnt++;
                                }
                            }
                        }
                    }
                    System.out.println("testCnt " + testCnt);
                    dto.setTestsCnt(testCnt);
                }
                this.getActivePlansAndTestsLst().add(dto);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void populateActiveMiceTests() {
        try {
            SimpleDTO dto;
            SimpleDTO testDTO;
            
            String owner = "";
            String own = "";
            String plan = "";
            String planName = "";

            long testCnt = 0;
            
            long miceCnt = 0;
            long cnt = 0;

            Result rs = null;

            SortedMap[] map = null;
            SortedMap[] miceMap = null;

            rs = rfl.findActivePlansByOwner(this.ownerLst);

            // get owners
            if (rs != null) {
                miceMap = rs.getRows();
            }

            rs = rfl.findActiveMouseTests(this.ownerLst);

            // get active mice by owner
            if (rs != null) {
                map = rs.getRows();
            }

            for (int m = 0; m < miceMap.length; ++m) {
                dto = new SimpleDTO();
                miceCnt = 0;

                // set the owner first
                if (miceMap[m].get("owner") != null) {
                    owner = miceMap[m].get("owner").toString();
                    dto.setOwner(owner);
                }

                // set the owner first
                if (miceMap[m].get("expPlanName") != null) {
                    plan = miceMap[m].get("expPlanName").toString();
                    dto.setExpPlanName(plan);
                }

                // set the owner first
                if (miceMap[m].get("tests") != null) {
                    dto.setTestsCnt((Long)miceMap[m].get("tests"));
                }

                if (owner != null) {
                    for (int i = 0; i < map.length; ++i) {
                        own = "";
                        planName = "";
                        cnt = 0;
                        
                        if (map[i].get("owner") != null) {
                            own = map[i].get("owner").toString();
                        }

                        // add rooms for the same owner
                        if (map[i].get("expPlanName") != null) {
                            planName = map[i].get("expPlanName").toString();
                        }

                        // add mice for the plans for the same owner
                        if (owner.equals(own) && plan.equals(planName)) {
                            if (map[i].get("mice") != null) {
                                cnt = (Long)map[i].get("mice");
                                miceCnt += cnt;
                            }
                        }
                    }
                    dto.setMiceCnt(miceCnt);
                }
                this.getActiveTestsLst().add(dto);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void populateActivePens() {
        try {
            SimpleDTO dto;
            SimpleDTO penDTO;

            String owner = "";
            String own = "";
            String room = "";
            String rm = "";
            String penStatus = "";
            String status = "";

            String roomColName = "room";

            long penCnt = 0;
            long penCntByOwner = 0;

            Result rs = null;

            SortedMap[] map = null;
            SortedMap[] penMap = null;
            SortedMap[] penStatusMap = null;

            rs = rfl.findActivePensByOwner(this.ownerLst);

            // get active mice by owner
            if (rs != null) {
                map = rs.getRows();
            }

            rs = rfl.findActivePensQueryResults(this.ownerLst);

            // get active pens by owner and room
            if (rs != null) {
                penMap = rs.getRows();
            }

            rs = rfl.findActivePenStatus();

            // get active pens by owner and room
            if (rs != null) {
                penStatusMap = rs.getRows();
            }
            
            for (int m = 0; m < map.length; ++m) {
                penDTO = new SimpleDTO();
                
                room = "";
                owner = "";
                penCntByOwner = 0;

                // set the owner first
                if (map[m].get("owner") != null) {
                    owner = map[m].get("owner").toString();
                    penDTO.setOwner(owner);
                }

                // add rooms for the same owner
                if (map[m].get("roomName") != null) {
                    room = map[m].get("roomName").toString();
                    penDTO.setRoom(room);
                }

                if (owner != null && room != null) {
                    for (int j = 0; j < penStatusMap.length; ++j) {
                        penCnt = 0;
                        dto = new SimpleDTO();

                        penStatus = penStatusMap[j].get("containerStatus").toString();
                        penDTO.setStatus(penStatus);

                        for (int i = 0; i < penMap.length; ++i) {
                            own = "";
                            rm = "";
                            status = "";


                            if (penMap[i].get("owner") != null) {
                                own = penMap[i].get("owner").toString();
                            }

                            if (penMap[i].get("roomName") != null) {
                                rm = penMap[i].get("roomName").toString();
                            }

                            if (penMap[i].get("containerStatus") != null) {
                                status = penMap[i].get("containerStatus").toString();
                            }

                            // add the rooms for the same owner
                            if (owner.equalsIgnoreCase(own) && room.equals(rm)
                                    && status.equals(penStatus)) {

                                dto.setOwner(own);
                                dto.setRoom(rm);
                                dto.setStatus(penStatus);
                                penCnt++;
                            }
                        }
                        dto.setPenCnt(penCnt);
                        penCntByOwner += penCnt;
                        this.getActivePensByOwnerLst().add(dto);
                    }
                }
                penDTO.setPenCntByOwner(penCntByOwner);
                this.getActivePensLst().add(penDTO);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void liveMiceDetailedReport() {
        try {
            this.populateLiveMiceStrains();
            this.setLiveMice(rfl.findLiveMiceStrainsByOwner(this.ownerLst));
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void activeMatingsDetailedReport() {
        try {
            this.setActiveMatings(rfl.findActiveMatingsByOwner(this.ownerLst));
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void activePensDetailedReport() {
        try {
            this.populateActivePens();
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void activePlansDetailedReport() {
        try {
            this.populateActivePlans();
            this.setActivePlansAndTests(rfl.findActivePlansAndTestsByOwner(
                    this.ownerLst));
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void activeMouseTestsDetailedReport() {
        try {
            this.populateActiveMiceTests();
            this.setActiveMouseTests(rfl.findActiveMouseTests(this.ownerLst));
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /**
     * @return the numberOfLiveMice
     */
    public int getNumberOfLiveMice() {
        return numberOfLiveMice;
    }

    /**
     * @param numberOfLiveMice the numberOfLiveMice to set
     */
    public void setNumberOfLiveMice(int numberOfLiveMice) {
        this.numberOfLiveMice = numberOfLiveMice;
    }

    /**
     * @return the numberOfActivePens
     */
    public int getNumberOfActivePens() {
        return numberOfActivePens;
    }

    /**
     * @param numberOfActivePens the numberOfActivePens to set
     */
    public void setNumberOfActivePens(int numberOfActivePens) {
        this.numberOfActivePens = numberOfActivePens;
    }

    /**
     * @return the numberOfActiveMatings
     */
    public int getNumberOfActiveMatings() {
        return numberOfActiveMatings;
    }

    /**
     * @param numberOfActiveMatings the numberOfActiveMatings to set
     */
    public void setNumberOfActiveMatings(int numberOfActiveMatings) {
        this.numberOfActiveMatings = numberOfActiveMatings;
    }

    /**
     * @return the numberOfActiveStrains
     */
    public int getNumberOfActiveStrains() {
        return numberOfActiveStrains;
    }

    /**
     * @param numberOfActiveStrains the numberOfActiveStrains to set
     */
    public void setNumberOfActiveStrains(int numberOfActiveStrains) {
        this.numberOfActiveStrains = numberOfActiveStrains;
    }

    /**
     * @return the numberOfActivePlans
     */
    public int getNumberOfActivePlans() {
        return numberOfActivePlans;
    }

    /**
     * @param numberOfActivePlans the numberOfActivePlans to set
     */
    public void setNumberOfActivePlans(int numberOfActivePlans) {
        this.numberOfActivePlans = numberOfActivePlans;
    }

    /**
     * @return the numberOfActiveTests
     */
    public int getNumberOfActiveTests() {
        return numberOfActiveTests;
    }

    /**
     * @param numberOfActiveTests the numberOfActiveTests to set
     */
    public void setNumberOfActiveTests(int numberOfActiveTests) {
        this.numberOfActiveTests = numberOfActiveTests;
    }

    /**
     * @return the numberOfMouseTests
     */
    public int getNumberOfMouseTests() {
        return numberOfMouseTests;
    }

    /**
     * @param numberOfMouseTests the numberOfMouseTests to set
     */
    public void setNumberOfMouseTests(int numberOfMouseTests) {
        this.numberOfMouseTests = numberOfMouseTests;
    }

    /**
     * @return the numberOfOwners
     */
    public int getNumberOfOwners() {
        return numberOfOwners;
    }

    /**
     * @param numberOfOwners the numberOfOwners to set
     */
    public void setNumberOfOwners(int numberOfOwners) {
        this.numberOfOwners = numberOfOwners;
    }

    /**
     * @return the owners
     */
    public Result getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(Result owners) {
        this.owners = owners;
    }

    /**
     * @return the LiveMice
     */
    public Result getLiveMice() {
        return liveMice;
    }

    /**
     * @param LiveMice the LiveMice to set
     */
    public void setLiveMice(Result LiveMice) {
        this.liveMice = LiveMice;
    }

    /**
     * @return the simpleDTOLst
     */
    public ArrayList<SimpleDTO> getSimpleDTOLst() {
        return simpleDTOLst;
    }

    /**
     * @param simpleDTOLst the simpleDTOLst to set
     */
    public void setSimpleDTOLst(ArrayList<SimpleDTO> simpleDTOLst) {
        this.simpleDTOLst = simpleDTOLst;
    }

    /**
     * @return the activePensLst
     */
    public ArrayList<SimpleDTO> getActivePensLst() {
        return activePensLst;
    }

    /**
     * @param activePensLst the activePensLst to set
     */
    public void setActivePensLst(ArrayList<SimpleDTO> activePensLst) {
        this.activePensLst = activePensLst;
    }

    /**
     * @return the activeMatings
     */
    public Result getActiveMatings() {
        return activeMatings;
    }

    /**
     * @param activeMatings the activeMatings to set
     */
    public void setActiveMatings(Result activeMatings) {
        this.activeMatings = activeMatings;
    }

    /**
     * @return the activePensByOwnerLst
     */
    public ArrayList<SimpleDTO> getActivePensByOwnerLst() {
        return activePensByOwnerLst;
    }

    /**
     * @param activePensByOwnerLst the activePensByOwnerLst to set
     */
    public void setActivePensByOwnerLst(ArrayList<SimpleDTO> activePensByOwnerLst) {
        this.activePensByOwnerLst = activePensByOwnerLst;
    }

    /**
     * @return the activePlans
     */
    public Result getActivePlans() {
        return activePlans;
    }

    /**
     * @param activePlans the activePlans to set
     */
    public void setActivePlans(Result activePlans) {
        this.activePlans = activePlans;
    }

    /**
     * @return the activePlansAndTests
     */
    public Result getActivePlansAndTests() {
        return activePlansAndTests;
    }

    /**
     * @param activePlansAndTests the activePlansAndTests to set
     */
    public void setActivePlansAndTests(Result activePlansAndTests) {
        this.activePlansAndTests = activePlansAndTests;
    }

    /**
     * @return the today
     */
    public String getToday() {
        return today;
    }

    /**
     * @param today the today to set
     */
    public void setToday(String today) {
        this.today = today;
    }

    /**
     * @return the activeTests
     */
    public Result getActiveTests() {
        return activeTests;
    }

    /**
     * @param activeTests the activeTests to set
     */
    public void setActiveTests(Result activeTests) {
        this.activeTests = activeTests;
    }

    /**
     * @return the activeMouseTests
     */
    public Result getActiveMouseTests() {
        return activeMouseTests;
    }

    /**
     * @param activeMouseTests the activeMouseTests to set
     */
    public void setActiveMouseTests(Result activeMouseTests) {
        this.activeMouseTests = activeMouseTests;
    }

    /**
     * @return the activeTestsLst
     */
    public ArrayList<SimpleDTO> getActiveTestsLst() {
        return activeTestsLst;
    }

    /**
     * @param activeTestsLst the activeTestsLst to set
     */
    public void setActiveTestsLst(ArrayList<SimpleDTO> activeTestsLst) {
        this.activeTestsLst = activeTestsLst;
    }

    /**
     * @return the activeMouseTestsLst
     */
    public ArrayList<SimpleDTO> getActiveMouseTestsLst() {
        return activeMouseTestsLst;
    }

    /**
     * @param activeMouseTestsLst the activeMouseTestsLst to set
     */
    public void setActiveMouseTestsLst(ArrayList<SimpleDTO> activeMouseTestsLst) {
        this.activeMouseTestsLst = activeMouseTestsLst;
    }

    /**
     * @return the activePlansAndTestsLst
     */
    public ArrayList<SimpleDTO> getActivePlansAndTestsLst() {
        return activePlansAndTestsLst;
    }

    /**
     * @param activePlansAndTestsLst the activePlansAndTestsLst to set
     */
    public void setActivePlansAndTestsLst(ArrayList<SimpleDTO> activePlansAndTestsLst) {
        this.activePlansAndTestsLst = activePlansAndTestsLst;
    }
}