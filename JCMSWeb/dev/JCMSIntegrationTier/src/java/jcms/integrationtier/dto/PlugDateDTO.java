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

import java.util.Date;

/**
 *
 * @author mkamato
 */
public class PlugDateDTO {
    
    private String plugDateKey = "";
    private String _mating_key = "";
    private String _mouse_key = "";
    private Date plugDate = new Date();
    private String obsolete = "";
    private String comment = "";
    private String version = "";
    
    public PlugDateDTO(PlugDateDTO dto){
        plugDateKey = dto.getPlugDateKey();
        _mating_key = dto.getMating_key();
        _mouse_key = dto.getMouse_key();
        plugDate = dto.getPlugDate();
        obsolete = dto.getObsolete();
        comment = dto.getComment();
        version = dto.getVersion();
    }
    
    public PlugDateDTO(){}

    /**
     * @return the plugDateKey
     */
    public String getPlugDateKey() {
        return plugDateKey;
    }

    /**
     * @param plugDateKey the plugDateKey to set
     */
    public void setPlugDateKey(String plugDateKey) {
        this.plugDateKey = plugDateKey;
    }

    /**
     * @return the _mating_key
     */
    public String getMating_key() {
        return _mating_key;
    }

    /**
     * @param mating_key the _mating_key to set
     */
    public void setMating_key(String mating_key) {
        this._mating_key = mating_key;
    }

    /**
     * @return the _mouse_key
     */
    public String getMouse_key() {
        return _mouse_key;
    }

    /**
     * @param mouse_key the _mouse_key to set
     */
    public void setMouse_key(String mouse_key) {
        this._mouse_key = mouse_key;
    }

    /**
     * @return the plugDate
     */
    public Date getPlugDate() {
        return plugDate;
    }

    /**
     * @param plugDate the plugDate to set
     */
    public void setPlugDate(Date plugDate) {
        this.plugDate = plugDate;
    }

    /**
     * @return the obsolete
     */
    public String getObsolete() {
        return obsolete;
    }

    /**
     * @param obsolete the obsolete to set
     */
    public void setObsolete(String obsolete) {
        this.obsolete = obsolete;
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
}
