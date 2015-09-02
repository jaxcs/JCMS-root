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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jcms.middletier.base.BTBaseObject;
import jcms.middletier.businessobject.CVCacheBO;
import jcms.middletier.facade.FacadeDao;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.colonyManagement.AlleleEntity;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.GeneEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
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
import jcms.integrationtier.dao.StrainDAO;
import jcms.integrationtier.jcmsWeb.CvQueryTypeEntity;
import jcms.integrationtier.jcmsWeb.QueryDefinitionEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.portal.IntegrationTierPortal;
import jcms.middletier.service.SearchAppService;


/**
 * <b>File name:</b>  ListSupportDTO.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Acts as a single transport object for all common controlled
 *      vocabulary lists and common list items.  <p>
 * <b>Overview:</b> Provides business object methods to load sets of data specific
 *      to one user interface.  This way this class doesn't return all controlled
 *      vocabulary and list items at once when only a select few are needed on
 *      the user interface.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class ListSupportDTO extends BTBaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private CVCacheBO                              cvCacheBO                  = new CVCacheBO();

    private List<CvCauseOfDeathEntity>             cvCauseOfDeath             = null;
    private List<CvCoatColorEntity>                cvCoatColor                = null;
    private List<CvContainerStatusEntity>          cvContainerStatus          = null;
    private List<CvDietEntity>                     cvDiet                     = null;
    private List<CvEpochEntity>                    cvEpoch                    = null;
    private List<CvExpSampleLocationEntity>        cvExpSampleLocation        = null;
    private List<CvExpStatusEntity>                cvExpStatus                = null;
    private List<CvFieldOfStudyEntity>             cvFieldOfStudy             = null;
    private List<CvGeneclassEntity>                cvGeneclass                = null;
    private List<CvGenerationEntity>               cvGeneration               = null;
    private List<CvGenotypeSpecimenLocationEntity> cvGenotypeSpecimenLocation = null;
    private List<CvHarvestMethodEntity>            cvHarvestMethod            = null;
    private List<CvHealthLevelEntity>              cvHealthLevel              = null;
    private List<CvKeywordsEntity>                 cvKeywords                 = null;
    private List<CvLocationTypeEntity>             cvLocationType             = null;
    private List<CvMatingCardNotesEntity>          cvMatingCardNotes          = null;
    private List<CvMouseOriginEntity>              cvMouseOrigin              = null;
    private List<CvMouseProtocolEntity>            cvMouseProtocol            = null;
    private List<CvMouseUseEntity>                 cvMouseUse                 = null;
    private List<CvPhenotypeEntity>                cvPhenotype                = null;
    private List<CvPhenotypeEntity>                phenotype                  = null;
    private List<CvPreservationDetailEntity>       cvPreservationDetail       = null;
    private List<CvPreservationMethodEntity>       cvPreservationMethod       = null;
    private List<CvPreservationTypeEntity>         cvPreservationType         = null;
    private List<CvSampleClassEntity>              cvSampleClass              = null;
    private List<CvSampleDateTypeEntity>           cvSampleDateType           = null;
    private List<CvSampleStatusEntity>             cvSampleStatus             = null;
    private List<CvSampleTypeEntity>               cvSampleType               = null;
    private List<CvStorageFacilityEntity>          cvStorageFacility          = null;
    private List<CvStrainTypeEntity>               cvStrainType               = null;
    private List<CvTestStatusEntity>               cvTestStatus               = null;
    private List<CvTimeUnitEntity>                 cvTimeUnit                 = null;
    private List<CvWeightUnitEntity>               cvWeightUnit               = null;
    private List<CvBreedingStatusEntity>           cvBreedingStatus           = null;
    private List<CvCrossstatusEntity>              cvCrossStatus              = null;
    private List<CvSexEntity>                      cvSex                      = null;
    
    private List<MouseEntity>                      mouseID                    = null;
    private List<StrainEntity>                     strain                     = null;
    private List<LifeStatusEntity>                 lifeStatus                 = null;
    private List<OwnerEntity>                      owner                      = null;
    private List<LitterEntity>                     litterID                   = null;
    private List<GeneEntity>                       gene                       = null;
    private List<MatingEntity>                     matingID                   = null;

    private List<ContainerEntity>                  pen                        = null;
    private List<RoomEntity>                       room                       = null;
    private List<QueryDefinitionEntity>            udQueries                  = null;
    
    private List<AlleleEntity>                     Alleles                    = null;

    private ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
    
    public ListSupportDTO() {        
    }

    public ListSupportDTO(ArrayList<OwnerEntity> ownerLst) {
        this.setOwnerLst(ownerLst);
    }
    
    /**
     * @return the Allele
     */
    public List<AlleleEntity> getAllelesByOwners() {
        List<AlleleEntity> alist = new ArrayList<AlleleEntity>();

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAllByOwners(new AlleleEntity(), this.getOwnerLst());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity : table.getList()) {
            alist.add((AlleleEntity) entity);
        }
        return alist;
    }
    
    /**
     * @return the Allele
     */
    public List<AlleleEntity> getAllAlleles() {
        List<AlleleEntity> alist = new ArrayList<AlleleEntity>();

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new AlleleEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity : table.getList()) {
            alist.add((AlleleEntity) entity);
        }
        return alist;
    }

    /**
     * @return the cvCauseOfDeath
     */
    public List<CvCauseOfDeathEntity> getCvCauseOfDeath() {
        cvCauseOfDeath = new ArrayList<CvCauseOfDeathEntity>();

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvCauseOfDeathEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvCauseOfDeath.add((CvCauseOfDeathEntity) entity);
        }
        return cvCauseOfDeath;
    }

    /**
     * @return the cvCoatColor
     */
    public List<CvCoatColorEntity> getCvCoatColor() {
        cvCoatColor = new ArrayList<CvCoatColorEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvCoatColorEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvCoatColor.add((CvCoatColorEntity) entity);
        }
        return cvCoatColor;
    }

    /**
     * @return the cvContainerStatus
     */
    public List<CvContainerStatusEntity> getCvContainerStatus() {
        cvContainerStatus = new ArrayList<CvContainerStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvContainerStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvContainerStatus.add((CvContainerStatusEntity) entity);
        }
        return cvContainerStatus;
    }

    /**
     * @return the cvDiet
     */
    public List<CvDietEntity> getCvDiet() {
        cvDiet = new ArrayList<CvDietEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvDietEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvDiet.add((CvDietEntity) entity);
        }
        return cvDiet;
    }

    /**
     * @return the cvEpoch
     */
    public List<CvEpochEntity> getCvEpoch() {
        cvEpoch = new ArrayList<CvEpochEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvEpochEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvEpoch.add((CvEpochEntity) entity);
        }
        return cvEpoch;
    }

    /**
     * @return the cvExpSampleLocation
     * *****NOTE: JCMS does not have this table*****
     */
    public List<CvExpSampleLocationEntity> getCvExpSampleLocation() {
        cvExpSampleLocation = new ArrayList<CvExpSampleLocationEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvExpSampleLocationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvExpSampleLocation.add((CvExpSampleLocationEntity) entity);
        }
        return cvExpSampleLocation;
    }

    /**
     * @return the cvExpStatus
     */
    public List<CvExpStatusEntity> getCvExpStatus() {
        cvExpStatus = new ArrayList<CvExpStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvExpStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvExpStatus.add((CvExpStatusEntity) entity);
        }
        return cvExpStatus;
    }

    /**
     * @return the cvFieldOfStudy
     */
    public List<CvFieldOfStudyEntity> getCvFieldOfStudy() {
        cvFieldOfStudy = new ArrayList<CvFieldOfStudyEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvFieldOfStudyEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvFieldOfStudy.add((CvFieldOfStudyEntity) entity);
        }
        return cvFieldOfStudy;
    }

    /**
     * @return the cvGeneclass
     */
    public List<CvGeneclassEntity> getCvGeneclass() {
        cvGeneclass = new ArrayList<CvGeneclassEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvGeneclassEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvGeneclass.add((CvGeneclassEntity) entity);
        }
        return cvGeneclass;
    }

    /**
     * @return the cvGeneration
     */
    public List<CvGenerationEntity> getCvGeneration() {
        cvGeneration = new ArrayList<CvGenerationEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvGenerationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvGeneration.add((CvGenerationEntity) entity);
        }
        return cvGeneration;
    }

    /**
     * @return the cvGenotypeSpecimenLocation
     */
    public List<CvGenotypeSpecimenLocationEntity> getCvGenotypeSpecimenLocation() {
        cvGenotypeSpecimenLocation = new ArrayList<CvGenotypeSpecimenLocationEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvGenotypeSpecimenLocationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvGenotypeSpecimenLocation.add((CvGenotypeSpecimenLocationEntity) entity);
        }
        return cvGenotypeSpecimenLocation;
    }

    /**
     * @return the cvHarvestMethod
     */
    public List<CvHarvestMethodEntity> getCvHarvestMethod() {
        cvHarvestMethod = new ArrayList<CvHarvestMethodEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvHarvestMethodEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvHarvestMethod.add((CvHarvestMethodEntity) entity);
        }
        return cvHarvestMethod;
    }

    /**
     * @return the cvHealthLevel
     */
    public List<CvHealthLevelEntity> getCvHealthLevel() {
        cvHealthLevel = new ArrayList<CvHealthLevelEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().baseFindAll(new CvHealthLevelEntity());
        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvHealthLevel.add((CvHealthLevelEntity) entity);
        }
        return cvHealthLevel;
    }

    /**
     * @return the cvKeywords
     */
    public List<CvKeywordsEntity> getCvKeywords() {
        cvKeywords = new ArrayList<CvKeywordsEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvKeywordsEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvKeywords.add((CvKeywordsEntity) entity);
        }
        return cvKeywords;
    }

    /**
     * @return the cvLocationType
     */
    public List<CvLocationTypeEntity> getCvLocationType() {
        cvLocationType = new ArrayList<CvLocationTypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvLocationTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvLocationType.add((CvLocationTypeEntity) entity);
        }
        return cvLocationType;
    }

    /**
     * @return the cvMatingCardNotes
     */
    public List<CvMatingCardNotesEntity> getCvMatingCardNotes() {
        cvMatingCardNotes = new ArrayList<CvMatingCardNotesEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvMatingCardNotesEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMatingCardNotes.add((CvMatingCardNotesEntity) entity);
        }
        return cvMatingCardNotes;
    }

    /**
     * @return the cvMouseOrigin
     */
    public List<CvMouseOriginEntity> getCvMouseOrigin() {
        cvMouseOrigin = new ArrayList<CvMouseOriginEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvMouseOriginEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMouseOrigin.add((CvMouseOriginEntity) entity);
        }
        return cvMouseOrigin;
    }

    /**
     * @return the cvMouseProtocol
     */
    //NOTE NOTE NOTE - THIS USES AN OWNER FILTER THAT IS NOT RIGHT AND
    //THEREFORE SHOULD NOT BE USED.
    public List<CvMouseProtocolEntity> getCvMouseProtocol() {
        cvMouseProtocol = new ArrayList<CvMouseProtocolEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAllByOwners(new CvMouseProtocolEntity(), ownerLst);
            //.baseFindAll(new CvMouseProtocolEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMouseProtocol.add((CvMouseProtocolEntity) entity);
        }
        return cvMouseProtocol;
    }

    /**
     * @return the cvMouseProtocol
     */
    public List<CvMouseProtocolEntity> getAllCvMouseProtocol() {
        cvMouseProtocol = new ArrayList<CvMouseProtocolEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new CvMouseProtocolEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMouseProtocol.add((CvMouseProtocolEntity) entity);
        }
        return cvMouseProtocol;
    }  

    /**
     * @return the cvMouseUse
     */
    public List<CvMouseUseEntity> getCvMouseUse() {
        cvMouseUse = new ArrayList<CvMouseUseEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().baseFindAll(new CvMouseUseEntity());
                //.baseFindAll(new CvMouseUseEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMouseUse.add((CvMouseUseEntity) entity);
        }
        return cvMouseUse;
    }

    /**
     * @return the cvMouseUse
     */
    public List<CvMouseUseEntity> getAllCvMouseUse() {
        cvMouseUse = new ArrayList<CvMouseUseEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new CvMouseUseEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvMouseUse.add((CvMouseUseEntity) entity);
        }
        return cvMouseUse;
    }

        /**
     * @return the cvPhenotype
     */
    public List<CvPhenotypeEntity> getCvPhenotype() {
        cvPhenotype = new ArrayList<CvPhenotypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().baseFindAll(new CvPhenotypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvPhenotype.add((CvPhenotypeEntity) entity);
        }
        return cvPhenotype;
    }

    /**
     * @return the cvPhenotype
     */
    public List<CvPhenotypeEntity> getAllCvPhenotype() {
        cvPhenotype = new ArrayList<CvPhenotypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new CvPhenotypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvPhenotype.add((CvPhenotypeEntity) entity);
        }
        return cvPhenotype;
    }
    
    /**
     * @return the cvPreservationDetail
     */
    public List<CvPreservationDetailEntity> getCvPreservationDetail() {
        cvPreservationDetail = new ArrayList<CvPreservationDetailEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvPreservationDetailEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvPreservationDetail.add((CvPreservationDetailEntity) entity);
        }
        return cvPreservationDetail;
    }

    /**
     * @return the cvPreservationMethod
     */
    public List<CvPreservationMethodEntity> getCvPreservationMethod() {
        cvPreservationMethod = new ArrayList<CvPreservationMethodEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvPreservationMethodEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvPreservationMethod.add((CvPreservationMethodEntity) entity);
        }
        return cvPreservationMethod;
    }

    /**
     * @return the cvPreservationType
     */
    public List<CvPreservationTypeEntity> getCvPreservationType() {
        cvPreservationType = new ArrayList<CvPreservationTypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvPreservationTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvPreservationType.add((CvPreservationTypeEntity) entity);
        }
        return cvPreservationType;
    }

    /**
     * @return the cvSampleClass
     */
    public List<CvSampleClassEntity> getCvSampleClass() {
        cvSampleClass = new ArrayList<CvSampleClassEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvSampleClassEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvSampleClass.add((CvSampleClassEntity) entity);
        }
        return cvSampleClass;
    }

    /**
     * @return the cvSampleDateType
     */
    public List<CvSampleDateTypeEntity> getCvSampleDateType() {
        cvSampleDateType = new ArrayList<CvSampleDateTypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvSampleDateTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvSampleDateType.add((CvSampleDateTypeEntity) entity);
        }
        return cvSampleDateType;
    }

    /**
     * @return the cvSampleStatus
     */
    public List<CvSampleStatusEntity> getCvSampleStatus() {
        cvSampleStatus = new ArrayList<CvSampleStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvSampleStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvSampleStatus.add((CvSampleStatusEntity) entity);
        }
        return cvSampleStatus;
    }

    /**
     * @return the cvSampleType
     */
    public List<CvSampleTypeEntity> getCvSampleType() {
        cvSampleType = new ArrayList<CvSampleTypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvSampleTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvSampleType.add((CvSampleTypeEntity) entity);
        }
        return cvSampleType;
    }

    /**
     * @return the cvStorageFacility
     */
    public List<CvStorageFacilityEntity> getCvStorageFacility() {
        cvStorageFacility = new ArrayList<CvStorageFacilityEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvStorageFacilityEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvStorageFacility.add((CvStorageFacilityEntity) entity);
        }
        return cvStorageFacility;
    }

    /**
     * @return the cvStrainType
     */
    public List<CvStrainTypeEntity> getCvStrainType() {
        cvStrainType = new ArrayList<CvStrainTypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvStrainTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvStrainType.add((CvStrainTypeEntity) entity);
        }
        return cvStrainType;
    }

    /**
     * @return the cvTestStatus
     */
    public List<CvTestStatusEntity> getCvTestStatus() {
        cvTestStatus = new ArrayList<CvTestStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvTestStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvTestStatus.add((CvTestStatusEntity) entity);
        }
        return cvTestStatus;
    }

    /**
     * @return the cvTimeUnit
     */
    public List<CvTimeUnitEntity> getCvTimeUnit() {
        cvTimeUnit = new ArrayList<CvTimeUnitEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvTimeUnitEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvTimeUnit.add((CvTimeUnitEntity) entity);
        }
        return cvTimeUnit;
    }

    /**
     * @return the cvWeightUnit
     */
    public List<CvWeightUnitEntity> getCvWeightUnit() {
        cvWeightUnit = new ArrayList<CvWeightUnitEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvWeightUnitEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvWeightUnit.add((CvWeightUnitEntity) entity);
        }
        return cvWeightUnit;
    }

    /**
     * @return the cvBreedingStatus
     */
    public List<CvBreedingStatusEntity> getCvBreedingStatus() {
        cvBreedingStatus = new ArrayList<CvBreedingStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvBreedingStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvBreedingStatus.add((CvBreedingStatusEntity) entity);
        }
        return cvBreedingStatus;
    }
    
    /**
     * @return the cvBreedingStatus
     */
    public List<CvCrossstatusEntity> getCvCrossStatus() {
        cvCrossStatus = new ArrayList<CvCrossstatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new CvCrossstatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvCrossStatus.add((CvCrossstatusEntity) entity);
        }
        return cvCrossStatus;
    }

    /**
     * @return the cvSex
     */
    public List<CvSexEntity> getCvSex() {
        cvSex = new ArrayList<CvSexEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvSexEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvSex.add((CvSexEntity) entity);
        }
        return cvSex;
    }

    /**
     * @return the mouseID
     */
    public List<MouseEntity> getMouseID() {
        mouseID = new ArrayList<MouseEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().
                baseFindAllByOwners(new MouseEntity(), this.getOwnerLst());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            mouseID.add((MouseEntity) entity);
        }
        return mouseID;
    }

    /**
     * @return the mouseID
     */
    public List<MouseEntity> getAllMouseIDs() {
        mouseID = new ArrayList<MouseEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().
                baseFindAll(new MouseEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            mouseID.add((MouseEntity) entity);
        }
        return mouseID;
    }

    /**
     * @return the strain
     */
    public List<StrainEntity> getMatingStrains(UserEntity userEntity) throws Exception {
        if(strain == null){
            strain = new ArrayList<StrainEntity>();
            StrainDAO strainDAO = new StrainDAO(userEntity);
            ITBaseEntityTable table = strainDAO.getMatingStrainsByOwner(this.getOwnerLst());

            for (ITBaseEntityInterface entity: table.getList())
            {
                strain.add((StrainEntity) entity);
            }
        }
        return strain;
    }

    /**
     * @return the strain
     */
    public List<StrainEntity> getMouseStrains(UserEntity userEntity) throws Exception {
        strain = new ArrayList<StrainEntity>();
        StrainDAO strainDAO = new StrainDAO(userEntity);
        ITBaseEntityTable table = strainDAO.getMouseStrainsByOwner(this.getOwnerLst());

        for (ITBaseEntityInterface entity: table.getList())
        {
            strain.add((StrainEntity) entity);
        }
        return strain;
    }

    /**
     * @return the strain
     */
    public List<StrainEntity> getAllStrains(UserEntity userEntity) throws Exception {
        strain = new ArrayList<StrainEntity>();
        StrainDAO strainDAO = new StrainDAO(userEntity);
        ITBaseEntityTable table = strainDAO.getAllStrains();

        for (ITBaseEntityInterface entity: table.getList())
        {
            strain.add((StrainEntity) entity);
        }

        return strain;        
    }
    
    /**
     * @return the strain
     */
    public List<StrainEntity> getActiveStrains(UserEntity userEntity) throws Exception {
        strain = new ArrayList<StrainEntity>();
        StrainDAO strainDAO = new StrainDAO(userEntity);
        ITBaseEntityTable table = strainDAO.getActiveStrains();

        for (ITBaseEntityInterface entity: table.getList())
        {
            strain.add((StrainEntity) entity);
        }

        return strain;
    }
    
    /**
     * @return the strain
     */
    public List<StrainEntity> getApprovedLitterStrains(int damKey, int sireKey, UserEntity userEntity) throws Exception {
        strain = new ArrayList<StrainEntity>();
        StrainDAO strainDAO = new StrainDAO(userEntity);

        ITBaseEntityTable table = strainDAO.getApprovedLitterStrains(damKey, sireKey);

        for (ITBaseEntityInterface entity: table.getList())
        {
            strain.add((StrainEntity) entity);
        }

        return strain;
    }
    
    /**
     * @return the strain
     */
    public List<AlleleEntity> getAllelesByGene(int geneKey) throws Exception {
        Alleles = new ArrayList<AlleleEntity>();

        ITBaseEntityTable table = new SearchAppService().
                findAllelesByGene(geneKey);

        for (ITBaseEntityInterface entity: table.getList())
        {
            Alleles.add((AlleleEntity) entity);
        }
        return Alleles;
    }

    /**
     * @return the lifeStatus
     */
    public List<LifeStatusEntity> getLifeStatus() {
        lifeStatus = new ArrayList<LifeStatusEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new LifeStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            lifeStatus.add((LifeStatusEntity) entity);
        }
        return lifeStatus;
    }
    
     /** 
     * @return the phenotype
     */
    public List<CvPhenotypeEntity> getPhenotype() {
        phenotype = new ArrayList<CvPhenotypeEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new CvPhenotypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            phenotype.add((CvPhenotypeEntity) entity);
        }
        return phenotype;
    }

    
    /**
     * @return the owner
     */
    public List<OwnerEntity> getOwner() {
        owner = new ArrayList<OwnerEntity>();            
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().baseFindAllByOwners(new OwnerEntity(), this.getOwnerLst());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            owner.add((OwnerEntity) entity);
        }
        return owner;
    }

    /**
     * @return the owner
     */
    public List<OwnerEntity> getAllOwners() {
        owner = new ArrayList<OwnerEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new OwnerEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            owner.add((OwnerEntity) entity);
        }
        return owner;
    }

    /**
     * @return the litterID
     */
    public List<LitterEntity> getLitterID() {
        litterID = new ArrayList<LitterEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new LitterEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            litterID.add((LitterEntity) entity);
        }
        return litterID;
    }

    /**
     * @return the litterID
     */
    public List<LitterEntity> getAllLitterIDs() {
        litterID = new ArrayList<LitterEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new LitterEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            litterID.add((LitterEntity) entity);
        }
        return litterID;
    }

    /**
     * @return the gene
     */
    public List<GeneEntity> getGene() {

            gene = new ArrayList<GeneEntity>();
            List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                    .baseFindAllByOwners(new GeneEntity(), this.getOwnerLst());

            ITBaseEntityTable table = new ITBaseEntityTable();
            table.setList(list);

            for (ITBaseEntityInterface entity: table.getList())
            {
                gene.add((GeneEntity) entity);
            }
        return gene;
    }

    /**
     * @return the gene
     */
    public List<GeneEntity> getAllGenes() {
            gene = new ArrayList<GeneEntity>();
            List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                    .baseFindAll(new GeneEntity());

            ITBaseEntityTable table = new ITBaseEntityTable();
            table.setList(list);

            for (ITBaseEntityInterface entity: table.getList())
            {
                gene.add((GeneEntity) entity);
            }
        return gene;
    }

    /**
     * @return the matingID
     */
    public List<MatingEntity> getMatingID() {
        matingID = new ArrayList<MatingEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAllByOwners(new MatingEntity(), this.getOwnerLst());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            matingID.add((MatingEntity) entity);
        }
        return matingID;
    }

    /**
     * @return the matingID
     */
    public List<MatingEntity> getAllMatingIDs() {
        matingID = new ArrayList<MatingEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new MatingEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            matingID.add((MatingEntity) entity);
        }
        return matingID;
    }

    /**
     * @return the pen
     */
    public List<ContainerEntity> getPen() {
        pen = new ArrayList<ContainerEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAllByOwners(new ContainerEntity(), this.getOwnerLst());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            pen.add((ContainerEntity) entity);
        }
        return pen;
    }

    /**
     * @return the pen
     */
    public List<ContainerEntity> getAllPens() {
        pen = new ArrayList<ContainerEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
                .baseFindAll(new ContainerEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            pen.add((ContainerEntity) entity);
        }
        return pen;
    }

    /**
     * @return the room
     */
    public List<RoomEntity> getRoom() {
        room = new ArrayList<RoomEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal()
            .baseFindAll(new RoomEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            room.add((RoomEntity) entity);
        }
        return room;
    }
    
    /**
     * @return the DBSetupVaraibles
     */
    public List<DbsetupEntity> getSetUpVariables() {
        List<DbsetupEntity> vars = new ArrayList<DbsetupEntity>();

        vars = new ArrayList<DbsetupEntity>();
        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeLocal().
                baseFindAll(new DbsetupEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity : table.getList()) {
            vars.add((DbsetupEntity) entity);
        }

        return vars;
    }

    /**
     * @return the dbInfo
     */
    public List<JCMSDbInfoEntity> getJCMSDbInfo() {
        List<JCMSDbInfoEntity> dbInfo = new ArrayList<JCMSDbInfoEntity>();

        List<ITBaseEntityInterface> list = new FacadeDao().
                getVocabularyFacadeLocal().baseFindAll(new JCMSDbInfoEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity : table.getList()) {
            dbInfo.add((JCMSDbInfoEntity) entity);
        }

        return dbInfo;
    }

    /**
     * @return the UserDefinedQueries
     */
    public List<QueryDefinitionEntity> getUserDefinedQueriesByUser(Integer key) throws Exception {
        udQueries = new ArrayList<QueryDefinitionEntity>();

        List<ITBaseEntityInterface> list = new IntegrationTierPortal().
                getJCMSWebSystemFacadeLocal().baseFindAllByUser(new
                QueryDefinitionEntity(), key);

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList()) {
            udQueries.add((QueryDefinitionEntity) entity);
        }
        return udQueries;
    }
    
    /**
     * @return the UserDefinedQueries
     */
    public List<QueryDefinitionEntity> getUserDefinedQueriesByWorkgroups(ArrayList<WorkgroupEntity> wgList) throws Exception {
        udQueries = new ArrayList<QueryDefinitionEntity>();
        List<ITBaseEntityInterface> list = new IntegrationTierPortal().getJCMSWebSystemFacadeLocal().baseFindAllByWorkgroups(new QueryDefinitionEntity(), wgList);
        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList()) {
            udQueries.add((QueryDefinitionEntity) entity);
        }
        return udQueries;
    }

    /**
     * @return the UserDefinedQueries
     */
    public List<QueryDefinitionEntity> getUserDefinedQueries() throws Exception {
        udQueries = new ArrayList<QueryDefinitionEntity>();

        List<ITBaseEntityInterface> list = new IntegrationTierPortal().
                getJCMSWebSystemFacadeLocal().
                baseFindAll(new QueryDefinitionEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            udQueries.add((QueryDefinitionEntity) entity);
        }
        return udQueries;
    }

    /**
     * @return the UserDefinedQueries
     */
    public List<CvQueryTypeEntity> getCvUserDefinedQueryType() throws Exception {
        List<CvQueryTypeEntity> cvQueries = new ArrayList<CvQueryTypeEntity>();
        List<ITBaseEntityInterface> list = new IntegrationTierPortal().getJCMSWebSystemFacadeLocal().baseFindAll(new CvQueryTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        for (ITBaseEntityInterface entity: table.getList())
        {
            cvQueries.add((CvQueryTypeEntity) entity);
        }
        return cvQueries;
    }
    
    /**
     * @return the ownerLst
     */
    public ArrayList<OwnerEntity> getOwnerLst() {
        return ownerLst;
    }

    /**
     * @param ownerLst the ownerLst to set
     */
    public void setOwnerLst(ArrayList<OwnerEntity> ownerLst) {
        this.ownerLst = ownerLst;
    }    

}