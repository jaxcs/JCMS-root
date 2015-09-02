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

/**
 * <b>File name:</b>  ITFilter.java  <p>
 * <b>RsDate developed:</b>  September 2010 <p>
 * <b>Purpose:</b>  Provides object for filter specification between tiers  <p>
 * <b>Overview:</b>
 * This class provides an object with which callers can specify filter criteria.
 * <p />
 *
 * <b>Last changed date:</b> $Date: 2010-10-04 17:30:07 -0400 (Mon, 04 Oct 2010) $   <p />
 * @author Mike Relac
 * @version $Revision: 11198 $
 */

import java.io.Serializable;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

/**
 *
 * @author mrelac
 */
public class ITFilter implements Serializable {

    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private WorkgroupEntity workgroup  = null;
    private Short           isActive   = null;

    // Filter entity by parent object key
    private Integer         parentKey  = null;

    /**
     * Creates and returns a new ITFilter object with null workgroup and
     * isActive flag.
     */
    public ITFilter() {

    }

    /**
     * Creates and returns a new ITFilter object with the specified workgroup
     * and a null isActive flag.
     *
     * @param workgroup the workgroup to filter by
     */
    public ITFilter( WorkgroupEntity workgroup ) {
        this( workgroup, (Short)null );
    }

    /**
     * Creates and returns a new ITFilter object with the specified workgroup
     * and isActive flag.
     *
     * @param workgroup the workgroup to filter by
     * @param isActive if 1, return only active rows. If 0, return
     *                   only inactive rows. If null, do not filter by isActive.
     */
    public ITFilter( WorkgroupEntity workgroup, Short isActive ) {
        setWorkgroup( workgroup );
        setIsActive( isActive );
    }

    /**
     * Creates and returns a new ITFilter object with the specified workgroup
     * and isActive flag.
     *
     * @param workgroup the workgroup to filter by
     * @param isActiveBoolean if true, return only active rows. If false, return
     *                   only inactive rows. If null, do not filter by isActive.
     */
    public ITFilter( WorkgroupEntity workgroup, Boolean isActiveBoolean ) {
        setWorkgroup( workgroup );
        setIsActive( isActive );
    }

    /**
     * Creates and returns a new ITFilter object with the specified workgroup
     * isActive flag and parent key.
     *
     * @param workgroup the workgroup to filter by
     * @param isActiveBoolean if true, return only active rows. If false, return
     *                   only inactive rows. If null, do not filter by isActive.
     */
    public ITFilter( WorkgroupEntity workgroup, Boolean isActiveBoolean,
                     Integer parentKey)
    {
        setWorkgroup( workgroup );
        setIsActive( isActive );
        setParentKey( parentKey );
    }

    /**
     * Return the isActive filter.
     * @return if 1, only active rows are returned. If 0, only inactive rows
     * are returned. If null, not filtering by isActive.
     */
    public Short getIsActive() {
        return isActive;
    }

    /**
     * Return the isActive filter as a Boolean value.
     * @return if true, only active rows are returned. If false, only inactive
     *         rows are returned. If null, not filtering by isActive.
     */
    public Boolean getIsActiveBoolean() {
        if ( isActive == null ) {
            return null;
        } else if ( isActive == 1 ) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param isActive if 1, return only active rows. If 0, return only inactive
     *                   rows. If null, do not filter by isActive.
     */
    public void setIsActive( Short isActive ) {
        this.isActive = isActive;
    }

    /**
     *
     * @param isActive if 1, return only active rows. If 0, return only inactive
     *                   rows. If null, do not filter by isActive.
     */
    public void setIsActive( Integer isActive ) {
        this.isActive = (isActive == null ? null : isActive.shortValue());
    }

    /**
     *
     * @param isActive if true, return only active rows. If false, return only
     *                 inactive rows. If null, do not filter by isActive.
     */
    public void setIsActive( Boolean isActive ) {
        if ( isActive == null ) {
            this.isActive = null;
        } else if ( isActive ) {
            this.isActive = 1;
        } else {
            this.isActive = 0;
        }
    }
    /**
     * Return the workgroup to filter by.
     * @return the workgroup
     */
    public WorkgroupEntity getWorkgroup() {
        return workgroup;
    }

    /**
     * Set the workgroup filter.
     * @param workgroup the workgroup to filter by
     */
    public void setWorkgroup( WorkgroupEntity workgroup ) {
        this.workgroup = workgroup;
    }

    /**
     * @return the parentKey
     */
    public Integer getParentKey() {
        return parentKey;
    }

    /**
     * @param parentKey the parentKey to set
     */
    public void setParentKey(Integer parentKey) {
        this.parentKey = parentKey;
    }

}
