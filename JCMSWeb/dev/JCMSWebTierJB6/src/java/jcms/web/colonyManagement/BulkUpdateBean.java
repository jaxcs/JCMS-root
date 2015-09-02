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
import jcms.integrationtier.cv.CvDietEntity; 
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
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
import jcms.integrationtier.dto.ContainerDTO;
import org.primefaces.event.TransferEvent;  
import java.util.Date;

/**
 *
 * @author mkamato
 * updated 6-2-14 by bas to add phenotypes
 */
public class BulkUpdateBean extends WTBaseBackingBean implements Serializable {
    
    private String editCategory = "";
    private String updateUnit = "";
    private StrainEntity mouseStrain = null; 
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private CvDietEntity newDiet = null;
    private cvPhenotypeTermDTO addPhenotype = null;
    private LifeStatusEntity newLifeStatus = null;
    private CvCauseOfDeathEntity cod = null;
    private OwnerEntity newOwner = null;
    private CvMouseProtocolEntity newProtocol = null;
    private DualListModel unitsModel = new DualListModel();
    private BulkUpdateDAO dao = new BulkUpdateDAO();
    private ArrayList<MouseEntity> mice = new ArrayList<MouseEntity>();
    private String selectPicklistLabel = "";
    private String selectedPicklistLabel = "";
    private StrainEntity strain = null;
    private Date exitDate = null;
        
    public void updateUnitChangeListener(){
        if(strain != null){
            if(updateUnit.equals("mouseID")){
                unitsModel.setSource(dao.getIDUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                setSelectPicklistLabel("Select Mouse IDs");
                setSelectedPicklistLabel("Selected Mouse IDs");
            }  
            else if(updateUnit.equals("litterID")){
                unitsModel.setSource(dao.getLitterUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                setSelectPicklistLabel("Select Litter IDs");
                setSelectedPicklistLabel("Selected Litter IDs");
            }
            else if(updateUnit.equals("cageID")){
                unitsModel.setSource(dao.getCageUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                setSelectPicklistLabel("Select Cage IDs");
                setSelectedPicklistLabel("Selected Cage IDs");
            }
            unitsModel.setTarget(new ArrayList<String>());
            mice = new ArrayList<MouseEntity>();
        }
    }
    
    /**
     * Backing bean method to save changes to entries in the mosue table
     * to the database.
     */
    public void saveChanges(){
        //validate
        boolean flag = false;
        if(editCategory.equals("editDiet") && newDiet == null){
            this.addToMessageQueue("To update diet please select a diet from the list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        else if(editCategory.equals("editLifeStatus") && newLifeStatus == null){
            this.addToMessageQueue("To update life status please select a life status from the list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        else if(editCategory.equals("editOwner") && newOwner == null){
            this.addToMessageQueue("To update owner please select a owner from the list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        else if(editCategory.equals("editProtocol") && newProtocol == null){
            this.addToMessageQueue("To update protocol please select a protocol from the list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        
        if(editCategory.equals("editLifeStatus") 
                && newLifeStatus != null
                && newLifeStatus.getExitStatus() 
                && (exitDate == null || cod == null)){
            this.addToMessageQueue("To change a life status to an exit status an exit date and cause of death are required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        
        
        //if everything's okay make updates
        if(!flag){
            try{
                //update mouse value
                for(MouseEntity mouse : mice){
                    if(editCategory.equals("editDiet")){
                        mouse.setDiet(newDiet.getDiet());
                    }
                    else if(editCategory.equals("editLifeStatus")){
                        mouse.setLifeStatus(newLifeStatus.getLifeStatus());
                        if(newLifeStatus.getExitStatus()){
                            mouse.setExitDate(exitDate);
                            mouse.setCod(cod.getCod());
                        }
                        else{
                            mouse.setExitDate(null);
                            mouse.setCod(null);
                        }
                    }
                    else if(editCategory.equals("editOwner")){                    
                        mouse.setOwner(newOwner.getOwner());
                    }
                    else if(editCategory.equals("editProtocol")){
                        mouse.setProtocol(newProtocol.getId());
                    }
                }
                dao.updateMouse(mice);
                this.addToMessageQueue("Mouse changes successfully saved.", FacesMessage.SEVERITY_INFO);

                //reset view
                if(updateUnit.equals("mouseID")){
                    unitsModel.setSource(dao.getIDUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }  
                else if(updateUnit.equals("litterID")){
                    unitsModel.setSource(dao.getLitterUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                else if(updateUnit.equals("cageID")){
                    unitsModel.setSource(dao.getCageUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                unitsModel.setTarget(new ArrayList<String>());
                mice = new ArrayList<MouseEntity>();
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
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
                if(updateUnit.equals("cageID")){
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
                        mice.add(me);
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
                if(updateUnit.equals("cageID")){
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
    
    public void removeMouseListener(){
        Integer key = this.getKey("paramRowIndex");
        mice.remove(key.intValue());
    }
    
    public void updateLifeStatus(){
        boolean flag = false;
        if(newLifeStatus == null){
            this.addToMessageQueue("Please select a new life status from the drop down below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        //use the list of mice instead of the target list of mouse IDs
        if(mice.isEmpty()){
            this.addToMessageQueue("Please select mice to update from the pick list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        
        if(!flag){
            ArrayList<String> keys = new ArrayList<String>();
            for(MouseEntity mouse : mice){
                String mouseKey =  mouse.getMouseKey().toString();
                keys.add(mouseKey);
            }
            
            try{
                //use the list of mice instead of the target list of mouse IDs
                //At this point then, don't need to differentiate between the selection of mouse ID, cage, or litter               
                dao.updateMiceAccordingToMouseKeys(keys, newLifeStatus);
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
                unitsModel.setTarget(new ArrayList<String>());
                this.addToMessageQueue("Life status successfully updated.", FacesMessage.SEVERITY_INFO);
                mice = new ArrayList<MouseEntity>();
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void updateDiet(){
        boolean flag = false;
        
        if(newDiet == null){
            this.addToMessageQueue("Please select a new diet from the drop down below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        //use the list of mice instead of the target list of mouse IDs
        if(mice.isEmpty()){
            this.addToMessageQueue("Please select mice to update from the pick list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        
        if(!flag){
            ArrayList<String> keys = new ArrayList<String>();
            for(MouseEntity mouse : mice){
                String mouseKey =  mouse.getMouseKey().toString();
                keys.add(mouseKey);
            }
            
            try{
                //use the list of mice instead of the target list of mouse IDs
                //At this point then, don't need to differentiate between the selection of mouse ID, cage, or litter               
                dao.updateMiceDietAccordingToMouseKeys(keys, newDiet.getDiet());
             
                
                
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
                unitsModel.setTarget(new ArrayList<String>());
                this.addToMessageQueue("Diet successfully updated.", FacesMessage.SEVERITY_INFO);
                mice = new ArrayList<MouseEntity>();
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void addNewPhenotype(){
        boolean flag = false;
        
        if(addPhenotype == null) {
            this.addToMessageQueue("Please select a phenotype term from the drop down below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }

        //Change to look at the mouse list
        if(mice.isEmpty()){
            this.addToMessageQueue("Please select mice to update from the pick list below.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        
        if(!flag){        
            try{
                //use the list of mice instead of the target list of mouse IDs
                //At this point then, don't need to differentiate between the selection of mouse ID, cage, or litter                    
                    String pName = addPhenotype.getPhenotypeTermName();
                    for(MouseEntity mouse : mice){
                        String mKey =  mouse.getMouseKey().toString();
                        
                        // Must verify that the phenotype is not already added, otherwise we'll have a duplicate
                        if(dao.mouseHasPhenotype(mKey, pName)){
                            this.addToMessageQueue("Mouse " + mouse.getId() + " already has the phenotype " + pName, FacesMessage.SEVERITY_WARN);
                        }
                        else{
                            dao.addMicePhenotypeAccordingToKeys(mKey, pName);
                        }
                    }
                
                //re-do the source and target lists
                if(updateUnit.equals("mouseID")){
                    unitsModel.setSource(dao.getIDUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }  
                else if(updateUnit.equals("litterID")){
                    unitsModel.setSource(dao.getLitterUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                else if(updateUnit.equals("cageID")){
                    unitsModel.setSource(dao.getCageUnits(strain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups()));
                }
                unitsModel.setTarget(new ArrayList<String>());
                this.addToMessageQueue("Phenotype successfully added.", FacesMessage.SEVERITY_INFO);
                mice = new ArrayList<MouseEntity>();
            }

        catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    /**
     * @return the editCategory
     */
    public String getEditCategory() {
        return editCategory;
    }

    /**
     * @param editCategory the editCategory to set
     */
    public void setEditCategory(String editCategory) {
        this.editCategory = editCategory;
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
     * @return the newDiet
     */
    public CvDietEntity getNewDiet() {
        return newDiet;
    }

    /**
     * @param newDiet the newDiet to set
     */
    public void setNewDiet(CvDietEntity newDiet) {
        this.newDiet = newDiet;
    }

    /**
     * @return the newLifeStatus
     */
    public LifeStatusEntity getNewLifeStatus() {
        return newLifeStatus;
    }

    /**
     * @param newLifeStatus the newLifeStatus to set
     */
    public void setNewLifeStatus(LifeStatusEntity newLifeStatus) {
        this.newLifeStatus = newLifeStatus;
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
     * @return the addPhenotype
     */
    public cvPhenotypeTermDTO getAddPhenotype() {
        return addPhenotype;
    }

    /**
     * @param addPhenotype the addPhenotype to set
     */
    public void setAddPhenotype(cvPhenotypeTermDTO addPhenotype) {
        this.addPhenotype = addPhenotype;
    }

    /**
     * @return the newOwner
     */
    public OwnerEntity getNewOwner() {
        return newOwner;
    }

    /**
     * @param newOwner the newOwner to set
     */
    public void setNewOwner(OwnerEntity newOwner) {
        this.newOwner = newOwner;
    }

    /**
     * @return the newProtocol
     */
    public CvMouseProtocolEntity getNewProtocol() {
        return newProtocol;
    }

    /**
     * @param newProtocol the newProtocol to set
     */
    public void setNewProtocol(CvMouseProtocolEntity newProtocol) {
        this.newProtocol = newProtocol;
    }

    /**
     * @return the exitDate
     */
    public Date getExitDate() {
        return exitDate;
    }

    /**
     * @param exitDate the exitDate to set
     */
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    /**
     * @return the cod
     */
    public CvCauseOfDeathEntity getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(CvCauseOfDeathEntity cod) {
        this.cod = cod;
    }
}
