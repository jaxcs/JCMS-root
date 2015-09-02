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
 * @author bas
 */
public class ExpDataTestTypeAndResultsDTO extends ITBaseDTO {
    private String caption ; 
    private String fieldDescription ; 
    private String dataFormat ; 
    private Boolean required ; 
    private String minValue ; 
    private String maxValue ; 
    private String d_Value ;
    private String d_Number;
    
    public ExpDataTestTypeAndResultsDTO() {

    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof ExpDataTestTypeAndResultsDTO) {
            ExpDataTestTypeAndResultsDTO dto = (ExpDataTestTypeAndResultsDTO) object;
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

    /**
     * @return the d_Value
     */
    public String getD_Value() {
        return d_Value;
    }

    /**
     * @param d_Value the d_Value to set
     */
    public void setD_Value(String d_Value) {
        this.d_Value = d_Value;
    }

    /**
     * @return the d_Number
     */
    public String getD_Number() {
        return d_Number;
    }

    /**
     * @param d_Number the d_Number to set
     */
    public void setD_Number(String d_Number) {
        this.d_Number = d_Number;
    }

}
