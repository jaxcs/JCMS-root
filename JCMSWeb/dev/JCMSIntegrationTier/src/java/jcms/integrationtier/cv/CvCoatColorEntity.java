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
 * <b>File name:</b>  CvCoatColorEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all coat color information  <p>
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
@Table(name = "cv_CoatColor", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"coatColor"})})
@NamedQueries({
    @NamedQuery(
        name = "CvCoatColorEntity.findAll",
        query = "SELECT c FROM CvCoatColorEntity c " +
                "ORDER BY c.coatColor "),

    @NamedQuery(
        name = "CvCoatColorEntity.findBykey",
        query = "SELECT c FROM CvCoatColorEntity c WHERE c.coatColorkey = :key"),

    @NamedQuery(name = "CvCoatColorEntity.findByCoatColorkey", query = "SELECT c FROM CvCoatColorEntity c WHERE c.coatColorkey = :coatColorkey"),
    @NamedQuery(name = "CvCoatColorEntity.findByCoatColor", query = "SELECT c FROM CvCoatColorEntity c WHERE c.coatColor = :coatColor"),
    @NamedQuery(name = "CvCoatColorEntity.findByDescription", query = "SELECT c FROM CvCoatColorEntity c WHERE c.description = :description"),
    @NamedQuery(name = "CvCoatColorEntity.findByVersion", query = "SELECT c FROM CvCoatColorEntity c WHERE c.version = :version")})

public class CvCoatColorEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_coatColor_key", nullable = false)
    private Integer coatColorkey;

    @Basic(optional = false)
    @Column(name = "coatColor", nullable = false, length = 8)
    private String coatColor;

    @Column(name = "description", length = 64)
    private String description;
    @Basic(optional = false)
    
    @Column(name = "version", nullable = false)
    private int version;

    public CvCoatColorEntity() {
    }

    public CvCoatColorEntity(Integer coatColorkey) {
        this.coatColorkey = coatColorkey;
    }

    public CvCoatColorEntity(Integer coatColorkey, String coatColor, int version) {
        this.coatColorkey = coatColorkey;
        this.coatColor = coatColor;
        this.version = version;
    }

    public Integer getCoatColorkey() {
        return coatColorkey;
    }

    public void setCoatColorkey(Integer coatColorkey) {
        this.coatColorkey = coatColorkey;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (coatColorkey != null ? coatColorkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvCoatColorEntity)) {
            return false;
        }
        CvCoatColorEntity other = (CvCoatColorEntity) object;
        if ((this.coatColorkey == null && other.coatColorkey != null) || (this.coatColorkey != null && !this.coatColorkey.equals(other.coatColorkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvCoatColorEntity[coatColorkey=" + coatColorkey + "]";
    }

    @Override
    public Integer getKey() {
        return coatColorkey;
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
