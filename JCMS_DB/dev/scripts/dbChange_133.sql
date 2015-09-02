#***
#Copyright (c) 2015 The Jackson Laboratory
#
#This is free software: you can redistribute it and/or modify it 
#under the terms of the GNU General Public License as published by  
#the Free Software Foundation, either version 3 of the License, or  
#(at your option) any later version.
# 
#This software is distributed in the hope that it will be useful,  
#but WITHOUT ANY WARRANTY; without even the implied warranty of 
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
#General Public License for more details.
#
#You should have received a copy of the GNU General Public License 
#along with this software.  If not, see <http://www.gnu.org/licenses/>.
#***
#
# Litter screen preferences
#   
INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'Litter', 'numberBornDead', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'numberBornDead';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'Litter', 'numberCulledAtWean', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'numberCulledAtWean';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'Litter', 'numberMissingAtWean', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'numberMissingAtWean';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'cv_LitterType', 'litterType', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'litterType';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'Litter', 'comment', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'comment';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('LitterEdit', 'Mouse', 'protocol', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'protocol';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'Mouse', 'origin', 1, 1, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'origin';

#
# Set the litter orgin default to the setup variable value
#
UPDATE UserPreferences SET defaultValue = (SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'MTS_DEFAULT_MOUSE_ORIGIN')
	WHERE UserPreferences._preference_key IN 
		(SELECT cv_Preferences._preference_key FROM cv_Preferences 
			WHERE cv_Preferences.fieldName = 'origin' AND cv_Preferences.screenName = 'litterEdit');

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', 'UseScheduleTerm', 'UseScheduleTermName', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'UseScheduleTermName';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('litterEdit', '', 'leavePupsInMatingPen', 1, 1, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'litterEdit' AND cv_Preferences.fieldName = 'leavePupsInMatingPen';

UPDATE dbInfo SET
	dbVers = 133,
	webReleaseNum= '3.11.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;
