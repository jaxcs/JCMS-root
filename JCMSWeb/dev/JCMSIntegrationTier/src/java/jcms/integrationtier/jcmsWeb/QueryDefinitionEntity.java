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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  QueryDefinition.java  <p>
 * <b>Date developed:</b>  October 2010 <p>
 * <b>Purpose:</b>  Contains all center information  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author: daves $ <p>
 * <b>Last changed date:</b> $Date: 2011-06-17 11:06:26 -0400 (Fri, 17 Jun 2011) $   <p>
 * @author springer
 */
@Entity
@Table(name = "QueryDefinition")
@NamedQueries({

    @NamedQuery(name = "QueryDefinitionEntity.findAll", query = "SELECT q FROM QueryDefinitionEntity q"),
    @NamedQuery(name = "QueryDefinitionEntity.findBykey",  query = "SELECT u FROM QueryDefinitionEntity u WHERE u.queryDefinitionkey = :key"),
    @NamedQuery(name = "QueryDefinitionEntity.findByWorkgroupkey",  query = "SELECT  u FROM QueryDefinitionEntity u WHERE u.workgroupkey IN (:wgKey) ORDER BY u.queryName, u.workgroupkey.workgroupName"),
    @NamedQuery(name = "QueryDefinitionEntity.findMaxPrimaryKey", query = "SELECT max(u.queryDefinitionkey) FROM QueryDefinitionEntity u " ),
    @NamedQuery(name = "QueryDefinitionEntity.findByQueryDefinitionkey", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.queryDefinitionkey = :queryDefinitionkey"),
    @NamedQuery(name = "QueryDefinitionEntity.findByUserkey", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.userkey = :userkey"),
    @NamedQuery(name = "QueryDefinitionEntity.findByQueryName", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.queryName = :queryName"),
    @NamedQuery(name = "QueryDefinitionEntity.findByIsActive", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.isActive = :isActive"),
    @NamedQuery(name = "QueryDefinitionEntity.findByCreatedBy", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.createdBy = :createdBy"),
    @NamedQuery(name = "QueryDefinitionEntity.findByDateCreated", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.dateCreated = :dateCreated"),
    @NamedQuery(name = "QueryDefinitionEntity.findByModifiedBy", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "QueryDefinitionEntity.findByDateModified", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.dateModified = :dateModified"),
    @NamedQuery(name = "QueryDefinitionEntity.findByVersion", query = "SELECT q FROM QueryDefinitionEntity q WHERE q.version = :version")})

public class QueryDefinitionEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_QueryDefinition_key")
    private Integer queryDefinitionkey;
    @Column(name = "_User_key")
    private Integer userkey;
    @Column(name = "QueryName")
    private String queryName;
    @Lob
    @Column(name = "QueryOptions")
    private String queryOptions;
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
    @JoinColumn(name = "_QueryType_key", referencedColumnName = "_QueryType_key")
    @ManyToOne
    private CvQueryTypeEntity queryTypekey;
    @JoinColumn(name = "_Workgroup_key", referencedColumnName = "_Workgroup_key")
    @ManyToOne
    private WorkgroupEntity workgroupkey;


    public QueryDefinitionEntity() {
    }

    public QueryDefinitionEntity(Integer queryDefinitionkey) {
        this.queryDefinitionkey = queryDefinitionkey;
    }

    public QueryDefinitionEntity(Integer queryDefinitionkey, String createdBy, Date dateCreated, String modifiedBy, Date dateModified, int version) {
        this.queryDefinitionkey = queryDefinitionkey;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.version = version;
    }

    public Integer getQueryDefinitionkey() {
        return queryDefinitionkey;
    }

    public void setQueryDefinitionkey(Integer queryDefinitionkey) {
        this.queryDefinitionkey = queryDefinitionkey;
    }

    public Integer getUserkey() {
        return userkey;
    }

    public void setUserkey(Integer userkey) {
        this.userkey = userkey;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryOptions() {
        return queryOptions;
    }

    public void setQueryOptions(String queryOptions) {
        this.queryOptions = queryOptions;
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

    public CvQueryTypeEntity getQueryTypekey() {
        return queryTypekey;
    }

    public void setQueryTypekey(CvQueryTypeEntity queryTypekey) {
        this.queryTypekey = queryTypekey;
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
        hash += (queryDefinitionkey != null ? queryDefinitionkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueryDefinitionEntity)) {
            return false;
        }
        QueryDefinitionEntity other = (QueryDefinitionEntity) object;
        if ((this.queryDefinitionkey == null && other.queryDefinitionkey != null) || (this.queryDefinitionkey != null && !this.queryDefinitionkey.equals(other.queryDefinitionkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.QueryDefinitionEntity[queryDefinitionkey=" + queryDefinitionkey + "]";
    }


    @Override
    public Integer getKey() {
        return queryDefinitionkey;
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
        System.out.println("\tQueryDefinitionEntity Contents");
        System.out.println("\t=====================");
        System.out.println("\tQueryName " + "\t" + this.getQueryName());
        System.out.println("\tQueryDef Key" + "\t" + this.getKey());
        System.out.println("\n");
    }

}
