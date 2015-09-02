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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import jcms.integrationtier.dao.SampleDAO;
import jcms.integrationtier.dto.PreservationDetailDTO;
import jcms.integrationtier.dto.SampleClassDTO;
import jcms.integrationtier.dto.PreservationTypeDTO;
import jcms.integrationtier.dto.PreservationMethodDTO;


/**
 *
 * @author mkamato
 */

@ManagedBean
@SessionScoped
public class AdminPreservationBean extends AdminBean{
    
    private SampleDAO dao = new SampleDAO();
    private ArrayList<SelectItem> sampleClasses = new ArrayList<SelectItem>();
    private String sampleClass = "";
    private ArrayList<SelectItem> presTypes = new ArrayList<SelectItem>();
    private String presType = "";
    private ArrayList<SelectItem> presMethods = new ArrayList<SelectItem>();
    private String presMethod = "";
    private ArrayList<SelectItem> presDetails = new ArrayList<SelectItem>();
    private String presDetail = "";
    
    private String addPresType = "";
    private String addPresMethod = "";
    private String addPresDetail = "";
    
    public AdminPreservationBean(){
        ArrayList<SampleClassDTO> classes = dao.getSampleClasses();
        sampleClasses.add(new SelectItem("", ""));
        for(SampleClassDTO dto : classes){
            sampleClasses.add(new SelectItem(dto.getSampleClass_key(), dto.getSampleClass()));
        }
        presType = "";
        presMethod = "";
        presDetail = "";
    }
    
    public void sampleClassChangeListener(){
        presTypes = new ArrayList<SelectItem>();
        if(!sampleClass.equals("")){
            ArrayList<PreservationTypeDTO> types = dao.getPreservationTypes(getSampleClass());
            for(PreservationTypeDTO dto : types){
                presTypes.add(new SelectItem(dto.getPreservationType_key(), dto.getPreservationType()));
            }
        }
        presMethods = new ArrayList<SelectItem>();
        presDetails = new ArrayList<SelectItem>();
        presType = "";
        presMethod = "";
        presDetail = "";            
    }
    
    public void preservationTypeSelectListener(){
        presMethods = new ArrayList<SelectItem>();
        ArrayList<PreservationMethodDTO> methods = dao.getPreservationMethods(getPresType());
        for(PreservationMethodDTO dto : methods){
            presMethods.add(new SelectItem(dto.getPreservationMethod_key(), dto.getPreservationMethod()));
        }
        presMethod = "";
        presDetail = "";
        presDetails = new ArrayList<SelectItem>();
    }
    
    public void preservationMethodSelectListener(){
        presDetails = new ArrayList<SelectItem>();
        ArrayList<PreservationDetailDTO> details = dao.getPreservationDetails(getPresMethod());
        for(PreservationDetailDTO dto : details){
            presDetails.add(new SelectItem(dto.getPreservationDetail_key(), dto.getPreservationDetail()));
        }        
        presDetail = "";
    }
    
    public void addPreservationType(){
        boolean flag = false;
        for(SelectItem si : presTypes){
            if(si.getLabel().equalsIgnoreCase(addPresType)){
                this.addToMessageQueue("A preservation type with this name already exists", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(sampleClass.equals("")){
            this.addToMessageQueue("You must select a sample class to add a preservation type", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(addPresType.equals("")){
            this.addToMessageQueue("Preservation type is required, please provide a preservation type.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                PreservationTypeDTO dto = new PreservationTypeDTO();
                dto.setPreservationType(addPresType);
                dto.setSampleClass_key(sampleClass);
                dto.setPreservationType_key(dao.insertPreservationType(dto));
                presTypes.add(new SelectItem(dto.getPreservationType_key(), dto.getPreservationType()));
                this.addToMessageQueue("Preservation type successfully added.", FacesMessage.SEVERITY_INFO);
                addPresType = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void addPreservationMethod(){
        boolean flag = false;
        for(SelectItem si : presMethods){
            if(si.getLabel().equalsIgnoreCase(addPresMethod)){
                this.addToMessageQueue("A preservation method with this name already exists", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(presType.equals("")){
            this.addToMessageQueue("You must select a preservation type to add a preservation method", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(addPresMethod.equals("")){
            this.addToMessageQueue("Preservation method is required, please provide a preservation method.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                PreservationMethodDTO dto = new PreservationMethodDTO();
                dto.setPreservationMethod(addPresMethod);
                dto.setPreservationType_key(presType);
                dto.setPreservationMethod_key(dao.insertPreservationMethod(dto));
                presMethods.add(new SelectItem(dto.getPreservationMethod_key(), dto.getPreservationMethod()));
                this.addToMessageQueue("Preservation method successfully added.", FacesMessage.SEVERITY_INFO);
                addPresMethod = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    public void addPreservationDetail(){
        boolean flag = false;
        for(SelectItem si : presDetails){
            if(si.getLabel().equalsIgnoreCase(addPresDetail)){
                this.addToMessageQueue("A preservation detail with this name already exists", FacesMessage.SEVERITY_ERROR);
                flag = true;
            }
        }
        if(presMethod.equals("")){
            this.addToMessageQueue("You must select a preservation method to add a preservation detail", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(addPresDetail.equals("")){
            this.addToMessageQueue("Preservation detail is required, please provide a preservation detail.", FacesMessage.SEVERITY_ERROR);
            flag = true;
        }
        if(!flag){
            try{
                PreservationDetailDTO dto = new PreservationDetailDTO();
                dto.setPreservationDetail(addPresDetail);
                dto.setPreservationMethod_key(presMethod);
                dto.setPreservationDetail_key(dao.insertPreservationDetail(dto));
                presDetails.add(new SelectItem(dto.getPreservationDetail_key(), dto.getPreservationDetail()));
                this.addToMessageQueue("Preservation detail successfully added.", FacesMessage.SEVERITY_INFO);
                addPresDetail = "";
            }
            catch(Exception e){
                this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    /**
     * @return the presTypes
     */
    public ArrayList<SelectItem> getPresTypes() {
        return presTypes;
    }

    /**
     * @param presTypes the presTypes to set
     */
    public void setPresTypes(ArrayList<SelectItem> presTypes) {
        this.presTypes = presTypes;
    }

    /**
     * @return the presMethods
     */
    public ArrayList<SelectItem> getPresMethods() {
        return presMethods;
    }

    /**
     * @param presMethods the presMethods to set
     */
    public void setPresMethods(ArrayList<SelectItem> presMethods) {
        this.presMethods = presMethods;
    }

    /**
     * @return the presDetails
     */
    public ArrayList<SelectItem> getPresDetails() {
        return presDetails;
    }

    /**
     * @param presDetails the presDetails to set
     */
    public void setPresDetails(ArrayList<SelectItem> presDetails) {
        this.presDetails = presDetails;
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
     * @return the presType
     */
    public String getPresType() {
        return presType;
    }

    /**
     * @param presType the presType to set
     */
    public void setPresType(String presType) {
        this.presType = presType;
    }

    /**
     * @return the presMethod
     */
    public String getPresMethod() {
        return presMethod;
    }

    /**
     * @param presMethod the presMethod to set
     */
    public void setPresMethod(String presMethod) {
        this.presMethod = presMethod;
    }

    /**
     * @return the presDetail
     */
    public String getPresDetail() {
        return presDetail;
    }

    /**
     * @param presDetail the presDetail to set
     */
    public void setPresDetail(String presDetail) {
        this.presDetail = presDetail;
    }

    /**
     * @return the addPresType
     */
    public String getAddPresType() {
        return addPresType;
    }

    /**
     * @param addPresType the addPresType to set
     */
    public void setAddPresType(String addPresType) {
        this.addPresType = addPresType;
    }

    /**
     * @return the addPresMethod
     */
    public String getAddPresMethod() {
        return addPresMethod;
    }

    /**
     * @param addPresMethod the addPresMethod to set
     */
    public void setAddPresMethod(String addPresMethod) {
        this.addPresMethod = addPresMethod;
    }

    /**
     * @return the addPresDetail
     */
    public String getAddPresDetail() {
        return addPresDetail;
    }

    /**
     * @param addPresDetail the addPresDetail to set
     */
    public void setAddPresDetail(String addPresDetail) {
        this.addPresDetail = addPresDetail;
    }
}
