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
import jcms.integrationtier.dao.MobileLoginDAO;

/**
 *
 * @author mkamato
 */
@Path("login")
public class Login  {
    @Path("login")
    @POST
    public String login(@FormParam("userName") String username, @FormParam("password") String password, @Context SecurityContext securityContext) {
        System.out.println(securityContext.isSecure());
        System.out.println(securityContext.getUserPrincipal());
        try{
            String token = new MobileLoginDAO().loginCheckDAO(username, encodePassword(password));
            if(token.equals("false")){
                return "false";
            }
            else{
                return token;
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "false";
        }
    }
    
    
    private String encodePassword(String password){
        String pw = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            byte byteData[] = md.digest(password.getBytes());
            pw = Base64.encodeBytes(byteData);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }   
        return pw;
    }
}