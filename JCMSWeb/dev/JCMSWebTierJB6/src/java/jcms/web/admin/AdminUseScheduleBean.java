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

package jcms.web.admin;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.dao.UseScheduleDAO;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.UseScheduleListDTO;
import jcms.integrationtier.dto.UseScheduleDTO;
import jcms.integrationtier.dto.cvMouseUseDTO;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;


/**
 *
 * @author mkamato
 */
public class AdminUseScheduleBean extends AdminBean {
    
    private UseScheduleDAO usDAO = new UseScheduleDAO();
    private ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
    private Integer editRow = null;
    private Integer useLinkKey = null;
    
    public void refresh(){
        useScheduleTerms = usDAO.getUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
        editRow = null;
        useLinkKey = null;
    }
    
    /*manage use schedule terms*/
    public void addUseScheduleTerm(){
        boolean flag = false;
        //only do one edit at a time
        if(editRow != null || useLinkKey != null){
            flag = true;
            this.addToMessageQueue("Please finish your current add/edit before starting a new one.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            UseScheduleTermDTO temp = new UseScheduleTermDTO();
            temp.setIsActive(true);
            useScheduleTerms.add(0, temp);
            editRow = new Integer(0);
        }
    }
    
    public void editAction(){
        boolean flag = false;
        //only do one edit/add at a time
        if(editRow != null || useLinkKey != null){
            flag = true;
            this.addToMessageQueue("Please finish your current add/edit before starting a new one.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            editRow = this.getKey("paramRowIndex");
        }
    }

    public void saveAction(){
        UseScheduleTermDTO dto = useScheduleTerms.get(editRow);
        if(!validateUseSchedule(dto)){
            try{
                //if no key doing insert, otherwise doing update
                if(dto.getUseScheduleTermKey().equals("")){
                    if(dto.getColor().equals("")){
                        dto.setColor("ffffff");
                    }
                    dto.setUseScheduleTermKey(usDAO.insertUseScheduleTerm(dto).toString());
                }
                else{
                    usDAO.updateUseScheduleTerm(dto);
                }
                editRow = null;
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void cancelAction(){
        UseScheduleTermDTO pOfInterest = useScheduleTerms.get(editRow);
        //if editing an existing row, return to original value, otherwise remove the row
        //was adding a row.
        if(pOfInterest.getUseScheduleTermKey().equals("")){
            useScheduleTerms.remove(editRow.intValue());
            editRow = null;
        }
        //was editing the row, get the old use schedule information and revert to that in case of changes
        else{
            useScheduleTerms.set(editRow.intValue(), usDAO.getUseScheduleTerm(pOfInterest.getUseScheduleTermKey()));
            editRow = null;
        }        
    }
    
    /* manage uses */
    public void addUse(){
        boolean flag = false;
        //check to make sure you're not adding/editing anything else.
        if(editRow != null || useLinkKey != null){
            flag = true;
            this.addToMessageQueue("Please finish your current add/edit action before starting another.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //get the use schedule you're adding the use to...
            Integer useScheduleTermKey = this.getKey("paramRowIndex");
            UseScheduleListDTO temp = new UseScheduleListDTO();
            temp.setUseScheduleListKey("0");
            useScheduleTerms.get(useScheduleTermKey).getUses().add(0, temp);
            useLinkKey = new Integer(0);
        }
    }
    
    public void editUseAction(){
        boolean flag = false;
        //check to make sure you're not adding/editing anything else.
        if(editRow != null || useLinkKey != null){
            flag = true;
            this.addToMessageQueue("Please finish your current add/edit action before starting another.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //get the usescheduleterm you're editing the use of...        
            useLinkKey = this.getKey("paramUseLinkKey");
        }
    }

    public void saveUseAction(){
        //get the usescheduleterm you're adding the use to...        
        Integer useScheduleTermIndex = this.getKey("paramRowIndex");    
        Integer useIndex = this.getKey("paramUseRowIndex");
        UseScheduleListDTO dto = useScheduleTerms.get(useScheduleTermIndex).getUses().get(useIndex);
        if(!validateUseScheduleList(dto)){
            try{
                dto.setUseScheduleTerm(useScheduleTerms.get(useScheduleTermIndex));
                //if no key, then doing an insert
                if(dto.getUseScheduleListKey().equals("0")){
                    dto.setUseScheduleListKey(usDAO.insertUseScheduleList(dto).toString());
                }
                else{
                    usDAO.updateUseScheduleList(dto);
                }
                useLinkKey = null;
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void cancelUseAction(){
        //get the use schedule you're cancelling action on...
        Integer useSchedule = this.getKey("paramRowIndex");
        Integer useIndex = this.getKey("paramUseRowIndex");
        UseScheduleListDTO dto = useScheduleTerms.get(useSchedule).getUses().get(useIndex);
        //if no key, then doing an insert
        if(dto.getUseScheduleListKey().equals("0")){
            useScheduleTerms.get(useSchedule).getUses().remove(dto);
        }
        else{
            useScheduleTerms.set(useSchedule.intValue(), usDAO.getUseScheduleTerm(useScheduleTerms.get(useSchedule).getUseScheduleTermKey()));
        }
        useLinkKey = null;
    }
    
    public void deleteUseAction(){
        //get the use schedule you're delete the use from...
        Integer useScheduleIndex = this.getKey("paramRowIndex");    
        Integer useIndex = this.getKey("paramUseRowIndex");
        UseScheduleListDTO dto = useScheduleTerms.get(useScheduleIndex).getUses().get(useIndex);
        try{
            usDAO.deleteUseScheduleList(dto);
            useScheduleTerms.get(useScheduleIndex).getUses().remove(useIndex.intValue());
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
        
        useLinkKey = null;
    }
    
    private boolean validateUseSchedule(UseScheduleTermDTO dto){
        boolean flag = false;
        if(dto.getUseScheduleTermName().equals("")){
            flag = true;
            this.addToMessageQueue("Use schedule Name is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(dto.getWorkgroupKey().equals("")){
            flag = true;
            this.addToMessageQueue("Workgroup is required.", FacesMessage.SEVERITY_ERROR);            
        }
        if(dto.getUseScheduleStartEventKey().equals("")){
            flag = true;
            this.addToMessageQueue("Use schedule start event is required.", FacesMessage.SEVERITY_ERROR);            
        }
        if(dto.getUseScheduleTermName().length() > 32){
            flag = true;
            this.addToMessageQueue("Use schedule Name must be 32 or fewer characters.", FacesMessage.SEVERITY_ERROR);
        }
        if(dto.getUseScheduleTermDetail().length() > 256){
            flag = true;
            this.addToMessageQueue("Use schedule detail must be 256 or fewer characters.", FacesMessage.SEVERITY_ERROR);
        }
        if(dto.getVersionNum().length() > 16){
            flag = true;
            this.addToMessageQueue("Version number must be 16 or fewer characters.", FacesMessage.SEVERITY_ERROR);
        }
        return flag;
    }
    
    private boolean validateUseScheduleList(UseScheduleListDTO dto){
        boolean flag = false;
        if(dto.getDaysAfterStart() == null || dto.getDaysAfterStart() < 0){          
            flag = true;
            this.addToMessageQueue("Days after event is required and must be a number greater than or equal to zero.", FacesMessage.SEVERITY_ERROR);
        }
        if(dto.getUse() == null){
            flag = true;
            this.addToMessageQueue("Use is required.", FacesMessage.SEVERITY_ERROR);
        }        
        return flag;
    }
    
    /**
     * @return the useScheduleTerms
     */
    public ArrayList<UseScheduleTermDTO> getUseScheduleTerms() {
        return useScheduleTerms;
    }

    /**
     * @param useScheduleTerms the useScheduleTerms to set
     */
    public void setUseScheduleTerms(ArrayList<UseScheduleTermDTO> useScheduleTerms) {
        this.useScheduleTerms = useScheduleTerms;
    }

    /**
     * @return the editRow
     */
    public Integer getEditRow() {
        return editRow;
    }

    /**
     * @param editRow the editRow to set
     */
    public void setEditRow(Integer editRow) {
        this.editRow = editRow;
    }

    /**
     * @return the useLinkKey
     */
    public Integer getUseLinkKey() {
        return useLinkKey;
    }

    /**
     * @param useLinkKey the useLinkKey to set
     */
    public void setUseLinkKey(Integer useLinkKey) {
        this.useLinkKey = useLinkKey;
    }
}
