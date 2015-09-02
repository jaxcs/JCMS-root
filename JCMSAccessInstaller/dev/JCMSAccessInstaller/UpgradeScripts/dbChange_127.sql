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
CREATE  TABLE `cv_Phenotype` (

  `_phenotype_key` INT(11) NOT NULL AUTO_INCREMENT ,

  `phenotype` VARCHAR(255) NOT NULL ,

  `description` VARCHAR(255) NULL ,

  `isActive` TINYINT(1) NOT NULL DEFAULT '-1' ,

  `version` INT(11) NOT NULL DEFAULT '1' ,

  PRIMARY KEY (`_phenotype_key`) ,

  UNIQUE INDEX `_phenotype_key_UNIQUE` (`_phenotype_key` ASC) ,

  UNIQUE INDEX `phenotype_UNIQUE` (`phenotype` ASC) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE  TABLE `PhenotypeMouseLink` (
  `_phenotypeMouseLink_key` INT(11) NOT NULL AUTO_INCREMENT ,
  `_phenotype_key` INT(11) NOT NULL ,
  `_mouse_key` INT(11) NOT NULL ,
  `version` INT(11) NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`_phenotypeMouseLink_key`) ,
  UNIQUE INDEX `_phenotypeMouseLink_key_UNIQUE` (`_phenotypeMouseLink_key` ASC) ,
  INDEX `phenotypeMouseLink_ibfk_1_idx` (`_phenotype_key` ASC),
  INDEX `phenotypeMouseLink_ibfk_2_idx` (`_mouse_key` ASC),
  CONSTRAINT `phenotypemouselink_phenotype_ibfk`
    FOREIGN KEY (`_phenotype_key`)
    REFERENCES `cv_Phenotype` (`_phenotype_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `phenotypemouselink_mouse_ibfk`
    FOREIGN KEY (`_mouse_key`)
    REFERENCES `Mouse` (`_mouse_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;
  
UPDATE dbInfo SET
	dbVers = 127,
	webReleaseNum= '3.8.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

