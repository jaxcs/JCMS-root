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
 * FindEntityException.java
 *
 * Created on August 5, 2010, 10:54 AM
 * 
 */

package jcms.integrationtier.exception;

/** 
 *  <b>File name:</b>  FindEntityException.java  <p>
 *  <b>Date developed:</b>  August 2010 <p>
 *  <b>Purpose:</b>  Unable to find database record exception.  <p>
 *  <b>Overview:</b>  Persistence API is unable to find the database record or
 *                    there is a problem with the persistence reference.  <p>
 *  <b>Last changed by:</b>   $Author$ <p>
 *  <b>Last changed date:</b> $Date$   <p>
 *  @author Kavitha Rama
 *  @version $Revision$  
 */
public class FindEntityException extends java.lang.RuntimeException
{
    
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new instance of <code>FindEntityException</code> without detail message.
     */
    public FindEntityException ()
    {
    }
    
    
    /**
     * Constructs an instance of <code>FindEntityException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FindEntityException (String msg)
    {
        super (msg);
    }
}
