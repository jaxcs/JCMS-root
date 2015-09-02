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

package jcms.web.dto;

import jcms.integrationtier.dto.MouseUsageDTO;
import java.util.Date;

/**
 *
 * @author mkamato
 */
public class PlugDateHelperDTO {
    
    private String DPC = "";
    private MouseUsageDTO dto = new MouseUsageDTO();
    private Date projectedDate = null;

    /**
     * @return the DPC
     */
    public String getDPC() {
        return DPC;
    }

    /**
     * @param DPC the DPC to set
     */
    public void setDPC(String DPC) {
        this.DPC = DPC;
    }

    /**
     * @return the dto
     */
    public MouseUsageDTO getDto() {
        return dto;
    }

    /**
     * @param dto the dto to set
     */
    public void setDto(MouseUsageDTO dto) {
        this.dto = dto;
    }

    /**
     * @return the projectedDate
     */
    public Date getProjectedDate() {
        return projectedDate;
    }

    /**
     * @param projectedDate the projectedDate to set
     */
    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
    }
}
