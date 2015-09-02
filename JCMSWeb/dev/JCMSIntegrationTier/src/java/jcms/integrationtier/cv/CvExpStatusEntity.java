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
 * <b>File name:</b>  CvExpStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Exp status information  <p>
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
@Table(name = "cv_ExpStatus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"status"})})
@NamedQueries({
    @NamedQuery(
        name = "CvExpStatusEntity.findAll",
        query = "SELECT c FROM CvExpStatusEntity c " +
                "ORDER BY c.status" ),

    @NamedQuery(
        name = "CvExpStatusEntity.findBykey",
        query = "SELECT c FROM CvExpStatusEntity c WHERE c.expStatuskey = :key"),

    @NamedQuery(name = "CvExpStatusEntity.findByExpStatuskey", query = "SELECT c FROM CvExpStatusEntity c WHERE c.expStatuskey = :expStatuskey"),
    @NamedQuery(name = "CvExpStatusEntity.findByStatus", query = "SELECT c FROM CvExpStatusEntity c WHERE c.status = :status"),
    @NamedQuery(name = "CvExpStatusEntity.findByVersion", query = "SELECT c FROM CvExpStatusEntity c WHERE c.version = :version")})

public class CvExpStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_expStatus_key", nullable = false)
    private Integer expStatuskey;

    @Basic(optional = false)
    @Column(name = "status", nullable = false, length = 16)
    private String status;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvExpStatusEntity() {
    }

    public CvExpStatusEntity(Integer expStatuskey) {
        this.expStatuskey = expStatuskey;
    }

    public CvExpStatusEntity(Integer expStatuskey, String status, int version) {
        this.expStatuskey = expStatuskey;
        this.status = status;
        this.version = version;
    }

    public Integer getExpStatuskey() {
        return expStatuskey;
    }

    public void setExpStatuskey(Integer expStatuskey) {
        this.expStatuskey = expStatuskey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (expStatuskey != null ? expStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvExpStatusEntity)) {
            return false;
        }
        CvExpStatusEntity other = (CvExpStatusEntity) object;
        if ((this.expStatuskey == null && other.expStatuskey != null) || (this.expStatuskey != null && !this.expStatuskey.equals(other.expStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvExpStatusEntity[expStatuskey=" + expStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return expStatuskey;
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
