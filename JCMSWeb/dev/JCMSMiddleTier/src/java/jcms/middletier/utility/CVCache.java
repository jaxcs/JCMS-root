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

package jcms.middletier.utility;

import jcms.middletier.base.BTBaseCache;
import jcms.integrationtier.base.ITBaseEntityTable;

/**
 *
 * @author cnh
 */
public class CVCache extends BTBaseCache
{
    private static ITBaseEntityTable cvCauseOfDeath             = null;
    private static ITBaseEntityTable cvCoatColor                = null;
    private static ITBaseEntityTable cvContainerStatus          = null;
    private static ITBaseEntityTable cvDiet                     = null;
    private static ITBaseEntityTable cvEpoch                    = null;
    private static ITBaseEntityTable cvExpSampleLocation        = null; //**** NOTE: This is not a table used by JCMS ****
    private static ITBaseEntityTable cvExpStatus                = null;
    private static ITBaseEntityTable cvFieldOfStudy             = null;
    private static ITBaseEntityTable cvGeneclass                = null;
    private static ITBaseEntityTable cvGeneration               = null;
    private static ITBaseEntityTable cvGenotypeSpecimenLocation = null;
    private static ITBaseEntityTable cvHarvestMethod            = null;
    private static ITBaseEntityTable cvHealthLevel              = null;
    private static ITBaseEntityTable cvKeywords                 = null;
    private static ITBaseEntityTable cvLocationType             = null;
    private static ITBaseEntityTable cvMatingCardNotes          = null;
    private static ITBaseEntityTable cvMouseOrigin              = null;
    private static ITBaseEntityTable cvMouseProtocol            = null;
    private static ITBaseEntityTable cvMouseUse                 = null;
    private static ITBaseEntityTable cvPreservationDetail       = null;
    private static ITBaseEntityTable cvPreservationMethod       = null;
    private static ITBaseEntityTable cvPreservationType         = null;
    private static ITBaseEntityTable cvSampleClass              = null;
    private static ITBaseEntityTable cvSampleDateType           = null;
    private static ITBaseEntityTable cvSampleStatus             = null;
    private static ITBaseEntityTable cvSampleType               = null;
    private static ITBaseEntityTable cvStorageFacility          = null;
    private static ITBaseEntityTable cvStrainType               = null;
    private static ITBaseEntityTable cvTestStatus               = null;
    private static ITBaseEntityTable cvTimeUnit                 = null;
    private static ITBaseEntityTable cvWeightUnit               = null;

    /**
     * @return the cvCauseOfDeath
     */
    public static ITBaseEntityTable getCvCauseOfDeath() {
        return cvCauseOfDeath;
    }

    /**
     * @param aCvCauseOfDeath the cvCauseOfDeath to set
     */
    public static void setCvCauseOfDeath(ITBaseEntityTable aCvCauseOfDeath) {
        cvCauseOfDeath = aCvCauseOfDeath;
    }

    /**
     * @return the cvCoatColor
     */
    public static ITBaseEntityTable getCvCoatColor() {
        return cvCoatColor;
    }

    /**
     * @param aCvCoatColor the cvCoatColor to set
     */
    public static void setCvCoatColor(ITBaseEntityTable aCvCoatColor) {
        cvCoatColor = aCvCoatColor;
    }

    /**
     * @return the cvContainerStatus
     */
    public static ITBaseEntityTable getCvContainerStatus() {
        return cvContainerStatus;
    }

    /**
     * @param aCvContainerStatus the cvContainerStatus to set
     */
    public static void setCvContainerStatus(ITBaseEntityTable aCvContainerStatus) {
        cvContainerStatus = aCvContainerStatus;
    }

    /**
     * @return the cvDiet
     */
    public static ITBaseEntityTable getCvDiet() {
        return cvDiet;
    }

    /**
     * @param aCvDiet the cvDiet to set
     */
    public static void setCvDiet(ITBaseEntityTable aCvDiet) {
        cvDiet = aCvDiet;
    }

    /**
     * @return the cvEpoch
     */
    public static ITBaseEntityTable getCvEpoch() {
        return cvEpoch;
    }

    /**
     * @param aCvEpoch the cvEpoch to set
     */
    public static void setCvEpoch(ITBaseEntityTable aCvEpoch) {
        cvEpoch = aCvEpoch;
    }

    /**
     * @return the cvExpSampleLocation
     *  *****NOTE: This is not a table used by JCMS ****
     */
    public static ITBaseEntityTable getCvExpSampleLocation() {
        return cvExpSampleLocation;
    }

    /**
     * @param aCvExpSampleLocation the cvExpSampleLocation to set
     * *****NOTE: This is not a table used by JCMS ****
     */
    public static void setCvExpSampleLocation(ITBaseEntityTable aCvExpSampleLocation) {
        cvExpSampleLocation = aCvExpSampleLocation;
    }

    /**
     * @return the cvExpStatus
     */
    public static ITBaseEntityTable getCvExpStatus() {
        return cvExpStatus;
    }

    /**
     * @param aCvExpStatus the cvExpStatus to set
     */
    public static void setCvExpStatus(ITBaseEntityTable aCvExpStatus) {
        cvExpStatus = aCvExpStatus;
    }

    /**
     * @return the cvFieldOfStudy
     */
    public static ITBaseEntityTable getCvFieldOfStudy() {
        return cvFieldOfStudy;
    }

    /**
     * @param aCvFieldOfStudy the cvFieldOfStudy to set
     */
    public static void setCvFieldOfStudy(ITBaseEntityTable aCvFieldOfStudy) {
        cvFieldOfStudy = aCvFieldOfStudy;
    }

    /**
     * @return the cvGeneration
     */
    public static ITBaseEntityTable getCvGeneration() {
        return cvGeneration;
    }

    /**
     * @param aCvGeneration the cvGeneration to set
     */
    public static void setCvGeneration(ITBaseEntityTable aCvGeneration) {
        cvGeneration = aCvGeneration;
    }

    /**
     * @return the cvGenotypeSpecimenLocation
     */
    public static ITBaseEntityTable getCvGenotypeSpecimenLocation() {
        return cvGenotypeSpecimenLocation;
    }

    /**
     * @param aCvGenotypeSpecimenLocation the cvGenotypeSpecimenLocation to set
     */
    public static void setCvGenotypeSpecimenLocation(ITBaseEntityTable aCvGenotypeSpecimenLocation) {
        cvGenotypeSpecimenLocation = aCvGenotypeSpecimenLocation;
    }

    /**
     * @return the cvHarvestMethod
     */
    public static ITBaseEntityTable getCvHarvestMethod() {
        return cvHarvestMethod;
    }

    /**
     * @param aCvHarvestMethod the cvHarvestMethod to set
     */
    public static void setCvHarvestMethod(ITBaseEntityTable aCvHarvestMethod) {
        cvHarvestMethod = aCvHarvestMethod;
    }

    /**
     * @return the cvHealthLevel
     */
    public static ITBaseEntityTable getCvHealthLevel() {
        return cvHealthLevel;
    }

    /**
     * @param aCvHealthLevel the cvHealthLevel to set
     */
    public static void setCvHealthLevel(ITBaseEntityTable aCvHealthLevel) {
        cvHealthLevel = aCvHealthLevel;
    }

    /**
     * @return the cvKeywords
     */
    public static ITBaseEntityTable getCvKeywords() {
        return cvKeywords;
    }

    /**
     * @param aCvKeywords the cvKeywords to set
     */
    public static void setCvKeywords(ITBaseEntityTable aCvKeywords) {
        cvKeywords = aCvKeywords;
    }

    /**
     * @return the cvLocationType
     */
    public static ITBaseEntityTable getCvLocationType() {
        return cvLocationType;
    }

    /**
     * @param aCvLocationType the cvLocationType to set
     */
    public static void setCvLocationType(ITBaseEntityTable aCvLocationType) {
        cvLocationType = aCvLocationType;
    }

    /**
     * @return the cvMatingCardNotes
     */
    public static ITBaseEntityTable getCvMatingCardNotes() {
        return cvMatingCardNotes;
    }

    /**
     * @param aCvMatingCardNotes the cvMatingCardNotes to set
     */
    public static void setCvMatingCardNotes(ITBaseEntityTable aCvMatingCardNotes) {
        cvMatingCardNotes = aCvMatingCardNotes;
    }

    /**
     * @return the cvMouseOrigin
     */
    public static ITBaseEntityTable getCvMouseOrigin() {
        return cvMouseOrigin;
    }

    /**
     * @param aCvMouseOrigin the cvMouseOrigin to set
     */
    public static void setCvMouseOrigin(ITBaseEntityTable aCvMouseOrigin) {
        cvMouseOrigin = aCvMouseOrigin;
    }

    /**
     * @return the cvMouseProtocol
     */
    public static ITBaseEntityTable getCvMouseProtocol() {
        return cvMouseProtocol;
    }

    /**
     * @param aCvMouseProtocol the cvMouseProtocol to set
     */
    public static void setCvMouseProtocol(ITBaseEntityTable aCvMouseProtocol) {
        cvMouseProtocol = aCvMouseProtocol;
    }

    /**
     * @return the cvMouseUse
     */
    public static ITBaseEntityTable getCvMouseUse() {
        return cvMouseUse;
    }

    /**
     * @param aCvMouseUse the cvMouseUse to set
     */
    public static void setCvMouseUse(ITBaseEntityTable aCvMouseUse) {
        cvMouseUse = aCvMouseUse;
    }

    /**
     * @return the cvPreservationDetail
     */
    public static ITBaseEntityTable getCvPreservationDetail() {
        return cvPreservationDetail;
    }

    /**
     * @param aCvPreservationDetail the cvPreservationDetail to set
     */
    public static void setCvPreservationDetail(ITBaseEntityTable aCvPreservationDetail) {
        cvPreservationDetail = aCvPreservationDetail;
    }

    /**
     * @return the cvPreservationMethod
     */
    public static ITBaseEntityTable getCvPreservationMethod() {
        return cvPreservationMethod;
    }

    /**
     * @param aCvPreservationMethod the cvPreservationMethod to set
     */
    public static void setCvPreservationMethod(ITBaseEntityTable aCvPreservationMethod) {
        cvPreservationMethod = aCvPreservationMethod;
    }

    /**
     * @return the cvPreservationType
     */
    public static ITBaseEntityTable getCvPreservationType() {
        return cvPreservationType;
    }

    /**
     * @param aCvPreservationType the cvPreservationType to set
     */
    public static void setCvPreservationType(ITBaseEntityTable aCvPreservationType) {
        cvPreservationType = aCvPreservationType;
    }

    /**
     * @return the cvSampleClass
     */
    public static ITBaseEntityTable getCvSampleClass() {
        return cvSampleClass;
    }

    /**
     * @param aCvSampleClass the cvSampleClass to set
     */
    public static void setCvSampleClass(ITBaseEntityTable aCvSampleClass) {
        cvSampleClass = aCvSampleClass;
    }

    /**
     * @return the cvSampleDateType
     */
    public static ITBaseEntityTable getCvSampleDateType() {
        return cvSampleDateType;
    }

    /**
     * @param aCvSampleDateType the cvSampleDateType to set
     */
    public static void setCvSampleDateType(ITBaseEntityTable aCvSampleDateType) {
        cvSampleDateType = aCvSampleDateType;
    }

    /**
     * @return the cvSampleStatus
     */
    public static ITBaseEntityTable getCvSampleStatus() {
        return cvSampleStatus;
    }

    /**
     * @param aCvSampleStatus the cvSampleStatus to set
     */
    public static void setCvSampleStatus(ITBaseEntityTable aCvSampleStatus) {
        cvSampleStatus = aCvSampleStatus;
    }

    /**
     * @return the cvSampleType
     */
    public static ITBaseEntityTable getCvSampleType() {
        return cvSampleType;
    }

    /**
     * @param aCvSampleType the cvSampleType to set
     */
    public static void setCvSampleType(ITBaseEntityTable aCvSampleType) {
        cvSampleType = aCvSampleType;
    }

    /**
     * @return the cvStorageFacility
     */
    public static ITBaseEntityTable getCvStorageFacility() {
        return cvStorageFacility;
    }

    /**
     * @param aCvStorageFacility the cvStorageFacility to set
     */
    public static void setCvStorageFacility(ITBaseEntityTable aCvStorageFacility) {
        cvStorageFacility = aCvStorageFacility;
    }

    /**
     * @return the cvStrainType
     */
    public static ITBaseEntityTable getCvStrainType() {
        return cvStrainType;
    }

    /**
     * @param aCvStrainType the cvStrainType to set
     */
    public static void setCvStrainType(ITBaseEntityTable aCvStrainType) {
        cvStrainType = aCvStrainType;
    }

    /**
     * @return the cvTestStatus
     */
    public static ITBaseEntityTable getCvTestStatus() {
        return cvTestStatus;
    }

    /**
     * @param aCvTestStatus the cvTestStatus to set
     */
    public static void setCvTestStatus(ITBaseEntityTable aCvTestStatus) {
        cvTestStatus = aCvTestStatus;
    }

    /**
     * @return the cvTimeUnit
     */
    public static ITBaseEntityTable getCvTimeUnit() {
        return cvTimeUnit;
    }

    /**
     * @param aCvTimeUnit the cvTimeUnit to set
     */
    public static void setCvTimeUnit(ITBaseEntityTable aCvTimeUnit) {
        cvTimeUnit = aCvTimeUnit;
    }

    /**
     * @return the cvWeightUnit
     */
    public static ITBaseEntityTable getCvWeightUnit() {
        return cvWeightUnit;
    }

    /**
     * @param aCvWeightUnit the cvWeightUnit to set
     */
    public static void setCvWeightUnit(ITBaseEntityTable aCvWeightUnit) {
        cvWeightUnit = aCvWeightUnit;
    }

    /**
     * @return the cvGeneclass
     */
    public static ITBaseEntityTable getCvGeneclass() {
        return cvGeneclass;
    }

    /**
     * @param aCvGeneclass the cvGeneclass to set
     */
    public static void setCvGeneclass(ITBaseEntityTable aCvGeneclass) {
        cvGeneclass = aCvGeneclass;
    }
}