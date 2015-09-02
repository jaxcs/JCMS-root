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

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class ColonySummaryDAO extends MySQLDAO   {

    // Recommended by lint if your class is serialiable
    private static final long serialVersionUID = 1002L;

    private Connection con = null;
    // Debug Flag, set to true to spit logmessage out to console.
    private boolean qDebug = false;
    
    String inList = "";

    public ColonySummaryDAO() {        
    }

    public Result getLiveMouseQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used 
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String liveMiceQuery = "SELECT Mouse.owner, Strain.strainName, "
                    + "Mouse.lifeStatus FROM Mouse "
                    + "LEFT JOIN Strain ON Mouse._strain_key = Strain._strain_key "
                    + "WHERE (Mouse.owner in (" + inList + ") AND Mouse.lifeStatus='A') "
                    + "ORDER BY Mouse.owner ";

            preparedStatement = con.prepareStatement(liveMiceQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "liveMouseQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }
    
    public Result getLiveMiceDistinctOwners(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;
        inList = "";

        // Result Set used 
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();            
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String query = "SELECT DISTINCT m.owner "
                + "FROM Mouse m "
                + "WHERE m.owner IN (" + inList + ") AND (m.lifeStatus='A') "
                + "ORDER BY m.owner ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "liveMouseQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getLiveMiceStrainsByOwner(String owner) throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT Mouse.owner, Strain.strainName, "
                    + "count(Mouse.ID) AS mice FROM Mouse "
                    + "LEFT JOIN Strain ON Mouse._strain_key = Strain._strain_key "
                    + "WHERE (Mouse.lifeStatus='A') AND Mouse.owner='" + owner + "' "
                    + "GROUP By Mouse.owner, Strain.strainName "
                    + "ORDER BY Mouse.owner ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "liveMouseQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getLiveMiceStrainsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;
        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String query = "SELECT Mouse.owner, Strain.strainName, "
                    + "count(Mouse.ID) AS mice FROM Mouse "
                    + "LEFT JOIN Strain ON Mouse._strain_key = Strain._strain_key "
                    + "WHERE Mouse.owner IN (" + inList + ") AND (Mouse.lifeStatus='A') "
                    + "GROUP By Mouse.owner, Strain.strainName "
                    + "ORDER BY Mouse.owner ";

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "liveMouseQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "selectQuery");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getLiveMouseQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getLiveMouseQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    /**
     * JCMS Web 
     *   Colony Summary Report - Number of Cages by Owner/Workgroup and Room
     * @param ownerLst
     * @return
     * @throws SQLException 
     */
    public Result getActivePensQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String activePensQuery = "SELECT DISTINCT Mouse.owner, Room.roomName "
                + "AS room, Container.ContainerID AS penID, "
                + "cv_ContainerStatus.containerStatus FROM Mouse "
                + "LEFT JOIN Container ON Mouse._pen_key = Container._container_key "
                + "LEFT JOIN ContainerHistory ON Container._containerHistory_key = "
                + "ContainerHistory._containerHistory_key "
                + "LEFT JOIN cv_ContainerStatus ON  ContainerHistory._containerStatus_key "
                + "= cv_ContainerStatus._containerStatus_key "
                + "LEFT JOIN Room on ContainerHistory._room_key = Room._room_key "
                + "WHERE Mouse.owner in (" + inList + ") AND cv_ContainerStatus.containerStatus != 'retired' "
                + "ORDER BY Mouse.owner ";

            preparedStatement = con.prepareStatement(activePensQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActivePensQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActivePens");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePensQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePensQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    /**
     * JCMS Web 
     *   Colony Summary Report - Number of active cages for the Owner/Workgroup
     * @param ownerLst
     * @return
     * @throws SQLException 
     */
    public Result getActivePensForOwners(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String activePensQuery = "SELECT DISTINCT Container._container_key "
                + "FROM Mouse "
                + "LEFT JOIN Container ON Mouse._pen_key = Container._container_key "
                + "LEFT JOIN ContainerHistory ON Container._containerHistory_key = "
                + "ContainerHistory._containerHistory_key "
                + "LEFT JOIN cv_ContainerStatus ON  ContainerHistory._containerStatus_key "
                + "= cv_ContainerStatus._containerStatus_key "
                + "LEFT JOIN Room on ContainerHistory._room_key = Room._room_key "
                + "WHERE Mouse.owner in (" + inList + ") AND cv_ContainerStatus.containerStatus != 'retired' "
                + "ORDER BY Mouse.owner ";

            preparedStatement = con.prepareStatement(activePensQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActivePensQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActivePens");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePensQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePensQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    /**
     * This query is very suspicious as it only returns 11 records compared to 
     * method getActivePensQueryResults() above returning on order of 690 count.
     * @param ownerLst
     * @return
     * @throws SQLException 
     */
    public Result getActivePensByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String activePensQuery = "SELECT Mouse.owner, Room.roomName AS room, "
                + "Container.ContainerID AS penID "
                + "FROM Mouse "
                + "LEFT JOIN Container ON Mouse._pen_key = Container._container_key "
                + "LEFT JOIN ContainerHistory ON Container._containerHistory_key = "
                + "ContainerHistory._containerHistory_key "
                + "LEFT JOIN cv_ContainerStatus ON  ContainerHistory._containerStatus_key "
                + "= cv_ContainerStatus._containerStatus_key "
                + "LEFT JOIN Room on ContainerHistory._room_key = Room._room_key "
                + "WHERE  Mouse.owner in (" + inList + ") AND "
                + "cv_ContainerStatus.containerStatus != 'retired' "
                + "GROUP BY Room.roomName, Mouse.owner "
                + "ORDER BY Mouse.owner ";                  

            preparedStatement = con.prepareStatement(activePensQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActivePensQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActivePens");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePensQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePensQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActiveMatingsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
                //inList.add(ownerLst.get(i).getOwner());
            }

            String activeMatingsQuery = "SELECT Mating._mating_key, "
                    + "Mating.owner FROM Mating WHERE "
                    + "(((Mating.retiredDate) Is Null) AND "
                    + "((Mating.matingDate) Is Not Null) AND "
                    + "Mating.owner in (" + inList + "))";

            preparedStatement = con.prepareStatement(activeMatingsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActiveMatingsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActiveMatings");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActiveMatingsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActiveMatingsQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActiveMatingsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String activeMatingsQuery = "SELECT Mating.owner, "
                    + "count(Mating.matingID) AS Matings "
                    + "FROM Mating "
                    + "WHERE (Mating.owner in (" + inList + ") AND "
                    + "((Mating.retiredDate) Is Null) AND "
                    + "((Mating.matingDate) Is Not Null)) "
                    + "GROUP BY Mating.owner "
                    + "ORDER BY Mating.owner ";

            preparedStatement = con.prepareStatement(activeMatingsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActiveMatingsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActiveMatings");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActiveMatingsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActiveMatingsQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActiveStrainsQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

//            String activeStrainsQuery = "SELECT DISTINCT Mouse.owner, "
            String activeStrainsQuery = "SELECT DISTINCT "
                    + "Strain.strainName FROM Mouse "
                    + "LEFT JOIN Strain ON Mouse._strain_key = Strain._strain_key "
                    + "WHERE Mouse.owner in (" + inList + ") AND Mouse.lifeStatus='A'";

            preparedStatement = con.prepareStatement(activeStrainsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActiveStrainsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActiveStrains");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActiveStrainsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActiveStrainsQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }

    public Result getActivePlansQueryResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }
            
            String activePlansQuery = "SELECT ExpPlan.planID, ExpPlan.expPlanName, "
                    + "ExpPlan.owner FROM ExpPlan "
                    + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' ";

            preparedStatement = con.prepareStatement(activePlansQuery);
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
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActivePlansAndTestsByOwner(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }
            
            String activePlansQuery = "SELECT ExpPlan.owner, ExpPlan.expPlanName, "
                    + "ExpTest.testID, ExpTest.testName, ExpTest.numMice, "
                    + "ExpTest.testStatus FROM ExpPlan "
                    + "LEFT JOIN ExpTest ON ExpPlan._expPlan_key=ExpTest._expPlan_key "
                    + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' "
                    + "ORDER BY ExpPlan.owner ";

            preparedStatement = con.prepareStatement(activePlansQuery);
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
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActivePlansByOwner(ArrayList<OwnerEntity> ownerLst) throws 
            SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }         
                        
            String activePlansQuery = "SELECT ExpPlan.owner, ExpPlan.expPlanName, "
                    + "count(ExpPlan.planID) AS plans FROM ExpPlan "
                    + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' "
                    + "GROUP BY ExpPlan.owner "
                    + "Order BY ExpPlan.owner";
                   
            preparedStatement = con.prepareStatement(activePlansQuery);
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
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActivePlansAndTestResults(ArrayList<OwnerEntity> ownerLst) 
            throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }            
            
            String activePlansQuery = "SELECT ExpPlan.owner, ExpPlan.expPlanName, "
                + "count(ExpTest.testID) AS tests FROM ExpPlan "
                + "LEFT JOIN ExpTest ON ExpPlan._expPlan_key=ExpTest._expPlan_key "
                + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' AND ExpTest.testStatus='active' "
                + "GROUP BY ExpPlan.owner, ExpPlan.expPlanName "
                + "ORDER BY ExpPlan.owner ";

            preparedStatement = con.prepareStatement(activePlansQuery);
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
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getPenStatus() throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            String query = "SELECT containerStatus FROM cv_ContainerStatus c "
                    + "WHERE containerStatus != 'retired'";

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
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActivePlansQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActivePlansQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
    }

    public Result getActiveTestsQueryResults() throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();

            String activeTestsQuery = "SELECT ExpTest.testID, ExpTest.testName "
                    + "FROM ExpTest "
                    + "WHERE ExpTest.testStatus='active'";
                   
            preparedStatement = con.prepareStatement(activeTestsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "ActiveTestsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "ActiveTests");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getActiveTestsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getActiveTestsQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }

     public Result getMouseTestsQueryResults(ArrayList<OwnerEntity> ownerLst) 
             throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String mouseTestsQuery = "SELECT ExpPlan.owner, ExpPlan.expPlanName, "
                    + "ExpTest.testName "
                    + "FROM ExpPlan "
                    + "LEFT JOIN ExpPlanMouseMap ON ExpPlan._expPlan_key="
                    + "ExpPlanMouseMap._expPlan_key LEFT JOIN Mouse ON "
                    + "ExpPlanMouseMap._mouse_key=Mouse._mouse_key "
                    + "LEFT JOIN ExpTestPlanMap ON ExpPlanMouseMap."
                    + "_expPlanMouseMap_key=ExpTestPlanMap._expPlanMouseMap_key "
                    + "RIGHT JOIN ExpTest ON ExpTest._expTest_key=ExpTestPlanMap._expTest_key "
                    + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' "
                    + "AND ExpTest.testStatus='active' "
                    + "ORDER BY ExpPlan.owner ";

            preparedStatement = con.prepareStatement(mouseTestsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "MouseTestsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "MouseTests");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getMouseTestsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getMouseTestsQueryResults  \n" + ex);
        }
        finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return resultData;
     }

     public Result getMouseScheduledForTests(ArrayList<OwnerEntity> ownerLst) 
             throws SQLException {
        Result resultData = null;

        // Result Set used
        ResultSet rs = null;

        // Used in SubQueries
        PreparedStatement preparedStatement  = null;

        try {
            // Open connect to database
            con = super.getConnection();
            
            inList = "";
            
            for(int i=0; i<ownerLst.size(); ++i) {
                if (i == ownerLst.size()-1)
                inList += "'" + ownerLst.get(i).getOwner() + "'";
                else
                inList += "'" + ownerLst.get(i).getOwner() + "'" + ", ";
            }

            String mouseTestsQuery = "SELECT ExpPlan.owner, ExpPlan.expPlanName, "
                    + "ExpTest.testID, ExpTest.testName, count(ExpPlanMouseMap._mouse_key) "
                    + "as mice FROM ExpPlan "
                    + "LEFT JOIN ExpPlanMouseMap ON ExpPlan._expPlan_key="
                    + "ExpPlanMouseMap._expPlan_key LEFT JOIN Mouse ON "
                    + "ExpPlanMouseMap._mouse_key=Mouse._mouse_key "
                    + "LEFT JOIN ExpTestPlanMap ON ExpPlanMouseMap."
                    + "_expPlanMouseMap_key=ExpTestPlanMap._expPlanMouseMap_key "
                    + "RIGHT JOIN ExpTest ON ExpTest._expTest_key=ExpTestPlanMap._expTest_key "
                    + "WHERE ExpPlan.owner in (" + inList + ") AND ExpPlan.expStatus='active' "
                    + "AND ExpTest.testStatus='active' "
                    + "GROUP BY ExpPlan.expPlanName, ExpTest.testName "
                    + "ORDER BY ExpPlan.owner ";

            preparedStatement = con.prepareStatement(mouseTestsQuery);
            rs = preparedStatement.executeQuery();

            rs.beforeFirst();

            // Very Very Very Important.  Doesn't convert to Result with out the
            // following line ->  myResult.beforeFirst();
            if (rs != null) {
                queryDebug("Converting result set into result object", "MouseTestsQuery");
                resultData = ResultSupport.toResult(rs);
                queryDebug("row cnt in IT " + resultData.getRowCount(), "MouseTests");
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::NullPointerException:: "
                    + "getMouseTestsQueryResults  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(ColonySummaryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::ColonySummaryDAO::SQLException:: "
                    + "getMouseTestsQueryResults  \n" + ex);
        }
        finally {
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
            getLogger().logDebug(formatLogMessage("ColonySummaryDAO." + 
                    method, message));
        }
    }
}
