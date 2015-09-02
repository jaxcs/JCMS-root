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

package jcms.middletier.businessobject;

import java.sql.SQLException;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.dao.ContainerHistoryDAO;
import jcms.integrationtier.dto.ContainerHistoryDTO;

/**
 *
 * @author cnh
 */
public class ContainerHistoryBO {
    
    public ContainerEntity getContainerEntityByContainerKey(Integer containerKey) throws SQLException {
        return new ContainerHistoryDAO().getContainerEntityByContainerKey(containerKey);
    }
    public RoomEntity getRoomEntityByContainerKey(Integer containerKey) throws SQLException {
        return new ContainerHistoryDAO().getRoomEntityByContainerKey(containerKey);
    }
    
    public int UpdateContainerHistory(ContainerHistoryDTO dto) throws SQLException {
        return new ContainerHistoryDAO().UpdateContainerHistory(dto);
    }
}
