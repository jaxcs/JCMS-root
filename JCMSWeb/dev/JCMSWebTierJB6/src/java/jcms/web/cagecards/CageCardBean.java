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

package jcms.web.cagecards;

import java.io.DataOutputStream;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.dtos.returnDTO;
import java.io.DataOutput;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.dto.CageCardDTO;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import jcms.web.base.WTBaseBackingBean;
import jcms.cagecard.cardtypes.FormatType;
import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.util.List;
import javax.faces.model.SelectItem;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardtypes.Orientation;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.web.common.SelectItemWrapper;
import jcms.middletier.businessobject.CageCardBO;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.exception.SaveException;
import jcms.middletier.service.SaveAppService;

/**
 *
 * @author mkamato
 */
public class CageCardBean extends WTBaseBackingBean{

    
    //error messages
    private String weanCardErrorMessage = "The requested card could not be printed as requested. Please make sure the requested cage"
            + " contains mice of the same strain";
    private String twoPenWeanCardErrorMessage = "The requested card could not be printed as requested. Please make sure the requested cage"
            + " contains mice of the same strain";
    private String matingCardErrorMessage = "The requested card could not be printed as requested. Please make sure the requested cage"
            + " contains 1 or 2 female mice and 0 or 1 male mice";
    private String detailCardErrorMessage = "You should never see this";
    
    //object
    private ServiceFacadeLocal sfl = new BusinessTierPortal().getServiceFacadeLocal();
    private CageCardDTO theDTO;
    
    //fields for DTO
    private String cageID;
    private String cageID2;
    private CageCardType cardType;
    private String cardQuantity = "single";
    private ArrayList<CustomCardField> customCard;
    private int numberOfCards = 1;
    
    //communicates between CC and JSP
    private boolean goodCard;
    private String cardTypeMask = "detail";
    private boolean notBlank = true;
    private ArrayList<String> badCards;
    private boolean printBlankCards = false;
    
    private QueryDefinitionEntity entity;
    private Integer entityKey;
    
    
    private SelectItemWrapper theWrapper = new SelectItemWrapper();
    private ArrayList<SelectItem> displayCards = initializeCards();
    
    
    public void changeCardListener() throws Exception{
        theWrapper = new SelectItemWrapper();        
        if(cardTypeMask.equals("detail")){
            setDisplayCards(theWrapper.getDetailCards());
        }
        else if(cardTypeMask.equals("mating")){
            setDisplayCards(theWrapper.getMatingCards());
        }
        else{
            setDisplayCards(theWrapper.getWeanCards());
        }
    }
    
    public ArrayList<SelectItem> initializeCards() {
        try {
            theWrapper = new SelectItemWrapper();
            if (cardTypeMask.equals("detail")) {
                return theWrapper.getDetailCards();
            } else if (cardTypeMask.equals("mating")) {
                return theWrapper.getMatingCards();
            } else {
                return theWrapper.getWeanCards();
            }
        } catch (Exception e) {
            this.getLogger().logError("Something went terribly wrong: " + e);
            return new ArrayList<SelectItem>();
        }
    }
    
    public void validateCageCardAction() throws Exception {
        try {
            boolean flag = false;
            setTheDTO(new CageCardDTO());

            setGoodCard(false);
            //set the card type
            if (cardTypeMask.equals("detail")) {
                setCardType(CageCardType.Detail);
            } 
            else if (cardTypeMask.equals("wean")) {
                setCardType(CageCardType.Wean);
            } 
            else if (cardTypeMask.equals("mating")) {
                setCardType(CageCardType.Mating);
            } 
            else if (cardTypeMask.equals("2PWean")) {
                setCardType(CageCardType.TwoPenWean);
            } 
            else {
                System.out.println("Something went terribly wrong...");
                return;
            }
            //need a cageID
            if (cageID == null || cageID.equals("")) {
                this.addToMessageQueue("Cage ID is missing. Please enter a Cage ID.",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Cage ID is missing. Please enter a Cage ID."));
                flag = true;
            }
            //need to select a card.
            if (this.entityKey == 0) {
                this.addToMessageQueue("Card Name is missing. Please enter a Card Name.",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Card Name is missing. Please enter a Card Name."));
                flag = true;
            }
            //printing multiple cards second cage id is required.
            if ((cageID2 == null || cageID2.equals("")) && cardQuantity.equals("multiple")) {
                this.addToMessageQueue("Second Cage ID is missing. Please enter a second Cage ID.",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Second Cage ID is missing. Please enter a second Cage ID."));
                flag = true;
            }
            //if printing multiple cards range must be < 50
            if (cardQuantity.equals("multiple") && (cageID2 != null && cageID2 != "")) {
                if (Integer.parseInt(cageID2) <= Integer.parseInt(cageID)) {
                    String temp = cageID;
                    cageID = cageID2;
                    cageID2 = temp;
                    getTheDTO().setPenID(cageID);
                    getTheDTO().setPenID2(cageID2);
                }
                if (Math.abs(Integer.parseInt(cageID) - Integer.parseInt(cageID2)) > 50) {
                    this.addToMessageQueue("Range of cages must be fewer than 50.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Range of cages must be fewer than 50."));
                    flag = true;
                }
            }

            if (flag) {
                return;
            }

            getTheDTO().setCageCardType(cardType);
            getTheDTO().setPenID(cageID);
            getTheDTO().setQuantity(cardQuantity);
            
            if (getCustomCard() != null) {
                getTheDTO().setCustomCard(getCustomCard());
            }
            if (cardQuantity.equals("multiple") && (cageID2 != null && !cageID2.equals(""))) {
                Integer.parseInt(cageID2);
                if (Integer.parseInt(cageID2) < 0) {
                    this.addToMessageQueue("Cage IDs must be positive integers.",
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            "Cage IDs must be positive integers."));
                    return;
                }
                getTheDTO().setPenID2(cageID2);
            }

            Integer.parseInt(cageID);

            if (Integer.parseInt(cageID) < 0) {
                this.addToMessageQueue("Cage IDs must be positive integers.",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Cage IDs must be positive integers."));
                return;
            }

            if (sfl.CageCardValidation(getTheDTO()) && notBlank) {
                if (cardType.equals(cardType.Detail)) {
                    this.addToMessageQueue(detailCardErrorMessage,
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            detailCardErrorMessage));
                } 
                else if (cardType.equals(cardType.Wean)) {
                    this.addToMessageQueue(weanCardErrorMessage,
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            weanCardErrorMessage));
                } 
                else if (cardType.equals(cardType.Mating)) {
                    this.addToMessageQueue(matingCardErrorMessage,
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            matingCardErrorMessage));
                }
                else {
                    this.addToMessageQueue(twoPenWeanCardErrorMessage,
                            FacesMessage.SEVERITY_ERROR);
                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                            twoPenWeanCardErrorMessage));
                }
                return;
            }
            setGoodCard(true);
        } catch (NumberFormatException e) {
            this.addToMessageQueue("Cage IDs must be integers. Please provide valid Cage IDs.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Cage IDs must be integers. Please provide valid Cage IDs."));
            return;
        } catch (Exception e) {
            System.out.println("There was an exception: " + e);
        }
    }

    public void downloadCageCardAction() {
        

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                
                initializeDTOForDownload();
                
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");

                returnDTO theReturnDTO = sfl.generateCageCard(getTheDTO(), hsr);
                
                
                writeBytes(theReturnDTO,hsr,context);
                
                if(cardQuantity == null){
                    cardQuantity = "single";
                }
                if (!cardQuantity.equals("single")) {
                    badCards = theReturnDTO.getBadCards();
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in cage card bean: " + ex);
        }
    }
    
    public void externalDownloadCageCard(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                
               // initializeDTOForDownload();
                
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");

                returnDTO theReturnDTO = sfl.generateCageCard(getTheDTO(), hsr);
                
                
                writeBytes(theReturnDTO,hsr,context);
                
                if(cardQuantity == null){
                    cardQuantity = "single";
                }
                if (!cardQuantity.equals("single")) {
                    badCards = theReturnDTO.getBadCards();
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in cage card bean: " + ex);
        }
    }

    public String validateAndDownloadCageCardAction() throws Exception {
        if (cardQuantity.equals("single")) {
            validateCageCardAction();
            if (goodCard) {
                downloadCageCardAction();
            }
            return "";
        } else {
            downloadMultipleCards();
            return "";
        }
    }

    private void writeBytes(returnDTO theReturnDTO, HttpServletResponse hsr, FacesContext context) throws Exception {
        //no error
        byte[] bytes = theReturnDTO.getBytes();
        if (bytes != null && bytes.length != 0) {
            DataOutput output = new DataOutputStream(hsr.getOutputStream());
            hsr.setContentLength(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                output.writeByte(bytes[i]);
            }
        } else {
            this.addToMessageQueue(theReturnDTO.getErrorMessage(),
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    ""));
        }
        //Tell JSF to skip the remaining phases of the lifecycle 
        context.responseComplete();
    }
    
    public void changeMaxPenId() {
        if(numberOfCards <= 0 || numberOfCards > 99){
                this.addToMessageQueue("Number of cards must be a number between 1 and 99.",
                    FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Number of cards must be a number between 1 and 99."));
                return;
        }
        JCMSDbInfoEntity dbInfoEntity = new JCMSDbInfoEntity();
        List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();

        int cageNumber;


        // get the dbinfo record
        info = new ListSupportDTO().getJCMSDbInfo();
        if (info.size() > 0) {
            dbInfoEntity = info.get(0);
        }

        cageNumber = dbInfoEntity.getMaxPenID() + numberOfCards;

        cageID = new Integer(dbInfoEntity.getMaxPenID() + 1).toString();
        cageID2 = new Integer(cageNumber).toString();

        dbInfoEntity.setMaxPenID(cageNumber);
        dbInfoEntity.setIsDirty();

        try {
            new SaveAppService().baseEdit(dbInfoEntity);
            this.getLogger().logInfo(this.formatLogMessage("save",
                    "DbInfo.maxPenId " + dbInfoEntity.getMaxPenID() + " has been updated"));
        } catch (SaveException se) {
            String msg = se.getMessage();
            addToMessageQueue(msg, FacesMessage.SEVERITY_ERROR);
            this.getLogger().logError(this.formatLogMessage(msg, "saveAction: " + se));
        }
    }

    
    public void downloadBlankCardAction() {
        try {
            String temp = cardQuantity;
            cardQuantity = "multiple";
            printBlankCards = true;
            
            downloadMultipleCards();
            cardQuantity = temp;
            printBlankCards = false;
        } catch (Exception e) {
            this.addToMessageQueue("Exception printing blank cards: " + e,
                    FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("error ",
                    "Exception printing blank cards: " + e));
        }
    }
    
    
    private void downloadMultipleCards() throws Exception {
        try {
            if (cardTypeMask.equals("detail")) {
                setCardType(CageCardType.Detail);
            } 
            else if (cardTypeMask.equals("wean")) {
                setCardType(CageCardType.Wean);
            } 
            else if (cardTypeMask.equals("mating")) {
                setCardType(CageCardType.Mating);
            } 
            else if (cardTypeMask.equals("2PWean")) {
                setCardType(CageCardType.TwoPenWean);
            } 
            else {
                System.out.println("Something went terribly wrong...");
                return;
            }

            if (cageID2 == null || cageID2.equals("")
                    || cageID == null || cageID.equals("")) {
                this.addToMessageQueue("Upper Cage ID is missing. Please select an upper Cage ID.",
                        FacesMessage.SEVERITY_ERROR);
                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "Upper Cage ID is missing. Please select an upper Cage ID."));
                return;
            }

            if (Integer.parseInt(cageID2) <= Integer.parseInt(cageID)) {
                String temp = cageID;
                cageID = cageID2;
                cageID2 = temp;
                getTheDTO().setPenID(cageID);
                getTheDTO().setPenID2(cageID2);
            }

            downloadCageCardAction();
        } catch (NumberFormatException e) {
            this.addToMessageQueue("Cage IDs must be integers. Please provide valid Cage IDs.",
                    FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Cage IDs must be integers. Please provide valid Cage IDs."));
        }
    }


    public void displayCardsNotPrinted() {
        System.out.println(cardQuantity);
        if (!cardQuantity.equals("single")) {
            int count = 0;
            while (badCards == null) {
                System.out.println(); //DON'T COMMENT THIS OUT, FOR SOME REASON CODE IS BROKEN WITHOUT THIS.
            }
            System.out.println(count);
            String theBadCards = "";

            if (badCards.size() > 0) {
                theBadCards = badCardBuilder();
                if(cardType.equals(CageCardType.Mating)){
                    System.out.println("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards 
                            + ". Cage must contain 0 or 1 male mice, and 1 or 2 females.");
                    this.addToMessageQueue("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards
                            + ". Cage must contain 0 or 1 male mice, and 1 or 2 females.", FacesMessage.SEVERITY_WARN);
                }
                else if(cardType.equals(CageCardType.Wean)){
                    System.out.println("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards 
                            + ". Cage must contain 0 or 1 male mice, and 1 or 2 females.");
                    this.addToMessageQueue("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards
                            + ". Cage must contain mice of the same strain.", FacesMessage.SEVERITY_WARN);
                }
                else{
                     System.out.println("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards);
                    this.addToMessageQueue("The following cards could not be printed, check that the"
                            + " cages meet the criteria to be a cage of the selected type: " + theBadCards, FacesMessage.SEVERITY_WARN);
                }
            }
            badCards = null;
        }
    }
    
    public String badCardBuilder() {
        String cardGroups = "";
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(badCards.get(0));
        int idx = 0;
        int previous = Integer.parseInt(badCards.get(0));
        if (badCards.size() == 1) {
            return badCards.get(0);
        }
        for (String s : badCards) {
            System.out.println(s);
            if (idx != 0) {
                int current = Integer.parseInt(s);
                if (current - previous <= 1) {
                    temp.add(s);
                    idx++;
                } else {
                    if (temp.size() > 1) {
                        if (cardGroups.equals("")) {
                            cardGroups = cardGroups + temp.get(0) + "-" + temp.get(temp.size() - 1);
                        } else {
                            cardGroups = cardGroups + ", " + temp.get(0) + "-" + temp.get(temp.size() - 1);
                        }
                        temp.clear();
                        temp.add(s);
                    } else {
                        if (cardGroups.equals("")) {
                            cardGroups = cardGroups + temp.get(0);
                        } else {
                            cardGroups = cardGroups + ", " + temp.get(0);
                        }
                        temp.clear();
                        temp.add(s);
                    }
                }
                previous = current;
            } else {
                idx++;
            }
        }
        if (temp.size() > 1) {
            if (cardGroups.equals("")) {
                cardGroups = cardGroups + temp.get(0) + "-" + temp.get(temp.size() - 1);
            } else {
                cardGroups = cardGroups + ", " + temp.get(0) + "-" + temp.get(temp.size() - 1);
            }
        } else {
            if (cardGroups.equals("")) {
                cardGroups = cardGroups + temp.get(0);
            } else {
                cardGroups = cardGroups + ", " + temp.get(0);
            }
        }
        System.out.println(cardGroups);
        return cardGroups;
    }

    public void initializeDTOForDownload(){
        theDTO = loadCageCardAction();
        theDTO.setBlankCards(printBlankCards);
        customCard = theDTO.getCustomCard();

        ArrayList<WorkgroupEntity> wgLst = ((ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst"));
        ArrayList<String> wgs = new ArrayList<String>();
        
        for(WorkgroupEntity we:wgLst){
            System.out.println("adding " + we.getWorkgroupName() + " to workgroup list...");
            wgs.add(we.getWorkgroupName());
        }
        theDTO.setMyWorkgroups(wgs);
        theDTO.setPenID(cageID);
        theDTO.setQuantity(cardQuantity);
        if(cageID2 != null){
            theDTO.setPenID2(cageID2);
        }
        System.out.println("gets out of initialize");
    }
    
    public CageCardDTO loadCageCardAction() {
        try {
            for(QueryDefinitionEntity e: new ListSupportDTO().getUserDefinedQueries() ){
                if(e.getKey().equals(entityKey)){
                    entity = e;
                }
            }
            CageCardDTO theDTO = new CageCardBO().xmlToDTO(entity.getQueryOptions());
            return theDTO;
        } catch (Exception e) {
            // Display error information
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
            return null;
        }
    }
        
    public void previewCard(){
        cageID = "-100";
        String theCardQuantity = cardQuantity;
        cardQuantity = "single";
        downloadCageCardAction();
        cardQuantity = theCardQuantity;
        cageID = "";
    }
    
    public String getCageID() {
        return this.cageID;
    }

    public void setCageID(String theCageID) {
        this.cageID = theCageID;
    }

    public CageCardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CageCardType theCardType) {
        this.cardType = theCardType;
    }

    public String getCardTypeMask() {
        return this.cardTypeMask;
    }

    public void setCardTypeMask(String theCardTypeMask) {
        this.cardTypeMask = theCardTypeMask;
    }

    protected void addToMessageQueue(String message, FacesMessage.Severity severity) {
        // Display user friendly error message
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(severity);
        facesMessage.setSummary(message);
        this.getFacesContext().addMessage("PlaceHolderClientId", facesMessage);
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * @return the goodCard
     */
    public boolean isGoodCard() {
        return goodCard;
    }

    /**
     * @param goodCard the goodCard to set
     */
    public void setGoodCard(boolean goodCard) {
        this.goodCard = goodCard;
    }

    /**
     * @return the cardQuantity
     */
    public String getCardQuantity() {
        return cardQuantity;
    }

    /**
     * @param cardQuantity the cardQuantity to set
     */
    public void setCardQuantity(String cardQuantity) {
        this.cardQuantity = cardQuantity;
    }

    /**
     * @return the cageID2
     */
    public String getCageID2() {
        return cageID2;
    }

    /**
     * @param cageID2 the cageID2 to set
     */
    public void setCageID2(String cageID2) {
        this.cageID2 = cageID2;
    }
   
    public CageCardDTO getTheDTO(){
        return this.theDTO;
    }

    /**
     * @param theDTO the theDTO to set
     */
    public void setTheDTO(CageCardDTO theDTO) {
        this.theDTO = theDTO;
    }

    /**
     * @return the customCard
     */
    public ArrayList<CustomCardField> getCustomCard() {
        return customCard;
    }

    /**
     * @param customCard the customCard to set
     */
    public void setCustomCard(ArrayList<CustomCardField> customCard) {
        this.customCard = customCard;
    }

    /**
     * @return the displayCards
     */
    public ArrayList<SelectItem> getDisplayCards() throws Exception{
        changeCardListener();
        return displayCards;
    }

    /**
     * @param displayCards the displayCards to set
     */
    public void setDisplayCards(ArrayList<SelectItem> displayCards) {
        this.displayCards = displayCards;
    }

    /**
     * @return the entity
     */
    public QueryDefinitionEntity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(QueryDefinitionEntity entity) {
        this.entity = entity;
    }

    /**
     * @return the entityKey
     */
    public Integer getEntityKey() {
        return entityKey;
    }

    /**
     * @param entityKey the entityKey to set
     */
    public void setEntityKey(Integer entityKey) {
        this.entityKey = entityKey;
    }

    /**
     * @return the numberOfCards
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * @param numberOfCards the numberOfCards to set
     */
    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
