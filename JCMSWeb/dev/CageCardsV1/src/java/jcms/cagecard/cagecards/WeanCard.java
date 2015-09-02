/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.cardtypes.WeanCardType;
import jcms.cagecard.dtos.WeanCardResultDTO;
import javax.servlet.jsp.jstl.sql.Result;

import java.util.SortedMap;
import java.util.ArrayList;
import jcms.cagecard.cardtypes.FormatType;

/**
 *
 * @author mkamato
 */
public class WeanCard extends CageCard {

    boolean flag = false;
    SortedMap[] results = null;

    @Override
    public boolean validate(WeanCardResultDTO WCRDTO) {
        if (WCRDTO.getResult() != null) {
            results = WCRDTO.getResult().getRows();
            checkStrain();
            return flag;
        } 
        else {
            int firstCage = WCRDTO.getStart();
            int idx = 0;
            for(Result r: WCRDTO.getResults()){
                results = r.getRows();
                checkStrain();
                if(!flag || WCRDTO.isBlankCards()){
                    WCRDTO.getGoodCards().add(new Integer(idx+firstCage).toString());
                }
                else{
                    WCRDTO.getBadCards().add(new Integer(idx+firstCage).toString());
                }
                flag = false;
                idx++;
            }
            if(WCRDTO.getGoodCards().isEmpty()){
                return true;
            }
            else{
                return false;
            }
        }
    }

    private void checkStrain() {
        ArrayList<String> strains = new ArrayList<String>();
        for (SortedMap mouse : results) {
            if (mouse.get("strainName") != null) {
                strains.add(mouse.get("strainName").toString());
            } else {
                strains.add(" ");
            }
        }
        for (String strain : strains) {
            String theStrain = strains.get(0);
            if (!theStrain.equals(strain)) {
                flag = true;
            }
        }
    }
}
