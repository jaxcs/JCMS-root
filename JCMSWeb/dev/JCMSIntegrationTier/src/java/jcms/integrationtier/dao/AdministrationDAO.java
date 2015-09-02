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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.dto.AdminCenterDTO;
import jcms.integrationtier.dto.AdminFunctionalAreaDTO;
import jcms.integrationtier.dto.AdminPrivilegeDTO;
import jcms.integrationtier.dto.AdminUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupDTO;
import jcms.integrationtier.dto.AdminWorkgroupUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupUserFunctionalAreaDTO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.FunctionalAreaPrivilegeDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 *
 * @author mkamato
 */
public class AdministrationDAO extends MySQLDAO {
    ArrayList<String> wgs;    
    
    private String centerRootQuery = "SELECT DISTINCT ABS(c._Center_key) as centerKey, TRIM(c.Center) AS center, ABS(c.IsActive) as isActive, " 
            + "TRIM(c.CreatedBy) AS createdBy, CONCAT(DATE(c.DateCreated),' ',TIME(c.DateCreated)) AS dateCreated, TRIM(c.ModifiedBy) AS modifiedBy , CONCAT(DATE(c.DateModified),' ',TIME(c.DateModified)) AS dateModified, ABS(c.Version) AS version " 
            + "FROM Center c JOIN Workgroup w ON c._Center_key = w._Center_key "
            + "WHERE c._Center_key > 0 ";
    
    private String allCentersQuery = "SELECT DISTINCT ABS(c._Center_key) as centerKey, TRIM(c.Center) AS center, ABS(c.IsActive) as isActive, " 
            + "TRIM(c.CreatedBy) AS createdBy, CONCAT(DATE(c.DateCreated),' ',TIME(c.DateCreated)) AS dateCreated, TRIM(c.ModifiedBy) AS modifiedBy , CONCAT(DATE(c.DateModified),' ',TIME(c.DateModified)) AS dateModified, ABS(c.Version) AS version " 
            + "FROM Center c "
            + "WHERE c._Center_key > 0 ";
    
    private String workgroupRootQuery = "SELECT ABS(_Workgroup_key) AS workgroupKey, ABS(_Center_key) AS centerKey, TRIM(WorkgroupName) AS workgroupName, " 
            + "ABS(IsActive) AS isActive, "
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM Workgroup "
            + "WHERE _Workgroup_key > 0 ";
    
    private String workgroupUserRootQuery = "SELECT ABS(_WorkgroupUser_key) AS workgroupUserKey, ABS(_Workgroup_key) AS workgroupKey, ABS(_User_key) AS userKey, " 
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM WorkgroupUser "
            + "WHERE _WorkgroupUser_key > 0 ";
    
    private String userRootQuery = "SELECT ABS(_User_key) AS userKey, TRIM(UserName) AS userName, " 
            + "ABS(_DefaultWorkgroup_key) AS defaultWorkgroupKey, TRIM(FirstName) AS firstName, TRIM(LastName) as lastName, ABS(IsActive) AS isActive, "
            + "TRIM(Password_) AS password_, TRIM(NetworkID) AS networkID, TRIM(IsMasterAdministrator) AS isMasterAdministrator, "
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM User "
            + "WHERE _User_key > 0 ";
    
    private String functionalAreaRootQuery = "SELECT ABS(_FunctionalArea_key) AS functionalAreaKey, TRIM(FunctionalArea) AS functionalArea, " 
            + "TRIM(Description) AS description, "
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM FunctionalArea "
            + "WHERE _FunctionalArea_key > 0 ";

    private String workgroupUserFunctionalAreaRootQuery = "SELECT ABS(_WorkgroupUserFunctionalArea_key) AS workgroupUserFunctionalAreaKey, "
            + "ABS(_WorkgroupUser_key) AS workgroupUserKey, ABS(_Privilege_key) AS privilegeKey, " 
            + "ABS(_FunctionalArea_key) AS functionalAreaKey, "
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM WorkgroupUserFunctionalArea "
            + "WHERE _WorkgroupUserFunctionalArea_key > 0 ";
    
    private String privilegeRootQuery = "SELECT ABS(_Privilege_key) AS privilegeKey, TRIM(Privilege) AS privilege, TRIM(Description) AS description, " 
            + "TRIM(CreatedBy) AS createdBy, CONCAT(DATE(DateCreated),' ',TIME(DateCreated)) AS dateCreated, TRIM(ModifiedBy) AS modifiedBy , CONCAT(DATE(DateModified),' ',TIME(DateModified)) AS dateModified, ABS(Version) AS version " 
            + "FROM Privilege "
            + "WHERE _Privilege_key > 0 ";
    
    private String adminWorkgroupsByUserQuery = "SELECT ABS(Workgroup._Workgroup_key) AS workgroupKey, ABS(Workgroup._Center_key) AS centerKey, TRIM(Workgroup.WorkgroupName) AS workgroupName, " 
            + "ABS(Workgroup.IsActive) AS isActive, TRIM(Workgroup.CreatedBy) AS createdBy, "
            + "CONCAT(DATE(Workgroup.DateCreated),' ',TIME(Workgroup.DateCreated)) AS dateCreated, TRIM(Workgroup.ModifiedBy) AS modifiedBy , "
            + "CONCAT(DATE(Workgroup.DateModified),' ',TIME(Workgroup.DateModified)) AS dateModified, ABS(Workgroup.Version) AS version "
            + "FROM WorkgroupUserFunctionalArea "
            + "JOIN WorkgroupUser ON WorkgroupUserFunctionalArea._WorkgroupUser_key = WorkgroupUser._WorkgroupUser_key "
            + "JOIN Workgroup ON WorkgroupUser._Workgroup_key = Workgroup._Workgroup_key "
            + "JOIN User ON WorkgroupUser._User_key = User._User_key "
            + "WHERE WorkgroupUserFunctionalArea._FunctionalArea_key = 1 AND User._User_key = ";
    
    private Connection con = null;

    public AdministrationDAO () {
    }
    
    private String getWorkgroupKeys(ArrayList<WorkgroupEntity> list) {
        String keys = "(";
        for (WorkgroupEntity entity : list) {
            if (keys.length() == 1)
                keys += entity.getWorkgroupkey();
            else 
                keys += ", "+  entity.getWorkgroupkey();
        }
        keys += ")";
        return keys;
    }
    
    private String getWorkgroupKeysWGDTO(ArrayList<AdminWorkgroupDTO> list){
        String keys = "(";
        for(AdminWorkgroupDTO dto : list){
            if(keys.length() == 1){
                keys+= dto.getWorkgroup_key();
            } 
            else{
                keys += ", " + dto.getWorkgroup_key();
            }
        }
        keys += ")";
        return keys;
    }
    
    public int getWorkgroupCount(String userKey){
        String query = "SELECT COUNT(*) AS workgroupCount" +
                       " FROM WorkgroupUser" +
                       " JOIN User" +
                       " ON WorkgroupUser._user_key = User._user_key" +
                       " JOIN Workgroup" +
                       " ON WorkgroupUser._workgroup_key = Workgroup._workgroup_key" +
                       " WHERE User._user_key = " + userKey;
        Result result = executeQuery(query);
        SortedMap[] rows = result.getRows();
        for(SortedMap row : rows){
            return Integer.parseInt(myGet("workgroupCount", row));            
        }
        return 0;
    }
    
    public int getAdminWorkgroupCount(String userKey){
        String query = "SELECT COUNT(*) AS adminWorkgroupCount" +
                       " FROM WorkgroupUser" +
                       " JOIN User" +
                       " ON WorkgroupUser._user_key = User._user_key" +
                       " JOIN Workgroup" +
                       " ON WorkgroupUser._workgroup_key = Workgroup._workgroup_key" +
                       " JOIN WorkgroupUserFunctionalArea" +
                       " ON WorkgroupUser._workgroupUser_key = WorkgroupUserFunctionalArea._workgroupUser_key" +
                       " JOIN FunctionalArea" +
                       " ON WorkgroupUserFunctionalArea._functionalArea_key = FunctionalArea._functionalArea_key" +
                       " WHERE User._user_key = " + userKey +
                       " AND FunctionalArea = 'Administration';";
        Result result = executeQuery(query);
        SortedMap[] rows = result.getRows();
        for(SortedMap row : rows){
            return Integer.parseInt(myGet("adminWorkgroupCount", row));            
        }
        return 0;
    }
    
    public ArrayList<AdminCenterDTO> getCenters(ArrayList<AdminWorkgroupDTO> list){
        ArrayList<AdminCenterDTO> dtoList = new ArrayList<AdminCenterDTO> ();
        AdminCenterDTO dto = null;
        Result result = executeQuery(centerRootQuery +" AND w._Workgroup_key IN "+ getWorkgroupKeysWGDTO(list) +" ORDER BY Center");

        for (SortedMap row : result.getRows()) {
            dto = new AdminCenterDTO(
                myGet("centerKey", row),
                myGet("center", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminCenterDTO> getCenters(){
        ArrayList<AdminCenterDTO> dtoList = new ArrayList<AdminCenterDTO> ();
        AdminCenterDTO dto = null;
        Result result = executeQuery(allCentersQuery + " ORDER BY Center");

        for (SortedMap row : result.getRows()) {
            dto = new AdminCenterDTO(
                myGet("centerKey", row),
                myGet("center", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public String insertCenter(AdminCenterDTO dto) throws SQLException {
        String pk = null;
        String cmd = "INSERT INTO Center (Center, IsActive, "
            + "CreatedBy, DateCreated, ModifiedBy, DateModified, Version) "
            + "VALUES ";
        cmd += "('"+ dto.getCenter() +"',"+ dto.getIsActive() +",'"
            + dto.getCreatedBy() +"',DATE_FORMAT('"+ dto.getDateCreated() +"', '"+ super.MYSQL_DATE_FORMAT +"'),'"
            + dto.getModifiedBy() +"',DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"'), "
            + dto.getVersion() +") ";
        
            Integer count = executeUpdate(cmd);
            Result result = executeQuery("SELECT MAX(_Center_key) FROM Center");
            for (SortedMap row : result.getRows()) {
                pk = myGet("MAX(_Center_key)", row);
                break;
            }
        
        return pk;
    }
    
    public void insertOwner(AdminWorkgroupDTO dto) throws SQLException {
        Result maxOwnerKeyResult = executeJCMSQuery("SELECT MAX(_owner_key) AS maxKey FROM Owner;");
        String ownerKey = new Integer(Integer.parseInt(myGet("maxKey" ,maxOwnerKeyResult.getRows()[0]))+1).toString();
        String ownerInsert = "INSERT INTO Owner (_owner_key, owner) VALUES (" + ownerKey + ", '" + dto.getWorkgroupName() + "');" ;
        this.executeJCMSUpdate(ownerInsert);
    }
    
    public OwnerEntity getOwnerEntityByName(String ownerName) throws Exception{
        String query = "SELECT * FROM Owner WHERE owner = '" + ownerName + "'";
        OwnerEntity oe = new OwnerEntity();
        SortedMap owner = executeJCMSQuery(query).getRows()[0];
        oe.setOwnerKey(Integer.parseInt(myGet("_owner_key", owner)));
        oe.setOwner(myGet("owner", owner));
        return oe;
    }
    
    public ArrayList<String> getOwnerNames(){
        ArrayList<String> names = new ArrayList<String>();
        String query = "SELECT owner FROM Owner;";
        SortedMap[] ownerNames = this.executeJCMSQuery(query).getRows();
        for(SortedMap ownerName : ownerNames){
            names.add(myGet("owner", ownerName));
        }
        return names;
    }
    
    public Integer updateCenter(AdminCenterDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE Center SET " 
            + " Center = '" + dto.getCenter()
            + "', IsActive = " + dto.getIsActive()
            + ", ModifiedBy = '" + dto.getModifiedBy()
            + "', DateModified = now() " 
            + ", Version = " + version.toString()
            + " WHERE _Center_key = " + dto.getCenterkey() ;
        
            Integer count = executeUpdate(cmd);
        
        return count;
    }
    
    public Integer deleteCenter(String primaryKey) throws SQLException {
        String cmd = "DELETE FROM Center " 
            + " WHERE _Center_key = " + primaryKey ;
        Integer count = executeUpdate(cmd);
        return count;
    }
    
    public ArrayList<AdminWorkgroupDTO> getWorkgroups(ArrayList<WorkgroupEntity> list){
        ArrayList<AdminWorkgroupDTO> dtoList = new ArrayList<AdminWorkgroupDTO> ();
        AdminWorkgroupDTO dto = null;
        Result result = executeQuery(workgroupRootQuery +" AND _Workgroup_key IN "+ getWorkgroupKeys(list) +" ORDER BY WorkgroupName");

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupDTO(
                myGet("workgroupKey", row),
                myGet("centerKey", row),
                myGet("workgroupName", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminWorkgroupDTO> getAdminWorkgroups(String userKey){
        ArrayList<AdminWorkgroupDTO> dtoList = new ArrayList<AdminWorkgroupDTO> ();
        AdminWorkgroupDTO dto = null;
        Result result = executeQuery(adminWorkgroupsByUserQuery + userKey + " ORDER BY WorkgroupName");
        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupDTO(
                myGet("workgroupKey", row),
                myGet("centerKey", row),
                myGet("workgroupName", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public AdminWorkgroupDTO getWorkgroup(String workgroupKey){
        
        AdminWorkgroupDTO dto = null;
        Result result = executeQuery(workgroupRootQuery + " AND _Workgroup_key = "+ workgroupKey);

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupDTO(
                myGet("workgroupKey", row),
                myGet("centerKey", row),
                myGet("workgroupName", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            break;
        }
        return dto;
    }
    
    public ArrayList<AdminWorkgroupDTO> getWorkgroups(String centerKey){
        ArrayList<AdminWorkgroupDTO> dtoList = new ArrayList<AdminWorkgroupDTO> ();
        AdminWorkgroupDTO dto = null;
        Result result = executeQuery(workgroupRootQuery + " AND _Center_key = "+ centerKey +" ORDER BY WorkgroupName");

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupDTO(
                myGet("workgroupKey", row),
                myGet("centerKey", row),
                myGet("workgroupName", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminWorkgroupDTO> getWorkgroups(){
        ArrayList<AdminWorkgroupDTO> dtoList = new ArrayList<AdminWorkgroupDTO> ();
        AdminWorkgroupDTO dto = null;
        Result result = executeQuery("SELECT * FROM Workgroup;");

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupDTO(
                myGet("workgroupKey", row),
                myGet("centerKey", row),
                myGet("workgroupName", row),
                myGet("isActive", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }

    public String insertWorkgroup(AdminWorkgroupDTO dto) throws SQLException {
        String pk = null;
        String cmd = "INSERT INTO Workgroup (_Center_key, WorkgroupName, IsActive, "
            + "CreatedBy, DateCreated, ModifiedBy, DateModified, Version) "
            + "VALUES ";
        cmd += "("+ dto.getCenter_key() +",'"+ dto.getWorkgroupName() +"',"+ dto.getIsActive() +",'"
            + dto.getCreatedBy() +"',DATE_FORMAT('"+ dto.getDateCreated() +"', '"+ super.MYSQL_DATE_FORMAT +"'),'"
            + dto.getModifiedBy() +"',DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"'), "
            + dto.getVersion() +") ";
        
            Integer count = executeUpdate(cmd);
            Result result = executeQuery("SELECT MAX(_Workgroup_key) FROM Workgroup");
            for (SortedMap row : result.getRows()) {
                pk = myGet("MAX(_Workgroup_key)", row);
                break;
            }
        insertOwner(dto);
        return pk;
    }
    
    public Integer updateWorkgroup(AdminWorkgroupDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE Workgroup SET " 
            + " _Center_key = " + dto.getCenter_key() 
            + ", WorkgroupName = '" + dto.getWorkgroupName()
            + "', IsActive = " + dto.getIsActive()
            + ", ModifiedBy = '" + dto.getModifiedBy()
            + "', DateModified = now() " 
            + ", Version = " + version.toString()
            + " WHERE _Workgroup_key = " + dto.getWorkgroup_key() ;
        
            Integer count = executeUpdate(cmd);
        
        return count;
    }
    
    public Integer deleteWorkgroup(String primaryKey) throws SQLException {
        String cmd = "DELETE FROM Workgroup " 
            + " WHERE _Workgroup_key = " + primaryKey ;
        Integer count = executeUpdate(cmd);
        return count;
    }
    
    public ArrayList<AdminWorkgroupUserDTO> getWorkgroupUsers(){
        ArrayList<AdminWorkgroupUserDTO> dtoList = new ArrayList<AdminWorkgroupUserDTO> ();
        AdminWorkgroupUserDTO dto = null;
        Result result = executeQuery(workgroupUserRootQuery);

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserDTO(
                myGet("workgroupUserKey", row),
                myGet("workgroupKey", row),
                myGet("userKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public String insertWorkgroupUser(AdminWorkgroupUserDTO dto) throws SQLException {
        String pk = null;
        String cmd = "INSERT INTO WorkgroupUser (_Workgroup_key, _User_key, "
            + "CreatedBy, DateCreated, ModifiedBy, DateModified, Version) "
            + "VALUES ";
        cmd += "("+ dto.getWorkgroup_key() +","+ dto.getUser_key() +",'"
            + dto.getCreatedBy() +"',DATE_FORMAT('"+ dto.getDateCreated() +"', '"+ super.MYSQL_DATE_FORMAT +"'),'"
            + dto.getModifiedBy() +"',DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"'), "
            + dto.getVersion() +") ";
        
            Integer count = executeUpdate(cmd);
            Result result = executeQuery("SELECT MAX(_WorkgroupUser_key) FROM WorkgroupUser");
            for (SortedMap row : result.getRows()) {
                pk = myGet("MAX(_WorkgroupUser_key)", row);
                break;
            }
        
        return pk;
    }

    public Integer updateWorkgroupUser(AdminWorkgroupUserDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE WorkgroupUser SET " 
            + " _Workgroup_key = " + dto.getWorkgroup_key()
            + ", _User_key = " + dto.getUser_key()
            + ", ModifiedBy = '" + dto.getModifiedBy()
            + "', DateModified = now() " 
            + ", Version = " + version.toString()
            + " WHERE _WorkgroupUser_key = " + dto.getWorkgroupUser_key() ;
        
            Integer count = executeUpdate(cmd);
        
        return count;
    }
    
    public Integer deleteWorkgroupUser(String primaryKey) throws SQLException {
        String cmd = "DELETE FROM WorkgroupUser " 
            + " WHERE _WorkgroupUser_key = " + primaryKey ;
        Integer count = executeUpdate(cmd);
        return count;
    }
    
    public ArrayList<AdminWorkgroupUserDTO> getWorkgroupUsersByWorkgroup(String workgroupKey){
        ArrayList<AdminWorkgroupUserDTO> dtoList = new ArrayList<AdminWorkgroupUserDTO> ();
        AdminWorkgroupUserDTO dto = null;
        Result result = executeQuery(workgroupUserRootQuery +" AND _Workgroup_key = "+ workgroupKey );

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserDTO(
                myGet("workgroupUserKey", row),
                myGet("workgroupKey", row),
                myGet("userKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminWorkgroupUserDTO> getWorkgroupUsersByUser(String userKey){
        ArrayList<AdminWorkgroupUserDTO> dtoList = new ArrayList<AdminWorkgroupUserDTO> ();
        AdminWorkgroupUserDTO dto = null;
        Result result = executeQuery(workgroupUserRootQuery +" AND _User_key = "+ userKey );

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserDTO(
                myGet("workgroupUserKey", row),
                myGet("workgroupKey", row),
                myGet("userKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminUserDTO> getUsers(){
        ArrayList<AdminUserDTO> dtoList = new ArrayList<AdminUserDTO> ();
        AdminUserDTO dto = null;
        Result result = executeQuery(userRootQuery +" ORDER BY LastName");

        for (SortedMap row : result.getRows()) {
            dto = new AdminUserDTO(
                myGet("userKey", row),
                myGet("defaultWorkgroupKey", row),
                myGet("userName", row),
                myGet("firstName", row),
                myGet("lastName", row),
                myGet("isActive", row),
                myGet("password_", row),
                myGet("networkID", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row),
                myGet("isMasterAdministrator", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminUserDTO> getUser(String userKey){
        ArrayList<AdminUserDTO> dtoList = new ArrayList<AdminUserDTO> ();
        AdminUserDTO dto = null;
        Result result = executeQuery(userRootQuery +" AND _User_key = "+ userKey);

        for (SortedMap row : result.getRows()) {
            dto = new AdminUserDTO(
                myGet("userKey", row),
                myGet("defaultWorkgroupKey", row),
                myGet("userName", row),
                myGet("firstName", row),
                myGet("lastName", row),
                myGet("isActive", row),
                myGet("password_", row),
                myGet("networkID", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row),
                myGet("isMasterAdministrator", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public String insertUser(AdminUserDTO dto) throws SQLException {
        String pk = null;
        String cmd = "INSERT INTO User (_DefaultWorkgroup_key, UserName, FirstName, LastName, IsActive, Password_, NetworkID, "
            + "CreatedBy, DateCreated, ModifiedBy, DateModified, Version, IsMasterAdministrator, passwordChangedDate) "
            + "VALUES ";
        cmd += "("+ dto.getDefaultWorkgroupKey() +",'"+ dto.getUserName() +"','"+ dto.getFirstName() +"','"
            + dto.getLastName() +"',"+ dto.getIsActive() +",'"+ dto.getPassword_() +"','"+ dto.getNetworkID() +"','"
            + dto.getCreatedBy() +"',DATE_FORMAT('"+ dto.getDateCreated() +"', '"+ super.MYSQL_DATE_FORMAT +"'),'"
            + dto.getModifiedBy() +"',DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"'), "
            + dto.getVersion() + ", " + dto.getIsMasterAdministrator()+ ", "
            + "DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"')) ";
        
            Integer count = executeUpdate(cmd);
            Result result = executeQuery("SELECT MAX(_User_key) FROM `User`");
            for (SortedMap row : result.getRows()) {
                pk = myGet("MAX(_User_key)", row);
                break;
            }
        
        return pk;
    }
    
    public Integer updateUser(AdminUserDTO dto, boolean passwordChanged) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE User SET " 
            + " _DefaultWorkgroup_key = " + dto.getDefaultWorkgroupKey() 
            + ", UserName = '" + dto.getUserName()
            + "', FirstName = '" + dto.getFirstName()
            + "', LastName = '" + dto.getLastName()
            + "', IsActive = " + dto.getIsActive()
            + ", Password_ = '" + dto.getPassword_()
            + "', NetworkID = '" + dto.getNetworkID()
            + "', ModifiedBy = '" + dto.getModifiedBy()
            + "', IsMasterAdministrator = '" + dto.getIsMasterAdministrator()
            + "', DateModified = now() " 
            + ", Version = " + version.toString()
            + " WHERE _User_key = " + dto.getUserKey() ;
        Integer count = executeUpdate(cmd);
        
        if(passwordChanged){
            String cmd2 = "UPDATE User SET `passwordChangedDate` = now() WHERE _User_key = " + dto.getUserKey();
            executeUpdate(cmd2);
        }
        
        
        return count;
    }
    
    public Integer deleteUser(String primaryKey) throws SQLException {
        String cmd = "DELETE FROM User " 
            + " WHERE _User_key = " + primaryKey ;
        Integer count = executeUpdate(cmd);
        return count;
    }
    
    public ArrayList<AdminFunctionalAreaDTO> getFunctionalAreas(){
        ArrayList<AdminFunctionalAreaDTO> dtoList = new ArrayList<AdminFunctionalAreaDTO> ();
        AdminFunctionalAreaDTO dto = null;
        Result result = executeQuery(functionalAreaRootQuery);

        for (SortedMap row : result.getRows()) {
            dto = new AdminFunctionalAreaDTO(
                myGet("functionalAreaKey", row),
                myGet("functionalArea", row),
                myGet("description", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
   
    public ArrayList<AdminWorkgroupUserFunctionalAreaDTO> getWorkgroupUserFunctionalAreas(){
        ArrayList<AdminWorkgroupUserFunctionalAreaDTO> dtoList = new ArrayList<AdminWorkgroupUserFunctionalAreaDTO> ();
        AdminWorkgroupUserFunctionalAreaDTO dto = null;
        Result result = executeQuery(workgroupUserFunctionalAreaRootQuery);

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserFunctionalAreaDTO(
                myGet("workgroupUserFunctionalAreaKey", row),
                myGet("functionalAreaKey", row),
                myGet("workgroupUserKey", row),
                myGet("privilegeKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public Integer updateWorkgroupUserFunctionalArea(AdminWorkgroupUserFunctionalAreaDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE WorkgroupUserFunctionalArea SET " 
            + " _FunctionalArea_key = " + dto.getFunctionalArea_key()
            + ", _WorkgroupUser_key = " + dto.getWorkgroupUser_key()
            + ", _Privilege_key = " + dto.getPrivilege_key()
            + ", ModifiedBy = '" + dto.getModifiedBy()
            + "', DateModified = now() " 
            + ", Version = " + version.toString()
            + " WHERE _WorkgroupUserFunctionalArea_key = " + dto.getWorkgroupUserFunctionalArea_key() ;
        
            Integer count = executeUpdate(cmd);
        
        return count;
    }

    public ArrayList<FunctionalAreaPrivilegeDTO> getDefaultWorkgroupFunctionalAreasByUserKey(String userKey){
        ArrayList<FunctionalAreaPrivilegeDTO> dtoList = new ArrayList<FunctionalAreaPrivilegeDTO> ();
        FunctionalAreaPrivilegeDTO dto = null;
        String query = "SELECT TRIM(FunctionalArea) AS functionalArea, TRIM(Privilege) AS privilege " 
            + "FROM WorkgroupUserFunctionalArea wufa "
            + "JOIN Privilege p ON wufa._Privilege_key = p._Privilege_key "
            + "JOIN FunctionalArea fa ON wufa._FunctionalArea_key = fa._FunctionalArea_key "
            + "JOIN WorkgroupUser W ON W._WorkgroupUser_key = wufa._WorkgroupUser_key "
            + "JOIN `User` U ON W._User_key = U._User_key "
            + "WHERE W._User_key = " + userKey 
            + "  AND U._DefaultWorkgroup_key = W._Workgroup_key";
        
        Result result = executeQuery(query);

        for (SortedMap row : result.getRows()) {
            dto = new FunctionalAreaPrivilegeDTO(
                myGet("functionalArea", row),
                myGet("privilege", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<FunctionalAreaPrivilegeDTO> getWorkgroupFunctionalAreasByUserKey(String userKey){
        ArrayList<FunctionalAreaPrivilegeDTO> dtoList = new ArrayList<FunctionalAreaPrivilegeDTO> ();
        FunctionalAreaPrivilegeDTO dto = null;
        String query = "SELECT TRIM(FunctionalArea) AS functionalArea, TRIM(Privilege) AS privilege " 
            + "FROM WorkgroupUserFunctionalArea wufa "
            + "JOIN Privilege p ON wufa._Privilege_key = p._Privilege_key "
            + "JOIN FunctionalArea fa ON wufa._FunctionalArea_key = fa._FunctionalArea_key "
            + "JOIN WorkgroupUser W ON W._WorkgroupUser_key = wufa._WorkgroupUser_key "
            + "JOIN `User` U ON W._User_key = U._User_key "
            + "WHERE W._User_key = " + userKey ;
        
        Result result = executeQuery(query);

        for (SortedMap row : result.getRows()) {
            dto = new FunctionalAreaPrivilegeDTO(
                myGet("functionalArea", row),
                myGet("privilege", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
   
    public Integer deleteWorkgroupUserFunctionalArea(String primaryKey) throws SQLException {
        String cmd = "DELETE FROM WorkgroupUserFunctionalArea " 
            + " WHERE _WorkgroupUserFunctionalArea_key = " + primaryKey ;
        
        Integer count = executeUpdate(cmd);
        
        return count;
    }
    
    public String insertWorkgroupUserFunctionalArea(AdminWorkgroupUserFunctionalAreaDTO dto) throws SQLException {
        String pk = null;
        String cmd = "INSERT INTO WorkgroupUserFunctionalArea (_FunctionalArea_key, _WorkgroupUser_key, _Privilege_key, "
            + "CreatedBy, DateCreated, ModifiedBy, DateModified, Version) "
            + "VALUES ";
        cmd += "("+ dto.getFunctionalArea_key() +","+ dto.getWorkgroupUser_key() +","+ dto.getPrivilege_key() +",'"
            + dto.getCreatedBy() +"',DATE_FORMAT('"+ dto.getDateCreated() +"', '"+ super.MYSQL_DATE_FORMAT +"'),'"
            + dto.getModifiedBy() +"',DATE_FORMAT('"+ dto.getDateModified() +"', '"+ super.MYSQL_DATE_FORMAT +"'), "
            + dto.getVersion() +") ";
        
            Integer count = executeUpdate(cmd);
            Result result = executeQuery("SELECT MAX(_WorkgroupUserFunctionalArea_key) FROM WorkgroupUserFunctionalArea");
            for (SortedMap row : result.getRows()) {
                pk = myGet("MAX(_WorkgroupUserFunctionalArea_key)", row);
                break;
            }
        
        return pk;
    }

    public ArrayList<AdminWorkgroupUserFunctionalAreaDTO> getWorkgroupUserFunctionalAreasByFunctionalArea(String functionalAreaKey){
        ArrayList<AdminWorkgroupUserFunctionalAreaDTO> dtoList = new ArrayList<AdminWorkgroupUserFunctionalAreaDTO> ();
        AdminWorkgroupUserFunctionalAreaDTO dto = null;
        Result result = executeQuery(workgroupUserFunctionalAreaRootQuery + " AND _FunctionalArea_key = "+ functionalAreaKey);

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserFunctionalAreaDTO(
                myGet("workgroupUserFunctionalAreaKey", row),
                myGet("functionalAreaKey", row),
                myGet("workgroupUserKey", row),
                myGet("privilegeKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminWorkgroupUserFunctionalAreaDTO> getWorkgroupUserFunctionalAreasByWorkgroupUser(String workgroupUserKey){
        ArrayList<AdminWorkgroupUserFunctionalAreaDTO> dtoList = new ArrayList<AdminWorkgroupUserFunctionalAreaDTO> ();
        AdminWorkgroupUserFunctionalAreaDTO dto = null;
        Result result = executeQuery(workgroupUserFunctionalAreaRootQuery + " AND _WorkgroupUser_key = "+ workgroupUserKey);

        for (SortedMap row : result.getRows()) {
            dto = new AdminWorkgroupUserFunctionalAreaDTO(
                myGet("workgroupUserFunctionalAreaKey", row),
                myGet("functionalAreaKey", row),
                myGet("workgroupUserKey", row),
                myGet("privilegeKey", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminPrivilegeDTO> getPrivileges(){
        ArrayList<AdminPrivilegeDTO> dtoList = new ArrayList<AdminPrivilegeDTO> ();
        AdminPrivilegeDTO dto = null;
        Result result = executeQuery(privilegeRootQuery);

        for (SortedMap row : result.getRows()) {
            dto = new AdminPrivilegeDTO(
                myGet("privilegeKey", row),
                myGet("privilege", row),
                myGet("description", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
            dtoList.add(dto);
        }
        return dtoList;
    }
   
    public Result executeQuery(String theQuery){
        ResultSet myResultSet;
        Result myResult;
        
        try {
            con = super.getJCMSWebConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
         }
         catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            super.closeConnection(con);
            return null;
        }
    }
    
    public Integer executeUpdate(String theUpdate) throws SQLException {
        Integer myResult = 0;
        
        try {
            con = super.getJCMSWebConnection();
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myResult = stmt.executeUpdate(theUpdate);
            super.closeConnection(con);
            return myResult;
        }
        catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);  
            super.closeConnection(con);
            throw new SQLException("Insert Failed: "+ theUpdate);
        } 
    }
        
    @Override
    protected String myGet(String field, SortedMap result){
        if(result.get(field) == null){
            return "";
        }
        else{
            return result.get(field).toString();
        }
    }

}
