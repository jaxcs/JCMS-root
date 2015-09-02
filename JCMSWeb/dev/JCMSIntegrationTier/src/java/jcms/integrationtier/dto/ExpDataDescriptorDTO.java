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
 * @author cnh
 */
public class ExpDataDescriptorDTO extends ITBaseDTO {
    private int expDataDescriptor_key ;    
    private String testType ; 
    private String testTypeNotes ; 
    private int version = 0;
    private String delimitedFields = "";
    private int expDataCount = 0;
    private int expTestCount = 0;
    private List<ExpDataDescriptorFieldDTO> fields = new ArrayList<ExpDataDescriptorFieldDTO>() ;

    public ExpDataDescriptorDTO() {
        
    }
    
    public int getNumberOfFields() {
        return fields.size();
    }
    
    public String getDelimitedFields() {
        String df = "";
        for (ExpDataDescriptorFieldDTO dto : fields) {
            df += (df.isEmpty() ? "": ", ") + dto.getCaption();
        }
        return df;
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof ExpDataDescriptorDTO) {
            ExpDataDescriptorDTO dto = (ExpDataDescriptorDTO) object;
            if (getExpDataDescriptor_key() == (dto.getExpDataDescriptor_key())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @return the testType
     */
    public String getTestType() {
        return testType;
    }

    /**
     * @param testType the testType to set
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * @return the testTypeNotes
     */
    public String getTestTypeNotes() {
        return testTypeNotes;
    }

    /**
     * @param testTypeNotes the testTypeNotes to set
     */
    public void setTestTypeNotes(String testTypeNotes) {
        this.testTypeNotes = testTypeNotes;
    }

    /**
     * @return the fields
     */
    public List<ExpDataDescriptorFieldDTO> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<ExpDataDescriptorFieldDTO> fields) {
        this.fields = fields;
    }

    /**
     * @return the expDataDescriptor_key
     */
    public int getExpDataDescriptor_key() {
        return expDataDescriptor_key;
    }

    /**
     * @param expDataDescriptor_key the expDataDescriptor_key to set
     */
    public void setExpDataDescriptor_key(int expDataDescriptor_key) {
        this.expDataDescriptor_key = expDataDescriptor_key;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return the expDataCount
     */
    public int getExpDataCount() {
        return expDataCount;
    }

    /**
     * @param expDataCount the expDataCount to set
     */
    public void setExpDataCount(int expDataCount) {
        this.expDataCount = expDataCount;
    }

    /**
     * @return the expTestCount
     */
    public int getExpTestCount() {
        return expTestCount;
    }

    /**
     * @param expTestCount the expTestCount to set
     */
    public void setExpTestCount(int expTestCount) {
        this.expTestCount = expTestCount;
    }
}
