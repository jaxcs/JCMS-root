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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "WorkgroupUserFunctionalArea")
@NamedQueries({
    @NamedQuery(
    name = "WorkgroupUserFunctionalAreaEntity.findAll",
            query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w"),

    @NamedQuery(
    name = "WorkgroupUserFunctionalAreaEntity.findBykey",
    query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.workgroupUserFunctionalAreakey = :key"),

    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByWorkgroupUserFunctionalAreakey", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.workgroupUserFunctionalAreakey = :workgroupUserFunctionalAreakey"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByPrivilegekey", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.privilegekey = :privilegekey"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByCreatedBy", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.createdBy = :createdBy"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByDateCreated", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.dateCreated = :dateCreated"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByModifiedBy", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByDateModified", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.dateModified = :dateModified"),
    @NamedQuery(name = "WorkgroupUserFunctionalAreaEntity.findByVersion", query = "SELECT w FROM WorkgroupUserFunctionalAreaEntity w WHERE w.version = :version")})
    
public class WorkgroupUserFunctionalAreaEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "_WorkgroupUserFunctionalArea_key")
    private Integer workgroupUserFunctionalAreakey;
    @Basic(optional = false)

    @Column(name = "_Privilege_key")
    private int privilegekey;
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

    @JoinColumn(name = "_FunctionalArea_key", referencedColumnName = "_FunctionalArea_key")
    @ManyToOne(optional = false)
    private FunctionalAreaEntity functionalAreakey;

    @JoinColumn(name = "_WorkgroupUser_key", referencedColumnName = "_WorkgroupUser_key")
    @ManyToOne(optional = false)
    private WorkgroupUserEntity workgroupUserkey;

    public WorkgroupUserFunctionalAreaEntity() {
    }

    public WorkgroupUserFunctionalAreaEntity(Integer workgroupUserFunctionalAreakey) {
        this.workgroupUserFunctionalAreakey = workgroupUserFunctionalAreakey;
    }

    public WorkgroupUserFunctionalAreaEntity(Integer workgroupUserFunctionalAreakey, int privilegekey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.workgroupUserFunctionalAreakey = workgroupUserFunctionalAreakey;
        this.privilegekey = privilegekey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getWorkgroupUserFunctionalAreakey() {
        return workgroupUserFunctionalAreakey;
    }

    public void setWorkgroupUserFunctionalAreakey(Integer workgroupUserFunctionalAreakey) {
        this.workgroupUserFunctionalAreakey = workgroupUserFunctionalAreakey;
    }

    public int getPrivilegekey() {
        return privilegekey;
    }

    public void setPrivilegekey(int privilegekey) {
        this.privilegekey = privilegekey;
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

    public FunctionalAreaEntity getFunctionalAreakey() {
        return functionalAreakey;
    }

    public void setFunctionalAreakey(FunctionalAreaEntity functionalAreakey) {
        this.functionalAreakey = functionalAreakey;
    }

    public WorkgroupUserEntity getWorkgroupUserkey() {
        return workgroupUserkey;
    }

    public void setWorkgroupUserkey(WorkgroupUserEntity workgroupUserkey) {
        this.workgroupUserkey = workgroupUserkey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workgroupUserFunctionalAreakey != null ? workgroupUserFunctionalAreakey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkgroupUserFunctionalAreaEntity)) {
            return false;
        }
        WorkgroupUserFunctionalAreaEntity other = (WorkgroupUserFunctionalAreaEntity) object;
        if ((this.workgroupUserFunctionalAreakey == null && other.workgroupUserFunctionalAreakey != null) || (this.workgroupUserFunctionalAreakey != null && !this.workgroupUserFunctionalAreakey.equals(other.workgroupUserFunctionalAreakey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.WorkgroupUserFunctionalAreaEntity[workgroupUserFunctionalAreakey=" + workgroupUserFunctionalAreakey + "]";
    }

    @Override
    public Integer getKey() {
        return workgroupUserFunctionalAreakey;
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
        System.out.println("\tDatabase key" + "\t" + this.workgroupUserFunctionalAreakey );
        System.out.println("\tMajor Version" + "\t" + this.getKey());
        System.out.println("\tBug Fix Version" + "\t" + this.getVersion() );
        System.out.println("\n");
    }
}