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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.exception.FindEntityException;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author rkavitha
 */
public class FilteredQueriesDAO extends MySQLDAO {

    // Recommended by lint if your class is serialiable
    private static final long serialVersionUID = 1002L;

    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    public FilteredQueriesDAO() {
    }
    
    public Result getMiceByOwner(ArrayList<OwnerEntity> ownerLst) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT * FROM Mouse m WHERE "
                    + "m.owner IN (" + inList + ") ORDER BY m.id ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getMiceByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getMiceByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getMiceByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getMatingsByOwner(ArrayList<OwnerEntity> ownerLst) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT * FROM Mating m WHERE "
                    + "m.owner IN (" + inList + ") ORDER BY m.matingID ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getMatingsByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getMatingsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getMatingsByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }
    
    public Result getLittersByOwner(ArrayList<OwnerEntity> ownerLst) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT l.* FROM Litter l " +
                           "WHERE EXISTS ( " +
                           "SELECT 1 FROM Mouse m " +
                           "WHERE m.owner in (" + inList + ") " +
                           "AND l._litter_Key = m._litter_Key) " +
                           "ORDER BY l.litterID ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getLittersByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getLittersByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getLittersByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getPensByOwner(ArrayList<OwnerEntity> ownerLst) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT c.* FROM Container c " +
            "WHERE EXISTS( " +
            "SELECT 1 FROM Mouse m " +
            "WHERE c._container_Key = m._pen_Key AND " +
            "m.owner IN (" + inList + ")) " +
            "ORDER BY c.containerID ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getPensByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getPensByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getPensByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getMouseProtocolsByOwner(ArrayList<OwnerEntity> ownerLst)
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT c.* FROM cv_MouseProtocol c " +
                           "WHERE EXISTS( " +
                           "SELECT 1 FROM Mouse m WHERE " +
                           "m.protocol = c.id AND m .owner IN (" + inList + ")) " +
                           "ORDER BY c.id ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getMouseProtocolsByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getMouseProtocolsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getMouseProtocolsByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getMiceUsageByOwner(ArrayList<OwnerEntity> ownerLst) throws
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        ArrayList<String> inList = new ArrayList<String>();

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                inList.add(ownerLst.get(i).getOwner());
            }

            String query = "SELECT c.* FROM cv_MouseUse c " +
                           "WHERE EXISTS( " +
                           "SELECT 1 FROM MouseUsage mu, Mouse m " +
                           "WHERE (mu._mouse_Key = m._mouse_Key AND c.mouseUse = mu.use) " +
                           "AND m.owner IN (" + inList + ")) " +
                           "ORDER BY mouseUse ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "getMiceUsageByOwner");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getMiceUsageByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getMiceUsageByOwner  \n" + ex);
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
            getLogger().logDebug(formatLogMessage("FilteredQueriesDAO." +
                    method, message));
        }
    }

     
     
     
}
