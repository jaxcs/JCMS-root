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

package jcms.web.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.base.WTBaseBackingBean;

/**
 * <b>File name:</b>  LoadQueryBean.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 *  <b>Purpose:</b>  Backing bean for Mouse Query Page.  <p>
 * <b>Overview:</b> Contains the getters and setters for all display elements.
 *      Backing bean conforms to Java Bean structural design.
 *      Represents JSF components and may or may not direct JSF action calls.
 *      Contains business logic to run, save and list query objects. Also
 *      contains File download option in case user wants to download search
 *      results.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2010-11-02 16:10:06 -0400 (Tue, 02 Nov 2010) $   <p>
 * @author Kavitha Rama
 * @version $Revision: 11448 $
 */
public class LoadQueryBean extends WTBaseBackingBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<QueryDefinitionEntity> queryLst = new ArrayList<QueryDefinitionEntity>();

    public LoadQueryBean() {
        try {
            // get the workgroups that user belongs to
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
            queryLst = new ListSupportDTO().getUserDefinedQueriesByWorkgroups(wgLst);   
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ", e.getMessage()));
        }
    }
    
    /**
     * @return the queryLst
     */
    public List<QueryDefinitionEntity> getQueryLst() {
        return queryLst;
    }

    /**
     * @param queryLst the queryLst to set
     */
    public void setQueryLst(List<QueryDefinitionEntity> queryLst) {
        this.queryLst = queryLst;
    }
}