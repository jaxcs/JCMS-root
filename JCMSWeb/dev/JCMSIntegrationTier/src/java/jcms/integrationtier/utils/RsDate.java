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

package jcms.integrationtier.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rkavitha
 */
public class RsDate
{
    public static final String MySQLDateTimeFormat  = "MM/dd/yyyy HH:mm:ss";
    public static final String MySQLDateFormat      = "MM/dd/yyyy";
    public static final String OracleDateFormat     = "MM/DD/YYYY HH:MI:SS";

    public String formatDateForDisplay(Calendar date)
    {

        if (date != null)
        {
            DateFormat formatter ;
            formatter = new SimpleDateFormat(MySQLDateTimeFormat);
            return  formatter.format(date.getTime());
        }
        else
        {
            return "";
        }

    }

    public String formatDateForDisplay(Date date)
    {
        if (date != null)
        {
            SimpleDateFormat f = new SimpleDateFormat();
            f.applyPattern(MySQLDateFormat);
            String sdf = f.format(date);

            return sdf ;
        }
        else
        {
            return "";
        }

    }

    public Date convertStringToDate(String s) throws ParseException
    {
        Date convertedDate = null;

        if (s.length() <= RsDate.MySQLDateFormat.length())
        {
            SimpleDateFormat formatter = new SimpleDateFormat(RsDate.MySQLDateFormat);
            convertedDate = formatter.parse(s);
        }
        else
        {
            SimpleDateFormat formatter = new SimpleDateFormat(RsDate.MySQLDateTimeFormat);
            convertedDate = formatter.parse(s);
        }

        return convertedDate ;
    }

    public Date convertCalendarToDate(Calendar cal)
    {
        String string = this.formatDateForDisplay(cal);
        Date date = null;
        
        try
        {
            date = this.convertStringToDate(string);
        } catch (ParseException ex)
        {
            Logger.getLogger(RsDate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return date ;


    }

}
