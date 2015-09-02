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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.web.base.WTBaseBackingBean;

/**
 *
 * @author rkavitha
 */
public class UniqueNewTagValidator extends WTBaseBackingBean implements Validator {

    protected FacesMessage buildErrorMessage(String errorMessage) {
        FacesMessage returnMessage = new FacesMessage();
        returnMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        returnMessage.setSummary(errorMessage);
        return returnMessage;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        String errMsg = null;
        String s = (String) value;

        if ((s != null) && (!s.isEmpty())) {
            // Check that user name is unique.
            MouseEntity mouseEntity = (MouseEntity) getRepositoryService().
                    baseFindByNewTag(new MouseEntity(), s);

            if (mouseEntity != null) {
                errMsg = "Replacement Tag already exists. Please choose a unique Replacement Tag.";
                throw new ValidatorException(buildErrorMessage(errMsg));
            }
        }
    }
}