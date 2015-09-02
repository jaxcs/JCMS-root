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

package com.corejsf;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 *
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date:  $   <p>
 * @author Kavitha Rama
 * @version $Revision:  $
 */
public class PagerRenderer extends Renderer
{
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        String id = component.getClientId(context);
        UIComponent parent = component;
        while (!(parent instanceof UIForm)) parent = parent.getParent();
        String formId = parent.getClientId(context);

        ResponseWriter writer = context.getResponseWriter();

        String styleClass = (String) component.getAttributes().get("styleClass");
        String selectedStyleClass
                = (String) component.getAttributes().get("selectedStyleClass");
        String dataTableId = (String) component.getAttributes().get("dataTableId");
        Integer a = (Integer) component.getAttributes().get("showpages");
        int showpages = a == null ? 0 : a.intValue();

        // find the component with the given ID

        UIData data = (UIData) component.findComponent(dataTableId);

        int first = data.getFirst();
        int itemcount = data.getRowCount();
        int pagesize = data.getRows();
        if (pagesize <= 0) pagesize = itemcount;

        int pages = itemcount / pagesize;
        if (itemcount % pagesize != 0) pages++;

        int currentPage = first / pagesize;
        if (first >= itemcount - pagesize) currentPage = pages - 1;
        int startPage = 0;
        int endPage = pages;
        if (showpages > 0) {
            startPage = (currentPage / showpages) * showpages;
            endPage = Math.min(startPage + showpages, pages);
        }

        if (currentPage > 0)
            writeLink(writer, component, formId, id, "<", styleClass);

        if (startPage > 0)
            writeLink(writer, component, formId, id, "<<", styleClass);

        for (int i = startPage; i < endPage; i++) {
            writeLink(writer, component, formId, id, "" + (i + 1),
                    i == currentPage ? selectedStyleClass : styleClass);
        }

        if (endPage < pages)
            writeLink(writer, component, formId, id, ">>", styleClass);

        if (first < itemcount - pagesize)
            writeLink(writer, component, formId, id, ">", styleClass);

        // hidden field to hold result
        writeHiddenField(writer, component, id);
    }

    private void writeLink(ResponseWriter writer, UIComponent component,
            String formId, String id, String value, String styleClass)
            throws IOException {
        writer.writeText(" ", null);
        writer.startElement("a", component);
        writer.writeAttribute("href", "#", null);
        writer.writeAttribute("onclick", onclickCode(formId, id, value), null);
        if (styleClass != null)
            writer.writeAttribute("class", styleClass, "styleClass");
        writer.writeText(value, null);
        writer.endElement("a");
    }

    private String onclickCode(String formId, String id, String value) {
        StringBuilder builder = new StringBuilder();
        builder.append("document.forms[");
        builder.append("'");
        builder.append(formId);
        builder.append("'");
        builder.append("]['");
        builder.append(id);
        builder.append("'].value='");
        builder.append(value);
        builder.append("';");
        builder.append(" document.forms[");
        builder.append("'");
        builder.append(formId);
        builder.append("'");
        builder.append("].submit()");
        builder.append("; return false;");
        return builder.toString();
    }

    private void writeHiddenField(ResponseWriter writer, UIComponent component,
            String id) throws IOException {
        writer.startElement("input", component);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", id, null);
        writer.endElement("input");
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        String id = component.getClientId(context);
        Map<String, String> parameters
                = context.getExternalContext().getRequestParameterMap();

        String response =  parameters.get(id);
        if (response == null || response.equals("")) return;

        String dataTableId = (String) component.getAttributes().get("dataTableId");
        Integer a = (Integer) component.getAttributes().get("showpages");
        int showpages = a == null ? 0 : a.intValue();

        UIData data = (UIData) component.findComponent(dataTableId);

        int first = data.getFirst();
        int itemcount = data.getRowCount();
        int pagesize = data.getRows();
        if (pagesize <= 0) pagesize = itemcount;

        if (response.equals("<")) first -= pagesize;
        else if (response.equals(">")) first += pagesize;
        else if (response.equals("<<")) first -= pagesize * showpages;
        else if (response.equals(">>")) first += pagesize * showpages;
        else {
            int page = Integer.parseInt(response);
            first = (page - 1) * pagesize;
        }
        if (first + pagesize > itemcount) first = itemcount - pagesize;
        if (first < 0) first = 0;
        data.setFirst(first);
    }
}