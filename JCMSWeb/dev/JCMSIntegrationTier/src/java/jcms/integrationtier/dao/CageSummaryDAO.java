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
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class CageSummaryDAO extends MySQLDAO {

    // Recommended by lint if your class is serialiable
    private static final long serialVersionUID = 1002L;
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    public Result getActivePenCount(CageSummaryDTO dto, String date) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        String whereClause = "";
        String innerWhereClause = "";

        String statusClause = "";
        String roomClause = "";

        String groupByClause = "";
        String selectList = "";
        String pDate = "";
        pDate = date;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // if status is selected
            if (dto.getStatus() != null && !dto.getStatus().getContainerStatus().equals("")
                    && dto.getStatus().getContainerStatus() != null) {
                statusClause = "t._containerStatus_key = '"
                        + dto.getStatus().getContainerStatuskey() + "'";                
            } else if (dto.isIsBillable()) {
                statusClause = "t._containerStatus_key IN ( "
                        + "SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                        + "WHERE c.billable=1 OR c.billable=-1) ";
            }

            // if room is selected
            if (dto.getRoom() != null && !dto.getRoom().getRoomName().equals("")
                    && dto.getRoom().getRoomName() != null) {
                // if all billable status is selected, then check for the status
                // that are billable
                roomClause = "t._room_key = '" + dto.getRoom().getRoomKey() + "'";
            }

            if (!statusClause.equals("")) {
                whereClause += " AND " + statusClause;
            }

            if (!roomClause.equals("")) {
                whereClause += " AND " + roomClause;
            }
            
            // if owner is selected, build the list
            String ownerClause = "";
            // if owner is selected, build the list
            if (dto.getOwners() != null) {
                for (int i = 0; i < dto.getOwners().size(); ++i) {
                    // if last item, then no comma
                    if (i == dto.getOwners().size() - 1) {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'";
                    } else {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'" + " ,";
                    }
                }
            }

            // construct groupby clause
            if (dto.getGroupBy() != null && !dto.getGroupBy().equals("")) {

                if (dto.getGroupBy().equalsIgnoreCase("status")) {
                    groupByClause = " GROUP BY containerStatus ";
                    selectList = ", containerStatus ";
                } else if (dto.getGroupBy().equalsIgnoreCase("room")) {
                    groupByClause = " GROUP BY roomName ";
                    selectList = ", roomName ";
                }
            }

            // in case of full day, Partial Firse day and partial last day, 
            // eliminate current day
            date += " 00:00:00";

            if (dto.getPenBilling() != null && !dto.getPenBilling().equals("")) {
                pDate += " 23:59:59";
                // if PFD, then get pens upto current day and current day as long as 
                // pen status is billable for the current day
                if (dto.getPenBilling().equalsIgnoreCase("Partial First Day")) {
                    innerWhereClause = " WHERE tDt.actionDate <= '" + pDate + "' ";

                    whereClause += "AND t._containerStatus_key NOT IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0) ";
                } // get the pen counts upto before current day and the get pens for 
                // current day as long as they are not billable
                else if (dto.getPenBilling().equalsIgnoreCase("Partial Last Day")) {
                    innerWhereClause = " WHERE tDt.actionDate <= '" + pDate + "' "
                            + " AND tDt._container_key NOT IN ( "
                            + " SELECT t._container_key FROM ContainerHistory t "
                            + " JOIN ( SELECT tDt._container_key, MAX(tDt.actionDate) AS actionDate " 
                            + " FROM ContainerHistory tDt "
                            + " WHERE tDt.actionDate <= '" + pDate + "' "
                            + " GROUP BY tDt._container_key "
                            + " HAVING MAX(tDt.actionDate)) t2 ON t._container_key = t2._container_key "
                            + " AND t.actionDate = t2.actionDate AND t._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0) JOIN Container c ON t._container_key = c._container_key) " 
                            //-- exclude billable status activations only for specific day
                            + " AND tDt._container_key NOT IN ( "
                            + " SELECT _container_key FROM ContainerHistory ch "
                            + " WHERE ch.actionDate BETWEEN '" + date + "' AND '" + pDate + "' "
                            + " AND ch._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable!=0)) "
                            //-- find the retired pens on cpecific day and get previous status
                            + " OR tDt.actionDate IN ( "
                            + " SELECT t.actionDate FROM ContainerHistory t "
                            + " JOIN (SELECT tDt._container_key, max(tDt.actionDate) as "
                            + " actionDate FROM ContainerHistory tDt "
                            + " WHERE tDt.actionDate <= '" + date + "' "
                            + " AND tDt._container_key IN ( "
                            + " SELECT _container_key FROM ContainerHistory c "
                            + " WHERE c.actionDate BETWEEN '" + date + "' AND '" + pDate + "' "
                            + " AND c._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0)) GROUP BY tDt._container_key "
                            + " HAVING MAX(tDt.actionDate)) t2 ON "
                            + " t._container_key = t2._container_key AND t.actionDate = t2.actionDate "
                            + " AND t._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable!=0) JOIN Container c ON t._container_key = c._container_key)";
                } // get the pen counts upto before current day and the get pens for 
                // current day as long as they are not billable
                else if (dto.getPenBilling().equalsIgnoreCase("Full Day")) {
                    innerWhereClause = " WHERE (tDt.actionDate <= '" + pDate + "' "
                            + " AND tDt._container_key NOT IN ( "
                            + " SELECT _container_key FROM ContainerHistory ch "
                            + " WHERE ch.actionDate BETWEEN '" + date + "' AND '" + pDate
                            + "' ))  ";
                    whereClause += "AND t._containerStatus_key NOT IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0) ";
                } // any day
                else if (dto.getPenBilling().equalsIgnoreCase("Any Day")) {
                    innerWhereClause = " WHERE tDt.actionDate <= '" + pDate + "' "
                            + " AND tDt._container_key NOT IN ( "
                            + " SELECT t._container_key FROM ContainerHistory t "
                            + " JOIN ( SELECT tDt._container_key, MAX(tDt.actionDate) AS actionDate " 
                            + " FROM ContainerHistory tDt "
                            + " WHERE tDt.actionDate <= '" + pDate + "' "
                            + " GROUP BY tDt._container_key "
                            + " HAVING MAX(tDt.actionDate)) t2 ON t._container_key = t2._container_key "
                            + " AND t.actionDate = t2.actionDate AND t._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0) JOIN Container c ON t._container_key = c._container_key) "                            
                            //-- find the retired pens on cpecific day and get previous status
                            + " OR tDt.actionDate IN ( "
                            + " SELECT t.actionDate FROM ContainerHistory t "
                            + " JOIN (SELECT tDt._container_key, max(tDt.actionDate) as "
                            + " actionDate FROM ContainerHistory tDt "
                            + " WHERE tDt.actionDate <= '" + date + "' "
                            + " AND tDt._container_key IN ( "
                            + " SELECT _container_key FROM ContainerHistory c "
                            + " WHERE c.actionDate BETWEEN '" + date + "' AND '" + pDate + "' "
                            + " AND c._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable=0)) GROUP BY tDt._container_key "
                            + " HAVING MAX(tDt.actionDate)) t2 ON "
                            + " t._container_key = t2._container_key AND t.actionDate = t2.actionDate "
                            + " AND t._containerStatus_key IN ( "
                            + " SELECT c._containerStatus_key FROM cv_ContainerStatus c "
                            + " WHERE c.billable!=0) JOIN Container c ON t._container_key = c._container_key)";
                }
            }

            // Open connect to database
            con = super.getConnection();

            String query = "SELECT count(distinct containerID) AS Pens"
                    + selectList + " FROM ContainerHistory t "
                    + " JOIN ( "
                    + " SELECT tDt._container_key, MAX(tDt.actionDate) AS actionDate "
                    + " FROM ContainerHistory tDt "
                    + innerWhereClause
                    + " GROUP BY tDt._container_key "
                    + " HAVING MAX(tDt.actionDate) "
                    + " ) t2 ON "
                    + " t._container_key = t2._container_key "
                    + " AND t.actionDate = t2.actionDate "
                    + whereClause
                    + " JOIN Container c on t._container_key = c._container_key "
                    + " LEFT JOIN cv_ContainerStatus cs ON cs._containerStatus_key = t._containerStatus_key "
                    + " LEFT JOIN Room r ON r._room_key = t._room_key "
                    + " LEFT JOIN Mouse m ON m._pen_key = t._container_key WHERE m.owner IN (" + ownerClause + ") "
                    + groupByClause;

            System.out.println(query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActivePlansQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActivePlans");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public int getRetiredKey() throws SQLException {
        ResultSet rs = null;
        int key = 0;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = " SELECT c._containerStatus_key "
                    + "FROM cv_ContainerStatus c "
                    + " WHERE c.containerStatus = 'retired' OR "
                    + " c.containerStatus = 'Retired'";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();
            while (rs.next()) {
                key = rs.getInt("_containerStatus_key");
            }
            System.out.println("retired key " + key);
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return key;
    }
    
    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {

        if (qDebug) {
            getLogger().logDebug(formatLogMessage("CageSummaryDAO."
                    + method, message));
        }
    }
}