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
public class LocationTypeDTO {
    
    private String locationType = "";
    private String _storageFacility_key = "";
    private String label = "";
    private String locationDetail = "";
    private String locationTypeRef = "";
    private String _locationType_key = "";
    private String version = "";
    
    public LocationTypeDTO(){}
    
    public LocationTypeDTO(LocationTypeDTO dto){
        locationType = dto.getLocationType();
        _storageFacility_key = dto.getStorageFacility_key();
        label = dto.getLabel();
        locationDetail = dto.getLocationDetail();
        locationTypeRef = dto.getLocationTypeRef();
        _locationType_key = dto.getLocationType_key();
        version = dto.getVersion();
    }

    /**
     * @return the locationType
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * @param locationType the locationType to set
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * @return the _storageFacility_key
     */
    public String getStorageFacility_key() {
        return _storageFacility_key;
    }

    /**
     * @param storageFacility_key the _storageFacility_key to set
     */
    public void setStorageFacility_key(String storageFacility_key) {
        this._storageFacility_key = storageFacility_key;
    }

    /**
     * @return the locationDetail
     */
    public String getLocationDetail() {
        return locationDetail;
    }

    /**
     * @param locationDetail the locationDetail to set
     */
    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    /**
     * @return the locationTypeRef
     */
    public String getLocationTypeRef() {
        return locationTypeRef;
    }

    /**
     * @param locationTypeRef the locationTypeRef to set
     */
    public void setLocationTypeRef(String locationTypeRef) {
        this.locationTypeRef = locationTypeRef;
    }

    /**
     * @return the _locationType_key
     */
    public String getLocationType_key() {
        return _locationType_key;
    }

    /**
     * @param locationType_key the _locationType_key to set
     */
    public void setLocationType_key(String locationType_key) {
        this._locationType_key = locationType_key;
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
}
