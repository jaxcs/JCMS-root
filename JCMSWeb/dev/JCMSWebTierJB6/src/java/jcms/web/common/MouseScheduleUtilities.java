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

package jcms.web.common;

import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dao.UseScheduleDAO;
import jcms.integrationtier.dto.UseScheduleDTO;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.dto.PlugDateDTO;

/**
 *
 * @author mkamato
 */
public class MouseScheduleUtilities {
    
    private UseScheduleDAO pDAO = new UseScheduleDAO();
    
    public void addMouseToCalendarUseSchedule(UseScheduleTermDTO dto, Integer mouseKey) throws Exception{
        
    }
    
    public void addMouseToBirthDateUseSchedule(UseScheduleTermDTO useScheduleTerm, MouseDTO mouse) throws Exception{
        //only proceed if mouse is not in use schedule already...
        if(useScheduleTerm != null && mouse != null){
            UseScheduleDTO spDTO = new UseScheduleDTO();
            spDTO.setMouse(mouse);
            spDTO.setUseScheduleTerm(useScheduleTerm);
            spDTO.setStartDate(mouse.getBirthDate());
            if(!pDAO.mouseInUseSchedule(spDTO)){
                //add mouse to the use schedule by adding 'UseSchedule' entry
                getpDAO().insertUseSchedule(spDTO, null);
            }
        }
    }
    
    public void addMouseToPlugdateUseSchedule(UseScheduleTermDTO useScheduleTerm, MouseDTO mouse, PlugDateDTO plug) throws Exception{
        if(useScheduleTerm != null &&mouse != null && plug != null){            
            //add mouse to the use schedule by adding 'UseSchedule' entry
            UseScheduleDTO spDTO = new UseScheduleDTO();
            spDTO.setMouse(mouse);
            spDTO.setUseScheduleTerm(useScheduleTerm);
            spDTO.setStartDate(plug.getPlugDate());
            getpDAO().insertUseSchedule(spDTO, plug);
        }
    }
    
    /**
     * @return the pDAO
     */
    public UseScheduleDAO getpDAO() {
        return pDAO;
    }

    /**
     * @param pDAO the pDAO to set
     */
    public void setpDAO(UseScheduleDAO pDAO) {
        this.pDAO = pDAO;
    }
}
