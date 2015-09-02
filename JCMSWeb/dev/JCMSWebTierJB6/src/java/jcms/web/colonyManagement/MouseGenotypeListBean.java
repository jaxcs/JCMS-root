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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityNotFoundException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.exception.SaveEntityException;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.exception.SaveException;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.middletier.service.SaveAppService;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;
import jcms.web.service.RepositoryService;

/**
 *
 * @author rkavitha
 */
public class MouseGenotypeListBean extends WTBaseBackingBean implements 
        Serializable {
    
    private static final long serialVersionUID = 005323L;
    
    private Integer           currentRow             = null;
    private MouseEntity       currentItem            = null;
    private boolean           isResultCountDisplayed = false;
    private SelectItemWrapper selectItemWrapper      = new SelectItemWrapper();
    
    private ListDataModel     mouseGenotypeDataModel     = null;
    private ExtendedTableBean mouseGenotypeETB           = new ExtendedTableBean();
    private ExtendedTableBean mouseGenotypeSelectionETB  = new ExtendedTableBean();
    
    private List<MouseGenotypeDTO> mouseGenotypeLst  = null;
    
    private MouseSearchDTO mouseSearch = null;
    private MiceListCommon mouseInfo = null;
    
    private GenotypeSearchDTO genotypeSearch = null;
    private GenotypeSearchDTO genotypePopupSearch = null;
    
    private ListDataModel     genotypeDataModel;
    private ExtendedTableBean genotypeETB           = new ExtendedTableBean();
    private ExtendedTableBean genotypeSelectionETB  = new ExtendedTableBean();
    private boolean           isGenotypeResultCountDisplayed = false;
    
    private GenotypeEntity genotypeEntity;
    private List<GenotypeEntity> genotypesInfo;
    private LifeStatusEntity lsEntity = new LifeStatusEntity();
    private CvBreedingStatusEntity bsEntity = new CvBreedingStatusEntity();
    private boolean tempAlleles;    
    private boolean setDirtyFlag;
    private int genotypesLimit = 0;
    private String mouseID = "";

    private List<SelectItem> allele1FilterItems = new ArrayList<SelectItem>();    
    private List<SelectItem> allele2FilterItems = new ArrayList<SelectItem>();   
    
    public MouseGenotypeListBean() {
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
        setMouseSearch(new MouseSearchDTO());
        setMouseInfo(new MiceListCommon());
        
        setGenotypeSearch(new GenotypeSearchDTO());
        this.setGenotypePopupSearch(new GenotypeSearchDTO());
        
        // Load data model with database data.
        //mouseDataModel = new ListDataModel(this.getRepositoryService().findAllUsers());
        
        // Create the ldap data model.
        this.setIsResultCountDisplayed(false);
        this.tempAlleles = false;        
        setSetDirtyFlag(false);
        setMouseGenotypeDataModel(new ListDataModel());
        getMouseGenotypeETB().setSelectionMode( ExtendedTableBean.SELECTIONMODE_NONE );
        setMouseGenotypeLst(new ArrayList<MouseGenotypeDTO>());
        
        setGenotypeDataModel(new ListDataModel());
        genotypesInfo = new ArrayList<GenotypeEntity>();
        
        // set the limit for genotypes to show up for each mouse
        this.setGenotypesLimit(10);
        
        allele1FilterItems.clear();
        allele1FilterItems.add(new SelectItem("", ""));
        allele2FilterItems.clear();
        allele2FilterItems.add(new SelectItem("", ""));
    }
    
    public void clearPopupAction() {
        System.out.println("Clear popup is called");
        this.genotypeDataModel.setWrappedData(new ArrayList<GenotypeEntity>());
        this.isGenotypeResultCountDisplayed = false;
        this.setGenotypePopupSearch(new GenotypeSearchDTO());
    }
    
    public void handleClick(ActionEvent event) {
        Object id = event.getComponent().getAttributes().get("paramMouseId");
        Object key = event.getComponent().getAttributes().get("paramMouseKey");
        
        System.out.println("Handle click is called");
        
        this.genotypeDataModel.setWrappedData(new ArrayList<GenotypeEntity>());
        this.isGenotypeResultCountDisplayed = false;
        this.setGenotypePopupSearch(new GenotypeSearchDTO());
        
        if (id != null) {
            this.genotypePopupSearch.setMouseID(id.toString());
            System.out.println(this.genotypePopupSearch.getMouseID());
        }
        
        if (key != null) {
            this.genotypePopupSearch.setMouseKey((Integer)key);
        }
    }
    
    public void miceSearchAction() {
        System.out.println("Search Results");
        
        // check if any search criteria is selected, else throw error message
        if (this.getMouseSearch() != null && 
                ((getMouseSearch().getMouseID() != null && !mouseSearch.getMouseID().equals("")) || 
                (getMouseSearch().getStrain() != null && !mouseSearch.getStrain().getStrainName().equals("")) ||
                (getMouseSearch().getLifeStatus() != null && !mouseSearch.getLifeStatus().getLifeStatus().equals("")) ||
                (mouseSearch.getOwners() != null && !mouseSearch.getOwners().isEmpty()) ||
                (getMouseSearch().getSex() != null && !mouseSearch.getSex().getSex().equals("")) ||
                (getMouseSearch().getGeneration() != null && !mouseSearch.getGeneration().getGeneration().equals("")) ||
                (getMouseSearch().getPenID() != null && !mouseSearch.getPenID().equals("")) ||
                (getMouseSearch().getDOBStartDate() != null) ||
                (getMouseSearch().getDOBEndDate() != null)) ) {
            
            if (this.mouseSearch.getOwners() == null || this.mouseSearch.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.addToMessageQueue("Select at least one Owner/Workgroup. ", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select at least one Owner/Workgroup. "));
                
            } else {
                this.setMouseGenotypeLst(this.getMouseInfo().miceGenotypeSearch(this.getMouseSearch(), this.genotypeSearch));
                this.getMouseGenotypeDataModel().setWrappedData(this.getMouseGenotypeLst());
                this.setIsResultCountDisplayed(true);
            }
            
        }
        else {
            this.addToMessageQueue("Select some search criteria", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Select some search criteria"));
        }
    }
    
    /**
     * Assign default values for a genotype record
     * @param ge
     * @return 
     */
    private GenotypeEntity assignDefaults(GenotypeEntity ge) {
        // assign defaults
        if (ge != null) {
            if (ge.getGenoPage() == null || ge.getGenoPage().equals("")) {
                ge.setGenoPage("None");
            }
            
            // assign true only for inserts
            if (ge.getGenotypeKey() == 0 ) {
                ge.setAll1Conf(true);
                ge.setAll2Conf(true);
            }
        }        
        return ge;
    }
    
    public String addGenotypeAction() { 
        this.initialize();
        this.initializeOwner();
        return "mouseGenotypeList";
    }
    
    public String clearSearchAction() { 
        this.initialize();
        this.initializeOwner();
        return "mouseGenotypeList";
    }
    
    public void showAction() {   
        int index = this.getKey("paramMouseIndex");
        int mKey = this.getKey("paramMouseKey");
        
        System.out.println("in show action");
        
        if (this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey()
                    == mKey || this.getMouseGenotypeLst().get(index).
                    getMouseEntity().getMouseKey().equals(mKey)) {
            this.getMouseGenotypeLst().get(index).setShowHideFlag(true);
            
            System.out.println("flag " + this.getMouseGenotypeLst().get(index).isShowHideFlag());
        }
    }
    
    public void hideAction() {        
        int index = this.getKey("paramMouseIndex");
        int mKey = this.getKey("paramMouseKey");
        
        System.out.println("in hide action");
        
        if (this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey()
                    == mKey || this.getMouseGenotypeLst().get(index).
                    getMouseEntity().getMouseKey().equals(mKey)) {
            this.getMouseGenotypeLst().get(index).setShowHideFlag(false);
            System.out.println("flag for " + this.getMouseGenotypeLst().get(index).isShowHideFlag());
        }
    }
    
   /**
     * <b>Purpose:</b> Prepares a new object for data entry. <br />
     * <b>Overview:</b> This method is usually called from an Add button on a
     *      list view.  Data transfer objects are created in preparation for
     *      entering data in a form view.  The data entered is saved to these
     *      data transfer objects by JSF API.  <p />
     * @return String action returned to faces-config.xml
     */
    public void editGenotypeAction() throws Exception {
        this.setSessionVariables();
        
        int gKey = (Integer)getSessionParameter("gKey");
        
        System.out.println("Edit genotype action");
        System.out.println("genotypeKey " + gKey);
        System.out.println("this.isSetDirtyFlag() " + this.isSetDirtyFlag());
        
        // proceed further only when set dirty flag is false to prevent one 
        // action at a time
        if (!this.isSetDirtyFlag()) {
            if (gKey > 0) {
                // Find the MouseEntity
                System.out.println("find genotype entity");
                setGenotypeEntity(new GenotypeEntity());

                this.setGenotypeEntity(getRepositoryService().
                        baseFindGenotype(gKey));
            }
        }
        else {
            String id = "";
            if (getSessionParameter("mouseID") != null) {
                id = getSessionParameter("mouseID").toString();
            }
            addToMessageQueue("Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id, 
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("save",
                      "Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id));
        }
    }
    
    /**
     * sets the variables in session for deletes
     * @throws Exception 
     */
    public void assignKeysAction() throws Exception {
        this.setSessionVariables();
        System.out.println("DirtyFlag " + this.setDirtyFlag);
            
        if (this.isSetDirtyFlag()) {
            String id = "";
            if (getSessionParameter("mouseID") != null) {
                id = getSessionParameter("mouseID").toString();
            }
            addToMessageQueue("Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id, 
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("save",
                      "Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id));
        }
    }
    
    /**
     * Adds a Genotype data entry row, when Add genotype button for specific 
     * mouse is clicked.
     */
    public void addGenotypeRecordAction() {

        int index = this.getKey("paramMouseIndex");
        int mKey = this.getKey("paramMouseKey");
        
        // proceed further only when set dirty flag is false to prevent one 
        // action at a time
        if (!this.isSetDirtyFlag()) {

            // reset alleles list
            this.tempAlleles = false;

            if (this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey() == mKey || 
                this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey().equals(mKey)) {
                
                // set the mouse ID in session
                this.mouseID = this.getMouseGenotypeLst().get(index).getMouseEntity().getId();
                this.putSessionParameter("mouseID", this.mouseID);
                this.getMouseGenotypeLst().get(index).setShowHideFlag(true);

                GenotypeEntity ge = new GenotypeEntity();
                // assign true only for inserts
                ge.setGenoPage("None");
                ge.setAll1Conf(true);
                ge.setAll2Conf(true);
                ge.setGtDate(new Date());

                setSetDirtyFlag(true);

                this.getMouseGenotypeLst().get(index).getGenotypeList().add(ge);
                System.out.println("new genotype row added for Mouse key " + mKey
                        + " and Mouse Index " + index);
            }
            this.getMouseGenotypeDataModel().setWrappedData(this.getMouseGenotypeLst());
        }
        else {
            String id = "";
            if (getSessionParameter("mouseID") != null) {
                id = getSessionParameter("mouseID").toString();
            }
            addToMessageQueue("Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id, 
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logInfo(this.formatLogMessage("validate",
                      "Action cannot be completed because you are in the "
                    + "middle of adding a Genotype row for the Mouse ID " + id));
        }
    }

    public void cancelEditGenotypeAction() {
        System.out.println("in cancel edit genotype action");
        int index = this.getKey("paramMouseIndex");
        int mKey = this.getKey("paramMouseKey");
        int gIndex = this.getKey("paramGenotypeIndex");
        
        if (this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey() == mKey || 
            this.getMouseGenotypeLst().get(index).getMouseEntity().getMouseKey().equals(mKey)) {
            if (this.getMouseGenotypeLst().get(index).getGenotypeList().get(gIndex).getGenotypeKey() == null) {
                this.getMouseGenotypeLst().get(index).getGenotypeList().remove(gIndex);
                setSetDirtyFlag(false);
            }
            System.out.println("flag " + this.getMouseGenotypeLst().get(index).isShowHideFlag());
        }

    }
    
    public String returnToSearchAction() { 
        
        this.miceSearchAction();
        return "mouseGenotypeList";
    }
    
    public void genotypeSearchAction() {
        System.out.println("Search Results");
        
        try {
            genotypesInfo = new ArrayList<GenotypeEntity>();
            ITBaseEntityTable genotypeTable = new BusinessTierPortal().getReportFacadeLocal().genotypeSearchResults(genotypePopupSearch);
            
            for (ITBaseEntityInterface entity: genotypeTable.getList()) {
                this.genotypesInfo.add((GenotypeEntity) entity);
            }     
            
            this.genotypeDataModel.setWrappedData(this.genotypesInfo);
            isGenotypeResultCountDisplayed = true;
        } 
        catch (Exception e) {
            this.getLogger().logInfo(this.formatLogMessage("search", e.toString()));
        }
    }
        
    public void valueChanged(ValueChangeEvent event) {
        this.tempAlleles = true;
    }
    
    /**
     * if  gene is selected, filter alleles by gene key
     * @throws Exception 
     */
    public List<SelectItem> getAllelesByGeneItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        
        // otherwise, when gene is selected, get the alleles
        if (this.tempAlleles) {
            int mIndex = this.getKey("paramMouseIndex");
            int gIndex = this.getKey("paramGenotypeIndex");
            int mKey = this.getKey("paramMouseKey");
            int gKey = 0;

            System.out.println("mouse row " + mIndex);
            System.out.println("genotype row " + gIndex);
            System.out.println("mouse Key " + mKey);

            if (this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey() == mKey || 
                this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey().equals(mKey)) {

                // to handle null pointer exception when there are no genotype rows
                if (this.getMouseGenotypeLst().get(mIndex).getGenotypeList() != null
                    && !this.getMouseGenotypeLst().get(mIndex).getGenotypeList().isEmpty()) {                    
                    gKey = this.getMouseGenotypeLst().get(mIndex).getGenotypeList().
                            get(gIndex).getGeneKey().getGeneKey();
                }
            }

            if (gKey > 0) {
                System.out.println("gene Key " + gKey);

                for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(gKey)) {
                    items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                }
            }
        }
        return items;
    }
    
    public void geneFilterChanged(ValueChangeEvent event){
        List<SelectItem> items = new ArrayList<SelectItem>();        
        items.add(new SelectItem("", ""));        
        this.getAllele1FilterItems().clear();
        this.getAllele2FilterItems().clear();
        GeneEntity geneEntity = (GeneEntity) event.getNewValue();
        
        try {
            if (geneEntity == null) {
                this.setAllele1FilterItems(items);
                this.setAllele2FilterItems(items);
            } 
            else {
                Integer geneKey = geneEntity.getGeneKey();
                if (geneKey != null && geneKey > 0) {
                    System.out.println("gene Key " + geneKey);

                    for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(geneKey)) {
                        items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                        System.out.println(entity.getAllele());
                    }
                }
            }
        } catch (Exception e) {
            // do nothing
        }
        this.setAllele1FilterItems(items);
        this.setAllele2FilterItems(items);
    }

    /**
     * if  gene is selected, filter alleles by gene key
     * @throws Exception 
     */
    public List<SelectItem> getAlleleByGeneItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));        
        
        if (this.genotypeSearch != null) {
            if (this.genotypeSearch.getGene() == null) {
                // return empty list
                return items;
            } // otherwise, when gene is selected, get the alleles
            else {
                int gKey = 0;
                
                if (this.genotypeSearch != null && genotypeSearch.getGene() != 
                        null && genotypeSearch.getGene().getGeneKey() != null) {
                    gKey = genotypeSearch.getGene().getGeneKey();
                }

                if (gKey > 0) {
                    System.out.println("gene Key " + gKey);

                    for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(gKey)) {
                        items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                        System.out.println(entity.getAllele());
                    }
                }
                
                return items;
            }
        } else {
            return items;
        }
    }
    
    /**
     * if  gene is selected, filter alleles by gene key
     * @throws Exception 
     */
    public List<SelectItem> getAlleleByGeneItemsPopup() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));        
        
        if (this.genotypePopupSearch != null) {
            if (this.genotypePopupSearch.getGene() == null) {
                // return empty list
                return items;
            } // otherwise, when gene is selected, get the alleles
            else {
                int gKey = 0;
                
                if (this.genotypePopupSearch != null && genotypePopupSearch.getGene() != 
                        null && genotypePopupSearch.getGene().getGeneKey() != null) {
                    gKey = genotypePopupSearch.getGene().getGeneKey();
                }

                if (gKey > 0) {
                    System.out.println("gene Key " + gKey);

                    for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(gKey)) {
                        items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                    }
                }
                return items;
            }
        } else {
            return items;
        }
    }
    
    /**
     * if  gene is selected, filter alleles by gene key
     * @throws Exception 
     */
    public List<SelectItem> getEditAllelesByGeneItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        
        int gKey = 0;
        
        if (this.genotypeEntity != null && this.genotypeEntity.getGeneKey() != null &&
                this.genotypeEntity.getGeneKey().getGeneKey()>0) {
            gKey = this.genotypeEntity.getGeneKey().getGeneKey();
        }
        
        if (gKey > 0) {
            System.out.println("gene Key " + gKey);

            for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(
                    gKey)) {
                items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
            }
        }
        return items;
    }

   /**
     * <b>Purpose:</b> Save a Genotype<br />
     * <b>Overview:</b> Save changes.  <br />
     * @return String action returned to faces-config.xml
     */
    public String insertGenotypeAction() {

        int mIndex = this.getKey("paramMouseIndex");
        int gIndex = this.getKey("paramGenotypeIndex");
        int mKey = this.getKey("paramMouseKey");
        GenotypeEntity genotypeEntity = null;
        
        System.out.println("entered into save with mouseKey " + mKey + 
                " mouseIndex " + mIndex + " genotypeIndex " + gIndex);

        try {
            if (this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey() == mKey || 
                this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey().equals(mKey)) {
                
                System.out.println("entered into if");

                // set the genotype with mouseEntity
                GenotypeEntity ge = new GenotypeEntity();
                ge = (GenotypeEntity) this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).getEntity();
                ge.setMouseKey(this.getMouseGenotypeLst().get(mIndex).getMouseEntity());
                
                boolean flag = false;
                                            
                // validate genotype record
                if (this.validateGenotypeRecord(0, mKey, ge.getGeneKey().getGeneKey())) {
                    flag = true;
                }
                
                // validate date
                if (!this.validateDate(ge.getGtDate())) {
                    flag = true;
                }
                
                // validate genoPage
                if (!this.validateGenoPage(ge.getGenoPage())) {
                    flag = true;
                }

                if (!flag) {                
                // debug genotype details
                System.out.println("GeneSymbol " + ge.getGeneKey().getGeneSymbol());
                System.out.println("Allele1 " + ge.getAllele1());
                System.out.println("Allele2 " + ge.getAllele2());
                System.out.println("All1Conf " + ge.getAll1Conf());
                System.out.println("SampleLocation " + ge.getSampleLocation());
                
                    System.out.println("entered into insert");
                    // generate primary key
                        Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(ge);

                        if (null == pk || 0 == pk) {
                            ge.setGenotypeKey(1);
                        } else {
                            ge.setGenotypeKey(pk + 1);
                        }
                        
                    new SaveAppService().baseCreate(ge);
                    this.getMouseGenotypeLst().get(mIndex).setGenotypeFilterTotal(this.getMouseGenotypeLst().get(mIndex).getGenotypeFilterTotal() + 1);
                    this.getMouseGenotypeLst().get(mIndex).setGenotypeMouseTotal(this.getMouseGenotypeLst().get(mIndex).getGenotypeMouseTotal() + 1);

                    genotypeEntity = this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex);
                    if (genotypeEntity.getAllele2().isEmpty())
                        genotypeEntity.setGenotypeDisplayFormat(genotypeEntity.getAllele1());
                    else
                        genotypeEntity.setGenotypeDisplayFormat(genotypeEntity.getAllele1() + "/" + genotypeEntity.getAllele2());
                    
                    addToMessageQueue("Genotype for the Gene " + ge.getGeneKey().getLabSymbol() + 
                            " and mouse " + ge.getMouseKey().getId() + 
                            " has been inserted", FacesMessage.SEVERITY_INFO);
                    this.getLogger().logInfo(this.formatLogMessage("save",
                                "Gene " + ge.getGeneKey().getLabSymbol() + 
                            " and mouse " + ge.getMouseKey().getId() + 
                            " has been inserted"));
                    
                    this.tempAlleles = false;
                    this.setSetDirtyFlag(false);
                    System.out.println("SetDirtyFlag " + this.isSetDirtyFlag());
                }
            }            
        } catch (SaveException se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            return null;
        }// General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (SaveEntityException ex) {
            String msg = "The system failed to save any mouse updates.  "
                    + "Please report this problem to the web master with date "
                    + "and time of error.  ";

            // Display user friendly error message
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    /**
     * Validate if genotype record already exists with same mouse and gene
     * @param mKey
     * @param gKey
     * @return
     * @throws Exception 
     */
    public boolean validateGenotypeRecord(int gnKey, int mKey, int gKey) throws Exception {
        // validate if record already exists with same mouse and gene
        GenotypeEntity gEntity = new RepositoryService().findGenotypeByMouseAndGene(mKey, gKey);

        // edit
        if (gnKey > 0) {

            if (gEntity != null && gnKey != gEntity.getGenotypeKey()) {
                addToMessageQueue("Genotype for the Gene " + gEntity.getGeneKey().getLabSymbol()
                        + " and mouse " + gEntity.getMouseKey().getId()
                        + " already exists, Please choose a different gene",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("validation",
                        "Genotype for the Gene " + gEntity.getGeneKey().getLabSymbol()
                        + " and mouse " + gEntity.getMouseKey().getId()
                        + " already exists, Please choose a different gene"));
                return true;
            } else {
                return false;
            }
        } // add
        else {
            if (gEntity != null) {
                addToMessageQueue("Genotype for the Gene " + gEntity.getGeneKey().getLabSymbol()
                        + " and mouse " + gEntity.getMouseKey().getId()
                        + " already exists, Please choose a different gene",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logInfo(this.formatLogMessage("validation",
                        "Genotype for the Gene " + gEntity.getGeneKey().getLabSymbol()
                        + " and mouse " + gEntity.getMouseKey().getId()
                        + " already exists, Please choose a different gene"));
                return true;
            } else {
                return false;
            }
        }
    }
    
    public String deleteGenotypeNoConfirmationAction()
    {
        // removed this call from jsp delete confirmation and 
        // added it here to bypass the confirmation
        try {
            this.assignKeysAction();
        } catch (Exception aka) {
            String msg = aka.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteAction"));
            return null;
        }
        deleteGenotypeAction();
        
        return null;
    }
    
    public void deleteGenotypeActionListener(ActionEvent event) {
        this.deleteGenotypeAction();
    }
    
    /**
     * <b>Purpose:</b> delete a Genotype<br />
     * <b>Overview:</b> delete changes.  <br />
     * @return String action returned to faces-config.xml
     */
    public String deleteGenotypeAction() {
        int mIndex = (Integer)getSessionParameter("mIndex");
        int gIndex = (Integer)getSessionParameter("gIndex");
        int mKey = (Integer)getSessionParameter("mKey");
        int gKey = 0;
        
        if (getSessionParameter("gKey") != null) {
            gKey = (Integer)getSessionParameter("gKey");
        }

        System.out.println("entered into delete with mouseKey " + mKey + " genotype key " + gKey 
                + " and mouseIndex " + mIndex + " genotypeIndex " + gIndex);

        try {
            if (this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey()
                    == mKey || this.getMouseGenotypeLst().get(mIndex).
                    getMouseEntity().getMouseKey().equals(mKey)) {

                System.out.println("entered into if");

                // set the genotype with mouseEntity
                GenotypeEntity ge = new GenotypeEntity();
                ge = (GenotypeEntity) this.getMouseGenotypeLst().
                        get(mIndex).getGenotypeList().get(gIndex).getEntity();
                ge.setMouseKey(this.getMouseGenotypeLst().get(mIndex).getMouseEntity());

                if (gKey > 0) {
                    System.out.println("entered into db delete");
                    // delete from db
                    new SaveAppService().baseDelete(ge);
                    
                    addToMessageQueue("Genotype for the gene "
                            + ge.getGeneKey().getLabSymbol() +
                            " and mouse " + ge.getMouseKey().getId() + 
                            " has been deleted", FacesMessage.SEVERITY_INFO);
                    this.getLogger().logInfo(this.formatLogMessage("save",
                            "Gene " + ge.getGeneKey().getLabSymbol() + 
                            " and mouse " + ge.getMouseKey().getId() + 
                            " has been deleted"));
                }
                else {
                    addToMessageQueue("Genotype for the mouse " + 
                            ge.getMouseKey().getId() + 
                            " has been deleted", FacesMessage.SEVERITY_INFO);
                    this.getLogger().logInfo(this.formatLogMessage("save",
                            "Genotype for the mouse " + ge.getMouseKey().getId() + 
                            " has been deleted"));                    
                }
                
                // delete from the list
                this.getMouseGenotypeLst().get(mIndex).getGenotypeList().remove(gIndex);
                this.clearSessionVariables();
                this.tempAlleles = false;
                setSetDirtyFlag(false);
                
                this.getMouseGenotypeLst().get(mIndex).setShowHideFlag(!this.getMouseGenotypeLst().get(mIndex).getGenotypeList().isEmpty());
           }
        } catch (EntityNotFoundException se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteAction"));
            return null;
        }// General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (IllegalStateException ex) {
            String msg = "The system failed to save any mouse updates.  "
                    + "Please report this problem to the web master with date "
                    + "and time of error.  ";

            // Display user friendly error message
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteAction"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteAction"));
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    /**
     * set the variables in session
     */
    private void setSessionVariables() {
        
        System.out.println("enered into session variables");
        
        int mIndex = this.getKey("paramMouseIndex");
        int gIndex = this.getKey("paramGenotypeIndex");
        int mKey = this.getKey("paramMouseKey");
        int gKey = this.getKey("paramGenotypeKey");
    
        this.putSessionParameter("mIndex", mIndex);
        this.putSessionParameter("gIndex", gIndex);
        this.putSessionParameter("mKey", mKey);
        
        if (gKey > 0) {
            this.putSessionParameter("gKey", gKey);
        }
        else {
            this.putSessionParameter("gKey", 0);
        }
    }
    
    /**
     * clear the variables in session
     */
    private void clearSessionVariables() {
        this.putSessionParameter("mIndex", 0);
        this.putSessionParameter("gIndex", 0);
        this.putSessionParameter("mKey", 0);
        this.putSessionParameter("gKey", 0);
    }

    
    /**
     * <b>Purpose:</b> Save a Genotype<br />
     * <b>Overview:</b> Save changes.  <br />
     * @return String action returned to faces-config.xml
     */
    
    public Object updateGenotypeAction() {
        System.out.println("updateGenotypeAction");
        try {
            int mIndex = (Integer) getSessionParameter("mIndex");
            int gIndex = (Integer) getSessionParameter("gIndex");
            int mKey = (Integer) getSessionParameter("mKey");
            int gKey = (Integer) getSessionParameter("gKey");

            System.out.println("entered into edit with mouseKey " + mKey + " genotype key " + gKey
                    + " and mouseIndex " + mIndex + " genotypeIndex " + gIndex);
            
            boolean flag = false;            

            // if record already exists then update
            if (this.genotypeEntity != null && this.genotypeEntity.getGenotypeKey()
                    != null && this.genotypeEntity.getGenotypeKey() > 0) {
                
                // validate date
                if (!this.validateDate(this.genotypeEntity.getGtDate())) {
                    flag = true;
                }
            
                // validate genotype record
                if (this.validateGenotypeRecord(this.genotypeEntity.getGenotypeKey(), 
                        this.genotypeEntity.getMouseKey().getMouseKey(), 
                        this.genotypeEntity.getGeneKey().getGeneKey())) {
                    flag = true;
                }
                
                // validate genoPage
                if (!this.validateGenoPage(this.genotypeEntity.getGenoPage())) {
                    flag = true;
                }

                if (!flag) {
                    // debug genotype details
                    System.out.println("GeneSymbol " + this.genotypeEntity.getGeneKey().getGeneSymbol());
                    System.out.println("Allele1 " + this.genotypeEntity.getAllele1());
                    System.out.println("Allele2 " + this.genotypeEntity.getAllele2());
                    System.out.println("All1Conf " + this.genotypeEntity.getAll1Conf());
                    System.out.println("SampleLocation " + this.genotypeEntity.getSampleLocation());

                    // set the version
                    int ver = this.genotypeEntity.getVersion();
                    System.out.println("previous version " + ver);
                    this.genotypeEntity.setVersion(ver + 1);

                    new SaveAppService().updateGenotype(this.genotypeEntity);

                    this.getLogger().logInfo(this.formatLogMessage("save",
                            "Gene " + this.genotypeEntity.getGeneKey().getLabSymbol()
                            + " and mouse " + this.genotypeEntity.getMouseKey().getId()
                            + " has been updated"));

                    // refresh the data in the table
                    if (this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey()
                            == mKey || this.getMouseGenotypeLst().get(mIndex).
                            getMouseEntity().getMouseKey().equals(mKey)) {

                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setAll1Conf(this.genotypeEntity.getAll1Conf());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setAll2Conf(this.genotypeEntity.getAll2Conf());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setAllele1(this.genotypeEntity.getAllele1());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setAllele2(this.genotypeEntity.getAllele2());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setComment(this.genotypeEntity.getComment());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setGeneKey(this.genotypeEntity.getGeneKey());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setGenoPage(this.genotypeEntity.getGenoPage());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setGtDate(this.genotypeEntity.getGtDate());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setSampleLocation(this.genotypeEntity.getSampleLocation());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setVersion(this.genotypeEntity.getVersion());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setGenotypeKey(this.genotypeEntity.getGenotypeKey());
                        this.getMouseGenotypeLst().get(mIndex).getGenotypeList().get(gIndex).setMouseKey(this.genotypeEntity.getMouseKey());
                    }
                    this.clearSessionVariables();
                    // reset alleles list
                    this.tempAlleles = false;
                    setSetDirtyFlag(false);
                }
            }
        } catch (SaveException se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            return null;
        }// General catch-all for failed saves. The exception's message has already been customized for user display.
        catch (SaveEntityException ex) {
            String msg = "The system failed to save any mouse updates.  "
                    + "Please report this problem to the web master with date "
                    + "and time of error.  ";

            // Display user friendly error message
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));

            // Return null to indicate that the JSF implementation
            // should reload the same page.
            return null;
        } catch (Exception se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction"));
            return null;
        }
        // Redisplay the page.
        return null;
    }
    
    /*
     * This method is called on change event on start date and end date to 
     * validate
     */
    public void validateDateAction() {
        int mIndex = this.getKey("paramMouseIndex");
        int gIndex = this.getKey("paramGenotypeIndex");
        int mKey = this.getKey("paramMouseKey");
        Date gtDate = null;

        System.out.println("mouse row " + mIndex);
        System.out.println("genotype row " + gIndex);
        System.out.println("mouse Key " + mKey);


        if (this.getMouseGenotypeLst().get(mIndex).getMouseEntity().getMouseKey()
                == mKey || this.getMouseGenotypeLst().get(mIndex).
                getMouseEntity().getMouseKey().equals(mKey)) {
            gtDate = this.getMouseGenotypeLst().get(mIndex).getGenotypeList().
                    get(gIndex).getGtDate();
        }
        this.validateDate(gtDate);
    }
    
    /*
     * This method is called on change event on start date and end date to 
     * validate
     */
    public void editValidateDateAction() {
        this.validateDate(this.genotypeEntity.getGtDate());        
    }
    
    /*
     * This method is called on change event on start date and end date to 
     * validate
     */
    public boolean validateDate(Date dt) {  
        boolean flag = true;
        
        // date cannot be greater than today
        if (dt != null && dt.compareTo(new Date()) > 0) {
            this.addToMessageQueue("Genotype Date cannot be greater than Today", 
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Genotype Date cannot be greater than Today"));
            flag = false;
        }
        return flag;
    }

    /* 
    * this method is called to verify the required genoPage field is not blank
    * Suggest "None" if it is
    */
    public boolean validateGenoPage(String genoPage) {
        boolean flag = false;
        
        if (genoPage != null) {
            if (!genoPage.equals ("")) {
                flag = true;
                return flag;
            }
        }
        this.addToMessageQueue("Genotype Page # cannot be blank, enter 'None' if not available.", 
                    FacesMessage.SEVERITY_ERROR);

        this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Genotype Page cannot be blank or null"));
        return flag;
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
     * @return the mouseInfo
     */
    public MiceListCommon getMouseInfo() {
        return mouseInfo;
    }

    /**
     * @param mouseInfo the mouseInfo to set
     */
    public void setMouseInfo(MiceListCommon mouseInfo) {
        this.mouseInfo = mouseInfo;
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
    
    /**
     * @return the mouseGenotypeDataModel
     */
    public ListDataModel getMouseGenotypeDataModel() {
        return mouseGenotypeDataModel;
    }

    /**
     * @param mouseGenotypeDataModel the mouseGenotypeDataModel to set
     */
    public void setMouseGenotypeDataModel(ListDataModel mouseGenotypeDataModel) {
        this.mouseGenotypeDataModel = mouseGenotypeDataModel;
    }

    /**
     * @return the mouseGenotypeETB
     */
    public ExtendedTableBean getMouseGenotypeETB() {
        return mouseGenotypeETB;
    }

    /**
     * @param mouseGenotypeETB the mouseGenotypeETB to set
     */
    public void setMouseGenotypeETB(ExtendedTableBean mouseGenotypeETB) {
        this.mouseGenotypeETB = mouseGenotypeETB;
    }

    /**
     * @return the mouseGenotypeSelectionETB
     */
    public ExtendedTableBean getMouseGenotypeSelectionETB() {
        return mouseGenotypeSelectionETB;
    }

    /**
     * @param mouseGenotypeSelectionETB the mouseGenotypeSelectionETB to set
     */
    public void setMouseGenotypeSelectionETB(ExtendedTableBean mouseGenotypeSelectionETB) {
        this.mouseGenotypeSelectionETB = mouseGenotypeSelectionETB;
    }

    /**
     * @return the mouseGenotypeLst
     */
    public List<MouseGenotypeDTO> getMouseGenotypeLst() {
        return mouseGenotypeLst;
    }

    /**
     * @param mouseGenotypeLst the mouseGenotypeLst to set
     */
    public void setMouseGenotypeLst(List<MouseGenotypeDTO> mouseGenotypeLst) {
        this.mouseGenotypeLst = mouseGenotypeLst;
    }

    /**
     * @return the genotypeEntity
     */
    public GenotypeEntity getGenotypeEntity() {
        return genotypeEntity;
    }

    /**
     * @param genotypeEntity the genotypeEntity to set
     */
    public void setGenotypeEntity(GenotypeEntity genotypeEntity) {
        this.genotypeEntity = genotypeEntity;
    }

    /**
     * @return the setDirtyFlag
     */
    public boolean isSetDirtyFlag() {
        return setDirtyFlag;
    }

    /**
     * @param setDirtyFlag the setDirtyFlag to set
     */
    public void setSetDirtyFlag(boolean setDirtyFlag) {
        this.setDirtyFlag = setDirtyFlag;
    }

    /**
     * @return the genotypeSearch
     */
    public GenotypeSearchDTO getGenotypeSearch() {
        return genotypeSearch;
    }

    /**
     * @param genotypeSearch the genotypeSearch to set
     */
    public void setGenotypeSearch(GenotypeSearchDTO genotypeSearch) {
        this.genotypeSearch = genotypeSearch;
    }

    /**
     * @return the genotypeDataModel
     */
    public ListDataModel getGenotypeDataModel() {
        return genotypeDataModel;
    }

    /**
     * @param genotypeDataModel the genotypeDataModel to set
     */
    public void setGenotypeDataModel(ListDataModel genotypeDataModel) {
        this.genotypeDataModel = genotypeDataModel;
    }

    /**
     * @return the genotypeETB
     */
    public ExtendedTableBean getGenotypeETB() {
        return genotypeETB;
    }

    /**
     * @param genotypeETB the genotypeETB to set
     */
    public void setGenotypeETB(ExtendedTableBean genotypeETB) {
        this.genotypeETB = genotypeETB;
    }

    /**
     * @return the genotypeSelectionETB
     */
    public ExtendedTableBean getGenotypeSelectionETB() {
        return genotypeSelectionETB;
    }

    /**
     * @param genotypeSelectionETB the genotypeSelectionETB to set
     */
    public void setGenotypeSelectionETB(ExtendedTableBean genotypeSelectionETB) {
        this.genotypeSelectionETB = genotypeSelectionETB;
    }

    /**
     * @return the isGenotypeResultCountDisplayed
     */
    public boolean isIsGenotypeResultCountDisplayed() {
        return isGenotypeResultCountDisplayed;
    }

    /**
     * @param isGenotypeResultCountDisplayed the isGenotypeResultCountDisplayed to set
     */
    public void setIsGenotypeResultCountDisplayed(boolean isGenotypeResultCountDisplayed) {
        this.isGenotypeResultCountDisplayed = isGenotypeResultCountDisplayed;
    }

    /**
     * @return the genotypesInfo
     */
    public List<GenotypeEntity> getGenotypesInfo() {
        return genotypesInfo;
    }

    /**
     * @param genotypesInfo the genotypesInfo to set
     */
    public void setGenotypesInfo(List<GenotypeEntity> genotypesInfo) {
        this.genotypesInfo = genotypesInfo;
    }

    /**
     * @return the genotypesLimit
     */
    public int getGenotypesLimit() {
        return genotypesLimit;
    }

    /**
     * @param genotypesLimit the genotypesLimit to set
     */
    public void setGenotypesLimit(int genotypesLimit) {
        this.genotypesLimit = genotypesLimit;
    }

    /**
     * @return the genotypePopupSearch
     */
    public GenotypeSearchDTO getGenotypePopupSearch() {
        return genotypePopupSearch;
    }

    /**
     * @param genotypePopupSearch the genotypePopupSearch to set
     */
    public void setGenotypePopupSearch(GenotypeSearchDTO genotypePopupSearch) {
        this.genotypePopupSearch = genotypePopupSearch;
    }

    /**
     * @return the mouseID
     */
    public String getMouseID() {
        return mouseID;
    }

    /**
     * @param mouseID the mouseID to set
     */
    public void setMouseID(String mouseID) {
        this.mouseID = mouseID;
    }

    /**
     * @return the allele1FilterItems
     */
    public List<SelectItem> getAllele1FilterItems() {
        return allele1FilterItems;
    }

    /**
     * @param allele1FilterItems the allele1FilterItems to set
     */
    public void setAllele1FilterItems(List<SelectItem> allele1FilterItems) {
        this.allele1FilterItems = allele1FilterItems;
    }

    /**
     * @return the allele2FilterItems
     */
    public List<SelectItem> getAllele2FilterItems() {
        return allele2FilterItems;
    }

    /**
     * @param allele2FilterItems the allele2FilterItems to set
     */
    public void setAllele2FilterItems(List<SelectItem> allele2FilterItems) {
        this.allele2FilterItems = allele2FilterItems;
    }
    
}