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

package jcms.integrationtier.dao;

import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author bas
 */
public class cvMatingUnitTypeDAO extends MySQLDAO{
    private String matingUnitTypeKeyQuery = "SELECT _matingUnitType_key FROM cv_MatingUnitType WHERE abbreviation  = ";
    
    private Integer matingUnitTypeKey = 0;
    private String query = null;
    
    public Integer getMatingUnitTypeKey(String abbreviation) {
        query = matingUnitTypeKeyQuery + "'" + abbreviation + "';";
        Result result = this.executeJCMSQuery(query);
        if (result != null){
            
            for (SortedMap row : result.getRows()){
                matingUnitTypeKey = Integer.parseInt(this.myGet("_matingUnitType_key", row));
                //Note there should only be one row in the results
            }
        }
        
        return matingUnitTypeKey;
    }

    /**
     * @return the matingUnitTypeKeyQuery
     */
    public String getMatingUnitTypeKeyQuery() {
        return matingUnitTypeKeyQuery;
    }

    /**
     * @param matingUnitTypeKeyQuery the matingUnitTypeKeyQuery to set
     */
    public void setMatingUnitTypeKeyQuery(String matingUnitTypeKeyQuery) {
        this.matingUnitTypeKeyQuery = matingUnitTypeKeyQuery;
    }

    /**
     * @return the matingUnitTypeKey
     */
    public Integer getMatingUnitTypeKey() {
        return matingUnitTypeKey;
    }

    /**
     * @param matingUnitTypeKey the matingUnitTypeKey to set
     */
    public void setMatingUnitTypeKey(Integer matingUnitTypeKey) {
        this.matingUnitTypeKey = matingUnitTypeKey;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
