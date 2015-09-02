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
 * <b>File name:</b>  CvHealthLevelEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all health level information  <p>
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
@Table(name = "cv_HealthLevel", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"healthLevel"})})
@NamedQueries({
    @NamedQuery(
        name = "CvHealthLevelEntity.findAll",
        query = "SELECT c FROM CvHealthLevelEntity c " +
                "ORDER BY c.healthLevel" ),

    @NamedQuery(
        name = "CvHealthLevelEntity.findBykey",
        query = "SELECT c FROM CvHealthLevelEntity c WHERE c.healthLevelkey = :key"),

    @NamedQuery(name = "CvHealthLevelEntity.findByHealthLevelkey", query = "SELECT c FROM CvHealthLevelEntity c WHERE c.healthLevelkey = :healthLevelkey"),
    @NamedQuery(name = "CvHealthLevelEntity.findByHealthLevel", query = "SELECT c FROM CvHealthLevelEntity c WHERE c.healthLevel = :healthLevel"),
    @NamedQuery(name = "CvHealthLevelEntity.findByDescription", query = "SELECT c FROM CvHealthLevelEntity c WHERE c.description = :description"),
    @NamedQuery(name = "CvHealthLevelEntity.findByVersion", query = "SELECT c FROM CvHealthLevelEntity c WHERE c.version = :version")})

public class CvHealthLevelEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_healthLevel_key", nullable = false)
    private Integer healthLevelkey;

    @Basic(optional = false)
    @Column(name = "healthLevel", nullable = false, length = 8)
    private String healthLevel;

    @Column(name = "description", length = 32)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvHealthLevelEntity() {
    }

    public CvHealthLevelEntity(Integer healthLevelkey) {
        this.healthLevelkey = healthLevelkey;
    }

    public CvHealthLevelEntity(Integer healthLevelkey, String healthLevel, int version) {
        this.healthLevelkey = healthLevelkey;
        this.healthLevel = healthLevel;
        this.version = version;
    }

    public Integer getHealthLevelkey() {
        return healthLevelkey;
    }

    public void setHealthLevelkey(Integer healthLevelkey) {
        this.healthLevelkey = healthLevelkey;
    }

    public String getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(String healthLevel) {
        this.healthLevel = healthLevel;
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
        hash += (healthLevelkey != null ? healthLevelkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvHealthLevelEntity)) {
            return false;
        }
        CvHealthLevelEntity other = (CvHealthLevelEntity) object;
        if ((this.healthLevelkey == null && other.healthLevelkey != null) || (this.healthLevelkey != null && !this.healthLevelkey.equals(other.healthLevelkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvHealthLevelEntity[healthLevelkey=" + healthLevelkey + "]";
    }

    @Override
    public Integer getKey() {
        return healthLevelkey;
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
