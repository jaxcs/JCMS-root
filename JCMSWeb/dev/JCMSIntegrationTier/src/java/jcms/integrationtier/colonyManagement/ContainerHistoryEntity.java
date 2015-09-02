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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.cv.CvContainerStatusEntity;

/**
 * <b>File name:</b>  ContainerHistoryEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Pen History information like pen number,
 * pen name etc  <p>
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
@Table(name = "ContainerHistory")
@NamedQueries({
    @NamedQuery(
    name = "ContainerHistoryEntity.findAll",
    query = "SELECT c FROM ContainerHistoryEntity c " +
                "ORDER BY c.actionDate "),

    @NamedQuery(
    name = "ContainerHistoryEntity.findBykey",
    query = "SELECT c FROM ContainerHistoryEntity c WHERE c.containerHistorykey = :key"),
    
    @NamedQuery(
        name = "ContainerHistoryEntity.findMaxPrimaryKey", 
        query = "SELECT max(c.containerHistorykey) FROM ContainerHistoryEntity c " ),

    @NamedQuery(name = "ContainerHistoryEntity.findByContainerHistorykey", query = "SELECT c FROM ContainerHistoryEntity c WHERE c.containerHistorykey = :containerHistorykey"),
    @NamedQuery(name = "ContainerHistoryEntity.findByActionDate", query = "SELECT c FROM ContainerHistoryEntity c WHERE c.actionDate = :actionDate"),
    @NamedQuery(name = "ContainerHistoryEntity.findByVersion", query = "SELECT c FROM ContainerHistoryEntity c WHERE c.version = :version")})

public class ContainerHistoryEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_containerHistory_key")
    private Integer containerHistorykey;

    @Column(name = "actionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @JoinColumn(name = "_container_key", referencedColumnName = "_container_key")
    @ManyToOne(optional = false)
    private ContainerEntity containerKey;

    @JoinColumn(name = "_containerStatus_key", referencedColumnName = "_containerStatus_key")
    @ManyToOne(optional = false)
    private CvContainerStatusEntity containerStatuskey;

    @JoinColumn(name = "_room_key", referencedColumnName = "_room_key")
    @ManyToOne(optional = false)
    private RoomEntity roomKey;

    public ContainerHistoryEntity() {
    }

    public ContainerHistoryEntity(Integer containerHistorykey) {
        this.containerHistorykey = containerHistorykey;
    }

    public ContainerHistoryEntity(Integer containerHistorykey, Date actionDate, int version) {
        this.containerHistorykey = containerHistorykey;
        this.actionDate = actionDate;
        this.version = version;
    }

    public Integer getContainerHistorykey() {
        return containerHistorykey;
    }

    public void setContainerHistorykey(Integer containerHistorykey) {
        this.containerHistorykey = containerHistorykey;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ContainerEntity getContainerKey() {
        return containerKey;
    }

    public void setContainerKey(ContainerEntity containerKey) {
        this.containerKey = containerKey;
    }

    public CvContainerStatusEntity getContainerStatuskey() {
        return containerStatuskey;
    }

    public void setContainerStatuskey(CvContainerStatusEntity containerStatuskey) {
        this.containerStatuskey = containerStatuskey;
    }

    public RoomEntity getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(RoomEntity roomKey) {
        this.roomKey = roomKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (containerHistorykey != null ? containerHistorykey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContainerHistoryEntity)) {
            return false;
        }
        ContainerHistoryEntity other = (ContainerHistoryEntity) object;
        if ((this.containerHistorykey == null && other.containerHistorykey != null) || (this.containerHistorykey != null && !this.containerHistorykey.equals(other.containerHistorykey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.ContainerHistoryEntity[containerHistorykey=" + containerHistorykey + "]";
    }

    @Override
    public Integer getKey() {
        return containerHistorykey;
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