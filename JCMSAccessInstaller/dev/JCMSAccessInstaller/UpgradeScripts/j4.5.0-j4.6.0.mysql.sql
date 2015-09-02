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


-- MySQL upgrade for JCMS 4.2.1 to JCMS 4.3.0
-- Tested on MySQL 5.0 on Windows 
/*
Delimiter //

DROP PROCEDURE IF EXISTS `j4_5_0__j4_6_0` //

CREATE  PROCEDURE `j4_5_0__j4_6_0`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_5_0__j4_6_0';
*/
CREATE  TABLE `lkpCalendarDimensions` ( 
  `_calendarDimensions_key` INT(11) NOT NULL AUTO_INCREMENT , 
  `MonthWidth` INT(11) NULL DEFAULT NULL , 
  `MonthHeight` INT(11) NULL DEFAULT NULL , 
  `OneWeekWidth` INT(11) NULL DEFAULT NULL , 
  `OneWeekHeight` INT(11) NULL DEFAULT NULL , 
  `TwoWeekWidth` INT(11) NULL DEFAULT NULL , 
  `TwoWeekHeight` INT(11) NULL DEFAULT NULL , 
  `OneDayWidth` INT(11) NULL DEFAULT NULL , 
  `OneDayHeight` INT(11) NULL DEFAULT NULL , 
  `CurrentSpan` INT NULL DEFAULT 1 , 
  `userName` VARCHAR(75) NOT NULL , 
  `calendarName` VARCHAR(32) NOT NULL , 
  `version` INT(11) NOT NULL DEFAULT 1 , 
  PRIMARY KEY (`_calendarDimensions_key`) , 
  UNIQUE INDEX `_calendarDimensions_key_UNIQUE` (`_calendarDimensions_key` ASC) )  ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  
  
ALTER TABLE `lkpCalendarDimensions` 
  ADD INDEX `userName` (`userName` ASC), 
  ADD INDEX `calendarName` (`calendarName` ASC) ;
  
INSERT INTO `lkpCalendarDimensions` (`MonthWidth`, `MonthHeight`, `OneWeekWidth`, `OneWeekHeight`, `TwoWeekWidth`, `TwoWeekHeight`, `OneDayWidth`, `OneDayHeight`, `CurrentSpan`, `userName`, `calendarName`) VALUES ( 2010, 1275, 1950, 6555, 1950, 3375, 8550, 5475, 1, 'mtsadmin', 'CalendarSchedule_MouseUses'); 

CREATE TABLE cv_GenotypingPlateStatus (
	_genotypingPlateStatus_key INT(11) NOT NULL AUTO_INCREMENT,
	genotypingPlateStatus VARCHAR(32) NOT NULL,
	isDefault TINYINT(1) NOT NULL,
	areWellsEditable TINYINT(1) NOT NULL,
	isPlateSubmittable TINYINT(1) NOT NULL,
	sortOrder INTEGER  NOT NULL,
	version INT(11)  NOT NULL DEFAULT 1, 
    PRIMARY KEY (_genotypingPlateStatus_key),
    UNIQUE INDEX _genotypingPlateStatus_key_UNIQUE (_genotypingPlateStatus_key ASC))  ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

INSERT INTO cv_GenotypingPlateStatus (_genotypingPlateStatus_key, genotypingPlateStatus, isDefault, areWellsEditable, isPlateSubmittable, sortOrder, version) VALUES (1, 'Open', -1, -1, 0, 1, 1);

INSERT INTO cv_GenotypingPlateStatus (_genotypingPlateStatus_key, genotypingPlateStatus, isDefault, areWellsEditable, isPlateSubmittable, sortOrder, version) VALUES (2, 'Sealed', 0, 0, -1, 2, 1); 

CREATE TABLE GenotypingRequest (
	_genotypingRequest_key INT(11) NOT NULL AUTO_INCREMENT ,
	genotypingRequestID varchar(32) UNIQUE NOT NULL,
	dateLastSubmitted datetime,
	version INT(11) NOT NULL DEFAULT 1,
    PRIMARY KEY (_genotypingRequest_key),
   UNIQUE INDEX _genotypingRequest_key_UNIQUE (_genotypingRequest_key ASC) 

)  ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE GenotypingPlate (
	_genotypingPlate_key INT(11) NOT NULL AUTO_INCREMENT ,
	genotypingPlateID varchar(20) UNIQUE NOT NULL,
	_genotypingRequest_key INT(11) NOT NULL REFERENCES GenotypingRequest(_genotypingRequest_key),
	_genotypingPlateStatus_key INT(11) NOT NULL REFERENCES cv_GenotypingPlateStatus(_genotypingPlateStatus_key),
	sequenceNumber INT(11) NOT NULL,
	numRows INT(11) NOT NULL,
	numCols INT(11) NOT NULL,
	rowStartUnfillable INT(11),
	colStartUnfillable INT(11),
	rowEndUnfillable INT(11),
	colEndUnfillable INT(11),
	genotypingPlateIDChangedSinceLastSubmit TINYINT(1),
	version INT(11) NOT NULL DEFAULT 1,
    PRIMARY KEY (_genotypingPlate_key),
   UNIQUE INDEX _genotypingPlate_key_UNIQUE (_genotypingPlate_key ASC) 

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE GenotypingWell (
	_genotypingWell_key INT(11) NOT NULL AUTO_INCREMENT ,
	_genotypingPlate_key INT(11) NOT NULL REFERENCES GenotypingPlate(_genotypingPlate_key),
	_mouse_key INT(11) NOT NULL REFERENCES Mouse(_mouse_key),
	row INT(11) NOT NULL,
	col INT(11) NOT NULL,
	sampleNumber INT(11) NOT NULL,
	version INT(11) NOT NULL DEFAULT 1,
    PRIMARY KEY (_genotypingWell_key),
   UNIQUE INDEX _genotypingWell_key_UNIQUE (_genotypingWell_key ASC) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ; 

INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Add or Edit Genotyping Request', 'Owner', 'AddOrEditGenotypingRequest', 'Add or edit genotyping request'); 

INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Calendar Schedule for Mouse Uses', 'Sec', 'CalendarSchedule_MouseUses', 'Calendar: Used to display both projected and actual mouse use dates'); 

INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Request a Calendar', 'Sec', 'RequestMouseUseCalendar', 'Calendar: Used to specify criteria for a calendar'); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_JAX_ACCOUNT_NUMBER', '12.210.3163.56048', 'The value to submit in every export-to-TGS file as the "JAX Account #". '); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_TGS_REQUEST_PATH', '\\\\jax\\jax\\cs\\private\\appdev\\src\\komp\\DEV', 'The file path to store the file being submitted to TGS.  Note this should not include the actual filename or a trailing slash.'); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_TGS_REQUEST_FILENAME', 'komp2typ.txt', 'The filename to store the file being submitted to TGS.  Note this value should not be overridden; TGS expects this filename verbatim.'); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_TGS_RESPONSE_PATH', '\\\\jax\\jax\\cs\\private\\appdev\\src\\komp\\DEV\\outbox', 'The file path to look for response files from TGS.'); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_MTS_IMPORT_PATH', 'C:\\', 'The file path to default to for non-TGS imports via the "Import Genotype" button. '); 

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_JAXLAB_INSTALLATION', 'false', 'Is this a Jackson Laboratory installation.');

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_GESTATION_PERIOD', '21', 'The length of gestation (pregnancy) is used to determine when plug dates expire.');

ALTER TABLE `cv_MouseUse` ADD COLUMN `d1Caption` VARCHAR(32) NOT NULL DEFAULT 'D1'  AFTER `version` , 
	ADD COLUMN `d2Caption` VARCHAR(32) NOT NULL DEFAULT 'D2'  AFTER `d1Caption` , 
	ADD COLUMN `d3Caption` VARCHAR(32) NOT NULL DEFAULT 'D3'  AFTER `d2Caption` , 
	ADD COLUMN `d4Caption` VARCHAR(32) NOT NULL DEFAULT 'D4'  AFTER `d3Caption` , 
	ADD COLUMN `d5Caption` VARCHAR(32) NOT NULL DEFAULT 'D5'  AFTER `d4Caption` , 
	ADD COLUMN `d6Caption` VARCHAR(32) NOT NULL DEFAULT 'D6'  AFTER `d5Caption` , 
	ADD COLUMN `d7Caption` VARCHAR(32) NOT NULL DEFAULT 'D7'  AFTER `d6Caption` , 
	ADD COLUMN `d8Caption` VARCHAR(32) NOT NULL DEFAULT 'D8'  AFTER `d7Caption` , 
	ADD COLUMN `d9Caption` VARCHAR(32) NOT NULL DEFAULT 'D9'  AFTER `d8Caption` , 
	ADD COLUMN `d10Caption` VARCHAR(32) NOT NULL DEFAULT 'D10'  AFTER `d9Caption` , 
	ADD COLUMN `isActive` TINYINT(1) NOT NULL DEFAULT -1  AFTER `d10Caption` ;
	
ALTER TABLE `cv_MouseUse` ADD INDEX `isActive` (`isActive` ASC);

CREATE  TABLE `UpgradeHistoryLog` ( 
  `_upgradeHistoryLog_key` INT(11) NOT NULL AUTO_INCREMENT , 
  `majorVersion` INT(11) NOT NULL , 
  `minorVersion` INT(11) NOT NULL , 
  `bugFixVersion` INT(11) NOT NULL , 
  `upgradeDate` DATETIME NOT NULL , 
  `actionDescription` VARCHAR(64) NULL , 
  `actionCompleted` TINYINT(1) NOT NULL DEFAULT 0 , 
  `actionDate` DATETIME NULL , 
  `comment` VARCHAR(255) NULL , 
  `version` INT(11) NOT NULL DEFAULT 1 , 
  PRIMARY KEY (`_upgradeHistoryLog_key`) , 
  UNIQUE INDEX `_upgradeHistoryLog_key_UNIQUE` (`_upgradeHistoryLog_key` ASC) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

INSERT INTO `UpgradeHistoryLog` (`majorVersion`, `minorVersion`, `bugFixVersion`, `upgradeDate`, `actionDescription`, `actionCompleted`, `actionDate`) VALUES (4, 6, 0, CURDATE(), 'Logging began', -1, CURDATE()); 
INSERT INTO `UpgradeHistoryLog` (`majorVersion`, `minorVersion`, `bugFixVersion`, `upgradeDate`, `actionDescription`, `actionCompleted`, `comment`) VALUES (4, 6, 0, '2012-11-30', 'Mouse use age', 0, 'The age is changed from months to days');   

/*

COMMIT;

END;

//

Delimiter ; */
