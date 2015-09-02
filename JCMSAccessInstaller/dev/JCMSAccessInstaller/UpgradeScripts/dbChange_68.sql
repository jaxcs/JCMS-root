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

UPDATE cv_ControlledVocabularyGroup SET name = 'Cages, Rooms and Cage Cards' WHERE name = 'Pens, Rooms and Cage Cards';

UPDATE ControlledVocabulary SET displayName = 'Cage Status' WHERE displayName = 'Pen Status';
UPDATE ControlledVocabulary SET displayName = 'Manage Cage Configuration' WHERE displayName = 'Manage Pen Configuration';
UPDATE ControlledVocabulary SET displayName = 'Manage Cages' WHERE displayName = 'Manage Pens';
UPDATE ControlledVocabulary SET displayName = 'Retire Cages' WHERE displayName = 'Retire Pens';

-- Fix Add Genotype search error.  A date of all zeros is not a valid date.
UPDATE Genotype SET gtDate = null WHERE gtDate = '0000-00-00 00:00:00';

UPDATE dbInfo SET dbVers = 68, versDate = now(), databaseReleaseNum = 68 ;

