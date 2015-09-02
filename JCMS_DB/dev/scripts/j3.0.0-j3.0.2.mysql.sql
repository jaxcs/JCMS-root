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


-- MySQL upgrade for JCMS 3.0.0 to JCMS 3.0.2
-- Tested on MySQL 5.0 on Windows 

Delimiter //

-- Drop Constraint Without Name -----------------------------------------
DROP PROCEDURE IF EXISTS `DropConstraintWithoutName` //

CREATE PROCEDURE `DropConstraintWithoutName`(IN myTable VARCHAR(32), IN myCol VARCHAR(32), IN refTable VARCHAR(32) , IN refCol VARCHAR(32) )
BEGIN

DECLARE myConstraintName VARCHAR(32)  ;
DECLARE myDatabase VARCHAR(32)  ;

SELECT DATABASE() INTO myDatabase;


SELECT CONSTRAINT_NAME INTO myConstraintName

FROM information_schema.key_column_usage
WHERE CONSTRAINT_SCHEMA =  myDatabase AND
            TABLE_NAME = myTable AND
            COLUMN_NAME = myCol AND
            REFERENCED_TABLE_NAME = refTable AND
            REFERENCED_COLUMN_NAME = refCol;


IF NULL <=> myConstraintName THEN
    Select 'WHOA DUDE';
    Select 'I can not find the constraint you are attempting to delete';
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

DROP PROCEDURE IF EXISTS `j3_0_0__j3_0_2` //

CREATE  PROCEDURE `j3_0_0__j3_0_2`()
BEGIN


SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_0_0__j3_0_2';


SELECT '1 --------------------------------------------------------------------------------------------';
ALTER table `DbSetup` ADD PRIMARY KEY `DbSetup`(MTSVar);


SELECT '2 --------------------------------------------------------------------------------------------';
ALTER table `Storage` ALTER COLUMN `_preservationDetail_key` SET DEFAULT 0;


SELECT '3 --------------------------------------------------------------------------------------------';
call DropConstraintWithoutName('Storage','_sampleStatus_key','cv_samplestatus','_sampleStatus_key');

COMMIT;

END;

//

Delimiter ;

call `j3_0_0__j3_0_2`();

