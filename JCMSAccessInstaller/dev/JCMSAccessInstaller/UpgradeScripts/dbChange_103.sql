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

SELECT '--dbChange_103.sql--';
SELECT '--1--';

SET FOREIGN_KEY_CHECKS = 0;

#Change Mating._matingType_key to be required
#Set all to be 1 for Natural mating
#Allow _dam1_key and _sire_key to be null. The default is null

SELECT '--5--';
UPDATE `Mating` SET Mating._matingType_key = 1 WHERE Mating._mating_key > 0;

CALL DropConstraintWithoutName('Mating','_matingType_key', 'cv_MatingType', '_matingType_key');
ALTER TABLE `Mating` CHANGE COLUMN `_matingType_key` `_matingType_key` INT(11) NOT NULL  ,
  ADD CONSTRAINT `mating_matingtype_ibfk`
  FOREIGN KEY (`_matingType_key` )
  REFERENCES `cv_MatingType` (`_matingType_key` );

CALL DropConstraintWithoutName('Mating','_sire_key', 'Mouse', '_mouse_key');
ALTER TABLE `Mating` CHANGE COLUMN `_dam1_key` `_dam1_key` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `_sire_key` `_sire_key` INT(11) NULL DEFAULT NULL  ,
  ADD CONSTRAINT `mating_mouse_ibfk`
  FOREIGN KEY (`_sire_key` )
  REFERENCES `Mouse` (`_mouse_key` );

#Create the appropriate records in the MatingUnitLink table for all existing Mating table records

SELECT '--6--';
INSERT INTO MatingUnitLink ( _mating_key, _mouse_key, _matingUnitType_key )
SELECT Mating._mating_key, Mating._dam1_key, 1 AS Expr1
FROM Mating LEFT JOIN MatingUnitLink ON Mating._mating_key = MatingUnitLink._mating_key;

INSERT INTO MatingUnitLink ( _mating_key, _mouse_key, _matingUnitType_key )
SELECT Mating._mating_key, Mating._sire_key, 2 AS Expr1
FROM Mating LEFT JOIN MatingUnitLink ON Mating._mating_key = MatingUnitLink._mating_key;

INSERT INTO MatingUnitLink ( _mating_key, _mouse_key, _matingUnitType_key )
SELECT DISTINCT Mating._mating_key, Mating._dam2_key, 1 AS Expr1
FROM Mating LEFT JOIN MatingUnitLink ON Mating._mating_key = MatingUnitLink._mating_key WHERE (Mating._dam2_key Is Not Null);

#Add to cv_SampleType the following seeded terms. They all have _sampleClass_key for "Live".
#Egg, Ovarian tissue, Ovary

SELECT '--7--';
CALL DropConstraintWithoutName('cv_SampleType','_sampleClass_key', 'cv_SampleClass', '_sampleClass_key');
ALTER TABLE `cv_SampleType` CHANGE COLUMN `_sampleType_key` `_sampleType_key` INT(11) NOT NULL AUTO_INCREMENT  , CHANGE COLUMN `_sampleClass_key` `_sampleClass_key` INT(11) NOT NULL  ,
  ADD CONSTRAINT `sampletype_sampletype_ibfk`
  FOREIGN KEY (`_sampleClass_key` )
  REFERENCES `cv_SampleClass` (`_sampleClass_key` );

SELECT '--8--';
INSERT IGNORE INTO cv_SampleClass (sampleClass, version) VALUES ('Live', 1);
  
SELECT '--8.1--';
INSERT INTO cv_SampleType (sampleType, _sampleClass_key)
	SELECT 'Egg',(SELECT _sampleClass_key FROM cv_SampleClass WHERE sampleClass = 'Live')
	FROM dbInfo
	WHERE NOT EXISTS
	 (SELECT 1 from cv_SampleType t WHERE t.sampleType = 'Egg')
	LIMIT 1;

INSERT INTO cv_SampleType (sampleType, _sampleClass_key)
	SELECT 'Ovary',(SELECT _sampleClass_key FROM cv_SampleClass WHERE sampleClass = 'Live')
	FROM dbInfo
	WHERE NOT EXISTS
	 (SELECT 1 from cv_SampleType t WHERE t.sampleType = 'Ovary')
	LIMIT 1;

INSERT INTO cv_SampleType (sampleType, _sampleClass_key)
	SELECT 'Ovarian tissue',(SELECT _sampleClass_key FROM cv_SampleClass WHERE sampleClass = 'Live')
	FROM dbInfo
	WHERE NOT EXISTS
	 (SELECT 1 from cv_SampleType t WHERE t.sampleType = 'Ovarian tissue')
	LIMIT 1;

#Add a record to UpgradeHistoryLog table

SELECT '--9--';
INSERT INTO UpgradeHistoryLog (majorVersion, minorVersion, bugFixVersion, upgradeDate, actionDescription, actionCompleted, actionDate) VALUES (6,0,0,now(),'NONE',-1,now());

SET FOREIGN_KEY_CHECKS = 1;

UPDATE dbInfo SET
	dbVers = 103,
	versDate = now(),
	releaseNum = '6.0.0', 		-- JCMS Access release number
	releaseDate = now()
    WHERE _dbinfo_key = 1
;