/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.cardtypes.FormatType;
import jcms.cagecard.cardtypes.TwoPenWeanCardType;
import jcms.cagecard.dtos.TwoPenWeanCardResultDTO;

/**
 *
 * @author mkamato
 */
public class TwoPenWeanCard extends CageCard{
    
    boolean flag = false;
    
    @Override
    public boolean validate(TwoPenWeanCardResultDTO DCRDTO){
        return flag;
    }
}
