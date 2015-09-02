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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedMap;
import javax.faces.application.FacesMessage;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.dto.CageSummaryDTO;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.DateIterator;
import jcms.web.common.FileDownloadBean;
import jcms.web.common.SelectItemWrapper;
import jcms.web.dto.SimpleDTO;


/**
 *
 * @author rkavitha
 */
public class CageSummaryBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 02445L;
    private SelectItemWrapper selectItemWrapper = new SelectItemWrapper();
    private CageSummaryDTO report = null;
    private ReportFacadeLocal rfl;
    private String fileName = "";
    private String inputPath = "";
    private String sDate = "";
    private String eDate = "";
    private String cStatus = "";
    private String owners = "";
    private String rName = "";
    private File tempFile;
    private ArrayList<SimpleDTO> activePensLst = null;
    private ArrayList<SimpleDTO> datesLst = null;

    public CageSummaryBean() {
       this.initialize();
    }
    
    private void initialize() {
        rfl = new BusinessTierPortal().getReportFacadeLocal();
        report = new CageSummaryDTO();
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

    public String cageSummaryReportAction() {
        this.initialize();

        return "cageReport";
    }

    public String runReportAction() {
        boolean validDates = this.validateDates();
        boolean validStatus = this.validateStatus();
        if (validDates && validStatus)
        {
            activePensLst = new ArrayList<SimpleDTO>();
            datesLst = new ArrayList<SimpleDTO>();

            if (this.report.getOwners() == null
                    || this.report.getOwners().isEmpty()) {
                System.out.println("ownerLst initialzed");
                this.report.setOwners((ArrayList<OwnerEntity>) 
                        getSessionParameter("guestOwnerEntityLst"));
            }

            if (this.validateDates() && this.validateStatus()) {
                this.generateOutput();
            }
            
            return "cageReportResults";
        }
        else
            return null;
    }

    /*
     * This method is called on change event on status update
     */
    public boolean validateStatus() {
        if (this.report.getStatus() != null && this.report.isIsBillable()) {
            this.addToMessageQueue("Select 'Status' from the list or select "
                    + "'All Billable' but not both", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Select 'Status' from the list or select "
                    + "'All Billable' but not both"));
            return false;
        }
        return true;
    }

    /*
     * This method generates output for the report, get the number of dates with 
     * in a date range and executes the query to get pen count for each day. Also,
     * generate output in an excel sheet.
     */
    public void generateOutput() {
        try {
            Result result = null;
            SimpleDateFormat formatter = null;
            String currentDt = "";

            SimpleDTO dto;
            SimpleDTO sdto;
            String status = "";
            String room = "";
            long penCnt = 0;
            cStatus = "''";
            rName = "''";
            setOwners("''");

            // Create temp file.
            tempFile = File.createTempFile("cageSummary", ".csv");

            // Delete temp file when program exits.
            tempFile.deleteOnExit();

            // Write to temp file
            BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));

            // set the search criteria to display in output
            // if status is selected
            if (report.getStatus() != null && !report.getStatus().getContainerStatus().equals("")
                    && report.getStatus().getContainerStatus() != null) {
                this.setcStatus("'" + report.getStatus().getContainerStatus() + "'");
            } else if (this.report.isIsBillable()) {
                this.setcStatus("'All Billable'");
            }

            // if room is selected
            if (report.getRoom() != null && !report.getRoom().getRoomName().equals("")
                    && report.getRoom().getRoomName() != null) {
                // if all billable status is selected, then check for the status
                // that are billable
                this.setrName("'" + report.getRoom().getRoomName() + "'");
            }
            
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
            }

            // write the search criteria          
            this.generateCSVHeaders(out);

            Iterator<Date> i = new DateIterator(report.getPbStartDate(),
                    report.getPbbEndDate());
            // iterate through dates with in a date range                
            while (i.hasNext()) {
                result = null;
                sdto = new SimpleDTO();
                dto = new SimpleDTO();

                Date date = i.next();

                // (2) create our date "formatter" (the date format we want)
                formatter = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd-hh.mm.ss");

                // (3) create a new String using the date format we want
                currentDt = formatter.format(date);
                
                sdto.setCurrentDate(currentDt);
                datesLst.add(sdto);
                out.append(currentDt);

                out.append("\r\n");

                // (4) this prints "Folder Name = 2009-09-06-08.23.23"
                System.out.println("Date " + currentDt);
                // get the pen count for each day
                result = rfl.findActivePenCount(report, currentDt);
                SortedMap[] map = result.getRows();

                for (int j = 0; j < map.length; ++j) {
                    dto = new SimpleDTO();
                    status = "";
                    room = "";
                    penCnt = 0;

                    dto.setCurrentDate(currentDt);

                    // get the pen count
                    if (map[j].get("Pens") != null) {
                        penCnt = (Long) map[j].get("Pens");
                        dto.setPenCnt(penCnt);
                    }

                    // get the room
                    if (map[j].get("RoomName") != null) {
                        room = map[j].get("RoomName").toString();
                        dto.setRoom(room);
                    }

                    // get the status
                    if (map[j].get("containerStatus") != null) {
                        status = map[j].get("containerStatus").toString();
                        dto.setStatus(status);
                    }

                    // append to CSV file for the appropriate pen billing options
                    if ((sdto.getCurrentDate() != null
                            && !sdto.getCurrentDate().equals(""))
                            && (dto.getCurrentDate() != null && !dto.getCurrentDate().equals(""))
                            && sdto.getCurrentDate().equals(dto.getCurrentDate())) {
                        // write the data rows to file
                        this.generateCSVData(out, penCnt, status, room);
                    }
                    activePensLst.add(dto);
                }
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void generateCSVHeaders(BufferedWriter out) {
        try {
            // write the search criteria
            out.append("Status = " + this.getcStatus() + ", ");
            out.append("Room = " + this.getrName() + ", ");
            out.append("Owners/Workgroups = " + this.getOwners() + ", ");
            out.append("Group By = '" + this.report.getGroupBy() + "', ");
            out.append("Cage Billing = '" + this.report.getPenBilling() + "'");
            out.append("\r\n");
            out.append("\r\n");

            // write the column headers
            out.append("Date, ");
            out.append("# Cages, ");

            if (report.getGroupBy().equalsIgnoreCase("none")) {
                out.append(" ");
            } else if (report.getGroupBy().equalsIgnoreCase("status")) {
                out.append("Status ");
            } else if (report.getGroupBy().equalsIgnoreCase("room")) {
                out.append("Room");
            }
            out.append("\r\n");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    public void generateCSVData(BufferedWriter out, long penCnt, String status,
            String room) {
        try {
            // first column "
            out.append(" , ");
            out.append("" + penCnt + ", ");
            if (report.getGroupBy().equalsIgnoreCase("none")) {
                out.append("  ");
            } else if (report.getGroupBy().equalsIgnoreCase("status")) {
                out.append(status);
            } else if (report.getGroupBy().equalsIgnoreCase("room")) {
                out.append(room);
            }
            out.append("\r\n");
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }

    /*
     * Set the appropriate values for the lists for various billing options
     */
    public void setPenBillingOptions(SimpleDTO sdto, ArrayList<SimpleDTO> datesLst, 
            String currentDt, BufferedWriter out) {
        SimpleDateFormat formatter = formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (report.getPenBilling() != null && (!report.getPenBilling().
                    equals(""))) {
                if (report.getPenBilling().equalsIgnoreCase("full day")) {
                    // if full day, then eliminate start date and end date
                    if (report.getPbStartDate() != null && report.getPbbEndDate() != null
                            && !formatter.format(report.getPbStartDate()).equals(currentDt)
                            && !formatter.format(report.getPbbEndDate()).equals(currentDt)) {
                        sdto.setCurrentDate(currentDt);
                        datesLst.add(sdto);
                        out.append(currentDt);
                    }
                } else if (report.getPenBilling().equalsIgnoreCase("Partial First Day")) {
                    // in case of single day
                    if (report.getPbStartDate() != null && report.getPbbEndDate() != null
                            && formatter.format(report.getPbStartDate()).equals(formatter.format(report.getPbbEndDate()))) {
                        sdto.setCurrentDate(currentDt);
                        datesLst.add(sdto);
                        out.append(currentDt);
                    } // if partial first day, then eliminate end date
                    else if (report.getPbbEndDate() != null
                            && !formatter.format(report.getPbbEndDate()).equals(currentDt)) {
                        sdto.setCurrentDate(currentDt);
                        datesLst.add(sdto);
                        out.append(currentDt);
                    }
                } else if (report.getPenBilling().equalsIgnoreCase("Partial Last Day")) {
                    // in case of single day
                    if (report.getPbStartDate() != null && report.getPbbEndDate() != null
                            && formatter.format(report.getPbStartDate()).equals(formatter.format(report.getPbbEndDate()))) {
                        sdto.setCurrentDate(currentDt);
                        datesLst.add(sdto);
                        out.append(currentDt);
                    } // if partial last day, then eliminate end date
                    else if (report.getPbStartDate() != null
                            && !formatter.format(report.getPbStartDate()).equals(currentDt)) {
                        sdto.setCurrentDate(currentDt);
                        datesLst.add(sdto);
                        out.append(currentDt);
                    }
                } else if (report.getPenBilling().equalsIgnoreCase("Any Day")) {
                    // if any day, then don't eliminate any date
                    sdto.setCurrentDate(currentDt);
                    datesLst.add(sdto);
                    out.append(currentDt);
                }
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
    }
    
    /**
     *  <b>Purpose:</b>  Download mouse query search results. <br>
     *  <b>Overview:</b>  Action to run mating query by initiating MouseQueryBO
     *  and download the result set by capturing all the query information into
     *  an excel sheet. <br>
     */
    public void downloadSearchResultsAction() {
        try {
            int pos = 0;
            inputPath = getTempFile().getAbsolutePath();
            String fs = File.separator;

            if (inputPath != null && (!inputPath.equals(""))) {
                // get the file name from file path
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

    /*
     * Validate date range between start date and end date
     * Should not be greate than 60
     */
    public boolean validateDates() {
        Calendar stDate = Calendar.getInstance();
        stDate.setTime(this.report.getPbStartDate());
        Calendar enDate = Calendar.getInstance();
        enDate.setTime(this.report.getPbbEndDate());

        long milliseconds1 = stDate.getTimeInMillis();
        long milliseconds2 = enDate.getTimeInMillis();

        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        // date range cannot be greater than 2 months
        if (diffDays > 61) {
            this.addToMessageQueue("Cage billing date range cannot be greater "
                    + "than two months", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Cage billing date range cannot be greater "
                    + "than two months "));
            return false;
        }

        // start date cannot be greater than end date
        if (this.report.getPbStartDate().compareTo(this.report.getPbbEndDate()) > 0) {
            this.addToMessageQueue("Cage billing Start date cannot be greater "
                    + "than Cage billing End date", FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Cage billing Start date cannot be greater "
                    + "than Cage billing End date"));
            return false;
        }
        return true;
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
    public CageSummaryDTO getReport() {
        return report;
    }

    /**
     * @param report the report to set
     */
    public void setReport(CageSummaryDTO report) {
        this.report = report;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the activePensLst
     */
    public ArrayList<SimpleDTO> getActivePensLst() {
        return activePensLst;
    }

    /**
     * @param activePensLst the activePensLst to set
     */
    public void setActivePensLst(ArrayList<SimpleDTO> activePensLst) {
        this.activePensLst = activePensLst;
    }

    /**
     * @return the datesLst
     */
    public ArrayList<SimpleDTO> getDatesLst() {
        return datesLst;
    }

    /**
     * @param datesLst the datesLst to set
     */
    public void setDatesLst(ArrayList<SimpleDTO> datesLst) {
        this.datesLst = datesLst;
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

    /**
     * @return the sDate
     */
    public String getsDate() {
        sDate = new SimpleDateFormat("MM/dd/yyyy").format(this.report.
                getPbStartDate());
        return sDate;
    }

    /**
     * @param sDate the sDate to set
     */
    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    /**
     * @return the eDate
     */
    public String geteDate() {
        eDate = new SimpleDateFormat("MM/dd/yyyy").format(this.report.
                getPbbEndDate());
        return eDate;
    }

    /**
     * @param eDate the eDate to set
     */
    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    /**
     * @return the cStatus
     */
    public String getcStatus() {
        return cStatus;
    }

    /**
     * @param cStatus the cStatus to set
     */
    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }

    /**
     * @param rName the rName to set
     */
    public void setrName(String rName) {
        this.rName = rName;
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
}
