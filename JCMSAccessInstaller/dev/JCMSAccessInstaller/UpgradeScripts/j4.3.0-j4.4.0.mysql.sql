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

Delimiter //

DROP PROCEDURE IF EXISTS `j4_3_0__j4_4_0` //

CREATE  PROCEDURE `j4_3_0__j4_4_0`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_3_0__j4_4_0';

-- SQL Commands go here.
SELECT '--1--';
CREATE  TABLE `cv_CrossStatus` ( 
  `_crossStatus_key` INT(11) NOT NULL AUTO_INCREMENT , 
  `crossStatus` VARCHAR(75) NOT NULL , 
  `abbreviation` VARCHAR(2) NULL , 
  `isActive` TINYINT(1) NULL , 
  `isDefault` TINYINT(1) NULL , 
  `sortOrder` INT NULL , 
  `_vocabularySource_key` INT(11) NULL , 
  `elementID` VARCHAR(18) NULL , 
  `isDeprecated` TINYINT(1) NULL , 
  `version` INT NOT NULL DEFAULT 1 , 
  UNIQUE INDEX `crossStatus_UNIQUE` (`crossStatus` ASC) , 
  PRIMARY KEY (`_crossStatus_key`) , 
  UNIQUE INDEX `_crossStatus_key_UNIQUE` (`_crossStatus_key` ASC) , 
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC) )  ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- Seeded values:
SELECT '--2--';
INSERT INTO `cv_CrossStatus` (`crossStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('designed', 'D', -1, 0); 
SELECT '--3--';
INSERT INTO `cv_CrossStatus` (`crossStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('active', 'A', -1, 0); 
SELECT '--4--';
INSERT INTO `cv_CrossStatus` (`crossStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('designed retirement', 'DR', -1, 0); 
SELECT '--5--';
INSERT INTO `cv_CrossStatus` (`crossStatus`, `abbreviation`, `isActive`, `isDefault`) VALUES ('retired', 'R', -1, 0);
SELECT '--6--';
ALTER TABLE `Mating` ADD COLUMN `_crossStatus_key` INT(11) NULL  AFTER `suggestedFirstLitterNum` ;

SELECT '--7--';
ALTER TABLE `Mating` ADD INDEX `_crossStatus_key` (`_crossStatus_key` ASC) ;

SELECT '--8--';
ALTER TABLE `Mating`  
  ADD CONSTRAINT `mating_crossstatus_ibfk` 
  FOREIGN KEY (`_crossStatus_key` ) 
  REFERENCES `cv_CrossStatus` (`_crossStatus_key` ) 
  ON DELETE NO ACTION 
  ON UPDATE NO ACTION 
, ADD INDEX `mating_ibfk_3` (`_crossStatus_key` ASC) ;

SELECT '--9--';
ALTER TABLE `Strain` ADD COLUMN `isActive` TINYINT(4) NOT NULL DEFAULT  -1  AFTER `formalName`;
SELECT '--10--';
ALTER TABLE `Strain` ADD INDEX `isActive` (`isActive` ASC) ;

COMMIT;

END;

//

Delimiter ;

Call `j4_3_0__j4_4_0`();