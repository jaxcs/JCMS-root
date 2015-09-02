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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;
import jcms.integrationtier.dto.WeanReportDTO;

/**
 *
 * @author bas
 */
public class WeanReportDAO extends MySQLDAO{
    
    // Recommended by lint if your class is serialiable
    private static final long serialVersionUID = 1002L;
    
    //Some of the code below is copied from mka CalendarDAO example
    //The goal is to have the calendar and this report list the same results
    //But to provide the results in very different formats (printed report vs. calendar)
    //Build the dto to return for the wean report
    //changing it to pass a string for the owner clause instead of an array.
    public ArrayList<WeanReportDTO> getWeanDates(Date afterDateFilter, Date beforeDateFilter, String ownerClause){
        ArrayList<WeanReportDTO> weanDates = new ArrayList<WeanReportDTO>();
        DbSetupDAO dbSetupDAO = new DbSetupDAO();
        //first you need all active matings owned where 
        String standardMatingDaysToWean = "39"; //this is the default
        Integer gestation = Integer.valueOf(dbSetupDAO.getJCMSGestationPeriod().getMTSValue());
        Integer standardWeanLength = Integer.valueOf(dbSetupDAO.getJCMSStandardWeanTime().getMTSValue());
        standardMatingDaysToWean = Integer.toString(gestation + standardWeanLength);
        String extendedMatingDaysToWean = "49"; //This is the default
        Integer extendedWeanLength = Integer.valueOf(dbSetupDAO.getJCMSExtWeanTime().getMTSValue());
        extendedMatingDaysToWean = Integer.toString(gestation + extendedWeanLength);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //create two queries, one for extended, one for standard
        //Modify query to add rack (level ID, x as column, y as row)
        //Modify the query to show both litters if one (or both) have not been weaned
        String query = "SELECT Mating.matingID,"
            + " DATE_FORMAT(Mating.matingDate, '%Y-%m-%d') AS matingDate, Strain.strainName, Mating.generation, Mating.owner,"
            + " Mating.owner, Room.roomName AS room, Container.ContainerID AS cageID, Container.containerName AS cageName,"
            + " Level.levelId AS rack, ContainerHistory.x AS rackColumn, ContainerHistory.y AS rackRow,"
            + " Mouse.ID, Mating.retiredDate, Litter.litterID, DATE_FORMAT(Litter.weanDate,'%Y-%m-%d') AS weanDate, Litter.status, Litter._litter_key, weanTime,"
            + " DATE_FORMAT(Litter.birthDate, '%Y-%m-%d') AS birthDate, Litter.totalBorn, Litter.numFemale, Litter.numMale,"
            + " DATE_FORMAT(Litter.tagDate, '%Y-%m-%d') AS tagDate, Mating.generation, Mating.needsTyping,"
            + " Litter.comment, Mating.weanNote, Strain.jrNum,"
            + " DATE_FORMAT(Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSStandardWeanTime().getMTSValue() + " DAY), '%Y-%m-%d') AS projectedLitterWeanDate, "
            + " DATE_FORMAT(Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSExtWeanTime().getMTSValue() + " DAY), '%Y-%m-%d') AS projectedExtLitterWeanDate, "
            + " DATE_FORMAT(Date_Add(Mating.matingDate, INTERVAL " + standardMatingDaysToWean + " DAY), '%Y-%m-%d') AS projectedStandardMatingWeanDate, "
            + " DATE_FORMAT(Date_Add(Mating.matingDate, INTERVAL " + extendedMatingDaysToWean + " DAY), '%Y-%m-%d') AS projectedExtMatingWeanDate"
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
            + " LEFT OUTER JOIN Level"
            + " ON ContainerHistory._level_key = Level._level_key"
            + " WHERE cv_CrossStatus.abbreviation = 'A' "
            + " AND matingType = 'Natural'" //following and clause will grab all matings/litters that could potentially be weans
            + " AND ((Litter._litter_key IS NOT NULL AND Litter.weanDate IS NULL AND Litter.status = 'A'" //case one is where active litter exists w/o a wean date
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
            +   " OR (Litter._litter_key IS NULL" //case two is where active mating exists that hasn't produced a litter in the last 40 days
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
            +    ")";
        
        //have to add the owner clause, changed from passing an array to passing it as a string
        String addOwnerCriteria = "";
        if(!ownerClause.equals("")) {
            addOwnerCriteria = " AND (Mating.owner IN (" + ownerClause + "))";
        }
               
        query = query + addOwnerCriteria;
        
        //Add sort by owner, strain, and room
        query = query + " ORDER BY Mating.owner, strainName, room";
        
        //B-03209 Add litters with a wean date that have no mice entered yet into the database 
        //(this will make the calendar and this report both return the same dates)
        //third case: find a litter that has mice born, but no mice have been added to DB -> implies user has been using Wean Date to schedule their weans
        String query2 = "SELECT DISTINCT(Litter._litter_key), dam1.ID, DATE_FORMAT(Litter.weanDate,'%Y-%m-%d') AS weanDate, Mating.matingID, Mouse._mouse_key, totalBorn, " 
            + " DATE_FORMAT(Mating.matingDate, '%Y-%m-%d') AS matingDate, Strain.strainName, Mating.generation, Mating.owner,"
            + " Mating.owner, Room.roomName AS room, c.ContainerID AS cageID, c.containerName AS cageName,"
            + " Level.levelId AS rack, ch.x AS rackColumn, ch.y AS rackRow,"
            + " Mouse.ID, Mating.retiredDate, Litter.litterID, Litter.status, weanTime,"
            + " DATE_FORMAT(Litter.birthDate, '%Y-%m-%d') AS birthDate, Litter.numFemale, Litter.numMale,"
            + " DATE_FORMAT(Litter.tagDate, '%Y-%m-%d') AS tagDate, Mating.generation, Mating.needsTyping,"
            + " Litter.comment, Mating.weanNote, Strain.jrNum,"
            + " DATE_FORMAT(Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSStandardWeanTime().getMTSValue() + " DAY), '%Y-%m-%d') AS projectedLitterWeanDate, "
            + " DATE_FORMAT(Date_Add(Litter.birthDate, INTERVAL " + dbSetupDAO.getJCMSExtWeanTime().getMTSValue() + " DAY), '%Y-%m-%d') AS projectedExtLitterWeanDate, "
            + " DATE_FORMAT(Date_Add(Mating.matingDate, INTERVAL " + standardMatingDaysToWean + " DAY), '%Y-%m-%d') AS projectedStandardMatingWeanDate, "
            + " DATE_FORMAT(Date_Add(Mating.matingDate, INTERVAL " + extendedMatingDaysToWean + " DAY), '%Y-%m-%d') AS projectedExtMatingWeanDate " +
                        "FROM Litter  " +
                        "LEFT JOIN Mouse " +
                        "ON Litter._litter_key = Mouse._litter_key " +
                        "JOIN Mating " +
                        "ON Litter._mating_key = Mating._mating_key " +
              " JOIN Strain"
            + " ON Mating._strain_key = Strain._strain_key " +
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
              " LEFT OUTER JOIN Level"
            + " ON ch._level_key = Level._level_key " +
                        "WHERE weanDate IS NOT NULL " +
                        "AND Mouse._mouse_key IS NULL " +
                        "AND totalBorn != 0 " +
                        "AND Litter.status = 'A' " +
                        "AND weanDate <= '" + formatter.format(beforeDateFilter) + "' " +
                        "AND weanDate >= '" + formatter.format(afterDateFilter) + "' " +
               addOwnerCriteria  
             + " ORDER BY Mating.owner, strainName, room";
        
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        SortedMap[] results2 = this.executeJCMSQuery(query2).getRows();
        
        //Now need to loop and create the resulting rows to be returned
        try{
            for(SortedMap result : results) {
                WeanReportDTO dto = new WeanReportDTO();
                dto.setMatingID (myGet("matingID", result));
                dto.setMatingDate (myGet("matingDate", result));
                dto.setStrainName (myGet("strainName", result));
                dto.setJrNum (myGet("jrNum", result));
                dto.setRoom (myGet("roomName", result)); //NOTE this must use the actual field name instead of the alias "room"
                dto.setRack (myGet("levelId", result));
                dto.setRackColumn (myGet("x", result));
                dto.setRackRow (myGet("y", result));
                dto.setOwner (myGet("owner", result));
                dto.setBirthDate (myGet("birthDate", result));
                //Figure out extended vs standard plus if have litter or not to calculate correct date
                //When there is a litter, calculate based on the birth date
                //Otherwise, calculate based on the mating date
                               
                if(myGet("_litter_key", result) != null) {
                    if(myGet("weanTime", result).equals("false")){
                        dto.setDateToWean (myGet("projectedExtLitterWeanDate", result));
                    } 
                    else {
                        dto.setDateToWean (myGet("projectedLitterWeanDate", result));
                    }   
                }
                if(myGet("_litter_key", result).equals("")) {
                    if(myGet("weanTime", result).equals("true")){
                        dto.setDateToWean (myGet("projectedStandardMatingWeanDate", result));
                    } 
                    else {
                        dto.setDateToWean (myGet("projectedExtMatingWeanDate", result));
                    }
                }        
                dto.setLitterID (myGet("litterID", result));
                dto.setTotalBorn (myGet("totalBorn", result));
                dto.setNumFemale (myGet("numFemale", result));
                dto.setNumMale (myGet("numMale", result));
                dto.setTagDate (myGet("tagDate", result));
                dto.setWeanRecorded (myGet("weanDate", result));
                dto.setLitterStatus (myGet("status", result)); 
                dto.setGeneration (myGet("generation", result));
                dto.setNeedsTyping (myGet("needsTyping", result));
                dto.setCageID (myGet("containerID", result));
                dto.setCageName (myGet("containerName", result));
                dto.setComment (myGet("comment", result));
                dto.setWeanNote (myGet("weanNote", result));
                weanDates.add(dto);
            }
        }
                catch(Exception e){
            System.out.println("ERROR: " + e);
        }
        //Now need to loop again create the resulting rows to be returned from query2
        try{
            for(SortedMap result : results2) {
                WeanReportDTO dto = new WeanReportDTO();
                dto.setMatingID (myGet("matingID", result));
                dto.setMatingDate (myGet("matingDate", result));
                dto.setStrainName (myGet("strainName", result));
                dto.setJrNum (myGet("jrNum", result));
                dto.setRoom (myGet("roomName", result)); //NOTE this must use the actual field name instead of the alias "room"
                dto.setRack (myGet("levelId", result));
                dto.setRackColumn (myGet("x", result));
                dto.setRackRow (myGet("y", result));
                dto.setOwner (myGet("owner", result));
                dto.setBirthDate (myGet("birthDate", result));
                //Figure out extended vs standard plus if have litter or not to calculate correct date
                //When there is a litter, calculate based on the birth date
                //Otherwise, calculate based on the mating date
                
                if(myGet("_litter_key", result) != null) {
                    if(myGet("weanTime", result).equals("false")){
                        dto.setDateToWean (myGet("projectedExtLitterWeanDate", result));
                    } 
                    else {
                        dto.setDateToWean (myGet("projectedLitterWeanDate", result));
                    }   
                }
                if(myGet("_litter_key", result).equals("")) {
                    if(myGet("weanTime", result).equals("true")){
                        dto.setDateToWean (myGet("projectedStandardMatingWeanDate", result));
                    } 
                    else {
                        dto.setDateToWean (myGet("projectedExtMatingWeanDate", result));
                    }
                }        
                dto.setLitterID (myGet("litterID", result));
                dto.setTotalBorn (myGet("totalBorn", result));
                dto.setNumFemale (myGet("numFemale", result));
                dto.setNumMale (myGet("numMale", result));
                dto.setTagDate (myGet("tagDate", result));
                dto.setWeanRecorded (myGet("weanDate", result));
                dto.setLitterStatus (myGet("status", result)); 
                dto.setGeneration (myGet("generation", result));
                dto.setNeedsTyping (myGet("needsTyping", result));
                dto.setCageID (myGet("containerID", result));
                dto.setCageName (myGet("containerName", result));
                dto.setComment (myGet("comment", result));
                dto.setWeanNote (myGet("weanNote", result));
                weanDates.add(dto);
            }
        }
        catch(Exception e){
            System.out.println("ERROR: " + e);
        }
        
return weanDates;}
}
