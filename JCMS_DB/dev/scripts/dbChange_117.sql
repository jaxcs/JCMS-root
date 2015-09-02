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
ALTER TABLE `User` 
ADD COLUMN `passwordChangedDate` DATETIME NOT NULL AFTER `_DefaultWorkgroup_key`;

UPDATE `User` SET `passwordChangedDate` = NOW() WHERE _user_key > 0;

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_ENFORCE_PASSWORD_CHANGE', 'false', 'Tells application whether to remind user whether to change password after password change period has elapsed');

INSERT INTO `DbSetup` (`MTSVar`, `MTSValue`, `MTSVarDescription`) VALUES ('JCMS_PASSWORD_CHANGE_PERIOD', '90', 'The number of days between a user changing and being reminded to change their password');

UPDATE dbInfo SET
	dbVers = 117,
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;
