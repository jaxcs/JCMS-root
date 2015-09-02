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
 * @author bas
 */
public class cvMatingUnitTypeDTO {
    private String _matingUnitType_key = "";
    private String matingUnitType = "";
    private String abbreviation ="";
    private String isActive = "";
    private String isDefault = "";
    private String sortOrder = "";
    private String vocabularySourceKey = "";
    private String elementID = "";
    private String isDeprecated = "";
    private String version = "";

    /**
     * @return the _matingUnitType_key
     */
    public String getMatingUnitType_key() {
        return _matingUnitType_key;
    }

    /**
     * @param _matingUnitType_key the _matingUnitType_key to set
     */
    public void setMatingUnitType_key(String _matingUnitType_key) {
        this._matingUnitType_key = _matingUnitType_key;
    }

    /**
     * @return the matingUnitType
     */
    public String getMatingUnitType() {
        return matingUnitType;
    }

    /**
     * @param matingUnitType the matingUnitType to set
     */
    public void setMatingUnitType(String matingUnitType) {
        this.matingUnitType = matingUnitType;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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
     * @return the isDefault
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the vocabularySourceKey
     */
    public String getVocabularySourceKey() {
        return vocabularySourceKey;
    }

    /**
     * @param vocabularySourceKey the vocabularySourceKey to set
     */
    public void setVocabularySourceKey(String vocabularySourceKey) {
        this.vocabularySourceKey = vocabularySourceKey;
    }

    /**
     * @return the elementID
     */
    public String getElementID() {
        return elementID;
    }

    /**
     * @param elementID the elementID to set
     */
    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    /**
     * @return the isDeprecated
     */
    public String getIsDeprecated() {
        return isDeprecated;
    }

    /**
     * @param isDeprecated the isDeprecated to set
     */
    public void setIsDeprecated(String isDeprecated) {
        this.isDeprecated = isDeprecated;
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
