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

import org.jax.cs.rscommon.LogIt;

/**
 * <b>File name:</b>  BTBaseObject.java  <p>
 * <b>RsDate developed:</b>  October 2009 <p>
 * <b>Purpose:</b>  Ancestor to all Business Tier classes. <p />
 * <b>Overview:</b>  Provides common functionality to all classes.  <p />
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */
public class BTBaseObject
{
    private LogIt   btLogger    = null;
    private String  message     = "";

    /**
     * <b>Purpose:</b>  Constructor loads the log4j properties file.  <br />
     */
    public BTBaseObject()
    {
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


}
