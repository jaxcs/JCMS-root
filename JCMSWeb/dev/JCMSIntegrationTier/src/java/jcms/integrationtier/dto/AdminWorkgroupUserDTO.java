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
public class AdminWorkgroupUserDTO {
    private String _WorkgroupUser_key;
    private String _Workgroup_key;
    private String _User_key;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;

    public AdminWorkgroupUserDTO() {
        
    }
    
    public AdminWorkgroupUserDTO (
        String workgroupUserKey,
        String workgroupKey,
        String userKey,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
    
        _WorkgroupUser_key = workgroupUserKey;
        _Workgroup_key = workgroupKey;
        _User_key = userKey;
        CreatedBy = createdBy;
        DateCreated = dateCreated;
        ModifiedBy = modifiedBy;
        DateModified = dateModified;
        Version = version;     
    }

    /**
     * @return the _WorkgroupUser_key
     */
    public String getWorkgroupUser_key() {
        return _WorkgroupUser_key;
    }

    /**
     * @param WorkgroupUser_key the _WorkgroupUser_key to set
     */
    public void setWorkgroupUser_key(String WorkgroupUser_key) {
        this._WorkgroupUser_key = WorkgroupUser_key;
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
     * @return the _Workgroup_key
     */
    public String getWorkgroup_key() {
        return _Workgroup_key;
    }

    /**
     * @param Workgroup_key the _Workgroup_key to set
     */
    public void setWorkgroup_key(String Workgroup_key) {
        this._Workgroup_key = Workgroup_key;
    }

    /**
     * @return the _User_key
     */
    public String getUser_key() {
        return _User_key;
    }

    /**
     * @param User_key the _User_key to set
     */
    public void setUser_key(String User_key) {
        this._User_key = User_key;
    }
    
}
