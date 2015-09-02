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

package jcms.integrationtier.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.cv.CvSexEntity;

/**
 * <b>File name:</b>  MouseQueryDTO.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides methods to capture mouse query filter criteria. <p>
 * <b>Overview:</b>  Provides methods to capture mouse query filter criteria.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 
 *
 * @author Kavitha Rama
 * @version $Revision$
 */
public class MouseQueryDTO {
    private MouseEntity mouseIDFrom;
    private MouseEntity mouseIDTo;
    private String mouseID = "";
    private String replacementTag = "";

    private int ageFrom = 0;
    private int ageTo = 0;

    private List<CvGenerationEntity> generation = null;
    private List<CvCauseOfDeathEntity> causeOfDeath = null;
    private List<CvMouseUseEntity> mouseUse = null;
    private List<CvMouseOriginEntity> origin = null;
    private List<CvMouseProtocolEntity> protocolID = null;
    private List<UseScheduleTermDTO> useScheduleTerms = null;
    private List<cvPhenotypeTermDTO> phenotypeTerms = null;
    
    private List<StrainEntity> strain = null;
    private List<CvBreedingStatusEntity> breedingStatus = null;
    private List<LifeStatusEntity> lifeStatus = null;
    private List<OwnerEntity> owner = null;
    private List<CvSexEntity> sex = null;
    private List<GeneEntity> genotype = null;
    private List<LitterEntity> litterNumber = null;
    private List<String> colonyManageOwners = new ArrayList<String>();
    
    private List<ContainerEntity> penNumber = null;
    private List<ContainerEntity> penName = null;
    private List<RoomEntity> rooms = null;
    
    private int resultSize = 500;

    private int penIDFrom = 0;
    private int penIDTo = 0;

    private String pName = "";
    private String litterID = "";
    
    private Date dobStartDate;
    private Date dobEndDate;
    private Date exitStartDate;
    private Date exitEndDate;
    private Date genotypeStartDate;
    private Date genotypeEndDate;

    private String mouseFilter = "Contains"; //Equals";
    private String strainFilter = "";
    private String ageFilter = "";
    private String ageMeasure = "Days";
    private String penIdFilter = "Between";
    private String penNameFilter = "Contains";
    private String litterFilter = "Contains";
    private String replacementTagFilter = "Contains";

    private boolean selectMouseID = false;
    private boolean selectMouseStrain = false;
    private boolean selectMouseLifeStatus = false;
    private boolean selectMouseBreedingStatus = false;
    private boolean selectMouseGeneration = false;
    private boolean selectMouseOwner = false;
    private boolean selectMouseOrigin = false;
    private boolean selectMouseSex = false;
    private boolean selectMouseCOD = false;
    private boolean selectMouseDOB = false;
    private boolean selectMouseExitDate = false;
    private boolean selectMouseLitter = false;
    private boolean selectMousePenID = false;
    private boolean selectMousePenName = false;
    private boolean selectMouseUse = false;
    private boolean selectMouseGenotype = false;
    private boolean selectMouseGenotypeDate = false;
    private boolean selectMouseAge = false;
    private boolean selectMouseMating = false;
    private boolean selectMouseParents = false;
    private boolean selectMouseCoatColor = false;
    private boolean selectMouseDiet = false;
    private boolean selectMouseProtocolID = false;
    private boolean selectUseSchedules = false;
    private boolean selectMousePhenotypes = false;
    private boolean selectMouseLitterMates = false;
    private boolean selectRoomName = false;
    private boolean selectMouseComment = false;
    private boolean selectReplacementTag = false;

    
    /**
     * @return the ageFrom
     */
    public int getAgeFrom() {
        return ageFrom;
    }

    /**
     * @param ageFrom the ageFrom to set
     */
    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    /**
     * @return the ageTo
     */
    public int getAgeTo() {
        return ageTo;
    }

    /**
     * @param ageTo the ageTo to set
     */
    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }
        
    /**
     * @return the dobStartDate
     */
    public Date getDobStartDate() {
        return dobStartDate;
    }

    /**
     * @param dobStartDate the dobStartDate to set
     */
    public void setDobStartDate(Date dobStartDate) {
        this.dobStartDate = dobStartDate;
    }

    /**
     * @return the dobEndDate
     */
    public Date getDobEndDate() {
        return dobEndDate;
    }

    /**
     * @param dobEndDate the dobEndDate to set
     */
    public void setDobEndDate(Date dobEndDate) {
        this.dobEndDate = dobEndDate;
    }

    /**
     * @return the exitStartDate
     */
    public Date getExitStartDate() {
        return exitStartDate;
    }

    /**
     * @param exitStartDate the exitStartDate to set
     */
    public void setExitStartDate(Date exitStartDate) {
        this.exitStartDate = exitStartDate;
    }

    /**
     * @return the exitEndDate
     */
    public Date getExitEndDate() {
        return exitEndDate;
    }

    /**
     * @param exitEndDate the exitEndDate to set
     */
    public void setExitEndDate(Date exitEndDate) {
        this.exitEndDate = exitEndDate;
    }

    /**
     * @return the genotypeStartDate
     */
    public Date getGenotypeStartDate() {
        return genotypeStartDate;
    }

    /**
     * @param genotypeStartDate the genotypeStartDate to set
     */
    public void setGenotypeStartDate(Date genotypeStartDate) {
        this.genotypeStartDate = genotypeStartDate;
    }

    /**
     * @return the genotypeEndDate
     */
    public Date getGenotypeEndDate() {
        return genotypeEndDate;
    }

    /**
     * @param genotypeEndDate the genotypeEndDate to set
     */
    public void setGenotypeEndDate(Date genotypeEndDate) {
        this.genotypeEndDate = genotypeEndDate;
    }

    /**
     * @return the mouseFilter
     */
    public String getMouseFilter() {
        return mouseFilter;
    }

    /**
     * @param mouseFilter the mouseFilter to set
     */
    public void setMouseFilter(String mouseFilter) {
        this.mouseFilter = mouseFilter;
    }

    /**
     * @return the strainFilter
     */
    public String getStrainFilter() {
        return strainFilter;
    }

    /**
     * @param strainFilter the strainFilter to set
     */
    public void setStrainFilter(String strainFilter) {
        this.strainFilter = strainFilter;
    }

    /**
     * @return the ageFilter
     */
    public String getAgeFilter() {
        return ageFilter;
    }

    /**
     * @param ageFilter the ageFilter to set
     */
    public void setAgeFilter(String ageFilter) {
        this.ageFilter = ageFilter;
    }

    /**
     * @return the ageMeasure
     */
    public String getAgeMeasure() {
        return ageMeasure;
    }

    /**
     * @param ageMeasure the ageMeasure to set
     */
    public void setAgeMeasure(String ageMeasure) {
        this.ageMeasure = ageMeasure;
    }

    /**
     * @return the selectMouseID
     */
    public boolean isSelectMouseID() {
        return selectMouseID;
    }

    /**
     * @param selectMouseID the selectMouseID to set
     */
    public void setSelectMouseID(boolean selectMouseID) {
        this.selectMouseID = selectMouseID;
    }

    /**
     * @return the selectMouseStrain
     */
    public boolean isSelectMouseStrain() {
        return selectMouseStrain;
    }

    /**
     * @param selectMouseStrain the selectMouseStrain to set
     */
    public void setSelectMouseStrain(boolean selectMouseStrain) {
        this.selectMouseStrain = selectMouseStrain;
    }

    /**
     * @return the selectMouseLifeStatus
     */
    public boolean isSelectMouseLifeStatus() {
        return selectMouseLifeStatus;
    }

    /**
     * @param selectMouseLifeStatus the selectMouseLifeStatus to set
     */
    public void setSelectMouseLifeStatus(boolean selectMouseLifeStatus) {
        this.selectMouseLifeStatus = selectMouseLifeStatus;
    }

    /**
     * @return the selectMouseBreedingStatus
     */
    public boolean isSelectMouseBreedingStatus() {
        return selectMouseBreedingStatus;
    }

    /**
     * @param selectMouseBreedingStatus the selectMouseBreedingStatus to set
     */
    public void setSelectMouseBreedingStatus(boolean selectMouseBreedingStatus) {
        this.selectMouseBreedingStatus = selectMouseBreedingStatus;
    }

    /**
     * @return the selectMouseGeneration
     */
    public boolean isSelectMouseGeneration() {
        return selectMouseGeneration;
    }

    /**
     * @param selectMouseGeneration the selectMouseGeneration to set
     */
    public void setSelectMouseGeneration(boolean selectMouseGeneration) {
        this.selectMouseGeneration = selectMouseGeneration;
    }

    /**
     * @return the selectMouseOwner
     */
    public boolean isSelectMouseOwner() {
        return selectMouseOwner;
    }

    /**
     * @param selectMouseOwner the selectMouseOwner to set
     */
    public void setSelectMouseOwner(boolean selectMouseOwner) {
        this.selectMouseOwner = selectMouseOwner;
    }

    /**
     * @return the selectMouseOrigin
     */
    public boolean isSelectMouseOrigin() {
        return selectMouseOrigin;
    }

    /**
     * @param selectMouseOrigin the selectMouseOrigin to set
     */
    public void setSelectMouseOrigin(boolean selectMouseOrigin) {
        this.selectMouseOrigin = selectMouseOrigin;
    }

    /**
     * @return the selectMouseSex
     */
    public boolean isSelectMouseSex() {
        return selectMouseSex;
    }

    /**
     * @param selectMouseSex the selectMouseSex to set
     */
    public void setSelectMouseSex(boolean selectMouseSex) {
        this.selectMouseSex = selectMouseSex;
    }

    /**
     * @return the selectMouseCOD
     */
    public boolean isSelectMouseCOD() {
        return selectMouseCOD;
    }

    /**
     * @param selectMouseCOD the selectMouseCOD to set
     */
    public void setSelectMouseCOD(boolean selectMouseCOD) {
        this.selectMouseCOD = selectMouseCOD;
    }

    /**
     * @return the selectMouseDOB
     */
    public boolean isSelectMouseDOB() {
        return selectMouseDOB;
    }

    /**
     * @param selectMouseDOB the selectMouseDOB to set
     */
    public void setSelectMouseDOB(boolean selectMouseDOB) {
        this.selectMouseDOB = selectMouseDOB;
    }

    /**
     * @return the selectMouseExitDate
     */
    public boolean isSelectMouseExitDate() {
        return selectMouseExitDate;
    }

    /**
     * @param selectMouseExitDate the selectMouseExitDate to set
     */
    public void setSelectMouseExitDate(boolean selectMouseExitDate) {
        this.selectMouseExitDate = selectMouseExitDate;
    }

    /**
     * @return the selectMouseLitter
     */
    public boolean isSelectMouseLitter() {
        return selectMouseLitter;
    }

    /**
     * @param selectMouseLitter the selectMouseLitter to set
     */
    public void setSelectMouseLitter(boolean selectMouseLitter) {
        this.selectMouseLitter = selectMouseLitter;
    }

    /**
     * @return the selectMousePenID
     */
    public boolean isSelectMousePenID() {
        return selectMousePenID;
    }

    /**
     * @param selectMousePenID the selectMousePenID to set
     */
    public void setSelectMousePenID(boolean selectMousePenID) {
        this.selectMousePenID = selectMousePenID;
    }

    /**
     * @return the selectMousePenName
     */
    public boolean isSelectMousePenName() {
        return selectMousePenName;
    }

    /**
     * @param selectMousePenName the selectMousePenName to set
     */
    public void setSelectMousePenName(boolean selectMousePenName) {
        this.selectMousePenName = selectMousePenName;
    }

    /**
     * @return the selectMouseUse
     */
    public boolean isSelectMouseUse() {
        return selectMouseUse;
    }

    /**
     * @param selectMouseUse the selectMouseUse to set
     */
    public void setSelectMouseUse(boolean selectMouseUse) {
        this.selectMouseUse = selectMouseUse;
    }

    /**
     * @return the selectMouseGenotype
     */
    public boolean isSelectMouseGenotype() {
        return selectMouseGenotype;
    }

    /**
     * @param selectMouseGenotype the selectMouseGenotype to set
     */
    public void setSelectMouseGenotype(boolean selectMouseGenotype) {
        this.selectMouseGenotype = selectMouseGenotype;
    }

    /**
     * @return the selectMouseGenotypeDate
     */
    public boolean isSelectMouseGenotypeDate() {
        return selectMouseGenotypeDate;
    }

    /**
     * @param selectMouseGenotypeDate the selectMouseGenotypeDate to set
     */
    public void setSelectMouseGenotypeDate(boolean selectMouseGenotypeDate) {
        this.selectMouseGenotypeDate = selectMouseGenotypeDate;
    }

    /**
     * @return the selectMouseAge
     */
    public boolean isSelectMouseAge() {
        return selectMouseAge;
    }

    /**
     * @param selectMouseAge the selectMouseAge to set
     */
    public void setSelectMouseAge(boolean selectMouseAge) {
        this.selectMouseAge = selectMouseAge;
    }

    /**
     * @return the selectMouseMating
     */
    public boolean isSelectMouseMating() {
        return selectMouseMating;
    }

    /**
     * @param selectMouseMating the selectMouseMating to set
     */
    public void setSelectMouseMating(boolean selectMouseMating) {
        this.selectMouseMating = selectMouseMating;
    }

    /**
     * @return the selectMouseParents
     */
    public boolean isSelectMouseParents() {
        return selectMouseParents;
    }

    /**
     * @param selectMouseParents the selectMouseParents to set
     */
    public void setSelectMouseParents(boolean selectMouseParents) {
        this.selectMouseParents = selectMouseParents;
    }

    /**
     * @return the selectMouseCoatColor
     */
    public boolean isSelectMouseCoatColor() {
        return selectMouseCoatColor;
    }

    /**
     * @param selectMouseCoatColor the selectMouseCoatColor to set
     */
    public void setSelectMouseCoatColor(boolean selectMouseCoatColor) {
        this.selectMouseCoatColor = selectMouseCoatColor;
    }

    /**
     * @return the selectMouseDiet
     */
    public boolean isSelectMouseDiet() {
        return selectMouseDiet;
    }

    /**
     * @param selectMouseDiet the selectMouseDiet to set
     */
    public void setSelectMouseDiet(boolean selectMouseDiet) {
        this.selectMouseDiet = selectMouseDiet;
    }

    /**
     * @return the selectMouseProtocolID
     */
    public boolean isSelectMouseProtocolID() {
        return selectMouseProtocolID;
    }

    /**
     * @param selectMouseProtocolID the selectMouseProtocolID to set
     */
    public void setSelectMouseProtocolID(boolean selectMouseProtocolID) {
        this.selectMouseProtocolID = selectMouseProtocolID;
    }

    /**
     * @return the selectMouseLitterMates
     */
    public boolean isSelectMouseLitterMates() {
        return selectMouseLitterMates;
    }

    /**
     * @param selectMouseLitterMates the selectMouseLitterMates to set
     */
    public void setSelectMouseLitterMates(boolean selectMouseLitterMates) {
        this.selectMouseLitterMates = selectMouseLitterMates;
    }

    /**
     * @return the generation
     */
    public List<CvGenerationEntity> getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(List<CvGenerationEntity> generation) {
        this.generation = generation;
    }

    /**
     * @return the causeOfDeath
     */
    public List<CvCauseOfDeathEntity> getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * @param causeOfDeath the causeOfDeath to set
     */
    public void setCauseOfDeath(List<CvCauseOfDeathEntity> causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    /**
     * @return the mouseUse
     */
    public List<CvMouseUseEntity> getMouseUse() {
        return mouseUse;
    }

    /**
     * @param mouseUse the mouseUse to set
     */
    public void setMouseUse(List<CvMouseUseEntity> mouseUse) {
        this.mouseUse = mouseUse;
    }

    /**
     * @return the origin
     */
    public List<CvMouseOriginEntity> getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(List<CvMouseOriginEntity> origin) {
        this.origin = origin;
    }

    /**
     * @return the strain
     */
    public List<StrainEntity> getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(List<StrainEntity> strain) {
        this.strain = strain;
    }

    /**
     * @return the breedingStatus
     */
    public List<CvBreedingStatusEntity> getBreedingStatus() {
        return breedingStatus;
    }

    /**
     * @param breedingStatus the breedingStatus to set
     */
    public void setBreedingStatus(List<CvBreedingStatusEntity> breedingStatus) {
        this.breedingStatus = breedingStatus;
    }

    /**
     * @return the lifeStatus
     */
    public List<LifeStatusEntity> getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(List<LifeStatusEntity> lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the owner
     */
    public List<OwnerEntity> getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(List<OwnerEntity> owner) {
        this.owner = owner;
    }

    /**
     * @return the sex
     */
    public List<CvSexEntity> getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(List<CvSexEntity> sex) {
        this.sex = sex;
    }

    /**
     * @return the genotype
     */
    public List<GeneEntity> getGenotype() {
        return genotype;
    }

    /**
     * @param genotype the genotype to set
     */
    public void setGenotype(List<GeneEntity> genotype) {
        this.genotype = genotype;
    }

    /**
     * @return the litterNumber
     */
    public List<LitterEntity> getLitterNumber() {
        return litterNumber;
    }

    /**
     * @param litterNumber the litterNumber to set
     */
    public void setLitterNumber(List<LitterEntity> litterNumber) {
        this.litterNumber = litterNumber;
    }

    /**
     * @return the mouseIDFrom
     */
    public MouseEntity getMouseIDFrom() {
        return mouseIDFrom;
    }

    /**
     * @param mouseIDFrom the mouseIDFrom to set
     */
    public void setMouseIDFrom(MouseEntity mouseIDFrom) {
        this.mouseIDFrom = mouseIDFrom;
    }

    /**
     * @return the mouseIDTo
     */
    public MouseEntity getMouseIDTo() {
        return mouseIDTo;
    }

    /**
     * @param mouseIDTo the mouseIDTo to set
     */
    public void setMouseIDTo(MouseEntity mouseIDTo) {
        this.mouseIDTo = mouseIDTo;
    }

    /**
     * @return the penNumber
     */
    public List<ContainerEntity> getPenNumber() {
        return penNumber;
    }

    /**
     * @param penNumber the penNumber to set
     */
    public void setPenNumber(List<ContainerEntity> penNumber) {
        this.penNumber = penNumber;
    }

    /**
     * @return the penName
     */
    public List<ContainerEntity> getPenName() {
        return penName;
    }

    /**
     * @param penName the penName to set
     */
    public void setPenName(List<ContainerEntity> penName) {
        this.penName = penName;
    }

    /**
     * @return the mouseID
     */
    public String getMouseID() {
        return mouseID;
    }

    /**
     * @param mouseID the mouseID to set
     */
    public void setMouseID(String mouseID) {
        this.mouseID = mouseID;
    }

    /**
     * @return the penIdFilter
     */
    public String getPenIdFilter() {
        return penIdFilter;
    }

    /**
     * @param penIdFilter the penIdFilter to set
     */
    public void setPenIdFilter(String penIdFilter) {
        this.penIdFilter = penIdFilter;
    }

    /**
     * @return the penNameFilter
     */
    public String getPenNameFilter() {
        return penNameFilter;
    }

    /**
     * @param penNameFilter the penNameFilter to set
     */
    public void setPenNameFilter(String penNameFilter) {
        this.penNameFilter = penNameFilter;
    }

    /**
     * @return the litterFilter
     */
    public String getLitterFilter() {
        return litterFilter;
    }

    /**
     * @param litterFilter the litterFilter to set
     */
    public void setLitterFilter(String litterFilter) {
        this.litterFilter = litterFilter;
    }

    /**
     * @return the penIDFrom
     */
    public int getPenIDFrom() {
        return penIDFrom;
    }

    /**
     * @param penIDFrom the penIDFrom to set
     */
    public void setPenIDFrom(int penIDFrom) {
        this.penIDFrom = penIDFrom;
    }

    /**
     * @return the penIDTo
     */
    public int getPenIDTo() {
        return penIDTo;
    }

    /**
     * @param penIDTo the penIDTo to set
     */
    public void setPenIDTo(int penIDTo) {
        this.penIDTo = penIDTo;
    }

    /**
     * @return the pName
     */
    public String getpName() {
        return pName;
    }

    /**
     * @param pName the pName to set
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * @return the litterID
     */
    public String getLitterID() {
        return litterID;
    }

    /**
     * @param litterID the litterID to set
     */
    public void setLitterID(String litterID) {
        this.litterID = litterID;
    }

    /**
     * @return the resultSize
     */
    public int getResultSize() {
        return resultSize;
    }

    /**
     * @param resultSize the resultSize to set
     */
    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    /**
     * @return the rooms
     */
    public List<RoomEntity> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the selectRoomName
     */
    public boolean isSelectRoomName() {
        return selectRoomName;
    }

    /**
     * @param selectRoomName the selectRoomName to set
     */
    public void setSelectRoomName(boolean selectRoomName) {
        this.selectRoomName = selectRoomName;
    }

    /**
     * @return the selectMouseComment
     */
    public boolean isSelectMouseComment() {
        return selectMouseComment;
    }

    /**
     * @param selectMouseComment the selectMouseComment to set
     */
    public void setSelectMouseComment(boolean selectMouseComment) {
        this.selectMouseComment = selectMouseComment;
    }

    /**
     * @return the useScheduleTerms
     */
    public List<UseScheduleTermDTO> getUseScheduleTerms() {
        return useScheduleTerms;
    }

    /**
     * @param useScheduleTerms the useScheduleTerms to set
     */
    public void setUseScheduleTerms(List<UseScheduleTermDTO> useScheduleTerms) {
        this.useScheduleTerms = useScheduleTerms;
    }

    /**
     * @return the selectUseSchedules
     */
    public boolean isSelectUseSchedules() {
        return selectUseSchedules;
    }

    /**
     * @param selectUseSchedules the selectUseSchedules to set
     */
    public void setSelectUseSchedules(boolean selectUseSchedules) {
        this.selectUseSchedules = selectUseSchedules;
    }
    
    /**
     * @return the phenotypeTerms
     */
    public List<cvPhenotypeTermDTO> getPhenotypeTerms() {
        return phenotypeTerms;
    }

    /**
     * @param phenotypeTerms the phenotypeTerms to set
     */
    public void setPhenotypeTerms(List<cvPhenotypeTermDTO> phenotypeTerms) {
        this.phenotypeTerms = phenotypeTerms;
    }

    /**
     * @return the selectMousePhenotypes
     */
    public boolean isSelectMousePhenotypes() {
        return selectMousePhenotypes;
    }

    /**
     * @param selectMousePhenotypes the selectMousePhenotypes to set
     */
    public void setSelectMousePhenotypes(boolean selectMousePhenotypes) {
        this.selectMousePhenotypes = selectMousePhenotypes;
    }

    /**
     * @return the protocolID
     */
    public List<CvMouseProtocolEntity> getProtocolID() {
        return protocolID;
    }

    /**
     * @param protocolID the protocolID to set
     */
    public void setProtocolID(List<CvMouseProtocolEntity> protocolID) {
        this.protocolID = protocolID;
    }

    /**
     * @return the replacementTagFilter
     */
    public String getReplacementTagFilter() {
        return replacementTagFilter;
    }

    /**
     * @param replacementTagFilter the replacementTagFilter to set
     */
    public void setReplacementTagFilter(String replacementTagFilter) {
        this.replacementTagFilter = replacementTagFilter;
    }

    /**
     * @return the replacementTag
     */
    public String getReplacementTag() {
        return replacementTag;
    }

    /**
     * @param replacementTag the replacementTag to set
     */
    public void setReplacementTag(String replacementTag) {
        this.replacementTag = replacementTag;
    }

    /**
     * @return the selectReplacementTag
     */
    public boolean isSelectReplacementTag() {
        return selectReplacementTag;
    }

    /**
     * @param selectReplacementTag the selectReplacementTag to set
     */
    public void setSelectReplacementTag(boolean selectReplacementTag) {
        this.selectReplacementTag = selectReplacementTag;
    }

    /**
     * @return the colonyManageOwners
     */
    public List<String> getColonyManageOwners() {
        return colonyManageOwners;
    }

    /**
     * @param colonyManageOwners the colonyManageOwners to set
     */
    public void setColonyManageOwners(List<String> colonyManageOwners) {
        this.colonyManageOwners = colonyManageOwners;
    }
}