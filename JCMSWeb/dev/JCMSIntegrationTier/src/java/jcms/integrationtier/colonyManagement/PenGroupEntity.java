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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  PenGroupEntity.java  <p>
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
 * *****
 * ***** This table has been deprecated. Use the container table instead *******
 * *****
 */
@Entity
@Table(name = "PenGroup")
@NamedQueries({
    @NamedQuery(
    name = "PenGroupEntity.findAll",
            query = "SELECT p FROM PenGroupEntity p"),

    @NamedQuery(
    name = "PenGroupEntity.findBykey",
            query = "SELECT p FROM PenGroupEntity p WHERE p.penKey = :key"),

    @NamedQuery(name = "PenGroupEntity.findByPenKey", query = "SELECT p FROM PenGroupEntity p WHERE p.penKey = :penKey"),
    @NamedQuery(name = "PenGroupEntity.findByPenID", query = "SELECT p FROM PenGroupEntity p WHERE p.penID = :penID"),
    @NamedQuery(name = "PenGroupEntity.findByRoom", query = "SELECT p FROM PenGroupEntity p WHERE p.room = :room"),
    @NamedQuery(name = "PenGroupEntity.findByPenStatus", query = "SELECT p FROM PenGroupEntity p WHERE p.penStatus = :penStatus"),
    @NamedQuery(name = "PenGroupEntity.findByBeginDate", query = "SELECT p FROM PenGroupEntity p WHERE p.beginDate = :beginDate"),
    @NamedQuery(name = "PenGroupEntity.findByHealthLevel", query = "SELECT p FROM PenGroupEntity p WHERE p.healthLevel = :healthLevel"),
    @NamedQuery(name = "PenGroupEntity.findByVersion", query = "SELECT p FROM PenGroupEntity p WHERE p.version = :version")})

public class PenGroupEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_pen_key")
    private Integer penKey;

    @Basic(optional = false)
    @Column(name = "penID")
    private int penID;

    @Column(name = "room")
    private String room;

    @Basic(optional = false)
    @Column(name = "penStatus")
    private boolean penStatus;

    @Basic(optional = false)
    @Column(name = "beginDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Basic(optional = false)
    @Column(name = "healthLevel")
    private short healthLevel;
    
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public PenGroupEntity() {
    }

    public PenGroupEntity(Integer penKey) {
        this.penKey = penKey;
    }

    public PenGroupEntity(Integer penKey, int penID, boolean penStatus, Date beginDate, short healthLevel, int version) {
        this.penKey = penKey;
        this.penID = penID;
        this.penStatus = penStatus;
        this.beginDate = beginDate;
        this.healthLevel = healthLevel;
        this.version = version;
    }

    public Integer getPenKey() {
        return penKey;
    }

    public void setPenKey(Integer penKey) {
        this.penKey = penKey;
    }

    public int getPenID() {
        return penID;
    }

    public void setPenID(int penID) {
        this.penID = penID;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean getPenStatus() {
        return penStatus;
    }

    public void setPenStatus(boolean penStatus) {
        this.penStatus = penStatus;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public short getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(short healthLevel) {
        this.healthLevel = healthLevel;
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
        hash += (penKey != null ? penKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PenGroupEntity)) {
            return false;
        }
        PenGroupEntity other = (PenGroupEntity) object;
        if ((this.penKey == null && other.penKey != null) || (this.penKey != null && !this.penKey.equals(other.penKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.PenGroupEntity[penKey=" + penKey + "]";
    }

    @Override
    public Integer getKey() {
        return penKey;
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
