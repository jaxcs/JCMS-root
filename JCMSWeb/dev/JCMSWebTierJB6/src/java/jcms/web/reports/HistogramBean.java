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

import jcms.web.base.WTBaseBackingBean;
import java.util.Date;
import java.text.SimpleDateFormat;
import jcms.integrationtier.dto.HistogramDayDTO;
import jcms.integrationtier.dto.HistogramUnitDTO;
import jcms.integrationtier.dto.HistogramDataDTO;
import jcms.integrationtier.dto.HistogramDTO;
import jcms.integrationtier.dao.DashboardDAO;
import org.primefaces.context.RequestContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


/**
 *
 * @author mkamato
 */
public class HistogramBean extends WTBaseBackingBean {
    
    DashboardDAO dDAO = new DashboardDAO();
    
    public void buildCageHistogramData(){
        String json = buildJSONData(dDAO.getCageHistogramData(this.getCurrentUserGuestWorkgroups()));
        RequestContext reqCTX = RequestContext.getCurrentInstance();
        reqCTX.addCallbackParam("cageHistogramData", json);
    }
    
    public void buildMouseHistogramData(){
        String json = buildJSONData(dDAO.getMouseHistogramData(this.getCurrentUserGuestWorkgroups()));
        RequestContext reqCTX = RequestContext.getCurrentInstance();
        reqCTX.addCallbackParam("mouseHistogramData", json);
    }
    
    private String buildJSONData(HistogramDTO histogram){
        JSONObject root = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        
        for(HistogramDataDTO data : histogram.getHistogram()){
            JSONArray days = new JSONArray();
            //each datum corresponds to a day (or bar if you prefer)        
            for(HistogramDayDTO datum : data.getHistogramData()){            
                if(datum != null){
                    //each unit corresponds to a portion of the volume of a day, for example
                    JSONObject day = new JSONObject();
                    if(datum.getDay() != null){
                        day.put("date", formatter.format(datum.getDay()));
                    }
                    else{
                        day.put("date", formatter.format(new Date())) ;
                    }
                    JSONArray units = new JSONArray();
                    for(HistogramUnitDTO unitDTO : datum.getUnits()){
                        JSONObject unit = new JSONObject();
                        unit.put("group", unitDTO.getFilterUnit());
                        unit.put("volume", unitDTO.getVolume());
                        units.add(unit);
                    }      
                    day.put("children", units);
                    days.add(day);
                }
            }
            root.put(data.getGroupingUnit(), days);
        }        
        return root.toJSONString().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
    }
}
