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

import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.ExpQueryResultsDTO;

/**
 *
 * @author bas
 */
public class ExpQueryDAO extends MySQLDAO {
    
    public ExpQueryDAO() {
        
    }
    
    public ArrayList<ExpQueryResultsDTO> getSearchResults (String searchString) {
        ArrayList<ExpQueryResultsDTO> dtoList = new ArrayList<ExpQueryResultsDTO> ();
        ExpQueryResultsDTO dto = null;
        Result result = executeJCMSWebQuery(searchString);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createExpQueryResultsDTO(row);
                
                dtoList.add(dto);
            }
        }
        
        return dtoList;
    }
    
    //Watch out, the myGet must use the exact name used by the database
    private ExpQueryResultsDTO createExpQueryResultsDTO (SortedMap row) {
        ExpQueryResultsDTO dto = new ExpQueryResultsDTO();
        dto.setTestType(myGet("testType", row));
        dto.setDataID(myGet("dataID", row));
        dto.setDataOwner(myGet("owner", row));  //It has to be owner, not an alias
        dto.setMouseOwner(myGet("owner", row)); //There is a MySQL bug that prevents this from working the way you would expect!
        dto.setExpDate(myGet("expDate", row));
        dto.setAge(myGet("age", row));
        dto.setAbnormalData(myGet("abnormalData", row));
        dto.setMouseID(myGet("ID", row));
        dto.setStrain(myGet("strainName", row));
        dto.setJrNum(myGet("jrNum", row));
        dto.setLifeStatus(myGet("lifeStatus", row));
        dto.setGeneration(myGet("generation", row));
        dto.setBirthDate(myGet("birthDate", row));
        dto.setSex(myGet("sex", row));
        dto.setCauseOfDeath(myGet("cod", row));
        dto.setContainerID(myGet("containerID", row));
        dto.setContainerName(myGet("containerName", row));
        dto.setMouseComments(myGet("comment", row));
        dto.setD1(myGet("d1", row));
        dto.setD2(myGet("d2", row));
        dto.setD3(myGet("d3", row));
        dto.setD4(myGet("d4", row));
        dto.setD5(myGet("d5", row));
        dto.setD6(myGet("d6", row));
        dto.setD7(myGet("d7", row));
        dto.setD8(myGet("d8", row));
        dto.setD9(myGet("d9", row));
        dto.setD10(myGet("d10", row));
        dto.setD11(myGet("d11", row));
        dto.setD12(myGet("d12", row));
        dto.setD13(myGet("d13", row));
        dto.setD14(myGet("d14", row));
        dto.setD15(myGet("d15", row));
        dto.setD16(myGet("d16", row));
        dto.setD17(myGet("d17", row));
        dto.setD18(myGet("d18", row));
        dto.setD19(myGet("d19", row));
        dto.setD20(myGet("d20", row));
        dto.setD21(myGet("d21", row));
        dto.setD22(myGet("d22", row));
        dto.setD23(myGet("d23", row));
        dto.setD24(myGet("d24", row));
        dto.setD25(myGet("d25", row));
        dto.setD26(myGet("d26", row));
        dto.setD27(myGet("d27", row));
        dto.setD28(myGet("d28", row));
        dto.setD29(myGet("d29", row));
        dto.setD30(myGet("d30", row));
        dto.setD1_caption(myGet("d1_caption", row));
        dto.setD2_caption(myGet("d2_caption", row));
        dto.setD3_caption(myGet("d3_caption", row));
        dto.setD4_caption(myGet("d4_caption", row));
        dto.setD5_caption(myGet("d5_caption", row));
        dto.setD6_caption(myGet("d6_caption", row));
        dto.setD7_caption(myGet("d7_caption", row));
        dto.setD8_caption(myGet("d8_caption", row));
        dto.setD9_caption(myGet("d9_caption", row));
        dto.setD10_caption(myGet("d10_caption", row));
        dto.setD11_caption(myGet("d11_caption", row));
        dto.setD12_caption(myGet("d12_caption", row));
        dto.setD13_caption(myGet("d13_caption", row));
        dto.setD14_caption(myGet("d14_caption", row));
        dto.setD15_caption(myGet("d15_caption", row));
        dto.setD16_caption(myGet("d16_caption", row));
        dto.setD17_caption(myGet("d17_caption", row));
        dto.setD18_caption(myGet("d18_caption", row));
        dto.setD19_caption(myGet("d19_caption", row));
        dto.setD20_caption(myGet("d20_caption", row));
        dto.setD21_caption(myGet("d21_caption", row));
        dto.setD22_caption(myGet("d22_caption", row));
        dto.setD23_caption(myGet("d23_caption", row));
        dto.setD24_caption(myGet("d24_caption", row));
        dto.setD25_caption(myGet("d25_caption", row));
        dto.setD26_caption(myGet("d26_caption", row));
        dto.setD27_caption(myGet("d27_caption", row));
        dto.setD28_caption(myGet("d28_caption", row));
        dto.setD29_caption(myGet("d29_caption", row));
        dto.setD30_caption(myGet("d30_caption", row));
        return dto;
    }
}
