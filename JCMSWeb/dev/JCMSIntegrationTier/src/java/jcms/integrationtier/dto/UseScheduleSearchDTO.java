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
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 *
 * @author mkamato
 */
public class UseScheduleSearchDTO {
    
    private String mouseID = "";
    private String mouseFilter = "Contains";
    
    private String penID = "";
    private String penFilter = "Equals";
    
    private StrainEntity strain = new StrainEntity();
    private LifeStatusEntity lifeStatus = new LifeStatusEntity();
    private List<WorkgroupEntity> wgs = null;
    private CvSexEntity sex = new CvSexEntity();
    private CvGenerationEntity generation = new CvGenerationEntity();
    
    private Date DOBStartDate;
    private Date DOBEndDate;
    
    private Date startDateLow;
    private Date startDateHigh;
    
    private UseScheduleTermDTO useScheduleTerm;
    private UseScheduleDTO useSchedule = new UseScheduleDTO();

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
     * @return the penID
     */
    public String getPenID() {
        return penID;
    }

    /**
     * @param penID the penID to set
     */
    public void setPenID(String penID) {
        this.penID = penID;
    }

    /**
     * @return the penFilter
     */
    public String getPenFilter() {
        return penFilter;
    }

    /**
     * @param penFilter the penFilter to set
     */
    public void setPenFilter(String penFilter) {
        this.penFilter = penFilter;
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
     * @return the lifeStatus
     */
    public LifeStatusEntity getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(LifeStatusEntity lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the wgs
     */
    public List<WorkgroupEntity> getWgs() {
        return wgs;
    }

    /**
     * @param wgs the wgs to set
     */
    public void setWgs(List<WorkgroupEntity> wgs) {
        this.wgs = wgs;
    }

    /**
     * @return the sex
     */
    public CvSexEntity getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(CvSexEntity sex) {
        this.sex = sex;
    }

    /**
     * @return the generation
     */
    public CvGenerationEntity getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(CvGenerationEntity generation) {
        this.generation = generation;
    }

    /**
     * @return the DOBStartDate
     */
    public Date getDOBStartDate() {
        return DOBStartDate;
    }

    /**
     * @param DOBStartDate the DOBStartDate to set
     */
    public void setDOBStartDate(Date DOBStartDate) {
        this.DOBStartDate = DOBStartDate;
    }

    /**
     * @return the DOBEndDate
     */
    public Date getDOBEndDate() {
        return DOBEndDate;
    }

    /**
     * @param DOBEndDate the DOBEndDate to set
     */
    public void setDOBEndDate(Date DOBEndDate) {
        this.DOBEndDate = DOBEndDate;
    }

    /**
     * @return the useScheduleTerm
     */
    public UseScheduleTermDTO getUseScheduleTerm() {
        return useScheduleTerm;
    }

    /**
     * @param useScheduleTerm the useScheduleTerm to set
     */
    public void setUseScheduleTerm(UseScheduleTermDTO useScheduleTerm) {
        this.useScheduleTerm = useScheduleTerm;
    }

    /**
     * @return the useSchedule
     */
    public UseScheduleDTO getUseSchedule() {
        return useSchedule;
    }

    /**
     * @param useSchedule the useSchedule to set
     */
    public void setUseSchedule(UseScheduleDTO useSchedule) {
        this.useSchedule = useSchedule;
    }

    /**
     * @return the startDateLow
     */
    public Date getStartDateLow() {
        return startDateLow;
    }

    /**
     * @param startDateLow the startDateLow to set
     */
    public void setStartDateLow(Date startDateLow) {
        this.startDateLow = startDateLow;
    }

    /**
     * @return the startDateHigh
     */
    public Date getStartDateHigh() {
        return startDateHigh;
    }

    /**
     * @param startDateHigh the startDateHigh to set
     */
    public void setStartDateHigh(Date startDateHigh) {
        this.startDateHigh = startDateHigh;
    }
    
}
