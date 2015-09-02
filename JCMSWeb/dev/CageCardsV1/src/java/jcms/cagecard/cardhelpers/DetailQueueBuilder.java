/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cardhelpers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.SortedMap;
/**
 *
 * @author mkamato
 */
public class DetailQueueBuilder {
    
    private LinkedList<String> IDQueue = new LinkedList<String>();
    private LinkedList<String> protocolQueue = new LinkedList<String>();
    private LinkedList<String> birthDateQueue = new LinkedList<String>();
    private LinkedList<String> genotypeQueue = new LinkedList<String>();
    private LinkedList<String> generationQueue = new LinkedList<String>();
    private LinkedList<String> strainNameQueue = new LinkedList<String>();
    private LinkedList<String> sexQueue = new LinkedList<String>();
    private LinkedList<String> commentQueue = new LinkedList<String>();
    private LinkedList<String> weanDateQueue = new LinkedList<String>();
    private LinkedList<String> lifeStatusQueue = new LinkedList<String>();
    private LinkedList<String> breedingStatusQueue = new LinkedList<String>();
    private LinkedList<String> originQueue = new LinkedList<String>();
    private LinkedList<String> litterIDQueue = new LinkedList<String>();
    private LinkedList<String> jrNumQueue = new LinkedList<String>();
    private LinkedList<String> dietQueue = new LinkedList<String>();
    private LinkedList<String> exitDateQueue = new LinkedList<String>();
    private LinkedList<String> CODQueue = new LinkedList<String>();
    private LinkedList<String> coatColorQueue = new LinkedList<String>();
    private LinkedList<String> CODNotesQueue = new LinkedList<String>();
    private LinkedList<String> ownerQueue = new LinkedList<String>();
    private LinkedList<String> newTagQueue = new LinkedList<String>();
    private LinkedList<String> matingIDQueue = new LinkedList<String>();
    private String containerName;
    private String containerID;
    private String MTSValue;
    private String PIPhone = "";
    private String PIName = "";
    private String activationDate = "";
    private String room = "";
    private String containerComment = "";
    private String containerStatus = "";
    private String detailCardNote = "";
    
    private SortedMap[] results;
    private ArrayList<String> wgList;
    
    public DetailQueueBuilder(SortedMap[] theResults, SortedMap[] dbSetupResults, ArrayList<String> theWgList){
        results = theResults;
        wgList = theWgList;
        if(results.length == 0){
            containerName = "";
            containerID = "";
            MTSValue= "false";
            if(dbSetupResults.length > 0){
                PIName = myGet(dbSetupResults[0],"PIName");
                PIPhone = myGet(dbSetupResults[0],"PIPhone");
                detailCardNote = myGet(dbSetupResults[0],"detailCardNote");
            }
        }
        else{
            containerName = myGet(results[0], "containerName");
            containerID = myGet(results[0], "containerID");
            MTSValue= myGet(results[0], "showExitedMice");
            PIPhone = myGet(results[0], "PIPhone");
            PIName = myGet(results[0], "PIName");
            activationDate =myGet(results[0], "ActivationDate");
            room = myGet(results[0], "roomName");
            containerComment = myGet(results[0],"containerComment");
            containerStatus = myGet(results[0],"containerStatus");
            detailCardNote = myGet(results[0],"detailCardNote");
        }
    }

    
    public void parseResults(){
        for (SortedMap mouse : getResults()) {
            if (printExitedMice(mouse)) {
                if (myContains(wgList, myGet(mouse, "owner"))) {
                    if (getIDQueue().contains(mouse.get("ID").toString())) {
                        if (mouse.get("gene") != null) {
                            getGenotypeQueue().add(getGenotypeQueue().pollLast() + ", " + mouse.get("gene"));
                        }
                    } 
                    else {
                        add(getIDQueue(), mouse.get("ID"));
                        add(getBirthDateQueue(), mouse.get("birthDate"));
                        add(getGenotypeQueue(), mouse.get("gene"));
                        add(getGenerationQueue(), mouse.get("generation"));
                        add(getStrainNameQueue(), mouse.get("strainName"));
                        add(getSexQueue(), mouse.get("sex"));
                        add(getLifeStatusQueue(), mouse.get("lifeStatus"));
                        add(getBreedingStatusQueue(), mouse.get("breedingStatus"));
                        add(getOriginQueue(), mouse.get("origin"));
                        add(getLitterIDQueue(), mouse.get("litterID"));
                        add(getJrNumQueue(), mouse.get("jrNum"));
                        add(getDietQueue(), mouse.get("diet"));
                        add(getWeanDateQueue(), mouse.get("weanDate"));
                        add(getExitDateQueue(), mouse.get("exitDate"));
                        add(getProtocolQueue(), mouse.get("protocol"));
                        add(getCODQueue(), mouse.get("cod"));
                        add(getCommentQueue(), mouse.get("comment"));
                        add(getCoatColorQueue(), mouse.get("coatColor"));
                        add(getCODNotesQueue(), mouse.get("codnotes"));
                        add(getOwnerQueue(), mouse.get("owner"));
                        add(getNewTagQueue(), mouse.get("newTag"));
                        add(getMatingIDQueue(), mouse.get("matingID"));
                    }
                }
                else{
                    System.out.println("You don't own this mouse...");
                }
            }
        }
    }
    
    public boolean printExitedMice(SortedMap mouse){
        boolean flag = false;
        if(MTSValue.equals("true")){
            return true;
        }            
        else{
            if(myGet(mouse,"lifeStatus").equals("A")){
                return true;
            }
        }
        return flag;
    }
    
    public void add(LinkedList theQueue, Object obj){
        if(obj!=null){
            theQueue.add(obj.toString());
        }
        else{
            theQueue.add("");
        }
    }
    
    public String myGet(SortedMap result, String key){
        if(result.get(key) != null){
            return result.get(key).toString();
        }
        else{
            return "";
        }
    }
    
    public boolean myContains(ArrayList<String> wgs, String workGroup){
        boolean flag = false;
        for(String theWorkgroup: wgs){
            if(theWorkgroup.equalsIgnoreCase(workGroup)){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @return the IDQueue
     */
    public LinkedList<String> getIDQueue() {
        return IDQueue;
    }

    /**
     * @param IDQueue the IDQueue to set
     */
    public void setIDQueue(LinkedList<String> IDQueue) {
        this.IDQueue = IDQueue;
    }

    /**
     * @return the birthDateQueue
     */
    public LinkedList<String> getBirthDateQueue() {
        return birthDateQueue;
    }

    /**
     * @param birthDateQueue the birthDateQueue to set
     */
    public void setBirthDateQueue(LinkedList<String> birthDateQueue) {
        this.birthDateQueue = birthDateQueue;
    }

    /**
     * @return the genotypeQueue
     */
    public LinkedList<String> getGenotypeQueue() {
        return genotypeQueue;
    }

    /**
     * @param genotypeQueue the genotypeQueue to set
     */
    public void setGenotypeQueue(LinkedList<String> genotypeQueue) {
        this.genotypeQueue = genotypeQueue;
    }

    /**
     * @return the generationQueue
     */
    public LinkedList<String> getGenerationQueue() {
        return generationQueue;
    }

    /**
     * @param generationQueue the generationQueue to set
     */
    public void setGenerationQueue(LinkedList<String> generationQueue) {
        this.generationQueue = generationQueue;
    }

    /**
     * @return the strainNameQueue
     */
    public LinkedList<String> getStrainNameQueue() {
        return strainNameQueue;
    }

    /**
     * @param strainNameQueue the strainNameQueue to set
     */
    public void setStrainNameQueue(LinkedList<String> strainNameQueue) {
        this.strainNameQueue = strainNameQueue;
    }

    /**
     * @return the sexQueue
     */
    public LinkedList<String> getSexQueue() {
        return sexQueue;
    }

    /**
     * @param sexQueue the sexQueue to set
     */
    public void setSexQueue(LinkedList<String> sexQueue) {
        this.sexQueue = sexQueue;
    }

    /**
     * @return the lifeStatusQueue
     */
    public LinkedList<String> getLifeStatusQueue() {
        return lifeStatusQueue;
    }

    /**
     * @param lifeStatusQueue the lifeStatusQueue to set
     */
    public void setLifeStatusQueue(LinkedList<String> lifeStatusQueue) {
        this.lifeStatusQueue = lifeStatusQueue;
    }

    /**
     * @return the breedingStatusQueue
     */
    public LinkedList<String> getBreedingStatusQueue() {
        return breedingStatusQueue;
    }

    /**
     * @param breedingStatusQueue the breedingStatusQueue to set
     */
    public void setBreedingStatusQueue(LinkedList<String> breedingStatusQueue) {
        this.breedingStatusQueue = breedingStatusQueue;
    }

    /**
     * @return the originQueue
     */
    public LinkedList<String> getOriginQueue() {
        return originQueue;
    }

    /**
     * @param originQueue the originQueue to set
     */
    public void setOriginQueue(LinkedList<String> originQueue) {
        this.originQueue = originQueue;
    }

    /**
     * @return the litterIDQueue
     */
    public LinkedList<String> getLitterIDQueue() {
        return litterIDQueue;
    }

    /**
     * @param litterIDQueue the litterIDQueue to set
     */
    public void setLitterIDQueue(LinkedList<String> litterIDQueue) {
        this.litterIDQueue = litterIDQueue;
    }

    /**
     * @return the jrNumQueue
     */
    public LinkedList<String> getJrNumQueue() {
        return jrNumQueue;
    }

    /**
     * @param jrNumQueue the jrNumQueue to set
     */
    public void setJrNumQueue(LinkedList<String> jrNumQueue) {
        this.jrNumQueue = jrNumQueue;
    }

    /**
     * @return the dietQueue
     */
    public LinkedList<String> getDietQueue() {
        return dietQueue;
    }

    /**
     * @param dietQueue the dietQueue to set
     */
    public void setDietQueue(LinkedList<String> dietQueue) {
        this.dietQueue = dietQueue;
    }

    /**
     * @return the exitDateQueue
     */
    public LinkedList<String> getExitDateQueue() {
        return exitDateQueue;
    }

    /**
     * @param exitDateQueue the exitDateQueue to set
     */
    public void setExitDateQueue(LinkedList<String> exitDateQueue) {
        this.exitDateQueue = exitDateQueue;
    }

    /**
     * @return the CODQueue
     */
    public LinkedList<String> getCODQueue() {
        return CODQueue;
    }

    /**
     * @param CODQueue the CODQueue to set
     */
    public void setCODQueue(LinkedList<String> CODQueue) {
        this.CODQueue = CODQueue;
    }

    /**
     * @return the coatColorQueue
     */
    public LinkedList<String> getCoatColorQueue() {
        return coatColorQueue;
    }

    /**
     * @param coatColorQueue the coatColorQueue to set
     */
    public void setCoatColorQueue(LinkedList<String> coatColorQueue) {
        this.coatColorQueue = coatColorQueue;
    }

    /**
     * @return the CODNotesQueue
     */
    public LinkedList<String> getCODNotesQueue() {
        return CODNotesQueue;
    }

    /**
     * @param CODNotesQueue the CODNotesQueue to set
     */
    public void setCODNotesQueue(LinkedList<String> CODNotesQueue) {
        this.CODNotesQueue = CODNotesQueue;
    }

    /**
     * @return the ownerQueue
     */
    public LinkedList<String> getOwnerQueue() {
        return ownerQueue;
    }

    /**
     * @param ownerQueue the ownerQueue to set
     */
    public void setOwnerQueue(LinkedList<String> ownerQueue) {
        this.ownerQueue = ownerQueue;
    }

    /**
     * @return the containerName
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * @param containerName the containerName to set
     */
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    /**
     * @return the containerID
     */
    public String getContainerID() {
        return containerID;
    }

    /**
     * @param containerID the containerID to set
     */
    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    /**
     * @return the results
     */
    public SortedMap[] getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(SortedMap[] results) {
        this.results = results;
    }

    /**
     * @return the PIPhone
     */
    public String getPIPhone() {
        return PIPhone;
    }

    /**
     * @param PIPhone the PIPhone to set
     */
    public void setPIPhone(String PIPhone) {
        this.PIPhone = PIPhone;
    }

    /**
     * @return the PIName
     */
    public String getPIName() {
        return PIName;
    }

    /**
     * @param PIName the PIName to set
     */
    public void setPIName(String PIName) {
        this.PIName = PIName;
    }

    /**
     * @return the activationDate
     */
    public String getActivationDate() {
        return activationDate;
    }

    /**
     * @param activationDate the activationDate to set
     */
    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * @return the protocolQueue
     */
    public LinkedList<String> getProtocolQueue() {
        return protocolQueue;
    }

    /**
     * @param protocolQueue the protocolQueue to set
     */
    public void setProtocolQueue(LinkedList<String> protocolQueue) {
        this.protocolQueue = protocolQueue;
    }

    /**
     * @return the commentQueue
     */
    public LinkedList<String> getCommentQueue() {
        return commentQueue;
    }

    /**
     * @param commentQueue the commentQueue to set
     */
    public void setCommentQueue(LinkedList<String> commentQueue) {
        this.commentQueue = commentQueue;
    }

    /**
     * @return the weanDateQueue
     */
    public LinkedList<String> getWeanDateQueue() {
        return weanDateQueue;
    }

    /**
     * @param weanDateQueue the weanDateQueue to set
     */
    public void setWeanDateQueue(LinkedList<String> weanDateQueue) {
        this.weanDateQueue = weanDateQueue;
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
     * @return the containerComment
     */
    public String getContainerComment() {
        return containerComment;
    }

    /**
     * @param containerComment the containerComment to set
     */
    public void setContainerComment(String containerComment) {
        this.containerComment = containerComment;
    }

    /**
     * @return the containerStatus
     */
    public String getContainerStatus() {
        return containerStatus;
    }

    /**
     * @param containerStatus the containerStatus to set
     */
    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    /**
     * @return the newTagQueue
     */
    public LinkedList<String> getNewTagQueue() {
        return newTagQueue;
    }

    /**
     * @param newTagQueue the newTagQueue to set
     */
    public void setNewTagQueue(LinkedList<String> newTagQueue) {
        this.newTagQueue = newTagQueue;
    }

    /**
     * @return the detailCardNote
     */
    public String getDetailCardNote() {
        return detailCardNote;
    }

    /**
     * @param detailCardNote the detailCardNote to set
     */
    public void setDetailCardNote(String detailCardNote) {
        this.detailCardNote = detailCardNote;
    }

    /**
     * @return the matingIDQueue
     */
    public LinkedList<String> getMatingIDQueue() {
        return matingIDQueue;
    }

    /**
     * @param matingIDQueue the matingIDQueue to set
     */
    public void setMatingIDQueue(LinkedList<String> matingIDQueue) {
        this.matingIDQueue = matingIDQueue;
    }
}
