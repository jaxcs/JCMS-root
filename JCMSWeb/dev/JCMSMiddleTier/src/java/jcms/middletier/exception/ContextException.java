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
 * ContextException.java
 *
 * Created on November 29, 2007, 2:38 PM
 * 
 */

package jcms.middletier.exception;

/** 
 *  <b>File name:</b>  ContextException.java  <p>
 *  <b>Date developed:</b>  December 2007 <p>
 *  <b>Purpose:</b>  Unable to get proper context.  <p>
 *  <b>Overview:</b>  Unable to get proper context. <p>
 *  <b>Last changed by:</b>   $Author$ <p>
 *  <b>Last changed date:</b> $Date$   <p>
 *  @author Craig Hanna  
 *  @version $Revision$  
 */
public class ContextException extends java.lang.RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new instance of <code>ContextException</code> without detail message.
     */
    public ContextException ()
    {
    }
    
    
    /**
     * Constructs an instance of <code>ContextException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ContextException (String msg)
    {
        super (msg);
    }
}
