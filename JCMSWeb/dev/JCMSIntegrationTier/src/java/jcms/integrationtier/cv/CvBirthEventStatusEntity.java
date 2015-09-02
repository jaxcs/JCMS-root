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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvBirthEventStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all birth information  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Entity
@Table(name = "cv_BirthEventStatus")
@NamedQueries({
    @NamedQuery(
    name = "CvBirthEventStatusEntity.findAll",
            query = "SELECT c FROM CvBirthEventStatusEntity c " +
                "ORDER BY c.birthEventStatus "),

    @NamedQuery(
    name = "CvBirthEventStatusEntity.findBykey",
            query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.birthEventStatuskey = :key"),

    @NamedQuery(name = "CvBirthEventStatusEntity.findByBirthEventStatuskey", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.birthEventStatuskey = :birthEventStatuskey"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByBirthEventStatus", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.birthEventStatus = :birthEventStatus"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByAbbreviation", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.abbreviation = :abbreviation"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByIsActive", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByIsDefault", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.isDefault = :isDefault"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findBySortOrder", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.sortOrder = :sortOrder"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByVocabularySourcekey", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.vocabularySourcekey = :vocabularySourcekey"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByElementID", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.elementID = :elementID"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByIsDeprecated", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.isDeprecated = :isDeprecated"),
    @NamedQuery(name = "CvBirthEventStatusEntity.findByVersion", query = "SELECT c FROM CvBirthEventStatusEntity c WHERE c.version = :version")})


public class CvBirthEventStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
   
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_birthEventStatus_key")
    private Integer birthEventStatuskey;

    @Basic(optional = false)
    @Column(name = "birthEventStatus")
    private String birthEventStatus;

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

    public CvBirthEventStatusEntity() {
    }

    public CvBirthEventStatusEntity(Integer birthEventStatuskey) {
        this.birthEventStatuskey = birthEventStatuskey;
    }

    public CvBirthEventStatusEntity(Integer birthEventStatuskey, String birthEventStatus, int version) {
        this.birthEventStatuskey = birthEventStatuskey;
        this.birthEventStatus = birthEventStatus;
        this.version = version;
    }

    public Integer getBirthEventStatuskey() {
        return birthEventStatuskey;
    }

    public void setBirthEventStatuskey(Integer birthEventStatuskey) {
        this.birthEventStatuskey = birthEventStatuskey;
    }

    public String getBirthEventStatus() {
        return birthEventStatus;
    }

    public void setBirthEventStatus(String birthEventStatus) {
        this.birthEventStatus = birthEventStatus;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (birthEventStatuskey != null ? birthEventStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvBirthEventStatusEntity)) {
            return false;
        }
        CvBirthEventStatusEntity other = (CvBirthEventStatusEntity) object;
        if ((this.birthEventStatuskey == null && other.birthEventStatuskey != null) || (this.birthEventStatuskey != null && !this.birthEventStatuskey.equals(other.birthEventStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvBirthEventStatusEntity[birthEventStatuskey=" + birthEventStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return birthEventStatuskey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }

}
