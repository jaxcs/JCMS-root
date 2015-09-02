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
import jcms.integrationtier.dto.AdminContainerStatusDTO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminContainerStatusBean extends AdminBean {
    private ArrayList<AdminContainerStatusDTO> vocabularyList       = null;
    private CVAdministrationDAO         cvAdministrationDAO     = new CVAdministrationDAO();

    public AdminContainerStatusBean() {
        this.clearSessionVariables();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminContainerStatusDTO dto = new AdminContainerStatusDTO();
            dto.setContainerStatusKey("0");
            dto.setContainerStatus("");
            dto.setBillable(true);
            dto.setVersion("0");
            this.vocabularyList.add(0, dto);
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel current action to add a new vocabulary. " ;
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

    public void deleteAction() {
        String strMessage = "";
        if (!this.getIsDirty()) {
            String rowIndex = getParamRowIndex();
            AdminContainerStatusDTO dto = (AdminContainerStatusDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
            this.setSessionVariables();
            try {
                this.cvAdministrationDAO.deleteContainerStatusVocabulary(dto);
            } catch (SQLException ex) {
                strMessage = "Delete failed, please refresh page and try again.  " + ex ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Delete Failed", strMessage));
            }
            this.setIsDirty(false);
            this.clearSessionVariables();
            this.reloadSelectItemWrapper();
            this.loadVocabularyAction();
        } else {
            addToMessageQueue(dirtyMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Dirty Row", dirtyMessage));
        }
    }

    public String saveAction() {
        if (this.getIsDirty()) {
            if (saveVocabulary()) {
                this.setIsDirty(false);
                this.clearSessionVariables();
                this.getFilterBO().clearFilters();
                this.reloadSelectItemWrapper();
                this.loadVocabularyAction();
            }
        }
        return null;
    }

    public void loadVocabularyAction() {
        setVocabularyDataModel(new ListDataModel());
        this.vocabularyList = cvAdministrationDAO.getContainerStatusVocabulary();
        getVocabularyDataModel().setWrappedData(this.vocabularyList);
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminContainerStatusDTO dto = (AdminContainerStatusDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
        if (isValidRow(dto) && !isDuplicate(dto) && !this.containsRestrictedCharacters(dto.getContainerStatus())) {
            try {
                success = (this.cvAdministrationDAO.saveContainerStatusVocabulary(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (AdminContainerStatusDTO dto) {
        Boolean isValid = true;
        if (dto.getContainerStatus().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Cage Status and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getContainerStatus().length() > 8) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Cage Status of 8 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid;
    }
    
    private Boolean isDuplicate(AdminContainerStatusDTO sourceDTO) {
        Integer count = 0;
        for(AdminContainerStatusDTO dto : this.vocabularyList) {
            if (sourceDTO.getContainerStatus().trim().equalsIgnoreCase(dto.getContainerStatus().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique Cage Status and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
    
}
