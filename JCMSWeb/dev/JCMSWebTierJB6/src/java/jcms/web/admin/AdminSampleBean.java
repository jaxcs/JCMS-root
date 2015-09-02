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

package jcms.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.dao.LocationDAO;
import jcms.integrationtier.dto.*;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author mkamato
 */
public class AdminSampleBean extends AdminBean{
    
    SampleDAO dao = new SampleDAO();
    private ArrayList<TimeUnitDTO> timeUnits = dao.getTimeUnits();
    private ArrayList<SampleStatusDTO> sampleStatuses = dao.getSampleStatuses();
    private String editSampleStatusKey = null;
    private String editTimeUnitKey = null;
    private LocationDAO locationDAO = new LocationDAO();
    private TreeNode root = new DefaultTreeNode();
    private boolean containsSamples = false;
    
    private boolean addLocation = false;
    private boolean addStorage = false;
    private TreeNode selectedTreeNode = null;
    private LocationTypeDTO location = null;
    private StorageFacilityDTO storageFacility = null;
    private String locationStorageFunction = "";
    private String parentLocationName = "";
    private String parentStorageFacility = "";
    private String newStorageFacilityName = "";
    private String newLocationTypeName = "";
    private String newLocationTypeDetail = "";

    public AdminSampleBean(){
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
    
    private void locationTreeBuild(String key, HashMap<String, ArrayList<LocationTypeDTO>> hm, DefaultTreeNode mom){
        ArrayList<LocationTypeDTO> list = hm.get(key);
        if(list != null){
            for(LocationTypeDTO dto : list){
                DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, mom);
                locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
            }
        }
    }
    
    public void addSampleStatusAction(){
        boolean flag = false;
        for(SampleStatusDTO dto : sampleStatuses){
            if(dto.getSampleStatus_key().equals("")){
                flag = true;
                this.addToMessageQueue("You must complete the current add before adding another Sample Status", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(!flag){
            sampleStatuses.add(0,new SampleStatusDTO());
        }
    }
    
    public void editTimeUnitAction(){
        editTimeUnitKey = this.getParamPrimaryKey();
    }
    
    public void editSampleStatusAction(){
        editSampleStatusKey = this.getParamPrimaryKey();
    }
    
    public void deleteTimeUnit(){        
        System.out.println(this.getParamPrimaryKey());
        try{
            dao.deleteTimeUnit(this.getParamPrimaryKey());
            this.addToMessageQueue("Time unit deleted successfully.", FacesMessage.SEVERITY_INFO);
            timeUnits.remove(Integer.parseInt(this.getParamRowIndex()));
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            this.addToMessageQueue("That time unit cannot not be deleted.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void updateTimeUnitAction(){
        if(editTimeUnitKey != null){
            TimeUnitDTO dto = null;
            for(TimeUnitDTO tuDTO : timeUnits){
                if(tuDTO.getTimeUnit_key().equals(editTimeUnitKey)){
                    dto = tuDTO;
                }
            }
            if(dto != null){
                boolean flag = false;
                if(dto.getMinutesPerUnit().equals("")){
                    this.addToMessageQueue("Minutes per unit is required.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                try{
                    Integer.parseInt(dto.getMinutesPerUnit());
                }
                catch(Exception e){
                    flag = true;
                    this.addToMessageQueue("Minutes per unit must be a whole number, please provide a valid value for Minutes Per Unit.", FacesMessage.SEVERITY_ERROR);            
                }
                if(dto.getTimeUnit().equals("")){
                    this.addToMessageQueue("Time Unit is required.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                if(dto.getTimeUnit().length() > 32){
                    this.addToMessageQueue("Time Unit must be fewer than 33 characters.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                if(dto.getAbbreviation().equals("")){
                    this.addToMessageQueue("Abbreviation is required.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }        
                if(dto.getAbbreviation().length() > 10){
                    this.addToMessageQueue("Abbreviation must be fewer than 11 characters.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                for(TimeUnitDTO tu : timeUnits){
                    if(!tu.getTimeUnit_key().equals(dto.getTimeUnit_key())){
                        if(tu.getAbbreviation().equals(dto.getAbbreviation())){
                            this.addToMessageQueue("Abbreviation must be unique.", FacesMessage.SEVERITY_ERROR);
                            flag = true;
                        }
                        if(tu.getTimeUnit().equals(dto.getTimeUnit())){
                            this.addToMessageQueue("Time Unit must be unique.", FacesMessage.SEVERITY_ERROR);
                            flag = true;
                        }
                    }
                }
                try{
                    if(!flag){
                        dao.updateTimeUnit(dto);
                        this.addToMessageQueue("Time Unit successfully updated.", FacesMessage.SEVERITY_INFO);                        
                        editTimeUnitKey = null;
                    }
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
    }
    
    public void updateSampleStatusAction(){
        if(editSampleStatusKey != null){
            SampleStatusDTO dto = null;
            for(SampleStatusDTO statusDTO : sampleStatuses){
                if(statusDTO.getSampleStatus_key().equals(editSampleStatusKey)){
                    dto = statusDTO;
                }
            }
            if(dto != null){
                boolean flag = false;
                if(dto.getSampleStatus().equals("")){
                    this.addToMessageQueue("Sample Status is required.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                if(dto.getSampleStatus().length() > 32){
                    this.addToMessageQueue("Sample Status must be fewer than 33 characters.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                if(!flag){
                    try{
                        if(dto.getIsInStorage().equals("true")){
                            dto.setIsInStorage("-1");
                        }
                        else{
                            dto.setIsInStorage("0");
                        }
                        dao.updateSampleStatus(dto);
                        sampleStatuses = dao.getSampleStatuses();
                        this.addToMessageQueue("Sample Status successfully updated.", FacesMessage.SEVERITY_INFO);
                        editSampleStatusKey = null;
                    }
                    catch(Exception e){
                        this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        }
    }
    
    public void cancelUpdateTimeUnitAction(){
        editTimeUnitKey = null;
    }
    
    public void cancelUpdateSampleStatusAction(){
        editSampleStatusKey = null;
    }
    
    public void deleteSampleStatus(){
        System.out.println(this.getParamPrimaryKey());
        try{
            dao.deleteSampleStatus(this.getParamPrimaryKey());
            this.addToMessageQueue("Sample status deleted successfully.", FacesMessage.SEVERITY_INFO);
            sampleStatuses.remove(Integer.parseInt(this.getParamRowIndex()));
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            this.addToMessageQueue("That sample status cannot not be deleted.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void addTimeUnitAction(){
        boolean flag = false;
        for(TimeUnitDTO dto : timeUnits){
            if(dto.getTimeUnit_key().equals("")){
                flag = true;
                this.addToMessageQueue("You must complete the current add before adding another Sample Status", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(!flag){
            timeUnits.add(0, new TimeUnitDTO());
        }
    }
    
    public void saveSampleStatusAction(){
        boolean flag = false;
        SampleStatusDTO dto = sampleStatuses.get(0);
        if(dto.getSampleStatus().equals("")){
            this.addToMessageQueue("Sample Status is required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(dto.getSampleStatus().length() > 32){
            this.addToMessageQueue("Sample Status must be fewer than 33 characters.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        for(SampleStatusDTO status : sampleStatuses){
            if(!status.getSampleStatus_key().equals("")){
                if(status.getSampleStatus().equals(dto.getSampleStatus())){
                    this.addToMessageQueue("Sample Status must be unique.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
            }
        }
        try{
            if(!flag){
                System.out.println(dto.getIsInStorage());
                if(dto.getIsInStorage().equals("true")){
                    dto.setIsInStorage("-1");
                }
                else{
                    dto.setIsInStorage("0");
                }
                //dao.insert
                dto.setSampleStatus_key(dao.insertSampleStatus(dto));
                sampleStatuses = dao.getSampleStatuses();
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void saveTimeUnitAction(){
        boolean flag = false;
        TimeUnitDTO dto = timeUnits.get(0);
        if(dto.getMinutesPerUnit().equals("")){
            this.addToMessageQueue("Minutes per unit is required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        try{
            Integer.parseInt(dto.getMinutesPerUnit());
        }
        catch(Exception e){
            flag = true;
            this.addToMessageQueue("Minutes per unit must be a whole number, please provide a valid value for Minutes Per Unit.", FacesMessage.SEVERITY_ERROR);            
        }
        if(dto.getTimeUnit().equals("")){
            this.addToMessageQueue("Time Unit is required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(dto.getTimeUnit().length() > 32){
            this.addToMessageQueue("Time Unit must be fewer than 33 characters.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(dto.getAbbreviation().equals("")){
            this.addToMessageQueue("Abbreviation is required.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }        
        if(dto.getAbbreviation().length() > 10){
            this.addToMessageQueue("Abbreviation must be fewer than 11 characters.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        for(TimeUnitDTO tu : timeUnits){
            if(!tu.getTimeUnit_key().equals("")){
                if(tu.getAbbreviation().equals(dto.getAbbreviation())){
                    this.addToMessageQueue("Abbreviation must be unique.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
                if(tu.getTimeUnit().equals(dto.getTimeUnit())){
                    this.addToMessageQueue("Time Unit must be unique.", FacesMessage.SEVERITY_ERROR);
                    flag = true;
                }
            }
        }
        try{
            if(!flag){
                //dao.insert
                dto.setTimeUnit_key(dao.insertTimeUnit(dto));
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void cancelSampleStatusAction(){
        sampleStatuses.remove(0);
    }
    
    public void cancelTimeUnitAction(){
        timeUnits.remove(0);
    }
        
    public void onNodeSelect(NodeSelectEvent event) {  
        storageFacility = null;
        location = null;
        
        selectedTreeNode = event.getTreeNode();
        //location selected
        if(event.getTreeNode().getData() instanceof LocationTypeDTO){
            location = (LocationTypeDTO) event.getTreeNode().getData();
            storageFacility = locationDAO.getStorageFacilityByKey(location.getStorageFacility_key());
            
            parentLocationName = location.getLocationType();
            if(storageFacility != null){
                parentStorageFacility = storageFacility.getStorageFacility();
            }
            else{
                parentStorageFacility = "";
            }
        }
        //storage facility selected
        else{
            storageFacility = (StorageFacilityDTO) event.getTreeNode().getData();
            parentLocationName = "";
            parentStorageFacility = storageFacility.getStorageFacility();
        }
    }  
    
    public void addStorageFacility(){
        boolean flag = false;
        if(newStorageFacilityName.equals("")){
            this.addToMessageQueue("Storage Facility name cannot be blank.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(newStorageFacilityName.length() > 64){
            this.addToMessageQueue("Storage Facility name must be 64 characters or fewer.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            StorageFacilityDTO dto = new StorageFacilityDTO();
            dto.setStorageFacility(newStorageFacilityName);
            dto.setLabel(newStorageFacilityName);
            try{
                dto.setStorageFacility_key(locationDAO.insertStorageFacility(dto));
                DefaultTreeNode locationNode = new DefaultTreeNode("storage", dto, root);
                this.addToMessageQueue("Storage facility " + dto.getStorageFacility() + " successfully added.", FacesMessage.SEVERITY_INFO);
                newStorageFacilityName = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void addLocationType(){
        boolean flag = false;
        if(newLocationTypeName.equals("")){
            this.addToMessageQueue("Location Type name cannot be blank.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(newLocationTypeName.length() > 32){
            this.addToMessageQueue("Location Type name must be 32 characters or fewer.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(selectedTreeNode == null){
            this.addToMessageQueue("You must select a parent storage facility or location type in the tree to the left.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(location == null && storageFacility == null && selectedTreeNode != null){
            this.addToMessageQueue("You must select a parent storage facility or location type in the tree to the left.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            LocationTypeDTO dto = new LocationTypeDTO();
            dto.setLocationType(newLocationTypeName);
            dto.setLocationDetail(newLocationTypeDetail);
            dto.setLabel(newLocationTypeName);
            if(location != null){
                dto.setLocationTypeRef(location.getLocationType_key());
                dto.setStorageFacility_key(location.getStorageFacility_key());
            }
            else if(storageFacility != null){
                dto.setStorageFacility_key(storageFacility.getStorageFacility_key());
            }
            try{
                dto.setLocationType_key(locationDAO.insertLocationType(dto));
                DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, selectedTreeNode);
                this.addToMessageQueue("Location type " + dto.getLocationType() + " successfully added.", FacesMessage.SEVERITY_INFO);
                newLocationTypeName = "";
                newLocationTypeDetail = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void updateLocationType(){
        boolean flag = false;
        if(newLocationTypeName.equals("")){
            this.addToMessageQueue("Location Type name cannot be blank.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(newLocationTypeName.length() > 32){
            this.addToMessageQueue("Location Type name must be 32 characters or fewer.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(location == null || selectedTreeNode == null){
            this.addToMessageQueue("You must select a location type in the tree to the left to update.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                LocationTypeDTO dto = new LocationTypeDTO();
                dto.setLocationDetail(newLocationTypeDetail);
                dto.setLocationType(newLocationTypeName);
                dto.setLocationTypeRef(location.getLocationTypeRef());
                dto.setLocationType_key(location.getLocationType_key());
                dto.setStorageFacility_key(location.getStorageFacility_key());
                locationDAO.updateLocationType(dto);
                LocationTypeDTO temp = (LocationTypeDTO) selectedTreeNode.getData();
                temp.setLocationDetail(newLocationTypeDetail);
                temp.setLocationType(newLocationTypeName);          
                temp.setLabel(newLocationTypeName);
                this.addToMessageQueue("Location type successfully updated.", FacesMessage.SEVERITY_INFO);
                newLocationTypeName = "";
                newLocationTypeDetail = "";                
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void updateStorageFacility(){
        boolean flag = false;
        if(newStorageFacilityName.equals("")){
            this.addToMessageQueue("Storage Facility name cannot be blank.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(newStorageFacilityName.length() > 64){
            this.addToMessageQueue("Storage Facility name must be 64 characters or fewer.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(storageFacility == null){
            this.addToMessageQueue("You must select a storage facility in the tree to the left to edit.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                StorageFacilityDTO dto = (StorageFacilityDTO) selectedTreeNode.getData();
                dto.setLabel(newStorageFacilityName);
                dto.setStorageFacility(newStorageFacilityName);
                locationDAO.updateStorageFacility(dto);
                newStorageFacilityName = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void deleteLocation(){
        boolean flag = false;
        locationContainsSamples(selectedTreeNode);
        if(containsSamples){
            flag = true;
            this.addToMessageQueue("This Location contains samples still, to delete a Location you must remove all samples first.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //do delete
            
        }
    }
    
    public void deleteStorage(){
        boolean flag = false;
        for(TreeNode child : selectedTreeNode.getChildren()){
            locationContainsSamples(child);
            if(containsSamples){
                flag = true;
                this.addToMessageQueue("This storage facility contains samples still, to delete a storage facility you must remove all samples first.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(!flag){
            //do delete - set parent to void, or remove child from children
            deleteWalk(selectedTreeNode);
            selectedTreeNode.setParent(null);
            selectedTreeNode = null;
        }
    }
    
    private void deleteWalk(TreeNode node){
        try{
            for(TreeNode child : node.getChildren()){
                LocationTypeDTO data = (LocationTypeDTO) node.getData();
                locationDAO.deleteLocation(data.getLocationType_key());
                deleteWalk(child);
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void locationContainsSamples(TreeNode location){
        for(TreeNode child : location.getChildren()){
            LocationTypeDTO data = (LocationTypeDTO) child.getData();
            if(locationDAO.locationContainsSamples(data.getLocationType_key())){
                containsSamples = true;
            }
            locationContainsSamples(child);
        }
    }
    
    /**
     * @return the timeUnits
     */
    public ArrayList<TimeUnitDTO> getTimeUnits() {
        return timeUnits;
    }

    /**
     * @param timeUnits the timeUnits to set
     */
    public void setTimeUnits(ArrayList<TimeUnitDTO> timeUnits) {
        this.timeUnits = timeUnits;
    }

    /**
     * @return the sampleStatuses
     */
    public ArrayList<SampleStatusDTO> getSampleStatuses() {
        return sampleStatuses;
    }

    /**
     * @param sampleStatuses the sampleStatuses to set
     */
    public void setSampleStatuses(ArrayList<SampleStatusDTO> sampleStatuses) {
        this.sampleStatuses = sampleStatuses;
    }

    /**
     * @return the editSampleStatusKey
     */
    public String getEditSampleStatusKey() {
        return editSampleStatusKey;
    }

    /**
     * @param editSampleStatusKey the editSampleStatusKey to set
     */
    public void setEditSampleStatusKey(String editSampleStatusKey) {
        this.editSampleStatusKey = editSampleStatusKey;
    }

    /**
     * @return the editTimeUnitKey
     */
    public String getEditTimeUnitKey() {
        return editTimeUnitKey;
    }

    /**
     * @param editTimeUnitKey the editTimeUnitKey to set
     */
    public void setEditTimeUnitKey(String editTimeUnitKey) {
        this.editTimeUnitKey = editTimeUnitKey;
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
     * @return the addLocation
     */
    public boolean isAddLocation() {
        return addLocation;
    }

    /**
     * @param addLocation the addLocation to set
     */
    public void setAddLocation(boolean addLocation) {
        this.addLocation = addLocation;
    }

    /**
     * @return the addStorage
     */
    public boolean isAddStorage() {
        return addStorage;
    }

    /**
     * @param addStorage the addStorage to set
     */
    public void setAddStorage(boolean addStorage) {
        this.addStorage = addStorage;
    }

    /**
     * @return the selectedTreeNode
     */
    public TreeNode getSelectedTreeNode() {
        return selectedTreeNode;
    }

    /**
     * @param selectedTreeNode the selectedTreeNode to set
     */
    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
    }

    /**
     * @return the storageFacility
     */
    public StorageFacilityDTO getStorageFacility() {
        return storageFacility;
    }

    /**
     * @param storageFacility the storageFacility to set
     */
    public void setStorageFacility(StorageFacilityDTO storageFacility) {
        this.storageFacility = storageFacility;
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
     * @return the locationStorageFunction
     */
    public String getLocationStorageFunction() {
        return locationStorageFunction;
    }

    /**
     * @param locationStorageFunction the locationStorageFunction to set
     */
    public void setLocationStorageFunction(String locationStorageFunction) {
        this.locationStorageFunction = locationStorageFunction;
    }

    /**
     * @return the parentLocationName
     */
    public String getParentLocationName() {
        return parentLocationName;
    }

    /**
     * @param parentLocationName the parentLocationName to set
     */
    public void setParentLocationName(String parentLocationName) {
        this.parentLocationName = parentLocationName;
    }

    /**
     * @return the parentStorageFacility
     */
    public String getParentStorageFacility() {
        return parentStorageFacility;
    }

    /**
     * @param parentStorageFacility the parentStorageFacility to set
     */
    public void setParentStorageFacility(String parentStorageFacility) {
        this.parentStorageFacility = parentStorageFacility;
    }

    /**
     * @return the newStorageFacilityName
     */
    public String getNewStorageFacilityName() {
        return newStorageFacilityName;
    }

    /**
     * @param newStorageFacilityName the newStorageFacilityName to set
     */
    public void setNewStorageFacilityName(String newStorageFacilityName) {
        this.newStorageFacilityName = newStorageFacilityName;
    }

    /**
     * @return the newLocationTypeName
     */
    public String getNewLocationTypeName() {
        return newLocationTypeName;
    }

    /**
     * @param newLocationTypeName the newLocationTypeName to set
     */
    public void setNewLocationTypeName(String newLocationTypeName) {
        this.newLocationTypeName = newLocationTypeName;
    }

    /**
     * @return the newLocationTypeDetail
     */
    public String getNewLocationTypeDetail() {
        return newLocationTypeDetail;
    }

    /**
     * @param newLocationTypeDetail the newLocationTypeDetail to set
     */
    public void setNewLocationTypeDetail(String newLocationTypeDetail) {
        this.newLocationTypeDetail = newLocationTypeDetail;
    }
}
