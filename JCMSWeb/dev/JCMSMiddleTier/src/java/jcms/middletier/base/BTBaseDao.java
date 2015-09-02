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

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import jcms.middletier.exception.ContextException;

/**
 * <b>File name:</b>  CvDAO.java  <p>
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
public class BTBaseDao extends BTBaseFactory
{
    private Object        ref       = null;
    
    /**
     * Creates a new instance of CvDAO
     */     
    public BTBaseDao ()
    {
    
    }     
    
    /**
     *  Creates a new instance of CvDAO
     *  <b>Purpose:</b>  Sets the hostname and port of the JNDI server.  <br>
     *  <b>Overview:</b>  Sets the hostname and port of the JNDI server.
     *                    Format is HOSTNAME:PORTNUMBER  <br>
     *  @param jndiServerName JNDI server name and port number separated by a colon.
     */
    public BTBaseDao(String jndiServerName)
    {
        super(jndiServerName);
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to BaseSessionBean local interface.  <br>
     *  <b>Overview:</b> Gets a reference to BaseSessionBean local interface.  <br>
     */
    public BTBaseFacadeLocal getBaseFacadeLocal()
    {
        BTBaseFacadeLocal instance = null;

        try
        {
            ref = super.getContext().lookup(BTBaseDao.BaseFacade + BTBaseDao.local);
            instance = (BTBaseFacadeLocal) PortableRemoteObject.narrow (ref, BTBaseFacadeLocal.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " + 
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to BaseSessionBean remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to BaseSessionBean remote interface.  <br>
     */
    public BTBaseFacadeRemote getBaseFacadeRemote()
    {
        BTBaseFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(BTBaseDao.BaseFacade + BTBaseDao.remote);
            instance = (BTBaseFacadeRemote) PortableRemoteObject.narrow (ref, BTBaseFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    
}
