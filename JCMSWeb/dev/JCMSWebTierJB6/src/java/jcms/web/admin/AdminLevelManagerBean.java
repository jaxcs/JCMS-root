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

import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dao.LevelDAO;
import jcms.integrationtier.dto.LevelDTO;
import jcms.integrationtier.dto.AdminRoomDTO;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;

/**
 *
 * @author mkamato
 */
public class AdminLevelManagerBean extends AdminBean {
    
    private DefaultTreeNode root = new DefaultTreeNode();
    private String xmax = "";
    private String ymax = "";
    private String levelName = "";
    private String levelDetail = "";
    private String parentLevel = "";
    private String parentRoom = "";
    
    private AdminRoomDTO selectedRoom = null;
    private LevelDTO selectedLevel = null;
    private TreeNode selectedTreeNode = null;
    
    private TreeNode draggedNode = null;
    private TreeNode droppedNode = null;
    
    private String editLevelName = "";
    private String editLevelDetail = "";
    private String editRows = "";
    private String editColumns = "";
    private boolean editIsActive;
    
    private LevelDAO levelDAO = new LevelDAO();
    private CVAdministrationDAO adminDAO = new CVAdministrationDAO();
    
    private String function = "addLevel";
    
    private String roomClass = "room";
    private String levelClass = "level";
    private boolean deleteDisplayed = false;
    private boolean editApplyToAllLevels = true;
    private boolean hasContainers = false;
    
    
    public AdminLevelManagerBean(){
        ArrayList<AdminRoomDTO> rooms = adminDAO.getRooms();
        for(AdminRoomDTO room : rooms){
            DefaultTreeNode roomNode = new DefaultTreeNode("room", room, root);
            for(LevelDTO level : levelDAO.getBaseLevelsByRoomKey(room.getRoomKey())){
                if(level.getIsActive().equals("false")){
                    level.setStyle("color: gray;");
                }
                else{
                    level.setStyle("color: black;");
                }
                DefaultTreeNode levelNode = new DefaultTreeNode("level", level, roomNode);
                buildSubTree(levelNode);
            }
        }
    }
    
    private void buildSubTree(TreeNode parent){
        LevelDTO dto = (LevelDTO) parent.getData();
        for(LevelDTO level : levelDAO.getReferencedLevelsByLevelKey(dto.getLevel_key())){
            if(level.getIsActive().equals("false")){
                level.setStyle("color: gray;");
            }
            else{
                level.setStyle("color: black;");
            }
            DefaultTreeNode child = new DefaultTreeNode("level", level, parent);
            buildSubTree(child);
        }
    }
    
    public void refresh(){
        root = new DefaultTreeNode();
        ArrayList<AdminRoomDTO> rooms = adminDAO.getRooms();
        for(AdminRoomDTO room : rooms){
            DefaultTreeNode roomNode = new DefaultTreeNode("room", room, root);
            for(LevelDTO level : levelDAO.getBaseLevelsByRoomKey(room.getRoomKey())){
                if(level.getIsActive().equals("false")){
                    level.setStyle("color: gray;");
                }
                else{
                    level.setStyle("color: black;");
                }
                DefaultTreeNode levelNode = new DefaultTreeNode("level", level, roomNode);
                buildSubTree(levelNode);
            }
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) { 
        selectedLevel = null;
        selectedRoom = null;
        deleteDisplayed = false;
        
        this.setEditColumns("");
        this.setEditRows("");
        this.setEditLevelDetail("");
        this.setEditLevelName("");

        selectedTreeNode = event.getTreeNode();
        //level selected
        if(event.getTreeNode().getData() instanceof LevelDTO){
            selectedLevel = (LevelDTO) event.getTreeNode().getData();
            deleteDisplayed = true;
            
            AdminRoomDTO temp = new AdminRoomDTO();
            temp.setRoomKey(selectedLevel.getRoom_key());
            selectedRoom = adminDAO.getRooms(temp).get(0);
            
            parentLevel = selectedLevel.getLevelId();
            if(selectedRoom != null){
                parentRoom = selectedRoom.getRoomName();
            }
            else{
                parentRoom = "";
            }
            this.setEditColumns(selectedLevel.getXmax());
            this.setEditRows(selectedLevel.getYmax());
            this.setEditLevelDetail(selectedLevel.getLevelDetail());
            this.setEditLevelName(selectedLevel.getLevelId());
            if(selectedLevel.getIsActive().equalsIgnoreCase("false")){
                this.setEditIsActive(false);
            }
            else{
                this.setEditIsActive(true);
            }
        }
        //storage facility selected
        else{
            selectedRoom = (AdminRoomDTO) event.getTreeNode().getData();
            parentLevel = "";
            
            parentRoom = selectedRoom.getRoomName();
        }
    }
    
    public void nodeExpand(NodeExpandEvent event){
        event.getTreeNode().setExpanded(true);
    }
    
    public void nodeCollapse(NodeCollapseEvent event){
        event.getTreeNode().setExpanded(false);
    }
    
    public void onNodeDrop(){
        String draggedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("levelDragKey");
        String droppedKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dropKey");
        //level or room
        String droppedClass = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dropClass");

        System.out.println("Dragged: " + draggedKey);
        System.out.println("Dropped on: " + droppedKey);
        System.out.println("Dropped class: " + droppedClass);
        
        boolean flag = false;
        
        if(function.equals("move")){
            if(draggedKey.equals(droppedKey) && droppedClass.equals(levelClass)){
                flag = true;
                this.addToMessageQueue("A vivaria layout cannot be moved into itself.", FacesMessage.SEVERITY_ERROR);
            }
            if(!flag){
                //tree walks
                getDraggedNode(draggedKey, root);
                getDroppedNode(droppedKey, root, droppedClass);
                
                try{
                    LevelDTO dto = (LevelDTO) draggedNode.getData();

                    if(!flag){
                        String newRoomKey = "";
                        
                        if(droppedNode.getData() instanceof LevelDTO){
                            //dropped on a level node
                            LevelDTO levDTO = (LevelDTO) droppedNode.getData();
                            dto.setRoom_key(levDTO.getRoom_key());
                            dto.setLevelRef(levDTO.getLevel_key());
                            levelTreeUpdate(levDTO.getRoom_key(), levDTO.getLevel_key(), draggedNode);
                            newRoomKey = levDTO.getRoom_key();
                        }
                        else{
                            //dropped on a storage facility
                            AdminRoomDTO roomDTO = (AdminRoomDTO) droppedNode.getData();
                            dto.setRoom_key(roomDTO.getRoomKey());
                            dto.setLevelRef("0");
                            levelTreeUpdate(roomDTO.getRoomKey(), "0", draggedNode);
                            newRoomKey = roomDTO.getRoomKey();
                        }
                        
                        //update all the containers container histories for that and subsequent levels 
                        updateContainerHistoryRoom(draggedNode, newRoomKey); 
                        
                        //update tree
                        draggedNode.setParent(droppedNode);

                        if(droppedNode.getData() instanceof LevelDTO){
                            LevelDTO locDTO = (LevelDTO) droppedNode.getData();
                            this.addToMessageQueue("Vivaria layout " + dto.getLevelId() + " moved to " + locDTO.getLevelId(), FacesMessage.SEVERITY_INFO);
                        }
                        else{
                            AdminRoomDTO storDTO = (AdminRoomDTO) droppedNode.getData();
                            this.addToMessageQueue("Vivaria layout " + dto.getLevelId() + " moved to " + storDTO.getRoomName(), FacesMessage.SEVERITY_INFO);
                        }
                    }
                }
                catch(Exception e){
                    this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        else if(function.equals("duplicate")){
            //tree walks
                getDraggedNode(draggedKey, root);
                getDroppedNode(droppedKey, root, droppedClass);

                //create subtree in database and PrimeFaces object
                TreeNode duplicate = duplicateTreeNode(draggedNode, droppedNode);
                createDraggedSubTree(draggedNode, duplicate);

                //add to view
                duplicate.setParent(droppedNode);
        }
        else{
            this.addToMessageQueue("To move or duplicate a vivaria layout please select that option from the functions above.", FacesMessage.SEVERITY_WARN);
        }
        draggedNode = null;
        droppedNode = null;
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
            //not the parent of the node being duplicated -- node is TreeNode to be duplicated
            LevelDTO dto = new LevelDTO((LevelDTO) node.getData());
            if(parent.getData() instanceof LevelDTO){
                //case 1: parent is another level type.
                LevelDTO parentDTO = new LevelDTO((LevelDTO) parent.getData());
                dto.setLevel_key("");
                dto.setRoom_key(parentDTO.getRoom_key());
                dto.setLevelRef(parentDTO.getLevel_key());
                //insert the duplicated level into the database with adjusted level and storage facility references
                dto.setLevel_key(levelDAO.insertLevel(dto));
            }
            else{    
                //case 2: parent is a storage facility
                AdminRoomDTO parentDTO = new AdminRoomDTO((AdminRoomDTO) parent.getData());
                dto.setLevel_key("");
                dto.setRoom_key(parentDTO.getRoomKey());
                dto.setLevelRef("0");
                //insert the duplicated level into the database with adjusted levels and storage facility references
                dto.setLevel_key(levelDAO.insertLevel(dto));
            }
            return new DefaultTreeNode("level", dto, null);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }
    
    private void levelTreeUpdate(String roomKey, String levelRef, TreeNode node) throws Exception{
        LevelDTO data = (LevelDTO) node.getData();
        for(TreeNode child : node.getChildren()){
            levelTreeUpdate(roomKey, data.getLevel_key(), child);
        }
        levelDAO.updateLevelRef(levelRef, data.getLevel_key(), roomKey);        
    }
    
    private void getDraggedNode(String draggedKey, TreeNode node){
        if(node.getChildCount() > 0){
            for(TreeNode child : node.getChildren()){
                getDraggedNode(draggedKey, child);
                if(child.getData() instanceof LevelDTO){
                    LevelDTO dto = (LevelDTO) child.getData();
                    if(dto.getLevel_key().equals(draggedKey)){
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
                if(dropType.equals("level")){
                    if(child.getData() instanceof LevelDTO){
                        LevelDTO dto = (LevelDTO) child.getData();
                        if(dto.getLevel_key().equals(droppedKey)){
                            droppedNode = child;
                        }
                    }   
                }
                else{
                     if(child.getData() instanceof AdminRoomDTO){
                        AdminRoomDTO dto = (AdminRoomDTO) child.getData();
                        if(dto.getRoomKey().equals(droppedKey)){
                            droppedNode = child;
                        }
                    }   
                }
            }
        }        
    }
    
    public void updateLevel(){
        boolean flag = false;
        if(editLevelName.equals("")){
            flag = true;
            this.addToMessageQueue("Vivaria layout name is required, please provide a vivaria layout name.", FacesMessage.SEVERITY_ERROR);
        }
        if(editColumns.equals("")){
            flag = true;
            this.addToMessageQueue("Columns are required, please provide a number of columns.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            try{
                Integer.parseInt(editColumns);
            }
            catch(Exception e){
                this.addToMessageQueue("Columns must be an integer.", FacesMessage.SEVERITY_ERROR);
                return;
            }
        }
        if(editRows.equals("")){
            flag = true;
            this.addToMessageQueue("Rows are required, please provide a number of rows.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            try{
                Integer.parseInt(editRows);
            }
            catch(Exception e){
                this.addToMessageQueue("Rows must be an integer.", FacesMessage.SEVERITY_ERROR);
                return;
            }
        }
        if(selectedLevel == null){
                flag = true;
                this.addToMessageQueue("You must select a vivaria layout you wish to edit.", FacesMessage.SEVERITY_ERROR);
        }
        if(levelDAO.getXMax(selectedLevel.getLevel_key()) > Integer.parseInt(editColumns)){
                flag = true;
                this.addToMessageQueue("There are one or more cages that exist in a column that is greater than the selected number of columns,"
                        + " please move or remove these cages in order to edit this vivaria layout .", FacesMessage.SEVERITY_ERROR);
        }
        if(levelDAO.getYMax(selectedLevel.getLevel_key()) > Integer.parseInt(editRows)){
                flag = true;
                this.addToMessageQueue("There are one or more cages that exist in a row that is greater than the selected number of rows,"
                        + " please move or remove these cages in order to edit this vivaria layout .", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            try{
                LevelDTO dto = (LevelDTO) selectedTreeNode.getData();
                if(editApplyToAllLevels){
                    if(editIsActive){
                        changeLevelTreeStatus("-1", selectedTreeNode);
                    }
                    else{
                        changeLevelTreeStatus("0", selectedTreeNode);
                    }
                }
                else{
                    if(editIsActive){
                        levelDAO.updateLevelStatus("-1", dto.getLevel_key());
                        dto.setIsActive("true");
                    }
                    else{
                        levelDAO.updateLevelStatus("0", dto.getLevel_key());
                        dto.setIsActive("false");
                    }
                }
                dto.setLabel(editLevelName);
                dto.setLevelId(editLevelName);
                dto.setXmax(editColumns);
                dto.setYmax(editRows);
                dto.setLevelDetail(editLevelDetail);
                levelDAO.updateLevel(dto);
                this.addToMessageQueue("Vivaria layout successfully edited.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    private void changeLevelTreeStatus(String status, TreeNode node) throws Exception{
        LevelDTO data = (LevelDTO) node.getData();
        if(status.equals("0")){
            data.setIsActive("false");
            data.setStyle("color: gray;");
        }
        else{
            data.setIsActive("true");
            data.setStyle("color: black;");
        }
        levelDAO.updateLevelStatus(status, data.getLevel_key());
        for(TreeNode child : node.getChildren()){
            changeLevelTreeStatus(status, child);
        }
    }
    
    public void addLevel(){
        boolean flag = false;
        if(levelName.equals("")){
            flag = true;
            this.addToMessageQueue("Vivaria layout name is required, please provide a vivaria layout name.", FacesMessage.SEVERITY_ERROR);
        }
        if(xmax.equals("")){
            flag = true;
            this.addToMessageQueue("Rows are required, please provide a value for rows.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            try{
                Integer.parseInt(xmax);
            }
            catch(Exception e){
                flag = true;
                this.addToMessageQueue("Rows must be an integer.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(ymax.equals("")){
            flag = true;
            this.addToMessageQueue("Columns are required, please provide a value for columns.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            try{
                Integer.parseInt(ymax);
            }
            catch(Exception e){
                flag = true;
                this.addToMessageQueue("Columns must be an integer.", FacesMessage.SEVERITY_ERROR);
            }
        }
        if(selectedTreeNode == null){
            flag = true;
            this.addToMessageQueue("Please select a parent vivaria layout  or Room", FacesMessage.SEVERITY_ERROR);
        }
        try{
            if(!flag){
                if(selectedTreeNode.getData() instanceof AdminRoomDTO){
                    AdminRoomDTO parent = (AdminRoomDTO) selectedTreeNode.getData();
                    LevelDTO dto = new LevelDTO();
                    dto.setLabel(levelName);
                    dto.setLevelDetail(levelDetail);
                    dto.setLevelId(levelName);
                    dto.setLevelRef("0");
                    dto.setRoom_key(parent.getRoomKey());
                    dto.setXmax(xmax);
                    dto.setYmax(ymax);
                    dto.setLevel_key(levelDAO.insertLevel(dto));
                    TreeNode temp = new DefaultTreeNode("level", dto, selectedTreeNode);
                }
                else{
                    LevelDTO parent = (LevelDTO) selectedTreeNode.getData();
                    LevelDTO dto = new LevelDTO();
                    dto.setLabel(levelName);
                    dto.setLevelDetail(levelDetail);
                    dto.setLevelId(levelName);
                    dto.setLevelRef(parent.getLevel_key());
                    dto.setRoom_key(parent.getRoom_key());
                    dto.setXmax(xmax);
                    dto.setYmax(ymax);
                    dto.setLevel_key(levelDAO.insertLevel(dto));
                    TreeNode temp = new DefaultTreeNode("level", dto, selectedTreeNode);
                }
                this.addToMessageQueue("Vivaria layout " + levelName + " successfully added.", FacesMessage.SEVERITY_INFO);
            }
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }            
    }

    public void deleteLevel(){
        boolean flag = false;
        
        //check if levels have cages here

        levelHasContainers(selectedTreeNode);
        if(hasContainers){
           flag = true;
           this.addToMessageQueue("This vivaria layout cannot be deleted because it is referenced by another entry in your database, if you do not wish to see this "
                   + "vivaria layout anymore change it to 'inactive' in the edit panel.", FacesMessage.SEVERITY_ERROR);
        }
        if(!flag){
            //do delete
            try{
                deleteWalk(selectedTreeNode);
                LevelDTO data = (LevelDTO) selectedTreeNode.getData(); 
                levelDAO.deleteLevel(data.getLevel_key());
                
                //update view
                selectedTreeNode.getParent().getChildren().remove(selectedTreeNode);
                selectedTreeNode.setParent(null);
                selectedTreeNode = null;
                this.addToMessageQueue("Vivaria layout " + data.getLevelId() + " successfully deleted.", FacesMessage.SEVERITY_INFO);
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
        hasContainers = false;
    }
    
    private void deleteWalk(TreeNode node) throws Exception{
        for(TreeNode child : node.getChildren()){
            LevelDTO data = (LevelDTO) child.getData();
            levelDAO.deleteLevel(data.getLevel_key());
            deleteWalk(child);
        }
    }
    
    private void levelHasContainers(TreeNode node){
        LevelDTO dto = (LevelDTO) node.getData();
        if(levelDAO.levelHasContainers(dto.getLevel_key())){
            hasContainers = true;
        }
        for(TreeNode child : node.getChildren()){
            levelHasContainers(child);
        }
    }
    
    private void updateContainerHistoryRoom(TreeNode node, String roomKey) throws Exception{
        LevelDTO data = (LevelDTO) node.getData();
        levelDAO.updateContainerHistoryRoomKeyByLevel(data.getLevel_key(), roomKey);
        for(TreeNode child : node.getChildren()){
            updateContainerHistoryRoom(child, roomKey);
        }
    }
    
    /**
     * @return the root
     */
    public DefaultTreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(DefaultTreeNode root) {
        this.root = root;
    }

    /**
     * @return the xmax
     */
    public String getXmax() {
        return xmax;
    }

    /**
     * @param xmax the xmax to set
     */
    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    /**
     * @return the ymax
     */
    public String getYmax() {
        return ymax;
    }

    /**
     * @param ymax the ymax to set
     */
    public void setYmax(String ymax) {
        this.ymax = ymax;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * @param levelName the levelName to set
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @return the levelDetail
     */
    public String getLevelDetail() {
        return levelDetail;
    }

    /**
     * @param levelDetail the levelDetail to set
     */
    public void setLevelDetail(String levelDetail) {
        this.levelDetail = levelDetail;
    }

    /**
     * @return the parentLevel
     */
    public String getParentLevel() {
        return parentLevel;
    }

    /**
     * @param parentLevel the parentLevel to set
     */
    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }

    /**
     * @return the parentRoom
     */
    public String getParentRoom() {
        return parentRoom;
    }

    /**
     * @param parentRoom the parentRoom to set
     */
    public void setParentRoom(String parentRoom) {
        this.parentRoom = parentRoom;
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
     * @return the roomClass
     */
    public String getRoomClass() {
        return roomClass;
    }

    /**
     * @param roomClass the roomClass to set
     */
    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    /**
     * @return the levelClass
     */
    public String getLevelClass() {
        return levelClass;
    }

    /**
     * @param levelClass the levelClass to set
     */
    public void setLevelClass(String levelClass) {
        this.levelClass = levelClass;
    }

    /**
     * @return the deleteDisplayed
     */
    public boolean isDeleteDisplayed() {
        return deleteDisplayed;
    }

    /**
     * @param deleteDisplayed the deleteDisplayed to set
     */
    public void setDeleteDisplayed(boolean deleteDisplayed) {
        this.deleteDisplayed = deleteDisplayed;
    }

    /**
     * @return the editLevelName
     */
    public String getEditLevelName() {
        return editLevelName;
    }

    /**
     * @param editLevelName the editLevelName to set
     */
    public void setEditLevelName(String editLevelName) {
        this.editLevelName = editLevelName;
    }

    /**
     * @return the editLevelDetail
     */
    public String getEditLevelDetail() {
        return editLevelDetail;
    }

    /**
     * @param editLevelDetail the editLevelDetail to set
     */
    public void setEditLevelDetail(String editLevelDetail) {
        this.editLevelDetail = editLevelDetail;
    }

    /**
     * @return the editRows
     */
    public String getEditRows() {
        return editRows;
    }

    /**
     * @param editRows the editRows to set
     */
    public void setEditRows(String editRows) {
        this.editRows = editRows;
    }

    /**
     * @return the editColumns
     */
    public String getEditColumns() {
        return editColumns;
    }

    /**
     * @param editColumns the editColumns to set
     */
    public void setEditColumns(String editColumns) {
        this.editColumns = editColumns;
    }

    /**
     * @return the editIsActive
     */
    public boolean isEditIsActive() {
        return editIsActive;
    }

    /**
     * @param editIsActive the editIsActive to set
     */
    public void setEditIsActive(boolean editIsActive) {
        this.editIsActive = editIsActive;
    }

    /**
     * @return the editApplyToAllLevels
     */
    public boolean isEditApplyToAllLevels() {
        return editApplyToAllLevels;
    }

    /**
     * @param editApplyToAllLevels the editApplyToAllLevels to set
     */
    public void setEditApplyToAllLevels(boolean editApplyToAllLevels) {
        this.editApplyToAllLevels = editApplyToAllLevels;
    }
}
