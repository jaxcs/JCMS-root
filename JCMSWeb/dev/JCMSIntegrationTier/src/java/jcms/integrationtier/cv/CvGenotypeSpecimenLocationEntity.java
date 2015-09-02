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
 * <b>File name:</b>  CvGenotypeSpecimenLocationEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all genotype specimen location information  <p>
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
@Table(name = "cv_GenotypeSpecimenLocation", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"genotypeSpecimenLocation"})})
@NamedQueries({
    @NamedQuery(
        name = "CvGenotypeSpecimenLocationEntity.findAll",
        query = "SELECT c FROM CvGenotypeSpecimenLocationEntity c " +
                "ORDER BY c.genotypeSpecimenLocation" ),

    @NamedQuery(
        name = "CvGenotypeSpecimenLocationEntity.findBykey",
        query = "SELECT c FROM CvGenotypeSpecimenLocationEntity c WHERE c.genotypeSpecimenLocationkey = :key"),

    @NamedQuery(name = "CvGenotypeSpecimenLocationEntity.findByGenotypeSpecimenLocationkey", query = "SELECT c FROM CvGenotypeSpecimenLocationEntity c WHERE c.genotypeSpecimenLocationkey = :genotypeSpecimenLocationkey"),
    @NamedQuery(name = "CvGenotypeSpecimenLocationEntity.findByGenotypeSpecimenLocation", query = "SELECT c FROM CvGenotypeSpecimenLocationEntity c WHERE c.genotypeSpecimenLocation = :genotypeSpecimenLocation"),
    @NamedQuery(name = "CvGenotypeSpecimenLocationEntity.findByVersion", query = "SELECT c FROM CvGenotypeSpecimenLocationEntity c WHERE c.version = :version")})

public class CvGenotypeSpecimenLocationEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_genotypeSpecimenLocation_key", nullable = false)
    private Integer genotypeSpecimenLocationkey;

    @Basic(optional = false)
    @Column(name = "genotypeSpecimenLocation", nullable = false, length = 16)
    private String genotypeSpecimenLocation;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    private int version;

    public CvGenotypeSpecimenLocationEntity() {
    }

    public CvGenotypeSpecimenLocationEntity(Integer genotypeSpecimenLocationkey) {
        this.genotypeSpecimenLocationkey = genotypeSpecimenLocationkey;
    }

    public CvGenotypeSpecimenLocationEntity(Integer genotypeSpecimenLocationkey, String genotypeSpecimenLocation, int version) {
        this.genotypeSpecimenLocationkey = genotypeSpecimenLocationkey;
        this.genotypeSpecimenLocation = genotypeSpecimenLocation;
        this.version = version;
    }

    public Integer getGenotypeSpecimenLocationkey() {
        return genotypeSpecimenLocationkey;
    }

    public void setGenotypeSpecimenLocationkey(Integer genotypeSpecimenLocationkey) {
        this.genotypeSpecimenLocationkey = genotypeSpecimenLocationkey;
    }

    public String getGenotypeSpecimenLocation() {
        return genotypeSpecimenLocation;
    }

    public void setGenotypeSpecimenLocation(String genotypeSpecimenLocation) {
        this.genotypeSpecimenLocation = genotypeSpecimenLocation;
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
        hash += (genotypeSpecimenLocationkey != null ? genotypeSpecimenLocationkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvGenotypeSpecimenLocationEntity)) {
            return false;
        }
        CvGenotypeSpecimenLocationEntity other = (CvGenotypeSpecimenLocationEntity) object;
        if ((this.genotypeSpecimenLocationkey == null && other.genotypeSpecimenLocationkey != null) || (this.genotypeSpecimenLocationkey != null && !this.genotypeSpecimenLocationkey.equals(other.genotypeSpecimenLocationkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.cv.CvGenotypeSpecimenLocationEntity[genotypeSpecimenLocationkey=" + genotypeSpecimenLocationkey + "]";
    }

    @Override
    public Integer getKey() {
        return genotypeSpecimenLocationkey;
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
