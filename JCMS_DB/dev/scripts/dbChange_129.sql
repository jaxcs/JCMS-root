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
ALTER TABLE `Litter` 
ADD COLUMN `numberBornDead` INT(11) NULL DEFAULT NULL AFTER `numMale`,
ADD COLUMN `numberCulledAtWean` INT(11) NULL DEFAULT NULL AFTER `numberBornDead`,
ADD COLUMN `numberMissingAtWean` INT(11) NULL DEFAULT NULL AFTER `numberCulledAtWean`;

UPDATE dbInfo SET
	dbVers = 129,
	webReleaseNum = '3.9.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;