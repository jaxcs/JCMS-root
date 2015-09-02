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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.AdminRoomDTO;
import jcms.integrationtier.dto.cvMouseUseDTO;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.integrationtier.dto.MouseUseDTO;
import jcms.integrationtier.dto.UseScheduleTermDTO;

/**
 *
 * @author mkamato
 */
public class MouseUseDAO extends MySQLDAO {
    
    private String plugDatesByMouseKeyQuery = "SELECT _plugDate_key, DATE_FORMAT(plugDate, '%m/%d/%Y') AS plugDate FROM PlugDate WHERE _mouse_key = ";
    
    private String cvMouseUseByUseKey = "SELECT * FROM cv_MouseUse WHERE _mouseUse_key = ";
    
    private String cvMouseUseByUse = "SELECT * FROM cv_MouseUse WHERE mouseUse = ";
    private String cvMouseUse = "SELECT * FROM cv_MouseUse ORDER BY mouseUse";
    
    private String mouseUsesByMouseKey = "SELECT _usage_key, ABS(MouseUsage._mouse_key), `use`, useAge, "
            + "DATE_FORMAT(projectedDate, '%m/%d/%Y') AS projectedDate, DATE_FORMAT(actualDate, '%m/%d/%Y') AS actualDate, "
            + "MouseUsage.done AS done, TRIM(MouseUsage.comment) AS comment, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, "
            + "ABS(MouseUsage.version) AS version, ABS(US._useSchedule_key) AS _useSchedule_key, UST.useScheduleTermName "
            + "FROM MouseUsage "
            + "LEFT JOIN UseSchedule AS US "
            + "ON MouseUsage._useSchedule_key = US._useSchedule_key "
            + "LEFT JOIN UseScheduleTerm AS UST "
            + "ON US._useScheduleTerm_key = UST._useScheduleTerm_key "
            + " WHERE MouseUsage._mouse_key = ";
    
    private String genotypeByMouseKey = "SELECT allele1, allele2, labSymbol "
            + "FROM Genotype "
            + "JOIN Mouse ON Genotype._mouse_key = Mouse._mouse_key "
            + "JOIN Gene ON Genotype._gene_key = Gene._gene_key "
            + "WHERE Mouse._mouse_key = ";
    
    public ArrayList<String> getPlugsByMouseKey(String mouseKey){
        ArrayList<String> plugs = new ArrayList<String>();
        String tempQuery = plugDatesByMouseKeyQuery + mouseKey + ";";
        SortedMap[] result = executeJCMSQuery(tempQuery).getRows();
        for(SortedMap sm: result){
            plugs.add(myGet("_plugDate_key", sm));
            plugs.add(myGet("plugDate", sm));            
        }
        return plugs;
    }
    
    public cvMouseUseDTO getCvMouseUseByKey(String useKey){
        cvMouseUseDTO useDTO = new cvMouseUseDTO();
        String queryString = cvMouseUseByUseKey + useKey + ";";
        SortedMap[] result = executeJCMSQuery(queryString).getRows();
        if(result.length != 0){
            SortedMap use = result[0];
            useDTO.setIsActive(myGet("isActive", use));
            useDTO.setMouseUse(myGet("mouseUse", use));
            useDTO.setMouseUseKey(myGet("_mouseUse_key", use));
            useDTO.setUseDescription(myGet("useDescription", use));
            useDTO.setVersion(myGet("version", use));
            useDTO.setD1Caption(myGet("d1Caption", use));
            useDTO.setD2Caption(myGet("d2Caption", use));
            useDTO.setD3Caption(myGet("d3Caption", use));
            useDTO.setD4Caption(myGet("d4Caption", use));
            useDTO.setD5Caption(myGet("d5Caption", use));
            useDTO.setD6Caption(myGet("d6Caption", use));
            useDTO.setD7Caption(myGet("d7Caption", use));
            useDTO.setD8Caption(myGet("d8Caption", use));
            useDTO.setD9Caption(myGet("d9Caption", use));
            useDTO.setD10Caption(myGet("d10Caption", use));
        }
        return useDTO;
    }
    
    public ArrayList<cvMouseUseDTO> getCvMouseUses(){
        ArrayList<cvMouseUseDTO> uses = new ArrayList<cvMouseUseDTO>();
        String query = "SELECT * FROM cv_MouseUse;";
        SortedMap[] result = executeJCMSQuery(query).getRows();
        for(SortedMap use : result){
            cvMouseUseDTO useDTO = new cvMouseUseDTO();
            useDTO.setIsActive(myGet("isActive", use));
            useDTO.setMouseUse(myGet("mouseUse", use));
            useDTO.setMouseUseKey(myGet("_mouseUse_key", use));
            useDTO.setUseDescription(myGet("useDescription", use));
            useDTO.setVersion(myGet("version", use));
            useDTO.setD1Caption(myGet("d1Caption", use));
            useDTO.setD2Caption(myGet("d2Caption", use));
            useDTO.setD3Caption(myGet("d3Caption", use));
            useDTO.setD4Caption(myGet("d4Caption", use));
            useDTO.setD5Caption(myGet("d5Caption", use));
            useDTO.setD6Caption(myGet("d6Caption", use));
            useDTO.setD7Caption(myGet("d7Caption", use));
            useDTO.setD8Caption(myGet("d8Caption", use));
            useDTO.setD9Caption(myGet("d9Caption", use));
            useDTO.setD10Caption(myGet("d10Caption", use));
            uses.add(useDTO);
        }
        return uses;
    }
    
    public String getMouseGenotypeByMouseKey(String mouseKey){
        String genotype = "";
        SortedMap[] results = executeJCMSQuery(genotypeByMouseKey + mouseKey).getRows();
        for(SortedMap theGenotype : results){
            genotype = genotype + myGet("labSymbol", theGenotype);
            genotype = genotype + " " + myGet("allele1", theGenotype);
            genotype = genotype + " " + myGet("allele2", theGenotype);
            if(!results[results.length - 1].equals(theGenotype)){
                genotype = genotype + ", ";
            }
        }
        return genotype;
    }
    
    public cvMouseUseDTO getCvMouseUseByUse(String mouseUse){
        cvMouseUseDTO useDTO = new cvMouseUseDTO();
        String queryString = cvMouseUseByUse + "'" + mouseUse + "'" + ";";
        SortedMap[] result = executeJCMSQuery(queryString).getRows();
        if(result.length != 0){
            SortedMap use = result[0];
            useDTO.setIsActive(myGet("isActive", use));
            useDTO.setMouseUse(myGet("mouseUse", use));
            useDTO.setMouseUseKey(myGet("_mouseUse_key", use));
            useDTO.setUseDescription(myGet("useDescription", use));
            useDTO.setVersion(myGet("version", use));
            useDTO.setD1Caption(myGet("d1Caption", use));
            useDTO.setD2Caption(myGet("d2Caption", use));
            useDTO.setD3Caption(myGet("d3Caption", use));
            useDTO.setD4Caption(myGet("d4Caption", use));
            useDTO.setD5Caption(myGet("d5Caption", use));
            useDTO.setD6Caption(myGet("d6Caption", use));
            useDTO.setD7Caption(myGet("d7Caption", use));
            useDTO.setD8Caption(myGet("d8Caption", use));
            useDTO.setD9Caption(myGet("d9Caption", use));
            useDTO.setD10Caption(myGet("d10Caption", use));
        }
        return useDTO;
    }
    
    public ArrayList<MouseUsageDTO> getMouseUsagesByMouseKey(String mouseKey){
        ArrayList<MouseUsageDTO> uses = new ArrayList<MouseUsageDTO>();
        String queryString = mouseUsesByMouseKey + mouseKey;
        SortedMap[] result = executeJCMSQuery(queryString).getRows();
        for(SortedMap use : result){
            MouseUsageDTO dto = new MouseUsageDTO();
            dto.setComment(myGet("comment", use));
            dto.setD1(myGet("D1", use));
            dto.setD10(myGet("D10", use));
            dto.setD2(myGet("D2", use));
            dto.setD3(myGet("D3", use));
            dto.setD4(myGet("D4", use));
            dto.setD5(myGet("D5", use));
            dto.setD6(myGet("D6", use));
            dto.setD7(myGet("D7", use));
            dto.setD8(myGet("D8", use));
            dto.setD9(myGet("D9", use));
            dto.setDone(myGet("done", use));
            dto.setMouseKey(myGet("_mouse_key", use));
            dto.setUsageKey(myGet("_usage_key", use));
            dto.setUse(myGet("use", use));
            dto.setUseAge(myGet("useAge", use));
            dto.setVersion(myGet("version", use));
            dto.setUseScheduleKey(myGet("_useSchedule_key", use));
            dto.getUseSchedule().setUseScheduleKey(myGet("_useSchedule_key", use));
            UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
            useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", use));
            dto.getUseSchedule().setUseScheduleTerm(useScheduleTerm);
            try{
                dto.setProjectedDate(convertStringToDate(myGet("projectedDate", use)));
            }
            catch(Exception e){
                System.out.println(e);
            }
            try{
                dto.setActualDate(convertStringToDate(myGet("actualDate", use)));
            }
            catch(Exception e){
                System.out.println(e);
            }
            uses.add(dto);
        }
        return uses;
    }
    
    public int addMouseUsage(MouseUsageDTO dto) throws Exception{
        int key = Integer.parseInt(getMaxValue("_usage_key", "MouseUsage")) + 1;
        String insertQuery = "INSERT INTO MouseUsage "
                + "(_usage_key, _mouse_key, _useSchedule_key, `use`, useAge, _plugDate_key, projectedDate, actualDate, comment, "
                + "D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, done, version) VALUES("
                //usage key
                + "?, " 
                //mouse key
                + "?, " 
                //use schedule key
                + "?, "
                //use name
                + "?, "
                //use age
                + "?, "
                //plug date key
                + "?, "
                //projected date
                + "?, "
                //actual date
                + "?, "
                //comment
                + "?, "
                //d1
                + "?, "
                //d2
                + "?, "
                //d3
                + "?, "
                //d4
                + "?, "
                //d5
                + "?, "
                //d6
                + "?, "
                //d7
                + "?, "
                //d8
                + "?, "
                //d9 
                + "?, "
                //d10
                + "?, "
                //done
                + "?, "
                //version
                + "1)";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(insertQuery);
        ps.setInt(1, key);
        ps.setInt(2, Integer.parseInt(dto.getMouseKey()));
        
        //use schedule key may be null...
        if(dto.getUseScheduleKey() == null || ("").equals(dto.getUseScheduleKey())){
            ps.setNull(3, java.sql.Types.INTEGER);
        }
        else{
            ps.setInt(3, Integer.parseInt(dto.getUseScheduleKey()));
        }
        
        ps.setString(4, dto.getUse());
        
        //use age may be null...
        if(dto.getUseAge() == null || dto.getUseAge().equals("")){
            ps.setNull(5, java.sql.Types.DOUBLE);
        }
        else{
            ps.setDouble(5, Double.parseDouble(dto.getUseAge()));
        }
        
        //plugdate key may be null...
        if(dto.getPlugDateKey() == null || ("").equals(dto.getPlugDateKey())){
            ps.setNull(6, java.sql.Types.INTEGER);
        }
        else{
            ps.setInt(6, Integer.parseInt(dto.getPlugDateKey()));
        }
        
        //projected date may be null...
        if(dto.getProjectedDate() == null){
            ps.setNull(7, java.sql.Types.DATE);
        }
        else{
            ps.setDate(7, new java.sql.Date(dto.getProjectedDate().getTime()));
        }
        
        //actual date may be null...
        if(dto.getActualDate() == null){
            ps.setNull(8, java.sql.Types.DATE);
        }
        else{
            ps.setDate(8, new java.sql.Date(dto.getActualDate().getTime()));
        }
        
        //comment may be null...
        if(dto.getComment() == null || dto.getComment().equals("")){
            ps.setNull(9, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(9, dto.getComment());
        }
                
        //d1 may be null...
        if(dto.getD1() == null || dto.getD1().equals("")){
            ps.setNull(10, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(10, dto.getD1());
        }
               
        //d2 may be null...
        if(dto.getD2() == null || dto.getD2().equals("")){
            ps.setNull(11, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(11, dto.getD2());
        }       
        
        //d2 may be null...
        if(dto.getD3() == null || dto.getD3().equals("")){
            ps.setNull(12, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(12, dto.getD3());
        }       
        
        //d2 may be null...
        if(dto.getD4() == null || dto.getD4().equals("")){
            ps.setNull(13, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(13, dto.getD4());
        }       
        
        //d2 may be null...
        if(dto.getD5() == null || dto.getD5().equals("")){
            ps.setNull(14, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(14, dto.getD5());
        }       
        
        //d2 may be null...
        if(dto.getD6() == null || dto.getD6().equals("")){
            ps.setNull(15, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(15, dto.getD6());
        }       
        
        //d2 may be null...
        if(dto.getD7() == null || dto.getD7().equals("")){
            ps.setNull(16, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(16, dto.getD7());
        }       
        
        //d2 may be null...
        if(dto.getD8() == null || dto.getD8().equals("")){
            ps.setNull(17, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(17, dto.getD8());
        }       
        
        //d2 may be null...
        if(dto.getD9() == null || dto.getD9().equals("")){
            ps.setNull(18, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(18, dto.getD9());
        }       
        
        //d2 may be null...
        if(dto.getD10() == null || dto.getD10().equals("")){
            ps.setNull(19, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(19, dto.getD10());
        }
        
        System.out.println(dto.getDone());
        //done, either -1 or 0...
        if(dto.getDone().equalsIgnoreCase("true")){
            ps.setInt(20, -1);
        }
        else{
            ps.setInt(20, 0);
        }
       
        this.executePreparedStatementUpdate(ps);
        return key;
    }
    
    public boolean plugDateReferenced(String plugDateKey){
        String query = "SELECT * FROM MouseUsage WHERE _plugDate_key = " + plugDateKey;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        return false;
    }
    
    public void editMouseUsage(MouseUsageDTO dto) throws Exception{
        String updateQuery = "UPDATE MouseUsage SET "
                + "`use` = ?, "
                + "useAge = ?, "
                + "`done` = ?, "
                + "comment = ?, "
                + "D1 = ?, "
                + "D2 = ?, "
                + "D3 = ?, "
                + "D4 = ?, "
                + "D5 = ?, "
                + "D6 = ?, "
                + "D7 = ?, "
                + "D8 = ?, "
                + "D9 = ?, "
                + "D10 = ?, "
                + "actualDate = ?, "
                + "projectedDate = ?, "
                + "version = version + 1 "
                + "WHERE _usage_key = ?;";
        
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(updateQuery);
        ps.setString(1, dto.getUse());
        
        //use age may be null...
        if(dto.getUseAge() == null || dto.getUseAge().equals("")){
            ps.setNull(2, java.sql.Types.DOUBLE);
        }
        else{
            ps.setDouble(2, Double.parseDouble(dto.getUseAge()));
        }
        
        if(dto.getDoneForInsert().equals("0")){
            ps.setInt(3, 0);
        }
        else{
            ps.setInt(3, -1);
        }
        
        //comment may be null...
        if(dto.getComment() == null || dto.getComment().equals("")){
            ps.setNull(4, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(4, dto.getComment());
        }
        
        //d1 may be null...
        if(dto.getD1() == null || dto.getD1().equals("")){
            ps.setNull(5, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(5, dto.getD1());
        }
               
        //d2 may be null...
        if(dto.getD2() == null || dto.getD2().equals("")){
            ps.setNull(6, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(6, dto.getD2());
        }       
        
        //d2 may be null...
        if(dto.getD3() == null || dto.getD3().equals("")){
            ps.setNull(7, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(7, dto.getD3());
        }       
        
        //d2 may be null...
        if(dto.getD4() == null || dto.getD4().equals("")){
            ps.setNull(8, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(8, dto.getD4());
        }       
        
        //d2 may be null...
        if(dto.getD5() == null || dto.getD5().equals("")){
            ps.setNull(9, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(9, dto.getD5());
        }       
        
        //d2 may be null...
        if(dto.getD6() == null || dto.getD6().equals("")){
            ps.setNull(10, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(10, dto.getD6());
        }       
        
        //d2 may be null...
        if(dto.getD7() == null || dto.getD7().equals("")){
            ps.setNull(11, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(11, dto.getD7());
        }       
        
        //d2 may be null...
        if(dto.getD8() == null || dto.getD8().equals("")){
            ps.setNull(12, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(12, dto.getD8());
        }       
        
        //d2 may be null...
        if(dto.getD9() == null || dto.getD9().equals("")){
            ps.setNull(13, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(13, dto.getD9());
        }       
        
        //d2 may be null...
        if(dto.getD10() == null || dto.getD10().equals("")){
            ps.setNull(14, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(14, dto.getD10());
        }
        
        //actual date may be null...
        if(dto.getActualDate() == null){
            ps.setNull(15, java.sql.Types.DATE);
        }
        else{
            ps.setDate(15, new java.sql.Date(dto.getActualDate().getTime()));
        }
        
        //projected date may be null...
        if(dto.getProjectedDate() == null){
            ps.setNull(16, java.sql.Types.DATE);
        }
        else{
            ps.setDate(16, new java.sql.Date(dto.getProjectedDate().getTime()));
        }
        
        ps.setInt(17, Integer.parseInt(dto.getUsageKey()));
        this.executePreparedStatementUpdate(ps);
    }
    
    public void deleteMouseUsage(String usageKey) throws Exception{
        String deleteQuery = "DELETE FROM MouseUsage WHERE _usage_key = " + usageKey + ";";
        executeJCMSUpdate(deleteQuery);
    }
    
    public ArrayList<MouseUseDTO> getMouseUses() {
        ArrayList<MouseUseDTO> dtoList = new ArrayList<MouseUseDTO> ();
        MouseUseDTO dto = null;
        Result result = executeJCMSQuery(cvMouseUse);
        if (result != null) {
            for (SortedMap row : result.getRows()) {
                dto = this.createMouseUseDTO(row);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    private MouseUseDTO createMouseUseDTO(SortedMap row) {
        MouseUseDTO dto = new MouseUseDTO();
        dto.setMouseUse_key(myGet("_mouseUse_key", row));
        dto.setMouseUse(myGet("mouseUse", row));
        dto.setUseDescription(myGet("useDescription", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("isActive", row)));
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

    public int saveMouseUse(MouseUseDTO dto) throws SQLException {
        if (dto.isInsert()) {
            return this.insertMouseUse(dto);
        } else {
            return this.updateMouseUse(dto);
        }
    }
    
    private Integer insertMouseUse(MouseUseDTO dto) throws SQLException {
        
        String cmd = "INSERT INTO cv_MouseUse " 
            + "\n (mouseUse, useDescription, version, isActive, d1Caption, d2Caption, d3Caption, d4Caption, d5Caption, d6Caption, d7Caption, d8Caption, d9Caption, d10Caption ) "
            + "\n VALUES (?"//+ varcharParser(dto.getMouseUse().trim())
            + ", ?"//+ varcharParser(dto.getUseDescription().trim())
            + ", 1"//+ version.toString() 
            + ", ?"//+ (dto.getIsActive() ? -1 : 0)
            + ", ?"//+ varcharParser(dto.getD1Caption().trim())
            + ", ?"//+ varcharParser(dto.getD2Caption().trim())
            + ", ?"//+ varcharParser(dto.getD3Caption().trim())
            + ", ?"//+ varcharParser(dto.getD4Caption().trim())
            + ", ?"//+ varcharParser(dto.getD5Caption().trim())
            + ", ?"//+ varcharParser(dto.getD6Caption().trim())
            + ", ?"//+ varcharParser(dto.getD7Caption().trim())
            + ", ?"//+ varcharParser(dto.getD8Caption().trim())
            + ", ?"//+ varcharParser(dto.getD9Caption().trim())
            + ", ?"//+ varcharParser(dto.getD10Caption().trim())
            + " )" ;
        
        
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        ps.setString(1, dto.getMouseUse());
        
        if(dto.getUseDescription() == null || dto.getUseDescription().trim().equals("")){
            ps.setNull(2, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(2, dto.getUseDescription().trim());
        }     
        if(dto.getIsActive()){
            ps.setInt(3, -1);
        }
        else{
            ps.setInt(3, 0);
        }
        if(dto.getD1Caption() == null || dto.getD1Caption().trim().equals("")){
            ps.setNull(4, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(4, dto.getD1Caption().trim());
        }     
        if(dto.getD2Caption() == null || dto.getD2Caption().trim().equals("")){
            ps.setNull(5, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(5, dto.getD2Caption().trim());
        }        
        if(dto.getD3Caption() == null || dto.getD3Caption().trim().equals("")){
            ps.setNull(6, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(6, dto.getD3Caption().trim());
        }  
        if(dto.getD4Caption() == null || dto.getD4Caption().trim().equals("")){
            ps.setNull(7, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(7, dto.getD4Caption().trim());
        } 
        if(dto.getD5Caption() == null || dto.getD5Caption().trim().equals("")){
            ps.setNull(8, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(8, dto.getD5Caption().trim());
        }   
        if(dto.getD6Caption() == null || dto.getD6Caption().trim().equals("")){
            ps.setNull(9, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(9, dto.getD6Caption().trim());
        }   
        if(dto.getD7Caption() == null || dto.getD7Caption().trim().equals("")){
            ps.setNull(10, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(10, dto.getD7Caption().trim());
        }   
        if(dto.getD8Caption() == null || dto.getD8Caption().trim().equals("")){
            ps.setNull(11, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(11, dto.getD8Caption().trim());
        }   
        if(dto.getD9Caption() == null || dto.getD9Caption().trim().equals("")){
            ps.setNull(12, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(12, dto.getD9Caption().trim());
        }   
        if(dto.getD10Caption() == null || dto.getD10Caption().trim().equals("")){
            ps.setNull(13, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(13, dto.getD10Caption().trim());
        } 
        
        this.executePreparedStatementUpdate(ps);

        Integer key = 0;
        Result result = this.executeJCMSQuery("SELECT MAX(_mouseUse_key) as primaryKey FROM cv_MouseUse");
        for (SortedMap row : result.getRows()) {
            key = Integer.parseInt(this.myGet("primaryKey", row));
        }
        return key;
    }

    private int updateMouseUse(MouseUseDTO dto) throws SQLException {
        
        String cmd = "UPDATE cv_MouseUse SET " 
            + " mouseUse = ?"// + varcharParser(dto.getMouseUse().trim())
            + ", useDescription = ?"// + varcharParser(dto.getUseDescription().trim())
            + ", isActive = ?"
            + ", d1Caption = ?"// + varcharParser(dto.getD1Caption().trim())
            + ", d2Caption = ?"// + varcharParser(dto.getD2Caption().trim())
            + ", d3Caption = ?"// + varcharParser(dto.getD3Caption().trim())
            + ", d4Caption = ?"// + varcharParser(dto.getD4Caption().trim())
            + ", d5Caption = ?"// + varcharParser(dto.getD5Caption().trim())
            + ", d6Caption = ?"// + varcharParser(dto.getD6Caption().trim())
            + ", d7Caption = ?"// + varcharParser(dto.getD7Caption().trim())
            + ", d8Caption = ?"// + varcharParser(dto.getD8Caption().trim())
            + ", d9Caption = ?"// + varcharParser(dto.getD9Caption().trim())
            + ", d10Caption = ?"// + varcharParser(dto.getD10Caption().trim())
            + ", version = version + 1"
            + "\n WHERE _mouseUse_key = ?;";//+ dto.getMouseUse_key() ;
        
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(cmd);
        
        ps.setString(1, dto.getMouseUse());
        
        if(dto.getUseDescription() == null || dto.getUseDescription().trim().equals("")){
            ps.setNull(2, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(2, dto.getUseDescription().trim());
        }     
        if(dto.getIsActive()){
            ps.setInt(3, -1);
        }
        else{
            ps.setInt(3, 0);
        }
        if(dto.getD1Caption() == null || dto.getD1Caption().trim().equals("")){
            ps.setNull(4, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(4, dto.getD1Caption().trim());
        }     
        if(dto.getD2Caption() == null || dto.getD2Caption().trim().equals("")){
            ps.setNull(5, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(5, dto.getD2Caption().trim());
        }        
        if(dto.getD3Caption() == null || dto.getD3Caption().trim().equals("")){
            ps.setNull(6, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(6, dto.getD3Caption().trim());
        }  
        if(dto.getD4Caption() == null || dto.getD4Caption().trim().equals("")){
            ps.setNull(7, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(7, dto.getD4Caption().trim());
        } 
        if(dto.getD5Caption() == null || dto.getD5Caption().trim().equals("")){
            ps.setNull(8, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(8, dto.getD5Caption().trim());
        }   
        if(dto.getD6Caption() == null || dto.getD6Caption().trim().equals("")){
            ps.setNull(9, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(9, dto.getD6Caption().trim());
        }   
        if(dto.getD7Caption() == null || dto.getD7Caption().trim().equals("")){
            ps.setNull(10, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(10, dto.getD7Caption().trim());
        }   
        if(dto.getD8Caption() == null || dto.getD8Caption().trim().equals("")){
            ps.setNull(11, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(11, dto.getD8Caption().trim());
        }   
        if(dto.getD9Caption() == null || dto.getD9Caption().trim().equals("")){
            ps.setNull(12, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(12, dto.getD9Caption().trim());
        }   
        if(dto.getD10Caption() == null || dto.getD10Caption().trim().equals("")){
            ps.setNull(13, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(13, dto.getD10Caption().trim());
        }
        ps.setInt(14, Integer.parseInt(dto.getMouseUse_key()));
        
        return this.executePreparedStatementUpdate(ps);
    }    
    
    public Integer deleteMouseUse(MouseUseDTO dto) throws SQLException {
        String cmd = "DELETE FROM cv_MouseUse WHERE _mouseUse_key = "+ dto.getMouseUse_key() ;  
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
}
