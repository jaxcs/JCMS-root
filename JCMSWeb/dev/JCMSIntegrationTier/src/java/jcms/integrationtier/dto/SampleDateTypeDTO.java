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
public class SampleDateTypeDTO {
    private String sampleDateType = "";
    private String _sampleDateType_key = "";
    private String version = "";

    /**
     * @return the sampleDateType
     */
    public String getSampleDateType() {
        return sampleDateType;
    }

    /**
     * @param sampleDateType the sampleDateType to set
     */
    public void setSampleDateType(String sampleDateType) {
        this.sampleDateType = sampleDateType;
    }

    /**
     * @return the _sampleDateType_key
     */
    public String getSampleDateType_key() {
        return _sampleDateType_key;
    }

    /**
     * @param sampleDateType_key the _sampleDateType_key to set
     */
    public void setSampleDateType_key(String sampleDateType_key) {
        this._sampleDateType_key = sampleDateType_key;
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
