package jax.cs.dataAccess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccessJDBC;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author daves
 */
public class DataAccessMsAccessTest {

    
    DataAccessJDBC instance;
    
    public DataAccessMsAccessTest() {
    }

    
    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new DataAccessJDBC("sun.jdbc.odbc.JdbcOdbcDriver","jdbc:odbc:JPT_Unit");
 
        // "sun.jdbc.odbc.JdbcOdbcDriver"
        
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getPartent method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetPartent() {
        System.out.println("getPartent");
        String strMouseID = "A11";
        Collection xExpResult = new ArrayList();
        xExpResult.add("A5 : 3");
        xExpResult.add("A6 : 3");
        xExpResult.add("A7 : 3");
        try {
        Collection result = instance.getPartent(strMouseID);
        assertEquals(xExpResult, result);
        } catch ( Exception e ) {}
    }

    /**
     * Test of getChildren method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetChildren() {
        System.out.println("getChildren");
        String strMouseID = "A11";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("A13 : 5 : 50");
        xExpResult.add("A14 : 5 : 50");
        xExpResult.add("A15 : 5 : 50");
        try {
        Collection result = instance.getChildren(strMouseID);
        assertEquals(xExpResult, result);
        } catch ( Exception e ) {}

    }

    /**
     * Test of getSyblings method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetSyblings() {
        System.out.println("getSyblings");
        String strMouseID = "A10";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("A9");
        xExpResult.add("A11");
        xExpResult.add("A12");
        try {
        Collection result = instance.getSyblings(strMouseID);
        assertEquals(xExpResult, result);
                } catch (Exception e) {}

    }

    /**
     * Test of getMouseData method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetMouseData() {
        System.out.println("getMouseData");
        String strMouseID = "A10";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("ID=A10");
        xExpResult.add("origin=JAX");
        xExpResult.add("protocol=");
        xExpResult.add("owner=nobody");
        
        xExpResult.add("sex=F");
        xExpResult.add("generation=F03");
        xExpResult.add("codNotes=null");

        xExpResult.add("cod=null");
        xExpResult.add("exitDate=null");
        xExpResult.add("birthDate=2008-02-29 00:00:00");
        xExpResult.add("comment=ADDED IN BULK AS LITTER by user: mtsadmin");
        try {
        Collection result = instance.getMouseData(strMouseID);
        System.out.print(result);
        assertEquals(xExpResult, result);
        } catch (Exception e) {}
    }
    /**
     * Test of getMouseStrain method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetMouseStrain() {
        System.out.println("getMouseStrain");
        String strMouseID = "A10";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("{BALB/cJ}");
        try {
        Collection result = instance.getMouseStrain(strMouseID);
       // System.out.print(result);
        assertEquals(xExpResult, result);
        } catch (Exception e) {}
    }

    /**
     * Test of getMouseGenoType method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetMouseGenoType() {
        System.out.println("getMouseGenoType");
        String strMouseID = "A10";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("{MyJeans}:{Levi}:{KO}:	{+}{1}:	{+}{1}");
        try {
        Collection result = instance.getMouseGenoType(strMouseID);
        //System.out.print(result);
        assertEquals(xExpResult, result);
                } catch (Exception e) {}

        
    }

        /**
     * Test of getMouseGenoType method, of class DataAccessMySQL.
     */
    @Test
    public void testGetMouseGenoType_NoConf() {
        System.out.println("getMouseGenoType");
        String strMouseID = "A11";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("{MyJeans}:{Levi}:{KO}:	{-}{1}:	{+}{0}");
        try {
        Collection result = instance.getMouseGenoType(strMouseID);
        System.out.print(result);
        assertEquals(xExpResult, result);
                } catch (Exception e) {}

    }
    
    /**
     * Test of getMouseList method, of class DataAccessMsAccess.
     */
    @Test
    public void testGetMouseList() {
        System.out.println("getMouseList");
        String strMouseID = "A11";
        ArrayList xExpResult = new ArrayList();
        xExpResult.add("{MyJeans}:{Levi}:{KO}:	{-}{1}:	{+}{0}");
        try {
        Collection result = instance.getMouseList();
        
        System.out.print(result.size());
        
        assertEquals(xExpResult, result);
                } catch (Exception e) {}

    }
}