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

-- This release updates the interface to have the copyright statement.
-- Minor change to add the bar code cage ID field to the included cage cards.
-- Note it is not visible. Mating cage card formats without the field will
-- give an error message.

UPDATE dbInfo SET
	dbVers = 142,	
	releaseNum= '6.1.9',		
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

SELECT '-- Database upgrade 142 complete --';

