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

package jcms.integrationtier.dto;

import java.util.ArrayList;
import java.util.List;
import jcms.integrationtier.base.ITBaseDTO;

/**
 *
 * @author bas
 */
public class ExpDataDTO extends ITBaseDTO {
    public String expDataKey = "";
    public String testTypeKey = "";
    public String expTestKey = "";
    public String specimenKey = "";
    public String dataID = "";
    public String specimen_type = "";
    public String owner = "";
    public String expDate = "";
    public String age = "";
    public Boolean abnormalData = false;
    public String version = "";
    public List<ExpDataResultsDTO> d_Results = new ArrayList<ExpDataResultsDTO>();

//Saving these in case they prove useful
//    private String d1 = "";
//    private String d2 = "";
//    private String d3 = "";
//    private String d4 = "";
//    private String d5 = "";
//    private String d6 = "";
//    private String d7 = "";
//    private String d8 = "";
//    private String d9 = "";
//    private String d10 = "";
//    private String d11 = "";
//    private String d12 = "";
//    private String d13 = "";
//    private String d14 = "";
//    private String d15 = "";
//    private String d16 = "";
//    private String d17 = "";
//    private String d18 = "";
//    private String d19 = "";
//    private String d20 = "";
//    private String d21 = "";
//    private String d22 = "";
//    private String d23 = "";
//    private String d24 = "";
//    private String d25 = "";
//    private String d26 = "";
//    private String d27 = "";
//    private String d28 = "";
//    private String d29 = "";
//    private String d30 = ""; 
    
    public ExpDataDTO() {
        
    }
    
    public int getNumberOfD_Results() {
        return d_Results.size();
    }
    
    @Override
    public boolean equals(Object object){
        if(object instanceof ExpDataDTO){
            ExpDataDTO dto = (ExpDataDTO) object;
            if(getExpDataKey().equals(dto.getExpDataKey())){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * @return the expDataKey
     */
    public String getExpDataKey() {
        return expDataKey;
    }

    /**
     * @param expDataKey the expDataKey to set
     */
    public void setExpDataKey(String expDataKey) {
        this.expDataKey = expDataKey;
    }

    /**
     * @return the testTypeKey
     */
    public String getTestTypeKey() {
        return testTypeKey;
    }

    /**
     * @param testTypeKey the testTypeKey to set
     */
    public void setTestTypeKey(String testTypeKey) {
        this.testTypeKey = testTypeKey;
    }

    /**
     * @return the expTestKey
     */
    public String getExpTestKey() {
        return expTestKey;
    }

    /**
     * @param expTestKey the expTestKey to set
     */
    public void setExpTestKey(String expTestKey) {
        this.expTestKey = expTestKey;
    }

    /**
     * @return the specimenKey
     */
    public String getSpecimenKey() {
        return specimenKey;
    }

    /**
     * @param specimenKey the specimenKey to set
     */
    public void setSpecimenKey(String specimenKey) {
        this.specimenKey = specimenKey;
    }

    /**
     * @return the dataID
     */
    public String getDataID() {
        return dataID;
    }

    /**
     * @param dataID the dataID to set
     */
    public void setDataID(String dataID) {
        this.dataID = dataID;
    }

    /**
     * @return the specimen_type
     */
    public String getSpecimen_type() {
        return specimen_type;
    }

    /**
     * @param specimen_type the specimen_type to set
     */
    public void setSpecimen_type(String specimen_type) {
        this.specimen_type = specimen_type;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the expDate
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * @param expDate the expDate to set
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

//    /**
//     * @return the d1
//     */
//    public String getD1() {
//        return d1;
//    }
//
//    /**
//     * @param d1 the d1 to set
//     */
//    public void setD1(String d1) {
//        this.d1 = d1;
//    }
//
//    /**
//     * @return the d2
//     */
//    public String getD2() {
//        return d2;
//    }
//
//    /**
//     * @param d2 the d2 to set
//     */
//    public void setD2(String d2) {
//        this.d2 = d2;
//    }
//
//    /**
//     * @return the d3
//     */
//    public String getD3() {
//        return d3;
//    }
//
//    /**
//     * @param d3 the d3 to set
//     */
//    public void setD3(String d3) {
//        this.d3 = d3;
//    }
//
//    /**
//     * @return the d4
//     */
//    public String getD4() {
//        return d4;
//    }
//
//    /**
//     * @param d4 the d4 to set
//     */
//    public void setD4(String d4) {
//        this.d4 = d4;
//    }
//
//    /**
//     * @return the d5
//     */
//    public String getD5() {
//        return d5;
//    }
//
//    /**
//     * @param d5 the d5 to set
//     */
//    public void setD5(String d5) {
//        this.d5 = d5;
//    }
//
//    /**
//     * @return the d6
//     */
//    public String getD6() {
//        return d6;
//    }
//
//    /**
//     * @param d6 the d6 to set
//     */
//    public void setD6(String d6) {
//        this.d6 = d6;
//    }
//
//    /**
//     * @return the d7
//     */
//    public String getD7() {
//        return d7;
//    }
//
//    /**
//     * @param d7 the d7 to set
//     */
//    public void setD7(String d7) {
//        this.d7 = d7;
//    }
//
//    /**
//     * @return the d8
//     */
//    public String getD8() {
//        return d8;
//    }
//
//    /**
//     * @param d8 the d8 to set
//     */
//    public void setD8(String d8) {
//        this.d8 = d8;
//    }
//
//    /**
//     * @return the d9
//     */
//    public String getD9() {
//        return d9;
//    }
//
//    /**
//     * @param d9 the d9 to set
//     */
//    public void setD9(String d9) {
//        this.d9 = d9;
//    }
//
//    /**
//     * @return the d10
//     */
//    public String getD10() {
//        return d10;
//    }
//
//    /**
//     * @param d10 the d10 to set
//     */
//    public void setD10(String d10) {
//        this.d10 = d10;
//    }
//
//    /**
//     * @return the d11
//     */
//    public String getD11() {
//        return d11;
//    }
//
//    /**
//     * @param d11 the d11 to set
//     */
//    public void setD11(String d11) {
//        this.d11 = d11;
//    }
//
//    /**
//     * @return the d12
//     */
//    public String getD12() {
//        return d12;
//    }
//
//    /**
//     * @param d12 the d12 to set
//     */
//    public void setD12(String d12) {
//        this.d12 = d12;
//    }
//
//    /**
//     * @return the d13
//     */
//    public String getD13() {
//        return d13;
//    }
//
//    /**
//     * @param d13 the d13 to set
//     */
//    public void setD13(String d13) {
//        this.d13 = d13;
//    }
//
//    /**
//     * @return the d14
//     */
//    public String getD14() {
//        return d14;
//    }
//
//    /**
//     * @param d14 the d14 to set
//     */
//    public void setD14(String d14) {
//        this.d14 = d14;
//    }
//
//    /**
//     * @return the d15
//     */
//    public String getD15() {
//        return d15;
//    }
//
//    /**
//     * @param d15 the d15 to set
//     */
//    public void setD15(String d15) {
//        this.d15 = d15;
//    }
//
//    /**
//     * @return the d16
//     */
//    public String getD16() {
//        return d16;
//    }
//
//    /**
//     * @param d16 the d16 to set
//     */
//    public void setD16(String d16) {
//        this.d16 = d16;
//    }
//
//    /**
//     * @return the d17
//     */
//    public String getD17() {
//        return d17;
//    }
//
//    /**
//     * @param d17 the d17 to set
//     */
//    public void setD17(String d17) {
//        this.d17 = d17;
//    }
//
//    /**
//     * @return the d18
//     */
//    public String getD18() {
//        return d18;
//    }
//
//    /**
//     * @param d18 the d18 to set
//     */
//    public void setD18(String d18) {
//        this.d18 = d18;
//    }
//
//    /**
//     * @return the d19
//     */
//    public String getD19() {
//        return d19;
//    }
//
//    /**
//     * @param d19 the d19 to set
//     */
//    public void setD19(String d19) {
//        this.d19 = d19;
//    }
//
//    /**
//     * @return the d20
//     */
//    public String getD20() {
//        return d20;
//    }
//
//    /**
//     * @param d20 the d20 to set
//     */
//    public void setD20(String d20) {
//        this.d20 = d20;
//    }
//
//    /**
//     * @return the d21
//     */
//    public String getD21() {
//        return d21;
//    }
//
//    /**
//     * @param d21 the d21 to set
//     */
//    public void setD21(String d21) {
//        this.d21 = d21;
//    }
//
//    /**
//     * @return the d22
//     */
//    public String getD22() {
//        return d22;
//    }
//
//    /**
//     * @param d22 the d22 to set
//     */
//    public void setD22(String d22) {
//        this.d22 = d22;
//    }
//
//    /**
//     * @return the d23
//     */
//    public String getD23() {
//        return d23;
//    }
//
//    /**
//     * @param d23 the d23 to set
//     */
//    public void setD23(String d23) {
//        this.d23 = d23;
//    }
//
//    /**
//     * @return the d24
//     */
//    public String getD24() {
//        return d24;
//    }
//
//    /**
//     * @param d24 the d24 to set
//     */
//    public void setD24(String d24) {
//        this.d24 = d24;
//    }
//
//    /**
//     * @return the d25
//     */
//    public String getD25() {
//        return d25;
//    }
//
//    /**
//     * @param d25 the d25 to set
//     */
//    public void setD25(String d25) {
//        this.d25 = d25;
//    }
//
//    /**
//     * @return the d26
//     */
//    public String getD26() {
//        return d26;
//    }
//
//    /**
//     * @param d26 the d26 to set
//     */
//    public void setD26(String d26) {
//        this.d26 = d26;
//    }
//
//    /**
//     * @return the d27
//     */
//    public String getD27() {
//        return d27;
//    }
//
//    /**
//     * @param d27 the d27 to set
//     */
//    public void setD27(String d27) {
//        this.d27 = d27;
//    }
//
//    /**
//     * @return the d28
//     */
//    public String getD28() {
//        return d28;
//    }
//
//    /**
//     * @param d28 the d28 to set
//     */
//    public void setD28(String d28) {
//        this.d28 = d28;
//    }
//
//    /**
//     * @return the d29
//     */
//    public String getD29() {
//        return d29;
//    }
//
//    /**
//     * @param d29 the d29 to set
//     */
//    public void setD29(String d29) {
//        this.d29 = d29;
//    }
//
//    /**
//     * @return the d30
//     */
//    public String getD30() {
//        return d30;
//    }
//
//    /**
//     * @param d30 the d30 to set
//     */
//    public void setD30(String d30) {
//        this.d30 = d30;
//    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the abnormalData
     */
    public Boolean getAbnormalData() {
        return abnormalData;
    }

    /**
     * @param abnormalData the abnormalData to set
     */
    public void setAbnormalData(Boolean abnormalData) {
        this.abnormalData = abnormalData;
    }

    /**
     * @return the d_Results
     */
    public List<ExpDataResultsDTO> getD_Results() {
        return d_Results;
    }

    /**
     * @param d_Results the d_Results to set
     */
    public void setD_Results(List<ExpDataResultsDTO> d_Results) {
        this.d_Results = d_Results;
    }
 

}
