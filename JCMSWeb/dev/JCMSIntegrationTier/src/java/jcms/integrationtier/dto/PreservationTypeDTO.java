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
public class PreservationTypeDTO {
    private String _preservationType_key = "";
    private String preservationType = "";
    private String _sampleClass_key = "";
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
     * @return the preservationType
     */
    public String getPreservationType() {
        return preservationType;
    }

    /**
     * @param preservationType the preservationType to set
     */
    public void setPreservationType(String preservationType) {
        this.preservationType = preservationType;
    }

    /**
     * @return the _sampleClass_key
     */
    public String getSampleClass_key() {
        return _sampleClass_key;
    }

    /**
     * @param sampleClass_key the _sampleClass_key to set
     */
    public void setSampleClass_key(String sampleClass_key) {
        this._sampleClass_key = sampleClass_key;
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
