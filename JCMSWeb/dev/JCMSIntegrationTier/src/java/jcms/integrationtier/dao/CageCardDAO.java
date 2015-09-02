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
import jcms.integrationtier.dto.QueryDefinitionDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;

/**
 *
 * @author cnh
 */
public class CageCardDAO extends MySQLDAO {

    private String selectCmd = "SELECT * FROM QueryDefinition " ; 
    
    public CageCardDAO () {}
    
    public ArrayList<QueryDefinitionDTO> getQueryDefinitions() {
        ArrayList<QueryDefinitionDTO> dtoList = new ArrayList<QueryDefinitionDTO> ();
        QueryDefinitionDTO dto = null;
        String cmd = selectCmd
                + " ORDER BY QueryName ";
        Result result = executeJCMSWebQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createQueryDefinitionDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    public ArrayList<QueryDefinitionDTO> getQueryDefinitions(String queryTypeKey) {
        ArrayList<QueryDefinitionDTO> dtoList = new ArrayList<QueryDefinitionDTO> ();
        QueryDefinitionDTO dto = null;
        String cmd = selectCmd
                + " WHERE _QueryType_key = "+ queryTypeKey 
                + " ORDER BY QueryName ";
        Result result = executeJCMSWebQuery(cmd);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createQueryDefinitionDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    private QueryDefinitionDTO createQueryDefinitionDTO(SortedMap row) {
        QueryDefinitionDTO dto = new QueryDefinitionDTO();
        dto.setQueryDefinition_key(myGet("_QueryDefinition_key", row));
        dto.setQueryType_key(myGet("_QueryType_key", row));
        dto.setUser_key(myGet("_User_key", row));
        dto.setQueryName(myGet("QueryName", row));
        dto.setQueryOptions(myGet("QueryOptions", row));
        
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
        dto.setCreatedBy(myGet("CreatedBy", row));
        dto.setDateCreated(myGet("DateCreated", row));
        dto.setModifiedBy(myGet("ModifiedBy", row));
        dto.setDateModified(myGet("DateModified", row));
        dto.setWorkgroup_key(myGet("_Workgroup_key", row));
        dto.setVersion(myGet("version", row));
        
        return dto;
    }
    
    public String insertCageCardLinkRow(String queryDefKey, String workgroupKey) throws Exception{
        String key = "";
        String query = "INSERT INTO QueryWorkgroupLink (_QueryDefinition_key, _Workgroup_key) "
                + "VALUES (" + queryDefKey + ", " + workgroupKey + ");";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public ArrayList<String> getCardOwnersByCardKey(String cardKey){
        ArrayList<String> cardGroups = new ArrayList<String>();
        String query = "SELECT WorkgroupName "
                + "FROM QueryWorkgroupLink "
                + "JOIN Workgroup "
                + "ON QueryWorkgroupLink._Workgroup_key = Workgroup._Workgroup_key "
                + "WHERE _QueryDefinition_key = " + cardKey;
        SortedMap[] workgroups = this.executeJCMSQuery(query).getRows();
        for(SortedMap wg : workgroups){
            cardGroups.add(myGet("WorkgroupName", wg));
        }
        return cardGroups;
    }
    
    public ArrayList<Integer> getWorkgroupKeysForCard(String cardName, String queryType){
        ArrayList<Integer> keys = new ArrayList<Integer>();
        String query = "SELECT QueryWorkgroupLink._Workgroup_key " +
            "FROM QueryWorkgroupLink " +
            "JOIN Workgroup " +
            "ON QueryWorkgroupLink._Workgroup_key = Workgroup._Workgroup_key " +
            "JOIN QueryDefinition " +
            "ON QueryWorkgroupLink._QueryDefinition_key = QueryDefinition._QueryDefinition_key " +
            "WHERE QueryName = '" + cardName + "' AND _QueryType_key = " + queryType;
        SortedMap[] wgKeys = this.executeJCMSQuery(query).getRows();
        for(SortedMap wgKey : wgKeys){
            keys.add(new Integer(myGet("_Workgroup_key", wgKey)));
        }
        return keys;
    }
    
    public void deleteQueryLinks(String queryKey) throws Exception {
        String query = "DELETE FROM QueryWorkgroupLink WHERE _QueryDefinition_key = " + queryKey;
        this.executeJCMSUpdate(query);
    }
    
    public ArrayList<QueryDefinitionEntity> getDetailCards(ArrayList<WorkgroupEntity> wgs){
        ArrayList<QueryDefinitionEntity> theCards = new ArrayList();
        String query = "SELECT DISTINCT QueryDefinition.* \n" + 
                "FROM QueryDefinition \n" + 
                "JOIN QueryWorkgroupLink \n" + 
                "ON QueryDefinition._QueryDefinition_key = QueryWorkgroupLink._QueryDefinition_key \n" + 
                "JOIN Workgroup \n" + 
                "ON QueryWorkgroupLink._Workgroup_key = Workgroup._Workgroup_key \n" + 
                "WHERE _QueryType_key = 3 AND (";
        String workgroupClause = "";
        for(WorkgroupEntity wg : wgs){
            if(wg.equals(wgs.get(wgs.size() - 1))){                
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey();
            }
            else{
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey() + " OR ";
            }
        }
        query = query + workgroupClause + ")";
        SortedMap[] cards = this.executeJCMSQuery(query).getRows();
        for(SortedMap card : cards){
            QueryDefinitionEntity qde = new QueryDefinitionEntity();
            qde.setQueryDefinitionkey(new Integer(myGet("_QueryDefinition_key", card)));
            qde.setQueryName(myGet("QueryName", card));
            theCards.add(qde);
        }
        return theCards;
    }
    
    public ArrayList<QueryDefinitionEntity> getMatingCards(ArrayList<WorkgroupEntity> wgs){
        ArrayList<QueryDefinitionEntity> theCards = new ArrayList();
        String query = "SELECT DISTINCT QueryDefinition.* \n" + 
                "FROM QueryDefinition \n" + 
                "JOIN QueryWorkgroupLink \n" + 
                "ON QueryDefinition._QueryDefinition_key = QueryWorkgroupLink._QueryDefinition_key \n" + 
                "JOIN Workgroup \n" + 
                "ON QueryWorkgroupLink._Workgroup_key = Workgroup._Workgroup_key \n" + 
                "WHERE _QueryType_key = 4 AND (";
        String workgroupClause = "";
        for(WorkgroupEntity wg : wgs){
            if(wg.equals(wgs.get(wgs.size() - 1))){                
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey();
            }
            else{
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey() + " OR ";
            }
        }
        query = query + workgroupClause + ")";
        SortedMap[] cards = this.executeJCMSQuery(query).getRows();
        for(SortedMap card : cards){
            QueryDefinitionEntity qde = new QueryDefinitionEntity();
            qde.setQueryDefinitionkey(new Integer(myGet("_QueryDefinition_key", card)));
            qde.setQueryName(myGet("QueryName", card));
            theCards.add(qde);
        }
        return theCards;
    }
    
    public ArrayList<QueryDefinitionEntity> getWeanCards(ArrayList<WorkgroupEntity> wgs){
        ArrayList<QueryDefinitionEntity> theCards = new ArrayList();
        String query = "SELECT DISTINCT QueryDefinition.* \n" + 
                "FROM QueryDefinition \n" + 
                "JOIN QueryWorkgroupLink \n" + 
                "ON QueryDefinition._QueryDefinition_key = QueryWorkgroupLink._QueryDefinition_key \n" + 
                "JOIN Workgroup \n" + 
                "ON QueryWorkgroupLink._Workgroup_key = Workgroup._Workgroup_key \n" + 
                "WHERE _QueryType_key = 5 AND (";
        String workgroupClause = "";
        for(WorkgroupEntity wg : wgs){
            if(wg.equals(wgs.get(wgs.size() - 1))){                
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey();
            }
            else{
                workgroupClause = workgroupClause + " Workgroup._Workgroup_key = " + wg.getWorkgroupkey() + " OR ";
            }
        }
        query = query + workgroupClause + ")";
        SortedMap[] cards = this.executeJCMSQuery(query).getRows();
        for(SortedMap card : cards){
            QueryDefinitionEntity qde = new QueryDefinitionEntity();
            qde.setQueryDefinitionkey(new Integer(myGet("_QueryDefinition_key", card)));
            qde.setQueryName(myGet("QueryName", card));
            theCards.add(qde);
        }
        return theCards;
    }
}
