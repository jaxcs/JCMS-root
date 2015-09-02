/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;

import javax.servlet.jsp.jstl.sql.Result;
import jcms.cagecard.cardtypes.FormatType;
import java.util.ArrayList;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardtypes.Orientation;

/**
 *
 * @author mkamato
 */
public abstract class CageCardResultDTO {

    private Result result;
    private boolean blankCards;
    private ArrayList<Result> results;
    private Result dbSetupResults;
    private ArrayList<String> goodCards = new ArrayList<String>();
    private ArrayList<String> badCards = new ArrayList<String>();
    private ArrayList<String> myWorkgroups = new ArrayList<String>();
    private ArrayList<String> customizations = new ArrayList<String>();
    private ArrayList<CustomCardField> customCard = null;
    private FormatType formatType;
    private Orientation orientation;
    private int start;

    public void setResult(Result setResult) {
        this.result = setResult;
    }

    public Result getResult() {
        return this.result;
    }

    /**
     * @return the formatType
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * @param formatType the formatType to set
     */
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    /**
     * @return the results
     */
    public ArrayList<Result> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    /**
     * @return the goodCards
     */
    public ArrayList<String> getGoodCards() {
        return goodCards;
    }

    /**
     * @param goodCards the goodCards to set
     */
    public void setGoodCards(ArrayList<String> goodCards) {
        this.goodCards = goodCards;
    }

    /**
     * @return the badCards
     */
    public ArrayList<String> getBadCards() {
        return badCards;
    }

    /**
     * @param badCards the badCards to set
     */
    public void setBadCards(ArrayList<String> badCards) {
        this.badCards = badCards;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the customizations
     */
    public ArrayList<String> getCustomizations() {
        return customizations;
    }

    /**
     * @param customizations the customizations to set
     */
    public void setCustomizations(ArrayList<String> customizations) {
        this.customizations = customizations;
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

    /**
     * @return the dbSetupResults
     */
    public Result getDbSetupResults() {
        return dbSetupResults;
    }

    /**
     * @param dbSetupResults the dbSetupResults to set
     */
    public void setDbSetupResults(Result dbSetupResults) {
        this.dbSetupResults = dbSetupResults;
    }
}
