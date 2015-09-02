package org.jax.cs.apps.jcms.subsystems.dataAccess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;

/**
 *
 * @author daves
 */
public class DataAccessMySQL {
    
    Connection dbConnect;

        
    DataAccessMySQL()
    {
        String ConnectString;
        ConnectString = "jdbc:mysql://localhost:3306/jpt_ut";
        conectToDatabase(ConnectString,"root","myadmin");

        // Open database
        //-------------------------------------------------------------------------
        // registers the Jdbc-Odbc driver with the driver manager
        // New JdbcOdbcDriver();
    }
     
        
    DataAccessMySQL(String ConnectString, String uName, String uPasswd)
    {

        
        conectToDatabase(ConnectString,uName,uPasswd);

        return ;
/*        // Open database
        //-------------------------------------------------------------------------
        // registers the Jdbc-Odbc driver with the driver manager
        // New JdbcOdbcDriver();

        // First, we need to load the driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }

        catch (Exception e)
        {
            System.out.println("Unable to load driver: " + e);
        }

        //  This creates the connection object for your database which will be used for the rest of the example 

        //    Connection myConnection = DriverManager.getConnection(url, myUser, myPassword);
        // ------------------------------------
        // Now, we can open a connection to the database and begin working
        try
        {
            // Get a DB connection from the DriverManager
            // Connection dbConnect = DriverManager.getConnection("jdbc:odbc:Tutorial", "", "");

            dbConnect = DriverManager.getConnection(ConnectString,uName, uPasswd);

            // Let's create a simple SQL statement to get all of the+
        }
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
 */
 
    }
        
    public String getPartent(String strMouseID)
    {
        // Mousey is the return string
        String Mousey = "";
        String MouseKey = "";
        String MySQLStatement;
        
        MySQLStatement = "SELECT Mating.`_dam1_key` as Dam1 , Mating.`_dam2_key` as Dam2, Mating.`_sire_key` as Sire " +
                          " FROM Mating " +
                          " WHERE Mating.`_mating_key` in " +
                          " (select Litter.`_mating_key` " +
                          " FROM Mouse INNER JOIN Litter ON Mouse.`_litter_key` = Litter.`_litter_key` " +
                          " Where Mouse.`ID` = '" + strMouseID + "');";

        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            tableData.next();
            
            MouseKey = tableData.getString("Dam1");
            Mousey = getMouseID(Integer.valueOf(MouseKey).intValue());
            // PrintMyLine(depth, Mousey);
            // FindAncestors(depth, Mousey);

            MouseKey = tableData.getString("Dam2");
            Mousey = Mousey + "\n" + getMouseID(Integer.valueOf(MouseKey).intValue());
            // PrintMyLine(depth, Mousey);
            // FindAncestors(depth, Mousey);

            MouseKey = tableData.getString("Sire");
            Mousey = Mousey + "\n" + getMouseID(Integer.valueOf(MouseKey).intValue());
           // PrintMyLine(depth, Mousey);
           // FindAncestors(depth, Mousey);
            
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }

        // System.out.print(MySQLStatement + "\n");
        return(Mousey);
    }
    public String getChildren (String strMouseID)
    {
        String Mousey = "";
        String MySQLStatement;

        MySQLStatement = "SELECT Mouse.ID FROM Mouse" +
                " WHERE (((Mouse.`_litter_key`) In (SELECT  Litter.`_litter_key` " +
                " FROM (Mouse INNER JOIN Mating ON Mouse.`_mouse_key`=Mating.`_sire_key` Or " +
                    " Mouse.`_mouse_key`=Mating.`_dam1_key` Or " +
                    " Mouse.`_mouse_key`=Mating.`_dam2_key`) " +
               " LEFT JOIN Litter ON Litter.`_mating_key`=Mating.`_mating_key` " +
               " WHERE (Mouse.ID = '" + strMouseID + "'))));";

        // System.out.print(MySQLStatement);

        try
        {
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                Mousey = Mousey + tableData.getString("ID") + "\n";
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(Mousey);
    
    }
    public String getSyblings(String strMouseID )
    {
       
        String Mousey = "";
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
                    Mousey = Mousey + strTemp + "\n";
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(Mousey);

    
    }
    public String getMouseData(String strMouseID)
    {

        String Mousey = "";
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
//                Mousey = "{" + tableData.getString("Mating ID") + "}:";
//                Mousey = "{" + tableData.getString("Litter ID") + "}:";
                Mousey = "ID=" + tableData.getString("ID") + "\n";
                Mousey = Mousey + "origin=" + tableData.getString("origin") + "\n";
                Mousey = Mousey +  "protocol=" + tableData.getString("protocol") + "\n";
                Mousey = Mousey +  "owner=" + tableData.getString("owner") + "\n";
                Mousey = Mousey +  "sex=" + tableData.getString("sex") + "\n";
                Mousey = Mousey +  "generation=" + tableData.getString("generation") + "\n";
                Mousey = Mousey +  "codNotes=" + tableData.getString("codNotes") + "\n";
                Mousey = Mousey +  "cod=" + tableData.getString("cod") + "\n";
                Mousey = Mousey +  "exitDate=" + tableData.getString("exitDate") + "\n";
                Mousey = Mousey +  "birthDate=" + tableData.getString("birthDate") + "\n";
                Mousey = Mousey +  "comment=" + tableData.getString("comment") + "\n";
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(Mousey);

        
   
  
        
    
    }
    public String getMouseStrain(String strMouseID)
    {
        String Mousey = "";
        String MySQLStatement;

        
        MySQLStatement = "SELECT Strain.strainName " + 
                    " FROM Mouse INNER JOIN  ON Mouse.`_strain_key`=Strain.`_strain_key` " + 
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
                Mousey = "{" + tableData.getString("strainName") + "}";
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(Mousey);

    
    }
    public String getMouseGenoType(String strMouseID)
    {
        String Mousey = "";
        String MySQLStatement;
        
        MySQLStatement = "SELECT  Gene.geneSymbol as GeneSymbol , Gene.labSymbol, Gene.geneClass, " +
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
            Statement sqlStmt = dbConnect.createStatement();
            ResultSet tableData = sqlStmt.executeQuery(MySQLStatement);

            // Display the contents
            while (tableData.next())
            {
                
                Mousey = "{" + tableData.getString("GeneSymbol") + "}:";
                Mousey = Mousey + "{" + tableData.getString("labSymbol") + "}:";
                Mousey = Mousey +  "{" + tableData.getString("geneClass") + "}:";
                Mousey = Mousey +  "\t{" + tableData.getString("allele1") + "}";
                Mousey = Mousey +  "{" + tableData.getString("all1Conf") + "}:";
                Mousey = Mousey +  "\t{" + tableData.getString("allele2") + "}";
                Mousey = Mousey +  "{" + tableData.getString("all2Conf") + "}";
            
            }
            tableData.close();
            sqlStmt.close();
        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
        return(Mousey);        
   
    }
    
    private void conectToDatabase(String ConnectString, String uName, String uPasswd)
    {
        // First, we need to load the driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }

        catch (Exception e)
        {
            System.out.println("Unable to load driver: " + e);
        }

        /**  This creates the connection object for your database which will be used for the rest of the example */

        //    Connection myConnection = DriverManager.getConnection(url, myUser, myPassword);
        // ------------------------------------
        // Now, we can open a connection to the database and begin working
        try
        {
            // Get a DB connection from the DriverManager
            // Connection dbConnect = DriverManager.getConnection("jdbc:odbc:Tutorial", "", "");

            dbConnect = DriverManager.getConnection(ConnectString,uName, uPasswd);

            // Let's create a simple SQL statement to get all of the+
        }
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }
        
    
    }
    
    private String getMouseID(long strMouseKey)
    {
        String strTemp = "";

        // data from our table
        try
        {
            String MySQLStatement = "SELECT ID FROM Mouse" +
                    " WHERE Mouse.`_mouse_key` = " + strMouseKey + ";"; 
            
            Statement sqlStatement = dbConnect.createStatement();
            ResultSet tD = sqlStatement.executeQuery(MySQLStatement);

            tD.next();
            strTemp = tD.getString("ID");

            // blah
            tD.close();
            sqlStatement.close();

        }
        // Close all database resources to make sure they're free
        catch (SQLException e)
        {
            System.out.println("An error occured while interacting with DB: " + e);
        }

        return(strTemp);
    }

}
