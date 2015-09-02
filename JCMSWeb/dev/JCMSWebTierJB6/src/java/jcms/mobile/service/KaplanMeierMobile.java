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

package jcms.mobile.service;

import jcms.integrationtier.dao.KaplanMeierDAO;
import jcms.integrationtier.dto.KaplanMeierMobileSearchDTO;
import jcms.integrationtier.dto.KaplanMeierChartDTO;
import jcms.integrationtier.dto.KaplanMeierLineDTO;
import jcms.integrationtier.dto.KaplanMeierLineDetailDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import javax.ws.rs.*;

/**
 *
 * @author mkamato
 */
@Path("kaplanMeier")
public class KaplanMeierMobile extends BaseMobile {
    
    String yAxisLabel = "Percent Alive";
    String xAxisLabel = "Days After Start";
    
    /**
     * Service method to build the KaplanMeierMobileSearchDTO that contains all 
     * the filters the user can select on the Client. This method also calls
     * the data access layer and retrieves the data corresponding to the user's
     * specifics and turns the POJO returned by the DAO into JSON that can be 
     * consumed by the client.
     * 
     * @param userName Requester's username which is used to determine 
     * @param encryptedPW Acts as the token key to validate the requester has 
     *                    permission to access data
     * @param strains
     * @param workgroups
     * @param useSchedules
     * @param cods
     * @param rooms
     * @param lifeStatuses
     * @return 
     */
    @Path("generateKaplanMeierData/{userName}/{encryptedPW}")
    @POST
    public String getKaplanMeierData(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW, 
                                    @FormParam("strains") String[] strains, @FormParam("workgroups") String[] workgroups, @FormParam("useSchedules") String[] useSchedules,
                                    @FormParam("cods") String[] cods, @FormParam("rooms") String[] rooms, @FormParam("lifeStatuses") String[] lifeStatuses){
        KaplanMeierDAO kmDAO = new KaplanMeierDAO();
        KaplanMeierMobileSearchDTO kmmDTO = new KaplanMeierMobileSearchDTO();
        //if workgroups is empty use all the workgroups
        if(workgroups.length == 0){
            try{
                List<WorkgroupEntity> wes = this.getUserGuestWorkgroups(userName);
                workgroups = new String[wes.size()];
                int idx = 0;
                for(WorkgroupEntity we : this.getUserGuestWorkgroups(userName)){
                    System.out.println(idx + ", " + we.getWorkgroupName());
                    workgroups[idx] = we.getWorkgroupName();
                    idx++;
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }
        }
        try{
            kmmDTO.setStrains(strains);
            kmmDTO.setWorkgroups(workgroups);
            kmmDTO.setUseSchedules(useSchedules);
            kmmDTO.setCods(cods);
            kmmDTO.setRooms(rooms);
            kmmDTO.setLifeStatuses(lifeStatuses);
            
            //return json data...
            return buildKaplanMeierJSON(kmDAO.findData(kmmDTO));
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
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
        }
        chart.put("children", lines);
        return chart.toJSONString().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
    }
}
