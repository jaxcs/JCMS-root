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

import jcms.integrationtier.dto.HistogramDayDTO;
import jcms.integrationtier.dto.HistogramUnitDTO;
import jcms.integrationtier.dto.HistogramDataDTO;
import jcms.integrationtier.dto.HistogramDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dao.DashboardDAO;
import jcms.integrationtier.dao.MobileUtilitiesDAO;
import jcms.integrationtier.dao.MobileDropdownsDAO;
import java.sql.ResultSet;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.*;

/**
 * A class built for the purposes of a utility place to build JSON that can be
 * converted into dropdowns at the client level of the JCMS Web Mobile app.
 * 
 * @author mkamato
 */
@Path("dropdown")
public class DropdownServiceMobile extends BaseMobile {
    
    DashboardDAO dDAO = new DashboardDAO();
    MobileDropdownsDAO mdDAO = new MobileDropdownsDAO();

    @GET
    @Path("test")
    public String getTest(){
        try{
            System.out.println("***************  TEST ********************");
            return "test";
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active strains the user has 
     * access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateStrainDropdownData/{userName}/{encryptedPW}")
    public String getStrains(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getActiveStrains(this.getUserGuestWorkgroups(userName));
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active use schedules the user 
     * has access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateUseScheduleDropdownData/{userName}/{encryptedPW}")
    public String getUseSchedules(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getUseSchedules(this.getUserGuestWorkgroups(userName));
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active workgroups the user has 
     * access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateWorkgroupDropdownData/{userName}/{encryptedPW}")
    public String getWorkgroups(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getGuestWorkgroupData(userName);
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active workgroups the user has 
     * access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateExitStatusDropdownData/{userName}/{encryptedPW}")
    public String getExitStatuses(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getExitStatuses(this.getUserGuestWorkgroups(userName));
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active workgroups the user has 
     * access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateContainerStatusDropdownData/{userName}/{encryptedPW}")
    public String getContainerStatuses(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getContainerStatusData();
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active cause of deaths the user 
     * has access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateCauseOfDeathDropdownData/{userName}/{encryptedPW}")
    public String getCauseOfDeaths(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getCODs(this.getUserGuestWorkgroups(userName));
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
    
    /**
     * A method to return JSON representing all active rooms the user has 
     * access to.
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateRoomDropdownData/{userName}/{encryptedPW}")
    public String getRooms(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mdDAO.getRooms(this.getUserGuestWorkgroups(userName));
            }
            else{
                return "";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
}
