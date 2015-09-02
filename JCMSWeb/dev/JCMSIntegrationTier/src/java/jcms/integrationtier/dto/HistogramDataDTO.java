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
/**
 *
 * @author mkamato
 */
public class HistogramDataDTO {
    
    private ArrayList<HistogramDayDTO> histogramData = new ArrayList<HistogramDayDTO>();
    private String groupingUnit = "";

    /**
     * @return the histogram
     */
    public ArrayList<HistogramDayDTO> getHistogramData() {
        return histogramData;
    }

    /**
     * @param histogram the histogram to set
     */
    public void setHistogramData(ArrayList<HistogramDayDTO> histogramData) {
        this.histogramData = histogramData;
    }

    /**
     * @return the groupingUnit
     */
    public String getGroupingUnit() {
        return groupingUnit;
    }

    /**
     * @param groupingUnit the groupingUnit to set
     */
    public void setGroupingUnit(String groupingUnit) {
        this.groupingUnit = groupingUnit;
    }
}
