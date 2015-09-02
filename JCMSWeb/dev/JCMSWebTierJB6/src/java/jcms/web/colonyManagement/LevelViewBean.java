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
import java.util.*;
import jcms.integrationtier.dao.LevelDAO;
import jcms.integrationtier.dto.AdminRoomDTO;
import jcms.integrationtier.dto.LevelDTO;
import jcms.web.base.WTBaseBackingBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import jcms.integrationtier.dao.ContainerDAO;
import jcms.integrationtier.dao.CVAdministrationDAO;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import jcms.integrationtier.dto.ContainerDTO;
import jcms.web.dto.LevelContainerDTO;
import jcms.web.dto.VivariaPupDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import jcms.web.common.SelectItemWrapper;
import jcms.integrationtier.colonyManagement.StrainEntity;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.*;
import jcms.integrationtier.dao.MouseDAO;
import jcms.integrationtier.dao.MatingDAO;
import jcms.integrationtier.dto.*;
import jcms.integrationtier.cv.CvContainerStatusEntity;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.cv.CvBirthEventStatusEntity;
import jcms.web.cagecards.CageCardBean;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.service.SaveAppService;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.cv.*;
import jcms.integrationtier.dao.*;
import org.primefaces.context.RequestContext;
/**
 *
 * @author mkamato
 */
public class LevelViewBean extends WTBaseBackingBean implements Serializable{
    
    private LevelDAO levelDAO = new LevelDAO();
    private CVAdministrationDAO adminDAO = new CVAdministrationDAO();
    private ContainerDAO containerDAO = new ContainerDAO();
    private TreeNode root = new DefaultTreeNode();
    private ArrayList<OwnerEntity> guestOwnerLst = (ArrayList<OwnerEntity>)getSessionParameter("guestOwnerEntityLst"); 
    private ArrayList<OwnerEntity> colonyManageOwnerLst = (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"); 
    private LinkedList<LevelContainerDTO> containers = new LinkedList<LevelContainerDTO>();
    private LevelDTO selectedLevel = null;
    private String columns = "5";
    private ArrayList<String> pickListContainers = null;
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private StrainEntity cageFilterStrain = null;
    private String function = "addPens";
    private String instructions = "";
    private String strainName = "";
    private ListDataModel mouseDataModel = new ListDataModel();
    private ArrayList<MouseDTO> mouseList = null;
    private Integer gridPage = 1;
    private LifeStatusEntity lifeStatus = null;
    private CvContainerStatusEntity containerStatus = null;
    private ArrayList<LevelContainerDTO> statusChangeContainers = new ArrayList<LevelContainerDTO>();
    private String removedCullCage = "";
    private String addCageLabel = "";
    private String selectedContainerID = "";
    private String selectedContainerKey = "";
    private String matingCardKey;
    private String detailCardKey;
    private boolean containerNameView = false;
    private ListSupportDTO listSupportDTO = new ListSupportDTO(guestOwnerLst);
    private MouseFunctionsBean mouseFunctions = new MouseFunctionsBean();
    SystemDao systemDAO = new SystemDao();
    private boolean expanded = true;
    
    //fields for add mating
    private StrainEntity litterStrain = null;
    private CvGenerationEntity litterGeneration = null;
    private CvDietEntity matingDiet = null;
    private Date matingDate = new Date();
    private String weanTime = "standard";
    private boolean needsGenotyping = false;
    private OwnerEntity matingOwner = null;
    private String weanNote = "";
    private String matingComment = "";
    private String dam1ID = "";
    private String dam2ID = "";
    private String sireID = "";
    private MouseDTO dam1 = null;
    private MouseDTO dam2 = null;
    private MouseDTO sire = null;
    private StrainEntity lastDam1Strain = null;
    private StrainEntity lastDam2Strain = null;
    private StrainEntity lastSireStrain = null;
    private StrainEntity lastLitterStrain = null;
    
    //fields for adding a cage
    private String cageID = "";
    private String cageName = "";
    private String cageComment = "";
    private AdminRoomDTO currentRoom = null;
    private Date actionDate = new Date();
    private boolean addingCage = false;
    
    //fields for add Litter (with pups)
    private ArrayList<VivariaPupDTO> pups = new ArrayList<VivariaPupDTO>();
    private String totalBorn = "";
    private Date dateBorn = new Date();
    private boolean calculateDates = true;
    private Date weanDate = new Date();
    private Date tagDate = new Date();
    private String litterStatus = null;
    private String litterComments = "";
    private String baseMouseID = "";
    private CvMouseProtocolEntity protocol = null;
    private CvMouseOriginEntity origin = null;
    private MatingEntity litterMating = null;
    private String numMale = "0";
    private String numFemale = "0";
    private int stdWeanTime = 0;   //used to calculate wean date from BD
    private String nextLitterID = "";
    private String numberBornDead = "";
    private String numberCulledAtWean = "";
    private String numberMissingAtWean = "";
            
    public LevelViewBean(){
        ArrayList<AdminRoomDTO> rooms = adminDAO.getRooms();
        for(AdminRoomDTO room : rooms){
            DefaultTreeNode roomNode = new DefaultTreeNode("room", room, getRoot());
            roomNode.setExpanded(true);
            for(LevelDTO level : levelDAO.getBaseLevelsByRoomKey(room.getRoomKey())){
                if(level.getIsActive().equalsIgnoreCase("true")){
                    DefaultTreeNode levelNode = new DefaultTreeNode("level", level, roomNode);
                    buildSubTree(levelNode);
                }
            }
        }
        
        DbsetupEntity entity = (DbsetupEntity) getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_STANDARD_WEAN_TIME");
        if (entity != null) {
            // assign default stdWeanTime
            if ((entity.getMTSValue() != null && !entity.getMTSValue().equals(""))) {
                stdWeanTime = Integer.parseInt(entity.getMTSValue());
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(weanDate);
        
        cal.add(Calendar.DAY_OF_MONTH, this.stdWeanTime); // add 18 days  
        Date newDate = cal.getTime();

        tagDate = newDate;
        weanDate = newDate;
        
        instructions = "Select a strain from the dropdown below to get a list of cages and drag the cage ID to where it is on the vivaria layout.";
    }
        
    private void buildSubTree(TreeNode parent){
        LevelDTO dto = (LevelDTO) parent.getData();
        for(LevelDTO level : levelDAO.getReferencedLevelsByLevelKey(dto.getLevel_key())){
            if(level.getIsActive().equalsIgnoreCase("true")){
                DefaultTreeNode child = new DefaultTreeNode("level", level, parent);
                buildSubTree(child);
            }
        }
    }
    
    public String navToLevelView(){
        root = new DefaultTreeNode();
        ArrayList<AdminRoomDTO> rooms = adminDAO.getRooms();
        for(AdminRoomDTO room : rooms){
            DefaultTreeNode roomNode = new DefaultTreeNode("room", room, getRoot());
            roomNode.setExpanded(true);
            for(LevelDTO level : levelDAO.getBaseLevelsByRoomKey(room.getRoomKey())){
                if(level.getIsActive().equalsIgnoreCase("true")){
                    DefaultTreeNode levelNode = new DefaultTreeNode("level", level, roomNode);
                    buildSubTree(levelNode);
                }
            }
        }
        strainFilterChange();
        return "levelView";
    }
    
    public void functionChangeListener(){
        if(function.equals("addPens")){
            instructions = "Select a strain from the dropdown below to get a list of cages and drag the cage ID to where it is on the vivaria layout.";
            clearLitterInfo();
            clearMatingInfo();
        }
        else if(function.equals("exitAndRetire")){
            instructions = "Drag the cage you would like to retire to the status change box and all mice will have their life status changed to the value in the dropdown.";
            clearLitterInfo();
            clearMatingInfo();
        }
        else if(function.equals("addMating")){
            instructions = "Select a cage to create the mating in and drag the cage or mouse you would like to add to the mating cage and hit save.";
            clearLitterInfo();
            clearCageInfo();
        }
        else if(function.equals("addTrioMating")){
            instructions = "Select a cage to create the mating in and drag the cage or mouse you would like to add to the mating cage and hit save."; 
            clearLitterInfo();  
            clearCageInfo();
        }
        else if(function.equals("addLitter")){
            instructions = "Select a mating cage in which to add the litter, drag pups to their new cage(s).";
            clearMatingInfo();
        }
    }
    
    public void printCageCard(ActionEvent event){

        String type = (String) event.getComponent().getAttributes().get("paramCageType");
        String id = (String) event.getComponent().getAttributes().get("paramCageID");
        
        boolean flag = false;
        if(type.equals("mating") && getMatingCardKey().equals("")){
            flag = true;
            this.addToMessageQueue("Please select a mating card from the dropdown to the left.", FacesMessage.SEVERITY_ERROR);
        }
        if(!type.equals("mating") && getDetailCardKey().equals("")){
            this.addToMessageQueue("Please select a detail card from the dropdown to the left.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            CageCardBean cageCardBean = new CageCardBean();

            CageCardDTO cageCardDTO = new CageCardDTO();
            cageCardDTO.setMyWorkgroups(this.getCurrentUserGuestWorkgroups());
            if(type.equals("mating")){
                cageCardDTO.setCageCardType(CageCardType.Mating);
            }
            else{
                cageCardDTO.setCageCardType(CageCardType.Detail);
            }
            cageCardDTO.setPenID(id);
            cageCardDTO.setQuantity("single");
            cageCardBean.setTheDTO(cageCardDTO);
            cageCardBean.setCageID(id);

            if(type.equals("mating")){
                cageCardBean.setEntityKey(new Integer(matingCardKey));
            }
            else{
                cageCardBean.setEntityKey(new Integer(detailCardKey));
            }
            cageCardBean.setNumberOfCards(1);
            cageCardBean.downloadCageCardAction();
            cageCardBean.externalDownloadCageCard();
        }
    }   
    
    public void showMice(ActionEvent event){
        selectedContainerID = (String) event.getComponent().getAttributes().get("paramCageID");
        selectedContainerKey = (String) event.getComponent().getAttributes().get("paramCageKey");
                
        this.setMouseDataModel(new ListDataModel());
        this.mouseList = new MouseDAO().getLivingMiceInCageByOwner(selectedContainerKey, this.getCurrentUserGuestWorkgroups());
       
        this.getMouseDataModel().setWrappedData(this.mouseList);
    }
        
    public void newCageDrop(){
        
        String draggedID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedCage");
        String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");

        System.out.println("Dragged: " + draggedID);
        System.out.println("Dropped on: " + droppedKey);
        
        boolean flag = false;
        for(LevelContainerDTO dto : containers){
            if(dto.getCage().getContainerID().equals(draggedID)){
                flag = true;
                this.addToMessageQueue("This container is already on this vivaria layout, if you wish to move it please drag the container panel"
                        + " to another location.", FacesMessage.SEVERITY_ERROR);
            }
        }        
        if(!flag){
            try{
                ContainerDTO container = containerDAO.getContainerByID(draggedID);

                //1. First go to the database
                //need to go from dropped key to x and y coords

                double position = Double.parseDouble(droppedKey);
                position = position + 1; //go from starting at 0 to one
                //to get y divide the position by the number of columns (xmax)
                int y = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
                int x = (int) position - (y-1) * (Integer.parseInt(getSelectedLevel().getXmax()));

                System.out.println("(" + x + ", " + y + ")");
                ContainerHistoryDTO dto = new ContainerHistoryDTO();
                dto.setX(new Integer(x).toString());
                dto.setY(new Integer(y).toString());
                dto.setZ("1");
                dto.setLevelKey(getSelectedLevel().getLevel_key());
                dto.setRoomKey(new Integer(getSelectedLevel().getRoom_key()));
                dto.setContainerKey(new Integer(container.getContainer_key()).intValue());
                dto.setActionDate(new Date());
                //insert new Container History into database
                container.setContainerHistory_key(containerDAO.insertLevelContainerHistory(dto));
                dto.setContainerHistorykey(new Integer(container.getContainerHistory_key()));
                container.setCageHistoryDTO(dto);

                //2. Adjust the view
                //get the dragged container
                LevelContainerDTO dragged = new LevelContainerDTO();
                dragged.setCage(container);
                dragged.setSex(levelDAO.getContainerSex(dragged.getCage().getContainer_key()));
                
                if(levelDAO.isMatingCage(draggedID)){
                    dragged.setStyle("slot cage matingCage");
                    dragged.setType("mating");
                    dragged.setSex("breeding");
                    
                    //cage strain
                    String cageStrain = levelDAO.getMatingCageLitterStrain(container.getContainerID());                    
                    dragged.setStrain(cageStrain);
                    if(cageStrain.length() <= 16){
                        dragged.setDisplayStrain(cageStrain);
                    }
                    else{
                        dragged.setDisplayStrain(cageStrain.substring(0, 7) + "..." + cageStrain.substring(cageStrain.length() - 7, cageStrain.length()));
                    }
                }
                else{
                    dragged.setStyle("slot cage");
                    dragged.setType("holding");
                    
                    //cage strain
                    String cageStrain = levelDAO.getContainerStrain(container.getContainer_key());
                    dragged.setStrain(cageStrain);
                    if(cageStrain.length() <= 16){
                        dragged.setDisplayStrain(cageStrain);
                    }
                    else{
                        dragged.setDisplayStrain(cageStrain.substring(0, 7) + "..." + cageStrain.substring(cageStrain.length() - 7, cageStrain.length()));
                    }
                }

                if(dragged.getStrain().equals(cageFilterStrain.getStrainName())){
                    dragged.setStyle(dragged.getStyle() + " strain-highlight");
                }

                dragged.setPosition(droppedKey);

                containers.set(Integer.parseInt(droppedKey), dragged);
                this.addToMessageQueue("Cage " + dragged.getCage().getContainerID() + " successfully added to " + getSelectedLevel().getLevelId(), FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void moveMouseDrop(){
        String draggedID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedMouse");
        String droppedPosition = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");

        System.out.println("Dragged: " + draggedID);
        System.out.println("Dropped on: " + droppedPosition);
        
        //don't forget to update strain and gender of dropped cage.
        ArrayList<MouseDTO> mice = (ArrayList<MouseDTO>) this.mouseDataModel.getWrappedData();
        MouseDTO movedMouse = null;
        for(MouseDTO mouse : mice){
            if(mouse.getID().equals(draggedID)){
                movedMouse = mouse;
            }
        }
        //make sure you can find moved mouse, this message should never be seen
        if(movedMouse == null){
            this.addToMessageQueue("Mouse could not be moved...", FacesMessage.SEVERITY_ERROR);
        }
        else if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), movedMouse.getOwner())){            
            this.addToMessageQueue("You do not have permission to move this mouse. You are only a guest in workgroup: " + movedMouse.getOwner(), FacesMessage.SEVERITY_ERROR);
        }
        else{
            try{
                //update DB
                LevelContainerDTO lc = containers.get(Integer.parseInt(droppedPosition));
                //check that mouse isn't already in that cage...
                if(lc.getCage().getContainerID().equals(this.selectedContainerID)){
                    this.addToMessageQueue("Mouse " + movedMouse.getID() + " is already in cage " + lc.getCage().getContainerID(), FacesMessage.SEVERITY_ERROR);
                }
                else{
                    levelDAO.moveMouse(movedMouse.getMouse_key(), lc.getCage().getContainer_key());
                    this.addToMessageQueue("Mouse " + movedMouse.getID() + " moved to container " + lc.getCage().getContainerID(), FacesMessage.SEVERITY_INFO);
                    //update view
                    mice.remove(movedMouse); 
                    //check to see that mouse is or isn't of same strain as cage it's being moved to
                    //if it's an empty cage set strain to mouse's strain
                    if(lc.getStrain().equals("")){
                        lc.setStrain(movedMouse.getStrainName());
                    }
                    else if(!lc.getStrain().equals(movedMouse.getStrainName())){
                        lc.setStrain("mixed");
                    }
                    if(lc.getStrain().length() <= 16){
                        lc.setDisplayStrain(lc.getStrain());
                    }
                    else{
                        lc.setDisplayStrain(lc.getStrain().substring(0, 7) + "..." + lc.getStrain().substring(lc.getStrain().length() - 7, lc.getStrain().length()));
                    }
                    if(lc.getSex().equals("")){
                        lc.setSex(movedMouse.getSex());
                    }
                    else if(!lc.getSex().equals(movedMouse.getSex())){
                        lc.setSex("F/M");
                    }
                    
                    LevelContainerDTO oldContainer = null;
                    //check to see if moving mouse changes status of mouse's old cage from mixed to all of one strain or sex and update view
                    for(LevelContainerDTO lcDTO : containers){
                        if(lcDTO.getCage().getContainerID().equals(this.selectedContainerID)){
                            oldContainer = lcDTO;
                        }
                    }
                    if(oldContainer != null){
                        if(mice.isEmpty()){
                            oldContainer.setDisplayStrain("");
                            oldContainer.setStrain("");
                            oldContainer.setSex("");
                        }
                        else{
                            if(oldContainer.getSex().equals("F/M") || oldContainer.getSex().equals("breeding")){
                                String sex1 = "";
                                boolean mixed = false;
                                for(MouseDTO mouse : mice){
                                    if(sex1.equals("")){
                                        sex1 = mouse.getSex();
                                    }
                                    String sex2 = mouse.getSex();
                                    if(!sex2.equals(sex1)){
                                        mixed = true;
                                    }
                                }
                                if(!mixed){
                                    oldContainer.setSex(sex1);
                                }
                            }
                            if(oldContainer.getStrain().equals("mixed")){
                                String strain1 = "";
                                boolean mixed = false;
                                for(MouseDTO mouse : mice){
                                    if(strain1.equals("")){
                                        strain1 = mouse.getStrainName();
                                    }
                                    String strain2 = mouse.getStrainName();
                                    if(!strain2.equals(strain1)){
                                        mixed = true;
                                    }
                                }
                                if(!mixed){
                                    oldContainer.setStrain(strain1);
                                    if(oldContainer.getStrain().length() <= 16){
                                        oldContainer.setDisplayStrain(oldContainer.getStrain());
                                    }
                                    else{
                                        oldContainer.setDisplayStrain(oldContainer.getStrain().substring(0, 7) + "..." + oldContainer.getStrain().substring(oldContainer.getStrain().length() - 7, oldContainer.getStrain().length()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    /*
     * 
     * 
     * MOUSE STATUS CHANGE STUFF
     * 
     * 
     */
    public void cullDrop(){
        boolean flag = false;
        String draggedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedPosition");
        System.out.println("Dragged: " + draggedKey);
        LevelContainerDTO dragged = containers.get(Integer.parseInt(draggedKey));
        
        ArrayList<MouseDTO> miceInCage = new MouseDAO().getLivingMiceInCage(dragged.getCage().getContainer_key());
        ArrayList<String> workgroups = this.getCurrentUserColonyManageWorkgroups();
        for(MouseDTO mouse : miceInCage){
            if(!containsIgnoreCase(workgroups, mouse.getOwner())){
                flag = true; 
                this.addToMessageQueue("You are not the owner of all the mice in this cage, to change life status you must own all the mice.", FacesMessage.SEVERITY_WARN);
            }
        }
        if(!flag){
            if(dragged.getStyle().contains("matingCage")){
                this.addToMessageQueue("Exiting and retiring a mating cage will automatically retire the mating, if you do not wish to retire this mating remove the mating cage before clicking submit.", FacesMessage.SEVERITY_WARN);
            }
            this.statusChangeContainers.add(dragged);
        }
    }
    
    public void exitAndRetireMice(){
        try{
            boolean flag = false;
            if(statusChangeContainers.isEmpty()){
                flag = true;
                this.addToMessageQueue("Add cages to the status change list before clicking submit.", FacesMessage.SEVERITY_ERROR);
            }
            if(lifeStatus == null){
                flag = true;
                this.addToMessageQueue("Life status is required, please select a life status.", FacesMessage.SEVERITY_ERROR);
            }
            if(containerStatus == null){
                flag = true;
                this.addToMessageQueue("Container status is required, please select a container status.", FacesMessage.SEVERITY_ERROR);                
            }
            if(!flag){
                
                for(LevelContainerDTO lc : this.statusChangeContainers){
                    levelDAO.changeContainerStatus(containerStatus.getContainerStatuskey().toString(), lc.getCage().getContainerHistory_key());
                    levelDAO.changePenLifeStatus(lifeStatus, lc.getCage().getContainer_key());
                    if(lc.getStyle().contains("matingCage") && lifeStatus.getExitStatus()){
                        //retire mating here
                        levelDAO.retireMating(lc.getCage().getContainerID());
                    }
                    //if cage isn't active anymore switch w/ empty slot
                    if(!containerStatus.getContainerStatus().equalsIgnoreCase("active")){
                        //initialize empty container
                        ContainerDTO cEmpty = new ContainerDTO();
                        cEmpty.setContainerID("empty");
                        cEmpty.setContainerName("empty");

                        //position is set as a hidden var in the view so when draggging and dropping cages and stuff
                        //we know where they are dragged and dropped on according to the LL
                        LevelContainerDTO empty = new LevelContainerDTO();
                        empty.setCage(cEmpty);
                        empty.setStyle("empty slot");
                        empty.setPosition(lc.getPosition());

                        containers.set(Integer.parseInt(lc.getPosition()), empty);
                    }
                    this.addToMessageQueue("Container " + lc.getCage().getContainerID() + " changed to " + containerStatus.getContainerStatus()
                            + " and mice changed to " + lifeStatus.getDescription(), FacesMessage.SEVERITY_INFO);                    
                }
                statusChangeContainers = new ArrayList<LevelContainerDTO>();
            }
            else{
                for(LevelContainerDTO lc : this.statusChangeContainers){
                    lc.setStyle(lc.getStyle() + " delete-highlight");
                }
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void removeFromStatusChange(){
        Integer remove = this.getKey("paramRowIndex");
        System.out.println(remove);
        LevelContainerDTO statusLC = statusChangeContainers.get(remove.intValue());
        removedCullCage = statusLC.getCage().getContainerID();
        statusChangeContainers.remove(remove.intValue());
        for(LevelContainerDTO lcDTO : containers){
            if(lcDTO.getCage().getContainerID().equals(statusLC.getCage().getContainerID())){
                lcDTO.setStyle(lcDTO.getStyle().replace("delete-highlight", ""));
            }
        }
    }
    
    //methods for adding a cage
    public void selectSlotForCage(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
        dto.setStyle(dto.getStyle() + " createCage");
        dto.getCage().setContainerID("New Cage");
        dto.getCage().setContainerName("New Cage");
        dto.setType("createCage");
        cageID = containerDAO.getNextAvailablePenId();
        addingCage = true;
    }
    
    public void cancelCreateCage(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        //have to create an empty cage and set value to it
        LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
        dto.setStyle("empty slot");
        dto.getCage().setContainerID("empty");
        dto.getCage().setContainerName("empty");
        dto.setType("");
        addingCage = false;
    }
    
    public void saveNewCage(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        //validate
        boolean flag = false;
        if(actionDate.after(new Date())){
            flag = true;
            this.addToMessageQueue("Cage date cannot be after today's date.", FacesMessage.SEVERITY_ERROR);
        }
        if(cageID.equals("")){
            flag = true;
            this.addToMessageQueue("Cage ID is required, please provide a cage ID.", FacesMessage.SEVERITY_ERROR);
        }
        if(containerDAO.containerExists(cageID)){
            flag = true;
            this.addToMessageQueue("Selected cage ID already exists, try another cage ID.", FacesMessage.SEVERITY_ERROR);
        }
        try{
            Integer.parseInt(cageID);
        }
        catch(Exception e){
            flag = true;
            this.addToMessageQueue("Cage ID must be an integer number.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //save
            try{
                LevelContainerDTO lcDTO = containers.get(Integer.parseInt(cagePos));
                
                ContainerDTO cDTO = new ContainerDTO();
                cDTO.setContainerID(cageID);
                cDTO.setContainerName(cageName);
                cDTO.setComment(cageComment);
                cDTO.setContainerHistory_key("0");
                cDTO.setContainer_key(containerDAO.insertContainer(cDTO));
 
                JCMSDbInfoEntity dbInfoEntity = new JCMSDbInfoEntity();
                List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();
                
                // get the dbinfo record
                info = new ListSupportDTO().getJCMSDbInfo();
                if (info.size() > 0) {
                    dbInfoEntity = info.get(0);
                }
                //set max pen id in dbinfo if new container ID is greater than max
                if(dbInfoEntity.getMaxPenID() < Integer.parseInt(cDTO.getContainerID())){
                    containerDAO.updateNextAvailablePenId(cDTO.getContainerID());
                }

                //now container history
                ContainerHistoryDTO chDTO = new ContainerHistoryDTO();
                chDTO.setActionDate(actionDate);
                chDTO.setContainerKey(new Integer(cDTO.getContainer_key()));
                chDTO.setLevelKey(getSelectedLevel().getLevel_key());
                chDTO.setRoomKey(new Integer(getSelectedLevel().getRoom_key()));

                //calculate x and y position from container position
                double position = Double.parseDouble(cagePos);
                position = position + 1; //go from starting at 0 to one
                //to get y divide the position by the number of columns (xmax)
                int y = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
                int x = (int) position - (y-1) * (Integer.parseInt(getSelectedLevel().getXmax()));

                chDTO.setX(new Integer(x).toString());
                chDTO.setY(new Integer(y).toString());
                chDTO.setZ("1");
                //insert container history, will also update the container automatically                
                chDTO.setContainerHistorykey(new Integer(containerDAO.insertLevelContainerHistory(chDTO)));

                cDTO.setCageHistoryDTO(chDTO);
                
                lcDTO.setCage(cDTO);
                lcDTO.setType("holding");
                lcDTO.setStyle("slot cage");
                addingCage = false;
                
                //reset view
                cageID = "";
                cageName = "";
                cageComment = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void clearCageInfo(){
        for(LevelContainerDTO dto : containers){
            if(dto.getType().equals("createCage")){
                dto.setStyle("empty slot");
                dto.getCage().setContainerID("empty");
                dto.getCage().setContainerName("empty");
                dto.setType("");
                addingCage = false;
            }
        }
    }
        
    public void nodeExpand(NodeExpandEvent event){
        event.getTreeNode().setExpanded(true);
    }
    
    public void nodeCollapse(NodeCollapseEvent event){
        event.getTreeNode().setExpanded(false);
    }
    
    public void strainFilterChange(){
        if(cageFilterStrain != null && cageFilterStrain.getStrainKey() != null){
            pickListContainers = levelDAO.getCageUnits(cageFilterStrain.getStrainKey().toString(), this.getCurrentUserColonyManageWorkgroups());
            strainName = cageFilterStrain.getStrainName();
        }
        else{
            pickListContainers = null;
            strainName = "";
        }
    }
    
    public void cageDrop(){
        String draggedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedPosition");
        String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");

        System.out.println("Dragged: " + draggedKey);
        System.out.println("Dropped on: " + droppedKey);
       
        LevelContainerDTO dragged = containers.get(Integer.parseInt(draggedKey));
        LevelContainerDTO dropped = containers.get(Integer.parseInt(droppedKey));
        
        try{
            double position = Double.parseDouble(droppedKey);
            position = position + 1; //go from starting at 0 to one
            
            //to get y divide the position by the number of columns (xmax)
            int y1 = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
            int x1 = (int) position - (y1-1) * (Integer.parseInt(getSelectedLevel().getXmax()));
                        
            dragged.getCage().getCageHistoryDTO();
            
            ContainerHistoryDTO dto = dragged.getCage().getCageHistoryDTO();
            dto.setX(new Integer(x1).toString());
            dto.setY(new Integer(y1).toString());
            dto.setActionDate(new Date());
            dto.setZ("1");
            dto.setContainerHistorykey(Integer.parseInt(dragged.getCage().getContainerHistory_key()));
            //update database 
            containerDAO.insertLevelContainerHistory(dto);

            
            //only needs to be done if swapping cages - ie, dropped cage is not empty
            if(!dropped.getCage().getContainerID().equals("empty")){
                position = Double.parseDouble(draggedKey);
                position = position + 1; //go from starting at 0 to one

                int y2 = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
                int x2 = (int) position - (y2-1) * (Integer.parseInt(getSelectedLevel().getXmax()));
                
                dto = dropped.getCage().getCageHistoryDTO();
                
                dto.setX(new Integer(x2).toString());
                dto.setY(new Integer(y2).toString());
                dto.setActionDate(new Date());
                dto.setZ("1");
                dto.setContainerHistorykey(Integer.parseInt(dropped.getCage().getContainerHistory_key()));
                //update database 
                containerDAO.insertLevelContainerHistory(dto);
            }
            
            //update view
            dropped.setPosition(draggedKey);
            dragged.setPosition(droppedKey);

            containers.set(Integer.parseInt(draggedKey), dropped);
            containers.set(Integer.parseInt(droppedKey), dragged);
            
            this.addToMessageQueue("Container " + dragged.getCage().getContainerID() + " successfully moved.", FacesMessage.SEVERITY_INFO);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event){
        if(event.getTreeNode().getData() instanceof LevelDTO){
            expanded = false;
            setSelectedLevel((LevelDTO) event.getTreeNode().getData());
            addCageLabel = " to " + getSelectedLevel().getLevelId();
            AdminRoomDTO room = new AdminRoomDTO();
            room.setRoomKey(selectedLevel.getRoom_key());
            for(AdminRoomDTO r : adminDAO.getRooms(room)){
                setCurrentRoom(r);
            }
            
            
            columns = getSelectedLevel().getXmax();
            //decompose x, y into linear coordinates where 1,1 is top left or 0 and xmax, ymax is bottom right or xmax*ymax - 1
            int length = Integer.parseInt(getSelectedLevel().getXmax()) * Integer.parseInt(getSelectedLevel().getYmax());
            containers = new LinkedList<LevelContainerDTO>();
            
            
            
            //position is set as a hidden var in the view so when draggging and dropping cages and stuff
            //we know where they are dragged and dropped on according to the LL
            int position = 0;
            //populate all positions with empty cages - those positions with cages in them will be over written
            while(containers.size() != length){//initialize empty container
                ContainerDTO cEmpty = new ContainerDTO();
                cEmpty.setContainerID("empty");
                cEmpty.setContainerName("empty");
                LevelContainerDTO empty = new LevelContainerDTO();
                empty.setCage(cEmpty);
                empty.setStyle("empty slot");
                empty.setPosition(new Integer(position).toString());
                position++;
                containers.add(empty);
            }
            
            ArrayList<ContainerDTO> containersInLevel = levelDAO.getContainersByLevel(getSelectedLevel().getLevel_key());
            ArrayList<String> matingCages = levelDAO.getMatingCages(getSelectedLevel().getLevel_key());
            for(ContainerDTO container : containersInLevel){
                LevelContainerDTO dto = new LevelContainerDTO();
                dto.setCage(container);
                
                dto.setSex(levelDAO.getContainerSex(container.getContainer_key()));                
                if(matingCages.contains(dto.getCage().getContainerID())){
                    dto.setStyle("slot cage matingCage");
                    dto.setType("mating");
                    dto.setSex("breeding");
                    
                    //use the litter strain as the cage strain if mating cage
                    String cageStrain = levelDAO.getMatingCageLitterStrain(dto.getCage().getContainerID());                    
                    dto.setStrain(cageStrain);
                    if(cageStrain.length() <= 16){                        
                        dto.setDisplayStrain(cageStrain);
                    }
                    else{
                        dto.setDisplayStrain(cageStrain.substring(0, 7) + "..." + cageStrain.substring(cageStrain.length() - 7, cageStrain.length()));
                    }
                }
                else{
                    dto.setStyle("slot cage");
                    dto.setType("holding");
                    
                    String cageStrain = levelDAO.getContainerStrain(container.getContainer_key());
                    dto.setStrain(cageStrain);
                    if(cageStrain.length() <= 16){
                        dto.setDisplayStrain(cageStrain);
                    }
                    else{
                        dto.setDisplayStrain(cageStrain.substring(0, 7) + "..." + cageStrain.substring(cageStrain.length() - 7, cageStrain.length()));
                    }
                }
                if(dto.getStrain().equals("")){
                    dto.setStrain("");
                    dto.setDisplayStrain("");
                }
                
                //get the position of the cage in the linear plane
                int cagePosition = Integer.parseInt(getSelectedLevel().getXmax())*(Integer.parseInt(container.getCageHistoryDTO().getY()) - 1) + Integer.parseInt(container.getCageHistoryDTO().getX()) - 1;
                dto.setPosition(new Integer(cagePosition).toString());
                containers.set(cagePosition, dto);
            }
            statusChangeContainers = new ArrayList<LevelContainerDTO>();
            this.clearCageInfo();
            this.clearLitterInfo();
            this.clearMatingInfo();
        }    
        else{
            addCageLabel = "";
        }
    }
        
    /*
     * Litter with pups stuff below
     */
    public void selectCageForLitter(ActionEvent event){
        boolean flag = false;
        for(LevelContainerDTO dto : containers){
            if(dto.getType().equals("createLitter")){
                this.addToMessageQueue("Only one litter can be created at a time.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(!flag){
            String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
            System.out.println(cagePos);
            LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
            dto.setStyle(dto.getStyle() + " createLitter");
            dto.setType("createLitter");
            try{
                int matingID = Integer.parseInt(levelDAO.getMatingInCage(dto.getCage().getContainerID()));
                litterMating = (MatingEntity) systemDAO.getSystemFacadeLocal().baseFindByMatingID(new MatingEntity(), matingID);
                MouseEntity dam1 = (MouseEntity) systemDAO.getSystemFacadeLocal().findMouse(litterMating.getDam1Key());
                this.baseMouseID = dam1.getId();
                dto.setStyle(dto.getStyle() + " purpleBackground");
            }
            catch(Exception e){
                this.addToMessageQueue("Mating could not be found for mice in cage " + dto.getCage().getContainerID(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void saveLitter(ActionEvent event){
        boolean flag = false;
        for(VivariaPupDTO vpDTO : pups){
            for(VivariaPupDTO vpDTO2 : pups){
                if(!vpDTO.equals(vpDTO2)){
                    if(vpDTO.getPup().getId().equals(vpDTO2.getPup().getId())){
                        this.addToMessageQueue("Duplicate mouse ID for " + vpDTO2.getPup().getId(), FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        }
        for(VivariaPupDTO vpDTO : pups){
            //check if mouse ID exists
            if(levelDAO.getMouseExists(vpDTO.getPup().getId())){
                flag = true;
                this.addToMessageQueue("Mouse with ID " + vpDTO.getPup().getId() + " already exists, please select another ID.", FacesMessage.SEVERITY_ERROR);   
            }
        }
        if(nextLitterID.equals("")){
            flag = true;
            this.addToMessageQueue("Litter ID is required, please provide a litter ID.", FacesMessage.SEVERITY_ERROR);            
        }
        if(litterMating == null){
            flag = true;
            this.addToMessageQueue("Mating is required, please select a mating to add a litter.", FacesMessage.SEVERITY_ERROR);    
        }
        
        if(totalBorn.equals("")){
            flag = true;
            this.addToMessageQueue("Total born is required, please select a value for total born to add a litter.", FacesMessage.SEVERITY_ERROR); 
        }
        else{
            try{
                Short.parseShort(totalBorn);
            }
            catch(NumberFormatException e){
                flag=true;
                this.addToMessageQueue("Total born must be an integer, please provide an integer for total born", FacesMessage.SEVERITY_ERROR);
            }
        }
        
        if(!numFemale.isEmpty()){
            try{
                Short.parseShort(numFemale);
            }
            catch(NumberFormatException e){
                flag=true;
                this.addToMessageQueue("Total born must be an integer, please provide an integer for total born", FacesMessage.SEVERITY_ERROR);
            }
        }
        
        if(!numMale.isEmpty()){
            try{
                Short.parseShort(numMale);
            }
            catch(NumberFormatException e){
                flag=true;
                this.addToMessageQueue("Total born must be an integer, please provide an integer for total born", FacesMessage.SEVERITY_ERROR);
            }
        }
        
        if(dateBorn == null){
                flag = true;
                this.addToMessageQueue("Date born is required, please select a date born to add a litter.", FacesMessage.SEVERITY_ERROR); 
            }
        
        Integer litterNumBornDead = null;
        Integer litterNumCulledAtWean = null;
        Integer litterNumMissingAtWean = null; 
        int combinedNumber = 0;
        //need to check for different cases, IE add litter w/ pups vs just add litter
        if(!numberBornDead.isEmpty()){
            try{
                litterNumBornDead = Integer.parseInt(this.numberBornDead);
                combinedNumber = combinedNumber + litterNumBornDead;
            }
            catch(NumberFormatException e){
                this.addToMessageQueue("Number born dead must be an integer, please provide an integer", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(!numberCulledAtWean.isEmpty()){
            try{
                litterNumCulledAtWean = Integer.parseInt(this.numberCulledAtWean);
                combinedNumber = combinedNumber + litterNumCulledAtWean;
            }
            catch(NumberFormatException e){
                this.addToMessageQueue("Number culled at wean must be an integer, please provide an integer", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(!numberMissingAtWean.isEmpty()){
            try{
                litterNumMissingAtWean = Integer.parseInt(this.numberMissingAtWean);
                combinedNumber = combinedNumber + litterNumMissingAtWean;
            }
            catch(NumberFormatException e){
                this.addToMessageQueue("Number missing at wean must be an integer, please provide an integer", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        try{
            if(combinedNumber > Integer.parseInt(totalBorn)){                
                this.addToMessageQueue("Number born dead, number culled at wean, and number missing at wean cannot be greater than total born.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        catch(Exception e){}
        if(!flag){
            //first need to create the cages
            try{
                for(VivariaPupDTO vpDTO : pups){
                    int pos = Integer.parseInt(vpDTO.getPosition());
                    LevelContainerDTO lcDTO = containers.get(pos);
                    if(lcDTO.getCage().getContainerID().equals("empty")){
                        ContainerDTO cDTO = new ContainerDTO();
                        if(lcDTO.getCage().getContainerName().equals("empty")){
                            lcDTO.getCage().setContainerName("");
                        }
                        cDTO.setContainerName(lcDTO.getCage().getContainerName());
                        cDTO.setContainerHistory_key("0");
                        cDTO.setContainerID(containerDAO.getNextAvailablePenId());
                        cDTO.setContainer_key(containerDAO.insertContainer(cDTO));

                        containerDAO.updateNextAvailablePenId(cDTO.getContainerID());

                        //now container history
                        ContainerHistoryDTO chDTO = new ContainerHistoryDTO();
                        chDTO.setActionDate(new Date());
                        chDTO.setContainerKey(new Integer(cDTO.getContainer_key()));
                        chDTO.setLevelKey(getSelectedLevel().getLevel_key());
                        chDTO.setRoomKey(new Integer(getSelectedLevel().getRoom_key()));

                        //calculate x and y position from container position
                        double position = Double.parseDouble(vpDTO.getPosition());
                        position = position + 1; //go from starting at 0 to one
                        //to get y divide the position by the number of columns (xmax)
                        int y = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
                        int x = (int) position - (y-1) * (Integer.parseInt(getSelectedLevel().getXmax()));

                        chDTO.setX(new Integer(x).toString());
                        chDTO.setY(new Integer(y).toString());
                        chDTO.setZ("1");
                        //insert container history, will also update the container automatically                
                        chDTO.setContainerHistorykey(new Integer(containerDAO.insertLevelContainerHistory(chDTO)));

                        cDTO.setCageHistoryDTO(chDTO);
                        lcDTO.setCage(cDTO);
                        lcDTO.setStrain(litterMating.getStrainKey().getStrainName());
                        if(lcDTO.getStrain().length() <= 16){
                            lcDTO.setDisplayStrain(lcDTO.getStrain());
                        }
                        else{
                            lcDTO.setDisplayStrain(lcDTO.getStrain().substring(0, 7) + "..." + lcDTO.getStrain().substring(lcDTO.getStrain().length() - 7, lcDTO.getStrain().length()));
                        }
                        lcDTO.setStyle("slot cage");
                        lcDTO.setType("holding");
                        lcDTO.setSex(vpDTO.getPup().getSex());
                        //check if multiple genders
                        for(VivariaPupDTO pup : pups){
                            if(pup.getPosition().equals(vpDTO.getPosition()) && !pup.getPup().getSex().equals(vpDTO.getPup().getSex())){
                                lcDTO.setSex("F/M");
                            }
                        }
                        containers.set(pos, lcDTO);
                        vpDTO.setCage(cDTO);
                    }
                    else{
                        //check to see if strain or sex needs to be updated on display
                        if(lcDTO.getSex().equals("")){
                            lcDTO.setSex(vpDTO.getPup().getSex());
                        }
                        if(lcDTO.getStrain().equals("")){
                            lcDTO.setStrain(litterMating.getStrainKey().getStrainName());
                            if(lcDTO.getStrain().length() <= 16){
                                lcDTO.setDisplayStrain(lcDTO.getStrain());
                            }
                            else{
                                lcDTO.setDisplayStrain(lcDTO.getStrain().substring(0, 7) + "..." + lcDTO.getStrain().substring(lcDTO.getStrain().length() - 7, lcDTO.getStrain().length()));
                            }
                        }
                        //check if multiple genders
                        for(VivariaPupDTO pup : pups){
                            if(pup.getPosition().equals(vpDTO.getPosition()) && !pup.getPup().getSex().equals(vpDTO.getPup().getSex())){
                                lcDTO.setSex("F/M");
                            }
                        }
                        if(!lcDTO.getSex().equals(vpDTO.getPup().getSex())){
                            lcDTO.setSex("F/M");
                        }
                        if(!lcDTO.getStrain().equals(litterMating.getStrainKey().getStrainName())){
                            lcDTO.setStrain("mixed");
                            lcDTO.setDisplayStrain("mixed");
                        }
                        containers.set(pos, lcDTO);
                    }
                }
                //then need to create the litter
                LitterEntity litterEntity = new LitterEntity();
                // generate primary key
                Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(litterEntity);
                if (null == pk || 0 == pk) {
                    litterEntity.setLitterKey(1);
                } 
                else {
                    litterEntity.setLitterKey(pk + 1);
                }
                litterEntity.setBirthDate(dateBorn);
                litterEntity.setLitterID(nextLitterID);
                litterEntity.setMatingKey(litterMating);
                litterEntity.setNumFemale(Short.parseShort(numFemale));
                litterEntity.setNumMale(Short.parseShort(numMale));
                if(numberBornDead.isEmpty()){
                    litterEntity.setNumberBornDead(null);
                }
                else{
                    litterEntity.setNumberBornDead(new Integer(numberBornDead));
                }
                if(numberMissingAtWean.isEmpty()){
                    litterEntity.setNumberMissingAtWean(null);
                }
                else{
                    litterEntity.setNumberMissingAtWean(new Integer(numberMissingAtWean));
                }
                if(numberCulledAtWean.isEmpty()){
                    litterEntity.setNumberCulledAtWean(null);
                }
                else{
                    litterEntity.setNumberCulledAtWean(new Integer(numberCulledAtWean));
                }
                litterEntity.setStatus(litterStatus);
                litterEntity.setTagDate(tagDate);
                litterEntity.setTotalBorn(Short.parseShort(totalBorn));
                litterEntity.setWeanDate(weanDate);
                CvLitterTypeEntity litterTypeEntity = (CvLitterTypeEntity) systemDAO.getSystemFacadeLocal().baseFindByLitterType(new CvLitterTypeEntity(), "Live Birth");
                litterEntity.setLitterTypekey(litterTypeEntity);                
                litterEntity.setVersion(1);
                new SaveAppService().baseCreate(litterEntity);
                //then need to add the mice
                for(VivariaPupDTO pup : pups){
                    int pos = Integer.parseInt(pup.getPosition());
                    LevelContainerDTO lcDTO = containers.get(pos);
                    
                    MouseEntity me = new MouseEntity();
                    // generate primary key
                    pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(me);
                    me.setBirthDate(dateBorn);
                    me.setBreedingStatus("V");
                    me.setGeneration(litterEntity.getMatingKey().getGeneration());
                    me.setId(pup.getPup().getId());
                    me.setSex(pup.getPup().getSex());
                    me.setLifeStatus("A");
                    me.setLitterKey(litterEntity);
                    me.setOrigin(origin.getMouseOrigin());
                    me.setOwner(litterEntity.getMatingKey().getOwner());
                    me.setComment("ADDED IN BULK AS LITTER by user: " + this.getUserEntity().getUserName());
                    me.setStrainKey(litterEntity.getMatingKey().getStrainKey());
                    if (null == pk || 0 == pk) {
                        me.setMouseKey(1);
                    } 
                    else {
                        me.setMouseKey(pk + 1);
                    }
                    
                    ContainerEntity ce = this.mouseFunctions.findPen(Integer.parseInt(lcDTO.getCage().getContainerID()));
                    if (ce != null) {
                        me.setPenKey(ce);
                    }
                    new SaveAppService().baseCreate(me);
                }
                this.addToMessageQueue("Litter " + nextLitterID + " successfully added.", FacesMessage.SEVERITY_INFO);
                if(pups.size() == 1){
                    this.addToMessageQueue("Pup " + pups.get(0).getPup().getId() + " successfully added.", FacesMessage.SEVERITY_INFO);
                }
                else if(pups.size() == 2){
                    this.addToMessageQueue("Pups " + pups.get(0).getPup().getId() + " and " + pups.get(1).getPup().getId() + " successfully added.", FacesMessage.SEVERITY_INFO);
                }
                else if(pups.size() > 2){
                    String msg = "Pups ";
                    for(VivariaPupDTO pup : pups){
                        //last pup
                        if(pup.equals(pups.get(pups.size() -1))){
                            msg = msg + " and " + pup.getPup().getId();
                        }
                        else{
                            msg = msg + pup.getPup().getId() + ", ";
                        }
                    }
                    msg = msg + " successfully added.";
                    this.addToMessageQueue(msg, FacesMessage.SEVERITY_INFO);
                }
                //reset view
                clearLitterInfo();
                String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
                System.out.println(cagePos);
                LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
                dto.setStyle("slot cage matingCage");
                dto.setType("mating");
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    //super convoluted means of getting next available litter ID - copied from 
    //mating bean explaining why it's convoluted
    private JCMSDbInfoEntity updateLitterInfo(MatingEntity me){
        JCMSDbInfoEntity dbInfoEntity = new JCMSDbInfoEntity();
        List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();
        int litterNum = 0;
        int maxLitterNum = 0;
        int nextLitterNum = 0;

        // get the dbinfo record
        info = new ListSupportDTO().getJCMSDbInfo();
        if (info.size() > 0) {
            dbInfoEntity = info.get(0);
        }

        DbsetupEntity setupvar = new DbsetupEntity();
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "MTS_AUTO_LITTER_NUMS");

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
        }

        DbsetupEntity setupvar1 = new DbsetupEntity();
        
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "MTS_NUM_AUTO_LITTER_NUMS");

        if (entity != null) {
            setupvar1 = (DbsetupEntity) entity;
        }

        //if MTS_AUTO_LITTER_NUMS is ON, then get the value of MTS_NUM_AUTO_LITTER_NUMS
        // and update maxAutoLitterID in dbinfo and increment litterNum by 1.
        if ((setupvar != null && setupvar.getMTSValue() != null
                && !setupvar.getMTSValue().equals("")
                && setupvar.getMTSValue().equalsIgnoreCase("on"))) {
            if (setupvar1 != null) {
                litterNum = dbInfoEntity.getMaxAutoLitterNum();

                if (setupvar1.getMTSValue() != null && !setupvar1.getMTSValue().equals("")) {
                    // set the maxLitterNum
                    maxLitterNum = litterNum + Integer.parseInt(setupvar1.getMTSValue());

                    // update dbInfo table with new maxAutoLitter ID
                    dbInfoEntity.setMaxAutoLitterNum(maxLitterNum);
                    dbInfoEntity.setIsDirty();
                }
                // increment litternum by 1
                nextLitterNum = litterNum + 1;
                me.setSuggestedFirstLitterNum(nextLitterNum);

                System.out.println("maxLitterNum " + maxLitterNum);
                System.out.println("nextLitterNum " + nextLitterNum);
            }
        }
        return dbInfoEntity;
    }
    
    public void birthDateChangeListener(){
        if(this.calculateDates){
            DbsetupEntity entity = (DbsetupEntity) getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_STANDARD_WEAN_TIME");
            if (entity != null) {
                // assign default stdWeanTime
                if ((entity.getMTSValue() != null && !entity.getMTSValue().equals(""))) {
                    stdWeanTime = Integer.parseInt(entity.getMTSValue());
                }
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateBorn);

            cal.add(Calendar.DAY_OF_MONTH, this.stdWeanTime); // add 18 days  
            Date newDate = cal.getTime();

            tagDate = newDate;
            weanDate = newDate;
        }
    }
    
    public void dropFemalePup(){
        boolean flag = false;
        if(litterMating == null){
            flag = true;
            this.addToMessageQueue("A mating must be selected to add a pup.", FacesMessage.SEVERITY_ERROR);            
        }
        if(baseMouseID.equals("")){
            flag = true;
            this.addToMessageQueue("Base mouse ID is required to add a pup, please provide a base mouse ID.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");
            VivariaPupDTO vpDTO = new VivariaPupDTO();
            vpDTO.setPosition(droppedKey);
            LevelContainerDTO temp = containers.get(new Integer(droppedKey).intValue());            
            if(temp.getCage().getContainerID().equals("empty")){
                vpDTO.setCage(temp.getCage());                
            }
            else{
                vpDTO.setCage(temp.getCage());
            }
            MouseEntity pup = new MouseEntity();
            pup.setSex("F");
            pup.setBirthDate(dateBorn);
            pup.setGeneration(litterMating.getGeneration());
            
            //figure out increment ids here
            pup.setId(attemptIdParse(baseMouseID));
            for(VivariaPupDTO thePup : pups){
                if(getBaseID(pup.getId()).equals(getBaseID(thePup.getPup().getId()))){
                    pup.setId(mouseFunctions.incrementID(pup.getId(), "JCMS_MOUSEID_INCREMENT_RIGHTMOST"));
                }
            }
            
            vpDTO.setPup(pup);
            pups.add(vpDTO);
            int females;
            int males; //for calculating total pups
            if(numFemale.equals("")){
                numFemale = "1";
                females = 1;
            }
            else{
                females = Integer.parseInt(numFemale) + 1;
                numFemale = new Integer(females).toString();
            }
            if(numMale.equals("")){
                males = 0;
            }
            else{
                males = Integer.parseInt(numMale);
            }
            //calculate and update in view iff 
            int totalPups = females + males;
            if(totalBorn.equals("") || totalPups > Integer.parseInt(totalBorn)){
                totalBorn = new Integer(totalPups).toString();
            }
        }
    }
    
    public void dropMalePup(){
        boolean flag = false;
        if(litterMating == null){
            flag = true;
            this.addToMessageQueue("A mating must be selected to add a pup.", FacesMessage.SEVERITY_ERROR);            
        }
        if(baseMouseID.equals("")){
            flag = true;
            this.addToMessageQueue("Base mouse ID is required to add a pup, please provide a base mouse ID.", FacesMessage.SEVERITY_ERROR);
        }
        if(pups.size() > 20){
            flag = true;
            this.addToMessageQueue("There can only be 20 pups in a litter.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");
            VivariaPupDTO vpDTO = new VivariaPupDTO();
            vpDTO.setPosition(droppedKey);
            LevelContainerDTO temp = containers.get(new Integer(droppedKey).intValue());            
            vpDTO.setCage(temp.getCage());
            MouseEntity pup = new MouseEntity();
            pup.setSex("M");
            pup.setBirthDate(dateBorn);
            pup.setGeneration(litterMating.getGeneration());
            
            //figure out increment ids here
            pup.setId(attemptIdParse(baseMouseID));
            for(VivariaPupDTO thePup : pups){
                if(getBaseID(pup.getId()).equals(getBaseID(thePup.getPup().getId()))){
                    pup.setId(mouseFunctions.incrementID(pup.getId(), "JCMS_MOUSEID_INCREMENT_RIGHTMOST"));
                }
            }
            
            vpDTO.setPup(pup);
            pups.add(vpDTO);
            int males;
            int females;
            if(numMale.equals("")){
                numMale = "1";
                males = 1;
            }
            else{
                males = Integer.parseInt(numMale) + 1;
                numMale = new Integer(males).toString();
            }
            if(numFemale.equals("")){
                females = 0;
            }
            else{
                females = Integer.parseInt(numFemale);
            }
            //calculate and update in view iff 
            int totalPups = females + males;
            if(totalBorn.equals("") || totalPups > Integer.parseInt(totalBorn)){
                totalBorn = new Integer(totalPups).toString();
            }
        }
    }
    
    public void removePup(){
        Integer remove = this.getKey("paramRowIndex");
        System.out.println(remove);
        VivariaPupDTO vpDTO = pups.remove(remove.intValue());
        if(vpDTO.getPup().getSex().equalsIgnoreCase("M")){
            int males = Integer.parseInt(numMale) - 1;
            numMale = new Integer(males).toString();
        }
        else{
            int females = Integer.parseInt(numFemale) - 1;
            numFemale = new Integer(females).toString();
        }
    }
    
    private String getBaseID(String id){
        String baseID = "";
        if(!id.equals("")){
            DbSetupDAO dao = new DbSetupDAO();
            try{
                Integer.parseInt(id);
                return "";
            }
            catch(NumberFormatException e){
                System.out.println("isn't a number...");
            }
            if(dao.getJCMSMouseIDIncrementRightmost().getBlnMTSValue()){
                char[] charArrId = id.toCharArray();
                char c = charArrId[charArrId.length - 1];
                int idx = 1;
                while(Character.isDigit(c)){
                    idx++;
                    c = charArrId[charArrId.length - idx];
                }
                baseID = new String(Arrays.copyOfRange(charArrId, 0, charArrId.length - (idx-1)));
            }
            else{
                char[] charArrId = id.toCharArray();
                char c = charArrId[0];
                int idx = 1;
                while(Character.isDigit(c)){
                    c = charArrId[idx];
                    idx++;
                }
                if(idx!=1){
                    baseID = new String(Arrays.copyOfRange(charArrId, idx, id.length()-1));
                }
            }
        }
        return baseID;
    }
    
    private String attemptIdParse(String id){
        /*
        * A mouse ID that needs to be incremented consists of two parts, a number and an identifier.
        * For example if the id is B6-1 the ID would be 'B6-' and the number is '1'. This method needs to
        * parse out the B6- and the 1 and increment the 1 until you have a unique ID that doesn't exist
        * in the JCMS database. For example if B6-1 through 10 already exists and a user types in B6-1 this method
        * should check B6-1,2,3...10, 11 and find that B6-11 doesn't exist and say that is the value to use.
        * It also needs to be checked whether the setup var MouseIDIncrementRightmost is true or false.
        * And if it is false the number is the leftmost portion so 123-B6 would be 124-B6 where as if 
        * it were true 123-B6 would be incremented to 123-B7.
        */
        if(!id.equals("")){
            String theBaseID = "";
            String number = "";
            String nextID = "";

            DbSetupDAO dao = new DbSetupDAO();
            //special case where ID is an integer
            try{
                Integer.parseInt(id);
                String MySQLStatement = 
                        "SELECT MAX(CONVERT(ID, UNSIGNED INTEGER)) AS max "
                        + "FROM Mouse "
                        + "WHERE ID REGEXP '^[0-9]*$';";
                try{
                    number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                }
                catch(Exception e){}
                nextID = theBaseID + number;
                System.out.println(MySQLStatement); 
                System.out.println("Next ID: " + nextID);
                return nextID;
                //return theBaseID; //is a number
            }
            catch(NumberFormatException e){}
            //incrementing the rightmost portion of ID
            if(dao.getJCMSMouseIDIncrementRightmost().getBlnMTSValue()){
                char[] charArrId = id.toCharArray();
                char c = charArrId[charArrId.length - 1];
                int idx = 1;
                while(Character.isDigit(c)){
                    idx++;
                    c = charArrId[charArrId.length - idx];
                }
                theBaseID = new String(Arrays.copyOfRange(charArrId, 0, charArrId.length - (idx-1)));
                //if idx is not one that means that there is AT LEAST one digit at the end of the mouse ID
                if(idx!=1){
                    number = new String(Arrays.copyOfRange(charArrId, charArrId.length - (idx-1), id.length()-1));
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^" + theBaseID + "[0-9]*$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                        //need to append leading zeros, if the number you get from mysql is less in length then the 
                        while(number.length() < (idx - 1)){
                            number = "0" + number;
                        }
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println(MySQLStatement); 
                    System.out.println("Next ID: " + nextID);
                }
                else{
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^" + theBaseID + "[0-9]*$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                        while(number.length() < (idx - 1)){
                            number = "0" + number;
                        }
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println("Next ID: " + nextID);
                }
            }
            //incrementing the leftmost portion of ID
            else{
                char[] charArrId = id.toCharArray();
                char c = charArrId[0];
                int idx = 1;
                while(Character.isDigit(c)){
                    c = charArrId[idx];
                    idx++;
                }
                if(idx!=1){
                    number = new String(Arrays.copyOfRange(charArrId, 0, idx));
                    theBaseID = new String(Arrays.copyOfRange(charArrId, idx, id.length()-1));
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^[0-9]*" + theBaseID + "$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println(MySQLStatement);     
                    System.out.println("Next ID: " + nextID);
                }
                else{   
                    String MySQLStatement = 
                            "SELECT MAX(CONVERT(REPLACE(LOWER(ID), LOWER('"+ theBaseID +"'), ''), UNSIGNED INTEGER)) AS max "
                            + "FROM Mouse "
                            + "WHERE ID REGEXP LOWER('^[0-9]*" + theBaseID + "$')";
                    try{
                        number = new Integer(Integer.parseInt(new LitterDAO().getMaxIdNumber(MySQLStatement)) + 1).toString();
                    }
                    catch(Exception e){}
                    nextID = theBaseID + number;
                    System.out.println("Next ID: " + nextID);
                }
            }
            return nextID;
        }        
        return "";
    }
    
    public void generateNextLitterIDAction() {
        String sNextLitterID = "";
        int lMatingKey = 0;

        // make sure mating is selected
        if (litterMating == null) {
            this.addToMessageQueue("A mating must be selected to generate a litter ID.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation", "Mating ID is missing, please select a Mating ID"));
        } 
        else {
            lMatingKey = litterMating.getMatingKey().intValue();
            System.out.println("lMatingKey " + lMatingKey);

            sNextLitterID = this.cycleLitterIDs(lMatingKey);

            if (sNextLitterID == null || sNextLitterID.equals("")) {
                // Get the last litter entry for this mating.
                String lLitterID = getRepositoryService().baseFindMaxLitterID(new LitterEntity(), lMatingKey);

                if (lLitterID != null && !lLitterID.equals("")) {
                    sNextLitterID = this.mouseFunctions.incrementID(lLitterID, "JCMS_LITTERID_INCREMENT_RIGHTMOST");
                } 
                // zero is not a valid suggested first litter ID. For some reason the
                // mating forms put zero into the data if user has selected not to use 
                // auto litter nums so we need to check here.
                else if (litterMating != null && litterMating.getSuggestedFirstLitterNum() > 0) {
                    sNextLitterID = Integer.toString(litterMating.getSuggestedFirstLitterNum());
                } 
                else {
                    this.addToMessageQueue("Unable to find a Litter # "
                            + "for you. The selected mating has no associated litters "
                            + "and does not have a suggested first litter number.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to "
                            + "find a Litter # for you. The selected mating "
                            + "has no associated litters and does not have a "
                            + "suggested first litter number.", "autoMouseIDAction"));
                }
            }

            // now check that the litter number we're suggesting is valid
            int lLoopCount = 0;
            
            while (!this.validateUniqueLitterNumber(sNextLitterID)) {
                lLoopCount++;
                if (lLoopCount > 100) {
                    this.addToMessageQueue("Unable to compute a next litter ID for you.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to find a litter number for this mating.", "generateNextLitterID"));
                    sNextLitterID = "";
                    break;
                }
                String sCurrentLitterID = sNextLitterID;
                                
                sNextLitterID = this.mouseFunctions.incrementID(sNextLitterID, "JCMS_LITTERID_INCREMENT_RIGHTMOST");
                if (sNextLitterID.equals(sCurrentLitterID)) {
                    this.addToMessageQueue("Unable to compute a next litter ID for you.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logError(this.formatLogMessage("Unable to compute a next litter ID for you.", "generateNextLitterID"));
                    sNextLitterID = "";
                }
            }
        }
        System.out.println("sNextLitterID " + sNextLitterID);
        nextLitterID = sNextLitterID;
    }
    
    public String cycleLitterIDs(int matingKey) {

        //Rule #1: JCMS_LOOP_LITTER_NUMBERS must be true...
        DbsetupEntity setupvar = new DbsetupEntity();
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new DbsetupEntity(), "JCMS_LOOP_LITTER_NUMBERS");

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
            if ((setupvar != null && setupvar.getMTSValue() != null && !setupvar.getMTSValue().equals(""))) {
                if (setupvar.getMTSValue().equalsIgnoreCase("false")){
                    return "";
                }
            }
        }

        // Rule #2: The last entered litter ID must be a number followed
        //  by at most one character.

        // Get the last litter entry for this mating.
        int lLitterkey = getRepositoryService().baseFindMaxLitterKey(new 
                LitterEntity(), matingKey);

        if (lLitterkey == 0) {
            return "";
        }

        String sNumPortion;
        LitterEntity lEntity = (LitterEntity) getRepositoryService().
                baseFindByKey(new LitterEntity(), lLitterkey);
        String sLitterId = "";
        if (lEntity != null) {
            sLitterId = lEntity.getLitterID();
            System.out.println("sLitterId " + sLitterId);
        }

        // If the last character is a character and
        // we strip off the last character will it be a number
        String sAscValue = "";
        Boolean bHasSuffix = false, bRolledOver = false;

        // get the last character
        sAscValue = sLitterId.substring(sLitterId.length() - 1, sLitterId.length());
        System.out.println("last character " + sAscValue);

        // a character
        if (!this.isDigit(sAscValue.charAt(0))) {
            // strip off last character
            sNumPortion = sLitterId.substring(0, sLitterId.length() - 1);
            bHasSuffix = true; // note this for later...
        } else {
            sNumPortion = sLitterId;
            sAscValue = "";
        }
        System.out.println("sNumPortion " + sNumPortion);

        // if not int, then return false
        if (sNumPortion.length() > 0) {
            for (int i = 0; i < sNumPortion.length(); ++i) {
                if (!this.isDigit(sNumPortion.charAt(i))) {
                    return "";
                }
            }
        }

        // OK. We have either a number or a number with a single character appended.
        // let's try to generate a new litter ID.
        // sNumPortion has the numeric portion and sAscValue has the suffix (if
        // bHasSuffix is true.

        // get the SuggestedFirstLitterNum
        int lSugFirstLitNum = 0;
        if (this.litterMating != null) {
            lSugFirstLitNum = this.litterMating.getSuggestedFirstLitterNum();
            System.out.println("lSugFirstLitNum " + lSugFirstLitNum);
        }

        // Verify that it is not in the database.
        //While DCount("litterID", "Litter", "litterID='" & sLitterId & "'") <> 0
        if (!this.validateUniqueLitterNumber(sLitterId)) {

            // If it is the max rollover,
            // If it is in range increment,
            // If it is out of range, bail.
            if (sNumPortion != null && !sNumPortion.equals("")) {
                int intPortion = Integer.parseInt(sNumPortion);
                //System.out.println("intPortion " + intPortion);

                if (intPortion < (lSugFirstLitNum + 9)) { // Then ' increment it) {
                    sNumPortion = Integer.toString(intPortion + 1);
                } else if (intPortion == (lSugFirstLitNum + 9)) { // Then rollback
                    sNumPortion = Integer.toString(lSugFirstLitNum);
                    bRolledOver = true; // increment the suffix

                    if (bHasSuffix == true) {
                        char val = (char) (sAscValue.charAt(0) + 1);
                        sAscValue = Character.toString(val);
                        System.out.println("sAscValue when bHasSuffix == true "
                                + sAscValue);
                    } else {
                        sAscValue = "A";
                    }
                } else {
                    this.getLogger().logError(this.formatLogMessage("LitterID " + "out of range.", "cycleLitterIDs"));
                    // We are outside the range
                    return "";
                }
            }
            sLitterId = sNumPortion + sAscValue;
            System.out.println("sLitterId " + sLitterId);
        }
        return sLitterId;
    }
    
    public boolean isDigit(char ch) {
        if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }
    
    private boolean validateUniqueLitterNumber(String id) {
        // Check that litter id is unique.
        LitterEntity lEntity = (LitterEntity) getRepositoryService().baseFindByLitterID(new LitterEntity(), id);

        if (lEntity == null) {
            return true;
        }
        return false;
    }
    
    public void cancelLitter(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        System.out.println(cagePos);
        LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
        clearLitterInfo();
        dto.setType("mating");
        dto.setStyle("slot cage matingCage");
    }
    
    private void clearLitterInfo(){
        litterMating = null;
        pups.clear();
        nextLitterID = "";
        baseMouseID = "";
        litterComments = "";
        totalBorn = "";
        numMale = "0";
        numFemale = "0";
        totalBorn = "";
        for(LevelContainerDTO lc : containers){
            if(lc.getType().equals("createLitter")){
                lc.setStyle("empty slot matingCage");
                lc.setType("mating");
            }
        }
    }
    
    /*
     * 
     * MATING STUFF BELOW 
     * 
     * 
     */
    
    public void selectCageForMating(ActionEvent event){
        boolean flag = false;
        for(LevelContainerDTO dto : containers){
            if(dto.getType().equals("createMating")){
                this.addToMessageQueue("Only one mating can be created at a time.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(!flag){
            String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
            System.out.println(cagePos);
            LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
            dto.setStyle(dto.getStyle() + " createMating");
            dto.getCage().setContainerID("Mating Cage");
            dto.setType("createMating");
        }
    }
    
    public void saveMating(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        System.out.println(cagePos);
        LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
        
        boolean flag = false;
        //validation
        if(dam1 == null){
            this.addToMessageQueue("Dam 1 is required, please select a dam 1.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(sire == null){
            this.addToMessageQueue("Sire is required, please select a sire.", FacesMessage.SEVERITY_ERROR);   
            flag = true;         
        }
        if(litterStrain == null){
            this.addToMessageQueue("Litter strain is required, please select a litter strain.", FacesMessage.SEVERITY_ERROR);    
            flag = true;
        }
        if(litterGeneration == null){
            this.addToMessageQueue("Litter generation is required, please select a litter generation.", FacesMessage.SEVERITY_ERROR);   
            flag = true; 
        }
        if(matingDate == null){
            this.addToMessageQueue("Mating date is required, please select a mating date.", FacesMessage.SEVERITY_ERROR); 
            flag = true;
        }
        if(matingOwner == null){
            this.addToMessageQueue("Mating owner is required, please select a mating owner.", FacesMessage.SEVERITY_ERROR); 
            flag = true;
        }
        if(!flag){
            //do save
            //first have to create the cage, then containerHistory
            try{
                ContainerDTO cDTO = new ContainerDTO();
                cDTO.setContainerName(cageName);
                cDTO.setComment(cageComment);
                cDTO.setContainerHistory_key("0");
                cDTO.setContainerID(containerDAO.getNextAvailablePenId());
                cDTO.setContainer_key(containerDAO.insertContainer(cDTO));
                
                containerDAO.updateNextAvailablePenId(cDTO.getContainerID());
                
                //now container history
                ContainerHistoryDTO chDTO = new ContainerHistoryDTO();
                chDTO.setActionDate(new Date());
                chDTO.setContainerKey(new Integer(cDTO.getContainer_key()));
                chDTO.setLevelKey(getSelectedLevel().getLevel_key());
                chDTO.setRoomKey(new Integer(getSelectedLevel().getRoom_key()));
                
                //calculate x and y position from container position
                double position = Double.parseDouble(cagePos);
                position = position + 1; //go from starting at 0 to one
                //to get y divide the position by the number of columns (xmax)
                int y = (int) Math.ceil(position/Double.parseDouble(getSelectedLevel().getXmax()));
                int x = (int) position - (y-1) * (Integer.parseInt(getSelectedLevel().getXmax()));

                chDTO.setX(new Integer(x).toString());
                chDTO.setY(new Integer(y).toString());
                chDTO.setZ("1");
                //insert container history, will also update the container automatically                
                chDTO.setContainerHistorykey(new Integer(containerDAO.insertLevelContainerHistory(chDTO)));
                
                
                cDTO.setCageHistoryDTO(chDTO);
                
                //add Mating
                MatingEntity me = new MatingEntity();
                me.setComment(matingComment);
                me.setDam1Key(Integer.parseInt(dam1.getMouse_key()));
                if(dam2 != null){
                    me.setDam2Key(new Integer(dam2.getMouse_key()));
                }
                me.setGeneration(litterGeneration.getGeneration());
                me.setMatingDate(matingDate);
                for (CvCrossstatusEntity entity : listSupportDTO.getCvCrossStatus()) {
                    if (entity.getCrossStatus().equalsIgnoreCase("active")) {
                        me.setCrossStatuskey(entity);
                        break;
                    }
                }
                MatingDAO mDAO = new MatingDAO();
                me.setMatingID(mDAO.getNextMatingID());
                me.setNeedsTyping(needsGenotyping);
                me.setOwner(matingOwner.getOwner());
                me.setSireKey(mouseFunctions.findMouse(sire.getID()));
                me.setStrainKey(litterStrain);
                JCMSDbInfoEntity dbInfo = updateLitterInfo(me); //sets suggested first litter num to value from dbInfo
                me.setWeanNote(weanNote);
                if(weanTime.equals("standard")){
                    me.setWeanTime(true);
                }
                else{
                    me.setWeanTime(false);
                }
                // generate primary key
                Integer pk = this.getRepositoryService().baseFindMaxEntityPrimaryKey(me);

                if (null == pk || 0 == pk) {
                    me.setMatingKey(1);
                } else {
                    me.setMatingKey(pk + 1);
                }

                // set the version
                me.setVersion(0);

                //attempt mating save
                new SaveAppService().baseCreate(me);
                //for now only doing natural matings...
                mDAO.insertNaturalMatingUnitLink(me);
                
                
                //update litter info in dbInfo
                new SaveAppService().baseEdit(dbInfo);
                
                ContainerEntity ce = this.mouseFunctions.findPen(Integer.parseInt(cDTO.getContainerID()));
                //move sire to pen
                MouseEntity entitySire = this.mouseFunctions.findMouse(sire.getID());
                updatePenAndDiet(entitySire, ce);
                //move dam1 to pen
                MouseEntity entityDam1 = this.mouseFunctions.findMouse(dam1.getID());
                updatePenAndDiet(entityDam1, ce);
                //if dam2 is there, add her too
                if(dam2 != null){
                    MouseEntity entityDam2 = this.mouseFunctions.findMouse(dam2.getID());
                    updatePenAndDiet(entityDam2, ce);
                }
                
                //update cage on view
                dto.setCage(cDTO);
                dto.setSex("breeding");
                dto.setStrain(litterStrain.getStrainName());
                
                dto.setStyle("slot cage matingCage");
                dto.setType("mating");            
                if(dto.getStrain().length() <= 16){
                    dto.setDisplayStrain(dto.getStrain());
                }
                else{
                    dto.setDisplayStrain(dto.getStrain().substring(0, 7) + "..." + dto.getStrain().substring(dto.getStrain().length() - 7, dto.getStrain().length()));
                }
                //reset values that shouldn't be preserved
                lastLitterStrain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(litterStrain.getStrainKey()));
                lastDam1Strain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(new Integer(dam1.getStrain_key())));
                if(dam2 != null){
                    lastDam2Strain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(new Integer(dam2.getStrain_key())));
                }
                else{
                    lastDam2Strain = null;
                }
                lastSireStrain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(new Integer(sire.getStrain_key())));

                litterGeneration = null;
                litterStrain = null;
                dam1 = null;
                dam1ID = "";
                dam2 = null;
                dam2ID = "";
                sire = null;
                sireID = "";
                for(LevelContainerDTO lcDTO : containers){
                    if(!lcDTO.getCage().getContainerID().equals("empty") && !lcDTO.getType().contains("mating")){
                        String cageStrain = levelDAO.getContainerStrain(lcDTO.getCage().getContainer_key());
                        lcDTO.setStrain(cageStrain);
                        lcDTO.setSex(levelDAO.getContainerSex(lcDTO.getCage().getContainer_key()));
                        if(cageStrain.length() >= 16){
                            lcDTO.setDisplayStrain(cageStrain.substring(0, 7) + "..." + cageStrain.substring(cageStrain.length() - 7, cageStrain.length()));
                        }
                        else{
                            lcDTO.setDisplayStrain(cageStrain);
                        }
                    }
                }
                if(mouseList != null && !mouseList.isEmpty()){
                    this.setMouseDataModel(new ListDataModel());
                    this.mouseList = new MouseDAO().getLivingMiceInCageByOwner(selectedContainerKey, this.getCurrentUserGuestWorkgroups());
                    this.getMouseDataModel().setWrappedData(this.mouseList);
                }
                this.addToMessageQueue("Mating " + me.getMatingID() + " successfully created in container " + ce.getContainerID(), FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void clearMatingInfo(){
        for(LevelContainerDTO lc : containers){
            if(lc.getCage().getContainerID().equals("Mating Cage")){
                lc.getCage().setContainerID("empty");
                lc.getCage().setContainerName("empty");
                lc.setStyle("empty slot");
                lc.setType("");
                dam1 = null;
                dam2 = null;
                sire = null;
                dam1ID = "";
                dam2ID = "";
                sireID = "";
                litterGeneration = null;
            }
        }
    }
    
    
    private void determineMatingDetails(){
        MouseEntity dam1Entity = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(dam1.getMouse_key()))));
        MouseEntity sireEntity = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(sire.getMouse_key()))));
        MouseEntity dam2Entity = null;
        if(dam2!= null){
            dam2Entity = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(dam2.getMouse_key()))));
        }
        //first try to get owner from dam1
        for(OwnerEntity owner : guestOwnerLst){
            if(owner.getOwner().equalsIgnoreCase(dam1.getOwner())){
                matingOwner = owner;
            }
        }
        StrainEntity dam1Strain = dam1Entity.getStrainKey();
        StrainEntity sireStrain = sireEntity.getStrainKey();
        StrainEntity dam2Strain = null;
        if(dam2Entity != null){
            dam2Strain = dam2Entity.getStrainKey();
        }   
        if(litterStrain == null){
            if(usePreviousMatingStrain(dam1Strain, dam2Strain, sireStrain)){
                litterStrain = lastLitterStrain;
            }
            //approved mating strain only exists if dam2 is not selected or dam2strain == dam1strain
            else if(dam2Strain == null || dam2Strain.equals(dam1Strain)){
                //try to find approved matings strain using dam1 and sire strain key
                if(dam1Strain.equals(sireStrain)){
                    litterStrain = dam1Strain;
                }
                else{
                    //check for approved mating strains
                    ArrayList<Integer> approvedStrainKeys = levelDAO.getApprovedMatingMatingStrains(dam1.getStrain_key(), sire.getStrain_key());
                    if(!approvedStrainKeys.isEmpty()){
                        litterStrain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(approvedStrainKeys.get(0)));
                    }
                }
            }
        }
        //change diet to dam1 if mating diet is null
        if(matingDiet == null && !dam1.getDiet().equals("")){
            //get diet from dam1 and set mating diet to it
            for(CvDietEntity diet : listSupportDTO.getCvDiet()){
                if(diet.getDiet().equalsIgnoreCase(dam1.getDiet())){
                    matingDiet = diet;
                }
            }
        }
    }
    
    private boolean usePreviousMatingStrain(StrainEntity dam1Strain, StrainEntity dam2Strain, StrainEntity sireStrain){
        boolean sameStrains = true;
        if(dam2Strain != null && lastDam2Strain == null){
            sameStrains = false;
        }
        else if(dam2Strain == null && lastDam2Strain != null){
            sameStrains = false;
        }
        else if(dam2Strain != null && lastDam2Strain != null){
            if(!dam2Strain.equals(lastDam2Strain)){
                sameStrains = false;
            }
        }
        if(!dam1Strain.equals(lastDam1Strain)){
           sameStrains = false;
        }
        if(!sireStrain.equals(lastSireStrain)){
            sameStrains = false;
        }
        return sameStrains;
    }
    
    public void updatePenAndDiet(MouseEntity mEntity, ContainerEntity pen) throws Exception {
        //MouseEntity mEntity = this.mouseFunctions.findMouse(id);

        if (mEntity != null) {
            mEntity.setPenKey(pen);
            mEntity.setBreedingStatus("B");

            // if there is a new diet, then update it
            if (this.matingDiet != null && matingDiet.getDietKey() != null && matingDiet.getDietKey() > 0) {
                String diet = matingDiet.getDiet();
                mEntity.setDiet(diet);
            }
            mEntity.setIsDirty(true);

            // set the version
            int ver = mEntity.getVersion();
            System.out.println("previous version " + ver);
            mEntity.setVersion(ver + 1);

            new SaveAppService().saveMouse(mEntity);

            //new SaveAppService().baseEdit(mEntity);
            this.getLogger().logInfo(this.formatLogMessage("save", "Mouse  "
                    + mEntity.getId() + " has been updated with new pen "
                    + pen.getContainerID()));
        }
    }
    
    
    public void removeDam1(){
        dam1ID = "";
        dam1 = null;
    }
    
    public void removeDam2(){
        dam2ID = "";
        dam2 = null;
    }
    
    public void removeSire(){
        sireID = "";
        sire = null;
    }
    
    public void cancelMating(ActionEvent event){
        String cagePos = (String) event.getComponent().getAttributes().get("paramCagePostion");
        System.out.println(cagePos);
        LevelContainerDTO dto = containers.get(Integer.parseInt(cagePos));
        dto.setStyle("empty slot");
        dto.getCage().setContainerID("empty");
        dto.getCage().setContainerName("empty");
        dto.setType("");
    }
    
    public void dropMatingMember(){
        String draggedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedPosition");
        System.out.println("Dragged: " + draggedKey);
        LevelContainerDTO dragged = containers.get(Integer.parseInt(draggedKey));
        
        //ownership check is in this statement so no worries.
        ArrayList<MouseDTO> miceInCage = new MouseDAO().getLivingMiceInCageByOwner(dragged.getCage().getContainer_key(), this.getCurrentUserColonyManageWorkgroups());
        boolean flag = false;
        //do validation here, such as if multiple genders in cage etc.
        if(miceInCage.size() < 1){
            flag = true;
            this.addToMessageQueue("No mice found in this cage that you own, please select another cage.", FacesMessage.SEVERITY_ERROR);
        }
        if(!(dragged.getSex().equalsIgnoreCase("M") || dragged.getSex().equalsIgnoreCase("F"))){
            flag = true;
            this.addToMessageQueue("Cage must contain either all male or all female mice.", FacesMessage.SEVERITY_ERROR);
        }
        if(dragged.getType().equals("mating")){
            flag = true;
            this.addToMessageQueue("Cage must not be an active mating cage.", FacesMessage.SEVERITY_ERROR);
        }
        if(dam1 != null && dragged.getSex().equalsIgnoreCase("F") && function.equals("addMating")){
            flag = true;
            this.addToMessageQueue("Mating already contains required dam for paired mating.", FacesMessage.SEVERITY_ERROR);
        }
        if(dam1 != null && dam2 != null && dragged.getSex().equalsIgnoreCase("F") && function.equals("addTrioMating")){
            flag = true;
            this.addToMessageQueue("Mating already contains all required dams for trio mating.", FacesMessage.SEVERITY_ERROR);
        }
        if(sire != null && dragged.getSex().equalsIgnoreCase("M")){
            flag = true;
            this.addToMessageQueue("Mating already contains required sire for trio mating.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            for(MouseDTO movedMouse : miceInCage){
                flag = false;
                if(movedMouse.getSex().equalsIgnoreCase("F")){
                    if(levelDAO.mouseInActiveMating(movedMouse.getMouse_key())){
                        flag = true;
                        this.addToMessageQueue("Mouse " + movedMouse.getID() + " is already in an active mating and could not be added to a new one.", FacesMessage.SEVERITY_WARN);
                    }
                    if(!flag){
                        if(dam1 != null && dam2 != null){
                            break;
                        }
                        if(dam1 == null){
                            dam1 = movedMouse;
                            if(dam1.getID().length() > 8){
                                dam1ID = (dam1.getID().substring(0, 3) + "..." + dam1.getID().substring(dam1.getID().length() - 3, dam1.getID().length()));
                            }
                            else{
                                dam1ID = dam1.getID();
                            }
                        }
                        else if(dam2 == null && function.equals("addTrioMating")){
                            dam2 = movedMouse;
                            if(dam2.getID().length() > 8){
                                dam2ID = (dam2.getID().substring(0, 3) + "..." + dam2.getID().substring(dam2.getID().length() - 3, dam2.getID().length()));
                            }
                            else{
                                dam2ID = dam2.getID();
                            }
                        }
                    }
                }
                else{
                    if(sire != null){
                        //just use the first sire you find, if multiple male mice in cage just use the first one.
                        break;
                    }
                    sire = movedMouse;
                    if(sire.getID().length() > 8){
                        sireID = (sire.getID().substring(0, 3) + "..." + sire.getID().substring(sire.getID().length() - 3, sire.getID().length()));
                    }
                    else{
                        sireID = sire.getID();
                    }
                }
            }
            if(sire != null && dam1 != null){
                //try to fill in as much info for the mating here as you can.
                determineMatingDetails();
            }
        }
    }
        
    public void matingPartnerDrop(){
        String draggedID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("draggedMouse");
        String droppedPosition = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("droppedPosition");

        System.out.println("Dragged: " + draggedID);
        System.out.println("Dropped on: " + droppedPosition); 
        
        //get the mouse info
        ArrayList<MouseDTO> mice = (ArrayList<MouseDTO>) this.mouseDataModel.getWrappedData();
        MouseDTO movedMouse = null;
        for(MouseDTO mouse : mice){
            if(mouse.getID().equals(draggedID)){
                movedMouse = mouse;
            }
        }
        
        //validate
        boolean flag = false;
        if(dam1 != null && dam1.equals(movedMouse)){
            flag = true;
            this.addToMessageQueue("Mouse is already in this mating, please select a different mouse.", FacesMessage.SEVERITY_ERROR);
        }
        if(dam2 != null && dam2.equals(movedMouse)){
            flag = true;
            this.addToMessageQueue("Mouse is already in this mating, please select a different mouse.", FacesMessage.SEVERITY_ERROR);
        }
        if(sire != null && sire.equals(movedMouse)){
            flag = true;
            this.addToMessageQueue("Mouse is already in this mating, please select a different mouse.", FacesMessage.SEVERITY_ERROR);
        }
        if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), movedMouse.getOwner())){
            flag = true;
            this.addToMessageQueue("You do not have permission to use this mouse in a mating.", FacesMessage.SEVERITY_ERROR);            
        }
        
        if(!flag){
            if(movedMouse.getSex().equalsIgnoreCase("F")){
                if(levelDAO.mouseInActiveMating(movedMouse.getMouse_key())){
                    flag = true;
                    this.addToMessageQueue("Mouse is already in an active mating and cannot be added to another until it's current mating is retired.", FacesMessage.SEVERITY_ERROR);
                }
                if(dam1 != null && dam2 != null){
                    flag = true;
                    this.addToMessageQueue("Both dam1 and dam2 are already in this mating, to select another dam please remove a dam first.", FacesMessage.SEVERITY_ERROR);
                }
                if(!flag){
                    if(dam1 == null){
                        dam1 = movedMouse;
                        if(dam1.getID().length() > 8){
                            dam1ID = (dam1.getID().substring(0, 3) + "..." + dam1.getID().substring(dam1.getID().length() - 3, dam1.getID().length()));
                        }
                        else{
                            dam1ID = dam1.getID();
                        }
                    }
                    else{
                        dam2 = movedMouse;
                        if(dam2.getID().length() > 8){
                            dam2ID = (dam2.getID().substring(0, 3) + "..." + dam2.getID().substring(dam2.getID().length() - 3, dam2.getID().length()));
                        }
                        else{
                            dam2ID = dam2.getID();
                        }
                    }
                }
            }
            else{
                if(sire != null){
                    flag = true;
                    this.addToMessageQueue("Sire is already selected for this mating, to select a different sire please remove the current sire.", FacesMessage.SEVERITY_ERROR);
                }
                if(!flag){
                    sire = movedMouse;
                    if(sire.getID().length() > 8){
                        sireID = (sire.getID().substring(0, 3) + "..." + sire.getID().substring(sire.getID().length() - 3, sire.getID().length()));
                    }
                    else{
                        sireID = sire.getID();
                    }
                }
            }
            if(sire != null && dam1 != null && !flag){
                //try to fill in as much info for the mating here as you can.
                determineMatingDetails();
            }
        }
    }
    
    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the containers
     */
    public LinkedList<LevelContainerDTO> getContainers() {
        return containers;
    }

    /**
     * @param containers the containers to set
     */
    public void setContainers(LinkedList<LevelContainerDTO> containers) {
        this.containers = containers;
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    /**
     * @return the pickListContainers
     */
    public ArrayList<String> getPickListContainers() {
        return pickListContainers;
    }

    /**
     * @param pickListContainers the pickListContainers to set
     */
    public void setPickListContainers(ArrayList<String> pickListContainers) {
        this.pickListContainers = pickListContainers;
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
     * @return the cageFilterStrain
     */
    public StrainEntity getCageFilterStrain() {
        return cageFilterStrain;
    }

    /**
     * @param cageFilterStrain the cageFilterStrain to set
     */
    public void setCageFilterStrain(StrainEntity cageFilterStrain) {
        this.cageFilterStrain = cageFilterStrain;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions the instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return the strainName
     */
    public String getStrainName() {
        return strainName;
    }

    /**
     * @param strainName the strainName to set
     */
    public void setStrainName(String strainName) {
        this.strainName = strainName;
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
     * @return the gridPage
     */
    public Integer getGridPage() {
        return gridPage;
    }

    /**
     * @param gridPage the gridPage to set
     */
    public void setGridPage(Integer gridPage) {
        this.gridPage = gridPage;
    }

    /**
     * @return the lifeStatus
     */
    public LifeStatusEntity getLifeStatus() {
        return lifeStatus;
    }

    /**
     * @param lifeStatus the lifeStatus to set
     */
    public void setLifeStatus(LifeStatusEntity lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * @return the containerStatus
     */
    public CvContainerStatusEntity getContainerStatus() {
        return containerStatus;
    }

    /**
     * @param containerStatus the containerStatus to set
     */
    public void setContainerStatus(CvContainerStatusEntity containerStatus) {
        this.containerStatus = containerStatus;
    }

    /**
     * @return the statusChangeContainers
     */
    public ArrayList<LevelContainerDTO> getStatusChangeContainers() {
        return statusChangeContainers;
    }

    /**
     * @param statusChangeContainers the statusChangeContainers to set
     */
    public void setStatusChangeContainers(ArrayList<LevelContainerDTO> statusChangeContainers) {
        this.statusChangeContainers = statusChangeContainers;
    }

    /**
     * @return the removedCullCage
     */
    public String getRemovedCullCage() {
        return removedCullCage;
    }

    /**
     * @param removedCullCage the removedCullCage to set
     */
    public void setRemovedCullCage(String removedCullCage) {
        this.removedCullCage = removedCullCage;
    }

    /**
     * @return the addCageLabel
     */
    public String getAddCageLabel() {
        return addCageLabel;
    }

    /**
     * @param addCageLabel the addCageLabel to set
     */
    public void setAddCageLabel(String addCageLabel) {
        this.addCageLabel = addCageLabel;
    }

    /**
     * @return the selectedContainerID
     */
    public String getSelectedContainerID() {
        return selectedContainerID;
    }

    /**
     * @param selectedContainerID the selectedContainerID to set
     */
    public void setSelectedContainerID(String selectedContainerID) {
        this.selectedContainerID = selectedContainerID;
    }

    /**
     * @return the matingCardKey
     */
    public String getMatingCardKey() {
        return matingCardKey;
    }

    /**
     * @param matingCardKey the matingCardKey to set
     */
    public void setMatingCardKey(String matingCardKey) {
        this.matingCardKey = matingCardKey;
    }

    /**
     * @return the detailCardKey
     */
    public String getDetailCardKey() {
        return detailCardKey;
    }

    /**
     * @param detailCardKey the detailCardKey to set
     */
    public void setDetailCardKey(String detailCardKey) {
        this.detailCardKey = detailCardKey;
    }

    /**
     * @return the containerNameView
     */
    public boolean isContainerNameView() {
        return containerNameView;
    }

    /**
     * @param containerNameView the containerNameView to set
     */
    public void setContainerNameView(boolean containerNameView) {
        this.containerNameView = containerNameView;
    }

    /**
     * @return the litterStrain
     */
    public StrainEntity getLitterStrain() {
        return litterStrain;
    }

    /**
     * @param litterStrain the litterStrain to set
     */
    public void setLitterStrain(StrainEntity litterStrain) {
        this.litterStrain = litterStrain;
    }

    /**
     * @return the litterGeneration
     */
    public CvGenerationEntity getLitterGeneration() {
        return litterGeneration;
    }

    /**
     * @param litterGeneration the litterGeneration to set
     */
    public void setLitterGeneration(CvGenerationEntity litterGeneration) {
        this.litterGeneration = litterGeneration;
    }

    /**
     * @return the matingDiet
     */
    public CvDietEntity getMatingDiet() {
        return matingDiet;
    }

    /**
     * @param matingDiet the matingDiet to set
     */
    public void setMatingDiet(CvDietEntity matingDiet) {
        this.matingDiet = matingDiet;
    }

    /**
     * @return the matingDate
     */
    public Date getMatingDate() {
        return matingDate;
    }

    /**
     * @param matingDate the matingDate to set
     */
    public void setMatingDate(Date matingDate) {
        this.matingDate = matingDate;
    }

    /**
     * @return the weanTime
     */
    public String getWeanTime() {
        return weanTime;
    }

    /**
     * @param weanTime the weanTime to set
     */
    public void setWeanTime(String weanTime) {
        this.weanTime = weanTime;
    }

    /**
     * @return the needsGenotyping
     */
    public boolean isNeedsGenotyping() {
        return needsGenotyping;
    }

    /**
     * @param needsGenotyping the needsGenotyping to set
     */
    public void setNeedsGenotyping(boolean needsGenotyping) {
        this.needsGenotyping = needsGenotyping;
    }

    /**
     * @return the matingOwner
     */
    public OwnerEntity getMatingOwner() {
        return matingOwner;
    }

    /**
     * @param matingOwner the matingOwner to set
     */
    public void setMatingOwner(OwnerEntity matingOwner) {
        this.matingOwner = matingOwner;
    }

    /**
     * @return the weanNote
     */
    public String getWeanNote() {
        return weanNote;
    }

    /**
     * @param weanNote the weanNote to set
     */
    public void setWeanNote(String weanNote) {
        this.weanNote = weanNote;
    }

    /**
     * @return the matingComment
     */
    public String getMatingComment() {
        return matingComment;
    }

    /**
     * @param matingComment the matingComment to set
     */
    public void setMatingComment(String matingComment) {
        this.matingComment = matingComment;
    }

    /**
     * @return the dam1ID
     */
    public String getDam1ID() {
        return dam1ID;
    }

    /**
     * @param dam1ID the dam1ID to set
     */
    public void setDam1ID(String dam1ID) {
        this.dam1ID = dam1ID;
    }

    /**
     * @return the dam2ID
     */
    public String getDam2ID() {
        return dam2ID;
    }

    /**
     * @param dam2ID the dam2ID to set
     */
    public void setDam2ID(String dam2ID) {
        this.dam2ID = dam2ID;
    }

    /**
     * @return the sireID
     */
    public String getSireID() {
        return sireID;
    }

    /**
     * @param sireID the sireID to set
     */
    public void setSireID(String sireID) {
        this.sireID = sireID;
    }

    /**
     * @return the dam1
     */
    public MouseDTO getDam1() {
        return dam1;
    }

    /**
     * @param dam1 the dam1 to set
     */
    public void setDam1(MouseDTO dam1) {
        this.dam1 = dam1;
    }

    /**
     * @return the dam2
     */
    public MouseDTO getDam2() {
        return dam2;
    }

    /**
     * @param dam2 the dam2 to set
     */
    public void setDam2(MouseDTO dam2) {
        this.dam2 = dam2;
    }

    /**
     * @return the sire
     */
    public MouseDTO getSire() {
        return sire;
    }

    /**
     * @param sire the sire to set
     */
    public void setSire(MouseDTO sire) {
        this.sire = sire;
    }

    /**
     * @return the cageName
     */
    public String getCageName() {
        return cageName;
    }

    /**
     * @param cageName the cageName to set
     */
    public void setCageName(String cageName) {
        this.cageName = cageName;
    }

    /**
     * @return the cageComment
     */
    public String getCageComment() {
        return cageComment;
    }

    /**
     * @param cageComment the cageComment to set
     */
    public void setCageComment(String cageComment) {
        this.cageComment = cageComment;
    }

    /**
     * @return the pups
     */
    public ArrayList<VivariaPupDTO> getPups() {
        return pups;
    }

    /**
     * @param pups the pups to set
     */
    public void setPups(ArrayList<VivariaPupDTO> pups) {
        this.pups = pups;
    }

    /**
     * @return the totalBorn
     */
    public String getTotalBorn() {
        return totalBorn;
    }

    /**
     * @param totalBorn the totalBorn to set
     */
    public void setTotalBorn(String totalBorn) {
        this.totalBorn = totalBorn;
    }

    /**
     * @return the dateBorn
     */
    public Date getDateBorn() {
        return dateBorn;
    }

    /**
     * @param dateBorn the dateBorn to set
     */
    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    /**
     * @return the calculateDates
     */
    public boolean isCalculateDates() {
        return calculateDates;
    }

    /**
     * @param calculateDates the calculateDates to set
     */
    public void setCalculateDates(boolean calculateDates) {
        this.calculateDates = calculateDates;
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
     * @return the litterStatus
     */
    public String getLitterStatus() {
        return litterStatus;
    }

    /**
     * @param litterStatus the litterStatus to set
     */
    public void setLitterStatus(String litterStatus) {
        this.litterStatus = litterStatus;
    }

    /**
     * @return the litterComments
     */
    public String getLitterComments() {
        return litterComments;
    }

    /**
     * @param litterComments the litterComments to set
     */
    public void setLitterComments(String litterComments) {
        this.litterComments = litterComments;
    }

    /**
     * @return the baseMouseID
     */
    public String getBaseMouseID() {
        return baseMouseID;
    }

    /**
     * @param baseMouseID the baseMouseID to set
     */
    public void setBaseMouseID(String baseMouseID) {
        this.baseMouseID = baseMouseID;
    }

    /**
     * @return the protocol
     */
    public CvMouseProtocolEntity getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(CvMouseProtocolEntity protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the litterMating
     */
    public MatingEntity getLitterMating() {
        return litterMating;
    }

    /**
     * @param litterMating the litterMating to set
     */
    public void setLitterMating(MatingEntity litterMating) {
        this.litterMating = litterMating;
    }

    /**
     * @return the origin
     */
    public CvMouseOriginEntity getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(CvMouseOriginEntity origin) {
        this.origin = origin;
    }

    /**
     * @return the numMale
     */
    public String getNumMale() {
        return numMale;
    }

    /**
     * @param numMale the numMale to set
     */
    public void setNumMale(String numMale) {
        this.numMale = numMale;
    }

    /**
     * @return the numFemale
     */
    public String getNumFemale() {
        return numFemale;
    }

    /**
     * @param numFemale the numFemale to set
     */
    public void setNumFemale(String numFemale) {
        this.numFemale = numFemale;
    }

    /**
     * @return the nextLitterID
     */
    public String getNextLitterID() {
        return nextLitterID;
    }

    /**
     * @param nextLitterID the nextLitterID to set
     */
    public void setNextLitterID(String nextLitterID) {
        this.nextLitterID = nextLitterID;
    }

    /**
     * @return the selectedLevel
     */
    public LevelDTO getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * @param selectedLevel the selectedLevel to set
     */
    public void setSelectedLevel(LevelDTO selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    /**
     * @return the cageID
     */
    public String getCageID() {
        return cageID;
    }

    /**
     * @param cageID the cageID to set
     */
    public void setCageID(String cageID) {
        this.cageID = cageID;
    }

    /**
     * @return the currentRoom
     */
    public AdminRoomDTO getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @param currentRoom the currentRoom to set
     */
    public void setCurrentRoom(AdminRoomDTO currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * @return the actionDate
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * @param actionDate the actionDate to set
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    /**
     * @return the addingCage
     */
    public boolean isAddingCage() {
        return addingCage;
    }

    /**
     * @param addingCage the addingCage to set
     */
    public void setAddingCage(boolean addingCage) {
        this.addingCage = addingCage;
    }

    /**
     * @return the expanded
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * @param expanded the expanded to set
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * @return the numberBornDead
     */
    public String getNumberBornDead() {
        return numberBornDead;
    }

    /**
     * @param numberBornDead the numberBornDead to set
     */
    public void setNumberBornDead(String numberBornDead) {
        this.numberBornDead = numberBornDead;
    }

    /**
     * @return the numberCulledAtWean
     */
    public String getNumberCulledAtWean() {
        return numberCulledAtWean;
    }

    /**
     * @param numberCulledAtWean the numberCulledAtWean to set
     */
    public void setNumberCulledAtWean(String numberCulledAtWean) {
        this.numberCulledAtWean = numberCulledAtWean;
    }

    /**
     * @return the numberMissingAtWean
     */
    public String getNumberMissingAtWean() {
        return numberMissingAtWean;
    }

    /**
     * @param numberMissingAtWean the numberMissingAtWean to set
     */
    public void setNumberMissingAtWean(String numberMissingAtWean) {
        this.numberMissingAtWean = numberMissingAtWean;
    }
}
