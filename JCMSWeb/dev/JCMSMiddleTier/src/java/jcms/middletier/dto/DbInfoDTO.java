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

package jcms.middletier.dto;

import jcms.integrationtier.jcmsWeb.DbInfoEntity;


/**
 *
 * @author cnh
 */
public class DbInfoDTO extends DbInfoEntity
{
    private static final long serialVersionUID = 1L;

    public DbInfoDTO (DbInfoEntity entity)
    {
        if (entity != null) {
            this.setDbinfoKey(entity.getDbinfoKey());
            this.setReleaseDate(entity.getReleaseDate());
            this.setReleaseNotes(entity.getReleaseNotes());
            this.setReleaseType(entity.getReleaseType());
            this.setWebReleaseNum(entity.getWebReleaseNum());
        }
    }

    public DbInfoEntity getDbInfoEntity()
    {
        DbInfoEntity entity = new DbInfoEntity();

        entity.setDbinfoKey(this.getDbinfoKey());
        entity.setReleaseDate(this.getReleaseDate());
        entity.setReleaseNotes(this.getReleaseNotes());
        entity.setReleaseType(this.getReleaseType());
        entity.setWebReleaseNum(this.getWebReleaseNum());
        
        return entity;
    }
}
