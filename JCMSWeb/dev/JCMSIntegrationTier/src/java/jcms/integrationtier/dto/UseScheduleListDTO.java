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
public class UseScheduleListDTO {
    private String useScheduleListKey = "";
    private UseScheduleTermDTO useScheduleTerm = null;
    private cvMouseUseDTO use = null;
    private Integer daysAfterStart = null;

    /**
     * @return the useScheduleListKey
     */
    public String getUseScheduleListKey() {
        return useScheduleListKey;
    }

    /**
     * @param useScheduleListKey the useScheduleListKey to set
     */
    public void setUseScheduleListKey(String useScheduleListKey) {
        this.useScheduleListKey = useScheduleListKey;
    }

    /**
     * @return the useScheduleTerm
     */
    public UseScheduleTermDTO getUseScheduleTerm() {
        return useScheduleTerm;
    }

    /**
     * @param useScheduleTerm the useScheduleTerm to set
     */
    public void setUseScheduleTerm(UseScheduleTermDTO useScheduleTerm) {
        this.useScheduleTerm = useScheduleTerm;
    }

    /**
     * @return the use
     */
    public cvMouseUseDTO getUse() {
        return use;
    }

    /**
     * @param use the use to set
     */
    public void setUse(cvMouseUseDTO use) {
        this.use = use;
    }

    /**
     * @return the daysAfterStart
     */
    public Integer getDaysAfterStart() {
        return daysAfterStart;
    }

    /**
     * @param daysAfterStart the daysAfterStart to set
     */
    public void setDaysAfterStart(Integer daysAfterStart) {
        this.daysAfterStart = daysAfterStart;
    }
}
