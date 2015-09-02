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

/**
 * <b>File name:</b>  DAOFactory.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides MySQL DAO Factory objects to requestors and gets 
 *                  component DAOs for MySQL database.     <p>
 * <b>Overview:</b>  Abstract database specific Data Access Objects from the 
 *                  implementation layer.  Provide specific database options.  
 *                  Provides a concrete method that returns an access point to 
 *                  all DAO's for a specific database implementation    <p>
 * <b>Usage:</b>  Call method getDAOFactory(this.MYSQL) specifying the type of 
 *                database using local static defines.  With this reference 
 *                get your DAO of choice.  
 *                <br>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */        
public abstract class DAOFactory
{
    // List of DAO types supported by the factory
    public static final int MYSQL       = 1;
    public static final int ORACLE9i    = 2;
    
    /** Creates a new instance of DAOFactory */
    public DAOFactory ()
    {
    }

    /**
     *  <b>Purpose:</b>  Provides support for multiple database implementation
     *                   and abstracts the implementation details.  <br>
     *  <b>Overview:</b>  Provides support for multiple database implementation
     *                   and abstracts the implementation details.  <br>
     *  @param factory Contains the type of database factory to provide.
     */
    public static DAOFactory getDAOFactory(int factory)
    {
        switch (factory)
        {
            case MYSQL:
                return new MySQLDAOFactory();
            case ORACLE9i:
                return new MySQLDAOFactory();
            default:
                return null;
        }
    }

    // Create a method for each DAO that can be created.
    // The concrete factories will have to implement these methods.

    /**
     *  <b>Purpose:</b>  Provides access to Search DAO regardless of the database 
     *                   implementation.  <br>
     *  <b>Overview:</b>  Provides access to Search DAO regardless of the database 
     *                   implementation.  <br>
     * @return MySQLSearchDAO Contains the MySQLSearchDAO
     */ 
    
    public abstract JDBCSearchDAO getSearchDAO();
}









