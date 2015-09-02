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

import jcms.integrationtier.base.JCMSWebSystemFacadeLocal;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.jcmsWeb.DbInfoEntity;
import jcms.middletier.dto.DbInfoDTO;
import jcms.middletier.dto.JCMSDbInfoDTO;


/**
 *
 * @author cnh
 */
public class DatabaseBO extends JCMSBO
{
    private JCMSWebSystemFacadeLocal systemFacade = this.getIntegrationTierPortal().
            getJCMSWebSystemFacadeLocal();
    
    public DbInfoDTO getDatabaseInformation()
    {
        DbInfoEntity entity = systemFacade.getDbInfo();
        
        return new DbInfoDTO(entity);
    }

    public JCMSDbInfoDTO getJCMSDatabaseInformation()
    {
        JCMSDbInfoEntity entity = this.getIntegrationTierPortal().
                getSystemFacadeLocal().getJCMSDbInfo();

        return new JCMSDbInfoDTO(entity);
    }
}