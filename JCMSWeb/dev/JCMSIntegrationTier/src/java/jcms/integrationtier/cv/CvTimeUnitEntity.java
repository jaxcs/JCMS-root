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
 * <b>File name:</b>  CvTimeUnitEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all time unit information  <p>
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
@Table(name = "cv_TimeUnit", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"_timeUnit_key"})})
@NamedQueries({
    @NamedQuery(
        name = "CvTimeUnitEntity.findAll",
        query = "SELECT c FROM CvTimeUnitEntity c " +
                "ORDER BY c.timeUnit"),

    @NamedQuery(
        name = "CvTimeUnitEntity.findBykey",
        query = "SELECT c FROM CvTimeUnitEntity c WHERE c.timeUnitkey = :key"),

    @NamedQuery(name = "CvTimeUnitEntity.findByTimeUnitkey", query = "SELECT c FROM CvTimeUnitEntity c WHERE c.timeUnitkey = :timeUnitkey"),
    @NamedQuery(name = "CvTimeUnitEntity.findByTimeUnit", query = "SELECT c FROM CvTimeUnitEntity c WHERE c.timeUnit = :timeUnit"),
    @NamedQuery(name = "CvTimeUnitEntity.findByAbbreviation", query = "SELECT c FROM CvTimeUnitEntity c WHERE c.abbreviation = :abbreviation"),
    @NamedQuery(name = "CvTimeUnitEntity.findByMinutesPerUnit", query = "SELECT c FROM CvTimeUnitEntity c WHERE c.minutesPerUnit = :minutesPerUnit"),
    @NamedQuery(name = "CvTimeUnitEntity.findByVersion", query = "SELECT c FROM CvTimeUnitEntity c WHERE c.version = :version")})

public class CvTimeUnitEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_timeUnit_key", nullable = false)
    private Integer timeUnitkey;

    @Basic(optional = false)
    @Column(name = "timeUnit", nullable = false, length = 32)
    private String timeUnit;

    @Basic(optional = false)
    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

    @Basic(optional = false)
    @Column(name = "minutesPerUnit", nullable = false)
    private int minutesPerUnit;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvTimeUnitEntity() {
    }

    public CvTimeUnitEntity(Integer timeUnitkey) {
        this.timeUnitkey = timeUnitkey;
    }

    public CvTimeUnitEntity(Integer timeUnitkey, String timeUnit, String abbreviation, int minutesPerUnit, int version) {
        this.timeUnitkey = timeUnitkey;
        this.timeUnit = timeUnit;
        this.abbreviation = abbreviation;
        this.minutesPerUnit = minutesPerUnit;
        this.version = version;
    }

    public Integer getTimeUnitkey() {
        return timeUnitkey;
    }

    public void setTimeUnitkey(Integer timeUnitkey) {
        this.timeUnitkey = timeUnitkey;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getMinutesPerUnit() {
        return minutesPerUnit;
    }

    public void setMinutesPerUnit(int minutesPerUnit) {
        this.minutesPerUnit = minutesPerUnit;
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
        hash += (timeUnitkey != null ? timeUnitkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvTimeUnitEntity)) {
            return false;
        }
        CvTimeUnitEntity other = (CvTimeUnitEntity) object;
        if ((this.timeUnitkey == null && other.timeUnitkey != null) || (this.timeUnitkey != null && !this.timeUnitkey.equals(other.timeUnitkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvTimeUnitEntity[timeUnitkey=" + timeUnitkey + "]";
    }

    @Override
    public Integer getKey() {
        return timeUnitkey;
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
