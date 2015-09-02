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

package jcms.web.cagecards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.dao.CageCardDAO;

/**
 *
 * @author mkamato
 */
public class LoadCageCardBean extends WTBaseBackingBean implements Serializable{
        private static final long serialVersionUID = 1L;
    private List<QueryDefinitionEntity> queryLst = new ArrayList<QueryDefinitionEntity>();
    private List<QueryDefinitionEntity> detailCards = new ArrayList<QueryDefinitionEntity>();
    private List<QueryDefinitionEntity> matingCards = new ArrayList<QueryDefinitionEntity>();
    private List<QueryDefinitionEntity> weanCards = new ArrayList<QueryDefinitionEntity>();
    private CageCardDAO ccDAO = new CageCardDAO();
    Integer key;

    public LoadCageCardBean() {
        try {
            // get the workgroups that user belongs to
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
            queryLst = new ListSupportDTO().getUserDefinedQueriesByWorkgroups(wgLst); 
            detailCards = ccDAO.getDetailCards(wgLst);
            matingCards = ccDAO.getMatingCards(wgLst);
            weanCards = ccDAO.getWeanCards(wgLst);
        } 
        catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }
    
    public void refresh(){
        System.out.println("started refreshing...");
        
        try{
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst");

            queryLst = new ListSupportDTO().getUserDefinedQueriesByWorkgroups(wgLst);
            detailCards = ccDAO.getDetailCards(wgLst);
            matingCards = ccDAO.getMatingCards(wgLst);
            weanCards = ccDAO.getWeanCards(wgLst);
            System.out.println("finished refreshing...");
        }
        catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }
    
    
    /**
     * @return the queryLst
     */
    public List<QueryDefinitionEntity> getQueryLst() {
        return queryLst;
    }
    
    public void deleteCancel(){
        this.addToMessageQueue("Delete action cancelled.", FacesMessage.SEVERITY_WARN);
    }
      
    /**
     * @param queryLst the queryLst to set
     */
    public void setQueryLst(List<QueryDefinitionEntity> queryLst) {
        this.queryLst = queryLst;
    }

    /**
     * @return the detailCards
     */
    public List<QueryDefinitionEntity> getDetailCards() {
        return detailCards;
    }

    /**
     * @param detailCards the detailCards to set
     */
    public void setDetailCards(List<QueryDefinitionEntity> detailCards) {
        this.detailCards = detailCards;
    }

    /**
     * @return the matingCards
     */
    public List<QueryDefinitionEntity> getMatingCards() {
        return matingCards;
    }

    /**
     * @param matingCards the matingCards to set
     */
    public void setMatingCards(List<QueryDefinitionEntity> matingCards) {
        this.matingCards = matingCards;
    }

    /**
     * @return the weanCards
     */
    public List<QueryDefinitionEntity> getWeanCards() {
        return weanCards;
    }

    /**
     * @param weanCards the weanCards to set
     */
    public void setWeanCards(List<QueryDefinitionEntity> weanCards) {
        this.weanCards = weanCards;
    }
}
