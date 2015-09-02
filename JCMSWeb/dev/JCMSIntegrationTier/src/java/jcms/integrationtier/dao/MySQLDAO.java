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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.base.ITBaseObject;
import jcms.integrationtier.utils.RsDate;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;


/**
 * <b>File name:</b>  MySQLDAO.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a MySQL factory generated database connection.     <p>
 * <b>Overview:</b>  Provides a MySQL factory generated database connection.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * 
 * @author Kavitha Rama
 * @version $Revision$
 */        
public class MySQLDAO extends ITBaseObject
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    protected static final String MYSQL_DATE_FORMAT = "%Y-%m-%e %H:%i:%s";
    public static final String SelectMySQLDateFormat = "%m/%d/%Y %H:%i:%s";
    public static final String MySQLDateTimeFormat  = "MM/dd/yyyy HH:mm:ss";
    public static final String MySQLDateFormat      = "MM/dd/yyyy";
    public static final String OracleDateFormat     = "MM/DD/YYYY HH:MI:SS";

    
    /**
     * Creates a new instance of MySQLDAO
     */
    public MySQLDAO () 
    {
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
     *  <b>Purpose:</b>  Gets a database connection via MySQL DAO Factory.  <br>
     *  <b>Overview:</b>  Gets a database connection via MySQL DAO Factory. <br>
     * @return Connection  Contains the MySQL database connection.
     */
    public Connection getJCMSWebConnection()
    {
        MySQLDAOFactory daoFactory = null;
        Connection      connection = null;

        daoFactory = (MySQLDAOFactory) MySQLDAOFactory.getDAOFactory (DAOFactory.MYSQL);
        connection = daoFactory.createJCMSWebConnection();

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
    
    protected int executePreparedStatementUpdate(PreparedStatement theUpdate) throws SQLException {
        try {
            return theUpdate.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(theUpdate.getConnection());
            throw new SQLException("Insert Failed: " + theUpdate);
        } 
    }
    
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
    
    protected Result executeJCMSWebQuery(String theQuery){
        Connection con = null;
        ResultSet myResultSet;
        Result myResult;
        
        try {
            con = this.getJCMSWebConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            this.closeConnection(con);
            return myResult;
         }
         catch (SQLException ex) {
            // Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(con);
            return null;
        }
    }
    
    protected Integer executeJCMSWebUpdate(String theUpdate) throws SQLException {
        Connection con = null;
        Integer myResult = 0;
        
        try {
            con = this.getJCMSWebConnection();
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myResult = stmt.executeUpdate(theUpdate);
            this.closeConnection(con);
            return myResult;
        }
        catch (SQLException ex) {
            // Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            this.closeConnection(con);
            throw new SQLException("Insert Failed: "+ theUpdate);
        } 
    }

    protected String myGet(String field, SortedMap result){
        if(result.get(field) == null){
            return "";
        }
        else{
            return result.get(field).toString();
        }
    }
    
    protected int myGetInt(String field, SortedMap result){
        if(result.get(field) == null){
            return 0;
        }
        else{
            try{
                return Integer.parseInt(result.get(field).toString());
            }
            catch(Exception e){
                System.out.println(e + "\t" + result.get(field).toString());
                return 0;
            }
        }
    }

    protected Float myGetFloat(String field, SortedMap result){
        if(result.get(field) == null){
            return null;
        }
        else{
            return Float.parseFloat(result.get(field).toString());
        }
    } 
    
    protected String myGetNumber(String value) {
        return ((value.length() > 0) ? value : "null") ;
    }
    
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
    
    public Date convertStringToShortDate(String s) 
    {
        Date convertedDate = null;
        if (s != null && s.length() > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(RsDate.MySQLDateFormat);
            try { convertedDate = formatter.parse(s); }
            catch (Exception e) {  } 
        }
        return convertedDate ;
    }
    
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
    
    public String varcharParser(String field){
        if(field.equals("")){
            return "null";
        }
        else{
            field = field.replace("\\", "\\\\");
            field = field.replace("'", "\\'");
            field = field.replace("\"", "\\\"");
            return "'" + field + "'";
        }
    }
    
    public String varcharParserContains(String field){
        if(field.equals("")){
            return "null";
        }
        else{
            field = field.replace("\\", "\\\\");
            field = field.replace("'", "\\'");
            field = field.replace("\"", "\\\"");
            return "'%" + field + "%'";
        }
    }
    
    public String numberParser(String field){
        if(field.equals("")){
            return "null";
        }
        else{
            return field;
        }
    }
    
    public String booleanParser(boolean field){
        if(field){
            return "-1";
        }
        else{
            return "0";
        }
    }
    
    public String dateParser(String date){
        if(date.equals("")){
            return "null";
        }
        else{
            return "DATE_FORMAT('" + date + "', '" + MYSQL_DATE_FORMAT + "')";
        }
    }

    public String getMaxValue(String fieldName, String tableName){
        String query = "SELECT MAX(" + fieldName + ") AS max FROM " + tableName;
        SortedMap max = executeJCMSQuery(query).getRows()[0];
        String value = myGet("max", max);
        if(value.equals("")){
            return "0";
        }
        else{
            return value;
        }
    }
    
    /*
     * Builds ownership string to add on filter for selects to display data, should be
     * in form " AND (Table.owner = 'owner1' OR Table.owner = 'owner2' OR ...) "
     */
    public String buildOwnerFilter(String table, List<String> owners){
        int count = 1;
        String ownerWhere = " AND (";
        for(String owner : owners){
            //last owner, doens't need OR
            if(count == 1){
                ownerWhere += table + ".owner = " + varcharParser(owner);
            }
            else{
                ownerWhere += " OR " + table + ".owner = " + varcharParser(owner);
            }
            count++;
        }
        ownerWhere += ") ";
        return ownerWhere;
    }
    
    /*
     * Builds ownership string to add on filter for selects to display data, should be
     * in form " AND (Table.owner = 'owner1' OR Table.owner = 'owner2' OR ...) "
     */
    public String buildOwnerFilterFromEntities(String table, List<OwnerEntity> owners){
        int count = 1;
        String ownerWhere = " AND (";
        for(OwnerEntity owner : owners){
            //last owner, doens't need OR
            if(count == 1){
                ownerWhere += table + ".owner = " + varcharParser(owner.getOwner());
            } else {
                ownerWhere += " OR " + table + ".owner = " + varcharParser(owner.getOwner()) ;
            }
            count++;
        }
        ownerWhere += ") ";
        return ownerWhere;
    }
    
    public String buildWorkgroupFilter(String table, List<WorkgroupEntity> wgs, boolean and){
        int count = 1;
        String wgWhere;
        if(and){
            wgWhere = " AND (";
        }
        else{
            wgWhere = " (";
        }
        for(WorkgroupEntity wg : wgs){
            //last owner, doens't need OR
            if(count == 1){
                wgWhere += table + "._Workgroup_key = " + wg.getWorkgroupkey();
            }
            else{
                wgWhere += " OR " + table + "._Workgroup_key = " + wg.getWorkgroupkey();
            }
            count++;
        }
        wgWhere += ") ";
        return wgWhere;
    }
}
