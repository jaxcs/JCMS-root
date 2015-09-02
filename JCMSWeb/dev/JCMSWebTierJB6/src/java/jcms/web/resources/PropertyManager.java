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

package jcms.web.resources;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jcms.web.base.WTBaseObject;
import jcms.web.base.WTBaseWebInterface;

/**
 * <b>File name:</b>  PropertyManager.java  <p>
 * <b>Date developed:</b>  September 2009 <p>
 * <b>Purpose:</b>  Manages creating and updating user preferences as cookies.  <p>
 * <b>Overview:</b>  Provides methods to create new cookies and get the values
 *      from existing cookies.  <p>
 * <b>Usage:</b>    <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2010-09-27 14:05:01 -0400 (Mon, 27 Sep 2010) $   <p>
 * @author Craig Hanna
 * @version $Revision: 11129 $
 */
public class PropertyManager extends WTBaseObject implements WTBaseWebInterface
{
    private HttpServletRequest httpServletRequest =
        (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private HttpServletResponse httpServletResponse =
        (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();

    public PropertyManager ()
    {

    }

    //
    // This method checks to see if the cookie already exists.  If it does it
    // just updates the value.  If the cookie does not exist this method creates
    // it.  Cookies are set to expire in 5 years.
    //
    public void setCookie(String key, String value)
    {
        Cookie cookie = null;

        // create cookies
        cookie = this.getCookie(key);
        
        if (cookie == null)
        {
            // Create new cookie.
            cookie = new Cookie(key, value);
            cookie.setDomain(PropertyManager.DOMAINNAME);
            cookie.setMaxAge(PropertyManager.TIMETOEXPIRE);
            httpServletResponse.addCookie(cookie);

            this.getLogger().logDebug(this.formatLogMessage("Added cookie ["+ 
                                            key +", "+ value +"]", "setCookie"));
        }
        else
        {
            // Update cookie value.
            cookie.setDomain(PropertyManager.DOMAINNAME);
            cookie.setValue(value);
            cookie.setMaxAge(PropertyManager.TIMETOEXPIRE);
            httpServletResponse.addCookie(cookie);

//            // Expire cookie by setting age to zero.
//            cookie.setMaxAge(0);
//            System.out.println("DEBUG: Cookie found, set to expire immediately.");
        }


    }

    public Cookie getCookie(String key)
    {
        Cookie cookie = null;

        // get cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        this.getLogger().logDebug(this.formatLogMessage("Number of cookies found " +
                "on remote client is " + cookies.length, "getCookie"));

        if (cookies != null)
        {
            int len = cookies.length;

            for(int i=0; i < len; i++)
            {
                this.getLogger().logDebug(this.formatLogMessage("Cookie name is ["+
                    cookies[i].getName(), "getCookie"));
                
                if (cookies[i].getName().equalsIgnoreCase(key))
                {
                    cookie = cookies[i];

                    this.getLogger().logDebug(this.formatLogMessage("Retrieved cookie ["+
                        cookies[i].getName() +", "+ cookies[i].getValue() +"]", "getCookie"));

                    break;
                }

            }
        }

//        this.getLogger().logDebug(this.formatLogMessage("Remote user is "+
//                            this.getRemoteUser(), "getCookie"));

        return cookie;
    }

    // Replaced by new class rs.web.security.User
    @Deprecated
    public String getRemoteUser()
    {
        String remoteUser = null;

        remoteUser = httpServletRequest.getRemoteUser();

        if (remoteUser == null)
            remoteUser = "NeedToAuthenticate";

        return remoteUser;
    }
}