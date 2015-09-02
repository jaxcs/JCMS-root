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
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.dto.AdminGeneralDTO;
import jcms.integrationtier.dto.SampleClassDTO;
import jcms.integrationtier.dto.ControlledVocabularyDTO;
import javax.faces.model.SelectItem;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminGeneralBean extends AdminBean {
    private ControlledVocabularyDTO controlledVocabularyDTO = null;
    private String                  controlledVocabularyKey = "";
    private ArrayList<AdminGeneralDTO> vocabularyList       = null;
    private CVAdministrationDAO     cvAdministrationDAO     = new CVAdministrationDAO();
    private SampleDAO               sampleDAO               = new SampleDAO();
    private String                  tableName               = "";
    private ArrayList<SelectItem>   items                   = null;

    public AdminGeneralBean() {
        this.clearSessionVariables();
    }
    
    public void addAction() {
        if (!this.getIsDirty()) {
            this.getFilterBO().clearFilters();
            this.putSessionParameter(this.ROWINDEX, "0");
            this.putSessionParameter(this.PRIMARYKEY, "0");
            AdminGeneralDTO dto = new AdminGeneralDTO();
            dto.setPrimaryKey("0");
            dto.setCreatedBy(this.getUserEntity().getUserName());
            dto.setModifiedBy(this.getUserEntity().getUserName());
            dto.setVersion("0");
            this.vocabularyList.add(0, dto);
            vocabularyDataModel.setWrappedData(this.vocabularyList);
            this.setIsDirty(true);
        } else {
            String strMessage = "Please save or cancel current action to add a new vocabulary. " ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", strMessage));
        }
    }

    public void cancelAction() {
        this.clearSessionVariables();
        this.setIsDirty(false);
        this.loadVocabulary();
    }

    public void editAction() {
        if (!this.getIsDirty()) {
            this.setIsDirty(true);
            this.setSessionVariables();
            this.loadVocabulary();
        } else {
            addToMessageQueue(dirtyMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate", dirtyMessage));
        }
    }

    public void deleteAction() {
        String strMessage = "";
        if (!this.getIsDirty()) {
            String rowIndex = getParamRowIndex();
            AdminGeneralDTO dto = (AdminGeneralDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
            this.setSessionVariables();
            try {
                this.cvAdministrationDAO.deleteGeneralVocabulary(dto, controlledVocabularyDTO);
            } catch (SQLException ex) {
                strMessage = "Delete failed, please refresh page and try again.  " + ex ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Delete Failed", strMessage));
            }
            this.setIsDirty(false);
            this.clearSessionVariables();
            this.reloadSelectItemWrapper();
            this.loadVocabulary();
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
                this.loadVocabulary();
            }
        }
        return null;
    }

    public void loadVocabularyAction() {
        if (this.getControlledVocabularyKey().length() > 0) {
            ControlledVocabularyDTO dto = new CVAdministrationDAO().getControlledVocabularyByKey(this.getControlledVocabularyKey());
            this.setControlledVocabularyDTO(dto);
        }
        else {
            this.setControlledVocabularyDTO(null);
        }
        this.clearSessionVariables();
        this.setIsDirty(false);
        loadVocabulary();
    }

    private void loadVocabulary() {
        this.resetSortAndFilter();
        vocabularyDataModel = new ListDataModel();
        if (this.getControlledVocabularyDTO() != null) {
            this.vocabularyList = cvAdministrationDAO.getGeneralVocabulary(controlledVocabularyDTO);
            vocabularyDataModel.setWrappedData(this.vocabularyList);
        }
    }
    
    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        AdminGeneralDTO dto = (AdminGeneralDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex))).getDTO();
        if(!controlledVocabularyDTO.getSubViewName().equals("SAMPLES")){
            if (isValidRow(dto) && !isDuplicate(dto) && !this.containsRestrictedCharacters(dto.getColumnOneValue())) {
                try {
                    success = (this.cvAdministrationDAO.saveGeneralVocabulary(dto, controlledVocabularyDTO) > 0);
                } catch (SQLException ex) {
                    String strMessage = "Save failed, please cancel and try again. " ;
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
                }
            }
        }
        else{
            if (isValidRow(dto) && !isDoubleDuplicate(dto) && !this.containsRestrictedCharacters(dto.getColumnOneValue())) {
                try {
                    success = (this.cvAdministrationDAO.saveGeneralVocabulary(dto, controlledVocabularyDTO) > 0);
                } catch (SQLException ex) {
                    String strMessage = "Save failed, please cancel and try again. " ;
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
                }
            }
        }
        return success;
    }
    
    private Boolean isValidRow (AdminGeneralDTO dto) {
        Boolean isValid = true;
        if (dto.getColumnOneValue().trim().length() == 0) {
            isValid = false;
            String strMessage = "Required value missing, please enter a "+ controlledVocabularyDTO.getDisplayColumnOneName() +" and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (this.getTableName().equalsIgnoreCase("cv_StrainStatus") && dto.getColumnOneValue().length() > 1) {
            isValid = false;
            String strMessage = "Value is too long, please enter a Strain Status of 1 character.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid;
    }

    private Boolean isDuplicate(AdminGeneralDTO sourceDTO) {
        Integer count = 0;
        for(AdminGeneralDTO dto : this.vocabularyList) {
            if (sourceDTO.getColumnOneValue().trim().equalsIgnoreCase(dto.getColumnOneValue().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique " + controlledVocabularyDTO.getDisplayColumnOneName() +" and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        return (count > 1);
    }
    
    private Boolean isDoubleDuplicate(AdminGeneralDTO sourceDTO) {
        Integer count = 0;
        //for some sample tables preservation type is not unique, but combination of two fields (preservation type and sample class for instance) must be
        for(AdminGeneralDTO dto : this.vocabularyList) {
            if (sourceDTO.getColumnOneValue().trim().equalsIgnoreCase(dto.getColumnOneValue().trim()) 
                    && sourceDTO.getColumnTwoValue().trim().equalsIgnoreCase(dto.getColumnTwoValue().trim()))
                count++;
        }
        if (count > 1) {
            String strMessage = "Duplicate entry, please enter a unique "+ controlledVocabularyDTO.getDisplayColumnOneName() +" and " + controlledVocabularyDTO.getColumnTwoName() +" combination and select save to continue.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        return (count > 1);
    }
       
    
    /** GETTERS AND SETTERS **/
    
    /**
     * @return the controlledVocabularyDTO
     */
    public ControlledVocabularyDTO getControlledVocabularyDTO() {
        return controlledVocabularyDTO;
    }

    /**
     * @param controlledVocabularyDTO the controlledVocabularyDTO to set
     */
    public void setControlledVocabularyDTO(ControlledVocabularyDTO controlledVocabularyDTO) {
        this.controlledVocabularyDTO = controlledVocabularyDTO;
    }

    /**
     * @return the controlledVocabularyKey
     */
    public String getControlledVocabularyKey() {
        return controlledVocabularyKey;
    }

    /**
     * @param controlledVocabularyKey the controlledVocabularyKey to set
     */
    public void setControlledVocabularyKey(String controlledVocabularyKey) {
        this.controlledVocabularyKey = controlledVocabularyKey;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the items
     */
    public ArrayList<SelectItem> getItems() {
        items = new ArrayList<SelectItem>();
        if(controlledVocabularyDTO != null){
            if(controlledVocabularyDTO.getTableName().equals("cv_PreservationDetail")){
                
            }
            else if(controlledVocabularyDTO.getTableName().equals("cv_PreservationType")){
                ArrayList<SampleClassDTO> list = sampleDAO.getSampleClasses();
                for(SampleClassDTO dto : list){
                    items.add(new SelectItem(dto.getSampleClass_key(), dto.getSampleClass()));   
                }
            }
            else if(controlledVocabularyDTO.getTableName().equals("cv_PreservationMethod")){
             
            }
            else if(controlledVocabularyDTO.getTableName().equals("cv_SampleType")){
                ArrayList<SampleClassDTO> list = sampleDAO.getSampleClasses();
                for(SampleClassDTO dto : list){
                    items.add(new SelectItem(dto.getSampleClass_key(), dto.getSampleClass()));
                }
            }
        }
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<SelectItem> items) {
        this.items = items;
    }

}
