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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class GenotypeDAO extends MySQLDAO {
    
    private static final long serialVersionUID = 0211002L;
    private Connection con = null;
    private ITBaseFacade it = new ITBaseFacade();
    SystemDao dao = new SystemDao();
    
    public int updateGenotype(GenotypeEntity genotype) {
        int updateCount = 0;
        int mouseKey = 0;
        int geneKey = 0;
        String genotypeDt = null;
                
        try {
            // Open connect to database
            con = super.getConnection();
            
            if (genotype.getMouseKey() != null) {
                mouseKey = genotype.getMouseKey().getMouseKey();
            }
            
            if (genotype.getGeneKey() != null) {
                geneKey = genotype.getGeneKey().getGeneKey();
            }
            
            // if genotypeDate is selected                        
            if (genotype.getGtDate() != null) {
                // set the actionDate
                Date date = genotype.getGtDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                genotypeDt = formatter.format(date);
            }
            
            String query = "Update Genotype set _mouse_key = ?, "
                    + "_gene_key = ?, allele1=?, allele2=?, all1Conf=?, all2Conf=?, "
                    + "genoPage=?, gtDate=?, sampleLocation=?, comment=?, version=? "
                    + "where _genotype_key=?";
            
            PreparedStatement updateString = con.prepareStatement(query);
            
            if (mouseKey > 0) {
                updateString.setInt(1, mouseKey);
            }
            else {
                updateString.setString(1, null);
            }
            
            if (geneKey > 0) {
                updateString.setInt(2, geneKey);
            }
            else {
                updateString.setString(2, null);
            }
            
            updateString.setString(3, genotype.getAllele1());
            updateString.setString(4, genotype.getAllele2());
            updateString.setInt(5, (genotype.getAll1Conf() == true ? -1 : 0));
            updateString.setInt(6, (genotype.getAll2Conf() == true ? -1 : 0));
            updateString.setString(7, genotype.getGenoPage());
            updateString.setString(8, genotypeDt);
            updateString.setString(9, genotype.getSampleLocation());
            updateString.setString(10, genotype.getComment());
            updateString.setInt(11, genotype.getVersion());
            updateString.setInt(12, genotype.getGenotypeKey());            
            
            updateCount = updateString.executeUpdate();

            System.out.println("Update Genotype query " + query);  
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseDAO::NullPointerException:: "
                    + "UpdateGenotype  \n" + npe);
        } catch (SQLException ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "UpdateGenotype  \n" + ex);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "UpdateGenotype  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return updateCount;
    }
    
    public int updateAlleleConfidence(GenotypeEntity genotype) {
        int updateCount = 0;
        int mouseKey = 0;
        int geneKey = 0;
        String genotypeDt = null;
                
        try {
            // Open connect to database
            con = super.getConnection();
            
            String query = "UPDATE Genotype SET all1Conf=?, all2Conf=? "
                    + " WHERE _genotype_key=?";
            
            PreparedStatement updateString = con.prepareStatement(query);
            
            updateString.setInt(1, (genotype.getAll1Conf() == true ? -1 : 0));
            updateString.setInt(2, (genotype.getAll2Conf() == true ? -1 : 0));
            updateString.setInt(3, genotype.getGenotypeKey());            
            
            updateCount = updateString.executeUpdate();

            System.out.println("Update Allele Confidence " + updateString);  
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::GenotypeDAO::NullPointerException:: "
                    + "updateAlleleConfidence  \n" + npe);
        } catch (SQLException ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::GenotypeDAO::SQLException:: "
                    + "updateAlleleConfidence  \n" + ex);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::GenotypeDAO::SQLException:: "
                    + "updateAlleleConfidence  \n" + ex);
        } finally {
            // Close database connection from MySQLDAO
            super.closeConnection(con);
        }
        return updateCount;
    }
    
    public GenotypeEntity findGenotype(int key) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        GenotypeEntity gEntity = new GenotypeEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT g._genotype_key, g._mouse_key, g._gene_key, "
                    + "g.all1Conf, g.all2Conf, g.genoPage, g.sampleLocation, "
                    + "g.gtDate, g.comment, g.allele1, g.allele2, g.version "
                    + "FROM Genotype g "
                    + "LEFT JOIN Mouse m ON m._mouse_key = g._mouse_key "
                    + "LEFT JOIN Gene ge ON ge._gene_key = g._gene_key "
                    + "WHERE g._genotype_key=" + key;
            
            System.out.println("find Genotype query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            gEntity = moveGenotypeToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return gEntity;
    }
    
    private GenotypeEntity moveGenotypeToEntity(ResultSet resultSet)
            throws SQLException {
        GenotypeEntity              genotypeEntity  = null;
        Integer                     genotypeKey     = -1;

        while (resultSet.next()) {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (genotypeKey != resultSet.getInt("_genotype_key"))
            {
                genotypeKey = resultSet.getInt("_genotype_key");

                // New genotype
                genotypeEntity = new GenotypeEntity();

                genotypeEntity.setGenotypeKey(genotypeKey);
                
                // set the FK's                  
                // set gene key
                if (resultSet.getObject("_gene_key") != null && 
                        resultSet.getInt("_gene_key") > 0) {
                   
                   genotypeEntity.setGeneKey((GeneEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new GeneEntity(), resultSet.getInt("_gene_key")));
                }
                
                // set mouse key
                if (resultSet.getObject("_mouse_key") != null && 
                        resultSet.getInt("_mouse_key") > 0) {
                   genotypeEntity.setMouseKey((MouseEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new MouseEntity(), resultSet.getInt("_mouse_key")));
                }
                
                genotypeEntity.setAll1Conf(resultSet.getBoolean("all1Conf"));
                genotypeEntity.setAll2Conf(resultSet.getBoolean("all2Conf"));

                genotypeEntity.setAllele1(resultSet.getString("allele1"));
                genotypeEntity.setAllele2(resultSet.getString("allele2"));
                genotypeEntity.setGtDate(resultSet.getDate("gtDate"));
                genotypeEntity.setGenoPage(resultSet.getString("genoPage"));
                genotypeEntity.setSampleLocation(resultSet.getString("sampleLocation"));
                genotypeEntity.setComment(resultSet.getString("comment"));
                genotypeEntity.setVersion(resultSet.getInt("version"));                   
            }
        }

        return genotypeEntity; //table;
    }    
    
    public GenotypeEntity findGenotypeByMouseAndGene(int mKey, int gKey) throws 
            SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        GenotypeEntity gEntity = new GenotypeEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT g._genotype_key, g._mouse_key, g._gene_key, "
                    + "g.all1Conf, g.all2Conf, g.genoPage, g.sampleLocation, "
                    + "g.gtDate, g.comment, g.allele1, g.allele2, g.version "
                    + "FROM Genotype g "
                    + "LEFT JOIN Mouse m ON m._mouse_key = g._mouse_key "
                    + "LEFT JOIN Gene ge ON ge._gene_key = g._gene_key "
                    + "WHERE g._mouse_key= " + mKey + " && g._gene_key = " + gKey;
            
            System.out.println("find Genotype query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            gEntity = moveGenotypeToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return gEntity;
    }
    
    public ITBaseEntityTable findAllelesByGene(int gKey) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ITBaseEntityTable table = new ITBaseEntityTable();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT Distinct Allele.* from Allele, Gene where "
                    + "(Allele._gene_key = " + gKey + ") || ((Allele._gene_key  is Null) "
                    + "&& ((Allele.genericAlleleGeneClass = Gene.geneClass) && "
                    + "(Gene._gene_key = " + gKey + "))) ORDER BY allele";
            
            System.out.println("find Allele query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveAlleleToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table;
    }
    
    private ITBaseEntityTable moveAlleleToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface> parentList  = new ArrayList<ITBaseEntityInterface>() ;
        AlleleEntity              alleleEntity  = null;
        Integer                   alleleKey     = -1;

        while (resultSet.next()) {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (alleleKey != resultSet.getInt("_allele_key"))
            {
                alleleKey = resultSet.getInt("_allele_key");

                // New allele
                alleleEntity = new AlleleEntity();

                alleleEntity.setAlleleKey(alleleKey);
                
                // set the FK's                  
                // set gene key
                if (resultSet.getObject("_gene_key") != null && 
                        resultSet.getInt("_gene_key") > 0) {
                   
                   alleleEntity.setGeneKey(resultSet.getInt("_gene_key"));
                }
                else {
                    alleleEntity.setGeneKey(null);
                }
                
                alleleEntity.setAllele(resultSet.getString("allele"));
                alleleEntity.setGenericAlleleGeneClass(resultSet.getString("genericAlleleGeneClass"));
                
                alleleEntity.setVersion(resultSet.getInt("version"));                   
            }
            if (alleleEntity != null) parentList.add(alleleEntity);
        }
        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }
    
    public ITBaseEntityTable genotypeSearchResults(GenotypeSearchDTO dto)
            throws SQLException {
        ITBaseEntityTable table = new ITBaseEntityTable();
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        // Result Set used
        ResultSet rs = null;
        
        String whereClause = " WHERE g._mouse_key = " + dto.getMouseKey();
        String geneClause = "";
        String allele1Clause = "";
        String allele2Clause = "";
        String locationClause = "";
        
        int startRow = 0;
        int endRow = 600;
        String query = "";

        try {            
            // if gene is selected
            if (dto.getGene() != null && dto.getGene().getLabSymbol() != null && !dto.getGene().getLabSymbol().equals("")) {
                geneClause = "g._gene_key = '"
                        + dto.getGene().getGeneKey() + "'";
            }
            
            // if allele1 is selected
            if (dto.getAlleleName1() != null && !dto.getAlleleName1().equals("")
                    && dto.getAlleleName1() != null) {
                allele1Clause = "g.allele1 = '" + dto.getAlleleName1() + "'";
            }
            
            // if allele2 is selected
            if (dto.getAlleleName2() != null && !dto.getAlleleName2().equals("")
                    && dto.getAlleleName2() != null) {
                allele2Clause = "g.allele2 = '" + dto.getAlleleName2() + "'";
            }
            
            // if location is selected
            if (dto.getLocation() != null && !dto.getLocation().equals("")) {
                locationClause = "g.sampleLocation = '"
                        + dto.getLocation() + "'";
            }
            
            if (!geneClause.equals("")) {
                whereClause += " AND " + geneClause;
            }

            if (!allele1Clause.equals("")) {
                whereClause += " AND " + allele1Clause;
            }
            
            if (!allele2Clause.equals("")) {
                whereClause += " AND " + allele2Clause;
            }

            if (!locationClause.equals("")) {
                whereClause += " AND " + locationClause;
            }

            // Open connect to database
            con = super.getConnection();
            
            query = "SELECT g.* FROM Genotype g "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;
            
            System.out.println("query " + query);
            
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveGenotypesToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(GenotypeDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::GenotypeDAO::NullPointerException:: "
                    + "genotypeSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(GenotypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::GenotypeDAO::SQLException:: "
                    + "genotypeSearchResults  \n" + ex);
        } finally {

        }
        return table;
    }
    
    private ITBaseEntityTable moveGenotypesToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface> parentList  = new ArrayList<ITBaseEntityInterface>() ;
        GenotypeEntity              genotypeEntity  = null;
        Integer                     genotypeKey     = -1;

        while (resultSet.next())
        {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (genotypeKey != resultSet.getInt("_genotype_key"))
            {
                genotypeKey = resultSet.getInt("_genotype_key");

                // New genotype
                genotypeEntity = new GenotypeEntity();

                genotypeEntity.setGenotypeKey(genotypeKey);
                
                // set the FK's                  
                // set gene key
                if (resultSet.getObject("_gene_key") != null && 
                        resultSet.getInt("_gene_key") > 0) {
                   
                   genotypeEntity.setGeneKey((GeneEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new GeneEntity(), resultSet.getInt("_gene_key")));
                }
                
                // set mouse key
                if (resultSet.getObject("_mouse_key") != null && 
                        resultSet.getInt("_mouse_key") > 0) {
                   genotypeEntity.setMouseKey((MouseEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new MouseEntity(), resultSet.getInt("_mouse_key")));
                }
                
                genotypeEntity.setAll1Conf(resultSet.getBoolean("all1Conf"));
                genotypeEntity.setAll2Conf(resultSet.getBoolean("all2Conf"));

                genotypeEntity.setAllele1(resultSet.getString("allele1"));
                genotypeEntity.setAllele2(resultSet.getString("allele2"));
                genotypeEntity.setGtDate(resultSet.getDate("gtDate"));
                genotypeEntity.setGenoPage(resultSet.getString("genoPage"));
                genotypeEntity.setSampleLocation(resultSet.getString("sampleLocation"));
                genotypeEntity.setComment(resultSet.getString("comment"));
                genotypeEntity.setVersion(resultSet.getInt("version"));                   
            }
            if (genotypeEntity != null) parentList.add(genotypeEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }
}
