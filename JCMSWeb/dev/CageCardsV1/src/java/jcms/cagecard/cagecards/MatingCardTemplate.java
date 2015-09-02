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
import jcms.cagecard.cardhelpers.MatingQueueBuilder;

/**
 *
 * @author mkamato
 */
public abstract class MatingCardTemplate {

    Document doc = new Document();
    Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    ArrayList<CustomCardField> customCard;
    MatingQueueBuilder theBuilder;
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
            theField = ccf.getCustomText()+ " ";
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
        else if (ccf.getFieldName().equals("Owner")) {
            theField = theField + theBuilder.getOwner();
            theBarcodeText = theBuilder.getOwner();
        } //birthdates
        else if (ccf.getFieldName().equals("dam1BD")) {
            theField = theField + theBuilder.getDam1Birthdate();
            theBarcodeText = theBuilder.getDam1Birthdate();
        } 
        else if (ccf.getFieldName().equals("dam2BD")) {
            theField = theField + theBuilder.getDam2Birthdate();
            theBarcodeText = theBuilder.getDam2Birthdate();
        } 
        else if (ccf.getFieldName().equals("sireBD")) {
            theField = theField + theBuilder.getSireBirthdate();
            theBarcodeText = theBuilder.getSireBirthdate();
        } //generations
        else if (ccf.getFieldName().equals("dam1Generation")) {
            theField = theField + theBuilder.getDam1Generation();
            theBarcodeText = theBuilder.getDam1Generation();
        } 
        else if (ccf.getFieldName().equals("dam2Generation")) {
            theField = theField + theBuilder.getDam1Generation();
            theBarcodeText = theBuilder.getDam1Generation();
        } 
        else if (ccf.getFieldName().equals("sireGeneration")) {
            theField = theField + theBuilder.getDam1Generation();
            theBarcodeText = theBuilder.getDam1Generation();
        } //genotypes
        else if (ccf.getFieldName().equals("dam1Genotype")) {
            theField = theField + theBuilder.getDam1Genotype();
            theBarcodeText = theBuilder.getDam1Genotype();
        } 
        else if (ccf.getFieldName().equals("dam2Genotype")) {
            theField = theField + theBuilder.getDam2Genotype();
            theBarcodeText = theBuilder.getDam1Genotype();
        } 
        else if (ccf.getFieldName().equals("sireGenotype")) {
            theField = theField + theBuilder.getSireGenotype();
            theBarcodeText = theBuilder.getSireGenotype();
        } //IDs
        else if (ccf.getFieldName().equals("dam1ID")) {
            theField = theField + theBuilder.getDam1ID();
            theBarcodeText = theBuilder.getDam1ID();
        } 
        else if (ccf.getFieldName().equals("dam2ID")) {
            theField = theField + theBuilder.getDam2ID();
            theBarcodeText = theBuilder.getDam2ID();
        } 
        else if (ccf.getFieldName().equals("sireID")) {
            theField = theField + theBuilder.getSireID();
            theBarcodeText = theBuilder.getSireID();
        } //jrNums
        else if (ccf.getFieldName().equals("dam1jrNum")) {
            theField = theField + theBuilder.getDam1JrNum();
            theBarcodeText = theBuilder.getDam1JrNum();
        } 
        else if (ccf.getFieldName().equals("dam2jrNum")) {
            theField = theField + theBuilder.getDam2JrNum();
            theBarcodeText = theBuilder.getDam2JrNum();
        } 
        else if (ccf.getFieldName().equals("sirejrNum")) {
            theField = theField + theBuilder.getSireJrNum();
            theBarcodeText = theBuilder.getSireJrNum();
        } //strains
        else if (ccf.getFieldName().equals("dam1StrainName")) {
            theField = theField + theBuilder.getDam1Strain();
            theBarcodeText = theBuilder.getDam1Strain();
        } 
        else if (ccf.getFieldName().equals("dam2StrainName")) {
            theField = theField + theBuilder.getDam2Strain();
            theBarcodeText = theBuilder.getDam2Strain();
        } 
        else if (ccf.getFieldName().equals("sireStrainName")) {
            theField = theField + theBuilder.getSireStrain();
            theBarcodeText = theBuilder.getSireStrain();
        } //litter Fields
        else if (ccf.getFieldName().equals("LitterGeneration")) {
            theField = theField + theBuilder.getLitterGeneration();
            theBarcodeText = theBuilder.getLitterGeneration();
        }
        else if (ccf.getFieldName().equals("litterID")) {
            theBarcodeText = queueGet(theBuilder.getLitterIDQueue());
            theField = theField + theBarcodeText;
        }
        else if (ccf.getFieldName().equals("LitterStrain")) {
            theField = theField + theBuilder.getLitterStrain();
            theBarcodeText = theBuilder.getLitterStrain();
        } 
        else if (ccf.getFieldName().equals("numMale")) {
            theBarcodeText = queueGet(theBuilder.getNumMaleQueue());
            theField = theField + theBarcodeText;
        } 
        else if (ccf.getFieldName().equals("numFemale")) {
            theBarcodeText = queueGet(theBuilder.getNumFemaleQueue());
            theField = theField + theBarcodeText;
        }
        else if (ccf.getFieldName().equals("totalBorn")) {
            theBarcodeText = queueGet(theBuilder.getNumBornQueue());
            theField = theField + theBarcodeText;
        } 
        else if (ccf.getFieldName().equals("litterBirthDate")) {
            theBarcodeText = queueGet(theBuilder.getLitterBirthdateQueue());
            theField = theField + theBarcodeText;
        } //Mating info Fields
        else if (ccf.getFieldName().equals("MatingDate")) {
            theField = theField + theBuilder.getMatingDate();
            theBarcodeText = theBuilder.getMatingDate();
        } 
        else if (ccf.getFieldName().equals("MatingID")) {
            theField = theField + theBuilder.getMatingID();
            theBarcodeText = theBuilder.getMatingID();
        }
        else if (ccf.getFieldName().equals("PIName")){
            theField = theField + theBuilder.getPIName();
            theBarcodeText = theBuilder.getPIName();
        }
        else if (ccf.getFieldName().equals("PIPhone")){
            theField = theField + theBuilder.getPIPhone();
            theBarcodeText = theBuilder.getPIPhone();
        }
        else if (ccf.getFieldName().equals("dam1LitterID")){
            theField = theField + theBuilder.getDam1LitterID();
            theBarcodeText = theBuilder.getDam1LitterID();
        }
        else if (ccf.getFieldName().equals("dam2LitterID")){
            theField = theField + theBuilder.getDam2LitterID();
            theBarcodeText = theBuilder.getDam2LitterID();
        }
        else if (ccf.getFieldName().equals("sireLitterID")){
            theField = theField + theBuilder.getSireLitterID();
            theBarcodeText = theBuilder.getSireLitterID();
        }
        else if (ccf.getFieldName().equals("dam1MatingID")){
            theField = theField + theBuilder.getDam1MatingID();
            theBarcodeText = theBuilder.getDam1LitterID();
        }
        else if (ccf.getFieldName().equals("dam2MatingID")){
            theField = theField + theBuilder.getDam2MatingID();
            theBarcodeText = theBuilder.getDam2LitterID();
        }
        else if (ccf.getFieldName().equals("sireMatingID")){
            theField = theField + theBuilder.getSireMatingID();
            theBarcodeText = theBuilder.getSireLitterID();
        }
        else if(ccf.getFieldName().equals("weanNote")){
            theField = theField + theBuilder.getWeanNote();
            theBarcodeText = theBuilder.getWeanNote();
        }
        else if(ccf.getFieldName().equals("dam1Protocol")){
            theField = theField + theBuilder.getDam1Protocol();
            theBarcodeText = theBuilder.getDam1Protocol();
        }
        else if(ccf.getFieldName().equals("dam2Protocol")){
            theField = theField + theBuilder.getDam2Protocol();
            theBarcodeText = theBuilder.getDam2Protocol();
        }
        else if(ccf.getFieldName().equals("sireProtocol")){
            theField = theField + theBuilder.getSireProtocol();
            theBarcodeText = theBuilder.getSireProtocol();
        }
        else if(ccf.getFieldName().equals("room")){
            theField = theField + theBuilder.getRoom();
            theBarcodeText = theBuilder.getRoom();
        }
        else if(ccf.getFieldName().equals("matingComment")){
            theField = theField + theBuilder.getMatingComment();
            theBarcodeText = theBuilder.getMatingComment();
        }
        else if(ccf.getFieldName().equals("containerStatus")){
            theField = theField + theBuilder.getPenStatus();
            theBarcodeText = theBuilder.getPenStatus();
        }
        else if(ccf.getFieldName().equals("statusDate")){
            theField = theField + theBuilder.getStatusDate();
            theBarcodeText = theBuilder.getStatusDate();
        }
        else if(ccf.getFieldName().equals("containerComment")){
            theField = theField + theBuilder.getPenComment();
            theBarcodeText = theBuilder.getPenComment();
        }
        else if(ccf.getFieldName().equals("dam1NewTag")){
            theField = theField + theBuilder.getDam1NewTag();
            theBarcodeText = theBuilder.getDam1NewTag();
        }
        else if(ccf.getFieldName().equals("dam2NewTag")){
            theField = theField + theBuilder.getDam2NewTag();
            theBarcodeText = theBuilder.getDam2NewTag();
        }
        else if(ccf.getFieldName().equals("sireNewTag")){
            theField = theField + theBuilder.getSireNewTag();
            theBarcodeText = theBuilder.getSireNewTag();
        }
        else if (ccf.getFieldName().equals("notes")) {
           
        } 
        else if (ccf.getFieldName().equals("blank")) {
            theField = ccf.getCustomText();
        }
        else {
            theField = "";
            System.out.println("Something went terribly wrong in the mating template... " + ccf.getFieldName());
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
