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
public class StorageDTO {
    
    private String _sample_key = "";
    private String _sampleStatus_key = "";
    private String sampleStatus = "";
    private String _preservationDetail_key = "";
    private String preservationDetail = "";
    private String _preservationMethod_key = "";
    private String preservationMethod = "";
    private String _preservationType_key = "";
    private String preservationType = "";
    private String _storage_key = "";
    private String _version_key = "";

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
     * @return the _preservationDetail_key
     */
    public String getPreservationDetail_key() {
        return _preservationDetail_key;
    }

    /**
     * @param preservationDetail_key the _preservationDetail_key to set
     */
    public void setPreservationDetail_key(String preservationDetail_key) {
        this._preservationDetail_key = preservationDetail_key;
    }

    /**
     * @return the preservationDetail
     */
    public String getPreservationDetail() {
        return preservationDetail;
    }

    /**
     * @param preservationDetail the preservationDetail to set
     */
    public void setPreservationDetail(String preservationDetail) {
        this.preservationDetail = preservationDetail;
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
     * @return the _version_key
     */
    public String getVersion_key() {
        return _version_key;
    }

    /**
     * @param version_key the _version_key to set
     */
    public void setVersion_key(String version_key) {
        this._version_key = version_key;
    }
    
}
