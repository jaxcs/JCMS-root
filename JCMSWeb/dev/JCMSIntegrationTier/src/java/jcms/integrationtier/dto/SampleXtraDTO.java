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

import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class SampleXtraDTO {
    
    private String _sample_key = "";
    private String _parentSample_key = "";
    private String age = "";
    private String _timeUnit_key = "";
    private String timeUnit = "";
    private String _epoch_key = "";
    private String epoch = "";
    private String harvestMethod = "";
    private String description = "";
    private String weight = "";
    private String _weightUnit_key = "";
    private String _sampleType_key = "";
    private String sampleType = "";
    private Date sampleDate = null;
    private String _sampleDateType_key = "";
    private String sampleDateType = "";
    private String _strain_key = "";
    private String owner = "";
    private String SampleID = "";
    private String SourceType = "";
    private String strainName = "";
    private String version = "";
    private StorageDTO storage = new StorageDTO();
    private String sourceID = "";
    private String weightUnit = "";
    private String sampleClass = "";
    private ArrayList<MouseEntity> mouseSources = null;
    private ArrayList<MatingEntity> matingSources = null;
    private ArrayList<LitterEntity> litterSources = null;
    private String _locationType_key = "";
    private String location = "";
    private String style = "";
    private boolean added = false;
    

    /**
     * @return the _sample_key
     */
    public String getSample_key() {
        return _sample_key;
    }

    /**
     * @param sample_key the _sample_key to set
     */
    public void setSample_key(String sample_key) {
        this._sample_key = sample_key;
    }

    /**
     * @return the _parentSample_key
     */
    public String getParentSample_key() {
        return _parentSample_key;
    }

    /**
     * @param parentSample_key the _parentSample_key to set
     */
    public void setParentSample_key(String parentSample_key) {
        this._parentSample_key = parentSample_key;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the _timeUnit_key
     */
    public String getTimeUnit_key() {
        return _timeUnit_key;
    }

    /**
     * @param timeUnit_key the _timeUnit_key to set
     */
    public void setTimeUnit_key(String timeUnit_key) {
        this._timeUnit_key = timeUnit_key;
    }

    /**
     * @return the _epoch_key
     */
    public String getEpoch_key() {
        return _epoch_key;
    }

    /**
     * @param epoch_key the _epoch_key to set
     */
    public void setEpoch_key(String epoch_key) {
        this._epoch_key = epoch_key;
    }

    /**
     * @return the harvestMethod
     */
    public String getHarvestMethod() {
        return harvestMethod;
    }

    /**
     * @param harvestMethod the harvestMethod to set
     */
    public void setHarvestMethod(String harvestMethod) {
        this.harvestMethod = harvestMethod;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return the _weightUnit_key
     */
    public String getWeightUnit_key() {
        return _weightUnit_key;
    }

    /**
     * @param weightUnit_key the _weightUnit_key to set
     */
    public void setWeightUnit_key(String weightUnit_key) {
        this._weightUnit_key = weightUnit_key;
    }

    /**
     * @return the _sampleType_key
     */
    public String getSampleType_key() {
        return _sampleType_key;
    }

    /**
     * @param sampleType_key the _sampleType_key to set
     */
    public void setSampleType_key(String sampleType_key) {
        this._sampleType_key = sampleType_key;
    }

    /**
     * @return the sampleDate
     */
    public Date getSampleDate() {
        return sampleDate;
    }

    /**
     * @param sampleDate the sampleDate to set
     */
    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    /**
     * @return the _sampleDateType_key
     */
    public String getSampleDateType_key() {
        return _sampleDateType_key;
    }

    /**
     * @param sampleDateType_key the _sampleDateType_key to set
     */
    public void setSampleDateType_key(String sampleDateType_key) {
        this._sampleDateType_key = sampleDateType_key;
    }

    /**
     * @return the _strain_key
     */
    public String getStrain_key() {
        return _strain_key;
    }

    /**
     * @param strain_key the _strain_key to set
     */
    public void setStrain_key(String strain_key) {
        this._strain_key = strain_key;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the SampleID
     */
    public String getSampleID() {
        return SampleID;
    }

    /**
     * @param SampleID the SampleID to set
     */
    public void setSampleID(String SampleID) {
        this.SampleID = SampleID;
    }

    /**
     * @return the SourceType
     */
    public String getSourceType() {
        return SourceType;
    }

    /**
     * @param SourceType the SourceType to set
     */
    public void setSourceType(String SourceType) {
        this.SourceType = SourceType;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the strainName
     */
    public String getStrainName() {
        return strainName;
    }

    /**
     * @param strainName the strainName to set
     */
    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    /**
     * @return the storage
     */
    public StorageDTO getStorage() {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage(StorageDTO storage) {
        this.storage = storage;
    }

    /**
     * @return the sampleType
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * @param sampleType the sampleType to set
     */
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    /**
     * @return the sourceID
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * @return the weightUnit
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * @param weightUnit the weightUnit to set
     */
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    /**
     * @return the timeUnit
     */
    public String getTimeUnit() {
        return timeUnit;
    }

    /**
     * @param timeUnit the timeUnit to set
     */
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * @return the epoch
     */
    public String getEpoch() {
        return epoch;
    }

    /**
     * @param epoch the epoch to set
     */
    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    /**
     * @return the sampleDateType
     */
    public String getSampleDateType() {
        return sampleDateType;
    }

    /**
     * @param sampleDateType the sampleDateType to set
     */
    public void setSampleDateType(String sampleDateType) {
        this.sampleDateType = sampleDateType;
    }

    /**
     * @return the sampleClass
     */
    public String getSampleClass() {
        return sampleClass;
    }

    /**
     * @param sampleClass the sampleClass to set
     */
    public void setSampleClass(String sampleClass) {
        this.sampleClass = sampleClass;
    }

    /**
     * @return the mouseSources
     */
    public ArrayList<MouseEntity> getMouseSources() {
        return mouseSources;
    }

    /**
     * @param mouseSources the mouseSources to set
     */
    public void setMouseSources(ArrayList<MouseEntity> mouseSources) {
        this.mouseSources = mouseSources;
    }

    /**
     * @return the matingSources
     */
    public ArrayList<MatingEntity> getMatingSources() {
        return matingSources;
    }

    /**
     * @param matingSources the matingSources to set
     */
    public void setMatingSources(ArrayList<MatingEntity> matingSources) {
        this.matingSources = matingSources;
    }

    /**
     * @return the litterSources
     */
    public ArrayList<LitterEntity> getLitterSources() {
        return litterSources;
    }

    /**
     * @param litterSources the litterSources to set
     */
    public void setLitterSources(ArrayList<LitterEntity> litterSources) {
        this.litterSources = litterSources;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the _locationType_key
     */
    public String getLocationType_key() {
        return _locationType_key;
    }

    /**
     * @param locationType_key the _locationType_key to set
     */
    public void setLocationType_key(String locationType_key) {
        this._locationType_key = locationType_key;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the added
     */
    public boolean isAdded() {
        return added;
    }

    /**
     * @param added the added to set
     */
    public void setAdded(boolean added) {
        this.added = added;
    }
}
