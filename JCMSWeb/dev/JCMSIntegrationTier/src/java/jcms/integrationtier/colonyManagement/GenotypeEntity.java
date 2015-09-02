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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  GenotypeEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains genotype information for a mouse  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Entity
@Table(name = "Genotype")
@NamedQueries({
    @NamedQuery(
    name = "GenotypeEntity.findAll",
            query = "SELECT g FROM GenotypeEntity g " +
            "ORDER BY g.allele1"),

    @NamedQuery(
    name = "GenotypeEntity.findBykey",
            query = "SELECT g FROM GenotypeEntity g WHERE g.genotypeKey = :key"),
    
    @NamedQuery(name = "GenotypeEntity.findMaxPrimaryKey", query = "SELECT max(g.genotypeKey) FROM GenotypeEntity g " ),
    
    @NamedQuery(name = "GenotypeEntity.findGenotypesByMouse", query = "SELECT g FROM GenotypeEntity g WHERE g.mouseKey = :mKey"),
    
    @NamedQuery(name = "GenotypeEntity.findByGenotypeKey", query = "SELECT g FROM GenotypeEntity g WHERE g.genotypeKey = :genotypeKey"),
    @NamedQuery(name = "GenotypeEntity.findByAllele1", query = "SELECT g FROM GenotypeEntity g WHERE g.allele1 = :allele1"),
    @NamedQuery(name = "GenotypeEntity.findByAll1Conf", query = "SELECT g FROM GenotypeEntity g WHERE g.all1Conf = :all1Conf"),
    @NamedQuery(name = "GenotypeEntity.findByAllele2", query = "SELECT g FROM GenotypeEntity g WHERE g.allele2 = :allele2"),
    @NamedQuery(name = "GenotypeEntity.findByAll2Conf", query = "SELECT g FROM GenotypeEntity g WHERE g.all2Conf = :all2Conf"),
    @NamedQuery(name = "GenotypeEntity.findByGenoPage", query = "SELECT g FROM GenotypeEntity g WHERE g.genoPage = :genoPage"),
    @NamedQuery(name = "GenotypeEntity.findBySampleLocation", query = "SELECT g FROM GenotypeEntity g WHERE g.sampleLocation = :sampleLocation"),
    @NamedQuery(name = "GenotypeEntity.findByGtDate", query = "SELECT g FROM GenotypeEntity g WHERE g.gtDate = :gtDate"),
    @NamedQuery(name = "GenotypeEntity.findByVersion", query = "SELECT g FROM GenotypeEntity g WHERE g.version = :version")})

public class GenotypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_genotype_key")
    private Integer genotypeKey;

    @Column(name = "allele1")
    private String allele1;

    @Basic(optional = false)
    @Column(name = "all1Conf")
    private boolean all1Conf;

    @Column(name = "allele2")
    private String allele2;

    @Basic(optional = false)
    @Column(name = "all2Conf")
    private boolean all2Conf;

    @Basic(optional = false)
    @Column(name = "genoPage")
    private String genoPage;

    @Column(name = "sampleLocation")
    private String sampleLocation;

    @Column(name = "gtDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gtDate;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @JoinColumn(name = "_gene_key", referencedColumnName = "_gene_key")
    @ManyToOne(optional = false)
    private GeneEntity geneKey;

    @JoinColumn(name = "_mouse_key", referencedColumnName = "_mouse_key")
    @ManyToOne(optional = false)
    private MouseEntity mouseKey;
    
    @Transient
    private Integer genotypeFilterTotal;

    @Transient
    private Integer genotypeMouseTotal;
    
    @Transient
    private String genotypeDisplayFormat;
    
    public GenotypeEntity() {
    }

    public GenotypeEntity(Integer genotypeKey) {
        this.genotypeKey = genotypeKey;
    }

    public GenotypeEntity(Integer genotypeKey, boolean all1Conf, boolean all2Conf, String genoPage, int version) {
        this.genotypeKey = genotypeKey;
        this.all1Conf = all1Conf;
        this.all2Conf = all2Conf;
        this.genoPage = genoPage;
        this.version = version;
    }

    public Integer getGenotypeKey() {
        return genotypeKey;
    }

    public void setGenotypeKey(Integer genotypeKey) {
        this.genotypeKey = genotypeKey;
    }

    public String getAllele1() {
        return allele1;
    }

    public void setAllele1(String allele1) {
        this.allele1 = allele1;
    }

    public boolean getAll1Conf() {
        return all1Conf;
    }

    public void setAll1Conf(boolean all1Conf) {
        this.all1Conf = all1Conf;
    }

    public String getAllele2() {
        return allele2;
    }

    public void setAllele2(String allele2) {
        this.allele2 = allele2;
    }

    public boolean getAll2Conf() {
        return all2Conf;
    }

    public void setAll2Conf(boolean all2Conf) {
        this.all2Conf = all2Conf;
    }

    public String getGenoPage() {
        return genoPage;
    }

    public void setGenoPage(String genoPage) {
        this.genoPage = genoPage;
    }

    public String getSampleLocation() {
        return sampleLocation;
    }

    public void setSampleLocation(String sampleLocation) {
        this.sampleLocation = sampleLocation;
    }

    public Date getGtDate() {
        return gtDate;
    }

    public void setGtDate(Date gtDate) {
        this.gtDate = gtDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public GeneEntity getGeneKey() {
        return geneKey;
    }

    public void setGeneKey(GeneEntity geneKey) {
        this.geneKey = geneKey;
    }

    public MouseEntity getMouseKey() {
        return mouseKey;
    }

    public void setMouseKey(MouseEntity mouseKey) {
        this.mouseKey = mouseKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (genotypeKey != null ? genotypeKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenotypeEntity)) {
            return false;
        }
        GenotypeEntity other = (GenotypeEntity) object;
        if ((this.genotypeKey == null && other.genotypeKey != null) || (this.genotypeKey != null && !this.genotypeKey.equals(other.genotypeKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.GenotypeEntity[genotypeKey=" + genotypeKey + "]";
    }

    @Override
    public Integer getKey() {
        return genotypeKey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }

    /**
     * @return the genotypeFilterTotal
     */
    public Integer getGenotypeFilterTotal() {
        return genotypeFilterTotal;
    }

    /**
     * @param genotypeFilterTotal the genotypeFilterTotal to set
     */
    public void setGenotypeFilterTotal(Integer genotypeFilterTotal) {
        this.genotypeFilterTotal = genotypeFilterTotal;
    }

    /**
     * @return the genotypeMouseTotal
     */
    public Integer getGenotypeMouseTotal() {
        return genotypeMouseTotal;
    }

    /**
     * @param genotypeMouseTotal the genotypeMouseTotal to set
     */
    public void setGenotypeMouseTotal(Integer genotypeMouseTotal) {
        this.genotypeMouseTotal = genotypeMouseTotal;
    }

    /**
     * @return the genotypeDisplayFormat
     */
    public String getGenotypeDisplayFormat() {
        return genotypeDisplayFormat;
    }

    /**
     * @param genotypeDisplayFormat the genotypeDisplayFormat to set
     */
    public void setGenotypeDisplayFormat(String genotypeDisplayFormat) {
        this.genotypeDisplayFormat = genotypeDisplayFormat;
    }
}