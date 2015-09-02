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

package jcms.web.main;

import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.faces.application.FacesMessage;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.AdministrationDAO;
import jcms.integrationtier.dto.FunctionalAreaPrivilegeDTO;
import jcms.integrationtier.dto.UserDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.service.RepositoryService;
import jcms.integrationtier.dao.DbSetupDAO;
import jcms.integrationtier.dto.DbSetupDTO;
import jcms.integrationtier.dao.JCMSWebDAO;

public class LoginOwnerBean extends WTBaseBackingBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserEntity userEntity = new UserEntity();
    private String loggedUser = "";
    private boolean masterAdmin = false;
    private boolean passwordExpired = false;
    private boolean showDashboard = true;
    private String activeVisualization = "none";
    private JCMSWebDAO webDAO = new JCMSWebDAO();
    
    public LoginOwnerBean() throws Exception {
        initialize();
        RepositoryService service = new RepositoryService();
        // temporary code to map logged in username to owner
        Principal principal = getFacesContext().getExternalContext().getUserPrincipal();       

        if (principal == null) {
//            this.addToMessageQueue("User has no valid role(s) for this application.", FacesMessage.SEVERITY_WARN);
            this.getLogger().logWarn(this.formatLogMessage("Unauthenticated User Account", "User has no valid role(s) for this application."));
            throw new LoginException("User has no valid role(s) for this application.");
        }
        UserDTO userDTO = service.findUser(principal.getName());
        
        if (userDTO != null) {
            userEntity = userDTO.getUserEntity();

            if (userEntity != null) {
                this.setLoggedUser(userEntity.getUserName());
            }
        }
        
        // get the workgroups
        SortedMap[] guestWGMap = null;
        SortedMap[] colonyManageWGMap = null;
        SortedMap[] adminWGMap = null;
        
        Result guestWGs = webDAO.getGuestWorkgroupsForUser(loggedUser);
        Result colonyManageWGs = webDAO.getColonyManagerWorkgroupsForUser(loggedUser);
        Result adminWGs = webDAO.getAdministrativeWorkgroupsForUser(loggedUser);
        
        ArrayList<WorkgroupEntity> guestWGEntities = new ArrayList<WorkgroupEntity>();
        ArrayList<WorkgroupEntity> colonyManageWGEntities = new ArrayList<WorkgroupEntity>();
        ArrayList<WorkgroupEntity> adminWGEntities = new ArrayList<WorkgroupEntity>();
        
        ArrayList<OwnerEntity> guestOwnerEntities = new ArrayList<OwnerEntity>();
        ArrayList<OwnerEntity> colonyManageOwnerEntities = new ArrayList<OwnerEntity>();
        ArrayList<OwnerEntity> adminOwnerEntities = new ArrayList<OwnerEntity>();
        
        String guestWGName = "";
        String colonyManageWGName = "";
        String adminWGName = "";
                
        // get the workgroups for logged in user
        if (guestWGs != null && colonyManageWGs != null && adminWGs != null) {
            guestWGMap = guestWGs.getRows();
            colonyManageWGMap = colonyManageWGs.getRows();
            adminWGMap = adminWGs.getRows();            
        }

        if (guestWGMap != null && colonyManageWGMap != null && adminWGMap != null) {            
            //build the colony manage privilege workgroups/owner list
            for (SortedMap guestWG : guestWGMap) {
                if (guestWG.get("WorkgroupName") != null) {
                    guestWGName = guestWG.get("WorkgroupName").toString();
                }


                WorkgroupEntity wgEntity;
                if (service.findWorkgroupEntity(guestWGName) != null) {
                    wgEntity = (WorkgroupEntity) service.findWorkgroupEntity(guestWGName);
                    guestWGEntities.add(wgEntity);
                }

                List<ITBaseEntityInterface> list = new RepositoryService().baseFindAllByOwner(new OwnerEntity(), guestWGName);

                ITBaseEntityTable table = new ITBaseEntityTable();
                table.setList(list);

                for (ITBaseEntityInterface entity : table.getList()) {
                    guestOwnerEntities.add((OwnerEntity) entity);
                }
            }
                        
            //build the colony manage privilege workgroups/owner list
            for (SortedMap cmWG : colonyManageWGMap) {
                if (cmWG.get("WorkgroupName") != null) {
                    colonyManageWGName = cmWG.get("WorkgroupName").toString();
                }


                WorkgroupEntity wgEntity;
                if (service.findWorkgroupEntity(colonyManageWGName) != null) {
                    wgEntity = (WorkgroupEntity) service.findWorkgroupEntity(colonyManageWGName);
                    colonyManageWGEntities.add(wgEntity);
                }

                List<ITBaseEntityInterface> list = new RepositoryService().baseFindAllByOwner(new OwnerEntity(), colonyManageWGName);

                ITBaseEntityTable table = new ITBaseEntityTable();
                table.setList(list);

                for (ITBaseEntityInterface entity : table.getList()) {
                    colonyManageOwnerEntities.add((OwnerEntity) entity);
                }
            }
            
            for (SortedMap adminWG : adminWGMap) {
                if (adminWG.get("WorkgroupName") != null) {
                    adminWGName = adminWG.get("WorkgroupName").toString();
                }


                WorkgroupEntity wgEntity;
                if (service.findWorkgroupEntity(adminWGName) != null) {
                    wgEntity = (WorkgroupEntity) service.findWorkgroupEntity(adminWGName);
                    adminWGEntities.add(wgEntity);
                }

                List<ITBaseEntityInterface> list = new RepositoryService().baseFindAllByOwner(new OwnerEntity(), adminWGName);

                ITBaseEntityTable table = new ITBaseEntityTable();
                table.setList(list);

                for (ITBaseEntityInterface entity : table.getList()) {
                    adminOwnerEntities.add((OwnerEntity) entity);
                }
            }            
        }

        if (this.userEntity != null){
            this.setAuthorization(userEntity.getUserkey().toString());
        }
        this.putSessionParameter("userEntity", userEntity);
        this.putSessionParameter("loggedUser", this.getLoggedUser());

        //workgroup entity lists
        this.putSessionParameter("guestWorkgroupEntityLst", guestWGEntities);
        this.putSessionParameter("colonyManageWorkgroupEntityLst", colonyManageWGEntities);
        this.putSessionParameter("adminWorkgroupEntityLst", adminWGEntities);
        //owner entity lists
        this.putSessionParameter("guestOwnerEntityLst", guestOwnerEntities);
        this.putSessionParameter("colonyManageOwnerEntityLst", colonyManageOwnerEntities);
        this.putSessionParameter("adminOwnerEntityLst", adminOwnerEntities);
        this.putSessionParameter("masterAdmin", this.isMasterAdmin());
    }

    private void initialize() {
            this.putSessionParameter("hasAdministration", "false");
            this.putSessionParameter("hasQuerying", "false");
            this.putSessionParameter("hasReporting", "false");
            this.putSessionParameter("hasColonyManagement", "false");
    }
    
    private void setAuthorization(String userKey) {
        AdministrationDAO adminDao = new AdministrationDAO();
        ArrayList<FunctionalAreaPrivilegeDTO> list =  adminDao.getWorkgroupFunctionalAreasByUserKey(userKey);
        for (FunctionalAreaPrivilegeDTO dto : list) {
            if (dto.getFunctionalArea().equalsIgnoreCase(WTBaseBackingBean.FA_ADMINISTRATION)) {
                if (dto.getPrivilege().equalsIgnoreCase("write"))
                    this.putSessionParameter(WTBaseBackingBean.HASADMINISTRATION, "true");
            } else
            if (dto.getFunctionalArea().equalsIgnoreCase(WTBaseBackingBean.FA_QUERYING)) {
                if (dto.getPrivilege().equalsIgnoreCase("write")) 
                    this.putSessionParameter(WTBaseBackingBean.HASQUERYING, "true");
            } else
            if (dto.getFunctionalArea().equalsIgnoreCase(WTBaseBackingBean.FA_REPORTING)) {
                if (dto.getPrivilege().equalsIgnoreCase("write")) 
                    this.putSessionParameter(WTBaseBackingBean.HASREPORTING, "true");
            }  
            if (dto.getFunctionalArea().equalsIgnoreCase(WTBaseBackingBean.FA_COLONYMANAGEMENT)) {
                if (dto.getPrivilege().equalsIgnoreCase("write")) 
                    this.putSessionParameter(WTBaseBackingBean.HASCOLONYMANAGEMENT, "true");
            }        
        }        
    }
    
    public void checkPasswordChange(){
        DbSetupDAO dao = new DbSetupDAO();
        DbSetupDTO enforcePWChange = dao.getJCMSEnforcePasswordChange();
        DbSetupDTO PWChangePeriod = dao.getJCMSPasswordChangePeriod();
        //if you're enforcing PW change continue, else don't tell user to change PW
        if(enforcePWChange.getBlnMTSValue()){  
            //get logged user info
            RepositoryService service = new RepositoryService();
            UserDTO userDTO = service.findUser(loggedUser);

            if (userDTO != null) {
                userEntity = userDTO.getUserEntity();
            }  
            Calendar cal = Calendar.getInstance();
            cal.setTime(userEntity.getPasswordChangedDate());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(PWChangePeriod.getMTSValue()));
            if(cal.getTime().before(new Date())){
                passwordExpired = true;
            }
            else{
                passwordExpired = false;
            }
        }
        else{
            passwordExpired = false;
        }        
    }
    
    public String logoutAction()
    {
        HttpSession session = (HttpSession) this.getFacesContext().getExternalContext().getSession(false);

        // Invalid session to force user to log back in.
        session.invalidate();
        
        return "welcome";
    }

    public int MasterAdminAction()
    {
        int val = 0;

        if (this.userEntity != null && this.getUserEntity().getIsMasterAdministrator() == 1) {
            val = 1;
        }
        else {
             // Display validation information
             this.addToMessageQueue("User does not have Master Admin Privilege", FacesMessage.SEVERITY_WARN);
             this.getLogger().logWarn(this.formatLogMessage("Validation ", "User does not have Master Admin Privilege"));
        }
        return val;
    }

    public void hideDashboard(){
        showDashboard = false;
    }
    
    public void viewDashboard(){
        showDashboard = true;
        setActiveVisualization("none");
    }

    /**
     * @return the loggedUser
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * @param loggedUser the loggedUser to set
     */
    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * @param userEntity the userEntity to set
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * @return the masterAdmin
     */
    public boolean isMasterAdmin() {
        return masterAdmin;
    }

    /**
     * @param masterAdmin the masterAdmin to set
     */
    public void setMasterAdmin(boolean masterAdmin) {
        this.masterAdmin = masterAdmin;
    }

    /**
     * @return the passwordExpired
     */
    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    /**
     * @param passwordExpired the passwordExpired to set
     */
    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    /**
     * @return the showDashboard
     */
    public boolean isShowDashboard() {
        return showDashboard;
    }

    /**
     * @param showDashboard the showDashboard to set
     */
    public void setShowDashboard(boolean showDashboard) {
        this.showDashboard = showDashboard;
    }

    /**
     * @return the activeVisualization
     */
    public String getActiveVisualization() {
        return activeVisualization;
    }

    /**
     * @param activeVisualization the activeVisualization to set
     */
    public void setActiveVisualization(String activeVisualization) {
        this.activeVisualization = activeVisualization;
    }
}