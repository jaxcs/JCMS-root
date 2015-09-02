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

package jcms.middletier.businessobject;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jcms.integrationtier.base.SystemFacadeLocal;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.base.BTBaseObject;
import jcms.middletier.service.JCMSWebAppService;
import org.jax.cs.utils.simpleXMLentity.*;
import static jcms.integrationtier.dao.MatingQueryConst.*;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 * <b>File name:</b>  MatingSearchBO.java  <p>
 * <b>Date developed:</b>  September 2010 <p>
 * <b>Purpose:</b>  Provides access to and enforces business logic for
 * mouse query page.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class MatingSearchBO extends BTBaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    SystemFacadeLocal portal;


    public MatingSearchBO() {
        portal = new IntegrationTierPortal().getSystemFacadeLocal();
    }

     public MatingSearchBO(String strTest) {

    }
     /**
     * Access the filter criteria from mating query page and generate and
     * execute sql query in  the integration tier and return the result set.
     * @param search of type MouseQueryDTO
     * @return result
     */
    public ResultDTO runQuery(MatingQueryDTO search) throws SQLException {
        int i = 0;

        System.out.println("Search Criteria details");
        if (search.getMatingIDFrom() != null) {
            System.out.println(search.getMatingIDFrom().getMatingID());
        }

        if (search.getLitterGeneration() != null) {
            for (i = 0; i < search.getLitterGeneration().size(); ++i) {
                System.out.println("Generation " + search.getLitterGeneration().get(i).getGeneration());
            }
        }

        System.out.println(search.isSelectBirthDates());
        System.out.println(search.isSelectTotalFemales());
        System.out.println(search.isSelectTotalMales());
        System.out.println(search.isSelectTotalPups());
        System.out.println(search.isSelectTotalFemales());
        System.out.println(search.isSelectTotalLitters());
        System.out.println(search.isSelectTotalLittersDead());

        System.out.println(search.isSelectMatingID());
        System.out.println(search.isSelectMatingDates());

        return portal.findMatingQueryJDBC(search);
    }

    /**
     * Access the filter criteria from mating query page and generate and
     * execute sql query in  the integration tier, generate the result set
     * and create a csv from resultset.
     * @param search of type MatingQueryDTO
     * @return String file Path
     */
    public void generateReport(MatingQueryDTO search, BufferedWriter out)
            throws SQLException {
        portal.generateMatingQueryReport(search, out);
    }

    /**
     * Access the filter criteria from mouse query page and generate the
     * sql query in  the integration tier and save it in the database.
     * @param search of type MatingQueryDTO, name of type String
     */
    public void saveQuery(MatingQueryDTO search, String name, UserEntity user,
            Integer wgKey, Integer queryDefinitionKey) throws Exception {
       getLogger().logInfo(formatLogMessage("SaveQuery ", "Search Criteria " +
               "details from the middle tier"));

        try {
            // serialize the MouseQueryDTO using XML properties
            getLogger().logInfo(formatLogMessage("serialize the MatingQueryDTO " +
                    "using XML properties", "SaveQuery "));

            String xmlString = this.dto2XML(search);
            QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
            // set the the properties for userdefinedentity
            if (queryDefinitionKey != null)
                udEntity.setQueryDefinitionkey(queryDefinitionKey);
            // Query Name
            udEntity.setQueryName(name);
            // Query Options
            udEntity.setQueryOptions(xmlString);
            // Query Type
            CvQueryTypeEntity udType = new CvQueryTypeEntity();
            udType.setQueryTypekey(2);
            udEntity.setQueryTypekey(udType);
            // Workgroup
            WorkgroupEntity we = new WorkgroupEntity();
            we.setWorkgroupkey(wgKey);
            udEntity.setWorkgroupkey(we);

            // CreatedBy
            udEntity.setCreatedBy(user.getUserName());

            // Date Created
            udEntity.setDateCreated(new Date());
            // ModifiedBy
            udEntity.setModifiedBy(user.getUserName());

            //DateModified
            udEntity.setDateModified(new Date());

            // set user
            udEntity.setUserkey(user.getKey());

            new JCMSWebAppService().saveQuery(udEntity);
            getLogger().logInfo(formatLogMessage( "Query Saved .....", "SaveQuery "));
        }
        catch(Exception e) {
           getLogger().logInfo(formatLogMessage("SaveQuery ", "Ooops!!"));
           getLogger().logInfo(formatLogMessage(e.getMessage() , "SaveQuery:"));

            throw new Exception();
        }

    }

    /**
     * Access the filter criteria from mouse query page and generate and
     * execute sql query in  the integration tier and return the result set.
     * @param search of type MouseQueryDTO
     * @return resultset
     */
    public MatingQueryDTO loadQuery(Integer key) throws Exception {
        QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
        udEntity = new JCMSWebAppService().loadQueryByKey(key);
        String xmlString = udEntity.getQueryOptions();
        MatingQueryDTO mq = this.xml2DTO(xmlString);
        // de-serialize the query from entity into DTO
        return mq;   
    }



    protected MatingQueryDTO xml2DTO(String xmlString) throws ParseException{
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList(xmlString);

        MatingQueryDTO mqDTO = new MatingQueryDTO();
        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        // Entity Lists
        List<String> matingStatusEntityList = new ArrayList<String>();
        List<OwnerEntity> matingOwnerEntityList = new ArrayList<OwnerEntity>();
        List<CvGenerationEntity> litterGenerationEntityList = new ArrayList<CvGenerationEntity>();
        List<StrainEntity> litterStrainEntityList = new ArrayList<StrainEntity>();
        List<ContainerEntity> containerIDEntityList = new ArrayList<ContainerEntity>();
        List<CvCrossstatusEntity> crossStatusEntityList = new ArrayList<CvCrossstatusEntity>();


        ArrayList<SimpleXMLEntity> myPropertyArray = sxpl.getPList();
        int arraySize = myPropertyArray.size();
        int counter = 0;
        SimpleXMLEntity node = null;

        while ( counter < arraySize ){
            node = myPropertyArray.get(counter);
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingID) ) {
                mqDTO.setSelectMatingID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingDates) ) {
                mqDTO.setSelectMatingDates(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingStatus) ) {
                mqDTO.setSelectMatingStatus(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingStrain) ) {
                mqDTO.setSelectMatingStrain(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectLitterGeneration) ) {
                mqDTO.setSelectMatingGeneration(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingOwner) ) {
                mqDTO.setSelectMatingOwner(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingPenId) ) {
                mqDTO.setSelectMatingPenId(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMatingPenName) ) {
                mqDTO.setSelectMatingPenName(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1ID) ) {
                mqDTO.setSelectDam1ID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1Strain) ) {
                mqDTO.setSelectDam1Strain(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1Stock) ) {
                mqDTO.setSelectDam1Stock(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1Gen) ) {
                mqDTO.setSelectDam1Gen(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1Genotype) ) {
                mqDTO.setSelectDam1Genotype(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1DOB) ) {
                mqDTO.setSelectDam1DOB(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam1PlugDate) ) {
                mqDTO.setSelectDam1PlugDate(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2ID) ) {
                mqDTO.setSelectDam2ID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2Strain) ) {
                mqDTO.setSelectDam2Strain(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2Stock) ) {
                mqDTO.setSelectDam2Stock(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2Gen) ) {
                mqDTO.setSelectDam2Gen(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2Genotype) ) {
                mqDTO.setSelectDam2Genotype(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2DOB) ) {
                mqDTO.setSelectDam2DOB(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDam2PlugDate) ) {
                mqDTO.setSelectDam2PlugDate(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireID) ) {
                mqDTO.setSelectSireID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireStrain) ) {
                mqDTO.setSelectSireStrain(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireStock) ) {
                mqDTO.setSelectSireStock(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireGen) ) {
                mqDTO.setSelectSireGen(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireGenotype) ) {
                mqDTO.setSelectSireGenotype(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectSireDOB) ) {
                mqDTO.setSelectSireDOB(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectDateRetired) ) {
                mqDTO.setSelectDateRetired(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectWeanTime) ) {
                mqDTO.setSelectWeanTime(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectNeedsTyping) ) {
                mqDTO.setSelectNeedsTyping(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalLitters) ) {
                mqDTO.setSelectTotalLitters(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalPups) ) {
                mqDTO.setSelectTotalPups(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalMales) ) {
                mqDTO.setSelectTotalMales(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalFemales) ) {
                mqDTO.setSelectTotalFemales(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalPupsBornDead) ) {
                mqDTO.setSelectTotalPupsBornDead(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalPupsMissingAtWean) ) {
                mqDTO.setSelectTotalPupsMissingAtWean(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalPupsCulledAtWean) ) {
                mqDTO.setSelectTotalPupsCulledAtWean(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectLitterIDs) ) {
                mqDTO.setSelectLitterIDs(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectTotalLittersDead) ) {
                mqDTO.setSelectTotalLittersDead(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectBirthDates) ) {
                mqDTO.setSelectBirthDates(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingStartDate) ) {
                mqDTO.setMatingStartDate( zDateformatter.parse(node.getPropertyValue()) );
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingEndDate) ) {
                mqDTO.setMatingEndDate( zDateformatter.parse(node.getPropertyValue()) );
            }

            if ( 0 == node.getPropertyName().trim().compareTo(matingFilter) ) {
                mqDTO.setMatingFilter(node.getPropertyValue().trim());
            }

            if ( 0 == node.getPropertyName().trim().compareTo(matingIDFromEntity) ) {
                MatingEntity me = new MatingEntity();
                // Set some Values
                me.setMatingID(Integer.parseInt(node.getPropertyValue().trim()));
                me.setMatingKey(Integer.parseInt(node.getPropertyKey().trim()));
                // Add to DTO
                mqDTO.setMatingIDFrom(me);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingIDToEntity) ) {
                MatingEntity me = new MatingEntity();
                // Set some Values
                me.setMatingID(Integer.parseInt(node.getPropertyValue().trim()));
                me.setMatingKey( Integer.parseInt(node.getPropertyValue().trim()) );
                // Add to DTO
                mqDTO.setMatingIDTo(me);
            }
            
            if ( 0 == node.getPropertyName().trim().compareTo(matingStatusEntity) ) {
                // Create Entity
                CvCrossstatusEntity se = new CvCrossstatusEntity();
                // Update Entity
                se.setCrossStatus(node.getPropertyValue().trim());
                se.setCrossStatuskey( Integer.parseInt(node.getPropertyKey().trim()) );
                // Add Entity to List
                crossStatusEntityList.add(se);
            }

            if ( 0 == node.getPropertyName().trim().compareTo(litterStrainEntity) ) {
                // Create Entity
                StrainEntity se = new StrainEntity();
                // Update Entity
                se.setStrainName(node.getPropertyValue().trim());
                se.setStrainKey( Integer.parseInt(node.getPropertyKey().trim()) );
                // Add Entity to List
                litterStrainEntityList.add(se);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(litterGenerationEntity) ) {
                // Create Entity
                CvGenerationEntity ge = new CvGenerationEntity();
                // Update Entity
                ge.setGeneration(node.getPropertyValue().trim());
                ge.setGenerationKey(Integer.parseInt(node.getPropertyKey().trim()) );
                // Add Entity to List
                litterGenerationEntityList.add(ge);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingOwnerEntity) ) {
                // Create Entity
                OwnerEntity oe = new OwnerEntity();
                // Update Entity
                oe.setOwner(node.getPropertyValue().trim());
                oe.setOwnerKey(Integer.parseInt(node.getPropertyKey().trim()));
                // Add Entity to List
                matingOwnerEntityList.add(oe);
            }

            // penID
            if ( 0 == node.getPropertyName().trim().compareTo(penIDFrom)) {
                // Add
                mqDTO.setPenIDFrom(Integer.parseInt(node.getPropertyValue()));
            }

            if ( 0 == node.getPropertyName().trim().compareTo(penIDTo)) {
                // Add
                mqDTO.setPenIDTo(Integer.parseInt(node.getPropertyValue()));
            }

            if ( 0 == node.getPropertyName().trim().compareTo(penIdFilter)) {
                // Add
                mqDTO.setPenIdFilter(node.getPropertyValue());
            }

            // pen name
            if ( 0 == node.getPropertyName().trim().compareTo(pName)) {
                // Add
                mqDTO.setpName(node.getPropertyValue());
            }

            if ( 0 == node.getPropertyName().trim().compareTo(penNameFilter)) {
                // Add
                mqDTO.setPenNameFilter(node.getPropertyValue());
            }

            // mating ID From
            if ( 0 == node.getPropertyName().trim().compareTo(mIDFrom)) {
                // Add
                mqDTO.setmIDFrom(Integer.parseInt(node.getPropertyValue()));
            }

            if ( 0 == node.getPropertyName().trim().compareTo(mIDTo)) {
                // Add
                mqDTO.setmIDTo(Integer.parseInt(node.getPropertyValue()));
            }

            if ( 0 == node.getPropertyName().trim().compareTo(containerIDEntity) ) {
                // Create Entity
                ContainerEntity ce = new ContainerEntity();
                // Update Entity
                ce.setContainerID(Integer.parseInt(node.getPropertyValue().trim()));
                ce.setContainerKey(Integer.parseInt(node.getPropertyKey().trim()));
                // Add Entity to List
                containerIDEntityList.add(ce);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingRetireDateStart) ) {
                mqDTO.setRetiredStartDate(zDateformatter.parse(node.getPropertyValue()) );
            }
            if ( 0 == node.getPropertyName().trim().compareTo(matingRetireDateEnd) ) {
                mqDTO.setRetiredEndDate(zDateformatter.parse(node.getPropertyValue()) );
            }
            counter++;
        }
        
        mqDTO.setLitterStrain( litterStrainEntityList );
        mqDTO.setCrossStatus(crossStatusEntityList);
        mqDTO.setLitterGeneration( litterGenerationEntityList );
        mqDTO.setMatingOwner( matingOwnerEntityList );
        mqDTO.setPenName( containerIDEntityList );
        mqDTO.setPenNumber( containerIDEntityList);

        return mqDTO;

    }


   protected String dto2XML(MatingQueryDTO search){
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList();
        SimpleXMLEntity sxp = null;
//
        StringBuffer strBuff = new StringBuffer();
        int counter = 0;
        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        // Select Item ....
	// Mating ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingID);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingID()));
        sxpl.addProperty(sxp);

	// Mating Dates
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingDates);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingDates()));
        sxpl.addProperty(sxp);


	// Mating Status
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingStatus);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingStatus()));
        sxpl.addProperty(sxp);

        // Litter Strain
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingStrain);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingStrain()));
        sxpl.addProperty(sxp);

        // Litter Generation
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectLitterGeneration);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingGeneration()));
        sxpl.addProperty(sxp);

        // Mating Owner
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingOwner);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingOwner()));
        sxpl.addProperty(sxp);

        // Pen ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingPenId);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingPenId()));
        sxpl.addProperty(sxp);

	// Pen Name
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMatingPenName);
        sxp.setPropertyValue( Boolean.toString(search.isSelectMatingPenName()));
        sxpl.addProperty(sxp);

        // Dam 1 ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1ID);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1ID()));
        sxpl.addProperty(sxp);

	// Dam 1 Strain
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1Strain);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1Strain()));
        sxpl.addProperty(sxp);

	// Dam 1 Stock #
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1Stock);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1Stock()));
        sxpl.addProperty(sxp);

        // Dam 1 Generation
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1Gen);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1Gen()));
        sxpl.addProperty(sxp);


	// Dam 1 Up To Three Genotypes
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1Genotype);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1Genotype()));
        sxpl.addProperty(sxp);

	// Dam 1 Date Of Birth
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1DOB);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1DOB()));
        sxpl.addProperty(sxp);

        // Dam 1 Plug Dates
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam1PlugDate);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam1PlugDate()  ));
        sxpl.addProperty(sxp);

        // Dam 2 ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2ID);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2ID()));
        sxpl.addProperty(sxp);

	// Dam 2 Strain
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2Strain );
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2Strain()  ));
        sxpl.addProperty(sxp);

	// Dam 2 Stock #
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2Stock);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2Stock()  ));
        sxpl.addProperty(sxp);

        // Dam 2 Generation
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectDam2Gen );
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2Gen()  ));
        sxpl.addProperty(sxp);

	// Dam 2 Up To Three Genotypes
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2Genotype);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2Genotype()  ));
        sxpl.addProperty(sxp);

	// Dam 2 Date Of Birth
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2DOB);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2DOB()));
        sxpl.addProperty(sxp);

        // Dam 2 Plug Dates
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDam2PlugDate);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDam2PlugDate()  ));
        sxpl.addProperty(sxp);

        // Sire ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireID);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireID()  ));
        sxpl.addProperty(sxp);

	// Sire Strain
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireStrain);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireStrain()  ));
        sxpl.addProperty(sxp);

        // Sire Stock #
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireStock);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireStock()  ));
        sxpl.addProperty(sxp);

	// Sire Generation
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireGen);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireGen()));
        sxpl.addProperty(sxp);

        // Sire Up To Three Genotypes
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireGenotype);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireGenotype()));
        sxpl.addProperty(sxp);

	// Sire Date Of Birth
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectSireDOB);
        sxp.setPropertyValue( Boolean.toString(search.isSelectSireDOB()  ));
        sxpl.addProperty(sxp);

        // Date Retired
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectDateRetired);
        sxp.setPropertyValue( Boolean.toString(search.isSelectDateRetired()));
        sxpl.addProperty(sxp);

	// Wean Time
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectWeanTime);
        sxp.setPropertyValue( Boolean.toString(search.isSelectWeanTime()));
        sxpl.addProperty(sxp);

	// Needs Typing
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectNeedsTyping );
        sxp.setPropertyValue( Boolean.toString(search.isSelectNeedsTyping()  ));
        sxpl.addProperty(sxp);
        
           //  Summarize Litter Information
        // Total number of litters
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectTotalLitters);
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalLitters()  ));
        sxpl.addProperty(sxp);

        // Total number of pups born
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectTotalPups);
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalPups()  ));
        sxpl.addProperty(sxp);

        // Total number of males weaned
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectTotalMales);
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalMales()  ));
        sxpl.addProperty(sxp);

        // Total number of females weaned
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectTotalFemales);
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalFemales()  ));
        sxpl.addProperty(sxp);

        // Total number of litters born dead
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectTotalLittersDead);
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalLittersDead()  ));
        sxpl.addProperty(sxp);

        // mating id from
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(mIDFrom);
        sxp.setPropertyValue(Integer.toString(search.getmIDFrom()));
        sxpl.addProperty(sxp);

        // mating id to
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(mIDTo);
        sxp.setPropertyValue(Integer.toString(search.getmIDTo()));
        sxpl.addProperty(sxp);

        // mating Filter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingFilter);
        sxp.setPropertyValue(search.getMatingFilter());
        sxpl.addProperty(sxp);

        // First and last birth dates
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectBirthDates);
        sxp.setPropertyValue( Boolean.toString(search.isSelectBirthDates()));
        sxpl.addProperty(sxp);
        
                // Litter IDs
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectLitterIDs );
        sxp.setPropertyValue( Boolean.toString(search.isSelectLitterIDs()  ));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectTotalPupsBornDead );
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalPupsBornDead()  ));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectTotalPupsCulledAtWean );
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalPupsCulledAtWean()  ));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName( selectTotalPupsMissingAtWean );
        sxp.setPropertyValue( Boolean.toString(search.isSelectTotalPupsMissingAtWean()  ));
        sxpl.addProperty(sxp);

        // Mating ID From
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingIDFromEntity);
        MatingEntity mid =  search.getMatingIDFrom();
        if ( null != mid ) {
            sxp.setPropertyValue(Integer.toString(mid.getMatingID()));
            sxp.setPropertyKey(Integer.toString(mid.getMatingKey()));
        } else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);

        // Mating ID To
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingIDToEntity);
        MatingEntity midT =  search.getMatingIDTo();
        if ( null != midT ) {
            sxp.setPropertyValue(Integer.toString(midT.getMatingID()));
            sxp.setPropertyKey(Integer.toString(midT.getMatingKey()));
        } else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);

	// Mating Dates
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingStartDate);
        if ( null != search.getMatingStartDate())
            sxp.setPropertyValue( zDateformatter.format(search.getMatingStartDate()).trim());
        else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);


        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingEndDate);
        if (null != search.getMatingEndDate())
            sxp.setPropertyValue( zDateformatter.format(search.getMatingEndDate()).trim());
        else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingRetireDateStart);
        if (null != search.getRetiredStartDate())
            sxp.setPropertyValue( zDateformatter.format(search.getRetiredStartDate()).trim());
        else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(matingRetireDateEnd);
        if (null != search.getRetiredEndDate())
            sxp.setPropertyValue( zDateformatter.format(search.getRetiredEndDate()).trim());
        else
            sxp.setPropertyValue("");
        sxpl.addProperty(sxp);

	// Litter Strain
        counter = 0;
        List<StrainEntity> lsList = search.getLitterStrain();
        if ( null != lsList ) {
            while ( counter < lsList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName( litterStrainEntity);
                sxp.setPropertyValue( ( lsList.get(counter)).getStrainName().trim() );
                sxp.setPropertyKey( Integer.toString((lsList.get(counter)).getKey()));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName( litterStrainEntity);
            sxp.setPropertyValue( "" );
            sxpl.addProperty(sxp);
        }
        
        // Mating Status
        counter = 0;
        List<CvCrossstatusEntity> lmList = search.getCrossStatus();
        if ( null != lmList ) {
            while ( counter < lmList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(matingStatusEntity);
                sxp.setPropertyValue( ( lmList.get(counter)).getCrossStatus().trim() );
                sxp.setPropertyKey( Integer.toString((lmList.get(counter)).getKey()));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(matingStatusEntity);
            sxp.setPropertyValue( "" );
            sxpl.addProperty(sxp);
        }

	// Litter Generation
        counter = 0;
        List<CvGenerationEntity> geList = search.getLitterGeneration();
        if ( null != geList ) {
            while ( counter < geList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName( litterGenerationEntity );
                sxp.setPropertyValue( ( geList.get(counter)).getGeneration().trim() );
                sxp.setPropertyKey( Integer.toString( ( geList.get(counter)).getKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName( litterGenerationEntity );
            sxp.setPropertyValue("");
            sxpl.addProperty(sxp);
        }

	// Mating Owner
        counter = 0;
        List<OwnerEntity> oList = search.getMatingOwner();
        if ( null != oList ) {
            while ( counter < oList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName( matingOwnerEntity );
                sxp.setPropertyValue( ( oList.get(counter)).getOwner().trim() );
                sxp.setPropertyKey( Integer.toString( ( oList.get(counter)).getKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName( matingOwnerEntity );
            sxp.setPropertyValue("");
            sxpl.addProperty(sxp);
        }

        // Pen ID From
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(penIDFrom);
        sxp.setPropertyValue(Integer.toString(search.getPenIDFrom()));
        sxpl.addProperty(sxp);

        // Pen ID To
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(penIDTo);
        sxp.setPropertyValue(Integer.toString(search.getPenIDTo()));
        sxpl.addProperty(sxp);

        // Pen ID Filter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(penIdFilter);
        sxp.setPropertyValue(search.getPenIdFilter());
        sxpl.addProperty(sxp);

        // Pen name
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(pName);
        sxp.setPropertyValue(search.getpName());
        sxpl.addProperty(sxp);

        // Pen name Filter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(penNameFilter);
        sxp.setPropertyValue(search.getPenNameFilter());
        sxpl.addProperty(sxp);

	// Pen ID
        counter = 0;
        List<ContainerEntity> ceList = search.getPenNumber();
        if ( null != ceList ) {
            while ( counter < ceList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName( containerIDEntity );
                sxp.setPropertyValue( Integer.toString((ceList.get(counter)).getContainerID()) );
                sxp.setPropertyKey( Integer.toString( ( ceList.get(counter)).getKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName( containerIDEntity);
                sxp.setPropertyValue("");
                sxpl.addProperty(sxp);
        }

	// Pen Name
        counter = 0;
        List<ContainerEntity> cnList = search.getPenName();
        if ( null != cnList ) {
            while ( counter < cnList.size() ){
               sxp = new SimpleXMLEntity();
                sxp.setPropertyName( containerNameEntity);
                sxp.setPropertyValue((cnList.get(counter)).getContainerName().trim());
                sxp.setPropertyKey( Integer.toString( (cnList.get(counter)).getKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        } else {
               sxp = new SimpleXMLEntity();
                sxp.setPropertyName( containerNameEntity);
                sxp.setPropertyValue("");
                sxpl.addProperty(sxp);
        }

        return sxpl.getXMLString();
   }


}