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

package jcms.integrationtier.jcmsWeb;

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
 * <b>File name:</b>  DbInfoEntity.java  <p>
 * <b>RsDate developed:</b>  October 2010 <p>
 * <b>Purpose:</b>  Persistent object and value object for controlled vocabulary
 *                  database table DbInfo.  <p>
 * <b>Overview:</b>  Contains the persistence annotations that Container
 *                   Managed Persistence API scans and invokes the appropriate
 *                   actions enabling data management functionality.  The entity
 *                   is also a data transfer object.  It contains all the getters
 *                   and setters to provide DTO functionality.  <p>
 * <b>Usage:</b>  Standard constructors exist for class invocation.  <p>
 * <b>Inputs:</b>  No external inputs.   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  bean CvFileTypeSession.  The queries represent standard
 *                  ANSII SQL syntax is managed by CMP and syntax checked by the
 *                  associated Query Language.  Query languages provide support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>    <p>
 * <b>Last changed date:</b> $Date: 2010-04-05 16:59:33 -0400 (Mon, 05 Apr 2010) $   <p>
 * @author Kavitha Rama
 * @version $Revision:  $
 */
@Entity
@Table(name = "dbInfo")
@NamedQueries({
     @NamedQuery(
        name = "DbInfoEntity.findAll",
        query = "SELECT d FROM DbInfoEntity d"),

     @NamedQuery(
        name = "DbInfoEntity.findBykey",
        query = "SELECT d FROM DbInfoEntity d WHERE d.dbinfoKey = :key"),

     @NamedQuery(
        name = "DbInfoEntity.findAllActive",
        query = "SELECT d FROM DbInfoEntity d"),

    @NamedQuery(name = "DbInfoEntity.findByDbinfoKey", query = "SELECT d FROM DbInfoEntity d WHERE d.dbinfoKey = :dbinfoKey"),
    @NamedQuery(name = "DbInfoEntity.findByReleaseDate", query = "SELECT d FROM DbInfoEntity d WHERE d.releaseDate = :releaseDate"),
    @NamedQuery(name = "DbInfoEntity.findByReleaseType", query = "SELECT d FROM DbInfoEntity d WHERE d.releaseType = :releaseType"),
    @NamedQuery(name = "DbInfoEntity.findByReleaseNotes", query = "SELECT d FROM DbInfoEntity d WHERE d.releaseNotes = :releaseNotes")
})

public class DbInfoEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_dbinfo_key")
    private Integer dbinfoKey;

    @Basic(optional = false)
    @Column(name = "releaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Column(name = "releaseType")
    private String releaseType;

    @Column(name = "releaseNotes")
    private String releaseNotes;
    
    @Basic(optional = false)
    @Column(name = "webReleaseNum")
    private String webReleaseNum;

    public DbInfoEntity() {
    }

    public DbInfoEntity(Integer dbinfoKey) {
        this.dbinfoKey = dbinfoKey;
    }

    public DbInfoEntity(Integer dbinfoKey, Date releaseDate) {
        this.dbinfoKey = dbinfoKey;
        this.releaseDate = releaseDate;
    }

    public Integer getDbinfoKey() {
        return dbinfoKey;
    }

    public void setDbinfoKey(Integer dbinfoKey) {
        this.dbinfoKey = dbinfoKey;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbinfoKey != null ? dbinfoKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbInfoEntity)) {
            return false;
        }
        DbInfoEntity other = (DbInfoEntity) object;
        if ((this.dbinfoKey == null && other.dbinfoKey != null) || (this.dbinfoKey != null && !this.dbinfoKey.equals(other.dbinfoKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.jcmsWeb.DbInfoEntity[dbinfoKey=" + dbinfoKey + "]";
    }

    @Override
    public Integer getKey() {
        return dbinfoKey;
    }

    @Override
    public ITBaseEntityInterface getEntity()
    {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println("\tDbInfoEntity Contents");
        System.out.println("\t=====================");
        System.out.println("\tDatabase key" + "\t" + this.getDbinfoKey() );
        System.out.println("\tRelease Date" + "\t" + this.getReleaseDate() );
        System.out.println("\tRelease Type" + "\t" + this.getReleaseType() );
        System.out.println("\tNotes" + "\t" + this.getReleaseNotes() );
        System.out.println("\n");
    }

    /**
     * @return the webReleaseNum
     */
    public String getWebReleaseNum() {
        return webReleaseNum;
    }

    /**
     * @param webReleaseNum the webReleaseNum to set
     */
    public void setWebReleaseNum(String webReleaseNum) {
        this.webReleaseNum = webReleaseNum;
    }
}