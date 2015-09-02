/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import jcms.cagecard.cardhelpers.CustomCardField;
import jcms.cagecard.cardhelpers.DetailQueueBuilder;

/**
 *
 * @author mkamato
 */
public abstract class DetailCardTemplate {

    Document doc = new Document();
    Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    ArrayList<CustomCardField> customCard;
    DetailQueueBuilder theBuilder;
    float textHeight = 12f;
    PdfPTable tableDoc;
    Barcode128 BARCODE = new Barcode128();
    PdfContentByte cb = null;
    PdfWriter writer = null;
    String theBarcodeText = "";
    String containerID;
       
    public String buildText(CustomCardField ccf) {
        String theField = "";
        
        if (ccf.isLabeled()) {
            theField = ccf.getCustomText() + " ";
        }
        
        
        if (ccf.getFieldName().equals("CageID")) {
            if (!containerID.equals("-100")) {
                theField = theField + containerID;
            }
            theBarcodeText = containerID;
        } 
        else if(ccf.getFieldName().equals("activationDate")){
            theField = theField + theBuilder.getActivationDate();
        }
        else if (ccf.getFieldName().equals("CageName")) {
            theField = theField + theBuilder.getContainerName();
        } 
        else if (ccf.getFieldName().equals("Owner")) {
            theField = theField + queueGet(theBuilder.getOwnerQueue());
        } 
        else if (ccf.getFieldName().equals("birthdate")) {
            theField = theField + queueGet(theBuilder.getBirthDateQueue());
        } 
        else if (ccf.getFieldName().equals("breedingStatus")) {
            theField = theField + queueGet(theBuilder.getBreedingStatusQueue());
        } 
        else if (ccf.getFieldName().equals("CODNotes")) {
            theField = theField + queueGet(theBuilder.getCODNotesQueue());
        } 
        else if (ccf.getFieldName().equals("COD")) {
            theField = theField + queueGet(theBuilder.getCODQueue());
        } 
        else if (ccf.getFieldName().equals("coatColor")) {
            theField = theField + queueGet(theBuilder.getCoatColorQueue());
        } 
        else if (ccf.getFieldName().equals("diet")) {
            theField = theField + queueGet(theBuilder.getDietQueue());
        } 
        else if (ccf.getFieldName().equals("exitDate")) {
            theField = theField + queueGet(theBuilder.getExitDateQueue());
        } 
        else if (ccf.getFieldName().equals("generation")) {
            theField = theField + queueGet(theBuilder.getGenerationQueue());
        } 
        else if (ccf.getFieldName().equals("genotype")) {
            theField = theField + queueGet(theBuilder.getGenotypeQueue());
        } 
        else if(ccf.getFieldName().equals("matingID")){
            theField = theField + queueGet(theBuilder.getMatingIDQueue());
        }
        else if (ccf.getFieldName().equals("containerComment")){
            theField = theField + theBuilder.getContainerComment();
            theBarcodeText = theBuilder.getContainerComment();
        }
        else if (ccf.getFieldName().equals("containerStatus")){
            theField = theField + theBuilder.getContainerStatus();
            theBarcodeText = theBuilder.getContainerStatus();
        }
        else if (ccf.getFieldName().equals("PIName")){
            theField = theField + theBuilder.getPIName();
            theBarcodeText = theBuilder.getPIName();
        }
        else if (ccf.getFieldName().equals("PIPhone")){
            theField = theField + theBuilder.getPIPhone();
            theBarcodeText = theBuilder.getPIPhone();
        }
        else if (ccf.getFieldName().equals("detailCardNote")){
            theField = theField + theBuilder.getDetailCardNote();
            theBarcodeText = theBuilder.getDetailCardNote();
        }
        else if (ccf.getFieldName().equals("detailCardNote")){
            theField = theField + theBuilder.getDetailCardNote();
            theBarcodeText = theBuilder.getDetailCardNote();
        }
        else if (ccf.getFieldName().equals("room")){
            theField = theField + theBuilder.getRoom();
            theBarcodeText = theBuilder.getRoom();
        }
        else if (ccf.getFieldName().equals("detailCardNote")){
            theField = theField + theBuilder.getDetailCardNote();
            theBarcodeText = theBuilder.getDetailCardNote();
        }
        else if (ccf.getFieldName().equals("ID")) {
            theField = theField + queueGet(theBuilder.getIDQueue());
        } 
        else if (ccf.getFieldName().equals("jrNum")) {
            theField = theField + queueGet(theBuilder.getJrNumQueue());
        } 
        else if (ccf.getFieldName().equals("lifeStatus")) {
            theField = theField + queueGet(theBuilder.getLifeStatusQueue());
        } 
        else if (ccf.getFieldName().equals("newTag")) {
            theField = theField + queueGet(theBuilder.getNewTagQueue());
        } 
        else if (ccf.getFieldName().equals("weanDate")) {
            theField = theField + queueGet(theBuilder.getWeanDateQueue());
        } 
        else if (ccf.getFieldName().equals("comment")) {
            theField = theField + queueGet(theBuilder.getCommentQueue());
        } 
        else if (ccf.getFieldName().equals("protocol")) {
            theField = theField + queueGet(theBuilder.getProtocolQueue());
        } 
        else if (ccf.getFieldName().equals("litterID")) {
            theField = theField + queueGet(theBuilder.getLitterIDQueue());
        } 
        else if (ccf.getFieldName().equals("origin")) {
            theField = theField + queueGet(theBuilder.getOriginQueue());
        } 
        else if (ccf.getFieldName().equals("sex")) {
            theField = theField + queueGet(theBuilder.getSexQueue());
        } 
        else if (ccf.getFieldName().equals("strainName")) {
            theField = theField + queueGet(theBuilder.getStrainNameQueue());
        } 
        else if (ccf.getFieldName().equals("notes")) {
        } 
        else if (ccf.getFieldName().equals("blank")) {
            theField = ccf.getCustomText();
        } 
        else {
            theField = "";
            System.out.println("Something went terribly wrong...");
        }
        return theField;
    }

    public String queueGet(LinkedList<String> theList) {
        String value = theList.poll();
        if (value == null) {
            theBarcodeText = "";
            return "";
        } else {
            theBarcodeText = value;
            return value;
        }
    }
}
