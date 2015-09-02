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
public class WeightUnitDTO {
    private String weightUnit = "";
    private String _weightUnit_key = "";
    private String version = "";

    /**
     * @return the weightUnit
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * @param weightUnit the weightUnit to set
     */
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    /**
     * @return the _weightUnit_key
     */
    public String getWeightUnit_key() {
        return _weightUnit_key;
    }

    /**
     * @param weightUnit_key the _weightUnit_key to set
     */
    public void setWeightUnit_key(String weightUnit_key) {
        this._weightUnit_key = weightUnit_key;
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
