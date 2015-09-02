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

import javax.ws.rs.*;
import java.sql.ResultSet;
import jcms.integrationtier.dao.MobileColonySummaryReportDAO;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Java Class meant to be accessed as web service by anyone intending to build 
 * a colony summary report as outlined by requirement for ColonySummaryReport in
 * JCMS.
 * 
 * @author mkamato
 */
@Path("colonySummary")
public class ColonySummaryReportMobile extends BaseMobile {
    
    /**
     * Service method will consume a username provided by the individual 
     * accessing the service and return to the client data formatted according 
     * to JSON notation for the workgroups the user has at least guest access
     * to. 
     * 
     * @param userName
     * @return 
     */
    @GET
    @Path("generateData/{userName}/{encryptedPW}")
    public String generateColonySummaryData(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        MobileColonySummaryReportDAO mcsrDAO = new MobileColonySummaryReportDAO(userName);    
        try{
            if(this.autheticateRequest(encryptedPW)){
                return mcsrDAO.getColonySummaryJSON();
            }
        }
        catch(Exception e){
            System.out.println("ERROR: " + e);
        }        
        return "";
    } 
}
