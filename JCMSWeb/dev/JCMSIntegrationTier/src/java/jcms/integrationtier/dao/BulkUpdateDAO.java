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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;

/**
 *
 * @author mkamato
 */
public class BulkUpdateDAO extends MySQLDAO {
    
    public ArrayList<String> getCageUnits(String strainKey, ArrayList<String> owners){
        ArrayList<String> cages = new ArrayList<String>();
        String query = "SELECT DISTINCT(containerID) "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "WHERE Mouse._strain_key = " + strainKey + " "
                + this.buildOwnerFilter("Mouse", owners);        
        SortedMap[] units = this.executeJCMSQuery(query).getRows();
        for(SortedMap map : units){
            cages.add(myGet("containerID", map));
        }
        return cages;
    }
    
    public ArrayList<String> getCageNameUnits(String strainKey, ArrayList<String> owners){
        ArrayList<String> cages = new ArrayList<String>();
        String query = "SELECT DISTINCT(containerID), containerName "
                + "FROM Mouse "
                + "JOIN Container "
                + "ON Mouse._pen_key = Container._container_key "
                + "WHERE containerName != '' "
                + this.buildOwnerFilter("Mouse", owners)  
                + " AND Mouse._strain_key = " + strainKey;        
        SortedMap[] units = this.executeJCMSQuery(query).getRows();
        for(SortedMap map : units){
            String cageName = myGet("containerName", map);
            if(!cageName.equals("")){
                cages.add(myGet("containerName", map));
            }
        }
        return cages;
    }
    
    public ArrayList<String> getLitterUnits(String strainKey, ArrayList<String> owners){
        ArrayList<String> litters = new ArrayList<String>();
        String query = "SELECT DISTINCT(litterID) "
                + "FROM Mouse "
                + "JOIN Litter "
                + "ON Mouse._litter_key = Litter._litter_key "
                + "WHERE Mouse._strain_key = " + strainKey + " "
                + this.buildOwnerFilter("Mouse", owners) + ";";
        SortedMap[] units = this.executeJCMSQuery(query).getRows();
        for(SortedMap map : units){
            litters.add(myGet("litterID", map));
        }
        return litters;
    }
    
    public ArrayList<String> getIDUnits(String strainKey, ArrayList<String> owners){
        ArrayList<String> IDs = new ArrayList<String>();
        String query = "SELECT ID FROM Mouse "
                + "WHERE Mouse._strain_key = " + strainKey + " "
                + this.buildOwnerFilter("Mouse", owners) + ";";
        SortedMap[] units = this.executeJCMSQuery(query).getRows();
        for(SortedMap map : units){
            IDs.add(myGet("ID", map));
        }
        return IDs;
    }
    
    public String getContainerKeyFromContainerID(String containerID){
        String query = "SELECT _container_key FROM Container WHERE containerID = " + numberParser(containerID);
        SortedMap[] ids = this.executeJCMSQuery(query).getRows();
        if(ids.length > 0){
            return myGet("_container_key", ids[0]);
        }
        return "";
    }
    
    public String getLitterKeyFromLitterID(String litterID){
        String query = "SELECT _litter_key FROM Litter WHERE litterID = " + varcharParser(litterID);
        SortedMap[] ids = this.executeJCMSQuery(query).getRows();
        if(ids.length > 0){
            return myGet("_litter_key", ids[0]);
        }
        return "";
    }
    
    public void updateMiceAccordingToLitter(ArrayList<String> litterKeys, LifeStatusEntity lifeStatus) throws Exception{
        for(String key : litterKeys){
            String query = "";
            if(lifeStatus.getExitStatus()){
                query = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET "
                    + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus()) + ", "
                    + "exitDate = now()"
                    + " WHERE exitStatus = 0 AND _litter_key = " + numberParser(key);
            }
            else{
                query = "UPDATE Mouse "
                        + "JOIN LifeStatus "
                        + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                        + "SET "
                        + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus())
                        + " WHERE exitStatus = 0 AND _litter_key = " + numberParser(key);
            }
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceAccordingToCage(ArrayList<String> cageKeys, LifeStatusEntity lifeStatus) throws Exception{
        for(String key : cageKeys){
            String query = "";
            if(lifeStatus.getExitStatus()){
                query = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET "
                    + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus()) + ", "
                    + "exitDate = now()"
                    + " WHERE exitStatus = 0 AND _pen_key = " + numberParser(key);
            }
            else{
                query = "UPDATE Mouse "
                        + "JOIN LifeStatus "
                        + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                        + "SET "
                        + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus())
                        + " WHERE exitStatus = 0 AND _pen_key = " + numberParser(key);
            }
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceAccordingToIDs(ArrayList<String> IDs, LifeStatusEntity lifeStatus) throws Exception{
        for(String ID : IDs){
            String query = "";
            if(lifeStatus.getExitStatus()){
                query = "UPDATE Mouse "
                    + "SET "
                    + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus()) + ", "
                    + "exitDate = now()"
                    + " WHERE ID = " + varcharParser(ID);
            }
            else{
                query = "UPDATE Mouse "
                        + "SET "
                        + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus())
                        + " WHERE ID = " + varcharParser(ID);
            }
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceAccordingToMouseKeys(ArrayList<String> keys, LifeStatusEntity lifeStatus) throws Exception{
        for(String key : keys){
            String query = "";
            //Made a change to set the exit date to NULL if the status is changed to an non-exit life status (June 2014)
            if(lifeStatus.getExitStatus()){
                query = "UPDATE Mouse "
                    + "SET "
                    + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus()) + ", "
                    + "exitDate = now()"
                    + " WHERE _mouse_key = " + key;
            }
            else{
                query = "UPDATE Mouse "
                        + "SET "
                        + "Mouse.lifeStatus = " + varcharParser(lifeStatus.getLifeStatus()) + ", "
                        + "exitDate = NULL"
                        + " WHERE _mouse_key = " + key;
            }
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceDietAccordingToLitter(ArrayList<String> litterKeys, String diet) throws Exception{
        for(String key : litterKeys){
            String query = "UPDATE Mouse "
                        + "JOIN LifeStatus "
                        + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                        + "SET "
                        + "diet = " + varcharParser(diet)
                        + " WHERE exitStatus = 0 AND _litter_key = " + numberParser(key);
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceDietAccordingToCage(ArrayList<String> cageKeys, String diet) throws Exception{
        for(String key : cageKeys){
            String query = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET "
                    + "diet = " + varcharParser(diet)
                    + " WHERE exitStatus = 0 AND _pen_key = " + numberParser(key);
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceDietAccordingToIDs(ArrayList<String> IDs, String diet) throws Exception{
        for(String ID : IDs){
            String query = "UPDATE Mouse "
                    + "SET "
                    + "diet = " + varcharParser(diet)
                    + " WHERE ID = " + varcharParser(ID);
            this.executeJCMSUpdate(query);
        }
    }
    
    public void updateMiceDietAccordingToMouseKeys(ArrayList<String> keys, String diet) throws Exception{
        for(String key : keys){
            String query = "UPDATE Mouse "
                    + "SET "
                    + "diet = " + varcharParser(diet)
                    + " WHERE _mouse_key = " + key;
            this.executeJCMSUpdate(query);
        }
    }
    
        public void addMicePhenotypeAccordingToLitter(ArrayList<String> litterKeys, String phenotype) throws Exception{
        for(String key : litterKeys){
            String query = "UPDATE Mouse "
                        + "JOIN LifeStatus "
                        + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                        + "SET "
                        + "phenotype = " + varcharParser(phenotype)
                        + " WHERE exitStatus = 0 AND _litter_key = " + numberParser(key);
            this.executeJCMSUpdate(query);
        }
    }
    
    public void addMicePhenotypeAccordingToCage(ArrayList<String> cageKeys, String phenotype) throws Exception{
        for(String key : cageKeys){
            String query = "UPDATE Mouse "
                    + "JOIN LifeStatus "
                    + "ON LifeStatus.lifeStatus = Mouse.lifeStatus "
                    + "SET "
                    + "phenotype = " + varcharParser(phenotype)
                    + " WHERE exitStatus = 0 AND _pen_key = " + numberParser(key);
            this.executeJCMSUpdate(query);
        }
    }
    
    public void addMicePhenotypeAccordingToKeys(String Key, String phenotype) throws Exception{
            
            String query = "INSERT INTO PhenotypeMouseLink (_phenotype_key, _mouse_key) "
                    + "VALUES ("
                    + numberParser(getPhenotypeKeyByPhenotypeTerm(phenotype)) + ", "
                    + numberParser(Key) + ");";      
                    
            this.executeJCMSUpdate(query);
        }
    
    
    public void addMicePhenotypeAccordingToIDs(ArrayList<String> IDs, String phenotype) throws Exception{
        String mouseKey = null;
        MouseEntity aMouse = new MouseEntity();
        for(String ID : IDs){
            
            mouseKey = aMouse.getMouseKey().toString();
            String query = "INSERT INTO PhenotypeMouseLink (_phenotype_key, _mouse_key) "
                    + "VALUES ("
                    + numberParser(getPhenotypeKeyByPhenotypeTerm(phenotype)) + ", "
                    + numberParser(mouseKey) + ");";      
                    
            this.executeJCMSUpdate(query);
        }
    }
    
    public ArrayList<String> getMiceByLitterID(String litterID, ArrayList<String> owners){
        ArrayList<String> mice = new  ArrayList<String>();
        String query = "SELECT _mouse_key "
                + "FROM Mouse "
                + "JOIN Litter "
                + "ON Mouse._litter_key = Litter._litter_key "
                + "WHERE Litter.litterID = '" + litterID + "' "
                + this.buildOwnerFilter("Mouse", owners) + ";";
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : results){
            mice.add(myGet("_mouse_key",row));
        }
        return mice;
    }

    private String getPhenotypeKeyByPhenotypeTerm(String phenotype) {
        String phenotypeKey = null;
        String query = "SELECT _phenotype_key FROM cv_Phenotype WHERE phenotype = '" + phenotype + "';";
        SortedMap[] result = this.executeJCMSQuery(query).getRows();
        if(result.length != 0){
            phenotypeKey = myGet("_phenotype_key", result[0]);
        }
        
        return phenotypeKey;
    }
    
    /**
     * Takes a list of mice and using a prepared statement updates all the information
     * for those mice to match what's in the entity object.
     * 
     * @param  mice - a list of mice to be updated
     * @return N/A
     */
    public void updateMouse(List<MouseEntity> mice) throws Exception {
        //initialize update string
        PreparedStatement updateMouse;
        String updateString = "UPDATE Mouse "
                + "SET "
                + "_mouse_key = ?, "
                + "_litter_key = ?, "
                + "_strain_key = ?, "
                + "_pen_key = ?, "
                + "ID = ?, "
                + "newTag = ?, "
                + "birthDate = ?, "
                + "generation = ?, "
                + "sex = ?, "
                + "lifeStatus = ?, "
                + "breedingStatus = ?, "
                + "coatColor = ?, "
                + "diet = ?, "
                + "owner = ?, "
                + "origin = ?, "
                + "comment = ?, "
                + "exitDate = ?, "
                + "cod = ?, "
                + "protocol = ?, "
                + "codNotes = ?, "
                + "sampleVialId = ?, "
                + "sampleVialTagPosition = ?, "
                + "version = ? "
                + "WHERE _mouse_key = ?;";
        //establish connection and initialize prepared statement
        Connection con = this.getConnection();
        updateMouse = con.prepareStatement(updateString);        
        
        for(MouseEntity mouse : mice){
            //put data in prepared statement
            updateMouse.setInt(1, mouse.getMouseKey());
            if(mouse.getLitterKey() == null){
                updateMouse.setNull(2, java.sql.Types.INTEGER);
            }
            else{
                updateMouse.setInt(2, mouse.getLitterKey().getLitterKey());                
            }
            updateMouse.setInt(3, mouse.getStrainKey().getStrainKey());
            updateMouse.setInt(4, mouse.getPenKey().getContainerKey());
            updateMouse.setString(5, mouse.getId());
            if(mouse.getNewTag() == null){
                updateMouse.setNull(6, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(6, mouse.getNewTag());               
            }
            updateMouse.setDate(7, new java.sql.Date(mouse.getBirthDate().getTime()));
            updateMouse.setString(8, mouse.getGeneration());
            updateMouse.setString(9, mouse.getSex());
            updateMouse.setString(10, mouse.getLifeStatus());
            updateMouse.setString(11, mouse.getBreedingStatus());
            //coat color
            if(mouse.getCoatColor() == null){
                updateMouse.setNull(12, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(12, mouse.getCoatColor());               
            }
            
            //new diet
            if(mouse.getDiet() == null){
                updateMouse.setNull(13, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(13, mouse.getDiet());               
            }
            updateMouse.setString(14, mouse.getOwner());
            updateMouse.setString(15, mouse.getOrigin());
            
            //comment
            if(mouse.getComment() == null){
                updateMouse.setNull(16, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(16, mouse.getComment());            
            }
            
            //exit date
            if(mouse.getExitDate() == null){
                updateMouse.setNull(17, java.sql.Types.DATE);
            }
            else{
                updateMouse.setDate(17, new java.sql.Date(mouse.getExitDate().getTime()));   
            }
            
            //cod
            if(mouse.getCod() == null){
                updateMouse.setNull(18, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(18, mouse.getCod());             
            }
            
            //protocol
            if(mouse.getProtocol() == null){
                updateMouse.setNull(19, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(19, mouse.getProtocol());            
            }
            
            //cod notes
            if(mouse.getCodNotes() == null){
                updateMouse.setNull(20, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(20, mouse.getCodNotes());              
            }
            
            //sample vial id
            if(mouse.getSampleVialID() == null){
                updateMouse.setNull(21, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(21, mouse.getSampleVialID());              
            }
            
            //sample vial tag position
            if(mouse.getSampleVialTagPosition() == null){
                updateMouse.setNull(22, java.sql.Types.VARCHAR);
            }
            else{
                updateMouse.setString(22, mouse.getSampleVialTagPosition());              
            }
            
            updateMouse.setInt(23, mouse.getVersion());
            updateMouse.setInt(24, mouse.getMouseKey());
            
            //update mouse in DB
            this.executePreparedStatementUpdate(updateMouse);
        }
        //close connection
        this.closeConnection(con);
    }
    
    public Boolean mouseHasPhenotype(String mouseKey, String phenotypeName){
        Boolean yesNo = true;
        String phenotypeKey = "0";
        String query = "SELECT _phenotype_key FROM cv_Phenotype WHERE phenotype = '" + phenotypeName + "';";
        SortedMap[] result = this.executeJCMSQuery(query).getRows();
        if(result.length != 0){
            phenotypeKey = myGet("_phenotype_key", result[0]);
        }
        query = "SELECT * FROM PhenotypeMouseLink WHERE _phenotype_key = " + phenotypeKey + " AND _mouse_key = " + mouseKey + ";";
        SortedMap[] result2 = this.executeJCMSQuery(query).getRows();
        if(result2.length == 0){
            yesNo = false;
        }
        return yesNo;
    }
}
