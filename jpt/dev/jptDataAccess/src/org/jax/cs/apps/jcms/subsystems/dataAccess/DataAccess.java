package org.jax.cs.apps.jcms.subsystems.dataAccess;

import java.sql.SQLException;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daves
 * 	getParents( strMouseID)
	getChildren ( strMouseID)
	getSyblings( strMouseID )
	getMouseData(strMouseID)
	getMouseStrain(strMouseID)
	getMouseGenoType(strMouseID)
        getMouseList();



 */

public abstract class DataAccess {
    
    public  static final byte DISP_Mating   =     1; // 1  - Only Valid with Ancestory
    public final static byte DISP_Litter   =     2; // 2  - Only Valid with Progeny
    public final static byte DISP_Strain   =     4; // 3
    public final static byte DISP_Genotype =     8; // 4
    public final static byte DISP_Sex   =    16; // 5
    public final static byte DISP_spare2   =    32; // 6
    public final static byte DISP_spare3   =    64; // 7 
    // Can't do the eighth bit, bytes are signed.
    
    abstract String getPartent(String strMouseID);
    abstract String getChildren (String strMouseID);
    abstract String getSyblings(String strMouseID );
    abstract String getMouseData(String strMouseID);
    abstract String getMouseStrain(String strMouseID);
    abstract String getMouseGenoType(String strMouseID);
    abstract String getMouseList() throws SQLException;
    abstract String getVersion();
    
}
