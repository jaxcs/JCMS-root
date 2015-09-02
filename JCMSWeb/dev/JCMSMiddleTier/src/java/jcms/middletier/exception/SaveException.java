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

package jcms.middletier.exception;

import jcms.middletier.dto.ValidationMessageList;
import jcms.integrationtier.exception.SaveEntityException;

/**
 *
 * @author cnh
 */
public class SaveException extends SaveEntityException
{
    private static final long serialVersionUID = 1L;
    
    private ValidationMessageList messages = null;

    /**
     * Creates a new instance of <code>SaveException</code> without detail message.
     */
    public SaveException() {
    }

    /**
     * Creates a new instance of <code>SaveException</code> without detail message.
     */
    public SaveException(ValidationMessageList messages)
    {
        setMessages(messages);
    }

    /**
     * Constructs an instance of <code>SaveException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SaveException(String msg) {
        super(msg);
    }

    /**
     * @return the messages
     */
    public ValidationMessageList getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(ValidationMessageList messages) {
        this.messages = messages;
    }
}
