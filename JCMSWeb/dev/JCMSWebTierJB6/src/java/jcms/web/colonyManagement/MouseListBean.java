/*/****
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
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author rkavitha
 */
public class MouseListBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 06523231L;
    
    private Integer           currentRow             = null;
    private MouseEntity       currentItem            = null;
    private boolean           isResultCountDisplayed = false;
    private SelectItemWrapper selectItemWrapper      = new SelectItemWrapper();
    private ListDataModel     mouseDataModel         = null;
    private ExtendedTableBean mouseETB               = new ExtendedTableBean();
    private ExtendedTableBean mouseSelectionETB      = new ExtendedTableBean();    
    
    private MouseSearchDTO mouseSearch = null;
    private MiceListCommon mouseInfo = null;
    private List<MouseEntity> mouseList = null;
    
    private LifeStatusEntity lsEntity = new LifeStatusEntity();
    private CvBreedingStatusEntity bsEntity = new CvBreedingStatusEntity();
    
    public MouseListBean() {
        this.initialize();
        this.initializeOwner();
    }
    
    // initialize the owner to logged in owner
    private void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");        
        if (ownerLst.size() > 0) {
            this.mouseSearch.setOwners(ownerLst);
        }
    }

    private void initialize() {
        mouseSearch = new MouseSearchDTO();
        mouseInfo = new MiceListCommon();        
        
        // Create the ldap data model.
        mouseDataModel = new ListDataModel();
        this.setIsResultCountDisplayed(false);

        // By default, create the bean without user selection.
        mouseETB.setSelectionMode( ExtendedTableBean.SELECTIONMODE_NONE );
        mouseList = new ArrayList<MouseEntity>();
    }
    
     /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void clearLifeStatusAction() { 
        lsEntity = new LifeStatusEntity();
    }
    
    /**
     * <b>Purpose:</b> Clear the results text and results table.  <br />
     */
    public void selectLifeStatusAction() {  
        System.out.println("set row dirty ");
        
        if (this.mouseDataModel.isRowAvailable()) {
            MouseEntity mouse = (MouseEntity) mouseDataModel.getRowData();

            System.out.println("current ls " + mouse.getLifeStatus());

            if (lsEntity != null && lsEntity.getLifeStatuskey() > 0) {
                mouse.setLifeStatus(this.lsEntity.getLifeStatus());
                mouse.setIsDirty(true);
                System.out.println("selected ls " + this.lsEntity.getLifeStatus());
            }
        }
    }
    
    public String miceListAction() { 
        
        this.initialize();
        this.initializeOwner();
        return "miceList";
    }
    
    public String returnToSearchAction() { 
        
        this.miceSearchAction();
        return "miceList";
    }
    
    public void miceSearchAction() {
        System.out.println("Search Results");
        
        // check if any search criteria is selected, else throw error message
        if (this.mouseSearch != null && 
                ((mouseSearch.getMouseID() != null && !mouseSearch.getMouseID().equals("")) || 
                 (mouseSearch.getStrain() != null && mouseSearch.getStrain().getStrainName() != null && !mouseSearch.getStrain().getStrainName().equals("")) ||
                 (mouseSearch.getLifeStatus() != null && mouseSearch.getLifeStatus().getLifeStatus() != null && !mouseSearch.getLifeStatus().getLifeStatus().equals("")) ||
                 (mouseSearch.getOwners() != null && !this.mouseSearch.getOwners().isEmpty()) ||
                 (mouseSearch.getSex() != null && mouseSearch.getSex().getSex() != null && !mouseSearch.getSex().getSex().equals("")) ||
                 (mouseSearch.getPenID() != null && !mouseSearch.getPenID().equals("")) ||
                 (mouseSearch.getDOBStartDate() != null) ||
                 (mouseSearch.getDOBEndDate() != null)) ) {
            
            // if empty, then set it to the owners that user belongs to
            if (this.mouseSearch.getOwners() == null || this.mouseSearch.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.addToMessageQueue("Select at least one Owner/Workgroup. ", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select at least one Owner/Workgroup. "));
            } else {
                this.mouseDataModel.setWrappedData(this.mouseInfo.miceSearch(this.mouseSearch));
                this.setIsResultCountDisplayed(true);
            }
        }
        else {
            this.addToMessageQueue("Select some search criteria", 
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Select some search criteria"));
        }
    }
    
    /**
     * <b>Purpose:</b> Save a UserBean<br />
     * <b>Overview:</b> Save changes.  <br />
     * @return String action returned to faces-config.xml
     */
    public String saveAction() {
        List<MouseEntity> miceList = (List<MouseEntity>) this.mouseDataModel.getWrappedData();
        List<ITBaseEntityInterface> changedMouseList = new ArrayList<ITBaseEntityInterface>();
        boolean flag = false;
        int ver = 0;
        
        try {
            for (MouseEntity changedMouse : miceList) {
                if (changedMouse.getIsDirty()) {
                    changedMouseList.add(changedMouse);
                    
                    // set the version
                    ver = changedMouse.getVersion();
                    changedMouse.setVersion(ver+1);
                        
                    new SaveAppService().saveMouse(changedMouse);
                    getLogger().logInfo("MouseListBean.saveAction() - Updating Mouse " + changedMouse.getId());
                    flag = true;
                }
            }
            
            if (flag) {
                this.addToMessageQueue("Mice have been saved", FacesMessage.SEVERITY_INFO);
                this.miceSearchAction();
            }
        } // General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "MouseListBean SaveAction"));
        }
        // Redisplay the page.
        return null;
    }
    
    /**
     * This action handler is invoked whenever a UserEntity element is changed
     * on the form.
     * @param vce Information about the value being changed
     */
    public void mouseEntityChangedAction( ValueChangeEvent vce ) {
        System.out.println("set row dirty ");
        if ( this.mouseDataModel.isRowAvailable() ) {
            System.out.println("set row dirty ");
            ((MouseEntity)mouseDataModel.getRowData()).setIsDirty( true );
        }
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
    public MouseEntity getCurrentItem() {
        return currentItem;
    }

    /**
     * @param currentItem the currentItem to set
     */
    public void setCurrentItem(MouseEntity currentItem) {
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
     * @return the mouseDataModel
     */
    public ListDataModel getMouseDataModel() {
        return mouseDataModel;
    }

    /**
     * @param mouseDataModel the mouseDataModel to set
     */
    public void setMouseDataModel(ListDataModel mouseDataModel) {
        this.mouseDataModel = mouseDataModel;
    }

    /**
     * @return the mouseETB
     */
    public ExtendedTableBean getMouseETB() {
        return mouseETB;
    }

    /**
     * @param mouseETB the mouseETB to set
     */
    public void setMouseETB(ExtendedTableBean mouseETB) {
        this.mouseETB = mouseETB;
    }

    /**
     * @return the mouseSelectionETB
     */
    public ExtendedTableBean getMouseSelectionETB() {
        return mouseSelectionETB;
    }

    /**
     * @param mouseSelectionETB the mouseSelectionETB to set
     */
    public void setMouseSelectionETB(ExtendedTableBean mouseSelectionETB) {
        this.mouseSelectionETB = mouseSelectionETB;
    }

    /**
     * @return the mouseSearch
     */
    public MouseSearchDTO getMouseSearch() {
        return mouseSearch;
    }

    /**
     * @param mouseSearch the mouseSearch to set
     */
    public void setMouseSearch(MouseSearchDTO mouseSearch) {
        this.mouseSearch = mouseSearch;
    }

    /**
     * @return the mouseList
     */
    public List<MouseEntity> getMouseList() {
        return mouseList;
    }

    /**
     * @param mouseList the mouseList to set
     */
    public void setMouseList(List<MouseEntity> mouseList) {
        this.mouseList = mouseList;
    }

    /**
     * @return the lsEntity
     */
    public LifeStatusEntity getLsEntity() {
        return lsEntity;
    }

    /**
     * @param lsEntity the lsEntity to set
     */
    public void setLsEntity(LifeStatusEntity lsEntity) {
        this.lsEntity = lsEntity;
    }

    /**
     * @return the bsEntity
     */
    public CvBreedingStatusEntity getBsEntity() {
        return bsEntity;
    }

    /**
     * @param bsEntity the bsEntity to set
     */
    public void setBsEntity(CvBreedingStatusEntity bsEntity) {
        this.bsEntity = bsEntity;
    }    

}