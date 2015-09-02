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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "Strain")
@NamedQueries({
    @NamedQuery(
    name = "StrainEntity.findAll",
            query = "SELECT s FROM StrainEntity s " +
                "ORDER BY s.strainName "),

    @NamedQuery(
    name = "StrainEntity.findAllByOwner",
            query = "SELECT DISTINCT s FROM StrainEntity s, MouseEntity m, MatingEntity ma " +
                    "WHERE (s.strainKey = m.strainKey OR s.strainKey = ma.strainKey) " +
                    "AND (m.owner = :fOwner OR ma.owner = :fOwner) " +
                "ORDER BY s.strainName "),

    @NamedQuery(
    name = "StrainEntity.findAllMouseStrainsByOwners",
            query = "SELECT s FROM StrainEntity s " +
                    "WHERE EXISTS( " +
                    "SELECT 1 FROM MouseEntity m " +
                    "WHERE m.owner in (:lOwner) " +
                    "AND m.strainKey = s.strainKey) " +
                    "ORDER BY s.strainName "),

    @NamedQuery(
    name = "StrainEntity.findAllMatingStrainsByOwners",
            query = "SELECT s FROM StrainEntity s " +
                    "WHERE EXISTS( " +
                    "SELECT 1 FROM MatingEntity m " +
                    "WHERE m.owner in (:lOwner) " +
                    "AND m.strainKey = s.strainKey) " +
                    "ORDER BY s.strainName "),

    @NamedQuery(
    name = "StrainEntity.findBykey",
            query = "SELECT s FROM StrainEntity s WHERE s.strainKey = :key"),

    @NamedQuery(name = "StrainEntity.findByStrainKey", query = "SELECT s FROM StrainEntity s WHERE s.strainKey = :strainKey"),
    @NamedQuery(name = "StrainEntity.findByStrainName", query = "SELECT s FROM StrainEntity s WHERE s.strainName = :strainName"),
    @NamedQuery(name = "StrainEntity.findByIsActive", query = "SELECT s FROM StrainEntity s WHERE s.isActive = -1"),    
    @NamedQuery(name = "StrainEntity.findByNickname", query = "SELECT s FROM StrainEntity s WHERE s.nickname = :nickname"),
    @NamedQuery(name = "StrainEntity.findByFormalName", query = "SELECT s FROM StrainEntity s WHERE s.formalName = :formalName"),
    @NamedQuery(name = "StrainEntity.findByStrainStatus", query = "SELECT s FROM StrainEntity s WHERE s.strainStatus = :strainStatus"),
    @NamedQuery(name = "StrainEntity.findByTagMin", query = "SELECT s FROM StrainEntity s WHERE s.tagMin = :tagMin"),
    @NamedQuery(name = "StrainEntity.findByTagMax", query = "SELECT s FROM StrainEntity s WHERE s.tagMax = :tagMax"),
    @NamedQuery(name = "StrainEntity.findByLastTag", query = "SELECT s FROM StrainEntity s WHERE s.lastTag = :lastTag"),
    @NamedQuery(name = "StrainEntity.findByJrNum", query = "SELECT s FROM StrainEntity s WHERE s.jrNum = :jrNum"),
    @NamedQuery(name = "StrainEntity.findByFeNumEmbryos", query = "SELECT s FROM StrainEntity s WHERE s.feNumEmbryos = :feNumEmbryos"),
    @NamedQuery(name = "StrainEntity.findByFeMaxGen", query = "SELECT s FROM StrainEntity s WHERE s.feMaxGen = :feMaxGen"),
    @NamedQuery(name = "StrainEntity.findByFsNumMales", query = "SELECT s FROM StrainEntity s WHERE s.fsNumMales = :fsNumMales"),
    @NamedQuery(name = "StrainEntity.findByFsMaxGen", query = "SELECT s FROM StrainEntity s WHERE s.fsMaxGen = :fsMaxGen"),
    @NamedQuery(name = "StrainEntity.findByFoNumFemales", query = "SELECT s FROM StrainEntity s WHERE s.foNumFemales = :foNumFemales"),
    @NamedQuery(name = "StrainEntity.findByFoMaxGen", query = "SELECT s FROM StrainEntity s WHERE s.foMaxGen = :foMaxGen"),
    @NamedQuery(name = "StrainEntity.findBySection", query = "SELECT s FROM StrainEntity s WHERE s.section = :section"),
    @NamedQuery(name = "StrainEntity.findByCardColor", query = "SELECT s FROM StrainEntity s WHERE s.cardColor = :cardColor"),
    @NamedQuery(name = "StrainEntity.findByStrainType", query = "SELECT s FROM StrainEntity s WHERE s.strainType = :strainType"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityYellowMinNumMales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityYellowMinNumMales = :lineViabilityYellowMinNumMales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityYellowMinNumFemales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityYellowMinNumFemales = :lineViabilityYellowMinNumFemales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityYellowMaxAgeMales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityYellowMaxAgeMales = :lineViabilityYellowMaxAgeMales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityYellowMaxAgeFemales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityYellowMaxAgeFemales = :lineViabilityYellowMaxAgeFemales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityRedMinNumMales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityRedMinNumMales = :lineViabilityRedMinNumMales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityRedMinNumFemales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityRedMinNumFemales = :lineViabilityRedMinNumFemales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityRedMaxAgeMales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityRedMaxAgeMales = :lineViabilityRedMaxAgeMales"),
    @NamedQuery(name = "StrainEntity.findByLineViabilityRedMaxAgeFemales", query = "SELECT s FROM StrainEntity s WHERE s.lineViabilityRedMaxAgeFemales = :lineViabilityRedMaxAgeFemales"),
    @NamedQuery(name = "StrainEntity.findByVersion", query = "SELECT s FROM StrainEntity s WHERE s.version = :version")})

public class StrainEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_strain_key")
    private Integer strainKey;

    @Basic(optional = false)
    @Column(name = "strainName")
    private String strainName;     

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "formalName")
    private String formalName;
    
    @Column(name = "isActive")
    private Short isActive;

    @Basic(optional = false)
    @Column(name = "strainStatus")
    private String strainStatus;

    @Column(name = "tagMin")
    private String tagMin;

    @Column(name = "tagMax")
    private String tagMax;

    @Column(name = "lastTag")
    private String lastTag;

    @Column(name = "jrNum")
    private Integer jrNum;

    @Basic(optional = false)
    @Column(name = "feNumEmbryos")
    private short feNumEmbryos;

    @Column(name = "feMaxGen")
    private String feMaxGen;

    @Basic(optional = false)
    @Column(name = "fsNumMales")
    private short fsNumMales;

    @Column(name = "fsMaxGen")
    private String fsMaxGen;

    @Basic(optional = false)
    @Column(name = "foNumFemales")
    private short foNumFemales;

    @Column(name = "foMaxGen")
    private String foMaxGen;

    @Column(name = "section_")
    private String section;

    @Column(name = "cardColor")
    private String cardColor;

    @Column(name = "strainType")
    private String strainType;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "lineViabilityYellowMinNumMales")
    private Integer lineViabilityYellowMinNumMales;

    @Column(name = "lineViabilityYellowMinNumFemales")
    private Integer lineViabilityYellowMinNumFemales;

    @Column(name = "lineViabilityYellowMaxAgeMales")
    private Integer lineViabilityYellowMaxAgeMales;

    @Column(name = "lineViabilityYellowMaxAgeFemales")
    private Integer lineViabilityYellowMaxAgeFemales;

    @Column(name = "lineViabilityRedMinNumMales")
    private Integer lineViabilityRedMinNumMales;

    @Column(name = "lineViabilityRedMinNumFemales")
    private Integer lineViabilityRedMinNumFemales;

    @Column(name = "lineViabilityRedMaxAgeMales")
    private Integer lineViabilityRedMaxAgeMales;

    @Column(name = "lineViabilityRedMaxAgeFemales")
    private Integer lineViabilityRedMaxAgeFemales;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "strainKey")
    private Collection<MouseEntity> mouseEntityCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "strainKey")
    private Collection<MatingEntity> matingEntityCollection;

    public StrainEntity() {
    }

    public StrainEntity(Integer strainKey) {
        this.strainKey = strainKey;
    }

    public StrainEntity(Integer strainKey, String strainName, String strainStatus, short feNumEmbryos, short fsNumMales, short foNumFemales, int version) {
        this.strainKey = strainKey;
        this.strainName = strainName;
        this.strainStatus = strainStatus;
        this.feNumEmbryos = feNumEmbryos;
        this.fsNumMales = fsNumMales;
        this.foNumFemales = foNumFemales;
        this.version = version;
    }

    public Integer getStrainKey() {
        return strainKey;
    }

    public void setStrainKey(Integer strainKey) {
        this.strainKey = strainKey;
    }

    public String getStrainName() {
        return strainName;
    }

    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getStrainStatus() {
        return strainStatus;
    }

    public void setStrainStatus(String strainStatus) {
        this.strainStatus = strainStatus;
    }

    public String getTagMin() {
        return tagMin;
    }

    public void setTagMin(String tagMin) {
        this.tagMin = tagMin;
    }

    public String getTagMax() {
        return tagMax;
    }

    public void setTagMax(String tagMax) {
        this.tagMax = tagMax;
    }

    public String getLastTag() {
        return lastTag;
    }

    public void setLastTag(String lastTag) {
        this.lastTag = lastTag;
    }

    public Integer getJrNum() {
        return jrNum;
    }

    public void setJrNum(Integer jrNum) {
        this.jrNum = jrNum;
    }

    public short getFeNumEmbryos() {
        return feNumEmbryos;
    }

    public void setFeNumEmbryos(short feNumEmbryos) {
        this.feNumEmbryos = feNumEmbryos;
    }

    public String getFeMaxGen() {
        return feMaxGen;
    }

    public void setFeMaxGen(String feMaxGen) {
        this.feMaxGen = feMaxGen;
    }

    public short getFsNumMales() {
        return fsNumMales;
    }

    public void setFsNumMales(short fsNumMales) {
        this.fsNumMales = fsNumMales;
    }

    public String getFsMaxGen() {
        return fsMaxGen;
    }

    public void setFsMaxGen(String fsMaxGen) {
        this.fsMaxGen = fsMaxGen;
    }

    public short getFoNumFemales() {
        return foNumFemales;
    }

    public void setFoNumFemales(short foNumFemales) {
        this.foNumFemales = foNumFemales;
    }

    public String getFoMaxGen() {
        return foMaxGen;
    }

    public void setFoMaxGen(String foMaxGen) {
        this.foMaxGen = foMaxGen;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    public String getStrainType() {
        return strainType;
    }

    public void setStrainType(String strainType) {
        this.strainType = strainType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLineViabilityYellowMinNumMales() {
        return lineViabilityYellowMinNumMales;
    }

    public void setLineViabilityYellowMinNumMales(Integer lineViabilityYellowMinNumMales) {
        this.lineViabilityYellowMinNumMales = lineViabilityYellowMinNumMales;
    }

    public Integer getLineViabilityYellowMinNumFemales() {
        return lineViabilityYellowMinNumFemales;
    }

    public void setLineViabilityYellowMinNumFemales(Integer lineViabilityYellowMinNumFemales) {
        this.lineViabilityYellowMinNumFemales = lineViabilityYellowMinNumFemales;
    }

    public Integer getLineViabilityYellowMaxAgeMales() {
        return lineViabilityYellowMaxAgeMales;
    }

    public void setLineViabilityYellowMaxAgeMales(Integer lineViabilityYellowMaxAgeMales) {
        this.lineViabilityYellowMaxAgeMales = lineViabilityYellowMaxAgeMales;
    }

    public Integer getLineViabilityYellowMaxAgeFemales() {
        return lineViabilityYellowMaxAgeFemales;
    }

    public void setLineViabilityYellowMaxAgeFemales(Integer lineViabilityYellowMaxAgeFemales) {
        this.lineViabilityYellowMaxAgeFemales = lineViabilityYellowMaxAgeFemales;
    }

    public Integer getLineViabilityRedMinNumMales() {
        return lineViabilityRedMinNumMales;
    }

    public void setLineViabilityRedMinNumMales(Integer lineViabilityRedMinNumMales) {
        this.lineViabilityRedMinNumMales = lineViabilityRedMinNumMales;
    }

    public Integer getLineViabilityRedMinNumFemales() {
        return lineViabilityRedMinNumFemales;
    }

    public void setLineViabilityRedMinNumFemales(Integer lineViabilityRedMinNumFemales) {
        this.lineViabilityRedMinNumFemales = lineViabilityRedMinNumFemales;
    }

    public Integer getLineViabilityRedMaxAgeMales() {
        return lineViabilityRedMaxAgeMales;
    }

    public void setLineViabilityRedMaxAgeMales(Integer lineViabilityRedMaxAgeMales) {
        this.lineViabilityRedMaxAgeMales = lineViabilityRedMaxAgeMales;
    }

    public Integer getLineViabilityRedMaxAgeFemales() {
        return lineViabilityRedMaxAgeFemales;
    }

    public void setLineViabilityRedMaxAgeFemales(Integer lineViabilityRedMaxAgeFemales) {
        this.lineViabilityRedMaxAgeFemales = lineViabilityRedMaxAgeFemales;
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

    public Collection<MatingEntity> getMatingEntityCollection() {
        return matingEntityCollection;
    }

    public void setMatingEntityCollection(Collection<MatingEntity> matingEntityCollection) {
        this.matingEntityCollection = matingEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (strainKey != null ? strainKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StrainEntity)) {
            return false;
        }
        StrainEntity other = (StrainEntity) object;
        if ((this.strainKey == null && other.strainKey != null) || (this.strainKey != null && !this.strainKey.equals(other.strainKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.StrainEntity[strainKey=" + strainKey + "]";
    }

    @Override
    public Integer getKey() {
        return strainKey;
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
        System.out.println("\tPK" + "\t" + this.getStrainKey() );
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tPen ID" + "\t" + this.getStrainName() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }

    /**
     * @return the isActive
     */
    public Short getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }
    
}