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

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.ExpDataDTO;
import jcms.integrationtier.dto.ExpDataResultsDTO;
import jcms.integrationtier.dto.ExpDataDescriptorDTO;
import jcms.integrationtier.dto.ExpDataDescriptorFieldDTO;
import jcms.integrationtier.dto.ExpDataTestTypeAndResultsDTO;
import jcms.integrationtier.dto.ExpDataTestTypeListDTO;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;

/**
 *
 * @author bas
 */
public class ExpDataDAO extends MySQLDAO {
    private Connection con = null;
    private String expData = "SELECT * FROM ExpData ORDER BY _expData_key";
    private ArrayList<ExpDataDescriptorDTO>     testTypes         = null;
    
    public ExpDataDAO() {
        super();
    }
    
    /**
     * JDBC auto appends .0 to all integer value since they are stored in the 
     * database as float. Use this method to revert all integer values.  Ignore
     * all other formats (dec, text and date).
     * @param value
     * @return 
     */
    private String checkFormat(String format, String value) {
        if (value != null && value.length() > 0 && 
            format.equalsIgnoreCase("int") && value.indexOf(".") > -1) {
            value = value.substring(0, value.indexOf("."));
        }
        return value;
    }

    public ArrayList<ExpDataDTO> getAllExpData() {
        ArrayList<ExpDataDTO> dtoList = new ArrayList<ExpDataDTO> ();
        ExpDataDTO dto = null;
        Result result = executeJCMSQuery(expData);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createExpDataDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    private ExpDataDTO createExpDataDTO(SortedMap row) {
        ExpDataDTO dto = new ExpDataDTO();
        dto.setExpDataKey(myGet("_expData_key",row));
        dto.setTestTypeKey(myGet("_expDataDesc_key",row));
        dto.setExpTestKey(myGet("_expTest_key",row));
        dto.setSpecimenKey(myGet("_specimen_key",row));
        dto.setDataID(myGet("dataID",row));
        dto.setSpecimen_type(myGet("specimen_type",row));
        dto.setOwner(myGet("owner",row));
        dto.setExpDate(myGet("expDate",row));
        dto.setAge(myGet("age",row));
        dto.setAbnormalData(Boolean.parseBoolean(myGet("abnormalData",row)));
        dto.setVersion(myGet("version",row));
        dto.setD_Results(this.getD_Results(row));
        
        return dto;
    }
    
    private ArrayList<ExpDataResultsDTO> getD_Results(SortedMap row) {
        ArrayList<ExpDataResultsDTO> d_Results = new ArrayList<ExpDataResultsDTO>() ;
        String d = "";
        for (int i=1; i <= 30 ;i++ ) {
            ExpDataResultsDTO dataResult = new ExpDataResultsDTO();
            d = this.myGet("d"+i,row);
           
            if (d == null || d.isEmpty()) {
                dataResult.setD_Result(""); // I think I need them even if they are null 
            } else {
                dataResult.setD_Result(d);
            }
            d_Results.add(dataResult);
        }
        
        return d_Results;
    }
    
        //Used to return a specific Test Type record associated with specific experimental data results
    public ExpDataTestTypeListDTO getTestTypeAndExpDataResults (Integer expDataDescriptorKey, String expDataKey) {
        ExpDataTestTypeListDTO dto = new ExpDataTestTypeListDTO();
        if (expDataKey == null || expDataKey.isEmpty()) {
            //We don't have an experimental data record yet, need to set up data results as blank for data entry
            String query2 = "SELECT * From ExpDataDescriptor WHERE "
                + " _expDataDescriptor_key =" + expDataDescriptorKey;
            Result result2 =  executeJCMSQuery(query2);
            if (result2 != null) {
                for (SortedMap row : result2.getRows()) {
                    dto.setTtWithDataResult(this.getTTandResultsList(row));
                    dto.setTestTypeKey(expDataDescriptorKey.toString());
                    dto.setExpDataKey("0");
                    break; //we should get only one row returned, but just in case, use first row
                }
            } 
        } else {
            //Look up existing ExpData record
            String query = "Select * From ExpData, ExpDataDescriptor Where "
                + " ExpDataDescriptor._expDataDescriptor_key =" + expDataDescriptorKey + " AND ExpData._expData_key =" + expDataKey;

            Result result =  executeJCMSQuery(query);
            if (result != null) {
                for (SortedMap row : result.getRows()) {
                    dto.setTtWithDataResult(this.getTTandResultsList(row));
                    dto.setTestTypeKey(expDataDescriptorKey.toString());
                    dto.setExpDataKey(expDataKey);
                    break; //we should get only one row returned, but just in case, use first row
                }
            }
        }
        return dto;
    }
    
    private ArrayList<ExpDataTestTypeAndResultsDTO> getTTandResultsList(SortedMap row) {
        ArrayList<ExpDataTestTypeAndResultsDTO> tT_ExpData = new ArrayList<ExpDataTestTypeAndResultsDTO>() ;
        String data = "";
        String caption = "";
        String iAsString = "";
        for (int i=1; i <= 30 ;i++ ) {
            ExpDataTestTypeAndResultsDTO dataResult = new ExpDataTestTypeAndResultsDTO();
            data = this.myGet("d"+i,row);
            caption = this.myGet("d"+i+"_caption", row);

            if (caption == null || caption.isEmpty()) {
                continue;  //skip any rows with no caption value
            } else {
                iAsString = Integer.toString(i);
                dataResult.setD_Number(iAsString); //This will save the Dx value, need to know it when saving data
                dataResult.setCaption(caption);
                dataResult.setDataFormat(this.myGet("d"+i+"_dataFormat",row));
                dataResult.setFieldDescription(this.myGet("d"+i+"_fieldDescription",row));
                dataResult.setRequired(Boolean.parseBoolean(this.myGet("d"+i+"_required",row)));
                dataResult.setMinValue(this.checkFormat(dataResult.getDataFormat(),this.myGet("d"+i+"_minValue",row)));
                dataResult.setMaxValue(this.checkFormat(dataResult.getDataFormat(),this.myGet("d"+i+"_maxValue",row)));
                if (data == null || data.isEmpty()) {
                    dataResult.setD_Value(""); //Initialize value to blank
                } else {
                    dataResult.setD_Value(data);   
                }
                tT_ExpData.add(dataResult);
            }
        }
        return tT_ExpData;
}
    
    public Integer insertExpData(ExpDataDTO dto, ExpDataTestTypeListDTO dto2) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer pk = 0;
        String pkString = "";
        String insertCmd = "INSERT INTO ExpData " +
            "    (_expData_key," + 
            "    _expDataDesc_key," +
            "    _expTest_key," +
            "    _specimen_key," +
            "    dataID," +
            "    specimen_type," +
            "    owner," +
            "    expDate," +
            "    age," +
            "    abnormalData," +
            " version ) "
            + " VALUES ("+ varcharParser(dto.getExpDataKey().trim())
            + ", " + varcharParser(dto.getTestTypeKey().trim())
            +  ", "+ varcharParser(dto.getExpTestKey().trim())
            +  ", "+ varcharParser(dto.getSpecimenKey().trim())
            +  ", "+ varcharParser(dto.getDataID().trim())
            +  ", "+ varcharParser(dto.getSpecimen_type().trim())
            +  ", "+ varcharParser(dto.getOwner().trim())
            +  ", "+ varcharParser(dto.getExpDate().trim())
            +  ", "+ varcharParser(dto.getAge().trim())
            +  ", "+ (dto.getAbnormalData()? -1 : 0)
            + ", "+ version.toString()
            + " )" ;
        
        try { 
            // Begin Transaction
            con = this.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstmt = con.prepareStatement(insertCmd, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.addBatch();
            pstmt.executeBatch();
            
            pk = Integer.parseInt(dto.getExpDataKey().trim());

            dto2.setExpDataKey(dto.getExpDataKey().trim());
            // Update ExpData results (dx 1 through 30)
            Integer fieldNumber = 1;
            // In the same transaction update all denormalized fields, 1 through 30

            PreparedStatement pstmt2 = null;
            
            if (dto2.getTtWithDataResult().size() > 0) {
                for (ExpDataTestTypeAndResultsDTO dDataField : dto2.getTtWithDataResult()) {
                    String updateCmd2 = "UPDATE ExpData SET "
                         + "d# = ? "
                         + " WHERE _expData_key = " + pk ;
                    pstmt2 = con.prepareStatement(updateCmd2.replace("#", dDataField.getD_Number())); //The actual # was saved in the DTO
                    pstmt2.setString(1, dDataField.getD_Value());
                    pstmt2.addBatch();
                    pstmt2.executeBatch();
                    fieldNumber++;
                }
                
            }

            // End Transaction
            con.commit();

            System.out.println("Inserted ExpData key: "+ pk);

        } catch (SQLFeatureNotSupportedException sfns) {
            con.rollback();
            pk=0;
            throw new SQLException(sfns);
        } catch (BatchUpdateException bue) {
            // Rollback transaction if there are any errors
            con.rollback();
            pk=0;
            throw new SQLException(bue);
        } catch (SQLException ex) {
            // Rollback transaction if there are any errors
            con.rollback();
            pk=0;
            throw new SQLException(ex);
        } finally {
            this.closeConnection(con);
        }
        //Return the dataID of the new record
        if (pk != 0) {
            pk = Integer.parseInt(dto.getDataID());
        }
        return pk;  //returns new DataID or 0 if there was an error
        
    }
    
    public Integer updateExpData(ExpDataDTO dto, ExpDataTestTypeListDTO dto2) throws SQLException {
        Integer success = 0;
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        
        String updatecmd1 = "UPDATE ExpData SET " 
            +  "owner="+ varcharParser(dto.getOwner().trim())
            +  ", expDate="+ varcharParser(dto.getExpDate().trim())
            +  ", age="+ varcharParser(dto.getAge().trim())
            +  ", abnormalData="+ (dto.getAbnormalData() ? -1 : 0)
            + ", version = " + version.toString()
            + " WHERE _expData_key = "+ dto.getExpDataKey() ;
        
        try { 
            // Begin Transaction
            con = this.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstmt = con.prepareStatement(updatecmd1);
            pstmt.addBatch();
            pstmt.executeBatch();

            // Update ExpData results (dx 1 through 30)
            Integer fieldNumber = 1;
            // In the same transaction update all denormalized fields, 1 through 30
           
            PreparedStatement pstmt2 = null;
            
            if (dto2.getTtWithDataResult().size() > 0) {
                for (ExpDataTestTypeAndResultsDTO dDataField : dto2.getTtWithDataResult()) {
                     String updateCmd2 = "UPDATE ExpData SET "
                        + "d# = ? "
                        + " WHERE _expData_key = " + dto.getExpDataKey() ;
                     pstmt2 = con.prepareStatement(updateCmd2.replace("#", dDataField.getD_Number()));  //The actual # was saved in the DTO
                     
                    pstmt2.setString(1, dDataField.getD_Value());
                    pstmt2.addBatch();
                    pstmt2.executeBatch();
                    fieldNumber++;
                }
                
            }

            // End Transaction
            con.commit();

            System.out.println("Updated ExpData key: "+ dto.getExpDataKey());
            success = 1;
        } catch (SQLFeatureNotSupportedException sfns) {
            con.rollback();
            throw new SQLException(sfns);
        } catch (BatchUpdateException bue) {
            // Rollback transaction if there are any errors
            con.rollback();
            throw new SQLException(bue);
        } catch (SQLException ex) {
            // Rollback transaction if there are any errors
            con.rollback();
            throw new SQLException(ex);
        } finally {
            this.closeConnection(con);
        }
        
        return success;
     }
     
    public ArrayList <ExpDataDTO> getDataIDs () {
        ArrayList<ExpDataDTO> dtoList = new ArrayList<ExpDataDTO> ();
        ExpDataDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM ExpData ORDER BY dataID");
        for (SortedMap row : result.getRows()) {
            dto = this.createExpDataDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    //Use this to get a dto list containing only records that may be edited by this user.
    //ownerLst must contain all valid owner's that can be edited.
    public ArrayList <ExpDataDTO> getDataIDsForOwner (String ownerLst) {
        ArrayList<ExpDataDTO> dtoList = new ArrayList<ExpDataDTO> ();
        ExpDataDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM ExpData WHERE owner IN (" + ownerLst + ") ORDER BY dataID DESC");
        for (SortedMap row : result.getRows()) {
            dto = this.createExpDataDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ExpDataDTO getExpData (Integer expDataKey) {
        ExpDataDTO dto = null;
        String query = "SELECT * FROM ExpData WHERE _expData_key =" + expDataKey;
        Result result =  executeJCMSQuery(query);
        for (SortedMap row : result.getRows()) {
            dto = this.createExpDataDTO(row);
        }
        return dto;
    }
    
    public String findMaxDataID() {
        String returnDataID = "0";
        Result result = this.executeJCMSQuery("SELECT MAX(dataID) as dataID FROM ExpData");
        for (SortedMap row : result.getRows()) {
            returnDataID = this.myGet("dataID", row);
        }   
        if (returnDataID == null || returnDataID.trim().equals("") ){
            returnDataID = "0";
        }
        
        return returnDataID;
    }
    
    public String findMaxExpDataKey() {
        String returnExpDataKey = "0";
        Result result = this.executeJCMSQuery("SELECT MAX(_expData_key) as expDataKey FROM ExpData");
        for (SortedMap row : result.getRows()) {
            returnExpDataKey = this.myGet("expDataKey", row);
        }   
        if (returnExpDataKey == null || returnExpDataKey.trim().equals("") ){
            returnExpDataKey = "0";
        }
        
        return returnExpDataKey;
    }

    public Integer changeMouseLifeStatus(String mouseID, LifeStatusEntity lifeStatus, String exitDate) throws Exception{
        String updateMouse = "";
        Integer returnValue = 0;
        if(lifeStatus.getExitStatus()){
            updateMouse = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET Mouse.lifeStatus = '" + lifeStatus.getLifeStatus() + "',"
                    + " exitDate = '" + exitDate
                    + "' WHERE Mouse.ID = '" + mouseID + "';";
        }
        else{
            updateMouse = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET Mouse.lifeStatus = '" + lifeStatus.getLifeStatus() + "',"
                    + " exitDate = null, cod = null "
                    + "WHERE Mouse.ID = '" + mouseID + "';";
        }
        this.executeJCMSUpdate(updateMouse);
        return returnValue;
    }
        
    // =================
    // TEST TYPE METHODS
    // =================
    
    /**
     * Get a complete list of experimental data test types.
     * Include ExpTest and ExpData test type counts.
     */
    public ArrayList<ExpDataDescriptorDTO> getAllTestTypes() {
        testTypes = new ArrayList<ExpDataDescriptorDTO>();
        ExpDataDescriptorDTO dto = null;
        String query = "SELECT *, (SELECT COUNT(et._expDataDescriptor_key) FROM ExpTest et WHERE et._expDataDescriptor_key = edd._expDataDescriptor_key) as expDataDescriptor_TestCount,"
            + "(SELECT COUNT(ed._expDataDesc_key) FROM ExpData ed WHERE ed._expDataDesc_key = edd._expDataDescriptor_key) as expDataDescriptor_DataCount "
            + " FROM ExpDataDescriptor edd "
            + " ORDER BY edd.testType";
        Result result = executeJCMSQuery(query);
        for (SortedMap row : result.getRows()) {
            dto = this.createExpDataDescriptorDTO(row);
            testTypes.add(dto);
        }
        return testTypes;
    }
    
    public ArrayList<ExpDataDescriptorDTO> getTestType(String testType) {
        ArrayList<ExpDataDescriptorDTO> dtoList = new ArrayList<ExpDataDescriptorDTO> ();
        ExpDataDescriptorDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM ExpDataDescriptor WHERE testType = '"+ testType +"'");
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createExpDataDescriptorDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    //Used by the TestTypeConverter to return a specific Test Type record 
    public ExpDataDescriptorDTO getTestType (Integer expDataDescriptorKey) {
        ExpDataDescriptorDTO dto = null;
        String query = "SELECT * FROM ExpDataDescriptor WHERE _expDataDescriptor_key =" + expDataDescriptorKey;
        Result result =  executeJCMSQuery(query);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createExpDataDescriptorDTO(row);
            } 
        }
        return dto;
    }
    
    private ExpDataDescriptorDTO createExpDataDescriptorDTO(SortedMap row) {
        ExpDataDescriptorDTO dto = new ExpDataDescriptorDTO();
        dto.setExpDataDescriptor_key(Integer.parseInt(myGet("_expDataDescriptor_key",row)));
        dto.setTestType(myGet("testType",row));
        dto.setTestTypeNotes(myGet("testTypeNotes",row));
        dto.setVersion(Integer.parseInt(myGet("version",row)));
        dto.setExpTestCount(myGetInt("expDataDescriptor_TestCount", row));
        dto.setExpDataCount(myGetInt("expDataDescriptor_DataCount", row));
        dto.setFields(this.getFields(row));
        return dto;
    }
     
    private ArrayList<ExpDataDescriptorFieldDTO> getFields(SortedMap row) {
        ArrayList<ExpDataDescriptorFieldDTO> fields = new ArrayList<ExpDataDescriptorFieldDTO>() ;
        String caption = "";
        for (int i=1; i <= 30 ;i++ ) {
            ExpDataDescriptorFieldDTO field = new ExpDataDescriptorFieldDTO();
            caption = this.myGet("d"+i+"_caption",row);
            if (caption == null || caption.isEmpty())
                break;
            field.setCaption(caption);
            field.setDataFormat(this.myGet("d"+i+"_dataFormat",row));
            field.setFieldDescription(this.myGet("d"+i+"_fieldDescription",row));
            field.setRequired(Boolean.parseBoolean(this.myGet("d"+i+"_required",row)));
            field.setMinValue(this.checkFormat(field.getDataFormat(), this.myGet("d"+i+"_minValue",row)));
            field.setMaxValue(this.checkFormat(field.getDataFormat(), this.myGet("d"+i+"_maxValue",row)));
            fields.add(field);
        }
        
        return fields;
    }
        
    /**
     * Inserts a new experimental data test type.
     * 
     * First Caveat:  JCMS data model design still carries a few non traditional 
     * denormalized data elements you would not find in a true relational 
     * database.  By virtue of this historical architecture the view and 
     * business logic support dynamic allocation while the test type data 
     * fields are limit to 30 instances.
     * 
     * Second Caveat:  JCMS Access treats boolean true as negative 1 instead of 1.
     * Until JCMS Access is phased out or updated to use 1 for true all true 
     * values are -1.
     * 
     * Future plans are to refactor this model to a relational structure.
     * 
     * @param dto
     * @return primary key
     * @throws SQLException 
     */
    public int insertTestType(ExpDataDescriptorDTO dto) throws SQLException {
        int pk = 0;
        // Insert first field
        String insertCmd = "INSERT INTO ExpDataDescriptor (testType, testTypeNotes) "
                + "VALUES (?, ?) " ;
        // In the same transaction update all denormalized fields, 1 through 30
        String updateCmd = "UPDATE ExpDataDescriptor SET "
                + "d#_caption = ?, d#_fieldDescription = ?, d#_dataFormat = ?, d#_required = ?, d#_minValue = ?, d#_maxValue = ? "
                + "WHERE _expDataDescriptor_key = ?" ;
        
        try { 
            // Begin Transaction
            con = this.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstmt = con.prepareStatement(insertCmd, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, dto.getTestType());
            pstmt.setString(2, dto.getTestTypeNotes());
            pstmt.addBatch();
            pstmt.executeBatch();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            pk = rs.getInt(1);
            //Using key in DTO as an integer
            dto.setExpDataDescriptor_key(pk);
            // Update test type fields 1 through 30
            Integer fieldNumber = 1;
            PreparedStatement pstmt2 = null;
            if (dto.getFields().size() > 0) {
                for (ExpDataDescriptorFieldDTO field : dto.getFields()) {
                    pstmt2 = con.prepareStatement(updateCmd.replace("#", fieldNumber.toString()));
                    pstmt2.setString(1, field.getCaption());
                    pstmt2.setString(2, field.getFieldDescription());
                    pstmt2.setString(3, field.getDataFormat());
                    pstmt2.setInt(4, (field.getRequired() == true ? -1: 0));
                    
                    if (field.getMinFloat() == null)
                        pstmt2.setNull(5, Types.FLOAT);
                    else
                        pstmt2.setFloat(5, field.getMinFloat());
                    
                    if (field.getMaxFloat() == null)
                        pstmt2.setNull(6, Types.FLOAT);
                    else
                        pstmt2.setFloat(6, field.getMaxFloat());
                    
                    pstmt2.setInt(7, pk);
                    pstmt2.addBatch();
                    int[] results = pstmt2.executeBatch();
                    fieldNumber++;
                }
            }
            
            // End Transaction
            con.commit();

            System.out.println("test type key:"+ pk);
        } catch (SQLFeatureNotSupportedException sfns) {
            con.rollback();
            throw new SQLException(sfns);
        } catch (BatchUpdateException bue) {
            // Rollback transaction if there are any errors
            con.rollback();
            throw new SQLException(bue);
        } catch (SQLException ex) {
            // Rollback transaction if there are any errors
            con.rollback();
            throw new SQLException(ex);
        } finally {
            this.closeConnection(con);
        }
        
        return pk;
    }
    
    public boolean deleteTestType(int expDataDescriptorKey) throws SQLException {
        String cmd = "DELETE FROM ExpDataDescriptor WHERE _expDataDescriptor_key = " + expDataDescriptorKey ;
        Integer count = this.executeJCMSWebUpdate(cmd);
        return count == 1;
    }
    
}
