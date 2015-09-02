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

import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class AdminGeneralDTO extends ITBaseDTO {
    private String tableName = "";
    private String primaryKey = "";
    private String columnOneValue = "";
    private String columnTwoValue = "";
    private String createdBy = "";
    private String modifiedBy = "";
    private String version = "";

    public AdminGeneralDTO() {
        
    }
    
    public AdminGeneralDTO (
            String tableName,
            String primaryKey, 
            String columnOneValue, 
            String columnTwoValue,
            String version) {
        this.setTableName(tableName);
        this.setPrimaryKey(primaryKey);
        this.setColumnOneValue(columnOneValue);
        this.setColumnTwoValue(columnTwoValue);
        this.setVersion(version);
    }
    
    /**
     * @return the primaryKey
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the columnOneValue
     */
    public String getColumnOneValue() {
        return columnOneValue;
    }

    /**
     * @param columnOneValue the columnOneValue to set
     */
    public void setColumnOneValue(String columnOneValue) {
        this.columnOneValue = columnOneValue;
    }

    /**
     * @return the columnTwoValue
     */
    public String getColumnTwoValue() {
        return columnTwoValue;
    }

    /**
     * @param columnTwoValue the columnTwoValue to set
     */
    public void setColumnTwoValue(String columnTwoValue) {
        this.columnTwoValue = columnTwoValue;
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
    
    public Boolean isInsert() {
        return (this.getPrimaryKey().equalsIgnoreCase("0") || this.getPrimaryKey().equalsIgnoreCase("")) ;
    }
    
    /**
     * @return the disableDelete
     */
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        
        if (this.getTableName().equalsIgnoreCase("cv_StrainStatus") &&
            (this.getColumnOneValue().equalsIgnoreCase("A") || 
             this.getColumnOneValue().equalsIgnoreCase("C") || 
             this.getColumnOneValue().equalsIgnoreCase("D") || 
             this.getColumnOneValue().equalsIgnoreCase("F")) ) {
            disabled = true;
        }
        return disabled;
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
}
