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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvCoatColorEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.integrationtier.dao.ContainerDAO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.exception.SaveException;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;
import jcms.web.common.PageBuilder;
import jcms.web.dto.LitterInfoDTO;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.dao.DbSetupDAO;
import jcms.integrationtier.dao.MouseUtilityDAO;
import jcms.integrationtier.dao.MouseDAO;
import jcms.middletier.businessobject.ContainerHistoryBO;
import jcms.web.dto.PenInfoDTO;
import jcms.web.service.SaveAppService;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.DbSetupDTO;
import jcms.web.common.MouseScheduleUtilities;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import org.primefaces.model.DualListModel;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.web.common.PhenotypeUtilities;
import jcms.integrationtier.jcmsWeb.UserEntity;
//import jcms.integrationtier.dto.UserPreferencesDTO;
//import jcms.integrationtier.dao.UserPreferencesDAO;
//import jcms.integrationtier.dto.cvPreferencesDTO;
import jcms.integrationtier.dao.cvPreferencesDAO;



/**
 *
 * @author rkavitha
 */
public class MouseBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0023231L;
    
    private MouseScheduleUtilities       pUtils = new MouseScheduleUtilities();
    private PhenotypeUtilities      ptUtils = new PhenotypeUtilities();
    private PhenotypeUtilities      pmlUtils = new PhenotypeUtilities();
    private SelectItemWrapper       selectItemWrapper;
    private PageBuilder             pageBuilder = new PageBuilder();
    private ListSupportDTO          listSupportDTO;
    private MouseEntity             mouseEntity;
    
    private CvMouseProtocolEntity   mouseProtocolEntity;
    private CvGenerationEntity      generationEntity;
    private CvSexEntity             sexEntity;
    private LifeStatusEntity        lifeStatusEntity;
    private CvBreedingStatusEntity  breedingStatusEntity;
    private CvCoatColorEntity       coatColorEntity;
    private CvDietEntity            dietEntity;
    private OwnerEntity             ownerEntity;
    private CvMouseOriginEntity     mouseOriginEntity;
    private CvCauseOfDeathEntity    causeOfDeathEntity;
    private ContainerEntity         penEntity;
    private LitterEntity            litterEntity;
    
    private boolean                 isPenResultCountDisplayed = false;
    private ListDataModel           penInfoDataModel;
    private ExtendedTableBean       penSelectionETB;
    private PenSearchDTO            penSearch;
    private PenBean                 newPen;
    private PenListCommon           penInfo;
    
    private boolean                 isLitterResultCountDisplayed = false;
    private ListDataModel           litterInfoDataModel;
    private ExtendedTableBean       litterSelectionETB;
    private LitterSearchDTO         litterSearch;
    private LitterListCommon        litterInfo;
    
    private String                  newTag               = "";
    private Integer                 penID                = null;
    private String                  litterID             = "";
    private String                  strainStatus         = "";
    
    private Mouse                   businessLogic;
    
    private int                     mouseKey = 0;
    
    private double                  mouseAge = 0;
    private int                     mouseAge1 = 0;
    private String                  ageMeasure = "Days";
    
    private boolean                  tempLifeStatus = false; //"A";
    private boolean                 tempShowCOD = false; //COD is displayed when this is true and tempLifeStatus is true
    private String                   tempStrainStatus = "";
    private String                   mouseID          = "";
    private int                      firstIntCharIndex = 0;
    private int                      lastIntCharIndex = 0;
    private boolean                  autoIncrement;
    private Boolean showReturnToSearchButton = true;
    
    private MouseFunctionsBean       mouseFunctions;
    
    private ArrayList<SelectItem> displayCards = initializeCards();
    private Integer entityKey;
    private ContainerDAO containerDAO = new ContainerDAO();
    private DualListModel useSchedulesModel = new DualListModel();
    private DualListModel phenotypeModel = new DualListModel();
    private String mouseGenotype = "";
    
    public MouseBean() {
        
    }
    
    public ArrayList<SelectItem> initializeCards(){
        try{
            return selectItemWrapper.getDetailCards();
        }
        catch(Exception e){
            System.out.println("Exception in MouseBean: " + e);
            return null;
        }
    }

    // initialize the owner to logged in owner
    public void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        if (ownerLst.size() > 0) {
            this.setOwnerEntity(ownerLst.get(0));
            System.out.println(ownerLst.get(0).getOwner());
        }
    }
    
    private void initialize() throws Exception {
        mouseEntity = new MouseEntity();
        penInfo = new PenListCommon();
        penSearch = new PenSearchDTO();
        setNewPen(new PenBean());
        penInfoDataModel = new ListDataModel();
        penSelectionETB = new ExtendedTableBean();

        mouseProtocolEntity = new CvMouseProtocolEntity();
        generationEntity = new CvGenerationEntity();
        sexEntity = new CvSexEntity();
        lifeStatusEntity = new LifeStatusEntity();
        breedingStatusEntity = new CvBreedingStatusEntity();
        coatColorEntity = new CvCoatColorEntity();
        dietEntity = new CvDietEntity();
        ownerEntity = new OwnerEntity();
        mouseOriginEntity = new CvMouseOriginEntity();
        causeOfDeathEntity = new CvCauseOfDeathEntity();
        penEntity = new ContainerEntity();

        isLitterResultCountDisplayed = false;
        litterInfoDataModel = new ListDataModel();
        litterSelectionETB = new ExtendedTableBean();
        litterSearch = new LitterSearchDTO();
        litterInfo = new LitterListCommon();
        useSchedulesModel.setSource(pUtils.getpDAO().getActiveBirthdateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
        phenotypeModel.setSource(ptUtils.getptDAO().getActiveCvPhenotypeTerms());
        phenotypeModel.setTarget(new ArrayList<cvPhenotypeTermDTO>());
        selectItemWrapper = new SelectItemWrapper();

        setLitterEntity(new LitterEntity());

        litterID = "";

        newTag = "";
        penID = null;

        ageMeasure = "Days";
        mouseAge = 0;
        mouseAge1 = 0;
        tempLifeStatus = false;
        tempShowCOD = false;
        autoIncrement = false;
        setMouseFunctions(new MouseFunctionsBean());

        businessLogic = new Mouse();
        mouseKey = getBusinessLogic().getKeyFromRequest();
        System.out.println("mouse key " + mouseKey);

        
        if (mouseKey > 0) {
            strainStatus = "All";
            tempStrainStatus = "All";
        } else {
            strainStatus = "Active only";
            tempStrainStatus = "Active only";
        }
        this.initialize(mouseKey);
    }

    private void initialize(Integer mouseKey) throws Exception {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");

        listSupportDTO = new ListSupportDTO(ownerLst);
        //Initialize the page builder here before it is changed by filling fields with current values
        this.initializePageBuilder();
        if (mouseKey != null && mouseKey > 0) {
            // Find the MouseEntity
            System.out.println("find mouse entity");
            this.mouseEntity = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(mouseKey));

            //load all the temp entities from Mouse entity
            if (mouseEntity != null) {
                //set the age
                this.calculateMouseAgeAction();

                if (mouseEntity.getExitDate() == null) {
                    mouseEntity.setExitDate(new Date());
                }
                
                if (mouseEntity.getProtocol() != null && !mouseEntity.getProtocol().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvMouseProtocolEntity entity : listSupportDTO.getAllCvMouseProtocol()) {
                        if (entity.getId().equals(mouseEntity.getProtocol())) {
                            this.mouseProtocolEntity = entity;
                            System.out.println("mouseProtocol " + entity.getId());
                            break;
                        }
                    }
                } 
                else {
                    this.mouseProtocolEntity = new CvMouseProtocolEntity();
                }
                
                useSchedulesModel.setSource(pUtils.getpDAO().getActiveBirthdateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
                //set the mouse use schedules to all the birthdate use schedules
                useSchedulesModel.setTarget(pUtils.getpDAO().getMouseUseScheduleTerms(mouseEntity.getMouseKey().toString(), "1", (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
                //remove those use schedules that the mouse is already a part of
                for(UseScheduleTermDTO dto : (ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget()){
                    useSchedulesModel.getSource().remove(dto);
                }
                
                //phenotypes: set the source phenotype list (left side of the control) to all the active phenotypes
                phenotypeModel.setSource(ptUtils.getptDAO().getActiveCvPhenotypeTerms());
                //Display in the target list (right side of the control) those Phenotypes associated with the mouse
                phenotypeModel.setTarget(ptUtils.getpmlDAO().getMousePhenotypeTerms(mouseEntity.getMouseKey().toString()));
                //remove from source side those phenotypes that the mouse already has
                for(cvPhenotypeTermDTO dto : (ArrayList<cvPhenotypeTermDTO>) phenotypeModel.getTarget()) {
                    phenotypeModel.getSource().remove(dto);
                }
                
                // generation
                if (mouseEntity.getGeneration() != null && !mouseEntity.getGeneration().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvGenerationEntity entity : listSupportDTO.getCvGeneration()) {
                        if (entity.getGeneration().equals(mouseEntity.getGeneration())) {
                            this.generationEntity = entity;
                            System.out.println("generation " + entity.getGeneration());
                            break;
                        }
                    }
                }

                // coat color
                if (mouseEntity.getCoatColor() != null && !mouseEntity.getCoatColor().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvCoatColorEntity entity : listSupportDTO.getCvCoatColor()) {
                        if (entity.getCoatColor().equals(mouseEntity.getCoatColor())) {
                            this.coatColorEntity = entity;
                            System.out.println("coatColor " + entity.getCoatColor());
                            break;
                        }
                    }
                } else {
                    this.coatColorEntity = new CvCoatColorEntity();
                }

                // cause of death
                if (mouseEntity.getCod() != null && !mouseEntity.getCod().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvCauseOfDeathEntity entity : listSupportDTO.getCvCauseOfDeath()) {
                        if (entity.getCod().equals(mouseEntity.getCod())) {
                            this.causeOfDeathEntity = entity;
                            System.out.println("causeOfDeath " + entity.getCod());
                            break;
                        }
                    }
                }

                // diet
                if (mouseEntity.getDiet() != null && !mouseEntity.getDiet().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                        if (entity.getDiet().equals(mouseEntity.getDiet())) {
                            this.dietEntity = entity;
                            System.out.println("Diet " + entity.getDiet());
                            break;
                        }
                    }
                } else {
                    this.dietEntity = new CvDietEntity();
                }

                // set the lifestatus
                if (mouseEntity.getLifeStatus() != null && !mouseEntity.getLifeStatus().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (LifeStatusEntity entity : listSupportDTO.getLifeStatus()) {
                        if (entity.getLifeStatus().equals(mouseEntity.getLifeStatus())) {
                            this.lifeStatusEntity = entity;
                            System.out.println("LifeStatus " + entity.getLifeStatus());

                            // set life status
                            if (entity.getLifeStatus() != null && entity.getExitStatus()) {
                                this.setTempLifeStatus(true);
                                this.setTempShowCOD(false);
                            } else {
                                this.setTempShowCOD(true); //This flag is to force the cod field to be viewable so COD can be entered when editing.
                            }
                            break;
                        }
                    }
                }

                if (mouseEntity.getOrigin() != null && !mouseEntity.getOrigin().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvMouseOriginEntity entity : listSupportDTO.getCvMouseOrigin()) {
                        if (entity.getMouseOrigin().equals(mouseEntity.getOrigin())) {
                            this.mouseOriginEntity = entity;
                            System.out.println("MouseOrigin " + entity.getMouseOrigin());
                            break;
                        }
                    }
                }

                if (mouseEntity.getOwner() != null && !mouseEntity.getOwner().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (OwnerEntity entity : listSupportDTO.getOwner()) {
                        if (entity.getOwner().equals(mouseEntity.getOwner())) {
                            this.ownerEntity = entity;
                            System.out.println("Owner " + entity.getOwner());
                            break;
                        }
                    }
                }

                if (mouseEntity.getSex() != null && !mouseEntity.getSex().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvSexEntity entity : listSupportDTO.getCvSex()) {
                        if (entity.getAbbreviation().equals(mouseEntity.getSex())) {
                            this.sexEntity = entity;
                            System.out.println("Sex " + entity.getAbbreviation());
                            break;
                        }
                    }
                }

                if (mouseEntity.getBreedingStatus() != null && !mouseEntity.getBreedingStatus().equals("")) {
                    // step through elements from cv and when match found, assign it
                    for (CvBreedingStatusEntity entity : listSupportDTO.getCvBreedingStatus()) {
                        if (entity.getAbbreviation().equals(mouseEntity.getBreedingStatus())) {
                            this.breedingStatusEntity = entity;
                            System.out.println("BreedingStatus " + entity.getAbbreviation());
                            break;
                        }
                    }
                }

                // set the pen ID
                if (mouseEntity.getPenKey() != null) {
                    Integer id = mouseEntity.getPenKey().getContainerID();
                    this.penID = id;
                    System.out.println("ContainerID " + this.penID);

                    // initialize newPen for update
                    Integer intContainerKey = mouseEntity.getPenKey().getContainerKey();
                    if (intContainerKey != null) {
                        updatePenChange(intContainerKey);
                    }
                }

                // set the litter ID
                if (mouseEntity.getLitterKey() != null) {
                    String id = mouseEntity.getLitterKey().getLitterID();
                    this.litterID = id;
                    System.out.println("LitterID " + this.litterID);

                }

                // set the new tag
                if (mouseEntity.getNewTag() != null && !mouseEntity.getNewTag().equals("")) {
                    this.newTag = mouseEntity.getNewTag();
                }
            }
            mouseGenotype = new MouseUtilityDAO().getMouseGenotypeByMouseKey(mouseKey);
        } else {
            // set defaults
            System.out.println("setting defaults");
            this.mouseEntity = new MouseEntity();
            mouseEntity.setBirthDate(new Date());
            mouseEntity.setExitDate(new Date());
            mouseEntity.setLifeStatus("A");
            this.initializeOwner();
            
            //HERE WE MUST INSERT THE USER PREFERENCES FOR DEFAULT VALUES             
            // protocol
             //Check for a default value and insert if there is one
             if (pageBuilder.getDefaultProtocolId() != null){
                 for (CvMouseProtocolEntity entity : listSupportDTO.getAllCvMouseProtocol()) {
                     if (entity.getId().equalsIgnoreCase(pageBuilder.getDefaultProtocolId())) {
                            this.mouseProtocolEntity = entity;
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultOrigin() != null){
                 for (CvMouseOriginEntity entity : listSupportDTO.getCvMouseOrigin()) {
                     if (entity.getMouseOrigin().equalsIgnoreCase(pageBuilder.getDefaultOrigin())) {
                            this.mouseOriginEntity = entity;
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultCOD() != null){
                 for (CvCauseOfDeathEntity entity : listSupportDTO.getCvCauseOfDeath()) {
                     if (entity.getCod().equalsIgnoreCase(pageBuilder.getDefaultCOD())) {
                            this.causeOfDeathEntity = entity;
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultRoomName() != null){
                 for (RoomEntity entity : listSupportDTO.getRoom()) {
                     if (entity.getRoomName().equalsIgnoreCase(pageBuilder.getDefaultRoomName())) {
                            this.newPen.setRoom(entity);
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultCoatColor() != null){
                 for (CvCoatColorEntity entity : listSupportDTO.getCvCoatColor()) {
                     if (entity.getCoatColor().equalsIgnoreCase(pageBuilder.getDefaultCoatColor())) {
                            this.coatColorEntity = entity;
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultDiet() != null){
                 for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                     if (entity.getDiet().equalsIgnoreCase(pageBuilder.getDefaultDiet())) {
                            this.dietEntity = entity;
                            break;
                     }
                 }
             }
             if (pageBuilder.getDefaultSex() != null){
                 for (CvSexEntity entity : listSupportDTO.getCvSex()) {
                     if (entity.getSex().equalsIgnoreCase(pageBuilder.getDefaultSex())) {
                            this.sexEntity = entity;
                            break;
                     }
                 }
             }
             
             if (pageBuilder.getDefaultBreedingStatus() != null){
                 for (CvBreedingStatusEntity entity : listSupportDTO.getCvBreedingStatus()) {
                     if (entity.getBreedingStatus().equalsIgnoreCase(pageBuilder.getDefaultBreedingStatus())) {
                            this.breedingStatusEntity = entity;
                            break;
                     }
                 }
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
        String hideField = DAO.getHideField(userKey,"mouseEdit", "protocol");
        //Have to convert hideField into boolean
        this.pageBuilder.setHideProtocolID(Boolean.parseBoolean(hideField));
        this.pageBuilder.setHideLitterID(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "litterID")));
        this.pageBuilder.setHideUseSchedules(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "useScheduleTermName")));
        this.pageBuilder.setHideOrigin(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "origin")));
        this.pageBuilder.setHideCOD(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "cod")));
        this.pageBuilder.setHideCODNotes(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "codNotes")));
        this.pageBuilder.setHideRoomName(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "roomName")));
        this.pageBuilder.setHidePhenotypes(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "phenotype")));
        this.pageBuilder.setHideMouseComment(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "comment")));
        this.pageBuilder.setHideCoatColor(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "coatColor")));
        this.pageBuilder.setHideDiet(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "diet")));
        this.pageBuilder.setHideSampleVialID(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "sampleVialID")));
        this.pageBuilder.setHideSampleVialTagPosition(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "sampleVialTagPosition")));
        this.pageBuilder.setHideReplacementTag(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "newTag")));
        
        //All fields that can have a default must have the default value saved too.
        this.pageBuilder.setDefaultProtocolId((DAO.GetDefaultValue(userKey,"mouseEdit", "protocol")));
        this.pageBuilder.setDefaultOrigin((DAO.GetDefaultValue(userKey,"mouseEdit", "origin")));
        this.pageBuilder.setDefaultCOD((DAO.GetDefaultValue(userKey,"mouseEdit", "cod")));
        this.pageBuilder.setDefaultRoomName((DAO.GetDefaultValue(userKey,"mouseEdit", "roomName")));
        this.pageBuilder.setDefaultCoatColor((DAO.GetDefaultValue(userKey,"mouseEdit", "coatColor")));
        this.pageBuilder.setDefaultDiet((DAO.GetDefaultValue(userKey,"mouseEdit", "diet")));
        this.pageBuilder.setDefaultSex((DAO.GetDefaultValue(userKey,"mouseEdit", "sex")));
        this.pageBuilder.setDefaultBreedingStatus((DAO.GetDefaultValue(userKey,"mouseEdit", "breedingStatus")));
    }
    
    public String addMouseAction() throws Exception {
        System.out.println("Add mouse action");
        this.initialize();

        return "addMouse";
    }
    
    public String calendarAddMouseAction() throws Exception {
        System.out.println("Add mouse action");
        this.initialize();
        
        LitterEntity le = new LitterEntity();
        le.setLitterKey(getKey("paramLitterKey"));
        
        litterEntity = (LitterEntity) getRepositoryService().baseFind(le);
        litterID = litterEntity.getLitterID();
        
        this.populateLitterInfo(litterID);
        return "addMouse";
    }

    /**
     * <b>Purpose:</b> Prepares a new object for data entry. <br />
     * <b>Overview:</b> This method is usually called from an Add button on a
     * list view. Data transfer objects are created in preparation for entering
     * data in a form view. The data entered is saved to these data transfer
     * objects by JSF API. <p />
     *
     * @return String action returned to faces-config.xml
     */
    public String editAction() throws Exception {
        System.out.println("Edit mouse action");
        System.out.println("mouse key " + this.getKey("paramMouseKey"));
        
        String returnToSearch = this.getRequestParameter("paramReturnToSearch");
        this.setShowReturnToSearchButton(true);
        if ((returnToSearch != null) && (returnToSearch.equalsIgnoreCase("false"))) {
            this.setShowReturnToSearchButton(false);
        }
        MouseEntity mouse = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(this.getKey("paramMouseKey")));
        
        this.initialize();
        System.out.println("done initializing");
        return "editMouse";
    }

    /**
     * <b>Purpose:</b> Prepares a new object for data entry. <br />
     * <b>Overview:</b> This method is usually called from an Add button on a
     * list view. Data transfer objects are created in preparation for entering
     * data in a form view. The data entered is saved to these data transfer
     * objects by JSF API. <p />
     *
     * @return String action returned to faces-config.xml
     */
    public String updateAction() {
        return "miceList";
    }

    /**
     * <b>Purpose:</b> Save a PenBean<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String savePenAction() throws Exception {
        try {
            if (newPen.getPenEntity().getContainerID() == 0 && this.penID != null)
                newPen.getPenEntity().setContainerID(this.penID);
            this.newPen.saveAction();
            this.penID = newPen.getPenEntity().getContainerID();
        } catch (Exception e) {
            String msg = "Save cage failed.  " + e.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            return null;
        }

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
    public String savePenAndCLoseAction() throws Exception {
        this.newPen.saveAction();
        this.setPenID(newPen.getPenEntity().getContainerID());
        // Redisplay the page
        return null;
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public boolean saveAction() throws Exception {
        boolean success = true;

        if (this.mouseEntity != null) {
            try {
                //validate mouse ID
                if (this.validateMouseID()) {
                    success = false;
                }

                if (this.validateReplacementTag()) {
                    success = false;
                }

                if (this.validateBirthDate()) {
                    success = false;
                }

                if (this.mouseEntity.getExitDate() != null) {
                    if (this.validateExitDate()) {
                        success = false;
                    }
                }

                //validate strain and generation
                if (this.mouseEntity.getStrainKey() == null) {
                    this.addToMessageQueue("Strain is missing. Please select a Strain.", FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Strain is missing. Please select a Strain."));
                    success = false;
                }

                if (this.generationEntity == null) {
                    this.addToMessageQueue("Generation is missing. Please select a Generation.",
                            FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Generation is missing. Please select a Generation."));
                    success = false;
                }

                // validate sex
                if (this.sexEntity == null) {
                    this.addToMessageQueue("Sex is missing. Please select the Sex.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Sex is missing. Please select the Sex."));
                    success = false;
                }

                // validate origin
                if (this.mouseOriginEntity == null) {
                    this.addToMessageQueue("Origin is missing. Please select the Origin.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Origin is missing. Please select the Origin."));
                    success = false;
                }

                if (success) {
                    //validate pen           
                    savePenAction();
                    if (this.penID != null && this.penID != 0) {
                        // check if it is valid
                        ContainerEntity me = this.mouseFunctions.findPen(this.penID);

                        if (me == null) {
                            this.addToMessageQueue("Cage# is invalid.", FacesMessage.SEVERITY_ERROR);
                            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Cage# is invalid."));
                            success = false;
                        }
                    }
                }
                
                if (success) {
                    //add mouse to protocol
                    if (this.mouseProtocolEntity != null) {
                        if (this.mouseProtocolEntity.getId() == null || this.mouseProtocolEntity.getId().equals("")) {
                            mouseEntity.setProtocol(null);
                        } else {
                            mouseEntity.setProtocol(this.mouseProtocolEntity.getId());
                        }
                    } else {
                        mouseEntity.setProtocol(null);
                    }

                    if (this.generationEntity != null) {
                        mouseEntity.setGeneration(this.generationEntity.getGeneration());
                    }

                    if (this.coatColorEntity != null) {
                        if (this.coatColorEntity.getCoatColor() == null || this.coatColorEntity.getCoatColor().equals("")) {
                            mouseEntity.setCoatColor(null);
                        } else {
                            mouseEntity.setCoatColor(this.coatColorEntity.getCoatColor());
                        }
                    } else {
                        mouseEntity.setCoatColor(null);
                    }

                    if (this.causeOfDeathEntity != null) {
                        mouseEntity.setCod(this.causeOfDeathEntity.getCod());
                    }

                    if (this.dietEntity != null) {
                        if (this.dietEntity.getDiet() == null || this.dietEntity.getDiet().equals("")) {
                            mouseEntity.setDiet(null);
                        } else {
                            mouseEntity.setDiet(this.dietEntity.getDiet());
                        }
                    } else {
                        mouseEntity.setDiet(null);
                    }

                    if (this.lifeStatusEntity != null) {
                        mouseEntity.setLifeStatus(this.lifeStatusEntity.getLifeStatus());

                        if (this.getLifeStatusEntity().getLifeStatus() != null
                                && !this.getLifeStatusEntity().getExitStatus()) {
                            mouseEntity.setExitDate(null);
                            mouseEntity.setCod("");
                            mouseEntity.setCodNotes("");
                        }
                    }

                    if (this.mouseOriginEntity != null) {
                        mouseEntity.setOrigin(this.mouseOriginEntity.getMouseOrigin());
                    }

                    if (this.ownerEntity != null) {
                        mouseEntity.setOwner(this.ownerEntity.getOwner());
                    }

                    if (this.sexEntity != null) {
                        mouseEntity.setSex(this.sexEntity.getAbbreviation());
                    }

                    if (this.breedingStatusEntity != null) {
                        System.out.println("set breeding status " + this.breedingStatusEntity.getAbbreviation());
                        mouseEntity.setBreedingStatus(this.breedingStatusEntity.getAbbreviation());
                    }

                    if (this.newTag == null || this.newTag.equals("")) {
                        mouseEntity.setNewTag(null);
                    } else {
                        mouseEntity.setNewTag(this.newTag);
                    }

                    // set the pen ID
                    if (this.penID != null && this.penID > 0) {
                        this.mouseEntity.setPenKey(this.mouseFunctions.findPen(this.penID));
                    }

                    // set the litter ID
                    if (this.litterID != null && !this.litterID.equals("")) {
                        litterEntity = new LitterEntity();

                        litterEntity = (LitterEntity) getRepositoryService().
                                baseFindByLitterID(new LitterEntity(), this.litterID);
                        this.mouseEntity.setLitterKey(litterEntity);

                    }

                    System.out.println("Mouse Entity Details");
                    System.out.println("Protocol " + mouseEntity.getProtocol());
                    System.out.println("Generation " + mouseEntity.getGeneration());
                    System.out.println("Cage ID " + mouseEntity.getPenKey().getKey());
                    System.out.println("CoatColor " + mouseEntity.getCoatColor());
                    System.out.println("Cod " + mouseEntity.getCod());
                    System.out.println("Diet " + mouseEntity.getDiet());
                    System.out.println("LifeStatus " + mouseEntity.getLifeStatus());
                    System.out.println("Origin " + mouseEntity.getOrigin());
                    System.out.println("Owner " + mouseEntity.getOwner());
                    System.out.println("Sex " + mouseEntity.getSex());
                    System.out.println("BreedingStatus " + mouseEntity.getBreedingStatus());

                    System.out.println("About to Save");
                }
            } catch (Exception se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
                success = false;
            }
        } else {
            this.getLogger().logError(this.formatLogMessage("Mouse entity is null.", "save"));
        }
        return success;
    }

    /**
     * <b>Purpose:</b> Save a Mouse<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String insertMouseAction() throws Exception {
        if (this.mouseEntity != null) {
            try {
                System.out.println("In insertMouseAction");
                
                // for insert operation
                if (this.saveAction()) {
                    if (mouseEntity.getMouseKey() == null || mouseEntity.getMouseKey() == 0) {

                        // generate primary key
                        Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(mouseEntity);
                        String primaryKey;
                        if (null == pk || 0 == pk) {
                            mouseEntity.setMouseKey(1);
                            primaryKey = "1";
                        } else {
                            mouseEntity.setMouseKey(pk + 1);
                            primaryKey = new Integer(pk + 1).toString();
                        }

                        // set the version
                        mouseEntity.setVersion(0);

                        new SaveAppService().baseCreate(this.mouseEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save",
                                "Mouse ID " + mouseEntity.getId() + " has been inserted"));

                        this.mouseEntity.setMouseKey(0);
                        this.addToMessageQueue("Mouse ID " + mouseEntity.getId() + " has been saved",
                                FacesMessage.SEVERITY_INFO);
                        
                        //initialize mouse
                        MouseDTO mouse = new MouseDTO();
                        mouse.setMouse_key(primaryKey);
                        mouse.setBirthDate(mouseEntity.getBirthDate());
                        //add mouse to use schedules
                        for(UseScheduleTermDTO dto : (ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget()){
                            pUtils.addMouseToBirthDateUseSchedule(dto, mouse);
                            this.addToMessageQueue("Mouse " + mouseEntity.getId() + " added to use schedule " + dto.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                        }

                        //add phenotypes to the mouse
                        for(cvPhenotypeTermDTO dto : (ArrayList<cvPhenotypeTermDTO>) phenotypeModel.getTarget()){
                            pmlUtils.getpmlDAO().addPhenotypeToMouse(dto, mouse);
                            this.addToMessageQueue("Phenotype " + dto.getPhenotypeTermName() + " added. ", FacesMessage.SEVERITY_INFO);
                        }
                        
                        if (this.autoIncrement) {
                            this.mouseID = mouseEntity.getId();
                            this.mouseEntity.setId(mouseFunctions.incrementID(mouseID, "JCMS_MOUSEID_INCREMENT_RIGHTMOST"));
                            System.out.println("new mouse ID " + this.mouseEntity.getId());
                        }
                    } else {
                        this.getLogger().logError(this.formatLogMessage("save", "Current Mouse ID "
                                + mouseEntity.getId() + " already submitted"));
                        this.addToMessageQueue("Current Mouse ID " + mouseEntity.getId() + " already submitted",
                                FacesMessage.SEVERITY_ERROR);
                    }
                    checkNumberMicePerPen();
                    if(sexEntity.getAbbreviation() != null){
                        if(containerDAO.multipleGendersInPen(penID.toString(), sexEntity.getAbbreviation())){
                            this.addToMessageQueue("You have added this mouse to a pen that contains one or more mice of a different sex, to move this mouse to another pen use the Edit Mouse page.", FacesMessage.SEVERITY_WARN);
                        }
                    }
                }
            } catch (SaveException se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
                return null;
            } // General catch-all for failed saves. The exception's message has already been customized for user display.
            catch (SaveEntityException ex) {
                String msg = "The system failed to save any mouse updates.  "
                        + "Please report this problem to the web master with date "
                        + "and time of error.  ";

                // Display user friendly error message
                this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));

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
            this.getLogger().logError(this.formatLogMessage("Mouse entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }
  
    public void downloadCageCardAction(){
        if (this.penID != null && penID != 0) {
            CageCardBean theCard = new CageCardBean();
            theCard.setEntityKey(entityKey);
            theCard.setCageID(new Integer(penID).toString());
            theCard.setCardQuantity("single");
            theCard.initializeDTOForDownload();
            theCard.downloadCageCardAction();
        }
    }
    
    /**
     * <b>Purpose:</b> Save a Mouse<br /> <b>Overview:</b> Pass on to Business
     * Tier to save. <p />
     *
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String updateMouseAction() throws Exception {
        // ******************************************************
        // Transfers control from the FORM view to the LIST view
        // pending a successful Save action.
        // ******************************************************

        if (this.mouseEntity != null) {
            try {
                if (this.saveAction()) {
                    // for edit operation
                    if (mouseEntity.getMouseKey() != null && mouseEntity.getMouseKey() > 0) {

                        // set the version
                        int ver = mouseEntity.getVersion();
                        System.out.println("previous version " + ver);
                        mouseEntity.setVersion(ver + 1);

                        new SaveAppService().saveMouse(this.mouseEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save", "Mouse ID "
                                + mouseEntity.getId() + " has been updated"));
                        this.getLogger().logInfo(this.formatLogMessage("save", "Version "
                                + mouseEntity.getVersion() + " has been updated"));
                        this.addToMessageQueue("Mouse ID " + mouseEntity.getId() + " has been updated",
                                FacesMessage.SEVERITY_INFO);
                        //use schedule stuff
                        /*check if current active mouse use schedules match the target list, if they do, nothing
                          has to be done, if they don't have to add the mouse to the use schedules*/
                        //current use schedules are all the use schedules mouse is currently in according to workgroup
                        ArrayList<UseScheduleTermDTO> currentUseSchedules = pUtils.getpDAO().getMouseUseScheduleTerms(mouseEntity.getMouseKey().toString(), "1", (ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
                        //target use schedules are all the use schedules that the user wants the mosue to be in according to workgroup
                        ArrayList<UseScheduleTermDTO> targetUseSchedules = (ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget();
                        if(!targetUseSchedules.equals(currentUseSchedules)){
                            /*check to see if current use schedule is in the target use schedules, if it is not
                             then the mouse was removed from that use schedule*/
                            for(UseScheduleTermDTO currentUseSchedule : currentUseSchedules){
                                if(!targetUseSchedules.contains(currentUseSchedule)){
                                    //remove the mouse from the use schedule
                                    //first need to make sure none of the uses are done, if they are mouse cannot be removed from use schedule
                                    MouseDTO mouse = new MouseDTO();
                                    mouse.setID(mouseEntity.getId());
                                    mouse.setBirthDate(mouseEntity.getBirthDate());
                                    mouse.setMouse_key(mouseEntity.getMouseKey().toString());
                                    //check to see if the use schedule has been started (data assocaited w/ uses OR usage marked as done) before being able to delete
                                    if(!pUtils.getpDAO().useScheduleStarted(currentUseSchedule, mouse)){
                                        pUtils.getpDAO().deleteUseSchedule(mouse, currentUseSchedule);
                                        this.addToMessageQueue("Mouse " + mouseEntity.getId() + " removed from use schedule " + currentUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                                    }
                                    else{
                                        //use schedule can't be removed, tell the user and add it back to the target and remove it from the source.
                                        useSchedulesModel.getTarget().add(currentUseSchedule);
                                        useSchedulesModel.getSource().remove(currentUseSchedule);
                                        this.addToMessageQueue("Mouse " + mouseEntity.getId() + " could not be removed from use schedule " + currentUseSchedule.getUseScheduleTermName() + " because one or more of the uses has data or been marked as done.", FacesMessage.SEVERITY_ERROR);
                                    }
                                }
                            }
                            /*check to see if the target use schedule is in the current use schedule list, 
                             if it is not it needs to be added.*/
                            for(UseScheduleTermDTO targetUseSchedule : targetUseSchedules){
                                if(!currentUseSchedules.contains(targetUseSchedule)){
                                    //add mouse to the use schedule
                                    MouseDTO mouse = new MouseDTO();
                                    mouse.setID(mouseEntity.getId());
                                    mouse.setBirthDate(mouseEntity.getBirthDate());
                                    mouse.setMouse_key(mouseEntity.getMouseKey().toString());
                                    pUtils.addMouseToBirthDateUseSchedule(targetUseSchedule, mouse);
                                    this.addToMessageQueue("Mouse " + mouseEntity.getId() + " added to use schedule " + targetUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                                }
                            }
                        }
                        
                        //phenotype stuff
                        /*check if the current phenotype list for the mouse matchs the target list, if yes, do nothing
                        */
                        
                        ArrayList<cvPhenotypeTermDTO> currentPhenotypeList = ptUtils.getpmlDAO().getMousePhenotypeTerms(mouseEntity.getMouseKey().toString());
                        //target phenotypes are all the phenotypes that the user put the target list
                        ArrayList<cvPhenotypeTermDTO> targetPhenotypeList = (ArrayList<cvPhenotypeTermDTO>) phenotypeModel.getTarget();
                        if(!targetPhenotypeList.equals(currentPhenotypeList)){
                            /*check to see if any current phenotypes are in the target phenotypes, if any are not
                             then the phenotype must be removed from the list for the mouse*/
                            for(cvPhenotypeTermDTO currentPhenotype : currentPhenotypeList){
                                if(!targetPhenotypeList.contains(currentPhenotype)){
                                    //remove the phenotype from the mouse's phenotype list
                                    MouseDTO mouse = new MouseDTO();
                                    mouse.setID(mouseEntity.getId());
                                    mouse.setMouse_key(mouseEntity.getMouseKey().toString());
                                    pmlUtils.getpmlDAO().deletePhenotypeFromMouse(mouse, currentPhenotype);
                                    this.addToMessageQueue("Phenotype " + currentPhenotype.getPhenotypeTermName() + " removed. ", FacesMessage.SEVERITY_INFO);
                                }
                            }
                            /*check to see if the target phenotypes are in the current phenotype list, 
                             if any are not it needs to be added.*/
                            for(cvPhenotypeTermDTO targetPhenotype : targetPhenotypeList){
                                if(!currentPhenotypeList.contains(targetPhenotype)){
                                    //add phenotype to the mouse's phenotype list
                                    MouseDTO mouse = new MouseDTO();
                                    mouse.setID(mouseEntity.getId());
                                    mouse.setMouse_key(mouseEntity.getMouseKey().toString());
                                    pmlUtils.getpmlDAO().addPhenotypeToMouse(targetPhenotype, mouse);
                                    this.addToMessageQueue("Phenotype " + targetPhenotype.getPhenotypeTermName() + " added. ", FacesMessage.SEVERITY_INFO);
                                }
                            }
                        }
                    }
                }
            } catch (SaveException se) {
                String msg = se.getMessage();
                addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
                return null;
            } // General catch-all for failed saves. The exception's message has already been customized for user display.
            catch (SaveEntityException ex) {
                String msg = "The system failed to save any mouse updates.  "
                        + "Please report this problem to the web master with date "
                        + "and time of error.  ";

                // Display user friendly error message
                this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));

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
            this.getLogger().logError(this.formatLogMessage("Mouse entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }

    /**
     * Calculate the age of this mouse and display it on the form It is the
     * difference between birth date and exit date. If there is an exit date,
     * calculate using it. If no exit date, calculate using today.
     */
    public void calculateMouseAgeAction(ValueChangeEvent event) {
        this.setAgeMeasure(event.getNewValue().toString());
        this.calculateMouseAgeAction();
    }
    public void calculateMouseAgeAction() {
        Date endDate;
        Date birthDate = this.getMouseEntity().getBirthDate();
        int dblAge = 0;
        double age = 0;
        this.mouseAge = 0;
        this.mouseAge1 = 0;

        if (birthDate != null) {

            if (this.getMouseEntity().getExitDate() != null) {
                endDate = getMouseEntity().getExitDate();
            } else {
                endDate = new Date();
            }

            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();

            cal1.setTime(birthDate);
            cal2.setTime(endDate);
            Date d1 = cal1.getTime();
            Date d2 = cal2.getTime();

            dblAge = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

            if (dblAge > 0) {
                if (this.ageMeasure.equalsIgnoreCase("days")) {
                    this.setMouseAge(dblAge);
                    this.setMouseAge1(dblAge);
                    System.out.println(this.getMouseAge() + " days");
                    //round to one decimal for weeks
                } else if (this.ageMeasure.equalsIgnoreCase("weeks")) {
                    age = dblAge / 7;
                    this.setMouseAge(this.roundOneDecimals(age));
                    System.out.println(this.getMouseAge() + " weeks");
                    //round to two decimal for months
                } else if (this.ageMeasure.equalsIgnoreCase("months")) {
                    age = dblAge / 30.4375;
                    this.setMouseAge(this.roundTwoDecimals(age));
                    System.out.println(this.getMouseAge() + " months");
                } else {
                    this.setMouseAge(0);
                }
            } else {
                this.setMouseAge(0);
            }
        }
    }

    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    private double roundOneDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }

    public String clearMouseAction() throws Exception {
        this.initialize();

        return "addMouse";
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public void validateDateAction() {
        // calculate the age
        if (!this.validateBirthDate() && this.mouseKey > 0) {
            this.calculateMouseAgeAction();
        }
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public boolean validateBirthDate() {
        boolean flag = false;

        // date cannot be greater than today
        if (this.mouseEntity.getBirthDate().compareTo(new Date()) > 0) {
            this.addToMessageQueue("Date Of Birth cannot be greater than Today",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Date Of Birth cannot be greater than Today"));
            flag = true;
        }
        return flag;
    }

    /*
     * This method is called on change event on Exit date to validate
     */
    public void validateExitDateAction() {
        // calculate the age
        if (!this.validateExitDate() && this.mouseKey > 0) {
            this.calculateMouseAgeAction();
        }
    }

    /*
     * This method is called on change event on Exit date to validate
     */
    public boolean validateExitDate() {
        boolean flag = false;

        // start date cannot be greater than end date
        if (this.mouseEntity.getBirthDate().compareTo(this.mouseEntity.getExitDate()) > 0) {
            this.addToMessageQueue("Exit date cannot be less "
                    + "than Birth date", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Exit date cannot be less than Birth date"));
            flag = true;
        }

        // date cannot be greater than today
        if (this.mouseEntity.getExitDate().compareTo(new Date()) > 0) {
            this.addToMessageQueue("Exit Date is in future",
                    FacesMessage.SEVERITY_WARN);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Exit Date is in future"));
            //flag = true;
        }
        return flag;
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public void validateLitterGenerationAction() {
        CvGenerationEntity genEntity = new CvGenerationEntity();
        String s = "";
        System.out.println("validate generation");
        genEntity = this.getGenerationEntity();

        try {
            if (genEntity != null) {
                s = genEntity.getGeneration();
            }
            System.out.println("generation " + s);

            if ((s == null) || (s.isEmpty())) {
                this.addToMessageQueue("Generation is missing. Please select a Generation.",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Generation is missing. Please select a Generation."));
            } else {
                Integer lID = 0;
                if (this.getLitterID() != null && !this.getLitterID().equals("")) {
                    lID = Integer.parseInt(this.getLitterID());
                }

                System.out.println("litter key " + lID);

                if (lID > 0) {
                    ReportFacadeLocal rfl = new BusinessTierPortal().getReportFacadeLocal();
                    String generation = rfl.findLitterGeneration(lID);
                    System.out.println("litter generation " + generation + " and selected generation " + s);

                    if (generation != null && !generation.equals("") && !generation.equalsIgnoreCase(s)) {
                        this.addToMessageQueue("The GENERATION you've selected for this mouse does not agree "
                                + "with its litter generation.",
                                FacesMessage.SEVERITY_WARN);

                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                "The GENERATION you've selected for this mouse does not agree with its "
                                + "litter generation "));
                    }
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public void validateLitterStrainAction() {
        StrainEntity sEntity = new StrainEntity();
        int s = 0;
        System.out.println("validate strain");
        sEntity = this.mouseEntity.getStrainKey();

        try {
            if (sEntity != null) {
                s = sEntity.getStrainKey();
            }
            System.out.println("strain " + s);

            if (s == 0) {
                this.addToMessageQueue("Strain is missing. Please select a Strain.",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Strain is missing. Please select a Strain."));
            } else {
                Integer lID = 0;
                if (this.getLitterID() != null && !this.getLitterID().equals("")) {
                    lID = Integer.parseInt(this.getLitterID());
                }

                System.out.println("litter key " + lID);

                if (lID > 0) {
                    ReportFacadeLocal rfl = new BusinessTierPortal().getReportFacadeLocal();
                    int skey = rfl.findLitterStrain(lID);
                    System.out.println("litter strain " + skey + " and selected strain " + s);

                    if (skey > 0 && skey != s) {
                        this.addToMessageQueue("The STRAIN you've selected for this mouse does not agree "
                                + "with its litter STRAIN.",
                                FacesMessage.SEVERITY_WARN);

                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                "The STRAIN you've selected for this mouse does not agree with its "
                                + "litter STRAIN."));
                    }
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public boolean validateMouseID() {
        boolean flag = false;

        try {
            // Check for null or empty String.
            String errMsg = null;
            String s = this.mouseEntity.getId();

            System.out.println("Entered into validate mouseID");

            if ((s == null) || (s.isEmpty())) {
                errMsg = "Mouse ID is missing. Please enter a Mouse ID.";
                this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
                flag = true;
            } else {
                // Check that mouse id is unique.
                MouseEntity mEntity = (MouseEntity) getRepositoryService().
                        baseFindByMoueID(new MouseEntity(), s);

                // edit
                if (mouseKey > 0) {
                    if (mEntity != null && mouseKey != mEntity.getMouseKey()) {
                        System.out.println("Entered into validate mouseID for mouse key > 0");
                        errMsg = "Mouse ID " + mEntity.getId() + " already exists. Please choose a unique Mouse ID.";
                        this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                errMsg));
                        flag = true;
                    }
                } // add
                else {
                    if (mEntity != null) {
                        errMsg = "Mouse ID " + mEntity.getId() + " already exists. Please choose a unique Mouse ID.";
                        this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                errMsg));
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        System.out.println("Mouse validate flag is: " + flag);
        return flag;
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public boolean validateReplacementTag() {
        boolean flag = false;
        try {
            String s = this.newTag;
            String errMsg = null;

            if ((s != null) && (!s.isEmpty())) {
                // Check that user name is unique.
                MouseEntity mEntity = (MouseEntity) getRepositoryService().
                        baseFindByNewTag(new MouseEntity(), s);

                // edit
                if (mouseKey > 0) {
                    if (mEntity != null && mouseKey != mEntity.getMouseKey()) {
                        errMsg = "Replacement Tag " + mEntity.getNewTag() + " already exists. Please choose a unique Replacement Tag.";
                        this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                errMsg));
                        flag = true;
                    }
                } // add
                else {
                    if (mEntity != null) {
                        errMsg = "Replacement Tag " + mEntity.getNewTag() + " already exists. Please choose a unique Replacement Tag.";
                        this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                                errMsg));
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return flag;
    }

    public void populateLitterInfoValueChangeListener(ValueChangeEvent event) {
        try {
            this.setLitterID(event.getNewValue().toString());
            this.populateLitterInfo(this.getLitterID());
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }

    private void populateLitterInfo(String litterID) {
        try {
            Result result = null;
            int strain = 0;
            Date litterBirthDate = null;
            String gen = "";
            ReportFacadeLocal rfl = new BusinessTierPortal().getReportFacadeLocal();

            if (litterID != null && !litterID.equals("")) {
                result = rfl.findLitterInfo(litterID);
                SortedMap[] map = result.getRows();

                for (int j = 0; j < map.length; ++j) {
                    // get the generation
                    if (map[j].get("generation") != null) {
                        gen = (map[j].get("generation").toString());
                    }

                    // get the strain
                    if (map[j].get("_strain_key") != null) {
                        strain = (Integer) map[j].get("_strain_key");
                    }
                    
                    //get the birthdate
                    if(map[j].get("birthDate") != null){
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String tempBirthDate = map[j].get("birthDate").toString();
                        litterBirthDate = formatter.parse(tempBirthDate);
                    }
                }
                // generation               
                // step through elements from cv and when match found, assign it
                for (CvGenerationEntity entity : listSupportDTO.getCvGeneration()) {
                    if (entity.getGeneration().equals(gen)) {
                        this.generationEntity = entity;
                        break;
                    }
                }

                // strain
                // step through elements from cv and when match found, assign it
                for (StrainEntity entity : listSupportDTO.getAllStrains(this.getUserEntity())) {
                    if (entity.getStrainKey() == strain) {
                        this.mouseEntity.setStrainKey(entity);
                        break;
                    }
                }
                
                if(litterBirthDate != null){
                    this.mouseEntity.setBirthDate(litterBirthDate);
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public void setLifeStatusAction() {
        this.setTempLifeStatus(false);
        // set life status
        if (this.lifeStatusEntity != null && this.getLifeStatusEntity().
                getLifeStatus() != null && this.getLifeStatusEntity().
                getExitStatus()) {
            this.setTempLifeStatus(true);
        }
    }

    /*
     * This method is called on change event on start date and end date to
     * validate
     */
    public void setStrainStatusAction() {
        if (this.strainStatus != null && !this.strainStatus.equals("")) {
            this.setTempStrainStatus(this.strainStatus);
        }
        this.selectItemWrapper = new SelectItemWrapper();
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

    public void selectPenAction() {
        System.out.println("Selected Pen");
        int intContainerKey = penInfo.getSelectedPenKey(this.penSelectionETB, this.penInfoDataModel);
        updatePenChange(intContainerKey);
        if(sexEntity != null){
            if(containerDAO.multipleGendersInPen(new Integer(penInfo.getSelectedPen(penSelectionETB, penInfoDataModel)).toString(), sexEntity.getAbbreviation())){
                this.addToMessageQueue("You are about to add this mouse to a pen that contains one or more mice of a different gender, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
            }
        }
    }
    
    public void sexChangeListener(){
        /* Adding here a check to see if the next pen check box is true, if so don't bother with this check */
        if(!newPen.isNextPen()) {  
            if(!newPen.getPenEntity().equals(new ContainerEntity())){
                if(containerDAO.multipleGendersInPen(new Integer(newPen.getPenEntity().getContainerID()).toString(), sexEntity.getAbbreviation())){
                    this.addToMessageQueue("You are about to add this mouse to a pen that contains one or more mice of a different gender, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                }
            }
        }  
    }
    
    public void validatePreviewCageCard(){
        if(penID == null || penID.intValue() == 0){            
            this.addToMessageQueue("Cage ID is missing. Please select a Cage ID.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void penValueChangeListener(){
        if(penID != null && penID.intValue() != 0){
            ContainerHistoryBO containerHistoryBO = new ContainerHistoryBO();
            ContainerEntity containerEntity = new ContainerEntity();

            try{
                //get PEN ID 
                Integer.parseInt(penID.toString());
                //get pen info
                ContainerDTO cDTO = containerDAO.getContainerByID(penID.toString());
                //get pen key IF container exists
                if(!cDTO.getContainer_key().equals("")){
                    int intContainerKey = Integer.parseInt(cDTO.getContainer_key());
                    containerEntity = containerHistoryBO.getContainerEntityByContainerKey(intContainerKey);
                    newPen.setPenEntity(containerEntity);
                    RoomEntity roomEntity = containerHistoryBO.getRoomEntityByContainerKey(intContainerKey);
                    newPen.setRoom(roomEntity);

                    // this is an edit do not auto increment next pen.
                    newPen.setNextPen(false);
                    System.out.println("pen ID " + this.getPenID());
                    if(sexEntity.getAbbreviation() != null){
                        if(containerDAO.multipleGendersInPen(penID.toString(), sexEntity.getAbbreviation())){
                            this.addToMessageQueue("You are about to add this mouse to a pen that contains one or more mice of a different gender, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                        }
                    }
                }
                else{
                    newPen.setPenEntity(new ContainerEntity());
                }
                checkNumberMicePerPen();
            }
            catch(Exception e){
                addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    private void updatePenChange(int intContainerKey)
    {
        ContainerHistoryBO containerHistoryBO = new ContainerHistoryBO();
        ContainerEntity containerEntity = new ContainerEntity();
        try {
            if (intContainerKey > 0) {
                containerEntity = containerHistoryBO.getContainerEntityByContainerKey(intContainerKey);
                this.setPenID(containerEntity.getContainerID());
                // compare to mouseEntity.penKey
                newPen.setPenEntity(containerEntity);
                RoomEntity roomEntity = containerHistoryBO.getRoomEntityByContainerKey(intContainerKey);
                newPen.setRoom(roomEntity);
                // this is an edit do not auto increment next pen.
                newPen.setNextPen(false);
                System.out.println("pen ID " + this.getPenID());
                checkNumberMicePerPen();
            }
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
        }
    }
    
    private void checkNumberMicePerPen() {
        MouseDAO dao = new MouseDAO();
        DbSetupDTO dtoMaxMicePerCage = new DbSetupDAO().getMTSMaxMicePerPen() ;
        if (dtoMaxMicePerCage.getMTSValue() != null && dtoMaxMicePerCage.getMTSValue().length() > 0) {
            Integer micePerCageCount = dao.getNumberOfMicePerPen(newPen.getPenEntity().getContainerKey());
            if (micePerCageCount.intValue() >= new Integer(dtoMaxMicePerCage.getMTSValue()).intValue()) {
                if (micePerCageCount.intValue() == new Integer(dtoMaxMicePerCage.getMTSValue()).intValue()) {
                    this.addToMessageQueue("You are about to exceed the maximum number of "+ dtoMaxMicePerCage.getMTSValue() +" mice per cage, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);
                } else {
                    this.addToMessageQueue("You have exceeded the maximum number of "+ dtoMaxMicePerCage.getMTSValue() +" mice per cage, are you sure you want to continue?", FacesMessage.SEVERITY_WARN);                    
                }
                
                this.addToMessageQueue("Navigate to 'Administration -> Miscellaneous -> JCMS Setup Variables -> Container tab -> field Maximum Mice Per Cage' to change this setting. ", FacesMessage.SEVERITY_WARN);
            }
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

    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearNewPenPopupAction() {
        this.newPen.clearPenAction();
    }

    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearLitterPopupAction() {

        System.out.println("Clear popup is called");
        litterInfoDataModel.setWrappedData(new ArrayList<LitterInfoDTO>());
        isLitterResultCountDisplayed = false;
        this.setLitterSearch(new LitterSearchDTO());
    }

    public void selectLitterAction() {
        System.out.println("Selected Litter");

        this.setLitterID(litterInfo.getSelectedLitter(this.litterSelectionETB,
                this.litterInfoDataModel));
        this.populateLitterInfo(this.getLitterID());
    }

    public void litterSearchAction() {
        System.out.println("Litter Search Results");

        // check if any search criteria is selected, else throw error message
        if (this.litterSearch != null && ((litterSearch.getLitterID() != null
                && !litterSearch.getLitterID().equals(""))
                || (litterSearch.getMatingID() != null
                && !litterSearch.getMatingID().equals(""))
                || (litterSearch.getBirthDate() != null)
                || //(!litterSearch.getStatus().equals("") && litterSearch.getStatus() != null) || 
                (litterSearch.getStrain() != null
                && !litterSearch.getStrain().getStrainName().equals("")
                && litterSearch.getStrain().getStrainName() != null))) {

            this.setLitterInfoDataModel(this.litterInfo.litterSearch(this.litterSearch));
            setIsLitterResultCountDisplayed(true);
        } else {
            this.addToMessageQueue("Select some search criteria",
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Select some search criteria"));
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
     * @return the mouseEntity
     */
    public MouseEntity getMouseEntity() {
        return mouseEntity;
    }

    /**
     * @param mouseEntity the mouseEntity to set
     */
    public void setMouseEntity(MouseEntity mouseEntity) {
        this.mouseEntity = mouseEntity;
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
     * @return the sexEntity
     */
    public CvSexEntity getSexEntity() {
        return sexEntity;
    }

    /**
     * @param sexEntity the sexEntity to set
     */
    public void setSexEntity(CvSexEntity sexEntity) {
        this.sexEntity = sexEntity;
    }

    /**
     * @return the lifeStatusEntity
     */
    public LifeStatusEntity getLifeStatusEntity() {
        return lifeStatusEntity;
    }

    /**
     * @param lifeStatusEntity the lifeStatusEntity to set
     */
    public void setLifeStatusEntity(LifeStatusEntity lifeStatusEntity) {
        this.lifeStatusEntity = lifeStatusEntity;
    }

    /**
     * @return the breedingStatusEntity
     */
    public CvBreedingStatusEntity getBreedingStatusEntity() {
        return breedingStatusEntity;
    }

    /**
     * @param breedingStatusEntity the breedingStatusEntity to set
     */
    public void setBreedingStatusEntity(CvBreedingStatusEntity breedingStatusEntity) {
        this.breedingStatusEntity = breedingStatusEntity;
    }

    /**
     * @return the coatColorEntity
     */
    public CvCoatColorEntity getCoatColorEntity() {
        return coatColorEntity;
    }

    /**
     * @param coatColorEntity the coatColorEntity to set
     */
    public void setCoatColorEntity(CvCoatColorEntity coatColorEntity) {
        this.coatColorEntity = coatColorEntity;
    }

    /**
     * @return the dietEntity
     */
    public CvDietEntity getDietEntity() {
        return dietEntity;
    }

    /**
     * @param dietEntity the dietEntity to set
     */
    public void setDietEntity(CvDietEntity dietEntity) {
        this.dietEntity = dietEntity;
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
     * @return the causeOfDeathEntity
     */
    public CvCauseOfDeathEntity getCauseOfDeathEntity() {
        return causeOfDeathEntity;
    }

    /**
     * @param causeOfDeathEntity the causeOfDeathEntity to set
     */
    public void setCauseOfDeathEntity(CvCauseOfDeathEntity causeOfDeathEntity) {
        this.causeOfDeathEntity = causeOfDeathEntity;
    }

    /**
     * @return the newTag
     */
    public String getNewTag() {
        return newTag;
    }

    /**
     * @param newTag the newTag to set
     */
    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }

    /**
     * @return the businessLogic
     */
    public Mouse getBusinessLogic() {
        return businessLogic;
    }

    /**
     * @param businessLogic the businessLogic to set
     */
    public void setBusinessLogic(Mouse businessLogic) {
        this.businessLogic = businessLogic;
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

    /**
     * @return the ageMeasure
     */
    public String getAgeMeasure() {
        return ageMeasure;
    }

    /**
     * @param ageMeasure the ageMeasure to set
     */
    public void setAgeMeasure(String ageMeasure) {
        this.ageMeasure = ageMeasure;
    }

    /**
     * @return the penID
     */
    public Integer getPenID() {
        return penID;
    }

    /**
     * @param penID the penID to set
     */
    public void setPenID(Integer penID) {
        this.penID = penID;
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
     * @return the isResultCountDisplayed
     */
    public boolean isIsPenResultCountDisplayed() {
        return isPenResultCountDisplayed;
    }

    /**
     * @param isResultCountDisplayed the isResultCountDisplayed to set
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
     * @return the litterSearch
     */
    public LitterSearchDTO getLitterSearch() {
        return litterSearch;
    }

    /**
     * @param litterSearch the litterSearch to set
     */
    public void setLitterSearch(LitterSearchDTO litterSearch) {
        this.litterSearch = litterSearch;
    }

    /**
     * @return the litterInfo
     */
    public LitterListCommon getLitterInfo() {
        return litterInfo;
    }

    /**
     * @param litterInfo the litterInfo to set
     */
    public void setLitterInfo(LitterListCommon litterInfo) {
        this.litterInfo = litterInfo;
    }

    /**
     * @return the isLitterResultCountDisplayed
     */
    public boolean isIsLitterResultCountDisplayed() {
        return isLitterResultCountDisplayed;
    }

    /**
     * @param isLitterResultCountDisplayed the isLitterResultCountDisplayed to
     * set
     */
    public void setIsLitterResultCountDisplayed(boolean isLitterResultCountDisplayed) {
        this.isLitterResultCountDisplayed = isLitterResultCountDisplayed;
    }

    /**
     * @return the litterInfoDataModel
     */
    public ListDataModel getLitterInfoDataModel() {
        return litterInfoDataModel;
    }

    /**
     * @param litterInfoDataModel the litterInfoDataModel to set
     */
    public void setLitterInfoDataModel(ListDataModel litterInfoDataModel) {
        this.litterInfoDataModel = litterInfoDataModel;
    }

    /**
     * @return the litterSelectionETB
     */
    public ExtendedTableBean getLitterSelectionETB() {
        return litterSelectionETB;
    }

    /**
     * @param litterSelectionETB the litterSelectionETB to set
     */
    public void setLitterSelectionETB(ExtendedTableBean litterSelectionETB) {
        this.litterSelectionETB = litterSelectionETB;
    }

    /**
     * @return the litterID
     */
    public String getLitterID() {
        return litterID;
    }

    /**
     * @param litterID the litterID to set
     */
    public void setLitterID(String litterID) {
        this.litterID = litterID;
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
     * @return the tempLifeStatus
     */
    public boolean getTempLifeStatus() {
        return tempLifeStatus;
    }

    /**
     * @param tempLifeStatus the tempLifeStatus to set
     */
    public void setTempLifeStatus(boolean tempLifeStatus) {
        this.tempLifeStatus = tempLifeStatus;
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
     * @return the tempStrainStatus
     */
    public String getTempStrainStatus() {
        return tempStrainStatus;
    }

    /**
     * @param tempStrainStatus the tempStrainStatus to set
     */
    public void setTempStrainStatus(String tempStrainStatus) {
        this.tempStrainStatus = tempStrainStatus;
    }

    /**
     * @return the autoIncrement
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * @param autoIncrement the autoIncrement to set
     */
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the mouseAge
     */
    public double getMouseAge() {
        return mouseAge;
    }

    /**
     * @param mouseAge the mouseAge to set
     */
    public void setMouseAge(double mouseAge) {
        this.mouseAge = mouseAge;
    }

    /**
     * @return the mouseAge1
     */
    public int getMouseAge1() {
        return mouseAge1;
    }

    /**
     * @param mouseAge1 the mouseAge1 to set
     */
    public void setMouseAge1(int mouseAge1) {
        this.mouseAge1 = mouseAge1;
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
     * @return the displayCards
     */
    public ArrayList<SelectItem> getDisplayCards() {
        return displayCards;
    }

    /**
     * @param displayCards the displayCards to set
     */
    public void setDisplayCards(ArrayList<SelectItem> displayCards) {
        this.displayCards = displayCards;
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
     * @return the showReturnToSearchButton
     */
    public Boolean getShowReturnToSearchButton() {
        return showReturnToSearchButton;
    }

    /**
     * @param showReturnToSearchButton the showReturnToSearchButton to set
     */
    public void setShowReturnToSearchButton(Boolean showReturnToSearchButton) {
        this.showReturnToSearchButton = showReturnToSearchButton;
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
     * @return the phenotypeModel
     */
    public DualListModel getphenotypeModel() {
        return phenotypeModel;
    }

    /**
     * @param phenotypeModel the phenotypeModel to set
     */
    public void setphenotypeModel(DualListModel phenotypeModel) {
        this.phenotypeModel = phenotypeModel;
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

    /**
     * @return the mouseGenotype
     */
    public String getMouseGenotype() {
        return mouseGenotype;
    }

    /**
     * @param mouseGenotype the mouseGenotype to set
     */
    public void setMouseGenotype(String mouseGenotype) {
        this.mouseGenotype = mouseGenotype;
    }

    /**
     * @return the tempShowCOD
     */
    public boolean isTempShowCOD() {
        return tempShowCOD;
    }

    /**
     * @param tempShowCOD the tempShowCOD to set
     */
    public void setTempShowCOD(boolean tempShowCOD) {
        this.tempShowCOD = tempShowCOD;
    }

}
