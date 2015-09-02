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

/**
 *
 * @author mkamato
 */

import jcms.integrationtier.colonyManagement.StrainEntity;
import java.util.Date;
//import jcms.integrationtier.colonyManagement.OwnerEntity;
import java.util.ArrayList;

public class SampleSearchDTO {
    
    private String sampleID = "";
    private String sampleIDFilter = "";
    private StrainEntity sampleStrain = null;
    private String sourceType = "";
    private String sourceID = "";
    private String sourceIDFilter = "";
    private String sampleClass = "";
    private String sampleType = "";
    private Date sampleDateBefore = null;
    private Date sampleDateAfter = null;
    private ArrayList<String> owners = null;
    private String location = "";
    private String storageFacility = "";

    /**
     * @return the sampleID
     */
    public String getSampleID() {
        return sampleID;
    }

    /**
     * @param sampleID the sampleID to set
     */
    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    /**
     * @return the sampleIDFilter
     */
    public String getSampleIDFilter() {
        return sampleIDFilter;
    }

    /**
     * @param sampleIDFilter the sampleIDFilter to set
     */
    public void setSampleIDFilter(String sampleIDFilter) {
        this.sampleIDFilter = sampleIDFilter;
    }

    /**
     * @return the sampleStrain
     */
    public StrainEntity getSampleStrain() {
        return sampleStrain;
    }

    /**
     * @param sampleStrain the sampleStrain to set
     */
    public void setSampleStrain(StrainEntity sampleStrain) {
        this.sampleStrain = sampleStrain;
    }

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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
     * @return the sourceIDFilter
     */
    public String getSourceIDFilter() {
        return sourceIDFilter;
    }

    /**
     * @param sourceIDFilter the sourceIDFilter to set
     */
    public void setSourceIDFilter(String sourceIDFilter) {
        this.sourceIDFilter = sourceIDFilter;
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
     * @return the owners
     */
    public ArrayList<String> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
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
     * @return the storageFacility
     */
    public String getStorageFacility() {
        return storageFacility;
    }

    /**
     * @param storageFacility the storageFacility to set
     */
    public void setStorageFacility(String storageFacility) {
        this.storageFacility = storageFacility;
    }

    /**
     * @return the sampleDateBefore
     */
    public Date getSampleDateBefore() {
        return sampleDateBefore;
    }

    /**
     * @param sampleDateBefore the sampleDateBefore to set
     */
    public void setSampleDateBefore(Date sampleDateBefore) {
        this.sampleDateBefore = sampleDateBefore;
    }

    /**
     * @return the sampleDateAfter
     */
    public Date getSampleDateAfter() {
        return sampleDateAfter;
    }

    /**
     * @param sampleDateAfter the sampleDateAfter to set
     */
    public void setSampleDateAfter(Date sampleDateAfter) {
        this.sampleDateAfter = sampleDateAfter;
    }
}
