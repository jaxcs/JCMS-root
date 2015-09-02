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

package jcms.integrationtier.jcmsWeb;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "Center")
@NamedQueries({
    @NamedQuery(name = "CenterEntity.findAll", query = "SELECT c FROM CenterEntity c"),
    @NamedQuery(name = "CenterEntity.findByCenterkey", query = "SELECT c FROM CenterEntity c WHERE c.centerkey = :centerkey"),
    @NamedQuery(name = "CenterEntity.findByCenter", query = "SELECT c FROM CenterEntity c WHERE c.center = :center"),
    @NamedQuery(name = "CenterEntity.findByCreatedBy", query = "SELECT c FROM CenterEntity c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "CenterEntity.findByDateCreated", query = "SELECT c FROM CenterEntity c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CenterEntity.findByModifiedBy", query = "SELECT c FROM CenterEntity c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CenterEntity.findByDateModified", query = "SELECT c FROM CenterEntity c WHERE c.dateModified = :dateModified"),
    @NamedQuery(name = "CenterEntity.findByVersion", query = "SELECT c FROM CenterEntity c WHERE c.version = :version")})
public class CenterEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_Center_key")
    private Integer centerkey;
    @Column(name = "Center")
    private String center;
    @Basic(optional = false)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "DateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "ModifiedBy")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "DateModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @Basic(optional = false)
    @Column(name = "Version")
    private int version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centerkey")
    private Collection<WorkgroupEntity> workgroupEntityCollection;

    public CenterEntity() {
    }

    public CenterEntity(Integer centerkey) {
        this.centerkey = centerkey;
    }

    public CenterEntity(Integer centerkey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.centerkey = centerkey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getCenterkey() {
        return centerkey;
    }

    public void setCenterkey(Integer centerkey) {
        this.centerkey = centerkey;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<WorkgroupEntity> getWorkgroupEntityCollection() {
        return workgroupEntityCollection;
    }

    public void setWorkgroupEntityCollection(Collection<WorkgroupEntity> workgroupEntityCollection) {
        this.workgroupEntityCollection = workgroupEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (centerkey != null ? centerkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CenterEntity)) {
            return false;
        }
        CenterEntity other = (CenterEntity) object;
        if ((this.centerkey == null && other.centerkey != null) || (this.centerkey != null && !this.centerkey.equals(other.centerkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.CenterEntity[centerkey=" + centerkey + "]";
    }

}
