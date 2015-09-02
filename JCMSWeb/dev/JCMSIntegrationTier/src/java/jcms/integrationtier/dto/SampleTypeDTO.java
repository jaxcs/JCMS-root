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
public class SampleTypeDTO {
    private String sampleType = "";
    private String _sampleClass_key = "";
    private String _sampleType_key = "";
    private String version = "";

    /**
     * @return the sampleType
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * @param sampleType the sampleType to set
     */
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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
     * @return the _sampleType_key
     */
    public String getSampleType_key() {
        return _sampleType_key;
    }

    /**
     * @param sampleType_key the _sampleType_key to set
     */
    public void setSampleType_key(String sampleType_key) {
        this._sampleType_key = sampleType_key;
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
