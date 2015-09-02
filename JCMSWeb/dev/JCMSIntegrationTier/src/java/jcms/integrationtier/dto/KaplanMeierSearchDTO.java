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
import java.util.ArrayList;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvDietEntity;

/**
 *
 * @author mkamato
 */
public class KaplanMeierSearchDTO {
    
    private String color = "";
    private String lineName = "";
    
    private List<StrainEntity> strains = new ArrayList<StrainEntity>();
    private List<OwnerEntity> owners = new ArrayList<OwnerEntity>();
    private List<CvCauseOfDeathEntity> cods = new ArrayList<CvCauseOfDeathEntity>();
    private List<CvDietEntity> diets = new ArrayList<CvDietEntity>();
    private List<LifeStatusEntity> lifeStatuses = new ArrayList<LifeStatusEntity>();
    private List<GeneEntity> genes = new ArrayList<GeneEntity>();
    private List<AlleleEntity> allele1s = new ArrayList<AlleleEntity>();
    private List<AlleleEntity> allele2s = new ArrayList<AlleleEntity>();
    private List<RoomEntity> rooms = new ArrayList<RoomEntity>();
    private List<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();

    /**
     * @return the strains
     */
    public List<StrainEntity> getStrains() {
        return strains;
    }

    /**
     * @param strains the strains to set
     */
    public void setStrains(List<StrainEntity> strains) {
        this.strains = strains;
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
     * @return the genes
     */
    public List<GeneEntity> getGenes() {
        return genes;
    }

    /**
     * @param genes the genes to set
     */
    public void setGenes(List<GeneEntity> genes) {
        this.genes = genes;
    }

    /**
     * @return the lifeStatuses
     */
    public List<LifeStatusEntity> getLifeStatuses() {
        return lifeStatuses;
    }

    /**
     * @param lifeStatuses the lifeStatuses to set
     */
    public void setLifeStatuses(List<LifeStatusEntity> lifeStatuses) {
        this.lifeStatuses = lifeStatuses;
    }

    /**
     * @return the rooms
     */
    public List<RoomEntity> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the cods
     */
    public List<CvCauseOfDeathEntity> getCods() {
        return cods;
    }

    /**
     * @param cods the cods to set
     */
    public void setCods(List<CvCauseOfDeathEntity> cods) {
        this.cods = cods;
    }

    /**
     * @return the diets
     */
    public List<CvDietEntity> getDiets() {
        return diets;
    }

    /**
     * @param diets the diets to set
     */
    public void setDiets(List<CvDietEntity> diets) {
        this.diets = diets;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the lineName
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * @return the allele1s
     */
    public List<AlleleEntity> getAllele1s() {
        return allele1s;
    }

    /**
     * @param allele1s the allele1s to set
     */
    public void setAllele1s(List<AlleleEntity> allele1s) {
        this.allele1s = allele1s;
    }

    /**
     * @return the allele2s
     */
    public List<AlleleEntity> getAllele2s() {
        return allele2s;
    }

    /**
     * @param allele2s the allele2s to set
     */
    public void setAllele2s(List<AlleleEntity> allele2s) {
        this.allele2s = allele2s;
    }
    
}
