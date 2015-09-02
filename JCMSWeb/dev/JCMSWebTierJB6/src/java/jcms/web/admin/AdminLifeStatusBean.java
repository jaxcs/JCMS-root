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
import jcms.integrationtier.dto.AdminLifeStatusDTO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminLifeStatusBean extends AdminBean {
    private ArrayList<AdminLifeStatusDTO> vocabularyList       = null;
    private CVAdministrationDAO         cvAdministrationDAO     = new CVAdministrationDAO();

    public AdminLifeStatusBean() {
        this.clearSessionVariables();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminLifeStatusDTO dto = new AdminLifeStatusDTO();
            dto.setLifeStatusKey("0");
            dto.setExitStatus(false);
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
            AdminLifeStatusDTO dto = (AdminLifeStatusDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
            this.setSessionVariables();
            try {
                this.cvAdministrationDAO.deleteLifeStatusVocabulary(dto);
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
        this.vocabularyList = cvAdministrationDAO.getLifeStatusVocabulary();
        getVocabularyDataModel().setWrappedData(this.vocabularyList);
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminLifeStatusDTO dto = (AdminLifeStatusDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
        if (isValidRow(dto) && !isDuplicate(dto) && !this.containsRestrictedCharacters(dto.getLifeStatus())) {
            try {
                success = (this.cvAdministrationDAO.saveLifeStatusVocabulary(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (AdminLifeStatusDTO dto) {
        Boolean isValid = true;
        if (dto.getLifeStatus().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Life Status and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getLifeStatus().length() > 2) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Life Status of 2 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getDescription().length() > 64) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Description of 64 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        
        return isValid;
    }
    
    private Boolean isDuplicate(AdminLifeStatusDTO sourceDTO) {
        Integer count = 0;
        for(AdminLifeStatusDTO dto : this.vocabularyList) {
            if (sourceDTO.getLifeStatus().trim().equalsIgnoreCase(dto.getLifeStatus().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique Life Status and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
    
    
}
