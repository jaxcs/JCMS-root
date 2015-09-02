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
import java.sql.Statement;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.cagecard.dtos.TwoPenWeanCardResultDTO;
import jcms.integrationtier.dto.CageCardDTO;

/**
 *
 * @author mkamato
 */
public class TwoPenWeanCardDAO extends MySQLDAO {
    private Connection con = null;
    CageCardResultDTO theResultDTO;
    private String theTwoPenWeanSQLQuery = "SELECT Mouse.*, Container.containerID, "
            + "CONCAT(Genotype.allele1, ' ', Genotype.allele2, ' ', Gene.labSymbol) "
            + "AS Gene FROM Mouse "
            + "LEFT JOIN Container "
            + "ON Mouse._pen_key = Container._container_key "
            + "LEFT JOIN Genotype "
            + "ON Genotype._mouse_key = Mouse._mouse_key "
            + "LEFT JOIN Gene "
            + "ON Gene._gene_key = Genotype._gene_key "
            + "WHERE Container.containerID = ";
    
    
    
    public CageCardResultDTO query(CageCardDTO ccDTO) throws SQLException{
        Result theResults = executeTwoPenWeanQuery(ccDTO);
        theResultDTO = new TwoPenWeanCardResultDTO();
        theResultDTO.setResult(theResults);
        theResultDTO.setFormatType(ccDTO.getFormat());
        return theResultDTO;
    }
    
    
    private Result executeTwoPenWeanQuery(CageCardDTO ccDTO){
        ResultSet myResultSet = null; 
        Result myResult = null;
                
        try {

            // Open connect to database
            con = super.getConnection();
            
            // Create the stmt used for updatable result set.
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);
            
            generateSQLQuery(ccDTO);
            myResultSet = stmt.executeQuery(theTwoPenWeanSQLQuery);
            
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
    
    private void generateSQLQuery(CageCardDTO ccDTO){
        theTwoPenWeanSQLQuery = theTwoPenWeanSQLQuery + ccDTO.getPenID() + ";";
        System.out.println("The TwoPenWean Query is: " + theTwoPenWeanSQLQuery);
    }
}
