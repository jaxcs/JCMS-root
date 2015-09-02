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
CREATE  TABLE `cv_Preferences` (

  `_preference_key` INT(11) NOT NULL AUTO_INCREMENT ,

  `screenName` VARCHAR(255) NULL ,

  `tableName` VARCHAR(255) NULL ,

  `fieldName` VARCHAR(255) NULL ,

  `canHaveDefault` TINYINT(1) NOT NULL DEFAULT '0' ,

  `defaultRequired` TINYINT(1) NOT NULL ,

  `isActive` TINYINT(1) NOT NULL DEFAULT '1' ,

  `version` INT(11) NOT NULL DEFAULT '1' ,

  PRIMARY KEY (`_preference_key`) ,

  UNIQUE INDEX `_preference_key_UNIQUE` (`_preference_key` ASC) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;


UPDATE dbInfo SET
	dbVers = 130,
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

INSERT INTO `cv_Preferences` (`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) VALUES ('mouseEdit', 'Mouse', 'protocol', 1, 0, 1, 1);

INSERT INTO `cv_Preferences` (`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) VALUES ('mouseEdit', 'Mouse', 'origin', 1, 1, 1, 1);

INSERT INTO `cv_Preferences` (`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) VALUES ('mouseEdit', 'UseScheduleTerm', 'UseScheduleTermName', 0, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Litter', 'litterID', 0, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'cod', 1, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'codNotes', 0, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('global', 'DbSetup', 'JCMS_STRAINNAME_FIRST', 1, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Room', 'roomName', 1, 1, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'cv_Phenotype', 'phenotype', 0, 0, 1, 1);

INSERT INTO cv_Preferences 
	(`screenName`, `tableName`, `fieldName`, `canHaveDefault`, `defaultRequired`, `isActive`, `version`) 
	VALUES ('mouseEdit', 'Mouse', 'comment', 0, 0, 1, 1);

CREATE  TABLE `UserPreferences` (

  `_userPreference_key` INT(11) NOT NULL AUTO_INCREMENT ,

  `_user_key` INT(11) NOT NULL ,

  `_preference_key` INT(11) NOT NULL ,

  `hideField` TINYINT(1) NOT NULL DEFAULT '0' ,

  `defaultValue` VARCHAR(255) NULL ,

  `version` INT(11) NOT NULL DEFAULT '1' ,

  PRIMARY KEY (`_userPreference_key`) ,

  UNIQUE INDEX `_userPreference_key_UNIQUE` (`_userPreference_key` ASC) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;

ALTER TABLE `UserPreferences` 

ADD INDEX `_user_key` (`_user_key` ASC) 

, ADD INDEX `_preference_key` (`_preference_key` ASC) ;

INSERT INTO UserPreferences (_user_key, _preference_key) 
    SELECT User._user_key, cv_Preferences._preference_key
    FROM User, cv_Preferences;

UPDATE `UserPreferences` SET `defaultValue`= (SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'JCMS_STRAINNAME_FIRST')
	WHERE UserPreferences._preference_key IN 
		(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'JCMS_STRAINNAME_FIRST');
    
UPDATE `UserPreferences` 
    SET `defaultValue`= 
        (SELECT DbSetup.mtsValue FROM DbSetup, Room WHERE DbSetup.mtsVar = 'MTS_DEFAULT_MOUSE_ROOM' AND
            DbSetup.mtsValue =  Room.roomName)
	WHERE UserPreferences._preference_key IN 
		(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'roomName');

UPDATE `UserPreferences` SET `defaultValue`= (SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'MTS_DEFAULT_MOUSE_ORIGIN')
	WHERE UserPreferences._preference_key IN 
		(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'origin');

UPDATE `UserPreferences` SET `defaultValue`= (SELECT DbSetup.mtsValue FROM DbSetup WHERE DbSetup.mtsVar = 'MTS_DEFAULT_COD')
	WHERE UserPreferences._preference_key IN 
		(SELECT cv_Preferences._preference_key FROM cv_Preferences WHERE cv_Preferences.fieldName = 'cod');
    
