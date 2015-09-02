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
# 
UPDATE dbInfo SET
	dbVers = 135,
	releaseNum = '6.1.5',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

#
# Release 6.1.5 for the JCMS Access interface changes dates to allow using formats
# that are different from the US standard mm/dd/yyyy (month/day/year)
#
# Bug fix to the data. 
# correct all natural mating records that have the sire coded as matingUnitType_key = 1 (for dam)
# They must be changed to 2 (for sire)
# The code bug is in JCMS web and causes the Access interface to not display the sires
#

Update MatingUnitLink, Mating Set MatingUnitLink._matingUnitType_key = 2
Where Mating._mating_key = MatingUnitLink._mating_key
And Mating._sire_key = MatingUnitLink._mouse_key
And Mating._matingType_key = 1;

#
# Bug fix to the data.
# The bug affects JCMS Access Handheld Users
# The handheld pair/trio mating form did not update the MatingUnitLink table when adding a mating
# this adds the missing records to the MatingUnitLink table
#

Insert Into MatingUnitLink (_mating_key, _mouse_key, _matingUnitType_key)
 (Select Mating._mating_key, Mating._dam1_key, 1 From Mating 
Where Mating._mating_key not in (Select Mating._mating_key From Mating, MatingUnitLink Where (Mating._mating_key = MatingUnitLink._mating_key AND Mating._dam1_key = MatingUnitLink._mouse_key and MatingUnitLink._matingUnitType_key = 1) or Mating._matingType_key <> 1));

Insert Into MatingUnitLink (_mating_key, _mouse_key, _matingUnitType_key)
 (Select Mating._mating_key, Mating._dam2_key, 1 From Mating 
Where Mating._mating_key not in (Select Mating._mating_key From Mating, MatingUnitLink Where (Mating._mating_key = MatingUnitLink._mating_key AND Mating._dam2_key = MatingUnitLink._mouse_key and MatingUnitLink._matingUnitType_key = 1) or (Mating._dam2_key is null) or Mating._matingType_key <> 1));

Insert Into MatingUnitLink (_mating_key, _mouse_key, _matingUnitType_key)
 (Select Mating._mating_key, Mating._sire_key, 2 From Mating 
Where Mating._mating_key not in (Select Mating._mating_key From Mating, MatingUnitLink Where (Mating._mating_key = MatingUnitLink._mating_key AND Mating._sire_key = MatingUnitLink._mouse_key and MatingUnitLink._matingUnitType_key = 2) or Mating._matingType_key <> 1));

