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

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class HistogramDayDTO {
    
    private Date day = new Date();
    private ArrayList<HistogramUnitDTO> units = new ArrayList<HistogramUnitDTO>();

    /**
     * @return the day
     */
    public Date getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * @return the units
     */
    public ArrayList<HistogramUnitDTO> getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(ArrayList<HistogramUnitDTO> units) {
        this.units = units;
    }
    
}
