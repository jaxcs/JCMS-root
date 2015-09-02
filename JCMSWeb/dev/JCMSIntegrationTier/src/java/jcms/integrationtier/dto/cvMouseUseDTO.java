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
 * @author mkamato
 */
public class cvMouseUseDTO {
    
    private String mouseUse = "";
    private String useDescription = "";
    private String mouseUseKey = "";
    private String version = "";
    private String d1Caption = "D1";
    private String d2Caption = "D2";
    private String d3Caption = "D3";
    private String d4Caption = "D4";
    private String d5Caption = "D5";
    private String d6Caption = "D6";
    private String d7Caption = "D7";    
    private String d8Caption = "D8";
    private String d9Caption = "D9";
    private String d10Caption = "D10";
    private String isActive = "";
    
    @Override
    public boolean equals(Object object){
        if(object instanceof cvMouseUseDTO){
            cvMouseUseDTO use = (cvMouseUseDTO) object;
            if(mouseUseKey.equals(use.getMouseUseKey())){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * @return the mouseUse
     */
    public String getMouseUse() {
        return mouseUse;
    }

    /**
     * @param mouseUse the mouseUse to set
     */
    public void setMouseUse(String mouseUse) {
        this.mouseUse = mouseUse;
    }

    /**
     * @return the useDescription
     */
    public String getUseDescription() {
        return useDescription;
    }

    /**
     * @param useDescription the useDescription to set
     */
    public void setUseDescription(String useDescription) {
        this.useDescription = useDescription;
    }

    /**
     * @return the mouseUseKey
     */
    public String getMouseUseKey() {
        return mouseUseKey;
    }

    /**
     * @param mouseUseKey the mouseUseKey to set
     */
    public void setMouseUseKey(String mouseUseKey) {
        this.mouseUseKey = mouseUseKey;
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

    /**
     * @return the d1Caption
     */
    public String getD1Caption() {
        return d1Caption;
    }

    /**
     * @param d1Caption the d1Caption to set
     */
    public void setD1Caption(String d1Caption) {
        this.d1Caption = d1Caption;
    }

    /**
     * @return the d2Caption
     */
    public String getD2Caption() {
        return d2Caption;
    }

    /**
     * @param d2Caption the d2Caption to set
     */
    public void setD2Caption(String d2Caption) {
        this.d2Caption = d2Caption;
    }

    /**
     * @return the d3Caption
     */
    public String getD3Caption() {
        return d3Caption;
    }

    /**
     * @param d3Caption the d3Caption to set
     */
    public void setD3Caption(String d3Caption) {
        this.d3Caption = d3Caption;
    }

    /**
     * @return the d4Caption
     */
    public String getD4Caption() {
        return d4Caption;
    }

    /**
     * @param d4Caption the d4Caption to set
     */
    public void setD4Caption(String d4Caption) {
        this.d4Caption = d4Caption;
    }

    /**
     * @return the d5Caption
     */
    public String getD5Caption() {
        return d5Caption;
    }

    /**
     * @param d5Caption the d5Caption to set
     */
    public void setD5Caption(String d5Caption) {
        this.d5Caption = d5Caption;
    }

    /**
     * @return the d6Caption
     */
    public String getD6Caption() {
        return d6Caption;
    }

    /**
     * @param d6Caption the d6Caption to set
     */
    public void setD6Caption(String d6Caption) {
        this.d6Caption = d6Caption;
    }

    /**
     * @return the d7Caption
     */
    public String getD7Caption() {
        return d7Caption;
    }

    /**
     * @param d7Caption the d7Caption to set
     */
    public void setD7Caption(String d7Caption) {
        this.d7Caption = d7Caption;
    }

    /**
     * @return the d8Caption
     */
    public String getD8Caption() {
        return d8Caption;
    }

    /**
     * @param d8Caption the d8Caption to set
     */
    public void setD8Caption(String d8Caption) {
        this.d8Caption = d8Caption;
    }

    /**
     * @return the d9Caption
     */
    public String getD9Caption() {
        return d9Caption;
    }

    /**
     * @param d9Caption the d9Caption to set
     */
    public void setD9Caption(String d9Caption) {
        this.d9Caption = d9Caption;
    }

    /**
     * @return the d10Caption
     */
    public String getD10Caption() {
        return d10Caption;
    }

    /**
     * @param d10Caption the d10Caption to set
     */
    public void setD10Caption(String d10Caption) {
        this.d10Caption = d10Caption;
    }

    /**
     * @return the isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
