-- ==================================================================
-- create_RSLIMSDatabase.sql
--
-- Created on July 21, 2009
--
-- ==================================================================
-- WARRANTY DISCLAIMER AND COPYRIGHT NOTICE
--
-- THE JACKSON LABORATORY MAKES NO REPRESENTATION ABOUT THE SUITABILITY OR
-- ACCURACY OF THIS SOFTWARE OR DATA FOR ANY PURPOSE, AND MAKES NO WARRANTIES,
-- EITHER EXPRESS OR IMPLIED, INCLUDING MERCHANTABILITY AND FITNESS FOR A
-- PARTICULAR PURPOSE OR THAT THE USE OF THIS SOFTWARE OR DATA WILL NOT
-- INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS, OR OTHER RIGHTS.
-- THE SOFTWARE AND DATA ARE PROVIDED "AS IS".
--
-- This software and data are provided to enhance knowledge and encourage
-- progress in the scientific community and are to be used only for research
-- and educational purposes. Any reproduction or use for commercial purpose is
-- prohibited without the prior express written permission of
-- the Jackson Laboratory.
--
-- Copyright  1996, 1999, 2000, 2007 by The Jackson Laboratory
-- All Rights Reserved
-- ==================================================================


-- ==================================================================
-- File name:       create_JCMSDatabase.sql
-- Date developed:  Friday, 25 February 2011
-- Purpose:         Create JCMSWeb databases
-- Overview:
--     Creates database schemas for JCMSWeb and JBOSS
--     ROOT and DBA passwords should be changed after
--     install.
-- Target DBMS : MySQL 5.1
-- Last changed by:   $Author:  $
-- Last changed date: $Date:  $
-- @author Craig Hanna
-- @version $Revision:  $
-- ==================================================================


CREATE DATABASE IF NOT EXISTS jcmsweb_db
default CHARACTER SET latin1 COLLATE latin1_swedish_ci;

CREATE DATABASE IF NOT EXISTS jcmsjboss
default CHARACTER SET latin1 COLLATE latin1_swedish_ci;

USE jcmsweb_db;

START TRANSACTION;

CREATE TABLE dbInfo(
    _dbinfo_key           INT              NOT NULL,
    majorVersion          INT              NOT NULL,
    minorVersion          INT              NOT NULL,
    bugFixVersion         INT,
    buildVersion          INT,
    releaseDate           DATETIME         NOT NULL,
    releaseType           VARCHAR(32),
    releaseNotes          VARCHAR(1024),
    databaseReleaseNum    VARCHAR(256)     NOT NULL,
    PRIMARY KEY (_dbinfo_key)
)ENGINE=INNODB
;

INSERT INTO dbInfo (_dbinfo_key, majorVersion, minorVersion, bugFixVersion, buildVersion, releaseDate, releaseType, releaseNotes, databaseReleaseNum)
VALUES (1, 0, 0, 0, 0, now(), 'Create Database', 'Create JCMSWeb_db and JBOSS schemas.', 1);

COMMIT;