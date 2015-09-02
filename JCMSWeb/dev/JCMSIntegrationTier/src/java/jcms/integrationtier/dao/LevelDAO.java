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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SortedMap;
import jcms.integrationtier.dto.LevelDTO;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class LevelDAO extends MySQLDAO {
    
    public ArrayList<LevelDTO> getBaseLevelsByRoomKey(String roomKey){
        ArrayList<LevelDTO> levels = new ArrayList<LevelDTO>();
        String query = "SELECT * FROM Level WHERE levelRef = 0 AND _room_key = " + roomKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap level : rows){
            LevelDTO dto = new LevelDTO();
            dto.setLevelDetail(myGet("levelDetail", level));
            dto.setLevelId(myGet("levelId", level));
            dto.setLevelRef(myGet("levelRef", level));
            dto.setLevel_key(myGet("_level_key", level));
            dto.setRoom_key(myGet("_room_key", level));
            dto.setXmax(myGet("xmax", level));
            dto.setYmax(myGet("ymax", level));
            dto.setZmax(myGet("zmax", level));
            dto.setLabel(myGet("levelId", level));
            dto.setIsActive(myGet("isActive", level));
            levels.add(dto);
        }        
        return levels;
    }
    
    public ArrayList<LevelDTO> getReferencedLevelsByLevelKey(String levelKey){
        ArrayList<LevelDTO> levels = new ArrayList<LevelDTO>();
        String query = "SELECT * FROM Level WHERE levelRef = " + levelKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap level : rows){
            LevelDTO dto = new LevelDTO();
            dto.setLevelDetail(myGet("levelDetail", level));
            dto.setLevelId(myGet("levelId", level));
            dto.setLevelRef(myGet("levelRef", level));
            dto.setLevel_key(myGet("_level_key", level));
            dto.setRoom_key(myGet("_room_key", level));
            dto.setXmax(myGet("xmax", level));
            dto.setYmax(myGet("ymax", level));
            dto.setZmax(myGet("zmax", level));
            dto.setLabel(myGet("levelId", level));
            dto.setIsActive(myGet("isActive", level));
            levels.add(dto);
        }     
        return levels;
    }
    
    public boolean getMouseExists(String mouseID){
        String query = "SELECT * FROM Mouse WHERE ID = '" + mouseID + "';";
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        return false;
    }
    
    public String getMatingInCage(String containerID){
        String matingID = "";
        String query = "SELECT matingID "
                + "\n FROM Mating "
                + "\n JOIN Mouse ON Mating._dam1_key = Mouse._mouse_key "
                + "\n JOIN Container ON Mouse._pen_key = Container._container_key "
                + "\n WHERE containerID = " + containerID;
        for(SortedMap mID : this.executeJCMSQuery(query).getRows()){
            matingID = myGet("matingID", mID);
        }
        return matingID;
    }
    
    public void retireMating(String cageID) throws Exception {
        String query = "UPDATE Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam1_key " +
                "JOIN Container " +
                "ON Mouse._pen_key = Container._container_key " +
                "JOIN ContainerHistory " +
                "ON Container._containerHistory_key = ContainerHistory._containerHistory_key " +
                "SET retiredDate = " + this.dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) +
                ", _crossStatus_key = (SELECT _crossStatus_key FROM cv_CrossStatus WHERE crossStatus = 'retired') " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND Container.containerID = " + cageID;
        this.executeJCMSUpdate(query);
    }
    
    public ArrayList<String> getCageUnits(String strainKey, ArrayList<String> owners){
        
        ArrayList<String> cages = new ArrayList<String>();
        String query = "SELECT DISTINCT(containerID) "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "WHERE Mouse.lifeStatus = 'A' "
                + buildOwnerFilter("Mouse", owners)
                + "AND Mouse._strain_key = " + strainKey;        
        SortedMap[] units = this.executeJCMSQuery(query).getRows();
        for(SortedMap map : units){
            cages.add(myGet("containerID", map));
        }
        return cages;
    }
    
    public void updateLevelRef(String parentKey, String childKey, String roomKey) throws Exception{
        //need to update the parent key and the storage facility key
            String query = "UPDATE Level SET "
                    + "levelRef = " + numberParser(parentKey) + ", "
                    + "_room_key = " + numberParser(roomKey)
                    + " WHERE _level_key = " + numberParser(childKey);
            this.executeJCMSUpdate(query);        
    }
    
    public void updateLevel(LevelDTO dto) throws Exception{
        String query = "UPDATE Level SET "
                + "levelId = " + varcharParser(dto.getLevelId()) + ", "
                + "levelDetail = " + varcharParser(dto.getLevelDetail()) + ", "
                + "xmax = " + numberParser(dto.getXmax()) + ", "
                + "ymax = " + numberParser(dto.getYmax())
                + " WHERE _level_key = " + numberParser(dto.getLevel_key());
        this.executeJCMSUpdate(query);
    }
    
    public String insertLevel(LevelDTO dto) throws Exception {
        String key = this.getMaxValue("_level_key", "Level");
        key = new Integer(Integer.parseInt(key)+1).toString();
        if(dto.getLevelRef().equals("")){
            dto.setLevelRef("0");
        }
        String query = "INSERT INTO Level "
                + "(_level_key, levelId, levelDetail, _room_key, levelRef, xmax, ymax) "
                + "VALUES ("
                + key + ", "
                + varcharParser(dto.getLevelId()) + ", "
                + varcharParser(dto.getLevelDetail()) + ", "
                + numberParser(dto.getRoom_key()) + ", "
                + numberParser(dto.getLevelRef()) + ", " 
                + numberParser(dto.getXmax()) + ", "
                + numberParser(dto.getYmax()) +  ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public void deleteLevel(String levelKey) throws Exception {
        String query = "DELETE FROM Level WHERE _level_key = " + levelKey;
        this.executeJCMSUpdate(query);
    }
    
    public ArrayList<ContainerDTO> getContainersByLevel(String levelKey){
        ArrayList<ContainerDTO> containers = new ArrayList<ContainerDTO>();
        String query = "SELECT Container._container_key, Container.containerID, Container.containerName, " +
                    "Container.comment, ContainerHistory._containerHistory_key, ContainerHistory._room_key, " +
                    "ContainerHistory.actionDate, ContainerHistory._containerStatus_key, ContainerHistory.x, " +
                    "ContainerHistory.y, ContainerHistory.z, ContainerHistory._level_key, " +
                    "cv_ContainerStatus.containerStatus, Room.roomName " +
                    "FROM Container " +
                    "JOIN ContainerHistory " +
                    "ON ContainerHistory._containerHistory_key = Container._containerHistory_key " +
                    "JOIN cv_ContainerStatus " +
                    "ON cv_ContainerStatus._containerStatus_key = ContainerHistory._containerStatus_key " +
                    "JOIN Room " +
                    "ON Room._room_key = ContainerHistory._room_key " +
                    "WHERE containerStatus = 'active' AND _level_key = " + levelKey;
        
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : results){
            ContainerDTO dto = new ContainerDTO();
            ContainerHistoryDTO chDTO = new ContainerHistoryDTO();
            dto.setComment(myGet("comment", row));
            dto.setContainerHistory_key(myGet("_containerHistory_key", row));
            dto.setContainerID(myGet("containerID", row));
            dto.setContainerName(myGet("containerName", row));
            dto.setContainer_key(myGet("_container_key", row));
            
            chDTO.setContainerHistorykey(new Integer(myGet("_containerHistory_key", row)));
            chDTO.setContainerID(myGet("containerID", row));
            chDTO.setContainerKey(new Integer(myGet("_container_key", row)));
            chDTO.setContainerStatus(myGet("containerStatus", row));
            chDTO.setContainerStatuskey(new Integer(myGet("_containerStatus_key", row)));        
            chDTO.setLevelKey(myGet("_level_key", row));
            chDTO.setRoomKey(new Integer(myGet("_room_key", row)));
            chDTO.setRoomName(myGet("roomName", row));
            try{
                chDTO.setActionDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("actionDate", row)));
            }
            catch(Exception e){
                chDTO.setActionDate(null);
            }
            chDTO.setX(myGet("x", row));
            chDTO.setY(myGet("y", row));
            chDTO.setZ(myGet("z", row));
            
            dto.setCageHistoryDTO(chDTO);
            
            containers.add(dto);
        }
        return containers;        
    }
    
    public String getContainerStrain(String containerKey){
        String strainName = "";
        String query = "SELECT strainName "
                + "FROM Container "
                + "JOIN Mouse "
                + "ON Mouse._pen_key = Container._container_key "
                + "JOIN Strain "
                + "ON Mouse._strain_key = Strain._strain_key "
                + "WHERE Mouse.lifeStatus = 'A' AND "
                + "_container_key = " + containerKey;
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        if(results.length > 0){
            strainName = myGet("strainName", results[0]);
        }
        for(SortedMap result : results){
            String thisStrain = myGet("strainName", result);
            if(!strainName.equals(thisStrain)){
                return "mixed";
            }
        }
        return strainName;
    }
    
    public String getContainerSex(String containerKey){
        String sex = "";
        String query = "SELECT sex, containerID "
                + "FROM Container "
                + "JOIN Mouse "
                + "ON Mouse._pen_key = Container._container_key "
                + "WHERE Mouse.lifeStatus = 'A' AND "
                + "_container_key = " + containerKey;
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        if(results.length > 0){
            sex = myGet("sex", results[0]);
        }
        for(SortedMap result : results){
            if(isMatingCage(myGet("containerID", result))){
                return "breeding";
            }
            String thisSex = myGet("sex", result);
            if(!sex.equals(thisSex)){
                return "F/M";
            }
        }
        return sex;
    }
    
    public ArrayList<String> getMatingCages(String levelKey){
        ArrayList<String> matingCages = new ArrayList<String>();
        String query = "SELECT DISTINCT containerID " +
                "FROM Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam1_key " +
                "JOIN Container " +
                "ON Mouse._pen_key = Container._container_key " +
                "JOIN ContainerHistory " +
                "ON  Container._containerHistory_key = ContainerHistory._containerHistory_key " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND ContainerHistory._level_key = " + levelKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            matingCages.add(myGet("containerID", row));
        }
        return matingCages;
    }
    
    public boolean isMatingCage(String cageID){
        String query = "SELECT DISTINCT containerID " +
                "FROM Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam1_key " +
                "JOIN Container " +
                "ON Mouse._pen_key = Container._container_key " +
                "JOIN ContainerHistory " +
                "ON  Container._containerHistory_key = ContainerHistory._containerHistory_key " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND Container.containerID = " + cageID;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public String getMatingCageLitterStrain(String cageID){
        String query = "SELECT DISTINCT strainName " +
                "FROM Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam1_key " +
                "JOIN Container " +
                "ON Mouse._pen_key = Container._container_key " +
                "JOIN ContainerHistory " +
                "ON  Container._containerHistory_key = ContainerHistory._containerHistory_key " +
                "JOIN Strain " +
                "ON Mating._strain_key = Strain._strain_key " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND Container.containerID = " + cageID;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            return this.myGet("strainName", rows[0]);
        }
        else{
            return "";
        }
    }
    
    public boolean mouseInActiveMating(String mouseKey){
        String query = "SELECT DISTINCT ID " +
                "FROM Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam1_key " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND Mouse._mouse_key = " + mouseKey;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        query = "SELECT DISTINCT ID " +
                "FROM Mating " +
                "JOIN Mouse " +
                "ON Mouse._mouse_key = Mating._dam2_key " +
                "WHERE Mating.retiredDate IS NULL " +
                "AND Mouse._mouse_key = " + mouseKey;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        return false;
    }
    
    public boolean levelHasContainers(String levelKey){
        String query = "SELECT * FROM ContainerHistory WHERE _level_key = " + levelKey;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void updateLevelStatus(String status, String levelKey) throws Exception{
        String query = "UPDATE Level SET isActive = " + this.numberParser(status) 
                + " WHERE _level_key = " + numberParser(levelKey);
        this.executeJCMSUpdate(query);
    }
    
    public void updateContainerHistoryRecord(ContainerHistoryDTO dto) throws Exception{
        String query = "UPDATE ContainerHistory SET x = " + dto.getX() + ", "
                + "y = " + dto.getY() + ", "
                + "z = " + dto.getZ()
                + " WHERE _containerHistory_key = " + dto.getContainerHistorykey();
        this.executeJCMSUpdate(query);
    }
    
    public void changePenLifeStatus(LifeStatusEntity lifeStatus, String containerKey) throws Exception{
        String updateMouse = "";
        if(lifeStatus.getExitStatus()){
            updateMouse = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET Mouse.lifeStatus = '" + lifeStatus.getLifeStatus() + "',"
                    + " exitDate = now() "
                    + "WHERE exitStatus = 0 AND _pen_key = " + containerKey;
        }
        else{
            updateMouse = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET Mouse.lifeStatus = '" + lifeStatus.getLifeStatus() + "' "
                    + "WHERE exitStatus = 0 AND _pen_key = " + containerKey;
        }
        System.out.println(updateMouse);
        this.executeJCMSUpdate(updateMouse);
    }
    
    public void changeContainerStatus(String containerStatusKey, String containerHistoryKey) throws Exception{
        String updateContainer = "UPDATE ContainerHistory SET _containerStatus_key = " + containerStatusKey
                + " WHERE _containerHistory_key = " + containerHistoryKey;
        this.executeJCMSUpdate(updateContainer);
    }
    
    public int getXMax(String levelKey){
        String query = "SELECT MAX(x) AS xMax " + 
                    "FROM ContainerHistory " +
                    "JOIN cv_ContainerStatus " +
                    "ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key " +
                    "WHERE containerStatus = 'active' AND _level_key = " + levelKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            try{
                return Integer.parseInt(myGet("xMax", rows[0]));
            }
            catch(Exception e){
                System.out.println("Error: " + e);
                return 0;
            }
        }
        else{
            return 0;
        }
    }
    
    public int getYMax(String levelKey){
        String query = "SELECT MAX(y) AS yMax " + 
                    "FROM ContainerHistory " +
                    "JOIN cv_ContainerStatus " +
                    "ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key " +
                    "WHERE containerStatus = 'active' AND _level_key = " + levelKey;
        
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            try{
                return Integer.parseInt(myGet("yMax", rows[0]));
            }
            catch(Exception e){
                System.out.println("Error: " + e);
                return 0;
            }
        }
        else{
            return 0;
        }
    }
    
    public void updateContainerHistoryRoomKeyByLevel(String levelKey, String roomKey) throws Exception{
        String query = "UPDATE ContainerHistory "
                + "JOIN Container "
                + "ON Container._containerHistory_key = ContainerHistory._containerHistory_key "
                + "JOIN Level "
                + "ON ContainerHistory._level_key = Level._level_key "
                + "JOIN cv_ContainerStatus "
                + "ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key "
                + "SET ContainerHistory._room_key = " + roomKey
                + " WHERE containerStatus = 'active' AND ContainerHistory._level_key = " + levelKey;
        this.executeJCMSUpdate(query);
    }
    
    public void moveMouse(String mouseKey, String containerKey) throws Exception{
        String query = "UPDATE Mouse SET _pen_key = " + containerKey + " WHERE _mouse_key = " + mouseKey;
        this.executeJCMSUpdate(query);
    }
    
    public ArrayList<Integer> getApprovedMatingMatingStrains(String damStrainKey, String sireStrainKey){
        ArrayList<Integer> approvedStrainKeys = new ArrayList<Integer>();
        String query = "SELECT _litterStrain_key FROM ApprovedStrainRegistry WHERE _sireStrain_key = " + sireStrainKey + " AND _damStrain_key = " + damStrainKey; 
        SortedMap[] strainKeys = this.executeJCMSQuery(query).getRows();
        for(SortedMap strainKey : strainKeys){
            approvedStrainKeys.add(new Integer(myGet("_litterStrain_key", strainKey)));
        }
        return approvedStrainKeys;
    }
}
