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

package jcms.web.validator;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.jax.cs.rscommon.Time;

/**
 *
 * @author cnh
 */
public class TimeValidator implements Validator
{
    private StringBuffer errorMessage = new StringBuffer();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
                         throws ValidatorException
    {

        if (value == null) return;

        // Refer to page 255 of "Core JavaServer Faces"
        if (value instanceof Date)
        {
            // Time entry encapsulated in a Date object.
            if (! this.isValid((Date)value))
            {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary(errorMessage.toString());
                
                throw new ValidatorException(message);
            }
        }

    }

    private Boolean isValid(Date timeEntry)
    {
        Boolean valid = Boolean.TRUE;
        Time time = new Time();

        // Time entry encapsulated in a Date object.
        String timeValue = time.extractTime(timeEntry);

        if (! time.isValid(timeValue) )
        {
            errorMessage.append("Time entry "+ timeValue +" is invalid.  Please check the time entries " +
                    "and see that each conforms to format HH:MM and range [1-11]:[0-59], {hour colon minute}.");
            valid = Boolean.FALSE;
        }

        return valid;
    }

}
