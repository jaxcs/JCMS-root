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
import jcms.integrationtier.dto.PlugDateDTO;
import java.text.SimpleDateFormat;

/**
 *
 * @author mkamato
 */
public class PlugDateDAO extends MySQLDAO{
    
    private String plugDatesByMouseKeyQuery = "SELECT _plugDate_key, _mating_key, _mouse_key, DATE_FORMAT(plugDate, '%m/%d/%Y') AS plugDate, obsolete, comment, version FROM PlugDate WHERE _mouse_key = ";
    
    public ArrayList<PlugDateDTO> getPlugsByMouseAndMatingKey(String mouseKey, String matingKey){
        ArrayList<PlugDateDTO> plugs = new ArrayList<PlugDateDTO>();
        String tempQuery = plugDatesByMouseKeyQuery + mouseKey + " AND _mating_key = " + matingKey +";";
        SortedMap[] result = executeJCMSQuery(tempQuery).getRows();
        
        for(SortedMap sm: result){
            PlugDateDTO plug = new PlugDateDTO();
            plug.setPlugDateKey(myGet("_plugDate_key", sm));
            plug.setComment(myGet("commnet", sm));
            plug.setMating_key(myGet("_mating_key", sm));
            plug.setMouse_key(myGet("_mouse_key", sm));
            plug.setObsolete(myGet("obsolete", sm));
            plug.setVersion(myGet("version", sm));
            try{
                plug.setPlugDate(new SimpleDateFormat("MM/dd/yyyy").parse(myGet("plugDate", sm))); 
            }
            catch(Exception e){
                System.out.println(e);
            }
            plugs.add(plug);
        }
        return plugs;
    }
    
    public String insertPlugDate(PlugDateDTO pgDTO) throws Exception{
        String plugDateKey = getMaxValue("_plugDate_key", "PlugDate");
        plugDateKey = new Integer(Integer.parseInt(plugDateKey) + 1).toString();
        
        String insertQuery = "INSERT INTO PlugDate "
                + "(_plugDate_key, _mating_key, _mouse_key, plugDate, obsolete, comment) "
                + "VALUES ("
                + numberParser(plugDateKey) + ", "
                + numberParser(pgDTO.getMating_key()) + ", "
                + numberParser(pgDTO.getMouse_key()) + ", "
                + dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pgDTO.getPlugDate())) + ", "
                + numberParser(pgDTO.getObsolete()) + ", "
                + varcharParser(pgDTO.getComment())
                + ")";
        System.out.println(insertQuery);
        executeJCMSUpdate(insertQuery);
        return plugDateKey;
    }
    
    public void deletePlugDate(String plugDateKey) throws Exception{
        String query = "DELETE FROM PlugDate WHERE _plugDate_key = " + plugDateKey;
        executeJCMSUpdate(query);
    }
    
    public void updatePlugDate(PlugDateDTO pgDTO) throws Exception{
        String updateQuery = "UPDATE PlugDate SET "
                + "_mating_key = " + numberParser(pgDTO.getMating_key()) + ", "
                + "_mouse_key = " + numberParser(pgDTO.getMouse_key()) + ", "
                + "plugDate = " + dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pgDTO.getPlugDate())) + ", "
                + "obsolete = " + numberParser(pgDTO.getObsolete()) + ", "
                + "comment = " + varcharParser(pgDTO.getComment()) + " "
                + "WHERE _plugDate_key = " + numberParser(pgDTO.getPlugDateKey()) + ";";
        System.out.println(updateQuery);
        executeJCMSUpdate(updateQuery);
    }
}
