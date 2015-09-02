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
import jcms.integrationtier.dao.PhenotypeTermDAO;
import jcms.integrationtier.dto.PhenotypeTermDTO;

/**
 *
 * @author bas
 */
@ManagedBean
@SessionScoped
public class AdminPhenotypeBean extends AdminBean {
    private ArrayList<PhenotypeTermDTO> vocabularyList = null;
    private PhenotypeTermDAO phenotypeTermDAO = new PhenotypeTermDAO();

    public AdminPhenotypeBean() {
        this.clearSessionVariables();
        
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            PhenotypeTermDTO dto = new PhenotypeTermDTO();
            dto.setPhenotypeTermKey("0");
            dto.setIsActive(true);
            dto.setVersionNum("0");
            dto.setPhenotypeTermDescription("");
            this.vocabularyList.add(0, dto);
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel current action to add a new phenotype term. " ;
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
            PhenotypeTermDTO dto = (PhenotypeTermDTO) (this.vocabularyList.get(this.vocabularyDataModel.getRowIndex())).getDTO();
            this.setSessionVariables();
            try {
                this.phenotypeTermDAO.deletePhenotypeTerm(dto);
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
        this.vocabularyList = phenotypeTermDAO.getPhenotypeTerms();
        getVocabularyDataModel().setWrappedData(this.vocabularyList);
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        PhenotypeTermDTO dto = (PhenotypeTermDTO) (this.vocabularyList.get(this.vocabularyDataModel.getRowIndex()).getDTO());
        if (isValidRow(dto) && !isDuplicate(dto)) {
            try {
                success = (this.phenotypeTermDAO.savePhenotypeTerm(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Phenotype Term Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (PhenotypeTermDTO dto) {
        Boolean isValid = true;
        if (dto.getPhenotypeTermName().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Phenotype Term.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getPhenotypeTermName().trim().length() > 255) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Phenotype term of 255 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getPhenotypeTermDescription().trim().length() > 255) {
            isValid = false;
            String strMessage = "Value is too long, please enter a description of 255 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid; 
    }
    
    private Boolean isDuplicate(PhenotypeTermDTO sourceDTO) {
        Integer count = 0;
        for(PhenotypeTermDTO dto : this.vocabularyList) {
            if (sourceDTO.getPhenotypeTermName().trim().equalsIgnoreCase(dto.getPhenotypeTermName().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique Phenotype Term.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
    
}