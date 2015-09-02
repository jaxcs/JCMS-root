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

package jcms.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.middletier.facade.ReportFacadeLocal;
import jcms.middletier.portal.BusinessTierPortal;
import jcms.web.base.WTBaseBackingBean;

/**
 *
 * @author rkavitha
 */
public class LitterGenerationValidator extends WTBaseBackingBean implements 
        Validator {

    protected FacesMessage buildErrorMessage(String errorMessage) {
        FacesMessage returnMessage = new FacesMessage();
        returnMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        returnMessage.setSummary(errorMessage);
        return returnMessage;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        // Check for null or empty String.
        String errMsg = null;
        String s = "";
        CvGenerationEntity generationEntity = new CvGenerationEntity();
        
        System.out.println("validate generation");
        generationEntity = (CvGenerationEntity)value;
        
        if (generationEntity != null) {
            s = generationEntity.getGeneration();
        }
        System.out.println("generation " + s);
        
        if ((s == null) || (s.isEmpty())) {
            errMsg = "Generation is missing. Please select a Generation.";
            throw new ValidatorException(buildErrorMessage(errMsg));
        }
        try {
            LitterEntity litterEntity = new LitterEntity();
            Integer litterKey = Integer.parseInt(this.getRequestParameter("paramLitterKey"));
            System.out.println("litter key " + litterKey);

            ReportFacadeLocal rfl = new BusinessTierPortal().getReportFacadeLocal();
            String generation = rfl.findLitterGeneration(litterKey);
            System.out.println("litter generation " + generation + " and selected generation " + s);

            if (generation != null && !generation.equals("") && !generation.equalsIgnoreCase(s)) {
                errMsg = "Mouse ID already exists. Please choose a unique Mouse ID.";
                throw new ValidatorException(buildErrorMessage(errMsg));
            }
        } catch (Exception e) {
        }
    }
}
