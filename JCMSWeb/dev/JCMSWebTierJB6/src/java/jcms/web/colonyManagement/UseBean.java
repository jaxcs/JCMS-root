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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.service.RepositoryService;
import javax.faces.model.SelectItem;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.dto.cvMouseUseDTO;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.web.colonyManagement.MouseFunctionsBean;
import jcms.web.common.ExtendedTableBean;
/**
 *
 * @author mkamato
 */
public class UseBean extends WTBaseBackingBean implements Serializable {
    
    private MouseUseDAO useDAO = new MouseUseDAO();
    private MouseEntity mouse = null;
    private String mouseID = "";
    private String useKey = "";
    private String useAgeDetermination = "manual";
    private String useAgeIn = "days";
    private String mouseGenotype = "";
    
    //used to calculated use age
    private String DPC = "";
    private String plugdate = "";
    
    //translated to days and then placed in the dto before insertion/edit
    private String useAge = "";    
        
    //used in the view to determine behavior
    private boolean autoIncrementId;
    
    //provide dropdowns in the view
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private ArrayList<SelectItem> plugDates = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> daysPostConception = null;
    ArrayList<OwnerEntity> ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");
    
    private cvMouseUseDTO selectedUse = new cvMouseUseDTO();
    private ArrayList<MouseUsageDTO> uses = new ArrayList<MouseUsageDTO>();
    private boolean editing = false;
    private MouseUsageDTO mouseUsage = new MouseUsageDTO();
    
    //edit Specific Vars
    private Integer editUsageKey = 0;
    private Integer editingRowKey;
    private Integer deletingRowKey;
    private boolean dontClearData = false;
    
    private MouseFunctionsBean mouseFunctions = new MouseFunctionsBean();
    
    //mouse search objects
    private MouseSearchDTO mouseSearch = new MouseSearchDTO();
    private ListDataModel mouseDataModel = null;
    private MiceListCommon mouseInfo = new MiceListCommon();
    private ExtendedTableBean mouseSelectionETB = new ExtendedTableBean();
    
    
    public UseBean(){
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_ADD_MOUSE_USE_INCREMENT");

        if (entity != null) {
            DbsetupEntity setupvar = (DbsetupEntity) entity;
            if(setupvar.getMTSValue().equalsIgnoreCase("true")){
                autoIncrementId = true;
            }
            else{
                autoIncrementId = false;
            }
        }
    }
    
    public boolean validateAddEditChange(){
        boolean flag = false;
        boolean owned = false;
        if(mouse != null){
            for(OwnerEntity owner : ownerLst){
                if(owner.getOwner().equalsIgnoreCase(mouse.getOwner())){
                    owned = true;
                }
            }
            //make sure user is in workgroup that owns mouse
            if(!owned){
                flag = true;
                this.addToMessageQueue("You are not the owner of that mouse, please select another mouse.", FacesMessage.SEVERITY_ERROR);
            }
        }
        //make sure there is a mouse use selected
        if(this.selectedUse.getMouseUse().equals("")){
            flag = true;
            this.addToMessageQueue("Mouse Use is required, please select a use.", FacesMessage.SEVERITY_ERROR);
        }   
        //make sure mouse exists
        if(this.getMouse() == null){
            flag = true;
            this.addToMessageQueue("Mouse is required, please select a mouse.", FacesMessage.SEVERITY_ERROR);
        }
        //make sure use age is a float
        try{
            if(!useAge.equals("")){
                Double.parseDouble(useAge);
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Use age must be a number, please select a correct use age.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        return flag;
    }
    
    public void saveUseAction(){
        try{
            if(!validateAddEditChange()){
                String tempUseAgeIn = useAgeIn;
                String tempUseAge = useAge;
                //convert use age to days
                if(!useAgeIn.equals("days")){
                    //turns use Age into days from whatever it was.
                    
                    setUseAgeIn("days");
                    //set use age in dto to this new calculated value
                    mouseUsage.setUseAge(useAge);
                    useAgeIn = tempUseAgeIn;
                }
                else{
                    mouseUsage.setUseAge(useAge);
                }
                                
                //ADD THE USE TO THE DATABASE BELOW
                //set the mouseusage key to the key for the usage you just inserted
                mouseUsage.setMouseKey(mouse.getMouseKey().toString());
                mouseUsage.setUsageKey(new Integer(useDAO.addMouseUsage(mouseUsage)).toString());
                
                this.addToMessageQueue("Mouse use successfully added!", FacesMessage.SEVERITY_INFO);
                                
                //add mouse usage to the table in the view
                uses.add(mouseUsage);
                
                //set use age back to user's original units (weeks, months...) will be cleared if user doesn't have clear data selected
                useAge = tempUseAge;
                //duplicate mouseUsage object so there isn't a pointer ref. to original mouse usage that causes change in data table
                //when not clearing data on submit
                mouseUsage = new MouseUsageDTO(mouseUsage);
                
                //reset the view if don't clear data isn't selected.
                if(!dontClearData){
                    mouseUsage = new MouseUsageDTO();
                    selectedUse = new cvMouseUseDTO();
                    useAge = "0";
                    useKey = "";
                }
                
                if(autoIncrementId){
                    mouseID = mouseFunctions.incrementID(mouseID, "JCMS_MOUSEID_INCREMENT_RIGHTMOST");
                    try{
                        mouse = this.mouseFunctions.findMouse(mouseID);
                        uses = new ArrayList<MouseUsageDTO>();
                        if(mouse == null){
                            this.addToMessageQueue("Mouse with ID " + mouseID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                        }
                        else{
                            //check whether mouse is owned by workgroup user is member of.
                            boolean owned = false;
                            for(OwnerEntity owner : ownerLst){
                                if(owner.getOwner().equalsIgnoreCase(mouse.getOwner())){
                                    owned = true;
                                }
                            }
                            if(owned){
                                //get plug dates (if any)
                                plugDates = this.findPlugDateList();
                                //get previous experiments (if any)
                                uses = useDAO.getMouseUsagesByMouseKey(mouse.getMouseKey().toString());
                                mouseUsage.setMouseKey(mouse.getMouseKey().toString());
                            }
                            else{
                                this.addToMessageQueue("You are not the owner of this mouse. Please select another mouse ID.", FacesMessage.SEVERITY_ERROR);
                            }
                        }
                    }
                    catch(Exception e){
                        this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Insert could not be completed: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Insert could not be completed: " + e));
        }
    }
        
    public void daysAfterBirthChangeListener(){
        Calendar cal = Calendar.getInstance();
        if(useAgeDetermination.equals("manual")){
            if(!useAge.equals("")){
                cal.setTime(mouse.getBirthDate());
                if(useAgeIn.equals("days")){
                    cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(useAge));  
                    mouseUsage.setProjectedDate(cal.getTime());
                }
                else if(useAgeIn.equals("weeks")){
                    //convert from weeks to days
                    Float valueInDays = Float.parseFloat(useAge) * 7;
                    cal.add(Calendar.DAY_OF_MONTH, Math.round(valueInDays)); 
                    mouseUsage.setProjectedDate(cal.getTime());
                }
                else{
                    Float valueInDays = Float.parseFloat(useAge) * new Float(30.4375);
                    cal.add(Calendar.DAY_OF_MONTH, Math.round(valueInDays));
                    mouseUsage.setProjectedDate(cal.getTime());
                }
            }
            else{
                mouseUsage.setProjectedDate(null);
            }
        }
        else{
            try{
                if(!DPC.equals("") && !plugdate.equals("")){
                    Date thePlugdate = new SimpleDateFormat(this.getDate_format()).parse(plugdate);
                    cal.setTime(thePlugdate);
                    cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(DPC));
                    mouseUsage.setProjectedDate(cal.getTime());
                    mouseUsage.setUseAge(useAge);
                    Calendar birthdate = Calendar.getInstance();
                    birthdate.setTime(mouse.getBirthDate());
                    calculateUseAge(birthdate, cal);
                }
                else{
                    mouseUsage.setProjectedDate(null);
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }
        }
    }
    
    private void calculateUseAge(Calendar birthdate, Calendar useDate){
        int days = 0;
        while(birthdate.before(useDate)){
            birthdate.add(Calendar.DAY_OF_MONTH, 1);
            days++;
        }
        if(useAgeIn.equals("days")){
            useAge = new Integer(days).toString();
        }
        else if(useAgeIn.equals("months")){
            useAge = new Float(new Float(days)/30.4375).toString();
        }
        else{
            useAge = new Float(new Float(days)/7).toString();
        }
    }
    
    public void editUseAction(){
        try{
            if(!validateAddEditChange()){
                if(!getUseAgeIn().equals("days")){
                    //turns use Age into days from whatever it was.
                    setUseAgeIn("days");
                    //set use age in dto to this new calculated value
                    mouseUsage.setUseAge(useAge);
                }
                else{
                    mouseUsage.setUseAge(useAge);
                }
                
                
                
                useDAO.editMouseUsage(mouseUsage);
                this.addToMessageQueue("Mouse use successfully edited!", FacesMessage.SEVERITY_INFO);
                
                
                //CHANGE THE VIEW
                MouseUsageDTO removeDTO = null;
               
                for(MouseUsageDTO dto:uses){
                    if(dto.getUsageKey().equals(mouseUsage.getUsageKey())){
                        removeDTO = dto;
                    }
                }
                uses.add(uses.lastIndexOf(removeDTO), mouseUsage);
                uses.remove(removeDTO);
                editing = false;
                
                //reset view
                cancelEditUseListener();
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Edit could not be completed: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Edit could not be completed: " + e));
        }
    }
    
    public void deleteUseAction(){
        try{

            //remove the actual use from the DB
            useDAO.deleteMouseUsage(deletingRowKey.toString());

            //remove the use from the view
            MouseUsageDTO removeDTO = null;
            for(MouseUsageDTO dto : uses){
                if(dto.getUsageKey().equals(deletingRowKey.toString())){
                    removeDTO = dto;
                }
            }
            uses.remove(removeDTO);
            this.addToMessageQueue("Mouse use successfully deleted.", FacesMessage.SEVERITY_INFO);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mouse use successfully deleted."));
        }
        catch(Exception e){
            this.addToMessageQueue("Deletion could not be completed: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Deletion could not be completed: " + e));
        }
    }
    
    /*This method may be really stupid, but I didn't want to import SelectItems into integration tier and this guarantees
     that plugdate matches up with its key, so get over it.*/
    private ArrayList<SelectItem> findPlugDateList(){
        ArrayList<SelectItem> plugs = new ArrayList<SelectItem>();
        plugs.add(new SelectItem("",""));
        ArrayList<String> plugsAndKeys = useDAO.getPlugsByMouseKey(mouse.getKey().toString());
        boolean key = true;
        SelectItem si = new SelectItem();
        for(String str : plugsAndKeys){
            //first value in plugsAndKeys is the key
            if(key){
              //  si.setValue(str);
            }
            //second value is plugdate (the label)
            else{
               si.setLabel(str);
               si.setValue(str);
               //at this point SelectItem has both a label and a value, add it to the list (will be displayed on UI)
               plugs.add(si);
               //create new SelectItem
               si = new SelectItem();
            }
            key = !key;
        }
        return plugs;
    }
    
    public void mouseValueChangeListener(ValueChangeEvent event){
        try{
            //reset view variables
            editing = false;
            editingRowKey = null;
            
            mouseGenotype = "";
            uses = new ArrayList<MouseUsageDTO>();
            plugDates = new ArrayList<SelectItem>();
            mouseUsage.setMouseKey("");
            
            mouse = this.mouseFunctions.findMouse(mouseID);
            
            if(mouse == null){
                this.addToMessageQueue("Mouse with ID " + mouseID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                useAgeIn = "days";
                useKey = "";
                useAge = "";
                mouseUsage = new MouseUsageDTO();
                selectedUse = new cvMouseUseDTO();
                useAgeDetermination = "manual";
            }
            else{
                //check whether mouse is owned by workgroup user is member of.
                boolean owned = false;
                for(OwnerEntity owner : ownerLst){
                    if(owner.getOwner().equalsIgnoreCase(mouse.getOwner())){
                        owned = true;
                    }
                }
                if(owned){
                    //get plug dates (if any)
                    plugDates = this.findPlugDateList();
                    //get previous experiments (if any)
                    uses = useDAO.getMouseUsagesByMouseKey(mouse.getMouseKey().toString());
                    mouseUsage.setMouseKey(mouse.getMouseKey().toString());
                    mouseGenotype = useDAO.getMouseGenotypeByMouseKey(mouse.getMouseKey().toString());
                }
                else{
                    this.addToMessageQueue("You are not the owner of this mouse. Please select another mouse ID.", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public String navigateUse(){
        return "addUse";
    }
    
    public void useValueChangeListener(){
        if(!useKey.equals("")){
            selectedUse = useDAO.getCvMouseUseByKey(useKey);
            mouseUsage.setUse(selectedUse.getMouseUse());
        }
        else{
            selectedUse = new cvMouseUseDTO();
        }
    }
    
    public void editUseListener(){
        editUsageKey = this.getKey("paramUsageKey");
        editingRowKey = this.getKey("paramRowIndex");
        for(MouseUsageDTO dto : uses){
            if(dto.getUsageKey().equals(editUsageKey.toString())){
                mouseUsage = new MouseUsageDTO(dto);
            }
        }
        editing = true;
        selectedUse = useDAO.getCvMouseUseByUse(mouseUsage.getUse());
        useKey = selectedUse.getMouseUseKey();
        useAgeDetermination = "manual";
        useAgeIn = "days";
        useAge = mouseUsage.getUseAge();        
    }
    
    public void cancelEditUseListener(){
        editUsageKey = null;
        editingRowKey = null;
        mouseUsage = new MouseUsageDTO();
        editing = false;
        selectedUse = new cvMouseUseDTO();
        useKey = "";
        useAgeDetermination = "manual";
        useAge = "";
        useAgeIn = "days";
    }
    
    public void deleteUseListener(){
        deletingRowKey = this.getKey("paramUsageKey");
    }
    
    public void selectMouseAction(){

        if (getMouseInfo() != null) {
            mouse = getMouseInfo().getSelectedMouse(this.getMouseSelectionETB(), this.mouseDataModel);

            if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
                System.out.println("Mouse is: " + mouse.getId());
                mouseID = mouse.getId();
                mouseValueChangeListener(null);
                clearMousePopupAction();
            } 
            else {
                this.addToMessageQueue("You must select a mouse to proceed.", FacesMessage.SEVERITY_ERROR);
            }
        } 
        else {
            this.addToMessageQueue("You must select a mouse to proceed.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void executeMouseSearch(){
        System.out.println("starting mouse search...");
        mouseDataModel = new ListDataModel();
        mouseDataModel.setWrappedData(mouseInfo.miceSearch(mouseSearch));
        System.out.println("holder");
    }
    
    public void clearMousePopupAction() {
        mouseDataModel = null;
        this.setMouseSearch(new MouseSearchDTO());
        wrapper = new SelectItemWrapper();
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
     * @return the useKey
     */
    public String getUseKey() {
        return useKey;
    }

    /**
     * @param useKey the useKey to set
     */
    public void setUseKey(String useKey) {
        this.useKey = useKey;
    }

    /**
     * @return the useAgeDetermination
     */
    public String getUseAgeDetermination() {
        return useAgeDetermination;
    }

    /**
     * @param useAgeDetermination the useAgeDetermination to set
     */
    public void setUseAgeDetermination(String useAgeDetermination) {
        this.useAgeDetermination = useAgeDetermination;
    }

    /**
     * @return the DPC
     */
    public String getDPC() {
        return DPC;
    }

    /**
     * @param DPC the DPC to set
     */
    public void setDPC(String DPC) {
        this.DPC = DPC;
    }

    /**
     * @return the plugdate
     */
    public String getPlugdate() {
        return plugdate;
    }

    /**
     * @param plugdate the plugdate to set
     */
    public void setPlugdate(String plugdate) {
        this.plugdate = plugdate;
    }

    /**
     * @return the useAgeIn
     */
    public String getUseAgeIn() {
        return useAgeIn;
    }

    /**
     * @param useAgeIn the useAgeIn to set
     */
    public void setUseAgeIn(String useAgeIn) {
        //this.useAgeIn is the previous value, useAgeIn is the current value
        try{
            if(this.useAgeIn.equals("days")){
                if(useAgeIn.equals("months")){
                    useAge = new Float(Float.parseFloat(useAge) / 30.4375).toString();
                }
                else if(useAgeIn.equals("weeks")){
                    useAge = new Float(Float.parseFloat(useAge) / 7).toString();
                }
            }
            else if(this.useAgeIn.equals("months")){
                if(useAgeIn.equals("days")){
                    useAge = new Float(Float.parseFloat(useAge) * 30.4375).toString();
                }
                else if(useAgeIn.equals("weeks")){
                    useAge = new Float((Float.parseFloat(useAge) * 30.4375) / 7).toString();
                }
            }
            else if(this.useAgeIn.equals("weeks")){
                if(useAgeIn.equals("days")){
                    useAge = new Float(Float.parseFloat(useAge) * 7).toString();
                }
                else if(useAgeIn.equals("months")){
                    useAge = new Float((Float.parseFloat(useAge) * 7) / 30.4375).toString();
                }
            }
        }
        catch(Exception e){}
        this.useAgeIn = useAgeIn;
    }

    /**
     * @return the mouse
     */
    public MouseEntity getMouse() {
        return mouse;
    }

    /**
     * @param mouse the mouse to set
     */
    public void setMouse(MouseEntity mouse) {
        this.mouse = mouse;
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
     * @return the autoIncrementId
     */
    public boolean isAutoIncrementId() {
        return autoIncrementId;
    }

    /**
     * @param autoIncrementId the autoIncrementId to set
     */
    public void setAutoIncrementId(boolean autoIncrementId) {
        this.autoIncrementId = autoIncrementId;
    }

    /**
     * @return the plugDates
     */
    public ArrayList<SelectItem> getPlugDates() {
        return plugDates;
    }

    /**
     * @param plugDates the plugDates to set
     */
    public void setPlugDates(ArrayList<SelectItem> plugDates) {
        this.plugDates = plugDates;
    }

    /**
     * @return the daysPostConception
     */
    public ArrayList<SelectItem> getDaysPostConception() {
        if(daysPostConception == null){
            daysPostConception = new ArrayList<SelectItem>();
            daysPostConception.add(new SelectItem("", ""));
            daysPostConception.add(new SelectItem(".5", ".5"));
            daysPostConception.add(new SelectItem("1", "1.5"));
            daysPostConception.add(new SelectItem("2", "2.5"));
            daysPostConception.add(new SelectItem("3", "3.5"));
            daysPostConception.add(new SelectItem("4", "4.5"));
            daysPostConception.add(new SelectItem("5", "5.5"));
            daysPostConception.add(new SelectItem("6", "6.5"));
            daysPostConception.add(new SelectItem("7", "7.5"));  
            daysPostConception.add(new SelectItem("8", "8.5"));  
            daysPostConception.add(new SelectItem("9", "9.5"));  
            daysPostConception.add(new SelectItem("10", "10.5"));  
            daysPostConception.add(new SelectItem("11", "11.5"));
            daysPostConception.add(new SelectItem("12", "12.5"));
            daysPostConception.add(new SelectItem("13", "13.5"));
            daysPostConception.add(new SelectItem("14", "14.5"));
            daysPostConception.add(new SelectItem("15", "15.5"));
            daysPostConception.add(new SelectItem("16", "16.5"));
            daysPostConception.add(new SelectItem("17", "17.5"));
            daysPostConception.add(new SelectItem("18", "18.5"));
            daysPostConception.add(new SelectItem("19", "19.5"));
            daysPostConception.add(new SelectItem("20", "20.5"));
            daysPostConception.add(new SelectItem("21", "21.5"));
        }
        return daysPostConception;
    }

    /**
     * @param daysPostConception the daysPostConception to set
     */
    public void setDaysPostConception(ArrayList<SelectItem> daysPostConception) {
        this.daysPostConception = daysPostConception;
    }

    /**
     * @return the selectedUse
     */
    public cvMouseUseDTO getSelectedUse() {
        return selectedUse;
    }

    /**
     * @param selectedUse the selectedUse to set
     */
    public void setSelectedUse(cvMouseUseDTO selectedUse) {
        this.selectedUse = selectedUse;
    }

    /**
     * @return the uses
     */
    public ArrayList<MouseUsageDTO> getUses() {
        return uses;
    }

    /**
     * @param uses the uses to set
     */
    public void setUses(ArrayList<MouseUsageDTO> uses) {
        this.uses = uses;
    }

    /**
     * @return the mouseUsage
     */
    public MouseUsageDTO getMouseUsage() {
        return mouseUsage;
    }

    /**
     * @param mouseUsage the mouseUsage to set
     */
    public void setMouseUsage(MouseUsageDTO mouseUsage) {
        this.mouseUsage = mouseUsage;
    }

    /**
     * @return the editing
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * @param editing the editing to set
     */
    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    /**
     * @return the editingRowKey
     */
    public Integer getEditingRowKey() {
        return editingRowKey;
    }

    /**
     * @param editingRowKey the editingRowKey to set
     */
    public void setEditingRowKey(Integer editingRowKey) {
        this.editingRowKey = editingRowKey;
    }

    /**
     * @return the useAge
     */
    public String getUseAge() {
        return useAge;
    }

    /**
     * @param useAge the useAge to set
     */
    public void setUseAge(String useAge) {
        this.useAge = useAge;
    }

    /**
     * @return the dontClearData
     */
    public boolean isDontClearData() {
        return dontClearData;
    }

    /**
     * @param dontClearData the dontClearData to set
     */
    public void setDontClearData(boolean dontClearData) {
        this.dontClearData = dontClearData;
    }

    /**
     * @return the mouseGenotype
     */
    public String getMouseGenotype() {
        return mouseGenotype;
    }

    /**
     * @param mouseGenotype the mouseGenotype to set
     */
    public void setMouseGenotype(String mouseGenotype) {
        this.mouseGenotype = mouseGenotype;
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
}
