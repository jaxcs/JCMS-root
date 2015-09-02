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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.AdminAlleleDTO;
import jcms.integrationtier.dto.AdminCenterDTO;
import jcms.integrationtier.dto.AdminContainerStatusDTO;
import jcms.integrationtier.dto.AdminDbInfoDTO;
import jcms.integrationtier.dto.AdminGeneClassDTO;
import jcms.integrationtier.dto.AdminGeneDTO;
import jcms.integrationtier.dto.AdminGeneralDTO;
import jcms.integrationtier.dto.AdminGenerationDTO;
import jcms.integrationtier.dto.AdminHealthLevelHistoryDTO;
import jcms.integrationtier.dto.AdminLifeStatusDTO;
import jcms.integrationtier.dto.AdminRoomDTO;
import jcms.integrationtier.dto.AdminStrainDTO;
import jcms.integrationtier.dto.AdminStrainStatusDTO;
import jcms.integrationtier.dto.AdminStrainTypeDTO;
import jcms.integrationtier.dto.AdminWorkgroupDTO;
import jcms.integrationtier.dto.ContainerHistoryDTO;
import jcms.integrationtier.dto.ControlledVocabularyDTO;
import jcms.integrationtier.dto.ControlledVocabularyGroupDTO;
import jcms.integrationtier.dto.MouseUseDTO;
import jcms.integrationtier.dto.QueryTypeDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author cnh
 */
public class CVAdministrationDAO extends MySQLDAO {
    
    public CVAdministrationDAO () {
    }
    
    public ArrayList<AdminCenterDTO> getCenters(ArrayList<AdminWorkgroupDTO> list){
        ArrayList<AdminCenterDTO> dtoList = new AdministrationDAO().getCenters(list);
        return dtoList;
    }

    /** CONTROLLED VOCABULARY AND CV GROUPS **/
    
    public ArrayList<ControlledVocabularyGroupDTO> getControlledVocabularyGroups(){
        ArrayList<ControlledVocabularyGroupDTO> dtoList = new ArrayList<ControlledVocabularyGroupDTO> ();
        ControlledVocabularyGroupDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_ControlledVocabularyGroup ORDER BY sortOrder ");

        for (SortedMap row : result.getRows()) {
            dto = new ControlledVocabularyGroupDTO(
                myGet("_controlledVocabularyGroup_key", row),
                myGet("name", row),
                myGet("sortOrder", row));
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ArrayList<ControlledVocabularyDTO> getControlledVocabularyByGroup(String controlledVocabularyGroupKey){
        ArrayList<ControlledVocabularyDTO> dtoList = new ArrayList<ControlledVocabularyDTO> ();
        ControlledVocabularyDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM ControlledVocabulary WHERE _controlledVocabularyGroup_key = "+ controlledVocabularyGroupKey +" ORDER BY sortOrder ");

        for (SortedMap row : result.getRows()) {
            dto = this.createControlledVocabularyDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ControlledVocabularyDTO getControlledVocabularyByKey(String controlledVocabularyKey){
        ControlledVocabularyDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM ControlledVocabulary WHERE _controlledVocabulary_key = "+ controlledVocabularyKey +" ORDER BY sortOrder ");

        for (SortedMap row : result.getRows()) {
            dto = this.createControlledVocabularyDTO(row);
            break;
        }
        return dto;
    }
    
    /** GENERAL CV ADMINISTRATION **/
    
    public Integer saveGeneralVocabulary(AdminGeneralDTO generalDTO, ControlledVocabularyDTO cvDTO) throws SQLException {
        Integer cnt = 0;
        if (generalDTO.isInsert()) {
            cnt = this.insertGeneralVocabulary(generalDTO, cvDTO);
        } else {
            cnt = this.updateGeneralVocabulary(generalDTO, cvDTO);
        }
        return cnt;
    }
    
    private Integer insertGeneralVocabulary(AdminGeneralDTO generalDTO, ControlledVocabularyDTO cvDTO) throws SQLException {
        Integer version = (Integer.parseInt(generalDTO.getVersion())) + 1;
        String columnTwoName = (cvDTO.getColumnTwoName().length() > 0 ? ","+ cvDTO.getColumnTwoName() : "");
        String columnTwoValue = (cvDTO.getColumnTwoName().length() > 0 ? ","+ varcharParser(generalDTO.getColumnTwoValue()) : "");
        String cmd = "INSERT INTO " + cvDTO.getTableName() 
            +"\n (" + cvDTO.getColumnOneName() + columnTwoName +", version) "
            +"\n VALUES ("+ varcharParser(generalDTO.getColumnOneValue()) + columnTwoValue +","+ version.toString() +")" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private Integer updateGeneralVocabulary(AdminGeneralDTO generalDTO, ControlledVocabularyDTO cvDTO) throws SQLException {
        Integer version = (Integer.parseInt(generalDTO.getVersion())) + 1;
        String columnTwoAssignment = (cvDTO.getColumnTwoName().length() > 0 ? ", "+ cvDTO.getColumnTwoName() +" = " + varcharParser(generalDTO.getColumnTwoValue()) : "");
        String cmd = "UPDATE "+ cvDTO.getTableName() +" SET " 
            + cvDTO.getColumnOneName() +" = " + varcharParser(generalDTO.getColumnOneValue())
            + columnTwoAssignment 
            + ", Version = " + version.toString()
            +"\n WHERE "+ cvDTO.getPkColumnName() +" = "+ generalDTO.getPrimaryKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteGeneralVocabulary(AdminGeneralDTO generalDTO, ControlledVocabularyDTO cvDTO) throws SQLException {
        String cmd = "DELETE FROM "+ cvDTO.getTableName() +" WHERE "+ cvDTO.getPkColumnName() +" = "+ generalDTO.getPrimaryKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private ControlledVocabularyDTO createControlledVocabularyDTO (SortedMap row) {
        ControlledVocabularyDTO dto = null;
            dto = new ControlledVocabularyDTO(
                myGet("_controlledVocabulary_key", row),
                myGet("_controlledVocabularyGroup_key", row),
                myGet("tableName", row),
                myGet("pkColumnName", row),
                myGet("columnOneName", row),
                myGet("columnTwoName", row),
                myGet("displayName", row),
                myGet("displayColumnOneName", row),
                myGet("displayColumnTwoName", row),
                myGet("subViewName", row),
                myGet("isUserAdministered", row),
                myGet("sortOrder", row),
                myGet("createdBy", row),
                myGet("dateCreated", row),
                myGet("modifiedBy", row),
                myGet("dateModified", row),
                myGet("version", row));
        return dto;
    }

    public ArrayList<AdminGeneralDTO> getGeneralVocabulary(ControlledVocabularyDTO cvDTO) {
        ArrayList<AdminGeneralDTO> dtoList = new ArrayList<AdminGeneralDTO> ();
        AdminGeneralDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM " + cvDTO.getTableName() + " ORDER BY "+ cvDTO.getColumnOneName());

        for (SortedMap row : result.getRows()) {
            dto = this.createGeneralDTO(row, cvDTO);
            dtoList.add(dto);
        }
        
        return dtoList;
    }
    
    private AdminGeneralDTO createGeneralDTO(SortedMap row, ControlledVocabularyDTO cvDTO) {
        AdminGeneralDTO dto = new AdminGeneralDTO(
                cvDTO.getTableName(),
                myGet(cvDTO.getPkColumnName(), row),
                myGet(cvDTO.getColumnOneName(), row),
                myGet(cvDTO.getColumnTwoName(), row),
                myGet("version", row));
        return dto;
    }
    
    /** LIFE STATUS **/

    public ArrayList<AdminLifeStatusDTO> getLifeStatusVocabulary() {
        ArrayList<AdminLifeStatusDTO> dtoList = new ArrayList<AdminLifeStatusDTO> ();
        AdminLifeStatusDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM LifeStatus ORDER BY lifeStatus ");
        for (SortedMap row : result.getRows()) {
            dto = this.createLifeStatusDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminLifeStatusDTO createLifeStatusDTO(SortedMap row) {
        AdminLifeStatusDTO dto = new AdminLifeStatusDTO();
        dto.setLifeStatusKey(myGet("_lifeStatus_key", row));
        dto.setLifeStatus(myGet("lifeStatus", row));
        dto.setExitStatus(Boolean.parseBoolean(myGet("exitStatus", row)));
        dto.setDescription(myGet("description", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    public Integer saveLifeStatusVocabulary(AdminLifeStatusDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertLifeStatusVocabulary(dto);
        } else {
            cnt = this.updateLifeStatusVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertLifeStatusVocabulary(AdminLifeStatusDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer intExitStatus = (dto.getExitStatus() ? -1 : 0);
        String cmd = "INSERT INTO LifeStatus " 
            +"\n (lifeStatus, exitStatus, description, version) "
            +"\n VALUES ("+ varcharParser(dto.getLifeStatus()) +
                        ", "+ intExitStatus +
                        ", "+ varcharParser(dto.getDescription()) +
                        ", "+ version.toString() +")" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private Integer updateLifeStatusVocabulary(AdminLifeStatusDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer intExitStatus = (dto.getExitStatus() ? -1 : 0);
        String cmd = "UPDATE LifeStatus SET " 
            + " lifeStatus = " + varcharParser(dto.getLifeStatus())
            + ", exitStatus = " + intExitStatus
            + ", description = " + varcharParser(dto.getDescription())
            + ", version = " + version.toString()
            +"\n WHERE _lifeStatus_key = "+ dto.getLifeStatusKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteLifeStatusVocabulary(AdminLifeStatusDTO dto) throws SQLException {
        String cmd = "DELETE FROM LifeStatus WHERE _lifeStatus_key = "+ dto.getLifeStatusKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    /** CONTAINER STATUS **/

    public ArrayList<AdminContainerStatusDTO> getContainerStatusVocabulary() {
        ArrayList<AdminContainerStatusDTO> dtoList = new ArrayList<AdminContainerStatusDTO> ();
        AdminContainerStatusDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_ContainerStatus ORDER BY containerStatus ");
        for (SortedMap row : result.getRows()) {
            dto = this.createContainerStatusDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminContainerStatusDTO createContainerStatusDTO(SortedMap row) {
        AdminContainerStatusDTO dto = new AdminContainerStatusDTO();
        dto.setContainerStatusKey(myGet("_containerStatus_key", row));
        dto.setContainerStatus(myGet("containerStatus", row));
        dto.setBillable(Boolean.parseBoolean(myGet("billable", row)));
        dto.setVersion(myGet("version", row));
        return dto;
    }
    
    public Integer saveContainerStatusVocabulary(AdminContainerStatusDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertContainerStatusVocabulary(dto);
        } else {
            cnt = this.updateContainerStatusVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertContainerStatusVocabulary(AdminContainerStatusDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer intBillable = (dto.getBillable() ? -1 : 0);
        String cmd = "INSERT INTO cv_ContainerStatus " 
            +"\n (containerStatus, billable, version) "
            +"\n VALUES ("+ varcharParser(dto.getContainerStatus()) +
                        ", "+ intBillable +
                        ", "+ version.toString() +")" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private Integer updateContainerStatusVocabulary(AdminContainerStatusDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer intExitStatus = (dto.getBillable() ? -1 : 0);
        String cmd = "UPDATE cv_ContainerStatus SET " 
            + " containerStatus = " + varcharParser(dto.getContainerStatus())
            + ", billable = " + intExitStatus
            + ", version = " + version.toString()
            +"\n WHERE _containerStatus_key = "+ dto.getContainerStatusKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteContainerStatusVocabulary(AdminContainerStatusDTO dto) throws SQLException {
        String cmd = "DELETE FROM cv_ContainerStatus WHERE _containerStatus_key = "+ dto.getContainerStatusKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public String getContainerStatusKeyFromTerm(String term) {
        String cmd = "SELECT _containerStatus_key FROM cv_ContainerStatus WHERE containerStatus ='" + term + "';";
        String key = "0";
        Result result = executeJCMSQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                key = myGet("_containerStatus_key", row);
            }
        }
        return key;
    }
    
    /** GENE **/

    public ArrayList<AdminGeneDTO> getGeneVocabulary() {
        ArrayList<AdminGeneDTO> dtoList = new ArrayList<AdminGeneDTO> ();
        AdminGeneDTO dto = null;
        String cmd = " SELECT g.*, "
                   + "     (SELECT COUNT(_genotype_key) FROM Genotype WHERE _gene_key = g._gene_key) as GenotypeCount, "
                   + "     (SELECT COUNT(_allele_key) FROM Allele WHERE _gene_key = g._gene_key) as AlleleCount "
                   + " FROM Gene g ORDER BY g.labSymbol " ;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createGeneDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminGeneDTO createGeneDTO(SortedMap row) {
        AdminGeneDTO dto = new AdminGeneDTO();
        dto.setGeneKey(myGet("_gene_key", row));
        dto.setGeneSymbol(myGet("geneSymbol", row));
        dto.setLabSymbol(myGet("labSymbol", row));
        dto.setChromosome(myGet("chromosome", row));
        dto.setcM(myGet("cM", row));
        dto.setGeneClass(myGet("geneClass", row));
        dto.setMegabase(myGet("megabase", row));
        dto.setComment(myGet("comment", row));
        dto.setVersion(myGet("version", row));
        dto.setGenotypeCount(myGet("GenotypeCount", row));
        dto.setAlleleCount(myGet("AlleleCount", row));
        return dto;
    }
    
    public Integer saveGeneVocabulary(AdminGeneDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertGeneVocabulary(dto);
        } else {
            cnt = this.updateGeneVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertGeneVocabulary(AdminGeneDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Double dblCM = ((dto.getcM().length() > 0) ? Double.parseDouble(dto.getcM()) : null);
        Float fltMegabase = ((dto.getMegabase().length() > 0) ? Float.parseFloat(dto.getMegabase()) : null);
        String cmd = "INSERT INTO Gene " 
            +"\n (geneSymbol, labSymbol, geneClass, chromosome, cM, megabase, comment, version) "
            +"\n VALUES ("+ varcharParser(dto.getGeneSymbol()) +
                        ", "+ varcharParser(dto.getLabSymbol()) +
                        ", "+ varcharParser(dto.getGeneClass()) +
                        ", "+ varcharParser(dto.getChromosome()) +
                        ", "+ dblCM +
                        ", "+ fltMegabase +
                        ", "+ varcharParser(dto.getComment()) +
                        ", "+ version.toString() +")" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private Integer updateGeneVocabulary(AdminGeneDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Double dblCM = ((dto.getcM().length() > 0) ? Double.parseDouble(dto.getcM()) : null);
        Float fltMegabase = ((dto.getMegabase().length() > 0) ? Float.parseFloat(dto.getMegabase()) : null);
        String cmd = "UPDATE Gene SET " 
            + " geneSymbol = " + varcharParser(dto.getGeneSymbol())
            + ", labSymbol = " + varcharParser(dto.getLabSymbol())
            + ", geneClass = " + varcharParser(dto.getGeneClass())
            + ", chromosome = " + varcharParser(dto.getChromosome())
            + ", cM = " + dblCM
            + ", megabase = " + fltMegabase
            + ", comment = " + varcharParser(dto.getComment())
            + ", version = " + version.toString()
            +"\n WHERE _gene_key = "+ dto.getGeneKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteGeneVocabulary(AdminGeneDTO dto) throws SQLException {
        String cmd = "DELETE FROM Gene WHERE _gene_key = "+ dto.getGeneKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    /** ALLELE **/

    public ArrayList<AdminAlleleDTO> getAlleleVocabularyByGeneKey(String geneKey) {
        ArrayList<AdminAlleleDTO> dtoList = new ArrayList<AdminAlleleDTO> ();
        AdminAlleleDTO dto = null;
        Result result = executeJCMSQuery(""
            + " SELECT DISTINCT Allele.* "
            + " FROM Allele, Gene " 
            + " WHERE (Allele._gene_key = "+ geneKey +") "
            + "    OR (Gene.geneClass = Allele.genericAlleleGeneClass " 
            + "        AND Gene._gene_key = "+ geneKey +") "
            + " ORDER BY allele ");
        if (result != null)
            for (SortedMap row : result.getRows()) {
                dto = this.createAlleleDTO(row);
                dtoList.add(dto);
            }
        return dtoList;
    }
    
    public ArrayList<AdminAlleleDTO> getAlleleVocabularyByGeneClass(String geneClass) {
        ArrayList<AdminAlleleDTO> dtoList = new ArrayList<AdminAlleleDTO> ();
        AdminAlleleDTO dto = null;
        Result result = executeJCMSQuery(""
            + " SELECT DISTINCT Allele.* "
            + " FROM Allele " 
            + " WHERE (Allele.genericAlleleGeneClass = '"+ geneClass +"') "
            + " ORDER BY allele ");
        if (result != null)
            for (SortedMap row : result.getRows()) {
                dto = this.createAlleleDTO(row);
                dtoList.add(dto);
            }
        return dtoList;
    }
    
    private AdminAlleleDTO createAlleleDTO(SortedMap row) {
        AdminAlleleDTO dto = new AdminAlleleDTO();
        dto.setAlleleKey(myGet("_allele_key", row));
        dto.setAllele(myGet("allele", row));
        dto.setGeneKey(myGet("_gene_key", row));
        dto.setGenericAlleleGeneClass(myGet("genericAlleleGeneClass", row));
        dto.setVersion(myGet("version", row));
        return dto;
    }
    
    public Integer saveAlleleVocabulary(AdminAlleleDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertAlleleVocabulary(dto);
        } else {
            cnt = this.updateAlleleVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertAlleleVocabulary(AdminAlleleDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String geneKey = ((dto.getGeneKey().length() > 0) ? dto.getGeneKey().toString() : "null");
        String cmd = "INSERT INTO Allele " 
            +"\n (allele, _gene_key, genericAlleleGeneClass, version) "
            +"\n VALUES ("+ varcharParser(dto.getAllele()) +
                        ", "+ geneKey +
                        ", "+ varcharParser(dto.getGenericAlleleGeneClass()) +
                        ", "+ version.toString() +")" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    private Integer updateAlleleVocabulary(AdminAlleleDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String geneKey = ((dto.getGeneKey().length() > 0) ? dto.getGeneKey().toString() : "null");
        String cmd = "UPDATE Allele SET " 
            + " allele = " + varcharParser(dto.getAllele())
            + ", _gene_key = " + geneKey
            + ", genericAlleleGeneClass = " + varcharParser(dto.getGenericAlleleGeneClass())
            + ", version = " + version.toString()
            +"\n WHERE _gene_key = "+ dto.getAlleleKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteAlleleVocabulary(AdminAlleleDTO dto) throws SQLException {
        String cmd = "DELETE FROM Allele WHERE _allele_key = "+ dto.getAlleleKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    /** GENE CLASS **/
    
    public ArrayList<AdminGeneClassDTO> getGeneClassVocabulary() {
        ArrayList<AdminGeneClassDTO> dtoList = new ArrayList<AdminGeneClassDTO> ();
        AdminGeneClassDTO dto = null;
        Result result = executeJCMSQuery(""
            + " SELECT * "
            + " FROM cv_GeneClass " 
            + " ORDER BY GeneClass ");
        for (SortedMap row : result.getRows()) {
            dto = this.createGeneClassDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
        
    }
    
    private AdminGeneClassDTO createGeneClassDTO(SortedMap row) {
        AdminGeneClassDTO dto = new AdminGeneClassDTO();
        dto.setGeneClassKey(myGet("_geneClass_key", row));
        dto.setGeneClass(myGet("GeneClass", row));
        dto.setDescription(myGet("Description", row));
        dto.setVersion(myGet("version", row));
        return dto;
    }
    
    /** STRAIN STATUS **/

    public ArrayList<AdminStrainStatusDTO> getStrainStatusVocabulary() {
        ArrayList<AdminStrainStatusDTO> dtoList = new ArrayList<AdminStrainStatusDTO> ();
        AdminStrainStatusDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_StrainStatus ORDER BY strainStatus ");
        for (SortedMap row : result.getRows()) {
            dto = this.createStrainStatusDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminStrainStatusDTO createStrainStatusDTO(SortedMap row) {
        AdminStrainStatusDTO dto = new AdminStrainStatusDTO();
        dto.setStrainStatusKey(myGet("_strainStatus_key", row));
        dto.setStrainStatus(myGet("strainStatus", row));
        dto.setDescription(myGet("description", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setCreatedBy(myGet("createdBy", row));
        dto.setDateCreated(myGet("dateCreated", row));
        dto.setModifiedBy(myGet("modifiedBy", row));
        dto.setDateModified(myGet("dateModified", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    /** GENERATION **/

    public ArrayList<AdminGenerationDTO> getGenerationVocabulary() {
        ArrayList<AdminGenerationDTO> dtoList = new ArrayList<AdminGenerationDTO> ();
        AdminGenerationDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_Generation ORDER BY generation ");
        for (SortedMap row : result.getRows()) {
            dto = this.createGenerationDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminGenerationDTO createGenerationDTO(SortedMap row) {
        AdminGenerationDTO dto = new AdminGenerationDTO();
        dto.setGenerationKey(myGet("_generation_key", row));
        dto.setGeneration(myGet("generation", row));
//        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
//        dto.setCreatedBy(myGet("createdBy", row));
//        dto.setDateCreated(myGet("dateCreated", row));
//        dto.setModifiedBy(myGet("modifiedBy", row));
//        dto.setDateModified(myGet("dateModified", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    /** STRAIN TYPE **/

    public ArrayList<AdminStrainTypeDTO> getStrainTypeVocabulary() {
        ArrayList<AdminStrainTypeDTO> dtoList = new ArrayList<AdminStrainTypeDTO> ();
        AdminStrainTypeDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_StrainType ORDER BY strainType ");
        for (SortedMap row : result.getRows()) {
            dto = this.createStrainTypeDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminStrainTypeDTO createStrainTypeDTO(SortedMap row) {
        AdminStrainTypeDTO dto = new AdminStrainTypeDTO();
        dto.setStrainTypeKey(myGet("_strainType_key", row));
        dto.setStrainType(myGet("strainType", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    /** STRAIN **/

    /**
     * This method checks user preferences to sort strains by 
     * strainName or jrNum
     * @param userEntity
     * @return 
     */
    private String getStrainOrderByClause(UserEntity userEntity) {
        if (new StrainDAO(userEntity).checkIfStrainFirst()) 
            return " ORDER BY strainName"; 
        else 
            return " ORDER BY jrNum";
    }
    
    public ArrayList<AdminStrainDTO> getStrainVocabulary() {
        ArrayList<AdminStrainDTO> dtoList = new ArrayList<AdminStrainDTO> ();
        AdminStrainDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM Strain ORDER BY strainName ");
        for (SortedMap row : result.getRows()) {
            dto = this.createStrainDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminStrainDTO> getStrains(AdminStrainDTO searchCriteria) {
        ArrayList<AdminStrainDTO> list = new ArrayList<AdminStrainDTO> ();
        String cmd = "SELECT * FROM Strain WHERE _strain_key > 0 " ; 

        if (searchCriteria.getStrainName().length() > 0) {
            cmd += " AND strainName LIKE '%"+ searchCriteria.getStrainName() +"%' ";
        }
        
        // Customized for Strain searching by Strain Name and Stock Number range
        // otherwise autocomplete search uses JRNum dto field
        if (searchCriteria.getJrNumFrom().length() > 0) {
            cmd += " AND jrNum >= '"+ searchCriteria.getJrNumFrom() +"%' ";
        }
        if (searchCriteria.getJrNumTo().length() > 0) {
            cmd += " AND jrNum <= '"+ searchCriteria.getJrNumTo() +"%' ";
        }
        
        cmd += this.getStrainOrderByClause(searchCriteria.getUser()); ;
        
        Result result = executeJCMSQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                list.add(this.createStrainDTO(row));
            }
        }
        return list;
    }
    
    public String getJRNumber(String strainName) {
        String rtnValue = "";
        Result result = executeJCMSQuery("SELECT jrNum FROM Strain WHERE TRIM(strainName) = "+ varcharParser(strainName.trim()) );
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                rtnValue = this.myGet("jrNum", row);
                break;
            }
        }
        return rtnValue;
    }
    
    public String getStrainName(String jrNumber) {
        String rtnValue = "";
        Result result = executeJCMSQuery("SELECT strainName FROM Strain WHERE jrNum = "+ varcharParser(jrNumber) );
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                rtnValue = this.myGet("strainName", row);
                break;
            }
        }
        return rtnValue;
    }
    
    public AdminStrainDTO getStrain(String strainName) {
        AdminStrainDTO dto = new AdminStrainDTO();
        Result result = executeJCMSQuery("SELECT * FROM Strain WHERE TRIM(strainName) = "+ varcharParser(strainName.trim()) );
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createStrainDTO(row);
                break;
            }
        }
        return dto;
    }
    
    public Boolean isDuplicate(String strainKey, String strainName) {
        Result result = null;
        String cmd = "";
        
        // New strain, no strain names should be found
        if (strainKey.equalsIgnoreCase("0")) {
            cmd = "SELECT * FROM Strain WHERE TRIM(strainName) =  "+ varcharParser(strainName.trim()) ;
            result = executeJCMSQuery(cmd);
            if (result != null) {
                if (result.getRowCount() > 0) {
                    return true;
                }
            }
        }
        
        cmd = "SELECT * FROM Strain WHERE _strain_key = "+ strainKey +" AND TRIM(strainName) =  "+ varcharParser(strainName.trim()) ;
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() == 1) {
                // Same strain
                return false;
            }
        }
        
        cmd = "SELECT * FROM Strain WHERE TRIM(strainName) = "+ varcharParser(strainName.trim()) ;
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() > 0) {
                return true;
            }
        }
            
        return false;
    }
    
    private AdminStrainDTO createStrainDTO(SortedMap row) {
        AdminStrainDTO dto = new AdminStrainDTO();
        dto.setStrainKey(myGet("_strain_key", row));
        dto.setStrainName(myGet("strainName", row));
        dto.setNickName(myGet("nickName", row));
        dto.setFormalName(myGet("formalName", row));
        dto.setStrainStatus(myGet("strainStatus", row));
        dto.setTagMin(myGet("tagMin", row));
        dto.setTagMax(myGet("tagMax", row));
        dto.setLastTag(myGet("lastTag", row));
        dto.setJrNum(myGet("jrNum", row));
        dto.setFeNumEmbryos(myGet("feNumEmbryos", row));
        dto.setFeMaxGen(myGet("feMaxGen", row));
        dto.setFsNumMales(myGet("fsNumMales", row));
        dto.setFsMaxGen(myGet("fsMaxGen", row));
        dto.setFoNumFemales(myGet("foNumFemales", row));
        dto.setFoMaxGen(myGet("foMaxGen", row));
        dto.setCardColor(myGet("cardColor", row));
        dto.setStrainType(myGet("strainType", row));
        dto.setComment(myGet("comment", row));
        dto.setLineViabilityYellowMinNumMales(myGet("lineViabilityYellowMinNumMales", row));
        dto.setLineViabilityYellowMinNumFemales(myGet("lineViabilityYellowMinNumFemales", row));
        dto.setLineViabilityYellowMaxAgeMales(myGet("lineViabilityYellowMaxAgeMales", row));
        dto.setLineViabilityYellowMaxAgeFemales(myGet("lineViabilityYellowMaxAgeFemales", row));
        dto.setLineViabilityRedMinNumMales(myGet("lineViabilityRedMinNumMales", row));
        dto.setLineViabilityRedMinNumFemales(myGet("lineViabilityRedMinNumFemales", row));
        dto.setLineViabilityRedMaxAgeMales(myGet("lineViabilityRedMaxAgeMales", row));
        dto.setLineViabilityRedMaxAgeFemales(myGet("lineViabilityRedMaxAgeFemales", row));
        dto.setSection_(myGet("section_", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }

    public Integer saveStrainVocabulary(AdminStrainDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertStrainVocabulary(dto);
        } else {
            cnt = this.updateStrainVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertStrainVocabulary(AdminStrainDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "INSERT INTO Strain " 
            + "\n (strainName, nickname, formalName, strainStatus, tagMin, tagMax, lastTag, "
            + "\n  jrNum, feNumEmbryos, feMaxGen, fsNumMales, fsMaxGen, foNumFemales, foMaxGen, "
            + "\n  cardColor, strainType, comment, "
            + "\n  lineViabilityYellowMinNumMales, lineViabilityYellowMinNumFemales, lineViabilityYellowMaxAgeMales, lineViabilityYellowMaxAgeFemales, "
            + "\n  lineViabilityRedMinNumMales, lineViabilityRedMinNumFemales, lineViabilityRedMaxAgeMales, lineViabilityRedMaxAgeFemales, "
            + "\n  version, section_, isActive ) "
            + "\n VALUES ("+ varcharParser(dto.getStrainName().trim())
                        + ", "+ varcharParser(dto.getNickName().trim())
                        + ", "+ varcharParser(dto.getFormalName().trim())
                        + ", "+ varcharParser(dto.getStrainStatus())
                        + ", "+ varcharParser(dto.getTagMin())
                        + ", "+ varcharParser(dto.getTagMax())
                        + ", "+ varcharParser(dto.getLastTag())
                        + ", "+ myGetNumber(dto.getJrNum())
                        + ", "+ myGetNumber(dto.getFeNumEmbryos())  
                        + ", "+ varcharParser(dto.getFeMaxGen())
                        + ", "+ myGetNumber(dto.getFsNumMales())  
                        + ", "+ varcharParser(dto.getFsMaxGen())
                        + ", "+ myGetNumber(dto.getFoNumFemales())  
                        + ", "+ varcharParser(dto.getFoMaxGen())
                        + ", "+ varcharParser(dto.getCardColor())
                        + ", "+ varcharParser(dto.getStrainType())
                        + ", "+ varcharParser(dto.getComment())
                        + ", "+ myGetNumber(dto.getLineViabilityYellowMinNumMales()) 
                        + ", "+ myGetNumber(dto.getLineViabilityYellowMinNumFemales())  
                        + ", "+ myGetNumber(dto.getLineViabilityYellowMaxAgeMales())  
                        + ", "+ myGetNumber(dto.getLineViabilityYellowMaxAgeFemales()) 
                        + ", "+ myGetNumber(dto.getLineViabilityRedMinNumMales()) 
                        + ", "+ myGetNumber(dto.getLineViabilityRedMinNumFemales()) 
                        + ", "+ myGetNumber(dto.getLineViabilityRedMaxAgeMales()) 
                        + ", "+ myGetNumber(dto.getLineViabilityRedMaxAgeFemales()) 
                        + ", "+ version.toString()
                        + ", "+ varcharParser(dto.getSection_())
                        + ", "+ (dto.getIsActive() ? -1 : 0) +" )" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }

    private Integer updateStrainVocabulary(AdminStrainDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE Strain SET " 
            + " strainName = " + varcharParser(dto.getStrainName().trim())
            + ", nickname = " + varcharParser(dto.getNickName().trim())
            + ", formalName = " + varcharParser(dto.getFormalName().trim())
            + ", strainStatus = " + varcharParser(dto.getStrainStatus())
            + ", tagMin = " + varcharParser(dto.getTagMin())
            + ", tagMax = " + varcharParser(dto.getTagMax())
            + ", lastTag = " + varcharParser(dto.getLastTag())
            + ", jrNum = " + myGetNumber(dto.getJrNum())
            + ", feNumEmbryos = " + myGetNumber(dto.getFeNumEmbryos())  
            + ", feMaxGen = " + varcharParser(dto.getFeMaxGen())
            + ", fsNumMales = " + myGetNumber(dto.getFsNumMales())  
            + ", fsMaxGen = " + varcharParser(dto.getFsMaxGen())
            + ", foNumFemales = " + myGetNumber(dto.getFoNumFemales())  
            + ", foMaxGen = " + varcharParser(dto.getFoMaxGen())
            + ", cardColor = " + varcharParser(dto.getCardColor())
            + ", strainType = " + varcharParser(dto.getStrainType())
            + ", comment = " + varcharParser(dto.getComment())
            + ", lineViabilityYellowMinNumMales = " + myGetNumber(dto.getLineViabilityYellowMinNumMales()) 
            + ", lineViabilityYellowMinNumFemales = " +  myGetNumber(dto.getLineViabilityYellowMinNumFemales())  
            + ", lineViabilityYellowMaxAgeMales = " +  myGetNumber(dto.getLineViabilityYellowMaxAgeMales())  
            + ", lineViabilityYellowMaxAgeFemales = " + myGetNumber(dto.getLineViabilityYellowMaxAgeFemales()) 
            + ", lineViabilityRedMinNumMales = " + myGetNumber(dto.getLineViabilityRedMinNumMales()) 
            + ", lineViabilityRedMinNumFemales = " + myGetNumber(dto.getLineViabilityRedMinNumFemales()) 
            + ", lineViabilityRedMaxAgeMales = " + myGetNumber(dto.getLineViabilityRedMaxAgeMales()) 
            + ", lineViabilityRedMaxAgeFemales = " + myGetNumber(dto.getLineViabilityRedMaxAgeFemales()) 
            + ", section_ = " + varcharParser(dto.getSection_())
            + ", isActive = " + (dto.getIsActive() ? -1 : 0)
            + ", version = " + version.toString()
            +"\n WHERE _strain_key = "+ dto.getStrainKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteStrainVocabulary(AdminStrainDTO dto) throws SQLException {
        String cmd = "UPDATE Strain SET isActive = 0 WHERE _strain_key = "+ dto.getStrainKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }

    /** RETIRE PENS **/
    
    public int retirePens(String errorLog) {
        ArrayList<ContainerHistoryDTO> retirePenList = getPensToRetire();
        String retiredContainerStatusKey = this.getRetiredContainerStatusKey();
        String cmd = "";
        int pensRetired = 0;
        
        if (retiredContainerStatusKey.length() > 0) {
            for (ContainerHistoryDTO dto : retirePenList) {
                try {
                    // Create ContainerHistory record 
                    cmd = "\n INSERT INTO ContainerHistory (_room_key, _container_key, actionDate, _containerStatus_key, version) "
                        + "\n VALUES ("+ dto.getRoomKey() 
                        + " ," + dto.getContainerKey() 
                        + " , now()" 
                        + " ," + retiredContainerStatusKey
                        + " , 1)" ;
                    executeJCMSUpdate(cmd);

                    // Update Container to point to new retired container history record
                    cmd = "UPDATE Container SET " 
                        + " _containerHistory_key = (SELECT MAX(_containerHistory_key) FROM ContainerHistory)"
                        + " WHERE _container_key = "+ dto.getContainerKey() ;
                    executeJCMSUpdate(cmd);
                    pensRetired++;
                } catch (SQLException ex) {
                    errorLog += "Failed to retire container ID " + dto.getContainerID() +"\n";
                }
            }
        } else {
            errorLog = "Retire pen transaction cancelled.  Failed to find container status, retired."; 
        }
        
        return pensRetired;
    }
    
    private ArrayList<ContainerHistoryDTO> getPensToRetire() {
        ArrayList<ContainerHistoryDTO> dtoList = new ArrayList<ContainerHistoryDTO> ();
        ContainerHistoryDTO dto = null;
        String cmd = ""
                + "\n SELECT ch._containerHistory_key, ch._room_key, ch._container_key, ch.actionDate, ch._containerStatus_key, s.containerStatus, c.containerID "
                + "\n FROM ContainerHistory ch "
                + "\n INNER JOIN Container c ON c._container_key = ch._container_key "
                + "\n INNER JOIN cv_ContainerStatus s ON ch._containerStatus_key = s._containerStatus_key "
                + "\n WHERE ch.actionDate IN "
                + "\n       (SELECT MAX(ch2.actionDate) "
                + "\n        FROM ContainerHistory ch2 "
                + "\n        WHERE ch2._container_key = ch._container_key) "
                + "\n   AND NOT EXISTS (SELECT 1 FROM Mouse "
                + "\n                   WHERE _pen_key = ch._container_key "
                + "\n                     AND lifeStatus = 'A') "
                + "\n   AND NOT EXISTS (SELECT 1 FROM ContainerHistory ch3 "
                + "\n                   INNER JOIN cv_ContainerStatus s3 ON ch3._containerStatus_key = s3._containerStatus_key "
                + "\n                   WHERE ch._containerHistory_key = ch3._containerHistory_key "
                + "\n                     AND s3.containerStatus = 'retired') "
                + "\n   AND NOT EXISTS (SELECT 1 FROM ContainerHistory ch4 "
                + "\n                   WHERE ch._containerHistory_key = ch4._containerHistory_key "
                + "\n                     AND ch4.actionDate > now()) "
                + "\n GROUP BY ch._container_key "
                + "\n HAVING MAX(ch.actionDate) ";

        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createContainerHistoryDTO(row);
            dtoList.add(dto);
        }
        
        return dtoList;
    }
    
    private ContainerHistoryDTO createContainerHistoryDTO(SortedMap row) {
        ContainerHistoryDTO dto = new ContainerHistoryDTO();
        dto.setContainerHistorykey(Integer.parseInt(myGet("_containerHistory_key", row)));
        dto.setRoomKey(Integer.parseInt(myGet("_room_key", row)));
        dto.setContainerKey(Integer.parseInt(myGet("_container_key", row)));
        dto.setContainerID(myGet("containerID", row));
        return dto;
    }
    
    private String getRetiredContainerStatusKey()
    {
        String key = "";
        String cmd = ""
                + "\n SELECT _containerStatus_key "
                + "\n FROM cv_ContainerStatus "
                + "\n WHERE containerStatus = 'retired' ";
        Result result = executeJCMSQuery(cmd);

        if (result != null) {
            for (SortedMap row : result.getRows()) {
                key = myGet("_containerStatus_key", row);
            }
        }
        return key;
    }

    /** DBINFO **/

    public ArrayList<AdminDbInfoDTO> getDbInfo() {
        ArrayList<AdminDbInfoDTO> dtoList = new ArrayList<AdminDbInfoDTO> ();
        AdminDbInfoDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM dbInfo");
        for (SortedMap row : result.getRows()) {
            dto = this.createDbInfoDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminDbInfoDTO createDbInfoDTO(SortedMap row) {
        AdminDbInfoDTO dto = new AdminDbInfoDTO();
        dto.setDbInfoKey(myGet("_dbinfo_key", row));
        dto.setDbVers(myGet("dbVers", row));
        dto.setVersDate(myGet("versDate", row));
        dto.setMaxPenID(myGet("maxPenID", row));
        dto.setReleaseNum(myGet("releaseNum", row));
        dto.setReleaseDate(myGet("releaseDate", row));
        dto.setMaxAutoLitterNum(myGet("maxAutoLitterNum", row));
        dto.setMaxAutoMouseID(myGet("maxAutoMouseID", row));
        dto.setReleaseType(myGet("releaseType", row));
        dto.setReleaseNotes(myGet("releaseNotes", row));
        dto.setWebReleaseNum(myGet("webReleaseNum", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }

    
    
    /** MANAGE ROOM **/

    private String roomSelect = "SELECT r._room_key, r.roomName, r._healthLevelHistory_key, r.version, r.createdBy, r.dateCreated, r.modifiedBy, r.dateModified, r.isActive,"
            + "\n h._healthLevel_key, DATE_FORMAT(h.startDate,'%m/%e/%Y %T') as startDate, h.version AS hlVersion, h.createdBy AS hlCreatedBy, h.dateCreated AS hlDateCreated, "
            + "\n h.modifiedBy AS hlModifiedBy, h.dateModified hlDateModifed, l.healthLevel, "
            + "\n (SELECT COUNT(h2._healthLevelHistory_key) FROM HealthLevelHistory h2 WHERE h2._room_key = r._room_key ) as historyCount "
            + "\n FROM Room r "
            + "\n INNER JOIN HealthLevelHistory h ON r._healthLevelHistory_key = h._healthLevelHistory_key "
            + "\n INNER JOIN cv_HealthLevel l ON h._healthLevel_key = l._healthLevel_key ";
    private String roomOrderBy = " ORDER BY r.roomName";
    private String roomWhere = " WHERE r._room_key > 0 ";
    private String healthLevelHistorySelect = " "
            + "\n SELECT h._healthLevelHistory_key, h._room_key, h._healthLevel_key, DATE_FORMAT(h.startDate,'%m/%e/%Y %T') as startDate, "
            + "\n h.version, h.createdBy, h.dateCreated, h.modifiedBy, h.dateModified, "
            + "\n l.healthLevel, l.description, l.version "
            + "\n FROM HealthLevelHistory h "
            + "\n INNER JOIN cv_HealthLevel l ON l._healthLevel_key = h._healthLevel_key " ;
    private String healthLevelHistoryOrderBy = "\n ORDER BY h.startDate DESC" ;
    
    public ArrayList<AdminRoomDTO> getRooms() {
        ArrayList<AdminRoomDTO> dtoList = new ArrayList<AdminRoomDTO> ();
        AdminRoomDTO dto = null;
        roomWhere += " AND r.isActive = -1 ";
        Result result = executeJCMSQuery(roomSelect + roomWhere + roomOrderBy);
        for (SortedMap row : result.getRows()) {
            dto = this.createRoomDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<AdminRoomDTO> getRooms(AdminRoomDTO searchCriteria) {
        ArrayList<AdminRoomDTO> list = new ArrayList<AdminRoomDTO> ();
        String cmd = roomSelect + roomWhere;
        
        if (searchCriteria.getIsActive() != null ) {
            cmd += " AND r.isActive = "+ (searchCriteria.getIsActive() ? "-1" : "0") +" ";
        }
        if (searchCriteria.getRoomKey() != null && searchCriteria.getRoomKey().length() > 0 && !searchCriteria.getRoomKey().equalsIgnoreCase("0")) {
            cmd += " AND r._room_key = "+ searchCriteria.getRoomKey() +" ";
        }
        if (searchCriteria.getRoomName() != null && searchCriteria.getRoomName().length() > 0) {
            cmd += " AND r.roomName LIKE "+ varcharParserContains(searchCriteria.getRoomName()) +" ";
        }
        if (searchCriteria.getHealthLevelHistoryDTO().getStartDateFrom() != null && searchCriteria.getHealthLevelHistoryDTO().getStartDateFrom().length() > 0) {
            cmd += " AND h.startDate >= "+ dateParser(searchCriteria.getHealthLevelHistoryDTO().getStartDateFrom()) + " " ;
        }
        if (searchCriteria.getHealthLevelHistoryDTO().getStartDateTo() != null && searchCriteria.getHealthLevelHistoryDTO().getStartDateTo().length() > 0) {
            cmd += " AND h.startDate <= "+ dateParser(searchCriteria.getHealthLevelHistoryDTO().getStartDateTo()) + " " ;
        }
        if (searchCriteria.getHealthLevelHistoryDTO().getHealthLevel() != null && searchCriteria.getHealthLevelHistoryDTO().getHealthLevel().length() > 0) {
            cmd += " AND l.healthLevel = "+ searchCriteria.getHealthLevelHistoryDTO().getHealthLevel() ;
        }
        
        cmd += roomOrderBy ;
        
        Result result = executeJCMSQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                list.add(this.createRoomDTO(row));
            }
        }
        return list;
    }
    
    public ArrayList<AdminHealthLevelHistoryDTO> getHealthLevelHistory(String roomKey) {
        ArrayList<AdminHealthLevelHistoryDTO> dtoList = new ArrayList<AdminHealthLevelHistoryDTO> ();
        AdminHealthLevelHistoryDTO dto = null;
        String whereClause = "\n WHERE h._room_key = "+ roomKey +" ";
        String cmd = healthLevelHistorySelect + whereClause + healthLevelHistoryOrderBy;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createHealthLevelHistoryDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private AdminRoomDTO createRoomDTO(SortedMap row) {
        AdminRoomDTO dto = new AdminRoomDTO();
        dto.setRoomKey(myGet("_room_key", row));
        dto.setHealthLevelHistoryKey(myGet("_healthLevelHistory_key", row));
        dto.setRoomName(myGet("roomName", row));
        dto.setVersion(myGet("version", row));
        dto.setIsActive((myGet("isActive", row).toString().equalsIgnoreCase("0") ? false : true));
        dto.setCreatedBy(myGet("createdBy", row));
        dto.setDateCreated(myGet("dateCreated", row));
        dto.setModifiedBy(myGet("modifiedBy", row));
        dto.setDateModified(myGet("dateModified", row));
        dto.setHistoryCount(myGet("historyCount", row));
        dto.setLabel(myGet("roomName", row));

        AdminHealthLevelHistoryDTO hlhDTO = this.createHealthLevelHistoryDTO(row);
        
        dto.setHealthLevelHistoryDTO(hlhDTO);

        return dto;
    }

    private AdminHealthLevelHistoryDTO createHealthLevelHistoryDTO(SortedMap row) {
        Date startDate = null;
        AdminHealthLevelHistoryDTO hlhDTO = new AdminHealthLevelHistoryDTO();
        hlhDTO.setHealthLevelHistoryKey(myGet("_healthLevelHistory_key", row));
        hlhDTO.setRoomKey(myGet("_room_key", row));
        hlhDTO.setHealthLevelKey(myGet("_healthLevel_key", row));
        hlhDTO.setHealthLevel(myGet("healthLevel", row));
        String strStartDate = myGet("startDate", row).toString();
        try { startDate = (strStartDate != null ? this.convertStringToDate(strStartDate) : null);
        } catch (Exception e) { }
        hlhDTO.setStartDate(startDate);
        hlhDTO.setVersion(myGet("version", row));
        hlhDTO.setCreatedBy(myGet("hlCreatedBy", row));
        hlhDTO.setDateCreated(myGet("hlDateCreated", row));
        hlhDTO.setModifiedBy(myGet("hlModifiedBy", row));
        hlhDTO.setDateModified(myGet("hlDateModified", row));
        
        return hlhDTO;
    }
    
    public Integer saveRoom(AdminRoomDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertRoom(dto);
        } else {
            cnt = this.updateRoom(dto);
        }
        return cnt;
    }
    
    private Integer insertRoom(AdminRoomDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer count = this.insertHealthLevelHistory(dto);
        
        if (count == 1) {
            String cmd = "INSERT INTO Room " 
                + "\n (roomName, _healthLevelHistory_key, version, isActive, createdBy, dateCreated, modifiedBy, dateModified ) "
                + "\n VALUES ("+ varcharParser(dto.getRoomName().trim())
                + ", (SELECT MAX(_healthLevelHistory_key) FROM HealthLevelHistory) "
                + ", "+ version.toString() 
                + ", "+ (dto.getIsActive() ? -1 : 0)
                + ", "+ varcharParser(dto.getModifiedBy())
                + ", now() "
                + ", "+ varcharParser(dto.getModifiedBy()) 
                + ", now() "
                + " )" ;
            count = executeJCMSUpdate(cmd);
            
            cmd = "UPDATE HealthLevelHistory SET _room_key = (SELECT MAX(_room_key) FROM Room) "
                + "WHERE _healthLevelHistory_key = (SELECT * FROM (SELECT MAX(_healthLevelHistory_key) FROM HealthLevelHistory) as t)";
            count = executeJCMSUpdate(cmd);
        }
        return count;
    }

    private Integer updateRoom(AdminRoomDTO dto) throws SQLException {
        Boolean isMaxStartDate = this.isMaxStartDate(dto);
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        Integer count = this.insertHealthLevelHistory(dto);
        
        if ((count == 1) && (isMaxStartDate)) {
        String cmd = "UPDATE Room SET " 
            + " roomName = " + varcharParser(dto.getRoomName().trim())
            + ", _healthLevelHistory_key = (SELECT MAX(_healthLevelHistory_key) FROM HealthLevelHistory) "
            + ", version = " + version.toString()
            + ", isActive = " + (dto.getIsActive() ? -1 : 0)
            + ", modifiedBy = " + varcharParser(dto.getModifiedBy())
            + ", dateModified = now() "
            + "\n WHERE _room_key = "+ dto.getRoomKey() ;
            count = executeJCMSUpdate(cmd);
            
            if (count == 1)
                updateRoomHealthLevelHistory(dto.getRoomKey());
        }
        return count;
    }
    
    public Integer deleteRoom(AdminRoomDTO dto) throws SQLException {
        String cmd = "UPDATE Room SET isActive = " + 0
            + " WHERE _room_key = "+ dto.getRoomKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }

    public Integer updateRoomHealthLevelHistory(String roomKey) throws SQLException {
        Integer count = 0;
        String cmd = "UPDATE Room SET _healthLevelHistory_key = "
            + " (SELECT h._healthLevelHistory_key "
            + "  FROM HealthLevelHistory h "
            + "  WHERE h._room_key = "+ roomKey +" AND h.startDate = "
            + "     (SELECT MAX(startDate) FROM HealthLevelHistory h2 WHERE h2._room_key = "+ roomKey +") LIMIT 1) "
            + "WHERE _room_key = "+ roomKey ;
        try {
            count = executeJCMSUpdate(cmd);
        } catch (Exception e) {
            // There is a unique index on room._healthLevelHistory_key called _currentHealthLevelHistory_key
            // This index throws an exception if the history key is set to the save value.
            System.out.println("\nUpdateRoomHealthLevelHistory Exception:  "+ e);
        }
        return count;
    }

    protected Integer insertHealthLevelHistory(AdminRoomDTO dto) throws SQLException {
        String roomKey = dto.getRoomKey();
        if (roomKey.length() == 0 || roomKey.equalsIgnoreCase("0")) 
            // Temporarily use default room key for unknown
            roomKey = "(SELECT MIN(_room_key) as _room_key FROM Room)";  
        else 
            roomKey = dto.getRoomKey();
        String cmd = "INSERT INTO HealthLevelHistory " 
            + "\n (_room_key, _healthLevel_key, startDate, version, createdBy, dateCreated, modifiedBy, dateModified ) "
            + "\n VALUES ("
            + roomKey
            + ", (SELECT _healthLevel_key FROM cv_HealthLevel WHERE healthLevel = "+ varcharParser(dto.getHealthLevelHistoryDTO().getHealthLevel()) + ")"
            + ", '"+ this.formatAsMySQLDate(dto.getHealthLevelHistoryDTO().getStartDate()) 
            + "', 1" 
            + ", "+ varcharParser(dto.getModifiedBy())
            + ", now()"
            + ", "+ varcharParser(dto.getModifiedBy()) 
            + ", now()"
            + " )" ;
        Integer count = executeJCMSUpdate(cmd);
        
        return count;
    }
    
    public Boolean deleteHealthLevelHistory(AdminHealthLevelHistoryDTO dto) throws SQLException {
        String cmd = " DELETE FROM HealthLevelHistory "
                   + " WHERE _healthLevelHistory_key = " + dto.getHealthLevelHistoryKey() ;
        Integer count = executeJCMSUpdate(cmd);
        
        if (count == 1) {
            this.updateRoomHealthLevelHistory(dto.getRoomKey());
        }
        return (count == 1);
    }
    
    public Boolean isDuplicateRoom(String roomKey, String roomName) {
        Result result = null;
        String cmd = "";
        
        if (roomKey.equalsIgnoreCase("0")) {
            cmd = "SELECT * FROM Room WHERE TRIM(roomName) =  "+ varcharParser(roomName.trim()) +" ";
            result = executeJCMSQuery(cmd);
            if (result != null) {
                if (result.getRowCount() > 0) {
                    return true;
                }
            }
        }
        
        cmd = "SELECT * FROM Room WHERE _room_key = "+ roomKey +" AND TRIM(roomName) =  "+ varcharParser(roomName.trim()) +" ";
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() == 1) {
                // Same room
                return false;
            }
        }
        
        cmd = "SELECT * FROM Room WHERE TRIM(roomName) =  "+ varcharParser(roomName.trim()) +" ";
        result = executeJCMSQuery(cmd);
        if (result != null) {
            if (result.getRowCount() > 0) {
                return true;
            }
        }
            
        return false;
    }
    
    public Boolean isSameDate(AdminRoomDTO dto) {
        Result result = null;
        String cmd = " SELECT * FROM HealthLevelHistory "
                + " WHERE _room_key = " + dto.getRoomKey()
                + "   AND startDate = '" + this.formatAsMySQLDate(dto.getHealthLevelHistoryDTO().getStartDate()) + "' ";
        try {
            result = executeJCMSQuery(cmd);
        } catch (Exception e) {
            return true;
        }
        return (result.getRowCount() > 0) ;
    }
    public Boolean isMaxStartDate(AdminRoomDTO dto) {
        Result result = null;
        String cmd = " SELECT * "
                   + " FROM Room r "
                   + " INNER JOIN HealthLevelHistory h ON r._healthLevelHistory_key = h._healthLevelHistory_key "
                   + " WHERE roomName = "+ varcharParser(dto.getRoomName()) 
                   + "   AND h.startDate > '" + this.formatAsMySQLDate(dto.getHealthLevelHistoryDTO().getStartDate()) + "' ";
        if (dto.getRoomKey().equalsIgnoreCase("0")) 
            return true;
        try {
            result = executeJCMSQuery(cmd);
        } catch (Exception e) {
            return true;
        }
        return (result.getRowCount() == 0) ;
    }

    /** MOUSE USE **/

    public ArrayList<MouseUseDTO> getMouseUseVocabulary() {
        ArrayList<MouseUseDTO> dtoList = new ArrayList<MouseUseDTO> ();
        MouseUseDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_MouseUse ORDER BY mouseUse ");
        for (SortedMap row : result.getRows()) {
            dto = this.createMouseUseDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private MouseUseDTO createMouseUseDTO(SortedMap row) {
        MouseUseDTO dto = new MouseUseDTO();
        dto.setMouseUse_key(myGet("_mouseUse_key", row));
        dto.setMouseUse(myGet("mouseUse", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setUseDescription(myGet("description", row));
        dto.setD1Caption(myGet("d1Caption", row));
        dto.setD2Caption(myGet("d2Caption", row));
        dto.setD3Caption(myGet("d3Caption", row));
        dto.setD4Caption(myGet("d4Caption", row));
        dto.setD5Caption(myGet("d5Caption", row));
        dto.setD6Caption(myGet("d6Caption", row));
        dto.setD7Caption(myGet("d7Caption", row));
        dto.setD8Caption(myGet("d8Caption", row));
        dto.setD9Caption(myGet("d9Caption", row));
        dto.setD10Caption(myGet("d10Caption", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    /** QUERY TYPE **/

    public ArrayList<QueryTypeDTO> getQueryTypeVocabulary() {
        ArrayList<QueryTypeDTO> dtoList = new ArrayList<QueryTypeDTO> ();
        QueryTypeDTO dto = null;
        Result result = executeJCMSQuery("SELECT * FROM cv_QueryType ORDER BY QueryType ");
        for (SortedMap row : result.getRows()) {
            dto = this.createQueryTypeDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<QueryTypeDTO> getCageCardQueryTypes() {
        ArrayList<QueryTypeDTO> dtoList = new ArrayList<QueryTypeDTO> ();
        QueryTypeDTO dto = null;
        Result result = executeJCMSWebQuery("SELECT * FROM cv_QueryType WHERE QueryType IN ('Detail', 'Mating', 'Wean') ORDER BY QueryType ");
        for (SortedMap row : result.getRows()) {
            dto = this.createQueryTypeDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    private QueryTypeDTO createQueryTypeDTO(SortedMap row) {
        QueryTypeDTO dto = new QueryTypeDTO();
        dto.setQueryType_key(myGet("_QueryType_key", row));
        dto.setQueryType(myGet("QueryType", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setIsDefault(Boolean.parseBoolean(myGet("isDefault", row)));
        dto.setIsDeprecated(Boolean.parseBoolean(myGet("isDeprecated", row)));
        dto.setCreatedBy(myGet("CreatedBy", row));
        dto.setDateCreated(myGet("DateCreated", row));
        dto.setModifiedBy(myGet("ModifiedBy", row));
        dto.setDateModified(myGet("DateModified", row));
        dto.setElementID(myGet("ElementID", row));
        dto.setSortOrder(myGet("SortOrder", row));
        dto.setVocabularySource_key(myGet("_VocabularySource_key", row));
        dto.setWorkgroup_key(myGet("_Workgroup_key", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    


}
