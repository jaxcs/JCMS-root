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

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author rkavitha
 */
public class DateIterator implements Iterator, Iterable {

    private Calendar end = Calendar.getInstance();
    private Calendar current = Calendar.getInstance();

    public DateIterator(Date start, Date end) {
        this.end.setTime(end);
        this.end.add(Calendar.DATE, -1);
        this.current.setTime(start);
        this.current.add(Calendar.DATE, -1);
    }

    public boolean hasNext() {
        // return !current.after(end);
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b = false;
        b1 = current.get(Calendar.YEAR) > end.get(Calendar.YEAR);
        b2 = current.get(Calendar.YEAR) == end.get(Calendar.YEAR);
        b3 = current.get(Calendar.DAY_OF_YEAR) > end.get(Calendar.DAY_OF_YEAR);
        b = b1 || (b2 && b3);
        return !b;
    }

    public Date next() {
        current.add(Calendar.DATE, 1);
        return current.getTime();
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove");
    }

    public Iterator iterator() {
        return this;
    }
}