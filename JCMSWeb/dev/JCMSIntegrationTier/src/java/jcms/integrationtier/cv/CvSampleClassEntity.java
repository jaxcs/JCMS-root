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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvSampleClassEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all sample classs information  <p>
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
@Table(name = "cv_SampleClass", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_sampleClass_key"}),
    @UniqueConstraint(columnNames = {"sampleClass"})})
@NamedQueries({
    @NamedQuery(
        name = "CvSampleClassEntity.findAll",
        query = "SELECT c FROM CvSampleClassEntity c " +
                "ORDER BY c.sampleClass"),

    @NamedQuery(
        name = "CvSampleClassEntity.findBykey",
        query = "SELECT c FROM CvSampleClassEntity c WHERE c.sampleClasskey = :key"),

    @NamedQuery(name = "CvSampleClassEntity.findBySampleClasskey", query = "SELECT c FROM CvSampleClassEntity c WHERE c.sampleClasskey = :sampleClasskey"),
    @NamedQuery(name = "CvSampleClassEntity.findBySampleClass", query = "SELECT c FROM CvSampleClassEntity c WHERE c.sampleClass = :sampleClass"),
    @NamedQuery(name = "CvSampleClassEntity.findByVersion", query = "SELECT c FROM CvSampleClassEntity c WHERE c.version = :version")})

public class CvSampleClassEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_sampleClass_key", nullable = false)
    private Integer sampleClasskey;

    @Basic(optional = false)
    @Column(name = "sampleClass", nullable = false, length = 32)
    private String sampleClass;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sampleClasskey")
    private Collection<CvPreservationTypeEntity> cvPreservationTypeEntityCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sampleClasskey")
    private Collection<CvSampleTypeEntity> cvSampleTypeEntityCollection;

    public CvSampleClassEntity() {
    }

    public CvSampleClassEntity(Integer sampleClasskey) {
        this.sampleClasskey = sampleClasskey;
    }

    public CvSampleClassEntity(Integer sampleClasskey, String sampleClass, int version) {
        this.sampleClasskey = sampleClasskey;
        this.sampleClass = sampleClass;
        this.version = version;
    }

    public Integer getSampleClasskey() {
        return sampleClasskey;
    }

    public void setSampleClasskey(Integer sampleClasskey) {
        this.sampleClasskey = sampleClasskey;
    }

    public String getSampleClass() {
        return sampleClass;
    }

    public void setSampleClass(String sampleClass) {
        this.sampleClass = sampleClass;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<CvPreservationTypeEntity> getCvPreservationTypeEntityCollection() {
        return cvPreservationTypeEntityCollection;
    }

    public void setCvPreservationTypeEntityCollection(Collection<CvPreservationTypeEntity> cvPreservationTypeEntityCollection) {
        this.cvPreservationTypeEntityCollection = cvPreservationTypeEntityCollection;
    }

    public Collection<CvSampleTypeEntity> getCvSampleTypeEntityCollection() {
        return cvSampleTypeEntityCollection;
    }

    public void setCvSampleTypeEntityCollection(Collection<CvSampleTypeEntity> cvSampleTypeEntityCollection) {
        this.cvSampleTypeEntityCollection = cvSampleTypeEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sampleClasskey != null ? sampleClasskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvSampleClassEntity)) {
            return false;
        }
        CvSampleClassEntity other = (CvSampleClassEntity) object;
        if ((this.sampleClasskey == null && other.sampleClasskey != null) || (this.sampleClasskey != null && !this.sampleClasskey.equals(other.sampleClasskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvSampleClassEntity[sampleClasskey=" + sampleClasskey + "]";
    }

    @Override
    public Integer getKey() {
        return sampleClasskey;
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
