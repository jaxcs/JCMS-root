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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.web.base.WTBaseConverter;

/**
 *
 * @author cnh
 */
public class AlleleStringsConverter extends WTBaseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        String allele = null;
        try {
            if (newValue == null) {
                return "";
            } else 
            if (newValue.equals("")) {
                allele = "";
            } else {
                allele = newValue;
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("Allele conversion failed.");
            context.addMessage(FacesMessage.FACES_MESSAGES, message);
            getLogger().logWarn(this.formatLogMessage("ConverterException ", "getAsObject"));
            throw new ConverterException(message);
        }
        return allele;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String displayValue = "";
        try {
            if (value == null) {
                return displayValue;
            } else {
                displayValue = value.toString();
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("Allele conversion failed.");
            context.addMessage(FacesMessage.FACES_MESSAGES, message);
            this.getLogger().logWarn(this.formatLogMessage("ConverterException ", "getAsString"));
            throw new ConverterException(message);
        }
        return displayValue;
    }

    /**
     * Purpose:  Find the controlled vocabulary data type object for the
     *      given data type key.
     * Overview:  Converts the unique String representation of Gene
     *      to the actual object.
     * @param key Gene primary key
     * @return GeneEntity Data Type object
     */
    private AlleleEntity findEntity(String key) {
       AlleleEntity dataTypeObject = null;

        try {
            for (AlleleEntity e : this.getListSupportDTO().getAllAlleles()) {
                if (key.equalsIgnoreCase(e.getAlleleKey().toString())) {
                    dataTypeObject = e;
                    break;
                }
            }
        } catch (NullPointerException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = this.getMessage(context, "Invalid Key");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(FacesMessage.FACES_MESSAGES, message);
            this.getLogger().logWarn(this.formatLogMessage("NullPointerException ",
                    "getAsObject"));
        }
        return dataTypeObject;
    }    
    
            
}
