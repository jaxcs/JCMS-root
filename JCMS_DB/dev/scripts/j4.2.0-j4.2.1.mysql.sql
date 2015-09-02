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


-- MySQL upgrade for JCMS 4.2.0 to JCMS 4.2.1
-- Tested on MySQL 5.0 on Windows 

Delimiter //

DROP PROCEDURE IF EXISTS `j4_2_0__j4_2_1` //

CREATE  PROCEDURE `j4_2_0__j4_2_1`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_2_0__j4_2_1';

-- Change the defaults
SELECT '-- Modifying Genotype.all1Conf ---';
ALTER TABLE Genotype MODIFY all1Conf TINYINT(1) DEFAULT -1;

SELECT '-- Modifying cv_ContainerStatus billable ---';
ALTER TABLE cv_ContainerStatus MODIFY billable TINYINT(1) DEFAULT -1;

SELECT '-- Modifying Genotype.all2Conf ---';
ALTER TABLE Genotype MODIFY all2Conf TINYINT(1) DEFAULT -1;

SELECT '-- Modifying Mating.weanTime ---';
ALTER TABLE Mating MODIFY weanTime TINYINT(1) DEFAULT -1;

SELECT '-- Modifying Mating.needsTyping ---';
ALTER TABLE Mating MODIFY needsTyping TINYINT(1) DEFAULT -1;

SELECT '-- Modifying cv_TableName: ExpData ---';
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('ExpData', 'Exp Data', '-1', '0');

SELECT '-- Modifying cv_TableName: ExpDataDescriptor ---';
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('ExpDataDescriptor', 'Test Type', '-1', '0');

SELECT '-- Modifying cv_TableName: MouseUsage ---';
INSERT INTO `cv_TableName` (`tableName`, `abbreviation`, `isActive`, `isDefault`) VALUES ('MouseUsage', 'Mouse Use', '-1', '0');

COMMIT;


END;

//

Delimiter ;

Call `j4_2_0__j4_2_1`();