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

package jcms.middletier.dto;

import java.io.Serializable;
import jcms.middletier.base.BTBaseObject;

/**
 * <b>File name:</b> ValidationMessageDTO.java  <p>
 * <b>RsDate developed:</b>  November 2009 <p>
 * <b>Purpose:</b> Contains error message details for data failing validation.  <p>
 * <b>Overview:</b> Use this object to pass meaningful error messages back to
 *      the web tier.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */
public class ValidationMessageDTO extends BTBaseObject implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String  fieldName       = ""; 
    private Integer primaryKey      = 0;
    private String  errorMessage    = "";

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the primaryKey
     */
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
