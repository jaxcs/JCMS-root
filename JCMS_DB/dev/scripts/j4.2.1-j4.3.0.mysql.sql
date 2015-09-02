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

DROP PROCEDURE IF EXISTS `j4_2_1__j4_3_0` //

CREATE  PROCEDURE `j4_2_1__j4_3_0`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_2_1__j4_3_0';

ALTER TABLE `Mouse`
	CHANGE COLUMN `newTag` `newTag` VARCHAR(16) NULL DEFAULT NULL  ,
	CHANGE COLUMN `coatColor` `coatColor` VARCHAR(8) NULL DEFAULT NULL  ,
	CHANGE COLUMN `diet` `diet` VARCHAR(32) NULL DEFAULT NULL  ;
COMMIT;


END;

//

Delimiter ;

Call `j4_2_1__j4_3_0`();