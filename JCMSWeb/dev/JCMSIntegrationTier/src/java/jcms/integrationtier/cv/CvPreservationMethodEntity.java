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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvPreservationMethodEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all preservation method information  <p>
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
@Table(name = "cv_PreservationMethod")
@NamedQueries({
    @NamedQuery(
        name = "CvPreservationMethodEntity.findAll",
        query = "SELECT c FROM CvPreservationMethodEntity c " +
                "ORDER BY c.preservationMethod"),

    @NamedQuery(
        name = "CvPreservationMethodEntity.findBykey",
        query = "SELECT c FROM CvPreservationMethodEntity c WHERE c.preservationMethodkey = :key"),

    @NamedQuery(name = "CvPreservationMethodEntity.findByPreservationMethodkey", query = "SELECT c FROM CvPreservationMethodEntity c WHERE c.preservationMethodkey = :preservationMethodkey"),
    @NamedQuery(name = "CvPreservationMethodEntity.findByPreservationMethod", query = "SELECT c FROM CvPreservationMethodEntity c WHERE c.preservationMethod = :preservationMethod"),
    @NamedQuery(name = "CvPreservationMethodEntity.findByVersion", query = "SELECT c FROM CvPreservationMethodEntity c WHERE c.version = :version")})

public class CvPreservationMethodEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_preservationMethod_key", nullable = false)
    private Integer preservationMethodkey;

    @Basic(optional = false)
    @Column(name = "preservationMethod", nullable = false, length = 32)
    private String preservationMethod;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    @JoinColumn(name = "_preservationType_key", referencedColumnName = "_preservationType_key", nullable = false)
    @ManyToOne(optional = false)
    private CvPreservationTypeEntity preservationTypekey;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preservationMethodkey")
    private Collection<CvPreservationDetailEntity> cvPreservationDetailEntityCollection;

    public CvPreservationMethodEntity() {
    }

    public CvPreservationMethodEntity(Integer preservationMethodkey) {
        this.preservationMethodkey = preservationMethodkey;
    }

    public CvPreservationMethodEntity(Integer preservationMethodkey, String preservationMethod, int version) {
        this.preservationMethodkey = preservationMethodkey;
        this.preservationMethod = preservationMethod;
        this.version = version;
    }

    public Integer getPreservationMethodkey() {
        return preservationMethodkey;
    }

    public void setPreservationMethodkey(Integer preservationMethodkey) {
        this.preservationMethodkey = preservationMethodkey;
    }

    public String getPreservationMethod() {
        return preservationMethod;
    }

    public void setPreservationMethod(String preservationMethod) {
        this.preservationMethod = preservationMethod;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CvPreservationTypeEntity getPreservationTypekey() {
        return preservationTypekey;
    }

    public void setPreservationTypekey(CvPreservationTypeEntity preservationTypekey) {
        this.preservationTypekey = preservationTypekey;
    }

    public Collection<CvPreservationDetailEntity> getCvPreservationDetailEntityCollection() {
        return cvPreservationDetailEntityCollection;
    }

    public void setCvPreservationDetailEntityCollection(Collection<CvPreservationDetailEntity> cvPreservationDetailEntityCollection) {
        this.cvPreservationDetailEntityCollection = cvPreservationDetailEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preservationMethodkey != null ? preservationMethodkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvPreservationMethodEntity)) {
            return false;
        }
        CvPreservationMethodEntity other = (CvPreservationMethodEntity) object;
        if ((this.preservationMethodkey == null && other.preservationMethodkey != null) || (this.preservationMethodkey != null && !this.preservationMethodkey.equals(other.preservationMethodkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvPreservationMethodEntity[preservationMethodkey=" + preservationMethodkey + "]";
    }

    @Override
    public Integer getKey() {
        return preservationMethodkey;
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
