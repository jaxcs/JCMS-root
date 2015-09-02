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

/*
 * NodeDAO.java
 *
 * Created on August 4, 2010, 10:14 AM
 */

package jcms.integrationtier.dao;

import java.io.BufferedWriter;
import java.sql.SQLException;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;

/**
 * <b>File name:</b>  SearchDAOInterface.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Returns dynamic search results as jobs with cases and 
 *                  jobs without cases.  <p>
 * <b>Overview:</b>  Returns dynamic search results as jobs with cases and 
 *                  jobs without cases.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */        
public interface SearchDAOInterface 
{
    public void testConnection();

    public ResultDTO getMouseQueryResults(MouseQueryDTO query) throws SQLException;

    public void getMouseQueryReport(MouseQueryDTO query, BufferedWriter out) throws SQLException;

    public ResultDTO getMatingQueryResults(MatingQueryDTO query) throws SQLException;

    public void getMatingQueryReport(MatingQueryDTO query, BufferedWriter out) throws SQLException;
}