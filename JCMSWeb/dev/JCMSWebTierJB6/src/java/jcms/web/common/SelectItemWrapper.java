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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.model.SelectItem;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.LifeStatusEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.RoomEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import jcms.integrationtier.cv.CvBreedingStatusEntity;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvCoatColorEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;
import jcms.integrationtier.cv.CvCrossstatusEntity;
import jcms.integrationtier.cv.CvDietEntity;
import jcms.integrationtier.cv.CvEpochEntity;
import jcms.integrationtier.cv.CvExpSampleLocationEntity;
import jcms.integrationtier.cv.CvExpStatusEntity;
import jcms.integrationtier.cv.CvFieldOfStudyEntity;
import jcms.integrationtier.cv.CvGeneclassEntity;
import jcms.integrationtier.cv.CvGenerationEntity;
import jcms.integrationtier.cv.CvGenotypeSpecimenLocationEntity;
import jcms.integrationtier.cv.CvHarvestMethodEntity;
import jcms.integrationtier.cv.CvHealthLevelEntity;
import jcms.integrationtier.cv.CvKeywordsEntity;
import jcms.integrationtier.cv.CvLocationTypeEntity;
import jcms.integrationtier.cv.CvMatingCardNotesEntity;
import jcms.integrationtier.cv.CvMouseOriginEntity;
import jcms.integrationtier.cv.CvMouseProtocolEntity;
import jcms.integrationtier.cv.CvMouseUseEntity;
import jcms.integrationtier.cv.CvPhenotypeEntity;
import jcms.integrationtier.cv.CvPreservationDetailEntity;
import jcms.integrationtier.cv.CvPreservationMethodEntity;
import jcms.integrationtier.cv.CvPreservationTypeEntity;
import jcms.integrationtier.cv.CvSampleClassEntity;
import jcms.integrationtier.cv.CvSampleDateTypeEntity;
import jcms.integrationtier.cv.CvSampleStatusEntity;
import jcms.integrationtier.cv.CvSampleTypeEntity;
import jcms.integrationtier.cv.CvSexEntity;
import jcms.integrationtier.cv.CvStorageFacilityEntity;
import jcms.integrationtier.cv.CvStrainTypeEntity;
import jcms.integrationtier.cv.CvTestStatusEntity;
import jcms.integrationtier.cv.CvTimeUnitEntity;
import jcms.integrationtier.cv.CvWeightUnitEntity;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.web.base.WTBaseObject;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;

import jcms.integrationtier.dao.CVAdministrationDAO;
import jcms.integrationtier.dao.CageCardDAO;
import jcms.integrationtier.dao.ExpDataDAO;
import jcms.integrationtier.dao.MouseUseDAO;
import jcms.integrationtier.dao.PhenotypeTermDAO;
import jcms.integrationtier.dao.StrainDAO;
import jcms.integrationtier.dao.UseScheduleDAO;
import jcms.integrationtier.dao.cvPreferencesDAO;
import jcms.integrationtier.dto.AdminGeneDTO;
import jcms.integrationtier.dto.AdminGenerationDTO;
import jcms.integrationtier.dto.AdminStrainDTO;
import jcms.integrationtier.dto.AdminStrainStatusDTO;
import jcms.integrationtier.dto.AdminStrainTypeDTO;
import jcms.integrationtier.dto.ExpDataDTO;
import jcms.integrationtier.dto.ExpDataDescriptorDTO;
import jcms.integrationtier.dto.MouseUseDTO;
import jcms.integrationtier.dto.QueryDefinitionDTO;
import jcms.integrationtier.dto.QueryTypeDTO;
import jcms.integrationtier.dto.UseScheduleTermDTO;
import jcms.integrationtier.dto.cvMouseUseDTO;
import jcms.integrationtier.dto.PhenotypeTermDTO;
import jcms.integrationtier.dto.UseScheduleStartEventDTO;
import jcms.integrationtier.dto.cvPhenotypeTermDTO;
import jcms.integrationtier.jcmsWeb.UserEntity;

/**
 * <b>File name:</b> SelectItemWrapper.java  <p>
 * <b>Date developed:</b> July 2010 <p>
 * <b>Purpose:</b> A class that provides a reference to lists of controlled
 * vocabulary of type JSF component List<SelectItem>.  <br />
 * <b>Last changed by:</b>   $Author: cnh $ <p>
 * <b>Last changed date:</b> $Date: 2013-01-07 09:45:24 -0500 (Mon, 07 Jan 2013) $   <p>
 * <b>Last changed by: </b>  $Author: bas $ <p>
 * <b>Last changed date:</b> $Date: 2014-05-30 (Fri, May 30 2014) $ <p>
 * @author Kavitha Rama
 * @version $Revision: 19142 $
 */
public class SelectItemWrapper extends WTBaseObject
{
    private ListSupportDTO listSupportDTO;

    private List<SelectItem>  cvCauseOfDeathItems             = null;
    private List<SelectItem>  cvCoatColorItems                = null;
    private List<SelectItem>  cvContainerStatusItems          = null;
    private List<SelectItem>  cvDietItems                     = null;
    private List<SelectItem>  cvEpochItems                    = null;
    private List<SelectItem>  cvExpSampleLocationItems        = null;
    private List<SelectItem>  cvExpStatusItems                = null;
    private List<SelectItem>  cvFieldOfStudyItems             = null;
    private List<SelectItem>  cvGeneclassItems                = null;
    private List<SelectItem>  cvGeneClassStringItemsOptional  = null;
    private List<SelectItem>  cvGenerationItems               = null;
    private List<SelectItem>  cvGenotypeSpecimenLocationItems = null;
    private List<SelectItem>  cvHarvestMethodItems            = null;
    private List<SelectItem>  cvHealthLevelItems              = null;
    private List<SelectItem>  cvKeywordsItems                 = null;
    private List<SelectItem>  cvLocationTypeItems             = null;
    private List<SelectItem>  cvMatingCardNotesItems          = null;
    private List<SelectItem>  cvMouseOriginItems              = null;
    private List<SelectItem>  cvMouseProtocolItems            = null;
    private List<SelectItem>  cvMouseProtocolItemsOptional    = null;
    private List<SelectItem>  cvMouseUseItems                 = null;
    private List<SelectItem>  cvMouseUseItemsStringKey        = null;
    private List<SelectItem>  cvMouseUseItemsPairOptional     = null;
    private List<SelectItem>  cvMouseUseActiveItems           = null;
    private List<SelectItem>  cvPhenotypeItems                = null;
    private List<SelectItem>  cvPhenotypeItemsStringKey       = null;
    private List<SelectItem>  cvPhenotypeItemsPairOptional    = null;
    private List<SelectItem>  cvPhenotypeActiveItems          = null;
    private List<SelectItem>  cvPreservationDetailItems       = null;
    private List<SelectItem>  cvPreservationMethodItems       = null;
    private List<SelectItem>  cvPreservationTypeItems         = null;
    private List<SelectItem>  cvSampleClassItems              = null;
    private List<SelectItem>  cvSampleDateTypeItems           = null;
    private List<SelectItem>  cvSampleStatusItems             = null;
    private List<SelectItem>  cvSampleTypeItems               = null;
    private List<SelectItem>  cvStorageFacilityItems          = null;
    private List<SelectItem>  cvStrainTypeItems               = null;
    private List<SelectItem>  cvTestStatusItems               = null;
    private List<SelectItem>  cvTimeUnitItems                 = null;
    private List<SelectItem>  cvWeightUnitItems               = null;

    private List<SelectItem>  alleleItems                           = null;
    private List<SelectItem>  alleleItemsOptional                   = null;
    private List<SelectItem>  cvGenerationStringItemsOptional       = null;
    private List<SelectItem>  cvHealthLevelStringItemsOptional      = null;
    private List<SelectItem>  allStrains                            = null;
    private List<SelectItem>  strainItemsOptional                   = null;
    private List<SelectItem>  activeStrainsOptional                 = null;
    private List<SelectItem>  cvStrainStatusStringItemsOptional     = null;
    private List<SelectItem>  cvStrainTypeStringItemsOptional       = null;
    
    private List<SelectItem>  activeItems                     = null;
    private List<SelectItem>  amPmItems                       = null;
    private List<SelectItem>  timeFrameItems                  = null;
    private List<SelectItem>  sexItems                        = null;
    private List<SelectItem>  stringStatuses                  = null;
    private List<SelectItem>  stringRooms                     = null;
    private List<SelectItem>  stringCvDietItems               = null;
    private List<SelectItem>  activeBirthdateCalendarUseScheduleItems    = null;
    private List<SelectItem>  activeUseScheduleItems            = null;
    private List<SelectItem>  activePlugdateUseScheduleItems    = null;
    
    private List<SelectItem>  cvCageCardQueryTypesOptional    = null;
    private List<SelectItem>  queryDefinitionsOptional        = null;
    
    private List<SelectItem>  testTypeFieldFormats = null;
    
    private int damStrainKey = 0;
    private int sireStrainKey = 0;
    private int geneKey = 0;
    public UserEntity userEntity = null;
    
    private String ownerLogged = "";
    private ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
    private CVAdministrationDAO adminDAO = new CVAdministrationDAO();
    private MouseUseDAO muDAO = new MouseUseDAO();
    private PhenotypeTermDAO ptDAO = new PhenotypeTermDAO();
    private CageCardDAO ccDAO = new CageCardDAO();
    private UseScheduleDAO pDAO = new UseScheduleDAO();
    private ExpDataDAO expDataDAO = new ExpDataDAO();
    private String horizontalTab = "&#09;";
    
    /**
     * <b>Purpose:</b> Constructor loads procedure vocabulary - lookup data <br />
     */
    public SelectItemWrapper()
    {      

        ownerLst = (ArrayList<OwnerEntity>)getSessionParameter("guestOwnerEntityLst");

        listSupportDTO = new ListSupportDTO(ownerLst);
        
        if (getSessionParameter("damStrainKey") != null) {
            this.damStrainKey = (Integer)getSessionParameter("damStrainKey");
        }
        
        if (getSessionParameter("sireStrainKey") != null) {
            this.sireStrainKey = (Integer)getSessionParameter("sireStrainKey");
        }
        
        if (getSessionParameter("geneKey") != null) {
            this.geneKey = (Integer)getSessionParameter("geneKey");
        }
        
        if (getSessionParameter("userEntity") != null) {
            this.userEntity = (UserEntity)getSessionParameter("userEntity");
        }
    }
    
    /*public SelectItemWrapper(int damKey, int sireKey) {
        this.damKey = damKey;
        this.sireKey = sireKey;
    }*/

    private SelectItem getBlankItem()
    {
        return new SelectItem("", " ");
    }

    /**
     * @return the listSupportDTO
     */
    public ListSupportDTO getListSupportDTO() {
        return listSupportDTO;
    }

    /**
     * <b>Purpose:</b> Loads all CauseOfDeath controlled vocabulary for
     *      populating JSF SelectItem components. <br />     *
     * @return the cvCauseOfDeath of type List<SelectItem>
     */
    public List<SelectItem> getCvCauseOfDeathItems() {
        if (cvCauseOfDeathItems == null)
        {
            cvCauseOfDeathItems = new ArrayList<SelectItem>();

            for (CvCauseOfDeathEntity entity: getListSupportDTO().getCvCauseOfDeath())
            {
                cvCauseOfDeathItems.add(new SelectItem(entity, entity.getCod()));
            }
        }
        return cvCauseOfDeathItems;
    }
    
    /**
     * <b>Purpose:</b> Loads all CauseOfDeath controlled vocabulary for
     *      populating JSF SelectItem components. <br />     *
     * @return the cvCauseOfDeath of type List<SelectItem>
     */
    public List<SelectItem> getCvCauseOfDeathItemsOptional() {
        if (cvCauseOfDeathItems == null)
        {
            cvCauseOfDeathItems = new ArrayList<SelectItem>();
            
            cvCauseOfDeathItems.add(new SelectItem("", ""));

            for (CvCauseOfDeathEntity entity: getListSupportDTO().getCvCauseOfDeath())
            {
                cvCauseOfDeathItems.add(new SelectItem(entity, entity.getCod()));
            }
        }
        return cvCauseOfDeathItems;
    }

    /**
     * <b>Purpose:</b> Loads all CoatColor controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvCoatColor of type List<SelectItem>
     */    
    public List<SelectItem> getCvCoatColorItems() {
        String desc = "";
        if (cvCoatColorItems == null)
        {
            cvCoatColorItems = new ArrayList<SelectItem>();
            
            cvCoatColorItems.add(new SelectItem("", ""));

            for (CvCoatColorEntity entity: getListSupportDTO().getCvCoatColor())
            {
                if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                    desc = entity.getDescription();
                }            
                cvCoatColorItems.add(new SelectItem(entity, entity.getCoatColor() + " " + 
                    ":" + " " + desc));
            }
        }
        return cvCoatColorItems;
    }

    /**
     * <b>Purpose:</b> Loads all ContainerStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvContainerStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvContainerStatusItems() {
        if (cvContainerStatusItems == null)
        {
            cvContainerStatusItems = new ArrayList<SelectItem>();
            for (CvContainerStatusEntity entity: getListSupportDTO().getCvContainerStatus())
            {
                cvContainerStatusItems.add(new SelectItem(entity, entity.getContainerStatus()));
            }
        }
        return cvContainerStatusItems;
    }
    
    /**
     * <b>Purpose:</b> Loads all ContainerStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvContainerStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvContainerStatusItemsOptional() {
        if (cvContainerStatusItems == null)
        {
            cvContainerStatusItems = new ArrayList<SelectItem>();
            cvContainerStatusItems.add(new SelectItem("", ""));
            for (CvContainerStatusEntity entity: getListSupportDTO().getCvContainerStatus())
            {
                cvContainerStatusItems.add(new SelectItem(entity, entity.getContainerStatus()));
            }
        }
        return cvContainerStatusItems;
    }

    public List<SelectItem> getCvContainerStatusItemsStringOptional() {
        if (cvContainerStatusItems == null)
        {
            cvContainerStatusItems = new ArrayList<SelectItem>();
            cvContainerStatusItems.add(new SelectItem("", ""));
            for (CvContainerStatusEntity entity: getListSupportDTO().getCvContainerStatus())
            {
                cvContainerStatusItems.add(new SelectItem(entity.getContainerStatus(), entity.getContainerStatus()));
            }
        }
        return cvContainerStatusItems;
    }

    public List<SelectItem> getCvContainerStatusItemsPairOptional() {
        if (cvContainerStatusItems == null)
        {
            cvContainerStatusItems = new ArrayList<SelectItem>();
            cvContainerStatusItems.add(new SelectItem("", ""));
            for (CvContainerStatusEntity entity: getListSupportDTO().getCvContainerStatus())
            {
                cvContainerStatusItems.add(new SelectItem(entity.getContainerStatuskey().toString(), entity.getContainerStatus()));
            }
        }
        return cvContainerStatusItems;
    }

    /**
     * <b>Purpose:</b> Loads all ContainerStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvContainerStatus of type List<SelectItem>
     */
    public List<SelectItem> getActiveCvContainerStatusItems() {
        if (cvContainerStatusItems == null)
        {
            cvContainerStatusItems = new ArrayList<SelectItem>();
            cvContainerStatusItems.add(new SelectItem("", ""));

            // eliminate 'Retired' from list
            for (CvContainerStatusEntity entity: getListSupportDTO().
                    getCvContainerStatus())
            {
                if (!entity.getContainerStatus().isEmpty() &&
                        !entity.getContainerStatus().equalsIgnoreCase("retired")) {
                    cvContainerStatusItems.add(new SelectItem(entity,
                            entity.getContainerStatus()));
                }
            }
        }
        return cvContainerStatusItems;
    }

    /**
     * <b>Purpose:</b> Loads all CvDiet controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvDiet of type List<SelectItem>
     */
    public List<SelectItem> getCvDietItems() {
        String desc = "";
        if (cvDietItems == null)
        {
            cvDietItems = new ArrayList<SelectItem>();
            
            cvDietItems.add(new SelectItem("", ""));

            for (CvDietEntity entity: getListSupportDTO().getCvDiet())
            {
                if (entity.getDietDescription() != null && !entity.getDietDescription().equals("")) {
                    desc = entity.getDietDescription();
                }            
                cvDietItems.add(new SelectItem(entity, entity.getDiet() + " " + 
                    ":" + " " + desc));
            }
        }
        return cvDietItems;
    }
    
    public List<SelectItem> getStringCvDietItems() {
        String desc = "";
        if (stringCvDietItems == null)
        {
            stringCvDietItems = new ArrayList<SelectItem>();
            
            stringCvDietItems.add(new SelectItem("", ""));

            for (CvDietEntity entity: getListSupportDTO().getCvDiet())
            {
                if (entity.getDietDescription() != null && !entity.getDietDescription().equals("")) {
                    desc = entity.getDietDescription();
                }            
                stringCvDietItems.add(new SelectItem(entity.getDiet(), entity.getDiet() + " " + ":" + " " + desc));
            }
        }
        return stringCvDietItems;
    }

    /**
     * <b>Purpose:</b> Loads CvEpoch controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvEpoch of type List<SelectItem>
     */
    public List<SelectItem> getCvEpochItems() {
        if (cvEpochItems == null)
        {
            cvEpochItems = new ArrayList<SelectItem>();

            for (CvEpochEntity entity: getListSupportDTO().getCvEpoch())
            {
                cvEpochItems.add(new SelectItem(entity, entity.getEpoch()));
            }
        }
        return cvEpochItems;
    }

    /**
     * <b>Purpose:</b> Loads CvExpSampleLocation controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvExpSampleLocation of type List<SelectItem>
     * NOTE: This table is not used in JCMS
     */
    public List<SelectItem> getCvExpSampleLocationItems() {
        if (cvExpSampleLocationItems == null)
        {
            cvExpSampleLocationItems = new ArrayList<SelectItem>();

            for (CvExpSampleLocationEntity entity: getListSupportDTO().getCvExpSampleLocation())
            {
                cvExpSampleLocationItems.add(new SelectItem(entity, entity.getLocation()));
            }
        }
        return cvExpSampleLocationItems;
    }

    /**
     * <b>Purpose:</b> Loads CvExpStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvExpStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvExpStatusItems() {
        if (cvExpStatusItems == null)
        {
            cvExpStatusItems = new ArrayList<SelectItem>();

            for (CvExpStatusEntity entity: getListSupportDTO().getCvExpStatus())
            {
                cvExpStatusItems.add(new SelectItem(entity, entity.getStatus()));
            }
        }
        return cvExpStatusItems;
    }

    /**
     * <b>Purpose:</b> Loads CvFieldOfStudy controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvFieldOfStudy of type List<SelectItem>
     */
    public List<SelectItem> getCvFieldOfStudyItems() {
        if (cvFieldOfStudyItems == null)
        {
            cvFieldOfStudyItems = new ArrayList<SelectItem>();

            for (CvFieldOfStudyEntity entity: getListSupportDTO().getCvFieldOfStudy())
            {
                cvFieldOfStudyItems.add(new SelectItem(entity, entity.getFieldOfStudyName()));
            }
        }
        return cvFieldOfStudyItems;
    }

    /**
     * <b>Purpose:</b> Loads CvGeneclass controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvGeneclass of type List<SelectItem>
     */
    public List<SelectItem> getCvGeneclassItems() {
        if (cvGeneclassItems == null)
        {
            cvGeneclassItems = new ArrayList<SelectItem>();

            for (CvGeneclassEntity entity: getListSupportDTO().getCvGeneclass())
            {
                cvGeneclassItems.add(new SelectItem(entity, entity.getGeneClass()));
            }
        }
        return cvGeneclassItems;
    }

    /**
     * <b>Purpose:</b> Loads CvGeneration controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvGeneration of type List<SelectItem>
     */
    public List<SelectItem> getCvGenerationItems() {
        if (cvGenerationItems == null)
        {
            cvGenerationItems = new ArrayList<SelectItem>();

            for (CvGenerationEntity entity: getListSupportDTO().getCvGeneration())
            {
                cvGenerationItems.add(new SelectItem(entity, entity.getGeneration()));
            }
        }
        return cvGenerationItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvGeneration controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvGeneration of type List<SelectItem>
     */
    public List<SelectItem> getCvGenerationItemsOptional() {
        if (cvGenerationItems == null)
        {
            cvGenerationItems = new ArrayList<SelectItem>();
            
            cvGenerationItems.add(new SelectItem("", ""));

            for (CvGenerationEntity entity: getListSupportDTO().getCvGeneration())
            {
                cvGenerationItems.add(new SelectItem(entity, entity.getGeneration()));
            }
        }
        return cvGenerationItems;
    }

    /**
     * <b>Purpose:</b> Loads CvGenotypeSpecimenLocation controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvGenotypeSpecimenLocation of type List<SelectItem>
     */
    public List<SelectItem> getCvGenotypeSpecimenLocationItems() {
        if (cvGenotypeSpecimenLocationItems == null)
        {
            cvGenotypeSpecimenLocationItems = new ArrayList<SelectItem>();

            for (CvGenotypeSpecimenLocationEntity entity: getListSupportDTO().getCvGenotypeSpecimenLocation())
            {
                cvGenotypeSpecimenLocationItems.add(new SelectItem(entity, entity.getGenotypeSpecimenLocation()));
            }
        }
        return cvGenotypeSpecimenLocationItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvGenotypeSpecimenLocation controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvGenotypeSpecimenLocation of type List<SelectItem>
     */
    public List<SelectItem> getCvGenotypeSpecimenLocationItemsOptional() {
        if (cvGenotypeSpecimenLocationItems == null)
        {
            cvGenotypeSpecimenLocationItems = new ArrayList<SelectItem>();
            
            cvGenotypeSpecimenLocationItems.add(new SelectItem("", ""));

            for (CvGenotypeSpecimenLocationEntity entity: getListSupportDTO().getCvGenotypeSpecimenLocation())
            {
                cvGenotypeSpecimenLocationItems.add(new SelectItem(entity.getGenotypeSpecimenLocation(), 
                        entity.getGenotypeSpecimenLocation()));
            }
        }
        return cvGenotypeSpecimenLocationItems;
    }

    /**
     * <b>Purpose:</b> Loads CvHarvestMethod controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvHarvestMethod of type List<SelectItem>
     */
    public List<SelectItem> getCvHarvestMethodItems() {
        if (cvHarvestMethodItems == null)
        {
            cvHarvestMethodItems = new ArrayList<SelectItem>();

            for (CvHarvestMethodEntity entity: getListSupportDTO().getCvHarvestMethod())
            {
                cvHarvestMethodItems.add(new SelectItem(entity, entity.getHarvestMethod()));
            }
        }
        return cvHarvestMethodItems;
    }

    /**
     * <b>Purpose:</b> Loads CvHealthLevel controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvHealthLevel of type List<SelectItem>
     */
    public List<SelectItem> getCvHealthLevelItems() {
        if (cvHealthLevelItems == null)
        {
            cvHealthLevelItems = new ArrayList<SelectItem>();

            for (CvHealthLevelEntity entity: getListSupportDTO().getCvHealthLevel())
            {
                cvHealthLevelItems.add(new SelectItem(entity, entity.getHealthLevel()));
            }
        }
        return cvHealthLevelItems;
    }

    /**
     * <b>Purpose:</b> Loads CvKeywords controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvKeywords of type List<SelectItem>
     */
    public List<SelectItem> getCvKeywordsItems() {
        if (cvKeywordsItems == null)
        {
            cvKeywordsItems = new ArrayList<SelectItem>();

            for (CvKeywordsEntity entity: getListSupportDTO().getCvKeywords())
            {
                cvKeywordsItems.add(new SelectItem(entity, entity.getKeyword()));
            }
        }
        return cvKeywordsItems;
    }

    /**
     * <b>Purpose:</b> Loads CvLocationType controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvLocationType of type List<SelectItem>
     */
    public List<SelectItem> getCvLocationTypeItems() {
        if (cvLocationTypeItems == null)
        {
            cvLocationTypeItems = new ArrayList<SelectItem>();

            for (CvLocationTypeEntity entity: getListSupportDTO().getCvLocationType())
            {
                cvLocationTypeItems.add(new SelectItem(entity, entity.getLocationType()));
            }
        }
        return cvLocationTypeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvMatingCardNotes controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMatingCardNotes of type List<SelectItem>
     */
    public List<SelectItem> getCvMatingCardNotesItems() {
        if (cvMatingCardNotesItems == null)
        {
            cvMatingCardNotesItems = new ArrayList<SelectItem>();

            for (CvMatingCardNotesEntity entity: getListSupportDTO().getCvMatingCardNotes())
            {
                cvMatingCardNotesItems.add(new SelectItem(entity, entity.getMatingNotes()));
            }
        }
        return cvMatingCardNotesItems;
    }

    /**
     * <b>Purpose:</b> Loads CvMouseOrigin controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMouseOrigin of type List<SelectItem>
     */
    public List<SelectItem> getCvMouseOriginItems() {
        if (cvMouseOriginItems == null)
        {
            cvMouseOriginItems = new ArrayList<SelectItem>();

            for (CvMouseOriginEntity entity: getListSupportDTO().getCvMouseOrigin())
            {
                cvMouseOriginItems.add(new SelectItem(entity, entity.getMouseOrigin()));
            }
        }
        return cvMouseOriginItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvMouseOrigin controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMouseOrigin of type List<SelectItem>
     */
    public List<SelectItem> getCvMouseOriginItemsOptional() {
        if (cvMouseOriginItems == null)
        {
            cvMouseOriginItems = new ArrayList<SelectItem>();
            
            cvMouseOriginItems.add(new SelectItem("", ""));

            for (CvMouseOriginEntity entity: getListSupportDTO().getCvMouseOrigin())
            {
                cvMouseOriginItems.add(new SelectItem(entity, entity.getMouseOrigin()));
            }
        }
        return cvMouseOriginItems;
    }

    /**
     * <b>Purpose:</b> Loads CvMouseProtocol controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMouseProtocol of type List<SelectItem>
     */
    public List<SelectItem> getCvMouseProtocolItems() {
        if (cvMouseProtocolItems == null)
        {
            cvMouseProtocolItems = new ArrayList<SelectItem>();

            for (CvMouseProtocolEntity entity: getListSupportDTO().getAllCvMouseProtocol())
            {
                cvMouseProtocolItems.add(new SelectItem(entity, entity.getId()));
            }
        }
        return cvMouseProtocolItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvMouseProtocol controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMouseProtocol of type List<SelectItem>
     */
    public List<SelectItem> getCvMouseProtocolItemsOptional() {
        if (cvMouseProtocolItemsOptional == null)
        {
            cvMouseProtocolItemsOptional = new ArrayList<SelectItem>();
            cvMouseProtocolItemsOptional.add(new SelectItem("", ""));

            for (CvMouseProtocolEntity entity: getListSupportDTO().getAllCvMouseProtocol())
            {
                cvMouseProtocolItemsOptional.add(new SelectItem(entity, entity.getId()));
            }
        }
        return cvMouseProtocolItemsOptional;
    }
    
    /**
     * <b>Purpose:</b> Loads CvMouseUseItems controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvMouseUse of type List<SelectItem>
     */
    public List<SelectItem> getCvMouseUseItems() {
        if (cvMouseUseItems == null)
        {
            cvMouseUseItems = new ArrayList<SelectItem>();

            for (CvMouseUseEntity entity: getListSupportDTO().getCvMouseUse())
            {
                cvMouseUseItems.add(new SelectItem(entity, entity.getMouseUse()));
            }
        }
        return cvMouseUseItems;
    }
    
    public List<SelectItem> getCvMouseUseItemsStringKey(){
        if(cvMouseUseItemsStringKey == null){
            cvMouseUseItemsStringKey = new ArrayList<SelectItem>();
            cvMouseUseItemsStringKey.add(new SelectItem("", ""));
            for (CvMouseUseEntity entity: getListSupportDTO().getCvMouseUse())
            {
                cvMouseUseItemsStringKey.add(new SelectItem(entity.getKey(), entity.getMouseUse()));
            }
        }
        return cvMouseUseItemsStringKey;
    }

    public List<SelectItem> getCvMouseUseItemsPairOptional(){
        if(cvMouseUseItemsPairOptional == null){
            cvMouseUseItemsPairOptional = new ArrayList<SelectItem>();
            cvMouseUseItemsPairOptional.add(new SelectItem("", ""));
            for (MouseUseDTO dto: this.adminDAO.getMouseUseVocabulary())
            {
                cvMouseUseItemsPairOptional.add(new SelectItem(dto.getMouseUse_key(), dto.getMouseUse()));
            }
        }
        return cvMouseUseItemsPairOptional;
    }
    
    public List<SelectItem> getCvMouseUseActiveItems(){
        cvMouseUseActiveItems = new ArrayList<SelectItem>();
        for(cvMouseUseDTO dto : muDAO.getCvMouseUses()){
            cvMouseUseActiveItems.add(new SelectItem(dto, dto.getMouseUse()));
        }
        return cvMouseUseActiveItems;
    }

        /**
     * <b>Purpose:</b> Loads CvPhenotypeItems controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvPhenotype of type List<SelectItem>
     */
    public List<SelectItem> getCvPhenotypeTermItems() {
        if (cvPhenotypeItems == null)
        {
            cvPhenotypeItems = new ArrayList<SelectItem>();

            for (CvPhenotypeEntity entity: getListSupportDTO().getCvPhenotype())
            {
                cvPhenotypeItems.add(new SelectItem(entity, entity.getPhenotypeTermName()));
            }
        }
        return cvPhenotypeItems;
    }
    
    public List<SelectItem> getCvPhenotypeItemsStringKey(){
        if(cvPhenotypeItemsStringKey == null){
            cvPhenotypeItemsStringKey = new ArrayList<SelectItem>();
            cvPhenotypeItemsStringKey.add(new SelectItem("", ""));
            for (CvPhenotypeEntity entity: getListSupportDTO().getCvPhenotype())
            {
                cvPhenotypeItemsStringKey.add(new SelectItem(entity.getKey(), entity.getPhenotypeTermName()));
            }
        }
        return cvPhenotypeItemsStringKey;
    }
    
    public List<SelectItem> getCvPhenotypeActiveItems(){
        cvPhenotypeActiveItems = new ArrayList<SelectItem>();
        for(cvPhenotypeTermDTO dto : ptDAO.getActiveCvPhenotypeTerms()){
            cvPhenotypeActiveItems.add(new SelectItem(dto, dto.getPhenotypeTermName()));
        }
        return cvPhenotypeActiveItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvPreservationDetail controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvPreservationDetail of type List<SelectItem>
     */
    public List<SelectItem> getCvPreservationDetailItems() {
        if (cvPreservationDetailItems == null)
        {
            cvPreservationDetailItems = new ArrayList<SelectItem>();

            for (CvPreservationDetailEntity entity: getListSupportDTO().getCvPreservationDetail())
            {
                cvPreservationDetailItems.add(new SelectItem(entity, entity.getPreservationDetail()));
            }
        }
        return cvPreservationDetailItems;
    }

    /**
     * <b>Purpose:</b> Loads CvPreservationMethod controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvPreservationMethod of type List<SelectItem>
     */
    public List<SelectItem> getCvPreservationMethodItems() {
        if (cvPreservationMethodItems == null)
        {
            cvPreservationMethodItems = new ArrayList<SelectItem>();

            for (CvPreservationMethodEntity entity: getListSupportDTO().getCvPreservationMethod())
            {
                cvPreservationMethodItems.add(new SelectItem(entity, entity.getPreservationMethod()));
            }
        }
        return cvPreservationMethodItems;
    }

    /**
     * <b>Purpose:</b> Loads CvPreservationType controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvPreservationType of type List<SelectItem>
     */
    public List<SelectItem> getCvPreservationTypeItems() {
        if (cvPreservationTypeItems == null)
        {
            cvPreservationTypeItems = new ArrayList<SelectItem>();

            for (CvPreservationTypeEntity entity: getListSupportDTO().getCvPreservationType())
            {
                cvPreservationTypeItems.add(new SelectItem(entity, entity.getPreservationType()));
            }
        }
        return cvPreservationTypeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvSampleClass controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvSampleClass of type List<SelectItem>
     */
    public List<SelectItem> getCvSampleClassItems() {
        if (cvSampleClassItems == null)
        {
            cvSampleClassItems = new ArrayList<SelectItem>();

            for (CvSampleClassEntity entity: getListSupportDTO().getCvSampleClass())
            {
                cvSampleClassItems.add(new SelectItem(entity, entity.getSampleClass()));
            }
        }
        return cvSampleClassItems;
    }

    /**
     * <b>Purpose:</b> Loads CvSampleDateType controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvSampleDateType of type List<SelectItem>
     */
    public List<SelectItem> getCvSampleDateTypeItems() {
        if (cvSampleDateTypeItems == null)
        {
            cvSampleDateTypeItems = new ArrayList<SelectItem>();

            for (CvSampleDateTypeEntity entity: getListSupportDTO().getCvSampleDateType())
            {
                cvSampleDateTypeItems.add(new SelectItem(entity, entity.getSampleDateType()));
            }
        }
        return cvSampleDateTypeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvSampleStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvSampleStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvSampleStatusItems() {
        if (cvSampleStatusItems == null)
        {
            cvSampleStatusItems = new ArrayList<SelectItem>();

            for (CvSampleStatusEntity entity: getListSupportDTO().getCvSampleStatus())
            {
                cvSampleStatusItems.add(new SelectItem(entity, entity.getSampleStatus()));
            }
        }
        return cvSampleStatusItems;
    }

    /**
     * <b>Purpose:</b> Loads CvSampleType controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvSampleType of type List<SelectItem>
     */
    public List<SelectItem> getCvSampleTypeItems() {
        if (cvSampleTypeItems == null)
        {
            cvSampleTypeItems = new ArrayList<SelectItem>();

            for (CvSampleTypeEntity entity: getListSupportDTO().getCvSampleType())
            {
                cvSampleTypeItems.add(new SelectItem(entity, entity.getSampleType()));
            }
        }
        return cvSampleTypeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvStorageFacility controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvStorageFacility of type List<SelectItem>
     */
    public List<SelectItem> getCvStorageFacilityItems() {
        if (cvStorageFacilityItems == null)
        {
            cvStorageFacilityItems = new ArrayList<SelectItem>();

            for (CvStorageFacilityEntity entity: getListSupportDTO().getCvStorageFacility())
            {
                cvStorageFacilityItems.add(new SelectItem(entity, entity.getStorageFacility()));
            }
        }
        return cvStorageFacilityItems;
    }

    /**
     * <b>Purpose:</b> Loads CvStrainTypeItems controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvStrainType of type List<SelectItem>
     */
    public List<SelectItem> getCvStrainTypeItems() {
        if (cvStrainTypeItems == null)
        {
            cvStrainTypeItems = new ArrayList<SelectItem>();

            for (CvStrainTypeEntity entity: getListSupportDTO().getCvStrainType())
            {
                cvStrainTypeItems.add(new SelectItem(entity, entity.getStrainType()));
            }
        }
        return cvStrainTypeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvTestStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvTestStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvTestStatusItems() {
        if (cvTestStatusItems == null)
        {
            cvTestStatusItems = new ArrayList<SelectItem>();

            for (CvTestStatusEntity entity: getListSupportDTO().getCvTestStatus())
            {
                cvTestStatusItems.add(new SelectItem(entity, entity.getTestStatus()));
            }
        }
        return cvTestStatusItems;
    }

    /**
     * <b>Purpose:</b> Loads CvTimeUnit controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvTimeUnit of type List<SelectItem>
     */
    public List<SelectItem> getCvTimeUnitItems() {
        if (cvTimeUnitItems == null)
        {
            cvTimeUnitItems = new ArrayList<SelectItem>();

            for (CvTimeUnitEntity entity: getListSupportDTO().getCvTimeUnit())
            {
                cvTimeUnitItems.add(new SelectItem(entity, entity.getTimeUnit()));
            }
        }
        return cvTimeUnitItems;
    }

    /**
     * <b>Purpose:</b> Loads CvWeightUnit controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvWeightUnit of type List<SelectItem>
     */
    public List<SelectItem> getCvWeightUnitItems() {
        if (cvWeightUnitItems == null)
        {
            cvWeightUnitItems = new ArrayList<SelectItem>();

            for (CvWeightUnitEntity entity: getListSupportDTO().getCvWeightUnit())
            {
                cvWeightUnitItems.add(new SelectItem(entity, entity.getWeightUnit()));
            }
        }
        return cvWeightUnitItems;
    }

    /**
     * @param listSupportDTO the listSupportDTO to set
     */
    public void setListSupportDTO(ListSupportDTO listSupportDTO) {
        this.listSupportDTO = listSupportDTO;
    }

     /**
     * <b>Purpose:</b> Loads Time frame controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the time frame of type List<SelectItem>
     */
    public List<SelectItem> getTimeFrameItems()
    {
        if (timeFrameItems == null)
        {
            timeFrameItems = new ArrayList<SelectItem>();
            timeFrameItems.add(new SelectItem(SelectItemWrapper.TIMEFRAME_NONE, ""));
            timeFrameItems.add(new SelectItem(SelectItemWrapper.TIMEFRAME_BEFORE, "before"));
            timeFrameItems.add(new SelectItem(SelectItemWrapper.TIMEFRAME_AFTER, "after"));
        }

        return timeFrameItems;
    }

    /**
     * @param timeFrameItems the timeFrameItems to set
     */
    public void setTimeFrame(List<SelectItem> timeFrameItems) {
        this.timeFrameItems = timeFrameItems;
    }

    /**
     * @return the amPmItems
     */
    public List<SelectItem> getAmPmItems() {
        if (amPmItems == null)
        {
            amPmItems = new ArrayList<SelectItem>();
            amPmItems.add(new SelectItem("AM","AM"));
            amPmItems.add(new SelectItem("PM","PM"));
        }

        return amPmItems;
    }

    /**
     * @return the activeItems
     */
    public List<SelectItem> getActiveItems()
    {
        if (activeItems == null)
        {
            activeItems = new ArrayList<SelectItem>();
            activeItems.add(new SelectItem(1,"active"));
            activeItems.add(new SelectItem(0,"inactive"));
        }

        return activeItems;
    }

    /**
     * <b>Purpose:</b> Loads CvSex controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getCvSexItems() {
        if (sexItems == null)
        {
            sexItems = new ArrayList<SelectItem>();

            for (CvSexEntity entity: getListSupportDTO().getCvSex())
            {
                sexItems.add(new SelectItem(entity, entity.getSex()));
            }
        }
        return sexItems;
    }
    
    /**
     * <b>Purpose:</b> Loads CvSex controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getCvSexItemsOptional() {
        if (sexItems == null)
        {
            sexItems = new ArrayList<SelectItem>();
            
            sexItems.add(new SelectItem("", ""));

            for (CvSexEntity entity: getListSupportDTO().getCvSex())
            {
                sexItems.add(new SelectItem(entity, entity.getSex()));
            }
        }
        return sexItems;
    }

    /**
     * <b>Purpose:</b> Loads  Mouse Filters for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getMouseFilters() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        //items.add(new SelectItem("Between", "Between"));
        items.add(new SelectItem("Contains", "Contains"));
        items.add(new SelectItem("Equals", "Equals"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads  Mouse Filters for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getResultSize() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        //items.add(new SelectItem("Between", "Between"));
        items.add(new SelectItem("500", "500"));
        items.add(new SelectItem("1000", "1000"));
        items.add(new SelectItem("1500", "1500"));
        items.add(new SelectItem("2000", "2000"));
        items.add(new SelectItem("2500", "2500"));
        
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Afe filters for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getIntegerFilters() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Equals", "Equals"));        
        items.add(new SelectItem("Greater Than", "Greater Than"));
        items.add(new SelectItem("Less Than", "Less Than"));        

        return items;
    }

    /**
     * <b>Purpose:</b> Loads Afe filters for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getAgeFilters() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem(" ", " "));
        items.add(new SelectItem("Between", "Between"));
        items.add(new SelectItem("Greater Than", "Greater Than"));
        items.add(new SelectItem("Less Than", "Less Than"));
        items.add(new SelectItem("Equals", "Equals"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads afe measures for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getAgeMeasures() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Days", "Days"));
        items.add(new SelectItem("Weeks", "Weeks"));
        items.add(new SelectItem("Months", "Months"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads afe measures for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getAbnormalDataChoices() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Any", "Any"));
        items.add(new SelectItem("True", "True"));
        items.add(new SelectItem("False", "False"));

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads afe measures for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getCageSummaryGroupBy() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("None", "None"));
        items.add(new SelectItem("Room", "Room"));
        items.add(new SelectItem("Status", "Status"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads afe measures for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getCageSummaryPenOptions() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Full Day", "Full Day"));
        items.add(new SelectItem("Partial First Day", "Partial First Day"));
        items.add(new SelectItem("Partial Last Day", "Partial Last Day"));
        items.add(new SelectItem("Any Day", "Any Day"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads mating filters for
     *      populating JSF SelectItem components. <br />
     * @return the List<SelectItem>
     */
    public List<SelectItem> getMatingFilters() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Between", "Between"));
        items.add(new SelectItem("Equals", "Equals"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads CvBreedingStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvBreedingStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvBreedingStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (CvBreedingStatusEntity entity : getListSupportDTO().getCvBreedingStatus()) {
            items.add(new SelectItem(entity, entity.getBreedingStatus()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvBreedingStatus controlled vocabulary for
     *      populating JSF SelectItem components with an option for a blank choice. <br />
     * @return the CvBreedingStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvBreedingStatusItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        
        items.add(new SelectItem("", ""));

        for (CvBreedingStatusEntity entity : getListSupportDTO().getCvBreedingStatus()) {
            items.add(new SelectItem(entity, entity.getBreedingStatus()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvBreedingStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvBreedingStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvSetupVariableItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (DbsetupEntity entity : getListSupportDTO().getSetUpVariables()) {
            items.add(new SelectItem(entity, entity.getMTSVar()));
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads MatingStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the MatingStatus of type List<SelectItem>
     */
    public List<SelectItem> getMatingStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("Active", "Active"));        
        items.add(new SelectItem("Proposed", "Proposed"));
        items.add(new SelectItem("Proposed for Retirement", "Proposed for Retirement"));
        items.add(new SelectItem("Retired", "Retired"));

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads MatingStatus controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the MatingStatus of type List<SelectItem>
     */
    public List<SelectItem> getLitterStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("A", "A : Active"));        
        items.add(new SelectItem("B", "B : Born dead"));
        items.add(new SelectItem("D", "D : Dead at Weaning"));
        items.add(new SelectItem("H", "H : Harvested"));
        items.add(new SelectItem("K", "K : Killed"));
        items.add(new SelectItem("M", "M : Missing"));

        return items;
    }

    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvLifeStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";

        for (LifeStatusEntity entity : getListSupportDTO().getLifeStatus()) {
            // get pen ID and name
            if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                desc = entity.getDescription();
            }
            items.add(new SelectItem(entity, entity.getLifeStatus() + " " + 
                    ":" + " " + desc));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvLifeStatusStringItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";

        for (LifeStatusEntity entity : getListSupportDTO().getLifeStatus()) {
            // get pen ID and name
            if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                desc = entity.getDescription();
            }
            items.add(new SelectItem(entity.getLifeStatus(), entity.getLifeStatus() + " " + 
                    ":" + " " + desc));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvBreedingStatusStringItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";
        
        for (CvBreedingStatusEntity entity : getListSupportDTO().getCvBreedingStatus()) {
            // get pen ID and name
            if (entity.getBreedingStatus() != null && !entity.getBreedingStatus().equals("")) {
                desc = entity.getBreedingStatus();
            }           
            items.add(new SelectItem(entity.getAbbreviation(), entity.getAbbreviation() + " " + 
                   ":" + " " + desc));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvCrossStatusItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));
        
        for (CvCrossstatusEntity entity : getListSupportDTO().getCvCrossStatus()) {
            items.add(new SelectItem(entity, entity.getCrossStatus()));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvCrossStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (CvCrossstatusEntity entity : getListSupportDTO().getCvCrossStatus()) {
            items.add(new SelectItem(entity, entity.getCrossStatus()));
        }
        return items;
    }
    
    public List<SelectItem> getCvCrossStatusItemsNonDesigned(){
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (CvCrossstatusEntity entity : getListSupportDTO().getCvCrossStatus()) {
            if(!entity.getCrossStatus().contains("designed")){
                items.add(new SelectItem(entity, entity.getCrossStatus()));
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvLifeStatusItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";

        items.add(new SelectItem("", ""));
        for (LifeStatusEntity entity : getListSupportDTO().getLifeStatus()) {
            // get pen ID and name
            if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                desc = entity.getDescription();
            }
            items.add(new SelectItem(entity, entity.getLifeStatus() + " " + 
                    ":" + " " + desc));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads CvLifeStatuscontrolled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvLifeExitStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";

        for (LifeStatusEntity entity : getListSupportDTO().getLifeStatus()) {
            // only get the items where exit status is false
            if (!entity.getExitStatus()) {
                // get pen ID and name
                if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                    desc = entity.getDescription();
                }
                items.add(new SelectItem(entity, entity.getLifeStatus() + " "
                        + ":" + " " + desc));
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads exit status CvLifeStatusEntities for
     *      populating JSF SelectItem components. <br />
     * @return the CvLifeStatus of type List<SelectItem>
     */
    public List<SelectItem> getCvExitStatusItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String desc = "";

        for (LifeStatusEntity entity : getListSupportDTO().getLifeStatus()) {
            // only get the items where exit status is true
            if (entity.getExitStatus()) {
                if (entity.getDescription() != null && !entity.getDescription().equals("")) {
                    desc = entity.getDescription();
                }
                items.add(new SelectItem(entity, entity.getLifeStatus() + " " + ":" + " " + desc));
            }
        }
        return items;
    }

    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getMouseStrainItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String jrNum = "";

        for (StrainEntity entity : getListSupportDTO().getMouseStrains(this.getUserEntity())) {
            // get pen ID and name
            if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                jrNum = entity.getJrNum().toString();
            } else {
                jrNum = "";
            }
            // if the set up variable is true, then display strain name first 
            // and then Jr# (Stock #)
            if (this.getStrainDAO().checkIfStrainFirst()) {
                items.add(new SelectItem(entity, entity.getStrainName() + " " + 
                    ":" + " " + jrNum));
            }
            else {
                items.add(new SelectItem(entity, jrNum + " " + 
                    ":" + " " + entity.getStrainName()));
            }
        }

        return items;
    }
    
    private StrainDAO getStrainDAO() {
        StrainDAO strainDAO = new StrainDAO(this.getUserEntity());
        return strainDAO;
    }
    
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getStrainItemsOptional() throws Exception {
            List<SelectItem> items = new ArrayList<SelectItem>();
            String jrNum = "";
    
            items.add(new SelectItem("", ""));

            for (StrainEntity entity : getListSupportDTO().getAllStrains(this.getUserEntity())) {

                // get jr#
                if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                    jrNum = entity.getJrNum().toString();
                } else {
                    jrNum = "";
                }
                // if the set up variable is true, then display strain name first 
                // and then Jr#
                if (this.getStrainDAO().checkIfStrainFirst()) {
                    items.add(new SelectItem(entity, entity.getStrainName() + " " + ":" + " " + jrNum));
                }
                else {
                    items.add(new SelectItem(entity, jrNum + " " + ":" + " " + entity.getStrainName()));
                }
            }
            strainItemsOptional = items;
            return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getAllStrains() throws Exception {        
            List<SelectItem> items = new ArrayList<SelectItem>();
            items.add(new SelectItem("", ""));
            String jrNum = "";

            for (StrainEntity entity : getListSupportDTO().getAllStrains(this.getUserEntity())) {
                // get jr#
                if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                    jrNum = entity.getJrNum().toString();
                } else {
                    jrNum = "";
                }

                // if the set up variable is true, then display strain name first 
                // and then Jr#
                if (this.getStrainDAO().checkIfStrainFirst()) {
                    items.add(new SelectItem(entity, entity.getStrainName() + " " + 
                        ":" + " " + jrNum));
                }
                else {
                    items.add(new SelectItem(entity, jrNum + " " + 
                        ":" + " " + entity.getStrainName()));
                }          
            }
            allStrains = items;
            return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getApprovedLitterStrains() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String jrNum = "";

        System.out.println("in approved strain with dam and sire " + this.damStrainKey + "  " + this.sireStrainKey);
        
        items.add(new SelectItem("", ""));
        
        if (this.damStrainKey > 0 && this.sireStrainKey > 0) {
            for (StrainEntity entity : this.getListSupportDTO().getApprovedLitterStrains(this.damStrainKey, this.sireStrainKey, this.getUserEntity())) {
                // get jr#
                if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                    jrNum = entity.getJrNum().toString();
                } else {
                    jrNum = "";
                }
                // if the set up variable is true, then display strain name first 
                // and then Jr#
                if (this.getStrainDAO().checkIfStrainFirst()) {
                    items.add(new SelectItem(entity, entity.getStrainName() + " "
                            + ":" + " " + jrNum));
                } else {
                    items.add(new SelectItem(entity, jrNum + " "
                            + ":" + " " + entity.getStrainName()));
                }
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getActiveStrainsOptional() throws Exception {
            List<SelectItem> items = new ArrayList<SelectItem>();
            String jrNum = "";

            items.add(new SelectItem("", ""));

            for (StrainEntity entity : getListSupportDTO().getActiveStrains(this.getUserEntity())) {
                // get pen ID and name
                if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                    jrNum = entity.getJrNum().toString();
                } else {
                    jrNum = "";
                }
                // if the set up variable is true, then display strain name first 
                // and then Jr#
                if (this.getStrainDAO().checkIfStrainFirst()) {
                    items.add(new SelectItem(entity, entity.getStrainName() + " " + ":" + " " + jrNum));
                }
                else {
                    items.add(new SelectItem(entity, jrNum + " " + ":" + " " + entity.getStrainName()));
                }
            }
            activeStrainsOptional = items;
            return items;
    }
        
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getActiveStrainsStringItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String jrNum = "";

        for (StrainEntity entity : getListSupportDTO().getActiveStrains(this.getUserEntity())) {
            // get pen ID and name
            if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                jrNum = entity.getJrNum().toString();
            } else {
                jrNum = "";
            }
            
            // if the set up variable is true, then display strain name first 
            // and then Jr#
            if (this.getStrainDAO().checkIfStrainFirst()) {
                items.add(new SelectItem(entity.getStrainName(), entity.getStrainName() + " " + 
                    ":" + " " + jrNum));
            }
            else {
                items.add(new SelectItem(entity.getStrainName(), jrNum + " " + 
                    ":" + " " + entity.getStrainName()));
            }
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getProtocolStringItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));
        // changed to NOT be filtered by owner -- BAS 7-28-14
        for (CvMouseProtocolEntity entity : getListSupportDTO().getAllCvMouseProtocol()) {
            items.add(new SelectItem(entity.getId(), entity.getId()));
        }
        return items;
    }

     /**
     * <b>Purpose:</b> Loads Strain controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public List<SelectItem> getMatingStrainItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String jrNum = "";

        for (StrainEntity entity : getListSupportDTO().getMatingStrains(this.getUserEntity())) {
            // get pen ID and name
            if (entity.getJrNum() != null && entity.getJrNum() > 0) {
                jrNum = entity.getJrNum().toString();
            } else {
                jrNum = "";
            }
            // if the set up variable is true, then display strain name first 
            // and then Jr#
            if (this.getStrainDAO().checkIfStrainFirst()) {
                items.add(new SelectItem(entity, entity.getStrainName() + " " + 
                    ":" + " " + jrNum));
            }
            else {
                items.add(new SelectItem(entity, jrNum + " " + 
                    ":" + " " + entity.getStrainName()));
            }
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getGuestOwnerItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getGuestOwnerItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("guestOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }

        return items;
    }
    
        /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getColonyManageOwnerItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }

        return items;
    }
    
        /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getColonyManageOwnerItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("colonyManageOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getAdminOwnerItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("adminOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Owner controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Owners of type List<SelectItem>
     */
    public List<SelectItem> getAdminOwnerItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        ArrayList<OwnerEntity> owners = (ArrayList<OwnerEntity>) this.getSessionParameter("adminOwnerEntityLst");
        for (OwnerEntity entity : owners) {
            items.add(new SelectItem(entity, entity.getOwner()));
        }

        return items;
    }
    
    
    

    /**
     * <b>Purpose:</b> Loads Litter controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Litters of type List<SelectItem>
     */
    public List<SelectItem> getLitterNumberItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (LitterEntity entity : getListSupportDTO().getLitterID()) {
            items.add(new SelectItem(entity, entity.getLitterID()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Litter controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Litters of type List<SelectItem>
     */
    public List<SelectItem> getLitterNumberItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        
        items.add(new SelectItem("", ""));

        for (LitterEntity entity : getListSupportDTO().getLitterID()) {
            items.add(new SelectItem(entity, entity.getLitterID()));
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads Mouse controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Mouse of type List<SelectItem>
     */
    public List<SelectItem> getMouseItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(this.getBlankItem());
        
        for (MouseEntity entity : getListSupportDTO().getMouseID()) {
            items.add(new SelectItem(entity, entity.getId()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Gene controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getGenesItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));
        ArrayList<AdminGeneDTO> geneList = adminDAO.getGeneVocabulary();

        for (AdminGeneDTO dto : geneList) {
            if (dto.getLabSymbol() != null && !dto.getLabSymbol().equals("")) {
                items.add(new SelectItem(new GeneEntity(dto), dto.getLabSymbol()));
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Gene controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getGenesItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        ArrayList<AdminGeneDTO> geneList = adminDAO.getGeneVocabulary();

        for (AdminGeneDTO dto : geneList) {
            if (dto.getLabSymbol() != null && !dto.getLabSymbol().equals("")) {
                items.add(new SelectItem(new GeneEntity(dto), dto.getLabSymbol()));
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Allele controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getAlleleStringItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        
        items.add(new SelectItem("", ""));

        for (AlleleEntity entity : getListSupportDTO().getAllAlleles()) {
            items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Allele controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getAllelesByGeneItems() throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();
        
        items.add(new SelectItem("", ""));

        if (geneKey > 0) {
            for (AlleleEntity entity : getListSupportDTO().
                getAllelesByGene(geneKey)) {
            items.add(new SelectItem(entity.getAllele(), entity.getAllele()));
            }
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Allele controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getAlleleItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (AlleleEntity entity : getListSupportDTO().getAllAlleles()) {
            items.add(new SelectItem(entity, entity.getAllele()));
        }
        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Allele controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getAlleleItemsByOwner() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (AlleleEntity entity : getListSupportDTO().getAllelesByOwners()) {
            items.add(new SelectItem(entity, entity.getAllele()));
        }
        return items;
    }

    /**
     * <b>Purpose:</b> Loads Gene controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Genes of type List<SelectItem>
     */
    public List<SelectItem> getRoomItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));

        for (RoomEntity entity : getListSupportDTO().getRoom()) {
            items.add(new SelectItem(entity, entity.getRoomName()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Room controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Rooms of type List<SelectItem>
     */
    public List<SelectItem> getCvRoomItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (RoomEntity entity : getListSupportDTO().getRoom()) {
            items.add(new SelectItem(entity, entity.getRoomName()));
        }

        return items;
    }

    public List<SelectItem> getCvRoomItemsStringOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));

        for (RoomEntity entity : getListSupportDTO().getRoom()) {
            items.add(new SelectItem(entity.getRoomName(), entity.getRoomName()));
        }

        return items;
    }

    public List<SelectItem> getCvRoomItemsPairOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("", ""));

        for (RoomEntity entity : getListSupportDTO().getRoom()) {
            items.add(new SelectItem(entity.getRoomKey(), entity.getRoomName()));
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Container controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return PenNumbers of type List<SelectItem>
     */
    public List<SelectItem> getPenNumbersItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String id = "";
        String name = "";

        for (ContainerEntity entity : getListSupportDTO().getPen()) {
            if (entity.getContainerID()>0) {
                // get pen ID and name
                id = Integer.toString(entity.getContainerID());
                if (entity.getContainerName() != null) {
                    name = entity.getContainerName();
                }
                items.add(new SelectItem(entity, id + " " + ":" + " " + name));
            }
        }

        return items;
    }
    
    /**
     * <b>Purpose:</b> Loads Container controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return PenNumbers of type List<SelectItem>
     */
    public List<SelectItem> getPenNumbersItemsOptional() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        String id = "";
        String name = "";
        
        items.add(new SelectItem("", ""));

        for (ContainerEntity entity : getListSupportDTO().getAllPens()) {
            if (entity.getContainerID()>0) {
                // get pen ID and name
                id = Integer.toString(entity.getContainerID());
                if (entity.getContainerName() != null) {
                    name = entity.getContainerName();
                }
                items.add(new SelectItem(entity, id + " " + ":" + " " + name));
            }
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads Container controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the PenNames of type List<SelectItem>
     */
    public List<SelectItem> getPenNamesItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (ContainerEntity entity : getListSupportDTO().getPen()) {
            items.add(new SelectItem(entity, entity.getContainerName()));
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads user defined query controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the query names of type List<SelectItem>
     */
    public List<SelectItem> getUserDefinedQueriesItems()
            throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (QueryDefinitionEntity entity : getListSupportDTO().
                getUserDefinedQueries()) {
            items.add(new SelectItem(entity, entity.getQueryName()));
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads user defined query controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the query names of type List<SelectItem>
     */
    public List<SelectItem> getCvUserDefinedQueryTypeItems()
            throws Exception {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (CvQueryTypeEntity entity : getListSupportDTO().
                getCvUserDefinedQueryType()) {
            items.add(new SelectItem(entity, entity.getQueryType()));
        }

        return items;
    }

    /**
     * <b>Purpose:</b> Loads Mating controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return Mating ID's of type List<SelectItem>
     */
    public List<SelectItem> getMatingIDItems() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(this.getBlankItem());
        String label = "";

        for (MatingEntity entity : getListSupportDTO().getMatingID()) {
            if (entity.getMatingID()>0) {
                label = Integer.toString(entity.getMatingID());
                items.add(new SelectItem(entity, label));
            }
        }

        return items;
    }
    
     /**
     * <b>Purpose:</b> Loads Detail Cards controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the Strains of type List<SelectItem>
     */
    public ArrayList<SelectItem> getDetailCards() throws Exception {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));
        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        ArrayList<QueryDefinitionEntity> detailCards = ccDAO.getDetailCards(wgLst);
        for (QueryDefinitionEntity entity : detailCards) {
            items.add(new SelectItem(entity.getKey(), entity.getQueryName()));
        }
        return items;
    }
    
    public ArrayList<SelectItem> getDetailCardsRequired() throws Exception {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        ArrayList<QueryDefinitionEntity> detailCards = ccDAO.getDetailCards(wgLst);
        for (QueryDefinitionEntity entity : detailCards) {
            items.add(new SelectItem(entity.getKey(), entity.getQueryName()));
        }
        return items;
    }
    
         /**
     * <b>Purpose:</b> Loads Wean Cards controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the wean cards of type List<SelectItem>
     */
    public ArrayList<SelectItem> getWeanCards() throws Exception {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));
        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        ArrayList<QueryDefinitionEntity> weanCards = ccDAO.getWeanCards(wgLst);
        for (QueryDefinitionEntity entity : weanCards) {
            items.add(new SelectItem(entity.getKey(), entity.getQueryName()));
        }
        return items;
    }
    
     /**
     * <b>Purpose:</b> Loads Mating Cards controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the mating cards of type List<SelectItem>
     */
    public ArrayList<SelectItem> getMatingCards() throws Exception {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        items.add(new SelectItem("", ""));
        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        ArrayList<QueryDefinitionEntity> matingCards = ccDAO.getMatingCards(wgLst);
        for (QueryDefinitionEntity entity : matingCards) {
            items.add(new SelectItem(entity.getKey(), entity.getQueryName()));
        }
        return items;
    }
    
    public ArrayList<SelectItem> getMatingCardsRequired() throws Exception {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        ArrayList<QueryDefinitionEntity> matingCards = ccDAO.getMatingCards(wgLst);
        for (QueryDefinitionEntity entity : matingCards) {
            items.add(new SelectItem(entity.getKey(), entity.getQueryName()));
        }
        return items;
    }
    
    public ArrayList<SelectItem> getGuestWorkgroups(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
        for (WorkgroupEntity entity : wgLst) {
            items.add(new SelectItem(entity, entity.getWorkgroupName()));
        }
        return items;
    }
    
    public ArrayList<SelectItem> getColonyManageWorkgroups(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst");
        for (WorkgroupEntity entity : wgLst) {
            items.add(new SelectItem(entity, entity.getWorkgroupName()));
        }
        return items;
    }
    
    public ArrayList<SelectItem> getAdminWorkgroups(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();

        ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("adminWorkgroupEntityLst");
        for (WorkgroupEntity entity : wgLst) {
            items.add(new SelectItem(entity, entity.getWorkgroupName()));
        }
        return items;
    }
    
    
    /**
     * @return the ownerLogged
     */
    public String getOwnerLogged() {
        return ownerLogged;
    }

    /**
     * @param ownerLogged the ownerLogged to set
     */
    public void setOwnerLogged(String ownerLogged) {
        this.ownerLogged = ownerLogged;
    }

    /**
     * @return the cvGeneClassStringItems
     */
    public List<SelectItem> getCvGeneClassStringItemsOptional() {
        if (cvGeneClassStringItemsOptional == null)
        {
            cvGeneClassStringItemsOptional = new ArrayList<SelectItem>();
            cvGeneClassStringItemsOptional.add(new SelectItem("", ""));
            for (CvGeneclassEntity entity: getListSupportDTO().getCvGeneclass())
            {
                cvGeneClassStringItemsOptional.add(new SelectItem(entity.getGeneClass(), entity.getGeneClass() + " (" +entity.getDescription()+")", entity.getDescription(), false));
            }
        }
        return cvGeneClassStringItemsOptional;
    }

    /**
     * @return the alleleItemsOptional
     */
    public List<SelectItem> getAlleleItemsString() {
        if (alleleItemsOptional == null)
        {
            alleleItemsOptional = new ArrayList<SelectItem>();
            alleleItemsOptional.add(new SelectItem("Gene Class", "Gene Class"));
            alleleItemsOptional.add(new SelectItem("Gene", "Gene"));
        }
        return alleleItemsOptional;
    }

    /**
     * @return the alleleItemsOptional
     */
    public List<SelectItem> getAlleleItemsOptional() {
        if (alleleItemsOptional == null)
        {
            alleleItemsOptional = new ArrayList<SelectItem>();
            alleleItemsOptional.add(new SelectItem("", ""));
            alleleItemsOptional.add(new SelectItem("Gene Class", "Gene Class"));
            alleleItemsOptional.add(new SelectItem("Gene", "Gene"));
        }
        return alleleItemsOptional;
    }

    /**
     * @return the getAdminStrainItemsOptional
     */
    public List<SelectItem> getAdminStrainItemsOptional() {
        if (strainItemsOptional == null)
        {
            strainItemsOptional = new ArrayList<SelectItem>();
            strainItemsOptional.add(new SelectItem(new AdminStrainDTO(), ""));
            for (AdminStrainDTO dto: adminDAO.getStrainVocabulary())
            {
                strainItemsOptional.add(new SelectItem(dto, dto.getStrainName()));
            }
        }
        return strainItemsOptional;
    }

    /**
     * @return the cvStrainStatusItemsOptional
     */
    public List<SelectItem> getCvStrainStatusStringItemsOptional() {
        if (cvStrainStatusStringItemsOptional == null)
        {
            cvStrainStatusStringItemsOptional = new ArrayList<SelectItem>();
            cvStrainStatusStringItemsOptional.add(new SelectItem("", ""));
            for (AdminStrainStatusDTO dto: adminDAO.getStrainStatusVocabulary())
            {
                cvStrainStatusStringItemsOptional.add(new SelectItem(dto.getStrainStatus(), dto.getStrainStatus() +"  -  "+ dto.getDescription()));
            }
        }
        return cvStrainStatusStringItemsOptional;
    }

    /**
     * @return the cvStrainStatusItemsOptional
     */
    public List<SelectItem> getCvGenerationStringItemsOptional() {
        if (cvGenerationStringItemsOptional == null)
        {
            cvGenerationStringItemsOptional = new ArrayList<SelectItem>();
            cvGenerationStringItemsOptional.add(new SelectItem("", ""));
            for (AdminGenerationDTO dto: adminDAO.getGenerationVocabulary())
            {
                cvGenerationStringItemsOptional.add(new SelectItem(dto.getGeneration(), dto.getGeneration()));
            }
        }
        return cvGenerationStringItemsOptional;
    }

    /**
     * @return the cvStrainTypeItemsOptional
     */
    public List<SelectItem> getCvStrainTypeStringItemsOptional() {
        if (cvStrainTypeStringItemsOptional == null)
        {
            cvStrainTypeStringItemsOptional = new ArrayList<SelectItem>();
            cvStrainTypeStringItemsOptional.add(new SelectItem("", ""));
            for (AdminStrainTypeDTO dto: adminDAO.getStrainTypeVocabulary())
            {
                cvStrainTypeStringItemsOptional.add(new SelectItem(dto.getStrainType(), dto.getStrainType()));
            }
        }
        return cvStrainTypeStringItemsOptional;
    }

    /**
     * @return the stringStatuses
     */
    public List<SelectItem> getStringStatuses() {
        stringStatuses = new ArrayList<SelectItem>();
        for (CvContainerStatusEntity entity: getListSupportDTO().getCvContainerStatus())
        {
            int statusKey = entity.getContainerStatuskey();
            stringStatuses.add(new SelectItem(statusKey, entity.getContainerStatus()));
        }
        return stringStatuses;
    }

    /**
     * @return the stringRooms
     */
    public List<SelectItem> getStringRooms() {
        stringRooms = new ArrayList<SelectItem>();
        for (RoomEntity entity : getListSupportDTO().getRoom()) {
            stringRooms.add(new SelectItem(entity.getRoomKey().toString(), entity.getRoomName()));
        }
        return stringRooms;
    }

    /**
     * <b>Purpose:</b> Loads CvHealthLevel controlled vocabulary for
     *      populating JSF SelectItem components. <br />
     * @return the cvHealthLevel of type List<SelectItem>
     */
    public List<SelectItem> getCvHealthLevelItemsStringOptional() {
        if (cvHealthLevelStringItemsOptional == null)
        {
            cvHealthLevelStringItemsOptional = new ArrayList<SelectItem>();
            cvHealthLevelStringItemsOptional.add(new SelectItem("", ""));
            for (CvHealthLevelEntity entity: getListSupportDTO().getCvHealthLevel())
            {
                cvHealthLevelStringItemsOptional.add(new SelectItem(entity.getHealthLevel(), entity.getHealthLevel()));
            }
        }
        return cvHealthLevelStringItemsOptional;
    }

    
    public List<SelectItem> getCvCageCardQueryTypes(Boolean isOptional) {
        if (cvCageCardQueryTypesOptional == null)
        {
            cvCageCardQueryTypesOptional = new ArrayList<SelectItem>();
            if (isOptional) cvCageCardQueryTypesOptional.add(new SelectItem("", ""));
            for (QueryTypeDTO dto: adminDAO.getCageCardQueryTypes())
            {
                cvCageCardQueryTypesOptional.add(new SelectItem(dto.getQueryType_key(), dto.getQueryType()));
            }
        }
        return cvCageCardQueryTypesOptional;
    }

    public List<SelectItem> getQueryDefinitions(Boolean isOptional) {
        queryDefinitionsOptional = new ArrayList<SelectItem>();
        if (isOptional) queryDefinitionsOptional.add(new SelectItem("", ""));
        for (QueryDefinitionDTO dto: ccDAO.getQueryDefinitions())
        {
            queryDefinitionsOptional.add(new SelectItem(dto.getQueryDefinition_key(), dto.getQueryName()));
        }
        return queryDefinitionsOptional;
    }
    
    public List<SelectItem> getQueryDefinitions(String queryTypeKey, Boolean isOptional) {
        queryDefinitionsOptional = new ArrayList<SelectItem>();
        if (isOptional) queryDefinitionsOptional.add(new SelectItem("", ""));
        for (QueryDefinitionDTO dto: ccDAO.getQueryDefinitions(queryTypeKey))
        {
            queryDefinitionsOptional.add(new SelectItem(dto.getQueryDefinition_key(), dto.getQueryName()));
        }
        return queryDefinitionsOptional;
    }
    
    public List<SelectItem> getUseScheduleStartEvents(){
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        for(UseScheduleStartEventDTO dto : pDAO.getUseScheduleStartEvents()){
            items.add(new SelectItem(dto.getUseScheduleStartEventKey(), dto.getUseScheduleStartEvent()));
        }
        return items;
    }

    /**
     * @return the activeBirthdateCalendarUseScheduleItems
     */
    public List<SelectItem> getActiveBirthdateCalendarUseScheduleItems() {
        if(activeBirthdateCalendarUseScheduleItems == null){
            activeBirthdateCalendarUseScheduleItems = new ArrayList<SelectItem>();
            activeBirthdateCalendarUseScheduleItems.add(new SelectItem("", ""));
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
            ArrayList<UseScheduleTermDTO> dtos = pDAO.getActiveBirthdateUseScheduleTerms(wgLst);
            for(UseScheduleTermDTO dto : dtos){
                activeBirthdateCalendarUseScheduleItems.add(new SelectItem(dto, dto.getUseScheduleTermName()));
            }
        }
        return activeBirthdateCalendarUseScheduleItems;
    }

    /**
     * @param activeBirthdateCalendarUseScheduleItems the activeBirthdateCalendarUseScheduleItems to set
     */
    public void setActiveBirthdateCalendarUseScheduleItems(List<SelectItem> activeBirthdateCalendarUseScheduleItems) {
        this.activeBirthdateCalendarUseScheduleItems = activeBirthdateCalendarUseScheduleItems;
    }
    
    /**
     * @return the activeUseScheduleItems
     */
    public List<SelectItem> getActiveUseScheduleItems() {
        //Changing to always recreate the list, that way if user adds a use schedule, it will show up 
        //the next time this is used.
        //This list is only used to show all the workgroups as choices for Kaplan Meier, where guests are okay
        //and for ManageUseSchedules, where we have to display all possible choices of use schedules in the search section
        //in order to be compatible with Kaplan Meier. The search results are always limited by the "owner" choices
        //so if a use schedule they are a guest in is chosen, it results in no listing
        //if(activeUseScheduleItems == null){
            activeUseScheduleItems = new ArrayList<SelectItem>();
            activeUseScheduleItems.add(new SelectItem("", ""));
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
            
//            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestOwnerEntityLst");
//            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("colonyManageWorkgroupEntityLst");
            System.out.println("workgroup list:" + wgLst);
            ArrayList<UseScheduleTermDTO> dtos = pDAO.getActiveBirthdateUseScheduleTerms(wgLst);
            for(UseScheduleTermDTO dto : dtos){
                activeUseScheduleItems.add(new SelectItem(dto, dto.getUseScheduleTermName()));
            }
            dtos = pDAO.getActivePlugDateUseScheduleTerms(wgLst);
            for(UseScheduleTermDTO dto : dtos){
                activeUseScheduleItems.add(new SelectItem(dto, dto.getUseScheduleTermName()));
            }
        //}
        return activeUseScheduleItems;
    }

    /**
     * @return the activePlugdateUseScheduleItems
     */
    public List<SelectItem> getActivePlugdateUseScheduleItems() {
        //Changing to always recreate the list, that way if user adds a use schedule, it will show up 
        //the next time this is used.
        //This list is only used to show all the workgroups as choices for Kaplan Meier, where guests are okay
        //and for ManageUseSchedules, where we have to display all possible choices of use schedules in the search section
        //in order to be compatible with Kaplan Meier. The search results are always limited by the "owner" choices
        //so if a use schedule they are a guest in is chosen, it results in no listing
        //if(activePlugdateUseScheduleItems == null){
            activePlugdateUseScheduleItems = new ArrayList<SelectItem>();
            activePlugdateUseScheduleItems.add(new SelectItem("", ""));
            ArrayList<WorkgroupEntity> wgLst = (ArrayList<WorkgroupEntity>) getSessionParameter("guestWorkgroupEntityLst");
            ArrayList<UseScheduleTermDTO> dtos = pDAO.getActivePlugDateUseScheduleTerms(wgLst);
            for(UseScheduleTermDTO dto : dtos){
                activePlugdateUseScheduleItems.add(new SelectItem(dto, dto.getUseScheduleTermName()));
            }
        //}
        return activePlugdateUseScheduleItems;
    }

    /**
     * @param activePlugdateUseScheduleItems the activePlugdateUseScheduleItems to set
     */
    public void setActivePlugdateUseScheduleItems(List<SelectItem> activePlugdateUseScheduleItems) {
        this.activePlugdateUseScheduleItems = activePlugdateUseScheduleItems;
    }

    /**
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * @param userEntity the userEntity to set
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * @return the testTypeFieldFormats
     */
    public List<SelectItem> getTestTypeFieldFormats() {
        testTypeFieldFormats = new ArrayList<SelectItem>();
        testTypeFieldFormats.add(new SelectItem("date", "Date"));
        testTypeFieldFormats.add(new SelectItem("dec", "Decimal"));
        testTypeFieldFormats.add(new SelectItem("int", "Integer"));
        testTypeFieldFormats.add(new SelectItem("text", "Text"));
        
        return testTypeFieldFormats;
    }

    /**
     * @param testTypeFieldFormats the testTypeFieldFormats to set
     */
    public void setTestTypeFieldFormats(List<SelectItem> testTypeFieldFormats) {
        this.testTypeFieldFormats = testTypeFieldFormats;
    }
    
}