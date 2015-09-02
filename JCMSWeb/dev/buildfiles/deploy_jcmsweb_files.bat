rem ==================================================================
rem   This script copies JCMS Web build files to The Jackson Laboratory
rem   servers hosting JCMW Web instances.  Aside from the standard 
rem   two jars and war file is a NUX shell script.  This script when 
rem   run on each server, from /usr/local/deploy, stops all JCMS Web
rem   application servers, updates the JCMS Web API files and restarts
rem   JCMS Web application.
rem 
rem   Preconditions:
rem     1. Create host directory /usr/local/deploy on each server
rem 
rem   Usage:
rem     1. ssh to each server as user jboss
rem     2. run /usr/local/deploy/deploy_jcmsweb.sh
rem 
rem   Author:	Craig Hanna (cnh@jax.org)
rem   Date:		5-20-2014
rem ==================================================================
scp deploy_jcmsweb.sh JCMSIntegrationTier.jar JCMSMiddleTier.jar JCMSWebTier.war jboss@bhjcmsweb01:/usr/local/deploy
scp deploy_jcmsweb.sh JCMSIntegrationTier.jar JCMSMiddleTier.jar JCMSWebTier.war jboss@bhjcmsweb02:/usr/local/deploy
scp deploy_jcmsweb.sh JCMSIntegrationTier.jar JCMSMiddleTier.jar JCMSWebTier.war jboss@bhjcmsweb03:/usr/local/deploy
scp deploy_jcmsweb.sh JCMSIntegrationTier.jar JCMSMiddleTier.jar JCMSWebTier.war jboss@bhjcmsweb04:/usr/local/deploy
