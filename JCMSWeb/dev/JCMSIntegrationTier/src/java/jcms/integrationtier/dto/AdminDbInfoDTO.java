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

/**
 *
 * @author cnh
 */
public class AdminDbInfoDTO extends ITBaseDTO {
    private String dbInfoKey = "";
    private String dbVers = "";
    private String versDate = "";
    private String maxPenID = "";
    private String releaseNum = "";
    private String releaseDate = "";
    private String maxAutoLitterNum = "";
    private String maxAutoMouseID = "";
    private String releaseType = "";
    private String releaseNotes = "";
    private String webReleaseNum = "";
    private String version = "";
    
    public AdminDbInfoDTO () {
        
    }
    public AdminDbInfoDTO (
        String dbInfoKey,
        String dbVers,
        String versDate,
        String maxPenID,
        String releaseNum,
        String releaseDate,
        String maxAutoLitterNum,
        String maxAutoMouseID,
        String releaseType,
        String releaseNotes,
        String webReleaseNum,
        String version ) {
        this.setDbInfoKey(dbInfoKey);
        this.setDbVers(dbVers);
        this.setVersDate(versDate);
        this.setMaxPenID(maxPenID);
        this.setReleaseNum(releaseNum);
        this.setReleaseDate(releaseDate);
        this.setMaxAutoLitterNum(maxAutoLitterNum);
        this.setMaxAutoMouseID(maxAutoMouseID);
        this.setReleaseType(releaseType);
        this.setReleaseNotes(releaseNotes);
        this.setWebReleaseNum(webReleaseNum);
        this.setVersion(version);
    }

    /**
     * @return the dbVers
     */
    public String getDbVers() {
        return dbVers;
    }

    /**
     * @param dbVers the dbVers to set
     */
    public void setDbVers(String dbVers) {
        this.dbVers = dbVers;
    }

    /**
     * @return the versDate
     */
    public String getVersDate() {
        return versDate;
    }

    /**
     * @param versDate the versDate to set
     */
    public void setVersDate(String versDate) {
        this.versDate = versDate;
    }

    /**
     * @return the maxPenID
     */
    public String getMaxPenID() {
        return maxPenID;
    }

    /**
     * @param maxPenID the maxPenID to set
     */
    public void setMaxPenID(String maxPenID) {
        this.maxPenID = maxPenID;
    }

    /**
     * @return the releaseNum
     */
    public String getReleaseNum() {
        return releaseNum;
    }

    /**
     * @param releaseNum the releaseNum to set
     */
    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    /**
     * @return the releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the maxAutoMouseID
     */
    public String getMaxAutoMouseID() {
        return maxAutoMouseID;
    }

    /**
     * @param maxAutoMouseID the maxAutoMouseID to set
     */
    public void setMaxAutoMouseID(String maxAutoMouseID) {
        this.maxAutoMouseID = maxAutoMouseID;
    }

    /**
     * @return the releaseType
     */
    public String getReleaseType() {
        return releaseType;
    }

    /**
     * @param releaseType the releaseType to set
     */
    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    /**
     * @return the releaseNotes
     */
    public String getReleaseNotes() {
        return releaseNotes;
    }

    /**
     * @param releaseNotes the releaseNotes to set
     */
    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
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
     * @return the dbInfoKey
     */
    public String getDbInfoKey() {
        return dbInfoKey;
    }

    /**
     * @param dbInfoKey the dbInfoKey to set
     */
    public void setDbInfoKey(String dbInfoKey) {
        this.dbInfoKey = dbInfoKey;
    }

    /**
     * @return the maxAutoLitterNum
     */
    public String getMaxAutoLitterNum() {
        return maxAutoLitterNum;
    }

    /**
     * @param maxAutoLitterNum the maxAutoLitterNum to set
     */
    public void setMaxAutoLitterNum(String maxAutoLitterNum) {
        this.maxAutoLitterNum = maxAutoLitterNum;
    }

    /**
     * @return the webReleaseNum
     */
    public String getWebReleaseNum() {
        return webReleaseNum;
    }

    /**
     * @param webReleaseNum the webReleaseNum to set
     */
    public void setWebReleaseNum(String webReleaseNum) {
        this.webReleaseNum = webReleaseNum;
    }
    
}
