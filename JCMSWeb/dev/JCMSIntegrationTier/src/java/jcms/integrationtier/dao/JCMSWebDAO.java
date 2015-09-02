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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class JCMSWebDAO extends MySQLDAO {

    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    public Result getWorkgroupsForUser(String user) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getJCMSWebConnection();

            String query = "SELECT DISTINCT w.WorkgroupName FROM Workgroup "
                    + "AS w JOIN WorkgroupUser AS wu ON wu._Workgroup_key = "
                    + "w._Workgroup_key JOIN User AS u ON u._User_key = wu._User_key "
                    + "WHERE u.UserName = '" + user +"' AND "
                    + "(u.IsActive = 1 OR u.IsActive = -1) ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "query");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
     private void queryDebug(String method, String message) {

        if (qDebug) {
            getLogger().logDebug(formatLogMessage("JCMSWebDAO." +
                    method, message));
        }
    }
     
     /**
      * getGuestWorkgroupsForUser : Finds the workgroup names for all the workgroups
      * a user has read privileges to (guests, colony managers, and administrators)
      * 
      * @param userName - the name of the user who is logging in.
      */
     public Result getGuestWorkgroupsForUser(String userName) throws Exception{
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;
        
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
        
            //establish connection and initialize prepared statement
            Connection con = this.getConnection();
        try{ 
            query = con.prepareStatement(queryString);    
            query.setString(1, userName);

            rs = query.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "query");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }
     

    /**
      * getColonyManagerWorkgroupsForUser : Finds the workgroup names for all the workgroups
      * a user has write privileges to (colony managers and administrators)
      * 
      * @param userName - the name of the user who is logging in.
      */
    public Result getColonyManagerWorkgroupsForUser(String userName) throws Exception{
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;
        
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
        
        Connection con = this.getConnection();
        try{
            //establish connection and initialize prepared statement
            query = con.prepareStatement(queryString);     
            query.setString(1, userName);

            rs = query.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "query");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }
     
     
    /**
      * getAdministrativeWorkgroupsForUser : Finds the workgroup names for all the workgroups
      * a user has write privileges to (only administrators)
      * 
      * @param userName - the name of the user who is logging in.
      */
    public Result getAdministrativeWorkgroupsForUser(String userName) throws Exception{
        PreparedStatement query;
        ResultSet rs;
        Result resultData = null;

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
                queryDebug("Converting result set into result object", "query");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }
}