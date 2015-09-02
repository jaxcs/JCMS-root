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
import javax.persistence.Transient;

/**
 *
 * @author rkavitha
 */
public class ITBaseEntity extends ITBaseObject implements Serializable
{
    // -Xlint flags this as warning if removed.
    private static final long serialVersionUID = 1L;

    // Show Detail property indicates whether or not to show details.
    // Managed by the WebTier.  Available to all Entities.
    @Transient
    private Boolean     showDetails     = Boolean.FALSE;

    @Transient
    private String      showDetailLabel = "show";

    // Identifies whether or not this data is modified
    @Transient
    private Boolean     isDirty         = Boolean.FALSE;


    /**
     * @return the showDetails
     */
    public Boolean getShowDetails() {
        return showDetails;
    }

    /**
     * @param showDetails the showDetails to set
     */
    public void setShowDetails(Boolean showDetails) {
        this.showDetails = showDetails;
    }

    /**
     * @return the showDetailLabel
     */
    public String getShowDetailLabel() 
    {
        if (showDetails)
            showDetailLabel = "hide";
        else
            showDetailLabel = "show";

        return showDetailLabel;
    }

    /**
     * @param showDetailLabel the showDetailLabel to set
     */
    public void setShowDetailLabel(String showDetailLabel) {
        this.showDetailLabel = showDetailLabel;
    }

    /**
     * @return the isDirty
     */
    public Boolean getIsDirty() {
        return isDirty;
    }

    /**
     * @param isDirty the isDirty to set
     */
    public void setIsDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    /**
     * Indicate the data has been modified.
     */
    public void setIsDirty() {
        this.isDirty = Boolean.TRUE;
    }
}
