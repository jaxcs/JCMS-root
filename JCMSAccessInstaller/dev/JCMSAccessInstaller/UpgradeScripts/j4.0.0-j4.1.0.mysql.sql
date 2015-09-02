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


-- MySQL upgrade for JCMS 4.0.0 to JCMS 4.1.0
-- Tested on MySQL 5.0 on Windows 

Delimiter //

DROP PROCEDURE IF EXISTS `j4_0_0__j4_1_0` //

CREATE  PROCEDURE `j4_0_0__j4_1_0`()
BEGIN

-- Declaration Section
  DECLARE myVar VARCHAR(64);

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_0_0__j4_1_0';

SELECT '-- 1 ---';
-- Drop Unique, add regular index
ALTER TABLE `ContainerHistory`
DROP INDEX `_container_key`, 
ADD INDEX `_container_key` (`_container_key` ASC) ;

SELECT '-- 2 ---';
-- Drop Unique, add regular index
ALTER TABLE `HealthLevelHistory`
DROP INDEX `_room_key`, 
ADD INDEX `_room_key` (`_room_key` ASC) ;

SELECT '-- 3 ---';
-- Add Unique index
ALTER TABLE `cv_ContainerStatus` 
ADD UNIQUE INDEX `containerStatus_UNIQUE` (`containerStatus` ASC) ;


--  <command name="DO_SQL" onError="continue">
SELECT '-- 4 ---';
SET myVar = NULL;
-- Is there an E life status in the database.
SELECT `lifeStatus` INTO myVar FROM `LifeStatus` 
    WHERE lifeStatus='E';
  
-- If NULL then insert, if not do nothing.
IF  myVar IS NULL THEN
    INSERT INTO LifeStatus (lifeStatus, description, exitStatus) 
        VALUES ('E','Euthanized', true);
END IF;


SELECT '-- 5 ---';
SET myVar = NULL;
  -- Is there a JCMS_WARN_LITTER_NOT_UPDATED in the database?
SELECT `MTSValue` INTO myVar FROM `DbSetup` 
	WHERE MTSVar='JCMS_WARN_LITTER_NOT_UPDATED';
  
  -- If NULL then insert, if not update.
IF  myVar IS NULL THEN
    INSERT INTO DbSetup ( `MTSValue`, `MTSVar`, `MTSVarDescription` ) 
        VALUES ('true','JCMS_WARN_LITTER_NOT_UPDATED','true or false, if true, add mice at weaning will warn if litter record was not updated.');
END IF;

      
SELECT '-- 6 ---';
SET myVar = NULL;
  -- Is there a JCMS_DAYS_TO_GENOTYPE in the database?
SELECT `MTSValue` INTO myVar FROM `DbSetup` 
	WHERE MTSVar='JCMS_DAYS_TO_GENOTYPE';
  
  -- If NULL then insert, if not update.
IF  myVar IS NULL THEN
INSERT INTO DbSetup ( `MTSValue`, `MTSVar`, `MTSVarDescription` ) 
    VALUES (14, 'JCMS_DAYS_TO_GENOTYPE', 'The number of days from the birth date to when the pups should be genotyped.');
END IF;

SELECT '-- 7 ---';  
SET myVar = NULL;
  -- Is there a Genotype Work Report in the DbFormPrivileges table?
SELECT `formName` INTO myVar FROM `DbFormPrivileges` 
	WHERE formName ='Genotype Work Report';
-- If NULL then insert, if not update.
IF  myVar IS NULL THEN
    INSERT INTO DbFormPrivileges (`formName`,`privilegeLevel` ,`completeFormName` ,`description`  ) 
      VALUES ('Genotype Work Report','Sec','GenotypeWorkReport','Reports: Genotyping - List litters that are expected to be genotyped based on a date range.');
END IF;

SELECT '-- 8 ---';
SET myVar = NULL;
-- Is there an Var then do nothing.
SELECT `MTSVar` INTO myVar FROM `DbSetup` 
    WHERE `MTSVar`='JCMS_IMPORT_EXP_DATA_MICE_MUST_BE_PRESELECTED';
  
-- If NULL then insert, if not do nothing.
IF  myVar IS NULL THEN
    INSERT INTO DbSetup (MTSVar, MTSValue, MTSVarDescription) 
	   VALUES ('JCMS_IMPORT_EXP_DATA_MICE_MUST_BE_PRESELECTED', 'true', 'true or false If false\, any mouse IDs not pre-selected will be automatically added to the plan and test.');
END IF;

SELECT '-- 9 ---';
SET myVar = NULL;
-- Is there an Var then do nothing.
SELECT `MTSVar` INTO myVar FROM `DbSetup` 
    WHERE `MTSVar`='JCMS_MAX_IMPORT_EXP_DATA_ERRORS';
  
-- If NULL then insert, if not do nothing.
IF  myVar IS NULL THEN
    INSERT INTO DbSetup (MTSVar, MTSValue, MTSVarDescription) 
	    VALUES ('JCMS_MAX_IMPORT_EXP_DATA_ERRORS', '10','Import  Experimental  data . When this number of errors is reached\, verification stops and a report is printed.');
END IF;

COMMIT;

END;

//

Delimiter ;

CALL `j4_0_0__j4_1_0`();