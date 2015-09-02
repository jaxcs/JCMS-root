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
public class ExpDataResultsDTO extends ITBaseDTO {
    public String d_Result;
    
    public ExpDataResultsDTO() {
        super();
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof ExpDataResultsDTO) {
            ExpDataResultsDTO dto = (ExpDataResultsDTO) object;
            if (getD_Result().equalsIgnoreCase(dto.getD_Result())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @return the d_Result
     */
    public String getD_Result() {
        return d_Result;
    }

    /**
     * @param d_Result the d_Result to set
     */
    public void setD_Result(String d_Result) {
        this.d_Result = d_Result;
    }
}
