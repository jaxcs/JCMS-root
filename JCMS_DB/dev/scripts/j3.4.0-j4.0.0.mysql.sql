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

 
-- MySQL upgrade for JCMS 3.4.0 to JCMS 4.0.1
-- Tested on MySQL 5.0 on Windows 

Delimiter //

-- Drop Constraint Without Name -----------------------------------------
DROP PROCEDURE IF EXISTS `DropConstraintWithoutName` //

CREATE PROCEDURE `DropConstraintWithoutName`(IN myTable VARCHAR(32), IN myCol VARCHAR(32), IN refTable VARCHAR(32) , IN refCol VARCHAR(32) )
BEGIN

DECLARE myConstraintName VARCHAR(32)  ;
DECLARE myDatabase VARCHAR(32)  ;

SELECT DATABASE() INTO myDatabase;


SELECT CONSTRAINT_NAME INTO myConstraintName

FROM information_schema.key_column_usage
WHERE CONSTRAINT_SCHEMA =  myDatabase AND
            TABLE_NAME = myTable AND
            COLUMN_NAME = myCol AND
            REFERENCED_TABLE_NAME = refTable AND
            REFERENCED_COLUMN_NAME = refCol;


IF NULL <=> myConstraintName THEN
    Select 'WHOA DUDE';
    Select 'I can not find the constraint you are attempting to delete';
ELSE

        SET @sql = CONCAT(' ALTER TABLE ', myTable );
        SET @sql = CONCAT(@sql , ' DROP FOREIGN KEY '  );
        SET @sql = CONCAT(@sql , myConstraintName  );
        SET @sql = CONCAT(@sql , ' ; '  );

        PREPARE stmt1 FROM @sql;
        EXECUTE stmt1;

END IF;



END

//


DROP PROCEDURE IF EXISTS `j3_4_0__j4_0_0` //

CREATE  PROCEDURE `j3_4_0__j4_0_0`()
BEGIN

-- Declaration Section
  DECLARE myVar VARCHAR(20);


-- Code Section
SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_4_0__j4_0_0';

SELECT '-- 1 ---';
  -- Is there a JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS in the database.
  SELECT `MTSValue` INTO myVar FROM `DbSetup` 
	WHERE MTSVar='JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS';
  
  -- If NULL then insert, if not update.
  IF  myVar IS NULL THEN
      INSERT INTO DbSetup ( `MTSValue`, `MTSVar`, `MTSVarDescription` ) 
      VALUES ( 'false','JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS',
        'true of false; If true\, mice with an exit status such as dead\, euthanized\, missing\, shipped\, etc. will print on cage cards.');
  ELSE
      UPDATE DbSetup 
      SET MTSVarDescription=
        'true or false if true\, mice with an exit status such as dead\, euthanized\, missing\, shipped\, etc. will print on cage cards.'
      WHERE MTSVar = 'JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS';
  END IF;

SELECT '-- 2 --';
  -- Is there a PrintSampleLables in the database.
  SELECT `formName` INTO myVar 
  FROM `DbFormPrivileges` 
  WHERE DbFormPrivileges.`completeFormName`='PrintSampleLables';

  -- If NULL then insert, if not update.
  IF  myVar IS NULL THEN
    INSERT INTO DbFormPrivileges (`formName`,`privilegeLevel`,`completeFormName`,`description`) 
        VALUES ('Print Sample Lables','Sec','PrintSampleLables','Select samples for printing sample label report.');
  ELSE
    UPDATE DbFormPrivileges 
    SET completeFormName = 'PrintSampleLabels' 
    WHERE (DbFormPrivileges.`completeFormName`='PrintSampleLables');
  END IF ;

SELECT '-- 3 --';
-- Copy the PenGroup Table to MyPenGroup to manipulate.
  CREATE TABLE IF NOT EXISTS `MyPenGroup` 
	(`_pen_key` INTEGER NOT NULL,
	`penID` INTEGER NOT NULL,
	`room` VARCHAR (8) NULL,
	`penStatus` TINYINT(1) NOT NULL DEFAULT 0,
	`beginDate` DATETIME NOT NULL,
	`healthLevel` SMALLINT NOT NULL,
	UNIQUE INDEX `_pen_key`(`_pen_key`),
	UNIQUE INDEX `Pen#`(`penID`),
	PRIMARY KEY (`_pen_key`)) 
	ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 4 --';
  INSERT INTO MyPenGroup SELECT * FROM PenGroup;

SELECT '-- 5 --';
  UPDATE MyPenGroup 
  SET room='UNKNOWN' 
  WHERE NOT `_pen_key` IN ( SELECT `_pen_key` FROM PenGroup WHERE NOT room = '' );

SELECT '-- 6 --';
-- cv_HealthLevel
  CREATE TABLE `cv_HealthLevel` 
  (`_healthLevel_key` INTEGER AUTO_INCREMENT,
  `healthLevel` VARCHAR (8) NOT NULL,
  `description` VARCHAR (32) NULL,
  UNIQUE INDEX `healthLevel`(`healthLevel`),
  PRIMARY KEY (`_healthLevel_key`),
  INDEX `_healthLevel_key`(`_healthLevel_key`)) 
  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 7 --';
  INSERT INTO cv_HealthLevel 
    (`_healthLevel_key`,healthLevel,description) 
	VALUES (1, '1','Health Level  1 Description');

SELECT '-- 8 --';
  INSERT INTO cv_HealthLevel 
	(`_healthLevel_key`,healthLevel,description) 
	VALUES (2, '2','Health Level  2 Description');

SELECT '-- 9 --';
  INSERT INTO cv_HealthLevel 
	(`_healthLevel_key`,healthLevel,description) 
	VALUES (3, '3','Health Level  3 Description');

SELECT '-- 10 --';
  INSERT INTO cv_HealthLevel 
	(`_healthLevel_key`,healthLevel,description) 
	VALUES (4, '4','Health Level  4 Description');

SELECT '-- 11 --';
-- cv_HealthLevel
  INSERT INTO cv_HealthLevel 
  (healthLevel, description) 
	SELECT DISTINCT  HealthLevel, 'Health Level Description' as description 
	FROM MyPenGroup 
	Where healthLevel > 4 OR NOT (healthLevel >= 1);

SELECT '-- 12 --';
  ALTER TABLE cv_HealthLevel MODIFY COLUMN `_healthLevel_key` INTEGER NULL;

SELECT '-- 13 --';
  ALTER TABLE cv_HealthLevel ADD INDEX (`healthLevel`);

SELECT '-- 14 --';
  ALTER TABLE cv_HealthLevel ADD INDEX (`_healthLevel_key`);
  
SELECT '-- 15 --';
-- Room
  CREATE TABLE `Room` (
  `_room_key` INTEGER AUTO_INCREMENT, 
  `roomName` VARCHAR (8) NOT NULL, 
  `_healthLevelHistory_key` INTEGER NOT NULL,
  PRIMARY KEY (`_room_key`) )  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 16 --';
  INSERT INTO Room 
	( roomName, _healthLevelHistory_key ) 
	SELECT DISTINCT room AS roomName, 1 AS _healthLevelHistory_key 
	FROM MyPenGroup;

SELECT '-- 17 --';
  UPDATE Room SET `_healthLevelHistory_key`= `_room_key`;

SELECT '-- 18 --';
  ALTER TABLE Room MODIFY COLUMN `_room_key` INTEGER  NOT NULL;

SELECT '-- 19 --';
  ALTER TABLE Room ADD CONSTRAINT UNIQUE INDEX (`_healthLevelHistory_key`);

SELECT '-- 20 --';
  ALTER TABLE `Room` ADD CONSTRAINT UNIQUE INDEX (`_room_key`);

SELECT '-- 21 --';
  ALTER TABLE `Room` ADD INDEX (`roomName`);
  
SELECT '-- 22 --';
-- HealthLevelHistory
  CREATE TABLE `HealthLevelHistory` (
	`_healthLevelHistory_key` INTEGER  NOT NULL, 
	`_room_key` INTEGER  NOT NULL, 
	`_healthLevel_key` INTEGER  NOT NULL, 
	`startDate` DATETIME NOT NULL) ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 23 --';
  INSERT INTO `HealthLevelHistory` (
  `_healthLevelHistory_key`,
  `_room_key`,
  `_healthLevel_key`,
  `startDate`) 
  SELECT DISTINCT `_room_key`,`_room_key`, 2, Now()
      FROM Room
      WHERE NOT (roomName = '');

SELECT '-- 24 --';
  ALTER TABLE `HealthLevelHistory` 
	ADD PRIMARY KEY `HealthLevelHistoryPK`(`_healthLevelHistory_key`);

SELECT '-- 25--';
  ALTER TABLE `HealthLevelHistory` ADD INDEX `HealthLevelHistory`(`_healthLevel_key`);

SELECT '-- 26 --';
  ALTER TABLE `HealthLevelHistory` 
	ADD CONSTRAINT UNIQUE INDEX (`_healthLevelHistory_key`);

SELECT '-- 27 --';
  ALTER TABLE `HealthLevelHistory` ADD CONSTRAINT UNIQUE INDEX (`_room_key`);

SELECT '-- 28 --';
  ALTER TABLE `HealthLevelHistory` ADD INDEX (`startDate`);
  
SELECT '-- 29 --';
-- cv_ContainerStatus
CREATE TABLE `cv_ContainerStatus` (
  `_containerStatus_key` INTEGER NOT NULL ,
  `containerStatus` VARCHAR(8) NOT NULL ,
  `billable` TINYINT(1) NULL DEFAULT 1 ,
  PRIMARY KEY (`_containerStatus_key`) ,
  UNIQUE INDEX `idnew_table_UNIQUE` (`_containerStatus_key` ASC) ) ENGINE =INNODB CHARACTER SET utf8;



SELECT '-- 30 --';
  INSERT INTO cv_ContainerStatus (`_containerStatus_key`,containerStatus,billable) 
	VALUES ( 1, 'proposed', false);

SELECT '-- 31 --';
  INSERT INTO cv_ContainerStatus (`_containerStatus_key`,containerStatus,billable) 
	VALUES ( 2, 'active', true);

SELECT '-- 32 --';
  INSERT INTO cv_ContainerStatus (`_containerStatus_key`,containerStatus,billable) 
	VALUES ( 3, 'retired', false);
  
SELECT '-- 35 --';
-- Container
  CREATE TABLE `Container` (
	`_container_key` INTEGER  NOT NULL, 
	`containerID` INTEGER  NOT NULL, 
	`containerName` VARCHAR (16) NULL, 
	`comment` VARCHAR (64) NULL,
    `_containerHistory_key` INTEGER  NOT NULL)  ENGINE =INNODB CHARACTER SET utf8;


SELECT '-- 36 --';
  INSERT INTO `Container` (`_container_key`, `containerID`,`_containerHistory_key`) 
	SELECT DISTINCT MyPenGroup.`_pen_key` as `_container_key`, 
		MyPenGroup.penID as `containerID`,  
		MyPenGroup.`_pen_key` as `_containerHistory_key`
      FROM MyPenGroup;

SELECT '-- 37 --';
  UPDATE Container 
	SET Comment = CONCAT('Pen format converted on ',CONVERT(Now(), CHAR(20))) ;

SELECT '-- 38 --';
  ALTER TABLE `Container` ADD CONSTRAINT UNIQUE INDEX (`_container_key`);

SELECT '-- 39 --';
  ALTER TABLE `Container` ADD CONSTRAINT UNIQUE INDEX (`_containerHistory_key`);

SELECT '-- 40 --';
  ALTER TABLE `Container` ADD CONSTRAINT UNIQUE INDEX (`containerID`);

SELECT '-- 41 --';
  ALTER TABLE `Container` ADD PRIMARY KEY `ContainerPK`(`_container_key`);

SELECT '-- 42 --';
  ALTER TABLE `Container` ADD INDEX (`containerName`);
  
SELECT '-- 43 --';
-- ContainerHistory
  CREATE TABLE `ContainerHistory` (
	`_containerHistory_key` INTEGER NOT NULL, 
	`_room_key` INTEGER NOT NULL, 
	`_container_key` INTEGER NOT NULL, 
	`actionDate` DATETIME NOT NULL, 
	`_containerStatus_key` INTEGER NOT NULL)  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 44 --';
  INSERT INTO `ContainerHistory` (
	`_containerHistory_key`, 
	`_room_key`, 
	`_container_key`, 
	`actionDate`, 
	`_containerStatus_key`) 
	SELECT MyPenGroup.`_pen_key` as `_containerHistory_key`, 
		Room.`_room_key` as `_room_key`, 
		MyPenGroup.`_pen_key`  as `_container_key`, 
		MyPenGroup.beginDate  as `actionDate`,
		MyPenGroup.penStatus  as `_containerStatus_key`
        FROM MyPenGroup LEFT JOIN Room ON Room.roomName = MyPenGroup.room;

SELECT '-- 45 --';
  UPDATE `ContainerHistory` 
	SET `_containerStatus_key` = 2 WHERE `_containerStatus_key` = -1;

SELECT '-- 46 --';
  UPDATE `ContainerHistory` 
	SET `_containerStatus_key` = 3 WHERE `_containerStatus_key` = 0;

SELECT '-- 47 --';
  ALTER TABLE `ContainerHistory` 
	ADD CONSTRAINT UNIQUE INDEX (`_containerHistory_key`);

SELECT '-- 48 --';
  ALTER TABLE `ContainerHistory` 
	ADD PRIMARY KEY `ContainerHistoryPK`(`_containerHistory_key`);

SELECT '-- 49 --';
  ALTER TABLE `ContainerHistory` 
	ADD CONSTRAINT UNIQUE INDEX (`_container_key`);

SELECT '-- 50 --';
  ALTER TABLE 
	`ContainerHistory` ADD INDEX (`_containerStatus_key`);

SELECT '-- 51 --';
  ALTER TABLE `ContainerHistory` ADD INDEX (`_room_key`);

SELECT '-- 52 --';
    ALTER TABLE ContainerHistory ADD INDEX (actionDate);

SELECT '-- 53 --';
  ALTER TABLE `ContainerHistory` ADD INDEX (`_room_key`);
  
SELECT '-- 54 --';
-- Add Index to Mouse Table
--  ALTER TABLE `Mouse` ADD INDEX (`_pen_key`);
  
SELECT '-- 55 --';
-- Insert new variables on DbSetup
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_AUTO_RETIRE_PENS',
    'true or false if true automatically retire a pen when it becomes empty or all occupants are not alive.');

SELECT '-- 56 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_BILL_PARTIAL_FIRST_DAY',
    'true or false  if true a pen is counted for billing on the day it is created or moved into a room.');

SELECT '-- 57 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_BILL_PARTIAL_LAST_DAY',
    'true or false If true a pen is counted for billing on the day it is retired or moved out of a room.');

SELECT '-- 58 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('active','JCMS_DEFAULT_CONTAINER_STATUS',
    'Specify the default value for pen status must match a value in the pen status table.');

SELECT '-- 59 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('E','JCMS_DEFAULT_EXIT_TERM',
    'The default term used when exiting mice from the colony\, usually E or K must match a value in the life status table. May be overridden.');

SELECT '-- 60 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('true','JCMS_PEN_NAMES_INCREMENT_RIGHTMOST',
    'true or false If true\, increment the rightmost numeric portion of the pen name; if false the leftmost.');

SELECT '-- 61 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_SORT_BY_PEN_NAME',
    'true or false If true then lists with pen ID and pen name will sort alphabetically by pen name instead of pen ID.');

SELECT '-- 62 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('true','JCMS_USING_HEALTH_LEVEL',
    'true or false If false the room health level will not be displayed on most forms.');

SELECT '-- 63 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('true','JCMS_USING_PEN_COMMENTS',
    'true or false If false the pen comment field will not be displayed on most forms.');

SELECT '-- 64 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('true','JCMS_USING_PEN_NAMES',
    'true or false If false pen names will not be displayed on most forms.');

SELECT '-- 65 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_WARN_DUPLICATE_PEN_NAME',
    'true or false If true warn if a duplicate pen name is used.');

SELECT '-- 66 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_AUTO_RETIRE_MATINGS',
    'true or false If true, automatically retire a mating when the sire and dam(s) all have an exit life status.');

SELECT '-- 67 --';
  INSERT INTO DbSetup (`MTSValue`,`MTSVar`,`MTSVarDescription`) 
	VALUES ('false','JCMS_IMPORT_EXP_DATA_ALLOW_MULTIPLE',
    'true or false If true, when importing exp data that is not associated with an exp plan, allow more than 1 record for a test type/mouse combo.');

SELECT '-- 68 --';
-- Update DbSetup
  UPDATE DbSetup 
	SET MTSVarDescription=
	'true or false If true the mating ID is automatically incremented after activating a mating. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ACTIVATE_MATINGS_INCREMENT';

SELECT '-- 69 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true the mouse ID is automatically incremented after adding a mouse at weaning. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_AT_WEAN_INCREMENT';

SELECT '-- 70 --';
  UPDATE DbSetup 
	SET MTSVarDescription=
    'true or false If true the mouse ID is automatically incremented after adding a genotype. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_GENOTYPE_INCREMENT';

SELECT '-- 71 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the litter ID is automatically incremented after adding a litter. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_LITTER_INCREMENT';

SELECT '-- 72 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the litter ID is automatically incremented after adding a litter with pups. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_LITTER_PUPS_INCREMENT';

SELECT '-- 73 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the mouse ID is automatically incremented after adding a mouse. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_MOUSE_INCREMENT';

SELECT '-- 74 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the mouse ID is automatically incremented after adding a mouse use. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_ADD_MOUSE_USE_INCREMENT';

SELECT '-- 75 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Character(s) displayed/printed as part of a genotype to indicate high confidence in an allele. May be blank.' 
	WHERE MTSVar = 'JCMS_ALLELE_CONF_HIGH';

SELECT '-- 76 --';
  UPDATE DbSetup SET MTSVarDescription=
    'Character(s) displayed/printed as part of a genotype to indicate low confidence in an allele. May be blank. Ex: use ? To display Abc +?/+? For low confidence in both alleles.'
    WHERE MTSVar = 'JCMS_ALLELE_CONF_LOW';

SELECT '-- 77 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Specify separators to display around the alleles. Ex: specify [] to get Abc[+/+] or leave blank for Abc +/+' 
	WHERE MTSVar = 'JCMS_ALLELE_GENE_SEPARATORS';

SELECT '-- 78 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true user may add generations to the Generations table on the fly.' 
	WHERE MTSVar = 'JCMS_ALLOW_USERDEFINED_GENERATIONS';

SELECT '-- 79 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true user may add strains to the Strains table (via the Strains form) on the fly.' 
	WHERE MTSVar = 'JCMS_ALLOW_USERDEFINED_STRAINS';

SELECT '-- 80 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false If true the generation for a litter will be automatically incremented when creating matings. May be overridden on the form.'
      WHERE MTSVar = 'JCMS_AUTOINCREMENT_GENERATION';

SELECT '-- 81 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true the pen ID is automatically incremented when adding new pens.' 
	WHERE MTSVar = 'JCMS_CREATE_PEN_INCREMENT';

SELECT '-- 82 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the mating ID is automatically incremented when using the design retire mating form. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_DESIGN_RETIRE_MATINGS_INCREMENT';

SELECT '-- 83 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the litter ID is automatically incremented after editing a litter. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_EDIT_LITTER_INCREMENT';

SELECT '-- 84 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the mouse ID is automatically incremented after editing a mouse. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_EDIT_MOUSE_INCREMENT';

SELECT '-- 85 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true the mouse ID is automatically incremented after editing a use. May be overridden on the form.'
    WHERE MTSVar = 'JCMS_EDIT_MOUSE_USE_INCREMENT';

SELECT '-- 86 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false Must be set to true to allow importing genotypes.' 
    WHERE MTSVar = 'JCMS_ENABLE_GENOTYPE_IMPORT';
  
SELECT '-- 87 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false If true then user is only allowed to set litter strain to pre-approved matings. May be overridden on the form.'
      WHERE MTSVar = 'JCMS_ENFORCE_APPROVED_MATINGS';

SELECT '-- 88 --';
  UPDATE DbSetup SET MTSVarDescription=
    'the number of days from the birth date to when a litter should be weaned - used for late weanings long wean time.'
    WHERE MTSVar = 'JCMS_EXT_WEAN_TIME';

SELECT '-- 89 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true females are the first to be assigned mouse IDs when adding mice with a bulk add.' 
	WHERE MTSVar = 'JCMS_FEMALES_FIRST';

SELECT '-- 90 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If true\, increment the rightmost numeric portion of the generation; if false the leftmost.' 
    WHERE MTSVar = 'JCMS_GENERATION_INCREMENT_RIGHTMOST';

SELECT '-- 91 --';
  UPDATE DbSetup SET MTSVarDescription=
    'true or false If false\, any mouse IDs not pre-selected will be automatically added to the plan and test.' 
    WHERE MTSVar = 'JCMS_IMPORT_EXP_DATA_MICE_MUST_BE_PRESELECTED';

SELECT '-- 92 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true\, increment the rightmost numeric portion of the litter ID; if false the leftmost.' 
	WHERE MTSVar = 'JCMS_LITTERID_INCREMENT_RIGHTMOST';

SELECT '-- 93 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true the litter numbers recycle after 10 litters\, appending a character to the number.' 
	WHERE MTSVar = 'JCMS_LOOP_LITTER_NUMBERS';

SELECT '-- 94 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Import Experimental data . When this number of errors is reached\, verification stops and a report is printed.'
    WHERE MTSVar = 'JCMS_MAX_IMPORT_EXP_DATA_ERRORS';

SELECT '-- 95 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true\, increment the rightmost numeric portion of the mouse ID; if false the leftmost.' 
	WHERE MTSVar = 'JCMS_MOUSEID_INCREMENT_RIGHTMOST';

SELECT '-- 96 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false If true the mating ID is automatically incremented after retiring a mating. May be overridden on the form.'
      WHERE MTSVar = 'JCMS_RETIRE_MATINGS_INCREMENT';

SELECT '-- 97 --';
  UPDATE DbSetup SET MTSVarDescription=
	'the number of days from the birth date to when a litter should normally be weaned.' 
	WHERE MTSVar = 'JCMS_STANDARD_WEAN_TIME';

SELECT '-- 98 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false If true the strain name will appear first (to the left of the JR number) in all the dropdown lists.'
    WHERE MTSVar = 'JCMS_STRAINNAME_FIRST';

SELECT '-- 99 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false Used by the Add Sample form for debugging\, should be set to false.' 
	WHERE MTSVar = 'JCMS_WRITE_FAILED_TRANSACTIONS';

SELECT '-- 100 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The name of the cage card report for 1-pen wean cage cards.' 
	WHERE MTSVar = 'MTS_1PEN_WEAN_CAGE_CARD';

SELECT '-- 101 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The name of the cage card report for 2-pen wean cage cards.' 
	WHERE MTSVar = 'MTS_2PEN_WEAN_CAGE_CARD';

SELECT '-- 102 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false if true\, then many forms get all data entry fields colored after user hits submit. Color is cleared after user visits the field.'
      WHERE MTSVar = 'MTS_AUTO_COLOR';

SELECT '-- 103 --';
  UPDATE DbSetup SET MTSVarDescription=
	'on or off if on litter numbers are automatically generated for matings.' 
	WHERE MTSVar = 'MTS_AUTO_LITTER_NUMS';

SELECT '-- 104 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false\, if true the dams and sire are automatically incremented on the handheld trio/pair mating form. May be overridden on the form.'
      WHERE MTSVar = 'MTS_AUTOINCR_DAMS_SIRES';

SELECT '-- 105 --';
  UPDATE DbSetup SET MTSVarDescription=
	'A note that will be printed on all Detail cage cards.' 
	WHERE MTSVar = 'MTS_CAGE_CARD_DETAIL_NOTE';

SELECT '-- 106 --';
  UPDATE DbSetup SET MTSVarDescription=
    'on or off if on then auto increment is the default on forms with functions that do not have a specific auto increrment setup variable.'
    WHERE MTSVar = 'MTS_DEFAULT_AUTO_INCREMENT';

SELECT '-- 107 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Specify the default cause of death. Must match a value in the cv_CauseOfDeath table.' 
	WHERE MTSVar = 'MTS_DEFAULT_COD';

SELECT '-- 108 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Specify the default room health level. Must match a value in the Health Level table.' 
	WHERE MTSVar = 'MTS_DEFAULT_HEALTH_LEVEL';

SELECT '-- 109 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Specify the default mouse origin. Must match a value in the cv_MouseOrigin table.' 
	WHERE MTSVar = 'MTS_DEFAULT_MOUSE_ORIGIN';

SELECT '-- 110 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Specify the default mouse room. Must match a value in the Room table.' 
	WHERE MTSVar = 'MTS_DEFAULT_MOUSE_ROOM';

SELECT '-- 111 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false if true the print cage card option on the handheld trio/pair mating form is set on. May be overridden on the form.'
      WHERE MTSVar = 'MTS_DEFAULT_PRINTCARDS';

SELECT '-- 112 --';
  UPDATE DbSetup SET MTSVarDescription=
      'true or false if true indicates using a base mouse id on the handheld add litter form. May be overridden on the form.'
      WHERE MTSVar = 'MTS_DEFAULT_USE_BASEMOUSE_ID';

SELECT '-- 113 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The name of the cage card report for detail cage cards.' 
	WHERE MTSVar = 'MTS_DETAIL_CAGE_CARD';

SELECT '-- 114 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Subtract this number of days from todays date to get the date of birth.' 
	WHERE MTSVar = 'MTS_DOB_ROLLBACK_OFFSET';

SELECT '-- 115 --';
  UPDATE DbSetup SET MTSVarDescription=
    'Specify an email address that users can send JCMS support questions to. Used as the Report a problem link on  the JCMS welcome window.'
    WHERE MTSVar = 'MTS_HELP_EMAIL';

SELECT '-- 116 --';
  UPDATE DbSetup SET MTSVarDescription=
    'Users will be warned if they try to import more than	 this number of mice at once (only effects bulk imports of mice).'
    WHERE MTSVar = 'MTS_IMPORT_MAX_WARNING';

SELECT '-- 117 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Name of this JCMS installation (anything you want to call it).' 
	WHERE MTSVar = 'MTS_INSTALLATION_NAME';

SELECT '-- 118 --';
  UPDATE DbSetup SET MTSVarDescription=
      'A short string of characters that are prefixed on litter IDs generated by JCMS (not all litter IDs are generated by JCMS).'
      WHERE MTSVar = 'MTS_LITTER_ID_PREFIX';

SELECT '-- 119 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Name of the main button bar form displayed when user clicks start workstation  from welcome window.' 
	WHERE MTSVar = 'MTS_MAIN_BUTTON_BAR';

SELECT '-- 120 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The name of the cage card report for mating cage cards.' 
	WHERE MTSVar = 'MTS_MATING_CAGE_CARD';

SELECT '-- 121 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The name of the cage card report for mating cage cards style 2 handheld only\, print cage card form.' 
	WHERE MTSVar = 'MTS_MATING_CAGE_CARD2';

SELECT '-- 122 --';
  UPDATE DbSetup SET MTSVarDescription=
	'A short string of characters that are prefixed on mating IDs when printed on some cage cards.' 
	WHERE MTSVar = 'MTS_MATING_ID_PREFIX';

SELECT '-- 123 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Maximum number of live mice in any pen.' 
	WHERE MTSVar = 'MTS_MAX_MICE_PER_PEN';

SELECT '-- 124 --';
  UPDATE DbSetup SET MTSVarDescription=
    'A short string of characters that are prefixed on mouse IDs generated by JCMS (not all mouse IDs are generated by JCMS).'
    WHERE MTSVar = 'MTS_MOUSE_ID_PREFIX';

SELECT '-- 125 --';
  UPDATE DbSetup SET MTSVarDescription=
    'this variable sets the number of litter numbers that are assigned to a mating. It should be set to a value bigger than the max number of litters you ever expect. Suggested values are 10 or 100.'
    WHERE MTSVar = 'MTS_NUM_AUTO_LITTER_NUMS';

SELECT '-- 126 --';
  UPDATE DbSetup SET MTSVarDescription=
	'A short string of characters that are prefixed on pen IDs when printed on some cage cards.' 
	WHERE MTSVar = 'MTS_PEN_ID_PREFIX';

SELECT '-- 127 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Name of lab PI who owns colonies tracked by JCMS\, printed on some cage cards.' 
	WHERE MTSVar = 'MTS_PI_NAME';

SELECT '-- 128 --';
  UPDATE DbSetup SET MTSVarDescription=
	'Phone numbers\, printed on mating card.' 
	WHERE MTSVar = 'MTS_PI_PHONE';

SELECT '-- 129 --';
  UPDATE DbSetup SET MTSVarDescription=
	'true or false if false\, then it is required that a cage card is printed for all pens.' 
	WHERE MTSVar = 'MTS_RELAXED_PEN_NUMS';

SELECT '-- 130 --';
  UPDATE DbSetup SET MTSVarDescription=
    'This variable will trigger a warning from the handheld wean and exit form when the number of affected mice exceeds this value.'
    WHERE MTSVar = 'MTS_THRESHOLD_MICE_BATCH_OPERATION';

SELECT '-- 131 --';
  UPDATE DbSetup SET MTSVarDescription=
    'The name of the report used to print sample labels from the Print Sample Label form.' 
    WHERE MTSVar = 'JCMS_SAMPLE_LABEL_REPORT';

SELECT '-- 132 --';
  UPDATE DbSetup SET MTSVarDescription=
	'The root directory where JCMS data files are stored' 
	WHERE MTSVar = 'JCMS_DATA_FILE_DIRECTORY';

SELECT '-- 133 --';
-- Delete DbSetup Variables
  DELETE FROM DbSetup WHERE MTSVar = 'JCMS_USE_LITTERID_FIELDWIDTH';

SELECT '-- 134 --';
  DELETE FROM DbSetup WHERE MTSVar = 'JCMS_USE_LITTERID_LEADING_ZEROS';

SELECT '-- 135 --';
  DELETE FROM DbSetup WHERE MTSVar = 'JCMS_USE_MOUSEID_FIELDWIDTH';

SELECT '-- 136 --';
  DELETE FROM DbSetup WHERE MTSVar = 'JCMS_USE_MOUSEID_LEADING_ZEROS';
  
SELECT '-- 137 --';
-- Update DbFormPrivileges
  UPDATE DbFormPrivileges SET completeFormName = 'AddPen' 
	WHERE (DbFormPrivileges.`completeFormName` ='CreatePen');

SELECT '-- 138 --';
  UPDATE DbFormPrivileges SET completeFormName = 'AddMatings' 
	WHERE (DbFormPrivileges.`completeFormName` ='DoMatings');

SELECT '-- 139 --';
  INSERT INTO DbFormPrivileges (
	`formName`, 
	`privilegeLevel` , 
	`completeFormName` , 
	`description`  
	) VALUES (
	'Cage Use Report Summary',
	'Owner',
	'CageUseReportSummary',
	'Summary of Billable Pens.');

SELECT '-- 140 --';
  INSERT INTO DbFormPrivileges (
	`formName`,  
	`privilegeLevel` , 
	`completeFormName` , 
	`description` 
	) VALUES (
	'Cage Use Report',
	'Owner',
	'CageUseReport',
    'Allows the user to see how many Pens were active for a specified period.');


SELECT '-- 141 --';
  ALTER TABLE ContainerHistory ADD  FOREIGN KEY (`_container_key`) references Container(`_container_key`);

SELECT '-- 142 --';
  ALTER TABLE ContainerHistory 
	ADD FOREIGN KEY `cv_ContainerStatusContainerHistoryFK` (`_containerStatus_key`) 
	REFERENCES cv_ContainerStatus(`_containerStatus_key`);

SELECT '-- 143 --';
  ALTER TABLE ContainerHistory 
	ADD FOREIGN KEY `RoomContainerHistoryFK` (`_room_key`) 
	REFERENCES Room(`_room_key`);

SELECT '-- 144 --';
  ALTER TABLE HealthLevelHistory 
	ADD FOREIGN KEY `cv_HealthLevelHealthLevelHistoryFK` (`_healthLevel_key`) 
	REFERENCES cv_HealthLevel(`_healthLevel_key`);

SELECT '-- 145 --';


  ALTER TABLE HealthLevelHistory 
	ADD FOREIGN KEY `RoomHealthLevelHistoryFK` (`_room_key`) REFERENCES Room(`_room_key`);

SELECT '-- 146 --';
CALL DropConstraintWithoutName('Mouse','_pen_key','PenGroup','_pen_key');

COMMIT;


SELECT '-- 147 --';
  DROP TABLE IF EXISTS `MyPenGroup`;

COMMIT;

SELECT '-- 148 --';
    ALTER TABLE Mouse ADD CONSTRAINT Mouse_container_ibfk FOREIGN KEY (_pen_key) REFERENCES Container (_container_key) ON UPDATE RESTRICT ON DELETE RESTRICT ;    


COMMIT;

END;

//

Delimiter ;

CALL `j3_4_0__j4_0_0`();
