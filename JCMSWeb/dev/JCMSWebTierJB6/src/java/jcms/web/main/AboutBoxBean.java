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
package jcms.web.main;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import jcms.integrationtier.colonyManagement.JCMSDbInfoEntity;
import jcms.integrationtier.dao.Connector;
import jcms.integrationtier.dao.DataSourceProperty;
import jcms.integrationtier.jcmsWeb.DbInfoEntity;
import jcms.middletier.dto.ListSupportDTO;
import jcms.web.base.WTBaseBackingBean;

/**
 * Backing bean for AboutBox.xhtml
 */
public class AboutBoxBean extends WTBaseBackingBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private DbInfoEntity dbInfoEntity = new DbInfoEntity();
    private JCMSDbInfoEntity jcmsDbInfoEntity = new JCMSDbInfoEntity();
    private String hostName = "";
    private String adminLink = "";
    private ArrayList<String> connections = null;
    private String softwareVersionNumber = "3.14.0";

    public AboutBoxBean() {
        this.setAdminLink(this.populateAdminModuleLink());
        this.initialize();
    }

    private void initialize() {
        if (this.getRepositoryService().getDatabaseInformation() != null) {
            dbInfoEntity = this.getRepositoryService().getDatabaseInformation().getDbInfoEntity();

            List<JCMSDbInfoEntity> info = new ArrayList<JCMSDbInfoEntity>();
             info = new ListSupportDTO().getJCMSDbInfo();

             if (info.size()>0) {
                 this.setJcmsDbInfoEntity(info.get(0));
             }
        }
        this.setHostName(this.getDBHostname());
    }

    public String getDBHostname() {
        String hostname = "";
        try {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();

            if (localMachine != null) {
                hostname = localMachine.getHostName();
            }
        }
        catch (java.net.UnknownHostException e) { 
            this.addToMessageQueue(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            this.getLogger().logWarn(this.formatLogMessage("Exception ",
                    e.getMessage()));
        }
        return hostname;
    }

    public String populateAdminModuleLink() {
        String link = "";
        link = "http://" + this.getDBHostname() + ":8080/rs/";

        return link;
    }

    public DbInfoEntity getDbInfoEntity() {
        return dbInfoEntity;
    }
    
    public void setDbInfoEntity(DbInfoEntity dbInfoEntity) {
        this.dbInfoEntity = dbInfoEntity;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the jcmsDbInfoEntity
     */
    public JCMSDbInfoEntity getJcmsDbInfoEntity() {
        return jcmsDbInfoEntity;
    }

    /**
     * @param jcmsDbInfoEntity the jcmsDbInfoEntity to set
     */
    public void setJcmsDbInfoEntity(JCMSDbInfoEntity jcmsDbInfoEntity) {
        this.jcmsDbInfoEntity = jcmsDbInfoEntity;
    }

    /**
     * @return the adminLink
     */
    public String getAdminLink() {
        return adminLink;
    }

    /**
     * @param adminLink the adminLink to set
     */
    public void setAdminLink(String adminLink) {
        this.adminLink = adminLink;
    }

    /**
     * @return the connections
     */
    public ArrayList<String> getConnections() {
        Connector connector = new Connector();
        DataSourceProperty property = null;
        ArrayList<String> connectionURLs = new ArrayList<String> ();
        String dbDetail = "";
        try {
            property = connector.getDatabaseConnectionParameters(Connector.JCMSDEFAULTDS);
            if (property.getConnectionUrl().length() > 0)
                connectionURLs.add(formatConnectionUrl(Connector.JCMSDEFAULTDS.toString(), property.getConnectionUrl()));
        } catch (FileNotFoundException fnf1) {  }
        try {
            property = connector.getDatabaseConnectionParameters(Connector.JCMSWebDS);
            if (property.getConnectionUrl().length() > 0)
                connectionURLs.add(formatConnectionUrl(Connector.JCMSWebDS.toString(), property.getConnectionUrl()));
        } catch (FileNotFoundException fnf1) {  }
        try {
            property = connector.getDatabaseConnectionParameters(Connector.JCMSWebDBDS);
            if (property.getConnectionUrl().length() > 0)
                connectionURLs.add(formatConnectionUrl(Connector.JCMSWebDBDS.toString(), property.getConnectionUrl()));
        } catch (FileNotFoundException fnf1) {  }
        
        return connectionURLs;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(ArrayList<String> connections) {
        this.connections = connections;
    }

    private String formatConnectionUrl(String name, String dbDetail) {
        dbDetail = dbDetail.replace("jdbc:mysql://", "");
        dbDetail = dbDetail.replace(":3306/", " : ");
        dbDetail = dbDetail.replace(";", "");
        dbDetail = name + " : " + dbDetail;
        return dbDetail;
    }

    /**
     * @return the softwareVersionNumber
     */
    public String getSoftwareVersionNumber() {
        return softwareVersionNumber;
    }

    /**
     * @param softwareVersionNumber the softwareVersionNumber to set
     */
    public void setSoftwareVersionNumber(String softwareVersionNumber) {
        this.softwareVersionNumber = softwareVersionNumber;
    }
}