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

package jcms.integrationtier.dto;

import jcms.cagecard.cardtypes.FormatType;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardtypes.CageCardType;
import jcms.cagecard.cardtypes.Orientation;
import java.util.ArrayList;

/**
 *
 * @author mkamato
 */
public class CageCardDTO {
    
    private CageCardType cct = null;
    private String cardName;
    private String PenID;
    private String PenID2;
    private FormatType format; //3x5, leftaligned right center etc.
    private String quantity;
    private ArrayList<String> myWorkgroups = new ArrayList<String>(); //workgroups requester belongs to.
    private ArrayList<CustomCardField> customCard = null;
    private Orientation orientation;
    private boolean blankCards;
    
    public void setCageCardType(CageCardType theType){
        cct = theType;
    }
    
    public CageCardType getCageCardType(){
        return this.cct;
    }
    
    public void setPenID(String thePenID){
        PenID = thePenID;
    }
    public String getPenID(){
        return this.PenID;
    }
    

    /**
     * @return the format
     */
    public FormatType getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(FormatType format) {
        this.format = format;
    }

    /**
     * @return the PenID2
     */
    public String getPenID2() {
        return PenID2;
    }

    /**
     * @param PenID2 the PenID2 to set
     */
    public void setPenID2(String PenID2) {
        this.PenID2 = PenID2;
    }

    /**
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return the myWorkgroups
     */
    public ArrayList<String> getMyWorkgroups() {
        return myWorkgroups;
    }

    /**
     * @param myWorkgroups the myWorkgroups to set
     */
    public void setMyWorkgroups(ArrayList<String> myWorkgroups) {
        this.myWorkgroups = myWorkgroups;
    }

    /**
     * @return the blankCards
     */
    public boolean isBlankCards() {
        return blankCards;
    }

    /**
     * @param blankCards the blankCards to set
     */
    public void setBlankCards(boolean blankCards) {
        this.blankCards = blankCards;
    }


}
