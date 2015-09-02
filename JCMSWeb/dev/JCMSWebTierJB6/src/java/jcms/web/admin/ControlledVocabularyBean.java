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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dto.ControlledVocabularyGroupDTO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class ControlledVocabularyBean extends AdminBean {
    private String  subViewName = "";
    private String  displayName = "";
    private String  ADMIN_CENTER                   = "CENTER";
    private String  ADMIN_ALLELE                   = "ALLELE"; 
    private String  ADMIN_GENE                     = "GENE";
    private String  ADMIN_GENERAL                  = "GENERAL";
    private String  ADMIN_APPROVEDMATINGSTRAINS    = "APPROVEDMATINGSTRAINS";
    private String  ADMIN_LIFESTATUS               = "LIFESTATUS";
    private String  ADMIN_STRAIN                   = "STRAIN";
    private String  ADMIN_MANAGEPENCONFIGURATION   = "MANAGEPENCONFIGURATION";
    private String  ADMIN_MOUSEUSE                 = "MOUSEUSE";
    private String  ADMIN_PENSTATUS                = "PENSTATUS";
    private String  ADMIN_RETIREPENS               = "RETIREPENS";
    private String  ADMIN_MANAGEPENS               = "MANAGEPENS";
    private String  ADMIN_MANAGEROOM               = "MANAGEROOM";
    private String  ADMIN_JCMSSETUPVARIABLES       = "JCMSSETUPVARIABLES";
    private String  ADMIN_PRESERVATION             = "PRESERVATION";
    private String  ADMIN_SAMPLE                   = "SAMPLES";
    private String  ADMIN_LOCATION                 = "LOCATIONS";
    private String  ADMIN_SAMPLESTATUS             = "SAMPLESTATUS";
    private String  ADMIN_TIMEUNIT                 = "TIMEUNIT";
    private String  ADMIN_ADDUSERWIZARD            = "ADDUSERWIZARD";
    private String  ADMIN_LEVELS                   = "LEVELS";
    private String  ADMIN_USESCHEDULETERMS         = "USESCHEDULETERMS";
    private String  ADMIN_PHENOTYPE                = "PHENOTYPE";
    private String  activeTab                      = "Centers";
    
    public ControlledVocabularyBean() {

    }
    
    public ArrayList<ControlledVocabularyGroupDTO> getControlledVocabularyGroups() {
        ArrayList<ControlledVocabularyGroupDTO> result = (new CVAdministrationDAO()).getControlledVocabularyGroups();
        return result;
    }

    public ArrayList<String> getGroupNames() {
        ArrayList<String> list = new ArrayList<String> ();
        ArrayList<ControlledVocabularyGroupDTO> controlledVocabularyGroups = this.getControlledVocabularyGroups();
        for (ControlledVocabularyGroupDTO dto : controlledVocabularyGroups) {
            list.add(dto.getName());
        }
        
        return list;
    }
    
    /**
     * @return the subViewName
     */
    public String getSubViewName() {
        return subViewName;
    }

    /**
     * @param subViewName the subViewName to set
     */
    public void setSubViewName(String subViewName) {
        this.subViewName = subViewName;
    }

    /**
     * @return the ADMIN_CENTER
     */
    public String getADMIN_CENTER() {
        return ADMIN_CENTER;
    }

    /**
     * @return the ADMIN_ALLELE
     */
    public String getADMIN_ALLELE() {
        return ADMIN_ALLELE;
    }

    /**
     * @return the ADMIN_GENE
     */
    public String getADMIN_GENE() {
        return ADMIN_GENE;
    }

    /**
     * @return the ADMIN_GENERAL
     */
    public String getADMIN_GENERAL() {
        return ADMIN_GENERAL;
    }

    /**
     * @return the ADMIN_APPROVEDMATINGSTRAINS
     */
    public String getADMIN_APPROVEDMATINGSTRAINS() {
        return ADMIN_APPROVEDMATINGSTRAINS;
    }

    /**
     * @return the ADMIN_LIFESTATUS
     */
    public String getADMIN_LIFESTATUS() {
        return ADMIN_LIFESTATUS;
    }

    /**
     * @return the ADMIN_STRAIN
     */
    public String getADMIN_STRAIN() {
        return ADMIN_STRAIN;
    }

    /**
     * @return the ADMIN_MANAGEPENCONFIGURATION
     */
    public String getADMIN_MANAGEPENCONFIGURATION() {
        return ADMIN_MANAGEPENCONFIGURATION;
    }

    /**
     * @return the ADMIN_PENSTATUS
     */
    public String getADMIN_PENSTATUS() {
        return ADMIN_PENSTATUS;
    }

    /**
     * @return the ADMIN_RETIREPENS
     */
    public String getADMIN_RETIREPENS() {
        return ADMIN_RETIREPENS;
    }

    /**
     * @return the ADMIN_MANAGEROOM
     */
    public String getADMIN_MANAGEROOM() {
        return ADMIN_MANAGEROOM;
    }

    /**
     * @return the ADMIN_JCMSSETUPVARIABLES
     */
    public String getADMIN_JCMSSETUPVARIABLES() {
        return ADMIN_JCMSSETUPVARIABLES;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the ADMIN_MANAGEPENS
     */
    public String getADMIN_MANAGEPENS() {
        return ADMIN_MANAGEPENS;
    }

    /**
     * @param ADMIN_MANAGEPENS the ADMIN_MANAGEPENS to set
     */
    public void setADMIN_MANAGEPENS(String ADMIN_MANAGEPENS) {
        this.ADMIN_MANAGEPENS = ADMIN_MANAGEPENS;
    }

    /**
     * @return the ADMIN_MOUSEUSE
     */
    public String getADMIN_MOUSEUSE() {
        return ADMIN_MOUSEUSE;
    }

    /**
     * @return the ADMIN_SAMPLE
     */
    public String getADMIN_SAMPLE() {
        return ADMIN_SAMPLE;
    }

    /**
     * @param ADMIN_SAMPLE the ADMIN_SAMPLE to set
     */
    public void setADMIN_SAMPLE(String ADMIN_SAMPLE) {
        this.ADMIN_SAMPLE = ADMIN_SAMPLE;
    }

    /**
     * @return the ADMIN_LOCATION
     */
    public String getADMIN_LOCATION() {
        return ADMIN_LOCATION;
    }

    /**
     * @param ADMIN_LOCATION the ADMIN_LOCATION to set
     */
    public void setADMIN_LOCATION(String ADMIN_LOCATION) {
        this.ADMIN_LOCATION = ADMIN_LOCATION;
    }

    /**
     * @return the ADMIN_PRESERVATION
     */
    public String getADMIN_PRESERVATION() {
        return ADMIN_PRESERVATION;
    }

    /**
     * @param ADMIN_PRESERVATION the ADMIN_PRESERVATION to set
     */
    public void setADMIN_PRESERVATION(String ADMIN_PRESERVATION) {
        this.ADMIN_PRESERVATION = ADMIN_PRESERVATION;
    }

    /**
     * @return the ADMIN_SAMPLESTATUS
     */
    public String getADMIN_SAMPLESTATUS() {
        return ADMIN_SAMPLESTATUS;
    }

    /**
     * @return the ADMIN_TIMEUNIT
     */
    public String getADMIN_TIMEUNIT() {
        return ADMIN_TIMEUNIT;
    }

    /**
     * @return the ADMIN_ADDUSERWIZARD
     */
    public String getADMIN_ADDUSERWIZARD() {
        return ADMIN_ADDUSERWIZARD;
    }

    /**
     * @param ADMIN_ADDUSERWIZARD the ADMIN_ADDUSERWIZARD to set
     */
    public void setADMIN_ADDUSERWIZARD(String ADMIN_ADDUSERWIZARD) {
        this.ADMIN_ADDUSERWIZARD = ADMIN_ADDUSERWIZARD;
    }

    /**
     * @return the ADMIN_LEVELS
     */
    public String getADMIN_LEVELS() {
        return ADMIN_LEVELS;
    }

    /**
     * @return the activeTab
     */
    public String getActiveTab() {
        return activeTab;
    }

    /**
     * @param activeTab the activeTab to set
     */
    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    /**
     * @return the ADMIN_USESCHEDULETERMS
     */
    public String getADMIN_USESCHEDULETERMS() {
        return ADMIN_USESCHEDULETERMS;
    }
    
    /**
     * @return the ADMIN_PHENOTYPE
     */
    public String getADMIN_PHENOTYPE() {
        return ADMIN_PHENOTYPE;
    }
}
