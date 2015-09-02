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
 * <b>File name:</b>  CvMatingCardNotesEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all mating card notes information  <p>
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
@Table(name = "cv_MatingCardNotes")
@NamedQueries({
    @NamedQuery(
        name = "CvMatingCardNotesEntity.findAll",
        query = "SELECT c FROM CvMatingCardNotesEntity c " +
                "ORDER BY c.matingNotes"),

    @NamedQuery(
        name = "CvMatingCardNotesEntity.findBykey",
        query = "SELECT c FROM CvMatingCardNotesEntity c WHERE c.matingCardNoteskey = :key"),

    @NamedQuery(name = "CvMatingCardNotesEntity.findByMatingCardNoteskey", query = "SELECT c FROM CvMatingCardNotesEntity c WHERE c.matingCardNoteskey = :matingCardNoteskey"),
    @NamedQuery(name = "CvMatingCardNotesEntity.findByMatingNotes", query = "SELECT c FROM CvMatingCardNotesEntity c WHERE c.matingNotes = :matingNotes"),
    @NamedQuery(name = "CvMatingCardNotesEntity.findByVersion", query = "SELECT c FROM CvMatingCardNotesEntity c WHERE c.version = :version")})

public class CvMatingCardNotesEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_matingCardNotes_key", nullable = false)
    private Integer matingCardNoteskey;

    @Basic(optional = false)
    @Column(name = "matingNotes", nullable = false, length = 64)
    private String matingNotes;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvMatingCardNotesEntity() {
    }

    public CvMatingCardNotesEntity(Integer matingCardNoteskey) {
        this.matingCardNoteskey = matingCardNoteskey;
    }

    public CvMatingCardNotesEntity(Integer matingCardNoteskey, String matingNotes, int version) {
        this.matingCardNoteskey = matingCardNoteskey;
        this.matingNotes = matingNotes;
        this.version = version;
    }

    public Integer getMatingCardNoteskey() {
        return matingCardNoteskey;
    }

    public void setMatingCardNoteskey(Integer matingCardNoteskey) {
        this.matingCardNoteskey = matingCardNoteskey;
    }

    public String getMatingNotes() {
        return matingNotes;
    }

    public void setMatingNotes(String matingNotes) {
        this.matingNotes = matingNotes;
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
        hash += (matingCardNoteskey != null ? matingCardNoteskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvMatingCardNotesEntity)) {
            return false;
        }
        CvMatingCardNotesEntity other = (CvMatingCardNotesEntity) object;
        if ((this.matingCardNoteskey == null && other.matingCardNoteskey != null) || (this.matingCardNoteskey != null && !this.matingCardNoteskey.equals(other.matingCardNoteskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvMatingCardNotesEntity[matingCardNoteskey=" + matingCardNoteskey + "]";
    }

    @Override
    public Integer getKey() {
        return matingCardNoteskey;
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
