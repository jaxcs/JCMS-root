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
public class LocationDTO {
    private String _storage_key = "";
    private String _locationType_key = "";
    private String _location_key = "";
    private String version = "";

    /**
     * @return the _storage_key
     */
    public String getStorage_key() {
        return _storage_key;
    }

    /**
     * @param storage_key the _storage_key to set
     */
    public void setStorage_key(String storage_key) {
        this._storage_key = storage_key;
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
     * @return the _location_key
     */
    public String getLocation_key() {
        return _location_key;
    }

    /**
     * @param location_key the _location_key to set
     */
    public void setLocation_key(String location_key) {
        this._location_key = location_key;
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
