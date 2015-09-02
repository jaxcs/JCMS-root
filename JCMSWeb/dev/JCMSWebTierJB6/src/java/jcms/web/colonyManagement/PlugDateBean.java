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
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import jcms.web.dto.PlugDateHelperDTO;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.PlugDateDAO;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.PlugDateDTO;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.web.base.WTBaseBackingBean;
import javax.faces.model.SelectItem;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.dao.EditMatingDAO;
import java.util.Date;
import java.text.SimpleDateFormat;
import jcms.web.common.MouseScheduleUtilities;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import org.primefaces.model.DualListModel;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dto.MouseDTO;

/**
 *
 * @author mkamato
 */
public class PlugDateBean extends WTBaseBackingBean implements Serializable{
    
    //plug date useSchedule key
    private static String Plug_Date_Start_Event_Key = "2";
    
    //for plug date in view
    private String matingID = "";
    private boolean dam1Selected = false;
    private boolean dam2Selected = false;
    private boolean obsolete = false;
    private Date plugDate = null;
    private String comments = "";
    private ArrayList<PlugDateDTO> dam1PlugDates = null;
    private ArrayList<PlugDateDTO> dam2PlugDates = null;
    private ArrayList<PlugDateHelperDTO> mouseUses = new ArrayList<PlugDateHelperDTO>();
    private ArrayList<MouseUsageDTO> dam1Uses = null;
    private ArrayList<MouseUsageDTO> dam2Uses = null;
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private ArrayList<SelectItem> daysPostConception = null;
    private boolean editing = false;
    private Integer deleteKey = null;
    //Mouse Uses
    private CvMouseUseEntity mouseUse = null;
    private String DPC = "";
    private Integer editUsageKey;
    private Integer editingUsageRowKey;
    private Integer editPlugDateKey;
    private Integer editingPlugDateRowKey;
    private Integer editDam2PlugDateKey;
    private Integer editingDam2PlugDateRowKey;
    
    private PlugDateDAO plugDateDAO = new PlugDateDAO();
    private EditMatingDAO editMatingDAO = new EditMatingDAO();
    private MouseUseDAO mouseUseDAO = new MouseUseDAO();
    private MatingEntity matingEntity = null;
    private MouseEntity dam1 = null;
    private MouseEntity dam2 = null;
    private MouseEntity sire = null;
    private String dam1Genotype = "";
    private String dam2Genotype = "";
    private String sireGenotype = "";
    private DualListModel useSchedulesModel = new DualListModel();
    private MouseScheduleUtilities pUtils = new MouseScheduleUtilities();
    
    public PlugDateBean(){
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
    
    
    public void changeMatingListener(){
        if(matingID == null || matingID.equals("")){
            dam1 = null;
            dam2 = null;
            sire = null;
            matingEntity = null;
            return;
        }
        try{
            Integer.parseInt(matingID);            
        }
        catch(Exception e){
            dam1 = null;
            dam2 = null;
            sire = null;
            matingEntity = null;
            this.addToMessageQueue("Mating ID must be an integer.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating ID must be an integer."));
            return;
        }
        try{
            int matingKey = Integer.parseInt(editMatingDAO.getMatingKeyByMatingID(matingID));
            if(matingOwnerCheck(new Integer(matingKey).toString())){
                matingEntity = (MatingEntity) getRepositoryService().baseFind(new MatingEntity(matingKey));
                if(matingEntity != null){
                    dam1 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam1Key()));
                    dam1PlugDates = plugDateDAO.getPlugsByMouseAndMatingKey(dam1.getMouseKey().toString(), matingEntity.getMatingKey().toString());
                    for(PlugDateDTO plug : dam1PlugDates){
                        if(plug.getObsolete().equalsIgnoreCase("false")){
                            plug.setObsolete("no");
                        }
                        else if(plug.getObsolete().equalsIgnoreCase("true")){
                            plug.setObsolete("yes");
                        }
                    }
                    dam1Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam1.getMouseKey().toString());
                    dam1Genotype = mouseUseDAO.getMouseGenotypeByMouseKey(dam1.getMouseKey().toString());
                    //dam2 info
                    if(matingEntity.getDam2Key() != null){
                        dam2 = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(matingEntity.getDam2Key()));
                        dam2PlugDates = plugDateDAO.getPlugsByMouseAndMatingKey(dam2.getMouseKey().toString(), matingEntity.getMatingKey().toString());
                        for(PlugDateDTO plug : dam2PlugDates){
                            if(plug.getObsolete().equalsIgnoreCase("false")){
                                plug.setObsolete("no");
                            }
                            else if(plug.getObsolete().equalsIgnoreCase("true")){
                                plug.setObsolete("yes");
                            }
                        }
                        dam2Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam2.getMouseKey().toString());
                        dam2Genotype = mouseUseDAO.getMouseGenotypeByMouseKey(dam2.getMouseKey().toString());
                    }
                    else{
                        dam2 = null;
                        dam2Selected = false;
                    }
                    sire = matingEntity.getSireKey();
                    sireGenotype = mouseUseDAO.getMouseGenotypeByMouseKey(sire.getMouseKey().toString());
                }
            }
            else{
                this.addToMessageQueue("You are not the owner of that mating.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "You are not the owner of that mating."));
                dam1 = null;
                dam2 = null;
                sire = null;
                matingEntity = null;
            }
        }
        catch(NumberFormatException e){
            dam1 = null;
            dam2 = null;
            sire = null;
            matingEntity = null;
            this.addToMessageQueue("Mating with matingID " + matingID + " could not be found.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Mating of number " + matingID + " could not be found."));
        }
    }
        
    private boolean matingOwnerCheck(String key){
        try{
            ArrayList<OwnerEntity> ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"); 
            String matingOwner = editMatingDAO.getMatingOwnerByKey(new Integer(key).toString());
            for(OwnerEntity o : ownerLst){
                if(o.getOwner().equalsIgnoreCase(matingOwner)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
            return false;
        }
    }
    
    public void addPlugDate(){
        boolean flag = false;
        if(!dam1Selected && !dam2Selected){
            flag = true;
            this.addToMessageQueue("Either Dam1 or Dam2 must be selected.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Either Dam1 or Dam2 must be selected."));
        }
        if(plugDate == null){
            flag = true;
            this.addToMessageQueue("Plug date is required.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Plug date is required."));
        }
        if(matingEntity == null){
            flag = true;
            this.addToMessageQueue("Plug date is required.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Plug date is required."));
        }
        if(dam1Selected){
            for(PlugDateDTO pgDTO : dam1PlugDates){
                if(pgDTO.getPlugDate().equals(plugDate)){
                    flag = true;
                    this.addToMessageQueue("Dam1 already contains a plug date for " + plugDate, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Dam1 already contains a plug date for " + plugDate));
                }
            }
        }
        if(dam2Selected){
            for(PlugDateDTO pgDTO : dam2PlugDates){
                if(pgDTO.getPlugDate().equals(plugDate)){
                    flag = true;
                    this.addToMessageQueue("Dam2 already contains a plug date for " + plugDate, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Dam2 already contains a plug date for " + plugDate));
                }
            }
        }
        if(!flag){
            if(dam1Selected){
                PlugDateDTO dto = new PlugDateDTO();
                dto.setComment(comments);
                dto.setMating_key(matingEntity.getMatingKey().toString());
                if(obsolete){
                    dto.setObsolete("-1");
                }
                else{
                    dto.setObsolete("0");
                }
                dto.setPlugDate(plugDate);
                dto.setMouse_key(dam1.getMouseKey().toString());
                try{
                    dto.setPlugDateKey(plugDateDAO.insertPlugDate(dto));
                    this.addToMessageQueue("Plug date added for Dam 1.", FacesMessage.SEVERITY_INFO);
                    if(obsolete){
                        dto.setObsolete("yes");
                    }
                    else{
                        dto.setObsolete("no");
                    }
                    dam1PlugDates.add(dto);
                    
                    for(PlugDateHelperDTO pdhDTO : getMouseUses()){
                        pdhDTO.getDto().setMouseKey(dam1.getMouseKey().toString());
                        pdhDTO.getDto().setDone("0");
                        pdhDTO.getDto().setUseAge(calculateUseAge(dam1.getBirthDate(), pdhDTO.getProjectedDate()));
                        pdhDTO.getDto().setProjectedDate(pdhDTO.getProjectedDate());
                        pdhDTO.getDto().setPlugDateKey(dto.getPlugDateKey());
                        mouseUseDAO.addMouseUsage(pdhDTO.getDto());
                        pdhDTO.getDto().setProjectedDate(pdhDTO.getProjectedDate());
                        this.addToMessageQueue("Mouse use added for Dam 1.", FacesMessage.SEVERITY_INFO);
                    }
                    //initialize mouse
                    MouseDTO mouse = new MouseDTO();
                    mouse.setMouse_key(dam1.getMouseKey().toString());
                    mouse.setBirthDate(dam1.getBirthDate());
                    //add mouse to use schedule
                    for(UseScheduleTermDTO pDTO : (ArrayList<UseScheduleTermDTO>) getUseSchedulesModel().getTarget()){
                        pUtils.addMouseToPlugdateUseSchedule(pDTO, mouse, dto);
                        this.addToMessageQueue("Mouse " + dam1.getId() + " added to use schedule " + pDTO.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                    }
                    dam1Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam1.getMouseKey().toString());
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
                }
            }
            if(dam2Selected){
                PlugDateDTO dto = new PlugDateDTO();
                dto.setComment(comments);
                dto.setMating_key(matingEntity.getMatingKey().toString());
                if(obsolete){
                    dto.setObsolete("-1");
                }
                else{
                    dto.setObsolete("0");
                }
                dto.setPlugDate(plugDate);
                dto.setMouse_key(dam2.getMouseKey().toString());
                try{
                    dto.setPlugDateKey(plugDateDAO.insertPlugDate(dto));
                    this.addToMessageQueue("Plug date added for Dam 2.", FacesMessage.SEVERITY_INFO);
                    if(obsolete){
                        dto.setObsolete("yes");
                    }
                    else{
                        dto.setObsolete("no");
                    }
                    dam2PlugDates.add(dto);
                    
                    for(PlugDateHelperDTO pdhDTO : getMouseUses()){
                        pdhDTO.getDto().setMouseKey(dam2.getMouseKey().toString());
                        pdhDTO.getDto().setDone("0");
                        pdhDTO.getDto().setUseAge(calculateUseAge(dam2.getBirthDate(), pdhDTO.getProjectedDate()));
                        pdhDTO.getDto().setProjectedDate(pdhDTO.getProjectedDate());
                        pdhDTO.getDto().setPlugDateKey(dto.getPlugDateKey());
                        mouseUseDAO.addMouseUsage(pdhDTO.getDto());
                        pdhDTO.getDto().setProjectedDate(pdhDTO.getProjectedDate());
                        dam2Uses.add(new MouseUsageDTO(pdhDTO.getDto()));
                        this.addToMessageQueue("Mouse use added for Dam 2.", FacesMessage.SEVERITY_INFO);
                    }
                    
                    //initialize mouse
                    MouseDTO mouse = new MouseDTO();
                    mouse.setMouse_key(dam2.getMouseKey().toString());
                    mouse.setBirthDate(dam2.getBirthDate());
                    //add mouse to use schedules
                    for(UseScheduleTermDTO pDTO : (ArrayList<UseScheduleTermDTO>) getUseSchedulesModel().getTarget()){
                        pUtils.addMouseToPlugdateUseSchedule(pDTO, mouse, dto);
                        this.addToMessageQueue("Mouse " + dam2.getId() + " added to use schedule " + pDTO.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                    }
                    dam2Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam2.getMouseKey().toString());
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
                }
            }
        }
    }
    
    public void clearPlugDate(){
        matingID = "";
        matingEntity = null;
        dam1 = null;
        dam2 = null;
        sire = null;
        comments = "";
        dam1Selected = false;
        dam2Selected = false;
        obsolete = false;
        plugDate = null;
        getMouseUses().clear();
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
    public void addMouseUse(){
        boolean flag = false;
        if(plugDate == null){
            flag = true;
            this.addToMessageQueue("Plug date is required to add a mouse use.", FacesMessage.SEVERITY_ERROR);
        }
        if(mouseUse == null || mouseUse.getMouseUse() == null){
            flag = true;
            this.addToMessageQueue("Mouse use is required to add a mouse use.", FacesMessage.SEVERITY_ERROR);
        }
        if(DPC == null || DPC.equals("")){
            flag = true;
            this.addToMessageQueue("Days post conception is required to add a mouse use.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //create and initialize DTO
            PlugDateHelperDTO dto = new PlugDateHelperDTO();
            dto.getDto().setUse(mouseUse.getMouseUse());
            dto.setDPC(DPC);
            int intDPC = Integer.parseInt(DPC);
            //calculate projected date
            Calendar cal = Calendar.getInstance();
            cal.setTime(plugDate);
            cal.add(Calendar.DAY_OF_MONTH, intDPC);
            dto.setProjectedDate(cal.getTime());
            dto.getDto().setProjectedDate(cal.getTime());

            //add to mouseUses
            getMouseUses().add(dto);
        }
    }
    
    private String calculateUseAge(Date birthdate, Date projectedDate){
        Calendar BDCal = Calendar.getInstance();
        BDCal.setTime(birthdate);
        Calendar PDCal = Calendar.getInstance();
        PDCal.setTime(projectedDate);
        int age = 0;
        if(BDCal.before(PDCal)){
            while(BDCal.before(PDCal)){
                BDCal.add(Calendar.DAY_OF_MONTH, 1);
                age++;
            }
        }
        else{
            this.addToMessageQueue("How is Projected Date before birthdate...", FacesMessage.SEVERITY_ERROR);
        }
        return new Integer(age).toString();
    }
    
    public void deleteMouseUsageListener(){
        editUsageKey = this.getKey("paramUsageKey");
        editingUsageRowKey = this.getKey("paramRowIndex");
        System.out.println(editUsageKey);
        System.out.println(editingUsageRowKey);
        getMouseUses().remove(editingUsageRowKey.intValue());
    }
    
    public void clearMouseUses(){
        getMouseUses().clear();
    }
    
    public void cancelMouseUsageEditListener(){
        editingUsageRowKey = null;
    }
    
    public void saveMouseUsageEditListener(){
        editingUsageRowKey = null;
    }
    
    public void editPlugDateListener(){
        if(!editing){
            editPlugDateKey = this.getKey("paramUsageKey");
            editingPlugDateRowKey = this.getKey("paramRowIndex");
            dam1Selected = true;
            dam2Selected = false;

            PlugDateDTO dto = new PlugDateDTO(dam1PlugDates.get(editingPlugDateRowKey));
            if(dto.getObsolete().equals("no")){
                obsolete = false;
            }
            else{
                obsolete = true;
            }
            comments = dto.getComment();
            plugDate = dto.getPlugDate();
            editing = true;
            
            //initialize the use schedules, find all the use schedules for that plug date
            ArrayList<UseScheduleTermDTO> mouseUseSchedules = pUtils.getpDAO().getPDMouseUseScheduleTerms(dto.getPlugDateKey(), (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst"));
            useSchedulesModel.setTarget(mouseUseSchedules);
            //remove the mouse use schedules from those they can choose from.
            //get all the use schedules
            ArrayList<UseScheduleTermDTO> useSchedules = pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst"));
            for(UseScheduleTermDTO pDTO : mouseUseSchedules){
                useSchedules.remove(pDTO);
            }
            useSchedulesModel.setSource(useSchedules);
        }
        else{
            this.addToMessageQueue("You must either save or cancel your current edit to edit another plug date", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "You must either save or cancel your current edit to edit another plug date"));
        }
    }
    
    public void deletePlugDateListener(){
        System.out.println(deleteKey);
        //code to delete from DB goes here
        if(!mouseUseDAO.plugDateReferenced(dam1PlugDates.get(deleteKey.intValue()).getPlugDateKey())){
            try{
                plugDateDAO.deletePlugDate(dam1PlugDates.get(deleteKey.intValue()).getPlugDateKey());
                dam1PlugDates.remove(deleteKey.intValue());
                this.addToMessageQueue("Plug date removed for Dam 1.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
            }
        }
        else{
            this.addToMessageQueue("This plug date cannot be deleted because it is referenced by one or more uses. First remove the uses for this plugdate then it can be deleted.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "This plug date cannot be deleted because it is referenced by one or more uses. First remove the uses for this plugdate then it can be deleted."));
        }
    }
    
    public void cancelPlugDateEditListener(){
        plugDate = null;
        dam1Selected = false;
        dam2Selected = false;
        obsolete = false;
        comments = "";
        editingPlugDateRowKey = null;
        editing = false;        
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
   public void deletePlugDateSetupListener(){
       deleteKey = this.getKey("paramRowIndex");
   }
    
    public void savePlugDateEditListener(){
        
        //save stuff here
        PlugDateDTO dto = new PlugDateDTO();
        dto.setPlugDate(plugDate);
        dto.setMouse_key(dam1.getMouseKey().toString());
        dto.setMating_key(matingEntity.getMatingKey().toString());
        dto.setComment(comments);
        if(obsolete){
            dto.setObsolete("-1");
        }
        else{
            dto.setObsolete("0");
        }
        dto.setPlugDateKey(editPlugDateKey.toString());
        //save stuff here
        try{
            plugDateDAO.updatePlugDate(dto);
            this.addToMessageQueue("Plug date updated for Dam 1.", FacesMessage.SEVERITY_INFO);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
        if(obsolete){
            dto.setObsolete("yes");
        }
        else{
            dto.setObsolete("no");
        }
        dam1PlugDates.set(editingPlugDateRowKey.intValue(), dto);
        
        /*use schedule Portion*/
        //get current use schedules
        ArrayList<UseScheduleTermDTO> activeUseSchedules = pUtils.getpDAO().getPDMouseUseScheduleTerms(dto.getPlugDateKey(), (ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
        //get target use schedules
        ArrayList<UseScheduleTermDTO> targetUseSchedules = (ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget();
        //initialize mouse
        MouseDTO mouse = new MouseDTO();
        mouse.setMouse_key(dam1.getMouseKey().toString());
        mouse.setBirthDate(dam1.getBirthDate());
        
        /*first need to see if mouse was added to plug date based on use schedule.
          To see this check if there are any use schedules in the target list that 
          are NOT in the list of original active plug date use schedules.*/ //initialize mouse
        for(UseScheduleTermDTO targetUseSchedule : targetUseSchedules){
            //if target use schedule isn't in current active use schedules, add mouse to it.
            if(!activeUseSchedules.contains(targetUseSchedule)){
                try{
                    pUtils.addMouseToPlugdateUseSchedule(targetUseSchedule, mouse, dto);     
                    this.addToMessageQueue("Mouse " + dam1.getId() + " added to use schedule " + targetUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        
        /*Next we need to check to see if the mouse was removed from any plug date use schedules.
          To do this check if there are any current use schedules that are not in the 
          target use schedules list.*/
        //before doing the remove check to make sure none of the uses have been started (in which case the use schedule cannot be deleted)
        for(UseScheduleTermDTO activeUseSchedule : activeUseSchedules){
            //if active use schedule isn't in target use schedules, try to remove mouse from it
            if(!targetUseSchedules.contains(activeUseSchedule)){
                try{
                    //make sure it hasn't been started
                    if(!pUtils.getpDAO().pdUseScheduleStarted(activeUseSchedule, dto)){
                        pUtils.getpDAO().deletePDUseSchedule(dto, activeUseSchedule);
                        this.addToMessageQueue("Mouse " + dam1.getId() + " removed from use schedule " + activeUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                    }
                    else{
                        this.addToMessageQueue("Use Schedule " + activeUseSchedule.getUseScheduleTermName() + " cannot be deleted because it has already been started for mouse " + dam1.getId(), FacesMessage.SEVERITY_ERROR);
                    }
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        
        dam1Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam1.getMouseKey().toString());
                    
        //reset view
        editingPlugDateRowKey = null;
        dam1Selected = false;
        dam2Selected = false;
        obsolete = false;
        plugDate = null;
        comments = "";
        editingPlugDateRowKey = null;
        editing = false;
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
    public void editDam2PlugDateListener(){
        editDam2PlugDateKey = this.getKey("paramUsageKey");
        editingDam2PlugDateRowKey = this.getKey("paramRowIndex");
        dam1Selected = false;
        dam2Selected = true;
        
        PlugDateDTO dto = new PlugDateDTO(dam2PlugDates.get(editingDam2PlugDateRowKey));
        if(dto.getObsolete().equals("no")){
            obsolete = false;
        }
        else{
            obsolete = true;
        }
        comments = dto.getComment();
        plugDate = dto.getPlugDate();
        editing = true;
        //initialize the use schedules, find all the use schedules for that plug date
        ArrayList<UseScheduleTermDTO> mouseUseSchedules = pUtils.getpDAO().getPDMouseUseScheduleTerms(dto.getPlugDateKey(), (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst"));
        useSchedulesModel.setTarget(mouseUseSchedules);
        //remove the mouse use schedules from those they can choose from.
        //get all the use schedules
        ArrayList<UseScheduleTermDTO> useSchedules = pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst"));
        for(UseScheduleTermDTO pDTO : mouseUseSchedules){
            useSchedules.remove(pDTO);
        }
        useSchedulesModel.setSource(useSchedules);
    }
    
    public void deleteDam2PlugDateListener(){
        System.out.println(deleteKey);
        //code to delete from DB goes here
        if(!mouseUseDAO.plugDateReferenced(dam2PlugDates.get(deleteKey.intValue()).getPlugDateKey())){
            try{
                plugDateDAO.deletePlugDate(dam2PlugDates.get(deleteKey.intValue()).getPlugDateKey());
                dam2PlugDates.remove(deleteKey.intValue());
                this.addToMessageQueue("Plug date deleted for Dam 2.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
            }
        }
        else{
            this.addToMessageQueue("This plug date cannot be deleted because it is referenced by one or more uses. First remove the uses for this plugdate then it can be deleted.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "This plug date cannot be deleted because it is referenced by one or more uses. First remove the uses for this plugdate then it can be deleted."));
        }
    }
    
    public void cancelDam2PlugDateEditListener(){
        plugDate = null;
        dam1Selected = false;
        dam2Selected = false;
        obsolete = false;
        comments = "";
        editingDam2PlugDateRowKey = null;
        editing = false;
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
    public void saveDam2PlugDateEditListener(){
        PlugDateDTO dto = new PlugDateDTO();
        dto.setPlugDate(plugDate);
        dto.setMouse_key(dam2.getMouseKey().toString());
        dto.setMating_key(matingEntity.getMatingKey().toString());
        dto.setComment(comments);
        if(obsolete){
            dto.setObsolete("-1");
        }
        else{
            dto.setObsolete("0");
        }
        dto.setPlugDateKey(editDam2PlugDateKey.toString());
        //save stuff here
        try{
            plugDateDAO.updatePlugDate(dto);
            this.addToMessageQueue("Plug date edited for Dam 2.", FacesMessage.SEVERITY_INFO);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
        if(obsolete){
            dto.setObsolete("yes");
        }
        else{
            dto.setObsolete("no");
        }
        dam2PlugDates.set(editingDam2PlugDateRowKey.intValue(), dto);
        
        /*use schedule Portion*/
        //get current use schedules
        ArrayList<UseScheduleTermDTO> activeUseSchedules = pUtils.getpDAO().getPDMouseUseScheduleTerms(dto.getPlugDateKey(), (ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
        //get target use schedules
        ArrayList<UseScheduleTermDTO> targetUseSchedules = (ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget();
        //initialize mouse
        MouseDTO mouse = new MouseDTO();
        mouse.setMouse_key(dam2.getMouseKey().toString());
        mouse.setBirthDate(dam2.getBirthDate());
        
        /*first need to see if mouse was added to use schedule date based on plug date.
          To see this check if there are any use schedules in the target list that 
          are NOT in the list of original active plug date use schedules.*/ //initialize mouse
        for(UseScheduleTermDTO targetUseSchedule : targetUseSchedules){
            //if target use schedule isn't in current active use schedules, add mouse to it.
            if(!activeUseSchedules.contains(targetUseSchedule)){
                try{
                    pUtils.addMouseToPlugdateUseSchedule(targetUseSchedule, mouse, dto);            
                    this.addToMessageQueue("Mouse " + dam2.getId() + " added to use schedule " + targetUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        
        /*Next we need to check to see if the mouse was removed from any plug date use schedules.
          To do this check if there are any current use schedules that are not in the 
          target use schedules list.*/
        //before doing the remove check to make sure none of the uses have been started (in which case the use schedule cannot be deleted)
        for(UseScheduleTermDTO activeUseSchedule : activeUseSchedules){
            //if active use schedule isn't in target use schedules, try to remove mouse from it
            if(!targetUseSchedules.contains(activeUseSchedule)){
                try{
                    //make sure it hasn't been started
                    if(!pUtils.getpDAO().pdUseScheduleStarted(activeUseSchedule, dto)){
                        pUtils.getpDAO().deletePDUseSchedule(dto, activeUseSchedule);
                        this.addToMessageQueue("Mouse " + dam2.getId() + " removed from use schedule " + activeUseSchedule.getUseScheduleTermName(), FacesMessage.SEVERITY_INFO);
                    }
                    else{
                        this.addToMessageQueue("Use Schedule " + activeUseSchedule.getUseScheduleTermName() + " cannot be deleted because it has already been started for mouse " + dam1.getId(), FacesMessage.SEVERITY_ERROR);
                    }
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        
        dam2Uses = mouseUseDAO.getMouseUsagesByMouseKey(dam2.getMouseKey().toString());
        
        
        //reset view
        editDam2PlugDateKey = null;
        editingDam2PlugDateRowKey = null;
        dam1Selected = false;
        dam2Selected = false;
        obsolete = false;
        comments = "";
        editingDam2PlugDateRowKey = null;
        editing = false;
        editing = false;
        useSchedulesModel.setSource(pUtils.getpDAO().getActivePlugDateUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
    }
    
    public void plugDateValueChangeListener(){
        for(PlugDateHelperDTO dto : getMouseUses()){
            int intDPC = Integer.parseInt(dto.getDPC());
            //calculate projected date
            Calendar cal = Calendar.getInstance();
            cal.setTime(plugDate);
            cal.add(Calendar.DAY_OF_MONTH, intDPC);
            dto.setProjectedDate(cal.getTime());
            dto.getDto().setProjectedDate(cal.getTime());
        }
    }
    
    public String navigatePlugdate(){
        return "plugDate";
    }

    /**
     * @return the matingID
     */
    public String getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(String matingID) {
        this.matingID = matingID;
    }

    /**
     * @return the matingEntity
     */
    public MatingEntity getMatingEntity() {
        return matingEntity;
    }

    /**
     * @param matingEntity the matingEntity to set
     */
    public void setMatingEntity(MatingEntity matingEntity) {
        this.matingEntity = matingEntity;
    }

    /**
     * @return the dam1
     */
    public MouseEntity getDam1() {
        return dam1;
    }

    /**
     * @param dam1 the dam1 to set
     */
    public void setDam1(MouseEntity dam1) {
        this.dam1 = dam1;
    }

    /**
     * @return the dam2
     */
    public MouseEntity getDam2() {
        return dam2;
    }

    /**
     * @param dam2 the dam2 to set
     */
    public void setDam2(MouseEntity dam2) {
        this.dam2 = dam2;
    }

    /**
     * @return the sire
     */
    public MouseEntity getSire() {
        return sire;
    }

    /**
     * @param sire the sire to set
     */
    public void setSire(MouseEntity sire) {
        this.sire = sire;
    }

    /**
     * @return the dam1Selected
     */
    public boolean isDam1Selected() {
        return dam1Selected;
    }

    /**
     * @param dam1Selected the dam1Selected to set
     */
    public void setDam1Selected(boolean dam1Selected) {
        this.dam1Selected = dam1Selected;
    }

    /**
     * @return the dam2Selected
     */
    public boolean isDam2Selected() {
        return dam2Selected;
    }

    /**
     * @param dam2Selected the dam2Selected to set
     */
    public void setDam2Selected(boolean dam2Selected) {
        this.dam2Selected = dam2Selected;
    }

    /**
     * @return the obsolete
     */
    public boolean isObsolete() {
        return obsolete;
    }

    /**
     * @param obsolete the obsolete to set
     */
    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    /**
     * @return the plugDate
     */
    public Date getPlugDate() {
        return plugDate;
    }

    /**
     * @param plugDate the plugDate to set
     */
    public void setPlugDate(Date plugDate) {
        this.plugDate = plugDate;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the dam1PlugDates
     */
    public ArrayList<PlugDateDTO> getDam1PlugDates() {
        return dam1PlugDates;
    }

    /**
     * @param dam1PlugDates the dam1PlugDates to set
     */
    public void setDam1PlugDates(ArrayList<PlugDateDTO> dam1PlugDates) {
        this.dam1PlugDates = dam1PlugDates;
    }

    /**
     * @return the dam2PlugDates
     */
    public ArrayList<PlugDateDTO> getDam2PlugDates() {
        return dam2PlugDates;
    }

    /**
     * @param dam2PlugDates the dam2PlugDates to set
     */
    public void setDam2PlugDates(ArrayList<PlugDateDTO> dam2PlugDates) {
        this.dam2PlugDates = dam2PlugDates;
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
     * @return the daysPostConception
     */
    public ArrayList<SelectItem> getDaysPostConception() {
        if(daysPostConception == null){
            daysPostConception = new ArrayList<SelectItem>();
            daysPostConception.add(new SelectItem("", ""));
            daysPostConception.add(new SelectItem("0", ".5"));
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
     * @return the mouseUse
     */
    public CvMouseUseEntity getMouseUse() {
        return mouseUse;
    }

    /**
     * @param mouseUse the mouseUse to set
     */
    public void setMouseUse(CvMouseUseEntity mouseUse) {
        this.mouseUse = mouseUse;
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
     * @return the editingUsageRowKey
     */
    public Integer getEditingUsageRowKey() {
        return editingUsageRowKey;
    }

    /**
     * @param editingUsageRowKey the editingUsageRowKey to set
     */
    public void setEditingUsageRowKey(Integer editingUsageRowKey) {
        this.editingUsageRowKey = editingUsageRowKey;
    }

    /**
     * @return the editPlugDateKey
     */
    public Integer getEditPlugDateKey() {
        return editPlugDateKey;
    }

    /**
     * @param editPlugDateKey the editPlugDateKey to set
     */
    public void setEditPlugDateKey(Integer editPlugDateKey) {
        this.editPlugDateKey = editPlugDateKey;
    }

    /**
     * @return the editingPlugDateRowKey
     */
    public Integer getEditingPlugDateRowKey() {
        return editingPlugDateRowKey;
    }

    /**
     * @param editingPlugDateRowKey the editingPlugDateRowKey to set
     */
    public void setEditingPlugDateRowKey(Integer editingPlugDateRowKey) {
        this.editingPlugDateRowKey = editingPlugDateRowKey;
    }

    /**
     * @return the editDam2PlugDateKey
     */
    public Integer getEditDam2PlugDateKey() {
        return editDam2PlugDateKey;
    }

    /**
     * @param editDam2PlugDateKey the editDam2PlugDateKey to set
     */
    public void setEditDam2PlugDateKey(Integer editDam2PlugDateKey) {
        this.editDam2PlugDateKey = editDam2PlugDateKey;
    }

    /**
     * @return the editingDam2PlugDateRowKey
     */
    public Integer getEditingDam2PlugDateRowKey() {
        return editingDam2PlugDateRowKey;
    }

    /**
     * @param editingDam2PlugDateRowKey the editingDam2PlugDateRowKey to set
     */
    public void setEditingDam2PlugDateRowKey(Integer editingDam2PlugDateRowKey) {
        this.editingDam2PlugDateRowKey = editingDam2PlugDateRowKey;
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
     * @return the mouseUses
     */
    public ArrayList<PlugDateHelperDTO> getMouseUses() {
        return mouseUses;
    }

    /**
     * @param mouseUses the mouseUses to set
     */
    public void setMouseUses(ArrayList<PlugDateHelperDTO> mouseUses) {
        this.mouseUses = mouseUses;
    }

    /**
     * @return the dam1Uses
     */
    public ArrayList<MouseUsageDTO> getDam1Uses() {
        return dam1Uses;
    }

    /**
     * @param dam1Uses the dam1Uses to set
     */
    public void setDam1Uses(ArrayList<MouseUsageDTO> dam1Uses) {
        this.dam1Uses = dam1Uses;
    }

    /**
     * @return the dam2Uses
     */
    public ArrayList<MouseUsageDTO> getDam2Uses() {
        return dam2Uses;
    }

    /**
     * @param dam2Uses the dam2Uses to set
     */
    public void setDam2Uses(ArrayList<MouseUsageDTO> dam2Uses) {
        this.dam2Uses = dam2Uses;
    }

    /**
     * @return the dam1Genotype
     */
    public String getDam1Genotype() {
        return dam1Genotype;
    }

    /**
     * @param dam1Genotype the dam1Genotype to set
     */
    public void setDam1Genotype(String dam1Genotype) {
        this.dam1Genotype = dam1Genotype;
    }

    /**
     * @return the dam2Genotype
     */
    public String getDam2Genotype() {
        return dam2Genotype;
    }

    /**
     * @param dam2Genotype the dam2Genotype to set
     */
    public void setDam2Genotype(String dam2Genotype) {
        this.dam2Genotype = dam2Genotype;
    }

    /**
     * @return the sireGenotype
     */
    public String getSireGenotype() {
        return sireGenotype;
    }

    /**
     * @param sireGenotype the sireGenotype to set
     */
    public void setSireGenotype(String sireGenotype) {
        this.sireGenotype = sireGenotype;
    }

    /**
     * @return the useSchedulesModel
     */
    public DualListModel getUseSchedulesModel() {
        return useSchedulesModel;
    }

    /**
     * @param useSchedulesModel the useSchedulesModel to set
     */
    public void setUseSchedulesModel(DualListModel useSchedulesModel) {
        this.useSchedulesModel = useSchedulesModel;
    }
}
