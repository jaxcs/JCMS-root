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
 * ConstraintViolationExceptionMySqlPatch.java
 *
 * Created on August 16, 2010
 *
 * This method exists to patch bug #3548 in MySQL (bug exists as of release 5.2.0)
 * wherein a thrown org.hibernate.exception.ConstraintViolationExceptionMySqlPatch, which
 * has a getter to retrieve the Constraint name, always returns null, even when
 * a constraint name has been defined for the column.
 *
 * This custom exception, then, simply extends the org.hibernate.exception.ConstraintViolationExceptionMySqlPatch
 * and patches the missing Constraint name by attempting to extract it from
 * the detailMessage, which appears to contain the constraint name. The detailMessage
 * appears to have the format:<br />
 * <em>Duplicate entry 'xxx' for key 'YYY'</em><b />
 * where xxx is the key value(s) causing the violation, and YYY is the constraint name.
 *
 * If the detailMessage instance does not conform to this scheme, then the constraint
 * name returned is null.
 *
 * @author mrelac
 */

package jcms.integrationtier.exception;

import java.sql.SQLException;
import org.hibernate.exception.ConstraintViolationException;


public class ConstraintViolationExceptionMySqlPatch extends ConstraintViolationException {
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private String   constraintName = null;
    private Object[] values         = new Object[ 0 ];

    public ConstraintViolationExceptionMySqlPatch( ConstraintViolationException cve ) {
        super( cve.getMessage(), cve.getSQLException(), cve.getSQL(), cve.getConstraintName() );
        this.constraintName = extractConstraintName();
    }

    public ConstraintViolationExceptionMySqlPatch(String message, SQLException root, String sql, String constraintName) {
        super(message, root, sql, constraintName);
        this.constraintName = extractConstraintName();
    }

    /**
     * Patched version that returns the constraint name by having extracted it
     * from the sql exception detail message. This entire class exists because
     * of MySQL bug #3548 (http://bugs.mysql.com/bug.php?id=3548) that always
     * returns a null constraint name, even when one was specified in the database.
     * @return the constraint name
     */
    @Override public String getConstraintName() {
        return this.constraintName;
    }

    /**
     * Returns an array of Object of all the values that violated the constraint.
     * @return an array of Object of all the values that violated the constraint
     */
    public Object[] getValues() {
        return values;
    }

    // private methods

    /**
     * A typical unique constraint violation looks like this one:
     *    Duplicate entry '42-d1' for key 'UniqueDepartment-InstitutionKey'
     * There seem to always be four single quotation marks, and the values that
     * violated the constraint are in the first quoted string, separated by a
     * single hyphen. That is the algorithm used to return the constraint name.
     * The violating values are placed directly into the instance variable used
     * to return the values that caused the unique constraint violation.
     *
     * @return the constraint name
     */
    private String extractConstraintName() {
        String message = this.getSQLException().getMessage();

        if ( ! message.isEmpty() ) {
            String[] splitMsg = message.split( "'" );                           // message is in the format "Duplicate entry 'xxx' for key 'YYY'"
            if ( splitMsg.length == 4 ) {
                values = splitMsg[ 1 ].split( "-" );                            // Return the values that caused the constraint violation in an array.
                return splitMsg[ 3 ];                                           // The constraint name is in the last array element.
            }
        }

        return super.getConstraintName();                                       // message was not in the format expected for extraction.
    }
}