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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvLocationTypeEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all location type information  <p>
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
@Table(name = "cv_LocationType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_locationType_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvLocationTypeEntity.findAll",
        query = "SELECT c FROM CvLocationTypeEntity c " +
                "ORDER BY c.locationType"),

    @NamedQuery(
        name = "CvLocationTypeEntity.findBykey",
        query = "SELECT c FROM CvLocationTypeEntity c WHERE c.locationTypekey = :key"),

    @NamedQuery(name = "CvLocationTypeEntity.findByLocationTypekey", query = "SELECT c FROM CvLocationTypeEntity c WHERE c.locationTypekey = :locationTypekey"),
    @NamedQuery(name = "CvLocationTypeEntity.findByLocationType", query = "SELECT c FROM CvLocationTypeEntity c WHERE c.locationType = :locationType"),
    @NamedQuery(name = "CvLocationTypeEntity.findByLocationTypeRef", query = "SELECT c FROM CvLocationTypeEntity c WHERE c.locationTypeRef = :locationTypeRef"),
    @NamedQuery(name = "CvLocationTypeEntity.findByVersion", query = "SELECT c FROM CvLocationTypeEntity c WHERE c.version = :version")})

public class CvLocationTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_locationType_key", nullable = false)
    private Integer locationTypekey;

    @Basic(optional = false)
    @Column(name = "locationType", nullable = false, length = 32)
    private String locationType;

    @Lob
    @Column(name = "locationDetail", length = 2147483647)
    private String locationDetail;

    @Column(name = "locationTypeRef")
    private Integer locationTypeRef;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;
    
    @JoinColumn(name = "_storageFacility_key", referencedColumnName = "_storageFacility_key", nullable = false)
    @ManyToOne(optional = false)
    private CvStorageFacilityEntity storageFacilitykey;

    public CvLocationTypeEntity() {
    }

    public CvLocationTypeEntity(Integer locationTypekey) {
        this.locationTypekey = locationTypekey;
    }

    public CvLocationTypeEntity(Integer locationTypekey, String locationType, int version) {
        this.locationTypekey = locationTypekey;
        this.locationType = locationType;
        this.version = version;
    }

    public Integer getLocationTypekey() {
        return locationTypekey;
    }

    public void setLocationTypekey(Integer locationTypekey) {
        this.locationTypekey = locationTypekey;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public Integer getLocationTypeRef() {
        return locationTypeRef;
    }

    public void setLocationTypeRef(Integer locationTypeRef) {
        this.locationTypeRef = locationTypeRef;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CvStorageFacilityEntity getStorageFacilitykey() {
        return storageFacilitykey;
    }

    public void setStorageFacilitykey(CvStorageFacilityEntity storageFacilitykey) {
        this.storageFacilitykey = storageFacilitykey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationTypekey != null ? locationTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvLocationTypeEntity)) {
            return false;
        }
        CvLocationTypeEntity other = (CvLocationTypeEntity) object;
        if ((this.locationTypekey == null && other.locationTypekey != null) || (this.locationTypekey != null && !this.locationTypekey.equals(other.locationTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvLocationTypeEntity[locationTypekey=" + locationTypekey + "]";
    }

    @Override
    public Integer getKey() {
        return locationTypekey;
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
