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
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.ContainerHistoryEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author rkavitha
 */
public class PenBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0000231L;
    
    private ContainerEntity penEntity;
    
    private CvContainerStatusEntity status;
    private RoomEntity room;
    private Date actionDate;
    
    private boolean nextPen;
    private boolean nextPenName;
    private String  useNextID = "false";
    
    private boolean penNameFlag;
    private boolean penCommentsFlag;
    private boolean penHealthLevelFlag;
    
    private MouseFunctionsBean mouseFunctions;
    
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    ListSupportDTO listSupportDTO;
    
    public PenBean() {
        System.out.println("PenBean constructor called");
        this.initialize();
    }
    
    private void initialize() {
        penEntity = new ContainerEntity();
        status = new CvContainerStatusEntity();
        room = new RoomEntity();
        actionDate = new Date();
        //nextPen = true;
        nextPenName = true;
        
        mouseFunctions = new MouseFunctionsBean();
        
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        listSupportDTO = new ListSupportDTO(ownerLst);
                        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_CREATE_PEN_INCREMENT");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            if (dbEntity.getMTSValue().equalsIgnoreCase("true")) {
                this.nextPen = true;
                this.useNextID = "true";
            } 
            else {
                this.nextPen = false;
                this.useNextID = "false";
            }
        }
        
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_USING_PEN_NAMES");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            if (dbEntity.getMTSValue().equalsIgnoreCase("true")) {
                this.penNameFlag = true;
            } else {
                this.penNameFlag = false;
            }
        }
        
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_USING_PEN_COMMENTS");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            if (dbEntity.getMTSValue().equalsIgnoreCase("true")) {
                this.penCommentsFlag = true;                    
            }
            else {
                this.penCommentsFlag = false; 
            }
        }
        
        // set the default status
        String defaultStatus = "";
                
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "JCMS_DEFAULT_CONTAINER_STATUS");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;
            defaultStatus = dbEntity.getMTSValue();
        }

        // set the default status
        if (defaultStatus != null && !defaultStatus.equals("")) {
            for (CvContainerStatusEntity centity : new ListSupportDTO().getCvContainerStatus()) {
                if (centity.getContainerStatus().equalsIgnoreCase(defaultStatus)) {
                    this.status = centity;
                    break;
                }
            }   
        }
        
        String defaultRoom = "";
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "MTS_DEFAULT_MOUSE_ROOM");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;
            defaultRoom = dbEntity.getMTSValue();
        }

        // set the default room
        if (defaultRoom != null && !defaultRoom.equals("")) {
            for (RoomEntity rentity : new ListSupportDTO().getRoom()) {
                if (rentity.getRoomName().equalsIgnoreCase(defaultRoom)) {
                    this.room = rentity;
                    break;
                }
            }
        }
    }
    
    /**
     * This is invoked when CLear button on query page is clicked, it resets
     * the query page.
     */
    public String clearPenAction() {
        this.initialize();

        return null;
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
     * @return the status
     */
    public CvContainerStatusEntity getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(CvContainerStatusEntity status) {
        this.status = status;
    }

    /**
     * @return the room
     */
    public RoomEntity getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    /**
     * @return the nextPen
     */
    public boolean isNextPen() {
        return nextPen;
    }

    /**
     * @param nextPen the nextPen to set
     */
    public void setNextPen(boolean nextPen) {
        this.nextPen = nextPen;
    }
    
    /**
     * validates pen ID
     * @return 
     */
    public boolean validateCageID() throws Exception {
        boolean flag = false;
        if (!this.nextPen) {
            System.out.println(this.penEntity.getContainerID());
            
        // first check if pen is in use, if yes...get outa here!
        if (!this.validateUniqueContainerID()) {
            return true;
        }

        // if not allow user to choose any pen number she wants, the
        // lets check if this pen number is valid. the funciton isValidPenID() will
        // return true or false and it will append to sErrMsg if needed
        this.mouseFunctions.checkRelaxedPenID(this.penEntity.getContainerID());      

        }
        return flag;
    }
    
    public boolean validatePenInfo() throws Exception {
        boolean flag = false;

        if (this.penEntity != null) {           

            // validate pen name
            // step through elements from cv and when match found, assign it
            String dupPenName = "";
            for (DbsetupEntity entity : listSupportDTO.getSetUpVariables()) {
                if (entity.getMTSVar().equals("JCMS_WARN_DUPLICATE_PEN_NAME")) {
                    dupPenName = entity.getMTSValue();
                    break;
                }
            }

            // if set up variable true, then validate and give warning
            if (dupPenName.equalsIgnoreCase("true")) {
                this.validateUniqueContainerName();
            }
            
            // validate pen id
            if (!this.nextPen) {
                System.out.println(this.penEntity.getContainerID());

                // first check if pen is in use, if yes...get outa here!
                if (this.validateUniqueContainerID()) {
                    return true;
                }
                // check the gap
                this.mouseFunctions.checkRelaxedPenID(this.penEntity.getContainerID()); 
            }
                        
            //validate room
            if (this.room == null) {
                this.addToMessageQueue("Room is missing. Please select a Room.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Room is missing. Please select a Room."));
                flag = true;
            } else if (this.room.getRoomName() == null || this.room.getRoomName().equals("")) {
                this.addToMessageQueue("Room is missing. Please select a Room.",  FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Room is missing. Please select a Room."));
                flag = true;
            }
        }

        return flag;
    }
    
    public String saveAndCloseAction() throws Exception {  
        ContainerHistoryEntity chEntity = new ContainerHistoryEntity();
        // ******************************************************
        // Transfers control from the FORM view to the LIST view
        // pending a successful Save action.
        // ******************************************************

        if (this.penEntity != null) {
            try {
                if (!this.validatePenInfo()) {
                    // set status
                    if (this.status != null) {
                        if (this.status.getContainerStatus() == null || this.status.getContainerStatus().equals("")) {
                            chEntity.setContainerStatuskey(null);
                        } else {
                            chEntity.setContainerStatuskey(status);
                        }
                    } else {
                        chEntity.setContainerStatuskey(null);
                    }

                    // set the room
                    if (this.room != null) {
                        if (this.room.getRoomName() == null || this.room.getRoomName().equals("")) {
                            chEntity.setRoomKey(null);
                        } else {
                            chEntity.setRoomKey(room);
                        }
                    } else {
                        chEntity.setRoomKey(null);
                    }

                    // set the date
                    if (this.actionDate != null) {
                        chEntity.setActionDate(actionDate);
                    } else {
                        chEntity.setActionDate(null);
                    }

                    // generate primary key
                    Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(chEntity);

                    if (null == pk || 0 == pk) {
                        chEntity.setContainerHistorykey(1);
                    } else {
                        chEntity.setContainerHistorykey(pk + 1);
                    }

                    System.out.println("About to Save");

                    // generate primary key
                    pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(penEntity);

                    if (null == pk || 0 == pk) {
                        penEntity.setContainerKey(1);
                    } else {
                        penEntity.setContainerKey(pk + 1);
                    }

                    // set the container ID if next available pen is checked
                    this.nextAvailablePenAction();

                    penEntity.setContainerHistorykey(chEntity.getContainerHistorykey());

                    System.out.println("Pen Entity Details");
                    System.out.println("ContainerID " + penEntity.getContainerID());
                    System.out.println("ContainerName " + penEntity.getContainerName());
                    System.out.println("ContainerStatus " + this.status.getContainerStatus());
                    System.out.println("RoomName " + room.getRoomName());
                    System.out.println("actionDate " + this.actionDate);

                    new SaveAppService().baseCreate(this.penEntity);
                    this.getLogger().logInfo(this.formatLogMessage("save", "Cage " + 
                            penEntity.getContainerID() + " has been inserted"));

                    // insert containerHistory record
                    chEntity.setContainerKey(penEntity);
                    new SaveAppService().baseCreate(chEntity);
                    
                    // update dbInfo table with new maxPenID,
                    // update it only after successfully adding pen.
                    JCMSDbInfoEntity dbInfoEntity = this.mouseFunctions.setMaxPenID(penEntity.getContainerID());
                    new SaveAppService().baseEdit(dbInfoEntity);
                    this.getLogger().logInfo(this.formatLogMessage("save", "DbInfo " + dbInfoEntity.getMaxAutoMouseID() + " has been updated"));

                }
            } 
            // General catch-all for failed saves. The exception's message has already been customized for user display.
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
            this.getLogger().logError(this.formatLogMessage("Cage entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    private ContainerHistoryDTO getContainerHistoryDTO() {
        ContainerHistoryDTO dto = new ContainerHistoryDTO();
        dto.setRoomKey(this.getRoom().getKey().intValue());
        return dto;
    }
    
    /**
     * <b>Purpose:</b> Save a UserBean<br />
     * <b>Overview:</b> Pass on to Business Tier to save.  <p />
     * @throws ValidatorException data failed validation
     * @return String action returned to faces-config.xml
     */
    public String saveAction() throws Exception {
        ContainerHistoryEntity chEntity = new ContainerHistoryEntity();
        
        if (this.penEntity != null) {
            try {
                if (!this.validatePenInfo()) {
                    // set status
                    if (this.status != null) {
                        if (this.status.getContainerStatus() == null || this.status.getContainerStatus().equals("")) {
                            chEntity.setContainerStatuskey(null);
                        } else {
                            chEntity.setContainerStatuskey(status);
                        }
                    } else {
                        chEntity.setContainerStatuskey(null);
                    }

                    // set the room
                    if (this.room != null) {
                        if (this.room.getRoomName() == null || this.room.getRoomName().equals("")) {
                            chEntity.setRoomKey(null);
                        } else {
                            chEntity.setRoomKey(room);
                        }
                    } else {
                        chEntity.setRoomKey(null);
                    }

                    // set the date
                    if (this.actionDate != null) {
                        chEntity.setActionDate(actionDate);
                    } else {
                        chEntity.setActionDate(null);
                    }

                    // generate primary key
                    Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(chEntity);

                    if (null == pk || 0 == pk) {
                        chEntity.setContainerHistorykey(1);
                    } else {
                        chEntity.setContainerHistorykey(pk + 1);
                    }

                    System.out.println("About to Save");

                    // for insert operation
                    boolean blnNewPen = false;
                    if ((penEntity.getContainerKey() == null || penEntity.getContainerKey() == 0) || (this.nextPen)) {
                        blnNewPen = true;
                        // generate primary key
                        pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(penEntity);

                        if (null == pk || 0 == pk) {
                            penEntity.setContainerKey(1);
                        } else {
                            penEntity.setContainerKey(pk + 1);
                        }
                    }

                    if (this.nextPen) {
                        // set the container ID if next available pen is checked
                        this.nextAvailablePenAction();
                    }
                    
                    penEntity.setContainerHistorykey(chEntity.getContainerHistorykey());

                    System.out.println("Pen Entity Details");
                    System.out.println("ContainerID " + penEntity.getContainerID());
                    System.out.println("ContainerName " + penEntity.getContainerName());
                    System.out.println("ContainerStatus " + this.status.getContainerStatus());
                    System.out.println("RoomName " + room.getRoomName());
                    System.out.println("actionDate " + this.actionDate);

                    if (blnNewPen) {
                        new SaveAppService().baseCreate(this.penEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save", "Cage " + penEntity.getContainerID() + " has been inserted"));
                    }

                    // insert containerHistory record
                    chEntity.setContainerKey(penEntity);
                    new SaveAppService().baseCreate(chEntity);
                    
                    //Changed 9/9/2014 When updating a cage, the new ContainerHistory key must be inserted into the existing 
                    //Container record so it points to the most recent ContainerHistory record.
                    if (!blnNewPen) {
                        new SaveAppService().baseEdit(this.penEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save", "Pen  " + penEntity.getContainerID() + " has been updated"));
                    }
                    
                    // update dbInfo table with new maxPenID,
                    // update it only after successfully adding pen.
                    if (blnNewPen) {
                        JCMSDbInfoEntity dbInfoEntity = this.mouseFunctions.setMaxPenID(penEntity.getContainerID());
                        new SaveAppService().baseEdit(dbInfoEntity);
                        this.getLogger().logInfo(this.formatLogMessage("save", "DbInfo " + dbInfoEntity.getMaxAutoMouseID() + " has been updated"));
                    }

                    this.nextPen = false;
                    this.useNextID = "false";

                    this.addToMessageQueue("Cage " + penEntity.getContainerID() + "  has been saved", FacesMessage.SEVERITY_INFO);
                }
            } 
            // General catch-all for failed saves. The exception's message has already been customized for user display.
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
            this.getLogger().logError(this.formatLogMessage("Cage entity is null.", "save"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    /*
     * Check that the pen name can be incremented and create the first name
     * Attempt to add -01 to a name that cannot be incremented.
     */
    public String incrementPenName(String name, boolean flag) {
        String newPenName = "";

        //increment name only id incrementName is true else return same pen name
        if (flag) {
            if (name != null && !name.equals("")) {
                newPenName = this.mouseFunctions.incrementID(name, "JCMS_PEN_NAMES_INCREMENT_RIGHTMOST");
            }
            //We could not increment it successfully
            //Try adding "-01" to the name
            if (newPenName.equals(name)) {
                newPenName = name + "-01";
            }
        } else {
            newPenName = name;
        }
        return newPenName;
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
     * @return the actionDate
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * @param actionDate the actionDate to set
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    public void validateUniqueContainerIDAction() {
        this.validateUniqueContainerID();
    }
    
    /**
     * checkIfCanCreateNewPen()
     * INPUTS: proposedPenID as long - a pen number (not a pen kek!!)
     *         sWarnMsg as string - this function may return a string in this variable (see rules below)
     *         sErrMsg as string - this function may return a string in this variable (see rules)
     *
     * RETURNS: true or false (see rules for details)
     * EFFECTS: sWarnMsg or sErrMsg strings may be appended to
     * ERROR CONDITIONS: none handled
     * ASSUMES: nothing
     * COMMENTS:
     * call this function from forms that may require creating new pen records.
     *
     * The RULES following rules are applied in order (order is important).
     *  - if pen ID already is used, then return false
     *  - if we don't allow relaxed pen numbers and pen ID is less than or equal to dbinfo.maxPenID then
     *    return true
     *  - if we don't allow relaxed pen numbers and pen ID is greater than dbinfo.maxPenID
     *    return false, AND set sErrMsg
     *  - if we do allow relaxed pen numbers then call checkRelaxedPenID() function and return true.
     *     (the call to checkRelaxedPenID() may result in sWarnMsg being updated.
     * @return 
     */
    public boolean checkIfCanCreateNewPen(int proposedPenID) throws Exception {        

        // first check if pen number is 0 or negative
        if (proposedPenID <= 0) {
            this.addToMessageQueue("Pen numbers must be greater than zero.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Pen numbers must be greater than zero."));
            return false;
        }

        // first check if pen is in use, if yes...get outa here!
        if (!this.validateUniqueContainerID()) {
            return false;
        }

        // if not allow user to choose any pen number she wants, the
        // lets check if this pen number is valid. the funciton isValidPenID() will
        // return true or false and it will append to sErrMsg if needed
        this.mouseFunctions.checkRelaxedPenID(proposedPenID);        
        
        return true;
    }
    
    /*
     * This method is called when Pen ID is entered 
     * validate
     */
    public boolean validateUniqueContainerID() {
        try {
            // Check for null or empty String.
            String errMsg = null;
            int s = this.penEntity.getContainerID();
            //MouseFunctionsBean mfunc  = new MouseFunctionsBean();
            
            if (s == 0) {
                errMsg = this.getCustomResourceString("mouse.penID.missing");
                this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Validation ", errMsg));
                return true;
            } else {
                // Check that mouse id is unique.
                ContainerEntity mEntity = this.mouseFunctions.findPen(s);

                if (mEntity != null && (this.getPenEntity().getContainerKey() == null || this.getPenEntity().getContainerKey() == 0)) {
                    errMsg = "Cage number already exists. Please choose a unique cage number.";
                    this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", errMsg));
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return false;
    }
    
    /*
     * This method is called when Pen ID is entered 
     * validate
     */
    public void validateUniqueContainerName() {
        try {
            // Check for null or empty String.
            String errMsg = null;
            String s = this.penEntity.getContainerName();
            //MouseFunctionsBean mfunc  = new MouseFunctionsBean();
            
            if (s != null && !s.equals("")) {                
                // Check that name is unique.
                List<ITBaseEntityInterface> mEntity = this.mouseFunctions.
                        findByPenName(s);

                if (mEntity != null && mEntity.size() > 0) {
                    errMsg = "Cage Name already exists.";
                    this.addToMessageQueue(errMsg, FacesMessage.SEVERITY_WARN);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            errMsg));
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
    public void validateUniqueContainerNameAction() {
        try {
            // Check for null or empty String.
            // validate pen name
            // step through elements from cv and when match found, assign it
            String dupPenName = "";
            for (DbsetupEntity entity : listSupportDTO.getSetUpVariables()) {
                if (entity.getMTSVar().equals("JCMS_WARN_DUPLICATE_PEN_NAME")) {
                    dupPenName = entity.getMTSValue();
                    break;
                }
            }

            // if set up variable true, then validate and give warning
            if (dupPenName.equalsIgnoreCase("true")) {
                this.validateUniqueContainerName();
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
    public void nextAvailablePenAction() {
        try {
            if (this.nextPen) {
                // generate primary key
                Integer pk = this.getRepositoryService().baseFindMaxContainerID(new ContainerEntity());

                if (null == pk || 0 == pk) {
                    this.penEntity.setContainerID(1);
                } else {
                    this.penEntity.setContainerID(pk + 1);
                }
                
                // When the user has checked “use next available ID” then
                // Determine what the next available pen ID will be
                // this.penEntity.setContainerID(this.mouseFunctions.getMaxPenID()+1);
                // if maxPenID is out of sync, then it is creating a problem, so not used
                System.out.println("container ID " + this.penEntity.getContainerID());
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /**
     * @return the nextPenName
     */
    public boolean isNextPenName() {
        return nextPenName;
    }

    /**
     * @param nextPenName the nextPenName to set
     */
    public void setNextPenName(boolean nextPenName) {
        this.nextPenName = nextPenName;
    }

    /**
     * @return the penNameFlag
     */
    public boolean isPenNameFlag() {
        return penNameFlag;
    }

    /**
     * @param penNameFlag the penNameFlag to set
     */
    public void setPenNameFlag(boolean penNameFlag) {
        this.penNameFlag = penNameFlag;
    }

    /**
     * @return the penCommentsFlag
     */
    public boolean isPenCommentsFlag() {
        return penCommentsFlag;
    }

    /**
     * @param penCommentsFlag the penCommentsFlag to set
     */
    public void setPenCommentsFlag(boolean penCommentsFlag) {
        this.penCommentsFlag = penCommentsFlag;
    }

    /**
     * @return the penHealthLevelFlag
     */
    public boolean isPenHealthLevelFlag() {
        return penHealthLevelFlag;
    }

    /**
     * @param penHealthLevelFlag the penHealthLevelFlag to set
     */
    public void setPenHealthLevelFlag(boolean penHealthLevelFlag) {
        this.penHealthLevelFlag = penHealthLevelFlag;
    }

    /**
     * @return the useNextID
     */
    public String getUseNextID() {
        return useNextID;
    }

    /**
     * @param useNextID the useNextID to set
     */
    public void setUseNextID(String useNextID) {
        this.useNextID = useNextID;
    }
}
