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
 * <b>File name:</b>  CvDietEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all diet information  <p>
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
@Table(name = "cv_Diet", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"diet"})})
@NamedQueries({
    @NamedQuery(
        name = "CvDietEntity.findAll",
        query = "SELECT c FROM CvDietEntity c " +
                "ORDER BY c.diet" ),

    @NamedQuery(
        name = "CvDietEntity.findByKey",
        query = "SELECT c FROM CvDietEntity c WHERE c.dietKey = :key"),

    @NamedQuery(name = "CvDietEntity.findByDietKey", query = "SELECT c FROM CvDietEntity c WHERE c.dietKey = :dietKey"),
    @NamedQuery(name = "CvDietEntity.findByDiet", query = "SELECT c FROM CvDietEntity c WHERE c.diet = :diet"),
    @NamedQuery(name = "CvDietEntity.findByDietDescription", query = "SELECT c FROM CvDietEntity c WHERE c.dietDescription = :dietDescription"),
    @NamedQuery(name = "CvDietEntity.findByVersion", query = "SELECT c FROM CvDietEntity c WHERE c.version = :version")})

public class CvDietEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_diet_key", nullable = false)
    private Integer dietKey;
    @Basic(optional = false)
    @Column(name = "diet", nullable = false, length = 32)
    private String diet;
    @Column(name = "dietDescription", length = 64)
    private String dietDescription;
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvDietEntity() {
    }

    public CvDietEntity(Integer dietKey) {
        this.dietKey = dietKey;
    }

    public CvDietEntity(Integer dietKey, String diet, int version) {
        this.dietKey = dietKey;
        this.diet = diet;
        this.version = version;
    }

    public Integer getDietKey() {
        return dietKey;
    }

    public void setDietKey(Integer dietKey) {
        this.dietKey = dietKey;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getDietDescription() {
        return dietDescription;
    }

    public void setDietDescription(String dietDescription) {
        this.dietDescription = dietDescription;
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
        hash += (dietKey != null ? dietKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvDietEntity)) {
            return false;
        }
        CvDietEntity other = (CvDietEntity) object;
        if ((this.dietKey == null && other.dietKey != null) || (this.dietKey != null && !this.dietKey.equals(other.dietKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvDietEntity[dietKey=" + dietKey + "]";
    }

    @Override
    public Integer getKey() {
        return dietKey;
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
