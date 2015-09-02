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

package jcms.integrationtier.dao;

import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.DbSetupDTO;

/**
 *
 * @author cnh
 */
public class DbSetupDAO extends MySQLDAO {
    
    public DbSetupDAO() {
    }
    
    private DbSetupDTO selectDbSetupVariable(String dbSetupVariable) {   
        DbSetupDTO dto = null;
        String cmd = "SELECT * FROM DbSetup WHERE MTSVar = '"+ dbSetupVariable +"'";
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createDbSetupDTO(row);
        }
        return dto;
    }

    private DbSetupDTO createDbSetupDTO(SortedMap row) {
        DbSetupDTO dto = new DbSetupDTO();
        dto.setDbSetupKey(myGet("_dbSetup_key", row));
        dto.setMTSVar(myGet("MTSVar", row));
        
        if (dto.getMTSVar().equalsIgnoreCase("MTS_AUTO_LITTER_NUMS")) {
            dto.setMTSValue(myGet("MTSValue", row).equalsIgnoreCase("on") ? "true" : "false");
        } else {
            dto.setMTSValue(myGet("MTSValue", row));
        }
        
        dto.setMTSDescription(myGet("MTSVarDescription", row));
        dto.setVersion(myGet("Version", row));
        dto.setBlnMTSValue((dto.getMTSValue().equalsIgnoreCase("true") ? Boolean.TRUE : (dto.getMTSValue().equalsIgnoreCase("false") ? Boolean.FALSE : null)));
        
        dto.setMTSValueOriginal(dto.getMTSValue());
        
        return dto;
    }
    
    
    public Integer saveDbSetupVariable(DbSetupDTO dto) throws Exception {
        String value = dto.getMTSValue();
        
        if (dto.getMTSVar().equalsIgnoreCase("MTS_AUTO_LITTER_NUMS")) {
            value = (value.equalsIgnoreCase("true") ? "on" : "off");
        }
        
        String cmd = "UPDATE DbSetup SET MTSValue = '"+ value
                  + "'\n , MTSVarDescription = '"+ dto.getMTSDescription() 
                  + "'\n , version = "+ (Integer.parseInt(dto.getVersion()) + 1)
                  + "\n WHERE _dbSetup_key = "+ dto.getDbSetupKey();
        Integer count = 0; 
        count = this.executeJCMSUpdate(cmd);
        return count;
    }

    public Integer saveDbSetupVariables(ArrayList<DbSetupDTO> dtoList) throws Exception {
        Integer count = 0;
        Integer commitCount = 0;
        Boolean saveIt = false;
        for (DbSetupDTO dto : dtoList) {
            if (dto.getMTSValue() == null && dto.getMTSValueOriginal() == null) {
                // do not save
            } else if ((dto.getMTSValue() == null && dto.getMTSValueOriginal() != null) || 
                       (dto.getMTSValue() != null && dto.getMTSValueOriginal() == null)) {
                saveIt = true;
            } else if (!(dto.getMTSValue().equalsIgnoreCase(dto.getMTSValueOriginal()))) {
                saveIt = true;
            }
            
            if (saveIt) {
                commitCount = this.saveDbSetupVariable(dto);
                if (commitCount == 1) {
                    // TODO:  Item saved, update originally so it doesn't get saved again
                    // life cycle seems to be triggering another save operation, not sure.
                    dto.setMTSValueOriginal(dto.getMTSValue());
                    count += 1;
                    commitCount = 0;
                }
            }
            
            saveIt = false;
        }
        return count;
    }
    
    /** CONTAINER VARIABLES **/

    public DbSetupDTO getJCMSCreatePenIncrement() {
        return this.selectDbSetupVariable("JCMS_CREATE_PEN_INCREMENT");
    }    
    public DbSetupDTO getJCMSDefaultContainerStatus() {
        return this.selectDbSetupVariable("JCMS_DEFAULT_CONTAINER_STATUS");
    }    
    public DbSetupDTO getJCMSUsingPenNames() {
        return this.selectDbSetupVariable("JCMS_USING_PEN_NAMES");
    }    
    public DbSetupDTO getMTSDefaultMouseRoom() {
        return this.selectDbSetupVariable("MTS_DEFAULT_MOUSE_ROOM");
    }    
    public DbSetupDTO getMTSMaxMicePerPen() {
        return this.selectDbSetupVariable("MTS_MAX_MICE_PER_PEN");
    }    
    public DbSetupDTO getJCMSWarnDuplicatePenName() {
        return this.selectDbSetupVariable("JCMS_WARN_DUPLICATE_PEN_NAME");
    }
    
    public DbSetupDTO getJCMSPrintExitedMiceOnCageCards() {
        return this.selectDbSetupVariable("JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS");
    }    
    public DbSetupDTO getMTSCageCardDetailNote() {
        return this.selectDbSetupVariable("MTS_CAGE_CARD_DETAIL_NOTE");
    }    
    public DbSetupDTO getMTSPIPhone() {
        return this.selectDbSetupVariable("MTS_PI_PHONE");
    }    
    public DbSetupDTO getMTSPIName() {
        return this.selectDbSetupVariable("MTS_PI_NAME");
    }    
    public DbSetupDTO getMTSRelaxedPenNums() {
        return this.selectDbSetupVariable("MTS_RELAXED_PEN_NUMS");
    }    
    
    /** GENOTYPE VARIABLES **/
    
    public DbSetupDTO getJCMSAddGenotypeIncrement() {
        return this.selectDbSetupVariable("JCMS_ADD_GENOTYPE_INCREMENT");
    }    
    public DbSetupDTO getJCMSAlleleConfHigh() {
        return this.selectDbSetupVariable("JCMS_ALLELE_CONF_HIGH");
    }    
    public DbSetupDTO getJCMSAlleleConfLow() {
        return this.selectDbSetupVariable("JCMS_ALLELE_CONF_LOW");
    }    
    public DbSetupDTO getJCMSAlleleGeneSeparators() {
        return this.selectDbSetupVariable("JCMS_ALLELE_GENE_SEPARATORS");
    }    
    public DbSetupDTO getJCMSAllowUserDefinedGenerations() {
        return this.selectDbSetupVariable("JCMS_ALLOW_USERDEFINED_GENERATIONS");
    }    
    public DbSetupDTO getJCMSAllowUserDefinedStrains() {
        return this.selectDbSetupVariable("JCMS_ALLOW_USERDEFINED_STRAINS");
    }    

    /** LITTER VARIABLES **/
    
    public DbSetupDTO getJCMSAddLitterIncrement() {
        return this.selectDbSetupVariable("JCMS_ADD_LITTER_INCREMENT");
    }    
    public DbSetupDTO getJCMSAddLitterPupsIncrement() {
        return this.selectDbSetupVariable("JCMS_ADD_LITTER_PUPS_INCREMENT");
    }    
    public DbSetupDTO getJCMSLoopLitterNumbers() {
        return this.selectDbSetupVariable("JCMS_LOOP_LITTER_NUMBERS");
    }    
    public DbSetupDTO getMTSAutoLitterNums() {
        return this.selectDbSetupVariable("MTS_AUTO_LITTER_NUMS");
    }    
    public DbSetupDTO getMTSNumAutoLitterNums() {
        return this.selectDbSetupVariable("MTS_NUM_AUTO_LITTER_NUMS");
    }    
    
    /** MATING VARIABLES **/
    
    public DbSetupDTO getJCMSActivateMatingsIncrement() {
        return this.selectDbSetupVariable("JCMS_ACTIVATE_MATINGS_INCREMENT");
    }    
    public DbSetupDTO getJCMSEnforceApprovedMatings() {
        return this.selectDbSetupVariable("JCMS_ENFORCE_APPROVED_MATINGS");
    }    
    
    /** MOUSE VARIABLES **/
    
    public DbSetupDTO getJCMSExtWeanTime() {
        return this.selectDbSetupVariable("JCMS_EXT_WEAN_TIME");
    }    
    public DbSetupDTO getJCMSStandardWeanTime() {
        return this.selectDbSetupVariable("JCMS_STANDARD_WEAN_TIME");
    } 
    
    public DbSetupDTO getJCMSGestationPeriod () {
        return this.selectDbSetupVariable("JCMS_GESTATION_PERIOD");
    }
    
    public DbSetupDTO getJCMSStrainNameFirst() {
        return this.selectDbSetupVariable("JCMS_STRAINNAME_FIRST");
    }    
    public DbSetupDTO getMTSMouseIDPrefix() {
        return this.selectDbSetupVariable("MTS_MOUSE_ID_PREFIX");
    }    
        
    /** QUERY VARIABLES **/
       
    
    
    /** RIGHTMOST INCREMENT VARIABLES **/
    public DbSetupDTO getJCMSGenerationIncrementRightmost() {
        return this.selectDbSetupVariable("JCMS_GENERATION_INCREMENT_RIGHTMOST");
    }    
    public DbSetupDTO getJCMSLitterIDIncrementRightmost() {
        return this.selectDbSetupVariable("JCMS_LITTERID_INCREMENT_RIGHTMOST");
    }    
    public DbSetupDTO getJCMSMouseIDIncrementRightmost() {
        return this.selectDbSetupVariable("JCMS_MOUSEID_INCREMENT_RIGHTMOST");
    }    
    public DbSetupDTO getJCMSPenNamesIncrementRightmost() {
        return this.selectDbSetupVariable("JCMS_PEN_NAMES_INCREMENT_RIGHTMOST");
    }
    
    /** MISCELLANEOUS VARIABLE **/
    public DbSetupDTO getJCMSEnforcePasswordChange() {
        return this.selectDbSetupVariable("JCMS_ENFORCE_PASSWORD_CHANGE");        
    }
    public DbSetupDTO getJCMSPasswordChangePeriod() {
        return this.selectDbSetupVariable("JCMS_PASSWORD_CHANGE_PERIOD");        
    }
    public DbSetupDTO getJCMSWebDateFormat() {
        return this.selectDbSetupVariable("JCMS_WEB_DATE_FORMAT");        
    }
}
