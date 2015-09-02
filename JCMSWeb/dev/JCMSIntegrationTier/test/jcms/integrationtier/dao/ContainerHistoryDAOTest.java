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

import java.sql.ResultSet;
import jcms.integrationtier.colonyManagement.RoomEntity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cnh
 */
public class ContainerHistoryDAOTest {
    
    public ContainerHistoryDAOTest() {
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

    /**
     * Test of getContainerHistoryByContainerKey method, of class ContainerHistoryDAO.
     */
//    @Test
//    public void testGetContainerHistoryByContainerKey() throws Exception {
//        System.out.println("getContainerHistoryByContainerKey");
//        Integer containerKey = 19337;
//        ContainerHistoryDAO instance = new ContainerHistoryDAO();
//        ResultSet rs = instance.getContainerHistoryByContainerKey(containerKey);
//    }

    /**
     * Test of getRoomByContainerKey method, of class ContainerHistoryDAO.
     */
    @Test
    public void testGetRoomByContainerKey() throws Exception {
        System.out.println("getRoomByContainerKey");
        Integer containerKey = 19337;
        ContainerHistoryDAO instance = new ContainerHistoryDAO();
//        RoomEntity result = instance.getRoomByContainerKey(containerKey);
    }
}
