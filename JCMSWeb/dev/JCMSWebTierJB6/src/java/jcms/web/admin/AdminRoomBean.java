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

package jcms.web.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dto.AdminHealthLevelHistoryDTO;
import jcms.integrationtier.dto.AdminRoomDTO;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminRoomBean extends AdminBean {
    private ArrayList<AdminRoomDTO> vocabularyList = null;
    private ArrayList<AdminHealthLevelHistoryDTO> healthLevelHistoryList = null;
    private ListDataModel healthLevelHistoryDataModel = null;
    private CVAdministrationDAO cvAdministrationDAO = new CVAdministrationDAO();
    private AdminRoomDTO roomSearchDTO = new AdminRoomDTO();
    private ExtendedTableBean searchRoomETB = new ExtendedTableBean();
    private AdminRoomDTO selectedRoomDTO = new AdminRoomDTO();
    

    public AdminRoomBean() {
        this.clearSessionVariables();
        loadVocabularyAction();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminRoomDTO dto = new AdminRoomDTO();
            dto.setRoomKey("0");
            dto.setIsActive(true);
            dto.setVersion("0");
            this.vocabularyList.add(0, dto);
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel current action to add a new room. " ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", strMessage));
        }
    }

    public void cancelAction() {
        this.setIsDirty(false);
        this.clearSessionVariables();
        this.loadVocabularyAction();
    }

    public void editAction() {
        if (!this.getIsDirty()) {
            this.setIsDirty(true);
            this.setSessionVariables();
            this.loadVocabularyAction();
        } else {
            addToMessageQueue(dirtyMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", dirtyMessage));
        }
    }

    public void deleteHealthLevelHistoryAction() {
        String strMessage = "";
        if (!this.getIsDirty()) {
            String rowIndex = getParamHistoryRowIndex();
            AdminHealthLevelHistoryDTO dto = (AdminHealthLevelHistoryDTO) (this.healthLevelHistoryList.get(Integer.parseInt(rowIndex))).getDTO();
            try {
                this.cvAdministrationDAO.deleteHealthLevelHistory(dto);
            } catch (SQLException ex) {
                strMessage = "Delete health level history failed, please refresh page and try again.  " + ex ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Delete Failed", strMessage));
            }
            this.loadVocabularyAction();
            this.loadHealthLevelHistoryAction(dto.getRoomKey());
        } else {
            addToMessageQueue(dirtyMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Dirty Row", dirtyMessage));
        }
    }

    private String getParamHistoryRowIndex() {
        return this.getRequestParameter("paramHistoryRowIndex").toString();
    }
    public void saveAction() {
        if (this.getIsDirty()) {
            if (saveVocabulary()) {
                this.setIsDirty(false);
                this.clearSessionVariables();
                this.getFilterBO().clearFilters();
                this.reloadSelectItemWrapper();
                this.loadVocabularyAction();
                this.loadHealthLevelHistoryAction(this.getParamPrimaryKey());
            }
        }
    }

    public void searchRoomAction() {
        this.loadVocabularyAction();
    }
    
    private void clearLocalVariables() {
        if (healthLevelHistoryList != null)
            healthLevelHistoryList.clear();
        if (searchRoomETB != null)
            searchRoomETB.clearSelection();
    }
    
    @Override
    public void loadVocabularyMenuAction() {
        this.setIsDirty(false);
        this.clearSessionVariables();
        this.clearLocalVariables();
        // reset vocabularies to include new items and edits
        this.setSelectItemWrapper(new SelectItemWrapper());
    }
    
    public void loadVocabularyAction() {
        this.setVocabularyDataModel(new ListDataModel());
        this.vocabularyList = cvAdministrationDAO.getRooms(this.getRoomSearchDTO());
        this.getVocabularyDataModel().setWrappedData(this.vocabularyList);
        this.resetHistoryAction();
    }
    
    public void loadHealthLevelHistoryAction(String roomKey) {
        this.setHealthLevelHistoryDataModel(new ListDataModel());
        this.healthLevelHistoryList = cvAdministrationDAO.getHealthLevelHistory(roomKey);
        this.getHealthLevelHistoryDataModel().setWrappedData(this.healthLevelHistoryList);
    }

    public void showRoomHistoryAction(){
        System.out.println("Room key is "+ this.getSelectedRowKey() );
        String rowKey = this.getSelectedRowKey();
        if (rowKey.length() > 0) {
            this.loadHealthLevelHistoryAction(rowKey);
            for (AdminRoomDTO dto : this.vocabularyList) {
                if (dto.getRoomKey().equalsIgnoreCase(rowKey)) {
                    this.getSelectedRoomDTO().setRoomName(dto.getRoomName());
                    break;
                }
            }
        }
    }
    
    public void resetHistoryAction() {
        this.healthLevelHistoryDataModel = new ListDataModel();
        this.getSelectedRoomDTO().setRoomName("");
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminRoomDTO dto = (AdminRoomDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex)));
        if (isValidRow(dto) && !isDuplicate(dto) && !isSameDate(dto)) {
            try {
                dto.setModifiedBy(this.getSessionParameter("loggedUser").toString());
                success = (this.cvAdministrationDAO.saveRoom(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (AdminRoomDTO dto) {
        Boolean isValid = true;
        if (dto.getRoomName().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Room Name.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getHealthLevelHistoryDTO().getHealthLevel().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please select a Health Level.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getHealthLevelHistoryDTO().getStartDate() == null) {
            isValid = false;
            String strMessage = "Required value missing, please select a Start Date.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getRoomName().length() > 8) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Room Name of 8 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid;
    }
    
    private Boolean isDuplicate(AdminRoomDTO sourceDTO) {
        Integer count = 0;
        for(AdminRoomDTO dto : this.vocabularyList) {
            if (sourceDTO.getRoomName().trim().equalsIgnoreCase(dto.getRoomName().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique Room Name and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
    
    private Boolean isSameDate(AdminRoomDTO sourceDTO) {
        Boolean sameDate = false;
  
        //changing to check the date only when the health level is changed
        if (this.isHealthLevelChange(sourceDTO)) {
            sameDate = cvAdministrationDAO.isSameDate(sourceDTO);
            if (sameDate) {
                String strMessage = "A change in health level requires a unique start date.  Select a different start date than " + sourceDTO.getHealthLevelHistoryDTO().getStartDate();
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
                this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
            }
        }
        
        return sameDate;
    }
    
    //NOTE - this is not used. If it was used, it is checking for a duplicate date, so needs modification to find if it is the maximum date.
    private Boolean isMaxDate(AdminRoomDTO sourceDTO) {
        Boolean maxDate = cvAdministrationDAO.isSameDate(sourceDTO);
        if (maxDate) {
            String strMessage = "Duplicate health level for this day.  Select a different start date than " + sourceDTO.getHealthLevelHistoryDTO().getStartDate();
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return maxDate;
    }
    
    private Boolean isHealthLevelChange(AdminRoomDTO sourceDTO) {
        Boolean healthLevelChange = false;
        
        //Have to look up the original health level term for this room
        //and see if it matches the health level term the user has set on screen.
 
        //However, if we have not yet added the room, the key is zero, skip
        if (sourceDTO.getRoomKey().equalsIgnoreCase("0")) {
            return healthLevelChange;
        }
        
        //Find the original room
        AdminRoomDTO filterDTO = new AdminRoomDTO();
        filterDTO.setRoomKey(sourceDTO.getRoomKey());
        AdminRoomDTO originalRoom = (cvAdministrationDAO.getRooms(filterDTO)).get(0);
        //Find the health level associated with the original room and compare to screen value
        if (originalRoom.getHealthLevelHistoryDTO().getHealthLevel().equalsIgnoreCase(sourceDTO.getHealthLevelHistoryDTO().getHealthLevel())) {
            healthLevelChange = false;
        } else {
            healthLevelChange = true;
        }
        
        return healthLevelChange;
    }
    
    private Boolean isRoomNameChange(AdminRoomDTO sourceDTO) {
        Boolean roomNameChange = false;
        if (sourceDTO.getRoomKey().equalsIgnoreCase("0")) {
            return roomNameChange;
        }
        AdminRoomDTO filterDTO = new AdminRoomDTO();
        filterDTO.setRoomKey(sourceDTO.getRoomKey());
        filterDTO.setIsActive(null);
        AdminRoomDTO originalRoom = (cvAdministrationDAO.getRooms(filterDTO)).get(0);
        if (!sourceDTO.getRoomName().equalsIgnoreCase(originalRoom.getRoomName())) {
            roomNameChange = true;
        }
            
        return roomNameChange;
    }
    
    public void clearSearchAction() {
        this.getRoomSearchDTO().clear();
        this.setVocabularyDataModel(new ListDataModel());
    }
    
    public Boolean getShowCount() {
        if (this.getVocabularyDataModel() != null) {
            Integer c = this.getVocabularyDataModel().getRowCount();
            Boolean b = vocabularyDataModel.getRowCount() != -1;
        }
        return (vocabularyDataModel != null && vocabularyDataModel.getRowCount() != -1);
    }

    /**
     * @return the roomSearchDTO
     */
    public AdminRoomDTO getRoomSearchDTO() {
        return roomSearchDTO;
    }

    /**
     * @param roomSearchDTO the roomSearchDTO to set
     */
    public void setRoomSearchDTO(AdminRoomDTO roomSearchDTO) {
        this.roomSearchDTO = roomSearchDTO;
    }

    /**
     * @return the searchRoomETB
     */
    public ExtendedTableBean getSearchRoomETB() {
        return searchRoomETB;
    }

    /**
     * @param searchRoomETB the searchRoomETB to set
     */
    public void setSearchRoomETB(ExtendedTableBean searchRoomETB) {
        this.searchRoomETB = searchRoomETB;
    }

    /**
     * @return the healthLevelHistoryDataModel
     */
    public ListDataModel getHealthLevelHistoryDataModel() {
        return healthLevelHistoryDataModel;
    }

    /**
     * @param healthLevelHistoryDataModel the healthLevelHistoryDataModel to set
     */
    public void setHealthLevelHistoryDataModel(ListDataModel healthLevelHistoryDataModel) {
        this.healthLevelHistoryDataModel = healthLevelHistoryDataModel;
    }

    /**
     * @return the selectedRoomDTO
     */
    public AdminRoomDTO getSelectedRoomDTO() {
        return selectedRoomDTO;
    }

}
