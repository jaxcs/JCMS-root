#***
#Copyright (c) 2015 The Jackson Laboratory
#
#This is free software: you can redistribute it and/or modify it 
#under the terms of the GNU General Public License as published by  
#the Free Software Foundation, either version 3 of the License, or  
#(at your option) any later version.
# 
#This software is distributed in the hope that it will be useful,  
#but WITHOUT ANY WARRANTY; without even the implied warranty of 
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
#General Public License for more details.
#
#You should have received a copy of the GNU General Public License 
#along with this software.  If not, see <http://www.gnu.org/licenses/>.
#***
INSERT INTO DbSetup (`MTSVar`, `MTSValue`, `MTSVarDescription`) 
	VALUES ('JCMS_MYSQL_DATABASE_NAME', 'jcms_db', 'The name of your MySQL database.');
INSERT INTO DbSetup (`MTSVar`, `MTSValue`, `MTSVarDescription`) 
	VALUES ('JCMS_MYSQL_DRIVER', '{MySQL ODBC 3.51 Driver}', 'The name of your MySQL driver.');
INSERT INTO DbSetup (`MTSVar`, `MTSValue`, `MTSVarDescription`) 
	VALUES ('JCMS_MYSQL_SERVER', 'localhost', 'The name of your MySQL server.');
INSERT INTO DbSetup (`MTSVar`, `MTSValue`, `MTSVarDescription`) 
	VALUES ('JCMS_MYSQL_USER_ID', 'root', 'The name of your MySQL user ID.');

UPDATE dbInfo SET
	dbVers = 115,
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;
