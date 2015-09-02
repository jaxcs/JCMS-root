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
public class KaplanMeierLineDTO {
    
    private String total = "";
    private String maxDays = "";
    private String lineName = "";
    private String color = "";
    private ArrayList<KaplanMeierLineDetailDTO> details = new ArrayList<KaplanMeierLineDetailDTO>();

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the maxDays
     */
    public String getMaxDays() {
        return maxDays;
    }

    /**
     * @param maxDays the maxDays to set
     */
    public void setMaxDays(String maxDays) {
        this.maxDays = maxDays;
    }

    /**
     * @return the lineName
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * @param protocolName the protocolName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * @return the details
     */
    public ArrayList<KaplanMeierLineDetailDTO> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(ArrayList<KaplanMeierLineDetailDTO> details) {
        this.details = details;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
}
