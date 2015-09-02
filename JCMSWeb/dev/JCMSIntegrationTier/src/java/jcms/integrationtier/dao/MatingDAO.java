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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.exception.FindEntityException;
import jcms.integrationtier.dao.cvMatingUnitTypeDAO;
import java.util.SortedMap;

/**
 *
 * @author rkavitha
 */
public class MatingDAO extends MySQLDAO {
    
    private static final long serialVersionUID = 01002L;
    private Connection con = null;
    private ITBaseFacade it = new ITBaseFacade();
    SystemDao dao = new SystemDao();
    
    public int saveMating(MatingEntity mating) {
        int updateCount = 0;
        int dam2Key = 0;
        String matingDt = null;
        String retiredDt = null;
                
        try {
            // Open connect to database
            con = super.getConnection();
            
            if (mating.getDam2Key() != null) {
                dam2Key = mating.getDam2Key();
            }
            
            // if birthDate is selected                        
            if (mating.getMatingDate() != null) {
                // set the actionDate
                Date date = mating.getMatingDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                matingDt = formatter.format(date);
            }
            
            // if exitDate is selected                        
            if (mating.getRetiredDate() != null) {
                // set the actionDate
                Date date = mating.getRetiredDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                retiredDt = formatter.format(date);
            }
            
            String query = "UPDATE Mating SET _dam1_key=?, _dam2_key=?, _sire_key=?, "
                    + "_strain_key=?, suggestedPenID=?, weanTime=?, matingDate=?, "
                    + "retiredDate=?, generation=?, owner=?, weanNote=?, "
                    + "needsTyping=?, comment=?, version=?, _crossStatus_key=? "
                    + "WHERE _mating_key=?";
            
            System.out.println("Update Mating query " + query);
            
            PreparedStatement updateString = con.prepareStatement(query);
            
            updateString.setInt(1, mating.getDam1Key());
            if (dam2Key > 0) {
                updateString.setInt(2, dam2Key);
            }
            else {
                updateString.setString(2, null);
            }
            updateString.setInt(3, mating.getSireKey().getMouseKey());
            
            updateString.setInt(4, mating.getStrainKey().getStrainKey());
            updateString.setInt(5, mating.getSuggestedPenID());
            
            updateString.setBoolean(6, mating.getWeanTime());
            updateString.setString(7, matingDt);
            updateString.setString(8, retiredDt);
            updateString.setString(9, mating.getGeneration());
            updateString.setString(10, mating.getOwner());
            updateString.setString(11, mating.getWeanNote());
            updateString.setBoolean(12, mating.getNeedsTyping());
            updateString.setString(13, mating.getComment());            
            
            updateString.setInt(14, mating.getVersion());
            
            if (mating.getCrossStatuskey().getCrossStatuskey() != null) {
                updateString.setInt(15, mating.getCrossStatuskey().getCrossStatuskey());
            }
            else {
                updateString.setString(15, null);
            }
            
            updateString.setInt(15, mating.getCrossStatuskey().getCrossStatuskey());
            updateString.setInt(16, mating.getMatingKey());
            
            updateCount = updateString.executeUpdate();
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MatingDAO::NullPointerException:: "
                    + "saveMating  \n" + npe);
        } catch (SQLException ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingDAO::SQLException:: "
                    + "saveMating  \n" + ex);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MatingDAO::SQLException:: "
                    + "saveMating  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return updateCount;
    }    
    
    public void insertNaturalMatingUnitLink(MatingEntity me) throws Exception {
        cvMatingUnitTypeDAO mutDAO = new cvMatingUnitTypeDAO();
        
        String dam1Query = "INSERT INTO MatingUnitLink  (_mating_key, _mouse_key, _sample_key, _matingUnitType_key) "
                + "VALUES("
                + me.getMatingKey().toString() + ", "
                + new Integer(me.getDam1Key()).toString() + ", "
                + "NULL, "
                + new Integer(mutDAO.getMatingUnitTypeKey("Dam")).toString() + ");";
        String dam2Query = "";
        if(me.getDam2Key() != null && me.getDam2Key().intValue() != 0){
            dam2Query = "INSERT INTO MatingUnitLink  (_mating_key, _mouse_key, _sample_key, _matingUnitType_key) "
                    + "VALUES("
                    + me.getMatingKey().toString() + ", "
                    + me.getDam2Key().toString() + ", "
                    + "NULL, "
                    + new Integer(mutDAO.getMatingUnitTypeKey("Dam")).toString() + ");";
        }
        String sireQuery = "INSERT INTO MatingUnitLink  (_mating_key, _mouse_key, _sample_key, _matingUnitType_key) "
                + "VALUES("
                + me.getMatingKey().toString() + ", "
                + me.getSireKey().getMouseKey().toString() + ", "
                + "NULL, "
                + new Integer(mutDAO.getMatingUnitTypeKey("Sire")).toString() + ");";
        this.executeJCMSUpdate(dam1Query);
        if(!dam2Query.equals("")){
            this.executeJCMSUpdate(dam2Query);
        }
        this.executeJCMSUpdate(sireQuery);
    }
    
    public int getNextMatingID(){
        String query = "SELECT MAX(matingID) AS max FROM Mating;";
        SortedMap[] max = this.executeJCMSQuery(query).getRows();
        String maxID = myGet("max", max[0]);
        if(maxID == null || maxID.equalsIgnoreCase("null") || maxID.equals("")){
            return 1;
        }
        else{
            return Integer.parseInt(maxID) + 1;
        }
    }
}