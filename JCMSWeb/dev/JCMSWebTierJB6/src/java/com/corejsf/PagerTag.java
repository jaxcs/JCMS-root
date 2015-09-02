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

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

/**
 *
 * @author rkavitha
 */
public class PagerTag extends UIComponentELTag {
    private ValueExpression showpages;
    private ValueExpression dataTableId;
    private ValueExpression styleClass;
    private ValueExpression selectedStyleClass;

    public String getRendererType() { return "com.corejsf.Pager"; }
    public String getComponentType() { return "javax.faces.Output"; }

    public void setShowpages(ValueExpression newValue) { showpages = newValue; }
    public void setDataTableId(ValueExpression newValue) { dataTableId = newValue; }
    public void setStyleClass(ValueExpression newValue) { styleClass = newValue; }
    public void setSelectedStyleClass(ValueExpression newValue) {
        selectedStyleClass = newValue;
    }

    @Override
    public void setProperties(UIComponent component) {
        super.setProperties(component);
        if (component == null) return;
        component.setValueExpression("showpages", showpages);
        component.setValueExpression("dataTableId", dataTableId);
        component.setValueExpression("styleClass", styleClass);
        component.setValueExpression("selectedStyleClass", selectedStyleClass);
    }

    @Override
    public void release() {
        super.release();
        showpages = null;
        dataTableId = null;
        styleClass = null;
        selectedStyleClass = null;
    }
}