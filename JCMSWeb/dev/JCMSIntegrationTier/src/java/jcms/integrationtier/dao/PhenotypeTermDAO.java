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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.PhenotypeTermDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;

/**
 *
 * @author bas
 */
public class PhenotypeTermDAO extends MySQLDAO {
    
    private Connection con = null;

    private String cvPhenotypeTermByPhenotypeKey = "SELECT * FROM cv_Phenotype WHERE _phenotype_key = ";
    
    private String cvPhenotypeTermByPhenotype = "SELECT * FROM cv_Phenotype WHERE phenotype = ";
    
    private String cvPhenotypeTerm = "SELECT * FROM cv_Phenotype ORDER BY phenotype";
    
    public cvPhenotypeTermDTO getCvPhenotypeTermByKey(String phenotypeTermKey){
        cvPhenotypeTermDTO phenotypeDTO = new cvPhenotypeTermDTO();
        String queryString = cvPhenotypeTermByPhenotypeKey + phenotypeTermKey + ";";
        SortedMap[] result = executeJCMSQuery(queryString).getRows();
        if(result.length != 0){
            SortedMap phenotype = result[0];
            phenotypeDTO.setIsActive(myGet("isActive", phenotype));
            phenotypeDTO.setPhenotypeTermName(myGet("phenotype", phenotype));
            phenotypeDTO.setPhenotypeTermKey(myGet("_phenotype_key", phenotype));
            phenotypeDTO.setPhenotypeTermDescription(myGet("description", phenotype));
            phenotypeDTO.setVersionNum(myGet("version", phenotype));
        }
        return phenotypeDTO;
    }
    
    public ArrayList<cvPhenotypeTermDTO> getCvPhenotypeTerms(){
        ArrayList<cvPhenotypeTermDTO> phenotypeTerms = new ArrayList<cvPhenotypeTermDTO>();
        String query = "SELECT * FROM cv_Phenotype ORDER BY phenotype;";
        SortedMap[] result = executeJCMSQuery(query).getRows();
        for(SortedMap phenotype : result){
            cvPhenotypeTermDTO phenotypeDTO = new cvPhenotypeTermDTO();
            phenotypeDTO.setIsActive(myGet("isActive", phenotype));
            phenotypeDTO.setPhenotypeTermName(myGet("phenotype", phenotype));
            phenotypeDTO.setPhenotypeTermKey(myGet("_phenotype_key", phenotype));
            phenotypeDTO.setPhenotypeTermDescription(myGet("description", phenotype));
            phenotypeDTO.setVersionNum(myGet("version", phenotype));
            phenotypeTerms.add(phenotypeDTO);
        }
        return phenotypeTerms;
    }
    
    public ArrayList<cvPhenotypeTermDTO> getActiveCvPhenotypeTerms(){
        ArrayList<cvPhenotypeTermDTO> phenotypeTerms = new ArrayList<cvPhenotypeTermDTO>();
        String query = "SELECT * FROM cv_Phenotype WHERE isActive = '-1' ORDER BY phenotype;";
        SortedMap[] result = executeJCMSQuery(query).getRows();
        for(SortedMap phenotype : result){
            cvPhenotypeTermDTO phenotypeDTO = new cvPhenotypeTermDTO();
            phenotypeDTO.setIsActive(myGet("isActive", phenotype));
            phenotypeDTO.setPhenotypeTermName(myGet("phenotype", phenotype));
            phenotypeDTO.setPhenotypeTermKey(myGet("_phenotype_key", phenotype));
            phenotypeDTO.setPhenotypeTermDescription(myGet("description", phenotype));
            phenotypeDTO.setVersionNum(myGet("version", phenotype));
            phenotypeTerms.add(phenotypeDTO);
        }
        return phenotypeTerms;
    }
    
    public cvPhenotypeTermDTO getCvPhenotypeTermByPhenotype(String phenotypeTerm){
        cvPhenotypeTermDTO phenotypeDTO = new cvPhenotypeTermDTO();
        String queryString = cvPhenotypeTermByPhenotype + "'" + phenotypeTerm + "'" + ";";
        SortedMap[] result = executeJCMSQuery(queryString).getRows();
        if(result.length != 0){
            SortedMap phenotype = result[0];
            phenotypeDTO.setIsActive(myGet("isActive", phenotype));
            phenotypeDTO.setPhenotypeTermName(myGet("phenotype", phenotype));
            phenotypeDTO.setPhenotypeTermKey(myGet("_phenotype_key", phenotype));
            phenotypeDTO.setPhenotypeTermDescription(myGet("description", phenotype));
            phenotypeDTO.setVersionNum(myGet("version", phenotype));
        }
        return phenotypeDTO;
    }
    
// mka's newer method of using the cv tables?
    public ArrayList<PhenotypeTermDTO> getPhenotypeTerms() {
        ArrayList<PhenotypeTermDTO> dtoList = new ArrayList<PhenotypeTermDTO> ();
        PhenotypeTermDTO dto = null;
        Result result = executeJCMSQuery(cvPhenotypeTerm);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createPhenotypeTermDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    private PhenotypeTermDTO createPhenotypeTermDTO(SortedMap row) {
        PhenotypeTermDTO dto = new PhenotypeTermDTO();
        dto.setPhenotypeTermKey(myGet("_phenotype_key", row));
        dto.setPhenotypeTermName(myGet("phenotype", row));
        dto.setPhenotypeTermDescription(myGet("description", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setVersionNum(myGet("version", row));
        
        return dto;
    }
    
    public Integer savePhenotypeTerm(PhenotypeTermDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertPhenotypeTerm(dto);
        } else {
            cnt = this.updatePhenotypeTerm(dto);
        }
        return cnt;
    }
        
    private Integer insertPhenotypeTerm(PhenotypeTermDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersionNum())) + 1;
        
        String cmd = "INSERT INTO cv_Phenotype " 
            + "\n (phenotype, description, version, isActive ) "
            + "\n VALUES ("+ varcharParser(dto.getPhenotypeTermName().trim())
            + ", "+ varcharParser(dto.getPhenotypeTermDescription().trim())
            + ", "+ version.toString() 
            + ", "+ (dto.getIsActive() ? -1 : 0)
            + " )" ;
        executeJCMSUpdate(cmd);

        Integer key = 0;
        Result result = this.executeJCMSQuery("SELECT MAX(_phenotype_key) as primaryKey FROM cv_Phenotype");
        for (SortedMap row : result.getRows()) {
            key = Integer.parseInt(this.myGet("primaryKey", row));
        }
        return key;
    }

    private Integer updatePhenotypeTerm(PhenotypeTermDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersionNum())) + 1;
        
        String cmd = "UPDATE cv_Phenotype SET " 
            + " phenotype = " + varcharParser(dto.getPhenotypeTermName().trim())
            + ", description = " + varcharParser(dto.getPhenotypeTermDescription().trim())
            + ", isActive = " + (dto.getIsActive() ? -1 : 0)
            + ", version = " + version.toString()
            + "\n WHERE _phenotype_key = "+ dto.getPhenotypeTermKey() ;
        Integer count = executeJCMSUpdate(cmd);
            
        return count;
    }    
    
    public Integer deletePhenotypeTerm(PhenotypeTermDTO dto) throws SQLException {
        String cmd = "DELETE FROM cv_Phenotype WHERE _phenotype_key = "+ dto.getPhenotypeTermKey() ;  
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
}