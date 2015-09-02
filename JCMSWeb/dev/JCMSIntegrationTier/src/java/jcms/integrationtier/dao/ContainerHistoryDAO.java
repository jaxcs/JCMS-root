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
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author cnh
 */
public class ContainerHistoryDAO extends MySQLDAO {
    private Connection con = null;
    private String select = "SELECT ch._containerHistory_key as _containerHistory_key, ch._container_key, ch.actionDate, ch._containerStatus_key, " 
        + "r._room_key, r.roomName, r._healthLevelHistory_key "
        + "FROM ContainerHistory ch "
        + "JOIN Room r ON r._room_key = ch._room_key ";
    
    private Result getContainerByContainerKey(Integer containerKey) throws SQLException {
        Result      result      = null;
        ResultSet   resultSet   = null;
        Statement   stmt        = null;

        try {
            // Open connect to jcms_db database
            con = super.getConnection();
            select = "SELECT * FROM Container c "
                + "WHERE c._container_key = " + containerKey ;
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery(select);
            resultSet.beforeFirst();
            result = ResultSupport.toResult(resultSet);
        } catch (SQLException sqle) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, sqle);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::SQLException:: " + "getContainerByContainerKey  \n" + sqle);
        } catch (Exception ex) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::Exception:: " + "getContainerByContainerKey  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return result;
    }

    public Result getContainerHistoryByContainerKey(Integer containerKey) throws SQLException {
        Result      result      = null;
        ResultSet   resultSet   = null;
        Statement   stmt        = null;

        try {
            // Open connect to jcms_db database
            con = super.getConnection();
            select += " WHERE ch._container_key = " + containerKey 
                   +  " ORDER BY ch.actionDate DESC LIMIT 1";
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery(select);
            resultSet.beforeFirst();
            result = ResultSupport.toResult(resultSet);
        } catch (SQLException sqle) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, sqle);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::SQLException:: " + "getContainerHistoryByContainerKey  \n" + sqle);
        } catch (Exception ex) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::Exception:: " + "getContainerHistoryByContainerKey  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return result;
    }

    public ContainerEntity getContainerEntityByContainerKey(Integer containerKey) throws SQLException {
        ContainerEntity entity = new ContainerEntity();
        Result result = this.getContainerByContainerKey(containerKey);
        SortedMap[] map = result.getRows();

        for (int x=0;x < map.length;x++) {
            entity.setContainerKey(Integer.parseInt(map[x].get("_container_key").toString()));
            entity.setContainerID(Integer.parseInt(map[x].get("containerID").toString()));
            entity.setContainerName((map[x].get("containerName") != null ? map[x].get("containerName").toString() : ""));
            entity.setContainerHistorykey(Integer.parseInt(map[x].get("_containerHistory_key").toString()));
            entity.setComment((map[x].get("comment") != null ? map[x].get("comment").toString() : ""));
            break;
        }

        return entity;
    }
    
    public RoomEntity getRoomEntityByContainerKey(Integer containerKey) throws SQLException {
        RoomEntity entity = new RoomEntity();
        Result result = this.getContainerHistoryByContainerKey(containerKey);
        SortedMap[] map = result.getRows();

        for (int x=0;x < map.length;x++) {
            entity.setRoomKey(Integer.parseInt(map[x].get("_Room_key").toString()));
            entity.setRoomName(map[x].get("RoomName").toString());
            entity.setHealthLevelHistorykey(Integer.parseInt(map[x].get("_healthLevelHistory_key").toString()));
            break;
        }

        return entity;
    }
    
    public int UpdateContainerHistory(ContainerHistoryDTO dto) throws SQLException {
        int         result = 0;
        Statement   stmt        = null;
        String      update  = "UPDATE ContainerHistory SET _room_key = " + dto.getRoomKey();
        
        try {
            // Open connect to jcms_db database
            con = super.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = stmt.executeUpdate(update);
        } catch (SQLException sqle) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, sqle);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::SQLException:: " + "getContainerHistoryByContainerKey  \n" + sqle);
        } catch (Exception ex) {
            Logger.getLogger(ContainerHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ContainerHistoryDAO::Exception:: " + "getContainerHistoryByContainerKey  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return result;
    }
    public Boolean isDuplicateDate(ContainerHistoryDTO dto) {
        Result result = null;
        String cmd = " SELECT * FROM ContainerHistory "
                + " WHERE _container_key != " + dto.getContainerHistorykey()
                + "   AND actionDate = '" + this.formatAsMySQLDate(dto.getActionDate()) + "' ";
        try {
            result = executeJCMSQuery(cmd);
        } catch (Exception e) {
            return true;
        }
        return (result.getRowCount() > 0) ;
    }
}
