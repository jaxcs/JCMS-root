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
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  ContainerStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all container status information  <p>
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
@Table(name = "cv_ContainerStatus")
@NamedQueries({
    @NamedQuery(
        name = "CvContainerStatusEntity.findAll",
        query = "SELECT c FROM CvContainerStatusEntity c " +
                "ORDER BY c.containerStatus "),

    @NamedQuery(
        name = "CvContainerStatusEntity.findBykey",
        query = "SELECT c FROM CvContainerStatusEntity c WHERE c.containerStatuskey = :key"),

    @NamedQuery(name = "CvContainerStatusEntity.findByContainerStatuskey", query = "SELECT c FROM CvContainerStatusEntity c WHERE c.containerStatuskey = :containerStatuskey"),
    @NamedQuery(name = "CvContainerStatusEntity.findByContainerStatus", query = "SELECT c FROM CvContainerStatusEntity c WHERE c.containerStatus = :containerStatus"),
    @NamedQuery(name = "CvContainerStatusEntity.findByBillable", query = "SELECT c FROM CvContainerStatusEntity c WHERE c.billable = :billable"),
    @NamedQuery(name = "CvContainerStatusEntity.findByVersion", query = "SELECT c FROM CvContainerStatusEntity c WHERE c.version = :version")})

public class CvContainerStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_containerStatus_key", nullable = false)
    private Integer containerStatuskey;

    @Basic(optional = false)
    @Column(name = "containerStatus", nullable = false, length = 8)
    private String containerStatus;

    @Column(name = "billable")
    private Boolean billable;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvContainerStatusEntity() {
    }

    public CvContainerStatusEntity(Integer containerStatuskey) {
        this.containerStatuskey = containerStatuskey;
    }

    public CvContainerStatusEntity(Integer containerStatuskey, String containerStatus, int version) {
        this.containerStatuskey = containerStatuskey;
        this.containerStatus = containerStatus;
        this.version = version;
    }

    public Integer getContainerStatuskey() {
        return containerStatuskey;
    }

    public void setContainerStatuskey(Integer containerStatuskey) {
        this.containerStatuskey = containerStatuskey;
    }

    public String getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
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
        hash += (containerStatuskey != null ? containerStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvContainerStatusEntity)) {
            return false;
        }
        CvContainerStatusEntity other = (CvContainerStatusEntity) object;
        if ((this.containerStatuskey == null && other.containerStatuskey != null) || (this.containerStatuskey != null && !this.containerStatuskey.equals(other.containerStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvContainerStatusEntity[containerStatuskey=" + containerStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return containerStatuskey;
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
