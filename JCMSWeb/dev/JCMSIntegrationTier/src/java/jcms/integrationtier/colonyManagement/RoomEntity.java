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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  RoomEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Room information  <p>
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
@Table(name = "Room")
@NamedQueries({
    @NamedQuery(
    name = "RoomEntity.findAll",
    query = "SELECT r FROM RoomEntity r " +
                "ORDER BY r.roomName "),

    @NamedQuery(
    name = "RoomEntity.findBykey",
    query = "SELECT r FROM RoomEntity r WHERE r.roomKey = :key"),

    @NamedQuery(name = "RoomEntity.findByRoomKey", query = "SELECT r FROM RoomEntity r WHERE r.roomKey = :roomKey"),
    @NamedQuery(name = "RoomEntity.findByRoomName", query = "SELECT r FROM RoomEntity r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "RoomEntity.findByHealthLevelHistorykey", query = "SELECT r FROM RoomEntity r WHERE r.healthLevelHistorykey = :healthLevelHistorykey"),
    @NamedQuery(name = "RoomEntity.findByVersion", query = "SELECT r FROM RoomEntity r WHERE r.version = :version")})

public class RoomEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_room_key")
    private Integer roomKey;

    @Basic(optional = false)
    @Column(name = "roomName")
    private String roomName;

    @Basic(optional = false)
    @Column(name = "_healthLevelHistory_key")
    private int healthLevelHistorykey;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomKey")
    private Collection<ContainerHistoryEntity> containerHistoryEntityCollection;

    public RoomEntity() {
    }

    public RoomEntity(Integer roomKey) {
        this.roomKey = roomKey;
    }

    public RoomEntity(Integer roomKey, String roomName, int healthLevelHistorykey, int version) {
        this.roomKey = roomKey;
        this.roomName = roomName;
        this.healthLevelHistorykey = healthLevelHistorykey;
        this.version = version;
    }

    public Integer getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(Integer roomKey) {
        this.roomKey = roomKey;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getHealthLevelHistorykey() {
        return healthLevelHistorykey;
    }

    public void setHealthLevelHistorykey(int healthLevelHistorykey) {
        this.healthLevelHistorykey = healthLevelHistorykey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<ContainerHistoryEntity> getContainerHistoryEntityCollection() {
        return containerHistoryEntityCollection;
    }

    public void setContainerHistoryEntityCollection(Collection<ContainerHistoryEntity> containerHistoryEntityCollection) {
        this.containerHistoryEntityCollection = containerHistoryEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomKey != null ? roomKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomEntity)) {
            return false;
        }
        RoomEntity other = (RoomEntity) object;
        if ((this.roomKey == null && other.roomKey != null) || (this.roomKey != null && !this.roomKey.equals(other.roomKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.RoomEntity[roomKey=" + roomKey + "]";
    }

    @Override
    public Integer getKey() {
        return roomKey;
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