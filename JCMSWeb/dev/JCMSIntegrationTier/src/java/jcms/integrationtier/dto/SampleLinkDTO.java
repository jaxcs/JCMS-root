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
// I believe this DTO is for the MatingSample table and that the field
// called _unit_key is the _mating_key field.
public class SampleLinkDTO {
    private String _matingSample_key = "";
    private String _sample_key = "";
    private String _unit_key = "";
    private String version = "";

    /**
     * @return the _sample_key
     */
    public String getSample_key() {
        return _sample_key;
    }

    /**
     * @param sample_key the _sample_key to set
     */
    public void setSample_key(String sample_key) {
        this._sample_key = sample_key;
    }

    /**
     * @return the _unit_key
     */
    public String getUnit_key() {
        return _unit_key;
    }

    /**
     * @param unit_key the _unit_key to set
     */
    public void setUnit_key(String unit_key) {
        this._unit_key = unit_key;
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
     * @return the _matingSample_key
     */
    public String getMatingSample_key() {
        return _matingSample_key;
    }

    /**
     * @param matingSample_key the _matingSample_key to set
     */
    public void setMatingSample_key(String matingSample_key) {
        this._matingSample_key = matingSample_key;
    }
}
