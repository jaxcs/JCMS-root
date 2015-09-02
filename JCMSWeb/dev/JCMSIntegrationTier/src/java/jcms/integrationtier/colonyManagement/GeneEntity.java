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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.dto.AdminGeneDTO;

/**
 * <b>File name:</b>  GeneEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Gene information like gene symbol, genotype etc <p>
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
@Table(name = "Gene")
@NamedQueries({
    @NamedQuery(
    name = "GeneEntity.findAll",
            query = "SELECT g FROM GeneEntity g " +
                "ORDER BY g.labSymbol "),

    @NamedQuery(
    name = "GeneEntity.findAllByOwner",
            query = "SELECT DISTINCT g FROM GeneEntity g, GenotypeEntity gt, MouseEntity m " +
            "WHERE g.geneKey = gt.geneKey AND gt.mouseKey = m.mouseKey " +
            "AND m.owner = :fOwner " +
            "ORDER BY g.labSymbol "),

    @NamedQuery(
    name = "GeneEntity.findAllByOwners",
            query = "SELECT DISTINCT g FROM GeneEntity g, GenotypeEntity gt, MouseEntity m " +
            "WHERE g.geneKey = gt.geneKey AND gt.mouseKey = m.mouseKey " +
            "AND m.owner IN (:lOwner) " +
            "ORDER BY g.labSymbol "),

    @NamedQuery(
    name = "GeneEntity.findBykey",
            query = "SELECT g FROM GeneEntity g WHERE g.geneKey = :key"),

    @NamedQuery(name = "GeneEntity.findByGeneKey", query = "SELECT g FROM GeneEntity g WHERE g.geneKey = :geneKey"),
    @NamedQuery(name = "GeneEntity.findByGeneSymbol", query = "SELECT g FROM GeneEntity g WHERE g.geneSymbol = :geneSymbol"),
    @NamedQuery(name = "GeneEntity.findByLabSymbol", query = "SELECT g FROM GeneEntity g WHERE g.labSymbol = :labSymbol"),
    @NamedQuery(name = "GeneEntity.findByGeneClass", query = "SELECT g FROM GeneEntity g WHERE g.geneClass = :geneClass"),
    @NamedQuery(name = "GeneEntity.findByChromosome", query = "SELECT g FROM GeneEntity g WHERE g.chromosome = :chromosome"),
    @NamedQuery(name = "GeneEntity.findByCM", query = "SELECT g FROM GeneEntity g WHERE g.cM = :cM"),
    @NamedQuery(name = "GeneEntity.findByMegabase", query = "SELECT g FROM GeneEntity g WHERE g.megabase = :megabase"),
    @NamedQuery(name = "GeneEntity.findByVersion", query = "SELECT g FROM GeneEntity g WHERE g.version = :version")})

public class GeneEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_gene_key")
    private Integer geneKey;

    @Column(name = "geneSymbol")
    private String geneSymbol;

    @Basic(optional = false)
    @Column(name = "labSymbol")
    private String labSymbol;

    @Column(name = "geneClass")
    private String geneClass;

    @Column(name = "chromosome")
    private String chromosome;

    @Column(name = "cM")
    private Double cM;

    @Column(name = "megabase")
    private Float megabase;

    @Lob
    @Column(name = "comment")
    private String comment;
    
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public GeneEntity() {
    }

    public GeneEntity(Integer geneKey) {
        this.geneKey = geneKey;
    }
    
    public GeneEntity(Integer geneKey, String labSymbol, int version) {
        this.geneKey = geneKey;
        this.labSymbol = labSymbol;
        this.version = version;
    }

    public GeneEntity(AdminGeneDTO dto) {
        this.setGeneKey((dto.getGeneKey().length() > 0 ? Integer.parseInt(dto.getGeneKey()) : 0));
        this.setCM((dto.getcM().length() > 0 ? Double.parseDouble(dto.getcM()) : 0));
        this.setMegabase((dto.getMegabase().length() > 0 ? Float.parseFloat(dto.getMegabase()) : 0));
        this.setChromosome(dto.getChromosome());
        this.setComment(dto.getComment());
        this.setGeneClass(dto.getGeneClass());
        this.setGeneSymbol(dto.getGeneSymbol());
        this.setLabSymbol(dto.getLabSymbol());
        this.setVersion(Integer.parseInt(dto.getVersion()));
    }
    public Integer getGeneKey() {
        return geneKey;
    }

    public void setGeneKey(Integer geneKey) {
        this.geneKey = geneKey;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getLabSymbol() {
        return labSymbol;
    }

    public void setLabSymbol(String labSymbol) {
        this.labSymbol = labSymbol;
    }

    public String getGeneClass() {
        return geneClass;
    }

    public void setGeneClass(String geneClass) {
        this.geneClass = geneClass;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Double getCM() {
        return cM;
    }

    public void setCM(Double cM) {
        this.cM = cM;
    }

    public Float getMegabase() {
        return megabase;
    }

    public void setMegabase(Float megabase) {
        this.megabase = megabase;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geneKey != null ? geneKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneEntity)) {
            return false;
        }
        GeneEntity other = (GeneEntity) object;
        if ((this.geneKey == null && other.geneKey != null) || (this.geneKey != null && !this.geneKey.equals(other.geneKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.GeneEntity[geneKey=" + geneKey + "]";
    }

    @Override
    public Integer getKey() {
        return geneKey;
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
        System.out.println("\tPK" + "\t" + this.getGeneKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tPen ID" + "\t" + this.getGeneSymbol() );
        System.out.println("\tPen Name" + "\t" + this.getLabSymbol() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }
}
