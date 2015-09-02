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
 * <b>File name:</b>  CvCauseOfDeathEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Cause of Death information <p>
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
@Table(name = "cv_CauseOfDeath", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cod"})})
@NamedQueries({
    @NamedQuery(
        name = "CvCauseOfDeathEntity.findAll",
        query = "SELECT c FROM CvCauseOfDeathEntity c " +
                "ORDER BY c.cod "),

    @NamedQuery(
        name = "CvCauseOfDeathEntity.findBykey",
        query = "SELECT c FROM CvCauseOfDeathEntity c WHERE c.causeOfDeathkey = :key"),

    @NamedQuery(name = "CvCauseOfDeathEntity.findByCauseOfDeathkey", query = "SELECT c FROM CvCauseOfDeathEntity c WHERE c.causeOfDeathkey = :causeOfDeathkey"),
    @NamedQuery(name = "CvCauseOfDeathEntity.findByCod", query = "SELECT c FROM CvCauseOfDeathEntity c WHERE c.cod = :cod"),
    @NamedQuery(name = "CvCauseOfDeathEntity.findByDescription", query = "SELECT c FROM CvCauseOfDeathEntity c WHERE c.description = :description"),
    @NamedQuery(name = "CvCauseOfDeathEntity.findByVersion", query = "SELECT c FROM CvCauseOfDeathEntity c WHERE c.version = :version")})

public class CvCauseOfDeathEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_causeOfDeath_key", nullable = false)
    private Integer causeOfDeathkey;

    @Basic(optional = false)
    @Column(name = "cod", nullable = false, length = 32)
    private String cod;

    @Column(name = "description", length = 255)
    private String description;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvCauseOfDeathEntity() {
    }

    public CvCauseOfDeathEntity(Integer causeOfDeathkey) {
        this.causeOfDeathkey = causeOfDeathkey;
    }

    public CvCauseOfDeathEntity(Integer causeOfDeathkey, String cod, int version) {
        this.causeOfDeathkey = causeOfDeathkey;
        this.cod = cod;
        this.version = version;
    }

    public Integer getCauseOfDeathkey() {
        return causeOfDeathkey;
    }

    public void setCauseOfDeathkey(Integer causeOfDeathkey) {
        this.causeOfDeathkey = causeOfDeathkey;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
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
        hash += (causeOfDeathkey != null ? causeOfDeathkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvCauseOfDeathEntity)) {
            return false;
        }
        CvCauseOfDeathEntity other = (CvCauseOfDeathEntity) object;
        if ((this.causeOfDeathkey == null && other.causeOfDeathkey != null) || (this.causeOfDeathkey != null && !this.causeOfDeathkey.equals(other.causeOfDeathkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvCauseOfDeathEntity[causeOfDeathkey=" + causeOfDeathkey + "]";
    }

    @Override
    public Integer getKey() {
        return causeOfDeathkey;
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
