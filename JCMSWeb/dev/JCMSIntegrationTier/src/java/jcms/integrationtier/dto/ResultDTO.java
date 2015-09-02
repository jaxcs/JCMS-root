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

import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author springer
 */
public class ResultDTO {

    private Result result = null;
    private String errorMessage = "";


    public ResultDTO () {
    }

    public void setResult(Result setResult) {
        this.result = setResult;
    }

    public Result getResult() {
        return this.result;
    }

    public void setMessage(String message) {
        this.errorMessage = message;
    }

    public String getMessage() {
        return this.errorMessage;
    }

    public boolean isMessageToDisplay() {

        if ( 0 != this.errorMessage.compareTo("")) {
             return true;
        } else {
             return false;
        }

    }

    
}
