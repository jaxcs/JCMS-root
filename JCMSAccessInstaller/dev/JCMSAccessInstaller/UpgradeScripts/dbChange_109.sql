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
CREATE TABLE `Level` (
  `_level_key` int(11) NOT NULL,
  `_room_key` int(11) NOT NULL,
  `levelRef` int(11) NOT NULL,
  `levelId` varchar(32) NOT NULL,
  `levelDetail` LONGTEXT NULL DEFAULT NULL,
  `xmax` int(11) NOT NULL,
  `ymax` int(11) NOT NULL,
  `zmax` int(11) NOT NULL DEFAULT '1',
  `version` int(11) NOT NULL DEFAULT '1',
  `isActive` tinyint(1) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`_level_key`),
  UNIQUE KEY `_level_keyl_UNIQUE` (`_level_key`),
  KEY `room_ibfk_1_idx` (`_room_key`),
  KEY `levelindex1` (`levelId`),
  CONSTRAINT `level_room_ibfk` FOREIGN KEY (`_room_key`) REFERENCES `Room` (`_room_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `ContainerHistory` 
ADD COLUMN `x` INT(11) NULL DEFAULT NULL AFTER `_containerStatus_key`,
ADD COLUMN `y` INT(11) NULL DEFAULT NULL AFTER `x`,
ADD COLUMN `z` INT(11) NULL DEFAULT NULL AFTER `y`,
ADD COLUMN `_level_key` INT(11) NULL DEFAULT NULL AFTER `z`,
ADD INDEX `containerhistory_ibfk_4_idx` (`_level_key` ASC);
ALTER TABLE `ContainerHistory` 
ADD CONSTRAINT `containerhistory_level_ibfk`
  FOREIGN KEY (`_level_key`)
  REFERENCES `Level` (`_level_key`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
INSERT INTO `ControlledVocabulary` (`_controlledVocabulary_key`, `_controlledVocabularyGroup_key`, `tableName`, `pkColumnName`, `columnOneName`, `columnTwoName`, `displayName`, `displayColumnOneName`, `displayColumnTwoName`, `subViewName`, `isUserAdministered`, `sortOrder`, `createdBy`, `dateCreated`, `modifiedBy`, `dateModified`, `version`) VALUES ('39', '6', 'Level', '', '', '', 'Vivaria Layout Manager', '', '', 'LEVELS', '1', '7', 'dba', now(), 'dba', now(), '1');


UPDATE dbInfo SET
	dbVers = 109,
	versDate = now(),
	releaseNum = '6.1.0',
	webReleaseNum = '3.0.0',
	releaseDate = '2013-05-11 00:00:00'
    WHERE _dbinfo_key = 1
;
