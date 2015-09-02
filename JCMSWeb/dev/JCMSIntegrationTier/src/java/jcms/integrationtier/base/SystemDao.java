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

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import jcms.integrationtier.exception.ContextException;

/**
 * <b>File name:</b>  SystemDao.java  <p>
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
public class SystemDao extends ITBaseFactory
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private Object        ref       = null;
    
    /**
     * Creates a new instance of SystemDao
     */     
    public SystemDao ()
    {
    
    }     
    
    /**
     *  Creates a new instance of SystemDao
     *  <b>Purpose:</b>  Sets the hostname and port of the JNDI server.  <br>
     *  <b>Overview:</b>  Sets the hostname and port of the JNDI server.
     *                    Format is HOSTNAME:PORTNUMBER  <br>
     *  @param jndiServerName JNDI server name and port number separated by a colon.
     */
    public SystemDao(String jndiServerName)
    {
        super(jndiServerName);
    }

    /** 
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */ 
    public SystemFacadeLocal getSystemFacadeLocal()
    {
        SystemFacadeLocal instance = null;
        
        try      
        {
            instance = (SystemFacadeLocal) super.getContext().lookup(SystemDao.SystemFacade + SystemDao.local);
        }
        catch(NamingException ne) 
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }        
        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public SystemFacadeRemote getSystemFacadeRemote()
    {
        SystemFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(SystemDao.SystemFacade + SystemDao.remote);
            instance = (SystemFacadeRemote) PortableRemoteObject.narrow (ref, SystemFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */
    public JCMSWebSystemFacadeLocal getJCMSWebSystemFacadeLocal()
    {
        JCMSWebSystemFacadeLocal instance = null;

        try
        {
            instance = (JCMSWebSystemFacadeLocal) super.getContext().lookup(SystemDao.JCMSWebSystemFacade + SystemDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }
        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public JCMSWebSystemFacadeRemote getJCMSWebSystemFacadeRemote()
    {
        JCMSWebSystemFacadeRemote instance = null;

        try {
            ref = super.getContext().lookup(SystemDao.SystemFacade + SystemDao.remote);
            instance = (JCMSWebSystemFacadeRemote) PortableRemoteObject.narrow (ref,
                    JCMSWebSystemFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */
    public ITJCMSWebBaseFacadeLocal getITJCMSWebBaseFacadeLocal()
    {
        ITJCMSWebBaseFacadeLocal instance = null;

        try
        {
            instance = (ITJCMSWebBaseFacadeLocal) super.getContext().lookup(SystemDao.ITJCMSWebBaseFacade + SystemDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }
        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public ITJCMSWebBaseFacadeRemote getITJCMSWebBaseFacadeRemote()
    {
        ITJCMSWebBaseFacadeRemote instance = null;

        try {
            ref = super.getContext().lookup(SystemDao.SystemFacade + SystemDao.remote);
            instance = (ITJCMSWebBaseFacadeRemote) PortableRemoteObject.narrow (ref,
                    ITJCMSWebBaseFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }
}