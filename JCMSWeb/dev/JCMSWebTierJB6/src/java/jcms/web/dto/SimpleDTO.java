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

package jcms.web.dto;

import java.util.ArrayList;

/**
 *
 * @author rkavitha
 */
public class SimpleDTO {

    private String strainName = "";
    private String owner = "";
    private String room = "";
    private String status = "";

    private String currentDate = "";

    private String expPlanName = "";

    private ArrayList<DisplayDTO> statusLst = new ArrayList<DisplayDTO>();

    private int numberOfMice = 0;

    private long liveMiceCnt = 0;
    private int strainCnt = 0;

    private long penCnt = 0;
    private int roomCnt = 0;
    private long penCntByOwner = 0;

    private long miceCnt = 0;
    private long testsCnt = 0;

    /**
     * @return the strainName
     */
    public String getStrainName() {
        return strainName;
    }

    /**
     * @param strainName the strainName to set
     */
    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    /**
     * @return the numberOfMice
     */
    public int getNumberOfMice() {
        return numberOfMice;
    }

    /**
     * @param numberOfMice the numberOfMice to set
     */
    public void setNumberOfMice(int numberOfMice) {
        this.numberOfMice = numberOfMice;
    }

    /**
     * @return the liveMiceCnt
     */
    public long getLiveMiceCnt() {
        return liveMiceCnt;
    }

    /**
     * @param liveMiceCnt the liveMiceCnt to set
     */
    public void setLiveMiceCnt(long liveMiceCnt) {
        this.liveMiceCnt = liveMiceCnt;
    }

    /**
     * @return the strainCnt
     */
    public int getStrainCnt() {
        return strainCnt;
    }

    /**
     * @param strainCnt the strainCnt to set
     */
    public void setStrainCnt(int strainCnt) {
        this.strainCnt = strainCnt;
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
     * @return the penCnt
     */
    public long getPenCnt() {
        return penCnt;
    }

    /**
     * @param penCnt the penCnt to set
     */
    public void setPenCnt(long penCnt) {
        this.penCnt = penCnt;
    }

    /**
     * @return the roomCnt
     */
    public int getRoomCnt() {
        return roomCnt;
    }

    /**
     * @param roomCnt the roomCnt to set
     */
    public void setRoomCnt(int roomCnt) {
        this.roomCnt = roomCnt;
    }

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the penCntByOwner
     */
    public long getPenCntByOwner() {
        return penCntByOwner;
    }

    /**
     * @param penCntByOwner the penCntByOwner to set
     */
    public void setPenCntByOwner(long penCntByOwner) {
        this.penCntByOwner = penCntByOwner;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the statusLst
     */
    public ArrayList<DisplayDTO> getStatusLst() {
        return statusLst;
    }

    /**
     * @param statusLst the statusLst to set
     */
    public void setStatusLst(ArrayList<DisplayDTO> statusLst) {
        this.statusLst = statusLst;
    }

    /**
     * @return the expPlanName
     */
    public String getExpPlanName() {
        return expPlanName;
    }

    /**
     * @param expPlanName the expPlanName to set
     */
    public void setExpPlanName(String expPlanName) {
        this.expPlanName = expPlanName;
    }

    /**
     * @return the miceCnt
     */
    public long getMiceCnt() {
        return miceCnt;
    }

    /**
     * @param miceCnt the miceCnt to set
     */
    public void setMiceCnt(long miceCnt) {
        this.miceCnt = miceCnt;
    }

    /**
     * @return the testsCnt
     */
    public long getTestsCnt() {
        return testsCnt;
    }

    /**
     * @param testsCnt the testsCnt to set
     */
    public void setTestsCnt(long testsCnt) {
        this.testsCnt = testsCnt;
    }

    /**
     * @return the currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}