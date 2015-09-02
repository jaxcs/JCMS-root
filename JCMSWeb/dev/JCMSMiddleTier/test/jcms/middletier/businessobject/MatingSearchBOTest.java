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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.dto.MatingQueryDTO;
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
public class MatingSearchBOTest {
    private String xmlPropertyList;

    private DateFormat zDateformatter = null;


    public MatingSearchBOTest() {
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

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingID2xml() throws Exception {
        System.out.println("testMatingID2xml");

        MatingQueryDTO mq = new MatingQueryDTO();
        mq.setSelectMatingID(true);

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingID2dto() throws Exception {
        System.out.println("testMatingID2dto");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(expResult);

        // TestHelper.printTestStrings( Boolean.toString(true), Boolean.toString(mq.isSelectMatingID()));
        assertEquals(true, mq.isSelectMatingID());
    }
    
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSelectAll2xml() throws Exception {
        System.out.println("testSelectAll2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectDam1DOB(true);
        mq.setSelectDam1Gen(true);
        mq.setSelectDam1Genotype(true);
        mq.setSelectDam1ID(true);
        mq.setSelectDam1PlugDate(true);
        mq.setSelectDam1Stock(true);
        mq.setSelectDam1Strain(true);

        mq.setSelectDam2DOB(true);
        mq.setSelectDam2Gen(true);
        mq.setSelectDam2Genotype(true);
        mq.setSelectDam2ID(true);
        mq.setSelectDam2PlugDate(true);
        mq.setSelectDam2Stock(true);
        mq.setSelectDam2Strain(true);

        mq.setSelectDateRetired(true);
        mq.setSelectMatingDates(true);
        mq.setSelectMatingGeneration(true);
        mq.setSelectMatingID(true);

        mq.setSelectMatingOwner(true);
        mq.setSelectMatingPenId(true);
        mq.setSelectMatingPenName(true);
        mq.setSelectMatingStatus(true);
        mq.setSelectMatingStrain(true);

        mq.setSelectNeedsTyping(true);

        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);

        mq.setSelectTotalFemales(true);
        mq.setSelectTotalLitters(true);
        mq.setSelectTotalLittersDead(true);
        mq.setSelectTotalMales(true);
        mq.setSelectTotalPups(true);

        mq.setSelectWeanTime(true);
        
        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSelectAll2dto() throws Exception {
        System.out.println("testSelectAll2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectDam1DOB() = true\nmq.isSelectDam1Gen() = true\nmq.isSelectDam1Genotype() = true\nmq.isSelectDam1ID() = true\nmq.isSelectDam1PlugDate() = true\nmq.isSelectDam1Stock() = true\nmq.isSelectDam1Strain() = true\nmq.isSelectDam2DOB() = true\nmq.isSelectDam2Gen() = true\nmq.isSelectDam2Genotype() = true\nmq.isSelectDam2ID() = true\nmq.isSelectDam2PlugDate() = true\nmq.isSelectDam2Stock() = true\nmq.isSelectDam2Strain() = true\nmq.isSelectDateRetired() = true\nmq.isSelectMatingDates() = true\nmq.isSelectMatingGeneration() = true\nmq.isSelectMatingID() = true\nmq.isSelectMatingOwner() = true\nmq.isSelectMatingPenId() = true\nmq.isSelectMatingPenName() = true\nmq.isSelectMatingStatus() = true\nmq.isSelectMatingStrain() = true\nmq.isSelectNeedsTyping() = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nmq.isSelectTotalFemales() = true\nmq.isSelectTotalLitters() = true\nmq.isSelectTotalLittersDead() = true\nmq.isSelectTotalMales() = true\nmq.isSelectTotalPups() = true\nmq.isSelectWeanTime() = true";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectDam1DOB() = " + mq.isSelectDam1DOB() + "\n" +
                "mq.isSelectDam1Gen() = " + mq.isSelectDam1Gen() + "\n" + 
                "mq.isSelectDam1Genotype() = "  + mq.isSelectDam1Genotype() + "\n" +
                "mq.isSelectDam1ID() = " + mq.isSelectDam1Genotype() + "\n" +
                "mq.isSelectDam1PlugDate() = " + mq.isSelectDam1Genotype() + "\n" +
                "mq.isSelectDam1Stock() = " + mq.isSelectDam1Stock() + "\n" +
                "mq.isSelectDam1Strain() = " + mq.isSelectDam1Strain() + "\n" +

                "mq.isSelectDam2DOB() = " + mq.isSelectDam1Strain() + "\n" +
                "mq.isSelectDam2Gen() = " + mq.isSelectDam2Gen()  + "\n" +
                "mq.isSelectDam2Genotype() = " + mq.isSelectDam2Genotype()  + "\n" +
                "mq.isSelectDam2ID() = " + mq.isSelectDam2ID() + "\n" +
                "mq.isSelectDam2PlugDate() = " + mq.isSelectDam2PlugDate() + "\n" +
                "mq.isSelectDam2Stock() = " + mq.isSelectDam2Stock() + "\n" +
                "mq.isSelectDam2Strain() = " + mq.isSelectDam2Strain() + "\n" +

                "mq.isSelectDateRetired() = " +  mq.isSelectDateRetired() + "\n" +
                "mq.isSelectMatingDates() = " + mq.isSelectMatingDates() + "\n" +
                "mq.isSelectMatingGeneration() = " + mq.isSelectMatingGeneration() + "\n" +
                "mq.isSelectMatingID() = " +  mq.isSelectMatingID() + "\n" +

                "mq.isSelectMatingOwner() = " +  mq.isSelectMatingOwner() + "\n" +
                "mq.isSelectMatingPenId() = " + mq.isSelectMatingPenId() + "\n" +
                "mq.isSelectMatingPenName() = " +  mq.isSelectMatingPenName() + "\n" +
                "mq.isSelectMatingStatus() = " +  mq.isSelectMatingStatus() + "\n" +
                "mq.isSelectMatingStrain() = " + mq.isSelectMatingStrain() + "\n" +

                "mq.isSelectNeedsTyping() = " + mq.isSelectNeedsTyping() + "\n" +

                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " +  mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = " + mq.isSelectSireGenotype() + "\n" + 
                "mq.isSelectSireID() = " + mq.isSelectSireID()  + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n" +

                "mq.isSelectTotalFemales() = " + mq.isSelectTotalFemales() + "\n" +
                "mq.isSelectTotalLitters() = " + mq.isSelectTotalLitters() + "\n" +
                "mq.isSelectTotalLittersDead() = " + mq.isSelectTotalLittersDead() + "\n" +
                "mq.isSelectTotalMales() = " + mq.isSelectTotalMales() + "\n" +
                "mq.isSelectTotalPups() = " + mq.isSelectTotalPups() + "\n" +

                "mq.isSelectWeanTime() = " + mq.isSelectWeanTime();

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

 
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPenID2xml() throws Exception {
        System.out.println("testPenID2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectDam1DOB(true);
        mq.setSelectDam1Gen(true);
        mq.setSelectDam1Genotype(true);
        mq.setSelectDam1ID(true);
        mq.setSelectDam1PlugDate(true);
        mq.setSelectDam1Stock(true);
        mq.setSelectDam1Strain(true);


        // Create List
        List<ContainerEntity> containerIDEntityList = new ArrayList<ContainerEntity>();

        // Create Entity
        ContainerEntity ce1 = new ContainerEntity();
        // Update Entity
        ce1.setContainerID(3);
        ce1.setContainerKey(3);
        // Add Entity to List
        containerIDEntityList.add(ce1);

        // Create Entity
        ContainerEntity ce2 = new ContainerEntity();
        // Update Entity
        ce2.setContainerID(23);
        ce2.setContainerKey(23);
        // Add Entity to List
        containerIDEntityList.add(ce2);

                // Create Entity
        ContainerEntity ce3 = new ContainerEntity();
        // Update Entity
        ce3.setContainerID(33);
        ce3.setContainerKey(33);
        // Add Entity to List
        containerIDEntityList.add(ce3);

        // Create Entity
        ContainerEntity ce4 = new ContainerEntity();
        // Update Entity
        ce4.setContainerID(66);
        ce4.setContainerKey(66);
        // Add Entity to List
        containerIDEntityList.add(ce4);

        // Create Entity
        ContainerEntity ce5 = new ContainerEntity();
        // Update Entity
        ce5.setContainerID(99);
        ce5.setContainerKey(99);
        // Add Entity to List
        containerIDEntityList.add(ce5);

        // Add to DTO
        mq.setPenNumber(containerIDEntityList);


        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>3</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>23</PropertyValue>\n        <PropertyKey>23</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>33</PropertyValue>\n        <PropertyKey>33</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>66</PropertyValue>\n        <PropertyKey>66</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>99</PropertyValue>\n        <PropertyKey>99</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPenID2dto() throws Exception {
        System.out.println("testPenID2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>3</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>23</PropertyValue>\n        <PropertyKey>23</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>33</PropertyValue>\n        <PropertyKey>33</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>66</PropertyValue>\n        <PropertyKey>66</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue>99</PropertyValue>\n        <PropertyKey>99</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectDam1DOB() = true\nmq.isSelectDam1Gen() = true\nmq.isSelectDam1Genotype() = true\nmq.isSelectDam1ID() = true\nmq.isSelectDam1PlugDate() = true\nmq.isSelectDam1Stock() = true\nmq.isSelectDam1Strain() = true\nContainerID = 33\nContainerID = 2323\nContainerID = 3333\nContainerID = 6666\nContainerID = 9999\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectDam1DOB() = " + mq.isSelectDam1Strain() + "\n" +
                "mq.isSelectDam1Gen() = " + mq.isSelectDam1Gen()  + "\n" +
                "mq.isSelectDam1Genotype() = " + mq.isSelectDam1Genotype()  + "\n" +
                "mq.isSelectDam1ID() = " + mq.isSelectDam1ID() + "\n" +
                "mq.isSelectDam1PlugDate() = " + mq.isSelectDam1PlugDate() + "\n" +
                "mq.isSelectDam1Stock() = " + mq.isSelectDam1Stock() + "\n" +
                "mq.isSelectDam1Strain() = " + mq.isSelectDam1Strain() + "\n"
                ;


        List<ContainerEntity> containerIDEntityList = new ArrayList<ContainerEntity>();
        containerIDEntityList = mq.getPenName();
        int counter = 0 ;
        while ( counter < containerIDEntityList.size()){
            result = result + "ContainerID = " +
                    Integer.toString(containerIDEntityList.get(counter).getContainerID()) +
                    Integer.toString(containerIDEntityList.get(counter).getContainerKey()) +
                    "\n";
            counter++;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testOwner2xml() throws Exception {
        System.out.println("testOwner2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectDam2DOB(true);
        mq.setSelectDam2Gen(true);
        mq.setSelectDam2Genotype(true);
        mq.setSelectDam2ID(true);
        mq.setSelectDam2PlugDate(true);
        mq.setSelectDam2Stock(true);
        mq.setSelectDam2Strain(true);

        // Create List
        List<OwnerEntity> ownerEntityList = new ArrayList<OwnerEntity>();

        // Create Entity
        OwnerEntity ce1 = new OwnerEntity();
        // Update Entity
        ce1.setOwner("Ernhart");
        ce1.setOwnerKey(1);
        // Add Entity to List
        ownerEntityList.add(ce1);

        // Create Entity
        OwnerEntity ce2 = new OwnerEntity();
        // Update Entity
        ce2.setOwner("Jordan");
        ce2.setOwnerKey(2);
        // Add Entity to List
        ownerEntityList.add(ce2);

                // Create Entity
        OwnerEntity ce3 = new OwnerEntity();
        // Update Entity
        ce3.setOwner("Bird");
        ce3.setOwnerKey(3);
        // Add Entity to List
        ownerEntityList.add(ce3);

        // Create Entity
        OwnerEntity ce4 = new OwnerEntity();
        // Update Entity
        ce4.setOwner("Lemieux");
        ce4.setOwnerKey(4);
        // Add Entity to List
        ownerEntityList.add(ce4);

        // Create Entity
        OwnerEntity ce5 = new OwnerEntity();
        // Update Entity
        ce5.setOwner("Gretzky");
        ce5.setOwnerKey(5);

        // Add Entity to List
        ownerEntityList.add(ce5);

        // Add to DTO
        mq.setMatingOwner(ownerEntityList);


        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testOwner2dto() throws Exception {
        System.out.println("testOwner2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectDam2DOB() = true\nmq.isSelectDam2Gen() = true\nmq.isSelectDam2Genotype() = true\nmq.isSelectDam2ID() = true\nmq.isSelectDam2PlugDate() = true\nmq.isSelectDam2Stock() = true\nmq.isSelectDam21Strain() = true\nOwner = Ernhart1\nOwner = Jordan2\nOwner = Bird3\nOwner = Lemieux4\nOwner = Gretzky5\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectDam2DOB() = " + mq.isSelectDam2DOB() + "\n" +
                "mq.isSelectDam2Gen() = " + mq.isSelectDam2Gen() + "\n" +
                "mq.isSelectDam2Genotype() = "  + mq.isSelectDam2Genotype() + "\n" +
                "mq.isSelectDam2ID() = " + mq.isSelectDam2Genotype() + "\n" +
                "mq.isSelectDam2PlugDate() = " + mq.isSelectDam2Genotype() + "\n" +
                "mq.isSelectDam2Stock() = " + mq.isSelectDam2Stock() + "\n" +
                "mq.isSelectDam21Strain() = " + mq.isSelectDam2Strain() + "\n";

        List<OwnerEntity> ownerEntityList = new ArrayList<OwnerEntity>();
        ownerEntityList = mq.getMatingOwner();
        int counter = 0 ;
        while ( counter < ownerEntityList.size()){
            result = result + "Owner = " +
                    ownerEntityList.get(counter).getOwner().trim() +
                    Integer.toString(ownerEntityList.get(counter).getOwnerKey()) +
                    "\n";
            counter++;
        }



        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testLitterGeneration2xml() throws Exception {
        System.out.println("testLitterGeneration2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);

        // Create List
        List<CvGenerationEntity> generationEntityList = new ArrayList<CvGenerationEntity>();

        // Create Entity
        CvGenerationEntity ce1 = new CvGenerationEntity();
        // Update Entity
        ce1.setGeneration("Ernhart");
        ce1.setGenerationKey(1);
        // Add Entity to List
        generationEntityList.add(ce1);

        // Create Entity
        CvGenerationEntity ce2 = new CvGenerationEntity();
        // Update Entity
        ce2.setGeneration("Jordan");
        ce2.setGenerationKey(2);
        // Add Entity to List
        generationEntityList.add(ce2);

                // Create Entity
        CvGenerationEntity ce3 = new CvGenerationEntity();
        // Update Entity
        ce3.setGeneration("Bird");
        ce3.setGenerationKey(3);
        // Add Entity to List
        generationEntityList.add(ce3);

        // Create Entity
        CvGenerationEntity ce4 = new CvGenerationEntity();
        // Update Entity
        ce4.setGeneration("Lemieux");
        ce4.setGenerationKey(4);
        // Add Entity to List
        generationEntityList.add(ce4);

        // Create Entity
        CvGenerationEntity ce5 = new CvGenerationEntity();
        // Update Entity
        ce5.setGeneration("Gretzky");
        ce5.setGenerationKey(5);
        // Add Entity to List
        generationEntityList.add(ce5);

        // Add to DTO
        mq.setLitterGeneration(generationEntityList);


        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testLitterGeneration2dto() throws Exception {
        System.out.println("testLitterGeneration2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nGeneration = Ernhart1\nGeneration = Jordan2\nGeneration = Bird3\nGeneration = Lemieux4\nGeneration = Gretzky5\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n";

       
                
        List<CvGenerationEntity> generationEntityList = new ArrayList<CvGenerationEntity>();
        generationEntityList = mq.getLitterGeneration();

        int counter = 0 ;
        while ( counter < generationEntityList.size()){
            result = result + "Generation = " +
                    generationEntityList.get(counter).getGeneration().trim() +
                    Integer.toString(generationEntityList.get(counter).getGenerationKey()) +
                    "\n";
            counter++;
        }



        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }



    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testLitterStrain2xml() throws Exception {
        System.out.println("testLitterStrain2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);

        // Create List
        List<StrainEntity> strainEntityList = new ArrayList<StrainEntity>();

        // Create Entity
        StrainEntity ce1 = new StrainEntity();
        // Update Entity
        ce1.setStrainName("Ernhart");
        ce1.setStrainKey(1);
        // Add Entity to List
        strainEntityList.add(ce1);

        // Create Entity
        StrainEntity ce2 = new StrainEntity();
        // Update Entity
        ce2.setStrainName("Jordan");
        ce2.setStrainKey(2);
        // Add Entity to List
        strainEntityList.add(ce2);

                // Create Entity
        StrainEntity ce3 = new StrainEntity();
        // Update Entity
        ce3.setStrainName("Bird");
        ce3.setStrainKey(3);
        // Add Entity to List
        strainEntityList.add(ce3);

        // Create Entity
        StrainEntity ce4 = new StrainEntity();
        // Update Entity
        ce4.setStrainName("Lemieux");
        ce4.setStrainKey(4);
        // Add Entity to List
        strainEntityList.add(ce4);

        // Create Entity
        StrainEntity ce5 = new StrainEntity();
        // Update Entity
        ce5.setStrainName("Gretzky");
        ce5.setStrainKey(5);
        // Add Entity to List
        strainEntityList.add(ce5);

        // Add to DTO
        mq.setLitterStrain(strainEntityList);


        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testLitterStrain2dto() throws Exception {
        System.out.println("testLitterStrain2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Ernhart</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Jordan</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Bird</PropertyValue>\n        <PropertyKey>3</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Lemieux</PropertyValue>\n        <PropertyKey>4</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue>Gretzky</PropertyValue>\n        <PropertyKey>5</PropertyKey>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";

        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nGeneration = Ernhart1\nGeneration = Jordan2\nGeneration = Bird3\nGeneration = Lemieux4\nGeneration = Gretzky5\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n";



        List<StrainEntity> strainEntityList = new ArrayList<StrainEntity>();
        strainEntityList = mq.getLitterStrain();

        int counter = 0 ;
        while ( counter < strainEntityList.size()){
            result = result + "Generation = " +
                    strainEntityList.get(counter).getStrainName().trim() +
                    Integer.toString(strainEntityList.get(counter).getStrainKey()) +
                    "\n";
            counter++;
        }



        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingDateStart2now_2xml() throws Exception {
        System.out.println("testMatingDateStart2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);


        mq.setMatingStartDate(zDateformatter.parse("2010-06-22"));

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingDateStart2now_2dto() throws Exception {
        System.out.println("testMatingDateStart2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nmq.getMatingStartDate() = 2010-06-22\nmq.getMatingEndDate() = null\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n";

        if ( null == mq.getMatingStartDate() ) {
            result = result + "mq.getMatingStartDate() = " + mq.getMatingStartDate() + "\n" ;
        } else {
           result = result + "mq.getMatingStartDate() = " + zDateformatter.format(mq.getMatingStartDate()) + "\n" ;
        }

        if ( null == mq.getMatingEndDate() ) {
        result = result + "mq.getMatingEndDate() = " + mq.getMatingEndDate() + "\n" ;
        } else {
           result = result + "mq.getMatingEndDate() = " + zDateformatter.format(mq.getMatingEndDate()) + "\n" ;        
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingDateEnd2now_2xml() throws Exception {
        System.out.println("testMatingDateEnd2now_2xml");

        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");
        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);

        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);


        mq.setMatingEndDate(zDateformatter.parse("2010-06-22"));


        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingDateEnd2now_2dto() throws Exception {
        System.out.println("testMatingDateEnd2now_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue>2010-06-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nmq.getMatingStartDate() = null\nmq.getMatingEndDate() = 2010-06-22\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n";

        if ( null == mq.getMatingStartDate() ) {
            result = result + "mq.getMatingStartDate() = " + mq.getMatingStartDate() + "\n" ;
        } else {
           result = result + "mq.getMatingStartDate() = " + zDateformatter.format(mq.getMatingStartDate()) + "\n" ;
        }

        if ( null == mq.getMatingEndDate() ) {
        result = result + "mq.getMatingEndDate() = " + mq.getMatingEndDate() + "\n" ;
        } else {
           result = result + "mq.getMatingEndDate() = " + zDateformatter.format(mq.getMatingEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingIDEquals2_2xml() throws Exception {
        System.out.println("testMatingIDEquals2_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);
        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);

        mq.setMatingFilter("Equals");
        MatingEntity me = new MatingEntity();
        me.setMatingID(1);
        me.setMatingKey(1);
        mq.setMatingIDFrom(me);

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue>1</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingIDEquals2dto() throws Exception {
        System.out.println("testMatingIDEquals2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue>1</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nmq.getMatingFilter() = Equals\nmq.getMatingIDFrom() = 1\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n" +
                "mq.getMatingFilter() = " + mq.getMatingFilter() + "\n" +
                "mq.getMatingIDFrom() = " + Integer.toString(mq.getMatingIDFrom().getMatingID()) + "\n" ;

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingIDBetween_2xml() throws Exception {
        System.out.println("testMatingIDBetween_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectBirthDates(true);
        mq.setSelectSireDOB(true);
        mq.setSelectSireGen(true);
        mq.setSelectSireGenotype(true);
        mq.setSelectSireID(true);
        mq.setSelectSireStock(true);
        mq.setSelectSireStrain(true);

        mq.setMatingFilter("Between");
        MatingEntity me = new MatingEntity();
        me.setMatingID(1);
        me.setMatingKey(1);
        mq.setMatingIDFrom(me);

        MatingEntity me1 = new MatingEntity();
        me1.setMatingID(2);
        me1.setMatingKey(2);
        mq.setMatingIDTo(me1);

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue>1</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue>2</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingIDEquals2_2dto() throws Exception {
        System.out.println("testMatingIDEquals2_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Between</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue>1</PropertyValue>\n        <PropertyKey>1</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue>2</PropertyValue>\n        <PropertyKey>2</PropertyKey>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.isSelectBirthDates = true\nmq.isSelectSireDOB() = true\nmq.isSelectSireGen() = true\nmq.isSelectSireGenotype() = true\nmq.isSelectSireID() = true\nmq.isSelectSireStock() = true\nmq.isSelectSireStrain() = true\nmq.getMatingFilter() = Between\nmq.getMatingIDFrom() = 1\nmq.getMatingIDTo() = 2\n";
        String result = "mq.isSelectBirthDates = " + mq.isSelectBirthDates() + "\n" +
                "mq.isSelectSireDOB() = " + mq.isSelectSireDOB() + "\n" +
                "mq.isSelectSireGen() = " + mq.isSelectSireGen() + "\n" +
                "mq.isSelectSireGenotype() = "  + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireID() = " + mq.isSelectSireGenotype() + "\n" +
                "mq.isSelectSireStock() = " + mq.isSelectSireStock() + "\n" +
                "mq.isSelectSireStrain() = " + mq.isSelectSireStrain() + "\n" +
                "mq.getMatingFilter() = " + mq.getMatingFilter() + "\n" +
                "mq.getMatingIDFrom() = " + Integer.toString(mq.getMatingIDFrom().getMatingID()) + "\n" +
                "mq.getMatingIDTo() = " + Integer.toString(mq.getMatingIDTo().getMatingID()) + "\n" ;

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatus_2xml() throws Exception {
        System.out.println("testMatingStatus_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();

        mq.setSelectMatingID(true);

        String [] matingStatusX = new String[4];
        matingStatusX[0] = "One";
        matingStatusX[1] = "Two";
        matingStatusX[2] = "Three";
        matingStatusX[3] = "Four";

        mq.setMatingStatus(matingStatusX);

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>One</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Two</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Three</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Four</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatus_2dto() throws Exception {
        System.out.println("testMatingStatus_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>One</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Two</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Three</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Four</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.setSelectMatingID = false\nmq.matingStatus() = One\nmq.matingStatus() = Two\nmq.matingStatus() = Three\nmq.matingStatus() = Four\n";
        String result = "mq.setSelectMatingID = " + mq.isSelectBirthDates() + "\n" ;

        String [] strMatingStatus = mq.getMatingStatus();

        for ( int i = 0 ; i < strMatingStatus.length; i++){
            result = result + "mq.matingStatus() = "  + strMatingStatus[i] + "\n" ;
        }
        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_A_2xml() throws Exception {
        System.out.println("testMatingStatusDates_A_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        String [] matingStatusX = new String[1];
        matingStatusX[0] = "Retired";
        mq.setMatingStatus(matingStatusX);

        // Set Both Dates
        mq.setRetiredStartDate(zDateformatter.parse("2003-02-22"));
        // mq.setRetiredEndDate(zDateformatter.parse("2010-02-22"));

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue>2003-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_A_2dto() throws Exception {
        System.out.println("testMatingStatusDates_A_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue>2003-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.setSelectMatingID = true\nmq.setSelectMatingStatus = true\nmq.matingStatus() = Retired\nmq.getRetiredStartDate() = Sat Feb 22 00:00:00 MST 2003\nmq.getRetiredEndDate() = null\n";
        String result = "mq.setSelectMatingID = " + mq.isSelectMatingID() + "\n" +
                "mq.setSelectMatingStatus = " + mq.isSelectMatingStatus() + "\n" ;


        String [] strMatingStatus = mq.getMatingStatus();

        for ( int i = 0 ; i < strMatingStatus.length; i++){
            result = result + "mq.matingStatus() = "  + strMatingStatus[i] + "\n" ;
        }


        // RetireDates
        if ( null == mq.getMatingStartDate() ) {
            result = result + "mq.getRetiredStartDate() = " + mq.getRetiredStartDate() + "\n" ;
        } else {
            result = result + "mq.getRetiredStartDate() = " + zDateformatter.format(mq.getRetiredStartDate()) + "\n" ;
        }

        if ( null == mq.getMatingEndDate() ) {
        result = result + "mq.getRetiredEndDate() = " + mq.getRetiredEndDate() + "\n" ;
        } else {
           result = result + "mq.getRetiredEndDate() = " + zDateformatter.format(mq.getRetiredEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_B_2xml() throws Exception {
        System.out.println("testMatingStatusDates_B_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        String [] matingStatusX = new String[1];
        matingStatusX[0] = "Retired";
        mq.setMatingStatus(matingStatusX);

        // Set Both Dates
        mq.setRetiredEndDate(zDateformatter.parse("2010-02-22"));

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue>2010-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_B_2dto() throws Exception {
        System.out.println("testMatingStatusDates_B_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue>2010-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.setSelectMatingID = true\nmq.setSelectMatingStatus = true\nmq.matingStatus() = Retired\nmq.getRetiredStartDate() = null\nmq.getRetiredEndDate() = Mon Feb 22 00:00:00 MST 2010\n";
        String result = "mq.setSelectMatingID = " + mq.isSelectMatingID() + "\n" +
                "mq.setSelectMatingStatus = " + mq.isSelectMatingStatus() + "\n" ;


        String [] strMatingStatus = mq.getMatingStatus();

        for ( int i = 0 ; i < strMatingStatus.length; i++){
            result = result + "mq.matingStatus() = "  + strMatingStatus[i] + "\n" ;
        }


        // RetireDates
        if ( null == mq.getMatingStartDate() ) {
            result = result + "mq.getRetiredStartDate() = " + mq.getRetiredStartDate() + "\n" ;
        } else {
            result = result + "mq.getRetiredStartDate() = " + zDateformatter.format(mq.getRetiredStartDate()) + "\n" ;
        }

        if ( null == mq.getMatingEndDate() ) {
        result = result + "mq.getRetiredEndDate() = " + mq.getRetiredEndDate() + "\n" ;
        } else {
           result = result + "mq.getRetiredEndDate() = " + zDateformatter.format(mq.getRetiredEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }


    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_C_2xml() throws Exception {
        System.out.println("testMatingStatusDates_C_2xml");

        MatingQueryDTO mq = new MatingQueryDTO();
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        String [] matingStatusX = new String[1];
        matingStatusX[0] = "Retired";
        mq.setMatingStatus(matingStatusX);

        // Set Both Dates
        mq.setRetiredStartDate(zDateformatter.parse("2003-02-22"));
        mq.setRetiredEndDate(zDateformatter.parse("2010-02-22"));

        MatingSearchBO msbo = new MatingSearchBO("Testing");
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue>2003-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue>2010-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";
        String result = msbo.dto2XML(mq);

        // TestHelper.printTestStrings(expResult, result);
        assertEquals(expResult, result);
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testMatingStatusDates_C_2dto() throws Exception {
        System.out.println("testMatingStatusDates_C_2dto");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Properties>\n    <Property PropertyName=\"MatingID\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatus\">\n        <PropertyValue>true</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterGeneration\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingOwner\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenId\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingPenName\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam1PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2ID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Strain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Stock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Gen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2Genotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2DOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"Dam2PlugDate\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireID\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStrain\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireStock\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGen\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireGenotype\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"SireDOB\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"DateRetired\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"WeanTime\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"NeedsTyping\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLitters\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalPups\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalMales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalFemales\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"TotalLittersDead\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingFilter\">\n        <PropertyValue>Equals</PropertyValue>\n    </Property>\n    <Property PropertyName=\"BirthDates\">\n        <PropertyValue>false</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingIDFromEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingIDToEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingStartDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingEndDate\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"matingRetireDateStart\">\n        <PropertyValue>2003-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"matingRetireDateEnd\">\n        <PropertyValue>2010-02-22</PropertyValue>\n    </Property>\n    <Property PropertyName=\"MatingStatusEntity\">\n        <PropertyValue>Retired</PropertyValue>\n    </Property>\n    <Property PropertyName=\"LitterStrainEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"LitterGenerationEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"MatingOwnerEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerIDEntity\">\n        <PropertyValue/>\n    </Property>\n    <Property PropertyName=\"ContainerNameEntity\">\n        <PropertyValue/>\n    </Property>\n</Properties>\n";


        MatingSearchBO msbo = new MatingSearchBO("Testing");

        MatingQueryDTO mq = null;
        mq = msbo.xml2DTO(xmlString);
        String expResult = "mq.setSelectMatingID = true\nmq.setSelectMatingStatus = true\nmq.matingStatus() = Retired\nmq.getRetiredStartDate() = Sat Feb 22 00:00:00 MST 2003\nmq.getRetiredEndDate() = Mon Feb 22 00:00:00 MST 2010\n";
        String result = "mq.setSelectMatingID = " + mq.isSelectMatingID() + "\n" +
                "mq.setSelectMatingStatus = " + mq.isSelectMatingStatus() + "\n" ;


        String [] strMatingStatus = mq.getMatingStatus();

        for ( int i = 0 ; i < strMatingStatus.length; i++){
            result = result + "mq.matingStatus() = "  + strMatingStatus[i] + "\n" ;
        }


        // RetireDates
        if ( null == mq.getMatingStartDate() ) {
            result = result + "mq.getRetiredStartDate() = " + mq.getRetiredStartDate() + "\n" ;
        } else {
            result = result + "mq.getRetiredStartDate() = " + zDateformatter.format(mq.getRetiredStartDate()) + "\n" ;
        }

        if ( null == mq.getMatingEndDate() ) {
        result = result + "mq.getRetiredEndDate() = " + mq.getRetiredEndDate() + "\n" ;
        } else {
           result = result + "mq.getRetiredEndDate() = " + zDateformatter.format(mq.getRetiredEndDate()) + "\n" ;
        }

        // TestHelper.printTestStrings(expResult , result);
        assertEquals(expResult, result);
    }



}