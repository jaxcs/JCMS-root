/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package jcms.web.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dao.WeanReportDAO;
import jcms.integrationtier.dto.WeanReportDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.FileDownloadBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author bas
 */
public class WeanReportBean extends WTBaseBackingBean implements Serializable{

    private ReportFacadeLocal rfl;
    private WeanReportDTO report = null;
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private String owners = "";
    private String startDate = "";
    private String myStartDate = "";
    private Date myDate = null;
    private Date endDate = null;
    private Date myBeginDate = null;
    private Date returnDate = null;
    private WeanReportDAO dao                       = new WeanReportDAO();
    private ArrayList<String> ownerArray            = new ArrayList<String>();
    private ArrayList<WeanReportDTO> toWeanLst2 = null;
    private String inputPath = "";
    private File tempFile;
    private String fileName = "";
        
    public WeanReportBean() {
       this.initialize();
    }
    
    private void initialize() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
        report = new WeanReportDTO();
        this.initializeOwner();
    }
    // initialize the owner to logged in owner
    private void initializeOwner() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("guestOwnerEntityLst");
        if (ownerLst.size() > 0) {
            this.report.setOwners(ownerLst);
        }
    }

    public String weanReportAction() {
        this.initialize();

        return "weanReport";
    }
    
    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }

    /**
     * @return the report
     */
    public WeanReportDTO getReport() {
        return report;
    }
    /**
     * @param report the report to set
     */
    public void setReport(WeanReportDTO report) {
        this.report = report;
    }
    public String runReportAction() {
           { 
               toWeanLst2 = new ArrayList<WeanReportDTO>();  
               if (this.report.getOwners() == null
                    || this.report.getOwners().isEmpty()) {
                //System.out.println("ownerLst initialzed");
                this.report.setOwners((ArrayList<OwnerEntity>) 
                        getSessionParameter("guestOwnerEntityLst"));
                }
                this.generateOutput();
            }
        
        return "weanReportResults";
    }
        /*
     * This method generates output for the report
     */
    public void generateOutput() {
        
        try {
            //These fields are used for the csv file
            String strain = "";
            String room = "";
            String rack = "";
            String rackRow = "";
            String rackColumn = "";
            String matingID = "";
            String matingDate = "";
            String jrNum = "";
            String owner = "";
            String birthDate = "";
            String dateToWean = "";
            String litterID = "";
            String totalBorn = "";
            String numFemale = "";
            String numMale = "";
            String tagDate = "";
            String weanRecorded = "";
            String litterStatus = "";
            String generation = "";
            String needsTyping = "";
            String cageID = "";
            String cageName = "";
            String comment = "";
            String weanNote = "";
            setOwners("''");
            
            // Create temp file.
            tempFile = File.createTempFile("weanReport", ".csv");
            
            // Delete temp file when program exits.
            tempFile.deleteOnExit();

            // Write to temp file
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter(tempFile));
            
            String ownerClause = "";
            // if owner is selected, build the list
            if (report.getOwners() != null) {
                for (int i = 0; i < report.getOwners().size(); ++i) {
                    // if last item, then no comma
                    if (i == report.getOwners().size() - 1) {
                        ownerClause += "'" + report.getOwners().get(i).getOwner() + "'";
                    } else {
                        ownerClause += "'" + report.getOwners().get(i).getOwner() + "'" + " ,";
                    }
                }
                this.setOwners(ownerClause);
                //System.out.println("ownerClause created");
            }
            //This creates the csv file  
            this.generateCSVHeaders(out);

            // Obtain the date to use and convert it from string to date format
            // This will be the date to end with (i.e. show weans up to and including this date)
            myStartDate = getStartDate();
            this.myDate = convertToDateFormat(myStartDate);
            // Use today as the end date -- NO!      
            // Use 60 days before start date to begin at
            //Define a date in a format that supports addition/subtraction
            Calendar myCalDate = Calendar.getInstance();
            //Now set it to my date and subtract 60 days
            myCalDate.setTime(myDate);
            myCalDate.add(Calendar.DATE, -60);
            //Now must change the format to Date, first set it to string
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");     
            String myStringDate = sdf.format(myCalDate.getTime());
            this.myBeginDate = convertToDateFormat(myStringDate);
            
            // Get the array of wean information
            //Pass the owner clause instead of an array since it is already created
            ArrayList<WeanReportDTO> toWeanRows = dao.getWeanDates (myBeginDate, myDate, ownerClause);
            //Need to create rows for the xhtml page here
            toWeanLst2 = toWeanRows;

            //This needs to be in a loop
            //Loop works to generate the csv file rows
            for (WeanReportDTO dto : toWeanRows) {
                try {
                    //Create the fields for the csv file
                    matingID = dto.getMatingID();
                    matingDate = dto.getMatingDate();
                    strain = dto.getStrainName();
                    jrNum = dto.getJrNum();
                    room = dto.getRoom();
                    rack = dto.getRack();
                    rackColumn = dto.getRackColumn();
                    rackRow = dto.getRackRow();
                    owner = dto.getOwner ();
                    birthDate = dto.getBirthDate ();
                    dateToWean = dto.getDateToWean ();
                    litterID = dto.getLitterID ();
                    totalBorn = dto.getTotalBorn ();
                    numFemale = dto.getNumFemale ();
                    numMale = dto.getNumMale ();
                    tagDate = dto.getTagDate ();
                    weanRecorded = dto.getWeanRecorded ();
                    litterStatus = dto.getLitterStatus ();
                    generation = dto.getGeneration ();
                    needsTyping = dto.getNeedsTyping ();
                    cageID = dto.getCageID ();
                    cageName = dto.getCageName ();
                    comment = dto.getComment ();
                    weanNote = dto.getWeanNote ();
                    this.generateCSVData(out, owner, strain, jrNum, room, 
                            rack, rackColumn, rackRow,  
                            cageID, cageName, 
                            matingID, matingDate, generation, needsTyping, weanNote, 
                            litterID, birthDate, dateToWean, weanRecorded, tagDate, 
                            totalBorn, numFemale, numMale, litterStatus, comment);
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
            //Need this to close the file
            out.flush();
            out.close();
        
         } catch (ParseException e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
         }
         catch (IOException ex) {
                Logger.getLogger(WeanReportBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
     public void generateCSVHeaders(BufferedWriter out) {
        try {
            // write the column headers
            out.append("Owner, ");
            out.append("Strain, ");
            out.append("Stock #, ");
            out.append("Room, ");
            out.append("Rack, ");
            out.append("Rack Column, ");
            out.append("Rack Row, ");
            out.append("Cage ID, ");
            out.append("Cage Name, ");
            out.append("Mating ID, ");
            out.append("Mating Date, ");
            out.append("Generation, ");
            out.append("Needs Typing, ");
            out.append("Wean Note, ");
            out.append("Litter ID, ");
            out.append("Birth Date, ");
            out.append("Projected Date To Wean, ");
            out.append("Wean Date Recorded, ");
            out.append("Tag Date, ");
            out.append("Total Born, ");
            out.append("# Female, ");
            out.append("# Male, ");
            out.append("Litter Status, ");
            out.append("Comment, ");
            out.append("\r\n");
            
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void generateCSVData(BufferedWriter out, String owner, String strain, String jrNum, String room, 
            String rack, String rackColumn, String rackRow, 
            String cageID, String cageName,
            String matingID, String matingDate, String generation, String needsTyping, String weanNote, 
            String litterID, String birthDate, String dateToWean, String weanRecorded, String tagDate, 
            String totalBorn, String numFemale, String numMale,  String litterStatus, String comment) {

        try {
            // columns 
            out.append("" + owner + ", ");
            out.append("" + strain + ", ");
            out.append("" + jrNum + ", ");
            out.append("" + room + ", " );
            out.append("" + rack + ", ");
            out.append("" + rackColumn + ", ");
            out.append("" + rackRow + ", "); 
            out.append("" + cageID + ", ");
            out.append("" + cageName + ", ");
            out.append("" + matingID + ", ");
            out.append("" + matingDate + ", ");
            out.append("" + generation + ", ");
            out.append("" + needsTyping + ", ");
            out.append("" + weanNote + ", ");
            out.append("" + litterID + ", ");
            out.append("" + birthDate + ", ");
            out.append("" + dateToWean + ", ");
            out.append("" + weanRecorded + ", ");
            out.append("" + tagDate + ", ");
            out.append("" + totalBorn + ", ");
            out.append("" + numFemale + ", ");
            out.append("" + numMale + ", ");
            out.append("" + litterStatus + ", ");
            out.append("" + comment + ", ");
            out.append("\r\n");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }
    // This will download the result set by capturing all the query information into
    //  an excel sheet
    public void downloadResultsAction() {
        try {
            int pos = 0;
            inputPath = getTempFile().getAbsolutePath();
            String fs = File.separator;

            if (inputPath != null && (!inputPath.equals(""))) {
                pos = inputPath.lastIndexOf(fs);
            }

            if (pos > 0) {
                fileName = inputPath.substring((pos + 1), (inputPath.length()));
            }

            if (fileName != null && (!fileName.equals(""))) {
                new FileDownloadBean().downloadFile(this.inputPath, this.fileName);
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }
    /**
     * @return the owners
     */
    public String getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(String owners) {
        this.owners = owners;
    }        
    /**
     * @return the startDate
     */
    public String getStartDate() {
        startDate = new SimpleDateFormat("MM/dd/yyyy").format(this.report.
                getPbStartDate());
        return startDate;
    } 
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public Date convertToDateFormat(String dateString)throws ParseException{
        DateFormat formatter = null;
        formatter = new SimpleDateFormat("MM/dd/yyyy");

        returnDate =  (Date) formatter.parse(dateString);

        return returnDate;
    }
        /**
     * @return the list of matings that possibly have a litter to wean
     */
    

    public ArrayList<WeanReportDTO> getToWeanLst2() {
        return toWeanLst2;
    }

    /**
     * @param toWeanLst2
     */
    public void setToWeanLst2(ArrayList<WeanReportDTO> toWeanLst2) {
        this.toWeanLst2 = toWeanLst2;
    }
        /**
     * @return the tempFile
     */
    public File getTempFile() {
        return tempFile;
    }

    /**
     * @param tempFile the tempFile to set
     */
    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }
}
