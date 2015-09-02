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

SELECT '--dbChange_105.sql--';
SELECT '--1--';
Delimiter //

-- Drop Constraint Without Name -----------------------------------------
DROP PROCEDURE IF EXISTS `DropConstraintWithoutName` //

CREATE PROCEDURE `DropConstraintWithoutName`(IN myTable VARCHAR(32), IN myCol VARCHAR(32), IN refTable VARCHAR(32) , IN refCol VARCHAR(32) )
BEGIN

DECLARE myConstraintName VARCHAR(32)  ;
DECLARE myDatabase VARCHAR(32)  ;
DECLARE flag INT DEFAULT 0;

SELECT DATABASE() INTO myDatabase;

SELECT '--2--';
SELECT CONSTRAINT_NAME INTO myConstraintName
FROM information_schema.KEY_COLUMN_USAGE
WHERE CONSTRAINT_SCHEMA =  myDatabase AND
            TABLE_NAME = myTable AND
            COLUMN_NAME = myCol AND
            REFERENCED_TABLE_NAME = refTable AND
            REFERENCED_COLUMN_NAME = refCol AND
            CONSTRAINT_NAME LIKE '%ibfk%'
LIMIT 1;

SELECT '--3--';
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

DELIMITER ;

SELECT '--4--';
INSERT INTO `cv_ControlledVocabularyGroup` (`name`, `sortOrder`) VALUES ('Samples', '7');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('26', '7', 'cv_SampleType', '_sampleType_key', 'sampleType', '_sampleClass_key', 'Sample Types', 'Sample Type', 'Sample Class', 'SAMPLES', '1', '2', 'dba', now(), 'dba', now(), '1');


INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('27', '7', 'cv_SampleClass', '_sampleClass_key', 'sampleClass', '', 'Sample Class', 'Sample Class', '', 'GENERAL', '1', '1', 'dba', now(), 'dba', now(), '1'
);

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('28', '7', 'cv_PreservationType', '', '', '', 'Preservation', '', '', 'PRESERVATION', '1', '3', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('29', '7', 'cv_SampleDateType', '_sampleDateType_key', 'sampleDateType', '', 'Sample Date Type', 'Sample Date Type', '', 'GENERAL', '1', '6', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('30', '7', 'cv_HarvestMethod', '_harvestMethod_key', 'harvestMethod', '', 'Harvest Method', 'Harvest Method', '', 'GENERAL', '1', '7', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('31', '7', 'cv_WeightUnit', '_weightUnit_key', 'weightUnit', '', 'Weight Unit', 'Weight Unit', '', 'GENERAL', '1', '8', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('32', '7', 'cv_TimeUnit', '', '', '', 'Time Unit', '', '', 'TIMEUNIT', '1', '9', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('33', '7', 'cv_Epoch', '_epoch_key', 'epoch', '', 'Epoch', 'Epoch', '', 'GENERAL', '1', '10', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('34', '7', 'cv_SampleStatus', '', '', '', 'Sample Status', '', '', 'SAMPLESTATUS', '1', '11', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('35', '7', 'Location', '', '', '', 'Locations/Storage Facilities', '', '', 'LOCATIONS', '1', '12', 'dba', now(), 'dba', now(), '1');

INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`)
VALUES ('38', '1', 'User', '', '', '', 'Add User', '', '', 'ADDUSERWIZARD', '1', '2', 'dba', now(), 'dba', now(), '1');

SELECT '--5--';
CALL DropConstraintWithoutName('cv_PreservationType','_sampleClass_key', 'cv_SampleClass', '_sampleClass_key');
CALL DropConstraintWithoutName('cv_SampleType','_sampleClass_key', 'cv_SampleClass', '_sampleClass_key');
CALL DropConstraintWithoutName('cv_SampleType','_sampleClass_key', 'cv_SampleClass', '_sampleClass_key');
ALTER TABLE `cv_SampleClass` CHANGE COLUMN `_sampleClass_key` `_sampleClass_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `cv_SampleType` ADD CONSTRAINT `sampletype_sampleclass_ibfk` FOREIGN KEY `sampletype_sampleclass_ibfk` (`_sampleClass_key`)
    REFERENCES `cv_SampleClass` (`_sampleClass_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `cv_PreservationType` ADD CONSTRAINT `preservationtype_sampleclass_ibfk` FOREIGN KEY `preservationtype_sampleclass_ibfk` (`_sampleClass_key`)
    REFERENCES `cv_SampleClass` (`_sampleClass_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `cv_SampleType` CHANGE COLUMN `_sampleType_key` `_sampleType_key` INT(11) NOT NULL AUTO_INCREMENT;

SELECT '--6--';
CALL DropConstraintWithoutName('Storage','_sampleStatus_key', 'cv_SampleStatus', '_sampleStatus_key');
ALTER TABLE `cv_SampleStatus` CHANGE COLUMN `_sampleStatus_key` `_sampleStatus_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `Storage` ADD CONSTRAINT `storage_samplestatus_ibfk` FOREIGN KEY `storage_samplestatus_ibfk` (`_sampleStatus_key`)
    REFERENCES `cv_SampleStatus` (`_sampleStatus_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--7--';
CALL DropConstraintWithoutName('Sample','_sampleDateType_key', 'cv_SampleDateType', '_sampleDateType_key');
ALTER TABLE `cv_SampleDateType` CHANGE COLUMN `_sampleDateType_key` `_sampleDateType_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `Sample` ADD CONSTRAINT `sample_sampledatetype_ibfk` FOREIGN KEY `sample_sampledatetype_ibfk` (`_sampleDateType_key`)
    REFERENCES `cv_SampleDateType` (`_sampleDateType_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--8--';
CALL DropConstraintWithoutName('cv_PreservationMethod','_preservationType_key', 'cv_PreservationType', '_preservationType_key');
ALTER TABLE `cv_PreservationType` CHANGE COLUMN `_preservationType_key` `_preservationType_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `cv_PreservationMethod` ADD CONSTRAINT `preservationmethod_preservationtype_ibfk` FOREIGN KEY `preservationmethod_preservationtype_ibfk` (`_preservationType_key`)
    REFERENCES `cv_PreservationType` (`_preservationType_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--9--';
CALL DropConstraintWithoutName('cv_PreservationDetail','_preservationMethod_key', 'cv_PreservationMethod', '_preservationMethod_key');
ALTER TABLE `cv_PreservationMethod` CHANGE COLUMN `_preservationMethod_key` `_preservationMethod_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `cv_PreservationDetail` ADD CONSTRAINT `preservationdetail_preservationmethod_ibfk` FOREIGN KEY `preservationdetail_preservationmethod_ibfk` (`_preservationMethod_key`)
    REFERENCES `cv_PreservationMethod` (`_preservationMethod_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `cv_PreservationDetail` CHANGE COLUMN `_preservationDetail_key` `_preservationDetail_key` INT(11) NOT NULL AUTO_INCREMENT  ;

SELECT '--10--';
CALL DropConstraintWithoutName('Sample','_timeUnit_key', 'cv_TimeUnit', '_timeUnit_key');
ALTER TABLE `cv_TimeUnit` CHANGE COLUMN `_timeUnit_key` `_timeUnit_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `Sample` ADD CONSTRAINT `sample_timeunit_ibfk` FOREIGN KEY `sample_timeunit_ibfk` (`_timeUnit_key`)
    REFERENCES `cv_TimeUnit` (`_timeUnit_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--11--';
CALL DropConstraintWithoutName('Sample','_weightUnit_key', 'cv_WeightUnit', '_weightUnit_key');
ALTER TABLE `cv_WeightUnit` CHANGE COLUMN `_weightUnit_key` `_weightUnit_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `Sample` ADD CONSTRAINT `sample_weightunit_ibfk` FOREIGN KEY `sample_weightunit_ibfk` (`_weightUnit_key`)
    REFERENCES `cv_WeightUnit` (`_weightUnit_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--11--';
CALL DropConstraintWithoutName('Sample','_epoch_key', 'cv_Epoch', '_epoch_key');
ALTER TABLE `cv_Epoch` CHANGE COLUMN `_epoch_key` `_epoch_key` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `Sample` ADD CONSTRAINT `sample_epoch_ibfk` FOREIGN KEY `sample_epoch_ibfk` (`_epoch_key`)
    REFERENCES `cv_Epoch` (`_epoch_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

SELECT '--12--';
ALTER TABLE `cv_HarvestMethod` CHANGE COLUMN `_harvestMethod_key` `_harvestMethod_key` INT(11) NOT NULL AUTO_INCREMENT  ;

UPDATE `ControlledVocabulary` SET `displayName`='Accounts' WHERE `_controlledVocabulary_key`='1';

INSERT INTO `FunctionalArea` VALUES('4', 'ColonyManagement', NULL, 'dba', NOW(), 'dba', NOW(), '1');

UPDATE dbInfo SET dbVers = 105 WHERE _dbinfo_key = 1;