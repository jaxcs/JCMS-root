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

package jcms.middletier.service;

import jcms.integrationtier.dto.CageCardDTO;
import jcms.middletier.businessobject.CageCardBO;
import javax.servlet.http.HttpServletResponse;
import jcms.cagecard.dtos.returnDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 *
 * @author mkamato
 */
public class CageCardAppService {
    
    public returnDTO generateCageCard(CageCardDTO dto, HttpServletResponse HSR) throws SQLException{
        return new CageCardBO().runQuery(dto, HSR);
    } 
    
    public boolean CageCardValidation(CageCardDTO dto) throws SQLException{
        return new CageCardBO().runValidate(dto);
    }
    
    public Integer saveCageCard(CageCardDTO theCard, String cardName, UserEntity user, ArrayList<Integer> wgKeys) throws Exception {
        return new CageCardBO().saveCageCard(theCard, cardName, user, wgKeys);
    }
    
    public CageCardDTO loadCageCard(Integer key) {
        try {
            return new CageCardBO().loadCageCard(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
