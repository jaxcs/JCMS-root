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
import javax.persistence.FetchType;
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
@Table(name = "User")
@NamedQueries({
    @NamedQuery(
    name = "UserEntity.findAll",
    query = "SELECT u FROM UserEntity u ORDER BY u.userName"),

    @NamedQuery(
    name = "UserEntity.findBykey",
    query = "SELECT u FROM UserEntity u WHERE u.userkey = :key"),

    @NamedQuery(name = "UserEntity.findByUserkey", query = "SELECT u FROM UserEntity u WHERE u.userkey = :userkey"),
    @NamedQuery(name = "UserEntity.findByNetworkID", query = "SELECT u FROM UserEntity u WHERE u.networkID = :networkID"),
    @NamedQuery(name = "UserEntity.findByFirstName", query = "SELECT u FROM UserEntity u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UserEntity.findByLastName", query = "SELECT u FROM UserEntity u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "UserEntity.findByTitle", query = "SELECT u FROM UserEntity u WHERE u.title = :title"),
    @NamedQuery(name = "UserEntity.findByEmailAddress", query = "SELECT u FROM UserEntity u WHERE u.emailAddress = :emailAddress"),
    @NamedQuery(name = "UserEntity.findByInternalPhone", query = "SELECT u FROM UserEntity u WHERE u.internalPhone = :internalPhone"),
    @NamedQuery(name = "UserEntity.findByExternalPhone", query = "SELECT u FROM UserEntity u WHERE u.externalPhone = :externalPhone"),
    @NamedQuery(name = "UserEntity.findByHireDate", query = "SELECT u FROM UserEntity u WHERE u.hireDate = :hireDate"),
    @NamedQuery(name = "UserEntity.findByIsActive", query = "SELECT u FROM UserEntity u WHERE u.isActive = :isActive"),
    @NamedQuery(name = "UserEntity.findByCreatedBy", query = "SELECT u FROM UserEntity u WHERE u.createdBy = :createdBy"),
    @NamedQuery(name = "UserEntity.findByDateCreated", query = "SELECT u FROM UserEntity u WHERE u.dateCreated = :dateCreated"),
    @NamedQuery(name = "UserEntity.findByModifiedBy", query = "SELECT u FROM UserEntity u WHERE u.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "UserEntity.findByDateModified", query = "SELECT u FROM UserEntity u WHERE u.dateModified = :dateModified"),
    @NamedQuery(name = "UserEntity.findByVersion", query = "SELECT u FROM UserEntity u WHERE u.version = :version"),
    @NamedQuery(name = "UserEntity.findByIsMasterAdministrator", query = "SELECT u FROM UserEntity u WHERE u.isMasterAdministrator = :isMasterAdministrator"),
    @NamedQuery(name = "UserEntity.findByUserName", query = "SELECT u FROM UserEntity u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserEntity.findByPassword", query = "SELECT u FROM UserEntity u WHERE u.password = :password")})

public class UserEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "_User_key")
    private Integer userkey;

    @Column(name = "NetworkID")
    private String networkID;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Title")
    private String title;

    @Column(name = "EmailAddress")
    private String emailAddress;

    @Column(name = "InternalPhone")
    private String internalPhone;

    @Column(name = "ExternalPhone")
    private String externalPhone;

    @Column(name = "HireDate")
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @Column(name = "IsActive")
    private Short isActive;
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

    @Column(name = "IsMasterAdministrator")
    private Short isMasterAdministrator;
    @Basic(optional = false)

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Password_")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userkey", fetch = FetchType.EAGER)
    private Collection<WorkgroupUserEntity> workgroupUserEntityCollection;

    @JoinColumn(name = "_DefaultWorkgroup_key", referencedColumnName = "_Workgroup_key")
    @ManyToOne(optional = false)
    private WorkgroupEntity defaultWorkgroupkey;
    
    @Column(name = "passwordChangedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordChangedDate;

    public UserEntity() {
    }

    public UserEntity(Integer userkey) {
        this.userkey = userkey;
    }

    public UserEntity(Integer userkey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version, String userName) {
        this.userkey = userkey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
        this.userName = userName;
    }

    public Integer getUserkey() {
        return userkey;
    }

    public void setUserkey(Integer userkey) {
        this.userkey = userkey;
    }

    public String getNetworkID() {
        return networkID;
    }

    public void setNetworkID(String networkID) {
        this.networkID = networkID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInternalPhone() {
        return internalPhone;
    }

    public void setInternalPhone(String internalPhone) {
        this.internalPhone = internalPhone;
    }

    public String getExternalPhone() {
        return externalPhone;
    }

    public void setExternalPhone(String externalPhone) {
        this.externalPhone = externalPhone;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
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

    public Short getIsMasterAdministrator() {
        return isMasterAdministrator;
    }

    public void setIsMasterAdministrator(Short isMasterAdministrator) {
        this.isMasterAdministrator = isMasterAdministrator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<WorkgroupUserEntity> getWorkgroupUserEntityCollection() {
        return workgroupUserEntityCollection;
    }

    public void setWorkgroupUserEntityCollection(Collection<WorkgroupUserEntity> workgroupUserEntityCollection) {
        this.workgroupUserEntityCollection = workgroupUserEntityCollection;
    }

    public WorkgroupEntity getDefaultWorkgroupkey() {
        return defaultWorkgroupkey;
    }

    public void setDefaultWorkgroupkey(WorkgroupEntity defaultWorkgroupkey) {
        this.defaultWorkgroupkey = defaultWorkgroupkey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userkey != null ? userkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.userkey == null && other.userkey != null) || (this.userkey != null && !this.userkey.equals(other.userkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.UserEntity[userkey=" + userkey + "]";
    }

    @Override
    public Integer getKey() {
        return userkey;
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
        System.out.println("\tDatabase key" + "\t" + this.userkey );
        System.out.println("\tMajor Version" + "\t" + this.getKey());
        System.out.println("\tMinor Version" + "\t" + this.getUserName() );
        System.out.println("\tBug Fix Version" + "\t" + this.getFirstName() );
        System.out.println("\n");
    }

    /**
     * @return the passwordChangedDate
     */
    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    /**
     * @param passwordChangedDate the passwordChangedDate to set
     */
    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }
}