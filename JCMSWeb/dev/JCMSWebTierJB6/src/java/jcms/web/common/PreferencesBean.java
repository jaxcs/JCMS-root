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

package jcms.web.common;

/**
 *
 * @author bas
 */

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.dao.cvPreferencesDAO;
import jcms.integrationtier.dao.UserPreferencesDAO;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.cv.CvCoatColorEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;

public class PreferencesBean extends WTBaseBackingBean implements Serializable {
    
    private ListSupportDTO          listSupportDTO;
    
    //provide dropdowns on the xhtml screen
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private CvMouseProtocolEntity   mouseProtocolEntity;
    private CvCauseOfDeathEntity    mouseCODEntity;
    private CvMouseOriginEntity     mouseOriginEntity;
    private RoomEntity              roomEntity;
    private CvCoatColorEntity       coatColorEntity;
    private CvDietEntity            dietEntity;
    private CvDietEntity            matingDietEntity;
    private CvSexEntity             sexEntity;
    private CvBreedingStatusEntity  breedingStatusEntity;
    private CvMouseProtocolEntity   litterProtocolEntity;
    private CvMouseOriginEntity     litterOriginEntity;
    
    //boolean values for the checkboxes on the xhtml screen
    private boolean hideLitterID = false;
    private boolean hideProtocolID = false;
    private boolean hideUseSchedules = false;
    private boolean hideMouseOrigin = false;
    private boolean hideCOD = false;
    private boolean hideCODNotes = false;
    private boolean hideRoomName = false;
    private boolean hidePhenotypes = false;
    private boolean hideMouseComment = false;
    private boolean hideCoatColor = false;
    private boolean hideDiet = false;
    private boolean hideSampleVialID = false;
    private boolean hideSampleVialTagPosition = false;
    private boolean hideReplacementTag = false;
    private boolean hideWeanNote = false;
    private boolean hideMatingComment = false;
    private boolean hideWeanTime = false;
    private boolean hideNeedsTyping = false;
    private boolean hideMatingDiet = false;
    private boolean hideMatingDam2 = false;
    private boolean hideNumberBornDead = false;
    private boolean hideNumberCulledAtWean = false;
    private boolean hideNumberMissingAtWean = false;
    private boolean hideLitterType = false;
    private boolean hideLitterComments = false;
    private boolean hideLitterProtocol = false;
    private boolean hideLitterOrigin = false;
    private boolean hideLitterUseSchedules = false;
    private boolean hideLeavePupsInMatingPen = false;
    
    private String newStrainFirstDefault = "";
    private String newProtocolIdDefault = "";
    private String newMouseOriginDefault = "";
    private String newMouseCODDefault = "";
    private String newRoomNameDefault = "";
    private String newCoatColorDefault = "";
    private String newDietDefault = "";
    private String newSexDefault = "";
    private String newBreedingStatusDefault = "";
    private String newWeanTimeDefault = "";
    private String newNeedsTypingDefault = "";
    private String newMatingDietDefault = "";
    private String newLitterProtocolIdDefault = "";
    private String newLitterOriginDefault = "";
    private String newLeavePupsInMatingPenDefault = "";
    
    //values for any variables on the xhtml screen, such as radio buttons
    private String strainFirst = "True";
    private String weanTime = "true";
    private String needsTyping = "false";
    private String leavePupsInMatingPen = "false";
    
    private Integer count = 0;
    
       
    public String initializePreferences() throws Exception {
        mouseProtocolEntity = new CvMouseProtocolEntity();
        setMouseOriginEntity(new CvMouseOriginEntity());
        setMouseCODEntity(new CvCauseOfDeathEntity());
        roomEntity = new RoomEntity();
        coatColorEntity = new CvCoatColorEntity();
        setDietEntity(new CvDietEntity());
        setMatingDietEntity(new CvDietEntity());
        sexEntity = new CvSexEntity();
        setBreedingStatusEntity(new CvBreedingStatusEntity());
        litterProtocolEntity = new CvMouseProtocolEntity();
        litterOriginEntity = new CvMouseOriginEntity();
        
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
        listSupportDTO = new ListSupportDTO(ownerLst);
        //First we need to know the current user's key
        String userKey = "";
        UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
        userKey = currentUser.getUserkey().toString();
        System.out.println("current user " + userKey);
        //All fields on this form must have the user's current preferences looked up
        //and displayed.
        cvPreferencesDAO DAO = new cvPreferencesDAO();
        //Initialize the hide check box fields for the mouseEdit screen
        setHideLitterID(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "litterID")));
        hideProtocolID = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "protocol")));
        hideUseSchedules = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "useScheduleTermName")));
        hideMouseOrigin = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "origin")));
        hideCOD = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "cod")));
        hideCODNotes = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "codNotes")));
        hideRoomName = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "roomName")));
        hidePhenotypes = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "phenotype")));
        hideMouseComment = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "comment")));
        hideCoatColor = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "coatColor")));
        setHideDiet(Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "diet")));
        hideSampleVialID = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "sampleVialID")));
        hideSampleVialTagPosition = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "sampleVialTagPosition")));
        hideReplacementTag = (Boolean.parseBoolean(DAO.getHideField(userKey,"mouseEdit", "newTag")));
        //initializations for the mating screen
        hideWeanNote = (Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "weanNote")));
        hideMatingComment = (Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "comment")));
        hideNeedsTyping = (Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "needsTyping")));
        hideWeanTime = (Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "weanTime")));
        setHideMatingDiet(Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "diet")));
        hideMatingDam2 = (Boolean.parseBoolean(DAO.getHideField(userKey,"matingEdit", "_dam2_key")));
        //initializations for the litter screen
        setHideNumberBornDead(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberBornDead")));
        setHideNumberCulledAtWean(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberCulledAtWean")));
        setHideNumberMissingAtWean(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "numberMissingAtWean")));
        setHideLitterType(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "litterType")));
        setHideLitterComments(Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "comment")));
        hideLitterProtocol = (Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "protocol")));
        hideLitterUseSchedules = (Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "useScheduleTermName")));
        hideLitterOrigin = (Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "origin")));
        hideLeavePupsInMatingPen = (Boolean.parseBoolean(DAO.getHideField(userKey,"litterEdit", "leavePupsInMatingPen")));
               
        //Initialize any defaults
        //All fields that can have a default must have the default value put into the control on the screen.
        //If the default is not in the cv table display blank
        //Note that if the user preference for strain name first is not "false", we will default to true
        strainFirst = "true";
        if (DAO.GetDefaultValue(userKey,"global", "JCMS_STRAINNAME_FIRST") != null) {
            if (!DAO.GetDefaultValue(userKey,"global", "JCMS_STRAINNAME_FIRST").equals("")) {
                setNewStrainFirstDefault(DAO.GetDefaultValue(userKey,"global", "JCMS_STRAINNAME_FIRST"));
                if (getNewStrainFirstDefault().equalsIgnoreCase("false")) {
                    strainFirst = "false";
                } 
            } 
        } 
                
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "protocol") != null) {
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "protocol").equals("")) {
                setNewProtocolIdDefault(DAO.GetDefaultValue(userKey, "mouseEdit", "protocol"));
                for (CvMouseProtocolEntity entity : listSupportDTO.getAllCvMouseProtocol()) {
                    if (entity.getId().trim().equalsIgnoreCase(getNewProtocolIdDefault().trim())) {
                        this.mouseProtocolEntity = entity;
                        break;
                     }
                 }
             }
         }
        
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "origin") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "origin").equals("")) { 
                setNewMouseOriginDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "origin"));
                // step through elements from cv and when match found, assign it
                for (CvMouseOriginEntity entity : listSupportDTO.getCvMouseOrigin()) {
                    if (entity.getMouseOrigin().equalsIgnoreCase(getNewMouseOriginDefault())) {
                        this.setMouseOriginEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "cod") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "cod").equals("")) { 
                setNewMouseCODDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "cod"));
                // step through elements from cv and when match found, assign it
                for (CvCauseOfDeathEntity entity : listSupportDTO.getCvCauseOfDeath()) {
                    if (entity.getCod().equalsIgnoreCase(getNewMouseCODDefault())) {
                        this.setMouseCODEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //Note, room name must have a default. This control may not have a blank row for selecting
        //If the value is not in the cv table
        //the first value in the control ends up displayed to the user
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "roomName") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "roomName").equals("")) { 
                setNewRoomNameDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "roomName"));
                
                // step through elements from cv and when match found, assign it
                for (RoomEntity entity : listSupportDTO.getRoom()) {
                    if (entity.getRoomName().equalsIgnoreCase(getNewRoomNameDefault())) {
                        this.setRoomEntity(entity);
                        //System.out.println("Room Name default: " + newRoomNameDefault);
                        break;
                    }    
                 }
             }
         }
        
        //coat color
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "coatColor") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "coatColor").equals("")) { 
                newCoatColorDefault = ((DAO.GetDefaultValue(userKey,"mouseEdit", "coatColor")));
                // step through elements from cv and when match found, assign it
                for (CvCoatColorEntity entity : listSupportDTO.getCvCoatColor()) {
                    if (entity.getCoatColor().equalsIgnoreCase(newCoatColorDefault)) {
                        this.setCoatColorEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //diet
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "diet") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "diet").equals("")) { 
                setNewDietDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "diet"));
                // step through elements from cv and when match found, assign it
                for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                    if (entity.getDiet().equalsIgnoreCase(getNewDietDefault())) {
                        this.setDietEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //sex
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "sex") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "sex").equals("")) { 
                setNewSexDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "sex"));
                // step through elements from cv and when match found, assign it
                for (CvSexEntity entity : listSupportDTO.getCvSex()) {
                    if (entity.getSex().equalsIgnoreCase(getNewSexDefault())) {
                        this.setSexEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //breeding status
        if (DAO.GetDefaultValue(userKey,"mouseEdit", "breedingStatus") != null){
            if (!DAO.GetDefaultValue(userKey,"mouseEdit", "breedingStatus").equals("")) { 
                setNewBreedingStatusDefault(DAO.GetDefaultValue(userKey,"mouseEdit", "breedingStatus"));
                // step through elements from cv and when match found, assign it
                for (CvBreedingStatusEntity entity : listSupportDTO.getCvBreedingStatus()) {
                    if (entity.getBreedingStatus().equalsIgnoreCase(getNewBreedingStatusDefault())) {
                        this.setBreedingStatusEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //needs typing
        //If null in cv_Preferences, default it to false
        needsTyping = "false";
        if (DAO.GetDefaultValue(userKey,"matingEdit", "needsTyping") != null){
            if (!DAO.GetDefaultValue(userKey,"matingEdit", "needsTyping").equals("")) { 
                setNewNeedsTypingDefault(DAO.GetDefaultValue(userKey,"matingEdit", "needsTyping"));
                if (getNewNeedsTypingDefault().equalsIgnoreCase("true")) {
                    needsTyping = "true";
                }
            }
        }
        
        //wean time
        //If null in cv_Preferences, default it to true
        weanTime = "true";
        if (DAO.GetDefaultValue(userKey,"matingEdit", "weanTime") != null){
            if (!DAO.GetDefaultValue(userKey,"matingEdit", "weanTime").equals("")) { 
                setNewWeanTimeDefault(DAO.GetDefaultValue(userKey,"matingEdit", "weanTime"));
                if (getNewWeanTimeDefault().equalsIgnoreCase("false")) {
                    weanTime = "false";
                }
            }
        }
        
        //mating diet
        if (DAO.GetDefaultValue(userKey,"matingEdit", "diet") != null){
            if (!DAO.GetDefaultValue(userKey,"matingEdit", "diet").equals("")) { 
                setNewMatingDietDefault(DAO.GetDefaultValue(userKey,"matingEdit", "diet"));
                // step through elements from cv and when match found, assign it
                for (CvDietEntity entity : listSupportDTO.getCvDiet()) {
                    if (entity.getDiet().equalsIgnoreCase(getNewMatingDietDefault())) {
                        this.setMatingDietEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //Litter defaults
        if (DAO.GetDefaultValue(userKey,"litterEdit", "protocol") != null) {
            if (!DAO.GetDefaultValue(userKey,"litterEdit", "protocol").equals("")) {
                setNewLitterProtocolIdDefault(DAO.GetDefaultValue(userKey, "litterEdit", "protocol"));
                for (CvMouseProtocolEntity entity : listSupportDTO.getAllCvMouseProtocol()) {
                    if (entity.getId().trim().equalsIgnoreCase(getNewLitterProtocolIdDefault().trim())) {
                        this.litterProtocolEntity = entity;
                        break;
                     }
                 }
             }
         }
        
        if (DAO.GetDefaultValue(userKey,"litterEdit", "origin") != null){
            if (!DAO.GetDefaultValue(userKey,"litterEdit", "origin").equals("")) { 
                setNewLitterOriginDefault(DAO.GetDefaultValue(userKey,"litterEdit", "origin"));
                // step through elements from cv and when match found, assign it
                for (CvMouseOriginEntity entity : listSupportDTO.getCvMouseOrigin()) {
                    if (entity.getMouseOrigin().equalsIgnoreCase(getNewLitterOriginDefault())) {
                        this.setLitterOriginEntity(entity);
                        break;
                    }    
                 }
             }
         }
        
        //Note that if the user preference for leavePupsInMatingPen is not "true", we will default to false
        leavePupsInMatingPen = "false";
        if (DAO.GetDefaultValue(userKey,"litterEdit", "leavePupsInMatingPen") != null) {
            if (!DAO.GetDefaultValue(userKey,"litterEdit", "leavePupsInMatingPen").equals("")) {
                setNewLeavePupsInMatingPenDefault(DAO.GetDefaultValue(userKey,"litterEdit", "leavePupsInMatingPen"));
                if (getNewLeavePupsInMatingPenDefault().equalsIgnoreCase("true")) {
                    leavePupsInMatingPen = "true";
                } 
            } 
        }
        
        // By telling it to return this string, it will open the xhtml page associated with this name.
        return "myPreferences";
    }
    
    public void savePreferences() throws Exception {
        
        try {
            boolean success = true;
            System.out.println("Save preferences action");
            //First we need to know the current user's key
            String userKey = "";
            UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
            userKey = currentUser.getUserkey().toString();
            UserPreferencesDAO userPrefDAO = new UserPreferencesDAO();
            
            //Check for required fields
            if (this.hideMouseOrigin && this.getMouseOriginEntity() == null) {
                //error - field is required
                this.addToMessageQueue("Mouse origin is required when the field is hidden. ", FacesMessage.SEVERITY_ERROR); 
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mouse origin is missing. "));
                success = false;
             } else {
                //have to save the origin, replace null with empty
                if (this.getMouseOriginEntity() == null) {
                    this.setNewMouseOriginDefault("");
                } else {
                    this.setNewMouseOriginDefault(this.getMouseOriginEntity().getMouseOrigin());    
                }
            }
            
            if (this.hideRoomName && this.getRoomEntity() == null) {
                //error - field is required
                this.addToMessageQueue("Room Name default is required when the field is hidden. ", FacesMessage.SEVERITY_ERROR); 
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Room Name default is missing. "));
                success = false;
             } else {
                //have to save the room, replace null with empty
                if (this.getRoomEntity() == null) {
                    this.setNewRoomNameDefault("");
                } else {
                    this.setNewRoomNameDefault(this.getRoomEntity().getRoomName());    
                }
            }
            
            //Litter required default
            if (this.hideLitterOrigin && this.getLitterOriginEntity() == null) {
                //error - field is required
                this.addToMessageQueue("Mouse origin on the litter screen is required when the field is hidden. ", FacesMessage.SEVERITY_ERROR); 
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mouse origin on litter screen is missing. "));
                success = false;
             } else {
                //have to save the Litter origin, replace null with empty
                if (this.getLitterOriginEntity() == null) {
                    this.setNewLitterOriginDefault("");
                } else {
                    this.setNewLitterOriginDefault(this.getLitterOriginEntity().getMouseOrigin());    
                }
            }
            
            //Get the strain first value to save
            if (strainFirst.equals("true")) {
                this.setNewStrainFirstDefault("true");
            } else {
                this.setNewStrainFirstDefault("false");
            }
            
            //Get the wean time value to save
            if (weanTime.equals("true")) {
                this.setNewWeanTimeDefault("true");
            } else {
                this.setNewWeanTimeDefault("false");
            }
            //Get the needs genotyping value to save
            if (needsTyping.equals("true")) {
                this.setNewNeedsTypingDefault("true");
            } else {
                this.setNewNeedsTypingDefault("false");
            }
            
            //Get the leave pups in mating pen value to save
            if (leavePupsInMatingPen.equals("true")) {
                this.setNewLeavePupsInMatingPenDefault("true");
            } else {
                this.setNewLeavePupsInMatingPenDefault("false");
            }
            //Get defaults that are not required
            //have to save the protocol, replace null with empty
            if (this.mouseProtocolEntity == null) {
                this.setNewProtocolIdDefault("");
            } else {
                this.setNewProtocolIdDefault(this.mouseProtocolEntity.getId());    
            }
            
            //have to save the COD, replace null with empty
            if (this.getMouseCODEntity() == null) {
                this.setNewMouseCODDefault("");
            } else {
                this.setNewMouseCODDefault(this.getMouseCODEntity().getCod());    
            }
            
            //have to save the coat color, replace null with empty
            if (this.getCoatColorEntity() == null) {
                this.newCoatColorDefault = "";
            } else {
                this.newCoatColorDefault = this.getCoatColorEntity().getCoatColor();    
            }
            
            //have to save the diet, replace null with empty
            if (this.getDietEntity() == null) {
                this.newDietDefault = "";
            } else {
                this.newDietDefault = this.getDietEntity().getDiet();    
            }
            
            //have to save the sex, replace null with empty
            if (this.getSexEntity() == null) {
                this.newSexDefault = "";
            } else {
                this.newSexDefault = this.getSexEntity().getSex();    
            }
            
            //have to save the breeding status, replace null with empty
            if (this.getBreedingStatusEntity() == null) {
                this.newBreedingStatusDefault = "";
            } else {
                this.newBreedingStatusDefault = this.getBreedingStatusEntity().getBreedingStatus();    
            }
            
            //have to save the mating diet, replace null with empty
            if (this.getMatingDietEntity() == null) {
                this.newMatingDietDefault = "";
            } else {
                this.newMatingDietDefault = this.getMatingDietEntity().getDiet();    
            }
            
            //have to save the litter protocol, replace null with empty
            if (this.litterProtocolEntity == null) {
                this.setNewLitterProtocolIdDefault("");
            } else {
                this.setNewLitterProtocolIdDefault(this.litterProtocolEntity.getId());    
            }
            
            //Now save the values if there were no error messages
            if (success) {
                count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "protocol", hideProtocolID, getNewProtocolIdDefault());
                if (count != 0) {
                    count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "UseScheduleTermName", hideUseSchedules, "");
                    if (count != 0) {
                        count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "origin", hideMouseOrigin, getNewMouseOriginDefault());
                        if (count != 0) {
                            count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "litterID", isHideLitterID(), "");
                            if (count != 0) {
                                count = userPrefDAO.updateMyUserPreference(userKey, "global", "JCMS_STRAINNAME_FIRST", false, getNewStrainFirstDefault());
                                if (count != 0) {
                                    count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "cod", hideCOD, getNewMouseCODDefault());
                                    if (count != 0) {
                                        count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "codNotes", isHideCODNotes(), "");
                                        if (count != 0) {
                                            count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "roomName", hideRoomName, getNewRoomNameDefault());
                                            if (count != 0) { 
                                                count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "phenotype", hidePhenotypes, "");
                                                if (count != 0) {
                                                    count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "comment", hideMouseComment, "");
                                                    if (count != 0) {
                                                        count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "coatColor", hideCoatColor, newCoatColorDefault);
                                                        if (count != 0) {
                                                            count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "diet", hideDiet, newDietDefault);
                                                            if (count != 0) {
                                                                count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "sampleVialID", hideSampleVialID, "");
                                                                if (count != 0) {
                                                                    count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "sampleVialTagPosition", hideSampleVialTagPosition, "");
                                                                    if (count != 0) {
                                                                        count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "sex", false, newSexDefault);
                                                                        if (count != 0) {
                                                                            count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "breedingStatus", false, newBreedingStatusDefault);
                                                                            if (count != 0) {
                                                                                count = userPrefDAO.updateMyUserPreference(userKey, "mouseEdit", "newTag", hideReplacementTag, "");
                                                                                if (count != 0) {
                                                                                    count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "weanNote", hideWeanNote, "");
                                                                                    if (count != 0) {
                                                                                        count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "comment", hideMatingComment, "");
                                                                                        if (count != 0) {
                                                                                            count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "needsTyping", hideNeedsTyping, newNeedsTypingDefault);
                                                                                            if (count != 0) {
                                                                                                count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "weanTime", hideWeanTime, newWeanTimeDefault);
                                                                                                if (count != 0) {
                                                                                                    count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "diet", hideMatingDiet, newMatingDietDefault);
                                                                                                    if (count != 0) {
                                                                                                        count = userPrefDAO.updateMyUserPreference(userKey, "matingEdit", "_dam2_key", hideMatingDam2, "");
                                                                                                        if (count != 0) {
                                                                                                            count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "numberBornDead", hideNumberBornDead, "");
                                                                                                            if (count != 0){
                                                                                                                count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "numberCulledAtWean", hideNumberCulledAtWean, "");
                                                                                                                if (count != 0) {
                                                                                                                    count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "numberMissingAtWean", hideNumberMissingAtWean, "");
                                                                                                                    if (count != 0) {
                                                                                                                        count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "litterType", hideLitterType, "");
                                                                                                                        if (count != 0) {
                                                                                                                            count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "comment", hideLitterComments, "");
                                                                                                                            if (count != 0) {
                                                                                                                                count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "protocol", hideLitterProtocol, getNewLitterProtocolIdDefault());
                                                                                                                                if (count != 0) {
                                                                                                                                    count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "origin", hideLitterOrigin, getNewLitterOriginDefault());
                                                                                                                                    if (count != 0) {
                                                                                                                                        count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "UseScheduleTermName", hideLitterUseSchedules, "");
                                                                                                                                        if (count != 0) {
                                                                                                                                            count = userPrefDAO.updateMyUserPreference(userKey, "litterEdit", "leavePupsInMatingPen", hideLeavePupsInMatingPen, getNewLeavePupsInMatingPenDefault());
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (count != 0) {
                    this.addToMessageQueue("Your preferences have been changed. ", FacesMessage.SEVERITY_INFO); 
                } else {
                    this.addToMessageQueue("Due to an internal error, your preferences have not all been changed. Click 'My Preferences' on the menu bar to refresh the current preferences", FacesMessage.SEVERITY_ERROR);      
                }
                
            }
        }
                            
        catch (Exception e) {
            String msg = "Save preferences failed.  " + e.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "savePreferencesAction"));
        }
    }
    
    /**
     * returns value of JCMS setup variable,
     * otherwise "" is returned
     * @return
     * @throws Exception 
     */
    private String returnSetupVariableValue(String setupVariable) {
        DbsetupEntity setupvar = new DbsetupEntity();
        String variableValue = "";
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), setupVariable);

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
            if ((setupvar != null && setupvar.getMTSValue() != null
                    && !setupvar.getMTSValue().equals(""))) {
                variableValue = setupvar.getMTSValue().trim();
            }
        }   
        return variableValue;
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
     * @return the hideProtocolID
     */
    public boolean isHideProtocolID() {
        return hideProtocolID;
    }

    /**
     * @param hideProtocolID the hideProtocolID to set
     */
    public void setHideProtocolID(boolean hideProtocolID) {
        this.hideProtocolID = hideProtocolID;
    }

    /**
     * @return the hideMouseOrigin
     */
    public boolean isHideMouseOrigin() {
        return hideMouseOrigin;
    }

    /**
     * @param hideMouseOrigin the hideMouseOrigin to set
     */
    public void setHideMouseOrigin(boolean hideMouseOrigin) {
        this.hideMouseOrigin = hideMouseOrigin;
    }

    /**
     * @return the hideUseSchedules
     */
    public boolean isHideUseSchedules() {
        return hideUseSchedules;
    }

    /**
     * @param hideUseSchedules the hideUseSchedules to set
     */
    public void setHideUseSchedules(boolean hideUseSchedules) {
        this.hideUseSchedules = hideUseSchedules;
    }

    /**
     * @return the hideLitterID
     */
    public boolean isHideLitterID() {
        return hideLitterID;
    }

    /**
     * @param hideLitterID the hideLitterID to set
     */
    public void setHideLitterID(boolean hideLitterID) {
        this.hideLitterID = hideLitterID;
    }

    /**
     * @return the strainFirst
     */
    public String getStrainFirst() {
        return strainFirst;
    }

    /**
     * @param strainFirst the strainFirst to set
     */
    public void setStrainFirst(String strainFirst) {
        this.strainFirst = strainFirst;
    }

    /**
     * @return the hideCOD
     */
    public boolean isHideCOD() {
        return hideCOD;
    }

    /**
     * @param hideCOD the hideCOD to set
     */
    public void setHideCOD(boolean hideCOD) {
        this.hideCOD = hideCOD;
    }

    /**
     * @return the mouseCODEntity
     */
    public CvCauseOfDeathEntity getMouseCODEntity() {
        return mouseCODEntity;
    }

    /**
     * @param mouseCODEntity the mouseCODEntity to set
     */
    public void setMouseCODEntity(CvCauseOfDeathEntity mouseCODEntity) {
        this.mouseCODEntity = mouseCODEntity;
    }

    /**
     * @return the hideCODNotes
     */
    public boolean isHideCODNotes() {
        return hideCODNotes;
    }

    /**
     * @param hideCODNotes the hideCODNotes to set
     */
    public void setHideCODNotes(boolean hideCODNotes) {
        this.hideCODNotes = hideCODNotes;
    }

    /**
     * @return the roomEntity
     */
    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    /**
     * @param roomEntity the roomEntity to set
     */
    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    /**
     * @return the hideRoomName
     */
    public boolean isHideRoomName() {
        return hideRoomName;
    }

    /**
     * @param hideRoomName the hideRoomName to set
     */
    public void setHideRoomName(boolean hideRoomName) {
        this.hideRoomName = hideRoomName;
    }

    /**
     * @return the hidePhenotypes
     */
    public boolean isHidePhenotypes() {
        return hidePhenotypes;
    }

    /**
     * @param hidePhenotypes the hidePhenotypes to set
     */
    public void setHidePhenotypes(boolean hidePhenotypes) {
        this.hidePhenotypes = hidePhenotypes;
    }

    /**
     * @return the hideMouseComment
     */
    public boolean isHideMouseComment() {
        return hideMouseComment;
    }

    /**
     * @param hideMouseComment the hideMouseComment to set
     */
    public void setHideMouseComment(boolean hideMouseComment) {
        this.hideMouseComment = hideMouseComment;
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
     * @return the hideCoatColor
     */
    public boolean isHideCoatColor() {
        return hideCoatColor;
    }

    /**
     * @param hideCoatColor the hideCoatColor to set
     */
    public void setHideCoatColor(boolean hideCoatColor) {
        this.hideCoatColor = hideCoatColor;
    }

    /**
     * @return the newCoatColorDefault
     */
    public String getNewCoatColorDefault() {
        return newCoatColorDefault;
    }

    /**
     * @param newCoatColorDefault the newCoatColorDefault to set
     */
    public void setNewCoatColorDefault(String newCoatColorDefault) {
        this.newCoatColorDefault = newCoatColorDefault;
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
     * @return the hideDiet
     */
    public boolean isHideDiet() {
        return hideDiet;
    }

    /**
     * @param hideDiet the hideDiet to set
     */
    public void setHideDiet(boolean hideDiet) {
        this.hideDiet = hideDiet;
    }

    /**
     * @return the newDietDefault
     */
    public String getNewDietDefault() {
        return newDietDefault;
    }

    /**
     * @param newDietDefault the newDietDefault to set
     */
    public void setNewDietDefault(String newDietDefault) {
        this.newDietDefault = newDietDefault;
    }

    /**
     * @return the hideSampleVialID
     */
    public boolean isHideSampleVialID() {
        return hideSampleVialID;
    }

    /**
     * @param hideSampleVialID the hideSampleVialID to set
     */
    public void setHideSampleVialID(boolean hideSampleVialID) {
        this.hideSampleVialID = hideSampleVialID;
    }

    /**
     * @return the hideSampleVialTagPosition
     */
    public boolean isHideSampleVialTagPosition() {
        return hideSampleVialTagPosition;
    }

    /**
     * @param hideSampleVialTagPosition the hideSampleVialTagPosition to set
     */
    public void setHideSampleVialTagPosition(boolean hideSampleVialTagPosition) {
        this.hideSampleVialTagPosition = hideSampleVialTagPosition;
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
     * @return the newSexDefault
     */
    public String getNewSexDefault() {
        return newSexDefault;
    }

    /**
     * @param newSexDefault the newSexDefault to set
     */
    public void setNewSexDefault(String newSexDefault) {
        this.newSexDefault = newSexDefault;
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
     * @return the newStrainFirstDefault
     */
    public String getNewStrainFirstDefault() {
        return newStrainFirstDefault;
    }

    /**
     * @param newStrainFirstDefault the newStrainFirstDefault to set
     */
    public void setNewStrainFirstDefault(String newStrainFirstDefault) {
        this.newStrainFirstDefault = newStrainFirstDefault;
    }

    /**
     * @return the newProtocolIdDefault
     */
    public String getNewProtocolIdDefault() {
        return newProtocolIdDefault;
    }

    /**
     * @param newProtocolIdDefault the newProtocolIdDefault to set
     */
    public void setNewProtocolIdDefault(String newProtocolIdDefault) {
        this.newProtocolIdDefault = newProtocolIdDefault;
    }

    /**
     * @return the newMouseOriginDefault
     */
    public String getNewMouseOriginDefault() {
        return newMouseOriginDefault;
    }

    /**
     * @param newMouseOriginDefault the newMouseOriginDefault to set
     */
    public void setNewMouseOriginDefault(String newMouseOriginDefault) {
        this.newMouseOriginDefault = newMouseOriginDefault;
    }

    /**
     * @return the newMouseCODDefault
     */
    public String getNewMouseCODDefault() {
        return newMouseCODDefault;
    }

    /**
     * @param newMouseCODDefault the newMouseCODDefault to set
     */
    public void setNewMouseCODDefault(String newMouseCODDefault) {
        this.newMouseCODDefault = newMouseCODDefault;
    }

    /**
     * @return the newRoomNameDefault
     */
    public String getNewRoomNameDefault() {
        return newRoomNameDefault;
    }

    /**
     * @param newRoomNameDefault the newRoomNameDefault to set
     */
    public void setNewRoomNameDefault(String newRoomNameDefault) {
        this.newRoomNameDefault = newRoomNameDefault;
    }

    /**
     * @return the newBreedingStatusDefault
     */
    public String getNewBreedingStatusDefault() {
        return newBreedingStatusDefault;
    }

    /**
     * @param newBreedingStatusDefault the newBreedingStatusDefault to set
     */
    public void setNewBreedingStatusDefault(String newBreedingStatusDefault) {
        this.newBreedingStatusDefault = newBreedingStatusDefault;
    }

    /**
     * @return the hideReplacementTag
     */
    public boolean isHideReplacementTag() {
        return hideReplacementTag;
    }

    /**
     * @param hideReplacementTag the hideReplacementTag to set
     */
    public void setHideReplacementTag(boolean hideReplacementTag) {
        this.hideReplacementTag = hideReplacementTag;
    }

    /**
     * @return the hideWeanNote
     */
    public boolean isHideWeanNote() {
        return hideWeanNote;
    }

    /**
     * @param hideWeanNote the hideWeanNote to set
     */
    public void setHideWeanNote(boolean hideWeanNote) {
        this.hideWeanNote = hideWeanNote;
    }

    /**
     * @return the hideMatingComment
     */
    public boolean isHideMatingComment() {
        return hideMatingComment;
    }

    /**
     * @param hideMatingComment the hideMatingComment to set
     */
    public void setHideMatingComment(boolean hideMatingComment) {
        this.hideMatingComment = hideMatingComment;
    }

    /**
     * @return the hideWeanTime
     */
    public boolean isHideWeanTime() {
        return hideWeanTime;
    }

    /**
     * @param hideWeanTime the hideWeanTime to set
     */
    public void setHideWeanTime(boolean hideWeanTime) {
        this.hideWeanTime = hideWeanTime;
    }

    /**
     * @return the hideNeedsTyping
     */
    public boolean isHideNeedsTyping() {
        return hideNeedsTyping;
    }

    /**
     * @param hideNeedsTyping the hideNeedsTyping to set
     */
    public void setHideNeedsTyping(boolean hideNeedsTyping) {
        this.hideNeedsTyping = hideNeedsTyping;
    }

    /**
     * @return the newWeanTimeDefault
     */
    public String getNewWeanTimeDefault() {
        return newWeanTimeDefault;
    }

    /**
     * @param newWeanTimeDefault the newWeanTimeDefault to set
     */
    public void setNewWeanTimeDefault(String newWeanTimeDefault) {
        this.newWeanTimeDefault = newWeanTimeDefault;
    }

    /**
     * @return the newNeedsTypingDefault
     */
    public String getNewNeedsTypingDefault() {
        return newNeedsTypingDefault;
    }

    /**
     * @param newNeedsTypingDefault the newNeedsTypingDefault to set
     */
    public void setNewNeedsTypingDefault(String newNeedsTypingDefault) {
        this.newNeedsTypingDefault = newNeedsTypingDefault;
    }

    /**
     * @return the weanTime
     */
    public String getWeanTime() {
        return weanTime;
    }

    /**
     * @param weanTime the weanTime to set
     */
    public void setWeanTime(String weanTime) {
        this.weanTime = weanTime;
    }

    /**
     * @return the needsTyping
     */
    public String getNeedsTyping() {
        return needsTyping;
    }

    /**
     * @param needsTyping the needsTyping to set
     */
    public void setNeedsTyping(String needsTyping) {
        this.needsTyping = needsTyping;
    }

    /**
     * @return the hideMatingDiet
     */
    public boolean isHideMatingDiet() {
        return hideMatingDiet;
    }

    /**
     * @param hideMatingDiet the hideMatingDiet to set
     */
    public void setHideMatingDiet(boolean hideMatingDiet) {
        this.hideMatingDiet = hideMatingDiet;
    }

    /**
     * @return the newMatingDietDefault
     */
    public String getNewMatingDietDefault() {
        return newMatingDietDefault;
    }

    /**
     * @param newMatingDietDefault the newMatingDietDefault to set
     */
    public void setNewMatingDietDefault(String newMatingDietDefault) {
        this.newMatingDietDefault = newMatingDietDefault;
    }

    /**
     * @return the matingDietEntity
     */
    public CvDietEntity getMatingDietEntity() {
        return matingDietEntity;
    }

    /**
     * @param matingDietEntity the matingDietEntity to set
     */
    public void setMatingDietEntity(CvDietEntity matingDietEntity) {
        this.matingDietEntity = matingDietEntity;
    }

    /**
     * @return the hideMatingDam2
     */
    public boolean isHideMatingDam2() {
        return hideMatingDam2;
    }

    /**
     * @param hideMatingDam2 the hideMatingDam2 to set
     */
    public void setHideMatingDam2(boolean hideMatingDam2) {
        this.hideMatingDam2 = hideMatingDam2;
    }

    /**
     * @return the hideNumberBornDead
     */
    public boolean isHideNumberBornDead() {
        return hideNumberBornDead;
    }

    /**
     * @param hideNumberBornDead the hideNumberBornDead to set
     */
    public void setHideNumberBornDead(boolean hideNumberBornDead) {
        this.hideNumberBornDead = hideNumberBornDead;
    }

    /**
     * @return the hideNumberCulledAtWean
     */
    public boolean isHideNumberCulledAtWean() {
        return hideNumberCulledAtWean;
    }

    /**
     * @param hideNumberCulledAtWean the hideNumberCulledAtWean to set
     */
    public void setHideNumberCulledAtWean(boolean hideNumberCulledAtWean) {
        this.hideNumberCulledAtWean = hideNumberCulledAtWean;
    }

    /**
     * @return the hideNumberMissingAtWean
     */
    public boolean isHideNumberMissingAtWean() {
        return hideNumberMissingAtWean;
    }

    /**
     * @param hideNumberMissingAtWean the hideNumberMissingAtWean to set
     */
    public void setHideNumberMissingAtWean(boolean hideNumberMissingAtWean) {
        this.hideNumberMissingAtWean = hideNumberMissingAtWean;
    }

    /**
     * @return the hideLitterType
     */
    public boolean isHideLitterType() {
        return hideLitterType;
    }

    /**
     * @param hideLitterType the hideLitterType to set
     */
    public void setHideLitterType(boolean hideLitterType) {
        this.hideLitterType = hideLitterType;
    }

    /**
     * @return the hideLitterComments
     */
    public boolean isHideLitterComments() {
        return hideLitterComments;
    }

    /**
     * @param hideLitterComments the hideLitterComments to set
     */
    public void setHideLitterComments(boolean hideLitterComments) {
        this.hideLitterComments = hideLitterComments;
    }

    /**
     * @return the litterProtocolEntity
     */
    public CvMouseProtocolEntity getLitterProtocolEntity() {
        return litterProtocolEntity;
    }

    /**
     * @param litterProtocolEntity the litterProtocolEntity to set
     */
    public void setLitterProtocolEntity(CvMouseProtocolEntity litterProtocolEntity) {
        this.litterProtocolEntity = litterProtocolEntity;
    }

    /**
     * @return the litterOriginEntity
     */
    public CvMouseOriginEntity getLitterOriginEntity() {
        return litterOriginEntity;
    }

    /**
     * @param litterOriginEntity the litterOriginEntity to set
     */
    public void setLitterOriginEntity(CvMouseOriginEntity litterOriginEntity) {
        this.litterOriginEntity = litterOriginEntity;
    }

    /**
     * @return the hideLitterProtocol
     */
    public boolean isHideLitterProtocol() {
        return hideLitterProtocol;
    }

    /**
     * @param hideLitterProtocol the hideLitterProtocol to set
     */
    public void setHideLitterProtocol(boolean hideLitterProtocol) {
        this.hideLitterProtocol = hideLitterProtocol;
    }

    /**
     * @return the hideLitterOrigin
     */
    public boolean isHideLitterOrigin() {
        return hideLitterOrigin;
    }

    /**
     * @param hideLitterOrigin the hideLitterOrigin to set
     */
    public void setHideLitterOrigin(boolean hideLitterOrigin) {
        this.hideLitterOrigin = hideLitterOrigin;
    }

    /**
     * @return the hideLitterUseSchedules
     */
    public boolean isHideLitterUseSchedules() {
        return hideLitterUseSchedules;
    }

    /**
     * @param hideLitterUseSchedules the hideLitterUseSchedules to set
     */
    public void setHideLitterUseSchedules(boolean hideLitterUseSchedules) {
        this.hideLitterUseSchedules = hideLitterUseSchedules;
    }

    /**
     * @return the hideLeavePupsInMatingPen
     */
    public boolean isHideLeavePupsInMatingPen() {
        return hideLeavePupsInMatingPen;
    }

    /**
     * @param hideLeavePupsInMatingPen the hideLeavePupsInMatingPen to set
     */
    public void setHideLeavePupsInMatingPen(boolean hideLeavePupsInMatingPen) {
        this.hideLeavePupsInMatingPen = hideLeavePupsInMatingPen;
    }

    /**
     * @return the newLitterProtocolIdDefault
     */
    public String getNewLitterProtocolIdDefault() {
        return newLitterProtocolIdDefault;
    }

    /**
     * @param newLitterProtocolIdDefault the newLitterProtocolIdDefault to set
     */
    public void setNewLitterProtocolIdDefault(String newLitterProtocolIdDefault) {
        this.newLitterProtocolIdDefault = newLitterProtocolIdDefault;
    }

    /**
     * @return the newLitterOriginDefault
     */
    public String getNewLitterOriginDefault() {
        return newLitterOriginDefault;
    }

    /**
     * @param newLitterOriginDefault the newLitterOriginDefault to set
     */
    public void setNewLitterOriginDefault(String newLitterOriginDefault) {
        this.newLitterOriginDefault = newLitterOriginDefault;
    }

    /**
     * @return the leavePupsInMatingPen
     */
    public String getLeavePupsInMatingPen() {
        return leavePupsInMatingPen;
    }

    /**
     * @param leavePupsInMatingPen the leavePupsInMatingPen to set
     */
    public void setLeavePupsInMatingPen(String leavePupsInMatingPen) {
        this.leavePupsInMatingPen = leavePupsInMatingPen;
    }

    /**
     * @return the newLeavePupsInMatingPenDefault
     */
    public String getNewLeavePupsInMatingPenDefault() {
        return newLeavePupsInMatingPenDefault;
    }

    /**
     * @param newLeavePupsInMatingPenDefault the newLeavePupsInMatingPenDefault to set
     */
    public void setNewLeavePupsInMatingPenDefault(String newLeavePupsInMatingPenDefault) {
        this.newLeavePupsInMatingPenDefault = newLeavePupsInMatingPenDefault;
    }
}
