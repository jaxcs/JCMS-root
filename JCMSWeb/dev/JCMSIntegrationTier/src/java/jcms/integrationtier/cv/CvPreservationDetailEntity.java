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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvPreservationDetailEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all preservation detail information  <p>
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
@Table(name = "cv_PreservationDetail")
@NamedQueries({
    @NamedQuery(
        name = "CvPreservationDetailEntity.findAll",
        query = "SELECT c FROM CvPreservationDetailEntity c " +
                "ORDER BY c.preservationDetail"),

    @NamedQuery(
        name = "CvPreservationDetailEntity.findBykey",
        query = "SELECT c FROM CvPreservationDetailEntity c WHERE c.preservationDetailkey = :key"),

    @NamedQuery(name = "CvPreservationDetailEntity.findByPreservationDetailkey", query = "SELECT c FROM CvPreservationDetailEntity c WHERE c.preservationDetailkey = :preservationDetailkey"),
    @NamedQuery(name = "CvPreservationDetailEntity.findByPreservationDetail", query = "SELECT c FROM CvPreservationDetailEntity c WHERE c.preservationDetail = :preservationDetail"),
    @NamedQuery(name = "CvPreservationDetailEntity.findByVersion", query = "SELECT c FROM CvPreservationDetailEntity c WHERE c.version = :version")})

public class CvPreservationDetailEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_preservationDetail_key", nullable = false)
    private Integer preservationDetailkey;

    @Basic(optional = false)
    @Column(name = "preservationDetail", nullable = false, length = 32)
    private String preservationDetail;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;
    
    @JoinColumn(name = "_preservationMethod_key", referencedColumnName = "_preservationMethod_key", nullable = false)
    @ManyToOne(optional = false)
    private CvPreservationMethodEntity preservationMethodkey;

    public CvPreservationDetailEntity() {
    }

    public CvPreservationDetailEntity(Integer preservationDetailkey) {
        this.preservationDetailkey = preservationDetailkey;
    }

    public CvPreservationDetailEntity(Integer preservationDetailkey, String preservationDetail, int version) {
        this.preservationDetailkey = preservationDetailkey;
        this.preservationDetail = preservationDetail;
        this.version = version;
    }

    public Integer getPreservationDetailkey() {
        return preservationDetailkey;
    }

    public void setPreservationDetailkey(Integer preservationDetailkey) {
        this.preservationDetailkey = preservationDetailkey;
    }

    public String getPreservationDetail() {
        return preservationDetail;
    }

    public void setPreservationDetail(String preservationDetail) {
        this.preservationDetail = preservationDetail;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CvPreservationMethodEntity getPreservationMethodkey() {
        return preservationMethodkey;
    }

    public void setPreservationMethodkey(CvPreservationMethodEntity preservationMethodkey) {
        this.preservationMethodkey = preservationMethodkey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preservationDetailkey != null ? preservationDetailkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvPreservationDetailEntity)) {
            return false;
        }
        CvPreservationDetailEntity other = (CvPreservationDetailEntity) object;
        if ((this.preservationDetailkey == null && other.preservationDetailkey != null) || (this.preservationDetailkey != null && !this.preservationDetailkey.equals(other.preservationDetailkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvPreservationDetailEntity[preservationDetailkey=" + preservationDetailkey + "]";
    }

    @Override
    public Integer getKey() {
        return preservationDetailkey;
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
