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
import jcms.web.common.SelectItemWrapper;
import jcms.web.dto.PenInfoDTO;

/**
 *
 * @author rkavitha
 */
public class PenSearchCommon extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0323231L;
    
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private boolean isResultCountDisplayed = false;
    private ListDataModel penInfoDataModel = null;
    private ExtendedTableBean penSelectionETB = new ExtendedTableBean();
    private PenSearchDTO penSearch;
    private ReportFacadeLocal rfl;

    public PenSearchCommon() {
        penSearch = new PenSearchDTO();
        penInfoDataModel = new ListDataModel();
        rfl = new BusinessTierPortal().getReportFacadeLocal();
    }

    public String searchAction() {

        List<PenInfoDTO> penInfo = new ArrayList<PenInfoDTO>();
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
                if (map[j].get("containerHistoryKey") != null) {
                    dto.setContainerHistoryKey((Integer) (map[j].get("containerHistoryKey")));
                }

                // get the containerKey
                if (map[j].get("containerKey") != null) {
                    dto.setContainerKey((Integer) map[j].get("containerKey"));
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
            getLogger().logDebug("PenListBean.searchAction: " + penInfoDataModel.getRowCount() + " rows returned.");

            setIsResultCountDisplayed(true);
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return null;
    }
    
    public void selectPenAction() {
        System.out.println("Selected Pen");
    }
    
    /**
     * <b>Purpose:</b> Add newly selected pen.  <br />
     * <b>Overview:</b> Add newly selected pen.  <br />
     */
    public String addAction() {
        PenInfoDTO selectedPen = new PenInfoDTO();
        
        System.out.println("Selected Pen");
        
        if (penSelectionETB != null) {

        }
        
        return null;
    }

    /**
     * <b>Purpose:</b> Clear the results text, results table, and Look For objects.  <br />
     */
    public String clearPopupAction() {
        penSearch = new PenSearchDTO();
        penInfoDataModel.setWrappedData(new ArrayList<PenInfoDTO>());
        isResultCountDisplayed = false;

        return null;
    }
    
    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }

    /**
     * @return the penInfoDataModel
     */
    public ListDataModel getPenInfoDataModel() {
        return penInfoDataModel;
    }

    /**
     * @param penInfoDataModel the penInfoDataModel to set
     */
    public void setPenInfoDataModel(ListDataModel penInfoDataModel) {
        this.penInfoDataModel = penInfoDataModel;
    }

    /**
     * @return the isResultCountDisplayed
     */
    public boolean isIsResultCountDisplayed() {
        return isResultCountDisplayed;
    }

    /**
     * @param isResultCountDisplayed the isResultCountDisplayed to set
     */
    public void setIsResultCountDisplayed(boolean isResultCountDisplayed) {
        this.isResultCountDisplayed = isResultCountDisplayed;
    }

    /**
     * @return the penSearch
     */
    public PenSearchDTO getPenSearch() {
        return penSearch;
    }

    /**
     * @param penSearch the penSearch to set
     */
    public void setPenSearch(PenSearchDTO penSearch) {
        this.penSearch = penSearch;
    }

    /**
     * @return the userSelectionETB
     */
    public ExtendedTableBean getPenSelectionETB() {
        return penSelectionETB;
    }

    /**
     * @param userSelectionETB the userSelectionETB to set
     */
    public void setPenSelectionETB(ExtendedTableBean penSelectionETB) {
        this.penSelectionETB = penSelectionETB;
    }
}