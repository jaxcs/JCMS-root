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
public class SampleStatusDTO {
    private String sampleStatus = "";
    private String isInStorage = "";
    private String _sampleStatus_key = "";
    private String version = "";

    /**
     * @return the sampleStatus
     */
    public String getSampleStatus() {
        return sampleStatus;
    }

    /**
     * @param sampleStatus the sampleStatus to set
     */
    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    /**
     * @return the isInStorage
     */
    public String getIsInStorage() {
        return isInStorage;
    }

    /**
     * @param isInStorage the isInStorage to set
     */
    public void setIsInStorage(String isInStorage) {
        this.isInStorage = isInStorage;
    }

    /**
     * @return the _sampleStatus_key
     */
    public String getSampleStatus_key() {
        return _sampleStatus_key;
    }

    /**
     * @param sampleStatus_key the _sampleStatus_key to set
     */
    public void setSampleStatus_key(String sampleStatus_key) {
        this._sampleStatus_key = sampleStatus_key;
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
