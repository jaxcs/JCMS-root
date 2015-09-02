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

import jcms.integrationtier.dao.PhenotypeMouseLinkDAO;
import jcms.integrationtier.dao.PhenotypeTermDAO;

/**
 *
 * @author bas
 */
public class PhenotypeUtilities {
    
    private PhenotypeMouseLinkDAO pmlDAO = new PhenotypeMouseLinkDAO();
    private PhenotypeTermDAO ptDAO = new PhenotypeTermDAO();
       
    /**
     * @return the pmlDAO
     */
    public PhenotypeMouseLinkDAO getpmlDAO() {
        return pmlDAO;
    }

    /**
     * @param pmlDAO the pmlDAO to set
     */
    public void setpmlDAO(PhenotypeMouseLinkDAO pmlDAO) {
        this.pmlDAO = pmlDAO;
    }

    /**
     * @return the ptDAO
     */
    public PhenotypeTermDAO getptDAO() {
        return ptDAO;
    }

    /**
     * @param ptDAO the ptDAO to set
     */
    public void setptDAO(PhenotypeTermDAO ptDAO) {
        this.ptDAO = ptDAO;
    }
}
