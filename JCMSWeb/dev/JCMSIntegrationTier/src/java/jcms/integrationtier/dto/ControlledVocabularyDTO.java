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

package jcms.integrationtier.dto;

/**
 *
 * @author cnh
 */
public class ControlledVocabularyDTO {
    private String _controlledVocabulary_key = "";
    private String _controlledVocabularyGroup_key = "";
    private String tableName = "";
    private String pkColumnName = "";
    private String columnOneName = "";
    private String columnTwoName = "";
    private String displayName = "";
    private String displayColumnOneName = "";
    private String displayColumnTwoName = "";
    private String subViewName = "";
    private String isUserAdministered = "";
    private String sortOrder = "";
    private String createdBy = "";
    private String dateCreated = "";
    private String modifiedBy = "";
    private String dateModified = "";
    private String version = "";

    public ControlledVocabularyDTO () {
    }

    public ControlledVocabularyDTO (
        String _controlledVocabulary_key,
        String _controlledVocabularyGroup_key,
        String tableName,
        String pkColumnName,
        String columnOneName,
        String columnTwoName,
        String displayName,
        String displayColumnOneName,
        String displayColumnTwoName,
        String subViewName,
        String isUserAdministered,
        String sortOrder,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version ) {
        this.setControlledVocabulary_key(_controlledVocabulary_key);
        this.setControlledVocabularyGroup_key(_controlledVocabularyGroup_key);
        this.setTableName(tableName);
        this.setPkColumnName(pkColumnName);
        this.setColumnOneName(columnOneName);
        this.setColumnTwoName(columnTwoName);
        this.setDisplayName(displayName);
        this.setDisplayColumnOneName(displayColumnOneName);
        this.setDisplayColumnTwoName(displayColumnTwoName);
        this.setSubViewName(subViewName);
        this.setIsUserAdministered(isUserAdministered);
        this.setSortOrder(sortOrder);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
        this.setVersion(version);
    }

    /**
     * @return the _controlledVocabulary_key
     */
    public String getControlledVocabulary_key() {
        return _controlledVocabulary_key;
    }

    /**
     * @param controlledVocabulary_key the _controlledVocabulary_key to set
     */
    public void setControlledVocabulary_key(String controlledVocabulary_key) {
        this._controlledVocabulary_key = controlledVocabulary_key;
    }

    /**
     * @return the _controlledVocabularyGroup_key
     */
    public String getControlledVocabularyGroup_key() {
        return _controlledVocabularyGroup_key;
    }

    /**
     * @param controlledVocabularyGroup_key the _controlledVocabularyGroup_key to set
     */
    public void setControlledVocabularyGroup_key(String controlledVocabularyGroup_key) {
        this._controlledVocabularyGroup_key = controlledVocabularyGroup_key;
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
     * @return the pkColumnName
     */
    public String getPkColumnName() {
        return pkColumnName;
    }

    /**
     * @param pkColumnName the pkColumnName to set
     */
    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    /**
     * @return the columnOneName
     */
    public String getColumnOneName() {
        return columnOneName;
    }

    /**
     * @param columnOneName the columnOneName to set
     */
    public void setColumnOneName(String columnOneName) {
        this.columnOneName = columnOneName;
    }

    /**
     * @return the columnTwoName
     */
    public String getColumnTwoName() {
        return columnTwoName;
    }

    /**
     * @param columnTwoName the columnTwoName to set
     */
    public void setColumnTwoName(String columnTwoName) {
        this.columnTwoName = columnTwoName;
    }

    /**
     * @return the isUserAdministered
     */
    public String getIsUserAdministered() {
        return isUserAdministered;
    }

    /**
     * @param isUserAdministered the isUserAdministered to set
     */
    public void setIsUserAdministered(String isUserAdministered) {
        this.isUserAdministered = isUserAdministered;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the dateModified
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the subViewName
     */
    public String getSubViewName() {
        return subViewName;
    }

    /**
     * @param subViewName the subViewName to set
     */
    public void setSubViewName(String subViewName) {
        this.subViewName = subViewName;
    }

    /**
     * @return the displayColumnOneName
     */
    public String getDisplayColumnOneName() {
        return displayColumnOneName;
    }

    /**
     * @param displayColumnOneName the displayOneName to set
     */
    public void setDisplayColumnOneName(String displayOneName) {
        this.displayColumnOneName = displayOneName;
    }

    /**
     * @return the displayColumnTwoName
     */
    public String getDisplayColumnTwoName() {
        return displayColumnTwoName;
    }

    /**
     * @param displayColumnTwoName the displayTwoName to set
     */
    public void setDisplayColumnTwoName(String displayTwoName) {
        this.displayColumnTwoName = displayTwoName;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
