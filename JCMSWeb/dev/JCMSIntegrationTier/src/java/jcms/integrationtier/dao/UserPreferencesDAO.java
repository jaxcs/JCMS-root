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
import jcms.integrationtier.dto.UserPreferencesDTO;

/**
 *
 * @author bas
 */
public class UserPreferencesDAO extends MySQLDAO {
    
    private Connection con = null;
    
    private String cvPreferences = "SELECT * FROM cv_Preferences ORDER BY _preferences_key";
    
    private String userPreferences = "SELECT * FROM UserPreferences ORDER BY _UserPreferences_key";
    
    private String specificUserPreferences = "SELECT * FROM UserPreferences WHERE _user_key =";
    
    public ArrayList<UserPreferencesDTO> getAllUserPreferences() {
        ArrayList<UserPreferencesDTO> dtoList = new ArrayList<UserPreferencesDTO> ();
        UserPreferencesDTO dto = null;
        Result result = executeJCMSQuery(userPreferences);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createUserPreferencesDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
        public ArrayList<UserPreferencesDTO> getMyUserPreferences(String userKey) {
        ArrayList<UserPreferencesDTO> dtoList = new ArrayList<UserPreferencesDTO> ();
        UserPreferencesDTO dto = null;
        Result result = executeJCMSQuery(userPreferences + userKey + ";");
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createUserPreferencesDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
        
    private UserPreferencesDTO createUserPreferencesDTO(SortedMap row) {
        UserPreferencesDTO dto = new UserPreferencesDTO();
        dto.setUserPreferencesKey(myGet("_userPreferences_key", row));
        dto.setPreferenceKey(myGet("_preference_key", row));
        dto.setUserKey(myGet("_user_key", row));
        dto.setHideField(myGet("hideField", row));
        dto.setDefaultValue(myGet("defaultValue", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
           
    private Integer insertUserPreference(UserPreferencesDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        
        String cmd = "INSERT INTO UserPreferences " 
            + "\n (_user_key, _preference_key, hideField, defaultValue, version ) "
            + "\n VALUES ("+ varcharParser(dto.getUserKey().trim())
            + ", "+ varcharParser(dto.getPreferenceKey().trim())
            + ", "+ varcharParser(dto.getHideField().trim())
            + ", "+ varcharParser(dto.getDefaultValue().trim())
            + ", "+ version.toString() 
            + " )" ;
        executeJCMSUpdate(cmd);

        Integer key = 0;
        Result result = this.executeJCMSQuery("SELECT MAX(_userPreference_key) as primaryKey FROM UserPreferences");
        for (SortedMap row : result.getRows()) {
            key = Integer.parseInt(this.myGet("primaryKey", row));
        }
        return key;
    }

    //For when a new user is added, use this to create their UserPreferences in the table
    public Integer insertUserPreferences(String userKey) throws SQLException {
        Integer success = 0;
        Integer count = 0;
        
        String cmd = "INSERT INTO UserPreferences (_user_key, _preference_key) "
            + " SELECT User._user_key, cv_Preferences._preference_key"
            + " FROM User, cv_Preferences"
            + " WHERE User._user_key =" + userKey + ";";
        
        success = executeJCMSUpdate(cmd);
        //Skip the next part if the rows were not added
        if (0 < success) {
            //update this user's preference defaults to be equal to the setup variable values
            //NOTE the setup variables could be set to something invalid, which is caught later when My Preferences is displayed
            //Strain name first
            cmd = "UPDATE UserPreferences SET defaultValue = (SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'JCMS_STRAINNAME_FIRST') "
                + " WHERE UserPreferences._user_key =" + userKey
                + " AND UserPreferences._preference_key IN "
                + "(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'JCMS_STRAINNAME_FIRST');";
            count = executeJCMSUpdate(cmd);
            success = success + count;
            //default mouse room name is set to null if the room name is not valid
            cmd = "UPDATE UserPreferences SET defaultValue ="
                + " (SELECT DbSetup.mtsValue FROM DbSetup, Room WHERE DbSetup.mtsVar = 'MTS_DEFAULT_MOUSE_ROOM'"
                    + " AND DbSetup.mtsValue =  Room.roomName)"
                + " WHERE UserPreferences._user_key =" + userKey
                    + " AND UserPreferences._preference_key IN "
                    + "(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'roomName');";
            count = executeJCMSUpdate(cmd);
            success = success + count;
            //default mouse origin
            cmd = "UPDATE UserPreferences SET defaultValue="
                + "(SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'MTS_DEFAULT_MOUSE_ORIGIN')"
                + " WHERE UserPreferences._user_key =" + userKey
                    + " AND UserPreferences._preference_key IN"
                    + " (SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'origin');";
            count = executeJCMSUpdate(cmd);
            success = success + count;
            //default cause of death
            cmd = "UPDATE UserPreferences SET defaultValue= "
                  + "(SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'MTS_DEFAULT_COD')"
                  + " WHERE UserPreferences._user_key =" + userKey
                  + " AND UserPreferences._preference_key IN "
		  + "(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'cod');";
            count = executeJCMSUpdate(cmd);
            success = success + count;
            
        }
        
        return success;
    }
    
    private Integer updateUserPreferences(UserPreferencesDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        
        String cmd = "UPDATE UserPreferences SET " 
            + " hideField = " + varcharParser(dto.getHideField().trim())
            + ", defaultValue = " + varcharParser(dto.getDefaultValue().trim())
            + ", version = " + version.toString()
            + "\n WHERE _userPreference_key = "+ dto.getUserPreferencesKey() ;
        Integer count = executeJCMSUpdate(cmd);
            
        return count;
    }    
    
    public Integer updateMyUserPreference(String userKey, String screenName, String fieldName, Boolean hideField, String defaultValue) throws SQLException {
        
        
        String cmd = "UPDATE UserPreferences SET "
            + " hideField=" + hideField
            + ", defaultValue=" + varcharParser(defaultValue.trim())
            + " WHERE _user_key = " + varcharParser(userKey.trim()) 
            + " AND UserPreferences._preference_key= (Select cv_Preferences._preference_key FROM cv_Preferences WHERE "
            + " cv_Preferences.screenName = " + varcharParser(screenName.trim()) 
            + " AND cv_Preferences.fieldName = " + varcharParser(fieldName.trim())
            + ");";

        Integer count = executeJCMSUpdate(cmd);
            
        return count;
    } 
}

