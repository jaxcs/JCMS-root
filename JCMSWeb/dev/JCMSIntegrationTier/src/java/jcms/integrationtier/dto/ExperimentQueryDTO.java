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

import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * <b>File name:</b>  ExperimentQueryDTO.java  <p>
 * <b>Date developed:</b>  January 2015 <p>
 * <b>Purpose:</b>  Provides methods to capture experiment query filter criteria. <p>
 * @author bas
 */
public class ExperimentQueryDTO {
    private String mouseIDLike = "";
    private String testType = "";
    private String expDataDescriptorKey = "0";
    
    //data table headers for the dx caption fields
    private String d1Header = "";
    private String d2Header = "";
    private String d3Header = "";
    private String d4Header = "";
    private String d5Header = "";
    private String d6Header = "";
    private String d7Header = "";
    private String d8Header = "";
    private String d9Header = "";
    private String d10Header = "";
    private String d11Header = "";
    private String d12Header = "";
    private String d13Header = "";
    private String d14Header = "";
    private String d15Header = "";
    private String d16Header = "";
    private String d17Header = "";
    private String d18Header = "";
    private String d19Header = "";
    private String d20Header = "";
    private String d21Header = "";
    private String d22Header = "";
    private String d23Header = "";
    private String d24Header = "";
    private String d25Header = "";
    private String d26Header = "";
    private String d27Header = "";
    private String d28Header = "";
    private String d29Header = "";
    private String d30Header = "";
    
    private String dataIDFrom = "0";
    private String dataIDTo = "0";
    private String ageFrom = "0";
    private String ageTo = "0";
    private Date startDate;
    private Date endDate;
    
    private Date expDate;
    private Float age = null;
    private Boolean abnormalData = false;
    
    private List<StrainEntity> strain = null;
    private List<LifeStatusEntity> lifeStatus = null;
    private List<OwnerEntity> dataOwner = null;
    private List<OwnerEntity> mouseOwner = null;
    
    private String testTypeFilter = "Equals";
    private String mouseFilter = "Contains";
    private String strainFilter = "";
    private String dataIDFilter = "Between";
    private String ageFilter = "";
    private String ageMeasure = "Days";
    private String abnormalDataChoice = "Any";
    
    private boolean selectDataID = false;
    private boolean selectTestType = true; //This is selected by default
    private boolean selectDataOwner = false;
    private boolean selectCollectionDate = false;
    private boolean selectCollectionAge = false;
    private boolean selectAbnormalData = false;
    private boolean selectMouseID = false;
    private boolean selectMouseStrain = false;
    private boolean selectMouseLifeStatus = false;
    private boolean selectMouseOwner = false;
    private boolean selectDataResults = false;
    private boolean selectMouseComment = false;
    private boolean selectGeneration = false;
    private boolean selectBirthDate = false;
    private boolean selectSex = false;
    private boolean selectCOD = false;
    private boolean selectMouseCageID = false;
    private boolean selectCageName = false;

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
     * @return the selectGeneration
     */
    public boolean isSelectGeneration() {
        return selectGeneration;
    }

    /**
     * @param selectGeneration the selectGeneration to set
     */
    public void setSelectGeneration(boolean selectGeneration) {
        this.selectGeneration = selectGeneration;
    }

    /**
     * @return the selectBirthDate
     */
    public boolean isSelectBirthDate() {
        return selectBirthDate;
    }

    /**
     * @param selectBirthDate the selectBirthDate to set
     */
    public void setSelectBirthDate(boolean selectBirthDate) {
        this.selectBirthDate = selectBirthDate;
    }

    /**
     * @return the selectSex
     */
    public boolean isSelectSex() {
        return selectSex;
    }

    /**
     * @param selectSex the selectSex to set
     */
    public void setSelectSex(boolean selectSex) {
        this.selectSex = selectSex;
    }

    /**
     * @return the selectCOD
     */
    public boolean isSelectCOD() {
        return selectCOD;
    }

    /**
     * @param selectCOD the selectCOD to set
     */
    public void setSelectCOD(boolean selectCOD) {
        this.selectCOD = selectCOD;
    }

    /**
     * @return the selectDataID
     */
    public boolean isSelectDataID() {
        return selectDataID;
    }

    /**
     * @param selectDataID the selectDataID to set
     */
    public void setSelectDataID(boolean selectDataID) {
        this.selectDataID = selectDataID;
    }

    /**
     * @return the selectDataResults
     */
    public boolean isSelectDataResults() {
        return selectDataResults;
    }

    /**
     * @param selectDataResults the selectDataResults to set
     */
    public void setSelectDataResults(boolean selectDataResults) {
        this.selectDataResults = selectDataResults;
    }

    /**
     * @return the dataIDFilter
     */
    public String getDataIDFilter() {
        return dataIDFilter;
    }

    /**
     * @param dataIDFilter the dataIDFilter to set
     */
    public void setDataIDFilter(String dataIDFilter) {
        this.dataIDFilter = dataIDFilter;
    }

    /**
     * @return the testType
     */
    public String getTestType() {
        return testType;
    }

    /**
     * @param testType the testType to set
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * @return the testTypeFilter
     */
    public String getTestTypeFilter() {
        return testTypeFilter;
    }

    /**
     * @param testTypeFilter the testTypeFilter to set
     */
    public void setTestTypeFilter(String testTypeFilter) {
        this.testTypeFilter = testTypeFilter;
    }

    /**
     * @return the selectTestType
     */
    public boolean isSelectTestType() {
        return selectTestType;
    }

    /**
     * @param selectTestType the selectTestType to set
     */
    public void setSelectTestType(boolean selectTestType) {
        this.selectTestType = selectTestType;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the selectCollectionDate
     */
    public boolean isSelectCollectionDate() {
        return selectCollectionDate;
    }

    /**
     * @param selectCollectionDate the selectCollectionDate to set
     */
    public void setSelectCollectionDate(boolean selectCollectionDate) {
        this.selectCollectionDate = selectCollectionDate;
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
     * @return the selectCollectionAge
     */
    public boolean isSelectCollectionAge() {
        return selectCollectionAge;
    }

    /**
     * @param selectCollectionAge the selectCollectionAge to set
     */
    public void setSelectCollectionAge(boolean selectCollectionAge) {
        this.selectCollectionAge = selectCollectionAge;
    }

    /**
     * @return the selectAbnormalData
     */
    public boolean isSelectAbnormalData() {
        return selectAbnormalData;
    }

    /**
     * @param selectAbnormalData the selectAbnormalData to set
     */
    public void setSelectAbnormalData(boolean selectAbnormalData) {
        this.selectAbnormalData = selectAbnormalData;
    }

    /**
     * @return the selectDataOwner
     */
    public boolean isSelectDataOwner() {
        return selectDataOwner;
    }

    /**
     * @param selectDataOwner the selectDataOwner to set
     */
    public void setSelectDataOwner(boolean selectDataOwner) {
        this.selectDataOwner = selectDataOwner;
    }

    /**
     * @return the abnormalDataChoice
     */
    public String getAbnormalDataChoice() {
        return abnormalDataChoice;
    }

    /**
     * @param abnormalDataChoice the abnormalDataChoice to set
     */
    public void setAbnormalDataChoice(String abnormalDataChoice) {
        this.abnormalDataChoice = abnormalDataChoice;
    }

    /**
     * @return the dataOwner
     */
    public List<OwnerEntity> getDataOwner() {
        return dataOwner;
    }

    /**
     * @param dataOwner the dataOwner to set
     */
    public void setDataOwner(List<OwnerEntity> dataOwner) {
        this.dataOwner = dataOwner;
    }

    /**
     * @return the mouseOwner
     */
    public List<OwnerEntity> getMouseOwner() {
        return mouseOwner;
    }

    /**
     * @param mouseOwner the mouseOwner to set
     */
    public void setMouseOwner(List<OwnerEntity> mouseOwner) {
        this.mouseOwner = mouseOwner;
    }

    /**
     * @return the expDataDescriptorKey
     */
    public String getExpDataDescriptorKey() {
        return expDataDescriptorKey;
    }

    /**
     * @param expDataDescriptorKey the expDataDescriptorKey to set
     */
    public void setExpDataDescriptorKey(String expDataDescriptorKey) {
        this.expDataDescriptorKey = expDataDescriptorKey;
    }

    /**
     * @return the dataIDFrom
     */
    public String getDataIDFrom() {
        return dataIDFrom;
    }

    /**
     * @param dataIDFrom the dataIDFrom to set
     */
    public void setDataIDFrom(String dataIDFrom) {
        this.dataIDFrom = dataIDFrom;
    }

    /**
     * @return the dataIDTo
     */
    public String getDataIDTo() {
        return dataIDTo;
    }

    /**
     * @param dataIDTo the dataIDTo to set
     */
    public void setDataIDTo(String dataIDTo) {
        this.dataIDTo = dataIDTo;
    }

    /**
     * @return the ageFrom
     */
    public String getAgeFrom() {
        return ageFrom;
    }

    /**
     * @param ageFrom the ageFrom to set
     */
    public void setAgeFrom(String ageFrom) {
        this.ageFrom = ageFrom;
    }

    /**
     * @return the ageTo
     */
    public String getAgeTo() {
        return ageTo;
    }

    /**
     * @param ageTo the ageTo to set
     */
    public void setAgeTo(String ageTo) {
        this.ageTo = ageTo;
    }

    /**
     * @return the mouseIDLike
     */
    public String getMouseIDLike() {
        return mouseIDLike;
    }

    /**
     * @param mouseIDLike the mouseIDLike to set
     */
    public void setMouseIDLike(String mouseIDLike) {
        this.mouseIDLike = mouseIDLike;
    }

    /**
     * @return the d1Header
     */
    public String getD1Header() {
        return d1Header;
    }

    /**
     * @param d1Header the d1Header to set
     */
    public void setD1Header(String d1Header) {
        this.d1Header = d1Header;
    }

    /**
     * @return the d2Header
     */
    public String getD2Header() {
        return d2Header;
    }

    /**
     * @param d2Header the d2Header to set
     */
    public void setD2Header(String d2Header) {
        this.d2Header = d2Header;
    }

    /**
     * @return the d3Header
     */
    public String getD3Header() {
        return d3Header;
    }

    /**
     * @param d3Header the d3Header to set
     */
    public void setD3Header(String d3Header) {
        this.d3Header = d3Header;
    }

    /**
     * @return the d4Header
     */
    public String getD4Header() {
        return d4Header;
    }

    /**
     * @param d4Header the d4Header to set
     */
    public void setD4Header(String d4Header) {
        this.d4Header = d4Header;
    }

    /**
     * @return the d5Header
     */
    public String getD5Header() {
        return d5Header;
    }

    /**
     * @param d5Header the d5Header to set
     */
    public void setD5Header(String d5Header) {
        this.d5Header = d5Header;
    }

    /**
     * @return the d6Header
     */
    public String getD6Header() {
        return d6Header;
    }

    /**
     * @param d6Header the d6Header to set
     */
    public void setD6Header(String d6Header) {
        this.d6Header = d6Header;
    }

    /**
     * @return the d7Header
     */
    public String getD7Header() {
        return d7Header;
    }

    /**
     * @param d7Header the d7Header to set
     */
    public void setD7Header(String d7Header) {
        this.d7Header = d7Header;
    }

    /**
     * @return the d8Header
     */
    public String getD8Header() {
        return d8Header;
    }

    /**
     * @param d8Header the d8Header to set
     */
    public void setD8Header(String d8Header) {
        this.d8Header = d8Header;
    }

    /**
     * @return the d9Header
     */
    public String getD9Header() {
        return d9Header;
    }

    /**
     * @param d9Header the d9Header to set
     */
    public void setD9Header(String d9Header) {
        this.d9Header = d9Header;
    }

    /**
     * @return the d10Header
     */
    public String getD10Header() {
        return d10Header;
    }

    /**
     * @param d10Header the d10Header to set
     */
    public void setD10Header(String d10Header) {
        this.d10Header = d10Header;
    }

    /**
     * @return the d11Header
     */
    public String getD11Header() {
        return d11Header;
    }

    /**
     * @param d11Header the d11Header to set
     */
    public void setD11Header(String d11Header) {
        this.d11Header = d11Header;
    }

    /**
     * @return the d12Header
     */
    public String getD12Header() {
        return d12Header;
    }

    /**
     * @param d12Header the d12Header to set
     */
    public void setD12Header(String d12Header) {
        this.d12Header = d12Header;
    }

    /**
     * @return the d13Header
     */
    public String getD13Header() {
        return d13Header;
    }

    /**
     * @param d13Header the d13Header to set
     */
    public void setD13Header(String d13Header) {
        this.d13Header = d13Header;
    }

    /**
     * @return the d14Header
     */
    public String getD14Header() {
        return d14Header;
    }

    /**
     * @param d14Header the d14Header to set
     */
    public void setD14Header(String d14Header) {
        this.d14Header = d14Header;
    }

    /**
     * @return the d15Header
     */
    public String getD15Header() {
        return d15Header;
    }

    /**
     * @param d15Header the d15Header to set
     */
    public void setD15Header(String d15Header) {
        this.d15Header = d15Header;
    }

    /**
     * @return the d16Header
     */
    public String getD16Header() {
        return d16Header;
    }

    /**
     * @param d16Header the d16Header to set
     */
    public void setD16Header(String d16Header) {
        this.d16Header = d16Header;
    }

    /**
     * @return the d17Header
     */
    public String getD17Header() {
        return d17Header;
    }

    /**
     * @param d17Header the d17Header to set
     */
    public void setD17Header(String d17Header) {
        this.d17Header = d17Header;
    }

    /**
     * @return the d18Header
     */
    public String getD18Header() {
        return d18Header;
    }

    /**
     * @param d18Header the d18Header to set
     */
    public void setD18Header(String d18Header) {
        this.d18Header = d18Header;
    }

    /**
     * @return the d19Header
     */
    public String getD19Header() {
        return d19Header;
    }

    /**
     * @param d19Header the d19Header to set
     */
    public void setD19Header(String d19Header) {
        this.d19Header = d19Header;
    }

    /**
     * @return the d20Header
     */
    public String getD20Header() {
        return d20Header;
    }

    /**
     * @param d20Header the d20Header to set
     */
    public void setD20Header(String d20Header) {
        this.d20Header = d20Header;
    }

    /**
     * @return the d21Header
     */
    public String getD21Header() {
        return d21Header;
    }

    /**
     * @param d21Header the d21Header to set
     */
    public void setD21Header(String d21Header) {
        this.d21Header = d21Header;
    }

    /**
     * @return the d22Header
     */
    public String getD22Header() {
        return d22Header;
    }

    /**
     * @param d22Header the d22Header to set
     */
    public void setD22Header(String d22Header) {
        this.d22Header = d22Header;
    }

    /**
     * @return the d23Header
     */
    public String getD23Header() {
        return d23Header;
    }

    /**
     * @param d23Header the d23Header to set
     */
    public void setD23Header(String d23Header) {
        this.d23Header = d23Header;
    }

    /**
     * @return the d24Header
     */
    public String getD24Header() {
        return d24Header;
    }

    /**
     * @param d24Header the d24Header to set
     */
    public void setD24Header(String d24Header) {
        this.d24Header = d24Header;
    }

    /**
     * @return the d25Header
     */
    public String getD25Header() {
        return d25Header;
    }

    /**
     * @param d25Header the d25Header to set
     */
    public void setD25Header(String d25Header) {
        this.d25Header = d25Header;
    }

    /**
     * @return the d26Header
     */
    public String getD26Header() {
        return d26Header;
    }

    /**
     * @param d26Header the d26Header to set
     */
    public void setD26Header(String d26Header) {
        this.d26Header = d26Header;
    }

    /**
     * @return the d27Header
     */
    public String getD27Header() {
        return d27Header;
    }

    /**
     * @param d27Header the d27Header to set
     */
    public void setD27Header(String d27Header) {
        this.d27Header = d27Header;
    }

    /**
     * @return the d28Header
     */
    public String getD28Header() {
        return d28Header;
    }

    /**
     * @param d28Header the d28Header to set
     */
    public void setD28Header(String d28Header) {
        this.d28Header = d28Header;
    }

    /**
     * @return the d29Header
     */
    public String getD29Header() {
        return d29Header;
    }

    /**
     * @param d29Header the d29Header to set
     */
    public void setD29Header(String d29Header) {
        this.d29Header = d29Header;
    }

    /**
     * @return the d30Header
     */
    public String getD30Header() {
        return d30Header;
    }

    /**
     * @param d30Header the d30Header to set
     */
    public void setD30Header(String d30Header) {
        this.d30Header = d30Header;
    }

    /**
     * @return the expDate
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * @param expDate the expDate to set
     */
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    /**
     * @return the abnormalData
     */
    public Boolean getAbnormalData() {
        return abnormalData;
    }

    /**
     * @param abnormalData the abnormalData to set
     */
    public void setAbnormalData(Boolean abnormalData) {
        this.abnormalData = abnormalData;
    }

    /**
     * @return the age
     */
    public Float getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Float age) {
        this.age = age;
    }

    /**
     * @return the selectMouseCageID
     */
    public boolean isSelectMouseCageID() {
        return selectMouseCageID;
    }

    /**
     * @param selectMouseCageID the selectMouseCageID to set
     */
    public void setSelectMouseCageID(boolean selectMouseCageID) {
        this.selectMouseCageID = selectMouseCageID;
    }

    /**
     * @return the selectCageName
     */
    public boolean isSelectCageName() {
        return selectCageName;
    }

    /**
     * @param selectCageName the selectCageName to set
     */
    public void setSelectCageName(boolean selectCageName) {
        this.selectCageName = selectCageName;
    }

}
