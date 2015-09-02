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
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.facade.FacadeDao;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author rkavitha
 */
public class GenerateCSVFile {

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

    @Test
    public void generateCSV() throws SQLException {
        String sFileName = "c:\\temp_csv\test.csv";

        System.out.println("find mouse");
/// begin dave
        MouseQueryDTO mq = new MouseQueryDTO();

        // All select
        mq.setSelectMouseID(true);
        mq.setSelectMouseLifeStatus(true);
        mq.setSelectMouseGeneration(true);
/// end dave

    }    
}