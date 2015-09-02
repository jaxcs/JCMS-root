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

import java.io.Serializable;
import jcms.integrationtier.cv.CvTimeUnitEntity;
import jcms.integrationtier.dto.DeleteDTO;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.service.RepositoryService;

/**
 * <b>File name:</b> SpecialHandling.java  <p>
 * <b>Date developed:</b> December 2009 <p>
 * <b>Purpose:</b> Backing bean contains useful specialized business level
 *      attributes for user interface processing.  <br />
 * <b>Overview:</b> This backing bean contains business level attributes that
 *      may be useful to all JSPs.  Here they are contained in one location
 *      instead of being reproduced in all primary backing beans.  This also
 *      allows a single object to be added to the HTTP Request stack instead a
 *      myriad of properties.  <br />
 *      Backing Bean conforms to Java Bean requirements.  <p>
 * <b>Last changed by:</b>   $Author: daves $ <p>
 * <b>Last changed date:</b> $Date: 2011-03-09 18:07:06 -0500 (Wed, 09 Mar 2011) $   <p>
 * @author Craig Hanna
 * @version $Revision: 12442 $
 */
public class SpecialHandling extends WTBaseBackingBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String ANCHOR_TOP           = "#TOP";
    public static final String ANCHOR_BOTTOM        = "#BOTTOM";
    public static final String ANCHOR_NONE          = "";

    public static final String ANCHOR_CONTACTS      = "#CONTACTS";
    public static final String ANCHOR_DEPARTMENTS   = "#DEPARTMENTS";
    public static final String ANCHOR_INPUTS        = "#INPUTS";
    public static final String ANCHOR_LOCATIONS     = "#LOCATIONS";
    public static final String ANCHOR_LINES         = "#LINES";
    public static final String ANCHOR_RESOURCES     = "#RESOURCES";
    public static final String ANCHOR_OUTPUTS       = "#OUTPUTS";
    public static final String ANCHOR_PROCEDURES    = "#PROCEDURES";

    private String      anchor              = SpecialHandling.ANCHOR_TOP;

    // This is an internal property referenced by the JSP to determine whether
    // or not to render the java script command to jump to an anchor.
    private Boolean     isAnchor            = Boolean.FALSE;

    private CvTimeUnitEntity    defaultTimeUnit = null;

    // Simple field to indicate whether or not a new version of the current
    // record should be created.  The return action would simply clear the
    // primary key prior to saving.  The EJB container would automatically
    // create a new record.  And link back to parent where necessary.
    private Boolean     isNewVersion        = Boolean.FALSE;

    // Data transport object for all entities that need to be removed from 
    // the database.  Integration Tier will check for dependencies.  When
    // a dependency is found the record is deactivated.
    private DeleteDTO   deleteDTO           = null;

    private Boolean     showProperties      = Boolean.FALSE;

    private Integer     outputKeySelected   = null;

    /**
     * @return the anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * @param anchor the anchor to set
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * @return the isNewVersion
     */
    public Boolean getIsNewVersion() {
        return isNewVersion;
    }

    /**
     * @param isNewVersion the isNewVersion to set
     */
    public void setIsNewVersion(Boolean isNewVersion) {
        this.isNewVersion = isNewVersion;
    }

    /**
     * @return the deleteDTO
     */
    public DeleteDTO getDeleteDTO()
    {
        // Only create object if the interface needs it.
        if (deleteDTO == null)
            deleteDTO = new DeleteDTO();

        return deleteDTO;
    }

    /**
     * @param deleteDTO the deleteDTO to set
     */
    public void setDeleteDTO(DeleteDTO deleteDTO) {
        this.deleteDTO = deleteDTO;
    }

    public void goBOTTOM()
    {
        setAnchor(SpecialHandling.ANCHOR_BOTTOM);
    }

    public void goINPUTS()
    {
        setAnchor(SpecialHandling.ANCHOR_INPUTS);
    }

    public void goLOCATIONS()
    {
        setAnchor(SpecialHandling.ANCHOR_LOCATIONS);
    }

    public void goLINES()
    {
        setAnchor(SpecialHandling.ANCHOR_LINES);
    }

    public void goNONE()
    {
        setAnchor(SpecialHandling.ANCHOR_NONE);
    }

    public void goRESOURCES()
    {
        setAnchor(SpecialHandling.ANCHOR_RESOURCES);
    }

    public void goOUTPUTS()
    {
        setAnchor(SpecialHandling.ANCHOR_OUTPUTS);
    }

    public void goPROCEDURES()
    {
        setAnchor(SpecialHandling.ANCHOR_PROCEDURES);
    }

    public void goTOP()
    {
        setAnchor(SpecialHandling.ANCHOR_TOP);
    }

    /**
     * @return the showProperties
     */
    public Boolean getShowProperties() {
        return showProperties;
    }

    /**
     * @param showProperties the showProperties to set
     */
    public void setShowProperties(Boolean showProperties) {
        this.showProperties = showProperties;
    }

    /**
     *
     */
    public void toggleShowProperties()
    {
        if (this.showProperties)
            this.setShowProperties(Boolean.FALSE);
        else
            this.setShowProperties(Boolean.TRUE);

        this.getLogger().logInfo(this.formatLogMessage("Show is " + this.showProperties, "toggleShowProperties"));
    }

    /**
     * @return the isAnchor
     */
    public Boolean getIsAnchor() 
    {
        if (this.anchor.length() > 0)
            this.isAnchor = Boolean.TRUE;
        else
            this.isAnchor = Boolean.FALSE;
        
        return isAnchor;
    }

    /**
     * @param isAnchor the isAnchor to set
     */
    public void setIsAnchor(Boolean isAnchor) {
        this.isAnchor = isAnchor;
    }

    /**
     * @return the defaultTimeUnit
     */
    public CvTimeUnitEntity getDefaultTimeUnit() 
    {
        if (defaultTimeUnit == null)
            defaultTimeUnit = (CvTimeUnitEntity) new RepositoryService().baseFindDefaultEntity(new CvTimeUnitEntity());
        return defaultTimeUnit;
    }

    /**
     * @param defaultTimeUnit the defaultTimeUnit to set
     */
    public void setDefaultTimeUnit(CvTimeUnitEntity defaultTimeUnit) {
        this.defaultTimeUnit = defaultTimeUnit;
    }

    /**
     * @return the outputKeySelected
     */
    public Integer getOutputKeySelected() {
        return outputKeySelected;
    }

    /**
     * @param outputKeySelected the outputKeySelected to set
     */
    public void setOutputKeySelected(Integer outputKeySelected)
    {
        this.outputKeySelected = outputKeySelected;
    }
}