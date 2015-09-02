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


SELECT '--dbChange_104.sql--';
SELECT '--1--';
--
-- Creates User Account tables if they exist or not
--

ALTER TABLE dbInfo DROP COLUMN databaseReleaseNum ;
ALTER TABLE dbInfo DROP COLUMN majorVersion ;
ALTER TABLE dbInfo DROP COLUMN minorVersion ;
ALTER TABLE dbInfo DROP COLUMN bugFixVersion ;
ALTER TABLE dbInfo DROP COLUMN buildVersion ;

ALTER TABLE dbInfo ADD COLUMN webReleaseNum VARCHAR(16) NOT NULL AFTER version ;


-- DISABLE
SET FOREIGN_KEY_CHECKS = 0;

--
-- Table structure for table `Privilege`
--

SELECT '--2--';
DROP TABLE IF EXISTS `Privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `Privilege` (
  `_Privilege_key` int(11) NOT NULL AUTO_INCREMENT,
  `Privilege` varchar(75) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Privilege_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO Privilege (
	Privilege,
	Description,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
	Version
	)
-- VALUES (
	SELECT 'Read','User is able to select data','dba','2010-10-25 16:41:28','dba','2010-10-25 16:41:28',1
  FROM dbInfo
  WHERE NOT EXISTS (SELECT 1 FROM Privilege t where t.Privilege = 'Read')
  LIMIT 1
-- )
;
INSERT INTO Privilege (
	Privilege,
	Description,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
	Version
	)
-- VALUES (
	SELECT 'Write','User is able to select, insert, update, and delete data','dba','2010-10-25 16:41:28','dba','2010-10-25 16:41:28',1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM Privilege t where t.Privilege = 'Write')
  LIMIT 1
 -- )
;

--
-- Table structure for table `FunctionalArea`
--

SELECT '--3--';
DROP TABLE IF EXISTS `FunctionalArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `FunctionalArea` (
  `_FunctionalArea_key` int(11) NOT NULL AUTO_INCREMENT,
  `FunctionalArea` varchar(75) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_FunctionalArea_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO FunctionalArea (
	FunctionalArea,
	Description,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
	Version
	)
-- VALUES (
	SELECT 'Administration',NULL,'dba',now(),'dba',now(),1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM FunctionalArea t where t.FunctionalArea = 'Administration')
  LIMIT 1
 -- )
;
INSERT INTO FunctionalArea (
	FunctionalArea,
	Description,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
	Version
	)
-- VALUES (
	SELECT 'Querying',NULL,'dba',now(),'dba',now(),1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM FunctionalArea t where t.FunctionalArea = 'Querying')
  LIMIT 1
 -- )
;
INSERT INTO FunctionalArea (
	FunctionalArea,
	Description,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
	Version
	)
-- VALUES (
	SELECT 'Reporting',NULL,'dba',now(),'dba',now(),1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM FunctionalArea t where t.FunctionalArea = 'Reporting')
  LIMIT 1
 -- )
;


--
-- Table structure for table `Center`
--

SELECT '--4--';
DROP TABLE IF EXISTS `Center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `Center` (
  `_Center_key` int(11) NOT NULL AUTO_INCREMENT,
  `Center` varchar(75) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Center_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Workgroup`
--

SELECT '--5--';
DROP TABLE IF EXISTS `Workgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `Workgroup` (
  `_Workgroup_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Center_key` int(11) NOT NULL,
  `WorkgroupName` varchar(75) NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Workgroup_key`),
  KEY `Ref189300` (`_Center_key`),
  CONSTRAINT `RefCenter300` FOREIGN KEY (`_Center_key`) REFERENCES `Center` (`_Center_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

SELECT '--6--';
DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `User` (
  `_User_key` int(11) NOT NULL AUTO_INCREMENT,
  `NetworkID` varchar(64) DEFAULT NULL,
  `FirstName` varchar(64) DEFAULT NULL,
  `LastName` varchar(64) DEFAULT NULL,
  `Title` varchar(128) DEFAULT NULL,
  `EmailAddress` varchar(64) DEFAULT NULL,
  `InternalPhone` varchar(64) DEFAULT NULL,
  `ExternalPhone` varchar(64) DEFAULT NULL,
  `HireDate` date DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_DefaultWorkgroup_key` int(11) NOT NULL DEFAULT '1',
  `IsMasterAdministrator` tinyint(4) DEFAULT NULL,
  `UserName` varchar(128) NOT NULL,
  `Password_` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`_User_key`),
  KEY `RefDefaultWorkgroup` (`_DefaultWorkgroup_key`),
  CONSTRAINT `RefDefaultWorkgroup` FOREIGN KEY (`_DefaultWorkgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkgroupUser`
--

SELECT '--7--';
DROP TABLE IF EXISTS `WorkgroupUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `WorkgroupUser` (
  `_WorkgroupUser_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Workgroup_key` int(11) NOT NULL,
  `_User_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_WorkgroupUser_key`),
  KEY `Ref181295` (`_Workgroup_key`),
  KEY `Ref184296` (`_User_key`),
  CONSTRAINT `RefUser296` FOREIGN KEY (`_User_key`) REFERENCES `User` (`_User_key`) ON DELETE CASCADE,
  CONSTRAINT `WorkgroupUser-Workgroup` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkgroupUserFunctionalArea`
--

SELECT '--8--';
DROP TABLE IF EXISTS `WorkgroupUserFunctionalArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `WorkgroupUserFunctionalArea` (
  `_WorkgroupUserFunctionalArea_key` int(11) NOT NULL AUTO_INCREMENT,
  `_FunctionalArea_key` int(11) NOT NULL,
  `_WorkgroupUser_key` int(11) NOT NULL,
  `_Privilege_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_WorkgroupUserFunctionalArea_key`),
  KEY `RefFunctionalArea4` (`_FunctionalArea_key`),
  KEY `RefPrivilege6` (`_Privilege_key`),
  KEY `RefWorkgroupUser` (`_WorkgroupUser_key`),
  CONSTRAINT `RefFunctionalArea4` FOREIGN KEY (`_FunctionalArea_key`) REFERENCES `FunctionalArea` (`_FunctionalArea_key`),
  CONSTRAINT `RefPrivilege6` FOREIGN KEY (`_Privilege_key`) REFERENCES `Privilege` (`_Privilege_key`),
  CONSTRAINT `RefWorkgroupUser` FOREIGN KEY (`_WorkgroupUser_key`) REFERENCES `WorkgroupUser` (`_WorkgroupUser_key`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cv_VocabularySource`
--

SELECT '--9--';
DROP TABLE IF EXISTS `cv_VocabularySource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `cv_VocabularySource` (
  `_VocabularySource_key` int(11) NOT NULL AUTO_INCREMENT,
  `VocabularySource` varchar(75) NOT NULL,
  `URL` varchar(250) DEFAULT NULL,
  `SourceVersion` decimal(18,0) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_VocabularySource_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkgroupVocabularySource`
--

SELECT '--10--';
DROP TABLE IF EXISTS `WorkgroupVocabularySource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `WorkgroupVocabularySource` (
  `_WorkgroupVocabularySource_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Workgroup_key` int(11) NOT NULL,
  `_VocabularySource_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `HasAdminPrivileges` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`_WorkgroupVocabularySource_key`),
  KEY `RefWorkgroup1` (`_Workgroup_key`),
  KEY `_WorkgroupVocabularySource-cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup1` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `_WorkgroupVocabularySource-cv_VocabularySource` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cv_QueryType`
--

SELECT '--11--';
DROP TABLE IF EXISTS `cv_QueryType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `cv_QueryType` (
  `_QueryType_key` int(11) NOT NULL AUTO_INCREMENT,
  `QueryType` varchar(45) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_QueryType_key`),
  UNIQUE KEY `QueryType_UNIQUE` (`QueryType`),
  KEY `Refcv_VocabularySource_cv_QueryType` (`_VocabularySource_key`),
  KEY `RefWorkgroup_cv_QueryType` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup_cv_QueryType` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource_cv_QueryType` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



SELECT '--12--';
INSERT INTO cv_QueryType (
	QueryType,
	IsActive,
  SortOrder,
  _Workgroup_key,
  IsDefault,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
  IsDeprecated,
  _VocabularySource_key,
  ElementID,
	Version
	)
-- VALUES (
	SELECT 'MouseQuery',NULL,NULL,NULL,NULL,'jcmsWeb Installer',now(),'jcmsWeb Installer',now(),NULL,NULL,NULL,1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM cv_QueryType t where t.QueryType = 'MouseQuery')
  LIMIT 1
 -- )
;
INSERT INTO cv_QueryType (
	QueryType,
	IsActive,
  SortOrder,
  _Workgroup_key,
  IsDefault,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
  IsDeprecated,
  _VocabularySource_key,
  ElementID,
	Version
	)
-- VALUES (
	SELECT 'MatingQuery',NULL,NULL,NULL,NULL,'jcmsWeb Installer',now(),'jcmsWeb Installer',now(),NULL,NULL,NULL,1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM cv_QueryType t where t.QueryType = 'MatingQuery')
  LIMIT 1
 -- )
;
INSERT INTO cv_QueryType (
	QueryType,
	IsActive,
  SortOrder,
  _Workgroup_key,
  IsDefault,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
  IsDeprecated,
  _VocabularySource_key,
  ElementID,
	Version
	)
-- VALUES (
	SELECT 'Detail',NULL,NULL,NULL,NULL,'jcmsWeb Installer',now(),'jcmsWeb Installer',now(),NULL,NULL,NULL,1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM cv_QueryType t where t.QueryType = 'Detail')
  LIMIT 1
 -- )
;
INSERT INTO cv_QueryType (
	QueryType,
	IsActive,
  SortOrder,
  _Workgroup_key,
  IsDefault,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
  IsDeprecated,
  _VocabularySource_key,
  ElementID,
	Version
	)
-- VALUES (
	SELECT 'Mating',NULL,NULL,NULL,NULL,'jcmsWeb Installer',now(),'jcmsWeb Installer',now(),NULL,NULL,NULL,1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM cv_QueryType t where t.QueryType = 'Mating')
  LIMIT 1
 -- )
;
INSERT INTO cv_QueryType (
	QueryType,
	IsActive,
  SortOrder,
  _Workgroup_key,
  IsDefault,
	CreatedBy,
	DateCreated,
	ModifiedBy,
	DateModified,
  IsDeprecated,
  _VocabularySource_key,
  ElementID,
	Version
	)
-- VALUES (
	SELECT 'Wean',NULL,NULL,NULL,NULL,'jcmsWeb Installer',now(),'jcmsWeb Installer',now(),NULL,NULL,NULL,1
  FROM dbInfo
  WHERE NOT EXISTS
    (SELECT 1 FROM cv_QueryType t where t.QueryType = 'Wean')
  LIMIT 1
 -- )
;


--
-- Table structure for table `QueryDefinition`
--

SELECT '--13--';
DROP TABLE IF EXISTS `QueryDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `QueryDefinition` (
  `_QueryDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_User_key` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) DEFAULT NULL,
  `QueryName` varchar(45) DEFAULT NULL,
  `QueryOptions` longtext COMMENT 'This holds the text that represents XML serialized from the query page managed bean.',
  `_QueryType_key` int(11) DEFAULT '0' COMMENT '0 Mouse\n1 Mating\n\n',
  `IsActive` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_QueryDefinition_key`),
  KEY `fk_QueryDefinition_1` (`_QueryType_key`),
  KEY `fk_QueryDefintion_2` (`_Workgroup_key`),
  CONSTRAINT `fk_QueryDefintion_2` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `fk_QueryDefinition_1` FOREIGN KEY (`_QueryType_key`) REFERENCES `cv_QueryType` (`_QueryType_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- ENABLE
SET FOREIGN_KEY_CHECKS = 1;


UPDATE dbInfo SET
	dbVers = 104,
	versDate = now(),
	releaseNum = '6.0.0', 		-- JCMS Access release number
	webReleaseNum='2.9.0', 		-- JCMS Web release number
	releaseDate = now()
    WHERE _dbinfo_key = 1
;