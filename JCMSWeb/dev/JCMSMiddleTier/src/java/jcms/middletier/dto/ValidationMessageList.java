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

package jcms.middletier.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>File name:</b> ValidationMessageList.java  <p>
 * <b>RsDate developed:</b>  November 2009 <p>
 * <b>Purpose:</b> Contains one or more error messages.  <p>
 * <b>Overview:</b> Use this object to pass meaningful error messages back to
 *      the web tier.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */
public class ValidationMessageList implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private List<ValidationMessageDTO> messageList = null;
    
    /**
     * <b>Purpose:</b> Add a message to the growing list of messages.  <p>
     * @param messageDTO message to add
     * @return void
     */
    public void addMessage(ValidationMessageDTO messageDTO)
    {
        if (getMessageList() == null)
            setMessageList(new ArrayList<ValidationMessageDTO>());
        
        messageList.add(messageDTO);
    }

    /**
     * @return the messageList
     */
    public List<ValidationMessageDTO> getMessageList() {
        return messageList;
    }

    /**
     * @param messageList the messageList to set
     */
    public void setMessageList(List<ValidationMessageDTO> messageList) {
        this.messageList = messageList;
    }

}
