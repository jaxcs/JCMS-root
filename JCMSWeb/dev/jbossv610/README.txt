-------------------------------------------------------------------------------

  JBoss, Home of Professional Open Source 
  Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
  as indicated by the @author tags. All rights reserved. 
  See the copyright.txt in the distribution for a 
  full listing of individual contributors.

  This copyrighted material is made available to anyone wishing to use, 
  modify, copy, or redistribute it subject to the terms and conditions 
  of the GNU Lesser General Public License, v. 2.1.

  This program is distributed in the hope that it will be useful, but WITHOUT A 
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
  PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
  You should have received a copy of the GNU Lesser General Public License, 
  v.2.1 along with this distribution; if not, write to the Free Software 
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
  MA  02110-1301, USA.

-------------------------------------------------------------------------------

Introduction
------------

The JBoss Application Server is an open source implementation of the Java EE suite of 
services. It comprises a set of JBoss Enterprise Middleware components that have 
been tested and certified together to provide an integrated experience.

The full set of documentation is available at http://www.jboss.org/jbossas


System Requirements
-------------------

The JBoss Application Server 6 requires at least JDK 1.6.  In addition, you must
set the JAVA_HOME environment variable to the root directory of the JDK.


Staring/Stopping the JBoss Application Server
---------------------------------------------

The startup/shutdown scripts are located in the `bin` directory.  To start the 
JBoss server, use `run.sh` (or run.bat on Windows)

    bin/run.sh
  
To stop the JBoss server, open a new console window and run `shutdown.sh` (shutdown.bat on Windows)

    bin/shutdown.sh
  

Overview of Directory Structure
-------------------------------

bin - Contains scripts for starting and stopping JBoss

client - Contains jars required for certain client applications which communicate with the JBoss server

common - Jar files and deployments common to all profiles on the server

docs - Documentation such as examples, XML schemas, and detailed license information

lib - Jar files used by the JBoss server

server - Contains server profiles which determine the components available to JBoss at runtime


