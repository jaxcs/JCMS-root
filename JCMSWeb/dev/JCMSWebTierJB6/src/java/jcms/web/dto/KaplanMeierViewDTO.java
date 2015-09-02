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

import org.primefaces.model.DualListModel;

/**
 *
 * @author mkamato
 */
public class KaplanMeierViewDTO {
    
    private String title = "";
    private String color = "";
    private DualListModel useSchedulesModel = new DualListModel();
    private DualListModel ownersModel = new DualListModel();
    private DualListModel strainsModel = new DualListModel();
    private DualListModel codModel = new DualListModel();
    private DualListModel dietModel = new DualListModel();
    private DualListModel geneModel = new DualListModel();
    private DualListModel roomModel = new DualListModel();
    private DualListModel allele1Model = new DualListModel();
    private DualListModel allele2Model = new DualListModel();
    private DualListModel lifeStatusesModel = new DualListModel();

    /**
     * @return the useSchedulesModel
     */
    public DualListModel getUseSchedulesModel() {
        return useSchedulesModel;
    }

    /**
     * @param useSchedulesModel the useSchedulesModel to set
     */
    public void setUseSchedulesModel(DualListModel useSchedulesModel) {
        this.useSchedulesModel = useSchedulesModel;
    }

    /**
     * @return the ownersModel
     */
    public DualListModel getOwnersModel() {
        return ownersModel;
    }

    /**
     * @param ownersModel the ownersModel to set
     */
    public void setOwnersModel(DualListModel ownersModel) {
        this.ownersModel = ownersModel;
    }

    /**
     * @return the strainsModel
     */
    public DualListModel getStrainsModel() {
        return strainsModel;
    }

    /**
     * @param strainsModel the strainsModel to set
     */
    public void setStrainsModel(DualListModel strainsModel) {
        this.strainsModel = strainsModel;
    }

    /**
     * @return the lifeStatusesModel
     */
    public DualListModel getLifeStatusesModel() {
        return lifeStatusesModel;
    }

    /**
     * @param lifeStatusesModel the lifeStatusesModel to set
     */
    public void setLifeStatusesModel(DualListModel lifeStatusesModel) {
        this.lifeStatusesModel = lifeStatusesModel;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the dietModel
     */
    public DualListModel getDietModel() {
        return dietModel;
    }

    /**
     * @param dietModel the dietModel to set
     */
    public void setDietModel(DualListModel dietModel) {
        this.dietModel = dietModel;
    }

    /**
     * @return the geneModel
     */
    public DualListModel getGeneModel() {
        return geneModel;
    }

    /**
     * @param geneModel the geneModel to set
     */
    public void setGeneModel(DualListModel geneModel) {
        this.geneModel = geneModel;
    }

    /**
     * @return the roomModel
     */
    public DualListModel getRoomModel() {
        return roomModel;
    }

    /**
     * @param roomModel the roomModel to set
     */
    public void setRoomModel(DualListModel roomModel) {
        this.roomModel = roomModel;
    }

    /**
     * @return the codModel
     */
    public DualListModel getCodModel() {
        return codModel;
    }

    /**
     * @param codModel the codModel to set
     */
    public void setCodModel(DualListModel codModel) {
        this.codModel = codModel;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the allele1Model
     */
    public DualListModel getAllele1Model() {
        return allele1Model;
    }

    /**
     * @param allele1Model the allele1Model to set
     */
    public void setAllele1Model(DualListModel allele1Model) {
        this.allele1Model = allele1Model;
    }

    /**
     * @return the allele2Model
     */
    public DualListModel getAllele2Model() {
        return allele2Model;
    }

    /**
     * @param allele2Model the allele2Model to set
     */
    public void setAllele2Model(DualListModel allele2Model) {
        this.allele2Model = allele2Model;
    }
}
