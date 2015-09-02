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
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "FunctionalArea")
@NamedQueries({
    @NamedQuery(
    name = "FunctionalAreaEntity.findAll",
            query = "SELECT f FROM FunctionalAreaEntity f ORDER " +
            "BY f.functionalArea"),

    @NamedQuery(
    name = "FunctionalAreaEntity.findBykey",
    query = "SELECT f FROM FunctionalAreaEntity f WHERE f.functionalAreakey = :key"),

    @NamedQuery(name = "FunctionalAreaEntity.findByFunctionalAreakey", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.functionalAreakey = :functionalAreakey"),
    @NamedQuery(name = "FunctionalAreaEntity.findByFunctionalArea", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.functionalArea = :functionalArea"),
    @NamedQuery(name = "FunctionalAreaEntity.findByDescription", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.description = :description"),
    @NamedQuery(name = "FunctionalAreaEntity.findByCreatedBy", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.createdBy = :createdBy"),
    @NamedQuery(name = "FunctionalAreaEntity.findByDateCreated", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.dateCreated = :dateCreated"),
    @NamedQuery(name = "FunctionalAreaEntity.findByModifiedBy", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "FunctionalAreaEntity.findByDateModified", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.dateModified = :dateModified"),
    @NamedQuery(name = "FunctionalAreaEntity.findByVersion", query = "SELECT f FROM FunctionalAreaEntity f WHERE f.version = :version")})

public class FunctionalAreaEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "_FunctionalArea_key")
    private Integer functionalAreakey;
    @Basic(optional = false)

    @Column(name = "FunctionalArea")
    private String functionalArea;

    @Column(name = "Description")
    private String description;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "functionalAreakey")
    private Collection<WorkgroupUserFunctionalAreaEntity> workgroupUserFunctionalAreaEntityCollection;

    // These functional area strings must match those in the FunctionalArea
    // table exactly in spelling and number.

    public final static String ADMINISTRATION = "Administration";
    public final static String QUERYING    = "Querying";

    public FunctionalAreaEntity() {
    }

    public FunctionalAreaEntity(Integer functionalAreakey) {
        this.functionalAreakey = functionalAreakey;
    }

    public FunctionalAreaEntity(Integer functionalAreakey, String functionalArea, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.functionalAreakey = functionalAreakey;
        this.functionalArea = functionalArea;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getFunctionalAreakey() {
        return functionalAreakey;
    }

    public void setFunctionalAreakey(Integer functionalAreakey) {
        this.functionalAreakey = functionalAreakey;
    }

    public String getFunctionalArea() {
        return functionalArea;
    }

    public void setFunctionalArea(String functionalArea) {
        this.functionalArea = functionalArea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<WorkgroupUserFunctionalAreaEntity> getWorkgroupUserFunctionalAreaEntityCollection() {
        return workgroupUserFunctionalAreaEntityCollection;
    }

    public void setWorkgroupUserFunctionalAreaEntityCollection(Collection<WorkgroupUserFunctionalAreaEntity> workgroupUserFunctionalAreaEntityCollection) {
        this.workgroupUserFunctionalAreaEntityCollection = workgroupUserFunctionalAreaEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (functionalAreakey != null ? functionalAreakey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FunctionalAreaEntity)) {
            return false;
        }
        FunctionalAreaEntity other = (FunctionalAreaEntity) object;
        if ((this.functionalAreakey == null && other.functionalAreakey != null) || (this.functionalAreakey != null && !this.functionalAreakey.equals(other.functionalAreakey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.FunctionalAreaEntity[functionalAreakey=" + functionalAreakey + "]";
    }

    @Override
    public Integer getKey() {
        return functionalAreakey;
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
        System.out.println("\tDatabase key" + "\t" + this.functionalAreakey );
        System.out.println("\tMajor Version" + "\t" + this.getKey());
        System.out.println("\tMinor Version" + "\t" + this.getFunctionalArea() );
        System.out.println("\tBug Fix Version" + "\t" + this.getVersion() );
        System.out.println("\n");
    }
}