/*
 * =======================================================
 * WARRANTY DISCLAIMER AND COPYRIGHT NOTICE
 *
 * THE JACKSON LABORATORY MAKES NO REPRESENTATION ABOUT THE SUITABILITY OR
 * ACCURACY OF THIS SOFTWARE OR DATA FOR ANY PURPOSE, AND MAKES NO WARRANTIES,
 * EITHER EXPRESS OR IMPLIED, INCLUDING MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE OR THAT THE USE OF THIS SOFTWARE OR DATA WILL NOT
 * INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS, OR OTHER RIGHTS.
 * THE SOFTWARE AND DATA ARE PROVIDED "AS IS".
 *
 * This software and data are provided to enhance knowledge and encourage
 * progress in the scientific community and are to be used only for research
 * and educational purposes. Any reproduction or use for commercial purpose is
 * prohibited without the prior express written permission of
 * the Jackson Laboratory.
 *
 * Copyright © 1996, 1999, 2000, 2007 by The Jackson Laboratory
 * All Rights Reserved
 * =======================================================
 */

package jcms.web.base;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import jcms.middletier.dto.ValidationMessageDTO;
import jcms.middletier.exception.SaveException;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.dao.DbSetupDAO;
import jcms.web.resources.PropertyManager;

public class WTBaseBackingBean extends WTBaseObject 
{
    protected static final String HASADMINISTRATION = "hasAdministration";
    protected static final String HASQUERYING = "hasQuerying";
    protected static final String HASREPORTING = "hasReporting";
    protected static final String HASCOLONYMANAGEMENT = "hasColonyManagement";
    protected static final String FA_ADMINISTRATION = "Administration";
    protected static final String FA_QUERYING = "Querying";
    protected static final String FA_REPORTING = "Reporting";
    protected static final String FA_COLONYMANAGEMENT = "ColonyManagement";
    
    //date format, found in DbSetup table
    private String date_format = determineDateFormat();
    private String date_format_with_time = date_format + " hh:mm:ss a";

    
    private int numberDataTableRows;          // zero hides data scroller
    
    public WTBaseBackingBean() {
        try {
            initiailizeUserSession();
        } catch ( LoginException le ) {
            
        }
    }

    private int debug = 0;

    // ACTION:  Remove at a later date.
    @Deprecated

    /**
     * <b>Purpose:</b> Get the value of the cookie key. <br />
     * @param key cookie name
     * @return String cookie value
     */
    protected String getCookie(String key)
    {
        PropertyManager pm          = new PropertyManager();
        Cookie          cookie      = pm.getCookie(key);
        String          cookieValue = "";
        
        try
        {
            cookieValue = cookie.getValue();
        }
        catch (NullPointerException e)
        {
            cookieValue = "";
        }

        return cookieValue;
    }

    /**
     * <b>Purpose:</b> Set the cookie key and value.  <br />
     * @param key cookie name
     * @param value cookie value
     */
    protected void setCookie(String key, String value)
    {
        PropertyManager pm = new PropertyManager();
        pm.setCookie(key, value);
    }

    /**
     * <b>Purpose:</b> Process the save exception text for displaying to the user.  <br />
     * @param ex exception thrown for a failed save
     * @param value cookie value
     * @return String exception text formatted for user display
     */
    protected String formatException(SaveException ex)
    {
        StringBuffer message = new StringBuffer();

        // Format exception for display.
        for (ValidationMessageDTO dto: ex.getMessages().getMessageList())
        {
            message.append(dto.getFieldName() + ":  " + dto.getErrorMessage() + this.getHtmlBreak());
        }

        return message.toString();
    }
    
    protected boolean containsIgnoreCase(List<String> vals, String val){
        for(String val1 : vals){
            if(val1.equalsIgnoreCase(val)){
                return true;
            }
        }
        return false;
    }

    protected void displayTable(ITBaseEntityTable table)
    {
        List<ITBaseEntityInterface> saveList = table.getSaveList();
        List<ITBaseEntityInterface> deleteList = table.getDeleteList();

        if (saveList != null)
        {
            this.getLogger().logInfo(this.formatLogMessage("Objects to Save", "displayTable"));

            for (ITBaseEntityInterface e: saveList)
            {
                this.getLogger().logInfo(this.formatLogMessage("Class is " +
                    e.getClass().getSimpleName() + "\tprimary key = "+ e.getKey(), "displayTable"));
            }
        }

        if (deleteList != null)
        {
            this.getLogger().logInfo(this.formatLogMessage("Objects to Delete", "displayTable"));

            for (ITBaseEntityInterface e: deleteList)
            {
                this.getLogger().logInfo(this.formatLogMessage("Class is " +
                    e.getClass().getSimpleName() + "\tprimary key = "+ e.getKey(), "displayTable"));
            }
        }
    }

    // GETTERS AND SETTERS

    // PRIVATE METHODS

    private void initiailizeUserSession() throws LoginException
    {
        // Just after the user is authenticated and this code is entered, the
        // MainBean.USERSESSIONNAME is null. If it is null, fill it in with
        // the UserDTO corresponding to this principal.
        /*if ( getSessionParameter( MainBean.USERSESSIONNAME ) == null ) {
            Principal principal = getFacesContext().getExternalContext().getUserPrincipal();

            if ( principal == null ) {
                throw new LoginException( "User has no valid role(s) for this application." );
            }
            UserDTO userDTO = new RepositoryService().findUser( principal.getName() );

            // Set the current workgroup to the default workgroup.
            userDTO.setCurrentWorkgroup( userDTO.getUserEntity().getDefaultWorkgroupkey() );
            this.putSessionParameter( MainBean.USERSESSIONNAME, userDTO );
        }*/
    }

    public boolean getHasAdministration () {
        boolean hasIt = this.getSessionParameter(WTBaseBackingBean.HASADMINISTRATION).toString() == "true";
        return hasIt;
    }
    
    public boolean getHasReporting () {
        boolean hasIt = this.getSessionParameter(WTBaseBackingBean.HASREPORTING).toString() == "true";
        return hasIt;
    }
    
    public boolean getHasQuerying () {
        boolean hasIt = this.getSessionParameter(WTBaseBackingBean.HASQUERYING).toString() == "true";
        return hasIt;
    }
    
    public boolean getHasColonyManagement () {
        boolean hasIt = this.getSessionParameter(WTBaseBackingBean.HASCOLONYMANAGEMENT).toString() == "true";
        return hasIt;
    }
    
    public FacesMessage getMessage(FacesContext context, String key) {
        Locale locale = null;

        UIViewRoot root = context.getViewRoot();

         if (root != null)
         {
             locale = root.getLocale();
         }
         if (locale == null)
         {
             locale = Locale.getDefault();
         }

         ResourceBundle bundle = ResourceBundle.getBundle(
                 "com.apress.javaxml.i18n.messages", locale);
         String msg = bundle.getString(key);
         return new FacesMessage(msg);
     }

    public void validateDateAction(ValueChangeEvent event) {
        try {
            Date d = (Date) event.getNewValue();
            validateDateAction(d);
        } catch (Exception e) {
            this.addToMessageQueue("Invalid date format please update.", FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Validation ", "Invalid date format please update."));
        }
    }
        
    /*
     * This method is called on change event on start date and end date to 
     * validate
     */
    public void validateDateAction(Date newValue) {  
        boolean flag = true;
        
        // date cannot be greater than today
        if (newValue.compareTo(new Date()) > 0) {
            this.addToMessageQueue("Date cannot be greater than Today", 
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "Date cannot be greater than Today"));
            flag = false;
        }
    }
    
    protected static boolean isNumeric(String str)
    {
      NumberFormat formatter = NumberFormat.getInstance();
      ParsePosition pos = new ParsePosition(0);
      formatter.parse(str, pos);
      return str.length() == pos.getIndex();
    }

    protected static boolean isInteger(String str)
    {
        boolean isInteger = false;
        try {
            if (str != null && str.length() > 0 && (str.indexOf(".") == -1)) {
                Integer.parseInt(str);
                isInteger = true;
            }
        } catch (Exception e) {
            isInteger = false;
        }
        return isInteger;
    }

    /**
     * date validation using SimpleDateFormat
     * it will take a string and make sure it's in the proper
     * format as defined by you, and it will also make sure that
     * it's a legal date
     * @param date
     * @param dateFormat
     * @return 
     */
    public boolean isValidDate(String date, String dateFormat)
    {
        // set date format, this can be changed to whatever format
        // you want, MM-dd-yyyy, MM.dd.yyyy, dd.MM.yyyy etc.
        // you can read more about it here:
        // http://java.sun.com/j2se/1.4.2/docs/api/index.html

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        // declare and initialize testDate variable, this is what will hold
        // our converted string

        Date testDate = null;

        // we will now try to parse the string into date form
        try
        {
          testDate = sdf.parse(date);
        }

        // if the format of the string provided doesn't match the format we
        // declared in SimpleDateFormat() we will get an exception

        catch (ParseException e)
        {
          return false;
        }

        // dateformat.parse will accept any date as long as it's in the format
        // you defined, it simply rolls dates over, for example, december 32
        // becomes jan 1 and december 0 becomes november 30
        // This statement will make sure that once the string
        // has been checked for proper formatting that the date is still the
        // date that was entered, if it's not, we assume that the date is invalid

        if (!sdf.format(testDate).equals(date))
        {
          return false;
        }

        // if we make it to here without getting an error it is assumed that
        // the date was a valid one and that it's in the proper format

        return true;
    } 
    
    public int getNumberDataTableRows() {
        return this.numberDataTableRows;
    }
    
    public void setNumberDataTableRows(int rows) {
        this.numberDataTableRows = rows;
    }
    
    private String determineDateFormat(){
        //Find the MM, yyyy, and dd - use the separator variable as well
        return new DbSetupDAO().getJCMSWebDateFormat().getMTSValue();
    }
    
    /**
     * @return the date_format
     */
    public String getDate_format() {
        return date_format;
    }

    /**
     * @param date_format the date_format to set
     */
    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }

    /**
     * @return the date_format_with_time
     */
    public String getDate_format_with_time() {
        return date_format_with_time;
    }

    /**
     * @param date_format_with_time the date_format_with_time to set
     */
    public void setDate_format_with_time(String date_format_with_time) {
        this.date_format_with_time = date_format_with_time;
    }
    
}