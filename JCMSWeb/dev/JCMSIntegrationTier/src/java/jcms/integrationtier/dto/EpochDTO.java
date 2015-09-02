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
public class EpochDTO {
    private String epoch = "";
    private String _epoch_key = "";
    private String version = "";

    /**
     * @return the epoch
     */
    public String getEpoch() {
        return epoch;
    }

    /**
     * @param epoch the epoch to set
     */
    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    /**
     * @return the _epoch_key
     */
    public String getEpoch_key() {
        return _epoch_key;
    }

    /**
     * @param epoch_key the _epoch_key to set
     */
    public void setEpoch_key(String epoch_key) {
        this._epoch_key = epoch_key;
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
