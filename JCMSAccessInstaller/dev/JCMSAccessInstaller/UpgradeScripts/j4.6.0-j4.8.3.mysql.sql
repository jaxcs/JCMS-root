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
ALTER TABLE `Mouse`
CHANGE COLUMN `newTag` `newTag` VARCHAR(16) NULL DEFAULT NULL  ,
CHANGE COLUMN `coatColor` `coatColor` VARCHAR(8) NULL DEFAULT NULL  ,
CHANGE COLUMN `diet` `diet` VARCHAR(32) NULL DEFAULT NULL  ;

INSERT INTO `UpgradeHistoryLog` (`majorVersion`, `minorVersion`, `bugFixVersion`, `upgradeDate`, `actionDescription`, `actionCompleted`, `comment`) VALUES (4, 8, 3, CURDATE(), 'None', -1, CURDATE());   
