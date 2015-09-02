/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import com.itextpdf.text.Document;
import jcms.cagecard.cardtypes.MatingCardType;
import jcms.cagecard.dtos.MatingCardResultDTO;
import javax.servlet.http.HttpServletResponse;
import java.util.SortedMap;
import java.util.ArrayList;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.cagecard.cardtypes.FormatType;

/**
 *
 * @author mkamato
 */
public class MatingCard extends CageCard {

    SortedMap[] matings;

    @Override
    /*
     * @param MCRDTO - contains information from mating query
     * @return true -> was not a mating cage, error || no mating cage in range, error
     */
    public boolean validate(MatingCardResultDTO MCRDTO) {
        //first case, getting whether a mating card for just one cage
        if(MCRDTO.getMatingResult()!=null){
            matings = MCRDTO.getMatingResult().getRows();
            for(SortedMap mating : matings){
                if(mating.get("MatingID") == null || mating.get("MatingID").equals("")){
                    return true;
                }
                else{
                    return false;
                }
            }
            return true;
        }
        //case 2, getting whether a list of cages are mating cages
        else{
            int firstCage = MCRDTO.getStart();
            int idx = 0;
            for(Result r: MCRDTO.getMatingResults()){
                matings = r.getRows();
                //if there are any matings, check to see there's a mating id, if so a good mating card
                if(matings.length > 0){
                    for(SortedMap mating : matings){
                        if(mating.get("MatingID") == null || mating.get("MatingID").equals("")){                        
                            MCRDTO.getBadCards().add(new Integer(idx+firstCage).toString());
                        }
                        else{                        
                            MCRDTO.getGoodCards().add(new Integer(idx+firstCage).toString());
                        }
                    }
                }
                else if(MCRDTO.isBlankCards()){
                    MCRDTO.getGoodCards().add(new Integer(idx+firstCage).toString());                    
                }
                else{
                    MCRDTO.getBadCards().add(new Integer(idx+firstCage).toString());
                }
                idx++;
            }
            if(MCRDTO.getGoodCards().isEmpty()){
                return true;
            }
            else{
                return false;
            }            
        }
    }
}
