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

package jcms.integrationtier.portal;

import jcms.integrationtier.base.ITBaseObject;
import jcms.integrationtier.base.ITJCMSWebBaseFacadeLocal;
import jcms.integrationtier.base.ITJCMSWebBaseFacadeRemote;
import jcms.integrationtier.base.JCMSWebSystemFacadeLocal;
import jcms.integrationtier.base.JCMSWebSystemFacadeRemote;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.base.SystemFacadeLocal;
import jcms.integrationtier.base.SystemFacadeRemote;


/**
 * <b>File name:</b>  IntegrationTierPortal.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a single point of access to all integration tier
 *                  session beans.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */        
public class IntegrationTierPortal extends ITBaseObject
{
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of IntegrationTierPortal
     */
    public IntegrationTierPortal ()
    {
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */
    public SystemFacadeLocal getSystemFacadeLocal()
    {
        return new SystemDao().getSystemFacadeLocal() ;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public SystemFacadeRemote getSystemFacadeRemote()
    {
        return new SystemDao().getSystemFacadeRemote() ;
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */
    public JCMSWebSystemFacadeLocal getJCMSWebSystemFacadeLocal()
    {
        return new SystemDao().getJCMSWebSystemFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public JCMSWebSystemFacadeRemote getJCMSWebSystemFacadeRemote()
    {
        return new SystemDao().getJCMSWebSystemFacadeRemote();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade local interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade local interface.  <br>
     */
    public ITJCMSWebBaseFacadeLocal getITJCMSWebBaseFacadeLocal()
    {
        return new SystemDao().getITJCMSWebBaseFacadeLocal();
    }

    /**
     *  <b>Purpose:</b>  Gets a reference to SystemFacade remote interface.  <br>
     *  <b>Overview:</b> Gets a reference to SystemFacade remote interface.  <br>
     */
    public ITJCMSWebBaseFacadeRemote getITJCMSWebBaseFacadeRemote()
    {
        return new SystemDao().getITJCMSWebBaseFacadeRemote();
    }
}