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

import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  BTBaseBOInterface.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides general support to all Business Tier business objects.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public interface BTBaseBOInterface
{
    /**
     * <b>Purpose:</b> Validate object <br />
     * <b>Overview:</b> Update informative message for web tier use.  Can be
     *      displayed to the user pointing out which fields failed valudation 
     *      and why.  <p />
     * @param entity object to validate
     * @return Boolean valid object is 1, invalid object is 0
     */
    Boolean validate(ITBaseEntityInterface entity);

}
