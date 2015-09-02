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

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <b>File name:</b>  MySQLDAOFactory.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides MySQL database connectivity and gets component DAO 
 *                  interfaces to MySQL implementations.  <p>
 * <b>Overview:</b>  Provides MySQL database connectivity and gets component DAO 
 *                  interfaces to MySQL implementations.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */        
public class MySQLDAOFactory extends DAOFactory
{

    /** Creates a new instance of MySQLDAOFactory */
    public MySQLDAOFactory ()
    {
    }

    /** 
     *  <b>Purpose:</b>  Provides a brute force JDBC connection to the 
     *                   database.  <br>
     *  <b>Overview:</b>  Provides a brute force JDBC connection to the 
     *                    database.  No connection pooling and no JNDI 
     *                    lookup.  <br>
     *  <b>Performance:</b>  This connection method meets expected performance 
     *                       parameters.  To run dynamic query for Request, RTC,
     *                       and ST_Case the total runtime is 0.5 seconds.  Time
     *                       consuming connection was at least 7.0 seconds.  Each
     *                       time another field was acquired from the ResultSet 
     *                       time to execute would increase by an unexceptable 
     *                       number of seconds.  Like 0.4 seconds.
     *  <b>Conclusion:</b>   Using JNDI to lookup and connect to the database has
     *                       an adverse affect on getting data types from the 
     *                       ResultSet.  The connection is fast but getting types
     *                       from the ResultSet is slow. 
     *  <b>Resolution:</b>   This implementation works the best.  It gets the 
     *                       connection properties from the JBOSS_HOME datasource
     *                       xml file, mysql-ds.xml.
     *  @return List<RequestToCaseEntity>  Contains the list of <code>RequestToCaseEntity</code>.
     */ 
    public Connection createConnection()
    {
        Connection    conn     = null;
        
        try 
        {
            DataSourceProperty property = new Connector().getDatabaseConnectionParameters(Connector.JCMSWebDS);
            Class.forName(property.getDriverClass());
            conn = DriverManager.getConnection(property.getConnectionUrl(), 
                                               property.getUsername(), 
                                               property.getPassword());
        } 


        catch (ClassNotFoundException ex) 
        {
            System.out.println(MySQLDAOFactory.class.getName() + 
                    "::ClassNotFoundException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() + 
                    "::FileNotFoundException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
        catch (SQLException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() + 
                    "::SQLException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
        
        return conn ;
    }

    public Connection createJCMSWebConnection()
    {
        Connection    con     = null;

        try
        {
            DataSourceProperty property = new Connector().getDatabaseConnectionParameters(Connector.JCMSWebDBDS);
            Class.forName(property.getDriverClass());
            con = DriverManager.getConnection(property.getConnectionUrl(),
                                               property.getUsername(),
                                               property.getPassword());
        }


        catch (ClassNotFoundException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() +
                    "::ClassNotFoundException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() +
                    "::FileNotFoundException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
        catch (SQLException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() +
                    "::SQLException::createConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }

        return con;
    }
    
    /** 
     *  <b>Purpose:</b>  Closes an open connection. <br>
     *  <b>Overview:</b>  Creates a MySQL JDBC connection to the  
     *                    MySQL database.  <br> 
     *  @param con JDBC connection to MySQL database.
     */ 
    public void closeConnection(Connection con)
    {
        try
        {
            if (!con.isClosed())
            {
                con.close();
                con = null;
            }
        } 
        catch (SQLException ex)
        {
            System.out.println(MySQLDAOFactory.class.getName() + 
                    "::SQLException::closeConnection::" +
                    "Problem closing JDBC connection.  "+ ex);
        }
    }
    
    /**
     *  <b>Purpose:</b>  Provides access to Dynamic Search Data Access Object. <br>
     *  <b>Overview:</b>  Creates a Dyanamic Search DAO object and returns it to the 
     *                    calling code segment.  <br> 
     * @return MySQLSearchDAO MySQLSearchDAO dynamic search data access object
     */ 
    @Override
    public JDBCSearchDAO getSearchDAO ()
    {
        // MySQLSearchDAO implements MySQLSearchDAO
        return new JDBCSearchDAO();
    }
}
