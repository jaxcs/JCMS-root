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
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;

/**
 *
 * @author rkavitha
 */
public class MiceListCommon extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0311231L;
    private ReportFacadeLocal rfl;

    public MiceListCommon() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
    }

    public List<MouseEntity> miceSearch(MouseSearchDTO mouseSearch) {
        List<MouseEntity> mouseInfo = new ArrayList<MouseEntity>();
        
        try {
            ITBaseEntityTable mouseTable = rfl.findMiceSearchResults(mouseSearch);
            
            for (ITBaseEntityInterface entity: mouseTable.getList()) {
                mouseInfo.add((MouseEntity) entity);
            }        
            getLogger().logDebug("MiceSearch: " + mouseInfo.size() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return mouseInfo;
    }
    
    public List<MouseGenotypeDTO> miceGenotypeSearch(MouseSearchDTO mouseSearch,
            GenotypeSearchDTO gdto) {
        List<MouseGenotypeDTO> mouseInfo = new ArrayList<MouseGenotypeDTO>();
        MouseGenotypeDTO mgEntity;
        
        try {
            mouseInfo = rfl.findMouseGenotypeSearchResults(mouseSearch, gdto);            
            
            System.out.println("MiceSearch: " + mouseInfo.size() + " rows returned.");
            getLogger().logDebug("MiceSearch: " + mouseInfo.size() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return mouseInfo;
    }
    
    public List<MouseEntity> mouseSearchList(Result result) {
        List<MouseEntity> mouseInfo = new ArrayList<MouseEntity>();
        MouseEntity dto;
        
        try {            
            SortedMap[] map = result.getRows();

            for (int j = 0; j < map.length; ++j) {
                dto = new MouseEntity();

                if (map[j].get("_mouse_key") != null) {
                    dto.setMouseKey((Integer) map[j].get("_mouse_key"));
                }

                // get the id
                if (map[j].get("id") != null) {
                    dto.setId(map[j].get("id").toString());
                }

                // get the birthDate
                if (map[j].get("birthDate") != null) {
                    dto.setBirthDate((Date) map[j].get("birthDate"));
                }

                // get the generation
                if (map[j].get("generation") != null) {
                    dto.setGeneration(map[j].get("generation").toString());
                }
                
                // get the owner
                if (map[j].get("owner") != null) {
                    dto.setOwner(map[j].get("owner").toString());
                }
                
                // get the lifeStatus
                if (map[j].get("lifeStatus") != null) {
                    dto.setLifeStatus(map[j].get("lifeStatus").toString());
                }
                
                // get the breedingStatus
                if (map[j].get("breedingStatus") != null) {
                    dto.setBreedingStatus(map[j].get("breedingStatus").toString());
                }
                
                // get the diet
                if (map[j].get("diet") != null) {
                    dto.setDiet(map[j].get("diet").toString());
                }
                
                mouseInfo.add(dto);
            }
            getLogger().logDebug("MiceSearch: " + mouseInfo.size() + " rows returned.");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return mouseInfo;
    }
    
    public ListDataModel damSearch(MouseSearchDTO mouseSearch) {
        ListDataModel mouseInfoDataModel = new ListDataModel();
        
        try {
            Result result = rfl.findDamSearchResults(mouseSearch);
            
            if (result != null && result.getRowCount() > 0) {
                mouseInfoDataModel.setWrappedData(this.mouseSearchList(result));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return mouseInfoDataModel;
    }
    
    public ListDataModel sireSearch(MouseSearchDTO mouseSearch) {
        ListDataModel mouseInfoDataModel = new ListDataModel();
        
        try {
            Result result = rfl.findSireSearchResults(mouseSearch);
            if (result != null && result.getRowCount() > 0) {
                mouseInfoDataModel.setWrappedData(this.mouseSearchList(result));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return mouseInfoDataModel;
    }
       
    /**
     * <b>Purpose:</b> Add newly selected users to existing users panel.  <br />
     * <b>Overview:</b> Add newly selected users to existing users panel.  <br />
     */
    public MouseEntity getSelectedMouse(ExtendedTableBean mouseSelectionETB, 
            ListDataModel mouseInfoDataModel) {
        MouseEntity selectedMouse = null;
        
        if (mouseSelectionETB != null) {
            List<MouseEntity> miceList = new ArrayList<MouseEntity>();
            miceList = (List<MouseEntity>) mouseInfoDataModel.getWrappedData();
                        
            // Grab the indices of the rows to be added. NOTE: These are *just* the indices.
            Iterator<Object> iterator = mouseSelectionETB.getSelection().iterator();  
            
            // get the selected pen ID
            while ( iterator.hasNext() ) {
                int index = (Integer)iterator.next();
                selectedMouse = miceList.get( index );
                break;
            }
        }
        return selectedMouse;
    }    
}
