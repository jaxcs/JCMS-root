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

package jcms.web.main;

import javax.faces.application.FacesMessage;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.reports.BubbleStrain;
import jcms.web.reports.HistogramBean;
import jcms.integrationtier.dao.DashboardDAO;


/**
 * <b>File name:</b>  MainBean.java  <p>
 * <b>Date developed:</b>  February 2009 <p>
 * <b>Purpose:</b>  Backing bean for MainBean web page.  <p>
 * <b>Overview:</b> Initializes user session object.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b> $Date: 2011-01-19 15:17:04 -0500 (Wed, 19 Jan 2011) $   <p>
 * @author Craig Hanna
 * @version $Revision: 11948 $
 */
public class MainBean extends WTBaseBackingBean {
    // Constructor
    public MainBean()
    {

    }
    
    public void buildDashboardData(){
        BubbleStrain bubbleBean = new BubbleStrain(true);
        HistogramBean histoBean = new HistogramBean();
        bubbleBean.buildBubbleStrainLite();
        histoBean.buildCageHistogramData();
        histoBean.buildMouseHistogramData();
    }
    
    public void updateDashboardData(){
        try{
            new DashboardDAO().updateDashboard();
        }
        catch(Exception e){
            this.addToMessageQueue("Error: " + e, FacesMessage.SEVERITY_ERROR);
        }
    }
}