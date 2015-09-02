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

/**
 *
 * @author bas
 */

import jcms.web.base.WTBaseBackingBean;     

public class PageBuilder extends WTBaseBackingBean {

    //mouse
    private Boolean hideLitterID = false;
    private Boolean hideUseSchedules = false;
    private Boolean hideProtocolID  = false;
    private String defaultProtocolId = "";
    private Boolean hideOrigin = false;
    private String defaultOrigin = "";
    private Boolean hideCOD = false;
    private String defaultCOD = "";
    private Boolean hideCODNotes = false;
    private Boolean hideRoomName = false;
    private String defaultRoomName ="";
    private Boolean hidePhenotypes = false;
    private Boolean hideMouseComment = false;
    private Boolean hideCoatColor = false;
    private String defaultCoatColor = "";
    private Boolean hideDiet = false;
    private String defaultDiet = "";
    private Boolean hideSampleVialID = false;
    private Boolean hideSampleVialTagPosition = false;
    private String defaultSex = "";
    private String defaultBreedingStatus = "";
    private Boolean hideReplacementTag = false;
    //mating
    private Boolean hideWeanNote = false;
    private Boolean hideMatingComment = false;
    private Boolean hideWeanTime = false;
    private String defaultWeanTime = "";
    private Boolean hideNeedsTyping = false;
    private String defaultNeedsTyping = "";
    private Boolean hideMatingDiet = false;
    private String defaultMatingDiet = "";
    private Boolean hideMatingDam2 = false;
    //litter
    private Boolean hideNumberBornDead = false;
    private Boolean hideNumberCulledAtWean = false;
    private Boolean hideNumberMissingAtWean = false;
    private Boolean hideLitterType = false;
    private Boolean hideLitterComments = false;
    private Boolean hideLitterUseSchedules = false;
    private Boolean hideLitterProtocolID  = false;
    private String defaultLitterProtocolId = "";
    private Boolean hideLitterOrigin = false;
    private String defaultLitterOrigin = "";
    private Boolean hideLeavePupsInMatingPen = false;
    private String defaultLeavePupsInMatingPen = "";
    

    public Boolean getHideUseSchedules(){
       return hideUseSchedules;
}

    public void setHideUseSchedules(Boolean hideUseSchedules){
       this.hideUseSchedules = hideUseSchedules;
}

    /**
     * @return the hideProtocolID
     */
    public Boolean getHideProtocolID() {
        return hideProtocolID;
    }

    /**
     * @param hideProtocolID the hideProtocolID to set
     */
    public void setHideProtocolID(Boolean hideProtocolID) {
        this.hideProtocolID = hideProtocolID;
    }

    /**
     * @return the defaultProtocolId
     */
    public String getDefaultProtocolId() {
        return defaultProtocolId;
    }

    /**
     * @param defaultProtocolId the defaultProtocolId to set
     */
    public void setDefaultProtocolId(String defaultProtocolId) {
        this.defaultProtocolId = defaultProtocolId;
    }

    /**
     * @return the hideOrigin
     */
    public Boolean getHideOrigin() {
        return hideOrigin;
    }

    /**
     * @param hideOrigin the hideOrigin to set
     */
    public void setHideOrigin(Boolean hideOrigin) {
        this.hideOrigin = hideOrigin;
    }

    /**
     * @return the defaultOrigin
     */
    public String getDefaultOrigin() {
        return defaultOrigin;
    }

    /**
     * @param defaultOrigin the defaultOrigin to set
     */
    public void setDefaultOrigin(String defaultOrigin) {
        this.defaultOrigin = defaultOrigin;
    }

    /**
     * @return the hideLitterID
     */
    public Boolean getHideLitterID() {
        return hideLitterID;
    }

    /**
     * @param hideLitterID the hideLitterID to set
     */
    public void setHideLitterID(Boolean hideLitterID) {
        this.hideLitterID = hideLitterID;
    }

    /**
     * @return the hideCOD
     */
    public Boolean getHideCOD() {
        return hideCOD;
    }

    /**
     * @param hideCOD the hideCOD to set
     */
    public void setHideCOD(Boolean hideCOD) {
        this.hideCOD = hideCOD;
    }

    /**
     * @return the defaultCOD
     */
    public String getDefaultCOD() {
        return defaultCOD;
    }

    /**
     * @param defaultCOD the defaultCOD to set
     */
    public void setDefaultCOD(String defaultCOD) {
        this.defaultCOD = defaultCOD;
    }

    /**
     * @return the hideCODNotes
     */
    public Boolean getHideCODNotes() {
        return hideCODNotes;
    }

    /**
     * @param hideCODNotes the hideCODNotes to set
     */
    public void setHideCODNotes(Boolean hideCODNotes) {
        this.hideCODNotes = hideCODNotes;
    }

    /**
     * @return the hideRoomName
     */
    public Boolean getHideRoomName() {
        return hideRoomName;
    }

    /**
     * @param hideRoomName the hideRoomName to set
     */
    public void setHideRoomName(Boolean hideRoomName) {
        this.hideRoomName = hideRoomName;
    }

    /**
     * @return the defaultRoomName
     */
    public String getDefaultRoomName() {
        return defaultRoomName;
    }

    /**
     * @param defaultRoomName the defaultRoomName to set
     */
    public void setDefaultRoomName(String defaultRoomName) {
        this.defaultRoomName = defaultRoomName;
    }

    /**
     * @return the hidePhenotypes
     */
    public Boolean getHidePhenotypes() {
        return hidePhenotypes;
    }

    /**
     * @param hidePhenotypes the hidePhenotypes to set
     */
    public void setHidePhenotypes(Boolean hidePhenotypes) {
        this.hidePhenotypes = hidePhenotypes;
    }

    /**
     * @return the hideMouseComment
     */
    public Boolean getHideMouseComment() {
        return hideMouseComment;
    }

    /**
     * @param hideMouseComment the hideMouseComment to set
     */
    public void setHideMouseComment(Boolean hideMouseComment) {
        this.hideMouseComment = hideMouseComment;
    }

    /**
     * @return the hideCoatColor
     */
    public Boolean getHideCoatColor() {
        return hideCoatColor;
    }

    /**
     * @param hideCoatColor the hideCoatColor to set
     */
    public void setHideCoatColor(Boolean hideCoatColor) {
        this.hideCoatColor = hideCoatColor;
    }

    /**
     * @return the hideDiet
     */
    public Boolean getHideDiet() {
        return hideDiet;
    }

    /**
     * @param hideDiet the hideDiet to set
     */
    public void setHideDiet(Boolean hideDiet) {
        this.hideDiet = hideDiet;
    }

    /**
     * @return the defaultCoatColor
     */
    public String getDefaultCoatColor() {
        return defaultCoatColor;
    }

    /**
     * @param defaultCoatColor the defaultCoatColor to set
     */
    public void setDefaultCoatColor(String defaultCoatColor) {
        this.defaultCoatColor = defaultCoatColor;
    }

    /**
     * @return the defaultDiet
     */
    public String getDefaultDiet() {
        return defaultDiet;
    }

    /**
     * @param defaultDiet the defaultDiet to set
     */
    public void setDefaultDiet(String defaultDiet) {
        this.defaultDiet = defaultDiet;
    }

    /**
     * @return the hideSampleVialID
     */
    public Boolean getHideSampleVialID() {
        return hideSampleVialID;
    }

    /**
     * @param hideSampleVialID the hideSampleVialID to set
     */
    public void setHideSampleVialID(Boolean hideSampleVialID) {
        this.hideSampleVialID = hideSampleVialID;
    }

    /**
     * @return the hideSampleVialTagPosition
     */
    public Boolean getHideSampleVialTagPosition() {
        return hideSampleVialTagPosition;
    }

    /**
     * @param hideSampleVialTagPosition the hideSampleVialTagPosition to set
     */
    public void setHideSampleVialTagPosition(Boolean hideSampleVialTagPosition) {
        this.hideSampleVialTagPosition = hideSampleVialTagPosition;
    }

    /**
     * @return the defaultSex
     */
    public String getDefaultSex() {
        return defaultSex;
    }

    /**
     * @param defaultSex the defaultSex to set
     */
    public void setDefaultSex(String defaultSex) {
        this.defaultSex = defaultSex;
    }

    /**
     * @return the defaultBreedingStatus
     */
    public String getDefaultBreedingStatus() {
        return defaultBreedingStatus;
    }

    /**
     * @param defaultBreedingStatus the defaultBreedingStatus to set
     */
    public void setDefaultBreedingStatus(String defaultBreedingStatus) {
        this.defaultBreedingStatus = defaultBreedingStatus;
    }

    /**
     * @return the hideReplacementTag
     */
    public Boolean getHideReplacementTag() {
        return hideReplacementTag;
    }

    /**
     * @param hideReplacementTag the hideReplacementTag to set
     */
    public void setHideReplacementTag(Boolean hideReplacementTag) {
        this.hideReplacementTag = hideReplacementTag;
    }

    /**
     * @return the hideWeanNote
     */
    public Boolean getHideWeanNote() {
        return hideWeanNote;
    }

    /**
     * @param hideWeanNote the hideWeanNote to set
     */
    public void setHideWeanNote(Boolean hideWeanNote) {
        this.hideWeanNote = hideWeanNote;
    }

    /**
     * @return the hideMatingComment
     */
    public Boolean getHideMatingComment() {
        return hideMatingComment;
    }

    /**
     * @param hideMatingComment the hideMatingComment to set
     */
    public void setHideMatingComment(Boolean hideMatingComment) {
        this.hideMatingComment = hideMatingComment;
    }

    /**
     * @return the hideWeanTime
     */
    public Boolean getHideWeanTime() {
        return hideWeanTime;
    }

    /**
     * @param hideWeanTime the hideWeanTime to set
     */
    public void setHideWeanTime(Boolean hideWeanTime) {
        this.hideWeanTime = hideWeanTime;
    }

    /**
     * @return the defaultWeanTime
     */
    public String getDefaultWeanTime() {
        return defaultWeanTime;
    }

    /**
     * @param defaultWeanTime the defaultWeanTime to set
     */
    public void setDefaultWeanTime(String defaultWeanTime) {
        this.defaultWeanTime = defaultWeanTime;
    }

    /**
     * @return the hideNeedsTyping
     */
    public Boolean getHideNeedsTyping() {
        return hideNeedsTyping;
    }

    /**
     * @param hideNeedsTyping the hideNeedsTyping to set
     */
    public void setHideNeedsTyping(Boolean hideNeedsTyping) {
        this.hideNeedsTyping = hideNeedsTyping;
    }

    /**
     * @return the defaultNeedsTyping
     */
    public String getDefaultNeedsTyping() {
        return defaultNeedsTyping;
    }

    /**
     * @param defaultNeedsTyping the defaultNeedsTyping to set
     */
    public void setDefaultNeedsTyping(String defaultNeedsTyping) {
        this.defaultNeedsTyping = defaultNeedsTyping;
    }

    /**
     * @return the hideMatingDiet
     */
    public Boolean getHideMatingDiet() {
        return hideMatingDiet;
    }

    /**
     * @param hideMatingDiet the hideMatingDiet to set
     */
    public void setHideMatingDiet(Boolean hideMatingDiet) {
        this.hideMatingDiet = hideMatingDiet;
    }

    /**
     * @return the defaultMatingDiet
     */
    public String getDefaultMatingDiet() {
        return defaultMatingDiet;
    }

    /**
     * @param defaultMatingDiet the defaultMatingDiet to set
     */
    public void setDefaultMatingDiet(String defaultMatingDiet) {
        this.defaultMatingDiet = defaultMatingDiet;
    }

    /**
     * @return the hideMatingDam2
     */
    public Boolean getHideMatingDam2() {
        return hideMatingDam2;
    }

    /**
     * @param hideMatingDam2 the hideMatingDam2 to set
     */
    public void setHideMatingDam2(Boolean hideMatingDam2) {
        this.hideMatingDam2 = hideMatingDam2;
    }

    /**
     * @return the hideNumberBornDead
     */
    public Boolean getHideNumberBornDead() {
        return hideNumberBornDead;
    }

    /**
     * @param hideNumberBornDead the hideNumberBornDead to set
     */
    public void setHideNumberBornDead(Boolean hideNumberBornDead) {
        this.hideNumberBornDead = hideNumberBornDead;
    }

    /**
     * @return the hideNumberCulledAtWean
     */
    public Boolean getHideNumberCulledAtWean() {
        return hideNumberCulledAtWean;
    }

    /**
     * @param hideNumberCulledAtWean the hideNumberCulledAtWean to set
     */
    public void setHideNumberCulledAtWean(Boolean hideNumberCulledAtWean) {
        this.hideNumberCulledAtWean = hideNumberCulledAtWean;
    }

    /**
     * @return the hideNumberMissingAtWean
     */
    public Boolean getHideNumberMissingAtWean() {
        return hideNumberMissingAtWean;
    }

    /**
     * @param hideNumberMissingAtWean the hideNumberMissingAtWean to set
     */
    public void setHideNumberMissingAtWean(Boolean hideNumberMissingAtWean) {
        this.hideNumberMissingAtWean = hideNumberMissingAtWean;
    }

    /**
     * @return the hideLitterType
     */
    public Boolean getHideLitterType() {
        return hideLitterType;
    }

    /**
     * @param hideLitterType the hideLitterType to set
     */
    public void setHideLitterType(Boolean hideLitterType) {
        this.hideLitterType = hideLitterType;
    }

    /**
     * @return the hideLitterComments
     */
    public Boolean getHideLitterComments() {
        return hideLitterComments;
    }

    /**
     * @param hideLitterComments the hideLitterComments to set
     */
    public void setHideLitterComments(Boolean hideLitterComments) {
        this.hideLitterComments = hideLitterComments;
    }

    /**
     * @return the hideLitterUseSchedules
     */
    public Boolean getHideLitterUseSchedules() {
        return hideLitterUseSchedules;
    }

    /**
     * @param hideLitterUseSchedules the hideLitterUseSchedules to set
     */
    public void setHideLitterUseSchedules(Boolean hideLitterUseSchedules) {
        this.hideLitterUseSchedules = hideLitterUseSchedules;
    }

    /**
     * @return the hideLitterProtocolID
     */
    public Boolean getHideLitterProtocolID() {
        return hideLitterProtocolID;
    }

    /**
     * @param hideLitterProtocolID the hideLitterProtocolID to set
     */
    public void setHideLitterProtocolID(Boolean hideLitterProtocolID) {
        this.hideLitterProtocolID = hideLitterProtocolID;
    }

    /**
     * @return the defaultLitterProtocolId
     */
    public String getDefaultLitterProtocolId() {
        return defaultLitterProtocolId;
    }

    /**
     * @param defaultLitterProtocolId the defaultLitterProtocolId to set
     */
    public void setDefaultLitterProtocolId(String defaultLitterProtocolId) {
        this.defaultLitterProtocolId = defaultLitterProtocolId;
    }

    /**
     * @return the hideLitterOrigin
     */
    public Boolean getHideLitterOrigin() {
        return hideLitterOrigin;
    }

    /**
     * @param hideLitterOrigin the hideLitterOrigin to set
     */
    public void setHideLitterOrigin(Boolean hideLitterOrigin) {
        this.hideLitterOrigin = hideLitterOrigin;
    }

    /**
     * @return the defaultLitterOrigin
     */
    public String getDefaultLitterOrigin() {
        return defaultLitterOrigin;
    }

    /**
     * @param defaultLitterOrigin the defaultLitterOrigin to set
     */
    public void setDefaultLitterOrigin(String defaultLitterOrigin) {
        this.defaultLitterOrigin = defaultLitterOrigin;
    }

    /**
     * @return the hideLeavePupsInMatingPen
     */
    public Boolean getHideLeavePupsInMatingPen() {
        return hideLeavePupsInMatingPen;
    }

    /**
     * @param hideLeavePupsInMatingPen the hideLeavePupsInMatingPen to set
     */
    public void setHideLeavePupsInMatingPen(Boolean hideLeavePupsInMatingPen) {
        this.hideLeavePupsInMatingPen = hideLeavePupsInMatingPen;
    }

    /**
     * @return the defaultLeavePupsInMatingPen
     */
    public String getDefaultLeavePupsInMatingPen() {
        return defaultLeavePupsInMatingPen;
    }

    /**
     * @param defaultLeavePupsInMatingPen the defaultLeavePupsInMatingPen to set
     */
    public void setDefaultLeavePupsInMatingPen(String defaultLeavePupsInMatingPen) {
        this.defaultLeavePupsInMatingPen = defaultLeavePupsInMatingPen;
    }

}
