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

package jcms.integrationtier.cv;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvPreservationTypeEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all preservation type information  <p>
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
@Table(name = "cv_PreservationType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_preservationType_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvPreservationTypeEntity.findAll",
        query = "SELECT c FROM CvPreservationTypeEntity c " +
                "ORDER BY c.preservationType"),

    @NamedQuery(
        name = "CvPreservationTypeEntity.findBykey",
        query = "SELECT c FROM CvPreservationTypeEntity c WHERE c.preservationTypekey = :key"),

    @NamedQuery(name = "CvPreservationTypeEntity.findByPreservationTypekey", query = "SELECT c FROM CvPreservationTypeEntity c WHERE c.preservationTypekey = :preservationTypekey"),
    @NamedQuery(name = "CvPreservationTypeEntity.findByPreservationType", query = "SELECT c FROM CvPreservationTypeEntity c WHERE c.preservationType = :preservationType"),
    @NamedQuery(name = "CvPreservationTypeEntity.findByVersion", query = "SELECT c FROM CvPreservationTypeEntity c WHERE c.version = :version")})

public class CvPreservationTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_preservationType_key", nullable = false)
    private Integer preservationTypekey;

    @Basic(optional = false)
    @Column(name = "preservationType", nullable = false, length = 32)
    private String preservationType;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preservationTypekey")
    private Collection<CvPreservationMethodEntity> cvPreservationMethodEntityCollection;
    
    @JoinColumn(name = "_sampleClass_key", referencedColumnName = "_sampleClass_key", nullable = false)
    @ManyToOne(optional = false)
    private CvSampleClassEntity sampleClasskey;

    public CvPreservationTypeEntity() {
    }

    public CvPreservationTypeEntity(Integer preservationTypekey) {
        this.preservationTypekey = preservationTypekey;
    }

    public CvPreservationTypeEntity(Integer preservationTypekey, String preservationType, int version) {
        this.preservationTypekey = preservationTypekey;
        this.preservationType = preservationType;
        this.version = version;
    }

    public Integer getPreservationTypekey() {
        return preservationTypekey;
    }

    public void setPreservationTypekey(Integer preservationTypekey) {
        this.preservationTypekey = preservationTypekey;
    }

    public String getPreservationType() {
        return preservationType;
    }

    public void setPreservationType(String preservationType) {
        this.preservationType = preservationType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<CvPreservationMethodEntity> getCvPreservationMethodEntityCollection() {
        return cvPreservationMethodEntityCollection;
    }

    public void setCvPreservationMethodEntityCollection(Collection<CvPreservationMethodEntity> cvPreservationMethodEntityCollection) {
        this.cvPreservationMethodEntityCollection = cvPreservationMethodEntityCollection;
    }

    public CvSampleClassEntity getSampleClasskey() {
        return sampleClasskey;
    }

    public void setSampleClasskey(CvSampleClassEntity sampleClasskey) {
        this.sampleClasskey = sampleClasskey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preservationTypekey != null ? preservationTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvPreservationTypeEntity)) {
            return false;
        }
        CvPreservationTypeEntity other = (CvPreservationTypeEntity) object;
        if ((this.preservationTypekey == null && other.preservationTypekey != null) || (this.preservationTypekey != null && !this.preservationTypekey.equals(other.preservationTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvPreservationTypeEntity[preservationTypekey=" + preservationTypekey + "]";
    }

    @Override
    public Integer getKey() {
        return preservationTypekey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }
}