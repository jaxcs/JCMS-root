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
import java.sql.PreparedStatement;
import java.sql.Connection;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.UseScheduleListDTO;
import jcms.integrationtier.dto.UseScheduleDTO;
import jcms.integrationtier.dto.UseScheduleStartEventDTO;
import jcms.integrationtier.dto.PlugDateDTO;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.dto.cvMouseUseDTO;
import jcms.integrationtier.dto.UseScheduleSearchDTO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author mkamato
 */
public class UseScheduleDAO extends MySQLDAO {
    
    MouseUseDAO muDAO = new MouseUseDAO();
    
    public Integer insertUseScheduleTerm(UseScheduleTermDTO dto) throws Exception {
        String query = "INSERT INTO `UseScheduleTerm` (`useScheduleTermName`, `useScheduleTermDetail`, `_Workgroup_key`, "
                + "`_useScheduleStartEvent_key`, `isActive`, `versionNum`, `color`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);"; 
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, dto.getUseScheduleTermName());
        if(("").equals(dto.getUseScheduleTermDetail())){
            ps.setNull(2, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(2, dto.getUseScheduleTermDetail());
        }
        ps.setInt(3, Integer.parseInt(dto.getWorkgroupKey()));
        ps.setInt(4, Integer.parseInt(dto.getUseScheduleStartEventKey()));
        if(dto.isIsActive()){
            ps.setInt(5, -1);
        }
        else{
            ps.setInt(5,0);
        }
        if(("").equals(dto.getVersionNum())){
            ps.setNull(6, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(6, dto.getVersionNum());
        }
        ps.setString(7, dto.getColor());
        this.executePreparedStatementUpdate(ps);
        return new Integer(this.getMaxValue("_useScheduleTerm_key", "UseScheduleTerm"));
    }
    
    public Integer insertUseScheduleList(UseScheduleListDTO dto) throws Exception{
        String query = "INSERT INTO `UseScheduleList` (`_useScheduleTerm_key`, `_mouseUse_key`, `daysPostEvent`) "
                + "VALUES (" 
                + numberParser(dto.getUseScheduleTerm().getUseScheduleTermKey()) + ", " 
                + numberParser(dto.getUse().getMouseUseKey()) + ", "
                + numberParser(dto.getDaysAfterStart().toString()) 
                + ");";
        this.executeJCMSUpdate(query);
        return new Integer(this.getMaxValue("_useScheduleList_key", "UseScheduleList"));
    }
    
    public Integer insertUseSchedule(UseScheduleDTO dto, PlugDateDTO plug) throws Exception {
        //add use schedule
        String query = "";
        /*
         * Two possibilities, you need a plug key, or you don't
         */
        if(plug == null){
            query = "INSERT INTO `UseSchedule` (`_mouse_key`, `_useScheduleTerm_key`, `startDate`, `comment`) "
                    + "VALUES (" 
                    + numberParser(dto.getMouse().getMouse_key()) + ", " 
                    + numberParser(dto.getUseScheduleTerm().getUseScheduleTermKey()) + ", '"
                    + formatAsMySQLDate(dto.getStartDate()) + "', "
                    + varcharParser(dto.getComment())
                    + ");";
        }
        else{
            query = "INSERT INTO `UseSchedule` (`_mouse_key`, `_useScheduleTerm_key`, `_plugDate_key`, `startDate`, `comment`) "
                    + "VALUES (" 
                    + numberParser(dto.getMouse().getMouse_key()) + ", " 
                    + numberParser(dto.getUseScheduleTerm().getUseScheduleTermKey()) + ", "
                    + numberParser(plug.getPlugDateKey()) + ", '"
                    + formatAsMySQLDate(dto.getStartDate()) + "', "
                    + varcharParser(dto.getComment())
                    + ");";
        }
        this.executeJCMSUpdate(query);
        Integer useScheduleKey = new Integer(this.getMaxValue("_useSchedule_key", "UseSchedule"));
        dto.setUseScheduleKey(useScheduleKey.toString());
        //add mouse uses
        for(UseScheduleListDTO pulDTO : dto.getUseScheduleTerm().getUses()){
            MouseUsageDTO muDTO = new MouseUsageDTO();
            muDTO.setUse(pulDTO.getUse().getMouseUse());
            /*
             * Have to figure out the date for the mouse usage, calculate based
             * on either plug date, birthdate, or calendar date depending on type 
             * of use schedule
             */
            Calendar cal = Calendar.getInstance();
            cal.setTime(dto.getStartDate());
            cal.add(Calendar.DAY_OF_MONTH, pulDTO.getDaysAfterStart().intValue());
            Date useDate = cal.getTime();
            muDTO.setProjectedDate(useDate);
            muDTO.setMouseKey(dto.getMouse().getMouse_key());
            muDTO.setUseScheduleKey(dto.getUseScheduleKey());
            //birth date
            if(pulDTO.getUseScheduleTerm().getUseScheduleStartEventKey().equals("1")){                
                System.out.println("Birth date based use schedule...");
                muDTO.setUseAge(pulDTO.getDaysAfterStart().toString());
                muDAO.addMouseUsage(muDTO);
            }
            //plug date
            else if(dto.getUseScheduleTerm().getUseScheduleStartEventKey().equals("2")){
                System.out.println("Plug date based use schedule...");                
                //to get the use age, add a day to the birthdate until it equals the projected date.
                Calendar bCal = Calendar.getInstance();
                bCal.setTime(dto.getMouse().getBirthDate());
                int age = 0;
                while(bCal.before(cal)){
                    age++;
                    bCal.add(Calendar.DAY_OF_MONTH, 1);
                }
                muDTO.setUseAge(new Integer(age).toString());
                muDTO.setPlugDateKey(plug.getPlugDateKey());
                muDAO.addMouseUsage(muDTO);
            }
            //calendar date
            else{
                System.out.println("Calendar based use schedule...");
            }
        }
        return new Integer(this.getMaxValue("_useSchedule_key", "UseSchedule"));
    }
    
    public void updateUseScheduleTerm(UseScheduleTermDTO dto) throws Exception{
        String query = "UPDATE `UseScheduleTerm` SET "
                + "`useScheduleTermName` = ?, "
                + "`useScheduleTermDetail` = ?, "
                + "`_Workgroup_key` = ?, "
                + "`_useScheduleStartEvent_key` = ?, "
                + "`versionNum` = ?, "
                + "`color` = ?, "
                + "`isActive` = ? "
                + "WHERE _useScheduleTerm_key = ?;";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, dto.getUseScheduleTermName());
        if(("").equals(dto.getUseScheduleTermDetail())){
            ps.setNull(2, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(2, dto.getUseScheduleTermDetail());
        }
        ps.setInt(3, Integer.parseInt(dto.getWorkgroupKey()));
        ps.setInt(4, Integer.parseInt(dto.getUseScheduleStartEventKey()));
        if(("").equals(dto.getVersionNum())){
            ps.setNull(5, java.sql.Types.VARCHAR);
        }
        else{
            ps.setString(5, dto.getVersionNum());
        }
        ps.setString(6, dto.getColor());
        if(dto.isIsActive()){
            ps.setInt(7, -1);
        }
        else{
            ps.setInt(7, 0);
        }
        ps.setInt(8, Integer.parseInt(dto.getUseScheduleTermKey()));        
        this.executePreparedStatementUpdate(ps);
    }
    
    public void updateUseScheduleList(UseScheduleListDTO dto) throws Exception{
        String query = "UPDATE `UseScheduleList` SET "
                + "`_useScheduleTerm_key` = " + numberParser(dto.getUseScheduleTerm().getUseScheduleTermKey()) + ", " 
                + "`_mouseUse_key` = " + numberParser(dto.getUse().getMouseUseKey()) + ", "
                + "`daysPostEvent` = " + numberParser(dto.getDaysAfterStart().toString()) 
                + " WHERE _useScheduleList_key = " + dto.getUseScheduleListKey() + ";";
        this.executeJCMSUpdate(query);  
    }
    
    public void updateUseSchedule(UseScheduleDTO dto) throws Exception{
        String query = "UPDATE `UseSchedule` SET "
                + "`_mouse_key` = " + numberParser(dto.getMouse().getMouse_key()) + ", " 
                + "`_useScheduleTerm_key` = " + numberParser(dto.getUseScheduleTerm().getUseScheduleTermKey()) + ", "
                + " `startDate` = '" + formatAsMySQLDate(dto.getStartDate()) + "', "
                + " `done` = " + (dto.getDone() ? "-1" : "0") + ", "
                + "`comment` = " + varcharParser(dto.getComment())
                + " WHERE _useSchedule_key = " + dto.getUseScheduleKey();
        this.executeJCMSUpdate(query);
    }
        
    public void deleteUseScheduleList(UseScheduleListDTO dto) throws Exception {
        String query = "DELETE FROM `UseScheduleList` WHERE _useScheduleList_key = " + dto.getUseScheduleListKey();
        this.executeJCMSUpdate(query);
    }
    
    //need to delete the all associated MouseUsages and then the use schedule
    public void deleteUseSchedule(MouseDTO mouse, UseScheduleTermDTO dto) throws Exception {
        //get the use Schedule information from the mouse and use schedule type
        String deleteUsageQuery = "DELETE MouseUsage FROM MouseUsage "
                + "JOIN UseSchedule "
                + "ON MouseUsage._useSchedule_key = UseSchedule._useSchedule_key "
                + "WHERE UseSchedule._useScheduleTerm_key = " + dto.getUseScheduleTermKey()
                + " AND UseSchedule.done = 0"
                + " AND UseSchedule._mouse_key = " + mouse.getMouse_key();
        String deleteUseScheduleQuery = "DELETE FROM UseSchedule "
                + "WHERE UseSchedule._useScheduleTerm_key = " + dto.getUseScheduleTermKey()
                + " AND UseSchedule.done = 0"
                + " AND UseSchedule._mouse_key = " + mouse.getMouse_key();
        //delete associated usages
        this.executeJCMSUpdate(deleteUsageQuery);
        //delete associated UseSchedule
        this.executeJCMSUpdate(deleteUseScheduleQuery);
    }    
    
    //need to delete the all associated MouseUsages and then the use schedule
    public void deletePDUseSchedule(PlugDateDTO plug, UseScheduleTermDTO dto) throws Exception {
        //get the use Schedule information from the mouse and use schedule type
        String deleteUsageQuery = "DELETE MouseUsage FROM MouseUsage "
                + "JOIN UseSchedule "
                + "ON MouseUsage._useSchedule_key = UseSchedule._useSchedule_key "
                + "WHERE UseSchedule._useScheduleTerm_key = " + dto.getUseScheduleTermKey()
                + " AND UseSchedule.done = 0"
                + " AND UseSchedule._plugDate_key = " + plug.getPlugDateKey();
        String deleteUseScheduleQuery = "DELETE FROM UseSchedule "
                + "WHERE UseSchedule._useScheduleTerm_key = " + dto.getUseScheduleTermKey()
                + " AND UseSchedule.done = 0"
                + " AND UseSchedule._plugDate_key = " + plug.getPlugDateKey();
        //delete associated usages
        this.executeJCMSUpdate(deleteUsageQuery);
        //delete associated UseSchedule
        this.executeJCMSUpdate(deleteUseScheduleQuery);
    }
    
    /*
     * get all the use schedule terms, and the associated uses
     */
    public ArrayList<UseScheduleTermDTO> getUseScheduleTerms(ArrayList<WorkgroupEntity> wgs){
        String wgFilter = this.buildWorkgroupFilter("UseScheduleTerm", wgs, false);
        ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
        String query = "SELECT UseScheduleTerm._useScheduleTerm_key, UseScheduleTerm._useScheduleStartEvent_key, "
                + "UseScheduleTerm._Workgroup_key, UseScheduleTerm.color, "
                + "UseScheduleTerm.useScheduleTermName,  UseScheduleTerm.useScheduleTermDetail, "
                + "UseScheduleTerm.versionNum, UseScheduleTerm.isActive AS useScheduleTermActive, "
                + "UseScheduleList._useScheduleList_key, UseScheduleList.daysPostEvent, cv_MouseUse.mouseUse, cv_MouseUse.useDescription, "
                + "cv_MouseUse._mouseUse_key, cv_MouseUse.d1Caption, cv_MouseUse.d2Caption, cv_MouseUse.d3Caption, cv_MouseUse.d4Caption, "
                + "cv_MouseUse.d5Caption, cv_MouseUse.d6Caption, cv_MouseUse.d7Caption, cv_MouseUse.d8Caption, cv_MouseUse.d9Caption, "
                + "cv_MouseUse.d10Caption "
                + "FROM UseScheduleTerm "
                + "LEFT JOIN UseScheduleList "
                + "ON UseScheduleTerm._useScheduleTerm_key = UseScheduleList._useScheduleTerm_key "
                + "LEFT JOIN cv_MouseUse "
                + "ON UseScheduleList._mouseUse_key = cv_MouseUse._mouseUse_key "
                + "WHERE " + wgFilter;
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        String lastUseScheduleTermAddedKey = "0";
        UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
        for(SortedMap result : results){
            String currentUseScheduleTermKey = myGet("_useScheduleTerm_key", result);
            /*
             * two cases: 
             * 1. case one you're adding a new useScheduleTerm to the useScheduleTerm list
             * 2. case two you're adding another mouse usage to the useScheduleTerm
             */
            if(!lastUseScheduleTermAddedKey.equals(currentUseScheduleTermKey)){
                lastUseScheduleTermAddedKey = currentUseScheduleTermKey;
                useScheduleTerm = new UseScheduleTermDTO();
                //set useScheduleTerm items
                //System.out.println("Case 1: Use Schedule term:" + myGet("useScheduleTermName", result));
                if(myGet("useScheduleTermActive", result).equalsIgnoreCase("0")){
                    useScheduleTerm.setIsActive(false);
                }
                else{
                    useScheduleTerm.setIsActive(true);
                }
                useScheduleTerm.setUseScheduleTermDetail(myGet("useScheduleTermDetail", result));
                useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", result));
                useScheduleTerm.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", result));
                useScheduleTerm.setVersionNum(myGet("versionNum", result));
                useScheduleTerm.setWorkgroupKey(myGet("_Workgroup_key", result));
                useScheduleTerm.setUseScheduleTermKey(myGet("_useScheduleTerm_key", result));
                useScheduleTerm.setColor(myGet("color", result));
                WorkgroupEntity workgroup = null;
                for(WorkgroupEntity we : wgs){
                    if(we.getWorkgroupkey().toString().equals(myGet("_Workgroup_key", result))){
                        workgroup = we;                        
                    }
                }
                useScheduleTerm.setWorkgroup(workgroup);
                useScheduleTerms.add(useScheduleTerm);
            }
            if(!myGet("_mouseUse_key", result).equals("")){
                //add the links
                UseScheduleListDTO dto = new UseScheduleListDTO();
                dto.setDaysAfterStart(new Integer(myGet("daysPostEvent", result)));
                dto.setUseScheduleTerm(useScheduleTerm);
                dto.setUseScheduleListKey(myGet("_useScheduleList_key", result));
                dto.setUse(muDAO.getCvMouseUseByKey(myGet("_mouseUse_key", result)));
                useScheduleTerm.getUses().add(dto);
            }
        }
        return useScheduleTerms;
    }
    
    //get active birthdate use schedule terms
    public ArrayList<UseScheduleTermDTO> getActiveBirthdateUseScheduleTerms(ArrayList<WorkgroupEntity> wgs){
        String wgFilter = this.buildWorkgroupFilter("UseScheduleTerm", wgs, false);
        //System.out.println("Active birthdate use schedule items workgroup filter: " + wgFilter);
        ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
        String query = "SELECT UseScheduleTerm._useScheduleTerm_key, UseScheduleList._useScheduleList_key,  "
                + "UseScheduleTerm._Workgroup_key, UseScheduleTerm.color, UseScheduleTerm.useScheduleTermName, "
                + "UseScheduleTerm.useScheduleTermDetail,  UseScheduleTerm.versionNum, "
                + "UseScheduleTerm.isActive AS useScheduleTermActive, UseScheduleList._useScheduleList_key, "
                + "UseScheduleList.daysPostEvent, cv_MouseUse.mouseUse, cv_MouseUse.useDescription, "
                + "cv_MouseUse._mouseUse_key, cv_MouseUse.d1Caption, cv_MouseUse.d2Caption, cv_MouseUse.d3Caption, "
                + "cv_MouseUse.d4Caption, cv_MouseUse.d5Caption, cv_MouseUse.d6Caption, cv_MouseUse.d7Caption, "
                + "cv_MouseUse.d8Caption, cv_MouseUse.d9Caption, cv_MouseUse.d10Caption "
                + "FROM UseScheduleTerm "
                + "LEFT JOIN UseScheduleList "
                + "ON UseScheduleTerm._useScheduleTerm_key = UseScheduleList._useScheduleTerm_key "
                + "LEFT JOIN cv_MouseUse "
                + "ON UseScheduleList._mouseUse_key = cv_MouseUse._mouseUse_key "
                + "JOIN cv_UseScheduleStartEvent "
                + "ON UseScheduleTerm._useScheduleStartEvent_key = cv_UseScheduleStartEvent._useScheduleStartEvent_key "
                + "WHERE " + wgFilter + " AND UseScheduleTerm.isActive != 0 "
                + "AND (cv_UseScheduleStartEvent.useScheduleStartEvent = 'Birthdate' OR cv_UseScheduleStartEvent.useScheduleStartEvent = 'Calendar Date');";
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        String lastUseScheduleTermAddedKey = "0";
        UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
        for(SortedMap result : results){
            String currentUseScheduleTermKey = myGet("_useScheduleTerm_key", result);
            //System.out.println("Active birth date use schedule term key:" + myGet("_useScheduleTerm_key", result));
            /*
             * two cases: 
             * 1. case one you're adding a new UseScheduleTerm to the UseScheduleTerm list
             * 2. case two you're adding another mouse usage to the UseScheduleTerm
             */
            if(!lastUseScheduleTermAddedKey.equals(currentUseScheduleTermKey)){
                lastUseScheduleTermAddedKey = currentUseScheduleTermKey;
                useScheduleTerm = new UseScheduleTermDTO();
                //set use schedule term
                //System.out.println("Case 1:Active birth date use schedule term:" + myGet("useScheduleTermName", result));
                if(myGet("useScheduleTerm", result).equalsIgnoreCase("0")){
                    useScheduleTerm.setIsActive(false);
                }
                else{
                    useScheduleTerm.setIsActive(true);
                }
                useScheduleTerm.setUseScheduleTermDetail(myGet("useScheduleTermDetail", result));
                useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", result));
                useScheduleTerm.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", result));
                useScheduleTerm.setVersionNum(myGet("versionNum", result));
                useScheduleTerm.setWorkgroupKey(myGet("_Workgroup_key", result));
                useScheduleTerm.setUseScheduleTermKey(myGet("_useScheduleTerm_key", result));
                useScheduleTerm.setColor(myGet("color", result));
                useScheduleTerms.add(useScheduleTerm);
            }
            if(!myGet("_mouseUse_key", result).equals("")){
                //add the links
                UseScheduleListDTO dto = new UseScheduleListDTO();
                dto.setDaysAfterStart(new Integer(myGet("daysPostEvent", result)));
                dto.setUseScheduleTerm(useScheduleTerm);
                dto.setUseScheduleListKey(myGet("_useScheduleList_key", result));
                dto.setUse(muDAO.getCvMouseUseByKey(myGet("_mouseUse_key", result)));
                //System.out.println("Case 2: use schedule term key:" + myGet("_useScheduleList_key", result));
                useScheduleTerm.getUses().add(dto);
            }
        }
        return useScheduleTerms;
    }
    
    //get active plugdate use schedule terms
    public ArrayList<UseScheduleTermDTO> getActivePlugDateUseScheduleTerms(ArrayList<WorkgroupEntity> wgs){
        String wgFilter = this.buildWorkgroupFilter("UseScheduleTerm", wgs, false);
        //System.out.println(wgFilter);
        ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
        String query = "SELECT UseScheduleTerm._useScheduleTerm_key, UseScheduleList._useScheduleList_key,  UseScheduleTerm._Workgroup_key, UseScheduleTerm.color, "
                + " UseScheduleTerm.useScheduleTermName, UseScheduleTerm.useScheduleTermDetail,  UseScheduleTerm.versionNum, UseScheduleTerm.isActive AS useScheduleTermActive, "
                + "UseScheduleList._useScheduleList_key, UseScheduleList.daysPostEvent, cv_MouseUse.mouseUse, cv_MouseUse.useDescription, "
                + "cv_MouseUse._mouseUse_key, cv_MouseUse.d1Caption, cv_MouseUse.d2Caption, cv_MouseUse.d3Caption, cv_MouseUse.d4Caption, "
                + "cv_MouseUse.d5Caption, cv_MouseUse.d6Caption, cv_MouseUse.d7Caption, cv_MouseUse.d8Caption, cv_MouseUse.d9Caption, "
                + "cv_MouseUse.d10Caption "
                + "FROM UseScheduleTerm "
                + "LEFT JOIN UseScheduleList "
                + "ON UseScheduleTerm._useScheduleTerm_key = UseScheduleList._useScheduleTerm_key "
                + "LEFT JOIN cv_MouseUse "
                + "ON UseScheduleList._mouseUse_key = cv_MouseUse._mouseUse_key "
                + "JOIN cv_UseScheduleStartEvent "
                + "ON UseScheduleTerm._useScheduleStartEvent_key = cv_UseScheduleStartEvent._useScheduleStartEvent_key "
                + "WHERE " + wgFilter + " AND UseScheduleTerm.isActive != 0 "
                + "AND cv_UseScheduleStartEvent.useScheduleStartEvent = 'Plug Date';";
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        String lastUseScheduleTermAddedKey = "0";
        UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
        for(SortedMap result : results){
            String currentUseScheduleTermKey = myGet("_useScheduleTerm_key", result);
            /*
             * two cases: 
             * 1. case one you're adding a new UseScheduleTerm to the UseScheduleTerm list
             * 2. case two you're adding another mouse usage to the UseScheduleTerm
             */
            if(!lastUseScheduleTermAddedKey.equals(currentUseScheduleTermKey)){
                lastUseScheduleTermAddedKey = currentUseScheduleTermKey;
                useScheduleTerm = new UseScheduleTermDTO();
                //set use schedule term
                //System.out.println("Case 1: Active plug date term:" + myGet("useScheduleTermName", result));
                if(myGet("useScheduleTerm", result).equalsIgnoreCase("0")){
                    useScheduleTerm.setIsActive(false);
                }
                else{
                    useScheduleTerm.setIsActive(true);
                }
                useScheduleTerm.setUseScheduleTermDetail(myGet("useScheduleTermDetail", result));
                useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", result));
                useScheduleTerm.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", result));
                useScheduleTerm.setVersionNum(myGet("versionNum", result));
                useScheduleTerm.setWorkgroupKey(myGet("_Workgroup_key", result));
                useScheduleTerm.setUseScheduleTermKey(myGet("_useScheduleTerm_key", result));
                useScheduleTerm.setColor(myGet("color", result));
                useScheduleTerms.add(useScheduleTerm);
            }
            if(!myGet("_mouseUse_key", result).equals("")){
                //add the links
                UseScheduleListDTO dto = new UseScheduleListDTO();
                dto.setDaysAfterStart(new Integer(myGet("daysPostEvent", result)));
                dto.setUseScheduleTerm(useScheduleTerm);
                dto.setUseScheduleListKey(myGet("_useScheduleList_key", result));
                dto.setUse(muDAO.getCvMouseUseByKey(myGet("_mouseUse_key", result)));
                useScheduleTerm.getUses().add(dto);
            }
        }
        return useScheduleTerms;
    }
        
    //get Single use schedule term based on use schedule term key
    public UseScheduleTermDTO getUseScheduleTerm(String useScheduleTermKey){
        String query = "SELECT UseScheduleTerm._useScheduleTerm_key, UseScheduleTerm._useScheduleStartEvent_key,  UseScheduleTerm._Workgroup_key, UseScheduleTerm.color, "
                + " UseScheduleTerm.useScheduleTermName,  UseScheduleTerm.useScheduleTermDetail,  UseScheduleTerm.versionNum, UseScheduleTerm.isActive AS useScheduleTermActive, "
                + "UseScheduleList._useScheduleList_key, UseScheduleList.daysPostEvent, cv_MouseUse.mouseUse, cv_MouseUse.useDescription, "
                + "cv_MouseUse._mouseUse_key, cv_MouseUse.d1Caption, cv_MouseUse.d2Caption, cv_MouseUse.d3Caption, cv_MouseUse.d4Caption, "
                + "cv_MouseUse.d5Caption, cv_MouseUse.d6Caption, cv_MouseUse.d7Caption, cv_MouseUse.d8Caption, cv_MouseUse.d9Caption, "
                + "cv_MouseUse.d10Caption "
                + "FROM UseScheduleTerm "
                + "LEFT JOIN UseScheduleList "
                + "ON UseScheduleTerm._useScheduleTerm_key = UseScheduleList._useScheduleTerm_key "
                + "LEFT JOIN cv_MouseUse "
                + "ON UseScheduleList._mouseUse_key = cv_MouseUse._mouseUse_key "
                + "WHERE UseScheduleTerm._useScheduleTerm_key = " + useScheduleTermKey;
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        String lastUseScheduleTermAddedKey = "0";
        UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
        for(SortedMap result : results){
            String currentUseScheduleTermKey = myGet("_useScheduleTerm_key", result);
            /*
             * two cases: 
             * 1. case one you're creating the use Schedule term
             * 2. case two you're adding another mouse usage to the use Schedule term
             */
            if(!lastUseScheduleTermAddedKey.equals(currentUseScheduleTermKey)){
                lastUseScheduleTermAddedKey = currentUseScheduleTermKey;
                useScheduleTerm = new UseScheduleTermDTO();
                //set use schedule term items
                //System.out.println(myGet("useScheduleTermActive", result));
                if(myGet("useScheduleTermActive", result).equalsIgnoreCase("0")){
                    useScheduleTerm.setIsActive(false);
                }
                else{
                    useScheduleTerm.setIsActive(true);
                }
                useScheduleTerm.setUseScheduleTermDetail(myGet("useScheduleTermDetail", result));
                useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", result));
                useScheduleTerm.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", result));
                useScheduleTerm.setVersionNum(myGet("versionNum", result));
                useScheduleTerm.setWorkgroupKey(myGet("_Workgroup_key", result));
                useScheduleTerm.setUseScheduleTermKey(myGet("_useScheduleTerm_key", result));
                useScheduleTerm.setColor(myGet("color", result));
            }
            if(!myGet("_mouseUse_key", result).equals("")){
                //add the links
                UseScheduleListDTO dto = new UseScheduleListDTO();
                dto.setDaysAfterStart(new Integer(myGet("daysPostEvent", result)));
                dto.setUseScheduleTerm(useScheduleTerm);
                dto.setUseScheduleListKey(myGet("_useScheduleList_key", result));
                dto.setUse(muDAO.getCvMouseUseByKey(myGet("_mouseUse_key", result)));
                useScheduleTerm.getUses().add(dto);
            }
        }
        return useScheduleTerm;
    }
    
    public ArrayList<UseScheduleStartEventDTO> getUseScheduleStartEvents(){
        ArrayList<UseScheduleStartEventDTO> useScheduleStartEvents = new ArrayList<UseScheduleStartEventDTO>();
        String query = "SELECT * FROM cv_UseScheduleStartEvent WHERE isActive != 0;";
        SortedMap[] startEvents = this.executeJCMSQuery(query).getRows();
        for(SortedMap startEvent : startEvents){
            UseScheduleStartEventDTO useScheduleStartEvent = new UseScheduleStartEventDTO();
            useScheduleStartEvent.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", startEvent));
            useScheduleStartEvent.setUseScheduleStartEvent(myGet("useScheduleStartEvent", startEvent));
            useScheduleStartEvent.setUseScheduleStartEventDetail(myGet("useScheduleStartEventDetail", startEvent));
            useScheduleStartEvents.add(useScheduleStartEvent);
        }
        return useScheduleStartEvents;
    }
    
    public UseScheduleStartEventDTO getUseScheduleStartEvent(String useScheduleStartEventKey){
        String query = "SELECT * FROM cv_useScheduleStartEvent WHERE _useScheduleStartEvent_key = " + useScheduleStartEventKey;
        SortedMap[] startEvents = this.executeJCMSQuery(query).getRows();
        UseScheduleStartEventDTO useScheduleStartEvent = new UseScheduleStartEventDTO();
        if(startEvents.length > 0){
            SortedMap startEvent = startEvents[0];
            useScheduleStartEvent.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", startEvent));
            useScheduleStartEvent.setUseScheduleStartEvent(myGet("useScheduleStartEvent", startEvent));
            useScheduleStartEvent.setUseScheduleStartEventDetail(myGet("useScheduleStartEventDetail", startEvent));
        }
        return useScheduleStartEvent;
    }
    
    /*
     * get use schedules:
     * Will probably need to get scheduled use schedules in several ways
     * 1. By Mouse key/type - user will want to know what use schedules a mouse is scheduled for
     * 2. By date range - return all use schedules that begin between date x and y
     * 3. Find them all!
     * 
     * A mouse can only be in one instance of a use schedule at a time, so for example mouse can  be
     * in use schedule 1 and use schedule 2, but a mosue cannot be in use schedule 1 x2 UNLESS the mouse is 
     * already finished with use schedule 1, then it can be added to use schedule 1 AGAIN
     */
    
    public ArrayList<UseScheduleTermDTO> getMouseUseScheduleTerms(String mouseKey, String startEventKey, ArrayList<WorkgroupEntity> wgs){
        ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
        //get use schedules
        String query = "SELECT DISTINCT(UseScheduleTerm._useScheduleTerm_key) "
                + "FROM UseSchedule "
                + "JOIN UseScheduleTerm "
                + "ON UseSchedule._useScheduleTerm_key = UseScheduleTerm._useScheduleTerm_key "
                + "LEFT JOIN MouseUsage "
                + "ON UseSchedule._useSchedule_key = MouseUsage._useSchedule_key "
                + "WHERE UseSchedule._mouse_key = " + mouseKey
                + " AND _useScheduleStartEvent_key = " + startEventKey
                + this.buildWorkgroupFilter("UseScheduleTerm", wgs, true) + ";";
        for(SortedMap pKey : this.executeJCMSQuery(query).getRows()){
            useScheduleTerms.add(getUseScheduleTerm(myGet("_useScheduleTerm_key", pKey)));
        }
        return useScheduleTerms;
    }    
     
    public ArrayList<UseScheduleTermDTO> getPDMouseUseScheduleTerms(String plugDateKey, ArrayList<WorkgroupEntity> wgs){
        ArrayList<UseScheduleTermDTO> useScheduleTerms = new ArrayList<UseScheduleTermDTO>();
        //get use schedules
        String query = "SELECT DISTINCT(UseScheduleTerm._useScheduleTerm_key) "
                + "FROM UseSchedule "
                + "JOIN UseScheduleTerm "
                + "ON UseSchedule._useScheduleTerm_key = UseScheduleTerm._useScheduleTerm_key "
                + "WHERE _plugDate_key = " + plugDateKey
                + this.buildWorkgroupFilter("UseScheduleTerm", wgs, true) + ";";
        for(SortedMap pKey : this.executeJCMSQuery(query).getRows()){
            useScheduleTerms.add(getUseScheduleTerm(myGet("_useScheduleTerm_key", pKey)));
        }
        return useScheduleTerms;
    }  
    
    /*
    * get the active Use Schedule, then check if any of the uses for that use schedule are marked as done.
    * The active use schedule is the use schedule where at least one of the mouse usages is not
    * marked as done.
    * Will return false if the use schedule has not been started (indicating it's ok to delete)
    * will return true if the use schedule has been started (has data associated w/ the usages 
    * OR a usage has been marked as 'done'.
    */
    //used to see if a mouse can be removed from a use schedule
    public boolean useScheduleStarted(UseScheduleTermDTO useScheduleTerm, MouseDTO mouse){
        /*
         * following query will return whether the mouse use Schedule has been started but not finished
         * i.e. whether the the scheduled use schedule is NOT DONE AND at least one of the mouse uses
         * is marked as done.
         */
        String query = "SELECT * FROM UseScheduleTerm "
                + "JOIN UseSchedule ON UseScheduleTerm._useScheduleTerm_key = UseSchedule._useScheduleTerm_key "
                + "JOIN MouseUsage ON UseSchedule._useSchedule_key = MouseUsage._useSchedule_key "
                + "WHERE UseScheduleTerm._useScheduleTerm_key = " + useScheduleTerm.getUseScheduleTermKey()
                + " AND UseSchedule._mouse_key = " + mouse.getMouse_key()
                + " AND UseSchedule.done = 0"
                + " AND MouseUsage.done != 0;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        //if no results, there are no done usages for the use schedule, still need to check if any uses have data
        if(rows.length < 1){
            return checkForMouseUsageData(rows);
        }
        else{
            return true;
        }
    }
    
    /*
    * get the active Use Schedule, then check if any of the uses for that use schedule are marked as done.
    * The active use schedule is the use schedule where at least one of the mouse usages is not
    * marked as done.
    * Will return false if the use schedule has not been started (indicating it's ok to delete)
    * will return true if the use schedule has been started (has data associated w/ the usages 
    * OR a usage has been marked as 'done'.
    */
    //used to see if a mouse can be removed from a use schedule
    public boolean pdUseScheduleStarted(UseScheduleTermDTO useScheduleTerm, PlugDateDTO plug){
        /*
         * following query will return whether the mouse use Schedule has been started but not finished
         * i.e. whether the the scheduled use schedule is NOT DONE AND at least one of the mouse uses
         * is marked as done.
         */
        String query = "SELECT * FROM UseSchedule "
                + "JOIN UseScheduleTerm ON UseSchedule._useScheduleTerm_key = UseScheduleTerm._useScheduleTerm_key "
                + "JOIN MouseUsage ON UseSchedule._useSchedule_key = MouseUsage._useSchedule_key "
                + "WHERE UseScheduleTerm._useScheduleTerm_key = " + useScheduleTerm.getUseScheduleTermKey()
                + " AND UseSchedule._plugDate_key = " + plug.getPlugDateKey()
                + " AND UseSchedule.done = 0"
                + " AND MouseUsage.done != 0;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        //if no results, there are no done usages for the use schedule, still need to check if any uses have data
        if(rows.length < 1){
            return checkForMouseUsageData(rows);
        }
        else{
            return true;
        }
    }
    
    /*
     * a private method to determine whether there is data associated with the usage
     */
    private boolean checkForMouseUsageData(SortedMap[] usages){
        for(SortedMap usage : usages){
            if(!myGet("D1", usage).equals("")){
                return true;
            }
            if(!myGet("D2", usage).equals("")){
                return true;
            }
            if(!myGet("D3", usage).equals("")){
                return true;
            }
            if(!myGet("D4", usage).equals("")){
                return true;
            }
            if(!myGet("D5", usage).equals("")){
                return true;
            }
            if(!myGet("D6", usage).equals("")){
                return true;
            }
            if(!myGet("D7", usage).equals("")){
                return true;
            }
            if(!myGet("D8", usage).equals("")){
                return true;
            }
            if(!myGet("D9", usage).equals("")){
                return true;
            }
            if(!myGet("D10", usage).equals("")){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<UseScheduleDTO> executeUseScheduleSearch(UseScheduleSearchDTO dto){
        ArrayList<UseScheduleDTO> sUseSchedules = new ArrayList<UseScheduleDTO>();  
        //add the order clause to group the rows for easier parsing
        String orderClause = " ORDER BY _useSchedule_key ASC;";
        //add the group by clause to not return multiple results for the same mosue usage key
        String groupClause = " GROUP BY _usage_key, _useSchedule_key ";
        String query = "SELECT "
                //Use Schedule
                + "SP._useSchedule_key, DATE_FORMAT(SP.startDate, '" + SelectMySQLDateFormat + "') AS startDate, SP.comment, "
                + "FLOOR(SP.done) AS useScheduleDone, "
                //MouseUsage
                + "MU._usage_key, MU.use, MU._plugDate_key, MU.useAge, "
                + "DATE_FORMAT(MU.projectedDate, '" + SelectMySQLDateFormat + "') AS projectedDate, "
                + "DATE_FORMAT(MU.actualDate, '" + SelectMySQLDateFormat + "') AS actualDate, "
                + "FLOOR(MU.done) AS mouseUseDone, "
                + "MU.comment, MU.D1, MU.D2, MU.D3, MU.D4, MU.D5, MU.D6, MU.D7, MU.D8, MU.D9, MU.D10, "
                //Mouse
                + "M._mouse_key, M._litter_key, M._strain_key, M._pen_key, M.ID, M.newTag, "
                + "DATE_FORMAT(M.birthDate, '" + SelectMySQLDateFormat + "') AS birthDate, "
                + "DATE_FORMAT(M.exitDate, '" + SelectMySQLDateFormat + "') AS exitDate, "
                + "M.cod, M.codNotes, "
                + "M.generation, M.sex, M.lifeStatus, M.breedingStatus, M.coatColor, M.diet, M.owner, M.origin, M.comment, "
                //Container
                + "C.containerID, "
                //Strain
                + "S.strainName, "
                //use schedule term
                + "P._useScheduleTerm_key, P.useScheduleTermName, P.useScheduleTermDetail, "
                + "FLOOR(P.isActive) AS useScheduleTermActive, P.versionNum, P._workgroup_key, P._useScheduleStartEvent_key, "
                + "P.color, "
                //UseScheduleList
                + "PUL._useScheduleList_key, PUL.daysPostEvent, "
                //cv_MouseUse
                + "cvMU.mouseUse, cvMU.useDescription, cvMU._mouseUse_key, cvMU.d1Caption, "
                + "cvMU.d2Caption, cvMU.d3Caption, cvMU.d4Caption, cvMU.d5Caption, "
                + "cvMU.d6Caption, cvMU.d7Caption, cvMU.d8Caption, cvMU.d9Caption, "
                + "cvMU.d10Caption "
                + "FROM UseSchedule AS SP "
                + "JOIN UseScheduleTerm AS P "
                + "ON SP._useScheduleTerm_key = P._useScheduleTerm_key "
                + "JOIN Mouse AS M "
                + "ON SP._mouse_key = M._mouse_key "
                + "LEFT JOIN UseScheduleList AS PUL "
                + "ON P._useScheduleTerm_key = PUL._useScheduleTerm_key  "
                + "LEFT JOIN cv_MouseUse AS cvMU "
                + "ON PUL._mouseUse_key = cvMU._mouseUse_key "
                + "LEFT JOIN MouseUsage AS MU "
                + "ON SP._useSchedule_key = MU._useSchedule_key AND cvMU.mouseUse = MU.`use` "
                + "LEFT JOIN Workgroup AS W "
                + "ON M.owner = W.WorkgroupName "
                + "JOIN Container AS C "
                + "ON M._pen_key = C._container_key "
                + "JOIN Strain AS S "
                + "ON M._strain_key = S._strain_key "
                + "WHERE ";
        String whereClause = this.buildWorkgroupFilter("W", dto.getWgs(), false);
        if(dto.getUseScheduleTerm() != null){
            whereClause += " AND (P._useScheduleTerm_key = " + dto.getUseScheduleTerm().getUseScheduleTermKey() + ")";            
        }
        if(dto.getGeneration() != null){
            whereClause += " AND (M.generation = '" + dto.getGeneration().getGeneration() + "')";
        }
        if(dto.getLifeStatus() != null){
            whereClause += " AND (M.lifeStatus = '" + dto.getLifeStatus().getLifeStatus() + "')";
        }
        if(dto.getSex() != null){
            whereClause += " AND (M.sex = '" + dto.getSex().getAbbreviation() + "')";
        }
        if(dto.getStrain() != null){
            whereClause += " AND (M._strain_key = " + dto.getStrain().getStrainKey() + ")";
        }
        if(!dto.getMouseID().trim().equals("")){
            //if contains do that, if equals do that.
            if (dto.getMouseFilter().equalsIgnoreCase("equals")) {
                whereClause += "AND (M.ID = '" + dto.getMouseID().trim() + "')";
            }
            else {
                whereClause += " AND (M.ID LIKE '%" + dto.getMouseID().trim() + "%') ";
            }                              
        }
        if(!dto.getPenID().equals("")){
            //get proper filter...
            if(dto.getPenFilter().equals("Greather Than")){
                whereClause += " AND (C.containerID > " + dto.getPenID() + ") ";
            }
            else if(dto.getPenFilter().equals("Less Than")){
                whereClause += " AND (C.containerID < " + dto.getPenID() + ") ";
            }
            else{
                whereClause += " AND (C.containerID = " + dto.getPenID() + ") ";
            }
        }
        
        //birthdate...
        // set the birthDate
        Date startDt = null;
        Date endDt = null;

        if (dto.getDOBStartDate() != null) {
            startDt = dto.getDOBStartDate();
        }

        if (dto.getDOBEndDate() != null) {
            endDt = dto.getDOBEndDate();
        }

        // (2) create our date "formatter" (the date format we want)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");

        if (startDt != null && endDt != null) {
            String sDt = formatter.format(startDt);
            String eDt = formatter.format(endDt);

            whereClause += " AND (M.birthDate BETWEEN '" + sDt + "' AND '" + eDt + "') ";
        }
        
        //start date
        // set the startdate
        Date startDate = null;
        Date endStartDate = null;

        if (dto.getStartDateLow() != null) {
            startDate = dto.getStartDateLow();
        }

        if (dto.getStartDateHigh() != null) {
            endStartDate = dto.getStartDateHigh();
        }

        if (startDate != null && endStartDate != null) {
            String sDt = formatter.format(startDate);
            String eDt = formatter.format(endStartDate);

            whereClause += " AND (SP.startDate BETWEEN '" + sDt + "' AND '" + eDt + "') ";
        }
        
        if(dto.getUseSchedule().getDone() != null){
            if(dto.getUseSchedule().getDone()){
                whereClause += " AND (SP.done != 0) ";
            }
            else{
                whereClause += " AND (SP.done = 0) ";
            }
        }
        System.out.println(whereClause);
        query += whereClause;
        query += groupClause;
        query += orderClause;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        String currentUseScheduleKey = "";      
        UseScheduleDTO sDTO = new UseScheduleDTO();
        for(SortedMap row : rows){
            /*will have multiple returns for each Scheduled use schedule so check SP key and
             * if it matches previous row add that row data to the same row. Will need 
             * both the administrative data (use schedule table + UseScheduleList + cv_MouseUse
             * as well as the Use Schedule + MouseUsage + Mouse data)*/
            //if they are the same, dont need to add a new use schedule, need to add other info
            if(!currentUseScheduleKey.equals(myGet("_useSchedule_key", row))){
                currentUseScheduleKey = myGet("_useSchedule_key", row);
                sDTO = new UseScheduleDTO();
                sDTO.setComment(myGet("comment",row));
                //done
                if(myGet("useScheduleDone",row).equals("0")){
                    sDTO.setDone(false);
                }
                else{
                    sDTO.setDone(true);
                }
                sDTO.setUseScheduleKey(myGet("_useSchedule_key", row));
                //start date
                try{
                    sDTO.setStartDate(convertStringToDate(myGet("startDate", row)));
                }
                catch(ParseException e){
                    System.out.println(e);
                }
                
                //create and set mouse
                MouseDTO mouse = new MouseDTO();
                try{
                    mouse.setBirthDate(convertStringToDate(myGet("birthdate", row)));
                    if(!myGet("exitDate", row).equals("")){
                        mouse.setExitDate(convertStringToDate(myGet("exitDate", row)));
                    }
                }
                catch(ParseException e){
                    System.out.println(e);
                }
                mouse.setMouse_key(myGet("_mouse_key", row));
                mouse.setLitter_key(myGet("_litter_key", row));
                mouse.setStrain_key(myGet("_strain_key", row));
                mouse.setPen_key(myGet("_pen_key", row));
                mouse.setID(myGet("ID", row));
                mouse.setNewTag(myGet("newTag", row));
                mouse.setCod(myGet("cod", row));
                mouse.setCodNotes(myGet("codNotes", row));
                mouse.setGeneration(myGet("generation", row));
                mouse.setSex(myGet("sex", row));
                mouse.setLifeStatus(myGet("lifeStatus", row));
                mouse.setBreedingStatus(myGet("breedingStatus", row));
                mouse.setCoatColor(myGet("coatColor", row));
                mouse.setDiet(myGet("diet", row));
                mouse.setOwner(myGet("owner", row));
                mouse.setOrigin(myGet("origin", row));
                mouse.setComment(myGet("comment", row));
                mouse.setContainerID(myGet("containerID", row));
                mouse.setStrainName(myGet("strainName", row));
                sDTO.setMouse(mouse);
                
                //create and set use schedule term details
                UseScheduleTermDTO useScheduleTerm = new UseScheduleTermDTO();
                useScheduleTerm.setColor(myGet("color", row));
                useScheduleTerm.setUseScheduleTermDetail(myGet("useScheduleTermDetail", row));
                //is use schedule term active?
                if(myGet("useScheduleTermActive",row).equals("0")){
                    useScheduleTerm.setIsActive(false);
                }
                else{
                    useScheduleTerm.setIsActive(true);
                }
                useScheduleTerm.setUseScheduleTermKey(myGet("_useScheduleTerm_key", row));
                useScheduleTerm.setUseScheduleTermName(myGet("useScheduleTermName", row));
                useScheduleTerm.setUseScheduleStartEventKey(myGet("_useScheduleStartEvent_key", row));
                useScheduleTerm.setVersionNum(myGet("versionNum", row));
                useScheduleTerm.setWorkgroupKey(myGet("_workgroup_key", row));
                sDTO.setUseScheduleTerm(useScheduleTerm);      
                sUseSchedules.add(sDTO);
            }
            if(!myGet("use",row).equals("")){
                //mouse usage
                MouseUsageDTO usage = new MouseUsageDTO();
                try{
                    usage.setActualDate(convertStringToDate(myGet("actualDate", row)));
                }
                catch(ParseException e){
                    System.out.println(e);
                }
                usage.setComment(myGet("comment", row));
                usage.setD1(myGet("D1", row));
                usage.setD2(myGet("D2", row));
                usage.setD3(myGet("D3", row));
                usage.setD4(myGet("D4", row));
                usage.setD5(myGet("D5", row));
                usage.setD6(myGet("D6", row));
                usage.setD7(myGet("D7", row));
                usage.setD8(myGet("D8", row));
                usage.setD9(myGet("D9", row));
                usage.setD10(myGet("D10", row));
                usage.setDone(myGet("mouseUseDone", row));
                usage.setMouseKey(myGet("_mouse_key", row));
                usage.setPlugDateKey(myGet("_plugDate_key", row));
                try{
                    usage.setProjectedDate(convertStringToDate(myGet("projectedDate", row)));
                }
                catch(ParseException e){
                    System.out.println(e);
                }
                usage.setUseScheduleKey(myGet("_useSchedule_key", row));
                usage.setUsageKey(myGet("_usage_key", row));
                usage.setUse(myGet("use", row));
                usage.setUseAge(myGet("useAge", row));
                sDTO.getUsages().add(usage);

                //use schedule term list
                UseScheduleListDTO link = new UseScheduleListDTO();
                String dpe = myGet("daysPostEvent", row);
                if(!dpe.equals("")){
                    link.setDaysAfterStart(new Integer(myGet("daysPostEvent", row)));
                }
                link.setUseScheduleTerm(sDTO.getUseScheduleTerm());
                link.setUseScheduleListKey(myGet("_useScheduleList_key", row));
                //initialize use
                cvMouseUseDTO use = new cvMouseUseDTO();
                use.setMouseUse(myGet("mouseUse", row));
                use.setUseDescription(myGet("useDescription",row));
                use.setMouseUseKey(myGet("_mouseUse_key",row));
                use.setD1Caption(myGet("d1Caption",row));
                use.setD2Caption(myGet("d2Caption",row));
                use.setD3Caption(myGet("d3Caption",row));
                use.setD4Caption(myGet("d4Caption",row));
                use.setD5Caption(myGet("d5Caption",row));
                use.setD6Caption(myGet("d6Caption",row));
                use.setD7Caption(myGet("d7Caption",row));
                use.setD8Caption(myGet("d8Caption",row));
                use.setD9Caption(myGet("d9Caption",row));
                use.setD10Caption(myGet("d10Caption",row));
                link.setUse(use);
                
                //add use to usage as well
                usage.setMouseUse(use);
                sDTO.getUseScheduleTerm().getUses().add(link);
            }
        }
        return sUseSchedules;
    }
    
    //moved update mouse usage over from mouse use DAO so I could make some changes w/o effects upstream
    public void updateMouseUsage(MouseUsageDTO dto) throws Exception{
        String updateQuery = "UPDATE MouseUsage SET "
                + "`use` = '" + dto.getUse() + "', "
                + "useAge = " + numberParser(dto.getUseAge()) + ", "
                + "`done` = " + dto.getDoneForInsert() + ", "
                + "comment = " + varcharParser(dto.getComment()) + ", "
                + "D1 = " + varcharParser(dto.getD1()) + ", "
                + "D2 = " + varcharParser(dto.getD2()) + ", "
                + "D3 = " + varcharParser(dto.getD3()) + ", "
                + "D4 = " + varcharParser(dto.getD4()) + ", "
                + "D5 = " + varcharParser(dto.getD5()) + ", "
                + "D6 = " + varcharParser(dto.getD6()) + ", "
                + "D7 = " + varcharParser(dto.getD7()) + ", "
                + "D8 = " + varcharParser(dto.getD8()) + ", "
                + "D9 = " + varcharParser(dto.getD9()) + ", "
                + "D10 = " + varcharParser(dto.getD10()) + ", ";
        String projDate = formatAsMySQLDate(dto.getProjectedDate());
        String actualDate = formatAsMySQLDate(dto.getActualDate());
        //projected date
        if(projDate.equals("")){
            updateQuery += "projectedDate = null, ";
        }
        else{
            updateQuery += "projectedDate = '" + projDate + "', ";
        }
        //actual date
        if(actualDate.equals("")){
            updateQuery += "actualDate = null, ";
        }
        else{
            updateQuery += "actualDate = '" + actualDate + "', ";
        }
        
        updateQuery += "version = " + dto.getVersion() + " "
                    + "WHERE _usage_key = " + dto.getUsageKey() + ";";
        System.out.println(updateQuery);
        executeJCMSUpdate(updateQuery);
    }
    
    public boolean mouseInUseSchedule(UseScheduleDTO dto){
        String query = "SELECT * FROM Mouse AS M "
                + "JOIN UseSchedule AS SP ON M._mouse_key = SP._mouse_key "
                + "WHERE SP._useScheduleTerm_key = " + dto.getUseScheduleTerm().getUseScheduleTermKey()
                + " AND M._mouse_key = " + dto.getMouse().getMouse_key() + ";";
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        return false;
    }
}
