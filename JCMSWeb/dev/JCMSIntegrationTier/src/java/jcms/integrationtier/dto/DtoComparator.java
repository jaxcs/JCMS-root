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

import java.util.Comparator;

/**
 *
 * @author cnh
 */
public class DtoComparator implements Comparator<Object> {

    private String getter;

    public DtoComparator(String field) {
        this.getter = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            if (o1 != null && o2 != null) {
                o1 = o1.getClass().getMethod(getter, new Class[0]).invoke(o1, new Object[0]);
                o2 = o2.getClass().getMethod(getter, new Class[0]).invoke(o2, new Object[0]);
            }
        } catch (Exception e) {
            // If this exception occurs, then it is usually a fault of the developer.
            throw new RuntimeException("Cannot compare " + o1 + " with " + o2 + " on " + getter, e);
        }

        return (o1 == null) ? -1 : ((o2 == null) ? 1 : ((Comparable<Object>) o1).compareTo(o2));
    }

}    

