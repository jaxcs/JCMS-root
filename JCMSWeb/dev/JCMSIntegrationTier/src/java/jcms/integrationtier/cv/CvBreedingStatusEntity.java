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
 * <b>File name:</b>  CvBreedingStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains Breeding status information  <p>
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
@Table(name = "cv_BreedingStatus")
@NamedQueries({
    @NamedQuery(
    name = "CvBreedingStatusEntity.findAll",
            query = "SELECT c FROM CvBreedingStatusEntity c " +
                "ORDER BY c.breedingStatus "),

    @NamedQuery(
    name = "CvBreedingStatusEntity.findBykey",
            query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.breedingStatuskey = :key"),

    @NamedQuery(name = "CvBreedingStatusEntity.findByBreedingStatuskey", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.breedingStatuskey = :breedingStatuskey"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByBreedingStatus", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.breedingStatus = :breedingStatus"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByAbbreviation", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.abbreviation = :abbreviation"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByIsActive", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByIsDefault", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.isDefault = :isDefault"),
    @NamedQuery(name = "CvBreedingStatusEntity.findBySortOrder", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.sortOrder = :sortOrder"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByVocabularySourcekey", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.vocabularySourcekey = :vocabularySourcekey"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByElementID", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.elementID = :elementID"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByIsDeprecated", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.isDeprecated = :isDeprecated"),
    @NamedQuery(name = "CvBreedingStatusEntity.findByVersion", query = "SELECT c FROM CvBreedingStatusEntity c WHERE c.version = :version")})

public class CvBreedingStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_breedingStatus_key")
    private Integer breedingStatuskey;

    @Basic(optional = false)
    @Column(name = "breedingStatus")
    private String breedingStatus;

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

    public CvBreedingStatusEntity() {
    }

    public CvBreedingStatusEntity(Integer breedingStatuskey) {
        this.breedingStatuskey = breedingStatuskey;
    }

    public CvBreedingStatusEntity(Integer breedingStatuskey, String breedingStatus, int version) {
        this.breedingStatuskey = breedingStatuskey;
        this.breedingStatus = breedingStatus;
        this.version = version;
    }

    public Integer getBreedingStatuskey() {
        return breedingStatuskey;
    }

    public void setBreedingStatuskey(Integer breedingStatuskey) {
        this.breedingStatuskey = breedingStatuskey;
    }

    public String getBreedingStatus() {
        return breedingStatus;
    }

    public void setBreedingStatus(String breedingStatus) {
        this.breedingStatus = breedingStatus;
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
        hash += (breedingStatuskey != null ? breedingStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvBreedingStatusEntity)) {
            return false;
        }
        CvBreedingStatusEntity other = (CvBreedingStatusEntity) object;
        if ((this.breedingStatuskey == null && other.breedingStatuskey != null) || (this.breedingStatuskey != null && !this.breedingStatuskey.equals(other.breedingStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvBreedingStatusEntity[breedingStatuskey=" + breedingStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return breedingStatuskey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println(this.getClass().getSimpleName() + " Contents");
        System.out.println("================================");
        System.out.println("\tKey" + "\t" + this.breedingStatuskey );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tStatus" + "\t" + this.getBreedingStatus() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );

    }

}
