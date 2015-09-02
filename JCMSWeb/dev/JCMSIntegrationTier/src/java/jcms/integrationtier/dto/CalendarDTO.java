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
import java.util.List;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class CalendarDTO {
    //used in the query as conditions to get info from DB
    private List<String> strains = new ArrayList<String>();
    private List<String> owners = new ArrayList<String>();
    private List<String> uses = new ArrayList<String>();
    private List<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
    private String status = "";
    private Date afterDate = null;
    private Date beforeDate = null;
    
    
    //used as return parameters
    private String mouseID = "";
    private String mouseKey = "";
    private String mouseBirthDate = "";
    private String strainName = "";
    private String jrNum = "";
    private String cageID = "";
    private String cageName = "";
    private MouseUsageDTO use = new MouseUsageDTO();
    private cvMouseUseDTO cvMouseUse = new cvMouseUseDTO();
    private UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
    private String genotype = "";
    
    public CalendarDTO(){}
    
    public CalendarDTO(CalendarDTO dto){
        this.strains        = dto.strains;
        this.owners         = dto.owners;
        this.uses           = dto.uses;
        this.status         = dto.status;
        this.mouseID        = dto.mouseID;
        this.mouseKey       = dto.mouseKey;
        this.mouseBirthDate = dto.mouseBirthDate;
        this.strainName     = dto.strainName;
        this.jrNum          = dto.jrNum;
        this.use            = dto.use;
        this.cvMouseUse     = dto.cvMouseUse;
        this.genotype       = dto.genotype;
        this.cageID         = dto.cageID;
        this.cageName       = dto.cageName;
        this.useScheduleTerm       = dto.useScheduleTerm;
    }
    
    //helpers used in the view


    /**
     * @return the strains
     */
    public List<String> getStrains() {
        return strains;
    }

    /**
     * @param strains the strains to set
     */
    public void setStrains(ArrayList<String> strains) {
        this.strains = strains;
    }

    /**
     * @return the owners
     */
    public List<String> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    /**
     * @return the uses
     */
    public List<String> getUses() {
        return uses;
    }

    /**
     * @param uses the uses to set
     */
    public void setUses(List<String> uses) {
        this.uses = uses;
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
     * @return the mouseKey
     */
    public String getMouseKey() {
        return mouseKey;
    }

    /**
     * @param mouseKey the mouseKey to set
     */
    public void setMouseKey(String mouseKey) {
        this.mouseKey = mouseKey;
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
     * @return the jrNum
     */
    public String getJrNum() {
        return jrNum;
    }

    /**
     * @param jrNum the jrNum to set
     */
    public void setJrNum(String jrNum) {
        this.jrNum = jrNum;
    }

    /**
     * @return the use
     */
    public MouseUsageDTO getUse() {
        return use;
    }

    /**
     * @param use the use to set
     */
    public void setUse(MouseUsageDTO use) {
        this.use = use;
    }

    /**
     * @return the cvMouseUse
     */
    public cvMouseUseDTO getCvMouseUse() {
        return cvMouseUse;
    }

    /**
     * @param cvMouseUse the cvMouseUse to set
     */
    public void setCvMouseUse(cvMouseUseDTO cvMouseUse) {
        this.cvMouseUse = cvMouseUse;
    }

    /**
     * @return the mouseBirthDate
     */
    public String getMouseBirthDate() {
        return mouseBirthDate;
    }

    /**
     * @param mouseBirthDate the mouseBirthDate to set
     */
    public void setMouseBirthDate(String mouseBirthDate) {
        this.mouseBirthDate = mouseBirthDate;
    }

    /**
     * @return the genotype
     */
    public String getGenotype() {
        return genotype;
    }

    /**
     * @param genotype the genotype to set
     */
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    /**
     * @return the cageID
     */
    public String getCageID() {
        return cageID;
    }

    /**
     * @param cageID the cageID to set
     */
    public void setCageID(String cageID) {
        this.cageID = cageID;
    }

    /**
     * @return the cageName
     */
    public String getCageName() {
        return cageName;
    }

    /**
     * @param cageName the cageName to set
     */
    public void setCageName(String cageName) {
        this.cageName = cageName;
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
     * @return the afterDate
     */
    public Date getAfterDate() {
        return afterDate;
    }

    /**
     * @param afterDate the afterDate to set
     */
    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    /**
     * @return the beforeDate
     */
    public Date getBeforeDate() {
        return beforeDate;
    }

    /**
     * @param beforeDate the beforeDate to set
     */
    public void setBeforeDate(Date beforeDate) {
        this.beforeDate = beforeDate;
    }
}
