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
public class UseScheduleStartEventDTO {
    
    private String useScheduleStartEventKey = "";
    private String useScheduleStartEvent = "";
    private String useScheduleStartEventDetail = "";

    /**
     * @return the useScheduleStartEventKey
     */
    public String getUseScheduleStartEventKey() {
        return useScheduleStartEventKey;
    }

    /**
     * @param useScheduleStartEventKey the useScheduleStartEventKey to set
     */
    public void setUseScheduleStartEventKey(String useScheduleStartEventKey) {
        this.useScheduleStartEventKey = useScheduleStartEventKey;
    }

    /**
     * @return the useScheduleStartEvent
     */
    public String getUseScheduleStartEvent() {
        return useScheduleStartEvent;
    }

    /**
     * @param useScheduleStartEvent the useScheduleStartEvent to set
     */
    public void setUseScheduleStartEvent(String useScheduleStartEvent) {
        this.useScheduleStartEvent = useScheduleStartEvent;
    }

    /**
     * @return the useScheduleStartEventDetail
     */
    public String getUseScheduleStartEventDetail() {
        return useScheduleStartEventDetail;
    }

    /**
     * @param useScheduleStartEventDetail the useScheduleStartEventDetail to set
     */
    public void setUseScheduleStartEventDetail(String useScheduleStartEventDetail) {
        this.useScheduleStartEventDetail = useScheduleStartEventDetail;
    }
}
