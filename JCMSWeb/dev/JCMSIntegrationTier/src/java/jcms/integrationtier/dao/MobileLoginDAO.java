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

package jcms.integrationtier.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author mkamato
 */
public class MobileLoginDAO extends MySQLMobileDAO {
    
    public String loginCheckDAO(String userName, String password){
        
        PreparedStatement loginQuery = null;
        ResultSet result = null;
        Connection con = null;
        String query = "SELECT count(*) AS userExists "
                + "FROM User "
                + "JOIN WorkgroupUser AS wu "
                + "ON User._User_key = wu._User_key "
                + "JOIN WorkgroupUserFunctionalArea AS wufa "
                + "ON wu._WorkgroupUser_key = wufa._WorkgroupUser_key "
                + "WHERE UserName = ? AND Password_ = ? AND User.isActive != 0;";
        try{
            con = this.getConnection();
            loginQuery = con.prepareStatement(query);   

            loginQuery.setString(1, userName);
            loginQuery.setString(2, password);

            ResultSet rs = this.executePreparedStatementQuery(loginQuery);
            rs.first();
            //user found, login away
            if(rs.getInt("userExists") > 0){
                String getToken = "SELECT PASSWORD(?) AS token;";
                con = this.getConnection();
                loginQuery = con.prepareStatement(getToken);   
                loginQuery.setString(1, password);
                result = this.executePreparedStatementQuery(loginQuery);
                result.first();
                return result.getString("token");
            }
            //no such user exists
            else{
                return "false";
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "false";
        }
        finally{
            try{if(result != null) {result.close();}}catch(Exception e){};
            try{if(loginQuery != null) {loginQuery.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
}
