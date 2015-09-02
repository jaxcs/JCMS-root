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
 * HierarchicalPath.java
 *
 * Created on August 24, 2010, 12:10 PM
 *
 */

package jcms.integrationtier.dao;

import java.io.BufferedWriter;
import java.sql.SQLException;
import javax.servlet.jsp.jstl.sql.Result;
import jcms.integrationtier.dto.MatingQueryDTO;
import jcms.integrationtier.dto.MouseQueryDTO;
import jcms.integrationtier.dto.ResultDTO;
import jcms.integrationtier.exception.EntityNotFoundException;
import jcms.integrationtier.exception.FindEntityException;

/**
 * <b>File name:</b>  HierarchicalPath.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Provides a way to generate a hierarchical path of DAG Terms
 *      given a specific DAG and text to search for.  <p>
 * <b>Overview:</b>  
 *      Provides a specific implementation to generate all the hierarchical paths
 *      for a wild card search of terms given a specific DAG and searchable text. 
 *      This extraction process is a recursive routine that determines the ancestrial
 *      path of a selected node.  This is unlike other crisp implementations of 
 *      recursion.  This recursion must deal with a return path of more than one.
 *      Because their can be more than one ancestor path, each ancestor node when 
 *      found is immediately added to a class level variable capturing the complete 
 *      hierarchical path of each originating node.
 *      <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */     
public class SearchJDBC
{
    private DAOFactory          jcmsFactory   = null;
    private SearchDAOInterface  jcmsSearch    = null;
    
    /** Creates a new instance of HierarchicalPath */
    public SearchJDBC ()
    {
        // Performance:  Instantiate once, use throughout class.
        this.createFactories ();
    }

    /**
     *  <b>Purpose:</b>  Prevent multiple database connections by opening  
     *      one interface for the class to share.  
     *      <br>
     *  <b>Overview:</b>  Prevent multiple database connections by opening  
     *      one interface for the class to share.  All public methods who use 
     *      common DAO's check to ensure the DAO's are instantiated.
     *      <br>
     * @return Collection<NodeEntity>  Return a list of <code>NodeEntity</code> entity.
     * @throws EntityNotFoundException  Unable to find database record exception.
     * @throws FindEntityException  Unable to find database record exception.
     */
    private void createFactories() {
        // Create desired DAO Factory.
        if (jcmsFactory == null)
            jcmsFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

        // Create desired DAO.  DAO automatically creates a connection.
        if (jcmsSearch == null) jcmsSearch = jcmsFactory.getSearchDAO();
    }

    /**
     *  <b>Purpose:</b>  Searches for jobs based on the search criteria provided. <br>
     *  <b>Overview:</b>  Searches for jobs based on the search criteria provided. 
     *                    Returns two separate lists one list contains all jobs 
     *                    meeting the search criteria with cases.  The other list 
     *                    contains jobs meeting the search criteria without cases.  <br>
     * @param stSearchTokens  Contains search criteria.
     * @return DynamicSearchDTO;  Return search result object <code>DynamicSearchDTO</code> entity.
     */
    public void testConnection() {
        jcmsSearch.testConnection();        
    }    

    public ResultDTO getMouseQueryResults(MouseQueryDTO query) throws SQLException {
        return jcmsSearch.getMouseQueryResults(query);
    }

    public void getMouseQueryReport(MouseQueryDTO query, BufferedWriter out) throws SQLException {
        jcmsSearch.getMouseQueryReport(query, out);
    }

    public ResultDTO getMatingQueryResults(MatingQueryDTO query) throws SQLException {
        return jcmsSearch.getMatingQueryResults(query);
    }

    public void getMatingQueryReport(MatingQueryDTO query, BufferedWriter out) throws SQLException {
        jcmsSearch.getMatingQueryReport(query, out);
    }
}