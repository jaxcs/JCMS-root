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
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.cv.CvSexEntity;
import static jcms.integrationtier.dao.MouseQueryConst.*;

import jcms.middletier.base.BTBaseObject;
import jcms.middletier.service.SearchAppService;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.service.JCMSWebAppService;
import org.jax.cs.utils.simpleXMLentity.*;

/**
 * <b>File name:</b>  MouseSearchBO.java  <p>
 * <b>Date developed:</b>  September 2010 <p>
 * <b>Purpose:</b>  Provides access to and enforces business logic for 
 * mouse query page.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class MouseSearchBO extends BTBaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    SearchAppService service;

    public MouseSearchBO() {
        service = new SearchAppService();
    }

    // Don't get the SearchAppService to test xml2dto and dto2xml
    public MouseSearchBO(String strForTesting) {        
    }
    /**
     * Access the filter criteria from mouse query page and generate and
     * execute sql query in  the integration tier and return the result set.
     * @param search of type MouseQueryDTO
     * @return resultset
     */
    public ResultDTO runQuery(MouseQueryDTO search) throws SQLException {
        return new IntegrationTierPortal().getSystemFacadeLocal().
                findMouseQueryJDBC(search);
    }

    /**
     * Access the filter criteria from mating query page and generate and
     * execute sql query in  the integration tier, generate the result set
     * and create a csv from resultset.
     * @param search of type MouseQueryDTO
     * @return String file Path
     */
    public void generateReport(MouseQueryDTO search, BufferedWriter out)
            throws SQLException {
        new IntegrationTierPortal().getSystemFacadeLocal().
                generateMouseQueryReport(search, out);
    }

    /**
     * Access the filter criteria from mouse query page and generate the
     * sql query in  the integration tier and save it in the database.
     * @param search of type MouseQueryDTO, name of type String
     */
    public void saveQuery(MouseQueryDTO search, String name, UserEntity user, Integer wgKey, Integer queryDefinitionKey) 
        throws Exception {getLogger().logInfo(formatLogMessage("SaveQuery ", "Search Criteria details from the middle tier"));

        try {
            // serialize the MouseQueryDTO using XML properties
            getLogger().logInfo(formatLogMessage("serialize the MouseQueryDTO " +
                    "using XML properties", "MouseSearchBO"));

            String xmlString = this.dto2XML(search);
            QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
            if (queryDefinitionKey != null)
                udEntity.setQueryDefinitionkey(queryDefinitionKey);
            // set the the properties for userdefinedentity
            // Query Name
            udEntity.setQueryName(name);
            // Query Options
            udEntity.setQueryOptions(xmlString);
            // Query Type
            CvQueryTypeEntity udType = new CvQueryTypeEntity();
            udType.setQueryTypekey(1);
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
        }
        catch(Exception e) {
           getLogger().logInfo(formatLogMessage("SaveQuery ", "Ooops!!"));
           getLogger().logInfo(formatLogMessage(e.getMessage() , "MouseSearchBO"));

            throw new Exception();
        }
    }

    /**
     * Access the filter criteria from mouse query page and generate and
     * execute sql query in  the integration tier and return the result set.
     * @param search of type MouseQueryDTO
     * @return resultset
     */
    public MouseQueryDTO loadQuery(Integer key) throws Exception {
        QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
        udEntity = new JCMSWebAppService().loadQueryByKey(key);
        String xmlString = udEntity.getQueryOptions();
        
        // de-serialize the query from entity into DTO
        MouseQueryDTO search = this.xml2DTO(xmlString);

        return search;
    }

    protected MouseQueryDTO xml2DTO(String xmlString) throws ParseException {
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList(xmlString);

        MouseQueryDTO mqDTO = new MouseQueryDTO();
        DateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        // Entity Lists
        List<OwnerEntity> ownerEntityList = new ArrayList<OwnerEntity>();
        List<CvGenerationEntity> generationEntityList = new ArrayList<CvGenerationEntity>();
        List<ContainerEntity> containerEntityList = new ArrayList<ContainerEntity>();
        List<CvBreedingStatusEntity> breedingStatusEntityList = new ArrayList<CvBreedingStatusEntity>();
        List<CvSexEntity> sexEntityList = new ArrayList<CvSexEntity>();
        List<CvMouseOriginEntity> originEntityList = new ArrayList<CvMouseOriginEntity>();
        List<LifeStatusEntity> lifeStatusEntityList = new ArrayList<LifeStatusEntity>();
        List<StrainEntity> strainEntityList = new ArrayList<StrainEntity>();
        List<CvCauseOfDeathEntity> causeOfDeathEntityList = new ArrayList<CvCauseOfDeathEntity>();
        List<CvMouseProtocolEntity> protocolEntityList = new ArrayList<CvMouseProtocolEntity>();
        List<UseScheduleTermDTO> useScheduleTermsList = new ArrayList<UseScheduleTermDTO>();
        List<cvPhenotypeTermDTO> phenotypeTermsList = new ArrayList<cvPhenotypeTermDTO>();
        List<CvMouseUseEntity> mouseUseEntityList = new ArrayList<CvMouseUseEntity>();
        List<GeneEntity> geneEntityList = new ArrayList<GeneEntity>();
        List<LitterEntity> litterEntityList = new ArrayList<LitterEntity>();
        List<RoomEntity> roomEntityList = new ArrayList<RoomEntity>();


        ArrayList<SimpleXMLEntity> myPropertyArray = sxpl.getPList();
        int arraySize = myPropertyArray.size();
        int counter = 0;
        SimpleXMLEntity node = null;

        while ( counter < arraySize ){
            node = myPropertyArray.get(counter);

            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseID)) {
                mqDTO.setSelectMouseID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseLitterMates)) {
                mqDTO.setSelectMouseLitterMates(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseStrain)) {
                mqDTO.setSelectMouseStrain(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseLifeStatus)) {
                mqDTO.setSelectMouseLifeStatus(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseBreedingStatus)) {
                mqDTO.setSelectMouseBreedingStatus(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseGeneration )) {
                mqDTO.setSelectMouseGeneration(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseOwner)) {
                mqDTO.setSelectMouseOwner(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseOrigin)) {
                mqDTO.setSelectMouseOrigin(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseSex)) {
                mqDTO.setSelectMouseSex(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseCOD)) {
                mqDTO.setSelectMouseCOD(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseDOB)) {
                mqDTO.setSelectMouseDOB(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseExitDate)) {
                mqDTO.setSelectMouseExitDate(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseLitter)) {
                mqDTO.setSelectMouseLitter(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMousePenID)) {
                mqDTO.setSelectMousePenID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMousePenName)) {
                mqDTO.setSelectMousePenName(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseUse)) {
                mqDTO.setSelectMouseUse(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectUseSchedules)) {
                mqDTO.setSelectUseSchedules(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseComment)) {
                mqDTO.setSelectMouseComment(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMousePhenotypes)) {
                mqDTO.setSelectMousePhenotypes(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseGenotype)) {
                mqDTO.setSelectMouseGenotype(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseGenotypeDate)) {
                mqDTO.setSelectMouseGenotypeDate(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseAge)) {
                mqDTO.setSelectMouseAge(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseMating)) {
                mqDTO.setSelectMouseMating(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseParents)) {
                mqDTO.setSelectMouseParents(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseCoatColor)) {
                mqDTO.setSelectMouseCoatColor(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseDiet)) {
                mqDTO.setSelectMouseDiet(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseProtocolID)) {
                mqDTO.setSelectMouseProtocolID(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(selectMouseRoom)) {
                mqDTO.setSelectRoomName(Boolean.parseBoolean(node.getPropertyValue().trim()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseIDFromEntity)) {
                // Create Entity
                MouseEntity me = new MouseEntity();
                // Update Entity
                me.setId(node.getPropertyValue());
                me.setMouseKey( Integer.parseInt(node.getPropertyKey()) );
                // Add
                mqDTO.setMouseIDFrom(me);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseIDToEntity)) {
                // Create Entity
                MouseEntity me = new MouseEntity();
                // Update Entity
                me.setId(node.getPropertyValue());
                me.setMouseKey( Integer.parseInt(node.getPropertyKey()) );
                // Add
                mqDTO.setMouseIDTo(me);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseID)) {
                // Add
                mqDTO.setMouseID(node.getPropertyValue());
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseIDFilter)) {
                // Add
                mqDTO.setMouseFilter(node.getPropertyValue());
            }
            if ( 0 == node.getPropertyName().trim().compareTo(sexEntity)) {
                // Create Entity
                CvSexEntity se = new CvSexEntity();
                // Update Entity
                se.setSex(node.getPropertyValue());
                se.setSexKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                sexEntityList.add(se);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(ageFromEntity)) {
                mqDTO.setAgeFrom(Integer.parseInt(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(ageToEntity)) {
                mqDTO.setAgeTo(Integer.parseInt(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(ageFilter)) {
                mqDTO.setAgeFilter(node.getPropertyValue());
            }
            if ( 0 == node.getPropertyName().trim().compareTo(ageMeasure)) {
                mqDTO.setAgeMeasure(node.getPropertyValue());
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseStartDOBEntity)) {
                mqDTO.setDobStartDate(zDateformatter.parse(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseEndDOBEntity)) {
                mqDTO.setDobEndDate(zDateformatter.parse(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(exitDateStartEntity)) {
                mqDTO.setExitStartDate(zDateformatter.parse(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(exitDateEndEntity)) {
                mqDTO.setExitEndDate(zDateformatter.parse(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(breedingStatusEntity)){
                // Create Entity
                CvBreedingStatusEntity bse = new CvBreedingStatusEntity();
                // Update Entity
                bse.setBreedingStatus(node.getPropertyValue());
                bse.setBreedingStatuskey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                breedingStatusEntityList.add(bse);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(originEntity)) {
                // Create Entity
                CvMouseOriginEntity oe = new CvMouseOriginEntity();
                // Update Entity
                oe.setMouseOrigin(node.getPropertyValue());
                oe.setMouseOriginkey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                originEntityList.add(oe);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(lifeStatusEntity)) {
                // Create Entity
                LifeStatusEntity lse = new LifeStatusEntity();
                // Update Entity
                lse.setLifeStatus(node.getPropertyValue());
                lse.setLifeStatuskey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                lifeStatusEntityList.add(lse);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(strainEntity)) {
                // Create Entity
                StrainEntity se = new StrainEntity();
                // Update Entity
                se.setStrainName(node.getPropertyValue());
                se.setStrainKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                strainEntityList.add(se);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(roomEntity)) {
                // Create Entity
                RoomEntity re = new RoomEntity();
                // Update Entity
                re.setRoomName(node.getPropertyValue());
                re.setRoomKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                roomEntityList.add(re);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(generationEntity)) {
                // Create Entity
                CvGenerationEntity ge = new CvGenerationEntity();
                // Update Entity
                ge.setGeneration(node.getPropertyValue());
                ge.setGenerationKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                generationEntityList.add(ge);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(ownerEntity)) {
                // Create Entity
                OwnerEntity oe = new OwnerEntity();
                // Update Entity
                oe.setOwner(node.getPropertyValue());
                oe.setOwnerKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                ownerEntityList.add(oe);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(causeOfDeathEntity)) {
                // Create Entity
                CvCauseOfDeathEntity cod = new CvCauseOfDeathEntity();
                // Update Entity
                cod.setCod(node.getPropertyValue());
                cod.setCauseOfDeathkey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                causeOfDeathEntityList.add(cod);
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

            // litter ID
            if ( 0 == node.getPropertyName().trim().compareTo(litterID)) {
                // Add
                mqDTO.setLitterID(node.getPropertyValue());
            }

            if ( 0 == node.getPropertyName().trim().compareTo(litterFilter)) {
                // Add
                mqDTO.setLitterFilter(node.getPropertyValue());
            }

            if ( 0 == node.getPropertyName().trim().compareTo(litterEntity)) {
                // Create Entity
                LitterEntity le = new LitterEntity();
                // Update Entity
                le.setLitterID(node.getPropertyValue());
                le.setLitterKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                litterEntityList.add(le);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(containerEntity)) {
                // Create Entity
                ContainerEntity ce = new ContainerEntity();

                // Update Entity
                ce.setContainerID(Integer.parseInt(node.getPropertyValue()));
                ce.setContainerKey(Integer.parseInt(node.getPropertyKey()));

                // Add to List
                containerEntityList.add(ce);
            }
            if (0 == node.getPropertyName().trim().compareTo(useScheduleTerm)) {
                // Create Entity
                UseScheduleTermDTO pe = new UseScheduleTermDTO();
                // Update Entity
                pe.setUseScheduleTermName(node.getPropertyValue());
                pe.setUseScheduleTermKey(node.getPropertyKey());
                // Add to List
                useScheduleTermsList.add(pe);
            }
            if (0 == node.getPropertyName().trim().compareTo(phenotypeTerm)) {
                // Create Entity
                cvPhenotypeTermDTO pte = new cvPhenotypeTermDTO();
                // Update Entity
                pte.setPhenotypeTermName(node.getPropertyValue());
                pte.setPhenotypeTermKey(node.getPropertyKey());
                // Add to List
                phenotypeTermsList.add(pte);
            }
            if (0 == node.getPropertyName().trim().compareTo(protocolEntity)) {
                // Create Entity
                CvMouseProtocolEntity pe = new CvMouseProtocolEntity();
                // Update Entity
                pe.setId(node.getPropertyValue());
                pe.setMouseProtocolkey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                protocolEntityList.add(pe);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(mouseUseEntity)) {
                // Create Entity
                CvMouseUseEntity mue = new CvMouseUseEntity();
                // Update Entity
                mue.setMouseUse(node.getPropertyValue());
                mue.setMouseUsekey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                mouseUseEntityList.add(mue);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(geneEntity)) {
                // Create Entity
                GeneEntity ge = new GeneEntity();
                // Update Entity
                ge.setGeneSymbol(node.getPropertyValue());
                ge.setGeneKey(Integer.parseInt(node.getPropertyKey()));
                // Add to List
                geneEntityList.add(ge);
            }
            if ( 0 == node.getPropertyName().trim().compareTo(genotypeStartDateEntity)) {
                mqDTO.setGenotypeStartDate(zDateformatter.parse(node.getPropertyValue()));
            }
            if ( 0 == node.getPropertyName().trim().compareTo(genotypeEndDateEntity)) {
                mqDTO.setGenotypeEndDate(zDateformatter.parse(node.getPropertyValue()));
            }
            counter++;
        }

        mqDTO.setBreedingStatus(breedingStatusEntityList);
        mqDTO.setOrigin(originEntityList);
        mqDTO.setLifeStatus(lifeStatusEntityList);
        mqDTO.setStrain(strainEntityList);
        mqDTO.setGeneration(generationEntityList);
        mqDTO.setOwner(ownerEntityList);
        mqDTO.setCauseOfDeath(causeOfDeathEntityList);
        mqDTO.setPenName(containerEntityList);
        mqDTO.setPenNumber(containerEntityList);
        mqDTO.setUseScheduleTerms(useScheduleTermsList);
        mqDTO.setPhenotypeTerms(phenotypeTermsList);
        mqDTO.setProtocolID(protocolEntityList);
        mqDTO.setMouseUse(mouseUseEntityList);
        mqDTO.setGenotype(geneEntityList);
        mqDTO.setLitterNumber(litterEntityList);
        mqDTO.setSex(sexEntityList);
        mqDTO.setRooms(roomEntityList);

        return mqDTO;
    }

    protected String dto2XML(MouseQueryDTO search) {
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList();
        SimpleXMLEntity sxp = null;

        SimpleDateFormat zDateformatter;
        zDateformatter = new SimpleDateFormat("yyyy-MM-dd");

        // Mouse ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseID);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseID()));
        sxpl.addProperty(sxp);


        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseStrain);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseStrain()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseLifeStatus);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseLifeStatus()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseBreedingStatus);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseBreedingStatus()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseGeneration);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseGeneration()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseOwner);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseOwner()));
        sxpl.addProperty(sxp);
                
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseOrigin);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseOrigin()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseSex);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseSex()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseCOD);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseCOD()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseDOB);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseDOB()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseExitDate);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseExitDate()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseLitter);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseLitter()));
        sxpl.addProperty(sxp);


        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMousePenID);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMousePenID()));
        sxpl.addProperty(sxp);


        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMousePenName);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMousePenName()));
        sxpl.addProperty(sxp);


        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseUse);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseUse()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseGenotype);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseGenotype()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseGenotypeDate);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseGenotypeDate()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseAge);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseAge()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseMating);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseMating()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseParents);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseParents()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseCoatColor);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseCoatColor()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseDiet);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseDiet()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseProtocolID);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseProtocolID()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectUseSchedules);
        sxp.setPropertyValue(Boolean.toString(search.isSelectUseSchedules()));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMousePhenotypes);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMousePhenotypes()));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseComment);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseComment()));
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseLitterMates);
        sxp.setPropertyValue(Boolean.toString(search.isSelectMouseLitterMates()));
        sxpl.addProperty(sxp);

        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(selectMouseRoom);
        sxp.setPropertyValue(Boolean.toString(search.isSelectRoomName()));
        sxpl.addProperty(sxp);
        
        int counter = 0;

        // Mouse ID From
        if ( null != search.getMouseIDFrom() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(mouseIDFromEntity);
            sxp.setPropertyValue(search.getMouseIDFrom().getId().trim());
            sxp.setPropertyKey(Integer.toString(search.getMouseIDFrom().getMouseKey()));
            sxpl.addProperty(sxp);
        }

        // Mouse ID To
        if ( null != search.getMouseIDTo() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(mouseIDToEntity);
            sxp.setPropertyValue(search.getMouseIDTo().getId().trim());
            sxp.setPropertyKey(Integer.toString(search.getMouseIDTo().getMouseKey()));
            sxpl.addProperty(sxp);
        }

        // Mouse ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(mouseID);
        sxp.setPropertyValue(search.getMouseID());
        sxpl.addProperty(sxp);

        // Mouse ID Filter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(mouseIDFilter);
        sxp.setPropertyValue(search.getMouseFilter());
        sxpl.addProperty(sxp);

        counter = 0;
        // Sex - List
        List<CvSexEntity> sexList = search.getSex();
        if ( null != sexList ) {
            while ( counter < sexList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(sexEntity);
                sxp.setPropertyValue( (sexList.get(counter)).getSex().trim() );
                sxp.setPropertyKey(Integer.toString( (sexList.get(counter)).getSexKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Age - Between, Greater, Less, Equals - Exclusive Option
        // ageFrom
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(ageFromEntity);
        sxp.setPropertyValue(Integer.toString(search.getAgeFrom()));
        sxpl.addProperty(sxp);
        // ageTo
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(ageToEntity);
        sxp.setPropertyValue(Integer.toString(search.getAgeTo()));
        sxpl.addProperty(sxp);
        // ageFilter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(ageFilter);
        sxp.setPropertyValue(search.getAgeFilter());
        sxpl.addProperty(sxp);
        // ageMeasure
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(ageMeasure);
        sxp.setPropertyValue(search.getAgeMeasure());
        sxpl.addProperty(sxp);


        // Date of Birth Start
        if ( null != search.getDobStartDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(mouseStartDOBEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getDobStartDate()).trim());
            sxpl.addProperty(sxp);
        }


        // Date of Birth End
        if ( null != search.getDobEndDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(mouseEndDOBEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getDobEndDate()).trim());
            sxpl.addProperty(sxp);
        }


        // Exit Date - Start
        if ( null != search.getExitStartDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(exitDateStartEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getExitStartDate()).trim());
            sxpl.addProperty(sxp);
        }

        // Exit Date - End
        if ( null != search.getExitEndDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(exitDateEndEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getExitEndDate()).trim());
            sxpl.addProperty(sxp);
        }

        // Breeding Status - List
        counter = 0;
        List<CvBreedingStatusEntity> breedingStatus = search.getBreedingStatus();
        if ( null != breedingStatus ) {
            while ( counter < breedingStatus.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(breedingStatusEntity);
                sxp.setPropertyValue( (breedingStatus.get(counter)).getBreedingStatus().trim() );
                sxp.setPropertyKey(Integer.toString(  ( breedingStatus.get(counter)).getBreedingStatuskey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Origin - List
        counter = 0;
        List<CvMouseOriginEntity> mouseOriginList = search.getOrigin();
        if ( null != mouseOriginList ) {
            while ( counter < mouseOriginList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(originEntity);
                sxp.setPropertyValue( (mouseOriginList.get(counter)).getMouseOrigin().trim() );
                sxp.setPropertyKey(Integer.toString( (mouseOriginList.get(counter)).getMouseOriginkey()  ));
                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Life Status - List
        counter = 0;
        List<LifeStatusEntity> lifeStatusList = search.getLifeStatus();
        if ( null != lifeStatusList ) {
            while ( counter < lifeStatusList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(lifeStatusEntity);
                sxp.setPropertyValue( (lifeStatusList.get(counter)).getLifeStatus().trim() );
                sxp.setPropertyKey(Integer.toString(  (lifeStatusList.get(counter)).getKey()));

                sxpl.addProperty(sxp);
                counter++;
            }
        }


        // Strain - List
        counter = 0;
        List<StrainEntity> seList = search.getStrain();
        if ( null != seList ) {
            while ( counter < seList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(strainEntity);
                sxp.setPropertyValue( (seList.get(counter)).getStrainName().trim() );
                sxp.setPropertyKey(Integer.toString(  (seList.get(counter)).getStrainKey()  ));
                sxpl.addProperty(sxp);
                counter++;
            }
        }
        
       
        //Room - List
        counter = 0;
        List<RoomEntity> roomList = search.getRooms();
        if ( null != roomList ) {
            while ( counter < roomList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(roomEntity);
                sxp.setPropertyValue( (roomList.get(counter)).getRoomName().trim() );
                sxp.setPropertyKey(Integer.toString(  (roomList.get(counter)).getRoomKey()));
                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Generation - List
        counter = 0;
        List<CvGenerationEntity> geList = search.getGeneration();
        if ( null != geList ) {
            while ( counter < geList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(generationEntity);
                sxp.setPropertyValue( ( geList.get(counter)).getGeneration().trim() );
                sxp.setPropertyKey(Integer.toString(  ( geList.get(counter)).getGenerationKey()  ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }


        // Owner - List
        counter = 0;
        List<OwnerEntity> oList = search.getOwner();
        if ( null != oList ) {
            while ( counter < oList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(ownerEntity);
                sxp.setPropertyValue( ( oList.get(counter)).getOwner().trim() );
                sxp.setPropertyKey(Integer.toString(  ( oList.get(counter)).getOwnerKey()  ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Cause of Death - List
        counter = 0;
        List<CvCauseOfDeathEntity> codList = search.getCauseOfDeath();
        if ( null != codList ) {
            while ( counter < codList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(causeOfDeathEntity);
                sxp.setPropertyValue( ( codList.get(counter)).getCod().trim() );
                sxp.setPropertyKey(Integer.toString( (codList.get(counter)).getKey()  ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }


        // Litter # - List
        counter = 0;
        List<LitterEntity> lList = search.getLitterNumber();
        if ( null != lList ) {
            while ( counter < lList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(litterEntity);
                sxp.setPropertyValue( (lList.get(counter)).getLitterID().trim() );
                sxp.setPropertyKey(Integer.toString( ( lList.get(counter)).getLitterKey() ));
                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Litter ID
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(litterID);
        sxp.setPropertyValue(search.getLitterID());
        sxpl.addProperty(sxp);

        // Litter ID Filter
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName(litterFilter);
        sxp.setPropertyValue(search.getLitterFilter());
        sxpl.addProperty(sxp);

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

        // Pen ID - List
        counter = 0;
        List<ContainerEntity> cidList = search.getPenNumber();
        if ( null != cidList ) {
            while ( counter < cidList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(containerEntity);
                sxp.setPropertyValue( Integer.toString(( cidList.get(counter)).getContainerID()) );
                sxp.setPropertyKey(Integer.toString( ( cidList.get(counter)).getContainerKey()  ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Pen Name - List
        counter = 0;
        List<ContainerEntity> cndList = search.getPenName();
        if ( null != cndList ) {
            while ( counter < cndList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(containerEntity);
                sxp.setPropertyValue( ( cndList.get(counter)).getContainerName() );
                sxp.setPropertyKey(Integer.toString( ( cidList.get(counter)).getContainerKey() ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }


        // use schedule term - List
        counter = 0;
        List<UseScheduleTermDTO> ustList = search.getUseScheduleTerms();
        if ( null != ustList ) {
            while ( counter < ustList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(useScheduleTerm);
                sxp.setPropertyValue( ( ustList.get(counter)).getUseScheduleTermName() );
                sxp.setPropertyKey((ustList.get(counter)).getUseScheduleTermKey());

                sxpl.addProperty(sxp);
                counter++;
            }
        }
        
        // phenotype term - List
        counter = 0;
        List<cvPhenotypeTermDTO> ptList = search.getPhenotypeTerms();
        if ( null != ptList ) {
            while ( counter < ptList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(phenotypeTerm);
                sxp.setPropertyValue( ( ptList.get(counter)).getPhenotypeTermName() );
                sxp.setPropertyKey((ptList.get(counter)).getPhenotypeTermKey());

                sxpl.addProperty(sxp);
                counter++;
            }
        }
        
        // Protocol ID - List
        counter = 0;
        List<CvMouseProtocolEntity> cmpList = search.getProtocolID();
        if ( null != cmpList ) {
            while ( counter < cmpList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(protocolEntity);
                sxp.setPropertyValue( ( cmpList.get(counter)).getId() );
                sxp.setPropertyKey(Integer.toString((cmpList.get(counter)).getKey()));

                sxpl.addProperty(sxp);
                counter++;
            }
        }



       // Mouse Use - List
       counter = 0;
       List<CvMouseUseEntity> muList = search.getMouseUse();
       if ( null != muList ) {
            while ( counter < muList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(mouseUseEntity);
                sxp.setPropertyValue( (muList.get(counter)).getMouseUse().trim() );
                sxp.setPropertyKey(Integer.toString( (muList.get(counter)).getMouseUsekey() ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Genotype - List
       counter = 0;
       List<GeneEntity> gList = search.getGenotype();
       if ( null != gList ) {
            while ( counter < gList.size() ){
                sxp = new SimpleXMLEntity();
                sxp.setPropertyName(geneEntity);
                sxp.setPropertyValue( (gList.get(counter)).getGeneSymbol().trim() );
                sxp.setPropertyKey(Integer.toString( ( gList.get(counter)).getGeneKey() ));

                sxpl.addProperty(sxp);
                counter++;
            }
        }

        // Genotype Start Date
        if ( null != search.getGenotypeStartDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(genotypeStartDateEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getGenotypeStartDate()).trim());
            sxpl.addProperty(sxp);
        }

        // Genotype End Date
        if ( null != search.getGenotypeEndDate() ) {
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(genotypeEndDateEntity);
            sxp.setPropertyValue(zDateformatter.format(search.getGenotypeEndDate()).trim());
            sxpl.addProperty(sxp);
        }

        return sxpl.getXMLString();
    }



}