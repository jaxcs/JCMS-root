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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.MatingEntity;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "cv_CrossStatus")
@NamedQueries({
    @NamedQuery(name = "CvCrossstatusEntity.findAll", 
        query = "SELECT c FROM CvCrossstatusEntity c ORDER BY c.crossStatus"),
    
    @NamedQuery(
        name = "CvCrossstatusEntity.findBykey",
        query = "SELECT c FROM CvCrossstatusEntity c WHERE c.crossStatuskey = :key"),
    
    @NamedQuery(name = "CvCrossstatusEntity.findByCrossStatuskey", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.crossStatuskey = :crossStatuskey"),
    @NamedQuery(name = "CvCrossstatusEntity.findByCrossStatus", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.crossStatus = :crossStatus"),
    @NamedQuery(name = "CvCrossstatusEntity.findByAbbreviation", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.abbreviation = :abbreviation"),
    @NamedQuery(name = "CvCrossstatusEntity.findByIsActive", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvCrossstatusEntity.findByIsDefault", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.isDefault = :isDefault"),
    @NamedQuery(name = "CvCrossstatusEntity.findBySortOrder", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.sortOrder = :sortOrder"),
    @NamedQuery(name = "CvCrossstatusEntity.findByVocabularySourcekey", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.vocabularySourcekey = :vocabularySourcekey"),
    @NamedQuery(name = "CvCrossstatusEntity.findByElementID", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.elementID = :elementID"),
    @NamedQuery(name = "CvCrossstatusEntity.findByIsDeprecated", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.isDeprecated = :isDeprecated"),
    @NamedQuery(name = "CvCrossstatusEntity.findByVersion", query = "SELECT c FROM CvCrossstatusEntity c WHERE c.version = :version")})

public class CvCrossstatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 007651L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "_crossStatus_key")
    private Integer crossStatuskey;
    
    @Basic(optional = false)
    @Column(name = "crossStatus")
    private String crossStatus;
    @Column(name = "abbreviation")
    private String abbreviation;
    @Column(name = "isActive")
    private Short isActive;
    @Column(name = "isDefault")
    private Short isDefault;
    @Column(name = "sortOrder")
    private Integer sortOrder;
    @Column(name = "_vocabularySource_key")
    private Integer vocabularySourcekey;
    @Column(name = "elementID")
    private String elementID;
    @Column(name = "isDeprecated")
    private Short isDeprecated;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crossStatuskey")
    private Collection<MatingEntity> matingEntityCollection;

    public CvCrossstatusEntity() {
    }

    public CvCrossstatusEntity(Integer crossStatuskey) {
        this.crossStatuskey = crossStatuskey;
    }

    public CvCrossstatusEntity(Integer crossStatuskey, String crossStatus, int version) {
        this.crossStatuskey = crossStatuskey;
        this.crossStatus = crossStatus;
        this.version = version;
    }

    public Integer getCrossStatuskey() {
        return crossStatuskey;
    }

    public void setCrossStatuskey(Integer crossStatuskey) {
        this.crossStatuskey = crossStatuskey;
    }

    public String getCrossStatus() {
        return crossStatus;
    }

    public void setCrossStatus(String crossStatus) {
        this.crossStatus = crossStatus;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getVocabularySourcekey() {
        return vocabularySourcekey;
    }

    public void setVocabularySourcekey(Integer vocabularySourcekey) {
        this.vocabularySourcekey = vocabularySourcekey;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public Short getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(Short isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<MatingEntity> getMatingEntityCollection() {
        return matingEntityCollection;
    }

    public void setMatingEntityCollection(Collection<MatingEntity> matingEntityCollection) {
        this.matingEntityCollection = matingEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crossStatuskey != null ? crossStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvCrossstatusEntity)) {
            return false;
        }
        CvCrossstatusEntity other = (CvCrossstatusEntity) object;
        if ((this.crossStatuskey == null && other.crossStatuskey != null) || (this.crossStatuskey != null && !this.crossStatuskey.equals(other.crossStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvCrossstatusEntity[ crossStatuskey=" + crossStatuskey + " ]";
    }
    
    @Override
    public Integer getKey() {
        return crossStatuskey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this;
    }

    @Override
    public void printDetail()
    {

    }    
}