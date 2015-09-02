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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "Workgroup")
@NamedQueries({
    @NamedQuery(
    name = "WorkgroupEntity.findAll",
    query = "SELECT w FROM WorkgroupEntity w ORDER BY w.workgroupName"),

    @NamedQuery(
    name = "WorkgroupEntity.findBykey",
    query = "SELECT w FROM WorkgroupEntity w WHERE w.workgroupkey = :key"),

    @NamedQuery(name = "WorkgroupEntity.findByWorkgroupkey", query = "SELECT w FROM WorkgroupEntity w WHERE w.workgroupkey = :workgroupkey"),
    @NamedQuery(name = "WorkgroupEntity.findByWorkgroupName", query = "SELECT w FROM WorkgroupEntity w WHERE w.workgroupName = :workgroupName"),
    @NamedQuery(name = "WorkgroupEntity.findByIsActive", query = "SELECT w FROM WorkgroupEntity w WHERE w.isActive = :isActive"),
    @NamedQuery(name = "WorkgroupEntity.findByCreatedBy", query = "SELECT w FROM WorkgroupEntity w WHERE w.createdBy = :createdBy"),
    @NamedQuery(name = "WorkgroupEntity.findByDateCreated", query = "SELECT w FROM WorkgroupEntity w WHERE w.dateCreated = :dateCreated"),
    @NamedQuery(name = "WorkgroupEntity.findByModifiedBy", query = "SELECT w FROM WorkgroupEntity w WHERE w.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "WorkgroupEntity.findByDateModified", query = "SELECT w FROM WorkgroupEntity w WHERE w.dateModified = :dateModified"),
    @NamedQuery(name = "WorkgroupEntity.findByVersion", query = "SELECT w FROM WorkgroupEntity w WHERE w.version = :version")})

public class WorkgroupEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "_Workgroup_key")
    private Integer workgroupkey;
    @Basic(optional = false)

    @Column(name = "WorkgroupName")
    private String workgroupName;
    @Basic(optional = false)

    @Column(name = "isActive")
    private short isActive;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workgroupkey")
    private Collection<WorkgroupUserEntity> workgroupUserEntityCollection;

    @OneToMany(mappedBy = "workgroupkey")
    private Collection<QueryDefinitionEntity> QueryDefinitionEntityCollection;

    @JoinColumn(name = "_Center_key", referencedColumnName = "_Center_key")
    @ManyToOne(optional = false)
    private CenterEntity centerkey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "defaultWorkgroupkey")
    private Collection<UserEntity> userEntityCollection;

    public WorkgroupEntity() {
    }

    public WorkgroupEntity(Integer workgroupkey) {
        this.workgroupkey = workgroupkey;
    }

    public WorkgroupEntity(Integer workgroupkey, String workgroupName, short isActive, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.workgroupkey = workgroupkey;
        this.workgroupName = workgroupName;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getWorkgroupkey() {
        return workgroupkey;
    }

    public void setWorkgroupkey(Integer workgroupkey) {
        this.workgroupkey = workgroupkey;
    }

    public String getWorkgroupName() {
        return workgroupName;
    }

    public void setWorkgroupName(String workgroupName) {
        this.workgroupName = workgroupName;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
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

    public Collection<WorkgroupUserEntity> getWorkgroupUserEntityCollection() {
        return workgroupUserEntityCollection;
    }

    public void setWorkgroupUserEntityCollection(Collection<WorkgroupUserEntity> workgroupUserEntityCollection) {
        this.workgroupUserEntityCollection = workgroupUserEntityCollection;
    }

    public Collection<QueryDefinitionEntity> getQueryDefinitionEntityCollection() {
        return QueryDefinitionEntityCollection;
    }

    public void setQueryDefinitionEntityCollection(Collection<QueryDefinitionEntity> QueryDefinitionEntityCollection) {
        this.QueryDefinitionEntityCollection = QueryDefinitionEntityCollection;
    }

    public CenterEntity getCenterkey() {
        return centerkey;
    }

    public void setCenterkey(CenterEntity centerkey) {
        this.centerkey = centerkey;
    }

    public Collection<UserEntity> getUserEntityCollection() {
        return userEntityCollection;
    }

    public void setUserEntityCollection(Collection<UserEntity> userEntityCollection) {
        this.userEntityCollection = userEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workgroupkey != null ? workgroupkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkgroupEntity)) {
            return false;
        }
        WorkgroupEntity other = (WorkgroupEntity) object;
        if ((this.workgroupkey == null && other.workgroupkey != null) || (this.workgroupkey != null && !this.workgroupkey.equals(other.workgroupkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.WorkgroupEntity[workgroupkey=" + workgroupkey + "]";
    }

    @Override
    public Integer getKey() {
        return workgroupkey;
    }

    @Override
    public ITBaseEntityInterface getEntity()
    {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println("\tDbInfoEntity Contents");
        System.out.println("\tDatabase key" + "\t" + this.workgroupkey );
        System.out.println("\tMajor Version" + "\t" + this.getKey());
        System.out.println("\tMinor Version" + "\t" + this.getWorkgroupName() );
        System.out.println("\tBug Fix Version" + "\t" + this.getVersion() );
        System.out.println("\n");
    }
}