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
import java.sql.PreparedStatement;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * MobileColonySummaryReportDAO is the DAO that handles all data access for the 
 * JCMS Web Mobile application's Colony Summary report. It extends 
 * MySQLMobileDAO which contains many recycled methods such as methods to 
 * execute prepared statements and methods to get the requesting user's 
 * permissions.
 * 
 * @author mkamato
 */
public class MobileColonySummaryReportDAO extends MySQLMobileDAO{
   
    
    List<String> owners;
    
    public MobileColonySummaryReportDAO(String userName){
        owners = this.getGuestWorkgroups(userName);
    }
    
    /**
     * Method to find the summary statistics for the JCMS Mobile colony summary
     * report
     * 
     * @param userName - the user who made the requests username
     * @return ResultSet containing the summary stats
     * @throws Exception 
     */
    public ResultSet getSummaryStatistics(Connection con) throws Exception{
        ResultSet rs;
        String mouseOwnerWhere = "";
        String matingOwnerWhere = "";
        if(owners.size() > 0){
            mouseOwnerWhere = "(";
            matingOwnerWhere = "(";
        }
        for(String owner : owners){
            //last element in list
            if(owner.equals(owners.get(owners.size() - 1))){
                mouseOwnerWhere += "Mouse.owner = ? )";
                matingOwnerWhere += "Mating.owner = ? )";
            }
            //not last element
            else{
                mouseOwnerWhere += "Mouse.owner = ? OR ";
                matingOwnerWhere += "Mating.owner = ? OR ";
            }
        }
        
        /* 
         * this query is probably pretty dirty bird, but I figured instead of 
         * executing a bunch of querys one at a time I would just write one 
         * query to return all the desired results in a single row.
         * 
         * I apologize for any offense,
         * MKA
         */        
        String query = "SELECT m.mouseCount, s.strainCount, c.cageCount, mat.matingCount "
                    + "FROM "
                    //first get the total mice that the user owns...
                    + "(SELECT COUNT(*) AS mouseCount "
                    + "FROM Mouse  "
                    + "JOIN LifeStatus "
                    + "ON Mouse.lifeStatus = LifeStatus.lifeStatus "
                    + "WHERE " + mouseOwnerWhere + " AND exitStatus = 0) AS m "
                    + "LEFT JOIN "
                    //then get the strains on shelf...
                    + "(SELECT COUNT(DISTINCT strainName) AS strainCount "
                    + "FROM Mouse "
                    + "JOIN Strain "
                    + "ON Mouse._strain_key = Strain._strain_key  "
                    + "WHERE " + mouseOwnerWhere + " AND Mouse.lifeStatus = 'A') AS s "
                    + "ON true "
                    + "LEFT JOIN "
                    //next get the active cages...
                    + "(SELECT COUNT(DISTINCT Container._container_key) AS cageCount "
                    + "FROM Mouse "
                    + "JOIN Container "
                    + "ON Mouse._pen_key = Container._container_key "
                    + "JOIN ContainerHistory "
                    + "ON Container._containerHistory_key = ContainerHistory._containerHistory_key "
                    + "JOIN cv_ContainerStatus "
                    + "ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key "
                    + "WHERE " + mouseOwnerWhere 
                    + "  AND cv_ContainerStatus.containerStatus != 'retired' "
                    + ") AS c "
                    + "ON true "
                    + "LEFT JOIN  "
                    //active matings...
                    + "(SELECT COUNT(*) matingCount "
                    + "FROM Mating "
                    + "JOIN cv_CrossStatus "
                    + "ON Mating._crossStatus_key = cv_CrossStatus._crossStatus_key "
                    + "WHERE " + matingOwnerWhere + " AND crossStatus = 'active') AS mat "
                    + "ON true;";       
        
        PreparedStatement summaryStatisticsStatement = con.prepareStatement(query);   
        /*figure out how many times the owner filter was used (number of 
        * ownereWheres in the above query * the number of owners in the owners 
        * list) 
        */
        // four where clauses to go through in the above query...
        int jdx = 1;
        for(int idx = 0; idx < 4; idx++){
            for(String owner : owners){
                summaryStatisticsStatement.setString(jdx, owner);
                jdx++;
            }            
        }
        rs = this.executePreparedStatementQuery(summaryStatisticsStatement);
        return rs;
    }
    
    /**
     * Method to get a result set with the number of live mice grouped by owner
     * and strain for all the workgroups to which the user has at least guest
     * access.
     * 
     * @return ResultSet for above criteria
     * @throws Exception 
     */
    public ResultSet getNumberLiveMiceByOwnerStrain(Connection con) throws Exception {
        ResultSet rs = null;
        String ownerWhere = "";
        if(owners.size() > 0){
            ownerWhere = "(";
        }
        for(String owner : owners){
            //last element in list
            if(owner.equals(owners.get(owners.size() - 1))){
                ownerWhere += "Mouse.owner = ? )";
            }
            //not last element
            else{
                ownerWhere += "Mouse.owner = ? OR ";
            }
        }
        String query = "SELECT COUNT(_mouse_key) AS totalMice, Mouse.owner, "
                + "Strain.strainName, Mouse.lifeStatus "
                + "FROM Mouse "
                + "JOIN Strain "
                + "ON Mouse._strain_key = Strain._strain_key "
                + "WHERE " + ownerWhere + " AND Mouse.lifeStatus='A' "
                + "GROUP BY owner, Strain._strain_key "
                + "ORDER BY Mouse.owner, strainName;";
        
        PreparedStatement liveMiceByOwnerStatement = con.prepareStatement(query);
        //fill in variables...
        int idx = 1;
        for(String owner : owners){
            liveMiceByOwnerStatement.setString(idx, owner);
            idx++;
        }
        rs = this.executePreparedStatementQuery(liveMiceByOwnerStatement);
        return rs;
    }
    
    /**
     * Method to get a result set with the number of active cages grouped by 
     * owner and room for all the workgroups to which the user has at least 
     * guest access.
     * 
     * @return ResultSet for above criteria
     * @throws Exception 
     */
    public ResultSet getNumberCagesByOwnerRoom(Connection con) throws Exception {
        ResultSet rs = null;
        String ownerWhere = "";
        if(owners.size() > 0){
            ownerWhere = "(";
        }
        for(String owner : owners){
            //last element in list
            if(owner.equals(owners.get(owners.size() - 1))){
                ownerWhere += "Mouse.owner = ? )";
            }
            //not last element
            else{
                ownerWhere += "Mouse.owner = ? OR ";
            }
        }
        String query = "SELECT COUNT(DISTINCT Container._container_key) AS totalCages, "
                + "Mouse.owner, Room.roomName "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "JOIN ContainerHistory "
                + "ON Container._containerHistory_key = ContainerHistory._containerHistory_key "
                + "JOIN cv_ContainerStatus ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key "
                + "JOIN Room "
                + "ON ContainerHistory._room_key = Room._room_key "
                + "WHERE " + ownerWhere 
                + " AND cv_ContainerStatus.containerStatus != 'retired'"
//                + " AND Mouse.lifeStatus='A' "
                + "GROUP BY Mouse.owner, Room.roomName "
                + "ORDER BY Mouse.owner, Room.roomName;";
        
        PreparedStatement numberCagesByOwnerRoom = con.prepareStatement(query);
        //fill in variables...
        int idx = 1;
        for(String owner : owners){
            numberCagesByOwnerRoom.setString(idx, owner);
            idx++;
        }
        rs = this.executePreparedStatementQuery(numberCagesByOwnerRoom);
        return rs;
    }
    
    /**
     * Method to get a result set with the number of active matings grouped by 
     * owner and litter strain for all the workgroups to which the user has at 
     * least guest access.
     * 
     * @return ResultSet for above criteria
     * @throws Exception 
     */
    public ResultSet getNumberMatingsByOwnerLitterStrain(Connection con) throws Exception {
        ResultSet rs = null;
        String ownerWhere = "";
        if(owners.size() > 0){
            ownerWhere = "(";
        }
        for(String owner : owners){
            //last element in list
            if(owner.equals(owners.get(owners.size() - 1))){
                ownerWhere += "Mating.owner = ? )";
            }
            //not last element
            else{
                ownerWhere += "Mating.owner = ? OR ";
            }
        }
        String query = "SELECT COUNT(*) AS totalMatings, "
                + "Mating.owner, Strain.strainName "
                + "FROM Mating "
                + "JOIN Strain "
                + "ON Mating._strain_key = Strain._strain_key "
                + "JOIN cv_CrossStatus "
                + "ON Mating._crossStatus_key = cv_CrossStatus._crossStatus_key "
                + "WHERE " + ownerWhere + " AND cv_CrossStatus.crossStatus = 'Active' "
                + "GROUP BY Mating.owner, Strain._strain_key "
                + "ORDER BY Mating.owner, Strain.strainName;";
        
        PreparedStatement numberMatingsByOwnerLitterStrain = con.prepareStatement(query);
        //fill in variables...
        int idx = 1;
        for(String owner : owners){
            numberMatingsByOwnerLitterStrain.setString(idx, owner);
            idx++;
        }
        rs = this.executePreparedStatementQuery(numberMatingsByOwnerLitterStrain);
        return rs;
    }
    
    /**
     * Method to gather data and build JSON to be returned to client describing 
     * a JCMS Mobile colony summary report.
     * 
     * @return String of JSON to be returned to client.
     */
    public String getColonySummaryJSON() {
        Connection con = null;
            ResultSet ssRS = null;
            ResultSet miceRS = null;
            ResultSet cagesRS = null;
            ResultSet matingsRS = null;
        try{
            con = this.getConnection();
            //get data...
            ssRS = this.getSummaryStatistics(con);
            miceRS = this.getNumberLiveMiceByOwnerStrain(con);
            cagesRS = this.getNumberCagesByOwnerRoom(con);
            matingsRS = this.getNumberMatingsByOwnerLitterStrain(con);

            //root will be the root json object
            JSONObject root = new JSONObject();

            //first add summary stats...
            JSONObject summaryStats = new JSONObject();

            ssRS.first();
            summaryStats.put("mouseCount", ssRS.getInt("mouseCount"));
            summaryStats.put("cageCount", ssRS.getInt("cageCount"));
            summaryStats.put("strainCount", ssRS.getInt("strainCount"));
            summaryStats.put("matingCount", ssRS.getInt("matingCount"));

            root.put("summaryStats", summaryStats);

            /*
            * Hierarchically, the data is organized as follows:
            * -root -> Simple JSON Object
            *      - summary statistics -> JSON Array
            *          ...
            *      - live mice data -> JSON Array
            *          -owner 1 ->Simple JSON Object
            *              -total strains -> Simple JSON Object
            *              -total mice -> Simple JSON Object
            *              -mice by strain for owner -> JSON Array
            *                  -strain 1 : 15 -> Simple JSON Object
            *                  -strain 2 : 21 -> Simple JSON Object
            *                  ...
            */
            JSONArray mouseDetails = new JSONArray();
            miceRS.beforeFirst();

            JSONObject mouseOwner = new JSONObject();
            String currentMouseOwner = "";
            JSONArray strains = new JSONArray();;
            int totalStrains = 0;
            int totalMice = 0;
            while(miceRS.next()){
                //first...
                if(miceRS.isFirst()){
                    currentMouseOwner = miceRS.getString("owner");
                }
                /*
                * end of current owner, add the current owner to the mouse 
                * details array and reset the json owner info to reflect a 
                * 'start' state
                */
                else if(!currentMouseOwner.equalsIgnoreCase(miceRS.getString("owner"))){
                    /*
                    * set the owner object up with the strain values and the 
                    * total mice/strains that owner has on shelf
                    */
                    mouseOwner.put("strains", strains);
                    mouseOwner.put("totalMice", totalMice);
                    mouseOwner.put("totalStrains", totalStrains);
                    mouseOwner.put("owner", currentMouseOwner);
                    mouseDetails.add(mouseOwner);

                    //reset values for new owner...
                    mouseOwner = new JSONObject();
                    strains = new JSONArray();
                    totalStrains = 0;
                    totalMice = 0;
                    currentMouseOwner = miceRS.getString("owner");
                }
                /*
                * Each row of the result set will correspond to an owner and 
                * a strain. The result set should be ordered by owner so when 
                * you reach the end of one owner the owner should be done and 
                * you can add the information as is and start creating the next
                * owner JSON object.
                * 
                * | owner | strain | total mice |
                * | mike  | asdf   |    15      |
                * | mike  | bla    |    9       |
                * | cjd   | asdf   |    11      |
                * | cjd   | qwre   |    25      |
                *             ...
                */
                JSONObject strain = new JSONObject();
                strain.put("strainName", miceRS.getString("strainName"));
                strain.put("totalMice", miceRS.getInt("totalMice"));
                //calculate new values for total mice/strains
                totalMice += miceRS.getInt("totalMice");
                totalStrains++;
                //add new strain to strain list...
                strains.add(strain);

                //if it's the last row you have to put the owner together
                if(miceRS.isLast()){                    
                    mouseOwner.put("strains", strains);
                    mouseOwner.put("totalMice", totalMice);
                    mouseOwner.put("totalStrains", totalStrains);
                    mouseOwner.put("owner", currentMouseOwner);
                    mouseDetails.add(mouseOwner);
                }
            }


            /*
            * Ok, now moving on to the cages by room and owner...
            * 
            * We'll use same data organizing idea as above...
            */
            JSONArray cageDetails = new JSONArray();
            cagesRS.beforeFirst();

            JSONObject cageOwner = new JSONObject();
            String currentCageOwner = "";
            JSONArray rooms = new JSONArray();
            int totalCages = 0;
            int totalRooms = 0;
            while(cagesRS.next()){
                //first...
                if(cagesRS.isFirst()){
                    currentCageOwner = cagesRS.getString("owner");
                }
                /*
                * if the current row's owner value doesn't match the previous 
                * row's owner that data is over and you can add and reset the 
                * json objects
                */
                else if(!currentCageOwner.equalsIgnoreCase(cagesRS.getString("owner"))){
                    cageOwner.put("rooms", rooms);
                    cageOwner.put("totalCages", totalCages);
                    cageOwner.put("totalRooms", totalRooms);
                    cageOwner.put("owner", currentCageOwner);                    
                    cageDetails.add(cageOwner);

                    cageOwner = new JSONObject();
                    rooms = new JSONArray();
                    totalCages = 0;
                    totalRooms = 0;
                    currentCageOwner = cagesRS.getString("owner");
                }
                JSONObject room = new JSONObject();
                room.put("roomName", cagesRS.getString("roomName"));
                room.put("totalCages", cagesRS.getInt("totalCages"));
                totalRooms++;
                totalCages += cagesRS.getInt("totalCages");
                rooms.add(room);

                //check to make sure it isn't last row, if it is add the room
                if(cagesRS.isLast()){
                    cageOwner.put("rooms", rooms);
                    cageOwner.put("totalCages", totalCages);
                    cageOwner.put("totalRooms", totalRooms);
                    cageOwner.put("owner", currentCageOwner);                    
                    cageDetails.add(cageOwner);
                }
            }

            //finally the matings...
            JSONArray matingDetails = new JSONArray();
            matingsRS.beforeFirst();

            JSONObject matingsOwner = new JSONObject();
            String currentMatingsOwner = "";
            JSONArray matingStrains = new JSONArray();
            int totalMatings = 0;
            int totalMatingStrains = 0;
            while(matingsRS.next()){
                //first...
                if(matingsRS.isFirst()){
                    currentMatingsOwner = matingsRS.getString("owner");
                }
                else if(!currentMatingsOwner.equalsIgnoreCase(matingsRS.getString("owner"))){
                    matingsOwner.put("strains", matingStrains);
                    matingsOwner.put("totalMatings", totalMatings);
                    matingsOwner.put("totalStrains", totalMatingStrains);
                    matingsOwner.put("owner", currentMatingsOwner);
                    matingDetails.add(matingsOwner);

                    matingsOwner = new JSONObject();
                    matingStrains = new JSONArray();
                    totalMatings = 0;
                    totalMatingStrains = 0;
                    currentMatingsOwner = matingsRS.getString("owner");
                }
                JSONObject strain = new JSONObject();
                strain.put("strainName", matingsRS.getString("strainName"));
                strain.put("totalMatings", matingsRS.getInt("totalMatings"));
                matingStrains.add(strain);
                totalMatingStrains++;
                totalMatings += matingsRS.getInt("totalMatings");

                //check for last...
                if(matingsRS.isLast()){                    
                    matingsOwner.put("strains", matingStrains);
                    matingsOwner.put("totalMatings", totalMatings);
                    matingsOwner.put("totalStrains", totalMatingStrains);
                    matingsOwner.put("owner", currentMatingsOwner);
                    matingDetails.add(matingsOwner);
                }
            }
            root.put("matingStats", matingDetails);
            root.put("cageStats", cageDetails);
            root.put("mouseStats", mouseDetails);
            String json = root.toJSONString().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
            return json;
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return "";
        }
        finally{
            try{if(miceRS != null) {miceRS.close();}}catch(Exception e){};
            try{if(cagesRS != null) {cagesRS.close();}}catch(Exception e){};
            try{if(matingsRS != null) {matingsRS.close();}}catch(Exception e){};
            try{if(ssRS != null) {ssRS.close();}}catch(Exception e){};
            try{if(con != null) {con.close();}}catch(Exception e){};
        }
    }
}
