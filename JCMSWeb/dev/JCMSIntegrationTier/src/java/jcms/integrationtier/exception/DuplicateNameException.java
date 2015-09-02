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
/**
 * DuplicateNameException.java
 *
 * Created on August 13, 2010
 *
 *
 * This custom exception is thrown whenever an INSERT or UPDATE violates a
 * UNIQUE constraint on a field in the database. This type of exception permits
 * us to enforce a UNIQUE constraint on a column name to avoid duplicate names.
 *
 * @author rkavitha
 */

package jcms.integrationtier.exception;

public class DuplicateNameException extends java.lang.Exception {
    private String   constraintName = null;
    private Object[] values         = null;
   // -Xlint compile options likes this ...
   private static final long serialVersionUID = 1L;


    /**
     * Creates a new instance of <code>DuplicateNameException</code> with a
     * default, user-friendly message.
     */
    public DuplicateNameException ()
    {
        super( "That name already exists." );
    }


    /**
     * Constructs an instance of <code>DuplicateNameException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DuplicateNameException (String msg)
    {
        super (msg);
    }

    /**
     * Return the name of the constraint that caused the violation.
     * @return the name of the constraint that caused the violation
     */
    public String getConstraintName() {
        return constraintName;
    }

    /**
     * Set the name of the constraint that caused the violation.
     * @param constraintName the name of the constraint that caused the violation
     */
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    /**
     * Returns an array of Strings of all the values that violated the constraint.
     * @return an array of Strings of all the values that violated the constraint
     */
    public Object[] getValues() {
        return values;
    }
    
    /**
     * Sets <code>values</code> to the array of Strings that violated the constraint.
     * @param values the array of Strings that violated the constraint.
     */
    public void setValues(Object[] values) {
        this.values = values;
    }

}
