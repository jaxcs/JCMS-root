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
public class AdminCenterDTO {
    private String Centerkey;
    private String Center;
    private String IsActive;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private String Version;

    public AdminCenterDTO() {
        
    }
    
    public AdminCenterDTO(String centerkey,
                            String center,
                            String isActive,
                            String createdBy,
                            String dateCreated,
                            String modifiedBy,
                            String dateModified,
                            String version) {
        this.setCenterkey(centerkey);
        this.setCenter(center);
        this.setIsActive(isActive);
        this.setCreatedBy(createdBy);
        this.setDateCreated(dateCreated);
        this.setDateModified(dateModified);
        this.setModifiedBy(modifiedBy);
        this.setDateModified(dateModified);
        this.setVersion(version);
    };

    /**
     * @return the Centerkey
     */
    public String getCenterkey() {
        return Centerkey;
    }

    /**
     * @param Centerkey the Centerkey to set
     */
    public void setCenterkey(String Centerkey) {
        this.Centerkey = Centerkey;
    }

    /**
     * @return the Center
     */
    public String getCenter() {
        return Center;
    }

    /**
     * @param Center the Center to set
     */
    public void setCenter(String Center) {
        this.Center = Center;
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
    }
    

}
