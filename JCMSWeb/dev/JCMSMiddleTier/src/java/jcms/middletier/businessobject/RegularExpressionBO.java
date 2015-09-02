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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author cnh
 */
public class RegularExpressionBO extends JCMSBO
{
    private Matcher matcher = null;

    public Boolean isValidExpression(String regularExpression, String value)
    {
        Pattern pattern = Pattern.compile(regularExpression);
        matcher = pattern.matcher(value);

        Boolean found = Boolean.FALSE;
        if (matcher.find())
        {
            found = Boolean.TRUE;

        }

        return found;
    }

    public Matcher getMatcher()
    {
        return matcher;
    }

}
