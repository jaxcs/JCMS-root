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
public class UserPreferencesDTO {
    private String userPreferencesKey ="";
    private String preferenceKey = "";
    private String userKey = "";
    private String hideField = "";
    private String defaultValue = "";
    private String version = "";
    
    @Override
    public boolean equals(Object object){
        if(object instanceof UserPreferencesDTO){
            UserPreferencesDTO userPreferencesKey = (UserPreferencesDTO) object;
            if(getUserPreferencesKey().equals(userPreferencesKey.getUserPreferencesKey())){
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
     * @return the userPreferencesKey
     */
    public String getUserPreferencesKey() {
        return userPreferencesKey;
    }

    /**
     * @param userPreferencesKey the userPreferencesKey to set
     */
    public void setUserPreferencesKey(String userPreferencesKey) {
        this.userPreferencesKey = userPreferencesKey;
    }

    /**
     * @return the preferenceKey
     */
    public String getPreferenceKey() {
        return preferenceKey;
    }

    /**
     * @param preferenceKey the preferenceKey to set
     */
    public void setPreferenceKey(String preferenceKey) {
        this.preferenceKey = preferenceKey;
    }

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param userKey the userKey to set
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return the hideField
     */
    public String getHideField() {
        return hideField;
    }

    /**
     * @param hideField the hideField to set
     */
    public void setHideField(String hideField) {
        this.hideField = hideField;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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
