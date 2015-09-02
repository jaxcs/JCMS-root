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

package jcms.web.cagecards;

/**
 *
 * @author mkamato
 */
import java.io.DataOutput;
import java.io.DataOutputStream;
import javax.faces.model.SelectItem;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.cardtypes.FormatType;
import jcms.integrationtier.dto.CageCardDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardtypes.Orientation;
import jcms.cagecard.dtos.returnDTO;
import jcms.integrationtier.jcmsWeb.*;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.common.SelectItemWrapper;
import jcms.web.base.WTBaseBackingBean;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.service.JCMSWebAppService;
import jcms.integrationtier.dao.CageCardDAO;

public class CustomCageCardBean extends WTBaseBackingBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private SelectItemWrapper theWrapper = new SelectItemWrapper();

    private ArrayList<SelectItem> detailFields = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> matingFields = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> weanFields = new ArrayList<SelectItem>();
    
    private ArrayList<SelectItem> rowPortionFields = new ArrayList<SelectItem>();
    
    private ArrayList<SelectItem> displayFields = getDetailFields();
    private ArrayList<CustomCardField> customCard = new ArrayList<CustomCardField>();
    
    private ArrayList<SelectItem> displayCards = new ArrayList<SelectItem>();
    
    private QueryDefinitionEntity theLoadCard;
    
    ServiceFacadeLocal sfl = new BusinessTierPortal().getServiceFacadeLocal();
    
    private CustomCardField editField = new CustomCardField();
    boolean flag = true;
    boolean orientationChange = false;
    Integer cardKey;
    boolean saved = false;
    String rowColor = "";
    
    //field details
    private String fieldName;
    private String rowPortion;
    private String numberOfRows = "1";
    private boolean labels = true;
    private boolean barCode;
    private boolean borders = true;
    private String customText = "";
    
    //card details
    private String cardName;
    private String cardTypeMask = "detail";
    private String orientation = "portrait";
    private String printSettings;
    private int rowsAvailable= 30;
    
    //edit field details
    int editFieldIndex;
    private String editFieldName;
    private String editNumberOfRows;
    private String editRowPortion;
    private boolean editLabels;
    private boolean editBarCode;
    private boolean editBorders;
    private String editCustomText = "";
    private boolean addField = false;
    
    //insert field details
    private int insertFieldIndex;
    private String insertFieldName;
    private String insertNumberOfRows = "1";
    private String insertRowPortion;
    private boolean insertLabels = true;
    private boolean insertBarCode;
    private boolean insertBorders = true;
    private String insertCustomText = "";

    //workgroups
    private ArrayList<WorkgroupEntity> workgroups = (ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst");
    private WorkgroupEntity[] selectedWorkgroups = new WorkgroupEntity[workgroups.size()];
    
    public void autosetLabels(){
        customText = fieldName;
        for(SelectItem si: displayFields){
            if(si.getValue().equals(fieldName)){
                if(si.getValue().equals("blank")){
                    customText = "";
                }
                else{
                    if(customText != null && !customText.equals("")){
                        customText = si.getLabel() + ": ";
                    }
                    else{
                        customText = si.getLabel();
                    }
                }
                return;
            }
        }
    }
    
    //Set fields in edit field modal panel to proper values
    public void autosetEditLabels(){
        editCustomText = editFieldName;
        for(SelectItem si: displayFields){
            if(si.getValue().equals(editFieldName)){
                if(si.getValue().equals("blank")){
                    editCustomText = "";
                }
                else{
                    if(editCustomText != null){
                        editCustomText = si.getLabel() + ": ";
                    }
                    else{
                        editCustomText = si.getLabel();
                    }
                }
                return;
            }
        }
    }
    
    //set fields in addfield Modal Panel to proper values
    public void autosetInsertLabels(){
        insertCustomText = insertFieldName;
        for(SelectItem si: displayFields){

            if(si.getValue().equals(insertFieldName)){
                if(si.getValue().equals("blank")){
                    insertCustomText = "";
                }
                else{
                    if(insertCustomText != null){
                        insertCustomText = si.getLabel() + ": ";
                    }
                    else{
                        insertCustomText = si.getLabel();
                    }
                }
                return;
            }
        }
    }
    
    //change drop downs to correct fields for set card type
    public void changeDropDownVals(){
        if(cardTypeMask.equals("detail")){
            setDisplayFields(getDetailFields());
        }
        else if(cardTypeMask.equals("mating")){
            setDisplayFields(getMatingFields());
        }
        else{
            setDisplayFields(getWeanFields());
        }
        setRowColors();
    }
    
    public void changeRowPortions(){
        setRowPortionFields(getRowPortionFields());
    }
    
    public ArrayList<SelectItem> getRowPortionFields(){
        setRowPortionFields(new ArrayList<SelectItem>());
        if(orientation.equals("landscape")){
            rowPortionFields.add(new SelectItem("8","8%"));
            rowPortionFields.add(new SelectItem("12","12%"));
            rowPortionFields.add(new SelectItem("16","16%"));
            rowPortionFields.add(new SelectItem("25","25%"));
            rowPortionFields.add(new SelectItem("33","33%"));
            rowPortionFields.add(new SelectItem("37","37%"));
            rowPortionFields.add(new SelectItem("42","42%"));
            rowPortionFields.add(new SelectItem("50","50%"));
            rowPortionFields.add(new SelectItem("58","58%"));
            rowPortionFields.add(new SelectItem("67","67%"));
            rowPortionFields.add(new SelectItem("75","75%"));
            rowPortionFields.add(new SelectItem("83","83%"));
            rowPortionFields.add(new SelectItem("87","87%"));
            rowPortionFields.add(new SelectItem("91","91%"));
            rowPortionFields.add(new SelectItem("100","100%"));
        }
        else{
            rowPortionFields.add(new SelectItem("16","16%"));
            rowPortionFields.add(new SelectItem("25","25%"));
            rowPortionFields.add(new SelectItem("33","33%"));
            rowPortionFields.add(new SelectItem("50","50%"));
            rowPortionFields.add(new SelectItem("67","67%"));
            rowPortionFields.add(new SelectItem("75","75%"));
            rowPortionFields.add(new SelectItem("100","100%"));
        }
        return rowPortionFields;
    }

    public ArrayList<SelectItem> getDetailFields() {
        detailFields = new ArrayList<SelectItem>();
        detailFields.add(new SelectItem("", ""));
        detailFields.add(new SelectItem("activationDate", "Activation Date"));
        detailFields.add(new SelectItem("birthdate", "Birthdate"));
        detailFields.add(new SelectItem("blank", "blank"));
        detailFields.add(new SelectItem("breedingStatus", "Breeding Status"));
        detailFields.add(new SelectItem("CageID", "Cage ID"));
        detailFields.add(new SelectItem("CageName", "Cage Name"));
        detailFields.add(new SelectItem("coatColor", "Coat Color"));
        detailFields.add(new SelectItem("comment", "Comment"));
        detailFields.add(new SelectItem("containerComment", "Container Comment"));
        detailFields.add(new SelectItem("containerStatus", "Container Status"));
        detailFields.add(new SelectItem("detailCardNote", "Detail Card Note"));
        detailFields.add(new SelectItem("diet", "Diet"));
        detailFields.add(new SelectItem("generation", "Generation"));
        detailFields.add(new SelectItem("genotype", "Genotype"));
        detailFields.add(new SelectItem("lifeStatus", "Life Status"));
        detailFields.add(new SelectItem("litterID", "Litter ID"));
        detailFields.add(new SelectItem("matingID", "Mating ID"));
        detailFields.add(new SelectItem("ID", "Mouse ID"));
        detailFields.add(new SelectItem("newTag", "New Tag"));
        detailFields.add(new SelectItem("notes", "Notes"));
        detailFields.add(new SelectItem("origin", "Origin"));
        detailFields.add(new SelectItem("Owner", "Owner"));
        detailFields.add(new SelectItem("PIName", "PI Name"));
        detailFields.add(new SelectItem("PIPhone", "PI Phone"));
        detailFields.add(new SelectItem("protocol", "Protocol"));
        detailFields.add(new SelectItem("room", "Room"));
        detailFields.add(new SelectItem("sex", "Sex"));
        detailFields.add(new SelectItem("jrNum", "Stock Number"));
        detailFields.add(new SelectItem("strainName", "Strain Name"));
        detailFields.add(new SelectItem("weanDate", "Wean Date"));
        return this.detailFields;
    }

    public ArrayList<SelectItem> getMatingFields(){
        matingFields= new ArrayList<SelectItem>();
        matingFields.add(new SelectItem("", ""));
        
        matingFields.add(new SelectItem("CageID", "Cage ID"));
        matingFields.add(new SelectItem("CageName", "Cage Name"));
        matingFields.add(new SelectItem("activationDate", "Activation Date"));
        matingFields.add(new SelectItem("Owner", "Owner"));
        matingFields.add(new SelectItem("blank", "blank"));
        matingFields.add(new SelectItem("PIName", "PI Name"));
        matingFields.add(new SelectItem("PIPhone", "PI Phone"));
        matingFields.add(new SelectItem("containerComment", "Container Comment"));
        matingFields.add(new SelectItem("activationDate", "Activation Date"));
        matingFields.add(new SelectItem("containerStatus", "Cage Status"));
        matingFields.add(new SelectItem("room", "Room"));
        
        matingFields.add(new SelectItem("dam1BD", "Dam1 Birthdate"));
        matingFields.add(new SelectItem("dam1Generation", "Dam1 Generation"));
        matingFields.add(new SelectItem("dam1Genotype", "Dam1 Genotype"));
        matingFields.add(new SelectItem("dam1ID", "Dam1 ID"));
        matingFields.add(new SelectItem("dam1jrNum", "Dam1 Stock #"));
        matingFields.add(new SelectItem("dam1LitterID", "Dam1 Litter ID"));
        matingFields.add(new SelectItem("dam1MatingID", "Dam1 Mating ID"));
        matingFields.add(new SelectItem("dam1NewTag", "Dam1 New Tag"));
        matingFields.add(new SelectItem("dam1Protocol", "Dam1 Protocol"));
        matingFields.add(new SelectItem("dam1StrainName", "Dam1 StrainName"));
        
        
        matingFields.add(new SelectItem("dam2BD", "Dam2 Birthdate"));
        matingFields.add(new SelectItem("dam2Generation", "Dam2 Generation"));
        matingFields.add(new SelectItem("dam2Genotype", "Dam2 Genotype"));
        matingFields.add(new SelectItem("dam2ID", "Dam2 ID"));
        matingFields.add(new SelectItem("dam2jrNum", "Dam2 Stock #"));
        matingFields.add(new SelectItem("dam2LitterID", "Dam2 Litter ID"));
        matingFields.add(new SelectItem("dam2MatingID", "Dam2 Mating ID"));
        matingFields.add(new SelectItem("dam2NewTag", "Dam2 New Tag"));
        matingFields.add(new SelectItem("dam2Protocol", "Dam2 Protocol"));
        matingFields.add(new SelectItem("dam2StrainName", "Dam2 StrainName"));
                
        
        matingFields.add(new SelectItem("sireBD", "Sire Birthdate"));
        matingFields.add(new SelectItem("sireGeneration", "Sire Generation"));
        matingFields.add(new SelectItem("sireGenotype", "Sire Genotype"));
        matingFields.add(new SelectItem("sireID", "Sire ID"));
        matingFields.add(new SelectItem("sirejrNum", "Sire Stock #"));
        matingFields.add(new SelectItem("sireLitterID", "Sire Litter ID"));
        matingFields.add(new SelectItem("sireMatingID", "Sire Mating ID"));
        matingFields.add(new SelectItem("sireNewTag", "Sire New Tag"));
        matingFields.add(new SelectItem("sireProtocol", "Sire Protocol"));
        matingFields.add(new SelectItem("sireStrainName","Sire StrainName" ));
        
        matingFields.add(new SelectItem("notes", "Notes"));
        matingFields.add(new SelectItem("matingComment", "Mating Comment"));
        matingFields.add(new SelectItem("weanNote", "Wean Note"));
 
        matingFields.add(new SelectItem("numMale", "Number Male"));
        matingFields.add(new SelectItem("numFemale", "Number Female"));
        
        
        matingFields.add(new SelectItem("litterBirthDate", "Litter birthdate"));
        matingFields.add(new SelectItem("LitterStrain", "Litter Strain"));
        matingFields.add(new SelectItem("LitterGeneration", "Litter Generation"));
        matingFields.add(new SelectItem("litterID", "Litter ID"));
        
        matingFields.add(new SelectItem("MatingID", "Mating ID"));
        
        matingFields.add(new SelectItem("totalBorn", "Pup Count"));
        
        
        
        return this.matingFields;
    }
    
    public ArrayList<SelectItem> getWeanFields(){
        weanFields = new ArrayList<SelectItem>();
        weanFields.add(new SelectItem("", ""));
        weanFields.add(new SelectItem("activationDate", "Activation Date"));
        weanFields.add(new SelectItem("birthDate", "Mouse Birthdate"));
        weanFields.add(new SelectItem("blank", "blank"));
        weanFields.add(new SelectItem("breedingStatus", "Breeding Status"));
        weanFields.add(new SelectItem("cageComment", "Cage Comment"));
        weanFields.add(new SelectItem("CageID", "Cage ID"));
        weanFields.add(new SelectItem("CageName", "Cage Name"));
        weanFields.add(new SelectItem("containerStatus", "Cage Status"));
        weanFields.add(new SelectItem("coatColor", "Coat Color"));
        weanFields.add(new SelectItem("comment", "Mouse Comment"));
        weanFields.add(new SelectItem("dam1Genotype", "Dam1 Genotype"));
        weanFields.add(new SelectItem("dam1Strain", "Dam1 Strain"));
        weanFields.add(new SelectItem("dam1ID", "Dam1 ID"));
        weanFields.add(new SelectItem("dam2Genotype", "Dam2 Genotype"));
        weanFields.add(new SelectItem("dam2ID", "Dam2 ID"));
        weanFields.add(new SelectItem("dam2Strain", "Dam2 Strain"));
        weanFields.add(new SelectItem("generation", "Generation"));
        weanFields.add(new SelectItem("genotype", "Genotype"));
        weanFields.add(new SelectItem("litterBirthDate", "Litter Birthdate"));
        weanFields.add(new SelectItem("litterID","Litter ID"));
        weanFields.add(new SelectItem("status", "Litter Status"));
        weanFields.add(new SelectItem("matingID", "Mating ID"));
        weanFields.add(new SelectItem("ID", "Mouse ID"));
        weanFields.add(new SelectItem("newTag", "New Tag"));
        weanFields.add(new SelectItem("notes", "Notes"));
        weanFields.add(new SelectItem("numFemale", "Number Female"));
        weanFields.add(new SelectItem("numMale", "Number Male"));
        weanFields.add(new SelectItem("Owner", "Owner"));
        weanFields.add(new SelectItem("PIName", "PI Name"));
        weanFields.add(new SelectItem("PIPhone", "PI Phone"));
        weanFields.add(new SelectItem("protocol", "Protocol"));
        weanFields.add(new SelectItem("jrNum", "Stock Number"));
        weanFields.add(new SelectItem("room", "Room"));
        weanFields.add(new SelectItem("sex", "Sex"));
        weanFields.add(new SelectItem("sireGenotype", "Sire Genotype"));
        weanFields.add(new SelectItem("sireID", "Sire ID"));
        weanFields.add(new SelectItem("sireStrain", "Sire Strain"));
        weanFields.add(new SelectItem("statusDate", "Status Date"));
        weanFields.add(new SelectItem("strainName", "Strain"));
        weanFields.add(new SelectItem("totalBorn", "Total Born"));
        weanFields.add(new SelectItem("tagDate", "Tag Date"));
        weanFields.add(new SelectItem("weanDate", "Wean Date"));
        
        
        return this.weanFields;
    }
    
    public boolean contains(ArrayList<SelectItem> array, String value){
        for(SelectItem si:array){
            if(si.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
    
    public void setRowColors(){
        for(CustomCardField ccf:customCard){
            if(contains(displayFields,ccf.getFieldName())){
                ccf.setColor("blankRowBackground");
            }
            else{
                ccf.setColor("badRowBackground");
            }
        }
    }
    
    public void updateDetails() {
        addField = true;
        validateField(numberOfRows,rowPortion);
        if (!flag) {
            CustomCardField theField = new CustomCardField();

            theField.setBarCoded(barCode);
            theField.setFieldName(fieldName);
            theField.setLabeled(labels);
            theField.setNumberOfRows(Integer.parseInt(numberOfRows));
            theField.setRowPortion(rowPortion);
            theField.setBorders(borders);
            theField.setCustomText(customText);

            getCustomCard().add(theField);
            
            
            cardCalculate();
            
            numberOfRows="1";
        }
        customText = "";
        addField = false;
    }
    
    public void insertField(){
        validateField(insertNumberOfRows, insertRowPortion);
        if(!flag){
            CustomCardField theField = new CustomCardField();
            
            theField.setBarCoded(insertBarCode);
            theField.setFieldName(insertFieldName);
            theField.setLabeled(insertLabels);
            theField.setNumberOfRows(Integer.parseInt(insertNumberOfRows));
            theField.setRowPortion(insertRowPortion);
            theField.setBorders(insertBorders);
            theField.setCustomText(insertCustomText);

            
            getCustomCard().add(insertFieldIndex, theField);
            cardCalculate();
            
            if(rowsAvailable < 0){
                getCustomCard().remove(theField);
                this.addToMessageQueue("Error: You do not have enough room to insert the requested field.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Not enough room to insert the requested field to the card."));
            }
            cardCalculate();
        }       
        insertCustomText = "";
    }
    
    public void startInsert(){
        insertFieldIndex = this.getKey("fieldIndex");
    }
    
    public void barcodeWarningHelper(){
        if(barCode){
            barCodeWarning(barCode,rowPortion);
        }
    }
    
    public void deleteField() {
        int fieldIndex = this.getKey("fieldIndex");
        customCard.remove(fieldIndex);
        cardCalculate();
    }

    public void editTheField() {
        editFieldIndex = this.getKey("fieldIndex");
        
        setEditField(customCard.get(editFieldIndex));
        
        setEditFieldName(editField.getFieldName());
        setEditRowPortion(new Integer(editField.getRowPortion()).toString());
        seteditNumberOfRows(new Integer(editField.getNumberOfRows()).toString());
        setEditLabels(editField.isLabeled());
        setEditBarCode(editField.isBarCoded());
        setEditBorders(editField.isBorders());
        setEditCustomText(editField.getCustomText());
        
    }
    
    public void onOrientationChange(){
        changeRowPortions();
        cardCalculate();
    }
    
    
    public void cardCalculate(){
        int tempCardSize = 0;
        double tempRowSize = 0;
        boolean barCodeRow = false;
        

        for (CustomCardField c : customCard) {
            barCodeRow = false;
            
            if(tempCardSize == 30){
                tempCardSize = 31;
                break;
            }
            if(tempCardSize == 17 && orientation.equals("landscape")){
                tempCardSize = 18;
                break;
            }
            if (c.isBarCoded()) {
                barCodeRow = true;
            }
            double span = Integer.parseInt(c.getRowPortion());//fractionToPercent(c.getRowPortion());
            //field doesn't fit in row
            if ((span + tempRowSize) > 100) {
                if (barCodeRow) {
                    tempCardSize = tempCardSize + 2;
                } 
                else {
                    tempCardSize++;
                }
                if (c.getRowPortion().equals("100")) {
                    if (barCodeRow && (c.getNumberOfRows() == 1)) {
                        tempCardSize = tempCardSize + 2;
                    } 
                    else {
                        tempCardSize = tempCardSize + c.getNumberOfRows();
                    }
                    tempRowSize = 0;
                } 
                else {
                    tempRowSize = span;
                }
            } //field completes row
            else if ((span + tempRowSize) == 100) {
                if (c.getRowPortion().equals("100")) {
                    if (barCodeRow && (c.getNumberOfRows() == 1)) {
                        tempCardSize = tempCardSize + 2;
                    } 
                    else {
                        tempCardSize = tempCardSize + c.getNumberOfRows();
                    }
                } 
                else {
                    if (barCodeRow) {
                        tempCardSize = tempCardSize + 2;
                    } 
                    else {
                        tempCardSize++;
                    }
                }
                tempRowSize = 0;
            } //field fits in row, but doesn't complete it.
            else {
                tempRowSize = span + tempRowSize;
            }
        }

        if(orientation.equals("landscape")){
            setRowsAvailable(17 - tempCardSize);
        }
        else{
            setRowsAvailable(30 - tempCardSize);
        }
        
        if(getRowsAvailable()<0 && addField){
            customCard.remove(customCard.size()-1);
            this.addToMessageQueue("Error: You do not have enough room to add the requested field.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Not enough room to add the requested field to the card."));
            addField = false;
            cardCalculate();
        }
        else if (addField){
            this.addToMessageQueue("You have successfully added the field: " + fieldName,
                    FacesMessage.SEVERITY_INFO);
        }
    }
    
    public double fractionToPercent(String fraction){
        double value;
        if(fraction.equals("1/12")){
            value = 8.3333333; //2/24
        }
        else if(fraction.equals("1/8")){
            value = 12.5; //3/24
        }
        else if(fraction.equals("1/6")){
            value = 16.6666667; //4/24
        }
        else if(fraction.equals("1/4")){
            value = 25; //6/24
        }
        else if(fraction.equals("1/3")){
            value = 33.3333333; //8/24
        }
        else if(fraction.equals("3/8")){
            value = 37.5; //9/24
        }
        else if(fraction.equals("5/12")){
            value = 41.6666667; //10/24
        }
        else if(fraction.equals("1/2")){
            value = 50;
        }
        else if(fraction.equals("7/12")){
            value = 58.3333333;
        }
        else if(fraction.equals("2/3")){
            value = 66.6666667;
        }
        else if(fraction.equals("3/4")){
            value = 75;
        }
        else if(fraction.equals("5/6")){
            value = 83.3333333;
        }
        else if(fraction.equals("7/8")){
            value = 87.5;
        }
        else if(fraction.equals("11/12")){
            value = 91.6666667;
        }
        else {
            value = 100;
        }
        
        return value;
    }
    
    public void barCodeWarning(boolean barcoded, String rowportion){
        if(barcoded && (rowportion.equals("33") || rowportion.equals("25"))){
            this.addToMessageQueue("Warning: It is recommended that barcoded values take up at least 50% of the row to "
                    + "guarantee readability.",
                    FacesMessage.SEVERITY_WARN);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "It is recommended that barcoded values take up at least 50% of the row to "
                    + "guarantee readability."));
        }
    }

    public void changeFields() {
        
        CustomCardField temp = new CustomCardField();
        temp.setFieldName(customCard.get(editFieldIndex).getFieldName());
        temp.setBarCoded(customCard.get(editFieldIndex).isBarCoded());
        temp.setLabeled(customCard.get(editFieldIndex).isLabeled());
        temp.setNumberOfRows(customCard.get(editFieldIndex).getNumberOfRows());
        temp.setRowPortion(customCard.get(editFieldIndex).getRowPortion());
        temp.setBorders(customCard.get(editFieldIndex).isBorders());
        temp.setCustomText(customCard.get(editFieldIndex).getCustomText());
        
        validateField(editNumberOfRows, editRowPortion);
        if (!flag) {
            customCard.get(editFieldIndex).setBarCoded(editBarCode);
            customCard.get(editFieldIndex).setFieldName(editFieldName);
            customCard.get(editFieldIndex).setLabeled(editLabels);
            customCard.get(editFieldIndex).setRowPortion(editRowPortion);
            customCard.get(editFieldIndex).setNumberOfRows(Integer.parseInt(getEditNumberOfRows()));
            customCard.get(editFieldIndex).setBorders(editBorders);
            customCard.get(editFieldIndex).setCustomText(editCustomText);
        }
        cardCalculate();
        if(getRowsAvailable() < 0){
            customCard.set(editFieldIndex,temp);
            this.addToMessageQueue("Error: You do not have enough room to modify the requested field.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Not enough room to modify the requested field to the card."));
            cardCalculate();
        }
        editCustomText = "";
        setRowColors();
    }

    public void clear() {
        customCard = new ArrayList<CustomCardField>();
        cardCalculate();
    }

    public void validateField(String numberOfRows, String rowPortion) {
        flag = false;
        if(!rowPortion.equals("100") && !numberOfRows.equals("1")){
            flag = true;
            this.addToMessageQueue("Error: To span multiple rows the field must occupy 100% of the row",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "To span multiple rows the field must occupy 100% of the row"));
        }
    }
    
    public void multiRowWarning(){
        //only display warning if rowPortion isn't 100 and
        if(!rowPortion.equals("100") && !numberOfRows.equals("1")){
            this.addToMessageQueue("Warning: To span multiple rows the field must occupy 100% of the row",
                        FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void preview(){

        CageCardType theType;
        FormatType theFormat;
        
        //set card type
        if (cardTypeMask.equals("detail")) {
            theType = CageCardType.Detail;
        } else if (cardTypeMask.equals("wean")) {
            theType = CageCardType.Wean;
        } else {
            theType = CageCardType.Mating;
        }

        //set format
        if (printSettings.equals("3x5")) {
            theFormat = FormatType.threeByFive;
        } else if (printSettings.equals("right")) {
            theFormat = FormatType.alignRight;
        } else if (printSettings.equals("left")) {
            theFormat = FormatType.alignLeft;
        } else {
            theFormat = FormatType.alignCenter;
        }
        
        
            
        CageCardDTO temp = new CageCardDTO();
        
        if(orientation.equals("portrait")){
            temp.setOrientation(Orientation.portrait);
        }
        else{
            temp.setOrientation(Orientation.landscape);
        }
        temp.setCageCardType(theType);
        temp.setCustomCard(customCard);
        temp.setFormat(theFormat);
        temp.setPenID("-100");
        temp.setQuantity("single");
        
        downloadCageCardAction(temp);
    }
    
    public void downloadCageCardAction(CageCardDTO DTO) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                                
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                returnDTO theReturnDTO = sfl.generateCageCard(DTO, hsr);
                
                
                writeBytes(theReturnDTO,hsr,context);
                
            }
        } catch (Exception ex) {
            System.out.println("Exception in cage card bean: " + ex);
        }

    }
    
    private void writeBytes(returnDTO theReturnDTO, HttpServletResponse hsr, FacesContext context) throws Exception {
        //no error
        byte[] bytes = theReturnDTO.getBytes();
        if (bytes != null && bytes.length != 0) {
            DataOutput output = new DataOutputStream(hsr.getOutputStream());
            hsr.setContentLength(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                output.writeByte(bytes[i]);
            }
        } else {
            this.addToMessageQueue(theReturnDTO.getErrorMessage(),
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",""));
        }
        //Tell JSF to skip the remaining phases of the lifecycle 
        context.responseComplete();
    }
        
    public void saveCageCard(){
        CageCardType theType;
        FormatType theFormat;
        
        UserEntity user;

        Integer key = 0;

        boolean flag = false;
        ArrayList<Integer> workgroupKeys = new ArrayList<Integer>();

        if(selectedWorkgroups.length < 1){
            flag = true;
            this.addToMessageQueue("Please select the workgroup(s) to which this card will be available.", FacesMessage.SEVERITY_ERROR);
        }
        if(validateCardName()){
            this.addToMessageQueue("There already exists a cage card named " + cardName + " of type " + cardTypeMask, FacesMessage.SEVERITY_ERROR);
            flag = true;                
        }
        if(!flag){
            for(WorkgroupEntity wge : getSelectedWorkgroups()){
                workgroupKeys.add(wge.getKey());
            }
            user = (UserEntity) getSessionParameter("userEntity");
            key = user.getUserkey();

            this.getLogger().logWarn(this.formatLogMessage("User: "
                    + user.getUserName(), "User Key" + Integer.toString(key)));
            this.getLogger().logWarn(this.formatLogMessage("User: "
                    + user.getFirstName(), "User Key" + Integer.toString(key)));
            this.getLogger().logWarn(this.formatLogMessage("User: "
                    + user.getLastName(), "User Key" + Integer.toString(key)));
            
            //set up DTO with redundant unneccessary values
            if (cardTypeMask.equals("detail")) {
                theType = CageCardType.Detail;
            } else if (cardTypeMask.equals("wean")) {
                theType = CageCardType.Wean;
            } else {
                theType = CageCardType.Mating;
            }

            if (printSettings.equals("3x5")) {
                theFormat = FormatType.threeByFive;
            } else if (printSettings.equals("right")) {
                theFormat = FormatType.alignRight;
            } else if (printSettings.equals("left")) {
                theFormat = FormatType.alignLeft;
            } else {
                theFormat = FormatType.alignCenter;
            }

            CageCardDTO theCard = new CageCardDTO();

            if(orientation.equals("portrait")){
                theCard.setOrientation(Orientation.portrait);
            }
            else{
                theCard.setOrientation(Orientation.landscape);
            }        

            theCard.setCageCardType(theType);
            theCard.setCustomCard(customCard);
            theCard.setFormat(theFormat);
            theCard.setPenID("-100");
            theCard.setQuantity("single");
            theCard.setCardName(cardName);
            //save
            try{
                Integer cardkey = sfl.saveCageCard(theCard, cardName, user, workgroupKeys);
                CageCardDAO dao = new CageCardDAO();
                for(Integer wgKey : workgroupKeys){
                    dao.insertCageCardLinkRow(cardkey.toString(), wgKey.toString());
                }
                saved = true;
                this.addToMessageQueue(cardName + " of card type " 
                        + cardTypeMask + " successfully saved!", FacesMessage.SEVERITY_INFO);
                this.getLogger().logInfo(cardName + " of card type " 
                        + cardTypeMask + " successfully saved!");
            }
            catch(Exception e){
                this.addToMessageQueue("Cage card " + cardName + " with error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public String loadCageCardAction() {
        try {
            CageCardDTO theDTO;
            Integer key = this.getKey("udMouseQueryKey");
            theDTO = sfl.loadCageCard(key);  
            
            //set field details
            customCard = theDTO.getCustomCard();
            
            cardCalculate();
            
            //set name
            cardName = theDTO.getCardName();
            
            //set format
            if(theDTO.getFormat() == FormatType.threeByFive){
                printSettings = "3x5";
            }
            else if(theDTO.getFormat() == FormatType.alignRight){
                printSettings = "right";
            }
            else if(theDTO.getFormat() == FormatType.alignLeft){
                printSettings = "left";
            }
            else{
                printSettings="center";
            }
            
            //set card type
            if(theDTO.getCageCardType() == CageCardType.Detail){
                cardTypeMask = "detail";
            }
            else if(theDTO.getCageCardType() == CageCardType.Wean){
                cardTypeMask = "wean";
            }
            else{
                cardTypeMask = "mating";
            }
            
            //set orientation
            if(Orientation.landscape == theDTO.getOrientation()){
                orientation = "landscape";
            }
            else{
                orientation = "portrait";
            }
            //here you need to get the workgroups to which the card belongs.
            CageCardDAO dao = new CageCardDAO();
            ArrayList<String> ccWgs = dao.getCardOwnersByCardKey(key.toString());
            selectedWorkgroups = new WorkgroupEntity[workgroups.size()];
            int jdx = 0;
            for(String wg : ccWgs){
                int idx = 0;
                for(WorkgroupEntity workgroup : workgroups){
                    if(workgroup.getWorkgroupName().equalsIgnoreCase(wg)){
                        selectedWorkgroups[jdx] = workgroup;
                        jdx++;
                    }
                    idx++;
                }
            }
            changeDropDownVals();
        }catch (Exception e) {
            // Display error information
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
        return "customCageCard";
    }
    
    
    
    public void deleteCardHelper(){
        cardKey = this.getKey("udMouseQueryKey");
    }
    
    public String deleteCard(){
        boolean theFlag = true;       
        try{
            
            CageCardDAO dao = new CageCardDAO();
            //wgLst are the workgroups user is a member of
            ArrayList<WorkgroupEntity> userWgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst");
            ArrayList<String> cardWgLst = dao.getCardOwnersByCardKey(cardKey.toString());
            QueryDefinitionEntity udEntity = new JCMSWebAppService().loadQueryByKey(cardKey);
            
            for(WorkgroupEntity wge: userWgLst){
                if(cardWgLst.contains(wge.getWorkgroupName())){
                    theFlag = false;
                    System.out.println("User in card workgroup");                    
                }
                else{
                    System.out.println("User not in card workgroup");
                }
            }
            
            if(!theFlag){
                //first remove the links
                dao.deleteQueryLinks(udEntity.getQueryDefinitionkey().toString());
                new JCMSWebAppService().baseDelete(udEntity);
            }
            else{
                this.addToMessageQueue("You must be member of workgroup " + 
                        udEntity.getQueryName() + " to delete that cage card.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("You must be member of workgroup " + 
                        udEntity.getQueryName() + " to delete that cage card.", "deleteCageCardAction"));
            }
            
        }
        catch(Exception e){
            String msg = "The system failed on delete: ";
            this.addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "deleteCageCardAction"));
        }
        return "loadCageCard";
    }
       
    private boolean validateCardName() {
        //workgroup key is the key of the workgroup you'd like to add the CC to
        
        //ListSupportDTO lstDTO = new ListSupportDTO();
        
        //get all cards of same type that have same card name:
        CageCardDAO dao = new CageCardDAO();
        String queryTypeKey;
        if(cardTypeMask.equals("detail")){
            queryTypeKey = "3";
        }
        else if(cardTypeMask.equals("mating")){
            queryTypeKey = "4";
        }
        else{
            queryTypeKey = "5";
        }
        ArrayList<Integer> wgs = dao.getWorkgroupKeysForCard(cardName, queryTypeKey);
        if(wgs.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return the cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return the cardTypeMask
     */
    public String getCardTypeMask() {
        return cardTypeMask;
    }

    /**
     * @param cardTypeMask the cardTypeMask to set
     */
    public void setCardTypeMask(String cardTypeMask) {
        this.cardTypeMask = cardTypeMask;
    }

    /**
     * @return the orientation
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the labels
     */
    public boolean isLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(boolean labels) {
        this.labels = labels;
    }

    /**
     * @return the barCode
     */
    public boolean isBarCode() {
        return barCode;
    }

    /**
     * @param barCode the barCode to set
     */
    public void setBarCode(boolean barCode) {
        this.barCode = barCode;
    }

    /**
     * @return the customCard
     */
    public ArrayList<CustomCardField> getCustomCard() {
        return customCard;
    }

    /**
     * @param customCard the customCard to set
     */
    public void setCustomCard(ArrayList<CustomCardField> customCard) {
        this.customCard = customCard;
    }


    /**
     * @return the editField
     */
    public CustomCardField getEditField() {
        return editField;
    }

    /**
     * @param editField the editField to set
     */
    public void setEditField(CustomCardField editField) {
        this.editField = editField;
    }

    /**
     * @return the editFieldName
     */
    public String getEditFieldName() {
        return editFieldName;
    }

    /**
     * @param editFieldName the editFieldName to set
     */
    public void setEditFieldName(String editFieldName) {
        this.editFieldName = editFieldName;
    }

    /**
     * @return the editLabels
     */
    public boolean isEditLabels() {
        return editLabels;
    }

    /**
     * @param editLabels the editLabels to set
     */
    public void setEditLabels(boolean editLabels) {
        this.editLabels = editLabels;
    }

    /**
     * @return the editbarCode
     */
    public boolean isEditBarCode() {
        return editBarCode;
    }

    /**
     * @param editbarCode the editbarCode to set
     */
    public void setEditBarCode(boolean editbarCode) {
        this.editBarCode = editbarCode;
    }

    /**
     * @return the printSettings
     */
    public String getPrintSettings() {
        return printSettings;
    }

    /**
     * @param printSettings the printSettings to set
     */
    public void setPrintSettings(String printSettings) {
        this.printSettings = printSettings;
    }

    /**
     * @return the rowPortion
     */
    public String getRowPortion() {
        return rowPortion;
    }

    /**
     * @param rowPortion the rowPortion to set
     */
    public void setRowPortion(String rowPortion) {
        this.rowPortion = rowPortion;
    }

    /**
     * @return the editNumberOfRows
     */
    public String geteditNumberOfRows() {
        return getEditNumberOfRows();
    }

    /**
     * @param editNumberOfRows the editNumberOfRows to set
     */
    public void seteditNumberOfRows(String editNumberOfRows) {
        this.setEditNumberOfRows(editNumberOfRows);
    }

    /**
     * @return the editRowPortion
     */
    public String getEditRowPortion() {
        return editRowPortion;
    }

    /**
     * @param editRowPortion the editRowPortion to set
     */
    public void setEditRowPortion(String editRowPortion) {
        this.editRowPortion = editRowPortion;
    }

    /**
     * @return the numberOfRows
     */
    public String getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param numberOfRows the numberOfRows to set
     */
    public void setNumberOfRows(String numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    /**
     * @return the rowsAvailable
     */
    public int getRowsAvailable() {
        return rowsAvailable;
    }

    /**
     * @param rowsAvailable the rowsAvailable to set
     */
    public void setRowsAvailable(int rowsAvailable) {
        this.rowsAvailable = rowsAvailable;
    }

    /**
     * @return the editNumberOfRows
     */
    public String getEditNumberOfRows() {
        return editNumberOfRows;
    }

    /**
     * @param editNumberOfRows the editNumberOfRows to set
     */
    public void setEditNumberOfRows(String editNumberOfRows) {
        this.editNumberOfRows = editNumberOfRows;
    }

    /**
     * @return the displayFields
     */
    public ArrayList<SelectItem> getDisplayFields() {
        return displayFields;
    }

    /**
     * @param displayFields the displayFields to set
     */
    public void setDisplayFields(ArrayList<SelectItem> displayFields) {
        this.displayFields = displayFields;
    }

    /**
     * @return the theWrapper
     */
    public SelectItemWrapper getTheWrapper() {
        return theWrapper;
    }

    /**
     * @param theWrapper the theWrapper to set
     */
    public void setTheWrapper(SelectItemWrapper theWrapper) {
        this.theWrapper = theWrapper;
    }

    /**
     * @return the displayCards
     */
    public ArrayList<SelectItem> getDisplayCards() {
        return displayCards;
    }

    /**
     * @param displayCards the displayCards to set
     */
    public void setDisplayCards(ArrayList<SelectItem> displayCards) {
        this.displayCards = displayCards;
    }

    /**
     * @return the theLoadCard
     */
    public QueryDefinitionEntity getTheLoadCard() {
        return theLoadCard;
    }

    /**
     * @param theLoadCard the theLoadCard to set
     */
    public void setTheLoadCard(QueryDefinitionEntity theLoadCard) {
        this.theLoadCard = theLoadCard;
    }

    /**
     * @return the insertFieldIndex
     */
    public int getInsertFieldIndex() {
        return insertFieldIndex;
    }

    /**
     * @param insertFieldIndex the insertFieldIndex to set
     */
    public void setInsertFieldIndex(int insertFieldIndex) {
        this.insertFieldIndex = insertFieldIndex;
    }

    /**
     * @return the insertFieldName
     */
    public String getInsertFieldName() {
        return insertFieldName;
    }

    /**
     * @param insertFieldName the insertFieldName to set
     */
    public void setInsertFieldName(String insertFieldName) {
        this.insertFieldName = insertFieldName;
    }

    /**
     * @return the insertNumberOfRows
     */
    public String getInsertNumberOfRows() {
        return insertNumberOfRows;
    }

    /**
     * @param insertNumberOfRows the insertNumberOfRows to set
     */
    public void setInsertNumberOfRows(String insertNumberOfRows) {
        this.insertNumberOfRows = insertNumberOfRows;
    }

    /**
     * @return the insertRowPortion
     */
    public String getInsertRowPortion() {
        return insertRowPortion;
    }

    /**
     * @param insertRowPortion the insertRowPortion to set
     */
    public void setInsertRowPortion(String insertRowPortion) {
        this.insertRowPortion = insertRowPortion;
    }

    /**
     * @return the insertLabels
     */
    public boolean isInsertLabels() {
        return insertLabels;
    }

    /**
     * @param insertLabels the insertLabels to set
     */
    public void setInsertLabels(boolean insertLabels) {
        this.insertLabels = insertLabels;
    }

    /**
     * @return the insertBarCode
     */
    public boolean isInsertBarCode() {
        return insertBarCode;
    }

    /**
     * @param insertBarCode the insertBarCode to set
     */
    public void setInsertBarCode(boolean insertBarCode) {
        this.insertBarCode = insertBarCode;
    }

    /**
     * @return the borders
     */
    public boolean isBorders() {
        return borders;
    }

    /**
     * @param borders the borders to set
     */
    public void setBorders(boolean borders) {
        this.borders = borders;
    }

    /**
     * @return the customText
     */
    public String getCustomText() {
        return customText;
    }

    /**
     * @param customText the customText to set
     */
    public void setCustomText(String customText) {
        this.customText = customText;
    }

    /**
     * @return the editBorders
     */
    public boolean isEditBorders() {
        return editBorders;
    }

    /**
     * @param editBorders the editBorders to set
     */
    public void setEditBorders(boolean editBorders) {
        this.editBorders = editBorders;
    }

    /**
     * @return the editCustomText
     */
    public String getEditCustomText() {
        return editCustomText;
    }

    /**
     * @param editCustomText the editCustomText to set
     */
    public void setEditCustomText(String editCustomText) {
        this.editCustomText = editCustomText;
    }

    /**
     * @return the insertBorders
     */
    public boolean isInsertBorders() {
        return insertBorders;
    }

    /**
     * @param insertBorders the insertBorders to set
     */
    public void setInsertBorders(boolean insertBorders) {
        this.insertBorders = insertBorders;
    }

    /**
     * @return the insertCustomText
     */
    public String getInsertCustomText() {
        if(insertCustomText==null){
            return "";
        }
        else{
            return insertCustomText;
        }
    }

    /**
     * @param insertCustomText the insertCustomText to set
     */
    public void setInsertCustomText(String insertCustomText) {
        if(insertCustomText == null){
            this.insertCustomText = "";
        }
        else{
            this.insertCustomText = insertCustomText;
        }
    }

    /**
     * @param rowPortionFields the rowPortionFields to set
     */
    public void setRowPortionFields(ArrayList<SelectItem> rowPortionFields) {
        this.rowPortionFields = rowPortionFields;
    }
    
    public void setRowColor(String rowColor){
        this.rowColor = rowColor;
    }

    /**
     * @return the workgroups
     */
    public ArrayList<WorkgroupEntity> getWorkgroups() {
        return workgroups;
    }

    /**
     * @param workgroups the workgroups to set
     */
    public void setWorkgroups(ArrayList<WorkgroupEntity> workgroups) {
        this.workgroups = workgroups;
    }

    /**
     * @return the selectedWorkgroups
     */
    public WorkgroupEntity[] getSelectedWorkgroups() {
        return selectedWorkgroups;
    }

    /**
     * @param selectedWorkgroups the selectedWorkgroups to set
     */
    public void setSelectedWorkgroups(WorkgroupEntity[] selectedWorkgroups) {
        this.selectedWorkgroups = selectedWorkgroups;
    }
}
