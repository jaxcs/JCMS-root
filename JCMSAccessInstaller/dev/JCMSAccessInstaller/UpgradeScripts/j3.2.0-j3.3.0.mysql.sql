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


-- MySQL upgrade for JCMS 3.2.0 to JCMS 3.3.0
-- Tested on MySQL 5.0 on Windows 

Delimiter //

DROP PROCEDURE IF EXISTS `j3_2_0__j3_3_0` //

CREATE  PROCEDURE `j3_2_0__j3_3_0`()
BEGIN


SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_2_0__j3_3_0';




SELECT '1 --------------------------------------------------------------------------------------------';
CREATE TABLE IF NOT EXISTS `PlugDate`
(`_plugDate_key` INTEGER NOT NULL,
 `_mating_key` INTEGER NOT NULL,
 `_mouse_key` INTEGER NOT NULL,
 `plugDate` DATETIME NOT NULL,
 `obsolete` TINYINT(1) NULL DEFAULT 0, 
 `comment` VARCHAR (255) NULL,
UNIQUE INDEX `plugDate_key`(`_plugDate_key`),
PRIMARY KEY (`_plugDate_key`),
INDEX `_mating_key`(`_mating_key`),
INDEX `_mouse_key`(`_mouse_key`),
INDEX `MatingPlugDate`(`_mating_key`),
INDEX `MousePlugDate`(`_mouse_key`)) ENGINE =INNODB CHARACTER SET utf8;



SELECT '2 --------------------------------------------------------------------------------------------';
ALTER TABLE `PlugDate` ADD FOREIGN KEY (`_mating_key`) REFERENCES `Mating`(`_mating_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;

SELECT '3 --------------------------------------------------------------------------------------------';
ALTER TABLE `PlugDate` ADD FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


SELECT '4 --------------------------------------------------------------------------------------------';
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Add Plug Date','Sec','AddPlugDate','Add Plug Date:  Associate  a plug date with a specific mating and dam');

SELECT '5 --------------------------------------------------------------------------------------------';
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Browse Sample By Location','Sec','BrowseSampleByLocation','Query Samples: Look at the locations of samples using a browse type interface.');

SELECT '6 --------------------------------------------------------------------------------------------';
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Edit  Plug  Date','Sec','EditPlugDate','Edit  Plug  Date:  Edit  plug  dates associated with a mating');

SELECT '7 --------------------------------------------------------------------------------------------';
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('Plug Date and Pregnancy Reports','Sec','PlugDateReports','Reports: Plug check, Preganancy check, Stage check, Litter check work reports');

COMMIT;

END;


//

Delimiter ;
Call `j3_2_0__j3_3_0`()