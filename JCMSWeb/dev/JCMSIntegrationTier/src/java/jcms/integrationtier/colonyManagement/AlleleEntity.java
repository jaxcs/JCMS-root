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

package jcms.integrationtier.colonyManagement;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  AlleleEntity.java  <p>
 * <b>Date developed:</b>  July 2012 <p>
 * <b>Purpose:</b>  Contains all Gene information like gene symbol, genotype etc <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2011-05-18 12:43:20 -0400 (Wed, 18 May 2011) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 12974 $
 */
@Entity
@Table(name = "Allele")
@NamedQueries({
    @NamedQuery(name = "AlleleEntity.findAll", 
        query = "SELECT a FROM AlleleEntity a ORDER BY a.allele"),
    
    @NamedQuery(
    name = "AlleleEntity.findAllByOwner",
            query = "SELECT DISTINCT a FROM AlleleEntity a, GeneEntity g, "
            + "GenotypeEntity gt, MouseEntity m "
            + "WHERE a.alleleKey = a.geneKey AND g.geneKey = gt.geneKey "
            + "AND gt.mouseKey = m.mouseKey AND m.owner = :fOwner "
            + "ORDER BY a.allele"),

    @NamedQuery(
    name = "AlleleEntity.findAllByOwners",
            query = "SELECT DISTINCT a FROM AlleleEntity a, GeneEntity g, "
            + "GenotypeEntity gt, MouseEntity m "
            + "WHERE a.alleleKey = a.geneKey AND g.geneKey = gt.geneKey "
            + "AND gt.mouseKey = m.mouseKey AND m.owner IN (:lOwner) "
            + "ORDER BY a.allele"),
    
    @NamedQuery(
    name = "AlleleEntity.findBykey",
            query = "SELECT a FROM AlleleEntity a WHERE a.alleleKey = :key"),
    
    @NamedQuery(name = "AlleleEntity.findByGeneKey", query = "SELECT a FROM AlleleEntity a WHERE a.geneKey = :geneKey"),
    @NamedQuery(name = "AlleleEntity.findByAllele", query = "SELECT a FROM AlleleEntity a WHERE a.allele = :allele"),
    @NamedQuery(name = "AlleleEntity.findByGenericAlleleGeneClass", query = "SELECT a FROM AlleleEntity a WHERE a.genericAlleleGeneClass = :genericAlleleGeneClass"),
    @NamedQuery(name = "AlleleEntity.findByAlleleKey", query = "SELECT a FROM AlleleEntity a WHERE a.alleleKey = :alleleKey"),
    @NamedQuery(name = "AlleleEntity.findByVersion", query = "SELECT a FROM AlleleEntity a WHERE a.version = :version")})

public class AlleleEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "_gene_key")
    private Integer geneKey;
    
    @Basic(optional = false)
    @Column(name = "allele")
    private String allele;
    
    @Column(name = "genericAlleleGeneClass")
    private String genericAlleleGeneClass;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_allele_key")
    private Integer alleleKey;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public AlleleEntity() {
    }

    public AlleleEntity(Integer alleleKey) {
        this.alleleKey = alleleKey;
    }

    public AlleleEntity(Integer alleleKey, String allele, int version) {
        this.alleleKey = alleleKey;
        this.allele = allele;
        this.version = version;
    }

    public Integer getGeneKey() {
        return geneKey;
    }

    public void setGeneKey(Integer geneKey) {
        this.geneKey = geneKey;
    }

    public String getAllele() {
        return allele;
    }

    public void setAllele(String allele) {
        this.allele = allele;
    }

    public String getGenericAlleleGeneClass() {
        return genericAlleleGeneClass;
    }

    public void setGenericAlleleGeneClass(String genericAlleleGeneClass) {
        this.genericAlleleGeneClass = genericAlleleGeneClass;
    }

    public Integer getAlleleKey() {
        return alleleKey;
    }

    public void setAlleleKey(Integer alleleKey) {
        this.alleleKey = alleleKey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public AlleleEntity getBlankAlleleEntity() {
        AlleleEntity entity = new AlleleEntity();
        entity.setAlleleKey(0);
        entity.setAllele("");
        return entity;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alleleKey != null ? alleleKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlleleEntity)) {
            return false;
        }
        AlleleEntity other = (AlleleEntity) object;
        if ((this.alleleKey == null && other.alleleKey != null) || (this.alleleKey != null && !this.alleleKey.equals(other.alleleKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.AlleleEntity[ alleleKey=" + alleleKey + " ]";
    }
    
    @Override
    public Integer getKey() {
        return alleleKey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println(this.getClass().getSimpleName() + " Contents");
        System.out.println("================================");
        System.out.println("\tPK" + "\t" + this.alleleKey );
        System.out.println("\tKey" + "\t" + this.getKey() );
    }
}
