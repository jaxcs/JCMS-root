/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package jcms.integrationtier.dao;

/**
 *
 * @author springer
 *
 * Constants used in MatingQuery.  Using the constants provides compiler
 * assistance and spell checking, and code consistency.
 */
public class MatingQueryConst {

	// Mating ID
        public  final static String selectMatingID = "MatingID";
	// Mating Dates
        public  final static String selectMatingDates = "MatingDates";
	// Mating Status
        public  final static String selectMatingStatus = "MatingStatus";
        // Litter Strain
        public  final static String selectMatingStrain = "MatingStrain";
        // Litter Generation
        public  final static String selectLitterGeneration = "LitterGeneration";
        // Mating Owner
        public  final static String selectMatingOwner = "MatingOwner";
        // Pen ID
        public  final static String selectMatingPenId = "MatingPenId";
	// Pen Name
        public  final static String selectMatingPenName = "MatingPenName";
        // Dam 1 ID
        public  final static String selectDam1ID = "Dam1ID";
	// Dam 1 Strain
        public  final static String selectDam1Strain = "Dam1Strain";
	// Dam 1 Stock #
        public  final static String selectDam1Stock = "Dam1Stock";
        // Dam 1 Generation
        public  final static String selectDam1Gen = "Dam1Gen";
	// Dam 1 Up To Three Genotypes
        public  final static String selectDam1Genotype = "Dam1Genotype";
	// Dam 1 Date Of Birth
        public  final static String selectDam1DOB = "Dam1DOB";
        // Dam 1 Plug Dates
        public  final static String selectDam1PlugDate = "Dam1PlugDate";
        // Dam 2 ID
        public  final static String selectDam2ID = "Dam2ID";
	// Dam 2 Strain
        public  final static String selectDam2Strain = "Dam2Strain";
	// Dam 2 Stock #
        public  final static String selectDam2Stock = "Dam2Stock";
        // Dam 2 Generation
        public  final static String selectDam2Gen = "Dam2Gen";
	// Dam 2 Up To Three Genotypes
        public  final static String selectDam2Genotype = "Dam2Genotype";
	// Dam 2 Date Of Birth
        public  final static String selectDam2DOB = "Dam2DOB";
        // Dam 2 Plug Dates
        public  final static String selectDam2PlugDate = "Dam2PlugDate";
        // Sire ID
        public  final static String selectSireID = "SireID";
	// Sire Strain
        public  final static String selectSireStrain = "SireStrain";
        // Sire Stock #
        public  final static String selectSireStock = "SireStock";
	// Sire Generation
        public  final static String selectSireGen = "SireGen";
        // Sire Up To Three Genotypes
        public  final static String selectSireGenotype = "SireGenotype";
	// Sire Date Of Birth
        public  final static String selectSireDOB = "SireDOB";
        // Date Retired
        public  final static String selectDateRetired = "DateRetired";
	// Wean Time
        public  final static String selectWeanTime = "WeanTime";
	// Needs Typing
        public  final static String selectNeedsTyping = "NeedsTyping";
        //  Summarize Litter Information
        // Total number of litters
        public  final static String selectTotalLitters = "TotalLitters";
        // Total number of pups born
        public  final static String selectTotalPups = "TotalPups";
        // Total number of males weaned
        public  final static String selectTotalMales = "TotalMales";
        // Total number of females weaned
        public  final static String selectTotalFemales = "TotalFemales";
        // Total number of litters born dead
        public  final static String selectTotalLittersDead = "TotalLittersDead";
        // First and last birth dates
        public  final static String selectBirthDates = "BirthDates";
        // Mating ID From
        public  final static String matingIDFromEntity = "MatingIDFromEntity";
        // Mating ID To
        public  final static String matingIDToEntity = "MatingIDToEntity";
	// Mating Dates
        public  final static String matingStartDate = "MatingStartDate";
        public  final static String matingEndDate = "MatingEndDate";
        // Mating Status
        public  final static String matingStatusEntity = "MatingStatusEntity";
        // Litter Strain
        public  final static String litterStrainEntity = "LitterStrainEntity";
	// Litter Generation
        public  final static String litterGenerationEntity = "LitterGenerationEntity";
        // Mating Owner
        public  final static String matingOwnerEntity = "MatingOwnerEntity";
	// Pen ID
        public  final static String containerIDEntity = "ContainerIDEntity";
	// Pen Name
        public  final static String containerNameEntity = "ContainerNameEntity";
	// Mating Filter
        public  final static String matingFilter = "matingFilter";
	// Retire Date Start
        public  final static String matingRetireDateStart = "matingRetireDateStart";
	// Retire Date End
        public  final static String matingRetireDateEnd = "matingRetireDateEnd";
	// Retire Date End
        public  final static String selectLitterIDs = "litterIDs";
	// Retire Date End
        public  final static String selectTotalPupsBornDead = "totalPupsBornDead";
	// Retire Date End
        public  final static String selectTotalPupsCulledAtWean = "totalPupsCulledAtWean";
	// Retire Date End
        public  final static String selectTotalPupsMissingAtWean = "totalPupsMissingAtWean";

        public  final static String penIDFrom = "PenIDFrom";
        public  final static String penIDTo = "PenIDTo";
        public  final static String pName = "PName";
        public  final static String mIDFrom = "MIDFrom";
        public  final static String mIDTo = "MIDTo";

        public final static String penIdFilter = "PenIdFilter";
        public final static String penNameFilter = "PenNameFilter";

}
