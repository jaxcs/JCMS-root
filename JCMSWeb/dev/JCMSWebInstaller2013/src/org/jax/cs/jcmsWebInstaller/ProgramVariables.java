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

package org.jax.cs.jcmsWebInstaller;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author cnh
 */
public class ProgramVariables {
    
    private Boolean MySQLInstalled = false;
    private Boolean ODBCInstalled = false;
    private Boolean installSuccess = false;
    private Boolean upgradeSuccess = false;
    private LinkedList<String> upgradeScripts = new LinkedList<String>();
    private HashMap<String, Integer> upgradeMap = new HashMap<String, Integer>();
    private String dbInfoName = "dbInfo";

    public ProgramVariables() {
        initialize();
    }
    
    //initialize upgradeMap and upgradeScripts in constructor
    public void initialize()
    {
        //MAKE SURE THAT YOU ADD NEW UPGRADE SCRIPTS TO THE END OF THIS LIST, NOT HERE!!!
        getUpgradeScripts().addLast("j3.0.0-j3.0.2.mysql.sql");
        getUpgradeScripts().addLast("j3.0.2-j3.0.3.mysql.sql");
        getUpgradeScripts().addLast("j3.1.0-j3.2.0.mysql.sql");
        getUpgradeScripts().addLast("j3.2.0-j3.3.0.mysql.sql");
        getUpgradeScripts().addLast("j3.3.0-j3.4.0.mysql.sql");
        getUpgradeScripts().addLast("j3.4.0-j4.0.0.mysql.sql");
        getUpgradeScripts().addLast("j4.0.0-j4.1.0.mysql.sql");
        getUpgradeScripts().addLast("j4.1.0-j4.2.0.mysql.sql");
        getUpgradeScripts().addLast("j4.2.0-j4.2.1.mysql.sql");
        getUpgradeScripts().addLast("j4.2.1-j4.3.0.mysql.sql");
        getUpgradeScripts().addLast("j4.3.0-j4.4.0.mysql.sql");
        getUpgradeScripts().addLast("j4.4.0-j4.5.0.mysql.sql");
        getUpgradeScripts().addLast("j4.5.0-j4.6.0.mysql.sql");
        getUpgradeScripts().addLast("j4.6.0-j4.8.3.mysql.sql");
        getUpgradeScripts().addLast("j4.8.3-j4.8.4.mysql.sql");
//        upgradeScripts.addLast("j4.8.3-j4.8.4.part2.mysql.sql"); //this is an item potent script. DEPRECATED Now part of j4.8.3-j4.8.4.mysql.sql
        getUpgradeScripts().addLast("j4.8.4-j5.0.0.mysql.sql");
        getUpgradeScripts().addLast("jdbInfoUpdate.mysql.sql");    // Sets dbVers to 102.  Upgrade using dbInfo.dbVers from NOW ON!
        /** key is versionNum from database, value is number of scripts your popping off of linked list "upgradeScripts"
         * for example if version number is 3.0.0 should pop off 0 scripts from array list because all scripts need to be
         * run to upgrade user to most recent version. If versionNum from DB is 3.1.0, 3.0.0 and 3.0.2 should be popped
         * off of arraylist.
         * This depends on the scripts existing in Queue as ordered queue as below:
         * 
         * Pop from up here (head of queue, these are removed first)
         * ______________________
         * |3.0.0 to 3.0.2       |
         * |_____________________|
         * |3.0.2 to 3.0.3       |
         * |_____________________|
         *           .
         *           .
         *           .
         * __________.___________     
         * | Most recent script  |
         * |_____________________|
         *
         * 
         */
         
        getUpgradeScripts().addLast("dbChange_103.sql");
        getUpgradeScripts().addLast("dbChange_104.sql");        
        getUpgradeScripts().addLast("dbChange_105.sql");        
        getUpgradeScripts().addLast("dbChange_106.sql");        
        getUpgradeScripts().addLast("dbChange_107.sql");        
        getUpgradeScripts().addLast("dbChange_108.sql");        
        getUpgradeScripts().addLast("dbChange_109.sql");        
        getUpgradeScripts().addLast("dbChange_110.sql");        
        getUpgradeScripts().addLast("dbChange_111.sql");        
        getUpgradeScripts().addLast("dbChange_112.sql");
        getUpgradeScripts().addLast("dbChange_113.sql");
        getUpgradeScripts().addLast("dbChange_114.sql");
        getUpgradeScripts().addLast("dbChange_115.sql");
        getUpgradeScripts().addLast("dbChange_116.sql");
        getUpgradeScripts().addLast("dbChange_117.sql");
        getUpgradeScripts().addLast("dbChange_118.sql");
        getUpgradeScripts().addLast("dbChange_119.sql");
        getUpgradeScripts().addLast("dbChange_120.sql");
        getUpgradeScripts().addLast("dbChange_121.sql");
        getUpgradeScripts().addLast("dbChange_122.sql");
        getUpgradeScripts().addLast("dbChange_123.sql");
        getUpgradeScripts().addLast("dbChange_124.sql");
        getUpgradeScripts().addLast("dbChange_125.sql");
        getUpgradeScripts().addLast("dbChange_126.sql");
        getUpgradeScripts().addLast("dbChange_127.sql");
        getUpgradeScripts().addLast("dbChange_128.sql");
        getUpgradeScripts().addLast("dbChange_129.sql");
        getUpgradeScripts().addLast("dbChange_130.sql");
        getUpgradeScripts().addLast("dbChange_131.sql");
        getUpgradeScripts().addLast("dbChange_132.sql");
        getUpgradeScripts().addLast("dbChange_133.sql");
        getUpgradeScripts().addLast("dbChange_134.sql");
        getUpgradeScripts().addLast("dbChange_135.sql");
        getUpgradeScripts().addLast("dbChange_136.sql");
        getUpgradeScripts().addLast("dbChange_137.sql");
        getUpgradeScripts().addLast("dbChange_138.sql");
        getUpgradeScripts().addLast("dbChange_139.sql");
        getUpgradeScripts().addLast("dbChange_140.sql");
        getUpgradeScripts().addLast("dbChange_141.sql");

        //MAKE SURE YOU USE ADDLAST SO NEWEST IS AT END OF 'QUEUE'!!!!
        
        /** 
         * Push onto here (tail of queue, remember a queue is just like a line at the bank, first in first out)
         */
        int index = 0;
        getUpgradeMap().put("3.0.0", index);
        getUpgradeMap().put("3.0.2", ++index);
        getUpgradeMap().put("3.0.3", index);
        getUpgradeMap().put("3.1.0", ++index);
        getUpgradeMap().put("3.2.0", ++index);
        getUpgradeMap().put("3.2.1", index);
        getUpgradeMap().put("3.3.0", ++index);
        getUpgradeMap().put("3.3.1", index);
        getUpgradeMap().put("3.4.0", ++index);
        getUpgradeMap().put("3.4.1", index);
        getUpgradeMap().put("3.4.2", index);
        getUpgradeMap().put("3.4.3", index);
        getUpgradeMap().put("4.0.0", ++index);
        getUpgradeMap().put("4.0.1", index);
        getUpgradeMap().put("4.1.0", ++index);
        getUpgradeMap().put("4.1.1", index);
        getUpgradeMap().put("4.1.2", index);
        getUpgradeMap().put("4.1.3", index);
        getUpgradeMap().put("4.2.0", ++index);
        getUpgradeMap().put("4.2.1", ++index);
        getUpgradeMap().put("4.3.0", ++index);
        getUpgradeMap().put("4.4.0", ++index);
        getUpgradeMap().put("4.5.0", ++index);
        getUpgradeMap().put("4.6.0", ++index);
        getUpgradeMap().put("4.7.0", index);
        getUpgradeMap().put("4.8.0", index);
        getUpgradeMap().put("4.8.1", index);
        getUpgradeMap().put("4.8.2", index);
        getUpgradeMap().put("4.8.3", ++index);
        getUpgradeMap().put("4.8.4", ++index);
        getUpgradeMap().put("5.0.0", ++index);
        getUpgradeMap().put("5.1.0", index);
        getUpgradeMap().put("102", ++index);
        getUpgradeMap().put("103", ++index);    
        getUpgradeMap().put("104", ++index);    
        getUpgradeMap().put("105", ++index);    
        getUpgradeMap().put("106", ++index);    
        getUpgradeMap().put("107", ++index);    
        getUpgradeMap().put("108", ++index);    
        getUpgradeMap().put("109", ++index);    
        getUpgradeMap().put("110", ++index);    
        getUpgradeMap().put("111", ++index);
        getUpgradeMap().put("112", ++index);
        getUpgradeMap().put("113", ++index);
        getUpgradeMap().put("114", ++index);
        getUpgradeMap().put("115", ++index);
        getUpgradeMap().put("116", ++index);
        getUpgradeMap().put("117", ++index);
        getUpgradeMap().put("118", ++index);
        getUpgradeMap().put("119", ++index);
        getUpgradeMap().put("120", ++index);
        getUpgradeMap().put("121", ++index);
        getUpgradeMap().put("122", ++index);
        getUpgradeMap().put("123", ++index);
        getUpgradeMap().put("124", ++index);
        getUpgradeMap().put("125", ++index);
        getUpgradeMap().put("126", ++index);
        getUpgradeMap().put("127", ++index);
        getUpgradeMap().put("128", ++index);
        getUpgradeMap().put("129", ++index);
        getUpgradeMap().put("130", ++index);
        getUpgradeMap().put("131", ++index);
        getUpgradeMap().put("132", ++index);
        getUpgradeMap().put("133", ++index);
        getUpgradeMap().put("134", ++index);
        getUpgradeMap().put("135", ++index);
        getUpgradeMap().put("136", ++index);
        getUpgradeMap().put("137", ++index);
        getUpgradeMap().put("138", ++index);
        getUpgradeMap().put("139", ++index);
        getUpgradeMap().put("140", ++index);
        getUpgradeMap().put("141", ++index);
    }

    /**
     * @return the MySQLInstalled
     */
    public Boolean getMySQLInstalled() {
        return MySQLInstalled;
    }

    /**
     * @param aMySQLInstalled the MySQLInstalled to set
     */
    public void setMySQLInstalled(Boolean aMySQLInstalled) {
        MySQLInstalled = aMySQLInstalled;
    }

    /**
     * @return the ODBCInstalled
     */
    public Boolean getODBCInstalled() {
        return ODBCInstalled;
    }

    /**
     * @param aODBCInstalled the ODBCInstalled to set
     */
    public void setODBCInstalled(Boolean aODBCInstalled) {
        ODBCInstalled = aODBCInstalled;
    }

    /**
     * @return the installSuccess
     */
    public Boolean getInstallSuccess() {
        return installSuccess;
    }

    /**
     * @param aInstallSuccess the installSuccess to set
     */
    public void setInstallSuccess(Boolean aInstallSuccess) {
        installSuccess = aInstallSuccess;
    }

    /**
     * @return the upgradeSuccess
     */
    public Boolean getUpgradeSuccess() {
        return upgradeSuccess;
    }

    /**
     * @param aUpgradeSuccess the upgradeSuccess to set
     */
    public void setUpgradeSuccess(Boolean aUpgradeSuccess) {
        upgradeSuccess = aUpgradeSuccess;
    }

    /**
     * @return the upgradeScripts
     */
    public LinkedList<String> getUpgradeScripts() {
        return upgradeScripts;
    }

    /**
     * @param aUpgradeScripts the upgradeScripts to set
     */
    public void setUpgradeScripts(LinkedList<String> aUpgradeScripts) {
        upgradeScripts = aUpgradeScripts;
    }

    /**
     * @return the upgradeMap
     */
    public HashMap<String, Integer> getUpgradeMap() {
        return upgradeMap;
    }

    /**
     * @param aUpgradeMap the upgradeMap to set
     */
    public void setUpgradeMap(HashMap<String, Integer> aUpgradeMap) {
        upgradeMap = aUpgradeMap;
    }

    /**
     * @return the dbInfoName
     */
    public String getDbInfoName() {
        return dbInfoName;
    }

    /**
     * @param aDbInfoName the dbInfoName to set
     */
    public void setDbInfoName(String aDbInfoName) {
        dbInfoName = aDbInfoName;
    }


}
