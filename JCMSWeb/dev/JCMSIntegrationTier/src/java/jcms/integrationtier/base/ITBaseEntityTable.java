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

package jcms.integrationtier.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.dto.DeleteDTO;

/**
 *
 * @author rkavitha
 */
public class ITBaseEntityTable implements Serializable
{
    // -Xlint flags this as warning if removed.
    private static final long serialVersionUID = 1L;

    private List<ITBaseEntityInterface> saveList    = new ArrayList<ITBaseEntityInterface> ();
    private DeleteDTO                   deleteDTO   = new DeleteDTO();

    public ITBaseEntityTable()
    {
    }

    public void add(ITBaseEntityInterface entity)
    {
        saveList.add(entity);
    }

    public void add(List<ITBaseEntityInterface> entityList)
    {
        if (entityList != null)
        {
            saveList.addAll(entityList);
        }
    }

    public void addSave(ITBaseEntityInterface entity)
    {
        saveList.add(entity);
    }

    public void addSave(List<ITBaseEntityInterface> entityList)
    {
        if (entityList != null)
        {
            saveList.addAll(entityList);
        }
    }

    public void addDelete(ITBaseEntityInterface entity)
    {
        getDeleteDTO().add(entity);
    }

    public void addDelete(List<ITBaseEntityInterface> entityList)
    {
        if (entityList != null)
        {
            getDeleteDTO().add(entityList);
        }
    }

    /**
     * @return the list
     */
    public List<ITBaseEntityInterface> getList() {
        return saveList;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<ITBaseEntityInterface> list) {
        this.saveList = list;
    }

    /**
     * @return the list
     */
    public List<ITBaseEntityInterface> getSaveList() {
        return saveList;
    }

    /**
     * @param list the list to set
     */
    public void setSaveList(List<ITBaseEntityInterface> list) {
        this.saveList = list;
    }

    /**
     * @return the deleteList
     */
    public List<ITBaseEntityInterface> getDeleteList() {
        return getDeleteDTO().getDeleteList();
    }

    /**
     * @param deleteList the deleteList to set
     */
    public void setDeleteList(List<ITBaseEntityInterface> deleteList) {
        this.getDeleteDTO().setDeleteList(deleteList);
    }

    /**
     * @return the deleteDTO
     */
    public DeleteDTO getDeleteDTO() {
        return deleteDTO;
    }

    /**
     * @param deleteDTO the deleteDTO to set
     */
    public void setDeleteDTO(DeleteDTO deleteDTO) {
        this.deleteDTO = deleteDTO;
    }


}
