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

package jcms.web.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.web.service.ControlledVocabularyService;
import jcms.web.service.RepositoryService;
import org.jax.cs.rscommon.LogIt;
import org.jax.cs.rscommon.RSUtils;

/**
 * <b>File name:</b>  WTBaseObject.java  <p>
 * <b>RsDate developed:</b>  October 2009 <p>
 * <b>Purpose:</b>  Ancestor to all Web Tier classes. <p />
 * <b>Overview:</b>  Provides common functionality to all classes.  <p />
 * <b>Last changed by:</b>   $Author: cnh $ <p>
 * <b>Last changed date:</b> $Date: 2012-12-17 11:50:00 -0500 (Mon, 17 Dec 2012) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 18958 $
 */
public class WTBaseObject implements WTBaseWebInterface
{
    private RepositoryService           repositoryService = null;
    private ControlledVocabularyService vocabularyService = null;

    private String  htmlBreak   = "<br />" ;
    private String  htmlQuote   = "&#34;" ;

    private LogIt   btLogger    = null;
    private String  message     = "";

    public static final String CUSTOM_MESSAGES_CLASS = "jcms.web.resources.CustomMessages";
    public static final String UI_MESSAGES_CLASS = "jcms.web.resources.UIResources";

    /**
     * constructor
     */
    public WTBaseObject()
    {
        RSUtils.setCustomResourceBundle( ResourceBundle.getBundle( CUSTOM_MESSAGES_CLASS ) );
        RSUtils.setUIResourceBundle( ResourceBundle.getBundle( UI_MESSAGES_CLASS ) );
    }

    /**
     * @return the btLogger
     */
    public LogIt getLogger()
    {
        if (btLogger == null)
            btLogger = new LogIt(this.getClass().getName());

        return btLogger;
    }
    
    /**
     * Applies a standard format on the message logged.
     * @param message the message to set
     * @param methodName the method name
     * @return String standard format message
     */
    public String formatLogMessage(String message, String methodName)
    {
        String logMsg = "[" + methodName + "] " ;
        this.message = logMsg + message;

        return this.message;
    }

    /**
     * @return the htmlBreak
     */
    public String getHtmlBreak() {
        return htmlBreak;
    }

    /**
     * @return the htmlQuote
     */
    public String getHtmlQuote() {
        return htmlQuote;
    }
    
    // HTTP Protocol Methods

    /**
     * <b>Purpose:</b> Get a reference to the current Faces context. <br />
     * @return FacesContext current faces context
     */
    protected FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    /**
     * <b>Purpose:</b> Add a message to the FacesContext message list and it 
     *      will display when control returns to the web page from a backing 
     *      bean.  <br />
     * <b>Overview:</b> Ideal mechanism for displaying informational, warning, 
     *      and error messages.  All pages have a reference tag to <h:messages>.
     *      The messages tag is where the text will be rendered.  <br />
     * @param message user friendly message
     * @param severity FacesMessage enumerated type Severity, FacesMessage.SEVERITY_ERROR ...
     * @return FacesContext current faces context
     */
    protected void addToMessageQueue(String message, Severity severity)
    {
        // Display user friendly error message
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(severity);
        facesMessage.setSummary(message);
        this.getFacesContext().addMessage("PlaceHolderClientId", facesMessage);
    }


    /**
     * <b>Purpose:</b> Get the value of the map name from the HTTP Request Parameter map. <br />
     * @param key map name
     * @return String value for map name
     */
    protected String getRequestParameter(String key)
    {
        String value = getFacesContext().getExternalContext().getRequestParameterMap().get(key);

        return value;
    }

    /**
     * <b>Purpose:</b> As a debugging feature, this logs all the HTTP Request map
     *      parameters to the Application Server console. <br />
     * @param key map name
     */
    protected void displayRequestParameters()
    {
        this.getLogger().logInfo(this.formatLogMessage("HTTP REQUEST PARAMETERS", "displayRequestParameter"));
        Map<String, String> map = getFacesContext().getExternalContext().getRequestParameterMap();

        for (Map.Entry<String, String> entry: map.entrySet())
        {
            this.getLogger().logInfo(this.formatLogMessage("  [key,value] " +
                    "[" + entry.getKey() + "," + entry.getValue() + "]", "displayRequestParameter"));
        }
    }

    /**
     * <b>Purpose:</b> The purpose is to return the institution primary key
     *      from the http request parameter.  <br />
     * <b>Overview:</b> The add and edit action from the list view places the
     *      primary key in the http request as a parameter.  <br />
     * @param parameterName name of parameter to get from request scope
     * @return Integer valid primary key or 0 to add new
     */
    protected Integer getKey(String parameterName)
    {
        // Primary key passed from list view to form view.
        // Map returns 0 if value not found.
        Integer rtnKey    = 0;
        Object  key       = this.getRequestParameter(parameterName);

        if (key != null)
        {
            try
            {
                rtnKey  = Integer.parseInt((String)key);
            }
            catch (ClassCastException e)
            {
                // Add new record.
                rtnKey = 0;
                this.getLogger().logWarn(this.formatLogMessage("ClassCastException " +
                        "Error trying to cast an object to Integer.", "getKey"));
            }
            catch (NumberFormatException nfe)
            {
                // Add new record.
                rtnKey = 0;
                this.getLogger().logWarn(this.formatLogMessage("NumberFormatException " +
                        "Error, object is not an Integer.", "getKey"));
            }

        }
        else
            this.getLogger().logDebug(this.formatLogMessage("Action is to ADD a new record.", "getKey"));


        return rtnKey;
    }

    /**
     * <b>Purpose:</b> Take a list of entity and return the smallest key. <br />
     * <b>Overview:</b> The primary intent of this method is to aid UI movement of
     *      dynamic rows of data.  Specifically data that does not have a primary
     *      key assignment yet.  This method is useful for list of type ITBaseEntityInterface
     *      for providing a unique key before submission to the database. this
     *      method can be passed any list of entity to get the next lowest key value.  <br />
     * @param List<ITBaseEntityInterface> the generic entity list
     * @return Integer smallest and available primary key value
     */
    protected synchronized Integer getSmallestNegativeKey(List<ITBaseEntityInterface> list)
    {
        Integer minKey = 0;

        if ((list != null) && (list.size() > 0))
        {
            for (ITBaseEntityInterface e: list)
            {
                if ((e.getKey() != null) && (e.getKey().intValue() < minKey.intValue()))
                {
                    minKey = e.getKey();
                }
            }
        }

        return minKey;
    }

    /**
     * Common Session parameters
     * Variable                 Type
     * --------                 ----
     * userEntity		UserEntity
     * loggedUser		String
     * workgroupEntityLst	List<WorkgroupEntity>
     * ownerEntityLst		List<OwnerEntity>
     * masterAdmin		Boolean
     * 
     * this.getSessionParameter(String key) returns Object
     * 
     */
    
    /**
     * <b>Purpose:</b> Store an object in the HTTP Session Parameter map. <br />
     * <b>Overview:</b> Operation requires a string type key to uniquely
     *      identify the object.  Object is any type thing to be stored.  <br />
     * @param key map name
     * @return Object value of map name
     */
    protected void putSessionParameter(String key, Object value)
    {
        Object put = getFacesContext().getExternalContext().getSessionMap().put(key, value);
    }

    /**
     * <b>Purpose:</b> Get the value of the map name from the HTTP Session Parameter map. <br />
     * @param key map name
     * @return Object value of map name
     */
    protected Object getSessionParameter(String key)
    {
        Object object = null;
        FacesContext fc = getFacesContext();
        if ( fc != null ) {
            ExternalContext ec = fc.getExternalContext();
            if ( ec != null ) {
                Map<String, Object> map = ec.getSessionMap();
                if ( map != null ) {
                    object = map.get( key );
                }
            }
        }

        return object;
    }

    /**
     * <b>Purpose:</b> Remove an object from the HTTP Session Parameter map. <br />
     * <b>Overview:</b> Operation requires a string type key to uniquely
     *      identify the object.  <br />
     * @param key map name
     */
    protected void removeSessionParameter(String key)
    {
        Object removed = getFacesContext().getExternalContext().getSessionMap().remove(key);
    }

    /**
     * <b>Purpose:</b> As a debugging feature, this logs all the HTTP Session map
     *      parameters to the Application Server console. <br />
     * @param key map name
     */
    protected void displaySessionParameters()
    {
        this.getLogger().logInfo(this.formatLogMessage("HTTP SESSION PARAMETERS", "displaySessionParameters"));
        Map<String, Object> map = getFacesContext().getExternalContext().getSessionMap();

        for (Map.Entry<String, Object> entry: map.entrySet())
        {
            this.getLogger().logInfo(this.formatLogMessage("  [key,value] " +
                    "[" + entry.getKey() + "," + entry.getValue().toString() + "]", "displaySessionParameters"));
        }

        this.getLogger().logInfo(this.formatLogMessage("HTTP AUTHENTICATION PARAMETERS", "displaySessionParameters"));
        ExternalContext ext = getFacesContext().getExternalContext();
        String authType = ext.getAuthType();
        String remoteUser = ext.getRemoteUser();
        getLogger().logInfo( formatLogMessage( "  authType       = " + authType, "displaySessionParameters" ) );
        getLogger().logInfo( formatLogMessage( "  remoteUser     = " + remoteUser, "displaySessionParameters" ) );

        map = getFacesContext().getExternalContext().getApplicationMap();

        for (Map.Entry<String, Object> entry : map.entrySet() ) {
            this.getLogger().logInfo(this.formatLogMessage("  [key],[value] " +
                    "[" + entry.getKey() + "],[" + entry.getValue() + "]", "displaySessionParameter"));
        }
    }

    /**
     * <b>Purpose:</b> Get an object from the HTTP Protocol session scope. <br />
     * @param parameterName the parameter name
     * @return Object the object null if not found
     */
    protected Object getObjectFromSession(String parameterName)
    {
        Object  object  = this.getSessionParameter(parameterName);

        return object;
    }
    
    protected ArrayList<String> getCurrentUserGuestWorkgroups() {
        ArrayList<String> workgroups = new ArrayList<String>();
        List<WorkgroupEntity> workgroupList = (List<WorkgroupEntity>) this.getSessionParameter("guestWorkgroupEntityLst");
        for (WorkgroupEntity entity : workgroupList) {
            workgroups.add(entity.getWorkgroupName());
        }
        
        return workgroups;
    }
    
    protected ArrayList<String> getCurrentUserColonyManageWorkgroups() {
        ArrayList<String> workgroups = new ArrayList<String>();
        List<WorkgroupEntity> workgroupList = (List<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst");
        for (WorkgroupEntity entity : workgroupList) {
            workgroups.add(entity.getWorkgroupName());
        }
        
        return workgroups;
    }

    /**
     * <b>Purpose:</b> Wraps a list of ITBaseEntityInterface and a list of objects
     *      to delete as a ITBaseEntityTable object <br />
     * @param listToSave objects to wrap and save
     * @param listToDelete objects to wrap and delete
     * @return ITBaseEntityTable table transfer object for list of entities
     */
    protected ITBaseEntityTable convertToTable(List<ITBaseEntityInterface> listToSave,
                                            List<ITBaseEntityInterface> listToDelete)
    {
        ITBaseEntityTable   table = new ITBaseEntityTable();

        // Save
        table.addSave(listToSave);

        // Delete
        table.addDelete(listToDelete);

        return table;
    }
    
    /**
     * <b>Purpose:</b> Wraps an ITBaseEntityInterface and list of objects to
     *      delete as a ITBaseEntityTable object <br />
     * @param itemToSave object to wrap and save
     * @param listToDelete objects to wrap and delete
     * @return ITBaseEntityTable table transfer object for list of entities
     */
    protected ITBaseEntityTable convertToTable(ITBaseEntityInterface itemToSave,
                                            List<ITBaseEntityInterface> listToDelete)
    {
        ITBaseEntityTable   table = new ITBaseEntityTable();

        // Save
        table.addSave(itemToSave);

        // Delete
        table.addDelete(listToDelete);

        return table;
    }

    // GETTERS AND SETTERS

    /**
     * @return the repositoryService
     */
    protected RepositoryService getRepositoryService()
    {
        if (repositoryService == null)
            repositoryService = new RepositoryService();

        return repositoryService;
    }

    /**
     * @param repositoryService the repositoryService to set
     */
    protected void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * @return the vocabularyService
     */
    protected ControlledVocabularyService getVocabularyService()
    {
        if (vocabularyService == null)
            vocabularyService = new ControlledVocabularyService();

        return vocabularyService;
    }

    /**
     * @param vocabularyService the vocabularyService to set
     */
    protected void setVocabularyService(ControlledVocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }


    /**
     * Given the name of a resource in the CustomMessages properties file, returns
     * the matching resource string.
     * @param resourceName the name of the resource
     * @return the string matching the resource name in CustomMessages.properties
     */
    public static String getCustomResourceString( String resourceName ) {
        return getResourceString( CUSTOM_MESSAGES_CLASS, resourceName );
    }

    /**
     * Given a fully-qualified resource class and the name of a resource inside
     * of that resource class, returns the matching resource string. If the
     * resource is a properties file, drop the '.properties' extension and
     * prepend the package name to the file.<br /><br />
     *
     * For example, in the RSWebTier, the custom properties file is named
     * CustomMessages.properties and exists in the rs.web.resources package.
     * Thus, the resourceClass string would be "rs.web.resources.CustomMessages".
     *
     * NOTE: For reasons I don't understand, this code fails when placed inside
     *       of RSCommon. I tried getting it to work many times, but it always
     *       threw a resource not found exception. Probably a classloader issue.
     *       Try annotating the variable as transient in RSCommon!!!!  (CNH)
     *
     * @param resourceClass the name of the resource class
     * @param resourceName the name of the resource
     * @return the string matching the resource name in the given resource class
     */
    public static String getResourceString( String resourceClass, String resourceName ) {
        Locale locale = Locale.getDefault();

        ResourceBundle rb = ResourceBundle.getBundle( resourceClass, locale );
        return rb.getString( resourceName );
    }    
    
    
    /**
     *
     * @return
     */
    public UserEntity getUserEntity() {
        return (UserEntity) this.getSessionParameter("userEntity");
    }
}