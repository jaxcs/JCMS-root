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
 * MouseQueryDAO.java
 *
 * Created on November 07, 2010
 */

package jcms.integrationtier.dao;


import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.dto.MouseQueryDTO;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.jstl.sql.Result;


// Parsing Dates
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Entities
import java.util.List;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;

//dtos
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
//import org.apache.xml.serializer.utils.Utils;
import static org.jax.cs.common.Utils.*;

/**
 *
 * @author springer
 *
 * Mouse Query DAO, Data Access Object, takes a Mouse DTO, Data Transfer Object
 * and creates the SQL Statements and executes them and returns a Result or
 * ResultSet.
 *
 */

public class MouseQueryDAO extends MySQLDAO   {

    // Recommended by lint if your class is serialiable 
    private static final long serialVersionUID = 1L;
    private int maxQueryRows = 1000;
    /**
     * Default Constructor
     */
    public MouseQueryDAO(){
        //
        maxQueryRows = 1000;

    }

    public MouseQueryDAO(int maxRows){
        //
        maxQueryRows = maxRows;
    }

    // Building blocks of a SQL query.
    private ArrayList<String> selectList = new ArrayList<String>();
    private ArrayList<String> fromList = new ArrayList<String>();
    private ArrayList<String> whereList = new ArrayList<String>();
    private ArrayList<String> groupByList = new ArrayList<String>();

    // Use the string constants and the addUnquie method for string lists to
    //  ensure unnessarly complex SQL statements.
    private final static String  strainJoin ="Left Join Strain On Strain.`_strain_key` = Mouse.`_strain_key` \n";
    private final static String  litterJoin ="Left Join Litter On Litter.`_litter_key` = Mouse.`_litter_key` \n";
    private final static String  containerJoin ="Left Join Container On Container.`_container_key` = Mouse.`_pen_key` \n";
    private final static String  containerHistoryJoin = "Left Join ContainerHistory ON Container.`_containerHistory_key` = ContainerHistory.`_containerHistory_key` \n";
    private final static String  roomJoin = "Left Join Room ON ContainerHistory.`_room_key` = Room.`_room_key` \n";
    private final static String  mouseUsageJoin ="Left Join MouseUsage On MouseUsage.`_mouse_key` = Mouse.`_mouse_key` \n";
    private final static String  genotypeJoin ="Left Join Genotype On Genotype.`_mouse_key` = Mouse.`_mouse_key` \n";
    private final static String  geneJoin ="Left Join Gene On Genotype.`_gene_key` = Gene.`_gene_key` \n";
    private final static String  matingJoin ="Left Join Mating On (Mating.`_dam1_key` = Mouse.`_mouse_key` OR Mating.`_dam2_key` = Mouse.`_mouse_key` OR Mating.`_sire_key` = Mouse.`_mouse_key` ) \n";
    private final static String  useScheduleJoin ="Left Join UseSchedule On Mouse.`_mouse_key` = UseSchedule.`_mouse_key` \n";
    private final static String  useScheduleTermJoin ="Left Join UseScheduleTerm On UseSchedule.`_useScheduleTerm_key` = UseScheduleTerm.`_useScheduleTerm_key` \n";
    private final static String  phenotypeJoin = "Left Join PhenotypeMouseLink on Mouse.`_mouse_key` = PhenotypeMouseLink.`_mouse_key` \n";
    private final static String  phenotypeTermJoin = "Left Join cv_Phenotype on PhenotypeMouseLink.`_phenotype_key` = cv_Phenotype.`_phenotype_key` \n";
    
    private Connection con = null;

    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;



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
    public Result query(MouseQueryDTO mq) throws SQLException{

        ResultSet rs = executeQuery(mq);
        Result rss = null;
        // Very Very Very Important.  Doesn't convert to Rusult with out the
        // following line ->  myResult.beforeFirst();
        rs.beforeFirst();
        if (rs != null) {
            queryDebug("Converting result set into result object", "selectQuery");
            rss = ResultSupport.toResult(rs);
            queryDebug("row cnt in IT " + rss.getRowCount(), "selectQuery");
        }
        super.closeConnection(con);
        return rss;
    }

     /**
     * query : Given a DTO return a jstl Result that can be used in the WebTier.
     *
     * This method simple called executeQuery with given DTO then translates the
     * ResultSet to a Result.
     *
     * @param mq - MouseQueryDTO which holds user selection in Mating Query page.
     * @return : jstl Result representing the MatingQuery.
     *
     */
    public ResultSet executeQuery(MouseQueryDTO mq){

        // Spit out the value of MouseQueryDTO mq, look in jboss log
        debugDTOandSQL(mq);

        // Get SQL statements for Mouse Query
        String sqlStatement[] = generateSQLQuery(mq);

        // Result Set use as a place holder to collect all the data.
        ResultSet myResult = null;

        // Result Set used over and over for the subQueries.
        ResultSet ret = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
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
            // User over and over for where clause.
            String searchName = "";


            if ( null == myResult ) {
                queryDebug("MouseQueryDTO.subQuery  ", "************** isNull **********************");
            }
            
            //set whether the row is editable
            myResult.beforeFirst();
            while ( myResult.next() ) {          
                if(containsIgnoreCase(myResult.getString("hiddenOwner"), mq.getColonyManageOwners())){
                    myResult.updateString("editable", "true");
                }
                else{
                    myResult.updateString("editable", "false");
                }
                myResult.updateRow();
            }  

            // If by chance none of these are true then exit
            if (!(mq.isSelectMouseLitterMates()  ||
                  mq.isSelectMouseParents() ||
                  mq.isSelectMouseGenotype()  ||
                  mq.isSelectMouseGenotypeDate()  ||
                  mq.isSelectMouseUse()  ||
                  mq.isSelectMouseMating() ||
                  mq.isSelectMousePhenotypes() ||  
                  mq.isSelectUseSchedules()))
                {
                    // Very Very Very Important.  Doesn't convert to Result with out the
                    // following line ->  myResult.beforeFirst();
                    myResult.beforeFirst();
                    return myResult;
                }


            // Just to make sure set set the row count to just before the first.
            myResult.beforeFirst();
                while ( myResult.next() ) {
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    // Find Litter Mates
                    if (mq.isSelectMouseLitterMates()) {
                        // Get MouseID
                        searchName = myResult.getString("LitterID");
                        if ( null != searchName ) {
                            // Query for Genotypes
                            String subQueryStatement = "Select Mouse.ID as MouseID " +
                                    " From Mouse " +
                                    " LEFT JOIN Litter ON Litter._litter_key = Mouse._litter_key " +
                                    " WHERE " +
                                    " Litter.litterID = '" + searchName.trim() + "'; ";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get Litter Mates
                            String litterMateReturn = "";
                            while(ret.next()){
                                litterMateReturn = litterMateReturn + ret.getString("MouseID").trim() + " ";
                                if ( ! ret.isLast() ) {
                                    litterMateReturn = litterMateReturn + " ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("LitterMates", litterMateReturn);
                            //update row
                            myResult.updateRow();
                        }
                    }

                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    // Find Mouse Parents ....
                    if (mq.isSelectMouseParents() )  {
                        // Get MouseID
                        searchName = myResult.getString("LitterID");
                        if (null != searchName) {
                            // Prepare the SubQuery
                            String parentQuery = "Select D1.ID as Dam1, D2.ID as Dam2, SM.ID as Sire " +
                                    " From Litter " +
                                    " LEFT JOIN Mating ON Mating._mating_key =  Litter._mating_key " +
                                    " LEFT JOIN Mouse as D1 ON Mating._dam1_key = D1._mouse_key " +
                                    " LEFT JOIN Mouse as D2 ON Mating._dam2_key = D2._mouse_key " +
                                    " LEFT JOIN Mouse as SM ON Mating._sire_key = SM._mouse_key  " +
                                    " where Litter.litterID = '" + searchName.trim() + "' ;";

                            // Query for Mouse Parents ...
                            ret = null;
                            preparedStatement =  con.prepareStatement(parentQuery);
                            ret =  preparedStatement.executeQuery();

                            // Get the Genotypes
                            if ( null != ret ){
                                while(ret.next()){
                                    if ( null != ret.getString("Dam1") )  myResult.updateString("Dam1", ret.getString("Dam1").trim());
                                    if ( null != ret.getString("Dam2") )  myResult.updateString("Dam2", ret.getString("Dam2").trim());
                                    if ( null != ret.getString("Sire") )  myResult.updateString("Sire", ret.getString("Sire").trim());
                                }
                                ret.close();
                            }
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    
                    if(mq.isSelectMouseGenotype()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for Genotypes
                            String subQueryStatement = 
                                    "SELECT CONCAT(Gene.labSymbol, ' ', COALESCE(Genotype.allele1), ' ', COALESCE(Genotype.allele2,'')) AS Genotype "
                                    + "FROM Genotype "
                                    + "LEFT JOIN Mouse ON Genotype._mouse_key = Mouse._mouse_key "
                                    + "LEFT JOIN Gene ON Genotype._gene_key = Gene._gene_key "
                                    + "WHERE Mouse.ID = '" + searchName.trim() + "';";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get Litter Mates
                            String genotypeReturn = "";
                            while(ret.next()){
                                genotypeReturn = genotypeReturn + ret.getString("Genotype").trim();
                                if ( ! ret.isLast() ) {
                                    genotypeReturn = genotypeReturn + "; ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("Genotype", genotypeReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    
                    if(mq.isSelectUseSchedules()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for Use Schedules
                            String subQueryStatement = 
                                    "SELECT useScheduleTermName "
                                    + "FROM UseSchedule "
                                    + "LEFT JOIN Mouse ON UseSchedule._mouse_key = Mouse._mouse_key "
                                    + "LEFT JOIN UseScheduleTerm ON UseSchedule._useScheduleTerm_key = UseScheduleTerm._useScheduleTerm_key "
                                    + "WHERE Mouse.ID = '" + searchName.trim() + "';";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get use schedules
                            String useScheduleReturn = "";
                            while(ret.next()){
                                useScheduleReturn = useScheduleReturn + ret.getString("useScheduleTermName").trim();
                                if ( ! ret.isLast() ) {
                                    useScheduleReturn = useScheduleReturn + "; ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("UseScheduleTerms", useScheduleReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    
                    if(mq.isSelectMousePhenotypes()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for phenotype terms
                            String subQueryStatement = 
                                    "SELECT DISTINCT ptName.phenotype AS PhenotypeTerms "
                                    + "FROM cv_Phenotype, PhenotypeMouseLink "
                                    + "LEFT JOIN Mouse ON PhenotypeMouseLink._mouse_key = Mouse._mouse_key "
                                    + "LEFT JOIN cv_Phenotype AS ptName ON PhenotypeMouseLink._phenotype_key = ptName._phenotype_key "
                                    + "WHERE Mouse.ID = '" + searchName.trim() + "';";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get phenotype terms
                            String phenotypeReturn = "";
                            while(ret.next()){
                                phenotypeReturn = phenotypeReturn + ret.getString("PhenotypeTerms").trim();
                                if ( ! ret.isLast() ) {
                                    phenotypeReturn = phenotypeReturn + "; ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("PhenotypeTerms", phenotypeReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                                        
                    if(mq.isSelectMouseGenotypeDate()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for Genotypes
                            String subQueryStatement = 
                                    "SELECT Date(Genotype.gtDate) AS gtDate "
                                    + "FROM Genotype "
                                    + "LEFT JOIN Mouse ON Genotype._mouse_key = Mouse._mouse_key "
                                    + "WHERE Mouse.ID = '" + searchName.trim() + "';";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get Litter Mates
                            String gtDateReturn = "";
                            while(ret.next()){
                                if(ret.getString("gtDate") != null){
                                        gtDateReturn = gtDateReturn + ret.getString("gtDate").trim();
                                        if ( ! ret.isLast() ) {
                                                gtDateReturn = gtDateReturn + " ";
                                        }
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("GenotypeDate", gtDateReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    
                    if(mq.isSelectMouseUse()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for Genotypes
                            String subQueryStatement = 
                                    "SELECT MouseUsage.`use` AS `use` "
                                    + "FROM MouseUsage "
                                    + "JOIN Mouse ON MouseUsage._mouse_key = Mouse._mouse_key "
                                    + "WHERE Mouse.ID = '" + searchName.trim() + "';";

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get Litter Mates
                            String useReturn = "";
                            while(ret.next()){
                                useReturn = useReturn + ret.getString("use").trim();
                                if ( ! ret.isLast() ) {
                                    useReturn = useReturn + "; ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("MouseUse", useReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    
                    if(mq.isSelectMouseMating()){
                        // Get MouseID
                        searchName = myResult.getString("MouseID");
                        if ( null != searchName ) {
                            // Query for Genotypes
                            String subQueryStatement = 
                                    "SELECT matingID " + 
                                    "FROM Mating " + 
                                    "JOIN Mouse AS dam1 " + 
                                    "ON Mating._dam1_key = dam1._mouse_key " + 
                                    "LEFT JOIN Mouse AS dam2 " + 
                                    "ON Mating._dam2_key = dam2._mouse_key " +
                                    "JOIN Mouse AS sire " + 
                                    "ON Mating._sire_key = sire._mouse_key " + 
                                    "WHERE dam1.ID = '" + searchName.trim() + "' " + 
                                    "OR dam2.ID = '" + searchName.trim() + "' " +
                                    "OR sire.ID = '" + searchName.trim() + "';" ;
                            

                            preparedStatement = con.prepareStatement(subQueryStatement);
                            ret = preparedStatement.executeQuery();

                            // Get Litter Mates
                            String matingIDReturn = "";
                            while(ret.next()){
                                matingIDReturn = matingIDReturn + ret.getString("matingID").trim();
                                if ( ! ret.isLast() ) {
                                    matingIDReturn = matingIDReturn + "; ";
                                }
                            }
                            ret.close();
                            // Update Cell
                            myResult.updateString("MatingID", matingIDReturn);
                            // Update row!
                            myResult.updateRow();
                        }
                    }
                    myResult.updateRow();
                }  // While next

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
    protected String dumpMouseQueryDTO(MouseQueryDTO mq){
        StringBuffer strBuff = new StringBuffer();
        int counter = 0;
        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        queryDebug("dumpMouseQueryDTO  ", "** Select Items **");

        // Mouse ID  Mouse.ID
        strBuff.append("isSelectMouseID()=").append(mq.isSelectMouseID()).append("\n");
       

        // Age Mouse.
        strBuff.append("isSelectMouseAge()=").append(mq.isSelectMouseAge()).append("\n");

        // Age Measure
        strBuff.append("getAgeMeasure()=").append(mq.getAgeMeasure().trim()).append("\n");

        // Date of Birth
        strBuff.append("sSelectMouseDOB()=").append(mq.isSelectMouseDOB()).append("\n");

        // Exit Date
        strBuff.append("sSelectMouseExitDate()=").append(mq.isSelectMouseExitDate()).append("\n");

        // Breeding Status
        strBuff.append("isSelectMouseBreedingStatus()=").append(mq.isSelectMouseBreedingStatus()).append("\n");

        // Origin
        strBuff.append("isSelectMouseOrigin() =").append(mq.isSelectMouseOrigin()).append("\n");

        // Life Status
        strBuff.append("isSelectMouseLifeStatus()  =").append(mq.isSelectMouseLifeStatus()).append("\n");

        // Strain
        strBuff.append("isSelectMouseStrain()  =").append(mq.isSelectMouseStrain()).append("\n");

        // Generation
        strBuff.append("isSelectMouseGeneration() =").append(mq.isSelectMouseGeneration()).append("\n");

        // Owner
        strBuff.append("isSelectMouseOwner() =").append(mq.isSelectMouseOwner()).append("\n");

        // Cause of Death
        strBuff.append("isSelectMouseCOD() =").append(mq.isSelectMouseCOD()).append("\n");

        // Litter #
        strBuff.append("isSelectMouseLitter() =").append(mq.isSelectMouseLitter()).append("\n");

        // Pen ID
        strBuff.append("isSelectMousePenID() =").append(mq.isSelectMousePenID()).append("\n");
        // Pen Name
        StringBuffer append = strBuff.append("isSelectMousePenName()  =").append(mq.isSelectMousePenName()).append("\n");

        // Protocol ID
        strBuff.append("isSelectMouseProtocolID() =").append(mq.isSelectMouseProtocolID()).append("\n");

        // Mouse Use
        strBuff.append("isSelectMouseUse() =").append(mq.isSelectMouseUse()).append("\n");

        // Genotype
        strBuff.append("isSelectMouseGenotype() =").append(mq.isSelectMouseGenotype()).append("\n");

        //Genotype Date
        strBuff.append("isSelectMouseGenotypeDate() =").append(mq.isSelectMouseGenotypeDate()).append("\n");

        //Matings
        strBuff.append("isSelectMouseMating() =").append(mq.isSelectMouseMating()).append("\n");

        // Littermates  -- Right Now only litter IDs
        strBuff.append("isSelectMouseLitterMates() =").append(mq.isSelectMouseLitterMates()).append("\n");

        // Parents    -- Right Now only Matings IDs
        strBuff.append("isSelectMouseParents() =").append(mq.isSelectMouseParents()).append("\n");

        // Coat Color
        strBuff.append("isSelectMouseCoatColor() =").append(mq.isSelectMouseCoatColor()).append("\n");

        // Diet
        strBuff.append("isSelectMouseDiet() =").append(mq.isSelectMouseDiet()).append("\n");

    //*****************************************
    // Go through the Filter Options
    //*****************************************
        queryDebug("dumpMouseQueryDTO  ", "** Filter Options **");

        strBuff.append("getMouseID() =").append(mq.getMouseID()).append("\n");
        strBuff.append("getMouseFilter() =").append(mq.getMouseFilter()).append("\n");

       if (null != mq.getMouseIDFrom() )
           strBuff.append("getMouseIDFrom().getId().trim() =").append(mq.getMouseIDFrom().getId().trim()).append("\n");
       else
           strBuff.append("getMouseIDFrom() = null \n");



       if (null != mq.getMouseIDTo() )
            strBuff.append("getMouseIDTo().getId().trim() =").append(mq.getMouseIDTo().getId().trim()).append("\n");
       else
            strBuff.append("getMouseIDTo() = null \n");

        // Sex - List
        List<CvSexEntity> sexList = mq.getSex();
        if (null != sexList) {
            strBuff.append("Sex - List =");
            counter = 0;
            while (counter < sexList.size()) {
                strBuff.append(sexList.get(counter).getAbbreviation().trim());
                if (counter + 1 != sexList.size()) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else {
            strBuff.append("Sex - List  = null \n");
        }

       // Age - Between, Greater, Less, Equals - Exclusive Option
        strBuff.append("getAgeFrom() =").append(Integer.toString(mq.getAgeFrom())).append("\n");
        strBuff.append("getAgeTo() =").append(Integer.toString(mq.getAgeTo())).append("\n");
        strBuff.append("getAgeFilter().toString().trim() =").append(mq.getAgeFilter().toString().trim()).append("\n");
        strBuff.append("getAgeMeasure().toString() =").append(mq.getAgeMeasure().toString()).append("\n");

        // Date of Birth - Between
      if ( null != mq.getDobStartDate() )
        strBuff.append("getDobStartDate() =").append(zDateformatter.format(mq.getDobStartDate()).trim()).append("\n");
      else
        strBuff.append("getDobStartDate() = null \n");
       

     if (null != mq.getDobEndDate() )
            strBuff.append("getDobEndDate()  =").append(zDateformatter.format(mq.getDobEndDate()).trim()).append("\n");
     else
            strBuff.append("getDobEndDate()  = null \n");

    // Exit Date - Between
      if ( null != mq.getExitStartDate() )
        strBuff.append("getExitStartDate() =").append(zDateformatter.format(mq.getExitStartDate()).trim()).append("\n");
      else
        strBuff.append("getExitStartDate() = null \n");


     if (null != mq.getExitEndDate() )
            strBuff.append("getExitEndDate() =").append(zDateformatter.format(mq.getExitEndDate()).trim()).append("\n");
     else
            strBuff.append("getExitEndDate()  = null \n");



        // Breeding Status - List
        List<CvBreedingStatusEntity> breedingStatus = mq.getBreedingStatus();
        if ( null != breedingStatus ) {
                strBuff.append("Breeding Status - List =");
            counter = 0;
            while ( counter < breedingStatus.size() ){
                strBuff.append( breedingStatus.get(counter).getBreedingStatus().trim());
                if ( counter + 1 !=  breedingStatus.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Breeding Status - List  = null \n");


        // Origin - List
        List<CvMouseOriginEntity> mouseOriginList = mq.getOrigin();
        if ( null != mouseOriginList ) {
                strBuff.append("Mouse Origin - List =");
            counter = 0;
            while ( counter < mouseOriginList.size() ){
                strBuff.append(mouseOriginList.get(counter).getMouseOrigin().trim());
                if ( counter + 1 !=  mouseOriginList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Mouse Origin - List  = null \n");

        // Life Status - List
        List<LifeStatusEntity> lifeStatusList = mq.getLifeStatus();
        if ( null != lifeStatusList ) {
                strBuff.append("Life Status - List =");
            counter = 0;
            while ( counter < lifeStatusList.size() ){
                strBuff.append( lifeStatusList.get(counter).getLifeStatus().trim());
                if ( counter + 1 !=  lifeStatusList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Life Status - List  = null \n");

        // Strain - List
        List<StrainEntity> seList = mq.getStrain();
        if ( null != seList ) {
                strBuff.append("Strain - List =");
            counter = 0;
            while ( counter < seList.size() ){
                strBuff.append(seList.get(counter).getStrainName().trim());
                if ( counter + 1 !=  seList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Strain - List  = null \n");

        // Generation - List
        List<CvGenerationEntity> geList = mq.getGeneration();
        if ( null != geList ) {
                strBuff.append("Generation - List =");
            counter = 0;
            while ( counter < geList.size() ){
                strBuff.append(geList.get(counter).getGeneration().trim());
                if ( counter + 1 !=  geList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Generation - List  = null \n");

        // Owner - List
        List<OwnerEntity> oList = mq.getOwner();
        if ( null != oList ) {
                strBuff.append("Owner - List =");
            counter = 0;
            while ( counter < oList.size() ){
                if ( null != oList.get(counter).getOwner() ) {
                    strBuff.append( oList.get(counter).getOwner().trim());
                }
                if ( counter + 1 !=  oList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Owner - List  = null \n");

        // Cause of Death - List
        List<CvCauseOfDeathEntity> codList = mq.getCauseOfDeath();
        if ( null != codList ) {
                strBuff.append("Cause Of Death - List =");
            counter = 0;
            while ( counter < codList.size() ){
                strBuff.append( codList.get(counter).getCod().trim());
                if ( counter + 1 !=  codList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Cause Of Death - List  = null \n");

        // Litter # - List
        List<LitterEntity> lList = mq.getLitterNumber();
        if ( null != lList ) {
                strBuff.append("Litter #  - List =");
            counter = 0;
            while ( counter < lList.size() ){
                strBuff.append( lList.get(counter).getLitterID().trim());
                if ( counter + 1 !=  lList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Litter #  - List  = null \n");

                // Protocol ID - List
        List<CvMouseProtocolEntity> cmpList = mq.getProtocolID();
        if ( null != cmpList ) {
            strBuff.append("Protocol  - List =");
            counter = 0;
            while ( counter < cmpList.size() ){
                strBuff.append( cmpList.get(counter).getId()  );
                if ( counter + 1 !=  cmpList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } 
        else{
                  strBuff.append("Protocol - List  = null \n");
        }

        
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

        // Pen Name - List
        List<ContainerEntity> cndList = mq.getPenName();
        if ( null != cndList ) {
                strBuff.append("Pen Name  - List =");
            counter = 0;
            while ( counter < cndList.size() ){
                strBuff.append(  cndList.get(counter).getContainerID()  );
                if ( counter + 1 !=  cndList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
                  strBuff.append("Pen Name - List  = null \n");


        // Mouse Use - List
        List<CvMouseUseEntity> muList = mq.getMouseUse();
       if ( null != muList ) {
                strBuff.append("Mouse Use  - List =");
            counter = 0;
            while ( counter < muList.size() ){
                strBuff.append(  muList.get(counter).getMouseUse()  );
                if ( counter + 1 !=  muList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Mouse Use - List  = null \n");

        // Genotype - List
        List<GeneEntity> gList = mq.getGenotype();
       if ( null != gList ) {
                strBuff.append("Genotype  - List =");
            counter = 0;
            while ( counter < gList.size() ){
                strBuff.append(gList.get(counter).getGeneSymbol()  );
                if ( counter + 1 !=  gList.size() ) {
                    strBuff.append(" | ");
                }
                counter++;
            }
            strBuff.append("\n");
        } else
            strBuff.append("Genotype - List  = null \n");

    // Genotype Date - Between
        if ( null != mq.getGenotypeStartDate())
            strBuff.append("mq.getGenotypeStartDate() =").append(zDateformatter.format(mq.getGenotypeStartDate()).trim()).append("\n");
        else
            strBuff.append("mq.getGenotypeStartDate() = null \n");


        if (null != mq.getGenotypeEndDate() )
            strBuff.append("mq.getGenotypeEndDate()  =").append(zDateformatter.format(mq.getGenotypeEndDate()).trim()).append("\n");
        else
            strBuff.append("mq.getGenotypeEndDate()  = null \n");

        return strBuff.toString();

    }

   /**
     * debugDTOandSQL : Spit out the DTO selections and the Resulting SQL
     *      statements.
     *
     * @param mq
     */
    protected void debugDTOandSQL(MouseQueryDTO mq){
        if (qDebug)
        {
            queryDebug("Debug", "**************** DTO **********************");
            String dtoSettings =  dumpMouseQueryDTO(mq);
            queryDebug("Debug", "\n" + dtoSettings + "\n");
            queryDebug("Debug", "**************** SQL **********************");
            String sqlStatement[] = new String [3];
            sqlStatement = generateSQLQuery(mq);
            queryDebug("Debug", "**************** [0] **********************");
            queryDebug("Debug", "\n" + sqlStatement[0] + "\n");
            queryDebug("Debug", "**************** [1] **********************");
            queryDebug("Debug", "\n" + sqlStatement[1] + "\n");
            queryDebug("Debug", "**************** [2] **********************");
            queryDebug("Debug", "\n" + sqlStatement[2] + "\n");
            queryDebug("Debug", "**************** END **********************");
        }
  }


   /**
    * generateSQLQuery :
    *
    * Interpret the values of the MouseQueryDTO and construct appropriate SQL
    * that represents the users desired query.
    *
    * @param MouseQueryDTO : Takes a DTO from user interface.
    * @return String       : Returns appropriate SQL for desired query.
    *
    */
    public String []  generateSQLQuery(MouseQueryDTO mq){
        this.selectList.clear();
        this.fromList.clear();
        this.whereList.clear();
        int counter = 0;

        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

    //*****************************************
    // Go through the Select Options
    //*****************************************

        //default mouse_key
        /*
         * always add mousekey for group by clause at end, anything returning
         * more than one row (genotype, use schedule, littermates etc.) should be
         * handled in later part of execute query method above 
         */
        addUnquieProperty( "Mouse._mouse_key as MouseKey", selectList);
        addUnquieProperty( "Mouse", fromList);
        
        addUnquieProperty("Mouse.owner as hiddenOwner", selectList);
        addUnquieProperty( "Mouse", fromList);
        
        addUnquieProperty("' ' as editable", selectList);
        addUnquieProperty( "Mouse", fromList);

        // Mouse ID  Mouse.ID
        if (mq.isSelectMouseID()) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        
        // Mouse.newTag
        if(mq.isSelectReplacementTag()){
            addUnquieProperty( "Mouse.newTag as replacementTag", selectList);
            addUnquieProperty( "Mouse", fromList);
        }

        // Mouse.sex
        if (mq.isSelectMouseSex()) {
            addUnquieProperty( "Mouse.sex as Sex", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        
        // Mouse.comment
        if(mq.isSelectMouseComment()){
            addUnquieProperty("Mouse.comment as mouseComment", selectList);
            addUnquieProperty( "Mouse", fromList);
        }

        // Age Mouse.
        if (mq.isSelectMouseAge()) {
            // Days
            if ( 0 == mq.getAgeMeasure().toString().trim().compareTo("Days")){
                addUnquieProperty("" +
                        "TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) as AgeInDays", selectList);
                addUnquieProperty( "Mouse", fromList);
            }
            // Weeks
            if ( 0 == mq.getAgeMeasure().toString().trim().compareTo("Weeks")){
                addUnquieProperty("TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) as AgeInWeeks", selectList);
                addUnquieProperty( "Mouse", fromList);
            }
            // Months
            if ( 0 == mq.getAgeMeasure().toString().trim().compareTo("Months")){
                addUnquieProperty("TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE())  as AgeInMonths", selectList);
                addUnquieProperty( "Mouse", fromList);
            }
            // Years
            if ( 0 == mq.getAgeMeasure().toString().trim().compareTo("Years")){
                addUnquieProperty( "TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) as AgeInYears", selectList);
                addUnquieProperty( "Mouse", fromList);
            }
        }

        // Date of Birth
        if (mq.isSelectMouseDOB()) {
            addUnquieProperty( "Date(Mouse.birthDate) as BirthDate", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Exit Date
        if (mq.isSelectMouseExitDate() ) {
            addUnquieProperty( "Date(Mouse.exitDate) as ExitDate", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Breeding Status
        if (mq.isSelectMouseBreedingStatus() ) {
            addUnquieProperty( "Mouse.breedingStatus as BreedingStatus", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Origin
        if (mq.isSelectMouseOrigin() ) {
            addUnquieProperty( "Mouse.origin as Origin", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Life Status
        if (mq.isSelectMouseLifeStatus() ) {
            addUnquieProperty( "Mouse.lifeStatus as LifeStatus", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Protocol ID
        if (mq.isSelectMouseProtocolID() ) {
            addUnquieProperty( "Mouse.protocol as Protocol", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Strain
        if (mq.isSelectMouseStrain() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "Strain.strainName as Strain", selectList);
            addUnquieProperty(strainJoin, fromList);
        }
        // Generation
        if (mq.isSelectMouseGeneration() ) {
            addUnquieProperty( "Mouse.generation as Generation", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Owner
        if (mq.isSelectMouseOwner() ) {
            addUnquieProperty( "Mouse.owner as Owner", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Cause of Death
        if (mq.isSelectMouseCOD() ) {
            addUnquieProperty( "Mouse.cod as CauseOfDeath", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Litter #
        if (mq.isSelectMouseLitter() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "Litter.litterID as LitterID", selectList);
            addUnquieProperty( litterJoin, fromList);
        }
        // Pen ID
        if (mq.isSelectMousePenID() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "Container.containerID as PenID", selectList);
            addUnquieProperty( containerJoin, fromList);
        }
        // Pen Name
        if (mq.isSelectMousePenName() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "Container.containerName as PenName", selectList);
            addUnquieProperty( containerJoin, fromList);
        }
        // Room Name
        if(mq.isSelectRoomName()){
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "Room.roomName as RoomName", selectList);
            addUnquieProperty( containerJoin, fromList);
            addUnquieProperty( containerHistoryJoin, fromList);
            addUnquieProperty( roomJoin, fromList);
        }
        
        // use schedules
        if (mq.isSelectUseSchedules() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as UseScheduleTerms", selectList);
        }
        // Mouse Use
        if (mq.isSelectMouseUse() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as MouseUse", selectList);
        //    addUnquieProperty( mouseUsageJoin, fromList);
        }
        
        // phenotypes
        if (mq.isSelectMousePhenotypes() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as PhenotypeTerms", selectList);
        }
        
        // Genotype
        if (mq.isSelectMouseGenotype() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as Genotype", selectList);
        //    addUnquieProperty( "' ' as Allele1", selectList);
        //    addUnquieProperty( "' ' as Allele2", selectList);
        //    addUnquieProperty( genotypeJoin, fromList);
        //    addUnquieProperty( geneJoin, fromList);
        }
        //Genotype Date
        if (mq.isSelectMouseGenotypeDate() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as GenotypeDate", selectList);
        //    addUnquieProperty( genotypeJoin, fromList);
        }
        //Matings
        if (mq.isSelectMouseMating() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as MatingID", selectList);
        //    addUnquieProperty( matingJoin, fromList);
        }
        // Littermates  
        if (mq.isSelectMouseLitterMates()) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' ' as LitterMates", selectList);
            addUnquieProperty( "Litter.litterID as LitterID", selectList);
            addUnquieProperty( litterJoin, fromList);
        }
        // Parents  
        if (mq.isSelectMouseParents() ) {
            addUnquieProperty( "Mouse.ID as MouseID", selectList);
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty( "' '  as Dam1", selectList);
            addUnquieProperty( "' '  as Dam2", selectList);
            addUnquieProperty( "' '  as Sire", selectList);
            addUnquieProperty( "Litter.litterID as LitterID", selectList);
            addUnquieProperty( litterJoin, fromList);
        }
        // Coat Color
        if (mq.isSelectMouseCoatColor() ) {
            addUnquieProperty( "Mouse.coatColor as CoatColor", selectList);
            addUnquieProperty( "Mouse", fromList);
        }
        // Diet
        if (mq.isSelectMouseDiet() ) {
            addUnquieProperty( "Mouse.diet as Diet", selectList);
            addUnquieProperty( "Mouse", fromList);
        }

    //*****************************************
    // Go through the Filter Options
    //*****************************************

        // Mouse ID - Equals, Contains,Between
        if (0 != mq.getMouseID().toString().trim().compareTo("")) {
            // Contains
            if (0 == mq.getMouseFilter().toString().trim().compareTo("Contains")) {
                addUnquieProperty( "Mouse", fromList);
                addUnquieProperty( "Mouse.ID LIKE '%" +
                        mq.getMouseID().toString() +"%'" , whereList);
            }

            // Equals
            if (0 == mq.getMouseFilter().toString().trim().compareTo("Equals")) {
                addUnquieProperty( "Mouse", fromList);
                addUnquieProperty( "Mouse.ID = '" +
                        mq.getMouseID().toString().trim() + "'" , whereList);
            }
        }
        
        if(!("").equals(mq.getReplacementTag().trim())){
            //contains
            if(("Contains").equalsIgnoreCase(mq.getReplacementTagFilter().trim())){
                addUnquieProperty( "Mouse", fromList);
                addUnquieProperty( "Mouse.newTag LIKE '%" + mq.getReplacementTag().trim() + "%'" , whereList);
            }
            //equals
            if(("Equals").equalsIgnoreCase(mq.getReplacementTagFilter().trim())){
                addUnquieProperty( "Mouse", fromList);
                addUnquieProperty( "Mouse.newTag = '" + mq.getReplacementTag().trim() + "'" , whereList);
            }
        }

        // Sex - List
                    counter = 0;
        List<CvSexEntity> sexList = mq.getSex();
        if ( null != sexList &&  counter < sexList.size() ) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer sexWhere = new StringBuffer();
            sexWhere.append("(");
            while ( counter < sexList.size() ){
                sexWhere.append(" Mouse.sex = '").append(sexList.get(counter).getAbbreviation().trim()).append("' ");
                if ( counter + 1 !=  sexList.size() ) {
                    sexWhere.append(" OR ");
                }
                counter++;
            }
            sexWhere.append(")");
            addUnquieProperty( sexWhere.toString().trim() , whereList);
        }

        // Age - Between, Greater, Less, Equals - Exclusive Option
         if (0 == mq.getAgeFilter().toString().trim().compareTo("Equals")) {
             addUnquieProperty( "Mouse", fromList);
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Days")) {
                addUnquieProperty("(TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) = " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Weeks")) {
                addUnquieProperty("(TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) = " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Months")) {
                addUnquieProperty("(TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) = " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Years")) {
                addUnquieProperty("(TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) = " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
         }

         if (0 == mq.getAgeFilter().toString().trim().compareTo("Less Than")) {
             addUnquieProperty( "Mouse", fromList);
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Days")) {
                addUnquieProperty("(TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) < " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Weeks")) {
                addUnquieProperty("(TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) < " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Months")) {
                addUnquieProperty("(TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) < " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Years")) {
                addUnquieProperty("(TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) < " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }

         }

         if (0 == mq.getAgeFilter().toString().trim().compareTo("Greater Than")) {
             addUnquieProperty( "Mouse", fromList);
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Days")) {
                addUnquieProperty("(TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) > " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Weeks")) {
                addUnquieProperty("(TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) > " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Months")) {
                addUnquieProperty("(TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) > " + Integer.toString(mq.getAgeFrom()) + ")" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Years")) {
                addUnquieProperty("(TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) > " + Integer.toString(mq.getAgeFrom()) + " )" , whereList);
            }

         }



         if (0 == mq.getAgeFilter().toString().trim().compareTo("Between")) {
             addUnquieProperty( "Mouse", fromList);
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Days")) {
                addUnquieProperty("(TIMESTAMPDIFF(DAY,Mouse.birthDate,CURDATE()) BETWEEN " + Integer.toString(mq.getAgeFrom()) + " AND " + Integer.toString(mq.getAgeTo()) + " )" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Weeks")) {
                addUnquieProperty("(TIMESTAMPDIFF(WEEK,Mouse.birthDate,CURDATE()) BETWEEN " + Integer.toString(mq.getAgeFrom()) + " AND " + Integer.toString(mq.getAgeTo()) + " )" , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Months")) {
                addUnquieProperty("(TIMESTAMPDIFF(MONTH,Mouse.birthDate,CURDATE()) BETWEEN " + Integer.toString(mq.getAgeFrom()) + " AND " + Integer.toString(mq.getAgeTo()) + " )"  , whereList);
            }
            if (0 == mq.getAgeMeasure().toString().trim().compareTo("Years")) {
                addUnquieProperty("(TIMESTAMPDIFF(YEAR,Mouse.birthDate,CURDATE()) BETWEEN " + Integer.toString(mq.getAgeFrom()) + " AND " + Integer.toString(mq.getAgeTo()) + " )"  , whereList);
            }

         }



        // Date of Birth - Between
        if ( null != mq.getDobStartDate() && null != mq.getDobEndDate() ){
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty("(Mouse.birthDate Between '" + zDateformatter.format(mq.getDobStartDate() ).trim() + "'  AND '" + zDateformatter.format(mq.getDobEndDate()).trim() + "')"  , whereList);
        }

        // Current to End Date
        if ( null == mq.getDobStartDate() && null != mq.getDobEndDate() ){
             addUnquieProperty( "Mouse", fromList);
             addUnquieProperty("(Mouse.birthDate Between CURDATE()  AND '" + zDateformatter.format(mq.getDobEndDate()).trim() + "')"  , whereList);

        }

        // Start Date to Current
        if ( null != mq.getDobStartDate() && null == mq.getDobEndDate() ){
             addUnquieProperty( "Mouse", fromList);
             addUnquieProperty("(Mouse.birthDate Between '" + zDateformatter.format(mq.getDobStartDate()).trim() + "'  AND CURDATE())"  , whereList);
        }

        // Exit Date - Between
        if ( null != mq.getExitStartDate() && null != mq.getExitEndDate() ){
            addUnquieProperty( "Mouse", fromList);
            addUnquieProperty("(Mouse.exitDate Between '" + zDateformatter.format(mq.getExitStartDate()).trim() + "'  AND '" + zDateformatter.format(mq.getExitEndDate()).trim() + "')"  , whereList);
        }

        if ( null == mq.getExitStartDate() && null != mq.getExitEndDate() ){
             addUnquieProperty( "Mouse", fromList);
             addUnquieProperty("(Mouse.exitDate Between CURDATE()  AND '" + zDateformatter.format(mq.getExitEndDate()) + "')"  , whereList);
        }

        if ( null != mq.getExitStartDate() && null == mq.getExitEndDate() ){
             addUnquieProperty( "Mouse", fromList);
             addUnquieProperty("(Mouse.exitDate Between '" + zDateformatter.format(mq.getExitStartDate()).trim() + "'  AND CURDATE())"  , whereList);
        }

        // Breeding Status - List
        counter = 0;
        List<CvBreedingStatusEntity> breedingStatus = mq.getBreedingStatus();
        if ( null != breedingStatus && counter < breedingStatus.size()) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer breedingStatusWhere = new StringBuffer();
            breedingStatusWhere.append("(");
            while ( counter < breedingStatus.size() ){
                breedingStatusWhere.append(" Mouse.breedingStatus = '").append(breedingStatus.get(counter).getAbbreviation().trim()).append("' ");
                if ( counter + 1 !=  breedingStatus.size() ) {
                    breedingStatusWhere.append(" OR ");
                }
                counter++;
            }
            breedingStatusWhere.append(")");
            addUnquieProperty( breedingStatusWhere.toString().trim() , whereList);

        }

        // Origin - List
        counter = 0;
        List<CvMouseOriginEntity> mouseOriginList = mq.getOrigin();
        if ( null != mouseOriginList && counter < mouseOriginList.size()) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer originWhere = new StringBuffer();
            originWhere.append("(");
            while ( counter < mouseOriginList.size() ){
                originWhere.append(" Mouse.origin = '").append(mouseOriginList.get(counter).getMouseOrigin().trim()).append("' ");
                if ( counter + 1 !=  mouseOriginList.size() ) {
                    originWhere.append(" OR ");
                }
                counter++;
            }
            originWhere.append(")");
            addUnquieProperty( originWhere.toString().trim() , whereList);
        }

        // Life Status - List
        counter = 0;
        List<LifeStatusEntity> lifeStatusList = mq.getLifeStatus();
        if ( null != lifeStatusList &&  counter < lifeStatusList.size() ) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < lifeStatusList.size() ){
                myWhere.append(" Mouse.lifeStatus = '").append(lifeStatusList.get(counter).getLifeStatus().trim()).append("' ");
                if ( counter + 1 !=  lifeStatusList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Strain - List
        counter = 0;
        List<StrainEntity> seList = mq.getStrain();
        if ( null != seList && counter < seList.size() ) {
            addUnquieProperty(strainJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < seList.size() ){
                myWhere.append(" Strain.strainName = '").append(seList.get(counter).getStrainName().trim()).append("' ");
                if ( counter + 1 !=  seList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Generation - List
        counter = 0;
        List<CvGenerationEntity> geList = mq.getGeneration();
        if ( null != geList &&  counter < geList.size()) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < geList.size() ){
                myWhere.append(" Mouse.generation = '").append(geList.get(counter).getGeneration().trim()).append("' ");
                if ( counter + 1 !=  geList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Owner - List
        counter = 0;
        List<OwnerEntity> oList = mq.getOwner();
        if ( null != oList &&  counter < oList.size() ) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < oList.size() ){
                myWhere.append(" Mouse.owner = '").append(oList.get(counter).getOwner().trim()).append("' ");
                if ( counter + 1 !=  oList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Cause of Death - List
        counter = 0;
        List<CvCauseOfDeathEntity> codList = mq.getCauseOfDeath();
        if ( null != codList && counter < codList.size() ) {
            addUnquieProperty( "Mouse", fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < codList.size() ){
                myWhere.append(" Mouse.cod = '").append(codList.get(counter).getCod().trim()).append("' ");
                if ( counter + 1 !=  codList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }
        
        //room name - List
        counter = 0;
        List<RoomEntity> roomList = mq.getRooms();
        if(null != roomList && counter < roomList.size()){
            addUnquieProperty( containerJoin, fromList);
            addUnquieProperty(containerHistoryJoin, fromList);
            addUnquieProperty(roomJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < roomList.size() ){
                myWhere.append(" Room.roomName = '").append(roomList.get(counter).getRoomName()).append("' ");
                if ( counter + 1 !=  roomList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }


        // Litter # - List
        counter = 0;
        List<LitterEntity> lList = mq.getLitterNumber();
        if ( null != lList && counter < lList.size()) {
            addUnquieProperty( litterJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < lList.size() ){
                myWhere.append(" Litter.LitterID = '").append(lList.get(counter).getLitterID().trim()).append("' ");
                if ( counter + 1 !=  lList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Litter ID
        if (null != mq.getLitterID() && !mq.getLitterID().equals("") ){
            if (0 == mq.getLitterFilter().toString().trim().compareTo("Contains")) {
                addUnquieProperty( litterJoin, fromList);
                addUnquieProperty( " Litter.litterID LIKE '%" +
                        mq.getLitterID() + "%' " , whereList);
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

        // Pen ID - List
        counter = 0;
        List<ContainerEntity> cidList = mq.getPenNumber();
        if ( null != cidList && counter < cidList.size() ) {
            addUnquieProperty( containerJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < cidList.size() ){
                myWhere.append(" Container.containerID = ").append(Integer.toString(cidList.get(counter).getContainerID())).append(" ");
                if ( counter + 1 !=  cidList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Pen Name - List
        counter = 0;
        List<ContainerEntity> cndList = mq.getPenName();
        if ( null != cndList && counter < cndList.size() ) {
            addUnquieProperty( containerJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < cndList.size() ){
                myWhere.append(" Container.containerName = '").append(cndList.get(counter).getContainerName().trim()).append("' ");
                if ( counter + 1 !=  cndList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }
        
        // Protocol ID - List
        counter = 0;
        List<CvMouseProtocolEntity> cmpList = mq.getProtocolID();
        if ( null != cmpList && counter < cmpList.size() ) {
            addUnquieProperty("Mouse", fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < cmpList.size() ){
                myWhere.append(" Mouse.protocol = '").append(cmpList.get(counter).getId().trim()).append("' ");
                if ( counter + 1 !=  cmpList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Mouse Use - List
        counter = 0;
        List<CvMouseUseEntity> muList = mq.getMouseUse();
        if ( null != muList && counter < muList.size() ) {
            addUnquieProperty(mouseUsageJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < muList.size() ){
                myWhere.append(" MouseUsage.use = '").append(muList.get(counter).getMouseUse().trim()).append("' ");
                if ( counter + 1 !=  muList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Genotype - List
        counter = 0;
        List<GeneEntity> gList = mq.getGenotype();
        if ( null != gList &&  counter < gList.size() ) {
            addUnquieProperty(genotypeJoin, fromList);
            addUnquieProperty(geneJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < gList.size() ){
                myWhere.append(" Gene.labSymbol = '").append(gList.get(counter).getLabSymbol().trim()).append("' ");
                if ( counter + 1 !=  gList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }
        
        // Use Schedule Terms ID - List
        counter = 0;
        List<UseScheduleTermDTO> ustList = mq.getUseScheduleTerms();
        if ( null != ustList && counter < ustList.size() ) {
            addUnquieProperty( useScheduleJoin, fromList);
            addUnquieProperty( useScheduleTermJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < ustList.size() ){
                myWhere.append(" UseScheduleTerm._useScheduleTerm_key = '").append(ustList.get(counter).getUseScheduleTermKey().trim()).append("' ");
                if ( counter + 1 !=  ustList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }

        // Phenotype Terms ID - List
        counter = 0;
        List<cvPhenotypeTermDTO> ptList = mq.getPhenotypeTerms();
        if ( null != ptList && counter < ptList.size() ) {
            addUnquieProperty( phenotypeJoin, fromList);
            addUnquieProperty( phenotypeTermJoin, fromList);
            StringBuffer myWhere = new StringBuffer();
            myWhere.append("(");
            while ( counter < ptList.size() ){
                myWhere.append(" cv_Phenotype._phenotype_key = '").append(ptList.get(counter).getPhenotypeTermKey().trim()).append("' ");
                if ( counter + 1 !=  ptList.size() ) {
                    myWhere.append(" OR ");
                }
                counter++;
            }
            myWhere.append(")");
            addUnquieProperty( myWhere.toString().trim() , whereList);
        }
        
        // Genotype Date - Between
        if ( null != mq.getGenotypeStartDate() && null != mq.getGenotypeEndDate() ){
            addUnquieProperty(genotypeJoin, fromList);
            addUnquieProperty("(Genotype.gtDate Between '" + zDateformatter.format(mq.getGenotypeStartDate()).trim() + "'  AND '" + zDateformatter.format(mq.getGenotypeEndDate()).trim() + "')"  , whereList);
        }

        if ( null == mq.getGenotypeStartDate() && null != mq.getGenotypeEndDate() ){
            addUnquieProperty(genotypeJoin, fromList);
            addUnquieProperty("(Genotype.gtDate Between CURDATE()  AND '" + zDateformatter.format(mq.getGenotypeEndDate()) + "')"  , whereList);
        }

        if ( null != mq.getGenotypeStartDate() && null == mq.getGenotypeEndDate() ){
             addUnquieProperty(genotypeJoin, fromList);
             addUnquieProperty("(Genotype.gtDate Between '" + zDateformatter.format(mq.getGenotypeStartDate()).trim() + "'  AND CURDATE())"  , whereList);
        }

        //add group by clause
        addUnquieProperty("MouseKey", groupByList);
        
        queryDebug("MouseQueryDTO.genQuery  ", "*******************  build query *****************");

        String [] retMouseQuery = new String[3];
        // xxx remove const and replace with value from db.
        retMouseQuery = buildQuery(selectList,fromList,whereList, groupByList, maxQueryRows);
 
        queryDebug("MouseQueryDTO.genQuery  ", "*******************  query built *****************");
        
        // debug
        for(int i=0; i<retMouseQuery.length; ++i) {
            System.out.println("Mouse query " + retMouseQuery[i]);
        }

        return (retMouseQuery);
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
    
    /**
     * containsIgnoreCase : will take a list of strings and return true if any of
     *                      the values match ignoring case.
     * 
     * @param val   - The string to match
     * @param vals  - The list of strings to match it to
     */
    private boolean containsIgnoreCase(String val, List<String> vals){
        for(String iVal : vals){
            if(iVal.equalsIgnoreCase(val)){
                return true;
            }
        }
        return false;
    }


}