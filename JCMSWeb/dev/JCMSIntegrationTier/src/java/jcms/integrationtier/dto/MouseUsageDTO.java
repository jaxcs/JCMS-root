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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class MouseUsageDTO {
    private String usageKey = "";
    private String mouseKey = "";
    private String plugDateKey = "";
    private String useScheduleKey = "";
    private String use = "";
    private String useAge = "";
    //add these because it was a mistake to not have all dates be date objects.
    private Date projectedDate = null;
    private Date actualDate = null;
    private String done = "";
    private String comment = "";
    private String D1 = "";
    private String D2 = "";
    private String D3 = "";
    private String D4 = "";
    private String D5 = "";
    private String D6 = "";
    private String D7 = "";
    private String D8 = "";
    private String D9 = "";
    private String D10 = "";
    private String version = "1";
    //helpful to have labels for usage in the usage
    private cvMouseUseDTO mouseUse = new cvMouseUseDTO();
    private UseScheduleDTO useSchedule = new UseScheduleDTO();
    
    public MouseUsageDTO(MouseUsageDTO dto){
        this.usageKey = dto.usageKey;
        this.D1 = dto.D1;
        this.D2 = dto.D2;
        this.D3 = dto.D3;
        this.D4 = dto.D4;
        this.D5 = dto.D5;
        this.D6 = dto.D6;
        this.D7 = dto.D7;
        this.D8 = dto.D8;
        this.D9 = dto.D9;
        this.D10 = dto.D10;
        this.comment = dto.comment;
        this.done = dto.done;
        this.mouseKey = dto.mouseKey;
        this.use = dto.use;
        this.useAge = dto.useAge;
        this.version = dto.version;
        this.projectedDate = dto.projectedDate;
        this.actualDate = dto.actualDate;
    }
    
    public MouseUsageDTO(){};

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

    /**
     * @return the usageKey
     */
    public String getUsageKey() {
        return usageKey;
    }

    /**
     * @param usageKey the usageKey to set
     */
    public void setUsageKey(String usageKey) {
        this.usageKey = usageKey;
    }

    /**
     * @return the mouseKey
     */
    public String getMouseKey() {
        return mouseKey;
    }

    /**
     * @param mouseKey the mouseKey to set
     */
    public void setMouseKey(String mouseKey) {
        this.mouseKey = mouseKey;
    }

    /**
     * @return the use
     */
    public String getUse() {
        return use;
    }

    /**
     * @param use the use to set
     */
    public void setUse(String use) {
        this.use = use;
    }

    /**
     * @return the useAge
     */
    public String getUseAge() {
        return useAge;
    }

    /**
     * @param useAge the useAge to set
     */
    public void setUseAge(String useAge) {
        this.useAge = useAge;
    }

    /**
     * @return the done
     */
    public String getDone() {
        if(done.equals("0")){
            return "false";
        }
        else if(done.equals("-1")){
            return "true";
        }
        return done;
    }
    
    public String getDoneForInsert() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(String done) {
        if(done.equalsIgnoreCase("false")){
            this.done = "0";
        }
        else if(done.equalsIgnoreCase("true")){
            this.done = "-1";
        }
        else{
            this.done = done;
        }
    }
    
    public void setDone(boolean done){
        if(done){
            this.done = "-1";
        }
        else{
            this.done = "0";
        }
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
     * @return the D1
     */
    public String getD1() {
        return D1;
    }

    /**
     * @param D1 the D1 to set
     */
    public void setD1(String D1) {
        this.D1 = D1;
    }

    /**
     * @return the D2
     */
    public String getD2() {
        return D2;
    }

    /**
     * @param D2 the D2 to set
     */
    public void setD2(String D2) {
        this.D2 = D2;
    }

    /**
     * @return the D3
     */
    public String getD3() {
        return D3;
    }

    /**
     * @param D3 the D3 to set
     */
    public void setD3(String D3) {
        this.D3 = D3;
    }

    /**
     * @return the D4
     */
    public String getD4() {
        return D4;
    }

    /**
     * @param D4 the D4 to set
     */
    public void setD4(String D4) {
        this.D4 = D4;
    }

    /**
     * @return the D5
     */
    public String getD5() {
        return D5;
    }

    /**
     * @param D5 the D5 to set
     */
    public void setD5(String D5) {
        this.D5 = D5;
    }

    /**
     * @return the D6
     */
    public String getD6() {
        return D6;
    }

    /**
     * @param D6 the D6 to set
     */
    public void setD6(String D6) {
        this.D6 = D6;
    }

    /**
     * @return the D7
     */
    public String getD7() {
        return D7;
    }

    /**
     * @param D7 the D7 to set
     */
    public void setD7(String D7) {
        this.D7 = D7;
    }

    /**
     * @return the D8
     */
    public String getD8() {
        return D8;
    }

    /**
     * @param D8 the D8 to set
     */
    public void setD8(String D8) {
        this.D8 = D8;
    }

    /**
     * @return the D9
     */
    public String getD9() {
        return D9;
    }

    /**
     * @param D9 the D9 to set
     */
    public void setD9(String D9) {
        this.D9 = D9;
    }

    /**
     * @return the D10
     */
    public String getD10() {
        return D10;
    }

    /**
     * @param D10 the D10 to set
     */
    public void setD10(String D10) {
        this.D10 = D10;
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
     * @return the dateProjectedDate
     */
    public Date getProjectedDate() {
        return projectedDate;
    }

    /**
     * @param dateProjectedDate the dateProjectedDate to set
     */
    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
    }

    /**
     * @return the dateActualDate
     */
    public Date getActualDate() {
        return actualDate;
    }

    /**
     * @param dateActualDate the dateActualDate to set
     */
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    /**
     * @return the mouseUse
     */
    public cvMouseUseDTO getMouseUse() {
        return mouseUse;
    }

    /**
     * @param mouseUse the mouseUse to set
     */
    public void setMouseUse(cvMouseUseDTO mouseUse) {
        this.mouseUse = mouseUse;
    }

    /**
     * @return the useSchedule
     */
    public UseScheduleDTO getUseSchedule() {
        return useSchedule;
    }

    /**
     * @param useSchedule the useSchedule to set
     */
    public void setUseSchedule(UseScheduleDTO useSchedule) {
        this.useSchedule = useSchedule;
    }
}
