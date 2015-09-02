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

import jcms.middletier.base.BTBaseBO;
import jcms.middletier.base.BTBaseBOInterface;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  SystemActionBO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides access to and enforces business logic for System Action data.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class SystemActionBO extends BTBaseBO implements BTBaseBOInterface
{ 
    @Override
    public Boolean validate(ITBaseEntityInterface entity)
    {
        Boolean isValid = Boolean.TRUE;

        return isValid;
    }
}