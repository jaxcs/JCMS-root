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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import jcms.integrationtier.base.ITBaseDTO;
import jcms.integrationtier.base.ITBaseEntityInterface;
import org.richfaces.component.UIExtendedDataTable;

//@ManagedBean
//@SessionScoped
public class ExtendedTableBean extends DataTable implements Serializable
{
    public static final String SORTMODE_SINGLE      = "single";
    public static final String SORTMODE_MULTIPLE    = "multiple";

    public static final String SELECTIONMODE_SINGLE = "single";
    public static final String SELECTIONMODE_MULTIPLE  = "multiple";
    public static final String SELECTIONMODE_NONE   = "none";

    // ExtendedDataTable Modes
    private String      sortMode            = ExtendedTableBean.SORTMODE_SINGLE;
    private String      selectionMode       = ExtendedTableBean.SELECTIONMODE_SINGLE;

    private Collection<Object> selection;
    private List<ITBaseEntityInterface> selectionItems = new ArrayList<ITBaseEntityInterface>();

    // For new DTOs replacing Entity's
    private List<ITBaseDTO> selectionDTOItems = new ArrayList<ITBaseDTO>();
    /**
     * <b>Purpose:</b> Constructor loads procedure vocabulary - lookup data <br />
     */
    public ExtendedTableBean()
    {

    }

    // BUSINESS LOGIC

    public void clearSelection()
    {
        getSelectionItems().clear();
        this.getSelectionDTOItems().clear();
    }

    public void selectionListener(AjaxBehaviorEvent event){
        UIExtendedDataTable dataTable = (UIExtendedDataTable)event.getComponent();
        Object originalKey = dataTable.getRowKey();
        getSelectionItems().clear();

        for (Object selectionKey: getSelection()) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                getSelectionItems().add((ITBaseEntityInterface)dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }
    
    public void selectionDTOListener(AjaxBehaviorEvent event){
        UIExtendedDataTable dataTable = (UIExtendedDataTable)event.getComponent();
        Object originalKey = dataTable.getRowKey();
        getSelectionDTOItems().clear();

        for (Object selectionKey: getSelection()) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                getSelectionDTOItems().add((ITBaseDTO)dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }
      
    // GETTERS AND SETTERS

    /**
     * @return the sortMode
     */
    public String getSortMode() {
        return sortMode;
    }

    /**
     * <b>Range:</b> {SpecialHandling.SORTMODE_SINGLE, SpecialHandling.SORTMODE_MULTI}
     * @param sortMode the sortMode to set
     */
    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    /**
     * @return the selectionMode
     */
    public String getSelectionMode() {
        return selectionMode;
    }

    /**
     * <b>Range:</b> {SpecialHandling.SELECTIONMODE_SINGLE,
     *                SpecialHandling.SELECTIONMODE_MULTI,
     *                SpecialHandling.SELECTIONMODE_NONE}
     * @param selectionMode the selectionMode to set
     */
    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    /**
     * @return the selection
     */
    public Collection<Object> getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(Collection<Object> selection) {
        this.selection = selection;
    }

    /**
     * @return the selectionItems
     */
    public List<ITBaseEntityInterface> getSelectionItems() {
        return selectionItems;
    }

    /**
     * @param selectionItems the selectionItems to set
     */
    public void setSelectionItems(List<ITBaseEntityInterface> selectionItems) {
        this.selectionItems = selectionItems;
    }

    /**
     * @return the selectionDTOItems
     */
    public List<ITBaseDTO> getSelectionDTOItems() {
        return selectionDTOItems;
    }

    /**
     * @param selectionDTOItems the selectionDTOItems to set
     */
    public void setSelectionDTOItems(List<ITBaseDTO> selectionDTOItems) {
        this.selectionDTOItems = selectionDTOItems;
    }
}