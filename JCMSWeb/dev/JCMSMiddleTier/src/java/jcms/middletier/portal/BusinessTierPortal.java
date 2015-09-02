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
/*
 * BusinessTierDAO.java
 *
 * Created on August 22, 2010, 8:25 AM
 * 
 */

package jcms.middletier.portal;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import jcms.integrationtier.exception.ContextException;
import jcms.middletier.facade.FacadeDao;
import jcms.middletier.facade.JCMSWebFacadeLocal;
import jcms.middletier.facade.JCMSWebFacadeRemote;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.facade.ReportFacadeRemote;
import jcms.middletier.facade.RepositoryFacadeLocal;
import jcms.middletier.facade.RepositoryFacadeRemote;
import jcms.middletier.facade.ServiceFacadeLocal;
import jcms.middletier.facade.ServiceFacadeRemote;
import jcms.middletier.facade.VocabularyFacadeLocal;
import jcms.middletier.facade.VocabularyFacadeRemote;


/**
 * <b>File name:</b>  BusinessTierDAO.java  <p>
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
public class BusinessTierPortal 
{

    /**
     * Creates a new instance of BusinessTierDAO
     */     
    public BusinessTierPortal () 
    {
    }     
    
    /**
     *  <b>Purpose:</b>  Gets a reference to RepositoryFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to RepositoryFacade local interface.  <br>
     */
    public RepositoryFacadeLocal getRepositoryFacadeLocal()
    {
        return new FacadeDao().getRepositoryFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to RepositoryFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to RepositoryFacade remote interface.  <br>
     */
    public RepositoryFacadeRemote getRepositoryFacadeRemote() 
    {
        return new FacadeDao().getRepositoryFacadeRemote();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ServiceFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to ServiceFacade local interface.  <br>
     */
    public ServiceFacadeLocal getServiceFacadeLocal()
    {
        return new FacadeDao().getServiceFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ServiceFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to ServiceFacade remote interface.  <br>
     */
    public ServiceFacadeRemote getServiceFacadeRemote()
    {
        return new FacadeDao().getServiceFacadeRemote();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ReportFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to ReportFacade local interface.  <br>
     */
    public ReportFacadeLocal getReportFacadeLocal()
    {
        return new FacadeDao().getReportFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to ReportFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to ReportFacade remote interface.  <br>
     */
    public ReportFacadeRemote getReportFacadeRemote()
    {
        return new FacadeDao().getReportFacadeRemote();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to VocabularyFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to VocabularyFacade local interface.  <br>
     */
    public VocabularyFacadeLocal getVocabularyFacadeLocal()
    {
        return new FacadeDao().getVocabularyFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to VocabularyFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to VocabularyFacade remote interface.  <br>
     */
    public VocabularyFacadeRemote getVocabularyFacadeRemote()
    {
        return new FacadeDao().getVocabularyFacadeRemote();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to JCMSWebFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to JCMSWebFacade local interface.  <br>
     */
    public JCMSWebFacadeLocal getJCMSWebFacadeLocal()
    {
        return new FacadeDao().getJCMSWebFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to JCMSWebFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to JCMSWebFacade remote interface.  <br>
     */
    public JCMSWebFacadeRemote getJCMSWebFacadeRemote()
    {
       return new FacadeDao().getJCMSWebFacadeRemote();
    }
}