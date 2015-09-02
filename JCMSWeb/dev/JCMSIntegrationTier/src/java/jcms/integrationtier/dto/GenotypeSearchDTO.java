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
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;

/**
 *
 * @author rkavitha
 */
public class GenotypeSearchDTO {
    
    private GeneEntity gene = new GeneEntity();
    private String partialGene = "";
    private AlleleEntity allele1 = null;
    private AlleleEntity allele2 = null;
    private String alleleName1 = null;
    private String alleleName2 = null;
    private String location = "";
    private Date gtDate = new Date();
    private String mouseID = "";
    private int mouseKey = 0;

    public Boolean hasFilter() {
               
        if (gene != null) {
            if (gene.getGeneSymbol() != null && !gene.getGeneSymbol().isEmpty()) 
                return true;
            if (allele1 != null && !allele1.getAllele().isEmpty())
                return true;
            if (allele2 != null && !allele2.getAllele().isEmpty())
                return true;
            if (!partialGene.isEmpty() || !location.isEmpty())
                return true;
        } 
        
        return false;
    }

    /**
     * @return the gene
     */
    public GeneEntity getGene() {
        return gene;
    }

    /**
     * @param gene the gene to set
     */
    public void setGene(GeneEntity gene) {
        this.gene = gene;
    }

    /**
     * @return the gtDate
     */
    public Date getGtDate() {
        return gtDate;
    }

    /**
     * @param gtDate the gtDate to set
     */
    public void setGtDate(Date gtDate) {
        this.gtDate = gtDate;
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
     * @return the allele1
     */
    public AlleleEntity getAllele1() {
        return allele1;
    }

    /**
     * @param allele1 the allele1 to set
     */
    public void setAllele1(AlleleEntity allele1) {
        this.allele1 = allele1;
    }

    /**
     * @return the allele2
     */
    public AlleleEntity getAllele2() {
        return allele2;
    }

    /**
     * @param allele2 the allele2 to set
     */
    public void setAllele2(AlleleEntity allele2) {
        this.allele2 = allele2;
    }

    /**
     * @return the mouseKey
     */
    public int getMouseKey() {
        return mouseKey;
    }

    /**
     * @param mouseKey the mouseKey to set
     */
    public void setMouseKey(int mouseKey) {
        this.mouseKey = mouseKey;
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
     * @return the partialGene
     */
    public String getPartialGene() {
        return partialGene;
    }

    /**
     * @param partialGene the partialGene to set
     */
    public void setPartialGene(String partialGene) {
        this.partialGene = partialGene;
    }

    /**
     * @return the alleleName1
     */
    public String getAlleleName1() {
        return alleleName1;
    }

    /**
     * @param alleleName1 the alleleName1 to set
     */
    public void setAlleleName1(String alleleName1) {
        this.alleleName1 = alleleName1;
    }

    /**
     * @return the alleleName2
     */
    public String getAlleleName2() {
        return alleleName2;
    }

    /**
     * @param alleleName2 the alleleName2 to set
     */
    public void setAlleleName2(String alleleName2) {
        this.alleleName2 = alleleName2;
    }

}