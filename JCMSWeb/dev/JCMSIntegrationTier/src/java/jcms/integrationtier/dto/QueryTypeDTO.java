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

import java.util.Date;
import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class QueryTypeDTO extends ITBaseDTO {
    private String _QueryType_key; 
    private String QueryType;
    private Boolean IsActive;
    private String SortOrder;
    private String _Workgroup_key;
    private Boolean IsDefault;
    private String CreatedBy;
    private String DateCreated;
    private String ModifiedBy;
    private String DateModified;
    private Boolean IsDeprecated;
    private String _VocabularySource_key;
    private String ElementID;
    private String Version;
    
    public QueryTypeDTO () {
        
    }

    public QueryTypeDTO (String queryTypeKey) {
        this.setQueryType_key(queryTypeKey);
    }
    /**
     * @return the _QueryType_key
     */
    public String getQueryType_key() {
        return _QueryType_key;
    }

    /**
     * @param QueryType_key the _QueryType_key to set
     */
    public void setQueryType_key(String QueryType_key) {
        this._QueryType_key = QueryType_key;
    }

    /**
     * @return the QueryType
     */
    public String getQueryType() {
        return QueryType;
    }

    /**
     * @param QueryType the QueryType to set
     */
    public void setQueryType(String QueryType) {
        this.QueryType = QueryType;
    }

    /**
     * @return the IsActive
     */
    public Boolean getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive the IsActive to set
     */
    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    /**
     * @return the SortOrder
     */
    public String getSortOrder() {
        return SortOrder;
    }

    /**
     * @param SortOrder the SortOrder to set
     */
    public void setSortOrder(String SortOrder) {
        this.SortOrder = SortOrder;
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
     * @return the IsDefault
     */
    public Boolean getIsDefault() {
        return IsDefault;
    }

    /**
     * @param IsDefault the IsDefault to set
     */
    public void setIsDefault(Boolean IsDefault) {
        this.IsDefault = IsDefault;
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
     * @return the IsDeprecated
     */
    public Boolean getIsDeprecated() {
        return IsDeprecated;
    }

    /**
     * @param IsDeprecated the IsDeprecated to set
     */
    public void setIsDeprecated(Boolean IsDeprecated) {
        this.IsDeprecated = IsDeprecated;
    }

    /**
     * @return the _VocabularySource_key
     */
    public String getVocabularySource_key() {
        return _VocabularySource_key;
    }

    /**
     * @param VocabularySource_key the _VocabularySource_key to set
     */
    public void setVocabularySource_key(String VocabularySource_key) {
        this._VocabularySource_key = VocabularySource_key;
    }

    /**
     * @return the ElementID
     */
    public String getElementID() {
        return ElementID;
    }

    /**
     * @param ElementID the ElementID to set
     */
    public void setElementID(String ElementID) {
        this.ElementID = ElementID;
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
    
    
}
