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
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  MouseEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Mouse information like mouse ID, strain,
 * protocol etc  <p>
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
@Table(name = "Mouse")
@NamedQueries({
    @NamedQuery(
    name = "MouseEntity.findAll",
    query = "SELECT m FROM MouseEntity m " +
                "ORDER BY m.id "),

    @NamedQuery(
    name = "MouseEntity.findAllByOwner",
    query = "SELECT m FROM MouseEntity m WHERE m.owner = :fOwner " +
                "ORDER BY m.id "),

    @NamedQuery(
    name = "MouseEntity.findAllByOwners",
    query = "SELECT m FROM MouseEntity m WHERE m.owner IN (:lOwner) " +
                "ORDER BY m.id "),

    @NamedQuery(
    name = "MouseEntity.findBykey",
    query = "SELECT m FROM MouseEntity m WHERE m.mouseKey = :key"),
    
    @NamedQuery(name = "MouseEntity.findMaxPrimaryKey", query = "SELECT max(u.mouseKey) FROM MouseEntity u " ),
    

    @NamedQuery(name = "MouseEntity.findByMouseKey", query = "SELECT m FROM MouseEntity m WHERE m.mouseKey = :mouseKey"),
    @NamedQuery(name = "MouseEntity.findById", query = "SELECT m FROM MouseEntity m WHERE m.id = :id"),
    @NamedQuery(name = "MouseEntity.findByNewTag", query = "SELECT m FROM MouseEntity m WHERE m.newTag = :newTag"),
    @NamedQuery(name = "MouseEntity.findByBirthDate", query = "SELECT m FROM MouseEntity m WHERE m.birthDate = :birthDate"),
    @NamedQuery(name = "MouseEntity.findByExitDate", query = "SELECT m FROM MouseEntity m WHERE m.exitDate = :exitDate"),
    @NamedQuery(name = "MouseEntity.findByCod", query = "SELECT m FROM MouseEntity m WHERE m.cod = :cod"),
    @NamedQuery(name = "MouseEntity.findByCodNotes", query = "SELECT m FROM MouseEntity m WHERE m.codNotes = :codNotes"),
    @NamedQuery(name = "MouseEntity.findByGeneration", query = "SELECT m FROM MouseEntity m WHERE m.generation = :generation"),
    @NamedQuery(name = "MouseEntity.findBySex", query = "SELECT m FROM MouseEntity m WHERE m.sex = :sex"),
    @NamedQuery(name = "MouseEntity.findByLifeStatus", query = "SELECT m FROM MouseEntity m WHERE m.lifeStatus = :lifeStatus"),
    @NamedQuery(name = "MouseEntity.findByBreedingStatus", query = "SELECT m FROM MouseEntity m WHERE m.breedingStatus = :breedingStatus"),
    @NamedQuery(name = "MouseEntity.findByCoatColor", query = "SELECT m FROM MouseEntity m WHERE m.coatColor = :coatColor"),
    @NamedQuery(name = "MouseEntity.findByDiet", query = "SELECT m FROM MouseEntity m WHERE m.diet = :diet"),
    @NamedQuery(name = "MouseEntity.findByOwner", query = "SELECT m FROM MouseEntity m WHERE m.owner = :owner"),
    @NamedQuery(name = "MouseEntity.findByOrigin", query = "SELECT m FROM MouseEntity m WHERE m.origin = :origin"),
    @NamedQuery(name = "MouseEntity.findByProtocol", query = "SELECT m FROM MouseEntity m WHERE m.protocol = :protocol"),
    @NamedQuery(name = "MouseEntity.findByComment", query = "SELECT m FROM MouseEntity m WHERE m.comment = :comment"),
    @NamedQuery(name = "MouseEntity.findBySampleVialID", query = "SELECT m FROM MouseEntity m WHERE m.sampleVialID = :sampleVialID"),
    @NamedQuery(name = "MouseEntity.findBySampleVialTagPosition", query = "SELECT m FROM MouseEntity m WHERE m.sampleVialTagPosition = :sampleVialTagPosition"),
    @NamedQuery(name = "MouseEntity.findByVersion", query = "SELECT m FROM MouseEntity m WHERE m.version = :version")})

public class MouseEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_mouse_key")
    private Integer mouseKey;

    @Basic(optional = false)
    @Column(name = "ID")
    private String id;

    @Column(name = "newTag")
    private String newTag;

    @Basic(optional = false)
    @Column(name = "birthDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "exitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;

    @Column(name = "cod")
    private String cod;

    @Column(name = "codNotes")
    private String codNotes;

    @Basic(optional = false)
    @Column(name = "generation")
    private String generation;

    @Basic(optional = false)
    @Column(name = "sex")
    private String sex;

    @Basic(optional = false)
    @Column(name = "lifeStatus")
    private String lifeStatus;

    @Basic(optional = false)
    @Column(name = "breedingStatus")
    private String breedingStatus;

    @Column(name = "coatColor")
    private String coatColor;

    @Column(name = "diet")
    private String diet;

    @Basic(optional = false)
    @Column(name = "owner")
    private String owner;

    @Basic(optional = false)
    @Column(name = "origin")
    private String origin;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "comment")
    private String comment;

    @Column(name = "sampleVialID")
    private String sampleVialID;

    @Column(name = "sampleVialTagPosition")
    private String sampleVialTagPosition;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @JoinColumn(name = "_litter_key", referencedColumnName = "_litter_key")
    @ManyToOne
    private LitterEntity litterKey;

    @JoinColumn(name = "_strain_key", referencedColumnName = "_strain_key")
    @ManyToOne(optional = false)
    private StrainEntity strainKey;

    @JoinColumn(name = "_pen_key", referencedColumnName = "_container_key")
    @ManyToOne(optional = false)
    private ContainerEntity penKey;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sireKey")
    private Collection<MatingEntity> matingEntityCollection;

    public MouseEntity() {
    }

    public MouseEntity(Integer mouseKey) {
        this.mouseKey = mouseKey;
    }

    public MouseEntity(Integer mouseKey, String id, Date birthDate, String generation, String sex, String lifeStatus, String breedingStatus, String owner, String origin, int version) {
        this.mouseKey = mouseKey;
        this.id = id;
        this.birthDate = birthDate;
        this.generation = generation;
        this.sex = sex;
        this.lifeStatus = lifeStatus;
        this.breedingStatus = breedingStatus;
        this.owner = owner;
        this.origin = origin;
        this.version = version;
    }

    public Integer getMouseKey() {
        return mouseKey;
    }

    public void setMouseKey(Integer mouseKey) {
        this.mouseKey = mouseKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewTag() {
        return newTag;
    }

    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCodNotes() {
        return codNotes;
    }

    public void setCodNotes(String codNotes) {
        this.codNotes = codNotes;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getBreedingStatus() {
        return breedingStatus;
    }

    public void setBreedingStatus(String breedingStatus) {
        this.breedingStatus = breedingStatus;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSampleVialID() {
        return sampleVialID;
    }

    public void setSampleVialID(String sampleVialID) {
        this.sampleVialID = sampleVialID;
    }

    public String getSampleVialTagPosition() {
        return sampleVialTagPosition;
    }

    public void setSampleVialTagPosition(String sampleVialTagPosition) {
        this.sampleVialTagPosition = sampleVialTagPosition;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LitterEntity getLitterKey() {
        return litterKey;
    }

    public void setLitterKey(LitterEntity litterKey) {
        this.litterKey = litterKey;
    }

    public StrainEntity getStrainKey() {
        return strainKey;
    }

    public void setStrainKey(StrainEntity strainKey) {
        this.strainKey = strainKey;
    }

    public ContainerEntity getPenKey() {
        return penKey;
    }

    public void setPenKey(ContainerEntity penKey) {
        this.penKey = penKey;
    }

    public Collection<MatingEntity> getMatingEntityCollection() {
        return matingEntityCollection;
    }

    public void setMatingEntityCollection(Collection<MatingEntity> matingEntityCollection) {
        this.matingEntityCollection = matingEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mouseKey != null ? mouseKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MouseEntity)) {
            return false;
        }
        MouseEntity other = (MouseEntity) object;
        if ((this.mouseKey == null && other.mouseKey != null) || (this.mouseKey != null && !this.mouseKey.equals(other.mouseKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.MouseEntity[mouseKey=" + mouseKey + "]";
    }

    @Override
    public Integer getKey() {
        return mouseKey;
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
        System.out.println("\tKey" + "\t" + this.getMouseKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tID" + "\t" + this.getId());
        System.out.println("\tVersion" + "\t" + this.getVersion() );

    }
}