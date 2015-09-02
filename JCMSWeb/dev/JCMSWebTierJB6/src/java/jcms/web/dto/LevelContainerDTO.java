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

package jcms.web.dto;

import jcms.integrationtier.dto.ContainerDTO;

/**
 *
 * @author mkamato
 */
public class LevelContainerDTO {
    
    private ContainerDTO cage = new ContainerDTO();
    private String style = "";
    private String strain = "";
    private String sex = "";
    private String position = "";
    private String type = "";
    private String displayStrain = "";

    /**
     * @return the cage
     */
    public ContainerDTO getCage() {
        return cage;
    }

    /**
     * @param cage the cage to set
     */
    public void setCage(ContainerDTO cage) {
        this.cage = cage;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the strain
     */
    public String getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(String strain) {
        this.strain = strain;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the displayStrain
     */
    public String getDisplayStrain() {
        return displayStrain;
    }

    /**
     * @param displayStrain the displayStrain to set
     */
    public void setDisplayStrain(String displayStrain) {
        this.displayStrain = displayStrain;
    }
}
