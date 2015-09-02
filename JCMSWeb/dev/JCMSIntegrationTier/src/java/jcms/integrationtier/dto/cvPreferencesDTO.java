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
 * @author bas
 */
public class cvPreferencesDTO {
    private String preferencesKey = "";
    private String screenName = "";
    private String tableName = "";
    private String fieldName = "";
    private String canHaveDefault = "";
    private String defaultRequired = "";
    private String isActive = "";
    private String version = "";
    
    @Override
    public boolean equals(Object object){
        if(object instanceof cvPreferencesDTO){
            cvPreferencesDTO preferencesKey = (cvPreferencesDTO) object;
            if(getPreferencesKey().equals(preferencesKey.getPreferencesKey())){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * @return the preferencesKey
     */
    public String getPreferencesKey() {
        return preferencesKey;
    }

    /**
     * @param preferencesKey the preferencesKey to set
     */
    public void setPreferencesKey(String preferencesKey) {
        this.preferencesKey = preferencesKey;
    }

    /**
     * @return the screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * @param screenName the screenName to set
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
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
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the canHaveDefault
     */
    public String getCanHaveDefault() {
        return canHaveDefault;
    }

    /**
     * @param canHaveDefault the canHaveDefault to set
     */
    public void setCanHaveDefault(String canHaveDefault) {
        this.canHaveDefault = canHaveDefault;
    }

    /**
     * @return the defaultRequired
     */
    public String getDefaultRequired() {
        return defaultRequired;
    }

    /**
     * @param defaultRequired the defaultRequired to set
     */
    public void setDefaultRequired(String defaultRequired) {
        this.defaultRequired = defaultRequired;
    }

    /**
     * @return the isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
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
    
}
