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
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.dto.LitterInfoDTO;

/**
 *
 * @author rkavitha
 */
public class LitterListCommon extends WTBaseBackingBean implements Serializable {
    private static final long serialVersionUID = 0323231L;
    private ReportFacadeLocal rfl;

    public LitterListCommon() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
    }

    public ListDataModel litterSearch(LitterSearchDTO litterSearch) {
        List<LitterInfoDTO> litterInfo = new ArrayList<LitterInfoDTO>();
        ListDataModel litterInfoDataModel = new ListDataModel();
        LitterInfoDTO dto = null;
        Result result = null;
        
        try {
            result = rfl.findLitterSearchResults(litterSearch);
            SortedMap[] map = result.getRows();

            for (int j = 0; j < map.length; ++j) {
                dto = new LitterInfoDTO();

                if (map[j].get("litterID") != null) {
                    dto.setLitterID(map[j].get("litterID").toString());
                }

                // get the status
                if (map[j].get("status") != null) {
                    dto.setStatus(map[j].get("status").toString());
                }
                
                 // get the strain
                if (map[j].get("strainName") != null) {
                    dto.setStrain(map[j].get("strainName").toString());
                }

                if (map[j].get("litterKey") != null) {
                    dto.setLitterKey((Integer) (map[j].get("litterKey")));
                }
                
                if (map[j].get("totalBorn") != null) {
                    dto.setTotalBorn((Integer) (map[j].get("totalBorn")));
                }
                
                if (map[j].get("numMale") != null) {
                    dto.setNumMale((Integer) (map[j].get("numMale")));
                }
                
                if (map[j].get("numFemale") != null) {
                    dto.setNumFemale((Integer) (map[j].get("numFemale")));
                }
                
                if (map[j].get("matingID") != null) {
                    dto.setMatingID((Integer) (map[j].get("matingID")));
                }

                // get the room
                if (map[j].get("birthDate") != null) {
                    dto.setBirthDate((Date) map[j].get("birthDate"));
                }
                litterInfo.add(dto);
            }
            
            litterInfoDataModel.setWrappedData(litterInfo);
            getLogger().logDebug("LitterSearch: " + litterInfoDataModel.getRowCount() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return litterInfoDataModel;
    }
    
    public List<LitterEntity> litterSearchList(LitterSearchDTO litterSearch) {
        List<LitterEntity> litterInfo = new ArrayList<LitterEntity>();
        
        try {
            ITBaseEntityTable litterTable = rfl.findLittersSearchResults(
                    litterSearch);
            
            for (ITBaseEntityInterface entity: litterTable.getList()) {
                litterInfo.add((LitterEntity) entity);
            }        
            getLogger().logDebug("LitterSearch: " + litterInfo.size() + 
                    " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return litterInfo;
    }
       
    /**
     * <b>Purpose:</b> Add newly selected users to existing users panel.  <br />
     * <b>Overview:</b> Add newly selected users to existing users panel.  <br />
     */
    public String getSelectedLitter(ExtendedTableBean litterSelectionETB, ListDataModel
            litterInfoDataModel) {
        
        System.out.println("Selected Litter");
        String litterID = "";
        
        if (litterSelectionETB != null) {
            List<LitterInfoDTO> littersList = new ArrayList<LitterInfoDTO>();
            littersList = (List<LitterInfoDTO>) litterInfoDataModel.getWrappedData();
            LitterInfoDTO selectedLitter = null;
            
            Iterator<Object> iterator = litterSelectionETB.getSelection().iterator();  // Grab the indices of the rows to be added. NOTE: These are *just* the indices.
            
            // get the selected pen ID
            while ( iterator.hasNext() ) {
                int index = (Integer)iterator.next();
                selectedLitter = littersList.get( index );
                break;
            }
            if (selectedLitter != null) {
                litterID = selectedLitter.getLitterID();
            }
            System.out.println("LitterID " + litterID);
        }
        return litterID;
    }    
}
