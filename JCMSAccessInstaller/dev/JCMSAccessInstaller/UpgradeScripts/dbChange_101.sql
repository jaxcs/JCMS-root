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
SELECT '--dbChange_101.sql--';
SELECT '--1--';
CREATE TABLE MatingUnitLink(
    _matingUnitLink_key    INT(11)    AUTO_INCREMENT,
    _mating_key            INT(11),
    _mouse_key             INT(11),
    _sample_key            INT(11),
    _matingUnitType_key    INT(11)    NOT NULL,
    PRIMARY KEY (_matingUnitLink_key)
)ENGINE=INNODB;
#
# TABLE: cv_MatingUnitType
#
SELECT '--2--';
CREATE TABLE cv_MatingUnitType(
    _matingUnitType_key      INT(11)            AUTO_INCREMENT,
    matingUnitType           VARCHAR(75)    NOT NULL,
    abbreviation             VARCHAR(12),
    isActive                 TINYINT,
    isDefault                TINYINT,
    sortOrder                INT(11),
    _vocabularySource_key    INT(11),
    elementID                VARCHAR(18),
    isDeprecated             TINYINT,
    version                  INT(11)            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_matingUnitType_key)
)ENGINE=INNODB;

#
SELECT '--3--';
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Dam (Biological Female Parent)', 'Dam', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Sire (Biological Male Parent)', 'Sire', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Embryo Sample', 'Embryo', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Ovary Sample', 'Ovary', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Surrogate or Host Mother', 'Host Dam', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Egg Sample', 'Egg', -1, 0, 0);
INSERT INTO `cv_MatingUnitType` (`matingUnitType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Sperm Sample', 'Sperm', -1, 0, 0);
#
#
# TABLE: cv_LitterType
#
SELECT '--4--';
CREATE TABLE cv_LitterType(
    _litterType_key          INT(11)            AUTO_INCREMENT,
    litterType               VARCHAR(75)    NOT NULL,
    abbreviation             VARCHAR(8),
    isActive                 TINYINT,
    isDefault                TINYINT,
    sortOrder                INT(11),
    _vocabularySource_key    INT(11),
    elementID                VARCHAR(18),
    isDeprecated             TINYINT,
    version                  INT(11)            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_litterType_key)
)ENGINE=INNODB;
#
INSERT INTO `cv_LitterType` (`litterType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Live Birth', 'A', -1, -1, 0);
INSERT INTO `cv_Littertype` (`litterType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Harvested Embryos', 'E', -1, 0, 0);
INSERT INTO `cv_LitterType` (`litterType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Embryonic Stem Cells', 'ES', -1, 0, 0);

#
# TABLE: cv_MatingType
#
SELECT '--5--';
CREATE TABLE cv_MatingType(
    _matingType_key          INT(11)            AUTO_INCREMENT,
    matingType               VARCHAR(75)    NOT NULL,
    abbreviation             VARCHAR(8),
    isActive                 TINYINT,
    isDefault                TINYINT,
    sortOrder                INT(11),
    _vocabularySource_key    INT(11),
    elementID                VARCHAR(18),
    isDeprecated             TINYINT,
    version                  INT(11)           DEFAULT 1 NOT NULL,
    PRIMARY KEY (_matingType_key)
)ENGINE=INNODB;

INSERT INTO `cv_MatingType` (`matingType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Natural', 'N', -1, -1, 0);
INSERT INTO `cv_Matingtype` (`matingType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Ovary Transfer', 'OT', -1, 0, 0);
INSERT INTO `cv_Matingtype` (`matingType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('Embryo Transfer', 'ET', -1, 0, 0);
INSERT INTO `cv_Matingtype` (`matingType`, `abbreviation`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('In vitro Fertilization', 'IVF', -1, 0, 0);

#
# TABLE: cv_theilerStage
#
SELECT '--6--';
CREATE TABLE cv_TheilerStage(
    _theilerStage_key        INT(11)            AUTO_INCREMENT,
    TheilerStage             VARCHAR(8)     NOT NULL,
    abbreviation             VARCHAR(4)     NOT NULL,
    dpc                      DOUBLE            NOT NULL,
    startDPC                 DOUBLE            NOT NULL,
    endDPC                   DOUBLE            NOT NULL,
    description              VARCHAR(64),
    isActive                 TINYINT        NOT NULL,
    isDefault                TINYINT,
    sortOrder                INT(11),
    _vocabularySource_key    INT(11),
    elementID                VARCHAR(18),
    isDeprecated             TINYINT,
    version                  INT(11)            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_theilerStage_key)
)ENGINE=INNODB;

INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('1', 'TS01', 0, 0, 2.5, 'One-cell egg', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('2', 'TS02', 1, 1.0, 2.5, 'Beginning of cell division', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('3', 'TS03', 2, 1.0, 3.5, 'Morula', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('4', 'TS04', 3, 2.0, 4.0, 'Advanced division/segmentation', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('5', 'TS05', 4, 3.0, 5.5, 'Blastocyst', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('6', 'TS06', 4.5, 4.0, 6.0, 'Implantation', -1, 0, 0);

INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`)  VALUES ('7', 'TS07', 5, 4.5, 6, 'Formation of egg cylinder', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('8', 'TS08', 6, 5, 6.5, 'Differentiation of egg cylinder', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('9', 'TS09', 6.5, 6.25, 7.25, 'Advanced endometrial reaction, Prestreak', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('10', 'TS10', 7, 6.5, 7.75, 'Amnion, Midstreak', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('11', 'TS11', 7.5, 7.25, 8, 'Neural plate, presomite stage, no allantoic bud', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('12', 'TS12', 8, 7.5, 8.25, 'First somites, late head fold', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('13', 'TS13', 8.5, 8, 9.25, `description`='Turning', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('14', 'TS14', 9, 8.5, 9.75, 'Formation & closure of anterior neuropore', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('15', 'TS15', 9.5, 9, 10.5, 'of posterior neuropore, forelimb bud', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('16', 'TS16', 10, 9.5, 10.75, 'Closure posterior neuropore, hindlimb & tail bud', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('17', 'TS17', 10.5, 10, 11.25, 'Deep lens indentation', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('18', 'TS18', 11, 10.5, 11.25, 'Complete separation of lens vesicle', -1, 0, 0);

INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('19', 'TS19', 11.5, 11, 12.25, 'Closure lens vesicle', -1, 0, 0);

INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('20', 'TS20', 12, 11.5, 13, 'Earliest sign of fingers', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('21', 'TS21', 13, 12.5, 14, 'Anterior footplate indented, marked pinna', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('22', 'TS22', 14, 13.5, 15, 'Fingers separate distally', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('23', 'TS23', 15, 15, 15, 'Toes separate', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('24', 'TS24', 16, 16, 16, 'Reposition of umbilical hernia', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('25', 'TS25', 17, 17, 17, 'Fingers and toes joined together', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('26', 'TS26', 18, 18, 18, 'Long wiskers', -1, 0, 0);
INSERT INTO `cv_TheilerStage` (`TheilerStage`, `abbreviation`, `dpc`, `startDPC`, `endDPC`, `description`, `isActive`, `isDefault`, `isDeprecated`) VALUES ('27', 'TS27', 19, 19, 19, 'Newborn', -1, 0, 0);

#
# TABLE: MatingUnitLink ADD Relationships
#
SELECT '--7--';
ALTER TABLE MatingUnitLink ADD CONSTRAINT Refcv_MatingUnitType76
    FOREIGN KEY (_matingUnitType_key)
    REFERENCES cv_MatingUnitType(_matingUnitType_key);
ALTER TABLE MatingUnitLink ADD CONSTRAINT Refmating79
    FOREIGN KEY (_mating_key)
    REFERENCES Mating(_mating_key);
ALTER TABLE MatingUnitLink ADD CONSTRAINT Refmouse80
    FOREIGN KEY (_mouse_key)
    REFERENCES Mouse(_mouse_key);
ALTER TABLE MatingUnitLink ADD CONSTRAINT Refsample81
    FOREIGN KEY (_sample_key)
    REFERENCES Sample(_sample_key);
#
# INDEX: Ref10976
#

SELECT '--8--';
ALTER TABLE MatingUnitLink ADD INDEX `Ref10976`(_matingUnitType_key);
#CREATE INDEX Ref10976 ON MatingUnitLink(_matingUnitType_key);
#
# INDEX: Ref5079
#
ALTER TABLE MatingUnitLink ADD INDEX `Ref5079`(_mating_key);
#CREATE INDEX Ref5079 ON MatingUnitLink(_mating_key);
#
# INDEX: Ref5280
#
ALTER TABLE MatingUnitLink ADD INDEX `Ref5280`(_mouse_key);
#CREATE INDEX Ref5280 ON MatingUnitLink(_mouse_key);
#
# INDEX: Ref5781
#
ALTER TABLE MatingUnitLink ADD INDEX `Ref5781`(_sample_key);
#CREATE INDEX Ref5781 ON MatingUnitLink(_sample_key);
#
# Litter table new columns and allow birthdate to be null
#
SELECT '--9--';
ALTER TABLE `Litter` ADD COLUMN `_litterType_key` INT(11) NOT NULL  AFTER `version` ,
ADD COLUMN `harvestDate`  DATE NULL DEFAULT NULL  AFTER `_litterType_key`,
ADD COLUMN `numberHarvested` INT(11) NULL DEFAULT NULL  AFTER `harvestDate` ;
ALTER TABLE `Litter` CHANGE COLUMN `birthDate` `birthDate` DATETIME NULL DEFAULT NULL  ;
ALTER TABLE `Litter` ADD COLUMN `_theilerStage_key` INT(11) NULL DEFAULT NULL  AFTER `_mating_key` ;
#
# INDEX: Ref11184
#
#CREATE INDEX Ref11184 ON Litter(_litterType_key);

SELECT '--10--';
ALTER TABLE Litter ADD INDEX `REF11184` (`_litterType_key`);

UPDATE Litter SET _litterType_key = 1 WHERE _litter_key > 0;

ALTER TABLE Litter ADD CONSTRAINT litter_littertype_ibfk
    FOREIGN KEY (_litterType_key)
    REFERENCES cv_LitterType(_litterType_key);

ALTER TABLE `Litter`
  ADD CONSTRAINT `litter_theilerstage_ibfk`
  FOREIGN KEY (`_theilerStage_key` )
  REFERENCES `cv_TheilerStage` (`_theilerStage_key` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `litter_ibfk_2` (`_theilerStage_key` ASC) ;
#
# Mating table new columns
# *************PHASE 1, CHANGE SCRIPT TO ALLOW THE NEW KEY TO BE NULL !!!!!
#
SELECT '--11--';
ALTER TABLE `Mating` ADD COLUMN `_matingType_key` INT(11) NULL DEFAULT NULL AFTER `_mating_key` ;
#
# INDEX: Ref11083
#
CREATE INDEX Ref11083 ON Mating(_matingType_key);

ALTER TABLE Mating ADD CONSTRAINT Refcv_MatingType83
    FOREIGN KEY (_matingType_key)
    REFERENCES cv_MatingType(_matingType_key);

#
# Genotype table new column and make _mouse_key nullable
#
SELECT '--12--';
ALTER TABLE `Genotype`
  ADD COLUMN `_sample_key` INT(11) NULL DEFAULT NULL  AFTER `_mouse_key`,
  ADD CONSTRAINT `genotype_sample_ibfk` FOREIGN KEY `genotype_sample_ibfk` (`_sample_key`)
    REFERENCES `Sample` (`_sample_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  ADD INDEX `_sample_key` (`_sample_key` ASC)  ,
  ADD INDEX `genotype_ibfk_3` (`_sample_key` ASC) ;
CALL DropConstraintWithoutName('Genotype','_mouse_key', 'Mouse', '_mouse_key');
ALTER TABLE `Genotype`
  CHANGE COLUMN `_mouse_key` `_mouse_key` INT(11) NULL  ,
  ADD CONSTRAINT `genotype_mouse_ibfk` FOREIGN KEY `genotype_mouse_ibfk` (`_mouse_key`)
    REFERENCES `Mouse` (`_mouse_key`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
#
# Add seeded values
#


#
# MouseUsage table new field
#
SELECT '--13--';
ALTER TABLE `MouseUsage` ADD COLUMN `_plugDate_key` INT(11) NULL DEFAULT NULL  AFTER `_mouse_key` ;
ALTER TABLE `MouseUsage`
  ADD CONSTRAINT `mouseusage_plugdate_ibfk`
  FOREIGN KEY (`_plugDate_key` )
  REFERENCES `PlugDate` (`_plugDate_key` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `mouseusage_ibfk_2` (`_plugDate_key` ASC) ;

INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES('Add Embryo Litter', 'Sec', 'AddEmbryoLitter', 'Add Embryo Litter: A litter that is stored as a sample');
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES('Edit Embryo Litter', 'Owner', 'EditEmbryoLitter', 'Edit Embryo Litter: Edit litters that created samples');

UPDATE dbInfo SET dbVers = 101, versDate = now(), databaseReleaseNum = 101 ;