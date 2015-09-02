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

package jcms.web.experiments;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.ExpDataDAO;
import jcms.integrationtier.dto.ExpDataDescriptorDTO;
import jcms.integrationtier.dto.ExpDataDescriptorFieldDTO;

import org.primefaces.event.ReorderEvent;

/**
 *
 * @author cnh
 */
@ManagedBean
@ViewScoped
public class ExperimentTestTypeBean extends ExperimentsBean implements Serializable {
    private OwnerEntity ownerEntity = new OwnerEntity();
    private List<ExpDataDescriptorDTO> testTypes = null;
    
    // Test Type
    private ExpDataDescriptorDTO testType = null;
    private boolean showTestType = false;
    
    /**
     * Initialize experimental test type bean.
     */
    public ExperimentTestTypeBean() {
        this.setTestTypes(this.findTestTypes());
    }
    
    /**
     * Get a complete list of test types from the database.
     * @return 
     */
    private List<ExpDataDescriptorDTO> findTestTypes() {
        List<ExpDataDescriptorDTO> testTypes = new ExpDataDAO().getAllTestTypes();
        return testTypes;
    }
    
    public String addTestTypeAction() {
        
        try {
            this.setTestType(new ExpDataDescriptorDTO());
            this.setShowTestType(true);
        } catch (Exception e ) {
            // Error processing add data action.  Halt navigation and display
            // appropriate message to user.
            this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Data Action ERROR", null));
            return null;
        }
        
        //Indicate what xhtml form to open by returning its name.
        return "";
    }
    
    public String addFieldAction() {
        this.getTestType().getFields().add(new ExpDataDescriptorFieldDTO());
        return "";
    }
    
    public String cancelTestTypeAction() {
        this.setShowTestType(false);
        this.setTestTypes(this.findTestTypes());
        this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Test type changes discarded.  ", null));    
        
        return "";
    }
    
    public String saveTestTypeAction() {
        if (this.isValid()) {
            try {
                if (this.getTestType().getExpDataDescriptor_key() == 0) {
                    insertTestType();
                }
                this.setShowTestType(false);
                this.setTestTypes(this.findTestTypes());
            } catch (Exception e) {
                this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Test type save failed.  " + e, null));    
                return null;
            }
        } else {
            return null;
        }
        
        return "";
    }
    
    private void insertTestType() {
        try {
            new ExpDataDAO().insertTestType(testType);
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Test type "+ this.getTestType().getTestType() +" added.  Form is ready to add another test type.", null));    
            // initialize another test type
            this.setTestType(new ExpDataDescriptorDTO());
        } catch (SQLException e) {
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add new test type failed. "+ e, null));    
        }
    }
    
    /**
     * Validate test type fields for required values, ranges and formats.  Post
     * the validation once for each field.
     * @return 
     */
    private boolean isValid() {
        boolean isValid = true;
        boolean isValidMin = true;
        boolean isValidMax = true;
        
        boolean showCaptionMsg = false;
        boolean showCaptionTooLongMsg = false;
        boolean showDescriptionTooLongMsg = false;
        boolean showDataFormatMsg = false;
        boolean showMinMsg = false;
        boolean showMaxMsg = false;
        boolean showRangeMsg = false;
        
        if (this.getTestType().getTestType().trim().isEmpty()) {
            isValid = false;
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Test Type is missing. Please enter a test type.", null));        
        } else if (this.isDuplicateTestType(this.getTestType().getTestType())) {
            isValid = false;
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Duplicate test type. Please enter a unique test type name.", null));        
        }
        if (this.getTestType().getTestType().length() > 32) {
            isValid = false;
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Test type name is too long. Please enter a test type name up to 32 characters.", null));        
        }        
        
        for (ExpDataDescriptorFieldDTO dto : this.getTestType().getFields()) {
            if (dto.getCaption().trim().isEmpty()) {
                isValid = false;
                showCaptionMsg = true;
                dto.setBackgroundColor("red");
            } else if (dto.getCaption().length() > 32) {
                isValid = false;
                showCaptionTooLongMsg = true;
                dto.setBackgroundColor("red");
            }

            if (dto.getFieldDescription().length() > 32) {
                isValid = false;
                showDescriptionTooLongMsg = true;
                dto.setBackgroundColor("red");
            }            
            
            if (dto.getDataFormat().trim().isEmpty()) {
                isValid = false;
                showDataFormatMsg = true;
                dto.setBackgroundColor("red");
            } else {
                // Validate min and max data types
                if (!isValidDataType(dto.getDataFormat(), dto.getMinValue())) {
                    isValidMin = false;
                    isValid = false;
                    showMinMsg = true;
                    dto.setBackgroundColor("red");
                }
                if (!isValidDataType(dto.getDataFormat(), dto.getMaxValue())) {
                    isValidMax = false;
                    isValid = false;
                    showMaxMsg = true;
                    dto.setBackgroundColor("red");
                }
                
                // Validate min max range
                // Data type must be integer or decimal
                if (dto.getMinValue() != null && dto.getMaxValue() != null && 
                    !dto.getMinValue().trim().isEmpty() && !dto.getMaxValue().trim().isEmpty() &&
                    isValidMin && isValidMax &&
                    (dto.getDataFormat().equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_INTEGER) 
                        || dto.getDataFormat().equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_DECIMAL)) ) {

                    if (Float.parseFloat(dto.getMaxValue()) < Float.parseFloat(dto.getMinValue()) ) {
                        isValid = false;
                        showRangeMsg = true;
                        dto.setBackgroundColor("red");
                    }
                    isValidMin = true;
                    isValidMax = true;
                }
            }
            
        }
        
        if (showCaptionMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Caption is missing. Please enter a caption.", null));        
        if (showCaptionTooLongMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Caption is too long. Please enter a caption up to 32 characters.", null));        
        if (showDescriptionTooLongMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Description is too long. Please enter a description up to 32 characters.", null));        
        if (showDataFormatMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Format is missing. Please select a format.", null));        
        if (showMinMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Minimum number is invalid. Please enter a valid minimum value.", null));        
        if (showMaxMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Maximum number is invalid. Please enter a valid maximum value.", null));                  
        if (showRangeMsg)
            this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Not a valid range. Max value cannot be less than Min value.  Please enter a valid minimum and maximum range.", null));        

        return isValid;
    }
    
    private boolean isDuplicateTestType(String testType) {
        return (new ExpDataDAO().getTestType(testType).size()) > 0;
    }
    
    private boolean isValidDataType(String format, String value) {
        boolean isValidType = true;
        
        if (value == null || value.trim().length() == 0) {
            isValidType = true;
        } else if (format.equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_DATE)) {
            // no type check required
        } else if (format.equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_DECIMAL)) {
            try {
                Float.parseFloat(value);
            } catch (Exception e) {
                isValidType = false;
                this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid decimal value.  Enter a valid decimal.", null));    
            }
        } else if (format.equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_INTEGER)) {
            if (!this.isInteger(value)) {
                isValidType = false;
                this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid integer value.  Enter a valid integer.", null));    
            }
        } else if (format.equalsIgnoreCase(ExpDataDescriptorFieldDTO.DATA_TYPE_TEXT)) {
            // no type check required
        } 
        
        
        return isValidType;
    }
    
    public void dataFormatChanged(ValueChangeEvent event) {
        String frIndex = (String) event.getComponent().getAttributes().get("fieldRowIndex");
        
        System.out.println("changeFormat" + frIndex);
    }
    
    public void onRowReorder(ReorderEvent event) {
        this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Reorder from " + event.getFromIndex() + " to "+ event.getToIndex(), null));    
        // Get FROM object
        ExpDataDescriptorFieldDTO temp = this.getTestType().getFields().get(event.getFromIndex());
        // Move TO object to FROM location
        this.getTestType().getFields().set(event.getFromIndex(), this.getTestType().getFields().get(event.getToIndex()));
        // Move FROM object to TO location
        this.getTestType().getFields().set(event.getToIndex(), temp);
    }
    
    public void deleteFieldAction(ActionEvent event) {
        Integer fieldIndex = Integer.parseInt(this.getRequestParameter("paramFieldRowIndex"));
        this.getTestType().getFields().remove(this.getTestType().getFields().get(fieldIndex));
        
        
        this.getFacesContext().addMessage("expDataTestTypeMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Test type "+ this.getTestType().getFields().get(fieldIndex) +" deleted.  Select Save to make this change permanent.", null));    
    }
    
    public void deleteTestTypeAction(ActionEvent event) {
        Integer testTypeIndex = 0;
        try {
            testTypeIndex = Integer.parseInt(this.getRequestParameter("paramTestTypeRowIndex"));
            new ExpDataDAO().deleteTestType(this.getTestTypes().get(testTypeIndex).getExpDataDescriptor_key());
            this.getFacesContext().addMessage("expDataTestTypeMessages", 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Test type "+ this.getTestTypes().get(testTypeIndex).getTestType() + " successfully deleted.", null));    
        } catch (SQLException e) {
            this.getFacesContext().addMessage("expDataTestTypeMessages", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete test type "+ this.getTestTypes().get(testTypeIndex).getTestType(), null));    
        }
        this.setTestTypes(this.findTestTypes());
    }
    
    /**
     * Give the user the ability to copy a test type.  Copy adds a new test type 
     * copies the selected test type to the add test type form and appends (COPY)
     * to the test type name.  Duplicate test type name are not allowed.
     * @param event 
     */
    public void copyTestTypeAction(ActionEvent event) {
        Integer testTypeIndex = Integer.parseInt(this.getRequestParameter("paramTestTypeRowIndex"));
        
        ExpDataDescriptorDTO dto = this.getTestTypes().get(testTypeIndex);
        dto.setTestType(this.getTestTypes().get(testTypeIndex).getTestType() + " (COPY)");
        dto.setExpDataDescriptor_key(0);
        this.setTestType(dto);
        
        this.setShowTestType(true);
    }
    
    
    // GETTERS AND SETTERS
    
    /**
     * @return the ownerEntity
     */
    public OwnerEntity getOwnerEntity() {
        return ownerEntity;
    }

    /**
     * @param ownerEntity the ownerEntity to set
     */
    public void setOwnerEntity(OwnerEntity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    /**
     * @return the testType
     */
    public ExpDataDescriptorDTO getTestType() {
        return testType;
    }

    /**
     * @param testType the testType to set
     */
    public void setTestType(ExpDataDescriptorDTO testType) {
        this.testType = testType;
    }

    public List<ExpDataDescriptorDTO> getTestTypes() {
        return testTypes;
    }
    
    /**
     * @param testTypes the testTypes to set
     */
    public void setTestTypes(List<ExpDataDescriptorDTO> testTypes) {
        this.testTypes = testTypes;
    }

    /**
     * @return the showTestType
     */
    public boolean getShowTestType() {
        return showTestType;
    }

    /**
     * @param showTestType the showTestType to set
     */
    public void setShowTestType(boolean showTestType) {
        this.showTestType = showTestType;
    }

}
