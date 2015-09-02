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

package jcms.integrationtier.colonyManagement;

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

/**
 * <b>File name:</b>  LifeStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all life status information of a mouse  <p>
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
@Table(name = "LifeStatus")
@NamedQueries({
    @NamedQuery(
    name = "LifeStatusEntity.findAll",
            query = "SELECT l FROM LifeStatusEntity l " +
                "ORDER BY l.lifeStatus "),

    @NamedQuery(
    name = "LifeStatusEntity.findBykey",
            query = "SELECT l FROM LifeStatusEntity l WHERE l.lifeStatuskey = :key"),

    @NamedQuery(name = "LifeStatusEntity.findByLifeStatuskey", query = "SELECT l FROM LifeStatusEntity l WHERE l.lifeStatuskey = :lifeStatuskey"),
    @NamedQuery(name = "LifeStatusEntity.findByLifeStatus", query = "SELECT l FROM LifeStatusEntity l WHERE l.lifeStatus = :lifeStatus"),
    @NamedQuery(name = "LifeStatusEntity.findByDescription", query = "SELECT l FROM LifeStatusEntity l WHERE l.description = :description"),
    @NamedQuery(name = "LifeStatusEntity.findByExitStatus", query = "SELECT l FROM LifeStatusEntity l WHERE l.exitStatus = :exitStatus"),
    @NamedQuery(name = "LifeStatusEntity.findByVersion", query = "SELECT l FROM LifeStatusEntity l WHERE l.version = :version")})

public class LifeStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_lifeStatus_key")
    private Integer lifeStatuskey;

    @Basic(optional = false)
    @Column(name = "lifeStatus")
    private String lifeStatus;

    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "exitStatus")
    private boolean exitStatus;
    
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public LifeStatusEntity() {
    }

    public LifeStatusEntity(Integer lifeStatuskey) {
        this.lifeStatuskey = lifeStatuskey;
    }

    public LifeStatusEntity(Integer lifeStatuskey, String lifeStatus, boolean exitStatus, int version) {
        this.lifeStatuskey = lifeStatuskey;
        this.lifeStatus = lifeStatus;
        this.exitStatus = exitStatus;
        this.version = version;
    }

    public Integer getLifeStatuskey() {
        return lifeStatuskey;
    }

    public void setLifeStatuskey(Integer lifeStatuskey) {
        this.lifeStatuskey = lifeStatuskey;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(boolean exitStatus) {
        this.exitStatus = exitStatus;
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
        hash += (lifeStatuskey != null ? lifeStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LifeStatusEntity)) {
            return false;
        }
        LifeStatusEntity other = (LifeStatusEntity) object;
        if ((this.lifeStatuskey == null && other.lifeStatuskey != null) || (this.lifeStatuskey != null && !this.lifeStatuskey.equals(other.lifeStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.LifeStatusEntity[lifeStatuskey=" + lifeStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return lifeStatuskey;
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
