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

package jcms.middletier.facade;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import jcms.middletier.base.BTBaseFactory;
import jcms.middletier.exception.ContextException;

/**
 * <b>File name:</b>  CvDAO.java  <p>
 * <b>Date developed:</b>  August 2009 <p>
 * <b>Purpose:</b>  Provides an interface to local and remote session beans.  <p>
 * <b>Overview:</b>  Provides an interface to local and remote session beans.  
 *                   This hides the implementation specific details from external 
 *                   tiers.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */        
public class FacadeDao extends BTBaseFactory
{
    private Object        ref       = null;
    
    /**
     * Creates a new instance of CvDAO
     */     
    public FacadeDao ()
    {
    
    }     
    
    /**
     *  Creates a new instance of CvDAO
     *  <b>Purpose:</b>  Sets the hostname and port of the JNDI server.  <br>
     *  <b>Overview:</b>  Sets the hostname and port of the JNDI server.
     *                    Format is HOSTNAME:PORTNUMBER  <br>
     *  @param jndiServerName JNDI server name and port number separated by a colon.
     */
    public FacadeDao(String jndiServerName)
    {
        super(jndiServerName);
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to RepositoryFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to RepositoryFacade local interface.  <br>
     */
    public RepositoryFacadeLocal getRepositoryFacadeLocal()
    {
        RepositoryFacadeLocal instance = null;

        try
        {
            instance = (RepositoryFacadeLocal) super.getContext().lookup(FacadeDao.RepositoryFacade + FacadeDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " + 
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to RepositoryFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to RepositoryFacade remote interface.  <br>
     */
    public RepositoryFacadeRemote getRepositoryFacadeRemote()
    {
        RepositoryFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(FacadeDao.RepositoryFacade + FacadeDao.remote);
            instance = (RepositoryFacadeRemote) PortableRemoteObject.narrow (ref, RepositoryFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to JCMSWebFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to JCMSWebFacade local interface.  <br>
     */
    public JCMSWebFacadeLocal getJCMSWebFacadeLocal()
    {
        JCMSWebFacadeLocal instance = null;

        try
        {
            instance = (JCMSWebFacadeLocal) super.getContext().lookup(FacadeDao.JCMSWebFacade + FacadeDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to JCMSWebFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to JCMSWebFacade remote interface.  <br>
     */
    public JCMSWebFacadeRemote getJCMSWebFacadeRemote()
    {
        JCMSWebFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(FacadeDao.JCMSWebFacade + FacadeDao.remote);
            instance = (JCMSWebFacadeRemote) PortableRemoteObject.narrow (ref, JCMSWebFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ServiceFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to ServiceFacade local interface.  <br>
     */
    public ServiceFacadeLocal getServiceFacadeLocal()
    {
        ServiceFacadeLocal instance = null;

        try
        {
            instance = (ServiceFacadeLocal) super.getContext().lookup(FacadeDao.ServiceFacade + FacadeDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ServiceFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to ServiceFacade remote interface.  <br>
     */
    public ServiceFacadeRemote getServiceFacadeRemote()
    {
        ServiceFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(FacadeDao.ServiceFacade + FacadeDao.remote);
            instance = (ServiceFacadeRemote) PortableRemoteObject.narrow (ref, ServiceFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ReportFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to ReportFacade local interface.  <br>
     */
    public ReportFacadeLocal getReportFacadeLocal()
    {
        ReportFacadeLocal instance = null;

        try
        {
            instance = (ReportFacadeLocal) super.getContext().lookup(FacadeDao.ReportFacade + FacadeDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ReportFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to ReportFacade remote interface.  <br>
     */
    public ReportFacadeRemote getReportFacadeRemote()
    {
        ReportFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(FacadeDao.ReportFacade + FacadeDao.remote);
            instance = (ReportFacadeRemote) PortableRemoteObject.narrow (ref, ReportFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to VocabularyFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to VocabularyFacade local interface.  <br>
     */
    public VocabularyFacadeLocal getVocabularyFacadeLocal()
    {
        VocabularyFacadeLocal instance = null;

        try
        {
            instance = (VocabularyFacadeLocal) super.getContext().lookup(FacadeDao.VocabularyFacade + FacadeDao.local);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a local " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to VocabularyFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to VocabularyFacade remote interface.  <br>
     */
    public VocabularyFacadeRemote getVocabularyFacadeRemote()
    {
        VocabularyFacadeRemote instance = null;

        try
        {
            ref = super.getContext().lookup(FacadeDao.VocabularyFacade + FacadeDao.remote);
            instance = (VocabularyFacadeRemote) PortableRemoteObject.narrow (ref, VocabularyFacadeRemote.class);
        }
        catch(NamingException ne)
        {
            throw new ContextException("Failed to get an instance of a remote " +
                                        "interface.  " + ne);
        }

        return instance;
    }

    
}
