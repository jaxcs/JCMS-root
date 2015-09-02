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

package jcms.web.base;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import jcms.middletier.dto.ListSupportDTO;


/**
 * <b>File name:</b> WTBaseConverter.java  <p>
 * <b>Date developed:</b> July 2010 <p>
 * <b>Purpose:</b> Ancestor class provides a reference to lists of controlled
 * vocabulary.  <br />
 * <b>Last changed by:</b>   $Author: cnh $ <p>
 * <b>Last changed date:</b> $Date: 2013-01-08 14:49:53 -0500 (Tue, 08 Jan 2013) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 19175 $
 */
public class WTBaseConverter extends WTBaseObject
{
    // Controlled Vocabulary
    private ListSupportDTO listSupportDTO = new ListSupportDTO();

    /**
     * @return the listSupportDTO
     */
    public ListSupportDTO getListSupportDTO() {
        return listSupportDTO;
    }

    /**
     * @param listSupportDTO the listSupportDTO to set
     */
    public void setListSupportDTO(ListSupportDTO listSupportDTO) {
        this.listSupportDTO = listSupportDTO;
    }

    public FacesMessage getMessage(FacesContext context, String key) {
        Locale locale = null;

        UIViewRoot root = context.getViewRoot();

         if (root != null)
             locale = root.getLocale();
         if (locale == null)
             locale = Locale.getDefault();

         ResourceBundle bundle = ResourceBundle.getBundle("com.apress.javaxml.i18n.messages", locale);
         String msg = bundle.getString(key);
         return new FacesMessage(msg);
     }
}