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
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.dto.AdminApprovedStrainDTO;
import jcms.integrationtier.exception.FindEntityException;

public class LitterDAO extends MySQLDAO {
    private static final long serialVersionUID = 065002L;
    private Connection con = null;
    
    public LitterDAO() {
        
    }
    
    public ArrayList<AdminApprovedStrainDTO> getApprovedStrains() throws SQLException {
        con = super.getConnection();
        ArrayList<AdminApprovedStrainDTO> dtoList = new ArrayList<AdminApprovedStrainDTO> ();
        AdminApprovedStrainDTO dto = null;
        String cmd = 
            "SELECT asr._approvedStrain_key, asr._damStrain_key, asr._sireStrain_key, "
            + "     asr._litterStrain_key, asr.active, asr._owner_key, asr.version, "
            + "     strain1.strainName AS damStrain, strain2.strainName AS sireStrain, strain3.strainName AS litterStrain "
            + " FROM ApprovedStrainRegistry asr "
            + " INNER JOIN Strain AS strain1 ON asr._damStrain_key = strain1._strain_key "
            + " INNER JOIN Strain AS strain2 ON asr._sireStrain_key = strain2._strain_key "
            + " INNER JOIN Strain AS strain3 ON asr._litterStrain_key = strain3._strain_key "
            + " ORDER BY strain1.strainName ";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(cmd);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    dto = createApprovedStrainDTO(rs);
                    dtoList.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }

        return dtoList;
    }
    
    private AdminApprovedStrainDTO createApprovedStrainDTO(ResultSet rs) throws SQLException {
        AdminApprovedStrainDTO dto = new AdminApprovedStrainDTO();
        dto.setApprovedStrainKey(rs.getString("_approvedStrain_key"));
        dto.setDamStrainKey(rs.getString("_damStrain_key"));
        dto.setSireStrainKey(rs.getString("_sireStrain_key"));
        dto.setLitterStrainKey(rs.getString("_litterStrain_key"));
        dto.setOwnerKey(rs.getString("_owner_key"));
        dto.setIsActive(rs.getBoolean("active"));
        dto.setVersion(rs.getString("version"));
        dto.setDamStrain(rs.getString("damStrain"));     
        dto.setSireStrain(rs.getString("sireStrain"));
        dto.setLitterStrain(rs.getString("litterStrain"));
        
        return dto;
    }
    
    private AdminApprovedStrainDTO createApprovedStrainDTO(SortedMap row) {
        AdminApprovedStrainDTO dto = new AdminApprovedStrainDTO();
        dto.setApprovedStrainKey(myGet("_approvedStrain_key", row));
        dto.setDamStrainKey(myGet("_damStrain_key", row));
        dto.setSireStrainKey(myGet("_sireStrain_key", row));
        dto.setLitterStrainKey(myGet("_litterStrain_key", row));
        dto.setOwnerKey(myGet("_owner_key", row));
        dto.setIsActive(Boolean.parseBoolean(myGet("active", row)));
        dto.setVersion(myGet("version", row));
        dto.setDamStrain(myGet("damStrain", row));     
        dto.setSireStrain(myGet("sireStrain", row));
        dto.setLitterStrain(myGet("litterStrain", row));
        
        return dto;
    }

    public Integer saveApprovedStrainVocabulary(AdminApprovedStrainDTO dto) throws SQLException {
        Integer cnt = 0;
        if (dto.isInsert()) {
            cnt = this.insertApprovedStrainVocabulary(dto);
        } else {
            cnt = this.updateApprovedStrainVocabulary(dto);
        }
        return cnt;
    }
    
    private Integer insertApprovedStrainVocabulary(AdminApprovedStrainDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "INSERT INTO ApprovedStrainRegistry " 
            + "\n (_damStrain_key, _sireStrain_key, _litterStrain_key, version, active) "
            + "\n VALUES ("+ dto.getDamStrainKey()
                        + ", "+ dto.getSireStrainKey()
                        + ", "+ dto.getLitterStrainKey()
                        + ", "+ version.toString()
                        + ", "+ (dto.getIsActive() ? -1 : 0) +" )" ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }

    private Integer updateApprovedStrainVocabulary(AdminApprovedStrainDTO dto) throws SQLException {
        Integer version = (Integer.parseInt(dto.getVersion())) + 1;
        String cmd = "UPDATE ApprovedStrainRegistry SET " 
            + " _damStrain_key = " + dto.getDamStrainKey()
            + ", _sireStrain_key = " + dto.getSireStrainKey()
            + ", _litterStrain_key = " + dto.getLitterStrainKey()
            + ", active = " + (dto.getIsActive() ? -1 : 0)
            + ", version = " + version.toString()
            +"\n WHERE _approvedStrain_key = "+ dto.getApprovedStrainKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    public Integer deleteApprovedStrainVocabulary(AdminApprovedStrainDTO dto) throws SQLException {
        String cmd = "DELETE FROM ApprovedStrainRegistry WHERE _approvedStrain_key = "+ dto.getApprovedStrainKey() ;
        Integer count = executeJCMSUpdate(cmd);
        return count;
    }
    
    
    
    
    /**
     * 
     * ENTITY IMPLEMENTATION - DEPRECATED ARCHITECTURE
     * 
     */
    
    public int saveLitter(LitterEntity litter) {
        int updateCount = 0;
        
        try {
            // Open connect to database
            con = super.getConnection();
            String birthDt = null;
            String weanDt = null;
            String tagDt = null;
            
            // if birthDate is selected                        
            if (litter.getBirthDate() != null) {
                // set the actionDate
                Date date = litter.getBirthDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                birthDt = formatter.format(date);
            }
            
            // if exitDate is selected                        
            if (litter.getWeanDate() != null) {
                // set the actionDate
                Date date = litter.getWeanDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                weanDt = formatter.format(date);
            }
            
            // if tagDate is selected                        
            if (litter.getTagDate() != null) {
                // set the actionDate
                Date date = litter.getTagDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                tagDt = formatter.format(date);
            }
            
            String query = "Update Litter set _mating_key = ?, "
                    + "litterID = ?, totalBorn=?, birthDate=?, numFemale=?, numMale=?, " 
                    + "weanDate=?, tagDate=?, status=?, comment=?, version=?, "
                    + "numberBornDead=?, numberCulledAtWean=?, numberMissingAtWean=? "
                    + "where _litter_key=?";
            
            PreparedStatement updateString = con.prepareStatement(query);
            
            //set values in the statement...
            updateString.setInt(1, litter.getMatingKey().getMatingKey());
            updateString.setString(2, litter.getLitterID());
            updateString.setInt(3, litter.getTotalBorn() );
            updateString.setString(4, birthDt);
            
            if(litter.getNumFemale() == null){
                updateString.setNull(5, java.sql.Types.INTEGER);
            }
            else{
                updateString.setInt(5, litter.getNumFemale());
            }
            
            if(litter.getNumMale() == null){
                updateString.setNull(6, java.sql.Types.INTEGER);
            }
            else{
                updateString.setInt(6, litter.getNumMale());
            }
            
            if(weanDt == null){
                updateString.setNull(7, java.sql.Types.VARCHAR);
            }
            else{
                updateString.setString(7, weanDt);
            }
            
            if(tagDt == null){
                updateString.setNull(8, java.sql.Types.VARCHAR);
            }
            else{
                updateString.setString(8, tagDt);
            }
            
            updateString.setString(9, litter.getStatus());
            
            if(litter.getComment() == null){
                updateString.setNull(10, java.sql.Types.VARCHAR);
            }
            else{
                updateString.setString(10, litter.getComment());
            }
            
            updateString.setInt(11, litter.getVersion());
            
            if(litter.getNumberBornDead() == null){
                updateString.setNull(12, java.sql.Types.INTEGER);
            }
            else{
                updateString.setInt(12, litter.getNumberBornDead());
            }
            
            if(litter.getNumberCulledAtWean() == null){
                updateString.setNull(13, java.sql.Types.INTEGER);
            }
            else{
                updateString.setInt(13, litter.getNumberCulledAtWean());
            }
            
            if(litter.getNumberMissingAtWean() == null){
                updateString.setNull(14, java.sql.Types.INTEGER);
            }
            else{
                updateString.setInt(14, litter.getNumberMissingAtWean());
            }
            
            updateString.setInt(15, litter.getLitterKey());
            
            updateCount = updateString.executeUpdate();

            System.out.println("Update Litter query " + query);

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
        return updateCount;
    }        
    
    public String getMaxIdNumber(String query){
        SortedMap[] max = this.executeJCMSQuery(query).getRows();
        if(max.length > 0){
            String maxNumber = myGet("max", max[0]);
            if(maxNumber.equals("")){
                return "0";
            }
            else{
                return maxNumber;
            }
        }
        else{
            return "0";
        }
    }
}