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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.base.ITBaseObject;
import jcms.integrationtier.utils.RsDate;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;

/**
 * <b>File name:</b>  MySQLMobileDAO.java  <p>
 * <b>Date developed:</b>  July 2014 <p>
 * <b>Purpose:</b>  Provides a MySQL factory generated database connection.     <p>
 * <b>Overview:</b>  Provides a MySQL factory generated database connection.   <p>
 * <b>Last changed by:</b>   $Author: mkamato $ <p>
 * <b>Last changed date:</b> $Date: 2014-07-30 15:05:01 -0400 (Fri, 30 Jul 2014) $   <p>
 * 
 * @author Michael Amato
 * @version $Revision: 24421 $
 */     
public class MySQLMobileDAO {
    
    
    /**
     * Method to determine user's preferred date format from date format DB 
     * Setup variable.
     * 
     * @return String representing the preferred date format 
     */
    public String determineDateFormat(){
        //Find the MM, yyyy, and dd - use the separator variable as well
        return new DbSetupDAO().getJCMSWebDateFormat().getMTSValue();
    }
    
    /**
     *  <b>Purpose:</b>  Gets a database connection via MySQL DAO Factory.  <br>
     *  <b>Overview:</b>  Gets a database connection via MySQL DAO Factory. <br>
     * @return Connection  Contains the MySQL database connection.
     */ 
    public Connection getConnection()
    {
        MySQLDAOFactory daoFactory = null;
        Connection      connection = null;
        
        daoFactory = (MySQLDAOFactory) MySQLDAOFactory.getDAOFactory (DAOFactory.MYSQL);
        connection = daoFactory.createConnection ();
        
        return connection;
    }
    
    /**
     *  <b>Purpose:</b>  Closes a database connection via MySQL DAO Factory.  <br>
     *  <b>Overview:</b>  Closes a database connection via MySQL DAO Factory. <br>
     * @param connection  Contains the database connection.
     */ 
    public void closeConnection(Connection connection)
    {
        new MySQLDAOFactory().closeConnection (connection);
    }
    
    /**
     * A method to run an update against the JCMS database
     * 
     * @param theUpdate - the update SQL statement
     * @return number of rows updated
     * @throws SQLException 
     */
    protected Integer executeJCMSUpdate(String theUpdate) throws SQLException {
        Connection con = null;
        Integer myResult = 0;
        
        try {
            con = this.getConnection();
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myResult = stmt.executeUpdate(theUpdate);
            this.closeConnection(con);
            return myResult;
        }
        catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(con);
            throw new SQLException("Insert Failed: "+ theUpdate);
        } 
    }

    /**
     * Method to execute a query against the JCMS database.
     * 
     * @param theQuery - the query the user wishes to execute
     * @return - A result for theQuery
     */
    protected Result executeJCMSQuery(String theQuery){
        Connection con = null;
        ResultSet myResultSet;
        Result myResult;
        
        try {
            con = this.getConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            this.closeConnection(con);
            return myResult;
         }
         catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(con);
            return null;
        }
    }
    
    /**
     * Method to run an update against the JCMS database using the update 
     * statement parameter and a prepared statement
     * 
     * @param theUpdate - the update statement to be run
     * @throws SQLException 
     */
    protected void executePreparedStatementUpdate(PreparedStatement theUpdate) throws SQLException {
        try {
            theUpdate.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(theUpdate.getConnection());
            throw new SQLException("Insert Failed: " + theUpdate);
        } 
    }
    
    /**
     * Method to run a query against the JCMS Database (no change to the 
     * database will be made) using a prepared statement.
     * 
     * @param theQuery - the Query to be executed
     * @return Result set for theQuery
     * @throws SQLException 
     */
    protected ResultSet executePreparedStatementQuery(PreparedStatement theQuery) throws SQLException {
        try {
            return theQuery.executeQuery();
        }
        catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(theQuery.getConnection());
            throw new SQLException("Query Failed: " + theQuery);
        } 
    }
    
    
    /**
     * Method to get data from sorted map where field is the name of the column
     * and result is the sortedmap representing the row. Will return empty 
     * string '' if column value is null.
     * 
     * @param field - column name
     * @param result - the row
     * @return - the value of that column for that row.
     */
    protected String myGet(String field, SortedMap result){
        if(result.get(field) == null){
            return "";
        }
        else{
            return result.get(field).toString();
        }
    }
    
    /**
     * Converts a string from a mysql result to a date.
     * 
     * @param s String version of the date
     * @return Date object corresponding to date expressed by s
     * @throws ParseException 
     */
    public Date convertStringToDate(String s) throws ParseException
    {
        Date convertedDate = null;

        if (s == null || s.length() == 0) 
            return null;
        
        try {
            if (s.length() <= RsDate.MySQLDateFormat.length())
            {
                SimpleDateFormat formatter = new SimpleDateFormat(RsDate.MySQLDateFormat);
                convertedDate = formatter.parse(s);
            }
            else
            {
                SimpleDateFormat formatter = new SimpleDateFormat(RsDate.MySQLDateTimeFormat);
                convertedDate = formatter.parse(s);
            }
        } catch (ParseException e) {}

        return convertedDate ;
    }   
    
    /**
     * Formats a date object for use in a mysql statement as a string.
     * 
     * @param d - the date object to be converted
     * @return Stringified date in format shown below
     */
    public String formatAsMySQLDate(Date d) 
    {
        String convertedDate = null;

        if (d == null || d.toString().length() == 0) {
            return "";
        }
        if (d.toString().length() <= RsDate.MySQLDateFormat.length())
        {
            convertedDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
        }
        else
        {
            convertedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        }
            
        return convertedDate ;
    }
    
    /**
     * Method to get the workgroups to which the user with given username has at
     * least guest access.
     * 
     * @param userName - the requesters username
     * @return - list of workgroup names
     */
    public List<String> getGuestWorkgroups(String userName){
        List<String> owners = new ArrayList<String>();
        String queryString = "SELECT DISTINCT w.WorkgroupName "
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
        
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;
        Connection con = this.getConnection();
        try{
            //establish connection and initialize prepared statement
            query = con.prepareStatement(queryString);     
            //set ? in queryString to userName
            query.setString(1, userName);

            rs = query.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                resultData = ResultSupport.toResult(rs);
                for(SortedMap workgroup : resultData.getRows()){
                    owners.add(this.myGet("WorkgroupName", workgroup));
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: " + e);
        }
        return owners;
    }
    
    /**
     * Method to get the workgroups to which the user with given username has at
     * least colony manage access.
     * 
     * @param userName - the requesters username
     * @return - list of workgroup names
     */
    public List<String> getColonyManageWorkgroups(String userName){
        List<String> owners = new ArrayList<String>();        
        String queryString = "SELECT DISTINCT w.WorkgroupName "
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
                + "AND (fa.FunctionalArea = 'ColonyManagement'"
                + "OR fa.FunctionalArea = 'Administration')";
        
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;
        Connection con = this.getConnection();
        try{
            //establish connection and initialize prepared statement
            query = con.prepareStatement(queryString);     
            //set ? in queryString to userName
            query.setString(1, userName);

            rs = query.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                resultData = ResultSupport.toResult(rs);
                for(SortedMap workgroup : resultData.getRows()){
                    owners.add(this.myGet("WorkgroupName", workgroup));
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: " + e);
        }
        return owners;
    }
    
    /**
     * Method to get the workgroups to which the user with given username has at
     * least administrative access.
     * 
     * @param userName - the requesters username
     * @return - list of workgroup names
     */
    public List<String> getAdministrativeWorkgroups(String userName){
        List<String> owners = new ArrayList<String>();
        
        String queryString = "SELECT DISTINCT w.WorkgroupName "
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
                + "AND fa.FunctionalArea = 'Administration'";
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;
        Connection con = this.getConnection();
        try{
            //establish connection and initialize prepared statement
            query = con.prepareStatement(queryString);     
            //set ? in queryString to userName
            query.setString(1, userName);

            rs = query.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                resultData = ResultSupport.toResult(rs);
                for(SortedMap workgroup : resultData.getRows()){
                    owners.add(this.myGet("WorkgroupName", workgroup));
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: " + e);
        }
        return owners;
    }
    
    /**
     * A method to get the Workgroup Entities for workgroups to which user is a 
     * guest.
     * 
     * @param userName
     * @return
     * @throws Exception 
     */
    public List<WorkgroupEntity> getGuestWorkgroupEntities(String userName) throws Exception{
        List<WorkgroupEntity> wgs = new ArrayList<WorkgroupEntity>();
        PreparedStatement workgroupsQuery;
        String query = "SELECT DISTINCT * "
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
        Connection con = this.getConnection();
        workgroupsQuery = con.prepareStatement(query);   

        workgroupsQuery.setString(1, userName);
        
        ResultSet rs = this.executePreparedStatementQuery(workgroupsQuery);
        
        return resultSetToWorkgroupList(rs);
    }
    
    /**
     * A method to get the Workgroup Entities for workgroups to which user is a 
     * colony manager.
     * 
     * @param userName
     * @return
     * @throws Exception 
     */
    public List<WorkgroupEntity> getColonyManageWorkgroupEntities(String userName) throws Exception{
        List<WorkgroupEntity> wgs = new ArrayList<WorkgroupEntity>();
        PreparedStatement workgroupsQuery;
        String query = "SELECT DISTINCT * "
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
                + "AND (fa.FunctionalArea = 'ColonyManagement'"
                + "OR fa.FunctionalArea = 'Administration')";
        Connection con = this.getConnection();
        workgroupsQuery = con.prepareStatement(query);   

        workgroupsQuery.setString(1, userName);
        
        ResultSet rs = this.executePreparedStatementQuery(workgroupsQuery);
        
        return resultSetToWorkgroupList(rs);
    }
    
    /**
     * A method to get the Workgroup Entities for workgroups to which user is an 
     * administrator.
     * 
     * @param userName
     * @return
     * @throws Exception 
     */
    public List<WorkgroupEntity> getAdministratorWorkgroupEntities(String userName) throws Exception{
        List<WorkgroupEntity> wgs = new ArrayList<WorkgroupEntity>();
        PreparedStatement workgroupsQuery;
        String query = "SELECT DISTINCT * "
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
                + "AND fa.FunctionalArea = 'Administration'";
        Connection con = this.getConnection();
        workgroupsQuery = con.prepareStatement(query);   

        workgroupsQuery.setString(1, userName);
        
        ResultSet rs = this.executePreparedStatementQuery(workgroupsQuery);
        
        return resultSetToWorkgroupList(rs);
    }
    
    /**
     * A method to convert a result set to a list of WorkgroupEntities.
     * 
     * @param rs
     * @return
     * @throws Exception 
     */
    private List<WorkgroupEntity> resultSetToWorkgroupList(ResultSet rs) throws Exception{
        List<WorkgroupEntity> wgs = new ArrayList<WorkgroupEntity>();
        rs.beforeFirst();
        while(rs.next()){
            WorkgroupEntity we = new WorkgroupEntity();
            we.setCreatedBy(rs.getString("CreatedBy"));
            we.setDateCreated(rs.getDate("DateCreated"));
            we.setDateModified(rs.getDate("DateModified"));
            we.setIsActive((short) 1);
            we.setModifiedBy(rs.getString("ModifiedBy"));
            we.setVersion(rs.getInt("version"));
            we.setWorkgroupName(rs.getString("WorkgroupName"));
            we.setWorkgroupkey(rs.getInt("_Workgroup_key"));
            wgs.add(we);
        }
        return wgs;        
    }
    
    /**
     * A method to convert a result set to a list of OwnerEntities.
     * 
     * @param rs
     * @return
     * @throws Exception 
     */
    private List<OwnerEntity> resultSetToOwnerList(ResultSet rs) throws Exception{
        List<OwnerEntity> owners = new ArrayList<OwnerEntity>();
        rs.beforeFirst();
        while(rs.next()){
            OwnerEntity oe = new OwnerEntity();
            oe.setOwner(rs.getString("owner"));
            oe.setOwnerKey(rs.getInt("_owner_key"));
            oe.setVersion(rs.getInt("version"));
            owners.add(oe);
        }
        return owners;                
    }
    
    /**
     * Method to retrieve the highest value in a column in a table. Usually
     * used in conjunction with inserts when a new insert is done in a table
     * and the auto-incremented key needs to be retrieved.
     * 
     * @param fieldName - the field which you wish to know the max value of
     * @param tableName - the table in which that field exists
     * @return 
     */
    public int getMaxValue(String fieldName, String tableName){
        String query = "SELECT MAX(" + fieldName + ") AS max FROM " + tableName;
        SortedMap max = executeJCMSQuery(query).getRows()[0];
        String value = myGet("max", max);
        if(value.equals("")){
            return 0;
        }
        else{
            return Integer.parseInt(value);
        }
    }
}
