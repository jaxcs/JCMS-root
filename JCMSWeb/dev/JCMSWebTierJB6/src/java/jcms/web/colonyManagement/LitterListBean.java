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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author rkavitha
 */
public class LitterListBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0112331L;
    
    private Integer            currentRow             = null;
    private MatingEntity       currentItem            = null;
    private boolean            isResultCountDisplayed;
    private SelectItemWrapper  selectItemWrapper      = new SelectItemWrapper();
    private ListDataModel      litterDataModel        = null;
    private ExtendedTableBean  litterETB              = new ExtendedTableBean();
    private ExtendedTableBean  litterSelectionETB     = new ExtendedTableBean();
    
    private LitterSearchDTO    litterSearch           = null;
    private LitterListCommon   litterInfo             = null;
    private List<MatingEntity> litterList             = null;
    
    public LitterListBean() {
        this.initialize();   
    }
    
    // initialize the owner to logged in owner
    private void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");        
        if (ownerLst.size() > 0) {
            this.litterSearch.setOwners(ownerLst);
        }
    }
    
    private void initialize() {
        setLitterSearch(new LitterSearchDTO());
        setLitterInfo(new LitterListCommon());
        
        // Create the ldap data model.
        setLitterDataModel(new ListDataModel());
        this.setIsResultCountDisplayed(false);

        // By default, create the bean without user selection.
        getLitterETB().setSelectionMode( ExtendedTableBean.SELECTIONMODE_NONE );
        setLitterList(new ArrayList<MatingEntity>());
    }
    
    public String litterListAction() { 
        
        this.initialize();
        this.initializeOwner();
        
        return "litterList";
    }
    
    public String returnToSearchAction() { 
        
        this.litterSearchAction();
        return "litterList";
    }
    
    public void litterSearchAction() {
        System.out.println("Litter Search Results");
        
        // check if any search criteria is selected, else throw error message
        if (this.litterSearch != null && 
                ((litterSearch.getLitterID() != null && !litterSearch.getLitterID().equals("")) || 
                (litterSearch.getMatingID() != null && !litterSearch.getMatingID().equals("")) ||
                (litterSearch.getBirthDate() != null) ||
                (litterSearch.getStrain() != null && !litterSearch.getStrain().getStrainName().equals("") && litterSearch.getStrain().getStrainName() != null) ||
                (litterSearch.getOwners() != null && !litterSearch.getOwners().isEmpty())) ) {
            
            // if empty, then set it to the owners that user belongs to
            if (this.litterSearch.getOwners() == null || this.litterSearch.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.addToMessageQueue("Select at least one Owner/Workgroup. ", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select at least one Owner/Workgroup. "));
            } else {
                this.getLitterDataModel().setWrappedData(this.getLitterInfo().litterSearchList(this.getLitterSearch()));
                this.setIsResultCountDisplayed(true);
            }
        }
        else {
            this.addToMessageQueue("Select some search criteria", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select some search criteria"));
        }
    }
    
    /**
     * This action handler is invoked whenever a UserEntity element is changed
     * on the form.
     * @param vce Information about the value being changed
     */
    public void litterEntityChangedAction(ValueChangeEvent vce) {
        System.out.println("set row dirty ");
        if ( this.litterDataModel.isRowAvailable() ) {
            System.out.println("set row dirty ");
            ((LitterEntity)litterDataModel.getRowData()).setIsDirty( true );
        }
    }
    
     /**
     * <b>Purpose:</b> Save a LitterBean<br />
     * <b>Overview:</b> Save changes.  <br />
     * @return String action returned to faces-config.xml
     */
    public String saveAction() {
        List<LitterEntity> lList = (List<LitterEntity>) this.
                litterDataModel.getWrappedData();
        List<ITBaseEntityInterface> changedLitterList = new 
                ArrayList<ITBaseEntityInterface>();
        boolean flag = false;
        
        try {
            for (LitterEntity changedLitter : lList) {
                if (changedLitter.getIsDirty()) {
                    changedLitterList.add(changedLitter);
                    new SaveAppService().baseEdit(changedLitter);
                    getLogger().logInfo("MouseListBean.saveAction() - Updating "
                            + "Mouse " + changedLitter.getLitterID());
                    flag = true;
                }
            }
            
            if (flag) {
                this.addToMessageQueue("litter have been saved", 
                        FacesMessage.SEVERITY_INFO);
                this.litterSearchAction();
            }

        } // General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, 
                    "LitterListBean SaveAction"));
        }
        // Redisplay the page.
        return null;
    }

    /**
     * @return the currentRow
     */
    public Integer getCurrentRow() {
        return currentRow;
    }

    /**
     * @param currentRow the currentRow to set
     */
    public void setCurrentRow(Integer currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * @return the currentItem
     */
    public MatingEntity getCurrentItem() {
        return currentItem;
    }

    /**
     * @param currentItem the currentItem to set
     */
    public void setCurrentItem(MatingEntity currentItem) {
        this.currentItem = currentItem;
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
     * @return the litterDataModel
     */
    public ListDataModel getLitterDataModel() {
        return litterDataModel;
    }

    /**
     * @param litterDataModel the litterDataModel to set
     */
    public void setLitterDataModel(ListDataModel litterDataModel) {
        this.litterDataModel = litterDataModel;
    }

    /**
     * @return the litterETB
     */
    public ExtendedTableBean getLitterETB() {
        return litterETB;
    }

    /**
     * @param litterETB the litterETB to set
     */
    public void setLitterETB(ExtendedTableBean litterETB) {
        this.litterETB = litterETB;
    }

    /**
     * @return the litterSelectionETB
     */
    public ExtendedTableBean getLitterSelectionETB() {
        return litterSelectionETB;
    }

    /**
     * @param litterSelectionETB the litterSelectionETB to set
     */
    public void setLitterSelectionETB(ExtendedTableBean litterSelectionETB) {
        this.litterSelectionETB = litterSelectionETB;
    }

    /**
     * @return the litterSearch
     */
    public LitterSearchDTO getLitterSearch() {
        return litterSearch;
    }

    /**
     * @param litterSearch the litterSearch to set
     */
    public void setLitterSearch(LitterSearchDTO litterSearch) {
        this.litterSearch = litterSearch;
    }

    /**
     * @return the litterInfo
     */
    public LitterListCommon getLitterInfo() {
        return litterInfo;
    }

    /**
     * @param litterInfo the litterInfo to set
     */
    public void setLitterInfo(LitterListCommon litterInfo) {
        this.litterInfo = litterInfo;
    }

    /**
     * @return the litterList
     */
    public List<MatingEntity> getLitterList() {
        return litterList;
    }

    /**
     * @param litterList the litterList to set
     */
    public void setLitterList(List<MatingEntity> litterList) {
        this.litterList = litterList;
    }
}
