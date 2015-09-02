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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jcms.integrationtier.base.ITBaseEntity;
import jcms.integrationtier.base.ITBaseEntityInterface;

/**
 * <b>File name:</b>  MouseUsageEntity.java  <p>
 * <b>Date developed:</b>  August 2010 <p>
 * <b>Purpose:</b>  Contains all Mouse Use information  <p>
 * <b>Inputs:</b>  None   <p>
 * <b>Outputs:</b>  Named queries can be invoked from a local or remote session
 *                  beans.  EJB query language provides support
 *                  for setting query parameters as indicated by
 *                  colon and parameter syntax, :ParameterName.  <p>
 * <b>Last changed by:</b>   $Author$ <p>
 * <b>Last changed date:</b> $Date$   <p>
 * @author Kavitha Rama
 * @version $Revision$
 */
@Entity
@Table(name = "MouseUsage")
@NamedQueries({
    @NamedQuery(
    name = "MouseUsageEntity.findAll",
            query = "SELECT m FROM MouseUsageEntity m " +
            "ORDER BY m.use"),

    @NamedQuery(
    name = "MouseUsageEntity.findBykey",
            query = "SELECT m FROM MouseUsageEntity m WHERE m.usageKey = :key"),

    @NamedQuery(name = "MouseUsageEntity.findByUsageKey", query = "SELECT m FROM MouseUsageEntity m WHERE m.usageKey = :usageKey"),
    @NamedQuery(name = "MouseUsageEntity.findByUse", query = "SELECT m FROM MouseUsageEntity m WHERE m.use = :use"),
    @NamedQuery(name = "MouseUsageEntity.findByUseAge", query = "SELECT m FROM MouseUsageEntity m WHERE m.useAge = :useAge"),
    @NamedQuery(name = "MouseUsageEntity.findByProjectedDate", query = "SELECT m FROM MouseUsageEntity m WHERE m.projectedDate = :projectedDate"),
    @NamedQuery(name = "MouseUsageEntity.findByActualDate", query = "SELECT m FROM MouseUsageEntity m WHERE m.actualDate = :actualDate"),
    @NamedQuery(name = "MouseUsageEntity.findByDone", query = "SELECT m FROM MouseUsageEntity m WHERE m.done = :done"),
    @NamedQuery(name = "MouseUsageEntity.findByComment", query = "SELECT m FROM MouseUsageEntity m WHERE m.comment = :comment"),
    @NamedQuery(name = "MouseUsageEntity.findByD1", query = "SELECT m FROM MouseUsageEntity m WHERE m.d1 = :d1"),
    @NamedQuery(name = "MouseUsageEntity.findByD2", query = "SELECT m FROM MouseUsageEntity m WHERE m.d2 = :d2"),
    @NamedQuery(name = "MouseUsageEntity.findByD3", query = "SELECT m FROM MouseUsageEntity m WHERE m.d3 = :d3"),
    @NamedQuery(name = "MouseUsageEntity.findByD4", query = "SELECT m FROM MouseUsageEntity m WHERE m.d4 = :d4"),
    @NamedQuery(name = "MouseUsageEntity.findByD5", query = "SELECT m FROM MouseUsageEntity m WHERE m.d5 = :d5"),
    @NamedQuery(name = "MouseUsageEntity.findByD6", query = "SELECT m FROM MouseUsageEntity m WHERE m.d6 = :d6"),
    @NamedQuery(name = "MouseUsageEntity.findByD7", query = "SELECT m FROM MouseUsageEntity m WHERE m.d7 = :d7"),
    @NamedQuery(name = "MouseUsageEntity.findByD8", query = "SELECT m FROM MouseUsageEntity m WHERE m.d8 = :d8"),
    @NamedQuery(name = "MouseUsageEntity.findByD9", query = "SELECT m FROM MouseUsageEntity m WHERE m.d9 = :d9"),
    @NamedQuery(name = "MouseUsageEntity.findByD10", query = "SELECT m FROM MouseUsageEntity m WHERE m.d10 = :d10"),
    @NamedQuery(name = "MouseUsageEntity.findByVersion", query = "SELECT m FROM MouseUsageEntity m WHERE m.version = :version")})

public class MouseUsageEntity extends ITBaseEntity implements Serializable, ITBaseEntityInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "_usage_key")
    private Integer usageKey;

    @Basic(optional = false)
    @Column(name = "use")
    private String use;

    @Column(name = "useAge")
    private Double useAge;

    @Column(name = "projectedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectedDate;

    @Column(name = "actualDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualDate;

    @Basic(optional = false)
    @Column(name = "done")
    private boolean done;

    @Column(name = "comment")
    private String comment;

    @Column(name = "D1")
    private String d1;

    @Column(name = "D2")
    private String d2;

    @Column(name = "D3")
    private String d3;

    @Column(name = "D4")
    private String d4;

    @Column(name = "D5")
    private String d5;

    @Column(name = "D6")
    private String d6;

    @Column(name = "D7")
    private String d7;

    @Column(name = "D8")
    private String d8;

    @Column(name = "D9")
    private String d9;

    @Column(name = "D10")
    private String d10;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    
    @JoinColumn(name = "_mouse_key", referencedColumnName = "_mouse_key")
    @ManyToOne(optional = false)
    private MouseEntity mouseKey;

    public MouseUsageEntity() {
    }

    public MouseUsageEntity(Integer usageKey) {
        this.usageKey = usageKey;
    }

    public MouseUsageEntity(Integer usageKey, String use, boolean done, int version) {
        this.usageKey = usageKey;
        this.use = use;
        this.done = done;
        this.version = version;
    }

    public Integer getUsageKey() {
        return usageKey;
    }

    public void setUsageKey(Integer usageKey) {
        this.usageKey = usageKey;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public Double getUseAge() {
        return useAge;
    }

    public void setUseAge(Double useAge) {
        this.useAge = useAge;
    }

    public Date getProjectedDate() {
        return projectedDate;
    }

    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public String getD7() {
        return d7;
    }

    public void setD7(String d7) {
        this.d7 = d7;
    }

    public String getD8() {
        return d8;
    }

    public void setD8(String d8) {
        this.d8 = d8;
    }

    public String getD9() {
        return d9;
    }

    public void setD9(String d9) {
        this.d9 = d9;
    }

    public String getD10() {
        return d10;
    }

    public void setD10(String d10) {
        this.d10 = d10;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public MouseEntity getMouseKey() {
        return mouseKey;
    }

    public void setMouseKey(MouseEntity mouseKey) {
        this.mouseKey = mouseKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usageKey != null ? usageKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MouseUsageEntity)) {
            return false;
        }
        MouseUsageEntity other = (MouseUsageEntity) object;
        if ((this.usageKey == null && other.usageKey != null) || (this.usageKey != null && !this.usageKey.equals(other.usageKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcms.integrationtier.colonyManagement.MouseUsageEntity[usageKey=" + usageKey + "]";
    }

     @Override
    public Integer getKey() {
        return usageKey;
    }

    @Override
    public ITBaseEntityInterface getEntity() {
        return this ;
    }

    @Override
    public void printDetail()
    {

    }
}