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

package jcms.web.dto;

import java.util.Date;

/**
 *
 * @author rkavitha
 */
public class LitterInfoDTO {
    private int litterKey = 0;
    private int matingKey = 0;    
    private int numFemale = 0;
    private int numMale = 0;
    private int totalBorn = 0;
    private int matingID = 0;
    
    private String litterID = "";    
    private String status = "";
    private String strain = "";
    
    private Date birthDate = null;
    private Date weanDate = null;

    /**
     * @return the litterKey
     */
    public int getLitterKey() {
        return litterKey;
    }

    /**
     * @param litterKey the litterKey to set
     */
    public void setLitterKey(int litterKey) {
        this.litterKey = litterKey;
    }

    /**
     * @return the matingKey
     */
    public int getMatingKey() {
        return matingKey;
    }

    /**
     * @param matingKey the matingKey to set
     */
    public void setMatingKey(int matingKey) {
        this.matingKey = matingKey;
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
     * @return the strain
     */
    public String getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(String strain) {
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
     * @return the matingID
     */
    public int getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(int matingID) {
        this.matingID = matingID;
    }
}