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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseFacade;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.dto.MouseDTO;
import jcms.integrationtier.exception.FindEntityException;

/**
 *
 * @author rkavitha
 */
public class MouseDAO extends MySQLDAO {
    
    private static final long serialVersionUID = 0211002L;
    private Connection con = null;
    private ITBaseFacade it = new ITBaseFacade();
    SystemDao dao = new SystemDao();

    private String mouseSelect = "SELECT m._mouse_key, m._litter_key, m._strain_key, m._pen_key, m.ID, m.newTag, "
            + " DATE_FORMAT(m.birthDate,'%m/%e/%Y %T') as birthDate, DATE_FORMAT(m.exitDate,'%m/%e/%Y %T') as exitDate, "
            + " m.cod, m.codNotes, m.generation, m.sex, m.lifeStatus, m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
            + " m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, m.version, s.strainName "
            + "\n FROM Mouse m "
            + "\n INNER JOIN Strain s ON m._strain_key = s._strain_key ";
    private String mouseOrderBy = " ORDER BY m.ID DESC";
    
    public int saveMouse(MouseEntity mouse) {
        int updateCount = 0;
        int litterKey = 0;
        String birthDt = null;
        String exitDt = null;
                
        try {
            // Open connect to database
            con = super.getConnection();
            
            if (mouse.getLitterKey() != null) {
                litterKey = mouse.getLitterKey().getLitterKey();
            }
            
            // if birthDate is selected                        
            if (mouse.getBirthDate() != null) {
                // set the actionDate
                Date date = mouse.getBirthDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                birthDt = formatter.format(date);
            }
            
            // if exitDate is selected                        
            if (mouse.getExitDate() != null) {
                // set the actionDate
                Date date = mouse.getExitDate();
                // (2) create our date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");//("yyyy-MM-dd-hh.mm.ss");
                // (3) create a new String using the date format we want
                exitDt = formatter.format(date);
            }
            
            System.out.println("BirthDate " + birthDt);
            System.out.println("ExitDate " + exitDt);
                       
            String query = "Update Mouse set _litter_key = ?, _strain_key = ?, "
                    + "_pen_key = ?, ID=?, newTag=?, birthDate=?, generation=?, "
                    + "sex=?, lifeStatus=?, breedingStatus=?, coatColor=?, diet=?, "
                    + "owner=?, origin=?, comment=?, exitDate=?, cod=?, protocol=?, "
                    + "codNotes=?, sampleVialId=?, sampleVialTagPosition=?, version=? "
                    + "where _mouse_key=?";
            
            PreparedStatement updateString = con.prepareStatement(query);
            
            if (litterKey > 0) {
                updateString.setInt(1, litterKey);
            }
            else {
                updateString.setString(1, null);
            }
            updateString.setInt(2, mouse.getStrainKey().getStrainKey());
            updateString.setInt(3, mouse.getPenKey().getContainerKey());
            updateString.setString(4, mouse.getId());
            updateString.setString(5, mouse.getNewTag());
            updateString.setString(6, birthDt);
            updateString.setString(7, mouse.getGeneration());
            updateString.setString(8, mouse.getSex());
            updateString.setString(9, mouse.getLifeStatus());
            updateString.setString(10, mouse.getBreedingStatus());
            updateString.setString(11, mouse.getCoatColor());
            updateString.setString(12, mouse.getDiet());
            updateString.setString(13, mouse.getOwner());
            updateString.setString(14, mouse.getOrigin());
            updateString.setString(15, mouse.getComment());
            updateString.setString(16, exitDt);
            updateString.setString(17, mouse.getCod());
            updateString.setString(18, mouse.getProtocol());
            updateString.setString(19, mouse.getCodNotes());
            updateString.setString(20, mouse.getSampleVialID());
            updateString.setString(21, mouse.getSampleVialTagPosition());
            updateString.setInt(22, mouse.getVersion());
            updateString.setInt(23, mouse.getMouseKey());
            
            updateCount = updateString.executeUpdate();

            System.out.println("Update Mouse query " + query);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseDAO::NullPointerException:: "
                    + "saveMouse  \n" + npe);
        } catch (SQLException ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "saveMouse  \n" + ex);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "saveMouse  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return updateCount;
    }    
    
    public MouseEntity findMouse(int key) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MouseEntity mEntity = new MouseEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m._mouse_key, s.strainName, l.litterID, "
                    + "c.containerID, m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
                    + "m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, "
                    + "m._litter_key, m._pen_key, m._strain_key, m.version FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + "LEFT JOIN Litter l ON m._litter_key = l._litter_key "
                    + "LEFT JOIN Container c ON m._pen_key = c._container_key "
                    + "WHERE m._mouse_key=" + key;
            
            //System.out.println("find Mouse query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            mEntity = moveMiceToEntity(rs);
            
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
        return mEntity;
    }
    
    public MouseEntity validDam(String id) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MouseEntity mEntity = new MouseEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m._mouse_key, s.strainName, l.litterID, "
                    + "c.containerID, m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
                    + "m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, "
                    + "m._litter_key, m._pen_key, m._strain_key, m.version FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + "LEFT JOIN Litter l ON m._litter_key = l._litter_key "
                    + "LEFT JOIN Container c ON m._pen_key = c._container_key "
                    + "WHERE m.ID='" + id + "' AND m.sex = 'F'";
            

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            mEntity = moveMiceToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseDAO::NullPointerException:: "
                    + "validDam  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "validDam  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return mEntity;
    }
    
    public MouseEntity validSire(String id) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MouseEntity mEntity = new MouseEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m._mouse_key, s.strainName, l.litterID, "
                    + "c.containerID, m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
                    + "m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, "
                    + "m._litter_key, m._pen_key, m._strain_key, m.version FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + "LEFT JOIN Litter l ON m._litter_key = l._litter_key "
                    + "LEFT JOIN Container c ON m._pen_key = c._container_key "
                    + "WHERE m.ID='" + id + "' AND m.sex = 'M'";
            

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            mEntity = moveMiceToEntity(rs);
            
        } catch (NullPointerException npe) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, npe);
            throw new FindEntityException("IntegrationTier::MouseDAO::NullPointerException:: "
                    + "validSire  \n" + npe);
        } catch (Exception ex) {
            Logger.getLogger(MouseSearchDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new FindEntityException("IntegrationTier::MouseDAO::SQLException:: "
                    + "validSire  \n" + ex);
        } finally {
            // Close databaes connection from MySQLDAO
            super.closeConnection(con);
        }
        return mEntity;
    }
    
    public MouseEntity findMouseByID(String id) throws SQLException {
        // Used in SubQueries
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MouseEntity mEntity = new MouseEntity();

        try {            
            // Open connect to database
            con = super.getConnection();

            String query = "SELECT m._mouse_key, s.strainName, l.litterID, "
                    + "c.containerID, m.ID, m.newTag, m.birthDate, m.exitDate, "
                    + "m.cod, m.codNotes, m.generation,m.sex, m.lifeStatus, "
                    + "m.breedingStatus, m.coatColor, m.diet, m.owner, m.origin, "
                    + "m.protocol, m.comment, m.sampleVialID, m.sampleVialTagPosition, "
                    + "m._litter_key, m._pen_key, m._strain_key, m.version FROM Mouse m "
                    + "LEFT JOIN Strain s ON m._strain_key = s._strain_key "
                    + "LEFT JOIN Litter l ON m._litter_key = l._litter_key "
                    + "LEFT JOIN Container c ON m._pen_key = c._container_key "
                    + "WHERE m.ID=" + id;
            
            System.out.println("find Mouse query " + query);

            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            mEntity = moveMiceToEntity(rs);
            
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
        return mEntity;
    }
    
    private MouseEntity moveMiceToEntity(ResultSet resultSet)
            throws SQLException {
        List<ITBaseEntityInterface>   parentList  = new ArrayList<ITBaseEntityInterface> () ;
        MouseEntity                  mouseEntity  = null;
        Integer                     mouseKey     = -1;

        if (resultSet.next())
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
        }
        return mouseEntity;
    }

    public Integer getNumberOfMicePerPen(Integer penKey) {
        String cmd = " SELECT _mouse_key FROM Mouse "
                   + " WHERE _pen_key = " + penKey ; 
        Result result = executeJCMSQuery(cmd);
        
        return result.getRowCount();
    }

    public ArrayList<MouseDTO> getMiceInCage(String containerKey) {
        ArrayList<MouseDTO> dtoList = new ArrayList<MouseDTO> ();
        MouseDTO dto = null;
        String whereClause = "\n WHERE m._pen_key = "+ containerKey +" ";
        String orderClause = "\n ORDER BY m.ID";
        String cmd = mouseSelect + whereClause + orderClause;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createMouseDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }    
    
    public ArrayList<MouseDTO> getLivingMiceInCage(String containerKey) {
        ArrayList<MouseDTO> dtoList = new ArrayList<MouseDTO> ();
        MouseDTO dto = null;
        String whereClause = "\n WHERE m._pen_key = "+ containerKey +" "
                           + "\n AND m.lifeStatus = 'A' ";
        String orderClause = "\n ORDER BY m.ID";
        String cmd = mouseSelect + whereClause + orderClause;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createMouseDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<MouseDTO> getNonExitedMiceInCage(String containerKey) {
        ArrayList<MouseDTO> dtoList = new ArrayList<MouseDTO> ();
        MouseDTO dto = null;
        String whereClause =  "\n INNER JOIN LifeStatus ON m.lifeStatus = LifeStatus.lifeStatus "
                            + "\n WHERE m._pen_key = '"+ containerKey +"' "
                           //+ "\n AND m.lifeStatus = 'A' ";
                           + "\n AND LifeStatus.exitStatus = '0' ";
        String orderClause = "\n ORDER BY m.ID";
        String cmd = mouseSelect + whereClause + orderClause;
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createMouseDTO(row);
            dtoList.add(dto);
        }
        return dtoList;
    }
    
    public ArrayList<MouseDTO> getLivingMiceInCageByOwner(String containerKey, ArrayList<String> owners) {
        ArrayList<MouseDTO> dtoList = new ArrayList<MouseDTO> ();
        MouseDTO dto = null;
        String ownerString = "";
        for(String owner : owners){
            if(!owners.get(owners.size() - 1).equals(owner)){
                ownerString = ownerString + "owner = '" + owner + "' OR ";
            }
            else{
                ownerString = ownerString + "owner = '" + owner + "'";
            }
        }
        String whereClause = "\n WHERE m._pen_key = "+ containerKey +" "
                           + "\n AND m.lifeStatus = 'A' "
                           + "\n AND (" + ownerString 
                            + ")";
        String orderClause = "\n ORDER BY m.ID";
        String cmd = mouseSelect + whereClause + orderClause;
        System.out.println(cmd);
        Result result = executeJCMSQuery(cmd);
        for (SortedMap row : result.getRows()) {
            dto = this.createMouseDTO(row);
            dtoList.add(dto);
        }
        setMouseGenotypes(dtoList);
        return dtoList;
    }
    
    private void setMouseGenotypes(ArrayList<MouseDTO> mice){
        String genotypeQuery = "SELECT CONCAT(labSymbol, ' ', COALESCE(allele1, ''), ' ', COALESCE(allele2, '') ) AS genotype "
                                + "FROM Mouse "
                                + "JOIN Genotype "
                                + "ON Mouse._mouse_key = Genotype._mouse_key "
                                + "JOIN Gene "
                                + "ON Genotype._gene_key = Gene._gene_key "
                                + "WHERE Mouse._mouse_key = ";
        for(MouseDTO mouse : mice){
            String mouseGenotypeQuery = genotypeQuery + mouse.getMouse_key();
            System.out.println(mouseGenotypeQuery);
            SortedMap[] genotypes = executeJCMSQuery(mouseGenotypeQuery).getRows();
            String genotypeString = "";
            boolean first = true;
            for(SortedMap genotype : genotypes){
                if(first){
                    genotypeString = myGet("genotype", genotype);
                    first = false;
                } 
                else{
                    genotypeString = genotypeString + ", " + myGet("genotype", genotype);                    
                }
            }
            mouse.setGenotype(genotypeString);
        }
    }
    
    private MouseDTO createMouseDTO(SortedMap row) {
        MouseDTO dto = new MouseDTO();
        String strDate = "";
        Date dtDate = null;
        
        strDate = myGet("birthDate", row).toString();
        try { 
            dtDate = (strDate != null ? this.convertStringToDate(strDate) : null);
            dto.setBirthDate(dtDate);
        } catch (Exception e) { }

        strDate = myGet("exitDate", row).toString();
        try { 
            dtDate = (strDate != null ? this.convertStringToDate(strDate) : null);
            dto.setExitDate(dtDate);
        } catch (Exception e) { }
        
        dto.setMouse_key(myGet("_mouse_key", row));
        dto.setBreedingStatus(myGet("breedingStatus", row));
        dto.setCoatColor(myGet("coatColor", row));
        dto.setCod(myGet("cod", row));
        dto.setCodNotes(myGet("codNotes", row));
        dto.setComment(myGet("comment", row));
        dto.setDiet(myGet("diet", row));
        dto.setGeneration(myGet("generation", row));
        dto.setID(myGet("ID", row));
        dto.setLifeStatus(myGet("lifeStatus", row));
        dto.setLitter_key(myGet("_litter_key", row));
        dto.setMouse_key(myGet("_mouse_key", row));
        dto.setNewTag(myGet("newTag", row));
        dto.setOrigin(myGet("origin", row));
        dto.setOwner(myGet("owner", row));
        dto.setPen_key(myGet("_pen_key", row));
        dto.setProtocol(myGet("protocol", row));
        dto.setSampleVialID(myGet("sampleVialID", row));
        dto.setSampleVialTagPosition(myGet("sampleVialTagPosition", row));
        dto.setSex(myGet("sex", row));
        dto.setStrainName(myGet("strainName", row));
        dto.setStrain_key(myGet("_strain_key", row));
        dto.setVersion(myGet("version", row));

        return dto;
    }
    public Integer getMaxQueryDefinitionKey() {
        Integer key = null;
        Result result = executeJCMSWebQuery("SELECT MAX(_QueryDefinition_key) AS MaxKey FROM QueryDefinition");
        if (result != null && result.getRowCount() == 1)
        for (SortedMap row : result.getRows()) {
            key = new Integer(myGet("MaxKey", row));
            break;
        }
        return key;
    }
    
}
