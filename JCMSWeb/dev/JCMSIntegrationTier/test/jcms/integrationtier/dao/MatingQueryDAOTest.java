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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.dto.MatingQueryDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jax.cs.common.TestHelper;


/**
 *
 * @author springer
 */
public class MatingQueryDAOTest {

    public MatingQueryDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {    //  Summarize Litter Information
    
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of dumpMatingQueryDTO method, of class MatingQueryDAO.
     */
    @Test
    public void testDumpMatingQueryDTO() throws ParseException {
        System.out.println("dumpMatingQueryDTO");
        MatingQueryDTO mq =  new MatingQueryDTO();
        MatingQueryDAO instance = new MatingQueryDAO();

        // Select Filters

        // Select Item ....
	// Mating ID
        mq.setSelectMatingID(true);

        // Mating Dates
        mq.setSelectMatingDates(true);


	// Mating Status
        mq.setSelectMatingStatus(true);

        // Litter Strain 
        mq.setSelectMatingStrain(true);


        // Litter Generation
        mq.setSelectMatingGeneration(true);

        // Mating Owner
        mq.setSelectMatingOwner(true);

        // Pen ID
        mq.setSelectMatingPenId(true);


	// Pen Name
        mq.setSelectMatingPenName(true);

        // Dam 1 ID
        mq.setSelectDam1ID(true);

	// Dam 1 Strain
        mq.setSelectDam1Strain(true);


	// Dam 1 Stock #
        mq.setSelectDam1Stock(true);

        // Dam 1 Generation
        mq.setSelectDam1Gen(true) ;

	// Dam 1 Up To Three Genotypes
        mq.setSelectDam1Genotype(true);

	// Dam 1 Date Of Birth
        mq.setSelectDam1DOB(true);

        // Dam 1 Plug Dates
        mq.setSelectDam1PlugDate(true);


        // Dam 2 ID
        mq.setSelectDam2ID(true) ;

	// Dam 2 Strain
        mq.setSelectDam2Strain(true) ;

	// Dam 2 Stock #
        mq.setSelectDam2Stock(true) ;

        // Dam 2 Generation
        mq.setSelectDam2Gen(true);

	// Dam 2 Up To Three Genotypes
        mq.setSelectDam2Genotype(true);

	// Dam 2 Date Of Birth
        mq.setSelectDam2DOB(true);

        // Dam 2 Plug Dates
        mq.setSelectDam2PlugDate(true);

        // Sire ID
        mq.setSelectSireID(true);


	// Sire Strain
        mq.setSelectSireStrain(true);

        // Sire Stock #
        mq.setSelectSireStock(true);

	// Sire Generation
        mq.setSelectSireGen(true) ;

        // Sire Up To Three Genotypes
        mq.setSelectSireGenotype(true) ;


	// Sire Date Of Birth
        mq.setSelectSireDOB(true);

        // Date Retired
        mq.setSelectDateRetired(true);

	// Wean Time
        mq.setSelectWeanTime(true);

	// Needs Typing
        mq.setSelectNeedsTyping(true);

        //  Summarize Litter Information
        // Total number of litters
        mq.setSelectTotalLitters(true);

        // Total number of pups born
        mq.setSelectTotalPups(true);

        // Total number of males weaned
        mq.setSelectTotalMales(true);

        // Total number of females weaned
        mq.setSelectTotalFemales(true);

        // Total number of litters born dead
        mq.setSelectTotalLittersDead(true);

        // First and last birth dates
        mq.setSelectBirthDates(true);

        //*****************************************
        // Go through the Filter Options
        //*****************************************
        //    Mating ID
        // Mating ID Equals
        MatingEntity me1  = new MatingEntity();
        me1.setMatingID(1);
        mq.setMatingIDFrom(me1);


        MatingEntity me10  = new MatingEntity();
        me10.setMatingID(10);
        mq.setMatingIDFrom(me10);


        // DateOfBirth filters
        Date msdEnd, msdStart;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        msdEnd = (Date)formatter.parse("2010-10-15");
        msdStart = (Date)formatter.parse("2010-10-01");

        //	Mating Dates  - Between
        mq.setMatingStartDate(msdStart);
        mq.setMatingEndDate(msdEnd);


        //	Mating Status
        String z[] = new String[2];
        z[0] = "A";
        z[1] = "B";
        mq.setMatingStatus(z);



        // Litter Strain
        StrainEntity se = new StrainEntity();
        se.setStrainName("C57J/B6");
        List<StrainEntity> selist =  new ArrayList<StrainEntity>();
        selist.add(se);
        mq.setLitterStrain(selist);


        //	Litter Generation
        CvGenerationEntity cge = new CvGenerationEntity();
        cge.setGeneration("N01");
        List<CvGenerationEntity> cgelist =  new ArrayList<CvGenerationEntity>();
        cgelist.add(cge);
        mq.setLitterGeneration(cgelist);

        //	Mating Owner
        OwnerEntity oe = new OwnerEntity();
        oe.setOwner("nobody");
        List<OwnerEntity> olist =  new ArrayList<OwnerEntity>();
        olist.add(oe);
        mq.setMatingOwner(olist);

        // Pen ID
        ContainerEntity pid = new ContainerEntity();
        pid.setContainerID(10);
        List<ContainerEntity> pIdlist =  new ArrayList<ContainerEntity>();
        pIdlist.add(pid);
        mq.setPenNumber(pIdlist);

        // Pen Name
        ContainerEntity pn = new ContainerEntity();
        pid.setContainerName("Pen10");
        List<ContainerEntity> pnlist =  new ArrayList<ContainerEntity>();
        pnlist.add(pn);
        mq.setPenName(pnlist);


        String expResult = "isSelectMatingID()=true\nisSelectMatingDates()=true\nisSelectMatingStatus()=true\nisSelectMatingStrain()=true\nisSelectLitterGeneration()=true\nisSelectMatingOwner()=true\nisSelectMatingPenId()=true\nisSelectMatingPenName()=true\nisSelectDam1ID()=true\nisSelectDam1Strain()=true\nisSelectDam1Stock()=true\nisSelectDam1Gen()=true\nisSelectDam1Gen()=true\nisSelectDam1DOB()=true\nisSelectDam1PlugDate()=true\nisSelectDam2ID()=true\nisSelectDam2Strain()=true\nisSelectDam2Stock()=true\nisSelectDam2Gen()=true\nisSelectDam2Gen()=true\nisSelectDam2DOB()=true\nisSelectDam2PlugDate()=true\nisSelectSireID()=true\nisSelectSireStrain()=true\nisSelectSireStock()=true\nisSelectSireGen()=true\nisSelectSireGenotype()=true\nisSelectSireDOB()=true\nisSelectDateRetired()=true\nisSelectWeanTime()=true\nisSelectNeedsTyping()=true\nisSelectTotalLitters()=true\nisSelectTotalLitters()=true\nisSelectTotalMales()=true\nisSelectTotalFemales()=true\nisSelectTotalLittersDead()=true\nisSelectBirthDates()=true\nMatingID From =10getMatingStartDate() =2010-10-01\nmq.getMatingEndDate()  =2010-10-15\nMating Status - List =A | B\nLitter Strain - List =C57J/B6\nLitter Generation - List =N01\nMating Owner - List =nobody\nContainer ID - List =10\nContainer Name - List =Pen10\n";
        String result = instance.dumpMatingQueryDTO(mq);
        // TestHelper.printTestStrings(expResult,result);
        assertEquals(expResult, result);

    }



    /**
     * Test of generateSQLQuery method, of class MatingQueryDAO.
     */
    @Test
    public void testSelect_all() throws ParseException {
        System.out.println("Select_all");
        MatingQueryDTO mq = new MatingQueryDTO();
        MatingQueryDAO instance = new MatingQueryDAO();
//-----------------------------------------------------------------------
        // Select Filters

        // Select Item ....
	// Mating ID
        mq.setSelectMatingID(true);

        // Mating Dates
        mq.setSelectMatingDates(true);


	// Mating Status
        mq.setSelectMatingStatus(true);

        // Litter Strain 
        mq.setSelectMatingStrain(true);


        // Litter Generation
        mq.setSelectMatingGeneration(true);

        // Mating Owner
        mq.setSelectMatingOwner(true);

        // Pen ID
        mq.setSelectMatingPenId(true);


	// Pen Name
        mq.setSelectMatingPenName(true);

        // Dam 1 ID
        mq.setSelectDam1ID(true);

	// Dam 1 Strain
        mq.setSelectDam1Strain(true);


	// Dam 1 Stock #
        mq.setSelectDam1Stock(true);

        // Dam 1 Generation
        mq.setSelectDam1Gen(true) ;

	// Dam 1 Up To Three Genotypes
        mq.setSelectDam1Genotype(true);

	// Dam 1 Date Of Birth
        mq.setSelectDam1DOB(true);

        // Dam 1 Plug Dates
        mq.setSelectDam1PlugDate(true);


        // Dam 2 ID
        mq.setSelectDam2ID(true) ;

	// Dam 2 Strain
        mq.setSelectDam2Strain(true) ;

	// Dam 2 Stock #
        mq.setSelectDam2Stock(true) ;

        // Dam 2 Generation
        mq.setSelectDam2Gen(true);

	// Dam 2 Up To Three Genotypes
        mq.setSelectDam2Genotype(true);

	// Dam 2 Date Of Birth
        mq.setSelectDam2DOB(true);

        // Dam 2 Plug Dates
        mq.setSelectDam2PlugDate(true);

        // Sire ID
        mq.setSelectSireID(true);


	// Sire Strain
        mq.setSelectSireStrain(true);

        // Sire Stock #
        mq.setSelectSireStock(true);

	// Sire Generation
        mq.setSelectSireGen(true) ;

        // Sire Up To Three Genotypes
        mq.setSelectSireGenotype(true) ;


	// Sire Date Of Birth
        mq.setSelectSireDOB(true);

        // Date Retired
        mq.setSelectDateRetired(true);

	// Wean Time
        mq.setSelectWeanTime(true);

	// Needs Typing
        mq.setSelectNeedsTyping(true);

        //  Summarize Litter Information
        // Total number of litters
        mq.setSelectTotalLitters(true);

        // Total number of pups born
        mq.setSelectTotalPups(true);

        // Total number of males weaned
        mq.setSelectTotalMales(true);

        // Total number of females weaned
        mq.setSelectTotalFemales(true);

        // Total number of litters born dead
        mq.setSelectTotalLittersDead(true);

        // First and last birth dates
        mq.setSelectBirthDates(true);

        //*****************************************
        // Go through the Filter Options
        //*****************************************
        //    Mating ID
        // Mating ID Equals
        MatingEntity me1  = new MatingEntity();
        me1.setMatingID(1);
        mq.setMatingIDFrom(me1);


        MatingEntity me10  = new MatingEntity();
        me10.setMatingID(10);
        mq.setMatingIDFrom(me10);


        // DateOfBirth filters
        Date msdEnd, msdStart;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        msdEnd = (Date)formatter.parse("2010-10-15");
        msdStart = (Date)formatter.parse("2010-10-01");

        //	Mating Dates  - Between
        mq.setMatingStartDate(msdStart);
        mq.setMatingEndDate(msdEnd);


        //	Mating Status
        String z[] = new String[1];
        z[0] = "Active";
        mq.setMatingStatus(z);



        // Litter Strain
        StrainEntity se = new StrainEntity();
        se.setStrainName("C57J/B6");
        List<StrainEntity> selist =  new ArrayList<StrainEntity>();
        selist.add(se);
        mq.setLitterStrain(selist);


        //	Litter Generation
        CvGenerationEntity cge = new CvGenerationEntity();
        cge.setGeneration("N01");
        List<CvGenerationEntity> cgelist =  new ArrayList<CvGenerationEntity>();
        cgelist.add(cge);
        mq.setLitterGeneration(cgelist);

        //	Mating Owner
        OwnerEntity oe = new OwnerEntity();
        oe.setOwner("nobody");
        List<OwnerEntity> olist =  new ArrayList<OwnerEntity>();
        olist.add(oe);
        mq.setMatingOwner(olist);

        // Pen ID
        ContainerEntity pid = new ContainerEntity();
        pid.setContainerID(10);
        List<ContainerEntity> pIdlist =  new ArrayList<ContainerEntity>();
        pIdlist.add(pid);
        mq.setPenNumber(pIdlist);

        // Pen Name
        ContainerEntity pn = new ContainerEntity();
        pn.setContainerName("Pen10");
        List<ContainerEntity> pnlist =  new ArrayList<ContainerEntity>();
        pnlist.add(pn);
        mq.setPenName(pnlist);
        
        String expResult = "Insert INTO MyTemp ( \n MatingID ,  MatingDate ,  MatingStatus ,  LitterStrain ,  LitterGeneration ,  MatingOwner ,  PenID ,  PenName ,  Dam1 ,  Dam1Strain ,  Dam1StockNumber ,  Dam1Generation ,  Dam1Genotypes ,  Dam1PlugDate ,  Dam2 ,  Dam2Strain ,  Dam2StockNumber ,  Dam2Generation ,  Dam2Genotypes ,  Dam2PlugDate ,  Sire ,  SireStrain ,  SireStockNumber ,  SireGeneration ,  SireGenotypes ,  RetireDate ,  WeanTime ,  NeedsTyping ,  TotalLitters ,  TotalPups ,  TotalMales ,  TotalFemales ,  TotalLittersDead ,  LitterBirthDates)\nSelect \nMating.matingID as MatingID , Mating.matingDate  as MatingDate , Mating.matingDate  is not null as MatingStatus , MatS.strainName  as LitterStrain , Mating.generation as LitterGeneration , Mating.owner as MatingOwner , Container.containerID as PenID , Container.containerName as PenName , dam1M.ID as Dam1 , dam1S.strainName as Dam1Strain , dam1S.jrNum as Dam1StockNumber , dam1M.generation as Dam1Generation , ' ' as Dam1Genotypes , ' ' as Dam1PlugDate , dam2M.ID as Dam2 , dam2S.strainName as Dam2Strain , dam2S.jrNum as Dam2StockNumber , dam2M.generation as Dam2Generation , ' ' as Dam2Genotypes , ' ' as Dam2PlugDate , Mouse.ID as Sire , Strain.strainName as SireStrain , Strain.jrNum as SireStockNumber , Mouse.generation as SireGeneration , ' ' as SireGenotypes , Mating.retiredDate as RetireDate , Mating.weanTime as WeanTime , Mating.needsTyping as NeedsTyping , ' ' as TotalLitters , ' ' as TotalPups , ' ' as TotalMales , ' ' as TotalFemales , ' ' as TotalLittersDead , ' ' as LitterBirthDates\nFrom \nMating \nLeft Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \nLeft Join Mouse as dam1M ON dam1M.`_mouse_key` = Mating.`_dam1_key` \nLeft Join Strain as dam1S ON dam1S.`_strain_key` = dam1M.`_strain_key` \nLeft Join Mouse as dam2M ON dam2M.`_mouse_key` = Mating.`_dam2_key` \nLeft Join Strain as dam2S ON dam2S.`_strain_key` = dam2M.`_strain_key` \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(Mating.matingID = 10) AND (Mating.matingDate Between '2010-10-01'  AND '2010-10-15') AND (Mating.matingDate is null) AND (MatS.strainName = 'C57J/B6') AND (Mating.generation = 'N01') AND (Mating.owner = 'nobody') AND (Container.containerID = 10) AND (Container.containerName = 'Pen10');\n";
        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResult,result[1]);
        assertEquals(expResult, result[1]);
    }

    /**
     * Test of generateSQLQuery method, of class MatingQueryDAO.
     */
    @Test
    public void testSelect_MatingID_Equals()  {
        System.out.println("Select_MatingID_Equals");
        MatingQueryDTO mq = new MatingQueryDTO();
        MatingQueryDAO instance = new MatingQueryDAO();
//-----------------------------------------------------------------------
        // Select Filters

        mq.setMatingFilter("Equals");
        // Select Item ....
        mq.setSelectMatingID(true);

        //  Mating ID
        MatingEntity me1  = new MatingEntity();
        me1.setMatingID(1);
        mq.setMatingIDFrom(me1);


        String expResult = "Insert INTO MyTemp ( \n MatingID)\nSelect \nMating.matingID as MatingID\nFrom \nMating \n\nWhere \n(Mating.matingID = 1);\n";
        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResult,result[1]);
        assertEquals(expResult, result[1]);


    }


    /**
     * Test of generateSQLQuery method, of class MatingQueryDAO.
     */
    @Test
    public void testSelect_MatingID_Between()  {
        System.out.println("Select_MatingID_Between");
        MatingQueryDTO mq = new MatingQueryDTO();
        MatingQueryDAO instance = new MatingQueryDAO();

        // Select Filters
        mq.setMatingFilter("Between");

        // Select Item ....
        mq.setSelectMatingID(true);

        //  Mating ID 1
        MatingEntity me1  = new MatingEntity();
        me1.setMatingID(1);
        mq.setMatingIDFrom(me1);


        //  Mating ID 10
        MatingEntity me10  = new MatingEntity();
        me10.setMatingID(10);
        mq.setMatingIDTo(me10);

        String expResult = "Insert INTO MyTemp ( \n MatingID)\nSelect \nMating.matingID as MatingID\nFrom \nMating \n\nWhere \n(Mating.matingID BETWEEN 1 AND 10);\n";
        String result[]  = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResult,result[1]);
        assertEquals(expResult, result[1]);


    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingDate_Between() throws java.text.ParseException  {
        System.out.println("MatingDate_Between");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingDates(true);

        // Date filters
        Date dStart;
        Date dEnd;


        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        dStart = (Date)formatter.parse(str_date);
        str_date="2010-10-01";
        dEnd = (Date)formatter.parse(str_date);

        mq.setMatingStartDate(dStart);
        mq.setMatingEndDate(dEnd);


        DateFormat zformatter ;
        zformatter = new SimpleDateFormat("yyyy-mm-dd");

        zformatter.format(dEnd);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingDate)\nSelect \nMating.matingID as MatingID , Mating.matingDate  as MatingDate\nFrom \nMating \n\nWhere \n(Mating.matingDate Between '2010-06-01'  AND '2010-10-01');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }



        /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingDate_NowToEnd() throws ParseException {
        System.out.println("MatingDate_NowToEnd");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingDates(true);

        // DateOfBirth filters
        Date dStart;

        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        dStart = (Date)formatter.parse(str_date);
        mq.setMatingEndDate(dStart);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingDate)\nSelect \nMating.matingID as MatingID , Mating.matingDate  as MatingDate\nFrom \nMating \n\nWhere \n(Mating.matingDate Between CURDATE()  AND '2010-06-01');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingDate_StartToNow() throws ParseException {
        System.out.println("MatingDate_StartToNow");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingDates(true);

        // DateOfBirth filters
        Date dEnd;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        String str_date="2010-10-01";
        dEnd = (Date)formatter.parse(str_date);
        mq.setMatingStartDate(dEnd);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingDate)\nSelect \nMating.matingID as MatingID , Mating.matingDate  as MatingDate\nFrom \nMating \n\nWhere \n(Mating.matingDate Between '2010-10-01'  AND CURDATE());\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingStatus_Any_1() throws ParseException {
        System.out.println("MatingStatus_Any_1");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        // DateOfBirth filters
        String st[] = new String[1];
        st[0] = "Any";
        mq.setMatingStatus(st);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingStatus)\nSelect \nMating.matingID as MatingID , Mating.matingDate  is not null as MatingStatus\nFrom \nMating \n\n;\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingStatus_Any_2() throws ParseException {
        System.out.println("MatingStatus_Any_2");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        // DateOfBirth filters
        String st[] = new String[2];
        st[0] = "Retired";
        st[1] = "Active";
        mq.setMatingStatus(st);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingStatus)\nSelect \nMating.matingID as MatingID , Mating.matingDate  is not null as MatingStatus\nFrom \nMating \n\n;\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingStatus_Active() throws ParseException {
        System.out.println("MatingStatus_Active");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        // DateOfBirth filters
        String st[] = new String[1];
        st[0] = "Active";
        mq.setMatingStatus(st);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingStatus)\nSelect \nMating.matingID as MatingID , Mating.matingDate  is not null as MatingStatus\nFrom \nMating \n\nWhere \n(Mating.matingDate is null);\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMatingStatus_Retire() throws ParseException {
        System.out.println("MatingStatus_Retire");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStatus(true);

        // DateOfBirth filters
        String st[] = new String[1];
        st[0] = "Retired";
        mq.setMatingStatus(st);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingStatus)\nSelect \nMating.matingID as MatingID , Mating.matingDate  is not null as MatingStatus\nFrom \nMating \n\nWhere \n(Mating.matingDate is not null);\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testStrain_One() throws ParseException {
        System.out.println("Strain_One");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStrain(true);

        // Strain Filters
        ArrayList<StrainEntity> stList = new ArrayList<StrainEntity>();

        // Add One Strain ....
        StrainEntity slOne = new StrainEntity();
        slOne.setStrainName("B6.129P2-Apoe<tmlUnc>/J");
        stList.add(slOne);


        mq.setLitterStrain(stList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterStrain)\nSelect \nMating.matingID as MatingID , MatS.strainName  as LitterStrain\nFrom \nMating \nLeft Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \n\nWhere \n(MatS.strainName = 'B6.129P2-Apoe<tmlUnc>/J');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testStrain_two() throws ParseException {
        System.out.println("Strain_two");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStrain(true);

        // Strain Filters
        ArrayList<StrainEntity> stList = new ArrayList<StrainEntity>();

        // Add One Strain ....
        StrainEntity slOne = new StrainEntity();
        slOne.setStrainName("B6.129P2-Apoe<tmlUnc>/J");
        stList.add(slOne);

        // Add Two Strain ....
        StrainEntity slTwo = new StrainEntity();
        slTwo.setStrainName("C57BL/6J");
        stList.add(slTwo);


        mq.setLitterStrain(stList);



        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterStrain)\nSelect \nMating.matingID as MatingID , MatS.strainName  as LitterStrain\nFrom \nMating \nLeft Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \n\nWhere \n(MatS.strainName = 'B6.129P2-Apoe<tmlUnc>/J'  OR  MatS.strainName = 'C57BL/6J');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testStrain_three() throws ParseException {
        System.out.println("Strain_three");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingStrain(true);

        // Strain Filters
        ArrayList<StrainEntity> stList = new ArrayList<StrainEntity>();

        // Add One Strain ....
        StrainEntity slOne = new StrainEntity();
        slOne.setStrainName("B6.129P2-Apoe<tmlUnc>/J");
        stList.add(slOne);

        // Add Two Strain ....
        StrainEntity slTwo = new StrainEntity();
        slTwo.setStrainName("C57BL/6J");
        stList.add(slTwo);

        // Add Two Strain ....
        StrainEntity slThree = new StrainEntity();
        slThree.setStrainName("NOD.CB17-Prkdc<scid>/J");
        stList.add(slThree);

        mq.setLitterStrain(stList);


        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterStrain)\nSelect \nMating.matingID as MatingID , MatS.strainName  as LitterStrain\nFrom \nMating \nLeft Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \n\nWhere \n(MatS.strainName = 'B6.129P2-Apoe<tmlUnc>/J'  OR  MatS.strainName = 'C57BL/6J'  OR  MatS.strainName = 'NOD.CB17-Prkdc<scid>/J');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

        /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGeneration_one() throws ParseException {
        System.out.println("Generation_one");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingGeneration(true);

        // Strain Filters
        ArrayList<CvGenerationEntity> gList = new ArrayList<CvGenerationEntity>();

        // Add One generation ....
        CvGenerationEntity genOne = new CvGenerationEntity();
        genOne.setGeneration("F01");
        gList.add(genOne);

        mq.setLitterGeneration(gList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterGeneration)\nSelect \nMating.matingID as MatingID , Mating.generation as LitterGeneration\nFrom \nMating \n\nWhere \n(Mating.generation = 'F01');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGeneration_two() throws ParseException {
        System.out.println("Generation_two");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingGeneration(true);

        // Strain Filters
        ArrayList<CvGenerationEntity> gList = new ArrayList<CvGenerationEntity>();

        // Add One generation ....
        CvGenerationEntity genOne = new CvGenerationEntity();
        genOne.setGeneration("F01");
        gList.add(genOne);

        // Add Second generation ....
        CvGenerationEntity genTwo = new CvGenerationEntity();
        genTwo.setGeneration("N04");
        gList.add(genTwo);

        mq.setLitterGeneration(gList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterGeneration)\nSelect \nMating.matingID as MatingID , Mating.generation as LitterGeneration\nFrom \nMating \n\nWhere \n(Mating.generation = 'F01'  OR  Mating.generation = 'N04');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGeneration_three() throws ParseException {
        System.out.println("Generation_three");
        MatingQueryDTO mq = new MatingQueryDTO();

        // All select
        mq.setSelectMatingID(true);
        mq.setSelectMatingGeneration(true);
        mq.setSelectMatingStrain(true);

        // Strain Filters
        ArrayList<CvGenerationEntity> gList = new ArrayList<CvGenerationEntity>();

        // Add One generation ....
        CvGenerationEntity genOne = new CvGenerationEntity();
        genOne.setGeneration("F01");
        gList.add(genOne);

        // Add Second generation ....
        CvGenerationEntity genTwo = new CvGenerationEntity();
        genTwo.setGeneration("N04");
        gList.add(genTwo);

        // Add Thrid generation ....
        CvGenerationEntity genThree = new CvGenerationEntity();
        genThree.setGeneration("N14");
        gList.add(genThree);

        mq.setLitterGeneration(gList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  LitterStrain ,  LitterGeneration)\nSelect \nMating.matingID as MatingID , MatS.strainName  as LitterStrain , Mating.generation as LitterGeneration\nFrom \nMating \nLeft Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \n\nWhere \n(Mating.generation = 'F01'  OR  Mating.generation = 'N04'  OR  Mating.generation = 'N14');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOwner_one() throws ParseException {
        System.out.println("Owner_one");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingOwner(true);


        //  Filters
        ArrayList<OwnerEntity> oList = new ArrayList<OwnerEntity>();

        // Add One  ....
        OwnerEntity One = new OwnerEntity();
        One.setOwner("Owner_one");
        oList.add(One);

        mq.setMatingOwner(oList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingOwner)\nSelect \nMating.matingID as MatingID , Mating.owner as MatingOwner\nFrom \nMating \n\nWhere \n(Mating.owner = 'Owner_one');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOwner_Two() throws ParseException {
        System.out.println("Owner_Two");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingOwner(true);


        //  Filters
        ArrayList<OwnerEntity> oList = new ArrayList<OwnerEntity>();


        // Add One  ....
        OwnerEntity One = new OwnerEntity();
        One.setOwner("Owner_one");
        oList.add(One);

        // Add Two  ....
        OwnerEntity Two = new OwnerEntity();
        Two.setOwner("Owner_Two");
        oList.add(Two);

        mq.setMatingOwner(oList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingOwner)\nSelect \nMating.matingID as MatingID , Mating.owner as MatingOwner\nFrom \nMating \n\nWhere \n(Mating.owner = 'Owner_one'  OR  Mating.owner = 'Owner_Two');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOwner_Three() throws ParseException {
        System.out.println("Owner_Three");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingOwner(true);


        //  Filters
        ArrayList<OwnerEntity> oList = new ArrayList<OwnerEntity>();


        // Add One  ....
        OwnerEntity One = new OwnerEntity();
        One.setOwner("Owner_one");
        oList.add(One);

        // Add Two  ....
        OwnerEntity Two = new OwnerEntity();
        Two.setOwner("Owner_Two");
        oList.add(Two);

        // Add Three  ....
        OwnerEntity Three = new OwnerEntity();
        Three.setOwner("Owner_Three");
        oList.add(Three);

        mq.setMatingOwner(oList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  MatingOwner)\nSelect \nMating.matingID as MatingID , Mating.owner as MatingOwner\nFrom \nMating \n\nWhere \n(Mating.owner = 'Owner_one'  OR  Mating.owner = 'Owner_Two'  OR  Mating.owner = 'Owner_Three');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerID_one() throws ParseException {
        System.out.println("ContainerID_one");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerID(100);
        cList.add(One);

        mq.setPenNumber(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerID = 100);\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerID_two() throws ParseException {
        System.out.println("ContainerID_two");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerID(100);
        cList.add(One);

        // Add Second ContainerID ....
        ContainerEntity Two = new ContainerEntity();
        Two.setContainerID(101);
        cList.add(Two);

        mq.setPenNumber(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerID = 100  OR  Container.containerID = 101);\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

        /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerID_three() throws ParseException {
        System.out.println("ContainerID_three");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerID(100);
        cList.add(One);

        // Add Second ContainerID ....
        ContainerEntity Two = new ContainerEntity();
        Two.setContainerID(101);
        cList.add(Two);

        // Add Second ContainerID ....
        ContainerEntity Three = new ContainerEntity();
        Three.setContainerID(102);
        cList.add(Three);

        mq.setPenNumber(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerID = 100  OR  Container.containerID = 101  OR  Container.containerID = 102);\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerName_one() throws ParseException {
        System.out.println("ContainerName_one");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerName("Pen_One");
        cList.add(One);

        mq.setPenName(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerName = 'Pen_One');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerName_two() throws ParseException {
        System.out.println("ContainerName_two");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerName("Pen_One");
        cList.add(One);

        // Add Second ContainerID ....
        ContainerEntity Two = new ContainerEntity();
        Two.setContainerName("Pen_Two");
        cList.add(Two);

        mq.setPenName(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerName = 'Pen_One'  OR  Container.containerName = 'Pen_Two');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

        /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testContainerName_three() throws ParseException {
        System.out.println("ContainerName_three");
        MatingQueryDTO mq = new MatingQueryDTO();

        // Select
        mq.setSelectMatingID(true);
        mq.setSelectMatingPenId(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerName("Pen_One");
        cList.add(One);

        // Add Second ContainerID ....
        ContainerEntity Two = new ContainerEntity();
        Two.setContainerName("Pen_Two");
        cList.add(Two);

        // Add Second ContainerID ....
        ContainerEntity Three = new ContainerEntity();
        Three.setContainerName("Pen_Three");
        cList.add(Three);

        mq.setPenName(cList);

        MatingQueryDAO instance = new MatingQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MatingID ,  PenID)\nSelect \nMating.matingID as MatingID , Container.containerID as PenID\nFrom \nMating \nLeft Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(Container.containerName = 'Pen_One'  OR  Container.containerName = 'Pen_Two'  OR  Container.containerName = 'Pen_Three');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }



}