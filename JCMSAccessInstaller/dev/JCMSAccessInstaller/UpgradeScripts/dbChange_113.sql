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
CREATE TABLE `QueryWorkgroupLink` (
  `_queryWorkgroupLink_key` INT(11) NOT NULL AUTO_INCREMENT,
  `_Workgroup_key` INT(11) NOT NULL,
  `_QueryDefinition_key` INT(11) NOT NULL,
  `version` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`_queryWorkgroupLink_key`),
  UNIQUE INDEX `_queryWorkgroupLink_key_UNIQUE` (`_queryWorkgroupLink_key` ASC),
  INDEX `queryworkgrouplink_ibfk_2_idx` (`_QueryDefinition_key` ASC),
  INDEX `queryworkgrouplink_ibfk_1_idx` (`_Workgroup_key` ASC),
  CONSTRAINT `queryworkgrouplink_workgroup_ibfk`
    FOREIGN KEY (`_Workgroup_key`)
    REFERENCES `Workgroup` (`_Workgroup_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `queryworkgrouplink_ibfk`
    FOREIGN KEY (`_QueryDefinition_key`)
    REFERENCES `QueryDefinition` (`_QueryDefinition_key`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
INSERT INTO QueryWorkgroupLink (_QueryDefinition_key, _Workgroup_key) 
SELECT _QueryDefinition_key, _Workgroup_key 
FROM QueryDefinition 
WHERE _QueryType_key = 3 OR _QueryType_key = 4 OR _QueryType_key = 5;


UPDATE dbInfo SET
	dbVers = 113,
	versDate = now(),
	releaseNum = '6.1.1',
	webReleaseNum = '3.1.0',
	releaseDate = '2013-12-11 00:00:00'
    WHERE _dbinfo_key = 1
;
