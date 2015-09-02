/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;

import jcms.cagecard.cardtypes.TwoPenWeanCardType;
/**
 *
 * @author mkamato
 */
public class TwoPenWeanCardResultDTO extends CageCardResultDTO{
    
    private TwoPenWeanCardType theTwoPenWeanCard;
    
    public TwoPenWeanCardResultDTO(){
        this.theTwoPenWeanCard = TwoPenWeanCardType.Simple;
    }
    
    public void setTwoPenWeanCardType(TwoPenWeanCardType theType){
        this.theTwoPenWeanCard = theType;
    }
    
    public TwoPenWeanCardType getTwoPenWeanCardType(){
        return this.theTwoPenWeanCard;
    }
}
