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
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  CvGeneclassEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Geneclass information  <p>
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
@Table(name = "cv_GeneClass")
@NamedQueries({
    @NamedQuery(
        name = "CvGeneclassEntity.findAll",
        query = "SELECT c FROM CvGeneclassEntity c " +
                "ORDER BY c.geneClass" ),

    @NamedQuery(
        name = "CvGeneclassEntity.findBykey",
        query = "SELECT c FROM CvGeneclassEntity c WHERE c.geneClasskey = :key"),

    @NamedQuery(name = "CvGeneclassEntity.findByGeneClasskey", query = "SELECT c FROM CvGeneclassEntity c WHERE c.geneClasskey = :geneClasskey"),
    @NamedQuery(name = "CvGeneclassEntity.findByGeneClass", query = "SELECT c FROM CvGeneclassEntity c WHERE c.geneClass = :geneClass"),
    @NamedQuery(name = "CvGeneclassEntity.findByDescription", query = "SELECT c FROM CvGeneclassEntity c WHERE c.description = :description"),
    @NamedQuery(name = "CvGeneclassEntity.findByVersion", query = "SELECT c FROM CvGeneclassEntity c WHERE c.version = :version")})

public class CvGeneclassEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_geneClass_key", nullable = false)
    private Integer geneClasskey;

    @Basic(optional = false)
    @Column(name = "GeneClass", nullable = false, length = 16)
    private String geneClass;

    @Column(name = "Description", length = 50)
    private String description;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvGeneclassEntity() {
    }

    public CvGeneclassEntity(Integer geneClasskey) {
        this.geneClasskey = geneClasskey;
    }

    public CvGeneclassEntity(Integer geneClasskey, String geneClass, int version) {
        this.geneClasskey = geneClasskey;
        this.geneClass = geneClass;
        this.version = version;
    }

    public Integer getGeneClasskey() {
        return geneClasskey;
    }

    public void setGeneClasskey(Integer geneClasskey) {
        this.geneClasskey = geneClasskey;
    }

    public String getGeneClass() {
        return geneClass;
    }

    public void setGeneClass(String geneClass) {
        this.geneClass = geneClass;
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
        hash += (geneClasskey != null ? geneClasskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvGeneclassEntity)) {
            return false;
        }
        CvGeneclassEntity other = (CvGeneclassEntity) object;
        if ((this.geneClasskey == null && other.geneClasskey != null) || (this.geneClasskey != null && !this.geneClasskey.equals(other.geneClasskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvGeneclassEntity[geneClasskey=" + geneClasskey + "]";
    }

    @Override
    public Integer getKey() {
        return geneClasskey;
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
