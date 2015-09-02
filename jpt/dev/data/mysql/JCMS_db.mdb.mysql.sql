
CREATE DATABASE IF NOT EXISTS jpt_ut;


USE jpt_ut;


CREATE TABLE IF NOT EXISTS jpt_ut.`Allele`
(`_gene_key` INTEGER NULL DEFAULT NULL, 
 `allele` VARCHAR (8) NOT NULL, 
 `genericAlleleGeneClass` VARCHAR (16) NULL, 
 `_allele_key` INTEGER  NOT NULL AUTO_INCREMENT, 
PRIMARY KEY (`_allele_key`), 
INDEX `_gene_key`(`_gene_key`), 
INDEX `allele`(`allele`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ApprovedStrainRegistry`
(`_approvedStrain_key` INTEGER  NOT NULL AUTO_INCREMENT, 
 `active` TINYINT(1) NOT NULL DEFAULT 1, 
 `_damStrain_key` INTEGER NOT NULL DEFAULT 0, 
 `_sireStrain_key` INTEGER NOT NULL DEFAULT 0, 
 `_litterStrain_key` INTEGER NOT NULL DEFAULT 0, 
 `_owner_key` INTEGER NULL DEFAULT 0, 
PRIMARY KEY (`_approvedStrain_key`), 
INDEX `_approvedStrain_key`(`_approvedStrain_key`), 
INDEX `_damStrain_key`(`_damStrain_key`), 
INDEX `_litterStrain_key`(`_litterStrain_key`), 
INDEX `_owner_key`(`_owner_key`), 
INDEX `_sireStrain_key`(`_sireStrain_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_CauseOfDeath`
(`cod` VARCHAR (32) NULL, 
 `description` VARCHAR (255) NULL, 
UNIQUE INDEX `cod`(`cod`), 
PRIMARY KEY (`cod`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_CoatColor`
(`coatColor` VARCHAR (8) NOT NULL, 
 `description` VARCHAR (64) NULL, 
UNIQUE INDEX `coatColor`(`coatColor`), 
PRIMARY KEY (`coatColor`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_Diet`
(`diet` VARCHAR (32) NOT NULL, 
 `dietDescription` VARCHAR (64) NULL, 
UNIQUE INDEX `diet`(`diet`), 
PRIMARY KEY (`diet`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_Epoch`
(`_epoch_key` INTEGER NOT NULL DEFAULT 0, 
 `epoch` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `cv_Epoch_epoch_key`(`_epoch_key`), 
UNIQUE INDEX `epoch`(`epoch`), 
PRIMARY KEY (`_epoch_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_ExpSampleLocation`
(`location` VARCHAR (16) NOT NULL, 
UNIQUE INDEX `location`(`location`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_ExpStatus`
(`status` VARCHAR (16) NOT NULL, 
PRIMARY KEY (`status`), 
UNIQUE INDEX `status`(`status`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_FieldOfStudy`
(`fieldOfStudyName` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `expGroup`(`fieldOfStudyName`), 
PRIMARY KEY (`fieldOfStudyName`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_GeneClass`
(`GeneClass` VARCHAR (16) NULL, 
 `Description` VARCHAR (50) NULL, 
PRIMARY KEY (`GeneClass`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_Generation`
(`generation` VARCHAR (16) NOT NULL, 
PRIMARY KEY (`generation`), 
UNIQUE INDEX `genName`(`generation`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_GenotypeSpecimenLocation`
(`genotypeSpecimenLocation` VARCHAR (16) NOT NULL, 
UNIQUE INDEX `sampleLocation`(`genotypeSpecimenLocation`), 
PRIMARY KEY (`genotypeSpecimenLocation`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_HarvestMethod`
(`harvestMethod` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `harvestMethod`(`harvestMethod`), 
PRIMARY KEY (`harvestMethod`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_Keywords`
(`keyword` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `keyword`(`keyword`), 
PRIMARY KEY (`keyword`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_LocationType`
(`_locationType_key` INTEGER NOT NULL DEFAULT 0, 
 `locationType` VARCHAR (32) NOT NULL, 
 `_storageFacility_key` INTEGER NOT NULL DEFAULT 0, 
 `locationDetail` LONGTEXT NULL, 
 `locationTypeRef` INTEGER NULL DEFAULT 0, 
UNIQUE INDEX `cv_LocationType_locationType_key`(`_locationType_key`), 
PRIMARY KEY (`_locationType_key`), 
INDEX `cv_LocationType_storageFacility_key`(`_storageFacility_key`), 
INDEX `cv_StorageFacilitycv_LocationType`(`_storageFacility_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_MatingCardNotes`
(`matingNotes` VARCHAR (64) NULL, 
PRIMARY KEY (`matingNotes`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_MouseOrigin`
(`where` VARCHAR (32) NULL, 
PRIMARY KEY (`where`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_MouseProtocol`
(`id` VARCHAR (32) NOT NULL, 
 `description` VARCHAR (255) NULL, 
PRIMARY KEY (`id`), 
UNIQUE INDEX `protocolId`(`id`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_MouseUse`
(`mouseUse` VARCHAR (32) NOT NULL, 
 `useDescription` VARCHAR (64) NULL, 
UNIQUE INDEX `mouseUse`(`mouseUse`), 
PRIMARY KEY (`mouseUse`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_PreservationDetail`
(`_preservationDetail_key` INTEGER NOT NULL, 
 `preservationDetail` VARCHAR (32) NOT NULL, 
 `_preservationMethod_key` INTEGER NOT NULL, 
PRIMARY KEY (`_preservationDetail_key`), 
INDEX `cv_PreservationDetail_preservationDetail_key`(`_preservationDetail_key`), 
INDEX `cv_PreservationDetail_preservationMethod_key`(`_preservationMethod_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_PreservationMethod`
(`_preservationMethod_key` INTEGER NOT NULL, 
 `preservationMethod` VARCHAR (32) NOT NULL, 
 `_preservationType_key` INTEGER NOT NULL, 
PRIMARY KEY (`_preservationMethod_key`), 
INDEX `cv_PreservationMethod_preservationMethod_key`(`_preservationMethod_key`), 
INDEX `cv_PreservationMethod_preservationType_key`(`_preservationType_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_PreservationType`
(`_preservationType_key` INTEGER NOT NULL, 
 `preservationType` VARCHAR (32) NOT NULL, 
 `_sampleClass_key` INTEGER NOT NULL, 
UNIQUE INDEX `cv_PreservationType_preservationType_key`(`_preservationType_key`), 
PRIMARY KEY (`_preservationType_key`), 
INDEX `cv_PreservationType_sampleClass_key`(`_sampleClass_key`), 
INDEX `cv_SampleClasscv_PreservationType`(`_sampleClass_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_Room`
(`room` VARCHAR (8) NOT NULL, 
UNIQUE INDEX `room`(`room`), 
PRIMARY KEY (`room`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_SampleClass`
(`_sampleClass_key` INTEGER NOT NULL DEFAULT 0, 
 `sampleClass` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `cv_SampleClass_sampleClass_key`(`_sampleClass_key`), 
UNIQUE INDEX `sampleClass`(`sampleClass`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_SampleDateType`
(`_sampleDateType_key` INTEGER NOT NULL DEFAULT 0, 
 `sampleDateType` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `cv_SampleDateType_sampleDateType_key`(`_sampleDateType_key`), 
UNIQUE INDEX `sampleDateType`(`sampleDateType`), 
PRIMARY KEY (`_sampleDateType_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_SampleStatus`
(`_sampleStatus_key` INTEGER NOT NULL DEFAULT 0, 
 `sampleStatus` VARCHAR (32) NOT NULL, 
 `isInStorage` TINYINT(1) NULL, 
UNIQUE INDEX `cv_SampleStatus_sampleStatus_key`(`_sampleStatus_key`), 
PRIMARY KEY (`_sampleStatus_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_SampleType`
(`_sampleType_key` INTEGER NOT NULL DEFAULT 0, 
 `sampleType` VARCHAR (32) NOT NULL, 
 `_sampleClass_key` INTEGER NOT NULL DEFAULT 0, 
UNIQUE INDEX `cv_SampleType_sampleType_key`(`_sampleType_key`), 
PRIMARY KEY (`_sampleType_key`), 
INDEX `cv_SampleClasscv_SampleType`(`_sampleClass_key`), 
INDEX `cv_SampleType_sampleClass_key`(`_sampleClass_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_StorageFacility`
(`_storageFacility_key` INTEGER NOT NULL DEFAULT 0, 
 `storageFacility` VARCHAR (64) NOT NULL, 
PRIMARY KEY (`_storageFacility_key`), 
UNIQUE INDEX `storageFacility`(`storageFacility`), 
INDEX `cv_StorageFacility_storageFacility_key`(`_storageFacility_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_StrainType`
(`strainType` VARCHAR (64) NULL, 
PRIMARY KEY (`strainType`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_TestStatus`
(`testStatus` VARCHAR (8) NOT NULL, 
PRIMARY KEY (`testStatus`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_TimeUnit`
(`_timeUnit_key` INTEGER NOT NULL DEFAULT 0, 
 `timeUnit` VARCHAR (32) NOT NULL, 
 `abbreviation` VARCHAR (10) NOT NULL, 
 `minutesPerUnit` INTEGER NOT NULL DEFAULT 0, 
UNIQUE INDEX `cv_TimeUnit_timeUnit_key`(`_timeUnit_key`), 
PRIMARY KEY (`_timeUnit_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`cv_WeightUnit`
(`_weightUnit_key` INTEGER NOT NULL DEFAULT 0, 
 `weightUnit` VARCHAR (32) NOT NULL, 
UNIQUE INDEX `cv_WeightUnit_weightUnit_key`(`_weightUnit_key`), 
UNIQUE INDEX `weightUnit`(`weightUnit`), 
PRIMARY KEY (`_weightUnit_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`DbFormPrivileges`
(`formName` VARCHAR (32) NOT NULL, 
 `privilegeLevel` VARCHAR (8) NOT NULL, 
 `completeFormName` VARCHAR (64) NOT NULL, 
 `description` VARCHAR (128) NULL, 
UNIQUE INDEX `completeFormName`(`completeFormName`), 
UNIQUE INDEX `formName`(`formName`), 
INDEX `privilegeLevel`(`privilegeLevel`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Dbinfo`
(`dbVers` VARCHAR (8) NOT NULL, 
 `versDate` DATETIME NOT NULL, 
 `maxPenID` INTEGER NOT NULL DEFAULT 0, 
 `releaseNum` VARCHAR (16) NULL, 
 `releaseDate` DATETIME NULL, 
 `maxAutoLitterNum` INTEGER NULL DEFAULT 0, 
 `maxAutoMouseID` INTEGER NULL DEFAULT 0, 
PRIMARY KEY (`dbVers`), 
INDEX `maxAutoLitterNum`(`maxAutoLitterNum`), 
INDEX `maxAutoMouseNum`(`maxAutoMouseID`), 
INDEX `maxPenID`(`maxPenID`), 
INDEX `releaseNum`(`releaseNum`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`DbRelationships`
(`ccolumn` INTEGER NULL, 
 `grbit` INTEGER NULL, 
 `icolumn` INTEGER NULL, 
 `szColumn` VARCHAR (255) NULL, 
 `szObject` VARCHAR (255) NULL, 
 `szReferencedColumn` VARCHAR (255) NULL, 
 `szReferencedObject` VARCHAR (255) NULL, 
 `szRelationship` VARCHAR (255) NULL, 
INDEX `szObject`(`szObject`), 
INDEX `szReferencedObject`(`szReferencedObject`), 
INDEX `szRelationship`(`szRelationship`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`DbSetup`
(`MTSVar` VARCHAR (64) NULL, 
 `MTSValue` VARCHAR (128) NULL, 
 `MTSVarDescription` VARCHAR (255) NULL, 
PRIMARY KEY (`MTSVar`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpData`
(`_expData_key` INTEGER NOT NULL, 
 `_expDataDesc_key` INTEGER NOT NULL, 
 `_expTest_key` INTEGER NULL, 
 `_specimen_key` INTEGER NULL, 
 `dataID` INTEGER NOT NULL, 
 `specimen_type` VARCHAR (8) NOT NULL, 
 `owner` VARCHAR (8) NOT NULL, 
 `expDate` DATETIME NULL, 
 `age` FLOAT NULL, 
 `abnormalData` TINYINT(1) NULL, 
 `_sampleStorHISTO_key` INTEGER NULL, 
 `_sampleStorLIVE_key` INTEGER NULL, 
 `_sampleStorMOLBIO_key` INTEGER NULL, 
 `d1` VARCHAR (64) NULL, 
 `d2` VARCHAR (64) NULL, 
 `d3` VARCHAR (64) NULL, 
 `d4` VARCHAR (64) NULL, 
 `d5` VARCHAR (64) NULL, 
 `d6` VARCHAR (64) NULL, 
 `d7` VARCHAR (64) NULL, 
 `d8` VARCHAR (64) NULL, 
 `d9` VARCHAR (64) NULL, 
 `d10` VARCHAR (64) NULL, 
 `d11` VARCHAR (64) NULL, 
 `d12` VARCHAR (64) NULL, 
 `d13` VARCHAR (64) NULL, 
 `d14` VARCHAR (64) NULL, 
 `d15` VARCHAR (64) NULL, 
 `d16` VARCHAR (64) NULL, 
 `d17` VARCHAR (64) NULL, 
 `d18` VARCHAR (64) NULL, 
 `d19` VARCHAR (64) NULL, 
 `d20` VARCHAR (64) NULL, 
 `d21` VARCHAR (64) NULL, 
 `d22` VARCHAR (64) NULL, 
 `d23` VARCHAR (64) NULL, 
 `d24` VARCHAR (64) NULL, 
 `d25` VARCHAR (64) NULL, 
 `d26` VARCHAR (64) NULL, 
 `d27` VARCHAR (64) NULL, 
 `d28` VARCHAR (64) NULL, 
 `d29` VARCHAR (64) NULL, 
 `d30` VARCHAR (64) NULL, 
UNIQUE INDEX `_expData_key`(`_expData_key`), 
UNIQUE INDEX `dataID`(`dataID`), 
PRIMARY KEY (`_expData_key`), 
INDEX `_expDataDesc_key`(`_expDataDesc_key`), 
INDEX `_expTest_key`(`_expTest_key`), 
INDEX `_sampleStorHISTOL_key`(`_sampleStorHISTO_key`), 
INDEX `_sampleStorLIVE_key`(`_sampleStorLIVE_key`), 
INDEX `_sampleStorLIVE_key1`(`_sampleStorMOLBIO_key`), 
INDEX `_specimen_key`(`_specimen_key`), 
INDEX `{14C09F25-7450-4308-816D-F8917D17F343}`(`_expDataDesc_key`), 
INDEX `{2B2EB535-1AD4-4D95-BC20-41B1E65D3C8A}`(`_expTest_key`), 
INDEX `{70F7E37D-76DB-44F6-8B89-A2794F55E9C3}`(`_specimen_key`), 
INDEX `abnormalData`(`abnormalData`), 
INDEX `owner`(`owner`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpDataDefault`
(`_expDataDefault_key` INTEGER NOT NULL, 
 `_expDataDescriptor_key` INTEGER NOT NULL, 
 `defaultName` VARCHAR (32) NOT NULL, 
 `comment` VARCHAR (128) NULL, 
 `d1_default` VARCHAR (64) NULL, 
 `d2_default` VARCHAR (64) NULL, 
 `d3_default` VARCHAR (64) NULL, 
 `d4_default` VARCHAR (64) NULL, 
 `d5_default` VARCHAR (64) NULL, 
 `d6_default` VARCHAR (64) NULL, 
 `d7_default` VARCHAR (64) NULL, 
 `d8_default` VARCHAR (64) NULL, 
 `d9_default` VARCHAR (64) NULL, 
 `d10_default` VARCHAR (64) NULL, 
 `d11_default` VARCHAR (64) NULL, 
 `d12_default` VARCHAR (64) NULL, 
 `d13_default` VARCHAR (64) NULL, 
 `d14_default` VARCHAR (64) NULL, 
 `d15_default` VARCHAR (64) NULL, 
 `d16_default` VARCHAR (64) NULL, 
 `d17_default` VARCHAR (64) NULL, 
 `d18_default` VARCHAR (64) NULL, 
 `d19_default` VARCHAR (64) NULL, 
 `d20_default` VARCHAR (64) NULL, 
 `d21_default` VARCHAR (64) NULL, 
 `d22_default` VARCHAR (64) NULL, 
 `d23_default` VARCHAR (64) NULL, 
 `d24_default` VARCHAR (64) NULL, 
 `d25_default` VARCHAR (64) NULL, 
 `d26_default` VARCHAR (64) NULL, 
 `d27_default` VARCHAR (64) NULL, 
 `d28_default` VARCHAR (64) NULL, 
 `d29_default` VARCHAR (64) NULL, 
 `d30_default` VARCHAR (64) NULL, 
UNIQUE INDEX `_expDataDefault_key`(`_expDataDefault_key`), 
PRIMARY KEY (`_expDataDefault_key`), 
INDEX `_expDataDescriptor_key`(`_expDataDescriptor_key`), 
INDEX `{7D2B75DF-28A0-4387-A3C1-9EFB27169EDF}`(`_expDataDescriptor_key`), 
INDEX `defaultName`(`defaultName`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpDataDescriptor`
(`_expDataDescriptor_key` INTEGER NOT NULL, 
 `testType` VARCHAR (32) NOT NULL, 
 `d1_caption` VARCHAR (32) NULL, 
 `d1_fieldDescription` VARCHAR (32) NULL, 
 `d1_dataFormat` VARCHAR (4) NULL, 
 `d1_required` TINYINT(1) NULL, 
 `d1_minValue` FLOAT NULL, 
 `d1_maxValue` FLOAT NULL, 
 `d2_caption` VARCHAR (32) NULL, 
 `d2_fieldDescription` VARCHAR (32) NULL, 
 `d2_dataFormat` VARCHAR (4) NULL, 
 `d2_required` TINYINT(1) NULL, 
 `d2_minValue` FLOAT NULL, 
 `d2_maxValue` FLOAT NULL, 
 `d3_caption` VARCHAR (32) NULL, 
 `d3_fieldDescription` VARCHAR (32) NULL, 
 `d3_dataFormat` VARCHAR (4) NULL, 
 `d3_required` TINYINT(1) NULL, 
 `d3_minValue` FLOAT NULL, 
 `d3_maxValue` FLOAT NULL, 
 `d4_caption` VARCHAR (32) NULL, 
 `d4_fieldDescription` VARCHAR (32) NULL, 
 `d4_dataFormat` VARCHAR (4) NULL, 
 `d4_required` TINYINT(1) NULL, 
 `d4_minValue` FLOAT NULL, 
 `d4_maxValue` FLOAT NULL, 
 `d5_caption` VARCHAR (32) NULL, 
 `d5_fieldDescription` VARCHAR (32) NULL, 
 `d5_dataFormat` VARCHAR (4) NULL, 
 `d5_required` TINYINT(1) NULL, 
 `d5_minValue` FLOAT NULL, 
 `d5_maxValue` FLOAT NULL, 
 `d6_caption` VARCHAR (32) NULL, 
 `d6_fieldDescription` VARCHAR (32) NULL, 
 `d6_dataFormat` VARCHAR (4) NULL, 
 `d6_required` TINYINT(1) NULL, 
 `d6_minValue` FLOAT NULL, 
 `d6_maxValue` FLOAT NULL, 
 `d7_caption` VARCHAR (32) NULL, 
 `d7_fieldDescription` VARCHAR (32) NULL, 
 `d7_dataFormat` VARCHAR (4) NULL, 
 `d7_required` TINYINT(1) NULL, 
 `d7_minValue` FLOAT NULL, 
 `d7_maxValue` FLOAT NULL, 
 `d8_caption` VARCHAR (32) NULL, 
 `d8_fieldDescription` VARCHAR (32) NULL, 
 `d8_dataFormat` VARCHAR (4) NULL, 
 `d8_required` TINYINT(1) NULL, 
 `d8_minValue` FLOAT NULL, 
 `d8_maxValue` FLOAT NULL, 
 `d9_caption` VARCHAR (32) NULL, 
 `d9_fieldDescription` VARCHAR (32) NULL, 
 `d9_dataFormat` VARCHAR (4) NULL, 
 `d9_required` TINYINT(1) NULL, 
 `d9_minValue` FLOAT NULL, 
 `d9_maxValue` FLOAT NULL, 
 `d10_caption` VARCHAR (32) NULL, 
 `d10_fieldDescription` VARCHAR (32) NULL, 
 `d10_dataFormat` VARCHAR (4) NULL, 
 `d10_required` TINYINT(1) NULL, 
 `d10_minValue` FLOAT NULL, 
 `d10_maxValue` FLOAT NULL, 
 `d11_caption` VARCHAR (32) NULL, 
 `d11_fieldDescription` VARCHAR (32) NULL, 
 `d11_dataFormat` VARCHAR (4) NULL, 
 `d11_required` TINYINT(1) NULL, 
 `d11_minValue` FLOAT NULL, 
 `d11_maxValue` FLOAT NULL, 
 `d12_caption` VARCHAR (32) NULL, 
 `d12_fieldDescription` VARCHAR (32) NULL, 
 `d12_dataFormat` VARCHAR (4) NULL, 
 `d12_required` TINYINT(1) NULL, 
 `d12_minValue` FLOAT NULL, 
 `d12_maxValue` FLOAT NULL, 
 `d13_caption` VARCHAR (32) NULL, 
 `d13_fieldDescription` VARCHAR (32) NULL, 
 `d13_dataFormat` VARCHAR (4) NULL, 
 `d13_required` TINYINT(1) NULL, 
 `d13_minValue` FLOAT NULL, 
 `d13_maxValue` FLOAT NULL, 
 `d14_caption` VARCHAR (32) NULL, 
 `d14_fieldDescription` VARCHAR (32) NULL, 
 `d14_dataFormat` VARCHAR (4) NULL, 
 `d14_required` TINYINT(1) NULL, 
 `d14_minValue` FLOAT NULL, 
 `d14_maxValue` FLOAT NULL, 
 `d15_caption` VARCHAR (32) NULL, 
 `d15_fieldDescription` VARCHAR (32) NULL, 
 `d15_dataFormat` VARCHAR (4) NULL, 
 `d15_required` TINYINT(1) NULL, 
 `d15_minValue` FLOAT NULL, 
 `d15_maxValue` FLOAT NULL, 
 `d16_caption` VARCHAR (32) NULL, 
 `d16_fieldDescription` VARCHAR (32) NULL, 
 `d16_dataFormat` VARCHAR (4) NULL, 
 `d16_required` TINYINT(1) NULL, 
 `d16_minValue` FLOAT NULL, 
 `d16_maxValue` FLOAT NULL, 
 `d17_caption` VARCHAR (32) NULL, 
 `d17_fieldDescription` VARCHAR (32) NULL, 
 `d17_dataFormat` VARCHAR (4) NULL, 
 `d17_required` TINYINT(1) NULL, 
 `d17_minValue` FLOAT NULL, 
 `d17_maxValue` FLOAT NULL, 
 `d18_caption` VARCHAR (32) NULL, 
 `d18_fieldDescription` VARCHAR (32) NULL, 
 `d18_dataFormat` VARCHAR (4) NULL, 
 `d18_required` TINYINT(1) NULL, 
 `d18_minValue` FLOAT NULL, 
 `d18_maxValue` FLOAT NULL, 
 `d19_caption` VARCHAR (32) NULL, 
 `d19_fieldDescription` VARCHAR (32) NULL, 
 `d19_dataFormat` VARCHAR (4) NULL, 
 `d19_required` TINYINT(1) NULL, 
 `d19_minValue` FLOAT NULL, 
 `d19_maxValue` FLOAT NULL, 
 `d20_caption` VARCHAR (32) NULL, 
 `d20_fieldDescription` VARCHAR (32) NULL, 
 `d20_dataFormat` VARCHAR (4) NULL, 
 `d20_required` TINYINT(1) NULL, 
 `d20_minValue` FLOAT NULL, 
 `d20_maxValue` FLOAT NULL, 
 `d21_caption` VARCHAR (32) NULL, 
 `d21_fieldDescription` VARCHAR (32) NULL, 
 `d21_dataFormat` VARCHAR (4) NULL, 
 `d21_required` TINYINT(1) NULL, 
 `d21_minValue` FLOAT NULL, 
 `d21_maxValue` FLOAT NULL, 
 `d22_caption` VARCHAR (32) NULL, 
 `d22_fieldDescription` VARCHAR (32) NULL, 
 `d22_dataFormat` VARCHAR (4) NULL, 
 `d22_required` TINYINT(1) NULL, 
 `d22_minValue` FLOAT NULL, 
 `d22_maxValue` FLOAT NULL, 
 `d23_caption` VARCHAR (32) NULL, 
 `d23_fieldDescription` VARCHAR (32) NULL, 
 `d23_dataFormat` VARCHAR (4) NULL, 
 `d23_required` TINYINT(1) NULL, 
 `d23_minValue` FLOAT NULL, 
 `d23_maxValue` FLOAT NULL, 
 `d24_caption` VARCHAR (32) NULL, 
 `d24_fieldDescription` VARCHAR (32) NULL, 
 `d24_dataFormat` VARCHAR (4) NULL, 
 `d24_required` TINYINT(1) NULL, 
 `d24_minValue` FLOAT NULL, 
 `d24_maxValue` FLOAT NULL, 
 `d25_caption` VARCHAR (32) NULL, 
 `d25_fieldDescription` VARCHAR (32) NULL, 
 `d25_dataFormat` VARCHAR (4) NULL, 
 `d25_required` TINYINT(1) NULL, 
 `d25_minValue` FLOAT NULL, 
 `d25_maxValue` FLOAT NULL, 
 `d26_caption` VARCHAR (32) NULL, 
 `d26_fieldDescription` VARCHAR (32) NULL, 
 `d26_dataFormat` VARCHAR (4) NULL, 
 `d26_required` TINYINT(1) NULL, 
 `d26_minValue` FLOAT NULL, 
 `d26_maxValue` FLOAT NULL, 
 `d27_caption` VARCHAR (32) NULL, 
 `d27_fieldDescription` VARCHAR (32) NULL, 
 `d27_dataFormat` VARCHAR (4) NULL, 
 `d27_required` TINYINT(1) NULL, 
 `d27_minValue` FLOAT NULL, 
 `d27_maxValue` FLOAT NULL, 
 `d28_caption` VARCHAR (32) NULL, 
 `d28_fieldDescription` VARCHAR (32) NULL, 
 `d28_dataFormat` VARCHAR (4) NULL, 
 `d28_required` TINYINT(1) NULL, 
 `d28_minValue` FLOAT NULL, 
 `d28_maxValue` FLOAT NULL, 
 `d29_caption` VARCHAR (32) NULL, 
 `d29_fieldDescription` VARCHAR (32) NULL, 
 `d29_dataFormat` VARCHAR (4) NULL, 
 `d29_required` TINYINT(1) NULL, 
 `d29_minValue` FLOAT NULL, 
 `d29_maxValue` FLOAT NULL, 
 `d30_caption` VARCHAR (32) NULL, 
 `d30_fieldDescription` VARCHAR (32) NULL, 
 `d30_dataFormat` VARCHAR (4) NULL, 
 `d30_required` TINYINT(1) NULL, 
 `d30_minValue` FLOAT NULL, 
 `d30_maxValue` FLOAT NULL, 
 `testTypeNotes` LONGTEXT NULL, 
UNIQUE INDEX `_expDataDescriptor_key`(`_expDataDescriptor_key`), 
PRIMARY KEY (`_expDataDescriptor_key`), 
UNIQUE INDEX `testType`(`testType`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpPlan`
(`_expPlan_key` INTEGER NOT NULL, 
 `planID` INTEGER NOT NULL, 
 `expPlanName` VARCHAR (64) NOT NULL, 
 `owner` VARCHAR (8) NOT NULL, 
 `fieldOfStudy` VARCHAR (32) NULL, 
 `expNotes` LONGTEXT NULL, 
 `expStatus` VARCHAR (16) NOT NULL DEFAULT "ACTIVE", 
 `keyword1` VARCHAR (32) NULL, 
 `keyword2` VARCHAR (32) NULL, 
 `keyword3` VARCHAR (32) NULL, 
 `keyword4` VARCHAR (32) NULL, 
 `keyword5` VARCHAR (32) NULL, 
UNIQUE INDEX `_expPlan_key`(`_expPlan_key`), 
UNIQUE INDEX `expPlanName`(`expPlanName`), 
PRIMARY KEY (`_expPlan_key`), 
UNIQUE INDEX `planID`(`planID`), 
INDEX `expStatus`(`expStatus`), 
INDEX `keyword1`(`keyword1`), 
INDEX `keyword11`(`keyword2`), 
INDEX `keyword12`(`keyword3`), 
INDEX `keyword13`(`keyword4`), 
INDEX `keyword14`(`keyword5`), 
INDEX `owner`(`owner`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpPlanMouseMap`
(`_expPlanMouseMap_key` INTEGER NOT NULL, 
 `_mouse_key` INTEGER NOT NULL, 
 `_expPlan_key` INTEGER NOT NULL, 
UNIQUE INDEX `_expPlanMouseMap_key`(`_expPlanMouseMap_key`), 
UNIQUE INDEX `MiceInPlan`(`_expPlan_key`,`_mouse_key`), 
PRIMARY KEY (`_expPlanMouseMap_key`), 
INDEX `_expPlan_key`(`_expPlan_key`), 
INDEX `_mouse_key`(`_mouse_key`), 
INDEX `ExpPlanExpPlanMouseMap`(`_expPlan_key`), 
INDEX `MouseExpPlanMouseMap`(`_mouse_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpTest`
(`_expTest_key` INTEGER NOT NULL, 
 `_expPlan_key` INTEGER NOT NULL, 
 `_expDataDescriptor_key` INTEGER NOT NULL, 
 `testName` VARCHAR (32) NULL, 
 `testID` INTEGER NOT NULL, 
 `proposedTestDate` DATETIME NULL, 
 `proposedMinAge` REAL NULL, 
 `proposedMaxAge` REAL NULL, 
 `numMice` INTEGER NULL, 
 `testStatus` VARCHAR (8) NOT NULL, 
 `d1_defaultValue` VARCHAR (64) NULL, 
 `d2_defaultValue` VARCHAR (64) NULL, 
 `d3_defaultValue` VARCHAR (64) NULL, 
 `d4_defaultValue` VARCHAR (64) NULL, 
 `d5_defaultValue` VARCHAR (64) NULL, 
 `d6_defaultValue` VARCHAR (64) NULL, 
 `d7_defaultValue` VARCHAR (64) NULL, 
 `d8_defaultValue` VARCHAR (64) NULL, 
 `d9_defaultValue` VARCHAR (64) NULL, 
 `d10_defaultValue` VARCHAR (64) NULL, 
 `d11_defaultValue` VARCHAR (64) NULL, 
 `d12_defaultValue` VARCHAR (64) NULL, 
 `d13_defaultValue` VARCHAR (64) NULL, 
 `d14_defaultValue` VARCHAR (64) NULL, 
 `d15_defaultValue` VARCHAR (64) NULL, 
 `d16_defaultValue` VARCHAR (64) NULL, 
 `d17_defaultValue` VARCHAR (64) NULL, 
 `d18_defaultValue` VARCHAR (64) NULL, 
 `d19_defaultValue` VARCHAR (64) NULL, 
 `d20_defaultValue` VARCHAR (64) NULL, 
 `d21_defaultValue` VARCHAR (64) NULL, 
 `d22_defaultValue` VARCHAR (64) NULL, 
 `d23_defaultValue` VARCHAR (64) NULL, 
 `d24_defaultValue` VARCHAR (64) NULL, 
 `d25_defaultValue` VARCHAR (64) NULL, 
 `d26_defaultValue` VARCHAR (64) NULL, 
 `d27_defaultValue` VARCHAR (64) NULL, 
 `d28_defaultValue` VARCHAR (64) NULL, 
 `d29_defaultValue` VARCHAR (64) NULL, 
 `d30_defaultValue` VARCHAR (64) NULL, 
 `proposedSampleClass` VARCHAR (8) NULL, 
 `proposedSampleType` VARCHAR (16) NULL, 
 `proposedHarvestMethod` VARCHAR (16) NULL, 
 `proposedHarvestDate` DATETIME NULL, 
 `proposedExpSampleLoc` VARCHAR (16) NULL, 
 `proposedCrossRef` VARCHAR (16) NULL, 
 `proposedPresType` VARCHAR (50) NULL, 
 `proposedPresMethod` VARCHAR (16) NULL, 
 `proposedPresDetail` VARCHAR (16) NULL, 
UNIQUE INDEX `_expTest_key`(`_expTest_key`), 
PRIMARY KEY (`_expTest_key`), 
UNIQUE INDEX `testID`(`testID`), 
INDEX `_expDataDescriptor_key`(`_expDataDescriptor_key`), 
INDEX `{1BD728BD-49BE-4B35-95A3-151A984C2A12}`(`_expPlan_key`), 
INDEX `{F100C89A-0338-44A2-923A-38F9965ACC8A}`(`_expDataDescriptor_key`), 
INDEX `numMice`(`numMice`), 
INDEX `planID`(`_expPlan_key`), 
INDEX `testStatus`(`testStatus`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`ExpTestPlanMap`
(`_expTest_key` INTEGER NOT NULL, 
 `_expPlanMouseMap_key` INTEGER NOT NULL, 
 `_exptestplan_key` INTEGER  NOT NULL AUTO_INCREMENT, 
PRIMARY KEY (`_exptestplan_key`), 
UNIQUE INDEX `MiceInExpTest`(`_expTest_key`,`_expPlanMouseMap_key`), 
INDEX `_expPlanMouseMap_key`(`_expPlanMouseMap_key`), 
INDEX `_expTest_key`(`_expTest_key`), 
INDEX `ExpPlanMouseMapExpTestPlanMap`(`_expPlanMouseMap_key`), 
INDEX `ExpTestExpTestPlanMap`(`_expTest_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Gene`
(`_gene_key` INTEGER NOT NULL DEFAULT 0, 
 `geneSymbol` VARCHAR (32) NULL, 
 `labSymbol` VARCHAR (32) NOT NULL, 
 `geneClass` VARCHAR (16) NULL, 
 `chromosome` VARCHAR (2) NULL, 
 `cM` REAL NULL, 
 `megabase` FLOAT NULL DEFAULT 0, 
 `comment` LONGTEXT NULL, 
UNIQUE INDEX `_gene_key`(`_gene_key`), 
UNIQUE INDEX `labSymbol`(`labSymbol`), 
PRIMARY KEY (`_gene_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Genotype`
(`_genotype_key` INTEGER NOT NULL DEFAULT 0, 
 `_mouse_key` INTEGER NOT NULL, 
 `_gene_key` INTEGER NOT NULL, 
 `allele1` VARCHAR (8) NOT NULL, 
 `all1Conf` TINYINT(1) NOT NULL DEFAULT 0, 
 `allele2` VARCHAR (8) NOT NULL, 
 `all2Conf` TINYINT(1) NOT NULL DEFAULT 0, 
 `genoPage` VARCHAR (16) NOT NULL, 
 `sampleLocation` VARCHAR (16) NULL, 
 `gtDate` DATETIME NULL, 
 `comment` LONGTEXT NULL, 
UNIQUE INDEX `_genotype_key`(`_genotype_key`), 
PRIMARY KEY (`_genotype_key`), 
INDEX `{73B83876-BA68-47FE-B451-5E17BA3319D9}`(`_gene_key`), 
INDEX `{A63318B2-02AC-447D-9603-AD964D09C53D}`(`_mouse_key`), 
INDEX `allele1`(`allele1`), 
INDEX `allele2`(`allele2`), 
INDEX `ID#`(`_mouse_key`), 
INDEX `Marker`(`_gene_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`LifeStatus`
(`lifeStatus` VARCHAR (2) NULL, 
 `description` VARCHAR (64) NULL, 
 `exitStatus` TINYINT(1) NOT NULL, 
UNIQUE INDEX `lifeStatus`(`lifeStatus`), 
PRIMARY KEY (`lifeStatus`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Litter`
(`_litter_key` INTEGER NOT NULL, 
 `_mating_key` INTEGER NOT NULL, 
 `litterID` VARCHAR (16) NOT NULL, 
 `totalBorn` SMALLINT NOT NULL DEFAULT 0, 
 `birthDate` DATETIME NOT NULL, 
 `numFemale` SMALLINT NULL DEFAULT NULL, 
 `numMale` SMALLINT NULL DEFAULT NULL, 
 `weanDate` DATETIME NULL, 
 `tagDate` DATETIME NULL, 
 `status` VARCHAR (1) NOT NULL DEFAULT "A", 
 `comment` LONGTEXT NULL, 
UNIQUE INDEX `cross#`(`_litter_key`), 
UNIQUE INDEX `litterID`(`litterID`), 
PRIMARY KEY (`_litter_key`), 
INDEX `{E03DA0F7-AFFB-4271-9823-C475C6A9D205}`(`_mating_key`), 
INDEX `matingLink`(`_mating_key`), 
INDEX `numFemale`(`numFemale`), 
INDEX `numMale`(`numMale`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`LitterSample`
(`_litterSample_key` INTEGER NOT NULL, 
 `_litter_key` INTEGER NOT NULL, 
 `_sample_key` INTEGER NOT NULL, 
PRIMARY KEY (`_litterSample_key`), 
INDEX `_litter_key`(`_litter_key`), 
INDEX `_litterSample_key`(`_litterSample_key`), 
INDEX `LitterLitterSample`(`_litter_key`), 
INDEX `LitterSample_sample_key`(`_sample_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Location`
(`_location_key` INTEGER NOT NULL DEFAULT 0, 
 `_storage_key` INTEGER NOT NULL DEFAULT 0, 
 `_locationType_key` INTEGER NOT NULL DEFAULT 0, 
UNIQUE INDEX `_location_key`(`_location_key`), 
PRIMARY KEY (`_location_key`), 
INDEX `cv_LocationTypeLocation`(`_locationType_key`), 
INDEX `Location_locationDetail_key`(`_locationType_key`), 
INDEX `Location_storage_key`(`_storage_key`), 
INDEX `StorageLocation`(`_storage_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Mating`
(`_mating_key` INTEGER NOT NULL, 
 `_dam1_key` INTEGER NOT NULL, 
 `_dam2_key` INTEGER NULL DEFAULT NULL, 
 `_sire_key` INTEGER NOT NULL, 
 `_strain_key` INTEGER NOT NULL, 
 `matingID` INTEGER NOT NULL, 
 `suggestedPenID` INTEGER NULL, 
 `weanTime` TINYINT(1) NOT NULL DEFAULT 0, 
 `matingDate` DATETIME NULL DEFAULT NULL, 
 `retiredDate` DATETIME NULL DEFAULT NULL, 
 `generation` VARCHAR (16) NOT NULL, 
 `owner` VARCHAR (8) NOT NULL, 
 `weanNote` VARCHAR (64) NULL, 
 `needsTyping` TINYINT(1) NOT NULL DEFAULT 0, 
 `comment` VARCHAR (255) NULL, 
 `proposedDiet` VARCHAR (32) NULL, 
 `proposedRetireDate` DATETIME NULL, 
 `proposedRetireD1Diet` VARCHAR (32) NULL, 
 `proposedRetireD2Diet` VARCHAR (32) NULL, 
 `proposedRetireSDiet` VARCHAR (32) NULL, 
 `proposedRetireD1BrStatus` VARCHAR (1) NULL, 
 `proposedRetireD2BrStatus` VARCHAR (1) NULL, 
 `proposedRetireSBrStatus` VARCHAR (1) NULL, 
 `proposedRetireD1LfStatus` VARCHAR (1) NULL, 
 `proposedRetireD2LfStatus` VARCHAR (1) NULL, 
 `proposedRetireSLfStatus` VARCHAR (1) NULL, 
 `proposedRetirePenStatus` VARCHAR (1) NULL, 
 `suggestedFirstLitterNum` INTEGER NULL DEFAULT 0, 
UNIQUE INDEX `matingID`(`matingID`), 
PRIMARY KEY (`_mating_key`), 
INDEX `{069800AF-BBC3-474A-AF69-31C8CFA0754F}`(`_strain_key`), 
INDEX `dam1`(`_dam1_key`), 
INDEX `dam2`(`_dam2_key`), 
INDEX `gen`(`generation`), 
INDEX `owner`(`owner`), 
INDEX `sire`(`_sire_key`), 
INDEX `strain`(`_strain_key`), 
INDEX `suggestedFirstLitterNum`(`suggestedFirstLitterNum`), 
INDEX `suggestedPenID`(`suggestedPenID`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`MatingSample`
(`_matingSample_key` INTEGER NOT NULL, 
 `_sample_key` INTEGER NOT NULL, 
 `_mating_key` INTEGER NOT NULL, 
PRIMARY KEY (`_matingSample_key`), 
INDEX `_mating_key`(`_mating_key`), 
INDEX `_matingSample_key`(`_matingSample_key`), 
INDEX `MatingMatingSample`(`_mating_key`), 
INDEX `MatingSample_sample_key`(`_sample_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Mouse`
(`_mouse_key` INTEGER NOT NULL, 
 `_litter_key` INTEGER NULL DEFAULT NULL, 
 `_strain_key` INTEGER NOT NULL, 
 `_pen_key` INTEGER NOT NULL, 
 `ID` VARCHAR (16) NOT NULL, 
 `newTag` VARCHAR (16) NULL DEFAULT NULL, 
 `birthDate` DATETIME NOT NULL, 
 `exitDate` DATETIME NULL, 
 `cod` VARCHAR (32) NULL, 
 `codNotes` VARCHAR (255) NULL, 
 `generation` VARCHAR (16) NOT NULL, 
 `sex` VARCHAR (1) NOT NULL DEFAULT '-', 
 `lifeStatus` VARCHAR (2) NOT NULL DEFAULT 'A', 
 `breedingStatus` VARCHAR (1) NOT NULL DEFAULT 'V', 
 `coatColor` VARCHAR (8) NULL DEFAULT NULL, 
 `diet` VARCHAR (32) NULL DEFAULT NULL, 
 `owner` VARCHAR (8) NOT NULL, 
 `origin` VARCHAR (16) NOT NULL DEFAULT "SJ", 
 `protocol` VARCHAR (32) NULL, 
 `comment` VARCHAR (255) NULL, 
 `sampleVialID` VARCHAR (32) NULL, 
 `sampleVialTagPosition` VARCHAR (32) NULL, 
UNIQUE INDEX `_mouse_key`(`_mouse_key`), 
UNIQUE INDEX `ID`(`ID`), 
UNIQUE INDEX `newTag`(`newTag`), 
PRIMARY KEY (`_mouse_key`), 
INDEX `{467270A5-EBD6-4219-B104-7D65BFEBED51}`(`_litter_key`), 
INDEX `{D71AC749-0B32-4A7A-8D62-E3E419545838}`(`_strain_key`), 
INDEX `{E9BF7F60-A333-4731-B500-BB51EB48B1C9}`(`_pen_key`), 
INDEX `Generation`(`generation`), 
INDEX `lifeStatus`(`lifeStatus`), 
INDEX `litter`(`_litter_key`), 
INDEX `owner`(`owner`), 
INDEX `Pen#`(`_pen_key`), 
INDEX `StrainKey`(`_strain_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`MouseSample`
(`_mouseSample_key` INTEGER NOT NULL, 
 `_mouse_key` INTEGER NOT NULL, 
 `_sample_key` INTEGER NOT NULL, 
PRIMARY KEY (`_mouseSample_key`), 
INDEX `_mouse_key`(`_mouse_key`), 
INDEX `_mouseSample_key`(`_mouseSample_key`), 
INDEX `MouseMouseSample`(`_mouse_key`), 
INDEX `MouseSample_sample_key`(`_sample_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`MouseUsage`
(`_usage_key` INTEGER NOT NULL DEFAULT 0, 
 `_mouse_key` INTEGER NOT NULL, 
 `use` VARCHAR (16) NOT NULL, 
 `useAge` REAL NULL, 
 `projectedDate` DATETIME NULL, 
 `actualDate` DATETIME NULL, 
 `done` TINYINT(1) NOT NULL DEFAULT 0, 
 `comment` VARCHAR (255) NULL, 
 `D1` VARCHAR (128) NULL, 
 `D2` VARCHAR (128) NULL, 
 `D3` VARCHAR (128) NULL, 
 `D4` VARCHAR (128) NULL, 
 `D5` VARCHAR (128) NULL, 
 `D6` VARCHAR (128) NULL, 
 `D7` VARCHAR (128) NULL, 
 `D8` VARCHAR (128) NULL, 
 `D9` VARCHAR (128) NULL, 
 `D10` VARCHAR (128) NULL, 
UNIQUE INDEX `_usage_key`(`_usage_key`), 
PRIMARY KEY (`_usage_key`), 
INDEX `_mouse_key`(`_mouse_key`), 
INDEX `{8C69793D-CB9B-481F-88F2-AA970EA090D8}`(`_mouse_key`), 
INDEX `useCode`(`use`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Owner`
(`_owner_key` INTEGER NOT NULL DEFAULT 0, 
 `owner` VARCHAR (8) NOT NULL, 
UNIQUE INDEX `_owner_key`(`_owner_key`), 
UNIQUE INDEX `owner`(`owner`), 
PRIMARY KEY (`_owner_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`PenGroup`
(`_pen_key` INTEGER NOT NULL, 
 `penID` INTEGER NOT NULL, 
 `room` VARCHAR (8) NULL, 
 `penStatus` TINYINT(1) NOT NULL DEFAULT 0, 
 `beginDate` DATETIME NOT NULL, 
 `healthLevel` SMALLINT NOT NULL, 
UNIQUE INDEX `_pen_key`(`_pen_key`), 
UNIQUE INDEX `Pen#`(`penID`), 
PRIMARY KEY (`_pen_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Sample`
(`_sample_key` INTEGER NOT NULL, 
 `_parentSample_key` INTEGER NOT NULL, 
 `age` FLOAT NOT NULL, 
 `_timeUnit_key` INTEGER NOT NULL, 
 `_epoch_key` INTEGER NOT NULL, 
 `harvestMethod` VARCHAR (32) NULL, 
 `description` LONGTEXT NOT NULL, 
 `weight` FLOAT NOT NULL, 
 `_weightUnit_key` INTEGER NOT NULL, 
 `_sampleType_key` INTEGER NOT NULL, 
 `sampleDate` DATETIME NOT NULL, 
 `_sampleDateType_key` INTEGER NOT NULL DEFAULT 0, 
 `_strain_key` INTEGER NOT NULL, 
 `owner` VARCHAR (8) NOT NULL, 
 `SampleID` VARCHAR (32) NULL, 
 `SourceType` INTEGER NULL DEFAULT 0, 
UNIQUE INDEX `Sample_sample_key`(`_sample_key`), 
UNIQUE INDEX `SampleID`(`SampleID`), 
PRIMARY KEY (`_sample_key`), 
INDEX `_parentSample_key`(`_parentSample_key`), 
INDEX `_strain_key`(`_strain_key`), 
INDEX `cv_EpochSample`(`_epoch_key`), 
INDEX `cv_SampleDateTypeSample`(`_sampleDateType_key`), 
INDEX `cv_SampleTypeSample`(`_sampleType_key`), 
INDEX `cv_TimeUnitSample`(`_timeUnit_key`), 
INDEX `cv_WeightUnitSample`(`_weightUnit_key`), 
INDEX `Sample_epoch_key`(`_epoch_key`), 
INDEX `Sample_harvestMethod_key`(`harvestMethod`), 
INDEX `Sample_sampleDateType_key`(`_sampleDateType_key`), 
INDEX `Sample_sampleType_key`(`_sampleType_key`), 
INDEX `Sample_timeUnit_key`(`_timeUnit_key`), 
INDEX `Sample_weightUnit_key`(`_weightUnit_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Secretary`
(`_secretary_key` INTEGER NOT NULL DEFAULT 0, 
 `_owner_key` INTEGER NOT NULL DEFAULT 0, 
 `secretary` VARCHAR (8) NOT NULL, 
UNIQUE INDEX `_secretary_key`(`_secretary_key`), 
PRIMARY KEY (`_secretary_key`), 
INDEX `_owner_key`(`_owner_key`), 
INDEX `{10F06FC7-4462-481A-8E77-9DDA2FD373AD}`(`_owner_key`), 
INDEX `secretary`(`secretary`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`SNPLoader`
(`_snp_loader_key` INTEGER NULL DEFAULT 0, 
 `status_Description` VARCHAR (255) NULL, 
 `status` TINYINT(1) NULL DEFAULT 1, 
 `sequence` INTEGER NULL DEFAULT 0, 
PRIMARY KEY (`_snp_loader_key`), 
INDEX `_snp_loader_key`(`_snp_loader_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Storage`
(`_storage_key` INTEGER NOT NULL DEFAULT 0, 
 `_sample_key` INTEGER NOT NULL, 
 `_sampleStatus_key` INTEGER NOT NULL DEFAULT 0, 
 `_preservationMethod_key` INTEGER NULL DEFAULT 0, 
 `_preservationType_key` INTEGER NULL DEFAULT 0, 
 `_preservationDetail_key` INTEGER NULL DEFAULT 0, 
UNIQUE INDEX `Storage_storage_key`(`_storage_key`), 
PRIMARY KEY (`_storage_key`), 
INDEX `_preservationDetail_key`(`_preservationDetail_key`), 
INDEX `_preservationMethod_key`(`_preservationMethod_key`), 
INDEX `_preservationType_key`(`_preservationType_key`), 
INDEX `cv_SampleStatusStorage`(`_sampleStatus_key`), 
INDEX `SampleStorage`(`_sample_key`), 
INDEX `Storage_sample_key`(`_sample_key`), 
INDEX `Storage_sampleStatus_key`(`_sampleStatus_key`)) ENGINE =INNODB CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS jpt_ut.`Strain`
(`_strain_key` INTEGER NOT NULL, 
 `strainName` VARCHAR (64) NOT NULL, 
 `nickname` VARCHAR (128) NULL, 
 `formalName` VARCHAR (128) NULL, 
 `strainStatus` VARCHAR (1) NOT NULL DEFAULT "A", 
 `tagMin` VARCHAR (16) NULL, 
 `tagMax` VARCHAR (16) NULL, 
 `lastTag` VARCHAR (16) NULL, 
 `jrNum` INTEGER NULL DEFAULT 0, 
 `feNumEmbryos` SMALLINT NOT NULL DEFAULT 0, 
 `feMaxGen` VARCHAR (16) NULL, 
 `fsNumMales` SMALLINT NOT NULL DEFAULT 0, 
 `fsMaxGen` VARCHAR (16) NULL, 
 `foNumFemales` SMALLINT NOT NULL DEFAULT 0, 
 `foMaxGen` VARCHAR (16) NULL, 
 `section` VARCHAR (32) NULL, 
 `cardColor` VARCHAR (32) NULL, 
 `strainType` VARCHAR (32) NULL, 
 `comment` LONGTEXT NULL, 
PRIMARY KEY (`_strain_key`), 
UNIQUE INDEX `StrainName`(`strainName`), 
INDEX `jrNum`(`jrNum`), 
INDEX `StrainKey`(`_strain_key`)) ENGINE =INNODB CHARACTER SET utf8;


LOAD DATA LOCAL INFILE 'Allele.txt' INTO TABLE jpt_ut.Allele FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_allele_key`, `_gene_key`, `allele`, `genericAlleleGeneClass`);;


LOAD DATA LOCAL INFILE 'ApprovedStrainRegistry.txt' INTO TABLE jpt_ut.ApprovedStrainRegistry FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_approvedStrain_key`, `_damStrain_key`, `_litterStrain_key`, `_owner_key`, `_sireStrain_key`, `active`);;


LOAD DATA LOCAL INFILE 'cv_CauseOfDeath.txt' INTO TABLE jpt_ut.cv_CauseOfDeath FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`cod`, `description`);;


LOAD DATA LOCAL INFILE 'cv_CoatColor.txt' INTO TABLE jpt_ut.cv_CoatColor FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`coatColor`, `description`);;


LOAD DATA LOCAL INFILE 'cv_Diet.txt' INTO TABLE jpt_ut.cv_Diet FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`diet`, `dietDescription`);;


LOAD DATA LOCAL INFILE 'cv_Epoch.txt' INTO TABLE jpt_ut.cv_Epoch FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_epoch_key`, `epoch`);;


LOAD DATA LOCAL INFILE 'cv_ExpSampleLocation.txt' INTO TABLE jpt_ut.cv_ExpSampleLocation FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`location`);;


LOAD DATA LOCAL INFILE 'cv_ExpStatus.txt' INTO TABLE jpt_ut.cv_ExpStatus FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`status`);;


LOAD DATA LOCAL INFILE 'cv_FieldOfStudy.txt' INTO TABLE jpt_ut.cv_FieldOfStudy FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`fieldOfStudyName`);;


LOAD DATA LOCAL INFILE 'cv_GeneClass.txt' INTO TABLE jpt_ut.cv_GeneClass FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`Description`, `GeneClass`);;


LOAD DATA LOCAL INFILE 'cv_Generation.txt' INTO TABLE jpt_ut.cv_Generation FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`generation`);;


LOAD DATA LOCAL INFILE 'cv_GenotypeSpecimenLocation.txt' INTO TABLE jpt_ut.cv_GenotypeSpecimenLocation FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`genotypeSpecimenLocation`);;


LOAD DATA LOCAL INFILE 'cv_HarvestMethod.txt' INTO TABLE jpt_ut.cv_HarvestMethod FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`harvestMethod`);;


LOAD DATA LOCAL INFILE 'cv_Keywords.txt' INTO TABLE jpt_ut.cv_Keywords FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`keyword`);;


LOAD DATA LOCAL INFILE 'cv_LocationType.txt' INTO TABLE jpt_ut.cv_LocationType FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_locationType_key`, `_storageFacility_key`, `locationDetail`, `locationType`, `locationTypeRef`);;


LOAD DATA LOCAL INFILE 'cv_MatingCardNotes.txt' INTO TABLE jpt_ut.cv_MatingCardNotes FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`matingNotes`);;


LOAD DATA LOCAL INFILE 'cv_MouseOrigin.txt' INTO TABLE jpt_ut.cv_MouseOrigin FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`where`);;


LOAD DATA LOCAL INFILE 'cv_MouseProtocol.txt' INTO TABLE jpt_ut.cv_MouseProtocol FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`description`, `id`);;


LOAD DATA LOCAL INFILE 'cv_MouseUse.txt' INTO TABLE jpt_ut.cv_MouseUse FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`mouseUse`, `useDescription`);;


LOAD DATA LOCAL INFILE 'cv_PreservationDetail.txt' INTO TABLE jpt_ut.cv_PreservationDetail FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_preservationDetail_key`, `_preservationMethod_key`, `preservationDetail`);;


LOAD DATA LOCAL INFILE 'cv_PreservationMethod.txt' INTO TABLE jpt_ut.cv_PreservationMethod FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_preservationMethod_key`, `_preservationType_key`, `preservationMethod`);;


LOAD DATA LOCAL INFILE 'cv_PreservationType.txt' INTO TABLE jpt_ut.cv_PreservationType FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_preservationType_key`, `_sampleClass_key`, `preservationType`);;


LOAD DATA LOCAL INFILE 'cv_Room.txt' INTO TABLE jpt_ut.cv_Room FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`room`);;


LOAD DATA LOCAL INFILE 'cv_SampleClass.txt' INTO TABLE jpt_ut.cv_SampleClass FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_sampleClass_key`, `sampleClass`);;


LOAD DATA LOCAL INFILE 'cv_SampleDateType.txt' INTO TABLE jpt_ut.cv_SampleDateType FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_sampleDateType_key`, `sampleDateType`);;


LOAD DATA LOCAL INFILE 'cv_SampleStatus.txt' INTO TABLE jpt_ut.cv_SampleStatus FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_sampleStatus_key`, `isInStorage`, `sampleStatus`);;


LOAD DATA LOCAL INFILE 'cv_SampleType.txt' INTO TABLE jpt_ut.cv_SampleType FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_sampleClass_key`, `_sampleType_key`, `sampleType`);;


LOAD DATA LOCAL INFILE 'cv_StorageFacility.txt' INTO TABLE jpt_ut.cv_StorageFacility FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_storageFacility_key`, `storageFacility`);;


LOAD DATA LOCAL INFILE 'cv_StrainType.txt' INTO TABLE jpt_ut.cv_StrainType FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`strainType`);;


LOAD DATA LOCAL INFILE 'cv_TestStatus.txt' INTO TABLE jpt_ut.cv_TestStatus FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`testStatus`);;


LOAD DATA LOCAL INFILE 'cv_TimeUnit.txt' INTO TABLE jpt_ut.cv_TimeUnit FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_timeUnit_key`, `abbreviation`, `minutesPerUnit`, `timeUnit`);;


LOAD DATA LOCAL INFILE 'cv_WeightUnit.txt' INTO TABLE jpt_ut.cv_WeightUnit FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_weightUnit_key`, `weightUnit`);;


LOAD DATA LOCAL INFILE 'DbFormPrivileges.txt' INTO TABLE jpt_ut.DbFormPrivileges FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`completeFormName`, `description`, `formName`, `privilegeLevel`);;


LOAD DATA LOCAL INFILE 'Dbinfo.txt' INTO TABLE jpt_ut.Dbinfo FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`dbVers`, `maxAutoLitterNum`, `maxAutoMouseID`, `maxPenID`, `releaseDate`, `releaseNum`, `versDate`);;


LOAD DATA LOCAL INFILE 'DbRelationships.txt' INTO TABLE jpt_ut.DbRelationships FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`ccolumn`, `grbit`, `icolumn`, `szColumn`, `szObject`, `szReferencedColumn`, `szReferencedObject`, `szRelationship`);;


LOAD DATA LOCAL INFILE 'DbSetup.txt' INTO TABLE jpt_ut.DbSetup FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`MTSValue`, `MTSVar`, `MTSVarDescription`);;


LOAD DATA LOCAL INFILE 'ExpData.txt' INTO TABLE jpt_ut.ExpData FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expData_key`, `_expDataDesc_key`, `_expTest_key`, `_sampleStorHISTO_key`, `_sampleStorLIVE_key`, `_sampleStorMOLBIO_key`, `_specimen_key`, `abnormalData`, `age`, `d1`, `d10`, `d11`, `d12`, `d13`, `d14`, `d15`, `d16`, `d17`, `d18`, `d19`, `d2`, `d20`, `d21`, `d22`, `d23`, `d24`, `d25`, `d26`, `d27`, `d28`, `d29`, `d3`, `d30`, `d4`, `d5`, `d6`, `d7`, `d8`, `d9`, `dataID`, `expDate`, `owner`, `specimen_type`);;


LOAD DATA LOCAL INFILE 'ExpDataDefault.txt' INTO TABLE jpt_ut.ExpDataDefault FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expDataDefault_key`, `_expDataDescriptor_key`, `comment`, `d1_default`, `d10_default`, `d11_default`, `d12_default`, `d13_default`, `d14_default`, `d15_default`, `d16_default`, `d17_default`, `d18_default`, `d19_default`, `d2_default`, `d20_default`, `d21_default`, `d22_default`, `d23_default`, `d24_default`, `d25_default`, `d26_default`, `d27_default`, `d28_default`, `d29_default`, `d3_default`, `d30_default`, `d4_default`, `d5_default`, `d6_default`, `d7_default`, `d8_default`, `d9_default`, `defaultName`);;


LOAD DATA LOCAL INFILE 'ExpDataDescriptor.txt' INTO TABLE jpt_ut.ExpDataDescriptor FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expDataDescriptor_key`, `d1_caption`, `d1_dataFormat`, `d1_fieldDescription`, `d1_maxValue`, `d1_minValue`, `d1_required`, `d10_caption`, `d10_dataFormat`, `d10_fieldDescription`, `d10_maxValue`, `d10_minValue`, `d10_required`, `d11_caption`, `d11_dataFormat`, `d11_fieldDescription`, `d11_maxValue`, `d11_minValue`, `d11_required`, `d12_caption`, `d12_dataFormat`, `d12_fieldDescription`, `d12_maxValue`, `d12_minValue`, `d12_required`, `d13_caption`, `d13_dataFormat`, `d13_fieldDescription`, `d13_maxValue`, `d13_minValue`, `d13_required`, `d14_caption`, `d14_dataFormat`, `d14_fieldDescription`, `d14_maxValue`, `d14_minValue`, `d14_required`, `d15_caption`, `d15_dataFormat`, `d15_fieldDescription`, `d15_maxValue`, `d15_minValue`, `d15_required`, `d16_caption`, `d16_dataFormat`, `d16_fieldDescription`, `d16_maxValue`, `d16_minValue`, `d16_required`, `d17_caption`, `d17_dataFormat`, `d17_fieldDescription`, `d17_maxValue`, `d17_minValue`, `d17_required`, `d18_caption`, `d18_dataFormat`, `d18_fieldDescription`, `d18_maxValue`, `d18_minValue`, `d18_required`, `d19_caption`, `d19_dataFormat`, `d19_fieldDescription`, `d19_maxValue`, `d19_minValue`, `d19_required`, `d2_caption`, `d2_dataFormat`, `d2_fieldDescription`, `d2_maxValue`, `d2_minValue`, `d2_required`, `d20_caption`, `d20_dataFormat`, `d20_fieldDescription`, `d20_maxValue`, `d20_minValue`, `d20_required`, `d21_caption`, `d21_dataFormat`, `d21_fieldDescription`, `d21_maxValue`, `d21_minValue`, `d21_required`, `d22_caption`, `d22_dataFormat`, `d22_fieldDescription`, `d22_maxValue`, `d22_minValue`, `d22_required`, `d23_caption`, `d23_dataFormat`, `d23_fieldDescription`, `d23_maxValue`, `d23_minValue`, `d23_required`, `d24_caption`, `d24_dataFormat`, `d24_fieldDescription`, `d24_maxValue`, `d24_minValue`, `d24_required`, `d25_caption`, `d25_dataFormat`, `d25_fieldDescription`, `d25_maxValue`, `d25_minValue`, `d25_required`, `d26_caption`, `d26_dataFormat`, `d26_fieldDescription`, `d26_maxValue`, `d26_minValue`, `d26_required`, `d27_caption`, `d27_dataFormat`, `d27_fieldDescription`, `d27_maxValue`, `d27_minValue`, `d27_required`, `d28_caption`, `d28_dataFormat`, `d28_fieldDescription`, `d28_maxValue`, `d28_minValue`, `d28_required`, `d29_caption`, `d29_dataFormat`, `d29_fieldDescription`, `d29_maxValue`, `d29_minValue`, `d29_required`, `d3_caption`, `d3_dataFormat`, `d3_fieldDescription`, `d3_maxValue`, `d3_minValue`, `d3_required`, `d30_caption`, `d30_dataFormat`, `d30_fieldDescription`, `d30_maxValue`, `d30_minValue`, `d30_required`, `d4_caption`, `d4_dataFormat`, `d4_fieldDescription`, `d4_maxValue`, `d4_minValue`, `d4_required`, `d5_caption`, `d5_dataFormat`, `d5_fieldDescription`, `d5_maxValue`, `d5_minValue`, `d5_required`, `d6_caption`, `d6_dataFormat`, `d6_fieldDescription`, `d6_maxValue`, `d6_minValue`, `d6_required`, `d7_caption`, `d7_dataFormat`, `d7_fieldDescription`, `d7_maxValue`, `d7_minValue`, `d7_required`, `d8_caption`, `d8_dataFormat`, `d8_fieldDescription`, `d8_maxValue`, `d8_minValue`, `d8_required`, `d9_caption`, `d9_dataFormat`, `d9_fieldDescription`, `d9_maxValue`, `d9_minValue`, `d9_required`, `testType`, `testTypeNotes`);;


LOAD DATA LOCAL INFILE 'ExpPlan.txt' INTO TABLE jpt_ut.ExpPlan FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expPlan_key`, `expNotes`, `expPlanName`, `expStatus`, `fieldOfStudy`, `keyword1`, `keyword2`, `keyword3`, `keyword4`, `keyword5`, `owner`, `planID`);;


LOAD DATA LOCAL INFILE 'ExpPlanMouseMap.txt' INTO TABLE jpt_ut.ExpPlanMouseMap FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expPlan_key`, `_expPlanMouseMap_key`, `_mouse_key`);;


LOAD DATA LOCAL INFILE 'ExpTest.txt' INTO TABLE jpt_ut.ExpTest FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expDataDescriptor_key`, `_expPlan_key`, `_expTest_key`, `d1_defaultValue`, `d10_defaultValue`, `d11_defaultValue`, `d12_defaultValue`, `d13_defaultValue`, `d14_defaultValue`, `d15_defaultValue`, `d16_defaultValue`, `d17_defaultValue`, `d18_defaultValue`, `d19_defaultValue`, `d2_defaultValue`, `d20_defaultValue`, `d21_defaultValue`, `d22_defaultValue`, `d23_defaultValue`, `d24_defaultValue`, `d25_defaultValue`, `d26_defaultValue`, `d27_defaultValue`, `d28_defaultValue`, `d29_defaultValue`, `d3_defaultValue`, `d30_defaultValue`, `d4_defaultValue`, `d5_defaultValue`, `d6_defaultValue`, `d7_defaultValue`, `d8_defaultValue`, `d9_defaultValue`, `numMice`, `proposedCrossRef`, `proposedExpSampleLoc`, `proposedHarvestDate`, `proposedHarvestMethod`, `proposedMaxAge`, `proposedMinAge`, `proposedPresDetail`, `proposedPresMethod`, `proposedPresType`, `proposedSampleClass`, `proposedSampleType`, `proposedTestDate`, `testID`, `testName`, `testStatus`);;


LOAD DATA LOCAL INFILE 'ExpTestPlanMap.txt' INTO TABLE jpt_ut.ExpTestPlanMap FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_expPlanMouseMap_key`, `_expTest_key`, `_exptestplan_key`);;


LOAD DATA LOCAL INFILE 'Gene.txt' INTO TABLE jpt_ut.Gene FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_gene_key`, `chromosome`, `cM`, `comment`, `geneClass`, `geneSymbol`, `labSymbol`, `megabase`);;


LOAD DATA LOCAL INFILE 'Genotype.txt' INTO TABLE jpt_ut.Genotype FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_gene_key`, `_genotype_key`, `_mouse_key`, `all1Conf`, `all2Conf`, `allele1`, `allele2`, `comment`, `genoPage`, `gtDate`, `sampleLocation`);;


LOAD DATA LOCAL INFILE 'LifeStatus.txt' INTO TABLE jpt_ut.LifeStatus FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`description`, `exitStatus`, `lifeStatus`);;


LOAD DATA LOCAL INFILE 'Litter.txt' INTO TABLE jpt_ut.Litter FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_litter_key`, `_mating_key`, `birthDate`, `comment`, `litterID`, `numFemale`, `numMale`, `status`, `tagDate`, `totalBorn`, `weanDate`);;


LOAD DATA LOCAL INFILE 'LitterSample.txt' INTO TABLE jpt_ut.LitterSample FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_litter_key`, `_litterSample_key`, `_sample_key`);;


LOAD DATA LOCAL INFILE 'Location.txt' INTO TABLE jpt_ut.Location FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_location_key`, `_locationType_key`, `_storage_key`);;


LOAD DATA LOCAL INFILE 'Mating.txt' INTO TABLE jpt_ut.Mating FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_dam1_key`, `_dam2_key`, `_mating_key`, `_sire_key`, `_strain_key`, `comment`, `generation`, `matingDate`, `matingID`, `needsTyping`, `owner`, `proposedDiet`, `proposedRetireD1BrStatus`, `proposedRetireD1Diet`, `proposedRetireD1LfStatus`, `proposedRetireD2BrStatus`, `proposedRetireD2Diet`, `proposedRetireD2LfStatus`, `proposedRetireDate`, `proposedRetirePenStatus`, `proposedRetireSBrStatus`, `proposedRetireSDiet`, `proposedRetireSLfStatus`, `retiredDate`, `suggestedFirstLitterNum`, `suggestedPenID`, `weanNote`, `weanTime`);;


LOAD DATA LOCAL INFILE 'MatingSample.txt' INTO TABLE jpt_ut.MatingSample FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_mating_key`, `_matingSample_key`, `_sample_key`);;


LOAD DATA LOCAL INFILE 'Mouse.txt' INTO TABLE jpt_ut.Mouse FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_litter_key`, `_mouse_key`, `_pen_key`, `_strain_key`, `birthDate`, `breedingStatus`, `coatColor`, `cod`, `codNotes`, `comment`, `diet`, `exitDate`, `generation`, `ID`, `lifeStatus`, `newTag`, `origin`, `owner`, `protocol`, `sampleVialID`, `sampleVialTagPosition`, `sex`);;


LOAD DATA LOCAL INFILE 'MouseSample.txt' INTO TABLE jpt_ut.MouseSample FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_mouse_key`, `_mouseSample_key`, `_sample_key`);;


LOAD DATA LOCAL INFILE 'MouseUsage.txt' INTO TABLE jpt_ut.MouseUsage FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_mouse_key`, `_usage_key`, `actualDate`, `comment`, `D1`, `D10`, `D2`, `D3`, `D4`, `D5`, `D6`, `D7`, `D8`, `D9`, `done`, `projectedDate`, `use`, `useAge`);;


LOAD DATA LOCAL INFILE 'Owner.txt' INTO TABLE jpt_ut.Owner FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_owner_key`, `owner`);;


LOAD DATA LOCAL INFILE 'PenGroup.txt' INTO TABLE jpt_ut.PenGroup FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_pen_key`, `beginDate`, `healthLevel`, `penID`, `penStatus`, `room`);;


LOAD DATA LOCAL INFILE 'Sample.txt' INTO TABLE jpt_ut.Sample FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_epoch_key`, `_parentSample_key`, `_sample_key`, `_sampleDateType_key`, `_sampleType_key`, `_strain_key`, `_timeUnit_key`, `_weightUnit_key`, `age`, `description`, `harvestMethod`, `owner`, `sampleDate`, `SampleID`, `SourceType`, `weight`);;


LOAD DATA LOCAL INFILE 'Secretary.txt' INTO TABLE jpt_ut.Secretary FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_owner_key`, `_secretary_key`, `secretary`);;


LOAD DATA LOCAL INFILE 'SNPLoader.txt' INTO TABLE jpt_ut.SNPLoader FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_snp_loader_key`, `sequence`, `status`, `status_Description`);;


LOAD DATA LOCAL INFILE 'Storage.txt' INTO TABLE jpt_ut.Storage FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_preservationDetail_key`, `_preservationMethod_key`, `_preservationType_key`, `_sample_key`, `_sampleStatus_key`, `_storage_key`);;


LOAD DATA LOCAL INFILE 'Strain.txt' INTO TABLE jpt_ut.Strain FIELDS TERMINATED BY ',' ESCAPED BY '\\' LINES TERMINATED BY 0x0d0a (`_strain_key`, `cardColor`, `comment`, `feMaxGen`, `feNumEmbryos`, `foMaxGen`, `foNumFemales`, `formalName`, `fsMaxGen`, `fsNumMales`, `jrNum`, `lastTag`, `nickname`, `section`, `strainName`, `strainStatus`, `strainType`, `tagMax`, `tagMin`);;


ALTER TABLE `Mating` ADD FOREIGN KEY (`_strain_key`) REFERENCES `Strain`(`_strain_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Secretary` ADD FOREIGN KEY (`_owner_key`) REFERENCES `Owner`(`_owner_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpData` ADD FOREIGN KEY (`_expDataDesc_key`) REFERENCES `ExpDataDescriptor`(`_expDataDescriptor_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpTest` ADD FOREIGN KEY (`_expPlan_key`) REFERENCES `ExpPlan`(`_expPlan_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpData` ADD FOREIGN KEY (`_expTest_key`) REFERENCES `ExpTest`(`_expTest_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Mouse` ADD FOREIGN KEY (`_litter_key`) REFERENCES `Litter`(`_litter_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Mating` ADD FOREIGN KEY (`_sire_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpData` ADD FOREIGN KEY (`_specimen_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Genotype` ADD FOREIGN KEY (`_gene_key`) REFERENCES `Gene`(`_gene_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpDataDefault` ADD FOREIGN KEY (`_expDataDescriptor_key`) REFERENCES `ExpDataDescriptor`(`_expDataDescriptor_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `MouseUsage` ADD FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Genotype` ADD FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Mouse` ADD FOREIGN KEY (`_strain_key`) REFERENCES `Strain`(`_strain_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Litter` ADD FOREIGN KEY (`_mating_key`) REFERENCES `Mating`(`_mating_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Mouse` ADD FOREIGN KEY (`_pen_key`) REFERENCES `PenGroup`(`_pen_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpTest` ADD FOREIGN KEY (`_expDataDescriptor_key`) REFERENCES `ExpDataDescriptor`(`_expDataDescriptor_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpPlanMouseMap` ADD FOREIGN KEY (`_expPlan_key`) REFERENCES `ExpPlan`(`_expPlan_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpTestPlanMap` ADD FOREIGN KEY (`_expPlanMouseMap_key`) REFERENCES `ExpPlanMouseMap`(`_expPlanMouseMap_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpTestPlanMap` ADD FOREIGN KEY (`_expTest_key`) REFERENCES `ExpTest`(`_expTest_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Sample` ADD FOREIGN KEY (`_epoch_key`) REFERENCES `cv_Epoch`(`_epoch_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `ExpPlanMouseMap` ADD FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `cv_PreservationDetail` ADD FOREIGN KEY (`_preservationMethod_key`) REFERENCES `cv_PreservationMethod`(`_preservationMethod_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `cv_PreservationMethod` ADD FOREIGN KEY (`_preservationType_key`) REFERENCES `cv_PreservationType`(`_preservationType_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Storage` ADD FOREIGN KEY (`_sampleStatus_key`) REFERENCES `cv_SampleStatus`(`_sampleStatus_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Sample` ADD FOREIGN KEY (`_weightUnit_key`) REFERENCES `cv_WeightUnit`(`_weightUnit_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Sample` ADD FOREIGN KEY (`_sampleDateType_key`) REFERENCES `cv_SampleDateType`(`_sampleDateType_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `cv_PreservationType` ADD FOREIGN KEY (`_sampleClass_key`) REFERENCES `cv_SampleClass`(`_sampleClass_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Sample` ADD FOREIGN KEY (`_timeUnit_key`) REFERENCES `cv_TimeUnit`(`_timeUnit_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `cv_SampleType` ADD FOREIGN KEY (`_sampleClass_key`) REFERENCES `cv_SampleClass`(`_sampleClass_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Sample` ADD FOREIGN KEY (`_sampleType_key`) REFERENCES `cv_SampleType`(`_sampleType_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Storage` ADD FOREIGN KEY (`_sample_key`) REFERENCES `Sample`(`_sample_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `MatingSample` ADD FOREIGN KEY (`_mating_key`) REFERENCES `Mating`(`_mating_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Location` ADD FOREIGN KEY (`_storage_key`) REFERENCES `Storage`(`_storage_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `Location` ADD FOREIGN KEY (`_locationType_key`) REFERENCES `cv_LocationType`(`_locationType_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `LitterSample` ADD FOREIGN KEY (`_sample_key`) REFERENCES `Sample`(`_sample_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `LitterSample` ADD FOREIGN KEY (`_litter_key`) REFERENCES `Litter`(`_litter_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `cv_LocationType` ADD FOREIGN KEY (`_storageFacility_key`) REFERENCES `cv_StorageFacility`(`_storageFacility_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `MatingSample` ADD FOREIGN KEY (`_sample_key`) REFERENCES `Sample`(`_sample_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `MouseSample` ADD FOREIGN KEY (`_sample_key`) REFERENCES `Sample`(`_sample_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `MouseSample` ADD FOREIGN KEY (`_mouse_key`) REFERENCES `Mouse`(`_mouse_key`) ON DELETE RESTRICT ON UPDATE RESTRICT;

