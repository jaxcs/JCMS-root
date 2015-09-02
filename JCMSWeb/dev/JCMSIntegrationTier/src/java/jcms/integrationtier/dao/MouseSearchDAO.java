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
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.GenotypeEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.dto.GenotypeSearchDTO;
import jcms.integrationtier.dto.MouseGenotypeDTO;
import jcms.integrationtier.dto.MouseSearchDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class MouseSearchDAO extends MySQLDAO {
    
    private static final long serialVersionUID = 001002L;
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;
    private ITBaseFacade it = new ITBaseFacade();
    SystemDao dao = new SystemDao();

    public String searchResults(MouseSearchDTO dto, GenotypeSearchDTO gdto) throws SQLException {
        String whereClause = " WHERE m._mouse_key > 0 ";
        String strainClause = "";
        String lifeStatusClause = "";
        String penClause = "";
        String geneClause = "";
        String genotypeClause = "";
        
        int startRow = 0;
        int endRow = 500;
        String query = "";

        try {
            String mouseIDFilter = "";
                        
            // if mouseID is selected
            String mouseID = (dto.getMouseID() == null || dto.getMouseID().equals("") ? "" : dto.getMouseID().toString());
                        
            if (!mouseID.trim().isEmpty()) {       
                String mouseFilter = (dto.getMouseFilter() == null || dto.getMouseFilter().equals("") ? "" : dto.getMouseFilter());
                
                if (mouseFilter.equalsIgnoreCase("equals")) {
                    mouseIDFilter = "m.ID = '" + mouseID + "'";
                }
                else if (mouseFilter.equalsIgnoreCase("contains")) {
                    mouseIDFilter = "m.ID LIKE '%" + mouseID + "%'";
                }                              
                whereClause += " AND " + mouseIDFilter;
            }

            // if strain is selected
            if (dto.getStrain() != null && !dto.getStrain().getStrainName().equals("")
                    && dto.getStrain().getStrainName() != null) {
                strainClause = "m._strain_key = '"
                        + dto.getStrain().getStrainKey() + "'";
            }

            // if LifeStatus is selected
            if (dto.getLifeStatus() != null && !dto.getLifeStatus().getLifeStatus().equals("")
                    && dto.getLifeStatus().getLifeStatus() != null) {
                lifeStatusClause = "m.lifeStatus = '" + dto.getLifeStatus().getLifeStatus() + "'";
            }

            // if owner is selected, build the list
            String ownerClause = "";

            if (dto.getOwners() != null) {
                for (int i = 0; i < dto.getOwners().size(); ++i) {
                    // if last item, then no comma
                    if (i == dto.getOwners().size() - 1) {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'";
                    } else {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'" + " ,";
                    }
                }
            }
            
            String penIDFilter = "";
                        
            // if penid is selected
            String penID = (dto.getPenID() == null || dto.getPenID().equals("") ? "" : dto.getPenID().toString());
                        
            if (!penID.trim().isEmpty()) {       
                String penFilter = (dto.getPenFilter() == null || dto.getPenFilter().equals("") ? "" : dto.getPenFilter());
                
                if (penFilter.equalsIgnoreCase("equals")) {
                    penIDFilter = "c.containerID = " + penID;
                }
                else if (penFilter.equalsIgnoreCase("greater than")) {
                    penIDFilter = "c.containerID > " + penID;
                }
                else if (penFilter.equalsIgnoreCase("less than")) {
                    penIDFilter = "c.containerID < " + penID;
                }
                else {
                    penIDFilter = "c.containerID = " + penID;
                }                                
                penClause += penIDFilter;
            }
            
            // if sex is selected
            String sexClause = "";
            if (dto.getSex() != null && dto.getSex().getSex() != null && 
                    !dto.getSex().getSex().equals("")) {
                sexClause = "m.sex = '" + dto.getSex().getAbbreviation() + "'";
            }
            
            // if generation is selected
            String generationClause = "";
            if (dto.getGeneration() != null && dto.getGeneration().getGeneration() != null && 
                    !dto.getGeneration().getGeneration().equals("")) {
                generationClause = "m.generation = '" + dto.getGeneration().getGeneration() + "'";
            }
            
            // set the birthDate
            Date startDt = null;
            Date endDt = null;
            
            if (dto.getDOBStartDate() != null) {
                startDt = dto.getDOBStartDate();
            }
            
            if (dto.getDOBEndDate() != null) {
                endDt = dto.getDOBEndDate();
            }
            
            // (2) create our date "formatter" (the date format we want)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");

            // (3) create a new String using the date format we want
            String dateClause = "";
            
            if (startDt != null && endDt != null) {
                String sDt = formatter.format(startDt);
                String eDt = formatter.format(endDt);

                dateClause = "m.birthDate BETWEEN '" + sDt + "' AND '" + eDt + 
                        "'";
            }
            
            // =======================
            // if genotype is selected
            // =======================
            
            // if gene is selected
            if (gdto.getGene() != null && gdto.getGene().getLabSymbol() != null && !gdto.getGene().getLabSymbol().equals("")) {
                genotypeClause = " g._gene_key = " + gdto.getGene().getGeneKey() + " ";
            }
            
            // if allele1 is selected
            if (gdto.getAlleleName1() != null && !gdto.getAlleleName1().equals("")) {
                if (genotypeClause.length() > 0) genotypeClause += " AND ";
                genotypeClause += " g.allele1 = " + varcharParser(gdto.getAlleleName1()) + " ";
            }
            
            // if allele2 is selected
            if (gdto.getAlleleName2() != null && !gdto.getAlleleName2().equals("")) {
                if (genotypeClause.length() > 0) genotypeClause += " AND ";
                genotypeClause += " g.allele2 = " + varcharParser(gdto.getAlleleName2()) + " ";
            }
            
            // if gene contains search
            if (gdto.getPartialGene().length() > 0) {
                geneClause = " gn.labSymbol LIKE " + this.varcharParserContains(gdto.getPartialGene()) + " ";
            }
            
            // =======================
            
            
            if (!strainClause.equals("")) {
                whereClause += " AND " + strainClause;
            }

            if (!ownerClause.equals("")) {
                whereClause += " AND m.owner IN (" + ownerClause + ") ";
            }

            if (!lifeStatusClause.equals("")) {
                whereClause += " AND " + lifeStatusClause;
            }
            
            if (!penClause.equals("")) {
                whereClause += " AND " + penClause;
            }
            
            if (!sexClause.equals("")) {
                whereClause += " AND " + sexClause;
            }
            
            if (!generationClause.equals("")) {
                whereClause += " AND " + generationClause;
            }
            
            if (!dateClause.equals("")) {
                whereClause += " AND " + dateClause;
            }

            if (!genotypeClause.equals("")) {
                whereClause += " AND " + genotypeClause;
            }
            
            if (!geneClause.equals("")) {
                whereClause += " AND " + geneClause;
            }
            
            // Open connect to database
            con = super.getConnection();

            query = "SELECT m._mouse_key, s.strainName, l.litterID, "
                    + "c.containerID, m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation, m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
                    + "m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, "
                    + "m._litter_key, m._pen_key, m._strain_key, m.version FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + "LEFT JOIN Litter l ON m._litter_key = l._litter_key "
                    + "LEFT JOIN Container c ON m._pen_key = c._container_key " ;
                    
            if (!genotypeClause.equals("") && geneClause.equals("")) {
                query += "LEFT JOIN Genotype g ON m._mouse_key = g._mouse_key ";
            }
            
            if (!geneClause.equals("")) {
                query += "LEFT JOIN Genotype g ON m._mouse_key = g._mouse_key ";
                query += "LEFT JOIN Gene gn ON g._gene_key = gn._gene_key ";
            }
                    
            query += whereClause + " LIMIT " + startRow + ", " + endRow;

            System.out.println("Mouse query " + query);
 
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {

        }
        return query;
    }
    
    public ITBaseEntityTable getSearchResults(MouseSearchDTO dto) throws 
            SQLException {
        ITBaseEntityTable table = new ITBaseEntityTable();
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        // Result Set used
        ResultSet rs = null;
        
        try {
            String query = this.searchResults(dto, new GenotypeSearchDTO());
            
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveMiceToEntity(rs);
            
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
    
    public ITBaseEntityTable getSearchResults(MouseSearchDTO dto, GenotypeSearchDTO gDTO) throws 
            SQLException {
        ITBaseEntityTable table = new ITBaseEntityTable();
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        // Result Set used
        ResultSet rs = null;
        
        try {
            String query = this.searchResults(dto, gDTO);
            
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveMiceToEntity(rs);
            
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
    
    public ITBaseEntityTable findGenotypesByMouseKey(int mKey, GenotypeSearchDTO dto) throws
            SQLException {
        ITBaseEntityTable table = new ITBaseEntityTable();
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementGenotypeFilterTotal = null;
        PreparedStatement preparedStatementGenotypeMouseTotal = null;
        // Result Set used
        ResultSet rs = null;
        ResultSet rsGenotypeFilterTotal = null;
        ResultSet rsGenotypeMouseTotal = null;
        
        String whereClause = " WHERE g._mouse_key = " + mKey;
        String geneClause = "";
        String geneContainsClause = "";
        String allele1Clause = "";
        String allele2Clause = "";
        String locationClause = "";
        
        int startRow = 0;
        int endRow = 10;
        String query = "";
        
        try {
            
            // if gene is selected
            if (dto.getGene() != null && dto.getGene().getLabSymbol() != null && !dto.getGene().getLabSymbol().equals("")) {
                geneClause = "g._gene_key = '"
                        + dto.getGene().getGeneKey() + "'";
            }
            
            // if gene contains search
            if (dto.getPartialGene().length() > 0) {
                geneContainsClause = "gn.labSymbol LIKE '%" + dto.getPartialGene() + "%'";
            }
            
            // if allele1 is selected
            if (dto.getAlleleName1() != null && !dto.getAlleleName1().equals("")) {
                allele1Clause = " g.allele1 = " + varcharParser(dto.getAlleleName1()) + " ";
            }

            // if allele2 is selected
            if (dto.getAlleleName2() != null && !dto.getAlleleName2().equals("")) {
                allele2Clause = " g.allele2 = " + varcharParser(dto.getAlleleName2()) + " ";
            }
            
            // if location is selected
            if (dto.getLocation() != null && !dto.getLocation().equals("")) {
                locationClause = " g.sampleLocation = '"
                        + dto.getLocation() + "'";
            }
            
            if (!geneClause.equals("") && !geneContainsClause.equals("")) {
                whereClause += " AND (" + geneClause + " OR " + geneContainsClause + ") ";
            }
            else {
                if (!geneClause.equals("")) {
                    whereClause += " AND " + geneClause;
                }
                if (!geneContainsClause.equals("")) {
                    whereClause += " AND " + geneContainsClause;
                }
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
            
            // Run separate query to get the total number of genotypes for this mouse
            query = "SELECT COUNT(g._gene_key) AS genotypeMouseTotal FROM Genotype g " 
                    + " WHERE g._mouse_key = " + mKey ;
            preparedStatementGenotypeMouseTotal = con.prepareStatement(query);
            rsGenotypeMouseTotal = preparedStatementGenotypeMouseTotal.executeQuery();

            // Run separate query to get the total number of filtered genotypes
            query = "SELECT COUNT(g._gene_key) AS genotypeFilterTotal FROM Genotype g " 
                  + "INNER JOIN Gene gn ON g._gene_key = gn._gene_key "
                  + whereClause;
            preparedStatementGenotypeFilterTotal = con.prepareStatement(query);
            rsGenotypeFilterTotal = preparedStatementGenotypeFilterTotal.executeQuery();

            query = "SELECT g.*, gn.* FROM Genotype g " 
                  + " INNER JOIN Gene gn ON g._gene_key = gn._gene_key "
                  + whereClause
                  + " LIMIT " + startRow + ", " + endRow;
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveGenotypeToEntity(rs, rsGenotypeFilterTotal, rsGenotypeMouseTotal);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {

        }
        return table;
    }
    
    public List<MouseGenotypeDTO> getMouseGenotypeSearchResults(MouseSearchDTO dto, GenotypeSearchDTO gdto) throws SQLException {
        List<MouseGenotypeDTO> dtoList  = new ArrayList<MouseGenotypeDTO>();
         // Used in SubQueries
        PreparedStatement preparedStatement = null;
        // Result Set used
        ResultSet rs = null;
        
        try {
            String query = this.searchResults(dto, gdto);
            
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            dtoList = this.generateMouseGenotypeDTOLst(rs, gdto);
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
        return dtoList;
    }
    
    public Result getDams(MouseSearchDTO dto) throws SQLException {
        // Result Set used
        ResultSet rs = null;
        Result resultData = null;
        
        String whereClause = " WHERE m.sex='F' ";
        String strainClause = "";
        String lifeStatusClause = "";
        
        int startRow = 0;
        int endRow = 500;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // if strain is selected
            if (dto.getStrain() != null && !dto.getStrain().getStrainName().equals("")
                    && dto.getStrain().getStrainName() != null) {
                strainClause = "m._strain_key = '"
                        + dto.getStrain().getStrainKey() + "'";
            }

            // if LifeStatus is selected
            if (dto.getLifeStatus() != null && !dto.getLifeStatus().getLifeStatus().equals("")
                    && dto.getLifeStatus().getLifeStatus() != null) {
                lifeStatusClause = "m.lifeStatus = '" + dto.getLifeStatus().getLifeStatus() + "'";
            }
            
            // if owner is selected, build the list
            String ownerClause = "";
            if (dto.getOwners() != null) {
                for (int i = 0; i < dto.getOwners().size(); ++i) {
                    // if last item, then no comma
                    if (i == dto.getOwners().size() - 1) {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'";
                    } else {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'" + " ,";
                    }
                }
            }

            if (!ownerClause.equals("")) {
                whereClause += " AND m.owner IN (" + ownerClause + ") ";
            }

            if (!strainClause.equals("")) {
                whereClause += " AND " + strainClause;
            }

            if (!lifeStatusClause.equals("")) {
                whereClause += " AND " + lifeStatusClause;
            }
            
            // Open connect to database
            con = super.getConnection();
            
            String query = "SELECT DISTINCT m._mouse_key, s.strainName, "
                    + "m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin " 
                    + "FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println("Mouse query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActivePlansQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActivePlans");
            }
            
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
        return resultData;
    }
    
    public Result getSires(MouseSearchDTO dto) throws SQLException {
        // Result Set used
        ResultSet rs = null;
        Result resultData = null;
        
        String whereClause = " WHERE m.sex='M' ";
        String strainClause = "";
        String lifeStatusClause = "";
        
        int startRow = 0;
        int endRow = 500;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // if strain is selected
            if (dto.getStrain() != null && !dto.getStrain().getStrainName().equals("")
                    && dto.getStrain().getStrainName() != null) {
                strainClause = "m._strain_key = '"
                        + dto.getStrain().getStrainKey() + "'";
            }

            // if LifeStatus is selected
            if (dto.getLifeStatus() != null && !dto.getLifeStatus().getLifeStatus().equals("")
                    && dto.getLifeStatus().getLifeStatus() != null) {
                lifeStatusClause = "m.lifeStatus = '" + dto.getLifeStatus().getLifeStatus() + "'";
            }
            
            // if owner is selected, build the list
            String ownerClause = "";
            if (dto.getOwners() != null) {
                for (int i = 0; i < dto.getOwners().size(); ++i) {
                    // if last item, then no comma
                    if (i == dto.getOwners().size() - 1) {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'";
                    } else {
                        ownerClause += "'" + dto.getOwners().get(i).getOwner() + "'" + " ,";
                    }
                }
            }

            if (!ownerClause.equals("")) {
                whereClause += " AND m.owner IN (" + ownerClause + ") ";
            }
            
            if (!strainClause.equals("")) {
                whereClause += " AND " + strainClause;
            }

            if (!lifeStatusClause.equals("")) {
                whereClause += " AND " + lifeStatusClause;
            }
            
            // Open connect to database
            con = super.getConnection();
            
            String query = "SELECT DISTINCT m._mouse_key, s.strainName, "
                    + "m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin "
                    + "FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println("Mouse query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "MouseQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "MouseQuery");
            }            
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
        return resultData;
    }
        
    private ITBaseEntityTable moveMiceToEntity(ResultSet resultSet) throws SQLException {
        List<ITBaseEntityInterface>   parentList  = new ArrayList<ITBaseEntityInterface> () ;
        MouseEntity                   mouseEntity = null;
        Integer                       mouseKey    = -1;

        while (resultSet.next())
        {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (mouseKey != resultSet.getInt("_mouse_key"))
            {
                mouseKey = resultSet.getInt("_mouse_key");

                // New mouse
                mouseEntity = new MouseEntity();
                mouseEntity.setMouseKey(mouseKey);
                
                // set the FK's                  
                // set litter key
                if (resultSet.getObject("_litter_key") != null && 
                        resultSet.getInt("_litter_key") > 0) {
                   
                   mouseEntity.setLitterKey((LitterEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new LitterEntity(), resultSet.getInt("_litter_key")));
                }
                
                // set strain key
                if (resultSet.getObject("_strain_key") != null && 
                        resultSet.getInt("_strain_key") > 0) {
                   mouseEntity.setStrainKey((StrainEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new StrainEntity(), resultSet.getInt("_strain_key")));
                }
                
                // set pen key
                if (resultSet.getObject("_pen_key") != null && 
                        resultSet.getInt("_pen_key") > 0) {                   
                   mouseEntity.setPenKey((ContainerEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new ContainerEntity(), resultSet.getInt("_pen_key")));
                }

                mouseEntity.setId(resultSet.getString("ID"));
                mouseEntity.setNewTag(resultSet.getString("newTag"));

                mouseEntity.setBirthDate(resultSet.getDate("birthDate"));
                mouseEntity.setExitDate(resultSet.getDate("exitDate"));
                mouseEntity.setCod(resultSet.getString("cod"));
                mouseEntity.setCodNotes(resultSet.getString("codNotes"));
                mouseEntity.setGeneration(resultSet.getString("generation"));
                mouseEntity.setSex(resultSet.getString("sex"));
                mouseEntity.setLifeStatus(resultSet.getString("lifeStatus"));
                mouseEntity.setBreedingStatus(resultSet.getString("breedingStatus"));
                mouseEntity.setCoatColor(resultSet.getString("coatColor"));
                mouseEntity.setDiet(resultSet.getString("diet"));
                mouseEntity.setOwner(resultSet.getString("owner"));
                mouseEntity.setOrigin(resultSet.getString("origin"));
                mouseEntity.setProtocol(resultSet.getString("protocol"));                
                
                mouseEntity.setComment(resultSet.getString("comment"));
                mouseEntity.setSampleVialID(resultSet.getString("sampleVialID"));
                mouseEntity.setSampleVialTagPosition(resultSet.getString("sampleVialTagPosition"));
                mouseEntity.setVersion(resultSet.getInt("version"));                
            }
            if (mouseEntity != null) parentList.add(mouseEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }
    
    private ITBaseEntityTable moveGenotypeToEntity(ResultSet resultSet, ResultSet rsGenotypeFilterTotal, ResultSet rsGenotypeMouseTotal)
            throws SQLException {
        List<ITBaseEntityInterface> parentList  = new ArrayList<ITBaseEntityInterface>() ;
        GenotypeEntity              genotypeEntity  = null;
        Integer                     genotypeKey     = -1;

        // Get total number of filtered genotypes
        Integer genotypeFilterTotal = null;
        while (rsGenotypeFilterTotal.next()) {
            genotypeFilterTotal = rsGenotypeFilterTotal.getInt("genotypeFilterTotal");
        }
        // Get total number of mouse genotypes
        Integer genotypeMouseTotal = null;
        while (rsGenotypeMouseTotal.next()) {
            genotypeMouseTotal = rsGenotypeMouseTotal.getInt("genotypeMouseTotal");
        }

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
                if (resultSet.getObject("gtDate") != null && resultSet.getObject("gtDate").toString().length() > 5) 
                    genotypeEntity.setGtDate(resultSet.getDate("gtDate"));
                genotypeEntity.setGenoPage(resultSet.getString("genoPage"));
                genotypeEntity.setSampleLocation((resultSet.getString("sampleLocation") != null ? resultSet.getString("sampleLocation") : "")) ;
                genotypeEntity.setComment((resultSet.getString("comment") != null ? resultSet.getString("comment") : ""));
                genotypeEntity.setVersion(resultSet.getInt("version"));          
                genotypeEntity.setGenotypeFilterTotal(genotypeFilterTotal);
                genotypeEntity.setGenotypeMouseTotal(genotypeMouseTotal);
                
                if (genotypeEntity.getAllele2() != null && !genotypeEntity.getAllele2().isEmpty())
                    genotypeEntity.setGenotypeDisplayFormat(genotypeEntity.getAllele1() + "/" + genotypeEntity.getAllele2());
                else
                    genotypeEntity.setGenotypeDisplayFormat(genotypeEntity.getAllele1());
            }
            if (genotypeEntity != null) parentList.add(genotypeEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }
    
    private List<MouseGenotypeDTO> generateMouseGenotypeDTOLst(ResultSet 
            resultSet, GenotypeSearchDTO gdto)
            throws SQLException {
        List<MouseGenotypeDTO> dtoList  = new ArrayList<MouseGenotypeDTO>();
        List<GenotypeEntity>   gList;
        MouseGenotypeDTO       mouseGenotypeDTO;
        MouseEntity            mouseEntity  = null;
        Integer                mouseKey     = -1;
        Boolean                addIt        = false;

        while (resultSet.next())
        {
            mouseGenotypeDTO = new MouseGenotypeDTO();
            gList = new ArrayList<GenotypeEntity>();
            
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (mouseKey != resultSet.getInt("_mouse_key"))
            {
                mouseKey = resultSet.getInt("_mouse_key");

                // New mouse
                mouseEntity = new MouseEntity();
                mouseEntity.setMouseKey(mouseKey);
                
                // set the FK's                  
                // set litter key
                if (resultSet.getObject("_litter_key") != null && 
                        resultSet.getInt("_litter_key") > 0) {
                   
                   mouseEntity.setLitterKey((LitterEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new LitterEntity(), resultSet.getInt("_litter_key")));
                }
                
                // set strain key
                if (resultSet.getObject("_strain_key") != null && 
                        resultSet.getInt("_strain_key") > 0) {
                   mouseEntity.setStrainKey((StrainEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new StrainEntity(), resultSet.getInt("_strain_key")));
                }
                
                // set pen key
                if (resultSet.getObject("_pen_key") != null && 
                        resultSet.getInt("_pen_key") > 0) {                   
                   mouseEntity.setPenKey((ContainerEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new ContainerEntity(), resultSet.getInt("_pen_key")));
                }

                mouseEntity.setId(resultSet.getString("ID"));
                mouseEntity.setNewTag(resultSet.getString("newTag"));

                mouseEntity.setBirthDate(resultSet.getDate("birthDate"));
                mouseEntity.setExitDate(resultSet.getDate("exitDate"));
                mouseEntity.setCod(resultSet.getString("cod"));
                mouseEntity.setCodNotes(resultSet.getString("codNotes"));
                mouseEntity.setGeneration(resultSet.getString("generation"));
                mouseEntity.setSex(resultSet.getString("sex"));
                mouseEntity.setLifeStatus(resultSet.getString("lifeStatus"));
                mouseEntity.setBreedingStatus(resultSet.getString("breedingStatus"));
                mouseEntity.setCoatColor(resultSet.getString("coatColor"));
                mouseEntity.setDiet(resultSet.getString("diet"));
                mouseEntity.setOwner(resultSet.getString("owner"));
                mouseEntity.setOrigin(resultSet.getString("origin"));
                mouseEntity.setProtocol(resultSet.getString("protocol"));                
                
                mouseEntity.setComment(resultSet.getString("comment"));
                mouseEntity.setSampleVialID(resultSet.getString("sampleVialID"));
                mouseEntity.setSampleVialTagPosition(resultSet.getString("sampleVialTagPosition"));
                mouseEntity.setVersion(resultSet.getInt("version"));                
            }
            if (mouseEntity != null) {
                
                // generate dto with mouse and genotypes
                mouseGenotypeDTO.setMouseEntity(mouseEntity);
                
                
                ITBaseEntityTable table = new ITBaseEntityTable();
                table = this.findGenotypesByMouseKey(mouseKey, gdto);
            
                for (ITBaseEntityInterface entity : table.getList()) {
                    gList.add((GenotypeEntity) entity);
                }
                
                // Add Genotype Form
                // Return mouse and genotypes if no genotype filter
                // Or return mouse and filtered genotype if filtering by genotype value
                // set the genotype list
                if (gdto.hasFilter() != null && gdto.hasFilter()) {
                    // filtering by genotype info, add mouse only if genotype found
                    if (table.getList().size() > 0) {
                        addIt = true;
                    }
                }
                else {
                    // no genotype filters, just add the mouse
                    addIt = true;
                }
                if (addIt) {
                    mouseGenotypeDTO.setGenotypeList(gList);
                    if (mouseGenotypeDTO.getGenotypeList() != null && !mouseGenotypeDTO.getGenotypeList().isEmpty()) {
                        mouseGenotypeDTO.setGenotypeFilterTotal(gList.get(0).getGenotypeFilterTotal().intValue());
                        mouseGenotypeDTO.setGenotypeMouseTotal(gList.get(0).getGenotypeMouseTotal().intValue());
                    }
                    dtoList.add(mouseGenotypeDTO);
                }
            }
            addIt = false;
        }
        return dtoList;
    }
    
    public LitterEntity getLitterEntity(int lkey) throws
            SQLException {
        LitterEntity litterInfo = new LitterEntity();
        // Result Set used
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT l._litter_key, l._mating_key, l.litterID, "
                    + "l.totalBorn, l.birthDate, l.numFemale, l.numMale, l.weanDate, "
                    + "l.tagDate, l.status, l.comment, l.version FROM litter l "
                    + "WHERE l._litter_key = " + lkey;

            System.out.println("litter query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            LitterEntity litterEntity = null;

            while (rs.next()) {
                // Since the result set can have more than one LineName per Line
                // The results are sorted by LineKey and StockNumber
                // Only create a new LineEntity if the primary key has changed.
                // New mouse
                litterEntity = new LitterEntity();

                litterEntity.setLitterKey(lkey);

                // set the FK's

                // set strain key
                if (rs.getObject("_mating_key") != null && 
                        rs.getInt("_mating_key") > 0) {
                   litterEntity.setMatingKey((MatingEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new StrainEntity(), rs.getInt("_mating_key")));
                   System.out.println("strain " + litterEntity.getMatingKey().getMatingID());
                }

                litterEntity.setLitterID(rs.getString("litterID"));
                litterEntity.setTotalBorn(rs.getShort("totalBorn"));
                litterEntity.setBirthDate(rs.getDate("birthDate"));

                litterEntity.setNumFemale(rs.getShort("numFemale"));
                litterEntity.setNumMale(rs.getShort("numMale"));

                litterEntity.setWeanDate(rs.getDate("weanDate"));
                litterEntity.setTagDate(rs.getDate("tagDate"));
                litterEntity.setStatus(rs.getString("status"));
                litterEntity.setComment(rs.getString("comment"));
                litterEntity.setVersion(rs.getInt("version"));

                if (litterEntity != null) {
                    litterInfo = litterEntity;
                }
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::NullPointerException:: "
                    + "getLitterEntity  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseSearchDAO::SQLException:: "
                    + "getLitterEntity  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return litterInfo;
    }
        
    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {
        if (qDebug) {
            getLogger().logDebug(formatLogMessage("PenSearchDAO."
                    + method, message));
        }
    }    
}