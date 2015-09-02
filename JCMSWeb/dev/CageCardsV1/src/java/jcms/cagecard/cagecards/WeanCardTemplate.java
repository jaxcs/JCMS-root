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
import jcms.cagecard.cardhelpers.WeanQueueBuilder;

/**
 *
 * @author mkamato
 */
public class WeanCardTemplate {
    
    Document doc = new Document();
    Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    ArrayList<CustomCardField> customCard;
    WeanQueueBuilder theBuilder;
    float textHeight = 12f;
    PdfPTable tableDoc;
    Barcode128 BARCODE = new Barcode128();
    PdfContentByte cb = null;
    PdfWriter writer = null;
    String theBarcodeText = "";
    String containerID;
    
    public String buildText(CustomCardField ccf) {
        String theField = "";
        if(ccf.isLabeled()){
            theField = ccf.getCustomText();
        }
        
        
        if (ccf.getFieldName().equals("CageID")) {
            if (!containerID.equals("-100")) {
                theField = theField + containerID;
            }
            theBarcodeText = containerID;
        } 
        else if(ccf.getFieldName().equals("activationDate")){
            theField = theField + theBuilder.getActivationDate();
            theBarcodeText = theBuilder.getActivationDate();
        }
        else if (ccf.getFieldName().equals("CageName")) {
            theField = theField + theBuilder.getContainerName();
            theBarcodeText = theBuilder.getContainerName();
        }
        else if (ccf.getFieldName().equals("cageStatus")) {
            theField = theField + theBuilder.getContainerStatus();
            theBarcodeText = theBuilder.getContainerStatus();
        } 
        else if (ccf.getFieldName().equals("cageComment")) {
            theField = theField + theBuilder.getcontainerComment();
            theBarcodeText = theBuilder.getcontainerComment();
        } 
        else if (ccf.getFieldName().equals("room")) {
            theField = theField + theBuilder.getRoom();
            theBarcodeText = theBuilder.getRoom();
        } 
        else if (ccf.getFieldName().equals("Owner")) {
            theField = theField + queueGet(theBuilder.getOwnerQueue());
        } 
        else if(ccf.getFieldName().equals("protocol")){
            theField = theField + queueGet(theBuilder.getProtocolQueue());
        }
        else if (ccf.getFieldName().equals("litterID")) {
            theField = theField + queueGet(theBuilder.getLitterIDQueue());
        } 
        else if(ccf.getFieldName().equals("coatColor")){
            theField = theField + queueGet(theBuilder.getCoatColorQueue());
        }
        else if (ccf.getFieldName().equals("ID")) {
            theField = theField + queueGet(theBuilder.getIDQueue());
        } 
        else if (ccf.getFieldName().equals("generation")) {
            theField = theField + queueGet(theBuilder.getGenerationQueue());
        } 
        else if(ccf.getFieldName().equals("comment")){
            theField = theField + queueGet(theBuilder.getCommentQueue());
        }
        else if (ccf.getFieldName().equals("jrNum")) {
            theField = theField + queueGet(theBuilder.getJrNumQueue());
        } 
        else if (ccf.getFieldName().equals("sex")) {
            theField = theField + queueGet(theBuilder.getSexQueue());
        } 
        else if (ccf.getFieldName().equals("breedingStatus")) {            
            theField = theField + queueGet(theBuilder.getBreedingStatusQueue());
        } 
        else if (ccf.getFieldName().equals("birthDate")) {
            theField = theField + queueGet(theBuilder.getBirthDateQueue());
        } 
        else if (ccf.getFieldName().equals("totalBorn")) {
            theField = theField + queueGet(theBuilder.getTotalBornQueue());
        } 
        else if (ccf.getFieldName().equals("litterBirthDate")) {
            theField = theField + queueGet(theBuilder.getLitterBirthDateQueue());
        } 
        else if (ccf.getFieldName().equals("numFemale")) {
            theField = theField + queueGet(theBuilder.getNumFemaleQueue());
        } 
        else if (ccf.getFieldName().equals("numMale")) {
            theField = theField + queueGet(theBuilder.getNumMaleQueue());
        } 
        else if (ccf.getFieldName().equals("weanDate")) {
            theField = theField + queueGet(theBuilder.getWeanDateQueue());
        } 
        else if (ccf.getFieldName().equals("tagDate")) {
            theField = theField + queueGet(theBuilder.getTagDateQueue());
        } 
        else if (ccf.getFieldName().equals("status")) {
            theField = theField + queueGet(theBuilder.getStatusQueue());
        } 
        else if (ccf.getFieldName().equals("strainName")) {
            theField = theField + queueGet(theBuilder.getStrainNameQueue());
        } 
        else if (ccf.getFieldName().equals("genotype")) {
            theField = theField + queueGet(theBuilder.getGenotypeQueue());
        } 
        else if(ccf.getFieldName().equals("dam1Genotype")){
            theField = theField + queueGet(theBuilder.getDam1GenotypeQueue());
        }
        else if(ccf.getFieldName().equals("dam2Genotype")){
            theField = theField + queueGet(theBuilder.getDam2GenotypeQueue());
        }
        else if(ccf.getFieldName().equals("sireGenotype")){
            theField = theField + queueGet(theBuilder.getSireGenotypeQueue());
        }
        else if(ccf.getFieldName().equals("matingID")){
            theField = theField + queueGet(theBuilder.getMatingIDQueue());
        }
        else if(ccf.getFieldName().equals("newTag")){
            theField = theField + queueGet(theBuilder.getNewTagQueue());
        }
        else if(ccf.getFieldName().equals("dam1ID")){
            theField = theField + queueGet(theBuilder.getDam1IDQueue());
        }
        else if(ccf.getFieldName().equals("dam2ID")){
            theField = theField + queueGet(theBuilder.getDam2IDQueue());
        }
        else if(ccf.getFieldName().equals("sireID")){
            theField = theField + queueGet(theBuilder.getSireIDQueue());
        }
        else if (ccf.getFieldName().equals("PIName")){
            theField = theField + theBuilder.getPIName();
            theBarcodeText = theBuilder.getPIName();
        }
        else if (ccf.getFieldName().equals("PIPhone")){
            theField = theField + theBuilder.getPIPhone();
            theBarcodeText = theBuilder.getPIPhone();
        }
        else if (ccf.getFieldName().equals("blank")) {
            theBarcodeText = ccf.getCustomText();
            theField = ccf.getCustomText();
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
