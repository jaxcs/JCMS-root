-- MySQL dump 10.13  Distrib 5.5.29, for Win32 (x86)
--
-- Host: localhost    Database: jcms_db
-- ------------------------------------------------------
-- Server version	5.5.29

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

--
-- Table structure for table `Allele`
--

DROP TABLE IF EXISTS `Allele`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Allele` (
  `_gene_key` int(11) DEFAULT NULL,
  `allele` varchar(8) NOT NULL,
  `genericAlleleGeneClass` varchar(16) DEFAULT NULL,
  `_allele_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_allele_key`),
  KEY `_gene_key` (`_gene_key`),
  KEY `allele` (`allele`),
  CONSTRAINT `FK_Gene_Allele_1` FOREIGN KEY (`_gene_key`) REFERENCES `Gene` (`_gene_key`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Allele`
--

LOCK TABLES `Allele` WRITE;
/*!40000 ALTER TABLE `Allele` DISABLE KEYS */;
INSERT INTO `Allele` VALUES (NULL,'-','E',1,1),(NULL,'+','E',2,1),(NULL,'-','KI',3,1),(NULL,'+','KI',4,1),(NULL,'-','KO',5,1),(NULL,'+','KO',6,1),(NULL,'-','TG',7,1),(NULL,'+','TG',8,1),(NULL,'0','TG',9,1);
/*!40000 ALTER TABLE `Allele` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApprovedStrainRegistry`
--

DROP TABLE IF EXISTS `ApprovedStrainRegistry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApprovedStrainRegistry` (
  `_approvedStrain_key` int(11) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT '-1',
  `_damStrain_key` int(11) NOT NULL DEFAULT '0',
  `_sireStrain_key` int(11) NOT NULL DEFAULT '0',
  `_litterStrain_key` int(11) NOT NULL DEFAULT '0',
  `_owner_key` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_approvedStrain_key`),
  KEY `_approvedStrain_key` (`_approvedStrain_key`),
  KEY `_damStrain_key` (`_damStrain_key`),
  KEY `_litterStrain_key` (`_litterStrain_key`),
  KEY `_owner_key` (`_owner_key`),
  KEY `_sireStrain_key` (`_sireStrain_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApprovedStrainRegistry`
--

LOCK TABLES `ApprovedStrainRegistry` WRITE;
/*!40000 ALTER TABLE `ApprovedStrainRegistry` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApprovedStrainRegistry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Container`
--

DROP TABLE IF EXISTS `Container`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Container` (
  `_container_key` int(11) NOT NULL AUTO_INCREMENT,
  `containerID` int(11) NOT NULL,
  `containerName` varchar(16) DEFAULT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `_containerHistory_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_container_key`),
  UNIQUE KEY `_container_key` (`_container_key`),
  UNIQUE KEY `_currentContainerHistory_key` (`_containerHistory_key`),
  UNIQUE KEY `containerID` (`containerID`),
  KEY `containerName` (`containerName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Container`
--

LOCK TABLES `Container` WRITE;
/*!40000 ALTER TABLE `Container` DISABLE KEYS */;
/*!40000 ALTER TABLE `Container` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ContainerHistory`
--

DROP TABLE IF EXISTS `ContainerHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContainerHistory` (
  `_containerHistory_key` int(11) NOT NULL AUTO_INCREMENT,
  `_room_key` int(11) NOT NULL,
  `_container_key` int(11) NOT NULL,
  `actionDate` datetime NOT NULL,
  `_containerStatus_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_containerHistory_key`),
  UNIQUE KEY `_containerHistory_key` (`_containerHistory_key`),
  KEY `_container_key` (`_container_key`),
  KEY `_containerStatus_key` (`_containerStatus_key`),
  KEY `_room_key` (`_room_key`),
  KEY `actionDate` (`actionDate`),
  KEY `ContainerContainerHistory` (`_container_key`),
  KEY `cv_ContainerStatusContainerHistory` (`_containerStatus_key`),
  KEY `RoomContainerHistory` (`_room_key`),
  CONSTRAINT `containerhistory_ibfk_1` FOREIGN KEY (`_container_key`) REFERENCES `Container` (`_container_key`),
  CONSTRAINT `containerhistory_ibfk_2` FOREIGN KEY (`_containerStatus_key`) REFERENCES `cv_ContainerStatus` (`_containerStatus_key`),
  CONSTRAINT `containerhistory_ibfk_3` FOREIGN KEY (`_room_key`) REFERENCES `Room` (`_room_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  `_controlledVocabulary_key` int(11) NOT NULL AUTO_INCREMENT,
  `_controlledVocabularyGroup_key` int(11) NOT NULL,
  `tableName` varchar(50) DEFAULT NULL,
  `pkColumnName` varchar(100) DEFAULT NULL,
  `columnOneName` varchar(100) DEFAULT NULL,
  `columnTwoName` varchar(100) DEFAULT NULL,
  `displayName` varchar(100) DEFAULT NULL,
  `displayColumnOneName` varchar(100) DEFAULT NULL,
  `displayColumnTwoName` varchar(100) DEFAULT NULL,
  `subViewName` varchar(100) DEFAULT NULL,
  `isUserAdministered` tinyint(4) NOT NULL DEFAULT '0',
  `sortOrder` int(11) NOT NULL,
  `createdBy` varchar(18) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `modifiedBy` varchar(18) NOT NULL,
  `dateModified` datetime NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_controlledVocabulary_key`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ControlledVocabulary`
--

LOCK TABLES `ControlledVocabulary` WRITE;
/*!40000 ALTER TABLE `ControlledVocabulary` DISABLE KEYS */;
INSERT INTO `ControlledVocabulary` VALUES (1,1,'Center','','','','Centers','','','CENTER',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(2,2,'Allele','','','','Allele','','','ALLELE',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(3,2,'Gene','','','','Gene','','','GENE',1,2,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(4,2,'cv_GeneClass','_geneClass_key','GeneClass','Description','Gene Class Terms','Gene Class','Description','GENERAL',1,3,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(5,2,'cv_GenotypeSpecimenLocation','_genotypeSpecimenLocation_key','genotypeSpecimenLocation','','Genotype Specimen Location Terms','Genotype Specimen Location','','GENERAL',1,4,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(6,3,'ApprovedStrainRegistry','','','','Approved Mating Strains','','','APPROVEDMATINGSTRAINS',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(7,5,'cv_CauseOfDeath','_causeOfDeath_key','cod','description','Cause of Death Terms','Cause of Death','Description','GENERAL',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(8,5,'cv_CoatColor','_coatColor_key','coatColor','description','Coat Color Terms','Coat Color','Description','GENERAL',1,2,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(9,5,'cv_Diet','_diet_key','diet','dietDescription','Diet Terms','Diet','Description','GENERAL',1,3,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(10,5,'cv_Generation','_generation_key','generation','','Generation Terms','Generation','','GENERAL',1,4,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(11,5,'LifeStatus','','','','Life Status','','','LIFESTATUS',1,5,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(12,5,'cv_MouseOrigin','_mouseOrigin_key','mouseOrigin','','Mouse Origin Terms','Mouse Origin','','GENERAL',1,6,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(13,5,'cv_MouseProtocol','_mouseProtocol_key','id','description','Mouse Protocol Terms','Mouse Protocol','Description','GENERAL',1,7,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(14,5,'cv_MouseUse','','','','Mouse Use','','','MOUSEUSE',1,8,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(15,5,'Strain','','','','Strain','','','STRAIN',1,9,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(16,5,'cv_StrainStatus','_strainStatus_key','strainStatus','description','Strain Status Terms','Strain Status','Description','GENERAL',1,10,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(17,5,'cv_StrainType','_strainType_key','strainType','','Strain Type Terms','Strain Type','','GENERAL',1,11,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(18,6,'cv_HealthLevel','_healthLevel_key','healthLevel','description','Health Level Terms','Health Level','Description','GENERAL',1,2,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(19,6,'Room','','','','Manage Cage Configuration','','','MANAGEPENCONFIGURATION',1,2,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(20,6,'cv_MatingCardNotes','_matingCardNotes_key','matingNotes','','Mating Card Note Terms Terms','Mating Card Notes','','GENERAL',1,3,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(21,6,'cv_ContainerStatus','','','','Cage Status Terms','','','PENSTATUS',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(22,6,'Container','','','','Manage Cages','','','MANAGEPENS',1,3,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(23,6,'Container','','','','Retire Cages','','','RETIREPENS',1,6,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(24,6,'Room','','','','Manage Rooms','','','MANAGEROOM',1,4,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1),(25,4,'','','','','JCMS Setup Variables','','','JCMSSETUPVARIABLES',1,1,'dba','2013-06-10 13:43:16','dba','2013-06-10 13:43:16',1);
/*!40000 ALTER TABLE `ControlledVocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DbFormPrivileges`
--

DROP TABLE IF EXISTS `DbFormPrivileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DbFormPrivileges` (
  `formName` varchar(32) NOT NULL,
  `privilegeLevel` varchar(8) NOT NULL,
  `completeFormName` varchar(64) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `_dbFormPrivileges_key` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`_dbFormPrivileges_key`),
  UNIQUE KEY `completeFormName` (`completeFormName`),
  UNIQUE KEY `formName` (`formName`),
  KEY `privilegeLevel` (`privilegeLevel`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DbFormPrivileges`
--

LOCK TABLES `DbFormPrivileges` WRITE;
/*!40000 ALTER TABLE `DbFormPrivileges` DISABLE KEYS */;
INSERT INTO `DbFormPrivileges` VALUES ('Edit & Manage Experimental Plan','Owner','EditExpPlan','Experiment: Make changes to an experimental plan or manage the mice and tests that are part of the plan.',1,1),('Add Experimental Test','Owner','AddExpTest','Experiment: Add a new experimental test.',1,2),('Edit Experimental Test','Owner','EditExpTest','Experiment: Make changes to an experimental test.',1,3),('Add Test Type - Data Description','Owner','AddExpDataDescriptor','Experiment: Add a new test type and describe the data to be collected.',1,4),('Edit Test Type','Owner','EditExpDataDescriptor','Experiment: Make changes to a test type.',1,5),('Add Test Type Defaults','Owner','AddExpDataDefaults','Experiment: Add a new set of default values for the data corresponding to a particular test type.',1,6),('Edit Test Type Defaults','Admin','EditExpDataDefaults','Experiment: Make changes to the default data values for a particular test type.',1,7),('Add Experimental Data','Sec','AddExpData','Experiment: Add experimental data for a mouse.',1,8),('Edit Experimental Data','Sec','EditExpData','Experiment: Make changes to the experimental data associated with a mouse.',1,9),('Query Experiment','Sec','QueryExperimentalData','Query: Show information about the data collected for a mouse, experiment, or test.',1,10),('Bulk Add Mice from Query','Owner','BulkAddMiceFromQuery','Experiment: Add a group of mice into an experimental plan or test - mice are selected with the mouse query form.',1,11),('Bulk Add Experimental Data','Sec','AddExpDataToSpecificTest','Experiment: Add data values that are the same to a group of mice all at once.',1,12),('Owner Experiment Work Report','Sec','RequestExperimentWorkReport','Reports: Experiments - Show all the mice due for processing based on age and proposed test date.',1,13),('Handheld Forms','Sec','HandHeldMain','Handheld: Main menu of choices.',1,14),('Wean and/or Exit (handheld)','Sec','HandheldWeanAndExit','Handheld: Wean pups, Wean and Exit pups, or Bulk change life status.',1,15),('Add Pair Mating (handheld)','Sec','HandheldMating','Handheld: Add a pair or trio mating by moving the mice into a new pen.',1,16),('Add Litter w/Mice (handheld)','Sec','HandheldAddLitter','Handheld: Add a litter and add mouse records for the pups at the same time.',1,17),('Print Cage Card (handheld)','Sec','HandheldPrintCageCards','Handheld: Print a new cage card for a selected pen.',1,18),('Move Mice (handheld)','Owner','HandheldMoveMice','Handheld: Move mice from a selected pen into a new pen.',1,19),('Edit Life Status (handheld)','Owner','HandheldChangeMice','Handheld: Change the life status of mice in one or more pens.',1,20),('Add Mouse','Sec','AddMouse','Add Mice: Add new mice, one at a time.',1,21),('Edit Mouse','Sec','EditMouse','Edit Mice: Make changes to the information about a mouse.',1,22),('Add Mice at Wean','Owner','AddMouseAtWeaning','Add Mice: Add a new mouse from a specific JCMS litter.',1,23),('Move Mice','Owner','MoveMouse','Pens: Use this form to move mice from one pen into another.',1,24),('Import Mice','Owner','ImportMice','Add Mice: Add a group of mice at once. All must be similar.',1,25),('Add Litter w/Pups','Sec','AddLitterWithPups','Add Litter: Add a litter and optionally create mouse records for each pup at the same time.',1,26),('Add Litter','Sec','AddLitter','Add Litter: Add a litter.',1,27),('Edit Litter','Sec','EditLitter','Edit Litter: Make changes to the information about a litter of mice.',1,28),('Design Matings','Owner','DesignMatings','Add Mating: Create a mating \"on paper\" before doing it for real.',1,29),('Activate Matings','Owner','ActivateMatings','Add Mating: Finalize the creation of a mating that was designed previously by entering the mating date.',1,30),('Add Matings','Owner','AddMatings','Add Mating: Add a mating record after it has been done in the mouse room.',1,31),('Design Retire Matings','Owner','TSDesignRetireMating','Other Mating: Decide ahead of time what matings to retire.',1,32),('Retire Matings','Owner','RetireMatings','Other Mating: Finalize the end of a mating by retiring it.',1,33),('Edit Matings','Owner','EditMatings','Edit Mating: Make changes to the information about a mating.',1,34),('Add Genotype','Owner','AddGenotype','Add Genotype: Add new genotype information for a particular mouse.',1,35),('Edit Genotype','Owner','EditGenotype','Edit Genotype: Make changes to the genotype information for a mouse.',1,36),('Add Use','Owner','AddMouseUse','Add Mouse Use: Add a new use for a particular mouse. Uses can store small amounts of associated data.',1,37),('Edit Use','Owner','EditMouseUse','Edit Mouse Use: Make changes to the information about a mouse\'s use and add or update associated data.',1,38),('Wean Work Report','Sec','RequestWeanReport','Reports: Wean - List litters that have not been weaned.',1,39),('Mouse-Use Work Report','Sec','RequestMouseUseReport','Reports: Mouse Uses - List mice with uses that are not marked done.',1,40),('Edit Ear Tags','Admin','LastTagEdit','Misc: The strain record can contain a range of ear tags for use with only that strain.',1,41),('Add Pen','Owner','AddPen','Pens: Add a new empty mouse cage.',1,42),('Pen Info','Sec','PenInfo','Pens: Show what pen a mouse is in, list the other mice in the pen, and print a cage card.',1,43),('Edit Pen','Owner','EditPenGroup','Pens: Edit the basic information about a mouse cage.',1,44),('Print Cage Cards','Sec','PrintCageCards','Pens: Print blank cage cards.',1,45),('Bulk Edit Life Status or Diet','Owner','BulkChangeLifeStatusOrDietMouse','Edit Mice: Make a change to either the life status or diet of a group of mice.',1,46),('Bulk Add or Edit Use','Owner','BulkAddOrEditMouseUse','Add Mouse Use: Add or edit mouse uses for a group of mice.',1,47),('Bulk Add Genotype','Owner','BulkAddGenoType','Add Genotype: Add new genotype information for a group of mice.',1,48),('Data Statistics','Sec','DB Statistics','Misc: Show the current number of mice, matings, and pens in JCMS',1,49),('Query Matings','Sec','QueryMatings','Query: Show information about matings based on user criteria',1,50),('Query Mouse','Sec','QueryMouse','Query: Show information about mice based on user criteria.',1,51),('Add Experimental Plan','Owner','AddExpPlan','Experiment: Add a new experimental plan.',1,52),('Test Type Experiment Work Report','Sec','TestTypeBasedWorkReport','Reports: Experiments - Show the mice due for processing for selected test types.',1,53),('Change Mouse ID','Owner','ChangeMouseIDForm','Misc: Create a new ID number for an existing mouse',1,54),('Add Sample','Sec','AddSample','Add Sample: Add new samples.',1,55),('Edit Sample','Sec','EditSample','Edit Sample: Modify exsiting samples.',1,56),('Bulk Edit Sample','Sec','BulkUpdateSample','Bulk Edit Sample: Add or edit mouse Owner/Status/Location a group of samples.',1,57),('Query Samples','Sec','QuerySample','Query Samples:  Show information about samples based on user criteria.',1,58),('Import Experimental Data','Owner','ImportExpData','Experiment: Import external data',1,59),('Add Plug Date','Sec','AddPlugDate','Add Plug Date: Associate a plug date with a specific mating and dam',1,60),('Edit Plug Date','Sec','EditPlugDate','Edit Plug Date: Edit plug dates associated with a mating',1,61),('Plug Date and Pregnancy Reports','Sec','PlugDateReports','Reports: Plug check, Preganancy check, Stage check, Litter check work reports',1,62),('Browse Sample By Location','Sec','BrowseSampleByLocation','Query Samples: Look at the locations of samples using a browse type interface.',1,63),('Print Sample Labels','Sec','PrintSampleLabels','Reports: Select samples for printing sample label report.',1,64),('Cage Use Report','Owner','CageUseReport','Allows the user to see how many Pens were active for a specified period.',1,65),('Cage Use Report Summary','Owner','CageUseReportSummary','Summary of Billable Pens.',1,66),('Genotype Work Report','Sec','GenotypeWorkReport','Reports: Genotyping - List litters that are expected to be genotyped based on a date range.',1,67),('Add Document','Sec','AddDocument','Documents: Upload a document (file) to a JCMS folder and give it a title.',1,68),('Associate Documents','Sec','AssociateDocuments','Documents: Keep track of the associations between user  documents and mice, genotypes, experiments, etc.',1,69),('Edit Document Associations','Sec','EditDocumentAssociations','Documents: Make changes to the associations between documents and IDs.',1,70),('Bulk Add Document Associations','Sec','BulkAddDocumentAssociations','Documents: Add more associations between a document and IDs.',1,71),('Edit Document','Sec','EditDocument','Documents: Make changes to the information about a document.',1,72),('Bulk Edit Experimental Data','Owner','BulkEditExpData','Experiment: Make the same change to multiple experimental data records.',1,73),('Experimental Data Repeat Measure','Owner','ExpDataRepeatMeasurement','Experiment: Add experimental data for a repeated test.',1,74),('Experiment Work Report','Sec','RequestExperimentReport','Reports: Experiments - Show all the mice due for processing during a defined time span.',1,75),('Breeding Performance Report','Sec','BreedingPerformanceReport','Reports: Matings - Summarize litters',1,76),('Add or Edit Genotyping Request','Owner','AddOrEditGenotypingRequest','Add or edit genotyping request',1,77),('Calendar Schedule for Mouse Uses','Sec','CalendarSchedule_MouseUses','Calendar: Used to display both projected and actual mouse use dates',1,78),('Request a Calendar','Sec','RequestMouseUseCalendar','Calendar: Used to specify criteria for a calendar',1,79),('Add Embryo Litter','Sec','AddEmbryoLitter','Add Embryo Litter: A litter that is stored as a sample',1,80),('Edit Embryo Litter','Owner','EditEmbryoLitter','Edit Embryo Litter: Edit litters that created samples',1,81);
/*!40000 ALTER TABLE `DbFormPrivileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DbSetup`
--

DROP TABLE IF EXISTS `DbSetup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DbSetup` (
  `MTSVar` varchar(64) DEFAULT NULL,
  `MTSValue` varchar(128) DEFAULT NULL,
  `MTSVarDescription` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `_dbSetup_key` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`_dbSetup_key`),
  UNIQUE KEY `MTSVar_UNIQUE` (`MTSVar`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DbSetup`
--

LOCK TABLES `DbSetup` WRITE;
/*!40000 ALTER TABLE `DbSetup` DISABLE KEYS */;
INSERT INTO `DbSetup` VALUES ('JCMS_ACTIVATE_MATINGS_INCREMENT','false','true or false; if true the mating ID is automatically incremented after activating a mating. May be overridden on the form.',1,1),('JCMS_ADD_AT_WEAN_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after adding a mouse at weaning. May be overridden on the form.',1,2),('JCMS_ADD_GENOTYPE_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after adding a genotype. May be overridden on the form.',1,3),('JCMS_ADD_LITTER_INCREMENT','false','true or false; if true the litter ID is automatically incremented after adding a litter. May be overridden on the form.',1,4),('JCMS_ADD_LITTER_PUPS_INCREMENT','false','true or false; if true the litter ID is automatically incremented after adding a litter with pups. May be overridden on the form.',1,5),('JCMS_ADD_MOUSE_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after adding a mouse. May be overridden on the form.',1,6),('JCMS_ADD_MOUSE_USE_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after adding a mouse use. May be overridden on the form.',1,7),('JCMS_ALLELE_CONF_HIGH',NULL,'Character(s) displayed/printed as part of a genotype to indicate high confidence in an allele. May be blank.',1,8),('JCMS_ALLELE_CONF_LOW','?','Character(s) displayed/printed as part of a genotype to indicate low confidence in an allele. May be blank. Ex: use ? To display Abc +?/+? For low confidence in both alleles.',1,9),('JCMS_ALLELE_GENE_SEPARATORS',NULL,'Specify separators to display around the alleles. Ex: specify [] to get Abc[+/+] or leave blank for Abc +/+',1,10),('JCMS_ALLOW_USERDEFINED_GENERATIONS','false','true or false; If true user may add generations to the Generations table on the fly.',1,11),('JCMS_ALLOW_USERDEFINED_STRAINS','false','true or false; If true user may add strains to the Strains table (via the Strains form) on the fly.',1,12),('JCMS_AUTO_RETIRE_MATINGS','true','true or false; if true, automatically retire a mating when the sire and dam(s) all have an exit life status.',1,13),('JCMS_AUTO_RETIRE_PENS','false','true or false; If true automatically retire a pen when it becomes empty or all occupants are not alive.',1,14),('JCMS_AUTOINCREMENT_GENERATION','false','true or false; If true the generation for a litter will be automatically incremented when creating matings. May be overridden on the form.',1,15),('JCMS_BILL_PARTIAL_FIRST_DAY','false','true or false; if true a pen is counted for billing on the day it is created or moved into a room.',1,16),('JCMS_BILL_PARTIAL_LAST_DAY','false','true or false; if true a pen is counted for billing on the day it is retired or moved out of a room.',1,17),('JCMS_CREATE_PEN_INCREMENT','false','true or false; if true the pen ID is automatically incremented when adding new pens.',1,18),('JCMS_DATA_FILE_DIRECTORY',NULL,'The root directory where JCMS data files are stored',1,19),('JCMS_DAYS_TO_GENOTYPE','14','The number of days from the birth date to when the pups should be genotyped.',1,20),('JCMS_DEFAULT_CONTAINER_STATUS','active','Specify the default value for pen status; must match a value in the pen status table.',1,21),('JCMS_DEFAULT_EXIT_TERM','E','Specify the default term used when exiting mice from the colony, usually E or K; must match a value in the life status table. May be overridden on forms.',1,22),('JCMS_DESIGN_RETIRE_MATINGS_INCREMENT','false','true or false; if true the mating ID is automatically incremented when using the design retire mating form. May be overridden on the form.',1,23),('JCMS_EDIT_LITTER_INCREMENT','false','true or false; if true the litter ID is automatically incremented after editing a litter. May be overridden on the form.',1,24),('JCMS_EDIT_MOUSE_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after editing a mouse. May be overridden on the form.',1,25),('JCMS_EDIT_MOUSE_USE_INCREMENT','false','true or false; if true the mouse ID is automatically incremented after editing a use. May be overridden on the form.',1,26),('JCMS_ENABLE_GENOTYPE_IMPORT','true','true or false; must be set to true to allow importing genotypes.',1,27),('JCMS_ENFORCE_APPROVED_MATINGS','false','true or false; if true then user is only allowed to set litter strain to pre-approved matings. May be overridden on the form.',1,28),('JCMS_EXT_WEAN_TIME','28','the number of days from the birth date to when a litter should be weaned - used for late weanings/\"long\" wean time.',1,29),('JCMS_FEMALES_FIRST','true','true or false; if true females are the first to be assigned mouse IDs when adding mice with a bulk add.',1,30),('JCMS_GENERATION_INCREMENT_RIGHTMOST','true','true or false; if true, increment the rightmost numeric portion of the generation; if false the leftmost.',1,31),('JCMS_IMPORT_EXP_DATA_MICE_MUST_BE_PRESELECTED','true','true or false; if false, any mouse IDs not pre-selected will be automatically added to the plan and test.',1,33),('JCMS_LITTERID_INCREMENT_RIGHTMOST','true','true or false; if true, increment the rightmost numeric portion of the litter ID; if false the leftmost.',1,34),('JCMS_LOOP_LITTER_NUMBERS','true','true or false; if true the litter numbers recycle after 10 litters, appending a character to the number.',1,35),('JCMS_MAX_IMPORT_EXP_DATA_ERRORS','10','Import Experimental data; when this number of errors is reached, verification stops and a report is printed.',1,36),('JCMS_MOUSEID_INCREMENT_RIGHTMOST','true','true or false; if true, increment the rightmost numeric portion of the mouse ID; if false the leftmost.',1,37),('JCMS_PEN_NAMES_INCREMENT_RIGHTMOST','true','true or false; if true, increment the rightmost numeric portion of the pen name; if false the leftmost.',1,38),('JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS','true','true or false; if true, mice with an exit status such as dead, euthanized, missing, shipped, etc. will print on cage cards.',1,39),('JCMS_RETIRE_MATINGS_INCREMENT','false','true or false; if true the mating ID is automatically incremented after retiring a mating. May be overridden on the form.',1,40),('JCMS_SAMPLE_LABEL_REPORT','PrintSampleLabels','The name of the report used to print sample labels from the Print Sample Label form.',1,41),('JCMS_SORT_BY_PEN_NAME','false','true or false; if true then lists with pen ID and pen name will sort alphabetically by pen name instead of pen ID.',1,42),('JCMS_STANDARD_WEAN_TIME','18','the number of days from the birth date to when a litter should normally be weaned.',1,43),('JCMS_STRAINNAME_FIRST','true','true or false, if true the strain name will appear first (to the left of the JR number) in all the dropdown lists.',1,44),('JCMS_USING_HEALTH_LEVEL','true','true or false; if false the room health level will not be displayed on most forms.',1,45),('JCMS_USING_PEN_COMMENTS','true','true or false; if false the pen comment field will not be displayed on most forms.',1,46),('JCMS_USING_PEN_NAMES','true','true or false; if false pen names will not be displayed on most forms.',1,47),('JCMS_WARN_DUPLICATE_PEN_NAME','false','true or false; if true warn if a duplicate pen name is used.',1,48),('JCMS_WARN_LITTER_NOT_UPDATED','true','true or false, if true, add mice at weaning will warn if litter record was not updated.',1,49),('JCMS_WRITE_FAILED_TRANSACTIONS','false','true or false; used by the Add Sample form for debugging, should be set to false.',1,50),('MTS_1PEN_WEAN_CAGE_CARD','TS_1PWeanCageCard','The name of the cage card report for 1-pen wean cage cards.',1,51),('MTS_2PEN_WEAN_CAGE_CARD','OS_2PWeanCageCard','The name of the cage card report for 2-pen wean cage cards.',1,52),('MTS_AUTO_COLOR','true','true or false; if true, then many forms get all data entry fields colored after user hits submit. Color is cleared after user visits the field.',1,53),('MTS_AUTO_LITTER_NUMS','on','on or off; if on litter numbers are automatically generated for matings.',1,54),('MTS_AUTOINCR_DAMS_SIRES','false','true or false, if true the dams and sire are automatically incremented on the handheld trio/pair mating form. May be overridden on the form.',1,55),('MTS_CAGE_CARD_DETAIL_NOTE','Put card note here (dbsetup)','A note that will be printed on all Detail cage cards.',1,56),('MTS_DEFAULT_AUTO_INCREMENT','off','on or off; if on then auto increment is the default on forms with functions that do not have a specific auto increrment setup variable.',1,57),('MTS_DEFAULT_COD',NULL,'Specify the default cause of death; must match a value in the cv_CauseOfDeath table.',1,58),('MTS_DEFAULT_HEALTH_LEVEL','2','Specify the default room health level; must match a value in the Health Level table.',1,59),('MTS_DEFAULT_MOUSE_ORIGIN',NULL,'Specify the default mouse origin; must match a value in the cv_MouseOrigin table.',1,60),('MTS_DEFAULT_MOUSE_ROOM','unknown','Specify the default mouse room; must match a value in the Room table.',1,61),('MTS_DEFAULT_PRINTCARDS','true','true or false; if true the print cage card option on the handheld trio/pair mating form is set on. May be overridden on the form.',1,62),('MTS_DEFAULT_USE_BASEMOUSE_ID','false','true or false; if true indicates using a base mouse id on the handheld add litter form. May be overridden on the form.',1,63),('MTS_DETAIL_CAGE_CARD','TS_DetailCageCard','The name of the cage card report for detail cage cards.',1,64),('MTS_DOB_ROLLBACK_OFFSET','7','Subtract this number of days from today\'s date to get the date of birth.',1,65),('MTS_HELP_EMAIL','http://community.jax.org/forums/default.aspx?GroupID=7','Specify an email address that users can send JCMS support questions to. Used as the link for \"Report a problem\" on the JCMS welcome window.',1,66),('MTS_IMPORT_MAX_WARNING','20','Users will be warned if they try to import more than this number of mice at once (only effects bulk imports of mice).',1,67),('MTS_INSTALLATION_NAME','JCMS','Name of this JCMS installation (anything you want to call it).',1,68),('MTS_LITTER_ID_PREFIX','L','A short string of characters that are prefixed on litter IDs generated by JCMS (not all litter IDs are generated by JCMS).',1,69),('MTS_MAIN_BUTTON_BAR','MainButtonBarJCMS','Name of the main button bar form displayed when user clicks start workstation from welcome window.',1,70),('MTS_MATING_CAGE_CARD','OS_MatingCageCard','The name of the cage card report for mating cage cards.',1,71),('MTS_MATING_CAGE_CARD2','TS_MatingCageCardStyle2WithBarCode','The name of the cage card report for mating cage cards style 2; handheld only, print cage card form.',1,72),('MTS_MATING_ID_PREFIX','M','A short string of characters that are prefixed on mating IDs when printed on some cage cards.',1,73),('MTS_MAX_MICE_PER_PEN','10','Maximum number of live mice in any pen.',1,74),('MTS_MOUSE_ID_PREFIX','A','A short string of characters that are prefixed on mouse IDs generated by JCMS (not all mouse IDs are generated by JCMS).',1,75),('MTS_NUM_AUTO_LITTER_NUMS','10','this variable sets the number of litter numbers that are assigned to a mating. It should be set to a value bigger than the max number of litters you ever expect. Suggested values are 10 or 100.',1,76),('MTS_PEN_ID_PREFIX','P','A short string of characters that are prefixed on pen IDs when printed on some cage cards.',1,77),('MTS_PI_NAME','PI Name','Name of lab PI who owns colonies tracked by JCMS, printed on some cage cards.',1,78),('MTS_PI_PHONE','555-1212 (office)','Phone numbers, printed on mating card.',1,79),('MTS_RELAXED_PEN_NUMS','true','true or false; if false, then it is required that a cage card is printed for all pens.',1,80),('MTS_THRESHOLD_MICE_BATCH_OPERATION','50','This variable will trigger a warning from the handheld wean and exit form when the number of affected mice exceeds this value.',1,81),('JCMS_DATABASE_DBMS','MSAccess','Name of the database management system. Valid values are MSAccess or MySQL. Required for MySQL to function properly.',1,82),('JCMS_IMPORT_EXP_DATA_ALLOW_MULTIPLE','false','If importing with NO experimental plan, allow more than one record for a test type/mouse combination.',1,83),('JCMS_JAX_ACCOUNT_NUMBER','12.210.3163.56048','The value to submit in every export-to-TGS file as the \"JAX Account #\"',1,84),('JCMS_TGS_REQUEST_PATH','\\\\jax\\jax\\cs\\private\\appdev\\src\\komp\\DEV','The file path to store the file being submitted to TGS.  Note this should not include the actual filename or a trailing slash',1,85),('JCMS_TGS_REQUEST_FILENAME','komp2typ.txt','The filename to store the file being submitted to TGS.  Note this value should not be overridden; TGS expects this filename verbatim.',1,86),('JCMS_TGS_RESPONSE_PATH','\\\\jax\\jax\\cs\\private\\appdev\\src\\komp\\DEV\\outbox','The file path to look for response files from TGS.',1,87),('JCMS_MTS_IMPORT_PATH','C:\\','The file path to default to for non-TGS imports via the \"Import Genotype\" button.',1,88),('JCMS_GESTATION_PERIOD','21','The length of gestation (pregnancy) is used to determine when plug dates expire.',1,89),('JCMS_JAXLAB_INSTALLATION','false','Is this a Jackson Laboratory installation.',1,90);
/*!40000 ALTER TABLE `DbSetup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Document`
--

DROP TABLE IF EXISTS `Document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Document` (
  `_document_key` int(11) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT 'None',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_document_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Document`
--

LOCK TABLES `Document` WRITE;
/*!40000 ALTER TABLE `Document` DISABLE KEYS */;
/*!40000 ALTER TABLE `Document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DocumentMapping`
--

DROP TABLE IF EXISTS `DocumentMapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DocumentMapping` (
  `_documentMapping_key` int(11) NOT NULL,
  `_table_key` int(11) NOT NULL,
  `_tableName_key` int(11) NOT NULL,
  `_documentVersion_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_documentMapping_key`),
  KEY `DocumentMapping_ibfk_1` (`_tableName_key`),
  KEY `DocumentMapping_ibfk_11` (`_tableName_key`),
  KEY `DocumentMapping_ibfk_2` (`_documentVersion_key`),
  KEY `DocumentMapping_ibfk_22` (`_documentVersion_key`),
  CONSTRAINT `documentmapping_ibfk_2` FOREIGN KEY (`_documentVersion_key`) REFERENCES `DocumentVersion` (`_documentVersion_key`),
  CONSTRAINT `documentmapping_ibfk_1` FOREIGN KEY (`_tableName_key`) REFERENCES `cv_TableName` (`_tableName_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DocumentMapping`
--

LOCK TABLES `DocumentMapping` WRITE;
/*!40000 ALTER TABLE `DocumentMapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `DocumentMapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DocumentVersion`
--

DROP TABLE IF EXISTS `DocumentVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DocumentVersion` (
  `_document_key` int(11) NOT NULL,
  `FilePath` varchar(255) DEFAULT NULL,
  `FileName` varchar(64) DEFAULT NULL,
  `DateUpload` datetime DEFAULT NULL,
  `Owner` varchar(75) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `_documentVersion_key` int(11) NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '-1',
  `majorVersion` int(11) DEFAULT NULL,
  `minorVersion` int(11) DEFAULT NULL,
  `revision` int(11) DEFAULT NULL,
  `URL` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`_documentVersion_key`),
  KEY `DocumentVersion_ibfk_1` (`_document_key`),
  KEY `DocumentVersion_ibfk_11` (`_document_key`),
  CONSTRAINT `documentversion_ibfk_1` FOREIGN KEY (`_document_key`) REFERENCES `Document` (`_document_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DocumentVersion`
--

LOCK TABLES `DocumentVersion` WRITE;
/*!40000 ALTER TABLE `DocumentVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `DocumentVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpData`
--

DROP TABLE IF EXISTS `ExpData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpData` (
  `_expData_key` int(11) NOT NULL,
  `_expDataDesc_key` int(11) NOT NULL,
  `_expTest_key` int(11) DEFAULT NULL,
  `_specimen_key` int(11) NOT NULL,
  `dataID` int(11) NOT NULL,
  `specimen_type` varchar(8) NOT NULL,
  `owner` varchar(8) NOT NULL,
  `expDate` datetime DEFAULT NULL,
  `age` float DEFAULT NULL,
  `abnormalData` tinyint(1) DEFAULT NULL,
  `d1` varchar(64) DEFAULT NULL,
  `d2` varchar(64) DEFAULT NULL,
  `d3` varchar(64) DEFAULT NULL,
  `d4` varchar(64) DEFAULT NULL,
  `d5` varchar(64) DEFAULT NULL,
  `d6` varchar(64) DEFAULT NULL,
  `d7` varchar(64) DEFAULT NULL,
  `d8` varchar(64) DEFAULT NULL,
  `d9` varchar(64) DEFAULT NULL,
  `d10` varchar(64) DEFAULT NULL,
  `d11` varchar(64) DEFAULT NULL,
  `d12` varchar(64) DEFAULT NULL,
  `d13` varchar(64) DEFAULT NULL,
  `d14` varchar(64) DEFAULT NULL,
  `d15` varchar(64) DEFAULT NULL,
  `d16` varchar(64) DEFAULT NULL,
  `d17` varchar(64) DEFAULT NULL,
  `d18` varchar(64) DEFAULT NULL,
  `d19` varchar(64) DEFAULT NULL,
  `d20` varchar(64) DEFAULT NULL,
  `d21` varchar(64) DEFAULT NULL,
  `d22` varchar(64) DEFAULT NULL,
  `d23` varchar(64) DEFAULT NULL,
  `d24` varchar(64) DEFAULT NULL,
  `d25` varchar(64) DEFAULT NULL,
  `d26` varchar(64) DEFAULT NULL,
  `d27` varchar(64) DEFAULT NULL,
  `d28` varchar(64) DEFAULT NULL,
  `d29` varchar(64) DEFAULT NULL,
  `d30` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expData_key`),
  UNIQUE KEY `_expData_key` (`_expData_key`),
  UNIQUE KEY `dataID` (`dataID`),
  KEY `_expDataDesc_key` (`_expDataDesc_key`),
  KEY `_expTest_key` (`_expTest_key`),
  KEY `_specimen_key` (`_specimen_key`),
  KEY `{14C09F25-7450-4308-816D-F8917D17F343}` (`_expDataDesc_key`),
  KEY `{2B2EB535-1AD4-4D95-BC20-41B1E65D3C8A}` (`_expTest_key`),
  KEY `{70F7E37D-76DB-44F6-8B89-A2794F55E9C3}` (`_specimen_key`),
  KEY `abnormalData` (`abnormalData`),
  KEY `owner` (`owner`),
  CONSTRAINT `expdata_ibfk_3` FOREIGN KEY (`_specimen_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `expdata_ibfk_1` FOREIGN KEY (`_expDataDesc_key`) REFERENCES `ExpDataDescriptor` (`_expDataDescriptor_key`),
  CONSTRAINT `expdata_ibfk_2` FOREIGN KEY (`_expTest_key`) REFERENCES `ExpTest` (`_expTest_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpData`
--

LOCK TABLES `ExpData` WRITE;
/*!40000 ALTER TABLE `ExpData` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpDataDefault`
--

DROP TABLE IF EXISTS `ExpDataDefault`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpDataDefault` (
  `_expDataDefault_key` int(11) NOT NULL,
  `_expDataDescriptor_key` int(11) NOT NULL,
  `defaultName` varchar(32) NOT NULL,
  `comment` varchar(128) DEFAULT NULL,
  `d1_default` varchar(64) DEFAULT NULL,
  `d2_default` varchar(64) DEFAULT NULL,
  `d3_default` varchar(64) DEFAULT NULL,
  `d4_default` varchar(64) DEFAULT NULL,
  `d5_default` varchar(64) DEFAULT NULL,
  `d6_default` varchar(64) DEFAULT NULL,
  `d7_default` varchar(64) DEFAULT NULL,
  `d8_default` varchar(64) DEFAULT NULL,
  `d9_default` varchar(64) DEFAULT NULL,
  `d10_default` varchar(64) DEFAULT NULL,
  `d11_default` varchar(64) DEFAULT NULL,
  `d12_default` varchar(64) DEFAULT NULL,
  `d13_default` varchar(64) DEFAULT NULL,
  `d14_default` varchar(64) DEFAULT NULL,
  `d15_default` varchar(64) DEFAULT NULL,
  `d16_default` varchar(64) DEFAULT NULL,
  `d17_default` varchar(64) DEFAULT NULL,
  `d18_default` varchar(64) DEFAULT NULL,
  `d19_default` varchar(64) DEFAULT NULL,
  `d20_default` varchar(64) DEFAULT NULL,
  `d21_default` varchar(64) DEFAULT NULL,
  `d22_default` varchar(64) DEFAULT NULL,
  `d23_default` varchar(64) DEFAULT NULL,
  `d24_default` varchar(64) DEFAULT NULL,
  `d25_default` varchar(64) DEFAULT NULL,
  `d26_default` varchar(64) DEFAULT NULL,
  `d27_default` varchar(64) DEFAULT NULL,
  `d28_default` varchar(64) DEFAULT NULL,
  `d29_default` varchar(64) DEFAULT NULL,
  `d30_default` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expDataDefault_key`),
  UNIQUE KEY `_expDataDefault_key` (`_expDataDefault_key`),
  KEY `_expDataDescriptor_key` (`_expDataDescriptor_key`),
  KEY `{7D2B75DF-28A0-4387-A3C1-9EFB27169EDF}` (`_expDataDescriptor_key`),
  KEY `defaultName` (`defaultName`),
  CONSTRAINT `expdatadefault_ibfk_1` FOREIGN KEY (`_expDataDescriptor_key`) REFERENCES `ExpDataDescriptor` (`_expDataDescriptor_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpDataDefault`
--

LOCK TABLES `ExpDataDefault` WRITE;
/*!40000 ALTER TABLE `ExpDataDefault` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpDataDefault` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpDataDescriptor`
--

DROP TABLE IF EXISTS `ExpDataDescriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpDataDescriptor` (
  `_expDataDescriptor_key` int(11) NOT NULL,
  `testType` varchar(32) NOT NULL,
  `d1_caption` varchar(32) DEFAULT NULL,
  `d1_fieldDescription` varchar(32) DEFAULT NULL,
  `d1_dataFormat` varchar(4) DEFAULT NULL,
  `d1_required` tinyint(1) DEFAULT NULL,
  `d1_minValue` float DEFAULT NULL,
  `d1_maxValue` float DEFAULT NULL,
  `d2_caption` varchar(32) DEFAULT NULL,
  `d2_fieldDescription` varchar(32) DEFAULT NULL,
  `d2_dataFormat` varchar(4) DEFAULT NULL,
  `d2_required` tinyint(1) DEFAULT NULL,
  `d2_minValue` float DEFAULT NULL,
  `d2_maxValue` float DEFAULT NULL,
  `d3_caption` varchar(32) DEFAULT NULL,
  `d3_fieldDescription` varchar(32) DEFAULT NULL,
  `d3_dataFormat` varchar(4) DEFAULT NULL,
  `d3_required` tinyint(1) DEFAULT NULL,
  `d3_minValue` float DEFAULT NULL,
  `d3_maxValue` float DEFAULT NULL,
  `d4_caption` varchar(32) DEFAULT NULL,
  `d4_fieldDescription` varchar(32) DEFAULT NULL,
  `d4_dataFormat` varchar(4) DEFAULT NULL,
  `d4_required` tinyint(1) DEFAULT NULL,
  `d4_minValue` float DEFAULT NULL,
  `d4_maxValue` float DEFAULT NULL,
  `d5_caption` varchar(32) DEFAULT NULL,
  `d5_fieldDescription` varchar(32) DEFAULT NULL,
  `d5_dataFormat` varchar(4) DEFAULT NULL,
  `d5_required` tinyint(1) DEFAULT NULL,
  `d5_minValue` float DEFAULT NULL,
  `d5_maxValue` float DEFAULT NULL,
  `d6_caption` varchar(32) DEFAULT NULL,
  `d6_fieldDescription` varchar(32) DEFAULT NULL,
  `d6_dataFormat` varchar(4) DEFAULT NULL,
  `d6_required` tinyint(1) DEFAULT NULL,
  `d6_minValue` float DEFAULT NULL,
  `d6_maxValue` float DEFAULT NULL,
  `d7_caption` varchar(32) DEFAULT NULL,
  `d7_fieldDescription` varchar(32) DEFAULT NULL,
  `d7_dataFormat` varchar(4) DEFAULT NULL,
  `d7_required` tinyint(1) DEFAULT NULL,
  `d7_minValue` float DEFAULT NULL,
  `d7_maxValue` float DEFAULT NULL,
  `d8_caption` varchar(32) DEFAULT NULL,
  `d8_fieldDescription` varchar(32) DEFAULT NULL,
  `d8_dataFormat` varchar(4) DEFAULT NULL,
  `d8_required` tinyint(1) DEFAULT NULL,
  `d8_minValue` float DEFAULT NULL,
  `d8_maxValue` float DEFAULT NULL,
  `d9_caption` varchar(32) DEFAULT NULL,
  `d9_fieldDescription` varchar(32) DEFAULT NULL,
  `d9_dataFormat` varchar(4) DEFAULT NULL,
  `d9_required` tinyint(1) DEFAULT NULL,
  `d9_minValue` float DEFAULT NULL,
  `d9_maxValue` float DEFAULT NULL,
  `d10_caption` varchar(32) DEFAULT NULL,
  `d10_fieldDescription` varchar(32) DEFAULT NULL,
  `d10_dataFormat` varchar(4) DEFAULT NULL,
  `d10_required` tinyint(1) DEFAULT NULL,
  `d10_minValue` float DEFAULT NULL,
  `d10_maxValue` float DEFAULT NULL,
  `d11_caption` varchar(32) DEFAULT NULL,
  `d11_fieldDescription` varchar(32) DEFAULT NULL,
  `d11_dataFormat` varchar(4) DEFAULT NULL,
  `d11_required` tinyint(1) DEFAULT NULL,
  `d11_minValue` float DEFAULT NULL,
  `d11_maxValue` float DEFAULT NULL,
  `d12_caption` varchar(32) DEFAULT NULL,
  `d12_fieldDescription` varchar(32) DEFAULT NULL,
  `d12_dataFormat` varchar(4) DEFAULT NULL,
  `d12_required` tinyint(1) DEFAULT NULL,
  `d12_minValue` float DEFAULT NULL,
  `d12_maxValue` float DEFAULT NULL,
  `d13_caption` varchar(32) DEFAULT NULL,
  `d13_fieldDescription` varchar(32) DEFAULT NULL,
  `d13_dataFormat` varchar(4) DEFAULT NULL,
  `d13_required` tinyint(1) DEFAULT NULL,
  `d13_minValue` float DEFAULT NULL,
  `d13_maxValue` float DEFAULT NULL,
  `d14_caption` varchar(32) DEFAULT NULL,
  `d14_fieldDescription` varchar(32) DEFAULT NULL,
  `d14_dataFormat` varchar(4) DEFAULT NULL,
  `d14_required` tinyint(1) DEFAULT NULL,
  `d14_minValue` float DEFAULT NULL,
  `d14_maxValue` float DEFAULT NULL,
  `d15_caption` varchar(32) DEFAULT NULL,
  `d15_fieldDescription` varchar(32) DEFAULT NULL,
  `d15_dataFormat` varchar(4) DEFAULT NULL,
  `d15_required` tinyint(1) DEFAULT NULL,
  `d15_minValue` float DEFAULT NULL,
  `d15_maxValue` float DEFAULT NULL,
  `d16_caption` varchar(32) DEFAULT NULL,
  `d16_fieldDescription` varchar(32) DEFAULT NULL,
  `d16_dataFormat` varchar(4) DEFAULT NULL,
  `d16_required` tinyint(1) DEFAULT NULL,
  `d16_minValue` float DEFAULT NULL,
  `d16_maxValue` float DEFAULT NULL,
  `d17_caption` varchar(32) DEFAULT NULL,
  `d17_fieldDescription` varchar(32) DEFAULT NULL,
  `d17_dataFormat` varchar(4) DEFAULT NULL,
  `d17_required` tinyint(1) DEFAULT NULL,
  `d17_minValue` float DEFAULT NULL,
  `d17_maxValue` float DEFAULT NULL,
  `d18_caption` varchar(32) DEFAULT NULL,
  `d18_fieldDescription` varchar(32) DEFAULT NULL,
  `d18_dataFormat` varchar(4) DEFAULT NULL,
  `d18_required` tinyint(1) DEFAULT NULL,
  `d18_minValue` float DEFAULT NULL,
  `d18_maxValue` float DEFAULT NULL,
  `d19_caption` varchar(32) DEFAULT NULL,
  `d19_fieldDescription` varchar(32) DEFAULT NULL,
  `d19_dataFormat` varchar(4) DEFAULT NULL,
  `d19_required` tinyint(1) DEFAULT NULL,
  `d19_minValue` float DEFAULT NULL,
  `d19_maxValue` float DEFAULT NULL,
  `d20_caption` varchar(32) DEFAULT NULL,
  `d20_fieldDescription` varchar(32) DEFAULT NULL,
  `d20_dataFormat` varchar(4) DEFAULT NULL,
  `d20_required` tinyint(1) DEFAULT NULL,
  `d20_minValue` float DEFAULT NULL,
  `d20_maxValue` float DEFAULT NULL,
  `d21_caption` varchar(32) DEFAULT NULL,
  `d21_fieldDescription` varchar(32) DEFAULT NULL,
  `d21_dataFormat` varchar(4) DEFAULT NULL,
  `d21_required` tinyint(1) DEFAULT NULL,
  `d21_minValue` float DEFAULT NULL,
  `d21_maxValue` float DEFAULT NULL,
  `d22_caption` varchar(32) DEFAULT NULL,
  `d22_fieldDescription` varchar(32) DEFAULT NULL,
  `d22_dataFormat` varchar(4) DEFAULT NULL,
  `d22_required` tinyint(1) DEFAULT NULL,
  `d22_minValue` float DEFAULT NULL,
  `d22_maxValue` float DEFAULT NULL,
  `d23_caption` varchar(32) DEFAULT NULL,
  `d23_fieldDescription` varchar(32) DEFAULT NULL,
  `d23_dataFormat` varchar(4) DEFAULT NULL,
  `d23_required` tinyint(1) DEFAULT NULL,
  `d23_minValue` float DEFAULT NULL,
  `d23_maxValue` float DEFAULT NULL,
  `d24_caption` varchar(32) DEFAULT NULL,
  `d24_fieldDescription` varchar(32) DEFAULT NULL,
  `d24_dataFormat` varchar(4) DEFAULT NULL,
  `d24_required` tinyint(1) DEFAULT NULL,
  `d24_minValue` float DEFAULT NULL,
  `d24_maxValue` float DEFAULT NULL,
  `d25_caption` varchar(32) DEFAULT NULL,
  `d25_fieldDescription` varchar(32) DEFAULT NULL,
  `d25_dataFormat` varchar(4) DEFAULT NULL,
  `d25_required` tinyint(1) DEFAULT NULL,
  `d25_minValue` float DEFAULT NULL,
  `d25_maxValue` float DEFAULT NULL,
  `d26_caption` varchar(32) DEFAULT NULL,
  `d26_fieldDescription` varchar(32) DEFAULT NULL,
  `d26_dataFormat` varchar(4) DEFAULT NULL,
  `d26_required` tinyint(1) DEFAULT NULL,
  `d26_minValue` float DEFAULT NULL,
  `d26_maxValue` float DEFAULT NULL,
  `d27_caption` varchar(32) DEFAULT NULL,
  `d27_fieldDescription` varchar(32) DEFAULT NULL,
  `d27_dataFormat` varchar(4) DEFAULT NULL,
  `d27_required` tinyint(1) DEFAULT NULL,
  `d27_minValue` float DEFAULT NULL,
  `d27_maxValue` float DEFAULT NULL,
  `d28_caption` varchar(32) DEFAULT NULL,
  `d28_fieldDescription` varchar(32) DEFAULT NULL,
  `d28_dataFormat` varchar(4) DEFAULT NULL,
  `d28_required` tinyint(1) DEFAULT NULL,
  `d28_minValue` float DEFAULT NULL,
  `d28_maxValue` float DEFAULT NULL,
  `d29_caption` varchar(32) DEFAULT NULL,
  `d29_fieldDescription` varchar(32) DEFAULT NULL,
  `d29_dataFormat` varchar(4) DEFAULT NULL,
  `d29_required` tinyint(1) DEFAULT NULL,
  `d29_minValue` float DEFAULT NULL,
  `d29_maxValue` float DEFAULT NULL,
  `d30_caption` varchar(32) DEFAULT NULL,
  `d30_fieldDescription` varchar(32) DEFAULT NULL,
  `d30_dataFormat` varchar(4) DEFAULT NULL,
  `d30_required` tinyint(1) DEFAULT NULL,
  `d30_minValue` float DEFAULT NULL,
  `d30_maxValue` float DEFAULT NULL,
  `testTypeNotes` longtext,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expDataDescriptor_key`),
  UNIQUE KEY `_expDataDescriptor_key` (`_expDataDescriptor_key`),
  UNIQUE KEY `testType` (`testType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpDataDescriptor`
--

LOCK TABLES `ExpDataDescriptor` WRITE;
/*!40000 ALTER TABLE `ExpDataDescriptor` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpDataDescriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpPlan`
--

DROP TABLE IF EXISTS `ExpPlan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpPlan` (
  `_expPlan_key` int(11) NOT NULL,
  `planID` int(11) NOT NULL,
  `expPlanName` varchar(64) NOT NULL,
  `owner` varchar(8) NOT NULL,
  `fieldOfStudy` varchar(32) DEFAULT NULL,
  `expNotes` longtext,
  `expStatus` varchar(16) NOT NULL DEFAULT 'active',
  `keyword1` varchar(32) DEFAULT NULL,
  `keyword2` varchar(32) DEFAULT NULL,
  `keyword3` varchar(32) DEFAULT NULL,
  `keyword4` varchar(32) DEFAULT NULL,
  `keyword5` varchar(32) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expPlan_key`),
  UNIQUE KEY `_expPlan_key` (`_expPlan_key`),
  UNIQUE KEY `expPlanName` (`expPlanName`),
  UNIQUE KEY `planID` (`planID`),
  KEY `expStatus` (`expStatus`),
  KEY `keyword1` (`keyword1`),
  KEY `keyword11` (`keyword2`),
  KEY `keyword12` (`keyword3`),
  KEY `keyword13` (`keyword4`),
  KEY `keyword14` (`keyword5`),
  KEY `owner` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpPlan`
--

LOCK TABLES `ExpPlan` WRITE;
/*!40000 ALTER TABLE `ExpPlan` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpPlan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpPlanMouseMap`
--

DROP TABLE IF EXISTS `ExpPlanMouseMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpPlanMouseMap` (
  `_expPlanMouseMap_key` int(11) NOT NULL,
  `_mouse_key` int(11) NOT NULL,
  `_expPlan_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expPlanMouseMap_key`),
  UNIQUE KEY `_expPlanMouseMap_key` (`_expPlanMouseMap_key`),
  UNIQUE KEY `MiceInPlan` (`_expPlan_key`,`_mouse_key`),
  KEY `_expPlan_key` (`_expPlan_key`),
  KEY `_mouse_key` (`_mouse_key`),
  KEY `ExpPlanExpPlanMouseMap` (`_expPlan_key`),
  KEY `MouseExpPlanMouseMap` (`_mouse_key`),
  CONSTRAINT `expplanmousemap_ibfk_2` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `expplanmousemap_ibfk_1` FOREIGN KEY (`_expPlan_key`) REFERENCES `ExpPlan` (`_expPlan_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpPlanMouseMap`
--

LOCK TABLES `ExpPlanMouseMap` WRITE;
/*!40000 ALTER TABLE `ExpPlanMouseMap` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpPlanMouseMap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpTest`
--

DROP TABLE IF EXISTS `ExpTest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpTest` (
  `_expTest_key` int(11) NOT NULL,
  `_expPlan_key` int(11) NOT NULL,
  `_expDataDescriptor_key` int(11) NOT NULL,
  `testName` varchar(32) DEFAULT NULL,
  `testID` int(11) NOT NULL,
  `proposedTestDate` datetime DEFAULT NULL,
  `proposedMinAge` double DEFAULT NULL,
  `proposedMaxAge` double DEFAULT NULL,
  `numMice` int(11) DEFAULT NULL,
  `testStatus` varchar(8) NOT NULL,
  `d1_defaultValue` varchar(64) DEFAULT NULL,
  `d2_defaultValue` varchar(64) DEFAULT NULL,
  `d3_defaultValue` varchar(64) DEFAULT NULL,
  `d4_defaultValue` varchar(64) DEFAULT NULL,
  `d5_defaultValue` varchar(64) DEFAULT NULL,
  `d6_defaultValue` varchar(64) DEFAULT NULL,
  `d7_defaultValue` varchar(64) DEFAULT NULL,
  `d8_defaultValue` varchar(64) DEFAULT NULL,
  `d9_defaultValue` varchar(64) DEFAULT NULL,
  `d10_defaultValue` varchar(64) DEFAULT NULL,
  `d11_defaultValue` varchar(64) DEFAULT NULL,
  `d12_defaultValue` varchar(64) DEFAULT NULL,
  `d13_defaultValue` varchar(64) DEFAULT NULL,
  `d14_defaultValue` varchar(64) DEFAULT NULL,
  `d15_defaultValue` varchar(64) DEFAULT NULL,
  `d16_defaultValue` varchar(64) DEFAULT NULL,
  `d17_defaultValue` varchar(64) DEFAULT NULL,
  `d18_defaultValue` varchar(64) DEFAULT NULL,
  `d19_defaultValue` varchar(64) DEFAULT NULL,
  `d20_defaultValue` varchar(64) DEFAULT NULL,
  `d21_defaultValue` varchar(64) DEFAULT NULL,
  `d22_defaultValue` varchar(64) DEFAULT NULL,
  `d23_defaultValue` varchar(64) DEFAULT NULL,
  `d24_defaultValue` varchar(64) DEFAULT NULL,
  `d25_defaultValue` varchar(64) DEFAULT NULL,
  `d26_defaultValue` varchar(64) DEFAULT NULL,
  `d27_defaultValue` varchar(64) DEFAULT NULL,
  `d28_defaultValue` varchar(64) DEFAULT NULL,
  `d29_defaultValue` varchar(64) DEFAULT NULL,
  `d30_defaultValue` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `multipleMeasurements` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`_expTest_key`),
  UNIQUE KEY `_expTest_key` (`_expTest_key`),
  UNIQUE KEY `testID` (`testID`),
  KEY `_expDataDescriptor_key` (`_expDataDescriptor_key`),
  KEY `{1BD728BD-49BE-4B35-95A3-151A984C2A12}` (`_expPlan_key`),
  KEY `{F100C89A-0338-44A2-923A-38F9965ACC8A}` (`_expDataDescriptor_key`),
  KEY `numMice` (`numMice`),
  KEY `planID` (`_expPlan_key`),
  KEY `testStatus` (`testStatus`),
  CONSTRAINT `exptest_ibfk_2` FOREIGN KEY (`_expDataDescriptor_key`) REFERENCES `ExpDataDescriptor` (`_expDataDescriptor_key`),
  CONSTRAINT `exptest_ibfk_1` FOREIGN KEY (`_expPlan_key`) REFERENCES `ExpPlan` (`_expPlan_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpTest`
--

LOCK TABLES `ExpTest` WRITE;
/*!40000 ALTER TABLE `ExpTest` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpTest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpTestPlanMap`
--

DROP TABLE IF EXISTS `ExpTestPlanMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpTestPlanMap` (
  `_expTest_key` int(11) NOT NULL,
  `_expPlanMouseMap_key` int(11) NOT NULL,
  `_exptestplan_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_exptestplan_key`),
  UNIQUE KEY `MiceInExpTest` (`_expTest_key`,`_expPlanMouseMap_key`),
  KEY `_expPlanMouseMap_key` (`_expPlanMouseMap_key`),
  KEY `_expTest_key` (`_expTest_key`),
  KEY `ExpPlanMouseMapExpTestPlanMap` (`_expPlanMouseMap_key`),
  KEY `ExpTestExpTestPlanMap` (`_expTest_key`),
  CONSTRAINT `exptestplanmap_ibfk_2` FOREIGN KEY (`_expTest_key`) REFERENCES `ExpTest` (`_expTest_key`),
  CONSTRAINT `exptestplanmap_ibfk_1` FOREIGN KEY (`_expPlanMouseMap_key`) REFERENCES `ExpPlanMouseMap` (`_expPlanMouseMap_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpTestPlanMap`
--

LOCK TABLES `ExpTestPlanMap` WRITE;
/*!40000 ALTER TABLE `ExpTestPlanMap` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpTestPlanMap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gene`
--

DROP TABLE IF EXISTS `Gene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Gene` (
  `_gene_key` int(11) NOT NULL AUTO_INCREMENT,
  `geneSymbol` varchar(32) DEFAULT NULL,
  `labSymbol` varchar(32) NOT NULL,
  `geneClass` varchar(16) DEFAULT NULL,
  `chromosome` varchar(2) DEFAULT NULL,
  `cM` double DEFAULT NULL,
  `megabase` float DEFAULT '0',
  `comment` longtext,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_gene_key`),
  UNIQUE KEY `_gene_key` (`_gene_key`),
  UNIQUE KEY `labSymbol` (`labSymbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gene`
--

LOCK TABLES `Gene` WRITE;
/*!40000 ALTER TABLE `Gene` DISABLE KEYS */;
/*!40000 ALTER TABLE `Gene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Genotype`
--

DROP TABLE IF EXISTS `Genotype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Genotype` (
  `_genotype_key` int(11) NOT NULL DEFAULT '0',
  `_mouse_key` int(11) DEFAULT NULL,
  `_sample_key` int(11) DEFAULT NULL,
  `_gene_key` int(11) NOT NULL,
  `allele1` varchar(8) DEFAULT NULL,
  `all1Conf` tinyint(1) NOT NULL DEFAULT '-1',
  `allele2` varchar(8) DEFAULT NULL,
  `all2Conf` tinyint(1) NOT NULL DEFAULT '-1',
  `genoPage` varchar(16) NOT NULL,
  `sampleLocation` varchar(16) DEFAULT NULL,
  `gtDate` datetime DEFAULT NULL,
  `comment` longtext,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotype_key`),
  UNIQUE KEY `_genotype_key` (`_genotype_key`),
  KEY `{73B83876-BA68-47FE-B451-5E17BA3319D9}` (`_gene_key`),
  KEY `{A63318B2-02AC-447D-9603-AD964D09C53D}` (`_mouse_key`),
  KEY `allele1` (`allele1`),
  KEY `allele2` (`allele2`),
  KEY `ID#` (`_mouse_key`),
  KEY `Marker` (`_gene_key`),
  KEY `_sample_key` (`_sample_key`),
  KEY `genotype_ibfk_3` (`_sample_key`),
  CONSTRAINT `genotype_ibfk_2` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `genotype_ibfk_1` FOREIGN KEY (`_gene_key`) REFERENCES `Gene` (`_gene_key`),
  CONSTRAINT `genotype_ibfk_3` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Genotype`
--

LOCK TABLES `Genotype` WRITE;
/*!40000 ALTER TABLE `Genotype` DISABLE KEYS */;
/*!40000 ALTER TABLE `Genotype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GenotypingPlate`
--

DROP TABLE IF EXISTS `GenotypingPlate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GenotypingPlate` (
  `_genotypingPlate_key` int(11) NOT NULL AUTO_INCREMENT,
  `genotypingPlateID` varchar(20) NOT NULL,
  `_genotypingRequest_key` int(11) NOT NULL,
  `_genotypingPlateStatus_key` int(11) NOT NULL,
  `sequenceNumber` int(11) NOT NULL,
  `numRows` int(11) NOT NULL,
  `numCols` int(11) NOT NULL,
  `rowStartUnfillable` int(11) DEFAULT NULL,
  `colStartUnfillable` int(11) DEFAULT NULL,
  `rowEndUnfillable` int(11) DEFAULT NULL,
  `colEndUnfillable` int(11) DEFAULT NULL,
  `genotypingPlateIDChangedSinceLastSubmit` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotypingPlate_key`),
  UNIQUE KEY `Index_6EAD33D1_DEF9_4A6A` (`genotypingPlateID`),
  KEY `cv_GenotypingPlateStatusGenotypingPlate` (`_genotypingPlateStatus_key`),
  KEY `Rel_0C5FD264_7F8B_440F` (`_genotypingRequest_key`),
  CONSTRAINT `genotypingplate_ibfk_2` FOREIGN KEY (`_genotypingRequest_key`) REFERENCES `GenotypingRequest` (`_genotypingRequest_key`),
  CONSTRAINT `genotypingplate_ibfk_1` FOREIGN KEY (`_genotypingPlateStatus_key`) REFERENCES `cv_GenotypingPlateStatus` (`_genotypingPlateStatus_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GenotypingPlate`
--

LOCK TABLES `GenotypingPlate` WRITE;
/*!40000 ALTER TABLE `GenotypingPlate` DISABLE KEYS */;
/*!40000 ALTER TABLE `GenotypingPlate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GenotypingRequest`
--

DROP TABLE IF EXISTS `GenotypingRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GenotypingRequest` (
  `_genotypingRequest_key` int(11) NOT NULL AUTO_INCREMENT,
  `genotypingRequestID` varchar(32) NOT NULL,
  `dateLastSubmitted` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotypingRequest_key`),
  UNIQUE KEY `Index_7E49FA54_62D8_4B80` (`genotypingRequestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GenotypingRequest`
--

LOCK TABLES `GenotypingRequest` WRITE;
/*!40000 ALTER TABLE `GenotypingRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `GenotypingRequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GenotypingWell`
--

DROP TABLE IF EXISTS `GenotypingWell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GenotypingWell` (
  `_genotypingWell_key` int(11) NOT NULL AUTO_INCREMENT,
  `_genotypingPlate_key` int(11) NOT NULL,
  `_mouse_key` int(11) NOT NULL,
  `row` int(11) NOT NULL,
  `col` int(11) NOT NULL,
  `sampleNumber` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotypingWell_key`),
  KEY `Rel_1EDC46B3_76EA_4A29` (`_genotypingPlate_key`),
  KEY `Rel_EFE80893_4B49_410A` (`_mouse_key`),
  CONSTRAINT `genotypingwell_ibfk_2` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `genotypingwell_ibfk_1` FOREIGN KEY (`_genotypingPlate_key`) REFERENCES `GenotypingPlate` (`_genotypingPlate_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GenotypingWell`
--

LOCK TABLES `GenotypingWell` WRITE;
/*!40000 ALTER TABLE `GenotypingWell` DISABLE KEYS */;
/*!40000 ALTER TABLE `GenotypingWell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HealthLevelHistory`
--

DROP TABLE IF EXISTS `HealthLevelHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HealthLevelHistory` (
  `_healthLevelHistory_key` int(11) NOT NULL AUTO_INCREMENT,
  `_room_key` int(11) NOT NULL,
  `_healthLevel_key` int(11) NOT NULL,
  `startDate` datetime NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `createdBy` varchar(45) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `dateModified` datetime DEFAULT NULL,
  PRIMARY KEY (`_healthLevelHistory_key`),
  KEY `_healthLevel_key` (`_healthLevel_key`),
  KEY `_healthLevelHistory_key` (`_healthLevelHistory_key`),
  KEY `_room_key` (`_room_key`),
  KEY `cv_HealthLevelHealthLevelHistory` (`_healthLevel_key`),
  KEY `RoomHealthLevelHistory` (`_room_key`),
  KEY `startDate` (`startDate`),
  CONSTRAINT `healthlevelhistory_ibfk_1` FOREIGN KEY (`_healthLevel_key`) REFERENCES `cv_HealthLevel` (`_healthLevel_key`),
  CONSTRAINT `healthlevelhistory_ibfk_2` FOREIGN KEY (`_room_key`) REFERENCES `Room` (`_room_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HealthLevelHistory`
--

LOCK TABLES `HealthLevelHistory` WRITE;
/*!40000 ALTER TABLE `HealthLevelHistory` DISABLE KEYS */;
INSERT INTO `HealthLevelHistory` VALUES (1,1,2,'1990-01-01 00:00:00',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `HealthLevelHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LifeStatus`
--

DROP TABLE IF EXISTS `LifeStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LifeStatus` (
  `lifeStatus` varchar(2) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  `exitStatus` tinyint(1) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `_lifeStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`_lifeStatus_key`),
  UNIQUE KEY `lifeStatus` (`lifeStatus`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LifeStatus`
--

LOCK TABLES `LifeStatus` WRITE;
/*!40000 ALTER TABLE `LifeStatus` DISABLE KEYS */;
INSERT INTO `LifeStatus` VALUES ('A','Alive',0,1,1),('D','Dead',-1,1,2),('E','Euthanized',-1,1,3),('K','Killed',-1,1,4),('M','Missing',-1,1,5),('S','Shipped',-1,1,6);
/*!40000 ALTER TABLE `LifeStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Litter`
--

DROP TABLE IF EXISTS `Litter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Litter` (
  `_litter_key` int(11) NOT NULL,
  `_mating_key` int(11) NOT NULL,
  `_theilerStage_key` int(11) DEFAULT NULL,
  `litterID` varchar(16) NOT NULL,
  `totalBorn` smallint(6) NOT NULL DEFAULT '0',
  `birthDate` datetime DEFAULT NULL,
  `numFemale` smallint(6) DEFAULT NULL,
  `numMale` smallint(6) DEFAULT NULL,
  `weanDate` datetime DEFAULT NULL,
  `tagDate` datetime DEFAULT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'A',
  `comment` longtext,
  `version` int(11) NOT NULL DEFAULT '1',
  `_litterType_key` int(11) NOT NULL,
  `harvestDate` date DEFAULT NULL,
  `numberHarvested` int(11) DEFAULT NULL,
  PRIMARY KEY (`_litter_key`),
  UNIQUE KEY `cross#` (`_litter_key`),
  UNIQUE KEY `litterID` (`litterID`),
  KEY `{E03DA0F7-AFFB-4271-9823-C475C6A9D205}` (`_mating_key`),
  KEY `matingLink` (`_mating_key`),
  KEY `numFemale` (`numFemale`),
  KEY `numMale` (`numMale`),
  KEY `REF11184` (`_litterType_key`),
  KEY `litter_ibfk_2` (`_theilerStage_key`),
  CONSTRAINT `litter_ibfk_2` FOREIGN KEY (`_theilerStage_key`) REFERENCES `cv_TheilerStage` (`_theilerStage_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `litter_ibfk_1` FOREIGN KEY (`_mating_key`) REFERENCES `Mating` (`_mating_key`),
  CONSTRAINT `Refcv_LitterType84` FOREIGN KEY (`_litterType_key`) REFERENCES `cv_LitterType` (`_litterType_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Litter`
--

LOCK TABLES `Litter` WRITE;
/*!40000 ALTER TABLE `Litter` DISABLE KEYS */;
/*!40000 ALTER TABLE `Litter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LitterSample`
--

DROP TABLE IF EXISTS `LitterSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LitterSample` (
  `_litterSample_key` int(11) NOT NULL,
  `_litter_key` int(11) NOT NULL,
  `_sample_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_litterSample_key`),
  KEY `_litter_key` (`_litter_key`),
  KEY `_litterSample_key` (`_litterSample_key`),
  KEY `LitterLitterSample` (`_litter_key`),
  KEY `LitterSample_sample_key` (`_sample_key`),
  CONSTRAINT `littersample_ibfk_2` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`),
  CONSTRAINT `littersample_ibfk_1` FOREIGN KEY (`_litter_key`) REFERENCES `Litter` (`_litter_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LitterSample`
--

LOCK TABLES `LitterSample` WRITE;
/*!40000 ALTER TABLE `LitterSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `LitterSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `_location_key` int(11) NOT NULL DEFAULT '0',
  `_storage_key` int(11) NOT NULL DEFAULT '0',
  `_locationType_key` int(11) NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_location_key`),
  UNIQUE KEY `_location_key` (`_location_key`),
  KEY `cv_LocationTypeLocation` (`_locationType_key`),
  KEY `Location_locationDetail_key` (`_locationType_key`),
  KEY `Location_storage_key` (`_storage_key`),
  KEY `StorageLocation` (`_storage_key`),
  CONSTRAINT `location_ibfk_2` FOREIGN KEY (`_storage_key`) REFERENCES `Storage` (`_storage_key`),
  CONSTRAINT `location_ibfk_1` FOREIGN KEY (`_locationType_key`) REFERENCES `cv_LocationType` (`_locationType_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mating`
--

DROP TABLE IF EXISTS `Mating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mating` (
  `_mating_key` int(11) NOT NULL,
  `_matingType_key` int(11) DEFAULT NULL,
  `_dam1_key` int(11) NOT NULL,
  `_dam2_key` int(11) DEFAULT NULL,
  `_sire_key` int(11) NOT NULL,
  `_strain_key` int(11) NOT NULL,
  `matingID` int(11) NOT NULL,
  `suggestedPenID` int(11) DEFAULT NULL,
  `weanTime` tinyint(1) NOT NULL DEFAULT '-1',
  `matingDate` datetime DEFAULT NULL,
  `retiredDate` datetime DEFAULT NULL,
  `generation` varchar(16) NOT NULL,
  `owner` varchar(8) NOT NULL,
  `weanNote` varchar(64) DEFAULT NULL,
  `needsTyping` tinyint(1) NOT NULL DEFAULT '-1',
  `comment` varchar(255) DEFAULT NULL,
  `proposedDiet` varchar(32) DEFAULT NULL,
  `proposedRetireDate` datetime DEFAULT NULL,
  `proposedRetireD1Diet` varchar(32) DEFAULT NULL,
  `proposedRetireD2Diet` varchar(32) DEFAULT NULL,
  `proposedRetireSDiet` varchar(32) DEFAULT NULL,
  `proposedRetireD1BrStatus` varchar(1) DEFAULT NULL,
  `proposedRetireD2BrStatus` varchar(1) DEFAULT NULL,
  `proposedRetireSBrStatus` varchar(1) DEFAULT NULL,
  `proposedRetireD1LfStatus` varchar(2) DEFAULT NULL,
  `proposedRetireD2LfStatus` varchar(2) DEFAULT NULL,
  `proposedRetireSLfStatus` varchar(2) DEFAULT NULL,
  `proposedRetirePenStatus` varchar(1) DEFAULT NULL,
  `suggestedFirstLitterNum` int(11) DEFAULT '0',
  `_crossStatus_key` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_mating_key`),
  UNIQUE KEY `matingID` (`matingID`),
  KEY `_crossStatus_key` (`_crossStatus_key`),
  KEY `{069800AF-BBC3-474A-AF69-31C8CFA0754F}` (`_strain_key`),
  KEY `cv_CrossStatusMating` (`_crossStatus_key`),
  KEY `dam1` (`_dam1_key`),
  KEY `dam2` (`_dam2_key`),
  KEY `gen` (`generation`),
  KEY `owner` (`owner`),
  KEY `sire` (`_sire_key`),
  KEY `strain` (`_strain_key`),
  KEY `suggestedFirstLitterNum` (`suggestedFirstLitterNum`),
  KEY `suggestedPenID` (`suggestedPenID`),
  KEY `Ref11083` (`_matingType_key`),
  CONSTRAINT `Refcv_MatingType83` FOREIGN KEY (`_matingType_key`) REFERENCES `cv_MatingType` (`_matingType_key`),
  CONSTRAINT `mating_ibfk_1` FOREIGN KEY (`_strain_key`) REFERENCES `Strain` (`_strain_key`),
  CONSTRAINT `mating_ibfk_2` FOREIGN KEY (`_sire_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `mating_ibfk_3` FOREIGN KEY (`_crossStatus_key`) REFERENCES `cv_CrossStatus` (`_crossStatus_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mating`
--

LOCK TABLES `Mating` WRITE;
/*!40000 ALTER TABLE `Mating` DISABLE KEYS */;
/*!40000 ALTER TABLE `Mating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MatingSample`
--

DROP TABLE IF EXISTS `MatingSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MatingSample` (
  `_matingSample_key` int(11) NOT NULL,
  `_sample_key` int(11) NOT NULL,
  `_mating_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_matingSample_key`),
  KEY `_mating_key` (`_mating_key`),
  KEY `_matingSample_key` (`_matingSample_key`),
  KEY `MatingMatingSample` (`_mating_key`),
  KEY `MatingSample_sample_key` (`_sample_key`),
  CONSTRAINT `matingsample_ibfk_2` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`),
  CONSTRAINT `matingsample_ibfk_1` FOREIGN KEY (`_mating_key`) REFERENCES `Mating` (`_mating_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MatingSample`
--

LOCK TABLES `MatingSample` WRITE;
/*!40000 ALTER TABLE `MatingSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `MatingSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MatingUnitLink`
--

DROP TABLE IF EXISTS `MatingUnitLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MatingUnitLink` (
  `_matingUnitLink_key` int(11) NOT NULL AUTO_INCREMENT,
  `_mating_key` int(11) DEFAULT NULL,
  `_mouse_key` int(11) DEFAULT NULL,
  `_sample_key` int(11) DEFAULT NULL,
  `_matingUnitType_key` int(11) NOT NULL,
  PRIMARY KEY (`_matingUnitLink_key`),
  KEY `Ref10976` (`_matingUnitType_key`),
  KEY `Ref5079` (`_mating_key`),
  KEY `Ref5280` (`_mouse_key`),
  KEY `Ref5781` (`_sample_key`),
  CONSTRAINT `Refsample81` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`),
  CONSTRAINT `Refcv_MatingUnitType76` FOREIGN KEY (`_matingUnitType_key`) REFERENCES `cv_MatingUnitType` (`_matingUnitType_key`),
  CONSTRAINT `Refmating79` FOREIGN KEY (`_mating_key`) REFERENCES `Mating` (`_mating_key`),
  CONSTRAINT `Refmouse80` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MatingUnitLink`
--

LOCK TABLES `MatingUnitLink` WRITE;
/*!40000 ALTER TABLE `MatingUnitLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `MatingUnitLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mouse`
--

DROP TABLE IF EXISTS `Mouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mouse` (
  `_mouse_key` int(11) NOT NULL,
  `_litter_key` int(11) DEFAULT NULL,
  `_strain_key` int(11) NOT NULL,
  `_pen_key` int(11) NOT NULL,
  `ID` varchar(16) NOT NULL,
  `newTag` varchar(16) DEFAULT NULL,
  `birthDate` datetime NOT NULL,
  `exitDate` datetime DEFAULT NULL,
  `cod` varchar(32) DEFAULT NULL,
  `codNotes` varchar(255) DEFAULT NULL,
  `generation` varchar(16) NOT NULL,
  `sex` varchar(1) NOT NULL DEFAULT '-',
  `lifeStatus` varchar(2) NOT NULL DEFAULT 'A',
  `breedingStatus` varchar(1) NOT NULL DEFAULT 'V',
  `coatColor` varchar(8) DEFAULT NULL,
  `diet` varchar(32) DEFAULT NULL,
  `owner` varchar(8) NOT NULL,
  `origin` varchar(32) NOT NULL,
  `protocol` varchar(32) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `sampleVialID` varchar(32) DEFAULT NULL,
  `sampleVialTagPosition` varchar(32) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_mouse_key`),
  UNIQUE KEY `_mouse_key` (`_mouse_key`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `newTag` (`newTag`),
  KEY `{467270A5-EBD6-4219-B104-7D65BFEBED51}` (`_litter_key`),
  KEY `{D71AC749-0B32-4A7A-8D62-E3E419545838}` (`_strain_key`),
  KEY `ContainerMouse` (`_pen_key`),
  KEY `Generation` (`generation`),
  KEY `lifeStatus` (`lifeStatus`),
  KEY `litter` (`_litter_key`),
  KEY `owner` (`owner`),
  KEY `Pen#` (`_pen_key`),
  KEY `StrainKey` (`_strain_key`),
  CONSTRAINT `mouse_ibfk_3` FOREIGN KEY (`_pen_key`) REFERENCES `Container` (`_container_key`),
  CONSTRAINT `mouse_ibfk_1` FOREIGN KEY (`_litter_key`) REFERENCES `Litter` (`_litter_key`),
  CONSTRAINT `mouse_ibfk_2` FOREIGN KEY (`_strain_key`) REFERENCES `Strain` (`_strain_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mouse`
--

LOCK TABLES `Mouse` WRITE;
/*!40000 ALTER TABLE `Mouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `Mouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MouseSample`
--

DROP TABLE IF EXISTS `MouseSample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MouseSample` (
  `_mouseSample_key` int(11) NOT NULL,
  `_mouse_key` int(11) NOT NULL,
  `_sample_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_mouseSample_key`),
  KEY `_mouse_key` (`_mouse_key`),
  KEY `_mouseSample_key` (`_mouseSample_key`),
  KEY `MouseMouseSample` (`_mouse_key`),
  KEY `MouseSample_sample_key` (`_sample_key`),
  CONSTRAINT `mousesample_ibfk_2` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`),
  CONSTRAINT `mousesample_ibfk_1` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MouseSample`
--

LOCK TABLES `MouseSample` WRITE;
/*!40000 ALTER TABLE `MouseSample` DISABLE KEYS */;
/*!40000 ALTER TABLE `MouseSample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MouseUsage`
--

DROP TABLE IF EXISTS `MouseUsage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MouseUsage` (
  `_usage_key` int(11) NOT NULL DEFAULT '0',
  `_mouse_key` int(11) NOT NULL,
  `_plugDate_key` int(11) DEFAULT NULL,
  `use` varchar(32) NOT NULL,
  `useAge` double DEFAULT NULL,
  `projectedDate` datetime DEFAULT NULL,
  `actualDate` datetime DEFAULT NULL,
  `done` tinyint(1) NOT NULL DEFAULT '0',
  `comment` varchar(255) DEFAULT NULL,
  `D1` varchar(128) DEFAULT NULL,
  `D2` varchar(128) DEFAULT NULL,
  `D3` varchar(128) DEFAULT NULL,
  `D4` varchar(128) DEFAULT NULL,
  `D5` varchar(128) DEFAULT NULL,
  `D6` varchar(128) DEFAULT NULL,
  `D7` varchar(128) DEFAULT NULL,
  `D8` varchar(128) DEFAULT NULL,
  `D9` varchar(128) DEFAULT NULL,
  `D10` varchar(128) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_usage_key`),
  UNIQUE KEY `_usage_key` (`_usage_key`),
  KEY `_mouse_key` (`_mouse_key`),
  KEY `{8C69793D-CB9B-481F-88F2-AA970EA090D8}` (`_mouse_key`),
  KEY `useCode` (`use`),
  KEY `mouseusage_ibfk_2` (`_plugDate_key`),
  CONSTRAINT `mouseusage_ibfk_2` FOREIGN KEY (`_plugDate_key`) REFERENCES `PlugDate` (`_plugDate_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mouseusage_ibfk_1` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MouseUsage`
--

LOCK TABLES `MouseUsage` WRITE;
/*!40000 ALTER TABLE `MouseUsage` DISABLE KEYS */;
/*!40000 ALTER TABLE `MouseUsage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Owner`
--

DROP TABLE IF EXISTS `Owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Owner` (
  `_owner_key` int(11) NOT NULL DEFAULT '0',
  `owner` varchar(75) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_owner_key`),
  UNIQUE KEY `_owner_key` (`_owner_key`),
  UNIQUE KEY `owner` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Owner`
--

LOCK TABLES `Owner` WRITE;
/*!40000 ALTER TABLE `Owner` DISABLE KEYS */;
INSERT INTO `Owner` VALUES (1,'nobody',1);
/*!40000 ALTER TABLE `Owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PenGroup`
--

DROP TABLE IF EXISTS `PenGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PenGroup` (
  `_pen_key` int(11) NOT NULL,
  `penID` int(11) NOT NULL,
  `room` varchar(8) DEFAULT NULL,
  `penStatus` tinyint(1) NOT NULL DEFAULT '-1',
  `beginDate` datetime NOT NULL,
  `healthLevel` smallint(6) NOT NULL,
  PRIMARY KEY (`_pen_key`),
  UNIQUE KEY `_pen_key` (`_pen_key`),
  UNIQUE KEY `Pen#` (`penID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PenGroup`
--

LOCK TABLES `PenGroup` WRITE;
/*!40000 ALTER TABLE `PenGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `PenGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlugDate`
--

DROP TABLE IF EXISTS `PlugDate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlugDate` (
  `_plugDate_key` int(11) NOT NULL,
  `_mating_key` int(11) NOT NULL,
  `_mouse_key` int(11) NOT NULL,
  `plugDate` datetime NOT NULL,
  `obsolete` tinyint(1) DEFAULT '0',
  `comment` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_plugDate_key`),
  UNIQUE KEY `plugDate_key` (`_plugDate_key`),
  KEY `_mating_key` (`_mating_key`),
  KEY `_mouse_key` (`_mouse_key`),
  KEY `MatingPlugDate` (`_mating_key`),
  KEY `MousePlugDate` (`_mouse_key`),
  CONSTRAINT `plugdate_ibfk_2` FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse` (`_mouse_key`),
  CONSTRAINT `plugdate_ibfk_1` FOREIGN KEY (`_mating_key`) REFERENCES `Mating` (`_mating_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlugDate`
--

LOCK TABLES `PlugDate` WRITE;
/*!40000 ALTER TABLE `PlugDate` DISABLE KEYS */;
/*!40000 ALTER TABLE `PlugDate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
  `_room_key` int(11) NOT NULL AUTO_INCREMENT,
  `roomName` varchar(8) NOT NULL,
  `_healthLevelHistory_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `createdBy` varchar(45) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `dateModified` datetime DEFAULT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`_room_key`),
  UNIQUE KEY `_currentHealthLevelHistory_key` (`_healthLevelHistory_key`),
  UNIQUE KEY `_room_key` (`_room_key`),
  UNIQUE KEY `roomName` (`roomName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES (1,'unknown',1,1,NULL,NULL,NULL,NULL,-1);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SNPLoader`
--

DROP TABLE IF EXISTS `SNPLoader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SNPLoader` (
  `_snp_loader_key` int(11) NOT NULL DEFAULT '0',
  `status_Description` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '-1',
  `sequence` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_snp_loader_key`),
  KEY `_snp_loader_key` (`_snp_loader_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SNPLoader`
--

LOCK TABLES `SNPLoader` WRITE;
/*!40000 ALTER TABLE `SNPLoader` DISABLE KEYS */;
INSERT INTO `SNPLoader` VALUES (1,'Input file 06-05.csv is well-formed',-1,1,1),(2,'The file 06-05.csv is a valid JAX-CMS formatted genotype importation file',-1,1,1),(3,'Added 0 geneclass records',-1,2,1),(4,'Added 0 gene records',-1,3,1);
/*!40000 ALTER TABLE `SNPLoader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sample`
--

DROP TABLE IF EXISTS `Sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sample` (
  `_sample_key` int(11) NOT NULL,
  `_parentSample_key` int(11) NOT NULL,
  `age` float NOT NULL,
  `_timeUnit_key` int(11) NOT NULL,
  `_epoch_key` int(11) NOT NULL,
  `harvestMethod` varchar(32) DEFAULT NULL,
  `description` longtext NOT NULL,
  `weight` float NOT NULL,
  `_weightUnit_key` int(11) NOT NULL,
  `_sampleType_key` int(11) NOT NULL,
  `sampleDate` datetime NOT NULL,
  `_sampleDateType_key` int(11) NOT NULL DEFAULT '0',
  `_strain_key` int(11) NOT NULL,
  `owner` varchar(8) NOT NULL,
  `SampleID` varchar(32) NOT NULL,
  `SourceType` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sample_key`),
  UNIQUE KEY `Sample_sample_key` (`_sample_key`),
  UNIQUE KEY `SampleID` (`SampleID`),
  KEY `_parentSample_key` (`_parentSample_key`),
  KEY `_strain_key` (`_strain_key`),
  KEY `cv_EpochSample` (`_epoch_key`),
  KEY `cv_SampleDateTypeSample` (`_sampleDateType_key`),
  KEY `cv_SampleTypeSample` (`_sampleType_key`),
  KEY `cv_TimeUnitSample` (`_timeUnit_key`),
  KEY `cv_WeightUnitSample` (`_weightUnit_key`),
  KEY `Sample_epoch_key` (`_epoch_key`),
  KEY `Sample_harvestMethod_key` (`harvestMethod`),
  KEY `Sample_sampleDateType_key` (`_sampleDateType_key`),
  KEY `Sample_sampleType_key` (`_sampleType_key`),
  KEY `Sample_timeUnit_key` (`_timeUnit_key`),
  KEY `Sample_weightUnit_key` (`_weightUnit_key`),
  CONSTRAINT `sample_ibfk_5` FOREIGN KEY (`_weightUnit_key`) REFERENCES `cv_WeightUnit` (`_weightUnit_key`),
  CONSTRAINT `sample_ibfk_1` FOREIGN KEY (`_epoch_key`) REFERENCES `cv_Epoch` (`_epoch_key`),
  CONSTRAINT `sample_ibfk_2` FOREIGN KEY (`_sampleDateType_key`) REFERENCES `cv_SampleDateType` (`_sampleDateType_key`),
  CONSTRAINT `sample_ibfk_3` FOREIGN KEY (`_sampleType_key`) REFERENCES `cv_SampleType` (`_sampleType_key`),
  CONSTRAINT `sample_ibfk_4` FOREIGN KEY (`_timeUnit_key`) REFERENCES `cv_TimeUnit` (`_timeUnit_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sample`
--

LOCK TABLES `Sample` WRITE;
/*!40000 ALTER TABLE `Sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Secretary`
--

DROP TABLE IF EXISTS `Secretary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Secretary` (
  `_secretary_key` int(11) NOT NULL DEFAULT '0',
  `_owner_key` int(11) NOT NULL DEFAULT '0',
  `secretary` varchar(75) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_secretary_key`),
  UNIQUE KEY `_secretary_key` (`_secretary_key`),
  KEY `_owner_key` (`_owner_key`),
  KEY `{10F06FC7-4462-481A-8E77-9DDA2FD373AD}` (`_owner_key`),
  KEY `secretary` (`secretary`),
  CONSTRAINT `secretary_ibfk_1` FOREIGN KEY (`_owner_key`) REFERENCES `Owner` (`_owner_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Secretary`
--

LOCK TABLES `Secretary` WRITE;
/*!40000 ALTER TABLE `Secretary` DISABLE KEYS */;
/*!40000 ALTER TABLE `Secretary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Storage`
--

DROP TABLE IF EXISTS `Storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Storage` (
  `_storage_key` int(11) NOT NULL DEFAULT '0',
  `_sample_key` int(11) NOT NULL,
  `_sampleStatus_key` int(11) NOT NULL DEFAULT '0',
  `_preservationMethod_key` int(11) DEFAULT '0',
  `_preservationType_key` int(11) DEFAULT '0',
  `_preservationDetail_key` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_storage_key`),
  UNIQUE KEY `Storage_storage_key` (`_storage_key`),
  KEY `_preservationDetail_key` (`_preservationDetail_key`),
  KEY `_preservationMethod_key` (`_preservationMethod_key`),
  KEY `_preservationType_key` (`_preservationType_key`),
  KEY `cv_SampleStatusStorage` (`_sampleStatus_key`),
  KEY `SampleStorage` (`_sample_key`),
  KEY `Storage_sample_key` (`_sample_key`),
  KEY `Storage_sampleStatus_key` (`_sampleStatus_key`),
  CONSTRAINT `storage_ibfk_2` FOREIGN KEY (`_sample_key`) REFERENCES `Sample` (`_sample_key`),
  CONSTRAINT `storage_ibfk_1` FOREIGN KEY (`_sampleStatus_key`) REFERENCES `cv_SampleStatus` (`_sampleStatus_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Storage`
--

LOCK TABLES `Storage` WRITE;
/*!40000 ALTER TABLE `Storage` DISABLE KEYS */;
/*!40000 ALTER TABLE `Storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Strain`
--

DROP TABLE IF EXISTS `Strain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Strain` (
  `_strain_key` int(11) NOT NULL AUTO_INCREMENT,
  `strainName` varchar(64) NOT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `formalName` varchar(128) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT '-1',
  `strainStatus` varchar(1) NOT NULL DEFAULT 'A',
  `tagMin` varchar(16) DEFAULT NULL,
  `tagMax` varchar(16) DEFAULT NULL,
  `lastTag` varchar(16) DEFAULT NULL,
  `jrNum` int(11) DEFAULT '0',
  `feNumEmbryos` smallint(6) NOT NULL DEFAULT '0',
  `feMaxGen` varchar(16) DEFAULT NULL,
  `fsNumMales` smallint(6) NOT NULL DEFAULT '0',
  `fsMaxGen` varchar(16) DEFAULT NULL,
  `foNumFemales` smallint(6) NOT NULL DEFAULT '0',
  `foMaxGen` varchar(16) DEFAULT NULL,
  `cardColor` varchar(32) DEFAULT NULL,
  `strainType` varchar(32) DEFAULT NULL,
  `comment` longtext,
  `lineViabilityYellowMinNumMales` int(11) DEFAULT NULL,
  `lineViabilityYellowMinNumFemales` int(11) DEFAULT NULL,
  `lineViabilityYellowMaxAgeMales` int(11) DEFAULT NULL,
  `lineViabilityYellowMaxAgeFemales` int(11) DEFAULT NULL,
  `lineViabilityRedMinNumMales` int(11) DEFAULT NULL,
  `lineViabilityRedMinNumFemales` int(11) DEFAULT NULL,
  `lineViabilityRedMaxAgeMales` int(11) DEFAULT NULL,
  `lineViabilityRedMaxAgeFemales` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `section_` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`_strain_key`),
  UNIQUE KEY `StrainName` (`strainName`),
  KEY `isActive` (`isActive`),
  KEY `jrNum` (`jrNum`),
  KEY `StrainKey` (`_strain_key`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Strain`
--

LOCK TABLES `Strain` WRITE;
/*!40000 ALTER TABLE `Strain` DISABLE KEYS */;
INSERT INTO `Strain` VALUES (1,'B6D2F1/J','B6D2','B6D2F1/J',-1,'A',NULL,NULL,NULL,100006,0,NULL,0,NULL,0,NULL,NULL,'F1 hybrid',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(2,'B6.129P2-Apoe<tmlUnc>/J',NULL,'B6.129P2-Apoe<tmlUnc>/J',-1,'A',NULL,NULL,NULL,2052,0,NULL,0,NULL,0,NULL,NULL,'congenic',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(3,'BALB/cJ','C','BALB/cJ',-1,'A',NULL,NULL,NULL,651,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(4,'BALB/cByJ','CBy; BALB Bailey; BALB/c Bailey J','BALB/cByJ',-1,'A',NULL,NULL,NULL,1026,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(5,'C3H/HeJ','C3; C3H Heston','C3H/HeJ',-1,'A',NULL,NULL,NULL,659,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(6,'C57BL/6J','C57 Black; B6; B6J; Black 6','C57BL/6J',-1,'A',NULL,NULL,NULL,664,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(7,'CBA/J','CBA Jackson','CBA/J',-1,'A',NULL,NULL,NULL,656,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(8,'DBA/2J','D2; D2J','DBA/2J',-1,'A',NULL,NULL,NULL,671,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(9,'FVB/NJ','FVB; Friend Virus B NIH Jackson','FVB/NJ',-1,'A',NULL,NULL,NULL,1800,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(10,'NOD/ShiLtJ','NOD/LtJ; Non-obese Diabetic','NOD/ShiLtJ',-1,'A',NULL,NULL,NULL,1976,0,NULL,0,NULL,0,NULL,NULL,'inbred',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(11,'NOD.CB17-Prkdc<scid>/J','NOD SCID','NOD.CB17-Prkdc<scid>/J',-1,'A',NULL,NULL,NULL,1303,0,NULL,0,NULL,0,NULL,NULL,'congenic',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `Strain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UpgradeHistoryLog`
--

DROP TABLE IF EXISTS `UpgradeHistoryLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UpgradeHistoryLog` (
  `_upgradeHistoryLog_key` int(11) NOT NULL AUTO_INCREMENT,
  `majorVersion` int(11) NOT NULL,
  `minorVersion` int(11) NOT NULL,
  `bugFixVersion` int(11) NOT NULL,
  `upgradeDate` datetime NOT NULL,
  `actionDescription` varchar(64) DEFAULT NULL,
  `actionCompleted` tinyint(1) DEFAULT '0',
  `actionDate` datetime DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_upgradeHistoryLog_key`),
  UNIQUE KEY `_upgradeHistoryLog_key` (`_upgradeHistoryLog_key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UpgradeHistoryLog`
--

LOCK TABLES `UpgradeHistoryLog` WRITE;
/*!40000 ALTER TABLE `UpgradeHistoryLog` DISABLE KEYS */;
INSERT INTO `UpgradeHistoryLog` VALUES (1,4,6,0,'2012-11-30 00:00:00','Logging began',-1,'2012-11-30 00:00:00',NULL,1),(2,4,8,3,'2013-06-10 00:00:00','None',-1,NULL,'2013-06-10',1),(3,4,8,4,'2013-06-10 00:00:00','None',-1,NULL,'2013-06-10',1);
/*!40000 ALTER TABLE `UpgradeHistoryLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_BirthEventStatus`
--

DROP TABLE IF EXISTS `cv_BirthEventStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_BirthEventStatus` (
  `_birthEventStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `birthEventStatus` varchar(75) NOT NULL,
  `abbreviation` varchar(2) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isDefault` tinyint(1) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_birthEventStatus_key`),
  UNIQUE KEY `cvBirthEventStatus` (`birthEventStatus`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_BirthEventStatus`
--

LOCK TABLES `cv_BirthEventStatus` WRITE;
/*!40000 ALTER TABLE `cv_BirthEventStatus` DISABLE KEYS */;
INSERT INTO `cv_BirthEventStatus` VALUES (1,'Alive','A',-1,-1,NULL,NULL,NULL,0,1),(2,'Born dead','B',-1,0,NULL,NULL,NULL,0,1),(3,'Dead at weaning','D',-1,0,NULL,NULL,NULL,0,1),(4,'Harvested','H',-1,0,NULL,NULL,NULL,0,1),(5,'Killed','K',-1,0,NULL,NULL,NULL,0,1),(6,'Missing','M',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_BirthEventStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_BreedingStatus`
--

DROP TABLE IF EXISTS `cv_BreedingStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_BreedingStatus` (
  `_breedingStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `breedingStatus` varchar(75) NOT NULL,
  `abbreviation` varchar(2) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isDefault` tinyint(1) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_breedingStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_BreedingStatus`
--

LOCK TABLES `cv_BreedingStatus` WRITE;
/*!40000 ALTER TABLE `cv_BreedingStatus` DISABLE KEYS */;
INSERT INTO `cv_BreedingStatus` VALUES (1,'Breeding','B',-1,0,NULL,NULL,NULL,0,1),(2,'Retired breeder','R',-1,0,NULL,NULL,NULL,0,1),(3,'Virgin','V',-1,-1,NULL,NULL,NULL,0,1),(4,'Unknown','U',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_BreedingStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CauseOfDeath`
--

DROP TABLE IF EXISTS `cv_CauseOfDeath`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CauseOfDeath` (
  `cod` varchar(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `_causeOfDeath_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_causeOfDeath_key`),
  UNIQUE KEY `cod` (`cod`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CauseOfDeath`
--

LOCK TABLES `cv_CauseOfDeath` WRITE;
/*!40000 ALTER TABLE `cv_CauseOfDeath` DISABLE KEYS */;
INSERT INTO `cv_CauseOfDeath` VALUES ('Euthanasia',NULL,1,1);
/*!40000 ALTER TABLE `cv_CauseOfDeath` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CoatColor`
--

DROP TABLE IF EXISTS `cv_CoatColor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CoatColor` (
  `coatColor` varchar(8) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  `_coatColor_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_coatColor_key`),
  UNIQUE KEY `coatColor` (`coatColor`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CoatColor`
--

LOCK TABLES `cv_CoatColor` WRITE;
/*!40000 ALTER TABLE `cv_CoatColor` DISABLE KEYS */;
INSERT INTO `cv_CoatColor` VALUES ('Agouti','Agouti',1,1),('Albino','Albino',2,1),('Black','Black (non-agouti)',3,1),('BrAgouti','Brown agouti',4,1),('Brown','Brown',5,1),('Buff','Buff',6,1),('Chinchil','Light-bellied chinchilla',7,1),('DarkGrey','Dark grey',8,1),('DlBrown','Dilute brown',9,1),('DlBrPieb','Dilute brown, Piebald',10,1),('Fawn','Fawn',11,1),('GreyWhit','Greyish white',12,1),('Gunmetal','Gunmetal',13,1),('Lagouti','Light agouti',14,1),('Lchinchi','Light-bellied, Light chinchilla',15,1),('Leaden','Leaden (grey)',16,1),('Misty','Misty (grey)',17,1),('Piebald','Piebald, White-bellied agouti',18,1),('Sandy','Sandy',19,1),('Yellow','Yellow',20,1);
/*!40000 ALTER TABLE `cv_CoatColor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ContainerStatus`
--

DROP TABLE IF EXISTS `cv_ContainerStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ContainerStatus` (
  `_containerStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `containerStatus` varchar(8) NOT NULL,
  `billable` tinyint(1) DEFAULT '-1',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_containerStatus_key`),
  KEY `_containerStatus_key` (`_containerStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ContainerStatus`
--

LOCK TABLES `cv_ContainerStatus` WRITE;
/*!40000 ALTER TABLE `cv_ContainerStatus` DISABLE KEYS */;
INSERT INTO `cv_ContainerStatus` VALUES (1,'proposed',0,1),(2,'active',-1,1),(3,'retired',0,1);
/*!40000 ALTER TABLE `cv_ContainerStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ControlledVocabularyGroup`
--

DROP TABLE IF EXISTS `cv_ControlledVocabularyGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ControlledVocabularyGroup` (
  `_controlledVocabularyGroup_key` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `sortOrder` int(11) NOT NULL,
  PRIMARY KEY (`_controlledVocabularyGroup_key`),
  UNIQUE KEY `Index_2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ControlledVocabularyGroup`
--

LOCK TABLES `cv_ControlledVocabularyGroup` WRITE;
/*!40000 ALTER TABLE `cv_ControlledVocabularyGroup` DISABLE KEYS */;
INSERT INTO `cv_ControlledVocabularyGroup` VALUES (1,'Centers',1),(2,'Genotyping',2),(3,'Matings',3),(4,'Miscellaneous',4),(5,'Mice',5),(6,'Cages, Rooms and Cage Cards',6);
/*!40000 ALTER TABLE `cv_ControlledVocabularyGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_CrossStatus`
--

DROP TABLE IF EXISTS `cv_CrossStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_CrossStatus` (
  `_crossStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `crossStatus` varchar(75) NOT NULL,
  `abbreviation` varchar(2) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT '0',
  `isDefault` tinyint(1) DEFAULT '0',
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(1) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_crossStatus_key`),
  UNIQUE KEY `crossStatus` (`crossStatus`),
  KEY `_crossStatus_key` (`_crossStatus_key`),
  KEY `_vocabularySource_key` (`_vocabularySource_key`),
  KEY `abbreviation` (`abbreviation`),
  KEY `elementID` (`elementID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_CrossStatus`
--

LOCK TABLES `cv_CrossStatus` WRITE;
/*!40000 ALTER TABLE `cv_CrossStatus` DISABLE KEYS */;
INSERT INTO `cv_CrossStatus` VALUES (1,'designed','D',-1,0,NULL,NULL,NULL,0,1),(2,'active','A',-1,0,NULL,NULL,NULL,0,1),(3,'designed retirement','DR',-1,0,NULL,NULL,NULL,0,1),(8,'retired','R',-1,0,NULL,NULL,NULL,0,1),(9,'unknown','U',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_CrossStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Diet`
--

DROP TABLE IF EXISTS `cv_Diet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Diet` (
  `diet` varchar(32) NOT NULL,
  `dietDescription` varchar(64) DEFAULT NULL,
  `_diet_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_diet_key`),
  UNIQUE KEY `diet` (`diet`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Diet`
--

LOCK TABLES `cv_Diet` WRITE;
/*!40000 ALTER TABLE `cv_Diet` DISABLE KEYS */;
INSERT INTO `cv_Diet` VALUES ('4%',NULL,1,1),('6%',NULL,2,1);
/*!40000 ALTER TABLE `cv_Diet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Epoch`
--

DROP TABLE IF EXISTS `cv_Epoch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Epoch` (
  `_epoch_key` int(11) NOT NULL DEFAULT '0',
  `epoch` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_epoch_key`),
  UNIQUE KEY `cv_Epoch_epoch_key` (`_epoch_key`),
  UNIQUE KEY `epoch` (`epoch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Epoch`
--

LOCK TABLES `cv_Epoch` WRITE;
/*!40000 ALTER TABLE `cv_Epoch` DISABLE KEYS */;
INSERT INTO `cv_Epoch` VALUES (1,'From Birth',1),(2,'From Conception',1),(3,'From Harvest',1);
/*!40000 ALTER TABLE `cv_Epoch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_ExpStatus`
--

DROP TABLE IF EXISTS `cv_ExpStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_ExpStatus` (
  `status` varchar(16) NOT NULL,
  `_expStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_expStatus_key`),
  UNIQUE KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_ExpStatus`
--

LOCK TABLES `cv_ExpStatus` WRITE;
/*!40000 ALTER TABLE `cv_ExpStatus` DISABLE KEYS */;
INSERT INTO `cv_ExpStatus` VALUES ('aborted',1,1),('active',2,1),('completed',3,1);
/*!40000 ALTER TABLE `cv_ExpStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_FieldOfStudy`
--

DROP TABLE IF EXISTS `cv_FieldOfStudy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_FieldOfStudy` (
  `fieldOfStudyName` varchar(32) NOT NULL,
  `_fieldOfStudy_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_fieldOfStudy_key`),
  UNIQUE KEY `expGroup` (`fieldOfStudyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_FieldOfStudy`
--

LOCK TABLES `cv_FieldOfStudy` WRITE;
/*!40000 ALTER TABLE `cv_FieldOfStudy` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_FieldOfStudy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GeneClass`
--

DROP TABLE IF EXISTS `cv_GeneClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GeneClass` (
  `GeneClass` varchar(16) NOT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `_geneClass_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_geneClass_key`),
  UNIQUE KEY `geneClassTerm` (`GeneClass`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GeneClass`
--

LOCK TABLES `cv_GeneClass` WRITE;
/*!40000 ALTER TABLE `cv_GeneClass` DISABLE KEYS */;
INSERT INTO `cv_GeneClass` VALUES ('CTK','Combonation transgene + KO',1,1),('E','endogenous',2,1),('Floxed','tissue specific knock out',3,1),('KI','knock in',4,1),('KO','knock out',5,1),('MKO','multi allele knock out',6,1),('MTG','multi allele transgene',7,1),('TG','transgene',8,1);
/*!40000 ALTER TABLE `cv_GeneClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Generation`
--

DROP TABLE IF EXISTS `cv_Generation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Generation` (
  `generation` varchar(16) NOT NULL,
  `_generation_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_generation_key`),
  UNIQUE KEY `genName` (`generation`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Generation`
--

LOCK TABLES `cv_Generation` WRITE;
/*!40000 ALTER TABLE `cv_Generation` DISABLE KEYS */;
INSERT INTO `cv_Generation` VALUES ('F01',1,1),('F02',2,1),('F03',3,1),('N02',4,1),('N03',5,1),('N04',6,1),('N05',7,1),('N06',8,1),('N07',9,1),('N08',10,1),('N09',11,1),('N10',12,1),('N11',13,1),('N12',14,1),('N13',15,1),('N14',16,1),('N15',17,1),('N16',18,1),('N17',19,1),('N18',20,1),('N19',21,1),('N20',22,1),('N21',23,1),('N22',24,1),('N23',25,1),('N24',26,1),('N25',27,1),('N26',28,1),('N27',29,1),('N28',30,1),('N29',31,1),('N30',32,1);
/*!40000 ALTER TABLE `cv_Generation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GenotypeSpecimenLocation`
--

DROP TABLE IF EXISTS `cv_GenotypeSpecimenLocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GenotypeSpecimenLocation` (
  `genotypeSpecimenLocation` varchar(16) NOT NULL,
  `_genotypeSpecimenLocation_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotypeSpecimenLocation_key`),
  UNIQUE KEY `sampleLocation` (`genotypeSpecimenLocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GenotypeSpecimenLocation`
--

LOCK TABLES `cv_GenotypeSpecimenLocation` WRITE;
/*!40000 ALTER TABLE `cv_GenotypeSpecimenLocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_GenotypeSpecimenLocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_GenotypingPlateStatus`
--

DROP TABLE IF EXISTS `cv_GenotypingPlateStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_GenotypingPlateStatus` (
  `_genotypingPlateStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `genotypingPlateStatus` varchar(32) NOT NULL,
  `isDefault` tinyint(1) NOT NULL,
  `areWellsEditable` tinyint(1) NOT NULL,
  `isPlateSubmittable` tinyint(1) NOT NULL,
  `sortOrder` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_genotypingPlateStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_GenotypingPlateStatus`
--

LOCK TABLES `cv_GenotypingPlateStatus` WRITE;
/*!40000 ALTER TABLE `cv_GenotypingPlateStatus` DISABLE KEYS */;
INSERT INTO `cv_GenotypingPlateStatus` VALUES (1,'Open',-1,-1,0,1,1),(2,'Sealed',0,0,-1,2,1);
/*!40000 ALTER TABLE `cv_GenotypingPlateStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_HarvestMethod`
--

DROP TABLE IF EXISTS `cv_HarvestMethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_HarvestMethod` (
  `harvestMethod` varchar(32) NOT NULL,
  `_harvestMethod_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_harvestMethod_key`),
  UNIQUE KEY `harvestMethod` (`harvestMethod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_HarvestMethod`
--

LOCK TABLES `cv_HarvestMethod` WRITE;
/*!40000 ALTER TABLE `cv_HarvestMethod` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_HarvestMethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_HealthLevel`
--

DROP TABLE IF EXISTS `cv_HealthLevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_HealthLevel` (
  `_healthLevel_key` int(11) NOT NULL AUTO_INCREMENT,
  `healthLevel` varchar(8) NOT NULL,
  `description` varchar(32) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_healthLevel_key`),
  UNIQUE KEY `healthLevel` (`healthLevel`),
  KEY `_healthLevel_key` (`_healthLevel_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_HealthLevel`
--

LOCK TABLES `cv_HealthLevel` WRITE;
/*!40000 ALTER TABLE `cv_HealthLevel` DISABLE KEYS */;
INSERT INTO `cv_HealthLevel` VALUES (1,'1',NULL,1),(2,'2',NULL,1),(3,'3',NULL,1),(4,'4',NULL,1);
/*!40000 ALTER TABLE `cv_HealthLevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Keywords`
--

DROP TABLE IF EXISTS `cv_Keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Keywords` (
  `keyword` varchar(32) NOT NULL,
  `_keyword_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_keyword_key`),
  UNIQUE KEY `keyword` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Keywords`
--

LOCK TABLES `cv_Keywords` WRITE;
/*!40000 ALTER TABLE `cv_Keywords` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_Keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LineStatus`
--

DROP TABLE IF EXISTS `cv_LineStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LineStatus` (
  `_lineStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `lineStatus` varchar(75) NOT NULL,
  `abbreviation` varchar(2) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isDefault` tinyint(1) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_lineStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LineStatus`
--

LOCK TABLES `cv_LineStatus` WRITE;
/*!40000 ALTER TABLE `cv_LineStatus` DISABLE KEYS */;
INSERT INTO `cv_LineStatus` VALUES (1,'Active','A',-1,-1,NULL,NULL,NULL,0,1),(2,'See comments','C',-1,0,NULL,NULL,NULL,0,1),(3,'Discarded','D',-1,0,NULL,NULL,NULL,0,1),(4,'Frozen and not active','F',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_LineStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LitterType`
--

DROP TABLE IF EXISTS `cv_LitterType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LitterType` (
  `_litterType_key` int(11) NOT NULL AUTO_INCREMENT,
  `litterType` varchar(75) NOT NULL,
  `abbreviation` varchar(8) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT NULL,
  `isDefault` tinyint(4) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(4) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_litterType_key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LitterType`
--

LOCK TABLES `cv_LitterType` WRITE;
/*!40000 ALTER TABLE `cv_LitterType` DISABLE KEYS */;
INSERT INTO `cv_LitterType` VALUES (1,'Live Birth','A',-1,-1,NULL,NULL,NULL,0,1),(2,'Harvested Embryos','E',-1,0,NULL,NULL,NULL,0,1),(3,'Embryonic Stem Cells','ES',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_LitterType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_LocationType`
--

DROP TABLE IF EXISTS `cv_LocationType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_LocationType` (
  `_locationType_key` int(11) NOT NULL DEFAULT '0',
  `locationType` varchar(32) NOT NULL,
  `_storageFacility_key` int(11) NOT NULL DEFAULT '0',
  `locationDetail` longtext,
  `locationTypeRef` int(11) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_locationType_key`),
  UNIQUE KEY `cv_LocationType_locationType_key` (`_locationType_key`),
  KEY `cv_LocationType_storageFacility_key` (`_storageFacility_key`),
  KEY `cv_StorageFacilitycv_LocationType` (`_storageFacility_key`),
  CONSTRAINT `cv_locationtype_ibfk_1` FOREIGN KEY (`_storageFacility_key`) REFERENCES `cv_StorageFacility` (`_storageFacility_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_LocationType`
--

LOCK TABLES `cv_LocationType` WRITE;
/*!40000 ALTER TABLE `cv_LocationType` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_LocationType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MatingCardNotes`
--

DROP TABLE IF EXISTS `cv_MatingCardNotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MatingCardNotes` (
  `matingNotes` varchar(64) DEFAULT NULL,
  `_matingCardNotes_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_matingCardNotes_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MatingCardNotes`
--

LOCK TABLES `cv_MatingCardNotes` WRITE;
/*!40000 ALTER TABLE `cv_MatingCardNotes` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_MatingCardNotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MatingType`
--

DROP TABLE IF EXISTS `cv_MatingType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MatingType` (
  `_matingType_key` int(11) NOT NULL AUTO_INCREMENT,
  `matingType` varchar(75) NOT NULL,
  `abbreviation` varchar(8) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT NULL,
  `isDefault` tinyint(4) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(4) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_matingType_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MatingType`
--

LOCK TABLES `cv_MatingType` WRITE;
/*!40000 ALTER TABLE `cv_MatingType` DISABLE KEYS */;
INSERT INTO `cv_MatingType` VALUES (1,'Natural','N',-1,-1,NULL,NULL,NULL,0,1),(2,'Ovary Transfer','OT',-1,0,NULL,NULL,NULL,0,1),(3,'Embryo Transfer','ET',-1,0,NULL,NULL,NULL,0,1),(4,'In vitro Fertilization','IVF',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_MatingType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MatingUnitType`
--

DROP TABLE IF EXISTS `cv_MatingUnitType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MatingUnitType` (
  `_matingUnitType_key` int(11) NOT NULL AUTO_INCREMENT,
  `matingUnitType` varchar(75) NOT NULL,
  `abbreviation` varchar(12) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT NULL,
  `isDefault` tinyint(4) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(4) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_matingUnitType_key`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MatingUnitType`
--

LOCK TABLES `cv_MatingUnitType` WRITE;
/*!40000 ALTER TABLE `cv_MatingUnitType` DISABLE KEYS */;
INSERT INTO `cv_MatingUnitType` VALUES (1,'Dam (Biological Female Parent)','Dam',-1,0,NULL,NULL,NULL,0,1),(2,'Sire (Biological Male Parent)','Sire',-1,0,NULL,NULL,NULL,0,1),(3,'Embryo Sample','Embryo',-1,0,NULL,NULL,NULL,0,1),(4,'Ovary Sample','Ovary',-1,0,NULL,NULL,NULL,0,1),(5,'Surrogate or Host Mother','Host Dam',-1,0,NULL,NULL,NULL,0,1),(6,'Egg Sample','Egg',-1,0,NULL,NULL,NULL,0,1),(7,'Sperm Sample','Sperm',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_MatingUnitType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MouseOrigin`
--

DROP TABLE IF EXISTS `cv_MouseOrigin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MouseOrigin` (
  `_mouseOrigin_key` int(11) NOT NULL AUTO_INCREMENT,
  `mouseOrigin` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_mouseOrigin_key`),
  UNIQUE KEY `mouseOrigin` (`mouseOrigin`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MouseOrigin`
--

LOCK TABLES `cv_MouseOrigin` WRITE;
/*!40000 ALTER TABLE `cv_MouseOrigin` DISABLE KEYS */;
INSERT INTO `cv_MouseOrigin` VALUES (1,'internal',1),(2,'JAX',1);
/*!40000 ALTER TABLE `cv_MouseOrigin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MouseProtocol`
--

DROP TABLE IF EXISTS `cv_MouseProtocol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MouseProtocol` (
  `id` varchar(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `_mouseProtocol_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_mouseProtocol_key`),
  UNIQUE KEY `protocolId` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MouseProtocol`
--

LOCK TABLES `cv_MouseProtocol` WRITE;
/*!40000 ALTER TABLE `cv_MouseProtocol` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_MouseProtocol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_MouseUse`
--

DROP TABLE IF EXISTS `cv_MouseUse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_MouseUse` (
  `mouseUse` varchar(32) NOT NULL,
  `useDescription` varchar(64) DEFAULT NULL,
  `_mouseUse_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  `d1Caption` varchar(32) NOT NULL DEFAULT 'D1',
  `d2Caption` varchar(32) NOT NULL DEFAULT 'D2',
  `d3Caption` varchar(32) NOT NULL DEFAULT 'D3',
  `d4Caption` varchar(32) NOT NULL DEFAULT 'D4',
  `d5Caption` varchar(32) NOT NULL DEFAULT 'D5',
  `d6Caption` varchar(32) NOT NULL DEFAULT 'D6',
  `d7Caption` varchar(32) NOT NULL DEFAULT 'D7',
  `d8Caption` varchar(32) NOT NULL DEFAULT 'D8',
  `d9Caption` varchar(32) NOT NULL DEFAULT 'D9',
  `d10Caption` varchar(32) NOT NULL DEFAULT 'D10',
  `isActive` tinyint(1) DEFAULT '-1',
  PRIMARY KEY (`_mouseUse_key`),
  UNIQUE KEY `mouseUse` (`mouseUse`),
  KEY `isActive` (`isActive`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_MouseUse`
--

LOCK TABLES `cv_MouseUse` WRITE;
/*!40000 ALTER TABLE `cv_MouseUse` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_MouseUse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_PreservationDetail`
--

DROP TABLE IF EXISTS `cv_PreservationDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_PreservationDetail` (
  `_preservationDetail_key` int(11) NOT NULL,
  `preservationDetail` varchar(32) NOT NULL,
  `_preservationMethod_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_preservationDetail_key`),
  KEY `cv_PreservationDetail_preservationDetail_key` (`_preservationDetail_key`),
  KEY `cv_PreservationDetail_preservationMethod_key` (`_preservationMethod_key`),
  CONSTRAINT `cv_preservationdetail_ibfk_1` FOREIGN KEY (`_preservationMethod_key`) REFERENCES `cv_PreservationMethod` (`_preservationMethod_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_PreservationDetail`
--

LOCK TABLES `cv_PreservationDetail` WRITE;
/*!40000 ALTER TABLE `cv_PreservationDetail` DISABLE KEYS */;
INSERT INTO `cv_PreservationDetail` VALUES (1,'4% Formaldehyde',1,1);
/*!40000 ALTER TABLE `cv_PreservationDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_PreservationMethod`
--

DROP TABLE IF EXISTS `cv_PreservationMethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_PreservationMethod` (
  `_preservationMethod_key` int(11) NOT NULL,
  `preservationMethod` varchar(32) NOT NULL,
  `_preservationType_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_preservationMethod_key`),
  KEY `cv_PreservationMethod_preservationMethod_key` (`_preservationMethod_key`),
  KEY `cv_PreservationMethod_preservationType_key` (`_preservationType_key`),
  CONSTRAINT `cv_preservationmethod_ibfk_1` FOREIGN KEY (`_preservationType_key`) REFERENCES `cv_PreservationType` (`_preservationType_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_PreservationMethod`
--

LOCK TABLES `cv_PreservationMethod` WRITE;
/*!40000 ALTER TABLE `cv_PreservationMethod` DISABLE KEYS */;
INSERT INTO `cv_PreservationMethod` VALUES (1,'Immersion',1,1);
/*!40000 ALTER TABLE `cv_PreservationMethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_PreservationType`
--

DROP TABLE IF EXISTS `cv_PreservationType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_PreservationType` (
  `_preservationType_key` int(11) NOT NULL,
  `preservationType` varchar(32) NOT NULL,
  `_sampleClass_key` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_preservationType_key`),
  UNIQUE KEY `cv_PreservationType_preservationType_key` (`_preservationType_key`),
  KEY `cv_PreservationType_sampleClass_key` (`_sampleClass_key`),
  KEY `cv_SampleClasscv_PreservationType` (`_sampleClass_key`),
  CONSTRAINT `cv_preservationtype_ibfk_1` FOREIGN KEY (`_sampleClass_key`) REFERENCES `cv_SampleClass` (`_sampleClass_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_PreservationType`
--

LOCK TABLES `cv_PreservationType` WRITE;
/*!40000 ALTER TABLE `cv_PreservationType` DISABLE KEYS */;
INSERT INTO `cv_PreservationType` VALUES (1,'Fixed',1,1),(2,'Frozen',1,1),(3,'Dried',1,1);
/*!40000 ALTER TABLE `cv_PreservationType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleClass`
--

DROP TABLE IF EXISTS `cv_SampleClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleClass` (
  `_sampleClass_key` int(11) NOT NULL DEFAULT '0',
  `sampleClass` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sampleClass_key`),
  UNIQUE KEY `cv_SampleClass_sampleClass_key` (`_sampleClass_key`),
  UNIQUE KEY `sampleClass` (`sampleClass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleClass`
--

LOCK TABLES `cv_SampleClass` WRITE;
/*!40000 ALTER TABLE `cv_SampleClass` DISABLE KEYS */;
INSERT INTO `cv_SampleClass` VALUES (1,'Live',1),(2,'Histology',1),(3,'Mol-Bio',1);
/*!40000 ALTER TABLE `cv_SampleClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleDateType`
--

DROP TABLE IF EXISTS `cv_SampleDateType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleDateType` (
  `_sampleDateType_key` int(11) NOT NULL DEFAULT '0',
  `sampleDateType` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sampleDateType_key`),
  UNIQUE KEY `cv_SampleDateType_sampleDateType_key` (`_sampleDateType_key`),
  UNIQUE KEY `sampleDateType` (`sampleDateType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleDateType`
--

LOCK TABLES `cv_SampleDateType` WRITE;
/*!40000 ALTER TABLE `cv_SampleDateType` DISABLE KEYS */;
INSERT INTO `cv_SampleDateType` VALUES (1,'Date Harvested',1);
/*!40000 ALTER TABLE `cv_SampleDateType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleStatus`
--

DROP TABLE IF EXISTS `cv_SampleStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleStatus` (
  `_sampleStatus_key` int(11) NOT NULL DEFAULT '0',
  `sampleStatus` varchar(32) NOT NULL,
  `isInStorage` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sampleStatus_key`),
  UNIQUE KEY `cv_SampleStatus_sampleStatus_key` (`_sampleStatus_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleStatus`
--

LOCK TABLES `cv_SampleStatus` WRITE;
/*!40000 ALTER TABLE `cv_SampleStatus` DISABLE KEYS */;
INSERT INTO `cv_SampleStatus` VALUES (1,'Unprocessed',0,1),(2,'Processed',0,1),(3,'Archived',-1,1);
/*!40000 ALTER TABLE `cv_SampleStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_SampleType`
--

DROP TABLE IF EXISTS `cv_SampleType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_SampleType` (
  `_sampleType_key` int(11) NOT NULL DEFAULT '0',
  `sampleType` varchar(32) NOT NULL,
  `_sampleClass_key` int(11) NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sampleType_key`),
  UNIQUE KEY `cv_SampleType_sampleType_key` (`_sampleType_key`),
  KEY `cv_SampleClasscv_SampleType` (`_sampleClass_key`),
  KEY `cv_SampleType_sampleClass_key` (`_sampleClass_key`),
  CONSTRAINT `cv_sampletype_ibfk_1` FOREIGN KEY (`_sampleClass_key`) REFERENCES `cv_SampleClass` (`_sampleClass_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_SampleType`
--

LOCK TABLES `cv_SampleType` WRITE;
/*!40000 ALTER TABLE `cv_SampleType` DISABLE KEYS */;
INSERT INTO `cv_SampleType` VALUES (3,'DNA',3,1),(4,'RNA',3,1),(5,'Sperm',1,1),(6,'Embryo',1,1),(7,'Cell Line',1,1),(8,'Brain',2,1),(9,'Heart',2,1),(10,'Liver',2,1);
/*!40000 ALTER TABLE `cv_SampleType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_Sex`
--

DROP TABLE IF EXISTS `cv_Sex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_Sex` (
  `_sex_key` int(11) NOT NULL AUTO_INCREMENT,
  `sex` varchar(75) NOT NULL,
  `abbreviation` varchar(2) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isDefault` tinyint(1) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_sex_key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_Sex`
--

LOCK TABLES `cv_Sex` WRITE;
/*!40000 ALTER TABLE `cv_Sex` DISABLE KEYS */;
INSERT INTO `cv_Sex` VALUES (1,'Male','M',-1,0,NULL,NULL,NULL,0,1),(2,'Female','F',-1,0,NULL,NULL,NULL,0,1),(3,'Unknown','-',-1,-1,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_Sex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_StorageFacility`
--

DROP TABLE IF EXISTS `cv_StorageFacility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_StorageFacility` (
  `_storageFacility_key` int(11) NOT NULL DEFAULT '0',
  `storageFacility` varchar(64) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_storageFacility_key`),
  UNIQUE KEY `storageFacility` (`storageFacility`),
  KEY `cv_StorageFacility_storageFacility_key` (`_storageFacility_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_StorageFacility`
--

LOCK TABLES `cv_StorageFacility` WRITE;
/*!40000 ALTER TABLE `cv_StorageFacility` DISABLE KEYS */;
/*!40000 ALTER TABLE `cv_StorageFacility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_StrainStatus`
--

DROP TABLE IF EXISTS `cv_StrainStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_StrainStatus` (
  `_strainStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `strainStatus` varchar(1) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `isActive` int(11) NOT NULL DEFAULT '-1',
  `createdBy` varchar(45) NOT NULL DEFAULT 'dba',
  `dateCreated` datetime NOT NULL DEFAULT '1970-01-01 01:00:00',
  `modifiedBy` varchar(45) NOT NULL DEFAULT 'dba',
  `dateModified` datetime NOT NULL DEFAULT '1970-01-01 01:00:00',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_strainStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_StrainStatus`
--

LOCK TABLES `cv_StrainStatus` WRITE;
/*!40000 ALTER TABLE `cv_StrainStatus` DISABLE KEYS */;
INSERT INTO `cv_StrainStatus` VALUES (1,'A','Active',-1,'dba','2013-06-10 13:43:17','dba','2013-06-10 13:43:17',1),(2,'C','See comments',-1,'dba','2013-06-10 13:43:17','dba','2013-06-10 13:43:17',1),(3,'D','Discarded',-1,'dba','2013-06-10 13:43:17','dba','2013-06-10 13:43:17',1),(4,'F','Frozen and not active',-1,'dba','2013-06-10 13:43:17','dba','2013-06-10 13:43:17',1);
/*!40000 ALTER TABLE `cv_StrainStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_StrainType`
--

DROP TABLE IF EXISTS `cv_StrainType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_StrainType` (
  `strainType` varchar(64) NOT NULL,
  `_strainType_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_strainType_key`),
  UNIQUE KEY `strainType_UNIQUE` (`strainType`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_StrainType`
--

LOCK TABLES `cv_StrainType` WRITE;
/*!40000 ALTER TABLE `cv_StrainType` DISABLE KEYS */;
INSERT INTO `cv_StrainType` VALUES ('coisogenic',1,1),('congenic',2,1),('conplastic',3,1),('consomic',4,1),('F1 hybrid',5,1),('F2 hybrid',6,1),('inbred',7,1),('outbred',8,1),('recombinant congenic',9,1),('recombinant inbred',10,1),('wild-derived inbred',11,1);
/*!40000 ALTER TABLE `cv_StrainType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_TableName`
--

DROP TABLE IF EXISTS `cv_TableName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_TableName` (
  `_tableName_key` int(11) NOT NULL AUTO_INCREMENT,
  `tableName` varchar(255) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `isActive` tinyint(1) DEFAULT '-1',
  `isDefault` tinyint(1) DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_tableName_key`),
  UNIQUE KEY `abbreviation` (`abbreviation`),
  UNIQUE KEY `tableName` (`tableName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_TableName`
--

LOCK TABLES `cv_TableName` WRITE;
/*!40000 ALTER TABLE `cv_TableName` DISABLE KEYS */;
INSERT INTO `cv_TableName` VALUES (1,'Genotype','Genotype',-1,-1,1),(2,'ExpPlan','Exp Plan',-1,0,1),(3,'Litter','Litter',-1,0,1),(4,'Mating','Mating',-1,0,1),(5,'Mouse','Mouse',-1,0,1),(6,'Sample','Sample',-1,0,1),(7,'ExpData','Exp Data',-1,0,1),(8,'ExpDataDescriptor','Test Type',-1,0,1),(9,'MouseUsage','Mouse Use',-1,0,1);
/*!40000 ALTER TABLE `cv_TableName` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_TestStatus`
--

DROP TABLE IF EXISTS `cv_TestStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_TestStatus` (
  `testStatus` varchar(8) NOT NULL,
  `_testStatus_key` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_testStatus_key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_TestStatus`
--

LOCK TABLES `cv_TestStatus` WRITE;
/*!40000 ALTER TABLE `cv_TestStatus` DISABLE KEYS */;
INSERT INTO `cv_TestStatus` VALUES ('active',1,1),('done',2,1),('proposed',3,1);
/*!40000 ALTER TABLE `cv_TestStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_TheilerStage`
--

DROP TABLE IF EXISTS `cv_TheilerStage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_TheilerStage` (
  `_theilerStage_key` int(11) NOT NULL AUTO_INCREMENT,
  `TheilerStage` varchar(8) NOT NULL,
  `abbreviation` varchar(4) NOT NULL,
  `dpc` double NOT NULL,
  `startDPC` double NOT NULL,
  `endDPC` double NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  `isActive` tinyint(4) NOT NULL,
  `isDefault` tinyint(4) DEFAULT NULL,
  `sortOrder` int(11) DEFAULT NULL,
  `_vocabularySource_key` int(11) DEFAULT NULL,
  `elementID` varchar(18) DEFAULT NULL,
  `isDeprecated` tinyint(4) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_theilerStage_key`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_TheilerStage`
--

LOCK TABLES `cv_TheilerStage` WRITE;
/*!40000 ALTER TABLE `cv_TheilerStage` DISABLE KEYS */;
INSERT INTO `cv_TheilerStage` VALUES (1,'1','TS01',0,0,2.5,'One-cell egg',-1,0,NULL,NULL,NULL,0,1),(2,'2','TS02',1,1,2.5,'Beginning of cell division',-1,0,NULL,NULL,NULL,0,1),(3,'3','TS03',2,1,3.5,'Morula',-1,0,NULL,NULL,NULL,0,1),(4,'4','TS04',3,2,4,'Advanced division/segmentation',-1,0,NULL,NULL,NULL,0,1),(5,'5','TS05',4,3,5.5,'Blastocyst',-1,0,NULL,NULL,NULL,0,1),(6,'6','TS06',4.5,4,6,'Implantation',-1,0,NULL,NULL,NULL,0,1),(7,'7','TS07',5,4.5,6,'Formation of egg cylinder',-1,0,NULL,NULL,NULL,0,1),(8,'8','TS08',6,5,6.5,'Differentiation of egg cylinder',-1,0,NULL,NULL,NULL,0,1),(9,'9','TS09',6.5,6.25,7.25,'Advanced endometrial reaction, Prestreak',-1,0,NULL,NULL,NULL,0,1),(10,'10','TS10',7,6.5,7.75,'Amnion, Midstreak',-1,0,NULL,NULL,NULL,0,1),(11,'11','TS11',7.5,7.25,8,'Neural plate, presomite stage, no allantoic bud',-1,0,NULL,NULL,NULL,0,1),(12,'12','TS12',8,7.5,8.25,'First somites, late head fold',-1,0,NULL,NULL,NULL,0,1),(13,'13','TS13',8.5,8,9.25,'Turning',-1,0,NULL,NULL,NULL,0,1),(14,'14','TS14',9,8.5,9.75,'Formation & closure of anterior neuropore',-1,0,NULL,NULL,NULL,0,1),(15,'15','TS15',9.5,9,10.5,'of posterior neuropore, forelimb bud',-1,0,NULL,NULL,NULL,0,1),(16,'16','TS16',10,9.5,10.75,'Closure posterior neuropore, hindlimb & tail bud',-1,0,NULL,NULL,NULL,0,1),(17,'17','TS17',10.5,10,11.25,'Deep lens indentation',-1,0,NULL,NULL,NULL,0,1),(18,'18','TS18',11,10.5,11.25,'Complete separation of lens vesicle',-1,0,NULL,NULL,NULL,0,1),(19,'19','TS19',11.5,11,12.25,'Closure lens vesicle',-1,0,NULL,NULL,NULL,0,1),(20,'20','TS20',12,11.5,13,'Earliest sign of fingers',-1,0,NULL,NULL,NULL,0,1),(21,'21','TS21',13,12.5,14,'Anterior footplate indented, marked pinna',-1,0,NULL,NULL,NULL,0,1),(22,'22','TS22',14,13.5,15,'Fingers separate distally',-1,0,NULL,NULL,NULL,0,1),(23,'23','TS23',15,15,15,'Toes separate',-1,0,NULL,NULL,NULL,0,1),(24,'24','TS24',16,16,16,'Reposition of umbilical hernia',-1,0,NULL,NULL,NULL,0,1),(25,'25','TS25',17,17,17,'Fingers and toes joined together',-1,0,NULL,NULL,NULL,0,1),(26,'26','TS26',18,18,18,'Long wiskers',-1,0,NULL,NULL,NULL,0,1),(27,'27','TS27',19,19,19,'Newborn',-1,0,NULL,NULL,NULL,0,1);
/*!40000 ALTER TABLE `cv_TheilerStage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_TimeUnit`
--

DROP TABLE IF EXISTS `cv_TimeUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_TimeUnit` (
  `_timeUnit_key` int(11) NOT NULL DEFAULT '0',
  `timeUnit` varchar(32) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `minutesPerUnit` int(11) NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_timeUnit_key`),
  UNIQUE KEY `cv_TimeUnit_timeUnit_key` (`_timeUnit_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_TimeUnit`
--

LOCK TABLES `cv_TimeUnit` WRITE;
/*!40000 ALTER TABLE `cv_TimeUnit` DISABLE KEYS */;
INSERT INTO `cv_TimeUnit` VALUES (1,'Hours','h',60,1),(2,'Days','d',1440,1),(3,'Weeks','ww',10080,1),(4,'Months','m',43800,1);
/*!40000 ALTER TABLE `cv_TimeUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv_WeightUnit`
--

DROP TABLE IF EXISTS `cv_WeightUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_WeightUnit` (
  `_weightUnit_key` int(11) NOT NULL DEFAULT '0',
  `weightUnit` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_weightUnit_key`),
  UNIQUE KEY `cv_WeightUnit_weightUnit_key` (`_weightUnit_key`),
  UNIQUE KEY `weightUnit` (`weightUnit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_WeightUnit`
--

LOCK TABLES `cv_WeightUnit` WRITE;
/*!40000 ALTER TABLE `cv_WeightUnit` DISABLE KEYS */;
INSERT INTO `cv_WeightUnit` VALUES (1,'gram',1),(2,'ounces',1);
/*!40000 ALTER TABLE `cv_WeightUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbInfo`
--

DROP TABLE IF EXISTS `dbInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbInfo` (
  `_dbinfo_key` int(11) NOT NULL,
  `dbVers` varchar(8) NOT NULL,
  `versDate` datetime NOT NULL,
  `maxPenID` int(11) NOT NULL,
  `releaseNum` varchar(16) NOT NULL,
  `releaseDate` datetime DEFAULT NULL,
  `maxAutoLitterNum` int(11) DEFAULT NULL,
  `maxAutoMouseID` int(11) DEFAULT NULL,
  `majorVersion` int(11) NOT NULL,
  `minorVersion` int(11) NOT NULL,
  `bugFixVersion` int(11) DEFAULT NULL,
  `buildVersion` int(11) DEFAULT NULL,
  `releaseType` varchar(32) DEFAULT NULL,
  `releaseNotes` varchar(255) DEFAULT NULL,
  `databaseReleaseNum` varchar(255) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_dbinfo_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbInfo`
--

LOCK TABLES `dbInfo` WRITE;
/*!40000 ALTER TABLE `dbInfo` DISABLE KEYS */;
INSERT INTO `dbInfo` VALUES (1,'102',now(),0,'5.1.0','2013-07-10 00:00:00',9,0,5,1,0,0,'P','Original Release','102',1);
/*!40000 ALTER TABLE `dbInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lkpCalendarDimensions`
--

DROP TABLE IF EXISTS `lkpCalendarDimensions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lkpCalendarDimensions` (
  `_calendarDimensions_key` int(11) NOT NULL AUTO_INCREMENT,
  `MonthWidth` int(11) DEFAULT '0',
  `MonthHeight` int(11) DEFAULT '0',
  `OneWeekWidth` int(11) DEFAULT '0',
  `OneWeekHeight` int(11) DEFAULT '0',
  `TwoWeekWidth` int(11) DEFAULT '0',
  `TwoWeekHeight` int(11) DEFAULT '0',
  `OneDayWidth` int(11) DEFAULT '0',
  `OneDayHeight` int(11) DEFAULT '0',
  `CurrentSpan` smallint(6) DEFAULT '1',
  `userName` varchar(75) NOT NULL,
  `calendarName` varchar(32) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`_calendarDimensions_key`),
  KEY `_calendarDimensions_key` (`_calendarDimensions_key`),
  KEY `calendarName` (`calendarName`),
  KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lkpCalendarDimensions`
--

LOCK TABLES `lkpCalendarDimensions` WRITE;
/*!40000 ALTER TABLE `lkpCalendarDimensions` DISABLE KEYS */;
INSERT INTO `lkpCalendarDimensions` VALUES (1,2010,1275,1950,6555,1950,3375,8550,5475,1,'mtsadmin','CalendarSchedule_MouseUses',1);
/*!40000 ALTER TABLE `lkpCalendarDimensions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-10 13:45:51
