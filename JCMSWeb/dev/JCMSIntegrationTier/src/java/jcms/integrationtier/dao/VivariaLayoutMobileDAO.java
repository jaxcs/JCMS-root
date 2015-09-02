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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import jcms.integrationtier.dto.LevelDTO;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class VivariaLayoutMobileDAO extends MySQLMobileDAO {
    
    LevelDAO lDAO = new LevelDAO();
    
    /**
     * Generates and returns data for the vivaria layout.
     * 
     * @param - levelKey: the key of the level (viv layout) they are requesting
     * @param - userName: the requester's userName
     */
    public JSONObject generateVivariaLayoutDetails(String levelKey, String userName){
        JSONObject returnObject = new JSONObject();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT Container.*, Level.*, ContainerHistory.*, Room.roomName, "
                + "Mouse._mouse_key, Mouse.ID, Mouse.sex, Mouse.owner, "
                + "Strain.strainName, matingStrain.strainName AS matingStrain, crossStatus "
                + "FROM Container "
                + "JOIN ContainerHistory ON ContainerHistory._containerHistory_key = Container._containerHistory_key "
                + "JOIN Level  ON ContainerHistory._level_key = Level._level_key "
                + "JOIN cv_ContainerStatus ON cv_ContainerStatus._containerStatus_key = ContainerHistory._containerStatus_key "
                + "JOIN Room ON Room._room_key = ContainerHistory._room_key "
                + "LEFT JOIN Mouse ON Container._container_key = Mouse._pen_key AND Mouse.lifeStatus = 'A' "
                + "LEFT JOIN Strain ON Mouse._strain_key = Strain._strain_key "
                + "LEFT JOIN Mating ON Mouse._mouse_key = Mating._dam1_key "
                + "LEFT JOIN cv_CrossStatus ON Mating._crossStatus_key = cv_CrossStatus._crossStatus_key "
                + "LEFT JOIN Strain AS matingStrain ON Mating._strain_key = matingStrain._strain_key AND cv_CrossStatus.crossStatus = 'active' "
                + "WHERE containerStatus = 'active' "
                + "AND Level._level_key = ?;";
        
        try{
            con = this.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, levelKey);
            rs = ps.executeQuery();
            int currentX;
            int currentY;
            String currentCage = "";
            String currentCageKey = "";
            JSONObject level = new JSONObject();
            JSONArray cages = new JSONArray();
            returnObject.put("level", level);
            if(rs.next()){
                //set level info...
                level.put("xmax", rs.getString("xmax"));
                level.put("ymax", rs.getString("ymax"));
                level.put("levelId", rs.getString("levelId"));
                level.put("_level_key", rs.getString("_level_key"));
                level.put("cages", cages);
                
                
                /*
                 * initialize cage strain and gender
                 * Sex either M, F, M/F, or breeding
                 * Cage is either Strain of all mice in cage, mixed if multiple
                 * strains in the same cage, or the mating strain if there is a
                 * dam1 in an active mating in the cage
                 */
                //cage strain is the strain of the cage so far
                String cageStrain = "";
                //cage sex is the sex of the cage so far
                String cageSex = "";
                //boolean to say whether cage is mating cage
                boolean isMatingCage = false;
                
                //first cage...
                currentCage = rs.getString("containerID");
                currentCageKey = rs.getString("_container_key");
                currentX = rs.getInt("x");
                currentY = rs.getInt("y");
                JSONObject cage = new JSONObject();
                cage.put("containerID", rs.getString("containerID"));
                
                JSONArray mice = new JSONArray();
                
                //loop
                do{
                    /* CAGE INFO */
                    //new cage so add old cage and update data...
                    if(!currentCage.equals(rs.getString("containerID"))){
                        cage.put("mice", mice);
                        cage.put("matingCage", isMatingCage);
                        if(cageStrain == null){
                            cage.put("cageStrain", "");
                        }
                        else{
                            cage.put("cageStrain", cageStrain);
                        }
                        if(cageSex == null){
                            cage.put("cageSex", "");
                        }
                        else{
                            cage.put("cageSex", cageSex);
                        }
                        cage.put("x", currentX);
                        cage.put("y", currentY);
                        cage.put("containerID", currentCage);
                        cage.put("_container_key", currentCageKey);
                        cages.add(cage);
                        System.out.println("Cage Key: " + currentCageKey);
                        System.out.println("Cage ID: " + currentCage);
                        
                        //reset cage info...
                        currentCage = rs.getString("containerID");
                        currentCageKey = rs.getString("_container_key");
                        currentX = rs.getInt("x");
                        currentY = rs.getInt("y");
                        cage = new JSONObject();
                        mice = new JSONArray();
                        isMatingCage = false;
                        cageStrain = rs.getString("strainName");
                        cageSex = rs.getString("sex");
                    }
                    
                    /* MOUSE INFO */
                    //set mouse information - if no mice in cage handle accordingly
                    if(rs.getString("ID") != null){
                        JSONObject mouse = new JSONObject();
                        mouse.put("ID", rs.getString("ID"));
                        mouse.put("sex", rs.getString("sex"));
                        mouse.put("owner", rs.getString("owner"));

                        /* 
                        * OTHER INFO 
                        * 
                        * This info can only be calculated using all the mouse data
                        * and iterating over the rows...
                        * 
                        * use mouse information to determine things like cage sex
                        * and cage strain with criteria explained in earlier 
                        * comment.
                        * 
                        * 
                        * first the strain, if there is a mating strain available 
                        * then the cage is a mating cage AND the strain of the cage
                        * should be the mating strain.
                        */
                        if(rs.getString("matingStrain") != null){
                            isMatingCage = true;
                            cageStrain = rs.getString("matingStrain");
                        }
                        //if it's not a mating cage, keep checking
                        else if(!isMatingCage){
                            String currentStrain = rs.getString("strainName");
                            if(!currentStrain.equalsIgnoreCase(cageStrain)){
                                cageStrain = "mixed";
                            }
                            else{
                                cageStrain = currentStrain;
                            }
                        }

                        /* Next the sex (rules outlined earlier) */
                        if(isMatingCage){
                            cageSex = "breeding";
                        }
                        else{
                            if(!cageSex.equals(rs.getString("sex"))){
                                cageSex = "F/M";
                            }
                            else{
                                cageSex = rs.getString("sex");
                            }
                        }

                        mice.add(mouse);
                    }
                    else{
                        cage.put("cageStrain", "empty");
                        cage.put("cageSex", "empty");
                    }
                    //add the last cage info
                    if(rs.isLast()){
                        cage.put("mice", mice);
                        cage.put("matingCage", isMatingCage);
                        if(cageStrain == null){
                            cage.put("cageStrain", "");
                        }
                        else{
                            cage.put("cageStrain", cageStrain);
                        }
                        if(cageSex == null){
                            cage.put("cageSex", "");
                        }
                        else{
                            cage.put("cageSex", cageSex);
                        }
                        cage.put("x", currentX);
                        cage.put("y", currentY);
                        cage.put("containerID", currentCage);
                        cage.put("_container_key", currentCageKey);
                        cages.add(cage);
                    }
                } while(rs.next());
            }
            
            else{
                String levelQuery = "SELECT * FROM Level WHERE _level_key = ?";
                ps = con.prepareStatement(levelQuery);
                ps.setString(1, levelKey);
                rs = ps.executeQuery();
                rs.first();
                //set level info...
                level.put("xmax", rs.getString("xmax"));
                level.put("ymax", rs.getString("ymax"));
                level.put("levelId", rs.getString("levelId"));
                level.put("_level_key", rs.getString("_level_key"));
                level.put("cages", cages);
                level.put("status", "success");
            }     
            returnObject.put("status", "success");
            return returnObject;
        }
        catch(Exception e){            
            System.out.println("Error in generateVivariaLayoutDetails: " + e);
            returnObject.put("status", "error");
            returnObject.put("error", e);
            return returnObject;
        }
        finally{
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * Generates the data to create the vivaria layout tree:
     * Room 1
     *  - Level 1
     *  - Level 2
     *      -Rack 1
     * Room 2
     * Room 3
     *  - Another Level
     *  - Level 1
     * 
     * @return A JSONObject representing the hierarchical layout data 
     */
    public JSONObject generateVivariaLayoutTreeData(){        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONObject returnObject = new JSONObject();
        String query = "SELECT * "
                + "FROM Room "
                + "JOIN Level "
                + "ON Room._room_key = Level._room_key "
                + "WHERE Room.isActive != 0 "
                + "AND Level.isActive != 0 "
                + "AND Level.levelRef = 0;";
        try{
            con = this.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            String currentRoom = "";     
            JSONArray rooms = new JSONArray();
            returnObject.put("contents", rooms);
            JSONObject room = new JSONObject();
            JSONArray levels = new JSONArray();
            while(rs.next()){
                if(rs.isFirst()){
                    currentRoom = rs.getString("roomName");
                    //inintialize room json object...
                    room.put("nodes", levels);
                    room.put("text", rs.getString("roomName"));
                    room.put("roomName", rs.getString("roomName"));
                    room.put("_room_key", rs.getString("_room_key"));
                    room.put("color", "gray");
                    room.put("selectable", false);
                    room.put("type", "room");
                }
                /*
                 * check if currentRoom matches row room, if it does keep going
                 * otherwise, add the current Room json object to root and clear
                 * and initialize rooms object for next room.
                 */
                if(!currentRoom.equals(rs.getString("roomName"))){
                    rooms.add(room);
                    room = new JSONObject();          
                    levels = new JSONArray();          
                    room.put("roomName", rs.getString("roomName"));
                    room.put("_room_key", rs.getString("_room_key"));
                    room.put("nodes", levels);
                    room.put("color", "gray");
                    room.put("selectable", false);
                    room.put("text", rs.getString("roomName"));
                    room.put("type", "room");
                }
                JSONObject level = new JSONObject();
                level.put("_level_key", rs.getString("_level_key"));
                level.put("levelRef", rs.getString("levelRef"));
                level.put("levelId", rs.getString("levelId"));
                level.put("_room_key", rs.getString("_level_key"));
                level.put("text", rs.getString("levelId"));
                level.put("nodes", new JSONArray());
                level.put("type", "level");
                buildLevelJSON(level);
                JSONArray children = (JSONArray) level.get("nodes");
                if(children.isEmpty()){
                    level.remove("nodes");
                }
                levels.add(level);
                                
                if(rs.isLast()){
                    rooms.add(room);
                }
            }
            returnObject.put("status", "success");
            return returnObject;
        }
        catch(Exception e){
            System.out.println("Error in generateVivariaLayoutTreeData: " + e);
            returnObject.put("status", "error");
            returnObject.put("error", e);
            return returnObject;
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    private void buildLevelJSON(JSONObject parent){
        for(LevelDTO level : lDAO.getReferencedLevelsByLevelKey(parent.get("_level_key").toString())){
            if(level.getIsActive().equalsIgnoreCase("true")){
                JSONObject child = new JSONObject();
                //initialize child and add it to the parent
                child.put("_level_key", level.getLevel_key());
                child.put("levelRef", level.getLevelRef());
                child.put("levelId", level.getLevelId());
                child.put("_room_key", level.getRoom_key());
                child.put("type", "level");
                child.put("text", level.getLevelId());
                child.put("nodes", new JSONArray());
                JSONArray array = (JSONArray) parent.get("nodes");
                array.add(child);
                buildLevelJSON(child);
                JSONArray children = (JSONArray) child.get("nodes");
                if(children.isEmpty()){
                    child.remove("nodes");
                }
            }
        }
    }
    
    /**
     * Method to get the mice in the cage that the requester has access to.
     * 
     * @param containerID - the container ID of the container you are requesting 
     *                      information for.
     * @param currentUser - the username of the person requesting the mouse info
     * @return 
     */
    public JSONObject getMiceInCage(String containerID, String currentUser){
        JSONObject returnObject = new JSONObject();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> workgroups = this.getColonyManageWorkgroups(currentUser);
        String query = "SELECT Mouse._mouse_key, ID, sex, strainName, birthDate, _pen_key, "
                + "owner, CONCAT_WS('', labSymbol, ' ', allele1, CONCAT('/', allele2)) AS genotype "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "JOIN Strain "
                + "ON Mouse._strain_key = Strain._strain_key "
                + "LEFT JOIN Genotype "
                + "ON Mouse._mouse_key = Genotype._mouse_key "
                + "LEFT JOIN Gene "
                + "ON Genotype._gene_key = Gene._gene_key "
                + "WHERE containerID = ? "
                + "AND owner IN (";
        for(String workgroup : workgroups){
            if(workgroup.equals(workgroups.get(workgroups.size() - 1))){
                query += "?)";                
            }
            else{
                query += "?, ";
            }
        }
        try{
            con = this.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, containerID);
            int idx = 1;
            for(String workgroup : workgroups){
                ps.setString(++idx, workgroup);
            }
            rs = ps.executeQuery();
            JSONArray mice = new JSONArray();
            rs.beforeFirst();
            if(rs.next()){
                JSONObject mouse = new JSONObject();
                String currentMouse = rs.getString("_mouse_key");
                rs.beforeFirst();
                while(rs.next()){
                    //first row...
                    if(rs.isFirst()){
                        mouse.put("_mouse_key", rs.getInt("_mouse_key"));
                        mouse.put("sex", rs.getString("sex"));
                        mouse.put("ID", rs.getString("ID"));
                        mouse.put("strainName", rs.getString("strainName"));
                        mouse.put("birthDate", rs.getString("birthDate"));
                        mouse.put("owner", rs.getString("owner"));
                        mouse.put("_container_key", rs.getString("_pen_key"));
                        mouse.put("genotype", rs.getString("genotype"));
                    }
                    //new mouse
                    else if(!currentMouse.equals(rs.getString("_mouse_key"))){
                        mice.add(mouse);
                        mouse = new JSONObject();
                        currentMouse = rs.getString("_mouse_key");
                        mouse.put("_mouse_key", rs.getInt("_mouse_key"));
                        mouse.put("sex", rs.getString("sex"));
                        mouse.put("ID", rs.getString("ID"));
                        mouse.put("strainName", rs.getString("strainName"));
                        mouse.put("birthDate", rs.getString("birthDate"));
                        mouse.put("owner", rs.getString("owner"));
                        mouse.put("_container_key", rs.getString("_pen_key"));
                        mouse.put("genotype", rs.getString("genotype"));
                    }
                    //same mouse, another genotype
                    else{
                        mouse.put("genotype", mouse.get("genotype") + ", " + rs.getString("genotype"));
                    }
                    if(rs.isLast()){
                        mice.add(mouse);
                    }
                }
            }
            returnObject.put("results", mice);
            returnObject.put("status", "success");
            return returnObject;
        }
        catch(Exception e){
            System.out.println("Error in getMiceInCage: " + e);
            returnObject.put("error", e);
            returnObject.put("status", "error");
            return returnObject;
        }
        finally{            
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(rs != null) {rs.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
    
    /**
     * Update the container history for a container.
     * 
     * @param obj - JSONObject describing the cage and desired cage history
     * @return - string indicating success
     */
    public String updateCageContainerHistory(JSONObject obj){
        //create prepared statement/result set
        Connection con = null;
        PreparedStatement containerHistoryPS = null;
        PreparedStatement containerUpdatePS = null;
        try{
            //get map from json data from client
            Map data = (Map) obj;

            
            con = this.getConnection();
            int chKey = insertContainerHistory(data, con, containerHistoryPS);
            
            String cUpdateQuery = "Update Container SET _containerHistory_key = ?, version = version + 1 WHERE _container_key = ?;";
            containerUpdatePS = con.prepareStatement(cUpdateQuery);
            containerUpdatePS.setInt(1, chKey);
            containerUpdatePS.setString(2, data.get("_container_key").toString());
            containerUpdatePS.executeUpdate();
            return "success";
        }
        catch(Exception e){
            System.out.println("Error in updateCageContainerHistory: " + e);
            return "error";
        }
        finally{
            try{if(containerHistoryPS != null) {containerHistoryPS.close();}}catch(Exception e){};
            try{if(containerUpdatePS != null) {containerUpdatePS.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};            
        }        
    }
    
    private int insertContainerHistory(Map data, Connection con, PreparedStatement containerHistoryPS) throws Exception{
            String chInsertQuery;

            //no container status specified -> active
            if(data.get("_containerStatus_key") == null){
                chInsertQuery = "INSERT INTO ContainerHistory "
                        + "(_containerHistory_key, _room_key, _container_key, actionDate, _containerStatus_key, x, y, z, _level_key, version) "
                        + "VALUES (?, ?, ?, ?, (SELECT _containerStatus_key FROM cv_ContainerStatus WHERE containerStatus = 'active'), ?, ?, ?, ?, ?);";
            }
            else{
                chInsertQuery = "INSERT INTO ContainerHistory "
                        + "(_containerHistory_key, _room_key, _container_key, actionDate, x, y, z, _level_key, version, _containerStatus_key) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            }
            containerHistoryPS = con.prepareStatement(chInsertQuery);
            int chKey = this.getMaxValue("_containerHistory_key", "ContainerHistory") + 1;
            //set insert values... 
            containerHistoryPS.setInt(1, chKey);
            containerHistoryPS.setString(2, data.get("_room_key").toString());
            containerHistoryPS.setString(3, data.get("_container_key").toString());
            containerHistoryPS.setDate(4, new java.sql.Date(new Date().getTime()));
            //if x is null, set to null, else use the x
            if(data.get("x") == null){
                containerHistoryPS.setNull(5, java.sql.Types.INTEGER);
            }
            else{
                containerHistoryPS.setString(5, data.get("x").toString());
            }
            
            //if y is null, set to null, else use the y
            if(data.get("y") == null){
                containerHistoryPS.setNull(6, java.sql.Types.INTEGER);
            }
            else{
                containerHistoryPS.setString(6, data.get("y").toString());
            }
            containerHistoryPS.setInt(7, 1);
            
            //if _level_key is null, set to null, else use the _level_key
            if(data.get("_level_key") == null){
                containerHistoryPS.setNull(8, java.sql.Types.INTEGER);
            }
            else{
                containerHistoryPS.setString(8, data.get("_level_key").toString());
            }
            
            containerHistoryPS.setInt(9, 1);
            //if container status is null, ignore it as it is handled in the query.
            if(data.get("_containerStatus_key") != null){
                containerHistoryPS.setString(10, data.get("_containerStatus_key").toString());
            }
            containerHistoryPS.executeUpdate();
            return chKey;
    }
    
    /**
     * Moves mice into a new cage.
     * 
     * @param arr - an array of mice to be moved with the required cage info.
     * @return - json object indicating success or failure with failure details
     */
    public JSONObject moveMiceToCage(JSONArray arr){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = this.getConnection();
            con.setAutoCommit(false);
            String query = "UPDATE Mouse SET _pen_key = ? WHERE _mouse_key = ?;";
            ps = con.prepareStatement(query);
            
            String newContainerKey = "";
            String oldContainerKey = "";
            
            for(Object obj : arr){
                JSONObject jsonObj = (JSONObject) obj;
                ps.setString(1, jsonObj.get("_container_key").toString());
                ps.setString(2, jsonObj.get("_mouse_key").toString());
                ps.executeUpdate();
                newContainerKey = jsonObj.get("_container_key").toString();
                oldContainerKey = jsonObj.get("_container_key_previous").toString();                
            }
            con.commit();
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "success");
            /* 
             * contains cage sex and strain with strain occupying first index 
             * (cageSpecs[0]) and sex occupying second (cageSpecs[1])
             */
            String[] newCageSpecs = getContainerSpecs(newContainerKey);
            String[] oldCageSpecs = getContainerSpecs(oldContainerKey);
            returnObject.put("newCageStrain", newCageSpecs[0]);
            returnObject.put("newCageSex", newCageSpecs[1]);
            returnObject.put("oldCageStrain", oldCageSpecs[0]);
            returnObject.put("oldCageSex", oldCageSpecs[1]);
            
            return returnObject;
        }
        catch(Exception e){
            JSONObject returnObject = new JSONObject();
            returnObject.put("status", "success");
            returnObject.put("error", e.toString());
            System.out.println("Error in moveMiceToCage: " + e);
            return returnObject;
        }
        finally{
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};            
        } 
    }
    
    /**
     * Gets the 'cage' strain and 'cage' sex
     */
    private String[] getContainerSpecs(String containerKey){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //[0] is strain [1] is sex
        String[] cageSpecs = new String[2];
        try{
            con = this.getConnection();
            String query = "SELECT Strain.strainName, matingStrain.strainName AS matingStrainName, "
                    + "Mouse.sex "
                    + "FROM Container "
                    + "JOIN Mouse "
                    + "ON Mouse._pen_key = Container._container_key "
                    + "JOIN Strain "
                    + "ON Mouse._strain_key = Strain._strain_key "
                    + "LEFT JOIN Mating "
                    + "ON Mouse._mouse_key = Mating._dam1_key "
                    + "LEFT JOIN cv_CrossStatus "
                    + "ON Mating._crossStatus_key = cv_CrossStatus._crossStatus_key AND crossStatus = 'active' "
                    + "LEFT JOIN Strain AS matingStrain "
                    + "ON Mating._strain_key = matingStrain._strain_key "
                    + "WHERE Mouse.lifeStatus = 'A' AND "
                    + "_container_key = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, containerKey);
            rs = ps.executeQuery();
            String strainName = "";
            String sex = "";
            while(rs.next()){
                if(rs.getString("matingStrainName") != null){
                    String strain = rs.getString("matingStrainName");
                    cageSpecs[0] = strain;
                    cageSpecs[1] = "breeding";
                    return cageSpecs;
                }
                if(rs.isFirst()){
                    strainName = rs.getString("strainName");
                    sex = rs.getString("sex");
                }
                else{
                    String thisStrain = rs.getString("strainName");
                    String thisSex = rs.getString("sex");
                    if(!strainName.equals(thisStrain)){
                        strainName = "mixed";
                    }
                    if(!sex.equals(thisSex)){
                        sex = "M/F";
                    }
                }
            }
            cageSpecs[0] = strainName;
            cageSpecs[1] = sex;
            return cageSpecs;
        }
        catch(Exception e){
            cageSpecs[0] = "";
            cageSpecs[1] = "";
            return cageSpecs;
        }
        finally{       
            try{if(rs != null) {rs.close();}}catch(Exception e){};  
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};     
        } 
    }
    
    /**
     * Either creates a new cage or moves an existing cage to the desired level.
     * 
     * @param details - the cage details and level details
     * @return - json object indicating success or failure with failure details 
     *           or cage details in event of success
     */
    public JSONObject addCageToLevel(JSONObject details){
        System.out.println(details.toJSONString());
        JSONObject result = new JSONObject();
        //case one, create new cage
        if((Boolean) details.get("newCage")){
            //create new cage
            String containerID = getNextAvailablePenId();
            System.out.println(getNextAvailablePenId());
            details.put("containerID", containerID);
            result = insertContainer(details);
            result.put("x", details.get("x").toString());
            result.put("y", details.get("y").toString());
            result.put("containerID", containerID);
            updateNextAvailablePenId();
            System.out.println(getNextAvailablePenId());
        }
        else{
            details.put("_container_key", this.getContainerKey(details.get("containerID").toString()));
            //create new container history...
            updateCageContainerHistory(details);
            result.put("_container_key", details.get("_container_key").toString());
            result.put("status", "success");
            result.put("containerID", details.get("containerID").toString());
            result.put("x", details.get("x").toString());
            result.put("y", details.get("y").toString());
            String[] specs = this.getContainerSpecs(details.get("_container_key").toString());
            result.put("cageStrain", specs[0]);
            result.put("cageSex", specs[1]);
        }
        return result;
    }
    
    /**
     * Adds a container to the database.
     * 
     * @param cageDetails - The details of the cage you would like to add.
     * @return - json object indicating success or failure with failure details 
     *           or cage details in event of success
     */
    private JSONObject insertContainer(JSONObject cageDetails){
        Connection con = null;
        PreparedStatement ps = null;
        JSONObject returnObject = new JSONObject();
        
        try{
            con = this.getConnection();
            String insertQuery = "INSERT INTO Container "
                    + "(containerID, containerName, comment, _containerHistory_key) "
                    + "VALUES (?, ?, ?, ?);";
            ps = con.prepareStatement(insertQuery);
            ps.setString(1, cageDetails.get("containerID").toString());
            ps.setString(2, null);
            ps.setString(3, null);
            ps.setInt(4, 1);
            ps.executeUpdate();
            cageDetails.put("_container_key", this.getMaxValue("_container_key", "Container"));
            this.updateCageContainerHistory(cageDetails);
            returnObject.put("_container_key", cageDetails.get("_container_key").toString());
            returnObject.put("status", "success");
            returnObject.put("containerID", cageDetails.get("containerID"));
            returnObject.put("cageStrain", "");
            returnObject.put("cageSex", "");
        }
        catch(Exception e){
            returnObject.put("status", "error");
            returnObject.put("error", e.toString());
            System.out.println("Error in insertContainer: " + e);
        }
        finally{
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};   
            return returnObject;         
        } 
    }
    
    /**
     * Method to retire a cage and exit the mice and retire the matings those 
     * animals are in.
     * 
     * @param jso - JSONObject 
     * @return - json object indicating success or failure with failure details 
     *           or cage details in event of success
     */
    public JSONObject retireCage(JSONObject jso){
        //retire cage...
        JSONObject returnObject = new JSONObject();
        updateCageContainerHistory(jso);
        
        Connection con = null;
        //exit mice
        String query = "UPDATE Mouse SET lifeStatus = ?, cod = ?, exitDate = DATE(NOW()) WHERE _pen_key = ? AND lifeStatus = 'A';";
        PreparedStatement ps = null;
        
        //retire matings associated with given mice...
        String retireMatingsQuery = "UPDATE Mating "
                + "JOIN Mouse AS dam1 ON Mating._dam1_key = dam1._mouse_key "
                + "LEFT JOIN Mouse AS dam2 ON Mating._dam2_key = dam2._mouse_key "
                + "JOIN Mouse AS sire ON Mating._sire_key = sire._mouse_key "
                + "SET _crossStatus_key = (SELECT _crossStatus_key FROM cv_CrossStatus WHERE crossStatus = 'retired'), "
                + "retiredDate = DATE(NOW()) "
                + "WHERE (dam1._pen_key = ?) "
                + "OR (dam2._pen_key = ?) "
                + "OR (sire._pen_key = ?) "
                + "AND Mating.retiredDate IS NULL;";
        PreparedStatement retireMatingsPS = null;
        try{
            con = this.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, jso.get("lifeStatus").toString());
            ps.setString(2, jso.get("cod").toString());
            ps.setString(3, jso.get("_container_key").toString());
            
            //exit mice
            ps.executeUpdate();
            
            retireMatingsPS = con.prepareStatement(retireMatingsQuery);
            retireMatingsPS.setString(1, jso.get("_container_key").toString());
            retireMatingsPS.setString(2, jso.get("_container_key").toString());
            retireMatingsPS.setString(3, jso.get("_container_key").toString());
            
            //retire matings
            retireMatingsPS.executeUpdate();
            
            returnObject.put("status", "success");
        }
        catch(Exception e){
            returnObject.put("status", "error");
            returnObject.put("error", e);
            System.out.println("Error in retireCage: " + e);        
        }
        return returnObject;
    }
    
    private String getNextAvailablePenId(){
        String nextAvailableId = "";
        String query = "SELECT maxPenID FROM dbInfo;";
        nextAvailableId = new Integer(Integer.parseInt(myGet("maxPenId", this.executeJCMSQuery(query).getRows()[0])) + 1).toString();
        return nextAvailableId;
    }
    
    private void updateNextAvailablePenId() {
        try{
            String query = "UPDATE dbInfo SET maxPenID = maxPenID + 1;";
            this.executeJCMSUpdate(query);
        }
        catch(Exception e){
            System.out.println("Error in updateNextAvailablePenId: " + e);            
        }
    }
    
    private String getContainerKey(String containerID){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT _container_key FROM Container WHERE containerID = ?;";
            con = this.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, containerID);
            rs = ps.executeQuery();
            rs.first();
            return rs.getString("_container_key");
        }
        catch(Exception e){
            System.out.println("Error in getContainerKey: " + e);
            return "";
        }
        finally{
            try{if(rs != null) {rs.close();}}catch(Exception e){};   
            try{if(ps != null) {ps.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        } 
    }
}
