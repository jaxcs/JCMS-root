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
import jcms.integrationtier.dto.PhenotypeMouseLinkDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.integrationtier.dto.MouseDTO;

/**
 *
 * @author bas
 */
public class PhenotypeMouseLinkDAO extends MySQLDAO {

    public ArrayList<cvPhenotypeTermDTO> getMousePhenotypeTerms (String mouseKey) {
        ArrayList<cvPhenotypeTermDTO> phenotypeTerms = new ArrayList<cvPhenotypeTermDTO>();
        String query = "SELECT * FROM cv_Phenotype "
                + "JOIN PhenotypeMouseLink "
                + "ON cv_Phenotype._phenotype_key = PhenotypeMouseLink._phenotype_key "
                + "JOIN Mouse "
                + "ON PhenotypeMouseLink._mouse_key = Mouse._mouse_key "
                + "WHERE Mouse._mouse_key = " + mouseKey + " "
                + "ORDER BY phenotype;";
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
    
    public void addPhenotypeToMouse (cvPhenotypeTermDTO dto, MouseDTO mouse) throws Exception {
        String query = "INSERT INTO PhenotypeMouseLink (_phenotype_key, _mouse_key) "
                + "VALUES ("
                + numberParser(dto.getPhenotypeTermKey()) + ", "
                + numberParser(mouse.getMouse_key())
                + ");";
        this.executeJCMSUpdate(query);
    }
    
    public void deletePhenotypeFromMouse (MouseDTO mouse, cvPhenotypeTermDTO dto) throws Exception {
        String query = "DELETE FROM PhenotypeMouseLink "
                + "WHERE _phenotype_key = " + dto.getPhenotypeTermKey()
                + " AND _mouse_key = " + mouse.getMouse_key() + ";";
        this.executeJCMSUpdate(query);
    }
    
}

