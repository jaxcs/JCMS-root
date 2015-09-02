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
 * <b>File name:</b>  CvMouseUseEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Mouse Use information  <p>
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
@Table(name = "cv_MouseUse", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mouseUse"})})
@NamedQueries({
    @NamedQuery(
        name = "CvMouseUseEntity.findAll",
        query = "SELECT c FROM CvMouseUseEntity c " +
                "ORDER BY c.mouseUse"),

    @NamedQuery(
        name = "CvMouseUseEntity.findAllByOwner",
        query = "SELECT DISTINCT c FROM CvMouseUseEntity c, MouseUsageEntity mu, MouseEntity m " +
                "WHERE (mu.mouseKey = m.mouseKey AND c.mouseUse = mu.use) " +
                "AND m.owner = :fOwner " +
                "ORDER BY c.mouseUse"),

    @NamedQuery(
        name = "CvMouseUseEntity.findAllByOwners",
        query = "SELECT c FROM CvMouseUseEntity c " +
                "WHERE EXISTS( " +
                "SELECT 1 FROM MouseUsageEntity mu, MouseEntity m " +
                "WHERE (mu.mouseKey = m.mouseKey AND c.mouseUse = mu.use) " +
                "AND m.owner IN (:lOwner)) " +
                "ORDER BY c.mouseUse "),

    @NamedQuery(
        name = "CvMouseUseEntity.findBykey",
        query = "SELECT c FROM CvMouseUseEntity c WHERE c.mouseUsekey = :key"),

    @NamedQuery(name = "CvMouseUseEntity.findByMouseUsekey", query = "SELECT c FROM CvMouseUseEntity c WHERE c.mouseUsekey = :mouseUsekey"),
    @NamedQuery(name = "CvMouseUseEntity.findByMouseUse", query = "SELECT c FROM CvMouseUseEntity c WHERE c.mouseUse = :mouseUse"),
    @NamedQuery(name = "CvMouseUseEntity.findByUseDescription", query = "SELECT c FROM CvMouseUseEntity c WHERE c.useDescription = :useDescription"),
    @NamedQuery(name = "CvMouseUseEntity.findByVersion", query = "SELECT c FROM CvMouseUseEntity c WHERE c.version = :version")})

public class CvMouseUseEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_mouseUse_key", nullable = false)
    private Integer mouseUsekey;

    @Basic(optional = false)
    @Column(name = "mouseUse", nullable = false, length = 32)
    private String mouseUse;

    @Column(name = "useDescription", length = 64)
    private String useDescription;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvMouseUseEntity() {
    }

    public CvMouseUseEntity(Integer mouseUsekey) {
        this.mouseUsekey = mouseUsekey;
    }

    public CvMouseUseEntity(Integer mouseUsekey, String mouseUse, int version) {
        this.mouseUsekey = mouseUsekey;
        this.mouseUse = mouseUse;
        this.version = version;
    }

    public Integer getMouseUsekey() {
        return mouseUsekey;
    }

    public void setMouseUsekey(Integer mouseUsekey) {
        this.mouseUsekey = mouseUsekey;
    }

    public String getMouseUse() {
        return mouseUse;
    }

    public void setMouseUse(String mouseUse) {
        this.mouseUse = mouseUse;
    }

    public String getUseDescription() {
        return useDescription;
    }

    public void setUseDescription(String useDescription) {
        this.useDescription = useDescription;
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
        hash += (mouseUsekey != null ? mouseUsekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvMouseUseEntity)) {
            return false;
        }
        CvMouseUseEntity other = (CvMouseUseEntity) object;
        if ((this.mouseUsekey == null && other.mouseUsekey != null) || (this.mouseUsekey != null && !this.mouseUsekey.equals(other.mouseUsekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvMouseUseEntity[mouseUsekey=" + mouseUsekey + "]";
    }

    @Override
    public Integer getKey() {
        return mouseUsekey;
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
        System.out.println("\tPK" + "\t" + this.getMouseUsekey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tPen ID" + "\t" + this.getMouseUse() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }
}