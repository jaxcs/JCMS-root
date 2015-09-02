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
public class AdminFunctionalAreaDTO {
    private String _FunctionalArea_key;
    private String FunctionalArea;
    private String Description;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;

    public AdminFunctionalAreaDTO() {
        
    }
    
    public AdminFunctionalAreaDTO (
        String functionalAreaKey,
        String functionalArea,
        String description,
        String createdBy,
        String dateCreated,
        String modifiedBy,
        String dateModified,
        String version) {
        
        _FunctionalArea_key = functionalAreaKey;
        FunctionalArea = functionalArea;
        Description = description;
        CreatedBy = createdBy;
        DateCreated = dateCreated;
        ModifiedBy = modifiedBy;
        DateModified = dateModified;
        Version = version;
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
     * @return the FunctionalArea
     */
    public String getFunctionalArea() {
        return FunctionalArea;
    }

    /**
     * @param FunctionalArea the FunctionalArea to set
     */
    public void setFunctionalArea(String FunctionalArea) {
        this.FunctionalArea = FunctionalArea;
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
