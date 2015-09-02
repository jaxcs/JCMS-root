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

package jcms.web.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.web.experiments.ExperimentsBean;
import jcms.integrationtier.dao.ExpDataDAO;
import jcms.integrationtier.dao.ExpQueryDAO;
import jcms.integrationtier.dto.ExpDataDescriptorDTO;
import jcms.integrationtier.dto.ExpDataDescriptorFieldDTO;
import jcms.integrationtier.dto.ExpQueryResultsDTO;
import jcms.integrationtier.dto.ExperimentQueryDTO;
import jcms.web.common.FileDownloadBean;
import jcms.web.common.SelectItemWrapper;
import org.primefaces.model.DualListModel; 

/**
 *
 * @author bas
 */

public class ExperimentQueryBean extends ExperimentsBean implements Serializable {
    private static final long serialVersionUID = 00231L;
   
    private ExperimentQueryDTO search;
    private ArrayList<ExpQueryResultsDTO> resultList;
    private ArrayList<ExpQueryResultsDTO> filteredResultList; //this is used by the datatable to hold the results when filtered by the user
    private String searchString;
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    
    private ExpDataDescriptorDTO expDataDescriptorDTO;
    private DualListModel lifeStatuses = new DualListModel();
    private DualListModel strains = new DualListModel();
    private DualListModel dataOwners = new DualListModel();
    private DualListModel mouseOwners = new DualListModel();
    
    private int rowCnt = 0;
    private File tempFile;
    private String fileName = "";
    private String inputPath = "";
    
    private SimpleDateFormat mySQLDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private Boolean showModalDialog = true;
    
    public ExperimentQueryBean() {
        search = new ExperimentQueryDTO();
        setExpDataDescriptorDTO(new ExpDataDescriptorDTO());
        setupDualListModels();

    }
    
    private void setupDualListModels(){
        try{
            //life statuses
            ArrayList<LifeStatusEntity> theLifeStatuses = new ArrayList<LifeStatusEntity>();
            for(SelectItem si : selectItemWrapper.getCvLifeStatusItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theLifeStatuses.add((LifeStatusEntity) si.getValue());
                }
            }
            getLifeStatuses().setSource(theLifeStatuses);
            getLifeStatuses().setTarget(new ArrayList());
            
            //strains
            ArrayList<StrainEntity> theStrains = new ArrayList<StrainEntity>();
            for(SelectItem si : selectItemWrapper.getMouseStrainItems()){
                if(si.getValue() != null && !si.getValue().toString().equals("")){
                    theStrains.add((StrainEntity) si.getValue());
                }
            }
            getStrains().setSource(theStrains);
            getStrains().setTarget(new ArrayList());
            
            //owners
            ArrayList<OwnerEntity> theOwners = (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");
            getDataOwners().setSource(theOwners);
            getDataOwners().setTarget(new ArrayList());
            getMouseOwners().setSource(theOwners);
            getMouseOwners().setTarget(new ArrayList());
            
        } catch(Exception e){
            this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "There was an error populating DualListModels: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Exception populating DualListModel ", e.getMessage()));
        }
    }
        
    public ArrayList<ExpDataDescriptorDTO> completeTestType(String query) {
        //this method fills the list of all test types on the screen
        //query is passed from the auto-complete on the form
        ExpDataDAO dao = new ExpDataDAO();
        ArrayList<ExpDataDescriptorDTO> testTypes = dao.getAllTestTypes();
        ArrayList<ExpDataDescriptorDTO> filteredTestTypes = new ArrayList<ExpDataDescriptorDTO>();
        for (int i = 0; i < testTypes.size(); i++) {
            ExpDataDescriptorDTO dto = testTypes.get(i);
            if(dto.getTestType().toLowerCase().startsWith(query)) {
                filteredTestTypes.add(dto);
            }
        }
        if(filteredTestTypes.isEmpty()) {
            System.out.println("Selecting a test type returned no choices");
            this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "The test type entered is not in the database.", null));
        }    
        return filteredTestTypes;
    }
    
    private void initializeSearch(){
        int expDataDescKey = 0;
        expDataDescKey = getExpDataDescriptorDTO().getExpDataDescriptor_key();
        String expDataDescKeyString = Integer.toString(expDataDescKey);
        getSearch().setExpDataDescriptorKey(expDataDescKeyString);
        getSearch().setTestType(getExpDataDescriptorDTO().getTestType());
         
        getSearch().setLifeStatus(lifeStatuses.getTarget());
        getSearch().setStrain(getStrains().getTarget());
        
        //We have to filter always by owners so the user sees only the data they are allowed to see
        if(!dataOwners.getTarget().isEmpty()){
            search.setDataOwner(dataOwners.getTarget());
        }
        else{
            search.setDataOwner(dataOwners.getSource());
        }
        
        //We are NOT going to limit the mouse owners if no filter is selected
        //BECAUSE the data might include mice with other owners, the filter is on the data owner only
        //unless they choose to also filter on mouse owner
        if(!mouseOwners.getTarget().isEmpty()){
            search.setMouseOwner(mouseOwners.getTarget());
        }
        
        if(search.getDataIDFrom() == null || search.getDataIDFrom().trim().equals("") ) {
            search.setDataIDFrom("");
        }
        if(search.getDataIDTo() == null || search.getDataIDTo().trim().equals("")) {
            search.setDataIDTo("");
        }
        
        //Setup the values to use for the dx field headers
        //The headers will use the matching dx_caption
        //Use "" if there is nothing in dx_caption
        //Therefore, intialize all the headers to ""
        initializeDxHeaders();
        //Now look up the selected test type record and change the header for those dx that have a caption value
        ExpDataDescriptorDTO dto = new ExpDataDAO().getTestType(expDataDescKey);
            //make sure there are some captions
            Integer fieldNumber = 1;
            for (ExpDataDescriptorFieldDTO field : dto.getFields()) {
                if (!(field.getCaption() == null || field.getCaption().equals(""))) {
                    //Here we need to set the dx value that matches the dx_caption
                    switch(fieldNumber){
                        case 1:
                            search.setD1Header(field.getCaption()); 
                            break;
                        case 2: 
                            search.setD2Header(field.getCaption());
                            break;
                        case 3: 
                            search.setD3Header(field.getCaption());
                            break;
                        case 4: 
                            search.setD4Header(field.getCaption());
                            break;
                        case 5: 
                            search.setD5Header(field.getCaption());
                            break;
                        case 6: 
                            search.setD6Header(field.getCaption());
                            break;
                        case 7: 
                            search.setD7Header(field.getCaption());
                            break;
                        case 8: 
                            search.setD8Header(field.getCaption());
                            break;
                        case 9: 
                            search.setD9Header(field.getCaption());
                            break;
                        case 10: 
                            search.setD10Header(field.getCaption());
                            break;
                        case 11: 
                            search.setD11Header(field.getCaption());
                            break;
                        case 12: 
                            search.setD12Header(field.getCaption());
                            break;
                        case 13: 
                            search.setD13Header(field.getCaption());
                            break;
                        case 14: 
                            search.setD14Header(field.getCaption());
                            break;
                        case 15: 
                            search.setD15Header(field.getCaption());
                            break;
                        case 16: 
                            search.setD16Header(field.getCaption());
                            break;
                        case 17: 
                            search.setD17Header(field.getCaption());
                            break;
                        case 18: 
                            search.setD18Header(field.getCaption());
                            break;
                        case 19: 
                            search.setD19Header(field.getCaption());
                            break;
                        case 20: 
                            search.setD20Header(field.getCaption());
                            break;
                        case 21: 
                            search.setD21Header(field.getCaption());
                            break;
                        case 22: 
                            search.setD22Header(field.getCaption());
                            break;
                        case 23: 
                            search.setD23Header(field.getCaption());
                            break;
                        case 24: 
                            search.setD24Header(field.getCaption());
                            break;
                        case 25: 
                            search.setD25Header(field.getCaption());
                            break;
                        case 26: 
                            search.setD26Header(field.getCaption());
                            break;
                        case 27: 
                            search.setD27Header(field.getCaption());
                            break;
                        case 28: 
                            search.setD28Header(field.getCaption());
                            break;
                        case 29: 
                            search.setD29Header(field.getCaption());
                            break;
                        case 30: 
                            search.setD30Header(field.getCaption());
                            break;
                        }
                    }
                fieldNumber++;
                }
    }
    
    private void initializeDxHeaders() {
        search.setD1Header("");
        search.setD2Header("");
        search.setD3Header("");
        search.setD4Header("");
        search.setD5Header("");
        search.setD6Header("");
        search.setD7Header("");
        search.setD8Header("");
        search.setD9Header("");
        search.setD10Header("");
        search.setD11Header("");
        search.setD12Header("");
        search.setD13Header("");
        search.setD14Header("");
        search.setD15Header("");
        search.setD16Header("");
        search.setD17Header("");
        search.setD18Header("");
        search.setD19Header("");
        search.setD20Header("");
        search.setD21Header("");
        search.setD22Header("");
        search.setD23Header("");
        search.setD24Header("");
        search.setD25Header("");
        search.setD26Header("");
        search.setD27Header("");
        search.setD28Header("");
        search.setD29Header("");
        search.setD30Header("");
        
    }
        
    public String runQueryAction() {    
        String errMsg = "";
        this.setRowCnt(0); //initialize to zero
        //clear the results
        this.resultList = null;
        this.filteredResultList = null;
        this.showModalDialog = true; //Use this variable to decide if the results dialog box should be open
        try {
            // checks if at least one choice has been made
            if (!validateOutput()) {
                // Display validation information
                this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Experiment Query: " + "At least one output field (other than test type) needs to be selected", null));
                this.getLogger().logWarn(this.formatLogMessage("Validation ", "At least one output field (other than test type) needs to be selected"));
                this.showModalDialog = false; //flag to close the modal dialog box
                return "";
            } else {
                //validate the filter fields
                if (!validateFilters()){
                    this.showModalDialog = false;
                    return "";
                } else {
                    //Put the filter values into "search" (ExperimentQueryDTO)
                    initializeSearch();
                    //Process the request
                    this.setSearchString(createSearchString());
                    this.getLogger().logInfo(this.formatLogMessage("Search String: ", getSearchString()));
                    // get the result set
                    ExpQueryDAO dao = new ExpQueryDAO();
                    this.setResultList(dao.getSearchResults(getSearchString()));
                    if (this.resultList != null) {
                        this.setRowCnt(resultList.size()); 
                    }                    
                }
            }
        } catch (Exception e) {
            this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Experiment Query failed: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Experiment Query Exception ", e.getMessage()));
            this.showModalDialog = false;
            return "";
        }

        return "";
    } 
    
    private String createSearchString() {
        String fieldList = "testType, dataID, M.ID AS mouseID, Strain.strainName AS strain, jrNum";
        String tableList = " ExpDataDescriptor, ExpData, Mouse AS M, Strain, LifeStatus, Container";
        String criteriaListOfTables = " ExpDataDescriptor._expDataDescriptor_key = ExpData._expDataDesc_key"
                + " AND ExpData._specimen_key = M._mouse_key"
                + " AND M._strain_key = Strain._strain_key"
                + " AND M.lifeStatus = LifeStatus.lifeStatus"
                + " AND M._pen_key = Container._container_key";
        String fieldCriteria = "";
        String queryString = "";
             
        //Add test type, its required so there will always be a choice.
        fieldCriteria = " AND ExpDataDescriptor._expDataDescriptor_key =" + getSearch().getExpDataDescriptorKey(); 
        
        //Add data ID filters
        if (search.getDataIDFrom().equals("") & search.getDataIDTo().equals("") ){
            //nothing to filter
        } else {
            if (search.getDataIDFrom().equals("0") & search.getDataIDTo().equals("0") ){
                //nothing to filter
            } else {
                if (search.getDataIDFrom().equals("") & search.getDataIDTo().equals("0")){
                    //nothing to filter
                } else {
                    if (search.getDataIDTo().equals("") & search.getDataIDFrom().equals("0")){
                    //nothing to filter
                    } else {
                        if (search.getDataIDFrom().equals("")) {
                            fieldCriteria = fieldCriteria + " AND dataID <=" + search.getDataIDTo();
                        } else {
                            if (search.getDataIDTo().equals("")) {
                                fieldCriteria = fieldCriteria + " AND dataID >=" + search.getDataIDFrom();
                            } else {
                                fieldCriteria = fieldCriteria + " AND (dataID >=" + search.getDataIDFrom() + " AND dataID <=" + search.getDataIDTo() +")";
                            }
                        }
                    }
                }
            }
        }
        
        //Add data owner to fields to return
        if (search.isSelectDataOwner()){
            fieldList = fieldList + ", ExpData.owner AS dataOwner";
        }
        
        //Always add data owner filters since this user should not be able to see everyone's data
            String dataOwnerClause = " AND ExpData.owner IN (";
            for (int i = 0; i< search.getDataOwner().size(); ++i){
                //if last item, no comma
                if (i == search.getDataOwner().size() - 1) {
                    dataOwnerClause = dataOwnerClause + "'" + search.getDataOwner().get(i).getOwner() + "'";
                } else {
                    dataOwnerClause = dataOwnerClause + "'" + search.getDataOwner().get(i).getOwner() + "'" + " ,";
                }
            }
            fieldCriteria = fieldCriteria + dataOwnerClause + ")";
        
        //Add ExpData fields and filters
        if (search.isSelectCollectionDate()){
            fieldList = fieldList + ", ExpData.expDate";
        }
        
        if (search.getStartDate() != null){
            
            if (search.getEndDate() != null){
                fieldCriteria = fieldCriteria + " AND (expDate >='" + mySQLDateFormat.format(search.getStartDate()) 
                        + "' AND expDate <='" + mySQLDateFormat.format(search.getEndDate()) + "')";
            } else {
                fieldCriteria = fieldCriteria + " AND (expDate >='" + mySQLDateFormat.format(search.getStartDate()) + "')";
            }
        } else {
            if (search.getEndDate() != null){
                fieldCriteria = fieldCriteria + " AND (expDate <='" + mySQLDateFormat.format(search.getEndDate()) + "')";
            }
        }
        
        if (search.isSelectCollectionAge()){
            fieldList = fieldList + ", ExpData.age";
        }
        
        if(!(search.getAgeFilter() == null || search.getAgeFilter().trim().equals(""))) {
            //First convert the age entered by the user into days
            String tempAgeFromString = "";
            String tempAgeToString = "";
            if(search.getAgeMeasure().equals("Weeks")) {
                tempAgeFromString = convertWeeksToDays(search.getAgeFrom());
                if(search.getAgeFilter().equals("Between")) {
                    tempAgeToString = convertWeeksToDays(search.getAgeTo());
                }
            } else {
                if(search.getAgeMeasure().equals("Months")) {
                    tempAgeFromString = convertMonthsToDays(search.getAgeFrom());
                    if(search.getAgeFilter().equals("Between")) {
                        tempAgeToString = convertMonthsToDays(search.getAgeTo());
                    }
                } else {
                    tempAgeFromString = search.getAgeFrom();
                    if(search.getAgeFilter().equals("Between")) {
                        tempAgeToString = search.getAgeTo();
                    }
                }    
            }
            //Now create the sql
            if(search.getAgeFilter().equals("Between")) {
                fieldCriteria = fieldCriteria + " AND (age >='" + tempAgeFromString + "' AND age <= '" + tempAgeToString + "')";
            } else {
                if(search.getAgeFilter().equals("Greater Than")) {
                    fieldCriteria = fieldCriteria + " AND (age >='" + tempAgeFromString + "')";
                } else {
                    if(search.getAgeFilter().equals("Less Than")) {
                        fieldCriteria = fieldCriteria + " AND (age <='" + tempAgeFromString + "')";
                    } else {
                        fieldCriteria = fieldCriteria + " AND (age ='" + tempAgeFromString + "')";
                    }
                }
            }
        }

        if (search.isSelectAbnormalData()){
            fieldList = fieldList + ", ExpData.abnormalData";
        }
        
        if (!search.getAbnormalDataChoice().equals("Any")) {
            if(search.getAbnormalDataChoice().equals("True")) {
                fieldCriteria = fieldCriteria + " AND (abnormalData = -1)";
            } else {
                fieldCriteria = fieldCriteria + " AND (abnormalData = 0)";
            }
        }
            
        //Add mouse ID filters 
        if (!(search.getMouseIDLike() == null || search.getMouseIDLike().trim().equals(""))) {
            if(search.getMouseFilter().equals("Equals")) {
                fieldCriteria = fieldCriteria + " AND M.ID ='" + search.getMouseIDLike() +"'";
            } else {
                fieldCriteria = fieldCriteria + " AND M.ID LIKE '%" + search.getMouseIDLike() +"%'";
            }
        }
        
         //Add mouse owner to fields to return
        if (search.isSelectMouseOwner()){
            fieldList = fieldList + ", M.owner AS mouseOwner";
        }

        if(!mouseOwners.getTarget().isEmpty()) {
            String mouseOwnerClause = " AND M.owner IN (";
            for (int i = 0; i< search.getMouseOwner().size(); ++i){
                //if last item, no comma
                if (i == search.getMouseOwner().size() - 1) {
                    mouseOwnerClause = mouseOwnerClause + "'" + search.getMouseOwner().get(i).getOwner() + "'";
                } else {
                    mouseOwnerClause = mouseOwnerClause + "'" + search.getMouseOwner().get(i).getOwner() + "'" + " ,";
                }
            }
            fieldCriteria = fieldCriteria + mouseOwnerClause + ")";
        }
        
        //Add mouse fields that have filters
        if (search.isSelectMouseLifeStatus()){
            fieldList = fieldList + ", M.lifeStatus";
        }
        
        if (!lifeStatuses.getTarget().isEmpty()){
            String lifeStatusClause = " AND M.lifeStatus IN (";
            //we have to get the life status TERM from the life status entity list 
            //that is saved in the ExperimentQueryDTO (called search)
            for (int i = 0; i< search.getLifeStatus().size(); ++i) {
                if (i == search.getLifeStatus().size() -1) {
                    lifeStatusClause = lifeStatusClause + "'" + search.getLifeStatus().get(i).getLifeStatus() + "'";
                } else {
                    lifeStatusClause = lifeStatusClause + "'" + search.getLifeStatus().get(i).getLifeStatus() + "'" + " ,";
                }
            }
            fieldCriteria = fieldCriteria + lifeStatusClause + ")";
        }
        
        //strain Note: strainName and jrNum are already in the field list
        if (!strains.getTarget().isEmpty()){
            String strainClause = " AND Strain._strain_key IN (";
            //we have to get the strain key from the strain entity list 
            //that is saved in the ExperimentQueryDTO (called search)
            for (int i = 0; i< search.getStrain().size(); ++i) {
                if (i == search.getStrain().size() -1) {
                    strainClause = strainClause + search.getStrain().get(i).getKey();
                } else {
                    strainClause = strainClause + search.getStrain().get(i).getKey() + " ,";
                }
            }
            fieldCriteria = fieldCriteria + strainClause + ")";
        }
        
        //Add the optional mouse fields to return
        if (search.isSelectGeneration()){
            fieldList = fieldList + ", generation";
        }
        if (search.isSelectBirthDate()){
            fieldList = fieldList + ", birthDate";
        }
        if (search.isSelectSex()){
            fieldList = fieldList + ", sex";
        }
        if (search.isSelectCOD()){
            fieldList = fieldList + ", cod";
        }
        if (search.isSelectMouseCageID()){
            fieldList = fieldList + ", containerID";
        }
        if (search.isSelectCageName()){
            fieldList = fieldList + ", containerName";
        }
        if (search.isSelectMouseComment()){
            fieldList = fieldList + ", M.comment";
        }
        
        //Add all of the data results fields and the associated captions
        if (search.isSelectDataResults()){
            fieldList = fieldList + ", d1, d2, d3, d4, d5, d6, d7, d8, d9, " +
                    "d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, " +
                    "d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30";
            fieldList = fieldList + ", d1_caption, d2_caption, d3_caption, d4_caption, d5_caption, d6_caption, d7_caption, d8_caption, d9_caption, " +
                    "d10_caption, d11_caption, d12_caption, d13_caption, d14_caption, d15_caption, d16_caption, d17_caption, d18_caption, d19_caption, " +
                    "d20_caption, d21_caption, d22_caption, d23_caption, d24_caption, d25_caption, d26_caption, d27_caption, d28_caption, d29_caption, d30_caption ";
        }
        
        queryString = "SELECT " + fieldList + " FROM " + tableList + " WHERE " + criteriaListOfTables + fieldCriteria + ";";
        return queryString;
    }
    
    /**
     * This is invoked when Clear button on query page is clicked, it resets
     * the query page.
     */
    public String clearQueryAction() {
        
        setSearch(new ExperimentQueryDTO());
        setExpDataDescriptorDTO(new ExpDataDescriptorDTO());
        setupDualListModels();           
        
        return "experimentQuery";
    }
    
    /**
     *  Check if any of the output fields are selected in the query page
     * @return boolean
     */
    public boolean validateOutput() {
        boolean flag = false;

        if (getSearch() != null) {
            if (getSearch().isSelectMouseID()) {
                flag = true;
                return flag;
            }

            if (getSearch().isSelectMouseLifeStatus()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectMouseStrain()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectMouseOwner()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectDataID()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectDataOwner()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectCollectionDate()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectCollectionAge()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectAbnormalData()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectDataResults()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectMouseComment()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectGeneration()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectBirthDate()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectSex()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectCOD()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectMouseCageID()) {
                flag = true;
                return flag;
            }
            if (getSearch().isSelectCageName()) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }
    
    public boolean validateFilters() {
        boolean flag = true;
        try {
            //Test type is required, first verify it has a value
            if(expDataDescriptorDTO == null ){
                this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A test type must be selected.", null));
                flag = false;
            } else {
                if (expDataDescriptorDTO.getTestType() == null || expDataDescriptorDTO.getTestType().trim().equals("")) {
                    this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A test type must be selected.", null));
                    flag = false;
                }
            }
        
            //verify data IDs that are entered are numeric and From is < To
            //Set them to zero if field is blank so can test the range
            Float tempTo = 0.0f;
            Float tempFrom = 0.0f;
            try {
                if (search.getDataIDFrom() != null & !search.getDataIDFrom().trim().equals("")){
                    tempFrom = Float.parseFloat(search.getDataIDFrom());
                }
                if (search.getDataIDTo() != null & !search.getDataIDTo().trim().equals("")){
                    tempTo = Float.parseFloat(search.getDataIDTo());
                }
                if (tempTo != 0 & tempFrom > tempTo) {
                    flag = false;
                    this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data ID range is not valid. ", null));
                    this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Data ID range is not valid. "));
                }
                if (tempTo < 0 || tempFrom < 0) {
                    flag = false;
                    this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data ID may not be negative. ", null));
                    this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Data ID may not be negative. "));
                }
            } catch (NumberFormatException nfe) {
                flag = false;
                this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data ID must be a number. " + nfe.getMessage(), null));
                this.getLogger().logWarn(this.formatLogMessage("Experiment Query Exception ", nfe.getMessage()));
            }
            
            //Verify the requested date range is valid.
            if ((search.getStartDate() != null & search.getEndDate() != null)) {
                if (search.getStartDate().after(search.getEndDate()) ) {
                     flag = false;
                     this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Start date cannot be after the end date. ", null));
                     this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Start date cannot be after the end date. "));
                }
            }
            
            //Verify the age range is numeric
            try {
                Float tempAgeTo = 0.0f;
                Float tempAgeFrom = 0.0f;
                if(!search.getAgeFilter().equals("")) {
                    if(search.getAgeFrom() == null || search.getAgeFrom().equals("")) {
                        flag = false;
                        this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Age at data collection needs a value. ", null));
                        this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Age at data collection needs a value. "));
                    } else {
                        tempAgeFrom = Float.parseFloat(search.getAgeFrom());
                    }
                    if(search.getAgeFilter().equals("Between")) {
                        //also check ageTo
                        if(search.getAgeTo() == null || search.getAgeTo().equals("")) {
                            flag = false;
                            this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data collection age 'to' needs a value. ", null));
                            this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Data collection age 'to' needs a value. "));
                        } else {
                            tempAgeTo = Float.parseFloat(search.getAgeTo());
                            if (tempAgeTo < tempAgeFrom) {
                                flag = false;
                                this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data collection age 'to' cannot be less than the start age. ", null));
                                this.getLogger().logWarn(this.formatLogMessage("Experiment Query Error: ", "Data collection age 'to' cannot be less than the start age. "));
                            }
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                flag = false;
                this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Age at data collection range must be a number. " + nfe.getMessage(), null));
                this.getLogger().logWarn(this.formatLogMessage("Experiment Query Exception ", nfe.getMessage()));
            }
            
        } catch (Exception e) {
            flag = false;
            this.getFacesContext().addMessage("expQueryMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error checking the filter criteria: " + e.getMessage(), null));
            this.getLogger().logWarn(this.formatLogMessage("Experiment Query Exception: Error checking the filter criteria: ", e.getMessage()));
        }
        return flag;
    }
    
    public void createTemporaryFile() {
        if (this.resultList == null) {
            //don't create a file when there are no results
            return;
        }
        try {           
            // Create temp file.
            setTempFile(File.createTempFile("experimentQuery", ".csv"));

            // Delete temp file when program exits.
            getTempFile().deleteOnExit();

            // Write to temp file
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter(getTempFile()));

            //This creates the csv file  
            this.generateCSVHeaders(out);
            
            //Loop below works to generate the csv file rows
            //keep the order the same as the headers!
            for (ExpQueryResultsDTO dto : resultList) {
                try {
                    //Create the fields for the csv file
                    out.append("" + dto.getTestType() + ", ");
                    if(search.isSelectDataID()){
                        out.append("" + dto.getDataID() + ", ");
                    }
                    if(search.isSelectDataOwner()){
                        out.append("" + dto.getDataOwner() + ", ");
                    }
                    if(search.isSelectCollectionDate()){
                        out.append("" + dto.getExpDate() + ", ");
                    }
                    if(search.isSelectCollectionAge()){
                        out.append("" + dto.getAge() + ", ");
                    }
                    if(search.isSelectAbnormalData()){
                        out.append("" + dto.getAbnormalData() + ", ");
                    }
                    if (search.isSelectMouseID()) {
                        out.append("" + dto.getMouseID() + ", ");
                    }
                    if(search.isSelectMouseOwner()){
                        out.append("" + dto.getMouseOwner() + ", ");
                    }
                    if (search.isSelectMouseStrain()) {
                        out.append("" + dto.getStrain() + ", " + dto.getJrNum() + ", ");
                    }
                    if (search.isSelectGeneration()) {
                        out.append("" + dto.getGeneration() + ", ");
                    }
                    if (search.isSelectBirthDate()) {
                        out.append("" + dto.getBirthDate() + ", ");
                    }
                    if (search.isSelectSex()) {
                        out.append("" + dto.getSex() + ", ");
                    }
                    if (search.isSelectMouseLifeStatus()) {
                        out.append("" + dto.getLifeStatus() + ", ");
                    }
                    if (search.isSelectCOD()) {
                        out.append("" + dto.getCauseOfDeath() + ", ");
                    }
                    if (search.isSelectMouseCageID()) {
                        out.append("" + dto.getContainerID() + ", ");
                    }
                    if (search.isSelectCageName()) {
                        out.append("" + dto.getContainerName() + ", ");
                    }
                    if (search.isSelectMouseComment()) {
                        out.append("" + dto.getMouseComments() + ", ");
                    }
                    if (search.isSelectDataResults()){
                        //Only add data columns for those captions that are not blank or null
                        //The web page will then only display the matching dx value
                        //First look up the selected test type record
                        int testTypeKey = 0;
                        testTypeKey = Integer.parseInt(search.getExpDataDescriptorKey());
                        ExpDataDescriptorDTO dto2 = new ExpDataDAO().getTestType(testTypeKey);
                            Integer fieldNumber = 1;
                            for (ExpDataDescriptorFieldDTO field : dto2.getFields()) {
                                if (!(field.getCaption() == null || field.getCaption().equals(""))) {
                                    //Here we need to set the dx value that matches the dx_caption
                                    switch(fieldNumber){
                                        case 1:
                                            out.append("" + dto.getD1() + ", ");
                                            break;
                                        case 2: 
                                            out.append("" + dto.getD2() + ", ");
                                            break;
                                        case 3: 
                                            out.append("" + dto.getD3() + ", ");
                                            break;
                                        case 4: 
                                            out.append("" + dto.getD4() + ", ");
                                            break;
                                        case 5: 
                                            out.append("" + dto.getD5() + ", ");
                                            break;
                                        case 6: 
                                            out.append("" + dto.getD6() + ", ");
                                            break;
                                        case 7: 
                                            out.append("" + dto.getD7() + ", ");
                                            break;
                                        case 8: 
                                            out.append("" + dto.getD8() + ", ");
                                            break;
                                        case 9: 
                                            out.append("" + dto.getD9() + ", ");
                                            break;
                                        case 10: 
                                            out.append("" + dto.getD10() + ", ");
                                            break;
                                        case 11: 
                                            out.append("" + dto.getD11() + ", ");
                                            break;
                                        case 12: 
                                            out.append("" + dto.getD12() + ", ");
                                            break;
                                        case 13: 
                                            out.append("" + dto.getD13() + ", ");
                                            break;
                                        case 14: 
                                            out.append("" + dto.getD14() + ", ");
                                            break;
                                        case 15: 
                                            out.append("" + dto.getD15() + ", ");
                                            break;
                                        case 16: 
                                            out.append("" + dto.getD16() + ", ");
                                            break;
                                        case 17: 
                                            out.append("" + dto.getD17() + ", ");
                                            break;
                                        case 18: 
                                            out.append("" + dto.getD18() + ", ");
                                            break;
                                        case 19: 
                                            out.append("" + dto.getD19() + ", ");
                                            break;
                                        case 20: 
                                            out.append("" + dto.getD20() + ", ");
                                            break;
                                        case 21: 
                                            out.append("" + dto.getD21() + ", ");
                                            break;
                                        case 22: 
                                            out.append("" + dto.getD22() + ", ");
                                            break;
                                        case 23: 
                                            out.append("" + dto.getD23() + ", ");
                                            break;
                                        case 24: 
                                            out.append("" + dto.getD24() + ", ");
                                            break;
                                        case 25: 
                                            out.append("" + dto.getD25() + ", ");
                                            break;
                                        case 26: 
                                            out.append("" + dto.getD26() + ", ");
                                            break;
                                        case 27: 
                                            out.append("" + dto.getD27() + ", ");
                                            break;
                                        case 28: 
                                            out.append("" + dto.getD28() + ", ");
                                            break;
                                        case 29: 
                                            out.append("" + dto.getD29() + ", ");
                                            break;
                                        case 30: 
                                            out.append("" + dto.getD30() + ", ");
                                            break;
                                        }
                                    }
                                fieldNumber++;
                                }
                        }
                    
                    out.append("\r\n");
                } catch(Exception e){
                    System.out.println(e);
                }
            }       
            //Need this to close the file
            out.flush();
            out.close();
            } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception creating csv file: ",
                    e.getMessage()));
        }
    }
    
    public void downloadSearchResultsAction() {
        if (this.resultList == null) {
            //don't create a file when there are no results
            return;
        }
        System.out.println("Starting the download process.");
        
        try {    
            createTemporaryFile();
            int pos = 0;
            inputPath = getTempFile().getAbsolutePath();
            System.out.println("Input path: " + inputPath);
            String fs = File.separator;

            if (getInputPath() != null && (!inputPath.equals(""))) {
                // get the file name from file path
                //pos = inputPath.lastIndexOf("\\");
                pos = getInputPath().lastIndexOf(fs);
            }

            if (pos > 0) {
                setFileName(getInputPath().substring((pos + 1), getInputPath().length()));
            }
            System.out.println("File name: " + fileName);
            //the file has been created and saved already in a temp folder
            // the next command does not work, no window pops up for it so you can directly open it in Excel
            //or save it to your download folder
            if (fileName != null && (!fileName.equals(""))) {
                new FileDownloadBean().downloadFile(this.inputPath, this.fileName);
            }
            
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception creating csv file: ",
                    e.getMessage()));
        }
    }
    
    public void generateCSVHeaders(BufferedWriter out) {
        try {
            // write the column headers
            out.append("Test Type, ");
            if (search.isSelectDataID()){
                out.append("Data ID, ");
            }
            if (search.isSelectDataOwner()){
                out.append("Data Owner, ");
            }
            if(search.isSelectCollectionDate()){
                out.append("Data Collection Date, ");
            }
            if(search.isSelectCollectionAge()){
                out.append("Age, ");
            }
            if(search.isSelectAbnormalData()){
                out.append("Abnormal Data, ");
            }
            if (search.isSelectMouseID()) {
                out.append("Mouse ID, ");
            }
            if (search.isSelectMouseOwner()){
                out.append("Mouse Owner, ");
            }
            if (search.isSelectMouseStrain()) {
                out.append("Strain, Stock #, ");
            }
            if (search.isSelectGeneration()) {
                out.append("Generation, ");
            }
            if (search.isSelectBirthDate()) {
                out.append("Birth Date, ");
            }
            if (search.isSelectSex()) {
                out.append("Sex, ");
            }
            if (search.isSelectMouseLifeStatus()) {
                out.append("Life Status, ");
            }
            if (search.isSelectCOD()) {
                out.append("Cause of Death, ");
            }
            if (search.isSelectMouseCageID()) {
                out.append("Cage ID, ");
            }
            if (search.isSelectCageName()) {
                out.append("Cage Name, ");
            }
            if (search.isSelectMouseComment()) {
                out.append("Mouse Comments, ");
            }
            if (search.isSelectDataResults()){
                //Only add headers for those captions that are not blank or null
                //The web page will then only display the matching dx value
                //First look up the selected test type record
                int testTypeKey = 0;
                testTypeKey = Integer.parseInt(search.getExpDataDescriptorKey());
                ExpDataDescriptorDTO dto = new ExpDataDAO().getTestType(testTypeKey);
                    for (ExpDataDescriptorFieldDTO field : dto.getFields()) {
                        String caption = "";
                        if (!(field.getCaption() == null || field.getCaption().equals(""))) {
                            caption = field.getCaption();
                            out.append(caption + ", ");
                        }
                    }
            }
                
            out.append("\r\n");
            
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception creating csv headers: ",
                    e.getMessage()));
        }
    }
    
    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }

    /**
     * @return the lifeStatuses
     */
    public DualListModel getLifeStatuses() {
        return lifeStatuses;
    }

    /**
     * @param lifeStatuses the lifeStatuses to set
     */
    public void setLifeStatuses(DualListModel lifeStatuses) {
        this.lifeStatuses = lifeStatuses;
    }

    /**
     * @return the strains
     */
    public DualListModel getStrains() {
        return strains;
    }

    /**
     * @param strains the strains to set
     */
    public void setStrains(DualListModel strains) {
        this.strains = strains;
    }

    /**
     * @return the search
     */
    public ExperimentQueryDTO getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(ExperimentQueryDTO search) {
        this.search = search;
    }

    /**
     * @return the resultList
     */
    public ArrayList<ExpQueryResultsDTO> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(ArrayList<ExpQueryResultsDTO> resultList) {
        this.resultList = resultList;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the dataOwners
     */
    public DualListModel getDataOwners() {
        return dataOwners;
    }

    /**
     * @param dataOwners the dataOwners to set
     */
    public void setDataOwners(DualListModel dataOwners) {
        this.dataOwners = dataOwners;
    }

    /**
     * @return the mouseOwners
     */
    public DualListModel getMouseOwners() {
        return mouseOwners;
    }

    /**
     * @param mouseOwners the mouseOwners to set
     */
    public void setMouseOwners(DualListModel mouseOwners) {
        this.mouseOwners = mouseOwners;
    }

    /**
     * @return the expDataDescriptorDTO
     */
    public ExpDataDescriptorDTO getExpDataDescriptorDTO() {
        return expDataDescriptorDTO;
    }

    /**
     * @param expDataDescriptorDTO the expDataDescriptorDTO to set
     */
    public void setExpDataDescriptorDTO(ExpDataDescriptorDTO expDataDescriptorDTO) {
        this.expDataDescriptorDTO = expDataDescriptorDTO;
    }

    /**
     * @return the rowCnt
     */
    public int getRowCnt() {
        return rowCnt;
    }

    /**
     * @param rowCnt the rowCnt to set
     */
    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the tempFile
     */
    public File getTempFile() {
        return tempFile;
    }

    /**
     * @param tempFile the tempFile to set
     */
    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    /**
     * @return the filteredResultList
     */
    public ArrayList<ExpQueryResultsDTO> getFilteredResultList() {
        return filteredResultList;
    }

    /**
     * @param filteredResultList the filteredResultList to set
     */
    public void setFilteredResultList(ArrayList<ExpQueryResultsDTO> filteredResultList) {
        this.filteredResultList = filteredResultList;
    }

    /**
     * @return the showModalDialog
     */
    public Boolean getShowModalDialog() {
        return showModalDialog;
    }

    /**
     * @param showModalDialog the showModalDialog to set
     */
    public void setShowModalDialog(Boolean showModalDialog) {
        this.showModalDialog = showModalDialog;
    }

}
