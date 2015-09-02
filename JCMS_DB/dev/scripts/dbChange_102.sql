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

SELECT '--dbChange_102.sql--';
SELECT '--1--';

UPDATE ControlledVocabulary
SET pkColumnName = '',
    columnOneName = '',
    columnTwoName = '',
    displayColumnOneName = '',
    displayColumnTwoName = '',
    subViewName = 'MOUSEUSE'
WHERE tableName = 'cv_MouseUse'
;

SELECT '--2--';
UPDATE ControlledVocabulary SET displayName = 'Manage Rooms' WHERE displayName = 'Manage Room';
UPDATE ControlledVocabulary SET displayName = 'Mating Card Note Terms' WHERE displayName = 'Mating Card Notes';
UPDATE ControlledVocabulary SET displayName = CONCAT(displayName,' Terms') WHERE subViewName = 'GENERAL';
UPDATE ControlledVocabulary SET displayName = 'Cage Status Terms' WHERE displayName = 'Cage Status';

SELECT '--3--';
UPDATE ControlledVocabulary SET sortOrder = 1 WHERE displayName = 'Cage Status Terms';
UPDATE ControlledVocabulary SET sortOrder = 2 WHERE displayName = 'Health Level Terms';
UPDATE ControlledVocabulary SET sortOrder = 3 WHERE displayName = 'Manage Cages';
UPDATE ControlledVocabulary SET sortOrder = 4 WHERE displayName = 'Manage Rooms';
UPDATE ControlledVocabulary SET sortOrder = 5 WHERE displayName = 'Mating Card Note Terms';
UPDATE ControlledVocabulary SET sortOrder = 6 WHERE displayName = 'Retire Cages';


UPDATE dbInfo SET dbVers = 102, versDate = now(), databaseReleaseNum = 102 ;