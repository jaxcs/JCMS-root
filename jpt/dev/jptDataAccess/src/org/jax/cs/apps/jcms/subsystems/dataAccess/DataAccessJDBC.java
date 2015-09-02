package org.jax.cs.apps.jcms.subsystems.dataAccess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.*;
import java.lang.Exception.*;
import javax.swing.JOptionPane;
import static org.jax.cs.apps.jcms.subsystems.dataAccess.DataAccess.*;
/**
 *
 * @author daves
 */
public class DataAccessJDBC  {

    public boolean Connected; 
    private Connection dbConnect; 
    private String driver;
    private String url;
    private String uname;
    private String passwd;
    private Properties dbProp;
    
    private final int Access = 0 ;
    private final  int MySQL = 1 ;
    

    
    
    private byte displayOptions = 3;
    
    // Constructor  1 
    public DataAccessJDBC(String strDriver, String strURL)
    {
        driver = strDriver;
        url = strURL;
        // 
        conectToDatabase( 1 );
    }
    
    // Constructor 2
    public DataAccessJDBC(Properties prop)
    {
        
        int dbType;
        // get database type
        dbType = Integer.valueOf(prop.getProperty("databaseType"));
        
        // Configure database configuration
        if ( Access == dbType ) 
        {
            this.driver = prop.getProperty("databaseTypeDriver");
            this.url = prop.getProperty("jdbcURL");
            conectToDatabase( 1 );
            return;
        }
        
        if ( MySQL == dbType )
        {
            this.driver = prop.getProperty("databaseTypeDriver");
            this.url = prop.getProperty("jdbcURL");
            this.uname = prop.getProperty("dbUsername");
            this.passwd =   prop.getProperty("dbPassword");
            conectToDatabase( 3 );
            return;
        }
         
        // throw exception ??
        conectToDatabase( 2 );
    }

    // Constructor 3
    public DataAccessJDBC(String strDriver,  String strURL, String strUname, String strPasswd)
    {
        driver = strDriver;
        url = strURL;
        uname = strUname;
        passwd = strPasswd;
        // 
        conectToDatabase( 3 );
    }

    public void setDisplayOptions(byte displayOptions){
        this.displayOptions = displayOptions; 
    }
    
    public ArrayList getPartent (String strMouseID) throws SQLException 
    {
        ArrayList<String> myColl = new ArrayList<String>();
        List<String> tempList ;
                
        String mousey = "";  
        String MouseKey = "";
        String MySQLStatement;
        String matingID = "";
        

        MySQLStatement = "SELECT Mouse.ID as ID,  Mating.matingID as mID " +
            "FROM Mouse INNER JOIN Mating ON Mouse.`_mouse_key`=Mating.`_sire_key` Or Mouse.`_mouse_key`=Mating.`_dam1_key` Or Mouse.`_mouse_key`=Mating.`_dam2_key` " +
            "WHERE Mating.`_mating_key` in " +
            "(select Litter.`_mating_key` " + 
            "FROM Mouse INNER JOIN Litter ON Mouse.`_litter_key` = Litter.`_litter_key` " +
            "Where Mouse.`ID` = '" + strMouseID + "');";

        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Create Collection to return.
            
            while (tableData.next())
            {
                String nodeString = "";
                matingID = tableData.getString("mID");
                mousey = tableData.getString("ID");
                nodeString = mousey;
                
                if ( 0 < (DISP_Mating & displayOptions)) {
                    nodeString +=  " : M " + matingID; 
                }
                
                if (0 < (DISP_Strain & displayOptions)) {
                    tempList = getMouseStrain(mousey);
                    Iterator it = tempList.iterator ();
                    while ( it.hasNext ()) {
                        String strain = (String ) it.next ();
                        nodeString +=  " : S " + strain; 
                    }
                }

                if ( 0 < (DISP_Genotype & displayOptions)) {
                    tempList = getMouseGenoType(mousey);
                    Iterator it = tempList.iterator ();
                    it.hasNext ();

                    if ( 1 == tempList.size()) {
                        String genoType = (String ) it.next ();
                        nodeString +=  " : Gt " + genoType.replace('\t', ' ');

                    } else if ( 1 < tempList.size()) {
                        String genoType = (String ) it.next ();
                        nodeString +=  " : Gt " + genoType.replace('\t', ' ') + " ... ";

                    } else if ( 0 == tempList.size()) {
                        nodeString +=  " : Gt " + "         /     ";
                    } else {
                        // nasty message. 
                    }


                }
  
                if ( 0 < (DISP_Sex & displayOptions)) {
                    tempList = getMouseSex(mousey);
                    Iterator it = tempList.iterator ();
                    while ( it.hasNext ()) {
                        String sSex = (String ) it.next ();
                        nodeString +=  " : Sex " + sSex; 
                    }
                }

                myColl.add(nodeString);
            }
            
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
            throw(e);
        }

        return(myColl);
    }

    public ArrayList getChildren (String strMouseID) throws SQLException 
    {
        String MySQLStatement;
        
        ArrayList<String> myColl = new ArrayList<String>();

       
        MySQLStatement =  "SELECT Mouse.ID as ID, Mating.matingID as mID, Litter.litterID as lID " +
            "FROM (Mouse INNER JOIN Mating ON Mouse.`_mouse_key`=Mating.`_sire_key` Or Mouse.`_mouse_key`=Mating.`_dam1_key` Or Mouse.`_mouse_key`=Mating.`_dam2_key`) LEFT JOIN Litter ON Litter.`_mating_key`=Mating.`_mating_key` " +
            "WHERE Mouse.`_litter_key` in " +
            "(SELECT  Litter.`_litter_key` " + 
            "FROM (Mouse INNER JOIN Mating ON Mouse.`_mouse_key`=Mating.`_sire_key` Or Mouse.`_mouse_key`=Mating.`_dam1_key` Or Mouse.`_mouse_key`=Mating.`_dam2_key`) LEFT JOIN Litter ON Litter.`_mating_key`=Mating.`_mating_key` " + 
            "WHERE (Mouse.ID = '" + strMouseID + "') );";
        
        MySQLStatement = "SELECT Mouse.ID AS MID " +  
                " FROM  Mouse " + 
                " WHERE Mouse.`_litter_key` in " + 
                " ( " +
                " SELECT  Litter.`_litter_key`     " + 
                " FROM (Mouse INNER JOIN Mating ON Mouse.`_mouse_key`=Mating.`_sire_key` Or Mouse.`_mouse_key`=Mating.`_dam1_key` Or Mouse.`_mouse_key`=Mating.`_dam2_key`) LEFT JOIN Litter ON Litter.`_mating_key`=Mating.`_mating_key` " +
                " WHERE (Mouse.ID = '" + strMouseID + "') " +
                " ); ";

        // System.out.print(MySQLStatement);

        // data from our table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);
           
            // Display the contents
            while (tableData.next())
            {   
                String nodeString = "";
                List tempList;
                String mousey = tableData.getString("MID").trim();
                String litterID = getMouseLitterID(mousey);                     
                
                nodeString = mousey;

                //-------------------
                if ( 0 < (DISP_Litter & displayOptions)) {
                    nodeString +=  " : L " + litterID; 
                }
                
                if (0 < (DISP_Strain & displayOptions)) {
                    tempList = getMouseStrain(mousey);
                    Iterator it = tempList.iterator ();
                    while ( it.hasNext ()) {
                        String strain = (String ) it.next ();
                        nodeString +=  " : S " + strain; 
                    }
                }

                if ( 0 < (DISP_Genotype & displayOptions)) {
                    tempList = getMouseGenoType(mousey);
                    Iterator it = tempList.iterator ();
                    it.hasNext ();

                    if ( 1 == tempList.size()) {
                        String genoType = (String ) it.next ();
                        nodeString +=  " : Gt " + genoType.replace('\t', ' ');
                    } else if ( 1 < tempList.size()) {
                        String genoType = (String ) it.next ();
                        nodeString +=  " : Gt " + genoType.replace('\t', ' ') + " ... ";

                    } else if ( 0 == tempList.size()) {
                        nodeString +=  " : Gt " + "         /     ";
                    } else {
                        // nasty message.
                    }
              }
                
                if ( 0 < (DISP_Sex & displayOptions)) {
                    tempList = getMouseSex(mousey);
                    Iterator it = tempList.iterator ();
                    while ( it.hasNext ()) {
                        String sSex = (String ) it.next ();
                        nodeString +=  " : Sex " + sSex; 
                    }
                }
                
                myColl.add( nodeString );
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(myColl);
    }

    public ArrayList getSyblings(String strMouseID ) throws SQLException 
    {

        ArrayList<String> myColl = new ArrayList<String>();


        String MySQLStatement;
        String strTemp = "";

        MySQLStatement = "SELECT Mouse.ID FROM Mouse " +
                " WHERE Mouse.`_litter_key` in " +
                " ( SELECT Mouse.`_litter_key` " +
                "   FROM Mouse WHERE (Mouse.ID = '" + strMouseID + "'));";

        // data from our table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                strTemp = tableData.getString("ID");
                if ( ! strMouseID.equals(strTemp)) 
                    myColl.add(strTemp);
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        return(myColl);
    }

    
    
    
    public ArrayList getMouseData(String strMouseID) throws SQLException 
    {

        ArrayList<String> myColl = new ArrayList<String>();

        String MySQLStatement;

        
        MySQLStatement = "SELECT * " + 
                    " FROM Mouse " + 
                    " WHERE (Mouse.ID='" + strMouseID + "');";
                
        // System.out.print(MySQLStatement);
       
        // data from our table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                myColl.add("ID=" + tableData.getString("ID") );
                myColl.add("origin=" + tableData.getString("origin")  );
                myColl.add("protocol=" + tableData.getString("protocol") );
                myColl.add("owner=" + tableData.getString("owner") );
                myColl.add("sex=" + tableData.getString("sex") );
                myColl.add("generation=" + tableData.getString("generation") );
                myColl.add("codNotes=" + tableData.getString("codNotes"));
                myColl.add("cod=" + tableData.getString("cod") );
                myColl.add("exitDate=" + tableData.getString("exitDate") );
                myColl.add("birthDate=" + tableData.getString("birthDate") );
                myColl.add("comment=" + tableData.getString("comment") );
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(myColl);
    }

    
    public ArrayList getMouseStrain(String strMouseID) throws SQLException 
    {
        ArrayList<String> myColl = new ArrayList<String>();

        String MySQLStatement;
        
        MySQLStatement = "SELECT Strain.strainName " + 
                    " FROM Mouse INNER JOIN Strain ON Mouse.`_strain_key`=Strain.`_strain_key` " + 
                    " WHERE (Mouse.ID='" + strMouseID + "');";
                
        // System.out.print(MySQLStatement);
       
        // data from our table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                myColl.add("{" + tableData.getString("strainName") + "}");
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(myColl);
    
    }
    
    
    public ArrayList getMouseGenoType(String strMouseID) throws SQLException 
    {
        
        ArrayList<String> myColl = new ArrayList<String>();

        String MySQLStatement;

        MySQLStatement = "SELECT  Gene.labSymbol, Gene.geneClass, " +
            "Genotype.allele1,Genotype.all1Conf,Genotype.allele2, " + 
            "Genotype.all2Conf " + 
            "FROM ( Mouse " + 
            "LEFT JOIN Genotype ON Mouse.`_mouse_key`=Genotype.`_mouse_key`) "+
            "LEFT JOIN Gene  ON Gene.`_gene_key`=Genotype.`_gene_key` " +
            "WHERE (Mouse.ID = '" + strMouseID + "');";
                
//        System.out.print(MySQLStatement);
        
        // data from our table
        try
        {
            String myGenoType;

            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                myGenoType = tableData.getString("labSymbol")
                        +  "\t"
                        + tableData.getString("allele1") + "/"
                        + tableData.getString("allele2") ;

                myColl.add( myGenoType.replace("null", "   ")  );
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(myColl);

    }

    
    public ArrayList getMouseSex(String strMouseID){

        List<String> myColl = new ArrayList<String>();

        String MySQLStatement;

        MySQLStatement = "SELECT  Mouse.sex as sex  " +
            "FROM Mouse " + 
            "WHERE (Mouse.ID = '" + strMouseID.trim() + "');";
                
//        System.out.print(MySQLStatement);
        
        // data from our table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                myColl.add( tableData.getString("sex") );
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }

        return((ArrayList<String>)myColl);
       
    }
   
    public ArrayList getMouseList() throws SQLException 
    {
        ArrayList<String> myColl = new ArrayList<String>();
        String MySQLStatement;

        MySQLStatement = "SELECT `ID` FROM mouse ORDER BY `ID` ASC;" ;  
        
//        System.out.print(MySQLStatement);
        
        // Data from our Table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                myColl.add( tableData.getString("ID") );
                    
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
            throw (e);
        }
        
        return (myColl);
    }
    
//------
    
    public String getMouseLitterID(String strMouseID) throws SQLException 
    {
        String MySQLStatement;
        String retString = "";
      
        MySQLStatement = "select litterID as ID  " + 
                        "from Litter left join Mouse on Mouse.`_litter_key` = Litter.`_litter_key` " +
                        "where Mouse.ID = '" + strMouseID +"' ;" ;  
        
        // Data from our Table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                retString = tableData.getString("ID");
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
            throw (e);

        }
        return retString;
        
    }

    public String getMouseMatingID(String strMouseID) throws SQLException 
    {
        String MySQLStatement;
        String retString = "";
        
        MySQLStatement = "SELECT MatingID as ID   " + 
                       "FROM (Mating LEFT JOIN Litter ON Mating.`_Mating_key`=Litter.`_Mating_key`) LEFT JOIN Mouse ON Mouse.`_litter_key`=Litter.`_litter_key` " +
                       "where Mouse.ID = '" + strMouseID.trim() + "' ;" ;  

        // Data from our Table
        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                retString = tableData.getString("ID");
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
            throw (e);            
        }
        return retString;
    }
    
    public String getVersion()
    { 
       
        return("3.14");
    }
          
    
    private void conectToDatabase(int iConstructor)
    {
        Connected = false;
        // Open database
        //-------------------------------------------------------------------------
        // registers the Jdbc-Odbc driver with the driver manager
        // New JdbcOdbcDriver();

        // First, we need to load the driver
        try
        {
            // Java Specific
            // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Driver Not Found!", "DataAccessJDBC", 1); 
            System.out.println("Unable to load driver: " + e);
            return;
        }

            // J# Specific 
            // Class.forName("com.ms.jdbc.odbc.JdbcOdbcDriver");
      
        /**  This creates the connection object for your database which will be used for the rest of the example */

        //    Connection myConnection = DriverManager.getConnection(url, myUser, myPassword);
        // ------------------------------------
        // Now, we can open a connection to the database and begin working
        try
        {
            // Get a DB connection from the DriverManager
            // Connection dbConnect = DriverManager.getConnection("jdbc:odbc:Tutorial", "", "");

            if (1 == iConstructor)
                dbConnect = DriverManager.getConnection(url);
            else if ( 2 == iConstructor)
                dbConnect = DriverManager.getConnection(url, dbProp);
            else if ( 3 == iConstructor)
                dbConnect = DriverManager.getConnection(url, uname, passwd);
            else 
                throw new Exception("Something happened, Invalid Constructor.");
            
            
            // Let's create a simple SQL statement to get all of the+
        }
        catch (SQLException e )
        {
//            JOptionPane.showMessageDialog(null, "SQL Connect Error", "DataAccessJDBC", 1); 
            System.out.println("An error occured while interacting with DB: " + e);
            return;
        }
        catch (Exception e )
        {
            System.out.println("Invalid Constructor" + e);
            return;
        }
    
            Connected = true;

    }

}
