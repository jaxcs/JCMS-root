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
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.dto.MouseQueryDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jax.cs.common.TestHelper;


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


/**
 *
 * @author springer
 */
public class MouseQueryDAOTest {

    public MouseQueryDAOTest() {
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

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testEvertyingSelect() {
        System.out.println("EvertyingSelect");
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
        mq.setMouseIDFrom(fromMouse);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  BirthDate ,  ExitDate ,  BreedingStatus ,  Origin ,  LifeStatus ,  Strain ,  Generation ,  Owner ,  CauseOfDeath ,  LitterID ,  PenID ,  PenName ,  Protocol ,  MouseUse ,  Gene ,  Allele1 ,  Allele2 ,  GenotypeDate ,  MatingID ,  LitterMates ,  Dam1 ,  Dam2 ,  Sire ,  CoatColor ,  Diet)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.birthDate as BirthDate , Mouse.exitDate as ExitDate , Mouse.breedingStatus as BreedingStatus , Mouse.origin as Origin , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain , Mouse.generation as Generation , Mouse.owner as Owner , Mouse.cod as CauseOfDeath , Litter.litterID as LitterID , Container.containerID as PenID , Container.containerName as PenName , Mouse.protocol as Protocol , MouseUsage.use as MouseUse , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2 , Genotype.gtDate as GenotypeDate , Mating.matingID as MatingID , ' ' as LitterMates , ' '  as Dam1 , ' '  as Dam2 , ' '  as Sire , Mouse.coatColor as CoatColor , Mouse.diet as Diet\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \nLeft Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \nLeft Join Mating On (Mating.`_dam1_key` = Mouse.`_mouse_key` OR Mating.`_dam2_key` = Mouse.`_mouse_key` OR Mating.`_sire_key` = Mouse.`_mouse_key` ) \n\nWhere \n(Mouse.ID = 'c57j-0001');\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseFilter_Equals() {
        System.out.println("MouseFilter_Equals");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);
        mq.setAgeFilter("Months");
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseLifeStatus(true);

        // Set filters
        mq.setMouseFilter("Equals");
        // To
        MouseEntity fromMouse = new MouseEntity();
                fromMouse.setId("c57j-0001");
        mq.setMouseIDFrom(fromMouse);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  BirthDate ,  ExitDate ,  BreedingStatus ,  Origin ,  LifeStatus)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.birthDate as BirthDate , Mouse.exitDate as ExitDate , Mouse.breedingStatus as BreedingStatus , Mouse.origin as Origin , Mouse.lifeStatus as LifeStatus\nFrom \nMouse \n\nWhere \n(Mouse.ID = 'c57j-0001');\n";
        String result[] = instance.generateSQLQuery(mq);

        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseFilter_Contains() {
        System.out.println("MouseFilter_Contains");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);
        mq.setAgeFilter("Months");
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseLifeStatus(true);
        // Set filters
        mq.setMouseFilter("Contains");
        mq.setMouseID("c57j");

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  BirthDate ,  ExitDate ,  BreedingStatus ,  Origin ,  LifeStatus)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.birthDate as BirthDate , Mouse.exitDate as ExitDate , Mouse.breedingStatus as BreedingStatus , Mouse.origin as Origin , Mouse.lifeStatus as LifeStatus\nFrom \nMouse \n\nWhere \n(Mouse.ID LIKE '%c57j%');\n";
        String result[] = instance.generateSQLQuery(mq);

        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
        }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseFilter_Between() {
        System.out.println("MouseFilter_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);
        mq.setAgeFilter("Years");
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
        mq.setSelectMouseCoatColor(true);
        mq.setSelectMouseDiet(true);
        // Set filters
        mq.setMouseFilter("Between");
        // To
        MouseEntity fromMouse = new MouseEntity();
                fromMouse.setId("c57j-0001");
        mq.setMouseIDFrom(fromMouse);

        // From
        MouseEntity ToMouse = new MouseEntity();
                ToMouse.setId("c57j-0003");
        mq.setMouseIDTo(ToMouse);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  BirthDate ,  ExitDate ,  BreedingStatus ,  Origin ,  LifeStatus ,  Strain ,  Generation ,  Owner ,  CauseOfDeath ,  LitterID ,  PenID ,  PenName ,  Protocol ,  MouseUse ,  Gene ,  Allele1 ,  Allele2 ,  GenotypeDate ,  MatingID ,  CoatColor ,  Diet)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.birthDate as BirthDate , Mouse.exitDate as ExitDate , Mouse.breedingStatus as BreedingStatus , Mouse.origin as Origin , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain , Mouse.generation as Generation , Mouse.owner as Owner , Mouse.cod as CauseOfDeath , Litter.litterID as LitterID , Container.containerID as PenID , Container.containerName as PenName , Mouse.protocol as Protocol , MouseUsage.use as MouseUse , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2 , Genotype.gtDate as GenotypeDate , Mating.matingID as MatingID , Mouse.coatColor as CoatColor , Mouse.diet as Diet\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \nLeft Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \nLeft Join Mating On (Mating.`_dam1_key` = Mouse.`_mouse_key` OR Mating.`_dam2_key` = Mouse.`_mouse_key` OR Mating.`_sire_key` = Mouse.`_mouse_key` ) \n\nWhere \n((Mouse.ID BETWEEN 'c57j-0001' AND 'c57j-0003'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testSexFilter_One() {
        System.out.println("SexFilter_One");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseAge(true);
        mq.setAgeFilter("Days");
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseBreedingStatus(true);

        // Set filters
        ArrayList sexList = new ArrayList();


        CvSexEntity seMale = new CvSexEntity();
        seMale.setSex("M");
        sexList.add(seMale);

        mq.setSex(sexList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  BirthDate ,  ExitDate ,  BreedingStatus)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.birthDate as BirthDate , Mouse.exitDate as ExitDate , Mouse.breedingStatus as BreedingStatus\nFrom \nMouse \n\nWhere \n(( Mouse.sex = 'M' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testSexFilter_Two() {
        System.out.println("SexFilter_Two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // Set filters
        ArrayList sexList = new ArrayList();

        CvSexEntity seFemale = new CvSexEntity();
        seFemale.setSex("F");
        sexList.add(seFemale);

        CvSexEntity seMale = new CvSexEntity();
        seMale.setSex("M");
        sexList.add(seMale);

        mq.setSex(sexList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.sex = 'F'  OR  Mouse.sex = 'M' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testSexFilter_All() {
        System.out.println("SexFilter_All");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // Set filters
        ArrayList sexList = new ArrayList();

        CvSexEntity seFemale = new CvSexEntity();
        seFemale.setSex("F");
        sexList.add(seFemale);

        CvSexEntity seMale = new CvSexEntity();
        seMale.setSex("M");
        sexList.add(seMale);

        CvSexEntity seUnknown = new CvSexEntity();
        seUnknown.setSex("-");
        sexList.add(seUnknown);

        mq.setSex(sexList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.sex = 'F'  OR  Mouse.sex = 'M'  OR  Mouse.sex = '-' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInDays_Equals() {
        System.out.println("AgeInDays_Equals");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Days");
        mq.setAgeFilter("Equals");
        mq.setAgeFrom(5);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) = 5));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInDays_GreaterThan() {
        System.out.println("AgeInDays_GreaterThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Days");
        mq.setAgeFilter("Greater Than");
        mq.setAgeFrom(1);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) > 1));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInDays_LessThan() {
        System.out.println("AgeInDays_LessThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Days");
        mq.setAgeFilter("Less Than");
        mq.setAgeFrom(10);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) < 10));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);
    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInDays_Between() {
        System.out.println("AgeInDays_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Days");
        mq.setAgeFilter("Between");
        mq.setAgeFrom(10);
        mq.setAgeTo(20);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInDays ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) BETWEEN 10 AND 20 ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInWeeks_Equals() {
        System.out.println("AgeInWeeks_Equals");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Weeks");
        mq.setAgeFilter("Equals");
        mq.setAgeFrom(5);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInWeeks ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) as AgeInWeeks , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) = 5));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInWeeks_GreaterThan() {
        System.out.println("AgeInWeeks_GreaterThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Weeks");
        mq.setAgeFilter("Greater Than");
        mq.setAgeFrom(1);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInWeeks ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) as AgeInWeeks , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) > 1));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInWeeks_LessThan() {
        System.out.println("AgeInWeeks_LessThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Weeks");
        mq.setAgeFilter("Less Than");
        mq.setAgeFrom(10);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInWeeks ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) as AgeInWeeks , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) < 10));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInWeeks_Between() {
        System.out.println("AgeInWeeks_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Weeks");
        mq.setAgeFilter("Between");
        mq.setAgeFrom(10);
        mq.setAgeTo(20);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInWeeks ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) as AgeInWeeks , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) BETWEEN 10 AND 20 ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInMonths_Equals() {
        System.out.println("AgeInMonths_Equals");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Months");
        mq.setAgeFilter("Equals");
        mq.setAgeFrom(5);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults ="Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInMonths ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE())  as AgeInMonths , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) = 5));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInMonths_GreaterThan() {
        System.out.println("AgeInMonths_GreaterThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Months");
        mq.setAgeFilter("Greater Than");
        mq.setAgeFrom(1);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInMonths ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE())  as AgeInMonths , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) > 1));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInMonths_LessThan() {
        System.out.println("AgeInMonths_LessThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Months");
        mq.setAgeFilter("Less Than");
        mq.setAgeFrom(10);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInMonths ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE())  as AgeInMonths , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) < 10));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInMonths_Between() {
        System.out.println("AgeInMonths_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Months");
        mq.setAgeFilter("Between");
        mq.setAgeFrom(10);
        mq.setAgeTo(20);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInMonths ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE())  as AgeInMonths , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) BETWEEN 10 AND 20 ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInYears_Equals() {
        System.out.println("AgeInYears_Equals");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Years");
        mq.setAgeFilter("Equals");
        mq.setAgeFrom(5);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInYears ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) as AgeInYears , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) = 5));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInYears_GreaterThan() {
        System.out.println("AgeInYears_GreaterThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Years");
        mq.setAgeFilter("Greater Than");
        mq.setAgeFrom(1);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInYears ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) as AgeInYears , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) > 1 ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInYears_LessThan() {
        System.out.println("AgeInYears_LessThan");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Years");
        mq.setAgeFilter("Less Than");
        mq.setAgeFrom(10);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInYears ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) as AgeInYears , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) < 10));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testAgeInYears_Between() {
        System.out.println("AgeInYears_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseSex(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);
        mq.setSelectMouseAge(true);
        // Age filters
        mq.setAgeMeasure("Years");
        mq.setAgeFilter("Between");
        mq.setAgeFrom(10);
        mq.setAgeTo(20);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Sex ,  AgeInYears ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.sex as Sex , TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) as AgeInYears , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) BETWEEN 10 AND 20 ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testDOB_Between() throws java.text.ParseException  {
        System.out.println("DOB_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date dobStart;
        Date dobEnd;


        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        dobStart = (Date)formatter.parse(str_date);
        str_date="2010-10-01";
        dobEnd = (Date)formatter.parse(str_date);

        mq.setDobStartDate(dobStart);
        mq.setDobEndDate(dobEnd);


        DateFormat zformatter ;
        zformatter = new SimpleDateFormat("yyyy-mm-dd");

        zformatter.format(dobEnd);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BirthDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.birthDate as BirthDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.birthDate Between '2010-06-01'  AND '2010-10-01'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testDOB_NowToEnd() throws ParseException {
        System.out.println("DOB_NowToEnd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date dobStart;

        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        dobStart = (Date)formatter.parse(str_date);
        mq.setDobEndDate(dobStart);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BirthDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.birthDate as BirthDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.birthDate Between CURDATE()  AND '2010-06-01'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testDBO_StartToNow() throws ParseException {
        System.out.println("DBO_StartToNow");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseDOB(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date dobEnd;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        String str_date="2010-10-01";
        dobEnd = (Date)formatter.parse(str_date);
        mq.setDobStartDate(dobEnd);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BirthDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.birthDate as BirthDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.birthDate Between '2010-10-01'  AND CURDATE()));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testExitDate_Between() throws java.text.ParseException  {
        System.out.println("ExitDate_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date Start;
        Date End;


        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        Start = (Date)formatter.parse(str_date);
        str_date="2010-10-01";
        End = (Date)formatter.parse(str_date);

        mq.setExitStartDate(Start);
        mq.setExitEndDate(End);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  ExitDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.exitDate as ExitDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.exitDate Between '2010-06-01'  AND '2010-10-01'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testExitDate_NowToEnd() throws ParseException {
        System.out.println("ExitDate_NowToEnd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date Start;

        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        Start = (Date)formatter.parse(str_date);
        mq.setExitEndDate(Start);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  ExitDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.exitDate as ExitDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.exitDate Between CURDATE()  AND '2010-06-01'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testExitDate_StartToNow() throws ParseException {
        System.out.println("ExitDate_StartToNow");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseExitDate(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date End;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        String str_date="2010-10-01";
        End = (Date)formatter.parse(str_date);
        mq.setExitStartDate(End);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  ExitDate ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.exitDate as ExitDate , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n((Mouse.exitDate Between '2010-10-01'  AND CURDATE()));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testBreedingStatus_Breeding() throws ParseException {
        System.out.println("BreedingStatus_Breeding");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList bsList = new ArrayList();


        CvBreedingStatusEntity bsBreeding = new CvBreedingStatusEntity();
        bsBreeding.setBreedingStatus("B");
        bsList.add(bsBreeding);
        mq.setBreedingStatus(bsList);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BreedingStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.breedingStatus as BreedingStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.breedingStatus = 'B' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testBreedingStatus_Retired() throws ParseException {
        System.out.println("BreedingStatus_Retired");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList bsList = new ArrayList();


        CvBreedingStatusEntity bsRetired= new CvBreedingStatusEntity();
        bsRetired.setBreedingStatus("R");
        bsList.add(bsRetired);
        mq.setBreedingStatus(bsList);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BreedingStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.breedingStatus as BreedingStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.breedingStatus = 'R' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testBreedingStatus_Virgin() throws ParseException {
        System.out.println("BreedingStatus_Virgin");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList bsList = new ArrayList();


        CvBreedingStatusEntity bsVirgin = new CvBreedingStatusEntity();
        bsVirgin.setBreedingStatus("V");
        bsList.add(bsVirgin);
        mq.setBreedingStatus(bsList);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BreedingStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.breedingStatus as BreedingStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.breedingStatus = 'V' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testBreedingStatus_Unknown() throws ParseException {
        System.out.println("BreedingStatus_Unknown");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList bsList = new ArrayList();


        CvBreedingStatusEntity bsUnknown = new CvBreedingStatusEntity();
        bsUnknown.setBreedingStatus("U");
        bsList.add(bsUnknown);
        mq.setBreedingStatus(bsList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BreedingStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.breedingStatus as BreedingStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.breedingStatus = 'U' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testBreedingStatus_All() throws ParseException {
        System.out.println("BreedingStatus_All");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseBreedingStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList bsList = new ArrayList();

        // Breeding
        CvBreedingStatusEntity bsBreeding = new CvBreedingStatusEntity();
        bsBreeding.setBreedingStatus("B");
        bsList.add(bsBreeding);

        // Retired
        CvBreedingStatusEntity bsRetired= new CvBreedingStatusEntity();
        bsRetired.setBreedingStatus("R");
        bsList.add(bsRetired);

        // Virgin
        CvBreedingStatusEntity bsVirgin = new CvBreedingStatusEntity();
        bsVirgin.setBreedingStatus("V");
        bsList.add(bsVirgin);

        // Unknown
        CvBreedingStatusEntity bsUnknown = new CvBreedingStatusEntity();
        bsUnknown.setBreedingStatus("U");
        bsList.add(bsUnknown);


        mq.setBreedingStatus(bsList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  BreedingStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.breedingStatus as BreedingStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.breedingStatus = 'B'  OR  Mouse.breedingStatus = 'R'  OR  Mouse.breedingStatus = 'V'  OR  Mouse.breedingStatus = 'U' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOrigin_Jax() throws ParseException {
        System.out.println("Origin_Jax");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList orgList = new ArrayList();


        CvMouseOriginEntity orgJax = new CvMouseOriginEntity();
        orgJax.setMouseOrigin("JAX");
        orgList.add(orgJax);
        mq.setOrigin(orgList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Origin ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.origin as Origin , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.origin = 'JAX' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOrigin_Internal() throws ParseException {
        System.out.println("Origin_Internal");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList orgList = new ArrayList();


        CvMouseOriginEntity orgInternal = new CvMouseOriginEntity();
        orgInternal.setMouseOrigin("internal");
        orgList.add(orgInternal);
        mq.setOrigin(orgList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Origin ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.origin as Origin , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.origin = 'internal' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testOrigin_All() throws ParseException {
        System.out.println("Origin_All");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOrigin(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList orgList = new ArrayList();


        // Jax
        CvMouseOriginEntity orgJax = new CvMouseOriginEntity();
        orgJax.setMouseOrigin("JAX");
        orgList.add(orgJax);

        // Internal
        CvMouseOriginEntity orgInternal = new CvMouseOriginEntity();
        orgInternal.setMouseOrigin("internal");
        orgList.add(orgInternal);

        mq.setOrigin(orgList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Origin ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.origin as Origin , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.origin = 'JAX'  OR  Mouse.origin = 'internal' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLifeStatus_One() throws ParseException {
        System.out.println("LifeStatus_One");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList<LifeStatusEntity> lsList = new ArrayList<LifeStatusEntity>();


        // Add Alive ....
        LifeStatusEntity lsOne = new LifeStatusEntity();
        lsOne.setLifeStatus("A");
        lsList.add(lsOne);


        mq.setLifeStatus(lsList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.lifeStatus = 'A' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLifeStatus_two() throws ParseException {
        System.out.println("LifeStatus_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList<LifeStatusEntity> lsList = new ArrayList<LifeStatusEntity>();


        // Add Alive ....
        LifeStatusEntity lsOne = new LifeStatusEntity();
        lsOne.setLifeStatus("A");
        lsList.add(lsOne);

        // Add Euthanized ....
        LifeStatusEntity lsTwo = new LifeStatusEntity();
        lsTwo.setLifeStatus("E");
        lsList.add(lsTwo);

        mq.setLifeStatus(lsList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.lifeStatus = 'A'  OR  Mouse.lifeStatus = 'E' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLifeStatus_three() throws ParseException {
        System.out.println("LifeStatus_three");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseStrain(true);

        // Breeding Status Filters
        ArrayList<LifeStatusEntity> lsList = new ArrayList<LifeStatusEntity>();


        // Add Alive ....
        LifeStatusEntity lsOne = new LifeStatusEntity();
        lsOne.setLifeStatus("A");
        lsList.add(lsOne);

        // Add Euthanized ....
        LifeStatusEntity lsTwo = new LifeStatusEntity();
        lsTwo.setLifeStatus("E");
        lsList.add(lsTwo);

        // Add Euthanized ....
        LifeStatusEntity lsThree = new LifeStatusEntity();
        lsThree.setLifeStatus("M");
        lsList.add(lsThree);

        mq.setLifeStatus(lsList);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  LifeStatus ,  Strain)\nSelect \nMouse.ID as MouseID , Mouse.lifeStatus as LifeStatus , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.lifeStatus = 'A'  OR  Mouse.lifeStatus = 'E'  OR  Mouse.lifeStatus = 'M' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<StrainEntity> stList = new ArrayList<StrainEntity>();

        // Add One Strain ....
        StrainEntity slOne = new StrainEntity();
        slOne.setStrainName("B6.129P2-Apoe<tmlUnc>/J");
        stList.add(slOne);


        mq.setStrain(stList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Strain.strainName = 'B6.129P2-Apoe<tmlUnc>/J' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseStrain(true);

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


        mq.setStrain(stList);



        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Strain.strainName = 'B6.129P2-Apoe<tmlUnc>/J'  OR  Strain.strainName = 'C57BL/6J' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseStrain(true);

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

        mq.setStrain(stList);


        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Strain.strainName = 'B6.129P2-Apoe<tmlUnc>/J'  OR  Strain.strainName = 'C57BL/6J'  OR  Strain.strainName = 'NOD.CB17-Prkdc<scid>/J' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGeneration(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<CvGenerationEntity> gList = new ArrayList<CvGenerationEntity>();

        // Add One generation ....
        CvGenerationEntity genOne = new CvGenerationEntity();
        genOne.setGeneration("F01");
        gList.add(genOne);

        mq.setGeneration(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Generation)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.generation as Generation\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.generation = 'F01' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGeneration(true);
        mq.setSelectMouseStrain(true);

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

        mq.setGeneration(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Generation)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.generation as Generation\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.generation = 'F01'  OR  Mouse.generation = 'N04' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGeneration(true);
        mq.setSelectMouseStrain(true);

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

        mq.setGeneration(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Generation)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.generation as Generation\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.generation = 'F01'  OR  Mouse.generation = 'N04'  OR  Mouse.generation = 'N14' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testCauseOfDeath_one() throws ParseException {
        System.out.println("CauseOfDeath_one");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseCOD(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<CvCauseOfDeathEntity> codList = new ArrayList<CvCauseOfDeathEntity>();

        // Add One cod ....
        CvCauseOfDeathEntity codOne = new CvCauseOfDeathEntity();
        codOne.setCod("Euthanasia");
        codList.add(codOne);

        mq.setCauseOfDeath(codList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  CauseOfDeath)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.cod as CauseOfDeath\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.cod = 'Euthanasia' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testCauseOfDeath_two()  {
        System.out.println("CauseOfDeath_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseCOD(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<CvCauseOfDeathEntity> codList = new ArrayList<CvCauseOfDeathEntity>();

        // Add One cod ....
        CvCauseOfDeathEntity codOne = new CvCauseOfDeathEntity();
        codOne.setCod("Euthanasia");
        codList.add(codOne);

        // Add Two cod ....
        CvCauseOfDeathEntity codTwo = new CvCauseOfDeathEntity();
        codTwo.setCod("Tumor");
        codList.add(codTwo);

        mq.setCauseOfDeath(codList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  CauseOfDeath)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.cod as CauseOfDeath\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.cod = 'Euthanasia'  OR  Mouse.cod = 'Tumor' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLitter_one() throws ParseException {
        System.out.println("Litter_one");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLitter(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<LitterEntity> lList = new ArrayList<LitterEntity>();

        // Add One littter ....
        LitterEntity lOne = new LitterEntity();
        lOne.setLitterID("100");
        lList.add(lOne);

        mq.setLitterNumber(lList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  LitterID)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Litter.litterID as LitterID\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \n\nWhere \n(( Litter.LitterID = '100' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLitter_two() throws ParseException {
        System.out.println("Litter_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLitter(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<LitterEntity> lList = new ArrayList<LitterEntity>();

        // Add One littter ....
        LitterEntity lOne = new LitterEntity();
        lOne.setLitterID("100");
        lList.add(lOne);

        // Add Second littter ....
        LitterEntity lTwo = new LitterEntity();
        lTwo.setLitterID("101");
        lList.add(lTwo);

        mq.setLitterNumber(lList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  LitterID)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Litter.litterID as LitterID\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \n\nWhere \n(( Litter.LitterID = '100'  OR  Litter.LitterID = '101' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testLitter_three() throws ParseException {
        System.out.println("Litter_three");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLitter(true);
        mq.setSelectMouseStrain(true);

        // Strain Filters
        ArrayList<LitterEntity> lList = new ArrayList<LitterEntity>();

        // Add One litter ....
        LitterEntity lOne = new LitterEntity();
        lOne.setLitterID("100");
        lList.add(lOne);

        // Add Second litter ....
        LitterEntity lTwo = new LitterEntity();
        lTwo.setLitterID("101");
        lList.add(lTwo);

        // Add Thrid litter ....
        LitterEntity lThree = new LitterEntity();
        lThree.setLitterID("102");
        lList.add(lThree);
        mq.setLitterNumber(lList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  LitterID)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Litter.litterID as LitterID\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \n\nWhere \n(( Litter.LitterID = '100'  OR  Litter.LitterID = '101'  OR  Litter.LitterID = '102' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerID(100);
        cList.add(One);

        mq.setPenNumber(cList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerID = 100 ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

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

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerID = 100  OR  Container.containerID = 101 ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

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

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerID = 100  OR  Container.containerID = 101  OR  Container.containerID = 102 ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<ContainerEntity> cList = new ArrayList<ContainerEntity>();

        // Add One ContainerID ....
        ContainerEntity One = new ContainerEntity();
        One.setContainerName("Pen_One");
        cList.add(One);

        mq.setPenName(cList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerName = 'Pen_One' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

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

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerName = 'Pen_One'  OR  Container.containerName = 'Pen_Two' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMousePenID(true);
        mq.setSelectMouseStrain(true);

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

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  PenID ,  PenName)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Container.containerID as PenID , Container.containerName as PenName\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Container On Container.`_container_key` = Mouse.`_pen_key` \n\nWhere \n(( Container.containerName = 'Pen_One'  OR  Container.containerName = 'Pen_Two'  OR  Container.containerName = 'Pen_Three' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testProtocol_one() throws ParseException {
        System.out.println("Protocol_one");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseProtocolID(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseProtocolEntity> pList = new ArrayList<CvMouseProtocolEntity>();

        // Add One  ....
        CvMouseProtocolEntity One = new CvMouseProtocolEntity();
        One.setId("Protocol_One");
        pList.add(One);

        mq.setProtocolID(pList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Protocol)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.protocol as Protocol\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.protocol = 'Protocol_One' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testProtocol_two() throws ParseException {
        System.out.println("Protocol_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseProtocolID(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseProtocolEntity> pList = new ArrayList<CvMouseProtocolEntity>();

        // Add One  ....
        CvMouseProtocolEntity One = new CvMouseProtocolEntity();
        One.setId("Protocol_One");
        pList.add(One);

        // Add Two  ....
        CvMouseProtocolEntity Two = new CvMouseProtocolEntity();
        Two.setId("Protocol_Two");
        pList.add(Two);

        mq.setProtocolID(pList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Protocol)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.protocol as Protocol\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.protocol = 'Protocol_One'  OR  Mouse.protocol = 'Protocol_Two' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }
    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testProtocol_three() throws ParseException {
        System.out.println("Protocol_three");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseProtocolID(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseProtocolEntity> pList = new ArrayList<CvMouseProtocolEntity>();

        // Add One  ....
        CvMouseProtocolEntity One = new CvMouseProtocolEntity();
        One.setId("Protocol_One");
        pList.add(One);

        // Add Two  ....
        CvMouseProtocolEntity Two = new CvMouseProtocolEntity();
        Two.setId("Protocol_Two");
        pList.add(Two);
        
        // Add Three  ....
        CvMouseProtocolEntity Three = new CvMouseProtocolEntity();
        Three.setId("Protocol_Three");
        pList.add(Three);


        mq.setProtocolID(pList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Protocol)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Mouse.protocol as Protocol\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n\nWhere \n(( Mouse.protocol = 'Protocol_One'  OR  Mouse.protocol = 'Protocol_Two'  OR  Mouse.protocol = 'Protocol_Three' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseUse_one() throws ParseException {
        System.out.println("MouseUse_one");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseUse(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseUseEntity> muList = new ArrayList<CvMouseUseEntity>();

        // Add One  ....
        CvMouseUseEntity One = new CvMouseUseEntity();
        One.setMouseUse("MouseUse_one");
        muList.add(One);

        mq.setMouseUse(muList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  MouseUse)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , MouseUsage.use as MouseUse\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \n\nWhere \n(( MouseUsage.use = 'MouseUse_one' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseUse_two() throws ParseException {
        System.out.println("MouseUse_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseUse(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseUseEntity> muList = new ArrayList<CvMouseUseEntity>();

        // Add One  ....
        CvMouseUseEntity One = new CvMouseUseEntity();
        One.setMouseUse("MouseUse_one");
        muList.add(One);

        // Add Two  ....
        CvMouseUseEntity Two = new CvMouseUseEntity();
        Two.setMouseUse("MouseUse_Two");
        muList.add(Two);

        mq.setMouseUse(muList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  MouseUse)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , MouseUsage.use as MouseUse\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \n\nWhere \n(( MouseUsage.use = 'MouseUse_one'  OR  MouseUsage.use = 'MouseUse_Two' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testMouseUse_three() throws ParseException {
        System.out.println("MouseUse_three");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseUse(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<CvMouseUseEntity> muList = new ArrayList<CvMouseUseEntity>();

        // Add One  ....
        CvMouseUseEntity One = new CvMouseUseEntity();
        One.setMouseUse("MouseUse_one");
        muList.add(One);

        // Add Two  ....
        CvMouseUseEntity Two = new CvMouseUseEntity();
        Two.setMouseUse("MouseUse_Two");
        muList.add(Two);

        // Add Three  ....
        CvMouseUseEntity Three = new CvMouseUseEntity();
        Three.setMouseUse("MouseUse_Three");
        muList.add(Three);


        mq.setMouseUse(muList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  MouseUse)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , MouseUsage.use as MouseUse\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \n\nWhere \n(( MouseUsage.use = 'MouseUse_one'  OR  MouseUsage.use = 'MouseUse_Two'  OR  MouseUsage.use = 'MouseUse_Three' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGene_one() throws ParseException {
        System.out.println("Gene_one");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<GeneEntity> gList = new ArrayList<GeneEntity>();

        // Add One  ....
        GeneEntity One = new GeneEntity();
        One.setGeneSymbol("Gene_one");
        gList.add(One);

        mq.setGenotype(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n(( Gene.geneSymbol = 'Gene_one' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGene_two() throws ParseException {
        System.out.println("Gene_two");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<GeneEntity> gList = new ArrayList<GeneEntity>();

        // Add One  ....
        GeneEntity One = new GeneEntity();
        One.setGeneSymbol("Gene_one");
        gList.add(One);

        // Add Two  ....
        GeneEntity Two = new GeneEntity();
        Two.setGeneSymbol("Gene_Two");
        gList.add(Two);

        mq.setGenotype(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n(( Gene.geneSymbol = 'Gene_one'  OR  Gene.geneSymbol = 'Gene_Two' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGene_three() throws ParseException {
        System.out.println("Gene_three");
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseStrain(true);

        //  Filters
        ArrayList<GeneEntity> gList = new ArrayList<GeneEntity>();

        // Add One  ....
        GeneEntity One = new GeneEntity();
        One.setGeneSymbol("Gene_one");
        gList.add(One);

        // Add Two  ....
        GeneEntity Two = new GeneEntity();
        Two.setGeneSymbol("Gene_Two");
        gList.add(Two);

        // Add Three  ....
        GeneEntity Three = new GeneEntity();
        Three.setGeneSymbol("Gene_Three");
        gList.add(Three);

        mq.setGenotype(gList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n(( Gene.geneSymbol = 'Gene_one'  OR  Gene.geneSymbol = 'Gene_Two'  OR  Gene.geneSymbol = 'Gene_Three' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }


    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDate_Between() throws java.text.ParseException  {
        System.out.println("GenotypeDate_Between");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseGenotypeDate(true);
        mq.setSelectMouseStrain(true);

        // Date filters
        Date Start;
        Date End;


        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        Start = (Date)formatter.parse(str_date);
        str_date="2010-10-01";
        End = (Date)formatter.parse(str_date);

        mq.setGenotypeStartDate(Start);
        mq.setGenotypeEndDate(End);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2 ,  GenotypeDate)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2 , Genotype.gtDate as GenotypeDate\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n((Genotype.gtDate Between '2010-06-01'  AND '2010-10-01'));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDate_StartToNow() throws ParseException {
        System.out.println("GenotypeDate_StartToNow");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseGenotypeDate(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date Start;

        String str_date="2010-06-01";
        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        Start = (Date)formatter.parse(str_date);
        mq.setGenotypeStartDate(Start);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2 ,  GenotypeDate)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2 , Genotype.gtDate as GenotypeDate\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n((Genotype.gtDate Between '2010-06-01'  AND CURDATE()));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

    /***************************************************************************
     * Test of query method, of class MouseQueryDAO.
     */
    @Test
    public void testGenotypeDate_NowToEnd() throws ParseException {
        System.out.println("GenotypeDate_NowToEnd");
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseGenotype(true);
        mq.setSelectMouseGenotypeDate(true);
        mq.setSelectMouseStrain(true);

        // DateOfBirth filters
        Date End;

        DateFormat formatter ;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        String str_date="2015-10-01";
        End = (Date)formatter.parse(str_date);
        mq.setGenotypeEndDate(End);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Strain ,  Gene ,  Allele1 ,  Allele2 ,  GenotypeDate)\nSelect \nMouse.ID as MouseID , Strain.strainName as Strain , Gene.geneSymbol as Gene , Genotype.allele1 as Allele1 , Genotype.allele2 as Allele2 , Genotype.gtDate as GenotypeDate\nFrom \nMouse \nLeft Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \nLeft Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \nLeft Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n\nWhere \n((Genotype.gtDate Between CURDATE()  AND '2015-10-01'));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOwner(true);


        //  Filters
        ArrayList<OwnerEntity> oList = new ArrayList<OwnerEntity>();

        // Add One  ....
        OwnerEntity One = new OwnerEntity();
        One.setOwner("Owner_one");
        oList.add(One);

        mq.setOwner(oList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Owner)\nSelect \nMouse.ID as MouseID , Mouse.owner as Owner\nFrom \nMouse \n\nWhere \n(( Mouse.owner = 'Owner_one' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOwner(true);


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

        mq.setOwner(oList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Owner)\nSelect \nMouse.ID as MouseID , Mouse.owner as Owner\nFrom \nMouse \n\nWhere \n(( Mouse.owner = 'Owner_one'  OR  Mouse.owner = 'Owner_Two' ));\n";

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
        MouseQueryDTO mq = new MouseQueryDTO();

        // Select
        mq.setSelectMouseID(true);
        mq.setSelectMouseOwner(true);


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

        mq.setOwner(oList);

        MouseQueryDAO instance = new MouseQueryDAO();
        String expResults = "Insert INTO MyTemp ( \n MouseID ,  Owner)\nSelect \nMouse.ID as MouseID , Mouse.owner as Owner\nFrom \nMouse \n\nWhere \n(( Mouse.owner = 'Owner_one'  OR  Mouse.owner = 'Owner_Two'  OR  Mouse.owner = 'Owner_Three' ));\n";

        String result[] = instance.generateSQLQuery(mq);
        // TestHelper.printTestStrings(expResults,result[1]);
        assertEquals(expResults, result[1]);

    }

}