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

package org.jax.cs.jcmsWebInstaller;

/**
 *
 * @author cnh
 */
public class ResultDTO {
    private Boolean blnSuccess = true;
    private String strJavaRuntimeVersion = "";
    private String strJavaRuntimeVersionWhole = "";
    private String strJavaRuntimeVersionPart = "";
    private String strMessage = "";
    private Severity severity = Severity.INFO;
    private Integer exitValue = 0;
    /**
     * Exit Values
     * 0 Success
     * 1 
     * 2 File Not Found, File Create Failed
     */

    /**
     * @return the strJavaRuntimeVersionWhole
     */
    public String getStrJavaRuntimeVersionWhole() {
        return strJavaRuntimeVersionWhole;
    }

    /**
     * @param strJavaRuntimeVersionWhole the strJavaRuntimeVersionWhole to set
     */
    public void setStrJavaRuntimeVersionWhole(String strJavaRuntimeVersionWhole) {
        this.strJavaRuntimeVersionWhole = strJavaRuntimeVersionWhole;
    }

    /**
     * @return the strJavaRuntimeVersionPart
     */
    public String getStrJavaRuntimeVersionPart() {
        return strJavaRuntimeVersionPart;
    }

    /**
     * @param strJavaRuntimeVersionPart the strJavaRuntimeVersionPart to set
     */
    public void setStrJavaRuntimeVersionPart(String strJavaRuntimeVersionPart) {
        this.strJavaRuntimeVersionPart = strJavaRuntimeVersionPart;
    }

    /**
     * @return the strMessage
     */
    public String getStrMessage() {
        return strMessage;
    }

    /**
     * @param strMessage the strMessage to set
     */
    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    /**
     * @return the blnSuccess
     */
    public Boolean getBlnSuccess() {
        return blnSuccess;
    }

    /**
     * @param blnSuccess the blnSuccess to set
     */
    public void setBlnSuccess(Boolean blnSuccess) {
        this.blnSuccess = blnSuccess;
    }

    /**
     * @return the strJavaRuntimeVersion
     */
    public String getStrJavaRuntimeVersion() {
        return strJavaRuntimeVersion;
    }

    /**
     * @param strJavaRuntimeVersion the strJavaRuntimeVersion to set
     */
    public void setStrJavaRuntimeVersion(String strJavaRuntimeVersion) {
        this.strJavaRuntimeVersion = strJavaRuntimeVersion;
    }

    /**
     * @return the severity
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * @return the exitValue
     */
    public Integer getExitValue() {
        return exitValue;
    }

    /**
     * @param exitValue the exitValue to set
     */
    public void setExitValue(Integer exitValue) {
        this.exitValue = exitValue;
    }
    
}
