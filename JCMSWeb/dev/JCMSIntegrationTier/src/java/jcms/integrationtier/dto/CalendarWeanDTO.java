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
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;

import java.util.Date;

/**
 *
 * @author mkamato
 */
public class CalendarWeanDTO {
    private String weanInfo;
    private Date weanDate;
    private MatingEntity mating;
    private ContainerEntity container;
    private LitterEntity litter;
    private String weanType;

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
     * @return the container
     */
    public ContainerEntity getContainer() {
        return container;
    }

    /**
     * @param container the container to set
     */
    public void setContainer(ContainerEntity container) {
        this.container = container;
    }

    /**
     * @return the litter
     */
    public LitterEntity getLitter() {
        return litter;
    }

    /**
     * @param litter the litter to set
     */
    public void setLitter(LitterEntity litter) {
        this.litter = litter;
    }

    /**
     * @return the weanInfo
     */
    public String getWeanInfo() {
        return weanInfo;
    }

    /**
     * @param weanInfo the weanInfo to set
     */
    public void setWeanInfo(String weanInfo) {
        this.weanInfo = weanInfo;
    }

    /**
     * @return the weanType
     */
    public String getWeanType() {
        return weanType;
    }

    /**
     * @param weanType the weanType to set
     */
    public void setWeanType(String weanType) {
        this.weanType = weanType;
    }

    
}
