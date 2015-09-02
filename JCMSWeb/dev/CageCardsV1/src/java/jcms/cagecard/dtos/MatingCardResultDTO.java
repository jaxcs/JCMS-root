/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;


import jcms.cagecard.cardtypes.MatingCardType;
import javax.servlet.jsp.jstl.sql.Result;
import java.util.ArrayList;

/**
 *
 * @author mkamato
 */
public class MatingCardResultDTO extends CageCardResultDTO {
    
    private MatingCardType theMatingCard = null;
    private Result matingResult = null;
    private ArrayList<Result> matingResults = null;
    
    public MatingCardResultDTO(){
        this.theMatingCard = MatingCardType.Simple;
    }
    
    public void setMatingCardType(MatingCardType theType){
        theMatingCard = theType;
    }
    
    public MatingCardType getMatingCardType(){
        return this.theMatingCard;
    }

    /**
     * @return the matingResult
     */
    public Result getMatingResult() {
        return matingResult;
    }

    /**
     * @param matingResult the matingResult to set
     */
    public void setMatingResult(Result matingResult) {
        this.matingResult = matingResult;
    }

    /**
     * @return the matingResults
     */
    public ArrayList<Result> getMatingResults() {
        return matingResults;
    }

    /**
     * @param matingResults the matingResults to set
     */
    public void setMatingResults(ArrayList<Result> matingResults) {
        this.matingResults = matingResults;
    }
}
