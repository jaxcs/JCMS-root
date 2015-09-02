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
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import javax.faces.application.FacesMessage;
import javax.faces.model.ListDataModel;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;

/**
 *
 * @author rkavitha
 */
public class MatingListCommon extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0311231L;
    private ReportFacadeLocal rfl;

    public MatingListCommon() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
    }

    public List<MatingEntity> matingSearch(MatingSearchDTO matingSearch) {
        List<MatingEntity> matingInfo = new ArrayList<MatingEntity>();
        
        try {
            ITBaseEntityTable matingTable = rfl.findMatingSearchResults(matingSearch);
            
            for (ITBaseEntityInterface entity: matingTable.getList()) {
                matingInfo.add((MatingEntity) entity);
            }        
            getLogger().logDebug("MatingSearch: " + matingInfo.size() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return matingInfo;
    }
    
    public List<MatingEntity> matingSearchList(Result result) {
        List<MatingEntity> matingInfo = new ArrayList<MatingEntity>();
        MatingEntity dto;
        
        try {            
            SortedMap[] map = result.getRows();

            for (int j = 0; j < map.length; ++j) {
                dto = new MatingEntity();

                if (map[j].get("_mating_key") != null) {
                    dto.setMatingKey((Integer) map[j].get("_mating_key"));
                }

                // get the id
                if (map[j].get("matingID") != null) {
                    dto.setMatingID((Integer) map[j].get("matingID"));
                }

                // get the owner
                if (map[j].get("owner") != null) {
                    dto.setOwner(map[j].get("owner").toString());
                }
                
                matingInfo.add(dto);
            }
            getLogger().logDebug("MatingSearch: " + matingInfo.size() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return matingInfo;
    }
    
    /**
     * <b>Purpose:</b> Add newly selected users to existing users panel.  <br />
     * <b>Overview:</b> Add newly selected users to existing users panel.  <br />
     */
    public MatingEntity getSelectedMating(ExtendedTableBean matingSelectionETB, 
            ListDataModel matingInfoDataModel) {
        MatingEntity selectedMating = null;
        
        if (matingSelectionETB != null) {
            List<MatingEntity> matingList = new ArrayList<MatingEntity>();
            matingList = (List<MatingEntity>) matingInfoDataModel.getWrappedData();
                        
            // Grab the indices of the rows to be added. NOTE: These are *just* the indices.
            Iterator<Object> iterator = matingSelectionETB.getSelection().iterator();  
            
            // get the selected pen ID
            while ( iterator.hasNext() ) {
                int index = (Integer)iterator.next();
                selectedMating = matingList.get( index );
                break;
            }
        }
        return selectedMating;
    }
}