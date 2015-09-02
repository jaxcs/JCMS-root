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
public class PreservationMethodDTO {
    private String _preservationType_key = "";
    private String _preservationMethod_key = "";
    private String preservationMethod = "";
    private String version = "";

    /**
     * @return the _preservationType_key
     */
    public String getPreservationType_key() {
        return _preservationType_key;
    }

    /**
     * @param preservationType_key the _preservationType_key to set
     */
    public void setPreservationType_key(String preservationType_key) {
        this._preservationType_key = preservationType_key;
    }

    /**
     * @return the _preservationMethod_key
     */
    public String getPreservationMethod_key() {
        return _preservationMethod_key;
    }

    /**
     * @param preservationMethod_key the _preservationMethod_key to set
     */
    public void setPreservationMethod_key(String preservationMethod_key) {
        this._preservationMethod_key = preservationMethod_key;
    }

    /**
     * @return the preservationMethod
     */
    public String getPreservationMethod() {
        return preservationMethod;
    }

    /**
     * @param preservationMethod the preservationMethod to set
     */
    public void setPreservationMethod(String preservationMethod) {
        this.preservationMethod = preservationMethod;
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
