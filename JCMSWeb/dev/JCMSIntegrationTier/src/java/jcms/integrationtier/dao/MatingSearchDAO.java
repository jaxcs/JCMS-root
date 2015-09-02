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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.dto.MatingSearchDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class MatingSearchDAO extends MySQLDAO {
    
    private static final long serialVersionUID = 0102L;
    
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;
    private ITBaseFacade it = new ITBaseFacade();
    SystemDao dao = new SystemDao();

    public ITBaseEntityTable getSearchResults(MatingSearchDTO dto) throws 
            SQLException {
        ITBaseEntityTable table = new ITBaseEntityTable();

        // Result Set used
        ResultSet rs = null;

        String whereClause = " WHERE m._mating_key > 0 ";
        String strainClause = "";
        String matingStatusClause = "";
        
        int startRow = 0;
        int endRow = 500;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            String matingIDFilter = "";
                        
            // if matingID is selected
            String matingID = (dto.getMatingID() == null || dto.getMatingID().equals("") ? "" : dto.getMatingID().toString());
                        
            if (!matingID.trim().isEmpty()) {       
                String matingFilter = (dto.getMatingFilter() == null || dto.getMatingFilter().equals("") ? "" : dto.getMatingFilter());
                
                if (matingFilter.equalsIgnoreCase("equals")) {
                    matingIDFilter = "m.matingID = " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("greater than")) {
                    matingIDFilter = "m.matingID > " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("less than")) {
                    matingIDFilter = "m.matingID < " + matingID;
                }
                else {
                    matingIDFilter = "m.matingID = " + matingID;
                }                                
                whereClause += " AND " + matingIDFilter;
            }

            // if strain is selected
            if (dto.getStrain() != null && !dto.getStrain().getStrainName().equals("")
                    && dto.getStrain().getStrainName() != null) {
                strainClause = "m._strain_key = '"
                        + dto.getStrain().getStrainKey() + "'";
            }

            // if crossStatus is selected
            if (dto.getMatingStatus() != null && !dto.getMatingStatus().getCrossStatus().equals("")
                    && dto.getMatingStatus().getCrossStatus() != null) {
                matingStatusClause = "m._crossStatus_key = " + dto.getMatingStatus().getCrossStatuskey();
            }
            
            // if owner is selected
            String ownerClause = "";
            
            // if owner is selected, build the list
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
            
            if (!strainClause.equals("")) {
                whereClause += " AND " + strainClause;
            }

            if (!ownerClause.equals("")) {
                whereClause += " AND m.owner IN (" + ownerClause + ") ";
            }

            if (!matingStatusClause.equals("")) {
                whereClause += " AND " + matingStatusClause;
            }

            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.* FROM Mating m "
                    //+ "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println("Mating query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveMatingToEntity(rs);
        } catch (NullPointerException npe) {
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table;
    }
    
    public Result getMatings(MatingSearchDTO dto) throws SQLException {

        // Result Set used
        ResultSet rs = null;
        Result resultData = null;

        String whereClause = " WHERE m._mating_key > 0 ";
        String strainClause = "";
        String matingStatusClause = "";
        
        int startRow = 0;
        int endRow = 500;

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            String matingIDFilter = "";
                        
            // if matingID is selected
            String matingID = (dto.getMatingID() == null || dto.getMatingID().equals("") ? "" : dto.getMatingID().toString());
                        
            if (!matingID.trim().isEmpty()) {       
                String matingFilter = (dto.getMatingFilter() == null || dto.getMatingFilter().equals("") ? "" : dto.getMatingFilter());
                
                if (matingFilter.equalsIgnoreCase("equals")) {
                    matingIDFilter = "m.matingID = " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("greater than")) {
                    matingIDFilter = "m.matingID > " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("less than")) {
                    matingIDFilter = "m.matingID < " + matingID;
                }
                else {
                    matingIDFilter = "m.matingID = " + matingID;
                }                                
                whereClause += " AND " + matingIDFilter;
            }

            // if strain is selected
            if (dto.getStrain() != null && !dto.getStrain().getStrainName().equals("")
                    && dto.getStrain().getStrainName() != null) {
                strainClause = "m._strain_key = '"
                        + dto.getStrain().getStrainKey() + "'";
            }

            // if crossStatus is selected
            if (dto.getMatingStatus() != null && !dto.getMatingStatus().getCrossStatus().equals("")
                    && dto.getMatingStatus().getCrossStatus() != null) {
                matingStatusClause = "m._crossStatus_key = " + dto.getMatingStatus().getCrossStatuskey();
            }
            
            // if owner is selected
            
            if (!strainClause.equals("")) {
                whereClause += " AND " + strainClause;
            }

            if (!matingStatusClause.equals("")) {
                whereClause += " AND " + matingStatusClause;
            }

            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.* FROM Mating m "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println("Mating query " + query);

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
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "getSearchResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "getSearchResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }
        
    private ITBaseEntityTable moveMatingToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface>   parentList  = new ArrayList<ITBaseEntityInterface> () ;
        MatingEntity                  matingEntity  = null;
        Integer                     matingKey     = -1;

        while (resultSet.next())
        {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (matingKey != resultSet.getInt("_mating_key"))
            {
                matingKey = resultSet.getInt("_mating_key");

                // New mating
                matingEntity = new MatingEntity();

                matingEntity.setMatingKey(matingKey);
                
                // set the FK's  
                
                // set strain key
                if (resultSet.getObject("_strain_key") != null && 
                        resultSet.getInt("_strain_key") > 0) {
                   matingEntity.setStrainKey((StrainEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new StrainEntity(), resultSet.getInt("_strain_key")));
                }
                
                // set crossStatus key
                if (resultSet.getObject("_crossStatus_key") != null && 
                        resultSet.getInt("_crossStatus_key") > 0) {                   
                   matingEntity.setCrossStatuskey((CvCrossstatusEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new CvCrossstatusEntity(), resultSet.getInt("_crossStatus_key")));
                }
                
                // set sire key
                if (resultSet.getObject("_sire_key") != null && 
                        resultSet.getInt("_sire_key") > 0) {                   
                   matingEntity.setSireKey((MouseEntity)dao.getSystemFacadeLocal().
                           findMouse(resultSet.getInt("_sire_key")));

                }
                
                // set the Dam1 
                if (resultSet.getObject("_dam1_key") != null && 
                        resultSet.getInt("_dam1_key") > 0) {
                    MouseEntity dam1 = new MouseEntity();
                            
                    dam1 = (MouseEntity)dao.getSystemFacadeLocal().
                            findMouse(resultSet.getInt("_dam1_key"));
                    
                    if (dam1 != null && dam1.getId() != null && !dam1.getId().
                            equals("")) {
                         matingEntity.setMatingCageID(dam1.getPenKey().getContainerID());
                         matingEntity.setDam1ID(dam1.getId());
                         matingEntity.setDam1Key(resultSet.getInt("_dam1_key"));
                    }
                }
                
                if (resultSet.getObject("_dam2_key") != null && 
                        resultSet.getInt("_dam2_key") > 0) {
                    matingEntity.setDam2Key(resultSet.getInt("_dam2_key"));  
                }
                
                if (resultSet.getObject("suggestedPenID") != null && 
                        resultSet.getInt("suggestedPenID") > 0) {
                    matingEntity.setSuggestedPenID(resultSet.getInt("suggestedPenID"));
                }
                
                // set the litter collection                
                List<LitterEntity> litterCollection = new ArrayList<LitterEntity>();
                litterCollection = this.getLitterEntity(matingKey);
                
                
                int totalMales = 0;
                int totalFemales = 0;
                
                for (int i = 0; i < litterCollection.size(); ++i) {
                    // add up males for the mating
                    if (litterCollection.get(i).getNumMale() != null) {
                        totalMales += litterCollection.get(i).getNumMale();
                    }
                    
                    // add up females for the mating
                    if (litterCollection.get(i).getNumFemale() != null) {
                        totalFemales += litterCollection.get(i).getNumFemale();
                    }                    
                }
                               
                // set the female and male litters to mating
                matingEntity.setTotalFemales(totalFemales);
                matingEntity.setTotalMales(totalMales);

                matingEntity.setSuggestedPenID(resultSet.getInt("suggestedPenID"));
                matingEntity.setMatingID(resultSet.getInt("matingID"));
                
                if (resultSet.getObject("matingDate") != null) {
                    matingEntity.setMatingDate(resultSet.getDate("matingDate"));
                }
                
                if (resultSet.getObject("retiredDate") != null) {
                    matingEntity.setRetiredDate(resultSet.getDate("retiredDate"));
                }
                
                if (resultSet.getObject("proposedRetireDate") != null) {
                    matingEntity.setProposedRetireDate(resultSet.getDate("proposedRetireDate"));
                }
                
                matingEntity.setWeanTime(resultSet.getBoolean("weanTime"));
                matingEntity.setGeneration(resultSet.getString("generation"));
                matingEntity.setNeedsTyping(resultSet.getBoolean("needsTyping"));
                
                if (resultSet.getObject("weanNote") != null) {
                    matingEntity.setWeanNote(resultSet.getString("weanNote"));
                }
                
                if (resultSet.getString("comment") != null) {
                    matingEntity.setComment(resultSet.getString("comment"));
                }
                
                if (resultSet.getString("proposedDiet") != null) {
                    matingEntity.setProposedDiet(resultSet.getString("proposedDiet"));
                }
                                
                matingEntity.setOwner(resultSet.getString("owner"));
                
                if (resultSet.getString("proposedRetireD1BrStatus") != null) {
                    matingEntity.setProposedRetireD1BrStatus(resultSet.getString("proposedRetireD1BrStatus"));
                }
                
                if (resultSet.getString("proposedRetireD1Diet") != null) {
                    matingEntity.setProposedRetireD1Diet(resultSet.getString("proposedRetireD1Diet"));
                }
                
                if (resultSet.getString("proposedRetireD1LfStatus") != null) {
                    matingEntity.setProposedRetireD1LfStatus(resultSet.getString("proposedRetireD1LfStatus"));
                }
                
                if (resultSet.getString("proposedRetireD2BrStatus") != null) {
                    matingEntity.setProposedRetireD2BrStatus(resultSet.getString("proposedRetireD2BrStatus"));
                }
                
                if (resultSet.getString("proposedRetireD2Diet") != null) {
                    matingEntity.setProposedRetireD2Diet(resultSet.getString("proposedRetireD2Diet"));
                }
                
                if (resultSet.getString("proposedRetireD2LfStatus") != null) {
                    matingEntity.setProposedRetireD2LfStatus(resultSet.getString("proposedRetireD2LfStatus")); 
                }
                
                if (resultSet.getString("proposedRetirePenStatus") != null) {
                    matingEntity.setProposedRetirePenStatus(resultSet.getString("proposedRetirePenStatus"));  
                }
               
                if (resultSet.getString("proposedRetireSBrStatus") != null) {
                    matingEntity.setProposedRetireSBrStatus(resultSet.getString("proposedRetireSBrStatus")); 
                }
                
                if (resultSet.getString("proposedRetireSDiet") != null) {
                    matingEntity.setProposedRetireSDiet(resultSet.getString("proposedRetireSDiet")); 
                }
                
                if (resultSet.getString("proposedRetireSLfStatus") != null) {
                    matingEntity.setProposedRetireSLfStatus(resultSet.getString("proposedRetireSLfStatus"));
                }
                
                if (resultSet.getObject("suggestedFirstLitterNum") != null) {
                     matingEntity.setSuggestedFirstLitterNum(resultSet.getInt("suggestedFirstLitterNum"));
                }                
                
                matingEntity.setVersion(resultSet.getInt("version"));                
            }
            if (matingEntity != null) parentList.add(matingEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }    
    
    public int findMatingByDamAndSire(int dKey, int sKey) throws 
            SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int cnt = 0;

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT count(*) AS cnt FROM Mating m WHERE _dam1_key = " +
                    dKey + " and _sire_key = " + sKey;
            
            System.out.println("find MatingByDamAndSire " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                if (rs.getObject("cnt") != null && 
                        rs.getInt("cnt") > 0) {  
                    cnt = rs.getInt("cnt");
                }
            }            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "findMatingByDamAndSire \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "findMatingByDamAndSire  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return cnt;
    }
    
    public ITBaseEntityTable findMatingByMouse(int dKey) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ITBaseEntityTable table = new ITBaseEntityTable();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.* FROM Mating m WHERE _dam1_key = " + dKey;
            
            System.out.println("findMatingByMouse " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveMatingToEntity(rs);
                        
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "findMatingByDamAndSire \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "findMatingByDamAndSire  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table;
    }
    
    public ITBaseEntityTable findMatingBySire(int dKey) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ITBaseEntityTable table = new ITBaseEntityTable();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.* FROM Mating m WHERE _sire_key = " + dKey;
            
            System.out.println("findMatingBySire " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            table = moveMatingToEntity(rs);
                        
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "findMatingByDamAndSire \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "findMatingByDamAndSire  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table;
    }
    
    public List<LitterEntity> getLitterEntity(int mkey) throws
            SQLException {
        // Result Set used
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        List<LitterEntity> litterCollection = new ArrayList<LitterEntity>();

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT l.* FROM Litter l WHERE l._mating_key "
                    + "= " + mkey;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                if (rs.getObject("_litter_key") != null && 
                        rs.getInt("_litter_key") > 0) {  
                    litterCollection.add((LitterEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new LitterEntity(), rs.getInt("_litter_key")));
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
        return litterCollection;
    }
    
    public int findMaxLitterKeyByMating(ITBaseEntityInterface mEntity, int mkey) 
            throws SQLException {
        // Result Set used
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        int litterKey = 0;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT MAX(l._litter_key) AS lKey FROM Litter l WHERE "
                    + "l._mating_key = " + mkey;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                if (rs.getObject("lKey") != null && rs.getInt("lKey") > 0) {  
                    litterKey = rs.getInt("lKey");
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
        return litterKey;
    }
    
    public String findMaxLitterIDByMating(ITBaseEntityInterface mEntity, int 
            mkey) throws SQLException {
        // Result Set used
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String litterID = "";

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT MAX(l.litterID) AS ID FROM Litter l WHERE "
                    + "l._mating_key = " + mkey;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                if (rs.getObject("ID") != null) {  
                    litterID = rs.getString("ID");
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
        return litterID;
    }
    
    public String FindMatingWeanTimeAsString (int matingID) {
        String weanTimeAsString = "-1";
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        
        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT weanTime FROM Mating WHERE "
                    + "matingID = " + matingID;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                if (rs.getObject("weanTime") != null) {  
                    weanTimeAsString = rs.getString("weanTime");
                }
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::NullPointerException:: "
                    + "FindMatingWeanTimeAsString  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MatingSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingSearchDAO::SQLException:: "
                    + "FindMatingWeanTimeAsString  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return weanTimeAsString;
    }
    
    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {
        if (qDebug) {
            getLogger().logDebug(formatLogMessage("MatingSearchDAO."
                    + method, message));
        }
    }        
}