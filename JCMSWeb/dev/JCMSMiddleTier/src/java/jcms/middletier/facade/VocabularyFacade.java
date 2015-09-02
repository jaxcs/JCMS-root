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

package jcms.middletier.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.middletier.base.BTBaseFacade;
import jcms.middletier.dto.ListSupportDTO;
import jcms.middletier.service.VocabularyAppService;

/**
 * <b>File name:</b>  VocabularyFacade.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a single access point to all controlled vocabulary.  <p>
 * <b>Overview:</b>  The idea is to cache all controlled vocabulary for fast
 *      web tier response time.  Controlled vocabulary is requested for all
 *      drop down selection boxes on a web page.  Web pages may also have
 *      hundreds of records displayed on a page that also need lists of
 *      vocabulary.  This class design is in place to mitigate long response
 *      times building a web form.  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  All controlled vocabulary classes inherit <code>BaseEntityTable</code>.
 *      All result sets are returned as ancestor object <code>BaseEntityTable</code>.
 *      There are internal methods to extract the proper controlled vocabulary list.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Stateless(name=FacadeDao.VocabularyFacade)
public class VocabularyFacade extends BTBaseFacade implements VocabularyFacadeLocal,
                                                            VocabularyFacadeRemote
{
    VocabularyAppService vocabularyAppService = new VocabularyAppService();


}