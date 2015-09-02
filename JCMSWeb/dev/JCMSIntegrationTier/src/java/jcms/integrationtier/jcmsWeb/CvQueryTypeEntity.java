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
 * @author springer
 */
@Entity
@Table(name = "cv_QueryType")
@NamedQueries({
    @NamedQuery(name = "CvQueryTypeEntity.findAll", query = "SELECT c FROM CvQueryTypeEntity c"),
    @NamedQuery(name = "CvQueryTypeEntity.findBykey", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.queryTypekey = :key"),
    @NamedQuery(name = "CvQueryTypeEntity.findByQueryTypekey", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.queryTypekey = :queryTypekey"),
    @NamedQuery(name = "CvQueryTypeEntity.findByQueryType", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.queryType = :queryType"),
    @NamedQuery(name = "CvQueryTypeEntity.findByIsActive", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvQueryTypeEntity.findBySortOrder", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.sortOrder = :sortOrder"),
    @NamedQuery(name = "CvQueryTypeEntity.findByIsDefault", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.isDefault = :isDefault"),
    @NamedQuery(name = "CvQueryTypeEntity.findByCreatedBy", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "CvQueryTypeEntity.findByDateCreated", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CvQueryTypeEntity.findByModifiedBy", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CvQueryTypeEntity.findByDateModified", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.dateModified = :dateModified"),
    @NamedQuery(name = "CvQueryTypeEntity.findByIsDeprecated", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.isDeprecated = :isDeprecated"),
    @NamedQuery(name = "CvQueryTypeEntity.findByElementID", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.elementID = :elementID"),
    @NamedQuery(name = "CvQueryTypeEntity.findByVersion", query = "SELECT c FROM CvQueryTypeEntity c WHERE c.version = :version")})

public class CvQueryTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_QueryType_key")
    private Integer queryTypekey;
    @Column(name = "QueryType")
    private String queryType;
    @Column(name = "IsActive")
    private Short isActive;
    @Column(name = "SortOrder")
    private Integer sortOrder;
    @Column(name = "IsDefault")
    private Short isDefault;
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
    @Column(name = "IsDeprecated")
    private Short isDeprecated;
    @Column(name = "ElementID")
    private String elementID;
    @Basic(optional = false)
    @Column(name = "Version")
    private int version;
    @OneToMany(mappedBy = "queryTypekey")
    private Collection<QueryDefinitionEntity> queryDefinitionEntityCollection;

    @JoinColumn(name = "_Workgroup_key", referencedColumnName = "_Workgroup_key")
    @ManyToOne
    private WorkgroupEntity workgroupkey;



    public CvQueryTypeEntity() {
    }

    public CvQueryTypeEntity(Integer queryTypekey) {
        this.queryTypekey = queryTypekey;
    }

    public CvQueryTypeEntity(Integer queryTypekey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.queryTypekey = queryTypekey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getQueryTypekey() {
        return queryTypekey;
    }

    public void setQueryTypekey(Integer queryTypekey) {
        this.queryTypekey = queryTypekey;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
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

    public Short getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(Short isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<QueryDefinitionEntity> getQueryDefinitionEntityCollection() {
        return queryDefinitionEntityCollection;
    }

    public void setQueryDefinitionEntityCollection(Collection<QueryDefinitionEntity> queryDefinitionEntityCollection) {
        this.queryDefinitionEntityCollection = queryDefinitionEntityCollection;
    }

     public WorkgroupEntity getWorkgroupkey() {
        return workgroupkey;
    }

    public void setWorkgroupkey(WorkgroupEntity workgroupkey) {
        this.workgroupkey = workgroupkey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (queryTypekey != null ? queryTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvQueryTypeEntity)) {
            return false;
        }
        CvQueryTypeEntity other = (CvQueryTypeEntity) object;
        if ((this.queryTypekey == null && other.queryTypekey != null) || (this.queryTypekey != null && !this.queryTypekey.equals(other.queryTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.CvQueryTypeEntity[queryTypekey=" + queryTypekey + "]";
    }

    public Integer getKey() {
        return this.queryTypekey;
    }

    public ITBaseEntityInterface getEntity() {
        return this;
    }

    public void printDetail() {
        System.out.println("\n");
        System.out.println("\tCvQueryTypeEntity Contents");
        System.out.println("\t=====================");
        System.out.println("\tQueryType " + "\t" + this.queryType );
        System.out.println("\tQueryType  key" + "\t" + Integer.toString(this.getQueryTypekey()));
        System.out.println("\n");

    }

}
