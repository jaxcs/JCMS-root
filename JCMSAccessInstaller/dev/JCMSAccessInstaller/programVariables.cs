//***
//Copyright (c) 2015 The Jackson Laboratory
//
//This is free software: you can redistribute it and/or modify it 
//under the terms of the GNU General Public License as published by  
//the Free Software Foundation, either version 3 of the License, or  
//(at your option) any later version.
// 
//This software is distributed in the hope that it will be useful,  
//but WITHOUT ANY WARRANTY; without even the implied warranty of 
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
//General Public License for more details.
//
//You should have received a copy of the GNU General Public License 
//along with this software.  If not, see <http://www.gnu.org/licenses/>.
//***
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace JCMSAccessInstaller
{
    static class programVariables
    {
        private static bool MySQLInstalled = false;
        private static bool ODBCInstalled = false;
        private static bool Upgrade = false;
        private static bool Install = false;
        private static bool installSuccess = false;
        private static bool upgradeSuccess = false;
        private static LinkedList<string> upgradeScripts = new LinkedList<string>();
        private static Dictionary<string, int> upgradeMap = new Dictionary<string, int>();
        private static LinkedList<string> installScripts = new LinkedList<string>();

        public static LinkedList<string> InstallScripts = new LinkedList<string>();

        private static string dbInfoName = "dbInfo";
        private static string latestJCMS = "6.1.9";
        
        //initialize upgradeMap and upgradeScripts in constructor
        public static void initialize()
        {
            //MAKE SURE THAT YOU ADD NEW UPGRADE SCRIPTS TO THE END OF THIS LIST, NOT HERE!!!
            upgradeScripts.AddLast("j3.0.0-j3.0.2.mysql.sql");
            upgradeScripts.AddLast("j3.0.2-j3.0.3.mysql.sql");
            upgradeScripts.AddLast("j3.1.0-j3.2.0.mysql.sql");
            upgradeScripts.AddLast("j3.2.0-j3.3.0.mysql.sql");
            upgradeScripts.AddLast("j3.3.0-j3.4.0.mysql.sql");
            upgradeScripts.AddLast("j3.4.0-j4.0.0.mysql.sql");
            upgradeScripts.AddLast("j4.0.0-j4.1.0.mysql.sql");
            upgradeScripts.AddLast("j4.1.0-j4.2.0.mysql.sql");
            upgradeScripts.AddLast("j4.2.0-j4.2.1.mysql.sql");
            upgradeScripts.AddLast("j4.2.1-j4.3.0.mysql.sql");
            upgradeScripts.AddLast("j4.3.0-j4.4.0.mysql.sql");
            upgradeScripts.AddLast("j4.4.0-j4.5.0.mysql.sql");
            upgradeScripts.AddLast("j4.5.0-j4.6.0.mysql.sql");
            upgradeScripts.AddLast("j4.6.0-j4.8.3.mysql.sql");
            upgradeScripts.AddLast("j4.8.3-j4.8.4.mysql.sql");
    //        upgradeScripts.AddLast("j4.8.3-j4.8.4.part2.mysql.sql"); //this is an item potent script. DEPRECATED Now part of j4.8.3-j4.8.4.mysql.sql
            upgradeScripts.AddLast("j4.8.4-j5.0.0.mysql.sql");
            upgradeScripts.AddLast("dbChange_103.sql");
            upgradeScripts.AddLast("dbChange_104.sql");
            upgradeScripts.AddLast("dbChange_105.sql");
            upgradeScripts.AddLast("dbChange_106.sql");
            upgradeScripts.AddLast("dbChange_107.sql");
            upgradeScripts.AddLast("dbChange_108.sql");
            upgradeScripts.AddLast("dbChange_109.sql");
            upgradeScripts.AddLast("dbChange_110.sql");
            upgradeScripts.AddLast("dbChange_111.sql");
            upgradeScripts.AddLast("dbChange_112.sql");
            upgradeScripts.AddLast("dbChange_113.sql");
            upgradeScripts.AddLast("dbChange_114.sql");
            upgradeScripts.AddLast("dbChange_115.sql");
            upgradeScripts.AddLast("dbChange_116.sql");
            upgradeScripts.AddLast("dbChange_117.sql");
            upgradeScripts.AddLast("dbChange_118.sql");
            upgradeScripts.AddLast("dbChange_119.sql");
            upgradeScripts.AddLast("dbChange_120.sql");
            upgradeScripts.AddLast("dbChange_121.sql");
            upgradeScripts.AddLast("dbChange_122.sql");
            upgradeScripts.AddLast("dbChange_123.sql");
            upgradeScripts.AddLast("dbChange_124.sql");
            upgradeScripts.AddLast("dbChange_125.sql");
            upgradeScripts.AddLast("dbChange_126.sql");
            upgradeScripts.AddLast("dbChange_127.sql");
            upgradeScripts.AddLast("dbChange_128.sql");
            upgradeScripts.AddLast("dbChange_129.sql");
            upgradeScripts.AddLast("dbChange_130.sql");
            upgradeScripts.AddLast("dbChange_131.sql");
            upgradeScripts.AddLast("dbChange_132.sql");
            upgradeScripts.AddLast("dbChange_133.sql");
            upgradeScripts.AddLast("dbChange_134.sql");
            upgradeScripts.AddLast("dbChange_135.sql");
            upgradeScripts.AddLast("dbChange_136.sql");
            upgradeScripts.AddLast("dbChange_137.sql");
            upgradeScripts.AddLast("dbChange_138.sql");
            upgradeScripts.AddLast("dbChange_139.sql");
            upgradeScripts.AddLast("dbChange_140.sql");
            upgradeScripts.AddLast("dbChange_141.sql");
            upgradeScripts.AddLast("dbChange_142.sql");
            //ALL SUBSEQUENT UPGRADE SCRIPT NAMES SHOULD BE ADDED AFTER THE ABOVE ONE!!!!!
            //MAKE SURE YOU USE ADDLAST SO NEWEST IS AT END OF 'QUEUE'!!!!

            //upgradeScripts.AddLast("jdbInfoUpdate.mysql.sql");
            /*key is versionNum from database, value is number of scripts your popping off of linked list "upgradeScripts"
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
             * Push onto here (tail of queue, remember a queue is just like a line at the bank, first in first out)
             */
            int index = 0;
            upgradeMap.Add("3.0.0", index);
            upgradeMap.Add("3.0.2", ++index);
            upgradeMap.Add("3.0.3", index);
            upgradeMap.Add("3.1.0", ++index);
            upgradeMap.Add("3.2.0", ++index);
            upgradeMap.Add("3.2.1", index);
            upgradeMap.Add("3.3.0", ++index);
            upgradeMap.Add("3.3.1", index);
            upgradeMap.Add("3.4.0", ++index);
            upgradeMap.Add("3.4.1", index);
            upgradeMap.Add("3.4.2", index);
            upgradeMap.Add("3.4.3", index);
            upgradeMap.Add("4.0.0", ++index);
            upgradeMap.Add("4.0.1", index);
            upgradeMap.Add("4.1.0", ++index);
            upgradeMap.Add("4.1.1", index);
            upgradeMap.Add("4.1.2", index);
            upgradeMap.Add("4.1.3", index);
            upgradeMap.Add("4.2.0", ++index);
            upgradeMap.Add("4.2.1", ++index);
            upgradeMap.Add("4.3.0", ++index);
            upgradeMap.Add("4.4.0", ++index);
            upgradeMap.Add("4.5.0", ++index);
            upgradeMap.Add("4.6.0", ++index);
            upgradeMap.Add("4.7.0", index);
            upgradeMap.Add("4.8.0", index);
            upgradeMap.Add("4.8.1", index);
            upgradeMap.Add("4.8.2", index);
            upgradeMap.Add("4.8.3", ++index);
            upgradeMap.Add("4.8.4", ++index);
            upgradeMap.Add("5.0.0", ++index);
            upgradeMap.Add("5.1.0", index);
            //now instead of doing one script per upgrade where having multiple scripts so add the number 
            //of scripts your adding to index variable then set index var to that value as done below
            upgradeMap.Add("103", ++index); //103, 104, 105
            upgradeMap.Add("104", ++index); //103, 104, 105
            upgradeMap.Add("105", ++index); //103, 104, 105
            upgradeMap.Add("106", ++index); //103, 104, 105
            upgradeMap.Add("107", ++index); //103, 104, 105
            upgradeMap.Add("108", ++index); //103, 104, 105
            upgradeMap.Add("109", ++index); //103, 104, 105
            upgradeMap.Add("110", ++index); //103, 104, 105
            upgradeMap.Add("111", ++index); //103, 104, 105
            upgradeMap.Add("112", ++index); //103, 104, 105
            upgradeMap.Add("113", ++index); //103, 104, 105
            upgradeMap.Add("114", ++index); //103, 104, 105
            upgradeMap.Add("115", ++index); //103, 104, 105
            upgradeMap.Add("116", ++index); //103, 104, 105
            upgradeMap.Add("117", ++index); //103, 104, 105
            upgradeMap.Add("118", ++index); //103, 104, 105
            upgradeMap.Add("119", ++index); //103, 104, 105
            upgradeMap.Add("120", ++index); //103, 104, 105
            upgradeMap.Add("121", ++index); //103, 104, 105
            upgradeMap.Add("122", ++index); //103, 104, 105
            upgradeMap.Add("123", ++index); //103, 104, 105
            upgradeMap.Add("124", ++index); //103, 104, 105
            upgradeMap.Add("125", ++index); //103, 104, 105
            upgradeMap.Add("126", ++index); //103, 104, 105
            upgradeMap.Add("127", ++index); //103, 104, 105
            upgradeMap.Add("128", ++index); //103, 104, 105
            upgradeMap.Add("129", ++index); //103, 104, 105
            upgradeMap.Add("130", ++index); //103, 104, 105
            upgradeMap.Add("131", ++index); //103, 104, 105
            upgradeMap.Add("132", ++index);
            upgradeMap.Add("133", ++index);
            upgradeMap.Add("134", ++index);
            upgradeMap.Add("135", ++index);
            upgradeMap.Add("136", ++index);
            upgradeMap.Add("137", ++index);
            upgradeMap.Add("138", ++index);
            upgradeMap.Add("139", ++index);
            upgradeMap.Add("140", ++index);
            upgradeMap.Add("141", ++index);
            upgradeMap.Add("142", ++index);

            installScripts.AddLast("dbChange_103.sql");
            installScripts.AddLast("dbChange_104.sql");
            installScripts.AddLast("dbChange_105.sql");
            installScripts.AddLast("dbChange_106.sql");
            installScripts.AddLast("dbChange_107.sql");
            installScripts.AddLast("dbChange_108.sql");
            installScripts.AddLast("dbChange_109.sql");
            installScripts.AddLast("dbChange_110.sql");
            installScripts.AddLast("dbChange_111.sql");
            installScripts.AddLast("dbChange_112.sql");
            installScripts.AddLast("dbChange_113.sql");
            installScripts.AddLast("dbChange_114.sql");
            installScripts.AddLast("dbChange_115.sql");
            installScripts.AddLast("dbChange_116.sql");
            installScripts.AddLast("dbChange_117.sql");
            installScripts.AddLast("dbChange_118.sql");
            installScripts.AddLast("dbChange_119.sql");
            installScripts.AddLast("dbChange_120.sql");
            installScripts.AddLast("dbChange_121.sql");
            installScripts.AddLast("dbChange_122.sql");
            installScripts.AddLast("dbChange_123.sql");
            installScripts.AddLast("dbChange_124.sql");
            installScripts.AddLast("dbChange_125.sql");
            installScripts.AddLast("dbChange_126.sql");
            installScripts.AddLast("dbChange_127.sql");
            installScripts.AddLast("dbChange_128.sql");
            installScripts.AddLast("dbChange_129.sql");
            installScripts.AddLast("dbChange_130.sql");
            installScripts.AddLast("dbChange_131.sql");
            installScripts.AddLast("dbChange_132.sql");
            installScripts.AddLast("dbChange_133.sql");
            installScripts.AddLast("dbChange_134.sql");
            installScripts.AddLast("dbChange_135.sql");
            installScripts.AddLast("dbChange_136.sql");
            installScripts.AddLast("dbChange_137.sql");
            installScripts.AddLast("dbChange_138.sql");
            installScripts.AddLast("dbChange_139.sql");
            installScripts.AddLast("dbChange_140.sql");
            installScripts.AddLast("dbChange_141.sql");
            installScripts.AddLast("dbChange_142.sql");
        }

        public static void setMySQLInstalled(bool isMySQLInstalled)
        {
            MySQLInstalled = isMySQLInstalled;
        }

        public static bool getMySQLInstalled()
        {
            return MySQLInstalled;
        }

        public static void setODBCInstalled(bool isODBCInstalled)
        {
            ODBCInstalled = isODBCInstalled;
        }

        public static bool getODBCInstalled()
        {
            return ODBCInstalled;
        }

        public static void setInstall(bool install){
            Install = install;
        }

        public static bool getInstall()
        {
            return Install;
        }

        public static void setUpgrade(bool upgrade)
        {
            Upgrade = upgrade;
        }

        public static bool getUpgrade()
        {
            return Upgrade;
        }

        public static void setDbInfoName(string DbInfoName)
        {
            dbInfoName = DbInfoName;
        }

        public static string getDbInfoName()
        {
            return dbInfoName;
        }

        public static void setUpgradeScripts(LinkedList<string> theScripts)
        {
            upgradeScripts = theScripts;
        }


        public static LinkedList<string> getUpgradeScripts()
        {
            return upgradeScripts;
        }

        public static LinkedList<string> getInstallScripts()
        {
            return installScripts;
        }

        public static void setInstallScripts(LinkedList<string> theScripts)
        {
            installScripts = theScripts;
        }

        public static void setUpgradeMap(Dictionary<string, int> UpgradeMap)
        {
            upgradeMap = UpgradeMap;
        }

        public static Dictionary<string, int> getUpgradeMap()
        {
            return upgradeMap;
        }

        public static string getLatestJCMS()
        {
            return latestJCMS;
        }

        public static bool getInstallSuccess()
        {
            return installSuccess;
        }

        public static void setInstallSuccess(bool InstallSuccess)
        {
            installSuccess = InstallSuccess;
        }

        public static bool getUpgradeSuccess()
        {
            return upgradeSuccess;
        }

        public static void setUpgradeSuccess(bool UpgradeSuccess)
        {
            upgradeSuccess = UpgradeSuccess;
        }
    }
}
