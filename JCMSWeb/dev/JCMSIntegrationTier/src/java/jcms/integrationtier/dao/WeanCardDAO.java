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
import javax.servlet.jsp.jstl.sql.ResultSupport;


import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.dtos.*;
import jcms.integrationtier.dto.CageCardDTO;

/**
 *
 * @author mkamato
 */
public class WeanCardDAO extends MySQLDAO {

    private Connection con = null;
    private String theWeanSQLQuery = null;
    WeanCardResultDTO theResultDTO;
    //query for mating/litter info
    private String theWeanSQLQueryTemplate = 
            "SELECT "
            //DbSetup
            + "TRIM(DbSetup1.MTSValue) AS showExitedMice, TRIM(DbSetup2.MTSValue) AS PIName, TRIM(DbSetup3.MTSValue) AS PIPhone, "
            //Mouse info
            + "Mouse.*,  "
            + "CONCAT(COALESCE(Genotype.allele1, ''), ' ', COALESCE(Genotype.allele2, ''), ' ', Gene.labSymbol) AS gene, "
            //Room
            + "Room.roomName, "
            //Container History
            + "DATE(ContainerHistory.actionDate) as ActivationDate, "
            //containerStatus
            + "ContainerStatus.containerStatus, "
            //mating
            + "Mating.matingID, "
            //Dam1 info
            + "TRIM(Dam1.ID) AS dam1ID, TRIM(Dam1Strain.strainName) AS dam1Strain, "
            + "CONCAT(COALESCE(Dam1Genotype.allele1, ''), ' ', COALESCE(Dam1Genotype.allele2, ''), ' ', Dam1Gene.labSymbol, '') AS dam1Gene, "
            //Dam2 info
            + "TRIM(Dam2.ID) AS dam2ID, TRIM(Dam2Strain.strainName) AS dam2Strain, "
            + "CONCAT(COALESCE(Dam2Genotype.allele1, ''), ' ', COALESCE(Dam2Genotype.allele2, ''), ' ', Dam2Gene.labSymbol, '') AS dam2Gene, "            
            //Sire info
            + "TRIM(Sire.ID) AS sireID, TRIM(SireStrain.strainName) AS sireStrain, "
            + "CONCAT(COALESCE(SireGenotype.allele1, ''), ' ', COALESCE(SireGenotype.allele2, ''), ' ', SireGene.labSymbol, '') AS sireGene, "
            //container
            + "Container.containerID, Container.containerName, TRIM(Container.comment) AS containerComment, "
            //litter
            + "Litter.litterID, Litter.totalBorn, DATE(Litter.birthDate) AS litterBirthDate, "
            + "Litter.numFemale, Litter.numMale, DATE(Litter.weanDate) as weanDate, "
            + "DATE(Litter.tagDate) AS tagDate, Litter.status, "
            //Strain
            + "Strain.* "
            //Genotype
            
            + "FROM DbSetup AS DbSetup1, DbSetup AS DbSetup2, DbSetup AS DbSetup3, Mouse "
            + "LEFT JOIN Container "
            + "ON Mouse._pen_key = Container._container_key "
            + "LEFT JOIN ContainerHistory "
            + "ON Container.`_containerHistory_key` = ContainerHistory.`_containerHistory_key` "
            + "LEFT JOIN cv_ContainerStatus AS ContainerStatus "
            + "ON ContainerHistory._containerStatus_key = ContainerStatus._containerStatus_key "
            + "LEFT JOIN Room "
            + "ON ContainerHistory._room_key = Room._room_key "
            + "LEFT JOIN Genotype "
            + "ON Genotype._mouse_key = Mouse._mouse_key "
            + "LEFT JOIN Gene "
            + "ON Gene._gene_key = Genotype._gene_key "
            + "LEFT JOIN Litter "
            + "ON Litter._litter_key = Mouse._litter_key "
            + "LEFT JOIN Strain "
            + "ON Strain._strain_key = Mouse._strain_key "
            + "LEFT JOIN Mating "
            + "ON Litter._mating_key = Mating._mating_key "
            + "LEFT JOIN Mouse AS Sire "
            + "ON Mating._sire_key = Sire._mouse_key "
            + "LEFT JOIN Strain AS SireStrain "
            + "ON Sire._strain_key = SireStrain._strain_key "
            + "LEFT JOIN Mouse AS Dam1 "
            + "ON Mating._dam1_key = Dam1._mouse_key "
            + "LEFT JOIN Strain AS Dam1Strain "
            + "ON Dam1._strain_key = Dam1Strain._strain_key "
            + "LEFT JOIN Mouse AS Dam2 "
            + "ON Mating._dam2_key = Dam2._mouse_key "
            + "LEFT JOIN Strain AS Dam2Strain "
            + "ON Dam2._strain_key = Dam2Strain._strain_key "
            + "LEFT JOIN Genotype AS SireGenotype "
            + "ON Sire._mouse_key = SireGenotype._mouse_key "
            + "LEFT JOIN Genotype AS Dam1Genotype "
            + "ON Dam1._mouse_key = Dam1Genotype._mouse_key "
            + "LEFT JOIN Genotype AS Dam2Genotype "
            + "ON Dam2._mouse_key = Dam2Genotype._mouse_key "
            + "LEFT JOIN Gene AS SireGene "
            + "ON SireGenotype._gene_key = SireGene._gene_key "
            + "LEFT JOIN Gene AS Dam1Gene "
            + "ON Dam1Genotype._gene_key = Dam1Gene._gene_key "
            + "LEFT JOIN Gene AS Dam2Gene "
            + "ON Dam2Genotype._gene_key = Dam2Gene._gene_key "
            + "WHERE DbSetup1.MTSVar = 'JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS' "
            + "AND DbSetup2.MTSVar = 'MTS_PI_NAME' "
            + "AND DbSetup3.MTSVar = 'MTS_PI_PHONE' "
            + "AND Container.containerID = ";
    
        private String dbSetupQuery = "SELECT TRIM(dbSetup1.MTSValue) AS PIName, TRIM(dbSetup2.MTSValue) AS PIPhone, TRIM(dbSetup3.MTSValue) AS detailCardNote " +
                    "FROM DbSetup AS dbSetup1, DbSetup AS dbSetup2, DbSetup AS dbSetup3 " +
                    "WHERE dbSetup1.MTSVar = 'MTS_PI_NAME' " +
                    "AND dbSetup2.MTSVar = 'MTS_PI_PHONE' " +
                    "AND dbSetup3.MTSVar = 'MTS_CAGE_CARD_DETAIL_NOTE'; ";

    public WeanCardResultDTO query(CageCardDTO ccDTO) throws SQLException {
        theResultDTO = new WeanCardResultDTO();
        if (ccDTO.getQuantity().equals("single")) {
            generateSQLQuery(ccDTO.getPenID());
            theResultDTO.setFormatType(ccDTO.getFormat());
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
            //gives all the mouse info for the cage
            theResultDTO.setResult(executeWeanQuery(theWeanSQLQuery));
        } 
        else {
            theResultDTO.setFormatType(ccDTO.getFormat());
            ArrayList<Result> theResults = new ArrayList<Result>();
            int start = Integer.parseInt(ccDTO.getPenID());
            int max = Integer.parseInt(ccDTO.getPenID2());
            for (int idx = start; idx <= max; idx++) {
                generateSQLQuery(new Integer(idx).toString());
                theResults.add(executeWeanQuery(theWeanSQLQuery));
            }
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
            theResultDTO.setResults(theResults);
            theResultDTO.setStart(start);
        }
        return theResultDTO;
    }

    private Result executeWeanQuery(String theQuery) {
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
            //Logger.getLogger(MouseQueryDAO.class.getName()).log(Level.SEVERE, null, ex);  
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
        theWeanSQLQuery = theWeanSQLQueryTemplate + cageID + ";";
    }
}
