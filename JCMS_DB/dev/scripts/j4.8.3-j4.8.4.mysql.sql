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
Delimiter //

-- Drop Constraint Without Name -----------------------------------------
DROP PROCEDURE IF EXISTS `DropConstraintWithoutName` //

CREATE PROCEDURE `DropConstraintWithoutName`(IN myTable VARCHAR(32), IN myCol VARCHAR(32), IN refTable VARCHAR(32) , IN refCol VARCHAR(32) )
BEGIN

DECLARE myConstraintName VARCHAR(32)  ;
DECLARE myDatabase VARCHAR(32)  ;
DECLARE flag INT DEFAULT 0;

SELECT DATABASE() INTO myDatabase;

SELECT CONSTRAINT_NAME INTO myConstraintName
FROM information_schema.KEY_COLUMN_USAGE
WHERE CONSTRAINT_SCHEMA =  myDatabase AND
            TABLE_NAME = myTable AND
            COLUMN_NAME = myCol AND
            REFERENCED_TABLE_NAME = refTable AND
            REFERENCED_COLUMN_NAME = refCol AND
            CONSTRAINT_NAME LIKE '%ibfk%';
IF NULL <=> myConstraintName THEN
    Select 'Did not find the script is attempting to delete';
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

INSERT INTO `UpgradeHistoryLog` (`majorVersion`, `minorVersion`, `bugFixVersion`, `upgradeDate`, `actionDescription`, `actionCompleted`, `comment`) VALUES (4, 8, 4, CURDATE(), 'None', -1, CURDATE());   


DROP TABLE IF EXISTS ControlledVocabulary;
DROP TABLE IF EXISTS cv_ControlledVocabularyGroup;

CREATE TABLE IF NOT EXISTS cv_ControlledVocabularyGroup (
  _controlledVocabularyGroup_key INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  sortOrder INT(11) NOT NULL,
  PRIMARY KEY (_controlledVocabularyGroup_key),
  UNIQUE INDEX Index_2(name)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS ControlledVocabulary (
  _controlledVocabulary_key int(11) NOT NULL AUTO_INCREMENT,
  _controlledVocabularyGroup_key int(11) NOT NULL,
  tableName varchar(50) DEFAULT NULL,
  pkColumnName varchar(100) DEFAULT NULL,
  columnOneName varchar(100) DEFAULT NULL,
  columnTwoName varchar(100) DEFAULT NULL,
  displayName varchar(100) DEFAULT NULL,
  displayColumnOneName varchar(100) DEFAULT NULL,
  displayColumnTwoName varchar(100) DEFAULT NULL,
  subViewName varchar(100) DEFAULT NULL,
  isUserAdministered tinyint(4) NOT NULL DEFAULT '0',
  sortOrder INT(11) NOT NULL,
  createdBy varchar(18) NOT NULL,
  dateCreated datetime NOT NULL,
  modifiedBy varchar(18) NOT NULL,
  dateModified datetime NOT NULL,
  version int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (_controlledVocabulary_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ALTER TABLE ControlledVocabulary
-- ADD CONSTRAINT FK_ControlledVocabulary_1 FOREIGN KEY FK_controlledVocabulary_1 (_controlledVocabularyGroup_key)
--     REFERENCES cv_controlledVocabularyGroup (_controlledVocabularyGroup_key)
--     ON DELETE RESTRICT
--     ON UPDATE RESTRICT;

SET @CENTERS = 'Centers';
SET @GENOTYPING = 'Genotyping';
SET @MATINGS = 'Matings';
SET @MOUSE = 'Mice';
SET @PENSROOMSCAGECARDS = 'Pens, Rooms and Cage Cards';
SET @MISCELLANEOUS = 'Miscellaneous';

INSERT INTO cv_ControlledVocabularyGroup
(name, sortOrder)
VALUES
(@CENTERS, 1),
(@GENOTYPING, 2),
(@MATINGS, 3),
(@MISCELLANEOUS, 4),
(@MOUSE, 5),
(@PENSROOMSCAGECARDS, 6)
;

SET @CENTERSKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @CENTERS);
SET @MOUSEKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @MOUSE);
SET @GENOTYPINGKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @GENOTYPING);
SET @MATINGSKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @MATINGS);
SET @PENSROOMSCAGECARDSKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @PENSROOMSCAGECARDS);
SET @MISCELLANEOUSKEY = (SELECT _controlledVocabularyGroup_key FROM cv_ControlledVocabularyGroup WHERE name = @MISCELLANEOUS);

INSERT INTO ControlledVocabulary
(_controlledVocabularyGroup_key, tableName, pkColumnName, columnOneName, columnTwoName, displayName, displayColumnOneName, displayColumnTwoName, subViewName, isUserAdministered, sortOrder, createdBy, dateCreated, modifiedBy, dateModified, version)
VALUES
(@CENTERSKEY, 'Center', '', '', '', 'Centers', '', '', 'CENTER', 1, 1, 'dba', now(), 'dba', now(), 1),

(@GENOTYPINGKEY, 'Allele', '', '', '', 'Allele', '', '', 'ALLELE', 1, 1, 'dba', now(), 'dba', now(), 1),
(@GENOTYPINGKEY, 'Gene', '', '', '', 'Gene', '', '', 'GENE', 1, 2, 'dba', now(), 'dba', now(), 1),
(@GENOTYPINGKEY, 'cv_GeneClass', '_geneClass_key', 'GeneClass', 'Description', 'Gene Class', 'Gene Class', 'Description', 'GENERAL', 1, 3, 'dba', now(), 'dba', now(), 1),
(@GENOTYPINGKEY, 'cv_GenotypeSpecimenLocation', '_genotypeSpecimenLocation_key', 'genotypeSpecimenLocation', '', 'Genotype Specimen Location', 'Genotype Specimen Location', '', 'GENERAL', 1, 4, 'dba', now(), 'dba', now(), 1),

(@MATINGSKEY, 'ApprovedStrainRegistry', '', '', '', 'Approved Mating Strains', '', '', 'APPROVEDMATINGSTRAINS', 1, 1, 'dba', now(), 'dba', now(), 1),

(@MOUSEKEY, 'cv_CauseOfDeath', '_causeOfDeath_key', 'cod', 'description', 'Cause of Death', 'Cause of Death', 'Description', 'GENERAL', 1, 1, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_CoatColor', '_coatColor_key', 'coatColor', 'description', 'Coat Color', 'Coat Color', 'Description', 'GENERAL', 1, 2, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_Diet', '_diet_key', 'diet', 'dietDescription', 'Diet', 'Diet', 'Description', 'GENERAL', 1, 3, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_Generation', '_generation_key', 'generation', '', 'Generation', 'Generation', '', 'GENERAL', 1, 4, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'LifeStatus', '', '', '', 'Life Status', '', '', 'LIFESTATUS', 1, 5, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_MouseOrigin', '_mouseOrigin_key', 'mouseOrigin', '', 'Mouse Origin', 'Mouse Origin', '', 'GENERAL', 1, 6, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_MouseProtocol', '_mouseProtocol_key', 'id', 'description', 'Mouse Protocol', 'Mouse Protocol', 'Description', 'GENERAL', 1, 7, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_MouseUse', '_mouseUse_key', 'mouseUse', 'useDescription', 'Mouse Use', 'Mouse Use', 'Description', 'GENERAL', 1, 8, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'Strain', '', '', '', 'Strain', '', '', 'STRAIN', 1, 9, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_StrainStatus', '_strainStatus_key', 'strainStatus', 'description', 'Strain Status', 'Strain Status', 'Description', 'GENERAL', 1, 10, 'dba', now(), 'dba', now(), 1),
(@MOUSEKEY, 'cv_StrainType', '_strainType_key', 'strainType', '', 'Strain Type', 'Strain Type', '', 'GENERAL', 1, 11, 'dba', now(), 'dba', now(), 1),

(@PENSROOMSCAGECARDSKEY, 'cv_HealthLevel', '_healthLevel_key', 'healthLevel', 'description', 'Health Level', 'Health Level', 'Description', 'GENERAL', 1, 1, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'Room', '', '', '', 'Manage Pen Configuration', '', '', 'MANAGEPENCONFIGURATION', 1, 2, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'cv_MatingCardNotes', '_matingCardNotes_key', 'matingNotes', '', 'Mating Card Notes', 'Mating Card Notes', '', 'GENERAL', 1, 3, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'cv_ContainerStatus', '', '', '', 'Pen Status', '', '', 'PENSTATUS', 1, 4, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'Container', '', '', '', 'Manage Pens', '', '', 'MANAGEPENS', 1, 5, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'Container', '', '', '', 'Retire Pens', '', '', 'RETIREPENS', 1, 6, 'dba', now(), 'dba', now(), 1),
(@PENSROOMSCAGECARDSKEY, 'Room', '', '', '', 'Manage Room', '', '', 'MANAGEROOM', 1, 7, 'dba', now(), 'dba', now(), 1),

(@MISCELLANEOUSKEY, '',  '', '', '', 'JCMS Setup Variables', '', '', 'JCMSSETUPVARIABLES', 1, 1, 'dba', now(), 'dba', now(), 1)
;

CALL DropConstraintWithoutName('ContainerHistory','_containerStatus_key', 'cv_ContainerStatus', '_containerStatus_key');
CALL DropConstraintWithoutName('Genotype','_gene_key', 'Gene', '_gene_key');
CALL DropConstraintWithoutName('HealthLevelHistory','_healthLevel_key', 'cv_HealthLevel', '_healthLevel_key');

ALTER TABLE cv_ContainerStatus MODIFY COLUMN _containerStatus_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE Gene MODIFY COLUMN _gene_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE cv_HealthLevel MODIFY COLUMN _healthLevel_key INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ContainerHistory` ADD CONSTRAINT `containerhistory_containerstatus_ibfk` FOREIGN KEY `containerhistory_containerstatus_ibfk` (`_containerStatus_key`)
    REFERENCES `cv_ContainerStatus` (`_containerStatus_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE `Genotype` ADD CONSTRAINT `genotype_gene_ibfk` FOREIGN KEY `genotype_gene_ibfk` (`_gene_key`)
    REFERENCES `Gene` (`_gene_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE `HealthLevelHistory` ADD CONSTRAINT `healthlevelhistory_healthlevel_ibfk` FOREIGN KEY `healthlevelhistory_healthlevel_ibfk` (`_healthLevel_key`)
    REFERENCES `cv_HealthLevel` (`_healthLevel_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

CREATE TABLE IF NOT EXISTS cv_StrainStatus (
  _strainStatus_key INTEGER NOT NULL AUTO_INCREMENT,
  strainStatus VARCHAR(1) NOT NULL,
  description VARCHAR(1024) ,
  isActive INTEGER NOT NULL DEFAULT -1,
  createdBy VARCHAR(45) NOT NULL DEFAULT 'dba',
  dateCreated DATETIME NOT NULL DEFAULT '1970-01-01 01:00:00',
  modifiedBy VARCHAR(45) NOT NULL DEFAULT 'dba',
  dateModified DATETIME NOT NULL DEFAULT '1970-01-01 01:00:00',
  version INTEGER NOT NULL DEFAULT 1,
  PRIMARY KEY (_strainStatus_key)
)
ENGINE = InnoDB
CHARACTER SET utf8;

DELETE FROM cv_StrainStatus;
INSERT INTO cv_StrainStatus
(_strainStatus_key, strainStatus, description, createdBy, dateCreated, modifiedBy, dateModified, version, isActive)
VALUES
(1, 'A', 'Active', 'dba', now(), 'dba', now(), 1, -1),
(2, 'C', 'See comments', 'dba', now(), 'dba', now(), 1, -1),
(3, 'D', 'Discarded', 'dba', now(), 'dba', now(), 1, -1),
(4, 'F', 'Frozen and not active', 'dba', now(), 'dba', now(), 1, -1)
;

#dbChange 65

CALL DropConstraintWithoutName('Mouse','_strain_key', 'Strain', '_strain_key');
CALL DropConstraintWithoutName('Mating','_strain_key', 'Strain', '_strain_key');

CALL DropConstraintWithoutName('Mouse','_pen_key', 'Container', '_container_key');
CALL DropConstraintWithoutName('ContainerHistory','_container_key', 'Container', '_container_key');


ALTER TABLE Strain MODIFY COLUMN _strain_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE Container MODIFY COLUMN _container_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ContainerHistory MODIFY COLUMN _containerHistory_key INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `Mouse` ADD CONSTRAINT `mouse_strain_ibfk` FOREIGN KEY `mouse_strain_ibfk` (`_strain_key`)
    REFERENCES `Strain` (`_strain_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `Mating` ADD CONSTRAINT `mating_strain_ibfk` FOREIGN KEY `mating_strain_ibfk` (`_strain_key`)
    REFERENCES `Strain` (`_strain_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `Mouse` ADD CONSTRAINT `mouse_container_ibfk` FOREIGN KEY `mouse_container_ibfk` (`_pen_key`)
    REFERENCES `Container` (`_container_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `ContainerHistory` ADD CONSTRAINT `containerhistory_container_ibfk` FOREIGN KEY `containerhistory_container_ibfk` (`_container_key`)
    REFERENCES `Container` (`_container_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE Allele ADD CONSTRAINT allele_gene_ibfk FOREIGN KEY allele_gene_ibfk (_gene_key)
    REFERENCES Gene (_gene_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;

