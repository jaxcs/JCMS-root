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

package jcms.integrationtier.exception;

/**
 *
 * @author rkavitha
 */
public class TransactionRollbackException extends Exception {

    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>TransactionRollbackException</code> without detail message.
     */
    public TransactionRollbackException() {
    }


    /**
     * Constructs an instance of <code>TransactionRollbackException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TransactionRollbackException(String msg) {
        super(msg);
    }
}
