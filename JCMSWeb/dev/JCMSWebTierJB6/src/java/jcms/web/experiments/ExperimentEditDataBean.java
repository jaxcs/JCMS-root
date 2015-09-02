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

package jcms.web.experiments;

import java.util.ArrayList;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

import jcms.integrationtier.dto.ExpDataDTO;
import jcms.integrationtier.dao.ExpDataDAO;
import jcms.integrationtier.dto.ExpDataDescriptorDTO;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.web.colonyManagement.MouseFunctionsBean;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.ExpDataTestTypeAndResultsDTO;
import jcms.integrationtier.dto.ExpDataTestTypeListDTO;

import jcms.middletier.dto.ListSupportDTO;

/**
 *
 * @author bas
 */
public class ExperimentEditDataBean extends ExperimentsBean {
    private boolean showEditData            = false;
    private boolean editing                 = false;
    
    //These are for the controls on the screen
    private ExpDataDTO selectedDataID;                 //The control on the screen is using this dto for the ExpData table
    private Integer expDataKey              = 0;       //Used to keep track of the key for the ExpData record
    private String mouseID                  = "";
    private ExpDataDescriptorDTO expDataDescriptorDTO; //Note: the control on the screen is using the DTO
    private String currentDataOwner          = ""; 
    private OwnerEntity ownerEntity;                   //Note: the control on the screen is using ownerEntity
    private Date expDate                    = new Date();
    private String age                      = "";
    private boolean abnormalData            = false;
    
    private boolean ageDetermination        = false;
    private String ageIn                    = "days";
    private boolean changeLifeStatus        = false;
    private String newLifeStatus            = "";
    private boolean autoIncrement           = false; //Desirement, not implemented
    
//    Mouse fields
    private MouseFunctionsBean mouseFunctions = new MouseFunctionsBean();
    private MouseEntity mouse               = null;
    private String strain                   = "";
    private String generation               = "";
    private String sex                      = "";
    private SimpleDateFormat userFormat;  //used to format the date string shown on screen
    private String birthDate                = "";
    private String mouseAge                 = "";
    private String lifeStatus               = "";
    private String mouseOwner               = "";
    private MouseUseDAO useDAO = new MouseUseDAO(); //used to get genotype
    private String genotype                 = "";
    
//  Rows for the data
    private ExpDataTestTypeListDTO resultsListDTO = new ExpDataTestTypeListDTO();
    
    private MouseEntity mouseEntity;
    private LifeStatusEntity lifeStatusEntity;
    private ExpDataDAO expDataDAO = new ExpDataDAO();
    private ListSupportDTO listSupportDTO = new ListSupportDTO();
       
    public String addDataAction() {
        //Here need to initalize the screen for the add data version of the form
        setShowEditData (true);
        setEditing (false);
        try {
            this.initialize();
        } catch (Exception e ) {
            // Error processing add data action.  Halt navigation and display
            // appropriate message to user.
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Data Action ERROR:" + e.getMessage(), null));
            return null;
        }
        
        //Indicate what xhtml form to open by returning its name.
        return "experimentEditData";
    }
    
    public String editDataAction() throws Exception {
        //Here need to initalize the screen for the edit data version of the form
        setShowEditData (true);
        setEditing (true);
        //Redmine 747: When the edit version of the form opens, set change life status to no.
        setChangeLifeStatus(false);
        this.initialize();
        //Indicate what xhtml form to open by returning its name.
        return "experimentEditData";
    }
    
    private void initialize() throws Exception {
        //Set the blank form up
        setExpDataDescriptorDTO(new ExpDataDescriptorDTO());
        setMouseEntity(new MouseEntity());
        ownerEntity = new OwnerEntity();
        setLifeStatusEntity(new LifeStatusEntity());
        setSelectedDataID(new ExpDataDTO());
        setResultsListDTO(new ExpDataTestTypeListDTO());
        
        //First time through the key is always zero; 
        expDataKey = 0;
        this.initialize(expDataKey); 
    }
    
    private void initialize(Integer expDataKey) throws Exception {
        //Once the form has been set up, bring into it actual values based on the expDataKey
        //The key will be zero if we are adding new
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        setListSupportDTO(new ListSupportDTO(ownerLst));
        userFormat = new SimpleDateFormat(this.getDate_format()); 
            
        if (expDataKey != null && expDataKey > 0) {           
            //We are editing so look up the test type for this exp data record and set it on the screen
            String testTypeKey = getSelectedDataID().getTestTypeKey();
            int iTestTypeKey = Integer.parseInt(testTypeKey);
            // step through elements in the test type list and when match found, assign it
            for (ExpDataDescriptorDTO ttDTO : expDataDAO.getAllTestTypes()) {
                if (ttDTO.getExpDataDescriptor_key() == iTestTypeKey) {
                   this.expDataDescriptorDTO = ttDTO;
                   break;
              }
            }
            //initialize the test type list and data results on the screen
            this.setResultsListDTO(expDataDAO.getTestTypeAndExpDataResults(iTestTypeKey, expDataKey.toString()));
            
            //Set the mouseID on the screen
            String mouseKeyString = getSelectedDataID().getSpecimenKey();
            Integer mouseKey = Integer.valueOf(mouseKeyString);
            this.mouseEntity = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(mouseKey));
            mouseID = mouseEntity.getId();
            setMouse(this.getMouseFunctions().findMouse(mouseID));
            displayMouseInformation();
            
            //Set the ExpData record values on the screen
            age = getSelectedDataID().getAge();
            this.ageDetermination = false; //sets to manual entry
            this.ageIn = "days";
            
            //Owner needs to be set on the screen using the ownerEntity
            setCurrentDataOwner(getSelectedDataID().getOwner());
            if (getCurrentDataOwner() != null && !currentDataOwner.equals("")) {
                // step through elements from cv and when match found, assign it
                for (OwnerEntity entity : listSupportDTO.getOwner()) {
                    if (entity.getOwner().equals(getCurrentDataOwner())) {
                        this.ownerEntity = entity;
                        break;
                    }
                }
            }
            String expDateAsString = getSelectedDataID().getExpDate(); //returns a string in MySQL date format (yyyy-mm-dd 00.00.00.0)

            DateFormat dfMySQL = new SimpleDateFormat("yyyy-MM-dd");
            if(expDateAsString == null || expDateAsString.equals("") ) {
                this.expDate = null;
            } else {
                Date d1 = dfMySQL.parse(expDateAsString);
                this.expDate = d1;
            }
            this.abnormalData = getSelectedDataID().getAbnormalData();
        } else {
            // set defaults
            //fill owner control and select one
            //display the current owner on screen as the top one in the list
            this.initializeOwner();
            this.expDataKey = 0;
            this.getSelectedDataID().setExpDataKey("0");
            this.expDataDescriptorDTO.setExpDataDescriptor_key(0);
            this.getResultsListDTO().setTestTypeKey("0");
            this.getResultsListDTO().setExpDataKey("0");
            //Redmine 747: reset the life status to no when changing between add and edit modes
            setChangeLifeStatus(false);
        }
    }

    public ArrayList<ExpDataDTO> completeDataID(String query) {
        //this method fills the list of all data IDs on the screen
        //First it has to be filtered to show only those with a data owner that the current user may edit
        String ownerList = "";
        ArrayList<OwnerEntity> myOwners = new ArrayList<OwnerEntity>();
        myOwners = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        for (int i = 0; i < myOwners.size(); i++) {
            // if last item, then no comma
            if (i == myOwners.size() - 1) {
                ownerList = ownerList + " '" + myOwners.get(i).getOwner() + "'";
            } else {
                ownerList = ownerList + " '" + myOwners.get(i).getOwner() + "',";
            }
        }
        //Now use the query string to filter the list 
        ExpDataDAO dao = new ExpDataDAO();
        ArrayList<ExpDataDTO> dataIDs = dao.getDataIDsForOwner(ownerList);
        ArrayList<ExpDataDTO> filteredDataIDs = new ArrayList<ExpDataDTO>();
        for (int i = 0; i < dataIDs.size(); i++) {
            ExpDataDTO dto = dataIDs.get(i);
            if(dto.getDataID().toLowerCase().startsWith(query)) {
                filteredDataIDs.add(dto);
            }
        }
        if (filteredDataIDs.isEmpty() ){
            System.out.println("Selecting a data ID returned no choices");
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "The Data ID entered is not in the database.", null));
        }
        return filteredDataIDs;
  
    }
    
    public ArrayList<ExpDataDescriptorDTO> completeTestType(String query) {
        //this method fills the list of all test types on the screen
        //query is passed from the auto-complete on the form
        ExpDataDAO dao = new ExpDataDAO();
        ArrayList<ExpDataDescriptorDTO> testTypes = dao.getAllTestTypes();
        ArrayList<ExpDataDescriptorDTO> filteredTestTypes = new ArrayList<ExpDataDescriptorDTO>();
        for (int i = 0; i < testTypes.size(); i++) {
            ExpDataDescriptorDTO dto = testTypes.get(i);
            if(dto.getTestType().toLowerCase().startsWith(query)) {
                filteredTestTypes.add(dto);
            }
        }
        if(filteredTestTypes.isEmpty()) {
            System.out.println("Selecting a test type returned no choices");
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "The test type entered is not in the database.", null));
        }    
        return filteredTestTypes;
    }
    
    public void initializeDataIDAction(SelectEvent event) throws Exception {
        String msg = "";
        try {
            ExpDataDTO dto = (ExpDataDTO) event.getObject();

            if (dto != null) {
                expDataKey =  Integer.parseInt(dto.getExpDataKey());
                this.initialize(expDataKey);
            } else {
                System.out.println("Selecting a data ID returned null");
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "The Data ID that was selected returned nothing, select a different one.", null));
            }
        } catch (Exception se) {
            msg = se.getMessage();
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit Data ERROR selecting a data ID" + msg, null));
            this.getLogger().logError(this.formatLogMessage(msg, "initializeDataIDAction"));
        }
    }
    
    public String insertExpDataAction(ActionEvent event) throws Exception {
        String msg = "";

        try {
            if (validateExpData()) {
                //Everything is okay
                updateSelectedDataIDDTO();
                this.getSelectedDataID().setVersion("0");
                Integer TTkey = this.expDataDescriptorDTO.getExpDataDescriptor_key();
                String TTkeyString = TTkey.toString();
                this.getSelectedDataID().setTestTypeKey(TTkeyString);
                Integer mouseKey = mouse.getKey();
                this.getSelectedDataID().setSpecimenKey(mouseKey.toString());
                this.getSelectedDataID().setSpecimen_type("mouse");
                //Need to increment the Data ID
                String dataID;
                Integer temp;
                ExpDataDAO dao = new ExpDataDAO();
                dataID = dao.findMaxDataID();
                if (dataID.equals("0")) {
                    dataID = "1";
                } else { 
                    temp = (Integer.parseInt(dataID)) + 1;
                    dataID = Integer.toString(temp);
                }
                this.getSelectedDataID().setDataID(dataID);
                String expKey;
                expKey = dao.findMaxExpDataKey();
                if (expKey.equals("0")) {
                    expKey = "1";
                } else { 
                    temp = (Integer.parseInt(expKey)) + 1;
                    expKey = Integer.toString(temp);
                }
                this.getSelectedDataID().setExpDataKey(expKey);
                //Insert the ExpData record.
                Integer success;
                success = insertExpData();
                if (success == 1) {
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Adding Experiment Data completed " + msg, null));                    
                    //if the user wants the mouse life status changed, update that                    
                    if (this.changeLifeStatus) {
                        changeMouseLifeStatus();
                    }
                } else {
                    msg = "Not completed, An error occurred trying to add.";
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adding Experiment Data: " + msg, null));
                    return "experimentEditData";
                }  
            } else {
                msg = "Not completed, Data not valid";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adding Experiment Data: " + msg, null));
                return "experimentEditData";
            } 
        } catch (Exception e) {
            msg = e.getMessage();
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save Insert Action error: " + msg, null));
            this.getLogger().logError(this.formatLogMessage(msg, "saveInsertAction"));
            return "experimentEditData";
        }
        return "experimentEditData";
    }
    
    private void changeMouseLifeStatus() throws Exception {
        String msg = "";
        ExpDataDAO dao = new ExpDataDAO();
        if (this.getSelectedDataID().getExpDate() == null || this.getSelectedDataID().getExpDate().trim().equals("")) {
            //check if the new life status is an exit status, if so, it MUST have a date
            lifeStatusEntity.setLifeStatus(newLifeStatus);
            if (lifeStatusEntity.getExitStatus()) {
                msg = "Unable to change mouse life status. A data collection date is needed. It is used to set the mouse exit date.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
                return;
            } 
        } 
        lifeStatusEntity.setLifeStatus(newLifeStatus);
        if (dao.changeMouseLifeStatus(this.mouseID, lifeStatusEntity, this.getSelectedDataID().getExpDate()) != 0) {
            msg = "Unable to change mouse life status";
        } else {
            msg = "Mouse life status successfully changed";
            //Update the mouse information for the screen
            setMouse(this.getMouseFunctions().findMouse(mouseID));
            displayMouseInformation();
        }
        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Adding Experiment Data: " + msg, null));
    
    }
    
    public String updateExpDataAction(ActionEvent event) throws Exception {
        String msg = "";
        
        try {
            if (this.getSelectedDataID() == null) {
                msg = "You must choose a Data ID.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experiment Data: " + msg, null));
                return "experimentEditData";
            }
            if (this.expDataKey.equals(0)) {
                msg = "You must choose a Data ID, 0 is not valid.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experiment Data: " + msg, null));
                return "experimentEditData";
            }
            if (Integer.parseInt(this.getSelectedDataID().getExpDataKey()) != this.expDataKey) {
                msg = "You must choose a Data ID.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experiment Data: " + msg, null));
                System.out.println("There is a mis-match between selectedDataID and expDataKey - check code. ");
                return "experimentEditData";
            }

            if (validateExpData()) {
                //Everything is okay
                updateSelectedDataIDDTO();
                //Update the ExpData record.
                Integer success;
                success = updateExpData();
                if (success == 1) {
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Editing Experiment Data completed " + msg, null));
                    //if the user wants the mouse life status changed, update that
                    if (this.changeLifeStatus) {
                        changeMouseLifeStatus();
                        // Redmine 747: Reset the change life status control to "no" when editing
                        this.setChangeLifeStatus(false);
                    }
                } else { 
                    //Something happened trying to do the update
                    msg = "Not completed.";
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experiment Data:  " + msg, null));
                }
            } else {
                msg = "Not completed, Data not valid";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Experiment Data:  " + msg, null));
                //causes page to redisplay
                return "experimentEditData";
            }
        return "experimentEditData";

        } catch (Exception se) {
            msg = se.getMessage();
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save Update Action error. " + msg, null));
            this.getLogger().logError(this.formatLogMessage(msg, "saveUpdateAction"));
            return "experimentEditData";
        }
    }
    
    private Integer insertExpData() throws Exception {
        Integer success = 0;
        try {
            ExpDataDAO dao = new ExpDataDAO();
            Integer ok = dao.insertExpData(getSelectedDataID(), getResultsListDTO()); 
            if (ok != 0) {
                success = 1;
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Data is added. Data ID =" + ok, null));  
            } else {
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adding data failed. ", null));
            }
        } catch (SQLException e) {
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adding Experimental Data failed. "+ e.getMessage(), null));    
        }
        return success;
    }
    
    private Integer updateExpData() {
        Integer success = 0;
        try {
            //The new values we want to save for d1, d2, d3... are in this.resultsListDTO 
            Integer ok = new ExpDataDAO().updateExpData(getSelectedDataID(), getResultsListDTO()); 
            if (ok.equals(1)) {
                success = 1;
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Data is updated.", null));  
            } else {
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit failed. ", null));
            }
        } catch (SQLException e) {
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit of the Experimental Data failed. "+ e, null));    
        }
        return success;
    }
            
    private boolean validateExpData() throws Exception{
        boolean success = true;
        String msg = "";
        try {
            //Mouse ID is required
            if (!this.validateMouseID()) {
                    success = false;
                }
            //Test Type is required
            if (!this.validateTestType()) {
                    success = false;
                }
            //Owner is required
            if (!this.validateOwner()) {
                    success = false;
                } 
            //Age must be blank or a number
            if (!this.validateAge()) {
                    success = false;
            }
            //Mouse new Life status is required if user is changing the life status
            if (this.changeLifeStatus){
                if (!this.validateLifeStatus()) {
                    success = false;
                }
            }
            // Validate the D fields
            if (this.getResultsListDTO().getTtWithDataResult().size() > 0) {
                if (!this.validateDFields ()) {
                    success = false;
                }
            }

        } catch (Exception se) {
                success = false;
                msg = se.getMessage();
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error while validating: " + msg, null));
                this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            }
        return success;
    }
    
     public boolean validateMouseID() {
        boolean flag = true;
        String errMsg = null;
        try {
            // Check for null or empty String.
            
            String s = this.mouseID;

            if ((s == null) || (s.isEmpty())) {
                flag = false;
                errMsg = "Mouse ID is missing. Please enter a Mouse ID.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mouse ID is required." + errMsg, null));
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
                
            } else { 
                //Make sure mouse ID exists
                MouseEntity mEntity = (MouseEntity) getRepositoryService().
                        baseFindByMoueID(new MouseEntity(), s);

                if (mEntity == null) {
                    flag = false;
                    errMsg = "Mouse ID entered does not exist. ";
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mouse ID is required. " + errMsg, null));
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
                }
            }
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error validating mouse ID: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception validating mouse ID: ",
                    e.getMessage()));
        }
        return flag;
    }
    
    public boolean validateTestType() {
        boolean flag = true;
        String errMsg = null;
        String s = "";
        try {
            // Check for null 
            if (this.expDataDescriptorDTO == null) {
                flag = false;
                errMsg = "Test type is missing. Please enter a test type.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Test type is required." + errMsg, null));
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
            } else {
                s = this.expDataDescriptorDTO.getTestType();
                if (s.isEmpty()) {
                    flag = false;
                    errMsg = "Test type is missing. ";
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Test type is required." + errMsg, null));
                    this.getLogger().logWarn(this.formatLogMessage("Validation exception ",
                            errMsg));
                } 
            }              
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error validating test type: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception validating test type: ",
                    e.getMessage()));
        }
        return flag;
    }
    
    public boolean validateOwner() {
        boolean flag = true;
        String errMsg = null;
        String s = "";
        try {
            // Check for null or empty String. No need to validate otherwise, the control only contains valid choices.
            if (ownerEntity == null ){
                flag = false;
                errMsg = "The Owner/Workgroup is missing. Please enter an owner.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner/Workgroup is required." + errMsg, null));
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
            } else {
                s = this.ownerEntity.getOwner();
                if (s.isEmpty()) {
                    flag = false;
                    errMsg = "The Owner/Workgroup is missing. Please enter an owner.";
                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner/Workgroup is required." + errMsg, null));
                    this.getLogger().logWarn(this.formatLogMessage("Validation exception ",
                            errMsg));
                }
            }
            // Warn if the mouse owner and data owner are different 
            if (flag == true){
                MouseEntity mEntity = (MouseEntity) getRepositoryService().
                        baseFindByMoueID(new MouseEntity(), this.mouseID);
                if (!(mEntity == null )) {
                    if (!(s.equalsIgnoreCase(mEntity.getOwner())) ){
                        errMsg = "The mouse belongs to a different Owner/Workgroup. ";
                        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning: " + errMsg, null));
                        this.getLogger().logWarn(this.formatLogMessage("Validation warning ",
                            errMsg));
                    }
                }
            }
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error validating owner. " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception in validate owner ",
                    e.getMessage()));
        }
        return flag;
    }
    
    public boolean validateLifeStatus() {
        boolean flag = true;
        String errMsg = null;
        try {
            // Check for null or empty String. No need to validate otherwise, the control only contains valid choices.
            this.newLifeStatus = lifeStatusEntity.getLifeStatus();
            String s = this.newLifeStatus;

            if ((s == null) || (s.isEmpty())) {
                flag = false;
                errMsg = "The life status is missing. Please enter a life status.";
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Life status is required." + errMsg, null));
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        errMsg));
            }
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error validating new life status: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception validating life status: ",
                    e.getMessage()));
        }
        return flag;
    }
    
    public boolean validateAge() {
        boolean flag = true;
        String errMsg = null;
        try {
            //Age must be a number or blank
            if (!(this.age == null || this.age.trim().equals(""))) {
                Float.parseFloat(this.age);
            }
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Age must be a number or blank: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception validating age: ",
                    e.getMessage()));
        }
        return flag;
    }
    
    public boolean validateDFields () {
        boolean flag = true;
        boolean flag2 = true;
        boolean dateFlag = false;
        boolean fileFlag = false;
        String errMsg = "";
        try {
            for (ExpDataTestTypeAndResultsDTO dataResults : this.getResultsListDTO().getTtWithDataResult()) {
                if (dataResults.getCaption() == null || dataResults.getCaption().trim().equals("")) {
                    //There is no data if there is no caption, skip
                    continue;
                } 
                if(dataResults.getD_Value() == null || dataResults.getD_Value().trim().equals("")) {    
                    if (dataResults.getRequired()) {
                        //Watch out, it could contain a null value!!
                        //D value may not be blank
                        flag = false;
                        errMsg = dataResults.getCaption() + ": Data value is required.";
                        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",errMsg));
                        continue; //when no value there is nothing more to check
                    } else {
                        continue; //jump out if no data is entered
                    }
                }
                if (dataResults.getD_Value().length() > 64){
                    flag = false;
                        errMsg = dataResults.getCaption() + ": Too long, data value may be up to 64 characters.";
                        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        this.getLogger().logWarn(this.formatLogMessage("Validation ",errMsg));
                        continue; //jump out if there is an error
                }                
                //We now know we have a value to validate, what the validation is depends on the test type's data format                
                if (dataResults.getDataFormat().equals("date")) {
                    //We are unable to validate all the different date formats that MS Access allows when using that
                    //interface. Instead, set a flag here and display one info message.
                    dateFlag = true; 
                } else {
                    if (dataResults.getDataFormat().equals("file")) {
                        //We are not supporting the document feature (file format) in the MS Access interface.
                        //Set a flag here and display one info message.
                        fileFlag = true; 
                    } else {
                        int integerData;
                        float floatIntegerData;
                        if (dataResults.getDataFormat().equals("int")) { 
                            try {
                                integerData = Integer.parseInt(dataResults.getD_Value()); 
                                floatIntegerData = Float.parseFloat(dataResults.getD_Value()); //we'll use this value in the min/max check
                            } catch (NumberFormatException e) {
                                flag = false;
                                errMsg = dataResults.getCaption() + ": " + dataResults.getD_Value() + " is not an integer.";
                                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                                this.getLogger().logWarn(this.formatLogMessage("Validation ",errMsg));
                                continue; //when in error there is nothing more to check
                            }
                            //If passes do min/max check
                            if(!(dataResults.getMinValue() == null || dataResults.getMinValue().equals(""))) {
                                flag = minCheck(floatIntegerData, Float.parseFloat(dataResults.getMinValue()), dataResults.getCaption());
                            }
                            if(!(dataResults.getMaxValue() == null || dataResults.getMaxValue().equals(""))) {
                                flag2 = maxCheck(floatIntegerData, Float.parseFloat(dataResults.getMaxValue()), dataResults.getCaption());
                            } 
                        } else {
                            if (dataResults.getDataFormat().equals("dec")) {
                                Float floatData;
                                try { 
                                    floatData = Float.parseFloat(dataResults.getD_Value()); 
                                } catch (NumberFormatException e) {  
                                    flag = false;
                                    errMsg = dataResults.getCaption() + ": " + dataResults.getD_Value() + " is not a number.";
                                    this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                                    this.getLogger().logWarn(this.formatLogMessage("Validation ",errMsg));
                                    continue; //when in error there is nothing more to check
                                }    
                                //if passes do min/max check
                                if(!(dataResults.getMinValue() == null || dataResults.getMinValue().trim().equals(""))) {
                                    flag = minCheck(floatData, Float.parseFloat(dataResults.getMinValue()), dataResults.getCaption());
                                }
                                 
                                if(!(dataResults.getMaxValue() == null || dataResults.getMaxValue().trim().equals(""))) {
                                    flag2 = maxCheck(floatData, Float.parseFloat(dataResults.getMaxValue()), dataResults.getCaption());
                                }
                            }
                        }
                    }
                }
            }   
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save error: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception validating data results fields: ",
                    e.getMessage()));
        }
        if (dateFlag) {
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning: fields with a date format are not checked to verify they contain a date.", null));
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Warning: Fields with a date format are not checked to verify they contain a date."));
        }
        if (fileFlag) {
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning: the file format is not currently supported in JCMS Web.", null));
        }
        if (!flag2) {
            flag = flag2; //This catches if there was ever a max check error.
        }
        return flag;
    }
     
    public void updateSelectedDataIDDTO() {
        //Now set the values in the selectedDataID dto so any changes are there to save.
        this.getSelectedDataID().setOwner(this.ownerEntity.getOwner());
        //convert experiment date to string before saving
        String expDateString = "";
        if (this.expDate != null) {
            expDateString = new SimpleDateFormat("yyyy-MM-dd").format(this.expDate);
        }
        this.getSelectedDataID().setExpDate(expDateString);
        //convert age to days before saving
        String ageToSave = this.age;
        if (!(this.age == null || this.age.trim().equals(""))){
            if (!this.ageIn.equals("days")) {
                if(this.ageIn.equals("weeks")) {
                    ageToSave = convertWeeksToDays(this.age);
                } else {
                    ageToSave = convertMonthsToDays(this.age);
                }
            }
        }
        this.getSelectedDataID().setAge(ageToSave);
        this.getSelectedDataID().setAbnormalData(this.abnormalData);
    }
    
    public String clearAllAction(ActionEvent event) {   
        clearAll();
        //redisplay the page
        return "experimentEditData";
    }
    
    private void clearAll (){
        try {
            if (this.getSelectedDataID() != null) {
                this.getSelectedDataID().setExpDataKey("0");
                this.getSelectedDataID().setDataID("0");
            }
            expDataKey = 0;
            mouseID = "";
            clearMouseInformation();
            if (this.expDataDescriptorDTO != null) {
                this.expDataDescriptorDTO.setExpDataDescriptor_key(0);
                this.expDataDescriptorDTO.setTestType("");
            }
            currentDataOwner = "";
            if (this.ownerEntity != null) {
                ownerEntity.setOwner("");
                ownerEntity.setOwnerKey(0);
            }
            expDate = null;
            age = "";
            abnormalData = false;
            setResultsListDTO(null);
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Screen has been cleared", null));
        } 
        catch (Exception e) {
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clear Screen error. " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception clearing the screen: ",
                    e.getMessage()));
        }
    }
    
    public String clearResultsAction(ActionEvent event) {   
        
        clearResults();
        return "experimentEditData";
    }
    
    private void clearResults() {
        //remove all results values from the screen
        if (this.resultsListDTO != null){
            for (ExpDataTestTypeAndResultsDTO dataResults : this.getResultsListDTO().getTtWithDataResult()) {
                dataResults.setD_Value("");
            }
        }
        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Results cleared", null));
    }
    
    // initialize the owner to logged in owner
    public void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        if (ownerLst.size() > 0) {
            this.setOwnerEntity(ownerLst.get(0));
        }
    }
    
    public void selectDataButtonAction() {
        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Button not implemented; please type data ID", null));
    }
    
    public void selectMouseButtonAction() {
        this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Button not implemented; please type mouse ID", null));
    }
    
    public void testTypeChangeListener(SelectEvent event) {
        try {
            ExpDataDescriptorDTO dto = (ExpDataDescriptorDTO) event.getObject();
            if (dto.getExpDataDescriptor_key() == 0) {
                //remove test type and results fields
                this.setResultsListDTO(null);
            } else {
                //Now need to change the data results entry section on the form to display this test type
                this.setResultsListDTO(expDataDAO.getTestTypeAndExpDataResults(dto.getExpDataDescriptor_key(), ""));
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went wrong gathering the test type info: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void mouseIDChangeListener(ValueChangeEvent event) {
        try{
            // check if a mouse is selected
            if (!this.mouseID.equals("")) {
                setMouse(this.getMouseFunctions().findMouse(mouseID));
            }
            if(this.mouseID.equals("") || getMouse() == null) {
                this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mouse with ID " + mouseID + " could not be found.", null));
                // Clear mouse info on screen
                clearMouseInformation();
            } else {
                //Here must fill mouse info on screen
                displayMouseInformation();
            }
            calculateExperimentAge();
    }
        catch(Exception e){
            this.addToMessageQueue("Something went wrong determining the mouse ID info: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void clearMouseInformation() {
        mouseID = "";
        strain = "";    
        generation = "";
        sex = "";
        birthDate = "";
        mouseAge = "";
        lifeStatus = "";
        mouseOwner = "";
        genotype = "";
    }
    
    private void displayMouseInformation(){
        
        if (getMouse() != null & mouse.getMouseKey() != 0 ) { //Verify the mouse has been set
            strain = mouse.getStrainKey().getStrainName();
            generation = mouse.getGeneration();
            sex = mouse.getSex();
            //create the date with the format in the setup variables
            birthDate = userFormat.format(mouse.getBirthDate());
            Calendar DOB = Calendar.getInstance();
            DOB.setTime(mouse.getBirthDate());
            if (mouse.getExitDate() == null) {
                mouseAge = calculateAgeInDays(DOB, Calendar.getInstance());
            } else {
                Calendar exitDate = Calendar.getInstance();
                exitDate.setTime(mouse.getExitDate());
                mouseAge = calculateAgeInDays(DOB, exitDate);
            }
            lifeStatus = mouse.getLifeStatus();
            mouseOwner = mouse.getOwner();
            genotype = getUseDAO().getMouseGenotypeByMouseKey(mouse.getMouseKey().toString());
        }
    }
    
    public void ageDeterminationChangeListener(ValueChangeEvent event) {
        if (ageDetermination) {
            //true means to autocalculate
            calculateExperimentAge();
        } else {
            //false means manual input, clear the field
            age = "";           
        }
    }
    
    public void ageInChangeListener(ValueChangeEvent event) {
            calculateExperimentAge();
    }
    
    public void expDateSelectListener(SelectEvent event) {
            expDate = (Date) event.getObject();   
            calculateExperimentAge();
    }
    
    public void expDateChangeListener(AjaxBehaviorEvent event) {
            expDateManualChange();
    }
    
    public void expDateManualChange() {
        if (expDate == null) {
            age = "";
        } else {
            calculateExperimentAge(); 
        }
    }
    
    private void calculateExperimentAge(){
        //Reset the age at which the experiment was performed
        age = "";
        try { 
            if ( ageDetermination) {
                if ( !birthDate.equals("")) {
                    if (expDate != null)  {
                        if (!expDate.toString().equals("")) {
                            //autocalculate
                            Calendar DOB = Calendar.getInstance();

                            Date mouseBirthDate = new SimpleDateFormat(this.getDate_format()).parse(birthDate);
                            DOB.setTime(mouseBirthDate);
                            Calendar dateOfExp = Calendar.getInstance();
                            dateOfExp.setTime(expDate);

                            String numberOfDays = calculateAgeInDays(DOB, dateOfExp);
                            if(ageIn.equals("days")){
                                age = numberOfDays;
                            }
                            else {
                                if(ageIn.equals("weeks")){
                                    //convert from days to weeks
                                    age = new Float(new Float(numberOfDays)/7).toString();
                                } else {
                                    //convert from days to months
                                    age = new Float(new Float(numberOfDays)/30.4375).toString();
                                }
                            }
                        }
                    } 
                }
            }
        }
        catch(Exception e){
            this.getFacesContext().addMessage("expDataMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error calculating age: " + e.getMessage(), null));
            System.out.println("Error calculating the experiment age: " + e.getMessage());
        }
    }
    
    private String calculateAgeInDays(Calendar startdate, Calendar endDate){
        int days = 0;
        while(startdate.before(endDate)){
            startdate.add(Calendar.DAY_OF_MONTH, 1);
            days++;
        }
        
        return new Integer(days).toString();
    }
    
    private Date convertStringToShortDate(String s) {
        Date convertedDate = null;
        if (s != null && s.length() > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
            try { convertedDate = formatter.parse(s); }
            catch (Exception e) {  } 
        }
        return convertedDate ;
    }
       
    /**
     * @return the showEditData
     */
    public boolean isShowEditData() {
        return showEditData;
    }

    /**
     * @param showEditData the showEditData to set
     */
    public void setShowEditData(boolean showEditData) {
        this.showEditData = showEditData;
    }

    /**
     * @return the mouseID
     */
    public String getMouseID() {
        return mouseID;
    }

    /**
     * @param mouseID the mouseID to set
     */
    public void setMouseID(String mouseID) {
        this.mouseID = mouseID;
    }

    /**
     * @return the expDataKey
     */
    public Integer getExpDataKey() {
        return expDataKey;
    }

    /**
     * @param expDataKey the expDataKey to set
     */
    public void setExpDataKey(Integer expDataKey) {
        this.expDataKey = expDataKey;
    }

    /**
     * @return the strain
     */
    public String getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(String strain) {
        this.strain = strain;
    }

    /**
     * @return the generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the lifeStatus
     */
    public String getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the mouseOwner
     */
    public String getMouseOwner() {
        return mouseOwner;
    }

    /**
     * @param mouseOwner the mouseOwner to set
     */
    public void setMouseOwner(String mouseOwner) {
        this.mouseOwner = mouseOwner;
    }

    /**
     * @return the genotype
     */
    public String getGenotype() {
        return genotype;
    }

    /**
     * @param genotype the genotype to set
     */
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    /**
     * @return the ageDetermination
     */
    public Boolean getAgeDetermination() {
        return ageDetermination;
    }

    /**
     * @param ageDetermination the ageDetermination to set
     */
    public void setAgeDetermination(Boolean ageDetermination) {
        this.ageDetermination = ageDetermination;
    }

    /**
     * @return the ageIn
     */
    public String getAgeIn() {
        return ageIn;
    }

    /**
     * @param ageIn the ageIn to set
     */
    public void setAgeIn(String ageIn) {
        this.ageIn = ageIn;
    }

    /**
     * @return the abnormalData
     */
    public Boolean getAbnormalData() {
        return abnormalData;
    }

    /**
     * @param abnormalData the abnormalData to set
     */
    public void setAbnormalData(Boolean abnormalData) {
        this.abnormalData = abnormalData;
    }

    /**
     * @return the changeLifeStatus
     */
    public Boolean getChangeLifeStatus() {
        return changeLifeStatus;
    }

    /**
     * @param changeLifeStatus the changeLifeStatus to set
     */
    public void setChangeLifeStatus(Boolean changeLifeStatus) {
        this.changeLifeStatus = changeLifeStatus;
    }

    /**
     * @return the newLifeStatus
     */
    public String getNewLifeStatus() {
        return newLifeStatus;
    }

    /**
     * @param newLifeStatus the newLifeStatus to set
     */
    public void setNewLifeStatus(String newLifeStatus) {
        this.newLifeStatus = newLifeStatus;
    }

    /**
     * @return the autoIncrement
     */
    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    /**
     * @param autoIncrement the autoIncrement to set
     */
    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the expDate
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * @param expDate the expDate to set
     */
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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
     * @return the mouse
     */
    public MouseEntity getMouse() {
        return mouse;
    }

    /**
     * @param mouse the mouse to set
     */
    public void setMouse(MouseEntity mouse) {
        this.mouse = mouse;
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
     * @return the mouseAge
     */
    public String getMouseAge() {
        return mouseAge;
    }

    /**
     * @param mouseAge the mouseAge to set
     */
    public void setMouseAge(String mouseAge) {
        this.mouseAge = mouseAge;
    }

    /**
     * @return the useDAO
     */
    public MouseUseDAO getUseDAO() {
        return useDAO;
    }

    /**
     * @param useDAO the useDAO to set
     */
    public void setUseDAO(MouseUseDAO useDAO) {
        this.useDAO = useDAO;
    }

    /**
     * @return the selectedDataID
     */
    public ExpDataDTO getSelectedDataID() {
        return selectedDataID;
    }

    /**
     * @param selectedDataID the selectedDataID to set
     */
    public void setSelectedDataID(ExpDataDTO selectedDataID) {
        this.selectedDataID = selectedDataID;
    }

    /**
     * @return the expDataDAO
     */
    public ExpDataDAO getExpDataDAO() {
        return expDataDAO;
    }

    /**
     * @param expDataDAO the expDataDAO to set
     */
    public void setExpDataDAO(ExpDataDAO expDataDAO) {
        this.expDataDAO = expDataDAO;
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
     * @return the currentDataOwner
     */
    public String getCurrentDataOwner() {
        return currentDataOwner;
    }

    /**
     * @param currentDataOwner the currentDataOwner to set
     */
    public void setCurrentDataOwner(String currentDataOwner) {
        this.currentDataOwner = currentDataOwner;
    }

    /**
     * @return the expDataDescriptorDTO
     */
    public ExpDataDescriptorDTO getExpDataDescriptorDTO() {
        return expDataDescriptorDTO;
    }

    /**
     * @param expDataDescriptorDTO the expDataDescriptorDTO to set
     */
    public void setExpDataDescriptorDTO(ExpDataDescriptorDTO expDataDescriptorDTO) {
        this.expDataDescriptorDTO = expDataDescriptorDTO;
    }

    /**
     * @return the resultsListDTO
     */
    public ExpDataTestTypeListDTO getResultsListDTO() {
        return resultsListDTO;
    }

    /**
     * @param resultsListDTO the resultsListDTO to set
     */
    public void setResultsListDTO(ExpDataTestTypeListDTO resultsListDTO) {
        this.resultsListDTO = resultsListDTO;
    }

}