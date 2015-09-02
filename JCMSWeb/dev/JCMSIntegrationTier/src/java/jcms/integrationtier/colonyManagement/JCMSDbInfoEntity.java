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
 *
 * @author rkavitha
 */
@Entity
@Table(name = "dbInfo")
@NamedQueries({
    @NamedQuery(name = "JCMSDbInfoEntity.findAll", query = "SELECT j FROM JCMSDbInfoEntity j"),

    @NamedQuery(name = "JCMSDbInfoEntity.findBykey",
    query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.dbinfoKey = :key"),

    @NamedQuery(name = "JCMSDbInfoEntity.findByDbVers", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.dbVers = :dbVers"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByVersDate", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.versDate = :versDate"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByMaxPenID", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.maxPenID = :maxPenID"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByReleaseNum", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.releaseNum = :releaseNum"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByReleaseDate", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.releaseDate = :releaseDate"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByMaxAutoLitterNum", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.maxAutoLitterNum = :maxAutoLitterNum"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByMaxAutoMouseID", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.maxAutoMouseID = :maxAutoMouseID"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByVersion", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.version = :version"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByDbinfoKey", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.dbinfoKey = :dbinfoKey"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByReleaseNotes", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.releaseNotes = :releaseNotes"),
    @NamedQuery(name = "JCMSDbInfoEntity.findByReleaseType", query = "SELECT j FROM JCMSDbInfoEntity j WHERE j.releaseType = :releaseType")})

public class JCMSDbInfoEntity extends ITBaseEntity implements Serializable,
            ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_dbinfo_key")
    private Integer dbinfoKey;

    @Basic(optional = false)
    @Column(name = "dbVers")
    private String dbVers;

    @Basic(optional = false)
    @Column(name = "versDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date versDate;

    @Basic(optional = false)
    @Column(name = "maxPenID")
    private int maxPenID;

    @Basic(optional = false)
    @Column(name = "releaseNum")
    private String releaseNum;

    @Basic(optional = false)
    @Column(name = "webReleaseNum")
    private String webReleaseNum;

    @Column(name = "releaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Column(name = "maxAutoLitterNum")
    private Integer maxAutoLitterNum;

    @Column(name = "maxAutoMouseID")
    private Integer maxAutoMouseID;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @Column(name = "releaseNotes")
    private String releaseNotes;

    @Column(name = "releaseType")
    private String releaseType;

    public JCMSDbInfoEntity() {
    }

    public JCMSDbInfoEntity(String dbVers) {
        this.dbVers = dbVers;
    }

    public JCMSDbInfoEntity(String dbVers, Date versDate, int maxPenID, int version) {
        this.dbVers = dbVers;
        this.versDate = versDate;
        this.maxPenID = maxPenID;
        this.version = version;
    }

    public String getDbVers() {
        return dbVers;
    }

    public void setDbVers(String dbVers) {
        this.dbVers = dbVers;
    }

    public Date getVersDate() {
        return versDate;
    }

    public void setVersDate(Date versDate) {
        this.versDate = versDate;
    }

    public int getMaxPenID() {
        return maxPenID;
    }

    public void setMaxPenID(int maxPenID) {
        this.maxPenID = maxPenID;
    }

    public String getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getMaxAutoLitterNum() {
        return maxAutoLitterNum;
    }

    public void setMaxAutoLitterNum(Integer maxAutoLitterNum) {
        this.maxAutoLitterNum = maxAutoLitterNum;
    }

    public Integer getMaxAutoMouseID() {
        return maxAutoMouseID;
    }

    public void setMaxAutoMouseID(Integer maxAutoMouseID) {
        this.maxAutoMouseID = maxAutoMouseID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Integer getDbinfoKey() {
        return dbinfoKey;
    }

    public void setDbinfoKey(Integer dbinfoKey) {
        this.dbinfoKey = dbinfoKey;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbVers != null ? dbVers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JCMSDbInfoEntity)) {
            return false;
        }
        JCMSDbInfoEntity other = (JCMSDbInfoEntity) object;
        if ((this.dbVers == null && other.dbVers != null) || (this.dbVers != null && !this.dbVers.equals(other.dbVers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.JCMSDbInfoEntity[dbVers=" + dbVers + "]";
    }

    @Override
    public Integer getKey() {
        return dbinfoKey;
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
        System.out.println("\tPK" + "\t" + this.dbinfoKey );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tDatabase ReleaseNum" + "\t" + this.getReleaseNum() );
        System.out.println("\tRelease Type" + "\t" + this.getReleaseType() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
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