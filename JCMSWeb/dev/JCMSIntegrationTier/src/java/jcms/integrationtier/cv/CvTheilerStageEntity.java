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
@Table(name = "cv_TheilerStage", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_theilerStage_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvTheilerStageEntity.findAll",
        query = "SELECT c FROM CvTheilerStageEntity c " +
                "ORDER BY c.theilerStage"),
    @NamedQuery(
        name = "CvTheilerStageEntity.findBykey",
        query = "SELECT c FROM CvTheilerStageEntity c WHERE c.theilerStagekey = :key"),
    @NamedQuery(
        name = "CvTheilerStageEntity.findByTheilerStage",
        query = "SELECT c FROM CvTheilerStageEntity c WHERE c.theilerStage = :theilerStage")
})

public class CvTheilerStageEntity  extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_theilerStage_key", nullable = false)
    private Integer theilerStagekey;

    @Basic(optional = false)
    @Column(name = "TheilerStage", nullable = false, length = 8)
    private String theilerStage;
    
    @Column(name = "abbreviation", nullable = false, length = 4)
    private String abbreviation;
    
    @Column(name = "dpc", nullable = false)
    private Double dpc;

    @Column(name = "startDPC", nullable = false)
    private Double startDPC;

    @Column(name = "endDPC", nullable = false)
    private Double endDPC;

    @Column(name = "description", length = 64)
    private String description;
    
    @Column(name = "isActive", nullable = false)
    private Integer isActive;
    
    @Column(name = "isDefault")
    private Integer isDefault;
    
    @Column(name = "sortOrder")
    private Integer sortOrder;
    
    @Column(name = "_vocabularySource_key")
    private Integer vocabularySourcekey;
    
    @Column(name = "elementID", length = 18)
    private String elementID;
    
    @Column(name = "isDeprecated")
    private Integer isDeprecated;
    
    @Column(name = "version", nullable = false)
    private Integer version;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getTheilerStagekey() != null ? getTheilerStagekey().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvTheilerStageEntity)) {
            return false;
        }
        CvTheilerStageEntity other = (CvTheilerStageEntity) object;
        if ((this.getTheilerStagekey() == null && other.getTheilerStagekey() != null) || (this.getTheilerStagekey() != null && !this.theilerStagekey.equals(other.theilerStagekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTheilerStagekey().toString() ;
    }

    @Override
    public Integer getKey() {
        return getTheilerStagekey();
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
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    
    /**
     * @return the theilerStagekey
     */
    public Integer getTheilerStagekey() {
        return theilerStagekey;
    }

    /**
     * @param theilerStagekey the theilerStagekey to set
     */
    public void setTheilerStagekey(Integer theilerStagekey) {
        this.theilerStagekey = theilerStagekey;
    }

    /**
     * @return the theilerStage
     */
    public String getTheilerStage() {
        return theilerStage;
    }

    /**
     * @param theilerStage the theilerStage to set
     */
    public void setTheilerStage(String theilerStage) {
        this.theilerStage = theilerStage;
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
     * @return the dpc
     */
    public Double getDpc() {
        return dpc;
    }

    /**
     * @param dpc the dpc to set
     */
    public void setDpc(Double dpc) {
        this.dpc = dpc;
    }

    /**
     * @return the startDPC
     */
    public Double getStartDPC() {
        return startDPC;
    }

    /**
     * @param startDPC the startDPC to set
     */
    public void setStartDPC(Double startDPC) {
        this.startDPC = startDPC;
    }

    /**
     * @return the endDPC
     */
    public Double getEndDPC() {
        return endDPC;
    }

    /**
     * @param endDPC the endDPC to set
     */
    public void setEndDPC(Double endDPC) {
        this.endDPC = endDPC;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
