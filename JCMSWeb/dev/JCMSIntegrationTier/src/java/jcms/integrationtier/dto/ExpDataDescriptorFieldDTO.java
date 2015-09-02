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

import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class ExpDataDescriptorFieldDTO extends ITBaseDTO {
    public String caption ; 
    public String fieldDescription ; 
    public String dataFormat ; 
    public Boolean required = false; 
    public String minValue ; 
    public String maxValue ; 
    
    public static final String DATA_TYPE_DATE = "date";
    public static final String DATA_TYPE_DECIMAL = "dec";
    public static final String DATA_TYPE_INTEGER = "int";
    public static final String DATA_TYPE_TEXT = "text";
    
    public ExpDataDescriptorFieldDTO() {
        super();
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof ExpDataDescriptorFieldDTO) {
            ExpDataDescriptorFieldDTO dto = (ExpDataDescriptorFieldDTO) object;
            if (getCaption().equalsIgnoreCase(dto.getCaption())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the fieldDescription
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * @param fieldDescription the fieldDescription to set
     */
    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * @return the dataFormat
     */
    public String getDataFormat() {
        return dataFormat;
    }

    /**
     * @param dataFormat the dataFormat to set
     */
    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    /**
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
        * @return the minValue
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the maxValue
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    // Transient
    public Float getMinFloat() {
        if (minValue == null || minValue.trim().length() == 0)
            return null;
        else 
            return Float.parseFloat(minValue);
    }
    
    // Transient
    public Float getMaxFloat() {
        if (maxValue == null || maxValue.trim().length() == 0)
            return null;
        else 
            return Float.parseFloat(maxValue);
    }
}
