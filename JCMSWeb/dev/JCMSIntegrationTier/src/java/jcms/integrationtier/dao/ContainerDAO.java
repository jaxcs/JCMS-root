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
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.dto.cvContainerStatusDTO;

/**
 *
 * @author mkamato
 */
public class ContainerDAO extends MySQLDAO {
    private String getActivePensQuery = 
            "SELECT ABS(containerID) AS containerID, TRIM(containerName) AS containerName "
                + "FROM Container "
                + "LEFT JOIN ContainerHistory "
                + "ON Container._containerHistory_key = ContainerHistory._containerHistory_key "
                + "LEFT JOIN cv_ContainerStatus "
                + "ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key "
                + "WHERE containerStatus = 'active' "
                + "ORDER BY containerID ASC;";
    
    private String getContainerByContainerID = "SELECT * FROM Container WHERE containerID = ";
    private String getContainerByContainerKey = "SELECT * FROM Container WHERE _container_key = ";
    private String getContainerHistoryByKey = "SELECT * FROM ContainerHistory WHERE _containerHistory_key = ";
    private String getRoomByKey = "SELECT * FROM Room WHERE _room_key = ";
    private String getContainerStatusByKey = "SELECT * FROM cv_ContainerStatus WHERE _containerStatus_key = ";
    private String getMiceInContainer = 
            "SELECT COUNT(*) AS mice "
                + "FROM Mouse "
                + "JOIN Container ON Mouse._pen_key = Container._container_key "
                + "WHERE containerId = ";
    private String getBoolMiceInContainer = 
            "SELECT ID "
            + "FROM Mouse "
            + "JOIN Container "
            + "ON Mouse._pen_key = Container._container_key "
            + "WHERE containerID = ";
    private String getGendersInContainer = 
            "SELECT sex "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "WHERE containerID = ";

    private String containerSelect = "SELECT c._container_key, c.containerID, c.containerName, c.comment, c._containerHistory_key, c.version, "
            + "\n h._containerHistory_key, h._containerStatus_key, DATE_FORMAT(h.actionDate,'%m/%e/%Y %T') as actionDate, h.version AS hlVersion, "
            + "\n s.containerStatus, r._room_key, r.roomName, r._healthLevelHistory_key, hl.healthLevel, "
            + "\n (SELECT COUNT(h2._containerHistory_key) FROM ContainerHistory h2 WHERE h2._room_key = c._container_key ) as historyCount, "
            + "\n lvl.levelId as levelId "
            + "\n FROM Container c "
            + "\n INNER JOIN ContainerHistory h ON c._containerHistory_key = h._containerHistory_key "
            + "\n INNER JOIN cv_ContainerStatus s ON h._containerStatus_key = s._containerStatus_key "
            + "\n INNER JOIN Room r ON h._room_key = r._room_key "
            + "\n INNER JOIN HealthLevelHistory hlh ON r._healthLevelHistory_key = hlh._healthLevelHistory_key "
            + "\n INNER JOIN cv_HealthLevel hl ON hlh._healthLevel_key = hl._healthLevel_key "
            + "\n LEFT OUTER JOIN `Level` lvl ON h._level_key = lvl._level_key ";
            
    private String containerOrderBy = " ORDER BY c.containerID DESC";
    private String containerWhere = " WHERE c._container_key > 0 ";
    private String containerHistorySelect = " "
            + "\n SELECT h._containerHistory_key, h._room_key, h._container_key, h._containerStatus_key, DATE_FORMAT(h.actionDate,'%m/%e/%Y %T') as actionDate, "
            + "\n h.version, s.containerStatus, r._room_key, r.roomName, r._healthLevelHistory_key, hl.healthLevel, "
            + "\n lvl.levelId as levelId "
            + "\n FROM ContainerHistory h "
            + "\n INNER JOIN cv_ContainerStatus s ON s._containerStatus_key = h._containerStatus_key " 
            + "\n INNER JOIN Room r ON h._room_key = r._room_key "
            + "\n INNER JOIN HealthLevelHistory hlh ON r._healthLevelHistory_key = hlh._healthLevelHistory_key "
            + "\n INNER JOIN cv_HealthLevel hl ON hlh._healthLevel_key = hl._healthLevel_key "
            + "\n LEFT OUTER JOIN `Level` lvl ON h._level_key = lvl._level_key ";
    private String containerHistoryOrderBy = "\n ORDER BY h.actionDate DESC" ;
    
    private Connection con = null;
    
    
    public ContainerDTO getContainerByID(String containerID){
        String query = this.getContainerByContainerID +  containerID;
        ContainerDTO dto = new ContainerDTO();
        SortedMap[] containers = executeQuery(query).getRows();
        if(containers.length > 0){
            SortedMap Container = containers[0];
            dto.setComment(myGet("comment", Container));
            dto.setContainerHistory_key(myGet("_containerHistory_key", Container));
            dto.setContainerID(myGet("containerID", Container));
            dto.setContainerName(myGet("containerName", Container));
            dto.setContainer_key(myGet("_container_key", Container));
        }
        else{
            System.out.println("Container not found");
        }
        
        return dto;
    }
    
    public ContainerDTO getContainerByKey(String containerKey){
        String query = this.getContainerByContainerKey +  containerKey;
        ContainerDTO dto = new ContainerDTO();
        SortedMap[] containers = executeQuery(query).getRows();
        if(containers.length > 0){
            SortedMap Container = containers[0];
            dto.setComment(myGet("comment", Container));
            dto.setContainerHistory_key(myGet("_containerHistory_key", Container));
            dto.setContainerID(myGet("containerID", Container));
            dto.setContainerName(myGet("containerName", Container));
            dto.setContainer_key(myGet("_container_key", Container));
        }
        else{
            System.out.println("Container not found");
        }
        
        return dto;
    }
    
    public ContainerHistoryDTO getContainerHistoryAndRoom(String containerHistoryKey) throws Exception{
        ContainerHistoryDTO dto = new ContainerHistoryDTO();
        if(!containerHistoryKey.equals("")){
            String containerHistoryQuery = this.getContainerHistoryByKey +  containerHistoryKey + ";";
            SortedMap[] containerHistories = executeQuery(containerHistoryQuery).getRows();
            if(containerHistories.length > 0){
                SortedMap ContainerHistory = containerHistories[0];

                dto.setContainerHistorykey(new Integer(Integer.parseInt(containerHistoryKey)));
                dto.setRoomKey(Integer.parseInt(myGet("_room_key", ContainerHistory)));
                dto.setContainerKey(Integer.parseInt(myGet("_container_key", ContainerHistory)));
                dto.setActionDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("actionDate", ContainerHistory)));
                dto.setContainerStatuskey(Integer.parseInt(myGet("_containerStatus_key", ContainerHistory)));
                dto = getRoomByRoomKey(myGet("_room_key", ContainerHistory), dto);
            }
        }
        return dto;
    }
    
    public ContainerHistoryDTO getRoomByRoomKey(String roomKey, ContainerHistoryDTO dto){
        String query = getRoomByKey + roomKey;
        SortedMap[] rooms = executeQuery(query).getRows();
        if(rooms.length > 0){
            SortedMap room = rooms[0];
            dto.setRoomName(myGet("roomName", room));
            dto.setHealthLevelHistorykey(Integer.parseInt(myGet("_healthLevelHistory_key", room)));
        }
        return dto;
    }
    
    public cvContainerStatusDTO getContainerStatusByStatusKey(String key){
        cvContainerStatusDTO dto = new cvContainerStatusDTO();
        String query = getContainerStatusByKey + key + ";";
        SortedMap[] ContainerStatuses = executeQuery(query).getRows();
        if(ContainerStatuses.length > 0){
            SortedMap ContainerStatus = executeQuery(query).getRows()[0];
            dto.setBillable(myGet("billable", ContainerStatus));
            dto.setContainerStatus(myGet("containerStatus",ContainerStatus));
            dto.setContainerStatus_key(myGet("_containerStatus_key", ContainerStatus));
        }
        return dto;
    }
    
    public ArrayList<String> getContainers(){
        ArrayList<String> pens = new ArrayList<String>();
        Result thePens = executeQuery(getActivePensQuery);
        int count = 0 ;
        for(SortedMap pen: thePens.getRows()){
            String penString = myGet("containerID", pen);
            pens.add(penString);
            count++;
        }
        System.out.println("Number of active pens: " + count);
        return pens;
    }
    
    public ArrayList<String> getContainerNames(){
        ArrayList<String> pens = new ArrayList<String>();
        Result thePens = executeQuery(getActivePensQuery);
        int count = 0 ;
        for(SortedMap pen: thePens.getRows()){
            String penString = myGet("containerName", pen);
            pens.add(penString);
            count++;
        }
        System.out.println("Number of active pens: " + count);
        return pens;
    }
    
    public String getMiceInPen(String containerID){
        String query = this.getMiceInContainer + containerID + ";";
        SortedMap[] result = executeQuery(query).getRows();
        if(result.length > 0){
            return myGet("mice", result[0]);
        }
        return "";
    }
    
    public boolean boolGetMiceInPen(String containerID){
        String query = this.getBoolMiceInContainer + containerID + ";";
        SortedMap[] result = executeQuery(query).getRows();
        if(result.length > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean multipleGendersInPen(String penID, String mouseGender){
        String query = this.getGendersInContainer + penID + ";";
        SortedMap[] sexes = executeQuery(query).getRows();
        for(SortedMap sex : sexes){
            if(!mouseGender.equalsIgnoreCase(myGet("sex",sex))){
                return true;
            }
        }
        return false;
    }
    
    public String getContainerKeyForMouseByKey(String key) throws Exception{
        String query = "SELECT _pen_key FROM Mouse WHERE _mouse_key = " + key + ";";
        SortedMap[] pens = executeQuery(query).getRows();
        if(pens.length>0){
            return myGet("_pen_key", pens[0]);
        }
        System.out.println("couldn't a pen for that Mouse...");
        return ""; 
    }
    
    public String updateMouseContainer(String mouseKey, String containerKey) throws Exception{
        String updateQuery = "UPDATE Mouse SET _pen_key = " + containerKey + " WHERE _mouse_key=" + mouseKey;
        executeUpdate(updateQuery).toString();       
        return containerKey;
    }
    
    public String updateContainer(ContainerDTO dto) throws Exception{
        String updateQuery = "UPDATE Container SET "
                + "containerName=" + varcharParser(dto.getContainerName())
                + ", comment=" + varcharParser(dto.getComment()) 
                + ", _containerHistory_key=" + dto.getContainerHistory_key()
                + " WHERE _container_key = " + dto.getContainer_key();
        executeUpdate(updateQuery).toString();
        return dto.getContainer_key();
    }
    
    public String updateContainerHistory(ContainerHistoryDTO dto) throws Exception{
        String cnt ;
        String updateQuery = "UPDATE ContainerHistory SET "
                + "actionDate=DATE_FORMAT('" + dateParser(dto.getActionDate()) + "', '" + super.MYSQL_DATE_FORMAT + "')" 
                + ", _containerStatus_key=" + dto.getContainerStatuskey()
                + ", _room_key=" + dto.getRoomKey()
                + " WHERE _containerHistory_key = " + dto.getContainerHistorykey();
        cnt = executeUpdate(updateQuery).toString();
        this.updateContainersHistory(dto.getContainerKey().toString());
        return cnt;
    }
    
    public String insertContainerHistory(ContainerHistoryDTO dto) throws Exception{
        String insertQuery = "INSERT INTO ContainerHistory (_room_key, _container_key, actionDate, _containerStatus_key) VALUES "
                + "(" + new Integer(dto.getRoomKey()).toString() 
                + ", " + new Integer(dto.getContainerKey()).toString() 
                + ", DATE_FORMAT('" + dateParser(dto.getActionDate()) + "', '" + super.MYSQL_DATE_FORMAT + "')"
                + ", " + new Integer(dto.getContainerStatuskey()).toString() + ");";
        executeUpdate(insertQuery).toString();
        this.updateContainersHistory(dto.getContainerKey().toString());
        return getLatestInsertedValue("_containerHistory_key", "ContainerHistory");
    }
    
    public String onlyInsertContainerHistory(ContainerHistoryDTO dto) throws Exception{
        String insertQuery = "INSERT INTO ContainerHistory (_room_key, _container_key, actionDate, _containerStatus_key) VALUES "
                + "(" + new Integer(dto.getRoomKey()).toString() 
                + ", " + new Integer(dto.getContainerKey()).toString() 
                + ", DATE_FORMAT('" + dateParser(dto.getActionDate()) + "', '" + super.MYSQL_DATE_FORMAT + "')"
                + ", " + new Integer(dto.getContainerStatuskey()).toString() + ");";
        executeUpdate(insertQuery).toString();
        return getLatestInsertedValue("_containerHistory_key", "ContainerHistory");
    }
    
    public String insertLevelContainerHistory(ContainerHistoryDTO dto) throws Exception{
        String insertQuery;
        //if status is null automatically use active
        if(dto.getContainerStatuskey() == null || dto.getContainerStatuskey().equals(0)){
            insertQuery = "INSERT INTO ContainerHistory (_room_key, _container_key, actionDate, _containerStatus_key, x, y, z, _level_key) VALUES "
                    + "(" + new Integer(dto.getRoomKey()).toString() 
                    + ", " + new Integer(dto.getContainerKey()).toString() 
                    + ", DATE_FORMAT('" + dateParser(dto.getActionDate()) + "', '" + super.MYSQL_DATE_FORMAT + "')"
                    + ", (SELECT _containerStatus_key FROM cv_ContainerStatus WHERE containerStatus = 'active') "
                    + ", " + dto.getX() 
                    + ", " + dto.getY() 
                    + ", " + dto.getZ() 
                    + ", " + dto.getLevelKey() + ");";
        }
        else{
            insertQuery = "INSERT INTO ContainerHistory (_room_key, _container_key, actionDate, _containerStatus_key, x, y, z, _level_key) VALUES "
                    + "(" + new Integer(dto.getRoomKey()).toString() 
                    + ", " + new Integer(dto.getContainerKey()).toString() 
                    + ", DATE_FORMAT('" + dateParser(dto.getActionDate()) + "', '" + super.MYSQL_DATE_FORMAT + "')"
                    + ", " + new Integer(dto.getContainerStatuskey()).toString() 
                    + ", " + dto.getX() 
                    + ", " + dto.getY() 
                    + ", " + dto.getZ() 
                    + ", " + dto.getLevelKey() + ");";
        }
        executeUpdate(insertQuery).toString();
        this.updateContainersHistory(dto.getContainerKey().toString());
        return getLatestInsertedValue("_containerHistory_key", "ContainerHistory");
    }
    
    public String updateContainerHistory(String containerHistoryKey, String containerKey) throws Exception{
        String updateQuery = "UPDATE ContainerHistory SET _container_key = " + containerKey;
        executeUpdate(updateQuery).toString();
        return containerHistoryKey;
    }
    
    public String insertContainer(ContainerDTO dto) throws Exception{
        String insertQuery = "INSERT INTO Container (containerID, containerName, comment, _containerHistory_key) VALUES "
                + "(" + dto.getContainerID()
                + ", " + varcharParser(dto.getContainerName())
                + ", " + varcharParser(dto.getComment())
                + ", " + dto.getContainerHistory_key() + ");";
        executeUpdate(insertQuery).toString();
        return getLatestInsertedValue("_container_key", "Container");
    }
    
    public String getNextAvailablePenId(){
        String nextAvailableId = "";
        String query = "SELECT maxPenID FROM dbInfo;";
        nextAvailableId = new Integer(Integer.parseInt(myGet("maxPenId", executeQuery(query).getRows()[0])) + 1).toString();
        return nextAvailableId;
    }
    
    public void updateNextAvailablePenId(String penID) throws Exception{
        String updateQuery = "UPDATE dbInfo SET maxPenID = " + penID + " WHERE _dbinfo_key = 1";
        executeUpdate(updateQuery);
    }
    
    public boolean containerExists(String containerID){
        String query = "SELECT * FROM Container WHERE containerID = " + containerID;
        if(executeQuery(query).getRowCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    //used for getting data from DB
    public Result executeQuery(String theQuery) {
        ResultSet myResultSet;
        Result myResult;

        try {
            con = super.getConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
        } catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);
            super.closeConnection(con);
            return null;
        }
    }
    
    //used for inserting data into DB
    public Integer executeUpdate(String theUpdate) throws SQLException {
        Integer myResult = 0;

        try {
            con = super.getConnection();
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myResult = stmt.executeUpdate(theUpdate);
            super.closeConnection(con);
            return myResult;
        } catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);
            super.closeConnection(con);
            throw new SQLException("Insert Failed: " + theUpdate);
        }
    }

    private String dateParser(Date theDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(theDate);
    }
    
    private String getLatestInsertedValue(String fieldName, String tableName){
        String query = "SELECT MAX(" + fieldName + ") AS max FROM " + tableName;
        SortedMap max = executeQuery(query).getRows()[0];
        return myGet("max", max);
    }

 
    public ArrayList<ContainerDTO> getContainers(ContainerDTO searchCriteria) {
        ArrayList<ContainerDTO> list = new ArrayList<ContainerDTO> ();
        String cmd = containerSelect + containerWhere;

        if (searchCriteria.getContainer_key() != null && searchCriteria.getContainer_key().length() > 0 && !searchCriteria.getContainer_key().equalsIgnoreCase("0")) {
            cmd += " AND c._container_key = "+ searchCriteria.getContainer_key() +" ";
        }
        if (searchCriteria.getContainerID() != null && searchCriteria.getContainerID().length() > 0) {
            cmd += " AND c.containerID LIKE "+ varcharParserContains(searchCriteria.getContainerName()) +" ";
        }
        if (searchCriteria.getContainerName() != null && searchCriteria.getContainerName().length() > 0) {
            cmd += " AND c.containerName LIKE "+ varcharParserContains(searchCriteria.getContainerName()) +" ";
        }
        
        cmd += containerOrderBy ;
        Result result = executeJCMSQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                list.add(this.createContainerDTO(row));
            }
        }
        return list;
    }
    
    public ArrayList<ContainerDTO> getContainersByStrain(String strainKey) {
        ArrayList<ContainerDTO> list = new ArrayList<ContainerDTO> ();
        
        String query = "SELECT DISTINCT(c._container_key), c.containerID, c.containerName, c.comment, c._containerHistory_key, c.version, "
                + "\n h._containerHistory_key, h._containerStatus_key, DATE_FORMAT(h.actionDate,'%m/%e/%Y %T') as actionDate, h.version AS hlVersion, "
                + "\n s.containerStatus, r._room_key, r.roomName, r._healthLevelHistory_key, hl.healthLevel, "
                + "\n (SELECT COUNT(h2._containerHistory_key) FROM ContainerHistory h2 WHERE h2._room_key = c._container_key ) as historyCount "
                + "\n FROM Mouse "
                + "\n INNER JOIN Container c ON Mouse._pen_key = c._container_key "
                + "\n INNER JOIN ContainerHistory h ON c._containerHistory_key = h._containerHistory_key "
                + "\n INNER JOIN cv_ContainerStatus s ON h._containerStatus_key = s._containerStatus_key "
                + "\n INNER JOIN Room r ON h._room_key = r._room_key "
                + "\n INNER JOIN HealthLevelHistory hlh ON r._healthLevelHistory_key = hlh._healthLevelHistory_key "
                + "\n INNER JOIN cv_HealthLevel hl ON hlh._healthLevel_key = hl._healthLevel_key "
                + "\n WHERE containerName IS NOT NULL "
                + "\n AND containerName != '' "
                + "\n AND _strain_key = " + strainKey;
        
        Result result = executeJCMSQuery(query);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                list.add(this.createContainerDTO(row));
            }
        }
        return list;
    }
    
    public ArrayList<ContainerHistoryDTO> getContainerHistory(String containerKey) {
        ArrayList<ContainerHistoryDTO> dtoList = new ArrayList<ContainerHistoryDTO> ();
        ContainerHistoryDTO dto = null;
        String whereClause = "\n WHERE h._container_key = "+ containerKey +" ";
        String cmd = containerHistorySelect + whereClause + containerHistoryOrderBy;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createContainerHistoryDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private ContainerDTO createContainerDTO(SortedMap row) {
        ContainerDTO dto = new ContainerDTO();
        dto.setContainer_key(myGet("_container_key", row));
        dto.setContainerHistory_key(myGet("_containerHistory_key", row));
        dto.setContainerID(myGet("containerID", row));
        dto.setContainerName(myGet("containerName", row));
        dto.setComment(myGet("comment", row));
        dto.setVersion(myGet("version", row));
        dto.setHistoryCount(myGet("historyCount", row));

        ContainerHistoryDTO hlhDTO = this.createContainerHistoryDTO(row);
        dto.setCageHistoryDTO(hlhDTO);

        return dto;
    }

    private ContainerHistoryDTO createContainerHistoryDTO(SortedMap row) {
        Date actionDate = null;
        ContainerHistoryDTO hlhDTO = new ContainerHistoryDTO();
        hlhDTO.setContainerHistorykey(Integer.parseInt(myGet("_containerHistory_key", row)));
        hlhDTO.setRoomKey(Integer.parseInt(myGet("_room_key", row)));
        hlhDTO.setRoomName(myGet("roomName", row));
        hlhDTO.setContainerKey(Integer.parseInt(myGet("_container_key", row)));
        hlhDTO.setContainerStatuskey(Integer.parseInt(myGet("_containerStatus_key", row)));
        hlhDTO.setContainerStatus(myGet("containerStatus", row));
        hlhDTO.setHealthLevelHistorykey(Integer.parseInt(myGet("_healthLevelHistory_key", row)));
        hlhDTO.setHealthLevelHistory(myGet("healthLevel", row));
        String strActionDate = myGet("actionDate", row).toString();
        try { actionDate = (strActionDate != null ? this.convertStringToDate(strActionDate) : null);
        } catch (Exception e) { }
        hlhDTO.setActionDate(actionDate);
        hlhDTO.setLevelId(myGet("levelId", row));
        
        return hlhDTO;
    }
    
    public String saveContainer(ContainerDTO dto) throws Exception {
        String key = "0";
            this.insertContainerHistory(dto.getCageHistoryDTO());
            this.updateContainer(dto);
            this.updateContainersHistory(dto.getContainer_key());
        return key;
    }
    
    public Integer updateContainersHistory(String containerKey) throws SQLException {
        Integer count = 0;
        String cmd = "UPDATE Container SET _containerHistory_key = "
            + " (SELECT h._containerHistory_key "
            + "  FROM ContainerHistory h "
            + "  WHERE h._container_key = "+ containerKey +" AND h.actionDate = "
            + "     (SELECT MAX(actionDate) FROM ContainerHistory h2 WHERE h2._container_key = "+ containerKey +") LIMIT 1) "
            + "WHERE _container_key = "+ containerKey ;
        try {
            count = executeJCMSUpdate(cmd);
        } catch (Exception e) {
            // There is a unique index on room._healthLevelHistory_key called _currentHealthLevelHistory_key
            // This index throws an exception if the history key is set to the save value.
            System.out.println("\nUpdateContainerHistory Exception:  "+ e);
        }
        return count;
    }
    
    public Integer deleteContainer(ContainerDTO dto) throws SQLException {
        String cmd = "DELETE FROM Container " + 0
            + " WHERE _container_key = "+ dto.getContainer_key() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }

    public Boolean deleteContainerHistory(ContainerHistoryDTO dto) throws SQLException {
        String cmd = " DELETE FROM ContainerHistory "
                   + " WHERE _containerHistory_key = " + dto.getContainerHistorykey() ;
        Integer count = executeJCMSUpdate(cmd);
        this.updateContainersHistory(dto.getContainerKey().toString());
        return (count == 1);
    }
    
    public Boolean isDuplicateContainer(String roomKey, String containerName) {
        Result result = null;
        String cmd = "";
        
        if (roomKey.equalsIgnoreCase("0")) {
            cmd = "SELECT * FROM Container WHERE TRIM(containerName) =  "+ varcharParser(containerName.trim()) +" ";
            result = executeJCMSQuery(cmd);
            if (result != null) {
                if (result.getRowCount() > 0) {
                    return true;
                }
            }
        }
        
        cmd = "SELECT * FROM Container WHERE _container_key = "+ roomKey +" AND TRIM(containerName) =  "+ varcharParser(containerName.trim()) +" ";
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() == 1) {
                // Same room
                return false;
            }
        }
        
        cmd = "SELECT * FROM Container WHERE TRIM(containerName) =  "+ varcharParser(containerName.trim()) +" ";
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() > 0) {
                return true;
            }
        }
            
        return false;
    }
    
    public Boolean isSameDate(ContainerDTO dto) {
        Result result = null;
        String cmd = " SELECT * FROM ContainerHistory "
                + " WHERE _container_key = " + dto.getContainer_key()
                + "   AND actionDate = '" + this.formatAsMySQLDate(dto.getCageHistoryDTO().getActionDate()) + "' ";
        try {
            result = executeJCMSQuery(cmd);
        } catch (Exception e) {
            return true;
        }
        return (result.getRowCount() > 0) ;
    }
    public Boolean isMaxStartDate(ContainerDTO dto) {
        Result result = null;
        String cmd = " SELECT * "
                   + " FROM Container r "
                   + " INNER JOIN ContainerHistory h ON c._containerHistory_key = h._containerHistory_key "
                   + " WHERE containerName = "+ varcharParser(dto.getContainerName()) 
                   + "   AND h.actionDate > '" + this.formatAsMySQLDate(dto.getCageHistoryDTO().getActionDate()) + "' ";
        if (dto.getContainer_key().equalsIgnoreCase("0")) 
            return true;
        try {
            result = executeJCMSQuery(cmd);
        } catch (Exception e) {
            return true;
        }
        return (result.getRowCount() == 0) ;
    }


}
