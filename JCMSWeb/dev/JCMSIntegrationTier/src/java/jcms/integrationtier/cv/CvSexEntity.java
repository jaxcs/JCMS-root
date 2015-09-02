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
 * <b>File name:</b>  CvSexEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all sex of a mouse information  <p>
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
@Table(name = "cv_Sex")
@NamedQueries({
    @NamedQuery(
    name = "CvSexEntity.findAll",
            query = "SELECT c FROM CvSexEntity c " +
                "ORDER BY c.sex "),

    @NamedQuery(
    name = "CvSexEntity.findBykey",
            query = "SELECT c FROM CvSexEntity c WHERE c.sexKey = :key"),

    @NamedQuery(name = "CvSexEntity.findBySexKey", query = "SELECT c FROM CvSexEntity c WHERE c.sexKey = :sexKey"),
    @NamedQuery(name = "CvSexEntity.findBySex", query = "SELECT c FROM CvSexEntity c WHERE c.sex = :sex"),
    @NamedQuery(name = "CvSexEntity.findByAbbreviation", query = "SELECT c FROM CvSexEntity c WHERE c.abbreviation = :abbreviation"),
    @NamedQuery(name = "CvSexEntity.findByIsActive", query = "SELECT c FROM CvSexEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvSexEntity.findByIsDefault", query = "SELECT c FROM CvSexEntity c WHERE c.isDefault = :isDefault"),
    @NamedQuery(name = "CvSexEntity.findBySortOrder", query = "SELECT c FROM CvSexEntity c WHERE c.sortOrder = :sortOrder"),
    @NamedQuery(name = "CvSexEntity.findByVocabularySourcekey", query = "SELECT c FROM CvSexEntity c WHERE c.vocabularySourcekey = :vocabularySourcekey"),
    @NamedQuery(name = "CvSexEntity.findByElementID", query = "SELECT c FROM CvSexEntity c WHERE c.elementID = :elementID"),
    @NamedQuery(name = "CvSexEntity.findByIsDeprecated", query = "SELECT c FROM CvSexEntity c WHERE c.isDeprecated = :isDeprecated"),
    @NamedQuery(name = "CvSexEntity.findByVersion", query = "SELECT c FROM CvSexEntity c WHERE c.version = :version")})

public class CvSexEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_sex_key")
    private Integer sexKey;

    @Basic(optional = false)
    @Column(name = "sex")
    private String sex;

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

    public CvSexEntity() {
    }

    public CvSexEntity(Integer sexKey) {
        this.sexKey = sexKey;
    }

    public CvSexEntity(Integer sexKey, String sex, int version) {
        this.sexKey = sexKey;
        this.sex = sex;
        this.version = version;
    }

    public Integer getSexKey() {
        return sexKey;
    }

    public void setSexKey(Integer sexKey) {
        this.sexKey = sexKey;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
        hash += (sexKey != null ? sexKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvSexEntity)) {
            return false;
        }
        CvSexEntity other = (CvSexEntity) object;
        if ((this.sexKey == null && other.sexKey != null) || (this.sexKey != null && !this.sexKey.equals(other.sexKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvSexEntity[sexKey=" + sexKey + "]";
    }

    @Override
    public Integer getKey() {
        return sexKey;
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