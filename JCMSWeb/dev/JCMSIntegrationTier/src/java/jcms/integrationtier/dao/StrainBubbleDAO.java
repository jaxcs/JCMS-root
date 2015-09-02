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

import jcms.integrationtier.dto.StrainBubbleDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.FilteredQueriesDAO;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.jcmsWeb.UserEntity;
/**
 *
 * @author mkamato
 */
public class StrainBubbleDAO extends MySQLDAO {
    SystemDao sysDAO = new SystemDao();
    
    public StrainBubbleDAO() {
        
    }
    
    public ArrayList<StrainBubbleDTO> findBubblesLite(ArrayList<OwnerEntity> owners, UserEntity user) throws Exception {
        StrainDAO strainDAO = new StrainDAO(user);
        String ownersStmt = this.buildOwnerFilterFromEntities("Mouse", owners);
        
        ArrayList<StrainBubbleDTO> bubbles = new ArrayList<StrainBubbleDTO>();
        String defaultHighAge = "365";
        String defaultMediumAge = "240";
        String query = "SELECT total.*, "
                + " redF.redFemales, redM.redMales,  "
                + " yellowF.yellowFemales, yellowM.yellowMales,  "
                + " greenF.greenFemales, greenM.greenMales   "
                + " FROM ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS totalMice  "
                + "         FROM Mouse  "
                + "         JOIN Strain  "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A' "
                +           ownersStmt
                + "         GROUP BY Mouse._strain_key "
                + " ) AS total "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS redFemales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'F'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultHighAge + " DAY) < now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS redF "
                + " ON total.strainName = redF.strainName "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS redMales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'M'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultHighAge + " DAY) < now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS redM "
                + " ON total.strainName = redM.strainName "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS yellowFemales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'F'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultMediumAge + " DAY) < now()  "
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultHighAge + " DAY) > now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS yellowF "
                + " ON total.strainName = yellowF.strainName "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS yellowMales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'M'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultMediumAge + " DAY) < now()  "
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultHighAge + " DAY) > now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS yellowM "
                + " ON total.strainName = yellowM.strainName "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS greenFemales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'F'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultMediumAge + " DAY) > now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS greenF "
                + " ON total.strainName = greenF.strainName "
                + " LEFT JOIN  "
                + " ( "
                + "         SELECT  Strain.strainName, COUNT(_mouse_key) AS greenMales "
                + "         FROM Mouse JOIN Strain "
                + "         ON Mouse._strain_key = Strain._strain_key  "
                + "         WHERE Mouse.lifeStatus = 'A'  "
                + "         AND Mouse.sex = 'M'  "
                +           ownersStmt
                + "         AND DATE_ADD(Mouse.birthDate, INTERVAL " + defaultMediumAge + " DAY) > now()  "
                + "         GROUP BY Mouse._strain_key "
                + " ) AS greenM "
                + " ON total.strainName = greenM.strainName"
                + ";";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();

        List<ITBaseEntityInterface> strains =  strainDAO.getMouseStrainsByOwner(owners).getList();

        for(SortedMap row : rows){
            StrainBubbleDTO bubble = new StrainBubbleDTO();
            bubble.setNumberGreenFemales(myGetInt("greenFemales", row));
            bubble.setNumberGreenMales(myGetInt("greenMales", row));
            bubble.setNumberYellowFemales(myGetInt("yellowFemales", row));
            bubble.setNumberYellowMales(myGetInt("yellowMales", row));
            bubble.setNumberRedFemales(myGetInt("redFemales", row));
            bubble.setNumberRedMales(myGetInt("redMales", row));
            bubble.setNumberLiving(myGetInt("totalMice", row));
            for(ITBaseEntityInterface temp : strains){
                StrainEntity strain = (StrainEntity) temp;
                if(strain.getStrainName().equals(myGet("strainName", row))){
                    if(strain.getLineViabilityYellowMinNumFemales().equals(0)){
                        strain.setLineViabilityYellowMinNumFemales(12);
                    }
                    if(strain.getLineViabilityYellowMinNumMales().equals(0)){
                        strain.setLineViabilityYellowMinNumMales(6);
                    }
                    if(strain.getLineViabilityRedMinNumFemales().equals(0)){
                        strain.setLineViabilityRedMinNumFemales(6);
                    }
                    if(strain.getLineViabilityRedMinNumMales().equals(0)){
                        strain.setLineViabilityRedMinNumMales(3);
                    }
                    bubble.setStrain(strain);
                }                
            }
            bubbles.add(bubble);
        }        
        return bubbles;
    }
    
    public ArrayList<StrainBubbleDTO> findBubbles(ArrayList<String> wgs, ArrayList<String> strainNames){
        //first get all active strains that have a living mouse in the database for that user's WGs
        
        String strainQuery = "SELECT DISTINCT(Strain._strain_key) "
                            + "FROM Strain "
                            + "JOIN Mouse ON Mouse._strain_key = Strain._strain_key "
                            + "WHERE Mouse.lifeStatus = 'A' ";
        
        ArrayList<StrainBubbleDTO> bubbles = new ArrayList<StrainBubbleDTO>();
        String mouseOwnerWhere = " AND (";
        String matingOwnerWhere = " AND (";
        //build strain portion of where clause
        for(String wg : wgs){
            if(wg.equals(wgs.get(wgs.size() - 1))){
                mouseOwnerWhere = mouseOwnerWhere + "Mouse.owner = '" + wg + "'";
                matingOwnerWhere = matingOwnerWhere + "Mating.owner = '" + wg + "'";
            }
            else{
                mouseOwnerWhere = mouseOwnerWhere + "Mouse.owner = '" + wg + "' OR ";
                matingOwnerWhere = matingOwnerWhere + "Mating.owner = '" + wg + "' OR ";
            }
        }
        mouseOwnerWhere = mouseOwnerWhere+ ") ";
        matingOwnerWhere = matingOwnerWhere + ") ";
        strainQuery = strainQuery + mouseOwnerWhere;
        if(!strainNames.isEmpty()){
            String strainWhere = "AND (";
            for(String strainName : strainNames){
                if(strainName.equals(strainNames.get(strainNames.size() - 1))){
                    strainWhere = strainWhere + "Strain.strainName = '" + strainName + "'";
                }
                else{
                    strainWhere = strainWhere + "Strain.strainName = '" + strainName + "' OR ";
                }
            }
            strainWhere = strainWhere + ") ";
            strainQuery = strainQuery + strainWhere;
        }
        SortedMap[] strains = this.executeJCMSQuery(strainQuery).getRows();
        for(SortedMap strainKey : strains){
            StrainBubbleDTO dto = new StrainBubbleDTO();
            //Below ways was too slow, do it the old fashioned way (w/ query)
            //StrainEntity strain = (StrainEntity) sysDAO.getSystemFacadeLocal().baseFindByKey(new StrainEntity(), new Integer(this.myGet("_strain_key", strainKey)).intValue());
            String strainDetailsQuery = "SELECT * FROM Strain WHERE _strain_key = " + myGet("_strain_key", strainKey);
            SortedMap ssm = this.executeJCMSQuery(strainDetailsQuery).getRows()[0];
            //set up strain
            StrainEntity strain = new StrainEntity();
            strain.setStrainKey(new Integer(myGet("_strain_key", ssm)));
            strain.setStrainName(myGet("strainName", ssm));
            strain.setNickname(myGet("nickname", ssm));
            strain.setFormalName(myGet("formalName", ssm));
            if(myGet("isActive", ssm).equals("true")){
                strain.setIsActive(new Short("-1"));
            }else{
                strain.setIsActive(new Short("0"));
            }
            strain.setStrainStatus(myGet("strainStatus", ssm));
            strain.setTagMin(myGet("tagMin", ssm));
            strain.setTagMax(myGet("tagMax", ssm));
            strain.setLastTag(myGet("lastTag", ssm));
            String jrNum = myGet("jrNum", ssm);
            if(jrNum.equals("")){
                strain.setJrNum(new Integer(0));
            }
            else{
                strain.setJrNum(new Integer(jrNum));
            }
            strain.setFeNumEmbryos(Short.parseShort(myGet("feNumEmbryos", ssm)));
            strain.setFeMaxGen(myGet("feMaxGen", ssm));
            strain.setFsNumMales(Short.parseShort(myGet("fsNumMales", ssm)));
            strain.setFsMaxGen(myGet("fsMaxGen", ssm));
            strain.setFoNumFemales(Short.parseShort(myGet("foNumFemales", ssm)));
            strain.setFoMaxGen(myGet("foMaxGen", ssm));
            strain.setCardColor(myGet("cardColor", ssm));
            strain.setStrainType(myGet("strainType", ssm));
            strain.setComment(myGet("comment", ssm));
            if(myGet("lineViabilityYellowMinNumMales", ssm).equals("")){
                strain.setLineViabilityYellowMinNumMales(null);
            }
            else{
                strain.setLineViabilityYellowMinNumMales(new Integer(myGet("lineViabilityYellowMinNumMales", ssm)));
            }
            if(myGet("lineViabilityRedMinNumMales", ssm).equals("")){
                strain.setLineViabilityRedMinNumMales(null);
            }
            else{
                strain.setLineViabilityRedMinNumMales(new Integer(myGet("lineViabilityRedMinNumMales", ssm)));
            }
            if(myGet("lineViabilityYellowMinNumFemales", ssm).equals("")){
                strain.setLineViabilityYellowMinNumFemales(null);
            }
            else{
                strain.setLineViabilityYellowMinNumFemales(new Integer(myGet("lineViabilityYellowMinNumFemales", ssm)));
            }
            if(myGet("lineViabilityRedMinNumFemales", ssm).equals("")){
                strain.setLineViabilityRedMinNumFemales(null);
            }
            else{
                strain.setLineViabilityRedMinNumFemales(new Integer(myGet("lineViabilityRedMinNumFemales", ssm)));
            }
            if(myGet("lineViabilityYellowMaxAgeFemales", ssm).equals("")){
                strain.setLineViabilityYellowMaxAgeFemales(null);
            }
            else{
                strain.setLineViabilityYellowMaxAgeFemales(new Integer(myGet("lineViabilityYellowMaxAgeFemales", ssm)));
            }
            if(myGet("lineViabilityRedMaxAgeFemales", ssm).equals("")){
                strain.setLineViabilityRedMaxAgeFemales(null);
            }
            else{
                strain.setLineViabilityRedMaxAgeFemales(new Integer(myGet("lineViabilityRedMaxAgeFemales", ssm)));
            }
            if(myGet("lineViabilityYellowMaxAgeMales", ssm).equals("")){
                strain.setLineViabilityYellowMaxAgeMales(null);
            }
            else{
                strain.setLineViabilityYellowMaxAgeMales(new Integer(myGet("lineViabilityYellowMaxAgeMales", ssm)));
            }
            if(myGet("lineViabilityRedMaxAgeMales", ssm).equals("")){
                strain.setLineViabilityRedMaxAgeMales(null);
            }
            else{
                strain.setLineViabilityRedMaxAgeFemales(new Integer(myGet("lineViabilityRedMaxAgeMales", ssm)));
            }
            
            dto.setStrain(strain);
            
            Integer yellowFemaleAge;
            Integer yellowMaleAge;
            Integer redFemaleAge;
            Integer redMaleAge;
            //get yellow and red ages, if no value use 80 and 60 for female and 100 and 90 for males
            //yellow female age
            if(strain.getLineViabilityYellowMaxAgeFemales() == null || strain.getLineViabilityYellowMaxAgeFemales().intValue() == 0 ){
                yellowFemaleAge = new Integer(240);
                strain.setLineViabilityYellowMaxAgeFemales(yellowFemaleAge);
            }
            else{
                yellowFemaleAge = strain.getLineViabilityYellowMaxAgeFemales();
            }
            
            //yellow male age
            if(strain.getLineViabilityYellowMaxAgeMales() == null || strain.getLineViabilityYellowMaxAgeMales().intValue() == 0 ){
                yellowMaleAge = new Integer(240);
                strain.setLineViabilityYellowMaxAgeMales(yellowMaleAge);
            }
            else{
                yellowMaleAge = strain.getLineViabilityYellowMaxAgeMales();
            }
            
            //Red Female age
            if(strain.getLineViabilityRedMaxAgeFemales() == null || strain.getLineViabilityRedMaxAgeFemales().intValue() == 0 ){
                redFemaleAge = new Integer(365);
                strain.setLineViabilityRedMaxAgeFemales(redFemaleAge);
            }
            else{
                redFemaleAge = strain.getLineViabilityRedMaxAgeFemales();
            }
            
            //red male age
            if(strain.getLineViabilityRedMaxAgeMales() == null || strain.getLineViabilityRedMaxAgeMales().intValue() == 0 ){
                redMaleAge = new Integer(365);
                strain.setLineViabilityRedMaxAgeMales(redMaleAge);
            }
            else{
                redMaleAge = strain.getLineViabilityRedMaxAgeMales();
            }
            
            //get yellow and red numbers, if no value use 8 and 4 for female and 4 and 2 for males
            if(strain.getLineViabilityRedMinNumFemales() == null || strain.getLineViabilityRedMinNumFemales().intValue() == 0){
                strain.setLineViabilityRedMinNumFemales(new Integer(6));
            }
            if(strain.getLineViabilityYellowMinNumFemales() == null || strain.getLineViabilityYellowMinNumFemales().intValue() == 0){
                strain.setLineViabilityYellowMinNumFemales(new Integer(12));
            }
            if(strain.getLineViabilityRedMinNumMales() == null || strain.getLineViabilityRedMinNumMales().intValue() == 0){
                strain.setLineViabilityRedMinNumMales(new Integer(3));
            }
            if(strain.getLineViabilityYellowMinNumMales() == null || strain.getLineViabilityYellowMinNumMales().intValue() == 0){
                strain.setLineViabilityYellowMinNumMales(new Integer(6));
            }
            
            //get total living mice for all active strains
            String totalQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS totalMice " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " + 
                                "AND Mouse._strain_key = " + strain.getStrainKey().toString() +
                                mouseOwnerWhere +
                                " GROUP BY Mouse._strain_key;";
            SortedMap[] total = this.executeJCMSQuery(totalQuery).getRows();
            if(total.length > 0){
                dto.setNumberLiving(Integer.parseInt(myGet("totalMice", total[0])));
            }
            
            //get number cages
            String cagesQuery = "SELECT DISTINCT Strain.strainName, _container_key " +
                                "FROM Container " +
                                "JOIN Mouse ON Mouse._pen_key = Container._container_key " +
                                "JOIN Strain ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                mouseOwnerWhere +
                                "AND Mouse._strain_key = " + strain.getStrainKey().toString();
            SortedMap[] cages = this.executeJCMSQuery(cagesQuery).getRows();
            dto.setNumberCages(cages.length);
            
            //get number of matings
            String matingsQuery = "SELECT Strain.strainName, COUNT(_mating_key) AS matings "
                    + "FROM Mating "
                    + "JOIN Strain "
                    + "ON Mating._strain_key = Strain._strain_key "
                    + "JOIN cv_CrossStatus "
                    + "ON cv_CrossStatus._crossStatus_key = Mating._crossStatus_key "
                    + "WHERE cv_CrossStatus.abbreviation = 'A' "
                    + matingOwnerWhere
                    + "AND Mating._strain_key = " + strain.getStrainKey().toString() ;
            SortedMap[] matings = this.executeJCMSQuery(matingsQuery).getRows();
            if(matings.length > 0){
                String numMatings = myGet("matings", matings[0]);
                if(numMatings.equals("")){
                    dto.setNumberMatings(0);
                }
                else{
                    dto.setNumberMatings(Integer.parseInt(numMatings));
                }
            }
            
            
            /* After this, all gender red/yellow/green stuff */
            //Females
            //get total green female mice for all active strains
            String greenFemaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS greenFemales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                " AND Mouse.sex = 'F' " + 
                                " AND Mouse._strain_key = " + strain.getStrainKey().toString() + 
                                mouseOwnerWhere +
                                " AND DATE_ADD(Mouse.birthDate, INTERVAL " + yellowFemaleAge.toString() + " DAY) > now() " +
                                " GROUP BY Mouse._strain_key;";
            SortedMap[] greenFemales = this.executeJCMSQuery(greenFemaleQuery).getRows();
            if(greenFemales.length > 0){
                dto.setNumberGreenFemales(Integer.parseInt(myGet("greenFemales", greenFemales[0])));
            }
            
            //get total yellow female mice for all active strains
            String yellowFemaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS yellowFemales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                "AND Mouse.sex = 'F' " +
                                mouseOwnerWhere +
                                "AND Mouse._strain_key = " + strain.getStrainKey().toString() + " " +
                                "AND DATE_ADD(Mouse.birthDate, INTERVAL " + yellowFemaleAge.toString() + " DAY) < now() " +
                                "AND DATE_ADD(Mouse.birthDate, INTERVAL " + redFemaleAge.toString() + " DAY) > now() " +
                                "GROUP BY Mouse._strain_key;";
            SortedMap[] yellowFemales = this.executeJCMSQuery(yellowFemaleQuery).getRows();
            if(yellowFemales.length > 0){
                dto.setNumberYellowFemales(Integer.parseInt(myGet("yellowFemales", yellowFemales[0])));
            }
            
            //get total red female mice for all active strains
            String redFemaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS redFemales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                " AND Mouse.sex = 'F' " + 
                                mouseOwnerWhere +
                                " AND Mouse._strain_key = " + strain.getStrainKey().toString() +
                                " AND DATE_ADD(Mouse.birthDate, INTERVAL " + redFemaleAge.toString() + " DAY) < now() " +
                                " GROUP BY Mouse._strain_key;";
            SortedMap[] redFemales = this.executeJCMSQuery(redFemaleQuery).getRows();
            if(redFemales.length > 0){
                dto.setNumberRedFemales(Integer.parseInt(myGet("redFemales", redFemales[0])));
            }
            
            //Males
            //get total green male mice for all active strains
            String greenMaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS greenMales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                " AND Mouse.sex = 'M' " + 
                                mouseOwnerWhere +
                                " AND Mouse._strain_key = " + strain.getStrainKey().toString() +
                                " AND DATE_ADD(Mouse.birthDate, INTERVAL " + yellowMaleAge.toString() + " DAY) > now() " +
                                " GROUP BY Mouse._strain_key;";
            SortedMap[] greenMales = this.executeJCMSQuery(greenMaleQuery).getRows();
            if(greenMales.length > 0){
                dto.setNumberGreenMales(Integer.parseInt(myGet("greenMales", greenMales[0])));
            }
            
            //get total male yellow mice for all active strains
            String yellowMaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS yellowMales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                "AND Mouse.sex = 'M' " +
                                mouseOwnerWhere +
                                "AND Mouse._strain_key = " + strain.getStrainKey().toString() + " " +
                                "AND DATE_ADD(Mouse.birthDate, INTERVAL " + yellowMaleAge.toString() + " DAY) < now() " +
                                "AND DATE_ADD(Mouse.birthDate, INTERVAL " + redMaleAge.toString() + " DAY) > now() " +
                                "GROUP BY Mouse._strain_key;";
            SortedMap[] yellowMales = this.executeJCMSQuery(yellowMaleQuery).getRows();
            if(yellowMales.length > 0){
                dto.setNumberYellowMales(Integer.parseInt(myGet("yellowMales", yellowMales[0])));
            }
            
            //get total male red mice for all active strains
            String redMaleQuery = "SELECT Strain.strainName, COUNT(_mouse_key) AS redMales " +
                                "FROM Mouse " +
                                "JOIN Strain " +
                                "ON Mouse._strain_key = Strain._strain_key " +
                                "WHERE Mouse.lifeStatus = 'A' " +
                                " AND Mouse.sex = 'M' " + 
                                mouseOwnerWhere +
                                " AND Mouse._strain_key = " + strain.getStrainKey().toString() +
                                " AND DATE_ADD(Mouse.birthDate, INTERVAL " + redMaleAge.toString() + " DAY) < now() " +
                                " GROUP BY Mouse._strain_key;";
            SortedMap[] redMales = this.executeJCMSQuery(redMaleQuery).getRows();
            if(redMales.length > 0){
                dto.setNumberRedMales(Integer.parseInt(myGet("redMales", redMales[0])));
            }
            
            //get genotypes with counts
            String genotypesQuery = "SELECT CONCAT_WS('', Gene.labSymbol, ' ', Genotype.allele1, ' ', Genotype.allele2) AS genotype, "
                        + "COUNT(CONCAT_WS('', Gene.labSymbol, ' ', Genotype.allele1, ' ', Genotype.allele2)) AS genotypeCount "
                        + " FROM Mouse "
                        + " JOIN Strain "
                        + " ON Mouse._strain_key = Strain._strain_key "
                        + " LEFT JOIN Genotype "
                        + " ON Mouse._mouse_key = Genotype._mouse_key "
                        + " JOIN Gene "
                        + " ON Genotype._gene_key = Gene._gene_key "
                        + " WHERE Mouse.lifeStatus = 'A' "
                        + mouseOwnerWhere
                        + " AND Strain._strain_key = " + strain.getStrainKey().toString()
                        + " GROUP BY CONCAT_WS('', Gene.labSymbol, ' ', Genotype.allele1, ' ', Genotype.allele2);";
            dto.setGenotypes(this.executeJCMSQuery(genotypesQuery).getRows());         
            
            //get mice w/ no genotyeps
            String noGenotypeQuery = "SELECT DISTINCT COUNT(ID) AS noGenotypes "
                                + "FROM Mouse "
                                + "LEFT JOIN Genotype "
                                + "ON Mouse._mouse_key = Genotype._mouse_key "
                                + "JOIN Strain "
                                + "ON Mouse._strain_key = Strain._strain_key "
                                + "WHERE _genotype_key IS NULL "
                                + "AND Mouse.lifeStatus = 'A' "
                                + mouseOwnerWhere
                                + "AND Strain._strain_key = " + strain.getStrainKey().toString();
            SortedMap[] noGenotypes = this.executeJCMSQuery(noGenotypeQuery).getRows();
            if(noGenotypes.length > 0){
                dto.setNoGenotype(Integer.parseInt(myGet("noGenotypes", noGenotypes[0])));
            }
            
            bubbles.add(dto);
        }
        
        return bubbles;
    } 
    
    public String getMaxLivingMice(ArrayList<String> wgs){
        String mouseOwnerWhere = " AND (";
        //build strain portion of where clause
        for(String wg : wgs){
            if(wg.equals(wgs.get(wgs.size() - 1))){
                mouseOwnerWhere = mouseOwnerWhere + "Mouse.owner = '" + wg + "'";
            }
            else{
                mouseOwnerWhere = mouseOwnerWhere + "Mouse.owner = '" + wg + "' OR ";
            }
        }
        mouseOwnerWhere = mouseOwnerWhere + ") ";
        String query = "SELECT MAX(mice) AS maxNum " +
            "FROM " +
            "(SELECT count(_mouse_key) AS mice " +
            "FROM Mouse " +
            "JOIN Strain " +
            "ON Mouse._strain_key = Strain._strain_key " +    
            "WHERE Mouse.lifeStatus = 'A' " +
            mouseOwnerWhere +
            "GROUP BY Mouse._strain_key " +
            ") AS max;";
        SortedMap[] max = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : max){
            return myGet("maxNum", row);
        }
        return "";
    }
}
