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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvSampleTypeEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all sample type information  <p>
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
@Table(name = "cv_SampleType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_sampleType_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvSampleTypeEntity.findAll",
        query = "SELECT c FROM CvSampleTypeEntity c " +
                "ORDER BY c.sampleType"),

    @NamedQuery(
        name = "CvSampleTypeEntity.findBykey",
        query = "SELECT c FROM CvSampleTypeEntity c WHERE c.sampleTypekey = :key"),

    @NamedQuery(name = "CvSampleTypeEntity.findBySampleTypekey", query = "SELECT c FROM CvSampleTypeEntity c WHERE c.sampleTypekey = :sampleTypekey"),
    @NamedQuery(name = "CvSampleTypeEntity.findBySampleType", query = "SELECT c FROM CvSampleTypeEntity c WHERE c.sampleType = :sampleType"),
    @NamedQuery(name = "CvSampleTypeEntity.findByVersion", query = "SELECT c FROM CvSampleTypeEntity c WHERE c.version = :version")})

public class CvSampleTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_sampleType_key", nullable = false)
    private Integer sampleTypekey;

    @Basic(optional = false)
    @Column(name = "sampleType", nullable = false, length = 32)
    private String sampleType;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;
    
    @JoinColumn(name = "_sampleClass_key", referencedColumnName = "_sampleClass_key", nullable = false)
    @ManyToOne(optional = false)
    private CvSampleClassEntity sampleClasskey;

    public CvSampleTypeEntity() {
    }

    public CvSampleTypeEntity(Integer sampleTypekey) {
        this.sampleTypekey = sampleTypekey;
    }

    public CvSampleTypeEntity(Integer sampleTypekey, String sampleType, int version) {
        this.sampleTypekey = sampleTypekey;
        this.sampleType = sampleType;
        this.version = version;
    }

    public Integer getSampleTypekey() {
        return sampleTypekey;
    }

    public void setSampleTypekey(Integer sampleTypekey) {
        this.sampleTypekey = sampleTypekey;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
        hash += (sampleTypekey != null ? sampleTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvSampleTypeEntity)) {
            return false;
        }
        CvSampleTypeEntity other = (CvSampleTypeEntity) object;
        if ((this.sampleTypekey == null && other.sampleTypekey != null) || (this.sampleTypekey != null && !this.sampleTypekey.equals(other.sampleTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvSampleTypeEntity[sampleTypekey=" + sampleTypekey + "]";
    }

    @Override
    public Integer getKey() {
        return sampleTypekey;
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
