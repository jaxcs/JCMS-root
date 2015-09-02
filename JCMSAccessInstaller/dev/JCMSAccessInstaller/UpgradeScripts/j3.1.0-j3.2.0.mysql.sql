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


-- MySQL upgrade for JCMS 3.1.0 to JCMS 3.2.0
-- Tested on MySQL 5.0 on Windows 

Delimiter //

DROP PROCEDURE IF EXISTS `j3_1_0__j3_2_0` //

CREATE  PROCEDURE `j3_1_0__j3_2_0`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_1_0__j3_2_0';

SELECT '1 --------------------------------------------------------------------------------------------';
-- Old Way to Drop a constraint e.g. Foreign key
-- ALTER TABLE `expdata` DROP FOREIGN KEY `expdata_ibfk_3`;
-- New Way
-- call DropConstraintWithoutName('ExpData','_specimen_key','mouse','_mouse_key');


SELECT '2 --------------------------------------------------------------------------------------------';
ALTER TABLE `ExpData` MODIFY COLUMN `_specimen_key` INTEGER NOT NULL;


SELECT '3 --------------------------------------------------------------------------------------------';
-- ALTER TABLE  ExpData ADD FOREIGN KEY (`_specimen_key`)
--    REFERENCES `Mouse` (`_mouse_key`)
--    ON DELETE RESTRICT
--    ON UPDATE RESTRICT;





SELECT '4 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityYellowMinNumMales INTEGER NULL;

SELECT '5 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityYellowMinNumFemales INTEGER NULL;

SELECT '6 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityYellowMaxAgeMales  INTEGER NULL;

SELECT '7 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityYellowMaxAgeFemales  INTEGER NULL;

SELECT '8 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityRedMinNumMales  INTEGER NULL;

SELECT '9 --------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityRedMinNumFemales  INTEGER NULL;

SELECT '10 -------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityRedMaxAgeMales  INTEGER NULL;

SELECT '11 -------------------------------------------------------------------------------------------';
ALTER TABLE Strain ADD COLUMN  lineViabilityRedMaxAgeFemales  INTEGER NULL;


SELECT '12 -------------------------------------------------------------------------------------------';
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description) VALUES ('ImportExpData', 'Owner', 'ImportExpData', 'Experiment: Import external data');

COMMIT;

END

//

Delimiter ;

Call `j3_1_0__j3_2_0`();