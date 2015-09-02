#!/bin/bash

# =======================================================================================
# Shell script deploys JCMS Web jars and war files to each JAX JCMS Web instance.  Stops
# each JBOSS application server, updates the APIs, and restarts JBOSS application server.
# This script resides in host server folder /usr/local/deploy.  This script detects 
# JCMS Web instances by scanning the shutdown and run alias'.  A shutdown and run alias
# must be defined for this script to automatically include it.
#
# There are three scripts below to shutdown, copy files and restart.  Filter comments  
# show you how to omit JCMS Web instances from this automated process.  Filtering out
# a JCMS Web instances must be applied to all three scripts.
#
# Shutdown and run filters reference alias names while copy filter references JCMS Web
# directory names at /usr/local.
#
# Preconditions: 
#   1. Copy new JARs and WAR files to /usr/local/deploy
#   2. Alias' are defined to shutdown and run each JCMS Web instance.
#
# Filters:
#   Shutdown Filter:	alias | grep -E 'shutdown' | grep -v 'shutdowndemo' | ...
#   Copy File Filter:	... -not -path '*jboss' -not -path '*jbossdemo' ...		
#   Run Filter:			alias | grep -E 'run' | grep -v 'rundemo' | ...
#
# Usage:
#   /usr/local/deploy/deploy_jcmsweb.sh
#
# Author:	Craig Hanna (cnh@jax.org)
# Date:		5-20-2014
# =======================================================================================

# load alias definitions
source /usr/local/jboss/.bashrc

# Script to run each shutdown command from alias list
# UNIT TEST:  echo "alias shutdowndev='./jbossdev/bin/shutdown.sh --host=jcmsdev --port=1090 -S'" | grep -E 'shutdown' | grep -v 'shutdownqa=' | 
echo " "
echo "======================="
echo "Stop JCMS Web Instances"
echo "======================="
echo " "
alias | grep -E 'shutdown' | 
grep -v 'shutdowndemo' | 
while read -r result ; do 
starttext=${result%%[=]*} ; 
startpos=${#starttext} ; 
endtext=${result%%[=]*} ; 
endpos=${#result}
runcmd=${result:startpos + 2:((endpos - 1)-(startpos + 2))} ; 
echo "stopping:${runcmd}" ; 
${runcmd} ; 
sleep 10s ;
echo "stopped:${runcmd}" ; 
done 

deploydir=/server/default/deploy/

# Update JARs and WAR files
echo " "
echo "==================="
echo "Deploy JARS and WAR"
echo "==================="
echo " "
find ../ -maxdepth 1 -type d -name '*jboss*' -not -path '*jboss' -not -path '*jbossdemo' | 
while read -r result ; do
filename="JCMSIntegrationTier.jar"
cp $filename $result$deploydir$filename
echo "copied $filename to $result$deploydir$filename"
filename="JCMSMiddleTier.jar"
cp $filename $result$deploydir$filename
echo "copied $filename to $result$deploydir$filename"
filename="JCMSWebTier.war"
cp $filename $result$deploydir$filename
echo "copied $filename to $result$deploydir$filename"

echo "Checking out JBOSS libraries"

done

# Script to get each run command from alias list
# UNIT TEST:  echo "alias rundev='./jbossdev/bin/run.sh -b jcmsdev -Djboss.messaging.ServerPeerID=2 &'" | grep -E 'run' | grep -v 'runqa=' | 
echo " "
echo "=========================="
echo "Restart JCMS Web Instances"
echo "=========================="
echo " "
alias | grep -E 'run' | 
grep -v 'rundemo' | 
while read -r result ; do 
starttext=${result%%[=]*} ; 
startpos=${#starttext} ; 
endtext=${result%%[=]*} ; 
endpos=${#result}
runcmd=${result:startpos + 2:((endpos-1)-(startpos + 2))} ; 
echo "starting:${runcmd}" ; 
${runcmd}
sleep 60s ;
echo " " ;
echo "JCMS Web Instance Started:${runcmd}" ; 
echo " " ;
done 

# Shutdown Filters
#grep -v 'shutdownackerman' |
#grep -v 'shutdownbpaigen' |
#grep -v 'shutdownbult' |
#grep -v 'shutdownburgess' |
#grep -v 'shutdowncraniomurray' |
#grep -v 'shutdownfacebase' |
#grep -v 'shutdownserreze' |
#grep -v 'shutdownqa' | 

# Copy File Filters
#-not -path '*jbossackerman' -not -path '*jbossbult' -not -path '*jbossbpaigen' -not -path '*jbossburgess' -not -path '*jbosscraniomurray' -not -path '*jbossfacebase' -not -path '*jbossserreze' -not -path '*jbossqa' 

# Run Filters
#grep -v 'runackerman' |
#grep -v 'runbpaigen' |
#grep -v 'runbult' |
#grep -v 'runburgess' |
#grep -v 'runcraniomurray' |
#grep -v 'runfacebase' |
#grep -v 'runserreze' |
#grep -v 'runqa=' | 


