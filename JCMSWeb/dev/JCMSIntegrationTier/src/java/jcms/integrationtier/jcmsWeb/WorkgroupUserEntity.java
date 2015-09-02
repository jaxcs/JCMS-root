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
@Table(name = "WorkgroupUser")
@NamedQueries({
    @NamedQuery(
    name = "WorkgroupUserEntity.findAll",
            query = "SELECT w FROM WorkgroupUserEntity w"),

    @NamedQuery(
    name = "WorkgroupUserEntity.findBykey",
            query = "SELECT w FROM WorkgroupUserEntity w WHERE w.workgroupUserkey = :key"),

    @NamedQuery(name = "WorkgroupUserEntity.findByWorkgroupUserkey", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.workgroupUserkey = :workgroupUserkey"),
    @NamedQuery(name = "WorkgroupUserEntity.findByCreatedBy", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.createdBy = :createdBy"),
    @NamedQuery(name = "WorkgroupUserEntity.findByDateCreated", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.dateCreated = :dateCreated"),
    @NamedQuery(name = "WorkgroupUserEntity.findByModifiedBy", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "WorkgroupUserEntity.findByDateModified", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.dateModified = :dateModified"),
    @NamedQuery(name = "WorkgroupUserEntity.findByVersion", query = "SELECT w FROM WorkgroupUserEntity w WHERE w.version = :version")})

public class WorkgroupUserEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "_WorkgroupUser_key")
    private Integer workgroupUserkey;
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

    @JoinColumn(name = "_User_key", referencedColumnName = "_User_key")
    @ManyToOne(optional = false)
    private UserEntity userkey;

    @JoinColumn(name = "_Workgroup_key", referencedColumnName = "_Workgroup_key")
    @ManyToOne(optional = false)
    private WorkgroupEntity workgroupkey;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workgroupUserkey")
    private Collection<WorkgroupUserFunctionalAreaEntity> workgroupUserFunctionalAreaEntityCollection;

    public WorkgroupUserEntity() {
    }

    public WorkgroupUserEntity(Integer workgroupUserkey) {
        this.workgroupUserkey = workgroupUserkey;
    }

    public WorkgroupUserEntity(Integer workgroupUserkey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.workgroupUserkey = workgroupUserkey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getWorkgroupUserkey() {
        return workgroupUserkey;
    }

    public void setWorkgroupUserkey(Integer workgroupUserkey) {
        this.workgroupUserkey = workgroupUserkey;
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

    public UserEntity getUserkey() {
        return userkey;
    }

    public void setUserkey(UserEntity userkey) {
        this.userkey = userkey;
    }

    public WorkgroupEntity getWorkgroupkey() {
        return workgroupkey;
    }

    public void setWorkgroupkey(WorkgroupEntity workgroupkey) {
        this.workgroupkey = workgroupkey;
    }

    public Collection<WorkgroupUserFunctionalAreaEntity> getWorkgroupUserFunctionalAreaEntityCollection() {
        return workgroupUserFunctionalAreaEntityCollection;
    }

    public void setWorkgroupUserFunctionalAreaEntityCollection(Collection<WorkgroupUserFunctionalAreaEntity> workgroupUserFunctionalAreaEntityCollection) {
        this.workgroupUserFunctionalAreaEntityCollection = workgroupUserFunctionalAreaEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workgroupUserkey != null ? workgroupUserkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkgroupUserEntity)) {
            return false;
        }
        WorkgroupUserEntity other = (WorkgroupUserEntity) object;
        if ((this.workgroupUserkey == null && other.workgroupUserkey != null) || (this.workgroupUserkey != null && !this.workgroupUserkey.equals(other.workgroupUserkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.WorkgroupUserEntity[workgroupUserkey=" + workgroupUserkey + "]";
    }

    @Override
    public Integer getKey() {
        return workgroupUserkey;
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
        System.out.println("\t=====================");
        System.out.println("\tDatabase key" + "\t" + this.workgroupUserkey );
        System.out.println("\tMajor Version" + "\t" + this.getKey());
        System.out.println("\tBug Fix Version" + "\t" + this.getVersion() );
        System.out.println("\n");
    }
}