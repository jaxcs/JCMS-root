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
import jcms.integrationtier.dto.AdminStrainDTO;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminStrainBean extends AdminBean {
    private ArrayList<AdminStrainDTO> vocabularyList = null;
    private CVAdministrationDAO cvAdministrationDAO = new CVAdministrationDAO();
    private AdminStrainDTO strainSearchDTO = new AdminStrainDTO();
    private ExtendedTableBean searchStrainETB = new ExtendedTableBean();

    public AdminStrainBean() {
        this.clearSessionVariables();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminStrainDTO dto = new AdminStrainDTO();
            dto.setStrainKey("0");
            dto.setIsActive(true);
            dto.setStrainStatus("A");
            dto.setFeNumEmbryos("0");
            dto.setFoNumFemales("0");
            dto.setFsNumMales("0");
            dto.setVersion("0");
            this.vocabularyList.add(0, dto);
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel changes. " ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", strMessage));
        }
    }
    
    public void cancelAction() {
        this.setIsDirty(false);
        this.clearSessionVariables();
        this.loadVocabularyAction();
    }
    
    public String loadVocabularyStrainAction(){
        loadVocabularyMenuAction();
        return "adminStrainView";
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
            AdminStrainDTO dto = (AdminStrainDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
            this.setSessionVariables();
            try {
                this.cvAdministrationDAO.deleteStrainVocabulary(dto);
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
            AdminStrainDTO dto = (AdminStrainDTO) (this.vocabularyList.get(Integer.parseInt(this.getParamRowIndex()))).getDTO();
            Boolean moveFirst = (dto.getStrainKey().isEmpty() || dto.getStrainKey().equalsIgnoreCase("0"));
            if (saveVocabulary()) {
                this.setIsDirty(false);
                this.clearSessionVariables();
                this.getFilterBO().clearFilters();
                this.reloadSelectItemWrapper();
                this.loadVocabularyAction();
                if (moveFirst) {
                    moveFirst(dto);
                }
            }
        }
        return null;
    }
    
    private void moveFirst(AdminStrainDTO dto) {
        int index = 0;
        // loop through vocabularyList to find newely added row
        for (AdminStrainDTO d: this.vocabularyList) {
            if (d.getStrainName().equalsIgnoreCase(dto.getStrainName())) {
                break;
            } else {
                index++;
            }
        }
        if (index > 0 && index < this.vocabularyList.size()) {
            // move it back to the first row
            AdminStrainDTO mvDTO = this.vocabularyList.get(index);
            this.vocabularyList.remove(index);
            this.vocabularyList.add(0, mvDTO);
        }
        
    }

    public void searchStrainAction() {
        this.loadVocabularyAction();
    }
    
    public void loadVocabularyAction() {
        ArrayList<AdminStrainDTO> strains = null;
        if (isValidSearch(this.getStrainSearchDTO())) {
            this.setVocabularyDataModel(new ListDataModel());
            this.getStrainSearchDTO().setUser(this.getUserEntity());
            this.vocabularyList = cvAdministrationDAO.getStrains(this.getStrainSearchDTO());
            this.getVocabularyDataModel().setWrappedData(this.vocabularyList);
        }
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminStrainDTO dto = (AdminStrainDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
        if (isValidRow(dto) && !isDuplicate(dto)) {
            try {
                success = (this.cvAdministrationDAO.saveStrainVocabulary(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (AdminStrainDTO dto) {
        Boolean isValid = true;
        
        if (dto.getVersion().length() == 0) {
            dto.setVersion("0");
        }
        
        if (dto.getStrainName().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a Strain Name";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getStrainStatus().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please select a Strain Status";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getStrainName().length() > 64) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Strain Name of 64 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getNickName().length() > 128) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Nick Name of 128 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getFormalName().length() > 128) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Formal Name of 128 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getTagMin().length() > 16) {
            isValid = false;
            String strMessage = "Value is too long, please enter a minimum tag of 16 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getTagMax().length() > 16) {
            isValid = false;
            String strMessage = "Value is too long, please enter a maximum tag of 16 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        if (dto.getLastTag().length() > 16) {
            isValid = false;
            String strMessage = "Value is too long, please enter a last tag of 16 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        
        if (dto.getFeNumEmbryos().length() > 0 && !isNumeric(dto.getFeNumEmbryos())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for Frozen Embryos";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getFsNumMales().length() > 0 && !isNumeric(dto.getFsNumMales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for Frozen Sperm";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getFoNumFemales().length() > 0 && !isNumeric(dto.getFoNumFemales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for Frozen Ovaries";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        
        if (dto.getJrNum().length() > 0 && !isNumeric(dto.getJrNum())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for Stock Number (a.k.a JR Number)";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }

        if (dto.getLineViabilityRedMaxAgeFemales().length() > 0 && !isNumeric(dto.getLineViabilityRedMaxAgeFemales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for redline maximum age of females.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityRedMaxAgeMales().length() > 0 && !isNumeric(dto.getLineViabilityRedMaxAgeMales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for redline maximum age of males.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityRedMinNumFemales().length() > 0 && !isNumeric(dto.getLineViabilityRedMinNumFemales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for redline minimum number of females.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityRedMinNumMales().length() > 0 && !isNumeric(dto.getLineViabilityRedMinNumMales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for redline minimum number of males.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }

        if (dto.getLineViabilityYellowMaxAgeFemales().length() > 0 && !isNumeric(dto.getLineViabilityYellowMaxAgeFemales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for yellowline maximum age of females.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityYellowMaxAgeMales().length() > 0 && !isNumeric(dto.getLineViabilityYellowMaxAgeMales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for yellowline maximum age of males.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityYellowMinNumFemales().length() > 0 && !isNumeric(dto.getLineViabilityYellowMinNumFemales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for yellowline minimum number of females.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        if (dto.getLineViabilityYellowMinNumMales().length() > 0 && !isNumeric(dto.getLineViabilityYellowMinNumMales())) {
            isValid = false;
            String strMessage = "Invalid data type, enter a number for yellowline minimum number of males.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Invalid Data Type", strMessage));
        }
        
        return isValid;
    }
    
    private Boolean isDuplicate(AdminStrainDTO sourceDTO) {
        Boolean isDuplicate = this.cvAdministrationDAO.isDuplicate(sourceDTO.getStrainKey(), sourceDTO.getStrainName()) ;
        if (isDuplicate) {
            String strMessage = "Duplicate entry, please enter a unique Strain Name.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        return isDuplicate;
        
    }

    private Boolean isValidSearch(AdminStrainDTO dto) {
        Boolean isValid = true;
        
        if (dto != null) {
            if (dto.getJrNumFrom().length() > 0 && ! isNumeric(dto.getJrNumFrom())) {
                isValid = false;
                String strMessage = "Invalid search type, from stock number type is invalid.  Please enter a numeric value and search again.";
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
                this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
            }
            if (dto.getJrNumTo().length() > 0 && ! isNumeric(dto.getJrNumTo())) {
                isValid = false;
                String strMessage = "Invalid search type, to stock number type is invalid.  Please enter a numeric value and search again.";
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
                this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
            }
            
        }
            
        return isValid;
    }

    public void clearSearchAction() {
        this.strainSearchDTO.clear();
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
     * @return the strainSearchDTO
     */
    public AdminStrainDTO getStrainSearchDTO() {
        return strainSearchDTO;
    }

    /**
     * @param strainSearchDTO the strainSearchDTO to set
     */
    public void setStrainSearchDTO(AdminStrainDTO strainSearchDTO) {
        this.strainSearchDTO = strainSearchDTO;
    }

    /**
     * @return the searchStrainETB
     */
    public ExtendedTableBean getSearchStrainETB() {
        return searchStrainETB;
    }

    /**
     * @param searchStrainETB the searchStrainETB to set
     */
    public void setSearchStrainETB(ExtendedTableBean searchStrainETB) {
        this.searchStrainETB = searchStrainETB;
    }

    
}
