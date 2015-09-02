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
public class TimeUnitDTO {
    private String timeUnit = "";
    private String abbreviation = "";
    private String minutesPerUnit = "";
    private String _timeUnit_key = "";
    private String version = "";

    /**
     * @return the timeUnit
     */
    public String getTimeUnit() {
        return timeUnit;
    }

    /**
     * @param timeUnit the timeUnit to set
     */
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * @return the minutesPerUnit
     */
    public String getMinutesPerUnit() {
        return minutesPerUnit;
    }

    /**
     * @param minutesPerUnit the minutesPerUnit to set
     */
    public void setMinutesPerUnit(String minutesPerUnit) {
        this.minutesPerUnit = minutesPerUnit;
    }

    /**
     * @return the _timeUnit_key
     */
    public String getTimeUnit_key() {
        return _timeUnit_key;
    }

    /**
     * @param timeUnit_key the _timeUnit_key to set
     */
    public void setTimeUnit_key(String timeUnit_key) {
        this._timeUnit_key = timeUnit_key;
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
