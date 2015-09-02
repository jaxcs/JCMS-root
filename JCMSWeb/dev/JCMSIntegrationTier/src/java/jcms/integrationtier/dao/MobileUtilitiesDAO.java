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

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author mkamato
 */
public class MobileUtilitiesDAO extends MySQLMobileDAO {
    
    public boolean autheticateSession(String encryptedPW) throws Exception{
        String query = "SELECT COUNT(*) AS good FROM User WHERE PASSWORD(Password_) = ?;";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, encryptedPW);
        ResultSet rs = this.executePreparedStatementQuery(ps);
        
        rs.first();
        //user found, go ahead
        if(rs.getInt("good") > 0){
            return true;
        }
        //no such user exists
        else{
            return false;
        }
    }
}
