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
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.exception.FindEntityException;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author cnh
 */
public class StrainDAO extends MySQLDAO {
    // Recommended by lint if your class is serialiable
    private static final long serialVersionUID = 1002L;
    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;
    private UserEntity user = null;
    
    public StrainDAO(UserEntity userEntity) {
        this.setUser(userEntity);
    }
    
    /**
     * check if the set up variable is true /false
     * @return 
     */
    public boolean checkIfStrainFirst() {
        boolean flag = true;
        
        //CHANGED by bas July 2014
        //to use the user's preferences to determine if the strain should be displayed first.
        //First we need to know the current user's key
        cvPreferencesDAO DAO = new cvPreferencesDAO();
        
        if (this.getUser() != null) {
            String defaultValue = DAO.GetDefaultValue(this.getUser().getUserkey().toString(),"global", "JCMS_STRAINNAME_FIRST");
            if (defaultValue != null) {
                if (!defaultValue.trim().equals("")) {
                    if (defaultValue.equalsIgnoreCase("true")) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            }
        }
        
        return flag;
    }

     

    public ITBaseEntityTable getMouseStrainsByOwner(ArrayList<OwnerEntity> ownerLst) throws SQLException {
        ITBaseEntityTable table = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        String ownLst = "";

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                ownLst += "'" + ownerLst.get(i).getOwner() + "'";
                else
                ownLst += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            System.out.println("ownLst " + ownLst);
            
            String orderByClause = "";
            
            // if the set up variable is true, then display starin name first 
            // and then Jr#
            if (this.checkIfStrainFirst()) {
                orderByClause = " ORDER BY s.strainName";
            }
            else {
                orderByClause = " ORDER BY s.jrNum";
            }
            
            String query = "SELECT s.* FROM Strain s " +
                    "WHERE EXISTS( " +
                    "SELECT 1 FROM Mouse m " +
                    "WHERE m.owner in (" + ownLst + ") " +
                    "AND m._strain_key = s._strain_key) " + orderByClause;

            System.out.println("mouse Strains query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveStrainsToEntity(rs);

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getStrainsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getStrainsByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table; //resultData;        
    }
    
    public ITBaseEntityTable getApprovedLitterStrains(int damKey, int sireKey) 
            throws SQLException {
        ITBaseEntityTable table = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;
        try {
            // Open connect to database
            con = super.getConnection();
            
            String orderByClause = "";
            
            // if the set up variable is true, then display starin name first 
            // and then Jr#
            if (this.checkIfStrainFirst()) {
                orderByClause = " ORDER BY s.strainName";
            }
            else {
                orderByClause = " ORDER BY s.jrNum";
            }
            
            String query = "SELECT s.* FROM Strain s WHERE s._strain_key IN "
                    + "(SELECT ApprovedStrainRegistry._litterStrain_key FROM "
                    + "ApprovedStrainRegistry WHERE "
                    + "ApprovedStrainRegistry._sireStrain_key = " + sireKey 
                    + " AND ApprovedStrainRegistry._damStrain_key =  " + damKey
                    + " AND (ApprovedStrainRegistry.active = -1 OR "
                    + " ApprovedStrainRegistry.active = 1) ) "
                    + " OR ( s._strain_key = " + sireKey + " AND s._strain_key"
                    + " = " + damKey + ")" + orderByClause;            

            System.out.println("Strains query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveStrainsToEntity(rs);

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();

        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getStrainsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getStrainsByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table; //resultData;
    }
    
    public ITBaseEntityTable getAllStrains() 
            throws SQLException {
        ITBaseEntityTable table = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;
        try {
            // Open connect to database
            con = super.getConnection();
            
            String query = " ";
            
            // if the set up variable is true, then display starin name first 
            // and then Jr#
            if (this.checkIfStrainFirst()) {
                query = "SELECT s.* FROM Strain s ORDER BY s.strainName";
            }
            else {
                query = "SELECT s.* FROM Strain s ORDER BY s.jrNum";
            }

            System.out.println("Strains query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveStrainsToEntity(rs);

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();

        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getStrainsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getStrainsByOwner  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return table; //resultData;
    }
    
    public ITBaseEntityTable getActiveStrains() throws SQLException {
        ITBaseEntityTable table = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;
        try {
            // Open connect to database
            con = super.getConnection();
            
            String query = "";
            
            // if the set up variable is true, then display starin name first 
            // and then Jr#
            if (this.checkIfStrainFirst()) {
                query = "SELECT s.* FROM Strain s WHERE (s.isActive = -1 " +
                    "OR s.isActive = 1) ORDER BY s.strainName";
            }
            else {
                query = "SELECT s.* FROM Strain s WHERE (s.isActive = -1 " +
                    "OR s.isActive = 1) ORDER BY s.jrNum";
            }

            System.out.println("Strains query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveStrainsToEntity(rs);

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();

        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getStrainsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getStrainsByOwner  \n" + ex);
        }
        finally {
            // Close database connection from MySQLDAO
            super.closeConnection(con);
        }
        return table; //resultData;
    }

    public ITBaseEntityTable getMatingStrainsByOwner(ArrayList<OwnerEntity> 
            ownerLst) throws SQLException {
        ITBaseEntityTable table = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

       String ownLst = "";

        try {
            // Open connect to database
            con = super.getConnection();

            for(int i=0; i<ownerLst.size(); ++i) {
                // if last item, then no comma
                if (i == ownerLst.size()-1)
                ownLst += "'" + ownerLst.get(i).getOwner() + "'";
                else
                ownLst += "'" + ownerLst.get(i).getOwner() + "'" + " ,";
            }

            System.out.println("ownLst " + ownLst);
            
            String orderByClause = "";
            
            // if the set up variable is true, then display starin name first 
            // and then Jr#
            if (this.checkIfStrainFirst()) {
                orderByClause = " ORDER BY s.strainName";
            }
            else {
                orderByClause = " ORDER BY s.jrNum";
            }

            String query = "SELECT s.* FROM Strain s " +
                    "WHERE EXISTS( " +
                    "SELECT 1 FROM Mating m " +
                    "WHERE m.owner in (" + ownLst + ") " +
                    "AND m._strain_key = s._strain_key) " + orderByClause;

            System.out.println("mating Strains query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            table = moveStrainsToEntity(rs);
            System.out.println("moveStrainsToEntity done");

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();

        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::NullPointerException:: "
                    + "getStrainsByOwner  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::FilteredQueriesDAO::SQLException:: "
                    + "getStrainsByOwner  \n" + ex);
        }
        finally {
            // Close database connection from MySQLDAO
            super.closeConnection(con);
        }
        return table; //resultData;
    }

    private ITBaseEntityTable moveStrainsToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface>   parentList  = new ArrayList<ITBaseEntityInterface> () ;
        StrainEntity                  strainEntity  = null;
        Integer                     strainKey     = -1;

        while (resultSet.next())
        {
            // Since the result set can have more than one LineName per Line
            // The results are sorted by LineKey and StockNumber
            // Only create a new LineEntity if the primary key has changed.
            if (strainKey != resultSet.getInt("_strain_key"))
            {
                strainKey = resultSet.getInt("_strain_key");

                // New strain
                strainEntity = new StrainEntity();

                strainEntity.setStrainKey(strainKey);

                strainEntity.setCardColor(resultSet.getString("cardColor"));
                strainEntity.setComment(resultSet.getString("comment"));

                strainEntity.setFeMaxGen(resultSet.getString("feMaxGen"));
                strainEntity.setFeNumEmbryos(resultSet.getShort("feNumEmbryos"));
                strainEntity.setFoMaxGen(resultSet.getString("foMaxGen"));
                strainEntity.setFoNumFemales(resultSet.getShort("foNumFemales"));
                strainEntity.setFormalName(resultSet.getString("formalName"));
                strainEntity.setFsMaxGen(resultSet.getString("fsMaxGen"));
                strainEntity.setFsNumMales(resultSet.getShort("fsNumMales"));

                strainEntity.setJrNum(resultSet.getInt("jrNum"));
                strainEntity.setLastTag(resultSet.getString("lastTag"));

                strainEntity.setLineViabilityRedMaxAgeFemales(resultSet.getInt("lineViabilityRedMaxAgeFemales"));
                strainEntity.setLineViabilityRedMaxAgeMales(resultSet.getInt("lineViabilityRedMaxAgeMales"));
                strainEntity.setLineViabilityRedMinNumFemales(resultSet.getInt("lineViabilityRedMinNumFemales"));
                strainEntity.setLineViabilityRedMinNumMales(resultSet.getInt("lineViabilityRedMinNumMales"));
                strainEntity.setLineViabilityYellowMaxAgeFemales(resultSet.getInt("lineViabilityYellowMaxAgeFemales"));
                strainEntity.setLineViabilityYellowMaxAgeMales(resultSet.getInt("lineViabilityYellowMaxAgeMales"));
                strainEntity.setLineViabilityYellowMinNumFemales(resultSet.getInt("lineViabilityYellowMinNumFemales"));
                strainEntity.setLineViabilityYellowMinNumMales(resultSet.getInt("lineViabilityYellowMinNumMales"));

                strainEntity.setNickname(resultSet.getString("nickname"));
                strainEntity.setSection(resultSet.getString("section_"));
                strainEntity.setStrainName(resultSet.getString("strainName"));
                strainEntity.setStrainStatus(resultSet.getString("strainStatus"));
                strainEntity.setStrainType(resultSet.getString("strainType"));
                strainEntity.setTagMax(resultSet.getString("tagMax"));
                strainEntity.setTagMin(resultSet.getString("tagMin"));
                strainEntity.setVersion(resultSet.getInt("version"));
            }
            if (strainEntity != null) parentList.add(strainEntity);
        }

        // Add the last line to list.
        // While loop pops out before last line is added.

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.add(parentList);

        return table;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
