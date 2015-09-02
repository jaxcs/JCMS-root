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

import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class AdminGeneClassDTO extends ITBaseDTO{
    private String geneClassKey = "";
    private String geneClass = "";
    private String description = "";
    private String version = "";
    
    public AdminGeneClassDTO() {
        
    }

    public AdminGeneClassDTO(
        String geneClassKey,
        String geneClass,
        String description,
        String version) {
        this.setGeneClassKey(geneClassKey);
        this.setGeneClass(geneClass);
        this.setDescription(description);
        this.setVersion(version);
    }

    /**
     * @return the geneClassKey
     */
    public String getGeneClassKey() {
        return geneClassKey;
    }

    /**
     * @param geneClassKey the geneClassKey to set
     */
    public void setGeneClassKey(String geneClassKey) {
        this.geneClassKey = geneClassKey;
    }

    /**
     * @return the geneClass
     */
    public String getGeneClass() {
        return geneClass;
    }

    /**
     * @param geneClass the geneClass to set
     */
    public void setGeneClass(String geneClass) {
        this.geneClass = geneClass;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
