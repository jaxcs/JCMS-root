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

package jcms.integrationtier.dto;

import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author bas
 */
public class ExpDataTestTypeListDTO extends ITBaseDTO {
    private String expDataKey = "";
    private String testTypeKey = "";
    //This list used in order to directly provide the testtype information associated with the 30 dx fields
    private List<ExpDataTestTypeAndResultsDTO> ttWithDataResult = new ArrayList<ExpDataTestTypeAndResultsDTO>();
    
    public ExpDataTestTypeListDTO() {
        
    }

    /**
     * @return the expDataKey
     */
    public String getExpDataKey() {
        return expDataKey;
    }

    /**
     * @param expDataKey the expDataKey to set
     */
    public void setExpDataKey(String expDataKey) {
        this.expDataKey = expDataKey;
    }

    /**
     * @return the testTypeKey
     */
    public String getTestTypeKey() {
        return testTypeKey;
    }

    /**
     * @param testTypeKey the testTypeKey to set
     */
    public void setTestTypeKey(String testTypeKey) {
        this.testTypeKey = testTypeKey;
    }

    /**
     * @return the ttWithDataResult
     */
    public List<ExpDataTestTypeAndResultsDTO> getTtWithDataResult() {
        return ttWithDataResult;
    }

    /**
     * @param ttWithDataResult the ttWithDataResult to set
     */
    public void setTtWithDataResult(List<ExpDataTestTypeAndResultsDTO> ttWithDataResult) {
        this.ttWithDataResult = ttWithDataResult;
    }
}
