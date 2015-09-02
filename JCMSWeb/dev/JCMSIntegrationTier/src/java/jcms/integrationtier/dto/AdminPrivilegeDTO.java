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

/**
 *
 * @author cnh
 */
public class AdminPrivilegeDTO {
    private String PrivilegeKey; 
    private String Privilege;
    private String Description;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;
    
    public AdminPrivilegeDTO() {
    }
    
    public AdminPrivilegeDTO(
        String privilegeKey, 
        String privilege,
        String description,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
        
    PrivilegeKey = privilegeKey; 
    Privilege = privilege;
    Description = description;
    CreatedBy = createdBy;
    DateCreated = dateCreated;
    ModifiedBy = modifiedBy;
    DateModified = dateModified;
    Version = version;
        
    }

    /**
     * @return the PrivilegeKey
     */
    public String getPrivilegeKey() {
        return PrivilegeKey;
    }

    /**
     * @param PrivilegeKey the PrivilegeKey to set
     */
    public void setPrivilegeKey(String PrivilegeKey) {
        this.PrivilegeKey = PrivilegeKey;
    }

    /**
     * @return the Privilege
     */
    public String getPrivilege() {
        return Privilege;
    }

    /**
     * @param Privilege the Privilege to set
     */
    public void setPrivilege(String Privilege) {
        this.Privilege = Privilege;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
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
    
}
