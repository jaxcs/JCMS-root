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
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.web.base.WTBaseConverter;

/**
 * <b>File name:</b> OwnerConverter.java  <p>
 * <b>Date developed:</b> August 2010 <p>
 * <b>Purpose:</b> A class that converts an object to String to display as
 * JSF component SelectItem and JSF component to an Object to map to
 * backend  <br />
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2010-11-18 15:49:30 -0500 (Thu, 18 Nov 2010) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 11549 $
 */
public class OwnerConverter extends WTBaseConverter implements Converter {
 /**
     * Purpose: Convert the string value, primary key, selected by the User
     *      from a list of SelectItems and return the corresponding object the
     *      key maps to.
     * @param context Java Server Faces context
     * @param component JSF UIComponent
     * @param value key value selected from JSF SelectItem component
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        OwnerEntity entity = null;
        try
        {
            if (value != null && (!value.equals(""))) {
                entity = findEntity(value);
            }
        }
        catch (Exception e) {
            FacesMessage message = this.getMessage(context, "Invalid Value");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(FacesMessage.FACES_MESSAGES, message);
            this.getLogger().logWarn(this.formatLogMessage("ConverterException ",
                    "getAsObject"));
            throw new ConverterException(message);
        }

        return entity;
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
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        String key = "";
        try {
        if (value != null && (!value.equals(""))) {
            key = (((OwnerEntity) value).getKey()).toString();
        }
        }
        catch (Exception e) {
            FacesMessage message = this.getMessage(context, "Invalid Value");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(FacesMessage.FACES_MESSAGES, message);
            this.getLogger().logWarn(this.formatLogMessage("ConverterException ",
                    "getAsString"));
            throw new ConverterException(message);
        }

        return key;
    }

    /**
     * Purpose:  Find the controlled vocabulary data type object for the
     *      given data type key.
     * Overview:  Converts the unique String representation of OwnerEntity
     *      to the actual object.
     * @param key OwnerEntity primary key
     * @return OwnerEntity object
     */
    private OwnerEntity findEntity(String key)
    {
        OwnerEntity dataTypeObject = null;

        try {
        for (OwnerEntity e: this.getListSupportDTO().getAllOwners())
        {
            if (key.equalsIgnoreCase(e.getOwnerKey().toString()))
            {
                dataTypeObject = e;
                break;
            }
        }
        }
        catch (NullPointerException e) {
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