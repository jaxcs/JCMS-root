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

package jcms.web.colonyManagement;

import java.io.Serializable;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.dto.UseScheduleSearchDTO;
import jcms.integrationtier.dto.UseScheduleDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dto.MouseUsageDTO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import jcms.web.common.MouseScheduleUtilities;
/**
 *
 * @author mkamato
 */
public class UseScheduleListBean extends WTBaseBackingBean implements Serializable {
    
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private UseScheduleSearchDTO useScheduleSearch = new UseScheduleSearchDTO();
    private MouseScheduleUtilities pUtils = new MouseScheduleUtilities();
    private ArrayList<UseScheduleDTO> useSchedules = new ArrayList<UseScheduleDTO>();
    private String resultsLength = "";    
    
    public UseScheduleListBean(){
        //inititalize owners - these have to be limited to those workgroups the user can edit, no guests allowed here
        useScheduleSearch.setWgs((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
    }
    
    public void executeUseScheduleSearch(){
        boolean flag = false;
        if(useScheduleSearch.getWgs().isEmpty()){
            flag = true;
            this.addToMessageQueue("Please at least one workgroup from the select box below to search.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            useSchedules = pUtils.getpDAO().executeUseScheduleSearch(useScheduleSearch);
            setResultsLength(new Integer(useSchedules.size()).toString());
        }
    }
    
    public void clearSearchPanel(){
        useScheduleSearch = new UseScheduleSearchDTO();
        useScheduleSearch.setWgs((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
    }
    
    public void saveUseScheduleAction(){        
        Integer useScheduleIndex = this.getKey("paramRowIndex"); 
        System.out.println(useScheduleIndex);
        UseScheduleDTO dto = useSchedules.get(useScheduleIndex);
        //updating a scheduling use schedule means updating the scheduled use schedule AND all the associated uses.
        try{
            pUtils.getpDAO().updateUseSchedule(dto);
            for(MouseUsageDTO usage : dto.getUsages()){
                //manages the usage dates so they are formatted properly for the insert.
                pUtils.getpDAO().updateMouseUsage(usage);
            }
            this.addToMessageQueue("use schedule " + dto.getUseScheduleTerm().getUseScheduleTermName() + " updated for mouse " + dto.getMouse().getID(), FacesMessage.SEVERITY_INFO);        
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the wrapper
     */
    public SelectItemWrapper getWrapper() {
        return wrapper;
    }

    /**
     * @param wrapper the wrapper to set
     */
    public void setWrapper(SelectItemWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * @return the useScheduleSearch
     */
    public UseScheduleSearchDTO getUseScheduleSearch() {
        return useScheduleSearch;
    }

    /**
     * @param useScheduleSearch the useScheduleSearch to set
     */
    public void setUseScheduleSearch(UseScheduleSearchDTO useScheduleSearch) {
        this.useScheduleSearch = useScheduleSearch;
    }

    /**
     * @return the useSchedules
     */
    public ArrayList<UseScheduleDTO> getUseSchedules() {
        return useSchedules;
    }

    /**
     * @param useSchedules the useSchedules to set
     */
    public void setUseSchedules(ArrayList<UseScheduleDTO> useSchedules) {
        this.useSchedules = useSchedules;
    }

    /**
     * @return the resultsLength
     */
    public String getResultsLength() {
        return resultsLength;
    }

    /**
     * @param resultsLength the resultsLength to set
     */
    public void setResultsLength(String resultsLength) {
        this.resultsLength = resultsLength;
    }
}
