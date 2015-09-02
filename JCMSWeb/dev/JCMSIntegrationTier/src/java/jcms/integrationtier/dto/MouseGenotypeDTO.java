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

import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;

/**
 *
 * @author rkavitha
 */
public class MouseGenotypeDTO {
    
    private MouseEntity mouseEntity = new MouseEntity();
    private List<GenotypeEntity> genotypeList = new ArrayList<GenotypeEntity>();
    private int genotypeCnt = 0;
    private int genotypeFilterTotal = 0;
    private int genotypeMouseTotal = 0;
    private boolean showHideFlag = false;

    /**
     * @return the mouseEntity
     */
    public MouseEntity getMouseEntity() {
        return mouseEntity;
    }

    /**
     * @param mouseEntity the mouseEntity to set
     */
    public void setMouseEntity(MouseEntity mouseEntity) {
        this.mouseEntity = mouseEntity;
    }

    /**
     * @return the genotypeList
     */
    public List<GenotypeEntity> getGenotypeList() {
        return genotypeList;
    }

    /**
     * @param genotypeList the genotypeList to set
     */
    public void setGenotypeList(List<GenotypeEntity> genotypeList) {
        this.genotypeList = genotypeList;
    }

    /**
     * @return the genotypeCnt
     */
    public int getGenotypeCnt() {
        genotypeCnt = this.genotypeList.size();
        return genotypeCnt;
    }

    /**
     * @param genotypeCnt the genotypeCnt to set
     */
    public void setGenotypeCnt(int genotypeCnt) {
        this.genotypeCnt = genotypeCnt;
    }

    /**
     * @return the showHideFlag
     */
    public boolean isShowHideFlag() {
        return showHideFlag;
    }

    /**
     * @param showHideFlag the showHideFlag to set
     */
    public void setShowHideFlag(boolean showHideFlag) {
        this.showHideFlag = showHideFlag;
    }

    /**
     * @return the genotypeFilterTotal
     */
    public int getGenotypeFilterTotal() {
        return genotypeFilterTotal;
    }

    /**
     * @param genotypeFilterTotal the genotypeFilterTotal to set
     */
    public void setGenotypeFilterTotal(int genotypeFilterTotal) {
        this.genotypeFilterTotal = genotypeFilterTotal;
    }

    /**
     * @return the genotypeMouseTotal
     */
    public int getGenotypeMouseTotal() {
        return genotypeMouseTotal;
    }

    /**
     * @param genotypeMouseTotal the genotypeMouseTotal to set
     */
    public void setGenotypeMouseTotal(int genotypeMouseTotal) {
        this.genotypeMouseTotal = genotypeMouseTotal;
    }
}