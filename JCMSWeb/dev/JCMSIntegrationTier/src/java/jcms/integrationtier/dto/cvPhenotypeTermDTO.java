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
 * NOTE: isActive is defined as a string
 */
public class cvPhenotypeTermDTO {

    private String phenotypeTermName = "";
    private String phenotypeTermDescription = "";
    private String phenotypeTermKey = "";
    private String versionNum = "";
    private String isActive = "";
    
    @Override
    public boolean equals(Object object){
        if(object instanceof cvPhenotypeTermDTO){
            cvPhenotypeTermDTO phenotypeTerm = (cvPhenotypeTermDTO) object;
            if(getPhenotypeTermKey().equals(phenotypeTerm.getPhenotypeTermKey())){
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
     * @return the phenotypeTermName
     */
    public String getPhenotypeTermName() {
        return phenotypeTermName;
    }

    /**
     * @param phenotypeTermName the phenotypeTermName to set
     */
    public void setPhenotypeTermName(String phenotypeTermName) {
        this.phenotypeTermName = phenotypeTermName;
    }

    /**
     * @return the phenotypeTermDescription
     */
    public String getPhenotypeTermDescription() {
        return phenotypeTermDescription;
    }

    /**
     * @param phenotypeTermDescription the phenotypeTermDescription to set
     */
    public void setPhenotypeTermDescription(String phenotypeTermDescription) {
        this.phenotypeTermDescription = phenotypeTermDescription;
    }

    /**
     * @return the phenotypeTermKey
     */
    public String getPhenotypeTermKey() {
        return phenotypeTermKey;
    }

    /**
     * @param phenotypeTermKey the phenotypeTermKey to set
     */
    public void setPhenotypeTermKey(String phenotypeTermKey) {
        this.phenotypeTermKey = phenotypeTermKey;
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
