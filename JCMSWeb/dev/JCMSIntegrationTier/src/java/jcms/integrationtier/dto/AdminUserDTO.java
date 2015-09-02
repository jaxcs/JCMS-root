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

package jcms.integrationtier.dto;

import java.security.MessageDigest;
import org.jboss.util.Base64;

/**
 *
 * @author cnh
 */
public class AdminUserDTO {
    
    private String UserKey;
    private String DefaultWorkgroupKey;
    private String UserName;
    private String FirstName;
    private String LastName;
    private String IsActive;
    private String Password_;
    private String NetworkID;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;
    private String IsMasterAdministrator;
    
    
    public AdminUserDTO() {
        
    }
    
    //make shallow copy
    public AdminUserDTO(AdminUserDTO dto){
        UserKey = dto.UserKey;
        DefaultWorkgroupKey = dto.DefaultWorkgroupKey;
        UserName = dto.UserName;
        FirstName = dto.FirstName;
        LastName = dto.LastName;
        IsActive = dto.IsActive;
        Password_ = dto.Password_;
        NetworkID = dto.NetworkID;
        CreatedBy = dto.CreatedBy;
        DateCreated = dto.DateCreated;
        ModifiedBy = dto.ModifiedBy;
        DateModified = dto.DateModified;
        Version = dto.Version; 
        IsMasterAdministrator = dto.IsMasterAdministrator;
    }
    
    public AdminUserDTO (
        String userKey,
        String defaultWorkgroupKey,
        String userName,
        String firstName,
        String lastName,
        String isActive,
        String password_,
        String networkID,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version,
        String isMasterAdministrator) {
        
        UserKey = userKey;
        DefaultWorkgroupKey = defaultWorkgroupKey;
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        IsActive = isActive;
        this.setPassword_(password_);
        NetworkID = networkID;
        CreatedBy = createdBy;
        DateCreated = dateCreated;
        ModifiedBy = modifiedBy;
        DateModified = dateModified;
        Version = version; 
        IsMasterAdministrator = isMasterAdministrator;
    }

    /**
     * @return the UserKey
     */
    public String getUserKey() {
        return UserKey;
    }

    /**
     * @param UserKey the UserKey to set
     */
    public void setUserKey(String UserKey) {
        this.UserKey = UserKey;
    }

    /**
     * @return the DefaultWorkgroupKey
     */
    public String getDefaultWorkgroupKey() {
        return DefaultWorkgroupKey;
    }

    /**
     * @param DefaultWorkgroupKey the DefaultWorkgroupKey to set
     */
    public void setDefaultWorkgroupKey(String DefaultWorkgroupKey) {
        this.DefaultWorkgroupKey = DefaultWorkgroupKey;
    }

    /**
     * @return the UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param UserName the UserName to set
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * @return the FirstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param FirstName the FirstName to set
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * @return the LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName the LastName to set
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * @return the IsActive
     */
    public String getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive the IsActive to set
     */
    public void setIsActive(String IsActive) {
        this.IsActive = IsActive;
        if(IsActive.equals("true")){
            this.IsActive = "-1";
        }
        else if(IsActive.equals("false")){
            this.IsActive = "0";
        }
    }
    
    public void setIsActive(boolean IsActive){
        if(IsActive){
            this.IsActive = "1";
        }
        else{
            this.IsActive = "0";
        }
    }

    /**
     * @return the Password_
     */
    public String getPassword_() {
        return Password_;
    }

    /**
     * @param Password_ the Password_ to set
     */
    public void setPassword_(String password_) {
        this.Password_ = password_;
    }

    
    /**
     * @return the NetworkID
     */
    public String getNetworkID() {
        return NetworkID;
    }

    /**
     * @param NetworkID the NetworkID to set
     */
    public void setNetworkID(String NetworkID) {
        this.NetworkID = NetworkID;
    }

    /**
     * @return the CreatedBy
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * @param CreatedBy the CreatedBy to set
     */
    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    /**
     * @return the DateCreated
     */
    public String getDateCreated() {
        return DateCreated;
    }

    /**
     * @param DateCreated the DateCreated to set
     */
    public void setDateCreated(String DateCreated) {
        this.DateCreated = DateCreated;
    }

    /**
     * @return the ModifiedBy
     */
    public String getModifiedBy() {
        return ModifiedBy;
    }

    /**
     * @param ModifiedBy the ModifiedBy to set
     */
    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    /**
     * @return the DateModified
     */
    public String getDateModified() {
        return DateModified;
    }

    /**
     * @param DateModified the DateModified to set
     */
    public void setDateModified(String DateModified) {
        this.DateModified = DateModified;
    }

    /**
     * @return the Version
     */
    public String getVersion() {
        return Version;
    }

    /**
     * @param Version the Version to set
     */
    public void setVersion(String Version) {
        this.Version = Version;
    }

    /**
     * @return the IsMasterAdministrator
     */
    public String getIsMasterAdministrator() {
        return IsMasterAdministrator;
    }

    /**
     * @param IsMasterAdministrator the IsMasterAdministrator to set
     */
    public void setIsMasterAdministrator(String IsMasterAdministrator) {
        this.IsMasterAdministrator = IsMasterAdministrator;
        if(IsMasterAdministrator.equals("true")){
            this.IsMasterAdministrator = "1";
        }
        else if(IsMasterAdministrator.equals("false")){
            this.IsMasterAdministrator = "0";
        }
        this.IsMasterAdministrator = IsMasterAdministrator;
    }
    
    public void setIsMasterAdministrator(boolean IsMasterAdministrator){
        if(IsMasterAdministrator){
            this.IsMasterAdministrator = "1";
        }
        else{
            this.IsMasterAdministrator = "0";
        }
    }

}
