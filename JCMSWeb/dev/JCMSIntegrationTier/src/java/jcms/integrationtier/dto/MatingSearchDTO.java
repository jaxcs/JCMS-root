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

import java.util.List;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;

/**
 *
 * @author rkavitha
 */
public class MatingSearchDTO {
    private String matingID = "";
    private String matingFilter = "Contains";
    
    private StrainEntity strain = new StrainEntity();
    private CvCrossstatusEntity matingStatus = new CvCrossstatusEntity();
    private OwnerEntity owner = new OwnerEntity();
    private List<OwnerEntity> owners = null;

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
     * @return the matngFilter
     */
    public String getMatingFilter() {
        return matingFilter;
    }

    /**
     * @param matngFilter the matngFilter to set
     */
    public void setMatingFilter(String matngFilter) {
        this.matingFilter = matngFilter;
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
     * @return the matingStatus
     */
    public CvCrossstatusEntity getMatingStatus() {
        return matingStatus;
    }

    /**
     * @param matingStatus the matingStatus to set
     */
    public void setMatingStatus(CvCrossstatusEntity matingStatus) {
        this.matingStatus = matingStatus;
    }

    /**
     * @return the owner
     */
    public OwnerEntity getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
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
