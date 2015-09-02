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
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
/**
 *
 * @author mkamato
 */
public class UseScheduleTermDTO {
    
    private String useScheduleTermKey = "";
    private String useScheduleTermName = "";
    private String useScheduleTermDetail = "";
    private boolean isActive;
    private String versionNum = "";
    private String workgroupKey = "";
    private WorkgroupEntity workgroup = null;
    private String useScheduleStartEventKey = "";
    private ArrayList<UseScheduleListDTO> uses = new ArrayList<UseScheduleListDTO>();
    private String color = "";
    
    @Override
    public boolean equals(Object object){
        if(object instanceof UseScheduleTermDTO){
            UseScheduleTermDTO useSchedule = (UseScheduleTermDTO) object;
            if(useScheduleTermKey.equals(useSchedule.getUseScheduleTermKey())){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * @return the useScheduleTermKey
     */
    public String getUseScheduleTermKey() {
        return useScheduleTermKey;
    }

    /**
     * @param useScheduleTermKey the useScheduleTermKey to set
     */
    public void setUseScheduleTermKey(String useScheduleTermKey) {
        this.useScheduleTermKey = useScheduleTermKey;
    }

    /**
     * @return the useScheduleTermDetail
     */
    public String getUseScheduleTermDetail() {
        return useScheduleTermDetail;
    }

    /**
     * @param useScheduleTermDetail the useScheduleTermDetail to set
     */
    public void setUseScheduleTermDetail(String useScheduleTermDetail) {
        this.useScheduleTermDetail = useScheduleTermDetail;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the useScheduleTermName
     */
    public String getUseScheduleTermName() {
        return useScheduleTermName;
    }

    /**
     * @param useScheduleTermName the useScheduleTermName to set
     */
    public void setUseScheduleTermName(String useScheduleTermName) {
        this.useScheduleTermName = useScheduleTermName;
    }

    /**
     * @return the uses
     */
    public ArrayList<UseScheduleListDTO> getUses() {
        return uses;
    }

    /**
     * @param uses the uses to set
     */
    public void setUses(ArrayList<UseScheduleListDTO> uses) {
        this.uses = uses;
    }

    /**
     * @return the versionNum
     */
    public String getVersionNum() {
        return versionNum;
    }

    /**
     * @param versionNum the versionNum to set
     */
    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * @return the workgroupKey
     */
    public String getWorkgroupKey() {
        return workgroupKey;
    }

    /**
     * @param workgroupKey the workgroupKey to set
     */
    public void setWorkgroupKey(String workgroupKey) {
        this.workgroupKey = workgroupKey;
    }

    /**
     * @param workgroup the workgroup to set
     */
    public void setWorkgroupKey(WorkgroupEntity workgroup) {
        this.workgroupKey = workgroup.getWorkgroupkey().toString();
    }
    
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

    /**
     * @return the workgroup
     */
    public WorkgroupEntity getWorkgroup() {
        return workgroup;
    }

    /**
     * @param workgroup the workgroup to set
     */
    public void setWorkgroup(WorkgroupEntity workgroup) {
        this.workgroup = workgroup;
        this.workgroupKey = workgroup.getWorkgroupkey().toString();
    }
    
}
