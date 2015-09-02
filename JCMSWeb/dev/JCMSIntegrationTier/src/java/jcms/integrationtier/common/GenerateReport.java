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

package jcms.integrationtier.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <b>File name:</b>  FileDownloadBean.java  <p>
 * <b>Date developed:</b>  October 2010 <p>
 *  <b>Purpose:</b>  Generates output file from JDBC Result set.  <p>
 * <b>Overview:</b> Contains the getters and setters for all file elements.
 *      Reads the JDBC Resultset content and writes it into the file.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b>    <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class GenerateReport {

    public GenerateReport() {

    }

    /*
     * Generates CSV format file from the jdbc result set
     */
    public void generateCSVFile(ResultSet rs, BufferedWriter out)
            throws SQLException {
        int cnt = 0;

        try {
            while (rs.next()) {
                int ncols = rs.getMetaData().getColumnCount();

                // write the column headers
                for (int i = 2; i < (ncols + 1); i++) {
                    out.append(rs.getMetaData().getColumnName(i));
                    if (i < ncols) {
                        out.append(",");
                    } else {
                        out.append("\r\n");
                    }
                }

                // set the result set to first row
                rs.beforeFirst();
                
                // write the data
                while (rs.next()) {
                    cnt++;

                    for (int i = 2; i < (ncols + 1); i++) {
                        if (rs.getString(i) != null) {
                            out.append(rs.getString(i));
                        }
                        else {
                            out.append("");
                        }

                        if (i < ncols) {
                            out.append(",");
                        } else {
                            out.append("\r\n");
                        }
                    }
                }
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /*
     * returns timestamped date, used for unique file names
     */
    public final static String getDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("EST"));
        return df.format(new Date());
    }
}