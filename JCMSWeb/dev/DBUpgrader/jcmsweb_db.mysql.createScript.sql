-- MySQL dump 10.13  Distrib 5.1.56, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: jcmsweb_db
-- ------------------------------------------------------
-- Server version	5.1.56

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE DATABASE `jcmsweb_db`;
USE `jcmsweb_db`;

--
-- Table structure for table `AccessionIdentifiers`
--

DROP TABLE IF EXISTS `AccessionIdentifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccessionIdentifiers` (
  `_AccessionIdentifier_key` int(11) NOT NULL AUTO_INCREMENT,
  `_MarkerRepository_key` int(11) NOT NULL,
  `_Marker_key` int(11) NOT NULL,
  `AccessionIdentifier` varchar(50) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Allele_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_AccessionIdentifier_key`),
  KEY `Ref5383` (`_MarkerRepository_key`),
  KEY `Ref5884` (`_Marker_key`),
  KEY `RefAllele434` (`_Allele_key`),
  KEY `RefWorkgroup402` (`_Workgroup_key`),
  CONSTRAINT `RefAllele434` FOREIGN KEY (`_Allele_key`) REFERENCES `Allele` (`_Allele_key`),
  CONSTRAINT `Refcv_MarkerRepository83` FOREIGN KEY (`_MarkerRepository_key`) REFERENCES `cv_MarkerRepository` (`_MarkerRepository_key`),
  CONSTRAINT `RefMarker84` FOREIGN KEY (`_Marker_key`) REFERENCES `Marker` (`_Marker_key`),
  CONSTRAINT `RefWorkgroup402` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccessionIdentifiers`
--

LOCK TABLES `AccessionIdentifiers` WRITE;
/*!40000 ALTER TABLE `AccessionIdentifiers` DISABLE KEYS */;
/*!40000 ALTER TABLE `AccessionIdentifiers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AlertCondition`
--

DROP TABLE IF EXISTS `AlertCondition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AlertCondition` (
  `_AlertCondition_key` int(11) NOT NULL AUTO_INCREMENT,
  `AlertCondition` varchar(5000) NOT NULL,
  `Message` varchar(255) NOT NULL,
  `NumberOfFields` int(11) NOT NULL,
  `IsActive` tinyint(4) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_AlertCondition_key`),
  KEY `RefWorkgroup404` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup404` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AlertCondition`
--

LOCK TABLES `AlertCondition` WRITE;
/*!40000 ALTER TABLE `AlertCondition` DISABLE KEYS */;
/*!40000 ALTER TABLE `AlertCondition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AlertConditionFunctionalArea`
--

DROP TABLE IF EXISTS `AlertConditionFunctionalArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AlertConditionFunctionalArea` (
  `_AlertConditionFunctionalArea_key` int(11) NOT NULL AUTO_INCREMENT,
  `_FunctionalArea_key` int(11) NOT NULL,
  `_AlertCondition_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_AlertConditionFunctionalArea_key`),
  KEY `Ref213357` (`_FunctionalArea_key`),
  KEY `Ref210358` (`_AlertCondition_key`),
  KEY `RefWorkgroup405` (`_Workgroup_key`),
  CONSTRAINT `RefAlertCondition358` FOREIGN KEY (`_AlertCondition_key`) REFERENCES `AlertCondition` (`_AlertCondition_key`),
  CONSTRAINT `Refcv_FunctionalArea357` FOREIGN KEY (`_FunctionalArea_key`) REFERENCES `FunctionalArea` (`_FunctionalArea_key`),
  CONSTRAINT `RefWorkgroup405` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AlertConditionFunctionalArea`
--

LOCK TABLES `AlertConditionFunctionalArea` WRITE;
/*!40000 ALTER TABLE `AlertConditionFunctionalArea` DISABLE KEYS */;
/*!40000 ALTER TABLE `AlertConditionFunctionalArea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AlertInstance`
--

DROP TABLE IF EXISTS `AlertInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AlertInstance` (
  `_AlertInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_FunctionalArea_key` int(11) NOT NULL,
  `Message` varchar(255) NOT NULL,
  `IsDismissed` tinyint(4) NOT NULL,
  `Comment` varchar(1024) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `DateExpires` datetime NOT NULL,
  PRIMARY KEY (`_AlertInstance_key`) USING BTREE,
  KEY `Ref213356` (`_FunctionalArea_key`),
  KEY `RefWorkgroup403` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AlertInstance`
--

LOCK TABLES `AlertInstance` WRITE;
/*!40000 ALTER TABLE `AlertInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `AlertInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Allele`
--

DROP TABLE IF EXISTS `Allele`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Allele` (
  `_Allele_key` int(11) NOT NULL AUTO_INCREMENT,
  `_HeritabilityMode_key` int(11) DEFAULT NULL,
  `_HeritabilityStatus_key` int(11) DEFAULT NULL,
  `_Marker_key` int(11) DEFAULT NULL,
  `_AlleleClass_key` int(11) DEFAULT NULL,
  `Allele` varchar(500) DEFAULT NULL,
  `ExpressedInMutant` tinyint(4) DEFAULT NULL,
  `AlleleSymbol` varchar(50) DEFAULT NULL,
  `HeritabilityData` varchar(100) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `AlleleSymbolMarkup` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`_Allele_key`),
  KEY `Ref6078` (`_HeritabilityMode_key`),
  KEY `Ref6179` (`_HeritabilityStatus_key`),
  KEY `Ref5880` (`_Marker_key`),
  KEY `Ref5981` (`_AlleleClass_key`),
  KEY `RefWorkgroup406` (`_Workgroup_key`),
  CONSTRAINT `Refcv_AlleleClass81` FOREIGN KEY (`_AlleleClass_key`) REFERENCES `cv_AlleleClass` (`_AlleleClass_key`),
  CONSTRAINT `Refcv_HeritabilityMode78` FOREIGN KEY (`_HeritabilityMode_key`) REFERENCES `cv_HeritabilityMode` (`_HeritabilityMode_key`),
  CONSTRAINT `Refcv_HeritabilityStatus79` FOREIGN KEY (`_HeritabilityStatus_key`) REFERENCES `cv_HeritabilityStatus` (`_HeritabilityStatus_key`),
  CONSTRAINT `RefMarker80` FOREIGN KEY (`_Marker_key`) REFERENCES `Marker` (`_Marker_key`),
  CONSTRAINT `RefWorkgroup406` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Allele`
--

LOCK TABLES `Allele` WRITE;
/*!40000 ALTER TABLE `Allele` DISABLE KEYS */;
/*!40000 ALTER TABLE `Allele` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BirthEvent`
--

DROP TABLE IF EXISTS `BirthEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BirthEvent` (
  `_BirthEvent_key` int(11) NOT NULL AUTO_INCREMENT,
  `_BirthEventStatus_key` int(11) NOT NULL,
  `_Cross_key` int(11) NOT NULL,
  `_NameFamily_key` int(11) DEFAULT NULL,
  `_PseudoDamOrganism_key` int(11) DEFAULT NULL,
  `DateBorn` datetime DEFAULT NULL,
  `NumberLiveBirths` int(11) DEFAULT NULL,
  `NumberStillBirths` int(11) DEFAULT NULL,
  `NumberLiveFemales` int(11) DEFAULT NULL,
  `NumberLiveMales` int(11) DEFAULT NULL,
  `NumberMissing` int(11) DEFAULT NULL,
  `DateWean` datetime DEFAULT NULL,
  `DateTag` datetime DEFAULT NULL,
  `GenotypeOffspring` tinyint(4) DEFAULT NULL,
  `TimeWean` int(11) DEFAULT NULL,
  `WeanComments` int(11) DEFAULT NULL,
  `Comments` varchar(500) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `BirthEventID` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`_BirthEvent_key`),
  KEY `Ref23` (`_BirthEventStatus_key`),
  KEY `Ref1133` (`_Cross_key`),
  KEY `Ref3050` (`_PseudoDamOrganism_key`),
  KEY `Ref3451` (`_NameFamily_key`),
  KEY `RefWorkgroup407` (`_Workgroup_key`),
  CONSTRAINT `RefCross_33` FOREIGN KEY (`_Cross_key`) REFERENCES `Cross_` (`_Cross_key`),
  CONSTRAINT `Refcv_BirthEventStatus3` FOREIGN KEY (`_BirthEventStatus_key`) REFERENCES `cv_BirthEventStatus` (`_BirthEventStatus_key`),
  CONSTRAINT `RefNameFamily51` FOREIGN KEY (`_NameFamily_key`) REFERENCES `NameFamily` (`_NameFamily_key`),
  CONSTRAINT `RefOrganism50` FOREIGN KEY (`_PseudoDamOrganism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefWorkgroup407` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BirthEvent`
--

LOCK TABLES `BirthEvent` WRITE;
/*!40000 ALTER TABLE `BirthEvent` DISABLE KEYS */;
/*!40000 ALTER TABLE `BirthEvent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BirthEventSample`
--

DROP TABLE IF EXISTS `BirthEventSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BirthEventSample` (
  `_BirthEventSample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_BirthEvent_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_BirthEventSample_key`),
  KEY `Ref101` (`_BirthEvent_key`),
  KEY `Ref4930` (`_Sample_key`),
  KEY `RefWorkgroup408` (`_Workgroup_key`),
  CONSTRAINT `RefBirthEvent1` FOREIGN KEY (`_BirthEvent_key`) REFERENCES `BirthEvent` (`_BirthEvent_key`),
  CONSTRAINT `RefSample30` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup408` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BirthEventSample`
--

LOCK TABLES `BirthEventSample` WRITE;
/*!40000 ALTER TABLE `BirthEventSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `BirthEventSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Center`
--

DROP TABLE IF EXISTS `Center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Center` (
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
-- Dumping data for table `Center`
--

LOCK TABLES `Center` WRITE;
/*!40000 ALTER TABLE `Center` DISABLE KEYS */;
-- INSERT INTO `Center` VALUES (1,'Reproductive Sciences',1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',77),(2,'Custom Breeding',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',77),(3,'JaxWest',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',77),(4,'Scientific Services',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',77),(5,'Molecular Phenotyping Sciences',1,'michaelm','2011-03-31 14:01:51','douglash','2011-03-31 14:28:00',29);
/*!40000 ALTER TABLE `Center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ChangeLog`
--

DROP TABLE IF EXISTS `ChangeLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChangeLog` (
  `_ChangeLog_key` int(11) NOT NULL AUTO_INCREMENT,
  `PrimaryKey` int(11) NOT NULL,
  `TableName` varchar(45) DEFAULT NULL,
  `ColumnName` varchar(45) DEFAULT NULL,
  `OldValue` varchar(1000) DEFAULT NULL,
  `NewValue` varchar(1000) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_ChangeLog_key`),
  KEY `RefChangeLog1` (`_Workgroup_key`),
  CONSTRAINT `RefChangeLog1` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChangeLog`
--

LOCK TABLES `ChangeLog` WRITE;
/*!40000 ALTER TABLE `ChangeLog` DISABLE KEYS */;
/*!40000 ALTER TABLE `ChangeLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Characteristic`
--

DROP TABLE IF EXISTS `Characteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Characteristic` (
  `_Characteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CharacteristicGroup_key` int(11) NOT NULL,
  `Characteristic` varchar(50) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsStandardized` tinyint(4) DEFAULT NULL,
  `IsRequired` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`_Characteristic_key`),
  KEY `Ref194327` (`_CharacteristicGroup_key`),
  KEY `RefWorkgroup411` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicGroup327` FOREIGN KEY (`_CharacteristicGroup_key`) REFERENCES `CharacteristicGroup` (`_CharacteristicGroup_key`),
  CONSTRAINT `RefWorkgroup411` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Characteristic`
--

LOCK TABLES `Characteristic` WRITE;
/*!40000 ALTER TABLE `Characteristic` DISABLE KEYS */;
-- INSERT INTO `Characteristic` VALUES (1,1,'Coat Color','The color of the mouse\'s coat',1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL,NULL),(2,2,'First Name','The person\'s first name',1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,NULL),(3,2,'Last Name','The person\'s family name',1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,NULL),(4,3,'Date Hired','The date on which the person was hired for employment',1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,NULL),(5,4,'Start Time','The normal time team members arrive for work',1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,NULL),(6,4,'End Time','The normal time team members leave work',1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,NULL);
/*!40000 ALTER TABLE `Characteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CharacteristicGroup`
--

DROP TABLE IF EXISTS `CharacteristicGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CharacteristicGroup` (
  `_CharacteristicGroup_key` int(11) NOT NULL AUTO_INCREMENT,
  `CharacteristicGroup` varchar(18) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_CharacteristicGroup_key`),
  KEY `RefWorkgroup412` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup412` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CharacteristicGroup`
--

LOCK TABLES `CharacteristicGroup` WRITE;
/*!40000 ALTER TABLE `CharacteristicGroup` DISABLE KEYS */;
-- INSERT INTO `CharacteristicGroup` VALUES (1,'Coat Color',NULL,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(2,'Name',NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(3,'Date Hired',NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(4,'Normal Work Hours',NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1);
/*!40000 ALTER TABLE `CharacteristicGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CharacteristicUnit`
--

DROP TABLE IF EXISTS `CharacteristicUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CharacteristicUnit` (
  `_CharacteristicUnit_key` int(11) NOT NULL AUTO_INCREMENT,
  `CharacteristicUnit` varchar(18) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_CharacteristicUnit_key`),
  KEY `RefWorkgroup413` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup413` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CharacteristicUnit`
--

LOCK TABLES `CharacteristicUnit` WRITE;
/*!40000 ALTER TABLE `CharacteristicUnit` DISABLE KEYS */;
/*!40000 ALTER TABLE `CharacteristicUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CharacteristicVersion`
--

DROP TABLE IF EXISTS `CharacteristicVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CharacteristicVersion` (
  `_CharacteristicVersion_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Characteristic_key` int(11) NOT NULL,
  `_DataType_key` int(11) NOT NULL,
  `_EnumerationClass_key` int(11) DEFAULT NULL,
  `_CharacteristicUnit_key` int(11) DEFAULT NULL,
  `NumericValidationMin` decimal(38,0) DEFAULT NULL,
  `NumericValidationMax` decimal(38,0) DEFAULT NULL,
  `DateValidationMin` datetime DEFAULT NULL,
  `DateValidationMax` datetime DEFAULT NULL,
  `TextValidationRegex` varchar(256) DEFAULT NULL,
  `IsRequired` int(11) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `Abbreviation` varchar(100) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_CharacteristicVersion_key`),
  KEY `Ref159230` (`_CharacteristicUnit_key`),
  KEY `Ref78231` (`_EnumerationClass_key`),
  KEY `Ref65232` (`_DataType_key`),
  KEY `Ref197326` (`_Characteristic_key`),
  KEY `RefWorkgroup414` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristic326` FOREIGN KEY (`_Characteristic_key`) REFERENCES `Characteristic` (`_Characteristic_key`),
  CONSTRAINT `RefCharacteristicUnit230` FOREIGN KEY (`_CharacteristicUnit_key`) REFERENCES `CharacteristicUnit` (`_CharacteristicUnit_key`),
  CONSTRAINT `Refcv_DataType232` FOREIGN KEY (`_DataType_key`) REFERENCES `cv_DataType` (`_DataType_key`),
  CONSTRAINT `RefEnumerationClass231` FOREIGN KEY (`_EnumerationClass_key`) REFERENCES `EnumerationClass` (`_EnumerationClass_key`),
  CONSTRAINT `RefWorkgroup414` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CharacteristicVersion`
--

LOCK TABLES `CharacteristicVersion` WRITE;
/*!40000 ALTER TABLE `CharacteristicVersion` DISABLE KEYS */;
-- INSERT INTO `CharacteristicVersion` VALUES (1,1,6,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(2,2,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(3,3,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(4,4,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(5,5,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(6,6,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1);
/*!40000 ALTER TABLE `CharacteristicVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContactPerson`
--

DROP TABLE IF EXISTS `ContactPerson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactPerson` (
  `_ContactPerson_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Institution_key` int(11) NOT NULL,
  `_Country_key` int(11) NOT NULL,
  `Title` varchar(128) DEFAULT NULL,
  `FirstName` varchar(64) NOT NULL,
  `LastName` varchar(64) NOT NULL,
  `EmailAddress` varchar(64) NOT NULL,
  `NameSuffix` varchar(18) DEFAULT NULL,
  `Address` varchar(250) DEFAULT NULL,
  `City` varchar(60) DEFAULT NULL,
  `State` varchar(10) DEFAULT NULL,
  `PostalCode` varchar(10) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Fax` varchar(20) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `Extension` varchar(10) DEFAULT NULL,
  `Salutation` varchar(18) DEFAULT NULL,
  `ExternalID` varchar(11) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_ContactPerson_key`),
  KEY `Ref160234` (`_Institution_key`),
  KEY `Ref204351` (`_Country_key`),
  KEY `FK_ContactPerson_3` (`_VocabularySource_key`),
  CONSTRAINT `FK_ContactPerson_3` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_Country351` FOREIGN KEY (`_Country_key`) REFERENCES `cv_Country` (`_Country_key`),
  CONSTRAINT `RefInstitution234` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContactPerson`
--

LOCK TABLES `ContactPerson` WRITE;
/*!40000 ALTER TABLE `ContactPerson` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContactPerson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContactPersonContactPersonType`
--

DROP TABLE IF EXISTS `ContactPersonContactPersonType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactPersonContactPersonType` (
  `_ContactPersonContactPersonType_key` int(11) NOT NULL,
  `_ContactPerson_key` int(11) NOT NULL,
  `_ContactPersonType_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ContactPersonContactPersonType_key`),
  KEY `Ref161354` (`_ContactPerson_key`),
  KEY `Ref188355` (`_ContactPersonType_key`),
  KEY `RefWorkgroup887` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson354` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `Refcv_ContactPersonType355` FOREIGN KEY (`_ContactPersonType_key`) REFERENCES `cv_ContactPersonType` (`_ContactPersonType_key`),
  CONSTRAINT `RefWorkgroup887` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContactPersonContactPersonType`
--

LOCK TABLES `ContactPersonContactPersonType` WRITE;
/*!40000 ALTER TABLE `ContactPersonContactPersonType` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContactPersonContactPersonType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContactPersonDepartment`
--

DROP TABLE IF EXISTS `ContactPersonDepartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactPersonDepartment` (
  `_ContactPersonDepartment_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ContactPerson_key` int(11) NOT NULL,
  `_Department_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ContactPersonDepartment_key`),
  KEY `Ref161342` (`_ContactPerson_key`),
  KEY `Ref199343` (`_Department_key`),
  KEY `RefWorkgroup888` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson342` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `RefDepartment343` FOREIGN KEY (`_Department_key`) REFERENCES `Department` (`_Department_key`),
  CONSTRAINT `RefWorkgroup888` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContactPersonDepartment`
--

LOCK TABLES `ContactPersonDepartment` WRITE;
/*!40000 ALTER TABLE `ContactPersonDepartment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContactPersonDepartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Container`
--

DROP TABLE IF EXISTS `Container`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Container` (
  `_Container_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentContainer_key` int(11) NOT NULL,
  `_Location_key` int(11) NOT NULL,
  `_ContainerType_key` int(11) NOT NULL,
  `_SubcontainerPosition_key` int(11) DEFAULT NULL,
  `_LocationContainerPosition_key` int(11) DEFAULT NULL,
  `ContainerName` varchar(75) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Container_key`),
  KEY `Ref114177` (`_Location_key`),
  KEY `Ref122179` (`_ContainerType_key`),
  KEY `Ref117245` (`_ParentContainer_key`),
  KEY `Ref174276` (`_SubcontainerPosition_key`),
  KEY `Ref173277` (`_LocationContainerPosition_key`),
  KEY `RefWorkgroup416` (`_Workgroup_key`),
  CONSTRAINT `RefContainer245` FOREIGN KEY (`_ParentContainer_key`) REFERENCES `Container` (`_Container_key`),
  CONSTRAINT `Refcv_ContainerType179` FOREIGN KEY (`_ContainerType_key`) REFERENCES `cv_ContainerType` (`_ContainerType_key`),
  CONSTRAINT `RefLocation177` FOREIGN KEY (`_Location_key`) REFERENCES `Location` (`_Location_key`),
  CONSTRAINT `RefLocationContainerPosition277` FOREIGN KEY (`_LocationContainerPosition_key`) REFERENCES `LocationContainerPosition` (`_LocationContainerPosition_key`),
  CONSTRAINT `RefSubcontainerPosition276` FOREIGN KEY (`_SubcontainerPosition_key`) REFERENCES `SubcontainerPosition` (`_SubcontainerPosition_key`),
  CONSTRAINT `RefWorkgroup416` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Container`
--

LOCK TABLES `Container` WRITE;
/*!40000 ALTER TABLE `Container` DISABLE KEYS */;
/*!40000 ALTER TABLE `Container` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContainerCharacteristic`
--

DROP TABLE IF EXISTS `ContainerCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContainerCharacteristic` (
  `_ContainerCharacteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_ContainerType_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateTaken` datetime DEFAULT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ContainerCharacteristic_key`),
  KEY `Ref155257` (`_CharacteristicVersion_key`),
  KEY `Ref122272` (`_ContainerType_key`),
  KEY `RefWorkgroup417` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion257` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_ContainerType272` FOREIGN KEY (`_ContainerType_key`) REFERENCES `cv_ContainerType` (`_ContainerType_key`),
  CONSTRAINT `RefWorkgroup417` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContainerCharacteristic`
--

LOCK TABLES `ContainerCharacteristic` WRITE;
/*!40000 ALTER TABLE `ContainerCharacteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContainerCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContainerCharacteristicInstance`
--

DROP TABLE IF EXISTS `ContainerCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContainerCharacteristicInstance` (
  `_ContainerCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ContainerCharacteristic_key` int(11) NOT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,0) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(4) DEFAULT NULL,
  `DateStart` datetime DEFAULT NULL,
  `DateEnd` int(11) DEFAULT NULL,
  `Comments` varchar(1000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_ContainerCharacteristicInstance_key`),
  KEY `Ref169255` (`_ContainerCharacteristic_key`),
  KEY `RefEnumerationItem300` (`_EnumerationItem_key`),
  KEY `RefWorkgroup418` (`_Workgroup_key`),
  CONSTRAINT `RefContainerCharacteristic255` FOREIGN KEY (`_ContainerCharacteristic_key`) REFERENCES `ContainerCharacteristic` (`_ContainerCharacteristic_key`),
  CONSTRAINT `RefEnumerationItem300` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefWorkgroup418` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContainerCharacteristicInstance`
--

LOCK TABLES `ContainerCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `ContainerCharacteristicInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContainerCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContainerHistory`
--

DROP TABLE IF EXISTS `ContainerHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContainerHistory` (
  `_ContainerHistory_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentContainer_key` int(11) DEFAULT NULL,
  `_Location_key` int(11) DEFAULT NULL,
  `_SubcontainerPosition_key` int(11) DEFAULT NULL,
  `_LocationContainerPosition_key` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `DateStart` datetime NOT NULL,
  `DateEnd` datetime DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ContainerHistory_key`),
  KEY `Ref114333` (`_Location_key`),
  KEY `Ref198334` (`_ParentContainer_key`),
  KEY `Ref174335` (`_SubcontainerPosition_key`),
  KEY `Ref173336` (`_LocationContainerPosition_key`),
  KEY `RefWorkgroup419` (`_Workgroup_key`),
  CONSTRAINT `RefContainerHistory334` FOREIGN KEY (`_ParentContainer_key`) REFERENCES `ContainerHistory` (`_ContainerHistory_key`),
  CONSTRAINT `RefLocation333` FOREIGN KEY (`_Location_key`) REFERENCES `Location` (`_Location_key`),
  CONSTRAINT `RefLocationContainerPosition336` FOREIGN KEY (`_LocationContainerPosition_key`) REFERENCES `LocationContainerPosition` (`_LocationContainerPosition_key`),
  CONSTRAINT `RefSubcontainerPosition335` FOREIGN KEY (`_SubcontainerPosition_key`) REFERENCES `SubcontainerPosition` (`_SubcontainerPosition_key`),
  CONSTRAINT `RefWorkgroup419` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContainerHistory`
--

LOCK TABLES `ContainerHistory` WRITE;
/*!40000 ALTER TABLE `ContainerHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContainerHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ControlledVocabulary`
--

DROP TABLE IF EXISTS `ControlledVocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ControlledVocabulary` (
  `_ControlledVocabulary_key` int(11) NOT NULL AUTO_INCREMENT,
  `TableName` varchar(50) DEFAULT NULL,
  `PKColumnName` varchar(100) DEFAULT NULL,
  `ValueColumnName` varchar(100) DEFAULT NULL,
  `DisplayName` varchar(100) DEFAULT NULL,
  `IsUserAdministered` tinyint(4) NOT NULL DEFAULT '0',
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ControlledVocabulary_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ControlledVocabulary`
--

LOCK TABLES `ControlledVocabulary` WRITE;
/*!40000 ALTER TABLE `ControlledVocabulary` DISABLE KEYS */;
-- INSERT INTO `ControlledVocabulary` VALUES (1,'cv_ProjectType','_ProjectType_key','ProjectType','Project Type',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1),(2,'cv_ProjectStatus','_ProjectStatus_key','ProjectStatus','Project Status',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1),(3,'cv_ProcedureStatus','_ProcedureStatus_key','ProcedureStatus','Procedure Status',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1),(4,'cv_LocationType','_LocationType_key','LocationType','Location Type',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1),(5,'cv_ResourceType','_ResourceType_key','ResourceType','Resource Type',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1),(6,'cv_ScheduleReason','_ScheduleReason_key','ScheduleReason','Schedule Reason',1,'dba','2011-06-13 12:24:03','dba','2011-06-13 12:24:03',1);
/*!40000 ALTER TABLE `ControlledVocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CrossSample`
--

DROP TABLE IF EXISTS `CrossSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CrossSample` (
  `_CrossSample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Cross_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_CrossSample_key`),
  KEY `Ref112` (`_Cross_key`),
  KEY `Ref4929` (`_Sample_key`),
  KEY `RefWorkgroup421` (`_Workgroup_key`),
  CONSTRAINT `RefCross_2` FOREIGN KEY (`_Cross_key`) REFERENCES `Cross_` (`_Cross_key`),
  CONSTRAINT `RefSample29` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup421` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CrossSample`
--

LOCK TABLES `CrossSample` WRITE;
/*!40000 ALTER TABLE `CrossSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `CrossSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cross_`
--

DROP TABLE IF EXISTS `Cross_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cross_` (
  `_Cross_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CrossRetirementReason_key` int(11) DEFAULT NULL,
  `_Diet_key` int(11) DEFAULT NULL,
  `_WeanDirective_key` int(11) DEFAULT NULL,
  `_Generation_key` int(11) DEFAULT NULL,
  `_CrossPurpose_key` int(11) NOT NULL,
  `_CrossType_key` int(11) NOT NULL,
  `_ProposedOffspringLine_key` int(11) DEFAULT NULL,
  `_ProposedOffspringProtocol_key` int(11) DEFAULT NULL,
  `CrossID` varchar(75) NOT NULL,
  `ProposedPenID` decimal(10,0) DEFAULT NULL,
  `DateProposedRetirement` datetime DEFAULT NULL,
  `DateCross` datetime DEFAULT NULL,
  `OtherRetirementReason` text,
  `Comments` text,
  `FirstBirthNumber` int(11) DEFAULT NULL,
  `OtherWeanDirective` varchar(18) DEFAULT NULL,
  `ProposedGenotypeOffspring` tinyint(4) DEFAULT NULL,
  `ProposedWeanTime` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Cross_key`),
  KEY `Ref47` (`_Diet_key`),
  KEY `Ref631` (`_CrossPurpose_key`),
  KEY `Ref732` (`_CrossType_key`),
  KEY `Ref1953` (`_WeanDirective_key`),
  KEY `Ref2354` (`_Generation_key`),
  KEY `Ref1855` (`_CrossRetirementReason_key`),
  KEY `Ref3557` (`_ProposedOffspringLine_key`),
  KEY `RefWorkgroup420` (`_Workgroup_key`),
  CONSTRAINT `Refcv_CrossPurpose31` FOREIGN KEY (`_CrossPurpose_key`) REFERENCES `cv_CrossPurpose` (`_CrossPurpose_key`),
  CONSTRAINT `Refcv_CrossRetirementReason55` FOREIGN KEY (`_CrossRetirementReason_key`) REFERENCES `cv_CrossRetirementReason` (`_CrossRetirementReason_key`),
  CONSTRAINT `Refcv_CrossType32` FOREIGN KEY (`_CrossType_key`) REFERENCES `cv_CrossType` (`_CrossType_key`),
  CONSTRAINT `Refcv_Diet7` FOREIGN KEY (`_Diet_key`) REFERENCES `cv_Diet` (`_Diet_key`),
  CONSTRAINT `Refcv_Generation54` FOREIGN KEY (`_Generation_key`) REFERENCES `cv_Generation` (`_Generation_key`),
  CONSTRAINT `Refcv_WeanDirective53` FOREIGN KEY (`_WeanDirective_key`) REFERENCES `cv_WeanDirective` (`_WeanDirective_key`),
  CONSTRAINT `RefLine57` FOREIGN KEY (`_ProposedOffspringLine_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefWorkgroup420` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cross_`
--

LOCK TABLES `Cross_` WRITE;
/*!40000 ALTER TABLE `Cross_` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cross_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DamInCross`
--

DROP TABLE IF EXISTS `DamInCross`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DamInCross` (
  `_DamsInCross_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Cross_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_DamChangeReason_key` int(11) DEFAULT NULL,
  `_Organism_key` int(11) NOT NULL,
  `_OnRetireBreedingStatus_key` int(11) DEFAULT NULL,
  `_OnRetireDiet_key` int(11) DEFAULT NULL,
  `DateIn` datetime DEFAULT NULL,
  `DateOut` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DamsInCross_key`),
  KEY `Ref466` (`_OnRetireDiet_key`),
  KEY `Ref3048` (`_Organism_key`),
  KEY `Ref1158` (`_Cross_key`),
  KEY `Ref4959` (`_Sample_key`),
  KEY `Ref560` (`_DamChangeReason_key`),
  KEY `Ref362` (`_OnRetireBreedingStatus_key`),
  KEY `RefWorkgroup480` (`_Workgroup_key`),
  CONSTRAINT `RefCross_58` FOREIGN KEY (`_Cross_key`) REFERENCES `Cross_` (`_Cross_key`),
  CONSTRAINT `Refcv_BreedingStatus62` FOREIGN KEY (`_OnRetireBreedingStatus_key`) REFERENCES `cv_BreedingStatus` (`_BreedingStatus_key`),
  CONSTRAINT `Refcv_DamChangeReason60` FOREIGN KEY (`_DamChangeReason_key`) REFERENCES `cv_DamChangeReason` (`_DamChangeReason_key`),
  CONSTRAINT `Refcv_Diet66` FOREIGN KEY (`_OnRetireDiet_key`) REFERENCES `cv_Diet` (`_Diet_key`),
  CONSTRAINT `RefOrganism48` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefSample59` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup480` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DamInCross`
--

LOCK TABLES `DamInCross` WRITE;
/*!40000 ALTER TABLE `DamInCross` DISABLE KEYS */;
/*!40000 ALTER TABLE `DamInCross` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFile`
--

DROP TABLE IF EXISTS `DataFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFile` (
  `_DataFile_key` int(11) NOT NULL AUTO_INCREMENT,
  `_FileExtension_key` int(11) NOT NULL,
  `Title` varchar(60) NOT NULL,
  `FileNameRegEx` varchar(256) DEFAULT NULL,
  `ColumnDelimiter` varchar(18) DEFAULT NULL,
  `HasHeader` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DataFile_key`),
  UNIQUE KEY `UniqueTitleInWorkgroup` (`Title`,`_Workgroup_key`),
  KEY `Ref248623` (`_FileExtension_key`),
  KEY `Ref181624` (`_Workgroup_key`),
  CONSTRAINT `Refcv_FileExtension6231` FOREIGN KEY (`_FileExtension_key`) REFERENCES `cv_FileExtension` (`_FileExtension_key`),
  CONSTRAINT `RefWorkgroup6241` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFile`
--

LOCK TABLES `DataFile` WRITE;
/*!40000 ALTER TABLE `DataFile` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFileComponent`
--

DROP TABLE IF EXISTS `DataFileComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFileComponent` (
  `_DataFileComponent_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataFile_key` int(11) NOT NULL,
  `_SectionType_key` int(11) NOT NULL,
  `_DataType_key` int(11) DEFAULT NULL,
  `_FieldType_key` int(11) NOT NULL,
  `Label` varchar(40) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DataFileComponent_key`) USING BTREE,
  KEY `Ref246618` (`_SectionType_key`),
  KEY `Ref244619` (`_DataFile_key`),
  KEY `Ref65620` (`_DataType_key`),
  KEY `Ref181621` (`_Workgroup_key`),
  KEY `Ref247622` (`_FieldType_key`),
  CONSTRAINT `Refcv_DataType6201` FOREIGN KEY (`_DataType_key`) REFERENCES `cv_DataType` (`_DataType_key`),
  CONSTRAINT `Refcv_FieldType6221` FOREIGN KEY (`_FieldType_key`) REFERENCES `cv_FieldType` (`_FieldType_key`),
  CONSTRAINT `Refcv_SectionType6181` FOREIGN KEY (`_SectionType_key`) REFERENCES `cv_SectionType` (`_SectionType_key`),
  CONSTRAINT `RefDataFile6191` FOREIGN KEY (`_DataFile_key`) REFERENCES `DataFile` (`_DataFile_key`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RefWorkgroup6211` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFileComponent`
--

LOCK TABLES `DataFileComponent` WRITE;
/*!40000 ALTER TABLE `DataFileComponent` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFileComponent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFileComponentInput`
--

DROP TABLE IF EXISTS `DataFileComponentInput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFileComponentInput` (
  `_DataFileComponentInput_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataFileComponent_key` int(11) NOT NULL,
  `_Input_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DataFileComponentInput_key`),
  KEY `Ref70632` (`_Input_key`),
  KEY `Ref181635` (`_Workgroup_key`),
  KEY `Ref245629` (`_DataFileComponent_key`) USING BTREE,
  CONSTRAINT `RefDataFileComponent6291` FOREIGN KEY (`_DataFileComponent_key`) REFERENCES `DataFileComponent` (`_DataFileComponent_key`),
  CONSTRAINT `RefInput6321` FOREIGN KEY (`_Input_key`) REFERENCES `Input` (`_Input_key`),
  CONSTRAINT `RefWorkgroup6351` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFileComponentInput`
--

LOCK TABLES `DataFileComponentInput` WRITE;
/*!40000 ALTER TABLE `DataFileComponentInput` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFileComponentInput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFileComponentOutput`
--

DROP TABLE IF EXISTS `DataFileComponentOutput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFileComponentOutput` (
  `_DataFileComponentOutput_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataFileComponent_key` int(11) NOT NULL,
  `_Output_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DataFileComponentOutput_key`),
  KEY `Ref64631` (`_Output_key`),
  KEY `Ref181636` (`_Workgroup_key`),
  KEY `Ref245628` (`_DataFileComponent_key`) USING BTREE,
  CONSTRAINT `RefDataFileComponent6281` FOREIGN KEY (`_DataFileComponent_key`) REFERENCES `DataFileComponent` (`_DataFileComponent_key`),
  CONSTRAINT `RefOutput6311` FOREIGN KEY (`_Output_key`) REFERENCES `Output` (`_Output_key`),
  CONSTRAINT `RefWorkgroup6361` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFileComponentOutput`
--

LOCK TABLES `DataFileComponentOutput` WRITE;
/*!40000 ALTER TABLE `DataFileComponentOutput` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFileComponentOutput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFileProcedureDefinition`
--

DROP TABLE IF EXISTS `DataFileProcedureDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFileProcedureDefinition` (
  `_DataFileProcedureDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataFile_key` int(11) NOT NULL,
  `_ProcedureDefinition_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_DataFileProcedureDefinition_key`),
  KEY `Ref244630` (`_DataFile_key`),
  KEY `Ref196633` (`_ProcedureDefinition_key`),
  KEY `Ref181634` (`_Workgroup_key`),
  CONSTRAINT `RefDataFile6301` FOREIGN KEY (`_DataFile_key`) REFERENCES `DataFile` (`_DataFile_key`),
  CONSTRAINT `RefProcedureDefinition6331` FOREIGN KEY (`_ProcedureDefinition_key`) REFERENCES `ProcedureDefinition` (`_ProcedureDefinition_key`),
  CONSTRAINT `RefWorkgroup6341` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFileProcedureDefinition`
--

LOCK TABLES `DataFileProcedureDefinition` WRITE;
/*!40000 ALTER TABLE `DataFileProcedureDefinition` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFileProcedureDefinition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataLoad`
--

DROP TABLE IF EXISTS `DataLoad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataLoad` (
  `_DataLoad_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataLoadProcess_key` int(11) NOT NULL,
  `FileName` varchar(250) DEFAULT NULL,
  `StartDateTime` datetime DEFAULT NULL,
  `EndDateTime` datetime DEFAULT NULL,
  `LoadSuccess` tinyint(4) DEFAULT NULL,
  `Message` varchar(250) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_DataLoad_key`),
  KEY `RefDataLoadProcess372` (`_DataLoadProcess_key`),
  KEY `RefWorkgroup481` (`_Workgroup_key`),
  CONSTRAINT `RefDataLoadProcess372` FOREIGN KEY (`_DataLoadProcess_key`) REFERENCES `DataLoadProcess` (`_DataLoadProcess_key`),
  CONSTRAINT `RefWorkgroup481` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataLoad`
--

LOCK TABLES `DataLoad` WRITE;
/*!40000 ALTER TABLE `DataLoad` DISABLE KEYS */;
-- INSERT INTO `DataLoad` VALUES (1,1,'','2010-04-26 11:26:05','2010-04-26 11:27:02',1,'','MBTC','2010-04-26 11:27:03','MBTC','2010-04-26 11:27:03',0,1);
/*!40000 ALTER TABLE `DataLoad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataLoadProcess`
--

DROP TABLE IF EXISTS `DataLoadProcess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataLoadProcess` (
  `_DataLoadProcess_key` int(11) NOT NULL AUTO_INCREMENT,
  `DataLoadProcess` varchar(75) NOT NULL,
  `LaunchInterval` int(11) DEFAULT NULL,
  `_TimeUnit_key` int(11) DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_DataLoadProcess_key`),
  UNIQUE KEY `DataLoadProcess` (`DataLoadProcess`),
  KEY `RefTimeUnit372` (`_TimeUnit_key`),
  KEY `RefWorkgroup482` (`_Workgroup_key`),
  CONSTRAINT `RefTimeUnit372` FOREIGN KEY (`_TimeUnit_key`) REFERENCES `cv_TimeUnit` (`_TimeUnit_key`),
  CONSTRAINT `RefWorkgroup482` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataLoadProcess`
--

LOCK TABLES `DataLoadProcess` WRITE;
/*!40000 ALTER TABLE `DataLoadProcess` DISABLE KEYS */;
-- INSERT INTO `DataLoadProcess` VALUES (1,'Import_Strains',1440,1,'','MBTC','2010-04-26 11:26:05','MBTC','2010-04-26 11:26:05',0,1),(2,'Refresh_ScheduleAlerts',1,1,'','MBTC','2010-04-26 11:26:05','MBTC','2010-04-26 11:26:05',0,1);
/*!40000 ALTER TABLE `DataLoadProcess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `_Department_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Institution_key` int(11) NOT NULL,
  `DepartmentName` varchar(75) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Department_key`),
  KEY `Ref160339` (`_Institution_key`),
  KEY `RefWorkgroup484` (`_Workgroup_key`),
  CONSTRAINT `RefInstitution339` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`),
  CONSTRAINT `RefWorkgroup484` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnumerationClass`
--

DROP TABLE IF EXISTS `EnumerationClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnumerationClass` (
  `_EnumerationClass_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataType_key` int(11) NOT NULL,
  `EnumerationClass` varchar(50) NOT NULL,
  `Description` varchar(80) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_EnumerationClass_key`),
  KEY `Ref65140` (`_DataType_key`),
  KEY `RefWorkgroup485` (`_Workgroup_key`),
  CONSTRAINT `Refcv_DataType140` FOREIGN KEY (`_DataType_key`) REFERENCES `cv_DataType` (`_DataType_key`),
  CONSTRAINT `RefWorkgroup485` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnumerationClass`
--

LOCK TABLES `EnumerationClass` WRITE;
/*!40000 ALTER TABLE `EnumerationClass` DISABLE KEYS */;
-- INSERT INTO `EnumerationClass` VALUES (1,2,'Coat Color','A list of standard mouse coat colors',1,1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(2,2,'Sex','A list of genders',1,1,1,'dba','2011-02-02 11:23:41','dba','2011-02-02 11:23:41',1),(3,2,'Genotype','A list of all possible genotype values',1,1,1,'dba','2011-02-02 11:23:41','dba','2011-02-02 11:23:41',1);
/*!40000 ALTER TABLE `EnumerationClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnumerationClassItem`
--

DROP TABLE IF EXISTS `EnumerationClassItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnumerationClassItem` (
  `_enumerationClassItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_EnumerationItem_key` int(11) NOT NULL,
  `_EnumerationClass_key` int(11) NOT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_enumerationClassItem_key`),
  KEY `Ref77101` (`_EnumerationItem_key`),
  KEY `Ref78102` (`_EnumerationClass_key`),
  KEY `RefWorkgroup486` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationClass102` FOREIGN KEY (`_EnumerationClass_key`) REFERENCES `EnumerationClass` (`_EnumerationClass_key`),
  CONSTRAINT `RefEnumerationItem101` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefWorkgroup486` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnumerationClassItem`
--

LOCK TABLES `EnumerationClassItem` WRITE;
/*!40000 ALTER TABLE `EnumerationClassItem` DISABLE KEYS */;
-- INSERT INTO `EnumerationClassItem` VALUES (1,1,1,1,1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(2,2,1,1,2,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(3,3,1,1,3,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(4,4,1,1,4,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(5,5,1,1,5,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1);
/*!40000 ALTER TABLE `EnumerationClassItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnumerationItem`
--

DROP TABLE IF EXISTS `EnumerationItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnumerationItem` (
  `_EnumerationItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentEnumerationItem_key` int(11) DEFAULT NULL,
  `EnumerationItem` varchar(128) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `Description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`_EnumerationItem_key`),
  KEY `Ref77100` (`_ParentEnumerationItem_key`),
  KEY `RefWorkgroup487` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem100` FOREIGN KEY (`_ParentEnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefWorkgroup487` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnumerationItem`
--

LOCK TABLES `EnumerationItem` WRITE;
/*!40000 ALTER TABLE `EnumerationItem` DISABLE KEYS */;
-- INSERT INTO `EnumerationItem` VALUES (1,NULL,'Black',1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL),(2,NULL,'Albino',1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL),(3,NULL,'Agouti',1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL),(4,NULL,'Dilute brown',1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL),(5,NULL,'Chinchilla',1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL);
/*!40000 ALTER TABLE `EnumerationItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnumerationItemDetail`
--

DROP TABLE IF EXISTS `EnumerationItemDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnumerationItemDetail` (
  `_EnumerationItemDetails_key` int(11) NOT NULL AUTO_INCREMENT,
  `_EnumerationItem_key` int(11) NOT NULL,
  `ItemDetail` int(11) NOT NULL,
  `ItemDetailValue` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_EnumerationItemDetails_key`),
  KEY `Ref77103` (`_EnumerationItem_key`),
  KEY `RefWorkgroup488` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem103` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefWorkgroup488` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnumerationItemDetail`
--

LOCK TABLES `EnumerationItemDetail` WRITE;
/*!40000 ALTER TABLE `EnumerationItemDetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `EnumerationItemDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FunctionalArea`
--

DROP TABLE IF EXISTS `FunctionalArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FunctionalArea` (
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

--
-- Dumping data for table `FunctionalArea`
--

LOCK TABLES `FunctionalArea` WRITE;
/*!40000 ALTER TABLE `FunctionalArea` DISABLE KEYS */;
INSERT INTO `FunctionalArea` VALUES 
(1,'Administration',NULL,'dba','2010-10-25 16:41:29','dba','2010-10-25 16:41:29',1),
(2,'Querying',NULL,'dba','2010-10-25 16:41:29','dba','2010-10-25 16:41:29',1),
(3,'Reporting',NULL,'dba','2010-10-25 16:41:29','dba','2010-10-25 16:41:29',1);
/*!40000 ALTER TABLE `FunctionalArea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Genotype`
--

DROP TABLE IF EXISTS `Genotype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Genotype` (
  `_Genotype_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Organism_key` int(11) NOT NULL,
  `_GenotypeStatus_key` int(11) NOT NULL,
  `_GenotypeSource_key` int(11) DEFAULT NULL,
  `NotebookPage` int(11) DEFAULT NULL,
  `DateGenotype` datetime DEFAULT NULL,
  `Comments` varchar(1000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `GenotypeDescription` varchar(256) DEFAULT NULL,
  `RepeatLength` varchar(18) DEFAULT NULL,
  `_GenotypeSymbol_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_Genotype_key`),
  KEY `Ref5585` (`_GenotypeStatus_key`),
  KEY `Ref5787` (`_GenotypeSource_key`),
  KEY `Ref3088` (`_Organism_key`),
  KEY `GenotypeSymbol386` (`_GenotypeSymbol_key`),
  KEY `RefWorkgroup489` (`_Workgroup_key`),
  CONSTRAINT `GenotypeSymbol386` FOREIGN KEY (`_GenotypeSymbol_key`) REFERENCES `cv_GenotypeSymbol` (`_GenotypeSymbol_key`),
  CONSTRAINT `Refcv_GenotypeSource87` FOREIGN KEY (`_GenotypeSource_key`) REFERENCES `cv_GenotypeSource` (`_GenotypeSource_key`),
  CONSTRAINT `Refcv_GenotypeStatus85` FOREIGN KEY (`_GenotypeStatus_key`) REFERENCES `cv_GenotypeStatus` (`_GenotypeStatus_key`),
  CONSTRAINT `RefOrganism88` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefWorkgroup489` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Genotype`
--

LOCK TABLES `Genotype` WRITE;
/*!40000 ALTER TABLE `Genotype` DISABLE KEYS */;
/*!40000 ALTER TABLE `Genotype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GenotypeAllele`
--

DROP TABLE IF EXISTS `GenotypeAllele`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GenotypeAllele` (
  `_GenotypeAllele_key` int(11) NOT NULL,
  `_Genotype_key` int(11) NOT NULL,
  `_Allele_key` int(11) NOT NULL,
  `Confidence` tinyint(4) NOT NULL,
  `CopyNumber` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_GenotypeAllele_key`),
  KEY `RefGenotype384` (`_Genotype_key`),
  KEY `RefAllele385` (`_Allele_key`),
  KEY `RefWorkgroup490` (`_Workgroup_key`),
  CONSTRAINT `RefAllele385` FOREIGN KEY (`_Allele_key`) REFERENCES `Allele` (`_Allele_key`),
  CONSTRAINT `RefGenotype384` FOREIGN KEY (`_Genotype_key`) REFERENCES `Genotype` (`_Genotype_key`),
  CONSTRAINT `RefWorkgroup490` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GenotypeAllele`
--

LOCK TABLES `GenotypeAllele` WRITE;
/*!40000 ALTER TABLE `GenotypeAllele` DISABLE KEYS */;
/*!40000 ALTER TABLE `GenotypeAllele` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Input`
--

DROP TABLE IF EXISTS `Input`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Input` (
  `_Input_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataType_key` int(11) NOT NULL,
  `_InputOutputUnit_key` int(11) DEFAULT NULL,
  `_EnumerationClass_key` int(11) DEFAULT NULL,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `InputName` varchar(100) NOT NULL,
  `Description` varchar(128) DEFAULT NULL,
  `IsStandardized` tinyint(4) DEFAULT NULL,
  `IsMultiSelectEnumeration` tinyint(4) NOT NULL DEFAULT '0',
  `NumericValidationMin` decimal(38,0) DEFAULT NULL,
  `NumericValidationMax` decimal(38,0) DEFAULT NULL,
  `DateValidationMin` date DEFAULT NULL,
  `DateValidationMax` date DEFAULT NULL,
  `TimeValidationMin` time DEFAULT NULL,
  `TimeValidationMax` time DEFAULT NULL,
  `TextValidationRegex` varchar(256) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `IsRequired` tinyint(4) DEFAULT NULL,
  `IsScheduleDependent` tinyint(4) DEFAULT NULL,
  `Abbreviation` varchar(100) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Input_key`),
  KEY `Ref6595` (`_DataType_key`),
  KEY `Ref6796` (`_InputOutputUnit_key`),
  KEY `Ref78139` (`_EnumerationClass_key`),
  KEY `Ref90149` (`_ProcedureDefinitionVersion_key`),
  KEY `RefWorkgroup491` (`_Workgroup_key`),
  CONSTRAINT `Refcv_DataType95` FOREIGN KEY (`_DataType_key`) REFERENCES `cv_DataType` (`_DataType_key`),
  CONSTRAINT `RefEnumerationClass139` FOREIGN KEY (`_EnumerationClass_key`) REFERENCES `EnumerationClass` (`_EnumerationClass_key`),
  CONSTRAINT `RefInputOutputUnit96` FOREIGN KEY (`_InputOutputUnit_key`) REFERENCES `InputOutputUnit` (`_InputOutputUnit_key`),
  CONSTRAINT `RefProcedureDefinitionVersion149` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup491` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Input`
--

LOCK TABLES `Input` WRITE;
/*!40000 ALTER TABLE `Input` DISABLE KEYS */;
-- INSERT INTO `Input` VALUES (8,1,9,NULL,9,'664-qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',1),(9,2,NULL,NULL,9,'664-sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',1),(10,1,NULL,NULL,9,'664-age',NULL,0,0,'3','3',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',1),(11,2,NULL,NULL,9,'664 -genotype',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',1),(12,1,NULL,NULL,10,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(13,2,NULL,NULL,10,'Sex',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(14,1,NULL,NULL,10,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(15,1,NULL,NULL,10,'Age',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(16,2,NULL,NULL,10,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(17,1,NULL,NULL,11,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(18,1,NULL,NULL,11,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(19,1,NULL,NULL,11,'dob',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(20,2,NULL,NULL,11,'Genotype',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(21,2,NULL,1,11,'Coat Color',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(22,1,NULL,NULL,12,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',0),(23,1,NULL,NULL,12,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',0),(24,1,NULL,NULL,13,'Tank',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(25,1,NULL,NULL,13,'Box',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(26,1,NULL,NULL,13,'Cassette',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(27,1,NULL,NULL,13,'Straw#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(28,1,NULL,NULL,14,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(29,2,NULL,NULL,14,'Sex',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(30,1,NULL,NULL,14,'Male id#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(31,2,NULL,NULL,14,'Coat Color',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(32,3,NULL,NULL,14,'DOB',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(33,1,NULL,NULL,15,'stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(34,1,NULL,NULL,15,'Qty of straws',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(35,1,NULL,NULL,16,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:02:16','sbean','2011-02-03 13:02:16',0),(37,3,NULL,NULL,18,'IVF Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',0),(38,1,NULL,NULL,19,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',0),(39,1,NULL,NULL,19,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',0),(40,1,NULL,NULL,20,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(41,2,NULL,NULL,20,'Sex',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(42,1,NULL,NULL,20,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(43,2,NULL,NULL,20,'Coat Color',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(44,2,NULL,NULL,20,'Genotype',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(45,3,NULL,NULL,20,'DOB',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(46,1,NULL,NULL,20,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(47,1,NULL,NULL,21,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(48,1,NULL,NULL,21,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(49,1,NULL,NULL,22,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(50,1,NULL,NULL,22,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(51,1,NULL,NULL,23,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(52,1,NULL,NULL,23,'Dish#1',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(53,1,NULL,NULL,25,'# of Pseudos',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(54,1,NULL,NULL,25,'# of embryo tranfers',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(55,1,NULL,NULL,26,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(56,1,NULL,NULL,26,'Psuedo #',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(57,3,NULL,NULL,26,'Embryo Transfer Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(58,1,NULL,NULL,27,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(59,1,NULL,NULL,27,'# of dishes',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(60,1,NULL,NULL,28,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(61,1,NULL,NULL,28,'Straw or Vial#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(62,1,NULL,NULL,28,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(63,1,NULL,NULL,29,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(64,1,NULL,NULL,29,'Pseudo #',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(65,1,NULL,NULL,29,'# of pups',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(66,1,NULL,NULL,30,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(67,1,NULL,NULL,30,'# of females',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(68,1,NULL,NULL,30,'# of males',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(69,1,NULL,NULL,30,'Wean Date',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(70,1,NULL,NULL,31,'Pseudo #',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',0),(71,1,NULL,NULL,32,'Pseudo #',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(72,3,NULL,NULL,32,'Date Sent',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(73,6,2,1,33,'Type of Sample',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(74,2,NULL,NULL,33,'Container Type',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(75,2,NULL,NULL,33,'Container Layout',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(76,3,NULL,NULL,33,'Date Taken',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(77,6,NULL,1,34,'Type of Id',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(78,2,NULL,NULL,34,'Ear Id#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(79,6,NULL,2,34,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(80,3,NULL,NULL,34,'DOB',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(81,2,NULL,NULL,35,'Shipment Type',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(82,2,NULL,NULL,36,'Plate#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(83,2,NULL,NULL,36,'Stock#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(84,2,NULL,NULL,36,'Ear Id#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(85,6,NULL,3,36,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(86,3,NULL,NULL,37,'Shipment Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',0),(87,3,NULL,NULL,42,'Estimated Ship Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(88,1,NULL,NULL,48,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(89,2,NULL,2,48,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(90,2,NULL,1,48,'Coat Color',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(91,2,NULL,3,48,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(92,3,NULL,NULL,48,'DOB',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(93,2,NULL,NULL,48,'Box#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(94,2,NULL,NULL,48,'Side',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(95,2,NULL,NULL,51,'Gene name',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',0),(96,2,NULL,NULL,52,'construct design file',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',0),(97,2,NULL,NULL,53,'Gene Name',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,NULL,11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',0),(98,1,2,NULL,66,'Number of cells',NULL,0,0,'0','10000000',NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',1);
/*!40000 ALTER TABLE `Input` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InputDefault`
--

DROP TABLE IF EXISTS `InputDefault`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InputDefault` (
  `_InputDefault_key` int(11) NOT NULL AUTO_INCREMENT,
  `_MethodProcedureDefinition_key` int(11) NOT NULL,
  `_Input_key` int(11) NOT NULL,
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  `_Parameter_key` int(11) NOT NULL,
  `NumericValue` decimal(18,0) NOT NULL,
  `TextValue` int(11) NOT NULL,
  `DateValue` date NOT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_InputDefault_key`),
  KEY `Ref70142` (`_Input_key`),
  KEY `Ref92143` (`_MethodProcedureDefinition_key`),
  KEY `Ref77146` (`_EnumerationItem_key`),
  KEY `RefWorkgroup492` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem146` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefInput142` FOREIGN KEY (`_Input_key`) REFERENCES `Input` (`_Input_key`),
  CONSTRAINT `RefMethodProcedureDefinition143` FOREIGN KEY (`_MethodProcedureDefinition_key`) REFERENCES `MethodProcedureDefinition` (`_MethodProcedureDefinition_key`),
  CONSTRAINT `RefWorkgroup492` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InputDefault`
--

LOCK TABLES `InputDefault` WRITE;
/*!40000 ALTER TABLE `InputDefault` DISABLE KEYS */;
/*!40000 ALTER TABLE `InputDefault` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InputInstance`
--

DROP TABLE IF EXISTS `InputInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InputInstance` (
  `_InputInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Input_key` int(11) NOT NULL,
  `_ProcedureInstance_key` int(11) NOT NULL,
  `NumericValue` decimal(18,0) DEFAULT NULL,
  `TextValue` varchar(1000) DEFAULT NULL,
  `DateTimeValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_InputInstance_key`),
  KEY `Ref7097` (`_Input_key`),
  KEY `Ref93159` (`_ProcedureInstance_key`),
  KEY `RefWorkgroup493` (`_Workgroup_key`),
  CONSTRAINT `RefInput97` FOREIGN KEY (`_Input_key`) REFERENCES `Input` (`_Input_key`),
  CONSTRAINT `RefProcedureInstance159` FOREIGN KEY (`_ProcedureInstance_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup493` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InputInstance`
--

LOCK TABLES `InputInstance` WRITE;
/*!40000 ALTER TABLE `InputInstance` DISABLE KEYS */;
-- INSERT INTO `InputInstance` VALUES (1,82,1,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:16:03','amiller','2011-04-06 08:46:21',0),(2,83,1,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:16:03','amiller','2011-04-06 08:46:21',0),(3,84,1,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:16:03','amiller','2011-04-06 08:46:21',0),(4,85,1,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:16:03','sbean','2011-02-21 14:16:03',0),(5,63,2,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(6,64,2,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(7,65,2,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(8,66,3,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(9,67,3,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(10,68,3,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(11,69,3,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(12,70,4,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(13,71,5,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(14,72,5,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:15:45','amiller','2011-04-06 08:46:21',0),(15,77,6,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','sbean','2011-02-21 14:13:50',0),(16,78,6,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','amiller','2011-04-06 08:46:21',0),(17,79,6,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','sbean','2011-02-21 14:13:50',0),(18,80,6,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','amiller','2011-04-06 08:46:21',0),(19,73,7,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','sbean','2011-02-21 14:13:50',0),(20,74,7,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','amiller','2011-04-06 08:46:21',0),(21,75,7,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:50','amiller','2011-04-06 08:46:21',0),(24,53,10,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:13:15','amiller','2011-04-06 08:46:21',0),(37,44,14,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(38,45,14,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(39,46,14,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(40,47,15,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(41,48,15,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(42,49,16,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(43,50,16,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(44,51,17,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(45,52,17,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:58','amiller','2011-04-06 08:46:21',0),(46,22,18,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(47,23,18,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(48,24,19,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(49,25,19,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(50,26,19,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(51,27,19,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(52,28,20,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(53,29,20,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(54,30,20,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(55,31,20,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(56,32,20,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(57,33,21,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(58,34,21,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:12:38','amiller','2011-04-06 08:46:21',0),(59,87,24,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:16:26','amiller','2011-04-06 08:46:21',0),(69,77,37,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(70,78,37,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(71,79,37,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(72,80,37,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(73,73,38,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(74,74,38,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(75,75,38,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(76,76,38,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:02','sbean','2011-02-21 14:35:02',0),(77,81,39,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:01','sbean','2011-02-21 14:35:01',0),(78,22,40,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(79,23,40,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(80,24,41,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(81,25,41,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(82,26,41,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(83,27,41,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(84,28,42,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(85,29,42,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(86,30,42,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(87,31,42,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(88,32,42,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(89,33,43,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(90,34,43,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(91,35,44,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0),(92,38,46,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:58','sbean','2011-02-21 14:33:58',0),(93,39,46,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:58','sbean','2011-02-21 14:33:58',0),(94,40,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(95,41,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(96,42,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(97,43,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(98,44,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(99,45,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(100,46,47,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(101,47,48,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(102,48,48,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(103,49,49,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(104,50,49,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(105,51,50,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(106,52,50,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0),(107,53,52,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(108,54,52,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(109,55,53,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(110,56,53,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(111,57,53,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(112,58,54,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(113,59,54,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0),(114,63,55,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(115,64,55,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(116,65,55,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(117,66,56,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(118,67,56,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(119,68,56,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(120,69,56,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(121,70,57,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:20','sbean','2011-02-21 14:33:20',0),(122,71,58,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:19','sbean','2011-02-21 14:33:19',0),(123,72,58,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:19','sbean','2011-02-21 14:33:19',0),(124,77,59,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(125,78,59,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(126,79,59,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(127,80,59,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(128,73,60,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(129,74,60,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(130,75,60,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(131,76,60,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(132,81,61,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0),(133,82,62,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:50','sbean','2011-02-21 14:32:50',0),(134,83,62,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:50','sbean','2011-02-21 14:32:50',0),(135,84,62,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:50','sbean','2011-02-21 14:32:50',0),(136,85,62,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:50','sbean','2011-02-21 14:32:50',0),(137,87,63,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:22','sbean','2011-02-21 14:32:22',0),(138,86,64,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:32:22','sbean','2011-02-21 14:32:22',0),(139,88,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(140,89,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(141,90,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(142,91,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(143,92,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(144,93,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(145,94,74,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0),(146,88,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(147,89,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(148,90,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(149,91,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(150,92,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(151,93,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(152,94,77,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0),(153,60,79,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:14',1),(154,61,79,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:14',1),(155,62,79,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(156,53,81,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(157,54,81,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(158,55,82,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(159,56,82,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(160,57,82,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(161,58,83,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(162,59,83,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:10:15',1),(163,95,84,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:10:30','TODO','2011-03-31 15:10:30',1),(164,96,85,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:10:30','TODO','2011-03-31 15:10:30',1),(165,97,86,NULL,'BAX',NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:28','douglash','2011-04-05 18:24:28',0),(166,97,99,NULL,'',NULL,NULL,NULL,11,'douglash','2011-04-05 18:27:36','douglash','2011-04-05 18:27:36',0),(167,17,101,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',1),(168,18,101,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',1),(169,19,101,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',1),(170,20,101,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',1),(171,21,101,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',1),(173,8,119,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',1),(174,9,119,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',1),(175,10,119,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',1),(176,11,119,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',1),(177,98,120,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:37','michaelm','2011-04-06 13:04:06',1);
/*!40000 ALTER TABLE `InputInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InputInstanceEnumerationItem`
--

DROP TABLE IF EXISTS `InputInstanceEnumerationItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InputInstanceEnumerationItem` (
  `_InputInstanceEnumerationItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_InputInstance_key` int(11) NOT NULL,
  `_EnumerationItem_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_InputInstanceEnumerationItem_key`),
  KEY `RefEnumerationItem2` (`_EnumerationItem_key`),
  KEY `RefInputInstanceWk` (`_Workgroup_key`),
  KEY `RefInputInstance4` (`_InputInstance_key`),
  CONSTRAINT `RefEnumerationItem2` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefInputInstance4` FOREIGN KEY (`_InputInstance_key`) REFERENCES `InputInstance` (`_InputInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefInputInstanceWk` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InputInstanceEnumerationItem`
--

LOCK TABLES `InputInstanceEnumerationItem` WRITE;
/*!40000 ALTER TABLE `InputInstanceEnumerationItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `InputInstanceEnumerationItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InputOutputUnit`
--

DROP TABLE IF EXISTS `InputOutputUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InputOutputUnit` (
  `_InputOutputUnit_key` int(11) NOT NULL AUTO_INCREMENT,
  `InputOutputUnit` varchar(20) NOT NULL,
  `Description` varchar(32) NOT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `SortOrder` int(11) NOT NULL DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_InputOutputUnit_key`),
  KEY `RefWorkgroup494` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup494` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InputOutputUnit`
--

LOCK TABLES `InputOutputUnit` WRITE;
/*!40000 ALTER TABLE `InputOutputUnit` DISABLE KEYS */;
-- INSERT INTO `InputOutputUnit` VALUES (1,'gm','grams',1,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(2,'mL','mililiters',1,2,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(3,'mg/Kg','milligrams/kilograms',1,3,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(4,'mm3','cubic milimeters',1,4,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(5,'s','seconds',1,5,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(6,'ms','miliseconds',1,6,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(7,'mol','mole',1,7,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(8,'%','percent',1,8,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(9,'oz','ounce',1,9,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(10,'gm','grams',1,10,2,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(11,'gm','grams',1,11,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(12,'gm','grams',1,12,4,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(13,'gm','grams',1,13,5,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(14,'gm','grams',1,14,6,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(15,'gm','grams',1,15,7,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(16,'gm','grams',1,16,8,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(17,'gm','grams',1,17,9,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1),(18,'mg/kg','milligrams/kilograms',1,10,3,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1);
/*!40000 ALTER TABLE `InputOutputUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inquiry`
--

DROP TABLE IF EXISTS `Inquiry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Inquiry` (
  `_Inquiry_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ContactPerson_key` int(11) NOT NULL,
  `Contact_key` int(11) DEFAULT NULL,
  `Customer_key` int(11) DEFAULT NULL,
  `InquiryDate` datetime NOT NULL,
  `Description` varchar(5000) DEFAULT NULL,
  `CostEstimateRequested` tinyint(4) DEFAULT NULL,
  `QuoteRequested` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Inquiry_key`),
  KEY `Ref161235` (`_ContactPerson_key`),
  KEY `RefWorkgroup495` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson235` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `RefWorkgroup495` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inquiry`
--

LOCK TABLES `Inquiry` WRITE;
/*!40000 ALTER TABLE `Inquiry` DISABLE KEYS */;
/*!40000 ALTER TABLE `Inquiry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Institution`
--

DROP TABLE IF EXISTS `Institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Institution` (
  `_Institution_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Country_key` int(11) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Fax` varchar(20) DEFAULT NULL,
  `Address` varchar(250) DEFAULT NULL,
  `City` varchar(250) DEFAULT NULL,
  `State` varchar(10) DEFAULT NULL,
  `PostalCode` varchar(10) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `ExternalID` varchar(11) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_Institution_key`),
  KEY `Ref204352` (`_Country_key`),
  KEY `RefWorkgroup496` (`_Workgroup_key`),
  KEY `Refcv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_Country352` FOREIGN KEY (`_Country_key`) REFERENCES `cv_Country` (`_Country_key`),
  CONSTRAINT `Refcv_VocabularySource` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup496` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Institution`
--

LOCK TABLES `Institution` WRITE;
/*!40000 ALTER TABLE `Institution` DISABLE KEYS */;
/*!40000 ALTER TABLE `Institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KeywordIndexMethod`
--

DROP TABLE IF EXISTS `KeywordIndexMethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KeywordIndexMethod` (
  `_KeywordIndexMethod_key` int(11) NOT NULL AUTO_INCREMENT,
  `_KeywordIndex_key` int(11) NOT NULL,
  `_MethodVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_KeywordIndexMethod_key`),
  KEY `Ref94166` (`_MethodVersion_key`),
  KEY `Ref112162` (`_KeywordIndex_key`),
  KEY `RefWorkgroup497` (`_Workgroup_key`),
  CONSTRAINT `Refcv_KeywordIndex162` FOREIGN KEY (`_KeywordIndex_key`) REFERENCES `cv_KeywordIndex` (`_KeywordIndex_key`),
  CONSTRAINT `RefMethodVersion166` FOREIGN KEY (`_MethodVersion_key`) REFERENCES `MethodVersion` (`_MethodVersion_key`),
  CONSTRAINT `RefWorkgroup497` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KeywordIndexMethod`
--

LOCK TABLES `KeywordIndexMethod` WRITE;
/*!40000 ALTER TABLE `KeywordIndexMethod` DISABLE KEYS */;
/*!40000 ALTER TABLE `KeywordIndexMethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KeywordIndexProcedure`
--

DROP TABLE IF EXISTS `KeywordIndexProcedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KeywordIndexProcedure` (
  `_KeywordIndexProcedure_key` int(11) NOT NULL AUTO_INCREMENT,
  `_KeywordIndex_key` int(11) NOT NULL,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_KeywordIndexProcedure_key`),
  KEY `Ref90165` (`_ProcedureDefinitionVersion_key`),
  KEY `Ref112161` (`_KeywordIndex_key`),
  KEY `RefWorkgroup498` (`_Workgroup_key`),
  CONSTRAINT `Refcv_KeywordIndex161` FOREIGN KEY (`_KeywordIndex_key`) REFERENCES `cv_KeywordIndex` (`_KeywordIndex_key`),
  CONSTRAINT `RefProcedureDefinitionVersion165` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`),
  CONSTRAINT `RefWorkgroup498` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KeywordIndexProcedure`
--

LOCK TABLES `KeywordIndexProcedure` WRITE;
/*!40000 ALTER TABLE `KeywordIndexProcedure` DISABLE KEYS */;
/*!40000 ALTER TABLE `KeywordIndexProcedure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KeywordIndexStudy`
--

DROP TABLE IF EXISTS `KeywordIndexStudy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KeywordIndexStudy` (
  `_KeywordIndexStudy_key` int(11) NOT NULL,
  `_Study_key` int(11) NOT NULL,
  `_KeywordIndex_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_KeywordIndexStudy_key`),
  KEY `Ref47167` (`_Study_key`),
  KEY `Ref112163` (`_KeywordIndex_key`),
  KEY `RefWorkgroup499` (`_Workgroup_key`),
  CONSTRAINT `Refcv_KeywordIndex163` FOREIGN KEY (`_KeywordIndex_key`) REFERENCES `cv_KeywordIndex` (`_KeywordIndex_key`),
  CONSTRAINT `RefStudy167` FOREIGN KEY (`_Study_key`) REFERENCES `Study` (`_Study_key`),
  CONSTRAINT `RefWorkgroup499` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KeywordIndexStudy`
--

LOCK TABLES `KeywordIndexStudy` WRITE;
/*!40000 ALTER TABLE `KeywordIndexStudy` DISABLE KEYS */;
/*!40000 ALTER TABLE `KeywordIndexStudy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LimitDefinition`
--

DROP TABLE IF EXISTS `LimitDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LimitDefinition` (
  `_LimitDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LimitType_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LimitDefinition_key`),
  KEY `Ref75150` (`_LimitType_key`),
  KEY `RefWorkgroup500` (`_Workgroup_key`),
  CONSTRAINT `Refcv_LimitType150` FOREIGN KEY (`_LimitType_key`) REFERENCES `cv_LimitType` (`_LimitType_key`),
  CONSTRAINT `RefWorkgroup500` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LimitDefinition`
--

LOCK TABLES `LimitDefinition` WRITE;
/*!40000 ALTER TABLE `LimitDefinition` DISABLE KEYS */;
/*!40000 ALTER TABLE `LimitDefinition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Line`
--

DROP TABLE IF EXISTS `Line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Line` (
  `_Line_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LineType_key` int(11) NOT NULL,
  `_LineStatus_key` int(11) NOT NULL,
  `_Taxon_key` int(11) NOT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `SortOrder` int(11) DEFAULT NULL,
  `StockNumber` varchar(18) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Line_key`),
  UNIQUE KEY `StockNumber` (`StockNumber`,`ModifiedBy`) USING BTREE,
  KEY `Ref1471` (`_LineType_key`),
  KEY `Ref1272` (`_LineStatus_key`),
  KEY `Ref13311` (`_Taxon_key`),
  KEY `RefWorkgroup501` (`_Workgroup_key`),
  KEY `RefLine_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_LineStatus72` FOREIGN KEY (`_LineStatus_key`) REFERENCES `cv_LineStatus` (`_LineStatus_key`),
  CONSTRAINT `Refcv_LineType71` FOREIGN KEY (`_LineType_key`) REFERENCES `cv_LineType` (`_LineType_key`),
  CONSTRAINT `Refcv_Taxon311` FOREIGN KEY (`_Taxon_key`) REFERENCES `cv_Taxon` (`_Taxon_key`),
  CONSTRAINT `RefLine_VocabularySource` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup501` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Line`
--

LOCK TABLES `Line` WRITE;
/*!40000 ALTER TABLE `Line` DISABLE KEYS */;
/*!40000 ALTER TABLE `Line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LineAllele`
--

DROP TABLE IF EXISTS `LineAllele`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LineAllele` (
  `_LineAllele_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Line_key` int(11) NOT NULL,
  `_Allele_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LineAllele_key`),
  KEY `RefAllele1` (`_Allele_key`),
  KEY `RefWorkgroup502` (`_Workgroup_key`),
  CONSTRAINT `RefAllele1` FOREIGN KEY (`_Allele_key`) REFERENCES `Allele` (`_Allele_key`),
  CONSTRAINT `RefWorkgroup502` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LineAllele`
--

LOCK TABLES `LineAllele` WRITE;
/*!40000 ALTER TABLE `LineAllele` DISABLE KEYS */;
/*!40000 ALTER TABLE `LineAllele` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LineCharacteristic`
--

DROP TABLE IF EXISTS `LineCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LineCharacteristic` (
  `_LineCharacteristics_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LineType_key` int(11) NOT NULL,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LineCharacteristics_key`),
  KEY `Ref14305` (`_LineType_key`),
  KEY `Ref155306` (`_CharacteristicVersion_key`),
  KEY `RefWorkgroup503` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion306` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_LineType305` FOREIGN KEY (`_LineType_key`) REFERENCES `cv_LineType` (`_LineType_key`),
  CONSTRAINT `RefWorkgroup503` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LineCharacteristic`
--

LOCK TABLES `LineCharacteristic` WRITE;
/*!40000 ALTER TABLE `LineCharacteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `LineCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LineCharacteristicInstance`
--

DROP TABLE IF EXISTS `LineCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LineCharacteristicInstance` (
  `_LineCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LineCharacteristics_key` int(11) NOT NULL,
  `_Line_key` int(11) NOT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,10) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(1) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_LineCharacteristicInstance_key`),
  KEY `Ref191307` (`_LineCharacteristics_key`),
  KEY `Ref35310` (`_Line_key`),
  KEY `RefEnumerationItem301` (`_EnumerationItem_key`),
  KEY `RefWorkgroup999` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem301` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefLine310` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefLineCharacteristic307` FOREIGN KEY (`_LineCharacteristics_key`) REFERENCES `LineCharacteristic` (`_LineCharacteristics_key`),
  CONSTRAINT `RefWorkgroup999` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LineCharacteristicInstance`
--

LOCK TABLES `LineCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `LineCharacteristicInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `LineCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LineMarker`
--

DROP TABLE IF EXISTS `LineMarker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LineMarker` (
  `_LineMarker_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Line_key` int(11) NOT NULL,
  `_Marker_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LineMarker_key`),
  KEY `RefMarker5` (`_Marker_key`),
  KEY `RefLine394` (`_Workgroup_key`),
  CONSTRAINT `RefLine394` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `RefMarker5` FOREIGN KEY (`_Marker_key`) REFERENCES `Marker` (`_Marker_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LineMarker`
--

LOCK TABLES `LineMarker` WRITE;
/*!40000 ALTER TABLE `LineMarker` DISABLE KEYS */;
/*!40000 ALTER TABLE `LineMarker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LineName`
--

DROP TABLE IF EXISTS `LineName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LineName` (
  `_LineName_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Line_key` int(11) NOT NULL,
  `_NameFamily_key` int(11) DEFAULT NULL,
  `DateAssigned` datetime DEFAULT NULL,
  `IsPrimaryName` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_LineNameType_key` int(11) NOT NULL,
  `LineName` varchar(200) DEFAULT NULL,
  `LineNameMarkup` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`_LineName_key`),
  KEY `Ref3515` (`_Line_key`),
  KEY `Ref3452` (`_NameFamily_key`),
  KEY `Refcv_LineNameType359` (`_LineNameType_key`),
  KEY `RefMarker998` (`_Workgroup_key`),
  CONSTRAINT `Refcv_LineNameType359` FOREIGN KEY (`_LineNameType_key`) REFERENCES `cv_LineNameType` (`_LineNameType_key`),
  CONSTRAINT `RefLine15` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefMarker998` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `RefNameFamily52` FOREIGN KEY (`_NameFamily_key`) REFERENCES `NameFamily` (`_NameFamily_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LineName`
--

LOCK TABLES `LineName` WRITE;
/*!40000 ALTER TABLE `LineName` DISABLE KEYS */;
/*!40000 ALTER TABLE `LineName` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `_Location_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentLocation_key` int(11) DEFAULT NULL,
  `_LocationType_key` int(11) NOT NULL,
  `LocationName` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `CreatedBy` varchar(128) NOT NULL,
  PRIMARY KEY (`_Location_key`),
  KEY `Ref114168` (`_ParentLocation_key`),
  KEY `Ref172263` (`_LocationType_key`),
  KEY `RefLocation996` (`_Workgroup_key`),
  CONSTRAINT `Refcv_LocationType263` FOREIGN KEY (`_LocationType_key`) REFERENCES `cv_LocationType` (`_LocationType_key`),
  CONSTRAINT `RefLocation168` FOREIGN KEY (`_ParentLocation_key`) REFERENCES `Location` (`_Location_key`),
  CONSTRAINT `RefLocation996` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `RefMarker997` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
-- INSERT INTO `Location` VALUES (1,NULL,1,'SPF RG1210 / SPF HD room',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(2,NULL,1,'SPF RG1230 / SPF Cryo',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(3,NULL,1,'SPF Custom',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(4,NULL,1,'SPF Embryo Transfer',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(5,NULL,1,'Bldg 3 lab / R&D Lab',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(6,NULL,1,'MP26',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(8,NULL,1,'CB-BSL2',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(9,NULL,1,'B1B',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(10,NULL,1,'CB-64',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(11,NULL,1,'Importation',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(12,NULL,1,'Importation IVF',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(13,NULL,1,'Cryo IVF',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(14,NULL,1,'Media Lab',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(15,NULL,1,'MicroInjection',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(16,NULL,1,'Cell Biology',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(17,NULL,1,'Biobank',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(18,NULL,1,'Embryo Cryo',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(19,NULL,1,'Stem Cell and Primary Cell Facility;',1,1,'2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,'dba'),(20,NULL,2,'MP23',1,2,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(21,NULL,2,'MP24',1,2,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(22,NULL,2,'AX7',1,2,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(23,NULL,2,'AX29',1,2,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(24,NULL,2,'Behavioral  Suite  2059',1,3,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(25,NULL,2,'Behavioral  Suite  2064',1,3,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(26,NULL,2,'Cancer Wet Lab',1,3,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(27,NULL,2,'Cell Culture',1,3,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(28,NULL,2,'ERB 1419',1,4,'2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,'dba'),(29,NULL,2,'Office',1,1,'2011-02-02 16:41:44','dba','2011-02-02 16:41:44',1,'dba'),(30,NULL,1,'3035',1,11,'2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,'dba');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocationCharacteristic`
--

DROP TABLE IF EXISTS `LocationCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocationCharacteristic` (
  `_LocationCharacteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_LocationType_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LocationCharacteristic_key`),
  KEY `Ref155226` (`_CharacteristicVersion_key`),
  KEY `Ref172271` (`_LocationType_key`),
  KEY `RefLocation995` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion226` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_LocationType271` FOREIGN KEY (`_LocationType_key`) REFERENCES `cv_LocationType` (`_LocationType_key`),
  CONSTRAINT `RefLocation995` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocationCharacteristic`
--

LOCK TABLES `LocationCharacteristic` WRITE;
/*!40000 ALTER TABLE `LocationCharacteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `LocationCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocationCharacteristicInstance`
--

DROP TABLE IF EXISTS `LocationCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocationCharacteristicInstance` (
  `_LocationCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LocationCharacteristic_key` int(11) NOT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,10) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(1) DEFAULT NULL,
  `DateStart` datetime DEFAULT NULL,
  `DateEnd` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_LocationCharacteristicInstance_key`),
  KEY `Ref156227` (`_LocationCharacteristic_key`),
  KEY `RefEnumerationItem302` (`_EnumerationItem_key`),
  KEY `RefWorkgroup504` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem302` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefLocationCharacteristic227` FOREIGN KEY (`_LocationCharacteristic_key`) REFERENCES `LocationCharacteristic` (`_LocationCharacteristic_key`),
  CONSTRAINT `RefWorkgroup504` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocationCharacteristicInstance`
--

LOCK TABLES `LocationCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `LocationCharacteristicInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `LocationCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocationContainerLayout`
--

DROP TABLE IF EXISTS `LocationContainerLayout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocationContainerLayout` (
  `_LocationContainerLayout_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LocationType_key` int(11) NOT NULL,
  `_ContainerType_key` int(11) NOT NULL,
  `LayoutName` varchar(75) NOT NULL,
  `QuantityX` int(11) DEFAULT NULL,
  `QuantityY` int(11) DEFAULT NULL,
  `QuantityZ` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LocationContainerLayout_key`),
  KEY `Ref122249` (`_ContainerType_key`),
  KEY `Ref172264` (`_LocationType_key`),
  KEY `RefWorkgroup505` (`_Workgroup_key`),
  CONSTRAINT `Refcv_ContainerType249` FOREIGN KEY (`_ContainerType_key`) REFERENCES `cv_ContainerType` (`_ContainerType_key`),
  CONSTRAINT `Refcv_LocationType264` FOREIGN KEY (`_LocationType_key`) REFERENCES `cv_LocationType` (`_LocationType_key`),
  CONSTRAINT `RefWorkgroup505` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocationContainerLayout`
--

LOCK TABLES `LocationContainerLayout` WRITE;
/*!40000 ALTER TABLE `LocationContainerLayout` DISABLE KEYS */;
/*!40000 ALTER TABLE `LocationContainerLayout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocationContainerPosition`
--

DROP TABLE IF EXISTS `LocationContainerPosition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocationContainerPosition` (
  `_LocationContainerPosition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LocationContainerLayout_key` int(11) NOT NULL,
  `PositionName` varchar(18) NOT NULL,
  `Ordinal` int(11) NOT NULL,
  `XPosition` int(11) DEFAULT NULL,
  `YPosition` int(11) DEFAULT NULL,
  `ZPosition` int(11) DEFAULT NULL,
  `IsActive` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_LocationContainerPosition_key`),
  KEY `Ref163274` (`_LocationContainerLayout_key`),
  KEY `RefWorkgroup506` (`_Workgroup_key`),
  CONSTRAINT `RefLocationContainerLayout274` FOREIGN KEY (`_LocationContainerLayout_key`) REFERENCES `LocationContainerLayout` (`_LocationContainerLayout_key`),
  CONSTRAINT `RefWorkgroup506` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocationContainerPosition`
--

LOCK TABLES `LocationContainerPosition` WRITE;
/*!40000 ALTER TABLE `LocationContainerPosition` DISABLE KEYS */;
/*!40000 ALTER TABLE `LocationContainerPosition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Marker`
--

DROP TABLE IF EXISTS `Marker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Marker` (
  `_Marker_key` int(11) NOT NULL AUTO_INCREMENT,
  `_AlleleClass_key` int(11) DEFAULT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `_MarkerType_key` int(11) NOT NULL,
  `MarkerName` varchar(500) DEFAULT NULL,
  `MarkerSymbol` varchar(50) DEFAULT NULL,
  `LabSymbol` varchar(50) DEFAULT NULL,
  `Chromosome` varchar(18) DEFAULT NULL,
  `StartCoordinate` decimal(10,0) DEFAULT NULL,
  `EndCoordinate` decimal(10,0) DEFAULT NULL,
  `Strand` varchar(1) DEFAULT NULL,
  `Primer` varchar(50) DEFAULT NULL,
  `CmPostition` int(11) DEFAULT NULL,
  `MbPosition` int(11) DEFAULT NULL,
  `BuildNumber` varchar(50) DEFAULT NULL,
  `Comments` varchar(500) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `MarkerSymbolMarkup` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`_Marker_key`),
  KEY `Ref5977` (`_AlleleClass_key`),
  KEY `Ref5282` (`_MarkerType_key`),
  KEY `Ref3589` (`_Line_key`),
  KEY `RefWorkgroup507` (`_Workgroup_key`),
  CONSTRAINT `Refcv_AlleleClass77` FOREIGN KEY (`_AlleleClass_key`) REFERENCES `cv_AlleleClass` (`_AlleleClass_key`),
  CONSTRAINT `Refcv_MarkerType82` FOREIGN KEY (`_MarkerType_key`) REFERENCES `cv_MarkerType` (`_MarkerType_key`),
  CONSTRAINT `RefLine89` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefWorkgroup507` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Marker`
--

LOCK TABLES `Marker` WRITE;
/*!40000 ALTER TABLE `Marker` DISABLE KEYS */;
/*!40000 ALTER TABLE `Marker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Method`
--

DROP TABLE IF EXISTS `Method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Method` (
  `_Method_key` int(11) NOT NULL AUTO_INCREMENT,
  `Method` varchar(250) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Method_key`),
  KEY `RefWorkgroup508` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup508` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Method`
--

LOCK TABLES `Method` WRITE;
/*!40000 ALTER TABLE `Method` DISABLE KEYS */;
-- INSERT INTO `Method` VALUES (2,'Sperm Freeze','Sperm Freeze',NULL,1,'sbean','2011-02-03 12:46:50','sbean','2011-02-03 12:46:50',2),(3,'IVF','',NULL,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',1),(4,'Weaning Mice','Wean mice in SPF',NULL,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',1),(5,'Embryo Transfers (fresh embryos)','Embryo transfers using fresh embryos produced',NULL,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',1),(6,'Embryo Transfers (frozen embryos)','Embryos transfers for frozen embryos',NULL,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',1),(7,'Tail Tipping','Protocol for sampling mice',NULL,1,'sbean','2011-02-14 11:15:56','sbean','2011-02-14 11:15:56',1),(8,'Recording Genotyping Results','Record genotyping results from TGS, Laragen, Transnetyx and the customer.',NULL,1,'sbean','2011-02-14 11:17:45','sbean','2011-02-14 11:17:45',1),(9,'Prepping for Shipment of Mice','Prepping mice for shipment',NULL,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',1),(10,'Shipping Mice','Protocol for shipping mice',NULL,1,'sbean','2011-02-21 14:07:43','sbean','2011-02-21 14:07:43',1),(11,'construct protocol','asasasas',NULL,1,'michaelm','2011-03-31 15:09:40','michaelm','2011-03-31 15:09:40',1),(12,'Floxed Allele construct','this protocol contains the steps necessary to generate a Floxed allele construct',NULL,11,'douglash','2011-03-31 15:42:26','douglash','2011-04-05 18:11:21',2),(13,'mikes protocol','test',NULL,1,'michaelm','2011-04-06 07:12:42','michaelm','2011-04-06 07:12:42',1),(14,'Make MEFs','',NULL,1,'michaelm','2011-04-06 12:44:55','michaelm','2011-04-06 12:44:55',1);
/*!40000 ALTER TABLE `Method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MethodInstance`
--

DROP TABLE IF EXISTS `MethodInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MethodInstance` (
  `_MethodInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Project_key` int(11) NOT NULL,
  `_ServiceOrderItem_key` int(11) DEFAULT NULL,
  `MethodAlias` varchar(200) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `_MethodVersion_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_MethodInstance_key`),
  KEY `RefWorkgroup777` (`_Workgroup_key`),
  KEY `RefProject1` (`_Project_key`),
  KEY `RefMethodVersion` (`_MethodVersion_key`),
  KEY `FK_MethodInstance_4` (`_ServiceOrderItem_key`),
  CONSTRAINT `FK_MethodInstance_4` FOREIGN KEY (`_ServiceOrderItem_key`) REFERENCES `ServiceOrderItem` (`_ServiceOrderItem_key`),
  CONSTRAINT `RefMethodVersion` FOREIGN KEY (`_MethodVersion_key`) REFERENCES `MethodVersion` (`_MethodVersion_key`),
  CONSTRAINT `RefProject1` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup777` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MethodInstance`
--

LOCK TABLES `MethodInstance` WRITE;
/*!40000 ALTER TABLE `MethodInstance` DISABLE KEYS */;
-- INSERT INTO `MethodInstance` VALUES (1,1,NULL,'Recording Genotyping Results','sbean','2011-02-21 14:16:03','sbean','2011-02-21 14:16:03',0,1,NULL),(2,1,NULL,'Weaning Mice','sbean','2011-02-21 14:15:45','sbean','2011-02-21 14:15:45',0,1,NULL),(3,1,NULL,'Tail Tipping','sbean','2011-02-21 14:13:50','sbean','2011-02-21 14:13:50',0,1,NULL),(4,1,NULL,'Embryo Transfers (fresh embryos)','sbean','2011-02-21 14:13:15','sbean','2011-02-21 14:13:15',0,1,NULL),(5,1,NULL,'IVF','sbean','2011-02-21 14:12:58','sbean','2011-02-21 14:12:58',0,1,NULL),(6,1,NULL,'Sperm Freeze','sbean','2011-02-21 14:12:37','sbean','2011-02-21 14:12:37',0,1,NULL),(7,1,NULL,'Prepping for Shipment of Mice','sbean','2011-02-21 14:16:25','sbean','2011-02-21 14:16:25',0,1,NULL),(8,1,NULL,'Shipping Mice','sbean','2011-02-21 14:16:41','sbean','2011-02-21 14:16:41',0,1,NULL),(9,2,NULL,'Tail Tipping','sbean','2011-02-21 14:35:01','sbean','2011-02-21 14:35:01',0,1,NULL),(10,2,NULL,'Sperm Freeze','sbean','2011-02-21 14:34:47','sbean','2011-02-21 14:34:47',0,1,NULL),(11,2,NULL,'IVF','sbean','2011-02-21 14:33:57','sbean','2011-02-21 14:33:57',0,1,NULL),(12,2,NULL,'Embryo Transfers (fresh embryos)','sbean','2011-02-21 14:33:39','sbean','2011-02-21 14:33:39',0,1,NULL),(13,2,NULL,'Weaning Mice','sbean','2011-02-21 14:33:19','sbean','2011-02-21 14:33:19',0,1,NULL),(14,2,NULL,'Tail Tipping','sbean','2011-02-21 14:33:05','sbean','2011-02-21 14:33:05',0,1,NULL),(15,2,NULL,'Recording Genotyping Results','sbean','2011-02-21 14:32:50','sbean','2011-02-21 14:32:50',0,1,NULL),(16,2,NULL,'Prepping for Shipment of Mice','sbean','2011-02-21 14:32:22','sbean','2011-02-21 14:32:22',0,1,NULL),(17,2,NULL,'Shipping Mice','sbean','2011-02-21 14:31:53','sbean','2011-02-21 14:31:53',0,1,NULL),(18,3,NULL,'Shipping Mice','deiben','2011-03-29 15:46:13','deiben','2011-03-29 15:46:13',0,1,NULL),(19,4,NULL,'Embryo Transfers (frozen embryos)','TODO','2011-03-31 15:07:50','TODO','2011-03-31 15:07:50',1,1,6),(20,4,NULL,'construct protocol','TODO','2011-03-31 15:10:30','TODO','2011-03-31 15:10:30',1,1,11),(21,5,NULL,'Floxed Allele construct','douglash','2011-04-05 18:24:28','douglash','2011-04-05 18:24:28',0,11,NULL),(24,9,NULL,'Weaning Mice','amiller','2011-04-06 11:19:30','amiller','2011-04-06 11:19:30',1,1,4),(25,9,NULL,'Embryo Transfers (fresh embryos)','amiller','2011-04-06 11:19:31','amiller','2011-04-06 11:19:31',1,1,5),(26,9,NULL,'Embryo Transfers (frozen embryos)','amiller','2011-04-06 11:19:31','amiller','2011-04-06 11:19:31',1,1,6),(28,10,NULL,'Make MEFs','michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 12:56:36',1,1,14);
/*!40000 ALTER TABLE `MethodInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MethodProcedureDefinition`
--

DROP TABLE IF EXISTS `MethodProcedureDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MethodProcedureDefinition` (
  `_MethodProcedureDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_RelativeToProcedure_key` int(11) DEFAULT NULL,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `_MethodVersion_key` int(11) NOT NULL,
  `_Epoch_key` int(11) DEFAULT NULL,
  `_TimeUnit_key` int(11) NOT NULL,
  `TimeFromRelativeProcedure` int(11) DEFAULT NULL,
  `ProcedureAlias` varchar(50) NOT NULL,
  `ProposedProcedureDate` int(11) DEFAULT NULL,
  `ScheduleWindow` decimal(38,0) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_MethodProcedureDefinition_key`),
  KEY `Ref90108` (`_ProcedureDefinitionVersion_key`),
  KEY `Ref94112` (`_MethodVersion_key`),
  KEY `Ref92329` (`_RelativeToProcedure_key`),
  KEY `Ref107135` (`_Epoch_key`),
  KEY `Ref106136` (`_TimeUnit_key`),
  KEY `RefWorkgroup509` (`_Workgroup_key`),
  CONSTRAINT `Refcv_Epoch135` FOREIGN KEY (`_Epoch_key`) REFERENCES `cv_Epoch` (`_Epoch_key`),
  CONSTRAINT `Refcv_TimeUnit136` FOREIGN KEY (`_TimeUnit_key`) REFERENCES `cv_TimeUnit` (`_TimeUnit_key`),
  CONSTRAINT `RefMethodProcedureDefinition329` FOREIGN KEY (`_RelativeToProcedure_key`) REFERENCES `MethodProcedureDefinition` (`_MethodProcedureDefinition_key`) ON DELETE SET NULL,
  CONSTRAINT `RefMethodVersion112` FOREIGN KEY (`_MethodVersion_key`) REFERENCES `MethodVersion` (`_MethodVersion_key`),
  CONSTRAINT `RefProcedureDefinitionVersion108` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`),
  CONSTRAINT `RefWorkgroup509` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MethodProcedureDefinition`
--

LOCK TABLES `MethodProcedureDefinition` WRITE;
/*!40000 ALTER TABLE `MethodProcedureDefinition` DISABLE KEYS */;
-- INSERT INTO `MethodProcedureDefinition` VALUES (1,NULL,12,2,NULL,1,0,'Order Straws',NULL,NULL,1,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(2,NULL,13,2,NULL,1,0,'Assign Locations',NULL,NULL,2,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(3,NULL,14,2,NULL,1,0,'Harvest Sperm',NULL,NULL,3,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(4,NULL,15,2,NULL,1,0,'Load Straws',NULL,NULL,4,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(5,NULL,16,2,NULL,1,0,'Freeze Sperm',NULL,NULL,5,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(6,NULL,17,2,NULL,1,0,'Store Sperm',NULL,NULL,6,1,'sbean','2011-02-03 13:12:41','sbean','2011-02-03 13:12:41',0),(7,NULL,19,3,NULL,1,0,'Prep Dishes',NULL,NULL,1,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',0),(8,NULL,20,3,NULL,1,0,'Perform the IVF',NULL,NULL,2,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',0),(9,NULL,21,3,NULL,1,0,'Perform the IVF check',NULL,NULL,3,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',0),(10,NULL,22,3,NULL,1,0,'Perform the Change Over',NULL,NULL,4,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',0),(11,NULL,23,3,NULL,1,0,'Prep 2 cells for Embryo Transfer',NULL,NULL,5,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',0),(12,NULL,29,4,NULL,1,0,'Record Pups Born',NULL,NULL,1,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',0),(13,NULL,30,4,NULL,1,0,'Wean Mice',NULL,NULL,2,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',0),(14,NULL,31,4,NULL,1,0,'Prep lily tubs for LAH',NULL,NULL,3,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',0),(15,NULL,32,4,NULL,1,0,'Pack/Ship Pseduos to LAH',NULL,NULL,4,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',0),(16,NULL,24,5,NULL,1,0,'Schedule Washing',NULL,NULL,1,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',0),(17,NULL,25,5,NULL,1,0,'Schedule embryo transfers',NULL,NULL,2,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',0),(18,NULL,26,5,NULL,1,0,'Print Cage Cards for Embryo Transfers',NULL,NULL,3,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',0),(19,NULL,27,5,NULL,1,0,'Print Labels for dishes',NULL,NULL,4,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',0),(20,NULL,28,6,NULL,1,0,'Thaw Embryos',NULL,NULL,1,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',0),(21,NULL,24,6,NULL,1,0,'Schedule Washing',NULL,NULL,2,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',0),(22,NULL,25,6,NULL,1,0,'Schedule embryo transfers',NULL,NULL,3,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',0),(23,NULL,26,6,NULL,1,0,'Print Cage Cards for Embryo Transfers',NULL,NULL,4,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',0),(24,NULL,27,6,NULL,1,0,'Print Labels for dishes',NULL,NULL,5,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',0),(25,NULL,34,7,NULL,1,0,'Id Mice',NULL,NULL,1,1,'sbean','2011-02-14 11:15:56','sbean','2011-02-14 11:15:56',0),(26,NULL,33,7,NULL,1,0,'Sample Mice',NULL,NULL,2,1,'sbean','2011-02-14 11:15:56','sbean','2011-02-14 11:15:56',0),(27,NULL,35,7,NULL,1,0,'Submit Samples',NULL,NULL,3,1,'sbean','2011-02-14 11:15:56','sbean','2011-02-14 11:15:56',0),(28,NULL,36,8,NULL,1,0,'Record Genotyping Results',NULL,NULL,1,1,'sbean','2011-02-14 11:17:45','sbean','2011-02-14 11:17:45',0),(29,NULL,42,9,NULL,1,0,'Enter Estimated Ship Date',NULL,NULL,1,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(30,NULL,37,9,NULL,1,0,'Confirm Ship Dates',NULL,NULL,2,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(31,NULL,38,9,NULL,1,0,'Print Check Lists for Shipments',NULL,NULL,3,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(32,NULL,40,9,NULL,1,0,'Print report for mice ready',NULL,NULL,4,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(33,NULL,41,9,NULL,1,0,'Confirm sex for mice ready to ship',NULL,NULL,5,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(34,NULL,39,9,NULL,1,0,'Label Shipping Boxes',NULL,NULL,6,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(35,NULL,44,9,NULL,1,0,'Print snapouts',NULL,NULL,7,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(36,NULL,46,9,NULL,1,0,'Enter Shipments in CDT for IMR',NULL,NULL,8,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(37,NULL,45,9,NULL,1,0,'Check boxes in Shipping section for food and water',NULL,NULL,9,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(38,NULL,43,9,NULL,1,0,'Set up Shipping Boxes',NULL,NULL,10,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',0),(39,NULL,47,10,NULL,1,0,'Print Axapta Ship Sheets',NULL,NULL,1,1,'sbean','2011-02-21 14:07:43','sbean','2011-02-21 14:07:43',0),(40,NULL,48,10,NULL,1,0,'Ship and Pack Mouse Shipment',NULL,NULL,2,1,'sbean','2011-02-21 14:07:43','sbean','2011-02-21 14:07:43',0),(41,NULL,49,10,NULL,1,0,'PPC orders in Axapta',NULL,NULL,3,1,'sbean','2011-02-21 14:07:43','sbean','2011-02-21 14:07:43',0),(42,NULL,51,11,NULL,1,0,'Construct Design',NULL,NULL,1,1,'michaelm','2011-03-31 15:09:40','michaelm','2011-03-31 15:09:40',0),(43,NULL,52,11,NULL,1,0,'Construct Approval',NULL,NULL,2,1,'michaelm','2011-03-31 15:09:40','michaelm','2011-03-31 15:09:40',0),(44,NULL,53,12,NULL,1,0,'Construct Design',NULL,NULL,1,11,'douglash','2011-03-31 15:42:26','douglash','2011-04-05 18:11:21',1),(45,NULL,54,12,NULL,1,0,'Design Approval',NULL,NULL,2,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(46,NULL,55,12,NULL,1,0,'Gap Repair',NULL,NULL,3,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(47,NULL,56,12,NULL,1,0,'First Targeting',NULL,NULL,4,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(48,NULL,57,12,NULL,1,0,'Neo Excision',NULL,NULL,5,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(49,NULL,58,12,NULL,1,0,'Second targeting',NULL,NULL,6,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(50,NULL,59,12,NULL,1,0,'Cre test',NULL,NULL,7,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(51,NULL,60,12,NULL,1,0,'FLP test',NULL,NULL,8,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(52,NULL,61,12,NULL,1,0,'Sequence verified',NULL,NULL,9,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(53,NULL,63,12,NULL,1,0,'Southern Validation',NULL,NULL,10,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(54,NULL,62,12,NULL,1,0,'Plasmid Prep for Electroporation',NULL,NULL,11,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(55,NULL,64,12,NULL,1,0,'Initial Southern Screen',NULL,NULL,12,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(56,NULL,65,12,NULL,1,0,'Southern Confirmation',NULL,NULL,13,11,'douglash','2011-04-05 18:11:21','douglash','2011-04-05 18:11:21',0),(57,NULL,37,13,NULL,1,0,'Confirm Ship Dates',NULL,NULL,1,1,'michaelm','2011-04-06 07:12:42','michaelm','2011-04-06 07:12:42',0),(58,NULL,39,13,NULL,1,0,'Label Shipping Boxes',NULL,NULL,2,1,'michaelm','2011-04-06 07:12:42','michaelm','2011-04-06 07:12:42',0),(59,NULL,9,14,NULL,1,0,'Order Mice',NULL,NULL,1,1,'michaelm','2011-04-06 12:44:55','michaelm','2011-04-06 12:44:55',0),(60,NULL,66,14,NULL,1,0,'Derive MEFs',NULL,NULL,2,1,'michaelm','2011-04-06 12:44:55','michaelm','2011-04-06 12:44:55',0);
/*!40000 ALTER TABLE `MethodProcedureDefinition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MethodVersion`
--

DROP TABLE IF EXISTS `MethodVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MethodVersion` (
  `_MethodVersion_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Method_key` int(11) NOT NULL,
  `_CurrentSOP_key` int(11) DEFAULT NULL,
  `_Study_key` int(11) DEFAULT NULL,
  `VersionNumber` int(11) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_MethodVersion_key`),
  KEY `Ref95119` (`_CurrentSOP_key`),
  KEY `Ref195321` (`_Method_key`),
  KEY `Ref47137` (`_Study_key`),
  KEY `RefWorkgroup510` (`_Workgroup_key`),
  CONSTRAINT `RefMethod321` FOREIGN KEY (`_Method_key`) REFERENCES `Method` (`_Method_key`),
  CONSTRAINT `RefSOP121` FOREIGN KEY (`_CurrentSOP_key`) REFERENCES `SOPVersion` (`_SOPVersion_key`),
  CONSTRAINT `RefStudy137` FOREIGN KEY (`_Study_key`) REFERENCES `Study` (`_Study_key`),
  CONSTRAINT `RefWorkgroup510` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MethodVersion`
--

LOCK TABLES `MethodVersion` WRITE;
/*!40000 ALTER TABLE `MethodVersion` DISABLE KEYS */;
-- INSERT INTO `MethodVersion` VALUES (2,2,NULL,NULL,1,1,1,'sbean','2011-02-03 12:46:50','sbean','2011-02-03 12:46:50',2),(3,3,NULL,NULL,1,1,1,'sbean','2011-02-03 13:22:26','sbean','2011-02-03 13:22:26',1),(4,4,NULL,NULL,1,1,1,'sbean','2011-02-03 15:31:06','sbean','2011-02-03 15:31:06',1),(5,5,NULL,NULL,1,1,1,'sbean','2011-02-03 15:32:39','sbean','2011-02-03 15:32:39',1),(6,6,NULL,NULL,1,1,1,'sbean','2011-02-03 15:33:58','sbean','2011-02-03 15:33:58',1),(7,7,NULL,NULL,1,1,1,'sbean','2011-02-14 11:15:56','sbean','2011-02-14 11:15:56',1),(8,8,NULL,NULL,1,1,1,'sbean','2011-02-14 11:17:45','sbean','2011-02-14 11:17:45',1),(9,9,NULL,NULL,1,1,1,'sbean','2011-02-21 13:59:11','sbean','2011-02-21 13:59:11',1),(10,10,NULL,NULL,1,1,1,'sbean','2011-02-21 14:07:43','sbean','2011-02-21 14:07:43',1),(11,11,NULL,NULL,1,1,1,'michaelm','2011-03-31 15:09:40','michaelm','2011-03-31 15:09:40',1),(12,12,NULL,NULL,1,1,11,'douglash','2011-03-31 15:42:26','douglash','2011-03-31 15:42:26',2),(13,13,NULL,NULL,1,1,1,'michaelm','2011-04-06 07:12:42','michaelm','2011-04-06 07:12:42',1),(14,14,NULL,NULL,1,1,1,'michaelm','2011-04-06 12:44:55','michaelm','2011-04-06 12:44:55',1);
/*!40000 ALTER TABLE `MethodVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NameComponentDetails`
--

DROP TABLE IF EXISTS `NameComponentDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NameComponentDetails` (
  `_NameComponentDetails_key` int(11) NOT NULL DEFAULT '0',
  `_NameFamily_key` int(11) NOT NULL,
  `NameComponent` varchar(100) NOT NULL,
  `FormatModel` varchar(100) DEFAULT NULL,
  `IsIncremented` tinyint(4) NOT NULL,
  `IsUserSupplied` tinyint(4) NOT NULL,
  `SelectStatement` varchar(1000) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `ComponentOrder` decimal(10,0) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_NameComponentDetails_key`),
  KEY `Ref349` (`_NameFamily_key`),
  KEY `RefWorkgroup511` (`_Workgroup_key`),
  CONSTRAINT `RefNameFamily9` FOREIGN KEY (`_NameFamily_key`) REFERENCES `NameFamily` (`_NameFamily_key`),
  CONSTRAINT `RefWorkgroup511` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NameComponentDetails`
--

LOCK TABLES `NameComponentDetails` WRITE;
/*!40000 ALTER TABLE `NameComponentDetails` DISABLE KEYS */;
-- INSERT INTO `NameComponentDetails` VALUES (1,1,'INC_ID','0999999',1,0,NULL,'ID Component','1',1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1);
/*!40000 ALTER TABLE `NameComponentDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NameFamily`
--

DROP TABLE IF EXISTS `NameFamily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NameFamily` (
  `_NameFamily_key` int(11) NOT NULL AUTO_INCREMENT,
  `_NamingConvention_key` int(11) NOT NULL,
  `NameFormat` varchar(60) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `RegularExpression` varchar(100) DEFAULT NULL,
  `UserFriendlyFormat` varchar(100) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_NameFamily_key`),
  UNIQUE KEY `UserFriendlyFormat` (`UserFriendlyFormat`,`_Workgroup_key`),
  KEY `Ref1538` (`_NamingConvention_key`),
  KEY `RefWorkgroup512` (`_Workgroup_key`),
  CONSTRAINT `Refcv_NamingConvention38` FOREIGN KEY (`_NamingConvention_key`) REFERENCES `cv_NamingConvention` (`_NamingConvention_key`),
  CONSTRAINT `RefWorkgroup512` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NameFamily`
--

LOCK TABLES `NameFamily` WRITE;
/*!40000 ALTER TABLE `NameFamily` DISABLE KEYS */;
-- INSERT INTO `NameFamily` VALUES (1,1,'#INC_ID#',NULL,'^\\d{1,25}$','Project ID',1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1);
/*!40000 ALTER TABLE `NameFamily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NumericLimit`
--

DROP TABLE IF EXISTS `NumericLimit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NumericLimit` (
  `_NumericLimit_key` int(11) NOT NULL AUTO_INCREMENT,
  `_LimitDefinition_key` int(11) NOT NULL,
  `_Output_key` int(11) NOT NULL,
  `_Sex_key` int(11) DEFAULT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `AgeInWeeks` int(11) DEFAULT NULL,
  `LowLimit` decimal(38,0) DEFAULT NULL,
  `HighLimit` decimal(38,0) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_NumericLimit_key`),
  KEY `Ref87151` (`_LimitDefinition_key`),
  KEY `Ref64152` (`_Output_key`),
  KEY `Ref20153` (`_Sex_key`),
  KEY `Ref35154` (`_Line_key`),
  KEY `RefWorkgroup513` (`_Workgroup_key`),
  CONSTRAINT `Refcv_Sex153` FOREIGN KEY (`_Sex_key`) REFERENCES `cv_Sex` (`_Sex_key`),
  CONSTRAINT `RefLimitDefinition151` FOREIGN KEY (`_LimitDefinition_key`) REFERENCES `LimitDefinition` (`_LimitDefinition_key`),
  CONSTRAINT `RefLine154` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefOutput152` FOREIGN KEY (`_Output_key`) REFERENCES `Output` (`_Output_key`),
  CONSTRAINT `RefWorkgroup513` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NumericLimit`
--

LOCK TABLES `NumericLimit` WRITE;
/*!40000 ALTER TABLE `NumericLimit` DISABLE KEYS */;
/*!40000 ALTER TABLE `NumericLimit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderLine`
--

DROP TABLE IF EXISTS `OrderLine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderLine` (
  `_OrderLine_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Line_key` int(11) DEFAULT NULL,
  `_ServiceOrder_key` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrderLine_key`),
  KEY `Ref125324` (`_ServiceOrder_key`),
  KEY `Ref35325` (`_Line_key`),
  KEY `RefWorkgroup514` (`_Workgroup_key`),
  CONSTRAINT `RefLine325` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefServiceOrder324` FOREIGN KEY (`_ServiceOrder_key`) REFERENCES `ServiceOrder` (`_ServiceOrder_key`),
  CONSTRAINT `RefWorkgroup514` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderLine`
--

LOCK TABLES `OrderLine` WRITE;
/*!40000 ALTER TABLE `OrderLine` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderLine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Organism`
--

DROP TABLE IF EXISTS `Organism`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organism` (
  `_Organism_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Sex_key` int(11) DEFAULT NULL,
  `_BirthEvent_key` int(11) DEFAULT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `_Generation_key` int(11) DEFAULT NULL,
  `_BreedingStatus_key` int(11) DEFAULT NULL,
  `_Diet_key` int(11) DEFAULT NULL,
  `_OrganismOrigin_key` int(11) DEFAULT NULL,
  `_OrganismStatus_key` int(11) DEFAULT NULL,
  `_ExitReason_key` int(11) DEFAULT NULL,
  `_Subcolony_key` int(11) DEFAULT NULL,
  `_Taxon_key` int(11) NOT NULL,
  `OrganismID` int(11) NOT NULL,
  `DateBirth` datetime DEFAULT NULL,
  `DateExit` datetime DEFAULT NULL,
  `ExitReasonDetails` varchar(100) DEFAULT NULL,
  `Comments` varchar(1000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `DateReceived` datetime DEFAULT NULL,
  PRIMARY KEY (`_Organism_key`),
  KEY `Ref3512` (`_Line_key`),
  KEY `Ref435` (`_Diet_key`),
  KEY `Ref839` (`_ExitReason_key`),
  KEY `Ref2340` (`_Generation_key`),
  KEY `Ref2641` (`_Subcolony_key`),
  KEY `Ref2142` (`_OrganismOrigin_key`),
  KEY `Ref2043` (`_Sex_key`),
  KEY `Ref2244` (`_OrganismStatus_key`),
  KEY `Ref1345` (`_Taxon_key`),
  KEY `Ref346` (`_BreedingStatus_key`),
  KEY `Ref1047` (`_BirthEvent_key`),
  KEY `RefWorkgroup515` (`_Workgroup_key`),
  CONSTRAINT `RefBirthEvent47` FOREIGN KEY (`_BirthEvent_key`) REFERENCES `BirthEvent` (`_BirthEvent_key`),
  CONSTRAINT `Refcv_BreedingStatus46` FOREIGN KEY (`_BreedingStatus_key`) REFERENCES `cv_BreedingStatus` (`_BreedingStatus_key`),
  CONSTRAINT `Refcv_Diet35` FOREIGN KEY (`_Diet_key`) REFERENCES `cv_Diet` (`_Diet_key`),
  CONSTRAINT `Refcv_ExitReason39` FOREIGN KEY (`_ExitReason_key`) REFERENCES `cv_ExitReason` (`_ExitReason_key`),
  CONSTRAINT `Refcv_Generation40` FOREIGN KEY (`_Generation_key`) REFERENCES `cv_Generation` (`_Generation_key`),
  CONSTRAINT `Refcv_OrganismOrigin42` FOREIGN KEY (`_OrganismOrigin_key`) REFERENCES `cv_OrganismOrigin` (`_OrganismOrigin_key`),
  CONSTRAINT `Refcv_OrganismStatus44` FOREIGN KEY (`_OrganismStatus_key`) REFERENCES `cv_OrganismStatus` (`_OrganismStatus_key`),
  CONSTRAINT `Refcv_Sex43` FOREIGN KEY (`_Sex_key`) REFERENCES `cv_Sex` (`_Sex_key`),
  CONSTRAINT `Refcv_Subcolony41` FOREIGN KEY (`_Subcolony_key`) REFERENCES `cv_Subcolony` (`_Subcolony_key`),
  CONSTRAINT `Refcv_Taxon45` FOREIGN KEY (`_Taxon_key`) REFERENCES `cv_Taxon` (`_Taxon_key`),
  CONSTRAINT `RefLine12` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefWorkgroup515` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organism`
--

LOCK TABLES `Organism` WRITE;
/*!40000 ALTER TABLE `Organism` DISABLE KEYS */;
/*!40000 ALTER TABLE `Organism` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismContainer`
--

DROP TABLE IF EXISTS `OrganismContainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismContainer` (
  `_OrganismContainer_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Organism_key` int(11) NOT NULL,
  `_Container_key` int(11) NOT NULL,
  `DateIn` datetime DEFAULT NULL,
  `DateOut` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismContainer_key`),
  KEY `Ref117251` (`_Container_key`),
  KEY `Ref30253` (`_Organism_key`),
  KEY `RefWorkgroup516` (`_Workgroup_key`),
  CONSTRAINT `RefContainer251` FOREIGN KEY (`_Container_key`) REFERENCES `Container` (`_Container_key`),
  CONSTRAINT `RefOrganism253` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefWorkgroup516` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismContainer`
--

LOCK TABLES `OrganismContainer` WRITE;
/*!40000 ALTER TABLE `OrganismContainer` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismContainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismName`
--

DROP TABLE IF EXISTS `OrganismName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismName` (
  `_OrganismName_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Organism_key` int(11) NOT NULL,
  `_NameFamily_key` int(11) DEFAULT NULL,
  `OrganismName` varchar(40) NOT NULL,
  `AssignedDate` date DEFAULT NULL,
  `IsCurrentName` tinyint(4) NOT NULL DEFAULT '1',
  `Comments` varchar(256) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismName_key`),
  KEY `Ref3410` (`_NameFamily_key`),
  KEY `Ref3013` (`_Organism_key`),
  KEY `RefWorkgroup517` (`_Workgroup_key`),
  CONSTRAINT `RefNameFamily10` FOREIGN KEY (`_NameFamily_key`) REFERENCES `NameFamily` (`_NameFamily_key`),
  CONSTRAINT `RefOrganism13` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefWorkgroup517` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismName`
--

LOCK TABLES `OrganismName` WRITE;
/*!40000 ALTER TABLE `OrganismName` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismName` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismOrder`
--

DROP TABLE IF EXISTS `OrganismOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismOrder` (
  `_OrganismOrder_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Project_key` int(11) DEFAULT NULL,
  `_ServiceOrderVersion_key` int(11) NOT NULL,
  `_Taxon_key` int(11) NOT NULL,
  `_Institution_key` int(11) NOT NULL,
  `_ContactPerson_key` int(11) DEFAULT NULL,
  `DateOrdered` datetime DEFAULT NULL,
  `Comments` varchar(1000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_OrganismOrderStatus_key` int(11) DEFAULT NULL,
  `DateRequired` datetime DEFAULT NULL,
  `ExternalOrderID` varchar(18) DEFAULT NULL,
  `AccountNumber` varchar(18) DEFAULT NULL,
  `ContactComments` varchar(1000) DEFAULT NULL,
  `_Department_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_OrganismOrder_key`),
  KEY `Ref139200` (`_ServiceOrderVersion_key`),
  KEY `Ref142210` (`_Project_key`),
  KEY `Ref160315` (`_Institution_key`),
  KEY `Ref161317` (`_ContactPerson_key`),
  KEY `Ref13338` (`_Taxon_key`),
  KEY `Refcv_OrganismOrderStatus369` (`_OrganismOrderStatus_key`),
  KEY `RefDepartment386` (`_Department_key`),
  KEY `RefWorkgroup518` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson317` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `Refcv_OrganismOrderStatus369` FOREIGN KEY (`_OrganismOrderStatus_key`) REFERENCES `cv_OrganismOrderStatus` (`_OrganismOrderStatus_key`),
  CONSTRAINT `Refcv_Taxon338` FOREIGN KEY (`_Taxon_key`) REFERENCES `cv_Taxon` (`_Taxon_key`),
  CONSTRAINT `RefDepartment386` FOREIGN KEY (`_Department_key`) REFERENCES `Department` (`_Department_key`),
  CONSTRAINT `RefInstitution315` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`),
  CONSTRAINT `RefProject210` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefServiceOrderVersion200` FOREIGN KEY (`_ServiceOrderVersion_key`) REFERENCES `ServiceOrderVersion` (`_ServiceOrderVersion_key`),
  CONSTRAINT `RefWorkgroup518` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismOrder`
--

LOCK TABLES `OrganismOrder` WRITE;
/*!40000 ALTER TABLE `OrganismOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismOrderDetails`
--

DROP TABLE IF EXISTS `OrganismOrderDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismOrderDetails` (
  `_OrganismOrderDetails_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Sex_key` int(11) DEFAULT NULL,
  `_OrganismOrder_key` int(11) NOT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `NumberOfOrganisms` int(11) NOT NULL,
  `Genotype` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `Age` int(11) DEFAULT NULL,
  `_TimeUnit_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_OrganismOrderDetails_key`),
  KEY `Ref130199` (`_OrganismOrder_key`),
  KEY `Ref20212` (`_Sex_key`),
  KEY `Ref35213` (`_Line_key`),
  KEY `Refcv_TimeUnit342` (`_TimeUnit_key`),
  KEY `RefWorkgroup519` (`_Workgroup_key`),
  CONSTRAINT `Refcv_Sex212` FOREIGN KEY (`_Sex_key`) REFERENCES `cv_Sex` (`_Sex_key`),
  CONSTRAINT `Refcv_TimeUnit342` FOREIGN KEY (`_TimeUnit_key`) REFERENCES `cv_TimeUnit` (`_TimeUnit_key`),
  CONSTRAINT `RefLine213` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefOrganismOrder199` FOREIGN KEY (`_OrganismOrder_key`) REFERENCES `OrganismOrder` (`_OrganismOrder_key`),
  CONSTRAINT `RefWorkgroup519` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismOrderDetails`
--

LOCK TABLES `OrganismOrderDetails` WRITE;
/*!40000 ALTER TABLE `OrganismOrderDetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismOrderDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismSample`
--

DROP TABLE IF EXISTS `OrganismSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismSample` (
  `_OrganismSample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Sample_key` int(11) NOT NULL,
  `_Organism_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismSample_key`),
  KEY `Ref49312` (`_Sample_key`),
  KEY `Ref3014` (`_Organism_key`),
  KEY `RefWorkgroup520` (`_Workgroup_key`),
  CONSTRAINT `RefOrganism14` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefSample312` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup520` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismSample`
--

LOCK TABLES `OrganismSample` WRITE;
/*!40000 ALTER TABLE `OrganismSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismShipment`
--

DROP TABLE IF EXISTS `OrganismShipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismShipment` (
  `_OrganismShipment_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Line_key` int(11) DEFAULT NULL,
  `ExternalOrderID` varchar(18) NOT NULL,
  `BoxNumber` varchar(18) NOT NULL,
  `CountMales` int(11) NOT NULL,
  `CountFemales` int(11) NOT NULL,
  `ArrivalDate` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismShipment_key`),
  KEY `RefWorkgroup521` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup521` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismShipment`
--

LOCK TABLES `OrganismShipment` WRITE;
/*!40000 ALTER TABLE `OrganismShipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismShipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrganismStudy`
--

DROP TABLE IF EXISTS `OrganismStudy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrganismStudy` (
  `_OrganismStudy_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Study_key` int(11) NOT NULL,
  `_OrganismName_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismStudy_key`),
  KEY `Ref2816` (`_OrganismName_key`),
  KEY `Ref4737` (`_Study_key`),
  KEY `RefWorkgroup522` (`_Workgroup_key`),
  CONSTRAINT `RefOrganismName16` FOREIGN KEY (`_OrganismName_key`) REFERENCES `OrganismName` (`_OrganismName_key`),
  CONSTRAINT `RefStudy37` FOREIGN KEY (`_Study_key`) REFERENCES `Study` (`_Study_key`),
  CONSTRAINT `RefWorkgroup522` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganismStudy`
--

LOCK TABLES `OrganismStudy` WRITE;
/*!40000 ALTER TABLE `OrganismStudy` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrganismStudy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Output`
--

DROP TABLE IF EXISTS `Output`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Output` (
  `_Output_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DataType_key` int(11) NOT NULL,
  `_OutputGroup_key` int(11) NOT NULL,
  `_InputOutputUnit_key` int(11) DEFAULT NULL,
  `_EnumerationClass_key` int(11) DEFAULT NULL,
  `OutputName` varchar(100) NOT NULL,
  `Description` varchar(128) DEFAULT NULL,
  `IsStandardized` tinyint(4) DEFAULT NULL,
  `IsMultiSelectEnumeration` tinyint(4) DEFAULT '0',
  `NumericValidationMin` decimal(38,0) DEFAULT NULL,
  `NumericValidationMax` decimal(38,0) DEFAULT NULL,
  `DateValidationMin` datetime DEFAULT NULL,
  `DateValidationMax` datetime DEFAULT NULL,
  `TimeValidationMin` time DEFAULT NULL,
  `TimeValidationMax` time DEFAULT NULL,
  `TextValidationRegex` varchar(256) DEFAULT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `IsRequired` tinyint(4) DEFAULT NULL,
  `Abbreviation` varchar(100) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Output_key`),
  KEY `Ref6590` (`_DataType_key`),
  KEY `Ref6691` (`_OutputGroup_key`),
  KEY `Ref6792` (`_InputOutputUnit_key`),
  KEY `Ref78138` (`_EnumerationClass_key`),
  KEY `RefWorkgroup523` (`_Workgroup_key`),
  CONSTRAINT `Refcv_DataType90` FOREIGN KEY (`_DataType_key`) REFERENCES `cv_DataType` (`_DataType_key`),
  CONSTRAINT `RefEnumerationClass138` FOREIGN KEY (`_EnumerationClass_key`) REFERENCES `EnumerationClass` (`_EnumerationClass_key`),
  CONSTRAINT `RefInputOutputUnit92` FOREIGN KEY (`_InputOutputUnit_key`) REFERENCES `InputOutputUnit` (`_InputOutputUnit_key`),
  CONSTRAINT `RefOutputGroup91` FOREIGN KEY (`_OutputGroup_key`) REFERENCES `OutputGroup` (`_OutputGroup_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup523` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Output`
--

LOCK TABLES `Output` WRITE;
/*!40000 ALTER TABLE `Output` DISABLE KEYS */;
-- INSERT INTO `Output` VALUES (1,1,1,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(2,2,1,NULL,NULL,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(3,1,1,NULL,NULL,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(4,1,1,NULL,NULL,'Age',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(5,2,1,NULL,NULL,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(6,3,2,NULL,NULL,'Sperm Freeze Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(7,1,3,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(8,2,3,NULL,NULL,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(9,1,3,NULL,NULL,'Male id#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(10,2,3,NULL,NULL,'Coat Color',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(11,3,3,NULL,NULL,'DOB',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',1),(12,1,4,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(13,1,4,NULL,NULL,'Qty of straws',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(14,3,5,NULL,NULL,'IVF Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',0),(15,1,6,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(16,2,6,NULL,NULL,'Sex',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(17,1,6,NULL,NULL,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(18,2,6,NULL,NULL,'Coat Color',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(19,2,6,NULL,NULL,'Genotype',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(20,3,6,NULL,NULL,'DOB',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(21,1,6,NULL,NULL,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(22,1,7,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(23,1,7,NULL,NULL,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(24,1,8,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(25,1,8,NULL,NULL,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(26,1,9,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(27,1,9,NULL,NULL,'Dish#1',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(28,1,10,NULL,NULL,'# of Pseudos',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(29,1,10,NULL,NULL,'# of embryo transfers',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(30,1,11,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(31,1,11,NULL,NULL,'Pseudo#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(32,3,11,NULL,NULL,'Embryo Transfer Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(33,1,12,NULL,NULL,'Stock#','',0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(34,1,12,NULL,NULL,'# of dishes',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(35,1,13,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(36,1,13,NULL,NULL,'Straw or Vial#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(37,1,13,NULL,NULL,'Dish#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(38,1,14,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(39,1,14,NULL,NULL,'Pseudo#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(40,1,14,NULL,NULL,'# of pups',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(41,1,15,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(42,1,15,NULL,NULL,'# of females',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(43,1,15,NULL,NULL,'# of males',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(44,1,15,NULL,NULL,'Wean Date',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(45,1,16,NULL,NULL,'Pseudo#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',0),(46,1,17,NULL,NULL,'Pseudo #',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(47,3,17,NULL,NULL,'Date Sent',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(48,6,18,2,1,'Type of Sample',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(49,2,18,NULL,NULL,'Container Type',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(50,2,18,NULL,NULL,'Conatiner Layout',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(51,3,18,NULL,NULL,'Date Taken',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',0),(52,6,19,NULL,1,'Type of Id',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(53,2,19,NULL,NULL,'Ear Id#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(54,6,19,NULL,2,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(55,3,19,NULL,NULL,'DOB',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(56,2,20,NULL,NULL,'Shpment Type',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(57,2,21,NULL,NULL,'Plate#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(58,2,21,NULL,NULL,'Stock#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(59,2,21,NULL,NULL,'Ear Id#',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(60,6,21,NULL,3,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(61,3,22,NULL,NULL,'Shipment Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',0),(62,3,23,NULL,NULL,'Estimated Ship Date',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(63,1,24,NULL,NULL,'Qty',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(64,2,24,NULL,2,'Sex',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(65,2,24,NULL,1,'Coat Color',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(66,2,24,NULL,NULL,'Genotype',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(67,3,24,NULL,NULL,'DOB',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(68,2,24,NULL,NULL,'Box#',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(69,2,24,NULL,NULL,'Side',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(70,2,25,NULL,NULL,'path',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',0),(71,5,26,NULL,NULL,'ok',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',0),(72,2,26,NULL,NULL,'comment',NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',0),(73,1,28,NULL,NULL,'number of vials',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',1),(74,1,28,NULL,NULL,'passage number',NULL,0,0,'0','0',NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',1);
/*!40000 ALTER TABLE `Output` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutputDefault`
--

DROP TABLE IF EXISTS `OutputDefault`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OutputDefault` (
  `_OutputDefault_key` int(11) NOT NULL AUTO_INCREMENT,
  `_MethodProcedureDefinition_key` int(11) NOT NULL,
  `_Output_key` int(11) NOT NULL,
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  `_Parameter_key` int(11) NOT NULL,
  `NumericValue` decimal(18,0) NOT NULL,
  `TextValue` int(11) NOT NULL,
  `DateValue` date NOT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OutputDefault_key`),
  KEY `Ref64141` (`_Output_key`),
  KEY `Ref92144` (`_MethodProcedureDefinition_key`),
  KEY `Ref77145` (`_EnumerationItem_key`),
  KEY `RefWorkgroup524` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem145` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefMethodProcedureDefinition144` FOREIGN KEY (`_MethodProcedureDefinition_key`) REFERENCES `MethodProcedureDefinition` (`_MethodProcedureDefinition_key`),
  CONSTRAINT `RefOutput141` FOREIGN KEY (`_Output_key`) REFERENCES `Output` (`_Output_key`),
  CONSTRAINT `RefWorkgroup524` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutputDefault`
--

LOCK TABLES `OutputDefault` WRITE;
/*!40000 ALTER TABLE `OutputDefault` DISABLE KEYS */;
/*!40000 ALTER TABLE `OutputDefault` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutputGroup`
--

DROP TABLE IF EXISTS `OutputGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OutputGroup` (
  `_OutputGroup_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `OutputGroup` varchar(100) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OutputGroup_key`),
  KEY `Ref90148` (`_ProcedureDefinitionVersion_key`),
  KEY `RefWorkgroup528` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureDefinitionVersion148` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup528` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutputGroup`
--

LOCK TABLES `OutputGroup` WRITE;
/*!40000 ALTER TABLE `OutputGroup` DISABLE KEYS */;
-- INSERT INTO `OutputGroup` VALUES (1,10,'Receive Mice',1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',1),(2,11,'Sperm Freeze Date',1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',1),(3,14,'Harvest Sperm',1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',2),(4,15,'Load Straws',1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',1),(5,18,'IVF Date',1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',1),(6,20,'IVF',1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',1),(7,21,'IVF Check',1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',1),(8,22,'Change Over',1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',1),(9,23,'2cells for embryo transfer',1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',1),(10,25,'Embryo Transfer',1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',1),(11,26,'Cage Cards',1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',1),(12,27,'Labels for dishes',1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',1),(13,28,'Thaw embryos',1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',1),(14,29,'Record pups born',1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',1),(15,30,'Wean Mice',1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',1),(16,31,'Lily tub prep',1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',1),(17,32,'Pack/Ship Pseudos',1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',1),(18,33,'Sample Mice',1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',1),(19,34,'Id Mice',1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',1),(20,35,'Shiment Type',1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',1),(21,36,'Genotyping Results',1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',1),(22,37,'Shipment Date',1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',1),(23,42,'Estimated Ship Date',1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',1),(24,48,'Pack and Ship Mice',1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',1),(25,51,'path to file',1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',1),(26,52,'customer approval',1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',1),(27,53,'Powerpoint Presentation',11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',0),(28,66,'number of vials ',1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',2);
/*!40000 ALTER TABLE `OutputGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutputInstance`
--

DROP TABLE IF EXISTS `OutputInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OutputInstance` (
  `_OutputInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_OutputInstanceSet_key` int(11) NOT NULL,
  `_Output_key` int(11) NOT NULL,
  `NumericValue` decimal(18,0) DEFAULT NULL,
  `TextValue` varchar(1000) DEFAULT NULL,
  `DateTimeValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OutputInstance_key`),
  KEY `Ref6893` (`_OutputInstanceSet_key`),
  KEY `Ref6494` (`_Output_key`),
  KEY `RefWorkgroup529` (`_Workgroup_key`),
  CONSTRAINT `RefOutput94` FOREIGN KEY (`_Output_key`) REFERENCES `Output` (`_Output_key`),
  CONSTRAINT `RefOutputInstanceSet93` FOREIGN KEY (`_OutputInstanceSet_key`) REFERENCES `OutputInstanceSet` (`_OutputInstanceSet_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup529` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutputInstance`
--

LOCK TABLES `OutputInstance` WRITE;
/*!40000 ALTER TABLE `OutputInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `OutputInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutputInstanceEnumerationItem`
--

DROP TABLE IF EXISTS `OutputInstanceEnumerationItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OutputInstanceEnumerationItem` (
  `_OutputInstanceEnumerationItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_OutputInstance_key` int(11) NOT NULL,
  `_EnumerationItem_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_OutputInstanceEnumerationItem_key`),
  KEY `RefEnumerationItem3` (`_EnumerationItem_key`),
  KEY `RefOutputInstance5` (`_OutputInstance_key`),
  KEY `RefOutputInstanceWk` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem3` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefOutputInstance5` FOREIGN KEY (`_OutputInstance_key`) REFERENCES `OutputInstance` (`_OutputInstance_key`),
  CONSTRAINT `RefOutputInstanceWk` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutputInstanceEnumerationItem`
--

LOCK TABLES `OutputInstanceEnumerationItem` WRITE;
/*!40000 ALTER TABLE `OutputInstanceEnumerationItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `OutputInstanceEnumerationItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OutputInstanceSet`
--

DROP TABLE IF EXISTS `OutputInstanceSet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OutputInstanceSet` (
  `_OutputInstanceSet_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureInstance_key` int(11) NOT NULL,
  `CollectionDateTime` datetime NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OutputInstanceSet_key`),
  KEY `Ref93275` (`_ProcedureInstance_key`),
  KEY `RefWorkgroup530` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureInstance275` FOREIGN KEY (`_ProcedureInstance_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup530` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OutputInstanceSet`
--

LOCK TABLES `OutputInstanceSet` WRITE;
/*!40000 ALTER TABLE `OutputInstanceSet` DISABLE KEYS */;
/*!40000 ALTER TABLE `OutputInstanceSet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlugDate`
--

DROP TABLE IF EXISTS `PlugDate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlugDate` (
  `_PlugDate_key` int(11) NOT NULL AUTO_INCREMENT,
  `_DamsInCross_key` int(11) NOT NULL,
  `_BirthEvent_key` int(11) DEFAULT NULL,
  `PlugDate` datetime NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_PlugDate_key`),
  KEY `Ref3373` (`_DamsInCross_key`),
  KEY `Ref1074` (`_BirthEvent_key`),
  KEY `RefWorkgroup531` (`_Workgroup_key`),
  CONSTRAINT `RefBirthEvent74` FOREIGN KEY (`_BirthEvent_key`) REFERENCES `BirthEvent` (`_BirthEvent_key`),
  CONSTRAINT `RefDamInCross73` FOREIGN KEY (`_DamsInCross_key`) REFERENCES `DamInCross` (`_DamsInCross_key`),
  CONSTRAINT `RefWorkgroup531` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlugDate`
--

LOCK TABLES `PlugDate` WRITE;
/*!40000 ALTER TABLE `PlugDate` DISABLE KEYS */;
/*!40000 ALTER TABLE `PlugDate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Primer`
--

DROP TABLE IF EXISTS `Primer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Primer` (
  `_Primer_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Marker_key` int(11) NOT NULL,
  `3Prime` int(11) NOT NULL,
  `5Prime` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Primer_key`),
  KEY `Ref5886` (`_Marker_key`),
  KEY `RefWorkgroup532` (`_Workgroup_key`),
  CONSTRAINT `RefMarker86` FOREIGN KEY (`_Marker_key`) REFERENCES `Marker` (`_Marker_key`),
  CONSTRAINT `RefWorkgroup532` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Primer`
--

LOCK TABLES `Primer` WRITE;
/*!40000 ALTER TABLE `Primer` DISABLE KEYS */;
/*!40000 ALTER TABLE `Primer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Privilege`
--

DROP TABLE IF EXISTS `Privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Privilege` (
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

--
-- Dumping data for table `Privilege`
--

LOCK TABLES `Privilege` WRITE;
/*!40000 ALTER TABLE `Privilege` DISABLE KEYS */;
INSERT INTO `Privilege` VALUES 
(1,'Read','User is able to select data','dba','2010-10-25 16:41:28','dba','2010-10-25 16:41:28',1),
(2,'Write','User is able to select, insert, update, and delete data','dba','2010-10-25 16:41:28','dba','2010-10-25 16:41:28',1);
/*!40000 ALTER TABLE `Privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureDefinition`
--

DROP TABLE IF EXISTS `ProcedureDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureDefinition` (
  `_ProcedureDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProcedureDefinition` varchar(100) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureDefinition_key`),
  KEY `RefWorkgroup533` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup533` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureDefinition`
--

LOCK TABLES `ProcedureDefinition` WRITE;
/*!40000 ALTER TABLE `ProcedureDefinition` DISABLE KEYS */;
-- INSERT INTO `ProcedureDefinition` VALUES (8,'Enter a Project','Task to enter a new project',1,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',1),(9,'Order Mice','Order males and/or females from the internal/external customer.',1,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',2),(10,'Receive Mice','Receive mice from internal/external customers',1,1,'sbean','2011-02-03 12:36:45','sbean','2011-02-03 12:40:19',2),(11,'Schedule Sperm Freeze','Schedule the sperm freeze',1,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',1),(12,'Order Straws','Place an order with minitube for straws',1,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',1),(13,'Assign Locations','Assign Locations to the straws to put the straws away from the sperm freeze',1,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',1),(14,'Harvest Sperm','Sacrifice the male to harvest sperm',1,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',2),(15,'Load Straws','Load the straws with sperm to  freeze',1,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',1),(16,'Freeze Sperm','Freezing sperm in straws',1,1,'sbean','2011-02-03 13:02:16','sbean','2011-02-03 13:02:16',1),(17,'Store Sperm','Storing Sperm',1,1,'sbean','2011-02-03 13:03:17','sbean','2011-02-03 13:03:26',2),(18,'Schedule IVF','Schedule the IVF ',1,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',1),(19,'Prep Dishes','Prepare dishes for an IVF',1,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',1),(20,'Perform the IVF','Perform the IVF',1,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',1),(21,'Perform the IVF check','Check the dishes in the incubator to see if the sperm is fertilizing',1,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',1),(22,'Perform the Change Over','Perform the change over',1,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',1),(23,'Prep 2 cells for Embryo Transfer','Prepare the 2 cells for embryo transfer',1,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',1),(24,'Schedule Washing','Schedule the washing of 2 cells from the IVF group per strain',1,1,'sbean','2011-02-03 13:53:13','sbean','2011-02-03 13:53:13',1),(25,'Schedule embryo transfers','Schedule the embryo transfers',1,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',1),(26,'Print Cage Cards for Embryo Transfers','Printing cage cards for pseudos when embryo transfers are done',1,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',1),(27,'Print Labels for dishes','Printing labels to put dishes for thaws and washes',1,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',1),(28,'Thaw Embryos','Thawing frozen embryos',1,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',1),(29,'Record Pups Born','Record the number of pups born per embryo transfer',1,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',1),(30,'Wean Mice','Wean mice born ',1,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',1),(31,'Prep lily tubs for LAH','Prep lily tubs to send pseudo moms to be tested for ',1,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',1),(32,'Pack/Ship Pseduos to LAH','SPF would pack and ship the pseudos to LAH for testing',1,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',1),(33,'Sample Mice','Tail tip or ear notch mice for identification ',1,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',1),(34,'Id Mice','When sampling mice they need to be identified by ear notch, ear tags, tattoos or toe clips.',1,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',1),(35,'Submit Samples','Send and submit samples to be typed',1,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',1),(36,'Record Genotyping Results','Record Genotyping Results whether the results are uploaded from TGS, Transnetyx, Laragen or the customer',1,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',1),(37,'Confirm Ship Dates','Confirm the ship dates for mice shipping to internal or external customers',1,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',1),(38,'Print Check Lists for Shipments','Print check lists for shipments of mice to internal and external customers.',1,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',1),(39,'Label Shipping Boxes','Label shipping boxes for shipments for a week',1,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',1),(40,'Print report for mice ready','Print report for mice that are ready to be set up for shipments',1,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',1),(41,'Confirm sex for mice ready to ship','Confirm the sexes for the mice that are ready to be set up for shipment',1,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',1),(42,'Enter Estimated Ship Date','Enter estimate ship dates for mice that are ready for shipments.',1,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',1),(43,'Set up Shipping Boxes','Set up shipping boxes for mice shipping the next day',1,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',1),(44,'Print snapouts','Print snapouts',1,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',1),(45,'Check boxes in Shipping section for food and water','Check boxes in shipping section for food and water',1,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',1),(46,'Enter Shipments in CDT for IMR','Update Central Desktop with shipment information for IMR shipments',1,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',1),(47,'Print Axapta Ship Sheets','Print Axapta ship sheets for G30 and G50',1,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',1),(48,'Ship and Pack Mouse Shipment','Ship and pack mouse shipments for internal or external orders',1,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',1),(49,'PPC orders in Axapta','Pick, pack and confirm orders in Axapta',1,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',1),(50,'Thaw Sperm','Thaw container(s) of sperm',1,1,'sbean','2011-03-18 14:32:54','sbean','2011-03-18 14:32:54',1),(51,'Construct Design','sasas',1,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',1),(52,'Construct Approval','sasas',1,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',1),(53,'Construct Design','',1,11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',1),(54,'Design Approval','This requires a written approval from the PI',1,11,'douglash','2011-04-05 18:02:22','douglash','2011-04-05 18:02:22',1),(55,'Gap Repair','',1,11,'douglash','2011-04-05 18:02:59','douglash','2011-04-05 18:02:59',1),(56,'First Targeting','',1,11,'douglash','2011-04-05 18:03:31','douglash','2011-04-05 18:03:31',1),(57,'Neo Excision','',1,11,'douglash','2011-04-05 18:04:08','douglash','2011-04-05 18:04:08',1),(58,'Second targeting','',1,11,'douglash','2011-04-05 18:04:26','douglash','2011-04-05 18:04:26',1),(59,'Cre test','',1,11,'douglash','2011-04-05 18:04:41','douglash','2011-04-05 18:04:41',1),(60,'FLP test','',1,11,'douglash','2011-04-05 18:04:55','douglash','2011-04-05 18:04:55',1),(61,'Sequence verified','',1,11,'douglash','2011-04-05 18:05:24','douglash','2011-04-05 18:05:24',1),(62,'Plasmid Prep for Electroporation','',1,11,'douglash','2011-04-05 18:06:04','douglash','2011-04-05 18:06:04',1),(63,'Southern Validation','',1,11,'douglash','2011-04-05 18:06:27','douglash','2011-04-05 18:06:27',1),(64,'Initial Southern Screen','',1,11,'douglash','2011-04-05 18:07:01','douglash','2011-04-05 18:07:01',1),(65,'Southern Confirmation','',1,11,'douglash','2011-04-05 18:07:26','douglash','2011-04-05 18:07:26',1),(66,'Derive MEFs','zcdxcz',1,1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',2);
/*!40000 ALTER TABLE `ProcedureDefinition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureDefinitionLocation`
--

DROP TABLE IF EXISTS `ProcedureDefinitionLocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureDefinitionLocation` (
  `_ProcedureDefinitionLocation_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `_Location_key` int(11) NOT NULL,
  `IsDefaultLocation` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureDefinitionLocation_key`),
  KEY `Ref90303` (`_ProcedureDefinitionVersion_key`),
  KEY `Ref114304` (`_Location_key`),
  KEY `RefWorkgroup535` (`_Workgroup_key`),
  CONSTRAINT `RefLocation304` FOREIGN KEY (`_Location_key`) REFERENCES `Location` (`_Location_key`),
  CONSTRAINT `RefProcedureDefinitionVersion303` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup535` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureDefinitionLocation`
--

LOCK TABLES `ProcedureDefinitionLocation` WRITE;
/*!40000 ALTER TABLE `ProcedureDefinitionLocation` DISABLE KEYS */;
-- INSERT INTO `ProcedureDefinitionLocation` VALUES (19,8,11,1,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(23,9,18,1,1,'sbean','2011-02-03 12:37:26','sbean','2011-02-03 12:37:26',0),(24,10,5,1,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(25,10,11,0,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(26,11,11,1,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(27,12,17,1,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',0),(28,13,17,1,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(30,14,13,1,1,'sbean','2011-02-03 12:57:01','sbean','2011-02-03 12:57:01',0),(31,15,13,1,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(32,16,13,1,1,'sbean','2011-02-03 13:02:16','sbean','2011-02-03 13:02:16',0),(34,17,13,1,1,'sbean','2011-02-03 13:03:26','sbean','2011-02-03 13:03:26',0),(35,18,13,1,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',0),(36,19,13,1,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',0),(37,20,13,1,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(38,21,13,1,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(39,22,13,1,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(40,23,13,1,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(41,24,4,1,1,'sbean','2011-02-03 13:53:13','sbean','2011-02-03 13:53:13',0),(42,25,4,1,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(43,26,4,1,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(44,27,4,1,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(45,28,4,1,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(46,29,1,1,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(47,30,1,1,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(48,31,4,1,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',0),(49,32,2,1,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(50,33,11,1,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(51,33,1,0,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(52,33,2,0,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(53,34,1,1,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(54,35,11,0,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(55,35,1,0,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(56,35,2,1,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(57,36,11,0,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(58,36,1,0,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(59,36,2,1,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(60,37,2,1,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',0),(61,38,1,0,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',0),(62,38,2,1,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',0),(63,39,1,0,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',0),(64,39,2,1,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',0),(65,40,1,0,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',0),(66,40,2,1,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',0),(67,41,1,0,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',0),(68,41,2,1,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',0),(69,42,1,0,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(70,42,2,1,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(71,43,1,0,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',0),(72,43,2,1,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',0),(73,44,1,0,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',0),(74,44,2,1,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',0),(75,45,1,0,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',0),(76,45,2,1,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',0),(77,46,1,0,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',0),(78,46,2,1,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',0),(79,47,1,0,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',0),(80,47,2,1,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',0),(81,48,1,0,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(82,48,2,1,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(83,49,17,0,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(84,49,1,0,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(85,49,2,1,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(86,50,17,1,1,'sbean','2011-03-18 14:32:54','sbean','2011-03-18 14:32:54',0),(87,51,9,1,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',0),(88,52,9,1,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',0),(89,53,30,1,11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',0),(90,54,30,1,11,'douglash','2011-04-05 18:02:22','douglash','2011-04-05 18:02:22',0),(91,55,30,1,11,'douglash','2011-04-05 18:02:59','douglash','2011-04-05 18:02:59',0),(92,56,30,1,11,'douglash','2011-04-05 18:03:31','douglash','2011-04-05 18:03:31',0),(93,57,30,1,11,'douglash','2011-04-05 18:04:08','douglash','2011-04-05 18:04:08',0),(94,58,30,1,11,'douglash','2011-04-05 18:04:26','douglash','2011-04-05 18:04:26',0),(95,59,30,1,11,'douglash','2011-04-05 18:04:41','douglash','2011-04-05 18:04:41',0),(96,60,30,1,11,'douglash','2011-04-05 18:04:55','douglash','2011-04-05 18:04:55',0),(97,61,30,1,11,'douglash','2011-04-05 18:05:24','douglash','2011-04-05 18:05:24',0),(98,62,30,1,11,'douglash','2011-04-05 18:06:04','douglash','2011-04-05 18:06:04',0),(99,63,30,1,11,'douglash','2011-04-05 18:06:27','douglash','2011-04-05 18:06:27',0),(100,64,30,1,11,'douglash','2011-04-05 18:07:01','douglash','2011-04-05 18:07:01',0),(101,65,30,1,11,'douglash','2011-04-05 18:07:26','douglash','2011-04-05 18:07:26',0),(103,66,8,1,1,'michaelm','2011-04-06 12:42:42','michaelm','2011-04-06 12:42:42',0);
/*!40000 ALTER TABLE `ProcedureDefinitionLocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureDefinitionResource`
--

DROP TABLE IF EXISTS `ProcedureDefinitionResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureDefinitionResource` (
  `_ProcedureDefinitionResource_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `_Resource_key` int(11) NOT NULL,
  `AveragePerDay` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureDefinitionResource_key`),
  KEY `Ref97121` (`_Resource_key`),
  KEY `Ref90123` (`_ProcedureDefinitionVersion_key`),
  KEY `RefWorkgroup536` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureDefinitionVersion123` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`) ON DELETE CASCADE,
  CONSTRAINT `RefResource121` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup536` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureDefinitionResource`
--

LOCK TABLES `ProcedureDefinitionResource` WRITE;
/*!40000 ALTER TABLE `ProcedureDefinitionResource` DISABLE KEYS */;
-- INSERT INTO `ProcedureDefinitionResource` VALUES (16,8,57,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(17,8,7,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(18,8,55,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(19,8,56,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(20,8,24,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(21,8,62,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(22,8,58,NULL,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',0),(29,9,3,NULL,1,'sbean','2011-02-03 12:37:26','sbean','2011-02-03 12:37:26',0),(30,9,32,NULL,1,'sbean','2011-02-03 12:37:26','sbean','2011-02-03 12:37:26',0),(31,10,40,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(32,10,39,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(33,10,68,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(34,10,84,NULL,1,'sbean','2011-02-03 12:40:19','sbean','2011-02-03 12:40:19',0),(35,11,32,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(36,11,40,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(37,11,1,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(38,11,30,NULL,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',0),(39,12,60,NULL,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',0),(40,13,60,NULL,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',0),(42,14,64,NULL,1,'sbean','2011-02-03 12:57:01','sbean','2011-02-03 12:57:01',0),(43,15,64,NULL,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',0),(44,16,64,NULL,1,'sbean','2011-02-03 13:02:16','sbean','2011-02-03 13:02:16',0),(46,17,64,NULL,1,'sbean','2011-02-03 13:03:26','sbean','2011-02-03 13:03:26',0),(47,18,7,NULL,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',0),(48,18,32,NULL,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',0),(49,19,64,NULL,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',0),(50,20,64,NULL,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',0),(51,21,64,NULL,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',0),(52,22,64,NULL,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',0),(53,23,64,NULL,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',0),(54,24,66,NULL,1,'sbean','2011-02-03 13:53:13','sbean','2011-02-03 13:53:13',0),(55,25,66,NULL,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',0),(56,26,66,NULL,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',0),(57,27,66,NULL,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',0),(58,28,66,NULL,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',0),(59,29,80,NULL,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',0),(60,30,80,NULL,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',0),(61,31,80,NULL,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',0),(62,32,65,NULL,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',0),(63,33,68,NULL,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(64,33,84,NULL,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(65,33,80,NULL,1,'sbean','2011-02-14 10:26:44','sbean','2011-02-14 10:26:44',0),(66,34,68,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(67,34,84,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(68,34,80,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(69,34,65,NULL,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',0),(70,35,68,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(71,35,84,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(72,35,80,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(73,35,65,NULL,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',0),(74,36,68,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(75,36,84,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(76,36,80,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(77,36,65,NULL,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',0),(78,37,80,NULL,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',0),(79,37,65,NULL,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',0),(80,38,80,NULL,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',0),(81,38,65,NULL,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',0),(82,39,80,NULL,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',0),(83,39,65,NULL,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',0),(84,40,80,NULL,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',0),(85,40,65,NULL,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',0),(86,41,80,NULL,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',0),(87,41,65,NULL,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',0),(88,42,80,NULL,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(89,42,65,NULL,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',0),(90,43,80,NULL,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',0),(91,43,65,NULL,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',0),(92,44,80,NULL,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',0),(93,44,65,NULL,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',0),(94,45,80,NULL,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',0),(95,45,65,NULL,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',0),(96,46,80,NULL,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',0),(97,46,65,NULL,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',0),(98,47,80,NULL,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',0),(99,47,65,NULL,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',0),(100,48,80,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(101,48,65,NULL,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',0),(102,49,60,NULL,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(103,49,80,NULL,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(104,49,65,NULL,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',0),(105,50,60,NULL,1,'sbean','2011-03-18 14:32:54','sbean','2011-03-18 14:32:54',0),(106,50,66,NULL,1,'sbean','2011-03-18 14:32:54','sbean','2011-03-18 14:32:54',0),(107,51,79,NULL,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',0),(108,52,79,NULL,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',0),(109,53,90,NULL,11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',0),(110,54,90,NULL,11,'douglash','2011-04-05 18:02:22','douglash','2011-04-05 18:02:22',0),(111,55,90,NULL,11,'douglash','2011-04-05 18:02:59','douglash','2011-04-05 18:02:59',0),(112,56,90,NULL,11,'douglash','2011-04-05 18:03:31','douglash','2011-04-05 18:03:31',0),(113,57,90,NULL,11,'douglash','2011-04-05 18:04:08','douglash','2011-04-05 18:04:08',0),(114,58,90,NULL,11,'douglash','2011-04-05 18:04:26','douglash','2011-04-05 18:04:26',0),(115,59,90,NULL,11,'douglash','2011-04-05 18:04:41','douglash','2011-04-05 18:04:41',0),(116,60,90,NULL,11,'douglash','2011-04-05 18:04:55','douglash','2011-04-05 18:04:55',0),(117,61,90,NULL,11,'douglash','2011-04-05 18:05:24','douglash','2011-04-05 18:05:24',0),(118,62,90,NULL,11,'douglash','2011-04-05 18:06:04','douglash','2011-04-05 18:06:04',0),(119,63,90,NULL,11,'douglash','2011-04-05 18:06:27','douglash','2011-04-05 18:06:27',0),(120,64,90,NULL,11,'douglash','2011-04-05 18:07:01','douglash','2011-04-05 18:07:01',0),(121,65,90,NULL,11,'douglash','2011-04-05 18:07:26','douglash','2011-04-05 18:07:26',0),(123,66,77,NULL,1,'michaelm','2011-04-06 12:42:42','michaelm','2011-04-06 12:42:42',0),(124,66,94,NULL,1,'dba','2011-04-06 12:46:01','dba','2011-04-06 12:46:01',0);
/*!40000 ALTER TABLE `ProcedureDefinitionResource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureDefinitionVersion`
--

DROP TABLE IF EXISTS `ProcedureDefinitionVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureDefinitionVersion` (
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CurrentSOP_key` int(11) DEFAULT NULL,
  `_ProcedureDefinition_key` int(11) NOT NULL,
  `VersionNumber` int(11) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureDefinitionVersion_key`),
  KEY `Ref95117` (`_CurrentSOP_key`),
  KEY `Ref196323` (`_ProcedureDefinition_key`),
  KEY `RefWorkgroup537` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureDefinition323` FOREIGN KEY (`_ProcedureDefinition_key`) REFERENCES `ProcedureDefinition` (`_ProcedureDefinition_key`) ON DELETE CASCADE,
  CONSTRAINT `RefSOP118` FOREIGN KEY (`_CurrentSOP_key`) REFERENCES `SOPVersion` (`_SOPVersion_key`),
  CONSTRAINT `RefWorkgroup537` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureDefinitionVersion`
--

LOCK TABLES `ProcedureDefinitionVersion` WRITE;
/*!40000 ALTER TABLE `ProcedureDefinitionVersion` DISABLE KEYS */;
-- INSERT INTO `ProcedureDefinitionVersion` VALUES (8,NULL,8,1,1,1,'sbean','2011-02-03 12:16:18','sbean','2011-02-03 12:16:18',1),(9,NULL,9,1,1,1,'sbean','2011-02-03 12:29:00','sbean','2011-02-03 12:37:26',2),(10,NULL,10,1,1,1,'sbean','2011-02-03 12:36:45','sbean','2011-02-03 12:40:19',2),(11,NULL,11,1,1,1,'sbean','2011-02-03 12:45:36','sbean','2011-02-03 12:45:36',1),(12,NULL,12,1,1,1,'sbean','2011-02-03 12:50:25','sbean','2011-02-03 12:50:25',1),(13,NULL,13,1,1,1,'sbean','2011-02-03 12:52:14','sbean','2011-02-03 12:52:14',1),(14,NULL,14,1,1,1,'sbean','2011-02-03 12:56:23','sbean','2011-02-03 12:57:01',2),(15,NULL,15,1,1,1,'sbean','2011-02-03 12:59:24','sbean','2011-02-03 12:59:24',1),(16,NULL,16,1,1,1,'sbean','2011-02-03 13:02:16','sbean','2011-02-03 13:02:16',1),(17,NULL,17,1,1,1,'sbean','2011-02-03 13:03:17','sbean','2011-02-03 13:03:26',2),(18,NULL,18,1,1,1,'sbean','2011-02-03 13:05:05','sbean','2011-02-03 13:05:05',1),(19,NULL,19,1,1,1,'sbean','2011-02-03 13:13:54','sbean','2011-02-03 13:13:54',1),(20,NULL,20,1,1,1,'sbean','2011-02-03 13:17:47','sbean','2011-02-03 13:17:47',1),(21,NULL,21,1,1,1,'sbean','2011-02-03 13:18:52','sbean','2011-02-03 13:18:52',1),(22,NULL,22,1,1,1,'sbean','2011-02-03 13:20:03','sbean','2011-02-03 13:20:03',1),(23,NULL,23,1,1,1,'sbean','2011-02-03 13:21:36','sbean','2011-02-03 13:21:36',1),(24,NULL,24,1,1,1,'sbean','2011-02-03 13:53:13','sbean','2011-02-03 13:53:13',1),(25,NULL,25,1,1,1,'sbean','2011-02-03 13:56:28','sbean','2011-02-03 13:56:28',1),(26,NULL,26,1,1,1,'sbean','2011-02-03 14:12:47','sbean','2011-02-03 14:12:47',1),(27,NULL,27,1,1,1,'sbean','2011-02-03 14:22:56','sbean','2011-02-03 14:22:56',1),(28,NULL,28,1,1,1,'sbean','2011-02-03 14:24:22','sbean','2011-02-03 14:24:22',1),(29,NULL,29,1,1,1,'sbean','2011-02-03 14:26:07','sbean','2011-02-03 14:26:07',1),(30,NULL,30,1,1,1,'sbean','2011-02-03 14:32:41','sbean','2011-02-03 14:32:41',1),(31,NULL,31,1,1,1,'sbean','2011-02-03 14:47:51','sbean','2011-02-03 14:47:51',1),(32,NULL,32,1,1,1,'sbean','2011-02-03 15:30:10','sbean','2011-02-03 15:30:10',1),(33,NULL,33,1,1,1,'sbean','2011-02-14 10:18:06','sbean','2011-02-14 10:26:44',1),(34,NULL,34,1,1,1,'sbean','2011-02-14 10:30:30','sbean','2011-02-14 10:30:30',1),(35,NULL,35,1,1,1,'sbean','2011-02-14 10:32:19','sbean','2011-02-14 10:32:19',1),(36,NULL,36,1,1,1,'sbean','2011-02-14 10:36:31','sbean','2011-02-14 10:36:31',1),(37,NULL,37,1,1,1,'sbean','2011-02-21 10:59:16','sbean','2011-02-21 10:59:16',1),(38,NULL,38,1,1,1,'sbean','2011-02-21 13:15:05','sbean','2011-02-21 13:15:05',1),(39,NULL,39,1,1,1,'sbean','2011-02-21 13:15:45','sbean','2011-02-21 13:15:45',1),(40,NULL,40,1,1,1,'sbean','2011-02-21 13:17:33','sbean','2011-02-21 13:17:33',1),(41,NULL,41,1,1,1,'sbean','2011-02-21 13:20:37','sbean','2011-02-21 13:20:37',1),(42,NULL,42,1,1,1,'sbean','2011-02-21 13:24:15','sbean','2011-02-21 13:24:15',1),(43,NULL,43,1,1,1,'sbean','2011-02-21 13:27:05','sbean','2011-02-21 13:27:05',1),(44,NULL,44,1,1,1,'sbean','2011-02-21 13:39:32','sbean','2011-02-21 13:39:32',1),(45,NULL,45,1,1,1,'sbean','2011-02-21 13:40:18','sbean','2011-02-21 13:40:18',1),(46,NULL,46,1,1,1,'sbean','2011-02-21 13:41:15','sbean','2011-02-21 13:41:15',1),(47,NULL,47,1,1,1,'sbean','2011-02-21 14:00:05','sbean','2011-02-21 14:00:05',1),(48,NULL,48,1,1,1,'sbean','2011-02-21 14:04:36','sbean','2011-02-21 14:04:36',1),(49,NULL,49,1,1,1,'sbean','2011-02-21 14:06:02','sbean','2011-02-21 14:06:02',1),(50,NULL,50,1,1,1,'sbean','2011-03-18 14:32:54','sbean','2011-03-18 14:32:54',1),(51,NULL,51,1,1,1,'michaelm','2011-03-31 14:54:37','michaelm','2011-03-31 14:54:37',1),(52,NULL,52,1,1,1,'michaelm','2011-03-31 15:03:27','michaelm','2011-03-31 15:03:27',1),(53,NULL,53,1,1,11,'douglash','2011-03-31 15:40:51','douglash','2011-03-31 15:40:51',1),(54,NULL,54,1,1,11,'douglash','2011-04-05 18:02:22','douglash','2011-04-05 18:02:22',1),(55,NULL,55,1,1,11,'douglash','2011-04-05 18:02:59','douglash','2011-04-05 18:02:59',1),(56,NULL,56,1,1,11,'douglash','2011-04-05 18:03:31','douglash','2011-04-05 18:03:31',1),(57,NULL,57,1,1,11,'douglash','2011-04-05 18:04:08','douglash','2011-04-05 18:04:08',1),(58,NULL,58,1,1,11,'douglash','2011-04-05 18:04:26','douglash','2011-04-05 18:04:26',1),(59,NULL,59,1,1,11,'douglash','2011-04-05 18:04:41','douglash','2011-04-05 18:04:41',1),(60,NULL,60,1,1,11,'douglash','2011-04-05 18:04:55','douglash','2011-04-05 18:04:55',1),(61,NULL,61,1,1,11,'douglash','2011-04-05 18:05:24','douglash','2011-04-05 18:05:24',1),(62,NULL,62,1,1,11,'douglash','2011-04-05 18:06:04','douglash','2011-04-05 18:06:04',1),(63,NULL,63,1,1,11,'douglash','2011-04-05 18:06:27','douglash','2011-04-05 18:06:27',1),(64,NULL,64,1,1,11,'douglash','2011-04-05 18:07:01','douglash','2011-04-05 18:07:01',1),(65,NULL,65,1,1,11,'douglash','2011-04-05 18:07:26','douglash','2011-04-05 18:07:26',1),(66,NULL,66,1,1,1,'michaelm','2011-04-06 12:42:07','michaelm','2011-04-06 12:42:42',2);
/*!40000 ALTER TABLE `ProcedureDefinitionVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureInstance`
--

DROP TABLE IF EXISTS `ProcedureInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureInstance` (
  `_ProcedureInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_OriginatingProcedure_key` int(11) DEFAULT NULL,
  `_ProcedureDefinitionVersion_key` int(11) NOT NULL,
  `_ProcedureStatus_key` int(11) NOT NULL,
  `_ScheduleReason_key` int(11) NOT NULL,
  `_LevelTwoReviewAction_key` int(11) DEFAULT NULL,
  `_LevelOneReviewAction_key` int(11) DEFAULT NULL,
  `_Location_key` int(11) DEFAULT NULL,
  `ProcedureAlias` varchar(200) NOT NULL,
  `DateDue` datetime DEFAULT NULL,
  `DateComplete` datetime DEFAULT NULL,
  `Comments` varchar(2000) DEFAULT NULL,
  `ScheduleReasonDetails` varchar(250) DEFAULT NULL,
  `LevelOneReviewActionDetails` varchar(250) DEFAULT NULL,
  `LevelOneReviewUser` varchar(18) DEFAULT NULL,
  `LevelOneReviewDate` datetime DEFAULT NULL,
  `LevelTwoReviewActionDetails` int(11) DEFAULT NULL,
  `LevelTwoReviewUser` varchar(18) DEFAULT NULL,
  `LevelTwoReviewDate` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `TimeDue` time DEFAULT NULL,
  `TimeComplete` time DEFAULT NULL,
  `_Project_key` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_MethodInstance_key` int(11) DEFAULT NULL,
  `_MethodProcedureDefinition_key` int(11) DEFAULT NULL,
  `IsBillableItem` tinyint(4) NOT NULL,
  PRIMARY KEY (`_ProcedureInstance_key`),
  KEY `Ref90110` (`_ProcedureDefinitionVersion_key`),
  KEY `Ref73319` (`_LevelOneReviewAction_key`),
  KEY `Ref73320` (`_LevelTwoReviewAction_key`),
  KEY `Ref114330` (`_Location_key`),
  KEY `Ref74132` (`_ProcedureStatus_key`),
  KEY `Ref105133` (`_ScheduleReason_key`),
  KEY `Ref93157` (`_OriginatingProcedure_key`),
  KEY `RefWorkgroup538` (`_Workgroup_key`),
  KEY `RefProject295` (`_Project_key`),
  KEY `RefMethodInst385` (`_MethodInstance_key`),
  KEY `RefMethodProcDef` (`_MethodProcedureDefinition_key`),
  CONSTRAINT `Refcv_ProcedureStatus132` FOREIGN KEY (`_ProcedureStatus_key`) REFERENCES `cv_ProcedureStatus` (`_ProcedureStatus_key`),
  CONSTRAINT `Refcv_ReviewAction319` FOREIGN KEY (`_LevelOneReviewAction_key`) REFERENCES `cv_ReviewAction` (`_ReviewAction_key`),
  CONSTRAINT `Refcv_ReviewAction320` FOREIGN KEY (`_LevelTwoReviewAction_key`) REFERENCES `cv_ReviewAction` (`_ReviewAction_key`),
  CONSTRAINT `Refcv_ScheduleReason133` FOREIGN KEY (`_ScheduleReason_key`) REFERENCES `cv_ScheduleReason` (`_ScheduleReason_key`),
  CONSTRAINT `RefLocation330` FOREIGN KEY (`_Location_key`) REFERENCES `Location` (`_Location_key`),
  CONSTRAINT `RefMethodInst385` FOREIGN KEY (`_MethodInstance_key`) REFERENCES `MethodInstance` (`_MethodInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefMethodProcDef` FOREIGN KEY (`_MethodProcedureDefinition_key`) REFERENCES `MethodProcedureDefinition` (`_MethodProcedureDefinition_key`),
  CONSTRAINT `RefProcedureDefinitionVersion110` FOREIGN KEY (`_ProcedureDefinitionVersion_key`) REFERENCES `ProcedureDefinitionVersion` (`_ProcedureDefinitionVersion_key`),
  CONSTRAINT `RefProcedureInstance157` FOREIGN KEY (`_OriginatingProcedure_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`),
  CONSTRAINT `RefProject295` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefWorkgroup538` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureInstance`
--

LOCK TABLES `ProcedureInstance` WRITE;
/*!40000 ALTER TABLE `ProcedureInstance` DISABLE KEYS */;
-- INSERT INTO `ProcedureInstance` VALUES (1,NULL,36,1,1,NULL,NULL,1,'Record Genotyping Results','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,1,1,NULL,0),(2,NULL,29,1,1,NULL,NULL,1,'Record Pups Born','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,2,2,NULL,0),(3,NULL,30,1,1,NULL,NULL,1,'Wean Mice','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,3,2,NULL,0),(4,NULL,31,1,1,NULL,NULL,1,'Prep lily tubs for LAH','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,4,2,NULL,0),(5,NULL,32,1,1,NULL,NULL,1,'Pack/Ship Pseduos to LAH','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,5,2,NULL,0),(6,NULL,34,1,1,NULL,NULL,1,'Id Mice','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,6,3,NULL,0),(7,NULL,33,1,1,NULL,NULL,1,'Sample Mice','2011-03-14 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,7,3,NULL,0),(8,NULL,35,1,1,NULL,NULL,1,'Submit Samples','2011-03-16 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,8,3,NULL,0),(9,NULL,24,1,1,NULL,NULL,1,'Schedule Washing','2011-03-16 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',4,'13:52:00',NULL,1,9,4,NULL,0),(10,NULL,25,1,1,NULL,NULL,1,'Schedule embryo transfers','2011-03-15 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,10,4,NULL,0),(11,NULL,26,1,1,NULL,NULL,1,'Print Cage Cards for Embryo Transfers','2011-03-15 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,11,4,NULL,0),(12,NULL,27,1,1,NULL,NULL,1,'Print Labels for dishes','2011-03-15 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,12,4,NULL,0),(13,NULL,19,1,1,NULL,NULL,1,'Prep Dishes','2011-03-15 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,13,5,NULL,0),(14,NULL,20,1,1,NULL,NULL,1,'Perform the IVF','2011-03-15 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,14,5,NULL,0),(15,NULL,21,1,1,NULL,NULL,1,'Perform the IVF check','2011-03-16 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,15,5,NULL,0),(16,NULL,22,1,1,NULL,NULL,1,'Perform the Change Over','2011-03-17 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,16,5,NULL,0),(17,NULL,23,1,1,NULL,NULL,1,'Prep 2 cells for Embryo Transfer','2011-03-17 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,17,5,NULL,0),(18,NULL,12,1,1,NULL,NULL,1,'Order Straws','2011-03-17 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,18,6,NULL,0),(19,NULL,13,1,1,NULL,NULL,1,'Assign Locations','2011-03-17 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,'13:52:00',NULL,1,19,6,NULL,0),(20,NULL,14,1,1,NULL,NULL,1,'Harvest Sperm',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,NULL,NULL,1,20,6,NULL,0),(21,NULL,15,1,1,NULL,NULL,1,'Load Straws',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,NULL,NULL,1,21,6,NULL,0),(24,NULL,42,1,1,NULL,NULL,1,'Enter Estimated Ship Date',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:25:10','amiller','2011-04-06 08:46:21',5,NULL,NULL,1,22,7,NULL,0),(37,NULL,34,1,1,NULL,NULL,1,'Id Mice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,1,9,NULL,0),(38,NULL,33,1,1,NULL,NULL,11,'Sample Mice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,2,9,NULL,0),(39,NULL,35,1,1,NULL,NULL,11,'Submit Samples',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,3,9,NULL,0),(40,NULL,12,1,1,NULL,NULL,17,'Order Straws',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,4,10,NULL,0),(41,NULL,13,1,1,NULL,NULL,17,'Assign Locations',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,5,10,NULL,0),(42,NULL,14,1,1,NULL,NULL,13,'Harvest Sperm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,6,10,NULL,0),(43,NULL,15,1,1,NULL,NULL,13,'Load Straws',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,7,10,NULL,0),(44,NULL,16,1,1,NULL,NULL,13,'Freeze Sperm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,8,10,NULL,0),(45,NULL,17,1,1,NULL,NULL,13,'Store Sperm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,9,10,NULL,0),(46,NULL,19,1,1,NULL,NULL,13,'Prep Dishes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,10,11,NULL,0),(47,NULL,20,1,1,NULL,NULL,13,'Perform the IVF',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,11,11,NULL,0),(48,NULL,21,1,1,NULL,NULL,13,'Perform the IVF check',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,12,11,NULL,0),(49,NULL,22,1,1,NULL,NULL,13,'Perform the Change Over',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,13,11,NULL,0),(50,NULL,23,1,1,NULL,NULL,13,'Prep 2 cells for Embryo Transfer',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,14,11,NULL,0),(51,NULL,24,1,1,NULL,NULL,4,'Schedule Washing',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,15,12,NULL,0),(52,NULL,25,1,1,NULL,NULL,4,'Schedule embryo transfers',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,16,12,NULL,0),(53,NULL,26,1,1,NULL,NULL,4,'Print Cage Cards for Embryo Transfers',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,17,12,NULL,0),(54,NULL,27,1,1,NULL,NULL,4,'Print Labels for dishes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,18,12,NULL,0),(55,NULL,29,1,1,NULL,NULL,1,'Record Pups Born',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,19,13,NULL,0),(56,NULL,30,1,1,NULL,NULL,1,'Wean Mice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,20,13,NULL,0),(57,NULL,31,1,1,NULL,NULL,4,'Prep lily tubs for LAH',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,21,13,NULL,0),(58,NULL,32,1,1,NULL,NULL,2,'Pack/Ship Pseduos to LAH',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,22,13,NULL,0),(59,NULL,34,1,1,NULL,NULL,1,'Id Mice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,23,14,NULL,0),(60,NULL,33,1,1,NULL,NULL,11,'Sample Mice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,24,14,NULL,0),(61,NULL,35,1,1,NULL,NULL,11,'Submit Samples',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,25,14,NULL,0),(62,NULL,36,1,1,NULL,NULL,11,'Record Genotyping Results',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,26,15,NULL,0),(63,NULL,42,1,1,NULL,NULL,1,'Enter Estimated Ship Date',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,27,16,NULL,0),(64,NULL,37,1,1,NULL,NULL,2,'Confirm Ship Dates',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,28,16,NULL,0),(65,NULL,38,1,1,NULL,NULL,1,'Print Check Lists for Shipments',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,29,16,NULL,0),(66,NULL,40,1,1,NULL,NULL,1,'Print report for mice ready',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,30,16,NULL,0),(67,NULL,41,1,1,NULL,NULL,1,'Confirm sex for mice ready to ship',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,31,16,NULL,0),(68,NULL,39,1,1,NULL,NULL,1,'Label Shipping Boxes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,32,16,NULL,0),(69,NULL,44,1,1,NULL,NULL,1,'Print snapouts',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,33,16,NULL,0),(70,NULL,46,1,1,NULL,NULL,1,'Enter Shipments in CDT for IMR',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,34,16,NULL,0),(71,NULL,45,1,1,NULL,NULL,1,'Check boxes in Shipping section for food and water',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,35,16,NULL,0),(72,NULL,43,1,1,NULL,NULL,1,'Set up Shipping Boxes',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,36,16,NULL,0),(73,NULL,47,1,1,NULL,NULL,1,'Print Axapta Ship Sheets',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,37,17,NULL,0),(74,NULL,48,1,1,NULL,NULL,1,'Ship and Pack Mouse Shipment',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',1,NULL,NULL,2,38,17,NULL,0),(75,NULL,49,1,1,NULL,NULL,17,'PPC orders in Axapta',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'sbean','2011-02-21 14:35:19','sbean','2011-02-21 14:35:19',0,NULL,NULL,2,39,17,NULL,0),(76,NULL,47,3,1,NULL,NULL,1,'Print Axapta Ship Sheets',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:43','deiben','2011-03-29 15:49:31',1,NULL,NULL,3,1,18,NULL,0),(77,NULL,48,1,1,NULL,NULL,1,'Ship and Pack Mouse Shipment',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:43','deiben','2011-03-29 15:49:31',2,NULL,NULL,3,2,18,NULL,0),(78,NULL,49,1,1,NULL,NULL,17,'PPC orders in Axapta',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'deiben','2011-03-29 15:46:43','deiben','2011-03-29 15:49:31',1,NULL,NULL,3,3,18,NULL,0),(79,NULL,28,1,1,NULL,NULL,4,'Thaw Embryos',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','michaelm','2011-03-31 15:10:14',4,NULL,NULL,4,1,19,20,0),(80,NULL,24,1,1,NULL,NULL,4,'Schedule Washing',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','michaelm','2011-03-31 15:10:15',4,NULL,NULL,4,2,19,21,0),(81,NULL,25,1,1,NULL,NULL,4,'Schedule embryo transfers',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','michaelm','2011-03-31 15:10:15',4,NULL,NULL,4,3,19,22,0),(82,NULL,26,1,1,NULL,NULL,4,'Print Cage Cards for Embryo Transfers',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','michaelm','2011-03-31 15:10:15',4,NULL,NULL,4,4,19,23,0),(83,NULL,27,1,1,NULL,NULL,4,'Print Labels for dishes',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:07:50','michaelm','2011-03-31 15:10:15',4,NULL,NULL,4,5,19,24,0),(84,NULL,51,1,1,NULL,NULL,9,'Construct Design',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:10:30','TODO','2011-03-31 15:10:30',1,NULL,NULL,4,6,20,42,0),(85,NULL,52,1,1,NULL,NULL,9,'Construct Approval',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'TODO','2011-03-31 15:10:30','TODO','2011-03-31 15:10:30',1,NULL,NULL,4,7,20,43,0),(86,NULL,53,1,1,NULL,NULL,30,'Construct Design',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',5,NULL,NULL,5,2,21,NULL,0),(87,NULL,54,1,1,NULL,NULL,30,'Design Approval',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,3,21,NULL,0),(88,NULL,55,1,1,NULL,NULL,30,'Gap Repair',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,4,21,NULL,0),(89,NULL,56,1,1,NULL,NULL,30,'First Targeting',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,5,21,NULL,0),(90,NULL,57,1,1,NULL,NULL,30,'Neo Excision',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,6,21,NULL,0),(91,NULL,58,1,1,NULL,NULL,30,'Second targeting',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,7,21,NULL,0),(92,NULL,59,1,1,NULL,NULL,30,'Cre test',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,8,21,NULL,0),(93,NULL,60,1,1,NULL,NULL,30,'FLP test',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,9,21,NULL,0),(94,NULL,61,1,1,NULL,NULL,30,'Sequence verified',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,10,21,NULL,0),(95,NULL,63,1,1,NULL,NULL,30,'Southern Validation',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,11,21,NULL,0),(96,NULL,62,1,1,NULL,NULL,30,'Plasmid Prep for Electroporation',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,12,21,NULL,0),(97,NULL,64,1,1,NULL,NULL,30,'Initial Southern Screen',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,13,21,NULL,0),(98,NULL,65,1,1,NULL,NULL,30,'Southern Confirmation',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:24:45','douglash','2011-04-05 18:32:38',4,NULL,NULL,5,14,21,NULL,0),(99,NULL,53,1,1,NULL,NULL,30,'Construct Design',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'douglash','2011-04-05 18:27:50','douglash','2011-04-05 18:32:38',3,NULL,NULL,5,1,NULL,NULL,0),(101,NULL,11,1,1,NULL,NULL,11,'Schedule Sperm Freeze',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'amiller','2011-04-06 08:45:34','amiller','2011-04-06 08:46:21',2,NULL,NULL,1,23,NULL,NULL,0),(119,NULL,9,2,1,NULL,NULL,18,'Order Mice','2011-04-29 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',5,'23:15:00',NULL,10,1,28,59,0),(120,NULL,66,2,1,NULL,NULL,8,'Derive MEFs','2011-04-29 00:00:00',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,1,'michaelm','2011-04-06 12:56:36','michaelm','2011-04-06 13:04:06',5,'23:15:00',NULL,10,2,28,60,0);
/*!40000 ALTER TABLE `ProcedureInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureInstanceOrganism`
--

DROP TABLE IF EXISTS `ProcedureInstanceOrganism`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureInstanceOrganism` (
  `_ProcedureInstanceOrganism_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureInstance_key` int(11) NOT NULL,
  `_OrganismName_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureInstanceOrganism_key`),
  KEY `Ref93126` (`_ProcedureInstance_key`),
  KEY `Ref28134` (`_OrganismName_key`),
  KEY `RefWorkgroup539` (`_Workgroup_key`),
  CONSTRAINT `RefOrganismName134` FOREIGN KEY (`_OrganismName_key`) REFERENCES `OrganismName` (`_OrganismName_key`),
  CONSTRAINT `RefProcedureInstance126` FOREIGN KEY (`_ProcedureInstance_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup539` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureInstanceOrganism`
--

LOCK TABLES `ProcedureInstanceOrganism` WRITE;
/*!40000 ALTER TABLE `ProcedureInstanceOrganism` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProcedureInstanceOrganism` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureInstanceResource`
--

DROP TABLE IF EXISTS `ProcedureInstanceResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureInstanceResource` (
  `_ProcedureInstanceResource_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureInstance_key` int(11) NOT NULL,
  `_Resource_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureInstanceResource_key`),
  KEY `Ref93281` (`_ProcedureInstance_key`),
  KEY `Ref97282` (`_Resource_key`),
  KEY `RefWorkgroup540` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureInstance281` FOREIGN KEY (`_ProcedureInstance_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefResource282` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`),
  CONSTRAINT `RefWorkgroup540` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureInstanceResource`
--

LOCK TABLES `ProcedureInstanceResource` WRITE;
/*!40000 ALTER TABLE `ProcedureInstanceResource` DISABLE KEYS */;
-- INSERT INTO `ProcedureInstanceResource` VALUES (2,1,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(3,2,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(4,3,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(5,4,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(6,5,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(7,6,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(8,7,2,1,'TODO','2011-03-16 13:57:27','TODO','2011-03-16 13:57:27',1),(9,10,2,1,'TODO','2011-03-16 13:57:38','TODO','2011-03-16 13:57:38',1),(10,11,2,1,'TODO','2011-03-16 13:57:38','TODO','2011-03-16 13:57:38',1),(11,12,2,1,'TODO','2011-03-16 13:57:38','TODO','2011-03-16 13:57:38',1),(12,13,2,1,'TODO','2011-03-16 13:57:38','TODO','2011-03-16 13:57:38',1),(13,14,2,1,'TODO','2011-03-16 13:57:38','TODO','2011-03-16 13:57:38',1),(14,8,2,1,'TODO','2011-03-16 13:57:46','TODO','2011-03-16 13:57:46',1),(15,9,2,1,'TODO','2011-03-16 13:57:46','TODO','2011-03-16 13:57:46',1),(16,15,2,1,'TODO','2011-03-16 13:57:46','TODO','2011-03-16 13:57:46',1),(17,16,2,1,'TODO','2011-03-16 13:57:55','TODO','2011-03-16 13:57:55',1),(18,17,2,1,'TODO','2011-03-16 13:57:55','TODO','2011-03-16 13:57:55',1),(19,18,2,1,'TODO','2011-03-16 13:57:55','TODO','2011-03-16 13:57:55',1),(20,19,2,1,'TODO','2011-03-16 13:57:55','TODO','2011-03-16 13:57:55',1),(21,119,94,1,'michaelm','2011-04-06 13:01:12','michaelm','2011-04-06 13:01:12',1),(22,120,94,1,'michaelm','2011-04-06 13:01:12','michaelm','2011-04-06 13:01:12',1);
/*!40000 ALTER TABLE `ProcedureInstanceResource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcedureInstanceSample`
--

DROP TABLE IF EXISTS `ProcedureInstanceSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcedureInstanceSample` (
  `_ProcedureInstanceSample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ProcedureInstance_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProcedureInstanceSample_key`),
  KEY `Ref93113` (`_ProcedureInstance_key`),
  KEY `Ref49130` (`_Sample_key`),
  KEY `RefWorkgroup541` (`_Workgroup_key`),
  CONSTRAINT `RefProcedureInstance113` FOREIGN KEY (`_ProcedureInstance_key`) REFERENCES `ProcedureInstance` (`_ProcedureInstance_key`) ON DELETE CASCADE,
  CONSTRAINT `RefSample130` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup541` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcedureInstanceSample`
--

LOCK TABLES `ProcedureInstanceSample` WRITE;
/*!40000 ALTER TABLE `ProcedureInstanceSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProcedureInstanceSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `_Project_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ServiceOrder_key` int(11) DEFAULT NULL,
  `_ProjectStatus_key` int(11) NOT NULL,
  `_ProjectType_key` int(11) NOT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `_Resource_key` int(11) DEFAULT NULL,
  `ProjectID` varchar(25) NOT NULL,
  `ExternalProjectID` varchar(45) DEFAULT NULL,
  `DateStarted` datetime DEFAULT NULL,
  `DateEnded` datetime DEFAULT NULL,
  `ProjectGoal` varchar(1024) DEFAULT NULL,
  `ProjectNotes` varchar(1024) DEFAULT NULL,
  `AccountNumber` varchar(18) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_NameFamily_key` int(11) NOT NULL,
  `_Institution_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_Project_key`),
  KEY `Ref144214` (`_ProjectStatus_key`),
  KEY `Ref145215` (`_ProjectType_key`),
  KEY `Ref35217` (`_Line_key`),
  KEY `Ref125218` (`_ServiceOrder_key`),
  KEY `RefNameFamily362` (`_NameFamily_key`),
  KEY `RefWorkgroup542` (`_Workgroup_key`),
  KEY `RefInstitution1` (`_Institution_key`),
  KEY `FK_Project_8` (`_Resource_key`),
  CONSTRAINT `FK_Project_8` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`),
  CONSTRAINT `Refcv_ProjectStatus214` FOREIGN KEY (`_ProjectStatus_key`) REFERENCES `cv_ProjectStatus` (`_ProjectStatus_key`),
  CONSTRAINT `Refcv_ProjectType215` FOREIGN KEY (`_ProjectType_key`) REFERENCES `cv_ProjectType` (`_ProjectType_key`),
  CONSTRAINT `RefInstitution1` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`),
  CONSTRAINT `RefLine217` FOREIGN KEY (`_Line_key`) REFERENCES `Line` (`_Line_key`),
  CONSTRAINT `RefNameFamily362` FOREIGN KEY (`_NameFamily_key`) REFERENCES `NameFamily` (`_NameFamily_key`),
  CONSTRAINT `RefServiceOrder218` FOREIGN KEY (`_ServiceOrder_key`) REFERENCES `ServiceOrder` (`_ServiceOrder_key`),
  CONSTRAINT `RefWorkgroup542` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
-- INSERT INTO `Project` VALUES (1,NULL,1,1,5,NULL,'1',NULL,'2011-02-21 00:00:00',NULL,'Sperm Cryo with QCL confirm genotype by sending tail tips to TGS and ship mice to the room to start a colony','',NULL,1,'sbean','2011-02-21 00:00:00','amiller','0001-01-01 00:00:00',14,1,NULL),(2,NULL,1,1,9,NULL,'2',NULL,'2011-02-22 00:00:00',NULL,'Receive mice in Importation, sample mice upon arrival and then sperm cryo with QCL, TGS typing mice, shipping mice to room ','',NULL,1,'sbean','2011-02-21 00:00:00','sbean','2011-02-21 00:00:00',4,1,NULL),(3,NULL,1,5,7,NULL,'3',NULL,'2011-03-29 00:00:00','2012-03-08 00:00:00','','',NULL,1,'deiben','2011-03-29 00:00:00','deiben','2011-03-29 00:00:00',4,1,NULL),(4,NULL,1,1,3888,NULL,'4',NULL,'2011-03-31 00:00:00','2011-04-29 00:00:00','some goal','some notes',NULL,1,'TODO','2011-03-31 15:07:01','TODO','0001-01-01 00:00:00',5,1,NULL),(5,NULL,1,5,49,NULL,'5',NULL,'2011-04-05 00:00:00',NULL,'Make floxed allele of BAX','11-DAH-01',NULL,11,'douglash','2011-04-05 00:00:00','douglash','2011-04-05 00:00:00',17,1,NULL),(6,NULL,1,1,4774,NULL,'5',NULL,'2011-04-23 00:00:00','2011-05-27 00:00:00','znckjxznczjkxcn','mlopkiiejdweoidjwem  kkkkmmmmm',NULL,1,'michaelm','2011-04-06 07:14:01','michaelm','0001-01-01 00:00:00',6,1,NULL),(7,NULL,1,1,4790,NULL,'7',NULL,NULL,NULL,'Mike&#39;s Test 06-Apr-11 08.50 am','',NULL,1,'mrelac','2011-04-06 08:50:28','mrelac','2011-04-06 08:50:28',1,1,NULL),(9,NULL,1,1,2432,NULL,'8',NULL,NULL,NULL,'','',NULL,1,'amiller','2011-04-06 11:19:07','amiller','2011-04-06 11:19:07',1,1,NULL),(10,NULL,1,3,1451,NULL,'10',NULL,'2011-04-21 00:00:00','2011-05-25 00:00:00','scdcdcc','zxcxzczc',NULL,1,'michaelm','2011-04-06 12:56:24','michaelm','0001-01-01 00:00:00',5,1,NULL);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectContactPerson`
--

DROP TABLE IF EXISTS `ProjectContactPerson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectContactPerson` (
  `_ProjectContactPerson_key` int(11) NOT NULL,
  `_Project_key` int(11) NOT NULL,
  `_ContactPerson_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProjectContactPerson_key`),
  KEY `RefProject382` (`_Project_key`),
  KEY `RefContactPerson383` (`_ContactPerson_key`),
  KEY `RefWorkgroup544` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson383` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `RefProject382` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefWorkgroup544` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectContactPerson`
--

LOCK TABLES `ProjectContactPerson` WRITE;
/*!40000 ALTER TABLE `ProjectContactPerson` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectContactPerson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectOrganism`
--

DROP TABLE IF EXISTS `ProjectOrganism`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectOrganism` (
  `_ProjectOrganism_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Project_key` int(11) NOT NULL,
  `_Organism_key` char(10) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProjectOrganism_key`),
  KEY `Ref142203` (`_Project_key`),
  KEY `RefWorkgroup545` (`_Workgroup_key`),
  CONSTRAINT `RefProject203` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefWorkgroup545` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectOrganism`
--

LOCK TABLES `ProjectOrganism` WRITE;
/*!40000 ALTER TABLE `ProjectOrganism` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectOrganism` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectSample`
--

DROP TABLE IF EXISTS `ProjectSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectSample` (
  `_ProjectSample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Project_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ProjectSample_key`),
  KEY `Ref142202` (`_Project_key`),
  KEY `Ref49302` (`_Sample_key`),
  KEY `RefWorkgroup547` (`_Workgroup_key`),
  CONSTRAINT `RefProject202` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefSample302` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup547` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectSample`
--

LOCK TABLES `ProjectSample` WRITE;
/*!40000 ALTER TABLE `ProjectSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProjectSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QueryDefinition`
--

DROP TABLE IF EXISTS `QueryDefinition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QueryDefinition` (
  `_QueryDefinition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_User_key` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) DEFAULT NULL,
  `QueryName` varchar(45) DEFAULT NULL,
  `QueryOptions` text COMMENT 'This holds the text that represents XML serialized from the query page managed bean.',
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

--
-- Dumping data for table `QueryDefinition`
--

LOCK TABLES `QueryDefinition` WRITE;
/*!40000 ALTER TABLE `QueryDefinition` DISABLE KEYS */;
/*!40000 ALTER TABLE `QueryDefinition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Resource`
--

DROP TABLE IF EXISTS `Resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Resource` (
  `_Resource_key` int(11) NOT NULL AUTO_INCREMENT,
  `ResourceName` varchar(128) DEFAULT NULL,
  `Comments` varchar(5000) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_ResourceType_key` int(11) DEFAULT NULL,
  `IsGroup` tinyint(4) NOT NULL,
  `IsUser` tinyint(4) NOT NULL,
  `_User_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_Resource_key`),
  KEY `Ref181331` (`_Workgroup_key`),
  KEY `RefResource285` (`_ResourceType_key`),
  KEY `RefResource2967` (`_User_key`) USING BTREE,
  CONSTRAINT `RefResource285` FOREIGN KEY (`_ResourceType_key`) REFERENCES `cv_ResourceType` (`_ResourceType_key`),
  CONSTRAINT `RefResource296` FOREIGN KEY (`_User_key`) REFERENCES `User` (`_User_key`),
  CONSTRAINT `RefWorkgroup331` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Resource`
--

LOCK TABLES `Resource` WRITE;
/*!40000 ALTER TABLE `Resource` DISABLE KEYS */;
-- INSERT INTO `Resource` VALUES (1,'Myrna Parker',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(2,'Alyce Young',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(3,'Andy Carlson',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(4,'Claire Getchell',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(5,'Bree Sargent',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(6,'Suzan Payson',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(7,'Carol Sargent',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(8,'Ian Dunn',NULL,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,1,0,1,NULL),(12,'Ezra O’Connor',NULL,1,2,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(13,'Jen Littlefield',NULL,1,2,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(14,'Kathi Leathers',NULL,1,2,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(15,'Michael Walden',NULL,1,2,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(16,'Jennifer Bodoh',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(17,'Aaron Amper',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(18,'Louisa Barnhart',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(19,'Sandra Ramirez',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(20,'DEXA',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,3,0,0,NULL),(21,'BUXCO',NULL,1,3,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,3,0,0,NULL),(22,'Dave Walton',NULL,1,4,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(23,'Richard Farley',NULL,1,1,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(24,'Jen Richardson',NULL,1,1,'dba','2010-10-25 16:41:44','dba','2010-10-25 16:41:44',1,1,0,1,NULL),(25,'Craig Hanna','',1,4,'dba','2010-10-26 08:18:50','dba','2010-10-26 08:18:50',0,1,0,1,NULL),(26,'Terri Stratton-Linscott','',1,1,'cleduc','2010-11-02 13:44:38','cleduc','2010-11-02 13:59:48',1,1,0,1,NULL),(28,'Debbie Mayo','',1,1,'cleduc','2010-11-02 13:49:19','cleduc','2010-11-02 13:58:14',1,1,0,1,NULL),(29,'Sandra Rodick','',1,1,'cleduc','2010-11-02 13:49:47','cleduc','2010-11-02 13:59:33',1,1,0,1,NULL),(30,'Paul Williams','',1,1,'cleduc','2010-11-02 13:51:27','cleduc','2010-11-02 13:59:24',1,1,0,1,NULL),(31,'Lindsay Ware','',1,1,'cleduc','2010-11-02 13:51:48','cleduc','2010-11-02 13:59:06',1,1,0,1,NULL),(32,'Jane Farley','',1,1,'cleduc','2010-11-02 13:52:07','cleduc','2010-11-02 13:52:07',0,1,0,1,NULL),(34,'Chris Dion','',1,1,'cleduc','2010-11-02 13:52:29','cleduc','2010-11-02 13:52:29',0,1,0,1,NULL),(35,'Chris Russell','',1,1,'cleduc','2010-11-02 13:52:37','cleduc','2010-11-02 13:52:37',0,1,0,1,NULL),(36,'Mia Bowden','',1,1,'cleduc','2010-11-02 13:52:46','cleduc','2010-11-02 13:52:46',0,1,0,1,NULL),(37,'Steven Griswold','',1,1,'cleduc','2010-11-02 13:53:02','cleduc','2010-11-02 13:53:02',0,1,0,1,NULL),(38,'Laura Hendrix','',1,1,'cleduc','2010-11-02 13:53:14','cleduc','2010-11-02 13:53:14',0,1,0,1,NULL),(39,'Jim Kikel','',1,1,'cleduc','2010-11-02 13:53:20','cleduc','2010-11-02 13:53:20',0,1,0,1,NULL),(40,'Jeannie Jamo','',1,1,'cleduc','2010-11-02 13:53:32','cleduc','2010-11-02 13:53:32',0,1,0,1,NULL),(41,'Marie Hooper','',1,1,'cleduc','2010-11-02 13:53:40','cleduc','2010-11-02 13:53:40',0,1,0,1,NULL),(42,'Melissa Emery','',1,1,'cleduc','2010-11-02 13:53:49','cleduc','2010-11-02 13:53:49',0,1,0,1,NULL),(43,'Bret Tallent','',1,1,'cleduc','2010-11-02 13:53:57','cleduc','2010-11-02 13:53:57',0,1,0,1,36),(44,'Sarah Ingalls','',1,1,'cleduc','2010-11-02 13:54:06','cleduc','2010-11-02 13:54:06',0,1,0,1,NULL),(45,'Doug Howell','',1,1,'cleduc','2010-11-02 13:54:17','cleduc','2010-11-12 13:30:17',1,1,0,1,NULL),(48,'Kurt Christiansen','',1,1,'cleduc','2010-11-02 13:55:38','cleduc','2010-11-02 13:55:38',0,1,0,1,NULL),(49,'Michelle Cote','',1,1,'cleduc','2010-11-02 13:55:46','cleduc','2010-11-02 13:55:46',0,1,0,1,NULL),(50,'Becky Pavloff','',1,1,'cleduc','2010-11-02 13:56:12','cleduc','2010-11-02 13:57:02',1,1,0,1,NULL),(51,'Dan Myrick','',1,1,'cleduc','2010-11-12 12:59:52','cleduc','2010-11-12 12:59:52',0,1,0,1,NULL),(52,'Elisha Bannister','',1,1,'cleduc','2010-11-12 13:00:04','cleduc','2010-11-12 13:00:17',1,1,0,1,NULL),(53,'Dave Cottle','',1,1,'cleduc','2010-11-12 13:00:29','cleduc','2010-11-12 13:00:29',0,1,0,1,NULL),(54,'Candice Braley','',1,1,'cleduc','2010-11-12 13:00:51','cleduc','2010-11-12 13:00:51',0,1,0,1,NULL),(55,'Christa Starling','',1,1,'cleduc','2010-11-12 13:01:03','cleduc','2010-11-12 13:01:03',0,1,0,1,NULL),(56,'Ellen Savage','',1,1,'cleduc','2010-11-12 13:01:11','cleduc','2010-11-12 13:01:11',0,1,0,1,NULL),(57,'Alice Walls','',1,1,'cleduc','2010-11-12 13:01:24','cleduc','2010-11-12 13:01:24',0,1,0,1,NULL),(58,'Tiffany Strout','',1,1,'cleduc','2010-11-12 13:01:34','cleduc','2010-11-12 13:01:34',0,1,0,1,NULL),(60,'Bio Bank','',1,1,'cleduc','2010-11-12 13:02:50','cleduc','2010-11-12 13:44:04',2,2,1,0,NULL),(61,'RS PMs','',1,1,'cleduc','2010-11-12 13:10:02','cleduc','2010-11-12 13:39:45',2,2,1,0,NULL),(62,'Kat Taylor','',1,1,'cleduc','2010-11-12 13:10:11','cleduc','2010-11-12 13:10:11',0,1,0,1,NULL),(64,'IVF','',1,1,'cleduc','2010-11-12 13:16:19','cleduc','2010-11-12 13:44:19',2,2,1,0,NULL),(65,'SPF Colony Managers','',1,1,'cleduc','2010-11-12 13:17:48','cleduc','2010-11-12 13:39:10',1,2,1,0,NULL),(66,'Embryo Transfers','',1,1,'cleduc','2010-11-12 13:19:13','cleduc','2010-11-12 13:57:41',3,2,1,0,NULL),(67,'Tacy Robb','',1,1,'cleduc','2010-11-12 13:19:33','cleduc','2010-11-12 13:19:33',0,1,0,1,NULL),(68,'Quarantine','',1,1,'cleduc','2010-11-12 13:20:55','cleduc','2010-11-15 10:11:41',1,2,1,0,NULL),(69,'Robert Jones','',1,1,'cleduc','2010-11-12 13:21:04','cleduc','2010-11-12 13:21:04',0,1,0,1,NULL),(70,'Marjorie Rankin','',1,1,'cleduc','2010-11-12 13:21:12','cleduc','2010-11-12 13:21:12',0,1,0,1,NULL),(71,'Allen Sawyer','',1,1,'cleduc','2010-11-12 13:21:19','cleduc','2010-11-12 13:21:19',0,1,0,1,NULL),(72,'Maryann Pitts','',1,1,'cleduc','2010-11-12 13:21:26','cleduc','2010-11-12 13:21:26',0,1,0,1,NULL),(73,'Lisa Freeman','',1,1,'cleduc','2010-11-12 13:23:10','cleduc','2010-11-12 13:23:10',0,1,0,1,NULL),(74,'Yayoi Kimura','',1,1,'cleduc','2010-11-12 13:30:02','cleduc','2010-11-12 13:30:02',0,1,0,1,NULL),(75,'Teresa Rohner','',1,1,'cleduc','2010-11-12 13:30:39','cleduc','2010-11-12 13:30:39',0,1,0,1,NULL),(76,'Shannon Byers','',1,1,'cleduc','2010-11-12 13:31:06','cleduc','2010-11-12 13:31:06',0,1,0,1,NULL),(77,'Rob Taft','',1,1,'cleduc','2010-11-12 13:31:14','cleduc','2010-11-12 13:31:14',0,1,0,1,11),(78,'Sadie Murdie','',1,1,'cleduc','2010-11-12 13:31:23','cleduc','2010-11-12 13:31:23',0,1,0,1,NULL),(79,' RS R&D','',1,1,'cleduc','2010-11-12 13:31:56','cleduc','2010-11-12 13:31:56',0,2,1,0,NULL),(80,'SPF','',1,1,'cleduc','2010-11-12 13:40:47','cleduc','2010-11-12 13:40:47',0,2,1,0,NULL),(81,'Leanne York','',1,1,'cleduc','2010-11-12 13:43:19','cleduc','2010-11-12 13:43:19',0,1,0,1,NULL),(84,'Quarantine Colony Managers','',1,1,'cleduc','2010-11-15 10:13:17','cleduc','2010-11-15 10:13:17',0,2,1,0,NULL),(85,'Susan Bean','',1,1,'cleduc','2011-02-03 12:29:29','cleduc','2011-02-03 12:29:29',0,1,0,1,12),(86,'Kathy Norwood','',1,1,'cleduc','2011-02-03 12:29:54','cleduc','2011-02-03 12:29:54',0,1,0,1,NULL),(87,'Carrie LeDuc','',1,1,'cleduc','2011-02-03 12:30:12','cleduc','2011-02-03 12:30:12',0,1,0,1,6),(90,'MBS Staff','',1,11,'amiller','2011-03-31 15:38:07','amiller','2011-03-31 15:38:07',0,1,0,1,NULL),(91,'ScrumMaster','No explanation needed',1,1,'dba','2011-04-05 07:45:17','dba','2011-04-05 07:45:17',0,4,0,0,NULL),(92,'Joel Hyde','SQA',1,1,'dba','2011-04-05 07:53:56','dba','2011-04-05 07:53:56',0,4,0,0,NULL),(93,'Task validator','one who will validate the task',1,1,'dba','2011-04-05 08:10:28','dba','2011-04-05 08:10:28',0,4,0,0,NULL),(94,'Charlie Learner','',1,1,'dba','2011-04-06 12:46:05','dba','2011-04-06 12:46:05',0,4,0,0,NULL);
/*!40000 ALTER TABLE `Resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceCharacteristic`
--

DROP TABLE IF EXISTS `ResourceCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceCharacteristic` (
  `_ResourceCharacteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ResourceType_key` int(11) NOT NULL,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ResourceCharacteristic_key`),
  KEY `Ref179283` (`_ResourceType_key`),
  KEY `Ref155284` (`_CharacteristicVersion_key`),
  KEY `RefWorkgroup549` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion284` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_ResourceType283` FOREIGN KEY (`_ResourceType_key`) REFERENCES `cv_ResourceType` (`_ResourceType_key`),
  CONSTRAINT `RefWorkgroup549` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceCharacteristic`
--

LOCK TABLES `ResourceCharacteristic` WRITE;
/*!40000 ALTER TABLE `ResourceCharacteristic` DISABLE KEYS */;
-- INSERT INTO `ResourceCharacteristic` VALUES (1,1,2,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(2,1,3,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(3,1,4,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(4,1,5,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(5,2,6,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1);
/*!40000 ALTER TABLE `ResourceCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceCharacteristicInstance`
--

DROP TABLE IF EXISTS `ResourceCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceCharacteristicInstance` (
  `_ResourceCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ResourceCharacteristic_key` int(11) NOT NULL,
  `_Resource_key` int(11) NOT NULL,
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,10) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(1) DEFAULT NULL,
  `DateStart` datetime DEFAULT NULL,
  `DateEnd` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ResourceCharacteristicInstance_key`),
  KEY `RefResourceCharacteristic380` (`_ResourceCharacteristic_key`),
  KEY `RefWorkgroup550` (`_Workgroup_key`),
  KEY `RefResource381` (`_Resource_key`),
  CONSTRAINT `RefResource381` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`) ON DELETE CASCADE,
  CONSTRAINT `RefResourceCharacteristic380` FOREIGN KEY (`_ResourceCharacteristic_key`) REFERENCES `ResourceCharacteristic` (`_ResourceCharacteristic_key`),
  CONSTRAINT `RefWorkgroup550` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceCharacteristicInstance`
--

LOCK TABLES `ResourceCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `ResourceCharacteristicInstance` DISABLE KEYS */;
-- INSERT INTO `ResourceCharacteristicInstance` VALUES (1,1,1,NULL,'Myrna',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(2,2,1,NULL,'Parker',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(3,1,2,NULL,'Alice',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(4,2,2,NULL,'Young',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(5,1,3,NULL,'Andy',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(6,2,3,NULL,'Carlson',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(7,1,4,NULL,'Claire',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(8,2,4,NULL,'Getchell',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(9,1,5,NULL,'Bree',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(10,2,5,NULL,'Sargent',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(11,1,6,NULL,'Suzan',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(12,2,6,NULL,'Pason',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(13,1,7,NULL,'Carol',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(14,2,7,NULL,'Sargent',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(15,1,8,NULL,'Ian',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(16,2,8,NULL,'Dunn',NULL,NULL,NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(17,3,1,NULL,NULL,NULL,'0001-01-01',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(18,3,2,NULL,NULL,NULL,'0002-02-02',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(19,3,3,NULL,NULL,NULL,'0003-03-03',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(20,3,4,NULL,NULL,NULL,'0004-04-04',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(21,3,5,NULL,NULL,NULL,'0005-05-05',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(22,3,6,NULL,NULL,NULL,'0006-06-06',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(23,3,7,NULL,NULL,NULL,'0007-07-07',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1),(24,3,8,NULL,NULL,NULL,'0008-08-08',NULL,NULL,NULL,NULL,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1);
/*!40000 ALTER TABLE `ResourceCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceGroupMember`
--

DROP TABLE IF EXISTS `ResourceGroupMember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceGroupMember` (
  `_ResourceGroupMember_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Resource_key` int(11) NOT NULL,
  `_ParentResource_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ResourceGroupMember_key`),
  KEY `Ref97291` (`_Resource_key`),
  KEY `Ref97292` (`_ParentResource_key`),
  KEY `RefWorkgroup551` (`_Workgroup_key`),
  CONSTRAINT `RefResource291` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`) ON DELETE CASCADE,
  CONSTRAINT `RefResource292` FOREIGN KEY (`_ParentResource_key`) REFERENCES `Resource` (`_Resource_key`) ON DELETE CASCADE,
  CONSTRAINT `RefWorkgroup551` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceGroupMember`
--

LOCK TABLES `ResourceGroupMember` WRITE;
/*!40000 ALTER TABLE `ResourceGroupMember` DISABLE KEYS */;
-- INSERT INTO `ResourceGroupMember` VALUES (10,37,60,1,'cleduc','2010-11-12 13:08:01','cleduc','2010-11-12 13:08:01',0),(11,35,60,1,'cleduc','2010-11-12 13:08:12','cleduc','2010-11-12 13:08:12',0),(12,49,60,1,'cleduc','2010-11-12 13:08:27','cleduc','2010-11-12 13:08:27',0),(13,48,60,1,'cleduc','2010-11-12 13:08:32','cleduc','2010-11-12 13:08:32',0),(14,50,60,1,'cleduc','2010-11-12 13:08:39','cleduc','2010-11-12 13:08:39',0),(15,24,60,1,'cleduc','2010-11-12 13:08:52','cleduc','2010-11-12 13:08:52',0),(16,57,61,1,'cleduc','2010-11-12 13:09:17','cleduc','2010-11-12 13:09:17',0),(17,7,61,1,'cleduc','2010-11-12 13:09:25','cleduc','2010-11-12 13:09:25',0),(18,56,61,1,'cleduc','2010-11-12 13:09:34','cleduc','2010-11-12 13:09:34',0),(19,58,61,1,'cleduc','2010-11-12 13:09:50','cleduc','2010-11-12 13:09:50',0),(20,55,61,1,'cleduc','2010-11-12 13:09:58','cleduc','2010-11-12 13:09:58',0),(21,62,61,1,'cleduc','2010-11-12 13:10:35','cleduc','2010-11-12 13:10:35',0),(25,2,64,1,'cleduc','2010-11-12 13:14:33','cleduc','2010-11-12 13:14:33',0),(26,4,64,1,'cleduc','2010-11-12 13:14:45','cleduc','2010-11-12 13:14:45',0),(27,28,64,1,'cleduc','2010-11-12 13:14:54','cleduc','2010-11-12 13:14:54',0),(28,8,64,1,'cleduc','2010-11-12 13:15:06','cleduc','2010-11-12 13:15:06',0),(29,31,64,1,'cleduc','2010-11-12 13:15:21','cleduc','2010-11-12 13:15:21',0),(30,1,64,1,'cleduc','2010-11-12 13:15:40','cleduc','2010-11-12 13:15:40',0),(31,30,64,1,'cleduc','2010-11-12 13:15:46','cleduc','2010-11-12 13:15:46',0),(32,29,64,1,'cleduc','2010-11-12 13:15:55','cleduc','2010-11-12 13:15:55',0),(33,26,64,1,'cleduc','2010-11-12 13:16:02','cleduc','2010-11-12 13:16:02',0),(34,3,64,1,'cleduc','2010-11-12 13:16:17','cleduc','2010-11-12 13:16:17',0),(35,43,65,1,'cleduc','2010-11-12 13:16:56','cleduc','2010-11-12 13:16:56',0),(36,45,65,1,'cleduc','2010-11-12 13:17:10','cleduc','2010-11-12 13:17:10',0),(37,42,65,1,'cleduc','2010-11-12 13:17:28','cleduc','2010-11-12 13:17:28',0),(38,41,65,1,'cleduc','2010-11-12 13:17:33','cleduc','2010-11-12 13:17:33',0),(39,23,65,1,'cleduc','2010-11-12 13:17:41','cleduc','2010-11-12 13:17:41',0),(40,44,65,1,'cleduc','2010-11-12 13:17:46','cleduc','2010-11-12 13:17:46',0),(41,5,66,1,'cleduc','2010-11-12 13:18:19','cleduc','2010-11-12 13:18:19',0),(42,34,66,1,'cleduc','2010-11-12 13:18:27','cleduc','2010-11-12 13:18:27',0),(43,8,66,1,'cleduc','2010-11-12 13:18:33','cleduc','2010-11-12 13:18:33',0),(44,36,66,1,'cleduc','2010-11-12 13:18:50','cleduc','2010-11-12 13:18:50',0),(45,6,66,1,'cleduc','2010-11-12 13:18:56','cleduc','2010-11-12 13:18:56',0),(46,67,68,1,'cleduc','2010-11-12 13:20:32','cleduc','2010-11-12 13:20:32',0),(47,51,68,1,'cleduc','2010-11-12 13:20:37','cleduc','2010-11-12 13:20:37',0),(48,52,68,1,'cleduc','2010-11-12 13:20:42','cleduc','2010-11-12 13:20:42',0),(49,53,68,1,'cleduc','2010-11-12 13:20:47','cleduc','2010-11-12 13:20:47',0),(50,54,68,1,'cleduc','2010-11-12 13:20:53','cleduc','2010-11-12 13:20:53',0),(51,77,79,1,'cleduc','2010-11-12 13:31:38','cleduc','2010-11-12 13:31:38',0),(52,76,79,1,'cleduc','2010-11-12 13:31:48','cleduc','2010-11-12 13:31:48',0),(53,78,79,1,'cleduc','2010-11-12 13:31:55','cleduc','2010-11-12 13:31:55',0),(54,69,80,1,'cleduc','2010-11-12 13:40:02','cleduc','2010-11-12 13:40:02',0),(55,71,80,1,'cleduc','2010-11-12 13:40:09','cleduc','2010-11-12 13:40:09',0),(56,70,80,1,'cleduc','2010-11-12 13:40:17','cleduc','2010-11-12 13:40:17',0),(57,72,80,1,'cleduc','2010-11-12 13:40:23','cleduc','2010-11-12 13:40:23',0),(58,73,80,1,'cleduc','2010-11-12 13:40:32','cleduc','2010-11-12 13:40:32',0),(59,74,80,1,'cleduc','2010-11-12 13:40:38','cleduc','2010-11-12 13:40:38',0),(60,75,80,1,'cleduc','2010-11-12 13:40:45','cleduc','2010-11-12 13:40:45',0),(61,35,66,1,'cleduc','2010-11-12 13:41:11','cleduc','2010-11-12 13:41:11',0),(62,37,66,1,'cleduc','2010-11-12 13:41:21','cleduc','2010-11-12 13:41:21',0),(63,7,64,1,'cleduc','2010-11-12 13:41:34','cleduc','2010-11-12 13:41:34',0),(64,32,64,1,'cleduc','2010-11-12 13:41:42','cleduc','2010-11-12 13:41:42',0),(65,37,64,1,'cleduc','2010-11-12 13:41:48','cleduc','2010-11-12 13:41:48',0),(66,6,64,1,'cleduc','2010-11-12 13:42:16','cleduc','2010-11-12 13:42:16',0),(67,5,64,1,'cleduc','2010-11-12 13:42:19','cleduc','2010-11-12 13:42:19',0),(68,48,64,1,'cleduc','2010-11-12 13:42:25','cleduc','2010-11-12 13:42:25',0),(69,81,66,1,'cleduc','2010-11-12 13:43:48','cleduc','2010-11-12 13:43:48',0),(70,81,60,1,'cleduc','2010-11-12 13:44:02','cleduc','2010-11-12 13:44:02',0),(71,81,64,1,'cleduc','2010-11-12 13:44:15','cleduc','2010-11-12 13:44:15',0),(72,31,66,1,'cleduc','2010-11-12 13:57:39','cleduc','2010-11-12 13:57:39',0),(74,40,84,1,'cleduc','2010-11-15 10:12:11','cleduc','2010-11-15 10:12:11',0),(75,38,84,1,'cleduc','2010-11-15 10:12:42','cleduc','2010-11-15 10:12:42',0),(76,39,84,1,'cleduc','2010-11-15 10:13:16','cleduc','2010-11-15 10:13:16',0);
/*!40000 ALTER TABLE `ResourceGroupMember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SOP`
--

DROP TABLE IF EXISTS `SOP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SOP` (
  `_SOP_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `Title` varchar(256) NOT NULL,
  PRIMARY KEY (`_SOP_key`),
  KEY `RefWorkgroup568` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup568` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SOP`
--

LOCK TABLES `SOP` WRITE;
/*!40000 ALTER TABLE `SOP` DISABLE KEYS */;
/*!40000 ALTER TABLE `SOP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SOPVersion`
--

DROP TABLE IF EXISTS `SOPVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SOPVersion` (
  `_SOPVersion_key` int(11) NOT NULL AUTO_INCREMENT,
  `_SOP_key` int(11) NOT NULL,
  `_URLServer_key` int(11) DEFAULT NULL,
  `MajorVersion` int(11) DEFAULT NULL,
  `MinorVersion` int(11) DEFAULT NULL,
  `Revision` int(11) DEFAULT NULL,
  `SopUrl` varchar(50) DEFAULT NULL,
  `FileName` varchar(75) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SOPVersion_key`),
  KEY `Ref148205` (`_URLServer_key`),
  KEY `Ref95116` (`_SOP_key`),
  KEY `RefWorkgroup569` (`_Workgroup_key`),
  CONSTRAINT `Refcv_URLServer205` FOREIGN KEY (`_URLServer_key`) REFERENCES `cv_URLServer` (`_URLServer_key`),
  CONSTRAINT `RefSOP116` FOREIGN KEY (`_SOP_key`) REFERENCES `SOP` (`_SOP_key`),
  CONSTRAINT `RefWorkgroup569` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SOPVersion`
--

LOCK TABLES `SOPVersion` WRITE;
/*!40000 ALTER TABLE `SOPVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `SOPVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sample`
--

DROP TABLE IF EXISTS `Sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sample` (
  `_Sample_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentSample_key` int(11) DEFAULT NULL,
  `_SampleType_key` int(11) NOT NULL,
  `_SampleStatus_key` int(11) NOT NULL,
  `_NameFamily_key` decimal(38,0) DEFAULT NULL,
  `_Line_key` int(11) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `SampleName` varchar(32) NOT NULL,
  `IsQCSample` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Sample_key`),
  KEY `Ref2470` (`_SampleType_key`),
  KEY `Ref146204` (`_SampleStatus_key`),
  KEY `Ref4927` (`_ParentSample_key`),
  KEY `RefWorkgroup552` (`_Workgroup_key`),
  CONSTRAINT `Refcv_SampleStatus204` FOREIGN KEY (`_SampleStatus_key`) REFERENCES `cv_SampleStatus` (`_SampleStatus_key`),
  CONSTRAINT `Refcv_SampleType70` FOREIGN KEY (`_SampleType_key`) REFERENCES `cv_SampleType` (`_SampleType_key`),
  CONSTRAINT `RefSample27` FOREIGN KEY (`_ParentSample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup552` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sample`
--

LOCK TABLES `Sample` WRITE;
/*!40000 ALTER TABLE `Sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SampleCharacteristic`
--

DROP TABLE IF EXISTS `SampleCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SampleCharacteristic` (
  `_SampleCharacteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_SampleType_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SampleCharacteristic_key`),
  KEY `Ref155221` (`_CharacteristicVersion_key`),
  KEY `Ref24308` (`_SampleType_key`),
  KEY `RefWorkgroup553` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion221` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_SampleType308` FOREIGN KEY (`_SampleType_key`) REFERENCES `cv_SampleType` (`_SampleType_key`),
  CONSTRAINT `RefWorkgroup553` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SampleCharacteristic`
--

LOCK TABLES `SampleCharacteristic` WRITE;
/*!40000 ALTER TABLE `SampleCharacteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `SampleCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SampleCharacteristicInstance`
--

DROP TABLE IF EXISTS `SampleCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SampleCharacteristicInstance` (
  `_SampleCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_SampleCharacteristic_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,10) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(1) DEFAULT NULL,
  `DateStart` datetime DEFAULT NULL,
  `DateEnd` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_SampleCharacteristicInstance_key`),
  KEY `Ref152254` (`_SampleCharacteristic_key`),
  KEY `Ref49309` (`_Sample_key`),
  KEY `RefEnumerationItem303` (`_EnumerationItem_key`),
  KEY `RefWorkgroup554` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem303` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefSample309` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefSampleCharacteristic254` FOREIGN KEY (`_SampleCharacteristic_key`) REFERENCES `SampleCharacteristic` (`_SampleCharacteristic_key`),
  CONSTRAINT `RefWorkgroup554` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SampleCharacteristicInstance`
--

LOCK TABLES `SampleCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `SampleCharacteristicInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `SampleCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SampleContainer`
--

DROP TABLE IF EXISTS `SampleContainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SampleContainer` (
  `_SampleContainer_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Container_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `DateIn` datetime DEFAULT NULL,
  `DateOut` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SampleContainer_key`),
  KEY `Ref117181` (`_Container_key`),
  KEY `Ref49182` (`_Sample_key`),
  KEY `RefWorkgroup557` (`_Workgroup_key`),
  CONSTRAINT `RefContainer181` FOREIGN KEY (`_Container_key`) REFERENCES `Container` (`_Container_key`),
  CONSTRAINT `RefSample182` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup557` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SampleContainer`
--

LOCK TABLES `SampleContainer` WRITE;
/*!40000 ALTER TABLE `SampleContainer` DISABLE KEYS */;
/*!40000 ALTER TABLE `SampleContainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SampleOrder`
--

DROP TABLE IF EXISTS `SampleOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SampleOrder` (
  `_SampleOrder_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Project_key` int(11) DEFAULT NULL,
  `_ServiceOrderVersion_key` int(11) DEFAULT NULL,
  `_Institution_key` int(11) NOT NULL,
  `_ContactPerson_key` int(11) DEFAULT NULL,
  `_SampleType_key` int(11) NOT NULL,
  `DateOrdered` datetime DEFAULT NULL,
  `DateReceived` datetime DEFAULT NULL,
  `Comments` varchar(2000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SampleOrder_key`),
  KEY `Ref142211` (`_Project_key`),
  KEY `Ref139216` (`_ServiceOrderVersion_key`),
  KEY `Ref160316` (`_Institution_key`),
  KEY `Ref161318` (`_ContactPerson_key`),
  KEY `Ref24337` (`_SampleType_key`),
  KEY `RefWorkgroup558` (`_Workgroup_key`),
  CONSTRAINT `RefContactPerson318` FOREIGN KEY (`_ContactPerson_key`) REFERENCES `ContactPerson` (`_ContactPerson_key`),
  CONSTRAINT `Refcv_SampleType337` FOREIGN KEY (`_SampleType_key`) REFERENCES `cv_SampleType` (`_SampleType_key`),
  CONSTRAINT `RefInstitution316` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`),
  CONSTRAINT `RefProject211` FOREIGN KEY (`_Project_key`) REFERENCES `Project` (`_Project_key`),
  CONSTRAINT `RefServiceOrderVersion216` FOREIGN KEY (`_ServiceOrderVersion_key`) REFERENCES `ServiceOrderVersion` (`_ServiceOrderVersion_key`),
  CONSTRAINT `RefWorkgroup558` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SampleOrder`
--

LOCK TABLES `SampleOrder` WRITE;
/*!40000 ALTER TABLE `SampleOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `SampleOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SampleStudy`
--

DROP TABLE IF EXISTS `SampleStudy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SampleStudy` (
  `_SampleStudy_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Study_key` int(11) NOT NULL,
  `_Sample_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SampleStudy_key`),
  KEY `Ref47208` (`_Study_key`),
  KEY `Ref49209` (`_Sample_key`),
  KEY `RefWorkgroup559` (`_Workgroup_key`),
  CONSTRAINT `RefSample209` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefStudy208` FOREIGN KEY (`_Study_key`) REFERENCES `Study` (`_Study_key`),
  CONSTRAINT `RefWorkgroup559` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SampleStudy`
--

LOCK TABLES `SampleStudy` WRITE;
/*!40000 ALTER TABLE `SampleStudy` DISABLE KEYS */;
/*!40000 ALTER TABLE `SampleStudy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ScheduleNonTask`
--

DROP TABLE IF EXISTS `ScheduleNonTask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScheduleNonTask` (
  `_ScheduleNonTask_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Resource_key` int(11) NOT NULL,
  `Description` varchar(18) NOT NULL,
  `DateTimeStart` datetime NOT NULL,
  `DateTimeComplete` datetime NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ScheduleNonTask_key`),
  KEY `Ref97125` (`_Resource_key`),
  KEY `RefWorkgroup555` (`_Workgroup_key`),
  CONSTRAINT `RefResource125` FOREIGN KEY (`_Resource_key`) REFERENCES `Resource` (`_Resource_key`),
  CONSTRAINT `RefWorkgroup555` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScheduleNonTask`
--

LOCK TABLES `ScheduleNonTask` WRITE;
/*!40000 ALTER TABLE `ScheduleNonTask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ScheduleNonTask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceItem`
--

DROP TABLE IF EXISTS `ServiceItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceItem` (
  `_ServiceItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ServiceCategory_key` int(11) NOT NULL,
  `ServiceIItem` varchar(100) NOT NULL,
  `ItemCode` varchar(25) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ServiceItem_key`),
  KEY `Ref128187` (`_ServiceCategory_key`),
  KEY `RefWorkgroup561` (`_Workgroup_key`),
  CONSTRAINT `Refcv_ServiceCategory187` FOREIGN KEY (`_ServiceCategory_key`) REFERENCES `cv_ServiceCategory` (`_ServiceCategory_key`),
  CONSTRAINT `RefWorkgroup561` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceItem`
--

LOCK TABLES `ServiceItem` WRITE;
/*!40000 ALTER TABLE `ServiceItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceOrder`
--

DROP TABLE IF EXISTS `ServiceOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceOrder` (
  `_ServiceOrder_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Inquiry_key` int(11) NOT NULL,
  `_ServiceOrderStatus_key` int(11) NOT NULL,
  `_ServiceOrderCancelReason_key` int(11) DEFAULT NULL,
  `_Institution_key` int(11) DEFAULT NULL,
  `OrderID` varchar(18) NOT NULL,
  `ExternalOrderID` varchar(18) DEFAULT NULL,
  `AccountNumber` varchar(18) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ServiceOrder_key`),
  KEY `Ref137190` (`_ServiceOrderStatus_key`),
  KEY `Ref136192` (`_ServiceOrderCancelReason_key`),
  KEY `Ref133193` (`_Inquiry_key`),
  KEY `RefWorkgroup562` (`_Workgroup_key`),
  KEY `FK_ServiceOrder_5` (`_Institution_key`),
  CONSTRAINT `FK_ServiceOrder_5` FOREIGN KEY (`_Institution_key`) REFERENCES `Institution` (`_Institution_key`),
  CONSTRAINT `Refcv_ServiceOrderCancelReason192` FOREIGN KEY (`_ServiceOrderCancelReason_key`) REFERENCES `cv_ServiceOrderCancelReason` (`_ServiceOrderCancelReason_key`),
  CONSTRAINT `Refcv_ServiceOrderStatus190` FOREIGN KEY (`_ServiceOrderStatus_key`) REFERENCES `cv_ServiceOrderStatus` (`_ServiceOrderStatus_key`),
  CONSTRAINT `RefInquiry193` FOREIGN KEY (`_Inquiry_key`) REFERENCES `Inquiry` (`_Inquiry_key`),
  CONSTRAINT `RefWorkgroup562` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceOrder`
--

LOCK TABLES `ServiceOrder` WRITE;
/*!40000 ALTER TABLE `ServiceOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceOrderItem`
--

DROP TABLE IF EXISTS `ServiceOrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceOrderItem` (
  `_ServiceOrderItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentServiceItem_key` int(11) DEFAULT NULL,
  `_ServiceOrderVersion_key` int(11) NOT NULL,
  `_ServiceItem_key` int(11) NOT NULL,
  `_ServiceOrderItemStatus_key` int(11) NOT NULL,
  `OrderItemDate` datetime DEFAULT NULL,
  `AdjustedPrice` decimal(10,0) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `AccountNumber` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ServiceOrderItem_key`),
  KEY `Ref138186` (`_ParentServiceItem_key`),
  KEY `Ref139188` (`_ServiceOrderVersion_key`),
  KEY `Ref135195` (`_ServiceItem_key`),
  KEY `Ref129196` (`_ServiceOrderItemStatus_key`),
  KEY `RefWorkgroup563` (`_Workgroup_key`),
  CONSTRAINT `Refcv_ServiceOrderItemStatus196` FOREIGN KEY (`_ServiceOrderItemStatus_key`) REFERENCES `cv_ServiceOrderItemStatus` (`_ServiceOrderItemStatus_key`),
  CONSTRAINT `RefServiceItem195` FOREIGN KEY (`_ServiceItem_key`) REFERENCES `ServiceItem` (`_ServiceItem_key`),
  CONSTRAINT `RefServiceOrderItem186` FOREIGN KEY (`_ParentServiceItem_key`) REFERENCES `ServiceOrderItem` (`_ServiceOrderItem_key`),
  CONSTRAINT `RefServiceOrderVersion188` FOREIGN KEY (`_ServiceOrderVersion_key`) REFERENCES `ServiceOrderVersion` (`_ServiceOrderVersion_key`),
  CONSTRAINT `RefWorkgroup563` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceOrderItem`
--

LOCK TABLES `ServiceOrderItem` WRITE;
/*!40000 ALTER TABLE `ServiceOrderItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceOrderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceOrderVersion`
--

DROP TABLE IF EXISTS `ServiceOrderVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceOrderVersion` (
  `_ServiceOrderVersion_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ServiceOrder_key` int(11) NOT NULL,
  `ServiceOrderVersion` varchar(10) NOT NULL,
  `ServiceOrderVersionDate` datetime DEFAULT NULL,
  `IsActiveVersion` tinyint(4) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ServiceOrderVersion_key`),
  KEY `Ref125189` (`_ServiceOrder_key`),
  KEY `RefWorkgroup564` (`_Workgroup_key`),
  CONSTRAINT `RefServiceOrder189` FOREIGN KEY (`_ServiceOrder_key`) REFERENCES `ServiceOrder` (`_ServiceOrder_key`),
  CONSTRAINT `RefWorkgroup564` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceOrderVersion`
--

LOCK TABLES `ServiceOrderVersion` WRITE;
/*!40000 ALTER TABLE `ServiceOrderVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceOrderVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceTemplate`
--

DROP TABLE IF EXISTS `ServiceTemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceTemplate` (
  `_ServiceTemplate_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentServiceTemplate_key` int(11) DEFAULT NULL,
  `TemplateName` varchar(75) NOT NULL,
  `SubsidizedPrice` decimal(18,0) DEFAULT NULL,
  `StandardPrice` decimal(18,0) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ServiceTemplate_key`),
  KEY `Ref126197` (`_ParentServiceTemplate_key`),
  KEY `RefWorkgroup565` (`_Workgroup_key`),
  CONSTRAINT `RefServiceTemplate197` FOREIGN KEY (`_ParentServiceTemplate_key`) REFERENCES `ServiceTemplate` (`_ServiceTemplate_key`),
  CONSTRAINT `RefWorkgroup565` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceTemplate`
--

LOCK TABLES `ServiceTemplate` WRITE;
/*!40000 ALTER TABLE `ServiceTemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceTemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceTemplateItem`
--

DROP TABLE IF EXISTS `ServiceTemplateItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceTemplateItem` (
  `_ServiceTemplateItem_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentServiceTemplateItem_key` int(11) DEFAULT NULL,
  `_ServiceTemplate_key` int(11) NOT NULL,
  `_ServiceItem_key` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_ServiceTemplateItem_key`),
  KEY `Ref135185` (`_ServiceItem_key`),
  KEY `Ref127191` (`_ParentServiceTemplateItem_key`),
  KEY `Ref126194` (`_ServiceTemplate_key`),
  KEY `RefWorkgroup566` (`_Workgroup_key`),
  CONSTRAINT `RefServiceItem185` FOREIGN KEY (`_ServiceItem_key`) REFERENCES `ServiceItem` (`_ServiceItem_key`),
  CONSTRAINT `RefServiceTemplate194` FOREIGN KEY (`_ServiceTemplate_key`) REFERENCES `ServiceTemplate` (`_ServiceTemplate_key`),
  CONSTRAINT `RefServiceTemplateItem191` FOREIGN KEY (`_ParentServiceTemplateItem_key`) REFERENCES `ServiceTemplateItem` (`_ServiceTemplateItem_key`),
  CONSTRAINT `RefWorkgroup566` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceTemplateItem`
--

LOCK TABLES `ServiceTemplateItem` WRITE;
/*!40000 ALTER TABLE `ServiceTemplateItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceTemplateItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SireInCross`
--

DROP TABLE IF EXISTS `SireInCross`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SireInCross` (
  `_SireInCross_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Cross_key` int(11) NOT NULL,
  `_Sample_key` int(11) DEFAULT NULL,
  `_OnRetireBreedingStatus_key` int(11) DEFAULT NULL,
  `_OnRetireDiet_key` int(11) DEFAULT NULL,
  `_SireChangeReason_key` int(11) DEFAULT NULL,
  `_Organism_key` int(11) DEFAULT NULL,
  `DateIn` datetime DEFAULT NULL,
  `DateOut` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SireInCross_key`),
  KEY `Ref468` (`_OnRetireDiet_key`),
  KEY `Ref1769` (`_SireChangeReason_key`),
  KEY `Ref4928` (`_Sample_key`),
  KEY `Ref3034` (`_Organism_key`),
  KEY `Ref1136` (`_Cross_key`),
  KEY `Ref364` (`_OnRetireBreedingStatus_key`),
  KEY `RefWorkgroup567` (`_Workgroup_key`),
  CONSTRAINT `RefCross_36` FOREIGN KEY (`_Cross_key`) REFERENCES `Cross_` (`_Cross_key`),
  CONSTRAINT `Refcv_BreedingStatus64` FOREIGN KEY (`_OnRetireBreedingStatus_key`) REFERENCES `cv_BreedingStatus` (`_BreedingStatus_key`),
  CONSTRAINT `Refcv_Diet68` FOREIGN KEY (`_OnRetireDiet_key`) REFERENCES `cv_Diet` (`_Diet_key`),
  CONSTRAINT `Refcv_SireChangeReason69` FOREIGN KEY (`_SireChangeReason_key`) REFERENCES `cv_SireChangeReason` (`_SireChangeReason_key`),
  CONSTRAINT `RefOrganism34` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefSample28` FOREIGN KEY (`_Sample_key`) REFERENCES `Sample` (`_Sample_key`),
  CONSTRAINT `RefWorkgroup567` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SireInCross`
--

LOCK TABLES `SireInCross` WRITE;
/*!40000 ALTER TABLE `SireInCross` DISABLE KEYS */;
/*!40000 ALTER TABLE `SireInCross` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Study`
--

DROP TABLE IF EXISTS `Study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Study` (
  `_Study_key` int(11) NOT NULL AUTO_INCREMENT,
  `StudyName` varchar(75) NOT NULL,
  `Comments` varchar(1000) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_Study_key`),
  KEY `RefWorkgroup570` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup570` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Study`
--

LOCK TABLES `Study` WRITE;
/*!40000 ALTER TABLE `Study` DISABLE KEYS */;
/*!40000 ALTER TABLE `Study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SubcontainerLayout`
--

DROP TABLE IF EXISTS `SubcontainerLayout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SubcontainerLayout` (
  `_SubcontainerLayout_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ParentLayout_key` int(11) DEFAULT NULL,
  `_ContainerType_key` int(11) NOT NULL,
  `_SubcontainerType_key` int(11) NOT NULL,
  `LayoutName` varchar(75) NOT NULL,
  `QuantityX` int(11) DEFAULT NULL,
  `QuantityY` int(11) DEFAULT NULL,
  `QuantityZ` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SubcontainerLayout_key`),
  KEY `Ref122247` (`_ContainerType_key`),
  KEY `Ref122248` (`_SubcontainerType_key`),
  KEY `Ref164328` (`_ParentLayout_key`),
  KEY `RefWorkgroup571` (`_Workgroup_key`),
  CONSTRAINT `Refcv_ContainerType247` FOREIGN KEY (`_ContainerType_key`) REFERENCES `cv_ContainerType` (`_ContainerType_key`),
  CONSTRAINT `Refcv_ContainerType248` FOREIGN KEY (`_SubcontainerType_key`) REFERENCES `cv_ContainerType` (`_ContainerType_key`),
  CONSTRAINT `RefSubcontainerLayout328` FOREIGN KEY (`_ParentLayout_key`) REFERENCES `SubcontainerLayout` (`_SubcontainerLayout_key`),
  CONSTRAINT `RefWorkgroup571` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SubcontainerLayout`
--

LOCK TABLES `SubcontainerLayout` WRITE;
/*!40000 ALTER TABLE `SubcontainerLayout` DISABLE KEYS */;
/*!40000 ALTER TABLE `SubcontainerLayout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SubcontainerPosition`
--

DROP TABLE IF EXISTS `SubcontainerPosition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SubcontainerPosition` (
  `_SubcontainerPosition_key` int(11) NOT NULL AUTO_INCREMENT,
  `_SubcontainerLayout_key` int(11) NOT NULL,
  `PositionName` varchar(18) NOT NULL,
  `Ordinal` int(11) NOT NULL,
  `XPosition` int(11) DEFAULT NULL,
  `YPosition` int(11) DEFAULT NULL,
  `ZPosition` int(11) DEFAULT NULL,
  `IsActive` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SubcontainerPosition_key`),
  KEY `Ref164273` (`_SubcontainerLayout_key`),
  KEY `RefWorkgroup572` (`_Workgroup_key`),
  CONSTRAINT `RefSubcontainerLayout273` FOREIGN KEY (`_SubcontainerLayout_key`) REFERENCES `SubcontainerLayout` (`_SubcontainerLayout_key`),
  CONSTRAINT `RefWorkgroup572` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SubcontainerPosition`
--

LOCK TABLES `SubcontainerPosition` WRITE;
/*!40000 ALTER TABLE `SubcontainerPosition` DISABLE KEYS */;
/*!40000 ALTER TABLE `SubcontainerPosition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemAction`
--

DROP TABLE IF EXISTS `SystemAction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemAction` (
  `_SystemAction_key` int(11) NOT NULL AUTO_INCREMENT,
  `SystemAction` varchar(75) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SystemAction_key`),
  KEY `RefWorkgroup573` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup573` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemAction`
--

LOCK TABLES `SystemAction` WRITE;
/*!40000 ALTER TABLE `SystemAction` DISABLE KEYS */;
/*!40000 ALTER TABLE `SystemAction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemActionCharacteristic`
--

DROP TABLE IF EXISTS `SystemActionCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemActionCharacteristic` (
  `_SystemActionCharacteristic_key` int(11) NOT NULL,
  `_SystemAction_key` int(11) NOT NULL,
  `_Characteristic_key` int(11) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  PRIMARY KEY (`_SystemActionCharacteristic_key`),
  KEY `RefWorkgroup574` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup574` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemActionCharacteristic`
--

LOCK TABLES `SystemActionCharacteristic` WRITE;
/*!40000 ALTER TABLE `SystemActionCharacteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `SystemActionCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemActionInput`
--

DROP TABLE IF EXISTS `SystemActionInput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemActionInput` (
  `_SystemActionInput_key` int(11) NOT NULL AUTO_INCREMENT,
  `_SystemAction_key` int(11) NOT NULL,
  `_Input_key` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SystemActionInput_key`),
  KEY `Ref201345` (`_SystemAction_key`),
  KEY `Ref70348` (`_Input_key`),
  KEY `RefWorkgroup575` (`_Workgroup_key`),
  CONSTRAINT `RefInput348` FOREIGN KEY (`_Input_key`) REFERENCES `Input` (`_Input_key`),
  CONSTRAINT `RefSystemAction345` FOREIGN KEY (`_SystemAction_key`) REFERENCES `SystemAction` (`_SystemAction_key`),
  CONSTRAINT `RefWorkgroup575` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemActionInput`
--

LOCK TABLES `SystemActionInput` WRITE;
/*!40000 ALTER TABLE `SystemActionInput` DISABLE KEYS */;
/*!40000 ALTER TABLE `SystemActionInput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemActionOutput`
--

DROP TABLE IF EXISTS `SystemActionOutput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemActionOutput` (
  `_SystemActionOutput_key` int(11) NOT NULL,
  `_SystemAction_key` int(11) NOT NULL,
  `_Output_key` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SystemActionOutput_key`),
  KEY `Ref201344` (`_SystemAction_key`),
  KEY `Ref64346` (`_Output_key`),
  KEY `RefWorkgroup576` (`_Workgroup_key`),
  CONSTRAINT `RefOutput346` FOREIGN KEY (`_Output_key`) REFERENCES `Output` (`_Output_key`),
  CONSTRAINT `RefSystemAction344` FOREIGN KEY (`_SystemAction_key`) REFERENCES `SystemAction` (`_SystemAction_key`),
  CONSTRAINT `RefWorkgroup576` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemActionOutput`
--

LOCK TABLES `SystemActionOutput` WRITE;
/*!40000 ALTER TABLE `SystemActionOutput` DISABLE KEYS */;
/*!40000 ALTER TABLE `SystemActionOutput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SystemActionResourceType`
--

DROP TABLE IF EXISTS `SystemActionResourceType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SystemActionResourceType` (
  `_SystemActionResourceType_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ResourceType_key` int(11) NOT NULL,
  `_SystemAction_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  PRIMARY KEY (`_SystemActionResourceType_key`),
  KEY `Refcv_ResourceType1` (`_ResourceType_key`),
  KEY `RefSystemAction2` (`_SystemAction_key`),
  KEY `RefWorkgroup3` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup3` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`),
  CONSTRAINT `Refcv_ResourceType1` FOREIGN KEY (`_ResourceType_key`) REFERENCES `cv_ResourceType` (`_ResourceType_key`),
  CONSTRAINT `RefSystemAction2` FOREIGN KEY (`_SystemAction_key`) REFERENCES `SystemAction` (`_SystemAction_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SystemActionResourceType`
--

LOCK TABLES `SystemActionResourceType` WRITE;
/*!40000 ALTER TABLE `SystemActionResourceType` DISABLE KEYS */;
/*!40000 ALTER TABLE `SystemActionResourceType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaskSchedule`
--

DROP TABLE IF EXISTS `TaskSchedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskSchedule` (
  `_TaskSchedule_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProcessName` varchar(45) NOT NULL,
  `LastRunDate` datetime NOT NULL,
  `LastSuccessfulRunDate` datetime NOT NULL,
  PRIMARY KEY (`_TaskSchedule_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskSchedule`
--

LOCK TABLES `TaskSchedule` WRITE;
/*!40000 ALTER TABLE `TaskSchedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `TaskSchedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaxonCharacteristic`
--

DROP TABLE IF EXISTS `TaxonCharacteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaxonCharacteristic` (
  `_TaxonCharacteristic_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Taxon_key` int(11) NOT NULL,
  `_CharacteristicVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_TaxonCharacteristic_key`),
  KEY `Ref13223` (`_Taxon_key`),
  KEY `Ref155224` (`_CharacteristicVersion_key`),
  KEY `RefWorkgroup577` (`_Workgroup_key`),
  CONSTRAINT `RefCharacteristicVersion224` FOREIGN KEY (`_CharacteristicVersion_key`) REFERENCES `CharacteristicVersion` (`_CharacteristicVersion_key`),
  CONSTRAINT `Refcv_Taxon223` FOREIGN KEY (`_Taxon_key`) REFERENCES `cv_Taxon` (`_Taxon_key`),
  CONSTRAINT `RefWorkgroup577` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaxonCharacteristic`
--

LOCK TABLES `TaxonCharacteristic` WRITE;
/*!40000 ALTER TABLE `TaxonCharacteristic` DISABLE KEYS */;
-- INSERT INTO `TaxonCharacteristic` VALUES (1,1,1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1);
/*!40000 ALTER TABLE `TaxonCharacteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaxonCharacteristicInstance`
--

DROP TABLE IF EXISTS `TaxonCharacteristicInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaxonCharacteristicInstance` (
  `_TaxonCharacteristicInstance_key` int(11) NOT NULL AUTO_INCREMENT,
  `_TaxonCharacteristic_key` int(11) NOT NULL,
  `TextValue` varchar(250) DEFAULT NULL,
  `NumericValue` decimal(18,10) DEFAULT NULL,
  `DateValue` date DEFAULT NULL,
  `TimeValue` time DEFAULT NULL,
  `BooleanValue` tinyint(1) DEFAULT NULL,
  `DateStart` datetime DEFAULT NULL,
  `DateEnd` datetime DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_EnumerationItem_key` int(11) DEFAULT NULL,
  `_Organism_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_TaxonCharacteristicInstance_key`),
  KEY `Ref154228` (`_TaxonCharacteristic_key`),
  KEY `RefEnumerationItem304` (`_EnumerationItem_key`),
  KEY `RefOrganism434` (`_Organism_key`),
  KEY `RefWorkgroup578` (`_Workgroup_key`),
  CONSTRAINT `RefEnumerationItem304` FOREIGN KEY (`_EnumerationItem_key`) REFERENCES `EnumerationItem` (`_EnumerationItem_key`),
  CONSTRAINT `RefOrganism434` FOREIGN KEY (`_Organism_key`) REFERENCES `Organism` (`_Organism_key`),
  CONSTRAINT `RefTaxonCharacteristic228` FOREIGN KEY (`_TaxonCharacteristic_key`) REFERENCES `TaxonCharacteristic` (`_TaxonCharacteristic_key`),
  CONSTRAINT `RefWorkgroup578` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaxonCharacteristicInstance`
--

LOCK TABLES `TaxonCharacteristicInstance` WRITE;
/*!40000 ALTER TABLE `TaxonCharacteristicInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `TaxonCharacteristicInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
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
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
-- -- INSERT INTO `User` VALUES (1,'amiller','Abigail','Ames','Software Engineer III','abigail.ames@jax.org','207-288-6000x1805',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',15,11,1,'amiller',NULL),(2,'kub','Kirk','Barsanti','Software Quality Assurance Engineer II','kirk.barsanti@jax.org','207-288-6000x6218',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',12,8,1,'kub',NULL),(4,'cjd','Chuck','Donnelly','Director Computational Sciences','chuck.donnelly@jax.org','207-288-6000x6339',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',10,4,1,'cjd',NULL),(5,'cnh','Craig','Hanna','Software Engineer III','craig.hanna@jax.org','207-288-6000x6869',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',11,1,1,'cnh',NULL),(6,'cleduc','Carrie','LeDuc','Manager Reproductive Sciences Operations','carrie.leduc@jax.org','207-288-6000x6309',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',10,1,1,'cleduc',NULL),(7,'michaelm','Mike','McFarland','Manager Software Engineering','michael.mcfarland@jax.org','207-288-6000x6796',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',11,1,1,'michaelm',NULL),(8,'mrelac','Mike','Relac','Software Engineer III','mike.relac@jax.org','207-288-6000x6783',NULL,NULL,1,'dba','2010-04-26 11:23:46','cleduc','2010-06-04 14:14:50',11,1,1,'mrelac',NULL),(10,'tst-kub','Kirk','Barsanti (Test Account)','','','',NULL,NULL,1,'kub','2010-04-26 12:23:36','cnh','2010-10-27 07:56:58',8,1,1,'tst-kub',NULL),(11,'rat','Rob','Taft','Director Reproductive Sciences','rob.taft@jax.org','207-288-6000x6727',NULL,NULL,1,'cnh','2010-04-26 13:09:27','cleduc','2010-06-04 14:14:50',5,1,1,'rat',NULL),(12,'sbean','Susan','Bean','Project Coordinator I','susan.bean@jax.org','207-288-6000x6483',NULL,NULL,1,'cleduc','2010-05-04 16:42:04','cleduc','2010-06-04 14:14:50',3,1,0,'sbean',NULL),(13,'jsf','Jane','Farley','Manager Assisted Reproductive Technologies','jane.farley@jax.org','207-288-6000x6417',NULL,NULL,1,'cleduc','2010-05-04 16:49:25','cleduc','2010-06-04 14:14:50',2,1,0,'jsf',NULL),(14,'sian','Sian','Clements','Manager Cryopreservation Service','sian.clements@jax.org','207-288-6000x6658',NULL,NULL,1,'cleduc','2010-06-04 14:14:45','cleduc','2010-06-04 14:14:50',0,1,0,'sian',NULL),(15,'amehalow','Adrienne','Mehalow','Associate Study Director','adrienne.mehalow@jax.org','207-288-6000x6799',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,2,0,'amehalow',NULL),(16,'jjv','Jay','Vetelino','Senior Manager JAX Services Fiscal Operations','jay.vetelino@jax.org','207-288-6000x6882',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,2,0,'jjv',NULL),(17,'dac','Dave','Clary','Software Engineer II','dave.clary@jax.org','916-469-2600x2614',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'dac',NULL),(18,'ncg','Neal','Goodwin','In Vivo Program Director','neal.goodwin@jax.org','207-288-6000x6417',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'ncg',NULL),(19,'joy','Joy','Froding','Reproductive Sciences Database Specialist','joy.froding@jax.org','207-288-6000x6690',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'joy',NULL),(21,'cgetch','Claire','Getchell','Supervisor Reproductive Sciences','claire.getchell@jax.org','207-288-6000x6381',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'cgetch',NULL),(22,'myrna','Myrna','Parker','Biomedical Technologist II','myrna.parker@jax.org','207-288-6000x6667',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'myrna',NULL),(23,'kurtc','Kurt','Christiansen','Supervisor Reproductive Sciences','kurt.christiansen@jax.org','207-288-6000x6377',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,3,0,'kurtc',NULL),(24,'mem','Marge','May','Software Engineer II','marge.may@jax.org','207-288-6000x6630',NULL,NULL,1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1,4,1,'mem',NULL),(25,'pblauth','Peter','Blauth','Software Quality Assurance Engineer II','peter.blauth@jax.org','207-288-6000x1860',NULL,NULL,1,'dba','2010-10-25 16:41:32','cnh','2010-10-27 07:56:58',2,4,0,'pblauth',NULL),(26,NULL,'Abigail','Ames',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'abigail.ames@jax.org','JMo0sMFYPPRfbaBq26BfFB4+Jn9AniU1um0dJqXwBqM='),(27,NULL,'Kirk','Barsanti',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'kirk.barsanti@jax.org','eP+80DSvyQ/7GpeuFusGfYGULgq1CUBtmKnaw8l13Lk='),(28,NULL,'Peter','Blauth',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'peter.blauth@jax.org',' 31XeRcqYxHKLPh8o3DNR43YzbrDv5G0z8rcX/6FGEBE='),(29,NULL,'Chuck','Donnelly',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'chuck.donnelly@jax.org','mJQCzFibwtcu1uzjVKcSCuWEyRLI5kQRPlb8ohvXprA='),(30,NULL,'Craig','Hanna',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'craig.hanna@jax.org','UjtQLjywyWzwtV3rgSh3IuWkItu7UDm4O0U5bB7gOw0='),(31,NULL,'Carrie','LeDuc',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'carrie.leduc@jax.org','1tjPFK0ZddodIfxVz275xsVs2TZn9H9zJmfvTj8/sbU='),(33,NULL,'Mike','Relac',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'mike.relac@jax.org','GdyN09r5SbhsWWnvcc+eiuqKdWTlWsPSdp9t3FmTMW4='),(34,NULL,'Dave','Springer',NULL,NULL,NULL,NULL,NULL,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1,1,NULL,'dave.springer@jax.org','VtoDeQ+48vRCcghajay3EXxa61hqcDZ4Z/vjooF1wKE='),(35,'mjbraun','Madeleine','Braun','Senior Director Genotyping & Reproductive Sciences Services','madeleine.braun@jax.org','207-288-6000x6317',NULL,NULL,1,'cnh','2010-10-27 07:56:50','cnh','2010-10-27 07:56:58',0,4,NULL,'mjbraun',NULL),(36,'tst-amiller','Abigail','Ames (Test Account)','','','',NULL,NULL,1,'amiller','2010-10-29 13:43:00','amiller','2010-10-29 13:43:05',0,4,NULL,'tst-amiller',NULL),(37,'bjlunt','Barrett','Lunt','JAX Services Fiscal Manager','barrett.lunt@jax.org','207-288-6000x6824',NULL,NULL,1,'michaelm','2011-03-29 08:20:56','michaelm','2011-03-29 08:21:01',0,1,NULL,'bjlunt',NULL),(38,'deiben','Dorothea','Eiben','IT Business Analyst III','Dorothea.Eiben@jax.org','207-288-6000x6719',NULL,NULL,1,'michaelm','2011-03-29 08:21:51','michaelm','2011-03-29 08:21:54',0,1,NULL,'deiben',NULL),(39,'douglash','Douglas','Hinerfeld','Associate Director Molecular Phenotyping Sciences','douglas.hinerfeld@jax.org','207-288-6000x6703','',NULL,1,'amiller','2011-03-31 14:00:19','amiller','2011-03-31 14:18:16',1,11,0,'douglash',NULL),(40,'log','Leslie','Goodwin','Manager Molecular Biology Services','leslie.goodwin@jax.org','207-288-6000x6889','',NULL,1,'douglash','2011-03-31 14:20:00','douglash','2011-03-31 14:21:51',1,11,NULL,'log',NULL),(41,'gperry','Greg','Perry','Molecular Biology Technologist I','Greg.Perry@jax.org','207-288-6000x6197',NULL,NULL,1,'douglash','2011-03-31 14:20:33','douglash','2011-03-31 14:20:37',0,11,NULL,'gperry',NULL),(42,'ryw','Rob','Wilpan','Protein Technologist III','rob.wilpan@jax.org','207-288-6000x6194',NULL,NULL,1,'douglash','2011-03-31 14:20:45','douglash','2011-03-31 14:20:51',0,11,NULL,'ryw',NULL),(43,'jlm','Judy','Morgan','Genetic Resource Science Project Manager','judy.morgan@jax.org','207-288-6000x6878',NULL,NULL,1,'michaelm','2011-04-06 12:29:38','michaelm','2011-04-06 12:29:41',0,1,NULL,'jlm',NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Workgroup`
--

DROP TABLE IF EXISTS `Workgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Workgroup` (
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
-- Dumping data for table `Workgroup`
--

LOCK TABLES `Workgroup` WRITE;
/*!40000 ALTER TABLE `Workgroup` DISABLE KEYS */;
-- INSERT INTO `Workgroup` VALUES (1,1,'Reproductive Sciences',1,'dba','2010-04-26 11:23:40','michaelm','2011-04-06 12:31:00',6),(2,2,'Custom Breeding',1,'dba','2010-10-25 16:41:32','amiller','2011-03-31 14:10:50',2),(3,3,'In Vivo',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(4,4,'Applications',1,'dba','2010-10-25 16:41:32','michaelm','2011-01-17 13:38:28',2),(5,4,'caBIG',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(6,4,'HPC',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(7,4,'JCMS',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(8,4,'QA',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(9,4,'Statistics',1,'dba','2010-10-25 16:41:32','dba','2010-10-25 16:41:32',1),(11,5,'Molecular Biology Service',1,'michaelm','2011-03-31 14:09:42','amiller','2011-03-31 14:43:47',9);
/*!40000 ALTER TABLE `Workgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkgroupUser`
--

DROP TABLE IF EXISTS `WorkgroupUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkgroupUser` (
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
-- Dumping data for table `WorkgroupUser`
--

LOCK TABLES `WorkgroupUser` WRITE;
/*!40000 ALTER TABLE `WorkgroupUser` DISABLE KEYS */;
-- INSERT INTO `WorkgroupUser` VALUES (1,1,6,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(2,1,11,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(3,4,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(4,4,4,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(5,4,5,'dba','2010-10-25 16:41:34','cnh','2010-10-25 17:26:18',1),(6,4,7,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(7,4,24,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(8,4,8,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(9,8,2,'dba','2010-10-25 16:41:34','kub','2010-12-14 09:55:54',1),(10,8,25,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(11,1,1,'dba','2010-10-25 16:41:34','amiller','2010-11-10 10:40:50',1),(12,1,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(13,1,25,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(14,1,4,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(15,1,5,'dba','2010-10-25 16:41:34','cnh','2010-10-25 17:26:02',1),(16,1,7,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(17,1,24,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(18,1,8,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(19,1,26,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(20,1,27,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(21,1,28,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(22,1,29,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(23,1,30,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(24,1,31,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(26,1,33,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(28,1,35,'cnh','2010-10-27 07:57:46','michaelm','2011-03-29 08:23:12',0),(29,1,12,'amiller','2010-11-02 08:54:25','amiller','2010-11-02 10:11:26',0),(31,1,38,'michaelm','2011-03-29 08:22:24','michaelm','2011-03-29 08:22:56',0),(32,1,37,'michaelm','2011-03-29 08:22:34','michaelm','2011-03-29 08:23:36',0),(33,11,39,'michaelm','2011-03-31 14:10:47','michaelm','2011-03-31 14:11:31',0),(34,2,28,'amiller','2011-03-31 14:10:50','amiller','2011-03-31 14:10:50',0),(35,11,42,'douglash','2011-03-31 14:23:57','douglash','2011-03-31 14:27:22',0),(36,11,41,'douglash','2011-03-31 14:24:38','douglash','2011-03-31 14:27:47',0),(37,11,40,'douglash','2011-03-31 14:25:17','douglash','2011-03-31 14:26:42',0),(38,11,1,'amiller','2011-03-31 14:43:47','amiller','2011-03-31 14:43:47',0),(39,1,43,'michaelm','2011-04-06 12:31:00','michaelm','2011-04-06 12:34:07',0);
/*!40000 ALTER TABLE `WorkgroupUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkgroupUserFunctionalArea`
--

DROP TABLE IF EXISTS `WorkgroupUserFunctionalArea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkgroupUserFunctionalArea` (
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
-- Dumping data for table `WorkgroupUserFunctionalArea`
--

LOCK TABLES `WorkgroupUserFunctionalArea` WRITE;
/*!40000 ALTER TABLE `WorkgroupUserFunctionalArea` DISABLE KEYS */;
-- INSERT INTO `WorkgroupUserFunctionalArea` VALUES (1,1,1,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(2,3,1,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(3,1,2,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(4,3,2,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(5,1,3,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(6,3,3,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(8,1,4,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(9,3,4,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(10,1,14,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(14,1,6,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(15,3,6,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(16,1,16,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(17,1,7,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(18,3,7,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(19,1,17,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(20,1,8,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(21,3,8,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(22,1,18,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(25,1,12,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(26,1,10,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(27,3,10,1,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(28,1,13,2,'dba','2010-10-25 16:41:34','dba','2010-10-25 16:41:34',1),(29,1,19,2,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(30,1,20,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(31,1,21,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(32,1,22,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(33,1,23,2,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(34,1,24,1,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(36,1,26,2,'dba','2010-10-25 16:41:49','dba','2010-10-25 16:41:49',1),(38,1,15,2,'cnh','2010-10-25 17:26:02','cnh','2010-10-25 17:26:02',0),(39,3,15,2,'cnh','2010-10-25 17:26:02','cnh','2010-10-25 17:26:02',0),(40,1,5,2,'cnh','2010-10-25 17:26:18','cnh','2010-10-25 17:26:18',0),(41,3,5,2,'cnh','2010-10-25 17:26:18','cnh','2010-10-25 17:26:18',0),(44,1,29,1,'amiller','2010-11-02 10:11:26','amiller','2010-11-02 10:11:26',0),(45,3,29,1,'amiller','2010-11-02 10:11:26','amiller','2010-11-02 10:11:26',0),(46,2,29,1,'amiller','2010-11-02 10:11:26','amiller','2010-11-02 10:11:26',0),(47,4,29,1,'amiller','2010-11-02 10:11:26','amiller','2010-11-02 10:11:26',0),(48,1,11,2,'amiller','2010-11-10 10:40:50','amiller','2010-11-10 10:40:50',0),(49,3,11,2,'amiller','2010-11-10 10:40:50','amiller','2010-11-10 10:40:50',0),(50,1,9,2,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(51,6,9,1,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(52,5,9,1,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(53,3,9,1,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(54,2,9,1,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(55,4,9,1,'kub','2010-12-14 09:55:54','kub','2010-12-14 09:55:54',0),(56,1,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(57,6,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(58,5,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(59,3,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(60,2,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(61,4,31,2,'michaelm','2011-03-29 08:22:56','michaelm','2011-03-29 08:22:56',0),(62,1,28,2,'michaelm','2011-03-29 08:23:12','michaelm','2011-03-29 08:23:12',0),(63,3,28,2,'michaelm','2011-03-29 08:23:12','michaelm','2011-03-29 08:23:12',0),(70,1,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(71,6,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(72,5,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(73,3,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(74,2,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(75,4,32,2,'michaelm','2011-03-29 08:23:36','michaelm','2011-03-29 08:23:36',0),(82,1,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(83,6,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(84,5,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(85,3,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(86,2,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(87,4,33,2,'michaelm','2011-03-31 14:11:31','michaelm','2011-03-31 14:11:31',0),(88,1,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(89,6,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(90,5,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(91,3,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(92,2,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(93,4,37,2,'douglash','2011-03-31 14:26:42','douglash','2011-03-31 14:26:42',0),(94,1,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(95,6,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(96,5,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(97,3,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(98,2,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(99,4,35,2,'douglash','2011-03-31 14:27:22','douglash','2011-03-31 14:27:22',0),(100,1,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(101,6,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(102,5,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(103,3,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(104,2,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(105,4,36,2,'douglash','2011-03-31 14:27:47','douglash','2011-03-31 14:27:47',0),(112,1,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0),(113,6,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0),(114,5,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0),(115,3,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0),(116,2,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0),(117,4,39,2,'michaelm','2011-04-06 12:34:07','michaelm','2011-04-06 12:34:07',0);
/*!40000 ALTER TABLE `WorkgroupUserFunctionalArea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkgroupVocabularySource`
--

DROP TABLE IF EXISTS `WorkgroupVocabularySource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkgroupVocabularySource` (
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
-- Dumping data for table `WorkgroupVocabularySource`
--

LOCK TABLES `WorkgroupVocabularySource` WRITE;
/*!40000 ALTER TABLE `WorkgroupVocabularySource` DISABLE KEYS */;
-- INSERT INTO `WorkgroupVocabularySource` VALUES (1,1,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(2,2,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(3,3,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(4,4,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(5,5,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(6,6,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(7,7,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(8,8,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(9,9,2,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1,NULL),(10,1,3,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,1),(11,2,4,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,1),(12,3,5,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,1),(13,11,2,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,NULL),(14,11,6,'dba','2011-04-06 16:41:45','dba','2011-04-06 16:41:45',1,1);
/*!40000 ALTER TABLE `WorkgroupVocabularySource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_AlleleClass`
--

DROP TABLE IF EXISTS `cv_AlleleClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_AlleleClass` (
  `_AlleleClass_key` int(11) NOT NULL AUTO_INCREMENT,
  `AlleleClass` varchar(16) NOT NULL,
  `Description` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_AlleleClass_key`),
  KEY `Refcv_VocabularySource1` (`_VocabularySource_key`),
  KEY `RefWorkgroup422` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource1` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup422` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_AlleleClass`
--

LOCK TABLES `cv_AlleleClass` WRITE;
/*!40000 ALTER TABLE `cv_AlleleClass` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_AlleleClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_BirthEventStatus`
--

DROP TABLE IF EXISTS `cv_BirthEventStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_BirthEventStatus` (
  `_BirthEventStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `BirthEventStatus` varchar(75) NOT NULL,
  `Abbreviation` varchar(5) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_BirthEventStatus_key`),
  KEY `Refcv_VocabularySource2` (`_VocabularySource_key`),
  KEY `RefWorkgroup423` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource2` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup423` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_BirthEventStatus`
--

LOCK TABLES `cv_BirthEventStatus` WRITE;
/*!40000 ALTER TABLE `cv_BirthEventStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_BirthEventStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_BreedingStatus`
--

DROP TABLE IF EXISTS `cv_BreedingStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_BreedingStatus` (
  `_BreedingStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `BreedingStatus` varchar(75) NOT NULL,
  `Abbreviation` varchar(1) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_BreedingStatus_key`),
  KEY `Refcv_VocabularySource3` (`_VocabularySource_key`),
  KEY `RefWorkgroup424` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource3` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup424` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_BreedingStatus`
--

LOCK TABLES `cv_BreedingStatus` WRITE;
/*!40000 ALTER TABLE `cv_BreedingStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_BreedingStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ContactPersonType`
--

DROP TABLE IF EXISTS `cv_ContactPersonType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ContactPersonType` (
  `_ContactPersonType_key` int(11) NOT NULL AUTO_INCREMENT,
  `ContactPersonType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ContactPersonType_key`),
  KEY `Refcv_VocabularySource4` (`_VocabularySource_key`),
  KEY `RefWorkgroup426` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource4` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup426` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ContactPersonType`
--

LOCK TABLES `cv_ContactPersonType` WRITE;
/*!40000 ALTER TABLE `cv_ContactPersonType` DISABLE KEYS */;
-- INSERT INTO `cv_ContactPersonType` VALUES (1,'Project Manager',1,1,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(2,'Outgoing Shipment Contact',1,0,2,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(3,'Importation Shipment Contact',1,0,3,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(4,'Donating Investigator',1,0,4,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(5,'Sales Representative',1,0,5,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(6,'Customer Service Representative',1,0,6,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(7,'Opportunity Development Manager',1,0,7,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ContactPersonType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ContainerType`
--

DROP TABLE IF EXISTS `cv_ContainerType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ContainerType` (
  `_ContainerType_key` int(11) NOT NULL AUTO_INCREMENT,
  `_VolumeUnit_key` int(11) NOT NULL,
  `ContainerType` varchar(75) NOT NULL,
  `Volume` decimal(18,0) DEFAULT NULL,
  `IsActive` int(11) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `MaxSamples` int(11) DEFAULT NULL,
  `MaxOrganisms` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ContainerType_key`),
  KEY `Ref123178` (`_VolumeUnit_key`),
  KEY `Refcv_VocabularySource5` (`_VocabularySource_key`),
  KEY `RefWorkgroup427` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource5` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_VolumeUnit178` FOREIGN KEY (`_VolumeUnit_key`) REFERENCES `cv_VolumeUnit` (`_VolumeUnit_key`),
  CONSTRAINT `RefWorkgroup427` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ContainerType`
--

LOCK TABLES `cv_ContainerType` WRITE;
/*!40000 ALTER TABLE `cv_ContainerType` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ContainerType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Country`
--

DROP TABLE IF EXISTS `cv_Country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Country` (
  `_Country_key` int(11) NOT NULL AUTO_INCREMENT,
  `Country` varchar(80) NOT NULL,
  `Abbreviation` varchar(5) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Country_key`),
  KEY `Refcv_VocabularySource6` (`_VocabularySource_key`),
  KEY `RefWorkgroup428` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource6` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup428` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Country`
--

LOCK TABLES `cv_Country` WRITE;
/*!40000 ALTER TABLE `cv_Country` DISABLE KEYS */;
-- INSERT INTO `cv_Country` VALUES (2,'UNITED ARAB EMIRATES','AE',1,0,65,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(3,'ARGENTINA','AR',1,0,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(4,'AUSTRIA','AT',1,0,3,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(5,'AUSTRALIA','AU',1,0,2,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(6,'BELGIUM','BE',1,0,4,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(7,'BULGARIA','BG',1,0,8,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(8,'BRAZIL','BR',1,0,6,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(9,'CANADA','CA',1,0,9,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(10,'SWITZERLAND','CH',1,0,61,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(11,'CHILE','CL',1,0,10,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(12,'CHINA','CN',1,0,11,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(13,'COLOMBIA','CO',1,0,12,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(14,'CYPRUS','CY',1,0,14,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(15,'CZECH REPUBLIC','CZ',1,0,15,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(16,'GERMANY','DE',1,0,22,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(17,'DENMARK','DK',1,0,16,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(18,'ESTONIA','EE',1,0,18,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(19,'EGYPT','EG',1,0,17,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(20,'SPAIN','ES',1,0,59,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(21,'FINLAND','FI',1,0,19,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(22,'FRANCE','FR',1,0,20,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(24,'GEORGIA','GE',1,0,21,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(25,'GREECE','GR',1,0,23,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(26,'HONG KONG','HK',1,0,24,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(27,'CROATIA','HR',1,0,13,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(28,'HUNGARY','HU',1,0,25,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(29,'INDONESIA','ID',1,0,27,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(30,'IRELAND','IE',1,0,28,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(31,'ISRAEL','IL',1,0,30,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(32,'ISLE OF MAN','IN',1,0,29,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(33,'ITALY','IT',1,0,31,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(34,'JORDAN','JO',1,0,33,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(35,'JAPAN','JP',1,0,32,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(36,'KENYA','KE',1,0,35,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(37,'KYRGYZSTAN','KG',1,0,37,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(38,'KOREA, REPUBLIC OF','KR',1,0,36,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(39,'KAZAKHSTAN','KZ',1,0,34,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(40,'LEBANON','LB',1,0,38,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(41,'LITHUANIA','LT',1,0,39,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(42,'LUXEMBOURG','LU',1,0,40,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(43,'MEXICO','MX',1,0,42,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(44,'MALAYSIA','MY',1,0,41,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(45,'NICARAGUA','NI',1,0,45,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(46,'NETHERLANDS','NL',1,0,44,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(47,'NORWAY','NO',1,0,46,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(48,'NEPAL','NZ',1,0,43,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(49,'PAKISTAN','PK',1,0,47,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(50,'POLAND','PL',1,0,49,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(51,'PUERTO RICO','PR',1,0,51,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(52,'PORTUGAL','PT',1,0,50,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(53,'ROMANIA','RO',1,0,52,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(54,'RUSSIAN FEDERATION','RU',1,0,53,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(55,'SAUDI ARABIA','SA',1,0,54,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(56,'SWEDEN','SE',1,0,60,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(57,'SINGAPORE','SG',1,0,55,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(58,'SLOVENIA','SI',1,0,56,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(59,'TURKEY','TR',1,0,63,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(60,'TAIWAN, PROVINCE OF CHINA','TW',1,0,62,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(61,'UNITED KINGDOM','UK',1,0,66,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(62,'UNITED STATES','US',1,1,67,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(63,'URUGUAY','UY',1,0,68,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(64,'SOUTH AFRICA','ZA',1,0,58,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(65,'ZAMBIA','ZM',1,0,70,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(66,'BRUNEI DARUSSALAM','BN',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(67,'ICELAND','IS',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(68,'PHILIPPINES','PH',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(69,'BELGRADE REPUBLIC OF SERBIA','RS',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(70,'SLOVKIA','SK',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(71,'UKRAINE','UA',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL),(72,'VIETNAM','VN',1,0,1,1,'dba','2011-06-13 12:41:37','dba','2011-06-13 12:41:37',1,0,2,NULL);
/*!40000 ALTER TABLE `cv_Country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CrossPurpose`
--

DROP TABLE IF EXISTS `cv_CrossPurpose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CrossPurpose` (
  `_CrossPurpose_key` int(11) NOT NULL AUTO_INCREMENT,
  `CrossPurpose` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_CrossPurpose_key`),
  KEY `Refcv_VocabularySource7` (`_VocabularySource_key`),
  KEY `RefWorkgroup429` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource7` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup429` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CrossPurpose`
--

LOCK TABLES `cv_CrossPurpose` WRITE;
/*!40000 ALTER TABLE `cv_CrossPurpose` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_CrossPurpose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CrossRetirementReason`
--

DROP TABLE IF EXISTS `cv_CrossRetirementReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CrossRetirementReason` (
  `_CrossRetirementReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `CrossRetirementReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_CrossRetirementReason_key`),
  KEY `Refcv_VocabularySource8` (`_VocabularySource_key`),
  KEY `RefWorkgroup430` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource8` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup430` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CrossRetirementReason`
--

LOCK TABLES `cv_CrossRetirementReason` WRITE;
/*!40000 ALTER TABLE `cv_CrossRetirementReason` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_CrossRetirementReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CrossType`
--

DROP TABLE IF EXISTS `cv_CrossType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CrossType` (
  `_CrossType_key` int(11) NOT NULL AUTO_INCREMENT,
  `CrossType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_CrossType_key`),
  KEY `Refcv_VocabularySource9` (`_VocabularySource_key`),
  KEY `RefWorkgroup431` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource9` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup431` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CrossType`
--

LOCK TABLES `cv_CrossType` WRITE;
/*!40000 ALTER TABLE `cv_CrossType` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_CrossType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_DamChangeReason`
--

DROP TABLE IF EXISTS `cv_DamChangeReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_DamChangeReason` (
  `_DamChangeReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `DamChangeReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_DamChangeReason_key`),
  KEY `Refcv_VocabularySource10` (`_VocabularySource_key`),
  KEY `RefWorkgroup432` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource10` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup432` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_DamChangeReason`
--

LOCK TABLES `cv_DamChangeReason` WRITE;
/*!40000 ALTER TABLE `cv_DamChangeReason` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_DamChangeReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_DataType`
--

DROP TABLE IF EXISTS `cv_DataType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_DataType` (
  `_DataType_key` int(11) NOT NULL AUTO_INCREMENT,
  `DataType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_DataType_key`),
  KEY `Refcv_VocabularySource11` (`_VocabularySource_key`),
  KEY `RefWorkgroup433` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource11` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup433` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_DataType`
--

LOCK TABLES `cv_DataType` WRITE;
/*!40000 ALTER TABLE `cv_DataType` DISABLE KEYS */;
-- INSERT INTO `cv_DataType` VALUES (1,'Numeric',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Text',1,0,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Date',1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Time',1,0,4,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(5,'Boolean',0,1,5,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(6,'Enumeration',0,1,6,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_DataType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Diet`
--

DROP TABLE IF EXISTS `cv_Diet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Diet` (
  `_Diet_key` int(11) NOT NULL AUTO_INCREMENT,
  `Diet` varchar(75) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Diet_key`),
  KEY `Refcv_VocabularySource12` (`_VocabularySource_key`),
  KEY `RefWorkgroup434` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource12` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup434` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Diet`
--

LOCK TABLES `cv_Diet` WRITE;
/*!40000 ALTER TABLE `cv_Diet` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_Diet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Epoch`
--

DROP TABLE IF EXISTS `cv_Epoch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Epoch` (
  `_Epoch_key` int(11) NOT NULL AUTO_INCREMENT,
  `Epoch` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Epoch_key`),
  KEY `Refcv_VocabularySource13` (`_VocabularySource_key`),
  KEY `RefWorkgroup435` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource13` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup435` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Epoch`
--

LOCK TABLES `cv_Epoch` WRITE;
/*!40000 ALTER TABLE `cv_Epoch` DISABLE KEYS */;
-- INSERT INTO `cv_Epoch` VALUES (1,'From Conception',1,0,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'From Birth',1,1,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'From Wean',1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_Epoch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ExitReason`
--

DROP TABLE IF EXISTS `cv_ExitReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ExitReason` (
  `_ExitReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `ExitReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ExitReason_key`),
  KEY `Refcv_VocabularySource14` (`_VocabularySource_key`),
  KEY `RefWorkgroup436` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource14` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup436` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ExitReason`
--

LOCK TABLES `cv_ExitReason` WRITE;
/*!40000 ALTER TABLE `cv_ExitReason` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ExitReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_FieldType`
--

DROP TABLE IF EXISTS `cv_FieldType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_FieldType` (
  `_FieldType_key` int(11) NOT NULL AUTO_INCREMENT,
  `FieldType` varchar(25) NOT NULL,
  `IsDataTypeRequired` tinyint(4) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_FieldType_key`),
  KEY `Ref181627` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup6271` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_FieldType`
--

LOCK TABLES `cv_FieldType` WRITE;
/*!40000 ALTER TABLE `cv_FieldType` DISABLE KEYS */;
-- INSERT INTO `cv_FieldType` VALUES (1,'Ignore',0,1,0,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0),(2,'Input',1,1,1,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0),(3,'Output',1,1,2,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0),(4,'Test Code',0,1,3,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0);
/*!40000 ALTER TABLE `cv_FieldType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_FileExtension`
--

DROP TABLE IF EXISTS `cv_FileExtension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_FileExtension` (
  `_FileExtension_key` int(11) NOT NULL AUTO_INCREMENT,
  `ExtensionName` varchar(10) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) NOT NULL DEFAULT '1',
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_FileExtension_key`),
  KEY `Ref181625` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup6251` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_FileExtension`
--

LOCK TABLES `cv_FileExtension` WRITE;
/*!40000 ALTER TABLE `cv_FileExtension` DISABLE KEYS */;
INSERT INTO `cv_FileExtension` VALUES (1,'csv',1,1,0,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0);
/*!40000 ALTER TABLE `cv_FileExtension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Generation`
--

DROP TABLE IF EXISTS `cv_Generation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Generation` (
  `_Generation_key` int(11) NOT NULL AUTO_INCREMENT,
  `Generation` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Generation_key`),
  KEY `Refcv_VocabularySource16` (`_VocabularySource_key`),
  KEY `RefWorkgroup438` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource16` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup438` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Generation`
--

LOCK TABLES `cv_Generation` WRITE;
/*!40000 ALTER TABLE `cv_Generation` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_Generation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GenotypeSource`
--

DROP TABLE IF EXISTS `cv_GenotypeSource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GenotypeSource` (
  `_GenotypeSource_key` int(11) NOT NULL AUTO_INCREMENT,
  `GenotypeSource` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_GenotypeSource_key`),
  KEY `Refcv_VocabularySource17` (`_VocabularySource_key`),
  KEY `RefWorkgroup439` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource17` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup439` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GenotypeSource`
--

LOCK TABLES `cv_GenotypeSource` WRITE;
/*!40000 ALTER TABLE `cv_GenotypeSource` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_GenotypeSource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GenotypeStatus`
--

DROP TABLE IF EXISTS `cv_GenotypeStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GenotypeStatus` (
  `_GenotypeStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `GenotypeStatus` varchar(75) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_GenotypeStatus_key`),
  KEY `Refcv_VocabularySource18` (`_VocabularySource_key`),
  KEY `RefWorkgroup440` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource18` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup440` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GenotypeStatus`
--

LOCK TABLES `cv_GenotypeStatus` WRITE;
/*!40000 ALTER TABLE `cv_GenotypeStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_GenotypeStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GenotypeSymbol`
--

DROP TABLE IF EXISTS `cv_GenotypeSymbol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GenotypeSymbol` (
  `_GenotypeSymbol_key` int(11) NOT NULL,
  `GenotypeSymbol` varchar(18) NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  PRIMARY KEY (`_GenotypeSymbol_key`),
  KEY `RefWorkgroup441` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup441` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GenotypeSymbol`
--

LOCK TABLES `cv_GenotypeSymbol` WRITE;
/*!40000 ALTER TABLE `cv_GenotypeSymbol` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_GenotypeSymbol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_HeritabilityMode`
--

DROP TABLE IF EXISTS `cv_HeritabilityMode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_HeritabilityMode` (
  `_HeritabilityMode_key` int(11) NOT NULL AUTO_INCREMENT,
  `HeritabilityMode` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_HeritabilityMode_key`),
  KEY `Refcv_VocabularySource19` (`_VocabularySource_key`),
  KEY `RefWorkgroup442` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource19` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup442` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_HeritabilityMode`
--

LOCK TABLES `cv_HeritabilityMode` WRITE;
/*!40000 ALTER TABLE `cv_HeritabilityMode` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_HeritabilityMode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_HeritabilityStatus`
--

DROP TABLE IF EXISTS `cv_HeritabilityStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_HeritabilityStatus` (
  `_HeritabilityStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `HeritabilityStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_HeritabilityStatus_key`),
  KEY `Refcv_VocabularySource20` (`_VocabularySource_key`),
  KEY `RefWorkgroup443` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource20` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup443` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_HeritabilityStatus`
--

LOCK TABLES `cv_HeritabilityStatus` WRITE;
/*!40000 ALTER TABLE `cv_HeritabilityStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_HeritabilityStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_KeywordIndex`
--

DROP TABLE IF EXISTS `cv_KeywordIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_KeywordIndex` (
  `_KeywordIndex_key` int(11) NOT NULL AUTO_INCREMENT,
  `_SuperKeywordIndex_key` int(11) DEFAULT NULL,
  `KeywordIndex` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_KeywordIndex_key`),
  KEY `Ref112164` (`_SuperKeywordIndex_key`),
  KEY `Refcv_VocabularySource21` (`_VocabularySource_key`),
  KEY `RefWorkgroup444` (`_Workgroup_key`),
  CONSTRAINT `Refcv_KeywordIndex164` FOREIGN KEY (`_SuperKeywordIndex_key`) REFERENCES `cv_KeywordIndex` (`_KeywordIndex_key`),
  CONSTRAINT `Refcv_VocabularySource21` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup444` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_KeywordIndex`
--

LOCK TABLES `cv_KeywordIndex` WRITE;
/*!40000 ALTER TABLE `cv_KeywordIndex` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_KeywordIndex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LimitType`
--

DROP TABLE IF EXISTS `cv_LimitType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LimitType` (
  `_LimitType_key` int(11) NOT NULL AUTO_INCREMENT,
  `LimitType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_LimitType_key`),
  KEY `Refcv_VocabularySource22` (`_VocabularySource_key`),
  KEY `RefWorkgroup445` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource22` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup445` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LimitType`
--

LOCK TABLES `cv_LimitType` WRITE;
/*!40000 ALTER TABLE `cv_LimitType` DISABLE KEYS */;
-- INSERT INTO `cv_LimitType` VALUES (1,'Principal Investigator',1,0,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Technician',1,1,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_LimitType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LineNameType`
--

DROP TABLE IF EXISTS `cv_LineNameType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LineNameType` (
  `_LineNameType_key` int(11) NOT NULL,
  `LineNameType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_LineNameType_key`),
  KEY `Refcv_VocabularySource23` (`_VocabularySource_key`),
  KEY `RefWorkgroup446` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource23` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup446` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LineNameType`
--

LOCK TABLES `cv_LineNameType` WRITE;
/*!40000 ALTER TABLE `cv_LineNameType` DISABLE KEYS */;
-- INSERT INTO `cv_LineNameType` VALUES (1,'Official Name',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Abbreviated Name',1,2,1,0,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Nickname',1,3,1,0,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_LineNameType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LineStatus`
--

DROP TABLE IF EXISTS `cv_LineStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LineStatus` (
  `_LineStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `LineStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `Description` varchar(255) DEFAULT NULL,
  `_Workgroup_key` int(11) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_LineStatus_key`),
  KEY `Refcv_VocabularySource24` (`_VocabularySource_key`),
  KEY `RefWorkgroup447` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource24` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup447` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LineStatus`
--

LOCK TABLES `cv_LineStatus` WRITE;
/*!40000 ALTER TABLE `cv_LineStatus` DISABLE KEYS */;
-- INSERT INTO `cv_LineStatus` VALUES (1,'Available',1,1,1,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Pending',1,0,2,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Cryopreserved Embryos',1,0,3,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Bankstock',1,0,4,'Bankstock represents Cryo strains from three, six, and nine thousand numbers.  See Cryo database for specific status information.',1,'channa','2011-06-13 12:24:05','channa','2011-06-13 12:24:05',1,NULL,3,NULL);
/*!40000 ALTER TABLE `cv_LineStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LineType`
--

DROP TABLE IF EXISTS `cv_LineType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LineType` (
  `_LineType_key` int(11) NOT NULL AUTO_INCREMENT,
  `LineType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_LineType_key`),
  KEY `Refcv_VocabularySource25` (`_VocabularySource_key`),
  KEY `RefWorkgroup448` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource25` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup448` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LineType`
--

LOCK TABLES `cv_LineType` WRITE;
/*!40000 ALTER TABLE `cv_LineType` DISABLE KEYS */;
-- INSERT INTO `cv_LineType` VALUES (1,'Inbred Strain',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Embryonic Stem Cells',1,0,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Phenotypic Mutants',1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_LineType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LocationType`
--

DROP TABLE IF EXISTS `cv_LocationType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LocationType` (
  `_LocationType_key` int(11) NOT NULL AUTO_INCREMENT,
  `_VolumeUnit_key` int(11) DEFAULT NULL,
  `LocationType` varchar(75) NOT NULL,
  `Volume` int(11) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_LocationType_key`),
  KEY `Ref123265` (`_VolumeUnit_key`),
  KEY `Refcv_VocabularySource26` (`_VocabularySource_key`),
  KEY `RefWorkgroup449` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource26` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_VolumeUnit265` FOREIGN KEY (`_VolumeUnit_key`) REFERENCES `cv_VolumeUnit` (`_VolumeUnit_key`),
  CONSTRAINT `RefWorkgroup449` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LocationType`
--

LOCK TABLES `cv_LocationType` WRITE;
/*!40000 ALTER TABLE `cv_LocationType` DISABLE KEYS */;
-- INSERT INTO `cv_LocationType` VALUES (1,NULL,'Building',NULL,NULL,1,1,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL),(2,NULL,'Room',NULL,NULL,1,2,1,'dba','2010-04-26 11:23:42','dba','2010-04-26 11:23:42',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_LocationType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MarkerRepository`
--

DROP TABLE IF EXISTS `cv_MarkerRepository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MarkerRepository` (
  `_MarkerRepository_key` int(11) NOT NULL AUTO_INCREMENT,
  `MarkerRepository` varchar(75) NOT NULL,
  `BaseURL` varchar(500) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `IsOfficialSource` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`_MarkerRepository_key`),
  KEY `Refcv_VocabularySource27` (`_VocabularySource_key`),
  KEY `RefWorkgroup450` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource27` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup450` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MarkerRepository`
--

LOCK TABLES `cv_MarkerRepository` WRITE;
/*!40000 ALTER TABLE `cv_MarkerRepository` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_MarkerRepository` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MarkerType`
--

DROP TABLE IF EXISTS `cv_MarkerType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MarkerType` (
  `_MarkerType_key` int(11) NOT NULL AUTO_INCREMENT,
  `MarkerType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_MarkerType_key`),
  KEY `Refcv_VocabularySource28` (`_VocabularySource_key`),
  KEY `RefWorkgroup451` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource28` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup451` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MarkerType`
--

LOCK TABLES `cv_MarkerType` WRITE;
/*!40000 ALTER TABLE `cv_MarkerType` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_MarkerType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_NamingConvention`
--

DROP TABLE IF EXISTS `cv_NamingConvention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_NamingConvention` (
  `_NamingConvention_key` int(11) NOT NULL AUTO_INCREMENT,
  `NamingConvention` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_NamingConvention_key`),
  KEY `Refcv_VocabularySource29` (`_VocabularySource_key`),
  KEY `RefWorkgroup452` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource29` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup452` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_NamingConvention`
--

LOCK TABLES `cv_NamingConvention` WRITE;
/*!40000 ALTER TABLE `cv_NamingConvention` DISABLE KEYS */;
-- INSERT INTO `cv_NamingConvention` VALUES (1,'Project ID',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_NamingConvention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_OrganismOrderStatus`
--

DROP TABLE IF EXISTS `cv_OrganismOrderStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_OrganismOrderStatus` (
  `_OrganismOrderStatus_key` int(11) NOT NULL,
  `OrganismOrderStatus` int(11) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_OrganismOrderStatus_key`),
  KEY `RefWorkgroup453` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup453` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_OrganismOrderStatus`
--

LOCK TABLES `cv_OrganismOrderStatus` WRITE;
/*!40000 ALTER TABLE `cv_OrganismOrderStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_OrganismOrderStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_OrganismOrigin`
--

DROP TABLE IF EXISTS `cv_OrganismOrigin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_OrganismOrigin` (
  `_OrganismOrigin_key` int(11) NOT NULL AUTO_INCREMENT,
  `OrganismOrigin` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_OrganismOrigin_key`),
  KEY `Refcv_VocabularySource30` (`_VocabularySource_key`),
  KEY `RefWorkgroup454` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource30` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup454` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_OrganismOrigin`
--

LOCK TABLES `cv_OrganismOrigin` WRITE;
/*!40000 ALTER TABLE `cv_OrganismOrigin` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_OrganismOrigin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_OrganismStatus`
--

DROP TABLE IF EXISTS `cv_OrganismStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_OrganismStatus` (
  `_OrganismStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `OrganismStatus` varchar(75) NOT NULL,
  `Abbreviation` varchar(1) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `IsExitStatus` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`_OrganismStatus_key`),
  KEY `Refcv_VocabularySource31` (`_VocabularySource_key`),
  KEY `RefWorkgroup455` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource31` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup455` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_OrganismStatus`
--

LOCK TABLES `cv_OrganismStatus` WRITE;
/*!40000 ALTER TABLE `cv_OrganismStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_OrganismStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ProcedureStatus`
--

DROP TABLE IF EXISTS `cv_ProcedureStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ProcedureStatus` (
  `_ProcedureStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProcedureStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `IsEndState` tinyint(4) NOT NULL DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ProcedureStatus_key`),
  KEY `Refcv_VocabularySource32` (`_VocabularySource_key`),
  KEY `RefWorkgroup456` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource32` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup456` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ProcedureStatus`
--

LOCK TABLES `cv_ProcedureStatus` WRITE;
/*!40000 ALTER TABLE `cv_ProcedureStatus` DISABLE KEYS */;
-- INSERT INTO `cv_ProcedureStatus` VALUES (1,'Unscheduled',1,1,1,0,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Incomplete',1,0,2,0,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Removed',1,0,3,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Cancelled',1,0,4,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(5,'Complete',1,0,5,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ProcedureStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ProjectStatus`
--

DROP TABLE IF EXISTS `cv_ProjectStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ProjectStatus` (
  `_ProjectStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProjectStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `IsEndState` tinyint(4) NOT NULL DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ProjectStatus_key`),
  KEY `Refcv_VocabularySource33` (`_VocabularySource_key`),
  KEY `RefWorkgroup457` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource33` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup457` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ProjectStatus`
--

LOCK TABLES `cv_ProjectStatus` WRITE;
/*!40000 ALTER TABLE `cv_ProjectStatus` DISABLE KEYS */;
-- INSERT INTO `cv_ProjectStatus` VALUES (1,'Active',1,1,1,0,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Cancelled',1,0,2,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Pending Complete',1,0,3,0,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Complete',1,0,4,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ProjectStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ProjectType`
--

DROP TABLE IF EXISTS `cv_ProjectType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ProjectType` (
  `_ProjectType_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProjectType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `Abbreviation` varchar(5) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ProjectType_key`),
  KEY `Refcv_VocabularySource34` (`_VocabularySource_key`),
  KEY `RefWorkgroup458` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource34` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup458` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ProjectType`
--

LOCK TABLES `cv_ProjectType` WRITE;
/*!40000 ALTER TABLE `cv_ProjectType` DISABLE KEYS */;
-- INSERT INTO `cv_ProjectType` VALUES (1,'Cryopreservation',1,1,NULL,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,3,NULL),(2,'Cryorecovery',1,0,NULL,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,3,NULL),(3,'Microinjection',1,0,NULL,8,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,3,NULL),(5,'Inventory Maintenance',1,0,NULL,7,1,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,2,NULL),(6,'Dedicated Supply',1,0,NULL,5,2,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,4,NULL),(7,'Custom Breeding',1,1,NULL,4,2,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,4,NULL),(8,'Diet Induced Obesity',1,0,NULL,6,2,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,4,NULL),(9,'Phenotyping Service',1,1,NULL,9,3,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,5,NULL),(10,'Compound Evaluation Service',1,0,NULL,1,3,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,5,NULL),(11,'Stem Cell Therapeutics',1,0,NULL,11,3,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1,0,5,NULL),(12,'Software Engineering',1,1,NULL,1,4,'dba','2011-04-06 10:03:09','dba','2011-04-06 10:03:09',1,NULL,7,NULL),(13,'Molecular Biology Service',1,1,NULL,1,11,'dba','2011-04-06 10:05:52','dba','2011-04-06 10:05:52',1,NULL,6,NULL);
/*!40000 ALTER TABLE `cv_ProjectType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_QueryType`
--

DROP TABLE IF EXISTS `cv_QueryType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_QueryType` (
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

--
-- Dumping data for table `cv_QueryType`
--

LOCK TABLES `cv_QueryType` WRITE;
/*!40000 ALTER TABLE `cv_QueryType` DISABLE KEYS */;
INSERT INTO `cv_QueryType` VALUES (1,'MouseQuery',NULL,NULL,NULL,NULL,'jcmsWeb Installer','2011-06-18 14:02:41','jcmsWeb Installer','2011-06-18 14:02:41',NULL,NULL,NULL,1),(2,'MatingQuery',NULL,NULL,NULL,NULL,'jcmsWeb Installer','2011-06-18 14:02:41','jcmsWeb Installer','2011-06-18 14:02:41',NULL,NULL,NULL,1),('3', 'Detail', NULL, NULL, NULL, NULL, 'jcmsWebInstaller', '2012-10-10 00:00:00', 'jcmsWebInstaller', '2012-10-10 00:00:00', NULL, NULL, NULL, '1'),('4', 'Mating', NULL, NULL, NULL, NULL, 'jcmsWebInstaller', '2012-10-10 00:00:00', 'jcmsWebInstaller', '2012-10-10 00:00:00', NULL, NULL, NULL, '1'),
('5', 'Wean', NULL, NULL, NULL, NULL, 'jcmsWebInstaller', '2012-10-10 00:00:00', 'jcmsWebInstaller', '2012-10-10 00:00:00', NULL, NULL, NULL, '1');
/*!40000 ALTER TABLE `cv_QueryType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ResourceType`
--

DROP TABLE IF EXISTS `cv_ResourceType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ResourceType` (
  `_ResourceType_key` int(11) NOT NULL AUTO_INCREMENT,
  `ResourceType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ResourceType_key`),
  KEY `Refcv_VocabularySource35` (`_VocabularySource_key`),
  KEY `RefWorkgroup459` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource35` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup459` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ResourceType`
--

LOCK TABLES `cv_ResourceType` WRITE;
/*!40000 ALTER TABLE `cv_ResourceType` DISABLE KEYS */;
-- INSERT INTO `cv_ResourceType` VALUES (1,'Technician',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Team',1,0,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Equipment',1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Personnel',1,1,1,11,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ResourceType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ReviewAction`
--

DROP TABLE IF EXISTS `cv_ReviewAction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ReviewAction` (
  `_ReviewAction_key` int(11) NOT NULL AUTO_INCREMENT,
  `ReviewAction` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `Level` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ReviewAction_key`),
  KEY `Refcv_VocabularySource36` (`_VocabularySource_key`),
  KEY `RefWorkgroup460` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource36` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup460` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ReviewAction`
--

LOCK TABLES `cv_ReviewAction` WRITE;
/*!40000 ALTER TABLE `cv_ReviewAction` DISABLE KEYS */;
-- INSERT INTO `cv_ReviewAction` VALUES (1,'No Action',1,1,1,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Repeat Task',1,0,2,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Schedule Secondary Task',1,0,3,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Refer to Expert',1,0,4,NULL,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ReviewAction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleStatus`
--

DROP TABLE IF EXISTS `cv_SampleStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleStatus` (
  `_SampleStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `SampleStatus` varchar(75) NOT NULL,
  `IsInStorage` tinyint(4) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_SampleStatus_key`),
  KEY `Refcv_VocabularySource37` (`_VocabularySource_key`),
  KEY `RefWorkgroup461` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource37` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup461` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleStatus`
--

LOCK TABLES `cv_SampleStatus` WRITE;
/*!40000 ALTER TABLE `cv_SampleStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_SampleStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleType`
--

DROP TABLE IF EXISTS `cv_SampleType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleType` (
  `_SampleType_key` int(11) NOT NULL AUTO_INCREMENT,
  `SampleType` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `Abbreviation` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_SampleType_key`),
  KEY `Refcv_VocabularySource38` (`_VocabularySource_key`),
  KEY `RefWorkgroup462` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource38` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup462` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleType`
--

LOCK TABLES `cv_SampleType` WRITE;
/*!40000 ALTER TABLE `cv_SampleType` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_SampleType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ScheduleReason`
--

DROP TABLE IF EXISTS `cv_ScheduleReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ScheduleReason` (
  `_ScheduleReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `ScheduleReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ScheduleReason_key`),
  KEY `Refcv_VocabularySource39` (`_VocabularySource_key`),
  KEY `RefWorkgroup463` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource39` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup463` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ScheduleReason`
--

LOCK TABLES `cv_ScheduleReason` WRITE;
/*!40000 ALTER TABLE `cv_ScheduleReason` DISABLE KEYS */;
-- INSERT INTO `cv_ScheduleReason` VALUES (1,'Standard',1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Custom',1,0,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Repeat Task',1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Secondary Task',1,0,4,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_ScheduleReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SectionType`
--

DROP TABLE IF EXISTS `cv_SectionType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SectionType` (
  `_SectionType_key` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) NOT NULL DEFAULT '1',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_SectionType_key`),
  KEY `Ref181626` (`_Workgroup_key`),
  CONSTRAINT `RefWorkgroup6261` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SectionType`
--

LOCK TABLES `cv_SectionType` WRITE;
/*!40000 ALTER TABLE `cv_SectionType` DISABLE KEYS */;
-- INSERT INTO `cv_SectionType` VALUES (1,'Header',1,0,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0),(2,'Data',1,1,1,'dba','2011-02-10 16:13:08','dba','2011-02-10 16:13:08',0);
/*!40000 ALTER TABLE `cv_SectionType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ServiceCategory`
--

DROP TABLE IF EXISTS `cv_ServiceCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ServiceCategory` (
  `_ServiceCategory_key` int(11) NOT NULL AUTO_INCREMENT,
  `ServiceCategory` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ServiceCategory_key`),
  KEY `Refcv_VocabularySource40` (`_VocabularySource_key`),
  KEY `RefWorkgroup464` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource40` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup464` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ServiceCategory`
--

LOCK TABLES `cv_ServiceCategory` WRITE;
/*!40000 ALTER TABLE `cv_ServiceCategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ServiceCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ServiceOrderCancelReason`
--

DROP TABLE IF EXISTS `cv_ServiceOrderCancelReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ServiceOrderCancelReason` (
  `_ServiceOrderCancelReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `ServcieOrderCancelReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ServiceOrderCancelReason_key`),
  KEY `Refcv_VocabularySource41` (`_VocabularySource_key`),
  KEY `RefWorkgroup467` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource41` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup467` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ServiceOrderCancelReason`
--

LOCK TABLES `cv_ServiceOrderCancelReason` WRITE;
/*!40000 ALTER TABLE `cv_ServiceOrderCancelReason` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ServiceOrderCancelReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ServiceOrderItemStatus`
--

DROP TABLE IF EXISTS `cv_ServiceOrderItemStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ServiceOrderItemStatus` (
  `_ServiceOrderItemStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `ServiceOrderItemStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ServiceOrderItemStatus_key`),
  KEY `Refcv_VocabularySource42` (`_VocabularySource_key`),
  KEY `RefWorkgroup468` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource42` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup468` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ServiceOrderItemStatus`
--

LOCK TABLES `cv_ServiceOrderItemStatus` WRITE;
/*!40000 ALTER TABLE `cv_ServiceOrderItemStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ServiceOrderItemStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ServiceOrderStatus`
--

DROP TABLE IF EXISTS `cv_ServiceOrderStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ServiceOrderStatus` (
  `_ServiceOrderStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `ServiceOrderStatus` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `IsEndState` tinyint(4) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_ServiceOrderStatus_key`),
  KEY `Refcv_VocabularySource43` (`_VocabularySource_key`),
  KEY `RefWorkgroup469` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource43` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup469` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ServiceOrderStatus`
--

LOCK TABLES `cv_ServiceOrderStatus` WRITE;
/*!40000 ALTER TABLE `cv_ServiceOrderStatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_ServiceOrderStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Sex`
--

DROP TABLE IF EXISTS `cv_Sex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Sex` (
  `_Sex_key` int(11) NOT NULL AUTO_INCREMENT,
  `Sex` varchar(75) NOT NULL,
  `Abbreviation` varchar(2) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Sex_key`),
  KEY `Refcv_VocabularySource44` (`_VocabularySource_key`),
  KEY `RefWorkgroup470` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource44` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup470` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Sex`
--

LOCK TABLES `cv_Sex` WRITE;
/*!40000 ALTER TABLE `cv_Sex` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_Sex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SireChangeReason`
--

DROP TABLE IF EXISTS `cv_SireChangeReason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SireChangeReason` (
  `_SireChangeReason_key` int(11) NOT NULL AUTO_INCREMENT,
  `SireChangeReason` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_SireChangeReason_key`),
  KEY `Refcv_VocabularySource45` (`_VocabularySource_key`),
  KEY `RefWorkgroup471` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource45` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup471` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SireChangeReason`
--

LOCK TABLES `cv_SireChangeReason` WRITE;
/*!40000 ALTER TABLE `cv_SireChangeReason` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_SireChangeReason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_State`
--

DROP TABLE IF EXISTS `cv_State`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_State` (
  `_State_key` int(11) NOT NULL AUTO_INCREMENT,
  `_Country_key` int(11) NOT NULL,
  `StateName` varchar(50) DEFAULT NULL,
  `Abbreviation` varchar(5) NOT NULL,
  `IsActive` tinyint(4) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `IsDefault` tinyint(4) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `ElementID` varchar(18) DEFAULT NULL,
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  PRIMARY KEY (`_State_key`),
  KEY `Refcv_Country1` (`_Country_key`),
  KEY `RefWorkgroup472` (`_Workgroup_key`),
  KEY `Refcv_VocabularySource60` (`_VocabularySource_key`),
  CONSTRAINT `Refcv_Country1` FOREIGN KEY (`_Country_key`) REFERENCES `cv_Country` (`_Country_key`),
  CONSTRAINT `Refcv_VocabularySource60` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup472` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_State`
--

LOCK TABLES `cv_State` WRITE;
/*!40000 ALTER TABLE `cv_State` DISABLE KEYS */;
-- INSERT INTO `cv_State` VALUES (1,9,NULL,'ON',1,42,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(2,9,NULL,'BC',1,5,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(3,9,NULL,'QC',1,47,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(5,9,NULL,'NL',1,35,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(6,9,NULL,'NS',1,37,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(7,9,NULL,'SK',1,51,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(8,9,NULL,'AB',1,1,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(9,9,NULL,'MB',1,22,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(10,9,NULL,'PE',1,45,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(11,62,NULL,'AZ',1,4,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(12,62,NULL,'NC',1,30,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(13,62,NULL,'OH',1,40,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(14,62,NULL,'NJ',1,34,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(15,62,NULL,'NV',1,38,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(16,62,NULL,'VA',1,55,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(17,62,NULL,'WA',1,57,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(18,62,NULL,'MS',1,28,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(19,62,NULL,'IA',1,14,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(20,62,NULL,'LA',1,20,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(21,62,NULL,'VT',1,56,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(22,62,NULL,'MT',1,29,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(23,62,NULL,'HI',1,13,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(24,62,NULL,'TX',1,53,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(25,62,NULL,'ND',1,31,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(26,62,NULL,'NY',1,39,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(27,62,NULL,'WY',1,60,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(28,62,NULL,'NM',1,36,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(29,62,NULL,'AL',1,2,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(30,62,NULL,'MO',1,27,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(31,62,NULL,'IL',1,16,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(32,62,NULL,'CA',1,6,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(33,62,NULL,'KS',1,18,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(34,62,NULL,'FL',1,11,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(35,62,NULL,'ME',1,24,1,1,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(36,62,NULL,'NE',1,32,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(37,62,NULL,'SC',1,49,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(38,62,NULL,'DE',1,10,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(39,62,NULL,'OR',1,43,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(40,62,NULL,'PR',1,46,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(41,62,NULL,'MN',1,26,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(42,62,NULL,'ID',1,15,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(43,62,NULL,'DC',1,9,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(44,62,NULL,'PA',1,44,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(45,62,NULL,'GA',1,12,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(46,62,NULL,'RI',1,48,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(47,62,NULL,'MD',1,23,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(48,62,NULL,'TN',1,52,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(49,62,NULL,'AR',1,3,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(50,62,NULL,'MI',1,25,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(51,62,NULL,'CT',1,8,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(52,62,NULL,'OK',1,41,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(53,62,NULL,'WI',1,58,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(54,62,NULL,'UT',1,54,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(55,62,NULL,'CO',1,7,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(56,62,NULL,'KY',1,19,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(57,62,NULL,'NH',1,33,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(58,62,NULL,'SD',1,50,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(59,62,NULL,'WV',1,59,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(60,62,NULL,'MA',1,21,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2),(61,62,NULL,'IN',1,17,1,0,'dba','2010-04-26 11:23:43','dba','2010-04-26 11:23:43',1,NULL,NULL,2);
/*!40000 ALTER TABLE `cv_State` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Subcolony`
--

DROP TABLE IF EXISTS `cv_Subcolony`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Subcolony` (
  `_Subcolony_key` int(11) NOT NULL AUTO_INCREMENT,
  `Subcolony` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Subcolony_key`),
  KEY `Refcv_VocabularySource46` (`_VocabularySource_key`),
  KEY `RefWorkgroup473` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource46` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup473` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Subcolony`
--

LOCK TABLES `cv_Subcolony` WRITE;
/*!40000 ALTER TABLE `cv_Subcolony` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_Subcolony` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Taxon`
--

DROP TABLE IF EXISTS `cv_Taxon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Taxon` (
  `_Taxon_key` int(11) NOT NULL AUTO_INCREMENT,
  `Taxon` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  `CommonName` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_Taxon_key`),
  KEY `Refcv_VocabularySource47` (`_VocabularySource_key`),
  KEY `RefWorkgroup474` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource47` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup474` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Taxon`
--

LOCK TABLES `cv_Taxon` WRITE;
/*!40000 ALTER TABLE `cv_Taxon` DISABLE KEYS */;
-- INSERT INTO `cv_Taxon` VALUES (1,'Mus musculus',1,1,1,1,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1,NULL,2,'10090','Mouse');
/*!40000 ALTER TABLE `cv_Taxon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_TimeUnit`
--

DROP TABLE IF EXISTS `cv_TimeUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_TimeUnit` (
  `_TimeUnit_key` int(11) NOT NULL AUTO_INCREMENT,
  `TimeUnit` varchar(75) NOT NULL,
  `Abbreviation` varchar(5) DEFAULT NULL,
  `MinutesPerUnit` int(11) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT '0',
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_TimeUnit_key`),
  KEY `Refcv_VocabularySource48` (`_VocabularySource_key`),
  KEY `RefWorkgroup475` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource48` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup475` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_TimeUnit`
--

LOCK TABLES `cv_TimeUnit` WRITE;
/*!40000 ALTER TABLE `cv_TimeUnit` DISABLE KEYS */;
-- INSERT INTO `cv_TimeUnit` VALUES (1,'Minute','Min',1,1,1,1,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(2,'Hour','Hr',60,1,0,2,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(3,'Day','Day',1440,1,0,3,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL),(4,'Week','Wk',10080,1,0,4,1,'dba','2010-04-26 11:23:40','dba','2010-04-26 11:23:40',1,NULL,2,NULL);
/*!40000 ALTER TABLE `cv_TimeUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_URLServer`
--

DROP TABLE IF EXISTS `cv_URLServer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_URLServer` (
  `_URLServer_key` int(11) NOT NULL AUTO_INCREMENT,
  `ProtocolAndServer` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT '1',
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_URLServer_key`),
  KEY `Refcv_VocabularySource49` (`_VocabularySource_key`),
  KEY `RefWorkgroup476` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource49` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup476` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_URLServer`
--

LOCK TABLES `cv_URLServer` WRITE;
/*!40000 ALTER TABLE `cv_URLServer` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_URLServer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_VocabularySource`
--

DROP TABLE IF EXISTS `cv_VocabularySource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_VocabularySource` (
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
-- Dumping data for table `cv_VocabularySource`
--

LOCK TABLES `cv_VocabularySource` WRITE;
/*!40000 ALTER TABLE `cv_VocabularySource` DISABLE KEYS */;
-- INSERT INTO `cv_VocabularySource` VALUES (1,'NCBI Taxonomy Database','www.ncbi.nlm.nih.gov/Taxonomy/','1',1,2,0,0,'dba','2010-04-26 11:23:41','dba','2010-04-26 11:23:41',1),(2,'JOES','www.jax.org','1',1,1,0,1,'dba','2010-10-25 16:41:43','dba','2010-10-25 16:41:43',1),(3,'Reproductive Sciences Specific',NULL,'1',1,3,0,0,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1),(4,'Custom Breeding Specific',NULL,'1',1,4,0,0,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1),(5,'In Vivo Specific',NULL,'1',1,5,0,0,'dba','2010-10-25 16:41:45','dba','2010-10-25 16:41:45',1),(6,'Molecular Biology Service Specific',NULL,'1',1,10,0,0,'dba','2011-04-06 16:41:45','dba','2011-04-06 16:41:45',1),(7,'Applications Specific',NULL,'1',1,7,0,0,'dba','2011-04-06 16:41:45','dba','2011-04-06 16:41:45',1),(8,'JAX Public',NULL,'1',1,6,0,0,'dba','2011-06-13 12:24:07','dba','2011-06-13 12:24:07',1),(9,'JAX Private',NULL,'1',1,7,0,0,'dba','2011-06-13 12:24:07','dba','2011-06-13 12:24:07',1),(10,'JAX Confidential',NULL,'1',1,8,0,0,'dba','2011-06-13 12:24:07','dba','2011-06-13 12:24:07',1),(11,'JAX Public',NULL,'1',1,6,0,0,'dba','2011-06-13 12:37:06','dba','2011-06-13 12:37:06',1),(12,'JAX Private',NULL,'1',1,7,0,0,'dba','2011-06-13 12:37:06','dba','2011-06-13 12:37:06',1),(13,'JAX Confidential',NULL,'1',1,8,0,0,'dba','2011-06-13 12:37:06','dba','2011-06-13 12:37:06',1),(14,'JAX Public',NULL,'1',1,6,0,0,'dba','2011-06-13 12:40:48','dba','2011-06-13 12:40:48',1),(15,'JAX Private',NULL,'1',1,7,0,0,'dba','2011-06-13 12:40:48','dba','2011-06-13 12:40:48',1),(16,'JAX Confidential',NULL,'1',1,8,0,0,'dba','2011-06-13 12:40:48','dba','2011-06-13 12:40:48',1),(17,'JAX Public',NULL,'1',1,6,0,0,'dba','2011-06-13 12:41:35','dba','2011-06-13 12:41:35',1),(18,'JAX Private',NULL,'1',1,7,0,0,'dba','2011-06-13 12:41:35','dba','2011-06-13 12:41:35',1),(19,'JAX Confidential',NULL,'1',1,8,0,0,'dba','2011-06-13 12:41:35','dba','2011-06-13 12:41:35',1);
/*!40000 ALTER TABLE `cv_VocabularySource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_VolumeUnit`
--

DROP TABLE IF EXISTS `cv_VolumeUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_VolumeUnit` (
  `_VolumeUnit_key` int(11) NOT NULL AUTO_INCREMENT,
  `VolumeUnit` varchar(75) NOT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_VolumeUnit_key`),
  KEY `Refcv_VocabularySource50` (`_VocabularySource_key`),
  KEY `RefWorkgroup478` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource50` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup478` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_VolumeUnit`
--

LOCK TABLES `cv_VolumeUnit` WRITE;
/*!40000 ALTER TABLE `cv_VolumeUnit` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_VolumeUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_WeanDirective`
--

DROP TABLE IF EXISTS `cv_WeanDirective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_WeanDirective` (
  `_WeanDirective_key` int(11) NOT NULL AUTO_INCREMENT,
  `WeanDirective` varchar(500) DEFAULT NULL,
  `IsActive` tinyint(4) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `IsDefault` tinyint(4) DEFAULT NULL,
  `CreatedBy` varchar(128) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(128) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `IsDeprecated` tinyint(4) DEFAULT NULL,
  `_VocabularySource_key` int(11) DEFAULT NULL,
  `ElementID` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`_WeanDirective_key`),
  KEY `Refcv_VocabularySource51` (`_VocabularySource_key`),
  KEY `RefWorkgroup479` (`_Workgroup_key`),
  CONSTRAINT `Refcv_VocabularySource51` FOREIGN KEY (`_VocabularySource_key`) REFERENCES `cv_VocabularySource` (`_VocabularySource_key`),
  CONSTRAINT `RefWorkgroup479` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_WeanDirective`
--

LOCK TABLES `cv_WeanDirective` WRITE;
/*!40000 ALTER TABLE `cv_WeanDirective` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_WeanDirective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbInfo`
--

DROP TABLE IF EXISTS `dbInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbInfo` (
  `_dbinfo_key` int(11) NOT NULL,
  `majorVersion` int(11) NOT NULL,
  `minorVersion` int(11) NOT NULL,
  `bugFixVersion` int(11) DEFAULT NULL,
  `buildVersion` int(11) DEFAULT NULL,
  `releaseDate` datetime NOT NULL,
  `releaseType` varchar(32) DEFAULT NULL,
  `releaseNotes` varchar(1024) DEFAULT NULL,
  `databaseReleaseNum` varchar(256) NOT NULL,
  PRIMARY KEY (`_dbinfo_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbInfo`
--

LOCK TABLES `dbInfo` WRITE;
/*!40000 ALTER TABLE `dbInfo` DISABLE KEYS */;
INSERT INTO `dbInfo` VALUES (1,3,3,1,1,'2011-06-25 06:43:59','','JCMS Web tables added','54');
/*!40000 ALTER TABLE `dbInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-25  6:45:30

-- ==================================================================
-- Service Order does not require an inquiry in the new design
-- ==================================================================
ALTER TABLE ServiceOrder MODIFY COLUMN _Inquiry_key INT(11) DEFAULT NULL;

-- ==================================================================
-- Add Procedure Outcome cv table
-- ==================================================================

CREATE TABLE cv_ProcedureOutcome(
    _ProcedureOutcome_key         INT            NOT NULL AUTO_INCREMENT,
    ProcedureOutcome              VARCHAR(75)    NOT NULL,
    IsActive                 TINYINT        NULL,
    IsDefault                TINYINT        NULL,
    SortOrder                INT            NULL,
    _VocabularySource_key    INT            NULL,
    ElementID                VARCHAR(18)    NULL,
    IsDeprecated             TINYINT        NULL,
    _Workgroup_key           INT            NOT NULL,
    CreatedBy                VARCHAR(18)    NOT NULL,
    DateCreated              DATETIME       NOT NULL,
    ModifiedBy               VARCHAR(18)    NOT NULL,
    DateModified             DATETIME       NOT NULL,
    Version                  INT            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ProcedureOutcome_key)
)
;
ALTER TABLE cv_ProcedureOutcome ADD CONSTRAINT RefProcedureOutcome1
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;
ALTER TABLE cv_ProcedureOutcome ADD CONSTRAINT RefProcedureOutcome2
    FOREIGN KEY (_VocabularySource_key)
    REFERENCES cv_VocabularySource(_VocabularySource_key)
;

-- ==================================================================
-- Add ServiceOrderDefinition table
-- ==================================================================

CREATE TABLE ServiceOrderDefinition (
  _ServiceOrderDefinition_key INTEGER NOT NULL AUTO_INCREMENT,
  ServiceOrder VARCHAR(100) NOT NULL,
  isActive TINYINT NOT NULL DEFAULT 1,
  _Workgroup_key INTEGER NOT NULL,
  CreatedBy VARCHAR(18) NOT NULL,
  DateCreated DATETIME NOT NULL,
  ModifiedBy VARCHAR(18) NOT NULL,
  DateModified DATETIME NOT NULL,
  PRIMARY KEY (_ServiceOrderDefinition_key)
)
ENGINE = InnoDB
;

ALTER TABLE ServiceOrderDefinition ADD CONSTRAINT RefSODWorkgroup1
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;


-- ==================================================================
-- Add ServiceOrderDefinitionProtocol table
-- ==================================================================

CREATE TABLE ServiceOrderDefinitionProtocol (
  _ServiceOrderDefinitionProtocol_key INTEGER NOT NULL AUTO_INCREMENT,
  _ServiceOrderDefinition_key INTEGER NOT NULL,
  _MethodVersion_key INTEGER NOT NULL,
  _ProcedureDefinitionVersion_key INTEGER NOT NULL,
  SortOrder INTEGER NOT NULL,
  _Workgroup_key INTEGER NOT NULL,
  CreatedBy VARCHAR(18) NOT NULL,
  DateCreated DATETIME NOT NULL,
  ModifiedBy VARCHAR(18) NOT NULL,
  DateModified DATETIME NOT NULL,
  PRIMARY KEY (_ServiceOrderDefinitionProtocol_key)
)
ENGINE = InnoDB
;

ALTER TABLE ServiceOrderDefinitionProtocol ADD CONSTRAINT RefSODPServiceOrderDefinition1
    FOREIGN KEY (_ServiceOrderDefinition_key)
    REFERENCES ServiceOrderDefinition(_ServiceOrderDefinition_key)
;

ALTER TABLE ServiceOrderDefinitionProtocol ADD CONSTRAINT RefSODPMethodVersion1
    FOREIGN KEY (_MethodVersion_key)
    REFERENCES MethodVersion(_MethodVersion_key)
;

ALTER TABLE ServiceOrderDefinitionProtocol ADD CONSTRAINT RefSODPProcedureDefinitionVersion1
    FOREIGN KEY (_ProcedureDefinitionVersion_key)
    REFERENCES ProcedureDefinitionVersion(_ProcedureDefinitionVersion_key)
;

ALTER TABLE ServiceOrderDefinitionProtocol ADD CONSTRAINT RefSODPWorkgroup1
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;

-- ==================================================================
-- Add ServiceItemServiceOrderDefinition table
-- ==================================================================

CREATE TABLE ServiceItemServiceOrderDefinition (
  _ServiceItemServiceOrderDefinition_key INTEGER NOT NULL AUTO_INCREMENT,
  _ServiceItem_key INTEGER NOT NULL,
  _ServiceOrderDefinition_key INTEGER NOT NULL,
  _Workgroup_key INTEGER NOT NULL,
  CreatedBy VARCHAR(18) NOT NULL,
  DateCreated DATETIME NOT NULL,
  ModifiedBy VARCHAR(18) NOT NULL,
  DateModified DATETIME NOT NULL,
  PRIMARY KEY (_ServiceItemServiceOrderDefinition_key)
)
ENGINE = InnoDB
;

ALTER TABLE ServiceItemServiceOrderDefinition ADD CONSTRAINT RefSISODServiceItem1
    FOREIGN KEY (_ServiceItem_key)
    REFERENCES ServiceItem(_ServiceItem_key)
;

ALTER TABLE ServiceItemServiceOrderDefinition ADD CONSTRAINT RefSISODServiceOrderDefinition1
    FOREIGN KEY (_ServiceOrderDefinition_key)
    REFERENCES ServiceOrderDefinition(_ServiceOrderDefinition_key)
;

ALTER TABLE ServiceItemServiceOrderDefinition ADD CONSTRAINT RefSISODWorkgroup1
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;


ALTER TABLE ServiceOrderDefinition ADD COLUMN Version INTEGER NOT NULL;
ALTER TABLE ServiceOrderDefinitionProtocol ADD COLUMN Version INTEGER NOT NULL;
ALTER TABLE ServiceItemServiceOrderDefinition ADD COLUMN Version INTEGER NOT NULL;


-- ==================================================================
-- Add varchar min and max columns for inputs and outputs
-- ==================================================================
ALTER TABLE Input ADD COLUMN ValidationMin VARCHAR(256) AFTER IsMultiSelectEnumeration;
ALTER TABLE Input ADD COLUMN ValidationMax VARCHAR(256) AFTER ValidationMin;
ALTER TABLE Output ADD COLUMN ValidationMin VARCHAR(256) AFTER IsMultiSelectEnumeration;
ALTER TABLE Output ADD COLUMN ValidationMax VARCHAR(256) AFTER ValidationMin;


-- ==================================================================
-- Drop obsolete tables and fields
-- ==================================================================
ALTER TABLE ServiceOrderItem DROP COLUMN AdjustedPrice;
ALTER TABLE ServiceOrderItem DROP COLUMN AccountNumber;
ALTER TABLE ServiceOrderItem DROP FOREIGN KEY RefServiceOrderVersion188;
ALTER TABLE ServiceOrderItem DROP COLUMN _ServiceOrderVersion_key;
ALTER TABLE SampleOrder DROP FOREIGN KEY RefServiceOrderVersion216;
ALTER TABLE SampleOrder DROP COLUMN _ServiceOrderVersion_key;
ALTER TABLE OrganismOrder DROP FOREIGN KEY RefServiceOrderVersion200;
ALTER TABLE OrganismOrder DROP COLUMN _ServiceOrderVersion_key;

DROP TABLE ServiceOrderVersion;

ALTER TABLE ServiceOrderDefinitionProtocol DROP COLUMN _ProcedureDefinitionVersion_key,
 DROP FOREIGN KEY RefSODPProcedureDefinitionVersion1;

DROP TABLE OrderLine;
DROP TABLE ServiceOrderDefinitionProtocol;
DROP TABLE ServiceItemServiceOrderDefinition;
DROP TABLE ServiceOrderDefinition;
DROP TABLE ServiceTemplateItem;
DROP TABLE ServiceTemplate;


-- ==================================================================
-- Add ServiceItem to MethodVersion intersection table
-- ==================================================================
DROP TABLE IF EXISTS `jcmsweb_db`.`ServiceItemMethod`;
CREATE TABLE  `jcmsweb_db`.`ServiceItemMethod` (
  `_ServiceItemMethod_key` int(11) NOT NULL AUTO_INCREMENT,
  `_ServiceItem_key` int(11) NOT NULL,
  `_MethodVersion_key` int(11) NOT NULL,
  `_Workgroup_key` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `CreatedBy` varchar(18) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `ModifiedBy` varchar(18) NOT NULL,
  `DateModified` datetime NOT NULL,
  `Version` int(11) NOT NULL,
  PRIMARY KEY (`_ServiceItemMethod_key`),
  KEY `FK_ServiceItem_1` (`_ServiceItem_key`),
  KEY `FK_ServiceItem_2` (`_MethodVersion_key`),
  KEY `FK_ServiceItem_3` (`_Workgroup_key`),
  CONSTRAINT `FK_ServiceItem_1` FOREIGN KEY (`_ServiceItem_key`) REFERENCES `ServiceItem` (`_ServiceItem_key`),
  CONSTRAINT `FK_ServiceItem_2` FOREIGN KEY (`_MethodVersion_key`) REFERENCES `MethodVersion` (`_MethodVersion_key`),
  CONSTRAINT `FK_ServiceItem_3` FOREIGN KEY (`_Workgroup_key`) REFERENCES `Workgroup` (`_Workgroup_key`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

-- ==================================================================
-- Add non task time cv table
-- ==================================================================
CREATE TABLE cv_NonTaskReason(
	_NonTaskReason_key   	INT            NOT NULL AUTO_INCREMENT,
	NonTaskReason         	VARCHAR(50),
	SortOrder		INT,
	IsActive		TINYINT,
	IsDefault		TINYINT,
	ModifiedBy              VARCHAR(18)    NOT NULL,
	DateModified            DATETIME       NOT NULL,
	Version                 INT            DEFAULT 1 NOT NULL,
	_VocabularySource_key   INT,
	ElementID               VARCHAR(18),
	IsDeprecated            TINYINT,
	_Workgroup_key          INT            NOT NULL,
	CreatedBy               VARCHAR(18)    NOT NULL,
	DateCreated             DATETIME       NOT NULL,
	PRIMARY KEY (_NonTaskReason_key)
)ENGINE=INNODB
;
ALTER TABLE cv_NonTaskReason ADD CONSTRAINT RefWorkgroupNonTaskReason 
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;
ALTER TABLE ScheduleNonTask
	ADD COLUMN _NonTaskReason_key	INT	NOT NULL AFTER _Resource_key
;
ALTER TABLE ScheduleNonTask ADD CONSTRAINT RefScheduleNonTask5
    FOREIGN KEY (_NonTaskReason_key)
    REFERENCES cv_NonTaskReason(_NonTaskReason_key)
;
ALTER TABLE ServiceItem
	MODIFY COLUMN ItemCode VARCHAR (100)
;
ALTER TABLE ServiceOrderItem
	ADD COLUMN _ServiceOrder_key	INT	NOT NULL AFTER _ServiceItem_key
;
ALTER TABLE ServiceOrderItem ADD CONSTRAINT RefServiceOrderItem1
    FOREIGN KEY (_ServiceOrder_key)
    REFERENCES ServiceOrder(_ServiceOrder_key)
;
ALTER TABLE ServiceOrderItem
	ADD COLUMN _Line_key	INT	NULL AFTER _ServiceOrder_key
;
ALTER TABLE ServiceOrderItem ADD CONSTRAINT RefServiceOrderItemLine1
    FOREIGN KEY (_Line_key)
    REFERENCES Line(_Line_key)
;
ALTER TABLE `jcmsweb_db`.`ServiceItem` CHANGE COLUMN `ServiceIItem` `ServiceItem` VARCHAR(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
;

-- ==================================================================
-- Update ExternalOrderID to ExternalProjectID
-- ==================================================================
ALTER TABLE ServiceOrder 
CHANGE ExternalOrderID ExternalProjectID VARCHAR(18) DEFAULT NULL
;

-- ==================================================================
-- Change ContactPersonContactPersonType to ServiceOrderContactPerson
-- ==================================================================
DROP TABLE ContactPersonContactPersonType
;
CREATE TABLE ServiceOrderContactPerson(
    _ServiceOrderContactPerson_key    INT            NOT NULL,
    _ContactPerson_key                INT            NOT NULL,
    _ContactPersonType_key            INT            NOT NULL,
    _ServiceOrder_key                 INT            NOT NULL,
    CreatedBy                         VARCHAR(18)    NOT NULL,
    DateCreated                       DATETIME       NOT NULL,
    ModifiedBy                        VARCHAR(18)    NOT NULL,
    DateModified                      DATETIME       NOT NULL,
    Version                           INT            DEFAULT 1 NOT NULL,
    _Workgroup_key                    INT            NOT NULL,
    PRIMARY KEY (_ServiceOrderContactPerson_key)
)ENGINE=INNODB
;

ALTER TABLE ServiceOrderContactPerson ADD CONSTRAINT RefServiceOrder3 
    FOREIGN KEY (_ServiceOrder_key)
    REFERENCES ServiceOrder(_ServiceOrder_key)
;

ALTER TABLE ServiceOrderContactPerson ADD CONSTRAINT RefContactPerson1 
    FOREIGN KEY (_ContactPerson_key)
    REFERENCES ContactPerson(_ContactPerson_key)
;

ALTER TABLE ServiceOrderContactPerson ADD CONSTRAINT Refcv_ContactPersonType2 
    FOREIGN KEY (_ContactPersonType_key)
    REFERENCES cv_ContactPersonType(_ContactPersonType_key)
;

-- ==================================================================
-- Cascade delete defaults
-- ==================================================================
ALTER TABLE InputDefault
 DROP FOREIGN KEY RefMethodProcedureDefinition143;

ALTER TABLE InputDefault ADD CONSTRAINT RefMethodProcedureDefinition143 FOREIGN KEY RefMethodProcedureDefinition143 (_MethodProcedureDefinition_key)
    REFERENCES MethodProcedureDefinition (_MethodProcedureDefinition_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;


ALTER TABLE OutputDefault
 DROP FOREIGN KEY RefMethodProcedureDefinition144;

ALTER TABLE OutputDefault ADD CONSTRAINT RefMethodProcedureDefinition144 FOREIGN KEY RefMethodProcedureDefinition144 (_MethodProcedureDefinition_key)
    REFERENCES MethodProcedureDefinition (_MethodProcedureDefinition_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;

ALTER TABLE ServiceItemMethod
 DROP FOREIGN KEY FK_ServiceItem_2;

ALTER TABLE ServiceItemMethod ADD CONSTRAINT FK_ServiceItem_2 FOREIGN KEY FK_ServiceItem_2 (_MethodVersion_key)
    REFERENCES MethodVersion (_MethodVersion_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;
-- ==================================================================
-- Not null to null changes to support creation of contacts and institutions from AX
-- ==================================================================
ALTER TABLE Institution 
MODIFY COLUMN _Country_key int(11) NULL
;
ALTER TABLE ContactPerson 
MODIFY COLUMN _Country_key int(11) NULL,
MODIFY COLUMN EmailAddress varchar(64) NULL,
MODIFY COLUMN _Institution_key int(11) NULL
;
-- ==================================================================
-- Add service order relationships for bill to, ship to, PI
-- ==================================================================
ALTER TABLE ServiceOrder 
    ADD COLUMN _BillToContact_key int(11),
    ADD CONSTRAINT RefContactPerson4
    FOREIGN KEY (_BillToContact_key)
    REFERENCES ContactPerson(_ContactPerson_key)
;
ALTER TABLE ServiceOrder 
    ADD COLUMN _PrimaryInvestigator_key int(11),
    ADD CONSTRAINT RefContactPerson5
    FOREIGN KEY (_PrimaryInvestigator_key)
    REFERENCES ContactPerson(_ContactPerson_key)
;
ALTER TABLE ServiceOrder 
    ADD COLUMN _BillToInstitution_key int(11),
    ADD CONSTRAINT RefInstitution7
    FOREIGN KEY (_BillToInstitution_key)
    REFERENCES Institution(_Institution_key)
;
-- ==================================================================
-- Recreate cv_ProcedureOutcome so Engine=InnoDB
-- ==================================================================
DROP TABLE IF EXISTS cv_ProcedureOutcome
;
CREATE TABLE  cv_ProcedureOutcome (
  _ProcedureOutcome_key int(11) NOT NULL AUTO_INCREMENT,
  ProcedureOutcome varchar(75) NOT NULL,
  IsActive tinyint(4) DEFAULT NULL,
  IsDefault tinyint(4) DEFAULT NULL,
  SortOrder int(11) DEFAULT NULL,
  _VocabularySource_key int(11) DEFAULT NULL,
  ElementID varchar(18) DEFAULT NULL,
  IsDeprecated tinyint(4) DEFAULT NULL,
  _Workgroup_key int(11) NOT NULL,
  CreatedBy varchar(18) NOT NULL,
  DateCreated datetime NOT NULL,
  ModifiedBy varchar(18) NOT NULL,
  DateModified datetime NOT NULL,
  Version int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (_ProcedureOutcome_key),
  KEY RefProcedureOutcome1 (_Workgroup_key),
  KEY RefProcedureOutcome2 (_VocabularySource_key)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1
;
-- ==================================================================
-- Add procedure outcome to procedure instance
-- ==================================================================
ALTER TABLE ProcedureInstance 
    ADD COLUMN _ProcedureOutcome_key int(11) AFTER _Location_key,
    ADD CONSTRAINT RefPIOutcome
    FOREIGN KEY (_ProcedureOutcome_key)
    REFERENCES cv_ProcedureOutcome(_ProcedureOutcome_key)
;
-- ==================================================================
-- Drop line key from ServiceOrderItem
-- ==================================================================
ALTER TABLE ServiceOrderItem DROP COLUMN _Line_key,
 	DROP FOREIGN KEY RefServiceOrderItemLine1;
-- ==================================================================
-- Project to ServiceOrderItem mapping table
-- ==================================================================
 CREATE TABLE ProjectServiceOrderItem(
     _ProjectServiceOrderItem_key       INT            AUTO_INCREMENT,
     _Project_key                    	INT         NOT NULL,
     _ServiceOrderItem_key           	INT         NOT NULL,
    _Workgroup_key                 	INT            NOT NULL,
         CreatedBy                      VARCHAR(18)    NOT NULL,
         DateCreated                    DATETIME       NOT NULL,
         ModifiedBy                     VARCHAR(18)    NOT NULL,
         DateModified                   DATETIME       NOT NULL,
         Version                        INT            DEFAULT 1 NOT NULL,
         PRIMARY KEY (_ProjectServiceOrderItem_key)
     )ENGINE=InnoDB
 ;
 ALTER TABLE ProjectServiceOrderItem ADD CONSTRAINT RefProjectSOI1
     FOREIGN KEY (_Project_key)
     REFERENCES Project(_Project_key)
 ; 
 ALTER TABLE ProjectServiceOrderItem ADD CONSTRAINT RefProjectSOI2 
     FOREIGN KEY (_ServiceOrderItem_key)
     REFERENCES ServiceOrderItem(_ServiceOrderItem_key)
 ;
 ALTER TABLE ProjectServiceOrderItem ADD CONSTRAINT RefProjectSOI3 
     FOREIGN KEY (_Workgroup_key)
     REFERENCES Workgroup(_Workgroup_key)
;

-- BUG: SortOrder should have been added in 43, changed in 62. It wasn't
-- ALTER TABLE OutputInstance DROP COLUMN SortOrder
-- ;

ALTER TABLE ProjectServiceOrderItem 
 DROP FOREIGN KEY `RefProjectSOI1`;

ALTER TABLE ProjectServiceOrderItem ADD CONSTRAINT `RefProjectSOI1` FOREIGN KEY `RefProjectSOI1` (_Project_key)
    REFERENCES Project (_Project_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;

-- ==================================================================
-- Make time unit nullable in protocols
-- ==================================================================
ALTER TABLE MethodProcedureDefinition 
MODIFY COLUMN _TimeUnit_key int(11) NULL
;
-- ==================================================================
-- Create a standard comments cv and link to procedure instance
-- ==================================================================
CREATE TABLE cv_StandardComment(
    _StandardComment_key     INT             AUTO_INCREMENT,
    StandardComment          VARCHAR(500)    NOT NULL,
    DateCreated              DATETIME        NOT NULL,
    ModifiedBy               VARCHAR(18)     NOT NULL,
    DateModified             DATETIME        NOT NULL,
    Version                  INT             DEFAULT 1 NOT NULL,
    _VocabularySource_key    INT,
    ElementID                VARCHAR(18),
    IsDeprecated             TINYINT,
    _Workgroup_key           INT             NOT NULL,
    IsActive                 TINYINT         DEFAULT 1 
                             CHECK (IS_ACTIVE = 0 or IS_ACTIVE = 1),
    SortOrder                INT             DEFAULT 0,
    IsDefault                TINYINT,
    CreatedBy                VARCHAR(18)     NOT NULL,
    PRIMARY KEY (_StandardComment_key)
)ENGINE=INNODB
;
ALTER TABLE ProcedureInstance 
ADD COLUMN _StandardComment_key int(11)
AFTER _ProcedureOutcome_key
;
ALTER TABLE ProcedureInstance 
ADD CONSTRAINT Refcv_StandardComment
    FOREIGN KEY (_StandardComment_key)
    REFERENCES cv_StandardComment(_StandardComment_key)
;

-- ==================================================================
-- Modify ServiceOrder columns
-- ==================================================================
ALTER TABLE ServiceOrder 
MODIFY COLUMN _ServiceOrderStatus_key int(11) NULL
;
-- ==================================================================
-- Remove deprecated Project columns
-- ==================================================================
ALTER TABLE Project DROP FOREIGN KEY RefInstitution1,
DROP COLUMN _Institution_key,
DROP COLUMN ExternalProjectID
;
-- ==================================================================
-- Add time tracking table
-- ==================================================================
CREATE TABLE TimeTracking(
    _TimeTracking_key         INT            AUTO_INCREMENT,
    _Resource_key             INT            NOT NULL,
    _ProcedureInstance_key    INT            NOT NULL,
    StartDateTime             DATETIME       NULL,
    EndDateTime               DATETIME       NULL,
    ElapsedTime               INT            NULL,
    _Workgroup_key            INT            NOT NULL,
    ModifiedBy                VARCHAR(18)    NOT NULL,
    DateModified              DATETIME       NOT NULL,
    CreatedBy                 VARCHAR(18)    NOT NULL,
    DateCreated               DATETIME,
    DateEnd                   DATETIME,
    Version                   INT            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_TimeTracking_key)
)ENGINE=INNODB
;
ALTER TABLE TimeTracking ADD CONSTRAINT RefResource2
    FOREIGN KEY (_Resource_key)
    REFERENCES Resource(_Resource_key)
;
ALTER TABLE TimeTracking ADD CONSTRAINT RefTTWorkgroup
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;

ALTER TABLE TimeTracking ADD CONSTRAINT RefProcedureInstance4 
    FOREIGN KEY (_ProcedureInstance_key)
    REFERENCES ProcedureInstance(_ProcedureInstance_key)
;

-- ==================================================================
-- cv_ContainterType: Make _VolumeUnit_key NULLABLE
-- ==================================================================
ALTER TABLE cv_ContainerType 
	MODIFY COLUMN _VolumeUnit_key INT(11) DEFAULT NULL
;
-- ==================================================================
-- cv_AlleleClass: Change AlleleClass length to 75
-- ==================================================================
ALTER TABLE cv_AlleleClass 
	MODIFY COLUMN AlleleClass VARCHAR(75)
;
-- ==================================================================
-- Container: Make _ParentContainer_key NULLABLE
-- ==================================================================
ALTER TABLE Container 
	MODIFY COLUMN _ParentContainer_key INT(11) DEFAULT NULL
;
-- ==================================================================
-- ContainerHistory: Add Foreign Key to Container
-- ==================================================================
ALTER TABLE ContainerHistory 
	ADD COLUMN _Container_key INT(11) DEFAULT NULL AFTER _Location_key
;
ALTER TABLE ContainerHistory 
	ADD CONSTRAINT RefContainer31 
    	FOREIGN KEY (_Container_key)
    	REFERENCES Container(_Container_key)
;
-- ==================================================================
-- AccessionIdentifiers: Rename to AccessionIdentifier
-- ==================================================================
ALTER TABLE AccessionIdentifiers RENAME TO AccessionIdentifier
;
-- ==================================================================
-- NameComponentDetails: Rename to NameComponentDetail
-- ==================================================================
ALTER TABLE NameComponentDetails RENAME TO NameComponentDetail
;
-- ==================================================================
-- OrganismOrderDetails: Rename to OrganismOrderDetail
-- ==================================================================
ALTER TABLE OrganismOrderDetails RENAME TO OrganismOrderDetail
;
-- ==================================================================
-- Sample: Add Line and NameFamily Foreign Keys
-- ==================================================================
ALTER TABLE Sample 
	MODIFY COLUMN _NameFamily_key INT(11)
;
ALTER TABLE Sample 
	ADD CONSTRAINT RefSampleNameFamily 
    	FOREIGN KEY (_NameFamily_key)
    	REFERENCES NameFamily(_NameFamily_key)
;
ALTER TABLE Sample 
	ADD CONSTRAINT RefSampleLine 
    	FOREIGN KEY (_Line_key)
    	REFERENCES Line(_Line_key)
;
-- ==================================================================
-- AccessionIdentifer: Make _Marker_key NULLABLE
-- ==================================================================
ALTER TABLE AccessionIdentifier 
	MODIFY COLUMN _Marker_key INT(11) DEFAULT NULL
;

-- ==================================================================
-- Date column name consistency updates
-- ==================================================================
ALTER TABLE Inquiry 
	CHANGE COLUMN InquiryDate DateInquiry DATETIME NOT NULL;
ALTER TABLE OrganismName 
	CHANGE COLUMN AssignedDate DateAssigned DATE DEFAULT NULL;
ALTER TABLE OrganismShipment 
	CHANGE COLUMN ArrivalDate DateArrival DATETIME DEFAULT NULL;
ALTER TABLE PlugDate 
	CHANGE COLUMN PlugDate DatePlug DATETIME NOT NULL;
ALTER TABLE Cross_
	CHANGE COLUMN ProposedWeanTime ProposedDaysToWean INT(11) DEFAULT NULL
;
-- ==================================================================
-- Add FK to Taxon to Marker table
-- ==================================================================
ALTER TABLE Marker 
	ADD COLUMN _Taxon_key INT(11) DEFAULT NULL AFTER _MarkerType_key
;
ALTER TABLE Marker 
	ADD CONSTRAINT RefMarkerTaxon 
    	FOREIGN KEY (_Taxon_key)
    	REFERENCES cv_Taxon(_Taxon_key)
;
-- ==================================================================
-- Add missing standard columns to cv_GenotypeSymbol
-- ==================================================================
ALTER TABLE cv_GenotypeSymbol
	ADD COLUMN IsActive                    TINYINT,
	ADD COLUMN SortOrder                   INT,
	ADD COLUMN IsDefault                   TINYINT
;

-- ==================================================================
-- Add cascade delete of procedure definition to
-- ==================================================================
ALTER TABLE SystemActionInput
 DROP FOREIGN KEY RefInput348
;

ALTER TABLE SystemActionInput ADD CONSTRAINT RefInput348 FOREIGN KEY RefInput348 (_Input_key)
    REFERENCES Input (_Input_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
;

ALTER TABLE SystemActionOutput
 DROP FOREIGN KEY RefOutput346
;

ALTER TABLE SystemActionOutput ADD CONSTRAINT RefOutput346 FOREIGN KEY RefOutput346 (_Output_key)
    REFERENCES Output (_Output_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
;
-- ==================================================================
-- Add time tracking flag to ProcedureDefinitionVersion
-- ==================================================================
ALTER TABLE ProcedureDefinitionVersion
	ADD COLUMN TrackTime TINYINT DEFAULT NULL AFTER IsActive
;

-- ==================================================================
-- Update OrganismID to varchar
-- ==================================================================
ALTER TABLE Organism
	MODIFY COLUMN OrganismID varchar(75) NOT NULL
;

-- ==================================================================
-- Cascade delete input defaults
-- ==================================================================
ALTER TABLE InputDefault
 DROP FOREIGN KEY RefInput142
;

ALTER TABLE InputDefault ADD CONSTRAINT RefInput142 FOREIGN KEY RefInput142 (_Input_key)
    REFERENCES Input (_Input_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
;


-- ==================================================================
-- Auto incrememnt Genotype Symbol PK
-- ==================================================================
ALTER TABLE
	cv_GenotypeSymbol
	MODIFY COLUMN _GenotypeSymbol_key INT(11) NOT NULL AUTO_INCREMENT;
-- ==================================================================
-- Add missing vocabulary source FK's
-- ==================================================================
ALTER TABLE cv_GenotypeSymbol
    	ADD CONSTRAINT Refcv_VocabularySourceCVGS
	FOREIGN KEY (_VocabularySource_key)
	REFERENCES cv_VocabularySource (_VocabularySource_key)
;
ALTER TABLE cv_NonTaskReason
    	ADD CONSTRAINT Refcv_VocabularySourceCVNTR
	FOREIGN KEY (_VocabularySource_key)
	REFERENCES cv_VocabularySource (_VocabularySource_key)
;
ALTER TABLE cv_OrganismOrderStatus
    	ADD CONSTRAINT Refcv_VocabularySourceCVOOS
	FOREIGN KEY (_VocabularySource_key)
	REFERENCES cv_VocabularySource (_VocabularySource_key)
;
ALTER TABLE cv_ProcedureOutcome
    	ADD CONSTRAINT Refcv_VocabularySourceCVPO
	FOREIGN KEY (_VocabularySource_key)
	REFERENCES cv_VocabularySource (_VocabularySource_key)
;
ALTER TABLE cv_StandardComment
    	ADD CONSTRAINT Refcv_VocabularySourceCVSC
	FOREIGN KEY (_VocabularySource_key)
	REFERENCES cv_VocabularySource (_VocabularySource_key)
;

-- ==================================================================
-- Drop IsStandardized fields from input and output
-- ==================================================================
ALTER TABLE Input DROP COLUMN IsStandardized;
ALTER TABLE Output DROP COLUMN IsStandardized;
-- ==================================================================
-- Add OrganismOrder FK to Organism
-- ==================================================================
ALTER TABLE Organism 
ADD COLUMN _OrganismOrder_key INT(11) DEFAULT NULL AFTER _Taxon_key
;
ALTER TABLE Organism 
ADD CONSTRAINT FK_Organism_Order FOREIGN KEY FK_Organism_Order (_OrganismOrder_key)
    REFERENCES OrganismOrder (_OrganismOrder_key)
;
-- ==================================================================
-- Convert units to cv
-- ==================================================================
ALTER TABLE InputOutputUnit 
RENAME TO cv_InputOutputUnit
;
ALTER TABLE cv_InputOutputUnit
 ADD COLUMN IsDefault TINYINT UNSIGNED AFTER Version,
 ADD COLUMN _VocabularySource_key INT(11) AFTER IsDefault,
 ADD COLUMN ElementID VARCHAR(18) NOT NULL AFTER _VocabularySource_key,
 ADD COLUMN IsDeprecated TINYINT UNSIGNED AFTER ElementID
;
UPDATE cv_InputOutputUnit SET _VocabularySource_key=2
;
ALTER TABLE cv_InputOutputUnit ADD CONSTRAINT RefInputOutputUnitSource
    FOREIGN KEY (_VocabularySource_key)
    REFERENCES cv_VocabularySource(_VocabularySource_key)
;
-- ==================================================================
-- Input/Output storage clean up
-- ==================================================================
ALTER TABLE Input 
DROP COLUMN NumericValidationMin,
DROP COLUMN NumericValidationMax,
DROP COLUMN DateValidationMin,
DROP COLUMN DateValidationMax,
DROP COLUMN TimeValidationMin,
DROP COLUMN TimeValidationMax
;
ALTER TABLE InputDefault 
DROP COLUMN NumericValue,
DROP COLUMN TextValue,
DROP COLUMN DateValue,
DROP COLUMN TimeValue,
DROP COLUMN BooleanValue
;
ALTER TABLE InputInstance 
DROP COLUMN NumericValue,
DROP COLUMN TextValue,
DROP COLUMN DateTimeValue,
DROP COLUMN TimeValue,
DROP COLUMN BooleanValue
;
ALTER TABLE Output 
DROP COLUMN NumericValidationMin,
DROP COLUMN NumericValidationMax,
DROP COLUMN DateValidationMin,
DROP COLUMN DateValidationMax,
DROP COLUMN TimeValidationMin,
DROP COLUMN TimeValidationMax
;
ALTER TABLE OutputDefault 
DROP COLUMN NumericValue,
DROP COLUMN TextValue,
DROP COLUMN DateValue,
DROP COLUMN TimeValue,
DROP COLUMN BooleanValue
;
ALTER TABLE OutputInstance 
DROP COLUMN NumericValue,
DROP COLUMN TextValue,
DROP COLUMN DateTimeValue,
DROP COLUMN TimeValue,
DROP COLUMN BooleanValue
;
-- ==================================================================
-- Remove account number from service order
-- ==================================================================
ALTER TABLE ServiceOrder 
DROP COLUMN AccountNumber
;
ALTER TABLE DataFile
ADD COLUMN PathToFiles VARCHAR(500) AFTER Title,
ADD COLUMN PathToLoadedFiles VARCHAR(500) AFTER PathToFiles,
ADD COLUMN PathToFailedFiles VARCHAR(500) AFTER PathToLoadedFiles
;
DROP TABLE DataFileProcedureDefinition
;
ALTER TABLE DataFile
ADD COLUMN _ProcedureDefinition_key INTEGER(11) NULL AFTER _FileExtension_key,
ADD CONSTRAINT FK_DataFile_3 FOREIGN KEY FK_DataFile_3 (_ProcedureDefinition_key)
REFERENCES ProcedureDefinition (_ProcedureDefinition_key)
;
ALTER TABLE ProcedureDefinitionVersion
ADD COLUMN IsDiscretePerOrganism TINYINT NOT NULL DEFAULT 0 AFTER VersionNumber,
ADD COLUMN IsDiscretePerSample TINYINT NOT NULL DEFAULT 0 AFTER IsDiscretePerOrganism,
ADD COLUMN IsOrganismDependent TINYINT NOT NULL DEFAULT 0 AFTER IsDiscretePerSample,
ADD COLUMN IsSampleDependent TINYINT NOT NULL DEFAULT 0 AFTER IsOrganismDependent
;
ALTER TABLE Organism ADD COLUMN OrderID VARCHAR(45) AFTER Comments
;

-- ==================================================================
-- Add cv_ExternalConnectionType table
-- ==================================================================
CREATE TABLE cv_ExternalConnectionType(
	_ExternalConnectionType_key		INT				AUTO_INCREMENT,
	ExternalConnectionType			VARCHAR(75)		NOT NULL,
    CreatedBy                 		VARCHAR(18)	    NOT NULL,
    DateCreated               		DATETIME		NOT NULL,
    ModifiedBy                		VARCHAR(18)	    NOT NULL,
    DateModified              		DATETIME       	NOT NULL,
    Version                   		INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionType_key)
)ENGINE=INNODB
;
-- ==================================================================
-- Add ExternalConnection table
-- ==================================================================
CREATE TABLE ExternalConnection(
	_ExternalConnection_key			INT				AUTO_INCREMENT,
	_ExternalConnectionType_key		INT				NOT NULL,
	ExternalConnectionID			VARCHAR(75)		NOT NULL,
	ConnectionData					VARCHAR(1024)	NOT NULL,
	Description						VARCHAR(1024)	NOT NULL,
	Username						VARCHAR(75),
	Password						VARCHAR(75),
	AuditLevel						INT				DEFAULT 1 NOT NULL,
    CreatedBy                 		VARCHAR(18)	    NOT NULL,
    DateCreated               		DATETIME		NOT NULL,
    ModifiedBy                		VARCHAR(18)	    NOT NULL,
    DateModified              		DATETIME       	NOT NULL,
    Version                   		INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnection_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnection ADD CONSTRAINT RefExternalConnectionType
	FOREIGN KEY (_ExternalConnectionType_key)
	REFERENCES cv_ExternalConnectionType(_ExternalConnectionType_key)
;
ALTER TABLE ExternalConnection ADD CONSTRAINT UniqueExternalConnectionId
	UNIQUE (ExternalConnectionID)
;
-- ==================================================================
-- Add ExternalConnectionRest table
-- ==================================================================
CREATE TABLE ExternalConnectionRest(
	_ExternalConnectionRest_key		INT				AUTO_INCREMENT,
	_ExternalConnection_key			INT				NOT NULL,
	RestMethod						VARCHAR(75)		NOT NULL,
	RelativeUri						VARCHAR(256)	NOT NULL,
	RestfulBaseSerializerImpl		VARCHAR(1024)	NOT NULL,
    CreatedBy                 		VARCHAR(18)	    NOT NULL,
    DateCreated               		DATETIME		NOT NULL,
    ModifiedBy                		VARCHAR(18)	    NOT NULL,
    DateModified              		DATETIME       	NOT NULL,
    Version                   		INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionRest_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnectionRest ADD CONSTRAINT RefExternalConnection
	FOREIGN KEY (_ExternalConnection_key)
	REFERENCES ExternalConnection(_ExternalConnection_key)
;
-- ==================================================================
-- Add ExternalConnectionAudit table
-- ==================================================================
CREATE TABLE ExternalConnectionAudit(
	_ExternalConnectionAudit_key	INT				AUTO_INCREMENT,
	_ExternalConnection_key			INT				NOT NULL,
	Method							VARCHAR(256),
	Command							LONGTEXT,
	DataSent						LONGTEXT,
	ReturnCode						VARCHAR(256),
	DataReceived					LONGTEXT,
    CreatedBy                 		VARCHAR(18)	    NOT NULL,
    DateCreated               		DATETIME		NOT NULL,
    ModifiedBy                		VARCHAR(18)	    NOT NULL,
    DateModified              		DATETIME       	NOT NULL,
    Version                   		INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionAudit_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnectionAudit ADD CONSTRAINT RefExternalConnection2
	FOREIGN KEY (_ExternalConnection_key)
	REFERENCES ExternalConnection(_ExternalConnection_key)
;
-- ==================================================================
-- Add ExternalConnectionObjectLink table
-- ==================================================================
CREATE TABLE ExternalConnectionObjectLink(
	_ExternalConnectionObjectLink_key	INT				AUTO_INCREMENT,
	_ExternalConnection_key				INT				NOT NULL,
	ObjectType							VARCHAR(256)	NOT NULL,
	InternalKey							INT				NOT NULL,
	ExternalKey							VARCHAR(256)	NOT NULL,
    CreatedBy                 			VARCHAR(18)	    NOT NULL,
    DateCreated               			DATETIME		NOT NULL,
    ModifiedBy                			VARCHAR(18)	    NOT NULL,
    DateModified              			DATETIME       	NOT NULL,
    Version                   			INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionObjectLink_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnectionObjectLink ADD CONSTRAINT RefExternalConnection3
	FOREIGN KEY (_ExternalConnection_key)
	REFERENCES ExternalConnection(_ExternalConnection_key)
;

-- ==================================================================
-- Update subcontainterlayout table name
-- ==================================================================
ALTER TABLE SubcontainerLayout
RENAME TO ContainerLayout
;
ALTER TABLE ContainerLayout
MODIFY COLUMN _SubcontainerType_key int(11) DEFAULT NULL
;
-- ==================================================================
-- Add sortorder to outputinstance
-- ==================================================================
ALTER TABLE OutputInstance
ADD COLUMN SortOrder int(11) DEFAULT '0' AFTER _Output_key
;
-- ==================================================================
-- Add FunctionID to ExternalConnectionRest
-- ==================================================================
ALTER TABLE ExternalConnectionRest 
ADD COLUMN FunctionID VARCHAR(75) NOT NULL AFTER _ExternalConnection_key;


-- ==================================================================
-- Add cv_SearchOperator table
-- ==================================================================
CREATE TABLE cv_SearchOperator(
	_SearchOperator_key					INT				AUTO_INCREMENT,
	SearchOperator						VARCHAR(25)		NOT NULL,
	Description							VARCHAR(256)	NOT NULL,
    CreatedBy                 			VARCHAR(18)	    NOT NULL,
    DateCreated               			DATETIME		NOT NULL,
    ModifiedBy                			VARCHAR(18)	    NOT NULL,
    DateModified              			DATETIME       	NOT NULL,
    Version                   			INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_SearchOperator_key)
)ENGINE=INNODB
;
-- ==================================================================
-- Add ExternalConnectionSearchParam table
-- ==================================================================
CREATE TABLE ExternalConnectionSearchParam(
	_ExternalConnectionSearchParam_key	INT				AUTO_INCREMENT,
	_ExternalConnection_key				INT				NOT NULL,
	_DataType_key						INT				NOT NULL,
	Param								VARCHAR(75)		NOT NULL,
	Display								VARCHAR(75)		NOT NULL,
	SortOrder							INT				NOT NULL,
    CreatedBy                 			VARCHAR(18)	    NOT NULL,
    DateCreated               			DATETIME		NOT NULL,
    ModifiedBy                			VARCHAR(18)	    NOT NULL,
    DateModified              			DATETIME       	NOT NULL,
    Version                   			INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionSearchParam_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnectionSearchParam ADD CONSTRAINT RefExternalConnectionSearch
	FOREIGN KEY (_ExternalConnection_key)
	REFERENCES ExternalConnection(_ExternalConnection_key)
;
ALTER TABLE ExternalConnectionSearchParam ADD CONSTRAINT RefECSPDataType
	FOREIGN KEY (_DataType_key)
	REFERENCES cv_DataType(_DataType_key)
;
-- ==================================================================
-- Add ExternalConnectionSearchOperator table
-- ==================================================================
CREATE TABLE ExternalConnectionSearchOperator(
	_ExternalConnectionSearchOperator_key	INT				AUTO_INCREMENT,
	_ExternalConnectionSearchParam_key		INT				NOT NULL,
	_SearchOperator_key						INT				NOT NULL,
	OperatorValue							VARCHAR(75) 	NOT NULL,
	MultiValueDelimiter						VARCHAR(5),
	SortOrder								INT				NOT NULL,
    CreatedBy                 				VARCHAR(18)	    NOT NULL,
    DateCreated               				DATETIME		NOT NULL,
    ModifiedBy                				VARCHAR(18)	    NOT NULL,
    DateModified              				DATETIME       	NOT NULL,
    Version                   				INT            	DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ExternalConnectionSearchOperator_key)
)ENGINE=INNODB
;
ALTER TABLE ExternalConnectionSearchOperator ADD CONSTRAINT RefExternalConnectionSearchParam
	FOREIGN KEY (_ExternalConnectionSearchParam_key)
	REFERENCES ExternalConnectionSearchParam(_ExternalConnectionSearchParam_key)
;
ALTER TABLE ExternalConnectionSearchOperator ADD CONSTRAINT RefSearchOperator
	FOREIGN KEY (_SearchOperator_key)
	REFERENCES cv_SearchOperator(_SearchOperator_key)
;
-- ==================================================================
-- Add configuration tables
-- ==================================================================
CREATE TABLE Configuration(
    _Configuration_key	  INT		  NOT NULL	AUTO_INCREMENT,
    Configuration         VARCHAR(100)	  NOT NULL,
    Description           VARCHAR(500),
    DefaultSetting        VARCHAR(500)    NOT NULL,
    CreatedBy             VARCHAR(18)     NOT NULL,
    DateCreated           DATETIME        NOT NULL,
    ModifiedBy            VARCHAR(18)     NOT NULL,
    DateModified          DATETIME        NOT NULL,
    Version               INT             DEFAULT 1 NOT NULL,
    PRIMARY KEY (_Configuration_key)
)ENGINE=INNODB
;
CREATE TABLE ConfigurationSetting(
    _ConfigurationSetting_key	  INT		  NOT NULL	AUTO_INCREMENT,
    _Workgroup_key                INT             NOT NULL,
    _Configuration_key            INT        	  NOT NULL,
    Value                         VARCHAR(500)    NOT NULL,
    CreatedBy                     VARCHAR(18)     NOT NULL,
    DateCreated                   DATETIME        NOT NULL,
    ModifiedBy                    VARCHAR(18)     NOT NULL,
    DateModified                  DATETIME        NOT NULL,
    Version                       INT             DEFAULT 1 NOT NULL,
    PRIMARY KEY (_ConfigurationSetting_key)
)ENGINE=INNODB
;
ALTER TABLE ConfigurationSetting ADD CONSTRAINT RefConfiguration3 
    FOREIGN KEY (_Configuration_key)
    REFERENCES Configuration(_Configuration_key)
;
ALTER TABLE ConfigurationSetting ADD CONSTRAINT RefWorkgroup4 
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;
-- ******************************************************************************
--    UPGRADE THE DATABASE VERSION AT THE END OF EVERY DATABASE CHANGE SCRIPT.
-- ******************************************************************************
UPDATE dbInfo SET databaseReleaseNum = 81, majorVersion = 4, minorVersion = 3, bugFixVersion = 0,releaseNotes = 'Hertz Sprint', releaseType = 'Release', releaseDate = now()
;




