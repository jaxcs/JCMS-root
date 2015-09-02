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

import java.util.List;
import java.util.Date;
import java.util.SortedMap;
import jcms.integrationtier.dto.HistogramDayDTO;
import jcms.integrationtier.dto.HistogramUnitDTO;
import jcms.integrationtier.dto.HistogramDataDTO;
import jcms.integrationtier.dto.HistogramDTO;

/**
 *
 * @author mkamato
 */
public class DashboardDAO extends MySQLDAO {


    /**
     * Method to communicate with the database and return the data necessary to 
     * build a cage histogram. The method queries the HistogramDataTable
     * 
     * @param   owners - a list of the owners that the logged in person belongs to
     * @return  HistogramDTO - A DTO containing the data necessary to build a 
     *                         histogram.
     */
    public HistogramDTO getCageHistogramData(List<String> owners) {
        HistogramDTO histogram = new HistogramDTO();

        HistogramDataDTO strainData = new HistogramDataDTO();
        strainData.setGroupingUnit("strain");

        HistogramDataDTO ownerData = new HistogramDataDTO();
        ownerData.setGroupingUnit("owner");

        //owner stuff
        String cageOwnerQuery = "SELECT owner, "
                        + "DATE_FORMAT(`date`, '%m/%d/%Y %h:%i:%s') AS date, "
                        + "SUM(unitVolume) AS activeCages "
                        + "FROM HistogramDataTable "
                        + "WHERE unitType = 'cages' "
                        + this.buildOwnerFilter("HistogramDataTable", owners)
                        + " GROUP BY owner, date "
                        + "ORDER BY `date`, owner;";

        SortedMap[] cageOwnerRows = this.executeJCMSQuery(cageOwnerQuery).getRows();
        /*
            * Each row in the sorted map corresponds to one owners mice on that day,
            * it's ordered by day so that as you're iterating over the rows once you 
            * run into a new date you create a new DTO.
            */
        Date groupDate = new Date();
        HistogramDayDTO cageOwnerDay = null;
        for (SortedMap row : cageOwnerRows) {
            try{
                //case where you're starting...
                if(cageOwnerDay == null){
                    groupDate = this.convertStringToDate(myGet("date", row)); 
                    cageOwnerDay = new HistogramDayDTO(); 
                }
                Date rowDate = this.convertStringToDate(myGet("date", row)); 
                /*
                    * if rowDate isn't the same as group date -> you've reached
                    * the end of a group of date values so you need to start
                    * using a new day...
                    */
                if(!groupDate.equals(rowDate)){
                    //finalize the old day data and add to the histogram...
                    cageOwnerDay.setDay(groupDate);
                    ownerData.getHistogramData().add(cageOwnerDay);

                    //set up next day object and change date value
                    groupDate = rowDate;
                    cageOwnerDay = new HistogramDayDTO();                        
                }
                //create datapoint object and add to cage day...
                HistogramUnitDTO unit = new HistogramUnitDTO();
                unit.setFilterUnit(this.myGet("owner", row));
                unit.setVolume(Integer.parseInt(this.myGet("activeCages", row)));
                cageOwnerDay.getUnits().add(unit);
                //case where you're at the last row...
                if(row.equals(cageOwnerRows[cageOwnerRows.length - 1])){                        
                    cageOwnerDay.setDay(groupDate);
                    ownerData.getHistogramData().add(cageOwnerDay);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        ownerData.getHistogramData().add(cageOwnerDay);

        //strain stuff
        String cageStrainQuery = "SELECT strainName, "
                        + "DATE_FORMAT(`date`, '%m/%d/%Y %h:%i:%s') AS date, "
                        + "SUM(unitVolume) AS activeCages "
                        + "FROM HistogramDataTable "
                        + "WHERE unitType = 'cages' "
                        + this.buildOwnerFilter("HistogramDataTable", owners)
                        + " GROUP BY strainName, date "
                        + "ORDER BY `date`, strainName;";

        SortedMap[] cageStrainRows = this.executeJCMSQuery(cageStrainQuery).getRows();
        /*
            * Each row in the sorted map corresponds to one strains mice on that day,
            * it's ordered by day so that as you're iterating over the rows once you 
            * run into a new date you create a new DTO.
            */
        Date strainGroupDate = new Date();
        HistogramDayDTO cageStrainDay = null;
        for (SortedMap row : cageStrainRows) {
            try{
                //case where you're starting...
                if(cageStrainDay == null){
                    strainGroupDate = this.convertStringToDate(myGet("date", row)); 
                    cageStrainDay = new HistogramDayDTO(); 
                }
                Date rowDate = this.convertStringToDate(myGet("date", row)); 
                /*
                    * if rowDate isn't the same as group date -> you've reached
                    * the end of a group of date values so you need to start
                    * using a new day...
                    */
                if(!strainGroupDate.equals(rowDate)){
                    //finalize the old day data and add to the histogram...
                    cageStrainDay.setDay(strainGroupDate);
                    strainData.getHistogramData().add(cageStrainDay);

                    //set up next day object and change date value
                    strainGroupDate = rowDate;
                    cageStrainDay = new HistogramDayDTO();                        
                }
                //create datapoint object and add to cage day...
                HistogramUnitDTO unit = new HistogramUnitDTO();
                unit.setFilterUnit(this.myGet("strainName", row));
                unit.setVolume(Integer.parseInt(this.myGet("activeCages", row)));
                cageStrainDay.getUnits().add(unit);

                //case where you're at the last row...
                if(row.equals(cageStrainRows[cageStrainRows.length - 1])){                        
                    cageStrainDay.setDay(groupDate);
                    strainData.getHistogramData().add(cageStrainDay);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
            
        System.out.println("querying done...");
        histogram.getHistogram().add(ownerData);
        histogram.getHistogram().add(strainData);
        return histogram;
    }
    
    public HistogramDTO getMouseHistogramData(List<String> owners){
        HistogramDTO histogram = new HistogramDTO();

        HistogramDataDTO strainData = new HistogramDataDTO();
        strainData.setGroupingUnit("strain");

        HistogramDataDTO ownerData = new HistogramDataDTO();
        ownerData.setGroupingUnit("owner");
        
        //mice grouped by owner
        String mouseOwnerQuery = "SELECT owner, "
                + "DATE_FORMAT(`date`, '%m/%d/%Y %h:%i:%s') AS date, "
                + "SUM(unitVolume) AS mice "
                + "FROM HistogramDataTable "
                + "WHERE unitType = 'mice' "
                + this.buildOwnerFilter("HistogramDataTable", owners)
                + "GROUP BY owner, date "
                + "ORDER BY date, owner";
        SortedMap[] mouseOwnerRows = this.executeJCMSQuery(mouseOwnerQuery).getRows();
        /*
        * Each row in the sorted map corresponds to one owners mice on that day,
        * it's ordered by day so that as you're iterating over the rows once you 
        * run into a new date you create a new DTO.
        */
        Date ownerGroupDate = new Date();
        HistogramDayDTO mouseOwnerDay = null;
        for (SortedMap row : mouseOwnerRows) {
            try{
                //case where you're starting...
                if(mouseOwnerDay == null){
                    ownerGroupDate = this.convertStringToDate(myGet("date", row)); 
                    mouseOwnerDay = new HistogramDayDTO(); 
                }
                Date rowDate = this.convertStringToDate(myGet("date", row)); 
                /*
                    * if rowDate isn't the same as group date -> you've reached
                    * the end of a group of date values so you need to start
                    * using a new day...
                    */
                if(!ownerGroupDate.equals(rowDate)){
                    //finalize the old day data and add to the histogram...
                    mouseOwnerDay.setDay(ownerGroupDate);
                    ownerData.getHistogramData().add(mouseOwnerDay);

                    //set up next day object and change date value
                    ownerGroupDate = rowDate;
                    mouseOwnerDay = new HistogramDayDTO();                        
                }
                //create datapoint object and add to cage day...
                HistogramUnitDTO unit = new HistogramUnitDTO();
                unit.setFilterUnit(this.myGet("owner", row));
                unit.setVolume(Integer.parseInt(this.myGet("mice", row)));
                mouseOwnerDay.getUnits().add(unit);
                //case where you're at the last row...
                if(row.equals(mouseOwnerRows[mouseOwnerRows.length - 1])){                        
                    mouseOwnerDay.setDay(ownerGroupDate);
                    ownerData.getHistogramData().add(mouseOwnerDay);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }        
        
        //mice grouped by strain
        String mouseStrainQuery = "SELECT strainName, "
                + "DATE_FORMAT(`date`, '%m/%d/%Y %h:%i:%s') AS date, "
                + "SUM(unitVolume) AS mice "
                + "FROM HistogramDataTable "
                + "WHERE unitType = 'mice' "
                + this.buildOwnerFilter("HistogramDataTable", owners)
                + "GROUP BY strainName, date "
                + "ORDER BY date, strainName";
        SortedMap[] mouseStrainRows = this.executeJCMSQuery(mouseStrainQuery).getRows();
        Date strainGroupDate = new Date();
        HistogramDayDTO mouseStrainDay = null;
        for (SortedMap row : mouseStrainRows) {
            try{
                //case where you're starting...
                if(mouseStrainDay == null){
                    strainGroupDate = this.convertStringToDate(myGet("date", row)); 
                    mouseStrainDay = new HistogramDayDTO(); 
                }
                Date rowDate = this.convertStringToDate(myGet("date", row)); 
                /*
                * if rowDate isn't the same as group date -> you've reached
                * the end of a group of date values so you need to start
                * using a new day...
                */
                if(!strainGroupDate.equals(rowDate)){
                    //finalize the old day data and add to the histogram...
                    mouseStrainDay.setDay(strainGroupDate);
                    strainData.getHistogramData().add(mouseStrainDay);

                    //set up next day object and change date value
                    strainGroupDate = rowDate;
                    mouseStrainDay = new HistogramDayDTO();                        
                }
                //create datapoint object and add to cage day...
                HistogramUnitDTO unit = new HistogramUnitDTO();
                unit.setFilterUnit(this.myGet("strainName", row));
                unit.setVolume(Integer.parseInt(this.myGet("mice", row)));
                mouseStrainDay.getUnits().add(unit);
                
                //case where you're at the last row...
                if(row.equals(mouseStrainRows[mouseStrainRows.length - 1])){                        
                    mouseStrainDay.setDay(strainGroupDate);
                    strainData.getHistogramData().add(mouseStrainDay);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        
        //add data to histogram dto
        histogram.getHistogram().add(ownerData);
        histogram.getHistogram().add(strainData);
        return histogram;
    }
    
    public void updateDashboard() throws Exception{
        this.executeJCMSUpdate("CALL histoProcedure();");
    }
}
