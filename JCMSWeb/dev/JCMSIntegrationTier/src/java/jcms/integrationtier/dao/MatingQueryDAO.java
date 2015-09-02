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

/*
 * MatingQueryDAO.java
 *
 * Created on November 07, 2010
 *
 */

package jcms.integrationtier.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.dto.MatingQueryDTO;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.jstl.sql.Result;
import static org.jax.cs.common.Utils.*;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.jsp.jstl.sql.ResultSupport;


// Parsing Dates
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Entities
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.cv.CvGenerationEntity;



/**
 * @author springer
 *
 * Mating Query DAO, Data Access Object, takes a Mating DTO, Data Transfer Object
 * and creates the SQL
 *
 *
 */

public class MatingQueryDAO extends MySQLDAO   {

    // Recommended by lint for serializable classes
    private static final long serialVersionUID = 1L;
    private int maxQueryRows = 1000;

    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    /**
     * Default Constructor : doesn't do much!
     *
     */
    public MatingQueryDAO(){
        maxQueryRows = 1000;
    }

    public MatingQueryDAO(int maxRows){
        maxQueryRows = maxRows;
    }

    // Store the peices of the SQL query in these lists.
    private ArrayList<String> selectList = new ArrayList<String>();
    private ArrayList<String> fromList = new ArrayList<String>();
    private ArrayList<String> whereList = new ArrayList<String>();


    // Use the string constants and the addUnquie method for string lists to
    //  ensure unnessarly complex SQL statements.
    private final static String  mouseJoin  ="Left Join Mouse ON Mouse.`_mouse_key` = Mating.`_sire_key` \n";
    private final static String  dam1Join   ="Left Join Mouse as dam1M ON dam1M.`_mouse_key` = Mating.`_dam1_key` \n";
    private final static String  dam2Join   ="Left Join Mouse as dam2M ON dam2M.`_mouse_key` = Mating.`_dam2_key` \n";
    private final static String  strainJoin ="Left Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n";
    private final static String  dam1StrainJoin ="Left Join Strain as dam1S ON dam1S.`_strain_key` = dam1M.`_strain_key` \n";
    private final static String  dam2StrainJoin ="Left Join Strain as dam2S ON dam2S.`_strain_key` = dam2M.`_strain_key` \n";
    private final static String  matStrainJoin ="Left Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` \n";
    private final static String  containerJoin ="Left Join Container On Container.`_container_key` = Mouse.`_pen_key` \n";
    private final static String  crossStatusJoin ="Left Join cv_CrossStatus as MatingStatus On Mating.`_crossStatus_key` = MatingStatus.`_crossStatus_key` \n";
    
    Connection con = null;

    /**
     * query : Given a DTO return a jstl Result that can be used in the WebTier.
     *
     * This method simple called executeQuery with given DTO then translates the
     * ResultSet to a Result.
     *
     * @param mq - MatingQueryDTO which holds user selection in Mating Query page.
     * @return : jstl Result representing the MatingQuery.
     * @throws SQLException
     */
    public Result query(MatingQueryDTO mq) throws SQLException {
    // The javax.servlet.jsp.jstl.sql.Result that we return to the webTier
        Result resultData = null;

        try {
            // connection opened by executeQuery
            ResultSet resultSet = this.executeQuery(mq);

            if (resultSet != null) {
                queryDebug("Converting result set into result object", "selectQuery");
                resultData = ResultSupport.toResult(resultSet);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }


     /**
      * executeQuery : Given a MatingQueryDTO return a resultSet that represents a "MatingQuery"
      *
      * The mating queries are more than a simple SQL statement. Often multiple
      * Queries are made to fulfill the users request.  So the basic algorithm
      * is to parse the DTO into three SQL statements.
      * 1> Create Temporary Table;
      * 2> Create Insert Into Temporary Table.
      * 3> Select Temporary Table.
      *
      * Then
      *
      * @param mq       : MouseQueryDTO from the user interface.
      * @return ResultSets : SQL ResultSet.
      *
      *
      */
    public ResultSet executeQuery(MatingQueryDTO mq){


        // Spit out the value of MatingQueryDTO mq, look in jboss log
        debugDTOandSQL(mq);

        // Get SQL statements for Mating Query
        String sqlStatement[] = generateSQLQuery(mq);

        // Result Set use as a place holder to collect all the data.
        ResultSet myResult = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        // Some short hand used in constructing the LItter SubQuery
        // Litter Total Litters
        boolean TotalLittersSelected = false;
        // Litter Total Pups
        boolean TotalPupsSelected = false;
        // Litter TotalMales
        boolean TotalMalesSelected = false;
        // Litter TotalFemales
        boolean TotalFemalesSelected = false;
        // Litter TotalLittersDead
//        boolean LittersDeadSelected = false;
        // Litter BirthDates
        boolean BirthDatesSelectd = false;
        // Litter Field List
        ArrayList<String> litterSelectList = new ArrayList<String>();



        try {
            // Get databaes connection from MySQLDAO
            con = super.getConnection();


            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);

            // Create Temp Table
            stmt.execute(sqlStatement[0]);

            // Select Into Temp Table
            stmt.execute(sqlStatement[1]);

            // Get Result Set from Temp Table
            myResult = stmt.executeQuery(sqlStatement[2]);


            // -----------------------------------------------------------------
            // Update ResultSet with sub queries.

            // Use over and over for where clause.
            String searchName = "";

            if ( null == myResult ) {
            // queryDebug("MatingQueryDTO.subQuery  ", "************** isNull **********************");
            }

            // If by chance none of these are true then exit
            if (!(mq.isSelectDam1Genotype()     ||
                  mq.isSelectDam1PlugDate()     ||
                  mq.isSelectDam1Genotype()     ||
                  mq.isSelectDam2PlugDate()     ||
                  mq.isSelectSireGenotype()     ||
                  mq.isSelectTotalLitters()     ||
                  mq.isSelectLitterIDs()        ||
                  mq.isSelectTotalPups()        ||
                  mq.isSelectTotalMales()       ||
                  mq.isSelectTotalFemales()     ||
                  mq.isSelectTotalLittersDead() ||
                  mq.isSelectTotalPupsBornDead()||
                  mq.isSelectTotalPupsCulledAtWean()    ||
                  mq.isSelectTotalPupsMissingAtWean()   ||
                  mq.isSelectBirthDates()       ||
                  //MKA Edit, had to add these to get desired formatting of returned text.
                  mq.isSelectNeedsTyping()      ||
                  mq.isSelectWeanTime()         )) {
                    // Very Very Very Important.  Doesn't convert to Result with out the
                    // following line ->  myResult.beforeFirst();
                    myResult.beforeFirst();
                    return myResult;
            }


            // Just to make sure set set the row count to just before the first.
            myResult.beforeFirst();
            // Walk through the record set and fill in with sub queries.
            while ( myResult.next() ) {
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Dam1 GenoType Query  ... Dam1Genotypes
                if (mq.isSelectDam1Genotype()) {
                    // queryDebug("MatingQueryDTO.subQuery  ", "*******************  isSelectDam1Genotype  *****************");
                    // Get MouseID
                    searchName = myResult.getString("Dam1");
                    if ( null != searchName ){
                        ResultSet ret = null;
                        // Query for Genotypes
                        sqlStatement[0] = "Select Gene.labSymbol as ls ,  Genotype.allele1 as a1 , Genotype.allele2 as a2 " +
                                "FROM Genotype " +
                                "LEFT JOIN Gene ON  Genotype.`_gene_key` = Gene.`_gene_key` " +
                                "LEFT JOIN Mouse ON Mouse.`_mouse_key` = Genotype.`_mouse_key` " +
                                "WHERE " +
                                "Mouse.ID = '" + searchName.trim()  + "'; ";
                        preparedStatement =  con.prepareStatement(sqlStatement[0]);
                        ret =  preparedStatement.executeQuery();

                        if ( null != ret ){
                            // Get the Genotypes
                            ret.beforeFirst();
                            String genotypeReturn = "";
                            while(ret.next()){
                                if (ret != null) {
                                    genotypeReturn = genotypeReturn + 
                                            (ret.getString("ls") != null ? ret.getString("ls").trim() : "") + " " + 
                                            (ret.getString("a1") != null ? ret.getString("a1").trim() : "") + 
                                            (ret.getString("a2") != null ? "/" + ret.getString("a2").trim() : "") ;
                                    if ( ! ret.isLast() ) {
                                        genotypeReturn = genotypeReturn + " : ";
                                    }
                                }

                            }
                            ret.close();

                            // Update Cell
                            myResult.updateString("Dam1Genotypes", genotypeReturn);
                        }
                    }
                }

                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Dam1 PlugDate Query ....
                if (mq.isSelectDam1PlugDate() )  {
                    // queryDebug("MatingQueryDTO.subQuery  ", "*******************  isSelectDam1PlugDate *****************");
                    // Get MouseID
                    searchName = myResult.getString("Dam1");
                    int matingId = myResult.getInt("MatingID");
                    if ( null != searchName ) {
                        ResultSet ret = null;
                        // Prepare the SubQuery
                        String plugQuery = "SELECT plugDate " +
                                "FROM PlugDate " +
                                "LEFT JOIN Mouse ON Mouse.`_mouse_key` = PlugDate.`_mouse_key` " +
                                "LEFT JOIN Mating ON Mating.`_mating_key` = PlugDate.`_mating_key`  " +
                                "WHERE " +
                                "Mouse.ID = '" + searchName.trim() + "' AND " +
                                "Mating.matingID = " + matingId + " ;";

                        // Query for Genotypes ...
                        ret = null;
                        preparedStatement =  con.prepareStatement(plugQuery);
                        ret =  preparedStatement.executeQuery();

                        if ( null != ret ) {
                            // Get the Genotypes
                            ret.beforeFirst();
                            String plugDateReturn = "";
                            while(ret.next()){
                                plugDateReturn = plugDateReturn + ret.getString("plugDate").trim() ;
                                if ( ! ret.isLast() ) {
                                    plugDateReturn = plugDateReturn + " ";
                                }
                            }
                            ret.close();

                            // Update Cell
                            myResult.updateString("Dam1PlugDate", plugDateReturn);
                        }
                    }
                }

                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Dam2 GenoType Query  ... Dam1Genotypes
                if (mq.isSelectDam2Genotype()) {

                    // Get MouseID
                    searchName = myResult.getString("Dam2");
                    if ( null != searchName ) {
                        ResultSet ret = null;
                        // Query for Genotypes
                        String genotypeQuery = "Select Gene.labSymbol as ls ,  Genotype.allele1 as a1 , Genotype.allele2 as a2 " +
                                "FROM Genotype " +
                                "LEFT JOIN Gene ON  Genotype.`_gene_key` = Gene.`_gene_key` " +
                                "LEFT JOIN Mouse ON Mouse.`_mouse_key` = Genotype.`_mouse_key` " +
                                "WHERE " +
                                "Mouse.ID = '" + searchName.trim()  + "'; ";
                        preparedStatement = con.prepareStatement(genotypeQuery);
                        ret = preparedStatement.executeQuery();

                        if ( null != ret ){
                            // Get the Genotypes
                            ret.beforeFirst();
                            String genotypeReturn = "";
                            while(ret.next()){
                                genotypeReturn = genotypeReturn + ret.getString("ls").trim() + " " + ret.getString("a1").trim();
                                if(ret.getString("a2") != null){
                                    genotypeReturn +=  "/" +ret.getString("a2").trim();
                                }
                                if ( ! ret.isLast() ) {
                                    genotypeReturn = genotypeReturn + " ";
                                }
                            }
                            ret.close();

                            // Update Cell
                            myResult.updateString("Dam2Genotypes", genotypeReturn);
                        }
                    }
                }

                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Dam2 PlugDate Query ....
                if (mq.isSelectDam2PlugDate() )  {
                    // queryDebug("MatingQueryDTO.subQuery  ", "*******************  isSelectDam2PlugDate *****************");
                    // Get MouseID
                    searchName = myResult.getString("Dam2");
                    int matingId = myResult.getInt("MatingID");

                    if ( null != searchName ) {
                        ResultSet ret = null;
                        // Prepare SubQuery
                        String plugQuery = "SELECT plugDate " +
                                "FROM PlugDate " +
                                "LEFT JOIN Mouse ON Mouse.`_mouse_key` = PlugDate.`_mouse_key` " +
                                "LEFT JOIN Mating ON Mating.`_mating_key` = PlugDate.`_mating_key`  " +
                                "WHERE " +
                                "Mouse.ID = '" + searchName.trim() + "' AND " +
                                "Mating.matingID = " + matingId + " ;";

                        // Query for Genotypes ...
                        ret = null;
                        preparedStatement =  con.prepareStatement(plugQuery);
                        ret =  preparedStatement.executeQuery();

                        if ( null != ret ) {
                            // Get the Genotypes
                            ret.beforeFirst();
                            String plugDateReturn = "";
                            while(ret.next()){
                                plugDateReturn = plugDateReturn + ret.getString("plugDate").trim() ;
                                if ( ! ret.isLast() ) {
                                    plugDateReturn = plugDateReturn + " ";
                                }
                            }
                            ret.close();

                            // Update Cell
                            myResult.updateString("Dam2PlugDate", plugDateReturn);
                        }
                    }
                }

                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Sire GenoType Query  ... Dam1Genotypes
                if (mq.isSelectSireGenotype()) {
                    // queryDebug("MatingQueryDTO.subQuery  ", "*******************  isSelectSireGenotype  *****************");
                    // Get MouseID
                    searchName = myResult.getString("Sire");
                    if ( null != searchName ) {
                        ResultSet ret = null;
                        // Query for Genotypes
                        String genotypeQuery = "Select Gene.labSymbol as ls ,  Genotype.allele1 as a1 , Genotype.allele2 as a2 " +
                                "FROM Genotype " +
                                "LEFT JOIN Gene ON  Genotype.`_gene_key` = Gene.`_gene_key` " +
                                "LEFT JOIN Mouse ON Mouse.`_mouse_key` = Genotype.`_mouse_key` " +
                                "WHERE " +
                                "Mouse.ID = '" + searchName.trim()  + "'; ";

                        // Execute Query
                        preparedStatement = con.prepareStatement(genotypeQuery);
                        ret = preparedStatement.executeQuery();

                        if ( null != ret ) {
                            // Get the Genotypes
                            ret.beforeFirst();
                            String genotypeReturn = "";
                            while(ret.next()){
                                genotypeReturn = genotypeReturn + ret.getString("ls").trim() + " " + ret.getString("a1").trim();
                                if(ret.getString("a2") != null){
                                    genotypeReturn +=  "/" +ret.getString("a2").trim();
                                }
                                if ( ! ret.isLast() ) {
                                    genotypeReturn = genotypeReturn + " ";
                                }

                            }
                            ret.close();

                            // Update Cell
                            myResult.updateString("SireGenotypes", genotypeReturn);
                        }
                    }
                }
                //translate 
                if(mq.isSelectNeedsTyping()){
                    if(myResult.getString("NeedsTyping").equals("0")){
                        myResult.updateString("NeedsTyping", "no");
                    }
                    else{
                        myResult.updateString("NeedsTyping", "yes");
                    }
                }
                
                if(mq.isSelectWeanTime()){
                    if(myResult.getString("WeanTime").equals("0")){
                        myResult.updateString("WeanTime", "Extended (" + new DbSetupDAO().getJCMSExtWeanTime().getMTSValue() + ")");
                    }
                    else{
                        myResult.updateString("WeanTime", "Standard (" + new DbSetupDAO().getJCMSStandardWeanTime().getMTSValue() + ")");
                    }
                }
                
                if(mq.isSelectTotalPupsBornDead()){
                    ResultSet ret = null;
                    int matingKey = myResult.getInt("matingKey");
                    String totalDeadPupsQuery = "SELECT SUM(numberBornDead) AS totalPupsBornDead "
                            + "FROM Litter "
                            + "WHERE _mating_key = ?;";
                    
                    preparedStatement =  con.prepareStatement(totalDeadPupsQuery);
                    preparedStatement.setInt(1, matingKey);
                    
                    ret =  preparedStatement.executeQuery();
                    
                    if(null != ret){
                        ret.beforeFirst();
                        int totalPupsBornDead = 0;
                        while(ret.next()){
                            totalPupsBornDead = ret.getInt("totalPupsBornDead");
                        }
                        ret.close();
                        myResult.updateInt("TotalPupsBornDead", totalPupsBornDead);
                    }
                }
                
                if(mq.isSelectTotalPupsCulledAtWean()){
                    int matingKey = myResult.getInt("matingKey");
                    String totalCulledPupsQuery = "SELECT SUM(numberCulledAtWean) AS totalPupsCulledAtWean "
                            + "FROM Litter "
                            + "WHERE _mating_key = ?;";
                    
                    
                    ResultSet ret = null;
                    
                    preparedStatement =  con.prepareStatement(totalCulledPupsQuery);
                    preparedStatement.setInt(1, matingKey);
                    
                    ret =  preparedStatement.executeQuery();
                    
                    if(null != ret){
                        ret.beforeFirst();
                        int totalPupsCulledAtWean = 0;
                        while(ret.next()){
                            totalPupsCulledAtWean = ret.getInt("totalPupsCulledAtWean");
                        }
                        ret.close();
                        myResult.updateInt("TotalPupsCulledAtWean", totalPupsCulledAtWean);
                    }
                    
                }
                
                if(mq.isSelectTotalPupsMissingAtWean()){
                    int matingKey = myResult.getInt("matingKey");
                    String totalMissingPupsQuery = "SELECT SUM(numberMissingAtWean) AS totalPupsMissingAtWean "
                            + "FROM Litter "
                            + "WHERE _mating_key = ?;";
                    
                    
                    ResultSet ret = null;
                    
                    preparedStatement =  con.prepareStatement(totalMissingPupsQuery);
                    preparedStatement.setInt(1, matingKey);
                    
                    ret =  preparedStatement.executeQuery();
                    
                    if(null != ret){
                        ret.beforeFirst();
                        int totalPupsMissingAtWean = 0;
                        while(ret.next()){
                            totalPupsMissingAtWean = ret.getInt("totalPupsMissingAtWean");
                        }
                        ret.close();
                        myResult.updateInt("TotalPupsMissingAtWean", totalPupsMissingAtWean);
                    }                    
                } 
                if(mq.isSelectTotalLittersDead()){
                    int matingId = myResult.getInt("MatingID");
                    String totalLittersDeadQuery = "SELECT COUNT(*) AS deadLitterCount " +
                            "FROM Litter " +
                            "JOIN Mating ON Litter._mating_key = Mating._mating_key " +
                            "WHERE matingID = " + matingId + " AND status = 'B' " +
                            "GROUP BY `status`;";
                    ResultSet ret = null;
                    preparedStatement =  con.prepareStatement(totalLittersDeadQuery);
                    ret =  preparedStatement.executeQuery();

                    if ( null != ret ) {
                        ret.beforeFirst();
                        int littersDeadReturn = 0;
                        while(ret.next()){
                            littersDeadReturn = ret.getInt("deadLitterCount");
                        }
                        ret.close();

                        // Update Cell
                        myResult.updateInt("TotalLittersDead", littersDeadReturn);
                    }
                }
                if(mq.isSelectLitterIDs()){
                    int matingId = myResult.getInt("MatingID");
                    String litterIDsQuery = "SELECT litterID " +
                            "FROM Litter " +
                            "JOIN Mating ON Litter._mating_key = Mating._mating_key " +
                            "WHERE matingID = " + matingId;
                    ResultSet ret = null;
                    preparedStatement =  con.prepareStatement(litterIDsQuery);
                    ret =  preparedStatement.executeQuery();

                    if ( null != ret ) {
                        String litterIDs = "";
                        while(ret.next()){
                            if(ret.isLast()){
                                litterIDs = litterIDs + ret.getString("litterID");
                            }
                            else{
                                litterIDs = litterIDs + ret.getString("litterID") + ", ";
                            }
                        }
                        ret.close();

                        // Update Cell
                        myResult.updateString("LitterIDs", litterIDs);
                    }
                }
                if(mq.isSelectBirthDates()){
                    int matingId = myResult.getInt("MatingID");
                    String birthDatesQuery = "SELECT DATE_FORMAT(birthDate, '%c-%e-%Y') AS birthDate " +
                            "FROM Litter " +
                            "JOIN Mating ON Litter._mating_key = Mating._mating_key " +
                            "WHERE matingID = " + matingId + ";";                    
                    preparedStatement =  con.prepareStatement(birthDatesQuery);
                    ResultSet ret = null;
                    ret =  preparedStatement.executeQuery();
                    if ( null != ret ) {
                        ret.beforeFirst();
                        String birthDates = "";
                        while(ret.next()){
                            if(ret.isLast()){
                                birthDates = birthDates + ret.getString("birthDate");
                            }
                            else{
                                birthDates = birthDates + ret.getString("birthDate") + ", ";
                            }
                        }
                        ret.close();

                        // Update Cell
                        myResult.updateString("LitterBirthDates", birthDates);
                    }
                }

                // Litter SubQuery
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Empty before we start.
                litterSelectList.clear();

                if (mq.isSelectTotalLitters()){
                   TotalLittersSelected = true;
                   litterSelectList.add("count(`_litter_key`) as NumLitters");
                }

                if (mq.isSelectTotalPups()) {
                   TotalPupsSelected = true;
                   litterSelectList.add("Sum(totalBorn) as TotalPups");
                }

                // Litter TotalMales
                if (mq.isSelectTotalMales()) {
                   TotalMalesSelected = true;
                   litterSelectList.add("Sum(numMale) as NumMales");
                }
                // Litter TotalFemales
                if (mq.isSelectTotalFemales()) {
                   TotalFemalesSelected = true;
                   litterSelectList.add("Sum(numFemale) as NumFemales");
                }
                // Litter TotalLittersDead
//                if (mq.isSelectTotalLittersDead()) {
//                   LittersDeadSelected = true;
//                   litterSelectList.add("Max(birthDate) as TotalLittersDead");
//                }
                // Litter BirthDates
                if (mq.isSelectBirthDates()) {
                   BirthDatesSelectd = true;
                   litterSelectList.add("Min(birthDate) as minDOB");
                }


                // Do a Litter Subquery
                if (TotalPupsSelected  || TotalMalesSelected ||
                    TotalFemalesSelected || // LittersDeadSelected ||
                            BirthDatesSelectd ) {
                    // queryDebug("subQuery  ", "***********************************");
                    // Get the Mating ID
                    searchName = myResult.getString("MatingID");
                    if ( null != searchName ) {
                        StringBuffer litterQueryBuff = new StringBuffer();
                        litterQueryBuff.append( "Select \n " );

                        // queryDebug("subQuery  ", "***********************************");

                        int iCount = 0;
                        while ( iCount < litterSelectList.size() ){
                            // queryDebug("subQuery  ", Integer.toString(iCount) + "  *********************************");

                            litterQueryBuff.append(litterSelectList.get(iCount) );
                            if ( iCount + 1 != litterSelectList.size() ){
                                litterQueryBuff.append( ", \n " );
                            }
                            iCount++;
                        }
                        //  queryDebug("subQuery  ", "*********** blah blah ************************");

                        litterQueryBuff.append(" From Litter " + " Left Join Mating ON Mating.`_mating_key` = Litter.`_mating_key` " + " Where Mating.matingID = ").append(searchName).append( " ; ");
                        //  queryDebug("subQuery  ", "************* execute sub query **********************");
                        //  queryDebug("subQuery  ", litterQueryBuff.toString()   );
                        //  queryDebug("subQuery  ", "************* execute sub query **********************");

                        // Execute Query
                        ResultSet ret = null;
                        preparedStatement = con.prepareStatement(litterQueryBuff.toString());
                        ret = preparedStatement.executeQuery();

                        //  queryDebug("subQuery  ", "************  Get Litter Info ***********************");
                        if ( null != ret ) {
                        // Get the Litter Info
                        while(ret.next()){
                                if ( TotalLittersSelected && null != ret.getString("NumLitters")) {
                                    // Update Cell
                                    myResult.updateString("TotalLitters", ret.getString("NumLitters").trim() );
                                    myResult.updateRow();
                                }
                                if (TotalPupsSelected  && null != ret.getString("TotalPups")){
                                    // Update Cell
                                    myResult.updateString("TotalPups", ret.getString("TotalPups").trim() );
                                    myResult.updateRow();
                            }
                                if (TotalMalesSelected  && null != ret.getString("NumMales")) {
                                    // Update Cell
                                    myResult.updateString("TotalMales", ret.getString("NumMales").trim());
                                    myResult.updateRow();
                                }
                                if (TotalFemalesSelected && null != ret.getString("NumFemales")){
                                    // Update Cell
                                    myResult.updateString("TotalFemales", ret.getString("NumFemales").trim());
                                    myResult.updateRow();
                            }

                            }
                            ret.close();
                        }
                    }
                }

                // Reset the vars to false
                // Total Litters
                TotalLittersSelected = false;
                // Total Pups
                TotalPupsSelected = false;
                // Litter TotalMales
                TotalMalesSelected = false;
                // Litter TotalFemales
                TotalFemalesSelected = false;
                // Litter BirthDates
                BirthDatesSelectd = false;

                // Update row!
                myResult.updateRow();

            }  // While next

            // Very Very Very Important.  Doesn't convert to Rusult with out the
            // following line ->  myResult.beforeFirst();
            myResult.beforeFirst();

        } catch (SQLException ex) {
            Logger.getLogger(MouseQueryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myResult;
    }

    /**
     * dumpMouseQueryDTO
     * Use to determine the settings of a given DTO, useful in debugging.
     *
     * @param mq       : MouseQueryDTO from the user interface.
     * @return String  : A text description of the DTO.
     *
     */
    protected String dumpMatingQueryDTO(MatingQueryDTO mq){
        StringBuffer strBuff = new StringBuffer();
        int counter = 0;
        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        // Select Item ....
	// Mating ID
        strBuff.append("isSelectMatingID()=").append(mq.isSelectMatingID()).append("\n");

	// Mating Dates
        strBuff.append("isSelectMatingDates()=").append(mq.isSelectMatingDates()).append("\n");

	// Mating Status
        strBuff.append("isSelectMatingStatus()=").append(mq.isSelectMatingStatus()).append("\n");

        // Litter Strain
        strBuff.append("isSelectMatingStrain()=").append(mq.isSelectMatingStrain()).append("\n");

        // Litter Generation
        strBuff.append("isSelectLitterGeneration()=").append(mq.isSelectMatingGeneration()).append("\n");

        // Mating Owner
        strBuff.append("isSelectMatingOwner()=").append(mq.isSelectMatingOwner()).append("\n");

        // Pen ID
        strBuff.append("isSelectMatingPenId()=").append(mq.isSelectMatingPenId()).append("\n");

	// Pen Name
        strBuff.append("isSelectMatingPenName()=").append(mq.isSelectMatingPenName()).append("\n");

        // Dam 1 ID
        strBuff.append("isSelectDam1ID()=").append(mq.isSelectDam1ID()).append("\n");

	// Dam 1 Strain
        strBuff.append("isSelectDam1Strain()=").append(mq.isSelectDam1Strain()).append("\n");

	// Dam 1 Stock #
        strBuff.append("isSelectDam1Stock()=").append(mq.isSelectDam1Stock()).append("\n");

        // Dam 1 Generation
        strBuff.append("isSelectDam1Gen()=").append(mq.isSelectDam1Gen()).append("\n");


	// Dam 1 Up To Three Genotypes
        strBuff.append("isSelectDam1Gen()=").append(mq.isSelectDam1Genotype()).append("\n");

	// Dam 1 Date Of Birth
        strBuff.append("isSelectDam1DOB()=").append(mq.isSelectDam1DOB()).append("\n");

        // Dam 1 Plug Dates
        strBuff.append("isSelectDam1PlugDate()=").append(mq.isSelectDam1PlugDate()).append("\n");

        // Dam 2 ID
        strBuff.append("isSelectDam2ID()=").append(mq.isSelectDam2ID()).append("\n");

	// Dam 2 Strain
        strBuff.append("isSelectDam2Strain()=").append(mq.isSelectDam2Strain()).append("\n");

	// Dam 2 Stock #
        strBuff.append("isSelectDam2Stock()=").append(mq.isSelectDam2Stock()).append("\n");

        // Dam 2 Generation
        strBuff.append("isSelectDam2Gen()=").append(mq.isSelectDam2Gen()).append("\n");

	// Dam 2 Up To Three Genotypes
        strBuff.append("isSelectDam2Gen()=").append(mq.isSelectDam2Genotype()).append("\n");

	// Dam 2 Date Of Birth
        strBuff.append("isSelectDam2DOB()=").append(mq.isSelectDam2DOB()).append("\n");

        // Dam 2 Plug Dates
        strBuff.append("isSelectDam2PlugDate()=").append(mq.isSelectDam2PlugDate()).append("\n");

        // Sire ID
        strBuff.append("isSelectSireID()=").append(mq.isSelectSireID()).append("\n");

	// Sire Strain
        strBuff.append("isSelectSireStrain()=").append(mq.isSelectSireStrain()).append("\n");

        // Sire Stock #
        strBuff.append("isSelectSireStock()=").append(mq.isSelectSireStock()).append("\n");

	// Sire Generation
        strBuff.append("isSelectSireGen()=").append(mq.isSelectSireGen()).append("\n");

        // Sire Up To Three Genotypes
        strBuff.append("isSelectSireGenotype()=").append(mq.isSelectSireGenotype()).append("\n");

	// Sire Date Of Birth
        strBuff.append("isSelectSireDOB()=").append(mq.isSelectSireDOB()).append("\n");

        // Date Retired
        strBuff.append("isSelectDateRetired()=").append(mq.isSelectDateRetired()).append("\n");

	// Wean Time
        strBuff.append("isSelectWeanTime()=").append(mq.isSelectWeanTime()).append("\n");

	// Needs Typing
        strBuff.append("isSelectNeedsTyping()=").append(mq.isSelectNeedsTyping()).append("\n");


           //  Summarize Litter Information
        // Total number of litters
        strBuff.append("isSelectTotalLitters()=").append(mq.isSelectTotalLitters()).append("\n");
        // Total number of pups born
        strBuff.append("isSelectTotalLitters()=").append(mq.isSelectTotalPups()).append("\n");
        // Total number of males weaned
        strBuff.append("isSelectTotalMales()=").append(mq.isSelectTotalMales()).append("\n");
        // Total number of females weaned
        strBuff.append("isSelectTotalFemales()=").append(mq.isSelectTotalFemales()).append("\n");
        // Total number of litters born dead
        strBuff.append("isSelectTotalLittersDead()=").append(mq.isSelectTotalLittersDead()).append("\n");
        // First and last birth dates
        strBuff.append("isSelectBirthDates()=").append(mq.isSelectBirthDates()).append("\n");



        // Strain : List  Filter example
        // Mating ID
        MatingEntity mid =  mq.getMatingIDFrom();

        if ( null != mid ) {
                strBuff.append("MatingID From =");
                strBuff.append(mid.getMatingID());
        } else
                strBuff.append("MatingID From = null\n");

	// Mating Dates
        if ( null != mq.getMatingStartDate())
            strBuff.append("getMatingStartDate() =").append(zDateformatter.format(mq.getMatingStartDate()).trim()).append("\n");
        else
            strBuff.append("getMatingStartDate() = null \n");

        if (null != mq.getMatingEndDate())
            strBuff.append("mq.getMatingEndDate()  =").append(zDateformatter.format(mq.getMatingEndDate()).trim()).append("\n");
        else
            strBuff.append("mq.getMatingEndDate()  = null \n");


	// Mating Status
        counter = 0;
        List<CvCrossstatusEntity> lsList = mq.getCrossStatus();
        if ( null != lsList ) {
                strBuff.append("Mating Status - List =");
            while ( counter < lsList.size() ){
                strBuff.append( lsList.get(counter).getCrossStatus().trim());
                if ( counter + 1 !=  lsList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Mating Status - List  = null \n");
        

	// Litter Strain
        counter = 0;
        List<StrainEntity> lslist = mq.getLitterStrain();
        if ( null != lslist ) {
                strBuff.append("Litter Strain - List =");
            while ( counter < lslist.size() ){
                strBuff.append( lslist.get(counter).getStrainName().trim());
                if ( counter + 1 !=  lslist.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Litter Strain - List  = null \n");

	// Litter Generation
        counter = 0;
        List<CvGenerationEntity> geList =  mq.getLitterGeneration();
        if ( null != geList ) {
                strBuff.append("Litter Generation - List =");
            while ( counter < geList.size() ){
                strBuff.append(geList.get(counter).getGeneration().trim());
                if ( counter + 1 !=  geList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Litter Generation - List  = null \n");

	// Mating Owner
        counter = 0;
        List<OwnerEntity> oList = mq.getMatingOwner();
        if ( null != oList ) {
                strBuff.append("Mating Owner - List =");
            while ( counter < oList.size() ){
                if ( null !=  oList.get(counter).getOwner() ){
                    strBuff.append(oList.get(counter).getOwner().trim());
                }
                if ( counter + 1 !=  oList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Mating Owner - List  = null \n");

                
        // Pen ID - List
        List<ContainerEntity> cidList = mq.getPenNumber();
        if ( null != cidList ) {
                strBuff.append("PenID  - List =");
            counter = 0;
            while ( counter < cidList.size() ){
                strBuff.append(Integer.toString(cidList.get(counter).getContainerID()));
                if ( counter + 1 !=  cidList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("PenID  - List  = null \n");

        return strBuff.toString();
    }

    /**
     * debugDTOandSQL : Spit out the DTO selections and the Resulting SQL
     *      statements.
     *
     * @param mq
     */
    protected void debugDTOandSQL(MatingQueryDTO mq){
        if (qDebug) {
            queryDebug("query ", "**************** DTO **********************");
            String dtoSettings =  dumpMatingQueryDTO(mq);
            queryDebug("query ", "\n" + dtoSettings + "\n");
            queryDebug("query ", "**************** SQL **********************");
            String sqlStatement [] = generateSQLQuery(mq);
            queryDebug("query ", "**************** SQL [0] ******************");
            queryDebug("query ", "\n" + sqlStatement[0] + "\n");
            queryDebug("query ", "**************** SQL [1] ******************");
            queryDebug("query ", "\n" + sqlStatement[1] + "\n");
            queryDebug("query ", "**************** SQL [2] ******************");
            queryDebug("query ", "\n" + sqlStatement[2] + "\n");
            queryDebug("query ", "**************** END **********************");
        }
    }


   /**
     * generateSQLQuery
     * Interpret the values of the MouseQueryDTO and construct appropriate SQL
     * that represents the users desired query.
     *
     * @param MouseQueryDTO : Takes a DTO from user interface.
     * @return String       : Returns appropriate SQL for desired query.
     *
     */
    public String [] generateSQLQuery(MatingQueryDTO mq){        
        this.selectList.clear();
        this.fromList.clear();
        this.whereList.clear();
        int counter = 0;

        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

    //*****************************************
    // Go through the Select Options
    //*****************************************

        addUnquieProperty( "Mating", fromList);
        addUnquieProperty( "Mating._mating_key as matingKey", selectList);
        
        // Selected Items ....
	// Mating ID
        if (mq.isSelectMatingID()) {
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }

        // Mating Dates
        if (mq.isSelectMatingDates()) {
            addUnquieProperty( "Date(Mating.matingDate)  as MatingDate", selectList);
            addUnquieProperty( "Mating", fromList);
        }


	// Mating Status
        if (mq.isSelectMatingStatus()) {           
            addUnquieProperty( "MatingStatus.crossStatus as MatingStatus", selectList);
            addUnquieProperty( crossStatusJoin, fromList);
        }

        // Litter Strain
        if (mq.isSelectMatingStrain()) {
            addUnquieProperty( "MatS.strainName  as LitterStrain", selectList);
            addUnquieProperty( matStrainJoin, fromList);
        }

        // Litter Generation
        if (mq.isSelectMatingGeneration()) {
            addUnquieProperty( "Mating.generation as LitterGeneration", selectList);
            addUnquieProperty( "Mating", fromList);
        }

        // Mating Owner
        if ( mq.isSelectMatingOwner() ) {
            addUnquieProperty( "Mating.owner as MatingOwner", selectList);
            addUnquieProperty( "Mating", fromList);
        }

        // Pen ID
        if (mq.isSelectMatingPenId()){
            addUnquieProperty( "Container.containerID as PenID", selectList);
            addUnquieProperty( mouseJoin , fromList);
            addUnquieProperty( containerJoin , fromList);
        }


	// Pen Name
        if (mq.isSelectMatingPenName()){
            addUnquieProperty( "Container.containerName as PenName", selectList);
            addUnquieProperty( mouseJoin , fromList);
            addUnquieProperty( containerJoin , fromList);
        }

        // Dam 1 ID
        if ( mq.isSelectDam1ID() ){
            addUnquieProperty( "dam1M.ID as Dam1", selectList);
            addUnquieProperty( dam1Join , fromList);
        }

	// Dam 1 Strain
        if (mq.isSelectDam1Strain() ){
            addUnquieProperty( "dam1S.strainName as Dam1Strain", selectList);
            addUnquieProperty( dam1Join , fromList);
            addUnquieProperty( dam1StrainJoin , fromList);
        }


	// Dam 1 Stock #
        if (mq.isSelectDam1Stock() ){
            addUnquieProperty( "dam1S.jrNum as Dam1StockNumber", selectList);
            addUnquieProperty( dam1Join , fromList);
            addUnquieProperty( dam1StrainJoin , fromList);
        }

        // Dam 1 Generation
        if (mq.isSelectDam1Gen() ) {
            addUnquieProperty( "dam1M.generation as Dam1Generation", selectList);
            addUnquieProperty( dam1Join , fromList);
        }


	// Dam 1 Up To Three Genotypes
        if (mq.isSelectDam1Genotype() ) {
            // Dam1Genotypes is a text place holder for a requery.
            addUnquieProperty( "' ' as Dam1Genotypes", selectList);
            // Must have Dam1 Mouse ID if not selected
            addUnquieProperty( "dam1M.ID as Dam1", selectList);
            addUnquieProperty( dam1Join , fromList);
       }

	// Dam 1 Date Of Birth
        if (mq.isSelectDam1DOB()) {
            addUnquieProperty( "dam1M.birthDate as Dam1BirthDate", selectList);
            addUnquieProperty( dam1Join , fromList);
        }

        // Dam 1 Plug Dates
        if (mq.isSelectDam1PlugDate()) {
            addUnquieProperty( "' ' as Dam1PlugDate", selectList);
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "dam1M.ID as Dam1", selectList);
            addUnquieProperty( dam1Join , fromList);

        }


        // Dam 2 ID
        if ( mq.isSelectDam2ID() ){
            addUnquieProperty( "dam2M.ID as Dam2", selectList);
            addUnquieProperty( dam2Join , fromList);
        }

	// Dam 2 Strain
        if (mq.isSelectDam1Strain() ){
            addUnquieProperty( "dam2S.strainName as Dam2Strain", selectList);
            addUnquieProperty( dam2Join , fromList);
            addUnquieProperty( dam2StrainJoin , fromList);
        }

	// Dam 2 Stock #
        if (mq.isSelectDam2Stock() ){
            addUnquieProperty( "dam2S.jrNum as Dam2StockNumber", selectList);
            addUnquieProperty( dam2Join , fromList);
            addUnquieProperty( dam2StrainJoin , fromList);
        }

        // Dam 2 Generation
       if (mq.isSelectDam2Gen() ) {
            addUnquieProperty( "dam2M.generation as Dam2Generation", selectList);
            addUnquieProperty( dam2Join , fromList);
        }

	// Dam 2 Up To Three Genotypes
        if (mq.isSelectDam2Genotype() ) {
            // Dam2Genotypes is a text place holder for a requery.
            addUnquieProperty( "' ' as Dam2Genotypes", selectList);
            // Must have Dam2 Mouse ID if not selected
            addUnquieProperty( "dam2M.ID as Dam2", selectList);
            addUnquieProperty( dam2Join , fromList);
        }

	// Dam 2 Date Of Birth
        if (mq.isSelectDam2DOB()) {
            addUnquieProperty( "dam2M.birthDate as Dam2BirthDate", selectList);
            addUnquieProperty( dam2Join , fromList);
        }

        // Dam 2 Plug Dates
        if (mq.isSelectDam2PlugDate()) {
            addUnquieProperty( "' ' as Dam2PlugDate", selectList);
            // Must have Dam2 Mouse ID if not selected
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "dam2M.ID as Dam2", selectList);
            addUnquieProperty( dam2Join , fromList);
        }

        // Sire ID
        if ( mq.isSelectSireID() ){
            addUnquieProperty( "Mouse.ID as Sire", selectList);
            addUnquieProperty( mouseJoin , fromList);
        }


	// Sire Strain
        if (mq.isSelectSireStrain() ){
            addUnquieProperty( "Strain.strainName as SireStrain", selectList);
            addUnquieProperty( mouseJoin , fromList);
            addUnquieProperty( strainJoin , fromList);
        }

        // Sire Stock #
        if (mq.isSelectSireStock()){
            addUnquieProperty( "Strain.jrNum as SireStockNumber", selectList);
            addUnquieProperty( mouseJoin , fromList);
            addUnquieProperty( strainJoin , fromList);
        }

	// Sire Generation
        if (mq.isSelectSireGen() ) {
            addUnquieProperty( "Mouse.generation as SireGeneration", selectList);
            addUnquieProperty( mouseJoin , fromList);
        }

        // Sire Up To Three Genotypes
        if (mq.isSelectSireGenotype() ) {
            // SireGenotypes is a text place holder for a requery.
            addUnquieProperty( "' ' as SireGenotypes", selectList);
            // Must have Sire Mouse ID if not selected
            addUnquieProperty( "Mouse.ID as Sire", selectList);
            addUnquieProperty( mouseJoin , fromList);
        }


	// Sire Date Of Birth
       if (mq.isSelectSireDOB()) {
            addUnquieProperty( "Mouse.birthDate as SireBirthDate", selectList);
            addUnquieProperty( mouseJoin , fromList);
        }

        // Date Retired
        if (mq.isSelectDateRetired()) {
            addUnquieProperty( "Mating.retiredDate as RetireDate", selectList);
            addUnquieProperty( "Mating" , fromList);
        }
 
	// Wean Time
        if (mq.isSelectWeanTime()) {
            addUnquieProperty( "Mating.weanTime as WeanTime", selectList);
            addUnquieProperty( "Mating" , fromList);
        }
 
	// Needs Typing
        if (mq.isSelectNeedsTyping()) {
            addUnquieProperty( "Mating.needsTyping as NeedsTyping", selectList);
            addUnquieProperty( "Mating" , fromList);
        }

        //  Summarize Litter Information
        // Total number of litters
        if (mq.isSelectTotalLitters()){
            addUnquieProperty( "' ' as TotalLitters", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);

        }
        // Total number of pups born
        if (mq.isSelectTotalPups()){
            addUnquieProperty( "' ' as TotalPups", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of pups born
        if (mq.isSelectTotalPupsBornDead()){
            addUnquieProperty( "' ' as TotalPupsBornDead", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of pups born
        if (mq.isSelectTotalPupsMissingAtWean()){
            addUnquieProperty( "' ' as TotalPupsMissingAtWean", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of pups born
        if (mq.isSelectTotalPupsCulledAtWean()){
            addUnquieProperty( "' ' as TotalPupsCulledAtWean", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of males weaned
        if (mq.isSelectTotalMales()){
            addUnquieProperty( "' ' as TotalMales", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of females weaned
        if (mq.isSelectTotalFemales()){
            addUnquieProperty( "' ' as TotalFemales", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // Total number of litters born dead
        if (mq.isSelectTotalLittersDead()){
            addUnquieProperty( "' ' as TotalLittersDead", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // All Litter IDs
        if (mq.isSelectLitterIDs()){
            addUnquieProperty( "' ' as LitterIDs", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }
        // First and last birth dates
        if (mq.isSelectBirthDates()){
            addUnquieProperty( "' ' as LitterBirthDates", selectList);
            // Need MatingID for subquery.
            addUnquieProperty( "Mating.matingID as MatingID", selectList);
            addUnquieProperty( "Mating", fromList);
        }




        //*****************************************
        // Go through the Filter Options
        //*****************************************
        //    Mating ID
        // Mating ID Equals
        if (mq.getmIDFrom() > 0){
            if (0 == mq.getMatingFilter().toString().trim().compareTo("Equals")) {
                addUnquieProperty( "Mating", fromList);
                addUnquieProperty( "Mating.matingID = " +
                        Integer.toString(mq.getmIDFrom()) +
                        " " , whereList);
            }
        }
        // Mating ID Between

        if ( (mq.getmIDFrom() > 0) || (mq.getmIDTo() > 0) ) {
            if (0 == mq.getMatingFilter().toString().trim().compareTo("Between")) {
                addUnquieProperty( "Mating", fromList);
                addUnquieProperty( " Mating.matingID BETWEEN " +
                        Integer.toString(mq.getmIDFrom())  +
                        " AND " +
                        Integer.toString(mq.getmIDTo()) +
                        " " , whereList);
            }
        }
        
        // Pen ID Between
        if ( (mq.getPenIDFrom() > 0) || (mq.getPenIDTo() > 0) ) {
            if (0 == mq.getPenIdFilter().toString().trim().compareTo("Between")) {
                addUnquieProperty( containerJoin, fromList);
                addUnquieProperty( " (Container.containerID BETWEEN " +
                        mq.getPenIDFrom() + " AND " +
                        mq.getPenIDTo() + ")" , whereList);
            }
        }

        // Pen name
        if (null != mq.getpName() && 0 != mq.getpName().compareTo("") ){
            if (0 == mq.getPenNameFilter().toString().trim().compareTo("Contains")) {
                addUnquieProperty( containerJoin, fromList);
                addUnquieProperty( " Container.containerName LIKE '%" +
                        mq.getpName() + "%'" , whereList);
            }
        }
        
        //	Mating Dates  - Between
        if ( null != mq.getMatingStartDate() && null != mq.getMatingEndDate() ){
            addUnquieProperty( "Mating", fromList);
            addUnquieProperty("Mating.matingDate Between '" + zDateformatter.format(mq.getMatingStartDate() ).trim() + "'  AND '" + zDateformatter.format(mq.getMatingEndDate()).trim() + "'"  , whereList);
        }

        // Current to End
        if ( null == mq.getMatingStartDate() && null != mq.getMatingEndDate() ){
             addUnquieProperty( "Mating", fromList);
             addUnquieProperty("Mating.matingDate Between CURDATE()  AND '" + zDateformatter.format(mq.getMatingEndDate()).trim() + "'"  , whereList);

        }

        // Start to End
        if ( null != mq.getMatingStartDate() && null == mq.getMatingEndDate() ){
             addUnquieProperty( "Mating", fromList);
             addUnquieProperty("Mating.matingDate Between '" + zDateformatter.format(mq.getMatingStartDate()).trim() + "'  AND CURDATE()"  , whereList);

        }


        // Litter Strain
        counter = 0;
        List<StrainEntity> lsList = mq.getLitterStrain();
        if ( null != lsList && counter < lsList.size()) {
                addUnquieProperty( matStrainJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            while ( counter < lsList.size() ){
                myWhere.append(" MatS.strainName = '").append( lsList.get(counter).getStrainName()).append("' ");
                if ( counter + 1 !=  lsList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }
        
        // Mating status
        counter = 0;
        List<CvCrossstatusEntity> lmList = mq.getCrossStatus();
        if ( null != lmList && counter < lmList.size()) {
                addUnquieProperty( crossStatusJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            while ( counter < lmList.size() ){
                myWhere.append(" MatingStatus.crossStatus = '").append( lmList.get(counter).getCrossStatus()).append("' ");
                if ( counter + 1 !=  lmList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }


        //	Litter Generation
        counter = 0;
        List<CvGenerationEntity> lgList = mq.getLitterGeneration();
        if ( null != lgList && counter < lgList.size()) {
            addUnquieProperty( "Mating", fromList);
            StringBuffer myWhere = new StringBuffer();
            while ( counter < lgList.size() ){
                    myWhere.append(" Mating.generation = '").append(lgList.get(counter).getGeneration().trim()).append("' ");
                if ( counter + 1 !=  lgList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        //	Mating Owner
        counter = 0;
        List<OwnerEntity> oList =  mq.getMatingOwner();
        if ( null != oList &&  counter < oList.size() ) {
            addUnquieProperty( "Mating", fromList);
            StringBuffer myWhere = new StringBuffer();
            while ( counter < oList.size() ){

                myWhere.append(" Mating.owner = '").append(oList.get(counter).getOwner().trim()).append("' ");
                if ( counter + 1 !=  oList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Pen ID
        counter = 0;
        List<ContainerEntity> cidList = mq.getPenNumber();
        if ( null != cidList && counter < cidList.size() ) {
            addUnquieProperty( mouseJoin, fromList);
            addUnquieProperty( containerJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            while ( counter < cidList.size() ){
                    myWhere.append(" Container.containerID = ").append(Integer.toString(cidList.get(counter).getContainerID())).append(" ");
                if ( counter + 1 !=  cidList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }



        //*****************************************
        // Build SQL Statments
        //*****************************************
            StringBuffer    createBuffer = new StringBuffer();
            StringBuffer    selectBuffer  = new StringBuffer();

        // Get somewhat random temp table name.
            GregorianCalendar calendar = new GregorianCalendar();
            String tempTable = "MyTemp" + Long.toString(calendar.getTimeInMillis());


        // *** Create ***  MyTemp must be Dynamic
            createBuffer.append("CREATE TEMPORARY TABLE IF NOT EXISTS ").append(tempTable).append(" (\n `_temp_key`  INT NOT NULL AUTO_INCREMENT ,");


        // ***  Select  ***
            counter = 0 ;
            int pos = 0 ;
            selectBuffer.append("Insert INTO ").append(tempTable).append(" ( \n");


            counter = 0;
            while ( counter < selectList.size()){
                // Create Buffer Stuff
                pos = selectList.get(counter).toString().indexOf("as");
                createBuffer.append("\n").append(selectList.get(counter).toString().substring(pos + 2)).append(" VARCHAR(256) collate utf8_unicode_ci NOT NULL default ''");
                selectBuffer.append(selectList.get(counter).toString().substring(pos + 2) );
                if ((counter + 1) != selectList.size()) {
                    createBuffer.append(",\n");
                    selectBuffer.append(" , ");
                }

                counter++;
            }

            queryDebug("genQuery", "*******************  build query *****************");

            String [] matingQuery = new String[3];
            // xxx Remove const and replace with value from db.
            matingQuery = buildQuery(selectList,fromList,whereList, maxQueryRows );
            // ==============
            queryDebug("genQuery", "*******************  query built *****************");

            // debug
            for(int i=0; i<matingQuery.length; ++i) {
                System.out.println("Mouse query " + matingQuery[i]);
            }

            // ==============
            return (matingQuery);
  }

    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {

        if (qDebug) {
            getLogger().logInfo(formatLogMessage("MatingQueryDTO." +  method, message));
        }

    }

}
