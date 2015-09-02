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

package jcms.integrationtier.cv;

import java.util.Iterator;
import java.util.List;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cnh
 */
public class CvEntityTest
{
    public CvEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp()    
    {
        //systemFacade    = new SystemDao().getSystemFacadeLocal();
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void testFind() {
//        System.out.println("find");
//
//        DbInfoEntity entity = (DbInfoEntity) systemFacade.baseFind(new DbInfoEntity(key));
//        entity.printDetail();
//    }

//    @Test
//    public void testCreate()
//    {
//        System.out.println("create");
//
//        DbInfoEntity eFound = (DbInfoEntity) systemFacade.baseFind(new DbInfoEntity(key));
//        newEntity.setDbinfoKey(eFound.getDbinfoKey() + 1);
//
//        try {
//            systemFacade.baseCreate(newEntity);
//        } catch ( DuplicateNameException dne ) {
//            System.out.println( "DuplicateNameException: " + dne.getMessage() );
//        }
//        newEntity.printDetail();
//    }
//
//    @Test
//    public void testEdit() {
//        System.out.println("edit");
//
//        DbInfoEntity eFound = (DbInfoEntity) systemFacade.baseFind(new DbInfoEntity(newKey));
//
//        Integer major = eFound.getMajorVersion();
//        eFound.setMajorVersion(major + 50);
//        try {
//            systemFacade.baseEdit(eFound);
//        } catch (SaveEntityException ex) {
//            Logger.getLogger(CvEntityTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch ( DuplicateNameException dne ) {
//            System.out.println( "DuplicateNameException: " + dne.getMessage() );
//        }
//        eFound.printDetail();
//    }

    /*@Test
    public void testFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                baseFindAll(new CvGenerationEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvGenerationEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }

    @Test
    public void testBreedingStatusFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                baseFindAll(new CvBreedingStatusEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvBreedingStatusEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }*/

    /*@Test
    public void testMouseIDFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new MouseEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<MouseEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }*/

    /*@Test
    public void testLitterIDFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new LitterEntity(), "rama");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<LitterEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }

    @Test
    public void testPenFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                baseFindAllByOwner(new ContainerEntity(), "kavitha");

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<ContainerEntity> it = result.iterator();

        while (it.hasNext())
        {
            it.next().printDetail();
        }
    }*/

    /*@Test
    public void testStrainFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new StrainEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<StrainEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }*/

    /*@Test
    public void testLitterFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new LitterEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<LitterEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }*/

    @Test
    public void testOwnerFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new OwnerEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        /*List result = new IntegrationTierPortal().getSystemFacadeRemote().
                baseFindAllByOwner(new OwnerEntity(), "kavitha");*/

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<OwnerEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }

    /*@Test
    public void testContainerFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new ContainerEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<ContainerEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }*/

    /*@Test
    public void testCvMouseProtocolFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new CvMouseProtocolEntity(), "rama");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvMouseProtocolEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }

    @Test
    public void testCvMouseUseFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new CvMouseUseEntity(), "kavitha");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<CvMouseUseEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }*/

    /*@Test
    public void testGeneFindAll() {
        System.out.println("findAll");

        List result = new SystemDao().getSystemFacadeRemote().
                //baseFindByOwner(new MouseEntity(), "kavitha");
                baseFindAllByOwner(new GeneEntity(), "rama");
                //baseFindAll(new MouseEntity());

        //List result = systemFacade.baseFindAll(new CvGenerationEntity());
        Iterator<GeneEntity> it = result.iterator();
        int cnt = 0;

        while (it.hasNext())
        {
            cnt++;
            it.next().printDetail();
        }
        System.out.println("cnt " + cnt);
    }*/

//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        try {
//            systemFacade.baseDelete(new DbInfoEntity(newKey));
//        } catch (DeleteEntityException ex) {
//            Logger.getLogger(CvEntityTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("\tSuccess");
//    }


}