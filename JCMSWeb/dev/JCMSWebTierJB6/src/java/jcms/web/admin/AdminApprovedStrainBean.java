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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.base.ITBaseDTO;
import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dao.LitterDAO;
import jcms.integrationtier.dto.AdminApprovedStrainDTO;
import jcms.integrationtier.dto.AdminStrainDTO;
import jcms.web.common.ExtendedTableBean;

@ManagedBean
@SessionScoped
public class AdminApprovedStrainBean extends AdminBean {
    private ArrayList<AdminApprovedStrainDTO> vocabularyList = null;
    private LitterDAO litterDAO = new LitterDAO();
    private String strainType = "";
    private AdminStrainDTO strainSearchDTO = new AdminStrainDTO();
    private Integer searchResultsCount = 0;
    private ListDataModel<AdminStrainDTO> searchStrainDataModel = new ListDataModel<AdminStrainDTO> ();
    private ExtendedTableBean searchStrainETB = new ExtendedTableBean();
    private String approvedStrainsRowIndex = "";

    // Strain Search
    private CVAdministrationDAO adminDAO = new CVAdministrationDAO();
    
    public AdminApprovedStrainBean() {
        this.clearSessionVariables();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminApprovedStrainDTO dto = new AdminApprovedStrainDTO();
            dto.setApprovedStrainKey("0");
            dto.setIsActive(true);
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
            AdminApprovedStrainDTO dto = (AdminApprovedStrainDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
            this.setSessionVariables();
            try {
                this.litterDAO.deleteApprovedStrainVocabulary(dto);
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
        try {
            setVocabularyDataModel(new ListDataModel());
            this.vocabularyList = litterDAO.getApprovedStrains();
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
        } catch (SQLException ex) {
            String strMessage = "Failed to load approved strains.  " + ex ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
        }
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminApprovedStrainDTO dto = (AdminApprovedStrainDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
        if (isValidRow(dto) && !isDuplicate(dto)) {
            try {
                success = (this.litterDAO.saveApprovedStrainVocabulary(dto) > 0);
            } catch (SQLException ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
    }
    
    private Boolean isValidRow (AdminApprovedStrainDTO dto) {
        Boolean isValid = true;
        if (dto.getDamStrain().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please select a Dam Strain.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getSireStrain().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please select a Sire Strain.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getLitterStrain().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please select a Litter Strain.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        return isValid;
    }
    
    private Boolean isDuplicate(AdminApprovedStrainDTO sourceDTO) {
        Integer count = 0;
        for(AdminApprovedStrainDTO dto : this.vocabularyList) {
            if (sourceDTO.getDamStrainKey().equalsIgnoreCase(dto.getDamStrainKey()) && 
                sourceDTO.getSireStrainKey().equalsIgnoreCase(dto.getSireStrainKey()) && 
                sourceDTO.getLitterStrainKey().equalsIgnoreCase(dto.getLitterStrainKey()) )
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate mating found, change this set of strains or cancel the operation.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return (count > 1);
    }
        
    // ************************
    // Strain Search Code Begin
    // ************************
    public void showSelectStrainClickAction() {
        this.setApprovedStrainsRowIndex(this.getRequestParameter("paramRowIndex"));
        String s = this.getRequestParameter("paramStrainType").toString();
        this.setStrainType(s);
    }

    public void searchApprovedStrainsPopupAction() {
        ArrayList<AdminStrainDTO> strains = null;
        if (isValidSearch(this.getStrainSearchDTO())) {
            this.setSearchStrainDataModel(new ListDataModel());
            this.getStrainSearchDTO().setIsActive(true);
            strains = adminDAO.getStrains(this.getStrainSearchDTO());
            this.getSearchStrainDataModel().setWrappedData(strains);
        }
    }
    public void clearApprovedStrainsPopupAction() {
        this.strainSearchDTO.clear();
        this.setSearchStrainDataModel(new ListDataModel());
    }
    
    public Boolean getShowCount() {
        return (searchStrainDataModel != null && searchStrainDataModel.getRowCount() != -1);
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
            
            if (dto.getStrainName().length() > 0 || dto.getJrNumFrom().length() > 0 || dto.getJrNumTo().length() > 0) {
                String s = "";
            } else {
                isValid = false;
                String strMessage = "Search criteria required, please enter a strain name or stock number";
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
                this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
            }
        }
            
        return isValid;
    }

    public void selectStrainPopupOKAction() {
        List<ITBaseDTO> strainSelection = (List<ITBaseDTO>) searchStrainETB.getSelectionDTOItems();
        AdminStrainDTO strainDTO = null;
        
        if (strainSelection.size() == 1) {
            strainDTO = (AdminStrainDTO) strainSelection.get(0);
            
            String rowIndex = this.getApprovedStrainsRowIndex();
            AdminApprovedStrainDTO dto = (AdminApprovedStrainDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();

            if (this.getStrainType().equalsIgnoreCase("Dam")) {
                dto.setDamStrainKey(strainDTO.getStrainKey());
                dto.setDamStrain(strainDTO.getStrainName());
            } else if (this.getStrainType().equalsIgnoreCase("Sire")) {
                dto.setSireStrainKey(strainDTO.getStrainKey());
                dto.setSireStrain(strainDTO.getStrainName());
            } else if (this.getStrainType().equalsIgnoreCase("Litter")) {
                dto.setLitterStrainKey(strainDTO.getStrainKey());
                dto.setLitterStrain(strainDTO.getStrainName());
            }

            setVocabularyDataModel(new ListDataModel());
            getVocabularyDataModel().setWrappedData(this.vocabularyList);
        } else {
            String strMessage = "Select a "+ this.getStrainType() +" strain or close search" ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
    }
    
    
    
    // ************************
    // Strain Search Code END
    // ************************

    /**
     * @return the strainType
     */
    public String getStrainType() {
        return strainType;
    }

    /**
     * @param strainType the strainType to set
     */
    public void setStrainType(String strainType) {
        this.strainType = strainType;
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
     * @return the searchResultsCount
     */
    public Integer getSearchResultsCount() {
        return searchResultsCount;
    }

    /**
     * @param searchResultsCount the searchResultsCount to set
     */
    public void setSearchResultsCount(Integer searchResultsCount) {
        this.searchResultsCount = searchResultsCount;
    }

    /**
     * @return the searchStrainDataModel
     */
    public ListDataModel<AdminStrainDTO> getSearchStrainDataModel() {
        return searchStrainDataModel;
    }

    /**
     * @param searchStrainDataModel the searchStrainDataModel to set
     */
    public void setSearchStrainDataModel(ListDataModel<AdminStrainDTO> searchStrainDataModel) {
        this.searchStrainDataModel = searchStrainDataModel;
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

    /**
     * @return the approvedStrainsRowIndex
     */
    public String getApprovedStrainsRowIndex() {
        return approvedStrainsRowIndex;
    }

    /**
     * @param approvedStrainsRowIndex the approvedStrainsRowIndex to set
     */
    public void setApprovedStrainsRowIndex(String approvedStrainsRowIndex) {
        this.approvedStrainsRowIndex = approvedStrainsRowIndex;
    }
    
}
