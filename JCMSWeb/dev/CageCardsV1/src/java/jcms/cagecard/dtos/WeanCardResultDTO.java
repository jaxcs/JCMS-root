/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;

import jcms.cagecard.cardtypes.WeanCardType;
/**
 *
 * @author mkamato
 */
public class WeanCardResultDTO extends CageCardResultDTO{
    private WeanCardType theWeanCard = null;
    
    public WeanCardResultDTO(){
        this.theWeanCard = WeanCardType.Simple;
    }
    
    public void setWeanCardType(WeanCardType theCard){
        theWeanCard = theCard;
    }
    
    public WeanCardType getWeanCardType(){
        return this.theWeanCard;
    }
}
