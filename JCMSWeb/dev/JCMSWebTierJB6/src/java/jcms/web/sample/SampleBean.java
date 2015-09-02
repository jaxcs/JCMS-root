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

import java.io.Serializable;
import jcms.web.common.SelectItemWrapper;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import jcms.integrationtier.dto.HarvestMethodDTO;
import jcms.integrationtier.dto.PreservationDetailDTO;
import jcms.integrationtier.dto.PreservationMethodDTO;
import jcms.integrationtier.dto.PreservationTypeDTO;
import jcms.integrationtier.dto.SampleClassDTO;
import jcms.integrationtier.dto.SampleDateTypeDTO;
import jcms.integrationtier.dto.SampleTypeDTO;
import jcms.integrationtier.dto.SampleStatusDTO;
import jcms.integrationtier.dto.WeightUnitDTO;
import jcms.integrationtier.dto.TimeUnitDTO;
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import java.util.Date;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.web.base.WTBaseBackingBean;
import javax.faces.event.ValueChangeEvent;
import jcms.integrationtier.dto.EpochDTO;
import jcms.integrationtier.dto.SampleXtraDTO;
import jcms.integrationtier.dao.LocationDAO;
import jcms.integrationtier.dto.StorageFacilityDTO;
import jcms.integrationtier.dto.LocationTypeDTO;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.event.NodeSelectEvent; 
import jcms.web.colonyManagement.MouseFunctionsBean;

/**
 *
 * @author mkamato
 */
public class SampleBean extends WTBaseBackingBean implements Serializable{
    
    //Sample source
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private String sourceType = "1";
    private StrainEntity sampleStrain = null;
    private String sourceID = "";
    private boolean relaxed = false;
    private MouseEntity mouseSource = null;
    private MatingEntity matingSource = null;
    private LitterEntity litterSource = null;
    private SampleXtraDTO sampleSource = null;
    private ArrayList<MouseEntity> mouseSources = new ArrayList<MouseEntity>();
    private ArrayList<MatingEntity> matingSources = new ArrayList<MatingEntity>();
    private ArrayList<LitterEntity> litterSources = new ArrayList<LitterEntity>();
    private ArrayList<SampleXtraDTO> sampleSources = new ArrayList<SampleXtraDTO>();
    private boolean sourceTypeDisabled = false;
    private boolean relaxedDisabled = false;
    private ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
    
    //Sample
    private String sampleID = "";
    private boolean increment = false;
    private OwnerEntity owner;
    private ArrayList<SelectItem> sampleClasses = null;
    private String sampleClassKey = "";
    private ArrayList<SelectItem> sampleTypes = null;
    private String sampleTypeKey = "";
    private String description = "";
    private Date sampleDate = null;
    private ArrayList<SelectItem> sampleDateTypes = new ArrayList<SelectItem>();
    private String sampleDateKey = "";
    private ArrayList<SelectItem> harvestMethods = new ArrayList<SelectItem>();
    private String harvestMethodKey = "";
    private String weight = "";
    private String weightUnit = "";
    private ArrayList<SelectItem> units = new ArrayList<SelectItem>();
    private String age = "";
    private String timeUnit = "";
    private ArrayList<SelectItem> timeUnits = new ArrayList<SelectItem>();
    private String ageFrom = "";
    private ArrayList<SelectItem> ageFromList = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> preservationTypes = null;
    private String preservationTypeKey = "";
    private ArrayList<SelectItem> preservationDetails = null;
    private String preservationDetailKey = "";
    private ArrayList<SelectItem> preservationMethods = null;
    private String preservationMethodKey = "";
    private String sampleStatus = "";
    private ArrayList<SelectItem> sampleStatuses = new ArrayList<SelectItem>();
    private boolean calculateAgeDisabled = true;
    private ArrayList<SampleXtraDTO> existingSamples = new ArrayList<SampleXtraDTO>();
    private ArrayList<String> sourceList = new ArrayList<String>();
    
    //location
    private TreeNode root = new DefaultTreeNode();
    private LocationDAO locationDAO = new LocationDAO();
    private LocationTypeDTO location = null;
    
    //not in the view
    private SampleDAO dao = new SampleDAO();
    
    public SampleBean() {
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
            sampleDateKey = sdtDTOs.get(0).getSampleDateType_key();
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
            units.add(new SelectItem(wu.getWeightUnit_key(),wu.getWeightUnit()));
        }
        
        //sample statuses
        ArrayList<SampleStatusDTO> SampStats = dao.getSampleStatuses();
        for(SampleStatusDTO stat : SampStats){
            sampleStatus = SampStats.get(0).getSampleStatus_key();
            sampleStatuses.add(new SelectItem(stat.getSampleStatus_key(),stat.getSampleStatus()));
        }
        
        //epochs
        ArrayList<EpochDTO> epochs = dao.getEpochs();
        for(EpochDTO epoch : epochs){
            ageFrom = epochs.get(0).getEpoch_key();
            ageFromList.add(new SelectItem(epoch.getEpoch_key(),epoch.getEpoch()));
        }
        
        sampleTypes = null;
        preservationTypes = null;
        preservationMethods = null;
        preservationDetails = null;
        
        //set up locations
        ArrayList<StorageFacilityDTO> storages = locationDAO.getAllStorageFacilities();
        for(StorageFacilityDTO storageFacility : storages){
            TreeNode storageNode = new DefaultTreeNode("storage", storageFacility, root);
            HashMap<String, ArrayList<LocationTypeDTO>> hm = locationDAO.getLocationTypesMapByStorageFacility(storageFacility.getStorageFacility_key());
            if(hm.get("0") != null){
                for(LocationTypeDTO dto : (ArrayList<LocationTypeDTO>) hm.get("0")){
                    DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, storageNode);
                    locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
                }
            }
        }
    }
    
    public void showExistingSamples(){
        if(sourceType.equals("1")){
            existingSamples = dao.showExistingSamplesBySample(sampleSources);
        }
        else if(sourceType.equals("2")){
            existingSamples = dao.showExistingSamplesByMouse(mouseSources);
        }
        else if(sourceType.equals("3")){
            existingSamples = dao.showExistingSamplesByLitter(litterSources);
        }
        else if(sourceType.equals("4")){
            existingSamples = dao.showExistingSamplesByMating(matingSources);
        }
        else if(sourceType.equals("5")){
            existingSamples = dao.showExistingSamplesByOther(sampleStrain);
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
    
    private void autoIncrementSampleID(){
        //first separate integer portion from string portion
        String wordPortion = "";
        String numberPortion = "";
        char[] sample = sampleID.toCharArray();
        for(Character c : sample){
            if(Character.isDigit(c)){
                numberPortion = numberPortion + c;
            }
            else{
                wordPortion = wordPortion + numberPortion + c;
                numberPortion = "";                
            }
        }
        if(numberPortion.equals("")){
            sampleID = sampleID+"1";
        }
        else{
            sampleID = wordPortion + new Integer(Integer.parseInt(numberPortion) + 1).toString();
        }
    }
    
    public void sampleIDChangeListener(){
        if(dao.sampleIDExists(sampleID)){
            this.addToMessageQueue("A sample with this ID already exists.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            
        }
    }
    
    public void sampleClassChangeListener(){
        if(!sampleClassKey.equals("")){
            //sample class changes, get correct sample types and create new drop down
            ArrayList<SampleTypeDTO> sampTypes = dao.getSampleTypesBySampleClassKey(sampleClassKey);
            sampleTypes = new ArrayList<SelectItem>();
            sampleTypes.add(new SelectItem("", ""));
            for(SampleTypeDTO dto : sampTypes){
                sampleTypes.add(new SelectItem(dto.getSampleType_key(), dto.getSampleType()));
            }
        
        
            //get preservation types that correspond to the new sample class and create new dropdown
            ArrayList<PreservationTypeDTO> presTypes = dao.getPreservationTypes(sampleClassKey);
            setPreservationTypes(new ArrayList<SelectItem>());
            if(presTypes.size() < 1){
                setPreservationTypes(null);
                preservationTypeKey = "";
            }
            else{
                preservationTypeKey = "";
                preservationTypes.add(new SelectItem("", ""));
                for(PreservationTypeDTO dto : presTypes){
                    preservationTypes.add(new SelectItem(dto.getPreservationType_key(),dto.getPreservationType()));
                }
            }
            preservationMethods = null;
            preservationMethodKey = "";
            preservationDetails = null;
            preservationDetailKey = "";
        }
        else{
            sampleTypes = null;
            sampleTypeKey = "";
            preservationTypes = null;
            preservationTypeKey = "";
            preservationMethods = null;
            preservationMethodKey = "";
            preservationDetails = null;
            preservationDetailKey = "";
        }
    }
    
    public void preservationMethodChangeListener(){
        ArrayList<PreservationDetailDTO> presDetails;
        if(!preservationMethodKey.equals("")){
            presDetails = dao.getPreservationDetails(preservationMethodKey);
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
            preservationDetailKey = "";
        }
    }
    
    public void preservationTypeChangeListener(){
        ArrayList<PreservationMethodDTO> presMethods;
        if(!preservationTypeKey.equals("")){
            presMethods = dao.getPreservationMethods(preservationTypeKey);
        }
        else{
            presMethods = new ArrayList<PreservationMethodDTO>();
        }
        if(presMethods.size() > 0){
            preservationMethods = new ArrayList<SelectItem>();
            preservationMethods.add(new SelectItem("", ""));
            preservationMethodKey = "";
            for(PreservationMethodDTO dto : presMethods){
                preservationMethods.add(new SelectItem(dto.getPreservationMethod_key(), dto.getPreservationMethod()));
            }
            preservationDetails = null;
            preservationDetailKey = "";
        }
        else{
            preservationMethods = null;
            preservationMethodKey = "";
            preservationDetails = null;
            preservationDetailKey = "";
        }
    }
        
    public void addSampleSource(){
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
                        if(sampleSources.size() > 0){
                            String strainKey = dto.getStrain_key();
                            //check ownership
                            if(!this.containsIgnoreCase(this.getCurrentUserColonyManageWorkgroups(), sampleSource.getOwner())){
                                this.addToMessageQueue("You are not the owner of that sample.", FacesMessage.SEVERITY_ERROR);
                                sampleSource = null;
                                return;
                            }                            
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
                //have to call sourceIDChangelistener because change event for autocomplete only gets typed chars, not completed ones.
                this.sourceIDChangeListener();
                if(mouseSource != null){
                    //check ownership
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
                //have to call sourceIDChangelistener because change event for autocomplete only gets typed chars, not completed ones.
                this.sourceIDChangeListener();
                if(litterSource != null){
                    //check ownership
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
                //have to call sourceIDChangelistener because change event for autocomplete only gets typed chars, not completed ones.
                this.sourceIDChangeListener();
                if(matingSource != null){
                    //check ownership
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
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void ageFromChangeListener(){
        if(!calculateAgeDisabled){
            String theAgeFrom = getValueFromKey(ageFromList, ageFrom);
            if(!theAgeFrom.equalsIgnoreCase("From Birth")){
                calculateAgeDisabled = true;
            }
            else{
                calculateAgeDisabled = false;
            }
        }
    }
    
    public void clearSources(){
        mouseSources.clear();
        matingSources.clear();
        litterSources.clear();
        sampleSources.clear();
        sourceTypeDisabled = false;
        calculateAgeDisabled = true;
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
    
    public void deleteSampleSource(){
        sampleSources.remove(this.getKey("paramRowIndex").intValue());
        if(sampleSources.isEmpty()){
            sourceTypeDisabled = false;
        }
    }
    
    public void addSample(){
        boolean flag = false;
        if(sampleSources.isEmpty() && mouseSources.isEmpty() && matingSources.isEmpty() && litterSources.isEmpty() && !sourceType.equals("5")){
            flag = true;
            this.addToMessageQueue("Sample sources cannot be empty, add a sample source.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleStrain == null && sourceType.equals("5")){
            flag = true;
            this.addToMessageQueue("If your sample source type is other you must select a sample strain.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleID == null || sampleID.equals("")){
            flag = true;
            this.addToMessageQueue("Sample ID is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleID.length() > 32){
            flag = true;
            this.addToMessageQueue("Sample ID is too long.", FacesMessage.SEVERITY_ERROR);
        }
        if(owner ==  null){
            flag = true;
            this.addToMessageQueue("Owner is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleClassKey == null || sampleClassKey.equals("")){
            flag = true;
            this.addToMessageQueue("Sample Class is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleTypeKey == null || sampleTypeKey.equals("")){
            flag = true;
            this.addToMessageQueue("Sample Type is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleDate == null){
            flag = true; 
            this.addToMessageQueue("Sample Date is required.", FacesMessage.SEVERITY_ERROR);
        }
        if(sampleDateKey == null || sampleDateKey.equals("")){
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
        if(ageFrom == null || ageFrom.equals("")){
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
        if(dao.sampleIDExists(sampleID)){
            flag = true;
            this.addToMessageQueue("A sample with this ID already exists.", FacesMessage.SEVERITY_ERROR);     
        }
        for(SampleXtraDTO dto : samples){
            if(dto.getSampleID().equals(sampleID)){
                flag = true;
                this.addToMessageQueue("A sample with this ID is already in the list.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(!flag){
            SampleXtraDTO sxDTO = new SampleXtraDTO();
            sxDTO.setSampleID(sampleID);
            sxDTO.setAge(age);
            sxDTO.setDescription(description);
            sxDTO.setEpoch_key(ageFrom);
            sxDTO.setEpoch(getValueFromKey(ageFromList,ageFrom));
            sxDTO.setHarvestMethod(harvestMethodKey);
            sxDTO.setOwner(owner.getOwner());
            if(sourceType.equals("1")){
                sxDTO.setParentSample_key(sampleSources.get(0).getSample_key());
            }
            if(sourceType.equals("2")){
                //mouse stuff
                sxDTO.setParentSample_key("0");
                sxDTO.setMouseSources((ArrayList<MouseEntity>) mouseSources.clone());
            }
            if(sourceType.equals("4")){
                sxDTO.setParentSample_key("0");
                sxDTO.setMatingSources((ArrayList<MatingEntity>) matingSources.clone());

            }
            if(sourceType.equals("3")){
                sxDTO.setParentSample_key("0");
                sxDTO.setLitterSources((ArrayList<LitterEntity>) litterSources.clone());
            }
            if(sourceType.equals("5")){
                sxDTO.setParentSample_key("0");
            }
            sxDTO.setSampleDate((Date) sampleDate.clone());
            sxDTO.setSampleDateType_key(sampleDateKey);
            sxDTO.setSampleDateType(getValueFromKey(sampleDateTypes,sampleDateKey));
            sxDTO.setSourceType(sourceType);
            sxDTO.getStorage().setPreservationDetail_key(preservationDetailKey);
            sxDTO.getStorage().setPreservationDetail(getValueFromKey(preservationDetails, preservationDetailKey));
            sxDTO.getStorage().setPreservationMethod_key(preservationMethodKey);
            sxDTO.getStorage().setPreservationMethod(getValueFromKey(preservationMethods, preservationMethodKey));
            sxDTO.getStorage().setPreservationType_key(preservationTypeKey);
            sxDTO.getStorage().setPreservationType(getValueFromKey(preservationTypes, preservationTypeKey));
            sxDTO.getStorage().setSampleStatus_key(sampleStatus);
            sxDTO.getStorage().setSampleStatus(getValueFromKey(sampleStatuses, sampleStatus));
            sxDTO.setStrain_key(sampleStrain.getStrainKey().toString());
            sxDTO.setStrainName(sampleStrain.getStrainName());
            sxDTO.setTimeUnit_key(timeUnit);
            sxDTO.setTimeUnit(getValueFromKey(timeUnits, timeUnit));
            sxDTO.setWeight(weight);
            sxDTO.setWeightUnit_key(weightUnit);
            sxDTO.setWeightUnit(getValueFromKey(units, weightUnit));
            sxDTO.setSampleType_key(sampleTypeKey);
            sxDTO.setSampleType(getValueFromKey(sampleTypes, sampleTypeKey));
            sxDTO.setSampleClass(getValueFromKey(sampleClasses, sampleClassKey));
            sxDTO.setLocation(location.getLocationType());
            sxDTO.setLocationType_key(location.getLocationType_key());
            samples.add(0,sxDTO);
            if(increment){
                sampleID = new MouseFunctionsBean().incrementID(sampleID, "JCMS_MOUSEID_INCREMENT_RIGHTMOST");
            }
            this.addToMessageQueue("Don't forget that samples haven't been saved until the 'Save' button is clicked and the sample row color is blue.", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void saveSample(){
        boolean flag = false;
        if(samples.isEmpty()){
            flag = true;
            this.addToMessageQueue("You need to add a sample to save.", FacesMessage.SEVERITY_ERROR);
        }
        if(!samples.isEmpty() && samples.get(0).isAdded()){
            flag = true;
            this.addToMessageQueue("All samples have already been saved.", FacesMessage.SEVERITY_ERROR);            
        }
        if(!flag){
            try{
                for(SampleXtraDTO sxDTO : samples){
                    if(!sxDTO.isAdded()){
                        dao.insertSample(sxDTO);
                        this.addToMessageQueue("Sample " + sxDTO.getSampleID() + " successfully added.", FacesMessage.SEVERITY_INFO);
                        sxDTO.setAdded(true);
                        sxDTO.setStyle("addedRow");
                    }
                }
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
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
    
    public void removeSample(){
        samples.remove(this.getKey("paramRowIndex").intValue());
    }
    
    public void clearSamples(){
        samples.clear();
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
    
    public void clearForm(){
        //Sample source
        wrapper = new SelectItemWrapper();
        sourceType = "1";
        sampleStrain = null;
        sourceID = "";
        relaxed = false;
        mouseSource = null;
        matingSource = null;
        litterSource = null;
        sampleSource = null;
        mouseSources = new ArrayList<MouseEntity>();
        matingSources = new ArrayList<MatingEntity>();
        litterSources = new ArrayList<LitterEntity>();
        sampleSources = new ArrayList<SampleXtraDTO>();
        sourceTypeDisabled = false;
        relaxedDisabled = false;
        samples = new ArrayList<SampleXtraDTO>();

        //Sample
        sampleID = "";
        increment = false;
        owner = null;
        sampleClassKey = "";
        sampleTypeKey = "";
        description = "";
        sampleDate = null;
        sampleDateKey = "";
        harvestMethodKey = "";
        weight = "";
        weightUnit = "";
        age = "";
        timeUnit = "";
        ageFrom = "";
        preservationTypeKey = "";
        preservationDetailKey = "";
        preservationMethodKey = "";
        sampleStatus = "";
        calculateAgeDisabled = true;

        //location
        location = null;
        
        sampleTypes = null;
        preservationTypes = null;
        preservationMethods = null;
        preservationDetails = null;
    }
    
    public void sampleStrainValueChangeListener(){
        if(sampleStrain != null){
            sourceList = new ArrayList<String>();
            if(sourceType.equals("1")){
                sourceList = dao.getSampleIDsByStrain(sampleStrain.getStrainKey().toString(), (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));
            }
            else if(sourceType.equals("2")){
                sourceList = dao.getMouseIDsByStrain(sampleStrain.getStrainKey().toString(), (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
            }
            else if(sourceType.equals("3")){
                sourceList = dao.getLitterIDsByStrain(sampleStrain.getStrainKey().toString(), (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
            }
            else if(sourceType.equals("4")){
                sourceList = dao.getMatingIDsByStrain(sampleStrain.getStrainKey().toString(), (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst"));                
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
    
    public ArrayList<String> myComplete(String query){
        ArrayList<String> results = new ArrayList<String>();
        for(String id : sourceList){
            if(id.contains(query)){
                results.add(id);
            }
        }
        return results;
    }

    public void sourceTypeValueChangeListener(){
        this.sampleStrainValueChangeListener();
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
     * @return the relaxed
     */
    public boolean isRelaxed() {
        return relaxed;
    }

    /**
     * @param relaxed the relaxed to set
     */
    public void setRelaxed(boolean relaxed) {
        this.relaxed = relaxed;
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
     * @return the preservationTypeKey
     */
    public String getPreservationTypeKey() {
        return preservationTypeKey;
    }

    /**
     * @param preservationTypeKey the preservationTypeKey to set
     */
    public void setPreservationTypeKey(String preservationTypeKey) {
        this.preservationTypeKey = preservationTypeKey;
    }

    /**
     * @return the sampleClassKey
     */
    public String getSampleClassKey() {
        return sampleClassKey;
    }

    /**
     * @param sampleClassKey the sampleClassKey to set
     */
    public void setSampleClassKey(String sampleClassKey) {
        this.sampleClassKey = sampleClassKey;
    }

    /**
     * @return the sampleTypeKey
     */
    public String getSampleTypeKey() {
        return sampleTypeKey;
    }

    /**
     * @param sampleTypeKey the sampleTypeKey to set
     */
    public void setSampleTypeKey(String sampleTypeKey) {
        this.sampleTypeKey = sampleTypeKey;
    }

    /**
     * @return the sampleDateKey
     */
    public String getSampleDateKey() {
        return sampleDateKey;
    }

    /**
     * @param sampleDateKey the sampleDateKey to set
     */
    public void setSampleDateKey(String sampleDateKey) {
        this.sampleDateKey = sampleDateKey;
    }

    /**
     * @return the harvestMethodKey
     */
    public String getHarvestMethodKey() {
        return harvestMethodKey;
    }

    /**
     * @param harvestMethodKey the harvestMethodKey to set
     */
    public void setHarvestMethodKey(String harvestMethodKey) {
        this.harvestMethodKey = harvestMethodKey;
    }

    /**
     * @return the preservationDetailKey
     */
    public String getPreservationDetailKey() {
        return preservationDetailKey;
    }

    /**
     * @param preservationDetailKey the preservationDetailKey to set
     */
    public void setPreservationDetailKey(String preservationDetailKey) {
        this.preservationDetailKey = preservationDetailKey;
    }

    /**
     * @return the preservationMethodKey
     */
    public String getPreservationMethodKey() {
        return preservationMethodKey;
    }

    /**
     * @param preservationMethodKey the preservationMethodKey to set
     */
    public void setPreservationMethodKey(String preservationMethodKey) {
        this.preservationMethodKey = preservationMethodKey;
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
     * @return the units
     */
    public ArrayList<SelectItem> getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(ArrayList<SelectItem> units) {
        this.units = units;
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
     * @return the ageFrom
     */
    public String getAgeFrom() {
        return ageFrom;
    }

    /**
     * @param ageFrom the ageFrom to set
     */
    public void setAgeFrom(String ageFrom) {
        this.ageFrom = ageFrom;
    }

    /**
     * @return the ageFromList
     */
    public ArrayList<SelectItem> getAgeFromList() {
        return ageFromList;
    }

    /**
     * @param ageFromList the ageFromList to set
     */
    public void setAgeFromList(ArrayList<SelectItem> ageFromList) {
        this.ageFromList = ageFromList;
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
     * @return the increment
     */
    public boolean isIncrement() {
        return increment;
    }

    /**
     * @param increment the increment to set
     */
    public void setIncrement(boolean increment) {
        this.increment = increment;
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
     * @return the relaxedDisabled
     */
    public boolean isRelaxedDisabled() {
        return relaxedDisabled;
    }

    /**
     * @param relaxedDisabled the relaxedDisabled to set
     */
    public void setRelaxedDisabled(boolean relaxedDisabled) {
        this.relaxedDisabled = relaxedDisabled;
    }

    /**
     * @return the sample
     */
    public ArrayList<SampleXtraDTO> getSamples() {
        return samples;
    }

    /**
     * @param sample the sample to set
     */
    public void setSamples(ArrayList<SampleXtraDTO> sample) {
        this.samples = sample;
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
     * @return the existingSamples
     */
    public ArrayList<SampleXtraDTO> getExistingSamples() {
        return existingSamples;
    }

    /**
     * @param existingSamples the existingSamples to set
     */
    public void setExistingSamples(ArrayList<SampleXtraDTO> existingSamples) {
        this.existingSamples = existingSamples;
    }

    /**
     * @return the sourceList
     */
    public ArrayList<String> getSourceList() {
        return sourceList;
    }

    /**
     * @param sourceList the sourceList to set
     */
    public void setSourceList(ArrayList<String> sourceList) {
        this.sourceList = sourceList;
    }
}
