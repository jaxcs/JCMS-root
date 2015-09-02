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
# Additional mouse edit preferences
#   
INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'coatColor', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'coatColor';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'diet', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'diet';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'sampleVialTagPosition', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'sampleVialTagPosition';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'sampleVialID', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'sampleVialID';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'sex', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'sex';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'breedingStatus', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'breedingStatus';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'newTag', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'mouseEdit' AND cv_Preferences.fieldName = 'newTag';

# Mating preferences
#   
INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mating', 'weanNote', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = 'weanNote';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mating', 'comment', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = 'comment';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mating', 'needsTyping', 1, 1, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = 'needsTyping';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mating', 'weanTime', 1, 1, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = 'weanTime';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mouse', 'diet', 1, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = 'diet';

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('matingEdit', 'Mating', '_dam2_key', 0, 0, 1, 1);

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences
    WHERE cv_Preferences.screenName = 'matingEdit' AND cv_Preferences.fieldName = '_dam2_key';

UPDATE dbInfo SET
	dbVers = 131,
	webReleaseNum= '3.10.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;
