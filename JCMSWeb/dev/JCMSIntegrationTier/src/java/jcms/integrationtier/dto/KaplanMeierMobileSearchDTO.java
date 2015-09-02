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
public class KaplanMeierMobileSearchDTO {
     private String[] strains;
     private String[] useSchedules;
     private String[] workgroups;
     private String[] cods;
     private String[] lifeStatuses;
     private String[] diets;
     private String[] genes;
     private String[] allele1s;
     private String[] allele2s;
     private String[] rooms;
     

    /**
     * @return the strains
     */
    public String[] getStrains() {
        return strains;
    }
    
    /**
     * @return the useSchedules
     */
    public String[] getUseSchedules() {
        return useSchedules;
    }

    /**
     * @return the workgroups
     */
    public String[] getWorkgroups() {
        return workgroups;
    }

    /**
     * @return the cods
     */
    public String[] getCods() {
        return cods;
    }

    /**
     * @param cods the cods to set
     */
    public void setCods(String[] cods) {
        this.cods = cods;
    }

    /**
     * @return the lifeStatuses
     */
    public String[] getLifeStatuses() {
        return lifeStatuses;
    }

    /**
     * @param lifeStatuses the lifeStatuses to set
     */
    public void setLifeStatuses(String[] lifeStatuses) {
        this.lifeStatuses = lifeStatuses;
    }

    /**
     * @return the diets
     */
    public String[] getDiets() {
        return diets;
    }

    /**
     * @param diets the diets to set
     */
    public void setDiets(String[] diets) {
        this.diets = diets;
    }

    /**
     * @return the genes
     */
    public String[] getGenes() {
        return genes;
    }

    /**
     * @param genes the genes to set
     */
    public void setGenes(String[] genes) {
        this.genes = genes;
    }

    /**
     * @return the allele1s
     */
    public String[] getAllele1s() {
        return allele1s;
    }

    /**
     * @param allele1s the allele1s to set
     */
    public void setAllele1s(String[] allele1s) {
        this.allele1s = allele1s;
    }

    /**
     * @return the allele2s
     */
    public String[] getAllele2s() {
        return allele2s;
    }

    /**
     * @param allele2s the allele2s to set
     */
    public void setAllele2s(String[] allele2s) {
        this.allele2s = allele2s;
    }

    /**
     * @return the rooms
     */
    public String[] getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(String[] rooms) {
        this.rooms = rooms;
    }

    /**
     * @param strains the strains to set
     */
    public void setStrains(String[] strains) {
        this.strains = strains;
    }

    /**
     * @param useSchedules the useSchedules to set
     */
    public void setUseSchedules(String[] useSchedules) {
        this.useSchedules = useSchedules;
    }

    /**
     * @param workgroups the workgroups to set
     */
    public void setWorkgroups(String[] workgroups) {
        this.workgroups = workgroups;
    }
}
