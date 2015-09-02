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

package jcms.web.colonyManagement;

import jcms.web.base.WTBaseObject;

/**
 *
 * @author rkavitha
 */
public class Mating extends WTBaseObject {
    
    // HTTP PROTOCOL METHODS

    /**
     * <b>Purpose:</b> The purpose is to return the user primary key
     *      from the http request parameter.  <br />
     * <b>Overview:</b> The add and edit action from the list view places the
     *      primary key in the http request as a parameter.  <br />
     * @return Integer valid primary key or null to create a new user
     */
    public Integer getKeyFromRequest()
    {
        Integer key = this.getKey("paramMatingKey");

        return key;
    }    
    
}
