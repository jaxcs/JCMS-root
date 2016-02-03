# JCMS-root
The Jackson Laboratory Colony Management System

The Jackson Laboratory Colony Management System (JCMS) source code is now available as an 
Open Source software product. The software is no longer in active development and 
is provided “as is.”

Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

*****************************************************************

Synopsis of the folder contents:

LatestRelease -- The subfolders contain the download files for installing the 
     JCMSWeb interface and the JCMS Access Interface. 
     The JCMS Access to MySQL Converter Tool and other helpful files are included. 
     Read through the file: WhatFileToDownload.txt for helpful information about
     what file(s) you need.

JCMS -- the MS Access Interface source and documentation

JCMS/deploy -- Installer for the current version

JCMS/dev/interface -- MS Access interface (jcms.mdb)

JCMS/dev/database -- MS Access back end (jcms_db.mdb); only useful for version 3.4

JCMS/dev/docs -- user release documents

JCMS/dev/system -- *.OCX controls used by the JCMS Access interface

JCMS/dev/PenControl -- Note: The pen control is only used in versions prior to 4.5

JCMS_DB -- contains the scripts used for installing/upgrading the MySQL database.
           After Access version 4.6 a MySQL database is required.

JCMSAccessInstaller -- the source code for the Access interface installer and upgrader

JCMSAccessInstaller/deploy -- the current installer *.msi

JCMSAccessInstaller/dev/JCMSAccessInstaller -- the source code

JCMSAccessInstaller/docs -- the technical specification documentation

JCtrls -- the added Active X controls (*.OCX) used by the JCMS Access Interface

JCtrls/deploy -- *.msi and setup.exe for installing the Active X controls

JCtrls/dev -- source code for the Active X control installer

JCMSWeb -- the JCMS web interface and installer/upgrader source code

JCMSWeb/deploy -- the installer zip file

JCMSWeb/dev/JCMSIntegrationTier -- source code

JCMSWeb/dev/JCMSMiddleTier -- source code

JCMSWeb/dev/JCMSWebTierJB6 -- source code

JCMSWeb/dev/JCMSMobile -- source code for the Android app (released) and 
                          source code for iOS app (testing incomplete and not released)
                          
JCMS Web/dev/CageCardsV1 -- source code for cage cards

JCMSWeb/dev/JCMSWebInstaller2014 -- source code for the JCMS Web installer and upgrader

JCMSWeb/dev/jbossv610 -- JBoss used by JCMS Web

JCMSWeb/dev/build

JCMSWeb/dev/buildfiles -- *.jar and *.war files plus others needed for the installer

JCMSWeb/dev/ThirdPartyLibraries -- *.jar files for added libraries

JCMSWeb/dev/DBUpgrader -- miscellaneous *.sql files used by the installer and upgrader. 
       Note: JCMS_DB contains scripts for upgrading from older versions of both the Web and Access versions.
       
JCMSWeb/docs -- user release documents

JMyC -- the converter used to migrate a MS Access back end (jcms_db.mdb) to a MySQL database

JMyC/deploy -- setup.exe and *.msi files

JMyC/dev -- source code for the converter

JMyC/docs -- technical specifications for the converter

jpt -- JCMS Pedigree Tree for use with a MS Access back end (jcms_db.mdb)
       Note: JCMS Web contains its own pedigree tree

jpt/deploy -- *.zip for installing; user documentation; *.jar files

jpt/dev -- source code for the JCMS pedigree tree

jpt/docs -- design specification for the JCMS pedigree tree
