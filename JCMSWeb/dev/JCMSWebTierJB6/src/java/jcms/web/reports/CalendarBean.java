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

package jcms.web.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import jcms.integrationtier.dao.CalendarDAO;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.CalendarWeanDTO;
import jcms.integrationtier.dto.CalendarDTO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.web.common.SelectItemWrapper;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import jcms.web.base.WTBaseBackingBean;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DualListModel;  
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.dao.MouseSearchDAO;
import org.primefaces.context.RequestContext;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.colonyManagement.MiceListCommon;
import jcms.web.colonyManagement.MouseFunctionsBean;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.service.SaveAppService;
import jcms.web.common.MouseScheduleUtilities;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author mkamato
 */
public class CalendarBean extends WTBaseBackingBean {
        
    private ScheduleModel eventModel;
    private DefaultScheduleEvent selectedEvent  = new DefaultScheduleEvent();
    private DefaultScheduleEvent newEvent       = new DefaultScheduleEvent();
    private DualListModel usesModel             = new DualListModel();
    private DualListModel ownersModel           = new DualListModel();
    private DualListModel strainsModel          = new DualListModel();
    private DualListModel ownersModel2          = new DualListModel();
    private DualListModel useSchedulesModel     = new DualListModel();
    private ArrayList<String> owners            = new ArrayList<String>();
    private ArrayList<String> owners2           = new ArrayList<String>();
    private ArrayList<String> uses              = new ArrayList<String>();
    private ArrayList<String> strains           = new ArrayList<String>();
    private ArrayList<SelectItem> useDropDown   = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> useDropDown2  = new ArrayList<SelectItem>();
    private boolean showCalendar                = false;
    private boolean collapse                    = false;
    private SelectItemWrapper wrapper           = new SelectItemWrapper();
    private String status                       = "any";
    private CalendarDAO dao                     = new CalendarDAO();
    private MouseScheduleUtilities pUtils       = new MouseScheduleUtilities();
    private Date actualDate                     = null;
    private Date projectedDate                  = null;
    private Date newActualDate                  = null;
    private Date newProjectedDate               = null;
    private String useCount                     = "";
    private String weanCount                    = "";
    private Date initialDate                    = new Date();
    private ArrayList<String> mice              = new ArrayList<String>();
    private String newMouseID                   = "";
    private MouseFunctionsBean mouseFunctions   = new MouseFunctionsBean();
    private boolean displayWeanDates            = false;
    private Date afterWeanDateFilter            = new Date();
    private Date beforeWeanDateFilter           = new Date();
    private boolean useSelected                 = false;
    private CalendarDTO selectedUseDTO          = null;
    private CalendarWeanDTO selectedWeanDTO     = null;
    private Date weanDate                       = new Date();
    private Date tagDate                        = new Date();
    private Date useBeforeDate                  = null;
    private Date useAfterDate                   = null;
    private Integer numMale;
    private Integer numFemale;
    private DefaultScheduleEvent dfe;
    
    //messages explaining projected wean dates
    private String litterWithNoMice = "This wean date was projected because it is associated with a litter that has no mouse records in the database.";
    private String litterWithNoWeanDate = "This wean date was projected because it is associated with a litter with no wean date.";
    private String activeMatingWithNoLitter = "This wean date was projected because it is associated with an active mating with no litter records.";
    //projected wean date types
    private String litterNoMice = "litterNoPups";
    private String litterNoWeanDate = "litterNoWeanDate";
    private String activeMatingNoLitter = "unweanedMating";
    private Integer selectedLitterKey;
    
    //Following items are for the Mouse search
    private List<SelectItem> allele1FilterItems         = new ArrayList<SelectItem>();    
    private List<SelectItem> allele2FilterItems         = new ArrayList<SelectItem>();  
    private boolean isMouseResultCountDisplayed         = false;
    private ListSupportDTO listSupportDTO               = new ListSupportDTO();
    private MouseSearchDTO mouseSearch                  = new MouseSearchDTO();
    private GenotypeSearchDTO genotypeSearchDTO         = new GenotypeSearchDTO();
    private MiceListCommon mouseInfo                    = new MiceListCommon();
    private ListDataModel mouseDataModel                = new ListDataModel();
    private List filteredMouseDataModel                 = new ArrayList();
    private MouseEntity selectedMouse                   = null;
    private boolean mouseSearchCollapse                 = false;
    
    public CalendarBean() {  
        selectedEvent.setData(new CalendarDTO());
        newEvent.setData(new CalendarDTO());
        eventModel = new DefaultScheduleModel();  
        useSchedulesModel.setSource(pUtils.getpDAO().getUseScheduleTerms((ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst")));
        useSchedulesModel.setTarget(new ArrayList<UseScheduleTermDTO>());
        for(OwnerEntity owner: (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst")){
            owners.add(owner.getOwner());
        }
        for(OwnerEntity owner: (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst")){
            owners2.add(owner.getOwner());
        }
        for(SelectItem si : wrapper.getCvMouseUseItems()){
            uses.add(si.getLabel());
            useDropDown.add(new SelectItem(si.getLabel(), si.getLabel()));
            useDropDown2.add(new SelectItem(si.getLabel(), si.getLabel()));
        }
        try{
            for(SelectItem si : wrapper.getActiveStrainsStringItems()){
                strains.add(si.getValue().toString());
            }
        }
        catch(Exception e){}
        ownersModel.setSource(owners);
        ownersModel2.setSource(owners2);
        usesModel.setSource(uses);
        strainsModel.setSource(strains);
        mice = dao.getMouseList(owners);
    }  
    
    public void populateCalendarInfo(){
        //check that some strains, statuses, owners and mouse uses are selected
        try{
            eventModel = new DefaultScheduleModel();
            if(!validateCalendar()){
                showCalendar = true;
                weanCount = "";
                CalendarDTO dtoOut = new CalendarDTO();
                //add strains, uses, etc.
                for(Object use : usesModel.getTarget()){
                    dtoOut.getUses().add(use.toString());
                }
                for(Object strain : strainsModel.getTarget()){
                    dtoOut.getStrains().add(strain.toString());
                }
                if(!ownersModel.getTarget().isEmpty()){
                    for(Object owner : ownersModel.getTarget()){
                        dtoOut.getOwners().add(owner.toString());
                    }
                }
                //if ownersModel is empty assume they want to search all owners user is a member of
                else{
                    for(Object owner : ownersModel.getSource()){
                        dtoOut.getOwners().add(owner.toString());
                    }
                }
                if(!useSchedulesModel.getTarget().isEmpty()){
                    dtoOut.setUseScheduleTerms((ArrayList<UseScheduleTermDTO>) useSchedulesModel.getTarget());
                }
                dtoOut.setAfterDate(useAfterDate);
                dtoOut.setBeforeDate(useBeforeDate);
                //holder date to represent today and calculate stuff.
                Date today = new Date();
                //value used in scheduler as start date
                initialDate = null;
                //value to determine what is closest to today
                Long closestValue = null;
                int wCount = 0;
                if(displayWeanDates){
                    List<String> selectedOwners = new ArrayList<String>();
                    if(!ownersModel.getTarget().isEmpty()){
                        for(Object owner : ownersModel.getTarget()){
                            selectedOwners.add(owner.toString());
                        }
                    }
                    else{
                        selectedOwners = getCurrentUserColonyManageWorkgroups();
                    }
                    ArrayList<CalendarWeanDTO> weanDates = dao.getWeanDates(afterWeanDateFilter, getBeforeWeanDateFilter(), selectedOwners);                
                    for(CalendarWeanDTO dto : weanDates ){
                        try{
                            if(dto.getLitter() == null){
                                dto.setWeanInfo(this.activeMatingWithNoLitter);
                                dto.setWeanType(this.activeMatingNoLitter);
                            }
                            else if(dto.getLitter().getWeanDate() == null){
                                dto.setWeanInfo(this.litterWithNoWeanDate);
                                dto.setWeanType(litterNoWeanDate);
                            }
                            else{
                                dto.setWeanInfo(this.litterWithNoMice);
                                dto.setWeanType(this.litterNoMice);
                            }
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dto.getWeanDate());
                            cal.set(Calendar.HOUR_OF_DAY, 12);
                            Date date = cal.getTime();
                            dto.setWeanDate(cal.getTime());
                            if(closestValue == null || Math.abs(date.getTime() - today.getTime()) < closestValue){
                                initialDate = date;
                                closestValue = Math.abs(initialDate.getTime() - today.getTime());
                            }
                            DefaultScheduleEvent dfe = new DefaultScheduleEvent("Mating ID: " + dto.getMating().getMatingID() + ", Container ID: " + dto.getContainer().getContainerID(), date, date, dto);
                            dfe.setAllDay(true);
                            dfe.setStyleClass("weanStyle");
                            eventModel.addEvent(dfe);
                            wCount++;
                        }
                        catch(Exception e){
                            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                        }
                    }
                    weanCount = new Integer(wCount).toString();
                }
                dtoOut.setStatus(status);
                //only get uses if it seems user wants them.
                if(dtoOut.getUses().isEmpty() 
                        && dtoOut.getStrains().isEmpty() 
                        && (useAfterDate == null && useBeforeDate == null)
                        && ownersModel.getTarget().isEmpty() 
                        && useSchedulesModel.getTarget().isEmpty()
                        && displayWeanDates){
                    useCount = "0";
                }
                else{
                    ArrayList<CalendarDTO> theUses = dao.getCalendarData(dtoOut);
                    int count = 0;          

                    for(CalendarDTO dto : theUses){
                        try{
                            //if there is an actual date, base event on that, otherwise use is based on projected date
                            if(dto.getUse().getActualDate() != null){
                                Date date = dto.getUse().getActualDate();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(date);
                                cal.set(Calendar.HOUR_OF_DAY, 12);
                                date = cal.getTime();
                                if(closestValue == null || Math.abs(date.getTime() - today.getTime()) < closestValue){
                                    initialDate = date;
                                    closestValue = Math.abs(initialDate.getTime() - today.getTime());
                                }
                                String label = "ID: " + dto.getMouseID() + ", Use: " + dto.getUse().getUse();
                                if(!dto.getUseScheduleTerm().getUseScheduleTermName().equals("")){
                                    label += ", Use Schedule: " + dto.getUseScheduleTerm().getUseScheduleTermName();
                                }

                                DefaultScheduleEvent dfe = new DefaultScheduleEvent(label, date, date, dto);
                                dfe.setAllDay(true);
                                dfe.setStyleClass("actualStyle");
                                eventModel.addEvent(dfe);
                                count++;
                            }
                            else{
                                Date date = dto.getUse().getProjectedDate();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(date);
                                cal.set(Calendar.HOUR_OF_DAY, 12);
                                date = cal.getTime();
                                if(closestValue == null || Math.abs(date.getTime() - today.getTime()) < closestValue){
                                    initialDate = date;
                                    closestValue = Math.abs(initialDate.getTime() - today.getTime());
                                }
                                String label = "ID: " + dto.getMouseID() + ", Use: " + dto.getUse().getUse();
                                if(!dto.getUseScheduleTerm().getUseScheduleTermName().equals("")){
                                    label += ", Use Schedule: " + dto.getUseScheduleTerm().getUseScheduleTermName();
                                }
                                DefaultScheduleEvent dfe = new DefaultScheduleEvent(label, date, date, dto);
                                dfe.setAllDay(true);
                                dfe.setStyleClass("projectedStyle");
                                eventModel.addEvent(dfe);
                                count++;
                            }
                        }
                        catch(Exception e){
                            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                        }
                    }
                    if(initialDate == null){
                        initialDate = new Date();
                    }
                    useCount = new Integer(count).toString();
                }
                collapse = true;
            }
            else{
                showCalendar = false;
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private boolean validateCalendar(){
        boolean flag = false;
        if(status.equals("")){
            flag = true;
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Calendar Error", "Please select one or more uses, owners, and strains as well as a status to view a use schedule");  
            addMessage(facesMessage);  
        }
        //if you're getting wean dates, the before filter (to) must be before the after filter (from) 
        if(displayWeanDates){
            if(this.beforeWeanDateFilter.before(afterWeanDateFilter)){
                flag = true;
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Calendar Error", "The 'from' wean date filter must be before the 'to' wean date filter");  
                addMessage(facesMessage);  
            }
        }
        return flag;
    }
            
    public void onEventSelect(SelectEvent selectEvent) {
        dfe = (DefaultScheduleEvent) selectEvent.getObject();
        selectedEvent = (DefaultScheduleEvent) selectEvent.getObject();
        useSelected = false;
        if(selectedEvent.getData() instanceof CalendarDTO){
            useSelected = true;
            
            selectedEvent.setData(new CalendarDTO((CalendarDTO) selectedEvent.getData()));
            selectedUseDTO = (CalendarDTO) selectedEvent.getData();
            CalendarDTO dto = (CalendarDTO) selectedEvent.getData();
            try{
                actualDate = dto.getUse().getActualDate();
            }
            catch(Exception e){
                actualDate = null;
            }
            try{
                projectedDate = dto.getUse().getProjectedDate();
            }
            catch(Exception e){
                projectedDate = null;
            }
        }
        else if(selectedEvent.getData() instanceof CalendarWeanDTO){
            selectedWeanDTO = (CalendarWeanDTO) ((ScheduleEvent) selectEvent.getObject()).getData();
        }
    }  
    
    public void onUseChange(){
        CalendarDTO data = (CalendarDTO) selectedEvent.getData();
        data.getUse();
        data.setCvMouseUse(new MouseUseDAO().getCvMouseUseByUse(data.getUse().getUse()));
        selectedEvent.setData(data);
    }
    
    public void onNewUseChange(){
        CalendarDTO data = (CalendarDTO) newEvent.getData();
        data.setCvMouseUse(new MouseUseDAO().getCvMouseUseByUse(data.getUse().getUse()));
        newEvent.setData(data);
    }
    
    public String navigateCalendar(){
        return "calendar";
    }
      
    public void saveEditAction(){
        try{
            CalendarDTO data = new CalendarDTO((CalendarDTO) selectedEvent.getData());
            boolean flag = false;
            //validate that save will be ok.
            if(data.getMouseID().equals("")){
                flag = true;
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", "Mouse ID is required.");  
                addMessage(facesMessage);  
            }
            if(data.getUse().getUse().equals("")){
                flag = true;
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", "Mouse use is required.");  
                addMessage(facesMessage);  
            }
            if(selectedEvent.getStyleClass().equals("projectedStyle") && projectedDate == null){
                flag = true;
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", "Projected date is required.");  
                addMessage(facesMessage);  
            }
            if(selectedEvent.getStyleClass().equals("actualStyle") && actualDate == null){
                flag = true;
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", "Actual date is required.");  
                addMessage(facesMessage);  
            }
            //if it's ok go ahead and save
            if(!flag){
                if(actualDate != null){
                    data.getUse().setActualDate(actualDate);
                }
                if(projectedDate != null){
                    data.getUse().setProjectedDate(projectedDate);
                    data.getUse().setUseAge(calculateUseAge(new SimpleDateFormat("MM/dd/yyyy").parse(data.getMouseBirthDate()) ,projectedDate));
                }
                
                //get original usage to check values on update...
                MouseUsageDTO originalUse = dao.getMouseUsage(data.getUse().getUsageKey());
                
                //update in database
                dao.editMouseUsage(data);
                
                //update in view (implies updating ScheduleEvent)
                /*
                 * If the user had a projected date and added an actual date, 
                 * update the selected event so that it has the actual event 
                 * style.
                 * 
                 * The only case where one needs to update the view is if the 
                 * user adds an actual date OR the projectd date is changed AND
                 * there is NO actual date associated with the event.
                 */
                if(originalUse.getActualDate() == null && data.getUse().getActualDate() != null){
                    selectedEvent.setStyleClass("actualStyle");
                }
                
                //update data...
                selectedEvent.setData(data);
                
                //update title...
                String title = "ID: " + data.getMouseID() + ", Use: " + data.getUse().getUse();
                if(!data.getUseScheduleTerm().getUseScheduleTermName().equals("")){
                    title += ", Use Schedule: " + data.getUseScheduleTerm().getUseScheduleTermName();
                }
                selectedEvent.setTitle(title);
                
                //update event date...
                Date theDate;
                if(selectedEvent.getStyleClass().equals("projectedStyle")){
                    theDate = projectedDate;
                }
                else{
                    theDate = actualDate;                    
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(theDate);
                cal.set(Calendar.HOUR_OF_DAY, 12);
                theDate = cal.getTime();
                selectedEvent.setStartDate(theDate);
                selectedEvent.setEndDate(theDate);
                selectedEvent.setAllDay(true);
                
                //update the event...
                eventModel.updateEvent(selectedEvent);
                
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Mouse use successfully edited.");  
                addMessage(facesMessage);  
                //if update was successful, hide eventDialog box
                RequestContext.getCurrentInstance().execute("PF('eventDialog').hide()");
            }
        }
        catch(Exception e){
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", e.getMessage());  
            addMessage(facesMessage);  
        }
    }
    
    public void onDateSelect(SelectEvent selectEvent) {  
        this.newProjectedDate = (Date) selectEvent.getObject();
        
        this.ownersModel2.setSource(owners2);
        this.ownersModel2.setTarget(new ArrayList());
        
        newEvent.setData(new CalendarDTO((CalendarDTO) newEvent.getData()));
    }
    
    public void selectNewActualDateAction(){
        if(newProjectedDate != null){
            newActualDate = newProjectedDate;
        }
        else{
            newActualDate = new Date();
        }
    }
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        
        Calendar cal = Calendar.getInstance();
        Date date;
        
        if(event.getScheduleEvent().getData() instanceof CalendarDTO){
            
            System.out.println(event.getScheduleEvent().getStartDate());
            //get event data
            CalendarDTO dto = (CalendarDTO) event.getScheduleEvent().getData();            

            try{
                //changed a projected date
                if(event.getScheduleEvent().getStyleClass().equals("projectedStyle")){
                    //get date value of projected date for moved mouseusage
                    date = dto.getUse().getProjectedDate();
                    //create calendar using projected date, then add the number of days (or subtract if moved backwards)
                    cal.setTime(date);
                    cal.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
                    //after below line date is now what the NEW projected time will be
                    date = cal.getTime();
                    //get the birthdate to calculate the use age...
                    Date mouseBD = new SimpleDateFormat("MM/dd/yyyy").parse(dto.getMouseBirthDate());  
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
                    //update in the DB
                    dao.updateProjectedDate(dto.getUse().getUsageKey(), dateStr, calculateUseAge(mouseBD, date));
                    dto.getUse().setProjectedDate(date);
                    dto.getUse().setUseAge(calculateUseAge(mouseBD, date));
                    cal.set(Calendar.HOUR, 9);
                }
                else{
                    date = dto.getUse().getActualDate(); 
                    cal.setTime(date);
                    cal.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
                    date = cal.getTime();
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
                    dao.updateActualDate(dto.getUse().getUsageKey(), dateStr);
                    dto.getUse().setActualDate(date);
                    cal.set(Calendar.HOUR, 9);
                }
                //set times for the event.
                DefaultScheduleEvent theEvent = (DefaultScheduleEvent) event.getScheduleEvent();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 5);
                theEvent.setStartDate(cal.getTime());
                theEvent.setEndDate(cal.getTime());
                eventModel.updateEvent(theEvent);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", cal.getTime().toString());
                addMessage(message);  
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else if(event.getScheduleEvent().getData() instanceof CalendarWeanDTO){
            //set times for the event.
            //get event data
            CalendarWeanDTO dto = (CalendarWeanDTO) event.getScheduleEvent().getData();    
            DefaultScheduleEvent theEvent = (DefaultScheduleEvent) event.getScheduleEvent();
            date = dto.getWeanDate();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 5);
            theEvent.setStartDate(cal.getTime());
            theEvent.setEndDate(cal.getTime());
            dto.setWeanDate(cal.getTime());
            eventModel.updateEvent(theEvent);
            this.addToMessageQueue("Wean dates cannot be moved.", FacesMessage.SEVERITY_WARN);
        }
    }  
    
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
    
    private String calculateUseAge(Date BD, Date projectedDate){
        Calendar BDCal = Calendar.getInstance();
        BDCal.setTime(BD);
        Calendar pdCal = Calendar.getInstance();
        pdCal.setTime(projectedDate);
        int days = 0;
        if(BDCal.before(pdCal)){
            while(BDCal.before(pdCal)){
                BDCal.add(Calendar.DAY_OF_MONTH, 1);
                days++;
            }
        }
        return new Integer(days).toString();
    }
    
    public List<String> complete(String query){
        List<String> results = new ArrayList<String>();  
        
        System.out.println("called");
        
        if(mice.contains(query)){
            results.add(query);
        }
        
        for(String mouse : this.mice){
            if(mouse.startsWith(query) && !mouse.equals(query)){
                results.add(mouse);
                if(results.size() > 10){
                    System.out.println(results);
                    return results;
                }
            }
        }
        System.out.println("final return" + results);
        return results;
    }
    
    public void mouseChangeListener(){
        try{
            MouseEntity me = this.getMouseFunctions().findMouse(newMouseID);
            CalendarDTO data = (CalendarDTO) newEvent.getData();
            if(me != null){                
                if(myContains(owners, me.getOwner())){
                    data.setGenotype(dao.getMouseGenotype(newMouseID));
                    data.setStrainName(me.getStrainKey().getStrainName().toString());
                    data.setMouseBirthDate(new SimpleDateFormat("MM/dd/yyyy").format(me.getBirthDate()));
                    data.setMouseID(new String(newMouseID));
                    data.getUse().setMouseKey(me.getMouseKey().toString());
                    System.out.println(data.getMouseID());
                }
                else{
                    data.setGenotype("");
                    data.setStrainName("");
                    data.setMouseBirthDate("");
                    data.setMouseID("");
                    data.getUse().setMouseKey("");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You do not have permissions to use mouse " + newMouseID + ".");  
                    addMessage(message); 
                }
            }
            else{
                data.setGenotype("");
                data.setStrainName("");
                data.setMouseBirthDate("");
                data.setMouseID("");
                data.getUse().setMouseKey("");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mouse " + newMouseID + " could not be found.");  
                addMessage(message);  
            }
        }
        catch(Exception e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Exception: " + e);  
            addMessage(message);  
        }
    }
    
    public void insertMouseUsage(){
        boolean flag = false;
        CalendarDTO data = new CalendarDTO((CalendarDTO) newEvent.getData());
        MouseUsageDTO use = new MouseUsageDTO(data.getUse());
        FacesMessage message = new FacesMessage();
        if(use.getMouseKey().equals("")){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mouse ID is required. ");  
            flag = true;
        }
        if(this.newActualDate == null && this.newProjectedDate == null){
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail(message.getDetail() + "Either Actual Date or Projected Date is required. ");
            message.setSummary("Error");
            flag = true;
        }
        if(use.getUse() == null || use.getUse().equals("")){
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail(message.getSummary() + "Use is required.");
            message.setSummary("Error");
            flag = true;
        }
        if(message.getSummary() != null){
            addMessage(message);
        }
        if(!flag){
            try{
                if(newActualDate != null){
                    use.setActualDate(newActualDate);
                }
                if(newProjectedDate != null){
                    use.setProjectedDate(newProjectedDate);
                    use.setUseAge(calculateUseAge(new SimpleDateFormat("MM/dd/yyyy").parse(data.getMouseBirthDate()) ,newProjectedDate));
                }

                //create in database
                String key = dao.insertMouseUsage(use);
                use.setUsageKey(key);
                data.setUse(use);

                //update in view (implies updating ScheduleEvent)
                if(newActualDate != null){
                    //do time nonsense for schedule
                    Date theDate = newActualDate;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(theDate);
                    cal.set(Calendar.HOUR_OF_DAY, 12);
                    theDate = cal.getTime();
                    DefaultScheduleEvent dfe = new DefaultScheduleEvent("ID: " + data.getMouseID() + ", Use: " + data.getUse().getUse(), theDate, theDate, data);
                    dfe.setAllDay(true);
                    dfe.setStyleClass("actualStyle");
                    eventModel.addEvent(dfe);
                }
                else {
                    //do time nonsense for schedule
                    Date theDate = newProjectedDate;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(theDate);
                    cal.set(Calendar.HOUR_OF_DAY, 12);
                    theDate = cal.getTime();
                    
                    //create dfe
                    DefaultScheduleEvent dfe = new DefaultScheduleEvent("ID: " + data.getMouseID() + ", Use: " + data.getUse().getUse(), theDate, theDate, data);
                    dfe.setAllDay(true);
                    dfe.setStyleClass("projectedStyle");
                    eventModel.addEvent(dfe);
                }
                
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Add Successful", "Mouse use successfully added.");  
                addMessage(facesMessage);  
                //if insert was successful, hide eventDialog box
                RequestContext.getCurrentInstance().execute("PF('addNewEventDialog').hide()");
            }
            catch(Exception e){
                FacesMessage theMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: " + e);  
                addMessage(theMessage);  
            }
        }
    }
    
    public void clearNewUsePanel(){
        newEvent.setData(new CalendarDTO());
        newMouseID = "";
        newActualDate = null;
    }
    
    public void projectedDateChangeListener(){
        try{
            CalendarDTO data = (CalendarDTO) selectedEvent.getData();
            data.getUse().setUseAge(calculateUseAge(new SimpleDateFormat("MM/dd/yyyy").parse(data.getMouseBirthDate()), projectedDate));
        }
        catch(Exception e){}
    }
    
    public void newProjectedDateChangeListener(){
        boolean flag = false;
        CalendarDTO data = (CalendarDTO) selectedEvent.getData();
        if(newMouseID != null && !newMouseID.equals("")){
            flag=true;
        }
        if(newProjectedDate == null){
            data.getUse().setUseAge("");
            flag = true;
        }
        if(!flag){
            try{            
                data.getUse().setUseAge(calculateUseAge(new SimpleDateFormat("MM/dd/yyyy").parse(data.getMouseBirthDate()), projectedDate));
            }
            catch(Exception e){}
        }
    }
    
    
    /*
     * The following methods are for the mouse search
     * functionality.
     */
    
    public void executeMouseSearch(){
        setIsMouseResultCountDisplayed(true);

        System.out.println("starting mouse search...");
        setMouseDataModel(new ListDataModel());
        try{
            mouseSearch.setLifeStatus(null);
            mouseSearch.setOwners(null);
            ArrayList<OwnerEntity> theOwners = (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst");
            if(!this.ownersModel2.getTarget().isEmpty()){
                for(String owner : (ArrayList<String>) ownersModel2.getTarget()){
                    for(OwnerEntity e : theOwners){
                        if(e.getOwner().equalsIgnoreCase(owner)){
                            if(mouseSearch.getOwners() == null){
                                mouseSearch.setOwners(new ArrayList());
                                mouseSearch.getOwners().add(e);
                            }
                            else{
                                mouseSearch.getOwners().add(e);
                            }
                        }
                    }
                }
            }
            else{
                mouseSearch.setOwners((ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst"));
            }
            ITBaseEntityTable mouseTable = new MouseSearchDAO().getSearchResults(getMouseSearch(), getGenotypeSearchDTO());
            List<MouseEntity> mice = new ArrayList<MouseEntity>();
            
            for (ITBaseEntityInterface entity: mouseTable.getList()) {
                mice.add((MouseEntity) entity);
            }  
            getMouseDataModel().setWrappedData(mice);
            mouseSearchCollapse = true;
        }
        catch(Exception e){
            this.addToMessageQueue("Something went terribly wrong: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Something went terribly wrong: " + e));
        }
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
     
    public void selectMouseAction(){
        if(selectedMouse != null){
            newMouseID = selectedMouse.getId();
            //if update was successful, hide eventDialog box
            mouseChangeListener();
            RequestContext.getCurrentInstance().execute("PF('findMouseDialog').hide()");
        }
    }
     
    private boolean myContains(ArrayList<String> list, String value){
        for(String val : list){
            if(val.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }
    

    public void cullLitterAction(){
        try{
            Integer litterKey = this.getKey("paramLitterKey");
            System.out.println(litterKey);

            dao.cullLitter(litterKey.toString());

            eventModel.deleteEvent(dfe);
            int wc = Integer.parseInt(weanCount) - 1;
            weanCount = new Integer(wc).toString();

            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Litter status updated", "Litter status changed to harvested.");  
            addMessage(facesMessage); 
        }
        catch(Exception e){
            addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }  
    
    public void retireMatingAction(){
        try{
            Integer matingKey = (Integer) this.getKey("paramMatingKey");    
            System.out.println(matingKey);
            dao.retireMating(matingKey.toString());
            //update view
            eventModel.deleteEvent(dfe);
            int wc = Integer.parseInt(weanCount) - 1;
            weanCount = new Integer(wc).toString();
            
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mating Retired", "Mating successfully retired.");  
            addMessage(facesMessage); 
        }
        catch(Exception e){
            addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void updateLitterDetails(){
        selectedLitterKey = getKey("paramLitterKey");
        System.out.println("Litter Key: " + selectedLitterKey);
        
        LitterEntity le = new LitterEntity();
        le.setLitterKey(getKey("paramLitterKey"));

        LitterEntity litterEntity = (LitterEntity) getRepositoryService().baseFind(le);
        
        numFemale = new Integer(litterEntity.getNumFemale());
        numMale = new Integer(litterEntity.getNumMale());
    }
    
    public void updateLitter(){
        System.out.println("Litter Key: " + getKey("paramLitterKey"));
        
        boolean flag = false;
        if(weanDate == null){
            addToMessageQueue("Wean Date is required, please provide a wean date.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(numMale == null){
            addToMessageQueue("Number male is required, please provide a number male.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(numFemale == null){
            addToMessageQueue("Number female is required, please provide a number female.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            //update Litter     
            try{
                LitterEntity le = new LitterEntity();
                le.setLitterKey(getKey("paramLitterKey"));

                LitterEntity litterEntity = (LitterEntity) getRepositoryService().baseFind(le);
                litterEntity.setTagDate(tagDate);
                litterEntity.setWeanDate(weanDate);
                litterEntity.setNumFemale(new Short(numFemale.toString()));
                litterEntity.setNumMale(new Short(numMale.toString()));

                new SaveAppService().saveLitter(litterEntity);
                this.addToMessageQueue("Litter " + litterEntity.getLitterID() + " successfully updated.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(DefaultScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    /**
     * @return the usesModel
     */
    public DualListModel getUsesModel() {
        return usesModel;
    }

    /**
     * @param usesModel the usesModel to set
     */
    public void setUsesModel(DualListModel usesModel) {
        this.usesModel = usesModel;
    }

    /**
     * @return the ownersModel
     */
    public DualListModel getOwnersModel() {
        return ownersModel;
    }

    /**
     * @param ownersModel the ownersModel to set
     */
    public void setOwnersModel(DualListModel ownersModel) {
        this.ownersModel = ownersModel;
    }

    /**
     * @return the strainsModel
     */
    public DualListModel getStrainsModel() {
        return strainsModel;
    }

    /**
     * @param strainsModel the strainsModel to set
     */
    public void setStrainsModel(DualListModel strainsModel) {
        this.strainsModel = strainsModel;
    }

    /**
     * @return the showCalendar
     */
    public boolean isShowCalendar() {
        return showCalendar;
    }

    /**
     * @param showCalendar the showCalendar to set
     */
    public void setShowCalendar(boolean showCalendar) {
        this.showCalendar = showCalendar;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the useDropDown
     */
    public ArrayList<SelectItem> getUseDropDown() {
        return useDropDown;
    }

    /**
     * @param useDropDown the useDropDown to set
     */
    public void setUseDropDown(ArrayList<SelectItem> useDropDown) {
        this.useDropDown = useDropDown;
    }

    /**
     * @return the selectedEvent
     */
    public DefaultScheduleEvent getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * @param selectedEvent the selectedEvent to set
     */
    public void setSelectedEvent(DefaultScheduleEvent selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    /**
     * @return the actualDate
     */
    public Date getActualDate() {
        return actualDate;
    }

    /**
     * @param actualDate the actualDate to set
     */
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    /**
     * @return the projectedDate
     */
    public Date getProjectedDate() {
        return projectedDate;
    }

    /**
     * @param projectedDate the projectedDate to set
     */
    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
    }

    /**
     * @return the useCount
     */
    public String getUseCount() {
        return useCount;
    }

    /**
     * @param useCount the useCount to set
     */
    public void setUseCount(String useCount) {
        this.useCount = useCount;
    }

    /**
     * @return the initialDate
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * @param initialDate the initialDate to set
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * @return the collapse
     */
    public boolean isCollapse() {
        return collapse;
    }

    /**
     * @param collapse the collapse to set
     */
    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    /**
     * @return the newEvent
     */
    public DefaultScheduleEvent getNewEvent() {
        return newEvent;
    }

    /**
     * @param newEvent the newEvent to set
     */
    public void setNewEvent(DefaultScheduleEvent newEvent) {
        this.newEvent = newEvent;
    }

    /**
     * @return the newActualDate
     */
    public Date getNewActualDate() {
        return newActualDate;
    }

    /**
     * @param newActualDate the newActualDate to set
     */
    public void setNewActualDate(Date newActualDate) {
        this.newActualDate = newActualDate;
    }

    /**
     * @return the newProjectedDate
     */
    public Date getNewProjectedDate() {
        return newProjectedDate;
    }

    /**
     * @param newProjectedDate the newProjectedDate to set
     */
    public void setNewProjectedDate(Date newProjectedDate) {
        this.newProjectedDate = newProjectedDate;
    }

    /**
     * @return the newMouseID
     */
    public String getNewMouseID() {
        return newMouseID;
    }

    /**
     * @param newMouseID the newMouseID to set
     */
    public void setNewMouseID(String newMouseID) {
        this.newMouseID = newMouseID;
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

    /**
     * @return the isMouseResultCountDisplayed
     */
    public boolean isIsMouseResultCountDisplayed() {
        return isMouseResultCountDisplayed;
    }

    /**
     * @param isMouseResultCountDisplayed the isMouseResultCountDisplayed to set
     */
    public void setIsMouseResultCountDisplayed(boolean isMouseResultCountDisplayed) {
        this.isMouseResultCountDisplayed = isMouseResultCountDisplayed;
    }

    /**
     * @return the listSupportDTO
     */
    public ListSupportDTO getListSupportDTO() {
        return listSupportDTO;
    }

    /**
     * @param listSupportDTO the listSupportDTO to set
     */
    public void setListSupportDTO(ListSupportDTO listSupportDTO) {
        this.listSupportDTO = listSupportDTO;
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
     * @return the genotypeSearchDTO
     */
    public GenotypeSearchDTO getGenotypeSearchDTO() {
        return genotypeSearchDTO;
    }

    /**
     * @param genotypeSearchDTO the genotypeSearchDTO to set
     */
    public void setGenotypeSearchDTO(GenotypeSearchDTO genotypeSearchDTO) {
        this.genotypeSearchDTO = genotypeSearchDTO;
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
     * @return the mouseFunctions
     */
    public MouseFunctionsBean getMouseFunctions() {
        return mouseFunctions;
    }

    /**
     * @param mouseFunctions the mouseFunctions to set
     */
    public void setMouseFunctions(MouseFunctionsBean mouseFunctions) {
        this.mouseFunctions = mouseFunctions;
    }

    /**
     * @return the selectedMouse
     */
    public MouseEntity getSelectedMouse() {
        return selectedMouse;
    }

    /**
     * @param selectedMouse the selectedMouse to set
     */
    public void setSelectedMouse(MouseEntity selectedMouse) {
        this.selectedMouse = selectedMouse;
    }

    /**
     * @return the mouseSearchCollapse
     */
    public boolean isMouseSearchCollapse() {
        return mouseSearchCollapse;
    }

    /**
     * @param mouseSearchCollapse the mouseSearchCollapse to set
     */
    public void setMouseSearchCollapse(boolean mouseSearchCollapse) {
        this.mouseSearchCollapse = mouseSearchCollapse;
    }

    /**
     * @return the filteredMouseDataModel
     */
    public List getFilteredMouseDataModel() {
        return filteredMouseDataModel;
    }

    /**
     * @param filteredMouseDataModel the filteredMouseDataModel to set
     */
    public void setFilteredMouseDataModel(List filteredMouseDataModel) {
        this.filteredMouseDataModel = filteredMouseDataModel;
    }

    /**
     * @return the ownersModel2
     */
    public DualListModel getOwnersModel2() {
        return ownersModel2;
    }

    /**
     * @param ownersModel2 the ownersModel2 to set
     */
    public void setOwnersModel2(DualListModel ownersModel2) {
        this.ownersModel2 = ownersModel2;
    }

    /**
     * @return the useDropDown2
     */
    public ArrayList<SelectItem> getUseDropDown2() {
        return useDropDown2;
    }

    /**
     * @param useDropDown2 the useDropDown2 to set
     */
    public void setUseDropDown2(ArrayList<SelectItem> useDropDown2) {
        this.useDropDown2 = useDropDown2;
    }

    /**
     * @return the displayWeanDates
     */
    public boolean isDisplayWeanDates() {
        return displayWeanDates;
    }

    /**
     * @param displayWeanDates the displayWeanDates to set
     */
    public void setDisplayWeanDates(boolean displayWeanDates) {
        this.displayWeanDates = displayWeanDates;
    }
    
    /**
     * @return the afterWeanDateFilter
     */
    public Date getAfterWeanDateFilter() {
        return afterWeanDateFilter;
    }

    /**
     * @param afterWeanDateFilter the afterWeanDateFilter to set
     */
    public void setAfterWeanDateFilter(Date afterWeanDateFilter) {
        this.afterWeanDateFilter = afterWeanDateFilter;
    }

    /**
     * @return the weanCount
     */
    public String getWeanCount() {
        return weanCount;
    }

    /**
     * @param weanCount the weanCount to set
     */
    public void setWeanCount(String weanCount) {
        this.weanCount = weanCount;
    }

    /**
     * @return the useSelected
     */
    public boolean isUseSelected() {
        return useSelected;
    }

    /**
     * @param useSelected the useSelected to set
     */
    public void setUseSelected(boolean useSelected) {
        this.useSelected = useSelected;
    }

    /**
     * @return the selectedUseDTO
     */
    public CalendarDTO getSelectedUseDTO() {
        return selectedUseDTO;
    }

    /**
     * @param selectedUseDTO the selectedUseDTO to set
     */
    public void setSelectedUseDTO(CalendarDTO selectedUseDTO) {
        this.selectedUseDTO = selectedUseDTO;
    }

    /**
     * @return the selectedWeanDTO
     */
    public CalendarWeanDTO getSelectedWeanDTO() {
        return selectedWeanDTO;
    }

    /**
     * @param selectedWeanDTO the selectedWeanDTO to set
     */
    public void setSelectedWeanDTO(CalendarWeanDTO selectedWeanDTO) {
        this.selectedWeanDTO = selectedWeanDTO;
    }

    /**
     * @return the beforeWeanDateFilter
     */
    public Date getBeforeWeanDateFilter() {
        return beforeWeanDateFilter;
    }

    /**
     * @param beforeWeanDateFilter the beforeWeanDateFilter to set
     */
    public void setBeforeWeanDateFilter(Date beforeWeanDateFilter) {
        this.beforeWeanDateFilter = beforeWeanDateFilter;
    }

    /**
     * @return the weanDate
     */
    public Date getWeanDate() {
        return weanDate;
    }

    /**
     * @param weanDate the weanDate to set
     */
    public void setWeanDate(Date weanDate) {
        this.weanDate = weanDate;
    }

    /**
     * @return the tagDate
     */
    public Date getTagDate() {
        return tagDate;
    }

    /**
     * @param tagDate the tagDate to set
     */
    public void setTagDate(Date tagDate) {
        this.tagDate = tagDate;
    }

    /**
     * @return the selectedLitterKey
     */
    public Integer getSelectedLitterKey() {
        return selectedLitterKey;
    }

    /**
     * @param selectedLitterKey the selectedLitterKey to set
     */
    public void setSelectedLitterKey(Integer selectedLitterKey) {
        this.selectedLitterKey = selectedLitterKey;
    }

    /**
     * @return the numMale
     */
    public Integer getNumMale() {
        return numMale;
    }

    /**
     * @param numMale the numMale to set
     */
    public void setNumMale(Integer numMale) {
        this.numMale = numMale;
    }

    /**
     * @return the numFemale
     */
    public Integer getNumFemale() {
        return numFemale;
    }

    /**
     * @param numFemale the numFemale to set
     */
    public void setNumFemale(Integer numFemale) {
        this.numFemale = numFemale;
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

    /**
     * @return the useStartDate
     */
    public Date getUseAfterDate() {
        return useAfterDate;
    }

    /**
     * @param useStartDate the useStartDate to set
     */
    public void setUseAfterDate(Date useAfterDate) {
        this.useAfterDate = useAfterDate;
    }

    /**
     * @return the useEndDate
     */
    public Date getUseBeforeDate() {
        return useBeforeDate;
    }

    /**
     * @param useEndDate the useEndDate to set
     */
    public void setUseBeforeDate(Date useBeforeDate) {
        this.useBeforeDate = useBeforeDate;
    }
}
