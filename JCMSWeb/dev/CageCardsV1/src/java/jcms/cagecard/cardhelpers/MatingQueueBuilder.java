/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcms.cagecard.cardhelpers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedMap;

/**
 *
 * @author mkamato
 */
public class MatingQueueBuilder {

    private SortedMap[] results;
    private SortedMap[] dbSetupResults;
    private ArrayList<String> wgList;
    
    //container information
    private String containerName = "";
    private String containerID = ""; //must do get with "PenID"
    private String PIName = "";
    private String PIPhone = "";
    private String activationDate = "";
    private String room = "";
    private String penStatus = "";
    private String statusDate = "";
    private String penComment = "";
    
    
    //mating fields
    private String matingDate = "";
    private String litterGeneration = "";
    private String litterStrain = "";
    private String matingID = "";
    private String owner = "";
    private String matingComment = "";
    
    
    
    //dam1, dam2, sire fields
    private String dam1JrNum = "";
    private String dam2JrNum = "";
    private String sireJrNum = "";
    private String sireID = "";
    private String dam1ID = "";
    private String dam2ID = "";
    private String sireStrain = "";
    private String dam1Strain = "";
    private String dam2Strain = "";
    private String sireGeneration = "";
    private String dam1Generation = "";
    private String dam2Generation = "";
    private String sireGenotype = "";
    private String dam1Genotype = "";
    private String dam2Genotype = "";
    private String dam1Birthdate = "";
    private String dam2Birthdate = "";
    private String sireBirthdate = "";
    private String dam1MatingID = "";
    private String dam2MatingID = "";
    private String sireMatingID = "";
    private String dam1LitterID = "";
    private String dam2LitterID = "";
    private String sireLitterID = "";
    private String weanNote = "";
    private String dam1Protocol = "";
    private String dam2Protocol = "";
    private String sireProtocol = "";
    private String dam1NewTag = "";
    private String dam2NewTag = "";
    private String sireNewTag = "";
    
    
    //litter fields
    private LinkedList<String> litterBirthdateQueue = new LinkedList<String>();
    private LinkedList<String> litterIDQueue = new LinkedList<String>();
    private LinkedList<String> numBornQueue = new LinkedList<String>();
    private LinkedList<String> numMaleQueue = new LinkedList<String>();
    private LinkedList<String> numFemaleQueue = new LinkedList<String>();
    
    private String MTSValue;

    public MatingQueueBuilder(SortedMap[] theResults, SortedMap[] theDbSetupResults, ArrayList<String> theWgList) {
        dbSetupResults = theDbSetupResults;
        results = theResults;
        wgList = theWgList;
    }
    
    public void parseResults(){
        
        if(dbSetupResults.length > 0){
            PIName = get(dbSetupResults[0], "PIName");
            PIPhone = get(dbSetupResults[0], "PIPhone");
        }
        
        if (getResults().length > 0) {

            SortedMap result = getResults()[0];
            
            MTSValue = get(result,"showExitedMice"); 
                                
            if (myContains(wgList,get(result,"owner"))) {

                setContainerName(get(result, "containerName"));
                setContainerID(get(result, "containerID"));
                activationDate = get(result, "ActivationDate");
                setDam1JrNum(get(result, "dam1jrNum")); //done
                setDam2JrNum(get(result, "dam2jrNum"));
                setSireJrNum(get(result, "sirejrNum"));
                setDam1ID(get(result, "dam1ID")); //done
                setDam2ID(get(result, "dam2ID"));
                setSireID(get(result, "sireID"));
                setDam1Strain(get(result, "dam1StrainName"));  //done
                setDam2Strain(get(result, "dam2StrainName"));
                setSireStrain(get(result, "sireStrainName"));
                setDam1Generation(get(result, "dam1Generation"));  //done
                setDam2Generation(get(result, "dam2Generation"));
                setSireGeneration(get(result, "sireGeneration"));
                setDam1Genotype(get(result, "dam1Genotype")); //done
                setDam2Genotype(get(result, "dam2Genotype"));
                setSireGenotype(get(result, "sireGenotype"));
                setDam1Birthdate(get(result, "dam1BD")); //done
                setDam2Birthdate(get(result, "dam2BD"));
                setSireBirthdate(get(result, "sireBD"));
                setDam1LitterID(get(result,"dam1LitterID"));
                setDam2LitterID(get(result,"dam2LitterID"));
                setSireLitterID(get(result,"sireLitterID"));
                setDam1MatingID(get(result,"dam1MatingID"));
                setDam2MatingID(get(result,"dam2MatingID"));
                setSireMatingID(get(result,"sireMatingID"));
                setDam1NewTag(get(result,"dam1NewTag"));
                setDam2NewTag(get(result,"dam2NewTag"));
                setSireNewTag(get(result,"sireNewTag"));
                setDam1Protocol(get(result,"dam1Protocol"));
                setDam2Protocol(get(result,"dam2Protocol"));
                setSireProtocol(get(result,"sireProtocol"));
                
                
                setMatingDate(get(result, "matingDate")); //done
                setLitterGeneration(get(result, "LitterGeneration")); //done
                setLitterStrain(get(result, "LitterStrain")); //done
                setOwner(get(result, "owner")); //done
                setMatingID(get(result, "matingID")); //done
                setPenComment(get(result,"containerComment"));
                setStatusDate(get(result,"statusDate"));
                setPenStatus(get(result,"containerStatus"));
                setMatingComment(get(result,"matingComment"));
                setRoom(get(result,"room"));
                setWeanNote(get(result,"weanNote"));                
            }
        }
        for(SortedMap litter: results){
            if(getLitterIDQueue().contains(get(litter,"litterID"))){
                if(!dam1Genotype.contains(get(litter, "dam1Genotype"))){
                    setDam1Genotype(getDam1Genotype() + " " + get(litter, "dam1Genotype"));
                }   
                if(!dam2Genotype.contains(get(litter, "dam2Genotype"))){
                    setDam2Genotype(getDam2Genotype() + " " + get(litter, "dam2Genotype"));
                }
                if(!sireGenotype.contains(get(litter, "sireGenotype"))){
                    setSireGenotype(getSireGenotype() + " " + get(litter, "sireGenotype"));
                }
            }
            else{
                getLitterIDQueue().add(get(litter,"litterID"));
                getLitterBirthdateQueue().add(get(litter,"litterBirthDate"));
                getNumBornQueue().add(get(litter,"totalBorn"));
                getNumFemaleQueue().add(get(litter,"numFemale"));
                getNumMaleQueue().add(get(litter,"numMale"));
            }
        }    
    }
    
    public boolean printExitedMice(SortedMap mouse, String damOrSire){
        boolean flag = false;
        if(MTSValue.equals("true")){
            return true;
        }            
        else{
            if(get(mouse,damOrSire).equals("A")){
                return true;
            }
        }
        return flag;
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

    public String get(SortedMap result, String field) {
        if(result.get(field) != null){
            return result.get(field).toString();
        }
        else{
            return "";
        }
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
     * @return the matingDate
     */
    public String getMatingDate() {
        return matingDate;
    }

    /**
     * @param matingDate the matingDate to set
     */
    public void setMatingDate(String matingDate) {
        this.matingDate = matingDate;
    }

    /**
     * @return the litterGeneration
     */
    public String getLitterGeneration() {
        return litterGeneration;
    }

    /**
     * @param litterGeneration the litterGeneration to set
     */
    public void setLitterGeneration(String litterGeneration) {
        this.litterGeneration = litterGeneration;
    }

    /**
     * @return the litterStrain
     */
    public String getLitterStrain() {
        return litterStrain;
    }

    /**
     * @param litterStrain the litterStrain to set
     */
    public void setLitterStrain(String litterStrain) {
        this.litterStrain = litterStrain;
    }

    /**
     * @return the matingID
     */
    public String getMatingID() {
        return matingID;
    }

    /**
     * @param matingID the matingID to set
     */
    public void setMatingID(String matingID) {
        this.matingID = matingID;
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
     * @return the dam1JrNum
     */
    public String getDam1JrNum() {
        return dam1JrNum;
    }

    /**
     * @param dam1JrNum the dam1JrNum to set
     */
    public void setDam1JrNum(String dam1JrNum) {
        this.dam1JrNum = dam1JrNum;
    }

    /**
     * @return the dam2JrNum
     */
    public String getDam2JrNum() {
        return dam2JrNum;
    }

    /**
     * @param dam2JrNum the dam2JrNum to set
     */
    public void setDam2JrNum(String dam2JrNum) {
        this.dam2JrNum = dam2JrNum;
    }

    /**
     * @return the sireJrNum
     */
    public String getSireJrNum() {
        return sireJrNum;
    }

    /**
     * @param sireJrNum the sireJrNum to set
     */
    public void setSireJrNum(String sireJrNum) {
        this.sireJrNum = sireJrNum;
    }

    /**
     * @return the sireID
     */
    public String getSireID() {
        return sireID;
    }

    /**
     * @param sireID the sireID to set
     */
    public void setSireID(String sireID) {
        this.sireID = sireID;
    }

    /**
     * @return the dam1ID
     */
    public String getDam1ID() {
        return dam1ID;
    }

    /**
     * @param dam1ID the dam1ID to set
     */
    public void setDam1ID(String dam1ID) {
        this.dam1ID = dam1ID;
    }

    /**
     * @return the dam2ID
     */
    public String getDam2ID() {
        return dam2ID;
    }

    /**
     * @param dam2ID the dam2ID to set
     */
    public void setDam2ID(String dam2ID) {
        this.dam2ID = dam2ID;
    }

    /**
     * @return the sireStrain
     */
    public String getSireStrain() {
        return sireStrain;
    }

    /**
     * @param sireStrain the sireStrain to set
     */
    public void setSireStrain(String sireStrain) {
        this.sireStrain = sireStrain;
    }

    /**
     * @return the dam1Strain
     */
    public String getDam1Strain() {
        return dam1Strain;
    }

    /**
     * @param dam1Strain the dam1Strain to set
     */
    public void setDam1Strain(String dam1Strain) {
        this.dam1Strain = dam1Strain;
    }

    /**
     * @return the dam2Strain
     */
    public String getDam2Strain() {
        return dam2Strain;
    }

    /**
     * @param dam2Strain the dam2Strain to set
     */
    public void setDam2Strain(String dam2Strain) {
        this.dam2Strain = dam2Strain;
    }

    /**
     * @return the sireGeneration
     */
    public String getSireGeneration() {
        return sireGeneration;
    }

    /**
     * @param sireGeneration the sireGeneration to set
     */
    public void setSireGeneration(String sireGeneration) {
        this.sireGeneration = sireGeneration;
    }

    /**
     * @return the dam1Generation
     */
    public String getDam1Generation() {
        return dam1Generation;
    }

    /**
     * @param dam1Generation the dam1Generation to set
     */
    public void setDam1Generation(String dam1Generation) {
        this.dam1Generation = dam1Generation;
    }

    /**
     * @return the dam2Generation
     */
    public String getDam2Generation() {
        return dam2Generation;
    }

    /**
     * @param dam2Generation the dam2Generation to set
     */
    public void setDam2Generation(String dam2Generation) {
        this.dam2Generation = dam2Generation;
    }

    /**
     * @return the sireGenotype
     */
    public String getSireGenotype() {
        return sireGenotype;
    }

    /**
     * @param sireGenotype the sireGenotype to set
     */
    public void setSireGenotype(String sireGenotype) {
        this.sireGenotype = sireGenotype;
    }

    /**
     * @return the dam1Genotype
     */
    public String getDam1Genotype() {
        return dam1Genotype;
    }

    /**
     * @param dam1Genotype the dam1Genotype to set
     */
    public void setDam1Genotype(String dam1Genotype) {
        this.dam1Genotype = dam1Genotype;
    }

    /**
     * @return the dam2Genotype
     */
    public String getDam2Genotype() {
        return dam2Genotype;
    }

    /**
     * @param dam2Genotype the dam2Genotype to set
     */
    public void setDam2Genotype(String dam2Genotype) {
        this.dam2Genotype = dam2Genotype;
    }

    /**
     * @return the dam1Birthdate
     */
    public String getDam1Birthdate() {
        return dam1Birthdate;
    }

    /**
     * @param dam1Birthdate the dam1Birthdate to set
     */
    public void setDam1Birthdate(String dam1Birthdate) {
        this.dam1Birthdate = dam1Birthdate;
    }

    /**
     * @return the dam2Birthdate
     */
    public String getDam2Birthdate() {
        return dam2Birthdate;
    }

    /**
     * @param dam2Birthdate the dam2Birthdate to set
     */
    public void setDam2Birthdate(String dam2Birthdate) {
        this.dam2Birthdate = dam2Birthdate;
    }

    /**
     * @return the sireBirthdate
     */
    public String getSireBirthdate() {
        return sireBirthdate;
    }

    /**
     * @param sireBirthdate the sireBirthdate to set
     */
    public void setSireBirthdate(String sireBirthdate) {
        this.sireBirthdate = sireBirthdate;
    }

    /**
     * @return the litterBirthdateQueue
     */
    public LinkedList<String> getLitterBirthdateQueue() {
        return litterBirthdateQueue;
    }

    /**
     * @param litterBirthdateQueue the litterBirthdateQueue to set
     */
    public void setLitterBirthdateQueue(LinkedList<String> litterBirthdateQueue) {
        this.litterBirthdateQueue = litterBirthdateQueue;
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
     * @return the numBornQueue
     */
    public LinkedList<String> getNumBornQueue() {
        return numBornQueue;
    }

    /**
     * @param numBornQueue the numBornQueue to set
     */
    public void setNumBornQueue(LinkedList<String> numBornQueue) {
        this.numBornQueue = numBornQueue;
    }

    /**
     * @return the numMaleQueue
     */
    public LinkedList<String> getNumMaleQueue() {
        return numMaleQueue;
    }

    /**
     * @param numMaleQueue the numMaleQueue to set
     */
    public void setNumMaleQueue(LinkedList<String> numMaleQueue) {
        this.numMaleQueue = numMaleQueue;
    }

    /**
     * @return the numFemaleQueue
     */
    public LinkedList<String> getNumFemaleQueue() {
        return numFemaleQueue;
    }

    /**
     * @param numFemaleQueue the numFemaleQueue to set
     */
    public void setNumFemaleQueue(LinkedList<String> numFemaleQueue) {
        this.numFemaleQueue = numFemaleQueue;
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
     * @return the dam1MatingID
     */
    public String getDam1MatingID() {
        return dam1MatingID;
    }

    /**
     * @param dam1MatingID the dam1MatingID to set
     */
    public void setDam1MatingID(String dam1MatingID) {
        this.dam1MatingID = dam1MatingID;
    }

    /**
     * @return the dam2MatingID
     */
    public String getDam2MatingID() {
        return dam2MatingID;
    }

    /**
     * @param dam2MatingID the dam2MatingID to set
     */
    public void setDam2MatingID(String dam2MatingID) {
        this.dam2MatingID = dam2MatingID;
    }

    /**
     * @return the sireMatingID
     */
    public String getSireMatingID() {
        return sireMatingID;
    }

    /**
     * @param sireMatingID the sireMatingID to set
     */
    public void setSireMatingID(String sireMatingID) {
        this.sireMatingID = sireMatingID;
    }

    /**
     * @return the dam1LitterID
     */
    public String getDam1LitterID() {
        return dam1LitterID;
    }

    /**
     * @param dam1LitterID the dam1LitterID to set
     */
    public void setDam1LitterID(String dam1LitterID) {
        this.dam1LitterID = dam1LitterID;
    }

    /**
     * @return the dam2LitterID
     */
    public String getDam2LitterID() {
        return dam2LitterID;
    }

    /**
     * @param dam2LitterID the dam2LitterID to set
     */
    public void setDam2LitterID(String dam2LitterID) {
        this.dam2LitterID = dam2LitterID;
    }

    /**
     * @return the sireLitterID
     */
    public String getSireLitterID() {
        return sireLitterID;
    }

    /**
     * @param sireLitterID the sireLitterID to set
     */
    public void setSireLitterID(String sireLitterID) {
        this.sireLitterID = sireLitterID;
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
     * @return the penStatus
     */
    public String getPenStatus() {
        return penStatus;
    }

    /**
     * @param penStatus the penStatus to set
     */
    public void setPenStatus(String penStatus) {
        this.penStatus = penStatus;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the penComment
     */
    public String getPenComment() {
        return penComment;
    }

    /**
     * @param penComment the penComment to set
     */
    public void setPenComment(String penComment) {
        this.penComment = penComment;
    }

    /**
     * @return the weanNote
     */
    public String getWeanNote() {
        return weanNote;
    }

    /**
     * @param weanNote the weanNote to set
     */
    public void setWeanNote(String weanNote) {
        this.weanNote = weanNote;
    }

    /**
     * @return the dam1Protocol
     */
    public String getDam1Protocol() {
        return dam1Protocol;
    }

    /**
     * @param dam1Protocol the dam1Protocol to set
     */
    public void setDam1Protocol(String dam1Protocol) {
        this.dam1Protocol = dam1Protocol;
    }

    /**
     * @return the dam2Protocol
     */
    public String getDam2Protocol() {
        return dam2Protocol;
    }

    /**
     * @param dam2Protocol the dam2Protocol to set
     */
    public void setDam2Protocol(String dam2Protocol) {
        this.dam2Protocol = dam2Protocol;
    }

    /**
     * @return the sireProtocol
     */
    public String getSireProtocol() {
        return sireProtocol;
    }

    /**
     * @param sireProtocol the sireProtocol to set
     */
    public void setSireProtocol(String sireProtocol) {
        this.sireProtocol = sireProtocol;
    }

    /**
     * @return the dam1NewTag
     */
    public String getDam1NewTag() {
        return dam1NewTag;
    }

    /**
     * @param dam1NewTag the dam1NewTag to set
     */
    public void setDam1NewTag(String dam1NewTag) {
        this.dam1NewTag = dam1NewTag;
    }

    /**
     * @return the dam2NewTag
     */
    public String getDam2NewTag() {
        return dam2NewTag;
    }

    /**
     * @param dam2NewTag the dam2NewTag to set
     */
    public void setDam2NewTag(String dam2NewTag) {
        this.dam2NewTag = dam2NewTag;
    }

    /**
     * @return the sireNewTag
     */
    public String getSireNewTag() {
        return sireNewTag;
    }

    /**
     * @param sireNewTag the sireNewTag to set
     */
    public void setSireNewTag(String sireNewTag) {
        this.sireNewTag = sireNewTag;
    }

    /**
     * @return the matingComment
     */
    public String getMatingComment() {
        return matingComment;
    }

    /**
     * @param matingComment the matingComment to set
     */
    public void setMatingComment(String matingComment) {
        this.matingComment = matingComment;
    }
}
