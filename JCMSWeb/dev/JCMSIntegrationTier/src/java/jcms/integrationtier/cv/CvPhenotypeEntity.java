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
 *
 * @author bas
 */

/**
 * <b>File name:</b>  CvPhenotypeEntity.java  <p>
 * <b>Date developed:</b>  April 2014 <p>
 * <b>Purpose:</b>  Contains all Phenotype term information  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author: bas $ <p>
 * <b>Last changed date:</b> $Date: 2014-04-28 07:33:21 -0500 (Mon, 28 April 2014) $   <p>
 * NOTE the isActive field is MISSING from this definition  -- added 6 June 2014
 */
@Entity
@Table(name = "cv_Phenotype", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"phenotype"})})
@NamedQueries({
    @NamedQuery(
        name = "CvPhenotypeEntity.findAll",
        query = "SELECT c FROM CvPhenotypeEntity c " +
                "ORDER BY c.phenotypeTermName"),

    @NamedQuery(
        name = "CvPhenotypeEntity.findBykey",
        query = "SELECT c FROM CvPhenotypeEntity c WHERE c.phenotypeTermKey = :key"),

    @NamedQuery(name = "CvPhenotypeEntity.findByPhenotypeKey", query = "SELECT c FROM CvPhenotypeEntity c WHERE c.phenotypeTermKey = :phenotypeTermKey"),
    @NamedQuery(name = "CvPhenotypeEntity.findByPhenotype", query = "SELECT c FROM CvPhenotypeEntity c WHERE c.phenotypeTermName = :phenotypeTermName"),
    @NamedQuery(name = "CvPhenotypeEntity.findByPhenotypeDescription", query = "SELECT c FROM CvPhenotypeEntity c WHERE c.phenotypeTermDescription = :phenotypeTermDescription"),
    @NamedQuery(name = "CvPhenotypeEntity.findByIsActive", query = "SELECT c FROM CvPhenotypeEntity c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "CvPhenotypeEntity.findByVersion", query = "SELECT c FROM CvPhenotypeEntity c WHERE c.versionNum = :versionNum")})

public class CvPhenotypeEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {
 
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_phenotype_key", nullable = false)
    private Integer phenotypeTermKey;

    @Basic(optional = false)
    @Column(name = "phenotype", nullable = false, length = 255)
    private String phenotypeTermName;

    @Column(name = "description", length = 255)
    private String phenotypeTermDescription;
    
    @Basic(optional = false)
    @Column(name = "isActive", nullable = false)
    private int isActive;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int versionNum;

    public CvPhenotypeEntity() {
    }

    public CvPhenotypeEntity(Integer phenotypeTermKey) {
        this.phenotypeTermKey = phenotypeTermKey;
    }

    public CvPhenotypeEntity(Integer phenotypeTermKey, String phenotypeTermName, String phenotypeTermDescription, int isActive, int versionNum) {
        this.phenotypeTermKey = phenotypeTermKey;
        this.phenotypeTermName = phenotypeTermName;
        this.phenotypeTermDescription = phenotypeTermDescription;
        this.isActive = isActive;
        this.versionNum = versionNum;
    }

    public Integer getPhenotypeTermKey() {
        return phenotypeTermKey;
    }

    public void setPhenotypeTermKey(Integer phenotypeTermKey) {
        this.phenotypeTermKey = phenotypeTermKey;
    }

    public String getPhenotypeTermName() {
        return phenotypeTermName;
    }

    public void setPhenotypeTermName(String phenotypeTermName) {
        this.phenotypeTermName = phenotypeTermName;
    }

    public String getPhenotypeTermDescription() {
        return phenotypeTermDescription;
    }

    public void setPhenotypeTermDescription(String phenotypeTermDescription) {
        this.phenotypeTermDescription = phenotypeTermDescription;
    }
        /**
     * @return the isActive
     */
    public int getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (phenotypeTermKey != null ? phenotypeTermKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvPhenotypeEntity)) {
            return false;
        }
        CvPhenotypeEntity other = (CvPhenotypeEntity) object;
        if ((this.phenotypeTermKey == null && other.phenotypeTermKey != null) || (this.phenotypeTermKey != null && !this.phenotypeTermKey.equals(other.phenotypeTermKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvPhenotypeEntity[phenotypeTermKey=" + phenotypeTermKey + "]";
    }

    @Override
    public Integer getKey() {
        return phenotypeTermKey;
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
        System.out.println("\tPK" + "\t" + this.getPhenotypeTermKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tPhenotype" + "\t" + this.getPhenotypeTermName() );
        System.out.println("\tVersion" + "\t" + this.getVersionNum() );
    }
}
