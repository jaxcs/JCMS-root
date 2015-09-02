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
INSERT INTO DbSetup (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_WEB_DATE_FORMAT','MM-dd-yyyy','Value describing date format in JCMS Web');


CREATE TABLE `cv_UseScheduleStartEvent` (
  `_useScheduleStartEvent_key` INT(11) NOT NULL AUTO_INCREMENT,
  `useScheduleStartEvent` VARCHAR(16) NOT NULL,
  `useScheduleStartEventDetail` VARCHAR(128) NULL,
  `isActive` TINYINT(1) DEFAULT -1,
  `version` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`_useScheduleStartEvent_key`),
  UNIQUE INDEX `_useScheduleStartEvent_key_UNIQUE` (`_useScheduleStartEvent_key` ASC),
  UNIQUE INDEX `useScheduleStartEvent_UNIQUE` (`useScheduleStartEvent` ASC)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
INSERT INTO `cv_UseScheduleStartEvent` (`_useScheduleStartEvent_key`, `useScheduleStartEvent`, `isActive`, `version`) VALUES ('1', 'Birthdate', '-1', '1');
INSERT INTO `cv_UseScheduleStartEvent` (`_useScheduleStartEvent_key`, `useScheduleStartEvent`, `isActive`, `version`) VALUES ('2', 'Plug Date', '-1', '1');
INSERT INTO `cv_UseScheduleStartEvent` (`_useScheduleStartEvent_key`, `useScheduleStartEvent`, `isActive`, `version`) VALUES ('3', 'Calendar Date', '0', '1');


CREATE TABLE `UseScheduleTerm` (
  `_useScheduleTerm_key` int(11) NOT NULL AUTO_INCREMENT,
  `_useScheduleStartEvent_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `useScheduleTermName` varchar(32) NOT NULL,
  `useScheduleTermDetail` varchar(256) DEFAULT NULL,
  `versionNum` varchar(16) DEFAULT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '-1',
  `color` VARCHAR(16) NOT NULL DEFAULT 'ffffff',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_useScheduleTerm_key`),
  UNIQUE KEY `_useScheduleTerm_key_UNIQUE` (`_useScheduleTerm_key`),
  UNIQUE KEY `useScheduleTermName_UNIQUE` (`useScheduleTermName`),
  KEY `usescheduleterm_ibfk_1_idx` (`_Workgroup_key`),
  KEY `usescheduleterm_ibfk_2_idx` (`_useScheduleStartEvent_key`),
  CONSTRAINT `usescheduleterm_workgroup_ibfk` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usescheduleterm_useschedulestartevent_ibfk` FOREIGN KEY (`_useScheduleStartEvent_key`) REFERENCES `cv_UseScheduleStartEvent` (`_useScheduleStartEvent_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `UseScheduleList` (
  `_useScheduleList_key` INT(11) NOT NULL AUTO_INCREMENT,
  `_useScheduleTerm_key` INT(11) NOT NULL,
  `_mouseUse_key` INT(11) NOT NULL,
  `daysPostEvent` INT(11) NOT NULL, 
  `version` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`_useScheduleList_key`),
  UNIQUE INDEX `_useScheduleList_key_UNIQUE` (`_useScheduleList_key` ASC),
  INDEX `useschedulelist_ibfk_1_idx` (`_useScheduleTerm_key` ASC),
  INDEX `useschedulelist_ibfk_2_idx` (`_mouseUse_key` ASC),
  CONSTRAINT `useschedulelist_useschuduleterm_ibfk`
    FOREIGN KEY (`_useScheduleTerm_key`)
    REFERENCES `UseScheduleTerm` (`_useScheduleTerm_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `useschedulelist_mouseuse_ibfk`
    FOREIGN KEY (`_mouseUse_key`)
    REFERENCES `cv_MouseUse` (`_mouseUse_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `UseSchedule` (
  `_useSchedule_key` INT(11) NOT NULL AUTO_INCREMENT,
  `_mouse_key` INT(11) NOT NULL,
  `_useScheduleTerm_key` INT(11) NOT NULL,
  `_plugDate_key` INT(11) DEFAULT NULL,
  `startDate` datetime NOT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `done` TINYINT(1) NOT NULL DEFAULT 0,
  `version` INT(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_useSchedule_key`),
  UNIQUE KEY `_useSchedule_key_UNIQUE` (`_useSchedule_key`),
  KEY `useschedule_ibfk_1_idx` (`_mouse_key`),
  KEY `useschedule_ibfk_2_idx` (`_useScheduleTerm_key`),
  KEY `useschedule_ibfk_3_idx` (`_plugDate_key`),
  CONSTRAINT `useschedule_mouse_ibfk` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `useschedule_useschedule_term_ibfk` FOREIGN KEY (`_useScheduleTerm_key`) REFERENCES `UseScheduleTerm` (`_useScheduleTerm_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `useschedule_plugdate_ibfk` FOREIGN KEY (`_plugDate_key`) REFERENCES `PlugDate` (`_plugDate_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ControlledVocabulary` (`_controlledVocabularyGroup_key`, `displayName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`) VALUES (5, 'Use Schedule Terms', 'USESCHEDULETERMS', 1, 12, 'dba', now(), 'dba', now(), 1);
	
ALTER TABLE `MouseUsage` 
ADD COLUMN `_useSchedule_key` INT(11) DEFAULT NULL AFTER `_plugDate_key`,
ADD INDEX `mouseusage_ibfk_3_idx` (`_useSchedule_key` ASC);

ALTER TABLE `MouseUsage` 
ADD CONSTRAINT `mouseusage_useschedule_ibfk`
  FOREIGN KEY (`_useSchedule_key`)
  REFERENCES `UseSchedule` (`_useSchedule_key`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

DELIMITER //

CREATE TRIGGER updateBDUseSchedule AFTER UPDATE ON Mouse
FOR EACH ROW 
BEGIN 
	#Update Use Schedule
	UPDATE UseSchedule AS US
	JOIN UseScheduleTerm AS UST ON US._useScheduleTerm_key = UST._useScheduleTerm_key
	JOIN cv_UseScheduleStartEvent AS USSE ON UST._useScheduleStartEvent_key = USSE._useScheduleStartEvent_key
	SET startDate = NEW.BirthDate 
	WHERE US._mouse_key = NEW._mouse_key
	AND USSE.useScheduleStartEvent = 'Birthdate';
	
	#Update uses
	UPDATE MouseUsage AS MU
	JOIN UseSchedule AS US ON MU._useSchedule_key = US._useSchedule_key
	JOIN UseScheduleTerm AS UST ON US._useScheduleTerm_key = UST._useScheduleTerm_key
	JOIN cv_UseScheduleStartEvent AS USSE ON UST._useScheduleStartEvent_key = USSE._useScheduleStartEvent_key
	SET MU.projectedDate = DATE_ADD(NEW.birthDate, INTERVAL MU.useAge DAY)
	WHERE US._mouse_key = NEW._mouse_key
	AND USSE.useScheduleStartEvent = 'Birthdate';
END;//

CREATE TRIGGER updatePDUseSchedule AFTER UPDATE ON PlugDate
FOR EACH ROW 
BEGIN 
	#Update Use Schedule
	UPDATE UseSchedule AS US
	SET US.startDate = NEW.plugDate 
	WHERE US._plugDate_key = NEW._plugDate_key;

	#update mouse uses
	#projected date
	UPDATE MouseUsage AS MU
	SET MU.projectedDate = DATE_ADD(NEW.plugDate, INTERVAL DATEDIFF(MU.projectedDate, OLD.plugDate) DAY)
	WHERE NEW._plugDate_key = MU._plugDate_key;
	#use age
	UPDATE MouseUsage AS MU
	SET MU.useAge = DATEDIFF(MU.projectedDate, NEW.plugDate)
	WHERE NEW._plugDate_key = MU._plugDate_key;
END;//

DELIMITER ;
  
UPDATE dbInfo SET
	dbVers = 124,
	webReleaseNum= '3.6.0',
	releaseNum= '6.1.2',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

