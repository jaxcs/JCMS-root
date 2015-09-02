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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvSampleDataEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all sample data information   <p>
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
@Table(name = "cv_SampleDateType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_sampleDateType_key"}),
    @UniqueConstraint(columnNames = {"sampleDateType"})})
@NamedQueries({
    @NamedQuery(
        name = "CvSampleDateTypeEntity.findAll",
        query = "SELECT c FROM CvSampleDateTypeEntity c " +
                "ORDER BY c.sampleDateType"),

    @NamedQuery(
        name = "CvSampleDateTypeEntity.findBykey",
        query = "SELECT c FROM CvSampleDateTypeEntity c WHERE c.sampleDateTypekey = :key"),

    @NamedQuery(name = "CvSampleDateTypeEntity.findBySampleDateTypekey", query = "SELECT c FROM CvSampleDateTypeEntity c WHERE c.sampleDateTypekey = :sampleDateTypekey"),
    @NamedQuery(name = "CvSampleDateTypeEntity.findBySampleDateType", query = "SELECT c FROM CvSampleDateTypeEntity c WHERE c.sampleDateType = :sampleDateType"),
    @NamedQuery(name = "CvSampleDateTypeEntity.findByVersion", query = "SELECT c FROM CvSampleDateTypeEntity c WHERE c.version = :version")})

public class CvSampleDateTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_sampleDateType_key", nullable = false)
    private Integer sampleDateTypekey;

    @Basic(optional = false)
    @Column(name = "sampleDateType", nullable = false, length = 32)
    private String sampleDateType;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvSampleDateTypeEntity() {
    }

    public CvSampleDateTypeEntity(Integer sampleDateTypekey) {
        this.sampleDateTypekey = sampleDateTypekey;
    }

    public CvSampleDateTypeEntity(Integer sampleDateTypekey, String sampleDateType, int version) {
        this.sampleDateTypekey = sampleDateTypekey;
        this.sampleDateType = sampleDateType;
        this.version = version;
    }

    public Integer getSampleDateTypekey() {
        return sampleDateTypekey;
    }

    public void setSampleDateTypekey(Integer sampleDateTypekey) {
        this.sampleDateTypekey = sampleDateTypekey;
    }

    public String getSampleDateType() {
        return sampleDateType;
    }

    public void setSampleDateType(String sampleDateType) {
        this.sampleDateType = sampleDateType;
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
        hash += (sampleDateTypekey != null ? sampleDateTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvSampleDateTypeEntity)) {
            return false;
        }
        CvSampleDateTypeEntity other = (CvSampleDateTypeEntity) object;
        if ((this.sampleDateTypekey == null && other.sampleDateTypekey != null) || (this.sampleDateTypekey != null && !this.sampleDateTypekey.equals(other.sampleDateTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvSampleDateTypeEntity[sampleDateTypekey=" + sampleDateTypekey + "]";
    }

    @Override
    public Integer getKey() {
        return sampleDateTypekey;
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
