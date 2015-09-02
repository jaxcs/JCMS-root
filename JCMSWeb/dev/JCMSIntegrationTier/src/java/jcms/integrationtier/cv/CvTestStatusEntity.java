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
 * <b>File name:</b>  TestStatusEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all test status information  <p>
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
@Table(name = "cv_TestStatus")
@NamedQueries({
    @NamedQuery(
        name = "CvTestStatusEntity.findAll",
        query = "SELECT c FROM CvTestStatusEntity c " +
                "ORDER BY c.testStatus"),

    @NamedQuery(
        name = "CvTestStatusEntity.findBykey",
        query = "SELECT c FROM CvTestStatusEntity c WHERE c.testStatuskey = :key"),

    @NamedQuery(name = "CvTestStatusEntity.findByTestStatuskey", query = "SELECT c FROM CvTestStatusEntity c WHERE c.testStatuskey = :testStatuskey"),
    @NamedQuery(name = "CvTestStatusEntity.findByTestStatus", query = "SELECT c FROM CvTestStatusEntity c WHERE c.testStatus = :testStatus"),
    @NamedQuery(name = "CvTestStatusEntity.findByVersion", query = "SELECT c FROM CvTestStatusEntity c WHERE c.version = :version")})

public class CvTestStatusEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_testStatus_key", nullable = false)
    private Integer testStatuskey;

    @Basic(optional = false)
    @Column(name = "testStatus", nullable = false, length = 8)
    private String testStatus;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvTestStatusEntity() {
    }

    public CvTestStatusEntity(Integer testStatuskey) {
        this.testStatuskey = testStatuskey;
    }

    public CvTestStatusEntity(Integer testStatuskey, String testStatus, int version) {
        this.testStatuskey = testStatuskey;
        this.testStatus = testStatus;
        this.version = version;
    }

    public Integer getTestStatuskey() {
        return testStatuskey;
    }

    public void setTestStatuskey(Integer testStatuskey) {
        this.testStatuskey = testStatuskey;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
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
        hash += (testStatuskey != null ? testStatuskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvTestStatusEntity)) {
            return false;
        }
        CvTestStatusEntity other = (CvTestStatusEntity) object;
        if ((this.testStatuskey == null && other.testStatuskey != null) || (this.testStatuskey != null && !this.testStatuskey.equals(other.testStatuskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvTestStatusEntity[testStatuskey=" + testStatuskey + "]";
    }

    @Override
    public Integer getKey() {
        return testStatuskey;
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
