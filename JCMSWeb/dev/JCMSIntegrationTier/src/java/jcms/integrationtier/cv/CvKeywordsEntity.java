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
 * <b>File name:</b>  CvKeworsEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all keywords information <p>
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
@Table(name = "cv_Keywords", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"keyword"})})
@NamedQueries({
    @NamedQuery(
        name = "CvKeywordsEntity.findAll",
        query = "SELECT c FROM CvKeywordsEntity c " +
                "ORDER BY c.keyword"),

    @NamedQuery(
        name = "CvKeywordsEntity.findBykey",
        query = "SELECT c FROM CvKeywordsEntity c WHERE c.keywordKey = :key"),

    @NamedQuery(name = "CvKeywordsEntity.findByKeywordKey", query = "SELECT c FROM CvKeywordsEntity c WHERE c.keywordKey = :keywordKey"),
    @NamedQuery(name = "CvKeywordsEntity.findByKeyword", query = "SELECT c FROM CvKeywordsEntity c WHERE c.keyword = :keyword"),
    @NamedQuery(name = "CvKeywordsEntity.findByVersion", query = "SELECT c FROM CvKeywordsEntity c WHERE c.version = :version")})

public class CvKeywordsEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_keyword_key", nullable = false)
    private Integer keywordKey;

    @Basic(optional = false)
    @Column(name = "keyword", nullable = false, length = 32)
    private String keyword;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvKeywordsEntity() {
    }

    public CvKeywordsEntity(Integer keywordKey) {
        this.keywordKey = keywordKey;
    }

    public CvKeywordsEntity(Integer keywordKey, String keyword, int version) {
        this.keywordKey = keywordKey;
        this.keyword = keyword;
        this.version = version;
    }

    public Integer getKeywordKey() {
        return keywordKey;
    }

    public void setKeywordKey(Integer keywordKey) {
        this.keywordKey = keywordKey;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
        hash += (keywordKey != null ? keywordKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvKeywordsEntity)) {
            return false;
        }
        CvKeywordsEntity other = (CvKeywordsEntity) object;
        if ((this.keywordKey == null && other.keywordKey != null) || (this.keywordKey != null && !this.keywordKey.equals(other.keywordKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvKeywordsEntity[keywordKey=" + keywordKey + "]";
    }

    @Override
    public Integer getKey() {
        return keywordKey;
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
