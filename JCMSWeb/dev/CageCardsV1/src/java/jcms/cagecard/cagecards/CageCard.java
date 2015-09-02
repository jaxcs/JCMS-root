/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import jcms.cagecard.dtos.MatingCardResultDTO;
import jcms.cagecard.dtos.DetailCardResultDTO;
import jcms.cagecard.dtos.WeanCardResultDTO;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.cagecard.dtos.TwoPenWeanCardResultDTO;
import java.util.SortedMap;
import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.cardtypes.Orientation;
import jcms.cagecard.dtos.returnDTO;

/**
 *
 * @author mkamato
 */
public class CageCard {

    String weanCardErrorMessage = "Business rules violation: Make sure the requested cage"
            + "contains mice of the same strain";
    String twoPenWeanCardErrorMessage = "Business rules violation: Make sure the requested cage"
            + "contains mice of the same strain";
    String matingCardErrorMessage = "Business rules violation: Make sure the requested cage"
            + "contains 1 or 2 female mice and 0 or 1 male mice";
    String detailCardErrorMessage = "You should never see this";

    public returnDTO makeCard(CageCardResultDTO CCRDTO, HttpServletResponse HSR, String containerID) {
        returnDTO theReturnDTO = new returnDTO();
        if (CCRDTO instanceof MatingCardResultDTO) {
            MatingCardResultDTO MCRDTO = (MatingCardResultDTO) CCRDTO;
            theReturnDTO.setBytes(populateCustom(MCRDTO, HSR, containerID));
        } 
        else if (CCRDTO instanceof DetailCardResultDTO) {
            DetailCardResultDTO DCRDTO = (DetailCardResultDTO) CCRDTO;
            theReturnDTO.setBytes(populateCustom(DCRDTO, HSR, containerID));
        } 
        else if (CCRDTO instanceof WeanCardResultDTO) {
            WeanCardResultDTO WCRDTO = (WeanCardResultDTO) CCRDTO;
            theReturnDTO.setBytes(populateCustom(WCRDTO, HSR, containerID));
        } 
        else if (CCRDTO instanceof TwoPenWeanCardResultDTO) {
        //figure this out sometime
        } 
        else {
            System.out.println("something went terribly wrong");
            return null;
        }
        return theReturnDTO;
    }

    //validate and populate helpers for MatingCard
    public boolean validate(MatingCardResultDTO MCRDTO) {
        return new MatingCard().validate(MCRDTO);
    }

    //validate and populate helpers for WeanCard
    public boolean validate(WeanCardResultDTO WCRDTO) {
        return new WeanCard().validate(WCRDTO);
    }

    //validate and populate helpers for DetailCard
    public boolean validate(DetailCardResultDTO DCRDTO) {
        return new DetailCard().validate(DCRDTO);
    }

    public boolean validate(TwoPenWeanCardResultDTO TCRDTO) {
        return new TwoPenWeanCard().validate(TCRDTO);
    }

    //custom card methods
    public byte[] populateCustom(WeanCardResultDTO WCRDTO, HttpServletResponse HSR, String containerID) {
        if (WCRDTO.getResult() != null) {
            if(WCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomWeanCardGenerator().buildCard(WCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeWeanCardGenerator().buildCard(WCRDTO, HSR, containerID);
            }
        } else {
            if(WCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomWeanMultipleGenerator().buildCards(WCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeMultipleWeanCardGenerator().buildCards(WCRDTO, HSR, containerID);
            }  
        }
    }

    public byte[] populateCustom(DetailCardResultDTO DCRDTO, HttpServletResponse HSR, String containerID) {
        if (DCRDTO.getResult() != null) {
            if(DCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomDetailCardGenerator().buildCard(DCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeDetailCardGenerator().buildCard(DCRDTO, HSR, containerID);
            }
        } 
        else {
            if(DCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomDetailMultipleGenerator().buildCards(DCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeMultipleDetailCardGenerator().buildCards(DCRDTO, HSR, containerID);
            }
        }
    }

    public byte[] populateCustom(MatingCardResultDTO MCRDTO, HttpServletResponse HSR, String containerID) {
        if (MCRDTO.getResult() != null) {
            if(MCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomMatingCardGenerator().buildCard(MCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeMatingCardGenerator().buildCard(MCRDTO, HSR, containerID);
            }
        } 
        else {
            if(MCRDTO.getOrientation().equals(Orientation.portrait)){
                return new CustomMatingMultipleGenerator().buildCards(MCRDTO, HSR, containerID);
            }
            else{
                return new CustomLandscapeMultipleMatingCardGenerator().buildCards(MCRDTO, HSR, containerID);
            }        
        }
    }
}
