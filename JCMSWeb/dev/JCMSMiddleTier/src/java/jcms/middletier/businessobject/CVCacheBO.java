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

import java.io.Serializable;
import java.util.List;
import jcms.middletier.base.BTBaseObject;
import jcms.middletier.facade.FacadeDao;
import jcms.middletier.utility.CVCache;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.base.ITBaseEntityTable;
import jcms.integrationtier.cv.CvCauseOfDeathEntity;
import jcms.integrationtier.cv.CvCoatColorEntity;
import jcms.integrationtier.cv.CvContainerStatusEntity;
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
import jcms.integrationtier.cv.CvPreservationDetailEntity;
import jcms.integrationtier.cv.CvPreservationMethodEntity;
import jcms.integrationtier.cv.CvPreservationTypeEntity;
import jcms.integrationtier.cv.CvSampleClassEntity;
import jcms.integrationtier.cv.CvSampleDateTypeEntity;
import jcms.integrationtier.cv.CvSampleStatusEntity;
import jcms.integrationtier.cv.CvSampleTypeEntity;
import jcms.integrationtier.cv.CvStorageFacilityEntity;
import jcms.integrationtier.cv.CvStrainTypeEntity;
import jcms.integrationtier.cv.CvTestStatusEntity;
import jcms.integrationtier.cv.CvTimeUnitEntity;
import jcms.integrationtier.cv.CvWeightUnitEntity;
import jcms.integrationtier.portal.IntegrationTierPortal;

/**
 * <b>File name:</b>  CvCacheBO.java  <p>
 * <b>RsDate developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Business Object provides access to controlled vocabulary CACHE.  <p>
 * <b>Overview:</b>  The idea is to cache all controlled vocabulary for fast
 *      web tier response time.  Controlled vocabulary is requested for all
 *      drop down selection boxes on a web page.  Web pages may also have
 *      hundreds of records displayed on a page that also need lists of
 *      vocabulary.  This class design is in place to mitigate long response
 *      times building a web form.  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  All controlled vocabulary classes inherit <code>ITBaseEntityTable</code>.
 *      All result sets are returned as ancestor object <code>ITBaseEntityTable</code>.
 *      There are internal methods to extract the proper controlled vocabulary list.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
public class CVCacheBO extends BTBaseObject implements Serializable
{
    private static final long serialVersionUID = 1L;

    public CVCacheBO()
    {
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvLocationTypeEntity</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvLocationTypeEntity</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvCauseOfDeath()
    {
        if (CVCache.getCvCauseOfDeath() != null)
        {
            return CVCache.getCvCauseOfDeath();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvCauseOfDeathEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvCauseOfDeath(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvLocationTypeEntity</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvLocationTypeEntity</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvCoatColor()
    {
        if (CVCache.getCvCoatColor() != null)
        {
            return CVCache.getCvCoatColor();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvCoatColorEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvCoatColor(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>cvContainerStatus</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>cvContainerStatus</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvContainerStatus()
    {
        if (CVCache.getCvContainerStatus() != null)
        {
            return CVCache.getCvContainerStatus();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvContainerStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvContainerStatus(table);

        return table;
    }
    
    /**
     *  <b>Purpose:</b>  Get all <code>CvDiet</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvDiet</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvDiet()
    {
        if (CVCache.getCvDiet() != null)
        {
            return CVCache.getCvDiet();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvDietEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvDiet(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvEpoch</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvEpoch</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvEpoch()
    {
        if (CVCache.getCvEpoch() != null)
        {
            return CVCache.getCvEpoch();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvEpochEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvEpoch(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvExpSampleLocation</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvExpSampleLocation</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    //*****NOTE: This is not a table used in JCMS*****
    public ITBaseEntityTable getCvExpSampleLocation()
    {
        if (CVCache.getCvExpSampleLocation() != null)
        {
            return CVCache.getCvExpSampleLocation();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvExpSampleLocationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvExpSampleLocation(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvExpStatus</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvExpStatus</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvExpStatus()
    {
        if (CVCache.getCvExpStatus() != null)
        {
            return CVCache.getCvExpStatus();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvExpStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvExpStatus(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvFieldOfStudy</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvFieldOfStudy</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvFieldOfStudy()
    {
        if (CVCache.getCvFieldOfStudy() != null)
        {
            return CVCache.getCvFieldOfStudy();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvFieldOfStudyEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvFieldOfStudy(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvGeneclass</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvGeneclass</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvGeneclass()
    {
        if (CVCache.getCvGeneclass() != null)
        {
            return CVCache.getCvGeneclass();
        }

        List<ITBaseEntityInterface> list = new IntegrationTierPortal().
                getSystemFacadeRemote().baseFindAll(new CvGeneclassEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvGeneclass(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvGeneration</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvGeneration</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvGeneration()
    {
        if (CVCache.getCvGeneration() != null)
        {
            return CVCache.getCvGeneration();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new CvGenerationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvGeneration(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvGenotypeSpecimenLocation</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvGenotypeSpecimenLocation</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvGenotypeSpecimenLocation()
    {
        if (CVCache.getCvGenotypeSpecimenLocation() != null)
        {
            return CVCache.getCvGenotypeSpecimenLocation();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote().
                baseFindAll(new CvGenotypeSpecimenLocationEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvGenotypeSpecimenLocation(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvHarvestMethod</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvHarvestMethod</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvHarvestMethod()
    {
        if (CVCache.getCvHarvestMethod() != null)
        {
            return CVCache.getCvHarvestMethod();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvHarvestMethodEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvHarvestMethod(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvHealthLevel</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvHealthLevel</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvHealthLevel()
    {
        if (CVCache.getCvHealthLevel() != null)
        {
            return CVCache.getCvHealthLevel();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvHealthLevelEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvHealthLevel(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvKeywords</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvKeywords</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvKeywords()
    {
        if (CVCache.getCvKeywords() != null)
        {
            return CVCache.getCvKeywords();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvKeywordsEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvKeywords(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvLocationType</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvLocationType</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvLocationTypeEntity></code>
     */
    public ITBaseEntityTable getCvLocationType()
    {
        if (CVCache.getCvLocationType() != null)
        {
            return CVCache.getCvLocationType();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvLocationTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvLocationType(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvMatingCardNotes</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvMatingCardNotes</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvMatingCardNotesEntity></code>
     */
    public ITBaseEntityTable getCvMatingCardNotes()
    {
        if (CVCache.getCvMatingCardNotes() != null)
        {
            return CVCache.getCvMatingCardNotes();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvMatingCardNotesEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvMatingCardNotes(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvMouseOrigin</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvMouseOrigin</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvMouseOriginEntity></code>
     */
    public ITBaseEntityTable getCvMouseOrigin()
    {
        if (CVCache.getCvMouseOrigin() != null)
        {
            return CVCache.getCvMouseOrigin();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvMouseOriginEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvMouseOrigin(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvMouseProtocol</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvMouseProtocol</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvMouseProtocolEntity></code>
     */
    public ITBaseEntityTable getCvMouseProtocol()
    {
        if (CVCache.getCvMouseProtocol() != null)
        {
            return CVCache.getCvMouseProtocol();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvMouseProtocolEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvMouseProtocol(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvMouseUse</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvMouseUse</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvMouseUseEntity></code>
     */
    public ITBaseEntityTable getCvMouseUse()
    {
        if (CVCache.getCvMouseUse() != null)
        {
            return CVCache.getCvMouseUse();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvMouseUseEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvMouseUse(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvPreservationDetail</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvPreservationDetail</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvPreservationDetailEntity></code>
     */
    public ITBaseEntityTable getCvPreservationDetail()
    {
        if (CVCache.getCvPreservationDetail() != null)
        {
            return CVCache.getCvPreservationDetail();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvPreservationDetailEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvPreservationDetail(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvPreservationMethod</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvPreservationMethod</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvPreservationMethodEntity></code>
     */
    public ITBaseEntityTable getCvPreservationMethod()
    {
        if (CVCache.getCvPreservationMethod() != null)
        {
            return CVCache.getCvPreservationMethod();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvPreservationMethodEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvPreservationMethod(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvPreservationType</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvPreservationType</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvPreservationTypeEntity></code>
     */
    public ITBaseEntityTable getCvPreservationType()
    {
        if (CVCache.getCvPreservationType() != null)
        {
            return CVCache.getCvPreservationType();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvPreservationTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvPreservationType(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvSampleClass</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvSampleClass</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvSampleClassEntity></code>
     */
    public ITBaseEntityTable getCvSampleClass()
    {
        if (CVCache.getCvSampleClass() != null)
        {
            return CVCache.getCvSampleClass();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvSampleClassEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvSampleClass(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvSampleDateType</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvSampleDateType</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvSampleDateTypeEntity></code>
     */
    public ITBaseEntityTable getCvSampleDateType()
    {
        if (CVCache.getCvSampleDateType() != null)
        {
            return CVCache.getCvSampleDateType();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvSampleDateTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvSampleDateType(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvSampleStatus</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvSampleStatus</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvSampleStatusEntity></code>
     */
    public ITBaseEntityTable getCvSampleStatus()
    {
        if (CVCache.getCvSampleStatus() != null)
        {
            return CVCache.getCvSampleStatus();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvSampleStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvSampleStatus(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvSampleType</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvSampleType</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvSampleTypeEntity></code>
     */
    public ITBaseEntityTable getCvSampleType()
    {
        if (CVCache.getCvSampleType() != null)
        {
            return CVCache.getCvSampleType();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvSampleTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvSampleType(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvStorageFacility</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvStorageFacility</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvStorageFacilityEntity></code>
     */
    public ITBaseEntityTable getCvStorageFacility()
    {
        if (CVCache.getCvStorageFacility() != null)
        {
            return CVCache.getCvStorageFacility();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvStorageFacilityEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvStorageFacility(table);

        return table;
    }


    /**
     *  <b>Purpose:</b>  Get all <code>CvStrainType</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvStrainType</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvStrainTypeEntity></code>
     */
    public ITBaseEntityTable getCvStrainType()
    {
        if (CVCache.getCvStrainType() != null)
        {
            return CVCache.getCvStrainType();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvStrainTypeEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvStrainType(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvTestStatus</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvTestStatus</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvTestStatusEntity></code>
     */
    public ITBaseEntityTable getCvTestStatus()
    {
        if (CVCache.getCvTestStatus() != null)
        {
            return CVCache.getCvTestStatus();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvTestStatusEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvTestStatus(table);

        return table;
    }

    /**
     *  <b>Purpose:</b>  Get all <code>CvTimeUnitEntity</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvTimeUnitEntity</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvTimeUnitEntity></code>
     */
    public ITBaseEntityTable getCvTimeUnit()
    {
        if (CVCache.getCvTimeUnit() != null)
        {
            return CVCache.getCvTimeUnit();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvTimeUnitEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvTimeUnit(table);

        return table;
    }    
    
    /**
     *  <b>Purpose:</b>  Get all <code>CvWeightUnit</code> rows. <br>
     *  <b>Overview:</b>  Get all <code>CvTWeightUnit</code> entity from the
     *                    current persistent context. <br>
     * @return ITBaseEntityTable return <code>ITBaseEntityTable<CvWeightUnitEntity></code>
     */
    public ITBaseEntityTable getCvWeightUnit()
    {
        if (CVCache.getCvWeightUnit() != null)
        {
            return CVCache.getCvWeightUnit();
        }

        List<ITBaseEntityInterface> list = new FacadeDao().getVocabularyFacadeRemote()
                .baseFindAll(new CvWeightUnitEntity());

        ITBaseEntityTable table = new ITBaseEntityTable();
        table.setList(list);

        CVCache.setCvWeightUnit(table);

        return table;
    }    
}