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
 * @author bas
 */
public class PhenotypeMouseLinkDTO {
    private String PhenotypeMouseLinkKey = "";
    private String PhenotypeKey = "";
    private String MouseKey = "";
    private String versionNum = "";

    /**
     * @return the PhenotypeMouseLinkKey
     */
    public String getPhenotypeMouseLinkKey() {
        return PhenotypeMouseLinkKey;
    }

    /**
     * @param PhenotypeMouseLinkKey the PhenotypeMouseLinkKey to set
     */
    public void setPhenotypeMouseLinkKey(String PhenotypeMouseLinkKey) {
        this.PhenotypeMouseLinkKey = PhenotypeMouseLinkKey;
    }

    /**
     * @return the PhenotypeKey
     */
    public String getPhenotypeKey() {
        return PhenotypeKey;
    }

    /**
     * @param PhenotypeKey the PhenotypeKey to set
     */
    public void setPhenotypeKey(String PhenotypeKey) {
        this.PhenotypeKey = PhenotypeKey;
    }

    /**
     * @return the MouseKey
     */
    public String getMouseKey() {
        return MouseKey;
    }

    /**
     * @param MouseKey the MouseKey to set
     */
    public void setMouseKey(String MouseKey) {
        this.MouseKey = MouseKey;
    }

    /**
     * @return the versionNum
     */
    public String getVersionNum() {
        return versionNum;
    }

    /**
     * @param versionNum the versionNum to set
     */
    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }
    
}
