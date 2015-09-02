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

import javax.faces.application.FacesMessage;
import javax.faces.model.ListDataModel;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.colonyManagement.Filter;
import jcms.web.colonyManagement.Sort;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author cnh
 */
public class AdminBean extends WTBaseBackingBean {
    protected ListDataModel               vocabularyDataModel     = null;
    protected SelectItemWrapper           selectItemWrapper       = null;
    protected Boolean                     isDirty = false;
    protected String                      dirtyMessage = "Please save or cancel changes. " ;
    private String selectedRowKey = "";
    private Sort sortBO = new Sort();
    private Filter filterBO = new Filter();
    private Integer rows = 20;
    
    protected final String ROWINDEX = "rowIndex";
    protected final String PRIMARYKEY = "primaryKey";
    
    protected final String PARAMROWINDEX = "paramRowIndex";
    protected final String PARAMPRIMARYKEY = "paramPrimaryKey";
    
    public AdminBean() {
        this.putSessionParameter("selectItemWrapper", new SelectItemWrapper());
        this.setSelectItemWrapper(new SelectItemWrapper());
    }
    
    protected String getParamPrimaryKey() {
        return this.getRequestParameter(this.PARAMPRIMARYKEY).toString();
    }
    
    protected String getParamRowIndex() {
        return this.getRequestParameter(this.PARAMROWINDEX).toString();
    }
    
    protected Boolean containsRestrictedCharacters(String item) {
        Boolean containsRC = false;
        if ((item.contains("'")) || (item.contains("\"")) || (item.contains(",")) || item.contains(";")) {
            containsRC = true;
            String strMessage = "Restricted Characters Found.  Single quote, double quote, comma, and semicolon characters are not allowed in this vocabulary term.  Please remove and save to continue." ;
            addToMessageQueue(strMessage, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("Restricted Characters Found", strMessage));
        }
        return containsRC;
    }
    
    public void loadVocabularyMenuAction() {
        this.setIsDirty(false);
        this.loadVocabularyAction();
        this.clearSessionVariables();
        // reset vocabularies to include new items and edits
        this.setSelectItemWrapper(new SelectItemWrapper());
    }
    
    public void loadVocabularyAction() {
    }
    
    protected void resetSortAndFilter() {
        this.setSortBO(new Sort());
        this.setFilterBO(new Filter());
    }
    /**
     * clear the variables in session
     */
    protected void clearSessionVariables() {
        this.putSessionParameter(this.ROWINDEX, "");
        this.putSessionParameter(this.PRIMARYKEY, "0");
    }

    /**
     * set the variables in session
     */
    protected void setSessionVariables() {
        String primaryKey = getParamPrimaryKey();
        this.putSessionParameter("rowIndex", getParamRowIndex());
        if (primaryKey.length() > 0) {
            this.putSessionParameter("primaryKey", primaryKey);
        }
        else {
            this.putSessionParameter("primaryKey", 0);
        }
    }
    
    /**
     * @return the rowIndex
     */
    public String getRowIndex() {
        return this.getSessionParameter(this.ROWINDEX).toString();
    }

    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(String rowIndex) {
        this.putSessionParameter(this.ROWINDEX, rowIndex);
    }

    /**
     * @return the isDirty
     */
    public Boolean getIsDirty() {
        return isDirty;
    }

    /**
     * @param isDirty the isDirty to set
     */
    public void setIsDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    /**
     * @return the vocabularyDataModel
     */
    public ListDataModel getVocabularyDataModel() {
        return vocabularyDataModel;
    }

    /**
     * @param vocabularyDataModel the vocabularyDataModel to set
     */
    public void setVocabularyDataModel(ListDataModel vocabularyDataModel) {
        this.vocabularyDataModel = vocabularyDataModel;
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

    protected void reloadSelectItemWrapper() {
        this.putSessionParameter("selectItemWrapper", new SelectItemWrapper());
    }

    /**
     * @return the sortBO
     */
    public Sort getSortBO() {
        return sortBO;
    }

    /**
     * @param sortBO the sortBO to set
     */
    public void setSortBO(Sort sortBO) {
        this.sortBO = sortBO;
    }

    /**
     * @return the filterBO
     */
    public Filter getFilterBO() {
        return filterBO;
    }

    /**
     * @param filterBO the filterBO to set
     */
    public void setFilterBO(Filter filterBO) {
        this.filterBO = filterBO;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the selectedRowKey
     */
    public String getSelectedRowKey() {
        return selectedRowKey;
    }

    /**
     * @param selectedRowKey the selectedRowKey to set
     */
    public void setSelectedRowKey(String selectedRowKey) {
        this.selectedRowKey = selectedRowKey;
    }

    
}
