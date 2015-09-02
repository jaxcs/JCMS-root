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

import java.util.Date;
import java.util.List;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.cv.CvGenerationEntity;

/**
 * <b>File name:</b>  MatingQueryDTO.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides methods to capture mating query filter criteria. <p>
 * <b>Overview:</b>  Provides methods to capture mating query page filter criteria  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 *
 * @author Kavitha Rama
 * @version $Revision$
 */        
public class MatingQueryDTO {

    private MatingEntity matingIDFrom;
    private MatingEntity matingIDTo;
    
    private List<StrainEntity> litterStrain = null;
    private List<CvGenerationEntity> litterGeneration = null;
    private List<OwnerEntity> matingOwner = null;
    private List<ContainerEntity> penName = null;
    private List<ContainerEntity> penNumber = null;

    private int resultSize = 500;

    private int penIDFrom = 0;
    private int penIDTo = 0;

    private int mIDFrom = 0;
    private int mIDTo = 0;

    private String pName = "";  

    private Date matingStartDate;
    private Date matingEndDate;

    private Date retiredStartDate;
    private Date retiredEndDate;

    private String matingFilter = "Between";
    private String penIdFilter = "Between";
    private String penNameFilter = "Contains";

    private String[] matingStatus;
    private List<CvCrossstatusEntity> crossStatus = null;

    private boolean selectTotalLitters = false;
    private boolean selectTotalPups = false;
    private boolean selectTotalMales = false;
    private boolean selectTotalFemales = false;
    private boolean selectTotalLittersDead = false;
    private boolean selectLitterIDs = false;
    private boolean selectBirthDates = false;
    private boolean selectTotalPupsBornDead = false;
    private boolean selectTotalPupsCulledAtWean = false;
    private boolean selectTotalPupsMissingAtWean = false;

    private boolean selectMatingID = false;
    private boolean selectMatingDates = false;
    private boolean selectMatingStatus = false;
    private boolean selectMatingStrain = false;
    private boolean selectMatingGeneration = false;
    private boolean selectMatingOwner = false;
    private boolean selectMatingPenId = false;
    private boolean selectMatingPenName = false;

    private boolean selectDam1ID = false;
    private boolean selectDam1Strain = false;
    private boolean selectDam1Stock = false;
    private boolean selectDam1Gen = false;
    private boolean selectDam1Genotype = false;
    private boolean selectDam1DOB = false;
    private boolean selectDam1PlugDate = false;

    private boolean selectDam2ID = false;
    private boolean selectDam2Strain = false;
    private boolean selectDam2Stock = false;
    private boolean selectDam2Gen = false;
    private boolean selectDam2Genotype = false;
    private boolean selectDam2DOB = false;
    private boolean selectDam2PlugDate = false;

    private boolean selectSireID = false;
    private boolean selectSireStrain = false;
    private boolean selectSireStock = false;
    private boolean selectSireGen = false;
    private boolean selectSireGenotype = false;
    private boolean selectSireDOB = false;

    private boolean selectDateRetired = false;
    private boolean selectWeanTime = false;
    private boolean selectNeedsTyping = false;

    
    /**
     * @return the matingStartDate
     */
    public Date getMatingStartDate() {
        return matingStartDate;
    }

    /**
     * @param matingStartDate the matingStartDate to set
     */
    public void setMatingStartDate(Date matingStartDate) {
        this.matingStartDate = matingStartDate;
    }

    /**
     * @return the matingEndDate
     */
    public Date getMatingEndDate() {
        return matingEndDate;
    }

    /**
     * @param matingEndDate the matingEndDate to set
     */
    public void setMatingEndDate(Date matingEndDate) {
        this.matingEndDate = matingEndDate;
    }
    
    /**
     * @return the matingStatus
     */
    public String[] getMatingStatus() {
        return matingStatus;
    }

    /**
     * @param matingStatus the matingStatus to set
     */
    public void setMatingStatus(String[] matingStatus) {
        this.matingStatus = matingStatus;
    }

    /**
     * @return the selectMatingID
     */
    public boolean isSelectMatingID() {
        return selectMatingID;
    }

    /**
     * @param selectMatingID the selectMatingID to set
     */
    public void setSelectMatingID(boolean selectMatingID) {
        this.selectMatingID = selectMatingID;
    }

    /**
     * @return the selectMatingDates
     */
    public boolean isSelectMatingDates() {
        return selectMatingDates;
    }

    /**
     * @param selectMatingDates the selectMatingDates to set
     */
    public void setSelectMatingDates(boolean selectMatingDates) {
        this.selectMatingDates = selectMatingDates;
    }

    /**
     * @return the selectMatingStatus
     */
    public boolean isSelectMatingStatus() {
        return selectMatingStatus;
    }

    /**
     * @param selectMatingStatus the selectMatingStatus to set
     */
    public void setSelectMatingStatus(boolean selectMatingStatus) {
        this.selectMatingStatus = selectMatingStatus;
    }

    /**
     * @return the selectMatingStrain
     */
    public boolean isSelectMatingStrain() {
        return selectMatingStrain;
    }

    /**
     * @param selectMatingStrain the selectMatingStrain to set
     */
    public void setSelectMatingStrain(boolean selectMatingStrain) {
        this.selectMatingStrain = selectMatingStrain;
    }

    /**
     * @return the selectMatingGeneration
     */
    public boolean isSelectMatingGeneration() {
        return selectMatingGeneration;
    }

    /**
     * @param selectMatingGeneration the selectMatingGeneration to set
     */
    public void setSelectMatingGeneration(boolean selectMatingGeneration) {
        this.selectMatingGeneration = selectMatingGeneration;
    }

    /**
     * @return the selectMatingOwner
     */
    public boolean isSelectMatingOwner() {
        return selectMatingOwner;
    }

    /**
     * @param selectMatingOwner the selectMatingOwner to set
     */
    public void setSelectMatingOwner(boolean selectMatingOwner) {
        this.selectMatingOwner = selectMatingOwner;
    }

    /**
     * @return the selectMatingPenId
     */
    public boolean isSelectMatingPenId() {
        return selectMatingPenId;
    }

    /**
     * @param selectMatingPenId the selectMatingPenId to set
     */
    public void setSelectMatingPenId(boolean selectMatingPenId) {
        this.selectMatingPenId = selectMatingPenId;
    }

    /**
     * @return the selectMatingPenName
     */
    public boolean isSelectMatingPenName() {
        return selectMatingPenName;
    }

    /**
     * @param selectMatingPenName the selectMatingPenName to set
     */
    public void setSelectMatingPenName(boolean selectMatingPenName) {
        this.selectMatingPenName = selectMatingPenName;
    }

    /**
     * @return the selectDam1ID
     */
    public boolean isSelectDam1ID() {
        return selectDam1ID;
    }

    /**
     * @param selectDam1ID the selectDam1ID to set
     */
    public void setSelectDam1ID(boolean selectDam1ID) {
        this.selectDam1ID = selectDam1ID;
    }

    /**
     * @return the selectDam1Strain
     */
    public boolean isSelectDam1Strain() {
        return selectDam1Strain;
    }

    /**
     * @param selectDam1Strain the selectDam1Strain to set
     */
    public void setSelectDam1Strain(boolean selectDam1Strain) {
        this.selectDam1Strain = selectDam1Strain;
    }

    /**
     * @return the selectDam1Stock
     */
    public boolean isSelectDam1Stock() {
        return selectDam1Stock;
    }

    /**
     * @param selectDam1Stock the selectDam1Stock to set
     */
    public void setSelectDam1Stock(boolean selectDam1Stock) {
        this.selectDam1Stock = selectDam1Stock;
    }

    /**
     * @return the selectDam1Gen
     */
    public boolean isSelectDam1Gen() {
        return selectDam1Gen;
    }

    /**
     * @param selectDam1Gen the selectDam1Gen to set
     */
    public void setSelectDam1Gen(boolean selectDam1Gen) {
        this.selectDam1Gen = selectDam1Gen;
    }

    /**
     * @return the selectDam1Genotype
     */
    public boolean isSelectDam1Genotype() {
        return selectDam1Genotype;
    }

    /**
     * @param selectDam1Genotype the selectDam1Genotype to set
     */
    public void setSelectDam1Genotype(boolean selectDam1Genotype) {
        this.selectDam1Genotype = selectDam1Genotype;
    }

    /**
     * @return the selectDam1DOB
     */
    public boolean isSelectDam1DOB() {
        return selectDam1DOB;
    }

    /**
     * @param selectDam1DOB the selectDam1DOB to set
     */
    public void setSelectDam1DOB(boolean selectDam1DOB) {
        this.selectDam1DOB = selectDam1DOB;
    }

    /**
     * @return the selectDam1PlugDate
     */
    public boolean isSelectDam1PlugDate() {
        return selectDam1PlugDate;
    }

    /**
     * @param selectDam1PlugDate the selectDam1PlugDate to set
     */
    public void setSelectDam1PlugDate(boolean selectDam1PlugDate) {
        this.selectDam1PlugDate = selectDam1PlugDate;
    }

    /**
     * @return the selectDam2ID
     */
    public boolean isSelectDam2ID() {
        return selectDam2ID;
    }

    /**
     * @param selectDam2ID the selectDam2ID to set
     */
    public void setSelectDam2ID(boolean selectDam2ID) {
        this.selectDam2ID = selectDam2ID;
    }

    /**
     * @return the selectDam2Strain
     */
    public boolean isSelectDam2Strain() {
        return selectDam2Strain;
    }

    /**
     * @param selectDam2Strain the selectDam2Strain to set
     */
    public void setSelectDam2Strain(boolean selectDam2Strain) {
        this.selectDam2Strain = selectDam2Strain;
    }

    /**
     * @return the selectDam2Stock
     */
    public boolean isSelectDam2Stock() {
        return selectDam2Stock;
    }

    /**
     * @param selectDam2Stock the selectDam2Stock to set
     */
    public void setSelectDam2Stock(boolean selectDam2Stock) {
        this.selectDam2Stock = selectDam2Stock;
    }

    /**
     * @return the selectDam2Gen
     */
    public boolean isSelectDam2Gen() {
        return selectDam2Gen;
    }

    /**
     * @param selectDam2Gen the selectDam2Gen to set
     */
    public void setSelectDam2Gen(boolean selectDam2Gen) {
        this.selectDam2Gen = selectDam2Gen;
    }

    /**
     * @return the selectDam2Genotype
     */
    public boolean isSelectDam2Genotype() {
        return selectDam2Genotype;
    }

    /**
     * @param selectDam2Genotype the selectDam2Genotype to set
     */
    public void setSelectDam2Genotype(boolean selectDam2Genotype) {
        this.selectDam2Genotype = selectDam2Genotype;
    }

    /**
     * @return the selectDam2DOB
     */
    public boolean isSelectDam2DOB() {
        return selectDam2DOB;
    }

    /**
     * @param selectDam2DOB the selectDam2DOB to set
     */
    public void setSelectDam2DOB(boolean selectDam2DOB) {
        this.selectDam2DOB = selectDam2DOB;
    }

    /**
     * @return the selectDam2PlugDate
     */
    public boolean isSelectDam2PlugDate() {
        return selectDam2PlugDate;
    }

    /**
     * @param selectDam2PlugDate the selectDam2PlugDate to set
     */
    public void setSelectDam2PlugDate(boolean selectDam2PlugDate) {
        this.selectDam2PlugDate = selectDam2PlugDate;
    }

    /**
     * @return the selectSireID
     */
    public boolean isSelectSireID() {
        return selectSireID;
    }

    /**
     * @param selectSireID the selectSireID to set
     */
    public void setSelectSireID(boolean selectSireID) {
        this.selectSireID = selectSireID;
    }

    /**
     * @return the selectSireStrain
     */
    public boolean isSelectSireStrain() {
        return selectSireStrain;
    }

    /**
     * @param selectSireStrain the selectSireStrain to set
     */
    public void setSelectSireStrain(boolean selectSireStrain) {
        this.selectSireStrain = selectSireStrain;
    }

    /**
     * @return the selectSireStock
     */
    public boolean isSelectSireStock() {
        return selectSireStock;
    }

    /**
     * @param selectSireStock the selectSireStock to set
     */
    public void setSelectSireStock(boolean selectSireStock) {
        this.selectSireStock = selectSireStock;
    }

    /**
     * @return the selectSireGen
     */
    public boolean isSelectSireGen() {
        return selectSireGen;
    }

    /**
     * @param selectSireGen the selectSireGen to set
     */
    public void setSelectSireGen(boolean selectSireGen) {
        this.selectSireGen = selectSireGen;
    }

    /**
     * @return the selectSireGenotype
     */
    public boolean isSelectSireGenotype() {
        return selectSireGenotype;
    }

    /**
     * @param selectSireGenotype the selectSireGenotype to set
     */
    public void setSelectSireGenotype(boolean selectSireGenotype) {
        this.selectSireGenotype = selectSireGenotype;
    }

    /**
     * @return the selectSireDOB
     */
    public boolean isSelectSireDOB() {
        return selectSireDOB;
    }

    /**
     * @param selectSireDOB the selectSireDOB to set
     */
    public void setSelectSireDOB(boolean selectSireDOB) {
        this.selectSireDOB = selectSireDOB;
    }

    /**
     * @return the selectDateRetired
     */
    public boolean isSelectDateRetired() {
        return selectDateRetired;
    }

    /**
     * @param selectDateRetired the selectDateRetired to set
     */
    public void setSelectDateRetired(boolean selectDateRetired) {
        this.selectDateRetired = selectDateRetired;
    }

    /**
     * @return the selectWeanTime
     */
    public boolean isSelectWeanTime() {
        return selectWeanTime;
    }

    /**
     * @param selectWeanTime the selectWeanTime to set
     */
    public void setSelectWeanTime(boolean selectWeanTime) {
        this.selectWeanTime = selectWeanTime;
    }

    /**
     * @return the selectNeedsTyping
     */
    public boolean isSelectNeedsTyping() {
        return selectNeedsTyping;
    }

    /**
     * @param selectNeedsTyping the selectNeedsTyping to set
     */
    public void setSelectNeedsTyping(boolean selectNeedsTyping) {
        this.selectNeedsTyping = selectNeedsTyping;
    }

    /**
     * @return the matingFilter
     */
    public String getMatingFilter() {
        return matingFilter;
    }

    /**
     * @param matingFilter the matingFilter to set
     */
    public void setMatingFilter(String matingFilter) {
        this.matingFilter = matingFilter;
    }

    /**
     * @return the matingIDFrom
     */
    public MatingEntity getMatingIDFrom() {
        return matingIDFrom;
    }

    /**
     * @param matingIDFrom the matingIDFrom to set
     */
    public void setMatingIDFrom(MatingEntity matingIDFrom) {
        this.matingIDFrom = matingIDFrom;
    }

    /**
     * @return the matingIDTo
     */
    public MatingEntity getMatingIDTo() {
        return matingIDTo;
    }

    /**
     * @param matingIDTo the matingIDTo to set
     */
    public void setMatingIDTo(MatingEntity matingIDTo) {
        this.matingIDTo = matingIDTo;
    }

    /**
     * @return the litterStrain
     */
    public List<StrainEntity> getLitterStrain() {
        return litterStrain;
    }

    /**
     * @param litterStrain the litterStrain to set
     */
    public void setLitterStrain(List<StrainEntity> litterStrain) {
        this.litterStrain = litterStrain;
    }

    /**
     * @return the litterGeneration
     */
    public List<CvGenerationEntity> getLitterGeneration() {
        return litterGeneration;
    }

    /**
     * @param litterGeneration the litterGeneration to set
     */
    public void setLitterGeneration(List<CvGenerationEntity> litterGeneration) {
        this.litterGeneration = litterGeneration;
    }

    /**
     * @return the matingOwner
     */
    public List<OwnerEntity> getMatingOwner() {
        return matingOwner;
    }

    /**
     * @param matingOwner the matingOwner to set
     */
    public void setMatingOwner(List<OwnerEntity> matingOwner) {
        this.matingOwner = matingOwner;
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
     * @return the selectTotalLitters
     */
    public boolean isSelectTotalLitters() {
        return selectTotalLitters;
    }

    /**
     * @param selectTotalLitters the selectTotalLitters to set
     */
    public void setSelectTotalLitters(boolean selectTotalLitters) {
        this.selectTotalLitters = selectTotalLitters;
    }

    /**
     * @return the selectTotalPups
     */
    public boolean isSelectTotalPups() {
        return selectTotalPups;
    }

    /**
     * @param selectTotalPups the selectTotalPups to set
     */
    public void setSelectTotalPups(boolean selectTotalPups) {
        this.selectTotalPups = selectTotalPups;
    }

    /**
     * @return the selectTotalMales
     */
    public boolean isSelectTotalMales() {
        return selectTotalMales;
    }

    /**
     * @param selectTotalMales the selectTotalMales to set
     */
    public void setSelectTotalMales(boolean selectTotalMales) {
        this.selectTotalMales = selectTotalMales;
    }

    /**
     * @return the selectTotalFemales
     */
    public boolean isSelectTotalFemales() {
        return selectTotalFemales;
    }

    /**
     * @param selectTotalFemales the selectTotalFemales to set
     */
    public void setSelectTotalFemales(boolean selectTotalFemales) {
        this.selectTotalFemales = selectTotalFemales;
    }

    /**
     * @return the selectTotalLittersDead
     */
    public boolean isSelectTotalLittersDead() {
        return selectTotalLittersDead;
    }

    /**
     * @param selectTotalLittersDead the selectTotalLittersDead to set
     */
    public void setSelectTotalLittersDead(boolean selectTotalLittersDead) {
        this.selectTotalLittersDead = selectTotalLittersDead;
    }

    /**
     * @return the selectBirthDates
     */
    public boolean isSelectBirthDates() {
        return selectBirthDates;
    }

    /**
     * @param selectBirthDates the selectBirthDates to set
     */
    public void setSelectBirthDates(boolean selectBirthDates) {
        this.selectBirthDates = selectBirthDates;
    }

    /**
     * @return the retiredStartDate
     */
    public Date getRetiredStartDate() {
        return retiredStartDate;
    }

    /**
     * @param retiredStartDate the retiredStartDate to set
     */
    public void setRetiredStartDate(Date retiredStartDate) {
        this.retiredStartDate = retiredStartDate;
    }

    /**
     * @return the retiredEndDate
     */
    public Date getRetiredEndDate() {
        return retiredEndDate;
    }

    /**
     * @param retiredEndDate the retiredEndDate to set
     */
    public void setRetiredEndDate(Date retiredEndDate) {
        this.retiredEndDate = retiredEndDate;
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
     * @return the mIDFrom
     */
    public int getmIDFrom() {
        return mIDFrom;
    }

    /**
     * @param mIDFrom the mIDFrom to set
     */
    public void setmIDFrom(int mIDFrom) {
        this.mIDFrom = mIDFrom;
    }

    /**
     * @return the mIDTo
     */
    public int getmIDTo() {
        return mIDTo;
    }

    /**
     * @param mIDTo the mIDTo to set
     */
    public void setmIDTo(int mIDTo) {
        this.mIDTo = mIDTo;
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
     * @return the crossStatus
     */
    public List<CvCrossstatusEntity> getCrossStatus() {
        return crossStatus;
    }

    /**
     * @param crossStatus the crossStatus to set
     */
    public void setCrossStatus(List<CvCrossstatusEntity> crossStatus) {
        this.crossStatus = crossStatus;
    }

    /**
     * @return the selectLitterIDs
     */
    public boolean isSelectLitterIDs() {
        return selectLitterIDs;
    }

    /**
     * @param selectLitterIDs the selectLitterIDs to set
     */
    public void setSelectLitterIDs(boolean selectLitterIDs) {
        this.selectLitterIDs = selectLitterIDs;
    }

    /**
     * @return the selectTotalPupsBornDead
     */
    public boolean isSelectTotalPupsBornDead() {
        return selectTotalPupsBornDead;
    }

    /**
     * @param selectTotalPupsBornDead the selectTotalPupsBornDead to set
     */
    public void setSelectTotalPupsBornDead(boolean selectTotalPupsBornDead) {
        this.selectTotalPupsBornDead = selectTotalPupsBornDead;
    }

    /**
     * @return the selectTotalPupsCulledAtWean
     */
    public boolean isSelectTotalPupsCulledAtWean() {
        return selectTotalPupsCulledAtWean;
    }

    /**
     * @param selectTotalPupsCulledAtWean the selectTotalPupsCulledAtWean to set
     */
    public void setSelectTotalPupsCulledAtWean(boolean selectTotalPupsCulledAtWean) {
        this.selectTotalPupsCulledAtWean = selectTotalPupsCulledAtWean;
    }

    /**
     * @return the selectTotalPupsMissingAtWean
     */
    public boolean isSelectTotalPupsMissingAtWean() {
        return selectTotalPupsMissingAtWean;
    }

    /**
     * @param selectTotalPupsMissingAtWean the selectTotalPupsMissingAtWean to set
     */
    public void setSelectTotalPupsMissingAtWean(boolean selectTotalPupsMissingAtWean) {
        this.selectTotalPupsMissingAtWean = selectTotalPupsMissingAtWean;
    }
}