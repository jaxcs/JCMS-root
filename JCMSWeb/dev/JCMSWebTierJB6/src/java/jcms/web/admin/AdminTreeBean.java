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

package jcms.web.admin;

import java.util.ArrayList;
import javax.faces.model.SelectItem;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.richfaces.component.UIExtendedDataTable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import jcms.integrationtier.dto.AdminCenterDTO;
import jcms.integrationtier.dto.AdminUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupDTO;
import jcms.integrationtier.dto.AdminWorkgroupUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupUserFunctionalAreaDTO;
import jcms.integrationtier.dto.AdminFunctionalAreaDTO;
import jcms.integrationtier.dto.AdminPrivilegeDTO;
import jcms.integrationtier.dao.AdministrationDAO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.web.base.WTBaseBackingBean;
import java.security.MessageDigest;
import javax.servlet.http.HttpSession;
import org.jboss.util.Base64;
import jcms.integrationtier.dao.UserPreferencesDAO;

/**
 *
 * @author mkamato
 */
@ManagedBean
@ViewScoped
public class AdminTreeBean extends WTBaseBackingBean{

    //used to keep track of the selected User's workgroup key.
    private String parentWorkgroupKey = "";
    private String parentCenterKey = "";
    private String role = "";
    
    //values for privilege dropdown when user is selected or adding a new user
    private ArrayList<SelectItem> privs = new ArrayList<SelectItem>();
    
    //communicates privileges of selected user to web view and communicates user specified values 
    //for privileges on add new or edit existing user
    private String adminPriv = "0";
    private String queryPriv = "2";
    private String reportPriv = "2";
    private boolean administrator = false;
    
    
    //all the administrative info for workgroups this user belongs to.
    private ArrayList<WorkgroupEntity> myWorkgroups;    
    private ArrayList<AdminCenterDTO> centers;
    private ArrayList<AdminUserDTO> users;
    private ArrayList<AdminWorkgroupDTO> workgroups;
    private ArrayList<AdminWorkgroupUserDTO> workgroupUsers;
    private ArrayList<AdminWorkgroupUserFunctionalAreaDTO> workgroupUserFunctionalAreas;
    private ArrayList<AdminFunctionalAreaDTO> functionalAreas;
    private ArrayList<AdminPrivilegeDTO> privileges;
    private AdministrationDAO adminDAO = new AdministrationDAO();
    
    //password holders, verify against each other, used for add or edit user
    private String verifyPassword = "";
    private String password = "";
    
    //for making dates MySQL compatible
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.getDefault());
    //current user logged on, use username for inserts/updates
    private UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
    
    //for selected node stuff...
    private boolean selectedWorkgroup = false;
    private boolean selectedUser = false;
    private boolean selectedCenter = false;
    private AdminCenterDTO selectedCenterItem = new AdminCenterDTO();
    private AdminWorkgroupDTO selectedWorkgroupItem = new AdminWorkgroupDTO();
    private AdminUserDTO editUserItem = new AdminUserDTO();
    
    //for displaying on view
    private AdminTreeNode root = new AdminTreeNode();
    
    //for determining whether items are rendered on page
    private boolean addingExistingUser = false;
    private boolean changingPassword = false;
    private boolean addingCenter = false;
    private boolean addingUser = false;
    private boolean addingWorkgroup = false;
    
    //boolean active holders (for displaying boolean info from DB)
    private boolean isActiveUser = false;
    private boolean isMasterAdministrator = false;
    private boolean isActiveWorkgroup = false;
    private boolean isActiveCenter = false;
      
    //for keeping track of datatable selection/sorting
    private ArrayList<AdminUserDTO> usersSelection = new ArrayList<AdminUserDTO>();
    private ArrayList<Object> selection = new ArrayList<Object>();
    
    //add new DTOs
    private AdminCenterDTO addCenterDTO = new AdminCenterDTO();
    private AdminWorkgroupDTO addWorkgroupDTO = new AdminWorkgroupDTO();
    private AdminUserDTO addUserDTO = new AdminUserDTO();
    
    //warn before user change
    private boolean warnBeforeRemove = false;
    private boolean warnBeforeUpdate = false;
    
    //To create a new user's preferences
    private UserPreferencesDAO userPrefDAO = new UserPreferencesDAO();
    
    //constructor
    public AdminTreeBean(){
        myWorkgroups = ((ArrayList<WorkgroupEntity>) getSessionParameter("adminWorkgroupEntityLst"));
        users = adminDAO.getUsers();
   //     workgroups = adminDAO.getWorkgroups(myWorkgroups);
        workgroups = adminDAO.getAdminWorkgroups(new Integer(currentUser.getUserkey()).toString());
        currentUser=(UserEntity) getSessionParameter("userEntity");
        if(currentUser.getIsMasterAdministrator() == 0){
            centers = adminDAO.getCenters(workgroups);
        }
        else{
            centers = adminDAO.getCenters();
        }
        workgroupUsers = adminDAO.getWorkgroupUsers();
        workgroupUserFunctionalAreas = adminDAO.getWorkgroupUserFunctionalAreas();
        privileges = adminDAO.getPrivileges();
        functionalAreas = adminDAO.getFunctionalAreas();
        privs.add(new SelectItem("0", "none"));
        for(AdminPrivilegeDTO privilege : privileges){
            if(privilege.getPrivilege().equalsIgnoreCase("Write")){
                privs.add(new SelectItem(privilege.getPrivilegeKey(), privilege.getPrivilege()));
            }
        }
        buildTree();
    }
    
    //refreshes fields after a change is made
    public void refresh(){
        myWorkgroups = ((ArrayList<WorkgroupEntity>) getSessionParameter("adminWorkgroupEntityLst"));
        setUsers(adminDAO.getUsers());
        addingExistingUser = false;
        changingPassword = false;
        addingCenter = false;
        addingUser = false;
        addingWorkgroup = false;
        workgroups = adminDAO.getAdminWorkgroups(new Integer(currentUser.getUserkey()).toString());
        //if master administrator get all the centers, if not, get centers that individual is a member of.
        if(getCurrentUser().getIsMasterAdministrator() == 0){
            centers = adminDAO.getCenters(workgroups);
        }
        else{
            centers = adminDAO.getCenters();
        }        
        workgroupUsers = adminDAO.getWorkgroupUsers();
        workgroupUserFunctionalAreas = adminDAO.getWorkgroupUserFunctionalAreas();
        privileges = adminDAO.getPrivileges();
        functionalAreas = adminDAO.getFunctionalAreas();
        buildTree();        
    }
    
    //builds the tree
    private void buildTree(){
        int count = 1;
        //add centers to root
        root = new AdminTreeNode();
        for(AdminCenterDTO center : centers){
            AdminTreeNode centerNode = new AdminTreeNode(center);
            root.addChild(new Integer(count).toString(), centerNode);
            count++;
            //add workgroups to centers
            for(AdminWorkgroupDTO workgroup : workgroups){
                if(workgroup.getCenter_key().equals(center.getCenterkey())){
                    AdminTreeNode workgroupNode = new AdminTreeNode(workgroup);
                    workgroupNode.setParentKey(center.getCenterkey());
                    centerNode.addChild(new Integer(count).toString(), workgroupNode);
                    count++;
                    //add users to workgroups
                    for(AdminUserDTO user : getUsers()){
                        for(AdminWorkgroupUserDTO workgroupUser : workgroupUsers){
                            if(workgroupUser.getUser_key().equals(user.getUserKey()) 
                                    && workgroupUser.getWorkgroup_key().equals(workgroup.getWorkgroup_key())){
                                AdminTreeNode userNode = new AdminTreeNode(user);
                                //add permissions to users
                                for(AdminWorkgroupUserFunctionalAreaDTO workgroupUserFunctionalArea : workgroupUserFunctionalAreas){
                                    if(workgroupUserFunctionalArea.getWorkgroupUser_key().equals(workgroupUser.getWorkgroupUser_key())){
                                        userNode.getWUFADTOs().add(workgroupUserFunctionalArea);
                                    }
                                }
                                AdminWorkgroupDTO workgroupData = (AdminWorkgroupDTO) workgroupNode.getData();
                                userNode.setParentKey(workgroupData.getWorkgroup_key());
                                workgroupNode.addChild(new Integer(count).toString(), userNode);
                                count++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Count is: " + count);
    }
    
    //following methods are for displaying and hiding panels for creating and adding workgroups/centers/users
    public void addCenterDisplay(){
        setAddingUser(false);
        setAddingExistingUser(false);
        setAddingWorkgroup(false);
        setSelectedUser(false);
        setSelectedWorkgroup(false);
        setSelectedCenter(false);
        setAddingCenter(true);
    }
    
    public void addCenterHide(){
        setAddingCenter(false);
    }

    public void addWorkgroupDisplay(){
        setAddingWorkgroup(true);
    }
    
    public void addWorkgroupHide(){
        setAddingWorkgroup(false);
    }
    
    public void addUserDisplay(){
        adminPriv = "0";
        queryPriv = "2";
        reportPriv = "2";
        administrator = false;
        setAddingUser(true);
    }
    
    public void addExistingUserDisplay(){
        addingUser = false;
        adminPriv = "0";
        queryPriv = "2";
        reportPriv = "2";
        administrator = false;
        addingExistingUser = true;
    }
    
    public void addExistingUserHide(){
        addingExistingUser = false;
    }
    
    
    /*
     * Following methods will add/update fields to 
     * user, workgroup, center, WorkgroupUser, and
     * WorkgroupUserFunctionalArea tables. 
     * Errors are thrown from integration tier and 
     * caught here. 
     */    
    //adds a new center
    public void addNewCenter(){
        try{
            boolean flag = false;
            
            //all center names must be unique
            for(AdminCenterDTO dto : adminDAO.getCenters()){
                if(dto.getCenter().equals(addCenterDTO.getCenter())){
                    flag = true;
                }
            }
            if(addCenterDTO.getCenter().equals("")){
                this.addToMessageQueue("Center name cannot be blank.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Center name cannot be blank."));
                flag = true;               
            }
            if(!flag){
            
                String theDate = formatter.format(new Date());

                addCenterDTO.setCreatedBy(getCurrentUser().getUserName());
                addCenterDTO.setModifiedBy(getCurrentUser().getUserName());
                addCenterDTO.setDateCreated(theDate);
                addCenterDTO.setDateModified(theDate);
                addCenterDTO.setIsActive("1");
                addCenterDTO.setVersion("1");

                adminDAO.insertCenter(addCenterDTO);
                refresh();
                this.addToMessageQueue("Center: " + addCenterDTO.getCenter() + " successfully added.", FacesMessage.SEVERITY_INFO);
            }
            if(flag){
                this.addToMessageQueue("There already exists a center with that name, please "
                            + "select a unique center name.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "There already exists a center with "
                            + "that name, please select a unique center name."));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        addingCenter=false;
        addCenterDTO = new AdminCenterDTO();
    }
    
    //add a new workgroup
    public void addNewWorkgroup(){
        try{
            boolean flag = false;
            
            for(AdminWorkgroupDTO dto : adminDAO.getWorkgroups()){
                if(addWorkgroupDTO.getWorkgroupName().equalsIgnoreCase(dto.getWorkgroupName())){
                    this.addToMessageQueue("There already exists a workgroup with that name in the selected Center, please "
                            + "select another workgroup name.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "There already exists a workgroup with "
                            + "that name in the selected Center, please select another workgroup name."));
                    flag = true;
                }
            }
            for(String ownerName : adminDAO.getOwnerNames()){
                if(addWorkgroupDTO.getWorkgroupName().equalsIgnoreCase(ownerName)){
                    this.addToMessageQueue("There already exists an owner with that name. Please "
                            + "select another workgroup name.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "There already exists an owner with "
                            + "that name. Please select another workgroup name."));
                    flag = true;
                }
            }
            if(addWorkgroupDTO.getWorkgroupName().equals("")){
                this.addToMessageQueue("Workgroup name cannot be blank.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Workgroup name cannot be blank."));
                flag = true;
            }
            if(addWorkgroupDTO.getWorkgroupName().length() > 8){
                this.addToMessageQueue("Workgroup name must be 8 characters or fewer.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Workgroup name must be 8 characters or fewer."));
                flag = true;
            }
            if(!flag){
                //add Workgroup
                String theDate = formatter.format(new Date());
                addWorkgroupDTO.setDateCreated(theDate);
                addWorkgroupDTO.setDateModified(theDate);
                addWorkgroupDTO.setCenter_key(selectedCenterItem.getCenterkey());
                addWorkgroupDTO.setModifiedBy(getCurrentUser().getUserName());
                addWorkgroupDTO.setCreatedBy(getCurrentUser().getUserName());
                addWorkgroupDTO.setIsActive("1");
                addWorkgroupDTO.setVersion("1");

                String workgroupKey = adminDAO.insertWorkgroup(addWorkgroupDTO);

                //add MTSAdmin to workgroup
                AdminWorkgroupUserDTO wuDTO = new AdminWorkgroupUserDTO();
                wuDTO.setCreatedBy(getCurrentUser().getUserName());
                wuDTO.setDateCreated(theDate);
                wuDTO.setDateModified(theDate);
                wuDTO.setModifiedBy(getCurrentUser().getUserName());
                wuDTO.setUser_key("1");
                wuDTO.setVersion("1");
                wuDTO.setWorkgroup_key(workgroupKey);

                //key value for mtsadmin-created group WorkgroupUserKey
                String mtsadminWorkgroupUserKey = adminDAO.insertWorkgroupUser(wuDTO);

                AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO(); 
                //these fields stay the same for all
                dto.setModifiedBy(getCurrentUser().getUserName());
                dto.setCreatedBy(getCurrentUser().getUserName());
                dto.setDateCreated(theDate);
                dto.setDateModified(theDate);
                dto.setVersion("1");

                dto.setWorkgroupUser_key(mtsadminWorkgroupUserKey);
                dto.setFunctionalArea_key("1");
                dto.setPrivilege_key("2");

                //add WorkgroupUserFunctionalArea row to table
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("2");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("3");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("4");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);

                if(!currentUser.getUserName().equals("mtsadmin")){
                    wuDTO.setUser_key(getCurrentUser().getUserkey().toString());
                    String creatorWorkgroupUserKey = adminDAO.insertWorkgroupUser(wuDTO);

                    dto.setWorkgroupUser_key(creatorWorkgroupUserKey);
                    dto.setFunctionalArea_key("1");
                    dto.setPrivilege_key("2");

                    //add WorkgroupUserFunctionalArea row to table
                    adminDAO.insertWorkgroupUserFunctionalArea(dto);
                    dto.setFunctionalArea_key("2");
                    adminDAO.insertWorkgroupUserFunctionalArea(dto);
                    dto.setFunctionalArea_key("3");
                    adminDAO.insertWorkgroupUserFunctionalArea(dto);
                    dto.setFunctionalArea_key("4");
                    adminDAO.insertWorkgroupUserFunctionalArea(dto);
                }

                //update session parameter 'workgroupEntityLst
                myWorkgroups.add(new WorkgroupEntity(new Integer(workgroupKey),addWorkgroupDTO.getWorkgroupName(),
                        new Short(addWorkgroupDTO.getIsActive()), addWorkgroupDTO.getCreatedBy(), new Date(),
                        addWorkgroupDTO.getModifiedBy(), new Date(), 1));
                putSessionParameter("adminWorkgroupEntityLst", myWorkgroups);
                
                //update Session parameter 'ownerEntityList'
                ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) getSessionParameter("adminOwnerEntityLst");
                owners.add(adminDAO.getOwnerEntityByName(addWorkgroupDTO.getWorkgroupName()));
                putSessionParameter("adminOwnerEntityLst", owners);
                
                refresh();
                this.addToMessageQueue("Workgroup: " + addWorkgroupDTO.getWorkgroupName() + " successfully added.", FacesMessage.SEVERITY_INFO);
            }
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation", "There was an error " + e));
        }
        addWorkgroupDTO = new AdminWorkgroupDTO();        
    }
    
    //creates a new user and adds him to selected workgroup
    public void addNewUser(){
        try{
            
            //validation...
            boolean flag = false;
            if(!verifyPassword.equals(password)){
                this.addToMessageQueue("Your passwords do not match, please retype your passwords", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", 
                        "Your passwords do not match, please retype your passwords"));
                verifyPassword="";
                password = "";
                flag = true;
            }
            for(AdminUserDTO dto : adminDAO.getUsers()){
                if(dto.getUserName().equals(addUserDTO.getUserName())){
                    this.addToMessageQueue("A user with that name already exists, please choose another username.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", 
                            "A user with that name already exists, please choose another username."));
                    flag = true;
                }
            }
            if(password.equals("")){
                this.addToMessageQueue("Password is required, please provide a password.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", 
                            "Password is required, please provide a password."));
                flag = true;
            }
            if(addUserDTO.getUserName().equals("")){
                this.addToMessageQueue("Username is required, please provide a Username.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", 
                            "Username is required, please provide a Username."));
                flag = true;
            }
            if(role.equals("")){
                this.addToMessageQueue("Role is required, please provide a role.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "Role is required, please provide a role."));
                flag = true;
            }
            if(flag){ //there was a problem, don't add user
                return;
            }

            String theDate = formatter.format(new Date());
            
            //create user to insert in user table
            addUserDTO.setPassword_(encodePassword(password));
            addUserDTO.setCreatedBy(getCurrentUser().getUserName());
            addUserDTO.setDateCreated(theDate);
            addUserDTO.setDateModified(theDate);
            addUserDTO.setDefaultWorkgroupKey(selectedWorkgroupItem.getWorkgroup_key());
            addUserDTO.setIsActive("1");
            addUserDTO.setIsMasterAdministrator(isMasterAdministrator);
            addUserDTO.setModifiedBy(getCurrentUser().getUserName());
            addUserDTO.setVersion("1");
            //returns user key
            String userKey = adminDAO.insertUser(addUserDTO);
            this.addToMessageQueue("User " + addUserDTO.getUserName() + " successfully created.", FacesMessage.SEVERITY_INFO);
            
            //create workgroup user  to insert into workgroupuser table
            AdminWorkgroupUserDTO wuDTO = new AdminWorkgroupUserDTO();
            wuDTO.setCreatedBy(getCurrentUser().getUserName());
            wuDTO.setDateCreated(theDate);
            wuDTO.setDateModified(theDate);
            wuDTO.setModifiedBy(getCurrentUser().getUserName());
            wuDTO.setUser_key(userKey);
            wuDTO.setWorkgroup_key(selectedWorkgroupItem.getWorkgroup_key());
            wuDTO.setVersion("1");
            
            String workgroupUserKey = adminDAO.insertWorkgroupUser(wuDTO);
            
            //create AdminWorkgroupUserFunctionalArea
            AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO(); 
            //these fields stay the same for all
            dto.setModifiedBy(getCurrentUser().getUserName());
            dto.setCreatedBy(getCurrentUser().getUserName());
            dto.setDateCreated(theDate);
            dto.setDateModified(theDate);
            dto.setWorkgroupUser_key(workgroupUserKey);
            dto.setVersion("1");
            
            if(administrator){
                dto.setFunctionalArea_key("1");
                dto.setPrivilege_key("2");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
            }
            
            dto.setPrivilege_key("2");

            if(role.equals("administrator")){
                dto.setFunctionalArea_key("1");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("2");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("3");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("4");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
            }

            if(role.equals("colonyWorker")){
                dto.setFunctionalArea_key("2");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("3");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("4");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
            }

            if(role.equals("guest")){
                dto.setFunctionalArea_key("2");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);
                dto.setFunctionalArea_key("3");
                adminDAO.insertWorkgroupUserFunctionalArea(dto);                        
            }
            addingUser=false;

            

            this.addToMessageQueue("User " + addUserDTO.getUserName() + " successfully added to "
                    + selectedWorkgroupItem.getWorkgroupName(), FacesMessage.SEVERITY_INFO);
            password = "";
            verifyPassword = "";
            addUserDTO = new AdminUserDTO();
            
            administrator = false;
            refresh();
                        
            //Setup the user preferences for this new user
            if (userPrefDAO.insertUserPreferences(userKey) == 0) {
                //Something went wrong
                this.addToMessageQueue("User Preferences are not set up correctly.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Adding New User: ", "User has been added but User Preferences did not get set up correctly. "));
            }
            
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation", "There was an error " + e));
        }
    }
    
    public String editUser(){
        try{
            boolean flag = false;
            ArrayList<AdminWorkgroupUserDTO> WUS = adminDAO.getWorkgroupUsersByUser(editUserItem.getUserKey());
            boolean isGuest = false;
            boolean isNonGuest = false;
            if(!flag){
                String theDate = formatter.format(new Date());
                String user = getCurrentUser().getUserName();

                editUserItem.setDateModified(theDate);
                editUserItem.setModifiedBy(user);
                editUserItem.setVersion("1");
                editUserItem.setIsActive(isActiveUser);
                editUserItem.setIsMasterAdministrator(isMasterAdministrator);
                if(this.changingPassword){
                    if(!password.equals(verifyPassword)){
                        this.addToMessageQueue("Password and verify password must match, please re-enter your password", FacesMessage.SEVERITY_ERROR);
                        return "";
                    }
                    if(password.equals("")){
                        this.addToMessageQueue("Password cannot be blank, please enter a password.", FacesMessage.SEVERITY_ERROR);
                        return "";
                    }

                    editUserItem.setPassword_(encodePassword(password));
                }
                adminDAO.updateUser(editUserItem, changingPassword);

                String workgroupUserKey = "";
                for(AdminWorkgroupUserDTO dto : adminDAO.getWorkgroupUsersByUser(editUserItem.getUserKey())){
                    if(dto.getWorkgroup_key().equals(parentWorkgroupKey)){
                        workgroupUserKey = dto.getWorkgroupUser_key();
                    }
                }

                if(!workgroupUserKey.equals("")){
                    AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO();
                    dto.setCreatedBy(getCurrentUser().getUserName());
                    dto.setDateCreated(theDate);
                    dto.setDateModified(theDate);
                    dto.setModifiedBy(getCurrentUser().getUserName());
                    dto.setVersion("1");
                    dto.setWorkgroupUser_key(workgroupUserKey);
                    dto.setPrivilege_key("2");

                    String currentRole = this.getRoleByWorkgroupUserKey(workgroupUserKey);
                    //if the role was changed workgroupuserfunctionalareas must be added or deleted.
                    if(currentRole.equals("administrator")){
                        //going from administrator to colonyworker, remove admin privs
                        if(role.equals("colonyWorker")){
                            for(AdminWorkgroupUserFunctionalAreaDTO wufaDTO : adminDAO.getWorkgroupUserFunctionalAreasByWorkgroupUser(workgroupUserKey)){
                                if(wufaDTO.getFunctionalArea_key().equals("1")){
                                    adminDAO.deleteWorkgroupUserFunctionalArea(wufaDTO.getWorkgroupUserFunctionalArea_key());
                                }
                            }
                        }
                        //going from admin to guest, no more admin OR colonymanagement privs
                        else if(role.equals("guest")){
                            for(AdminWorkgroupUserFunctionalAreaDTO wufaDTO : adminDAO.getWorkgroupUserFunctionalAreasByWorkgroupUser(workgroupUserKey)){
                                if(wufaDTO.getFunctionalArea_key().equals("1")){
                                    adminDAO.deleteWorkgroupUserFunctionalArea(wufaDTO.getWorkgroupUserFunctionalArea_key());
                                }
                                if(wufaDTO.getFunctionalArea_key().equals("4")){
                                    adminDAO.deleteWorkgroupUserFunctionalArea(wufaDTO.getWorkgroupUserFunctionalArea_key());
                                }
                            }
                        }
                    }
                    if(currentRole.equals("colonyWorker")){
                        //going from colonyworker to administrator, add admin privs
                        if(role.equals("administrator")){
                            dto.setFunctionalArea_key("1");
                            adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        }
                        //going from colonyworker to guest, no more colonymanagement privs
                        else if(role.equals("guest")){
                            for(AdminWorkgroupUserFunctionalAreaDTO wufaDTO : adminDAO.getWorkgroupUserFunctionalAreasByWorkgroupUser(workgroupUserKey)){
                                if(wufaDTO.getFunctionalArea_key().equals("4")){
                                    adminDAO.deleteWorkgroupUserFunctionalArea(wufaDTO.getWorkgroupUserFunctionalArea_key());
                                }
                            }
                        }
                    }
                    if(currentRole.equals("guest")){
                        //going from guest to administrator, add admin privs AND colony management privs
                        if(role.equals("administrator")){
                            dto.setFunctionalArea_key("1");
                            adminDAO.insertWorkgroupUserFunctionalArea(dto);
                            dto.setFunctionalArea_key("4");
                            adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        }
                        //going from guest to colonyWorker, add colonymanagement privs
                        else if(role.equals("colonyWorker")){
                            dto.setFunctionalArea_key("4");
                            adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        }
                    }
                    if(warnBeforeUpdate){
                        return logoff();
                    }
                    refresh();
                    this.addToMessageQueue("User " + this.editUserItem.getUserName() + " successfully edited.", FacesMessage.SEVERITY_INFO);
                }
                else{
                    this.addToMessageQueue("No WorkgroupUser_key found, doesn't exist in that group. "
                            + "Made cosmetic changes to user (if any) but no permissions updates.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ", "No WorkgroupUser_key found, doesn't exist in that group. "
                            + "Made cosmetic changes to user (if any) but no permissions updates."));
                }
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Error: " + e));
        }
        return "";
    }
    
    private String getRoleByWorkgroupUserKey(String workgroupUserKey){
        String theRole = "guest";
        for(AdminWorkgroupUserFunctionalAreaDTO dto : adminDAO.getWorkgroupUserFunctionalAreasByWorkgroupUser(workgroupUserKey)){
            //user is administrator if administration is found
            if(dto.getFunctionalArea_key().equals("1")){
                return "administrator";
            }
            //if administrator is not found and colonymanagement is user is colony worker
            if(dto.getFunctionalArea_key().equals("4")){
                theRole = "colonyWorker";
            }
        }
        //if neither of the roles were found above the role never changed and remains 'guest'
        return theRole;
    }
         
    private String encodePassword(String password){
        String pw = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            byte byteData[] = md.digest(password.getBytes());
            pw = Base64.encodeBytes(byteData);
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "There was an error: " + e));
        }   
        return pw;
    }
    
    //adds user(s) that already exists to a workgroup
    public void addExistingUser(){
        if(!role.equals("")){
            String user = getCurrentUser().getUserName();
            String theDate = formatter.format(new Date());

            try{
                ArrayList<AdminUserDTO> temp = new ArrayList<AdminUserDTO>();
                //check that user isn't already in that workgroup.
                for(AdminUserDTO uDTO : usersSelection){
                    for(AdminWorkgroupUserDTO wuDTO : workgroupUsers){
                        if(wuDTO.getWorkgroup_key().equals(selectedWorkgroupItem.getWorkgroup_key()) && wuDTO.getUser_key().equals(uDTO.getUserKey())){
                            this.addToMessageQueue("Selected user " + uDTO.getUserName() 
                                    + " already exists in workgroup " + selectedWorkgroupItem.getWorkgroupName(), FacesMessage.SEVERITY_WARN);
                            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Selected user " + uDTO.getUserName() 
                                    + " already exists in workgroup " + selectedWorkgroupItem.getWorkgroupName()));
                            temp.add(uDTO);
                        }
                    }
                }
                //remove 
                for(AdminUserDTO dto : temp){
                    usersSelection.remove(dto);
                }

                temp = new ArrayList<AdminUserDTO>();
                //remove those that are guests...
                
                for(AdminUserDTO uDTO : usersSelection){
                    //create and add workgroup user

                    AdminWorkgroupUserDTO wuDTO = new AdminWorkgroupUserDTO();
                    wuDTO.setCreatedBy(user);
                    wuDTO.setDateCreated(theDate);
                    wuDTO.setDateModified(theDate);
                    wuDTO.setModifiedBy(user);
                    wuDTO.setUser_key(uDTO.getUserKey());
                    wuDTO.setVersion("1");
                    wuDTO.setWorkgroup_key(selectedWorkgroupItem.getWorkgroup_key());

                    String workgroupUserKey = adminDAO.insertWorkgroupUser(wuDTO);

                    //create AdminWorkgroupUserFunctionalArea
                    AdminWorkgroupUserFunctionalAreaDTO dto = new AdminWorkgroupUserFunctionalAreaDTO(); 
                    //these fields stay the same for all
                    dto.setModifiedBy(getCurrentUser().getUserName());
                    dto.setCreatedBy(getCurrentUser().getUserName());
                    dto.setWorkgroupUser_key(workgroupUserKey);
                    dto.setDateCreated(theDate);
                    dto.setDateModified(theDate);
                    dto.setVersion("1");

                    dto.setPrivilege_key("2");

                    if(role.equals("administrator")){
                        dto.setFunctionalArea_key("1");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("4");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                    }

                    if(role.equals("colonyWorker")){
                        dto.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("4");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                    }

                    if(role.equals("guest")){
                        dto.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);
                        dto.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(dto);                        
                    }

                    this.addToMessageQueue("User " + uDTO.getUserName() + " successfully added to workgroup "
                            + selectedWorkgroupItem.getWorkgroupName(), FacesMessage.SEVERITY_INFO);
                }


                addingExistingUser = false;

                refresh();
            } 
            catch(Exception e){
                this.addToMessageQueue("There was an error: " + e, FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "There was an error: " + e));
            }
        }
        else{
            this.addToMessageQueue("Role is required, please select a role.", FacesMessage.SEVERITY_ERROR);            
        }
    }
    
    public void validateChange(){
        warnBeforeUpdate = false;
        //user must be editing their own privs/user access
        if(editUserItem.getUserKey().equals(currentUser.getUserkey().toString())){
            //case where they are removing themselves from the last WG
            if((adminDAO.getWorkgroupCount(currentUser.getUserkey().toString()) == 1) || (adminDAO.getAdminWorkgroupCount(currentUser.getUserkey().toString()) == 1)){
                warnBeforeRemove = true;
            }
            //case where user is revoking admin rights for last workgroup
            if((adminDAO.getAdminWorkgroupCount(currentUser.getUserkey().toString()) == 1) && !role.equals("administrator")){
                warnBeforeUpdate = true;
            }
        }
    }
    
    //removes a user from the workgroup
    public String removeUser(){
        try{
            //key of the user being removed
            String userKey = editUserItem.getUserKey();
            //key of the workgroup user is being removed from
            String workgroupKey = parentWorkgroupKey;
            //key of field in WorkgroupUser (mapping) table
            String workgroupUserKey = "";
            
            //find WorkgroupUser row that corresponds to the one the user wants to remove
            for(AdminWorkgroupUserDTO dto : workgroupUsers){
                if(dto.getWorkgroup_key().equals(workgroupKey) && dto.getUser_key().equals(userKey)){
                    workgroupUserKey = dto.getWorkgroupUser_key();
                }
            }
            
            //make sure it's not mtsadmin
            if(!editUserItem.getUserName().equals("mtsadmin")){
                adminDAO.deleteWorkgroupUser(workgroupUserKey);
                for(AdminWorkgroupUserFunctionalAreaDTO dto: workgroupUserFunctionalAreas){
                    if(dto.getWorkgroupUser_key().equals(workgroupUserKey)){
                        adminDAO.deleteWorkgroupUserFunctionalArea(dto.getWorkgroupUserFunctionalArea_key());
                    }
                }
                //log user off if revoked admin privs for final group
                if(warnBeforeRemove){
                    return logoff();
                }
                else{
                    this.addToMessageQueue("The User " + editUserItem.getUserName() + " has been removed from " +
                                adminDAO.getWorkgroup(workgroupKey).getWorkgroupName(), FacesMessage.SEVERITY_INFO);
                    refresh();
                    selectedUser = false;
                }
            }
            else{
                this.addToMessageQueue("The mtsadmin user cannot be removed.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ", 
                        "The mtsadmin user cannot be removed."));
            }
            return "";
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "There was an error: " + e));
        }
        return "";
    }
    
    private String logoff(){
        HttpSession session = (HttpSession) this.getFacesContext().getExternalContext().getSession(false);
        // Invalid session to force user to log back in.
        session.invalidate();
        return "welcome";
    }
    
    /*
     * following methods are listeners f  or tree (main view) 
     * and datatable (seen when user opts to add existing user)
     * and used to set selected data (node in tree, or rows in
     * arraylist usersSelection). 
     */
    
    //method for getting selected info from the TreeView (selected item can be workgroup, user or center)
    public void processSelection(TreeSelectionChangeEvent event){
        try{
            administrator = false;
            Object data;

            editUserItem = new AdminUserDTO();

            addingCenter = false;
            addingWorkgroup = false;
            addingUser = false;
            addingExistingUser = false;

            selection = new ArrayList<Object>(event.getNewSelection());
            Object currentSelectionKey = selection.get(0);
            UITree tree = (UITree) event.getSource();

            Object storedKey = tree.getRowKey();
            tree.setRowKey(currentSelectionKey);
            AdminTreeNode currentSelection = (AdminTreeNode) tree.getRowData();
            tree.setRowKey(storedKey);
            data = currentSelection.getData();

            if(data instanceof AdminUserDTO){
                System.out.println("you selected a user");

                selectedUser = true;
                selectedWorkgroup = false;
                selectedCenter = false;   
                parentWorkgroupKey = currentSelection.getParentKey();

                currentSelection.getWUFADTOs();

                adminPriv = "0";
                queryPriv = "0";
                reportPriv = "0";
                
                String workgroupUserKey = "";
                for(AdminWorkgroupUserFunctionalAreaDTO dto : currentSelection.getWUFADTOs()){
                    workgroupUserKey = dto.getWorkgroupUser_key();
                    if(dto.getFunctionalArea_key().equals("1")){
                        adminPriv = dto.getPrivilege_key();
                        if(adminPriv.equals("2")){
                            administrator = true;
                        }
                    }
                    else if(dto.getFunctionalArea_key().equals("2")){
                        queryPriv = dto.getPrivilege_key();
                    }
                    else{
                        reportPriv = dto.getPrivilege_key();
                    }
                }
                role = getRoleByWorkgroupUserKey(workgroupUserKey);
                editUserItem = new AdminUserDTO((AdminUserDTO) data);
                
                setIsActiveUser(transformDatabaseBoolean(editUserItem.getIsActive()));
                setIsMasterAdministrator(transformDatabaseBoolean(editUserItem.getIsMasterAdministrator()));
                
            }
            else if(data instanceof AdminWorkgroupDTO){
                System.out.println("you selected a workgroup");
                selectedUser = false;
                selectedWorkgroup = true;
                selectedCenter = false; 

                parentCenterKey = currentSelection.getParentKey();
                setSelectedWorkgroupItem((AdminWorkgroupDTO) data); 
                setIsActiveWorkgroup(transformDatabaseBoolean(getSelectedWorkgroupItem().getIsActive()));
            }
            else{
                System.out.println("you selected a center");
                selectedUser = false;
                selectedWorkgroup = false;
                selectedCenter = true; 

                setSelectedCenterItem((AdminCenterDTO) data); 
                setIsActiveCenter(transformDatabaseBoolean(getSelectedCenterItem().getIsActive()));
            }
        }
        catch(Exception e){
            this.addToMessageQueue("There was an error: " + e, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "There was an error: " + e));
        }
    }
    
    //listeners to determine which users have been selected in the datatable.
    public void selectionListener(AjaxBehaviorEvent event){
        usersSelection.clear(); //make sure you clear the list, otherwise you end up with 'dangling references'
        UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
        for (Object selectionKey : getSelection()) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                usersSelection.add((AdminUserDTO) dataTable.getRowData());
            }
        }
    }
    
    //helper method to turn DB boolean value into java boolean
    public boolean transformDatabaseBoolean(String bool){
        if(bool.equals("0")){
            return false;
        }
        return true;
    }
    
    /**
     * @return the root
     */
    public AdminTreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(AdminTreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedWorkgroup
     */
    public boolean isSelectedWorkgroup() {
        return selectedWorkgroup;
    }

    /**
     * @param selectedWorkgroup the selectedWorkgroup to set
     */
    public void setSelectedWorkgroup(boolean selectedWorkgroup) {
        this.selectedWorkgroup = selectedWorkgroup;
    }

    /**
     * @return the selectedUser
     */
    public boolean isSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(boolean selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * @return the selectedCenter
     */
    public boolean isSelectedCenter() {
        return selectedCenter;
    }

    /**
     * @param selectedCenter the selectedCenter to set
     */
    public void setSelectedCenter(boolean selectedCenter) {
        this.selectedCenter = selectedCenter;
    }

    /**
     * @return the selectedCenterItem
     */
    public AdminCenterDTO getSelectedCenterItem() {
        return selectedCenterItem;
    }

    /**
     * @param selectedCenterItem the selectedCenterItem to set
     */
    public void setSelectedCenterItem(AdminCenterDTO selectedCenterItem) {
        this.selectedCenterItem = selectedCenterItem;
    }

    /**
     * @return the selectedWorkgroupItem
     */
    public AdminWorkgroupDTO getSelectedWorkgroupItem() {
        return selectedWorkgroupItem;
    }

    /**
     * @param selectedWorkgroupItem the selectedWorkgroupItem to set
     */
    public void setSelectedWorkgroupItem(AdminWorkgroupDTO selectedWorkgroupItem) {
        this.selectedWorkgroupItem = selectedWorkgroupItem;
    }

    /**
     * @return the editUserItem
     */
    public AdminUserDTO getEditUserItem() {
        return editUserItem;
    }

    /**
     * @param editUserItem the editUserItem to set
     */
    public void setEditUserItem(AdminUserDTO editUserItem) {
        this.editUserItem = editUserItem;
    }

    /**
     * @return the changingPassword
     */
    public boolean isChangingPassword() {
        return changingPassword;
    }

    /**
     * @param changingPassword the changingPassword to set
     */
    public void setChangingPassword(boolean changingPassword) {
        this.changingPassword = changingPassword;
    }

    /**
     * @return the isActiveUser
     */
    public boolean isIsActiveUser() {
        return isActiveUser;
    }

    /**
     * @param isActiveUser the isActiveUser to set
     */
    public void setIsActiveUser(boolean isActiveUser) {
        this.isActiveUser = isActiveUser;
    }

    /**
     * @return the isActiveWorkgroup
     */
    public boolean isIsActiveWorkgroup() {
        return isActiveWorkgroup;
    }

    /**
     * @param isActiveWorkgroup the isActiveWorkgroup to set
     */
    public void setIsActiveWorkgroup(boolean isActiveWorkgroup) {
        this.isActiveWorkgroup = isActiveWorkgroup;
    }

    /**
     * @return the isActiveCenter
     */
    public boolean isIsActiveCenter() {
        return isActiveCenter;
    }

    /**
     * @param isActiveCenter the isActiveCenter to set
     */
    public void setIsActiveCenter(boolean isActiveCenter) {
        this.isActiveCenter = isActiveCenter;
    }

    /**
     * @return the addingWorkgroup
     */
    public boolean isAddingWorkgroup() {
        return addingWorkgroup;
    }

    /**
     * @param addingWorkgroup the addingWorkgroup to set
     */
    public void setAddingWorkgroup(boolean addingWorkgroup) {
        this.addingWorkgroup = addingWorkgroup;
    }

    /**
     * @return the addCenterDTO
     */
    public AdminCenterDTO getAddCenterDTO() {
        return addCenterDTO;
    }

    /**
     * @param addCenterDTO the addCenterDTO to set
     */
    public void setAddCenterDTO(AdminCenterDTO addCenterDTO) {
        this.addCenterDTO = addCenterDTO;
    }

    /**
     * @return the addWorkgroupDTO
     */
    public AdminWorkgroupDTO getAddWorkgroupDTO() {
        return addWorkgroupDTO;
    }

    /**
     * @param addWorkgroupDTO the addWorkgroupDTO to set
     */
    public void setAddWorkgroupDTO(AdminWorkgroupDTO addWorkgroupDTO) {
        this.addWorkgroupDTO = addWorkgroupDTO;
    }

    /**
     * @return the addUserDTO
     */
    public AdminUserDTO getAddUserDTO() {
        return addUserDTO;
    }

    /**
     * @param addUserDTO the addUserDTO to set
     */
    public void setAddUserDTO(AdminUserDTO addUserDTO) {
        this.addUserDTO = addUserDTO;
    }

    /**
     * @return the addingCenter
     */
    public boolean isAddingCenter() {
        return addingCenter;
    }

    /**
     * @param addingCenter the addingCenter to set
     */
    public void setAddingCenter(boolean addingCenter) {
        this.addingCenter = addingCenter;
    }

    /**
     * @return the addingUser
     */
    public boolean isAddingUser() {
        return addingUser;
    }

    /**
     * @param addingUser the addingUser to set
     */
    public void setAddingUser(boolean addingUser) {
        this.addingUser = addingUser;
    }

    /**
     * @return the verifyPassword
     */
    public String getVerifyPassword() {
        return verifyPassword;
    }

    /**
     * @param verifyPassword the verifyPassword to set
     */
    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isMasterAdministrator
     */
    public boolean isIsMasterAdministrator() {
        return isMasterAdministrator;
    }

    /**
     * @param isMasterAdministrator the isMasterAdministrator to set
     */
    public void setIsMasterAdministrator(boolean isMasterAdministrator) {
        this.isMasterAdministrator = isMasterAdministrator;
    }

    /**
     * @return the functionalAreas
     */
    public ArrayList<AdminFunctionalAreaDTO> getFunctionalAreas() {
        return functionalAreas;
    }

    /**
     * @param functionalAreas the functionalAreas to set
     */
    public void setFunctionalAreas(ArrayList<AdminFunctionalAreaDTO> functionalAreas) {
        this.functionalAreas = functionalAreas;
    }

    /**
     * @return the areas
     */
    public ArrayList<SelectItem> getPrivs() {
        return privs;
    }

    /**
     * @param areas the areas to set
     */
    public void setPrivs(ArrayList<SelectItem> privs) {
        this.privs = privs;
    }

    /**
     * @return the queryPriv
     */
    public String getQueryPriv() {
        return queryPriv;
    }

    /**
     * @param queryPriv the queryPriv to set
     */
    public void setQueryPriv(String queryPriv) {
        this.queryPriv = queryPriv;
    }

    /**
     * @return the reportPriv
     */
    public String getReportPriv() {
        return reportPriv;
    }

    /**
     * @param reportPriv the reportPriv to set
     */
    public void setReportPriv(String reportPriv) {
        this.reportPriv = reportPriv;
    }

    /**
     * @return the adminPriv
     */
    public String getAdminPriv() {
        return adminPriv;
    }

    /**
     * @param adminPriv the adminPriv to set
     */
    public void setAdminPriv(String adminPriv) {
        this.adminPriv = adminPriv;
    }

    /**
     * @return the users
     */
    public ArrayList<AdminUserDTO> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<AdminUserDTO> users) {
        this.users = users;
    }

    /**
     * @return the addingExistingUser
     */
    public boolean isAddingExistingUser() {
        return addingExistingUser;
    }

    /**
     * @param addingExistingUser the addingExistingUser to set
     */
    public void setAddingExistingUser(boolean addingExistingUser) {
        this.addingExistingUser = addingExistingUser;
    }

    /**
     * @return the selection
     */
    public ArrayList<Object> getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(ArrayList<Object> selection) {
        this.selection = selection;
    }

    /**
     * @return the currentUser
     */
    public UserEntity getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the administrator
     */
    public boolean isAdministrator() {
        return administrator;
    }

    /**
     * @param administrator the administrator to set
     */
    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * @return the warnBeforeRemove
     */
    public boolean isWarnBeforeRemove() {
        return warnBeforeRemove;
    }

    /**
     * @param warnBeforeRemove the warnBeforeRemove to set
     */
    public void setWarnBeforeRemove(boolean warnBeforeRemove) {
        this.warnBeforeRemove = warnBeforeRemove;
    }

    /**
     * @return the warnBeforeUpdate
     */
    public boolean isWarnBeforeUpdate() {
        return warnBeforeUpdate;
    }

    /**
     * @param warnBeforeUpdate the warnBeforeUpdate to set
     */
    public void setWarnBeforeUpdate(boolean warnBeforeUpdate) {
        this.warnBeforeUpdate = warnBeforeUpdate;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
