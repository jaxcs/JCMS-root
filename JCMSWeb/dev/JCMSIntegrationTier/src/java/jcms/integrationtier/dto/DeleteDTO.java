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


package jcms.integrationtier.dto;

import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseObject;

/**
 * <b>File name:</b>  DeleteDTO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a transfer object for objects marked for deletion.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class DeleteDTO extends ITBaseObject
{
    // -Xlint flags this as warning if removed.
    private static final long serialVersionUID = 1L;

    private List<ITBaseEntityInterface> deleteList = new ArrayList<ITBaseEntityInterface>();

    /**
     * <b>Purpose:</b> Add an entity object to the list of items to be deleted
     *      from the database. <br />
     * @param entity object to delete
     * @return void
     */
    public void add(ITBaseEntityInterface entity)
    {
        getDeleteList().add(entity);
    }

    /**
     * <b>Purpose:</b> Add a list of entity objects to the list of items to be deleted
     *      from the database. <br />
     * @param list objects to delete
     * @return void
     */
    public void add(List<ITBaseEntityInterface> list)
    {
        getDeleteList().addAll(list);
    }

    /**
     * @return the deleteList
     */
    public List<ITBaseEntityInterface> getDeleteList() {
        return deleteList;
    }

    /**
     * @param deleteList the deleteList to set
     */
    public void setDeleteList(List<ITBaseEntityInterface> deleteList) {
        this.deleteList = deleteList;
    }

    /**
     * Clear deleteList of all objects.
     */
    public void resetDeleteList() {
        deleteList = null;
        deleteList = new ArrayList<ITBaseEntityInterface>();
    }
}
