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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.colonyManagement.MatingEntity;

import jcms.integrationtier.dto.MatingDTO;
import jcms.integrationtier.dto.MouseDTO;

/**
 *
 * @author mkamato
 */
public class EditMatingDAO extends MySQLDAO {

    private String getMatingIDsQuery = "SELECT matingID FROM Mating;";
    private String getMatingKeyFromIDQuery = "SELECT _mating_key FROM Mating WHERE matingID = ";
    
    private Connection con = null;
        
    public Integer updateMating(MatingDTO dto) throws SQLException {
        String updateStatement = "UPDATE Mating SET "
                + "matingID = " + dto.getMatingID()
                + ", _dam1_key = " + dto.getDam1_key()
                + ", _dam2_key = " + intParser(dto.getDam2_key())
                + ", _sire_key = " + dto.getSire_key()
                + ", _strain_key = " + dto.getStrain_key()
                + ", suggestedPenID = " + intParser(dto.getSuggestedPenID())
                + ", weanTime = " + dto.getWeanTime()
                + ", matingDate = DATE_FORMAT('" + dto.getMatingDate() + "', '" + super.MYSQL_DATE_FORMAT
                + "'), generation = '" + dto.getGeneration()
                + "', owner = '" + dto.getOwner()
                + "', weanNote = " + varcharParser(dto.getWeanNote())
                + ", needsTyping = " + dto.getNeedsTyping()
                + ", suggestedPenID = " + intParser(dto.getSuggestedPenID())
                + ", comment = " + varcharParser(dto.getComment());
        if(dto.getRetiredDate().equals("")){
            updateStatement = updateStatement + ", retiredDate = null"
                    + ", _crossStatus_key = (SELECT _crossStatus_key FROM cv_CrossStatus WHERE crossStatus = 'active')";
        }
        else{
            updateStatement = updateStatement + ", retiredDate = DATE_FORMAT('" + dto.getRetiredDate() + "', '" + super.MYSQL_DATE_FORMAT + "')"
                    + ", _crossStatus_key = (SELECT _crossStatus_key FROM cv_CrossStatus WHERE crossStatus = 'retired')";
        }
        updateStatement = updateStatement + " WHERE _mating_key = " + dto.getMating_key() + ";";
        System.out.println(updateStatement);
        executeUpdate(updateStatement);
        return new Integer(Integer.parseInt(dto.getMating_key()));
    }
    
    public Integer updateMatingWeanTimeNeedsTyping(MatingEntity dto) throws SQLException {
        String updateStatement = "UPDATE Mating SET "
                + " weanTime = " + (dto.getWeanTime() == true ? -1 : 0)
                + ", needsTyping = " + (dto.getNeedsTyping() == true ? -1 : 0);
        updateStatement = updateStatement + " WHERE _mating_key = " + dto.getMatingKey() + ";";
        System.out.println("updateMatingWeanTimeNeedsTyping:  "+ updateStatement);
        int rows = executeUpdate(updateStatement);
        return rows;
    }
    
    public String getMatingOwnerByKey(String key) throws Exception{
        String query = "SELECT owner FROM Mating WHERE _mating_key = " + key;
        SortedMap[] owners = executeQuery(query).getRows();
        if(owners.length > 0){
            return myGet("owner", owners[0]);
        }
        else{
            return "";
        }
    }
    
    public Integer updateMouse(MouseDTO dto) throws SQLException{
        String updateStatement = "UPDATE Mouse SET"
                + " _pen_key = " + dto.getPen_key()
                + " diet = " + dto.getDiet()
                + " breedingStatus = " + dto.getBreedingStatus()
                + " WHERE _mouse_key = " + dto.getMouse_key() + ";";
        
        System.out.println(updateStatement);
        return executeUpdate(updateStatement);
    }
    
    public ArrayList<String> getMatings(){
        ArrayList<String> Matings = new ArrayList<String>();
        Result theMatings = executeQuery(getMatingIDsQuery);
        int count = 0 ;
        for(SortedMap mating: theMatings.getRows()){
            String matingID = myGet("matingID", mating);
            Matings.add(matingID);
            count++;
        }
        System.out.println("Number of active pens: " + count);
        return Matings;
    }
    
    public String getMatingKeyByMatingID(String matingID){
        String key = "";
        SortedMap[] matingKey = executeQuery(getMatingKeyFromIDQuery + matingID).getRows();
        if(matingKey.length > 0){
            return myGet("_mating_key", matingKey[0]);
        }
        return key;
    }
    
    public String setMouseDiet(String mouseKey, String diet) throws Exception{
        String query = "UPDATE Mouse SET diet=" + diet + " WHERE _mouse_key = " + mouseKey;
        return executeUpdate(query).toString();
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

    @Override
    protected String myGet(String field, SortedMap result) {
        if (result.get(field) == null) {
            return "";
        } else {
            return result.get(field).toString();
        }
    }
    
    private String intParser(String field){
        if(field.equals("")){
            return "null";
        }
        else{
            return field;
        }
    }
    
    private String dateParser(Date theDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(theDate);
    }
    
    private String getLatestInsertedValue(String fieldName, String tableName){
        String query = "SELECT MAX(" + fieldName + ") AS max FROM " + tableName;
        SortedMap max = executeQuery(query).getRows()[0];
        return myGet("max", max);
    }
    
    public void updateMatingUnitLinkMouse(String matingKey, String oldMouseKey, String newMouseKey) throws Exception{
        String query = "UPDATE MatingUnitLink SET"
                + " _mouse_key = " + newMouseKey
                + " WHERE _mating_key = " + matingKey
                + " AND _mouse_key = " + oldMouseKey;
        this.executeUpdate(query);
    }
    
    public void insertMatingUnitLinkMouse(String matingKey, String newMouseKey) throws Exception{
        String query = "INSERT INTO MatingUnitLink  (_mating_key, _mouse_key, _sample_key, _matingUnitType_key) "
                    + "VALUES("
                    + matingKey + ", "
                    + newMouseKey + ", "
                    + "NULL, "
                    + "1);";
        this.executeUpdate(query);
    }
    
    public void deleteMatingUnitLinkMouse(String matingKey, String mouseKey) throws Exception{
        String getKeyQuery = "SELECT _matingUnitLink_key FROM MatingUnitLink WHERE _mating_key = " + matingKey
                + " AND _mouse_key = " + mouseKey;
        SortedMap[] keys = this.executeJCMSQuery(getKeyQuery).getRows();
        if(keys.length < 1){            
            throw new Exception("Error: MatingUnitLink table entry could not be found for mating with key " + matingKey + " and mouse with key " + mouseKey);
        }
        if(keys.length > 1){
            throw new Exception("Error: Found multiple MatingUnitLink table entrys for mating with key " + matingKey + " and mouse with key " + mouseKey);
        }
        if(keys.length == 1){
            String key = myGet("_matingUnitLink_key",keys[0]);
            String query = "DELETE FROM MatingUnitLink WHERE _matingUnitLink_key = " + key;
            this.executeJCMSUpdate(query);
        }
    }
}
