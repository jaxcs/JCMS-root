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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class PenSearchDAO extends MySQLDAO {
    // Recommended by lint if your class is serialiable

    private static final long serialVersionUID = 001002L;
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    public Result getSearchResults(PenSearchDTO dto)
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        Date date = new Date();

        String whereClause = "";
        String outerWhereClause = "";
        String statusClause = "";
        String roomClause = "";
        
        int startRow = 0;
        int endRow = 500;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {

            String penNameFilter = (dto.getContainerName() == null ? "" : dto.getContainerName());
            if (!penNameFilter.trim().isEmpty()) {
                penNameFilter = "c.containerName LIKE '%" + dto.getContainerName() + "%'";    // Filter: surround with "* ... *".
                outerWhereClause += " AND " + penNameFilter;
            }

            String penIDFilter = "";
                        
            // if mating is selected
            String penID = (dto.getPenID() == null || dto.getPenID().equals("") ? "" : dto.getPenID().toString());
                        
            if (!penID.trim().isEmpty()) {       
                String penFilter = (dto.getPenFilter() == null || dto.getPenFilter().equals("") ? "" : dto.getPenFilter());
                
                if (penFilter.equalsIgnoreCase("equals")) {
                    penIDFilter = "c.containerID = " + penID;
                }
                else if (penFilter.equalsIgnoreCase("greater than")) {
                    penIDFilter = "c.containerID > " + penID;
                }
                else if (penFilter.equalsIgnoreCase("less than")) {
                    penIDFilter = "c.containerID < " + penID;
                }
                else {
                    penIDFilter = "c.containerID = " + penID;
                }                                
                outerWhereClause += " AND " + penIDFilter;
            }

            // if status is selected
            if (dto.getContainerStatus() != null && !dto.getContainerStatus().getContainerStatus().equals("")
                    && dto.getContainerStatus().getContainerStatus() != null) {
                statusClause = "t._containerStatus_key = '"
                        + dto.getContainerStatus().getContainerStatuskey() + "'";
            } else if (dto.isIsbillable()) {
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

            // set the actionDate
            if (dto.getActionDate() != null) {
                date = dto.getActionDate();
            }
            
            // (2) create our date "formatter" (the date format we want)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");

            // (3) create a new String using the date format we want
            String currentDt = formatter.format(date);

            // Open connect to database
            con = super.getConnection();
           
            String query = "SELECT t._containerHistory_key AS containerHistoryKey, t.actionDate, c._container_key AS containerKey, c.containerID, c.containerName, cs.containerStatus, r.roomName "
                    + "\n FROM ContainerHistory t  "
                    + "\n JOIN (SELECT tDt._container_key, max(tDt._containerHistory_key) as containerHistoryKey"
                    + "\n       FROM ContainerHistory tDt "
                    + "\n       GROUP BY tDt._container_key "
                    + "\n       HAVING MAX(tDt._containerHistory_key)) t2 on t._container_key = t2._container_key"
                    + "\n     AND t._containerHistory_key = t2.containerHistoryKey "
                    + "\n" + whereClause
                    + "\n JOIN Container c ON t._container_key = c._container_key "
                    + "\n" + outerWhereClause
                    + "\n LEFT JOIN cv_ContainerStatus cs ON cs._containerStatus_key = t._containerStatus_key "
                    + "\n LEFT JOIN Room r ON r._room_key = t._room_key "
                    + "\n LIMIT " + startRow + ", " + endRow;
            
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

    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {
        if (qDebug) {
            getLogger().logDebug(formatLogMessage("PenSearchDAO."
                    + method, message));
        }
    }
}