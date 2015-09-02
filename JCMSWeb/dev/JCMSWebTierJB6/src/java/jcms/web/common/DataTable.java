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

package jcms.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.web.base.WTBaseObject;

/**
 * <b>File name:</b>  DataTable.java  <p>
 * <b>RsDate developed:</b> June 2010 <p>
 * <b>Purpose:</b> Provides standard operations to move a row of data to top,
 *      up one, down one, and to bottom of list.  <br />
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2011-05-18 12:28:36 -0400 (Wed, 18 May 2011) $   <p>
 * @author Craig Hanna
 * @version $Revision: 12972 $
 */  
public class DataTable extends WTBaseObject
{
    public static final String MOVE_FIRST           = "FIRST";
    public static final String MOVE_UP              = "UP";
    public static final String MOVE_DOWN            = "DOWN";
    public static final String MOVE_LAST            = "LAST";

    // This map contains the indices of all rows selected or marked for move
    protected HashMap<Integer, String>  indexMap = null;


    /**
     * Constructor
     */
    public DataTable()
    {
    }

    // BUSINESS LOGIC

    /**
     * <b>Purpose:</b> Returns all the indices of rows selected by the user.  <br />
     * @return HashMap<Integer, String> list index of selected row and don't care string
     */
    public  HashMap<Integer, String> getDataSelection()
    {
        return indexMap;
    }

    /**
     * <b>Purpose:</b> Create a map of all the rows in the list.  <br />
     * <b>Overview:</b> Data selection refers to the rows selected by the user
     *      or the rows calculated by business logic to move to another location
     *      in a list of rows.  The list simple identifies the index of each
     *      row to move.  The list getKey returns the List index of a row.  <br />
     * @param list list of entity selected by user
     */
    public void setDataSelection(List<ITBaseEntityInterface> list)
    {
        indexMap         = new HashMap<Integer, String>();
        Iterator<ITBaseEntityInterface> iterator    = list.iterator();

        while (iterator.hasNext())
        {
            Integer key = iterator.next().getKey();
            indexMap.put(key, "Dont care value");
        }

    }

    /**
     * <b>Purpose:</b> Moves a selection of rows. <br />
     * @param currentList list of extended table rows
     * @param direction tells how to move the rows,
     *      {DataTable.FIRST, DataTable.UP, DataTable.DOWN, DataTable.LAST}
     * @return List<ITBaseEntityInterface> reordered list of objects
     */
    public void moveSelection(List<ITBaseEntityInterface> currentList,
                                   String direction)
    {
        if (direction.equalsIgnoreCase(DataTable.MOVE_LAST))
            this.moveLastSelection(currentList);
        else
            this.moveSelection(currentList, direction, null);
    }

    /**
     * <b>Purpose:</b> Moves a selection of rows. <br />
     * @param currentList list of extended table rows
     * @param position the index to move the rows to within a list of objects
     * @return List<ITBaseEntityInterface> reordered list of objects
     */
    public void moveSelection(List<ITBaseEntityInterface> currentList,
                              Integer position)
    {
        if (position.intValue() == ((Integer)(currentList.size() - 1)).intValue())
            // Moving to last location just use the normal move last code.
            this.moveLastSelection(currentList);
        else
            this.moveSelection(currentList, null, position);
    }

    /**
     * <b>Purpose:</b> Moves a selection of protocol tasks one level up, down, or
     *      to the top of the list. <br />
     * @param currentList list of all data table rows
     * @param direction tells where to move the rows,
     *      {DataTable.FIRST, DataTable.UP, DataTable.DOWN}
     * @return List<ITBaseEntityInterface> reordered list of objects
     */
    protected void moveSelection(List<ITBaseEntityInterface> currentList, String direction, Integer position)
    {
        if ((currentList != null) && (currentList.size() > 0))
        {
            HashMap<Integer, String>    selectionMap    = this.getDataSelection();
            List<ITBaseEntityInterface> moveList        = this.removeRows(currentList, selectionMap);

            // For first, up, and down.
            Iterator    iterator    = selectionMap.keySet().iterator();

            Integer     newPosition = 0;

            if ((selectionMap.size() > 0) && (moveList.size() > 0))
            {
                // Starting index of first object to move.
                Integer     baseIndex = 0;

                if (position != null)
                    newPosition = position;
                else if (direction.equalsIgnoreCase(DataTable.MOVE_FIRST))
                    baseIndex   = selectionMap.keySet().iterator().next();
                else if (direction.equalsIgnoreCase(DataTable.MOVE_UP))
                    baseIndex   = 1;   // move up
                else if (direction.equalsIgnoreCase(DataTable.MOVE_DOWN))
                    baseIndex   = -1;   // move down

                this.getLogger().logInfo(this.formatLogMessage("Start\tBaseIndex\tNewPosition", "moveSelection"));

                while (iterator.hasNext())
                {
                    Integer startingPosition    = (Integer) iterator.next();

                    if (position == null)
                    {
                        // Use conceptual move UP, DOWN, TOP
                        newPosition = startingPosition - baseIndex;
                    }

                    this.getLogger().logInfo(this.formatLogMessage(startingPosition +"\t"+ baseIndex +"\t\t"+ newPosition, "moveSelection"));

                    if (newPosition < 0)
                        newPosition = 0;
                    else if (newPosition > currentList.size())
                        newPosition = currentList.size();

                    currentList.add(newPosition, moveList.remove(0));

                    if (position != null)
                        newPosition++;
                }
            }
        }

    }

    /**
     * <b>Purpose:</b> Moves all rows to the last position in the list, respectively. <br />
     * @param currentList list of extended table rows
     * @return List<ITBaseEntityInterface> reordered list of objects
     */
    protected void moveLastSelection(List<ITBaseEntityInterface> currentList)
    {
        if ((currentList != null) && (currentList.size() > 0))
        {
            HashMap<Integer, String>    selectionMap    = this.getDataSelection();
            List<ITBaseEntityInterface> moveList        = this.removeRows(currentList, selectionMap);

            if ((selectionMap.size() > 0) && (moveList.size() > 0))
            {
                List<Integer> topDownList = new ArrayList<Integer>();
                Iterator      iterator    = selectionMap.keySet().iterator();
                
                while (iterator.hasNext())
                    topDownList.add((Integer) iterator.next());

                // Starting index of first object to move.
                Integer     baseIndex   = topDownList.get(topDownList.size()-1);
//                this.selection.clear();
                
                for (int i=topDownList.size()-1; i >= 0 ;i--)
                {
                    // Move last end

                    Integer startingPosition    = topDownList.get(i);
                    Integer newPosition         = currentList.size() - (baseIndex - startingPosition);

                    if (newPosition < 0)
                        newPosition = 0;
                    else if (newPosition > currentList.size())
                        newPosition = currentList.size();

                    currentList.add(newPosition, moveList.remove(moveList.size() - 1));
//                    this.selection.addKey(newPosition);
                }
            }
        }

    }

    /**
     * <b>Purpose:</b> Remove selected rows and return them in a separate list.  <br />
     * @param currentList a list of rows, unselected and selected
     * @param map a hash of the index of the selected row and a don't care string
     * @return List<ITBaseEntityInterface>
     */
    protected List<ITBaseEntityInterface> removeRows(List<ITBaseEntityInterface> currentList, HashMap<Integer, String> map)
    {
        List<ITBaseEntityInterface> list        = new ArrayList<ITBaseEntityInterface>();
        Integer                     offset      = 0;

        Set<Integer>                keys        = map.keySet();
        Iterator                    iterator    = keys.iterator();

        // Get a copy then remove each selected object from currentList.
        while (iterator.hasNext())
        {
            Integer nextKey = (Integer) iterator.next();

            Integer removeIndex = nextKey - offset;

            ITBaseEntityInterface moveObject = currentList.remove(removeIndex.intValue());
            list.add(moveObject);

            // Accounts for an object being removed from currentList
            offset = offset + 1;
        }

        return list;
    }


    // GETTERS AND SETTERS



}
