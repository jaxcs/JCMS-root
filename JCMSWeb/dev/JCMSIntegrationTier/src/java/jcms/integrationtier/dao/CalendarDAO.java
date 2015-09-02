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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.dto.CalendarDTO;
import jcms.integrationtier.dto.CalendarWeanDTO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.MouseUsageDTO;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import java.util.Date;
import java.util.Calendar;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import java.util.List;

/**
 *
 * @author mkamato
 */
public class CalendarDAO extends MySQLDAO{
    
    private Connection con = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String calendarQuery = "SELECT Mouse.ID, Mouse._mouse_key AS mouseKey, DATE_FORMAT(Mouse.birthDate, '%m/%d/%Y') AS birthDate, " + 
                            "Strain.strainName, Strain.jrNum, Container.containerID, Container.containerName, " +
                            "MouseUsage._usage_key, `use`, useAge, " + 
                            "DATE_FORMAT(projectedDate, '%m/%e/%Y %H:%i:%s') AS projectedDate, DATE_FORMAT(actualDate, '%m/%e/%Y %H:%i:%s') AS actualDate, " +
                            "MouseUsage.done, MouseUsage.comment, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, " +
                            "UseScheduleTerm._useScheduleTerm_key, UseScheduleTerm.useScheduleTermDetail, UseScheduleTerm.color, UseScheduleTerm.versionNum, UseScheduleTerm.useScheduleTermName, " +
                            "cv_MouseUse.* " +
                            "FROM MouseUsage " +
                            "JOIN Mouse " +
                            "ON MouseUsage._mouse_key = Mouse._mouse_key " +
                            "JOIN Container " +
                            "ON Mouse._pen_key = Container._container_key " +
                            "JOIN Strain " +
                            "ON Mouse._strain_key = Strain._strain_key " +
                            "JOIN cv_MouseUse " +
                            "ON MouseUsage.`use` = cv_MouseUse.mouseUse " + 
                            "LEFT JOIN UseSchedule " +
                            "ON MouseUsage._useSchedule_key = UseSchedule._useSchedule_key " + 
                            "LEFT JOIN UseScheduleTerm " +
                            "ON UseSchedule._useScheduleTerm_key = UseScheduleTerm._useScheduleTerm_key ";
    
    private String genotypeQuery = "SELECT Mouse.ID, CONCAT(Gene.labSymbol, ' ', COALESCE(Genotype.allele1), ' ', COALESCE(Genotype.allele2,'')) AS genotype" +
                       " FROM Mouse" +
                       " LEFT JOIN Genotype" +
                       " ON Mouse._mouse_key = Genotype._mouse_key" +
                       " JOIN Gene" +
                       " ON Genotype._gene_key = Gene._gene_key" +
                       " WHERE Mouse.ID = '";
    
    SystemDao dao = new SystemDao();
    
    
    //build dto to return for calendar
    public ArrayList<CalendarDTO> getCalendarData(CalendarDTO dto){
        ArrayList<CalendarDTO> calendarUses = new ArrayList<CalendarDTO>();
        String theQuery = calendarQuery + buildWhereClause(dto);
        
        //first do it for actualDate data, next do it for projected date data
        System.out.println(theQuery);
        SortedMap[] actualDateData = executeQuery(theQuery).getRows();
        for(SortedMap data : actualDateData){
            CalendarDTO useDTO = new CalendarDTO();
            //mouse details
            useDTO.setCageID(myGet("containerID", data));
            useDTO.setCageName(myGet("containerName", data));
            useDTO.setJrNum(myGet("jrNum", data));
            useDTO.setMouseID(myGet("ID", data));
            useDTO.setMouseKey(myGet("mouseKey", data));
            useDTO.setStrainName(myGet("strainName", data));
            useDTO.setMouseBirthDate(myGet("birthdate", data));
            
            //use details
            try{
                useDTO.getUse().setActualDate(convertStringToDate(myGet("actualDate", data)));
                useDTO.getUse().setProjectedDate(convertStringToDate(myGet("projectedDate", data)));
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }
            useDTO.getUse().setComment(myGet("comment", data));
            useDTO.getUse().setD1(myGet("D1", data));
            useDTO.getUse().setD2(myGet("D2", data));
            useDTO.getUse().setD3(myGet("D3", data));
            useDTO.getUse().setD4(myGet("D4", data));
            useDTO.getUse().setD5(myGet("D5", data));
            useDTO.getUse().setD6(myGet("D6", data));
            useDTO.getUse().setD7(myGet("D7", data));
            useDTO.getUse().setD8(myGet("D8", data));
            useDTO.getUse().setD9(myGet("D9", data));
            useDTO.getUse().setD10(myGet("D10", data));
            useDTO.getUse().setDone(myGet("done", data));
            useDTO.getUse().setUsageKey(myGet("_usage_key", data));
            useDTO.getUse().setUse(myGet("use", data));
            useDTO.getUse().setUseAge(myGet("useAge", data));
            
            //cv use details
            useDTO.getCvMouseUse().setIsActive(myGet("isActive", data));
            useDTO.getCvMouseUse().setMouseUse(myGet("mouseUse", data));
            useDTO.getCvMouseUse().setMouseUseKey(myGet("_mouseUse_key", data));
            useDTO.getCvMouseUse().setUseDescription(myGet("useDescription", data));
            useDTO.getCvMouseUse().setVersion(myGet("version", data));
            useDTO.getCvMouseUse().setD1Caption(myGet("d1Caption", data));
            useDTO.getCvMouseUse().setD2Caption(myGet("d2Caption", data));
            useDTO.getCvMouseUse().setD3Caption(myGet("d3Caption", data));
            useDTO.getCvMouseUse().setD4Caption(myGet("d4Caption", data));
            useDTO.getCvMouseUse().setD5Caption(myGet("d5Caption", data));
            useDTO.getCvMouseUse().setD6Caption(myGet("d6Caption", data));
            useDTO.getCvMouseUse().setD7Caption(myGet("d7Caption", data));
            useDTO.getCvMouseUse().setD8Caption(myGet("d8Caption", data));
            useDTO.getCvMouseUse().setD9Caption(myGet("d9Caption", data));
            useDTO.getCvMouseUse().setD10Caption(myGet("d10Caption", data));
            useDTO.setGenotype(getMouseGenotype(useDTO.getMouseID()));
            
            //useScheduleTerm details
            useDTO.getUseScheduleTerm().setColor(myGet("color", data));
            useDTO.getUseScheduleTerm().setUseScheduleTermDetail(myGet("useScheduleTermDetail", data));
            useDTO.getUseScheduleTerm().setUseScheduleTermName(myGet("useScheduleTermName", data));
            useDTO.getUseScheduleTerm().setVersionNum(myGet("versionNum", data));
            useDTO.getUseScheduleTerm().setUseScheduleTermKey(myGet("_useScheduleTerm_key", data));
            calendarUses.add(useDTO);
        }
        return calendarUses;
    }
    
    
    public ArrayList<String> getMouseList(ArrayList<String> owners){
        ArrayList<String> mice = new ArrayList<String>();
        String theMouseQuery = "SELECT ID FROM Mouse WHERE ";
        for(String owner : owners){
            if(owner.equals(owners.get(owners.size() - 1))){
                theMouseQuery = theMouseQuery + "Mouse.owner = '" + owner + "'";
            }
            else{
                theMouseQuery = theMouseQuery + "Mouse.owner = '" + owner + "' OR ";
            }
        }
        SortedMap[] miceMap = executeQuery(theMouseQuery).getRows();
        for(SortedMap mouse : miceMap){
            mice.add(myGet("ID", mouse));
        }
        return mice;
    }
    
    //builds the where clause using input from user in CalendarDTO
    private String buildWhereClause(CalendarDTO dto){
        String whereClause = "WHERE (projectedDate IS NOT NULL OR actualDate IS NOT NULL)";
        //status
        if(dto.getStatus().equals("done")){
            whereClause += " AND MouseUsage.done != 0 ";
        }
        else if(dto.getStatus().equals("notDone")){
            whereClause += " AND MouseUsage.done = 0 ";
        }
       
        //fill out owner portion of query, should only gives uses for mice owned by requested owners
        String whereOwnerClause = "";
        for(String owner : dto.getOwners()){
            if(whereOwnerClause.equals("")){
                whereOwnerClause = " AND (Mouse.owner = '" + owner + "'";
            }
            else{
                whereOwnerClause = whereOwnerClause + " OR Mouse.owner = '" + owner + "'";
            }
        }
        whereOwnerClause = whereOwnerClause + ")";
        
        //build use portion of query, should only fill out calendar for uses requested by user
        String whereUseClause = "";
        if(!dto.getUses().isEmpty()){
            for(String use : dto.getUses()){
                if(whereUseClause.equals("")){
                    whereUseClause = " AND (MouseUsage.use = '" + use + "'";
                }
                else{
                    whereUseClause = whereUseClause + " OR MouseUsage.use = '" + use + "'";
                }
            }
            whereUseClause = whereUseClause + ")";
        }
        
        //Strain portion of the where clause        
        String whereStrainClause = "";
        //if user wants uses for all strains no need to add to where clause
        if(!dto.getStrains().isEmpty()){
            for(String strain : dto.getStrains()){
                if(whereStrainClause.equals("")){
                    whereStrainClause = " AND (Strain.strainName = '" + strain + "'";
                }
                else{
                    whereStrainClause = whereStrainClause + " OR Strain.strainName  = '" + strain + "'";
                }
            }
            whereStrainClause = whereStrainClause + ")";
        }
        
        //Use Schedule portion of the where clause
        String whereUseScheduleClause = "";
        if(!dto.getUseScheduleTerms().isEmpty()){
            for(UseScheduleTermDTO pDTO : dto.getUseScheduleTerms()){
                if(whereUseScheduleClause.equals("")){
                    whereUseScheduleClause = " AND (UseScheduleTerm._useScheduleTerm_key = " + pDTO.getUseScheduleTermKey();
                }
                else{
                    whereUseScheduleClause = whereUseScheduleClause + " OR UseScheduleTerm._useScheduleTerm_key  = " + pDTO.getUseScheduleTermKey();
                }
            }
            whereUseScheduleClause = whereUseScheduleClause + ")";
        }
        
        //before and after
        String whereBefore = "";
        String whereAfter = "";
        if(dto.getAfterDate() != null){
            whereAfter += " AND (MouseUsage.projectedDate > '" + this.formatAsMySQLDate(dto.getAfterDate()) + "' "
                    + "OR MouseUsage.actualDate > '" + this.formatAsMySQLDate(dto.getAfterDate()) + "') ";
        }
        if(dto.getBeforeDate() != null){            
            whereBefore += " AND (MouseUsage.projectedDate < '" + this.formatAsMySQLDate(dto.getBeforeDate()) + "' "
                    + "OR MouseUsage.actualDate < '" + this.formatAsMySQLDate(dto.getBeforeDate()) + "') ";
        }
        
        whereClause += whereOwnerClause + whereUseClause + whereStrainClause + whereUseScheduleClause + whereBefore + whereAfter;
        return whereClause;
    }
    
    public String getMouseGenotype(String mouseID){
        String genotype = "";
        SortedMap[] genotypes = executeQuery(genotypeQuery + mouseID + "';").getRows();
        for(SortedMap gt:genotypes){
            genotype = genotype + " " + myGet("genotype", gt);
        }
        return genotype;
    }
    
    public void updateActualDate(String usageKey, String actualDate) throws Exception{
        String updateQuery = "UPDATE MouseUsage SET"
                + " actualDate = " + dateParser(actualDate)
                + " WHERE _usage_key = " + numberParser(usageKey);
        System.out.println(updateQuery);
        executeUpdate(updateQuery);
    }
    
    public void updateProjectedDate(String usageKey, String projectedDate, String useAge) throws Exception{
        String updateQuery = "UPDATE MouseUsage SET"
                + " projectedDate = " + dateParser(projectedDate)
                + ", useAge = " + this.numberParser(useAge)
                + " WHERE _usage_key = " + this.numberParser(usageKey);
        executeUpdate(updateQuery);
    }
    
    public void editMouseUsage(CalendarDTO calendarDTO) throws Exception{    
        MouseUsageDTO dto = calendarDTO.getUse();
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
    
    public String insertMouseUsage(MouseUsageDTO dto) throws Exception{
        return new Integer(new MouseUseDAO().addMouseUsage(dto)).toString();
    }
    
    public ArrayList<CalendarWeanDTO> getWeanDates(Date afterDateFilter, Date beforeDateFilter, List<String> workgroups) throws Exception{
        ArrayList<CalendarWeanDTO> weanDates = new ArrayList<CalendarWeanDTO>();
        DbSetupDAO dbSetupDAO = new DbSetupDAO();
        //Calculate number of days it should take a mating to produce a litter (standard/ext. wean time + gestation period (20 days))
        String gestationPeriod = dbSetupDAO.getJCMSGestationPeriod().getMTSValue();
        String standardMatingDaysToWean = new Integer(Integer.parseInt(dbSetupDAO.getJCMSStandardWeanTime().getMTSValue()) + Integer.parseInt(gestationPeriod)).toString();
        String extendedMatingDaysToWean = new Integer(Integer.parseInt(dbSetupDAO.getJCMSExtWeanTime().getMTSValue()) + Integer.parseInt(gestationPeriod)).toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //create two queries, one for extended, one for standard
        String query = "SELECT Mating.matingID, Mating.matingDate, Strain.strainName, Mating.generation, Mating.owner,"
            + " Mating.owner, Room.roomName AS room, Container.ContainerID AS cageID, Container.containerName AS cageName,"
            + " Mouse.ID, Mating.retiredDate, Litter.litterID, Litter.weanDate, Litter._litter_key, weanTime,"
            + " Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSStandardWeanTime().getMTSValue() + " DAY) AS projectedLitterWeanDate, "
            + " Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSExtWeanTime().getMTSValue() + " DAY) AS projectedExtLitterWeanDate, "
            + " Date_Add(Mating.matingDate, INTERVAL " + standardMatingDaysToWean + " DAY) AS projectedStandardMatingWeanDate, "
            + " Date_Add(Mating.matingDate, INTERVAL " + extendedMatingDaysToWean + " DAY) AS projectedExtMatingWeanDate"
            + " FROM Mating"
            + " LEFT JOIN Litter "
            + " ON Mating._mating_key = Litter._mating_key"
            + " JOIN Strain"
            + " ON Mating._strain_key = Strain._strain_key"
            + " JOIN Mouse"
            + " ON Mating._dam1_key = Mouse._mouse_key"
            + " JOIN Container"
            + " ON Mouse._pen_key = Container._container_key"
            + " JOIN ContainerHistory"
            + " ON Container._containerHistory_key = ContainerHistory._containerHistory_key"
            + " JOIN Room"
            + " ON ContainerHistory._room_key = Room._room_key"
            + " JOIN cv_ContainerStatus"
            + " ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key"
            + " JOIN cv_CrossStatus"
            + " ON Mating._crossStatus_key = cv_CrossStatus._crossStatus_key"
            + " JOIN cv_MatingType"
            + " ON Mating._matingType_key = cv_MatingType._matingType_key"
            + " WHERE cv_CrossStatus.abbreviation = 'A' "
            + " AND matingType = 'Natural'" //following and clause will grab all matings/litters that could potentially be weans
            + " AND ((Litter._litter_key IS NOT NULL AND Litter.weanDate IS NULL AND Litter.status = 'A'" //case one is where litter exists w/o a wean date
            +       " AND ("
            +             "(weanTime != 0" //find within the time interval for both extended and standard wean time
            +             " AND Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSStandardWeanTime().getMTSValue() + " DAY) <= '" + formatter.format(beforeDateFilter)  
            +             "' AND Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSStandardWeanTime().getMTSValue() + " DAY) >= '" + formatter.format(afterDateFilter)  
            +             "')"
            +             " OR"
            +             " (weanTime = 0"
            +             " AND Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSExtWeanTime().getMTSValue() + " DAY) <= '" + formatter.format(beforeDateFilter)  
            +             "' AND Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSExtWeanTime().getMTSValue() + " DAY) >= '" + formatter.format(afterDateFilter) 
            +             "')"
            +           ")"
            +      ")"  
            +   " OR (Litter._litter_key IS NULL" //case two is where active mating exists that hasn't produced a litter in the last x days
            +      " AND ("//find within time interval for extended and standard wean times
            +           "(weanTime != 0 "
            +           " AND Date_Add(Mating.matingDate, INTERVAL " + standardMatingDaysToWean + " DAY) <= '" + formatter.format(beforeDateFilter) 
            +           "' AND Date_Add(Mating.matingDate, INTERVAL " + standardMatingDaysToWean + " DAY) >= '" + formatter.format(afterDateFilter) 
            +           "')"
            +           " OR "
            +           "(weanTime = 0 "
            +           " AND Date_Add(Mating.matingDate, INTERVAL " + extendedMatingDaysToWean + " DAY) <= '" + formatter.format(beforeDateFilter) 
            +           "' AND Date_Add(Mating.matingDate, INTERVAL " + extendedMatingDaysToWean + " DAY) >= '" + formatter.format(afterDateFilter)  
            +       "'))"
            +      ")"
            +    ")"
            + " AND "; //have to add the owner clause
        
        String ownerClause = "(";
        //build owners portion
        for(String wg : workgroups){
            if(!wg.equals(workgroups.get(workgroups.size() - 1))){
                ownerClause = ownerClause + "Mating.owner = '" + wg + "' OR ";
            }
            else{
                ownerClause = ownerClause + "Mating.owner = '" + wg + "')";
            }
        }
        //add owner clause
        query = query + ownerClause;
        
        //third case: find a litter that has mice born, but no mice have been added to DB -> implies user has been using Wean Date to schedule their weans
        String query2 = "SELECT DISTINCT(Litter._litter_key), dam1.ID, weanDate, containerID, roomName, matingID, Mouse._mouse_key, totalBorn " +
                        "FROM Litter  " +
                        "LEFT JOIN Mouse " +
                        "ON Litter._litter_key = Mouse._litter_key " +
                        "JOIN Mating " +
                        "ON Litter._mating_key = Mating._mating_key " +
                        "JOIN Mouse AS dam1 " +
                        "ON Mating._dam1_key = dam1._mouse_key " +
                        "JOIN Container AS c " +
                        "ON dam1._pen_key = c._container_key " +
                        "JOIN ContainerHistory AS ch " +
                        "ON c._containerHistory_key = ch._containerHistory_key " +
                        "JOIN Room " +
                        "ON ch._room_key = Room._room_key " +
                        "JOIN cv_ContainerStatus AS cs " +
                        "ON ch._containerStatus_key = cs._containerStatus_key " +
                        "WHERE weanDate IS NOT NULL " +
                        "AND Mouse._mouse_key IS NULL " +
                        "AND totalBorn != 0 " +
                        "AND Litter.status = 'A' " +
                        "AND weanDate <= '" + formatter.format(beforeDateFilter) + "' " +
                        "AND weanDate >= '" + formatter.format(afterDateFilter) + "' " +
                        "AND " + ownerClause;
        
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        SortedMap[] results2 = this.executeJCMSQuery(query2).getRows();
        for(SortedMap result : results){
            CalendarWeanDTO dto = new CalendarWeanDTO();
            //set up dtos
            //wean date depends on whether it is standard or extended wean time and whether wean date is based on mating
            //that hasn't produced a litter but probably should have, or a litter that has a birthdate but not a wean date

            //if litter key is null, the weanDate is based on a mating that has not produced a litter
            if(myGet("_litter_key", result).equals("")){
                if(myGet("weanTime", result).equals("false")){
                    dto.setWeanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("projectedExtMatingWeanDate",result)));     
                }
                else{
                    dto.setWeanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("projectedStandardMatingWeanDate",result)));      
                }
            }
            else{//otherwise there is a litter key which implies that it is a litter that doesn't have a wean date with it.
                if(myGet("weanTime", result).equals("false")){
                    dto.setWeanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("projectedExtLitterWeanDate",result)));    
                }
                else{
                    dto.setWeanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("projectedLitterWeanDate",result)));      
                }
                dto.setLitter((LitterEntity) dao.getSystemFacadeLocal().baseFind(new LitterEntity(new Integer(myGet("_litter_key", result)))));
            }
            dto.setMating((MatingEntity) dao.getSystemFacadeLocal().baseFindByMatingID(new MatingEntity(), Integer.parseInt(myGet("matingID", result))));
            dto.setContainer((ContainerEntity) dao.getSystemFacadeLocal().baseFindByContainerID(new ContainerEntity(), Integer.parseInt(myGet("containerID", result))));
            MouseEntity dam1 = (MouseEntity) dao.getSystemFacadeLocal().findMouse(dto.getMating().getDam1Key());
            //get dam1 and dam2 IDs
            if (dam1 != null && dam1.getId() != null && !dam1.getId().equals("")) {
                    dto.getMating().setDam1ID(dam1.getId());
            }
            if(dto.getMating().getDam2Key() != null){
                MouseEntity dam2 = (MouseEntity) dao.getSystemFacadeLocal().findMouse(dto.getMating().getDam2Key().intValue());
                if (dam2 != null && dam2.getId() != null && !dam2.getId().equals("")) {
                    dto.getMating().setDam2ID(dam2.getId());
                }
            }
            weanDates.add(dto);
        }

        for(SortedMap result : results2){
            CalendarWeanDTO dto = new CalendarWeanDTO();
            dto.setWeanDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myGet("weanDate", result)));

            dto.setMating((MatingEntity) dao.getSystemFacadeLocal().baseFindByMatingID(new MatingEntity(), Integer.parseInt(myGet("matingID", result))));
            dto.setContainer((ContainerEntity) dao.getSystemFacadeLocal().baseFindByContainerID(new ContainerEntity(), Integer.parseInt(myGet("containerID", result))));
            MouseEntity dam1 = (MouseEntity) dao.getSystemFacadeLocal().findMouse(dto.getMating().getDam1Key());
            //get dam1 and dam2 IDs
            if (dam1 != null && dam1.getId() != null && !dam1.getId().equals("")) {
                    dto.getMating().setDam1ID(dam1.getId());
            }
            if(dto.getMating().getDam2Key() != null){
                MouseEntity dam2 = (MouseEntity) dao.getSystemFacadeLocal().findMouse(dto.getMating().getDam2Key().intValue());
                if (dam2 != null && dam2.getId() != null && !dam2.getId().equals("")) {
                    dto.getMating().setDam2ID(dam2.getId());
                }
            }
            dto.setLitter((LitterEntity) dao.getSystemFacadeLocal().baseFind(new LitterEntity(new Integer(myGet("_litter_key", result)))));
            weanDates.add(dto);
        }
        return weanDates;
    }
    
    public void retireMating(String matingKey) throws Exception{
        String query = "UPDATE Mating " +
                "SET retiredDate = " + this.dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) +
                ", _crossStatus_key = (SELECT _crossStatus_key FROM cv_CrossStatus WHERE crossStatus = 'retired') " +
                "WHERE _mating_key = " + matingKey;
        this.executeJCMSUpdate(query);
    }
    
    public void cullLitter(String litterKey) throws Exception{
        String query = "UPDATE Litter " +
                "SET status = (SELECT abbreviation FROM cv_BirthEventStatus WHERE birthEventStatus = 'Killed') " +
                "WHERE _litter_key = " + litterKey;
        this.executeJCMSUpdate(query);
    }
    
    public MouseUsageDTO getMouseUsage(String mouseUsageKey){
        MouseUsageDTO usage = new MouseUsageDTO();
        String query = "SELECT * FROM MouseUsage WHERE _usage_key = " + mouseUsageKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
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
            usage.setDone(myGet("done", row));
            try{
            usage.setProjectedDate(this.convertStringToDate(myGet("projectedDate", row)));
            usage.setActualDate(this.convertStringToDate(myGet("actualDate", row)));
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }
            usage.setUsageKey(myGet("_usage_key", row));
            usage.setUse(myGet("use", row));
            usage.setUseAge(myGet("useAge", row)); 
        }
        return usage;
    }
    
    //used for getting data from DB
    public Result executeQuery(String theQuery) {
        ResultSet myResultSet;
        Result myResult;

        try {
            con = super.getConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
        } catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);
            super.closeConnection(con);
            return null;
        }
    }
    
    //used for inserting data into DB
    public Integer executeUpdate(String theUpdate) throws SQLException {
        Integer myResult = 0;

        try {
            con = super.getConnection();
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            myResult = stmt.executeUpdate(theUpdate);
            super.closeConnection(con);
            return myResult;
        } catch (SQLException ex) {
            Logger.getLogger(AdministrationDAO.class.getName()).log(Level.SEVERE, null, ex);
            super.closeConnection(con);
            throw new SQLException("Insert Failed: " + theUpdate);
        }
    }

    @Override
    protected String myGet(String field, SortedMap result) {
        if (result.get(field) == null) {
            return "";
        } else {
            return result.get(field).toString();
        }
    }
}
