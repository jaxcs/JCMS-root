/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jax.cs.apps.jcms.subsystems.jptConfig.JPTProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daves
 */
public class JPTPropertiesTest {

    public JPTPropertiesTest() {
    }

        JPTProperties myConf;
        
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        myConf = new JPTProperties();
//        myConf.loadProperties("JPTTest.conf");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setProperty method, of class JPTProperties.
     */
    @Test
    public void testSetProperty() {
        System.out.println("setProperty");
        String propKey = "xxx";
        String propValue = "zzz";
        String expResult = "zzz";
        String result;
        
        
            /** 
     * propKey = MySQLHost
     * propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = MySQLPort
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = MySQLUserName
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = MySQLPassword
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = MsAccessDSN
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = ApplicationTreeDepth
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = ReprotTreeDepth
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = ReportType
             *  propValue = 
        myConf.setProperty(propKey, propValue);
     * propKey = DataBaseType
             *  propValue = 
     * 
     */

//       JPTProperties instance = new JPTProperties();
        myConf.setProperty(propKey, propValue);
        result = myConf.getProperty(propKey);
        myConf.saveProperties();
        assertEquals(expResult, result);

    }

    /**
     * Test of getProperty method, of class JPTProperties.
     */
    @Test
    public void testGetProperty() {
        System.out.println("getProperty");
        String propKey = "xxx";
        String expResult = "zzz";
        String result = myConf.getProperty(propKey);
        assertEquals(expResult, result);
    }
    
    
    /** 
     * MySQLHost
     * MySQLPort
     * MySQLUserName
     * MySQLPassword
     * MsAccessDSN
     * ApplicationTreeDepth
     * ReprotTreeDepth
     * ReportType
     * DataBaseType
     * 
     */

}