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

/**
 * UserDTO.java
 *
 * Created on Feb 16, 2010
 *
 * This class encapsulates the code and data necessary to describe a single
 * user of this application. It is intended to be a superset of the UserEntity.
 *
 * @author mrelac
 */

package jcms.integrationtier.dto;

import java.util.HashMap;
import jcms.integrationtier.base.ITBaseObject;
import jcms.integrationtier.jcmsWeb.FunctionalAreaEntity;
import jcms.integrationtier.jcmsWeb.UserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupUserEntity;
import jcms.integrationtier.jcmsWeb.WorkgroupUserFunctionalAreaEntity;

public class UserDTO  extends ITBaseObject {
    // -Xlint compile options likes this ...
    private static final long serialVersionUID = 1L;
    private UserEntity                 userEntity           = null;
    private WorkgroupEntity            currentWorkgroup     = null;
    private WorkgroupUserEntity        currentWorkgroupUser = null;

    // This holds the workgroupUser's wufa collection, keyed by functional area name.
    private HashMap<String, WorkgroupUserFunctionalAreaEntity>  wufaHash = null;

    /**
     * <b>Purpose:</b> Creates a new, empty <code>UserDTO</code> object. <br />
     */
    public UserDTO() {
        
    }

    /**
     * <b>Purpose:</b> Creates a new <code>UserDTO</code> object containing the
     * given userEntity. The user's current workgroup will be set to their
     * default workgroup. <br />
     * @param userEntity the given UserEntity
     */
    public UserDTO( UserEntity userEntity ) {
        this( userEntity, null );
    }

    /**
     * <b>Purpose:</b> Creates a new <code>UserDTO</code> object containing the
     * given userEntity and given currentWorkgroup. <br />
     * @param userEntity the given UserEntity
     * @param currentWorkgroup the current workgroup
     */
    public UserDTO( UserEntity userEntity, WorkgroupEntity currentWorkgroup ) {
        this.userEntity       = userEntity;
        setCurrentWorkgroup( currentWorkgroup );
    }

    // GETTERS AND SETTERS

    /**
     * Though they may belong to many workgroups, by design, the user is always
     * associated with exactly one workgroup, known as the current workgroup.
     * This method returns that workgroup.
     *
     * @return the workgroup to which the user currently belongs
     */
    public WorkgroupEntity getCurrentWorkgroup() {
        return currentWorkgroup;
    }

    /**
     * Sets the current workgroup to the user's default workgroup.
     */
    public void setCurrentWorkgroup() {
        setCurrentWorkgroup( null );
    }

    /**
     * Sets the current workgroup to <code>currentWorkgroup</code>. If <code>
     * currentWorkgroup</code> is null, the current workgroup is set to the
     * user's default workgroup.
     *
     * @param currentWorkgroup the new current workgroup
     */
    public void setCurrentWorkgroup( WorkgroupEntity currentWorkgroup ) {
        this.currentWorkgroup = (currentWorkgroup == null ? userEntity.getDefaultWorkgroupkey() : currentWorkgroup);
        // Find the workgroupUserEntity and eagerly load it.
        //this.currentWorkgroupUser = new IntegrationTierPortal().getAdminFacadeLocal().findWorkgroupUser( currentWorkgroup, userEntity );

        // Load the workgroup user's WUFA into the wufa hash.
        wufaHash = new HashMap<String, WorkgroupUserFunctionalAreaEntity>();
        for ( WorkgroupUserFunctionalAreaEntity wufae : currentWorkgroupUser.getWorkgroupUserFunctionalAreaEntityCollection() ) {
            wufaHash.put( wufae.getFunctionalAreakey().getFunctionalArea(), wufae );
        }
    }

    /**
     * Though they may belong to many workgroups, by design, the user is always
     * associated with exactly one workgroup, known as the current workgroup.
     * This method returns the WorkgroupUserEntity corresponding to this user's
     * currently selected workgroup.
     *
     * @return the workgroupUser entity to which the user currently belongs
     */
    public WorkgroupUserEntity getCurrentWorkgroupUser() {
        return currentWorkgroupUser;
    }

    /**
     * Returns true if this user has either read or write access to ADMINISTRATION
     */
    public boolean getHasAdministrationAccess() {
        return wufaHash.containsKey( FunctionalAreaEntity.ADMINISTRATION );
    }

    /**
     * Returns true if this user has Read-only access to ADMINISTRATION
     */
    public boolean getHasAdministrationReadOnlyAccess() {
        boolean retVal = false;

        WorkgroupUserFunctionalAreaEntity wufae = wufaHash.get( FunctionalAreaEntity.ADMINISTRATION );

        return retVal;
    }

    /**
     * Returns true if this user has Write access to ADMINISTRATION
     */
    public boolean getHasAdministrationWriteAccess() {
        boolean retVal = false;

        WorkgroupUserFunctionalAreaEntity wufae = wufaHash.get( FunctionalAreaEntity.ADMINISTRATION );

        return retVal;
    }

    /**
     * Returns true if this user has either read or write access to ADMINISTRATION
     */
    public boolean getHasQueryingAccess() {
        return wufaHash.containsKey( FunctionalAreaEntity.QUERYING );
    }

    /**
     * Returns true if this user has Read-only access to ADMINISTRATION
     */
    public boolean getHasQueryingReadOnlyAccess() {
        boolean retVal = false;

        WorkgroupUserFunctionalAreaEntity wufae = wufaHash.get( FunctionalAreaEntity.QUERYING );

        return retVal;
    }

    /**
     * Returns true if this user has Write access to ADMINISTRATION
     */
    public boolean getHasQueryingWriteAccess() {
        boolean retVal = false;

        WorkgroupUserFunctionalAreaEntity wufae = wufaHash.get( FunctionalAreaEntity.QUERYING );
        //if ( (wufae != null) && wufae.getPrivilegekey().getIsWriteable() ) {
           // retVal = true;
       // }

        return retVal;
    }
        
    /**
     * <b>Purpose:</b> Get the name of this user, suitable for display, in
     * the form <b>FirstName LastName</b>.  <br />
     * @return the name of this user, suitable for display, in the form
     *          <b>FirstName LastName</b>
     */
    public String getFirstNameLastName()
    {
        return (userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    /**
     * <b>Purpose:</b> Get the name of this user, suitable for display, in
     * the form <b>LastName, FirstName</b>.  <br />
     * @return the name of this user, suitable for display, in the form
     *          <b>LastName, FirstName</b>
     */
    public String getLastNameFirstName()
    {
        return userEntity.getLastName() + ", " + userEntity.getFirstName() ;
    }

    /**
     * <b>Purpose:</b> Return the user entity.  <br />
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * <b>Purpose:</b> Set the user entity.  <br />
     * @param userEntity the <code>UserEntity</code> to set
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}