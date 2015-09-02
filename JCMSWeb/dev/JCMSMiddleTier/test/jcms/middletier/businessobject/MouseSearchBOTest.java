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

package jcms.middletier.businessobject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.dto.MouseQueryDTO;
import org.jax.cs.common.TestHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author springer
 */
public class MouseSearchBOTest {
    private String xmlPropertyList;
    private SimpleDateFormat zDateformatter;

    public MouseSearchBOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSelectMouseID2xml() throws Exception {
        System.out.println("testSelectMouseID2xml");

        MouseQueryDTO mq = new MouseQueryDTO();
        mq.setSelectMouseID(true);

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSelectMouseID2dto() throws Exception {
        System.out.println("testSelectMouseID2dto");


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String expResult = "";

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);

        // System.out.println(mq.isSelectMouseID());
        // TestHelper.printTestStrings(Boolean.toString(true), Boolean.toString(mq.isSelectMouseID()));
        assertEquals(true, mq.isSelectMouseID());
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testSelectAll2xml() {
        System.out.println("testSelectAll2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);
        mq.setAgeFilter("Days");
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseGeneration(true);
        mq.setSelectMouseOwner(true);
        mq.setSelectMouseCOD(true);
        mq.setSelectMouseLitter(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMousePenName(true);
        mq.setSelectMouseProtocolID(true);
        mq.setSelectMouseUse(true);
        mq.setSelectMouseUse(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseGenotypeDate(true);
        mq.setSelectMouseMating(true);
        mq.setSelectMouseLitterMates(true);
        mq.setSelectMouseParents(true);
        mq.setSelectMouseCoatColor(true);
        mq.setSelectMouseDiet(true);
        // Set filters

        mq.setMouseFilter("Equals");
        // To
        MouseEntity fromMouse = new MouseEntity();
                fromMouse.setId("c57j-0001");
                fromMouse.setMouseKey(1);
        mq.setMouseIDFrom(fromMouse);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFromEntity\">\n        <PropertyValue>c57j-0001</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testSelectAll2dto() throws ParseException {
        System.out.println("testSelectAll2xml");

        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFromEntity\">\n        <PropertyValue>c57j-0001</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = Days\nmq.isSelectMouseDOB() = true\nmq.isSelectMouseExitDate() = true\nmq.isSelectMouseBreedingStatus() = true\nmq.isSelectMouseOrigin() = true\nmq.isSelectMouseLifeStatus() = true\nmq.isSelectMouseStrain()= true\nmq.isSelectMouseGeneration()= true\nmq.isSelectMouseOwner()= true\nmq.isSelectMouseCOD()= true\nmq.isSelectMouseLitter()= true\nmq.isSelectMousePenID()= true\nmq.isSelectMousePenName()= true\nmq.isSelectMouseProtocolID()= true\nmq.isSelectMouseUse()= true\nmq.isSelectMouseUse()= true\nmq.isSelectMouseGenotype()= true\nmq.isSelectMouseGenotypeDate()= true\nmq.isSelectMouseMating()= true\nmq.isSelectMouseLitterMates()= false\nmq.isSelectMouseParents()= true\nmq.isSelectMouseCoatColor()= true\nmq.getMouseFilter() = Equals\nmq.getMouseID() = \nmq.getMouseIDFrom() = c57j-0001";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" +
            "mq.isSelectMouseExitDate() = " + mq.isSelectMouseExitDate() + "\n" +
            "mq.isSelectMouseBreedingStatus() = " + mq.isSelectMouseBreedingStatus() + "\n" +
            "mq.isSelectMouseOrigin() = "  + mq.isSelectMouseOrigin() + "\n" +
            "mq.isSelectMouseLifeStatus() = " + mq.isSelectMouseLifeStatus() + "\n" +
            "mq.isSelectMouseStrain()= " + mq.isSelectMouseStrain() + "\n" +
            "mq.isSelectMouseGeneration()= " + mq.isSelectMouseGeneration() + "\n" +
            "mq.isSelectMouseOwner()= " + mq.isSelectMouseOwner() + "\n" +
            "mq.isSelectMouseCOD()= " + mq.isSelectMouseCOD() + "\n" +
            "mq.isSelectMouseLitter()= " + mq.isSelectMouseLitter() + "\n" +
            "mq.isSelectMousePenID()= " + mq.isSelectMousePenID() + "\n" +
            "mq.isSelectMousePenName()= " + mq.isSelectMousePenName() + "\n" +
            "mq.isSelectMouseProtocolID()= " + mq.isSelectMouseProtocolID() + "\n" +
            "mq.isSelectMouseUse()= " + mq.isSelectMouseUse() + "\n" +
            "mq.isSelectMouseUse()= " + mq.isSelectMouseUse() + "\n" +
            "mq.isSelectMouseGenotype()= " + mq.isSelectMouseGenotype() + "\n" +
            "mq.isSelectMouseGenotypeDate()= " + mq.isSelectMouseGenotypeDate() + "\n" +
            "mq.isSelectMouseMating()= " + mq.isSelectMouseMating() + "\n" +
            "mq.isSelectMouseLitterMates()= " + mq.isSelectMouseLitterMates() + "\n" +
            "mq.isSelectMouseParents()= " + mq.isSelectMouseParents() + "\n" +
            "mq.isSelectMouseCoatColor()= " + mq.isSelectMouseCoatColor() + "\n" +
            "mq.getMouseFilter() = " + mq.getMouseFilter() + "\n" +
            "mq.getMouseID() = " + mq.getMouseID() + "\n" +
            "mq.getMouseIDFrom() = " + mq.getMouseIDFrom().getId() ;

        TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDateStart2now2xml() throws ParseException {
        System.out.println("testGenotypeDateStart2now2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        mq.setGenotypeStartDate(zDateformatter.parse("2010-06-22"));

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenotypeStartDateEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDateStart2now2dto() throws ParseException {
        System.out.println("testGenotypeDateStart2now2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenotypeStartDateEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.GenotypeStartDate() = 2010-06-22\nmq.GenotypeEndDate() = null\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        if ( null == mq.getGenotypeStartDate() ) {
            result = result + "mq.GenotypeStartDate() = " + mq.getGenotypeStartDate() + "\n" ;
        } else {
            result = result + "mq.GenotypeStartDate() = " + zDateformatter.format(mq.getGenotypeStartDate()) + "\n" ;
        }

        if ( null == mq.getGenotypeEndDate() ) {
        result = result + "mq.GenotypeEndDate() = " + mq.getGenotypeEndDate() + "\n" ;
        } else {
           result = result + "mq.GenotypeEndDate() = " + zDateformatter.format(mq.getGenotypeEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDateEnd2now2xml() throws ParseException {
        System.out.println("testGenotypeDateEnd2now2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        mq.setGenotypeEndDate(zDateformatter.parse("2010-06-22"));

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenotypeEndDateEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDateEnd2now2dto() throws ParseException {
        System.out.println("testGenotypeDateEnd2now2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenotypeEndDateEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = Equals\nmq.isSelectMouseDOB() = false\nmq.GenotypeStartDate() = null\nmq.GenotypeEndDate() = 2010-06-22\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        if ( null == mq.getGenotypeStartDate() ) {
            result = result + "mq.GenotypeStartDate() = " + mq.getGenotypeStartDate() + "\n" ;
        } else {
            result = result + "mq.GenotypeStartDate() = " + zDateformatter.format(mq.getGenotypeStartDate()) + "\n" ;
        }

        if ( null == mq.getGenotypeEndDate() ) {
        result = result + "mq.GenotypeEndDate() = " + mq.getGenotypeEndDate() + "\n" ;
        } else {
           result = result + "mq.GenotypeEndDate() = " + zDateformatter.format(mq.getGenotypeEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotype2xml() throws ParseException {
        System.out.println("testGenotype2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        GeneEntity ge = new GeneEntity();
        ge.setGeneSymbol("Levi");
        ge.setGeneKey(1);
        
        List<GeneEntity> geList = new ArrayList<GeneEntity>();
        geList.add(ge);
        mq.setGenotype(geList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotype2dto() throws ParseException {
        System.out.println("testGenotype2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.Genotype() = Levi\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<GeneEntity> geList = new ArrayList<GeneEntity>();
        geList = mq.getGenotype();

        if ( null == mq.getGenotype() ) {
            result = result + "mq.Genotype() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < geList.size()){
                result = result + "mq.Genotype() = " + geList.get(counter).getGeneSymbol() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Genotype2xml() throws ParseException {
        System.out.println("test2Genotype2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<GeneEntity> geList = new ArrayList<GeneEntity>();

        // Set filters
        GeneEntity ge = new GeneEntity();
        ge.setGeneSymbol("Levi");
        ge.setGeneKey(1);
        geList.add(ge);

        // Set filters
        GeneEntity ge1 = new GeneEntity();
        ge1.setGeneSymbol("Lee");
        ge1.setGeneKey(2);
        geList.add(ge1);

        // Set filters
        GeneEntity ge2 = new GeneEntity();
        ge2.setGeneSymbol("Wrangler");
        ge2.setGeneKey(3);
        geList.add(ge2);

        mq.setGenotype(geList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Genotype2dto() throws ParseException {
        System.out.println("test2GenotypeEnd2now2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GeneEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.Genotype() = Levi\nmq.Genotype() = Lee\nmq.Genotype() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<GeneEntity> geList = new ArrayList<GeneEntity>();
        geList = mq.getGenotype();

        if ( null == mq.getGenotype() ) {
            result = result + "mq.Genotype() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < geList.size()){
                result = result + "mq.Genotype() = " + geList.get(counter).getGeneSymbol() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseUse2xml() throws ParseException {
        System.out.println("testMouseUse2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        CvMouseUseEntity e = new CvMouseUseEntity();
        e.setMouseUse("WheelPower");
        e.setMouseUsekey(1);

        List<CvMouseUseEntity> eList = new ArrayList<CvMouseUseEntity>();
        eList.add(e);
        mq.setMouseUse(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseUse2dto() throws ParseException {
        System.out.println("testMouseUse2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getMouseUse() = WheelPower\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseUseEntity> eList = new ArrayList<CvMouseUseEntity>();
        eList = mq.getMouseUse();

        if ( null == mq.getGenotype() ) {
            result = result + "mq.getMouseUse() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getMouseUse() = " + eList.get(counter).getMouseUse() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2MouseUse2xml() throws ParseException {
        System.out.println("test2MouseUse2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvMouseUseEntity> eList = new ArrayList<CvMouseUseEntity>();

        // Set filters
        CvMouseUseEntity e = new CvMouseUseEntity();
        e.setMouseUse("Levi");
        e.setMouseUsekey(1);
        eList.add(e);

        // Set filters
        CvMouseUseEntity e1 = new CvMouseUseEntity();
        e1.setMouseUse("Lee");
        e1.setMouseUsekey(2);
        eList.add(e1);

        // Set filters
        CvMouseUseEntity e2 = new CvMouseUseEntity();
        e2.setMouseUse("Wrangler");
        e2.setMouseUsekey(3);
        eList.add(e2);

        mq.setMouseUse(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2MouseUse2dto() throws ParseException {
        System.out.println("test2MouseUse2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseUseEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getMouseUse() = Levi\nmq.getMouseUse() = Lee\nmq.getMouseUse() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseUseEntity> eList = new ArrayList<CvMouseUseEntity>();
        eList = mq.getMouseUse();

        if ( null == mq.getMouseUse() ) {
            result = result + "mq.getMouseUse() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getMouseUse() = " + eList.get(counter).getMouseUse() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testProtocolID2xml() throws ParseException {
        System.out.println("testProtocolID2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        CvMouseProtocolEntity e = new CvMouseProtocolEntity();
        e.setId("WheelPower");
        e.setMouseProtocolkey(1);

        List<CvMouseProtocolEntity> eList = new ArrayList<CvMouseProtocolEntity>();
        eList.add(e);
        mq.setProtocolID(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testProtocolID2dto() throws ParseException {
        System.out.println("testProtocolID2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getProtocolID() = WheelPower\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseProtocolEntity> eList = new ArrayList<CvMouseProtocolEntity>();
        eList = mq.getProtocolID();

        if ( null == mq.getProtocolID() ) {
            result = result + "mq.getProtocolID() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getProtocolID() = " + eList.get(counter).getId() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2ProtocolID2xml() throws ParseException {
        System.out.println("test2ProtocolID2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvMouseProtocolEntity> eList = new ArrayList<CvMouseProtocolEntity>();

        // Set filters
        CvMouseProtocolEntity e = new CvMouseProtocolEntity();
        e.setId("Levi");
        e.setMouseProtocolkey(1);
        eList.add(e);

        // Set filters
        CvMouseProtocolEntity e1 = new CvMouseProtocolEntity();
        e1.setId("Lee");
        e1.setMouseProtocolkey(2);
        eList.add(e1);

        // Set filters
        CvMouseProtocolEntity e2 = new CvMouseProtocolEntity();
        e2.setId("Wrangler");
        eList.add(e2);
        e2.setMouseProtocolkey(3);

        mq.setProtocolID(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2ProtocolID2dto() throws ParseException {
        System.out.println("test2ProtocolID2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ProtocolEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getProtocolID() = Levi\nmq.getProtocolID() = Lee\nmq.getProtocolID() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseProtocolEntity> eList = new ArrayList<CvMouseProtocolEntity>();
        eList = mq.getProtocolID();

        if ( null == mq.getProtocolID() ) {
            result = result + "mq.getProtocolID() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getProtocolID() = " + eList.get(counter).getId() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLitterNum2xml() throws ParseException {
        System.out.println("testLitterNum2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        LitterEntity e = new LitterEntity();
        e.setLitterID("10_WheelPower");
        e.setLitterKey(1);

        List<LitterEntity> eList = new ArrayList<LitterEntity>();
        eList.add(e);
        mq.setLitterNumber(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>10_WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLitterNum2dto() throws ParseException {
        System.out.println("testLitterNum2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>10_WheelPower</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getLitterNumber() = 10_WheelPower\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<LitterEntity> eList = new ArrayList<LitterEntity>();
        eList = mq.getLitterNumber();

        if ( null == mq.getLitterNumber() ) {
            result = result + "mq.getLitterNumber() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getLitterNumber() = " + eList.get(counter).getLitterID() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2LitterNum2xml() throws ParseException {
        System.out.println("test2LitterNum2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<LitterEntity> eList = new ArrayList<LitterEntity>();

        // Set filters
        LitterEntity e = new LitterEntity();
        e.setLitterID("Levi");
        e.setLitterKey(1);
        eList.add(e);

        // Set filters
        LitterEntity e1 = new LitterEntity();
        e1.setLitterID("Lee");
        e1.setLitterKey(2);
        eList.add(e1);

        // Set filters
        LitterEntity e2 = new LitterEntity();
        e2.setLitterID("Wrangler");
        e2.setLitterKey(3);
        eList.add(e2);

        mq.setLitterNumber(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2LitterNum2dto() throws ParseException {
        System.out.println("test2LitterNum2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getLitterNumber() = Levi\nmq.getLitterNumber() = Lee\nmq.getLitterNumber() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<LitterEntity> eList = new ArrayList<LitterEntity>();
        eList = mq.getLitterNumber();

        if ( null == mq.getLitterNumber() ) {
            result = result + "mq.getLitterNumber() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getLitterNumber() = " + eList.get(counter).getLitterID() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testCOD2xml() throws ParseException {
        System.out.println("testCOD2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        CvCauseOfDeathEntity e = new CvCauseOfDeathEntity();
        e.setCod("10_COD");
        e.setCauseOfDeathkey(1);

        List<CvCauseOfDeathEntity> eList = new ArrayList<CvCauseOfDeathEntity>();
        eList.add(e);
        mq.setCauseOfDeath(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>10_COD</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testCOD2dto() throws ParseException {
        System.out.println("testCOD2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>10_COD</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getCauseOfDeath() = 10_COD\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvCauseOfDeathEntity> eList = new ArrayList<CvCauseOfDeathEntity>();
        eList = mq.getCauseOfDeath();

        if ( null == mq.getLitterNumber() ) {
            result = result + "mq.getCauseOfDeath() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getCauseOfDeath() = " + eList.get(counter).getCod() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2COD2xml() throws ParseException {
        System.out.println("test2COD2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvCauseOfDeathEntity> eList = new ArrayList<CvCauseOfDeathEntity>();

        // Set filters
        CvCauseOfDeathEntity e = new CvCauseOfDeathEntity();
        e.setCod("Levi");
        e.setCauseOfDeathkey(1);
        eList.add(e);

        // Set filters
        CvCauseOfDeathEntity e1 = new CvCauseOfDeathEntity();
        e1.setCod("Lee");
        e1.setCauseOfDeathkey(2);
        eList.add(e1);

        // Set filters
        CvCauseOfDeathEntity e2 = new CvCauseOfDeathEntity();
        e2.setCod("Wrangler");
        e2.setCauseOfDeathkey(3);
        eList.add(e2);

        mq.setCauseOfDeath(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2COD2dto() throws ParseException {
        System.out.println("test2COD2now2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"CauseOfDeathEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getCauseOfDeath() = Levi\nmq.getCauseOfDeath() = Lee\nmq.getCauseOfDeath() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvCauseOfDeathEntity> eList = new ArrayList<CvCauseOfDeathEntity>();
        eList = mq.getCauseOfDeath();

        if ( null == mq.getLitterNumber() ) {
            result = result + "mq.getCauseOfDeath() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getCauseOfDeath() = " + eList.get(counter).getCod() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOwner2xml() throws ParseException {
        System.out.println("testOwner2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        OwnerEntity e = new OwnerEntity();
        e.setOwner("10_Owner");
        e.setOwnerKey(1);

        List<OwnerEntity> eList = new ArrayList<OwnerEntity>();
        eList.add(e);
        mq.setOwner(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>10_Owner</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOwner2dto() throws ParseException {
        System.out.println("testOwner2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>10_Owner</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getOwner() = 10_Owner\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<OwnerEntity> eList = new ArrayList<OwnerEntity>();
        eList = mq.getOwner();

        if ( null == mq.getOwner() ) {
            result = result + "mq.getOwner() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getOwner() = " + eList.get(counter).getOwner() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Owner2xml() throws ParseException {
        System.out.println("test2Owner2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<OwnerEntity> eList = new ArrayList<OwnerEntity>();

        // Set filters
        OwnerEntity e = new OwnerEntity();
        e.setOwner("Levi");
        e.setOwnerKey(1);
        eList.add(e);

        // Set filters
        OwnerEntity e1 = new OwnerEntity();
        e1.setOwner("Lee");
        e1.setOwnerKey(2);
        eList.add(e1);

        // Set filters
        OwnerEntity e2 = new OwnerEntity();
        e2.setOwner("Wrangler");
        e2.setOwnerKey(3);
        eList.add(e2);

        mq.setOwner(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Owner2dto() throws ParseException {
        System.out.println("test2Owner2now2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"OwnerEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getOwner() = Levi\nmq.getOwner() = Lee\nmq.getOwner() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<OwnerEntity> eList = new ArrayList<OwnerEntity>();
        eList = mq.getOwner();

        if ( null == mq.getOwner() ) {
            result = result + "mq.getOwner() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getOwner() = " + eList.get(counter).getOwner() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGeneration2xml() throws ParseException {
        System.out.println("testGeneration2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        CvGenerationEntity e = new CvGenerationEntity();
        e.setGeneration("10_Generation");
        e.setGenerationKey(1);

        List<CvGenerationEntity> eList = new ArrayList<CvGenerationEntity>();
        eList.add(e);
        mq.setGeneration(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>10_Generation</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGeneration2dto() throws ParseException {
        System.out.println("testGeneration2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>10_Generation</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getGeneration() = 10_Generation\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvGenerationEntity> eList = new ArrayList<CvGenerationEntity>();
        eList = mq.getGeneration();

        if ( null == mq.getGeneration() ) {
            result = result + "mq.getGeneration() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getGeneration() = " + eList.get(counter).getGeneration() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Generation2xml() throws ParseException {
        System.out.println("test2Generation2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvGenerationEntity> eList = new ArrayList<CvGenerationEntity>();

        // Set filters
        CvGenerationEntity e = new CvGenerationEntity();
        e.setGeneration("Levi");
        e.setGenerationKey(1);
        eList.add(e);

        // Set filters
        CvGenerationEntity e1 = new CvGenerationEntity();
        e1.setGeneration("Lee");
        e1.setGenerationKey(2);
        eList.add(e1);

        // Set filters
        CvGenerationEntity e2 = new CvGenerationEntity();
        e2.setGeneration("Wrangler");
        e2.setGenerationKey(3);
        eList.add(e2);

        mq.setGeneration(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Generation2dto() throws ParseException {
        System.out.println("test2Generation2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"GenerationEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getGeneration() = Levi\nmq.getGeneration() = Lee\nmq.getGeneration() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvGenerationEntity> eList = new ArrayList<CvGenerationEntity>();
        eList = mq.getGeneration();

        if ( null == mq.getGeneration() ) {
            result = result + "mq.getGeneration() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getGeneration() = " + eList.get(counter).getGeneration() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testStrain2xml() throws ParseException {
        System.out.println("testStrain2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        StrainEntity e = new StrainEntity();
        e.setStrainName("10_Strain");
        e.setStrainKey(1);


        List<StrainEntity> eList = new ArrayList<StrainEntity>();
        eList.add(e);
        mq.setStrain(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>10_Strain</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testStrain2dto() throws ParseException {
        System.out.println("testStrain2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>10_Strain</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getStrain() = 10_Strain\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<StrainEntity> eList = new ArrayList<StrainEntity>();
        eList = mq.getStrain();

        if ( null == mq.getStrain() ) {
            result = result + "mq.getStrain() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getStrain() = " + eList.get(counter).getStrainName() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Strain2xml() throws ParseException {
        System.out.println("test2Strain2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<StrainEntity> eList = new ArrayList<StrainEntity>();

        // Set filters
        StrainEntity e = new StrainEntity();
        e.setStrainName("Levi");
        e.setStrainKey(1);
        eList.add(e);

        // Set filters
        StrainEntity e1 = new StrainEntity();
        e1.setStrainName("Lee");
        e1.setStrainKey(2);
        eList.add(e1);

        // Set filters
        StrainEntity e2 = new StrainEntity();
        e2.setStrainName("Wrangler");
        e2.setStrainKey(3);
        eList.add(e2);

        mq.setStrain(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Strain2dto() throws ParseException {
        System.out.println("test2Strain2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"StrainEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getStrain() = Levi\nmq.getStrain() = Lee\nmq.getStrain() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<StrainEntity> eList = new ArrayList<StrainEntity>();
        eList = mq.getStrain();

        if ( null == mq.getStrain() ) {
            result = result + "mq.getStrain() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getStrain() = " + eList.get(counter).getStrainName() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOrigin2xml() throws ParseException {
        System.out.println("testOrigin2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        CvMouseOriginEntity e = new CvMouseOriginEntity();
        e.setMouseOrigin("10_Origin");
        e.setMouseOriginkey(1);

        List<CvMouseOriginEntity> eList = new ArrayList<CvMouseOriginEntity>();
        eList.add(e);
        mq.setOrigin(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>10_Origin</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOrigin2dto() throws ParseException {
        System.out.println("testOrigin2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>10_Origin</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getOrigin() = 10_Origin\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseOriginEntity> eList = new ArrayList<CvMouseOriginEntity>();
        eList = mq.getOrigin();

        if ( null == mq.getOrigin() ) {
            result = result + "mq.getOrigin() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getOrigin() = " + eList.get(counter).getMouseOrigin() + "\n" ;
                counter++;
           }

        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Origin2xml() throws ParseException {
        System.out.println("test2Origin2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvMouseOriginEntity> eList = new ArrayList<CvMouseOriginEntity>();

        // Set filters
        CvMouseOriginEntity e = new CvMouseOriginEntity();
        e.setMouseOrigin("Levi");
        e.setMouseOriginkey(1);
        eList.add(e);

        // Set filters
        CvMouseOriginEntity e1 = new CvMouseOriginEntity();
        e1.setMouseOrigin("Lee");
        e1.setMouseOriginkey(2);
        eList.add(e1);

        // Set filters
        CvMouseOriginEntity e2 = new CvMouseOriginEntity();
        e2.setMouseOrigin("Wrangler");
        e2.setMouseOriginkey(3);
        eList.add(e2);

        mq.setOrigin(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2Origin2dto() throws ParseException {
        System.out.println("test2Origin2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"originEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getOrigin() = Levi\nmq.getOrigin() = Lee\nmq.getOrigin() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvMouseOriginEntity> eList = new ArrayList<CvMouseOriginEntity>();
        eList = mq.getOrigin();

        if ( null == mq.getOrigin() ) {
            result = result + "mq.getOrigin() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getOrigin() = " + eList.get(counter).getMouseOrigin() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLifeStatus2xml() throws ParseException {
        System.out.println("testLifeStatus2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set filters
        LifeStatusEntity e = new LifeStatusEntity();
        e.setLifeStatuskey(1);
        e.setLifeStatus("10_LifeStatus");

        List<LifeStatusEntity> eList = new ArrayList<LifeStatusEntity>();
        eList.add(e);
        mq.setLifeStatus(eList);


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>10_LifeStatus</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }



    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLifeStatus2dto() throws ParseException {
        System.out.println("testLifeStatus2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>10_LifeStatus</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n</Properties>\n";
        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getLifeStatus() = 10_LifeStatus\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<LifeStatusEntity> eList = new ArrayList<LifeStatusEntity>();
        eList = mq.getLifeStatus();

        if ( null == mq.getLifeStatus() ) {
            result = result + "mq.getLifeStatus() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getLifeStatus() = " + eList.get(counter).getLifeStatus() + "\n" ;
                counter++;
           }
        }

       // TestHelper.printTestStrings(expResult,result);
       assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2LifeStatus2xml() throws ParseException {
        System.out.println("test2LifeStatus2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<LifeStatusEntity> eList = new ArrayList<LifeStatusEntity>();

        // Set filters
        LifeStatusEntity e = new LifeStatusEntity();
        e.setLifeStatuskey(1);
        e.setLifeStatus("Levi");
        eList.add(e);

        // Set filters
        LifeStatusEntity e1 = new LifeStatusEntity();
        e1.setLifeStatuskey(2);
        e1.setLifeStatus("Lee");
        eList.add(e1);

        // Set filters
        LifeStatusEntity e2 = new LifeStatusEntity();
        e2.setLifeStatuskey(3);
        e2.setLifeStatus("Wrangler");
        eList.add(e2);

        mq.setLifeStatus(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2LifeStatus2dto() throws ParseException {
        System.out.println("test2LifeStatus2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LifeStatusEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getLifeStatus() = Levi\nmq.getLifeStatus() = Lee\nmq.getLifeStatus() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<LifeStatusEntity> eList = new ArrayList<LifeStatusEntity>();
        eList = mq.getLifeStatus();

        if ( null == mq.getLifeStatus() ) {
            result = result + "mq.getLifeStatus() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getLifeStatus() = " + eList.get(counter).getLifeStatus() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

            
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2BreedingStatus2xml() throws ParseException {
        System.out.println("test2BreedingStatus2xml");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvBreedingStatusEntity> eList = new ArrayList<CvBreedingStatusEntity>();

        // Set filters
        CvBreedingStatusEntity e = new CvBreedingStatusEntity();
        e.setBreedingStatus("Levi");
        e.setBreedingStatuskey(1);
        eList.add(e);

        // Set filters
        CvBreedingStatusEntity e1 = new CvBreedingStatusEntity();
        e1.setBreedingStatus("Lee");
        e1.setBreedingStatuskey(2);
        eList.add(e1);

        // Set filters
        CvBreedingStatusEntity e2 = new CvBreedingStatusEntity();
        e2.setBreedingStatus("Wrangler");
        e2.setBreedingStatuskey(3);
        eList.add(e2);

        mq.setBreedingStatus(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void test2BreedingStatus2dto() throws ParseException {
        System.out.println("test2BreedingStatus2dto");
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"BreedingStatusEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n</Properties>\n";

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        MouseQueryDTO mq = msbo.xml2DTO(xmlString);

        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getBreedingStatus() = Levi\nmq.getBreedingStatus() = Lee\nmq.getBreedingStatus() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvBreedingStatusEntity> eList = new ArrayList<CvBreedingStatusEntity>();
        eList = mq.getBreedingStatus();

        if ( null == mq.getBreedingStatus() ) {
            result = result + "mq.getBreedingStatus() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getBreedingStatus() = " + eList.get(counter).getBreedingStatus() + "\n" ;
                counter++;
           }
        }

        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);
    }


    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testExitDateStart2now_2xml() throws Exception {
        System.out.println("testExitDateStart2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);


        mq.setExitStartDate(zDateformatter.parse("2010-06-22"));


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ExitDateStartEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testExitDateStart2now_2dto() throws Exception {
        System.out.println("testExitDateStart2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ExitDateStartEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");


        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getExitStartDate() = 2010-06-22\nmq.getExitEndDate() = null\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        if ( null == mq.getExitStartDate() ) {
            result = result + "mq.getExitStartDate() = " + mq.getExitStartDate() + "\n" ;
        } else {
           result = result + "mq.getExitStartDate() = " + zDateformatter.format(mq.getExitStartDate()) + "\n" ;
        }

        if ( null == mq.getExitEndDate()) {
        result = result + "mq.getExitEndDate() = " + mq.getExitEndDate() + "\n" ;
        } else {
           result = result + "mq.getExitEndDate() = " + zDateformatter.format(mq.getExitEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testExitDateEnd2now_2xml() throws Exception {
        System.out.println("testExitDateEnd2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);



        mq.setExitEndDate(zDateformatter.parse("2010-06-22"));


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ExitDateEndEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testExitDateEnd2now_2dto() throws Exception {
        System.out.println("testExitDateEnd2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"ExitDateEndEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getExitStartDate() = null\nmq.getExitEndDate() = 2010-06-22\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;


        if ( null == mq.getExitStartDate() ) {
            result = result + "mq.getExitStartDate() = " + mq.getExitStartDate() + "\n" ;
        } else {
           result = result + "mq.getExitStartDate() = " + zDateformatter.format(mq.getExitStartDate()) + "\n" ;
        }

        if ( null == mq.getExitEndDate()) {
            result = result + "mq.getExitEndDate() = " + mq.getExitEndDate() + "\n" ;
        } else {
           result = result + "mq.getExitEndDate() = " + zDateformatter.format(mq.getExitEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testDOBStart2now_2xml() throws Exception {
        System.out.println("testDOBStart2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);


        mq.setDobStartDate(zDateformatter.parse("2010-06-22"));


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStartDOBEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testDOBStart2now_2dto() throws Exception {
        System.out.println("testDOBStart2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStartDOBEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");


        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getDobStartDate() = 2010-06-22\nmq.getDobEndDate() = null\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        if ( null == mq.getDobStartDate() ) {
            result = result + "mq.getDobStartDate() = " + mq.getDobStartDate() + "\n" ;
        } else {
           result = result + "mq.getDobStartDate() = " + zDateformatter.format(mq.getDobStartDate()) + "\n" ;
        }

        if ( null == mq.getDobEndDate()) {
        result = result + "mq.getDobEndDate() = " + mq.getDobEndDate() + "\n" ;
        } else {
           result = result + "mq.getDobEndDate() = " + zDateformatter.format(mq.getDobEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testDOBEnd2now_2xml() throws Exception {
        System.out.println("testDOBEnd2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);



        mq.setDobEndDate(zDateformatter.parse("2010-06-22"));


        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseEndDOBEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testDOBEnd2now_2dto() throws Exception {
        System.out.println("testDOBEnd2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseEndDOBEntity\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getDobStartDate() = null\nmq.getDobEndDate() = 2010-06-22\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;


        if ( null == mq.getDobStartDate() ) {
            result = result + "mq.getDobStartDate() = " + mq.getDobStartDate() + "\n" ;
        } else {
           result = result + "mq.getDobStartDate() = " + zDateformatter.format(mq.getDobStartDate()) + "\n" ;
        }

        if ( null == mq.getDobEndDate()) {
        result = result + "mq.getDobEndDate() = " + mq.getDobEndDate() + "\n" ;
        } else {
           result = result + "mq.getDobEndDate() = " + zDateformatter.format(mq.getDobEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testAge_2xml() throws Exception {
        System.out.println("testAge_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set age
        mq.setAgeFilter("Between");
        mq.setAgeMeasure("Days");
        mq.setAgeFrom(1);
        mq.setAgeFrom(10);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>10</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testAge_2dto() throws Exception {
        System.out.println("testAge_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>10</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = Between\nmq.isSelectMouseDOB() = false\nmq.getAgeFilter() = Between\nmq.getAgeMeasure() = Days\nmq.getAgeFrom() = 10\nmq.getAgeTo() = 0\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.getAgeMeasure() = " + mq.getAgeMeasure() + "\n" +
            "mq.getAgeFrom() = " + mq.getAgeFrom() + "\n" +
            "mq.getAgeTo() = " + mq.getAgeTo() + "\n";

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSex2xml() throws Exception {
        System.out.println("testSex2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        List<CvSexEntity> eList = new ArrayList<CvSexEntity>();

        // Set filters
        CvSexEntity e = new CvSexEntity();
        e.setSex("Levi");
        e.setSexKey(1);
        eList.add(e);

        // Set filters
        CvSexEntity e1 = new CvSexEntity();
        e1.setSex("Lee");
        e1.setSexKey(2);
        eList.add(e1);

        // Set filters
        CvSexEntity e2 = new CvSexEntity();
        e2.setSex("Wrangler");
        e2.setSexKey(3);
        eList.add(e2);

        mq.setSex(eList);



        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSex2dto() throws Exception {
        System.out.println("testSex2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Lee</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"SexEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getSex() = Levi\nmq.getSex() = Lee\nmq.getSex() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" ;

        List<CvSexEntity> eList = new ArrayList<CvSexEntity>();
        eList = mq.getSex();

        if ( null == mq.getSex() ) {
            result = result + "mq.getSex() = null \n" ;
        } else {
           int counter = 0 ;
           while ( counter < eList.size()){
                result = result + "mq.getSex() = " + eList.get(counter).getSex() + "\n" ;
                counter++;
           }
        }


        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMouse2xml() throws Exception {
        System.out.println("testMouse2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);

        // Set age
        mq.setMouseFilter("Between");
        mq.setMouseID("wiskers");
        // Set filters
        MouseEntity e1 = new MouseEntity();
        e1.setId("Levi");
        e1.setMouseKey(1);
        mq.setMouseIDFrom(e1);

        MouseEntity e2 = new MouseEntity();
        e2.setId("Wrangler");
        e2.setMouseKey(2);
        mq.setMouseIDTo(e2);

        MouseSearchBO msbo = new MouseSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFromEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseIDToEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue>wiskers</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMouse2dto() throws Exception {
        System.out.println("testMouse2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MouseIDSelect\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLifeStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBreedingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseOrigin\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseSex\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCOD\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseBirthDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseExitDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseLitterNum\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MousePenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseUse\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseGenotypeDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseAge\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseMatings\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseParents\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseCoatColor\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseDiet\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseProtocolID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFromEntity\">\n        <PropertyValue>Levi</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseIDToEntity\">\n        <PropertyValue>Wrangler</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MouseID\">\n        <PropertyValue>wiskers</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MouseIDFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFromEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeToEntity\">\n        <PropertyValue>0</PropertyValue>\n    </Property>\n    <Property PropertyName=\"AgeFilter\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"AgeMeasure\">\n        <PropertyValue>Days</PropertyValue>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        MouseSearchBO msbo = new MouseSearchBO("Testing");

        MouseQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectMouseID() = true\nmq.isSelectMouseSex() = true\nmq.isSelectMouseAge() = true\nmq.getAgeFilter() = \nmq.isSelectMouseDOB() = false\nmq.getMouseFilter() = Between\nmq.getMouseID() = wiskers\nmq.getMouseIDFrom() = Levi\nmq.getMouseIDTo() = Wrangler\n";
        String result = "" +
            "mq.isSelectMouseID() = " + mq.isSelectMouseID() + "\n" +
            "mq.isSelectMouseSex() = " +   mq.isSelectMouseSex() + "\n" +
            "mq.isSelectMouseAge() = " +  mq.isSelectMouseAge() + "\n" +
            "mq.getAgeFilter() = " + mq.getAgeFilter() + "\n" +
            "mq.isSelectMouseDOB() = "  + mq.isSelectMouseDOB() + "\n" +
            "mq.getMouseFilter() = " + mq.getMouseFilter() + "\n" +
            "mq.getMouseID() = " + mq.getMouseID() + "\n" ;

        MouseEntity e = mq.getMouseIDFrom();
        if ( null != e ) {
            result = result + "mq.getMouseIDFrom() = " + e.getId() + "\n" ;
        } else {
            result = result + "mq.getMouseIDFrom() = null \n" ;
        }

        MouseEntity e2 = mq.getMouseIDTo();

        if ( null != e2 ) {
            result = result + "mq.getMouseIDTo() = " + e2.getId() + "\n" ;
        } else {
            result = result + "mq.getMouseIDTo() = null \n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


}