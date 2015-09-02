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
import javax.faces.model.SelectItem;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.MouseDAO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.service.JCMSWebAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.FileDownloadBean;
import jcms.web.common.MouseScheduleUtilities;
import jcms.web.common.PhenotypeUtilities;
import org.primefaces.model.DualListModel;  

/**
 * <b>File name:</b>  MouseQueryBean.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 *  <b>Purpose:</b>  Backing bean for Mouse Query Page.  <p>
 * <b>Overview:</b> Contains the getters and setters for all display elements.
 *      Backing bean conforms to Java Bean structural design.
 *      Represents JSF components and may or may not direct JSF action calls.
 *      Contains business logic to run, save and list query objects. Also
 *      contains File download option in case user wants to download search
 *      results.  <p>
 * <b>Last changed by:</b>   $Author: cnh $ <p>
 * <b>Last changed date:</b> $Date: 2012-12-05 14:58:23 -0500 (Wed, 05 Dec 2012) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 18833 $
 */
public class MouseQueryBean extends WTBaseBackingBean implements Serializable {
    private static final long serialVersionUID = 00231L;

    private MouseQueryDTO search; // = new MouseQueryDTO();
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private ServiceFacadeLocal sfl;
    private String queryName = "";
    private ResultDTO resultData = null;
    private int rowCnt = 0;
    private String fileName = "";
    private String inputPath = "";
    private String owner = "";
    private Integer queryKey;
    private Boolean isOverwrite = false;
    private Integer queryDefinitionKey = null;
    private DualListModel lifeStatuses = new DualListModel();
    private DualListModel origins = new DualListModel();
    private DualListModel strains = new DualListModel();
    private DualListModel generations = new DualListModel();
    private DualListModel owners = new DualListModel();
    private DualListModel CODs = new DualListModel();
    private DualListModel rooms = new DualListModel();
    private DualListModel useSchedules = new DualListModel();
    private DualListModel mousePhenotypes = new DualListModel();
    private DualListModel mouseUses = new DualListModel();
    private DualListModel genes = new DualListModel();
    private DualListModel protocolIDs = new DualListModel();

    public MouseQueryBean() {
        search = new MouseQueryDTO();
        sfl = new BusinessTierPortal().getServiceFacadeLocal();
        initializeOwner();
        setupDualListModels();
        this.queryName = "";
    }
    
    private void setupDualListModels(){
        try{
            //rooms
            ArrayList<RoomEntity> theRooms = new ArrayList<RoomEntity>();
            for(SelectItem si : selectItemWrapper.getRoomItems()){
                //RoomEntity e = (RoomEntity) si.getValue();
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theRooms.add((RoomEntity) si.getValue());
                }
            }
            rooms.setSource(theRooms);
            rooms.setTarget(new ArrayList());

            //life statuses
            ArrayList<LifeStatusEntity> theLifeStatuses = new ArrayList<LifeStatusEntity>();
            for(SelectItem si : selectItemWrapper.getCvLifeStatusItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theLifeStatuses.add((LifeStatusEntity) si.getValue());
                }
            }
            lifeStatuses.setSource(theLifeStatuses);
            lifeStatuses.setTarget(new ArrayList());

            //origins
            ArrayList<CvMouseOriginEntity> theOrigins = new ArrayList<CvMouseOriginEntity>();
            for(SelectItem si : selectItemWrapper.getCvMouseOriginItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theOrigins.add((CvMouseOriginEntity) si.getValue());
                }
            }
            origins.setSource(theOrigins);
            origins.setTarget(new ArrayList());

            //strains
            ArrayList<StrainEntity> theStrains = new ArrayList<StrainEntity>();
            for(SelectItem si : selectItemWrapper.getMouseStrainItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theStrains.add((StrainEntity) si.getValue());
                }
            }
            strains.setSource(theStrains);
            strains.setTarget(new ArrayList());
            
            //generations
            ArrayList<CvGenerationEntity> theGenerations = new ArrayList<CvGenerationEntity>();
            for(SelectItem si : selectItemWrapper.getCvGenerationItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theGenerations.add((CvGenerationEntity) si.getValue());
                }
            }
            generations.setSource(theGenerations);
            generations.setTarget(new ArrayList());
            
            //owners
            ArrayList<OwnerEntity> theOwners = (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");
            owners.setSource(theOwners);
            owners.setTarget(new ArrayList());
            
            //CODs
            ArrayList<CvCauseOfDeathEntity> theCODs = new ArrayList<CvCauseOfDeathEntity>();
            for(SelectItem si : selectItemWrapper.getCvCauseOfDeathItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theCODs.add((CvCauseOfDeathEntity) si.getValue());
                }
            }
            CODs.setSource(theCODs);
            CODs.setTarget(new ArrayList());
            
            //Protocol IDs
            ArrayList<CvMouseProtocolEntity> theProtocols = new ArrayList<CvMouseProtocolEntity>();
            for(SelectItem si : selectItemWrapper.getCvMouseProtocolItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theProtocols.add((CvMouseProtocolEntity) si.getValue());
                }
            }
            protocolIDs.setSource(theProtocols);
            protocolIDs.setTarget(new ArrayList());
            
            //use schedules
            ArrayList<UseScheduleTermDTO> theUseSchedules = new ArrayList<UseScheduleTermDTO>();
            for(UseScheduleTermDTO dto : new MouseScheduleUtilities().getpDAO().getUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("guestWorkgroupEntityLst"))){
                theUseSchedules.add(dto);
            }
            useSchedules.setSource(theUseSchedules);
            useSchedules.setTarget(new ArrayList<UseScheduleTermDTO>());
            
            //Mouse Uses
            ArrayList<CvMouseUseEntity> theUses = new ArrayList<CvMouseUseEntity>();
            for(SelectItem si : selectItemWrapper.getCvMouseUseItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theUses.add((CvMouseUseEntity) si.getValue());
                }
            }
            mouseUses.setSource(theUses);
            mouseUses.setTarget(new ArrayList());
            
            //phenotypes
            ArrayList<cvPhenotypeTermDTO> thePhenotypes = new ArrayList<cvPhenotypeTermDTO>();
            for(cvPhenotypeTermDTO dto : new PhenotypeUtilities().getptDAO().getCvPhenotypeTerms()){
                thePhenotypes.add(dto);
            }
            
            mousePhenotypes.setSource(thePhenotypes);
            mousePhenotypes.setTarget(new ArrayList<cvPhenotypeTermDTO>());
            
            //Genes
            ArrayList<GeneEntity> theGenes = new ArrayList<GeneEntity>();
            for(SelectItem si : selectItemWrapper.getGenesItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theGenes.add((GeneEntity) si.getValue());
                }
            }
            getGenes().setSource(theGenes);
            getGenes().setTarget(new ArrayList());
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error populating DualListModels: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }

    // initialize the owner to logged in owner
    public void initializeOwner() {
        List<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst");
        search.setOwner(ownerLst);
    }
    
    public void deleteQueryHelper(){
        queryKey = this.getKey("udMouseQueryKey");
    }

    /**
     *  <b>Purpose:</b>  Run mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b>  Action to run mouse query by initiating MouseQueryBO
     *  and return the result set by capturing all the query information in
     *  MouseQueryDTO. <br>
     *  @return String action returned to faces-config.xml
     *  @throws Exception  Unable to run query.
     */
    public void runQueryAction() {
        try {
            // checks the valid method, if false then return INPUT
            if (!validateOutput()) {
                // Display validation information
                this.addToMessageQueue("At least one output field needs to be selected", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "At least one output field needs to be selected"));
            } else {
                initializeSearch();
                // get the result set
                this.resultData = sfl.runMouseQuery(search);

                if (resultData != null) {
                    this.setRowCnt(resultData.getResult().getRowCount());
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }
    
    private void initializeSearch(){
        search.setRooms(rooms.getTarget());
        search.setLifeStatus(lifeStatuses.getTarget());
        search.setProtocolID(protocolIDs.getTarget());
        search.setOrigin(origins.getTarget());
        search.setStrain(strains.getTarget());
        search.setGeneration(generations.getTarget());
        if(!owners.getTarget().isEmpty()){
            search.setOwner(owners.getTarget());
        }
        else{
            search.setOwner(owners.getSource());
        }
        search.setCauseOfDeath(CODs.getTarget());
        search.setUseScheduleTerms(useSchedules.getTarget());
        search.setPhenotypeTerms(mousePhenotypes.getTarget());
        search.setMouseUse(mouseUses.getTarget());
        search.setGenotype(getGenes().getTarget());
        search.setColonyManageOwners(this.getCurrentUserColonyManageWorkgroups());
    }

    /**
     *  Check if any of the output field is selected in the query page
     * @return boolean
     */
    public boolean validateOutput() {
        boolean flag = false;

        if (search != null) {
            if (search.isSelectMouseAge()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseBreedingStatus()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseCOD()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseCoatColor()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseDOB()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseDiet()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseExitDate()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseGeneration()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseGenotype()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseGenotypeDate()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseLifeStatus()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseLitter()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseLitterMates()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseMating()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseOrigin()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseOwner()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseParents()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMousePenID()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMousePenName()) {
                flag = true;
                return flag;
            }

            if (search.isSelectUseSchedules()) {
                flag = true;
                return flag;
            }
            
            if (search.isSelectMousePhenotypes()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseSex()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseStrain()) {
                flag = true;
                return flag;
            }

            if (search.isSelectMouseUse()) {
                flag = true;
                return flag;
            }
            
            if(search.isSelectRoomName()){
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    /**
     *  <b>Purpose:</b>  Download mouse query search results. <br>
     *  <b>Overview:</b>  Action to run mating query by initiating MouseQueryBO
     *  and download the result set by capturing all the query information into
     *  an excel sheet. <br>
     */
    public void downloadSearchResultsAction() {
        try {
            // Create temp file.
            File tempFile = File.createTempFile("mouseQuery", ".csv");

            // Delete temp file when program exits.
            tempFile.deleteOnExit();

            // Write to temp file
            BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));

            // generate the search results report
            sfl.generateMouseQueryReport(search, out);

            int pos = 0;
            inputPath = tempFile.getAbsolutePath();
            String fs = File.separator;

            if (inputPath != null && (!inputPath.equals(""))) {
                // get the file name from file path
                pos = inputPath.lastIndexOf(fs);
            }

            if (pos > 0) {
                fileName = inputPath.substring((pos + 1), (inputPath.length()));
            }
            
            if (fileName != null && (!fileName.equals(""))) {
                new FileDownloadBean().downloadFile(this.inputPath, this.fileName);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /**
     *  <b>Purpose:</b>  Save mouse query by initiating MouseQueryBO. <br>
     *  <b>Overview:</b> Action to save mouse query by initiating MouseQueryBO
     *  by capturing all the query information in MouseQueryDTO. <br>
     *  @param query of type MouseQueryDTO, name of type String.
     */
    public void saveQueryAction() {
        try {
            if (!validateOutput()) {
                // Display validation information
                this.addToMessageQueue("At least one output field needs to be selected", FacesMessage.SEVERITY_WARN);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "At least one output field needs to be selected"));
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
                    initializeSearch();
                    
                    if (this.getSessionParameter("userEntity") != null) {
                        user = (UserEntity) getSessionParameter("userEntity");
                        key = user.getUserkey();
                        wge = user.getDefaultWorkgroupkey();

                        if (wge != null) {
                            wgKey = wge.getWorkgroupkey();
                        }
                    }

                    if (getIsOverwrite()) {
                        // duplicate query name in user's workgroup already done
                        // user has opted to overwrite the query
                        sfl.saveMouseQuery(search, this.getQueryName(), user, wgKey, this.queryDefinitionKey);
                        this.addToMessageQueue("Query saved.", FacesMessage.SEVERITY_INFO);
                    } else if (!validateQueryName(key, wgKey)) {
                        // save query only if name already doesn't exist
                        sfl.saveMouseQuery(search, this.getQueryName(), user, wgKey, null);
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
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
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
    protected boolean validateQueryName(Integer uKey, Integer wgKey) throws Exception {
        boolean flag = false;
        int i = 0;
        int key = 0;

        CvQueryTypeEntity queryType = null;
        ListSupportDTO lstDTO = new ListSupportDTO();

        List<CvQueryTypeEntity> cvQueryLst = new ArrayList<CvQueryTypeEntity>();
        cvQueryLst = lstDTO.getCvUserDefinedQueryType();

        // get a key for mouseQuery
        for (i = 0; i < cvQueryLst.size(); ++i) {
            if (cvQueryLst.get(i).getQueryType().equalsIgnoreCase("mousequery")) {
                queryType = (CvQueryTypeEntity) cvQueryLst.get(i).getEntity();
                key = queryType.getQueryTypekey();
            }
        }

        if (key > 0) {
            List<QueryDefinitionEntity> queryLst = new ArrayList<QueryDefinitionEntity>();
            queryLst = lstDTO.getUserDefinedQueries();

            // check if query name already exists for the query type
            for (i = 0; i < queryLst.size(); ++i) {
                if (queryLst.get(i).getQueryTypekey() != null && (queryLst.get(i).getQueryTypekey().getQueryTypekey() == key)) {
                    
                    // check if user already exists or if workgroup already exists
                    if ((queryLst.get(i).getUserkey() != null 
                            && queryLst.get(i).getUserkey().compareTo(uKey) == 0)
                            || (queryLst.get(i).getWorkgroupkey() != null
                            && queryLst.get(i).getWorkgroupkey().getWorkgroupkey() != null
                            && queryLst.get(i).getWorkgroupkey().getWorkgroupkey().compareTo(wgKey) == 0)) {
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
     * This is invoked when CLear button on query page is clicked, it resets
     * the query page.
     */
    public String clearQueryAction() {
        search = new MouseQueryDTO();
        initializeOwner();
        setupDualListModels();
        this.queryName = "";
        this.isOverwrite = false;

        return "mouseQuery";
    }

    /**
     *  <b>Purpose:</b>  Load Backing bean with options from database.. <br>
     *  <b>Overview:</b> Action to load mouse query by initiating MouseQueryBO
     *  by capturing all the query information from MouseQueryDTO. <br>
     *  5/28/14 - bas - Added a clear to make sure the form does not contain any leftovers from
     *  the previous query.
     */
    public String loadMouseQueryAction() {
        try {
            Integer key = this.getKey("udMouseQueryKey");
            clearQueryAction();
            this.setSearch(sfl.loadMouseQuery(key));          
            overwriteQueryListener();
            initializeDualListModelsOnLoad();
        } catch (Exception e) {
            // Display error information
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
        return "mouseQuery";
    }
    
    private void initializeDualListModelsOnLoad(){
        rooms.setTarget(search.getRooms());
        rooms.getSource().removeAll(rooms.getTarget());
        
        //get LifeStatusEntity objects from source and add to target
        for(Object obj : search.getLifeStatus()){
            LifeStatusEntity lse = (LifeStatusEntity) obj;
            int index = lifeStatuses.getSource().indexOf(lse);
            lifeStatuses.getTarget().add(lifeStatuses.getSource().get(index));
        }
        lifeStatuses.getSource().removeAll(lifeStatuses.getTarget());
        
        origins.setTarget(search.getOrigin());
        origins.getSource().removeAll(origins.getTarget());
        
        //get StrainEntity objects from source and add to target
        for(Object obj : search.getStrain()){
            StrainEntity se = (StrainEntity) obj;
            int index = strains.getSource().indexOf(se);
            strains.getTarget().add(strains.getSource().get(index));
        }
        //remove target from source
        genes.getSource().removeAll(genes.getTarget());
        
        generations.setTarget(search.getGeneration());
        generations.getSource().removeAll(generations.getTarget());
        
        owners.setTarget(search.getOwner());
        owners.getSource().removeAll(owners.getTarget());
        
        CODs.setTarget(search.getCauseOfDeath());
        CODs.getSource().removeAll(CODs.getTarget());
        
        protocolIDs.setTarget(search.getProtocolID());
        protocolIDs.getSource().removeAll(protocolIDs.getTarget());
        
        useSchedules.setTarget(search.getUseScheduleTerms());
        useSchedules.getSource().removeAll(useSchedules.getTarget());
        
        mouseUses.setTarget(search.getMouseUse());
        mouseUses.getSource().removeAll(mouseUses.getTarget());
        
        mousePhenotypes.setTarget(search.getPhenotypeTerms());
        mousePhenotypes.getSource().removeAll(mousePhenotypes.getTarget());
        
        //get GeneEntity objects from source and add to target
        for(Object obj : search.getGenotype()){
            GeneEntity ge = (GeneEntity) obj;
            int index = genes.getSource().indexOf(ge);
            genes.getTarget().add(genes.getSource().get(index));
        }
        //remove target from source
        genes.getSource().removeAll(genes.getTarget());
    }
    
    public String deleteQuery(){
        boolean theFlag = true;       
        try{
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
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
                        udEntity.getQueryName() + " to delete that query.", "deleteCageQueryAction"));
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
     * @return the search
     */
    public MouseQueryDTO getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(MouseQueryDTO search) {
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
     * @return the resultSet
     */
    public ResultDTO getResultData() {
        return resultData;
    }

    /**
     * @param resultSet the resultSet to set
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
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
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
     * @return the rooms
     */
    public DualListModel getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(DualListModel rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the lifeStatuses
     */
    public DualListModel getLifeStatuses() {
        return lifeStatuses;
    }

    /**
     * @param lifeStatuses the lifeStatuses to set
     */
    public void setLifeStatuses(DualListModel lifeStatuses) {
        this.lifeStatuses = lifeStatuses;
    }

    /**
     * @return the origins
     */
    public DualListModel getOrigins() {
        return origins;
    }

    /**
     * @param origins the origins to set
     */
    public void setOrigins(DualListModel origins) {
        this.origins = origins;
    }

    /**
     * @return the strains
     */
    public DualListModel getStrains() {
        return strains;
    }

    /**
     * @param strains the strains to set
     */
    public void setStrains(DualListModel strains) {
        this.strains = strains;
    }

    /**
     * @return the generations
     */
    public DualListModel getGenerations() {
        return generations;
    }

    /**
     * @param generations the generations to set
     */
    public void setGenerations(DualListModel generations) {
        this.generations = generations;
    }

    /**
     * @return the owners
     */
    public DualListModel getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(DualListModel owners) {
        this.owners = owners;
    }

    /**
     * @return the CODs
     */
    public DualListModel getCODs() {
        return CODs;
    }

    /**
     * @param CODs the CODs to set
     */
    public void setCODs(DualListModel CODs) {
        this.CODs = CODs;
    }
    
    /**
     * @return the protocolIDs
     */
    public DualListModel getProtocolIDs() {
        return protocolIDs;
    }

    /**
     * @param protocolIDs the protocolIDs to set
     */
    public void setProtocolIDs(DualListModel protocolIDs) {
        this.protocolIDs = protocolIDs;
    }
    
    /**
     * @return the mouseUses
     */
    public DualListModel getMouseUses() {
        return mouseUses;
    }

    /**
     * @param mouseUses the mouseUses to set
     */
    public void setMouseUses(DualListModel mouseUses) {
        this.mouseUses = mouseUses;
    }

    /**
     * @return the genes
     */
    public DualListModel getGenes() {
        return genes;
    }

    /**
     * @param genes the genes to set
     */
    public void setGenes(DualListModel genes) {
        this.genes = genes;
    }

    /**
     * @return the useSchedules
     */
    public DualListModel getUseSchedules() {
        return useSchedules;
    }

    /**
     * @param useSchedules the useSchedules to set
     */
    public void setUseSchedules(DualListModel useSchedules) {
        this.useSchedules = useSchedules;
    }
    
     /**
     * @return the mousePhenotypes
     */
    public DualListModel getMousePhenotypes() {
        return mousePhenotypes;
    }

    /**
     * @param mousePhenotypes the mousePhenotypes to set
     */
    public void setMousePhenotypes(DualListModel mousePhenotypes) {
        this.mousePhenotypes = mousePhenotypes;
    }
}