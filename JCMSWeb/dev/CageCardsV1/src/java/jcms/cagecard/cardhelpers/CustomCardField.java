/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cardhelpers;

/**
 *
 * @author mkamato
 */
public class CustomCardField {
    private String rowPortion;
    private int numberOfRows;
    private String fieldName;
    private boolean labeled;
    private boolean barCoded;
    private boolean borders;
    private String customText;
    private String color = "";

  
    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the labeled
     */
    public boolean isLabeled() {
        return labeled;
    }

    /**
     * @param labeled the labeled to set
     */
    public void setLabeled(boolean labeled) {
        this.labeled = labeled;
    }

    /**
     * @return the barCoded
     */
    public boolean isBarCoded() {
        return barCoded;
    }

    /**
     * @param barCoded the barCoded to set
     */
    public void setBarCoded(boolean barCoded) {
        this.barCoded = barCoded;
    }

    /**
     * @return the rowPortion
     */
    public String getRowPortion() {
        return rowPortion;
    }

    /**
     * @param rowPortion the rowPortion to set
     */
    public void setRowPortion(String rowPortion) {
        this.rowPortion = rowPortion;
    }

    /**
     * @return the numberOfRows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param numberOfRows the numberOfRows to set
     */
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    /**
     * @return the borders
     */
    public boolean isBorders() {
        return borders;
    }

    /**
     * @param borders the borders to set
     */
    public void setBorders(boolean borders) {
        this.borders = borders;
    }

    /**
     * @return the customText
     */
    public String getCustomText() {
        return customText;
    }

    /**
     * @param customText the customText to set
     */
    public void setCustomText(String customText) {
        this.customText = customText;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
}
