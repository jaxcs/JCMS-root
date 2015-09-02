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

package jcms.businesstier.facade;

import java.sql.SQLException;
import jcms.middletier.facade.FacadeDao;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvGeneclassEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.portal.IntegrationTierPortal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cnh
 */
public class VocabularyFacadeTest
{
    public VocabularyFacadeTest() {
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

    /*@Test
    public void testHowLongToUpdateAProject()
    {
        ProjectEntity pe = null;

        SystemFacadeRemote facade = new IntegrationTierPortal().getSystemFacadeRemote();
        pe = (ProjectEntity) facade.findAndForceLazyLoad(new ProjectEntity(5));

        pe.printDetail();

        pe.setProjectGoal("Test 18");

        try 
        {
//            ProjectFacadeRemote projectFacade = new IntegrationTierPortal().getProjectFacadeRemote();
            facade.baseSave(pe);
        }

        catch (SaveEntityException ex)
        {
            Logger.getLogger(VocabularyFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (DuplicateNameException ex)
        {
            Logger.getLogger(VocabularyFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        pe.printDetail();
    }*/

    @Test
    public void testFindAllGenerations()
    {
        System.out.println("findAll");

        //List result = new IntegrationTierPortal().getSystemFacadeRemote().
          //      baseFindAll(new CvGenerationEntity());

        List result = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new CvGenerationEntity());

        //List result = new FacadeDao().getVocabularyFacadeRemote()
               // .getControlledVocabulary().getCvGeneration();
        //List<CvGenerationEntity> result = new ListSupportDTO().getCvGeneration();

        System.out.println("Number of generations "+ result.size());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvGenerationEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }

    @Test
    public void testFindAllBreedingStatus()
    {
        System.out.println("findAll");

        //List result = new IntegrationTierPortal().getSystemFacadeRemote().
          //      baseFindAll(new CvGenerationEntity());

        List result = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new CvBreedingStatusEntity());

        //List result = new FacadeDao().getVocabularyFacadeRemote()
               // .getControlledVocabulary().getCvGeneration();
        //List<CvBreedingStatusEntity> result = new ListSupportDTO().getCvBreedingStatus();

        System.out.println("Number of generations "+ result.size());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvBreedingStatusEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }

    @Test
    public void testFindAllGeneClasses()
    {
        System.out.println("findAll");

        //List result = new IntegrationTierPortal().getSystemFacadeRemote().
          //      baseFindAll(new CvGenerationEntity());

        List result = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new CvGeneclassEntity());

        //List result = new FacadeDao().getVocabularyFacadeRemote()
                //.getControlledVocabulary().getCvGeneclass();
        //List<CvGenerationEntity> result = new ListSupportDTO().getCvGeneration();

        System.out.println("Number of generations "+ result.size());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvGeneclassEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }

    @Test
    public void findMouseJDBC() throws SQLException
    {
        System.out.println("find mouse");

        //List result = new IntegrationTierPortal().getSystemFacadeRemote().
          //      baseFindAll(new CvGenerationEntity());

        //Result rset = new FacadeDao().getServiceFacadeRemote().findMouseJDBC();
/// begin dave
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseGeneration(true);
/// end dave
        ResultDTO rset = new IntegrationTierPortal().getSystemFacadeRemote().
                findMouseQueryJDBC(mq);
    }

    @Test
    public void testFindAllPens()
    {
        System.out.println("findAll");

        //List result = new IntegrationTierPortal().getSystemFacadeRemote().
          //      baseFindAll(new CvGenerationEntity());

        List result = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new ContainerEntity());

        //List result = new FacadeDao().getVocabularyFacadeRemote()
                //.getControlledVocabulary().getCvGeneclass();
        //List<CvGenerationEntity> result = new ListSupportDTO().getCvGeneration();

        System.out.println("Number of pens "+ result.size());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<ContainerEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }


//    @Test
//    public void testFindDataLoadProcessByName()
//    {
//        DataLoadProcessEntity e = new DataLoadProcessEntity();
//        e.setDataLoadProcess("New");
//
//        facade = new BusinessTierPortal().getRepositoryFacadeRemote();
//        facade.findDataLoadProcessByName(e.getDataLoadProcess());
//    }

//    @Test
//    public void testFindAllDataLoadProcesses()
//    {
//        facade = new BusinessTierPortal().getRepositoryFacadeRemote();
//        List<DataLoadProcessEntity> list = facade.findAllDataLoadProcesses();
//
//        for (DataLoadProcessEntity e: list)
//        {
//            e.printDetail();
//        }
//    }


}