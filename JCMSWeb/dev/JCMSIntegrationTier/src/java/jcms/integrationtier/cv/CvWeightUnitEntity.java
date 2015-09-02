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
 * <b>File name:</b>  CvWeightUnitEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all weight unit information  <p>
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
@Table(name = "cv_WeightUnit", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"weightUnit"}),
    @UniqueConstraint(columnNames = {"_weightUnit_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvWeightUnitEntity.findAll",
        query = "SELECT c FROM CvWeightUnitEntity c " +
                "ORDER BY c.weightUnit"),

    @NamedQuery(
        name = "CvWeightUnitEntity.findBykey",
        query = "SELECT c FROM CvWeightUnitEntity c WHERE c.weightUnitkey = :key"),

    @NamedQuery(name = "CvWeightUnitEntity.findByWeightUnitkey", query = "SELECT c FROM CvWeightUnitEntity c WHERE c.weightUnitkey = :weightUnitkey"),
    @NamedQuery(name = "CvWeightUnitEntity.findByWeightUnit", query = "SELECT c FROM CvWeightUnitEntity c WHERE c.weightUnit = :weightUnit"),
    @NamedQuery(name = "CvWeightUnitEntity.findByVersion", query = "SELECT c FROM CvWeightUnitEntity c WHERE c.version = :version")})

public class CvWeightUnitEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_weightUnit_key", nullable = false)
    private Integer weightUnitkey;

    @Basic(optional = false)
    @Column(name = "weightUnit", nullable = false, length = 32)
    private String weightUnit;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvWeightUnitEntity() {
    }

    public CvWeightUnitEntity(Integer weightUnitkey) {
        this.weightUnitkey = weightUnitkey;
    }

    public CvWeightUnitEntity(Integer weightUnitkey, String weightUnit, int version) {
        this.weightUnitkey = weightUnitkey;
        this.weightUnit = weightUnit;
        this.version = version;
    }

    public Integer getWeightUnitkey() {
        return weightUnitkey;
    }

    public void setWeightUnitkey(Integer weightUnitkey) {
        this.weightUnitkey = weightUnitkey;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
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
        hash += (weightUnitkey != null ? weightUnitkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvWeightUnitEntity)) {
            return false;
        }
        CvWeightUnitEntity other = (CvWeightUnitEntity) object;
        if ((this.weightUnitkey == null && other.weightUnitkey != null) || (this.weightUnitkey != null && !this.weightUnitkey.equals(other.weightUnitkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvWeightUnitEntity[weightUnitkey=" + weightUnitkey + "]";
    }

    @Override
    public Integer getKey() {
        return weightUnitkey;
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
