#!/bin/bash

# =============================================================================
#   JCMS Web Installer build script.
#
#   Run this script to create a new jcmsWebInstaller.zip file.  JCMS Web 
#   Installer zip file is checked into subversion when the execution completes.
#   
#   https://cs-svn.jax.org/svn/csrepos/JCMSWeb/trunk/deploy/jcmsWebInstall.zip
#
#   Preconditions:
#   1. JCMS integration tier, middle tier, and web tier jars and war must be 
#      checked into each projects "dist" folder.  
#   2. Upgrade scripts must be up to date and checked in.  
#   3. JCMS Web Installer jar must be checked into it's project "dist" folder
#      along with adjacent "lib" folder.
#   4. Update all "Release Docs"
#   5. JBOSS distribution, "jbossv610", is up to date
#
#   Output:
#   New JCMS Web Installer file is created and checked into subversion at 
#   https://cs-svn.jax.org/svn/csrepos/JCMSWeb/trunk/deploy/jcmsWebInstall.zip
#   
# =============================================================================

# Set the default file creatation for this script.  Allow the group full action.
umask 0002

# Currently set in /etc/profile.d/java.sh, system needs to be rebooted before the 
# profiles are read.  Also set in .bashrc for command line use.
export APP_LIBS=/jcmsWeb/buildlibs
export ANT_HOME=/usr/share/ant
PATH=$PATH:/usr/share/ant/bin

echo "APP_LIBS:" $APP_LIBS
echo "JBOSS_HOME:" $JBOSS_HOME
echo "ANT_HOME:" $ANT_HOME

# Set Current Working Directory
echo "[1] cd /jcmsWeb"
cd /jcmsWeb

# Main
echo "[2] ant main "
ant main

echo "[3] ant main complete"


# Delete Today Dir
#echo "[2]:rm -rf `date +%A` "
#rm -rf `date +%A`

# Create Today Dir
#echo "[3]:mkdir  `date +%A`"
#mkdir  `date +%A`

# Copy build script
#echo "[4]:cp tools/build.*  `date +%A`/"
#cp tools/build.*  `date +%A`/


# cd Dir
#echo "[5]:cd `date +%A`"
#cd `date +%A`

# Clean
#echo "[2] ant clean "
#ant clean 

# Initialize
#echo "[3] ant init "
#ant init

# Export 
#echo "[4] ant get "
#ant get

# Build 
#echo "[5] and build "
#ant build 

# Package
#echo "[9]:ant distro"
#ant distro

# Package
#echo "[6] ant copy"
#ant copy 
