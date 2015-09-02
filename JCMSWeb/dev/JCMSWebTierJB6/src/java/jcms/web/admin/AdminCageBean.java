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
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;
import jcms.integrationtier.dao.ContainerDAO;
import jcms.integrationtier.dao.MouseDAO;
import jcms.integrationtier.dao.ContainerHistoryDAO;
import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.dto.QueryDefinitionDTO;
import jcms.integrationtier.dto.QueryTypeDTO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.cagecards.CageCardBean;
import jcms.web.common.ExtendedTableBean;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminCageBean extends AdminBean {
    private ArrayList<ContainerDTO> vocabularyList = null;
    private ArrayList<ContainerHistoryDTO> containerHistoryList = null;
    private ArrayList<MouseDTO> mouseList = null;
    private ArrayList<MouseDTO> nonExitedMouseList = null;
    private ListDataModel cageHistoryDataModel = null;
    private ListDataModel mouseDataModel = new ListDataModel();
    private ContainerDAO containerDAO = new ContainerDAO();
    private ContainerHistoryDAO containerHistoryDAO = new ContainerHistoryDAO();
    private CvContainerStatusEntity cvContainerStatusEntity = new CvContainerStatusEntity();
    private ContainerDTO cageSearchDTO = new ContainerDTO();
    private ExtendedTableBean searchCageETB = new ExtendedTableBean();
    private ContainerDTO selectedCageDTO = null;
    private Integer gridPage = 1;
    private String containerKey = "0";
    private String cageHistoryRowIndex = "";
    private Boolean preview = false;
    private String intendedUse = "";
    private Boolean printWeanCard = false;
    private Boolean printMatingCard = false;
    private QueryTypeDTO queryTypeDTO = new QueryTypeDTO();
    private QueryDefinitionDTO queryDefinitionDTO = new QueryDefinitionDTO();
    private Boolean warnCageNameDuplicate = false;
    private CVAdministrationDAO cvDAO = new CVAdministrationDAO();
    private String retiredKeyValue = "0";

    public AdminCageBean() {
        this.clearSessionVariables();
        this.setRows(10);
        // get the value of the setup variable about warning if there is a duplicate cage name  
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_WARN_DUPLICATE_PEN_NAME");

        if (entity != null) {
            DbsetupEntity dbEntity = (DbsetupEntity) entity;

            if (dbEntity.getMTSValue().equalsIgnoreCase("true")) {
                this.warnCageNameDuplicate = true;
            } 
            else {
                this.warnCageNameDuplicate = false;
            }
        }
        //Find out and save what the key value is for the term "retired" in the cv_ContainerStatus table
        this.retiredKeyValue = this.cvDAO.getContainerStatusKeyFromTerm("retired");
    }
    
    public void cancelAction() {
        this.setIsDirty(false);
        this.clearSessionVariables();
        this.loadVocabularyAction();
    }

    public void cancelHistoryAction() {
        this.setCageHistoryRowIndex("");
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

    public void editCageHistoryAction() {
        this.setCageHistoryRowIndex(this.getParamHistoryRowIndex());
    }

    public void saveCageHistoryAction() {
        String strMessage = "";
        String rowIndex = this.getParamHistoryRowIndex();
        Boolean isValid = true;
        
        ContainerHistoryDTO dto = (ContainerHistoryDTO) (this.containerHistoryList.get(Integer.parseInt(rowIndex))).getDTO();
        try {
            //Need to make sure the action date is not identical to any other action date in the container history for this container
            
            if (containerHistoryDAO.isDuplicateDate(dto)) {
                isValid = false;
                strMessage = "Cage history must have a unique action date/time. ";
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("ContainerHistory update cancelled ", strMessage));
            } 
            //May not have a cage status of retired if there are non-exited mice in the cage
            if(dto.getContainerStatuskey().toString().equals(retiredKeyValue)) {
                this.nonExitedMouseList = new MouseDAO().getNonExitedMiceInCage(dto.getContainerKey().toString());
                if (nonExitedMouseList != null && nonExitedMouseList.size() > 0) {
                    isValid = false;
                    strMessage = "Cage history may not have a status of retired if there are non-exited mice in the cage. ";
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logInfo(this.formatLogMessage("ContainerHistory update cancelled ", strMessage));
                }
            }
            if (isValid) {
                this.setCageHistoryRowIndex("");
                this.containerDAO.updateContainerHistory(dto);
            }
                
        } catch (Exception ex) {
            strMessage = "Save container history failed, please refresh page and try again.  " + ex ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Update Failed", strMessage));
        }
        this.loadVocabularyAction();
        this.loadContainerHistoryAction(dto.getContainerKey().toString());
    }
    
    public void deleteCageHistoryAction() {
        String strMessage = "";
        if (!this.getIsDirty()) {
            String rowIndex = getParamHistoryRowIndex();
            ContainerHistoryDTO dto = (ContainerHistoryDTO) (this.containerHistoryList.get(Integer.parseInt(rowIndex))).getDTO();
            try {
                this.containerDAO.deleteContainerHistory(dto);
            } catch (SQLException ex) {
                strMessage = "Delete container history failed, please refresh page and try again.  " + ex ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Delete Failed", strMessage));
            }
            this.loadVocabularyAction();
            this.loadContainerHistoryAction(dto.getContainerKey().toString());
        } else {
            addToMessageQueue(dirtyMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Dirty Row", dirtyMessage));
        }
    }

    public String getParamHistoryRowIndex() {
        if (this.getRequestParameter("paramHistoryRowIndex") == null)
            return "";
        return this.getRequestParameter("paramHistoryRowIndex").toString();
    }
    public void saveAction() {
        if (this.getIsDirty()) {
            if (saveVocabulary()) {
                this.setIsDirty(false);
                this.clearSessionVariables();
                this.reloadSelectItemWrapper();
                this.loadVocabularyAction();
                this.loadContainerHistoryAction(this.getContainerKey());
                this.loadMiceAction(getContainerKey());
            }
        }
    }

    public void searchCageAction() {
        this.loadVocabularyAction();
    }
    
    private void clearLocalVariables() {
        if (containerHistoryList != null)
            containerHistoryList.clear();
        if (searchCageETB != null)
            searchCageETB.clearSelection();
    }
    
    public void loadVocabularyAction() {
        this.setVocabularyDataModel(new ListDataModel());
        this.vocabularyList = containerDAO.getContainers(this.getCageSearchDTO());
        this.getVocabularyDataModel().setWrappedData(this.vocabularyList);
    }
    
    public void loadContainerHistoryAction(String containerKey) {
        this.setHealthLevelHistoryDataModel(new ListDataModel());
        this.containerHistoryList = containerDAO.getContainerHistory(containerKey);
        this.getHealthLevelHistoryDataModel().setWrappedData(this.containerHistoryList);
    }

    public void loadMiceAction(String containerKey) {
        this.setMouseDataModel(new ListDataModel());
        this.mouseList = new MouseDAO().getMiceInCage(containerKey);
        this.getMouseDataModel().setWrappedData(this.mouseList);
    }
    
    public void showCageHistory(){
        if (containerHistoryList != null && this.getHealthLevelHistoryDataModel() != null) {
            this.containerHistoryList.clear();
            this.getHealthLevelHistoryDataModel().setWrappedData(this.containerHistoryList);
        }
        this.cancelHistoryAction();
        System.out.println("ShowCageHistory - container key: " + this.getContainerKey());
        if (!this.getContainerKey().equalsIgnoreCase("")) {
            for(ContainerDTO dto : this.vocabularyList) {
                if (Integer.parseInt(dto.getContainer_key()) == Integer.parseInt(this.getContainerKey())) {
                    this.setSelectedCageDTO(dto);
                    this.loadContainerHistoryAction(getSelectedCageDTO().getContainer_key());
                    this.loadMiceAction(getSelectedCageDTO().getContainer_key());
                    break;
                }
            }
        }
        
        // Clear mouse selection
        this.setQueryTypeDTO(new QueryTypeDTO(""));
    }
    
    public Boolean getDisablePrintCageCardButton() {
        Boolean disablePrint = false;
        if (this.selectedCageDTO == null)
            return true;
        if (this.mouseList.size() <= 0) 
            return true;
        if (this.getQueryDefinitionDTO().getQueryDefinition_key().equalsIgnoreCase("") || this.getQueryDefinitionDTO().getQueryDefinition_key().equalsIgnoreCase("0")) 
            return true;
        return disablePrint;
    }
    
    public void printCageCardAction() {
        System.out.println("Printing Cage Card");
        
        String queryTypeKey = queryTypeDTO.getQueryType_key();
        String queryDefinitionName = this.getQueryDefinitionDTO().getQueryName();
        String queryDefinitionKey = this.getQueryDefinitionDTO().getQueryDefinition_key();
        
        if (this.selectedCageDTO == null || this.mouseList.size() == 0) {
            String strMessage = "Please select a cage.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Manage Cages", strMessage));
        } else {
            if (queryTypeKey.length() > 0 && queryDefinitionKey.length() > 0) {
                String queryType = "";
                for (SelectItem item : this.getCageCardQueryTypes()) {
                    if (queryTypeKey.equalsIgnoreCase(item.getValue().toString())) {
                        queryType = item.getLabel();
                    }
                }

                CageCardBean cageCardBean = new CageCardBean();

                QueryDefinitionEntity queryDefinitionEntity = new QueryDefinitionEntity();
                queryDefinitionEntity.setQueryDefinitionkey(new Integer(queryDefinitionKey));
                CvQueryTypeEntity queryTypeEntity = new CvQueryTypeEntity();
                queryTypeEntity.setQueryTypekey(new Integer(queryTypeKey));
                queryDefinitionEntity.setQueryTypekey(queryTypeEntity);
                cageCardBean.setEntity(queryDefinitionEntity);

                cageCardBean.setCageID(this.getSelectedCageDTO().getContainerID());
                cageCardBean.setCardQuantity("single");

                if (queryType.equalsIgnoreCase(CageCardType.Detail.name())) {
                    cageCardBean.setCardType(CageCardType.Detail);
                } if (queryType.equalsIgnoreCase(CageCardType.Mating.name())) {
                    cageCardBean.setCardType(CageCardType.Mating);
                } if (queryType.equalsIgnoreCase(CageCardType.Wean.name())) {
                    cageCardBean.setCardType(CageCardType.Wean);
                }

                CageCardDTO cageCardDTO = new CageCardDTO();
                cageCardDTO.setMyWorkgroups(this.getCurrentUserGuestWorkgroups());
                cageCardDTO.setCageCardType(cageCardBean.getCardType());
                cageCardDTO.setCardName(queryDefinitionName);
                cageCardDTO.setPenID(this.getSelectedCageDTO().getContainerID());
                cageCardDTO.setQuantity("single");
                cageCardBean.setTheDTO(cageCardDTO);

                cageCardBean.setEntityKey(new Integer(this.getQueryDefinitionDTO().getQueryDefinition_key()));
                cageCardBean.setNumberOfCards(1);
                cageCardBean.downloadCageCardAction();
                cageCardBean.externalDownloadCageCard();
            }
        }
        
    }
    
    public List<SelectItem> getCageCardQueryTypes() {
        List<SelectItem> list = this.selectItemWrapper.getCvCageCardQueryTypes(true);
        List<SelectItem> filteredList = new ArrayList<SelectItem>();
        Boolean isMating = this.printMatingCardCheck();
        Boolean isWean = this.printWeanCardCheck();
        this.setQueryTypeDTO(new QueryTypeDTO());
        for (SelectItem item : list) {
            if (item.getLabel().toString().equalsIgnoreCase("")) {
                filteredList.add(item);
            } else if (item.getLabel().toString().equalsIgnoreCase("Detail")) {
                filteredList.add(item);
            } else if (item.getLabel().toString().equalsIgnoreCase("Mating") && isMating) {
                filteredList.add(item);
            } else if (item.getLabel().toString().equalsIgnoreCase("Wean") && isWean) {
                filteredList.add(item);
            }
        }
        
        return filteredList;
    }
    
    private Boolean printWeanCardCheck() {
        Boolean canPrint = true;
        if (mouseList != null && mouseList.size() > 0) {
            String baseStrainKey = mouseList.get(0).getStrain_key();
            for (MouseDTO dto : this.mouseList) {
                if (!(baseStrainKey.equalsIgnoreCase(dto.getStrain_key()))) {
                    canPrint = false;
                    break;
                }
            }
        } else {
            canPrint = false;
        }
        this.setPrintWeanCard(canPrint);
        return canPrint;
    }
    
    private Boolean printMatingCardCheck() {
        Boolean canPrint = false;
        Integer femaleCount = 0;
        Integer maleCount = 0;
        Integer totalCount = 0;
        if (mouseList != null && mouseList.size() > 0) {
            for (MouseDTO dto : this.mouseList) {
                femaleCount = femaleCount + (dto.getSex().equalsIgnoreCase("F") ? 1 : 0);
                maleCount = maleCount + (dto.getSex().equalsIgnoreCase("M") ? 1 : 0);
                totalCount += 1;
            }
            canPrint = ((femaleCount == 1 || femaleCount == 2) && (maleCount == 0 || maleCount == 1) && (totalCount <= 3));
        }
        this.setPrintMatingCard(canPrint);
        return canPrint;
    }
    
    public List<SelectItem> getQueryDefinitions() {
        this.getQueryDefinitionDTO().setQueryDefinition_key("");
        return this.selectItemWrapper.getQueryDefinitions(this.getQueryTypeDTO().getQueryType_key(), true);
    }

    /**
     * SaveVocabulary handles both insert and update conditions.
     */
    private boolean saveVocabulary() {
        Boolean success = false;
        String rowIndex = getParamRowIndex();
        System.out.println("Row index equals "+ rowIndex +"  Model index is "+ this.vocabularyDataModel.getRowIndex());
        ContainerDTO dto = (ContainerDTO) (this.vocabularyList.get(Integer.parseInt(rowIndex)));
        //Duplicate cage names are allowed, but warn user they used a duplicate if setup variable is true
        if (isDuplicate(dto)) {
            if (warnCageNameDuplicate) {
                addToMessageQueue("Warning: the cage name is a duplicate.", FacesMessage.SEVERITY_INFO);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", "Warning: cage name is a duplicate"));
            }
        }

        if (isValidRow(dto) && !isSameDate(dto)) {
            try {
                this.containerDAO.saveContainer(dto);
                success = true;
            } catch (Exception ex) {
                String strMessage = "Save failed, please cancel and try again. " ;
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("Vocabulary Save", strMessage));
            }
        }
        return success;
        
    }
    
    private Boolean isValidRow (ContainerDTO dto) {
        Boolean isValid = true;
        String strMessage = "";
        if (dto.getCageHistoryDTO().getContainerStatuskey() == 0) {
            isValid = false;
            strMessage = "Required value missing, please select a cage Status.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        //If the cage status is retired, verify that all mice in the cage are exited.
        if (isValid){
            Integer containerStatusKey = 0;
            containerStatusKey = dto.getCageHistoryDTO().getContainerStatuskey();
            String containerStatus = "";
            for (CvContainerStatusEntity centity : new ListSupportDTO().getCvContainerStatus()) {
                if (centity.getContainerStatuskey().equals(containerStatusKey)) {
                    containerStatus = centity.getContainerStatus();
                    break;
                }
            } 

            if (containerStatus.equalsIgnoreCase("retired")) {
                this.nonExitedMouseList = new MouseDAO().getNonExitedMiceInCage(containerKey.toString());
                if (this.nonExitedMouseList != null && nonExitedMouseList.size() > 0) {
                    isValid = false;
                    strMessage = "A cage may not be retired if it contains non-exited mice.";
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logInfo(this.formatLogMessage("Cage not changed ", strMessage));
                }
            }
            
        }
        if (dto.getCageHistoryDTO().getActionDate() == null) {
            isValid = false;
            strMessage = "Required value missing, please select an Action Date.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Required Value Missing", strMessage));
        }
        if (dto.getContainerName().length() > 16) {
            isValid = false;
            strMessage = "Value is too long, please enter a Cage Name of 16 characters or less.";
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Value Is Too Long", strMessage));
        }
        return isValid;
    }
    
    private Boolean isDuplicate(ContainerDTO sourceDTO) {
        Integer count = 0;
        if (sourceDTO.getContainerName().trim().length() > 0) {
            for(ContainerDTO dto : this.vocabularyList) {
                if (sourceDTO.getContainerName().trim().equalsIgnoreCase(dto.getContainerName().trim()))
                    count++;
            }
            //Cages are allowed to have duplicate names
        }
        
        return (count > 1);
    }
    
    private Boolean isSameDate(ContainerDTO sourceDTO) {
        Boolean sameDate = false;
  
            sameDate = containerDAO.isSameDate(sourceDTO);
            if (sameDate) {
                String strMessage = "Duplicate action date/time for this day.  Select a different action date/time than " + sourceDTO.getCageHistoryDTO().getActionDate();
                addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
                this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
            }
        
        return sameDate;
    }
    
    private Boolean isMaxDate(ContainerDTO sourceDTO) {
        Boolean maxDate = containerDAO.isSameDate(sourceDTO);
        if (maxDate) {
            String strMessage = "Duplicate action date/time for this day.  Select a different action date/time than " + sourceDTO.getCageHistoryDTO().getActionDate();
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_WARN);
            this.getLogger().logInfo(this.formatLogMessage("Duplicate Entry", strMessage));
        }
        
        return maxDate;
    }
    
    private Boolean isCageNameChange(ContainerDTO sourceDTO) {
        Boolean roomNameChange = false;
        if (sourceDTO.getContainer_key().equalsIgnoreCase("0")) {
            return roomNameChange;
        }
        ContainerDTO filterDTO = new ContainerDTO();
        filterDTO.setContainer_key(sourceDTO.getContainer_key());
        ContainerDTO originalCage = (containerDAO.getContainers(filterDTO)).get(0);
        if (!sourceDTO.getContainerName().equalsIgnoreCase(originalCage.getContainerName())) {
            roomNameChange = true;
        }
            
        return roomNameChange;
    }
    
    public void clearSearchAction() {
        this.getCageSearchDTO().clear();
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
     * @return the cageSearchDTO
     */
    public ContainerDTO getCageSearchDTO() {
        return cageSearchDTO;
    }

    /**
     * @param cageSearchDTO the cageSearchDTO to set
     */
    public void setCageSearchDTO(ContainerDTO cageSearchDTO) {
        this.cageSearchDTO = cageSearchDTO;
    }

    /**
     * @return the searchCageETB
     */
    public ExtendedTableBean getSearchCageETB() {
        return searchCageETB;
    }

    /**
     * @param searchCageETB the searchCageETB to set
     */
    public void setSearchCageETB(ExtendedTableBean searchCageETB) {
        this.searchCageETB = searchCageETB;
    }

    /**
     * @return the cageHistoryDataModel
     */
    public ListDataModel getHealthLevelHistoryDataModel() {
        return getCageHistoryDataModel();
    }

    /**
     * @param cageHistoryDataModel the cageHistoryDataModel to set
     */
    public void setHealthLevelHistoryDataModel(ListDataModel cageHistoryDataModel) {
        this.setCageHistoryDataModel(cageHistoryDataModel);
    }

    /**
     * @return the selectedCageDTO
     */
    public ContainerDTO getSelectedCageDTO() {
        return selectedCageDTO;
    }

    /**
     * @return the cageHistoryDataModel
     */
    public ListDataModel getCageHistoryDataModel() {
        return cageHistoryDataModel;
    }

    /**
     * @param cageHistoryDataModel the cageHistoryDataModel to set
     */
    public void setCageHistoryDataModel(ListDataModel cageHistoryDataModel) {
        this.cageHistoryDataModel = cageHistoryDataModel;
    }

    /**
     * @return the gridPage
     */
    public Integer getGridPage() {
        return gridPage;
    }

    /**
     * @param gridPage the gridPage to set
     */
    public void setGridPage(Integer gridPage) {
        this.gridPage = gridPage;
    }

    /**
     * @param selectedCageDTO the selectedCageDTO to set
     */
    public void setSelectedCageDTO(ContainerDTO selectedCageDTO) {
        this.selectedCageDTO = selectedCageDTO;
    }

    /**
     * @return the containerKey
     */
    public String getContainerKey() {
        return containerKey;
    }

    /**
     * @param containerKey the containerKey to set
     */
    public void setContainerKey(String containerKey) {
        this.containerKey = containerKey;
    }

    /**
     * @return the cageHistoryRowIndex
     */
    public String getCageHistoryRowIndex() {
        return cageHistoryRowIndex;
    }

    /**
     * @param cageHistoryRowIndex the cageHistoryRowIndex to set
     */
    public void setCageHistoryRowIndex(String cageHistoryRowIndex) {
        this.cageHistoryRowIndex = cageHistoryRowIndex;
    }

    /**
     * @return the mouseDataModel
     */
    public ListDataModel getMouseDataModel() {
        return mouseDataModel;
    }

    /**
     * @param mouseDataModel the mouseDataModel to set
     */
    public void setMouseDataModel(ListDataModel mouseDataModel) {
        this.mouseDataModel = mouseDataModel;
    }

    /**
     * @return the preview
     */
    public Boolean getPreview() {
        return preview;
    }

    /**
     * @param preview the preview to set
     */
    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    /**
     * @return the intendedUse
     */
    public String getIntendedUse() {
        return intendedUse;
    }

    /**
     * @param intendedUse the intendedUse to set
     */
    public void setIntendedUse(String intendedUse) {
        this.intendedUse = intendedUse;
    }

    /**
     * @return the printWeanCard
     */
    public Boolean getPrintWeanCard() {
        return printWeanCard;
    }

    /**
     * @param printWeanCard the printWeanCard to set
     */
    public void setPrintWeanCard(Boolean printWeanCard) {
        this.printWeanCard = printWeanCard;
    }

    /**
     * @return the printMatingCard
     */
    public Boolean getPrintMatingCard() {
        return printMatingCard;
    }

    /**
     * @param printMatingCard the printMatingCard to set
     */
    public void setPrintMatingCard(Boolean printMatingCard) {
        this.printMatingCard = printMatingCard;
    }

    /**
     * @return the queryTypeDTO
     */
    public QueryTypeDTO getQueryTypeDTO() {
        return queryTypeDTO;
    }

    /**
     * @param queryTypeDTO the queryTypeDTO to set
     */
    public void setQueryTypeDTO(QueryTypeDTO queryTypeDTO) {
        this.queryTypeDTO = queryTypeDTO;
    }

    /**
     * @return the queryDefinitionDTO
     */
    public QueryDefinitionDTO getQueryDefinitionDTO() {
        return queryDefinitionDTO;
    }

    /**
     * @param queryDefinitionDTO the queryDefinitionDTO to set
     */
    public void setQueryDefinitionDTO(QueryDefinitionDTO queryDefinitionDTO) {
        this.queryDefinitionDTO = queryDefinitionDTO;
    }


}

