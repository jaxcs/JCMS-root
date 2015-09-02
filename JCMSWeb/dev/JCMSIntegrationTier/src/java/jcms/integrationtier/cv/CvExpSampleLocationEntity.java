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
 * <b>File name:</b>  CvExpSampleLocationEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all exp sample location information  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 * ****
 * **** NOTE this table does not exist in JCMS ****
 * ****
 */
@Entity
@Table(name = "cv_ExpSampleLocation", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"location"})})
@NamedQueries({
    @NamedQuery(
        name = "CvExpSampleLocationEntity.findAll",
        query = "SELECT c FROM CvExpSampleLocationEntity c " +
                "ORDER BY c.location" ),

    @NamedQuery(
        name = "CvExpSampleLocationEntity.findBykey",
        query = "SELECT c FROM CvExpSampleLocationEntity c WHERE c.expSampleLocationkey = :key"),

    @NamedQuery(name = "CvExpSampleLocationEntity.findByExpSampleLocationkey", query = "SELECT c FROM CvExpSampleLocationEntity c WHERE c.expSampleLocationkey = :expSampleLocationkey"),
    @NamedQuery(name = "CvExpSampleLocationEntity.findByLocation", query = "SELECT c FROM CvExpSampleLocationEntity c WHERE c.location = :location"),
    @NamedQuery(name = "CvExpSampleLocationEntity.findByVersion", query = "SELECT c FROM CvExpSampleLocationEntity c WHERE c.version = :version")})

public class CvExpSampleLocationEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_expSampleLocation_key", nullable = false)
    private Integer expSampleLocationkey;

    @Basic(optional = false)
    @Column(name = "location", nullable = false, length = 16)
    private String location;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvExpSampleLocationEntity() {
    }

    public CvExpSampleLocationEntity(Integer expSampleLocationkey) {
        this.expSampleLocationkey = expSampleLocationkey;
    }

    public CvExpSampleLocationEntity(Integer expSampleLocationkey, String location, int version) {
        this.expSampleLocationkey = expSampleLocationkey;
        this.location = location;
        this.version = version;
    }

    public Integer getExpSampleLocationkey() {
        return expSampleLocationkey;
    }

    public void setExpSampleLocationkey(Integer expSampleLocationkey) {
        this.expSampleLocationkey = expSampleLocationkey;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        hash += (expSampleLocationkey != null ? expSampleLocationkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvExpSampleLocationEntity)) {
            return false;
        }
        CvExpSampleLocationEntity other = (CvExpSampleLocationEntity) object;
        if ((this.expSampleLocationkey == null && other.expSampleLocationkey != null) || (this.expSampleLocationkey != null && !this.expSampleLocationkey.equals(other.expSampleLocationkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvExpSampleLocationEntity[expSampleLocationkey=" + expSampleLocationkey + "]";
    }

    @Override
    public Integer getKey() {
        return expSampleLocationkey;
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
