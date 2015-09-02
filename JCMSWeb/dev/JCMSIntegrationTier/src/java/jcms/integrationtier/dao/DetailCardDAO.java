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
import jcms.cagecard.dtos.DetailCardResultDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.cagecard.cardtypes.FormatType;
import jcms.integrationtier.dto.CageCardDTO;
import java.util.ArrayList;
/**
 *
 * @author mkamato
 */
public class DetailCardDAO extends MySQLDAO{
    
    //private static final long serialVersionUID = 1L;
    private Connection con = null;
    CageCardResultDTO theResultDTO;
    private String theDetailSQLQuery = null;
    private String theDetailSQLQueryTemplate = 
            "SELECT "
            //DbSetup
            + "TRIM(DbSetup1.MTSValue) AS showExitedMice, TRIM(DbSetup2.MTSValue) AS PIName, "
            + "TRIM(DbSetup3.MTSValue) AS PIPhone, TRIM(DbSetup4.MTSValue) AS detailCardNote, "
            //Container
            + "ContainerStatus.containerStatus, TRIM(Container.comment) AS containerComment, "
            + "Container.containerID, Container.containerName, "
            //Room
            + "Room.roomName, "
            //Mouse
            + "Mouse.ID, DATE(Mouse.birthDate) AS birthDate, Mouse.comment, Mouse.newTag, "
            + "DATE(Mouse.exitDate) AS exitDate, Mouse.cod, Mouse.codNotes, Mouse.protocol, "
            + "Mouse.generation, Mouse.sex, Mouse.lifeStatus, Mouse.breedingStatus, "
            + "Mouse.coatColor, Mouse.diet, Mouse.owner, Mouse.origin, "
            //Mouse Genotype
            + "CONCAT(COALESCE(Genotype.allele1, ''), ' ', COALESCE(Genotype.allele2, ''), ' ', Gene.labSymbol) AS Gene, "
            //ContainerHistory
            + "DATE(ContainerHistory.actionDate) as ActivationDate, "
            //Strain
            + "Strain.strainName, Strain.jrNum, "
            //Litter
            + "Litter.litterID, Litter.weanDate, "
            //Mating
            + "Mating.matingID "
            + "FROM DbSetup AS DbSetup1, DbSetup AS DbSetup2, DbSetup AS DbSetup3, DbSetup AS DbSetup4, Mouse "
            + "JOIN Container "
            + "ON Mouse._pen_key = Container._container_key "
            + "JOIN ContainerHistory "
            + "ON Container.`_containerHistory_key` = ContainerHistory.`_containerHistory_key` "
            + "JOIN cv_ContainerStatus AS ContainerStatus "
            + "ON ContainerHistory._containerStatus_key = ContainerStatus._containerStatus_key "
            + "JOIN Room "
            + "ON ContainerHistory._room_key = Room._room_key "
            + "JOIN Strain "
            + "ON Strain._strain_key = Mouse._strain_key "
            + "LEFT JOIN Genotype "
            + "ON Genotype._mouse_key = Mouse._mouse_key "
            + "LEFT JOIN Gene "
            + "ON Gene._gene_key = Genotype._gene_key "
            + "LEFT JOIN Litter "
            + "ON Litter._litter_key = Mouse._litter_key "
            + "LEFT JOIN Mating "
            + "ON Litter._mating_key = Mating._mating_key "
            + "WHERE  DbSetup1.MTSVar = 'JCMS_PRINT_EXITED_MICE_ON_CAGE_CARDS' "
            + "AND DbSetup2.MTSVar = 'MTS_PI_NAME' "
            + "AND DbSetup3.MTSVar = 'MTS_PI_PHONE' "
            + "AND DbSetup4.MTSVar = 'MTS_CAGE_CARD_DETAIL_NOTE' "
            + "AND Container.containerID = ";
    
    private String dbSetupQuery = "SELECT TRIM(dbSetup1.MTSValue) AS PIName, TRIM(dbSetup2.MTSValue) AS PIPhone, TRIM(dbSetup3.MTSValue) AS detailCardNote " +
                    "FROM DbSetup AS dbSetup1, DbSetup AS dbSetup2, DbSetup AS dbSetup3 " +
                    "WHERE dbSetup1.MTSVar = 'MTS_PI_NAME' " +
                    "AND dbSetup2.MTSVar = 'MTS_PI_PHONE' " +
                    "AND dbSetup3.MTSVar = 'MTS_CAGE_CARD_DETAIL_NOTE'; ";
    
    
    
    public CageCardResultDTO query(CageCardDTO ccDTO) throws SQLException{
        theResultDTO = new DetailCardResultDTO();
        //just want one cage card
        if(ccDTO.getQuantity().equals("single")){
            generateSQLQuery(ccDTO.getPenID());
            theResultDTO.setFormatType(ccDTO.getFormat());
            theResultDTO.setResult(executeDetailQuery());
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
        }
        else{
            theResultDTO.setFormatType(ccDTO.getFormat());
            ArrayList<Result> theResults = new ArrayList<Result>();
            int start = Integer.parseInt(ccDTO.getPenID());
            int max = Integer.parseInt(ccDTO.getPenID2());
            for(int idx = start; idx<=max; idx++){
                generateSQLQuery(new Integer(idx).toString());
                theResults.add(executeDetailQuery());
            }
            theResultDTO.setResults(theResults);
            theResultDTO.setDbSetupResults(executeDbSetupQuery());
            theResultDTO.setStart(start);
        }
        return theResultDTO;
    }
    
    
    private Result executeDetailQuery(){
        ResultSet myResultSet = null; 
        Result myResult = null;
                
        try {

            // Open connect to database
            con = super.getConnection();
            
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);
            myResultSet = stmt.executeQuery(theDetailSQLQuery);
            
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
    
    private void generateSQLQuery(String cageID){
        theDetailSQLQuery = theDetailSQLQueryTemplate + cageID + ";";
    }
}
