/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.dtos;

import java.util.ArrayList;

/**
 *
 * @author mkamato
 */
public class returnDTO {
    private byte[] bytes = null;
    private String errorMessage = null;
    private ArrayList<String> badCards = new ArrayList<String>();

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
}
