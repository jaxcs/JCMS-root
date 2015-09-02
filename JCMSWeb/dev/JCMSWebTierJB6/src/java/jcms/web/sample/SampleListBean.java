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
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.dto.SampleXtraDTO;
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.dao.LocationDAO;
import java.util.Date;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.web.common.SelectItemWrapper;
import org.primefaces.model.DualListModel;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.dto.SampleClassDTO;
import jcms.integrationtier.dto.SampleTypeDTO;
import jcms.integrationtier.dto.SampleSearchDTO;
import jcms.integrationtier.dto.StorageFacilityDTO;
import jcms.integrationtier.dto.LocationTypeDTO;
import java.util.HashMap;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;


/**
 *
 * @author mkamato
 */
public class SampleListBean extends WTBaseBackingBean implements Serializable{
    
    private ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
    private DualListModel ownersModel = new DualListModel();
    private SampleDAO dao = new SampleDAO();
    private LocationDAO locationDAO = new LocationDAO();
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private ArrayList<SelectItem> sampleClasses = new ArrayList<SelectItem>();
    private String sampleClass = "";
    private ArrayList<SelectItem> sampleTypes = new ArrayList<SelectItem>();
    private String sampleType = "";
    private ArrayList<SelectItem> sourceTypes = new ArrayList<SelectItem>();
    private String sourceType = "";
    private String sampleID = "";
    private String sampleIDFilter = "Contains";
    private String sourceID = "";
    private String sourceIDFilter = "Contains";
    private Date sampleDateAfter = null;
    private Date sampleDateBefore = null;
    private StrainEntity sampleStrain = null;
    private String totalResults = "";
    private TreeNode root = new DefaultTreeNode();
    private LocationTypeDTO location = new LocationTypeDTO();
    private StorageFacilityDTO storage = new StorageFacilityDTO();
     
    public SampleListBean(){
        ArrayList<String> owners = new ArrayList<String>();
        for(OwnerEntity owner: (ArrayList<OwnerEntity>)getSessionParameter("colonyManageOwnerEntityLst")){
            owners.add(owner.getOwner());
        }
        ownersModel.setSource(owners);
        sourceTypes.add(new SelectItem("", ""));
        sourceTypes.add(new SelectItem("1", "Sample"));
        sourceTypes.add(new SelectItem("2", "Mouse"));
        sourceTypes.add(new SelectItem("3", "Litter"));
        sourceTypes.add(new SelectItem("4", "Mating"));
        sourceTypes.add(new SelectItem("5", "Other"));
        
        ArrayList<SampleClassDTO> classes = dao.getSampleClasses();
        sampleClasses = new ArrayList<SelectItem>();
        sampleClasses.add(new SelectItem("", ""));
        for(SampleClassDTO dto : classes){
            sampleClasses.add(new SelectItem(dto.getSampleClass_key(), dto.getSampleClass()));
        }
        
        //set up locations
        ArrayList<StorageFacilityDTO> storages = locationDAO.getAllStorageFacilities();
        for(StorageFacilityDTO storageFacility : storages){
            TreeNode storageNode = new DefaultTreeNode("storage", storageFacility, getRoot());
            HashMap<String, ArrayList<LocationTypeDTO>> hm = locationDAO.getLocationTypesMapByStorageFacility(storageFacility.getStorageFacility_key());
            if(hm.get("0") != null){
                for(LocationTypeDTO dto : (ArrayList<LocationTypeDTO>) hm.get("0")){
                    DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, storageNode);
                    locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
                }
            }
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
    
    public void sampleClassChangeListener(){
        if(!sampleClass.equals("")){
            //sample class changes, get correct sample types and create new drop down
            ArrayList<SampleTypeDTO> sampTypes = dao.getSampleTypesBySampleClassKey(sampleClass);
            sampleTypes = new ArrayList<SelectItem>();
            sampleTypes.add(new SelectItem("", ""));
            for(SampleTypeDTO dto : sampTypes){
                sampleTypes.add(new SelectItem(dto.getSampleType_key(), dto.getSampleType()));
            }            
        }
        else{
            sampleTypes = new ArrayList<SelectItem>();
            sampleType = "";
        }
    }
    
    public void search(){
        if(sampleDateBefore != null && sampleDateAfter != null && sampleDateBefore.before(sampleDateAfter)){
            this.addToMessageQueue("The sample date before must be after sample date after to return results.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            SampleSearchDTO dto = new SampleSearchDTO();
            if(ownersModel.getTarget().isEmpty()){
                dto.setOwners((ArrayList<String>) ownersModel.getSource());
            }
            else{
                dto.setOwners((ArrayList<String>) ownersModel.getTarget());
            }
            dto.setSampleClass(sampleClass);
            dto.setSampleDateBefore(getSampleDateBefore());
            dto.setSampleDateAfter(getSampleDateAfter());
            dto.setSampleID(sampleID);
            dto.setSampleIDFilter(sampleIDFilter);
            dto.setSampleStrain(sampleStrain);
            dto.setSampleType(sampleType);
            dto.setSourceID(sourceID);
            dto.setSourceIDFilter(sourceIDFilter);
            dto.setSourceType(sourceType);
            dto.setLocation(location.getLocationType_key());
            dto.setStorageFacility(storage.getStorageFacility_key());

            samples = dao.sampleListQuery(dto);
            totalResults = new Integer(samples.size()).toString();
            if(samples.isEmpty()){
                this.addToMessageQueue("No results found for these search criteria.", FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) {  
        if(event.getTreeNode().getData() instanceof LocationTypeDTO){
            System.out.println("Valid Node Selected");
            location = (LocationTypeDTO) event.getTreeNode().getData();
            storage = new StorageFacilityDTO();
        }
        else{
            location = new LocationTypeDTO();
            storage = (StorageFacilityDTO) event.getTreeNode().getData();
        }
    }

    /**
     * @return the samples
     */
    public ArrayList<SampleXtraDTO> getSamples() {
        return samples;
    }

    /**
     * @param samples the samples to set
     */
    public void setSamples(ArrayList<SampleXtraDTO> samples) {
        this.samples = samples;
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
     * @return the sourceTypes
     */
    public ArrayList<SelectItem> getSourceTypes() {
        return sourceTypes;
    }

    /**
     * @param sourceTypes the sourceTypes to set
     */
    public void setSourceTypes(ArrayList<SelectItem> sourceTypes) {
        this.sourceTypes = sourceTypes;
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
     * @return the sampleIDFilter
     */
    public String getSampleIDFilter() {
        return sampleIDFilter;
    }

    /**
     * @param sampleIDFilter the sampleIDFilter to set
     */
    public void setSampleIDFilter(String sampleIDFilter) {
        this.sampleIDFilter = sampleIDFilter;
    }

    /**
     * @return the sourceIDFilter
     */
    public String getSourceIDFilter() {
        return sourceIDFilter;
    }

    /**
     * @param sourceIDFilter the sourceIDFilter to set
     */
    public void setSourceIDFilter(String sourceIDFilter) {
        this.sourceIDFilter = sourceIDFilter;
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
     * @return the totalResults
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults the totalResults to set
     */
    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
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
     * @return the sampleDateAfter
     */
    public Date getSampleDateAfter() {
        return sampleDateAfter;
    }

    /**
     * @param sampleDateAfter the sampleDateAfter to set
     */
    public void setSampleDateAfter(Date sampleDateAfter) {
        this.sampleDateAfter = sampleDateAfter;
    }

    /**
     * @return the sampleDateBefore
     */
    public Date getSampleDateBefore() {
        return sampleDateBefore;
    }

    /**
     * @param sampleDateBefore the sampleDateBefore to set
     */
    public void setSampleDateBefore(Date sampleDateBefore) {
        this.sampleDateBefore = sampleDateBefore;
    }
}
