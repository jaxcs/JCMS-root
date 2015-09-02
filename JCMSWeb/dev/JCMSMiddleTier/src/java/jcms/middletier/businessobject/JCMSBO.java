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

package jcms.middletier.businessobject;

import jcms.middletier.base.BTBaseObject;
import jcms.integrationtier.portal.IntegrationTierPortal;



/**
 * <b>File name:</b>  JCMSBO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Base business object class for all business objects.  <p>
 * <b>Overview:</b>  Base class contain general functionality that is referenced
 *      by all descendant classes.  Like access to the Integration Tier portal.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class JCMSBO extends BTBaseObject
{
    private IntegrationTierPortal integrationTierPortal = null;

    public JCMSBO()
    {
        integrationTierPortal = new IntegrationTierPortal();
    }

    /**
     * @return the integrationTierPortal
     */
    public IntegrationTierPortal getIntegrationTierPortal() {
        return integrationTierPortal;
    }
}