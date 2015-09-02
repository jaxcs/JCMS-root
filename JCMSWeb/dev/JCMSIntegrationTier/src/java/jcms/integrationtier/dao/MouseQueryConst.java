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
 * Constants used in MouseQuery.  Using the constants provides compiler
 * assistance and spell checking, and code consistency.
 */
public class MouseQueryConst {

        public final static String selectMouseID = "MouseIDSelect";
        public final static String selectMouseStrain  = "MouseStrain";
        public final static String selectMouseBreedingStatus = "MouseBreedingStatus";
        public final static String selectMouseGeneration = "MouseGeneration";
        public final static String selectMouseOwner = "MouseOwner";
        public final static String selectMouseOrigin = "MouseOrigin";
        public final static String selectMouseSex = "MouseSex";
        public final static String selectMouseCOD = "MouseCOD";
        public final static String selectMouseDOB = "MouseBirthDate";
        public final static String selectMouseExitDate ="MouseExitDate";
        public final static String selectMouseLitter = "MouseLitterNum";
        public final static String selectMousePenID = "MousePenID";
        public final static String selectMousePenName ="MousePenName";
        public final static String selectMouseUse = "MouseUse";
        public final static String selectMouseGenotype = "MouseGenotype";
        public final static String selectMouseGenotypeDate ="MouseGenotypeDate";
        public final static String selectMouseAge = "MouseAge";
        public final static String selectMouseMating =  "MouseMatings";
        public final static String selectMouseParents ="MouseParents";
        public final static String selectMouseCoatColor ="MouseCoatColor";
        public final static String selectMouseDiet = "MouseDiet";
        public final static String selectUseSchedules = "selectUseSchedules";
        public final static String selectMousePhenotypes = "selectMousePhenotypes";
        public final static String selectMouseProtocolID = "MouseProtocolID";
        public final static String selectMouseLitterMates ="MouseLitterMates";
        public final static String selectMouseLifeStatus = "MouseLifeStatus";
        public final static String selectMouseRoom = "MouseRoom";
        public final static String selectMouseComment = "selectMouseComment";

        public  final static String comparisonEquals="Equal";
        public  final static String comparisonBetween="Between";
        public  final static String comparisonContains="Contains";

        public  final static String comparisonGreaterThan="GreaterThan";
        public  final static String comparisonLessThan="LessThan";

        public  final static String sexEntity="SexEntity";
        public  final static String breedingStatusEntity = "BreedingStatusEntity";
        public  final static String originEntity = "originEntity";
        public  final static String generationEntity = "GenerationEntity";
        public  final static String causeOfDeathEntity = "CauseOfDeathEntity";
        public  final static String lifeStatusEntity = "LifeStatusEntity";
        public  final static String roomEntity = "RoomEntity";
        public  final static String strainEntity = "StrainEntity";
        public  final static String ownerEntity = "OwnerEntity";
        public  final static String containerEntity = "ContainerEntity";
        public  final static String useScheduleTerm = "UseScheduleTerm";
        public  final static String phenotypeTerm = "PhenotypeTerm";
        public  final static String protocolEntity = "ProtocolEntity";
        public  final static String mouseUseEntity = "MouseUseEntity";
        public  final static String geneEntity = "GeneEntity";
        public  final static String mouseIDFromEntity = "MouseIDFromEntity";
        public  final static String mouseIDToEntity = "MouseIDToEntity";
        public  final static String mouseID = "MouseID";
        public  final static String mouseIDFilter = "MouseIDFilter";
        public  final static String ageFromEntity = "AgeFromEntity";
        public  final static String ageToEntity = "AgeToEntity";
        public  final static String ageFilter = "AgeFilter";
        public  final static String ageMeasure = "AgeMeasure";
        public  final static String mouseStartDOBEntity = "MouseStartDOBEntity";
        public  final static String mouseEndDOBEntity = "MouseEndDOBEntity";
        public  final static String exitDateStartEntity = "ExitDateStartEntity";
        public  final static String exitDateEndEntity = "ExitDateEndEntity";
        public  final static String genotypeStartDateEntity = "GenotypeStartDateEntity";
        public  final static String genotypeEndDateEntity = "GenotypeEndDateEntity";
        public  final static String litterEntity = "LitterEntity";

        public  final static String penIDFrom = "PenIDFrom";
        public  final static String penIDTo = "PenIDTo";
        public  final static String pName = "PName";
        public  final static String litterID = "LitterID";
        
        public final static String penIdFilter = "PenIdFilter";
        public final static String penNameFilter = "PenNameFilter";
        public final static String litterFilter = "LitterFilter";
}
