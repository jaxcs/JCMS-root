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

package jcms.integrationtier.base;

import java.io.Serializable;
import org.jax.cs.rscommon.LogIt;



/**
 * <b>File name:</b>  ITBaseObject.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Ancestor to all Integration Tier classes. <p />
 * <b>Overview:</b>  Provides common functionality to all classes.  <p />
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class ITBaseObject implements ITConstantsInterface, Serializable
{
    private LogIt           btLogger    = null;
    private String          message     = "";
    // -Xlint flags this as warning if removed.
    private static final long serialVersionUID = 1L;

    public ITBaseObject()
    {
    }

    /**
     * @return the btLogger
     */
    protected LogIt getLogger()
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
    protected String formatLogMessage(String message, String methodName)
    {
        String logMsg = "[" + methodName + "] " ;
        this.message = logMsg + message;

        return this.message;
    }




}
