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

import java.util.Date;
import java.util.ArrayList;
/**
 *
 * @author mkamato
 */
public class UseScheduleDTO {
    private String useScheduleKey = "";
    private MouseDTO mouse = null;
    private UseScheduleTermDTO useScheduleTerm = null;
    private String plugDateKey = "";
    private Date startDate = null;
    private String comment = "";
    private Boolean done;
    private ArrayList<MouseUsageDTO> usages = new ArrayList<MouseUsageDTO>(); 

    /**
     * @return the useScheduleKey
     */
    public String getUseScheduleKey() {
        return useScheduleKey;
    }

    /**
     * @param useScheduleKey the useScheduleKey to set
     */
    public void setUseScheduleKey(String useScheduleKey) {
        this.useScheduleKey = useScheduleKey;
    }

    /**
     * @return the mouse
     */
    public MouseDTO getMouse() {
        return mouse;
    }

    /**
     * @param mouse the mouse to set
     */
    public void setMouse(MouseDTO mouse) {
        this.mouse = mouse;
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the usages
     */
    public ArrayList<MouseUsageDTO> getUsages() {
        return usages;
    }

    /**
     * @param usages the usages to set
     */
    public void setUsages(ArrayList<MouseUsageDTO> usages) {
        this.usages = usages;
    }

    /**
     * @return the done
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * @return the plugDateKey
     */
    public String getPlugDateKey() {
        return plugDateKey;
    }

    /**
     * @param plugDateKey the plugDateKey to set
     */
    public void setPlugDateKey(String plugDateKey) {
        this.plugDateKey = plugDateKey;
    }
    
    
}
