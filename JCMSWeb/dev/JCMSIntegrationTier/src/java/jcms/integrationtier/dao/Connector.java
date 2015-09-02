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

package jcms.integrationtier.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import jcms.integrationtier.base.ITBaseObject;

/**
 * <b>File name:</b>  Connector.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides worker methods to get connection properties from 
 *                  external sources. <p>
 * <b>Overview:</b>  Provides worker methods to get connection properties from 
 *                   external sources.  Such as extracting connection properties
 *                   from JBOSS MySQL datasource file.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * 
 * @author Kavitha Rama
 * @version $Revision$
 */        

public class Connector extends ITBaseObject
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    public static final String JCMSDEFAULTDS    = "DefaultDS";
    public static final String JCMSWebDS        = "JCMSWebDS";
    public static final String JCMSWebDBDS      = "JCMSWebDBDS";
    public static final String RSLIMSDS         = "RSLIMSDS";
    public static final String JAXSTRAINS       = "JAXSTRAINS";

    /** 
     *  <b>Purpose:</b>  Provides an object of database connection
     *                   properties for JDBC connections.  <br>
     *  <b>Overview:</b>  Provides an object of database connection
     *                   properties for JDBC connections.  <br>
     *  @return DataSourceProperty Contains database connection properties.
     *  @throws FileNotFoundException  XML file not found.
     */ 
    public DataSourceProperty getDatabaseConnectionParameters(String dataSource) throws FileNotFoundException
    {
        String JBOSS_Home   = System.getenv("JBOSS_HOME");
        
        String JBOSS_DEPLOY = JBOSS_Home + "/server/default/deploy";
        String mySqlDSXml   = "/mysql-ds.xml" ;
        String jbossDSXml   = "/jboss-ds.xml" ;

        this.getLogger().logDebug(this.formatLogMessage("JBOSS_Home is \t" + JBOSS_Home, "getDatabaseConnectionParameters"));
        this.getLogger().logDebug(this.formatLogMessage("JBOSS_Deploy is \t" + JBOSS_DEPLOY, "getDatabaseConnectionParameters"));
        
        Scanner input = null;
        Integer count = 0;
        
        DataSourceProperty property = new DataSourceProperty();
        File               file     = null;

        final String CONNECTIONURL  = "<connection-url>";
        final String DRIVERCLASS    = "<driver-class>";
        final String USERNAME       = "<user-name>";
        final String PASSWORD       = "<password>";
        
            file = new File(JBOSS_DEPLOY + mySqlDSXml);

        if (file.exists())
        {
            input = new Scanner(file);

            while (input.hasNext() && count < 4)
            {
                String line = input.nextLine();

                if (line.indexOf(dataSource) >= 0 )
                {
                    while (input.hasNext() && count < 4)
                    {
                        line = input.nextLine();

                        if (line.indexOf(CONNECTIONURL) >= 0 )
                        {
                            property.setConnectionUrl(this.getXmlProperty(CONNECTIONURL, line)) ;
                            count++;
                        }
                        else
                        if (line.indexOf(DRIVERCLASS) >= 0 )
                        {
                            property.setDriverClass(this.getXmlProperty(DRIVERCLASS, line)) ;
                            count++;
                        }
                        else
                        if (line.indexOf(USERNAME) >= 0 )
                        {
                            property.setUsername(this.getXmlProperty(USERNAME, line)) ;
                            count++;
                        }
                        else
                        if (line.indexOf(PASSWORD) >= 0 )
                        {
                            property.setPassword(this.getXmlProperty(PASSWORD, line)) ;
                            count++;
                        }

                    }
                }

            }
        }
        
        return property;
    }

    /** 
     *  <b>Purpose:</b>  Extracts the XML value for the given tag.  <br>
     *  <b>Overview:</b>  Extracts the XML value for the given tag.  <br>
     *  @param  tag  XML Contains the XML tag to search for.
     *  @param  line  Contains a line of XML text to search.
     *  @return String  Contains value of XML tag.
     */ 
    private String getXmlProperty(String tag, String line) 
    {
        Integer tagSize     = tag.length();
        
        Integer startPos    = line.indexOf(tag) + tagSize;
        Integer endPos      = line.indexOf("</",startPos+1);
        
        String rtnValue = line.substring(startPos, endPos) ;
        
        return  rtnValue;
    }
}