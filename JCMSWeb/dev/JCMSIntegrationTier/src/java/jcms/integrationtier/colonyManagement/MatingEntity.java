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
import javax.persistence.Transient;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.cv.CvCrossstatusEntity;

/**
 * <b>File name:</b>  MatingEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all mating information lfor a mouse  <p>
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
@Table(name = "Mating")
@NamedQueries({
    @NamedQuery(
    name = "MatingEntity.findAll",
            query = "SELECT m FROM MatingEntity m " +
                "ORDER BY m.matingID "),
    @NamedQuery(
    name = "MatingEntity.findAllByOwner",
            query = "SELECT m FROM MatingEntity m WHERE m.owner = :fOwner " +
                "ORDER BY m.matingID "),

    @NamedQuery(
    name = "MatingEntity.findAllByOwners",
            query = "SELECT m FROM MatingEntity m WHERE m.owner IN (:lOwner) " +
                "ORDER BY m.matingID "),

    @NamedQuery(
    name = "MatingEntity.findBykey",
            query = "SELECT m FROM MatingEntity m WHERE m.matingKey = :key"),
    
    @NamedQuery(
        name = "MatingEntity.findMaxPrimaryKey", 
        query = "SELECT max(c.matingKey) FROM MatingEntity c " ),
    
    @NamedQuery(
        name = "MatingEntity.findMaxMatingID", 
        query = "SELECT max(c.matingID) FROM MatingEntity c " ),

    @NamedQuery(name = "MatingEntity.findByMatingKey", query = "SELECT m FROM MatingEntity m WHERE m.matingKey = :matingKey"),
    @NamedQuery(name = "MatingEntity.findByDam1Key", query = "SELECT m FROM MatingEntity m WHERE m.dam1Key = :dam1Key"),
    @NamedQuery(name = "MatingEntity.findByDam2Key", query = "SELECT m FROM MatingEntity m WHERE m.dam2Key = :dam2Key"),
    @NamedQuery(name = "MatingEntity.findByMatingID", query = "SELECT m FROM MatingEntity m WHERE m.matingID = :matingID"),
    @NamedQuery(name = "MatingEntity.findBySuggestedPenID", query = "SELECT m FROM MatingEntity m WHERE m.suggestedPenID = :suggestedPenID"),
    @NamedQuery(name = "MatingEntity.findByWeanTime", query = "SELECT m FROM MatingEntity m WHERE m.weanTime = :weanTime"),
    @NamedQuery(name = "MatingEntity.findByMatingDate", query = "SELECT m FROM MatingEntity m WHERE m.matingDate = :matingDate"),
    @NamedQuery(name = "MatingEntity.findByRetiredDate", query = "SELECT m FROM MatingEntity m WHERE m.retiredDate = :retiredDate"),
    @NamedQuery(name = "MatingEntity.findByGeneration", query = "SELECT m FROM MatingEntity m WHERE m.generation = :generation"),
    @NamedQuery(name = "MatingEntity.findByOwner", query = "SELECT m FROM MatingEntity m WHERE m.owner = :owner"),
    @NamedQuery(name = "MatingEntity.findByWeanNote", query = "SELECT m FROM MatingEntity m WHERE m.weanNote = :weanNote"),
    @NamedQuery(name = "MatingEntity.findByNeedsTyping", query = "SELECT m FROM MatingEntity m WHERE m.needsTyping = :needsTyping"),
    @NamedQuery(name = "MatingEntity.findByComment", query = "SELECT m FROM MatingEntity m WHERE m.comment = :comment"),
    @NamedQuery(name = "MatingEntity.findByProposedDiet", query = "SELECT m FROM MatingEntity m WHERE m.proposedDiet = :proposedDiet"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireDate", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireDate = :proposedRetireDate"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD1Diet", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD1Diet = :proposedRetireD1Diet"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD2Diet", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD2Diet = :proposedRetireD2Diet"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireSDiet", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireSDiet = :proposedRetireSDiet"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD1BrStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD1BrStatus = :proposedRetireD1BrStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD2BrStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD2BrStatus = :proposedRetireD2BrStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireSBrStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireSBrStatus = :proposedRetireSBrStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD1LfStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD1LfStatus = :proposedRetireD1LfStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireD2LfStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireD2LfStatus = :proposedRetireD2LfStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetireSLfStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetireSLfStatus = :proposedRetireSLfStatus"),
    @NamedQuery(name = "MatingEntity.findByProposedRetirePenStatus", query = "SELECT m FROM MatingEntity m WHERE m.proposedRetirePenStatus = :proposedRetirePenStatus"),
    @NamedQuery(name = "MatingEntity.findBySuggestedFirstLitterNum", query = "SELECT m FROM MatingEntity m WHERE m.suggestedFirstLitterNum = :suggestedFirstLitterNum"),
    @NamedQuery(name = "MatingEntity.findByVersion", query = "SELECT m FROM MatingEntity m WHERE m.version = :version")})

public class MatingEntity extends ITBaseEntity implements Serializable, 
ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_mating_key")
    private Integer matingKey;

    @Basic(optional = false)
    @Column(name = "_dam1_key")
    private int dam1Key;

    @Column(name = "_dam2_key")
    private Integer dam2Key;

    @Basic(optional = false)
    @Column(name = "matingID")
    private int matingID;
    
    @Basic(optional = false)
    @Column(name = "_matingType_key")
    private int matingTypeKey;

    @Column(name = "suggestedPenID")
    private Integer suggestedPenID;

    @Basic(optional = false)
    @Column(name = "weanTime")
    private boolean weanTime;
    
    @Column(name = "matingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matingDate;

    @Column(name = "retiredDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retiredDate;

    @Basic(optional = false)
    @Column(name = "generation")
    private String generation;

    @Basic(optional = false)
    @Column(name = "owner")
    private String owner;

    @Column(name = "weanNote")
    private String weanNote;

    @Basic(optional = false)
    @Column(name = "needsTyping")
    private boolean needsTyping;

    @Column(name = "comment")
    private String comment;

    @Column(name = "proposedDiet")
    private String proposedDiet;

    @Column(name = "proposedRetireDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proposedRetireDate;

    @Column(name = "proposedRetireD1Diet")
    private String proposedRetireD1Diet;

    @Column(name = "proposedRetireD2Diet")
    private String proposedRetireD2Diet;

    @Column(name = "proposedRetireSDiet")
    private String proposedRetireSDiet;

    @Column(name = "proposedRetireD1BrStatus")
    private String proposedRetireD1BrStatus;

    @Column(name = "proposedRetireD2BrStatus")
    private String proposedRetireD2BrStatus;

    @Column(name = "proposedRetireSBrStatus")
    private String proposedRetireSBrStatus;

    @Column(name = "proposedRetireD1LfStatus")
    private String proposedRetireD1LfStatus;

    @Column(name = "proposedRetireD2LfStatus")
    private String proposedRetireD2LfStatus;

    @Column(name = "proposedRetireSLfStatus")
    private String proposedRetireSLfStatus;

    @Column(name = "proposedRetirePenStatus")
    private String proposedRetirePenStatus;

    @Column(name = "suggestedFirstLitterNum")
    private Integer suggestedFirstLitterNum;
    
    @Transient
    private String dam1ID = "";
    
    @Transient
    private String dam2ID = "";
    
    @Transient
    private int matingCageID;
    
    @Transient
    private int totalFemales = 0;
    
    @Transient
    private int totalMales = 0;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matingKey")
    private Collection<LitterEntity> litterEntityCollection;

    @JoinColumn(name = "_strain_key", referencedColumnName = "_strain_key")
    @ManyToOne(optional = false)
    private StrainEntity strainKey;
    
    @JoinColumn(name = "_sire_key", referencedColumnName = "_mouse_key")
    @ManyToOne(optional = false)
    private MouseEntity sireKey;
        
    @JoinColumn(name = "_crossStatus_key", referencedColumnName = "_crossStatus_key")
    @ManyToOne
    private CvCrossstatusEntity crossStatuskey;

    public MatingEntity() {
        this.matingTypeKey = 1;
    }

    public MatingEntity(Integer matingKey) {
        this.matingKey = matingKey;
        this.matingTypeKey = 1;
    }

    public MatingEntity(Integer matingKey, int dam1Key, int matingID, boolean 
            weanTime, String generation, String owner, boolean needsTyping, int version) {
        this.matingKey = matingKey;
        this.dam1Key = dam1Key;
        this.matingID = matingID;
        this.weanTime = weanTime;
        this.generation = generation;
        this.owner = owner;
        this.needsTyping = needsTyping;
        this.version = version;
        this.matingTypeKey = 1;
    }

    public Integer getMatingKey() {
        return matingKey;
    }

    public void setMatingKey(Integer matingKey) {
        this.matingKey = matingKey;
    }

    public int getDam1Key() {
        return dam1Key;
    }

    public void setDam1Key(int dam1Key) {
        this.dam1Key = dam1Key;
    }

    public Integer getDam2Key() {
        return dam2Key;
    }

    public void setDam2Key(Integer dam2Key) {
        this.dam2Key = dam2Key;
    }

    public int getMatingID() {
        return matingID;
    }

    public void setMatingID(int matingID) {
        this.matingID = matingID;
    }

    public Integer getSuggestedPenID() {
        return suggestedPenID;
    }

    public void setSuggestedPenID(Integer suggestedPenID) {
        this.suggestedPenID = suggestedPenID;
    }

    public boolean getWeanTime() {
        return weanTime;
    }

    public void setWeanTime(boolean weanTime) {
        this.weanTime = weanTime;
    }

    public Date getMatingDate() {
        return matingDate;
    }

    public void setMatingDate(Date matingDate) {
        this.matingDate = matingDate;
    }

    public Date getRetiredDate() {
        return retiredDate;
    }

    public void setRetiredDate(Date retiredDate) {
        this.retiredDate = retiredDate;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWeanNote() {
        return weanNote;
    }

    public void setWeanNote(String weanNote) {
        this.weanNote = weanNote;
    }

    public boolean getNeedsTyping() {
        return needsTyping;
    }

    public void setNeedsTyping(boolean needsTyping) {
        this.needsTyping = needsTyping;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProposedDiet() {
        return proposedDiet;
    }

    public void setProposedDiet(String proposedDiet) {
        this.proposedDiet = proposedDiet;
    }

    public Date getProposedRetireDate() {
        return proposedRetireDate;
    }

    public void setProposedRetireDate(Date proposedRetireDate) {
        this.proposedRetireDate = proposedRetireDate;
    }

    public String getProposedRetireD1Diet() {
        return proposedRetireD1Diet;
    }

    public void setProposedRetireD1Diet(String proposedRetireD1Diet) {
        this.proposedRetireD1Diet = proposedRetireD1Diet;
    }

    public String getProposedRetireD2Diet() {
        return proposedRetireD2Diet;
    }

    public void setProposedRetireD2Diet(String proposedRetireD2Diet) {
        this.proposedRetireD2Diet = proposedRetireD2Diet;
    }

    public String getProposedRetireSDiet() {
        return proposedRetireSDiet;
    }

    public void setProposedRetireSDiet(String proposedRetireSDiet) {
        this.proposedRetireSDiet = proposedRetireSDiet;
    }

    public String getProposedRetireD1BrStatus() {
        return proposedRetireD1BrStatus;
    }

    public void setProposedRetireD1BrStatus(String proposedRetireD1BrStatus) {
        this.proposedRetireD1BrStatus = proposedRetireD1BrStatus;
    }

    public String getProposedRetireD2BrStatus() {
        return proposedRetireD2BrStatus;
    }

    public void setProposedRetireD2BrStatus(String proposedRetireD2BrStatus) {
        this.proposedRetireD2BrStatus = proposedRetireD2BrStatus;
    }

    public String getProposedRetireSBrStatus() {
        return proposedRetireSBrStatus;
    }

    public void setProposedRetireSBrStatus(String proposedRetireSBrStatus) {
        this.proposedRetireSBrStatus = proposedRetireSBrStatus;
    }

    public String getProposedRetireD1LfStatus() {
        return proposedRetireD1LfStatus;
    }

    public void setProposedRetireD1LfStatus(String proposedRetireD1LfStatus) {
        this.proposedRetireD1LfStatus = proposedRetireD1LfStatus;
    }

    public String getProposedRetireD2LfStatus() {
        return proposedRetireD2LfStatus;
    }

    public void setProposedRetireD2LfStatus(String proposedRetireD2LfStatus) {
        this.proposedRetireD2LfStatus = proposedRetireD2LfStatus;
    }

    public String getProposedRetireSLfStatus() {
        return proposedRetireSLfStatus;
    }

    public void setProposedRetireSLfStatus(String proposedRetireSLfStatus) {
        this.proposedRetireSLfStatus = proposedRetireSLfStatus;
    }

    public String getProposedRetirePenStatus() {
        return proposedRetirePenStatus;
    }

    public void setProposedRetirePenStatus(String proposedRetirePenStatus) {
        this.proposedRetirePenStatus = proposedRetirePenStatus;
    }

    public Integer getSuggestedFirstLitterNum() {
        return suggestedFirstLitterNum;
    }

    public void setSuggestedFirstLitterNum(Integer suggestedFirstLitterNum) {
        this.suggestedFirstLitterNum = suggestedFirstLitterNum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<LitterEntity> getLitterEntityCollection() {
        return litterEntityCollection;
    }

    public void setLitterEntityCollection(Collection<LitterEntity> litterEntityCollection) {
        this.litterEntityCollection = litterEntityCollection;
    }

    public StrainEntity getStrainKey() {
        return strainKey;
    }

    public void setStrainKey(StrainEntity strainKey) {
        this.strainKey = strainKey;
    }

    public MouseEntity getSireKey() {
        return sireKey;
    }

    public void setSireKey(MouseEntity sireKey) {
        this.sireKey = sireKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matingKey != null ? matingKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatingEntity)) {
            return false;
        }
        MatingEntity other = (MatingEntity) object;
        if ((this.matingKey == null && other.matingKey != null) || (this.matingKey != null && !this.matingKey.equals(other.matingKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.MatingEntity[matingKey=" + matingKey + "]";
    }

    @Override
    public Integer getKey() {
        return matingKey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }

    /**
     * @return the crossStatuskey
     */
    public CvCrossstatusEntity getCrossStatuskey() {
        return crossStatuskey;
    }

    /**
     * @param crossStatuskey the crossStatuskey to set
     */
    public void setCrossStatuskey(CvCrossstatusEntity crossStatuskey) {
        this.crossStatuskey = crossStatuskey;
    }

    /**
     * @return the dam1ID
     */
    public String getDam1ID() {
        return dam1ID;
    }

    /**
     * @param dam1ID the dam1ID to set
     */
    public void setDam1ID(String dam1ID) {
        this.dam1ID = dam1ID;
    }

    /**
     * @return the dam2ID
     */
    public String getDam2ID() {
        return dam2ID;
    }

    /**
     * @param dam2ID the dam2ID to set
     */
    public void setDam2ID(String dam2ID) {
        this.dam2ID = dam2ID;
    }

    /**
     * @return the totalFemales
     */
    public int getTotalFemales() {
        return totalFemales;
    }

    /**
     * @param totalFemales the totalFemales to set
     */
    public void setTotalFemales(int totalFemales) {
        this.totalFemales = totalFemales;
    }

    /**
     * @return the totalMales
     */
    public int getTotalMales() {
        return totalMales;
    }

    /**
     * @param totalMales the totalMales to set
     */
    public void setTotalMales(int totalMales) {
        this.totalMales = totalMales;
    }

    /**
     * @return the matingTypeKey
     */
    public int getMatingTypeKey() {
        return matingTypeKey;
    }

    /**
     * @param matingTypeKey the matingTypeKey to set
     */
    public void setMatingTypeKey(int matingTypeKey) {
        this.matingTypeKey = matingTypeKey;
    }

    /**
     * @return the matingCageID
     */
    public int getMatingCageID() {
        return matingCageID;
    }

    /**
     * @param matingCageID the matingCageID to set
     */
    public void setMatingCageID(int matingCageID) {
        this.matingCageID = matingCageID;
    }
}
