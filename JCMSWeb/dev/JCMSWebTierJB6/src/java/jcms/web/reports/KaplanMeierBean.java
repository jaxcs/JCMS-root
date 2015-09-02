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

package jcms.web.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.middletier.dto.ListSupportDTO;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.dao.KaplanMeierDAO;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.KaplanMeierChartDTO;
import jcms.integrationtier.dto.KaplanMeierLineDTO;
import jcms.integrationtier.dto.KaplanMeierSearchDTO;
import jcms.integrationtier.dto.KaplanMeierLineDetailDTO;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.dto.KaplanMeierViewDTO;
import jcms.web.common.SelectItemWrapper;
import org.primefaces.context.RequestContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.primefaces.event.TransferEvent;  
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author mkamato
 */
public class KaplanMeierBean extends WTBaseBackingBean {
    
    private final String xAxisLabel = "Days After Start";
    private final String yAxisLabel = "Percent Alive";
    private final int totalStyles = 7;
    private ListSupportDTO listSupportDTO = new ListSupportDTO();
    private Integer activeIndex = 0;
    
    private ArrayList<KaplanMeierViewDTO> lines = new ArrayList<KaplanMeierViewDTO>();
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private KaplanMeierViewDTO activeTabData = null;
    private boolean collapsed = false;
    private KaplanMeierDAO kmDAO = new KaplanMeierDAO();
    
    public KaplanMeierBean(){
        addNewTab();
        if (lines.size() > 0)
            activeTabData = lines.get(0);
    }
    
    /**
     * Initializes the tab and then adds it to the tab list
     * 
     * @param dto - a helper view DTO that contains the picklist backing modules
     */
    private void addNewTab(){
        KaplanMeierViewDTO dto = new KaplanMeierViewDTO();
        List<OwnerEntity> owners = new ArrayList<OwnerEntity>();        
        List<UseScheduleTermDTO> useSchedules = new ArrayList<UseScheduleTermDTO>();
        List<StrainEntity> strains = new ArrayList<StrainEntity>();
        List<LifeStatusEntity> lifeStatuses = new ArrayList<LifeStatusEntity>();
        List<CvDietEntity> diets = new ArrayList<CvDietEntity>();
        List<CvCauseOfDeathEntity> cods = new ArrayList<CvCauseOfDeathEntity>();
        List<GeneEntity> genes = new ArrayList<GeneEntity>();
        List<RoomEntity> rooms = new ArrayList<RoomEntity>();
        try{
            for(SelectItem si : wrapper.getActiveUseScheduleItems()){
                if(!si.getValue().toString().isEmpty()){
                    useSchedules.add((UseScheduleTermDTO) si.getValue());
                }
            }
            for(OwnerEntity owner : (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst")){
                owners.add(owner);
            }
            for(SelectItem si : wrapper.getStrainItemsOptional()){
                if(!si.getValue().toString().isEmpty()){
                    strains.add((StrainEntity) si.getValue());
                }
            }
            for(SelectItem si : wrapper.getCvExitStatusItems()){
                if(!si.getValue().toString().isEmpty()){
                    lifeStatuses.add((LifeStatusEntity) si.getValue());
                }
            }
            for(SelectItem si : wrapper.getCvDietItems()){
                if(!si.getValue().toString().isEmpty()){
                    diets.add((CvDietEntity) si.getValue());
                }
            }
            for(SelectItem si : wrapper.getCvCauseOfDeathItems()){
                if(!si.getValue().toString().isEmpty()){
                    cods.add((CvCauseOfDeathEntity) si.getValue());
                }
            }
            for(SelectItem si : wrapper.getGenesItems()){
                if(!si.getValue().toString().isEmpty()){
                    genes.add((GeneEntity) si.getValue());
                }
            }
            for(SelectItem si : wrapper.getCvRoomItems()){
                if(!si.getValue().toString().isEmpty()){
                    rooms.add((RoomEntity) si.getValue());
                }
            }
            
            dto.getOwnersModel().setSource(owners);
            dto.getUseSchedulesModel().setSource(useSchedules);
            dto.getStrainsModel().setSource(strains);
            dto.getLifeStatusesModel().setSource(lifeStatuses);
            dto.getDietModel().setSource(diets);
            dto.getCodModel().setSource(cods);
            dto.getGeneModel().setSource(genes);
            dto.getRoomModel().setSource(rooms);
            
            dto.setTitle("Survival Curve " + new Integer(lines.size() + 1));
            lines.add(dto);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    /**
     * Action listener that adds proper alleles to the allele pick list when a 
     * gene is selected/removed over.
     * 
     * @param te - TransferEvent object containing details of the transfer.
     */
    public void geneTransferListener(TransferEvent te){        
        List<GeneEntity> genes = (List<GeneEntity>) te.getItems();
        List<AlleleEntity> activeTabAllele1Source = (List<AlleleEntity>) this.activeTabData.getAllele1Model().getSource();
        List<AlleleEntity> activeTabAllele2Source = (List<AlleleEntity>) this.activeTabData.getAllele2Model().getSource();
        List<AlleleEntity> activeTabAllele1Target = (List<AlleleEntity>) this.activeTabData.getAllele1Model().getTarget();
        List<AlleleEntity> activeTabAllele2Target = (List<AlleleEntity>) this.activeTabData.getAllele2Model().getTarget();
        if(te.isAdd()){
            try{
                List<AlleleEntity> allelesToAdd = kmDAO.getAllelesByGene(genes);
                activeTabAllele1Source.addAll(allelesToAdd);
                activeTabAllele2Source.addAll(allelesToAdd);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
        else{   
            try{
                List<AlleleEntity> allelesToRemove = kmDAO.getAllelesByGene(genes);
                activeTabAllele1Source.removeAll(allelesToRemove);
                activeTabAllele2Source.removeAll(allelesToRemove);
                activeTabAllele1Target.removeAll(allelesToRemove);
                activeTabAllele2Target.removeAll(allelesToRemove);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    /**
     * Called by backing bean when the active tab is changed to change the active
     * search in the backing bean.
     * 
     * @param tce - TabChangeEvent containing data on the change 
     * @return none
     */
    public void activeTabChangeListener(TabChangeEvent tce){
        activeTabData = (KaplanMeierViewDTO) tce.getData();
    }
    
    /**
     * Called by backing bean when a tab is closed to remove the view dto from the
     * lines object the defines the search.
     * 
     * @param tce - TabCloseEvent containing data on the close 
     * @return none
     */
    public void tabCloseListener(TabCloseEvent tce){
            lines.remove((KaplanMeierViewDTO) tce.getData());
    }
    
    /**
     * Called by backing bean to add a line (tab in view) to the Kaplan Meier
     * chart
     * 
     * @param none
     * @return none
     */
    public void addLine(){
        addNewTab();
    }
    
    /**
     * Called by backing bean to clear the Kaplan Meier chart criteria
     * 
     * @param none
     * @return none
     */
    public void clearSearch(){
        lines = new ArrayList<KaplanMeierViewDTO>();
        addNewTab();
        activeTabData = lines.get(0);
    }
       
    
    /**
     * Builds the Kaplan Meier data by getting a KaplanMeierChartDTO back from the DB.
     * Then passes the DTO to another method to turn it into a JSON string that gets passed to the view.
     * 
     * @param none
     * @return none
     */
    public void buildKaplanMeier(){
        //validate, only continue if no flag (validate returns false)
        RequestContext reqCTX = RequestContext.getCurrentInstance();
        if(!validateKaplanMeierSearch()){
            //collapse the panel with the filter options for me real estate in the view.
            collapsed = true;

            //convert kaplanmeierview dtos to search dtos...
            ArrayList<KaplanMeierSearchDTO> searches = new ArrayList<KaplanMeierSearchDTO>();
            for(KaplanMeierViewDTO viewDTO : lines){
                KaplanMeierSearchDTO search = new KaplanMeierSearchDTO();
                //get all the target filters...
                ArrayList<UseScheduleTermDTO> useSchedules = (ArrayList<UseScheduleTermDTO>) viewDTO.getUseSchedulesModel().getTarget();
                ArrayList<StrainEntity> strains = (ArrayList<StrainEntity>) viewDTO.getStrainsModel().getTarget();
                ArrayList<LifeStatusEntity> lifeStatuses = (ArrayList<LifeStatusEntity>) viewDTO.getLifeStatusesModel().getTarget();
                ArrayList<CvCauseOfDeathEntity> cods = (ArrayList<CvCauseOfDeathEntity>) viewDTO.getCodModel().getTarget();
                ArrayList<CvDietEntity> diets = (ArrayList<CvDietEntity>) viewDTO.getDietModel().getTarget();
                ArrayList<RoomEntity> rooms = (ArrayList<RoomEntity>) viewDTO.getRoomModel().getTarget();
                ArrayList<GeneEntity> genes = (ArrayList<GeneEntity>) viewDTO.getGeneModel().getTarget();
                ArrayList<AlleleEntity> allele1s = (ArrayList<AlleleEntity>) viewDTO.getAllele1Model().getTarget();
                ArrayList<AlleleEntity> allele2s = (ArrayList<AlleleEntity>) viewDTO.getAllele2Model().getTarget();
                /*
                * if no workgroups selected, assume user wants ALL workgroups they 
                * have at least guest access to...                              
                */
                ArrayList<OwnerEntity> owners;
                if(viewDTO.getOwnersModel().getTarget().isEmpty()){
                    owners = (ArrayList<OwnerEntity>) viewDTO.getOwnersModel().getSource();
                }
                else{                
                    owners = (ArrayList<OwnerEntity>) viewDTO.getOwnersModel().getTarget();
                }  

                search.setOwners(owners);
                search.setStrains(strains);
                search.setUseScheduleTerms(useSchedules);
                search.setAllele1s(allele1s);
                search.setAllele2s(allele2s);
                search.setCods(cods);
                search.setColor(viewDTO.getColor());
                search.setDiets(diets);
                search.setGenes(genes);
                search.setLifeStatuses(lifeStatuses);
                search.setLineName(viewDTO.getTitle());
                search.setRooms(rooms);

                searches.add(search);
            }

            //get JSON string...
            try{
                String json = buildKaplanMeierJSON(new KaplanMeierDAO().findData(searches));
                //get request context and add json string. Can be accessed in the view JS as args[0]
                reqCTX.addCallbackParam("jsonData", json);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
        else{
            reqCTX.addCallbackParam("jsonData", "");
        }
    }
    
    /**
     * A method to determine whether all the necessary search criteria are provided.
     * 
     * @return  - a boolean, true if there is a problem, false if there is not.
     */
    private boolean validateKaplanMeierSearch(){
        boolean flag = false;
        if(lines.isEmpty()){
            flag = true;
            this.addToMessageQueue("At least one line is required to create a "
                    + "Kaplan Meier Chart, please add a line.", FacesMessage.SEVERITY_ERROR);            
        }
        for(KaplanMeierViewDTO view : lines){
            System.out.println(view.getColor());
            if(view.getColor().equalsIgnoreCase("FFFFFF") || view.getColor().trim().isEmpty()){
                this.addToMessageQueue("Color is required for every line, "
                        + "please provide a color for line: " + view.getTitle() 
                        + " using the color picker.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
            if(view.getTitle().isEmpty()){
                this.addToMessageQueue("A title is required for each line, "
                        + "please provide a title.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
            if(view.getAllele1Model().getTarget().isEmpty()
                    && view.getAllele2Model().getTarget().isEmpty()
                    && view.getCodModel().getTarget().isEmpty()
                    && view.getDietModel().getTarget().isEmpty()
                    && view.getGeneModel().getTarget().isEmpty()
                    && view.getLifeStatusesModel().getTarget().isEmpty()
                    && view.getOwnersModel().getTarget().isEmpty()
                    && view.getRoomModel().getTarget().isEmpty()
                    && view.getStrainsModel().getTarget().isEmpty()
                    && view.getUseSchedulesModel().getTarget().isEmpty()){
                this.addToMessageQueue("Some filter is required for every line, "
                        + "please provide a filter for line: " + view.getTitle() 
                        + " using the picklists above.", FacesMessage.SEVERITY_ERROR);
                flag = true;                
            }
        }
        return flag;
    }
    
    /**
     * Method that takes the Kaplan Meier chart DTO and parses it into a
     * JSON string. JSON String has 3 'levels' the Chart level that contains all the 
     * chart data (chart title, the individual kaplan meier lines), the lines
     * that contain style class and the line data etc., and the line details that
     * is every point for the line.
     * 
     * @param data a Kaplan Meier Chart DTO containing all the information needed to
     *             build a Kaplan Meier chart
     * @return     A JSON string formatted according to specs in description
     */
    private String buildKaplanMeierJSON(KaplanMeierChartDTO data){
        //chart JSON object is the root, contains only chartName and children
        JSONObject chart = new JSONObject();
        //initialize chart labeling details, at the moment not using the title,
        //but may try to implement later...
        chart.put("chartName", data.getChartTitle());
        chart.put("xLabel", xAxisLabel);
        chart.put("yLabel", yAxisLabel);
        
        //lines will be the 'children' of the chart, each representing a kaplan meier curve
        JSONArray lines = new JSONArray();
        
        /*
         * I handle most of the visualization component building on the server side,
         * meaning that I define things like axis labels, line styles, etc. here on 
         * the server instead of the client.
         */
        //schedule number is used to increment the styles, can go up to 7 then recycles
        Integer scheduleNumber = new Integer(1);
        for(KaplanMeierLineDTO line : data.getLines()){
            //represents one kaplan meier curve
            JSONObject jsonLine = new JSONObject();
            /*In the case where there are no dead mice part of the protocol total mice and max days
              will return the empty string, but we want a number, so check for it and if it is '' 
              set it to 0*/
            //max days - used to build x axis
            if(line.getMaxDays().equals("")){
                jsonLine.put("maxDays", 0);
                jsonLine.put("yMax", 100);
            }
            else{
                jsonLine.put("maxDays", Double.parseDouble(line.getMaxDays()));
                jsonLine.put("yMax", 100);
            }
            //total mice - used to build y axis
            if(line.getTotal().equals("")){
                jsonLine.put("total", 0);
            }
            else{
                jsonLine.put("total", Double.parseDouble(line.getTotal()));
            }
            jsonLine.put("protocolName", line.getLineName());
            //set the class, should be schedule1, schedule2, ..., schedule7
            jsonLine.put("scheduleClass", "schedule" + scheduleNumber.toString());
            
            //set the color...
            jsonLine.put("color", line.getColor());
            
            //the line details, contains the points needed to build chart
            JSONArray details = new JSONArray();
            
            //first point won't be returned by SQL in integration tier so have to hard code here
            JSONObject start = new JSONObject();
            start.put("xValue", "0");
            start.put("countDied", "0");
            //as with max days and and total mice need to check for empty string with count left
            if(line.getTotal().equals("")){
                start.put("countLeft", 0);
                start.put("yValue", 0);
            }
            else{
                start.put("countLeft", Double.parseDouble(line.getTotal()));
                start.put("yValue", 100);
            }
            details.add(start);
            
            //now need to get all the rest of the points for the line...
            for(KaplanMeierLineDetailDTO detail : line.getDetails()){
                JSONObject jsonDetail = new JSONObject();
                jsonDetail.put("xValue", detail.getDaysAfterStart());
                jsonDetail.put("countDied", detail.getCountDied());
                if(detail.getCountLeft().equals("")){
                    jsonDetail.put("countLeft", 0);
                    jsonDetail.put("yValue", 0);
                }
                else{
                    jsonDetail.put("countLeft", Double.parseDouble(detail.getCountLeft()));
                    jsonDetail.put("yValue", Double.parseDouble(detail.getCountLeft()) * 100 / Double.parseDouble(line.getTotal()));
                }
                details.add(jsonDetail);
            }
            jsonLine.put("children", details);
            lines.add(jsonLine);
            
            //increment schedule number
            scheduleNumber = scheduleNumber + 1;
            //if schedule number is 8, reset to one since I only have 7 styles
            if(scheduleNumber.intValue() > totalStyles){
                scheduleNumber = new Integer(1);
            }
        }
        chart.put("children", lines);
        return chart.toJSONString().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
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
     * @return the collapsed
     */
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    /**
     * @return the lines
     */
    public ArrayList<KaplanMeierViewDTO> getLines() {
        return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(ArrayList<KaplanMeierViewDTO> lines) {
        this.lines = lines;
    }

    /**
     * @return the activeIndex
     */
    public Integer getActiveIndex() {
        return activeIndex;
    }

    /**
     * @param activeIndex the activeIndex to set
     */
    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }
    
}
