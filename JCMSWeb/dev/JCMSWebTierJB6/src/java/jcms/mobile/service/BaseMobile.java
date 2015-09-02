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
package jcms.mobile.service;

import jcms.integrationtier.dao.DbSetupDAO;
import java.util.List;
import java.util.ArrayList;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.dao.MobileUtilitiesDAO;
/**
 *
 * @author mkamato
 */
public class BaseMobile {
    
    MobileUtilitiesDAO muDAO = new MobileUtilitiesDAO();
        
    /**
     * Method to determine user's preferred date format from date format DB 
     * Setup variable.
     * 
     * @return String representing the preferred date format 
     */
    public String determineDateFormat(){
        //Find the MM, yyyy, and dd - use the separator variable as well
        return new DbSetupDAO().getJCMSWebDateFormat().getMTSValue();
    }
    
    /**
     * Method to retrieve list of Workgroups to which user has at least guest 
     * permissions for.
     * 
     * @return List of WorkgroupEntitys corresponding to the workgroups the user 
     *         has at least guest access to. 
     */
    public List<WorkgroupEntity> getUserGuestWorkgroups(String userName) throws Exception {
        return muDAO.getGuestWorkgroupEntities(userName);
    }
    
    /**
     * Method to retrieve list of Workgroups to which user has at least colony
     * manage permissions for.
     * 
     * @return List of WorkgroupEntitys corresponding to the workgroups the user 
     *         has at least colony manage access to. 
     */
    public List<WorkgroupEntity> getUserColonyManageWorkgroups(String userName) throws Exception {
        return muDAO.getColonyManageWorkgroupEntities(userName);
    }
    
    /**
     * Method to retrieve list of Workgroups to which user has at least guest 
     * permissions for.
     * 
     * @return List of WorkgroupEntitys corresponding to the workgroups the user 
     *         has administrative access to. 
     */
    public List<WorkgroupEntity> getUserAdministrativeWorkgroups(String userName) throws Exception {
        return muDAO.getAdministratorWorkgroupEntities(userName);
    }
    
    /**
     * Method to retrieve list of Owners to which user has at least guest 
     * permissions for.
     * 
     * @return List of OwnerEntitys corresponding to the owners the user 
     *         has at least guest access to. 
     */
    public List<OwnerEntity> getUserGuestOwners(String userName) throws Exception {
        List workgroups = new ArrayList<WorkgroupEntity>();
        
        return workgroups;
    }
    
    /**
     * Method to retrieve list of Owners to which user has at least colony 
     * manage permissions for.
     * 
     * @return List of OwnerEntitys corresponding to the owners the user 
     *         has at least colony manage access to. 
     */
    public List<OwnerEntity> getUserColonyManageOwners(String userName) throws Exception {
        List workgroups = new ArrayList<WorkgroupEntity>();
        
        return workgroups;
    }
    
    /**
     * Method to retrieve list of Owners to which user has administrative 
     * permissions for.
     * 
     * @return List of OwnerEntitys corresponding to the owners the user 
     *         has at least administrative access to. 
     */
    public List<OwnerEntity> getUserAdministrativeOwners(String userName) throws Exception {
        List workgroups = new ArrayList<WorkgroupEntity>();
        
        return workgroups;
    }
    
    public boolean autheticateRequest(String encryptedPW){
        try{
            return new MobileUtilitiesDAO().autheticateSession(encryptedPW);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            return false;
        }
    }
}
