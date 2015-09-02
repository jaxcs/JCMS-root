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
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.MouseUseDTO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminMouseUseBean extends AdminBean {
    private ArrayList<MouseUseDTO> vocabularyList = null;
    private MouseUseDAO mouseUseDAO = new MouseUseDAO();

    public AdminMouseUseBean() {
        this.clearSessionVariables();
        
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            MouseUseDTO dto = new MouseUseDTO();
            dto.setMouseUse_key("0");
            dto.setIsActive(true);
            dto.setVersion("0");
            dto.setD10Caption("D10");
            dto.setD1Caption("D1");
            dto.setD2Caption("D2");
            dto.setD3Caption("D3");
            dto.setD4Caption("D4");
            dto.setD5Caption("D5");
            dto.setD6Caption("D6");
            dto.setD7Caption("D7");
            dto.setD8Caption("D8");
            dto.setD9Caption("D9");
            this.vocabularyList.add(0, dto);
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel current action to add a new mouse use. " ;
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
            MouseUseDTO dto = (MouseUseDTO) (this.vocabularyList.get(this.vocabularyDataModel.getRowIndex())).getDTO();
            this.setSessionVariables();
            try {
                this.mouseUseDAO.deleteMouseUse(dto);
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
        this.vocabularyList = mouseUseDAO.getMouseUses();
        getVocabularyDataModel().setWrappedData(this.vocabularyList);
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        MouseUseDTO dto = (MouseUseDTO) (this.vocabularyList.get(this.vocabularyDataModel.getRowIndex()).getDTO());
        if (isValidRow(dto) && !isDuplicate(dto)) {
            try {
                success = (this.mouseUseDAO.saveMouseUse(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Mouse Use Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (MouseUseDTO dto) {
        Boolean isValid = true;
        if (dto.getMouseUse().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Mouse Use.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD1Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D1 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD2Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D2 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD3Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D3 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD4Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D4 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD5Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D5 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD6Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D6 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD7Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D7 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD8Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D8 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD9Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D9 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getD10Caption().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a D10 caption.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        
        if (dto.getMouseUse().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Mouse Use of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getUseDescription().trim().length() > 64) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Description of 64 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD1Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D1 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD2Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D2 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD3Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D3 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD4Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D4 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD5Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D5 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD6Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D6 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD7Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D7 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD8Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D8 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD9Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D9 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getD10Caption().trim().length() > 32) {
            isValid = false;
            String strMessage = "Value is too long, please enter a D10 caption of 32 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid;
    }
    
    private Boolean isDuplicate(MouseUseDTO sourceDTO) {
        Integer count = 0;
        for(MouseUseDTO dto : this.vocabularyList) {
            if (sourceDTO.getMouseUse().trim().equalsIgnoreCase(dto.getMouseUse().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique Mouse Use.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
    
}

