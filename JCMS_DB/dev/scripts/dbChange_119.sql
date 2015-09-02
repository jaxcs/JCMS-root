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
-- Version One Defect D-02209
UPDATE Mating SET needsTyping = -1 WHERE needsTyping = 1;
UPDATE Genotype SET all1Conf = -1 WHERE all1Conf = 1;
UPDATE Genotype SET all2Conf = -1 WHERE all2Conf = 1;
UPDATE cv_SampleStatus SET isInStorage = -1 WHERE isInStorage = 1;

UPDATE dbInfo SET
	dbVers = 119,
	webReleaseNum= '3.4.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;