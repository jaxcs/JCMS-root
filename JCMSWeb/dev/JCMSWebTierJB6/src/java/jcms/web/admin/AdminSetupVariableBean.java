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

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import jcms.integrationtier.dao.DbSetupDAO;
import jcms.integrationtier.dto.AdminRoomDTO;
import jcms.integrationtier.dto.DbSetupDTO;

/**
 *
 * @author cnh
 */
@ManagedBean
@SessionScoped
public class AdminSetupVariableBean extends AdminBean {
    private DbSetupDAO dao = null;
    
    private DbSetupDTO JCMSActivateMatingsIncrement = null;
    private DbSetupDTO JCMSAddGenotypeIncrement = null;
    private DbSetupDTO JCMSAddLitterIncrement = null;
    private DbSetupDTO JCMSAddLitterPupsIncrement = null;
    private DbSetupDTO JCMSAlleleConfHigh = null;
    private DbSetupDTO JCMSAlleleConfLow = null;
    private DbSetupDTO JCMSAlleleGeneSeparators = null;
    private DbSetupDTO JCMSAllowUserDefinedGenerations = null;
    private DbSetupDTO JCMSAllowUserDefinedStrains = null;
    private DbSetupDTO JCMSCreatePenIncrement = null;
    private DbSetupDTO JCMSDefaultContainerStatus = null;
    private DbSetupDTO JCMSEnforceApprovedMatings = null;
    private DbSetupDTO JCMSEnforcePasswordChange = null;
    private DbSetupDTO JCMSExtWeanTime = null;
    private DbSetupDTO JCMSLoopLitterNumbers = null;
    private DbSetupDTO JCMSPasswordChangePeriod = null;
    private DbSetupDTO JCMSPrintExitedMiceOnCageCards = null;
    private DbSetupDTO JCMSStandardWeanTime = null;
    private DbSetupDTO JCMSStrainNameFirst = null;
    private DbSetupDTO JCMSUsingPenNames = null;
    private DbSetupDTO JCMSWarnDuplicatePenName = null;
    
    private DbSetupDTO MTSAutoLitterNums = null;
    private DbSetupDTO MTSCageCardDetailNote = null;
    private DbSetupDTO MTSDefaultMouseRoom = null;
    private DbSetupDTO MTSMaxMicePerPen = null;
    private DbSetupDTO MTSMouseIDPrefix = null;
    private DbSetupDTO MTSNumAutoLitterNums = null;
    private DbSetupDTO MTSPIName = null;
    private DbSetupDTO MTSPIPhone = null;
    private DbSetupDTO MTSRelaxedPenNums = null;
    
    private DbSetupDTO JCMSWebDateFormat = null;
    
    private ArrayList<DbSetupDTO> setupVariableList = new ArrayList<DbSetupDTO> ();
    private String firstDateUnit = "";
    private String secondDateUnit = "";
    private String thirdDateUnit = "";
    private String separatorCharacter = "";
    
    public AdminSetupVariableBean() {
        dao = new DbSetupDAO();
        
        JCMSActivateMatingsIncrement = dao.getJCMSActivateMatingsIncrement();
        JCMSAddGenotypeIncrement = dao.getJCMSAddGenotypeIncrement();
        JCMSAddLitterIncrement = dao.getJCMSAddLitterIncrement();
        JCMSAddLitterPupsIncrement = dao.getJCMSAddLitterPupsIncrement();
        JCMSAlleleConfHigh = dao.getJCMSAlleleConfHigh();
        JCMSAlleleConfLow = dao.getJCMSAlleleConfLow();
        JCMSAlleleGeneSeparators = dao.getJCMSAlleleGeneSeparators();
        JCMSAllowUserDefinedGenerations = dao.getJCMSAllowUserDefinedGenerations();
        JCMSAllowUserDefinedStrains = dao.getJCMSAllowUserDefinedStrains();
        JCMSCreatePenIncrement = dao.getJCMSCreatePenIncrement();
        JCMSDefaultContainerStatus = dao.getJCMSDefaultContainerStatus();
        JCMSEnforceApprovedMatings = dao.getJCMSEnforceApprovedMatings();
        JCMSEnforcePasswordChange = dao.getJCMSEnforcePasswordChange();
        JCMSExtWeanTime = dao.getJCMSExtWeanTime();
        JCMSLoopLitterNumbers = dao.getJCMSLoopLitterNumbers();
        JCMSPasswordChangePeriod = dao.getJCMSPasswordChangePeriod();
        JCMSPrintExitedMiceOnCageCards = dao.getJCMSPrintExitedMiceOnCageCards();
        JCMSStandardWeanTime = dao.getJCMSStandardWeanTime();
        JCMSStrainNameFirst = dao.getJCMSStrainNameFirst();
        JCMSUsingPenNames = dao.getJCMSUsingPenNames();
        JCMSWarnDuplicatePenName = dao.getJCMSWarnDuplicatePenName();
    
        MTSAutoLitterNums = dao.getMTSAutoLitterNums();
        MTSCageCardDetailNote = dao.getMTSCageCardDetailNote();
        MTSDefaultMouseRoom = dao.getMTSDefaultMouseRoom();
        MTSMaxMicePerPen = dao.getMTSMaxMicePerPen();
        MTSMouseIDPrefix = dao.getMTSMouseIDPrefix();
        MTSNumAutoLitterNums = dao.getMTSNumAutoLitterNums();
        MTSPIName = dao.getMTSPIName();
        MTSPIPhone = dao.getMTSPIPhone();
        MTSRelaxedPenNums = dao.getMTSRelaxedPenNums();
        JCMSWebDateFormat = dao.getJCMSWebDateFormat();
        parseDateFormat();
    }
    
    private void loadVariableArray() {
        setupVariableList.clear();
        setupVariableList.add(JCMSActivateMatingsIncrement);
        setupVariableList.add(JCMSAddGenotypeIncrement);
        setupVariableList.add(JCMSAddLitterIncrement);
        setupVariableList.add(JCMSAddLitterPupsIncrement);
        setupVariableList.add(JCMSAlleleConfHigh);
        setupVariableList.add(JCMSAlleleConfLow);
        setupVariableList.add(JCMSAlleleGeneSeparators);
        setupVariableList.add(JCMSAllowUserDefinedGenerations);
        setupVariableList.add(JCMSAllowUserDefinedStrains);
        setupVariableList.add(JCMSCreatePenIncrement);
        setupVariableList.add(JCMSDefaultContainerStatus);
        setupVariableList.add(JCMSEnforceApprovedMatings);
        setupVariableList.add(JCMSEnforcePasswordChange);
        setupVariableList.add(JCMSExtWeanTime);
        setupVariableList.add(JCMSLoopLitterNumbers);
        setupVariableList.add(JCMSPasswordChangePeriod);
        setupVariableList.add(JCMSPrintExitedMiceOnCageCards);
        setupVariableList.add(JCMSStandardWeanTime);
        setupVariableList.add(JCMSStrainNameFirst);
        setupVariableList.add(JCMSUsingPenNames);
        setupVariableList.add(getJCMSWarnDuplicatePenName());
        
        setupVariableList.add(getMTSAutoLitterNums());
        setupVariableList.add(MTSCageCardDetailNote);
        setupVariableList.add(MTSDefaultMouseRoom);
        setupVariableList.add(MTSMaxMicePerPen);
        setupVariableList.add(MTSMouseIDPrefix);
        setupVariableList.add(MTSNumAutoLitterNums);
        setupVariableList.add(MTSPIName);
        setupVariableList.add(MTSPIPhone);
        setupVariableList.add(MTSRelaxedPenNums);
        setupVariableList.add(JCMSWebDateFormat);
    }
    
    public void saveAction() {
        
        if(validateDateFormat()){
            this.addToMessageQueue("Provided date format could not be saved, "
                    + "please make sure that a year, month, and day value are provided only once.", FacesMessage.SEVERITY_ERROR);
        }
        else{
            String strMessage ;
            this.loadVariableArray();
            try{
                if (dao.saveDbSetupVariables(setupVariableList) > 0) {
                    strMessage = "JCMS setup variables changes saved." ; 
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_INFO);
                } 
                else {
                    strMessage = "No changes detected, no setup variable changed." ;
                    addToMessageQueue(strMessage, FacesMessage.SEVERITY_INFO);
                }
                this.getLogger().logInfo(this.formatLogMessage("Save DbSetup Variables", strMessage));
            }
            catch(Exception e){
                this.addToMessageQueue("Save Failed: " + e, FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    
    /*
     * IF return false, no problem. Else return true.
     */
    private boolean validateDateFormat(){
        //none of the date units 
        if(validateDateFormatHelper(firstDateUnit, secondDateUnit)){
            return true;
        }
        else if(validateDateFormatHelper(firstDateUnit, thirdDateUnit)){
            return true;
        }
        else if(validateDateFormatHelper(secondDateUnit, thirdDateUnit)){
            return true;
        }
        this.getJCMSWebDateFormat().setMTSValue(firstDateUnit + separatorCharacter + secondDateUnit + separatorCharacter + thirdDateUnit);
        return false;
    }
    
    //return true -> there is a problem, false there isn't a problem
    private boolean validateDateFormatHelper(String unit1, String unit2){
        if(unit1.equals(unit2)){
            return true;
        }
        if(unit1.equals("yy") && unit2.equals("yyyy")){
            return true;
        }
        if(unit1.equals("yyyy") && unit2.equals("yy")){
            return true;
        }
        return false;
    }
    
    private void parseDateFormat(){
        String dateFormat = getJCMSWebDateFormat().getMTSValue();
        //build the date
        boolean unitOneDone = false;
        boolean unitTwoDone = false;
        String currentUnit = "";
        /*
         * parsing something like this: yyyy/MM/dd need to find first
         * second and third date unit and also the separator character
         */
        for(char c : dateFormat.toCharArray()){
            if(Character.isLetter(c)){
                currentUnit = currentUnit + c;
            }
            else{
                //set the separator character
                this.separatorCharacter = new Character(c).toString();
                //first unit isn't done, then first unit is the string currently built
                if(!unitOneDone){
                    this.firstDateUnit = currentUnit;
                    currentUnit = "";
                    unitOneDone = true;
                }
                else if(!unitTwoDone){
                    this.secondDateUnit = currentUnit;
                    currentUnit = "";
                    unitTwoDone = true;
                }
            }
        }
        //last unit after for loop ends
        this.thirdDateUnit = currentUnit;
        System.out.println(firstDateUnit);
        System.out.println(secondDateUnit);
        System.out.println(thirdDateUnit);
        System.out.println(separatorCharacter);
    }
    
    /**
     * @return the JCMSActivateMatingsIncrement
     */
    public DbSetupDTO getJCMSActivateMatingsIncrement() {
        return JCMSActivateMatingsIncrement;
    }

    /**
     * @param JCMSActivateMatingsIncrement the JCMSActivateMatingsIncrement to set
     */
    public void setJCMSActivateMatingsIncrement(DbSetupDTO JCMSActivateMatingsIncrement) {
        this.JCMSActivateMatingsIncrement = JCMSActivateMatingsIncrement;
    }

    /**
     * @return the JCMSAddGenotypeIncrement
     */
    public DbSetupDTO getJCMSAddGenotypeIncrement() {
        return JCMSAddGenotypeIncrement;
    }

    /**
     * @param JCMSAddGenotypeIncrement the JCMSAddGenotypeIncrement to set
     */
    public void setJCMSAddGenotypeIncrement(DbSetupDTO JCMSAddGenotypeIncrement) {
        this.JCMSAddGenotypeIncrement = JCMSAddGenotypeIncrement;
    }

    /**
     * @return the JCMSAddLitterIncrement
     */
    public DbSetupDTO getJCMSAddLitterIncrement() {
        return JCMSAddLitterIncrement;
    }

    /**
     * @param JCMSAddLitterIncrement the JCMSAddLitterIncrement to set
     */
    public void setJCMSAddLitterIncrement(DbSetupDTO JCMSAddLitterIncrement) {
        this.JCMSAddLitterIncrement = JCMSAddLitterIncrement;
    }

    /**
     * @return the JCMSAddLitterPupsIncrement
     */
    public DbSetupDTO getJCMSAddLitterPupsIncrement() {
        return JCMSAddLitterPupsIncrement;
    }

    /**
     * @param JCMSAddLitterPupsIncrement the JCMSAddLitterPupsIncrement to set
     */
    public void setJCMSAddLitterPupsIncrement(DbSetupDTO JCMSAddLitterPupsIncrement) {
        this.JCMSAddLitterPupsIncrement = JCMSAddLitterPupsIncrement;
    }

    /**
     * @return the JCMSAlleleConfHigh
     */
    public DbSetupDTO getJCMSAlleleConfHigh() {
        return JCMSAlleleConfHigh;
    }

    /**
     * @param JCMSAlleleConfHigh the JCMSAlleleConfHigh to set
     */
    public void setJCMSAlleleConfHigh(DbSetupDTO JCMSAlleleConfHigh) {
        this.JCMSAlleleConfHigh = JCMSAlleleConfHigh;
    }

    /**
     * @return the JCMSAlleleConfLow
     */
    public DbSetupDTO getJCMSAlleleConfLow() {
        return JCMSAlleleConfLow;
    }

    /**
     * @param JCMSAlleleConfLow the JCMSAlleleConfLow to set
     */
    public void setJCMSAlleleConfLow(DbSetupDTO JCMSAlleleConfLow) {
        this.JCMSAlleleConfLow = JCMSAlleleConfLow;
    }

    /**
     * @return the JCMSAlleleGeneSeparators
     */
    public DbSetupDTO getJCMSAlleleGeneSeparators() {
        return JCMSAlleleGeneSeparators;
    }

    /**
     * @param JCMSAlleleGeneSeparators the JCMSAlleleGeneSeparators to set
     */
    public void setJCMSAlleleGeneSeparators(DbSetupDTO JCMSAlleleGeneSeparators) {
        this.JCMSAlleleGeneSeparators = JCMSAlleleGeneSeparators;
    }

    /**
     * @return the JCMSAllowUserDefinedGenerations
     */
    public DbSetupDTO getJCMSAllowUserDefinedGenerations() {
        return JCMSAllowUserDefinedGenerations;
    }

    /**
     * @param JCMSAllowUserDefinedGenerations the JCMSAllowUserDefinedGenerations to set
     */
    public void setJCMSAllowUserDefinedGenerations(DbSetupDTO JCMSAllowUserDefinedGenerations) {
        this.JCMSAllowUserDefinedGenerations = JCMSAllowUserDefinedGenerations;
    }

    /**
     * @return the JCMSAllowUserDefinedStrains
     */
    public DbSetupDTO getJCMSAllowUserDefinedStrains() {
        return JCMSAllowUserDefinedStrains;
    }

    /**
     * @param JCMSAllowUserDefinedStrains the JCMSAllowUserDefinedStrains to set
     */
    public void setJCMSAllowUserDefinedStrains(DbSetupDTO JCMSAllowUserDefinedStrains) {
        this.JCMSAllowUserDefinedStrains = JCMSAllowUserDefinedStrains;
    }

    /**
     * @return the JCMSCreatePenIncrement
     */
    public DbSetupDTO getJCMSCreatePenIncrement() {
        return JCMSCreatePenIncrement;
    }

    /**
     * @param JCMSCreatePenIncrement the JCMSCreatePenIncrement to set
     */
    public void setJCMSCreatePenIncrement(DbSetupDTO JCMSCreatePenIncrement) {
        this.JCMSCreatePenIncrement = JCMSCreatePenIncrement;
    }

    /**
     * @return the JCMSDefaultContainerStatus
     */
    public DbSetupDTO getJCMSDefaultContainerStatus() {
        return JCMSDefaultContainerStatus;
    }

    /**
     * @param JCMSDefaultContainerStatus the JCMSDefaultContainerStatus to set
     */
    public void setJCMSDefaultContainerStatus(DbSetupDTO JCMSDefaultContainerStatus) {
        this.JCMSDefaultContainerStatus = JCMSDefaultContainerStatus;
    }

    /**
     * @return the JCMSEnforceApprovedMatings
     */
    public DbSetupDTO getJCMSEnforceApprovedMatings() {
        return JCMSEnforceApprovedMatings;
    }

    /**
     * @param JCMSEnforceApprovedMatings the JCMSEnforceApprovedMatings to set
     */
    public void setJCMSEnforceApprovedMatings(DbSetupDTO JCMSEnforceApprovedMatings) {
        this.JCMSEnforceApprovedMatings = JCMSEnforceApprovedMatings;
    }

    /**
     * @return the JCMSExtWeanTime
     */
    public DbSetupDTO getJCMSExtWeanTime() {
        return JCMSExtWeanTime;
    }

    /**
     * @param JCMSExtWeanTime the JCMSExtWeanTime to set
     */
    public void setJCMSExtWeanTime(DbSetupDTO JCMSExtWeanTime) {
        this.JCMSExtWeanTime = JCMSExtWeanTime;
    }

    /**
     * @return the JCMSLoopLitterNumbers
     */
    public DbSetupDTO getJCMSLoopLitterNumbers() {
        return JCMSLoopLitterNumbers;
    }

    /**
     * @param JCMSLoopLitterNumbers the JCMSLoopLitterNumbers to set
     */
    public void setJCMSLoopLitterNumbers(DbSetupDTO JCMSLoopLitterNumbers) {
        this.JCMSLoopLitterNumbers = JCMSLoopLitterNumbers;
    }

    /**
     * @return the JCMSPrintExitedMiceOnCageCards
     */
    public DbSetupDTO getJCMSPrintExitedMiceOnCageCards() {
        return JCMSPrintExitedMiceOnCageCards;
    }

    /**
     * @param JCMSPrintExitedMiceOnCageCards the JCMSPrintExitedMiceOnCageCards to set
     */
    public void setJCMSPrintExitedMiceOnCageCards(DbSetupDTO JCMSPrintExitedMiceOnCageCards) {
        this.JCMSPrintExitedMiceOnCageCards = JCMSPrintExitedMiceOnCageCards;
    }

    /**
     * @return the JCMSStandardWeanTime
     */
    public DbSetupDTO getJCMSStandardWeanTime() {
        return JCMSStandardWeanTime;
    }

    /**
     * @param JCMSStandardWeanTime the JCMSStandardWeanTime to set
     */
    public void setJCMSStandardWeanTime(DbSetupDTO JCMSStandardWeanTime) {
        this.JCMSStandardWeanTime = JCMSStandardWeanTime;
    }

    /**
     * @return the JCMSStrainNameFirst
     */
    public DbSetupDTO getJCMSStrainNameFirst() {
        return JCMSStrainNameFirst;
    }

    /**
     * @param JCMSStrainNameFirst the JCMSStrainNameFirst to set
     */
    public void setJCMSStrainNameFirst(DbSetupDTO JCMSStrainNameFirst) {
        this.JCMSStrainNameFirst = JCMSStrainNameFirst;
    }

    /**
     * @return the JCMSUsingPenNames
     */
    public DbSetupDTO getJCMSUsingPenNames() {
        return JCMSUsingPenNames;
    }

    /**
     * @param JCMSUsingPenNames the JCMSUsingPenNames to set
     */
    public void setJCMSUsingPenNames(DbSetupDTO JCMSUsingPenNames) {
        this.JCMSUsingPenNames = JCMSUsingPenNames;
    }

    /**
     * @return the MTSCageCardDetailNote
     */
    public DbSetupDTO getMTSCageCardDetailNote() {
        return MTSCageCardDetailNote;
    }

    /**
     * @param MTSCageCardDetailNote the MTSCageCardDetailNote to set
     */
    public void setMTSCageCardDetailNote(DbSetupDTO MTSCageCardDetailNote) {
        this.MTSCageCardDetailNote = MTSCageCardDetailNote;
    }

    /**
     * @return the MTSDefaultMouseRoom
     */
    public DbSetupDTO getMTSDefaultMouseRoom() {
        return MTSDefaultMouseRoom;
    }

    /**
     * @param MTSDefaultMouseRoom the MTSDefaultMouseRoom to set
     */
    public void setMTSDefaultMouseRoom(DbSetupDTO MTSDefaultMouseRoom) {
        this.MTSDefaultMouseRoom = MTSDefaultMouseRoom;
    }

    /**
     * @return the MTSMaxMicePerPen
     */
    public DbSetupDTO getMTSMaxMicePerPen() {
        return MTSMaxMicePerPen;
    }

    /**
     * @param MTSMaxMicePerPen the MTSMaxMicePerPen to set
     */
    public void setMTSMaxMicePerPen(DbSetupDTO MTSMaxMicePerPen) {
        this.MTSMaxMicePerPen = MTSMaxMicePerPen;
    }

    /**
     * @return the MTSMouseIDPrefix
     */
    public DbSetupDTO getMTSMouseIDPrefix() {
        return MTSMouseIDPrefix;
    }

    /**
     * @param MTSMouseIDPrefix the MTSMouseIDPrefix to set
     */
    public void setMTSMouseIDPrefix(DbSetupDTO MTSMouseIDPrefix) {
        this.MTSMouseIDPrefix = MTSMouseIDPrefix;
    }

    /**
     * @return the MTSNumAutoLitterNums
     */
    public DbSetupDTO getMTSNumAutoLitterNums() {
        return MTSNumAutoLitterNums;
    }

    /**
     * @param MTSNumAutoLitterNums the MTSNumAutoLitterNums to set
     */
    public void setMTSNumAutoLitterNums(DbSetupDTO MTSNumAutoLitterNums) {
        this.MTSNumAutoLitterNums = MTSNumAutoLitterNums;
    }

    /**
     * @return the MTSPIName
     */
    public DbSetupDTO getMTSPIName() {
        return MTSPIName;
    }

    /**
     * @param MTSPIName the MTSPIName to set
     */
    public void setMTSPIName(DbSetupDTO MTSPIName) {
        this.MTSPIName = MTSPIName;
    }

    /**
     * @return the MTSPIPhone
     */
    public DbSetupDTO getMTSPIPhone() {
        return MTSPIPhone;
    }

    /**
     * @param MTSPIPhone the MTSPIPhone to set
     */
    public void setMTSPIPhone(DbSetupDTO MTSPIPhone) {
        this.MTSPIPhone = MTSPIPhone;
    }

    /**
     * @return the MTSRelaxedPenNums
     */
    public DbSetupDTO getMTSRelaxedPenNums() {
        return MTSRelaxedPenNums;
    }

    /**
     * @param MTSRelaxedPenNums the MTSRelaxedPenNums to set
     */
    public void setMTSRelaxedPenNums(DbSetupDTO MTSRelaxedPenNums) {
        this.MTSRelaxedPenNums = MTSRelaxedPenNums;
    }

    /**
     * @return the MTSAutoLitterNums
     */
    public DbSetupDTO getMTSAutoLitterNums() {
        return MTSAutoLitterNums;
    }

    /**
     * @param MTSAutoLitterNums the MTSAutoLitterNums to set
     */
    public void setMTSAutoLitterNums(DbSetupDTO MTSAutoLitterNums) {
        this.MTSAutoLitterNums = MTSAutoLitterNums;
    }

    /**
     * @return the JCMSEnforcePasswordChange
     */
    public DbSetupDTO getJCMSEnforcePasswordChange() {
        return JCMSEnforcePasswordChange;
    }

    /**
     * @param JCMSEnforcePasswordChange the JCMSEnforcePasswordChange to set
     */
    public void setJCMSEnforcePasswordChange(DbSetupDTO JCMSEnforcePasswordChange) {
        this.JCMSEnforcePasswordChange = JCMSEnforcePasswordChange;
    }

    /**
     * @return the JCMSPasswordChangePeriod
     */
    public DbSetupDTO getJCMSPasswordChangePeriod() {
        return JCMSPasswordChangePeriod;
    }

    /**
     * @param JCMSPasswordChangePeriod the JCMSPasswordChangePeriod to set
     */
    public void setJCMSPasswordChangePeriod(DbSetupDTO JCMSPasswordChangePeriod) {
        this.JCMSPasswordChangePeriod = JCMSPasswordChangePeriod;
    }

    /**
     * @return the firstDateUnit
     */
    public String getFirstDateUnit() {
        return firstDateUnit;
    }

    /**
     * @param firstDateUnit the firstDateUnit to set
     */
    public void setFirstDateUnit(String firstDateUnit) {
        this.firstDateUnit = firstDateUnit;
    }

    /**
     * @return the secondDateUnit
     */
    public String getSecondDateUnit() {
        return secondDateUnit;
    }

    /**
     * @param secondDateUnit the secondDateUnit to set
     */
    public void setSecondDateUnit(String secondDateUnit) {
        this.secondDateUnit = secondDateUnit;
    }

    /**
     * @return the thirdDateUnit
     */
    public String getThirdDateUnit() {
        return thirdDateUnit;
    }

    /**
     * @param thirdDateUnit the thirdDateUnit to set
     */
    public void setThirdDateUnit(String thirdDateUnit) {
        this.thirdDateUnit = thirdDateUnit;
    }

    /**
     * @return the separatorCharacter
     */
    public String getSeparatorCharacter() {
        return separatorCharacter;
    }

    /**
     * @param separatorCharacter the separatorCharacter to set
     */
    public void setSeparatorCharacter(String separatorCharacter) {
        this.separatorCharacter = separatorCharacter;
    }

    /**
     * @return the JCMSWebDateFormat
     */
    public DbSetupDTO getJCMSWebDateFormat() {
        return JCMSWebDateFormat;
    }

    /**
     * @param JCMSWebDateFormat the JCMSWebDateFormat to set
     */
    public void setJCMSWebDateFormat(DbSetupDTO JCMSWebDateFormat) {
        this.JCMSWebDateFormat = JCMSWebDateFormat;
    }

    /**
     * @return the JCMSWarnDuplicatePenName
     */
    public DbSetupDTO getJCMSWarnDuplicatePenName() {
        return JCMSWarnDuplicatePenName;
    }

    /**
     * @param JCMSWarnDuplicatePenName the JCMSWarnDuplicatePenName to set
     */
    public void setJCMSWarnDuplicatePenName(DbSetupDTO JCMSWarnDuplicatePenName) {
        this.JCMSWarnDuplicatePenName = JCMSWarnDuplicatePenName;
    }


}
