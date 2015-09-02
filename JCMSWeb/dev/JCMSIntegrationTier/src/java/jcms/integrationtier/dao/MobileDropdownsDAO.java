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

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Data access layer to get result sets to populate dropdowns in JCMS Mobile 
 * App.
 * @author mkamato
 */
public class MobileDropdownsDAO extends MySQLMobileDAO{
    
    /**
     * A method to return a result set containing all active strains the user
     * has access to.
     * 
     * @param userName - the userName of the user used to determine
     * @return
     * @throws Exception 
     */
    public String getActiveStrains(List<WorkgroupEntity> wgs) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM Strain WHERE isActive != 0;";
            con = this.getConnection();
            ps = con.prepareStatement(query);
            return resultSetToJSON(this.executePreparedStatementQuery(ps), "strains");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * A method to return a result set containing all workgroups the user has
     * access to.
     * 
     * @param wgs
     * @return 
     */
    public String getUseSchedules(List<WorkgroupEntity> wgs) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM UseScheduleTerm WHERE isActive != 0 AND "
                    + "_Workgroup_key IN (";
            for(WorkgroupEntity we : wgs){
                //last
                if(wgs.get(wgs.size() - 1).equals(we)){
                    query += "?);";
                }
                else{
                    query += "?, ";
                }
            }    
            con = this.getConnection();
            ps = con.prepareStatement(query);
            for(int idx = 1; idx <= wgs.size(); idx++){
                ps.setString(idx, wgs.get(idx - 1).getWorkgroupkey().toString());
            }
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "useSchedules");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    
    /**
     * A method to return a result set containing all exit life statuses the 
     * user has access to.
     * 
     * @param wgs
     * @return 
     */
    public String getExitStatuses(List<WorkgroupEntity> wgs) throws Exception {        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM LifeStatus WHERE exitStatus != 0;";
            con = this.getConnection();
            ps = con.prepareStatement(query);
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "lifeStatuses");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * A method to return a result set containing all CODs the user has access to.
     * 
     * @param wgs
     * @return 
     */
    public String getCODs(List<WorkgroupEntity> wgs) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM cv_CauseOfDeath;";
            con = this.getConnection();
            ps = con.prepareStatement(query);
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "cods");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * A method to return a result set containing all exit life statuses the 
     * user has access to.
     * 
     * @param wgs
     * @return 
     */
    public String getRooms(List<WorkgroupEntity> wgs) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM Room WHERE isActive != 0;";
            con = this.getConnection();
            ps = con.prepareStatement(query);
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "rooms");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * A method to get the Workgroup data for workgroups to which user is a 
     * guest.
     * 
     * @param userName
     * @return ResultSet
     * @throws Exception 
     */
    public String getGuestWorkgroupData(String userName) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT DISTINCT w.* "
                    + "FROM Workgroup AS w "
                    + "JOIN WorkgroupUser AS wu "
                    + "ON wu._Workgroup_key = w._Workgroup_key "
                    + "JOIN User AS u "
                    + "ON u._User_key = wu._User_key "
                    + "JOIN WorkgroupUserFunctionalArea AS wufa "
                    + "ON wu._WorkgroupUser_key = wufa._WorkgroupUser_key "
                    + "JOIN FunctionalArea AS fa "
                    + "ON wufa._FunctionalArea_key = fa._FunctionalArea_key "
                    + "WHERE u.UserName = ? "
                    + "AND (u.IsActive = 1 OR u.IsActive = -1) "
                    + "AND fa.FunctionalArea = 'Querying'";
            con = this.getConnection();
            ps = con.prepareStatement(query);   
            ps.setString(1, userName);
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "workgroups");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * A method to get the cv_ContainerStatus data.
     * 
     * @param userName
     * @return ResultSet
     * @throws Exception 
     */
    public String getContainerStatusData() throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM cv_ContainerStatus WHERE billable = 0;";
            con = this.getConnection();
            ps = con.prepareStatement(query);   
            rs = this.executePreparedStatementQuery(ps);
            return resultSetToJSON(rs, "containerStatuses");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * This method turns a resultset into a json string that will be turned into
     * a dropdown on the client. The format will always be something like:
     * {"vocabName": [{"fieldName1":"fieldValue1", 
     *                  "fieldName2":"fieldValue2"},
     *                {"fieldName1":"fieldValue1", 
     *                  "fieldName2":"fieldValue2"}
     *                              .
     *                              .
     *                              .
     *                {"fieldName1":"fieldValue1", 
     *                  "fieldName2":"fieldValue2"}]}
     * 
     * With number of fields varying depending on vocabulary.
     * 
     * @param vocabName - root name of vocabulary to be used at client.
     * @param resultSet - a result set of the desired values to be displayed in 
     *                    the dropdown.
     * @return 
     */
    private String resultSetToJSON(ResultSet resultSet, String vocabName){
        JSONObject root = new JSONObject();
        JSONArray values = new JSONArray();
        try{
            resultSet.beforeFirst();
            while(resultSet.next()){
                //each row will be a new json object added to the values array
                JSONObject value = new JSONObject();
                //iterate over columns 
                for(int columnIdx = 1; columnIdx <= resultSet.getMetaData().getColumnCount(); columnIdx++){
                    String fieldName;
                    String fieldValue;
                    //if it's a date convert to java date then convert to the user's preferred date format
                    if(resultSet.getMetaData().getColumnType(columnIdx) == java.sql.Types.DATE){                        
                        fieldName = resultSet.getMetaData().getColumnName(columnIdx);
                        fieldValue = new SimpleDateFormat(this.determineDateFormat()).format(resultSet.getTimestamp(fieldName));
                    }
                    //otherwise just get it as a string
                    else{
                        fieldName = resultSet.getMetaData().getColumnName(columnIdx);
                        fieldValue = resultSet.getString(fieldName);
                    }
                    value.put(fieldName, fieldValue);
                }
                values.add(value);
            }
            root.put(vocabName, values);
            return root.toJSONString();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
}
