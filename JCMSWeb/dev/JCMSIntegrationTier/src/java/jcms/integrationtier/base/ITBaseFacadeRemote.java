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

import javax.ejb.Remote;

/**
 * <b>File name:</b>  ITBaseFacadeLocal.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Remote interface offers generic CRUD actions to submit
 *      database.  <p>
 * <b>Overview:</b>  Remote interface offers generic CRUD actions to submit
 *      database.   <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Remote
public interface ITBaseFacadeRemote extends ITBaseFacadeLocal
{

}
