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

package jcms.middletier.businessobject;

import java.util.List;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.exception.EntityNotFoundException;


/**
 * <b>File name:</b>  EnumerationBO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides business level implementations of enumeration data.  <p>
 * <b>Overview:</b>  Provides methods to act on system enumerations.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class EnumerationBO extends JCMSBO
{

    /**
     *  <b>Purpose:</b>  Find all <code>EnumerationClassItemEntity</code> items. <br>
     *  <b>Overview:</b>  Find all <code>EnumerationClassItemEntity</code> items.
     *      Null is returned when the result set has zero (0) rows.  <br>
     * @param enumerationItemkey Contains enumeration item key to search for
     * @return List<EnumerationClassItemEntity> return <code>List<EnumerationClassItemEntity></code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    public List<ITBaseEntityInterface> findEnumerationClassByItem(Integer enumerationItemkey) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = null;
        try
        {
            list = this.getIntegrationTierPortal().getSystemFacadeLocal().findEnumerationClassByItem(enumerationItemkey);
        }
        catch (Exception ex) //(EntityNotFoundException ex)
        {
            list = null;
        }

        return list;
    }

    /**
     *  <b>Purpose:</b>  Find all <code>EnumerationClassItemEntity</code> items. <br>
     *  <b>Overview:</b>  Find all <code>EnumerationClassItemEntity</code> items.
     *      Null is returned when the result set has zero (0) rows.  <br>
     * @param enumerationClasskey Contains enumeration class key to search for
     * @return List<EnumerationClassItemEntity> return <code>List<EnumerationClassItemEntity></code> entity.
     * @throws EntityNotFoundException  Unable to find database record
     *                                  for the given parameter.
     *                                  Checked Exception.
     */
    public List<ITBaseEntityInterface> findEnumerationItemByClass(Integer enumerationClasskey) throws EntityNotFoundException
    {
        List<ITBaseEntityInterface> list = null;
        try
        {
            list = this.getIntegrationTierPortal().getSystemFacadeLocal().findEnumerationItemByClass(enumerationClasskey);
        }
        catch (Exception ex) //(EntityNotFoundException ex)
        {
            list = null;
        }

        return list;
    }


}




