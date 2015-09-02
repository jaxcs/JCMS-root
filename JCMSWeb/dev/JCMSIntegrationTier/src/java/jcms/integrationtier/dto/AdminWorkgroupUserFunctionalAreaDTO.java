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
public class AdminWorkgroupUserFunctionalAreaDTO {
    private String _WorkgroupUserFunctionalArea_key;
    private String _FunctionalArea_key;
    private String _WorkgroupUser_key;
    private String _Privilege_key;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;

    public AdminWorkgroupUserFunctionalAreaDTO() {
        
    }
    
    public AdminWorkgroupUserFunctionalAreaDTO (
        String workgroupUserFunctionalAreaKey,
        String functionalAreaKey,
        String workgroupUserKey,
        String privilegeKey,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
        
        _WorkgroupUserFunctionalArea_key = workgroupUserFunctionalAreaKey;
        _FunctionalArea_key = functionalAreaKey;
        _WorkgroupUser_key = workgroupUserKey;
        _Privilege_key = privilegeKey;
        CreatedBy = createdBy;
        DateCreated = dateCreated;
        ModifiedBy = modifiedBy;
        DateModified = dateModified;
        Version = version;        
    }
    
    /**
     * @return the _WorkgroupUserFunctionalArea_key
     */
    public String getWorkgroupUserFunctionalArea_key() {
        return _WorkgroupUserFunctionalArea_key;
    }

    /**
     * @param WorkgroupUserFunctionalArea_key the _WorkgroupUserFunctionalArea_key to set
     */
    public void setWorkgroupUserFunctionalArea_key(String WorkgroupUserFunctionalArea_key) {
        this._WorkgroupUserFunctionalArea_key = WorkgroupUserFunctionalArea_key;
    }

    /**
     * @return the _Privilege_key
     */
    public String getPrivilege_key() {
        return _Privilege_key;
    }

    /**
     * @param Privilege_key the _Privilege_key to set
     */
    public void setPrivilege_key(String Privilege_key) {
        this._Privilege_key = Privilege_key;
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
     * @return the _FunctionalArea_key
     */
    public String getFunctionalArea_key() {
        return _FunctionalArea_key;
    }

    /**
     * @param FunctionalArea_key the _FunctionalArea_key to set
     */
    public void setFunctionalArea_key(String FunctionalArea_key) {
        this._FunctionalArea_key = FunctionalArea_key;
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
    
}
