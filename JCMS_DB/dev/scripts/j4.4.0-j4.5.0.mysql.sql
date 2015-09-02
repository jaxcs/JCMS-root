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


-- MySQL upgrade for JCMS 4.4.0 to JCMS 4.5.0
-- Tested on MySQL 5.0 on Windows 
/*
Delimiter //

DROP PROCEDURE IF EXISTS `j4_4_0__j4_5_0` //

CREATE  PROCEDURE `j4_4_0__j4_5_0`()
BEGIN

SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j4_4_0__j4_5_0';
*/

-- SQL Commands go here.
INSERT INTO DbFormPrivileges (formName, privilegeLevel, completeFormName, description, version)
SELECT * FROM (SELECT 'Breeding Performance Report', 'Sec', 'BreedingPerformanceReport', 'Reports: Matings - Summarize litters', 1) AS tmp
WHERE NOT EXISTS (
    SELECT formName FROM DbFormPrivileges WHERE formName = 'Breeding Performance Report'
) LIMIT 1;


/*
COMMIT;

END;

//

Delimiter ;*/