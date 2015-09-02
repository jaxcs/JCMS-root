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
public class KaplanMeierLineDetailDTO {
    
    private String daysAfterStart = "";
    private String countDied = "";
    private String countLeft = "";

    /**
     * @return the daysAfterStart
     */
    public String getDaysAfterStart() {
        return daysAfterStart;
    }

    /**
     * @param daysAfterStart the daysAfterStart to set
     */
    public void setDaysAfterStart(String daysAfterStart) {
        this.daysAfterStart = daysAfterStart;
    }

    /**
     * @return the countDied
     */
    public String getCountDied() {
        return countDied;
    }

    /**
     * @param countDied the countDied to set
     */
    public void setCountDied(String countDied) {
        this.countDied = countDied;
    }

    /**
     * @return the countLeft
     */
    public String getCountLeft() {
        return countLeft;
    }

    /**
     * @param countLeft the countLeft to set
     */
    public void setCountLeft(String countLeft) {
        this.countLeft = countLeft;
    }
    
}
