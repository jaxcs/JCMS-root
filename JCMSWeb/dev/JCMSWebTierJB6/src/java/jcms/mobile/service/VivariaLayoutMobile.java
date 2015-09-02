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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.jboss.util.Base64;
import java.security.MessageDigest;
import jcms.integrationtier.dao.VivariaLayoutMobileDAO;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author mkamato
 */
@Path("vivariaLayout")
public class VivariaLayoutMobile extends BaseMobile {
    
    VivariaLayoutMobileDAO vlmDAO = new VivariaLayoutMobileDAO();
    
    @Path("getRackDetails/{userName}/{encryptedPW}/{levelKey}")
    @POST
    public String getRackDetails(@PathParam("userName") String userName, 
                                 @PathParam("encryptedPW") String encryptedPW, 
                                 @PathParam("levelKey") Integer levelKey){
        if(this.autheticateRequest(encryptedPW)){
            return vlmDAO.generateVivariaLayoutDetails(levelKey.toString(), userName).toJSONString();
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
    
    @Path("generateLevelTreeData/{userName}/{encryptedPW}")
    @POST
    public String generateLevelTreeData(@PathParam("userName") String userName, @PathParam("encryptedPW") String encryptedPW){
        if(this.autheticateRequest(encryptedPW)){
            return vlmDAO.generateVivariaLayoutTreeData().toJSONString();
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
    
    @Path("getMiceInCage/{userName}/{encryptedPW}/{containerID}")
    @GET
    public String getMiceInCage(@PathParam("userName") String userName, 
                                @PathParam("encryptedPW") String encryptedPW,
                                @PathParam("containerID") String containerID){
        if(this.autheticateRequest(encryptedPW)){
            return vlmDAO.getMiceInCage(containerID, userName).toJSONString();
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
        
    @Path("updateCagePosition/{userName}/{encryptedPW}/{data}")
    @POST
    public String updateCagePosition(@PathParam("userName") String userName,
                                     @PathParam("encryptedPW") String encryptedPW,
                                     @PathParam("data") String data){
        if(this.autheticateRequest(encryptedPW)){
            JSONParser parser = new JSONParser();
            try{
                JSONArray arr = (JSONArray) parser.parse(data);
                JSONObject returnObject = new JSONObject();
                String status = "success";
                for(Object obj : arr){
                    if(vlmDAO.updateCageContainerHistory((JSONObject) obj).equals("error")){
                        status = "error";
                    }
                }
                returnObject.put("status", status);
                return returnObject.toJSONString();
            }
            catch(Exception e){
                JSONObject returnObject = new JSONObject();
                returnObject.put("status", "error");
                returnObject.put("error", e);
                return returnObject.toJSONString();
            }
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
    
    @Path("moveMiceToCage/{userName}/{encryptedPW}/{data}")
    @POST
    public String moveMiceToCage(@PathParam("userName") String userName,
                                     @PathParam("encryptedPW") String encryptedPW,
                                     @PathParam("data") String data){
        if(this.autheticateRequest(encryptedPW)){
            JSONParser parser = new JSONParser();
            try{
                JSONArray arr = (JSONArray) parser.parse(data);
                return vlmDAO.moveMiceToCage(arr).toJSONString();
            }
            catch(Exception e){
                JSONObject returnObject = new JSONObject();
                returnObject.put("status", "error");
                returnObject.put("error", e);
                return returnObject.toJSONString();
            }
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
    
    @Path("addCageToLevel/{userName}/{encryptedPW}/{data}")
    @POST
    public String addCageToLevel(@PathParam("userName") String userName,
                                     @PathParam("encryptedPW") String encryptedPW,
                                     @PathParam("data") String data){
        if(this.autheticateRequest(encryptedPW)){
            JSONParser parser = new JSONParser();
            try{
                JSONObject obj = (JSONObject) parser.parse(data);
                return vlmDAO.addCageToLevel(obj).toJSONString();
            }
            catch(Exception e){
                JSONObject returnObject = new JSONObject();
                returnObject.put("status", "error");
                returnObject.put("error", e);
                return returnObject.toJSONString();
            }
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
    
    @Path("retireCage/{userName}/{encryptedPW}/{data}")
    @POST
    public String retireCage(@PathParam("userName") String userName,
                                @PathParam("encryptedPW") String encryptedPW,
                                @PathParam("data") String data){
        if(this.autheticateRequest(encryptedPW)){
            JSONParser parser = new JSONParser();
            try{
                JSONObject obj = (JSONObject) parser.parse(data);
                return vlmDAO.retireCage(obj).toJSONString();
            }
            catch(Exception e){
                JSONObject returnObject = new JSONObject();
                returnObject.put("status", "error");
                returnObject.put("error", e);
                return returnObject.toJSONString();
            }
        }
        else{
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "error");
            returnObject.put("error", "You do not have permission to access this.");
            return returnObject.toJSONString();
        }
    }
}
