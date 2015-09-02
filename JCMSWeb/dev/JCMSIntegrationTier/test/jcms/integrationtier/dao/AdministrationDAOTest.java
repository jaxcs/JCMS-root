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

import java.sql.SQLException;
import jcms.integrationtier.dto.AdminWorkgroupUserFunctionalAreaDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cnh
 */
public class AdministrationDAOTest {
    
    public AdministrationDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

//    @Test
//    public void testGetCenters() {
//        System.out.println("getCenters");
//        AdministrationDAO instance = new AdministrationDAO();
//        
//        ArrayList<WorkgroupEntity> wList = new ArrayList<WorkgroupEntity> ();
//        WorkgroupEntity dto = new WorkgroupEntity();
//        dto.setWorkgroupkey(1);
//        wList.add(dto);
//        
//        ArrayList<AdminCenterDTO> result = instance.getCenters(wList);
//    }
//
//    @Test
//    public void testGetWorkgroups_ArrayList() {
//        System.out.println("getWorkgroups");
//        AdministrationDAO instance = new AdministrationDAO();
//
//        ArrayList<WorkgroupEntity> list = new ArrayList<WorkgroupEntity> ();
//        WorkgroupEntity dto = new WorkgroupEntity();
//        dto.setWorkgroupkey(1);
//        list.add(dto);
//        
//        ArrayList result = instance.getWorkgroups(list);
//    }
//
//    @Test
//    public void testGetWorkgroup() {
//        System.out.println("getWorkgroup");
//        String workgroupKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroup(workgroupKey);
//    }
//
//    @Test
//    public void testGetWorkgroups_String() {
//        System.out.println("getWorkgroups");
//        String centerKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroups(centerKey);
//    }
//
//    @Test
//    public void testGetWorkgroupUsers() {
//        System.out.println("getWorkgroupUsers");
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUsers();
//    }
//
//    @Test
//    public void testGetWorkgroupUsersByWorkgroup() {
//        System.out.println("getWorkgroupUsersByWorkgroup");
//        String workgroupKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUsersByWorkgroup(workgroupKey);
//    }
//
//    @Test
//    public void testGetWorkgroupUsersByUser() {
//        System.out.println("getWorkgroupUsersByUser");
//        String userKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUsersByUser(userKey);
//    }
//
//    @Test
//    public void testGetUsers() {
//        System.out.println("getUsers");
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getUsers();
//    }

//    @Test
//    public void testPrivileges() {
//        System.out.println("getPrivileges");
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getPrivileges();
//    }
    
//    @Test
//    public void testGetUser() {
//        System.out.println("getUser");
//        String userKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getUser(userKey);
//    }

//    @Test
//    public void testInsertUser() {
//        System.out.println("insertUser");
//        AdminUserDTO dto = new AdminUserDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-01 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-01 12:12:12");
//        dto.setDefaultWorkgroupKey("1");
//        dto.setFirstName("First");
//        dto.setLastName("Last");
//        dto.setIsActive("1");
//        dto.setNetworkID("NID2");
//        dto.setPassword_("pwd");
//        dto.setUserName("UName");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        String result = instance.insertUser(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testInsertWorkgroup() {
//        System.out.println("insertWorkgroup");
//        AdminWorkgroupDTO dto = new AdminWorkgroupDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-01 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-01 12:12:12");
//        dto.setWorkgroupName("WG1");
//        dto.setIsActive("1");
//        dto.setCenter_key("1");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        String result = instance.insertWorkgroup(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testInsertCenter() {
//        System.out.println("insertCenter");
//        AdminCenterDTO dto = new AdminCenterDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-01 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-01 12:12:12");
//        dto.setCenter("C1");
//        dto.setIsActive("1");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        String result = instance.insertCenter(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testInsertWorkgroupUser() {
//        System.out.println("insertWorkgroupUser");
//        AdminWorkgroupUserDTO dto = new AdminWorkgroupUserDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-01 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-01 12:12:12");
//        dto.setWorkgroup_key("1");
//        dto.setUser_key("1");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        String result = instance.insertWorkgroupUser(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testInsertWorkgroupUserFunctionalArea() {
//        System.out.println("insertWorkgroupUserFunctionalArea");
//        AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-01 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-01 12:12:12");
//        dto.setFunctionalArea_key("1");
//        dto.setWorkgroupUser_key("1");
//        dto.setPrivilege_key("1");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        String result = instance.insertWorkgroupUserFunctionalArea(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testGetFunctionalAreas() {
//        System.out.println("getFunctionalAreas");
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getFunctionalAreas();
//    }
//
//    @Test
//    public void testGetWorkgroupUserFunctionalAreas() {
//        System.out.println("getWorkgroupUserFunctionalAreas");
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUserFunctionalAreas();
//    }
//
//    @Test
//    public void testGetWorkgroupUserFunctionalAreasByFunctionalArea() {
//        System.out.println("getWorkgroupUserFunctionalAreasByFunctionalArea");
//        String functionalAreaKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUserFunctionalAreasByFunctionalArea(functionalAreaKey);
//    }
//
//    @Test
//    public void testGetWorkgroupUserFunctionalAreasByWorkgroupUser() {
//        System.out.println("getWorkgroupUserFunctionalAreasByWorkgroupUser");
//        String workgroupUserKey = "1";
//        AdministrationDAO instance = new AdministrationDAO();
//        ArrayList result = instance.getWorkgroupUserFunctionalAreasByWorkgroupUser(workgroupUserKey);
//    }
    
//    @Test
//    public void testUpdateUser() {
//        System.out.println("updateUser");
//        AdminUserDTO dto = new AdminUserDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-10 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-10 12:12:12");
//        
//        dto.setUserKey("10");
//        dto.setDefaultWorkgroupKey("1");
//        dto.setFirstName("Update");
//        dto.setLastName("Update");
//        dto.setIsActive("1");
//        dto.setNetworkID("Update");
//        dto.setPassword_("Update");
//        dto.setUserName("Update");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.updateUser(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testUpdateWorkgroup() {
//        System.out.println("updateWorkgroup");
//        AdminWorkgroupDTO dto = new AdminWorkgroupDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-10 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-10 12:12:12");
//        
//        dto.setWorkgroup_key("9");
//        dto.setCenter_key("1");
//        dto.setWorkgroupName("WG2");
//        dto.setIsActive("0");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.updateWorkgroup(dto);
//        } catch (SQLException e) {
//        }
//    }

//    @Test
//    public void testUpdateCenter() {
//        System.out.println("updateCenter");
//        AdminCenterDTO dto = new AdminCenterDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-10 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-10 12:12:12");
//        
//        dto.setCenterkey("2");
//        dto.setCenter("C2");
//        dto.setIsActive("0");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.updateCenter(dto);
//        } catch (SQLException e) {
//        }
//    }
    
//    @Test
//    public void testUpdateWorkgroupUser() {
//        System.out.println("updateWorkgroupUser");
//        AdminWorkgroupUserDTO dto = new AdminWorkgroupUserDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-10 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-10 12:12:12");
//        
//        dto.setWorkgroupUser_key("10");
//        dto.setWorkgroup_key("2");
//        dto.setUser_key("1");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.updateWorkgroupUser(dto);
//        } catch (SQLException e) {
//        }
//    }
    
//    @Test
//    public void testUpdateWorkgroupUserFunctionalArea() {
//        System.out.println("updateWorkgroupUserFunctionalArea");
//        AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO();
//        dto.setCreatedBy("cnh");
//        dto.setDateCreated("2013-01-10 12:12:12");
//        dto.setModifiedBy("cnh");
//        dto.setDateModified("2013-01-10 12:12:12");
//        
//        dto.setWorkgroupUserFunctionalArea_key("15");
//        dto.setFunctionalArea_key("2");
//        dto.setWorkgroupUser_key("10");
//        dto.setPrivilege_key("2");
//        dto.setVersion("1");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.updateWorkgroupUserFunctionalArea(dto);
//        } catch (SQLException e) {
//        }
//    }
    
//    @Test
//    public void testDeleteWorkgroupUserFunctionalArea() {
//        System.out.println("deleteWorkgroupUserFunctionalArea");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.deleteWorkgroupUserFunctionalArea("15");
//        } catch (SQLException e) {
//        }
//    }
    
//    @Test
//    public void testDeleteWorkgroupUser() {
//        System.out.println("deleteWorkgroupUser");
//        AdministrationDAO instance = new AdministrationDAO();
//        try {
//        Integer result = instance.deleteWorkgroupUser("10");
//        } catch (SQLException e) {
//        }
//    }
    
//
    
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        AdministrationDAO instance = new AdministrationDAO();
        try {
        Integer result = instance.deleteUser("12");
        } catch (SQLException e) {
        }
    }

    
}
