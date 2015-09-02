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
import jcms.integrationtier.dao.CVAdministrationDAO;

/**
 *
 * @author cnh
 */
public class ControlledVocabularyGroupDTO {
    private String _controlledVocabularyGroup_key = "";
    private String name = "";
    private String sortOrder = "";
    private ArrayList<ControlledVocabularyDTO> controlledVocabularyList = null;

    public ControlledVocabularyGroupDTO() {
    }
    
    public ControlledVocabularyGroupDTO(String _controlledVocabularyGroup_key, 
                                        String name,
                                        String sortOrder) {
        this.setControlledVocabularyGroup_key(_controlledVocabularyGroup_key);
        this.setName(name);
        this.setSortOrder(sortOrder);
        this.initializeControlledVocabulary();
    }
    
    private void initializeControlledVocabulary () {
        this.setControlledVocabularyList(new CVAdministrationDAO().getControlledVocabularyByGroup(this.getControlledVocabularyGroup_key()));
    }
    
    /**
     * @return the _controlledVocabularyGroup_key
     */
    public String getControlledVocabularyGroup_key() {
        return _controlledVocabularyGroup_key;
    }

    /**
     * @param controlledVocabularyGroup_key the _controlledVocabularyGroup_key to set
     */
    public void setControlledVocabularyGroup_key(String controlledVocabularyGroup_key) {
        this._controlledVocabularyGroup_key = controlledVocabularyGroup_key;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the controlledVocabularyList
     */
    public ArrayList<ControlledVocabularyDTO> getControlledVocabularyList() {
        return controlledVocabularyList;
    }

    /**
     * @param controlledVocabularyList the controlledVocabularyList to set
     */
    public void setControlledVocabularyList(ArrayList<ControlledVocabularyDTO> controlledVocabularyList) {
        this.controlledVocabularyList = controlledVocabularyList;
    }
    
}
