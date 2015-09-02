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

import jcms.web.cagecards.CageCardBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.exception.SaveException;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;
import jcms.web.dto.PenInfoDTO;
import jcms.web.service.RepositoryService;
import jcms.integrationtier.dto.MatingDTO;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.dto.cvContainerStatusDTO;
import jcms.integrationtier.dao.EditMatingDAO;
import jcms.integrationtier.dao.MouseSearchDAO;
import jcms.integrationtier.dao.ContainerDAO;
import java.text.SimpleDateFormat;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.*;
import jcms.integrationtier.dao.MatingDAO;
import jcms.integrationtier.dao.MouseUtilityDAO;
import jcms.integrationtier.dao.cvPreferencesDAO;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.web.common.PageBuilder;

/**
 *
 * @author rkavitha
 */
public class MatingBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0132231L;
    
    private SelectItemWrapper       selectItemWrapper;
    private ListSupportDTO          listSupportDTO;
    private MatingEntity            matingEntity;
    private String                  strainStatus      = "";
    private int                     matingKey = 0;
    private Mating                  businessLogic;
    
    private CvGenerationEntity      generationEntity;
    private OwnerEntity             ownerEntity;
    private ContainerEntity         penEntity;
    
    private boolean                 isPenResultCountDisplayed = false;
    private ListDataModel           penInfoDataModel;
    private ExtendedTableBean       penSelectionETB;
    private PenSearchDTO            penSearch;
    private PenBean                 newPen;
    private PenListCommon           penInfo;
    
    private boolean                 isMouseResultCountDisplayed;
    private ListDataModel           mouseDataModel;
    private ExtendedTableBean       mouseSelectionETB;
    private MouseSearchDTO          mouseSearch = null;
    private GenotypeSearchDTO       genotypeSearchDTO = null;
    private MiceListCommon          mouseInfo = null;
    private List<MouseEntity>       mouseList = null;
    private DbsetupEntity           setupVar = null;
    private MouseFunctionsBean      mouseFunctions = new MouseFunctionsBean();
    private PageBuilder             pageBuilder = new PageBuilder();
           
    private MouseEntity             dam1;
    private MouseEntity             dam2;
    private MouseEntity             sire;
    
    private String dam1ID              =  "";
    private String dam2ID              =  "";
    private String sireID              =  "";
    
    private String dam1Genotype        =  "";
    private String dam2Genotype        =  "";
    private String sireGenotype        =  "";
    
    private String dam1Strain          =  "";
    private String dam2Strain          =  "";
    private String sireStrain          =  "";
    private String dam1Pen          =  "";
    private String dam2Pen             =  "";
    private String sirePen          =  "";
    
    private int    searchPanel         =  0;
    
    private int    mouseKey            =  0;
    private int    strainKey           =  0;
    private CvDietEntity newDiet  =  new CvDietEntity();
    private String currentDiet         =  "";
    private boolean editing = false;
    
    private boolean getCageCard = false;
    private boolean goodCard = false;
    private boolean successful = false;
    private Integer entityKey;
    private EditMatingDAO editMatingDAO = new EditMatingDAO();
    private ArrayList<String> matings = editMatingDAO.getMatings();
    private ContainerDAO containerDAO = new ContainerDAO();
    private ArrayList<String> pens = containerDAO.getContainers();
    private ArrayList<String> penNames = containerDAO.getContainerNames();
    private ContainerDTO editContainerDTO = new ContainerDTO();
    private ContainerHistoryDTO editContainerHistoryDTO = new ContainerHistoryDTO();
    private cvContainerStatusDTO editContainerStatusDTO = new cvContainerStatusDTO();
    private String editContainerID = "";
    //control how cages are changed when editing cage
    private boolean editDam1Pen = false;
    private boolean editDam2Pen = false;
    private boolean editSirePen = false;
    //Control how pen portion of cage info for edit mating is rendered when use next available pen id is selected.
    private boolean next = false;
    //Control how the diet portion of dam1/2/sire is rendered
    private boolean editDam1Diet = false;
    private boolean editDam2Diet = false;
    private boolean editSireDiet = false;
    //Control whether old dam1/2/sire are rendered.
    private boolean showOldDam1 = false;
    private boolean showOldDam2 = false;
    private boolean showOldSire = false;
    //control how cages are changed when editing cage
    private boolean editOriginalDam1Pen = false;
    private boolean editOriginalDam2Pen = false;
    private boolean editOriginalSirePen = false;
    //Control how the diet portion of original dam1/2/sire is rendered
    private boolean editOriginalDam1Diet = false;
    private boolean editOriginalDam2Diet = false;
    private boolean editOriginalSireDiet = false;
    
    private MouseEntity originalDam1;
    private MouseEntity originalDam2;
    private MouseEntity originalSire;
    private boolean matingIdDisabled = true;
    
    
    private List<SelectItem> allele1FilterItems = new ArrayList<SelectItem>();    
    private List<SelectItem> allele2FilterItems = new ArrayList<SelectItem>();   
    
    private String cageID = "";
    
    public MatingBean() {
        
    }
    
    private void initializeForEdit(){
        
        //assorted booleans
        editDam1Pen = false;
        editDam2Pen = false;
        editSirePen = false;
        
        next = false;
        
        editDam1Diet = false;
        editDam2Diet = false;
        editSireDiet = false;
        
        showOldDam1 = false;
        showOldDam2 = false;
        showOldSire = false;
        
        //editing a mating, obv.
        editing = true;
        
        editContainerDTO = new ContainerDTO();
        editContainerHistoryDTO = new ContainerHistoryDTO();
        editContainerStatusDTO = new cvContainerStatusDTO();

        penNames = containerDAO.getContainerNames();
        pens = containerDAO.getContainers();
        setMatings(editMatingDAO.getMatings());
        selectItemWrapper = new SelectItemWrapper();
        
        allele1FilterItems.clear();
        allele1FilterItems.add(new SelectItem("", ""));
        allele2FilterItems.clear();
        allele2FilterItems.add(new SelectItem("", ""));
        
        ArrayList<OwnerEntity> ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"); 

        listSupportDTO = new ListSupportDTO(ownerLst);
        
        //Initialize the page builder here before it is changed by filling fields with current values
        this.initializePageBuilder();
        
        if(matingKey == 0){
            matingKey = Integer.parseInt(editMatingDAO.getMatingKeyByMatingID(new Integer(matingEntity.getMatingID()).toString()));
        }
        matingEntity = (MatingEntity) getRepositoryService().baseFind(new MatingEntity(matingKey));
        dam1 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam1Key()));
        setOriginalDam1((MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam1Key())));
        
        sire = matingEntity.getSireKey();
        //make sure that original sire is a SHALLOW copy
        setOriginalSire((MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getSireKey().getKey())));
        
        dam1ID = dam1.getId();
        currentDiet = dam1.getDiet();
        sireID = sire.getId();
        
        MouseUtilityDAO muDAO = new MouseUtilityDAO();
        try{
            dam1Genotype = muDAO.getMouseGenotypeByMouseKey(dam1.getMouseKey());
            sireGenotype = muDAO.getMouseGenotypeByMouseKey(sire.getMouseKey());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
        
        //sire info
        sireStrain = sire.getStrainKey().getStrainName();
        sirePen = new Integer(sire.getPenKey().getContainerID()).toString();
        this.putSessionParameter("sireStrainKey", 0);
        
        //dam1 info
        dam1Strain = dam1.getStrainKey().getStrainName();
        dam1Pen = new Integer(dam1.getPenKey().getContainerID()).toString();
        this.putSessionParameter("damStrainKey", dam1.getStrainKey().getStrainKey());
        
        //dam2 info
        if(matingEntity.getDam2Key() != null){
            dam2 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam2Key()));
            try{
                dam2Genotype = muDAO.getMouseGenotypeByMouseKey(dam2.getMouseKey());
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
            dam2Strain = dam2.getStrainKey().getStrainName();       
            dam2ID = dam2.getId();
            dam2Pen = new Integer(dam2.getPenKey().getContainerID()).toString();
            //SHALLOW copy
            setOriginalDam2((MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam2Key())));
        }
        else{
            dam2 = new MouseEntity();
            dam2Strain = "";       
            dam2ID = "";
            dam2Pen = "";
            setOriginalDam2(new MouseEntity());
        }
        
        setGenerationEntity(new CvGenerationEntity());
        if(matingEntity.getGeneration() != null){
            for (CvGenerationEntity entity : listSupportDTO.getCvGeneration()) {
                if(entity.getGeneration().equals(matingEntity.getGeneration())){
                    generationEntity = entity;
                }
            }
        }
        
        
        searchPanel = 0;
        setPenInfo(new PenListCommon());
        setPenSearch(new PenSearchDTO());
        setNewPen(new PenBean());
        setPenInfoDataModel(new ListDataModel());
        setPenSelectionETB(new ExtendedTableBean());
        
        //mating strain
        strainKey = matingEntity.getStrainKey().getStrainKey();
        //the mating diet.
        currentDiet = matingEntity.getProposedDiet();
        
        setIsMouseResultCountDisplayed(false);
        setMouseDataModel(new ListDataModel());
        setMouseSelectionETB(new ExtendedTableBean());

        setMouseSearch(new MouseSearchDTO());
        setGenotypeSearchDTO(new GenotypeSearchDTO());
        
        
        //start here
        
        for (LifeStatusEntity entity : listSupportDTO.getLifeStatus()) {
            if (entity.getLifeStatus().equals("A")) {
                this.mouseSearch.setLifeStatus(entity);
                break;
            }
        }
        setMouseInfo(new MiceListCommon());
        setMouseList(new ArrayList<MouseEntity>());

        setPenEntity(new ContainerEntity());

        setBusinessLogic(new Mating());
        setMatingKey(getBusinessLogic().getKeyFromRequest());
        System.out.println("mating key " + matingKey);

        if (getMatingKey() > 0) {
            setStrainStatus("All");
        }
        else {
            setStrainStatus("Active only");
        }


        if (ownerLst.size() > 0) {
            this.setOwnerEntity(ownerLst.get(0));
            System.out.println(ownerLst.get(0).getOwner());
        }
        
        for(OwnerEntity o : ownerLst){
            if(o.getOwner().equals(matingEntity.getOwner())){
                ownerEntity = o;
            }
        }
        
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_ENFORCE_APPROVED_MATINGS");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            //if  approvedstrains is checked, check if set up variable is true, 
            //then set the temp vlaue to true
            if ((dbEntity != null && dbEntity.getMTSValue() != null
                    && !dbEntity.getMTSValue().equals("")
                    && dbEntity.getMTSValue().equalsIgnoreCase("true"))) {
            }
        }
    }

    private void initialize() {
        
        //various rendering booleans
        editDam1Pen = false;
        editDam2Pen = false;
        editSirePen = false;
        
        next = false;
        
        editDam1Diet = false;
        editDam2Diet = false;
        editSireDiet = false;
        
        showOldDam1 = false;
        showOldDam2 = false;
        showOldSire = false;
        
        editing = false;
        
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"); 

        listSupportDTO = new ListSupportDTO(ownerLst);


        dam1ID              =  "";
        dam2ID              =  "";    
        sireID              =  "";

        searchPanel         =  0;    
        mouseKey            =  0;
        strainKey           =  0;
        currentDiet         =  "";
        dam1Strain          =  "";
        dam2Strain          =  "";
        sireStrain          =  "";
        dam1Pen             = "";
        sirePen             = "";
        dam2Pen             = "";
        
        allele1FilterItems.clear();
        allele1FilterItems.add(new SelectItem("", ""));
        allele2FilterItems.clear();
        allele2FilterItems.add(new SelectItem("", ""));
        
        setGenerationEntity(new CvGenerationEntity());
        searchPanel = 0;
        setPenInfo(new PenListCommon());
        setPenSearch(new PenSearchDTO());
        setNewPen(new PenBean());
        setPenInfoDataModel(new ListDataModel());
        setPenSelectionETB(new ExtendedTableBean());

        this.putSessionParameter("damStrainKey", 0);
        this.putSessionParameter("sireStrainKey", 0);

        selectItemWrapper = new SelectItemWrapper();

        setIsMouseResultCountDisplayed(false);
        setMouseDataModel(new ListDataModel());
        setMouseSelectionETB(new ExtendedTableBean());

        setMouseSearch(new MouseSearchDTO());
        setGenotypeSearchDTO(new GenotypeSearchDTO());
        newDiet  =  new CvDietEntity();
        
        dam1 = new MouseEntity();
        dam2 = new MouseEntity();

        // set the lifestatus
        // step through elements from cv and when match found, assign it
        for (LifeStatusEntity entity : listSupportDTO.getLifeStatus()) {
            if (entity.getLifeStatus().equals("A")) {
                this.mouseSearch.setLifeStatus(entity);
                break;
            }
        }
        setMouseInfo(new MiceListCommon());
        setMouseList(new ArrayList<MouseEntity>());

        setOwnerEntity(new OwnerEntity());
        setPenEntity(new ContainerEntity());

        setBusinessLogic(new Mating());
        setMatingKey(getBusinessLogic().getKeyFromRequest());
        System.out.println("mating key " + matingKey);

        if (getMatingKey() > 0) {
            setStrainStatus("All");
        }
        else {
            setStrainStatus("Active only");
        }

        // set defaults
        System.out.println("setting defaults");
        this.matingEntity = new MatingEntity();
        this.matingEntity.setSireKey(new MouseEntity());
        this.matingEntity.setMatingDate(new Date());

        //HERE WE MUST INSERT THE USER PREFERENCES FOR DEFAULT VALUES             
        //Wean Time
        this.matingEntity.setWeanTime(true);
        if (pageBuilder.getDefaultWeanTime() != null){
            if (!pageBuilder.getDefaultWeanTime().equals("")) {
                this.matingEntity.setWeanTime(Boolean.parseBoolean(pageBuilder.getDefaultWeanTime()));
            }
        }

        //Needs Genotyping
        this.matingEntity.setNeedsTyping(false);
        if (pageBuilder.getDefaultNeedsTyping() != null){
            if (!pageBuilder.getDefaultWeanTime().equals("")) {
                this.matingEntity.setNeedsTyping(Boolean.parseBoolean(pageBuilder.getDefaultNeedsTyping()));
            }
         }

         //Initialize any new diet default here so it will show on the screen
         if (pageBuilder.getDefaultMatingDiet() != null){
             for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                 if (entity.getDiet().equalsIgnoreCase(pageBuilder.getDefaultMatingDiet())) {
                     this.newDiet = entity;
                     break;
                 }
             }
         }

        if (ownerLst.size() > 0) {
            this.setOwnerEntity(ownerLst.get(0));
            System.out.println(ownerLst.get(0).getOwner());
        }
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_ENFORCE_APPROVED_MATINGS");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            //if  approvedstrains is checked, check if set up variable is true, 
            //then set the temp vlaue to true
            if ((dbEntity != null && dbEntity.getMTSValue() != null
                    && !dbEntity.getMTSValue().equals("")
                    && dbEntity.getMTSValue().equalsIgnoreCase("true"))) {
            }
        }
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String saveAction() throws Exception {
        setSuccessful(false);
        boolean flag = false;
        int cnt = 0;
        
        // ******************************************************
        // Transfers control from the FORM view to the LIST view
        // pending a successful Save action.
        // ******************************************************

        System.out.println("About to Save");
        cageID = "";

        if (this.matingEntity != null) {
            try {
                //validate strain and generation
                if (this.matingEntity.getStrainKey() == null) {
                    this.addToMessageQueue("Strain is missing. Please select a Strain.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Strain is missing. Please select a Strain."));
                    flag = true;
                }

                if (this.generationEntity == null) {
                    this.addToMessageQueue("Generation is missing. Please select a Generation.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Generation is missing. Please select a Generation."));
                    flag = true;
                }
                
                // validate dam1
                if (this.dam1ID == null || this.dam1ID.equals("")) {
                    this.addToMessageQueue("Dam1 is missing. Please select a Dam1.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Dam1 is missing. Please select a Dam1."));
                    flag = true;
                }
                else {
                    // check if it is valid
                    MouseEntity me = this.mouseFunctions.findMouse(dam1ID);
                    
                    if (me == null) {
                        this.addToMessageQueue("Dam1 is invalid.",
                            FacesMessage.SEVERITY_ERROR);

                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Dam1 is invalid."));
                        flag = true;
                    }  
                    else {
                        if (!me.getSex().equalsIgnoreCase("f")) {
                            this.addToMessageQueue("Dam1 ID " + this.dam1ID + " is not a Female",
                                    FacesMessage.SEVERITY_ERROR);
                            this.getLogger().logError(this.formatLogMessage("Dam1 ID "
                                    + this.dam1ID + " is not a Female", "ValidateDam1IDAction"));
                            flag = true;
                        }
                    }
                }
                
                // validate dam2
                if (this.dam2ID != null && !this.dam2ID.equals("")) {
                    // check if it is valid
                    MouseEntity me = this.mouseFunctions.findDam(dam2ID);
                    
                    if (me == null) {
                        this.addToMessageQueue("Dam2 is invalid.",
                            FacesMessage.SEVERITY_ERROR);

                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Dam2 is invalid."));
                        flag = true;
                    }
                    else {
                        if (!me.getSex().equalsIgnoreCase("f")) {
                            this.addToMessageQueue("Dam2 ID " + this.dam2ID + " is not a Female",
                                    FacesMessage.SEVERITY_ERROR);
                            this.getLogger().logError(this.formatLogMessage("Dam2 ID "
                                    + this.dam2ID + " is not a Female", "ValidateDam2IDAction"));
                            flag = true;
                        }
                    }
                }                               
                
                // validate sire
                if (this.matingEntity.getSireKey() == null) {
                    this.addToMessageQueue("Sire is missing. Please select a Sire.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Sire is missing. Please select a Sire."));
                    flag = true;
                }
                else {
                    // check if it is valid
                    MouseEntity me = this.mouseFunctions.findSire(this.
                            matingEntity.getSireKey().getId());
                    
                    if (me == null) {
                        this.addToMessageQueue("Sire is invalid.",
                            FacesMessage.SEVERITY_ERROR);

                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Sire is invalid."));
                        flag = true;
                    }
                    else {
                        if (!me.getSex().equalsIgnoreCase("m")) {
                            this.addToMessageQueue("Sire ID " + me.getId() + " is not a Male",
                                    FacesMessage.SEVERITY_ERROR);
                            this.getLogger().logError(this.formatLogMessage("Sire ID "
                                    + me.getId() + " is not a Male", "ValidateSireIDAction"));
                            flag = true;
                        }
                    }
                }
                
                //validate pen                
                if ((this.matingEntity.getSuggestedPenID() == null || this.matingEntity.getSuggestedPenID() == 0)
                        && !newPen.isNextPen()) {
                    this.addToMessageQueue("Cage# is missing. Please select a Cage#.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Cage# is missing. Please select a Cage#."));
                    flag = true;
                }
                
                //validate room of the pen, if there is no DEFAULT PEN according to the 
                //DbSetup table and no room selected in the pen
                if(newPen.getRoom() == null || newPen.getRoom().getRoomKey() == null){
                    //get the default room if it exists
                    DbsetupEntity dbe = (DbsetupEntity) getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "MTS_DEFAULT_MOUSE_ROOM");
                    if(dbe.getMTSValue() == null || dbe.getMTSValue().equals("")){
                        flag = true;
                        this.addToMessageQueue("No default room set in the setup variables, to use next cage ID please set a value for Default Room.", FacesMessage.SEVERITY_ERROR);
                    }
                }
                else {
                    if(newPen.isNextPen()){
                        matingEntity.setSuggestedPenID(Integer.parseInt(containerDAO.getNextAvailablePenId()));
                    }
                    // check if it is valid
                    ContainerEntity me = this.mouseFunctions.findPen(this.matingEntity.getSuggestedPenID());
                    
                    if (me == null) {
                        clearPenPopupAction();
                        this.newPen.getPenEntity().setContainerID(matingEntity.getSuggestedPenID());
                        this.newPen.saveAndCloseAction();
                    }
                }
                
                if(generationEntity.getGeneration() == null || generationEntity.getGeneration().equals("")){
                    this.addToMessageQueue("Generation is missing, please select a generation.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Generation is missing, please select a generation."));
                    flag = true;
                }
                
                if(ownerEntity == null || ownerEntity.getOwner().equals("")){
                    this.addToMessageQueue("Owner is missing, please select a Owner.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Owner is missing, please select a owner."));
                    flag = true;
                }
                
                if(matingEntity.getMatingDate() == null){
                    this.addToMessageQueue("Mating date is missing, please select a mating date.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating date is missing, please select a mating date."));
                    flag = true;
                }
                
                if (!flag) {

                    // check if mating already exists with dam and sire
                    cnt = 0;
                    // if dam1 exists, then check if mating already exists with this 
                    // dam and sire
                    cnt = new RepositoryService().findMatingByDamAndSire(this.
                            dam1.getMouseKey(), this.sire.getMouseKey());

                    if (cnt > 0) {
                        this.addToMessageQueue("Mating already exists with selected "
                                + "Dam1 " + this.dam1ID + " and Sire " + this.sireID,
                                FacesMessage.SEVERITY_WARN);
                        this.getLogger().logWarn(this.formatLogMessage("Mating "
                                + "already exists with Dam1 "
                                + this.dam1ID + " and Sire " + this.sireID, "Validate"));
                    }

                    if (this.generationEntity != null) {
                        matingEntity.setGeneration(this.generationEntity.getGeneration());
                    }

                    if (this.ownerEntity != null) {
                        matingEntity.setOwner(this.ownerEntity.getOwner());
                    }

                    System.out.println("Mating Entity Details");
                    System.out.println("Generation " + matingEntity.getGeneration());
                    System.out.println("Owner " + matingEntity.getOwner());
                    System.out.println("Dam1Key " + matingEntity.getDam1Key());
                    System.out.println("Dam2Key " + matingEntity.getDam2Key());
                    System.out.println("SireKey " + matingEntity.getSireKey().getId());

                    // for insert operation
                    if (matingEntity.getMatingKey() == null || matingEntity.getMatingKey() == 0) {

                        // set the SuggestFirstLitterNum
                        JCMSDbInfoEntity dbInfo = this.setLitterNumber();

                        // set the mating ID
                        Integer id = this.getRepositoryService().baseFindMaxMatingID(new MatingEntity());

                        if (null == id || 0 == id) {
                            this.matingEntity.setMatingID(1);
                        } else {
                            this.matingEntity.setMatingID(id + 1);
                        }
                        System.out.println("MatingID " + matingEntity.getMatingID());

                        // step through elements from cv and when match found, assign it
                        for (CvCrossstatusEntity entity : listSupportDTO.getCvCrossStatus()) {
                            if (entity.getCrossStatus().equalsIgnoreCase("active")) {
                                this.matingEntity.setCrossStatuskey(entity);
                                System.out.println("Mating status " + matingEntity.getCrossStatuskey().getCrossStatus());
                                break;
                            }
                        }

                        // generate primary key
                        Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(matingEntity);

                        if (null == pk || 0 == pk) {
                            matingEntity.setMatingKey(1);
                        } else {
                            matingEntity.setMatingKey(pk + 1);
                        }

                        // set the version
                        matingEntity.setVersion(0);

                        new SaveAppService().baseCreate(this.matingEntity);
                        //for now only doing natural matings...
                        new MatingDAO().insertNaturalMatingUnitLink(matingEntity);
                        
                        if (this.newDiet != null && newDiet.getDietKey() != null && newDiet.getDietKey() > 0) {
                            this.getLogger().logInfo(this.formatLogMessage("save", "Mating ID " + matingEntity.getMatingID() + 
                                    " with Dam " + this.dam1.getId() +
                                    " and Sire " + this.sire.getId() +
                                    " has been inserted and mice moved to mating cage " 
                                    + matingEntity.getSuggestedPenID()));

                            this.addToMessageQueue("Mating ID " + matingEntity.getMatingID() +
                                    " with Dam " + this.dam1.getId() +
                                    " and Sire " + this.sire.getId() 
                                    + " has been inserted and mice saved with new "
                                    + "diet and moved to mating cage "
                                    + matingEntity.getSuggestedPenID(), FacesMessage.SEVERITY_INFO);
                            
                            this.setCurrentDiet(this.newDiet.getDiet());
                        }
                        else {
                            this.getLogger().logInfo(this.formatLogMessage("save",
                                "Mating ID " + matingEntity.getMatingID() + 
                                " has been inserted"));

                            this.addToMessageQueue( "Mating ID " + matingEntity.getMatingID() + 
                                    " with Dam " + this.dam1.getId() +
                                    " and Sire " + this.sire.getId() +
                                    " has been inserted and mice moved to mating cage " 
                                    + matingEntity.getSuggestedPenID(), FacesMessage.SEVERITY_INFO);
                            setSuccessful(true);
                        }

                        // update dbInfo table with new maxAutoLitter ID, 
                        // update it only after successfully adding mating.
                        new SaveAppService().baseEdit(dbInfo);
                        this.getLogger().logInfo(this.formatLogMessage("save",
                                "DbInfo " + dbInfo.getMaxAutoLitterNum() + " has been updated"));


                        // move mice to pen after successful mating
                        // move dams and sire to mating pen
                        ContainerEntity pen = new ContainerEntity();
                        
                        pen = this.mouseFunctions.findPen(this.matingEntity.getSuggestedPenID());

                        this.moveMouseToPen(this.dam1, pen);
                        dam1.setPenKey(pen);

                        // if dam2 exists
                        if (this.dam2ID != null && !this.dam2ID.equals("")) {
                            this.moveMouseToPen(this.dam2, pen);
                            dam2.setPenKey(pen);
                        }

                        this.moveMouseToPen(this.sire, pen);
                        sire.setPenKey(pen);
                        //successful save should only be true if save was successful and cage met business rules.
                        if (getCageCard) {
                            CageCardBean theCard = new CageCardBean();
                            theCard.setEntityKey(entityKey);
                            theCard.setCageID(this.matingEntity.getSuggestedPenID().toString());
                            theCard.setCardQuantity("single");
                            theCard.setCardTypeMask("mating");
                            theCard.initializeDTOForDownload();
                            theCard.validateCageCardAction();
                            this.successful = theCard.isGoodCard();
                        }
                        
                        //update Dam1 and Sire pens in view.
                        this.setDam1Pen(new Integer(this.matingEntity.getSuggestedPenID()).toString());
                        this.setSirePen(new Integer(this.matingEntity.getSuggestedPenID()).toString());
                        
                        //increment pen or clear value
                        cageID = matingEntity.getSuggestedPenID().toString();
                        if(newPen.isNextPen()){
                            matingEntity.setSuggestedPenID(null);
                        }
                        else{
                            matingEntity.setSuggestedPenID(null);
                        }
                        
                        // set mating_key to 0 to allow successive matings and 
                        this.matingEntity.setMatingKey(0);
                        // If there is a user preference default, set the new diet to it here.
                        //previously, it was always clearing the new diet combo box.
                        this.setNewDiet(new CvDietEntity());
                        if (pageBuilder.getDefaultMatingDiet() != null){
                            for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                                if (entity.getDiet().equalsIgnoreCase(pageBuilder.getDefaultMatingDiet())) {
                                    this.newDiet = entity;
                                    break;
                                }
                            }
                        }
                    } // edit operation
                    else {
                        this.getLogger().logError(this.formatLogMessage("save",
                                "Current Mating ID " + matingEntity.getMatingID() + " already submitted"));

                        this.addToMessageQueue("Current Mating ID " + 
                                " with Dam " + this.dam1.getId() +
                                " and Sire " + this.sire.getId() +
                                " already submitted", FacesMessage.SEVERITY_ERROR);
                    }
                }
            } catch (SaveException se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction: " + se));
                return null;
            } // General catch-all for failed saves. The exception's message has already been customized for user display.
            catch (SaveEntityException ex) {
                String msg = "The system failed to save any mouse updates.  "
                        + "Please report this problem to the web master with date "
                        + "and time of error.  ";

                // Display user friendly error message
                this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction: " + ex));

                // Return null to indicate that the JSF implementation
                // should reload the same page.
                return null;
            } catch (Exception se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction: " + se));
                return null;
            }
        } else {
            this.getLogger().logError(this.formatLogMessage("Mating entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    public void makeSuccessfulFalse(){
        successful=false;
        this.addToMessageQueue("Mating ID " + matingEntity.getMatingID()
                                    + " has been inserted and mice moved to mating cage "
                                    + dam1.getPenKey().getContainerID(), FacesMessage.SEVERITY_INFO);
    }
    
    public void previewCageCard() {
        boolean flag = false;
        if(this.entityKey == null || entityKey.equals(0)){
            flag = true;
            this.addToMessageQueue("Please select a cage card from the drop down at the bottom of the form.", FacesMessage.SEVERITY_ERROR);
        }
        if(dam1.getPenKey() == null && matingEntity.getSuggestedPenID() == null){
            flag = true;
            this.addToMessageQueue("Please provide a cage ID in the cage ID field.", FacesMessage.SEVERITY_ERROR);
        }        
        try{
            if(!flag){
                CageCardBean theCard = new CageCardBean();
                theCard.setEntityKey(entityKey);
                if(dam1.getPenKey() != null){
                    theCard.setCageID(new Integer(dam1.getPenKey().getContainerID()).toString());
                }
                else{
                    theCard.setCageID(matingEntity.getSuggestedPenID().toString());
                }
                theCard.setCardQuantity("single");
                theCard.initializeDTOForDownload();
                theCard.downloadCageCardAction();
                System.out.println("Card type fits cage requested?" + goodCard);
            }
            else{
                this.addToMessageQueue("Please select a cage card from the drop down at the bottom of the form.", FacesMessage.SEVERITY_ERROR);
            }
            this.successful = false;
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
    
    public void cageCardTest() throws Exception{
        this.getCageCard = true;
        if(this.entityKey != null && !entityKey.equals(0)){
            saveAction();
        }
        else{
            this.addToMessageQueue("Please select a cage card from the drop down at the bottom of the form.", FacesMessage.SEVERITY_ERROR);
        }
        this.getCageCard = false;
    }

    public String addMatingAction() {
        System.out.println("Add mating action");
        this.initializePageBuilder();
        this.initialize();

        return "addMating";
    }
    
    public void nextAvailableIDListener(){
        if(newPen.isNextPen()){
            try{
              
            }
            catch(Exception e){

            }
        }
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearNewPenPopupAction() {
        this.newPen.clearPenAction();
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearPenPopupAction() {

        System.out.println("Clear popup is called");
        penInfoDataModel.setWrappedData(new ArrayList<PenInfoDTO>());
        isPenResultCountDisplayed = false;
        this.penSearch = new PenSearchDTO();
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearMousePopupAction() {

        mouseDataModel.setWrappedData(new ArrayList<MouseEntity>());
        isMouseResultCountDisplayed = false;
        mouseSearch = new MouseSearchDTO();
        genotypeSearchDTO = new GenotypeSearchDTO();
        selectItemWrapper = new SelectItemWrapper();
    }

    public void strainStatusAction(ValueChangeEvent event) {
        this.setStrainStatus(event.getNewValue().toString());

        if (event.getNewValue().toString().equalsIgnoreCase("Approved only")) {
            this.setStrainStatus("Approved only");
            try {
                this.setApprovedStrainsAction();
            } catch (Exception e) {
                this.addToMessageQueue("Unable to display approved strains" + e, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Load Approved Strains Error ", "Unable to display approved strains: " + e));
                System.out.println(e);
            }
        } 
        this.selectItemWrapper = new SelectItemWrapper();
        System.out.println("this.strainStatus " + this.getStrainStatus());
    }

    public void validateMatingDateValueChangeListener(ValueChangeEvent event) {
        boolean flag = true;
        this.matingEntity.setMatingDate((Date)event.getNewValue());

        // date cannot be greater than today
        if (this.matingEntity.getMatingDate().compareTo(new Date()) > 0) {
            this.addToMessageQueue("Mating Date cannot be greater than Today",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Mating Date cannot be greater than Today"));
            flag = false;
        }
    }

    /**
     * <b>Purpose:</b> Save a PenBean<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String savePenAction() throws Exception {
        this.newPen.saveAction();
        this.matingEntity.setSuggestedPenID(newPen.getPenEntity().getContainerID());
        // Redisplay the page.
        return null;
    }

    /**
     * <b>Purpose:</b> Save a PenBean<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String savePenAndCloseAction() throws Exception {
        this.newPen.saveAndCloseAction();
        this.matingEntity.setSuggestedPenID(newPen.getPenEntity().getContainerID());
        this.newPen.setNextPen(false);
        // Redisplay the page.
        return null;
    }

    public void selectPenAction() {
        System.out.println("Selected Pen");
        this.matingEntity.setSuggestedPenID(penInfo.getSelectedPen(this.penSelectionETB, this.penInfoDataModel));
        System.out.println("new pen ID " + this.matingEntity.getSuggestedPenID());
        if(!containerDAO.getMiceInPen(matingEntity.getSuggestedPenID().toString()).equals("0")){
            this.addToMessageQueue("This cage already contains mice, are you sure you wish to create the mating in this cage?", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void penSearchAction() {
        System.out.println("Search Results");

        // check if any search criteria is selected, else throw error message
        if (this.penSearch != null && ((penSearch.getPenID() != null
                && !penSearch.getPenID().equals(""))
                || (!penSearch.getContainerName().equals("") && penSearch.getContainerName() != null)
                || (penSearch.getContainerStatus() != null
                && !penSearch.getContainerStatus().getContainerStatus().equals("")
                && penSearch.getContainerStatus().getContainerStatus() != null)
                || (penSearch.getRoom() != null
                && penSearch.getRoom().getRoomName() != null
                && !penSearch.getRoom().getRoomName().equals("")))) {

            this.setPenInfoDataModel(this.penInfo.penSearch(this.penSearch));
            setIsPenResultCountDisplayed(true);
        } else {
            this.addToMessageQueue("Select some search criteria",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Select some search criteria"));
        }
    }
    
    public void setApprovedStrainsAction() throws Exception {
        Boolean requiredField = false;

        if (this.getStrainStatus().equalsIgnoreCase("Approved only")) {
            // if dam1 and sire exists, then set their strain keys into session
            if (this.dam1ID != null && !this.dam1ID.equals("")) {
                if (this.dam1 != null && this.dam1.getStrainKey() != null
                    && this.dam1.getStrainKey().getStrainKey() > 0 && this.matingEntity.
                    getSireKey() != null && this.matingEntity.getSireKey().getStrainKey() != null && 
                    this.matingEntity.getSireKey().getStrainKey().getStrainKey() > 0) {
                System.out.println("dam Strain key " + this.dam1.getStrainKey().getStrainKey());
                System.out.println("sire Strain key " + this.sire.getStrainKey().getStrainKey());

                // set the dam and sire values
                this.putSessionParameter("damStrainKey", this.dam1.getStrainKey().getStrainKey());
                this.putSessionParameter("sireStrainKey", this.sire.getStrainKey().getStrainKey());
                } else {
                    requiredField = true;
                }
            } else {
                requiredField = true;
            }
            if (requiredField) {
                this.addToMessageQueue("No approved mating strains defined or dam and sire strains are not selected.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "No approved mating strains defined or dam and sire strains are not selected."));
                this.setStrainStatus("All");
            }
        }
    }
    
    /**
     * if the user manually type in dam 1 ID, it is validated and dam1 
     * information is filled
     * @throws Exception 
     */
    public void setDam1IDValueChangeListener(ValueChangeEvent event) throws Exception {
        System.out.println("Selected Mouse");
        showOldDam1 = true;
        
        this.dam1ID = event.getNewValue().toString();
        this.matingEntity.setDam1Key(0);
        this.setCurrentDiet("");
        this.setDam1(new MouseEntity());
        this.setDam1Pen("");
        this.setStrainKey(0);
        this.mouseSearch.setStrain(new StrainEntity());
        this.setDam1Strain("");

        if (this.dam1ID != null && !this.dam1ID.equals("")) {
            // Check that mating id is unique.
            MouseEntity mouse = this.mouseFunctions.findMouse(this.dam1ID);
            
            if (mouse != null) {
                if (!mouse.getSex().equalsIgnoreCase("f")) {
                    this.addToMessageQueue("Dam1 ID " + this.dam1ID + " is not a Female", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Dam1 ID " + this.dam1ID + " is not a Female", "ValidateDam1IDAction"));
                } 
                else if(!mouseOwned(mouse.getOwner())){
                    this.addToMessageQueue("Dam1 ID " + this.dam1ID + " is not owned by a workgroup you belong to.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Dam1 ID " + this.dam1ID + " is not owned by a workgroup you belong to.", "ValidateDam1IDAction"));
                }
                else {
                    this.matingEntity.setDam1Key(mouse.getMouseKey());
                    this.setDam1ID(mouse.getId());
                    this.setCurrentDiet(mouse.getDiet());
                    this.setDam1(mouse);
                    this.dam1Genotype = new MouseUtilityDAO().getMouseGenotypeByMouseKey(this.dam1.getMouseKey());
                    StrainEntity sEntity = mouse.getStrainKey();
                    this.setDam1Pen(new Integer(mouse.getPenKey().getContainerID()).toString());

                    // check if sire has been already used for a mating, then give a warning
                    ITBaseEntityTable table = (new RepositoryService().findMatingByMouse(mouse.getMouseKey()));
                    MatingEntity mating = null;

                    for (ITBaseEntityInterface entity : table.getList()) {
                        mating = (MatingEntity) entity;
                    }

                    if (mating != null) {
                        this.addToMessageQueue("Mating already exists with Dam1 "
                                + this.dam1ID, FacesMessage.SEVERITY_WARN);
                        this.getLogger().logWarn(this.formatLogMessage("Mating "
                                + "already exists with Dam1 " + this.dam1ID, "Validate"));
                    }

                    if (sEntity != null) {
                        this.setStrainKey(sEntity.getStrainKey());
                        this.mouseSearch.setStrain(sEntity);
                        this.setDam1Strain(sEntity.getStrainName());
                    }
                    //tell user they have to click save
                    if(editing){
                        this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
                    }
                }
            } else {
                this.addToMessageQueue("Dam1 ID " + this.dam1ID + " is not valid",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Dam1 ID "
                        + this.dam1ID + " is not valid", "ValidateDam1IDAction"));
            }
        } else {
            this.addToMessageQueue("Dam1 is missing. Please select a Dam1.",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Dam1 is missing. Please select a Dam1."));
        }

    }

    public void selectDam1Action() throws Exception {
        MouseEntity mouse = new MouseEntity();

        mouse = mouseInfo.getSelectedMouse(this.mouseSelectionETB,
                this.mouseDataModel);
        if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
            if(editing){
                showOldDam1 = true;
            }
            this.matingEntity.setDam1Key(mouse.getMouseKey());
            this.setDam1ID(mouse.getId());
            this.setCurrentDiet(mouse.getDiet());

            // set the dam1 strain
            if (this.getDam1ID() != null && !this.getDam1ID().equals("")) {
                MouseEntity mEntity = this.mouseFunctions.findMouseByKey(mouse.getMouseKey());

                if (mEntity != null) {
                    this.setDam1(mEntity);
                    this.setDam1Genotype(new MouseUtilityDAO().getMouseGenotypeByMouseKey(this.dam1.getMouseKey()));
                    StrainEntity sEntity = mEntity.getStrainKey();
                    this.setDam1Pen(new Integer(mEntity.getPenKey().getContainerID()).toString());

                    if (sEntity != null) {
                        this.setStrainKey(sEntity.getStrainKey());
                        this.mouseSearch.setStrain(sEntity);
                        this.setDam1Strain(sEntity.getStrainName());
                    }

                    // check if sire has been already used for a mating, then give 
                    // a warning
                    ITBaseEntityTable table = (new RepositoryService().findMatingByMouse(mouse.getMouseKey()));
                    MatingEntity mating = null;

                    for (ITBaseEntityInterface entity : table.getList()) {
                        mating = (MatingEntity) entity;
                    }

                    if (mating != null) {
                        this.addToMessageQueue("Mating already exists with Dam1 "
                                + this.dam1ID, FacesMessage.SEVERITY_WARN);
                        this.getLogger().logWarn(this.formatLogMessage("Mating "
                                + "already exists with Dam1 " + this.dam1ID, "Validate"));
                    }
                    if(editing){
                        this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
                    }
                }
            }
            System.out.println("Selected Mouse key " + mouse.getMouseKey());
        }
    }
    
    /**
     * 
     * @param   owner - the owner of the mouse
     * @param   owners - a list of the owners the user belongs to
     * @return  boolean whether mouse is owned by one of the owners in the list
     */
    private boolean mouseOwned(String owner){
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst");
        for(OwnerEntity theOwner : owners){
            if(owner.equals(theOwner.getOwner())){
                return true;
            }
        }
        return false;
    }
    
    /*
     * gets a litter number from the dbinfo table and increments the litter
     * number. The litter number returned is maxAutoLitterID from table + 1.
     * maxAutoLitterID in table is always last used litter number (not next
     * available) Uses pessimistic locking so no other person can access the
     * database at the same time.
     *
     */
    public JCMSDbInfoEntity setLitterNumber() throws Exception {
        JCMSDbInfoEntity dbInfoEntity = new JCMSDbInfoEntity();
        List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();
        int litterNum = 0;
        int maxLitterNum = 0;
        int nextLitterNum = 0;

        // get the dbinfo record
        info = new ListSupportDTO().getJCMSDbInfo();
        if (info.size() > 0) {
            dbInfoEntity = info.get(0);
        }

        DbsetupEntity setupvar = new DbsetupEntity();
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "MTS_AUTO_LITTER_NUMS");

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
        }

        DbsetupEntity setupvar1 = new DbsetupEntity();
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "MTS_NUM_AUTO_LITTER_NUMS");

        if (entity != null) {
            setupvar1 = (DbsetupEntity) entity;
        }

        //if MTS_AUTO_LITTER_NUMS is ON, then get the value of MTS_NUM_AUTO_LITTER_NUMS
        // and update maxAutoLitterID in dbinfo and increment litterNum by 1.
        if ((setupvar != null && setupvar.getMTSValue() != null
                && !setupvar.getMTSValue().equals("")
                && setupvar.getMTSValue().equalsIgnoreCase("on"))) {
            if (setupvar1 != null) {
                litterNum = dbInfoEntity.getMaxAutoLitterNum();

                if (setupvar1.getMTSValue() != null && !setupvar1.getMTSValue().
                        equals("")) {
                    // set the maxLitterNum
                    maxLitterNum = litterNum + Integer.parseInt(setupvar1.getMTSValue());

                    // update dbInfo table with new maxAutoLitter ID
                    dbInfoEntity.setMaxAutoLitterNum(maxLitterNum);
                    dbInfoEntity.setIsDirty();

                }
                // incremnt litternum by 1
                nextLitterNum = litterNum + 1;
                this.matingEntity.setSuggestedFirstLitterNum(nextLitterNum);

                System.out.println("maxLitterNum " + maxLitterNum);
                System.out.println("nextLitterNum " + nextLitterNum);
            }
        }

        return dbInfoEntity;
    }
    
    public void saveMouse(MouseEntity mEntity, String diet) throws Exception {

        if (mEntity != null) {
            mEntity.setDiet(diet);

            mEntity.setIsDirty(true);

            new SaveAppService().baseEdit(mEntity);
            this.getLogger().logInfo(this.formatLogMessage("save", "Mouse  "
                    + mEntity.getId() + " has been updated with new diet " + mEntity.getDiet()));
        }
    }

    public void moveMouseToPen(MouseEntity mEntity, ContainerEntity pen) throws Exception {

        if (mEntity != null) {
            mEntity.setPenKey(pen);
            mEntity.setBreedingStatus("B");

            // if there is a new diet, then update it
            if (this.newDiet != null && newDiet.getDietKey() != null && newDiet.getDietKey() > 0) {
                String diet = newDiet.getDiet();
                mEntity.setDiet(diet);
            }
            mEntity.setIsDirty(true);

            // set the version
            int ver = mEntity.getVersion();
            System.out.println("previous version " + ver);
            mEntity.setVersion(ver + 1);

            new SaveAppService().saveMouse(mEntity);

            this.getLogger().logInfo(this.formatLogMessage("save", "Mouse  "
                    + mEntity.getId() + " has been updated with new pen "
                    + pen.getContainerID()));
        }
    }

    public void editMatingAction(){
        try{
            if(!editMatingValidate()){
                //simple date format for parsing mating and retired date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
                
                MatingDTO mDTO = new MatingDTO();
                if(matingEntity.getComment() != null){
                    mDTO.setComment(matingEntity.getComment());
                }
                else{
                    mDTO.setComment("");
                }
                mDTO.setCrossStatus_key(matingEntity.getCrossStatuskey().toString());
                mDTO.setDam1_key(dam1.getMouseKey().toString());
                //dam2 can be null or something
                if(matingEntity.getDam2Key() != null && matingEntity.getDam2Key().intValue() != 0){
                    mDTO.setDam2_key(dam2.getMouseKey().toString());
                }
                else{
                    mDTO.setDam2_key("");
                }
                mDTO.setGeneration(generationEntity.getGeneration());
                mDTO.setMatingDate(sdf.format(matingEntity.getMatingDate()));
                //retired date can be null or something.
                if(matingEntity.getRetiredDate() == null){
                    mDTO.setRetiredDate("");
                }
                else{
                    mDTO.setRetiredDate(sdf.format(matingEntity.getRetiredDate()));
                }
                mDTO.setMatingID(new Integer(matingEntity.getMatingID()).toString());
                mDTO.setNeedsTyping(matingEntity.getNeedsTyping());
                mDTO.setOwner(ownerEntity.getOwner());
                mDTO.setSire_key(sire.getMouseKey().toString());
                mDTO.setStrain_key(matingEntity.getStrainKey().getStrainKey().toString());
                if(matingEntity.getSuggestedPenID() != null){
                    mDTO.setSuggestedPenID(matingEntity.getSuggestedPenID().toString());
                }
                else{
                    mDTO.setSuggestedPenID("");
                }
                if(matingEntity.getWeanNote() != null){
                    mDTO.setWeanNote(matingEntity.getWeanNote());
                }
                else{
                    mDTO.setWeanNote("");
                }
                mDTO.setWeanTime(matingEntity.getWeanTime());         
                mDTO.setMating_key(matingEntity.getMatingKey().toString());
              
                editMatingDAO.updateMating(mDTO);
                //update mating unit link for dam 1 if it's changed
                if(!mDTO.getDam1_key().equals(originalDam1.getMouseKey().toString())){
                    editMatingDAO.updateMatingUnitLinkMouse(mDTO.getMating_key(), this.originalDam1.getMouseKey().toString(), mDTO.getDam1_key());
                }
                //update mating unit link for sire if it's changed
                if(!mDTO.getSire_key().equals(originalSire.getMouseKey().toString())){
                    editMatingDAO.updateMatingUnitLinkMouse(mDTO.getMating_key(), this.originalSire.getMouseKey().toString(), mDTO.getSire_key());
                }
                //update/insert/delete mating unit link for dam2 if it's changed
                //will be a delete if originaldam2 != new MouseEntity and mDTO.getDam2_key == ""
                if(!originalDam2.equals(new MouseEntity()) && mDTO.getDam2_key().equals("")){
                    //delete
                    editMatingDAO.deleteMatingUnitLinkMouse(mDTO.getMating_key(), originalDam2.getMouseKey().toString());
                }
                //will be a insert if originaldam2 == new MouseEntity and mDTO.getDam2_key != ""
                else if(originalDam2.equals(new MouseEntity()) && !mDTO.getDam2_key().equals("")){
                    //insert
                    editMatingDAO.insertMatingUnitLinkMouse(mDTO.getMating_key(), mDTO.getDam2_key());
                }
                //will be an update if originaldam2 != new MouseEntity and mDTO.getDam2Key != originalDam2.getMouseKey
                else if(!originalDam2.equals(new MouseEntity()) && !mDTO.getDam2_key().equals("")){
                    editMatingDAO.updateMatingUnitLinkMouse(mDTO.getMating_key(), this.originalDam2.getMouseKey().toString(), mDTO.getDam2_key());
                }
                
                this.addToMessageQueue("Mating " + matingEntity.getMatingID() + " successfully edited.",
                        FacesMessage.SEVERITY_INFO);
                this.getLogger().logInfo(this.formatLogMessage("Validation ",
                        "Mating " + matingEntity.getMatingID() + " successfully edited."));
                
                initializeForEdit();
            }
        }
        catch(Exception e){
            System.out.println(e);
            this.addToMessageQueue("Mating could not be edited: " + e,
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ",
                    "Mating could not be edited: " + e));
        }
    }
    
    private boolean editMatingValidate() throws Exception{
        boolean flag = false;
        //dam1 checks
        if(dam1ID.equals("")){
            flag = true;
            this.addToMessageQueue("Dam1 is required, please select a dam1", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 is required, please select a dam1"));
        }
        else if(this.mouseFunctions.findMouse(this.dam1ID) == null){
            flag = true;
            this.addToMessageQueue("Dam1 must be an existing female mouse, please check your dam1.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 must be an existing female mouse, please check your dam1."));
        }
        else if(!this.mouseFunctions.findMouse(this.dam1ID).getSex().equalsIgnoreCase("f")){
            flag = true;
            this.addToMessageQueue("Dam1 must be an existing female mouse, please check your dam1.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 must be an existing female mouse, please check your dam1."));
        }
        
        //sire checks
        if(sireID.equals("")){
            flag = true;
            this.addToMessageQueue("Sire is required, please select a sire", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Sire is required, please select a sire"));
        }
        else if(this.mouseFunctions.findMouse(this.sireID) == null){
            flag = true;
            this.addToMessageQueue("Sire must be an existing male mouse, please check your sire.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 must be an existing male mouse, please check your sire."));
        }
        else if(!this.mouseFunctions.findMouse(this.sireID).getSex().equalsIgnoreCase("m")){
            flag = true;
            this.addToMessageQueue("Sire must be an existing male mouse, please check your sire.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 must be an existing male mouse, please check your sire."));
        }
        
        //dam2 checks
        if(!dam2ID.equals("")){
            if(this.mouseFunctions.findMouse(this.dam2ID) == null){
                flag = true;
                this.addToMessageQueue("The selected Dam2 could not be found", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Validation ", "The selected Dam2 could not be found"));
            }
            else if(!this.mouseFunctions.findMouse(this.dam2ID).getSex().equalsIgnoreCase("f")){
                flag = true;
                this.addToMessageQueue("Sire must be an existing male mouse, please check your sire.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Validation ", "Dam1 must be an existing male mouse, please check your sire."));
            }
        }
        
        if(matingEntity.getStrainKey() == null){
            flag = true;
            this.addToMessageQueue("Litter strain is required, please select a dam1", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Litter strain is required, please select a dam1"));
        }
        if(generationEntity == null){
            flag = true;
            this.addToMessageQueue("Generation is required, please select a generation", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Generation is required, please select a generation"));
        }
        if(matingEntity.getMatingDate() == null){
            flag = true;
            this.addToMessageQueue("Mating date is required, please select a Mating date", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "Mating date is required, please select a Mating date"));
        }

        if(ownerEntity.getOwner().equals("")){
            flag = true;
            this.addToMessageQueue("Owner is required, please select a owner", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Validation ", "owner is required, please select a owner"));
        }
        return flag;
     }
    
    public void saveDietAction() throws Exception {

        if (this.newDiet != null && newDiet.getDietKey() != null
                && newDiet.getDietKey() > 0) {

            String diet = newDiet.getDiet();

            // save dam1 with new diet
            if (this.getDam1ID() != null && !this.getDam1ID().equals("")) {
                this.saveMouse(this.getDam1(), diet);
                System.out.println("saving dam1");
            }

            // save dam2 with new diet
            if (this.getDam2ID() != null && !this.getDam2ID().equals("")) {
                this.saveMouse(this.getDam2(), diet);
                System.out.println("saving dam2");
            }

            // save sire with new diet
            MouseEntity mEntity = this.matingEntity.getSireKey();
            if (mEntity != null) {
                mEntity.setDiet(diet);
                mEntity.setIsDirty(true);

                new SaveAppService().baseEdit(mEntity);
                this.getLogger().logInfo(this.formatLogMessage("save", "Mouse  "
                        + mEntity.getId() + " has been updated"));
            }

            this.addToMessageQueue("Dam and Sire have been saved with new diet",
                    FacesMessage.SEVERITY_INFO);

            this.getLogger().logInfo(this.formatLogMessage("Validation ",
                    "Dam and Sire have been saved with new diet"));
        }
    }
    
    /**
     * if the user manually type in dam 1 ID, it is validated and dam1 
     * information is filled
     * @throws Exception 
     */

    public void setDam2IDValueChangeListener(ValueChangeEvent event) throws Exception {
        
        if(originalDam2 != null && getOriginalDam2().getId() != null){
            showOldDam2 = true;
        }
        System.out.println("Selected Mouse");
        this.dam2ID = event.getNewValue().toString();
        this.matingEntity.setDam2Key(null);
        this.setDam2(new MouseEntity());
        dam2Strain = "";
        dam2Pen = "";
        

        if (this.dam2ID != null && !this.dam2ID.equals("")) {
            // Check that mating id is unique.
            MouseEntity mouse = this.mouseFunctions.findMouse(this.dam2ID);

            if (mouse != null) {
                if (!mouse.getSex().equalsIgnoreCase("f")) {
                    this.addToMessageQueue("Dam2 ID " + this.dam2ID + " is not a Female",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Dam2 ID "
                            + this.dam2ID + " is not a Female", "ValidateDam2IDAction"));
                }                
                else if(!mouseOwned(mouse.getOwner())){
                    this.addToMessageQueue("Dam2 ID " + this.dam2ID + " is not owned by a workgroup you belong to.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Dam2 ID " + this.dam2ID + " is not owned by a workgroup you belong to.", "ValidateDam1IDAction"));
                }
                else {
                    this.setDam2(mouse);
                    this.matingEntity.setDam2Key(mouse.getMouseKey());
                    this.setDam2ID(mouse.getId());
                    this.setDam2Genotype(new MouseUtilityDAO().getMouseGenotypeByMouseKey(dam2.getMouseKey()));
                    dam2Pen = new Integer(dam2.getPenKey().getContainerID()).toString();
                    if(dam2.getStrainKey() != null){
                        dam2Strain = dam2.getStrainKey().getStrainName();
                    }

                    //tell user they have to click save
                    if(editing){
                        this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
                    }
                }
            } else {
                this.addToMessageQueue("Dam2 ID " + this.dam2ID + " is not valid",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Dam2 ID "
                        + this.dam2ID + " is not valid", "ValidateDam2IDAction"));
            }
        }
    }

    /**
     * when dam2 is selected, it sets mating's dam2 info
     * @throws Exception 
     */
    public void selectDam2Action() throws Exception {
        MouseEntity mouse = new MouseEntity();

        mouse = mouseInfo.getSelectedMouse(this.mouseSelectionETB,
                this.mouseDataModel);

        if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
            if(editing && (originalDam2.getMouseKey() != null)){
                showOldDam2 = true;
            }
            this.matingEntity.setDam2Key(mouse.getMouseKey());
            this.setDam2ID(mouse.getId());

            // set the dam1 strain
            if (this.getDam2ID() != null && !this.getDam2ID().equals("")) {
                MouseEntity mEntity = this.mouseFunctions.findMouseByKey(mouse.getMouseKey());
                this.setDam2(mEntity);
                this.setDam2Genotype(new MouseUtilityDAO().getMouseGenotypeByMouseKey(dam2.getMouseKey()));
            }
            
            if(dam2.getPenKey() != null){
                dam2Pen = new Integer(dam2.getPenKey().getContainerID()).toString();
            }
            
            if(dam2.getStrainKey() != null){
                this.dam2Strain = dam2.getStrainKey().getStrainName();
            }
            
            if(editing){
                this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
            }
            System.out.println("Selected Mouse " + this.dam2ID);
        }
    }

        
    /**
     * if the user manually type in dam 1 ID, it is validated and dam1 
     * information is filled
     * @throws Exception 
     */
    public void setSireIDValueChangeListener(ValueChangeEvent event) throws Exception {
        System.out.println("Selected Mouse");
        showOldSire = true;
        this.sireID = event.getNewValue().toString();
        int cnt = 0;
        this.matingEntity.setSireKey(new MouseEntity());
        this.setSire(new MouseEntity());
        this.setSireStrain("");
        this.setSirePen("");

        if (this.sireID != null && !this.sireID.equals("")) {
            // Check that mouse is unique.
            MouseEntity mouse = this.mouseFunctions.findMouse(this.sireID);

            if (mouse != null) {
                if (!mouse.getSex().equalsIgnoreCase("m")) {
                    this.addToMessageQueue("Sire ID " + mouse.getId() + " is not a Male",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Sire ID "
                            + mouse.getId() + " is not a Male", "ValidateSireIDAction"));
                }               
                else if(!mouseOwned(mouse.getOwner())){
                    this.addToMessageQueue("Sire ID " + this.sireID + " is not owned by a workgroup you belong to.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Dam2 ID " + this.sireID + " is not owned by a workgroup you belong to.", "ValidateDam1IDAction"));
                }
                else {
                    this.matingEntity.setSireKey(mouse);
                    this.setSire(mouse);
                    this.setSireGenotype(new MouseUtilityDAO().getMouseGenotypeByMouseKey(sire.getMouseKey()));
                    
                    // check if sire has been already used for a mating, then give 
                    // a warning
                    ITBaseEntityTable table = (new RepositoryService().findMatingBySire(this.sire.getMouseKey()));
                    MatingEntity mating = null;

                    for (ITBaseEntityInterface entity : table.getList()) {
                        mating = (MatingEntity) entity;
                    }

                    if (mating != null) {
                        this.addToMessageQueue("Mating already exists with Sire "
                                + this.sireID, FacesMessage.SEVERITY_WARN);
                        this.getLogger().logWarn(this.formatLogMessage("Mating "
                                + "already exists with Sire " + this.sireID, "Validate"));
                    }

                    // if dam1 exists, then check if mating already exists with this 
                    // dam and sire
                    if (this.dam1 != null && this.dam1.getMouseKey() != null
                            && this.dam1.getMouseKey() > 0) {
                        System.out.println("dam key " + this.dam1.getMouseKey());
                        System.out.println("sire key " + this.sire.getMouseKey());

                        cnt = new RepositoryService().findMatingByDamAndSire(
                                this.dam1.getMouseKey(), this.sire.getMouseKey());
                    }

                    if (cnt > 0) {
                        this.addToMessageQueue("Mating already exists with selected "
                                + "Dam1 " + this.dam1ID + " and Sire " + this.sireID,
                                FacesMessage.SEVERITY_WARN);
                        this.getLogger().logWarn(this.formatLogMessage("Mating "
                                + "already exists with Dam1 "
                                + this.dam1ID + " and Sire " + this.sireID, "Validate"));
                    }
                    this.setSireStrain(this.matingEntity.getSireKey().getStrainKey().getStrainName());
                    this.setSirePen(new Integer(this.matingEntity.getSireKey().getPenKey().getContainerID()).toString());
                    
                    //tell user they have to click save
                    if(editing){
                        this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
                    }
                }
            } else {
                this.addToMessageQueue("Sire ID " + this.sireID + " is not valid",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Sire ID "
                        + this.sireID + " is not valid", "ValidateSireIDAction"));
            }
        } else {
            this.addToMessageQueue("Sire ID is missing. Please select a Sire ID.",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Sire ID is missing. Please select a Sire ID."));
        }
        this.setApprovedStrainsAction();
    }

    public void selectSireAction() throws Exception {
        MouseEntity mouse = new MouseEntity();
        int cnt = 0;

        mouse = mouseInfo.getSelectedMouse(this.mouseSelectionETB,
                this.mouseDataModel);
        if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
            if(editing){
                showOldSire = true;
            }
            MouseEntity mEntity = this.mouseFunctions.findMouseByKey(mouse.getMouseKey());
                    
            if (mEntity != null) {
                this.matingEntity.setSireKey(mEntity);
                this.setSire(mEntity);
                this.setSireID(mEntity.getId());
                this.setSireGenotype(new MouseUtilityDAO().getMouseGenotypeByMouseKey(sire.getMouseKey()));
                
                // check if sire has been already used for a mating, then give 
                // a warning
                ITBaseEntityTable table = (new RepositoryService().findMatingBySire(this.sire.getMouseKey()));
                MatingEntity mating = null;

                for (ITBaseEntityInterface entity : table.getList()) {
                    mating = (MatingEntity) entity;
                }
                
                if (mating != null) {
                    this.addToMessageQueue("Mating already exists with Sire " + 
                            this.sireID, FacesMessage.SEVERITY_WARN);
                    this.getLogger().logWarn(this.formatLogMessage("Mating "
                            + "already exists with Sire " + this.sireID, "Validate"));
                }
                
                // if dam1 exists, then check if mating already exists with this 
                // dam and sire
                if (this.dam1 != null && this.dam1.getMouseKey() != null && this.dam1.getMouseKey() > 0) {
                    System.out.println("dam key " + this.dam1.getMouseKey());
                    System.out.println("sire key " + this.sire.getMouseKey());
                    
                    cnt = new RepositoryService().findMatingByDamAndSire(this.
                        dam1.getMouseKey(), this.sire.getMouseKey());
                }
                
                if (cnt > 0) {
                    this.addToMessageQueue("Mating already exists with selected "
                            + "Dam1 " + this.dam1ID + " and Sire " + this.sireID,
                        FacesMessage.SEVERITY_WARN);
                    this.getLogger().logWarn(this.formatLogMessage("Mating "
                            + "already exists with Dam1 " 
                            + this.dam1ID + " and Sire " + this.sireID, "Validate"));
                }
            }
            if(editing){
                this.addToMessageQueue("If you wish to make changes to this mating be sure to click the save button before leaving this page "
                                + "or choosing another mating.", FacesMessage.SEVERITY_WARN);
            }
            this.setSireStrain(this.matingEntity.getSireKey().getStrainKey().getStrainName());
            this.setSirePen(new Integer(this.matingEntity.getSireKey().getPenKey().getContainerID()).toString());
        }
        this.setApprovedStrainsAction();
    }

    public void displayPenError() {
        this.addToMessageQueue("Please enter valid number in Cage# field.",
                FacesMessage.SEVERITY_ERROR);
        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                "Please enter valid number in Cage# field."));
    }

    public void validateCageCard() throws Exception {
        boolean flag = false;
        if(this.entityKey == null || entityKey.equals(0)){
            flag = true;
            this.addToMessageQueue("Please select a cage card from the drop down at the bottom of the form.", FacesMessage.SEVERITY_ERROR);
            this.setGoodCard(false);
        }
        if(dam1 == null || dam1.getPenKey() == null){
            flag = true;
            this.addToMessageQueue("Cage card cues off of dam 1's pen. Please provide a dam 1 to get a cage card.", FacesMessage.SEVERITY_ERROR);
            this.setGoodCard(false);
        }
        if(!flag){
            CageCardBean theCard = new CageCardBean();
            theCard.setEntityKey(entityKey);
            theCard.setCageID(new Integer(this.dam1.getPenKey().getContainerID()).toString());
            theCard.setCardQuantity("single");
            theCard.setCardTypeMask("mating");
            theCard.initializeDTOForDownload();
            theCard.validateCageCardAction();
            this.setGoodCard(theCard.isGoodCard());
            System.out.println("***IS CARD A GOOD CARD? " + theCard.isGoodCard());
            setSuccessful(false);
        }
    }
    
    public String editAction() throws Exception {
        System.out.println("Edit mating action");
        System.out.println("mating key " + this.getKey("paramMatingKey"));
        this.matingKey = this.getKey("paramMatingKey");
        this.initializeForEdit();
        System.out.println("done initializing");
        return "editMating";
    }
    
   
        
    /*3 steps, 1. Create new entry in ContainerHistory table if change to status, room, or actiondate
         *         2. Update entry in Container field.
         *         3. Update _pen_key field in Mouse table for that mouse if the container was changed.
         *          ***will need to check if the entry has changed (is dirty)*/
    public void editPen(){
        boolean passed = validatePenInfo();
        if(passed){
            //escape \, ', and " characters.
            editContainerDTO.setComment(editContainerDTO.getComment().replace("\\", "\\\\"));
            editContainerDTO.setComment(editContainerDTO.getComment().replace("'", "\\'"));
            editContainerDTO.setComment(editContainerDTO.getComment().replace("\"", "\\\""));
            editContainerDTO.setContainerName(editContainerDTO.getContainerName().replace("'", "\'"));
            editContainerDTO.setContainerName(editContainerDTO.getContainerName().replace("\\", "\\\\"));
            editContainerDTO.setContainerName(editContainerDTO.getContainerName().replace("\"", "\\\""));

            boolean dirty = false;
            boolean containerExists = containerDAO.containerExists(editContainerDTO.getContainerID());
            /* if pen status, room, or Action date has changed, create new entry in Container History table 
            * and update Container History field in Container entry */
            try{
                ContainerHistoryDTO originalDTO = containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key());
                //if container doesn't exist you need to create a new container no matter what.
                if(containerExists){
                    if(!originalDTO.getActionDate().equals(editContainerHistoryDTO.getActionDate())){
                        dirty = true;
                    }
                    if(originalDTO.getRoomKey() != editContainerHistoryDTO.getRoomKey()){
                        dirty = true;
                    }
                    if(originalDTO.getContainerStatuskey() != editContainerHistoryDTO.getContainerStatuskey()){
                        dirty = true;
                    }
                }
                else{
                    dirty = true;
                }
                if(dirty){
                    System.out.println("Create a new field in ContainerHistory Table");
                    //insert new Container History into DB
                    if(!containerExists){
                        editContainerHistoryDTO.setContainerKey(1);
                    }
                    String containerHistoryKey = containerDAO.onlyInsertContainerHistory(editContainerHistoryDTO);
                    //update containerDTO to new CH key
                    editContainerDTO.setContainerHistory_key(containerHistoryKey);
                    this.addToMessageQueue("Container History Updated.", FacesMessage.SEVERITY_INFO);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Pen " + editContainerDTO.getContainerID() + " updated."));
                }

                /*Check to see if the container information (pen name, comments) has changed, if so update */

                dirty = false;
                ContainerDTO originalContainerDTO = containerDAO.getContainerByID(editContainerDTO.getContainerID());
                if(!originalContainerDTO.getContainerName().equals(editContainerDTO.getContainerName())){
                    dirty = true;
                }
                if(!originalContainerDTO.getComment().equals(editContainerDTO.getComment())){
                    dirty = true;
                }
                if(!originalContainerDTO.getContainerHistory_key().equals(editContainerDTO.getContainerHistory_key())){
                    dirty = true;
                }
                if(dirty){
                    //need to figure out if creating a new container, or updating an exisiting one...
                    if(containerExists){
                        System.out.println("Update a field in Container Table");
                        //update the container if dirty
                        containerDAO.updateContainer(editContainerDTO);
                        this.addToMessageQueue("Pen " + editContainerDTO.getContainerID() + " updated.", FacesMessage.SEVERITY_INFO);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ", "Pen " + editContainerDTO.getContainerID() + " updated."));
                    }
                    else{
                        String containerKey = containerDAO.insertContainer(editContainerDTO);
                        if(Integer.parseInt(editContainerDTO.getContainerID()) >= Integer.parseInt(containerDAO.getNextAvailablePenId())){
                            containerDAO.updateNextAvailablePenId(editContainerDTO.getContainerID());
                        }
                        containerDAO.updateContainerHistory(editContainerDTO.getContainerHistory_key(), containerKey);
                        editContainerDTO.setContainer_key(containerKey);
                        //update next available pen ID if 
                        if(Integer.parseInt(containerDAO.getNextAvailablePenId()) < Integer.parseInt(editContainerDTO.getContainerID())){
                            containerDAO.updateNextAvailablePenId(editContainerDTO.getContainerID());
                        }                        
                        this.addToMessageQueue("Pen " + editContainerDTO.getContainerID() + " created.", FacesMessage.SEVERITY_INFO);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ", "Pen " + editContainerDTO.getContainerID() + " created."));
                    }
                }
                /*Finally, change the check to see if the container the mouse is supposed to be residing in has changed. */
                dirty = false;
                //keep track of mouse key to do update since you don't know if you're updating dam1, 2, or sire or an original mouse.
                String mouseKey = "";
                if(editDam1Pen){
                    System.out.println("editing dam1...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(dam1.getKey().toString());
                    mouseKey = dam1.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        dam1Pen = editContainerDTO.getContainerID();
                        dirty = true;
                    }
                }
                else if(editDam2Pen){
                    System.out.println("editing dam2...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(dam2.getKey().toString());     
                    mouseKey = dam2.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        dam2Pen = editContainerDTO.getContainerID();
                        dirty = true;
                    }
                }
                else if(editSirePen){
                    System.out.println("editing sire...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(sire.getKey().toString());
                    mouseKey = sire.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        sirePen = editContainerDTO.getContainerID();
                        dirty = true;
                    }
                }
                else if(editOriginalDam1Pen){
                    System.out.println("editing original dam1...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(originalDam1.getKey().toString());
                    mouseKey = originalDam1.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        originalDam1.getPenKey().setContainerID(Integer.parseInt(editContainerDTO.getContainerID()));
                        dirty = true;
                    }
                }
                else if(editOriginalDam2Pen){
                    System.out.println("editing original dam2...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(originalDam2.getKey().toString());
                    mouseKey = originalDam2.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        originalDam2.getPenKey().setContainerID(Integer.parseInt(editContainerDTO.getContainerID()));
                        dirty = true;
                    }
                }
                else if(editOriginalSirePen){
                    System.out.println("editing original sire...");
                    String originalContainerKey = containerDAO.getContainerKeyForMouseByKey(originalSire.getKey().toString());
                    mouseKey = originalSire.getKey().toString();
                    if(!originalContainerKey.equals(editContainerDTO.getContainer_key())){
                        originalSire.getPenKey().setContainerID(Integer.parseInt(editContainerDTO.getContainerID()));
                        dirty = true;
                    }
                }
                if(dirty){
                    System.out.println("Update _pen_key field in Mouse Table");
                    containerDAO.updateMouseContainer(mouseKey, editContainerDTO.getContainer_key());
                    this.addToMessageQueue("Mouse moved to pen: " + editContainerDTO.getContainerID(), FacesMessage.SEVERITY_INFO);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mouse moved to pen: " + editContainerDTO.getContainerID()));
                }
            }
            catch(Exception e){
                this.addToMessageQueue("Error while trying to adjust pen information: " + e, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error while trying to move mouse: " + e));
            }
            next = false;
            System.out.println("dam1 pen: " + dam1Pen + " dam2 pen: " + dam2Pen + " sire pen: " + sirePen);
        }
    }
    
    public void changeEditContainerListener(){
        try{
            if(editContainerDTO.getContainerID().equals("")){
                this.addToMessageQueue("Please select a cage ID.", FacesMessage.SEVERITY_ERROR);
            }
            else{
                if((Integer.parseInt(containerDAO.getNextAvailablePenId())) < Integer.parseInt(editContainerDTO.getContainerID())){
                    this.addToMessageQueue("You are about to create a pen that is greater than the current next available pen ID,"
                            + " are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                }
                //warn user if mouse is of different gender of mice currenlty in the pen
                if(editSirePen){
                    if(containerDAO.multipleGendersInPen(editContainerDTO.getContainerID(), "M")){
                        this.addToMessageQueue("You are about to add this male mouse to a pen that contains one or more female mice, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                    }
                }
                else{
                    if(containerDAO.multipleGendersInPen(editContainerDTO.getContainerID(), "F")){
                        this.addToMessageQueue("You are about to add this female mouse to a pen that contains one or more male mice, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                    }
                }
                if(containerDAO.containerExists(editContainerDTO.getContainerID())){
                    editContainerDTO = containerDAO.getContainerByID(editContainerDTO.getContainerID());
                    editContainerID = editContainerDTO.getContainerID();
                    editContainerHistoryDTO = containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key());
                    editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
                }
                else{
                    editContainerID = editContainerDTO.getContainerID();
                    editContainerDTO = new ContainerDTO();
                    editContainerDTO.setContainerID(editContainerID);
                    editContainerHistoryDTO = new ContainerHistoryDTO();
                    editContainerHistoryDTO.setContainerStatuskey(1);
                    editContainerHistoryDTO.setRoomKey(1);         
                    editContainerHistoryDTO.setActionDate(new Date());
                }
            }
        }
        catch(NumberFormatException nfe){
            this.addToMessageQueue("Cage ID must be an integer, please select an appropriate Cage ID", FacesMessage.SEVERITY_ERROR);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e,
                FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                "Error: " + e));
        }
    }
    
    public boolean validatePenInfo(){
        boolean passed = true;
        
        if(editContainerDTO.getContainerName().length() > 16){
            passed = false;
            this.addToMessageQueue("Cage Name must be fewer than 16 characters.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Cage Name must be fewer than 16 characters."));
        }
        
        if(editContainerDTO.getComment().length() > 64){
            passed = false;
            this.addToMessageQueue("Cage Name must be fewer than 16 characters.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Cage Name must be fewer than 16 characters."));
        }
        
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
            sdf.format(editContainerHistoryDTO.getActionDate());
        }
        catch(Exception e){
            passed = false;
            this.addToMessageQueue("The date is not properly formatted, please check the date or select a date by clicking on the calendar icon.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "The date is not properly formatted, please check the date or select a date by clicking on the calendar icon."));
        }
            
        try{
            Integer.parseInt(editContainerDTO.getContainerID());
        }
        catch(NumberFormatException e){
            passed = false;
            this.addToMessageQueue("Cage ID must be an integer value.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Cage ID must be an integer value."));
        }
        return passed;
    }
    
    public void populateEditCage(){
        containerDAO.getContainerByID(this.editContainerDTO.getContainerID());
    }
    
    public void cageIDValueChangeListener(){
        if(matingEntity.getSuggestedPenID() > (Integer.parseInt(containerDAO.getNextAvailablePenId())+1)){
            this.addToMessageQueue("The selected cage ID is greater than the next available cage ID. "
                    + "Adding a mating to this cage will cause a gap in cage IDs. Are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "The selected cage ID is greater than the next available cage ID. "
                    + "Adding a mating to this cage will cause a gap in cage IDs. Are you sure you want to continue?"));
        }        
        if(containerDAO.boolGetMiceInPen(matingEntity.getSuggestedPenID().toString())){
            this.addToMessageQueue("There are already mice in this pen, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "There are already mice in this pen, are you sure you want to continue?"));
        }
    }
    
    public void testing(){
        System.out.println("changed");
    }
    
    public void editDam1Container(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = true;
            editDam2Pen = false;
            editSirePen = false;
            dam1 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam1Key()));
            editContainerDTO = containerDAO.getContainerByID(new Integer(dam1.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            setEditContainerHistoryDTO(containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key()));
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
    }
    
    public void editDam2Container(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = false;
            editDam2Pen = true;
            editSirePen = false;
            dam2 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam2Key()));
            editContainerDTO = containerDAO.getContainerByID(new Integer(dam2.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            editContainerHistoryDTO = containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key());
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e,
                FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                "Error: " + e));
        }
    }
    
    public void editSireContainer(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = false;
            editDam2Pen = false;
            editSirePen = true;
            sire = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(sire.getMouseKey()));
            editContainerDTO = containerDAO.getContainerByID(new Integer(sire.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            editContainerHistoryDTO = containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key());
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e,
                FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                "Error: " + e));
        }
    }
    
    public void editOriginalDam1Container(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = false;
            editDam2Pen = false;
            editSirePen = false;
            editOriginalDam1Pen = true;
            editOriginalDam2Pen = false;
            editOriginalSirePen = false;
            editContainerDTO = containerDAO.getContainerByID(new Integer(originalDam1.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            setEditContainerHistoryDTO(containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key()));
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
    }
    
    public void editOriginalDam2Container(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = false;
            editDam2Pen = false;
            editSirePen = false;
            editOriginalDam1Pen = false;
            editOriginalDam2Pen = true;
            editOriginalSirePen = false;
            editContainerDTO = containerDAO.getContainerByID(new Integer(originalDam2.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            setEditContainerHistoryDTO(containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key()));
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
    }
    
    public void editOriginalSireContainer(){
        try{
            pens = containerDAO.getContainers();
            penNames = containerDAO.getContainerNames();
            
            next = false;
            editDam1Pen = false;
            editDam2Pen = false;
            editSirePen = false;
            editOriginalDam1Pen = false;
            editOriginalDam2Pen = false;
            editOriginalSirePen = true;
            editContainerDTO = containerDAO.getContainerByID(new Integer(originalSire.getPenKey().getContainerID()).toString());
            editContainerID = editContainerDTO.getContainerID();
            setEditContainerHistoryDTO(containerDAO.getContainerHistoryAndRoom(editContainerDTO.getContainerHistory_key()));
            editContainerStatusDTO = containerDAO.getContainerStatusByStatusKey(new Integer(editContainerHistoryDTO.getContainerStatuskey()).toString());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
    }
    
    
    public void nextMatingAction(){
        matingEntity.setMatingID(matingEntity.getMatingID() + 1);
        try{
            int temp = Integer.parseInt(editMatingDAO.getMatingKeyByMatingID(new Integer(matingEntity.getMatingID()).toString()));
            if(matingOwnerCheck(new Integer(temp).toString())){
                matingKey = temp;
                initializeForEdit();
                matingIdDisabled = true;
            }
            else{
                this.addToMessageQueue("You are not the owner of that mating.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "You are not the owner of that mating."));
                matingEntity.setMatingID(matingEntity.getMatingID() - 1);
            }
        }
        catch(NumberFormatException e){
            this.addToMessageQueue("Mating with matingID " + matingEntity.getMatingID() + " could not be found.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating of number " + matingEntity.getMatingID() + " could not be found."));
            matingEntity.setMatingID(matingEntity.getMatingID() - 1);
        }
    }
    
    public void previousMatingAction(){
        matingEntity.setMatingID(matingEntity.getMatingID() - 1);
        try{
            int temp = Integer.parseInt(editMatingDAO.getMatingKeyByMatingID(new Integer(matingEntity.getMatingID()).toString()));
            if(matingOwnerCheck(new Integer(temp).toString())){
                matingKey = temp;
                initializeForEdit();
                matingIdDisabled = true;
            }
            else{
                this.addToMessageQueue("You are not the owner of that mating.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "You are not the owner of that mating."));
                matingEntity.setMatingID(matingEntity.getMatingID() + 1);
            }
        }
        catch(NumberFormatException e){
            this.addToMessageQueue("Mating with matingID " + matingEntity.getMatingID() + " could not be found.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating of number " + matingEntity.getMatingID() + " could not be found."));
            matingEntity.setMatingID(matingEntity.getMatingID() + 1);
        }
    }
    
    public void activateMatingIDAutoComplete(){
        matingIdDisabled = false;
    }
    
    public void changeMatingListener(ValueChangeEvent vce){
        try{
            int temp = Integer.parseInt(editMatingDAO.getMatingKeyByMatingID(vce.getNewValue().toString()));
            if(matingOwnerCheck(new Integer(temp).toString())){
                matingKey = temp;
                initializeForEdit();
                matingIdDisabled = true;
            }
            else{
                this.addToMessageQueue("You are not the owner of that mating.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "You are not the owner of that mating."));
                matingEntity.setMatingID((Integer) vce.getOldValue());
            }
        }
        catch(NumberFormatException e){
            this.addToMessageQueue("Mating with matingID " + vce.getNewValue().toString() + " could not be found.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating of number " + vce.getNewValue().toString() + " could not be found."));
            matingEntity.setMatingID((Integer) vce.getOldValue());
        }
    }
    
    private boolean matingOwnerCheck(String key){
        try{
            ArrayList<OwnerEntity> ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"); 
            String matingOwner = editMatingDAO.getMatingOwnerByKey(new Integer(key).toString());
            for(OwnerEntity o : ownerLst){
                if(o.getOwner().equalsIgnoreCase(matingOwner)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
            return false;
        }
    }
    
    public void changeToNextAvailablePenValues(){
        System.out.println("Get next available pen number");
        
        editContainerDTO = new ContainerDTO();
        editContainerDTO.setContainerID(containerDAO.getNextAvailablePenId());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        Date now = new Date();
        String strDate = sdf.format(now);
        
        editContainerHistoryDTO = new ContainerHistoryDTO();
        
        editContainerHistoryDTO.setRoomKey(1);
        editContainerHistoryDTO.setContainerStatuskey(2);
        try{
            editContainerHistoryDTO.setActionDate(sdf.parse(strDate));
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void executeDamSearch(){
        isMouseResultCountDisplayed = true;
        for (LifeStatusEntity entity : listSupportDTO.getLifeStatus()) {
            if (entity.getLifeStatus().equals("A")) {
                this.mouseSearch.setLifeStatus(entity);
                break;
            }
        }
        for(CvSexEntity sex : listSupportDTO.getCvSex()){
            if(sex.getAbbreviation().equalsIgnoreCase("F")){
                mouseSearch.setSex(sex);
            }
        }
        if(mouseSearch.getOwners().isEmpty()){
            mouseSearch.setOwners((ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst"));
        }
        System.out.println("starting mouse search...");
        mouseDataModel = new ListDataModel();
        try{
            ITBaseEntityTable mouseTable = new MouseSearchDAO().getSearchResults(mouseSearch, genotypeSearchDTO);
            List<MouseEntity> mice = new ArrayList<MouseEntity>();
            
            for (ITBaseEntityInterface entity: mouseTable.getList()) {
                mice.add((MouseEntity) entity);
            }  
            mouseDataModel.setWrappedData(mice);
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void executeSireSearch(){
        isMouseResultCountDisplayed = true;
        for (LifeStatusEntity entity : listSupportDTO.getLifeStatus()) {
            if (entity.getLifeStatus().equals("A")) {
                this.mouseSearch.setLifeStatus(entity);
                break;
            }
        }
        for(CvSexEntity sex : listSupportDTO.getCvSex()){
            if(sex.getAbbreviation().equalsIgnoreCase("M")){
                mouseSearch.setSex(sex);
            }
        }
        if(mouseSearch.getOwners().isEmpty()){
            mouseSearch.setOwners((ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst"));
        }
        System.out.println("starting sire search...");
        mouseDataModel = new ListDataModel();
        try{
            ITBaseEntityTable mouseTable = new MouseSearchDAO().getSearchResults(mouseSearch, genotypeSearchDTO);
            List<MouseEntity> mice = new ArrayList<MouseEntity>();
            
            for (ITBaseEntityInterface entity: mouseTable.getList()) {
                mice.add((MouseEntity) entity);
            }  
            mouseDataModel.setWrappedData(mice);
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
     public void geneFilterChanged(ValueChangeEvent event){
        List<SelectItem> items = new ArrayList<SelectItem>();       
        items.add(new SelectItem("", ""));        
        this.getAllele1FilterItems().clear();
        this.getAllele2FilterItems().clear();
        GeneEntity geneEntity = (GeneEntity) event.getNewValue();
        
        try {
            if (geneEntity == null) {
                this.setAllele1FilterItems(items);
                this.setAllele2FilterItems(items);
            } 
            else {
                Integer geneKey = geneEntity.getGeneKey();
                if (geneKey != null && geneKey > 0) {
                    System.out.println("gene Key " + geneKey);

                    for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(geneKey)) {
                        items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                        System.out.println(entity.getAllele());
                    }
                }
            }
        } catch (Exception e) {
            // do nothing
        }
        this.setAllele1FilterItems(items);
        this.setAllele2FilterItems(items);
    }
    
    public void editDam1Diet(){
        editDam1Diet = true;
    }
    
    public void editDam2Diet(){
        editDam2Diet = true;
    }
    
    public void editSireDiet(){
        editSireDiet = true;
    }
    
    public void editOriginalDam1Diet(){
        editOriginalDam1Diet = true;
    }
    
    public void editOriginalDam2Diet(){
        editOriginalDam2Diet = true;
    }
    
    public void editOriginalSireDiet(){
        editOriginalSireDiet = true;
    }
    
    public void saveDam1Diet(){
        try{
            editDam1Diet = false;
            if(dam1.getDiet().equals("")){
               editMatingDAO.setMouseDiet(dam1.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(dam1.getKey().toString(), "'" + dam1.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void saveDam2Diet(){
        try{
            editDam2Diet = false;
            if(dam2.getDiet().equals("")){
               editMatingDAO.setMouseDiet(dam2.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(dam2.getKey().toString(), "'" + dam2.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void saveSireDiet(){
        try{
            editSireDiet = false;
            if(sire.getDiet().equals("")){
               editMatingDAO.setMouseDiet(sire.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(sire.getKey().toString(), "'" + sire.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void saveOriginalDam1Diet(){
        try{
            editOriginalDam1Diet = false;
            if(originalDam1.getDiet().equals("")){
               editMatingDAO.setMouseDiet(originalDam1.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(originalDam1.getKey().toString(), "'" + originalDam1.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void saveOriginalDam2Diet(){
        try{
            editOriginalDam2Diet = false;
            if(originalDam2.getDiet().equals("")){
               editMatingDAO.setMouseDiet(originalDam2.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(originalDam2.getKey().toString(), "'" + originalDam2.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
    
    public void saveOriginalSireDiet(){
        try{
            editOriginalSireDiet = false;
            if(originalSire.getDiet().equals("")){
               editMatingDAO.setMouseDiet(originalSire.getKey().toString(), "null"); 
            }
            else{
                editMatingDAO.setMouseDiet(originalSire.getKey().toString(), "'" + originalSire.getDiet() + "'");
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
    }
        
    public boolean singleQuoteParser(String str){
        for(char c : str.toCharArray()){
            if(c == '\''){
                return true;
            }
        }
        return false;
    }
    
    private void initializePageBuilder(){
        //First we need to know the current user's key
        String userKey = "";
        UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
        userKey = currentUser.getUserkey().toString();
        
        //All fields on this form that can be hidden must have the user's preferences looked up
        //and saved in PageBuilder.
        cvPreferencesDAO DAO = new cvPreferencesDAO();
        
        //Have to convert hideField into boolean

        this.getPageBuilder().setHideWeanNote(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "weanNote")));
        this.getPageBuilder().setHideMatingComment(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "comment")));
        this.getPageBuilder().setHideWeanTime(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "weanTime")));
        this.getPageBuilder().setHideNeedsTyping(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "needsTyping")));
        this.getPageBuilder().setHideMatingDiet(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "diet")));
        this.getPageBuilder().setHideMatingDam2(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "_dam2_key")));
        
        //All fields that can have a default must have the default value saved too.
        this.pageBuilder.setDefaultMatingDiet((DAO.GetDefaultValue(userKey,"matingEdit", "diet")));
        
        //These fields could be still null (they are intially created that way). If so, use a specific default
        if (DAO.GetDefaultValue(userKey,"matingEdit", "weanTime") != null) {
            this.pageBuilder.setDefaultWeanTime((DAO.GetDefaultValue(userKey,"matingEdit", "weanTime")));
        } else {
            this.pageBuilder.setDefaultWeanTime("true");
        }
        if (DAO.GetDefaultValue(userKey,"matingEdit", "needsTyping") != null) {
            this.pageBuilder.setDefaultNeedsTyping((DAO.GetDefaultValue(userKey,"matingEdit", "needsTyping")));
        } else {
            this.pageBuilder.setDefaultNeedsTyping("false");
        }

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
     * @return the matingEntity
     */
    public MatingEntity getMatingEntity() {
        return matingEntity;
    }

    /**
     * @param matingEntity the matingEntity to set
     */
    public void setMatingEntity(MatingEntity matingEntity) {
        this.matingEntity = matingEntity;
    }

    /**
     * @return the strainStatus
     */
    public String getStrainStatus() {
        return strainStatus;
    }

    /**
     * @param strainStatus the strainStatus to set
     */
    public void setStrainStatus(String strainStatus) {
        this.strainStatus = strainStatus;
    }

    /**
     * @return the matingKey
     */
    public int getMatingKey() {
        return matingKey;
    }

    /**
     * @param matingKey the matingKey to set
     */
    public void setMatingKey(int matingKey) {
        this.matingKey = matingKey;
    }

    /**
     * @return the businessLogic
     */
    public Mating getBusinessLogic() {
        return businessLogic;
    }

    /**
     * @param businessLogic the businessLogic to set
     */
    public void setBusinessLogic(Mating businessLogic) {
        this.businessLogic = businessLogic;
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
     * @return the isPenResultCountDisplayed
     */
    public boolean isIsPenResultCountDisplayed() {
        return isPenResultCountDisplayed;
    }

    /**
     * @param isPenResultCountDisplayed the isPenResultCountDisplayed to set
     */
    public void setIsPenResultCountDisplayed(boolean isPenResultCountDisplayed) {
        this.isPenResultCountDisplayed = isPenResultCountDisplayed;
    }

    /**
     * @return the penInfoDataModel
     */
    public ListDataModel getPenInfoDataModel() {
        return penInfoDataModel;
    }

    /**
     * @param penInfoDataModel the penInfoDataModel to set
     */
    public void setPenInfoDataModel(ListDataModel penInfoDataModel) {
        this.penInfoDataModel = penInfoDataModel;
    }

    /**
     * @return the penSelectionETB
     */
    public ExtendedTableBean getPenSelectionETB() {
        return penSelectionETB;
    }

    /**
     * @param penSelectionETB the penSelectionETB to set
     */
    public void setPenSelectionETB(ExtendedTableBean penSelectionETB) {
        this.penSelectionETB = penSelectionETB;
    }

    /**
     * @return the penSearch
     */
    public PenSearchDTO getPenSearch() {
        return penSearch;
    }

    /**
     * @param penSearch the penSearch to set
     */
    public void setPenSearch(PenSearchDTO penSearch) {
        this.penSearch = penSearch;
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
     * @return the penInfo
     */
    public PenListCommon getPenInfo() {
        return penInfo;
    }

    /**
     * @param penInfo the penInfo to set
     */
    public void setPenInfo(PenListCommon penInfo) {
        this.penInfo = penInfo;
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
     * @return the dam1ID
     */
    public String getDam1ID() {
        return dam1ID;
    }

    /**
     * @param dam1ID the dam1ID to set
     */
    public void setDam1ID(String dam1ID) {
        this.dam1ID = dam1ID;
    }

    /**
     * @return the dam2ID
     */
    public String getDam2ID() {
        return dam2ID;
    }

    /**
     * @param dam2ID the dam2ID to set
     */
    public void setDam2ID(String dam2ID) {
        this.dam2ID = dam2ID;
    }

    /**
     * @return the sireID
     */
    public String getSireID() {
        return sireID;
    }

    /**
     * @param sireID the sireID to set
     */
    public void setSireID(String sireID) {
        this.sireID = sireID;
    }

    /**
     * @return the searchPanel
     */
    public int getSearchPanel() {
        return searchPanel;
    }

    /**
     * @param searchPanel the searchPanel to set
     */
    public void setSearchPanel(int searchPanel) {
        this.searchPanel = searchPanel;
    }

    /**
     * @return the strainKey
     */
    public int getStrainKey() {
        return strainKey;
    }

    /**
     * @param strainKey the strainKey to set
     */
    public void setStrainKey(int strainKey) {
        this.strainKey = strainKey;
    }

    /**
     * @return the newDiet
     */
    public CvDietEntity getNewDiet() {
        return newDiet;
    }

    /**
     * @param newDiet the newDiet to set
     */
    public void setNewDiet(CvDietEntity newDiet) {
        this.newDiet = newDiet;
    }

    /**
     * @return the currentDiet
     */
    public String getCurrentDiet() {
        return currentDiet;
    }

    /**
     * @param currentDiet the currentDiet to set
     */
    public void setCurrentDiet(String currentDiet) {
        this.currentDiet = currentDiet;
    }

    /**
     * @return the mouseKey
     */
    public int getMouseKey() {
        return mouseKey;
    }

    /**
     * @param mouseKey the mouseKey to set
     */
    public void setMouseKey(int mouseKey) {
        this.mouseKey = mouseKey;
    }

//    /**
//     * @return the tempApprovedStrains
//     */
//    public boolean isTempApprovedStrains() {
//        return tempApprovedStrains;
//    }
//
//    /**
//     * @param tempApprovedStrains the tempApprovedStrains to set
//     */
//    public void setTempApprovedStrains(boolean tempApprovedStrains) {
//        this.tempApprovedStrains = tempApprovedStrains;
//    }

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
     * @return the dam1Strain
     */
    public String getDam1Strain() {
        return dam1Strain;
    }

    /**
     * @param dam1Strain the dam1Strain to set
     */
    public void setDam1Strain(String dam1Strain) {
        this.dam1Strain = dam1Strain;
    }

    /**
     * @return the dam2Strain
     */
    public String getDam2Strain() {
        return dam2Strain;
    }

    /**
     * @param dam2Strain the dam2Strain to set
     */
    public void setDam2Strain(String dam2Strain) {
        this.dam2Strain = dam2Strain;
    }

    /**
     * @return the sireStrain
     */
    public String getSireStrain() {
        return sireStrain;
    }

    /**
     * @param sireStrain the sireStrain to set
     */
    public void setSireStrain(String sireStrain) {
        this.sireStrain = sireStrain;
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
     * @return the dam2
     */
    public MouseEntity getDam2() {
        return dam2;
    }

    /**
     * @param dam2 the dam2 to set
     */
    public void setDam2(MouseEntity dam2) {
        this.dam2 = dam2;
    }

    /**
     * @return the goodCard
     */
    public boolean isGoodCard() {
        return goodCard;
    }

    /**
     * @param goodCard the goodCard to set
     */
    public void setGoodCard(boolean goodCard) {
        this.goodCard = goodCard;
    }

    /**
     * @return the dam1Pen
     */
    public String getDam1Pen() {
        return dam1Pen;
    }

    /**
     * @param dam1Pen the dam1Pen to set
     */
    public void setDam1Pen(String dam1Pen) {
        this.dam1Pen = dam1Pen;
    }

    /**
     * @return the sirePen
     */
    public String getSirePen() {
        return sirePen;
    }

    /**
     * @param sirePen the sirePen to set
     */
    public void setSirePen(String sirePen) {
        this.sirePen = sirePen;
    }

    /**
     * @return the sire
     */
    public MouseEntity getSire() {
        return sire;
    }

    /**
     * @param sire the sire to set
     */
    public void setSire(MouseEntity sire) {
        this.sire = sire;
    }

    /**
     * @return the successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * @return the entityKey
     */
    public Integer getEntityKey() {
        return entityKey;
    }

    /**
     * @param entityKey the entityKey to set
     */
    public void setEntityKey(Integer entityKey) {
        this.entityKey = entityKey;
    }

    /**
     * @return the editing
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * @param editing the editing to set
     */
    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    /**
     * @return the dam2Pen
     */
    public String getDam2Pen() {
        return dam2Pen;
    }

    /**
     * @param dam2Pen the dam2Pen to set
     */
    public void setDam2Pen(String dam2Pen) {
        this.dam2Pen = dam2Pen;
    }

    /**
     * @return the pens
     */
    public ArrayList<String> getPens() {
        return pens;
    }

    /**
     * @return the penNames
     */
    public ArrayList<String> getPenNames() {
        return penNames;
    }

    /**
     * @return the editContainerDTO
     */
    public ContainerDTO getEditContainerDTO() {
        return editContainerDTO;
    }

    /**
     * @param editContainerDTO the editContainerDTO to set
     */
    public void setEditContainerDTO(ContainerDTO editContainerDTO) {
        this.editContainerDTO = editContainerDTO;
    }

    /**
     * @return the editContainerHistoryDTO
     */
    public ContainerHistoryDTO getEditContainerHistoryDTO() {
        return editContainerHistoryDTO;
    }

    /**
     * @param editContainerHistoryDTO the editContainerHistoryDTO to set
     */
    public void setEditContainerHistoryDTO(ContainerHistoryDTO editContainerHistoryDTO) {
        this.editContainerHistoryDTO = editContainerHistoryDTO;
    }

    /**
     * @return the editContainerStatusDTO
     */
    public cvContainerStatusDTO getEditContainerStatusDTO() {
        return editContainerStatusDTO;
    }

    /**
     * @param editContainerStatusDTO the editContainerStatusDTO to set
     */
    public void setEditContainerStatusDTO(cvContainerStatusDTO editContainerStatusDTO) {
        this.editContainerStatusDTO = editContainerStatusDTO;
    }

    /**
     * @return the editContainerID
     */
    public String getEditContainerID() {
        return editContainerID;
    }

    /**
     * @param editContainerID the editContainerID to set
     */
    public void setEditContainerID(String editContainerID) {
        this.editContainerID = editContainerID;
    }

    /**
     * @return the matings
     */
    public ArrayList<String> getMatings() {
        return matings;
    }

    /**
     * @param matings the matings to set
     */
    public void setMatings(ArrayList<String> matings) {
        this.matings = matings;
    }

    /**
     * @return the next
     */
    public boolean isNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(boolean next) {
        this.next = next;
        System.out.println("next? " + next);
    }

    /**
     * @return the editDam1Diet
     */
    public boolean isEditDam1Diet() {
        return editDam1Diet;
    }

    /**
     * @param editDam1Diet the editDam1Diet to set
     */
    public void setEditDam1Diet(boolean editDam1Diet) {
        this.editDam1Diet = editDam1Diet;
    }

    /**
     * @return the editDam2Diet
     */
    public boolean isEditDam2Diet() {
        return editDam2Diet;
    }

    /**
     * @param editDam2Diet the editDam2Diet to set
     */
    public void setEditDam2Diet(boolean editDam2Diet) {
        this.editDam2Diet = editDam2Diet;
    }

    /**
     * @return the editSireDiet
     */
    public boolean isEditSireDiet() {
        return editSireDiet;
    }

    /**
     * @param editSireDiet the editSireDiet to set
     */
    public void setEditSireDiet(boolean editSireDiet) {
        this.editSireDiet = editSireDiet;
    }

    /**
     * @return the showOldDam1
     */
    public boolean isShowOldDam1() {
        return showOldDam1;
    }

    /**
     * @param showOldDam1 the showOldDam1 to set
     */
    public void setShowOldDam1(boolean showOldDam1) {
        this.showOldDam1 = showOldDam1;
    }

    /**
     * @return the showOldDam2
     */
    public boolean isShowOldDam2() {
        return showOldDam2;
    }

    /**
     * @param showOldDam2 the showOldDam2 to set
     */
    public void setShowOldDam2(boolean showOldDam2) {
        this.showOldDam2 = showOldDam2;
    }

    /**
     * @return the showOldSire
     */
    public boolean isShowOldSire() {
        return showOldSire;
    }

    /**
     * @param showOldSire the showOldSire to set
     */
    public void setShowOldSire(boolean showOldSire) {
        this.showOldSire = showOldSire;
    }

    /**
     * @return the originalDam1
     */
    public MouseEntity getOriginalDam1() {
        return originalDam1;
    }

    /**
     * @param originalDam1 the originalDam1 to set
     */
    public void setOriginalDam1(MouseEntity originalDam1) {
        this.originalDam1 = originalDam1;
    }

    /**
     * @return the originalDam2
     */
    public MouseEntity getOriginalDam2() {
        return originalDam2;
    }

    /**
     * @param originalDam2 the originalDam2 to set
     */
    public void setOriginalDam2(MouseEntity originalDam2) {
        this.originalDam2 = originalDam2;
    }

    /**
     * @return the originalSire
     */
    public MouseEntity getOriginalSire() {
        return originalSire;
    }

    /**
     * @param originalSire the originalSire to set
     */
    public void setOriginalSire(MouseEntity originalSire) {
        this.originalSire = originalSire;
    }

    /**
     * @return the editOriginalDam1Diet
     */
    public boolean isEditOriginalDam1Diet() {
        return editOriginalDam1Diet;
    }

    /**
     * @param editOriginalDam1Diet the editOriginalDam1Diet to set
     */
    public void setEditOriginalDam1Diet(boolean editOriginalDam1Diet) {
        this.editOriginalDam1Diet = editOriginalDam1Diet;
    }

    /**
     * @return the editOriginalDam2Diet
     */
    public boolean isEditOriginalDam2Diet() {
        return editOriginalDam2Diet;
    }

    /**
     * @param editOriginalDam2Diet the editOriginalDam2Diet to set
     */
    public void setEditOriginalDam2Diet(boolean editOriginalDam2Diet) {
        this.editOriginalDam2Diet = editOriginalDam2Diet;
    }

    /**
     * @return the editOriginalSireDiet
     */
    public boolean isEditOriginalSireDiet() {
        return editOriginalSireDiet;
    }

    /**
     * @param editOriginalSireDiet the editOriginalSireDiet to set
     */
    public void setEditOriginalSireDiet(boolean editOriginalSireDiet) {
        this.editOriginalSireDiet = editOriginalSireDiet;
    }

    /**
     * @return the editOriginalDam1Pen
     */
    public boolean isEditOriginalDam1Pen() {
        return editOriginalDam1Pen;
    }

    /**
     * @param editOriginalDam1Pen the editOriginalDam1Pen to set
     */
    public void setEditOriginalDam1Pen(boolean editOriginalDam1Pen) {
        this.editOriginalDam1Pen = editOriginalDam1Pen;
    }

    /**
     * @return the editOriginalDam2Pen
     */
    public boolean isEditOriginalDam2Pen() {
        return editOriginalDam2Pen;
    }

    /**
     * @param editOriginalDam2Pen the editOriginalDam2Pen to set
     */
    public void setEditOriginalDam2Pen(boolean editOriginalDam2Pen) {
        this.editOriginalDam2Pen = editOriginalDam2Pen;
    }

    /**
     * @return the editOriginalSirePen
     */
    public boolean isEditOriginalSirePen() {
        return editOriginalSirePen;
    }

    /**
     * @param editOriginalSirePen the editOriginalSirePen to set
     */
    public void setEditOriginalSirePen(boolean editOriginalSirePen) {
        this.editOriginalSirePen = editOriginalSirePen;
    }

    /**
     * @return the matingIdDisabled
     */
    public boolean isMatingIdDisabled() {
        return matingIdDisabled;
    }

    /**
     * @param matingIdDisabled the matingIdDisabled to set
     */
    public void setMatingIdDisabled(boolean matingIdDisabled) {
        this.matingIdDisabled = matingIdDisabled;
    }

    /**
     * @return the genotypeSearchDTO
     */
    public GenotypeSearchDTO getGenotypeSearchDTO() {
        return genotypeSearchDTO;
    }

    /**
     * @param genotypeSearchDTO the genotypeSearchDTO to set
     */
    public void setGenotypeSearchDTO(GenotypeSearchDTO genotypeSearchDTO) {
        this.genotypeSearchDTO = genotypeSearchDTO;
    }

    /**
     * @return the allele1FilterItems
     */
    public List<SelectItem> getAllele1FilterItems() {
        return allele1FilterItems;
    }

    /**
     * @param allele1FilterItems the allele1FilterItems to set
     */
    public void setAllele1FilterItems(List<SelectItem> allele1FilterItems) {
        this.allele1FilterItems = allele1FilterItems;
    }

    /**
     * @return the allele2FilterItems
     */
    public List<SelectItem> getAllele2FilterItems() {
        return allele2FilterItems;
    }

    /**
     * @param allele2FilterItems the allele2FilterItems to set
     */
    public void setAllele2FilterItems(List<SelectItem> allele2FilterItems) {
        this.allele2FilterItems = allele2FilterItems;
    }

    /**
     * @return the dam1Genotype
     */
    public String getDam1Genotype() {
        return dam1Genotype;
    }

    /**
     * @param dam1Genotype the dam1Genotype to set
     */
    public void setDam1Genotype(String dam1Genotype) {
        this.dam1Genotype = dam1Genotype;
    }

    /**
     * @return the dam2Genotype
     */
    public String getDam2Genotype() {
        return dam2Genotype;
    }

    /**
     * @param dam2Genotype the dam2Genotype to set
     */
    public void setDam2Genotype(String dam2Genotype) {
        this.dam2Genotype = dam2Genotype;
    }

    /**
     * @return the sireGenotype
     */
    public String getSireGenotype() {
        return sireGenotype;
    }

    /**
     * @param sireGenotype the sireGenotype to set
     */
    public void setSireGenotype(String sireGenotype) {
        this.sireGenotype = sireGenotype;
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