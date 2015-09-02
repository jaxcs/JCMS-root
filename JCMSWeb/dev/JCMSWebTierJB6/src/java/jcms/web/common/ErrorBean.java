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
package jcms.web.common;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

/**
 *
 * @author cnh
 */
public class ErrorBean
{

    public String getStackTrace()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> request = context.getExternalContext().getRequestMap();

        Throwable ex = (Throwable) request.get("javax.servlet.error.exception");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        fillStackTrace(ex,pw);

        return sw.toString();
    }

    private static void fillStackTrace(Throwable t, PrintWriter w)
    {
        if (t == null) return ;

        t.printStackTrace(w);

        if (t instanceof ServletException)
        {
            Throwable cause = ((ServletException) t).getRootCause();

            if (cause != null)
            {
                w.println("Root cause: ");
                fillStackTrace(cause, w);
            }
        }
        else if (t instanceof SQLException)
        {
            Throwable cause = ((SQLException) t).getNextException();

            if (cause != null)
            {
                w.println("Next exception: ");
                fillStackTrace(cause, w);
            }
        }
        else
        {
            Throwable cause = t.getCause();

            if (cause != null)
            {
                w.println("Cause: ");
                fillStackTrace(cause, w);
            }
        }
    }
    
}
