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

package jcms.integrationtier.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author bas
 */
public class cvPreferencesDAO extends MySQLDAO{
    
    private Connection con = null;
    
    private String cvPreferences = "SELECT * FROM cv_Preferences ORDER BY _preferences_key";
    
    private String hideFieldQuery = "SELECT hideField FROM cv_Preferences, UserPreferences WHERE ";
    
    private String defaultValueQuery = "SELECT defaultValue FROM cv_Preferences, UserPreferences WHERE ";
    
    private String hideField = "0";
    private String defaultValue = "";
    private String query = null;
    
    public String getHideField(String userKey, String screenName, String fieldName ){
        query = hideFieldQuery + "cv_Preferences._preference_key = UserPreferences._preference_key" +
                " AND cv_Preferences.screenName = '" + screenName + "'" +
                " AND cv_Preferences.fieldName = '" + fieldName + "'" +
                " AND UserPreferences._user_key = '" + userKey + "';";
        Result result = this.executeJCMSQuery(query);
        if (result != null){
            
            for (SortedMap row : result.getRows()){
                hideField = this.myGet("hideField", row);
                //Note there should only be one row in the results
            }
        }
        return hideField;
    }
    
    public String GetDefaultValue(String userKey, String screenName, String fieldName) {
        query = defaultValueQuery + "cv_Preferences._preference_key = UserPreferences._preference_key" +
                " AND cv_Preferences.screenName = '" + screenName + "'" +
                " AND cv_Preferences.fieldName = '" + fieldName + "'" +
                " AND UserPreferences._user_key = '" + userKey + "';";
        Result result = this.executeJCMSQuery(query);
        if (result != null){
            
            for (SortedMap row : result.getRows()){
                if  (this.myGet("defaultValue", row) != null) {
                    defaultValue = this.myGet("defaultValue", row);
                    //Note there should only be one row in the results
                }
                
            }
        }
        return defaultValue;
    }
}
