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

-- Drop foreign keys first before altering a primary key and setting it to auto increment.
-- Rename foreign key to avoid MySQL auto incrementing FK number suffix.

-- Drop Constraints
CALL DropConstraintWithoutName('ExpDataDefault','_expDataDescriptor_key','ExpDataDescriptor','_expDataDescriptor_key');
CALL DropConstraintWithoutName('ExpData','_specimen_key','Mouse','_mouse_key');
CALL DropConstraintWithoutName('ExpData','_expDataDesc_key','ExpDataDescriptor','_expDataDescriptor_key');
CALL DropConstraintWithoutName('ExpData','_expTest_key','ExpTest','_expTest_key');
CALL DropConstraintWithoutName('ExpPlanMouseMap','_mouse_key','Mouse','_mouse_key');
CALL DropConstraintWithoutName('ExpPlanMouseMap','_expPlan_key','ExpPlan','_expPlan_key');
CALL DropConstraintWithoutName('ExpTest','_expDataDescriptor_key','ExpDataDescriptor','_expDataDescriptor_key');
CALL DropConstraintWithoutName('ExpTest','_expPlan_key','ExpPlan','_expPlan_key');
CALL DropConstraintWithoutName('ExpTestPlanMap','_expTest_key','ExpTest','_expTest_key');
CALL DropConstraintWithoutName('ExpTestPlanMap','_expPlanMouseMap_key','ExpPlanMouseMap','_expPlanMouseMap_key');

-- Set Primary Keys to Auto Increment
ALTER TABLE ExpDataDefault MODIFY COLUMN _expDataDefault_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpData MODIFY COLUMN _expData_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpDataDescriptor MODIFY COLUMN _expDataDescriptor_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpPlan MODIFY COLUMN _expPlan_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpPlanMouseMap MODIFY COLUMN _expPlanMouseMap_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpTest MODIFY COLUMN _expTest_key INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE ExpTestPlanMap MODIFY COLUMN _expTestPlan_key INT(11) NOT NULL AUTO_INCREMENT;

-- Recreate constraints
ALTER TABLE ExpData ADD CONSTRAINT expdata_mouse_ibfk FOREIGN KEY expdata_mouse_ibfk (_specimen_key)
    REFERENCES Mouse (_mouse_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE ExpData ADD CONSTRAINT expdata_expdatadescriptor_ibfk FOREIGN KEY expdata_expdatadescriptor_ibfk (_expDataDesc_key)
    REFERENCES ExpDataDescriptor (_expDataDescriptor_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE ExpData ADD CONSTRAINT expdata_exptest_ibfk FOREIGN KEY expdata_exptest_ibfk (_expTest_key)
    REFERENCES ExpTest (_expTest_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE ExpDataDefault ADD CONSTRAINT expdatadefault_expdatadescriptor_ibfk FOREIGN KEY expdatadefault_expdatadescriptor_ibfk (_expDataDescriptor_key)
    REFERENCES ExpDataDescriptor (_expDataDescriptor_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE ExpPlanMouseMap ADD CONSTRAINT expplanmousemap_mouse_ibfk FOREIGN KEY expplanmousemap_mouse_ibfk (_mouse_key)
    REFERENCES Mouse (_mouse_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE ExpPlanMouseMap ADD CONSTRAINT expplanmousemap_expplan_ibfk FOREIGN KEY expplanmousemap_expplan_ibfk (_expPlan_key)
    REFERENCES ExpPlan (_expPlan_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE ExpTest ADD CONSTRAINT exptest_expdatadescriptor_ibfk FOREIGN KEY exptest_expdatadescriptor_ibfk (_expDataDescriptor_key)
    REFERENCES ExpDataDescriptor (_expDataDescriptor_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE ExpTest ADD CONSTRAINT exptest_expplan_ibfk FOREIGN KEY exptest_expplan_ibfk (_expPlan_key)
    REFERENCES ExpPlan (_expPlan_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE ExpTestPlanMap ADD CONSTRAINT exptestplanmap_exptest_ibfk FOREIGN KEY exptestplanmap_exptest_ibfk (_expTest_key)
    REFERENCES ExpTest (_expTest_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
ALTER TABLE ExpTestPlanMap ADD CONSTRAINT exptestplanmap_expplanmousemap_ibfk FOREIGN KEY exptestplanmap_expplanmousemap_ibfk (_expPlanMouseMap_key)
    REFERENCES ExpPlanMouseMap (_expPlanMouseMap_key)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

UPDATE dbInfo SET
	dbVers = 134,
	webReleaseNum= '3.12.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

