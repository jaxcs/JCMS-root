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

import jcms.integrationtier.base.ITBaseObject;

/**
 * <b>File name:</b> FilterDTO.java  <p>
 * <b>RsDate developed:</b> April 2010 <p>
 * <b>Purpose:</b> Contains values to filter by and specific filter properties to set.  <br />
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */  
public class FilterDTO extends ITBaseObject
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private Boolean  caseSensitiveMode   = Boolean.TRUE;

    // Filter by, values
    private String  firstNameValue  = "";
    private String  lastNameValue   = "";
    private String  emailValue      = "";

    /**
     * <b>Purpose:</b> Constructor loads procedure vocabulary - lookup data <br />
     */
    public FilterDTO()
    {
    }

    // GETTERS AND SETTERS
    
    /**
     * @return the caseSensitiveMode
     */
    public Boolean getCaseSensitiveMode() {
        return caseSensitiveMode;
    }

    /**
     * @param caseSensitiveMode the caseSensitiveMode to set
     */
    public void setCaseSensitiveMode(Boolean caseSensitiveMode) {
        this.caseSensitiveMode = caseSensitiveMode;
    }

    /**
     * @return the lastNameValue
     */
    public String getLastNameValue() {
        return lastNameValue;
    }

    /**
     * @param lastNameValue the lastNameValue to set
     */
    public void setLastNameValue(String lastNameValue) {
        this.lastNameValue = lastNameValue;
    }

    /**
     * @return the firstNameValue
     */
    public String getFirstNameValue() {
        return firstNameValue;
    }

    /**
     * @param firstNameValue the firstNameValue to set
     */
    public void setFirstNameValue(String firstNameValue) {
        this.firstNameValue = firstNameValue;
    }

    /**
     * @return the emailValue
     */
    public String getEmailValue() {
        return emailValue;
    }

    /**
     * @param emailValue the emailValue to set
     */
    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }



}
