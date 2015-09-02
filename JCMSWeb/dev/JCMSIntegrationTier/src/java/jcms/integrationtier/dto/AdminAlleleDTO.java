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
public class AdminAlleleDTO extends ITBaseDTO{
    private String alleleKey = "";
    private String allele = "";
    private String geneKey = "";
    private String genericAlleleGeneClass = "";
    private String version = "";
    
    public AdminAlleleDTO() {
        
    }
    
    public AdminAlleleDTO(
        String alleleKey,
        String allele,
        String geneKey,
        String genericAlleleGeneClass,
        String version) {
        this.setAlleleKey(alleleKey);
        this.setAllele(allele);
        this.setGeneKey(geneKey);
        this.setGenericAlleleGeneClass(genericAlleleGeneClass);
        this.setVersion(version);
    }
    
    public Boolean isInsert() {
        return (this.getAlleleKey().equalsIgnoreCase("0") || this.getAlleleKey().equalsIgnoreCase("")) ;
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        return disabled;
    }

    /**
     * @return the alleleKey
     */
    public String getAlleleKey() {
        return alleleKey;
    }

    /**
     * @param alleleKey the alleleKey to set
     */
    public void setAlleleKey(String alleleKey) {
        this.alleleKey = alleleKey;
    }

    /**
     * @return the allele
     */
    public String getAllele() {
        return allele;
    }

    /**
     * @param allele the allele to set
     */
    public void setAllele(String allele) {
        this.allele = allele;
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
     * @return the genericAlleleGeneClass
     */
    public String getGenericAlleleGeneClass() {
        return genericAlleleGeneClass;
    }

    /**
     * @param genericAlleleGeneClass the genericAlleleGeneClass to set
     */
    public void setGenericAlleleGeneClass(String genericAlleleGeneClass) {
        this.genericAlleleGeneClass = genericAlleleGeneClass;
    }
    
}
