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

package jcms.integrationtier.dto;

import jcms.integrationtier.base.ITBaseDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author cnh
 */
public class AdminStrainDTO extends ITBaseDTO {
    private String strainKey = "0";
    private String strainName = "";
    private String nickName = "";
    private String formalName = "";
    private String strainStatus = "";
    private String tagMin = "";
    private String tagMax = "";
    private String lastTag = "";
    private String jrNum = "";
    private String feNumEmbryos = "";
    private String feMaxGen = "";
    private String fsNumMales = "";
    private String fsMaxGen = "";
    private String foNumFemales = "";
    private String foMaxGen = "";
    private String cardColor = "";
    private String strainType = "";
    private String comment = "";
    private String lineViabilityYellowMinNumMales = "";
    private String lineViabilityYellowMinNumFemales = "";
    private String lineViabilityYellowMaxAgeMales = "";
    private String lineViabilityYellowMaxAgeFemales = "";
    private String lineViabilityRedMinNumMales = "";
    private String lineViabilityRedMinNumFemales = "";
    private String lineViabilityRedMaxAgeMales = "";
    private String lineViabilityRedMaxAgeFemales = "";
    private Boolean isActive = true;
    private String section_ = "";
    private String version = "";

    private String jrNumFrom = "";
    private String jrNumTo = "";
    
    public UserEntity user = null;
    
    
    public AdminStrainDTO() {
        
    }
    
    public Boolean isInsert() {
        return (this.getStrainKey().equalsIgnoreCase("0") || this.getStrainKey().equalsIgnoreCase("")) ;
    }
    
    public void clear() {
        this.setStrainName("");
        this.setJrNumFrom("");
        this.setJrNumTo("");
        this.setIsActive(true);
    }
    
    public Boolean getDisableDelete() {
        Boolean disabled = false;
        return disabled;
    }

    /**
     * @return the strainKey
     */
    public String getStrainKey() {
        return strainKey;
    }

    /**
     * @param strainKey the strainKey to set
     */
    public void setStrainKey(String strainKey) {
        this.strainKey = strainKey;
    }

    /**
     * @return the strainName
     */
    public String getStrainName() {
        return strainName;
    }

    /**
     * @param strainName the strainName to set
     */
    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return the formalName
     */
    public String getFormalName() {
        return formalName;
    }

    /**
     * @param formalName the formalName to set
     */
    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    /**
     * @return the strainStatus
     */
    public String getStrainStatus() {
        return strainStatus;
    }

    /**
     * @param strainStatus the strainStatus to set
     */
    public void setStrainStatus(String strainStatus) {
        this.strainStatus = strainStatus;
    }

    /**
     * @return the tagMin
     */
    public String getTagMin() {
        return tagMin;
    }

    /**
     * @param tagMin the tagMin to set
     */
    public void setTagMin(String tagMin) {
        this.tagMin = tagMin;
    }

    /**
     * @return the tagMax
     */
    public String getTagMax() {
        return tagMax;
    }

    /**
     * @param tagMax the tagMax to set
     */
    public void setTagMax(String tagMax) {
        this.tagMax = tagMax;
    }

    /**
     * @return the lastTag
     */
    public String getLastTag() {
        return lastTag;
    }

    /**
     * @param lastTag the lastTag to set
     */
    public void setLastTag(String lastTag) {
        this.lastTag = lastTag;
    }

    /**
     * @return the jrNum
     */
    public String getJrNum() {
        return jrNum;
    }

    /**
     * @param jrNum the jrNum to set
     */
    public void setJrNum(String jrNum) {
        this.jrNum = jrNum;
    }

    /**
     * @return the feNumEmbryos
     */
    public String getFeNumEmbryos() {
        return feNumEmbryos;
    }

    /**
     * @param feNumEmbryos the feNumEmbryos to set
     */
    public void setFeNumEmbryos(String feNumEmbryos) {
        this.feNumEmbryos = feNumEmbryos;
    }

    /**
     * @return the feMaxGen
     */
    public String getFeMaxGen() {
        return feMaxGen;
    }

    /**
     * @param feMaxGen the feMaxGen to set
     */
    public void setFeMaxGen(String feMaxGen) {
        this.feMaxGen = feMaxGen;
    }

    /**
     * @return the fsNumMales
     */
    public String getFsNumMales() {
        return fsNumMales;
    }

    /**
     * @param fsNumMales the fsNumMales to set
     */
    public void setFsNumMales(String fsNumMales) {
        this.fsNumMales = fsNumMales;
    }

    /**
     * @return the fsMaxGen
     */
    public String getFsMaxGen() {
        return fsMaxGen;
    }

    /**
     * @param fsMaxGen the fsMaxGen to set
     */
    public void setFsMaxGen(String fsMaxGen) {
        this.fsMaxGen = fsMaxGen;
    }

    /**
     * @return the foNumFemales
     */
    public String getFoNumFemales() {
        return foNumFemales;
    }

    /**
     * @param foNumFemales the foNumFemales to set
     */
    public void setFoNumFemales(String foNumFemales) {
        this.foNumFemales = foNumFemales;
    }

    /**
     * @return the foMaxGen
     */
    public String getFoMaxGen() {
        return foMaxGen;
    }

    /**
     * @param foMaxGen the foMaxGen to set
     */
    public void setFoMaxGen(String foMaxGen) {
        this.foMaxGen = foMaxGen;
    }

    /**
     * @return the cardColor
     */
    public String getCardColor() {
        return cardColor;
    }

    /**
     * @param cardColor the cardColor to set
     */
    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    /**
     * @return the strainType
     */
    public String getStrainType() {
        return strainType;
    }

    /**
     * @param strainType the strainType to set
     */
    public void setStrainType(String strainType) {
        this.strainType = strainType;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the lineViabilityYellowMinNumMales
     */
    public String getLineViabilityYellowMinNumMales() {
        return lineViabilityYellowMinNumMales;
    }

    /**
     * @param lineViabilityYellowMinNumMales the lineViabilityYellowMinNumMales to set
     */
    public void setLineViabilityYellowMinNumMales(String lineViabilityYellowMinNumMales) {
        this.lineViabilityYellowMinNumMales = lineViabilityYellowMinNumMales;
    }

    /**
     * @return the lineViabilityYellowMinNumFemales
     */
    public String getLineViabilityYellowMinNumFemales() {
        return lineViabilityYellowMinNumFemales;
    }

    /**
     * @param lineViabilityYellowMinNumFemales the lineViabilityYellowMinNumFemales to set
     */
    public void setLineViabilityYellowMinNumFemales(String lineViabilityYellowMinNumFemales) {
        this.lineViabilityYellowMinNumFemales = lineViabilityYellowMinNumFemales;
    }

    /**
     * @return the lineViabilityYellowMaxAgeMales
     */
    public String getLineViabilityYellowMaxAgeMales() {
        return lineViabilityYellowMaxAgeMales;
    }

    /**
     * @param lineViabilityYellowMaxAgeMales the lineViabilityYellowMaxAgeMales to set
     */
    public void setLineViabilityYellowMaxAgeMales(String lineViabilityYellowMaxAgeMales) {
        this.lineViabilityYellowMaxAgeMales = lineViabilityYellowMaxAgeMales;
    }

    /**
     * @return the lineViabilityYellowMaxAgeFemales
     */
    public String getLineViabilityYellowMaxAgeFemales() {
        return lineViabilityYellowMaxAgeFemales;
    }

    /**
     * @param lineViabilityYellowMaxAgeFemales the lineViabilityYellowMaxAgeFemales to set
     */
    public void setLineViabilityYellowMaxAgeFemales(String lineViabilityYellowMaxAgeFemales) {
        this.lineViabilityYellowMaxAgeFemales = lineViabilityYellowMaxAgeFemales;
    }

    /**
     * @return the lineViabilityRedMinNumMales
     */
    public String getLineViabilityRedMinNumMales() {
        return lineViabilityRedMinNumMales;
    }

    /**
     * @param lineViabilityRedMinNumMales the lineViabilityRedMinNumMales to set
     */
    public void setLineViabilityRedMinNumMales(String lineViabilityRedMinNumMales) {
        this.lineViabilityRedMinNumMales = lineViabilityRedMinNumMales;
    }

    /**
     * @return the lineViabilityRedMinNumFemales
     */
    public String getLineViabilityRedMinNumFemales() {
        return lineViabilityRedMinNumFemales;
    }

    /**
     * @param lineViabilityRedMinNumFemales the lineViabilityRedMinNumFemales to set
     */
    public void setLineViabilityRedMinNumFemales(String lineViabilityRedMinNumFemales) {
        this.lineViabilityRedMinNumFemales = lineViabilityRedMinNumFemales;
    }

    /**
     * @return the lineViabilityRedMaxAgeMales
     */
    public String getLineViabilityRedMaxAgeMales() {
        return lineViabilityRedMaxAgeMales;
    }

    /**
     * @param lineViabilityRedMaxAgeMales the lineViabilityRedMaxAgeMales to set
     */
    public void setLineViabilityRedMaxAgeMales(String lineViabilityRedMaxAgeMales) {
        this.lineViabilityRedMaxAgeMales = lineViabilityRedMaxAgeMales;
    }

    /**
     * @return the lineViabilityRedMaxAgeFemales
     */
    public String getLineViabilityRedMaxAgeFemales() {
        return lineViabilityRedMaxAgeFemales;
    }

    /**
     * @param lineViabilityRedMaxAgeFemales the lineViabilityRedMaxAgeFemales to set
     */
    public void setLineViabilityRedMaxAgeFemales(String lineViabilityRedMaxAgeFemales) {
        this.lineViabilityRedMaxAgeFemales = lineViabilityRedMaxAgeFemales;
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the section_
     */
    public String getSection_() {
        return section_;
    }

    /**
     * @param section_ the section_ to set
     */
    public void setSection_(String section_) {
        this.section_ = section_;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the jrNumFrom
     */
    public String getJrNumFrom() {
        return jrNumFrom;
    }

    /**
     * @param jrNumFrom the jrNumFrom to set
     */
    public void setJrNumFrom(String jrNumFrom) {
        this.jrNumFrom = jrNumFrom;
    }

    /**
     * @return the jrNumTo
     */
    public String getJrNumTo() {
        return jrNumTo;
    }

    /**
     * @param jrNumTo the jrNumTo to set
     */
    public void setJrNumTo(String jrNumTo) {
        this.jrNumTo = jrNumTo;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
}
