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
import java.util.HashMap;
import jcms.integrationtier.dto.StorageFacilityDTO;
import jcms.integrationtier.dto.LocationTypeDTO;

/**
 *
 * @author mkamato
 */
public class LocationDAO extends MySQLDAO {
    
    public ArrayList<StorageFacilityDTO> getAllStorageFacilities(){
        ArrayList<StorageFacilityDTO> storageFacilities = new ArrayList<StorageFacilityDTO>();
        String query = "SELECT * FROM cv_StorageFacility;";
        SortedMap[] facilities = this.executeJCMSQuery(query).getRows();
        for(SortedMap facility : facilities){
            StorageFacilityDTO dto = new StorageFacilityDTO();
            dto.setStorageFacility(myGet("storageFacility", facility));
            dto.setLabel(myGet("storageFacility", facility));
            dto.setStorageFacility_key(myGet("_storageFacility_key", facility));
            storageFacilities.add(dto);
        }
        return storageFacilities;
    }
    
    public ArrayList<LocationTypeDTO> getLocationTypesByStorageFacility(String storageFacilityKey){
        ArrayList<LocationTypeDTO> locationTypes = new ArrayList<LocationTypeDTO>();
        String query = "SELECT * FROM cv_LocationType WHERE _storageFacility_key = " + storageFacilityKey + ";";
        SortedMap[] locations = this.executeJCMSQuery(query).getRows();
        for(SortedMap location : locations){
            LocationTypeDTO dto = new LocationTypeDTO();
            dto.setLocationDetail(myGet("locationDetail", location));
            dto.setLocationType(myGet("locationType", location));
            dto.setLocationTypeRef(myGet("locationTypeRef", location));
            dto.setLocationType_key(myGet("_locationType_key", location));
            dto.setStorageFacility_key(myGet("_storageFacility_key", location));
            dto.setLabel(myGet("locationType", location));
            locationTypes.add(dto);
        }
        return locationTypes;
    }
    
    public HashMap<String, ArrayList<LocationTypeDTO>> getLocationTypesMapByStorageFacility(String storageFacilityKey){
        HashMap locationMap = new HashMap();
        String query = "SELECT * FROM cv_LocationType WHERE _storageFacility_key = " + storageFacilityKey + ";";
        SortedMap[] locations = this.executeJCMSQuery(query).getRows();
        for(SortedMap location : locations){
            LocationTypeDTO dto = new LocationTypeDTO();
            dto.setLocationDetail(myGet("locationDetail", location));
            dto.setLocationType(myGet("locationType", location));
            dto.setLocationTypeRef(myGet("locationTypeRef", location));
            dto.setLocationType_key(myGet("_locationType_key", location));
            dto.setStorageFacility_key(myGet("_storageFacility_key", location));
            dto.setLabel(myGet("locationType", location));
            if(locationMap.get(dto.getLocationTypeRef()) == null){
                ArrayList<LocationTypeDTO> list = new ArrayList<LocationTypeDTO>();
                list.add(dto);
                locationMap.put(dto.getLocationTypeRef(), list);
            }
            else{
                ArrayList<LocationTypeDTO> list = (ArrayList<LocationTypeDTO>) locationMap.get(dto.getLocationTypeRef());
                list.add(dto);
                locationMap.put(dto.getLocationTypeRef(), list);
            }
        }
        return locationMap;
    }
    
    public LocationTypeDTO getLocationTypeByKey(String locationTypeKey){
        LocationTypeDTO dto = new LocationTypeDTO();
        String query = "SELECT * FROM cv_LocationType WHERE _locationType_key = " + locationTypeKey;
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            dto.setLocationDetail(myGet("locationDetail", rows[0]));
            dto.setLocationType(myGet("locationType", rows[0]));
            dto.setLocationTypeRef(myGet("locationTypeRef", rows[0]));
            dto.setLocationType_key(myGet("_locationType_key", rows[0]));
            dto.setStorageFacility_key(myGet("_storageFacility_key", rows[0]));
            return dto;
        }
        else{
            return null;
        }
    }
    
    public StorageFacilityDTO getStorageFacilityByKey(String storageFacilityKey){
        StorageFacilityDTO dto = new StorageFacilityDTO();
        String query = "SELECT * FROM cv_StorageFacility WHERE _storageFacility_key = " + storageFacilityKey;    
        SortedMap[] rows = this.executeJCMSQuery(query).getRows();
        if(rows.length > 0){
            dto.setStorageFacility(myGet("storageFacility", rows[0]));
            dto.setStorageFacility_key(myGet("_storageFacility_key", rows[0]));
            return dto;
        }
        else{
            return null;
        }
    }
    
    public String insertStorageFacility(StorageFacilityDTO dto) throws Exception{
        String key = this.getMaxValue("_storageFacility_key", "cv_StorageFacility");
        key = new Integer(Integer.parseInt(key)+1).toString();
        String query = "INSERT INTO cv_StorageFacility "
                + "(_storageFacility_key, storageFacility) "
                + "VALUES ("
                + key + ", "
                + varcharParser(dto.getStorageFacility()) + ")";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public void updateStorageFacility(StorageFacilityDTO dto) throws Exception{
        String query = "UPDATE cv_StorageFacility SET "
                + "storageFacility = " + varcharParser(dto.getStorageFacility())
                + " WHERE _storageFacility_key = " + numberParser(dto.getStorageFacility_key());
                System.out.println(query);
                this.executeJCMSUpdate(query);
    }
    
    public String insertLocationType(LocationTypeDTO dto) throws Exception{
        String key = this.getMaxValue("_locationType_key", "cv_LocationType");
        key = new Integer(Integer.parseInt(key)+1).toString();
        if(dto.getLocationTypeRef().equals("")){
            dto.setLocationTypeRef("0");
        }
        String query = "INSERT INTO cv_LocationType "
                + "(_locationType_key, locationType, locationDetail, _storageFacility_key, locationTypeRef) "
                + "VALUES ("
                + key + ", "
                + varcharParser(dto.getLocationType()) + ", "
                + varcharParser(dto.getLocationDetail()) + ", "
                + numberParser(dto.getStorageFacility_key()) + ", "
                + numberParser(dto.getLocationTypeRef()) + ")";
        System.out.println(query);
        this.executeJCMSUpdate(query);
        return key;
    }
    
    public void updateLocationType(LocationTypeDTO dto) throws Exception{
        String query = "UPDATE cv_LocationType SET "
                + "locationType = " + varcharParser(dto.getLocationType()) + ", "
                + "locationDetail = " + varcharParser(dto.getLocationDetail())
                + " WHERE _locationType_key = " + numberParser(dto.getLocationType_key());
        
        System.out.println(query);
        this.executeJCMSUpdate(query);
    }
    
    public void updateLocationTypeRef(String parentKey, String childKey, String storageFacilityKey) throws Exception{
        //need to update the parent key and the storage facility key
            String query = "UPDATE cv_LocationType SET "
                    + "locationTypeRef = " + numberParser(parentKey) + ", "
                    + "_storageFacility_key = " + numberParser(storageFacilityKey)
                    + " WHERE _locationType_key = " + numberParser(childKey);
            System.out.println(query);
            this.executeJCMSUpdate(query);            
    }
    
    public void updateStorageFacilityRef(String childKey, String storageFacilityKey) throws Exception{
        //set location type ref to 0, storage facility key to new one
        String query = "UPDATE cv_LocationType SET "
                    + "locationTypeRef = " + numberParser("0") + ", "
                    + "_storageFacility_key = " + numberParser(storageFacilityKey) 
                    + " WHERE _locationType_key = " + numberParser(childKey);
        System.out.println(query);
        this.executeJCMSUpdate(query);
    }
    
    public boolean locationContainsSamples(String locationKey){
        String query = "SELECT * FROM Location WHERE _locationType_key = " + locationKey;
        if(this.executeJCMSQuery(query).getRowCount() > 0){
            return true;
        }
        return false;
    }
    
    public void deleteLocation(String locationKey) throws Exception {
        String query = "DELETE FROM cv_LocationType WHERE _locationType_key = " + locationKey;
        this.executeJCMSUpdate(query);
    }
    
    public void deleteStorageFacility(String storageFacilityKey) throws Exception {
        String query = "DELETE FROM cv_StorageFacility WHERE _storageFacility_key = " + storageFacilityKey;
        this.executeJCMSUpdate(query);
    }
}
