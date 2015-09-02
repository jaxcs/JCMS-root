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


-- MySQL upgrade for JCMS 3.0.2 to JCMS 3.1.0
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


DROP PROCEDURE IF EXISTS `j3_0_2__j3_0_3` //

CREATE  PROCEDURE `j3_0_2__j3_0_3`()
BEGIN


SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_0_2__j3_0_3';


SELECT '1 --------------------------------------------------------------------------------------------';
-- Old Way to Drop a constraint e.g. Foreign key
-- alter table `Sample` DROP FOREIGN KEY `sample_ibfk_2`;
-- New Way
call DropConstraintWithoutName('Sample','_weightUnit_key','cv_weightunit','_weightUnit_key');




SELECT '2 --------------------------------------------------------------------------------------------';
-- Old Way to Drop a constraint e.g. Foreign key
-- alter table `Sample` DROP FOREIGN KEY `sample_ibfk_7`;
-- New Way
call DropConstraintWithoutName('Sample','_sample_key','matingsample','_sample_key');



SELECT '3 --------------------------------------------------------------------------------------------';
alter table `MatingSample` ADD FOREIGN KEY matingsample_sample_key_ibfk (`_sample_key`) REFERENCES `Sample`(_sample_key);



SELECT '4 --------------------------------------------------------------------------------------------';
-- Old Way to Drop a constraint e.g. Foreign key
-- alter table `Sample` DROP FOREIGN KEY `sample_ibfk_8`;
-- New Way
call DropConstraintWithoutName('Sample','_sample_key','Mousesample','_sample_key');

SELECT '5 --------------------------------------------------------------------------------------------';
alter table `MouseSample` ADD FOREIGN KEY  mousesample_sample_key_ibfk (`_sample_key`) REFERENCES `Sample`(_sample_key);


COMMIT;


END;

//
