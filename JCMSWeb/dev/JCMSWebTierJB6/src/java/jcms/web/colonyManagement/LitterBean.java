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

package jcms.web.colonyManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.SystemFacadeLocal;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.ContainerHistoryEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvLitterTypeEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvTheilerStageEntity;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dao.DbSetupDAO;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.exception.SaveException;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;
import jcms.web.dto.DisplayDTO;
import jcms.web.main.LoginOwnerBean;
import jcms.integrationtier.dao.LitterDAO;
import jcms.integrationtier.dao.cvPreferencesDAO;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.web.common.MouseScheduleUtilities;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import org.primefaces.model.DualListModel;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.web.common.PageBuilder;
import jcms.integrationtier.dao.MatingSearchDAO;

/**
 *
 * @author rkavitha
 */
public class LitterBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 011231L;
    private SelectItemWrapper selectItemWrapper;
    private ListSupportDTO listSupportDTO;
    private LitterEntity litterEntity;
    private CvGenerationEntity generationEntity;
    private OwnerEntity ownerEntity;
    private ContainerEntity penEntity;
    private ContainerEntity matingPenEntity;
    private PenBean newPen;
    private boolean isMatingResultCountDisplayed;
    private ListDataModel matingDataModel;
    private ExtendedTableBean matingSelectionETB;
    private MatingSearchDTO matingSearch = null;
    private MatingListCommon matingInfo = null;
    private List<MatingEntity> matingList = null;
    private DbsetupEntity setupVar = null;
    private boolean isMouseResultCountDisplayed;
    private ListDataModel mouseDataModel;
    private ExtendedTableBean mouseSelectionETB;
    private MouseSearchDTO mouseSearch = null;
    private MiceListCommon mouseInfo = null;
    private List<MouseEntity> mouseList = null;
    private ListDataModel autoMouseDataModel;
    private ExtendedTableBean autoMouseETB;
    private CvMouseProtocolEntity mouseProtocolEntity;
    private MatingEntity matingEntity;
    private CvMouseOriginEntity mouseOriginEntity;
    private PageBuilder pageBuilder;
    private int matingID = 0;
    private int penID = 0;
    private int litterKey = 0;
    private boolean autoGenerateMouseRecords;
    private boolean femalesFirst;
    private boolean useMouseBaseNum;
    private boolean leavePupsInMatingPen;
    private String mouseBaseNum = "";
    private String mouseBaseID = "";
    private int numFemalesPerPen = 0;
    private int numMalesPerPen = 0;
    private int maxMicePerPen = 0;
    private int stdWeanTime = 0;
    private int extWeanTime = 0;
    private MouseFunctionsBean mouseFunctions;
    private List<DisplayDTO> miceList;
    private MouseEntity dam1 = null;
    private boolean manual = false;
    private boolean calculateWeanTagDates = true;
    private boolean calendarEdit = false;
    private DualListModel useSchedulesModel = new DualListModel();
    private MouseScheduleUtilities pUtils = new MouseScheduleUtilities();
    private String numberBornDead = "";
    private String numberCulledAtWean = "";
    private String numberMissingAtWean = "";
    
    public LitterBean() {
        
    }

    // initialize the owner to logged in owner
    private void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst;
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        if (ownerLst.size() > 0) {
            this.matingSearch.setOwners(ownerLst);
            this.mouseSearch.setOwners(ownerLst);
        }
    }

    private void initialize() {
        ArrayList<OwnerEntity> ownerLst;
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        
        SystemFacadeLocal facade = new IntegrationTierPortal().getSystemFacadeLocal();
        CvLitterTypeEntity litterTypeEntity = null;

        setMouseFunctions(new MouseFunctionsBean());
        setMiceList(new ArrayList<DisplayDTO>());

        isMouseResultCountDisplayed = false;
        mouseDataModel = new ListDataModel();
        mouseSelectionETB = new ExtendedTableBean();
        mouseSearch = new MouseSearchDTO();
        mouseInfo = new MiceListCommon();
        mouseList = new ArrayList<MouseEntity>();
        dam1 = new MouseEntity();
        
        setNewPen(new PenBean());
        // override the value because it should be always true when adding 
        // new pups
        this.getNewPen().setNextPen(true);

        setAutoMouseDataModel(new ListDataModel());
        setAutoMouseETB(new ExtendedTableBean());

        matingEntity = new MatingEntity();
        matingSearch = new MatingSearchDTO();
        setMouseOriginEntity(new CvMouseOriginEntity());
        setMouseProtocolEntity(new CvMouseProtocolEntity());
        setAutoGenerateMouseRecords(true);
        setFemalesFirst(true);
        setUseMouseBaseNum(false);
        //setLeavePupsInMatingPen(false); Removed, this is now set by the User Preferences

        setListSupportDTO(new ListSupportDTO(ownerLst));

        setGenerationEntity(new CvGenerationEntity());

        setSelectItemWrapper(new SelectItemWrapper());

        setIsMatingResultCountDisplayed(false);
        setMatingDataModel(new ListDataModel());
        setMatingSelectionETB(new ExtendedTableBean());

        setMatingInfo(new MatingListCommon());
        setMatingList(new ArrayList<MatingEntity>());

        setOwnerEntity(new OwnerEntity());
        setPenEntity(new ContainerEntity());
        
        
        getUseSchedulesModel().setSource(pUtils.getpDAO().getActiveBirthdateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
        getUseSchedulesModel().setTarget(new ArrayList<UseScheduleTermDTO>());

        matingPenEntity = new ContainerEntity();
        setPageBuilder(new PageBuilder());
        //Initialize the page builder here before it is changed by filling fields with current values
        this.initializePageBuilder();
        //Set the leave pups in mating cage check box; the default is false
        this.leavePupsInMatingPen = false;
        if (pageBuilder.getDefaultLeavePupsInMatingPen() != null) {
           if (!(pageBuilder.getDefaultLeavePupsInMatingPen().equals(""))){
               if (pageBuilder.getDefaultLeavePupsInMatingPen().equalsIgnoreCase("true")) {
                   this.leavePupsInMatingPen = true;
               }
           }
        }
        litterKey = this.getKey("paramLitterKey");
        System.out.println("litterKey " + litterKey);

        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_EXT_WEAN_TIME");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            // assign default extWeanTime
            if ((dbEntity.getMTSValue() != null && !dbEntity.getMTSValue().
                    equals(""))) {
                this.extWeanTime = Integer.parseInt(dbEntity.getMTSValue());
            }
        }

        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_STANDARD_WEAN_TIME");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            // assign default stdWeanTime
            if ((dbEntity.getMTSValue() != null && !dbEntity.getMTSValue().
                    equals(""))) {
                this.stdWeanTime = Integer.parseInt(dbEntity.getMTSValue());
            }
        }
        
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "MTS_MAX_MICE_PER_PEN");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            // assign default maximum number of mice per pen
            if ((dbEntity.getMTSValue() != null && !dbEntity.getMTSValue().
                    equals(""))) {
                this.maxMicePerPen = Integer.parseInt(dbEntity.getMTSValue());
                this.setNumFemalesPerPen(this.maxMicePerPen);
                this.setNumMalesPerPen(this.maxMicePerPen);
            }
        }
        
        //HERE WE MUST INSERT THE USER PREFERENCES FOR DEFAULT VALUES             
        // protocol and origin
         //Unlike editing a mouse or mating, it is possible for the litter record to be added 
         //and then later, you can use the edit litter version
         //to create the mice. Therefore these two defaults must always be displayed when the screen opens.
         //Edit mouse and mating do NOT display the default values when editing.
         if (pageBuilder.getDefaultLitterProtocolId() != null){
             for (CvMouseProtocolEntity Pentity : listSupportDTO.getAllCvMouseProtocol()) {
                 if (Pentity.getId().equalsIgnoreCase(pageBuilder.getDefaultLitterProtocolId())) {
                        this.mouseProtocolEntity = Pentity;
                        break;
                 }
             }
         }
         if (pageBuilder.getDefaultLitterOrigin() != null){
             for (CvMouseOriginEntity Oentity : listSupportDTO.getCvMouseOrigin()) {
                 if (Oentity.getMouseOrigin().equalsIgnoreCase(pageBuilder.getDefaultLitterOrigin())) {
                        this.mouseOriginEntity = Oentity;
                        break;
                 }
             }
         }

        if (litterKey == 0) {

            // set defaults
            System.out.println("setting defaults");
            this.litterEntity = new LitterEntity();

            this.litterEntity.setBirthDate(new Date());
            this.litterEntity.setTagDate(new Date());
            this.litterEntity.setWeanDate(new Date());
            
            numberBornDead = "";
            numberCulledAtWean = "";
            numberMissingAtWean = "";

            // TODO:  For this release JCMS Web only supports Live Birth litters while JCMS Access supports all.
            litterTypeEntity = (CvLitterTypeEntity) facade.baseFindByLitterType(new CvLitterTypeEntity(), "Live Birth");
            this.litterEntity.setLitterTypekey(litterTypeEntity);
            
            if (ownerLst.size() > 0) {
                this.setOwnerEntity(ownerLst.get(0));
                System.out.println(ownerLst.get(0).getOwner());
            }

            this.matingID = 0;
            this.mouseBaseID = "";
            this.mouseBaseNum = "";
            this.penID = 0;
             
        } else {
            // Find the MouseEntity
            System.out.println("find litter entity");
            this.litterEntity = (LitterEntity) getRepositoryService().baseFind(new LitterEntity(litterKey));
            
            if(litterEntity != null){
                //initialize # born dead
                if(litterEntity.getNumberBornDead() == null){
                    numberBornDead = "";
                }
                else{
                    numberBornDead = litterEntity.getNumberBornDead().toString();
                }
                
                //initialize # culled at wean
                if(litterEntity.getNumberCulledAtWean() == null){
                    numberCulledAtWean = "";
                }
                else{
                    numberCulledAtWean = litterEntity.getNumberCulledAtWean().toString();
                }
                
                //initialize # missing at wean
                if(litterEntity.getNumberMissingAtWean() == null){
                    numberMissingAtWean = "";
                }
                else{
                    numberMissingAtWean = litterEntity.getNumberMissingAtWean().toString();
                }
            }
            
            dam1 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(litterEntity.getMatingKey().getDam1Key()));

            if (litterEntity.getMatingKey() != null) {
                this.matingID = litterEntity.getMatingKey().getMatingID();
            }
            this.autoGenerateMouseRecords = false;
        }
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public boolean validatePups() throws Exception {
        boolean flag = false;

        // validate below only if autoGenerateMouseRecords is true
        if (this.autoGenerateMouseRecords) {
            if (this.litterEntity.getNumMale() == null) {
                this.addToMessageQueue("# Males is missing, Please enter "
                        + "# Males", FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation",
                        "# Males is missing, Please enter # Males"));
                flag = true;
            }

            if (this.litterEntity.getNumFemale() == null) {
                this.addToMessageQueue("# Females is missing, Please enter "
                        + "# Females", FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation",
                        "# Females is missing, Please enter # Females"));
                flag = true;
            }

            // validate total born
            if (this.litterEntity.getTotalBorn() == 0) {
                this.addToMessageQueue("Total Born # is missing, Please enter "
                        + "Total Born #",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation",
                        "Total Born # is missing, Please enter Total Born #"));
                flag = true;
            } else if (this.litterEntity.getTotalBorn() < this.litterEntity.getNumFemale()
                    + this.litterEntity.getNumMale()) {
                this.addToMessageQueue("# Males and # Females cannot be greater "
                        + "than Total Born #",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation",
                        "# Males and # Females cannot be greater than Total Born #"));
                flag = true;
            }
        }
        return flag;
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public boolean validate() throws Exception {
        boolean flag = false;
        // ******************************************************
        // Transfers control from the FORM view to the LIST view
        // pending a successful Save action.
        // ******************************************************

        System.out.println("About to Save");

        //validations

        // validate mating ID
        if (this.matingID == 0 || this.litterEntity.getMatingKey() == null) {
            this.addToMessageQueue("Please select a valid Mating ID", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation", "Please select a valid Mating ID"));
            flag = true;
        }

        // validate total born
        if (this.validatePups()) {
            flag = true;
        }

        // validate litter ID
        if (this.litterEntity.getLitterID() == null || this.litterEntity.getLitterID().equals("")) {
            this.addToMessageQueue("Litter # is missing, Please enter Litter #", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation", "Litter # is missing, Please enter Litter #"));
            flag = true;
        } 
        else {
            if (this.validateUniqueLitterID(this.litterEntity.getLitterID())) {
                this.addToMessageQueue("Litter # " + this.litterEntity.getLitterID() + " already exists", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation", "Litter # " + this.litterEntity.getLitterID() + " already exists"));
                flag = true;
            }
        }

        //validate the litter details - number born dead, # culled at wean, and # missing at wean
        boolean litterDetailsFlag = false;
        for(char c: this.numberCulledAtWean.toCharArray()){
            if(!this.isDigit(c)){
                this.addToMessageQueue("Number culled at wean must be an integer.", FacesMessage.SEVERITY_ERROR);
                litterDetailsFlag = true;
                break;
            }
        }
        for(char c: this.numberMissingAtWean.toCharArray()){
            if(!this.isDigit(c)){
                this.addToMessageQueue("Number missing at wean must be an integer.", FacesMessage.SEVERITY_ERROR);
                litterDetailsFlag = true;
                break;
            }
        }
        for(char c: this.numberBornDead.toCharArray()){
            if(!this.isDigit(c)){
                this.addToMessageQueue("Number born dead must be an integer.", FacesMessage.SEVERITY_ERROR);
                litterDetailsFlag = true;
                break;
            }
        }
        if(litterDetailsFlag){
            flag = true;
        }
        else{
            int numCulledAtWean;
            int numMissingAtWean;
            int numBornDead;

            if(numberCulledAtWean.isEmpty()){
                numCulledAtWean = 0;
            }
            else{
                numCulledAtWean = Integer.parseInt(numberCulledAtWean);
            }

            if(numberMissingAtWean.isEmpty()){
                numMissingAtWean = 0;
            }
            else{
                numMissingAtWean = Integer.parseInt(numberMissingAtWean);
            }

            if(numberBornDead.isEmpty()){
                numBornDead = 0;
            }
            else{
                numBornDead = Integer.parseInt(numberBornDead);
            }
            int totalNotWeaned = numBornDead + numMissingAtWean + numCulledAtWean;
            //validate all three together
            if(totalNotWeaned > this.litterEntity.getTotalBorn()){
                this.addToMessageQueue("Number born dead, number culled at wean, and number missing at wean cannot be greater than total born.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Number born dead, number culled at wean, and number missing at wean cannot be greater than total born."));
                flag = true;
            }
        }

        // validate below only if autoGenerateMouseRecords is true
        if (this.autoGenerateMouseRecords) {
            // if leaveMatingPens is true then get mating pen, else validate
            if (!this.leavePupsInMatingPen) {
                // check if numFemalesPerPen and numMalesPerPen are greater than the 
                // limit from set up variable.
                if (this.numFemalesPerPen > this.maxMicePerPen) {
                    this.addToMessageQueue("# Females / Cage cannot be greater than "
                            + this.maxMicePerPen, FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "# Females / Cage cannot be greater than " + this.maxMicePerPen));
                    flag = true;
                }
                if (this.numMalesPerPen > this.maxMicePerPen) {
                    this.addToMessageQueue("# Males / Cage cannot be greater than "
                            + this.maxMicePerPen, FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "# Males / Cage cannot be greater than " + this.maxMicePerPen));
                    flag = true;
                }
            }
            else {
                // set the mating entity
                int penId = dam1.getPenKey().getContainerID();
                ContainerHistoryEntity chEntity = null;
                int penHistoryKey = 0;

                if (penId > 0) {
                    this.matingPenEntity = this.mouseFunctions.findPen(penId);
                }

                // check if a mating pen is retired
                if (this.matingPenEntity != null) {
                    penHistoryKey = this.matingPenEntity.getContainerHistorykey();

                    chEntity = this.mouseFunctions.findPenHistory(penHistoryKey);

                    if (chEntity != null && chEntity.getContainerStatuskey() != null) {

                        if (chEntity.getContainerStatuskey().getContainerStatus().
                                equalsIgnoreCase("retired")) {
                            this.addToMessageQueue("The mating cage is retired. Unable "
                                    + "to keep pups in a retired cage.",
                                    FacesMessage.SEVERITY_ERROR);

                            this.getLogger().logWarn(this.formatLogMessage("Validation",
                                    "The mating cage is retired. Unable to keep pups in a retired cage."));
                            flag = true;
                        } 
                        else {
                            this.penID = penId;
                        }
                    } 
                    else {
                        this.addToMessageQueue("No ContainerHistory Record found "
                                + "for the pen.", FacesMessage.SEVERITY_ERROR);

                        this.getLogger().logWarn(this.formatLogMessage("Validation",
                                "No ContainerHistory Record found for the cage."));
                        flag = true;
                    }
                } 
                else {
                    this.addToMessageQueue("There is no cage for the selected "
                            + "Mating, please select a cage",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation",
                            "There is no cage for the selected Mating, please select a cage"));
                    flag = true;
                }
            }

            // validate base mouse id
            if (this.mouseBaseNum == null || this.mouseBaseNum.equals("")) {
                this.addToMessageQueue("Base mouse ID is missing. Please select a Base mouse ID.",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Base mouse ID is missing. Please select a Base mouse ID."));
                flag = true;
            }

            // validate mice list
            if (this.miceList != null && this.miceList.size() > 0) {
                if (validateMiceList()) {
                    flag = true;
                }
            } else {
                this.addToMessageQueue("Please populate Mouse ID's.",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Please populate Mouse ID's."));
                flag = true;
            }

            // validate origin
            if (this.mouseOriginEntity == null) {
                this.addToMessageQueue("Origin is missing. Please select the Origin.",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Origin is missing. Please select the Origin."));
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Create a new Pen using next available pen ID
     * 
     * @return ContainerEntity
     */
    private ContainerEntity createNewPen(String penName) {

        try {
            String comment = newPen.getPenEntity().getComment();
            this.newPen.setPenEntity(new ContainerEntity());
            if(comment != null){
                this.newPen.getPenEntity().setComment(comment);
            }
            this.newPen.setUseNextID("true");
            this.newPen.setNextPen(true);
            this.newPen.getPenEntity().setContainerName(penName);
            this.newPen.saveAction();

        } catch (SaveException se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "createCage"));
            return null;
        } // General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (SaveEntityException ex) {
            String msg = "The system failed to save any litter and mouse updates.  "
                    + "Please report this problem to the web master with date "
                    + "and time of error.  ";

            // Display user friendly error message
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "createPen"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "createPen"));
            return null;
        }
        return this.newPen.getPenEntity();
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String insertLitterAction() throws Exception {
        LoginOwnerBean lBean = new LoginOwnerBean();
        String user = lBean.getLoggedUser();
        
        if (this.litterEntity != null) {
            try {
                System.out.println("In insertLitterAction");
                
                // for insert operation
                if (!this.validate()) {
                    
                    if (litterEntity.getLitterKey() == null || litterEntity.getLitterKey() == 0) {

                        // generate primary key
                        Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(litterEntity);

                        if (null == pk || 0 == pk) {
                            litterEntity.setLitterKey(1);
                        } else {
                            litterEntity.setLitterKey(pk + 1);
                        }

                        // set the version
                        litterEntity.setVersion(0);

                        // litter info
                        System.out.println(litterEntity.getLitterKey());
                        System.out.println(litterEntity.getLitterID());
                        System.out.println(litterEntity.getNumFemale());
                        System.out.println(litterEntity.getNumMale());
                        System.out.println(litterEntity.getBirthDate());
                        System.out.println(litterEntity.getTotalBorn());
                        System.out.println(litterEntity.getStatus());
                        
                        //set num born dead, number killed at wean, number missing at wean
                        if(numberCulledAtWean.isEmpty()){
                            litterEntity.setNumberCulledAtWean(null);
                        }
                        else{
                            litterEntity.setNumberCulledAtWean(new Integer(numberCulledAtWean));
                        }
                        if(numberMissingAtWean.isEmpty()){
                            litterEntity.setNumberMissingAtWean(null);
                        }
                        else{
                            litterEntity.setNumberMissingAtWean(new Integer(numberMissingAtWean));
                        }
                        if(numberBornDead.isEmpty()){
                            litterEntity.setNumberBornDead(null);
                        }
                        else{
                            litterEntity.setNumberBornDead(new Integer(numberBornDead));
                        }

                        // insert the litter record first
                        new SaveAppService().baseCreate(this.litterEntity);
                        this.addToMessageQueue("Litter ID " + this.litterEntity.getLitterID() + " has been inserted",
                                FacesMessage.SEVERITY_INFO);
                        this.getLogger().logInfo(this.formatLogMessage("save",
                                "Litter ID " + this.litterEntity.getLitterID() + " has been inserted"));

                        // validate below only if autoGenerateMouseRecords is true
                        if (this.autoGenerateMouseRecords) {

                            // if true, use the mating pen
                            if (this.leavePupsInMatingPen) {

                                // create pups
                                for (int i = 0; i < this.miceList.size(); ++i) {

                                    // set the pen to mating pen when leavePupsInMatingPen 
                                    // is true
                                    // need to set pen
                                    // insert pup into db
                                    this.insertPups(this.matingPenEntity, i, user);
                                }
                            } else {

                                // create pups
                                ContainerEntity pen = null;
                                int k = 0, j = 0;
                                String newPenName = "";
                                String name = this.newPen.getPenEntity().getContainerName();
                                boolean pFlag = false;

                                if (name != null && !name.equals("") && this.newPen.isNextPenName()) {
                                    pFlag = true;
                                    newPenName = name;
                                }

                                if (this.femalesFirst) {
                                    // first process females
                                    //k is the number of females you've added
                                    for (j = 0, k = 0; j < this.numFemalesPerPen && k < this.litterEntity.getNumFemale(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numFemalesPerPen) {
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                        }
                                    }

                                    // then process males
                                    k = this.litterEntity.getNumFemale();
                                    System.out.println("now process males k " + k);
                                    
                                    if(k>0){
                                        newPenName = this.newPen.incrementPenName(name, pFlag);
                                        name = newPenName;
                                    }
                                    for (j = 0; j < this.numMalesPerPen && k < this.miceList.size(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                       if (j == 0) {                                            
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }
                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numMalesPerPen) {
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                        }
                                    }
                                } else {
                                    // first process males
                                    for (j = 0, k = 0; j < this.numMalesPerPen && k < this.litterEntity.getNumMale(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }

                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numMalesPerPen) {
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                        }
                                    }

                                    // then process females
                                    k = this.litterEntity.getNumMale();
                                    System.out.println("now process males k " + k);
                                    
                                    if(k>0){
                                        newPenName = this.newPen.incrementPenName(name, pFlag);
                                        name = newPenName;
                                    }
                                    
                                    for (j = 0; j < this.numFemalesPerPen && k < this.miceList.size(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }

                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numFemalesPerPen) {
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                        }
                                    }
                                }
                            }
                            // update dbInfo table with new maxAutoMouseID,
                            // update it only after successfully adding mpups.
                            JCMSDbInfoEntity dbInfoEntity = this.mouseFunctions.updateAutoMouseIDs(this.litterEntity.getNumFemale()
                                    + this.litterEntity.getNumMale());
                            new SaveAppService().baseEdit(dbInfoEntity);
                            this.getLogger().logInfo(this.formatLogMessage("save",
                                    "DbInfo " + dbInfoEntity.getMaxAutoMouseID() + " has been updated"));
                        }
                    } else {
                        this.getLogger().logError(this.formatLogMessage("save",
                                "Current Litter ID " + litterEntity.getLitterID()
                                + " already submitted"));
                        this.addToMessageQueue("Current Litter ID " + litterEntity.getLitterID()
                                + " already submitted", FacesMessage.SEVERITY_ERROR);
                    }
                }
            } catch (SaveException se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "insertAction"));
                return null;
            } // General catch-all for failed saves. The exception's message has already been customized for user display.
            catch (SaveEntityException ex) {
                String msg = "The system failed to save any litter and mouse updates.  "
                        + "Please report this problem to the web master with date "
                        + "and time of error.  ";

                // Display user friendly error message
                this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "insertAction"));

                // Return null to indicate that the JSF implementation
                // should reload the same page.
                return null;
            } catch (Exception se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
                return null;
            }
        } else {
            this.getLogger().logError(this.formatLogMessage("Litter entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        litterEntity.setLitterKey(0);
        return null;
    }

    /**
     * <b>Purpose:</b> Update a litter <br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String updateLitterAction() throws Exception {
        String user = this.getUserEntity().getUserName();
        
        System.out.println("entered into update litter");
        if (this.litterEntity != null) {
            try {
                if (!this.validate()) {
                    // for edit operation
                    if (litterEntity.getLitterKey() != null && litterEntity.getLitterKey() > 0) {

                        // set the version
                        int ver = litterEntity.getVersion();
                        System.out.println("previous version " + ver);
                        litterEntity.setVersion(ver + 1);
                        
                        //set num born dead, number killed at wean, number missing at wean
                        if(numberCulledAtWean.isEmpty()){
                            litterEntity.setNumberCulledAtWean(null);
                        }
                        else{
                            litterEntity.setNumberCulledAtWean(new Integer(numberCulledAtWean));
                        }
                        if(numberMissingAtWean.isEmpty()){
                            litterEntity.setNumberMissingAtWean(null);
                        }
                        else{
                            litterEntity.setNumberMissingAtWean(new Integer(numberMissingAtWean));
                        }
                        if(numberBornDead.isEmpty()){
                            litterEntity.setNumberBornDead(null);
                        }
                        else{
                            litterEntity.setNumberBornDead(new Integer(numberBornDead));
                        }

                        new SaveAppService().saveLitter(litterEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save", "Litter ID "
                                + litterEntity.getLitterID() + " has been updated"));
                        this.addToMessageQueue("Litter ID " + litterEntity.getLitterID() + " has been updated",
                                FacesMessage.SEVERITY_INFO);
                    }
                    // validate below only if autoGenerateMouseRecords is true
                        if (this.autoGenerateMouseRecords) {

                            // if true, use the mating pen
                            if (this.leavePupsInMatingPen) {

                                // create pups
                                for (int i = 0; i < this.miceList.size(); ++i) {

                                    // set the pen to mating pen when leavePupsInMatingPen 
                                    // is true
                                    // need to set pen
                                    // insert pup into db
                                    this.insertPups(this.matingPenEntity, i, user);
                                }
                            } else {

                                // create pups
                                ContainerEntity pen = null;
                                int k = 0, j = 0;
                                String newPenName = "";
                                String name = this.newPen.getPenEntity().getContainerName();
                                boolean pFlag = false;

                                if (name != null && !name.equals("") && this.newPen.isNextPenName()) {
                                    pFlag = true;
                                    newPenName = name;
                                }

                                if (this.femalesFirst) {
                                    // first process females
                                    //k is the number of females you've added
                                    for (j = 0, k = 0; j < this.numFemalesPerPen && k < this.litterEntity.getNumFemale(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numFemalesPerPen) {
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                        }
                                    }

                                    // then process males
                                    k = this.litterEntity.getNumFemale();
                                    System.out.println("now process males k " + k);
                                    
                                    if(k>0){
                                        newPenName = this.newPen.incrementPenName(name, pFlag);
                                        name = newPenName;
                                    }
                                    for (j = 0; j < this.numMalesPerPen && k < this.miceList.size(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                       if (j == 0) {                                            
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }
                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numMalesPerPen) {
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                        }
                                    }
                                } else {
                                    // first process males
                                    for (j = 0, k = 0; j < this.numMalesPerPen && k < this.litterEntity.getNumMale(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }

                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numMalesPerPen) {
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                        }
                                    }

                                    // then process females
                                    k = this.litterEntity.getNumMale();
                                    System.out.println("now process males k " + k);
                                    
                                    if(k>0){
                                        newPenName = this.newPen.incrementPenName(name, pFlag);
                                        name = newPenName;
                                    }
                                    
                                    for (j = 0; j < this.numFemalesPerPen && k < this.miceList.size(); ++k) {
                                        // if containerName exists, get the incremented pen name
                                        if (j == 0) {
                                            System.out.println("create new pen");
                                            pen = this.createNewPen(newPenName);
                                        }

                                        // insert pup into db
                                        this.insertPups(pen, k, user);
                                        ++j;
                                        // break up when it reaches maxmiceperpen limit
                                        if (j == this.numFemalesPerPen) {
                                            newPenName = this.newPen.incrementPenName(name, pFlag);
                                            name = newPenName;
                                            System.out.println("create a new pen as maxMiceperPen limit is reached");
                                            j = 0;
                                        }
                                    }
                                }
                            }
                            // update dbInfo table with new maxAutoMouseID,
                            // update it only after successfully adding mpups.
                            JCMSDbInfoEntity dbInfoEntity = this.mouseFunctions.updateAutoMouseIDs(this.litterEntity.getNumFemale()
                                    + this.litterEntity.getNumMale());
                            new SaveAppService().baseEdit(dbInfoEntity);
                            this.getLogger().logInfo(this.formatLogMessage("save",
                                    "DbInfo " + dbInfoEntity.getMaxAutoMouseID() + " has been updated"));
                        }
                }
            } catch (SaveException se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "updatedAction"));
                return null;
            } // General catch-all for failed saves. The exception's message has already been customized for user display.
            catch (SaveEntityException ex) {
                String msg = "The system failed to save any litter and mouse updates.  "
                        + "Please report this problem to the web master with date "
                        + "and time of error.  ";

                // Display user friendly error message
                this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "insertAction"));

                // Return null to indicate that the JSF implementation
                // should reload the same page.
                return null;
            } catch (Exception se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
                return null;
            }
        } else {
            this.getLogger().logError(this.formatLogMessage("Litter entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }

    private void insertPups(ContainerEntity pen, int k, String user) {
        MouseEntity mEntity = new MouseEntity();
        Integer pk = 0;

        try {
            mEntity.setBirthDate(this.litterEntity.getBirthDate());
            mEntity.setBreedingStatus("V");
            mEntity.setGeneration(this.litterEntity.getMatingKey().getGeneration());
            mEntity.setId(miceList.get(k).getValue());
            mEntity.setSex(this.miceList.get(k).getTemp());
            mEntity.setLifeStatus("A");
            mEntity.setLitterKey(litterEntity);
            mEntity.setOrigin(this.mouseOriginEntity.getMouseOrigin());
            mEntity.setOwner(this.litterEntity.getMatingKey().getOwner());
            mEntity.setComment("ADDED IN BULK AS LITTER by user: " + user);
            if (this.mouseProtocolEntity != null) {
                mEntity.setProtocol(this.mouseProtocolEntity.getId());
            }
            

            mEntity.setStrainKey(this.litterEntity.getMatingKey().getStrainKey());

            // generate primary key
            pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(mEntity);

            String primaryKey;
            if (null == pk || 0 == pk) {
                mEntity.setMouseKey(1);
                primaryKey = "1";
            } else {
                mEntity.setMouseKey(pk + 1);
                primaryKey = new Integer(pk + 1).toString();
            }
            if (pen != null) {
                mEntity.setPenKey(pen);
            }
            
            System.out.println(mEntity.getMouseKey());
            System.out.println(mEntity.getBirthDate());
            System.out.println(mEntity.getProtocol());
            System.out.println(mEntity.getPenKey().getContainerID());
            System.out.println(mEntity.getStrainKey().getStrainName());
            System.out.println(mEntity.getSex());
            System.out.println(mEntity.getId());

            // insert mouse record
            new SaveAppService().baseCreate(mEntity);
            this.getLogger().logInfo(this.formatLogMessage("save", "Mouse ID " + mEntity.getId() + " has been inserted"));
            
            
            //initialize mouse
            MouseDTO mouse = new MouseDTO();
            mouse.setMouse_key(primaryKey);
            mouse.setBirthDate(mEntity.getBirthDate());
            //add mouse to use schedules
            for(UseScheduleTermDTO dto : (ArrayList<UseScheduleTermDTO>) getUseSchedulesModel().getTarget()){
                pUtils.addMouseToBirthDateUseSchedule(dto, mouse);
                this.addToMessageQueue("Mouse " + mEntity.getId() + " added to use schedule " + dto.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
            }
            // General catch-all for failed saves. The exception's message has already been customized for user display.
        } catch (SaveEntityException ex) {
            String msg = "The system failed to save any litter and mouse updates.  "
                    + "Please report this problem to the web master with date "
                    + "and time of error.  ";

            // Display user friendly error message
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "insertAction"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "insertPups"));
        }
    }

    public String addLitterAction() {
        this.initialize();
        this.initializeOwner();

        return "addLitter";
    }
    
    public String calendarAddLitterAction() {
        this.initialize();
        this.initializeOwner();
        this.matingID = this.getKey("paramMatingID");
        
        MatingEntity mating = (MatingEntity) getRepositoryService().baseFindByMatingID(new MatingEntity(), this.matingID);
        this.litterEntity.setMatingKey(mating);
        dam1 = (MouseEntity) getRepositoryService().baseFindByKey(new MouseEntity(), mating.getDam1Key());
        return "addLitter";
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void clearNewPenPopupAction() {
        this.getNewPen().clearPenAction();
        // override the value because it should be always true when adding 
        // new pups
        this.getNewPen().setNextPen(true);
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void clearMatingPopupAction() {
        System.out.println("clear pop-up action called");
        matingDataModel.setWrappedData(new ArrayList<MatingEntity>());
        isMatingResultCountDisplayed = false;
        this.matingSearch = new MatingSearchDTO();
        setSelectItemWrapper(new SelectItemWrapper());
        this.initializeOwner();
    }

    /**
     * <b>Purpose:</b> Prepares a new object for data entry. <br />
     * <b>Overview:</b> This method is usually called from an Add button on a
     *      list view.  Data transfer objects are created in preparation for
     *      entering data in a form view.  The data entered is saved to these
     *      data transfer objects by JSF API.  <p />
     * @return String action returned to faces-config.xml
     */
    public String editAction() {
        calendarEdit = false;
        System.out.println("Edit litter action");
        System.out.println("litter key " + this.getKey("paramLitterKey"));
        this.initialize();
        this.initializeOwner();

        return "editLitter";
    }
    
    public String calendarEditAction() {
        calendarEdit = true;
        System.out.println("Edit litter action");
        System.out.println("litter key " + this.getKey("paramLitterKey"));
        this.initialize();
        this.initializeOwner();

        return "editLitter";
    }

    /*
     * This method is called on change event on birth date to 
     * validate
     */
    public void validateBirthDateFilterAction(ValueChangeEvent event) {
        if(!this.validateBirthDate((Date)event.getNewValue())){
            if (this.calculateWeanTagDates)
                validateWeanAndTagDateFromBirthDate((Date)event.getNewValue(), this.matingID);
        }
    }

    /*
     * This method is called on change event on birth date to 
     * validate
     */
    public boolean validateBirthDate(Date newDate) {
        boolean flag = false;

        // date cannot be greater than today
        if (newDate.compareTo(new Date()) > 0) {
            this.addToMessageQueue("Date of Birth cannot be greater than Today",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Date of Birth cannot be greater than Today"));
            flag = true;
        }
        return flag;
    }
    
    public void validateWeanAndTagDateAction(ValueChangeEvent event) {
        // Process only if backing bean properties are updated.
            if (this.calculateWeanTagDates)
                validateWeanAndTagDate((Date) event.getNewValue(), this.matingID);
    }

    public void validateWeanAndTagDateFromBirthDate(Date birthdate, int matingID) {
        Date newDate = new Date();
        String weanTimeAsString = "-1";
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthdate);

        System.out.println("entered into wean and tag dates from birthdate");

        // Adding change to check the calculate dates check box on the form
        // Only do if it is checked!
        if (this.calculateWeanTagDates) {
            if (birthdate != null) {
                System.out.println(cal.get(Calendar.DAY_OF_MONTH));
                //Must decide here if using standard or extended wean time for this mating
                MatingSearchDAO dao = new MatingSearchDAO();
                weanTimeAsString = dao.FindMatingWeanTimeAsString(matingID);
                System.out.println("Wean Time:" + weanTimeAsString + " mating ID: " + matingID);
                if(weanTimeAsString.equals("-1")) {
                    cal.add(Calendar.DAY_OF_MONTH, this.stdWeanTime); // add 18 days  
                    newDate = cal.getTime();

                    this.litterEntity.setWeanDate(newDate);
                    this.litterEntity.setTagDate(newDate);
                } else {
                    cal.add(Calendar.DATE, this.extWeanTime); // add 28 days  
                    newDate = cal.getTime();

                    this.litterEntity.setWeanDate(newDate);
                    this.litterEntity.setTagDate(newDate);
                }
            }
        }
    }
    
    public void validateWeanAndTagDate(Date newValue, int matingID) {
        //the newValue passed is the wean date for this litter
        Date newDate = new Date();
        String weanTimeAsString = "-1";
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.litterEntity.getBirthDate());

        System.out.println("entered into wean and tag dates (new value)");

        // Adding change to check the calculate dates check box on the form
        // Only do if it is checked!
        if (this.calculateWeanTagDates) {
            if (newValue != null) {
                System.out.println("Day of Month: " + cal.get(Calendar.DAY_OF_MONTH));
                //Must decide here if using standard or extended wean time for this mating
                //Mating entity has weanTime defined as a boolean, which ALWAYS returns false for -1 (we need this to be true)
                //Now passing the matingID here so can look up the value of Mating.weanTime
                MatingSearchDAO dao = new MatingSearchDAO();
                weanTimeAsString = dao.FindMatingWeanTimeAsString(matingID);
                System.out.println("Wean Time:" + weanTimeAsString + " mating ID: " + matingID);
                if(weanTimeAsString.equals("-1")) {
                    System.out.println("Standard wean time");
                    cal.add(Calendar.DAY_OF_MONTH, this.stdWeanTime); // add 18 days  
                    newDate = cal.getTime();

                    this.litterEntity.setWeanDate(newDate);
                    this.litterEntity.setTagDate(newDate);
                } else {
                cal.add(Calendar.DATE, this.extWeanTime); // add 28 days  
                System.out.println("Extended wean time");    
                newDate = cal.getTime();

                    this.litterEntity.setWeanDate(newDate);
                    this.litterEntity.setTagDate(newDate);
                }
            }
        }    
    }

    /**
     * <b>Purpose:</b> Save a PenBean<br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public void savePenAction() throws Exception {
        this.newPen.validatePenInfo();
    }

    public void matingSearchAction() {
        System.out.println("Search Results");

        // check if any search criteria is selected, else throw error message
        if (this.matingSearch != null && ((matingSearch.getMatingID() != null
                && !matingSearch.getMatingID().equals(""))
                || (matingSearch.getStrain() != null
                && !matingSearch.getStrain().getStrainName().equals("")
                || (matingSearch.getMatingStatus() != null)
                && !matingSearch.getMatingStatus().getCrossStatus().equals(""))
                || (matingSearch.getOwners() != null
                && !matingSearch.getOwners().isEmpty()))) {

            // if empty, then set it to the owners that user belongs to
            if (this.matingSearch.getOwners() == null
                    || this.matingSearch.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.matingSearch.setOwners((ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst"));
            }

            matingDataModel.setWrappedData(this.matingInfo.matingSearch(matingSearch));

            this.setIsMatingResultCountDisplayed(true);
        } else {
            this.addToMessageQueue("Select some search criteria",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Select some search criteria"));
        }
    }

    public void selectMatingAction() {
        System.out.println("Selected Mating");
        MatingEntity mating = new MatingEntity();

        mating = matingInfo.getSelectedMating(this.matingSelectionETB,
                this.matingDataModel);

        if (mating != null && mating.getMatingID() > 0) {
            this.litterEntity.setMatingKey(mating);
            this.setMatingID(mating.getMatingID());
            dam1 = (MouseEntity) getRepositoryService().baseFindByKey(new MouseEntity(), mating.getDam1Key());
            this.validateWeanAndTagDate(this.litterEntity.getWeanDate(), mating.getMatingID());
        }
        System.out.println("mating ID " + this.getMatingID());
        
    }

    public void setMatingIDValueChangeListener(ValueChangeEvent event) {
        System.out.println("Selected Mating");
        try {
            this.matingID = Integer.parseInt(event.getNewValue().toString());

            if (this.matingID > 0) {
                // Check that litter id is unique.
                MatingEntity mating = (MatingEntity) getRepositoryService().baseFindByMatingID(new MatingEntity(), this.matingID);

                if (mating != null) {
                    boolean owned = false;
                    for(WorkgroupEntity we : (ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst")){
                        if(we.getWorkgroupName().equalsIgnoreCase(mating.getOwner())){
                            owned = true;
                        }
                    }
                    if(owned){
                        this.litterEntity.setMatingKey(mating);
                        dam1 = (MouseEntity) getRepositoryService().baseFindByKey(new MouseEntity(), mating.getDam1Key());
                        
                        this.validateWeanAndTagDate(this.litterEntity.getWeanDate(), this.matingID);
                    }
                    else{
                        this.addToMessageQueue("You do not have the necessary permissions to add a litter to mating " + matingID, FacesMessage.SEVERITY_ERROR);
                        this.getLogger().logError(this.formatLogMessage("You do not have the necessary permissions to add a litter to mating " + matingID, "ValidateMatingIDAction"));
                    }
                } else {
                    this.addToMessageQueue("Mating ID " + this.matingID + " doesn't exist",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Mating ID " + this.matingID + " doesn't exist",
                            "ValidateMatingIDAction"));
                }
            }

            System.out.println("mating ID " + this.getMatingID());
        } catch (NumberFormatException e) {
            
        }
    }

    public void mouseSearchAction() {
        // if empty, then set it to the owners that user belongs to
        if (this.mouseSearch.getOwners() == null
                || this.mouseSearch.getOwners().isEmpty()) {
            System.out.println("ownerLst initialzed");
            this.mouseSearch.setOwners((ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst"));
        }
        this.mouseDataModel.setWrappedData(this.mouseInfo.miceSearch(this.mouseSearch));
        System.out.println("Search Results " + mouseDataModel.getRowCount());
        this.setIsMouseResultCountDisplayed(true);
    }

    /**
     * get the selected mouseID, increment the mouse ID and validate. If good, 
     * assign it to base mouse id.
     */
    public void selectMouseAction() throws Exception {
        MouseEntity mouse = new MouseEntity();
        String mouseID = "";
        String nextID = "";

        mouse = mouseInfo.getSelectedMouse(this.mouseSelectionETB,
                this.mouseDataModel);
        if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
            mouseID = mouse.getId();
        }
        if (mouseID != null && !mouseID.equals("")) {
            nextID = mouseFunctions.incrementID(mouseID,
                    "JCMS_MOUSEID_INCREMENT_RIGHTMOST");

            System.out.println("nextID " + nextID);

            // check if incremented mouse ID already exists
            attemptIdParse(nextID);
            //mouse ID not found do this
            if (this.validateMouseID(nextID)) {
                this.setMouseBaseNum(nextID);
            }
            else{
                this.setMouseBaseNum(attemptIdParse(nextID));
            }
        }
        System.out.println("MouseBaseNum " + this.getMouseBaseNum());
    }
    
    private String attemptIdParse(String id){
        /*
        * A mouse ID that needs to be incremented consists of two parts, a number and an identifier.
        * For example if the id is B6-1 the ID would be 'B6-' and the number is '1'. This method needs to
        * parse out the B6- and the 1 and increment the 1 until you have a unique ID that doesn't exist
        * in the JCMS database. For example if B6-1 through 10 already exists and a user types in B6-1 this method
        * should check B6-1,2,3...10, 11 and find that B6-11 doesn't exist and say that is the value to use.
        * It also needs to be checked whether the setup var MouseIDIncrementRightmost is true or false.
        * And if it is false the number is the leftmost portion so 123-B6 would be 124-B6 where as if 
        * it were true 123-B6 would be incremented to 123-B7.
        */
        if(!id.equals("")){
            String theBaseID = "";
            String number = "";
            String nextID = "";

            DbSetupDAO dao = new DbSetupDAO();
            //special case where ID is an integer
            try{
                Integer.parseInt(id);
                String MySQLStatement = 
                        "SELECT MAX(CONVERT(ID, UNSIGNED INTEGER)) AS max "
                        + "FROM Mouse "
                        + "WHERE ID REGEXP '^[0-9]*$';";
                try{
                    number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                }
                catch(Exception e){}
                nextID = theBaseID + number;
                System.out.println(MySQLStatement); 
                System.out.println("Next ID: " + nextID);
                return nextID;
            }
            catch(NumberFormatException e){}
            //incrementing the rightmost portion of ID
            if(dao.getJCMSMouseIDIncrementRightmost().getBlnMTSValue()){
                char[] charArrId = id.toCharArray();
                char c = charArrId[charArrId.length - 1];
                int idx = 1;
                while(Character.isDigit(c)){
                    idx++;
                    c = charArrId[charArrId.length - idx];
                }
                theBaseID = new String(Arrays.copyOfRange(charArrId, 0, charArrId.length - (idx-1)));
                //if idx is not one that means that there is AT LEAST one digit at the end of the mouse ID
                if(idx!=1){
                    number = new String(Arrays.copyOfRange(charArrId, charArrId.length - (idx-1), id.length()-1));
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^" + theBaseID + "[0-9]*$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                        //need to append leading zeros, if the number you get from mysql is less in length then the 
                        while(number.length() < (idx - 1)){
                            number = "0" + number;
                        }
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println(MySQLStatement); 
                    System.out.println("Next ID: " + nextID);
                }
                else{
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^" + theBaseID + "[0-9]*$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                        while(number.length() < (idx - 1)){
                            number = "0" + number;
                        }
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println("Next ID: " + nextID);
                }
            }
            //incrementing the leftmost portion of ID
            else{
                char[] charArrId = id.toCharArray();
                char c = charArrId[0];
                int idx = 0;
                while(Character.isDigit(c)){                    
                    idx++;
                    c = charArrId[idx];
                }
                theBaseID = new String(Arrays.copyOfRange(charArrId, idx, id.length()));
                if(idx!=0){
                    number = new String(Arrays.copyOfRange(charArrId, 0, idx));
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^[0-9]*" + theBaseID + "$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                    }
                    catch(Exception e){
                        this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                    }
                    nextID = number + theBaseID;
                    System.out.println(MySQLStatement);     
                    System.out.println("Next ID: " + nextID);
                }
                else{   
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^[0-9]*" + theBaseID + "$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                    }
                    catch(Exception e){}
                    nextID = number + theBaseID;
                    System.out.println("Next ID: " + nextID);
                }
            }
            return nextID;
        }        
        return "";
    }
    
    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void populateMouseIDAction() throws Exception {
        miceList = new ArrayList<DisplayDTO>();
        String baseID = "";
        String nextID = "";
        DisplayDTO dto;
        int i = 0;

        int totalCnt = this.litterEntity.getNumFemale() + this.litterEntity.getNumMale();

        // limit the total number of males and females to 20 pups
        if (totalCnt <= 20) {
            if (this.mouseBaseID != null && !this.mouseBaseID.equals("")) {

                if (this.validateMouseID(this.mouseBaseID)) {
                    baseID = this.mouseBaseID;
                    // first id is added
                    dto = new DisplayDTO();
                    dto.setValue(baseID);
                    if (this.femalesFirst) {
                        if (i < this.litterEntity.getNumFemale()) {
                            dto.setTemp("F");
                        } else {
                            dto.setTemp("M");
                        }
                    } else {
                        if (i < this.litterEntity.getNumMale()) {
                            dto.setTemp("M");
                        } else {
                            dto.setTemp("F");
                        }
                    }

                    miceList.add(dto);

                    // cnt from 2nd id
                    for (i = 1; i < totalCnt; ++i) {
                        if (baseID != null && !baseID.equals("")) {
                            nextID = mouseFunctions.incrementID(baseID, "JCMS_MOUSEID_INCREMENT_RIGHTMOST");
                            dto = new DisplayDTO();
                            dto.setValue(nextID);

                            if (this.femalesFirst) {
                                if (i < this.litterEntity.getNumFemale()) {
                                    dto.setTemp("F");
                                } else {
                                    dto.setTemp("M");
                                }
                            } else {
                                if (i < this.litterEntity.getNumMale()) {
                                    dto.setTemp("M");
                                } else {
                                    dto.setTemp("F");
                                }
                            }
                            miceList.add(dto);
                            System.out.println("auto mouse IDs " + nextID);
                            baseID = nextID;
                            if (!this.validateMouseID(nextID)) {
                                this.addToMessageQueue("Mouse ID " + nextID + " already exists",
                                        FacesMessage.SEVERITY_ERROR);
                                this.getLogger().logError(this.formatLogMessage("Mouse ID " + nextID + " already exists",
                                        "ValidateMouseIDAction"));
                                
                            }
                        }
                    }
                } else {
                    this.addToMessageQueue("Mouse ID " + this.mouseBaseID + " already exists",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Mouse ID " + this.mouseBaseID + " already exists",
                            "populateMouseIDAction"));
                }
            } else {
                this.addToMessageQueue("Base Mouse ID is missing",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Base Mouse ID is missing",
                        "populateMouseIDAction"));
            }
        } else {
            this.addToMessageQueue("Number of males and females cannot be greater than 20",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage("Number of males and females "
                    + "cannot be greater than 20", "populateMouseIDAction"));
        }
        System.out.println("size " + this.miceList.size());
        this.autoMouseDataModel.setWrappedData(this.miceList);
//        attemptIdParse(mouseBaseID);
    }

    public void validateMouseIDAction() throws Exception {
        System.out.println("Validating Mouse ID");
        if (!this.validateMouseID(this.mouseBaseNum)) {
            this.addToMessageQueue("Mouse ID " + this.mouseBaseNum + " already exists",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage("Mouse ID " + this.mouseBaseNum + " already exists",
                    "ValidateMouseIDAction"));
        }
    }

    /**
     * validates mouse id if already exists
     * @param id
     * @return
     * @throws Exception 
     */
    private boolean validateMouseID(String id) throws Exception {
        if (id != null && !id.equals("")) {
            MouseEntity mEntity = this.mouseFunctions.findMouse(id);

            if (mEntity == null) {
                return true;
            }
        }
        return false;
    }

    private boolean validateUniqueLitterID(String id) {
        boolean flag = false;

        // Check that litter id is unique.
        LitterEntity lEntity = (LitterEntity) getRepositoryService().
                baseFindByLitterID(new LitterEntity(), id);

        // edit
        if (litterKey > 0) {
            if (lEntity != null && litterKey != lEntity.getLitterKey()) {
                System.out.println("Entered into validate litterID for litter key > 0");
                flag = true;
            }
        } // add
        else {
            if (lEntity != null) {
                flag = true;
            }
        }       
        return flag;
    }

    private boolean validateUniqueLitterNumber(String id) {
        // Check that litter id is unique.
        LitterEntity lEntity = (LitterEntity) getRepositoryService().
                baseFindByLitterID(new LitterEntity(), id);

        if (lEntity == null) {
            return true;
        }
        return false;
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     * The above is a lie, the purpose of this is to use the mouseBaseNum 
     * variable to validate and set the mouseBaseID variable. It does not
     * make sense that two variables exist to do the same thing, but alas 
     * what choice do we have.
     */
    public boolean setBaseMouseIDAction() throws Exception {
        boolean success = false;
        String baseID = "";
        this.mouseBaseID = "";
        if (this.mouseBaseNum != null && !this.mouseBaseNum.equals("")) {
            baseID = this.mouseBaseNum;
            success = true;
        } 
        else {
            if (!this.validatePups()) {
                success = true;
                int numIDs = this.litterEntity.getNumFemale() + this.litterEntity.getNumMale();
                if (numIDs <= 20) {
                    if(new DbSetupDAO().getJCMSMouseIDIncrementRightmost().getBlnMTSValue()){
                        //check if last charcter is digit, if it is then just increment, 
                        //otherwise need to parseID out of it
                        if(!baseID.equals("") && Character.isDigit(baseID.charAt(baseID.length()-1))){
                            baseID = this.mouseFunctions.allocateAutoBaseMouseID(numIDs);
                        }
                        else{
                            mouseBaseID = attemptIdParse(baseID);
                        }
                    }
                    else{
                        if(!baseID.equals("") && Character.isDigit(baseID.charAt(0))){
                            baseID = this.mouseFunctions.allocateAutoBaseMouseID(numIDs);
                        }
                        else{
                            mouseBaseID = attemptIdParse(baseID);
                        }
                    }
                } else {
                    success = false;
                    this.addToMessageQueue("Number of males and females cannot be "
                            + "greater than 20", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Number of males "
                            + "and females cannot be greater than 20",
                            "setBaseMouseIDAction"));
                }
            }
        }
        if (baseID != null && !baseID.equals("")) {
            if (this.validateMouseID(baseID)) {
                if(new DbSetupDAO().getJCMSMouseIDIncrementRightmost().getBlnMTSValue()){
                    //check if last charcter is digit, if it is then just increment, 
                    //otherwise need to parseID out of it
                    if(!baseID.equals("") && Character.isDigit(baseID.charAt(baseID.length()-1))){
                        this.mouseBaseID = baseID;
                    }
                    else{
                        mouseBaseID = attemptIdParse(baseID);
                    }
                }
                else{
                    if(!baseID.equals("") && Character.isDigit(baseID.charAt(0))){
                        this.mouseBaseID = baseID;
                    }
                    else{
                        mouseBaseID = attemptIdParse(baseID);
                    }
                }
            } else {
                mouseBaseID = attemptIdParse(baseID);
            }
        }
        this.autoMouseDataModel.setWrappedData(new ArrayList<DisplayDTO>());
        return success;
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void autoMouseIDAction() throws Exception {
        String baseID = "";

        int numIDs = this.litterEntity.getNumFemale() + this.litterEntity.getNumMale();

        // limit the animals to 20
        if (numIDs <= 20) {
            baseID = this.mouseFunctions.allocateAutoBaseMouseID(numIDs);

            if (baseID != null && !baseID.equals("")) {
                MouseEntity mEntity = this.mouseFunctions.findMouse(baseID);

                if (mEntity == null) {
                    this.mouseBaseID = baseID;
                    this.populateMouseIDAction();
                    mouseBaseNum = baseID;
                } else {
                    this.addToMessageQueue("Mouse ID " + baseID + " already exists",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Mouse ID " + baseID + " already exists",
                            "autoMouseIDAction"));
                }
            }
        } else {
            this.addToMessageQueue("Number of males and females cannot be greater than 20",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage("Number of males and females "
                    + "cannot be greater than 20", "autoMouseIDAction"));
        }
    }

    public void saveMouseIDAction() throws Exception {
        if (!validateMiceList()) {
            this.mouseBaseNum = this.miceList.get(0).getValue();
        }
    }

    // validate mouse ID's
    public boolean validateMiceList() throws Exception {
        boolean flag = false;
        // validate mouse ID's
        for (int i = 0; i < this.miceList.size(); ++i) {
            System.out.println("Mouse ID " + miceList.get(i).getValue());
            if (!this.validateMouseID(miceList.get(i).getValue())) {
                this.addToMessageQueue("Mouse ID " + miceList.get(i).getValue()
                        + " already exists",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Mouse ID "
                        + miceList.get(i).getValue() + " already exists",
                        "saveMouseIDAction"));
                flag = true;
            }
        }
        return flag;
    }

    /**
     * <b>Purpose:</b> default to litter strain and clear values  <br />
     */
    public void setMiceSearchPanelAction() {
        mouseDataModel.setWrappedData(new ArrayList<MouseEntity>());
        isMouseResultCountDisplayed = false;
        this.mouseSearch = new MouseSearchDTO();
        this.selectItemWrapper = new SelectItemWrapper();
        this.initializeOwner();

        // default to litter strain
        MatingEntity mEntity = this.litterEntity.getMatingKey();
        StrainEntity sEntity = new StrainEntity();

        if (mEntity != null) {
            sEntity = this.litterEntity.getMatingKey().getStrainKey();
        }
        // set the strain
        this.mouseSearch.setStrain(sEntity);
    }

    /**
     * getNextLitterID()
     * INPUTS: lMatingKey - primary key of mating table
     *
     * RETURNS: the next available (suggested) litter number for associated mating.
     *  If no litter number can be suggested, then and error string is appended to sErrMsg
     * EFFECTS: sErrMsg may be updated on inability to get another litter number
     * ERROR CONDITIONS: Invalid Mating Key returns an error message in sErrMsg
     * ASSUMES:
     * COMMENTS: Next litter number is calculated as follows.
     *  1) if no litter numbers are found associated with the mating key,
     *    then check if there is a suggested litter number associated with the 
     *    mating. If there is no suggested number the return sErrMsg
     *  2) if there are litter numbers associated with the mating, get the
     *   MAX number and increment it using the auto increment. Note that
     *   since MAX() returns the alphabetic max of litter ID, it is possible
     *   that the auto increment will actually increment the litter ID to
     *   an existing litter ID. For example, if litter ID t9 and t10 are associated
     *  with a mating, then litter t9 will be returned by the MAX function. On
     *  incrementing t9 we get t10 which already exists (but is alphabetically 
     *  lower in the sort).
     *
     *  It is also possible when the that the max litter number associated with a 
     *  mating is incremented, it produces a litter number associated with another 
     *  mating. MTS checks that the litter number it returns is unique.
     * @param lMatingKey
     * @param sErrMsg
     * @return 
     */
    public void generateNextLitterIDAction() {
        String sNextLitterID = "";
        int lMatingKey = 0;

        // make sure mating is selected
        if (this.litterEntity.getMatingKey() == null) {
            this.addToMessageQueue("Mating ID is missing, please select a Mating ID",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation",
                    "Mating ID is missing, please select a Mating ID"));
        } else {
            lMatingKey = this.litterEntity.getMatingKey().getMatingKey();
            System.out.println("lMatingKey " + lMatingKey);

            sNextLitterID = this.cycleLitterIDs(lMatingKey);

            if (sNextLitterID == null || sNextLitterID.equals("")) {
                // Get the last litter entry for this mating.
                String lLitterID = getRepositoryService().baseFindMaxLitterID(
                        new LitterEntity(), lMatingKey);

                if (lLitterID != null && !lLitterID.equals("")) {
                    sNextLitterID = this.mouseFunctions.incrementID(lLitterID,
                            "JCMS_LITTERID_INCREMENT_RIGHTMOST");
                } // zero is not a valid suggested first litter ID. For some reason the
                // mating forms put zero into the data if user has selected not to use 
                // auto litter nums so we need to check here.
                else if (this.litterEntity.getMatingKey() != null && this.litterEntity.getMatingKey().getSuggestedFirstLitterNum() > 0) {
                    sNextLitterID = Integer.toString(this.litterEntity.getMatingKey().
                            getSuggestedFirstLitterNum());
                } else {
                    this.addToMessageQueue("Unable to find a Litter # "
                            + "for you. The selected mating has no associated litters "
                            + "and does not have a suggested first litter number.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to "
                            + "find a Litter # for you. The selected mating "
                            + "has no associated litters and does not have a "
                            + "suggested first litter number.", "autoMouseIDAction"));
                }
            }

            // now check that the litter number we're suggesting is valid
            int lLoopCount = 0;
            
            while (!this.validateUniqueLitterNumber(sNextLitterID)) {
                lLoopCount++;
                if (lLoopCount > 100) {
                    this.addToMessageQueue("Unable to compute a next litter ID for you.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to "
                            + "find a litter number for this mating.", "generateNextLitterID"));
                    sNextLitterID = "";
                    break;
                }
                String sCurrentLitterID = sNextLitterID;
                                
                sNextLitterID = this.mouseFunctions.incrementID(sNextLitterID,
                      "JCMS_LITTERID_INCREMENT_RIGHTMOST");
                if (sNextLitterID.equals(sCurrentLitterID)) {
                    this.addToMessageQueue("Unable to compute a next litter ID for you.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to "
                            + "compute a next litter ID for you.", "generateNextLitterID"));
                    sNextLitterID = "";
                }
            }
        }
        System.out.println("sNextLitterID " + sNextLitterID);
        this.litterEntity.setLitterID(sNextLitterID);
    }

    /**
     * ASSUMES: The higher the litter_key value the later the entry.
     *       _litter_key is always older than (_litter_key + 1)
     *
     * COMMENT: If the user is assumed to letting JCMS generate
     *       the litter numbers, then we can apply the 'append a character'
     *       algorithm if the setup variable JCMS_LOOP_LITTER_NUMBERS
     *       is true.
     *       Here are the rules:
     *           1. If JCMS_LOOP_LITTER_NUMBERS is not "true" then we bail.
     *           2. If the user has already gone outside the range of allocated
     *                   litter ids, we bail.
     *           3. If the user is using a non-conventional naming system, e.g leading
     *               with alpha characters, we bail.
     * @param matingKey
     * @return 
     */
    public String cycleLitterIDs(int matingKey) {

        //Rule #1: JCMS_LOOP_LITTER_NUMBERS must be true...
        DbsetupEntity setupvar = new DbsetupEntity();
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_LOOP_LITTER_NUMBERS");

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
            if ((setupvar != null && setupvar.getMTSValue() != null
                    && !setupvar.getMTSValue().equals(""))) {
                if (setupvar.getMTSValue().equalsIgnoreCase("false"))
                return "";
            }
        }

        // Rule #2: The last entered litter ID must be a number followed
        //  by at most one character.

        // Get the last litter entry for this mating.
        int lLitterkey = getRepositoryService().baseFindMaxLitterKey(new 
                LitterEntity(), matingKey);

        if (lLitterkey == 0) {
            return "";
        }

        String sNumPortion;
        LitterEntity lEntity = (LitterEntity) getRepositoryService().
                baseFindByKey(new LitterEntity(), lLitterkey);
        String sLitterId = "";
        if (lEntity != null) {
            sLitterId = lEntity.getLitterID();
            System.out.println("sLitterId " + sLitterId);
        }

        // If the last character is a character and
        // we strip off the last character will it be a number
        String sAscValue = "";
        Boolean bHasSuffix = false, bRolledOver = false;

        // get the last character
        sAscValue = sLitterId.substring(sLitterId.length() - 1, sLitterId.length());
        System.out.println("last character " + sAscValue);

        // a character
        if (!this.isDigit(sAscValue.charAt(0))) {
            // strip off last character
            sNumPortion = sLitterId.substring(0, sLitterId.length() - 1);
            bHasSuffix = true; // note this for later...
        } else {
            sNumPortion = sLitterId;
            sAscValue = "";
        }
        System.out.println("sNumPortion " + sNumPortion);

        // if not int, then return false
        if (sNumPortion.length() > 0) {
            for (int i = 0; i < sNumPortion.length(); ++i) {
                if (!this.isDigit(sNumPortion.charAt(i))) {
                    return "";
                }
            }
        }

        // OK. We have either a number or a number with a single character appended.
        // let's try to generate a new litter ID.
        // sNumPortion has the numeric portion and sAscValue has the suffix (if
        // bHasSuffix is true.

        // get the SuggestedFirstLitterNum
        int lSugFirstLitNum = 0;
        if (this.litterEntity.getMatingKey() != null) {
            lSugFirstLitNum = this.litterEntity.getMatingKey().
                    getSuggestedFirstLitterNum();
            System.out.println("lSugFirstLitNum " + lSugFirstLitNum);
        }

        // Verify that it is not in the database.
        //While DCount("litterID", "Litter", "litterID='" & sLitterId & "'") <> 0
        if (!this.validateUniqueLitterNumber(sLitterId)) {

            // If it is the max rollover,
            // If it is in range increment,
            // If it is out of range, bail.
            if (sNumPortion != null && !sNumPortion.equals("")) {
                int intPortion = Integer.parseInt(sNumPortion);

                if (intPortion < (lSugFirstLitNum + 9)) { // Then ' increment it) {
                    sNumPortion = Integer.toString(intPortion + 1);
                } else if (intPortion == (lSugFirstLitNum + 9)) { // Then rollback
                    sNumPortion = Integer.toString(lSugFirstLitNum);
                    bRolledOver = true; // increment the suffix

                    if (bHasSuffix == true) {
                        char val = (char) (sAscValue.charAt(0) + 1);
                        sAscValue = Character.toString(val);
                        System.out.println("sAscValue when bHasSuffix == true "
                                + sAscValue);
                    } else {
                        sAscValue = "A";
                    }
                } else {
                    this.getLogger().logError(this.formatLogMessage("LitterID "
                            + "out of range.", "cycleLitterIDs"));
                    // We are outside the range
                    return "";
                }
            }
            sLitterId = sNumPortion + sAscValue;
            System.out.println("sLitterId " + sLitterId);
        }
        return sLitterId;
    }

    /**
     * A method to check if a given character is a digit
     * @param ch
     * @return 
     */
    public boolean isDigit(char ch) {
        if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }
    
    public void populateIDs(){
        boolean flag = false;
        if(litterEntity.getNumFemale() == null || litterEntity.getNumMale() == null){
            flag = true;
            this.addToMessageQueue("To populate IDs please provide a number male and number female.", FacesMessage.SEVERITY_ERROR);
        }
        else if((litterEntity.getNumFemale() + litterEntity.getNumMale()) == 0){
            flag = true;
            this.addToMessageQueue("To populate IDs number male and number female cannot both be zero.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            try{
                boolean success = setBaseMouseIDAction();
                if(mouseBaseNum.equals("") && success){
                    autoMouseIDAction();
                }
                else if(success){
                    boolean goodToGo = true;
                    if(litterEntity.getNumMale() == null){
                        this.addToMessageQueue("# males is missing, Please enter # males", FacesMessage.SEVERITY_ERROR);
                        goodToGo = false;
                    }
                    if(litterEntity.getNumFemale() == null){
                        this.addToMessageQueue("# females is missing, Please enter # females", FacesMessage.SEVERITY_ERROR);
                        goodToGo = false;
                    }
                    if(litterEntity.getNumMale().equals(0) && litterEntity.getNumFemale().equals(0)){
                        this.addToMessageQueue("Total number of males and females must be greater than 0.", FacesMessage.SEVERITY_ERROR);
                        goodToGo = false;
                    }
                    if(goodToGo){
                        populateMouseIDAction();
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    private void initializePageBuilder(){
        //First we need to know the current user's key
        String userKey = "";
        UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
        userKey = currentUser.getUserkey().toString();
        
        //All fields on this form that can be hidden must have the user's preferences looked up
        //and saved in PageBuilder.
        cvPreferencesDAO DAO = new cvPreferencesDAO();
        this.getPageBuilder().setHideNumberBornDead(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberBornDead")));
        this.getPageBuilder().setHideNumberCulledAtWean(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberCulledAtWean")));
        this.getPageBuilder().setHideNumberMissingAtWean(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberMissingAtWean")));
        this.getPageBuilder().setHideLitterType(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "litterType")));
        this.getPageBuilder().setHideLitterComments(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "comment")));
        this.pageBuilder.setHideLitterUseSchedules(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "useScheduleTermName")));
        this.pageBuilder.setHideLitterOrigin(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "origin")));
        this.pageBuilder.setHideLitterProtocolID(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "protocol")));
        this.pageBuilder.setHideLeavePupsInMatingPen(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "leavePupsInMatingPen")));
        
        //All fields that can have a default must have the default value saved too.
        this.pageBuilder.setDefaultLitterProtocolId((DAO.GetDefaultValue(userKey,"litterEdit", "protocol")));
        this.pageBuilder.setDefaultLitterOrigin((DAO.GetDefaultValue(userKey,"litterEdit", "origin")));
        this.pageBuilder.setDefaultLeavePupsInMatingPen((DAO.GetDefaultValue(userKey,"litterEdit", "leavePupsInMatingPen")));
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
     * @return the listSupportDTO
     */
    public ListSupportDTO getListSupportDTO() {
        return listSupportDTO;
    }

    /**
     * @param listSupportDTO the listSupportDTO to set
     */
    public void setListSupportDTO(ListSupportDTO listSupportDTO) {
        this.listSupportDTO = listSupportDTO;
    }

    /**
     * @return the litterEntity
     */
    public LitterEntity getLitterEntity() {
        return litterEntity;
    }

    /**
     * @param litterEntity the litterEntity to set
     */
    public void setLitterEntity(LitterEntity litterEntity) {
        this.litterEntity = litterEntity;
    }

    /**
     * @return the generationEntity
     */
    public CvGenerationEntity getGenerationEntity() {
        return generationEntity;
    }

    /**
     * @param generationEntity the generationEntity to set
     */
    public void setGenerationEntity(CvGenerationEntity generationEntity) {
        this.generationEntity = generationEntity;
    }

    /**
     * @return the ownerEntity
     */
    public OwnerEntity getOwnerEntity() {
        return ownerEntity;
    }

    /**
     * @param ownerEntity the ownerEntity to set
     */
    public void setOwnerEntity(OwnerEntity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    /**
     * @return the penEntity
     */
    public ContainerEntity getPenEntity() {
        return penEntity;
    }

    /**
     * @param penEntity the penEntity to set
     */
    public void setPenEntity(ContainerEntity penEntity) {
        this.penEntity = penEntity;
    }

    /**
     * @return the newPen
     */
    public PenBean getNewPen() {
        return newPen;
    }

    /**
     * @param newPen the newPen to set
     */
    public void setNewPen(PenBean newPen) {
        this.newPen = newPen;
    }

    /**
     * @return the isMatingResultCountDisplayed
     */
    public boolean isIsMatingResultCountDisplayed() {
        return isMatingResultCountDisplayed;
    }

    /**
     * @param isMatingResultCountDisplayed the isMatingResultCountDisplayed to set
     */
    public void setIsMatingResultCountDisplayed(boolean isMatingResultCountDisplayed) {
        this.isMatingResultCountDisplayed = isMatingResultCountDisplayed;
    }

    /**
     * @return the matingDataModel
     */
    public ListDataModel getMatingDataModel() {
        return matingDataModel;
    }

    /**
     * @param matingDataModel the matingDataModel to set
     */
    public void setMatingDataModel(ListDataModel matingDataModel) {
        this.matingDataModel = matingDataModel;
    }

    /**
     * @return the mouseSelectionETB
     */
    public ExtendedTableBean getMatingSelectionETB() {
        return matingSelectionETB;
    }

    /**
     * @param mouseSelectionETB the mouseSelectionETB to set
     */
    public void setMatingSelectionETB(ExtendedTableBean matingSelectionETB) {
        this.matingSelectionETB = matingSelectionETB;
    }

    /**
     * @return the matingSearch
     */
    public MatingSearchDTO getMatingSearch() {
        return matingSearch;
    }

    /**
     * @param matingSearch the matingSearch to set
     */
    public void setMatingSearch(MatingSearchDTO matingSearch) {
        this.matingSearch = matingSearch;
    }

    /**
     * @return the matingInfo
     */
    public MatingListCommon getMatingInfo() {
        return matingInfo;
    }

    /**
     * @param matingInfo the matingInfo to set
     */
    public void setMatingInfo(MatingListCommon matingInfo) {
        this.matingInfo = matingInfo;
    }

    /**
     * @return the matingList
     */
    public List<MatingEntity> getMatingList() {
        return matingList;
    }

    /**
     * @param matingList the matingList to set
     */
    public void setMatingList(List<MatingEntity> matingList) {
        this.matingList = matingList;
    }

    /**
     * @return the setupVar
     */
    public DbsetupEntity getSetupVar() {
        return setupVar;
    }

    /**
     * @param setupVar the setupVar to set
     */
    public void setSetupVar(DbsetupEntity setupVar) {
        this.setupVar = setupVar;
    }

    /**
     * @return the matingID
     */
    public int getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(int matingID) {
        this.matingID = matingID;
    }

    /**
     * @return the mouseEntity
     */
    public MatingEntity getMatingEntity() {
        return matingEntity;
    }

    /**
     * @param mouseEntity the mouseEntity to set
     */
    public void setMatingEntity(MatingEntity matingEntity) {
        this.matingEntity = matingEntity;
    }

    /**
     * @return the penID
     */
    public int getPenID() {
        return penID;
    }

    /**
     * @param penID the penID to set
     */
    public void setPenID(int penID) {
        this.penID = penID;
    }

    /**
     * @return the autoGenerateMouseRecords
     */
    public boolean isAutoGenerateMouseRecords() {
        return autoGenerateMouseRecords;
    }

    /**
     * @param autoGenerateMouseRecords the autoGenerateMouseRecords to set
     */
    public void setAutoGenerateMouseRecords(boolean autoGenerateMouseRecords) {
        this.autoGenerateMouseRecords = autoGenerateMouseRecords;
    }

    /**
     * @return the femalesFirst
     */
    public boolean isFemalesFirst() {
        return femalesFirst;
    }

    /**
     * @param femalesFirst the femalesFirst to set
     */
    public void setFemalesFirst(boolean femalesFirst) {
        this.femalesFirst = femalesFirst;
    }

    /**
     * @return the useMouseBaseNum
     */
    public boolean isUseMouseBaseNum() {
        return useMouseBaseNum;
    }

    /**
     * @param useMouseBaseNum the useMouseBaseNum to set
     */
    public void setUseMouseBaseNum(boolean useMouseBaseNum) {
        this.useMouseBaseNum = useMouseBaseNum;
    }

    /**
     * @return the mouseBaseNum
     */
    public String getMouseBaseNum() {
        return mouseBaseNum;
    }

    /**
     * @param mouseBaseNum the mouseBaseNum to set
     */
    public void setMouseBaseNum(String mouseBaseNum) {
        this.mouseBaseNum = mouseBaseNum;
    }

    /**
     * @return the leavePupsInMatingPen
     */
    public boolean isLeavePupsInMatingPen() {
        return leavePupsInMatingPen;
    }

    /**
     * @param leavePupsInMatingPen the leavePupsInMatingPen to set
     */
    public void setLeavePupsInMatingPen(boolean leavePupsInMatingPen) {
        this.leavePupsInMatingPen = leavePupsInMatingPen;
    }

    /**
     * @return the mouseOriginEntity
     */
    public CvMouseOriginEntity getMouseOriginEntity() {
        return mouseOriginEntity;
    }

    /**
     * @param mouseOriginEntity the mouseOriginEntity to set
     */
    public void setMouseOriginEntity(CvMouseOriginEntity mouseOriginEntity) {
        this.mouseOriginEntity = mouseOriginEntity;
    }

    /**
     * @return the numFemalesPerPen
     */
    public int getNumFemalesPerPen() {
        return numFemalesPerPen;
    }

    /**
     * @param numFemalesPerPen the numFemalesPerPen to set
     */
    public void setNumFemalesPerPen(int numFemalesPerPen) {
        this.numFemalesPerPen = numFemalesPerPen;
    }

    /**
     * @return the numMalesPerPen
     */
    public int getNumMalesPerPen() {
        return numMalesPerPen;
    }

    /**
     * @param numMalesPerPen the numMalesPerPen to set
     */
    public void setNumMalesPerPen(int numMalesPerPen) {
        this.numMalesPerPen = numMalesPerPen;
    }

    /**
     * @return the isMouseResultCountDisplayed
     */
    public boolean isIsMouseResultCountDisplayed() {
        return isMouseResultCountDisplayed;
    }

    /**
     * @param isMouseResultCountDisplayed the isMouseResultCountDisplayed to set
     */
    public void setIsMouseResultCountDisplayed(boolean isMouseResultCountDisplayed) {
        this.isMouseResultCountDisplayed = isMouseResultCountDisplayed;
    }

    /**
     * @return the mouseDataModel
     */
    public ListDataModel getMouseDataModel() {
        return mouseDataModel;
    }

    /**
     * @param mouseDataModel the mouseDataModel to set
     */
    public void setMouseDataModel(ListDataModel mouseDataModel) {
        this.mouseDataModel = mouseDataModel;
    }

    /**
     * @return the mouseSelectionETB
     */
    public ExtendedTableBean getMouseSelectionETB() {
        return mouseSelectionETB;
    }

    /**
     * @param mouseSelectionETB the mouseSelectionETB to set
     */
    public void setMouseSelectionETB(ExtendedTableBean mouseSelectionETB) {
        this.mouseSelectionETB = mouseSelectionETB;
    }

    /**
     * @return the mouseSearch
     */
    public MouseSearchDTO getMouseSearch() {
        return mouseSearch;
    }

    /**
     * @param mouseSearch the mouseSearch to set
     */
    public void setMouseSearch(MouseSearchDTO mouseSearch) {
        this.mouseSearch = mouseSearch;
    }

    /**
     * @return the mouseInfo
     */
    public MiceListCommon getMouseInfo() {
        return mouseInfo;
    }

    /**
     * @param mouseInfo the mouseInfo to set
     */
    public void setMouseInfo(MiceListCommon mouseInfo) {
        this.mouseInfo = mouseInfo;
    }

    /**
     * @return the mouseList
     */
    public List<MouseEntity> getMouseList() {
        return mouseList;
    }

    /**
     * @param mouseList the mouseList to set
     */
    public void setMouseList(List<MouseEntity> mouseList) {
        this.mouseList = mouseList;
    }

    /**
     * @return the mouseBaseID
     */
    public String getMouseBaseID() {
        return mouseBaseID;
    }

    /**
     * @param mouseBaseID the mouseBaseID to set
     */
    public void setMouseBaseID(String mouseBaseID) {
        this.mouseBaseID = mouseBaseID;
    }

    /**
     * @return the mouseFunctions
     */
    public MouseFunctionsBean getMouseFunctions() {
        return mouseFunctions;
    }

    /**
     * @param mouseFunctions the mouseFunctions to set
     */
    public void setMouseFunctions(MouseFunctionsBean mouseFunctions) {
        this.mouseFunctions = mouseFunctions;
    }

    /**
     * @return the miceList
     */
    public List<DisplayDTO> getMiceList() {
        return miceList;
    }

    /**
     * @param miceList the miceList to set
     */
    public void setMiceList(List<DisplayDTO> miceList) {
        this.miceList = miceList;
    }

    /**
     * @return the autoMouseDataModel
     */
    public ListDataModel getAutoMouseDataModel() {
        return autoMouseDataModel;
    }

    /**
     * @param autoMouseDataModel the autoMouseDataModel to set
     */
    public void setAutoMouseDataModel(ListDataModel autoMouseDataModel) {
        this.autoMouseDataModel = autoMouseDataModel;
    }

    /**
     * @return the autoMouseETB
     */
    public ExtendedTableBean getAutoMouseETB() {
        return autoMouseETB;
    }

    /**
     * @param autoMouseETB the autoMouseETB to set
     */
    public void setAutoMouseETB(ExtendedTableBean autoMouseETB) {
        this.autoMouseETB = autoMouseETB;
    }

    /**
     * @return the litterKey
     */
    public int getLitterKey() {
        return litterKey;
    }

    /**
     * @param litterKey the litterKey to set
     */
    public void setLitterKey(int litterKey) {
        this.litterKey = litterKey;
    }

    /**
     * @return the dam1
     */
    public MouseEntity getDam1() {
        return dam1;
    }

    /**
     * @param dam1 the dam1 to set
     */
    public void setDam1(MouseEntity dam1) {
        this.dam1 = dam1;
    }

    /**
     * @return the manual
     */
    public boolean isManual() {
        return manual;
    }

    /**
     * @param manual the manual to set
     */
    public void setManual(boolean manual) {
        this.manual = manual;
    }

    /**
     * @return the calculateWeanTagDates
     */
    public boolean isCalculateWeanTagDates() {
        return calculateWeanTagDates;
    }

    /**
     * @param calculateWeanTagDates the calculateWeanTagDates to set
     */
    public void setCalculateWeanTagDates(boolean calculateWeanTagDates) {
        this.calculateWeanTagDates = calculateWeanTagDates;
    }

    /**
     * @return the calendarEdit
     */
    public boolean isCalendarEdit() {
        return calendarEdit;
    }

    /**
     * @param calendarEdit the calendarEdit to set
     */
    public void setCalendarEdit(boolean calendarEdit) {
        this.calendarEdit = calendarEdit;
    }

    /**
     * @return the useSchedulesModel
     */
    public DualListModel getUseSchedulesModel() {
        return useSchedulesModel;
    }

    /**
     * @param useSchedulesModel the useSchedulesModel to set
     */
    public void setUseSchedulesModel(DualListModel useSchedulesModel) {
        this.useSchedulesModel = useSchedulesModel;
    }

    /**
     * @return the mouseProtocolEntity
     */
    public CvMouseProtocolEntity getMouseProtocolEntity() {
        return mouseProtocolEntity;
    }

    /**
     * @param mouseProtocolEntity the mouseProtocolEntity to set
     */
    public void setMouseProtocolEntity(CvMouseProtocolEntity mouseProtocolEntity) {
        this.mouseProtocolEntity = mouseProtocolEntity;
    }

    /**
     * @return the numberBornDead
     */
    public String getNumberBornDead() {
        return numberBornDead;
    }

    /**
     * @param numberBornDead the numberBornDead to set
     */
    public void setNumberBornDead(String numberBornDead) {
        this.numberBornDead = numberBornDead;
    }

    /**
     * @return the numberCulledAtWean
     */
    public String getNumberCulledAtWean() {
        return numberCulledAtWean;
    }

    /**
     * @param numberCulledAtWean the numberCulledAtWean to set
     */
    public void setNumberCulledAtWean(String numberCulledAtWean) {
        this.numberCulledAtWean = numberCulledAtWean;
    }

    /**
     * @return the numberMissingAtWean
     */
    public String getNumberMissingAtWean() {
        return numberMissingAtWean;
    }

    /**
     * @param numberMissingAtWean the numberMissingAtWean to set
     */
    public void setNumberMissingAtWean(String numberMissingAtWean) {
        this.numberMissingAtWean = numberMissingAtWean;
    }

    /**
     * @return the pageBuilder
     */
    public PageBuilder getPageBuilder() {
        return pageBuilder;
    }

    /**
     * @param pageBuilder the pageBuilder to set
     */
    public void setPageBuilder(PageBuilder pageBuilder) {
        this.pageBuilder = pageBuilder;
    }
}
