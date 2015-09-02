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

package jcms.web.converter;

/**
 *
 * @author bas
 */

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import jcms.integrationtier.dao.ExpDataDAO;
import jcms.web.base.WTBaseConverter;
import jcms.integrationtier.dto.ExpDataDTO;

public class ExpDataDTOConverter extends WTBaseConverter implements Converter {
    /**
     * Purpose: Convert the string value, primary key, selected by the User
     *      from a list of SelectItems and return the corresponding object the
     *      key maps to.
     * @param context Java Server Faces context
     * @param component JSF UIComponent
     * @param value key value selected from JSF SelectItem component
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ExpDataDTO dto = null;
        if (value != null && value.trim().length() > 0){
            try {
                    dto = new ExpDataDAO().getExpData(Integer.parseInt(value));
                
            }
            catch (NullPointerException npe) {
               this.getLogger().logWarn(this.formatLogMessage("NullPointerException ", "getAsObject"));
               throw new ConverterException("Trying to convert a null ExpData record.  "
                       + "Please report this to the system administrator.  ");
           } 
            catch (NumberFormatException nfe) {
               dto = new ExpDataDAO().getExpData(0);
               this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: you entered a DataID that is not a number.", null));
               this.getLogger().logWarn(this.formatLogMessage("NumberFormatException ", "getAsObject"));
               
            }
        }
        return dto;
}
    
    /**
     * Purpose: Convert the object type, entity or dto, selected by the User
     *      from a list of SelectItems and return the corresponding primary key
     *      the entity maps to.
     * @param context Java Server Faces context
     * @param component JSF UIComponent
     * @param value entity or dto selected from JSF SelectItem component
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String key = "0";

        if (value != null && (!value.equals(""))) {
            key = (((ExpDataDTO) value).getExpDataKey()).toString();
        }

        return key;
    }

}

    