/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;

import jcms.cagecard.cardtypes.DetailCardType;
/**
 *
 * @author mkamato
 */
public class DetailCardResultDTO extends CageCardResultDTO{
    
    public DetailCardResultDTO(){
        this.theDetailCard = DetailCardType.Simple;
    }
    
    private DetailCardType theDetailCard = null;
    
    //which flavor of detail card is it?
    public void setDetailCardType(DetailCardType theType){
        theDetailCard = theType;
    }
    
    //get the flavor of detail card.
    public DetailCardType getDetailCardType(){
        return this.theDetailCard;
    }
}
