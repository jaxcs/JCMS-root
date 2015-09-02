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

package jcms.middletier.base;

/**
 *
 * @author rkavitha
 */
public interface BTConstantsInterface
{
    // JNDI EJB definitions
    static final String local               = "/local";
    static final String remote              = "/remote";

    // BUSINESS TIER
    // JNDI EJB root path definitions
    static final String baseEJBPath         = "jcms/businesstier/ejb/";
    static final String baseJndiPath        = baseEJBPath + "base/";
    static final String facadeJndiPath      = baseEJBPath + "facade/";

    // Base session bean definitions - JNDI path and class name
    static final String BaseFacade          = baseJndiPath + "BaseFacade";

    // Business Tier session bean definitions - JNDI path and class name
    static final String DocumentFacade          = facadeJndiPath + "DocumentFacade";
    static final String ReportFacade            = facadeJndiPath + "ReportFacade";
    static final String RepositoryFacade        = facadeJndiPath + "RepositoryFacade";
    static final String ServiceFacade           = facadeJndiPath + "ServiceFacade";
    static final String SessionManagerFacade    = facadeJndiPath + "SessionManagerFacade";
    static final String VocabularyFacade        = facadeJndiPath + "VocabularyFacade";
    static final String JCMSWebFacade           = facadeJndiPath + "JCMSWebFacade";

    static final String APPLICATION_PROPERTIES_ROOT = "WEB-INF";
    static final String APPLICATION_PROPERTIES_FILE = APPLICATION_PROPERTIES_ROOT + "/" + "jcms.properties";

    // RS service names aka Data Load Constants aka thread names
    static final String RS_IMPORTSTRAINS            = "Import_Strains";
    static final String RS_IMPORTINSTITUTIONS       = "Import_Institutions";

    static final String RS_REFRESHINVENTORYALERTS   = "Refresh_InventoryAlerts";
    static final String RS_REFRESHPROCEDUREALERTS   = "Refresh_ProcedureAlerts";
    static final String RS_REFRESHPROJECTALERTS     = "Refresh_ProjectAlerts";
    static final String RS_REFRESHSCHEDULEALERTS    = "Refresh_ScheduleAlerts";
    static final String RS_REFRESHORDERALERTS       = "Refresh_OrderAlerts";

    // Table cv_TimeUnit Constants
    // Maps to cv_TimeUnit tables primary key value.
    static final Integer TIMEUNIT_MINUTE            = 1;
    static final Integer TIMEUNIT_HOUR              = 2;
    static final Integer TIMEUNIT_DAY               = 3;
    static final Integer TIMEUNIT_WEEK              = 4;

    static final Short  SUCCESS                     = 1;
    static final Short  FAILURE                     = 0;
}






