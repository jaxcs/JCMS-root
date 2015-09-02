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
 * <b>File name:</b>  CvGenerationEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all generation information  <p>
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
@Table(name = "cv_Generation")
@NamedQueries({
    @NamedQuery(
        name = "CvGenerationEntity.findAll",
        query = "SELECT c FROM CvGenerationEntity c " +
                "ORDER BY c.generation" ),

    @NamedQuery(
        name = "CvGenerationEntity.findBykey",
        query = "SELECT c FROM CvGenerationEntity c WHERE c.generationKey = :key"),

    @NamedQuery(name = "CvGenerationEntity.findByGenerationKey", query = "SELECT c FROM CvGenerationEntity c WHERE c.generationKey = :generationKey"),
    @NamedQuery(name = "CvGenerationEntity.findByGeneration", query = "SELECT c FROM CvGenerationEntity c WHERE c.generation = :generation"),
    @NamedQuery(name = "CvGenerationEntity.findByVersion", query = "SELECT c FROM CvGenerationEntity c WHERE c.version = :version")})

public class CvGenerationEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_generation_key", nullable = false)
    private Integer generationKey;

    @Basic(optional = false)
    @Column(name = "generation", nullable = false, length = 16)
    private String generation;
    
    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvGenerationEntity() {
    }

    public CvGenerationEntity(Integer generationKey) {
        this.generationKey = generationKey;
    }

    public CvGenerationEntity(Integer generationKey, String generation, int version) {
        this.generationKey = generationKey;
        this.generation = generation;
        this.version = version;
    }

    public Integer getGenerationKey() {
        return generationKey;
    }

    public void setGenerationKey(Integer generationKey) {
        this.generationKey = generationKey;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
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
        hash += (generationKey != null ? generationKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvGenerationEntity)) {
            return false;
        }
        CvGenerationEntity other = (CvGenerationEntity) object;
        if ((this.generationKey == null && other.generationKey != null) || (this.generationKey != null && !this.generationKey.equals(other.generationKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvGenerationEntity[generationKey=" + generationKey + "]";
    }

    @Override
    public Integer getKey() {
        return generationKey;
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
        System.out.println("\tGeneration Key" + "\t" + this.getGenerationKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tGeneration" + "\t" + this.getGeneration() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }
}