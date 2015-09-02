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

import javax.servlet.jsp.jstl.sql.Result;
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
public class SearchDAOTest {

    public SearchDAOTest() {
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

//    @Test
//    public void testFindAllLineJDBC()
//    {
//        System.out.println("Test JDBC Searching");
//
//        SearchJDBC search = new SearchJDBC();
//        ITBaseEntityTable table = search.getLines();
//
//        System.out.println("Number of LineEntity is "+ table.getList().size());
//
//    }

    /*@Test
    public void testConnectionJDBC()
    {
    System.out.println("Test JDBC Connection");

    SearchJDBC search = new SearchJDBC();
    search.testConnection();
    }*/
    @Test
    public void testFindMouseJDBC() throws Exception {
        IntegrationTierPortal it = new IntegrationTierPortal();
        Result result = null;

        try {
            System.out.println("Test JDBC Searching");

            //result = new SystemDao().getSystemFacadeRemote().findMouseJDBC();
          //  result = it.getSystemFacadeRemote().findMouseJDBC();

            /*while (myResultSet.next()) {
                int ncols = myResultSet.getMetaData().getColumnCount();
                FileOutputStream fos = new FileOutputStream(new File("C:\\mouseQuery.csv"), false);
                Writer out = new OutputStreamWriter(new BufferedOutputStream(fos));

                for (int i = 1; i < (ncols + 1); i++) {
                    out.append(myResultSet.getMetaData().getColumnName(i));
                    if (i < ncols) {
                        out.append(",");
                    } else {
                        out.append("\r\n");
                    }
                }
                while (myResultSet.next()) {
                    for (int i = 1; i < (ncols + 1); i++) {
                        out.append(myResultSet.getString(i));
                        if (i < ncols) {
                            out.append(",");
                        } else {
                            out.append("\r\n");
                        }
                    }
                }
            }*/

        }
        catch (Exception e) {
        }
    }
}