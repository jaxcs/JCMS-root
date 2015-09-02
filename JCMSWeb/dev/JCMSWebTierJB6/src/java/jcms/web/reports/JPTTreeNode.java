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

package jcms.web.reports;

/**
 *
 * @author mkamato
 */

import org.richfaces.model.TreeNodeImpl;
import jcms.integrationtier.dto.JPTDTO;

public class JPTTreeNode extends TreeNodeImpl {
    
    private JPTDTO data;
    
    public JPTTreeNode(JPTDTO theData){
        data = theData;
    }
    
    public JPTTreeNode(){}

    /**
     * @return the data
     */
    public JPTDTO getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(JPTDTO data) {
        this.data = data;
    }
}
