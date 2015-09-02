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

/**
 *
 * @author rkavitha
 */
public interface WTBaseWebInterface
{
    // Cookie Definitions
    static final String     DOMAINNAME      = ".jax.org";
    static final String     APPNAME         = "jcms";
    static final Integer    TIMETOEXPIRE    = (5 * 365 * 24 * 60 * 60);  // Expire in 5 years

    // Page Definitions
    static final String     POPUP             = ".pop";
    static final String     NAVIGATOR         = ".nav";
    static final String     SELECTLINES       = ".sl";

    // Popup Preferences
    static final String POPUPNAVIGATORSELECTLINES   = 
            APPNAME + POPUP + NAVIGATOR + SELECTLINES ;

    // General field definitions
    static final String STOCKNUMBER         = ".stockNumber";
    static final String LINENAME            = ".lineName";
    static final String SORTBY              = ".sortBy";
    static final String LINEIMPORTPROCESS   = "Import_Strains";

    // Root Definitions
    static final String POPUPNSLSTOCKNUMBERFILTER   = POPUPNAVIGATORSELECTLINES + STOCKNUMBER ;
    static final String POPUPNSLLINENAMEFILTER      = POPUPNAVIGATORSELECTLINES + LINENAME ;
    static final String POPUPNSLSORTBYFILTER        = POPUPNAVIGATORSELECTLINES + SORTBY ;

    // Defines an HTTP SESSION name for web tier class User.
    // User class is put in the SESSION and accessible by all backing beans.
    static final String USERSESSIONNAME             = "User";

    static final Integer TIMEFRAME_NONE             = 0;
    static final Integer TIMEFRAME_BEFORE           = 1;
    static final Integer TIMEFRAME_AFTER            = 2;

}






