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

/**
 * <b>File name:</b>  ITBaseDao.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides an interface to local and remote session beans.  <p>
 * <b>Overview:</b>  Provides an interface to local and remote session beans.  
 *                   This hides the implementation specific details from external 
 *                   tiers.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */        
public class ITBaseDao extends ITBaseFactory
{
    // -Xlint flags this as warning if removed.
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of ITBaseDao
     */     
    public ITBaseDao ()
    {
    
    }     
    
    /**
     *  Creates a new instance of ITBaseDao
     *  <b>Purpose:</b>  Sets the hostname and port of the JNDI server.  <br>
     *  <b>Overview:</b>  Sets the hostname and port of the JNDI server.
     *                    Format is HOSTNAME:PORTNUMBER  <br>
     *  @param jndiServerName JNDI server name and port number separated by a colon.
     */
    public ITBaseDao(String jndiServerName)
    {
        super(jndiServerName);
    }
    
}
