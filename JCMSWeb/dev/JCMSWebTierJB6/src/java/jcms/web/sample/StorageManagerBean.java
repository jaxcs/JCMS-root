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
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import jcms.integrationtier.dao.LocationDAO;
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.dto.LocationTypeDTO;
import jcms.integrationtier.dto.StorageFacilityDTO;
import jcms.integrationtier.dto.SampleXtraDTO;
import jcms.integrationtier.dto.SampleSearchDTO;
import jcms.web.base.WTBaseBackingBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeSelectEvent;

/**
 *
 * @author mkamato
 */
public class StorageManagerBean extends WTBaseBackingBean implements Serializable{
    
    private LocationDAO locationDAO = new LocationDAO();
    private TreeNode root = new DefaultTreeNode();
    private String location = "";
    //either move or duplicate
    private String function = "move";
    
    //below two are like static values for hidden classes
    private String locationClass = "location";
    private String storageClass = "storage";
    
    private TreeNode droppedNode = null;
    private TreeNode draggedNode = null;
    
    private ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
    
    //add/edit storage facility/location
    private LocationTypeDTO selectedLocation = null;
    private StorageFacilityDTO selectedStorageFacility = null;
    private String parentLocationName = "";
    private String parentStorageFacility = "";
    private String newStorageFacilityName = "";
    private String newLocationTypeName = "";
    private String newLocationTypeDetail = "";
    private TreeNode selectedTreeNode = null;
    private boolean containsSamples = false;
    
    public StorageManagerBean(){
        //set up locations
        ArrayList<StorageFacilityDTO> storages = locationDAO.getAllStorageFacilities();
        for(StorageFacilityDTO storageFacility : storages){
            TreeNode storageNode = new DefaultTreeNode("storage", storageFacility, getRoot());
            storageNode.setExpanded(true);
            HashMap<String, ArrayList<LocationTypeDTO>> hm = locationDAO.getLocationTypesMapByStorageFacility(storageFacility.getStorageFacility_key());
            if(hm.get("0") != null){
                for(LocationTypeDTO dto : (ArrayList<LocationTypeDTO>) hm.get("0")){
                    DefaultTreeNode locationNode = new DefaultTreeNode("location", dto, storageNode);
                    locationTreeBuild(dto.getLocationType_key(), hm, locationNode);
                }
            }
        }
    }
    
    public void refresh(){
        //set up locations
        root = new DefaultTreeNode();
        
        ArrayList<StorageFacilityDTO> storages = locationDAO.getAllStorageFacilities();
        for(StorageFacilityDTO storageFacility : storages){
            TreeNode storageNode = new DefaultTreeNode("storage", storageFacility, getRoot());
            storageNode.setExpanded(true);
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
    
    public void nodeExpand(NodeExpandEvent event){
        event.getTreeNode().setExpanded(true);
    }
    
    public void nodeCollapse(NodeCollapseEvent event){
        event.getTreeNode().setExpanded(false);
    }
    
    public void dropNode(){
        if(function.equals("move") || function.equals("duplicate")){
            String draggedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("locationDragKey");
            String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dropKey");
            //location or storageFacility
            String droppedClass = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dropClass");

            System.out.println("Dragged: " + draggedKey);
            System.out.println("Dropped on: " + droppedKey);
            System.out.println("Dropped class: " + droppedClass);

            boolean flag = false;

            //move case
            if(function.equals("move")){
                if(draggedKey.equals(droppedKey) && droppedClass.equals(locationClass)){
                    flag = true;
                    this.addToMessageQueue("A location cannot be moved into itself.", FacesMessage.SEVERITY_ERROR);
                }
                if(!flag){
                    //tree walks
                    getDraggedNode(draggedKey, root);
                    getDroppedNode(droppedKey, root, droppedClass);
                    
                    try{
                        LocationTypeDTO dto = (LocationTypeDTO) draggedNode.getData();
                        
                        if(!this.locationChangeOk(dto.getLocationType_key())){
                            flag = true;
                            this.addToMessageQueue("You cannot move this location as you are not the owner of all samples contained in it.", FacesMessage.SEVERITY_ERROR);
                        }
                        if(!flag){
                            if(droppedNode.getData() instanceof LocationTypeDTO){
                                //dropped on a location node
                                LocationTypeDTO locDTO = (LocationTypeDTO) droppedNode.getData();
                                dto.setStorageFacility_key(locDTO.getStorageFacility_key());
                                dto.setLocationTypeRef(locDTO.getLocationType_key());
                                locationTreeUpdate(locDTO.getStorageFacility_key(), locDTO.getLocationType_key(), draggedNode);
                            }
                            else{
                                //dropped on a storage facility
                                StorageFacilityDTO storDTO = (StorageFacilityDTO) droppedNode.getData();
                                dto.setStorageFacility_key(storDTO.getStorageFacility_key());
                                dto.setLocationTypeRef("0");
                                locationTreeUpdate(storDTO.getStorageFacility_key(), "0", draggedNode);
                            }

                            //update tree
                            draggedNode.setParent(droppedNode);

                            if(droppedNode.getData() instanceof LocationTypeDTO){
                                LocationTypeDTO locDTO = (LocationTypeDTO) droppedNode.getData();
                                this.addToMessageQueue("Location " + dto.getLocationType() + " moved to " + locDTO.getLocationType(), FacesMessage.SEVERITY_INFO);
                            }
                            else{
                                StorageFacilityDTO storDTO = (StorageFacilityDTO) droppedNode.getData();
                                this.addToMessageQueue("Location " + dto.getLocationType() + " moved to " + storDTO.getStorageFacility(), FacesMessage.SEVERITY_INFO);
                            }
                        }
                    }
                    catch(Exception e){
                        this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
            //duplicate case
            else{
                //tree walks
                getDraggedNode(draggedKey, root);
                getDroppedNode(droppedKey, root, droppedClass);

                //create subtree in database and PrimeFaces object
                TreeNode duplicate = duplicateTreeNode(draggedNode, droppedNode);
                createDraggedSubTree(draggedNode, duplicate);

                //add to view
                duplicate.setParent(droppedNode);
            }
            draggedNode = null;
            droppedNode = null;
        }
        else{
            this.addToMessageQueue("To move/duplicate your locations please select that action from the choices below.", FacesMessage.SEVERITY_WARN);
        }
    }
    
    private void createDraggedSubTree(TreeNode subTree, TreeNode duplicate){
        for(TreeNode child : subTree.getChildren()){
            TreeNode node = duplicateTreeNode(child, duplicate);
            duplicate.getChildren().add(node);
            createDraggedSubTree(child, node);
        }
    }
    
    private TreeNode duplicateTreeNode(TreeNode node, TreeNode parent){
        try{
            //do the insert here I guess -- parent is what the parent of the duplicate should be,
            //not the pare of the node being duplicated -- node is TreeNode to be duplicated
            LocationTypeDTO dto = new LocationTypeDTO((LocationTypeDTO) node.getData());
            if(parent.getData() instanceof LocationTypeDTO){
                //case 1: parent is another location type.
                LocationTypeDTO parentDTO = new LocationTypeDTO((LocationTypeDTO) parent.getData());
                dto.setLocationType_key("");
                dto.setStorageFacility_key(parentDTO.getStorageFacility_key());
                dto.setLocationTypeRef(parentDTO.getLocationType_key());
                //insert the duplicated location into the database with adjusted locationtypes and storage facility references
                dto.setLocationType_key(locationDAO.insertLocationType(dto));
            }
            else{    
                //case 2: parent is a storage facility
                StorageFacilityDTO parentDTO = new StorageFacilityDTO((StorageFacilityDTO) parent.getData());
                dto.setLocationType_key("");
                dto.setStorageFacility_key(parentDTO.getStorageFacility_key());
                dto.setLocationTypeRef("0");
                //insert the duplicated location into the database with adjusted locationtypes and storage facility references
                dto.setLocationType_key(locationDAO.insertLocationType(dto));
            }
            return new DefaultTreeNode("location", dto, null);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }
        
    private void locationTreeUpdate(String storageFacilityKey, String locationRef, TreeNode node) throws Exception{
        LocationTypeDTO data = (LocationTypeDTO) node.getData();
        for(TreeNode child : node.getChildren()){
            locationTreeUpdate(storageFacilityKey, data.getLocationType_key(), child);
        }
        locationDAO.updateLocationTypeRef(locationRef, data.getLocationType_key(), storageFacilityKey);        
    }
    
    private void getDraggedNode(String draggedKey, TreeNode node){
        if(node.getChildCount() > 0){
            for(TreeNode child : node.getChildren()){
                getDraggedNode(draggedKey, child);
                if(child.getData() instanceof LocationTypeDTO){
                    LocationTypeDTO dto = (LocationTypeDTO) child.getData();
                    if(dto.getLocationType_key().equals(draggedKey)){
                        draggedNode = child;
                    }
                }   
            }
        }        
    }
    
    private void getDroppedNode(String droppedKey, TreeNode node, String dropType){
        if(node.getChildCount() > 0){
            for(TreeNode child : node.getChildren()){ 
                getDroppedNode(droppedKey, child, dropType);
                if(dropType.equals("location")){
                    if(child.getData() instanceof LocationTypeDTO){
                        LocationTypeDTO dto = (LocationTypeDTO) child.getData();
                        if(dto.getLocationType_key().equals(droppedKey)){
                            droppedNode = child;
                        }
                    }   
                }
                else{
                     if(child.getData() instanceof StorageFacilityDTO){
                        StorageFacilityDTO dto = (StorageFacilityDTO) child.getData();
                        if(dto.getStorageFacility_key().equals(droppedKey)){
                            droppedNode = child;
                        }
                    }   
                }
            }
        }        
    }
    
    public void onNodeSelect(NodeSelectEvent event) { 
        selectedLocation = null;
        selectedStorageFacility = null;

        selectedTreeNode = event.getTreeNode();
        SampleSearchDTO dto = new SampleSearchDTO();
        dto.setOwners(this.getCurrentUserColonyManageWorkgroups());
        //location selected
        if(event.getTreeNode().getData() instanceof LocationTypeDTO){
            selectedLocation = (LocationTypeDTO) event.getTreeNode().getData();
            selectedStorageFacility = locationDAO.getStorageFacilityByKey(selectedLocation.getStorageFacility_key());
            dto.setLocation(selectedLocation.getLocationType_key());
            samples = new SampleDAO().sampleListQuery(dto);
            
            parentLocationName = selectedLocation.getLocationType();
            if(selectedStorageFacility != null){
                setParentStorageFacility(selectedStorageFacility.getStorageFacility());
            }
            else{
                setParentStorageFacility("");
            }
        }
        //storage facility selected
        else{
            selectedStorageFacility = (StorageFacilityDTO) event.getTreeNode().getData();
            parentLocationName = "";
            dto.setStorageFacility(selectedStorageFacility.getStorageFacility_key());
            samples = new SampleDAO().sampleListQuery(dto);
            
            parentStorageFacility = selectedStorageFacility.getStorageFacility();
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
        if(selectedLocation == null && selectedStorageFacility == null && selectedTreeNode != null){
            this.addToMessageQueue("You must select a parent storage facility or location type in the tree to the left.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            LocationTypeDTO dto = new LocationTypeDTO();
            dto.setLocationType(newLocationTypeName);
            dto.setLocationDetail(newLocationTypeDetail);
            dto.setLabel(newLocationTypeName);
            if(selectedLocation != null){
                dto.setLocationTypeRef(selectedLocation.getLocationType_key());
                dto.setStorageFacility_key(selectedLocation.getStorageFacility_key());
            }
            else if(selectedStorageFacility != null){
                dto.setStorageFacility_key(selectedStorageFacility.getStorageFacility_key());
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
        if(selectedLocation == null || selectedTreeNode == null){
            this.addToMessageQueue("You must select a location type in the tree to the left to update.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        LocationTypeDTO loc = (LocationTypeDTO) selectedTreeNode.getData();
        if(!locationChangeOk(loc.getLocationType_key())){
            this.addToMessageQueue("You cannot edit this Location as you are not the owner of all samples in it.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                LocationTypeDTO dto = new LocationTypeDTO();
                dto.setLocationDetail(newLocationTypeDetail);
                dto.setLocationType(newLocationTypeName);
                dto.setLocationTypeRef(selectedLocation.getLocationTypeRef());
                dto.setLocationType_key(selectedLocation.getLocationType_key());
                dto.setStorageFacility_key(selectedLocation.getStorageFacility_key());
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
        if(selectedStorageFacility == null){
            this.addToMessageQueue("You must select a storage facility in the tree to the left to edit.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        for(TreeNode child : selectedTreeNode.getChildren()){
            LocationTypeDTO dto = (LocationTypeDTO) child.getData();
            if(!locationChangeOk(dto.getLocationType_key())){
                this.addToMessageQueue("You cannot edit this Storage Facility as you are not the owner of all samples in it.", FacesMessage.SEVERITY_ERROR);
                flag = true;
                break;
            }
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
        if(containsSamples || locationDAO.locationContainsSamples(selectedLocation.getLocationType_key())){
            containsSamples = false;
            flag = true;
            this.addToMessageQueue("This Location contains samples still, to delete a Location you must remove all samples first.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //do delete
            try{
                deleteWalk(selectedTreeNode);
                LocationTypeDTO data = (LocationTypeDTO) selectedTreeNode.getData(); 
                locationDAO.deleteLocation(data.getLocationType_key());
                
                //update view
                selectedTreeNode.getParent().getChildren().remove(selectedTreeNode);
                selectedTreeNode.setParent(null);
                selectedTreeNode = null;
                samples = new ArrayList<SampleXtraDTO>();
                this.addToMessageQueue("Location " + data.getLocationType() + " successfully deleted.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void deleteStorage(){
        boolean flag = false;
        for(TreeNode child : selectedTreeNode.getChildren()){
            locationContainsSamples(child);
            if(containsSamples){
                containsSamples = false;
                flag = true;
                this.addToMessageQueue("This storage facility contains samples still, to delete a storage facility you must remove all samples first.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(!flag){
            //do delete - set parent to void, or remove child from children
            try{
                //delete locations
                deleteWalk(selectedTreeNode);
                //delete storage facility
                StorageFacilityDTO data = (StorageFacilityDTO) selectedTreeNode.getData(); 
                locationDAO.deleteStorageFacility(data.getStorageFacility_key());
                
                //update view
                selectedTreeNode.getParent().getChildren().remove(selectedTreeNode);
                selectedTreeNode.setParent(null);
                selectedTreeNode = null;
                samples = new ArrayList<SampleXtraDTO>();
                this.addToMessageQueue("Storage Facility " + data.getStorageFacility() + " successfully deleted.", FacesMessage.SEVERITY_INFO);
                
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    private boolean locationChangeOk(String locationKey){
        ArrayList<String> owners = new SampleDAO().getLocationOwners(locationKey);
        for(String owner : owners){
            if(!containsIgnoreCase(owner, this.getCurrentUserColonyManageWorkgroups())){
                return false;
            }
        }        
        return true;
    }
    
    private boolean containsIgnoreCase(String str, ArrayList<String> values){
        boolean there = false;
        for(String value : values){
            if(str.equalsIgnoreCase(value)){
                there = true;
            }
        }
        return there;
    }
    
    private void deleteWalk(TreeNode node) throws Exception{
        for(TreeNode child : node.getChildren()){
            LocationTypeDTO data = (LocationTypeDTO) child.getData();
            locationDAO.deleteLocation(data.getLocationType_key());
            deleteWalk(child);
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
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the locationClass
     */
    public String getLocationClass() {
        return locationClass;
    }

    /**
     * @param locationClass the locationClass to set
     */
    public void setLocationClass(String locationClass) {
        this.locationClass = locationClass;
    }

    /**
     * @return the storageClass
     */
    public String getStorageClass() {
        return storageClass;
    }

    /**
     * @param storageClass the storageClass to set
     */
    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
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
     * @return the selectedLocation
     */
    public LocationTypeDTO getSelectedLocation() {
        return selectedLocation;
    }

    /**
     * @param selectedLocation the selectedLocation to set
     */
    public void setSelectedLocation(LocationTypeDTO selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    /**
     * @return the selectedStorageFacility
     */
    public StorageFacilityDTO getSelectedStorageFacility() {
        return selectedStorageFacility;
    }

    /**
     * @param selectedStorageFacility the selectedStorageFacility to set
     */
    public void setSelectedStorageFacility(StorageFacilityDTO selectedStorageFacility) {
        this.selectedStorageFacility = selectedStorageFacility;
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
}
