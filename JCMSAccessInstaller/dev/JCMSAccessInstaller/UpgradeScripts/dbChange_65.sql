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

ALTER TABLE Strain MODIFY COLUMN _strain_key INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Container MODIFY COLUMN _container_key INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE ContainerHistory MODIFY COLUMN _containerHistory_key INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Allele ADD CONSTRAINT FK_Gene_Allele_1 FOREIGN KEY FK_Gene_Allele_1 (_gene_key)
    REFERENCES Gene (_gene_key)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;

UPDATE dbInfo SET dbVers = 65, versDate = now(), databaseReleaseNum = 65 ;








