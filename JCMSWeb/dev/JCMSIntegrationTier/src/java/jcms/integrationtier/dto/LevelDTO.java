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

/**
 *
 * @author mkamato
 */
public class LevelDTO {
    
    private String _level_key = "";
    private String _room_key = "";
    private String levelRef = "";
    private String levelId = "";
    private String levelDetail = "";
    private String xmax = "";
    private String ymax = "";
    private String zmax = "";
    private String label = "";
    private String isActive = "";
    private String style = "";
    private String version = "";

    public LevelDTO(){}
    
    public LevelDTO(LevelDTO dto){
        this._level_key = dto.getLevel_key();
        this._room_key = dto.getRoom_key();
        this.levelDetail = dto.getLevelDetail();
        this.levelId = dto.getLevelId();
        this.levelRef = dto.getLevelRef();
        this.version = dto.getVersion();
        this.xmax = dto.getXmax();
        this.ymax = dto.getYmax();
        this.zmax = dto.getZmax();
        this.label = dto.getLabel();
    }
    
    /**
     * @return the _level_key
     */
    public String getLevel_key() {
        return _level_key;
    }

    /**
     * @param level_key the _level_key to set
     */
    public void setLevel_key(String level_key) {
        this._level_key = level_key;
    }

    /**
     * @return the _room_key
     */
    public String getRoom_key() {
        return _room_key;
    }

    /**
     * @param room_key the _room_key to set
     */
    public void setRoom_key(String room_key) {
        this._room_key = room_key;
    }

    /**
     * @return the levelRef
     */
    public String getLevelRef() {
        return levelRef;
    }

    /**
     * @param levelRef the levelRef to set
     */
    public void setLevelRef(String levelRef) {
        this.levelRef = levelRef;
    }

    /**
     * @return the levelId
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the levelDetail
     */
    public String getLevelDetail() {
        return levelDetail;
    }

    /**
     * @param levelDetail the levelDetail to set
     */
    public void setLevelDetail(String levelDetail) {
        this.levelDetail = levelDetail;
    }

    /**
     * @return the xmax
     */
    public String getXmax() {
        return xmax;
    }

    /**
     * @param xmax the xmax to set
     */
    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    /**
     * @return the ymax
     */
    public String getYmax() {
        return ymax;
    }

    /**
     * @param ymax the ymax to set
     */
    public void setYmax(String ymax) {
        this.ymax = ymax;
    }

    /**
     * @return the zmax
     */
    public String getZmax() {
        return zmax;
    }

    /**
     * @param zmax the zmax to set
     */
    public void setZmax(String zmax) {
        this.zmax = zmax;
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
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
}
