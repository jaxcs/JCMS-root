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

package jcms.web.sample;

import java.util.Date;
import java.util.ArrayList;
import jcms.web.common.SelectItemWrapper;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.HashMap;
import jcms.integrationtier.colonyManagement.*;
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.dao.LocationDAO;
import jcms.integrationtier.dto.*;
import org.primefaces.model.DefaultTreeNode;
import java.util.Calendar;
import org.primefaces.model.TreeNode;
import jcms.integrationtier.dao.SampleDAO;
import org.primefaces.event.NodeSelectEvent; 
import javax.faces.application.FacesMessage;

/**
 *
 * @author mkamato
 */
public class EditSampleBean extends WTBaseBackingBean implements Serializable{
    
    private SampleDAO dao = new SampleDAO();
    
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    
    private String sampleID = "";
    private String newID = "";
    private OwnerEntity owner = null;
    private String sampleClass = "";
    private ArrayList<SelectItem> sampleClasses = new ArrayList<SelectItem>();
    private String sampleType = "";
    private ArrayList<SelectItem> sampleTypes = new ArrayList<SelectItem>();
    private String description = "";
    private Date sampleDate = null;
    private String sampleDateType = "";
    private ArrayList<SelectItem> sampleDateTypes = new ArrayList<SelectItem>();
    private String harvestMethod = "";
    private ArrayList<SelectItem> harvestMethods = new ArrayList<SelectItem>();
    private String weight = "";
    private String weightUnit = "";
    private ArrayList<SelectItem> weightUnits = new ArrayList<SelectItem>();
    private String age = "";
    private String timeUnit = "";
    private ArrayList<SelectItem> timeUnits = new ArrayList<SelectItem>(); 
    private String epoch = "";
    private ArrayList<SelectItem> epochs = new ArrayList<SelectItem>();
    private String preservationType = "";
    private ArrayList<SelectItem> preservationTypes = new ArrayList<SelectItem>();
    private String preservationMethod = "";
    private ArrayList<SelectItem> preservationMethods = new ArrayList<SelectItem>();
    private String preservationDetail = "";
    private ArrayList<SelectItem> preservationDetails = new ArrayList<SelectItem>();
    private String sampleStatus = "";
    private ArrayList<SelectItem> sampleStatuses = new ArrayList<SelectItem>();
    
    //location
    private TreeNode root = new DefaultTreeNode();
    private TreeNode nodeLocation = null;
    private LocationDAO locationDAO = new LocationDAO();
    private LocationTypeDTO location = null;
    
    //sources
    private String sourceType = "1";
    private StrainEntity sampleStrain = null;
    private String sourceID = "";
    private ArrayList<MouseEntity> mouseSources = new ArrayList<MouseEntity>();
    private ArrayList<MatingEntity> matingSources = new ArrayList<MatingEntity>();
    private ArrayList<LitterEntity> litterSources = new ArrayList<LitterEntity>();
    private ArrayList<SampleXtraDTO> sampleSources = new ArrayList<SampleXtraDTO>();
    
    //control view
    private boolean sourceTypeDisabled = false;
    private boolean calculateAgeDisabled = false;
    private boolean sampleIDDisabled = true;
    
    private MouseEntity mouseSource = null;
    private LitterEntity litterSource = null;
    private MatingEntity matingSource = null;
    private SampleXtraDTO sampleSource = null;
    
    private ArrayList<String> sourceList = new ArrayList<String>();
    
    public EditSampleBean(){
        //sample classes
        ArrayList<SampleClassDTO> classes = dao.getSampleClasses();
        sampleClasses = new ArrayList<SelectItem>();
        sampleClasses.add(new SelectItem("", ""));
        for(SampleClassDTO dto : classes){
            sampleClasses.add(new SelectItem(dto.getSampleClass_key(), dto.getSampleClass()));
        }
        
        //Sample Date types
        ArrayList<SampleDateTypeDTO> sdtDTOs = dao.getSampleDateTypes();
        for(SampleDateTypeDTO dto: sdtDTOs){
            sampleDateType = sdtDTOs.get(0).getSampleDateType_key();
            sampleDateTypes.add(new SelectItem(dto.getSampleDateType_key(), dto.getSampleDateType()));
        }
        
        //harvest methods
        ArrayList<HarvestMethodDTO> hms = dao.getHarvestMethods();
        harvestMethods.add(new SelectItem("", ""));
        for(HarvestMethodDTO dto : hms){
            harvestMethods.add(new SelectItem(dto.getHarvestMethod_key(), dto.getHarvestMethod()));
        }
        
        //time units
        ArrayList<TimeUnitDTO> tus = dao.getTimeUnits();
        for(TimeUnitDTO tu : tus){
            timeUnit = tus.get(0).getTimeUnit_key();
            timeUnits.add(new SelectItem(tu.getTimeUnit_key(),tu.getTimeUnit()));
        }
        
        //weight units
        ArrayList<WeightUnitDTO> wus = dao.getWeightUnits();
        for(WeightUnitDTO wu : wus){
            weightUnit = wus.get(0).getWeightUnit_key();
            weightUnits.add(new SelectItem(wu.getWeightUnit_key(),wu.getWeightUnit()));
        }
        
        //sample statuses
        ArrayList<SampleStatusDTO> SampStats = dao.getSampleStatuses();
        for(SampleStatusDTO stat : SampStats){
            sampleStatus = SampStats.get(0).getSampleStatus_key();
            sampleStatuses.add(new SelectItem(stat.getSampleStatus_key(),stat.getSampleStatus()));
        }
        
        //epochs
        ArrayList<EpochDTO> theEpochs = dao.getEpochs();
        for(EpochDTO e : theEpochs){
            epoch = theEpochs.get(0).getEpoch_key();
            epochs.add(new SelectItem(e.getEpoch_key(), e.getEpoch()));
        }
        
        sampleTypes = null;
        preservationTypes = null;
        preservationMethods = null;
        preservationDetails = null;
        
        //set up locations
        buildTree();
    }
    
    private void buildTree(){
        root = new DefaultTreeNode();
        ArrayList<StorageFacilityDTO> storages = locationDAO.getAllStorageFacilities();
        for(StorageFacilityDTO storageFacility : storages){
            TreeNode storageNode = new DefaultTreeNode("storage", storageFacility, root);
            HashMap<String, ArrayList<LocationTypeDTO>> hm = locationDAO.getLocationTypesMapByStorageFacility(storageFacility.getStorageFacility_key());
            if(hm.get("0")!=null){
                for(LocationTypeDTO dto : (ArrayList<LocationTypeDTO>) hm.get("0")){
                    DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, storageNode);
                    locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
                }
            }
        }
    }
    
    public String editSample(){        
        System.out.println("Edit Sample action");
        System.out.println("Sample Key " + this.getKey("paramSampleKey"));
        
        this.sampleID = dao.getSampleIDByKey(this.getKey("paramSampleKey").toString());
        sampleIDChangeListener();
        return "editSample";
    }
    
    public void enableSampleID(){
        sampleIDDisabled = !sampleIDDisabled;
    }
    
    public void sampleIDChangeListener(){
        sampleID = sampleID.trim();
        
        SampleXtraDTO sample = dao.getSampleByID(sampleID);
        if(sample != null){
            
            mouseSources = new ArrayList<MouseEntity>();
            matingSources = new ArrayList<MatingEntity>();
            litterSources = new ArrayList<LitterEntity>();
            sampleSources = new ArrayList<SampleXtraDTO>();
            
            StorageDTO storage = sample.getStorage();

            sampleID = sample.getSampleID();
            for(OwnerEntity o : (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst")){
                if(o.getOwner().equals(sample.getOwner())){
                    owner = o;
                }
            }
            age = sample.getAge();
            description = sample.getDescription();
            epoch = sample.getEpoch_key();
            harvestMethod = sample.getHarvestMethod();
            sampleDate = sample.getSampleDate();
            sampleDateType = sample.getSampleDateType_key();

            buildTree();
            location = locationDAO.getLocationTypeByKey(sample.getLocationType_key());
            //build a method that walks tree and expands parents of the location dto
            findLocation(root, location);
            showLocation(nodeLocation);
            nodeLocation.setSelected(true);
            //based on sample type get sample class and set drop down options
            sampleClass = dao.getSampleTypeSampleClassKey(sample.getSampleType_key());
            sampleClassChangeListener();
            sampleType = sample.getSampleType_key();

            preservationType = storage.getPreservationType_key();
            this.preservationTypeChangeListener();
            preservationMethod = storage.getPreservationMethod_key();
            preservationDetail = storage.getPreservationDetail_key();
            this.preservationMethodChangeListener();


            sampleStrain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(new Integer(Integer.parseInt(sample.getStrain_key()))));
            timeUnit = sample.getTimeUnit_key();
            weight = sample.getWeight();

            //sources
            sourceType = sample.getSourceType();
            if(sourceType.equals("1")){
                sample.getParentSample_key();
            }
            else if(sourceType.equals("2")){
                mouseSources = (ArrayList<MouseEntity>) sample.getMouseSources().clone();
                if(mouseSources.size() > 0){
                    sourceTypeDisabled = true;
                }
            }
            else if(sourceType.equals("3")){
                litterSources = (ArrayList<LitterEntity>) sample.getLitterSources().clone();
                if(litterSources.size() > 0){
                    sourceTypeDisabled = true;
                }
            }
            else if(sourceType.equals("4")){
                matingSources = (ArrayList<MatingEntity>) sample.getMatingSources().clone();
                if(matingSources.size() > 0){
                    sourceTypeDisabled = true;
                }
            }  
            else if(sourceType.equals("5")){
                sourceTypeDisabled = false;
            }
            determineCalculateAgeDisabled();
            determineSourceTypeDisabled();   
            sampleIDDisabled = true;
        }
        else{
             this.addToMessageQueue("Sample with ID " + sampleID + " could not be found.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void findLocation(TreeNode node, LocationTypeDTO location){
        if(node.getChildCount() > 0){
            for(TreeNode child : node.getChildren()){
                findLocation(child, location);
                if(child.getData() instanceof LocationTypeDTO){
                    LocationTypeDTO dto = (LocationTypeDTO) child.getData();
                    if(dto.getLocationType_key().equals(location.getLocationType_key())){
                        nodeLocation = child;
                        System.out.println(location.getLocationType());
                    }
                }   
            }
        }        
    }
    
    private void showLocation(TreeNode node){
        if(node.getParent() != null){
            node.getParent().setExpanded(true);
            System.out.println("parent found.");
            showLocation(node.getParent());
        }
        System.out.println("parent found.");
    }
    
    private void determineSourceTypeDisabled(){
        sourceTypeDisabled = false;
        if(sourceType.equals("1")){
            if(sampleSources.size() > 0){
                sourceTypeDisabled = true;
            }
        }
        else if(sourceType.equals("2")){
            if(mouseSources.size() > 0){
                sourceTypeDisabled = true;
            }            
        }
        else if(sourceType.equals("3")){
            if(litterSources.size() > 0){
                sourceTypeDisabled = true;
            }
            
        }
        else if(sourceType.equals("4")){
            if(matingSources.size() > 0){
                sourceTypeDisabled = true;
            }            
        }
        else if(sourceType.equals("5")){
                     
        }
    }
    
    private void determineCalculateAgeDisabled(){
        calculateAgeDisabled = false;
        if(sourceType.equals("1")){
            if(sampleSources.isEmpty()){
                sourceTypeDisabled = false;
                calculateAgeDisabled = true;
            }
            else{
                calculateAgeDisabled = false;
                Date sd = sampleSources.get(0).getSampleDate();
                for(SampleXtraDTO dto: sampleSources){
                    if(!sd.equals(dto.getSampleDate())){
                        calculateAgeDisabled = true;
                    }
                }
            }
        }
        else if(sourceType.equals("2")){
            if(mouseSources.isEmpty()){
                calculateAgeDisabled = true;
            }
            else{
                calculateAgeDisabled = false;
                Date bd = mouseSources.get(0).getBirthDate();
                for(MouseEntity me: mouseSources){
                    if(!bd.equals(me.getBirthDate())){
                        calculateAgeDisabled = true;
                    }
                }
            }
        }
        else if(sourceType.equals("3")){
            if(litterSources.isEmpty()){
                calculateAgeDisabled = true;
            }
            else{
                calculateAgeDisabled = false;
                Date bd = litterSources.get(0).getBirthDate();
                for(LitterEntity le: litterSources){
                    if(!bd.equals(le.getBirthDate())){
                        calculateAgeDisabled = true;
                    }
                }
            }
        }
        else if(sourceType.equals("4")){
            if(matingSources.isEmpty()){
                calculateAgeDisabled = true;
            }
            else{
                calculateAgeDisabled = false;
                Date bd = matingSources.get(0).getMatingDate();
                for(MatingEntity me: matingSources){
                    if(!bd.equals(me.getMatingDate())){
                        calculateAgeDisabled = true;
                    }
                }
            }           
        }
        else if(sourceType.equals("5")){
            calculateAgeDisabled = true;
        }
    }
        
    private void locationTreeBuild(String key, HashMap<String, ArrayList<LocationTypeDTO>> hm, DefaultTreeNode mom){
        ArrayList<LocationTypeDTO> list = hm.get(key);
        if(list != null){
            for(LocationTypeDTO dto : list){
                DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, mom);
                locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
            }
        }
    }
    
    public ArrayList<String> myComplete(String query){
        ArrayList<String> results = new ArrayList<String>();
        for(String id : sourceList){
            if(id.contains(query)){
                results.add(id);
            }
        }
        return results;
    }
    
    public void changeSampleID(){
        try{
            newID = newID.trim();
            if(!dao.sampleIDExists(sampleID)){
                this.addToMessageQueue("Sample with ID " + sampleID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                return;
            }
            if(newID.equals("")){
                this.addToMessageQueue("New ID is required, please provide a new ID.", FacesMessage.SEVERITY_ERROR);
                return;
            }
            if(!dao.sampleIDExists(newID)){
                dao.changeSampleID(newID, dao.getSampleKeyByID(sampleID));
                this.addToMessageQueue("Sample ID changed from " + sampleID + " to " + newID, FacesMessage.SEVERITY_INFO);
                sampleID = newID;
                newID = "";
            }
            else{
                this.addToMessageQueue("Sample with ID " + newID +" already exists, please provide a different ID.", FacesMessage.SEVERITY_ERROR);
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) {  
        if(event.getTreeNode().getData() instanceof LocationTypeDTO){
            System.out.println("Valid Node Selected");
            location = (LocationTypeDTO) event.getTreeNode().getData();
        }
        else{
            System.out.println("Invalid Node Selected");            
            this.addToMessageQueue("The selected node is not a valid location, valid locations are denoted by the darker icon.", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void deleteSampleSource(){
        sampleSources.remove(this.getKey("paramRowIndex").intValue());
        if(sampleSources.isEmpty()){
            setSourceTypeDisabled(false);
        }
    }
    
    public void deleteMouseSource(){
        mouseSources.remove(this.getKey("paramRowIndex").intValue());
        if(mouseSources.isEmpty()){
            sourceTypeDisabled = false;
            calculateAgeDisabled = true;
        }
        else{
            calculateAgeDisabled = false;
            Date bd = mouseSources.get(0).getBirthDate();
            for(MouseEntity me: mouseSources){
                if(!bd.equals(me.getBirthDate())){
                    calculateAgeDisabled = true;
                }
            }
        }
    }
    
    public String returnToSearchAction(){
        return "sampleList";
    }
    
    public void deleteMatingSource(){
        matingSources.remove(this.getKey("paramRowIndex").intValue());
        if(matingSources.isEmpty()){
            sourceTypeDisabled = false;
            calculateAgeDisabled = true;
        }
        else{
            calculateAgeDisabled = false;
            Date md = matingSources.get(0).getMatingDate();
            for(MatingEntity me: matingSources){
                if(!md.equals(me.getMatingDate())){
                    calculateAgeDisabled = true;
                }
            }
        }
    }
    
    public void deleteLitterSource(){
        litterSources.remove(this.getKey("paramRowIndex").intValue());
        if(litterSources.isEmpty()){
            sourceTypeDisabled = false;
            calculateAgeDisabled = true;
        }
        else{
            calculateAgeDisabled = false;
            Date bd = litterSources.get(0).getBirthDate();
            for(LitterEntity le: litterSources){
                if(!bd.equals(le.getBirthDate())){
                    calculateAgeDisabled = true;
                }
            }
        }
    }
    
    public void sampleClassChangeListener(){
        if(!sampleClass.equals("")){
            //sample class changes, get correct sample types and create new drop down
            ArrayList<SampleTypeDTO> sampTypes = dao.getSampleTypesBySampleClassKey(sampleClass);
            sampleTypes = new ArrayList<SelectItem>();
            sampleTypes.add(new SelectItem("", ""));
            for(SampleTypeDTO dto : sampTypes){
                sampleTypes.add(new SelectItem(dto.getSampleType_key(), dto.getSampleType()));
            }
        
        
            //get preservation types that correspond to the new sample class and create new dropdown
            ArrayList<PreservationTypeDTO> presTypes = dao.getPreservationTypes(sampleClass);
            setPreservationTypes(new ArrayList<SelectItem>());
            if(presTypes.size() < 1){
                setPreservationTypes(null);
                preservationType = "";
            }
            else{
                preservationType = "";
                preservationTypes.add(new SelectItem("", ""));
                for(PreservationTypeDTO dto : presTypes){
                    preservationTypes.add(new SelectItem(dto.getPreservationType_key(),dto.getPreservationType()));
                }
            }
            preservationMethods = null;
            preservationMethod = "";
            preservationDetails = null;
            preservationDetail = "";
        }
        else{
            sampleTypes = null;
            sampleType = "";
            preservationTypes = null;
            preservationType = "";
            preservationMethods = null;
            preservationMethod = "";
            preservationDetails = null;
            preservationDetail = "";
        }
    }
    
    public void preservationMethodChangeListener(){
        ArrayList<PreservationDetailDTO> presDetails;
        if(!preservationMethod.equals("")){
            presDetails = dao.getPreservationDetails(preservationMethod);
        }
        else{
            presDetails = new ArrayList<PreservationDetailDTO>();
        }
        if(presDetails.size() > 0){
            preservationDetails = new ArrayList<SelectItem>();
            preservationDetails.add(new SelectItem("", ""));
            for(PreservationDetailDTO dto : presDetails){
                preservationDetails.add(new SelectItem(dto.getPreservationDetail_key(), dto.getPreservationDetail()));
            }
        }
        else{
            preservationDetails = null;
            preservationDetail = "";
        }
    }
    
    public void preservationTypeChangeListener(){
        ArrayList<PreservationMethodDTO> presMethods;
        if(!preservationType.equals("")){
            presMethods = dao.getPreservationMethods(preservationType);
        }
        else{
            presMethods = new ArrayList<PreservationMethodDTO>();
        }
        if(presMethods.size() > 0){
            preservationMethods = new ArrayList<SelectItem>();
            preservationMethods.add(new SelectItem("", ""));
            preservationMethod = "";
            for(PreservationMethodDTO dto : presMethods){
                preservationMethods.add(new SelectItem(dto.getPreservationMethod_key(), dto.getPreservationMethod()));
            }
            preservationDetails = null;
            preservationDetail = "";
        }
        else{
            preservationMethods = null;
            preservationMethod = "";
            preservationDetails = null;
            preservationDetail = "";
        }
    }
    
    public void ageFromChangeListener(){
        if(!isCalculateAgeDisabled()){
            String theAgeFrom = getValueFromKey(epochs, epoch);
            if(!theAgeFrom.equalsIgnoreCase("From Birth")){
                setCalculateAgeDisabled(true);
            }
            else{
                setCalculateAgeDisabled(false);
            }
        }
    }
    
    private String getValueFromKey(ArrayList<SelectItem> theList, String key){
        if(theList != null){
            for(SelectItem si : theList){
                String val = (String) si.getValue();
                if(val.equals(key)){
                    return si.getLabel();
                }
            }
        }
        return "";
    }
    
    
    
    public void updateSample(){
        //first check that the values are all ok:
        boolean flag = false;
        sampleID = sampleID.trim();
        if(sampleID == null || !dao.sampleIDExists(sampleID)){
            flag = true;
            this.addToMessageQueue("Sample " + sampleID + " could not be found.", FacesMessage.SEVERITY_ERROR);     
        }
        if(sampleSources.isEmpty() && mouseSources.isEmpty() && matingSources.isEmpty() && litterSources.isEmpty() && !sourceType.equals("5")){
            flag = true;
            this.addToMessageQueue("Sample sources cannot be empty, add a sample source.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleStrain == null && sourceType.equals("5")){
            flag = true;
            this.addToMessageQueue("If your sample source type is other you must select a sample strain.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleID.length() > 32){
            flag = true;
            this.addToMessageQueue("Sample ID is too long.", FacesMessage.SEVERITY_ERROR);
        }
        if(owner ==  null){
            flag = true;
            this.addToMessageQueue("Owner is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleClass == null || sampleClass.equals("")){
            flag = true;
            this.addToMessageQueue("Sample Class is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleType == null || sampleType.equals("")){
            flag = true;
            this.addToMessageQueue("Sample Type is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleDate == null){
            flag = true; 
            this.addToMessageQueue("Sample Date is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleDate == null || sampleDate.equals("")){
            flag = true;
            this.addToMessageQueue("Sample date type is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(weight == null || weight.equals("")){
            flag = true;
            this.addToMessageQueue("Weight is required.", FacesMessage.SEVERITY_ERROR);
        }
        try{
            Float.parseFloat(weight);
        }
        catch(Exception e){
            flag = true;
            this.addToMessageQueue("Weight could not be understood as a number, please check the weight value.", FacesMessage.SEVERITY_ERROR);
        }
        if(weightUnit == null || weightUnit.equals("")){
            flag = true;
            this.addToMessageQueue("Weight Units are required.", FacesMessage.SEVERITY_ERROR);
        }
        if(age == null || age.equals("")){
            flag = true;
            this.addToMessageQueue("Age is required.", FacesMessage.SEVERITY_ERROR);
        }        
        try{
            Float.parseFloat(age);
        }
        catch(Exception e){
            flag = true;
            this.addToMessageQueue("Age could not be understood as a number, please check the age value.", FacesMessage.SEVERITY_ERROR);
        }
        if(timeUnit == null || timeUnit.equals("")){
            flag = true;
            this.addToMessageQueue("Time units are required.", FacesMessage.SEVERITY_ERROR);
        }       
        if(epoch == null || epoch.equals("")){
            flag = true;
            this.addToMessageQueue("Age From is required.", FacesMessage.SEVERITY_ERROR);
        }       
        if(sampleStatus == null || sampleStatus.equals("")){
            flag = true;
            this.addToMessageQueue("Sample status is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(location == null){
            flag = true;
            this.addToMessageQueue("Location is required, please select a location from the Location tree.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //part one of update, update the sample portion, part two, update the Source portion
            try{
                SampleXtraDTO originalSample = dao.getSampleByID(sampleID);
                if(!originalSample.getAge().equals(age)){
                    originalSample.setAge(age);
                }
                if(!originalSample.getDescription().equals(description)){
                    originalSample.setDescription(description);
                }
                if(!originalSample.getEpoch_key().equals(epoch)){
                    originalSample.setEpoch_key(epoch);
                }   
                if(!originalSample.getHarvestMethod().equals(harvestMethod)){
                    originalSample.setHarvestMethod(harvestMethod);
                }  
                if(location != null && !originalSample.getLocationType_key().equals(location.getLocationType_key())){
                    originalSample.setLocationType_key(location.getLocationType_key());
                }         
                if(!originalSample.getStorage().getPreservationDetail_key().equals(preservationDetail)){
                    originalSample.getStorage().setPreservationDetail_key(preservationDetail);
                }
                if(!originalSample.getStorage().getPreservationMethod_key().equals(preservationMethod)){
                    originalSample.getStorage().setPreservationMethod_key(preservationMethod);
                }
                if(!originalSample.getStorage().getPreservationType_key().equals(preservationType)){
                    originalSample.getStorage().setPreservationType_key(preservationType);
                }
                if(!originalSample.getStorage().getSampleStatus_key().equals(sampleStatus)){
                    originalSample.getStorage().setSampleStatus_key(sampleStatus);
                }
                if(!originalSample.getOwner().equals(owner.getOwner())){
                    originalSample.setOwner(owner.getOwner());
                }
                if(!originalSample.getSampleDate().equals(sampleDate)){
                    originalSample.setSampleDate(sampleDate);
                }
                if(!originalSample.getSampleDateType_key().equals(sampleDateType)){
                    originalSample.setSampleDateType_key(sampleDateType);
                }
                if(!originalSample.getSampleType_key().equals(sampleType)){
                    originalSample.setSampleType_key(sampleType);
                }
                if(!originalSample.getStrain_key().equals(sampleStrain.getStrainKey().toString())){
                    originalSample.setStrain_key(sampleStrain.getStrainKey().toString());
                }
                if(!originalSample.getTimeUnit_key().equals(timeUnit)){
                    originalSample.setTimeUnit_key(timeUnit);
                }
                if(!originalSample.getWeight().equals(weight)){
                    originalSample.setWeight(weight);
                }
                if(!originalSample.getWeightUnit_key().equals(weightUnit)){
                    originalSample.setWeightUnit_key(weightUnit);
                }

                //sources
                if(!originalSample.getSourceType().equals(sourceType)){
                    //the source type has changed, update the sources
                    //source type was sample source, 
                    if(originalSample.getSourceType().equals("1")){
                        originalSample.setSample_key("0");
                    }
                    if(originalSample.getSourceType().equals("2")){
                        dao.deleteMouseSources(originalSample.getSample_key());                    
                    }
                    if(originalSample.getSourceType().equals("3")){
                        dao.deleteLitterSources(originalSample.getSample_key());
                    }
                    if(originalSample.getSourceType().equals("4")){
                        dao.deleteMatingSources(originalSample.getSample_key());
                    }
                    //if sample source type is other, nothing to delete/change, just adding
                    if(originalSample.getSourceType().equals("5")){

                    }

                    if(sourceType.equals("1")){
                        originalSample.setParentSample_key(sampleSources.get(0).getSample_key());
                        originalSample.setSourceType(sourceType);
                    }
                    else if(sourceType.equals("2")){
                        originalSample.setMouseSources(mouseSources);
                        originalSample.setSourceType(sourceType);
                        dao.insertSources(originalSample);
                    }
                    else if(sourceType.equals("3")){
                        originalSample.setLitterSources(litterSources);
                        originalSample.setSourceType(sourceType);
                        dao.insertSources(originalSample);
                    }
                    else if(sourceType.equals("4")){
                        originalSample.setMatingSources(matingSources);
                        originalSample.setSourceType(sourceType);
                        dao.insertSources(originalSample);
                    }
                    else if(sourceType.equals("5")){
                        //only identifier is strain which was set earlier
                    }
                    originalSample.setSourceType(sourceType);
                }
                else{
                    //if the source type didn't change, check to see if the 
                    if(sourceType.equals("1")){
                        originalSample.setParentSample_key(sampleSources.get(0).getSample_key());
                    }
                    else if(sourceType.equals("2")){
                        if(!mouseSources.equals(originalSample.getMouseSources())){
                            dao.deleteMouseSources(originalSample.getSample_key());
                            originalSample.setMouseSources(mouseSources);
                            dao.insertSources(originalSample);
                        }
                    }
                    else if(sourceType.equals("3")){
                        if(!litterSources.equals(originalSample.getLitterSources())){
                            dao.deleteLitterSources(originalSample.getSample_key());
                            originalSample.setLitterSources(litterSources);
                            dao.insertSources(originalSample);
                        }
                    }
                    else if(sourceType.equals("4")){
                        if(!matingSources.equals(originalSample.getMatingSources())){
                            dao.deleteMouseSources(originalSample.getSample_key());
                            originalSample.setMatingSources(matingSources);
                            dao.insertSources(originalSample);
                        }
                    }
                    else if(sourceType.equals("5")){
                        //only identifier is strain which was set earlier                    
                    }
                }
                dao.updateSample(originalSample);
                this.addToMessageQueue("Sample " + sampleID + " successfully updated.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR); 
            }
        }
    }
    
    public void clearSources(){
        if(sourceType.equals("1")){
            sampleSources.clear();
        }
        else if(sourceType.equals("2")){
            mouseSources.clear();
        }
        else if(sourceType.equals("3")){
            litterSources.clear();
        }
        else if(sourceType.equals("4")){
            matingSources.clear();
        }
        sourceTypeDisabled = false;
        calculateAgeDisabled = true;
    }
    
    public void addSampleSource(){
        boolean relaxed = true;
        boolean flag = false;
        if(sourceType.equals("1")){
            for(SampleXtraDTO dto : sampleSources){
                if(dto.getSampleID().equalsIgnoreCase(sourceID)){
                    flag = true;
                    this.addToMessageQueue("This source is already in the source list.", FacesMessage.SEVERITY_ERROR);
                }
            }
            if(sampleSources.size() >= 1){
                flag = true;
                this.addToMessageQueue("You can only have one sample source, add samples below then clear the source grid and select another source.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(sourceType.equals("2")){
            for(MouseEntity me : mouseSources){
                if(me.getId().equalsIgnoreCase(sourceID)){
                    flag = true;
                    this.addToMessageQueue("This source already exists in the list.", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        if(sourceType.equals("3")){
            for(LitterEntity le : litterSources){
                if(le.getLitterID().equalsIgnoreCase(sourceID)){
                    flag = true;
                    this.addToMessageQueue("This source already exists in the list.", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        if(sourceType.equals("4")){
            for(MatingEntity me : matingSources){
                if(new Integer(me.getMatingID()).toString().equals(sourceID)){
                    flag = true;
                    this.addToMessageQueue("This source already exists in the list.", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        if(sourceID.equals("")){
            flag=true;
            this.addToMessageQueue("Source ID is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            if(sourceType.equals("1")){
                try{
                    //get that sample information according to the ID
                    SampleXtraDTO dto = dao.getSampleByID(sourceID);
                    if(dto != null){
                        if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), sampleSource.getOwner())){
                            this.addToMessageQueue("You are not the owner of that sample.", FacesMessage.SEVERITY_ERROR);
                            sampleSource = null;
                            return;
                        }
                        if(sampleSources.size() > 0){
                            String strainKey = dto.getStrain_key();
                            //check that sample source to be added is of same strain as other samples
                            for(SampleXtraDTO SXdto : sampleSources){
                                if(!SXdto.getStrain_key().equals(strainKey)){
                                    flag = true;
                                }
                            }
                        }
                        //if relaxed go ahead, elsewise check to make sure that the strain test above was passed (flag == false)
                        if(relaxed || !flag){
                            sampleSources.add(dto);
                            if(!relaxed){
                                try{
                                    sampleStrain = (StrainEntity) getRepositoryService().baseFind(new StrainEntity(new Integer(Integer.parseInt(sampleSource.getStrain_key()))));
                                }
                                catch(Exception e){
                                    
                                }
                            }
                        }
                        else{
                            this.addToMessageQueue("In order to add a source of a different strain 'Relax Single Strain Constraint' must be selected.", FacesMessage.SEVERITY_ERROR);
                        }
                    }
                    else{
                        this.addToMessageQueue("Sample of ID " + sourceID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                        return;
                    }
                }
                catch(Exception e){
                    this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
            else if(sourceType.equals("2")){
                this.sourceIDChangeListener();
                if(mouseSource != null){
                    if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), mouseSource.getOwner())){
                        this.addToMessageQueue("You are not the owner of that mouse.", FacesMessage.SEVERITY_ERROR);
                        mouseSource = null;
                        return;
                    }
                    for(MouseEntity me : mouseSources){
                        if(!me.getStrainKey().equals(mouseSource.getStrainKey())){
                            flag = true;
                        }
                    }
                    if(relaxed || !flag){
                        calculateAgeDisabled = false;
                        mouseSources.add(mouseSource);
                        if(mouseSources.size() > 0){
                            calculateAgeDisabled = false;
                            Date bd = mouseSources.get(0).getBirthDate();
                            for(MouseEntity me : mouseSources){
                                if(!me.getBirthDate().equals(bd)){
                                    calculateAgeDisabled = true;
                                }
                            }
                        }
                        else{
                            calculateAgeDisabled = true;
                        }
                        if(!relaxed){
                            sampleStrain = mouseSource.getStrainKey();                           
                        }
                    }
                    else{
                        this.addToMessageQueue("In order to add a source of a different strain 'Relax Single Strain Constraint' must be selected.", FacesMessage.SEVERITY_ERROR);
                    }
                }
                else{
                    this.addToMessageQueue("Mouse with ID " + sourceID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
            else if(sourceType.equals("3")){
                this.sourceIDChangeListener();
                if(litterSource != null){
                    if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), litterSource.getMatingKey().getOwner())){
                        this.addToMessageQueue("You are not the owner of that litter.", FacesMessage.SEVERITY_ERROR);
                        litterSource = null;
                        return;
                    }
                    for(LitterEntity le : litterSources){
                        if(!le.getMatingKey().getStrainKey().equals(litterSource.getMatingKey().getStrainKey())){
                            flag = true;
                        }
                    }
                    if(relaxed || !flag){
                        litterSources.add(litterSource);
                        if(litterSources.size() > 0){
                            calculateAgeDisabled = false;
                            Date bd = litterSources.get(0).getBirthDate();
                            for(LitterEntity le : litterSources){
                                if(!le.getBirthDate().equals(bd)){
                                    calculateAgeDisabled = true;
                                }
                            }
                        }
                        else{
                            calculateAgeDisabled = true;
                        }
                        if(!relaxed){
                            sampleStrain = litterSource.getMatingKey().getStrainKey();
                        }
                    }
                    else{
                        this.addToMessageQueue("In order to add a source of a different strain 'Relax Single Strain Constraint' must be selected.", FacesMessage.SEVERITY_ERROR);
                    }
                }
                else{
                    this.addToMessageQueue("Litter with ID " + sourceID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
            else if(sourceType.equals("4")){
                this.sourceIDChangeListener();
                if(matingSource != null){
                    if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), matingSource.getOwner())){
                        this.addToMessageQueue("You are not the owner of that mating.", FacesMessage.SEVERITY_ERROR);
                        matingSource = null;
                        return;
                    }
                    for(MatingEntity me : matingSources){
                        if(!me.getStrainKey().equals(matingSource.getStrainKey())){
                            flag = true;
                        }
                    }
                    if(relaxed || !flag){
                        matingSources.add(matingSource);
                        if(matingSources.size() > 0){
                            calculateAgeDisabled = false;
                            Date md = matingSources.get(0).getMatingDate();
                            for(MatingEntity me : matingSources){
                                if(!me.getMatingDate().equals(md)){
                                    calculateAgeDisabled = true;
                                }
                            }
                        }
                        else{
                            calculateAgeDisabled = true;
                        }
                        if(!relaxed){
                            sampleStrain = matingSource.getStrainKey();
                        }
                    }
                    else{
                        this.addToMessageQueue("In order to add a source of a different strain 'Relax Single Strain Constraint' must be selected.", FacesMessage.SEVERITY_ERROR);
                    }
                }
                else{
                    this.addToMessageQueue("Mating with ID " + sourceID + " could not be found.", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
            else if(sourceType.equals("5")){
                
            }
            sourceTypeDisabled = true;
        }
    }
    
    public void sampleStrainValueChangeListener(){
        if(sampleStrain != null){
            sourceList = new ArrayList<String>();
            if(sourceType.equals("1")){
                sourceList = dao.getSampleIDsByStrain(sampleStrain.getStrainKey().toString(), 
                        (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));
            }
            else if(sourceType.equals("2")){
                sourceList = dao.getMouseIDsByStrain(sampleStrain.getStrainKey().toString(), 
                        (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
            }
            else if(sourceType.equals("3")){
                sourceList = dao.getLitterIDsByStrain(sampleStrain.getStrainKey().toString(), 
                        (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
            }
            else if(sourceType.equals("4")){
                sourceList = dao.getMatingIDsByStrain(sampleStrain.getStrainKey().toString(), 
                        (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
            }
            else if(sourceType.equals("5")){
                sourceID = "";
            }
            //build selectitems if source type isn't "other"
            if(!sourceType.equals("5")){}
        }
        else{
            sourceID = "";
        }
    }
    
    public void calculateSampleAge(){
        int days = 0;
        if(timeUnit == null){
             this.addToMessageQueue("You must choose a time unit to calculate age.", FacesMessage.SEVERITY_ERROR);
             return;
        }
        if(sourceType.equals("2")){
            Date date = mouseSources.get(0).getBirthDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            if(cal.before(today)){
                while(cal.before(today)){
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    days++;
                }
            }
            else{
                System.out.println("birthdate after today....");
            }
        }
        else if(sourceType.equals("3")){
            Date date = litterSources.get(0).getBirthDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            if(cal.before(today)){
                while(cal.before(today)){
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    days++;
                }
            }
            else{
                System.out.println("birthdate after today....");
            }
        }
        else if(sourceType.equals("4")){
            Date date = matingSources.get(0).getMatingDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            if(cal.before(today)){
                while(cal.before(today)){
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    days++;
                }
            }
            else{
                System.out.println("birthdate after today....");
            }
        }
        ArrayList<TimeUnitDTO> tus = dao.getTimeUnits();
        TimeUnitDTO tuDTO = null;
        for(TimeUnitDTO dto : tus){
            if(dto.getTimeUnit_key().equals(timeUnit)){
                tuDTO = dto;
            }
        }
        int minutes = days*1440;
        try{
            int minutesPerUnit = Integer.parseInt(tuDTO.getMinutesPerUnit());
            age = new Integer(minutes/minutesPerUnit).toString();
        }
        catch(Exception e){
            System.out.println(e);
            this.addToMessageQueue("Age could not be calculated, you'll have to enter it manually.", FacesMessage.SEVERITY_INFO);
        }
    }
    
    public void sourceTypeValueChangeListener(){
        this.sampleStrainValueChangeListener();
    }
    
    public void sourceIDChangeListener(){
        try{
            if(sourceType.equals("1")){
                sampleSource = null;
                if(sampleSources.size() > 1){
                    this.addToMessageQueue("You can only have one sample source, add samples below then clear the source grid and select another source.", FacesMessage.SEVERITY_WARN);
                }
                else{
                    sampleSource = dao.getSampleByID(sourceID);                    
                }
            }
            else if(sourceType.equals("2")){
                mouseSource = null;
                String mouseKey = dao.getMouseKeyByID(sourceID);
                if(!mouseKey.equals("")){
                    mouseSource = (MouseEntity) getRepositoryService().baseFind(new MouseEntity(new Integer(Integer.parseInt(mouseKey))));
                }
            }
            else if(sourceType.equals("3")){
                litterSource = null;
                String litterKey = dao.getLitterKeyByID(sourceID);
                if(!litterKey.equals("")){
                    litterSource = (LitterEntity) getRepositoryService().baseFind(new LitterEntity(new Integer(Integer.parseInt(litterKey))));
                }
            }
            else if(sourceType.equals("4")){
                matingSource = null;
                String matingKey = dao.getMatingKeyByID(sourceID);
                if(!matingKey.equals("")){
                    matingSource = (MatingEntity) getRepositoryService().baseFind(new MatingEntity(new Integer(Integer.parseInt(matingKey))));
                }
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    /**
     * @return the sampleID
     */
    public String getSampleID() {
        return sampleID;
    }

    /**
     * @param sampleID the sampleID to set
     */
    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    /**
     * @return the owner
     */
    public OwnerEntity getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }

    /**
     * @return the sampleClass
     */
    public String getSampleClass() {
        return sampleClass;
    }

    /**
     * @param sampleClass the sampleClass to set
     */
    public void setSampleClass(String sampleClass) {
        this.sampleClass = sampleClass;
    }

    /**
     * @return the sampleClasses
     */
    public ArrayList<SelectItem> getSampleClasses() {
        return sampleClasses;
    }

    /**
     * @param sampleClasses the sampleClasses to set
     */
    public void setSampleClasses(ArrayList<SelectItem> sampleClasses) {
        this.sampleClasses = sampleClasses;
    }

    /**
     * @return the sampleType
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * @param sampleType the sampleType to set
     */
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    /**
     * @return the sampleTypes
     */
    public ArrayList<SelectItem> getSampleTypes() {
        return sampleTypes;
    }

    /**
     * @param sampleTypes the sampleTypes to set
     */
    public void setSampleTypes(ArrayList<SelectItem> sampleTypes) {
        this.sampleTypes = sampleTypes;
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
     * @return the sampleDateTypes
     */
    public ArrayList<SelectItem> getSampleDateTypes() {
        return sampleDateTypes;
    }

    /**
     * @param sampleDateTypes the sampleDateTypes to set
     */
    public void setSampleDateTypes(ArrayList<SelectItem> sampleDateTypes) {
        this.sampleDateTypes = sampleDateTypes;
    }

    /**
     * @return the harvestMethod
     */
    public String getHarvestMethod() {
        return harvestMethod;
    }

    /**
     * @param harvestMethod the harvestMethod to set
     */
    public void setHarvestMethod(String harvestMethod) {
        this.harvestMethod = harvestMethod;
    }

    /**
     * @return the harvestMethods
     */
    public ArrayList<SelectItem> getHarvestMethods() {
        return harvestMethods;
    }

    /**
     * @param harvestMethods the harvestMethods to set
     */
    public void setHarvestMethods(ArrayList<SelectItem> harvestMethods) {
        this.harvestMethods = harvestMethods;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return the weightUnit
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * @param weightUnit the weightUnit to set
     */
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    /**
     * @return the weightUnits
     */
    public ArrayList<SelectItem> getWeightUnits() {
        return weightUnits;
    }

    /**
     * @param weightUnits the weightUnits to set
     */
    public void setWeightUnits(ArrayList<SelectItem> weightUnits) {
        this.weightUnits = weightUnits;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the timeUnits
     */
    public ArrayList<SelectItem> getTimeUnits() {
        return timeUnits;
    }

    /**
     * @param timeUnits the timeUnits to set
     */
    public void setTimeUnits(ArrayList<SelectItem> timeUnits) {
        this.timeUnits = timeUnits;
    }

    /**
     * @return the epochs
     */
    public ArrayList<SelectItem> getEpochs() {
        return epochs;
    }

    /**
     * @param epochs the epochs to set
     */
    public void setEpochs(ArrayList<SelectItem> epochs) {
        this.epochs = epochs;
    }

    /**
     * @return the preservationType
     */
    public String getPreservationType() {
        return preservationType;
    }

    /**
     * @param preservationType the preservationType to set
     */
    public void setPreservationType(String preservationType) {
        this.preservationType = preservationType;
    }

    /**
     * @return the preservationTypes
     */
    public ArrayList<SelectItem> getPreservationTypes() {
        return preservationTypes;
    }

    /**
     * @param preservationTypes the preservationTypes to set
     */
    public void setPreservationTypes(ArrayList<SelectItem> preservationTypes) {
        this.preservationTypes = preservationTypes;
    }

    /**
     * @return the preservationMethod
     */
    public String getPreservationMethod() {
        return preservationMethod;
    }

    /**
     * @param preservationMethod the preservationMethod to set
     */
    public void setPreservationMethod(String preservationMethod) {
        this.preservationMethod = preservationMethod;
    }

    /**
     * @return the preservationMethods
     */
    public ArrayList<SelectItem> getPreservationMethods() {
        return preservationMethods;
    }

    /**
     * @param preservationMethods the preservationMethods to set
     */
    public void setPreservationMethods(ArrayList<SelectItem> preservationMethods) {
        this.preservationMethods = preservationMethods;
    }

    /**
     * @return the preservationDetail
     */
    public String getPreservationDetail() {
        return preservationDetail;
    }

    /**
     * @param preservationDetail the preservationDetail to set
     */
    public void setPreservationDetail(String preservationDetail) {
        this.preservationDetail = preservationDetail;
    }

    /**
     * @return the preservationDetails
     */
    public ArrayList<SelectItem> getPreservationDetails() {
        return preservationDetails;
    }

    /**
     * @param preservationDetails the preservationDetails to set
     */
    public void setPreservationDetails(ArrayList<SelectItem> preservationDetails) {
        this.preservationDetails = preservationDetails;
    }

    /**
     * @return the sampleStatus
     */
    public String getSampleStatus() {
        return sampleStatus;
    }

    /**
     * @param sampleStatus the sampleStatus to set
     */
    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    /**
     * @return the sampleStatuses
     */
    public ArrayList<SelectItem> getSampleStatuses() {
        return sampleStatuses;
    }

    /**
     * @param sampleStatuses the sampleStatuses to set
     */
    public void setSampleStatuses(ArrayList<SelectItem> sampleStatuses) {
        this.sampleStatuses = sampleStatuses;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the location
     */
    public LocationTypeDTO getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(LocationTypeDTO location) {
        this.location = location;
    }

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return the sampleStrain
     */
    public StrainEntity getSampleStrain() {
        return sampleStrain;
    }

    /**
     * @param sampleStrain the sampleStrain to set
     */
    public void setSampleStrain(StrainEntity sampleStrain) {
        this.sampleStrain = sampleStrain;
    }

    /**
     * @return the sourceID
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * @return the sampleDateType
     */
    public String getSampleDateType() {
        return sampleDateType;
    }

    /**
     * @param sampleDateType the sampleDateType to set
     */
    public void setSampleDateType(String sampleDateType) {
        this.sampleDateType = sampleDateType;
    }

    /**
     * @return the mouseSources
     */
    public ArrayList<MouseEntity> getMouseSources() {
        return mouseSources;
    }

    /**
     * @param mouseSources the mouseSources to set
     */
    public void setMouseSources(ArrayList<MouseEntity> mouseSources) {
        this.mouseSources = mouseSources;
    }

    /**
     * @return the matingSources
     */
    public ArrayList<MatingEntity> getMatingSources() {
        return matingSources;
    }

    /**
     * @param matingSources the matingSources to set
     */
    public void setMatingSources(ArrayList<MatingEntity> matingSources) {
        this.matingSources = matingSources;
    }

    /**
     * @return the litterSources
     */
    public ArrayList<LitterEntity> getLitterSources() {
        return litterSources;
    }

    /**
     * @param litterSources the litterSources to set
     */
    public void setLitterSources(ArrayList<LitterEntity> litterSources) {
        this.litterSources = litterSources;
    }

    /**
     * @return the sampleSources
     */
    public ArrayList<SampleXtraDTO> getSampleSources() {
        return sampleSources;
    }

    /**
     * @param sampleSources the sampleSources to set
     */
    public void setSampleSources(ArrayList<SampleXtraDTO> sampleSources) {
        this.sampleSources = sampleSources;
    }

    /**
     * @return the wrapper
     */
    public SelectItemWrapper getWrapper() {
        return wrapper;
    }

    /**
     * @param wrapper the wrapper to set
     */
    public void setWrapper(SelectItemWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * @return the timeUnit
     */
    public String getTimeUnit() {
        return timeUnit;
    }

    /**
     * @param timeUnit the timeUnit to set
     */
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * @return the epoch
     */
    public String getEpoch() {
        return epoch;
    }

    /**
     * @param epoch the epoch to set
     */
    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    /**
     * @return the sourceTypeDisabled
     */
    public boolean isSourceTypeDisabled() {
        return sourceTypeDisabled;
    }

    /**
     * @param sourceTypeDisabled the sourceTypeDisabled to set
     */
    public void setSourceTypeDisabled(boolean sourceTypeDisabled) {
        this.sourceTypeDisabled = sourceTypeDisabled;
    }

    /**
     * @return the calculateAgeDisabled
     */
    public boolean isCalculateAgeDisabled() {
        return calculateAgeDisabled;
    }

    /**
     * @param calculateAgeDisabled the calculateAgeDisabled to set
     */
    public void setCalculateAgeDisabled(boolean calculateAgeDisabled) {
        this.calculateAgeDisabled = calculateAgeDisabled;
    }

    /**
     * @return the sampleDate
     */
    public Date getSampleDate() {
        return sampleDate;
    }

    /**
     * @param sampleDate the sampleDate to set
     */
    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    /**
     * @return the newID
     */
    public String getNewID() {
        return newID;
    }

    /**
     * @param newID the newID to set
     */
    public void setNewID(String newID) {
        this.newID = newID;
    }

    /**
     * @return the sampleIDDisabled
     */
    public boolean isSampleIDDisabled() {
        return sampleIDDisabled;
    }

    /**
     * @param sampleIDDisabled the sampleIDDisabled to set
     */
    public void setSampleIDDisabled(boolean sampleIDDisabled) {
        this.sampleIDDisabled = sampleIDDisabled;
    }
    
    
    
}
