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
public class StorageFacilityDTO {
    private String storageFacility = "";
    private String label = "";
    private String _storageFacility_key = "";
    private String version = "";
    
    public StorageFacilityDTO(){}
    
    public StorageFacilityDTO(StorageFacilityDTO dto){
        this._storageFacility_key = dto._storageFacility_key;
        this.label = dto.label;
        this.storageFacility = dto.storageFacility;
        this.version = dto.version;
    }

    /**
     * @return the storageFacility
     */
    public String getStorageFacility() {
        return storageFacility;
    }

    /**
     * @param storageFacility the storageFacility to set
     */
    public void setStorageFacility(String storageFacility) {
        this.storageFacility = storageFacility;
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
