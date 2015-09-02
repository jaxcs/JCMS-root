/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cagecards;

/**
 *
 * @author mkamato
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.util.SortedMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.cagecard.dtos.CageCardResultDTO;
import jcms.cagecard.cardhelpers.CustomCardField;
import java.util.ArrayList;
import java.util.LinkedList;
import jcms.cagecard.cardhelpers.DetailQueueBuilder;
import jcms.cagecard.cardtypes.FormatType;

public class CustomDetailMultipleGenerator extends DetailCardTemplate{

    //detail strings
    public void populateFields() {
    }

    public byte[] buildCards(CageCardResultDTO CCRDTO, HttpServletResponse HSR, String ContainerID) {
        try {
            tableDoc = new PdfPTable(12);
            //set up document specs
            doc.setMargins(5, 5, 5, 5);

            PdfPTable table = new PdfPTable(1);
            table.setTotalWidth(210f);
            table.setLockedWidth(true);
            if (CCRDTO.getFormatType().equals(FormatType.threeByFive)) {
                doc.setPageSize(new Rectangle(220, 370));
            } else if (CCRDTO.getFormatType().equals(FormatType.alignCenter)) {
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
            } else if (CCRDTO.getFormatType().equals(FormatType.alignLeft)) {
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            }

            //set up technical document info
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            writer = PdfWriter.getInstance(doc, buffer);
            doc.open();
            tableDoc.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            cb = writer.getDirectContent();
            tableDoc.setTotalWidth(210f);
            tableDoc.setLockedWidth(true);

            //barcode details
            BARCODE.setSize(22f);
            BARCODE.setCodeType(Barcode.CODE128);
            BARCODE.setFont(null);

            containerID = new Integer(CCRDTO.getStart()).toString();

            for (Result r : CCRDTO.getResults()) {

                theBuilder = new DetailQueueBuilder(r.getRows(), CCRDTO.getDbSetupResults().getRows(), CCRDTO.getMyWorkgroups());
                theBuilder.parseResults();

                customCard = CCRDTO.getCustomCard();
                int columnsUsed = 0;

                //go through array list of fields and add cells to the card
                for (CustomCardField ccf : customCard) {
                    PdfPCell barCodeCell = new PdfPCell();
                    PdfPCell textCell = new PdfPCell();
                    textCell.setFixedHeight(12f);
                    barCodeCell.setFixedHeight(24f);

                    //if row is complete, start new column count for row
                    if (columnsUsed == 12) {
                        columnsUsed = 0;
                    }
                    int colspan = getColSpan(ccf.getRowPortion());
                    columnsUsed = columnsUsed + colspan;
                    //adding new cell would overfill row, complete current row and add cell to new one
                    //changing columns used to the number of columns spanned by current cell
                    if (columnsUsed > 12) {
                        tableDoc.completeRow();
                        columnsUsed = colspan;
                    }
                    String theText = buildText(ccf);
                    if (!ccf.isBarCoded()) {
                        if (!ccf.isBorders()) {
                            textCell.setBorder(PdfPCell.NO_BORDER);
                        }
                        textCell.setColspan(colspan);
                        textCell.setPhrase(new Phrase(theText, FONT));
                        textCell.setFixedHeight(textHeight * ccf.getNumberOfRows());
                        tableDoc.addCell(textCell);
                    } else {
                        //create BC
                        BARCODE.setCode(theBarcodeText);
                        Image theBarCode = BARCODE.createImageWithBarcode(cb, null, null);

                        //create temp table
                        PdfPTable tempTable = new PdfPTable(2);

                        //create cells with text and barcode and add to temptable
                        PdfPCell theTextCell = new PdfPCell(new Phrase(theText, FONT));
                        theTextCell.setBorder(Rectangle.NO_BORDER);
                        PdfPCell theBCCell = new PdfPCell(theBarCode);
                        theBCCell.setVerticalAlignment(Element.ALIGN_CENTER);
                        theBCCell.setBorder(Rectangle.NO_BORDER);
                        if (ccf.isLabeled()) {
                            tempTable.addCell(theTextCell);
                            tempTable.addCell(theBCCell);
                        } else {
                            theBCCell.setColspan(2);
                            tempTable.addCell(theBCCell);
                        }
                        tempTable.setWidthPercentage(100f);

                        //add tempTable to cell and then add to tableDoc - done for formatting purposes
                        barCodeCell.addElement(tempTable);
                        barCodeCell.setColspan(colspan);
                        barCodeCell.setBorder(Rectangle.NO_BORDER);
                        barCodeCell.setFixedHeight(24f);
                        

                        tableDoc.addCell(barCodeCell);
                    }
                }

                //if last row isn't full, fill it.
                tableDoc.completeRow();


                //holder cell for formatting purposes
                PdfPCell tableHolder = new PdfPCell(tableDoc);


                //holder table for formatting purposes
                table.addCell(tableHolder);
                doc.add(table);

                //reinitialize components.
                doc.newPage();
                tableDoc = new PdfPTable(12);
                tableDoc.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tableDoc.setTotalWidth(210f);
                tableDoc.setLockedWidth(true);

                table = new PdfPTable(1);
                table.setTotalWidth(210f);
                table.setLockedWidth(true);
                if (CCRDTO.getFormatType().equals(FormatType.alignCenter)) {
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                } else if (CCRDTO.getFormatType().equals(FormatType.alignLeft)) {
                    table.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (CCRDTO.getFormatType().equals(FormatType.alignRight)) {
                    table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }
                containerID = new Integer(Integer.parseInt(containerID) + 1).toString();
            }

            doc.close();

            //turn into byte array and complete it.
            byte[] bytes = buffer.toByteArray();
            return bytes;
        } catch (Exception e) {
            System.out.println("Exception in CustomDetailMultipleCardGenerator: " + e);
            return null;
        }
    }

    public int getColSpan(String percent) {
        if (percent.equals("100")) {
            return 12;
        } else if (percent.equals("75")) {
            return 9;
        } else if (percent.equals("67")) {
            return 8;
        } else if (percent.equals("50")) {
            return 6;
        } else if (percent.equals("33")) {
            return 4;
        } else if (percent.equals("25")) {
            return 3;
        } else if (percent.equals("16")) {
            return 2;
        } else {
            System.out.println("Something went terribly wrong...");
            return 1;
        }
    }
}
