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

import java.io.BufferedWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.exception.FindEntityException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.common.GenerateReport;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;

/**
 * JDBCSearchDAO - Class that manages the JDBC queries to MySQL
 *
 * @author rkavitha
 */
public class JDBCSearchDAO extends MySQLDAO implements SearchDAOInterface,
        Serializable {
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private int maxNumberOfRows = 1000;

    Connection con = null;

    public JDBCSearchDAO() {
        con = super.getConnection();
        ResultSet rs = null;
        String x = "Select * From DbSetup Where DbSetup.MTSVar = 'JCMS_WEB_QUERY_MAX_ROWS';";
        String y = "";
        try {
            rs = this.executeQuery(x);
                rs.beforeFirst();
                while ( rs.next() ) {
                    y = rs.getString("MTSValue").trim();
                    maxNumberOfRows = Integer.parseInt(y);
                }

        } catch (SQLException ex) {
            Logger.getLogger(JDBCSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * executeQuery - Simply convert a valid SQL Select statement into a ResultSet
     * 
     * @param query - Text of a SQL query.
     * @return - A JDBC ResultSet
     * @throws SQLException
     */
    public final ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (NullPointerException npe) {
           getLogger().logError(formatLogMessage("NullPointerException thrown.  "
                  + "Check JDBC connection. ", query));
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::NullPointerException:: "
                  + npe);
        } catch (SQLException ex) {
            this.getLogger().logError(this.formatLogMessage("SQLException thrown.  "
                    + "Check select statement: " + query, query));
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::SQLException:: "
                    + query + " \n" + ex);
        }
        return resultSet;
    }

    /**
     * executeQuery - Simply convert a valid SQL Select statement into a Result
     *
     * @param query - Text of a SQL query.
     * @return - A JDBC ResultSet
     * @throws SQLException
     */
    public Result executeReport(String query) throws SQLException {
        // The javax.servlet.jsp.jstl.sql.Result that we return to the webTier
        Result resultData = null;
        ResultSet resultSet = null;
        try {
            // connection opened by executeQuery
            resultSet = executeQuery(query);

            if (resultSet != null) {
                resultData = ResultSupport.toResult(resultSet);
            }
        }
        finally {

        }
        return resultData;
    }

    /**
     *
     * getMouseQueryReport - Given DTO, write out CSV for the Resulting query.
     *
     * Report here means a CSV file written to out.
     *
     * @param query - DTO representing user Query selections.
     * @param out - Where to write the files.
     *
     * @throws SQLException
     */
    @Override
    public void getMouseQueryReport(MouseQueryDTO query, BufferedWriter out) throws SQLException {
        ResultSet resultSet = null;
        GenerateReport report = new GenerateReport();

        try {
            con = super.getConnection();

            resultSet = new MouseQueryDAO(maxNumberOfRows).executeQuery(query);
            if (resultSet != null) {
                report.generateCSVFile(resultSet, out);
            }
        } catch (NullPointerException npe) {
           getLogger().logError(formatLogMessage("NullPointerException thrown.  "
                  + "Check JDBC connection. ", "Mouse query"));
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::NullPointerException:: "
                  + npe);
        } catch (SQLException ex) {
            this.getLogger().logError(this.formatLogMessage("SQLException thrown.  "
                    + "Check select statement: " + query, "Mouse query"));
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::SQLException:: "
                    + query + " \n" + ex);
        }
        finally {
            super.closeConnection(con);
        }
    }

    /**
     * getMouseQueryResults - Given DTO, return a Result from the Resulting query.
     *
     * @param query - DTO representing user Query selections.
     * @return - Return a Result to WebTier
     * @throws SQLException
     */
    @Override
    public ResultDTO getMouseQueryResults(MouseQueryDTO query) throws
            SQLException {
        Result resultData = null;
        ResultDTO retResult = new ResultDTO();
   
        try {
            resultData = new MouseQueryDAO(maxNumberOfRows).query(query);
            retResult.setResult(resultData);
            if ( maxNumberOfRows <= resultData.getRowCount() ) {
                retResult.setMessage("Query returned more than " + Integer.toString(maxNumberOfRows - 1) + " rows, please filter search.");
            }
            
        } catch (NullPointerException npe) {
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::NullPointerException:: "
                    + "Mouse Query  \n" + npe);
        } catch (Exception ex) {
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::SQLException:: "
                    + "Mouse Query  \n" + ex);
        }
        return retResult;
    }

    /**
     *
     * getMatingQueryReport - Given DTO, write out CSV for the Resulting query.
     *
     * Report here means a CSV file written to out.
     *
     * @param query - DTO representing user Query selections.
     * @param out - Where to write the files.
     *
     * @throws SQLException
     */

    @Override
    public void getMatingQueryReport(MatingQueryDTO query, BufferedWriter out)
            throws SQLException {
        ResultSet resultSet = null;
        GenerateReport report = new GenerateReport();

        try {
            resultSet = new MatingQueryDAO(maxNumberOfRows).executeQuery(query);
            if (resultSet != null) {
                report.generateCSVFile(resultSet, out);
            }
        } catch (NullPointerException npe) {
           getLogger().logError(formatLogMessage("NullPointerException thrown.  "
                  + "Check JDBC connection. ", "getLine"));
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::NullPointerException:: "
                    + "Mating Query  \n" + npe);
        } catch (SQLException ex) {
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::SQLException:: "
                    + "Mating Query  \n" + ex);
        }
        finally {
        }
    }

    /**
     * getMatingQueryResults - Given DTO, return a Result from the Resulting query.
     *
     * @param query - DTO representing user Query selections.
     * @return - Return a Result to WebTier
     * @throws SQLException
     */
    @Override
    public ResultDTO getMatingQueryResults(MatingQueryDTO query) throws
            SQLException {
        Result resultData = null;
        ResultDTO retResult = new ResultDTO();
        try {
            resultData = new MatingQueryDAO(maxNumberOfRows).query(query);
            retResult.setResult(resultData);
            if ( maxNumberOfRows <= resultData.getRowCount() ) {
                retResult.setMessage("Query returned more than " + Integer.toString(maxNumberOfRows - 1) + " rows, please filter search.");
            }

        } catch (NullPointerException npe) {
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::NullPointerException:: "
                    + "Mating Query  \n" + npe);
        } catch (Exception ex) {
            throw new FindEntityException("IntegrationTier::JDBCSearchDAO::SQLException:: "
                    + "Mating Query  \n" + ex);
        }
        return retResult;
    }

    /**
     * testConnection - a left over item, it was never implemented 
     * for JCMSWeb and it is never called.  The Should be refactored out of the
     * SearchDAOInterface interface.
     * 
     */
    @Override
    public void testConnection() {

    }
}