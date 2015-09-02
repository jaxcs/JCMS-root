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

/**iet", catalog = "jcms_db", schema = "", uniqueConstraints = {
    @UniqueConstrain
 * <b>File name:</b>  CvStrainTypeEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all strain type information  <p>
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
@Table(name = "cv_StrainType")
@NamedQueries({
    @NamedQuery(
        name = "CvStrainTypeEntity.findAll",
        query = "SELECT c FROM CvStrainTypeEntity c " +
                "ORDER BY c.strainType"),

    @NamedQuery(
        name = "CvStrainTypeEntity.findBykey",
        query = "SELECT c FROM CvStrainTypeEntity c WHERE c.strainTypekey = :key"),

    @NamedQuery(name = "CvStrainTypeEntity.findByStrainTypekey", query = "SELECT c FROM CvStrainTypeEntity c WHERE c.strainTypekey = :strainTypekey"),
    @NamedQuery(name = "CvStrainTypeEntity.findByStrainType", query = "SELECT c FROM CvStrainTypeEntity c WHERE c.strainType = :strainType"),
    @NamedQuery(name = "CvStrainTypeEntity.findByVersiion", query = "SELECT c FROM CvStrainTypeEntity c WHERE c.versiion = :versiion")})

public class CvStrainTypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_strainType_key", nullable = false)
    private Integer strainTypekey;

    @Basic(optional = false)
    @Column(name = "strainType", nullable = false, length = 64)
    private String strainType;
    
    @Basic(optional = false)
    @Column(name = "versiion", nullable = false)
    private int versiion;

    public CvStrainTypeEntity() {
    }

    public CvStrainTypeEntity(Integer strainTypekey) {
        this.strainTypekey = strainTypekey;
    }

    public CvStrainTypeEntity(Integer strainTypekey, String strainType, int versiion) {
        this.strainTypekey = strainTypekey;
        this.strainType = strainType;
        this.versiion = versiion;
    }

    public Integer getStrainTypekey() {
        return strainTypekey;
    }

    public void setStrainTypekey(Integer strainTypekey) {
        this.strainTypekey = strainTypekey;
    }

    public String getStrainType() {
        return strainType;
    }

    public void setStrainType(String strainType) {
        this.strainType = strainType;
    }

    public int getVersiion() {
        return versiion;
    }

    public void setVersiion(int versiion) {
        this.versiion = versiion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (strainTypekey != null ? strainTypekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvStrainTypeEntity)) {
            return false;
        }
        CvStrainTypeEntity other = (CvStrainTypeEntity) object;
        if ((this.strainTypekey == null && other.strainTypekey != null) || (this.strainTypekey != null && !this.strainTypekey.equals(other.strainTypekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvStrainTypeEntity[strainTypekey=" + strainTypekey + "]";
    }

    @Override
    public Integer getKey() {
        return strainTypekey;
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