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
 * <b>File name:</b>  ContainerEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Pen information like pen number, pen name
 * etc  <p>
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
@Table(name = "Container")
@NamedQueries({
    @NamedQuery(
    name = "ContainerEntity.findAll",
    query = "SELECT c FROM ContainerEntity c " +
                "ORDER BY c.containerID "),

    @NamedQuery(
    name = "ContainerEntity.findAllByOwner",
    query = "SELECT c FROM ContainerEntity c " +
            "WHERE EXISTS( " +
            "SELECT 1 FROM MouseEntity m " +
            "WHERE c.containerKey = m.penKey AND " +
            "m.owner = :fOwner) " +
            "ORDER BY c.containerID "),

    @NamedQuery(
    name = "ContainerEntity.findAllByOwners",
    query = "SELECT c FROM ContainerEntity c " +
            "WHERE EXISTS( " +
            "SELECT 1 FROM MouseEntity m " +
            "WHERE c.containerKey = m.penKey AND " +
            "m.owner IN (:lOwner)) " +
            "ORDER BY c.containerID "),

    @NamedQuery(
    name = "ContainerEntity.findBykey",
    query = "SELECT c FROM ContainerEntity c WHERE c.containerKey = :key"),
    
    @NamedQuery(
        name = "ContainerEntity.findMaxPrimaryKey", 
        query = "SELECT max(c.containerKey) FROM ContainerEntity c " ),
    
    @NamedQuery(
        name = "ContainerEntity.findMaxContainerID", 
        query = "SELECT max(c.containerID) FROM ContainerEntity c " ),

    @NamedQuery(name = "ContainerEntity.findByContainerKey", query = "SELECT c FROM ContainerEntity c WHERE c.containerKey = :containerKey"),
    @NamedQuery(name = "ContainerEntity.findByContainerID", query = "SELECT c FROM ContainerEntity c WHERE c.containerID = :containerID"),
    @NamedQuery(name = "ContainerEntity.findByContainerName", query = "SELECT c FROM ContainerEntity c WHERE c.containerName = :containerName"),
    @NamedQuery(name = "ContainerEntity.findByComment", query = "SELECT c FROM ContainerEntity c WHERE c.comment = :comment"),
    @NamedQuery(name = "ContainerEntity.findByContainerHistorykey", query = "SELECT c FROM ContainerEntity c WHERE c.containerHistorykey = :containerHistorykey"),
    @NamedQuery(name = "ContainerEntity.findByVersion", query = "SELECT c FROM ContainerEntity c WHERE c.version = :version")})

public class ContainerEntity extends ITBaseEntity implements Serializable,
            ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_container_key")
    private Integer containerKey;

    @Basic(optional = false)
    @Column(name = "containerID")
    private int containerID;

    @Column(name = "containerName")
    private String containerName;

    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "_containerHistory_key")
    private int containerHistorykey;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "penKey")
    private Collection<MouseEntity> mouseEntityCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "containerKey")
    private Collection<ContainerHistoryEntity> containerHistoryEntityCollection;

    public ContainerEntity() {
    }

    public ContainerEntity(Integer containerKey) {
        this.containerKey = containerKey;
    }

    public ContainerEntity(Integer containerKey, int containerID,
            int containerHistorykey, int version) {
        this.containerKey = containerKey;
        this.containerID = containerID;
        this.containerHistorykey = containerHistorykey;
        this.version = version;
    }

    public Integer getContainerKey() {
        return containerKey;
    }

    public void setContainerKey(Integer containerKey) {
        this.containerKey = containerKey;
    }

    public int getContainerID() {
        return containerID;
    }

    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getContainerHistorykey() {
        return containerHistorykey;
    }

    public void setContainerHistorykey(int containerHistorykey) {
        this.containerHistorykey = containerHistorykey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<MouseEntity> getMouseEntityCollection() {
        return mouseEntityCollection;
    }

    public void setMouseEntityCollection(Collection<MouseEntity> mouseEntityCollection) {
        this.mouseEntityCollection = mouseEntityCollection;
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
        hash += (containerKey != null ? containerKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContainerEntity)) {
            return false;
        }
        ContainerEntity other = (ContainerEntity) object;
        if ((this.containerKey == null && other.containerKey != null) || (this.containerKey != null && !this.containerKey.equals(other.containerKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.ContainerEntity[containerKey=" + containerKey + "]";
    }

    @Override
    public Integer getKey() {
        return containerKey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println(this.getClass().getSimpleName() + " Contents");
        System.out.println("================================");
        System.out.println("\tPK" + "\t" + this.getContainerKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tPen ID" + "\t" + this.getContainerID() );
        System.out.println("\tPen Name" + "\t" + this.getContainerName() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }
}