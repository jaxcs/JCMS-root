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
 * <b>File name:</b>  CvSampleStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all sample status information   <p>
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
@Table(name = "cv_SampleStatus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_sampleStatus_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvSampleStatusEntity.findAll",
        query = "SELECT c FROM CvSampleStatusEntity c " +
                "ORDER BY c.sampleStatus"),

    @NamedQuery(
        name = "CvSampleStatusEntity.findBykey",
        query = "SELECT c FROM CvSampleStatusEntity c WHERE c.sampleStatuskey = :key"),

    @NamedQuery(name = "CvSampleStatusEntity.findBySampleStatuskey", query = "SELECT c FROM CvSampleStatusEntity c WHERE c.sampleStatuskey = :sampleStatuskey"),
    @NamedQuery(name = "CvSampleStatusEntity.findBySampleStatus", query = "SELECT c FROM CvSampleStatusEntity c WHERE c.sampleStatus = :sampleStatus"),
    @NamedQuery(name = "CvSampleStatusEntity.findByIsInStorage", query = "SELECT c FROM CvSampleStatusEntity c WHERE c.isInStorage = :isInStorage"),
    @NamedQuery(name = "CvSampleStatusEntity.findByVersion", query = "SELECT c FROM CvSampleStatusEntity c WHERE c.version = :version")})

public class CvSampleStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_sampleStatus_key", nullable = false)
    private Integer sampleStatuskey;

    @Basic(optional = false)
    @Column(name = "sampleStatus", nullable = false, length = 32)
    private String sampleStatus;

    @Column(name = "isInStorage")
    private Boolean isInStorage;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvSampleStatusEntity() {
    }

    public CvSampleStatusEntity(Integer sampleStatuskey) {
        this.sampleStatuskey = sampleStatuskey;
    }

    public CvSampleStatusEntity(Integer sampleStatuskey, String sampleStatus, int version) {
        this.sampleStatuskey = sampleStatuskey;
        this.sampleStatus = sampleStatus;
        this.version = version;
    }

    public Integer getSampleStatuskey() {
        return sampleStatuskey;
    }

    public void setSampleStatuskey(Integer sampleStatuskey) {
        this.sampleStatuskey = sampleStatuskey;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public Boolean getIsInStorage() {
        return isInStorage;
    }

    public void setIsInStorage(Boolean isInStorage) {
        this.isInStorage = isInStorage;
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
        hash += (sampleStatuskey != null ? sampleStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvSampleStatusEntity)) {
            return false;
        }
        CvSampleStatusEntity other = (CvSampleStatusEntity) object;
        if ((this.sampleStatuskey == null && other.sampleStatuskey != null) || (this.sampleStatuskey != null && !this.sampleStatuskey.equals(other.sampleStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvSampleStatusEntity[sampleStatuskey=" + sampleStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return sampleStatuskey;
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
