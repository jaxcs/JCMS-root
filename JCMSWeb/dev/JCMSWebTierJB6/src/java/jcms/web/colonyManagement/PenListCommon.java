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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import javax.faces.application.FacesMessage;
import javax.faces.model.ListDataModel;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.PenSearchDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.dto.PenInfoDTO;

/**
 *
 * @author rkavitha
 */
public class PenListCommon extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0323231L;
    private ReportFacadeLocal rfl;

    public PenListCommon() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
    }

    public ListDataModel penSearch(PenSearchDTO penSearch) {
        List<PenInfoDTO> penInfo = new ArrayList<PenInfoDTO>();
        ListDataModel penInfoDataModel = new ListDataModel();
        PenInfoDTO dto = null;
        Result result = null;
        
        try {
            result = rfl.findPenSearchResults(penSearch);
            SortedMap[] map = result.getRows();

            for (int j = 0; j < map.length; ++j) {
                dto = new PenInfoDTO();

                // get the room
                if (map[j].get("roomName") != null) {
                    dto.setRoom(map[j].get("roomName").toString());
                }

                // get the status
                if (map[j].get("containerStatus") != null) {
                    dto.setContainerStatus(map[j].get("containerStatus").toString());
                }

                // get the containerHistoryKey
                if (map[j].get("_containerHistory_key") != null) {
                    dto.setContainerHistoryKey((Integer) (map[j].get("_containerHistory_key")));
                }

                // get the containerKey
                if (map[j].get("_container_key") != null) {
                    dto.setContainerKey((Integer) map[j].get("_container_key"));
                }

                // get the room
                if (map[j].get("actionDate") != null) {
                    dto.setActionDate((Date) map[j].get("actionDate"));
                }

                // get the ContainerID
                if (map[j].get("containerID") != null) {
                    dto.setContainerID((Integer) map[j].get("containerID"));
                }

                // get the Container name
                if (map[j].get("ContainerName") != null) {
                    dto.setContainerName(map[j].get("ContainerName").toString());
                }
                penInfo.add(dto);
            }
            
            penInfoDataModel.setWrappedData(penInfo);
            getLogger().logDebug("PenSearch: " + penInfoDataModel.getRowCount() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return penInfoDataModel;
    }   
       
    /**
     * <b>Purpose:</b> Add newly selected pen.  <br />
     * <b>Overview:</b> Add newly selected pen.  <br />
     */
    public int getSelectedPen(ExtendedTableBean penSelectionETB, ListDataModel penInfoDataModel) {
        
        System.out.println("Selected Pen");
        int penID = 0;
        
        if (penSelectionETB != null) {
            List<PenInfoDTO> pensList = new ArrayList<PenInfoDTO>();
            pensList = (List<PenInfoDTO>) penInfoDataModel.getWrappedData();
            PenInfoDTO selectedPen = null;
            
            Iterator<Object> iterator = penSelectionETB.getSelection().iterator();  // Grab the indices of the rows to be added. NOTE: These are *just* the indices.
            
            // get the selected pen ID
            while ( iterator.hasNext() ) {
                int index = (Integer)iterator.next();
                selectedPen = pensList.get( index );
                break;
            }
            if (selectedPen != null) {
                penID = selectedPen.getContainerID();
            }
            System.out.println("ContainerID " + penID);
        }
        return penID;
    }
    public int getSelectedPenKey(ExtendedTableBean penSelectionETB, ListDataModel penInfoDataModel) {
        
        System.out.println("Selected Pen");
        int containerKey = 0;
        
        if (penSelectionETB != null) {
            List<PenInfoDTO> pensList = new ArrayList<PenInfoDTO>();
            pensList = (List<PenInfoDTO>) penInfoDataModel.getWrappedData();
            PenInfoDTO selectedPen = null;
            
            Iterator<Object> iterator = penSelectionETB.getSelection().iterator();  // Grab the indices of the rows to be added. NOTE: These are *just* the indices.
            
            // get the selected pen ID
            while ( iterator.hasNext() ) {
                int index = (Integer)iterator.next();
                selectedPen = pensList.get( index );
                break;
            }
            if (selectedPen != null) {
                containerKey = selectedPen.getContainerKey();
            }
            System.out.println("ContainerKey " + containerKey);
        }
        return containerKey;
    }
}