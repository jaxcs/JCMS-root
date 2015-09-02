/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.cagecard.dtos.DetailCardResultDTO;
import jcms.cagecard.cardtypes.DetailCardType;
import jcms.cagecard.cardtypes.FormatType;

/**
 * /**
 *
 * @author mkamato
 */
public class DetailCard extends CageCard {

    @Override
    public boolean validate(DetailCardResultDTO DCRDTO) {
        if (DCRDTO.getResult() != null) {
            return false;
        } //do nothing, anything can be printed on a detail card.
        else {
            int firstCage = DCRDTO.getStart();
            int idx = 0;
            for (Result r : DCRDTO.getResults()) {
                DCRDTO.getGoodCards().add(new Integer(idx + firstCage).toString());
                idx++;
            }        
            return false;
        }
    }
}
