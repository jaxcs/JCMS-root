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


-- MySQL upgrade for JCMS 3.3.0 to JCMS 3.4.0
-- Tested on MySQL 5.0 on Windows 

Delimiter //

DROP PROCEDURE IF EXISTS `j3_3_0__j3_4_0` //

CREATE  PROCEDURE `j3_3_0__j3_4_0`()
BEGIN


SELECT '----------------------------------------------------------------------------------------------';
SELECT 'EXECUTING j3_3_0__j3_4_0';




SELECT '1 ---Create Document Table -------------------------------------------------------------------';

CREATE TABLE `Document`(
	`_document_key` INTEGER NOT NULL, 
	`FilePath` VARCHAR (255) NULL, 
	`FileName` VARCHAR (64) NULL, 
	`DateUpload` DATETIME NULL, 
	`Owner` VARCHAR (255) NULL, 
	PRIMARY KEY (`_document_key`) 
)  ENGINE =INNODB CHARACTER SET utf8;

SELECT '2 ---Create GenotypeDocument Table -----------------------------------------------------------';
CREATE TABLE `GenotypeDocument` (
    `_genotypeDocument_key` INTEGER NULL,
    `_document_key` INTEGER NULL,
    `_genotype_key` INTEGER NULL,
    PRIMARY KEY (`_genotypeDocument_key`),
    CONSTRAINT UNIQUE INDEX (`_document_key`),
    CONSTRAINT UNIQUE INDEX (`_genotype_key`)
)  ENGINE =INNODB CHARACTER SET utf8; 

SELECT '3 --------------------------------------------------------------------------------------------';
ALTER TABLE Genotype MODIFY COLUMN allele1 VARCHAR(8) NULL;

SELECT '4 --------------------------------------------------------------------------------------------';
ALTER TABLE Genotype MODIFY COLUMN allele2 VARCHAR(8) NULL;

SELECT '5 --------------------------------------------------------------------------------------------';
INSERT INTO DbSetup ( `MTSValue`, `MTSVar`, `MTSVarDescription` )  VALUES ( 'PrintSampleLabels','JCMS_SAMPLE_LABEL_REPORT','The name of the report used to print sample labels from the Print Sample Label form');

SELECT '6 --------------------------------------------------------------------------------------------';
INSERT INTO DbSetup ( `MTSValue`, `MTSVar`, `MTSVarDescription` )  VALUES ( ' ','JCMS_DATA_FILE_DIRECTORY','The root directory where JCMS data files are stored');

SELECT '7 --------------------------------------------------------------------------------------------';
UPDATE DbSetup SET MTSValue='true' WHERE MTSVar = 'JCMS_ENABLE_GENOTYPE_IMPORT';



COMMIT;

END;


//

Delimiter ;

Call `j3_3_0__j3_4_0`();