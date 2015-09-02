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
public class HarvestMethodDTO {
    private String harvestMethod = "";
    private String _harvestMethod_key = "";
    private String version = "";

    /**
     * @return the _harvestMethod_key
     */
    public String getHarvestMethod_key() {
        return _harvestMethod_key;
    }

    /**
     * @param harvestMethod_key the _harvestMethod_key to set
     */
    public void setHarvestMethod_key(String harvestMethod_key) {
        this._harvestMethod_key = harvestMethod_key;
    }

    /**
     * @return the harvestMethod
     */
    public String getHarvestMethod() {
        return harvestMethod;
    }

    /**
     * @param harvestMethod the harvestMethod to set
     */
    public void setHarvestMethod(String harvestMethod) {
        this.harvestMethod = harvestMethod;
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
