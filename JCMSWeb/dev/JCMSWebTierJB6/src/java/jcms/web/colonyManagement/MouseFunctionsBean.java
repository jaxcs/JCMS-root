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

package jcms.web.colonyManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.base.ITBaseEntityInterface;
import jcms.integrationtier.colonyManagement.ContainerEntity;
import jcms.integrationtier.colonyManagement.ContainerHistoryEntity;
import jcms.integrationtier.colonyManagement.DbsetupEntity;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.colonyManagement.MouseEntity;
import jcms.integrationtier.colonyManagement.OwnerEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.base.WTBaseBackingBean;
import jcms.web.common.SelectItemWrapper;

/**
 *
 * @author rkavitha
 */
public class MouseFunctionsBean extends WTBaseBackingBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private SelectItemWrapper selectItemWrapper;
    private ListSupportDTO listSupportDTO;
    private String nextAvailableMouseID = "";
    private int firstIntCharIndex = 0;
    private int lastIntCharIndex = 0;

    public MouseFunctionsBean() {
        this.initialize();
    }

    private void initialize() {
        ArrayList<OwnerEntity> ownerLst = new ArrayList<OwnerEntity>();
        ownerLst = (ArrayList<OwnerEntity>) getSessionParameter("colonyManageOwnerEntityLst");

        setListSupportDTO(new ListSupportDTO(ownerLst));
        setSelectItemWrapper(new SelectItemWrapper());
    }

    /*
     * gets a mouse ID from the dbinfo table and increments
     * the mouse ID. The mouse ID returned is maxAutoMouseID from 
     * table + 1. maxAutoMouseID in table is always last used number 
     * (not next available)
     * */
    public JCMSDbInfoEntity updateAutoMouseIDs(int iNumMice) throws Exception {
        JCMSDbInfoEntity dbInfoEntity = null;
        int mID = 0;
        try {
            // get the dbinfo record
            dbInfoEntity = this.getDbInfo();

            if (dbInfoEntity != null) {

                mID = dbInfoEntity.getMaxAutoMouseID();
                mID = mID + iNumMice;

                // update dbInfo table with new maxAutoMouseID
                dbInfoEntity.setMaxAutoMouseID(mID);
                dbInfoEntity.setIsDirty();
            } else {
                this.addToMessageQueue("No DbInfo record found",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "No DbInfo record found"));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    e.getMessage()));
        }

        //return nextMouseID;
        return dbInfoEntity;
    }
    
    /*
     * gets a pen ID from the dbinfo table and increments
     * the pen ID. The pen ID returned is maxPenID from 
     * table + 1. maxPenID in table is always last used number 
     * (not next available)
     * */
    public JCMSDbInfoEntity setMaxPenID(int penID) throws Exception {
        JCMSDbInfoEntity dbInfoEntity = null;
        int mID = 0;
        try {
            // get the dbinfo record
            dbInfoEntity = this.getDbInfo();

            if (dbInfoEntity != null) {

                mID = dbInfoEntity.getMaxPenID();
                // if maxPenID < new penID then increment
                if (penID > mID) {
                    // update dbInfo table with new maxPenID
                    dbInfoEntity.setMaxPenID(penID);
                    dbInfoEntity.setIsDirty();
                }
            } else {
                this.addToMessageQueue("No DbInfo record found",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "No DbInfo record found"));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    e.getMessage()));
        }
        return dbInfoEntity;
    }
    
    /**
     * Get the maxPenID from dbInfo table
     */
     public int getMaxPenID() {
        JCMSDbInfoEntity dbInfoEntity = null;
        int mID = 0;

        try {
            // get the dbinfo record
            dbInfoEntity = this.getDbInfo();

            if (dbInfoEntity != null) {
                mID = dbInfoEntity.getMaxPenID();
            }
        }
        catch (Exception e) {
            this.addToMessageQueue(e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    e.getMessage()));
        }
        return mID;
     }
    
    /**
     * checks pen id against dbinfo pen id and returns a warning
     * if user's pen id is much greater than the maxpen id in dbinfo     
     * The reason is that user may be allowed relaxed pen ids, but JCMS
     * will always print cage cards from starting at the value in dbinfo
     * table. When we create a new pen with a large ID we reset the value
     * in dbinfo to the largest pen ID created or the largest pen ID a card
     * has been printed for. Thus a user could potentially (if they entered
     * a very large pen ID) run out of pen numbers quickly)
     */
    public void checkRelaxedPenID(int id) {
        int mID = 0;

        try {
            // get maxPenID
            mID = this.getMaxPenID();

            if (id > mID + 100) {
                    this.addToMessageQueue("The pen ID " + id + " you requested "
                            + "is much larger than " + mID + ". You can end up "
                            + "with large gaps in your pen IDs",
                    FacesMessage.SEVERITY_WARN);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "The pen ID " + id + " you requested "
                            + "is much larger than " + mID + ". You can end up "
                            + "with large gaps in your pen IDs"));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    e.getMessage()));
        }
        
    }
    
    /**
     * returns true if JCMS setup variable MTS_RELAXED_PEN_NUMS is true,
     * otherwise false is returned
     * @return
     * @throws Exception 
     */
    public boolean doAllowRelaxedPens() {
        DbsetupEntity setupvar = new DbsetupEntity();
        // step through elements from cv and when match found, assign it

        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), "MTS_RELAXED_PEN_NUMS");

        if (entity != null) {
            setupvar = (DbsetupEntity) entity;
            if ((setupvar != null && setupvar.getMTSValue() != null
                    && !setupvar.getMTSValue().equals(""))) {
                if (setupvar.getMTSValue().equalsIgnoreCase("true"))
                return true;
            }
        }   
        return false;
    }
    
    /**
     * isValidPenID()
     * INPUTS: a penID and a reference to a string.
     *
     * RETURNS: true if pen ID is in range 1 to maxPendID.
     * error message is returned if pen id is not valid.
     * EFFECTS:
     * ERROR CONDITIONS:
     * ASSUMES:
     * COMMENTS: valid pen ids are in range 1...dbInfo.maxPenId
     * @return
     * @throws Exception 
     */
    public boolean isValidPenID(int lPenID) throws Exception {
        int lMaxPenID = 0;

        // get the dbinfo record
        lMaxPenID = this.getMaxPenID();

        if (lPenID > lMaxPenID || lPenID <= 0) {
            this.addToMessageQueue("The pen number " + lPenID + " is not valid. ",
                    FacesMessage.SEVERITY_ERROR);

                    this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    "The pen number " + lPenID + " is not valid."));
            return false;
        }
        return true;
    }

    /*
     * gets the dbInfo record entity. It should contain only one record. 
     * */
    public JCMSDbInfoEntity getDbInfo() throws Exception {
        JCMSDbInfoEntity dbInfoEntity = new JCMSDbInfoEntity();
        List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();

        // get the dbinfo record
        info = new ListSupportDTO().getJCMSDbInfo();
        if (info.size() > 0) {
            dbInfoEntity = info.get(0);
        }

        return dbInfoEntity;
    }

    /*
     * gets a mouse ID from the dbinfo table and increments
     * the mouse ID. The mouse ID returned is maxAutoMouseID from 
     * table + 1. maxAutoMouseID in table is always last used number 
     * (not next available). dbInfo is used to keep track of mouse
     * IDs that begin with the base prefix saved as MTS_MOUSE_ID_PREFIX
     * in the Dbsetup table. This prefix is used to create the "base 
     * mouse ID"
     * */
    public String allocateAutoBaseMouseID(int iNumMice) throws Exception {
        JCMSDbInfoEntity dbInfoEntity = null;
        int mID = 0;
        long nextAvailableID = 0;
        String prefix = "";

        try {
            // get the dbinfo record
            dbInfoEntity = this.getDbInfo();

            DbsetupEntity setupvar = new DbsetupEntity();
            // step through elements from cv and when match found, assign it
            // get the value of setup variable
            ITBaseEntityInterface entity = null;
            entity = getRepositoryService().baseFindBySetupVariable(new 
                    DbsetupEntity(), "MTS_MOUSE_ID_PREFIX");

            if (entity != null) {
                setupvar = (DbsetupEntity) entity;
            }

            if ((setupvar != null && setupvar.getMTSValue() != null
                    && !setupvar.getMTSValue().equals(""))) {
                prefix = setupvar.getMTSValue();
            }

            if (dbInfoEntity != null) {
                mID = dbInfoEntity.getMaxAutoMouseID();
                mID = mID + iNumMice;

                nextAvailableID = mID - iNumMice + 1; // this is next available mID
                this.setNextAvailableMouseID(prefix + nextAvailableID);
                System.out.println(nextAvailableMouseID);
            } else {
                this.addToMessageQueue("No DbInfo record found",
                        FacesMessage.SEVERITY_ERROR);

                this.getLogger().logWarn(this.formatLogMessage("Validation ",
                        "No DbInfo record found"));
            }
        } catch (Exception e) {
            this.addToMessageQueue(e.getMessage(),
                    FacesMessage.SEVERITY_ERROR);

            this.getLogger().logWarn(this.formatLogMessage("Validation ",
                    e.getMessage()));
        }

        return this.getNextAvailableMouseID();
    }

    /*
     * This method is called to incremnent an ID
     */
    public String incrementID(String sID, String setUpVar) {
        int strLen = 0;
        int i = 0, j = 0;
        int iLenOfMidStr = 0, iLenOfIncrementedMidStr = 0; // used in checking for leading zeros   
        long lIDVal = 0;
        String sIDPrefixStr = "", //text before the integer portion
                sIDSuffixStr = "", //text after the integer portion.
                sIDMidStr = "", //this will be the integer portion as a string  
                incrementID = "";

        incrementID = sID; //If there is an error, pass back the ID unaltered        

        strLen = sID.length();
        setFirstIntCharIndex(0);
        setLastIntCharIndex(0);
        DbsetupEntity setupVar = new DbsetupEntity();

        // step through elements from cv and when match found, assign it
        // step through elements from cv and when match found, assign it
        // get the value of setup variable
        ITBaseEntityInterface entity = null;
        entity = getRepositoryService().baseFindBySetupVariable(new 
                DbsetupEntity(), setUpVar);

        if (entity != null) {
            setupVar = (DbsetupEntity) entity;
        }

        // Passing a setup variable for rightmost is optional
        // If no setup variable is passed, then the leftmost integer is incremented.
        if (setupVar != null && !setupVar.getMTSValue().equalsIgnoreCase("true")) {
            //leftmost integer is incremented, else rightmost.
            if (!getFirstIntegerField(sID)) {
                incrementID = sID;
                return incrementID;
            }
            // get the prefix and suffix
            sIDPrefixStr = sID.substring(0, getFirstIntCharIndex());
            sIDSuffixStr = sID.substring(getLastIntCharIndex() + 1, strLen);
        } else {
            if (!getLastIntegerField(sID)) {
                incrementID = sID;
                return incrementID;
            }
            // get the prefix and suffix
            sIDPrefixStr = sID.substring(0, getFirstIntCharIndex());
            sIDSuffixStr = sID.substring(getLastIntCharIndex() + 1, strLen);
        }
        //sIDMidStr = sID.substring(firstIntCharIndex, lastIntCharIndex - firstIntCharIndex + 1);
        if ((sIDPrefixStr != null && !sIDPrefixStr.equals(""))
                || (sIDSuffixStr != null && !sIDSuffixStr.equals(""))) {
            sIDMidStr = sID.substring(getFirstIntCharIndex(), getLastIntCharIndex() + 1);
        }

        System.out.println("sIDPrefixStr " + sIDPrefixStr);
        System.out.println("sIDSuffixStr " + sIDSuffixStr);
        System.out.println("sIDMidStr " + sIDMidStr);

        // increment integer portion
        if (sIDMidStr != null && !sIDMidStr.equals("")) {
            lIDVal = val(sIDMidStr) + 1;
        } else {
            // in case of integer mouse ID, prefix, midstring and suffix will all be 
            // null, so just increment integer value
            if ((sIDPrefixStr == null || sIDPrefixStr.equals(""))
                    && (sIDMidStr == null || sIDMidStr.equals(""))
                    && (sIDSuffixStr == null || sIDSuffixStr.equals(""))) {
                sIDMidStr = sID;
                lIDVal = val(sID) + 1;
            } else {
                sIDMidStr = sID;
            }
        }

        // now do the stuff to make it work correctly with leading zeros...
        iLenOfMidStr = sIDMidStr.length(); // get lengths of the strings

        if (lIDVal > 0) {
            iLenOfIncrementedMidStr = String.valueOf(lIDVal).length();

            sIDMidStr = String.valueOf(lIDVal); // set our new mid string now (we've now got the length before increment)
            // if lengths are equal, or if new lIDVal is longer (it may have incremented to new tens place)
            //  then we're fine.
            if (iLenOfMidStr > iLenOfIncrementedMidStr) {
                for (i = iLenOfIncrementedMidStr; i < iLenOfMidStr; ++i) {
                    sIDMidStr = "0" + sIDMidStr;
                }
            }
        }

        System.out.println("revised midstring " + sIDMidStr);

        incrementID = sIDPrefixStr + sIDMidStr + sIDSuffixStr;
        System.out.println("incrementID " + incrementID);
        return incrementID;
    }

    // converts a string to integer by eliminating non-integer characters
    public int val(String str) {
        StringBuilder validStr = new StringBuilder();
        boolean seenDot = false;   // when this is true, dots are not allowed
        boolean seenDigit = false; // when this is true, signs are not allowed

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '.' && !seenDot) {
                seenDot = true;
                validStr.append(c);
            } else if ((c == '-' || c == '+') && !seenDigit) {
                validStr.append(c);
            } else if (Character.isDigit(c)) {
                seenDigit = true;
                validStr.append(c);
            } else if (Character.isWhitespace(c) || c == '_') {
                // just skip over whitespace
                continue;
            } else {
                // invalid character
                break;
            }
        }
        return Integer.parseInt(validStr.toString());
    }

    /**
     * INPUTS: sID identifier that may or may not contain
     *               an integer field
     *     firstIntCharIndex (byRef) an integer that identifies the first
     *          occurrence of a number
     *  lastIntCharIndex (byRef) an integer that identifies the last
     *          occurrence of a number
     *
     * RETURNS: true if an integer field was found, false otherwise
     * COMMENTS: This method takes the given string and
     *  returns the first and last location of the integer
     *  sub field within sID.
    
     * If there is no integer subfield then it returns false.
     * @param id
     * @return getFirstIntegerField
     */
    public boolean getFirstIntegerField(String id) {

        boolean getFirstIntegerField = false;
        int strLen = 0;
        int i = 0;

        strLen = id.length();

        // get first integer character
        if (strLen > 0) {
            for (i = 0; i < strLen; ++i) {
                if (this.isDigit(id.charAt(i))) {
                    setFirstIntCharIndex(i);
                    getFirstIntegerField = true; //good data
                    break;
                }
            }

            for (i = getFirstIntCharIndex() + 1; i < strLen; ++i) {
                if (!this.isDigit(id.charAt(i))) {
                    setLastIntCharIndex(i - 1);
                    getFirstIntegerField = true; //good data
                    break;
                }
            }

            // we check if string of integer chars goes to end of string, then
            // we won't have found the last non-int char. We'll just know it is
            // the last char in the string...so we will set it here.
            if (i == strLen) {
                setLastIntCharIndex(strLen - 1);
            }
        }
        return getFirstIntegerField;
    }

    public boolean isDigit(char ch) {
        if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }

    /**
     * INPUTS: sID identifier that may or may not contain
     *               an integer field
     *     firstIntCharIndex (byRef) an integer that identifies the first
     *          occurrence of a number
     *  lastIntCharIndex (byRef) an integer that identifies the last
     *          occurrence of a number
     *
     * RETURNS: true if an integer field was found, false otherwise
     * COMMENTS: This method takes the given string and
     *  returns the first and last location of the integer
     *  sub field with sID.
    
     * If there is no integer subfield then it returns false.
     * @param id
     * @return getFirstIntegerField
     */
    public boolean getLastIntegerField(String id) {

        boolean getLastIntegerField = false;

        int strLen = 0;
        int i = 0;

        strLen = id.length();

        // get larst integer character
        if (strLen > 0) {
            for (i = strLen - 1; i >= 0; --i) {
                if (this.isDigit(id.charAt(i))) {
                    setLastIntCharIndex(i);
                    getLastIntegerField = true; //good data
                    break;
                }
            }

            //get first non-integer character that appears after our integer field
            for (i = getLastIntCharIndex() - 1; i >= 0; --i) {
                if (!this.isDigit(id.charAt(i))) {
                    setFirstIntCharIndex(i + 1);
                    getLastIntegerField = true; //good data
                    break;
                }
            }

            // we check if string of integer chars goes to end of string, then
            // we won't have found the last non-int char. We'll just know it is
            // the last char in the string...so we will set it here.
            if (getFirstIntCharIndex() == 0) {
                setFirstIntCharIndex(0);
            }
        }
        return getLastIntegerField;
    }

    /** 
     * find mouse entity given mouseID
     * 
     * @param id - String id
     * @return - MouseEntity mEntity
     */
    public MouseEntity findMouse(String id) throws Exception {
        // Check that mouse id is unique.
        MouseEntity mEntity = (MouseEntity) getRepositoryService().
                baseFindByMoueID(new MouseEntity(), id);

        return mEntity;
    }  
        
    /** 
     * find mouse entity given mouseID
     * 
     * @param id - String id
     * @return - MouseEntity mEntity
     */
    public MouseEntity findMouseByKey(int key) throws Exception {
        // Check that mouse id is unique.
        MouseEntity mEntity = getRepositoryService().
                baseFind(key);

        return mEntity;
    }
    
    /** 
     * find mouse entity given mouseID
     * 
     * @param id - String id
     * @return - MouseEntity mEntity
     */
    public MouseEntity findSire(String id) throws Exception {
        // Check that mouse id is unique.
        MouseEntity mEntity = getRepositoryService().findSire(id);
        return mEntity;
    }
    
    /** 
     * find mouse entity given mouseID
     * 
     * @param id - String id
     * @return - MouseEntity mEntity
     */
    public MouseEntity findDam(String id) throws Exception {
        // Check that mouse id is unique.
        MouseEntity mEntity = getRepositoryService().findDam(id);
        return mEntity;
    }

    /** 
     * find pen entity given penID
     * 
     * @param id - int id
     * @return - ContainerEntity cEntity
     */
    public ContainerEntity findPen(int id) {
        // Check that mouse id is unique.
        ContainerEntity cEntity = (ContainerEntity) getRepositoryService().
                baseFindByContainerID(new ContainerEntity(), id);

        return cEntity;
    }
    
    /** 
     * find pen entity given penID
     * 
     * @param id - int id
     * @return - ContainerEntity cEntity
     */
    public List<ITBaseEntityInterface> findByPenName(String name) {
        // Check that mouse id is unique.
        List<ITBaseEntityInterface> list = getRepositoryService().
                baseFindByContainerName(new ContainerEntity(), name);
                
        return list;
    }

    /** 
     * find pen entity given penID
     * 
     * @param id - int id
     * @return - ContainerEntity cEntity
     */
    public ContainerHistoryEntity findPenHistory(int id) {
        // Check that mouse id is unique.
        ContainerHistoryEntity cEntity = (ContainerHistoryEntity) getRepositoryService().
                baseFindByContainerHistoryKey(new ContainerHistoryEntity(), id);

        return cEntity;
    }

    /**
     * @return the selectItemWrapper
     */
    public SelectItemWrapper getSelectItemWrapper() {
        return selectItemWrapper;
    }

    /**
     * @param selectItemWrapper the selectItemWrapper to set
     */
    public void setSelectItemWrapper(SelectItemWrapper selectItemWrapper) {
        this.selectItemWrapper = selectItemWrapper;
    }

    /**
     * @return the listSupportDTO
     */
    public ListSupportDTO getListSupportDTO() {
        return listSupportDTO;
    }

    /**
     * @param listSupportDTO the listSupportDTO to set
     */
    public void setListSupportDTO(ListSupportDTO listSupportDTO) {
        this.listSupportDTO = listSupportDTO;
    }

    /**
     * @return the nextAvailableMouseID
     */
    public String getNextAvailableMouseID() {
        return nextAvailableMouseID;
    }

    /**
     * @param nextAvailableMouseID the nextAvailableMouseID to set
     */
    public void setNextAvailableMouseID(String nextAvailableMouseID) {
        this.nextAvailableMouseID = nextAvailableMouseID;
    }

    /**
     * @return the firstIntCharIndex
     */
    public int getFirstIntCharIndex() {
        return firstIntCharIndex;
    }

    /**
     * @param firstIntCharIndex the firstIntCharIndex to set
     */
    public void setFirstIntCharIndex(int firstIntCharIndex) {
        this.firstIntCharIndex = firstIntCharIndex;
    }

    /**
     * @return the lastIntCharIndex
     */
    public int getLastIntCharIndex() {
        return lastIntCharIndex;
    }

    /**
     * @param lastIntCharIndex the lastIntCharIndex to set
     */
    public void setLastIntCharIndex(int lastIntCharIndex) {
        this.lastIntCharIndex = lastIntCharIndex;
    }
}