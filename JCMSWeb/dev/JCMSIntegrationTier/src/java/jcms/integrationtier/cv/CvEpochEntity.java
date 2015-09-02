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
 * <b>File name:</b>  CvEpochEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all epoch information  <p>
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
@Table(name = "cv_Epoch", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"epoch"}),
    @UniqueConstraint(columnNames = {"_epoch_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvEpochEntity.findAll",
        query = "SELECT c FROM CvEpochEntity c " +
                "ORDER BY c.epoch" ),

    @NamedQuery(
        name = "CvEpochEntity.findBykey",
        query = "SELECT c FROM CvEpochEntity c WHERE c.epochKey = :key"),

    @NamedQuery(name = "CvEpochEntity.findByEpochKey", query = "SELECT c FROM CvEpochEntity c WHERE c.epochKey = :epochKey"),
    @NamedQuery(name = "CvEpochEntity.findByEpoch", query = "SELECT c FROM CvEpochEntity c WHERE c.epoch = :epoch"),
    @NamedQuery(name = "CvEpochEntity.findByVersion", query = "SELECT c FROM CvEpochEntity c WHERE c.version = :version")})

public class CvEpochEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_epoch_key", nullable = false)
    private Integer epochKey;

    @Basic(optional = false)
    @Column(name = "epoch", nullable = false, length = 32)
    private String epoch;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvEpochEntity() {
    }

    public CvEpochEntity(Integer epochKey) {
        this.epochKey = epochKey;
    }

    public CvEpochEntity(Integer epochKey, String epoch, int version) {
        this.epochKey = epochKey;
        this.epoch = epoch;
        this.version = version;
    }

    public Integer getEpochKey() {
        return epochKey;
    }

    public void setEpochKey(Integer epochKey) {
        this.epochKey = epochKey;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
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
        hash += (epochKey != null ? epochKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvEpochEntity)) {
            return false;
        }
        CvEpochEntity other = (CvEpochEntity) object;
        if ((this.epochKey == null && other.epochKey != null) || (this.epochKey != null && !this.epochKey.equals(other.epochKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvEpochEntity[epochKey=" + epochKey + "]";
    }

    @Override
    public Integer getKey() {
        return epochKey;
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
