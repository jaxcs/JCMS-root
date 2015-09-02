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

package jcms.middletier.dto.filter;

import jcms.middletier.base.BTBaseDTOInterface;


/**
 * <b>File name:</b>  FilterDTO.java  <p>
 * <b>RsDate developed:</b>  October 2009 <p>
 * <b>Purpose:</b>    <p>
 * <b>Inputs:</b>     <p>
 * <b>Outputs:</b>    <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Craig Hanna
 * @version $Revision$
 */
public class FilterDTO implements BTBaseDTOInterface
{
    private String   label      = null;     // UI label
    private String   name       = null;     // UI SelectItem name
    private String   filter     = "";       // User entered filter criterion.

    // Sort by the field this class represents or not.
    private Boolean  sortBy     = Boolean.FALSE;

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
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
     * @return the fitlerBy
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param fitlerBy the fitlerBy to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the sortBy
     */
    public Boolean getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(Boolean sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println(this.getClass().getSimpleName() + " Contents");
        System.out.println("================================");
        System.out.println("\tLabel" + "\t" + this.getLabel() );
        System.out.println("\tName" + "\t" + this.getName() );
        System.out.println("\tFilter" + "\t" + this.getFilter() );
        System.out.println("\tSortBy" + "\t" + this.getSortBy() );
        System.out.println("\n");
    }

}
