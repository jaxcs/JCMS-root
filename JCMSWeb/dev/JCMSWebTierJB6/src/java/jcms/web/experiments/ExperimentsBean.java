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

package jcms.web.experiments;

import java.text.DecimalFormat;
import javax.faces.application.FacesMessage;
import javax.faces.model.ListDataModel;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author bas
 */
public class ExperimentsBean extends WTBaseBackingBean {
    private SelectItemWrapper           selectItemWrapper       = null;
    private ListDataModel               experimentDataModel     = null;
    
    protected final String ROWINDEX = "rowIndex";
    protected final String PRIMARYKEY = "primaryKey";
    
    private String PARAMROWINDEX = "paramRowIndex";
    private String PARAMPRIMARYKEY = "paramPrimaryKey";
    private String PARAMDATAID = "paramDataID";
    
    public ExperimentsBean() {
        this.setSelectItemWrapper(new SelectItemWrapper());
    }
    
    protected String getParamPrimaryKey() {
        return this.getRequestParameter(this.PARAMPRIMARYKEY).toString();
    }
    
    protected String getParamDataID() {
        return this.getRequestParameter(this.PARAMDATAID).toString();
    }
        
    protected String getParamRowIndex() {
        return this.getRequestParameter(this.PARAMROWINDEX).toString();
    }

    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }
    
     /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }
    
     /**
     * clear the variables in session
     */
    protected void clearSessionVariables() {
        this.putSessionParameter(this.ROWINDEX, "");
        this.putSessionParameter(this.PRIMARYKEY, "0");
    }
    
    /**
     * set the variables in session
     */
    protected void setSessionVariables() {
        String primaryKey = getParamPrimaryKey();
        this.putSessionParameter("rowIndex", getParamRowIndex());
        if (primaryKey.length() > 0) {
            this.putSessionParameter("primaryKey", primaryKey);
        }
        else {
            this.putSessionParameter("primaryKey", 0);
        }
    }

    /**
     * @return the experimentDataModel
     */
    public ListDataModel getExperimentDataModel() {
        return experimentDataModel;
    }

    /**
     * @param experimentDataModel the experimentDataModel to set
     */
    public void setExperimentDataModel(ListDataModel experimentDataModel) {
        this.experimentDataModel = experimentDataModel;
    }

    public String convertMonthsToDays (String age) {
        String returnAge = "0";
        double dblAge = 0.0;
        dblAge = Double.parseDouble(age);
        dblAge = roundTwoDecimals(dblAge * 30.4375);
        returnAge = Double.toString(dblAge);
        return returnAge;
    }
    
    public String convertWeeksToDays (String age) {
        String returnAge = "0";
        double dblAge = 0.0;
        dblAge = Double.parseDouble(age);
        dblAge = roundOneDecimal(dblAge * 7);
        returnAge = Double.toString(dblAge);
        return returnAge;
    }
    
    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    private double roundOneDecimal(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }
    
    public boolean minCheck(Float theValue, Float minValue, String captionForMsg){
        boolean success = true;
        if (theValue < minValue) {
            success = false;
            captionForMsg = captionForMsg + ": " + theValue + " is less than the minimum allowed.";
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, captionForMsg, null));
            this.getLogger().logWarn(this.formatLogMessage("Validation ",captionForMsg));
        }                                    
        return success;
    }
    
    public boolean maxCheck(Float theValue, Float maxValue, String captionForMsg){
        boolean success = true;
        if (theValue > maxValue) {
            success = false;
            captionForMsg = captionForMsg + ": " + theValue + " is greater than the maximum allowed.";
            this.getFacesContext().addMessage("expDataEditDataMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, captionForMsg, null));
            this.getLogger().logWarn(this.formatLogMessage("Validation ",captionForMsg));
        }                                    
        return success;
    }
}
