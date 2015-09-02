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

package jcms.integrationtier.colonyManagement;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 *
 * @author rkavitha
 */
@Entity
@Table(name = "DbSetup")
@NamedQueries({
    @NamedQuery(name = "DbsetupEntity.findAll", query = "SELECT d FROM DbsetupEntity d"),
    
    @NamedQuery(
    name = "DbsetupEntity.findBykey",
    query = "SELECT d FROM DbsetupEntity d WHERE d.dbSetupkey = :key"),
    
    @NamedQuery(name = "DbsetupEntity.findByDbSetupkey", query = "SELECT d FROM DbsetupEntity d WHERE d.dbSetupkey = :dbSetupkey"),
    @NamedQuery(name = "DbsetupEntity.findByMTSVar", query = "SELECT d FROM DbsetupEntity d WHERE d.mTSVar = :mTSVar"),
    @NamedQuery(name = "DbsetupEntity.findByMTSValue", query = "SELECT d FROM DbsetupEntity d WHERE d.mTSValue = :mTSValue"),
    @NamedQuery(name = "DbsetupEntity.findByMTSVarDescription", query = "SELECT d FROM DbsetupEntity d WHERE d.mTSVarDescription = :mTSVarDescription"),
    @NamedQuery(name = "DbsetupEntity.findByVersion", query = "SELECT d FROM DbsetupEntity d WHERE d.version = :version")})

public class DbsetupEntity extends ITBaseEntity implements Serializable,
            ITBaseEntityInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "_dbSetup_key")
    private Integer dbSetupkey;
    @Basic(optional = false)
    @Column(name = "MTSVar")
    private String mTSVar;
    @Column(name = "MTSValue")
    private String mTSValue;
    @Column(name = "MTSVarDescription")
    private String mTSVarDescription;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public DbsetupEntity() {
    }

    public DbsetupEntity(Integer dbSetupkey) {
        this.dbSetupkey = dbSetupkey;
    }

    public DbsetupEntity(Integer dbSetupkey, String mTSVar, int version) {
        this.dbSetupkey = dbSetupkey;
        this.mTSVar = mTSVar;
        this.version = version;
    }

    public Integer getDbSetupkey() {
        return dbSetupkey;
    }

    public void setDbSetupkey(Integer dbSetupkey) {
        this.dbSetupkey = dbSetupkey;
    }

    public String getMTSVar() {
        return mTSVar;
    }

    public void setMTSVar(String mTSVar) {
        this.mTSVar = mTSVar;
    }

    public String getMTSValue() {
        return mTSValue;
    }

    public void setMTSValue(String mTSValue) {
        this.mTSValue = mTSValue;
    }

    public String getMTSVarDescription() {
        return mTSVarDescription;
    }

    public void setMTSVarDescription(String mTSVarDescription) {
        this.mTSVarDescription = mTSVarDescription;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbSetupkey != null ? dbSetupkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbsetupEntity)) {
            return false;
        }
        DbsetupEntity other = (DbsetupEntity) object;
        if ((this.dbSetupkey == null && other.dbSetupkey != null) || (this.dbSetupkey != null && !this.dbSetupkey.equals(other.dbSetupkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.DbsetupEntity[ dbSetupkey=" + dbSetupkey + " ]";
    }
    
    @Override
    public Integer getKey() {
        return dbSetupkey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {
        System.out.println("\n");
        System.out.println(this.getClass().getSimpleName() + " Contents");
        System.out.println("================================");
        System.out.println("\tKey" + "\t" + this.getKey() );
        System.out.println("\tVersion" + "\t" + this.getVersion() );
    }    
}