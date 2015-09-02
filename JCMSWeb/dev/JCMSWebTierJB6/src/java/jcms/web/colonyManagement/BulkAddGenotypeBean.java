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
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.web.common.SelectItemWrapper;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import org.primefaces.model.DualListModel;  
import jcms.integrationtier.dao.BulkUpdateDAO;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.web.colonyManagement.MiceListCommon;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import org.primefaces.event.TransferEvent;  
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import java.util.Date;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.*;
import jcms.middletier.dto.ListSupportDTO;
import jcms.integrationtier.dao.BulkAddGenotypeDAO;
import jcms.middletier.service.SaveAppService;
import jcms.web.service.RepositoryService;
import jcms.integrationtier.dao.ContainerDAO;
import jcms.integrationtier.dto.ContainerDTO;

/**
 *
 * @author mkamato
 */
public class BulkAddGenotypeBean extends WTBaseBackingBean {
    
    private String updateUnit = "";
    private StrainEntity mouseStrain = null; 
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private DualListModel unitsModel = new DualListModel();
    private ArrayList<MouseEntity> mice = new ArrayList<MouseEntity>();
    private String selectPicklistLabel = "";
    private String selectedPicklistLabel = "";
    private StrainEntity strain = null;
    private BulkUpdateDAO dao = new BulkUpdateDAO();
    private BulkAddGenotypeDAO bulkGenotypeDAO = new BulkAddGenotypeDAO();
    private ArrayList<SelectItem> allele1s = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> allele2s = new ArrayList<SelectItem>();
    
    //genotype info
    private GeneEntity gene = null;
    private String allele1 = null;
    private String allele2 = null;
    private boolean allele1Conf = true;
    private boolean allele2Conf = false;
    private String genotypePage = "None";
    private String sampleLocation = "";
    private Date genotypeDate = null;
    private String comment = "";
        
    public void updateUnitChangeListener(){
        if(getStrain() != null){
            if(getUpdateUnit().equals("mouseID")){
                unitsModel.setSource(dao.getIDUnits(getStrain().getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                selectPicklistLabel = ("Select Mouse IDs");
                selectedPicklistLabel = ("Selected Mouse IDs");
                unitsModel.setTarget(new ArrayList<String>());
            }  
            else if(getUpdateUnit().equals("litterID")){
                unitsModel.setSource(dao.getLitterUnits(getStrain().getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                selectPicklistLabel = ("Select Litter IDs");
                selectedPicklistLabel = ("Selected Litter IDs");
                unitsModel.setTarget(new ArrayList<String>());
            }
            else if(getUpdateUnit().equals("cageID")){
                unitsModel.setSource(dao.getCageUnits(getStrain().getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                selectPicklistLabel = ("Select Cage IDs");
                selectedPicklistLabel = ("Selected Cage IDs");
                unitsModel.setTarget(new ArrayList<String>());
            }
            else if(getUpdateUnit().equals("cageName")){
                unitsModel.setSource(dao.getCageNameUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                selectPicklistLabel = ("Select Cage Names");
                selectedPicklistLabel = ("Selected Cage Names");
                unitsModel.setTarget(new ArrayList<ContainerDTO>());
            }
            mice = new ArrayList<MouseEntity>();
        }
    }

    public void unitAddListener(TransferEvent event){
        MiceListCommon mlc = new MiceListCommon();
        if(event.isAdd()){
            for(Object item : event.getItems()){
                String unit;
                if(item instanceof String){
                    unit = (String) item;
                }
                else{
                    ContainerDTO dto = (ContainerDTO) item;
                    unit = dto.getContainerID();
                }
                MouseSearchDTO dto = new MouseSearchDTO();
                dto.setStrain(null);
                dto.setLifeStatus(null);
                dto.setDOBEndDate(null);
                dto.setGeneration(null);
                dto.setDOBStartDate(null);
                dto.setOwners((List<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst"));
                dto.setOwner(null);
                dto.setSex(null);
                if(updateUnit.equals("cageID") || updateUnit.equals("cageName")){
                    dto.setPenID(unit);
                    dto.setPenFilter("Equals");
                    for(MouseEntity me : mlc.miceSearch(dto)){
                        mice.add(me);
                    }
                }
                else if(updateUnit.equals("mouseID")){
                    dto.setMouseID(unit);
                    dto.setMouseFilter("Equals");
                    for(MouseEntity me : mlc.miceSearch(dto)){
                        mice.add(me);
                    }
                }
                //unfortunately no pre built search for mice by litter so I have to hack a bit:
                //will make trip to DB via DAO for all the ids of mice of that litter then get all
                //the mouse entities via a search for a single mouse
                else if(updateUnit.equals("litterID")){
                    ArrayList<String> keys = dao.getMiceByLitterID(unit, this.getCurrentUserColonyManageWorkgroups());
                    for(String key : keys){
                        MouseEntity me = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(key))));
                        if(this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), me.getOwner())){
                            mice.add(me);
                        }
                        else{
                            System.out.println("You don't own this mouse...");
                        }
                    }
                }
                else{
                    System.out.println("something went terribly wrong");
                }
            }
        }
        if(event.isRemove()){
            for(Object item : event.getItems()){
                String unit;
                if(item instanceof String){
                    unit = (String) item;
                }
                else{
                    ContainerDTO dto = (ContainerDTO) item;
                    unit = dto.getContainerID();
                }                
                MouseSearchDTO dto = new MouseSearchDTO();
                dto.setStrain(null);
                dto.setLifeStatus(null);
                dto.setDOBEndDate(null);
                dto.setGeneration(null);
                dto.setDOBStartDate(null);
                dto.setOwners((List<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst"));
                dto.setOwner(null);
                dto.setSex(null);
                if(updateUnit.equals("cageID") || updateUnit.equals("cageName")){
                    dto.setPenID(unit);
                    dto.setPenFilter("Equals");
                    for(MouseEntity me : mlc.miceSearch(dto)){
                        mice.remove(me);
                    }
                }
                else if(updateUnit.equals("mouseID")){
                    dto.setMouseID(unit);
                    dto.setMouseFilter("Equals");
                    for(MouseEntity me : mlc.miceSearch(dto)){
                        mice.remove(me);
                    }
                }
                //unfortunately no pre built search for mice by litter so I have to hack a bit:
                //will make trip to DB via DAO for all the ids of mice of that litter then get all
                //the mouse entities via a search for a single mouse
                else if(updateUnit.equals("litterID")){
                    ArrayList<String> keys = dao.getMiceByLitterID(unit, this.getCurrentUserColonyManageWorkgroups());
                    for(String key : keys){
                        mice.remove((MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(key)))));
                    }
                }
                else{
                    System.out.println("something went terribly wrong");
                }
            }
        }   
    }
    
    public void allele2ChangeListener(){
        if(allele2.equals("")){
            allele2Conf = false;
        }
        else{
            allele2Conf = true;
        }
    }
    
    public void removeMouseListener(){
        Integer key = this.getKey("paramRowIndex");
        mice.remove(key.intValue());
    }
    
    public void geneChangeListener(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();        
        items.add(new SelectItem("", ""));        
        getAllele1s().clear();
        getAllele2s().clear();
        GeneEntity geneEntity = gene;
        try {
            if (geneEntity == null) {
                setAllele1s((ArrayList<SelectItem>) items.clone());
                setAllele2s((ArrayList<SelectItem>) items.clone());
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
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
        setAllele1s((ArrayList<SelectItem>) items.clone());
        setAllele2s((ArrayList<SelectItem>) items.clone());
    }
    
    public void addGenotype(){
        boolean flag = false;
        if(gene == null){
            this.addToMessageQueue("Gene is required, please select a gene from the dropdown above.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(allele1 == null || allele1.equals("")){
            this.addToMessageQueue("First Allele is required, please select a first allele from the dropdown above.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(this.genotypePage == null || genotypePage.equals("")){
            this.addToMessageQueue("Page number is required, if no page give value 'none'.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(unitsModel.getTarget().isEmpty()){
            this.addToMessageQueue("Please select units to update from the pick list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(validateDate(genotypeDate)){
            flag = true;
        }
        if(!flag){
            try{
                //initialize genotype entity with informaiton that will be added to all mice
                GenotypeEntity ge = new GenotypeEntity();
                ge.setAll1Conf(allele1Conf);
                ge.setAll2Conf(allele2Conf);
                ge.setAllele1(allele1);
                ge.setAllele2(allele2);
                ge.setComment(comment);
                ge.setGenoPage(genotypePage);
                ge.setGeneKey(gene);
                ge.setGtDate(genotypeDate);
                ge.setSampleLocation(sampleLocation);
                
                SaveAppService sas = new SaveAppService();
                for(MouseEntity mouse : mice){
                    ge.setMouseKey(mouse);
                    //if mouse already has genotype for that gene don't allow add
                    if(bulkGenotypeDAO.mouseHasGenotype(mouse.getMouseKey().toString(), gene.getGeneKey().toString())){
                        this.addToMessageQueue("Mouse " + mouse.getId() + " already has a genotype for gene " + gene.getLabSymbol(), FacesMessage.SEVERITY_WARN);
                    }
                    else{
                        Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(ge);

                        if (null == pk || 0 == pk) {
                            ge.setGenotypeKey(1);
                        } else {
                            ge.setGenotypeKey(pk + 1);
                        }
                        sas.baseCreate(ge);
                        this.addToMessageQueue("Genotype " + gene.getLabSymbol() + " " + allele1 + " " + allele2 + " successfully added to mouse " + mouse.getId(), FacesMessage.SEVERITY_INFO);
                    }
                }
                
                //update
                if(updateUnit.equals("mouseID")){
                    unitsModel.setSource(dao.getIDUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }  
                else if(updateUnit.equals("litterID")){
                    unitsModel.setSource(dao.getLitterUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                else if(updateUnit.equals("cageID")){
                    unitsModel.setSource(dao.getCageUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                else if(updateUnit.equals("cageName")){
                    unitsModel.setSource(dao.getCageNameUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                unitsModel.setTarget(new ArrayList<String>());
                mice = new ArrayList<MouseEntity>();
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    /*
     * This method is called on change event on start date and end date to 
     * validate
     */
    public boolean validateDate(Date dt) {  
        boolean flag = false;
        
        // date cannot be greater than today
        if (dt != null && dt.compareTo(new Date()) > 0) {
            this.addToMessageQueue("Genotype Date cannot be greater than Today", 
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Genotype Date cannot be greater than Today"));
            flag = true;
        }
        return flag;
    }
 
    /**
     * @return the updateUnit
     */
    public String getUpdateUnit() {
        return updateUnit;
    }

    /**
     * @param updateUnit the updateUnit to set
     */
    public void setUpdateUnit(String updateUnit) {
        this.updateUnit = updateUnit;
    }

    /**
     * @return the mouseStrain
     */
    public StrainEntity getMouseStrain() {
        return mouseStrain;
    }

    /**
     * @param mouseStrain the mouseStrain to set
     */
    public void setMouseStrain(StrainEntity mouseStrain) {
        this.mouseStrain = mouseStrain;
    }

    /**
     * @return the wrapper
     */
    public SelectItemWrapper getWrapper() {
        return wrapper;
    }

    /**
     * @param wrapper the wrapper to set
     */
    public void setWrapper(SelectItemWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * @return the unitsModel
     */
    public DualListModel getUnitsModel() {
        return unitsModel;
    }

    /**
     * @param unitsModel the unitsModel to set
     */
    public void setUnitsModel(DualListModel unitsModel) {
        this.unitsModel = unitsModel;
    }

    /**
     * @return the mice
     */
    public ArrayList<MouseEntity> getMice() {
        return mice;
    }

    /**
     * @param mice the mice to set
     */
    public void setMice(ArrayList<MouseEntity> mice) {
        this.mice = mice;
    }

    /**
     * @return the selectPicklistLabel
     */
    public String getSelectPicklistLabel() {
        return selectPicklistLabel;
    }

    /**
     * @param selectPicklistLabel the selectPicklistLabel to set
     */
    public void setSelectPicklistLabel(String selectPicklistLabel) {
        this.selectPicklistLabel = selectPicklistLabel;
    }

    /**
     * @return the selectedPicklistLabel
     */
    public String getSelectedPicklistLabel() {
        return selectedPicklistLabel;
    }

    /**
     * @param selectedPicklistLabel the selectedPicklistLabel to set
     */
    public void setSelectedPicklistLabel(String selectedPicklistLabel) {
        this.selectedPicklistLabel = selectedPicklistLabel;
    }

    /**
     * @return the strain
     */
    public StrainEntity getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(StrainEntity strain) {
        this.strain = strain;
    }

    /**
     * @return the gene
     */
    public GeneEntity getGene() {
        return gene;
    }

    /**
     * @param gene the gene to set
     */
    public void setGene(GeneEntity gene) {
        this.gene = gene;
    }

    /**
     * @return the allele1Conf
     */
    public boolean isAllele1Conf() {
        return allele1Conf;
    }

    /**
     * @param allele1Conf the allele1Conf to set
     */
    public void setAllele1Conf(boolean allele1Conf) {
        this.allele1Conf = allele1Conf;
    }

    /**
     * @return the allele2Conf
     */
    public boolean isAllele2Conf() {
        return allele2Conf;
    }

    /**
     * @param allele2Conf the allele2Conf to set
     */
    public void setAllele2Conf(boolean allele2Conf) {
        this.allele2Conf = allele2Conf;
    }

    /**
     * @return the genotypePage
     */
    public String getGenotypePage() {
        return genotypePage;
    }

    /**
     * @param genotypePage the genotypePage to set
     */
    public void setGenotypePage(String genotypePage) {
        this.genotypePage = genotypePage;
    }

    /**
     * @return the sampleLocation
     */
    public String getSampleLocation() {
        return sampleLocation;
    }

    /**
     * @param sampleLocation the sampleLocation to set
     */
    public void setSampleLocation(String sampleLocation) {
        this.sampleLocation = sampleLocation;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the genotypeDate
     */
    public Date getGenotypeDate() {
        return genotypeDate;
    }

    /**
     * @param genotypeDate the genotypeDate to set
     */
    public void setGenotypeDate(Date genotypeDate) {
        this.genotypeDate = genotypeDate;
    }

    /**
     * @return the allele1s
     */
    public ArrayList<SelectItem> getAllele1s() {
        return allele1s;
    }

    /**
     * @param allele1s the allele1s to set
     */
    public void setAllele1s(ArrayList<SelectItem> allele1s) {
        this.allele1s = allele1s;
    }

    /**
     * @return the allele2s
     */
    public ArrayList<SelectItem> getAllele2s() {
        return allele2s;
    }

    /**
     * @param allele2s the allele2s to set
     */
    public void setAllele2s(ArrayList<SelectItem> allele2s) {
        this.allele2s = allele2s;
    }

    /**
     * @return the allele1
     */
    public String getAllele1() {
        return allele1;
    }

    /**
     * @param allele1 the allele1 to set
     */
    public void setAllele1(String allele1) {
        this.allele1 = allele1;
    }

    /**
     * @return the allele2
     */
    public String getAllele2() {
        return allele2;
    }

    /**
     * @param allele2 the allele2 to set
     */
    public void setAllele2(String allele2) {
        this.allele2 = allele2;
    }
}
