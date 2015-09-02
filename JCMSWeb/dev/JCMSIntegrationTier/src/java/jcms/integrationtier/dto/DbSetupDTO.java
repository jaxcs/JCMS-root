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

import java.util.SortedMap;
import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author cnh
 */
public class DbSetupDTO extends ITBaseDTO {
    private String dbSetupKey = "0";
    private String MTSVar;
    private String MTSValue;
    private Boolean blnMTSValue = null;
    private String MTSDescription;
    private String version = "0";
    private String MTSValueOriginal;
    
    public DbSetupDTO() {

    }

    /**
     * @return the dbSetupKey
     */
    public String getDbSetupKey() {
        return dbSetupKey;
    }

    /**
     * @param dbSetupKey the dbSetupKey to set
     */
    public void setDbSetupKey(String dbSetupKey) {
        this.dbSetupKey = dbSetupKey;
    }

    /**
     * @return the MTSVar
     */
    public String getMTSVar() {
        return MTSVar;
    }

    /**
     * @param MTSVar the MTSVar to set
     */
    public void setMTSVar(String MTSVar) {
        this.MTSVar = MTSVar;
    }

    /**
     * @return the MTSValue
     */
    public String getMTSValue() {
        return MTSValue;
    }

    /**
     * @param MTSValue the MTSValue to set
     */
    public void setMTSValue(String MTSValue) {
        this.MTSValue = MTSValue;
    }

    /**
     * @return the blnMTSValue
     */
    public Boolean getBlnMTSValue() {
        return blnMTSValue;
    }

    /**
     * @param blnMTSValue the blnMTSValue to set
     */
    public void setBlnMTSValue(Boolean blnMTSValue) {
        if (blnMTSValue != null && blnMTSValue.toString().length() > 0) {
            this.setMTSValue(blnMTSValue.toString());
        }
        this.blnMTSValue = blnMTSValue;
    }

    /**
     * @return the MTSDescription
     */
    public String getMTSDescription() {
        return MTSDescription;
    }

    /**
     * @param MTSDescription the MTSDescription to set
     */
    public void setMTSDescription(String MTSDescription) {
        this.MTSDescription = MTSDescription;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the MTSValueOriginal
     */
    public String getMTSValueOriginal() {
        return MTSValueOriginal;
    }

    /**
     * @param MTSValueOriginal the MTSValueOriginal to set
     */
    public void setMTSValueOriginal(String MTSValueOriginal) {
        this.MTSValueOriginal = MTSValueOriginal;
    }

    
}
