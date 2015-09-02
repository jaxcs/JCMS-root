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
 * <b>File name:</b>  ITConstantsInterface.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  General class for defining global constants for the Integration Tier.  <p>
 * <b>Overview:</b>  Also defines the JNDI path for all Enterprise Java Beans.
 *      Open a session facade and notice the annotation is not clear text.  It
 *      refers to the definitions in this file.  This is the central location
 *      for any refactoring of class paths.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public interface ITConstantsInterface
{
    // Persistent Unit definitions
    static final String IntegrationTierPU   = "JCMSIntegrationTierPU";
    static final String JCMSWebPU         = "JCMSWebIntegrationTierPU";

    // JNDI EJB definitions
    static final String local               = "/local";
    static final String remote              = "/remote";

    // INTEGRATION TIER
    // JNDI EJB root path definitions
    static final String baseEJBPath         = "jcms/integrationtier/ejb/";

    static final String adminJndiPath       = baseEJBPath + "admin/";
    static final String baseJndiPath        = baseEJBPath + "base/";
    static final String cvJndiPath          = baseEJBPath + "cv/";
    static final String methodPath          = baseEJBPath + "method/";
    static final String migratorJndiPath    = baseEJBPath + "utils/";
    static final String namingJndiPath      = baseEJBPath + "naming/";
    static final String projectJndiPath     = baseEJBPath + "project/";

    // Base session bean definitions - JNDI path and class name
    static final String BaseFacade          = baseJndiPath + "BaseFacade";

    // Session Facade definitions - JNDI path and class name
    static final String AdminFacade         = cvJndiPath + "AdminFacade";
    static final String CvDaoFacade         = cvJndiPath + "CvDaoFacade";
    static final String DbInfoFacade        = cvJndiPath + "DbInfoFacade";
    static final String JAXStrainFacade     = migratorJndiPath + "JAXStrainFacade";
    static final String MethodFacade        = methodPath + "MethodFacade";
    static final String NameFamilyFacade    = namingJndiPath + "NameFamilyFacade";
    static final String ProjectFacade       = projectJndiPath + "ProjectFacade";
    static final String SystemFacade        = baseJndiPath + "SystemFacade";
    static final String JCMSWebSystemFacade = baseJndiPath + "JCMSWebSystemFacade";
    static final String ITJCMSWebBaseFacade = baseJndiPath + "ITJCMSWebBaseFacade";

    // General constants
    static final Short   ACTIVE             = 1 ;
    static final Short   INACTIVE           = 0 ;
}