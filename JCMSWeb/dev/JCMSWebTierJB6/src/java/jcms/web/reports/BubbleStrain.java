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
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.StrainBubbleDAO;
import jcms.integrationtier.dto.StrainBubbleDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.SelectItemWrapper;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.SortedMap;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author mkamato
 */
public class BubbleStrain extends WTBaseBackingBean {
    
    private DualListModel ownersModel = new DualListModel();
    private DualListModel strainsModel = new DualListModel();
    private SelectItemWrapper wrapper = new SelectItemWrapper();
    private boolean collapsed = false;
    
    public BubbleStrain(){
        buildPickLists();
    }
    
    public BubbleStrain(boolean lite){
        
    }
    
    private void buildPickLists(){
        ArrayList<String> owners = new ArrayList<String>();        
        ArrayList<String> strains = new ArrayList<String>();
        try{
            for(SelectItem si : wrapper.getActiveStrainsStringItems()){
                strains.add(si.getValue().toString());
            }
            for(OwnerEntity owner: (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst")){
                owners.add(owner.getOwner());
            }
            ownersModel.setSource(owners);
            strainsModel.setSource(strains);
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void buildBubbleStrain(){
        String json = "";
        String maxMice = "0";
        collapsed = true;
        ArrayList<String> wgs = (ArrayList<String>) ownersModel.getTarget();
        ArrayList<String> strains = (ArrayList<String>) strainsModel.getTarget();
        // Workgroups and Strains are required
        if(wgs.size() > 0 && strains.size() > 0){
            json = buildBubbleJSON(new StrainBubbleDAO().findBubbles(wgs, strains));
            maxMice = new StrainBubbleDAO().getMaxLivingMice(wgs);
        } else {
            this.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Missing required fields. Please select at least one owner and one strain.", null));
        }
        RequestContext reqCTX = RequestContext.getCurrentInstance();
        reqCTX.addCallbackParam("strainViabilityData", json);
        reqCTX.addCallbackParam("maxMice", maxMice);
    }
    
    public void buildBubbleStrainLite(){
        try{
            ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");

            UserEntity userEntity = this.getUserEntity();
            
            StrainBubbleDAO sbDAO = new StrainBubbleDAO();
            ArrayList<StrainBubbleDTO> list = sbDAO.findBubblesLite(owners, userEntity);
            
            String json = buildBubbleJSON(list);
            RequestContext reqCTX = RequestContext.getCurrentInstance();
            reqCTX.addCallbackParam("strainViabilityData", json);
        }
        catch(Exception e){
            System.out.println(e);
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private String buildBubbleJSON(ArrayList<StrainBubbleDTO> bubbles){
        
        //each genotype will need a unique identifier, intialize it here.
        int genotypeIdentifier = 1;
        
        //create generic parent element that all strains reside in
        JSONObject jso = new JSONObject();
        jso.put("color", "white");
        jso.put("strainName", "");
        jso.put("strainKey", "");
        jso.put("type", "holder");
        //add the strains to the generic holding element (jso) w/ a JSONArray obj
        JSONArray jArr = new JSONArray();
        jso.put("children", jArr);
        //create the strain bubbles, put all the info in for the tooltip
        for(StrainBubbleDTO bubble : bubbles){
            JSONObject childJSO = new JSONObject();
            childJSO.put("color", getColor(bubble));
            childJSO.put("type", "strain");
            childJSO.put("totalMice", bubble.getNumberLiving());
            childJSO.put("greenFemales", bubble.getNumberGreenFemales());
            childJSO.put("yellowFemales", bubble.getNumberYellowFemales());
            childJSO.put("redFemales", bubble.getNumberRedFemales());
            childJSO.put("greenMales", bubble.getNumberGreenMales());
            childJSO.put("yellowMales", bubble.getNumberYellowMales());
            childJSO.put("redMales", bubble.getNumberRedMales());
            childJSO.put("strainKey", bubble.getStrain().getStrainKey().toString());
            childJSO.put("matings", bubble.getNumberMatings());
            childJSO.put("cages", bubble.getNumberCages());
            childJSO.put("strainName", bubble.getStrain().getStrainName());
            
            //create genotype children and add to strain bubble
            JSONArray jArrGenotype = new JSONArray();
            childJSO.put("children", jArrGenotype);
            if(bubble.getGenotypes() != null){
                for(SortedMap genotype : bubble.getGenotypes()){
                    JSONObject genotypeChild = new JSONObject();
                    genotypeChild.put("type", "genotype");
                    genotypeChild.put("genotype", genotype.get("genotype"));
                    genotypeChild.put("genotypeCount", genotype.get("genotypeCount"));
                    genotypeChild.put("value", genotype.get("genotypeCount"));
                    genotypeChild.put("genotypeKey", genotypeIdentifier);
                    genotypeChild.put("strainKey", bubble.getStrain().getStrainKey().toString());
                    //increment genotype identifier so that each is unique
                    genotypeIdentifier++;
                    jArrGenotype.add(genotypeChild);
                }
            }
            //create a 'no genotype bubble to display to users number that don't have a genotype
            if(bubble.getNoGenotype() > 0){
                JSONObject noGenotype = new JSONObject();
                noGenotype.put("type", "genotype");
                noGenotype.put("genotype", "No Genotype");
                noGenotype.put("genotypeCount", bubble.getNoGenotype());
                noGenotype.put("value", bubble.getNoGenotype());
                noGenotype.put("genotypeKey", genotypeIdentifier);
                genotypeIdentifier++;
                noGenotype.put("strainKey", bubble.getStrain().getStrainKey().toString());
                jArrGenotype.add(noGenotype);
            }
            jArr.add(childJSO);
        }
        return jso.toJSONString().replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
    }
    
    private String getColor(StrainBubbleDTO bubble){
        String color = "";
        //totalViable -> green + yellow mice
        Integer totalViableFemales = bubble.getNumberGreenFemales() + bubble.getNumberYellowFemales();
        Integer totalViableMales = bubble.getNumberGreenMales() + bubble.getNumberYellowMales();
        
        //check if green, to be green # green mice > yellow number
        if(bubble.getNumberGreenMales() >= bubble.getStrain().getLineViabilityYellowMinNumMales()
                && bubble.getNumberGreenFemales() >= bubble.getStrain().getLineViabilityYellowMinNumFemales()){
            color = "#008000";
        }
        //check if yellow, to be yellow !green and number of yellow and green mice > red number
        else if(totalViableFemales >= bubble.getStrain().getLineViabilityRedMinNumFemales() &&
                totalViableMales >= bubble.getStrain().getLineViabilityRedMinNumMales()){
            color = "#FFFF00";
        }
        else{
            color = "#CC0000";
        }
        return color;
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
     * @return the collapsed
     */
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }
}
