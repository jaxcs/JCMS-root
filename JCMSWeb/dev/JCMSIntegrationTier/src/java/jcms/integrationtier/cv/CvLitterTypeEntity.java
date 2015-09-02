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
 *
 * @author cnh
 */
@Entity
@Table(name = "cv_LitterType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_litterType_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvLitterTypeEntity.findAll",
        query = "SELECT c FROM CvLitterTypeEntity c " +
                "ORDER BY c.litterType"),

    @NamedQuery(
        name = "CvLitterTypeEntity.findBykey",
        query = "SELECT c FROM CvLitterTypeEntity c WHERE c.litterTypekey = :key"),

    @NamedQuery(name = "CvLitterTypeEntity.findByLitterTypekey", query = "SELECT c FROM CvLitterTypeEntity c WHERE c.litterTypekey = :litterTypekey"),
    @NamedQuery(name = "CvLitterTypeEntity.findByLitterType", query = "SELECT c FROM CvLitterTypeEntity c WHERE c.litterType = :litterType")})

public class CvLitterTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_litterType_key", nullable = false)
    private Integer litterTypekey;

    @Basic(optional = false)
    @Column(name = "litterType", nullable = false, length = 75)
    private String litterType;
    
    @Column(name = "abbreviation", length = 8)
    private String abbreviation;
    
    @Column(name = "isActive")
    private Integer isActive;
    
    @Column(name = "isDefault")
    private Integer isDefault;
    
    @Column(name = "isDeprecated")
    private Integer isDeprecated;
    
    @Column(name = "sortOrder")
    private Integer sortOrder;
    
    @Column(name = "_vocabularySource_key")
    private Integer vocabularySourcekey;
    
    @Column(name = "elementID", length = 18)
    private String elementID;
    
    @Column(name = "version", nullable = false)
    private Integer version;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getLitterTypekey() != null ? getLitterTypekey().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvLitterTypeEntity)) {
            return false;
        }
        CvLitterTypeEntity other = (CvLitterTypeEntity) object;
        if ((this.getLitterTypekey() == null && other.getLitterTypekey() != null) || (this.getLitterTypekey() != null && !this.litterTypekey.equals(other.litterTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getLitterTypekey().toString() ;
    }

    @Override
    public Integer getKey() {
        return getLitterTypekey();
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }

    /**
     * @return the litterTypekey
     */
    public Integer getLitterTypekey() {
        return litterTypekey;
    }

    /**
     * @param litterTypekey the litterTypekey to set
     */
    public void setLitterTypekey(Integer litterTypekey) {
        this.litterTypekey = litterTypekey;
    }

    /**
     * @return the litterType
     */
    public String getLitterType() {
        return litterType;
    }

    /**
     * @param litterType the litterType to set
     */
    public void setLitterType(String litterType) {
        this.litterType = litterType;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * @return the isActive
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the isDefault
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the isDeprecated
     */
    public Integer getIsDeprecated() {
        return isDeprecated;
    }

    /**
     * @param isDeprecated the isDeprecated to set
     */
    public void setIsDeprecated(Integer isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the vocabularySourcekey
     */
    public Integer getVocabularySourcekey() {
        return vocabularySourcekey;
    }

    /**
     * @param vocabularySourcekey the vocabularySourcekey to set
     */
    public void setVocabularySourcekey(Integer vocabularySourcekey) {
        this.vocabularySourcekey = vocabularySourcekey;
    }

    /**
     * @return the elementID
     */
    public String getElementID() {
        return elementID;
    }

    /**
     * @param elementID the elementID to set
     */
    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
