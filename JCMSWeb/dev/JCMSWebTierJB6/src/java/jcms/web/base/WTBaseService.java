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

package jcms.web.base;

import jcms.middletier.portal.BusinessTierPortal;


/**
 * <b>File name:</b>  WTBaseService.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides common functional access to all Web Tier services.  <p>
 * <b>Overview:</b>  Provides common functional access to all Web Tier services.
 *      For example, all services must have access to BusinessTierPortal object.
 *      This class provides a single point of access to BusinessTierPortal.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2010-09-27 14:05:01 -0400 (Mon, 27 Sep 2010) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 11129 $
 */
public class WTBaseService extends WTBaseObject
{
    private BusinessTierPortal businessTierPortal = null;

    public WTBaseService()
    {
        // All cases that extend this class need BusinessTierPortal object.
        businessTierPortal = new BusinessTierPortal();
    }
 
    /**
     * @return the businessTierPortal
     */
    protected BusinessTierPortal getBusinessTierPortal()
    {
        if (businessTierPortal == null)
            businessTierPortal = new BusinessTierPortal();

        return businessTierPortal;
    }

    /**
     * @param businessTierPortal the businessTierPortal to set
     */
    protected void setBusinessTierPortal(BusinessTierPortal businessTierPortal) {
        this.businessTierPortal = businessTierPortal;
    }

}
