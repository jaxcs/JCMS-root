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

package jcms.integrationtier.dao;

import java.util.ArrayList;
import java.util.SortedMap;
import jcms.integrationtier.dto.SampleClassDTO;
import jcms.integrationtier.dto.SampleTypeDTO;
import jcms.integrationtier.dto.SampleDateTypeDTO;
import jcms.integrationtier.dto.HarvestMethodDTO;
import jcms.integrationtier.dto.PreservationDetailDTO;
import jcms.integrationtier.dto.PreservationMethodDTO;
import jcms.integrationtier.dto.PreservationTypeDTO;
import jcms.integrationtier.dto.SampleStatusDTO;
import jcms.integrationtier.dto.TimeUnitDTO;
import jcms.integrationtier.dto.WeightUnitDTO;
import jcms.integrationtier.dto.EpochDTO;
import jcms.integrationtier.dto.SampleXtraDTO;
import jcms.integrationtier.dto.SampleLinkDTO;
import jcms.integrationtier.dto.StorageDTO;
import jcms.integrationtier.dto.LocationDTO;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.integrationtier.colonyManagement.MatingEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.LitterEntity;
import jcms.integrationtier.colonyManagement.StrainEntity;
import java.text.SimpleDateFormat;
import jcms.integrationtier.base.SystemDao;
import jcms.integrationtier.dto.SampleSearchDTO;

/**
 *
 * @author mkamato
 */
public class SampleDAO extends MySQLDAO{
    /*
     * Need to return information from epoch, harvest method, weight unitm sample 
     * date type, sample status, time unit, sample class, sample type, preservation and location from the database
     */
    
    SystemDao dao = new SystemDao();
    
    private String baseShowExistingSampleParentSample = "SELECT * FROM Sample WHERE _parentSample_key != 0 ";
    
    private String showExistingSampleMouse = "SELECT * FROM Sample "
                                                +"JOIN MouseSample " 
                                                +"ON Sample._sample_key = MouseSample._sample_key ";
    
    private String showExistingSampleLitter = "SELECT * FROM Sample "
                                                +"JOIN LitterSample " 
                                                +"ON Sample._sample_key = LitterSample._sample_key ";
    
    private String showExistingSampleMating = "SELECT * FROM Sample "
                                                +"JOIN MatingSample " 
                                                +"ON Sample._sample_key = MatingSample._sample_key ";
    
    private String showExistingSampleOther = "SELECT * FROM Sample "
                                            + "LEFT JOIN MatingSample  "
                                            + "ON Sample._sample_key = MatingSample._sample_key "
                                            + "LEFT JOIN MouseSample  "
                                            + "ON Sample._sample_key = MouseSample._sample_key "
                                            + "LEFT JOIN LitterSample "
                                            + "ON Sample._sample_key = LitterSample._sample_key "
                                            + "WHERE _parentSample_key = 0 "
                                            + "AND MatingSample._mating_key IS null "
                                            + "AND MouseSample._mouse_key IS null "
                                            + "AND LitterSample._litter_key IS null ";
    
    private ArrayList<String> referencedLocations = new ArrayList<String>();
    
    public ArrayList<SampleXtraDTO> showExistingSamplesByMouse(ArrayList<MouseEntity> list){
        ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
        SortedMap[] rows;
        if(!list.isEmpty()){
            String query = showExistingSampleMouse + "AND (";
            for(MouseEntity me : list){
                query = query + "_mouse_key = " + me.getMouseKey().toString();
                if(list.indexOf(me) != list.size() -1){
                    query = query + " OR ";
                }
            }            
            query = query + ")";
            System.out.println(query);
            rows = this.executeJCMSQuery(query).getRows(); 
        }       
        else{
            rows = this.executeJCMSQuery(showExistingSampleMouse).getRows(); 
        }
        for(SortedMap sm : rows){
            SampleXtraDTO dto = new SampleXtraDTO();
            dto.setSampleID(myGet("sampleID",sm));
            dto.setHarvestMethod(myGet("harvestMethod",sm));
            dto.setAge(myGet("age",sm));
            dto.setOwner(myGet("owner",sm));
            dto.setWeight(myGet("weight",sm));
            samples.add(dto);
        }
        return samples;
    }
    
    public ArrayList<SampleXtraDTO> showExistingSamplesByMating(ArrayList<MatingEntity> list){
        ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
        SortedMap[] rows;
        if(!list.isEmpty()){
            String query = showExistingSampleMating + "AND (";
            for(MatingEntity me : list){
                query = query + "_mating_key = " + me.getMatingKey().toString();
                if(list.indexOf(me) != list.size() -1){
                    query = query + " OR ";
                }
            }            
            query = query + ")";
            System.out.println(query);
            rows = this.executeJCMSQuery(query).getRows(); 
        }       
        else{
            rows = this.executeJCMSQuery(showExistingSampleMating).getRows(); 
        }
        for(SortedMap sm : rows){
            SampleXtraDTO dto = new SampleXtraDTO();
            dto.setSampleID(myGet("sampleID",sm));
            dto.setHarvestMethod(myGet("harvestMethod",sm));
            dto.setAge(myGet("age",sm));
            dto.setOwner(myGet("owner",sm));
            dto.setWeight(myGet("weight",sm));
            samples.add(dto);
        }
        return samples;
    }
    
    public ArrayList<SampleXtraDTO> showExistingSamplesByLitter(ArrayList<LitterEntity> list){
        ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
        SortedMap[] rows;
        if(!list.isEmpty()){
            String query = showExistingSampleLitter + "AND (";
            for(LitterEntity le : list){
                query = query + "_litter_key = " + le.getLitterKey().toString();
                if(list.indexOf(le) != list.size() -1){
                    query = query + " OR ";
                }
            }            
            query = query + ")";
            System.out.println(query);
            rows = this.executeJCMSQuery(query).getRows(); 
        }       
        else{
            rows = this.executeJCMSQuery(showExistingSampleLitter).getRows(); 
        }
        for(SortedMap sm : rows){
            SampleXtraDTO dto = new SampleXtraDTO();
            dto.setSampleID(myGet("sampleID",sm));
            dto.setHarvestMethod(myGet("harvestMethod",sm));
            dto.setAge(myGet("age",sm));
            dto.setOwner(myGet("owner",sm));
            dto.setWeight(myGet("weight",sm));
            samples.add(dto);
        }
        return samples;
    }
    
    public ArrayList<SampleXtraDTO> showExistingSamplesBySample(ArrayList<SampleXtraDTO> list){
        ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
        SortedMap[] rows;
        if(!list.isEmpty()){
            String query = baseShowExistingSampleParentSample + "AND (";
            for(SampleXtraDTO dto : list){
                query = query + "_parentSample_key = " + dto.getSample_key();
                if(list.indexOf(dto) != list.size() -1){
                    query = query + " OR ";
                }
            }            
            query = query + ")";
            System.out.println(query);
            rows = this.executeJCMSQuery(query).getRows(); 
        }       
        else{
            rows = this.executeJCMSQuery(baseShowExistingSampleParentSample).getRows(); 
        }
        for(SortedMap sm : rows){
            SampleXtraDTO dto = new SampleXtraDTO();
            dto.setSampleID(myGet("sampleID",sm));
            dto.setHarvestMethod(myGet("harvestMethod",sm));
            dto.setAge(myGet("age",sm));
            dto.setOwner(myGet("owner",sm));
            dto.setWeight(myGet("weight",sm));
            samples.add(dto);
        }
        return samples;
    }
    
    public ArrayList<SampleXtraDTO> showExistingSamplesByOther(StrainEntity se){
        ArrayList<SampleXtraDTO> samples = new ArrayList<SampleXtraDTO>();
        SortedMap[] rows;
        if(se!=null){
            String query = showExistingSampleOther + "AND _strain_key = " + se.getStrainKey().toString();
            rows = this.executeJCMSQuery(query).getRows();
        }
        else{
             rows = this.executeJCMSQuery(showExistingSampleOther).getRows();
        }
        for(SortedMap sm : rows){
            SampleXtraDTO dto = new SampleXtraDTO();
            dto.setSampleID(myGet("sampleID",sm));
            dto.setHarvestMethod(myGet("harvestMethod",sm));
            dto.setAge(myGet("age",sm));
            dto.setOwner(myGet("owner",sm));
            dto.setWeight(myGet("weight",sm));
            samples.add(dto);
        }
        return samples;
    }
    
    public ArrayList<SampleClassDTO> getSampleClasses(){
        ArrayList<SampleClassDTO> theVals = new ArrayList<SampleClassDTO>();
        String query = "SELECT * FROM cv_SampleClass";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            SampleClassDTO dto = new SampleClassDTO();
            dto.setSampleClass(myGet("sampleClass",row));
            dto.setSampleClass_key(myGet("_sampleClass_key",row));
            theVals.add(dto);
        }
        return theVals;
    }
    
    public ArrayList<SampleTypeDTO> getSampleTypesBySampleClassKey(String sampleClassKey){
        ArrayList<SampleTypeDTO> vals = new ArrayList<SampleTypeDTO>();
        String query = "SELECT * FROM cv_SampleType WHERE _sampleClass_key = " + sampleClassKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            SampleTypeDTO dto = new SampleTypeDTO();
            dto.setSampleClass_key(sampleClassKey);
            dto.setSampleType_key(myGet("_sampleType_key", row));
            dto.setSampleType(myGet("sampleType", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public boolean sampleIDExists(String sampleID){
        String query = "SELECT COUNT(*) AS idCount FROM Sample WHERE sampleID = '" + sampleID + "';";
        try{
            SortedMap map = this.executeJCMSQuery(query).getRows()[0];
            String value = myGet("idCount", map);
            if(value.equals("0")){
                return false;
            }
            else{
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
    }
    
    public ArrayList<SampleDateTypeDTO> getSampleDateTypes(){
        ArrayList<SampleDateTypeDTO> vals = new ArrayList<SampleDateTypeDTO>();
        String query = "SELECT * FROM cv_SampleDateType;";
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            SampleDateTypeDTO dto = new SampleDateTypeDTO();
            dto.setSampleDateType_key(myGet("_sampleDateType_key", row));
            dto.setSampleDateType(myGet("sampleDateType", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<HarvestMethodDTO> getHarvestMethods(){
        ArrayList<HarvestMethodDTO> vals = new ArrayList<HarvestMethodDTO>();
        String query = "SELECT * FROM cv_HarvestMethod;";
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            HarvestMethodDTO dto = new HarvestMethodDTO();
            dto.setHarvestMethod_key(myGet("_harvestMethod_key", row));
            dto.setHarvestMethod(myGet("harvestMethod", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<PreservationDetailDTO> getPreservationDetails(String preservationMethodKey){
        ArrayList<PreservationDetailDTO> vals = new ArrayList<PreservationDetailDTO>();
        String query = "SELECT * FROM cv_PreservationDetail WHERE _preservationMethod_key = " + preservationMethodKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            PreservationDetailDTO dto = new PreservationDetailDTO();
            dto.setPreservationDetail(myGet("preservationDetail", row));
            dto.setPreservationDetail_key(myGet("_preservationDetail_key", row));
            dto.setPreservationMethod_key(preservationMethodKey);
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<PreservationMethodDTO> getPreservationMethods(String preservationTypeKey){
        ArrayList<PreservationMethodDTO> vals = new ArrayList<PreservationMethodDTO>();
        String query = "SELECT * FROM cv_PreservationMethod WHERE _preservationType_key = " + preservationTypeKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            PreservationMethodDTO dto = new PreservationMethodDTO();
            dto.setPreservationMethod(myGet("preservationMethod", row));
            dto.setPreservationType_key(preservationTypeKey);
            dto.setPreservationMethod_key(myGet("_preservationMethod_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<PreservationTypeDTO> getPreservationTypes(String sampleClassKey){
        ArrayList<PreservationTypeDTO> vals = new ArrayList<PreservationTypeDTO>();
        String query = "SELECT * FROM cv_PreservationType WHERE _sampleClass_key = " + sampleClassKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            PreservationTypeDTO dto = new PreservationTypeDTO();
            dto.setPreservationType(myGet("preservationType", row));
            dto.setSampleClass_key(sampleClassKey);
            dto.setPreservationType_key(myGet("_preservationType_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<TimeUnitDTO> getTimeUnits(){
        ArrayList<TimeUnitDTO> vals = new ArrayList<TimeUnitDTO>();
        String query = "SELECT * FROM cv_TimeUnit;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            TimeUnitDTO dto = new TimeUnitDTO();
            dto.setAbbreviation(myGet("abbreviation", row));
            dto.setMinutesPerUnit(myGet("minutesPerUnit", row));
            dto.setTimeUnit(myGet("timeUnit", row));
            dto.setTimeUnit_key(myGet("_timeUnit_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<WeightUnitDTO> getWeightUnits(){
        ArrayList<WeightUnitDTO> vals = new ArrayList<WeightUnitDTO>();
        String query = "SELECT * FROM cv_WeightUnit;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            WeightUnitDTO dto = new WeightUnitDTO();
            dto.setWeightUnit(myGet("weightUnit", row));
            dto.setWeightUnit_key(myGet("_weightUnit_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<SampleStatusDTO> getSampleStatuses(){
        ArrayList<SampleStatusDTO> vals = new ArrayList<SampleStatusDTO>();
        String query = "SELECT * FROM cv_SampleStatus;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            System.out.println("Is IN Storage "+ myGet("isInStorage", row));
            SampleStatusDTO dto = new SampleStatusDTO();
            dto.setIsInStorage(myGet("isInStorage", row));
            dto.setSampleStatus(myGet("sampleStatus", row));
            dto.setSampleStatus_key(myGet("_sampleStatus_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public ArrayList<EpochDTO> getEpochs(){
        ArrayList<EpochDTO> vals = new ArrayList<EpochDTO>();
        String query = "SELECT * FROM cv_Epoch;";
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            EpochDTO dto = new EpochDTO();
            dto.setEpoch(myGet("epoch", row));
            dto.setEpoch_key(myGet("_epoch_key", row));
            vals.add(dto);
        }
        return vals;
    }
    
    public String getMouseKeyByID(String mouseID){
        try{
            String query = "SELECT _mouse_key FROM Mouse WHERE ID = " + varcharParser(mouseID);
            for(SortedMap sm : this.executeJCMSQuery(query).getRows()){
                return myGet("_mouse_key", sm);
            }
            return "";
        }
        catch(Exception e){
            System.out.println(e);
            return "";
        }
    }
    
    public String getLitterKeyByID(String litterID){
        try{
            String query = "SELECT _litter_key FROM Litter WHERE litterID = " + varcharParser(litterID);
            for(SortedMap sm : this.executeJCMSQuery(query).getRows()){
                return myGet("_litter_key", sm);
            }
            return "";
        }
        catch(Exception e){
            System.out.println(e);
            return "";
        }
    }
    
    public String getMatingKeyByID(String matingID){
        try{
            String query = "SELECT _mating_key FROM Mating WHERE matingID = " + numberParser(matingID);
            for(SortedMap sm : this.executeJCMSQuery(query).getRows()){
                return myGet("_mating_key", sm);
            }
            return "";
        }
        catch(Exception e){
            System.out.println(e);
            return "";
        }
    }
    
    public String getSampleKeyByID(String sampleID){
        String query = "SELECT _sample_key FROM Sample WHERE SampleID = " + varcharParser(sampleID);
        for(SortedMap sm : this.executeJCMSQuery(query).getRows()){
            return myGet("_sample_key", sm);
        }
        return "";
    }
    
    public StorageDTO getStorageByKey(String storageKey){
        StorageDTO dto = new StorageDTO();
        String query = "SELECT * FROM Storage WHERE _storage_key = " + storageKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            dto.setStorage_key(myGet("_storage_key", rows[0]));
            dto.setPreservationDetail_key(myGet("_preservationDetail_key", rows[0]));
            dto.setPreservationMethod_key(myGet("_preservationMethod_key", rows[0]));
            dto.setPreservationType(myGet("_preservationType_key", rows[0]));
            dto.setSampleStatus_key(myGet("_sampleStatus_key", rows[0]));
            dto.setSample_key(myGet("_sample_key", rows[0]));
            return dto;
        }
        else{
            return null;
        }
    }
    
    public LocationDTO getLocationByLocationKey(String locationKey){
        LocationDTO dto = new LocationDTO();
        String query = "SELECT * FROM Location WHERE _location_key = " + locationKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            dto.setLocationType_key(myGet("_locationType_key", rows[0]));
            dto.setLocation_key(myGet("_location_key", rows[0]));
            dto.setStorage_key(myGet("_storage_key", rows[0]));
            return dto;
        }
        else{
            return null;
        }
    }
    
    public SampleXtraDTO getSampleByID(String SampleID){
        String query = "SELECT Sample.*, Strain.strainName, cv_SampleStatus.sampleStatus, Storage.*, Location._locationType_key "
                + "FROM Sample "
                + "JOIN Storage "
                + "ON Sample._sample_key = Storage._sample_key "
                + "JOIN Strain "
                + "ON Sample._strain_key = Strain._strain_key "
                + "JOIN Location "
                + "ON Storage._storage_key = Location._storage_key "
                + "JOIN cv_SampleStatus "
                + "ON Storage._sampleStatus_key = cv_SampleStatus._sampleStatus_key "
                + "WHERE SampleID = '" + SampleID + "'";
        SampleXtraDTO dto = new SampleXtraDTO();
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length >= 1){
            SortedMap row = rows[0];
            dto.setAge(myGet("age",row));
            dto.setDescription(myGet("description", row));
            dto.setEpoch_key(myGet("_epoch_key", row));
            dto.setHarvestMethod(myGet("harvestMethod", row));
            dto.setOwner(myGet("owner", row));
            dto.setParentSample_key(myGet("_parentSample_key", row));
            try{
                dto.setSampleDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(myGet("sampleDate", row)));
            }
            catch(Exception e){
                System.out.println(e);
            }
            dto.setSampleDateType_key(myGet("_sampleDateType_key", row));
            dto.setSampleID(myGet("SampleID", row));
            dto.setSampleType_key(myGet("_sampleType_key", row));
            dto.setSample_key(myGet("_sample_key", row));
            dto.setSourceType(myGet("SourceType", row));
            dto.setTimeUnit_key(myGet("_timeUnit_key", row));
            dto.setWeight(myGet("weight", row));
            dto.setWeightUnit_key(myGet("_weightUnit_key", row));
            dto.setStrain_key(myGet("_strain_key", row));
            dto.setStrainName(myGet("strainName", row));
            dto.getStorage().setSampleStatus(myGet("sampleStatus", row));
            dto.getStorage().setPreservationDetail_key(myGet("_preservationDetail_key", row));
            dto.getStorage().setPreservationMethod_key(myGet("_preservationMethod_key", row));
            dto.getStorage().setPreservationType_key(myGet("_preservationType_key", row));
            dto.getStorage().setSampleStatus_key(myGet("_sampleStatus_key", row));
            dto.getStorage().setStorage_key(myGet("_storage_key", row));
            dto.setLocationType_key(myGet("_locationType_key", row));
            if(dto.getSourceType().equals("1")){
                //parent sample, parent sample key was set above
            }
            else if(dto.getSourceType().equals("2")){
                //mouse 
                dto.setMouseSources(this.getMouseSource(dto.getSample_key()));
            }
            else if(dto.getSourceType().equals("3")){
                //litter
                dto.setLitterSources(this.getLitterSource(dto.getSample_key()));
            }
            else if(dto.getSourceType().equals("4")){
                //mating
                dto.setMatingSources(this.getMatingSource(dto.getSample_key()));
            }
            else if(dto.getSourceType().equals("5")){
                //other
            }
            return dto;
        }
        else{
            return null;
        }
    }
    
    public ArrayList<MouseEntity> getMouseSource(String sampleKey){
        ArrayList<MouseEntity> mice = new ArrayList<MouseEntity>();
        String query = "SELECT _mouse_key FROM MouseSample WHERE _sample_key = " + sampleKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            try{
                mice.add((MouseEntity) dao.getSystemFacadeLocal().baseFindByKey(new MouseEntity(), Integer.parseInt(myGet("_mouse_key", row))));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return mice;
    }
    
    public ArrayList<LitterEntity> getLitterSource(String sampleKey){
        ArrayList<LitterEntity> litters = new ArrayList<LitterEntity>();
        String query = "SELECT _litter_key FROM LitterSample WHERE _sample_key = " + sampleKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            try{
                litters.add((LitterEntity) dao.getSystemFacadeLocal().baseFindByKey(new LitterEntity(), Integer.parseInt(myGet("_litter_key", row))));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return litters;
    }
    
    public ArrayList<MatingEntity> getMatingSource(String sampleKey){
        ArrayList<MatingEntity> matings = new ArrayList<MatingEntity>();
        String query = "SELECT _mating_key FROM MatingSample WHERE _sample_key = " + sampleKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        for(SortedMap row : rows){
            try{
                matings.add((MatingEntity) dao.getSystemFacadeLocal().baseFindByKey(new MatingEntity(), Integer.parseInt(myGet("_mating_key", row))));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return matings;
    }
     
    public String getSampleStrainKeyBySampleID(String sampleID){
        String query = "SELECT _strain_key FROM Sample WHERE SampleID = " + varcharParser(sampleID);
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            return myGet("_strain_key", rows[0]);
        }
        else{
            return "";
        }
    }
    
    public String insertSample(SampleXtraDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_sample_key", "Sample")) + 1).toString();
        String insertQuery = "INSERT INTO Sample "
                + "(_sample_key, age, _timeUnit_key, _epoch_key, harvestMethod, "
                + "description, weight, _weightUnit_key, _sampleType_key, "
                + "sampleDate, _sampleDateType_key, _strain_key, owner, SampleID, "
                + "_parentSample_key, SourceType) VALUES ("
                + key + ", "
                + numberParser(dto.getAge()) + ", "
                + numberParser(dto.getTimeUnit_key())  + ", "
                + numberParser(dto.getEpoch_key())  + ", "
                + varcharParser(dto.getHarvestMethod())  + ", "
                + "'" + dto.getDescription() + "'" + ", "
                + numberParser(dto.getWeight())  + ", "
                + numberParser(dto.getWeightUnit_key())  + ", "
                + numberParser(dto.getSampleType_key())  + ", "
                + dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDate())) + ", "
                + numberParser(dto.getSampleDateType_key()) + ", "
                + numberParser(dto.getStrain_key()) + ", "
                + varcharParser(dto.getOwner()) + ", "
                + varcharParser(dto.getSampleID()) + ", "
                + numberParser(dto.getParentSample_key()) + ", "
                + varcharParser(dto.getSourceType()) + ");";
        System.out.println(insertQuery);
        this.executeJCMSUpdate(insertQuery);
        
        /*after inserting into the sample table insert into Mating/Litter/Mouse sample tables
         * for each sample source in added by the user, if source is either other or sample
         * there is no linking table so no worries.*/ 
        if(dto.getSourceType().equals("2")){
            for(MouseEntity me : dto.getMouseSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(key);
                SLDTO.setUnit_key(me.getMouseKey().toString());
                insertMouseSample(SLDTO);
            }
        }
        else if(dto.getSourceType().equals("3")){       
            for(LitterEntity le : dto.getLitterSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(key);
                SLDTO.setUnit_key(le.getLitterKey().toString());
                insertLitterSample(SLDTO);
            }
        }
        else if(dto.getSourceType().equals("4")){
            for(MatingEntity me : dto.getMatingSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(key);
                SLDTO.setUnit_key(me.getMatingKey().toString());
                insertMatingSample(SLDTO);
            }
        }
        
        //insert into storage
        dto.getStorage().setSample_key(key);
        String storageKey = insertStorage(dto.getStorage());
        
        //insert into Location
        LocationDTO LDTO = new LocationDTO();
        LDTO.setLocationType_key(dto.getLocationType_key());
        LDTO.setStorage_key(storageKey);
        insertLocation(LDTO);
        
        return key;
    }
    
    public void updateSample(SampleXtraDTO dto) throws Exception{
        //update sample itself
        String query = "UPDATE Sample SET "
                + "age = " + numberParser(dto.getAge()) + ", "
                + "description = '" + dto.getDescription() + "', "
                + "_epoch_key = " + numberParser(dto.getEpoch_key()) + ", "
                + "harvestMethod = " + varcharParser(dto.getHarvestMethod()) + ", "
                + "sampleDate = " + dateParser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDate())) + ", "
                + "owner = " + varcharParser(dto.getOwner()) + ", "
                + "_parentSample_key = " + numberParser(dto.getParentSample_key()) + ", "
                + "_sampleDateType_key = " + numberParser(dto.getSampleDateType_key()) + ", "
                + "_sampleType_key = " + numberParser(dto.getSampleType_key()) + ", "
                + "sourceType = " + numberParser(dto.getSourceType()) + ", "
                + "_strain_key = " + numberParser(dto.getStrain_key()) + ", "
                + "_timeUnit_key = " + numberParser(dto.getTimeUnit_key()) + ", "
                + "weight = " + numberParser(dto.getWeight()) + ", "
                + "_weightUnit_key = " + numberParser(dto.getWeightUnit_key())
                + " WHERE _sample_key = " + dto.getSample_key() ;
        System.out.println(query);
        this.executeJCMSUpdate(query);
        
        
        //update storage for that sample
        dto.getStorage().setSample_key(dto.getSample_key());
        String updateStorage = "UPDATE Storage SET "
                + "_sampleStatus_key = " + numberParser(dto.getStorage().getSampleStatus_key()) + ", "
                + "_preservationType_key = " + numberParser(dto.getStorage().getPreservationType_key()) + ", "
                + "_preservationMethod_key = " + numberParser(dto.getStorage().getPreservationMethod_key()) + ", "
                + "_preservationDetail_key = " + numberParser(dto.getStorage().getPreservationDetail_key()) 
                + " WHERE _sample_key = " + dto.getSample_key();
        System.out.println(updateStorage);
        this.executeJCMSUpdate(updateStorage);
        
        //update Location for that storage
        String updateLocation = "UPDATE Location SET _locationType_key = " + dto.getLocationType_key()
                + " WHERE _storage_key = " + dto.getStorage().getStorage_key();
        System.out.println(updateLocation);
        this.executeJCMSUpdate(updateLocation);
        
    }
    
    public void insertSources(SampleXtraDTO dto) throws Exception{
        if(dto.getSourceType().equals("2")){
            for(MouseEntity me : dto.getMouseSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(dto.getSample_key());
                SLDTO.setUnit_key(me.getMouseKey().toString());
                insertMouseSample(SLDTO);
            }
        }
        else if(dto.getSourceType().equals("3")){       
            for(LitterEntity le : dto.getLitterSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(dto.getSample_key());
                SLDTO.setUnit_key(le.getLitterKey().toString());
                insertLitterSample(SLDTO);
            }
        }
        else if(dto.getSourceType().equals("4")){
            for(MatingEntity me : dto.getMatingSources()){
                SampleLinkDTO SLDTO = new SampleLinkDTO();
                SLDTO.setSample_key(dto.getSample_key());
                SLDTO.setUnit_key(me.getMatingKey().toString());
                insertMatingSample(SLDTO);
            }
        }
    }
    
    private String insertMatingSample(SampleLinkDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_matingSample_key", "MatingSample"))+1).toString();
        String query = "INSERT INTO MatingSample "
                + "(_matingSample_key, _sample_key, _mating_key) "
                + "VALUES"
                + "("
                + key + ", "
                + dto.getSample_key() + ", "
                + dto.getUnit_key()
                + ")";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    private String insertMouseSample(SampleLinkDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_mouseSample_key", "MouseSample"))+1).toString();
        String query = "INSERT INTO MouseSample "
                + "(_mouseSample_key, _sample_key, _mouse_key) "
                + "VALUES"
                + "("
                + key + ", "
                + dto.getSample_key() + ", "
                + dto.getUnit_key()
                + ")";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    private String insertLitterSample(SampleLinkDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_litterSample_key", "LitterSample"))+1).toString();
        String query = "INSERT INTO LitterSample "
                + "(_litterSample_key, _sample_key, _litter_key) "
                + "VALUES"
                + "("
                + numberParser(key) + ", "
                + numberParser(dto.getSample_key()) + ", "
                + numberParser(dto.getUnit_key()) 
                + ");";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    private String insertStorage(StorageDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_storage_key", "Storage"))+1).toString();
        String query = "INSERT INTO Storage ("
                + "_storage_key, _sample_key, _sampleStatus_key, "
                + "_preservationDetail_key, _preservationMethod_key, "
                + "_preservationType_key) VALUES ("
                + numberParser(key) + ", "
                + numberParser(dto.getSample_key()) + ", "
                + numberParser(dto.getSampleStatus_key()) + ", "
                + numberParser(dto.getPreservationDetail_key()) + ", "
                + numberParser(dto.getPreservationMethod_key()) + ", "
                + numberParser(dto.getPreservationType_key()) 
                + ")";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    private void updateStorage(StorageDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_storage_key", "Storage"))+1).toString();
        String query = "UPDATE Storage SET "
                + "_sampleStatus_key = " + numberParser(dto.getSampleStatus_key()) + ", "
                + "_preservationDetail_key =" + numberParser(dto.getPreservationDetail_key()) + ", "
                + "_preservationMethod_key =" + numberParser(dto.getPreservationMethod_key()) + ", "
                + "_preservationType_key = " + numberParser(dto.getPreservationType_key()) +
                " WHERE _sample_key = " + numberParser(dto.getSample_key());
        System.out.println(query);
        this.executeJCMSUpdate(query);
    }
    
    private void updateLocation(LocationDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_location_key", "Location"))+1).toString();
        String query = "UPDATE Location SET _locationType_key = " + dto.getLocationType_key()    
                + " WHERE _location_key = " + dto.getLocation_key();
        System.out.println(query);
        this.executeJCMSUpdate(query);
    }
    
    private String insertLocation(LocationDTO dto) throws Exception{
        String key = new Integer(Integer.parseInt(getMaxValue("_location_key", "Location"))+1).toString();
        String query = "INSERT INTO Location (_location_key, _locationType_key, _storage_key) "
                + "VALUES ("
                + key + ", "
                + dto.getLocationType_key() + ", "
                + dto.getStorage_key()
                + ")";    
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    } 
    
    public ArrayList<String> getMouseIDsByStrain(String strainKey, ArrayList<OwnerEntity> list){
        ArrayList<String> mice = new ArrayList<String>();
        String query = "SELECT _mouse_key, ID FROM Mouse WHERE _strain_key = " + strainKey + " AND " + ownerClauseBuilder(list);
        SortedMap[] values = this.executeJCMSQuery(query).getRows();
        for(SortedMap mouse : values){
            mice.add(myGet("ID", mouse));
        }
        return mice;
    }
    
    public ArrayList<String> getSampleIDsByStrain(String strainKey, ArrayList<OwnerEntity> list){
        ArrayList<String> samples = new ArrayList<String>();
        String query = "SELECT SampleID FROM Sample WHERE _strain_key = " + strainKey + " AND " + ownerClauseBuilder(list);
        SortedMap[] values = this.executeJCMSQuery(query).getRows();
        for(SortedMap sample : values){
            samples.add(myGet("SampleID", sample));
        }
        return samples;
    }
    
    public ArrayList<String> getMatingIDsByStrain(String strainKey, ArrayList<OwnerEntity> list){
        ArrayList<String> matings = new ArrayList<String>();
        String query = "SELECT matingID FROM Mating WHERE _strain_key = " + strainKey + " AND " + ownerClauseBuilder(list);
        SortedMap[] values = this.executeJCMSQuery(query).getRows();
        for(SortedMap mating : values){
            matings.add(myGet("matingID", mating));
        }
        return matings;
    }
    
    public ArrayList<String> getLitterIDsByStrain(String strainKey, ArrayList<OwnerEntity> list){
        ArrayList<String> litters = new ArrayList<String>();
        String query = 
                "SELECT litterID "
                + "FROM Litter "
                + "JOIN Mating "
                + "ON Litter._mating_key = Mating._mating_key "
                + "WHERE _strain_key = " + strainKey + " AND " + ownerClauseBuilder(list);
        SortedMap[] values = this.executeJCMSQuery(query).getRows();
        for(SortedMap litter : values){
            litters.add(myGet("litterID", litter));
        }
        return litters;
    }
    
    public String getSampleIDByKey(String sampleKey){
        String query = "SELECT SampleID FROM Sample WHERE _sample_key = " + sampleKey;
        SortedMap[] result = executeJCMSQuery(query).getRows();
        if(result.length > 0){
            return myGet("SampleID", result[0]);
        }
        else{
            System.out.println("That's weird, couldn't find sampleID for key " + sampleKey);
            return "";
        }
    }
    
    public void changeSampleID(String newID, String key) throws Exception{
        String query = "UPDATE Sample SET SampleID = " + varcharParser(newID) 
                + " WHERE _sample_key = " + key;
        this.executeJCMSUpdate(query);
    }
    
    public String getSampleTypeSampleClassKey(String sampleTypeKey){
        String query = "SELECT _sampleClass_key FROM cv_SampleType WHERE _sampleType_key = " + sampleTypeKey;
        SortedMap[] rows = executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            return myGet("_sampleClass_key", rows[0]);
        }
        else{
            return null;
        }
    }
    
    public void deleteMouseSources(String sampleKey) throws Exception{
        String query = "DELETE FROM MouseSample WHERE _sample_key = " + sampleKey;
        this.executeJCMSUpdate(query);
    }
    
    public void deleteLitterSources(String sampleKey) throws Exception{
        String query = "DELETE FROM MouseSample WHERE _sample_key = " + sampleKey;
        this.executeJCMSUpdate(query);
    }
    
    
    public void deleteMatingSources(String sampleKey) throws Exception{
        String query = "DELETE FROM MouseSample WHERE _sample_key = " + sampleKey;
        this.executeJCMSUpdate(query);
    }
    
    private String ownerClauseBuilder(ArrayList<OwnerEntity> list){
        String clause = "(";
        for(OwnerEntity owner : list){
            if(clause.equals("(")){
                clause = clause + "owner = '" + owner.getOwner() + "'"; 
            }
            else{
                clause = clause + " OR owner = '" + owner.getOwner() + "'";
            }
        }
        clause = clause + ")";
        return clause;
    }
    
    public ArrayList<SampleXtraDTO> sampleListQuery(SampleSearchDTO dto){
        ArrayList<SampleXtraDTO> results = new ArrayList<SampleXtraDTO>();
        //first step is to build the where clause
        String where = "WHERE (";
        for(String owner : dto.getOwners()){
            if(where.equals("WHERE (")){
                where = where + "owner = " + varcharParser(owner);
            }
            else{
                where = where + " OR owner = " + varcharParser(owner);
            }
        }
        where = where + ")";
        if(!dto.getSampleClass().equals("")){
            if(dto.getSampleType().equals("")){
                where = where + " AND (";
                ArrayList<SampleTypeDTO> types = this.getSampleTypesBySampleClassKey(dto.getSampleClass());
                for(SampleTypeDTO type : types){
                    //first
                    if(types.get(0).equals(type)){
                        where = where + "Sample._sampleType_key = " + varcharParser(type.getSampleType_key());
                    }
                    else{
                        where = where + " OR Sample._sampleType_key = " + varcharParser(type.getSampleType_key());
                    }
                }
                where = where + ")";
            }
            else{
                where = where + " AND Sample._sampleType_key = " + numberParser(dto.getSampleType());
            }
        }
        if(dto.getSampleDateBefore() != null){
            if(dto.getSampleDateAfter() != null){
                where = where + " AND (Sample.sampleDate < '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDateBefore()) + "'"
                        + " AND Sample.sampleDate > '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDateAfter()) + "')";
            }
            else{
                //samples before sample date sampleDateFrom
                where = where + " AND Sample.sampleDate < '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDateBefore()) + "'";
            }
        }
        if(dto.getSampleDateAfter() != null && dto.getSampleDateBefore() == null){
            where = where + " AND Sample.sampleDate > '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getSampleDateAfter()) + "'";
        }
        if(!dto.getSampleID().equals("")){
            where = where + " AND SampleID LIKE '%" + dto.getSampleID() + "%'";
        }
        if(dto.getSampleStrain() != null){
            where = where + " AND _strain_key = " + numberParser(dto.getSampleStrain().getStrainKey().toString());
        }

        if(!dto.getSourceType().equals("")){
            where = where + " AND SourceType = " + numberParser(dto.getSourceType());
        }
        if(!dto.getLocation().equals("")){
            referencedLocations = new ArrayList<String>();
            referencedLocations.add(dto.getLocation());
            this.buildLocationList(dto.getLocation());
            int idx = 0;
            for(String locationKey : referencedLocations){
                if(idx == 0){
                    where = where + " AND (cv_LocationType._locationType_key = " + numberParser(locationKey);
                }
                else{
                    where = where + " OR cv_LocationType._locationType_key = " + numberParser(locationKey);;
                }
                idx++;
            }
            where = where + ")";

        }
        //should only add Storage Facility to query IF no location specified
        else if(!dto.getStorageFacility().equals("")){
            where = where + " AND cv_LocationType._storageFacility_key = " + numberParser(dto.getStorageFacility());
        }
        System.out.println(where);
        
        String query = 
                "SELECT Sample.*, Strain.strainName, cv_SampleStatus.sampleStatus, Storage.*, "
                + "Location._locationType_key, cv_SampleType.sampleType, cv_LocationType.locationType "
                + "FROM Sample "
                + "JOIN Storage "
                + "ON Sample._sample_key = Storage._sample_key "
                + "JOIN Strain "
                + "ON Sample._strain_key = Strain._strain_key "
                + "JOIN Location "
                + "ON Storage._storage_key = Location._storage_key "
                + "JOIN cv_SampleStatus "
                + "ON Storage._sampleStatus_key = cv_SampleStatus._sampleStatus_key "
                + "JOIN cv_SampleType "
                + "ON Sample._sampleType_key = cv_SampleType._sampleType_key "
                + "JOIN cv_LocationType "
                + "ON Location._locationType_key = cv_LocationType._locationType_key " + where;
        System.out.println(query);
        
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        
        for(SortedMap row : rows){
            SampleXtraDTO sxDTO = new SampleXtraDTO();
            sxDTO.setAge(myGet("age",row));
            sxDTO.setDescription(myGet("description", row));
            sxDTO.setEpoch_key(myGet("_epoch_key", row));
            sxDTO.setHarvestMethod(myGet("harvestMethod", row));
            sxDTO.setOwner(myGet("owner", row));
            sxDTO.setParentSample_key(myGet("_parentSample_key", row));
            try{
                sxDTO.setSampleDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(myGet("sampleDate", row)));
            }
            catch(Exception e){
                System.out.println(e);
            }
            sxDTO.setSampleDateType_key(myGet("_sampleDateType_key", row));
            sxDTO.setSampleID(myGet("SampleID", row));
            sxDTO.setSampleType_key(myGet("_sampleType_key", row));
            sxDTO.setSampleType(myGet("sampleType", row));
            sxDTO.setSample_key(myGet("_sample_key", row));
            String sourceType = myGet("SourceType", row);
            if(sourceType.equals("1")){
                sxDTO.setSourceType("Sample");
            }
            else if(sourceType.equals("2")){
                sxDTO.setSourceType("Mouse");
            }
            else if(sourceType.equals("3")){
                sxDTO.setSourceType("Litter");
            }
            else if(sourceType.equals("4")){
                sxDTO.setSourceType("Mating");
            }
            else if(sourceType.equals("5")){
                sxDTO.setSourceType("Other");
            }
            sxDTO.setTimeUnit_key(myGet("_timeUnit_key", row));
            sxDTO.setWeight(myGet("weight", row));
            sxDTO.setWeightUnit_key(myGet("_weightUnit_key", row));
            sxDTO.setStrain_key(myGet("_strain_key", row));
            sxDTO.setStrainName(myGet("strainName", row));
            sxDTO.getStorage().setSampleStatus(myGet("sampleStatus", row));
            sxDTO.getStorage().setPreservationDetail_key(myGet("_preservationDetail_key", row));
            sxDTO.getStorage().setPreservationMethod_key(myGet("_preservationMethod_key", row));
            sxDTO.getStorage().setPreservationType_key(myGet("_preservationType_key", row));
            sxDTO.getStorage().setSampleStatus_key(myGet("_sampleStatus_key", row));
            sxDTO.getStorage().setStorage_key(myGet("_storage_key", row));
            sxDTO.setLocationType_key(myGet("_locationType_key", row));
            sxDTO.setLocation(myGet("locationType", row));
            if(sxDTO.getSourceType().equals("1")){
                //parent sample, parent sample key was set above
            }
            else if(sxDTO.getSourceType().equals("2")){
                //mouse 
                sxDTO.setMouseSources(this.getMouseSource(sxDTO.getSample_key()));
            }
            else if(sxDTO.getSourceType().equals("3")){
                //litter
                sxDTO.setLitterSources(this.getLitterSource(sxDTO.getSample_key()));
            }
            else if(sxDTO.getSourceType().equals("4")){
                //mating
                sxDTO.setMatingSources(this.getMatingSource(sxDTO.getSample_key()));
            }
            else if(sxDTO.getSourceType().equals("5")){
                //other
            }
            //return sxDTO;
            results.add(sxDTO);
        }
        return results;
    }
    
    private void buildLocationList(String locationTypeKey){
        String refQuery = "SELECT * FROM cv_LocationType WHERE locationTypeRef = ";
        SortedMap[] locations = this.executeJCMSQuery(refQuery + locationTypeKey ).getRows();       
        if(locations.length > 0){
            for(SortedMap location : locations){
                buildLocationList(myGet("_locationType_key", location));
                this.referencedLocations.add(myGet("_locationType_key", location));
            }
        }
    }
    
    public ArrayList<String> getLocationOwners(String location){
        ArrayList<String> owners = new ArrayList<String>();
        String query = "SELECT DISTINCT owner FROM Sample "
                     + "JOIN Storage "
                     + "ON Storage._sample_key = Sample._sample_key "
                     + "JOIN Location "
                     + "ON Location._storage_key = Storage._storage_key "
                     + "JOIN cv_LocationType "
                     + "ON Location._locationType_key = cv_LocationType._locationType_key "
                     + "WHERE ";
        referencedLocations = new ArrayList<String>();
        referencedLocations.add(location);
        this.buildLocationList(location);
        int idx = 0;
        for(String locationKey : referencedLocations){
            if(idx == 0){
                query = query + " cv_LocationType._locationType_key = " + numberParser(locationKey);
            }
            else{
                query = query + " OR cv_LocationType._locationType_key = " + numberParser(locationKey);
            }
            idx++;
        }
        SortedMap[] results = this.executeJCMSQuery(query).getRows();
        for(SortedMap sm : results){
            owners.add(myGet("owner", sm));
        }
        return owners;
    }
    
    public String insertPreservationType(PreservationTypeDTO dto) throws Exception{
        String key = this.getMaxValue("_preservationType_key", "cv_PreservationType");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_PreservationType "
                + "(_preservationType_key, _sampleClass_key, preservationType) VALUES ("
                + numberParser(key) + ", "
                + numberParser(dto.getSampleClass_key()) + ", "
                + varcharParser(dto.getPreservationType()) + ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    
    public String insertPreservationMethod(PreservationMethodDTO dto) throws Exception{
        String key = this.getMaxValue("_preservationMethod_key", "cv_PreservationMethod");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_PreservationMethod "
                + "(_preservationMethod_key, _preservationType_key, preservationMethod) VALUES ("
                + numberParser(key) + ", "
                + numberParser(dto.getPreservationType_key()) + ", "
                + varcharParser(dto.getPreservationMethod()) + ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    
    public String insertPreservationDetail(PreservationDetailDTO dto) throws Exception{
        String key = this.getMaxValue("_preservationDetail_key", "cv_PreservationDetail");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_PreservationDetail "
                + "(_preservationDetail_key, _preservationMethod_key, preservationDetail) VALUES ("
                + numberParser(key) + ", "
                + numberParser(dto.getPreservationMethod_key()) + ", "
                + varcharParser(dto.getPreservationDetail()) + ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public String insertSampleStatus(SampleStatusDTO dto) throws Exception{
        String key = this.getMaxValue("_sampleStatus_key", "cv_SampleStatus");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_SampleStatus "
                + "(_sampleStatus_key, isInStorage, sampleStatus) VALUES ("
                + numberParser(key) + ", "
                + numberParser(dto.getIsInStorage()) + ", "
                + varcharParser(dto.getSampleStatus()) + ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public void deleteSampleStatus(String key) throws Exception{
        String query = "DELETE FROM cv_SampleStatus WHERE _sampleStatus_key = " + key;
        this.executeJCMSUpdate(query);
    }
    
    public String insertTimeUnit(TimeUnitDTO dto) throws Exception{
        String key = this.getMaxValue("_timeUnit_key", "cv_TimeUnit");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_TimeUnit "
                + "(_timeUnit_key, timeUnit, abbreviation, minutesPerUnit) VALUES ("
                + numberParser(key) + ", "
                + varcharParser(dto.getTimeUnit()) + ", "
                + varcharParser(dto.getAbbreviation()) + ", "
                + numberParser(dto.getMinutesPerUnit()) + ")";
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public void deleteTimeUnit(String key) throws Exception{
        String query = "DELETE FROM cv_TimeUnit WHERE _timeUnit_key = " + key;
        this.executeJCMSUpdate(query);
    }
    
    public void updateTimeUnit(TimeUnitDTO dto) throws Exception{
        String query = "UPDATE cv_TimeUnit SET "
                + "timeUnit = " + varcharParser(dto.getTimeUnit()) + ", "
                + "abbreviation = " + varcharParser(dto.getAbbreviation()) + ", "
                + "minutesPerUnit = " + numberParser(dto.getMinutesPerUnit()) 
                + " WHERE _timeUnit_key = " + dto.getTimeUnit_key();
        this.executeJCMSUpdate(query);
    }
    
    public void updateSampleStatus(SampleStatusDTO dto) throws Exception{
        String query = "UPDATE cv_SampleStatus SET "
                + "sampleStatus = " + varcharParser(dto.getSampleStatus()) + ", "
                + "isInStorage = " + numberParser(dto.getIsInStorage()) 
                + " WHERE _sampleStatus_key = " + dto.getSampleStatus_key();
        this.executeJCMSUpdate(query);
    }
}
