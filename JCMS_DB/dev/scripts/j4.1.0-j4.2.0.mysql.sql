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


-- MySQL upgrade for JCMS 4.1.0 to JCMS 4.2.0
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

DROP PROCEDURE IF EXISTS `j4_1_0__j4_2_0` //

CREATE  PROCEDURE `j4_1_0__j4_2_0`()
BEGIN

-- Declaration Section
  DECLARE myVar VARCHAR(20);
  DECLARE IndexCount INT;

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_1_0__j4_2_0';

SELECT '-- 1 ---';
-- Drop Unique, add regular index

-- SR 4785 Dbinfo table has primary key defined on the dbvers field

-- SR 4741 Make Dbinfo table consistent with GUDM

-- SR 4915 Change name of Dbinfo table to dbInfo


SELECT '-- 2 ---';
CREATE TABLE `JCMS_Dbinfo` (
  `dbVers` varchar(8) NOT NULL,
  `versDate` datetime NOT NULL,
  `maxPenID` int(11) NOT NULL DEFAULT '0',
  `releaseNum` varchar(16) DEFAULT NULL,
  `releaseDate` datetime DEFAULT NULL,
  `maxAutoLitterNum` int(11) DEFAULT '0',
  `maxAutoMouseID` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`dbVers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

SELECT '-- 3 ---';

INSERT INTO JCMS_Dbinfo SELECT *, 1 as `version` FROM Dbinfo;

SELECT '-- 4 ---';
DROP TABLE Dbinfo;

COMMIT;

SELECT '-- 5 ---';

CREATE TABLE `dbInfo` (
  `_dbinfo_key` int(11) NOT NULL,
  `dbVers` varchar(8) NOT NULL,
  `versDate` datetime NOT NULL,
  `maxPenID` int(11) NOT NULL DEFAULT '0',
  `releaseNum` varchar(16) NOT NULL,
  `releaseDate` datetime DEFAULT NULL,
  `maxAutoLitterNum` int(11) DEFAULT '0',
  `maxAutoMouseID` int(11) DEFAULT '0',
  `majorVersion` int(11) NOT NULL,
  `minorVersion` int(11) NOT NULL,
  `bugFixVersion` int(11) DEFAULT NULL,
  `buildVersion` int(11) DEFAULT NULL,
  `releaseType` varchar(32) DEFAULT NULL,
  `releaseNotes` varchar(1024) DEFAULT NULL,
  `databaseReleaseNum` varchar(255) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_dbinfo_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;


COMMIT;

SELECT '-- 6 ---';
INSERT INTO dbInfo ( `_dbinfo_key`, `dbVers`,  `versDate` ,   `maxPenID` , `releaseNum` ,  `releaseDate` ,  `maxAutoLitterNum` ,  `maxAutoMouseID` ,   `majorVersion` ,  `minorVersion` ,  `bugFixVersion` ,  `buildVersion` ,  `releaseType` ,   `releaseNotes` ,  `databaseReleaseNum`  )
Select 1 AS `_dbinfo_key`, JCMS_Dbinfo.dbVers, JCMS_Dbinfo.versDate, JCMS_Dbinfo.maxPenID, JCMS_Dbinfo.releaseNum, JCMS_Dbinfo.releaseDate, JCMS_Dbinfo.maxAutoLitterNum ,  JCMS_Dbinfo.maxAutoMouseID, 
4, 2, 0, 1 , 'init', 'release notes', JCMS_Dbinfo.releaseNum
FROM JCMS_Dbinfo; 

COMMIT;

SELECT '-- 7 ---';

DROP TABLE JCMS_Dbinfo;

SELECT '-- 8 ---';
-- SR 4736 Change the length of the owner field from 8 to 75 characters 
-- in both the Owner table and the Secretary table.
-- SR 4742 In the Document table, there is an owner field that is text (255). 
-- Change the length to 75 and make the field required.

ALTER TABLE `Owner` MODIFY COLUMN `owner` VARCHAR(75) 
CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

SELECT '-- 9 ---';
-- set secretary column from varchr(8) to varchar(75) in the secretary table
ALTER TABLE `Secretary` MODIFY COLUMN `secretary` VARCHAR(75) 
CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

SELECT '-- 10 ---';
-- set owner column from varchr(8) to varchar(75) in the document table
ALTER TABLE `Document` MODIFY COLUMN `Owner` VARCHAR(75) 
CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;


SELECT '-- 11 ---';
-- SR 4740 Change the field name ?where? in the cv_MouseOrigin table.
ALTER TABLE `cv_MouseOrigin` CHANGE COLUMN `where` 
`mouseOrigin` VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci 
NOT NULL COMMENT 'Origin';
SELECT '-- 11.5 --';
ALTER TABLE cv_MouseOrigin ADD CONSTRAINT mouseOriginUNIQUE UNIQUE (mouseOrigin);

SELECT '-- 12 ---';
-- SR 4746 cv_sampleclass just had a column of sampleClass of varchar(32) 
-- and not null. No primary key defined.
ALTER TABLE `cv_SampleClass` CHANGE COLUMN `_sampleClass_key` 
 `_sampleClass_key` INT(11) NOT NULL

 , ADD PRIMARY KEY (`_sampleClass_key`) ;


SELECT '-- 13 ---';
-- SR 4738 Add interger primary key, not null, auto increment to all cv tables
-- SR# 2828 Update JCMS schema so that every table has a primary key

ALTER TABLE `cv_CauseOfDeath` ADD COLUMN `_causeOfDeath_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_causeOfDeath_key`);


SELECT '-- 14 ---';
ALTER TABLE `cv_CoatColor` ADD COLUMN `_coatColor_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_coatColor_key`);

-- for some reason, it is not set to auto-increment, leave it as it is

-- Revised 9/8/10: all cv tables changed to auto increment

SELECT '-- 15 ---';
ALTER TABLE `cv_ContainerStatus` MODIFY COLUMN 
 `_containerStatus_key` INT(11) NOT NULL;

SELECT '-- 16 ---';
ALTER TABLE `cv_Diet` ADD COLUMN `_diet_key` INT(11) 
NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_diet_key`);

SELECT '-- 17 ---';
ALTER TABLE `cv_Epoch` MODIFY COLUMN `_epoch_key` 
 INT(11) NOT NULL;

-- SR 4871 Remove table cv_ExpSampleLocation (will be done later)
-- Therfore, there is no point in adding a key
-- ALTER TABLE `cv_ExpSampleLocation` ADD COLUMN `_expSampleLocation_key` 
-- INT(11) NOT NULL AUTO_INCREMENT FIRST,
-- ADD PRIMARY KEY (`_expSampleLocation_key`);


SELECT '-- 18 ---';
ALTER TABLE `cv_ExpStatus` ADD COLUMN `_expStatus_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_expStatus_key`);


SELECT '-- 19 ---';
ALTER TABLE `cv_FieldOfStudy` ADD COLUMN `_fieldOfStudy_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_fieldOfStudy_key`);


SELECT '-- 20 ---';
ALTER TABLE `cv_GeneClass` ADD COLUMN `_geneClass_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_geneClass_key`);


SELECT '-- 21 ---';
ALTER TABLE `cv_Generation` ADD COLUMN `_generation_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_generation_key`);


SELECT '-- 22 ---';
ALTER TABLE `cv_GenotypeSpecimenLocation` ADD COLUMN `_genotypeSpecimenLocation_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_genotypeSpecimenLocation_key`);


SELECT '-- 23 ---';
ALTER TABLE `cv_HarvestMethod` ADD COLUMN `_harvestMethod_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_harvestMethod_key`);

SELECT '-- 24 ---';
 ALTER TABLE `cv_HealthLevel` MODIFY COLUMN `_healthLevel_key` 
 INT(11) NOT NULL;


SELECT '-- 25 ---';
ALTER TABLE `cv_Keywords` ADD COLUMN `_keyword_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_keyword_key`);

SELECT '-- 26 ---';
ALTER TABLE `cv_LocationType` MODIFY COLUMN `_locationType_key` 
 INT(11) NOT NULL;


SELECT '-- 27 ---';
ALTER TABLE `cv_MatingCardNotes` ADD COLUMN `_matingCardNotes_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_matingCardNotes_key`);


SELECT '-- 28 ---';
ALTER TABLE `cv_MouseOrigin` ADD COLUMN `_mouseOrigin_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_mouseOrigin_key`);


SELECT '-- 29 ---';
ALTER TABLE `cv_MouseProtocol` ADD COLUMN `_mouseProtocol_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_mouseProtocol_key`);


SELECT '-- 30 ---';
ALTER TABLE `cv_MouseUse` ADD COLUMN `_mouseUse_key` INT(11) 
NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_mouseUse_key`);

SELECT '-- 30.1 ---';
CREATE UNIQUE INDEX `mouseUse_UNIQUE` ON `cv_MouseUse` (`mouseUse` ASC) ;


SELECT '-- 31 ---';
ALTER TABLE `cv_PreservationDetail` MODIFY COLUMN `_preservationDetail_key` 
 INT(11) NOT NULL;

SELECT '-- 32 ---';
ALTER TABLE `cv_PreservationMethod` MODIFY COLUMN `_preservationMethod_key` 
 INT(11) NOT NULL;

SELECT '-- 33 ---';
ALTER TABLE `cv_PreservationType` MODIFY COLUMN `_preservationType_key` 
 INT(11) NOT NULL;


-- ALTER TABLE `cv_room` ADD COLUMN `_room_key` INT(11) 
-- NOT NULL AUTO_INCREMENT FIRST,
-- DROP PRIMARY KEY,
-- ADD PRIMARY KEY (`_room_key`);

SELECT '-- 34 ---';
ALTER TABLE `cv_SampleDateType` MODIFY COLUMN `_sampleDateType_key` 
INT(11) NOT NULL;

SELECT '-- 35 ---';
ALTER TABLE `cv_SampleStatus` MODIFY COLUMN `_sampleStatus_key` 
 INT(11) NOT NULL;

SELECT '-- 36 ---';
ALTER TABLE `cv_SampleType` MODIFY COLUMN `_sampleType_key` 
 INT(11) NOT NULL;

SELECT '-- 37 ---';
ALTER TABLE `cv_StorageFacility` MODIFY COLUMN `_storageFacility_key` 
 INT(11) NOT NULL;


SELECT '-- 38 ---';
ALTER TABLE `cv_StrainType` ADD COLUMN `_strainType_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_strainType_key`);

SELECT '-- 38.1 ---';
CREATE UNIQUE INDEX `strainType_UNIQUE` ON `cv_StrainType` (`strainType` ASC) ;

SELECT '-- 39 ---';
ALTER TABLE `cv_TestStatus` ADD COLUMN `_testStatus_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_testStatus_key`);

SELECT '-- 40 ---';
ALTER TABLE `cv_TimeUnit` MODIFY COLUMN `_timeUnit_key` 
 INT(11) NOT NULL;

SELECT '-- 41 ---';
ALTER TABLE `cv_WeightUnit` MODIFY COLUMN `_weightUnit_key` 
 INT(11) NOT NULL;



-- SR 4737 Add a new column ?version? default it to 1, 
-- not null to all tables in jcms_db database. 

SELECT '-- 42 ---';
ALTER TABLE `Allele` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_allele_key`;


SELECT '-- 43 ---';
ALTER TABLE `ApprovedStrainRegistry` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_owner_key`;
-- Default INSERTs to true (-1) rather 1.

SELECT '-- 43.5 --';
ALTER TABLE ApprovedStrainRegistry MODIFY active TINYINT(1) DEFAULT -1;

SELECT '-- 44 ---';
ALTER TABLE `Container` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_containerHistory_key`;


SELECT '-- 45 ---';
ALTER TABLE `ContainerHistory` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_containerStatus_key`;


SELECT '-- 46 ---';
ALTER TABLE `cv_CauseOfDeath` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `description`;


SELECT '-- 47 ---';
ALTER TABLE `cv_CoatColor` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `description`;


SELECT '-- 48 ---';
ALTER TABLE `cv_ContainerStatus` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `billable`;


SELECT '-- 49 ---';
ALTER TABLE `cv_Diet` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `dietDescription`;


SELECT '-- 50 ---';
ALTER TABLE `cv_Epoch` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `epoch`;

-- SR 4871 Remove table cv_ExpSampleLocation (will be done later)
-- Therefore, there is no need to add a field
-- ALTER TABLE `cv_ExpSampleLocation` ADD COLUMN `version` 
-- INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `location`;


SELECT '-- 51 ---';
ALTER TABLE `cv_ExpStatus` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `status`;


SELECT '-- 52 ---';
ALTER TABLE `cv_FieldOfStudy` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `fieldOfStudyName`;


SELECT '-- 53 ---';
ALTER TABLE `cv_GeneClass` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `Description`;

SELECT '-- 53.1 ---';
ALTER TABLE `cv_GeneClass` ADD CONSTRAINT GeneClass_UNIQUE UNIQUE (GeneClass); 

SELECT '-- 54 ---';
ALTER TABLE `cv_Generation` ADD COLUMN `version` INT(11)
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `generation`;


SELECT '-- 55 ---';
ALTER TABLE `cv_GenotypeSpecimenLocation` ADD COLUMN `version` 
INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `genotypeSpecimenLocation`;


SELECT '-- 56 ---';
ALTER TABLE `cv_HarvestMethod` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `harvestMethod`;


SELECT '-- 57 ---';
ALTER TABLE `cv_HealthLevel` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `description`;


SELECT '-- 58 ---';
ALTER TABLE `cv_Keywords` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `keyword`;


SELECT '-- 59 ---';
ALTER TABLE `cv_LocationType` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `locationTypeRef`;


SELECT '-- 60 ---';
ALTER TABLE `cv_MatingCardNotes` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `matingNotes`;


SELECT '-- 61 ---';
ALTER TABLE `cv_MouseOrigin` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `mouseOrigin`;


SELECT '-- 62 ---';
ALTER TABLE `cv_MouseProtocol` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `description`;


SELECT '-- 63 ---';
ALTER TABLE `cv_MouseUse` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `useDescription`;


SELECT '-- 64 ---';
ALTER TABLE `cv_PreservationDetail` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_preservationMethod_key`;


SELECT '-- 65 ---';
ALTER TABLE `cv_PreservationMethod` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_preservationType_key`;


SELECT '-- 66 ---';
ALTER TABLE `cv_PreservationType` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_sampleClass_key`;


-- ALTER TABLE `cv_room` ADD COLUMN `version` INT(11) 
-- NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `room`;


SELECT '-- 67 ---';
ALTER TABLE `cv_SampleClass` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `sampleClass`;


SELECT '-- 68 ---';
ALTER TABLE `cv_SampleDateType` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `sampleDateType`;


SELECT '-- 69 ---';
ALTER TABLE `cv_SampleStatus` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `isInStorage`;


SELECT '-- 70 ---';
ALTER TABLE `cv_SampleType` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_sampleClass_key`;


SELECT '-- 71 ---';
ALTER TABLE `cv_StorageFacility` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `storageFacility`;


SELECT '-- 72 ---';
ALTER TABLE `cv_StrainType` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `strainType`;


SELECT '-- 73 ---';
ALTER TABLE `cv_TestStatus` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `testStatus`;


SELECT '-- 74 ---';
ALTER TABLE `cv_TimeUnit` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `minutesPerUnit`;


SELECT '-- 75 ---';
ALTER TABLE `cv_WeightUnit` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `weightUnit`;


SELECT '-- 76 ---';
ALTER TABLE `DbFormPrivileges` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `description`;


-- ALTER TABLE `Dbinfo` ADD COLUMN `version` INT(11) 
-- NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `maxAutoMouseID`;


SELECT '-- 77 ---';
ALTER TABLE `DbSetup` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `MTSVarDescription`;

SELECT '-- 77.1 ---';
CREATE UNIQUE INDEX `MTSVar_UNIQUE` ON `DbSetup` (`MTSVar` ASC) ;


SELECT '-- 78 ---';
ALTER TABLE `Document` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `Owner`;


SELECT '-- 79 ---';
ALTER TABLE `ExpData` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `d30`;


SELECT '-- 80 ---';
ALTER TABLE `ExpDataDefault` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `d30_default`;

SELECT '-- 81 ---';
ALTER TABLE `ExpDataDescriptor` ADD COLUMN `version` INT(11)
 NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `testTypeNotes`;


SELECT '-- 82 ---';
ALTER TABLE `ExpPlan` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `keyword5`;


SELECT '-- 83 ---';
ALTER TABLE `ExpPlanMouseMap` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_expPlan_key`;


SELECT '-- 84 ---';
ALTER TABLE `ExpTest` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `proposedPresDetail`;


SELECT '-- 85 ---';
ALTER TABLE `ExpTestPlanMap` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_exptestplan_key`;


SELECT '-- 86 ---';
ALTER TABLE `Gene` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `comment`;

SELECT '-- 86.1 ---';
ALTER TABLE `Gene` ADD CONSTRAINT labSymbol_UNIQUE UNIQUE (labSymbol);
ALTER TABLE `Gene` MODIFY COLUMN `labSymbol` varchar(16) NOT NULL;

SELECT '-- 87 ---';
ALTER TABLE `Genotype` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `comment`;


SELECT '-- 88 ---';
ALTER TABLE `GenotypeDocument` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_genotype_key`;


SELECT '-- 89 ---';
ALTER TABLE `HealthLevelHistory` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `startDate`;


SELECT '-- 90 ---';
ALTER TABLE `LifeStatus` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `exitStatus`;


SELECT '-- 91 ---';
ALTER TABLE `Litter` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `comment`;


SELECT '-- 92 ---';
ALTER TABLE `LitterSample` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_sample_key`;


SELECT '-- 93 ---';
ALTER TABLE `Location` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_locationType_key`;


SELECT '-- 94 ---';
ALTER TABLE `Mating` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `suggestedFirstLitterNum`;


SELECT '-- 95 ---';
ALTER TABLE `MatingSample` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_mating_key`;


SELECT '-- 96 ---';
ALTER TABLE `Mouse` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `sampleVialTagPosition`;


SELECT '-- 97 ---';
ALTER TABLE `MouseSample` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_sample_key`;


SELECT '-- 98 ---';
ALTER TABLE `MouseUsage` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `D10`;


SELECT '-- 99 ---';
ALTER TABLE `Owner` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `owner`;

-- table no longer used
-- ALTER TABLE `pengroup` ADD COLUMN `version` INT(11) 
-- NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `healthLevel`;


SELECT '-- 100 ---';
ALTER TABLE `PlugDate` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `comment`;


SELECT '-- 101 ---';
ALTER TABLE `Room` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_healthLevelHistory_key`;


SELECT '-- 102 ---';
ALTER TABLE `Sample` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `SourceType`;


SELECT '-- 103 ---';
ALTER TABLE `Secretary` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `secretary`;


SELECT '-- 104 ---';
ALTER TABLE `SNPLoader` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `sequence`;


SELECT '-- 105 ---';
ALTER TABLE `Storage` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `_preservationDetail_key`;


SELECT '-- 106 ---';
ALTER TABLE `Strain` ADD COLUMN `version` INT(11) 
NOT NULL DEFAULT 1 COMMENT 'Version' AFTER `lineViabilityRedMaxAgeFemales`;


-- SR 4739   Most of the tables in the database has a primary 
-- key defined but not set to auto increment. (may be we will 
-- do it in future) 


-- ALTER TABLE `document` MODIFY COLUMN `_document_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `expdata` MODIFY COLUMN `_expData_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `expdatadefault` MODIFY COLUMN `_expDataDefault_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `expdatadescriptor` MODIFY COLUMN `_expDataDescriptor_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;

-- ALTER TABLE `expplan` MODIFY COLUMN `_expPlan_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `expplanmousemap` MODIFY COLUMN `_expPlanMouseMap_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `exptest` MODIFY COLUMN `_expTest_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `gene` MODIFY COLUMN `_gene_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `genotype` MODIFY COLUMN `_genotype_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `genotypedocument` MODIFY COLUMN `_genotypeDocument_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `healthlevelhistory` MODIFY COLUMN `_healthLevelHistory_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `litter` MODIFY COLUMN `_litter_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `littersample` MODIFY COLUMN `_litterSample_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `location` MODIFY COLUMN `_location_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `mating` MODIFY COLUMN `_mating_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `matingsample` MODIFY COLUMN `_matingSample_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `mouse` MODIFY COLUMN `_mouse_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `mousesample` MODIFY COLUMN `_mouseSample_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `mouseusage` MODIFY COLUMN `_usage_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `owner` MODIFY COLUMN `_owner_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `pengroup` MODIFY COLUMN `_pen_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `plugdate` MODIFY COLUMN `_plugDate_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `room` MODIFY COLUMN `_room_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `sample` MODIFY COLUMN `_sample_key` INT(11) 
-- NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `secretary` MODIFY COLUMN `_secretary_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `snploader` MODIFY COLUMN `_snp_loader_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `storage` MODIFY COLUMN `_storage_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- ALTER TABLE `strain` MODIFY COLUMN `_strain_key` 
-- INT(11) NOT NULL AUTO_INCREMENT;


-- SR 3399 Exp Data table foreign key _specimen_key should be required
SELECT '-- 107 ---';
ALTER TABLE `ExpData` MODIFY COLUMN `_specimen_key` INT(11) 
NOT NULL;


SELECT '-- 108 ---';
-- SR 4744 Removal of unused fields experimental data
ALTER TABLE `ExpData` DROP COLUMN `_sampleStorHISTO_key`,
 DROP COLUMN `_sampleStorLIVE_key`,
 DROP COLUMN `_sampleStorMOLBIO_key`;


SELECT '-- 109 ---';
ALTER TABLE `ExpTest` DROP COLUMN `proposedSampleClass`,
 DROP COLUMN `proposedSampleType`,
 DROP COLUMN `proposedHarvestMethod`,
 DROP COLUMN `proposedHarvestDate`,
 DROP COLUMN `proposedExpSampleLoc`,
 DROP COLUMN `proposedCrossRef`,
 DROP COLUMN `proposedPresType`,
 DROP COLUMN `proposedPresMethod`,
 DROP COLUMN `proposedPresDetail`;


SELECT '-- 110 ---';
-- SR 4750 No integer primary key defined. Create an autonumber 
-- primary key for: DbFormPrivileges, DbSetup, LifeStatus
ALTER TABLE `DbFormPrivileges` ADD COLUMN `_dbFormPrivileges_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 ADD PRIMARY KEY (`_dbFormPrivileges_key`);


SELECT '-- 111 ---';
ALTER TABLE `DbSetup` ADD COLUMN `_dbSetup_key` INT(11) 
NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_dbSetup_key`);


SELECT '-- 112 ---';
ALTER TABLE `LifeStatus` ADD COLUMN `_lifeStatus_key` 
INT(11) NOT NULL AUTO_INCREMENT FIRST,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY (`_lifeStatus_key`);


SELECT '-- 113 ---';
-- SR 4807 Some lists that should be CV tables are hard-coded in the application
-- Add cv_sex, cv_breedingStatus, and cv_birthEventStatus
-- Use the current hard-coded values as seeded values for the blank back end

-- cv_Sex
CREATE  TABLE `cv_Sex` (
  `_sex_key` INT NOT NULL AUTO_INCREMENT ,
  `sex` VARCHAR(75) NOT NULL COMMENT 'Sex' ,
  `abbreviation` VARCHAR(2) NULL COMMENT 'Abbreviation' ,
  `isActive` TINYINT NULL COMMENT 'Is active' ,
  `isDefault` TINYINT NULL COMMENT 'Is default' ,
  `sortOrder` INT NULL COMMENT 'Sort order' ,
  `_vocabularySource_key` INT(11) NULL ,
  `elementID` VARCHAR(18) NULL COMMENT 'Element ID' ,
  `isDeprecated` TINYINT NULL COMMENT 'Is deprecated' ,
  PRIMARY KEY (`_sex_key`) ,
  UNIQUE INDEX `idcv_sex_UNIQUE` (`_sex_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 114 ---';
ALTER TABLE `cv_Sex` ADD COLUMN `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version'  AFTER `isDeprecated` ;

SELECT '-- 115 ---';
INSERT INTO `cv_Sex` (`sex`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Male',  'M', -1, 0); 
INSERT INTO `cv_Sex` (`sex`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Female',  'F', -1, 0);
INSERT INTO `cv_Sex` (`sex`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Unknown',  '-' , -1, -1);

-- cv_BreedingStatus

SELECT '-- 116 ---';
CREATE  TABLE `cv_BreedingStatus` (
  `_breedingStatus_key` INT NOT NULL AUTO_INCREMENT ,
  `breedingStatus` VARCHAR(75) NOT NULL COMMENT 'Breeding Status' ,
  `abbreviation` VARCHAR(2) NULL COMMENT 'Abbreviation' ,
  `isActive` TINYINT NULL COMMENT 'Is active' ,
  `isDefault` TINYINT NULL COMMENT 'Is default' ,
  `sortOrder` INT NULL COMMENT 'Sort order' ,
  `_vocabularySource_key` INT(11) NULL ,
  `elementID` VARCHAR(18) NULL COMMENT 'Element ID' ,
  `isDeprecated` TINYINT NULL COMMENT 'Is deprecated' ,
  PRIMARY KEY (`_breedingStatus_key`) ,
  UNIQUE INDEX `idcv_breedingStatus_UNIQUE` (`_breedingStatus_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 117 ---';
ALTER TABLE `cv_BreedingStatus` ADD COLUMN `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version'  AFTER `isDeprecated` ;

SELECT '-- 118 ---';
INSERT INTO `cv_BreedingStatus` (`breedingStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Breeding',  'B' , -1, 0);
INSERT INTO `cv_BreedingStatus` (`breedingStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Retired breeder',  'R' , -1, 0);
INSERT INTO `cv_BreedingStatus` (`breedingStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Virgin',  'V' , -1, -1);
INSERT INTO `cv_BreedingStatus` (`breedingStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Unknown',  'U' , -1, 0);

SELECT '-- 119 ---';
-- cv_BirthEventStatus ("litter status")

CREATE  TABLE `cv_BirthEventStatus` (
  `_birthEventStatus_key` INT NOT NULL AUTO_INCREMENT ,
  `birthEventStatus` VARCHAR(75) NOT NULL COMMENT 'Litter status' ,
  `abbreviation` VARCHAR(2) NULL COMMENT 'Abbreviation' ,
  `isActive` TINYINT NULL COMMENT 'Is active' ,
  `isDefault` TINYINT NULL COMMENT 'Is default' ,
  `sortOrder` INT NULL COMMENT 'Sort order' ,
  `_vocabularySource_key` INT(11) NULL ,
  `elementID` VARCHAR(18) NULL COMMENT 'Element ID' ,
  `isDeprecated` TINYINT NULL COMMENT 'Is deprecated' ,
  PRIMARY KEY (`_birthEventStatus_key`) ,
  UNIQUE INDEX `idcv_birthEventStatus_UNIQUE` (`_birthEventStatus_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 120 ---';
ALTER TABLE `cv_BirthEventStatus` ADD COLUMN `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version'  AFTER `isDeprecated` ;

SELECT '-- 121 ---';
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Alive',  'A' , -1, -1);
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Born dead',  'B' , -1, 0);
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Dead at weaning',  'D' , -1, 0);
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Harvested',  'H' , -1, 0);
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Killed',  'K' , -1, 0);
INSERT INTO `cv_BirthEventStatus` (`birthEventStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Missing',  'M' , -1, 0);

-- SR 4815 Add cv table for strain status
-- It will be called cv_lineStatus to agree with the GUDM and contain
-- all the standard fields used for the other cv tables
-- Use the current hard-coded values as seeded values for the blank back end

SELECT '-- 122 ---';
-- cv_LineStatus
CREATE  TABLE `cv_LineStatus` (
  `_lineStatus_key` INT NOT NULL AUTO_INCREMENT ,
  `lineStatus` VARCHAR(75) NOT NULL COMMENT 'Strain status' ,
  `abbreviation` VARCHAR(2) NULL COMMENT 'Abbreviation' ,
  `isActive` TINYINT NULL COMMENT 'Is active' ,
  `isDefault` TINYINT NULL COMMENT 'Is default' ,
  `sortOrder` INT NULL COMMENT 'Sort order' ,
  `_vocabularySource_key` INT(11) NULL ,
  `elementID` VARCHAR(18) NULL COMMENT 'Element ID' ,
  `isDeprecated` TINYINT NULL COMMENT 'Is deprecated' ,
  PRIMARY KEY (`_lineStatus_key`) ,
  UNIQUE INDEX `idcv_lineStatus_UNIQUE` (`_lineStatus_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;

SELECT '-- 123 ---';
ALTER TABLE `cv_LineStatus` ADD COLUMN `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version'  AFTER `isDeprecated` ;

SELECT '-- 124 ---';
INSERT INTO `cv_LineStatus` (`lineStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Active',  'A', -1, -1);
INSERT INTO `cv_LineStatus` (`lineStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('See comments',  'C', -1, 0);
INSERT INTO `cv_LineStatus` (`lineStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Discarded',  'D', -1, 0);
INSERT INTO `cv_LineStatus` (`lineStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Frozen and not active',  'F', -1, 0);

-- SR 4825 The column name "section" in strain table 
-- is a SQL-99 reserved keyword. Needs renaming column name.
-- The field name is changed to section_

SELECT '-- 125 ---';
ALTER TABLE `Strain` CHANGE COLUMN `section` `section_` VARCHAR(32) NULL DEFAULT NULL COMMENT 'Section'  ;

-- SR 4745 Change caption for jrNum in strain table to Stock #

SELECT '-- 126 ---';
ALTER TABLE `Strain` CHANGE COLUMN `jrNum` `jrNum` INT(11) NULL DEFAULT '0' COMMENT 'Stock #'  ;

-- SR 4753 Remove cv_room table
-- cv_room was replaced by the Room table in release 4.0. It should now be removed. 

SELECT '-- 127 ---';
drop table `cv_Room` ;

-- SR 3469 Use and Origin field lengths differ in the tables

-- MouseUsage table
-- Change the field length of the "use" column to 32 characters

SELECT '-- 128 ---';
ALTER TABLE `MouseUsage` CHANGE COLUMN `use` `use` VARCHAR(32) NOT NULL  ;

-- SR 3152 Small schema changes

-- Mouse table origin column
-- Remove the default of "SJ"

-- Mouse table
-- Change the field length of origin to 32 characters

SELECT '-- 129 ---';
ALTER TABLE `Mouse` CHANGE COLUMN `origin` `origin` VARCHAR(32) NOT NULL COMMENT 'Origin' ;

-- Mating table
-- Change proposedRetireD1LfStatus, proposedRetireD2LfStatus, and proposedRetireSLfStatus
-- to be two characters long

SELECT '-- 130 ---';
ALTER TABLE `Mating` 
CHANGE COLUMN `proposedRetireD2LfStatus` `proposedRetireD2LfStatus` VARCHAR(2) NULL DEFAULT NULL  
, CHANGE COLUMN `proposedRetireSLfStatus` `proposedRetireSLfStatus` VARCHAR(2) NULL DEFAULT NULL  
, CHANGE COLUMN `proposedRetireD1LfStatus` `proposedRetireD1LfStatus` VARCHAR(2) NULL DEFAULT NULL  ;


-- LifeStatus table
-- Change the lifeStatus field to be required (not null)

SELECT '-- 131 ---';
ALTER TABLE `LifeStatus` CHANGE COLUMN `lifeStatus` `lifeStatus` VARCHAR(2) NOT NULL COMMENT 'Life status'  ;

-- Sample table
-- Change the sampleID field to be required (not null)

SELECT '-- 132 ---';
ALTER TABLE `Sample` CHANGE COLUMN `SampleID` `SampleID` VARCHAR(32) NOT NULL  ;

-- Genotype table
-- Change the caption for the sampleLocation field to be "Genotype specimen location"

SELECT '-- 133 ---';
ALTER TABLE `Genotype` CHANGE COLUMN `sampleLocation` `sampleLocation` VARCHAR(16) NULL DEFAULT NULL COMMENT 'Genotype specimen location';

-- SR 2051 Image storage
-- SR 4241 Improve ability to access genotype image files
-- SR 4600 Allow one genotype image be associated with many mice

-- Remove existing foreign key relationships to the primary key (_document_key) so it can be renamed.


-- ALTER TABLE `GenotypeDocument` DROP FOREIGN KEY `genotypedocument_ibfk_1` ;
SELECT '-- 134 ---';
call DropConstraintWithoutName('GenotypeDocument','_document_key','Document','_document_key');

-- Change the name of the document table

SELECT '-- 135 ---';
ALTER TABLE `Document` RENAME TO  `DocumentVersion` ;

-- Change the primary key name and add some fields

SELECT '-- 136 ---';
ALTER TABLE `DocumentVersion` CHANGE COLUMN `_document_key` `_documentVersion_key` INT(11) NOT NULL  
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`_documentVersion_key`) 
, ADD UNIQUE INDEX `_documentVersion_key` (`_documentVersion_key` ASC) ;

-- xxx  , DROP INDEX `_document_key` 


SELECT '-- 137 ---';
ALTER TABLE `DocumentVersion` ADD COLUMN `isActive` TINYINT NULL DEFAULT -1 COMMENT 'Is active'  AFTER `version` 
, ADD COLUMN `majorVersion` INT(11) NULL DEFAULT NULL COMMENT 'Major version'  AFTER `version` 
, ADD COLUMN `minorVersion` INT(11) NULL DEFAULT NULL COMMENT 'Minor version'  AFTER `majorVersion` 
, ADD COLUMN `revision` INT(11) NULL DEFAULT NULL COMMENT 'Revision'  AFTER `minorVersion`;

-- Add URL field

SELECT '-- 138 ---';
ALTER TABLE `DocumentVersion` ADD COLUMN `URL` VARCHAR(50) NULL  AFTER `isActive` ;

-- Add new foreign key called _document_key

SELECT '-- 139 ---';
ALTER TABLE `DocumentVersion` ADD COLUMN `_document_key` INT(11) NOT NULL  AFTER `URL`;


SELECT '-- 140 ---';
Update DocumentVersion  SET `_document_key` = DocumentVersion.`_documentVersion_key`;
  
SELECT '-- 141 ---';
ALTER TABLE `DocumentVersion` ADD UNIQUE INDEX `_document_key_UNIQUE` (`_document_key` ASC) ;

-- make the primary key auto increment

SELECT '-- 142 ---';
ALTER TABLE `DocumentVersion` CHANGE COLUMN `_documentVersion_key` `_documentVersion_key` INT(11) NOT NULL;

-- Create a new table called Document

SELECT '-- 143 ---';
CREATE  TABLE `Document` (
  `_document_key` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL DEFAULT 'None' COMMENT 'Title' ,
  `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' ,
  PRIMARY KEY (`_document_key`) ,
  UNIQUE INDEX `_document_key_UNIQUE` (`_document_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;
  
  -- Change the name of GenotypeDocument to DocumentMapping
  
SELECT '-- 144 ---';
ALTER TABLE `GenotypeDocument` RENAME TO  `DocumentMapping` ;
 
 -- Change field names
 -- _genotypeDocument_key to _documentMapping_key
 -- _genotype_key to _table_key
 -- Add field _tableName_key 
 
SELECT '-- 145 ---';
 ALTER TABLE `DocumentMapping` CHANGE COLUMN `_genotypeDocument_key` `_documentMapping_key` INT(11) NOT NULL,
    ADD UNIQUE INDEX `_documentMapping_key_UNIQUE` (`_documentMapping_key` ASC),
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`_documentMapping_key`);


-- Drop the index _GenotypeDocument_key if it exists.
-- First test if it exists

/*
The following section was removed and implemented by code in processProgress Class
as to allow more schema flexibility.

SELECT '-- 145.1 ---';
SELECT COUNT(1) INTO IndexCount
     FROM information_schema.statistics
     WHERE table_schema = 'jcms_db'
     AND table_name = 'GenotypeDocument'
     AND index_name = '_GenotypeDocument_key';
-- Now delete it if it did.

SELECT '-- 145.2 ---';
IF IndexCount > 0 THEN
    ALTER TABLE DocumentMapping DROP INDEX `_GenotypeDocument_key`  ;
END IF;
*/

SELECT '-- 146 ---';
ALTER TABLE `DocumentMapping` CHANGE COLUMN `_genotype_key` `_table_key` INT(11) NOT NULL  
, DROP INDEX `_genotype_key` ;

SELECT '-- 147 ---';
ALTER TABLE `DocumentMapping` ADD COLUMN `_tableName_key` INT(11) NOT NULL  AFTER `version` ;

-- Change name of _document_key to be _documentVersion_key and also change it to not allow null 

SELECT '-- 148 ---';
ALTER TABLE `DocumentMapping` DROP INDEX `_document_key` ;
SELECT '-- 149 ---';
COMMIT;

SELECT '-- 150 ---';
ALTER TABLE `DocumentMapping` CHANGE COLUMN `_document_key` `_documentVersion_key` INT(11) NOT NULL  
, ADD INDEX `_documentVersion_key` (`_documentVersion_key` ASC) ;

-- Make the DocumentMapping primary key auto increment

SELECT '-- 151 ---';
ALTER TABLE `DocumentMapping` CHANGE COLUMN `_documentMapping_key` `_documentMapping_key` INT(11) NOT NULL;

-- Add table cv_TableName

SELECT '-- 152 ---';
CREATE  TABLE `cv_TableName` (
  `_tableName_key` INT(11) NOT NULL AUTO_INCREMENT ,
  `tableName` VARCHAR(255) NOT NULL COMMENT 'Table name' ,
  `abbreviation` VARCHAR(10) NOT NULL COMMENT 'Abbreviation' ,
  `isActive` TINYINT NULL DEFAULT -1 COMMENT 'Is active' ,
  `isDefault` TINYINT NULL DEFAULT 0 COMMENT 'Is default' ,
  `version` INT(11) NOT NULL DEFAULT 1 COMMENT 'Version' ,
  PRIMARY KEY (`_tableName_key`) ,
  UNIQUE INDEX `idcv_tableName_UNIQUE` (`_tableName_key` ASC) )
  ENGINE =INNODB CHARACTER SET utf8;
  
  -- Add seeded values to cv_TableName
  
SELECT '-- 153 ---';
INSERT INTO `cv_TableName` (`_tableName_key`,`tableName`, `abbreviation`, `isActive`, `isDefault`)   VALUES (1,'Genotype', 'Genotype', '-1', '-1');
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('ExpPlan', 'Exp Plan', '-1', '0');
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Litter', 'Litter', '-1', '0');
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Mating', 'Mating', '-1', '0');
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Mouse', 'Mouse', '-1', '0');
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('Sample', 'Sample', '-1', '0');



-- Edit any existing data in the DocumentMapping table to contain the _tableName_key for Genotype (1)

SELECT '-- 154 ---';
UPDATE `DocumentMapping` SET `_tableName_key` = 1;


-- Setup the foreign key relationships 

SELECT '-- 155 ---';
ALTER TABLE `DocumentMapping` 
  ADD CONSTRAINT `documentmapping_tablename_ibfk`
  FOREIGN KEY (`_tableName_key` )
  REFERENCES `cv_TableName` (`_tableName_key` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `DocumentMapping_ibfk_1` (`_tableName_key` ASC) ;


SELECT '-- 156.1 ---';
ALTER TABLE `DocumentMapping` 
  ADD CONSTRAINT `documentmapping_documentversion_ibfk`
  FOREIGN KEY (`_documentVersion_key` )
  REFERENCES `DocumentVersion` (`_documentVersion_key` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `DocumentMapping_ibfk_2` (`_documentVersion_key` ASC) ;

SELECT '-- 156.2 ---';
INSERT INTO `Document` (`_document_key`) SELECT  (`_document_key`) FROM `DocumentVersion`;

SELECT '-- 157 ---';
ALTER TABLE `DocumentVersion` 
  ADD CONSTRAINT `documentversion_document_ibfk`
  FOREIGN KEY (`_document_key` )
  REFERENCES `Document` (`_document_key` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `DocumentVersion_ibfk_1` (`_document_key` ASC) ;


-- Create a Document record for each existing DocumentVersion record with “None” as the title
-- By copying the version from the existing records, one record will be created in the Document table
-- for each record in the DocumentVersion table.

SELECT '-- 158 ---';
-- INSERT INTO `Document` (`version`) SELECT  (version  ) FROM `DocumentVersion`;

SELECT '-- 159 ---';
UPDATE `Document` SET `title` = "None" ;

-- UPGRADER PROBLEM
-- The foreign key called _document_key in the DocumentVersion  table needs to be initialized to point to
-- a unique record in the new Document table.
-- It does not matter what record since at the moment they are all the same.

-- the script below will work if the user never deleted any documents prior to conversion.
-- but what if there are gaps in the key sequence?
-- Is there some other way to sequentially number the _document_key value?




SELECT '-- 160 ---';
-- 3.1.2	SR 4201 Need to allow multiple data records to be associated with one test
-- Add a boolean field called multipleMeasurements to the ExpTest table
ALTER TABLE `ExpTest` ADD COLUMN `multipleMeasurements` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Multiple measurements'  AFTER `version` ;

-- Remove setup variable: JCMS_IMPORT_EXP_DATA_ALLOW_MULTIPLE 
-- It is currently used only when importing data that is not associated with an experimental plan. 
-- By putting this choice on the Import Experimental Data form, the user will be able to specify 
-- if multiple measurements are allowed or not for a particular import.
-- DELETE FROM `DbSetup` WHERE `MTSVar`='JCMS_IMPORT_EXP_DATA_ALLOW_MULTIPLE';

SELECT '-- 161 ---';
INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_DATABASE_DBMS', 'MySQL', 'Name of the database management system. Valid values are MSAccess or MySQL. Required for MySQL to function properly.');

SELECT '-- 162 ---';
-- SR 4871 Remove table cv_ExpSampleLocation
-- This table is no longer used
drop table `cv_ExpSampleLocation`;


-- Add the new forms to DbFormPrivileges
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Add Document', 'Sec', 'AddDocument', 'Documents: Upload a document (file) to a JCMS folder and give it a title.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Associate Documents', 'Sec', 'AssociateDocuments', 'Documents: Keep track of the associations between user  documents and mice, genotypes, experiments, etc.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Edit Document Associations', 'Sec', 'EditDocumentAssociations', 'Documents: Make changes to the associations between documents and IDs.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Bulk Add Document Associations', 'Sec', 'BulkAddDocumentAssociations', 'Documents: Add more associations between a document and IDs.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Edit Document', 'Sec', 'EditDocument', 'Documents: Make changes to the information about a document.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Bulk Edit Experimental Data', 'Owner', 'BulkEditExpData', 'Experiment: Make the same change to multiple experimental data records.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Experimental Data Repeat Measure', 'Owner', 'ExpDataRepeatMeasurement', 'Experiment: Add experimental data for a repeated test.');
INSERT INTO `DbFormPrivileges` (`formName`, `privilegeLevel`, `completeFormName`, `description`) VALUES ('Experiment Work Report', 'Sec', 'RequestExperimentReport', 'Reports: Experiments - Show all the mice due for processing during a defined time span.');

-- Das end Ja!

COMMIT;


END;

//

Delimiter ;

Call `j4_1_0__j4_2_0`();