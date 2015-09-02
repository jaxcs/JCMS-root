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

//Integration Tier necessary libraries.
import java.sql.SQLException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import jcms.integrationtier.base.SystemFacadeLocal;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.cagecard.cagecards.CageCard;
import jcms.integrationtier.dto.CageCardDTO;
import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.dtos.returnDTO;
import jcms.cagecard.dtos.*;
import java.util.ArrayList;
import java.util.Date;
import jcms.middletier.base.BTBaseObject;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.cardtypes.FormatType;
import jcms.cagecard.cardtypes.Orientation;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.service.JCMSWebAppService;
import org.jax.cs.utils.simpleXMLentity.SimpleXMLEntity;
import org.jax.cs.utils.simpleXMLentity.SimpleXMLEntityList;
import jcms.integrationtier.dao.CageCardDAO;


/**
 *
 * @author mkamato
 */
public class CageCardBO extends BTBaseObject implements Serializable{
    private static final long serialVersionUID = 1L;

    SystemFacadeLocal portal;
    CageCardResultDTO queryResults;
    String containerID;
    returnDTO theReturnDTO;

    public CageCardBO() {
        portal = new IntegrationTierPortal().getSystemFacadeLocal();
    }

    public returnDTO runQuery(CageCardDTO search, HttpServletResponse HSR) throws SQLException {
        if (search.getQuantity().equals("single")) {
            queryResults = portal.getCageCardQueryResults(search);
            queryResults.setMyWorkgroups(search.getMyWorkgroups());
            queryResults.setOrientation(search.getOrientation());
            queryResults.setBlankCards(search.isBlankCards());
            containerID = search.getPenID();
            if(search.getCustomCard()!=null){
                queryResults.setCustomCard(search.getCustomCard());
            }
            return buildCageCard(HSR);
        }
        else{
            //checks Business rules for each card as well as gets results from DB
            containerID = search.getPenID();
            queryResults = portal.getCageCardQueryResults(search);
            runValidate(search);
            queryResults.setCustomCard(search.getCustomCard());
            queryResults.setOrientation(search.getOrientation());
            queryResults.setMyWorkgroups(search.getMyWorkgroups());
            
            theReturnDTO = buildCageCard(HSR);
            theReturnDTO.setBadCards(queryResults.getBadCards());
            return theReturnDTO;
        }
    }

    public boolean runValidate(CageCardDTO dto) throws SQLException {
        queryResults = portal.getCageCardQueryResults(dto);
        queryResults.setBlankCards(dto.isBlankCards());
        if (queryResults instanceof DetailCardResultDTO) {
            DetailCardResultDTO DCRDTO = (DetailCardResultDTO) queryResults;
            return new CageCard().validate(DCRDTO);
        } 
        else if (queryResults instanceof MatingCardResultDTO) {
            MatingCardResultDTO MCRDTO = (MatingCardResultDTO) queryResults;
            return new CageCard().validate(MCRDTO);
        } 
        else if (queryResults instanceof WeanCardResultDTO) {
            WeanCardResultDTO WCRDTO = (WeanCardResultDTO) queryResults;
            return new CageCard().validate(WCRDTO);
        } 
        else {
            TwoPenWeanCardResultDTO TPWCRDTO = (TwoPenWeanCardResultDTO) queryResults;
            return new CageCard().validate(TPWCRDTO);
        }
    }

    public returnDTO buildCageCard(HttpServletResponse HSR) {
        return new CageCard().makeCard(queryResults, HSR, containerID);
    }
    
    public Integer saveCageCard(CageCardDTO theCard, String cardName, UserEntity user, ArrayList<Integer> wgKeys) throws Exception {
        
        getLogger().logInfo(formatLogMessage("Save Cage Card", "Search Criteria " +
               "details from the middle tier"));
        
        String theXML = dtoToXML(theCard);
        getLogger().logInfo(formatLogMessage("serialize the MatingQueryDTO " +
                    "using XML properties", "SaveQuery "));
        QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
        
        // set the the properties for userdefinedentity
        // Query Name
        udEntity.setQueryName(cardName);
        // Query Options
        udEntity.setQueryOptions(theXML);
        // Query Type
        CvQueryTypeEntity udType = new CvQueryTypeEntity();
        if(theCard.getCageCardType() == CageCardType.Detail){
            udType.setQueryTypekey(3);
        }
        else if(theCard.getCageCardType() == CageCardType.Mating){
            udType.setQueryTypekey(4);
        }
        else{
            udType.setQueryTypekey(5);
        }
        udEntity.setQueryTypekey(udType);
        // Workgroup
        WorkgroupEntity we = new WorkgroupEntity();
        we.setWorkgroupkey(wgKeys.get(0));
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

        // Get next Key
        Integer z =  new JCMSWebAppService().findMaxPrimaryKey(udEntity, new Integer(0));
        if ( null == z ) {
            udEntity.setQueryDefinitionkey(1);
        } else {
            udEntity.setQueryDefinitionkey(z + 1);
        }

        //save cage card
        new JCMSWebAppService().saveQuery(udEntity);
        //link cage cards to workgroups
        getLogger().logInfo(formatLogMessage( "Query Saved .....", "SaveQuery "));
        return udEntity.getQueryDefinitionkey();
    }
    
    public String dtoToXML(CageCardDTO theDTO){       
        
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList();
        SimpleXMLEntity sxp = null;                
        
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName("cardName");
        sxp.setPropertyValue(theDTO.getCardName());
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName("cardType");
        sxp.setPropertyValue(theDTO.getCageCardType().toString());
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName("printSettings");
        sxp.setPropertyValue(theDTO.getFormat().toString());
        sxpl.addProperty(sxp);
        
        sxp = new SimpleXMLEntity();
        sxp.setPropertyName("orientation");
        if (theDTO.getOrientation() != null) {
            sxp.setPropertyValue(theDTO.getOrientation().toString());
        }
        else{
            sxp.setPropertyValue(Orientation.portrait.toString());
        }
        sxpl.addProperty(sxp);
        
        //combine property and theNumber to get different custom card fields
        String propertyName = "customCardField";
        Integer theNumber = new Integer(1);
        
        for(CustomCardField c:theDTO.getCustomCard()){
            propertyName = propertyName + theNumber.toString();
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(c.getFieldName());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(c.getRowPortion());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(new Integer(c.getNumberOfRows()).toString());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(new Boolean(c.isBarCoded()).toString());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(new Boolean(c.isLabeled()).toString());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            sxp.setPropertyValue(new Boolean(c.isBorders()).toString());
            sxpl.addProperty(sxp);
            
            sxp = new SimpleXMLEntity();
            sxp.setPropertyName(propertyName);
            if(c.getCustomText() == null || c.getCustomText().equals("")){
                sxp.setPropertyValue("whitespace");
            }else{
                sxp.setPropertyValue(c.getCustomText());
            }
            sxpl.addProperty(sxp);
            
            theNumber++;
        }
        
        return sxpl.getXMLString();
    }
    
    
    /*
     * Converts the xml from the DB Back to the DTO
     */
    public CageCardDTO xmlToDTO(String theXML){
        
        SimpleXMLEntityList sxpl = new SimpleXMLEntityList(theXML);
        CageCardDTO CCDTO = new CageCardDTO();
        
        
        ArrayList<SimpleXMLEntity> propertyArray = sxpl.getPList();
        ArrayList<CustomCardField> CustomCard = new ArrayList<CustomCardField>();
        int idx = 0;
        
        while(idx < propertyArray.size()){
            SimpleXMLEntity sxp = propertyArray.get(idx);
            if(sxp.getPropertyName().equals("cardType")){
                if (sxp.getPropertyValue().equals("Wean")) {
                    CCDTO.setCageCardType(CageCardType.Wean);
                }
                else if (sxp.getPropertyValue().equals("Mating")) {
                    CCDTO.setCageCardType(CageCardType.Mating);
                }
                else {
                    CCDTO.setCageCardType(CageCardType.Detail);
                }
            }
            else if(sxp.getPropertyName().equals("printSettings")){
                if(sxp.getPropertyValue().equals("threeByFive")){
                    CCDTO.setFormat(FormatType.threeByFive);
                }
                else if(sxp.getPropertyValue().equals("alignLeft")){
                    CCDTO.setFormat(FormatType.alignLeft);
                }
                else if(sxp.getPropertyValue().equals("alignRight")){
                    CCDTO.setFormat(FormatType.alignRight);
                }
                else {
                    CCDTO.setFormat(FormatType.alignCenter);
                }
            }
            else if(sxp.getPropertyName().equals("orientation")){
                if (sxp.getPropertyValue().equals("portrait")) {
                    CCDTO.setOrientation(Orientation.portrait);
                }
                else{
                    CCDTO.setOrientation(Orientation.landscape);
                }
            }
            else if(sxp.getPropertyName().equals("cardName")){
                CCDTO.setCardName(sxp.getPropertyValue());
            }
            else{
                CustomCardField theField = new CustomCardField();
                
                theField.setFieldName(sxp.getPropertyValue());
                idx++;
                
                sxp = propertyArray.get(idx);
                theField.setRowPortion(sxp.getPropertyValue());
                idx++;
                
                sxp = propertyArray.get(idx);
                theField.setNumberOfRows(Integer.parseInt(sxp.getPropertyValue()));
                idx++;
                
                sxp = propertyArray.get(idx);
                theField.setBarCoded(Boolean.parseBoolean(sxp.getPropertyValue()));
                idx++;
                
                sxp = propertyArray.get(idx);
                theField.setLabeled(Boolean.parseBoolean(sxp.getPropertyValue()));
                idx++;
                
                sxp = propertyArray.get(idx);
                theField.setBorders(Boolean.parseBoolean(sxp.getPropertyValue()));   
                idx++;
                
                sxp = propertyArray.get(idx);
                if(sxp.getPropertyValue().equals("whitespace")){
                    theField.setCustomText("");
                }
                else{
                    theField.setCustomText(sxp.getPropertyValue());
                }
                CustomCard.add(theField);
            }
            idx++;
        }
        CCDTO.setCustomCard(CustomCard);
        return CCDTO;
    }
    
    public CageCardDTO loadCageCard(Integer key){
        try{
        QueryDefinitionEntity udEntity = new QueryDefinitionEntity();
        udEntity = new JCMSWebAppService().loadQueryByKey(key);
        String xmlString = udEntity.getQueryOptions();
        CageCardDTO ccDTO = this.xmlToDTO(xmlString);
        // de-serialize the query from entity into DTO
        return ccDTO;   
        }
        catch(Exception e){
            System.out.println("Exception in LoadCard: "+ e);
            return null;
        }
    }
}
