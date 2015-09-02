-- ==================================================================
-- create_JCMSWebDatabase.sql
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
-- File name:       create_JCMSWebDatabase.sql
-- Date developed:  July 21, 2010
-- Purpose:         Create Reproductive Sciences database
-- Overview:
--     Creates database schemas for jcms_db, jcmsweb_db and JBOSS
--     ROOT and DBA passwords should be changed after
--     install.
-- Target DBMS : MySQL 5.1
-- Last changed by:   $Author:  $
-- Last changed date: $Date:  $
-- @author Craig Hanna
-- @version $Revision:  $
-- ==================================================================


START TRANSACTION;


-- ******************************************************************
-- Remove anonymous user accounts.
-- ******************************************************************
DELETE FROM mysql.user WHERE User = '';
FLUSH PRIVILEGES;


-- ******************************************************************
--     Setup jcms_db Database
--     JCMS application uses this account to connect to the jcms_db
--     database schema.
-- ******************************************************************
DROP USER 'jcms'@'localhost';
CREATE USER 'jcms'@'localhost' IDENTIFIED BY PASSWORD '*65A0C2FB8C999D66809145BFE3BFAC429AA363FD';

-- Need to explicitly set password for localhost even though it has
-- already been set in the Create User statement.
-- SET PASSWORD FOR 'jcms'@'localhost' = PASSWORD('jcms');

GRANT SELECT, CREATE TEMPORARY TABLES,INSERT, UPDATE
ON jcms_db.* TO 'jcms'@'localhost';


-- ******************************************************************
--     Setup SampleTracker Database
--     JCMS application uses this account to connect to the jcms_db
--     database schema.
-- ******************************************************************
-- DROP USER 'jcmsweb'@'localhost';
DROP USER 'jcmsweb'@'localhost';
CREATE USER 'jcmsweb'@'localhost' IDENTIFIED BY PASSWORD '*B714DE60E5FB410EBBEF9122F6CF3A75B37F049B';

-- Need to explicitly set password for localhost even though it has
-- already been set in the Create User statement.
-- SET PASSWORD FOR 'jcmsweb'@'localhost' = PASSWORD('jcmsweb');

GRANT SELECT,INSERT,UPDATE,DELETE
ON jcmsweb_db.* TO 'jcmsweb'@'localhost';



-- ******************************************************************
--     Setup JBOSS Database
--     Application Server uses this account to connect to the jboss
--     schema and automatically creates the tables that
--     JBOSS Application server needs to operate.
-- ******************************************************************
DROP USER 'jboss'@'localhost';
CREATE USER 'jboss'@'localhost' IDENTIFIED BY PASSWORD '*44DF74BBC93F4FDFF5CDAB3B1E0DE1C5B97114F9';

-- Need to explicitly set password for localhost even though it has
-- already been set in the Create User statement.
-- SET PASSWORD FOR 'jboss'@'localhost' = PASSWORD('jcms');

GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,INDEX,ALTER
ON jcmsjboss.* TO 'jboss'@'localhost' ;


-- ******************************************************************
--     Grant full access to schema to jcms_db, jcmsweb_db and jboss
-- ******************************************************************
GRANT ALL ON jcmsweb_db.* TO 'dba'@'localhost', 'dba'@'%.jax.org' ;
GRANT ALL ON jcms_db.* TO 'dba'@'localhost', 'dba'@'%.jax.org' ;
GRANT ALL ON jcmsjboss.* TO 'dba'@'localhost', 'dba'@'%.jax.org' ;


FLUSH PRIVILEGES;

COMMIT;

