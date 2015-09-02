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
import javax.persistence.UniqueConstraint;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvHarvestMethodEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all harvest method information  <p>
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
@Table(name = "cv_HarvestMethod", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"harvestMethod"})})
@NamedQueries({
    @NamedQuery(
        name = "CvHarvestMethodEntity.findAll",
        query = "SELECT c FROM CvHarvestMethodEntity c " +
                "ORDER BY c.harvestMethod" ),

    @NamedQuery(
        name = "CvHarvestMethodEntity.findBykey",
        query = "SELECT c FROM CvHarvestMethodEntity c WHERE c.harvestMethodkey = :key"),

    @NamedQuery(name = "CvHarvestMethodEntity.findByHarvestMethodkey", query = "SELECT c FROM CvHarvestMethodEntity c WHERE c.harvestMethodkey = :harvestMethodkey"),
    @NamedQuery(name = "CvHarvestMethodEntity.findByHarvestMethod", query = "SELECT c FROM CvHarvestMethodEntity c WHERE c.harvestMethod = :harvestMethod"),
    @NamedQuery(name = "CvHarvestMethodEntity.findByVersion", query = "SELECT c FROM CvHarvestMethodEntity c WHERE c.version = :version")})

public class CvHarvestMethodEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_harvestMethod_key", nullable = false)
    private Integer harvestMethodkey;

    @Basic(optional = false)
    @Column(name = "harvestMethod", nullable = false, length = 32)
    private String harvestMethod;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvHarvestMethodEntity() {
    }

    public CvHarvestMethodEntity(Integer harvestMethodkey) {
        this.harvestMethodkey = harvestMethodkey;
    }

    public CvHarvestMethodEntity(Integer harvestMethodkey, String harvestMethod, int version) {
        this.harvestMethodkey = harvestMethodkey;
        this.harvestMethod = harvestMethod;
        this.version = version;
    }

    public Integer getHarvestMethodkey() {
        return harvestMethodkey;
    }

    public void setHarvestMethodkey(Integer harvestMethodkey) {
        this.harvestMethodkey = harvestMethodkey;
    }

    public String getHarvestMethod() {
        return harvestMethod;
    }

    public void setHarvestMethod(String harvestMethod) {
        this.harvestMethod = harvestMethod;
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
        hash += (harvestMethodkey != null ? harvestMethodkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvHarvestMethodEntity)) {
            return false;
        }
        CvHarvestMethodEntity other = (CvHarvestMethodEntity) object;
        if ((this.harvestMethodkey == null && other.harvestMethodkey != null) || (this.harvestMethodkey != null && !this.harvestMethodkey.equals(other.harvestMethodkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvHarvestMethodEntity[harvestMethodkey=" + harvestMethodkey + "]";
    }

    @Override
    public Integer getKey() {
        return harvestMethodkey;
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
