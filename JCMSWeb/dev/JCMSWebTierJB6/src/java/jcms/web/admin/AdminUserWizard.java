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

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.dao.AdministrationDAO;
import jcms.integrationtier.dto.AdminUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupDTO;
import java.util.Date;
import jcms.integrationtier.dao.UserPreferencesDAO;
import jcms.integrationtier.dto.AdminWorkgroupUserDTO;
import jcms.integrationtier.dto.AdminWorkgroupUserFunctionalAreaDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;
import org.jboss.util.Base64;

/**
 *
 * @author mkamato
 */
public class AdminUserWizard extends WTBaseBackingBean{
    
    private ArrayList<AdminWorkgroupDTO> workgroups = null;
    private ArrayList<Object> selectedWorkgroups = new ArrayList<Object>();
    private String firstName = "";
    private String lastName = "";
    private String userName = "";
    private String password = "";
    private String verifyPassword = "";
    private boolean selectAll = false;
    private AdministrationDAO adminDAO = new AdministrationDAO();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    private UserEntity currentUser = (UserEntity) getSessionParameter("userEntity");
    private String role = "";
    private UserPreferencesDAO userPrefDAO = new UserPreferencesDAO();
    
    public AdminUserWizard(){
        workgroups = adminDAO.getAdminWorkgroups(currentUser.getUserkey().toString());
    }
    
    public void refresh(){
        workgroups = adminDAO.getAdminWorkgroups(currentUser.getUserkey().toString());        
    }
    
    public void addUser(){
        boolean flag = false;
        if(userName.equals("")){
            this.addToMessageQueue("User Name is required.", FacesMessage.SEVERITY_ERROR);            
            flag = true;
        }
        if(password.equals("")){
            this.addToMessageQueue("Password is required.", FacesMessage.SEVERITY_ERROR);            
            flag = true;
        }
        if(role.equals("")){
            this.addToMessageQueue("Role is required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!verifyPassword.equals(password)){
            this.addToMessageQueue("Your passwords did not match up, please retype your passwords", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        for(AdminUserDTO dto : adminDAO.getUsers()){
            if(dto.getUserName().equals(userName)){
                this.addToMessageQueue("User with that username already exists, please select another username.", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(selectedWorkgroups.isEmpty()){
            this.addToMessageQueue("Please select workgroup(s) to add the user to.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            String theDate = formatter.format(new Date());
            String str = "User " + userName + " added to workgroup(s)";
            int idx = 1;
            AdminUserDTO dto = new AdminUserDTO();
            dto.setUserName(userName);
            dto.setPassword_(encodePassword(password));
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setCreatedBy(currentUser.getUserName());
            dto.setDateCreated(theDate);
            dto.setDateModified(theDate);
            dto.setModifiedBy(currentUser.getUserName());
            Integer wgIndex = (Integer) selectedWorkgroups.get(0);
            dto.setDefaultWorkgroupKey(getWorkgroups().get(wgIndex.intValue()).getWorkgroup_key().toString());
            dto.setIsActive("1");
            dto.setIsMasterAdministrator("0");
            dto.setVersion("1");
            try{
                String userKey = adminDAO.insertUser(dto);
                for(Object obj : selectedWorkgroups){
                    //get workgroup
                    Integer i = (Integer) obj;
                    AdminWorkgroupDTO we = getWorkgroups().get(i.intValue());
                    
                    //build workgroupuser dto and insert
                    AdminWorkgroupUserDTO wuDTO = new AdminWorkgroupUserDTO();
                    wuDTO.setCreatedBy(currentUser.getUserName());
                    wuDTO.setDateCreated(theDate);
                    wuDTO.setDateModified(theDate);
                    wuDTO.setModifiedBy(currentUser.getUserName());
                    wuDTO.setUser_key(userKey);
                    wuDTO.setWorkgroup_key(we.getWorkgroup_key());
                    wuDTO.setVersion("1");
                    String workgroupUserKey = adminDAO.insertWorkgroupUser(wuDTO);
            
                    AdminWorkgroupUserFunctionalAreaDTO wufaDTO = new AdminWorkgroupUserFunctionalAreaDTO(); 
                    //these fields stay the same for all
                    wufaDTO.setModifiedBy(currentUser.getUserName());
                    wufaDTO.setCreatedBy(currentUser.getUserName());
                    wufaDTO.setDateCreated(theDate);
                    wufaDTO.setDateModified(theDate);
                    wufaDTO.setWorkgroupUser_key(workgroupUserKey);
                    wufaDTO.setVersion("1");
                    wufaDTO.setPrivilege_key("2");
                    
                    if(role.equals("administrator")){
                        wufaDTO.setFunctionalArea_key("1");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("4");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                    }

                    if(role.equals("colonyWorker")){
                        wufaDTO.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("4");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                    }
                    
                    if(role.equals("guest")){
                        wufaDTO.setFunctionalArea_key("2");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);
                        wufaDTO.setFunctionalArea_key("3");
                        adminDAO.insertWorkgroupUserFunctionalArea(wufaDTO);                        
                    }
                    
                    if(idx == 1){
                        str = str + " " + we.getWorkgroupName();
                    }
                    else{
                        str = str + ", " + we.getWorkgroupName();
                    }
                    idx++;
                }
                        
                //Setup the user preferences for this new user
                //We don't care if the user is in multiple workgroups, they will only
                //have one set of preferences
                if (userPrefDAO.insertUserPreferences(userKey) == 0) {
                    //Something went wrong
                    this.addToMessageQueue("User Preferences are not set up correctly.", FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Adding New User: ", "User has been added but User Preferences did not get set up correctly. "));
                }
                
                this.addToMessageQueue(str, FacesMessage.SEVERITY_INFO);
                userName = "";
                firstName = "";
                password = "";
                verifyPassword = "";
                lastName = "";
                selectedWorkgroups = new ArrayList<Object>();
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }       
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
    
    /**
     * @return the selectAll
     */
    public boolean isSelectAll() {
        return selectAll;
    }

    /**
     * @param selectAll the selectAll to set
     */
    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    /**
     * @return the selectedWorkgroups
     */
    public ArrayList<Object> getSelectedWorkgroups() {
        return selectedWorkgroups;
    }

    /**
     * @param selectedWorkgroups the selectedWorkgroups to set
     */
    public void setSelectedWorkgroups(ArrayList<Object> selectedWorkgroups) {
        this.selectedWorkgroups = selectedWorkgroups;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @return the workgroups
     */
    public ArrayList<AdminWorkgroupDTO> getWorkgroups() {
        return workgroups;
    }

    /**
     * @param workgroups the workgroups to set
     */
    public void setWorkgroups(ArrayList<AdminWorkgroupDTO> workgroups) {
        this.workgroups = workgroups;
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
