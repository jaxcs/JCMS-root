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
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;

/**
 *
 * @author rkavitha
 */
public class LitterSearchDTO {

    private int _litter_key = 0;
    private int totalBorn = 0;
    private int numFemale = 0;
    private int numMale = 0;
    
    private String litterID = "";
    private String matingID = "";
    private String status = "";
    
    private MatingEntity mating = new MatingEntity();
    private StrainEntity strain = new StrainEntity();
    private List<OwnerEntity> owners = null;
    
    private Date birthDate = null;
    private Date weanDate = new Date();
    private Date tagDate = new Date();
    
    private String matingFilter = "";

    /**
     * @return the _litter_key
     */
    public int getLitter_key() {
        return _litter_key;
    }

    /**
     * @param litter_key the _litter_key to set
     */
    public void setLitter_key(int litter_key) {
        this._litter_key = litter_key;
    }

    /**
     * @return the totalBorn
     */
    public int getTotalBorn() {
        return totalBorn;
    }

    /**
     * @param totalBorn the totalBorn to set
     */
    public void setTotalBorn(int totalBorn) {
        this.totalBorn = totalBorn;
    }

    /**
     * @return the numFemale
     */
    public int getNumFemale() {
        return numFemale;
    }

    /**
     * @param numFemale the numFemale to set
     */
    public void setNumFemale(int numFemale) {
        this.numFemale = numFemale;
    }

    /**
     * @return the numMale
     */
    public int getNumMale() {
        return numMale;
    }

    /**
     * @param numMale the numMale to set
     */
    public void setNumMale(int numMale) {
        this.numMale = numMale;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the mating
     */
    public MatingEntity getMating() {
        return mating;
    }

    /**
     * @param mating the mating to set
     */
    public void setMating(MatingEntity mating) {
        this.mating = mating;
    }

    /**
     * @return the strain
     */
    public StrainEntity getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(StrainEntity strain) {
        this.strain = strain;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the weanDate
     */
    public Date getWeanDate() {
        return weanDate;
    }

    /**
     * @param weanDate the weanDate to set
     */
    public void setWeanDate(Date weanDate) {
        this.weanDate = weanDate;
    }

    /**
     * @return the tagDate
     */
    public Date getTagDate() {
        return tagDate;
    }

    /**
     * @param tagDate the tagDate to set
     */
    public void setTagDate(Date tagDate) {
        this.tagDate = tagDate;
    }

    /**
     * @return the matingID
     */
    public String getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(String matingID) {
        this.matingID = matingID;
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
     * @return the owners
     */
    public List<OwnerEntity> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<OwnerEntity> owners) {
        this.owners = owners;
    }
}