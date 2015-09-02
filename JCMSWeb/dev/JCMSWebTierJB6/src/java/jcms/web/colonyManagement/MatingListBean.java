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
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author rkavitha
 */
public class MatingListBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 0073231L;
    
    private Integer            currentRow             = null;
    private MatingEntity       currentItem            = null;
    private boolean            isResultCountDisplayed;
    private SelectItemWrapper  selectItemWrapper      = new SelectItemWrapper();
    private ListDataModel      matingDataModel        = null;
    private ExtendedTableBean  matingETB              = new ExtendedTableBean();
    private ExtendedTableBean  matingSelectionETB     = new ExtendedTableBean();
    
    private MatingSearchDTO    matingSearch           = null;
    private MatingListCommon   matingInfo             = null;
    private List<MatingEntity> matingList             = null;
    
    public MatingListBean() {
        this.initialize(); 
        this.initializeOwner();
    }
    
    // initialize the owner to logged in owner
    private void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");        
        if (ownerLst.size() > 0) {
            this.matingSearch.setOwners(ownerLst);
        }
    }
    
    private void initialize() {
        matingSearch = new MatingSearchDTO();
        matingInfo = new MatingListCommon();
        
        // Create the ldap data model.
        matingDataModel = new ListDataModel();
        this.setIsResultCountDisplayed(false);

        // By default, create the bean without user selection.
        matingETB.setSelectionMode( ExtendedTableBean.SELECTIONMODE_NONE );
        matingList = new ArrayList<MatingEntity>();
    }
    
    public String matingListAction() {
        
        this.initialize();
        this.initializeOwner();
        return "matingList";
    }
    
    /**
     * This action handler is invoked whenever a MatingEntity field is changed
     * on the form.
     * @param vce Information about the value being changed
     */
    public void matingEntityChangedAction( ValueChangeEvent vce ) {
        System.out.println("set row dirty ");
        if ( this.matingDataModel.isRowAvailable() ) {
            System.out.println("set row dirty ");
            ((MatingEntity)matingDataModel.getRowData()).setIsDirty( true );
        }
    }
    
    /**
     * <b>Purpose:</b> Save a mating<br />
     * <b>Overview:</b> Save changes.  <br />
     * @return String action returned to faces-config.xml
     */
    public void saveAction() {
        List<MatingEntity> mList = (List<MatingEntity>) this.matingDataModel.getWrappedData();
        boolean flag = false;
        int ver = 0;
        
        try {
            for (MatingEntity changedMating : mList) {
                boolean goodToGo = true;
                if (changedMating.getIsDirty()) {
                    //validate - make sure that if retired there is a retired date, if not retired, no retired date
                    if(changedMating.getCrossStatuskey().getAbbreviation().equalsIgnoreCase("R")
                            && changedMating.getRetiredDate() == null){
                        this.addToMessageQueue("Could not save mating " + changedMating.getMatingID() 
                                + " as a mating cannot be retired without a retired date.", FacesMessage.SEVERITY_WARN);
                        goodToGo = false;
                    }
                    if(!changedMating.getCrossStatuskey().getAbbreviation().equalsIgnoreCase("R")
                            && changedMating.getRetiredDate() != null){
                        this.addToMessageQueue("Could not save mating " + changedMating.getMatingID() 
                                + " as a mating cannot have a retired date and not be retired.", FacesMessage.SEVERITY_WARN);
                        goodToGo = false;
                    }
                    
                    if(goodToGo){
                        System.out.println("mating_key " + changedMating.getMatingKey());

                        // set the version
                        ver = changedMating.getVersion();
                        changedMating.setVersion(ver+1);

                        new SaveAppService().saveMating(changedMating);
                        getLogger().logInfo("MatingListBean.saveAction() - Updating "
                                + "Mating " + changedMating.getMatingID());
                        this.addToMessageQueue("Mating " + changedMating.getMatingID() + " successfully saved", FacesMessage.SEVERITY_INFO);
                    }
                }
            }
            
        } // General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "MatingListBean SaveAction"));
        }
    }
    
    public void matingSearchAction() {
        System.out.println("Search Results");
        
        // check if any search criteria is selected, else throw error message
        if (this.matingSearch != null && ((matingSearch.getMatingID() != null && 
                !matingSearch.getMatingID().equals("")) || 
                (matingSearch.getStrain() != null &&
                !matingSearch.getStrain().getStrainName().equals("") ||
                (matingSearch.getMatingStatus() != null) &&
                !matingSearch.getMatingStatus().getCrossStatus().equals("")) ||
                (matingSearch.getOwners() != null &&
                !matingSearch.getOwners().isEmpty())) ) {
            
            // if empty, then set it to the owners that user belongs to
            if (this.matingSearch.getOwners() == null || this.matingSearch.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.addToMessageQueue("Select at least one Owner/Workgroup. ", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select at least one Owner/Workgroup. "));
            } else {
                this.matingDataModel.setWrappedData(this.matingInfo.matingSearch(this.matingSearch));
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
     * @return the matingDataModel
     */
    public ListDataModel getMatingDataModel() {
        return matingDataModel;
    }

    /**
     * @param matingDataModel the matingDataModel to set
     */
    public void setMatingDataModel(ListDataModel matingDataModel) {
        this.matingDataModel = matingDataModel;
    }

    /**
     * @return the matingETB
     */
    public ExtendedTableBean getMatingETB() {
        return matingETB;
    }

    /**
     * @param matingETB the matingETB to set
     */
    public void setMatingETB(ExtendedTableBean matingETB) {
        this.matingETB = matingETB;
    }

    /**
     * @return the matingSelectionETB
     */
    public ExtendedTableBean getMatingSelectionETB() {
        return matingSelectionETB;
    }

    /**
     * @param matingSelectionETB the matingSelectionETB to set
     */
    public void setMatingSelectionETB(ExtendedTableBean matingSelectionETB) {
        this.matingSelectionETB = matingSelectionETB;
    }

    /**
     * @return the matingSearch
     */
    public MatingSearchDTO getMatingSearch() {
        return matingSearch;
    }

    /**
     * @param matingSearch the matingSearch to set
     */
    public void setMatingSearch(MatingSearchDTO matingSearch) {
        this.matingSearch = matingSearch;
    }

    /**
     * @return the matingInfo
     */
    public MatingListCommon getMatingInfo() {
        return matingInfo;
    }

    /**
     * @param matingInfo the matingInfo to set
     */
    public void setMatingInfo(MatingListCommon matingInfo) {
        this.matingInfo = matingInfo;
    }

    /**
     * @return the matingList
     */
    public List<MatingEntity> getMatingList() {
        return matingList;
    }

    /**
     * @param matingList the matingList to set
     */
    public void setMatingList(List<MatingEntity> matingList) {
        this.matingList = matingList;
    }
    
    
}
