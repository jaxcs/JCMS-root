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

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.web.base.WTBaseConverter;
import javax.faces.convert.Converter;
import jcms.integrationtier.dao.UseScheduleDAO;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 *
 * @author mkamato
 */
public class UseScheduleTermConverter extends WTBaseConverter implements Converter{
    
    UseScheduleDAO dao = new UseScheduleDAO();
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
        UseScheduleTermDTO entity = null;
        try {
            if (value != null && (!value.equals(""))) {
                entity = findEntity(value);
            }
        } catch (Exception e) {
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
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String key = "";
        try {
            if (value != null && (!value.equals(""))) {
                key = ((UseScheduleTermDTO) value).getUseScheduleTermKey();
            }
        } catch (Exception e) {
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
     * Overview:  Converts the unique String representation of CvMouseUseEntity
     *      to the actual object.
     * @param key CvMouseUseEntity primary key
     * @return CvMouseUseEntity object
     */
    private UseScheduleTermDTO findEntity(String key) {
        UseScheduleTermDTO dataTypeObject = null;

//        ArrayList<UseScheduleTermDTO> uses = dao.getUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("colonyManageWorkgroupEntityLst"));
        //changing to use the guest list so the terms will be available for the Kaplan Meier.
        ArrayList<UseScheduleTermDTO> uses = dao.getUseScheduleTerms((ArrayList<WorkgroupEntity>) this.getSessionParameter("guestWorkgroupEntityLst"));
        try {
            for (UseScheduleTermDTO e : uses) {
                if (key.equalsIgnoreCase(e.getUseScheduleTermKey())) {
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
