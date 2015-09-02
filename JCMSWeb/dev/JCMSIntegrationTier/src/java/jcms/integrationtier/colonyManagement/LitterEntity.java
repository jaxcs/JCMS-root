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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.cv.CvLitterTypeEntity;
import jcms.integrationtier.cv.CvTheilerStageEntity;

/**
 * <b>File name:</b>  LitterEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all litter information like litter number, litter name
 * etc  <p>
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
@Table(name = "Litter")
@NamedQueries({
    @NamedQuery(
    name = "LitterEntity.findAll",
    query = "SELECT l FROM LitterEntity l " +
                "ORDER BY l.litterID "),

    @NamedQuery(
    name = "LitterEntity.findAllByOwner",
    query = "SELECT DISTINCT l FROM LitterEntity l, MatingEntity ma, MouseEntity m " +
            "WHERE (l.matingKey = ma.matingKey OR l.litterKey = m.litterKey) " +
            "AND (m.owner = :fOwner OR ma.owner = :fOwner) " +
            "ORDER BY l.litterID "),

    @NamedQuery(
    name = "LitterEntity.findAllByOwners",
    query = "SELECT l FROM LitterEntity l " +
            "WHERE EXISTS ( " +
            "SELECT 1 FROM MouseEntity m " +
            "WHERE m.owner in (:lOwner) " +
            "AND l.litterKey = m.litterKey) " +
            "ORDER BY l.litterID "),

            /*query = "SELECT l FROM LitterEntity l " +
            "JOIN l.MatingEntity m " +
            "WHERE m.owner = :fOwner " +
            "ORDER BY l.litterID "),
            //"l.matingKey = m.matingKey AND "*/
    
    @NamedQuery(
    name = "LitterEntity.findMaxLitterKeyByMating",
    query = "SELECT MAX(l.litterKey) FROM LitterEntity l WHERE l.matingKey = :lmatingKey"),
    
    @NamedQuery(
    name = "LitterEntity.findMaxLitterIDByMating",
    query = "SELECT MAX(l.litterID) FROM LitterEntity l WHERE l.matingKey = :lmatingKey"),              
        
    @NamedQuery(
    name = "LitterEntity.findBykey",
    query = "SELECT l FROM LitterEntity l WHERE l.litterKey = :key"),
    
    @NamedQuery(
        name = "LitterEntity.findMaxPrimaryKey", 
        query = "SELECT max(l.litterKey) FROM LitterEntity l " ),
    
    @NamedQuery(
    name = "LitterEntity.findLittersByMatingkey",
    query = "SELECT l FROM LitterEntity l WHERE l.matingKey = :mKey"),

    @NamedQuery(name = "LitterEntity.findByLitterKey", query = "SELECT l FROM LitterEntity l WHERE l.litterKey = :litterKey"),
    @NamedQuery(name = "LitterEntity.findByLitterID", query = "SELECT l FROM LitterEntity l WHERE l.litterID = :litterID"),
    @NamedQuery(name = "LitterEntity.findByTotalBorn", query = "SELECT l FROM LitterEntity l WHERE l.totalBorn = :totalBorn"),
    @NamedQuery(name = "LitterEntity.findByBirthDate", query = "SELECT l FROM LitterEntity l WHERE l.birthDate = :birthDate"),
    @NamedQuery(name = "LitterEntity.findByNumFemale", query = "SELECT l FROM LitterEntity l WHERE l.numFemale = :numFemale"),
    @NamedQuery(name = "LitterEntity.findByNumMale", query = "SELECT l FROM LitterEntity l WHERE l.numMale = :numMale"),
    @NamedQuery(name = "LitterEntity.findByWeanDate", query = "SELECT l FROM LitterEntity l WHERE l.weanDate = :weanDate"),
    @NamedQuery(name = "LitterEntity.findByTagDate", query = "SELECT l FROM LitterEntity l WHERE l.tagDate = :tagDate"),
    @NamedQuery(name = "LitterEntity.findByStatus", query = "SELECT l FROM LitterEntity l WHERE l.status = :status"),
    @NamedQuery(name = "LitterEntity.findByVersion", query = "SELECT l FROM LitterEntity l WHERE l.version = :version")})

public class LitterEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_litter_key")
    private Integer litterKey;

    @Basic(optional = false)
    @Column(name = "litterID")
    private String litterID;

    @Basic(optional = false)
    @Column(name = "totalBorn")
    private short totalBorn;

    @Basic(optional = false)
    @Column(name = "birthDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "numFemale")
    private Short numFemale;

    @Column(name = "numMale")
    private Short numMale;

    @Column(name = "weanDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date weanDate;

    @Column(name = "tagDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tagDate;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @Column(name = "harvestDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date harvestDate;
    
    @Column(name = "numberHarvested")
    private Integer numberHarvested;
    
    @Column(name = "numberBornDead")
    private Integer numberBornDead;
    
    @Column(name = "numberCulledAtWean")
    private Integer numberCulledAtWean;
    
    @Column(name = "numberMissingAtWean")
    private Integer numberMissingAtWean;
    
    @OneToMany(mappedBy = "litterKey")
    private Collection<MouseEntity> mouseEntityCollection;
    
    @JoinColumn(name = "_mating_key", referencedColumnName = "_mating_key")
    @ManyToOne(optional = false)
    private MatingEntity matingKey;

    @JoinColumn(name = "_litterType_key", referencedColumnName = "_litterType_key")
    @ManyToOne(optional = false)
    private CvLitterTypeEntity litterTypekey;
    
    @JoinColumn(name = "_theilerStage_key", referencedColumnName = "_theilerStage_key")
    @ManyToOne(optional = true)
    private CvTheilerStageEntity theilerStagekey;
    
    public LitterEntity() {
    }

    public LitterEntity(Integer litterKey) {
        this.litterKey = litterKey;
    }

    public LitterEntity(Integer litterKey, String litterID, short totalBorn, Date birthDate, String status, int version) {
        this.litterKey = litterKey;
        this.litterID = litterID;
        this.totalBorn = totalBorn;
        this.birthDate = birthDate;
        this.status = status;
        this.version = version;
    }

    public Integer getLitterKey() {
        return litterKey;
    }

    public void setLitterKey(Integer litterKey) {
        this.litterKey = litterKey;
    }

    public String getLitterID() {
        return litterID;
    }

    public void setLitterID(String litterID) {
        this.litterID = litterID;
    }

    public short getTotalBorn() {
        return totalBorn;
    }

    public void setTotalBorn(short totalBorn) {
        this.totalBorn = totalBorn;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Short getNumFemale() {
        return numFemale;
    }

    public void setNumFemale(Short numFemale) {
        this.numFemale = numFemale;
    }

    public Short getNumMale() {
        return numMale;
    }

    public void setNumMale(Short numMale) {
        this.numMale = numMale;
    }

    public Date getWeanDate() {
        return weanDate;
    }

    public void setWeanDate(Date weanDate) {
        this.weanDate = weanDate;
    }

    public Date getTagDate() {
        return tagDate;
    }

    public void setTagDate(Date tagDate) {
        this.tagDate = tagDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<MouseEntity> getMouseEntityCollection() {
        return mouseEntityCollection;
    }

    public void setMouseEntityCollection(Collection<MouseEntity> mouseEntityCollection) {
        this.mouseEntityCollection = mouseEntityCollection;
    }

    public MatingEntity getMatingKey() {
        return matingKey;
    }

    public void setMatingKey(MatingEntity matingKey) {
        this.matingKey = matingKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (litterKey != null ? litterKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LitterEntity)) {
            return false;
        }
        LitterEntity other = (LitterEntity) object;
        if ((this.litterKey == null && other.litterKey != null) || (this.litterKey != null && !this.litterKey.equals(other.litterKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.LitterEntity[litterKey=" + litterKey + "]";
    }

    @Override
    public Integer getKey() {
        return litterKey;
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
        System.out.println("\tKey" + "\t" + this.getLitterKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tID" + "\t" + this.getLitterID());
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }

    /**
     * @return the litterTypekey
     */
    public CvLitterTypeEntity getLitterTypekey() {
        return litterTypekey;
    }

    /**
     * @param litterTypekey the litterTypekey to set
     */
    public void setLitterTypekey(CvLitterTypeEntity litterTypekey) {
        this.litterTypekey = litterTypekey;
    }

    /**
     * @return the harvestDate
     */
    public Date getHarvestDate() {
        return harvestDate;
    }

    /**
     * @param harvestDate the harvestDate to set
     */
    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }

    /**
     * @return the numberHarvested
     */
    public Integer getNumberHarvested() {
        return numberHarvested;
    }

    /**
     * @param numberHarvested the numberHarvested to set
     */
    public void setNumberHarvested(Integer numberHarvested) {
        this.numberHarvested = numberHarvested;
    }

    /**
     * @return the theilerStagekey
     */
    public CvTheilerStageEntity getTheilerStagekey() {
        return theilerStagekey;
    }

    /**
     * @param theilerStagekey the theilerStagekey to set
     */
    public void setTheilerStagekey(CvTheilerStageEntity theilerStagekey) {
        this.theilerStagekey = theilerStagekey;
    }

    /**
     * @return the numberBornDead
     */
    public Integer getNumberBornDead() {
        return numberBornDead;
    }

    /**
     * @param numberBornDead the numberBornDead to set
     */
    public void setNumberBornDead(Integer numberBornDead) {
        this.numberBornDead = numberBornDead;
    }

    /**
     * @return the numberCulledAtWean
     */
    public Integer getNumberCulledAtWean() {
        return numberCulledAtWean;
    }

    /**
     * @param numberCulledAtWean the numberCulledAtWean to set
     */
    public void setNumberCulledAtWean(Integer numberCulledAtWean) {
        this.numberCulledAtWean = numberCulledAtWean;
    }

    /**
     * @return the numberMissingAtWean
     */
    public Integer getNumberMissingAtWean() {
        return numberMissingAtWean;
    }

    /**
     * @param numberMissingAtWean the numberMissingAtWean to set
     */
    public void setNumberMissingAtWean(Integer numberMissingAtWean) {
        this.numberMissingAtWean = numberMissingAtWean;
    }
}