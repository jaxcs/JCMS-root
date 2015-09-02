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

import jcms.cagecard.dtos.CageCardResultDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import jcms.integrationtier.dto.MatingQueryDTO;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;


import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.dtos.*;
import jcms.integrationtier.dto.CageCardDTO;

/**
 *
 * @author mkamato
 */
public class MatingCardDAO extends MySQLDAO {

    // Store the peices of the SQL query in these lists.
    private String theMatingSQLQuery = null;
    private Connection con = null;
    MatingCardResultDTO theResultDTO;
    //query for mouse info
    private String theMouseSQLQuery = null;
    private String theMouseSQLQueryTemplate = "SELECT Mouse.* "
            + "FROM Mouse "
            + "LEFT JOIN Container "
            + "ON Mouse._pen_key = Container._container_key "
            + "WHERE Container.containerID = ";
    //query for mating/litter info
    private String theMatingSQLQueryPT1 = 
            "SELECT "
            //DbSetups
            + "TRIM(DbSetup1.MTSValue) AS showExitedMice, TRIM(DbSetup2.MTSValue) AS PIName, TRIM(DbSetup3.MTSValue) AS PIPhone, "
            + "DATE(ContainerHistory.actionDate) as ActivationDate, "
            //Room
            + "Room.roomName, "
            //container
            + "TRIM(Container.comment) AS containerComment, ABS(Container.containerID) AS containerID, Container.containerName, "
            //ContainerStatus
            + "ContainerStatus.containerStatus, "
            //Mating
            + "ABS(Mating.matingID) as MatingID , TRIM(MatS.strainName)  as LitterStrain, "
            + "TRIM(Mating.generation) as LitterGeneration, DATE(Litter.birthDate) AS litterBirthDate, "
            + "Mating.owner, Date(Mating.matingDate) AS matingDate, TRIM(Mating.comment) AS matingComment, Mating.weanNote, "
            //Dam1
            + "TRIM(dam1M.ID) as dam1ID, DATE(dam1M.birthDate) AS dam1BD, TRIM(dam1Strain.strainName) AS dam1StrainName, "
            + "ABS(dam1Strain.jrNum) AS dam1jrNum, TRIM(dam1M.protocol) AS dam1Protocol, TRIM(dam1M.newTag) AS dam1NewTag, "
            + "TRIM(dam1Litter.litterID) AS dam1LitterID, TRIM(dam1Mating.matingID) AS dam1MatingID, TRIM(dam1M.generation) AS dam1Generation,  "
            //Sire
            + "TRIM(sire.ID) AS sireID, DATE(sire.birthDate) AS sireBD, TRIM(sireStrain.strainName) AS sireStrainName, "
            + "ABS(sireStrain.jrNum) AS sirejrNum, TRIM(sire.protocol) AS sireProtocol, TRIM(sire.newTag) AS sireNewTag, "
            + "TRIM(sireLitter.litterID) AS sireLitterID, TRIM(sireMating.matingID) AS sireMatingID, TRIM(sire.generation) AS sireGeneration, "
            //dam2
            + "TRIM(dam2M.ID) as dam2ID, DATE(dam2M.birthDate) AS dam2BD, TRIM(dam2Strain.strainName) AS dam2StrainName, "
            + "ABS(dam2Strain.jrNum) AS dam2jrNum, TRIM(dam2M.protocol) AS dam2Protocol, TRIM(dam2M.newTag) AS dam2NewTag, "
            + "TRIM(dam2Litter.litterID) AS dam2LitterID, TRIM(dam2Mating.matingID) AS dam2MatingID, TRIM(dam2M.generation) AS dam2Generation,  "
            //Litter
            + "Litter.totalBorn, Litter.litterID, Litter.numMale, Litter.numFemale, "
            //Genotypes
            + "CONCAT(COALESCE(sireGenotype.allele1, ''), ' ', COALESCE(sireGenotype.allele2, ''), ' ', sireGene.labSymbol) AS sireGenotype, "
            + "CONCAT(COALESCE(dam1Genotype.allele1, ''), ' ', COALESCE(dam1Genotype.allele2, ''), ' ', dam1Gene.labSymbol) AS dam1Genotype, "
            + "CONCAT(COALESCE(dam2Genotype.allele1, ''), ' ', COALESCE(dam2Genotype.allele2, ''), ' ', dam2Gene.labSymbol) AS dam2Genotype "
            + "FROM "
            + "DbSetup AS DbSetup1, DbSetup AS DbSetup2, DbSetup AS DbSetup3, Mating "
            + "Left Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` "
            + "Left Join Mouse as sire ON sire.`_mouse_key` = Mating.`_sire_key` "
            + "Left Join Mouse as dam1M ON dam1M.`_mouse_key` = Mating.`_dam1_key` "
            + "Left Join Mouse as dam2M ON dam2M.`_mouse_key` = Mating.`_dam2_key` "
            + "Left Join Strain as sireStrain ON sire._strain_key = sireStrain._strain_key "
            + "Left Join Strain as dam1Strain ON dam1M._strain_key = dam1Strain._strain_key "
            + "Left Join Strain as dam2Strain ON dam2M._strain_key = dam2Strain._strain_key "
            + "Left Join Container On Container.`_container_key` = dam1M.`_pen_key` "
            + "Left Join ContainerHistory ON Container.`_containerHistory_key` = ContainerHistory.`_containerHistory_key` "
            + "LEFT JOIN Room ON ContainerHistory._room_key = Room._room_key "
            + "LEFT JOIN cv_ContainerStatus AS ContainerStatus ON ContainerHistory._containerStatus_key = ContainerStatus._containerStatus_key "
            + "Left Join Litter ON Litter._mating_key =  Mating._mating_key "
            + "Left Join Genotype AS sireGenotype ON sireGenotype._mouse_key = sire._mouse_key "
            + "Left Join Genotype AS dam1Genotype ON dam1Genotype._mouse_key = dam1M._mouse_key "
            + "Left Join Genotype AS dam2Genotype ON dam2Genotype._mouse_key = dam2M._mouse_key "
            + "Left Join Gene AS dam1Gene ON dam1Gene._gene_key = dam1Genotype._gene_key "
            + "Left Join Gene AS dam2Gene ON dam2Gene._gene_key = dam2Genotype._gene_key "
            + "Left JOIN Gene AS sireGene ON sireGene._gene_key = sireGenotype._gene_key "
            + "LEFT JOIN Litter AS sireLitter ON sire._litter_key = sireLitter._litter_key "
            + "LEFT JOIN Litter AS dam1Litter ON dam1M._litter_key = dam1Litter._litter_key "
            + "LEFT JOIN Litter AS dam2Litter ON dam2M._litter_key = dam2Litter._litter_key "
            + "LEFT JOIN Mating AS dam1Mating ON dam1Litter._mating_key = dam1Mating._mating_key "
            + "LEFT JOIN Mating AS dam2Mating ON dam2Litter._mating_key = dam2Mating._mating_key "
            + "LEFT JOIN Mating AS sireMating ON sireLitter._mating_key = sireMating._mating_key "
            + "WHERE DbSetup1.MTSVar = 'JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS' "
            + "AND DbSetup2.MTSVar = 'MTS_PI_NAME' "
            + "AND DbSetup3.MTSVar = 'MTS_PI_PHONE' "
            + "AND Container.containerID = ";
    private String theMatingSQLQueryPT2 = " AND Mating.matingID = (SELECT MAX(matingID) "
            + "FROM Mating "
            + "Left Join Strain as MatS On MatS.`_strain_key` = Mating.`_strain_key` "
            + "Left Join Mouse as sire ON sire.`_mouse_key` = Mating.`_sire_key` "
            + "Left Join Mouse as dam1M ON dam1M.`_mouse_key` = Mating.`_dam1_key` "
            + "Left Join Container On Container.`_container_key` = dam1M.`_pen_key` "
            + "Left Join Litter ON Litter._mating_key =  Mating._mating_key "
            + "WHERE containerID = ";
    
    private String dbSetupQuery = "SELECT TRIM(dbSetup1.MTSValue) AS PIName, TRIM(dbSetup2.MTSValue) AS PIPhone, TRIM(dbSetup3.MTSValue) AS detailCardNote " +
                "FROM DbSetup AS dbSetup1, DbSetup AS dbSetup2, DbSetup AS dbSetup3 " +
                "WHERE dbSetup1.MTSVar = 'MTS_PI_NAME' " +
                "AND dbSetup2.MTSVar = 'MTS_PI_PHONE' " +
                "AND dbSetup3.MTSVar = 'MTS_CAGE_CARD_DETAIL_NOTE'; ";

    public MatingCardResultDTO query(CageCardDTO ccDTO) throws SQLException {
        theResultDTO = new MatingCardResultDTO();
        if (ccDTO.getQuantity().equals("single")) {
            generateSQLQuery(ccDTO.getPenID());
            theResultDTO.setResult(executeMatingQuery(theMouseSQLQuery));
            theResultDTO.setMatingResult(executeMatingQuery(theMatingSQLQuery));
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
            theResultDTO.setFormatType(ccDTO.getFormat());
        }
        else{
            theResultDTO.setFormatType(ccDTO.getFormat());
            ArrayList<Result> theResults = new ArrayList<Result>();
            ArrayList<Result> theMatingResults = new ArrayList<Result>();
            int start = Integer.parseInt(ccDTO.getPenID());
            int max = Integer.parseInt(ccDTO.getPenID2());
            for(int idx = start; idx<=max;idx++){
                generateSQLQuery(new Integer(idx).toString());
                theMatingResults.add(executeMatingQuery(theMatingSQLQuery));
                theResults.add(executeMatingQuery(theMouseSQLQuery));
            }
            theResultDTO.setResults(theResults);
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
            theResultDTO.setMatingResults(theMatingResults);
            theResultDTO.setStart(start);
        }
        return theResultDTO;
    }

    private Result executeMatingQuery(String theQuery) {
        ResultSet myResultSet = null;
        Result myResult = null;

        try {

            // Open connect to database
            con = super.getConnection();

            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            myResultSet = stmt.executeQuery(theQuery);

            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
        } catch (SQLException ex) {
            System.out.println(ex); 
        }
        super.closeConnection(con);
        return myResult;
    }

    private Result executeDbSetupQuery(){
        ResultSet myResultSet = null; 
        Result myResult = null;
                
        try {

            // Open connect to database
            con = super.getConnection();
            
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);
            myResultSet = stmt.executeQuery(this.dbSetupQuery);
            
            myResultSet.beforeFirst();
            myResult = ResultSupport.toResult(myResultSet);
            super.closeConnection(con);
            return myResult;
        }
        catch(SQLException ex){
            System.out.println(ex); 
        }
        super.closeConnection(con);
        return myResult;
    }
    
    private void generateSQLQuery(String cageID) {
        theMatingSQLQuery = theMatingSQLQueryPT1 + cageID
                + theMatingSQLQueryPT2 + cageID + ");";
        theMouseSQLQuery = theMouseSQLQueryTemplate + cageID + ";";
    }
}
