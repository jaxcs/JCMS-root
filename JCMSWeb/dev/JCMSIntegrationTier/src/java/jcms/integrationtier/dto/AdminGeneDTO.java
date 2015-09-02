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

import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class AdminGeneDTO extends ITBaseDTO{
    private String geneKey = "0";
    private String geneSymbol = "";
    private String labSymbol = "";
    private String geneClass = "";
    private String chromosome = "";
    private String cM = "";
    private String megabase = "";
    private String comment = "";
    private String version = "";
    private String genotypeCount = "0";
    private String alleleCount = "0";
    private String disableDeleteMessage = "";

    public AdminGeneDTO () {
        
    }

    public AdminGeneDTO (
        String geneKey,
        String geneSymbol,
        String labSymbol,
        String geneClass,
        String chromosome,
        String cM,
        String megabase,
        String comment,
        String version) {
        this.setGeneKey(geneKey);
        this.setGeneSymbol(geneSymbol);
        this.setLabSymbol(labSymbol);
        this.setGeneClass(geneClass);
        this.setChromosome(chromosome);
        this.setcM(cM);
        this.setMegabase(megabase);
        this.setComment(comment);
        this.setVersion(version);
    }

    /**
     * @return the geneKey
     */
    public String getGeneKey() {
        return geneKey;
    }

    /**
     * @param geneKey the geneKey to set
     */
    public void setGeneKey(String geneKey) {
        this.geneKey = geneKey;
    }

    /**
     * @return the geneSymbol
     */
    public String getGeneSymbol() {
        return geneSymbol;
    }

    /**
     * @param geneSymbol the geneSymbol to set
     */
    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    /**
     * @return the labSymbol
     */
    public String getLabSymbol() {
        return labSymbol;
    }

    /**
     * @param labSymbol the labSymbol to set
     */
    public void setLabSymbol(String labSymbol) {
        this.labSymbol = labSymbol;
    }

    /**
     * @return the geneClass
     */
    public String getGeneClass() {
        return geneClass;
    }

    /**
     * @param geneClass the geneClass to set
     */
    public void setGeneClass(String geneClass) {
        this.geneClass = geneClass;
    }

    /**
     * @return the chromosome
     */
    public String getChromosome() {
        return chromosome;
    }

    /**
     * @param chromosome the chromosome to set
     */
    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * @return the cM
     */
    public String getcM() {
        return cM;
    }

    /**
     * @param cM the cM to set
     */
    public void setcM(String cM) {
        this.cM = cM;
    }

    /**
     * @return the megabase
     */
    public String getMegabase() {
        return megabase;
    }

    /**
     * @param megabase the megabase to set
     */
    public void setMegabase(String megabase) {
        this.megabase = megabase;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isInsert() {
        return (this.getGeneKey().equalsIgnoreCase("0") || this.getGeneKey().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        if (this.getAlleleCount().equalsIgnoreCase("0") && this.getGenotypeCount().equalsIgnoreCase("0")) {
            disabled = false;
        } else {
            this.setDisableDeleteMessage("Delete gene disabled.  Found "+ this.getGenotypeCount()+ " genotype and "+ this.getAlleleCount() +" allele dependencies.");
            disabled = true;
        }
        return disabled;
    }

    /**
     * @return the genotypeCount
     */
    public String getGenotypeCount() {
        return genotypeCount;
    }

    /**
     * @param genotypeCount the genotypeCount to set
     */
    public void setGenotypeCount(String genotypeCount) {
        this.genotypeCount = genotypeCount;
    }

    /**
     * @return the alleleCount
     */
    public String getAlleleCount() {
        return alleleCount;
    }

    /**
     * @param alleleCount the alleleCount to set
     */
    public void setAlleleCount(String alleleCount) {
        this.alleleCount = alleleCount;
    }

    /**
     * @return the disableDeleteMessage
     */
    public String getDisableDeleteMessage() {
        return disableDeleteMessage;
    }

    /**
     * @param disableDeleteMessage the disableDeleteMessage to set
     */
    public void setDisableDeleteMessage(String disableDeleteMessage) {
        this.disableDeleteMessage = disableDeleteMessage;
    }
}
