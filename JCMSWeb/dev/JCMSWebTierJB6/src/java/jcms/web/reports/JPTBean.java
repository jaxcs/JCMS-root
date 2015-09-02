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

package jcms.web.reports;


import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.dto.JPTDTO;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.web.colonyManagement.MiceListCommon;
import jcms.web.common.ExtendedTableBean;
import jcms.web.common.SelectItemWrapper;
import org.richfaces.model.TreeNode;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import org.richfaces.component.UITree;
import jcms.web.reports.JPTTreeNode;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.primefaces.context.RequestContext;
import jcms.middletier.dto.ListSupportDTO;
import jcms.integrationtier.colonyManagement.AlleleEntity;




/**
 *
 * @author mkamato
 */
public class JPTBean extends WTBaseBackingBean{
    
    private JPTTreeNode rootNode = new JPTTreeNode();
    private JPTDTO selectedData = new JPTDTO();
    private JPTDTO theJPTData = null;
    private String mouseID = "";
    private String pedigreeType = "ancestry"; //either progeny or ancestry
    private ServiceFacadeLocal sfl = new BusinessTierPortal().getServiceFacadeLocal();
    private int depth = 3;
    private int count = 0;
    
    private boolean displayLitterID = true;
    private boolean displayMatingID = true;
    private boolean displaySex = true;
    private boolean displayStrain = true;
    private boolean displayGenotype = true;
    
    private String panelHeight = "";
    
    //variable for mouse search    
    private ListDataModel mouseDataModel = null;
    private MouseSearchDTO mouseSearch = new MouseSearchDTO();
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private MiceListCommon mouseInfo = new MiceListCommon();
    private ExtendedTableBean mouseSelectionETB = new ExtendedTableBean();
    private ArrayList<String> wgs = new ArrayList<String>();
    
    //for d3
    String jsonString;
    
    //for genotype
    private GeneEntity gene = null;
    private String allele1 = "";
    private String allele2 = "";
    private ArrayList<SelectItem> allele1s = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> allele2s = new ArrayList<SelectItem>();
    private int hetCount = 0;
    private int hom1Count = 0;
    private int hom2Count = 0;
    private int ko1Count = 0;
    private int ko2Count = 0;
    private int nullCount = 0;
    
    
    private boolean myContains(ArrayList<String> wgs, String owner){
        for(String wg:wgs){
            if(wg.equalsIgnoreCase(owner)){
                return true;
            }
        }
        return false;
    }
        
    public void generateTreeData(){
        //reset genotype counts
        hetCount = 0;
        hom1Count = 0;
        hom2Count = 0;
        ko1Count = 0;
        ko2Count = 0;
        nullCount = 0;
        
        selectedData = new JPTDTO();
        rootNode = new JPTTreeNode();
        JPTTreeNode rootMouse = new JPTTreeNode();
        
        //get workgroup information to pass on to integration tier.
        ArrayList<WorkgroupEntity> wgLst = ((ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst"));
        wgs = new ArrayList<String>();
        for (WorkgroupEntity we : wgLst) {
            wgs.add(we.getWorkgroupName());
        }
            
                
        if((MouseEntity) getRepositoryService().baseFindByMoueID(new MouseEntity(), mouseID) == null){
            this.addToMessageQueue("Mouse '" + mouseID + "' could not be found.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage("Mouse '" + mouseID + "' could not be found.", "generateTreeData"));
            return;
        }
        else{
            MouseEntity tempMouse = (MouseEntity) getRepositoryService().baseFindByMoueID(new MouseEntity(), mouseID);
            if(!myContains(wgs, tempMouse.getOwner())){
                this.addToMessageQueue("Mouse '" + mouseID + "' could not be found.", FacesMessage.SEVERITY_ERROR);
                this.getLogger().logError(this.formatLogMessage("Mouse '" + mouseID + "' could not be found.", "generateTreeData"));
                return;
            }
        }
        
        try{
            getRootNode().addChild("a", rootMouse);
            
            
            
            setTheJPTData(sfl.generateJPTData(mouseID, pedigreeType, depth, wgs));
            getTheJPTData().setDisplayString(buildDisplayString(getTheJPTData()));
            getTheJPTData().setType("type1");
            getTheJPTData().setRootNode(true);
            
            //sets rootnode as mouse of interest selected by user
            rootMouse.setData(getTheJPTData());
            System.out.println("Started Building rich tree");
            recursiveTreeBuild(rootMouse);
            System.out.println("Done Building rich tree");
        }
        catch(Exception e){
            this.addToMessageQueue("Could not generate JPT Data: " + e, FacesMessage.SEVERITY_ERROR);
        }
        System.out.println("Number of nodes: " + count);
        count = 0;
        JSONObject jo = new JSONObject();
        createJson(theJPTData, jo);
        jsonString = jo.toJSONString();
    }
    
    public void returnJSON(){
        RequestContext reqCTX = RequestContext.getCurrentInstance();
        jsonString = jsonString.replace("&", "&amp;");
        jsonString = jsonString.replace(">", "&gt;");
        jsonString = jsonString.replace("<", "&lt;");
        reqCTX.addCallbackParam("jsonData", jsonString);
        if(gene == null || gene.getLabSymbol() == null){
            reqCTX.addCallbackParam("gene", "null");
        }
        else{
            reqCTX.addCallbackParam("gene", gene.getLabSymbol().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;"));
        }
        reqCTX.addCallbackParam("allele1", allele1.replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;"));
        reqCTX.addCallbackParam("allele2", allele2.replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;"));
        reqCTX.addCallbackParam("hetCount", hetCount);
        reqCTX.addCallbackParam("hom1Count", hom1Count);
        reqCTX.addCallbackParam("hom2Count", hom2Count);
        reqCTX.addCallbackParam("ko1Count", ko1Count);
        reqCTX.addCallbackParam("ko2Count", ko2Count);
        reqCTX.addCallbackParam("nullCount", nullCount);
    }
    
    //walk JPT Data and create JSON
    private void createJson(JPTDTO dto, JSONObject jo){
        if(myContains(wgs, dto.getOwner())){
            //check genotype
            if(gene != null && !allele1.equals("")){
                checkGenotype(dto.getGenotype());
            }
            jo.put("ID", dto.getID());
            jo.put("birthDate", dto.getBirthDate());
            jo.put("breedingStatus", dto.getBreedingStatus());
            jo.put("COD", dto.getCOD());
            jo.put("CODNotes", dto.getCODNotes());
            jo.put("coatColor", dto.getCoatColor());
            jo.put("comment", dto.getComment());
            jo.put("dam1Genotype", dto.getDam1Genotype());
            jo.put("dam1ID", dto.getDam1ID());
            jo.put("dam2Genotype", dto.getDam2Genotype());
            jo.put("dam2ID", dto.getDam2ID());
            jo.put("diet", dto.getDiet());
            jo.put("exitDate", dto.getExitDate());
            jo.put("generation", dto.getGeneration());
            jo.put("genotype", dto.getGenotype());
            jo.put("lifeStatus", dto.getLifeStatus());
            jo.put("litterID", dto.getLitterID());
            jo.put("matingID", dto.getMatingID());
            jo.put("origin", dto.getOrigin());
            jo.put("owner", dto.getOwner());
            jo.put("protocol", dto.getProtocol());
            jo.put("sex", dto.getSex());
            jo.put("sireGenotype", dto.getSireGenotype());
            jo.put("sireID", dto.getSireID());
            jo.put("strainName", dto.getStrainName());//dto.getStrainName());
        }
        else{
            jo.put("ID", "You do not own this mouse.");
        }
        JSONArray jArr = new JSONArray();
        if(!dto.getNextGen().isEmpty()){
            jo.put("children", jArr);
        }
        for(JPTDTO jnode : dto.getNextGen()){
            JSONObject newJO = new JSONObject();
            createJson(jnode, newJO);
            jArr.add(newJO);
        }
    }
    
    private void checkGenotype(String genotype){
        //two cases, case one Gene + allele1 provided, case two Gene + allele1 + allele2 provided
        //only gene and allele 1
        if(allele2.equals("")){
            //two possibilities, either has a Gene + allele1 + whatever, or it doesn't.
            if(genotype.contains(gene.getLabSymbol() + " " + allele1)){
                hetCount++;
            }
            else{
                nullCount++;
            }
        }
        else{
            //6 possibilities, het1 or 2, hom 1 or 2, KO 1 or 2 OR not in there
            //hetCase 1 (Gene A + allele B + allele C
            if(genotype.contains(gene.getLabSymbol() + " " + allele1 + " " + allele2)){
                hetCount++;
            }
            //hetCase 2 (Gene A + allele C + allele B
            else if(genotype.contains(gene.getLabSymbol() + " " + allele2 + " " + allele1)){
                hetCount++;
            }
            //hom case 1 (Gene A + allele B + allele B
            else if(genotype.contains(gene.getLabSymbol() + " " + allele1 + " " + allele1)){
                hom1Count++;
            }
            //hom case 2 (Gene A + allele C + allele C
            else if(genotype.contains(gene.getLabSymbol() + " " + allele2 + " " + allele2)){
                hom2Count++;
            }
            //KO case 1 (Gene A + allele B)
            else if(genotype.contains(gene.getLabSymbol() + " " + allele1)){
                ko1Count++;
            }
            //KO case 2 (Gene A + allele B)
            else if(genotype.contains(gene.getLabSymbol() + " " + allele2)){
                ko2Count++;
            }
            else{
                nullCount++;
            }
        }
    }
        
    public void changeMouseIDtoSelected(){
        mouseID=selectedData.getID();
    }
    
    private void recursiveTreeBuild(JPTTreeNode root){
        JPTDTO rootData = root.getData();
        for(JPTDTO childData : rootData.getNextGen()){
            if (!childData.getID().equals("")) {
                JPTTreeNode child = new JPTTreeNode();
                childData.setType("type1");
                childData.setDisplayString(buildDisplayString(childData));
                child.setData(childData);
                root.addChild(new Integer(count).toString(), child);
                count++;
                recursiveTreeBuild(child);
            }
            else if(childData.getIconPath().equals("/images/stopSign.png")){
                JPTTreeNode child = new JPTTreeNode();
                childData.setType("type1");
                childData.setDisplayString("You do not have permissions to view this mouse.");
                child.setData(childData);
                root.addChild(new Integer(count).toString(), child);
                count++;
            }
        }
    }
    
    
    public String buildDisplayString(JPTDTO mouseData){
        String displayString = "Mouse ID: " + mouseData.getID();
        if(displayLitterID){
            displayString = displayString + " : Litter ID " + mouseData.getLitterID();
        }
        if(displayMatingID){
            displayString = displayString + " : Mating ID " + mouseData.getMatingID();
        }
        if(displayStrain){
            displayString = displayString + " : Strain " + mouseData.getStrainName();
        }
        if(displayGenotype){
            displayString = displayString + " : Genotype " + mouseData.getGenotype();
        }
        if(displaySex){
            displayString = displayString + " : Sex " + mouseData.getSex();
        }
        return displayString;
    }
        
    
    public void processSelection(TreeSelectionChangeEvent event){
        List<Object> selection = new ArrayList<Object>(event.getNewSelection());
        Object currentSelectionKey = selection.get(0);
        UITree tree = (UITree) event.getSource();
        
        Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        JPTTreeNode currentSelection = (JPTTreeNode) tree.getRowData();
        tree.setRowKey(storedKey);
        selectedData = currentSelection.getData();
    }
    
    public void mousePosition(ActionEvent e){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            String clientId = e.getComponent().getClientId(context);
            Map requestParams = context.getExternalContext().getRequestParameterMap();
            System.out.println(requestParams);
            this.addToMessageQueue(requestParams.get(clientId + ".x") + " " + requestParams.get(clientId + ".y"), FacesMessage.SEVERITY_INFO);
        }
        catch(Exception ex){
            this.addToMessageQueue("Error: " +ex, FacesMessage.SEVERITY_INFO);
        }
    }
    
    public Boolean nodeOpened(UITree tree){
        JPTDTO theDTO = (JPTDTO) tree.getRowData();
        if (theDTO != null) {
            if (theDTO.isRootNode()) {
                return Boolean.TRUE;
            } 
            else {
                return null;
            }
        }
        else{
            return null;
        }
    }
    
    public void executeMouseSearch(){
        System.out.println("starting mouse search...");
        mouseDataModel = new ListDataModel();
        if(mouseSearch.getOwners().isEmpty()){
            List<OwnerEntity> owners = (List<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");
            mouseSearch.setOwners(owners);
        }
        mouseDataModel.setWrappedData(mouseInfo.miceSearch(mouseSearch));
    }
    
    public void selectMouseAction(){

        if (mouseInfo != null) {
            MouseEntity mouse = mouseInfo.getSelectedMouse(this.mouseSelectionETB, this.mouseDataModel);

            if (mouse != null && !mouse.getId().equals("") && mouse.getId() != null) {
                System.out.println("Mouse is: " + mouse.getId());
                this.mouseID = mouse.getId();
                clearMousePopupAction();
            } 
            else {
                this.addToMessageQueue("You must select a mouse to proceed.", FacesMessage.SEVERITY_ERROR);
            }
        } 
        else {
            this.addToMessageQueue("You must select a mouse to proceed.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    /**
     * <b>Purpose:</b> Clear the results text and results table. <br />
     */
    public void clearMousePopupAction() {
        mouseDataModel = null;
        mouseSearch = new MouseSearchDTO();
    }
    
    public void geneChangeListener(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();        
        items.add(new SelectItem("", ""));        
        getAllele1s().clear();
        getAllele2s().clear();
        GeneEntity geneEntity = getGene();
        try {
            if (geneEntity == null) {
                setAllele1s((ArrayList<SelectItem>) items.clone());
                setAllele2s((ArrayList<SelectItem>) items.clone());
            } 
            else {
                Integer geneKey = geneEntity.getGeneKey();
                if (geneKey != null && geneKey > 0) {
                    System.out.println("gene Key " + geneKey);

                    for (AlleleEntity entity : new ListSupportDTO().getAllelesByGene(geneKey)) {
                        items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
                        System.out.println(entity.getAllele());
                    }
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
        setAllele1s((ArrayList<SelectItem>) items.clone());
        setAllele2s((ArrayList<SelectItem>) items.clone());
    }
    
    
    /**
     * @return the mouseID
     */
    public String getMouseID() {
        return mouseID.trim();
    }

    /**
     * @param mouseID the mouseID to set
     */
    public void setMouseID(String mouseID) {
        this.mouseID = mouseID.trim();
    }

    /**
     * @return the pedigreeType
     */
    public String getPedigreeType() {
        return pedigreeType;
    }

    /**
     * @param pedigreeType the pedigreeType to set
     */
    public void setPedigreeType(String pedigreeType) {
        this.pedigreeType = pedigreeType;
    }

    /**
     * @return the rootNode
     */
    public TreeNode getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(JPTTreeNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * @return the displayLitterID
     */
    public boolean isDisplayLitterID() {
        return displayLitterID;
    }

    /**
     * @param displayLitterID the displayLitterID to set
     */
    public void setDisplayLitterID(boolean displayLitterID) {
        this.displayLitterID = displayLitterID;
    }

    /**
     * @return the displaySex
     */
    public boolean isDisplaySex() {
        return displaySex;
    }

    /**
     * @param displaySex the displaySex to set
     */
    public void setDisplaySex(boolean displaySex) {
        this.displaySex = displaySex;
    }

    /**
     * @return the displayStrain
     */
    public boolean isDisplayStrain() {
        return displayStrain;
    }

    /**
     * @param displayStrain the displayStrain to set
     */
    public void setDisplayStrain(boolean displayStrain) {
        this.displayStrain = displayStrain;
    }

    /**
     * @return the displayGenotype
     */
    public boolean isDisplayGenotype() {
        return displayGenotype;
    }

    /**
     * @param displayGenotype the displayGenotype to set
     */
    public void setDisplayGenotype(boolean displayGenotype) {
        this.displayGenotype = displayGenotype;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return the selectedData
     */
    public JPTDTO getSelectedData() {
        return selectedData;
    }

    /**
     * @param selectedData the selectedData to set
     */
    public void setSelectedData(JPTDTO selectedData) {
        this.selectedData = selectedData;
    }
    
    /**
     * @return the theJPTData
     */
    public JPTDTO getTheJPTData() {
        return theJPTData;
    }

    /**
     * @param theJPTData the theJPTData to set
     */
    public void setTheJPTData(JPTDTO theJPTData) {
        this.theJPTData = theJPTData;
    }

    /**
     * @return the panelHeight
     */
    public String getPanelHeight() {
        return panelHeight;
    }

    /**
     * @param panelHeight the panelHeight to set
     */
    public void setPanelHeight(String panelHeight) {
        if(!panelHeight.equals("")){
            this.panelHeight = panelHeight;
        }
    }

    /**
     * @return the mouseDataModel
     */
    public ListDataModel getMouseDataModel() {
        return mouseDataModel;
    }

    /**
     * @param mouseDataModel the mouseDataModel to set
     */
    public void setMouseDataModel(ListDataModel mouseDataModel) {
        this.mouseDataModel = mouseDataModel;
    }

    /**
     * @return the mouseSearch
     */
    public MouseSearchDTO getMouseSearch() {
        return mouseSearch;
    }

    /**
     * @param mouseSearch the mouseSearch to set
     */
    public void setMouseSearch(MouseSearchDTO mouseSearch) {
        this.mouseSearch = mouseSearch;
    }

    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return new SelectItemWrapper();
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = new SelectItemWrapper();
    }

    /**
     * @return the mouseInfo
     */
    public MiceListCommon getMouseInfo() {
        return mouseInfo;
    }

    /**
     * @param mouseInfo the mouseInfo to set
     */
    public void setMouseInfo(MiceListCommon mouseInfo) {
        this.mouseInfo = mouseInfo;
    }

    /**
     * @return the mouseSelectionETB
     */
    public ExtendedTableBean getMouseSelectionETB() {
        return mouseSelectionETB;
    }

    /**
     * @param mouseSelectionETB the mouseSelectionETB to set
     */
    public void setMouseSelectionETB(ExtendedTableBean mouseSelectionETB) {
        this.mouseSelectionETB = mouseSelectionETB;
    }

    /**
     * @return the displayMatingID
     */
    public boolean isDisplayMatingID() {
        return displayMatingID;
    }

    /**
     * @param displayMatingID the displayMatingID to set
     */
    public void setDisplayMatingID(boolean displayMatingID) {
        this.displayMatingID = displayMatingID;
    }

    /**
     * @return the gene
     */
    public GeneEntity getGene() {
        return gene;
    }

    /**
     * @param gene the gene to set
     */
    public void setGene(GeneEntity gene) {
        this.gene = gene;
    }

    /**
     * @return the allele1
     */
    public String getAllele1() {
        return allele1;
    }

    /**
     * @param allele1 the allele1 to set
     */
    public void setAllele1(String allele1) {
        this.allele1 = allele1;
    }

    /**
     * @return the allele2
     */
    public String getAllele2() {
        return allele2;
    }

    /**
     * @param allele2 the allele2 to set
     */
    public void setAllele2(String allele2) {
        this.allele2 = allele2;
    }

    /**
     * @return the allele1s
     */
    public ArrayList<SelectItem> getAllele1s() {
        return allele1s;
    }

    /**
     * @param allele1s the allele1s to set
     */
    public void setAllele1s(ArrayList<SelectItem> allele1s) {
        this.allele1s = allele1s;
    }

    /**
     * @return the allele2s
     */
    public ArrayList<SelectItem> getAllele2s() {
        return allele2s;
    }

    /**
     * @param allele2s the allele2s to set
     */
    public void setAllele2s(ArrayList<SelectItem> allele2s) {
        this.allele2s = allele2s;
    }
}
