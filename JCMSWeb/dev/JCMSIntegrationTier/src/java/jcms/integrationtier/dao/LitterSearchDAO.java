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
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.dto.LitterSearchDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class LitterSearchDAO extends MySQLDAO {
    // Recommended by lint if your class is serialiable

    private static final long serialVersionUID = 001122L;
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;

    public Result getSearchResults(LitterSearchDTO dto)
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        String whereClause = "";
        String matingClause = "";
        String strainClause = "";
        String birthDateClause = "";

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            String statusFilter = (dto.getStatus() == null || dto.getStatus().equals("") ? "" : dto.getStatus());
            if (!statusFilter.trim().isEmpty()) {
                statusFilter = "l.status LIKE '%" + dto.getStatus() + "%'";    // Filter: surround with "* ... *".
                whereClause += " AND " + statusFilter;
            }

            String litterIDFilter = (dto.getLitterID() == null || dto.getLitterID().equals("") ? "" : dto.getLitterID().toString());
            if (!litterIDFilter.trim().isEmpty()) {
                litterIDFilter = "l.litterID like '%" + dto.getLitterID() + "%'";    // Filter: surround with "* ... *".
                whereClause += " AND " + litterIDFilter;
            }

            // if mating is selected
            String matingID = (dto.getMatingID() == null || dto.getMatingID().equals("") ? "" : dto.getMatingID().toString());
                        
            if (!matingID.trim().isEmpty()) {       
                String matingFilter = (dto.getMatingFilter() == null || dto.getMatingFilter().equals("") ? "" : dto.getMatingFilter());
                
                if (matingFilter.equalsIgnoreCase("equals")) {
                    matingClause = "m.matingID = " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("greater than")) {
                    matingClause = "m.matingID > " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("less than")) {
                    matingClause = "m.matingID < " + matingID;
                }
                else {
                    matingClause = "m.matingID = " + matingID;
                }
                                
                whereClause += " AND " + matingClause;
            }

            // if strain is selected
            if (dto.getStrain() != null && dto.getStrain().getStrainKey() > 0) {
                // if all billable status is selected, then check for the status
                // that are billable
                strainClause = "l._litter_key IN (SELECT l._litter_key FROM "
                        + "Mating m, Litter l WHERE l._mating_key = m._mating_key "
                        + "AND m._strain_key = '" + dto.getStrain().getStrainKey() + "')";
                whereClause += " AND " + strainClause;
            }

            // if birthDate is selected                        
            if (dto.getBirthDate() != null) {
                // set the actionDate
                Date date = dto.getBirthDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                String currentDt = formatter.format(date);
                // if all billable status is selected, then check for the status
                // that are billable
                birthDateClause = " l.birthDate >= '" + currentDt + "' ";
                whereClause += " AND " + birthDateClause;
            }
            int startRow = 0;
            int endRow = 500;

            // Open connect to database
            con = super.getConnection();

            String query = "SELECT l._litter_key AS litterKey, l.litterID, "
                    + " m.matingID, s.strainName, l.birthDate, l.status, l.totalBorn, "
                    + " l.numFemale, l.numMale, l.birthDate FROM Litter l, Mating m, "
                    + " Strain s WHERE l._mating_key = "
                    + " m._mating_key AND m._strain_key = s._strain_key "
                    + whereClause
                    + " ORDER BY l.birthDate DESC, m.matingID "
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println(query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "LitterSearch");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "LitterSearch");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }
    
    public ITBaseEntityTable getLitterSearchResults(LitterSearchDTO dto)
            throws SQLException {
        // Result Set used
        ResultSet rs = null;
        ITBaseEntityTable table = new ITBaseEntityTable();

        String whereClause = "";
        String matingClause = "";
        String strainClause = "";
        String birthDateClause = "";

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            String statusFilter = (dto.getStatus() == null || dto.getStatus().equals("") ? "" : dto.getStatus());
            if (!statusFilter.trim().isEmpty()) {
                statusFilter = "l.status LIKE '%" + dto.getStatus() + "%'";    // Filter: surround with "* ... *".
                whereClause += " AND " + statusFilter;
            }

            String litterIDFilter = (dto.getLitterID() == null || dto.getLitterID().equals("") ? "" : dto.getLitterID().toString());
            if (!litterIDFilter.trim().isEmpty()) {
                litterIDFilter = "l.litterID like '%" + dto.getLitterID() + "%'";    // Filter: surround with "* ... *".
                whereClause += " AND " + litterIDFilter;
            }

            // if mating is selected
            String matingID = (dto.getMatingID() == null || dto.getMatingID().equals("") ? "" : dto.getMatingID().toString());
                        
            if (!matingID.trim().isEmpty()) {       
                String matingFilter = (dto.getMatingFilter() == null || dto.getMatingFilter().equals("") ? "" : dto.getMatingFilter());
                
                if (matingFilter.equalsIgnoreCase("equals")) {
                    matingClause = "m.matingID = " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("greater than")) {
                    matingClause = "m.matingID > " + matingID;
                }
                else if (matingFilter.equalsIgnoreCase("less than")) {
                    matingClause = "m.matingID < " + matingID;
                }
                else {
                    matingClause = "m.matingID = " + matingID;
                }
                                
                whereClause += " AND " + matingClause;
            }

            // if strain is selected
            if (dto.getStrain() != null && dto.getStrain().getStrainKey() > 0) {
                // if all billable status is selected, then check for the status
                // that are billable
                strainClause = "l._litter_key IN (SELECT l._litter_key FROM "
                        + "Mating m, Litter l WHERE l._mating_key = m._mating_key "
                        + "AND m._strain_key = '" + dto.getStrain().getStrainKey() + "')";
                whereClause += " AND " + strainClause;
            }

            // if birthDate is selected                        
            if (dto.getBirthDate() != null) {
                // set the actionDate
                Date date = dto.getBirthDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                String currentDt = formatter.format(date);
                // if all billable status is selected, then check for the status
                // that are billable
                birthDateClause = " l.birthDate >= '" + currentDt + "' ";
                whereClause += " AND " + birthDateClause;
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
            
            if (!ownerClause.equals("")) {
                whereClause += " AND m.owner IN (" + ownerClause + ") ";
            }
            
            int startRow = 0;
            int endRow = 500;

            // Open connect to database
            con = super.getConnection();

            String query = "SELECT l.* FROM Litter l, Mating m, Strain s "
                    + "WHERE l._mating_key = m._mating_key AND "
                    + "m._strain_key = s._strain_key "
                    + whereClause
                    + " LIMIT " + startRow + ", " + endRow;

            System.out.println(query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveLitterToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table;
    }
    
    private ITBaseEntityTable moveLitterToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface>   parentList    = new 
                ArrayList<ITBaseEntityInterface> () ;
        LitterEntity                  litterEntity  = null;
        Integer                       litterKey     = -1;
        SystemDao                     dao           = new SystemDao();

        while (resultSet.next())
        {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (litterKey != resultSet.getInt("_litter_key"))
            {
                litterKey = resultSet.getInt("_litter_key");

                // New litter
                litterEntity = new LitterEntity();

                litterEntity.setLitterKey(litterKey);
                
                // set the FK's  
                
                // set strain key
                if (resultSet.getObject("_mating_key") != null && 
                        resultSet.getInt("_mating_key") > 0) {
                   litterEntity.setMatingKey((MatingEntity)dao.getSystemFacadeLocal()
                           .baseFindByKey(new MatingEntity(), resultSet.getInt("_mating_key")));
                }
                
                // set the values to entity
                litterEntity.setBirthDate(resultSet.getDate("birthDate"));
                litterEntity.setComment(resultSet.getString("comment"));

                litterEntity.setLitterID(resultSet.getString("litterID"));
                litterEntity.setNumFemale(resultSet.getShort("numFemale"));
                
                litterEntity.setNumMale(resultSet.getShort("numMale"));
                litterEntity.setStatus(resultSet.getString("status"));                

                litterEntity.setTagDate(resultSet.getDate("tagDate"));
                litterEntity.setTotalBorn(resultSet.getShort("totalBorn"));
                litterEntity.setWeanDate(resultSet.getDate("weanDate"));
                litterEntity.setVersion(resultSet.getInt("version"));            
            }
            if (litterEntity != null) parentList.add(litterEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }    
    
    public ArrayList<Integer> getMatingKeys(String id) throws SQLException {
        ResultSet rs = null;
        ArrayList<Integer> keys = new ArrayList<Integer>();

        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.mating_key FROM Mating m WHERE "
                    + id;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();
            
            while (rs.next()) {
                keys.add(rs.getInt("_mating_key"));
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return keys;
    }

    public String getLitterGeneration(int key) throws SQLException {
        ResultSet rs = null;
        String generation = "";
        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m.generation FROM "
                        + "Mating m, Litter l WHERE l._mating_key = m._mating_key "
                        + "AND l.litterID = " + key;
                        //+ "AND l._litter_key = " + key;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();
            
            while (rs.next()) {
                generation = rs.getString("generation");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return generation;
    }
    
    public int getLitterStrain(int lkey) throws SQLException {
        ResultSet rs = null;
        int key = 0;
        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m._strain_key FROM "
                        + "Mating m, Litter l WHERE l._mating_key = m._mating_key "
                        + "AND l.litterID = " + lkey;

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();
            
            while (rs.next()) {
                key = rs.getInt("_strain_key");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return key;
    }
    
    public Result getLitterInfo(String lkey) throws SQLException {
        ResultSet rs = null;
        Result resultData = null;
        
        // Used in SubQueries
        PreparedStatement preparedStatement = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT l._litter_key, l.litterID, l.birthDate, m.generation, "
                    + "m._strain_key FROM Mating m, Litter l WHERE l._mating_key "
                    + "= m._mating_key AND l.litterID = '" + lkey + "'";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();
            
            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "LitterSearch");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "LitterSearch");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::CageSummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    /**
     * queryDebug : Conditional print to Log.
     * If qDebug is false print nothing, which is the default setting.
     * If qDebug is true print out to jBoss log.
     *
     */
    private void queryDebug(String method, String message) {
        if (qDebug) {
            getLogger().logDebug(formatLogMessage("LitterSearchDAO."
                    + method, message));
        }
    }
}