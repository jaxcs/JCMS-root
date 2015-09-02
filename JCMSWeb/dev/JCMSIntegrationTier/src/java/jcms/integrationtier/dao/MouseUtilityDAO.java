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

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 * A class for common methods that will be used by multiple classes.
 * 
 * @author mkamato
 */
public class MouseUtilityDAO extends MySQLDAO {
    
    /**
     * Method to return the genotype of mouse with given mouse key.
     * 
     * @param mouseKey
     * @return Genotype of mouse with _mouse_key equal to mouseKey
     * @throws Exception 
     */
    public String getMouseGenotypeByMouseKey(int mouseKey) throws Exception{
        String genotype = "";
        String query = "SELECT * FROM Genotype "
                + "JOIN Gene ON Genotype._gene_key = Gene._gene_key "
                + "WHERE _mouse_key = ?;";
        
        //establish connection and initialize prepared statement
        Connection con = this.getConnection();
        PreparedStatement getGenotypes = con.prepareStatement(query);
        
        //format the query
        getGenotypes.setInt(1, mouseKey);
        //execute Query...
        ResultSet rs = this.executePreparedStatementQuery(getGenotypes);
        rs.beforeFirst();
        while(rs.next()){
            String gene = rs.getString("labSymbol");
            String allele1 = rs.getString("allele1");
            String allele2 = rs.getString("allele2");
            if(rs.isLast()){
                if(allele2 == null){
                    genotype += gene + " " + allele1 + " ";
                }
                else{                
                    genotype += gene + " " + allele1 + "/" + allele2 + " ";
                }
            }
            else{
                if(allele2 == null){
                    genotype += gene + " " + allele1 + ", ";
                }
                else{                
                    genotype += gene + " " + allele1 + "/" + allele2 + ", ";
                }
            }
        }        
        return genotype;
    }
}
