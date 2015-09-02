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

package jcms.web.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.MouseDAO;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.middletier.service.JCMSWebAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.FileDownloadBean;
import jcms.web.common.SelectItemWrapper;

/**
 * <b>File name:</b>  MatingQueryBean.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 *  <b>Purpose:</b>  Backing bean for Mating Query Page.  <p>
 * <b>Overview:</b> Contains the getters and setters for all display elements.
 *      Backing bean conforms to Java Bean structural design.
 *      Represents JSF components and may or may not direct JSF action calls.
 *      Contains business logic to run, save and list query objects. Also
 *      contains File download option in case user wants to download search
 *      results.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2012-07-17 17:12:11 -0400 (Tue, 17 Jul 2012) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 17319 $
 */
public class MatingQueryBean extends WTBaseBackingBean implements Serializable {
    private static final long serialVersionUID = 02323L;

    private MatingQueryDTO search; // = new MatingQueryDTO();
    private SelectItemWrapper   selectItemWrapper   = new SelectItemWrapper();

    private String queryName="";
    private ResultDTO resultData = null;
    private int rowCnt = 0;

    private ServiceFacadeLocal sfl = null;

    private String fileName = "";
    private String inputPath = "";

    private Integer queryKey;
    private String status = "";

    private Boolean isOverwrite = false;
    private Integer queryDefinitionKey = null;

    public MatingQueryBean() {
        search = new MatingQueryDTO();
        sfl = new BusinessTierPortal().getServiceFacadeLocal();
        initializeOwner();
        this.queryName = "";
    }
    
    public void deleteQueryHelper(){
        queryKey = this.getKey("udMatingQueryKey");
    }

    // initialize the owner to logged in owner
    public void initializeOwner() {
        List<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();

        if (this.getSessionParameter("guestOwnerEntityLst") != null) {
            ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("guestOwnerEntityLst");

            search.setMatingOwner(ownerLst);
        }
    }
    
    public String deleteQuery(){
        boolean theFlag = true;       
        try{
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst");
            UserEntity user = (UserEntity) getSessionParameter("userEntity");
            QueryDefinitionEntity udEntity = new JCMSWebAppService().loadQueryByKey(queryKey);
            
            for(WorkgroupEntity wge: wgLst){
                if(wge.getKey().equals(udEntity.getWorkgroupkey().getKey())){
                    System.out.println("Workgroup key: " + wge.getKey() + " ... " + 
                            "querydef wg key: " + udEntity.getWorkgroupkey().getKey());
                    theFlag = false;
                }
                else{
                    System.out.println("***Workgroup key: " + wge.getKey() + " ... " + 
                            "querydef wg key: " + udEntity.getWorkgroupkey().getKey());
                }
            }
            
            if(!theFlag){
                new JCMSWebAppService().baseDelete(udEntity);
            }
            else{
                this.addToMessageQueue("You must be member of workgroup " + 
                        udEntity.getQueryName() + " to delete that query.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("You must be member of workgroup " + 
                        udEntity.getQueryName() + " to delete that query.", "deleteQueryAction"));
            }
            
        }
        catch(Exception e){
            String msg = "The system failed on delete: ";
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteQueryAction"));
        }
        return "loadQuery";
    }
    
    public void deleteCancel(){
        this.addToMessageQueue("Delete action cancelled.", FacesMessage.SEVERITY_WARN);
    }

    /**
     *  <b>Purpose:</b>  Run mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b>  Action to run mating query by initiating MatingQueryBO
     *  and return the result set by capturing all the query information in
     *  MatingQueryDTO. <br>
     *  @return String action returned to faces-config.xml
     *  @throws Exception  Unable to run query.
     */
    public void runQueryAction() {
        try {
            // checks the valid method, if false then return INPUT
            if (!validateOutput()) {
                // Display validation information
                this.addToMessageQueue("At least one output field needs to be "
                        + "selected", FacesMessage.SEVERITY_WARN);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "At least one output field needs to be selected"));
            }
            else {
                this.resultData = sfl.runMatingQuery(search);
                if (resultData != null) {
                    this.setRowCnt(getResultData().getResult().getRowCount());
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /**
     *  Check if any of the output field is selected in the query page
     * @return boolean
     */
    public boolean validateOutput() {
        boolean flag = false;

        if (search != null) {
            if (search.isSelectBirthDates()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1DOB()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1Gen()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1Genotype()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1ID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1PlugDate()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1Stock()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam1Strain()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2DOB()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2Gen()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2Genotype()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2ID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2PlugDate()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2Stock()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDam2Strain()) {
                flag = true;
                return flag;
            }

            if (search.isSelectDateRetired()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingDates()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingGeneration()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingOwner()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingPenId()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingPenName()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingStatus()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMatingStrain()) {
                flag = true;
                return flag;
            }

            if (search.isSelectNeedsTyping()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireDOB()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireGen()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireGenotype()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireStock()) {
                flag = true;
                return flag;
            }

            if (search.isSelectSireStrain()) {
                flag = true;
                return flag;
            }

            if (search.isSelectWeanTime()) {
                flag = true;
                return flag;
            }

            if (search.isSelectTotalPupsBornDead()) {
                flag = true;
                return flag;
            }

            if (search.isSelectTotalPupsCulledAtWean()) {
                flag = true;
                return flag;
            }

            if (search.isSelectTotalPupsMissingAtWean()) {
                flag = true;
                return flag;
            }

            if (search.isSelectLitterIDs()) {
                flag = true;
                return flag;
            }
        }

        return flag;
    }

    /**
     *  <b>Purpose:</b>  Download mating query search results. <br>
     *  <b>Overview:</b>  Action to run mating query by initiating MatingQueryBO
     *  and download the result set by capturing all the query information into
     *  an excel sheet. <br>
     */
    public void downloadSearchResultsAction() {
        try {
            // Create temp file.
            File tempFile = File.createTempFile("matingQuery", ".csv");

            // Delete temp file when program exits.
            tempFile.deleteOnExit();

            // Write to temp file
            BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));

            // generate the search results report
            sfl.generateMatingQueryReport(search, out);

            int pos = 0;
            String fs = File.separator;
            inputPath = tempFile.getAbsolutePath();

            if (inputPath != null && (!inputPath.equals(""))) {
                // get the file name from file path
                pos = inputPath.lastIndexOf(fs);
            }

            if (pos > 0) {
                setFileName(getInputPath().substring(pos + 1, getInputPath().length()));
            }

            if (fileName != null && (!fileName.equals(""))) {
                new FileDownloadBean().downloadFile(this.getInputPath(),
                        this.getFileName());
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

     /**
     *  <b>Purpose:</b>  Set the mating status. <br>
     *  <b>Overview:</b>  Set the mating status to "Retired". <br>
     *  @throws Exception  Unable to run query.
     */
    public void setStatusAction() throws Exception {
        this.status = "";
                
        for (int i = 0; i < search.getCrossStatus().size(); ++i) {
            if (search.getCrossStatus().get(i).getCrossStatus().
                    equalsIgnoreCase("Retired")) {
                this.setStatus("Retired");
                break;
            }
        }
    }

    /**
     * This is invoked when CLear button on query page is clicked,
     * it resets the query page.
     */
    public String clearQueryAction() {
        search = new MatingQueryDTO();
        initializeOwner();
        this.queryName = "";
        this.isOverwrite = false;

        return "matingQuery";
    }

    /**
     *  <b>Purpose:</b>  Save mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b> Action to save mating query by initiating MatingQueryBO
     *  by capturing all the query information in MatingQueryDTO. <br>
     *  @param query of type MatingQueryDTO, name of type String.
     */
    public void saveQueryAction() {
        try {
            // checks the valid method, if false then return INPUT
            if (!validateOutput()) {
                // Display validation information
                this.addToMessageQueue("At least one output field needs to be " +
                        "selected", FacesMessage.SEVERITY_WARN);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "At least one output field needs to be selected"));
            }
            else {
                if (this.getQueryName().length() == 0) {
                    this.addToMessageQueue("Required field missing, please enter a name for this query.", FacesMessage.SEVERITY_WARN);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Required field missing, please enter a name for this query."));
                } else {
                    UserEntity user = new UserEntity();
                    WorkgroupEntity wge = new WorkgroupEntity();

                    Integer key = 0;
                    Integer wgKey = 0;

                    if (this.getSessionParameter("userEntity") != null) {
                        user = (UserEntity) getSessionParameter("userEntity");
                        key = user.getUserkey();
                        
                        this.getLogger().logWarn(this.formatLogMessage("User: " +
                                user.getUserName(), "User Key" + Integer.toString(key)));
                        this.getLogger().logWarn(this.formatLogMessage("User: " +
                                user.getFirstName(), "User Key" + Integer.toString(key)));
                        this.getLogger().logWarn(this.formatLogMessage("User: " +
                                user.getLastName(), "User Key" + Integer.toString(key)));


                        wge = user.getDefaultWorkgroupkey();

                        if (wge != null) {
                            wgKey = wge.getWorkgroupkey();
                        }
                    }
                    if (getIsOverwrite()) {
                        // duplicate query name in user's workgroup already done
                        // user has opted to overwrite the query
                        sfl.saveMatingQuery(search, this.getQueryName(), user, wgKey, this.queryDefinitionKey);
                        this.addToMessageQueue("Query saved.", FacesMessage.SEVERITY_INFO);
                    } else if (!validateQueryName(key, wgKey)) {
                        // save query only if name already doesn't exist
                        sfl.saveMatingQuery(search, this.getQueryName(), user, wgKey, null);
                        this.queryDefinitionKey = new MouseDAO().getMaxQueryDefinitionKey();
                        this.addToMessageQueue("Query saved.", FacesMessage.SEVERITY_INFO);
                    } else {
                        // set flag to true if query name already exists
                        this.addToMessageQueue("Query Name already exists, please enter a different query name or overwrite query on save.", FacesMessage.SEVERITY_WARN);
                    }
                    this.setIsOverwrite(true);
                    
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void overwriteQueryListener() {
        try {
            if (this.queryName.length() > 0) {
                UserEntity user = new UserEntity();
                WorkgroupEntity wge = new WorkgroupEntity();
                Integer key = 0;
                Integer wgKey = 0;

                if (this.getSessionParameter("userEntity") != null) {
                    user = (UserEntity) getSessionParameter("userEntity");
                    key = user.getUserkey();
                    wge = user.getDefaultWorkgroupkey();

                    if (wge != null) {
                        wgKey = wge.getWorkgroupkey();
                    }
                }

                // allow user to overwrite query or not
                if (validateQueryName(key, wgKey)) {
                    this.setIsOverwrite((Boolean) true);
                } else {
                    this.setIsOverwrite((Boolean) false);
                }
            } else {
                this.setIsOverwrite((Boolean) false);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }
    
    /**
     * Checks to see if the query name already exists in the database.
     * @return boolean flag
     * @throws Exception
     */
    protected boolean validateQueryName(Integer uKey, Integer wgKey) throws
            Exception {
        boolean flag = false;
        int i = 0;
        int key = 0;

        CvQueryTypeEntity queryType = null;
        ListSupportDTO lstDTO = new ListSupportDTO();

        List<CvQueryTypeEntity> cvQueryLst = new
                ArrayList<CvQueryTypeEntity>();
        cvQueryLst = lstDTO.getCvUserDefinedQueryType();

        // get a key for matingQuery
        for (i = 0; i < cvQueryLst.size(); ++i) {
            if (cvQueryLst.get(i).getQueryType().equalsIgnoreCase("matingquery")) {
                queryType = (CvQueryTypeEntity) cvQueryLst.get(i).
                        getEntity();
                key = queryType.getQueryTypekey();
            }
        }

        if (key > 0) {
            List<QueryDefinitionEntity> queryLst = new
                    ArrayList<QueryDefinitionEntity>();
            queryLst = lstDTO.getUserDefinedQueries();

            // check if query name already exists for the query type
            for (i = 0; i < queryLst.size(); ++i) {
                if (queryLst.get(i).getQueryTypekey() != null
                        && (queryLst.get(i).getQueryTypekey().
                        getQueryTypekey() == key)) {

                    // check if user already exists or if workgroup already
                    // exists
                    if ((queryLst.get(i).getUserkey() != null
                            && queryLst.get(i).getUserkey().compareTo(uKey) == 0)
                            || (queryLst.get(i).getWorkgroupkey() != null
                            && queryLst.get(i).getWorkgroupkey().getWorkgroupkey() != null
                            && queryLst.get(i).getWorkgroupkey().getWorkgroupkey().
                            compareTo(wgKey) == 0)) {

                        if (queryLst.get(i).getQueryName().equals(this.getQueryName())) {
                            System.out.println("Query Name already exists, please enter a different query name or opt to overwrite the query.");
                            System.out.println("Query definition key is " + queryLst.get(i).getQueryDefinitionkey());
                            this.queryDefinitionKey = queryLst.get(i).getQueryDefinitionkey();
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     *  <b>Purpose:</b>  Load mating query by initiating MatingQueryBO. <br>
     *  <b>Overview:</b> Action to load mating query by initiating MatingQueryBO
     *  by capturing all the query information in MatingQueryDTO. <br>
     */
    public String loadMatingQueryAction() {
        try {
            Integer key = this.getKey("udMatingQueryKey");
            this.search = sfl.loadMatingQuery(key);
            overwriteQueryListener();
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return "matingQuery";
    }

    /**
     * @return the search
     */
    public MatingQueryDTO getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(MatingQueryDTO search) {
        this.search = search;
    }

    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }

    /**
     * @return the queryName
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * @param queryName the queryName to set
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * @return the resultData
     */
    public ResultDTO getResultData() {
        return resultData;
    }

    /**
     * @param resultData the resultData to set
     */
    public void setResultData(ResultDTO resultData) {
        this.resultData = resultData;
    }

    /**
     * @return the rowCnt
     */
    public int getRowCnt() {
        return rowCnt;
    }

    /**
     * @param rowCnt the rowCnt to set
     */
    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the queryKey
     */
    public Integer getQueryKey() {
        return queryKey;
    }

    /**
     * @param queryKey the queryKey to set
     */
    public void setQueryKey(Integer queryKey) {
        this.queryKey = queryKey;
    }

    /**
     * @return the isOverwrite
     */
    public Boolean getIsOverwrite() {
        return isOverwrite;
    }

    /**
     * @param isOverwrite the isOverwrite to set
     */
    public void setIsOverwrite(Boolean isOverwrite) {
        this.isOverwrite = isOverwrite;
    }

    /**
     * @return the queryDefinitionKey
     */
    public Integer getQueryDefinitionKey() {
        return queryDefinitionKey;
    }

    /**
     * @param queryDefinitionKey the queryDefinitionKey to set
     */
    public void setQueryDefinitionKey(Integer queryDefinitionKey) {
        this.queryDefinitionKey = queryDefinitionKey;
    }
}