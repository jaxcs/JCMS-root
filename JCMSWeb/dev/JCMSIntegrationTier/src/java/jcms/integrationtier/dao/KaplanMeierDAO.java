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
import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.KaplanMeierChartDTO;
import jcms.integrationtier.dto.KaplanMeierLineDTO;
import jcms.integrationtier.dto.KaplanMeierLineDetailDTO;
import jcms.integrationtier.dto.KaplanMeierSearchDTO;
import jcms.integrationtier.dto.KaplanMeierMobileSearchDTO;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import java.sql.PreparedStatement;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import java.util.SortedMap;
import java.sql.ResultSet;


/**
 * A Data Access class to be used in to retrieve data that would be used to 
 * build a Kaplan Meier chart.
 * 
 * @author mkamato
 */
public class KaplanMeierDAO extends MySQLDAO {
    
    /**
     * Returns a KaplanMeierChartDTO object which contains all the information necessary
     * to build a KaplanMeierChart. Will get converted to JSON in the Webtier then sent 
     * to the view to be built as a Kaplan Meier chart using D3.
     * Business rules for which workgroups and use schedules can be selected are handled by
     * the view.
     * @param filters   A list containing all the filters for the Kaplan Meier survival curves
     * @return          A dto that contains chart scale data i.e. chart title and a list of
     *                  KaplanMeierChartLineDTOs, each of which represents a different protocol's
     *                  survivability on the chart.
     */
    public KaplanMeierChartDTO findData(ArrayList<KaplanMeierSearchDTO> filters) throws Exception{
        KaplanMeierChartDTO data = new KaplanMeierChartDTO();
        //will create line information for each use schedule term the user selected
        for(KaplanMeierSearchDTO search : filters){
            String where = buildWhereFilter(search);
            String KMQuery = 
                    "SELECT "
                        + "S.strainName, " 
                        //number of days after start or birth
                        + "TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate) AS daysAfterStart, "
                        //find number died because grouping is done via count died...
                        + "COUNT(TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate)) AS countDied, "
                        //count left, calculated at every iteration
                        + "(SELECT @countLeft:=@countLeft - COUNT(TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate))) AS countLeft, "
                        //total # of mice (remains static)
                        + "(SELECT COUNT(DISTINCT(M._mouse_key)) "
                            + "FROM Mouse M "
                            + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                            + " JOIN Container AS C ON M._pen_key = C._container_key "
                            + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";
                            //only join use schedule terms if 
                            if(!search.getUseScheduleTerms().isEmpty()){
                                KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                        + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                            }
                            //only join genotype table if filter is requested since it is time consuming
                            if(!((search.getAllele1s().isEmpty() || search.getAllele2s().isEmpty()) && search.getGenes().isEmpty())){
                                KMQuery += "JOIN Genotype ON M._mouse_key = Genotype._mouse_key ";
                            }          
                            KMQuery += "WHERE " + where +") AS total "
                        //END OF SELECTS, NOW THE FROMS/JOINS
                        + "FROM "
                        /*total left is the value that is subtracted from at each iteration, the
                        number subtracted is the number that died on that days after start total*/
                        + " (SELECT @countLeft:= "
                                + "(SELECT COUNT(DISTINCT(M._mouse_key)) "
                                + " FROM Mouse M "
                                + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                                + " JOIN Container AS C ON M._pen_key = C._container_key "
                                + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";
                            if(!((search.getAllele1s().isEmpty() || search.getAllele2s().isEmpty()) && search.getGenes().isEmpty())){
                                KMQuery += " JOIN Genotype ON M._mouse_key = Genotype._mouse_key ";
                            }     
                            //only join use schedule terms if 
                            if(!search.getUseScheduleTerms().isEmpty()){
                                KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                        + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                            }
                            KMQuery += "WHERE " + where +")) totalLeft, "
                            + "Mouse M "
                            + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                            + " JOIN Container AS C ON M._pen_key = C._container_key "
                            + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";
                            if(!((search.getAllele1s().isEmpty() || search.getAllele2s().isEmpty()) && search.getGenes().isEmpty())){
                                KMQuery += " JOIN (SELECT _mouse_key, _gene_key, allele1, allele2 "
                                                + "FROM Genotype) AS Genotype ON M._mouse_key = Genotype._mouse_key ";
                            }  //only join use schedule terms if 
                            if(!search.getUseScheduleTerms().isEmpty()){
                                KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                        + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                            }
                            KMQuery += "WHERE " + where 
                                + " GROUP BY TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate) "
                                + "ORDER BY daysAfterStart ASC;";
            System.out.println(KMQuery);
            KaplanMeierLineDTO line = new KaplanMeierLineDTO();
            line.setLineName(search.getLineName());
            line.setColor(search.getColor());
            //will return all the details for the line
            SortedMap[] rows = this.executeJCMSQuery(KMQuery).getRows();
            //populate DTO
            for(SortedMap row : rows){
                KaplanMeierLineDetailDTO detail = new KaplanMeierLineDetailDTO();
                detail.setCountDied(myGet("countDied", row));
                detail.setCountLeft(myGet("countLeft", row));
                detail.setDaysAfterStart(myGet("daysAfterStart", row));
                line.getDetails().add(detail);
                
                //last row, get the total and also set the days after start as total days
                if(row.equals(rows[rows.length - 1])){
                    line.setTotal(myGet("total", row));
                    line.setMaxDays(myGet("daysAfterStart", row));
                }
            }
            data.getLines().add(line);
        }        
        //create the data for the strains selected...
        return data;
    }
    
    /**
     * Builds the 'where' to filter out the undesired results.
     * 
     * @param search
     * @return 
     */
    private String buildWhereFilter(KaplanMeierSearchDTO search){
        String where = " M.exitDate IS NOT NULL AND M.birthDate < M.exitDate ";
        if(search.getCods().size() > 0){
            where += " AND (";
            for(CvCauseOfDeathEntity cod : search.getCods()){
                //last one
                if(cod.equals(search.getCods().get(search.getCods().size() - 1))){
                    where += "M.cod = '" + cod.getCod() + "')";
                }
                else{
                    where += "M.cod = '" + cod.getCod() +"' OR ";
                }
            }
        }
        if(search.getDiets().size() > 0){
            where += " AND (";
            for(CvDietEntity diet : search.getDiets()){
                //last one
                if(diet.equals(search.getDiets().get(search.getDiets().size() - 1))){
                    where += "M.diet = '" + diet.getDiet() + "')";
                }
                else{
                    where += "M.diet = '" + diet.getDiet() +"' OR ";
                }
            }
        }
        if(search.getGenes().size() > 0){
            where += " AND (";
            for(GeneEntity gene : search.getGenes()){
                //last one
                if(gene.equals(search.getGenes().get(search.getGenes().size() - 1))){
                    where += "_gene_key = '" + gene.getGeneKey() + "'";
                    ArrayList<AlleleEntity> tempAllele1s = new ArrayList<AlleleEntity>();
                    ArrayList<AlleleEntity> tempAllele2s = new ArrayList<AlleleEntity>();
                    //find the alleles meant for that gene if there are any
                    for(AlleleEntity allele : search.getAllele1s()){
                        if(gene.getGeneKey().equals(allele.getGeneKey()) 
                                || (allele.getGenericAlleleGeneClass() != null 
                                    && gene.getGeneClass() != null
                                    && gene.getGeneClass().equals(allele.getGenericAlleleGeneClass()))){
                            tempAllele1s.add(allele);
                        }
                    }
                    for(AlleleEntity allele : search.getAllele2s()){
                        if(gene.getGeneKey().equals(allele.getGeneKey()) 
                                || (allele.getGenericAlleleGeneClass() != null 
                                    && gene.getGeneClass() != null
                                    && gene.getGeneClass().equals(allele.getGenericAlleleGeneClass()))){
                            tempAllele2s.add(allele);
                        }
                    }
                    where += buildAlleles(tempAllele1s, tempAllele2s);
                    where += ")";
                }
                else{
                    where += "_gene_key = '" + gene.getGeneKey() + "' ";
                    ArrayList<AlleleEntity> tempAllele1s = new ArrayList<AlleleEntity>();
                    ArrayList<AlleleEntity> tempAllele2s = new ArrayList<AlleleEntity>();
                    //find the alleles meant for that gene if there are any
                    for(AlleleEntity allele : search.getAllele1s()){
                        if(gene.getGeneKey().equals(allele.getGeneKey()) 
                                || (allele.getGenericAlleleGeneClass() != null 
                                    && gene.getGeneClass() != null
                                    && gene.getGeneClass().equals(allele.getGenericAlleleGeneClass()))){
                            tempAllele1s.add(allele);
                        }
                    }
                    for(AlleleEntity allele : search.getAllele2s()){
                        if(gene.getGeneKey().equals(allele.getGeneKey()) 
                                || (allele.getGenericAlleleGeneClass() != null 
                                    && gene.getGeneClass() != null
                                    && gene.getGeneClass().equals(allele.getGenericAlleleGeneClass()))){
                            tempAllele2s.add(allele);
                        }
                    }
                    //build allele portion of query...                    
                    where += buildAlleles(tempAllele1s, tempAllele2s);
                    where += "OR ";
                }
            }
        }
        if(search.getLifeStatuses().size() > 0){
            where += " AND (";
            for(LifeStatusEntity lifeStatus : search.getLifeStatuses()){
                //last one
                if(lifeStatus.equals(search.getLifeStatuses().get(search.getLifeStatuses().size() - 1))){
                    where += "M.lifeStatus = '" + lifeStatus.getLifeStatus() + "')";
                }
                else{
                    where += "M.lifeStatus = '" + lifeStatus.getLifeStatus() + "' OR ";
                }
            }
        }
        if(search.getOwners().size() > 0){
            where += " AND (";
            for(OwnerEntity owner : search.getOwners()){
                //last one
                if(owner.equals(search.getOwners().get(search.getOwners().size() - 1))){
                    where += "M.owner = '" + owner.getOwner() + "')";
                }
                else{
                    where += "M.owner = '" + owner.getOwner() + "' OR ";
                }
            }
        }
        if(search.getRooms().size() > 0){
            where += " AND (";
            for(RoomEntity room : search.getRooms()){
                //last one
                if(room.equals(search.getRooms().get(search.getRooms().size() - 1))){
                    where += "_room_key = '" + room.getRoomKey() + "')";
                }
                else{
                    where += "_room_key = '" + room.getRoomKey() + "' OR ";
                }
            }
        }
        if(search.getStrains().size() > 0){
            where += " AND (";
            for(StrainEntity strain : search.getStrains()){
                //last one
                if(strain.equals(search.getStrains().get(search.getStrains().size() - 1))){
                    where += "strainName = '" + strain.getStrainName() + "')";
                }
                else{
                    where += "strainName = '" + strain.getStrainName() + "' OR ";
                }
            }
        }
        if(search.getUseScheduleTerms().size() > 0){
            where += " AND (";
            for(UseScheduleTermDTO ust : search.getUseScheduleTerms()){
                //last one
                if(ust.equals(search.getUseScheduleTerms().get(search.getUseScheduleTerms().size() - 1))){
                    where += "ust._useScheduleTerm_key = '" + ust.getUseScheduleTermKey() + "')";
                }
                else{
                    where += "ust._useScheduleTerm_key = '" + ust.getUseScheduleTermKey() + "' OR ";
                }
            }
        }
        
        return where;
    }
    
    /**
     * Returns a KaplanMeierChartDTO object which contains all the information necessary
     * to build a KaplanMeierChart. Will get converted to JSON in the Webtier then sent 
     * to the view to be built as a Kaplan Meier chart using D3.
     * 
     * Business rules for which workgroups and use schedules can be selected are handled by
     * the view.
     * 
     * @param search    A dto containing all the filters for the Kaplan Meier survival curves
     * @return          A dto that contains chart scale data i.e. chart title and a list of
     *                  KaplanMeierChartLineDTOs, each of which represents a different protocol's
     *                  survivability on the chart.
     */
    public KaplanMeierChartDTO findData(KaplanMeierMobileSearchDTO search) throws Exception{
        KaplanMeierChartDTO data = new KaplanMeierChartDTO();
        //will create line information for each use schedule term the user selected
        String where = buildMobileWhereFilter(search);
        String KMQuery = 
                "SELECT "
                    + "S.strainName, " 
                    //number of days after start or birth
                    + "TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate) AS daysAfterStart, "
                    //find number died because grouping is done via count died...
                    + "COUNT(TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate)) AS countDied, "
                    //count left, calculated at every iteration
                    + "(SELECT @countLeft:=@countLeft - COUNT(TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate))) AS countLeft, "
                    //total # of mice (remains static)
                    + "(SELECT COUNT(DISTINCT(M._mouse_key)) "
                        + "FROM Mouse M "
                        + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                        + " JOIN Container AS C ON M._pen_key = C._container_key "
                        + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";                        
                        //only join use schedule terms if necessary
                        if(search.getUseSchedules() != null && search.getUseSchedules().length > 0){
                            KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                    + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                        }  
                        KMQuery += "WHERE " + where +") AS total "
                    //END OF SELECTS, NOW THE FROMS/JOINS
                    + "FROM "
                    /*total left is the value that is subtracted from at each iteration, the
                    number subtracted is the number that died on that days after start total*/
                    + " (SELECT @countLeft:= "
                            + "(SELECT COUNT(DISTINCT(M._mouse_key)) "
                            + " FROM Mouse M "
                            + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                            + " JOIN Container AS C ON M._pen_key = C._container_key "
                            + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";
                        //only join use schedule terms if necessary
                        if(search.getUseSchedules() != null && search.getUseSchedules().length > 0){
                            KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                    + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                        }
                        KMQuery += "WHERE " + where +")) totalLeft, "
                        + "Mouse M "
                        + " JOIN Strain AS S ON M._strain_key = S._strain_key "
                        + " JOIN Container AS C ON M._pen_key = C._container_key "
                        + " JOIN ContainerHistory AS CH ON C._containerHistory_key = CH._containerHistory_key ";
                        //only join use schedule terms if necessary
                        if(search.getUseSchedules() != null && search.getUseSchedules().length > 0){
                            KMQuery += " JOIN UseSchedule ON M._mouse_key = UseSchedule._mouse_key "    
                                    + " JOIN UseScheduleTerm AS ust ON UseSchedule._useScheduleTerm_key = ust._useScheduleTerm_key ";
                        }
                        KMQuery += "WHERE " + where 
                            + " GROUP BY TIMESTAMPDIFF(DAY, M.birthDate, M.exitDate) "
                            + "ORDER BY daysAfterStart ASC;";
        System.out.println(KMQuery);
        KaplanMeierLineDTO line = new KaplanMeierLineDTO();
        //will return all the details for the line
        SortedMap[] rows = this.executeJCMSQuery(KMQuery).getRows();
        
        //handle the line name...
        if(search.getStrains() != null && search.getStrains().length > 0){
            int idx = 0;
            for(String strain : search.getStrains()){
                if(idx == 0){
                    line.setLineName(strain);
                }
                else{
                    line.setLineName(line.getLineName() + "/" + strain);
                }
                idx++;
            }
        }
        if(search.getUseSchedules() != null && search.getUseSchedules().length > 0){
            int idx = 0;
            for(String useScheduleTerm : search.getUseSchedules()){
                if(idx == 0){
                    line.setLineName(line.getLineName() + " " + useScheduleTerm);
                }
                else{
                    line.setLineName(line.getLineName() + "/" + useScheduleTerm);
                }
                idx++;
            }
        }
        
        //populate DTO
        for(SortedMap row : rows){
            KaplanMeierLineDetailDTO detail = new KaplanMeierLineDetailDTO();
            detail.setCountDied(myGet("countDied", row));
            detail.setCountLeft(myGet("countLeft", row));
            detail.setDaysAfterStart(myGet("daysAfterStart", row));
            line.getDetails().add(detail);

            //last row, get the total and also set the days after start as total days
            if(row.equals(rows[rows.length - 1])){
                line.setTotal(myGet("total", row));
                line.setMaxDays(myGet("daysAfterStart", row));
            }
        }
        data.getLines().add(line);
        //create the data for the strains selected...
        return data;
    }
    
    /**
     * Builds a where clause for the mobile queries.
     * 
     * @param search
     * @return 
     */
    private String buildMobileWhereFilter(KaplanMeierMobileSearchDTO search){
        String where = " M.exitDate IS NOT NULL AND M.birthDate < M.exitDate ";
        if(search.getCods() != null && search.getCods().length > 0){
            where += " AND (";
            for(String cod : search.getCods()){
                //last one
                if(cod.equals(search.getCods()[search.getCods().length - 1])){
                    where += "M.cod = '" + cod + "')";
                }
                else{
                    where += "M.cod = '" + cod +"' OR ";
                }
            }
        }
        if(search.getDiets() != null && search.getDiets().length > 0){
            where += " AND (";
            for(String diet : search.getDiets()){
                //last one
                if(diet.equals(search.getDiets()[search.getDiets().length - 1])){
                    where += "M.diet = '" + diet + "')";
                }
                else{
                    where += "M.diet = '" + diet +"' OR ";
                }
            }
        }
        if(search.getLifeStatuses() != null && search.getLifeStatuses().length > 0){
            where += " AND (";
            for(String lifeStatus : search.getLifeStatuses()){
                //last one
                if(lifeStatus.equals(search.getLifeStatuses()[search.getLifeStatuses().length - 1])){
                    where += "M.lifeStatus = '" + lifeStatus + "')";
                }
                else{
                    where += "M.lifeStatus = '" + lifeStatus + "' OR ";
                }
            }
        }
        if(search.getWorkgroups() != null && search.getWorkgroups().length > 0){
            where += " AND (";
            for(String wg : search.getWorkgroups()){
                //last one
                if(wg.equals(search.getWorkgroups()[search.getWorkgroups().length -1])){
                    where += "M.owner = '" + wg + "')";
                }
                else{
                    where += "M.owner = '" + wg + "' OR ";
                }
            }
        }
        if(search.getRooms() != null && search.getRooms().length > 0){
            where += " AND (";
            for(String roomKey : search.getRooms()){
                //last one
                if(roomKey.equals(search.getRooms()[search.getRooms().length - 1])){
                    where += "_room_key = '" + roomKey + "')";
                }
                else{
                    where += "_room_key = '" + roomKey + "' OR ";
                }
            }
        }
        if(search.getStrains() != null && search.getStrains().length > 0){
            where += " AND (";
            for(String strainName : search.getStrains()){
                //last one
                if(strainName.equals(search.getStrains()[search.getStrains().length - 1])){
                    where += "S.strainName = '" + strainName + "')";
                }
                else{
                    where += "S.strainName = '" + strainName + "' OR ";
                }
            }
        }
        if(search.getUseSchedules() != null && search.getUseSchedules().length > 0){
            where += " AND (";
            for(String useScheduleTerm : search.getUseSchedules()){
                //last one
                if(useScheduleTerm.equals(search.getUseSchedules()[search.getUseSchedules().length - 1])){
                    where += "ust.useScheduleTermName = '" + useScheduleTerm + "')";
                }
                else{
                    where += "ust.useScheduleTermName = '" + useScheduleTerm + "' OR ";
                }
            }
        }        
        return where;
    }
    
    /**
     * A method to get all the alleles associated with a group of genes.
     * 
     * @param genes The genes you want to get the alleles for.
     * @return A list of AlleleEntity objects associated with the genes param
     * @throws Exception 
     */
    public List<AlleleEntity> getAllelesByGene(List<GeneEntity> genes) throws Exception{
        List<AlleleEntity> alleles = new ArrayList<AlleleEntity>();
        
        String withClassQuery = "SELECT Allele.* FROM Allele"
                + " LEFT JOIN Gene ON Allele._gene_key = Gene._gene_key"
                + " WHERE Allele._gene_key = ? " 
                + " OR Allele.genericAlleleGeneClass = ?;";
        
        String withoutClassQuery = "SELECT Allele.* "
                + " FROM Allele"
                + " JOIN Gene ON Allele._gene_key = Gene._gene_key"
                + " WHERE Allele._gene_key = ?;";       
        
        //establish connection and initialize prepared statement
        Connection con = this.getConnection();
        PreparedStatement getAllelesWithClass = con.prepareStatement(withClassQuery);  
        PreparedStatement getAllelesWithoutClass = con.prepareStatement(withoutClassQuery);        
        //get alleles for each gene (including gene class)
        for(GeneEntity gene : genes){
            if(gene.getGeneClass() != null){
                //format the query
                getAllelesWithClass.setInt(1, gene.getGeneKey());
                if(gene.getGeneClass() != null){
                    getAllelesWithClass.setString(2, gene.getGeneClass());
                }
                else{
                    getAllelesWithClass.setNull(2, java.sql.Types.VARCHAR);
                }
                //execute Query...
                ResultSet rs = this.executePreparedStatementQuery(getAllelesWithClass);
                //set cursor to before first element to get all resutls...
                rs.beforeFirst();
                //iterate through results to get all corresponding alleles
                while(rs.next()){
                    AlleleEntity ae = new AlleleEntity();
                    ae.setAllele(rs.getString("allele"));
                    ae.setAlleleKey(rs.getInt("_allele_key"));
                    ae.setGeneKey(rs.getInt("_gene_key"));
                    ae.setGenericAlleleGeneClass(rs.getString("genericAlleleGeneClass"));
                    ae.setVersion(rs.getInt("version"));
                    alleles.add(ae);                    
                }
            }
            //no gene class, only one input
            else{                
                //format query
                getAllelesWithoutClass.setInt(1, gene.getGeneKey());
                //execute query...
                ResultSet rs = this.executePreparedStatementQuery(getAllelesWithoutClass);
                //set cursor to before first element to get all resutls...
                rs.beforeFirst();
                //iterate through results to get all corresponding alleles
                while(rs.next()){
                    AlleleEntity ae = new AlleleEntity();
                    ae.setAllele(rs.getString("allele"));
                    ae.setAlleleKey(rs.getInt("_allele_key"));
                    ae.setGeneKey(rs.getInt("_gene_key"));
                    ae.setGenericAlleleGeneClass(rs.getString("genericAlleleGeneClass"));
                    ae.setVersion(rs.getInt("version"));
                    alleles.add(ae);                  
                }
            }
        }
        return alleles;
    }
    
    /**
     * A helper method to build the genotype portion of the Kaplan Meier wheres.
     * 
     * @param alleleList1
     * @param alleleList2
     * @return 
     */
    private String buildAlleles(List<AlleleEntity> alleleList1, List<AlleleEntity> alleleList2){
        String alleleWhere = "";
        if(!alleleList1.isEmpty() && !alleleList2.isEmpty()){
            alleleWhere = " AND (";
            int allele1Idx = 1;
            for(AlleleEntity allele1 : alleleList1){
                int allele2Idx = 1;
                for(AlleleEntity allele2 : alleleList2){
                    if(allele2Idx == alleleList2.size()){
                        alleleWhere += "(allele1 = '" + allele1.getAllele() + "' AND allele2 = '" + allele2.getAllele() + "') OR "
                                + "(allele1 = '" + allele2.getAllele() + "' AND allele2 = '" + allele1.getAllele() + "')";
                    }
                    else{
                        alleleWhere += "(allele1 = '" + allele1.getAllele() + "' AND allele2 = '" + allele2.getAllele() + "') OR "
                                + "(allele1 = '" + allele2.getAllele() + "' AND allele2 = '" + allele1.getAllele() + "') OR ";                        
                    }
                    allele2Idx++;
                }
                //it's over
                if(allele1Idx == alleleList1.size()){
                    alleleWhere += ") ";                    
                }
                //more to go
                else{
                    alleleWhere+= " OR ";
                }
                allele1Idx++;
            }            
        }
        else if(!alleleList1.isEmpty()){
            alleleWhere = " AND (";
            int allele1Idx = 1;
            for(AlleleEntity allele : alleleList1){                
                if(allele1Idx == alleleList1.size()){
                     alleleWhere += "(allele1 = '" + allele.getAllele() + "' AND allele2 = '" + allele.getAllele() + "') OR "
                                + "(allele1 = '" + allele.getAllele() + "' AND allele2 IS NULL)";
                }
                else{
                    alleleWhere += "(allele1 = '" + allele.getAllele() + "' AND allele2 = '" + allele.getAllele() + "') OR "
                                + "(allele1 = '" + allele.getAllele() + "' AND allele2 IS NULL) OR ";
                }
                allele1Idx++;
            }
            alleleWhere+= ") ";
        }
        else if(!alleleList2.isEmpty()){
            alleleWhere = " AND (";
            int allele2Idx = 1;
            for(AlleleEntity allele : alleleList2){                
                if(allele2Idx == alleleList2.size()){
                     alleleWhere += "(allele1 = '" + allele.getAllele() + "' AND allele2 = '" + allele.getAllele() + "') OR "
                                + "(allele1 = '" + allele.getAllele() + "' AND allele2 IS NULL)";
                }
                else{
                    alleleWhere += "(allele1 = '" + allele.getAllele() + "' AND allele2 = '" + allele.getAllele() + "') OR "
                                + "(allele1 = '" + allele.getAllele() + "' AND allele2 IS NULL) OR ";
                }
                allele2Idx++;
            }
            alleleWhere+= ") ";
        }
        return alleleWhere;
    }
}
